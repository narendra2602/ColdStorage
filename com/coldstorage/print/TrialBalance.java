package com.coldstorage.print;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
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

import com.coldstorage.dao.AccountDAO;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.excel.WriteExcel;
import com.coldstorage.util.FigToWord;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.PrinterFactory;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class TrialBalance  extends WriteExcel
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
   SimpleDateFormat sdf1,sdf2;
   DecimalFormat df;

   String word="";
   FigToWord fg;  	
   RcpDto mdt;
   String wtxt,sdate,edate,inputFile;
   int depo,div,bkcd,slno,year;
   private String btnname,drvnm,printernm,brnm,divnm,filename; 
   private Date stdt,eddt;
   ArrayList<?> mis; 
   SheetSettings settings; 
   double ptot=0.00;
   Document doc;
   PdfWriter docWriter;
   PdfContentByte cb;
   int y;

  public TrialBalance(Integer year,Integer depo,Integer div, String sdate ,String edate ,String btnnm,String drvnm,String printernm,String brnm,String divnm,Integer doc_tp) 
  
  {
    try 
    {
    	fg = new FigToWord();
    	this.r=0;
    	this.i=0;
    	this.dash=110;
    	this.brnm=brnm;
    	this.divnm=divnm;
    	this.depo=depo;
    	this.div=div;
    	this.bkcd=bkcd;
    	this.year=year;
    	this.slno=slno;
      	this.sdate=sdate;
    	this.edate=edate;
    	this.btnname=btnnm;
		this.drvnm=drvnm;
		this.printernm=printernm;
    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mma");
    	filename="TRIALBALANCE-"+sdf2.format(new Date());

        jbInit();
  
    	File file=null;

		if (Desktop.isDesktopSupported()) {
			if (btnname.equalsIgnoreCase("View"))
			{
				file=new File(drvnm+"\\"+filename+".pdf");
				Desktop.getDesktop().open(file);
				
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
    	   sdf1 = new SimpleDateFormat("dd/MM/yy");
    	   stdt=sdf.parse(sdate);
    	   eddt=sdf.parse(edate);

    	   
    	   AccountDAO ms = new AccountDAO();
		   mis =   (ArrayList<?>) ms.getTrialBalance(year,div, depo, stdt,eddt);
		   mdt=null;
		   
		   
		   

			  if (btnname.equalsIgnoreCase("Excel"))
			  {
				  createExcel();
			  }
			  else
			  {
				  createPDF();   // for printing
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
      int size=mis.size();
      for (i=0;i<size;i++)
      {
    	  mdt = (RcpDto) mis.get(i);
    	  
    	  if(first)
    	  {
    	       head_Para();
    	       first=false;
    	  }
	      if (mdt.getDash()==0)  	  
	      {

	    	  printer.printString(mdt.getVac_code(),r,1,prop);
		      printer.printString(mdt.getParty_name(), r,10,prop);
		      printer.printString(String.format("%12s",df.format(mdt.getVamount())), r,50,prop);
		      printer.printString(String.format("%12s",df.format(mdt.getCr_amt())), r,70,prop);
      	      r++;
	      }

	      if (mdt.getDash()==1) 
	      {
	          printer.printHorizontalLine(r,1,dash); // dashLine -----------
	          r++;
	          printer.printString(mdt.getParty_name(), r,10,prop);
		      printer.printString(String.format("%12s",df.format(mdt.getVamount())), r,50,prop);
		      printer.printString(String.format("%12s",df.format(mdt.getCr_amt())), r,70,prop);
      	      r++;
	          printer.printHorizontalLine(r,1,dash); // dashLine -----------
	          r++;
	      }
	      
	      if (r > 56 )
	      {
	          printer.printHorizontalLine(r,1,dash); // dashLine -----------
	          r++;
	    	  printer.newPage();
	    	  first=true;
	    	  
	      }
	      
      }  // end of for loop
      
      printer.endJob();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
   }



   
   public void head_Para()
   {
	      //  header

   	r=1;
		
	      printer.printString(""+brnm+"", r,25,p);
	      r++;
	      printer.printString("Trial Balance as on "+edate, r,40,propBold);
	      r++;
	      printer.printHorizontalLine(r,1,dash); // dashLine -----------
	      r++;
	      printer.printString("Code.",r,1,prop);
	      printer.printString("Description", r,10,prop);
  		  printer.printString("   Dr Amount",r,50,prop);
  		  printer.printString("   Cr Amount",r,70,prop);
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
	   workbook.createSheet("TrialBalance", 0);
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

	   sheet.mergeCells(0, 0, 3, 0);
	   addCaption(sheet, 0, 0, brnm,10);

		sheet.mergeCells(0, 1, 3, 1);
		addCaption1(sheet, 0, 1, "Trial Balance as on "+edate,10);


	   addCaption2(sheet, 0, 3, "Code.",10);
	   addCaption2(sheet, 1, 3, "Description",40);
	   addCaption2(sheet, 2, 3, "Dr Amount",18);
	   addCaption2(sheet, 3, 3, "Cr Amount",18);
	   
	  // settings.setHorizontalFreeze(4);
	   settings.setVerticalFreeze(4);

	   

}  


public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   col=0;
	   r=4;
	   ptot=0.00;
	   int dash=0;
	   int cr=0;
	   // detail header
	   int size=mis.size();
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
			   addLabel(sheet, 0, r, mdt.getVac_code(),dash);
			   addLabel(sheet, 1, r, mdt.getParty_name(),dash);
			   addDouble(sheet, 2, r, mdt.getVamount(),dash);
			   addDouble(sheet, 3, r, mdt.getCr_amt(),dash);
	  		  }
	  			 
	  		 if (mdt.getDash()==1)
	  		  {
				   dash=2;
				   addLabel(sheet, 0, r, " ",dash);
				   addLabel(sheet, 1, r, mdt.getParty_name(),dash);
				   addDouble(sheet, 2, r, mdt.getVamount(),dash);
				   addDouble(sheet, 3, r, mdt.getCr_amt(),dash);
 
	  		  }
	  		 r++;

	   }


}


 
private void createPDF (){


	try {


		sdf = new SimpleDateFormat("dd/MM/yyyy");
		df = new DecimalFormat("0.00");
		 
		int psize=mis.size();
			  
		
		boolean first=true;
		boolean more=false;
		generateDocument();
		int pg=1;
		y=658;
		first=true;
			
		for(int j=0; j < psize; j++ )
		{
			  mdt = (RcpDto) mis.get(j);
			 
				if (first)
				{
					generateHeader(doc, cb);
					first=false;
				}  
			
					if(mdt.getDash()==1 )
					{
					    
						cb.moveTo(15,y); // DESCRIPTION : Vertical lines
						cb.lineTo(602,y);
						y=y-11;
						createContent2(cb,17,y, String.format("%-10s",mdt.getParty_name()),PdfContentByte.ALIGN_LEFT);
						createContent2(cb,475,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
						createContent2(cb,575,y, String.format("%10s",df.format(mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
						y=y-11;

						cb.moveTo(15,y); // DESCRIPTION : Vertical lines
						cb.lineTo(602,y);
						cb.stroke();
						more=true;
					}
					if(mdt.getDash()==0  )
					{
 							createContent2(cb,17,y, String.format("%-10s",mdt.getParty_name()),PdfContentByte.ALIGN_LEFT);
							createContent2(cb,475,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
							createContent2(cb,575,y, String.format("%10s",df.format(mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
					}
				
				y=y-11;
  
				if (y < 50)
				{
					createHeadings1(cb,300,31,"Page - "+(pg++));
					doc.newPage();
					generateHeader(doc, cb);
					y=658;
				}

			
             //  more=true; // only for pdf
               

			}// end of outer for loop 

		createHeadings1(cb,307,31,"Page - "+(pg++));

		if (doc != null)
		{
			doc.close();
		}
		if (docWriter != null)
		{
			docWriter.close();
		}

	} // end of try
	catch (Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
		if (doc != null)
		{
			doc.close();
		}
		if (docWriter != null)
		{
			docWriter.close();
		}
	}
}



private void generateHeader(Document doc, PdfContentByte cb)  {

	try 
	{
		redtext(cb,270,722,brnm);
		cb.setLineWidth(1f);

			cb.moveTo(15,690); // DESCRIPTION : Vertical lines
			cb.lineTo(602,690);
			cb.stroke();
			cb.moveTo(15,668); // DESCRIPTION : Vertical lines
			cb.lineTo(602,668);
			cb.stroke();
			createHeadings1(cb,245,708, "Trial Balance As on Date "+edate);

			//createHeadings(cb,17,678,"Schedule "+sch++);
			
			createHeadings(cb,17,678,"Particulars");
			createHeadings(cb,422,678,"Debit Amount");
			createHeadings(cb,515,678,"Credit Amount");
	}

	catch (Exception ex){
		ex.printStackTrace();
	}

}



private void generateDocument()
{

	try {
		doc = new Document();
		//			docWriter = null;
		initializeFonts();

		
		
		String path = drvnm+"\\"+filename+".pdf";
		doc = new Document();
			docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));
		
		doc.addAuthor("Ishika");
		doc.addCreationDate();
		doc.addProducer();
		doc.addCreator("prompt");
		doc.addTitle("Report");
		doc.setPageSize(PageSize.LETTER);

		doc.open();
		cb = docWriter.getDirectContent();

	} catch (Exception e) {
		e.printStackTrace();
	}

	
}
   
   

}
