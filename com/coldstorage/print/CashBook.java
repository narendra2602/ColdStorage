package com.coldstorage.print;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.excel.WriteExcel;
import com.coldstorage.util.WordUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.PrinterFactory;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class CashBook  extends WriteExcel
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

  
   RcpDto mdt;
   RcpDto mdt1;
   String wtxt,sdate,edate,inputFile;
   int depo,div,bkcd,slno,year,opt,doc_tp;
   private String btnname,drvnm,printernm,brnm,divnm,code,party_name; 
   ArrayList<?> mis; 
   SheetSettings settings; 
   private Date stdt,eddt;  
   HashMap prtMap=null;
   PartyDto pdto=null;
   WritableWorkbook workbook;
   
   
   Document doc;
   PdfWriter docWriter;
   PdfContentByte cb;
   String name,word;
   int y,pg;
   String flnm;
  public CashBook(Integer year,Integer depo,Integer div, String sdate ,String edate ,String btnnm,String drvnm,String printernm,String brnm,String divnm,Integer doc_tp) 

  {
    try 
    {
    	 
    	this.r=0;
    	this.i=0;
    	this.dash=110;
    	this.depo=depo;
    	this.div=div;
    	this.bkcd=bkcd;
    	this.year=year;
    	this.brnm=brnm;
    	this.divnm=divnm;
    	this.btnname=btnnm;
		this.drvnm=drvnm;
		this.printernm=printernm;
     	this.sdate=sdate;
    	this.edate=edate;
    	this.doc_tp=doc_tp;
    	
    	name="BankBook";
		if(doc_tp==10)
			name="CashBook";
		else if(doc_tp==40)
			name="SaleBook";
		else if(doc_tp==60)
			name="PurBook";
    	df = new DecimalFormat("0.00");
    	
    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mm-ssa");
    	flnm=name+"-"+sdf2.format(new Date());
    	jbInit();
  
    	File file=null;

		if (Desktop.isDesktopSupported()) {
			if (btnname.equalsIgnoreCase("View"))
			{
				file=new File(drvnm+"\\"+flnm+".pdf");
				Desktop.getDesktop().open(file);
				
			}
			if (btnname.equalsIgnoreCase("Excel"))
  			{
  				file=new File(drvnm+"\\"+flnm+".xls");
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

 			  

 			if (btnname.equalsIgnoreCase("View1"))
	        {
	 	        this.port=new FilePort(drvnm+"\\CASHBOOK.TXT");
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
    	   stdt=sdf.parse(sdate);
    	   eddt=sdf.parse(edate);
    	   
    	   AccountDAO ms = new AccountDAO();
    	   
    	   		if(doc_tp==40 || doc_tp==60)
    	    		   mis =   (ArrayList<?>) ms.getSaleBook(year,depo, div, stdt, eddt,doc_tp);
    	   		else
    	   			   mis =   (ArrayList<?>) ms.getCashBook(year,depo, div, stdt, eddt,doc_tp);
    	   
    		   
		   mdt=null;
		   
		   mdt = (RcpDto) mis.get(0);
		   

			  if (btnname.equalsIgnoreCase("Excel"))
			  {
				  createExcel();
			  }
			  else
			  {
				  createPDF();
//				  detail();   // for printing
			  }

		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	
	  }
	  /////// print file option //////////////  
 public void detail() {

	   try {		    

		   
		   
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
    			  pdto = (PartyDto) prtMap.get(mdt.getVac_code());
    			  if(pdto!=null)
    			  {
    				  party_name=pdto.getMac_name();
    			  }
    			  else
    			  {
    				  party_name="";
    			  }
    			  
    				  head_Para();
    				  first=false;
    			  if(mdt.getDash()==0)
    			  {
    				  printer.printString("Opening Balance", r,25,prop);
    				  printer.printString(String.format("%10s",df.format(0.00)), r,72,prop);
    			      printer.printString(String.format("%10s",df.format(0.00)), r,85,prop);
    			      printer.printString(String.format("%10s",df.format(0.00)), r,100,prop);
    			      r++;
    			  }
    	  }
	      if (mdt.getDash()==1)  	  
	      {
		      printer.printString(mdt.getVnart1(), r,25,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,72,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getBill_amt())), r,85,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getAdvance()))+" "+mdt.getVdbcr(), r,100,prop);
      	      r++;
	      }

	      
    	  if (mdt.getDash()==0)  	  
	      {
		      if(mdt.getVou_date()!=null)
		    	  printer.printString(sdf.format(mdt.getVou_date()),r,1,prop);
		      printer.printString("  "+mdt.getVou_lo(), r,11,prop);
		      if (mdt.getVnart1()!=null && mdt.getVnart1().trim().length()>1)
		    	  printer.printString(mdt.getVnart1(), r,25,prop);
		      else
		      {
		    	  printer.printString(mdt.getVnart2(), r,25,prop);
		    	  mdt.setVnart2(null);
		      }
//		      printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,56,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,72,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getBill_amt())), r,85,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getAdvance()))+" "+mdt.getVdbcr(), r,100,prop);
      	      r++;
      	      if (mdt.getVnart2()!=null)
      	      {
    		      printer.printString(mdt.getVnart2(), r,25,prop);
          	      r++;
    		      
      	      }
	      }

	      if (mdt.getDash()==2)  	  
	      {
	          printer.printHorizontalLine(r,1,dash); // dashLine -----------
	          r++;
		      printer.printString(mdt.getVnart1(), r,25,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,72,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getBill_amt())), r,85,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getAdvance()))+" "+mdt.getVdbcr(), r,100,prop);
      	      r++;
	          printer.printHorizontalLine(r,1,dash); // dashLine -----------
	          r++;
	          first=true;
              printer.newPage();
              r=0;

	          
	      }
	      
	   	  if (r>=55)
    	  {
    		  
              printer.printHorizontalLine(r,1,dash); // dashLine -----------
              r++;
              printer.newPage();
              r=0;
              head_Para();
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
			  printer.printString("STATMENT OF ACCOUNTS OF "+party_name, r,30,propBold);
		      r++;
			  printer.printString(pdto.getMadd1(), r,30,propBold);
		      r++;
			  printer.printString(pdto.getMadd2(), r,30,propBold);
		      r++;
			  printer.printString(pdto.getMcity(), r,30,propBold);
		      r++;
			  printer.printString(pdto.getMemail(), r,30,propBold);
		      r++;
			  printer.printString(pdto.getMobile(), r,30,propBold);
		      r++;
			  printer.printString("GST "+pdto.getGst_no(), r,72,propBold);
		      r++;

		      printer.printString(" FOR THE PERIOD FROM "+sdate+" To "+edate, r,30,propBold);
		      r++;
		      printer.printHorizontalLine(r,1,dash); // dashLine -----------
	   	      r++;
		      printer.printString("    DATE",r,1,prop);
		      printer.printString("  VOU.NO", r,11,prop);
		      printer.printString("PARTICULARS", r,25,prop);
//	  		  printer.printString("     GROSS",r,56,prop);
		      printer.printString("    DEBIT ", r,72,prop);
		      printer.printString("   CREDIT ", r,85,prop);
		      printer.printString("   BALANCE", r,100,prop);
		      r++;
//		      printer.printString("      DR  ", r,56,prop);
//      printer.printString("      DR  ", r,72,prop);
// 		  printer.printString("      CR  ",r,85,prop);
//      printer.printString("    AMOUNT", r,100,prop);
//      r++;
		      printer.printHorizontalLine(r,1,dash); // dashLine -----------
		      r++;


   }
   

//////excel file generation report ////////   
   
public void createExcel() throws WriteException, IOException {

	   setOutputFile(drvnm+"\\"+flnm+".xls");
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
	   workbook.createSheet(name, 0);
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
	   sheet.mergeCells(0, 0, 6, 0);
	   // Write a few headers
	   addCaption(sheet, 0, 0, brnm,10);
	   
	   sheet.mergeCells(0, 1, 6, 1);
	   if(doc_tp==10)
		   addCaption1(sheet, 0, 1, " Cash Book From "+sdate+" - "+edate,30);
	   else if(doc_tp==30)
		   addCaption1(sheet, 0, 1, " Journal Book From "+sdate+" - "+edate,30);
	   else if(doc_tp==40)
		   addCaption1(sheet, 0, 1, " Sale Book From "+sdate+" - "+edate,30);
	   else if(doc_tp==60)
		   addCaption1(sheet, 0, 1, " Purchase Book From "+sdate+" - "+edate,30);
	   else 
		   addCaption1(sheet, 0, 1, " Bank Book "+divnm+" From "+sdate+" - "+edate,30);


	  if(doc_tp==40 || doc_tp==60)
	  {
		   addCaption2(sheet, 0, 2, "DATE",10);
		   addCaption2(sheet, 1, 2, "BIll NO",15);
		   addCaption2(sheet, 2, 2, "PARTY NAME ",40);
		   addCaption2(sheet, 3, 2, "CITY ",20);
		   addCaption2(sheet, 4, 2, "AMOUNT",15);

	  }
	  else
	  {
		  addCaption2(sheet, 0, 2, "DATE",10);
		  addCaption2(sheet, 1, 2, "VOU.NO",8);
		  addCaption2(sheet, 2, 2, "A/C Head",30);
		  addCaption2(sheet, 3, 2, "PARTICULARS",30);
		  addCaption2(sheet, 4, 2, "DEBIT",12);
		  addCaption2(sheet, 5, 2, "CREDIT",12);
		  addCaption2(sheet, 6, 2, "BALANCE",12);
	  }
	   

//	   settings.setHorizontalFreeze(2);
	   settings.setVerticalFreeze(2);

	   

}  


public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   boolean second=false;
	   col=0;
	   r=3;
	   int j=1;
	   int dash=0;
	   // detail header
	   int size=mis.size();
	  
	   for (i=0;i<size;i++)
	   {
		   mdt = (RcpDto) mis.get(i);
		   
		   if(first)
	    	  {
	    			  createHeader(sheet);
	    			  first=false;
	    	  } // end of first 
		   
	  		 if (mdt.getDash()==3)
	  		  {
			   dash=0;
			   addLabel(sheet, 0, r, mdt.getVnart1()+sdf.format(mdt.getVou_date()),dash);
			   addLabel(sheet, 1, r, "",dash);
			   addLabel(sheet, 2, r, "",dash);
			   addLabel(sheet, 3, r, "",dash);
			   addLabel(sheet, 4, r, "",dash);
	  		  }

	  		 if (mdt.getDash()==1)
	  		  {
			   dash=1;
			   if(doc_tp==40 || doc_tp==60)
			   {
				   addLabel(sheet, 0, r, "",dash);
				   addLabel(sheet, 1, r, "",dash);
				   addLabel(sheet, 2, r, "",dash);
				   addLabel(sheet, 3, r, mdt.getParty_name(),dash);
				   addDouble(sheet,4, r, mdt.getVamount(),dash);
				   
			   }
			   else
			   {
				   addLabel(sheet, 0, r, "",dash);
				   addLabel(sheet, 1, r, "",dash);
				   addLabel(sheet, 2, r, "",dash);
				   addLabel(sheet, 3, r, mdt.getVnart2(),dash);
				   addDouble(sheet, 4, r, mdt.getVamount(),dash);
				   addDouble(sheet, 5, r, mdt.getBill_amt(),dash);
				   addLabel1(sheet, 6, r, df.format(mdt.getAdvance())+" "+mdt.getVdbcr(),dash);
				   //addDouble(sheet, 6, r, mdt.getAdvance(),dash);
				   //addLabel(sheet, 7, r, mdt.getVdbcr(),dash);
			   }
	  		  }


	  		 
/*	  		 if (mdt.getDash()==2)
	  		  {
			   dash=1;
			   addLabel(sheet, 0, r, "",dash);
			   addLabel(sheet, 1, r, "",dash);
			   addLabel(sheet, 2, r, mdt.getVnart1()+sdf.format(mdt.getVou_date()),dash);
			   addDouble(sheet, 3, r, mdt.getVamount(),dash);
			   addDouble(sheet, 4, r, mdt.getBill_amt(),dash);
	  		  }
*/
		   
	  		 if (mdt.getDash()==0)
	  		  {
			   dash=0;
			   addLabel(sheet,  0, r, sdf.format(mdt.getVou_date()),dash);
			   if(doc_tp==60)
				   addLabel(sheet, 1, r, mdt.getBill_no(),dash);
			   else if(mdt.getVou_no()>0)
				   addNumber(sheet, 1, r,mdt.getVou_no(),dash);
			   else
				   addLabel(sheet, 1, r, "",dash);
			   addLabel(sheet,  2, r, mdt.getParty_name(),dash);
			   if(doc_tp==40 || doc_tp==60)
			   {
				   addLabel(sheet,  3, r, mdt.getCity(),dash);
				   addDouble(sheet, 4, r, mdt.getVamount(),dash);
			   }
			   else
			   {
				   addLabel(sheet,  3, r, mdt.getVnart2().trim(),dash);
				   addDouble(sheet, 4, r, mdt.getVamount(),dash);
				   addDouble(sheet, 5, r, mdt.getBill_amt(),dash);
				   addLabel1(sheet, 6, r, df.format(mdt.getAdvance())+" "+mdt.getVdbcr(),dash);
   //			   addDouble(sheet, 6, r, mdt.getAdvance(),dash);
   //			   addLabel(sheet, 7, r, mdt.getVdbcr(),dash);
			   }

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

			y=658;
			first=true;
			
		int sno=1;
		System.out.println("SIZE OF LIST  IS "+psize+" DOCTO IUS "+doc_tp);
		for(int j=0; j < psize; j++ )
		{
			  mdt = (RcpDto) mis.get(j);
			 
				if (first)
				{
					generateHeader(doc, cb);
					first=false;
				}  

				if(doc_tp==40 || doc_tp==60)  // sale book
				{
					if(mdt.getDash()==0)
					{
						word = WordUtils.wrap(mdt.getParty_name()+", "+mdt.getCity(), 40); // change on
						createContent1(cb,50,y, String.format("%-10s",sdf.format(mdt.getVou_date())),PdfContentByte.ALIGN_LEFT);
						createContent1(cb,130,y, String.format("%-10s",doc_tp==40?mdt.getVou_no():mdt.getBill_no()),PdfContentByte.ALIGN_LEFT);
						
						//createContent3(cb,218,y, String.format("%-10s",mdt.getParty_name()),PdfContentByte.ALIGN_LEFT);
						createContent1(cb,530,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
						wordWrap(doc, cb, word, 218, y);
					}
					else
					{   
						cb.moveTo(15,y); // DESCRIPTION : Vertical lines
						cb.lineTo(602,y);
						cb.stroke();
						y=y-11;
						createContent1(cb,218,y, String.format("%-10s",mdt.getParty_name()),PdfContentByte.ALIGN_LEFT);
						createContent1(cb,530,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
					}
						
				}
				else
				{
					if(mdt.getDash()==3 )
					{
						createContent2(cb,17,y, String.format("%-10s",mdt.getVnart1()),PdfContentByte.ALIGN_LEFT);
						createContent2(cb,62,y, String.format("%-10s",sdf.format(mdt.getVou_date())),PdfContentByte.ALIGN_LEFT);

					}
					if(mdt.getDash()==1 )
					{
						cb.moveTo(15,y); // DESCRIPTION : Vertical lines
						cb.lineTo(602,y);
						cb.stroke();
						y=y-11;

						createContent2(cb,218,y, String.format("%-10s",mdt.getVnart1()),PdfContentByte.ALIGN_LEFT);
						createContent2(cb,445,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
						createContent2(cb,510,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);
						createContent2(cb,575,y, String.format("%10s",df.format(mdt.getAdvance()))+mdt.getVdbcr(),PdfContentByte.ALIGN_RIGHT);

					}
					if(mdt.getDash()==2 )
					{
						createContent2(cb,218,y, String.format("%-10s",mdt.getVnart1()+sdf.format(mdt.getVou_date())),PdfContentByte.ALIGN_LEFT);
						createContent2(cb,510,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
						createContent2(cb,575,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);

					}
					if(mdt.getDash()==0  )
					{
 
						     
						if(mdt.getVou_no()>0)
						{
							word = WordUtils.wrap(mdt.getVnart2(), 30); // change on
							//System.out.println("word is "+word);
							createContent1(cb,17,y, sdf.format(mdt.getVou_date()),PdfContentByte.ALIGN_LEFT);
							createContent1(cb,70,y, String.format("%-10s",mdt.getVou_no()),PdfContentByte.ALIGN_LEFT);
							createContent1(cb,112,y, String.format("%-10s",mdt.getParty_name()),PdfContentByte.ALIGN_LEFT);
							//createContent1(cb,240,y, String.format("%-10s",mdt.getVnart2()),PdfContentByte.ALIGN_LEFT);
							//createContent1(cb,240,y, word,PdfContentByte.ALIGN_LEFT);

							createContent1(cb,445,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
							createContent1(cb,510,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);
							createContent1(cb,575,y, String.format("%10s",df.format(mdt.getAdvance()))+mdt.getVdbcr(),PdfContentByte.ALIGN_RIGHT);
							wordWrap(doc, cb, word, 240, y);

							
/*							if((mdt.getVnart2().length()>0))
							{
			 					y=y-11;
								createContent1(cb,218,y, String.format("%-10s",mdt.getVnart2()),PdfContentByte.ALIGN_LEFT);
							}	
*/						}
						else
						{
							createContent2(cb,218,y, String.format("%-10s",mdt.getParty_name()+" on Date "+sdf.format(mdt.getVou_date())),PdfContentByte.ALIGN_LEFT);
							createContent2(cb,510,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
							createContent2(cb,575,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);

						}
					}
				}
				
				y=y-11;
  
/*				if (y < 50)
				{
					doc.newPage();
					generateHeader(doc, cb);
					y=658;
				}
*/
			
               more=true; // only for pdf
               

			}// end of outer for loop 

		cb.moveTo(15,y); // DESCRIPTION : Vertical lines
		cb.lineTo(602,y);
		cb.stroke();

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
			if(doc_tp==10)
				createHeadings(cb,260,702,"Cash Book From " +sdate+" To "+edate);
			else if(doc_tp==30)
				createHeadings(cb,210,702,"Journal Book From " +sdate+" To "+edate);
			else if(doc_tp==40 )
				createHeadings(cb,210,702,"Sale Book From " +sdate+" To "+edate);
			else if(doc_tp==60 )
				createHeadings(cb,210,702,"Purchase Book From " +sdate+" To "+edate);
			else if(doc_tp==88)
				createHeadings(cb,210,702,"Purchase Return Book From " +sdate+" To "+edate);
			else if(doc_tp==78)
				createHeadings(cb,210,702,"Sale Return Book From " +sdate+" To "+edate);
			else
				createHeadings(cb,150,702,"Bank Book "+divnm+" From " +sdate+" To "+edate);


			if(doc_tp==40 || doc_tp==60 )
			{
				createHeadings1(cb,50,678,"Date.");
				createHeadings1(cb,130,678,"Bill No");
				createHeadings1(cb,218,678,"Party Name");
				createHeadings1(cb,490,678,"   Amount");
				
			}
			else
			{
//				createHeadings(cb,17,678,(doc_tp==30?"Vou No.":"Sno."));
				createHeadings(cb,17,678,"Vou Date");
				createHeadings(cb,62,678,"Vou No.");
				createHeadings(cb,112,678,"Account Head");
				createHeadings(cb,240,678,"Narration");
				createHeadings(cb,425,678,"    Debit");
				createHeadings(cb,485,678,"   Credit");
				createHeadings(cb,543,678,"  Balance");
			}
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

		
		
		String path = drvnm+"\\"+flnm+".pdf";
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

public void LinePrint(PdfContentByte cb,int start,int end )
{
	cb.moveTo(start,y);  // horizontal lines 
	cb.lineTo(end,y);
	cb.stroke();
}

public void checkLn(Document doc, PdfContentByte cb) {
	if (y < 58) {
		// printPageNumber(cb);
		// createContent2(cb, 17, 40,"EPO Continued on Page "+(++pg),PdfContentByte.ALIGN_LEFT);
		y=658;
		if(doc_tp==40 || doc_tp==60 )
		{
  			 LinePrint(cb, 43, 605);
			 createContent1(cb,300,30, "Page # "+(++pg),PdfContentByte.ALIGN_LEFT);

		}
		else
			createContent2(cb, 17, 40, "Continued on ",PdfContentByte.ALIGN_LEFT);
		doc.newPage();		
		generateHeader(doc, cb);
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
		createContent1(cb, x1, y, (word.substring(j, (word.length()))),PdfContentByte.ALIGN_LEFT);
		checkLn(doc, cb);
		//y = y - 11;
	}
	else
		 y= y + 11;
	

}


   

}
