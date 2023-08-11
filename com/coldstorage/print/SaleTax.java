package com.coldstorage.print;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.coldstorage.dao.SalRegDAO;
import com.coldstorage.dto.InvFstDto;
import com.coldstorage.excel.WriteExcel;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class SaleTax  extends WriteExcel
{    
   /**   
	 * 
	 */
	private static final long serialVersionUID = 1L;
   FilePort port;
   TextPrinter printer;
   JobProperties job;
   TextProperties prop,propBold,p,propItalic;
   int r,i,col;
   int dash;
   double pgross=0.00;
   double pnet=0.00;
   double cgross=0.00;
   double cnet=0.00;
   double gross5=0.00;
   double vat5=0.00;
   double gross14=0.00;
   double vat14=0.00;
   double gross15=0.00;
   double vat15=0.00;
   
   double pgross5=0.00;
   double pvat5=0.00;
   double pgross14=0.00;
   double pvat14=0.00;
   double pgross15=0.00;
   double pvat15=0.00;
   
   SimpleDateFormat sdf;
   DecimalFormat df;
   ArrayList<?> salreg; 
   InvFstDto inv;
   String wtxt,monNm,sdate,edate, smon,inputFile,btnname,drvnm,printernm,brnm,divnm;
   int year,depo,div,optn,doc_tp;
   private Date stdt,eddt;
   SheetSettings settings; 
   public SaleTax(Integer year,Integer depo,Integer div, String sdate ,String edate ,String btnnm,String drvnm,String printernm,String brnm,String divnm,Integer doc_tp) 

   { 
    try {
    	
    	this.r=0;
    	this.i=0;
    	this.dash=180;
    	this.year=year;
    	this.depo=depo;
    	this.div=div;
      	this.sdate=sdate;
    	this.edate=edate;
    	this.btnname=btnnm;
    	this.drvnm=drvnm;
    	this.printernm=printernm;
    	this.brnm=brnm;
    	this.divnm=divnm;
    	this.doc_tp=doc_tp;
    	
    	jbInit();
    	
        File file=null;

  		if (Desktop.isDesktopSupported()) 
  		{
  		
  				file=new File(drvnm+"\\vattax.xls");
  				Desktop.getDesktop().open(file);
  				
  		}
    	
    	
   

    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }


    private void jbInit() throws Exception {
    	 
    	   
        try {
          
          this.order();


        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }    
    

   /**
    * test characters set conversion
    */
   public void order() {

    try {
    	
    	   sdf = new SimpleDateFormat("dd/MM/yyyy");
		   
    	   stdt=sdf.parse(sdate);
    	   eddt=sdf.parse(edate);
    	   
 
    	
           SalRegDAO srg = new SalRegDAO();
		   salreg =   (ArrayList<?>) srg.vatTaxSale(smon,year,depo,div,stdt,eddt,doc_tp);
		   
		   

				  createExcel();

    	} catch (Exception e) {
			  e.printStackTrace();
		  }
	
	  }
   
//////excel file generation report ////////   
   
public void createExcel() throws WriteException, IOException {

	   setOutputFile(drvnm+"\\vattax.xls");
	   write();
}


public  void setOutputFile(String inputFile) {
	   this.inputFile = inputFile;
} 

public void write() throws IOException, WriteException {
	   File file = new File(inputFile);
	   WorkbookSettings wbSettings = new WorkbookSettings();

	   wbSettings.setLocale(new Locale("en", "EN"));

	   WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);

	   workbook.createSheet("vattax", 0);
	   WritableSheet excelSheet = workbook.getSheet(0);
	   settings = excelSheet.getSettings();
	   createLabel(excelSheet);
	   createContent(excelSheet);

	   workbook.write();
	   workbook.close();
}


public void createHeader(WritableSheet sheet)
		   throws WriteException {
	    
	   
	   //Merge col[0-11] and row[0]
	   sheet.mergeCells(0, 0, 12, 0);
	   // Write a few headers
	   addCaption(sheet, 0, 0, brnm,10);
	   
	   sheet.mergeCells(0, 1, 12, 1);
	   addCaption1(sheet, 0, 1, "Vat Tax from "+sdate+" - "+edate,30);


	      
	   if(doc_tp==40)
	   {
		   addCaption2(sheet, 0, 2, "Name of Dealer",40);
		   addCaption2(sheet, 1, 2, "Tin Number",15);
		   addCaption2(sheet, 2, 2, "Total Sales",12);
		   addCaption2(sheet, 3, 2, "Quantity",12);
		   addCaption2(sheet, 4, 2, "Rate of Vat%",12);
		   addCaption2(sheet, 5, 2, "Amount of Vat",12);
		   addCaption2(sheet, 6, 2, "Invoice No",12);
		   addCaption2(sheet, 7, 2, "Invoice Date",12);
		   addCaption2(sheet, 8, 2, "\"ET not Paid\" seal affixed",12);
	   }
	   else
	   {
		   addCaption2(sheet, 0, 2, "Name of Dealer",40);
		   addCaption2(sheet, 1, 2, "Tin Number",15);
		   addCaption2(sheet, 2, 2, "Total Purchase",12);
		   addCaption2(sheet, 3, 2, "Quantity",12);
		   addCaption2(sheet, 4, 2, "Rate of Vat%",12);
		   addCaption2(sheet, 5, 2, "Amount of Vat",12);
		   addCaption2(sheet, 6, 2, "Invoice No",12);
		   addCaption2(sheet, 7, 2, "Invoice Date",12);
		   addCaption2(sheet, 8, 2, "ITR Claimed",12);
		   addCaption2(sheet, 9, 2, "ITR On goods other than plant,machinery,equiment's and parts thereof / On plant machinery and partys thereof",60);
		   addCaption2(sheet, 10, 2, "TDS Deucted",12);
		   addCaption2(sheet, 11, 2, "Goods",12);
		   addCaption2(sheet, 12, 2, "\"ET not Paid\" seal affixed",12);

	   }
	   
//	   settings.setHorizontalFreeze(2);
	   settings.setVerticalFreeze(3);

}  


public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   col=0;
	   r=3;
	   pgross=0.00;
	   pnet=0.00;
	   int dash=0;
	   // detail header
	   int size=salreg.size();
	   for (i=0;i<size;i++)
	   {
		   inv = (InvFstDto) salreg.get(i);
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
		   }

			   dash=0;
			   
			   addLabel(sheet, 0, r, inv.getParty_name(),dash);
			   addLabel(sheet, 1, r, inv.getTin_no(),dash);
			   addDouble(sheet, 2, r, inv.getGross5(),dash);
			   addLabel(sheet, 3, r, "",dash);
			   addDouble(sheet, 4, r, inv.getTax_1(),dash);
			   addDouble(sheet, 5, r, inv.getVat5(),dash);
			   addLabel(sheet, 6, r, "",dash);
			   addLabel(sheet, 7, r, "",dash);
			   addLabel(sheet, 8, r, "",dash);
			   if(doc_tp==60)
			   {
				   addLabel(sheet, 8, r, "Yes",dash);
				   addLabel(sheet, 9, r, "",dash);
				   addLabel(sheet, 10, r, "No",dash);
				   addLabel(sheet, 11, r, "",dash);
				   addLabel(sheet, 12, r, "",dash);
				   
			   }
		   r++;
	   }	   
}



}
