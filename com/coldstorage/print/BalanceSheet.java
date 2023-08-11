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


public class BalanceSheet  extends WriteExcel
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
   WritableWorkbook workbook;
   SheetSettings settings; 
   double ptot=0.00;
   String lname[];
   String rname[];
   double lamt[];
   double ramt[];
  public BalanceSheet(Integer year,Integer depo,Integer div, String sdate ,String edate ,String btnnm,String drvnm,String printernm,String brnm,String divnm,Integer doc_tp) 
  
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
    	filename="BALANCESHEET-"+sdf2.format(new Date());

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
    	   sdf1 = new SimpleDateFormat("dd/MM/yy");
    	   stdt=sdf.parse(sdate);
    	   eddt=sdf.parse(edate);

    	   
    	   AccountDAO ms = new AccountDAO();
		   mis =   (ArrayList<?>) ms.getBalanceSheet(year,div, depo, stdt,eddt);
		   mdt=null;
		   int size=mis.size();
		   lname = new String[size];
		   rname = new String[size];
		   lamt = new double[size];
		   ramt = new double[size];
		   
		   
		   

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

	   workbook = Workbook.createWorkbook(file, wbSettings);
	   workbook.createSheet("Trading-PL", 0);
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
		addCaption1(sheet, 0, 1, "Trading & Profit and Loss Account ",10);
		
		sheet.mergeCells(0, 2, 3, 2);
		addCaption1(sheet, 0, 2, "For the year ended "+edate,10);


	   addCaption2(sheet, 0, 3, "Particulars",40);
	   addCaption2(sheet, 1, 3, "Amount",18);
	   addCaption2(sheet, 2, 3, "Particulars",40);
	   addCaption2(sheet, 3, 3, "Amount",18);
	   
	  // settings.setHorizontalFreeze(4);
	   settings.setVerticalFreeze(4);

	   

}  
public void createNewSheet(WritableSheet sheet)
		   throws WriteException {

	

	   //Merge col[0-11] and row[0]

	   sheet.mergeCells(0, 0, 3, 0);
	   addCaption(sheet, 0, 0, brnm,10);

		sheet.mergeCells(0, 1, 3, 1);
		addCaption1(sheet, 0, 1, "Balance Sheet As on  "+edate,10);


	   addCaption2(sheet, 0, 3, "Particulars",40);
	   addCaption2(sheet, 1, 3, "Amount",18);
	   addCaption2(sheet, 2, 3, "Particulars",40);
	   addCaption2(sheet, 3, 3, "Amount",18);
	   
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
	   String lhead=null;
	   String rhead=null;
	   boolean second=true;
	   char sch='A';
	   int j=1;
	   int lcol=0;
	   int rcol=0;
	   int count=0;
	   for (i=0;i<size;i++)
	   {
		   mdt = (RcpDto) mis.get(i);
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
			   lhead=mdt.getBank_acno();
			   rhead=mdt.getBank_ifsc();
		   }

		     if(mdt.getDash()==3 && second)
		     {		dash=0;
		    	   for(int w=0;w<count;w++)
		    	   {
		    		   if(lamt[w]==ramt[w])
		    			   dash=6;
		    		   else
		    			   dash=0;
		    		   
		    		   addLabel(sheet, 0, r, lname[w],dash);
		    		   if(lamt[w]>0)
		    			   addDouble(sheet,1, r, lamt[w],dash);
		    		   else
		    			   addLabel(sheet, 1, r, "",dash);
		    		   addLabel(sheet, 2, r, rname[w],dash);
		    		   
		    		   if(ramt[w]>0)
		    			   addDouble(sheet,3, r, ramt[w],dash);
		    		   else
		    			   addLabel(sheet, 3, r, "",dash);

					   
					   r++;
		    	   }
		    	 
		    	   	count=0;
		    	   	lcol=0;
		    	   	rcol=0;
		    	   	lname = new String[size];
		    	   	rname = new String[size];
		    	   	lamt = new double[size];
		    	   	ramt = new double[size];
					workbook.createSheet("BalanceSheet",j);
					sheet = workbook.getSheet(j);
				    settings = sheet.getSettings();
				    createNewSheet(sheet);
					r=4;
					j++;  
					r++;
					second=false;
		     }
		   
	  		 if (mdt.getDash()==0 || mdt.getDash()==3)
	  		  {
	  			 
	  			 
			   if(mdt.getBank_acno()!=null && !mdt.getBank_acno().equals(lhead))
			   {

/*				   if(mdt.getCr_amt()>0)
				   {
					   lname[lcol]=mdt.getParty_name();
					   lamt[lcol]=mdt.getCr_amt();
					   lcol++;
				   }
				   if(mdt.getVamount()>0)
				   {
					   rname[rcol]=mdt.getBank_name();
					   ramt[rcol]=mdt.getVamount();
					   rcol++;
				   }
*/
	
				   
				   lname[lcol]=mdt.getBank_acno();
				   lamt[lcol]=0.00;
				   lcol++;
				   count++;

/*				   addCaption3(sheet, 0, r, mdt.getBank_acno(),35,2);
				   addLabel(sheet, 1, r, "",0);
				   addLabel(sheet, 2, r, "",0);
				   addLabel(sheet, 3, r, "",0);
				   r++;
*/				   
				   
				   if(mdt.getDoc_type()==1)
				   {

					   
					   lname[lcol]="(As Per Schedule "+sch+")";
					   lamt[lcol]=0.00;
					   lcol++;
					   count++;

/*					   addCaption3(sheet, 0, r, "(As Per Schedule "+sch+")",35,2);
					   addLabel(sheet, 1, r, "",0);
					   addLabel(sheet, 2, r, "",0);
					   addLabel(sheet, 3, r, "",0);
					   r++;
*/					   
					   sch++;
				   }
					   
			   }
			   if(mdt.getBank_ifsc()!=null && !mdt.getBank_ifsc().equals(rhead))
			   {
				   
				   rname[rcol]=mdt.getBank_ifsc();
				   ramt[rcol]=0.00;
				   rcol++;
				   count++;

				   
/*				   addLabel(sheet, 0, r, "",0);
				   addLabel(sheet, 1, r, "",0);
				   addCaption3(sheet, 2, r, mdt.getBank_ifsc(),35,2);
				   addLabel(sheet, 3, r, "",0);
				   r++;
*/				   
				   if(mdt.getDoc_type()==1)
				   {
/*					   addLabel(sheet, 0, r, "",0);
					   addLabel(sheet, 1, r, "",0);
					   addCaption3(sheet, 2, r, "(As Per Schedule "+sch+")",35,2);
					   addLabel(sheet, 3, r, "",0);
					   r++;
*/					   
					   rname[rcol]="(As Per Schedule "+sch+")";
					   ramt[rcol]=0.00;
					   rcol++;
					   count++;
	   
					   sch++;
				   }
			   }
	  		   dash=0;
	  		   
/*			   addLabel(sheet, 0, r, mdt.getParty_name(),dash);
			   addDouble(sheet,1, r, mdt.getCr_amt(),dash);
			   addLabel(sheet, 2, r, mdt.getBank_name(),dash);
			   addDouble(sheet,3, r, mdt.getVamount(),dash);
*/			   
			  
			   if(mdt.getCr_amt()>0)
			   {
				   lname[lcol]=mdt.getParty_name();
				   lamt[lcol]=mdt.getCr_amt();
				   lcol++;
				   count++;

			   }
			   if(mdt.getVamount()>0)
			   {
				   rname[rcol]=mdt.getBank_name();
				   ramt[rcol]=mdt.getVamount();
				   rcol++;
				   //count++;

			   }

			   
			   lhead=mdt.getBank_acno();
			   rhead=mdt.getBank_ifsc();

	  		  }
	  			 
	  		 if (mdt.getDash()==1)
	  		  {
				   dash=6;
				   
/*				   addLabel(sheet, 0, r, mdt.getParty_name(),dash);
				   addDouble(sheet,1, r, mdt.getCr_amt(),dash);
				   addLabel(sheet, 2, r, mdt.getBank_name(),dash);
				   addDouble(sheet,3, r, mdt.getVamount(),dash);
*/				   
				   
				   if(lcol>rcol)
					   rcol=lcol;
				   else
					   lcol=rcol;
				   
				   lname[lcol]=mdt.getParty_name();
				   lamt[lcol]=mdt.getCr_amt();
				   lcol++;
				   count++;


				   rname[rcol]=mdt.getBank_name();
				   ramt[rcol]=mdt.getVamount();
				   rcol++;
				  // count++;

	  		  }
	  		 //r++;

	   }


	   for(int w=0;w<count;w++)
	   {
		   if((lname[w]!=null && lname[w].equals("Total")) )
			   dash=6;
		   else
			   dash=0;
		   //if(lamt[w]==0)
			   
		   if(lamt[w]==0)
			   addCaption3(sheet, 0, r, lname[w],35,2);
		   else
			   addLabel(sheet, 0, r, lname[w],dash);
		   if(lamt[w]>0)
			   addDouble(sheet,1, r, lamt[w],dash);
		   else
			   addLabel(sheet, 1, r, "",dash);

		   if(ramt[w]==0)
			   addCaption3(sheet, 2, r, rname[w],35,2);
		   else
			   addLabel(sheet, 2, r, rname[w],dash);
		   
		   if(ramt[w]>0)
			   addDouble(sheet,3, r, ramt[w],dash);
		   else
			   addLabel(sheet, 3, r, "",dash);
		   
		   r++;
		   if(dash==6)
			   break;
	   }

	   
}


 
   
   

}
