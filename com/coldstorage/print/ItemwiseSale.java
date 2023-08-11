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
import com.coldstorage.dto.InvViewDto;
import com.coldstorage.excel.WriteExcel;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class ItemwiseSale  extends WriteExcel
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
   
   SimpleDateFormat sdf,sdf2;
   DecimalFormat df;
   ArrayList<?> salreg; 
   InvViewDto inv;
   String wtxt,monNm,sdate,edate, smon,inputFile,btnname,drvnm,printernm,brnm,divnm,filename;
   int year,depo,div,optn,doc_tp,code;
   private Date stdt,eddt;
   SheetSettings settings;
   ArrayList prtList;

   public ItemwiseSale(Integer year,Integer depo,Integer div, String sdate ,String edate ,String btnnm,String drvnm,String printernm,String brnm,Integer code,ArrayList prtList,Integer doc_tp) 

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
    	this.prtList=prtList;
    	this.code=code;
    	this.doc_tp=doc_tp;

    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mma");
    	filename="ItemWiseInward-"+sdf2.format(new Date());
    	wtxt="ItemWise";
    	if(this.div==99)
    	{
        	filename="GroupWiseInward-"+sdf2.format(new Date());
        	wtxt="GroupWise";

    	}	

    	jbInit();
    	
        File file=null;

  		if (Desktop.isDesktopSupported()) 
  		{
  		
  			    file=new File(drvnm+"\\"+filename+".xls");
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
		   salreg =   (ArrayList<?>) srg.ItemwiseSale(year,depo,div,stdt,eddt,doc_tp,code,prtList);
		   
		   

				  createExcel();

    	} catch (Exception e) {
			  e.printStackTrace();
		  }
	
	  }
   
//////excel file generation report ////////   
   
public void createExcel() throws WriteException, IOException {

		setOutputFile(drvnm+"\\"+filename+".xls");
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

	   workbook.createSheet(wtxt, 0);
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
//	   addCaption(sheet, 0, 0, brnm,10);
	   addCaption(sheet, 0, 0, "श्री लक्ष्मी आईस एण्ड  कोल्ड स्टोरेज प्रा.लि.",10);
	   sheet.mergeCells(0, 1, 12, 1);
	   addCaption1(sheet, 0, 1, wtxt+"/Partywise Inward  from "+sdate+" - "+edate,30);


	   	   addCaption2(sheet, 0, 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 1, 2, "नाम",30);
		   addCaption2(sheet, 2, 2, "शहर",15);
		   addCaption2(sheet, 3, 2, "मोबाईल  न .",15);
		   addCaption2(sheet, 4, 2, "रसीद क्रमांक",10);
		   addCaption2(sheet, 5, 2, "दिनाक",15);
		   addCaption2(sheet, 6, 2, "किस्म",15);
		   addCaption2(sheet, 7, 2, "नाम",20);
		   addCaption2(sheet, 8, 2, "कट्टे",10);
		   addCaption2(sheet, 9, 2, "वजन",12);
		   addCaption2(sheet, 10 , 2, "मार्का ",12);
		   addCaption2(sheet, 11, 2, "मंजिल ",12);
		   addCaption2(sheet, 12, 2, "गाला ",12);
		  


		   //settings.setHorizontalFreeze(5);
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
		   inv = (InvViewDto) salreg.get(i);
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
		   }

			   dash=inv.getDash();
			   if(inv.getDash()==0)
			   {
				   
				   addLabel(sheet, 0, r, inv.getMac_code(),dash);
				   addLabel(sheet, 1, r, inv.getMac_name_hindi(),dash);
				   addLabel(sheet, 2, r, inv.getMcity_hindi(),dash);
				   addLabel(sheet, 3, r, inv.getMobile(),dash);
				   addLabel(sheet,4, r, inv.getInv_no(),dash);
				   addLabel(sheet, 5, r, sdf.format(inv.getInv_date()),dash);
				   addLabel(sheet, 6, r, inv.getCategory_hindi(),dash);
				   addLabel(sheet, 7, r, inv.getPname_hindi(),dash);
				   addDouble(sheet,8, r, inv.getSqty(),dash);
				   addDouble(sheet,9, r, inv.getWeight(),dash);
				   addLabel(sheet, 10, r, inv.getMark_no(),dash);
				   addLabel(sheet, 11, r, inv.getFloor_no(),dash);
				   addLabel(sheet, 12, r, inv.getBlock_no(),dash);
			   }
			   else if(inv.getDash()==1)
			   {
				   addLabel(sheet, 0, r, "",dash);
				   addLabel(sheet, 1, r, "",dash);
				   addLabel(sheet, 2, r, inv.getMac_name_hindi(),dash);
				   addLabel(sheet, 3, r, "",dash);
				   addLabel(sheet, 4, r, "",dash);
				   addLabel(sheet, 5, r, "",dash);
				   addLabel(sheet, 6, r, "",dash);
				   addLabel(sheet, 7, r, "",dash);
				   addDouble(sheet,8, r, inv.getSqty(),dash);
				   addDouble(sheet,9, r, inv.getWeight(),dash);
				   addLabel(sheet, 10, r, "",dash);
				   addLabel(sheet, 11, r, "",dash);
				   addLabel(sheet, 12, r, "",dash);
			   }

			   
			   r++;
	   }	   
}



}
