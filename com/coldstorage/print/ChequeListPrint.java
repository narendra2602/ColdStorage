package com.coldstorage.print;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.PageOrientation;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.coldstorage.dao.AccountDAO;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.excel.WriteExcel;
import com.coldstorage.util.FigToWord;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.PrinterFactory;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class ChequeListPrint  extends WriteExcel
{    
   /**   
	 * 
	 */
	 
   FilePort port;
   TextPrinter printer;
   JobProperties job;
   TextProperties prop,propBold,p,propItalic;
   int r,i,col;
   int dash;
   SimpleDateFormat sdf;
   SimpleDateFormat sdf1;
   DecimalFormat df;

   String word="";
   FigToWord fg;  	
   RcpDto mdt;
   String wtxt,sdate,edate,inputFile;
   int depo,div,bkcd,slno,year,optn;
   private String btnname,drvnm,printernm,brnm,bank_name; 
   private Date stdt,eddt;
   ArrayList<?> mis; 
   SheetSettings settings; 
   OutputStream email;
   double ptot=0.00;
  public ChequeListPrint(Integer year,Integer depo,Integer div, String sdate ,String edate ,Integer bkcd,String btnnm,String drvnm,String printernm,String brnm,String bank_name,Integer optn,OutputStream email) 
  
  {
    try 
    {
    	fg = new FigToWord();
    	this.r=0;
    	this.i=0;
    	this.dash=110;
    	this.brnm=brnm;
    	this.bank_name=bank_name;
    	this.depo=depo;
    	this.div=div;
    	this.bkcd=bkcd;
    	this.year=year;
      	this.sdate=sdate;
    	this.edate=edate;
    	this.btnname=btnnm;
		this.drvnm=drvnm;
		this.printernm=printernm;
		this.optn=optn;
		this.email=email;
		jbInit();
  
    	File file=null;

		if (Desktop.isDesktopSupported()) {
			if (btnname.equalsIgnoreCase("View"))
			{
				file=new File(drvnm+"\\CHEQUELIST.txt");
				Desktop.getDesktop().edit(file);
				
			}
			if (btnname.equalsIgnoreCase("Excel"))
  			{
  				file=new File(drvnm+"\\COLLECTIONLIST.xls");
  				Desktop.getDesktop().open(file);
  				
  			}     
		}
        
         

    }
    catch(Exception e) 
    {
      e.printStackTrace();
    }
    
  }
  

  private void jbInit() throws Exception {
	  
	  File file=null;
	    try {

 			  

 			if (btnname.equalsIgnoreCase("View"))
	        {
	 	        this.port=new FilePort(drvnm+"\\CHEQUELIST.txt");
	 	        this.printer=PrinterFactory.getPrinter("PLAIN");
	        } 	
 			

	        
	        this.slipPrint();


	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }



   /**
    * test characters set conversion
    */
   public void slipPrint() {

    try {
    	
    	   sdf = new SimpleDateFormat("dd/MM/yyyy");
    	   sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
    	   stdt=sdf.parse(sdate);
    	   eddt=sdf.parse(edate);

    	   
    	   AccountDAO ms = new AccountDAO();
		   mis =   (ArrayList<?>) ms.getChqeueList(year,div, depo, bkcd, stdt,eddt,optn);
		   mdt=null;
		   
		   createExcel();

		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	
	  }
//////excel file generation report ////////   
   
public void createExcel() throws WriteException, IOException {

	   setOutputFile(drvnm+"\\COLLECTIONLIST.xls");
	   write();
}


public  void setOutputFile(String inputFile) {
	   this.inputFile = inputFile;
} 		

public void write() throws IOException, WriteException {
	   File file = new File(inputFile);
	   WorkbookSettings wbSettings = new WorkbookSettings();

	   wbSettings.setLocale(new Locale("en", "EN"));

	   WritableWorkbook workbook = null;
	   if(email==null)
		   workbook = Workbook.createWorkbook(file, wbSettings);
	   else
		   workbook = Workbook.createWorkbook(email);

	   workbook.createSheet("CollectionList", 0);
	   WritableSheet excelSheet = workbook.getSheet(0);
	   settings = excelSheet.getSettings();
	   settings.setPrintTitlesRow(0, 3);
	   settings.setOrientation(PageOrientation.PORTRAIT);
	   settings.setFitWidth(1);

	   createLabel(excelSheet);
	   createContent(excelSheet);

	   workbook.write();
	   workbook.close();
}


public void createHeader(WritableSheet sheet)
		   throws WriteException {

	

	   //Merge col[0-11] and row[0]

	   sheet.mergeCells(0, 0, 6, 0);
	   addCaption(sheet, 0, 0, brnm,10);

		//sheet.mergeCells(0, 1, 5, 1);
		//   addCaption1(sheet, 0, 1, bank_name,10);

	   sheet.mergeCells(0, 2, 6, 2);
	   addCaption2(sheet, 0, 2, "Collection List from "+sdate+" - "+edate,40);

	   addCaption3(sheet, 0, 3, "AREA",20,2);
	   addCaption3(sheet, 1, 3, "DATE",12,2);
	   addCaption3(sheet, 2, 3, "TYPE",15,2);
	   addCaption3(sheet, 3, 3, "PARTICULARS",20,2);
	   addCaption3(sheet, 4, 3, "PARTY NAME",35,2);
	   addCaption3(sheet, 5, 3, "CITY",20,2);
	   addCaption3(sheet, 6, 3, "AMOUNT",14,2);

	   //settings.setHorizontalFreeze(5);
	   settings.setVerticalFreeze(4);
	   
	   

}  


public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   col=0;
	   r=4;
	   ptot=0.00;
	   int dash=0;
	   int size=mis.size();
	   // detail header
	   for (i=0;i<size;i++)
	   {
		   mdt = (RcpDto) mis.get(i);
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
		   }

	  		 if (mdt.getDash()==0)
	  		  {
			   dash=0;
			   if(mdt.getChq_amt()<0)
				   dash=5;
			   addLabel(sheet, 0, r, mdt.getExp_name(),dash);
			   addLabel(sheet, 1, r, sdf1.format(mdt.getVou_date()).toString(),dash);
			   addLabel(sheet, 2, r, mdt.getSub_name(),dash);
			   addLabel(sheet, 3, r, mdt.getChq_no(),dash);
			   addLabel(sheet, 4, r, mdt.getParty_name(),dash);
			   addLabel(sheet, 5, r, mdt.getCity(),dash);
			   addDouble(sheet, 6, r, mdt.getChq_amt(),dash);
	  		  }
	  		 if (mdt.getDash()==1)
	  		  {
	  			dash=1;
			   addLabel(sheet, 0, r, " ",dash);
			   addLabel(sheet, 1, r, " ",dash);
			   addLabel(sheet, 2, r, " ",dash);
			   addLabel(sheet, 3, r, " ",dash);
			   addLabel(sheet, 4, r, " ",dash);
			   addLabel(sheet, 5, r, " Total : ",dash);
			   addDouble(sheet, 6, r, mdt.getChq_amt(),dash);

	  		  }

	  		 if (mdt.getDash()==2)
	  		  {
	  			dash=2;
			   addLabel(sheet, 0, r, " ",dash);
			   addLabel(sheet, 1, r, " ",dash);
			   addLabel(sheet, 2, r, " ",dash);
			   addLabel(sheet, 3, r, " ",dash);
			   addLabel(sheet, 4, r, " ",dash);
			   addLabel(sheet, 5, r, " Grand Total : ",dash);
			   addDouble(sheet, 6, r, mdt.getChq_amt(),dash);

	  		  }
	  		 r++;

	   }


}


 
   
   

}
