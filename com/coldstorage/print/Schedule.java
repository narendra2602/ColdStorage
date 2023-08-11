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
import jxl.format.PageOrientation;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.coldstorage.dao.AccountDAO;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.excel.WriteExcel;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;


public class Schedule  extends WriteExcel
{    
   /**   
	 * 
	 */
	 
   int r,i,col;
   int dash;
   SimpleDateFormat sdf;
   SimpleDateFormat sdf2;
   DecimalFormat df;

   RcpDto mdt;
   String wtxt,sdate,edate,inputFile;
   int depo,div,bkcd,slno,year;
   private String btnname,drvnm,printernm,brnm,divnm,filename; 
   private Date stdt,eddt;
   ArrayList<?> mis; 
   WritableWorkbook workbook;
   SheetSettings settings; 
   double ptot=0.00;
   char sch;
   String whead=null;
   
   
    
   
   Document doc;
   PdfWriter docWriter;
   PdfContentByte cb;
   int y;

  public Schedule(Integer year,Integer depo,Integer div, String sdate ,String edate ,String btnnm,String drvnm,String printernm,String brnm,String divnm,Integer doc_tp) 
  
  {
    try 
    {
    	this.r=0;
    	this.i=0;
    	this.dash=110;
    	this.brnm=brnm;
    	this.divnm=divnm;
    	this.depo=depo;
    	this.div=div;
    	this.year=year;
      	this.sdate=sdate;
    	this.edate=edate;
    	this.btnname=btnnm;
		this.drvnm=drvnm;
		this.printernm=printernm;
    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mma");
    	filename="SCHEDULE-"+sdf2.format(new Date());

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
	  
	    try {
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
    	   stdt=sdf.parse(sdate);
    	   eddt=sdf.parse(edate);

    	   
    	   AccountDAO ms = new AccountDAO();
		   mis =   (ArrayList<?>) ms.getSchedule(year,div, depo, stdt,eddt);
		   mdt=null;
		   mdt=(RcpDto) mis.get(0);
		   whead=mdt.getBank_name(); // head name 
		   
		   
		   

			  if (btnname.equalsIgnoreCase("Excel"))
			  {
				  createExcel();
			  }
			  else
			  {
				  createPDF();
				 // detail();   // for printing
			  }

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

	   workbook = Workbook.createWorkbook(file, wbSettings);
	   workbook.createSheet("Schedule-A", 0);
	   WritableSheet excelSheet = workbook.getSheet(0);
	   settings = excelSheet.getSettings();

	   settings.setPrintTitlesRow(0, 3);
	   settings.setOrientation(PageOrientation.PORTRAIT);
	  // settings.setDefaultRowHeight(360);
	   settings.setLeftMargin(0.5);
	   settings.setRightMargin(0.5);
	   settings.setTopMargin(0.5);
	   settings.setBottomMargin(0.5);
	   settings.setFitWidth(1);

	  
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
		addCaption1(sheet, 0, 1, whead+" Schedule  ",10);
		
		sheet.mergeCells(0, 2, 3, 2);
		addCaption1(sheet, 0, 2, "For the year ended "+edate,10);

		sch='A';
	   addCaption3(sheet, 0, 3, "Schedule "+sch++,40,0);
	   addCaption2(sheet, 0, 4, "Particulars",40);
	   addCaption2(sheet, 1, 4, "Debit Amount",18);
	   addCaption2(sheet, 2, 4, "Credit Amount",18);
	   
	  // settings.setHorizontalFreeze(4);
	   settings.setVerticalFreeze(4);

	   

}  
public void createNewSheet(WritableSheet sheet)
		   throws WriteException {

	

	   //Merge col[0-11] and row[0]

	   sheet.mergeCells(0, 0, 3, 0);
	   addCaption(sheet, 0, 0, brnm,10);

		sheet.mergeCells(0, 1, 3, 1);
		addCaption1(sheet, 0, 1, whead+" Schedule  ",10);

		addCaption3(sheet, 0, 3, "Schedule "+sch++,40,0);
		addCaption2(sheet, 0, 4, "Particulars",40);
		addCaption2(sheet, 1, 4, "Debit Amount",18);
		addCaption2(sheet, 2, 4, "Credit Amount",18);

	   
	  // settings.setHorizontalFreeze(4);
	   settings.setVerticalFreeze(4);

	   

}  

public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   col=0;
	   r=5;
	   ptot=0.00;
	   int dash=0;
	   // detail header
	   int size=mis.size();
	   boolean second=false;
	   int j=1;
	   for (i=0;i<size;i++)
	   {
		   mdt = (RcpDto) mis.get(i);
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
		   }

		   if(second)
		   {						   
			   whead=mdt.getBank_name();
			   workbook.createSheet("Schedule-"+sch,j);
			   sheet = workbook.getSheet(j);
			   settings = sheet.getSettings();
			   createNewSheet(sheet);
			   r=5;
			   j++;  
			   r++;
			   second=false;
		   }
		   
	  		 if (mdt.getDash()==0 )
	  		  {
	  			   dash=0;
	 			   addLabel(sheet, 0, r, mdt.getParty_name(),dash);
				   addDouble(sheet,1, r, mdt.getCr_amt(),dash);
				   addDouble(sheet,2, r, mdt.getVamount(),dash);
				   r++;
			   }

	  		 if (mdt.getDash()==1)
	  		  {
	  			   dash=1;
	 			   addLabel(sheet, 0, r, mdt.getParty_name(),dash);
				   addDouble(sheet,1, r, mdt.getCr_amt(),dash);
				   addDouble(sheet,2, r, mdt.getVamount(),dash);
				   r++;
				   second=true;
	  		  }

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

		y=658;
		first=true;
		int pg=1;	
		int sno=1;
		sch='A';
		for(int j=0; j < psize; j++ )
		{
			  mdt = (RcpDto) mis.get(j);
			 
				if (first)
				{
					generateHeader(doc, cb);
					first=false;
				}  
				if(more)
				{
					doc.newPage();
					generateHeader(doc, cb);
					y=658;
					more=false;
				}

					if(mdt.getDash()==1 )
					{
					    
						cb.moveTo(15,y); // DESCRIPTION : Vertical lines
						cb.lineTo(602,y);
						y=y-11;
						createContent2(cb,17,y, String.format("%-10s",mdt.getParty_name()),PdfContentByte.ALIGN_LEFT);
						createContent2(cb,475,y, String.format("%10s",df.format(mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
						createContent2(cb,575,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
						y=y-11;

						cb.moveTo(15,y); // DESCRIPTION : Vertical lines
						cb.lineTo(602,y);
						cb.stroke();
						more=true;
					}
					if(mdt.getDash()==0  )
					{
 
						     
							createContent2(cb,17,y, String.format("%-10s",mdt.getParty_name()),PdfContentByte.ALIGN_LEFT);
							createContent2(cb,475,y, String.format("%10s",df.format(mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
							createContent2(cb,575,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);

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
		
		createHeadings1(cb,300,31,"Page - "+(pg++));

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
			createHeadings(cb,230,702, "Schedule "+(sch++)+"     "+mdt.getBank_name()+" As on Date "+edate);

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
public void checkLn(Document doc, PdfContentByte cb) {
	if (y < 55) {
		// printPageNumber(cb);
		// createContent2(cb, 17, 40,"EPO Continued on Page "+(++pg),PdfContentByte.ALIGN_LEFT);
		createContent2(cb, 17, 40, "Continued on ",PdfContentByte.ALIGN_LEFT);
		doc.newPage();

	}
}    
    

public void wordWrap(Document doc, PdfContentByte cb, String word, int x1,int yy) {
	int k = word.indexOf("\n");
	int j = 0;
	while (k > 0) {
		checkLn(doc, cb);
		createContent1(cb, x1, y, (word.substring(j, k)),PdfContentByte.ALIGN_LEFT);
		y = y - 11;
		j = k + 1;

		k = (word.substring(k + 1)).indexOf("\n");
		if (k < 0) {
			k = j;
			break;
		} else
			k = k + j;

	}
	if (word.length() > k) {
		checkLn(doc, cb);
		createContent1(cb, x1, y, (word.substring(j, (word.length()))),PdfContentByte.ALIGN_LEFT);
		//y = y - 11;
	}
	else
		 y= y + 11;
	

}


 
   

}
