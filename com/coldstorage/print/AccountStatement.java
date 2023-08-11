package com.coldstorage.print;
import java.awt.Desktop;
import java.io.File;
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
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.coldstorage.dao.AccountDAO;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.excel.WriteExcel;
import com.coldstorage.view.BaseClass;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.PrinterFactory;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class AccountStatement  extends WriteExcel
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
   String wtxt,sdate,edate,inputFile;
   int depo,div,bkcd,slno,year,opt;
   private String btnname,drvnm,printernm,brnm,divnm,code,party_name,filename; 
   ArrayList<?> mis; 
   SheetSettings settings; 
   private Date stdt,eddt;  
   HashMap prtMap=null;
   PartyDto pdto=null;
   WritableWorkbook workbook;
  public AccountStatement(String sdate,String edate,String code,String party_name,Integer depo,Integer div,Integer year,String btnnm,String drvnm,String printernm,String brnm,String divnm,String opt) 
  
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
    	this.code=code;
    	this.party_name=party_name;
    	this.opt=Integer.parseInt(opt);
    	prtMap=BaseClass.loginDt.getPrtmap();
    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mm-ss");
    	filename=divnm+"-LEDGER-"+sdf2.format(new Date());

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
 			
 			
			if (btnname.equalsIgnoreCase("Email"))
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
    	   stdt=sdf.parse(sdate);
    	   eddt=sdf.parse(edate);
    	   
    	   AccountDAO ms = new AccountDAO();
    	   
    	    
    		   mis =   (ArrayList<?>) ms.ledger(year,depo, div, stdt, eddt,Integer.parseInt(code),opt);
    	   
    		   
		   mdt=null;
		   
		   mdt = (RcpDto) mis.get(0);
		   

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
    			  pdto = (PartyDto) prtMap.get(mdt.getVac_code());
    			  if(pdto!=null)
    			  {
    				  party_name=pdto.getMac_name()+"-"+pdto.getMac_code();
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
		      if (mdt.getVnart1()!=null)
		      printer.printString(mdt.getVnart1(), r,25,prop);
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
	   workbook.createSheet(mdt.getVac_code(), 0);
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
	   sheet.mergeCells(0, 0, 6, 0);
	   // Write a few headers
	   addCaption(sheet, 0, 0, brnm,10);
	   
	   sheet.mergeCells(0, 1, 6, 1);
	   addCaption1(sheet, 0, 1, "STATMENT OF ACCOUNTS OF "+party_name,30);
	   sheet.mergeCells(0, 2, 6, 2);

   	   addCaption1(sheet, 0, 2, pdto.getMadd1(),30);
	   sheet.mergeCells(0, 3, 6, 3);
	   addCaption1(sheet, 0, 3, pdto.getMadd2(),30);
	   sheet.mergeCells(0, 4, 6, 4);
	   addCaption1(sheet, 0, 4, pdto.getMcity(),30);
	   sheet.mergeCells(0, 5, 6, 5);
	   addCaption1(sheet, 0, 5, pdto.getMemail(),30);
	   sheet.mergeCells(0, 6, 6, 6);
	   addCaption1(sheet, 0, 6, pdto.getMobile(),30);
	   sheet.mergeCells(0, 7, 6, 7);
	   addCaption1(sheet, 0, 7, "GST No. "+pdto.getGst_no(),30);

	   sheet.mergeCells(0, 8, 6, 8);
	   addCaption1(sheet, 0, 8, " FOR THE PERIOD FROM "+sdate+" - "+edate,30);

	   addCaption2(sheet, 0, 9, "DATE",12);
	   addCaption2(sheet, 1, 9, "VOU.NO",10);
	   addCaption2(sheet, 2, 9, "PARTICULARS",30);
	   addCaption2(sheet, 3, 9, "DEBIT",12);
	   addCaption2(sheet, 4, 9, "CREDIT",12);
	   addCaption2(sheet, 5, 9, "BALANCE",12);
	   addCaption2(sheet, 6, 9, "DR/CR",8);

	   settings.setHorizontalFreeze(2);
	   settings.setVerticalFreeze(10);

	   

}  


public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   boolean second=false;
	   col=0;
	   r=11;
	   int j=1;
	   int dash=0;
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
	    				  party_name=pdto.getMac_name()+"-"+pdto.getMac_code();
	    			  }
	    			  else
	    			  {
	    				  party_name="";
	    			  }
	    			  createHeader(sheet);
	    			  first=false;
	    			  
	    			  if(mdt.getDash()==0)
	    			  {
	    				  dash=1;
	    				  addLabel(sheet, 0, r, "",dash);
	    				  addLabel(sheet, 1, r, "",dash);
	    				  addLabel(sheet, 2, r, "Opening Balance",dash);
	    				  addDouble(sheet, 3, r, 0.00,dash);
	    				  addDouble(sheet, 4, r, 0.00,dash);
	    				  addDouble(sheet, 5, r, 0.00,dash);
	    				   addLabel(sheet, 6, r, mdt.getVdbcr(),dash);
	    				  r++;
	    			  }
	    	  } // end of first 
		   
		   
		   if(second)
		   {
			  
 			  pdto = (PartyDto) prtMap.get(mdt.getVac_code());
 			  
 			  if(pdto!=null)
 			  {
 				  party_name=pdto.getMac_name()+"-"+pdto.getMac_code();
 			  }
 			  else
 			  {
 				  party_name="";
 				  
 			  }
 				  workbook.createSheet(mdt.getVac_code(), j);
 	 			  sheet= workbook.getSheet(j);
 	 			  settings = sheet.getSettings();
 	 			  createHeader(sheet); 
 	 			  r=11;
 	 			  j++;
 				   second=false;
			   
	 
			   
			   if(mdt.getDash()==0)
 			  {
				   dash=1;
 				  addLabel(sheet, 0, r, "",dash);
 				  addLabel(sheet, 1, r, "",dash);
 				  addLabel(sheet, 2, r, "Opening Balance",dash);
 				  addDouble(sheet, 3, r, 0.00,dash);
 				  addDouble(sheet, 4, r, 0.00,dash);
 				  addDouble(sheet, 5, r, 0.00,dash);
 				   addLabel(sheet, 6, r, mdt.getVdbcr(),dash);
 			       r++;
 			  }
		   }

	  		 if (mdt.getDash()==1)
	  		  {
			   dash=1;
			   addLabel(sheet, 0, r, "",dash);
			   addLabel(sheet, 1, r, "",dash);
			   addLabel(sheet, 2, r, mdt.getVnart1(),dash);
			   addDouble(sheet, 3, r, mdt.getVamount(),dash);
			   addDouble(sheet, 4, r, mdt.getBill_amt(),dash);
			   addDouble(sheet, 5, r, mdt.getAdvance(),dash);
			   addLabel(sheet, 6, r, mdt.getVdbcr(),dash);
		  	   r++;

	  		  }

		   
	  		 if (mdt.getDash()==0)
	  		  {
			   dash=0;
			   if(mdt.getVou_date()!=null)
				   addLabel(sheet, 0, r, sdf.format(mdt.getVou_date()).toString(),dash);
			   else
				   addLabel(sheet, 0, r, "",dash);
			   addLabel(sheet, 1, r, mdt.getVou_lo(),dash);
			   addLabel(sheet, 2, r, mdt.getVnart1(),dash);
			   addDouble(sheet, 3, r, mdt.getVamount(),dash);
			   addDouble(sheet, 4, r, mdt.getBill_amt(),dash);
			   addDouble(sheet, 5, r, mdt.getAdvance(),dash);
			   addLabel(sheet, 6, r, mdt.getVdbcr(),dash);
		  	   r++;

	  		  }

	  		 if (mdt.getDash()==2)
	  		  {
			   dash=2;
			   addLabel(sheet, 0, r, "",dash);
			   addLabel(sheet, 1, r, "",dash);
			   addLabel(sheet, 2, r, mdt.getVnart1(),dash);
			   addDouble(sheet, 3, r, mdt.getVamount(),dash);
			   addDouble(sheet, 4, r, mdt.getBill_amt(),dash);
			   addDouble(sheet, 5, r, mdt.getAdvance(),dash);
			   addLabel(sheet, 6, r, mdt.getVdbcr(),dash);
			   second=true;
		  	   r++;
	  		  }



	   }


}


 
   
   

}
