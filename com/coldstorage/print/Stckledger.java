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
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.coldstorage.dao.StckledgerDao;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.StckprnDto;
import com.coldstorage.excel.WriteExcel;


public class Stckledger extends WriteExcel 
{    
   /**   
	 * 
	 */
	 
   int r,i,col;
   int dash,dash1;
   SimpleDateFormat sdf,sdf2;
   DecimalFormat df;

   ArrayList<?> mis;    
   StckprnDto mdt;
   String wtxt,monNm,sdate,edate, smon,btnname,inputFile,drvnm,printernm,brnm,divnm,filename;
   int year,depo,div,saletype;
   private Date stdt,eddt;
   ArrayList<?> prd; 
   SheetSettings settings;
   GroupDto cmp;
   
   public Stckledger(String smon,String monnm,String year,String depo,String div,String sdate,String edate,String btnname,String drvnm,String printernm,String brnm,String divnm,ArrayList prd,Integer saletype,GroupDto cmp)

   {
    try 
    {
    	this.r=0;
    	this.i=0;
    	this.dash=124;
     	this.smon=smon;
    	this.year=Integer.parseInt(year);
    	this.depo=Integer.parseInt(depo);
    	this.div=Integer.parseInt(div);
    	this.monNm=monnm;
    	this.sdate=sdate;
    	this.edate=edate;
    	this.btnname=btnname;
    	this.drvnm=drvnm;
    	this.printernm=printernm;
    	this.brnm=brnm;
    	this.divnm=divnm;
    	this.saletype=saletype;
    	this.prd=prd;
    	this.cmp=cmp;
    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mma");
    	filename="STOCK_STATEMENT-"+brnm+"-"+sdf2.format(new Date());

        jbInit();
        
        File file=null;

   		if (Desktop.isDesktopSupported()) {

   			if (btnname.equalsIgnoreCase("Excel"))
   			{
   				file=new File(drvnm+"\\"+filename+".xls");
   				Desktop.getDesktop().open(file);
   				
   			}
  			if (btnname.equalsIgnoreCase("Print"))
   			{
   				file=new File(drvnm+"\\"+filename+".xls");
   				Desktop.getDesktop().print(file);
   				
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

         
      
      this.order();


    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


   /**
    * test characters set conversion
    */
   public void order() {

    try {
    	
    	   sdf = new SimpleDateFormat("dd/MM/yyyy");
    	   		   
    	   stdt=sdf.parse(sdate);
    	   eddt=sdf.parse(edate);
    	   
    	   StckledgerDao ms = new StckledgerDao();
    	   mis =   (ArrayList<?>) ms.stckPrint(smon,year,depo,div,stdt,eddt,prd,saletype,cmp);
    		   
		   mdt=null; 
		   
		   if(cmp!=null)
			   wtxt = " OF "+cmp.getGp_name().toUpperCase();
		   
				  createExcel();

		  } catch (Exception e) {
			  e.printStackTrace();
		  }

	}
   
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
	   workbook.createSheet("Stock", 0);
	   WritableSheet excelSheet = workbook.getSheet(0);
	   settings = excelSheet.getSettings();
	   settings.setPrintTitlesRow(0, 3);
	   
	   createLabel(excelSheet);
	   createContent(excelSheet);

	   workbook.write();
	   workbook.close();
}


public void createHeader(WritableSheet sheet)
		   throws WriteException {
	    

	   if(saletype==99)
	   {
		   sheet.mergeCells(0, 0, 5, 0);
		   addCaption(sheet, 0, 0, brnm,10);
		   sheet.mergeCells(0, 1, 5,1);
		   addCaption1(sheet, 0, 1, "STOCK STATEMENT FOR THE MONTH OF : "+monNm, 30);

		   addCaption2(sheet, 0, 3, "SNO",6);
		   addCaption2(sheet, 1, 3, "PRODUCT NAME",40);
		   addCaption2(sheet, 2, 3, "RATE", 11);
		   addCaption2(sheet, 3, 3, "QTY", 6);
		   addCaption2(sheet, 4, 3, "STOCK VALUE", 11);
		   addCaption2(sheet, 5, 3,"SALE VALUE", 11);
		   
	   }
	   else
	   {
		   sheet.mergeCells(0, 0, 10, 0);
		   addCaption(sheet, 0, 0, brnm,10);
		   sheet.mergeCells(0, 1, 10,1);
		   addCaption1(sheet, 0, 1, "STOCK STATEMENT"+wtxt+" FOR THE MONTH OF : "+monNm, 30);

		   addCaption2(sheet, 0, 3, "PRODUCT CODE", 10);
		   addCaption2(sheet, 1, 3, "D E S C R I P T I O N",50);
		   addCaption2(sheet, 2, 3, "PACKING", 20);
		   addCaption2(sheet, 3, 3, "OPENING", 14);
		   addCaption2(sheet, 4, 3, "RECEIPT", 10);
		   addCaption2(sheet, 5, 3, "SALE RETURN", 11);
		   addCaption2(sheet, 6, 3, "SALES", 11);
		   addCaption2(sheet, 7, 3, "TRF", 11);
		   addCaption2(sheet, 8, 3, "CLOSING", 11);
		   addCaption2(sheet, 9, 3, "STOCK VALUE", 11);
		   addCaption2(sheet, 10, 3, "SALE VALUE", 11);
		   settings.setHorizontalFreeze(4);
	   }

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
	   int sno=1;
	   for (i=0;i<size;i++)
	   {
		   mdt = (StckprnDto) mis.get(i);
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
		   }
		      if (mdt.getDash()==1)
		      {
		    	  dash=1;
		    	  if(saletype==99)
			    	 {
			    		 addLabel(sheet, 0, r, "",dash);
			    		 addLabel(sheet, 1, r, "",dash);
			    		 addLabel(sheet, 2, r, "",dash);
			    		 addLabel(sheet, 3, r, "",dash);
			    		 addDouble(sheet, 4, r, mdt.getClosval(),dash);
			    		 addDouble(sheet, 5, r, mdt.getClosvalnet(),dash);
			    	 }
			    	 else
			    	 {
			    		 addLabel(sheet, 0, r, "",dash);
			    		 addLabel(sheet, 1, r, mdt.getProduct(),dash);
			    		 addLabel(sheet, 2, r,"",dash);
			    		 addLabel(sheet, 3, r, "",dash);
			    		 addLabel(sheet, 4, r, "",dash);
			    		 addLabel(sheet, 5, r, "",dash);
			    		 addLabel(sheet, 6, r, "",dash);
			    		 addLabel(sheet, 7, r, "",dash);
			    		 addLabel(sheet, 8, r, "",dash);
			    		 addDouble(sheet, 9, r, mdt.getClosval(),dash);
			    		 addDouble(sheet, 10, r, mdt.getClosvalnet(),dash);
			    	 }
				   dash=0;
		      }

		      if (mdt.getDash()==0)
		      {
		    	  if(saletype==99)
		    	 {
					   addNumber(sheet, 0, r, sno++,0);
					   addLabel(sheet, 1, r, mdt.getProduct(),dash);
					   addDouble(sheet,2, r, mdt.getRate(),dash);
					   addNumber(sheet,3, r, (int) mdt.getClosqty(),dash);
					   addDouble(sheet,4, r, mdt.getClosval(),dash);
					   addDouble(sheet, 5, r, mdt.getClosvalnet(),dash);
		    		 
		    	 }
		    	 else
		    	 {
				   addNumber(sheet, 0, r, mdt.getCode(),0);
				   addLabel(sheet, 1, r, mdt.getProduct(),dash);
				   addLabel(sheet, 2, r, mdt.getPacking(),dash);
				   addDouble(sheet,3, r, mdt.getOpenqty(),dash);
				   addNumber(sheet,4, r, mdt.getReceipt(),dash);
				   addNumber(sheet,5, r, mdt.getSalereturn(),dash);
				   addDouble(sheet,6, r, mdt.getSaleqty(),dash);
				   addNumber(sheet,7, r, mdt.getTrf(),dash);
				   addDouble(sheet,8, r, mdt.getClosqty(),dash);
				   addDouble(sheet,9, r, mdt.getClosval(),dash);
				   addDouble(sheet, 10, r, mdt.getClosvalnet(),dash);
		    	 } 
		      }

	 	  r++;
		   
		   
		   
	   }	   


}

 
   
  
   

}
