package com.coldstorage.print;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.coldstorage.dao.PartyDAO;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.excel.WriteExcel;
import com.coldstorage.util.FigToWord;
import com.coldstorage.view.BaseClass;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.PrinterFactory;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class PartyPrint  extends WriteExcel
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
   DecimalFormat df;

   String word="";
   FigToWord fg;  	
   PartyDto mdt;
   String wtxt,sdate,edate,inputFile;
   int depo,div,bkcd,slno,year;
   private String btnname,drvnm,printernm,brnm,divnm; 
   ArrayList mis=null; 
   Vector mis1=null;
   SheetSettings settings; 
  public PartyPrint(Integer year,Integer depo,Integer div,String btnnm,String drvnm,String printernm,String brnm,String divnm) 
  
  {
    try 
    {
    	fg = new FigToWord();
    	this.r=0;
    	this.i=0;
    	this.brnm=brnm;
    	this.divnm=divnm;
    	this.dash=110;
    	this.depo=depo;
    	this.div=div;
    	this.year=year;
    	this.drvnm=drvnm;
		this.printernm=printernm;
		this.btnname=btnnm;
        jbInit();
  
    	File file=null;

		if (Desktop.isDesktopSupported()) {
			if (btnname.equalsIgnoreCase("View"))
			{
				file=new File(drvnm+"\\PARTY.txt");
				Desktop.getDesktop().edit(file);
				
			}
			if (btnname.equalsIgnoreCase("Excel"))
  			{
  				file=new File(drvnm+"\\PARTY.xls");
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
  				file=new File(drvnm+"\\PARTY.txt");
  				Desktop.getDesktop().edit(file);
  				
  			}	 

	        if (btnname.equalsIgnoreCase("Print"))
	        {
	        	this.port=new FilePort(printernm);
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
    	  
    	   String nm="";
    	   PartyDAO ms = new PartyDAO();
    		   mis =   (ArrayList) ms.getPartyList(depo, div);
		   mdt=null;
		   
		   
		   
		   
		   
		   

			  if (btnname.equalsIgnoreCase("Excel"))
			  {
				  createExcel();
			  }
			  else
			  {
				  detail();   // for printing
			  }

		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	
	  }
	  /////// print file option //////////////  
 public void detail() {

	   try {		    

		   df = new DecimalFormat("0.00");
		   
	 // printer.feedFormPerSoftware=true;
	   
	  
	  job= printer.getDefaultJobProperties();
      job.draftQuality=true;
      
   
    //  job.bottomMargin=4;
      //job.topMargin=2;
   //  job.paperSize=PaperSize.A4;  // paper size, only required if the pitch 
      								// and line spacing must be set automatically.
      job.cols=120;
      job.rows=68;
      job.pitch=10;

      printer.startJob(port,job);

      
      p=printer.getDefaultTextProperties();
      p.fontName="Arial";
      p.bold=true;
      p.proportional=true;
      p.doubleWide=true;
      
      

      
      prop=printer.getDefaultTextProperties();
      propBold=printer.getDefaultTextProperties();
      propBold.bold=true;
      propItalic=printer.getDefaultTextProperties();
      propItalic.italic=true;

      
     
        
       boolean first=true;
       col=0;
      // detail header
      for (i=0;i<mis.size();i++)
      {
    	  mdt = (PartyDto) mis.get(i);
    	  
    	  if(first)
    	  {
    	       head_Para();
    	       first=false;
    	  }
/*	    	  printer.printString(String.format("%3s", mdt.getPcode()),r,1,prop);
		      printer.printString(mdt.getPname(), r,10,prop);
		      printer.printString(mdt.getPack(), r,50,prop);
		      printer.printString(mdt.getIdentity(), r,50,prop);
		      printer.printString(mdt.getTax_type(), r,60,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getTax_per())), r,70,prop);
*/      	      r++;
	      
      }  // end of for loop
      

      printer.endJob();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
   }



   
   public void head_Para()
   {
	      //  header

	      printer.printString("Aristo Pharmaceuticals Pvt Ltd., ", r,25,p);
	      r++;
	      printer.printString("Stockiest Detail ", r,30,propBold);
	      r++;
     	  r=1;

	      printer.printHorizontalLine(r,1,dash); // dashLine -----------
	      r++;
  	      
	      printer.printString("A/c Code ",r,1,prop);
	      printer.printString("Party Name", r,30,prop);
	      printer.printString("Address1", r,40,prop);
  		  printer.printString("Identity",r,50,prop);
	      printer.printString("Tax Type", r,60,prop);
	      printer.printString("Tax %", r,70,prop);
	      r++;
	      printer.printHorizontalLine(r,1,dash); // dashLine -----------
	      r++;


   }
   

//////excel file generation report ////////   
   
public void createExcel() throws WriteException, IOException {

	   setOutputFile(drvnm+"\\PARTY.xls");
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
	   workbook.createSheet("PARTY", 0);
	   WritableSheet excelSheet = workbook.getSheet(0);
	   settings = excelSheet.getSettings();
	   createLabel(excelSheet);
	   createContent(excelSheet);

	   workbook.write();
	   workbook.close();
}


public void createHeader(WritableSheet sheet)
		   throws WriteException {
	    
			sheet.mergeCells(0, 0, 22, 0);
		   // Write a few headers
		   addCaption(sheet, 0, 0, BaseClass.loginDt.getBrnnm(),40);
	
		   
		   sheet.mergeCells(0, 2, 22, 2);
		   addCaption1(sheet, 0, 2, "Account Master ",30);
	   addCaption2(sheet, 0, 3, "A/c Code",10);
	   addCaption2(sheet, 1, 3, "Party Name",30);
	   addCaption2(sheet, 2, 3, "Address1",30);
	   addCaption2(sheet, 3, 3, "Address2",30);
	   addCaption2(sheet, 4, 3, "Address3",30);
	   addCaption2(sheet, 5, 3, "City",20);
	   addCaption2(sheet, 6, 3, "Pin",10);
	   addCaption2(sheet, 7, 3, "Phone",15);
	   addCaption2(sheet, 8, 3, "Mobile",15);
	   addCaption2(sheet, 9, 3, "Transporter",30);
	   addCaption2(sheet, 10, 3, "GST NO",15);
	   addCaption2(sheet, 11, 3, "Email-Id",30);
	   addCaption2(sheet, 12, 3, "Lock Y/N",10);
	   settings.setHorizontalFreeze(2);
	   settings.setVerticalFreeze(4);
	   

}  


public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   col=0;
	   r=4;
	   int dash=0;
	   // detail header
	   int size=0;
	   size=div==4 ?mis1.size():mis.size();
	   PartyDto pdt=null;
	   Vector c=null;
	   for (i=0;i<size;i++)
	   {
			   pdt = (PartyDto) mis.get(i);
		   
		   
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
		   }

			   dash=0;

			   
			   addLabel(sheet, 0, r, pdt.getMac_code(),dash);
			   addLabel(sheet, 1, r, pdt.getMac_name(),dash);
			   addLabel(sheet, 2, r, pdt.getMadd1(),dash);
			   addLabel(sheet, 3, r, pdt.getMadd2(),dash);
			   addLabel(sheet, 4, r, pdt.getMadd3(),dash);
			   addLabel(sheet, 5, r, pdt.getMcity(),dash);
			   addLabel(sheet, 6, r, pdt.getMpin(),dash);
			   addLabel(sheet, 7, r, pdt.getMphone(),dash);
			   addLabel(sheet, 8, r, pdt.getMobile(),dash);
			   addLabel(sheet, 9, r, pdt.getMtranspt(),dash);
			   addLabel(sheet, 10, r, pdt.getGst_no(),dash);
			   addLabel(sheet, 11, r, pdt.getMemail(),dash);
			   addLabel(sheet, 12, r, pdt.getMtype1(),dash);
			   r++;

	   }


}

}
