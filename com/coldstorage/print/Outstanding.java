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
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.excel.WriteExcel;
import com.coldstorage.view.BaseClass;
import com.java4less.textprinter.JobProperties;
import com.java4less.textprinter.PrinterFactory;
import com.java4less.textprinter.TextPrinter;
import com.java4less.textprinter.TextProperties;
import com.java4less.textprinter.ports.FilePort;


public class Outstanding  extends WriteExcel
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

   
   RcpDto mdt;
   String wtxt,sdate,edate,inputFile;
   int depo,div,bkcd,slno,year,opt;
   private String btnname,drvnm,printernm,brnm,divnm,code,party_name; 
   ArrayList<?> mis; 
   SheetSettings settings; 
   private Date stdt,eddt;  
   HashMap prtMap;
  public Outstanding(String sdate,String edate,String code,String party_name,Integer depo,Integer div,Integer year,String btnnm,String drvnm,String printernm,String brnm,String divnm,String opt) 
  
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
    	this.code=code;
    	this.party_name=party_name;
    	this.wtxt=party_name;
    	this.opt=Integer.parseInt(opt);
    	prtMap=BaseClass.loginDt.getPrtmap();
    	jbInit();
  
    	File file=null;

		if (Desktop.isDesktopSupported()) {
			if (btnname.equalsIgnoreCase("View"))
			{
				file=new File(drvnm+"\\out.TXT");
				Desktop.getDesktop().edit(file);
				
			}
			if (btnname.equalsIgnoreCase("Excel"))
  			{
  				file=new File(drvnm+"\\out.xls");
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
	 	        this.port=new FilePort(drvnm+"\\out.TXT");
	 	        this.printer=PrinterFactory.getPrinter("PLAIN");
	        } 	
 			
 			
			if (btnname.equalsIgnoreCase("Email"))
	        {
	 	        this.port=new FilePort(drvnm+"\\Out.TXT");
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
    	   
    		   mis =   (ArrayList<?>) ms.getOutstanding(year,depo, div, stdt, eddt,Integer.parseInt(code),opt);
    		   
    		   mdt=null;
		   
		   
    		   edate=sdf.format(new Date());

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

      
      double b30=0.00;
      double b60=0.00;
      double b90=0.00;
      double gb30=0.00;
      double gb60=0.00;
      double gb90=0.00;
      PartyDto pdto = null;
        
       boolean first=true;
       col=0;
      // detail header
       int size=mis.size();
      for (i=0;i<size;i++)
      {
    	  mdt = (RcpDto) mis.get(i);
    	  
    	  if(first)
    	  {
    		  if (code==null)
    		  {
    			  if(party_name!=null)
    				  party_name=mdt.getParty_name();
    		  }
    	       head_Para();
    	       first=false;
    	  }

    	  pdto =(PartyDto) prtMap.get(mdt.getVac_code());
		  if(pdto!=null)
		  {
			  party_name=pdto.getMac_name();
		  }
		  
    	  
    	  if(mdt.getVac_code().equals("5000"))
 		  {
 			  party_name=mdt.getParty_name();
 		  }
    	  
    	  if (mdt.getDash()==0)  	  
	      {

		      printer.printString(party_name, r,1,prop);
		      printer.printString((String.valueOf(mdt.getVou_no())), r,40,prop);
    		  if(mdt.getVou_date()!=null)
		    	  printer.printString(sdf.format(mdt.getVou_date()),r,50,prop);
    		  printer.printString(String.valueOf(mdt.getSerialno()), r,69,prop);
    		   if(mdt.getSerialno()<30)
			   {
    			   printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,76,prop);
				   b30+=mdt.getVamount();
				   gb30+=mdt.getVamount();
			   }
    		   else if(mdt.getSerialno()>=30 && mdt.getSerialno()<61)
			   {
    			   printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,88,prop);
				   b60+=mdt.getVamount();
				   gb60+=mdt.getVamount();
			   }
    		   else if(mdt.getSerialno()>60)
			   {
    			   printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,99,prop);
				   b90+=mdt.getVamount();
				   gb90+=mdt.getVamount();
			   }
    		  
		      r++;
		      
	      }

	      if (mdt.getDash()==2)  	  
	      {
	          printer.printHorizontalLine(r,1,dash); // dashLine -----------
	          r++;
		      printer.printString(mdt.getVnart1(), r,25,prop);
		      printer.printString(String.format("%10s",df.format(mdt.getVamount())), r,50,prop);
		      printer.printString(String.format("%10s",df.format(b30)), r,76,prop);
		      printer.printString(String.format("%10s",df.format(b60)), r,88,prop);
		      printer.printString(String.format("%10s",df.format(b90)), r,99,prop);
      	      r++;
	          printer.printHorizontalLine(r,1,dash); // dashLine -----------
	          r++;
	         // first=true;
	          b30=0.00;
	          b60=0.00;
	          b90=0.00;
              //printer.newPage();
             // r=0;

	          
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
      
      
      printer.printString("Grand Total", r,25,prop);
      printer.printString(String.format("%10s",df.format(gb30+gb60+gb90)), r,50,prop);
      printer.printString(String.format("%10s",df.format(gb30)), r,76,prop);
      printer.printString(String.format("%10s",df.format(gb60)), r,88,prop);
      printer.printString(String.format("%10s",df.format(gb90)), r,99,prop);
	      r++;
      printer.printHorizontalLine(r,1,dash); // dashLine -----------
      r++;
      
      
      StringBuffer sb = new StringBuffer(party_name+"\n");
      if(gb90>0)
    	  sb.append("Above 60 days "+df.format(gb90)+"\n");
      if(gb60>0)
    	  sb.append("31-60 days "+df.format(gb60)+"\n");
      if(gb30>0)
    	  sb.append("1-30 days "+df.format(gb30)+"\n");
      
     	  sb.append("Total = "+df.format((gb90+gb60+gb30))+"\n");
     	  
     	  if(depo==10)  // Nakoda 
         	  sb.append("Kotak Mahindra Bank\nA/c No 556044013810\nIFSC-KKBK0005933");
     	  else if(depo==11) // Navkar 
     		  sb.append("Bank : Axis Bank,Indore Branch\nA/c No 910030029239563\nIFS CODE UTIB0000043");
     	  
     	if((gb90+gb60+gb30)>0)  
         BaseClass.smsText=sb.toString();
     	else
     	  BaseClass.smsText=null;
      
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
			  printer.printString("OUTSTANDING  OF "+party_name, r,30,propBold);
		      r++;
			  printer.printString(" FOR THE PERIOD FROM "+sdate+" To "+edate, r,30,propBold);
		      r++;
		      printer.printHorizontalLine(r,1,dash); // dashLine -----------
	   	      r++;
		      printer.printString("NAME OF PARTY ",r,1,prop);
		      printer.printString("BILL NO  ", r,36,prop);
		      printer.printString("DATE  ", r,52,prop);
		      printer.printString("DAYS", r,67,prop);
		      printer.printString("   BELOW 30", r,75,prop);
		      printer.printString(" 30-60 DAYS", r,87,prop);
		      printer.printString("   ABOVE 60", r,98,prop);
		      r++;
		      printer.printHorizontalLine(r,1,dash); // dashLine -----------
		      r++;


   }
   

//////excel file generation report ////////   
   
public void createExcel() throws WriteException, IOException {

	   setOutputFile(drvnm+"\\out.xls");
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
	    
	   //Merge col[0-11] and row[0]
	   sheet.mergeCells(0, 0, 8, 0);
	   // Write a few headers
	   addCaption(sheet, 0, 0, brnm,10);
	   
	   sheet.mergeCells(0, 1, 8, 1);
	   if(opt==1)
		   addCaption1(sheet, 0, 1, "OUTSTANDING OF "+party_name+" AS ON - "+edate,30);
	   else
	   
		   addCaption1(sheet, 0, 1, "OUTSTANDING OF  AS ON - "+edate,30);
	  
	   if (opt>2)
		   addCaption1(sheet, 0, 2, wtxt,10);
		   

	   addCaption2(sheet, 0, 3, "CITY",20);
	   addCaption2(sheet, 1, 3, "NAME",50);
	   addCaption2(sheet, 2, 3, "DATE",12);
	   addCaption2(sheet, 3, 3, "BILL NO",20);
	   addCaption2(sheet, 4, 3, "DELAY DAYS",12);
	   addCaption2(sheet, 5, 3, "BILL AMOUNT",12);
	   addCaption2(sheet, 6, 3, "PAYMENT AMOUNT",12);
	   addCaption2(sheet, 7, 3, "DIFF AMOUNT",12);
	   addCaption2(sheet, 8, 3, "BALANCE",12);

	   settings.setHorizontalFreeze(2);
	   settings.setVerticalFreeze(4);

	   

}  


public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   boolean second=false;
	   String pcity="";
	   double b30=0.00;
	   double b60=0.00;
	   double b90=0.00;
	   col=0;
	   r=4;
	   int dash=0;
	   PartyDto pdto = null;
	   // detail header
	   int size=mis.size();
	   for (i=0;i<size;i++)
	   {
		   mdt = (RcpDto) mis.get(i);
		   
	    	  System.out.println(mdt.getParty_name()+" code "+mdt.getVac_code());
		  
		   
		   if(first)
		   {
	    		  if (code==null)
	    		  {

	    			  if(party_name!=null)
	    				  party_name=mdt.getParty_name();
	    		  }		
	    		  
	    		 pdto =(PartyDto) prtMap.get(mdt.getVac_code());
	    		   
	    		  if(pdto!=null)
	    		  {
	    			  party_name=pdto.getMac_name();
	    			  pcity=pdto.getMcity();
	    		  }
	    		  
	    		  if(mdt.getVac_code().equals("5000"))
	    		  {
	    			  party_name=mdt.getParty_name();
	    			  pcity="";
	    		  }
	    			  
	    		  
			   createHeader(sheet);
			   first=false;
		   }
		   
		   if(second)
		   {
			   if(party_name!=null)
				   party_name=mdt.getParty_name();

	    		 pdto =(PartyDto) prtMap.get(mdt.getVac_code());
	    		   
	    		  if(pdto!=null)
	    		  {
	    			  party_name=pdto.getMac_name();
	    			  pcity=pdto.getMcity();

	    		  }

//			   r++;
			   second=false;
		   }
		   
 		  if(mdt.getVac_code().equals("5000"))
 		  {
 			  party_name=mdt.getParty_name();
 			  pcity="";
 		  }
	  		 if (mdt.getDash()==0)
	  		  {
			   dash=0;

			   addLabel(sheet, 0, r, pcity,dash);
			   addLabel(sheet, 1, r, party_name,dash);
			   if(mdt.getVou_date()!=null)
				   addDate(sheet, 2, r, mdt.getVou_date(),dash);
			   else
				   addLabel(sheet, 2, r, "",dash);
			   addLabel(sheet, 3, r, String.valueOf(mdt.getVou_lo().equals("")? "":(mdt.getVou_lo()+"-"+String.valueOf(mdt.getVou_no()))),dash);
			   addNumber(sheet, 4, r, mdt.getSerialno(),dash);
			   addDouble(sheet, 5, r, mdt.getBill_amt(),dash);
			   addDouble(sheet, 6, r, mdt.getChq_amt(),dash);
			   addDouble(sheet, 7, r, mdt.getVamount(),dash);
			   addLabel(sheet, 8, r, "",dash);
			   b30+=mdt.getBill_amt();
			   b60+=mdt.getChq_amt();
			   b90+=mdt.getVamount();
			   r++;
	  		  }

	  		 if (mdt.getDash()==2)
	  		  {
/*			   dash=2;
			   addLabel(sheet, 0, r, "",dash);
			   addLabel(sheet, 1, r, "",dash);
			   addLabel(sheet, 2, r, mdt.getVnart1(),dash);
			   addLabel(sheet, 3, r, "",dash);
			   addLabel(sheet, 4, r, "",dash);
			   if (mdt.getBill_amt()!=null)
				   addDouble(sheet, 5, r, mdt.getBill_amt(),dash);
			   else
				   addLabel(sheet, 5, r, "",dash);

			   addDouble(sheet, 6, r, mdt.getChq_amt(),dash);
			   addDouble(sheet, 7, r, mdt.getVamount(),dash);
			   addLabel(sheet, 8, r, "",dash);

*/
	  			 addDouble(sheet, 8, (r-1), mdt.getVamount(),dash);
	  			 second=true;
	  		  }


	  		

	   }

	   addLabel(sheet, 0, r, "",dash);
	   addLabel(sheet, 1, r, "",dash);
	   addLabel(sheet, 2, r, "Grand Total",dash);
	   addLabel(sheet, 3, r, "",dash);
	   addLabel(sheet, 4, r, "",dash);
	   addDouble(sheet, 5, r, b30,dash);
	   addDouble(sheet, 6, r, b60,dash);
	   addDouble(sheet, 7, r, b90,dash);
	   addLabel(sheet, 8, r, "",dash);
	   r++;
	   
	   

}

   
public class LevelComparator implements Comparator<RcpDto> {
	public int compare(RcpDto p1, RcpDto p2) {
			return (p1.getVamount()>p2.getVamount()) ? -1 : (p1.getVamount()<p2.getVamount()) ? 1 : 0;
	}
}
   

}
