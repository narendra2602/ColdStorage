package com.coldstorage.print;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.coldstorage.dao.ProductDAO;
import com.coldstorage.dao.TaxDAO;
import com.coldstorage.dto.ProductDto;
import com.coldstorage.dto.TaxDto;
import com.coldstorage.excel.WriteExcel;
import com.coldstorage.util.FigToWord;
import com.coldstorage.view.BaseClass;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.PrinterFactory;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class ProductPrint  extends WriteExcel
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
   SimpleDateFormat sdf,sdf2;
   DecimalFormat df;

   String word="";
   FigToWord fg;  	
   ProductDto mdt;
   String wtxt,sdate,edate,inputFile;
   int depo,div,bkcd,slno,stcd;
   private String btnname,drvnm,printernm,stname,filename; 
   Vector mis=null; 
   HashMap prd;
   SheetSettings settings; 
   TaxDAO tdao;
   TaxDto tdto=null;
   HashMap tmap=null;
  public ProductPrint(Integer stcd,Integer depo,Integer div,String btnnm,String drvnm,String printernm,HashMap prd) 
  
  {
    try 
    {
    	fg = new FigToWord();
    	this.r=0;
    	this.i=0;
    	this.dash=110;
    	this.depo=depo;
    	this.div=div;
    	this.stcd=stcd;  
    	this.drvnm=drvnm;
		this.printernm=printernm;
		this.btnname="Excel";
		this.stname=btnnm;
    	this.prd=prd;
    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mma");
    	filename="ITEM LIST-"+"-"+sdf2.format(new Date());

        jbInit();
  
    	File file=null;

		if (Desktop.isDesktopSupported()) {
			if (btnname.equalsIgnoreCase("View"))
			{
				file=new File(drvnm+"\\"+filename+".TXT");
				Desktop.getDesktop().edit(file);
				
			}
			if (btnname.equalsIgnoreCase("Excel"))
  			{
  				file=new File(drvnm+"\\"+filename+".xls");
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
	 	        this.port=new FilePort(drvnm+"\\"+filename+".TXT");
	 	        this.printer=PrinterFactory.getPrinter("PLAIN");
	 	        
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
    	   
//    	   Date gstdate = sdf.parse("01/07/2017");
    	   Date gstdate = new Date();

    	   tdao = new TaxDAO();
    	   
    	   ProductDAO ms = new ProductDAO();
    	   
		   mis =   (Vector) ms.getPrdDetail(depo,div);
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
    	  mdt = (ProductDto) mis.get(i);
    	  
    	  if(first)
    	  {
    	       head_Para();
    	       first=false;
    	  }
	    	  printer.printString(String.format("%3s", mdt.getPcode()),r,1,prop);
		      printer.printString(mdt.getPname(), r,10,prop);
		      printer.printString(mdt.getPack(), r,50,prop);
		      printer.printString(mdt.getIdentity(), r,50,prop);
		      printer.printString(mdt.getTax_type(), r,60,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getTax_per())), r,70,prop);
      	      r++;
	      
      }  // end of for loop
      

      printer.endJob();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
   }



   
   public void head_Para()
   {
	      //  header

	      printer.printString(""+BaseClass.loginDt.getBrnnm()+"]", r,25,p);
	      r++;
	      printer.printString("Product Master ", r,30,propBold);
	      r++;
     	  r=1;

	      printer.printHorizontalLine(r,1,dash); // dashLine -----------
	      r++;
  	      
	      printer.printString("Code ",r,1,prop);
	      printer.printString("Product Name", r,10,prop);
	      printer.printString("Pack", r,40,prop);
  		  printer.printString("Short Name",r,50,prop);
	      printer.printString("Tax Type", r,60,prop);
	      printer.printString("Tax %", r,70,prop);
	      r++;
	      printer.printHorizontalLine(r,1,dash); // dashLine -----------
	      r++;


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
	   workbook.createSheet("Product", 0);
	   WritableSheet excelSheet = workbook.getSheet(0);
	   settings = excelSheet.getSettings();
	   createLabel(excelSheet);
	   createContent(excelSheet);

	   workbook.write();
	   workbook.close();
}


public void createHeader(WritableSheet sheet)
		   throws WriteException {
	    
	   sheet.mergeCells(0, 0, 11, 0);
	   // Write a few headers
	   addCaption(sheet, 0, 0, BaseClass.loginDt.getBrnnm(),40);
	   
	   sheet.mergeCells(0, 1, 11, 1);
	   addCaption1(sheet, 0, 1, "Product Master ",30);


	   addCaption2(sheet, 0, 3, "Group Name ",15);
	   addCaption2(sheet, 1, 3, "Product Name",40);
	   addCaption2(sheet, 2, 3, "Packing",15);
	   addCaption2(sheet, 3, 3, "Hindi Name",15);
	   addCaption2(sheet, 4, 3, "Rate",15);
	   settings.setHorizontalFreeze(3);
	   settings.setVerticalFreeze(4);
	   

}  


public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   col=0;
	   r=4;
	   int dash=0;
	   // detail header
	   int size=mis.size();
	   Vector col=null;
	   ProductDto pdt=null;
	    
	   for (i=0;i<size;i++)
	   {
		    col=(Vector) mis.get(i);
		   pdt = (ProductDto) col.get(2);
		  
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
		   }

			   dash=0;
			   addLabel(sheet, 0, r, pdt.getGroup_name(),dash);
			   addLabel(sheet, 1, r, pdt.getPname(),dash);
			   addLabel(sheet, 2, r, pdt.getPack(),dash);
			   addLabel(sheet, 3, r, pdt.getPack_name(),dash);
			   addDouble(sheet, 4, r, pdt.getNet_rt1(),dash);
			   r++;

	   }


}

   

}
