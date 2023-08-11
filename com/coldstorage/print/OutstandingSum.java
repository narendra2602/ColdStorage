package com.coldstorage.print;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import com.coldstorage.view.BaseClass;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.PrinterFactory;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class OutstandingSum  extends WriteExcel
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
   String sdate,edate,inputFile,wtxt;
   int depo,div,bkcd,slno,year,opt;
   private String btnname,drvnm,printernm,brnm,divnm ,filename; 
   ArrayList<RcpDto> mis; 
   SheetSettings settings; 
   private Date stdt,eddt;  
   HashMap prtMap;
  public OutstandingSum(String sdate,String edate,Integer depo,Integer div,Integer year,String btnnm,String drvnm,String printernm,String brnm,String divnm,String opt) 
  
  {
    try 
    {
    	 
    	this.r=0;
    	this.i=0;
    	this.dash=110;
    	this.depo=depo;
    	this.div=div;
    	 
    	this.year=year;
    	this.brnm=brnm;
    	this.divnm=divnm;
    	this.btnname=btnnm;
		this.drvnm=drvnm;
		this.printernm=printernm;
     	this.sdate=sdate;
    	this.edate=edate;
    	 
    	this.opt=Integer.parseInt(opt);
    	prtMap=BaseClass.loginDt.getPrtmap();
    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mm-ss");

    	filename="OUTSUM-"+sdf2.format(new Date());

    	jbInit(); 
  
    	File file=null;

		if (Desktop.isDesktopSupported()) {
			if (btnname.equalsIgnoreCase("View"))
			{
				file=new File(drvnm+"\\outsum.TXT");
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
	 	        this.port=new FilePort(drvnm+"\\outsum.TXT");
	 	        this.printer=PrinterFactory.getPrinter("PLAIN");
	        } 	
 			
 			
			if (btnname.equalsIgnoreCase("Email"))
	        {
	 	        this.port=new FilePort(drvnm+"\\Outsum.TXT");
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
    	   
    		   mis =   (ArrayList<RcpDto>) ms.getOutstandingSum(year,depo, div, stdt, eddt,opt);
    		  // Collections.sort(mis,new LevelComparator());
    		   mdt=null;

    		   edate=sdf.format(new Date());			 
		   
    	        if(opt==1)
    	        	  wtxt="PARTY WISE";
    	          else if(opt==3)
    	        	  wtxt="AREA WISE";
    	          else if(opt==4)
    	        	  wtxt="TERRITORY WISE";
		   
		   

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

    	  
    	  if (mdt.getDash()==2)  	  
	      {

    		  printer.printString(mdt.getParty_name(), r,1,prop);
    		  printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,61,prop);
    		  printer.printString(String.format("%10s",df.format(mdt.getBill_amt())), r,73,prop);
    		  printer.printString(String.format("%10s",df.format(mdt.getCr_amt())), r,84,prop);
		      r++;
	      }

	      if (mdt.getDash()==3)  	  
	      {
	          printer.printHorizontalLine(r,1,dash); // dashLine -----------
	          r++;
    		  printer.printString(mdt.getVnart1(), r,1,prop);
    		  printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,61,prop);
    		  printer.printString(String.format("%10s",df.format(mdt.getBill_amt())), r,73,prop);
    		  printer.printString(String.format("%10s",df.format(mdt.getCr_amt())), r,84,prop);
      	      r++;
	          printer.printHorizontalLine(r,1,dash); // dashLine -----------
	          r++;
	          
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
			  printer.printString("OUTSTANDING SUMMERY ", r,30,propBold);
		      r++;
			  printer.printString("FROM "+sdate+" To "+edate, r,30,propBold);
		      r++;
		      printer.printHorizontalLine(r,1,dash); // dashLine -----------
	   	      r++;

	   	      printer.printString("NAME  ",r,1,prop);
		      printer.printString("   BELOW 30", r,60,prop);
		      printer.printString(" 30-60 DAYS", r,72,prop);
		      printer.printString("   ABOVE 60", r,83,prop);
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
	   workbook.createSheet("Outstanding", 0);
	   WritableSheet excelSheet = workbook.getSheet(0);
	   settings = excelSheet.getSettings();
	   createLabel(excelSheet);
	   createContent(excelSheet);

	   workbook.write();
	   workbook.close();
}


public void createHeader(WritableSheet sheet)
		   throws WriteException {
	    int col=7;
		   if (opt==3 || opt==4)
			   col=5;
	   //Merge col[0-11] and row[0]
	   sheet.mergeCells(0, 0, col, 0);
	   // Write a few headers
	   addCaption(sheet, 0, 0, brnm,10);
	   
	   sheet.mergeCells(0, 1, col, 1);
		   addCaption1(sheet, 0, 1, wtxt+" OUTSTANDING SUMMERY AS ON  - "+edate,30);
	   

	   if (opt==3 || opt==4)
	   {
		   addCaption2(sheet, 0, 3, "Name",50);
		   addCaption2(sheet, 1, 3, "30 Days",12);
		   addCaption2(sheet, 2, 3, "31-60 Days",12);
		   addCaption2(sheet, 3, 3, "61-120 Days",12);
		   addCaption2(sheet, 4, 3, "Above 120 Days",12);
		   addCaption2(sheet, 5, 3, "Total",12);
	   }
	   else
	   {
		   addCaption2(sheet, 0, 3, "Name",40);
		   addCaption2(sheet, 1, 3, "City",20);
		   addCaption2(sheet, 2, 3, "Mobile",30);
		   addCaption2(sheet, 3, 3, "30 Days",12);
		   addCaption2(sheet, 4, 3, "31-60 Days",12);
		   addCaption2(sheet, 5, 3, "61-120 Days",12);
		   addCaption2(sheet, 6, 3, "Above 120 Days",12);
		   addCaption2(sheet, 7, 3, "Total",12);
		   addCaption2(sheet, 8, 3, "Last Date",12);
	   }   
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
	   int size=mis.size();
	   String party_name,pcity;
	   for (i=0;i<size;i++)
	   {
		   mdt = (RcpDto) mis.get(i);
		   
		  
		   
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
		   }
		   
		   
	  		 if (mdt.getDash()==2)
	  		  {
			       dash=0;
				   if (opt==3 || opt==4)
				   {   
			   	   addLabel(sheet, 0, r, mdt.getParty_name(),dash);
				   addDouble(sheet,1, r, mdt.getDiscount(),dash);
				   addDouble(sheet,2, r, mdt.getVamount(),dash);
				   addDouble(sheet,3, r, mdt.getBill_amt(),dash);
				   addDouble(sheet,4, r, mdt.getCr_amt(),dash);
				   addDouble(sheet,5, r, (mdt.getDiscount()+mdt.getVamount()+mdt.getBill_amt()+mdt.getCr_amt()),dash);
				   }
				   else
				   {   
						 party_name=mdt.getParty_name().split(",")[0];
						 //pcity=mdt.getParty_name().split(",")[1];
						 pcity="";

					   addLabel(sheet, 0, r, party_name,dash);
				   	   addLabel(sheet, 1, r, mdt.getCity(),dash);  
				   	   addLabel(sheet, 2, r, mdt.getPono(),dash);
					   addDouble(sheet,3, r, mdt.getDiscount(),dash);
					   addDouble(sheet,4, r, mdt.getVamount(),dash);
					   addDouble(sheet,5, r, mdt.getBill_amt(),dash);
					   addDouble(sheet,6, r, mdt.getCr_amt(),dash);
					   addDouble(sheet,7, r, (mdt.getDiscount()+mdt.getVamount()+mdt.getBill_amt()+mdt.getCr_amt()),dash);
					   if(mdt.getVou_date()!=null)
						   addLabel(sheet, 8, r, sdf.format(mdt.getVou_date()),dash);  
					   }
					   
	  		  }

	  		 if (mdt.getDash()==3)
	  		  {
			   dash=2;
			   if (opt==3 || opt==4)
			   {   
				   addLabel(sheet, 0, r, mdt.getVnart1(),dash);
				   addDouble(sheet,1, r, mdt.getDiscount(),dash);
				   addDouble(sheet,2, r, mdt.getVamount(),dash);
				   addDouble(sheet,3, r, mdt.getBill_amt(),dash);
				   addDouble(sheet,4, r, mdt.getCr_amt(),dash);
				   addDouble(sheet,5, r, (mdt.getDiscount()+mdt.getVamount()+mdt.getBill_amt()+mdt.getCr_amt()),dash);
			   }
			   else
			   {
				   addLabel(sheet, 0, r, "",dash);
				   addLabel(sheet, 1, r, "",dash);
				   addLabel(sheet, 2, r, mdt.getVnart1(),dash);
				   addDouble(sheet,3, r, mdt.getDiscount(),dash);
				   addDouble(sheet,4, r, mdt.getVamount(),dash);
				   addDouble(sheet,5, r, mdt.getBill_amt(),dash);
				   addDouble(sheet,6, r, mdt.getCr_amt(),dash);
				   addDouble(sheet,7, r, (mdt.getDiscount()+mdt.getVamount()+mdt.getBill_amt()+mdt.getCr_amt()),dash);
				   
			   }
	  		  }


	  		 r++;

	   }

	   

}
public class LevelComparator implements Comparator<RcpDto> {
	public int compare(RcpDto p1, RcpDto p2) {
            if(p1.getDash()==3 || p2.getDash()==3)
            	return 0;
            else 
                return ((p1.getDiscount()+p1.getVamount()+p1.getBill_amt()+p1.getCr_amt())>(p2.getDiscount()+p2.getVamount()+p2.getBill_amt()+p2.getCr_amt())) ? -1 : ((p1.getDiscount()+p1.getVamount()+p1.getBill_amt()+p1.getCr_amt())<(p2.getDiscount()+p2.getVamount()+p2.getBill_amt()+p2.getCr_amt())) ? 1 : 0;
	}
}
   

}
