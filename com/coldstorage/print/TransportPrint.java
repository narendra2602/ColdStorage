package com.coldstorage.print;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.excel.WriteExcel;

public class TransportPrint extends WriteExcel
{
	   /**   
		 * 
		 */
		 
	   int r,i,col,repno;
	   int dash;
	   SimpleDateFormat sdf,sdf2;
	   DecimalFormat df;
	   PartyDto mdt;
	   String wtxt,sdate,edate,inputFile,repname;
	   private String btnname,drvnm,printernm,brnm,divnm; 
	   Vector mis1=null;
	   SheetSettings settings; 
	   public TransportPrint(String drvnm,String brnm,Vector v) 
	  
	  {
	    try 
	    {
	    	
	    	this.r=0;
	    	this.i=0;
	    	this.brnm=brnm;
	    	this.dash=110;
	    	this.drvnm=drvnm;
		    this.mis1=v;
		    
	    	
	    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mma");
	    	repname="ACCOUNT_MASTER-"+sdf2.format(new Date());

		    jbInit();
	  
	    	File file=null;

			if (Desktop.isDesktopSupported()) {

					file=new File(drvnm+"\\"+repname+".xls");
	  				Desktop.getDesktop().open(file);
	  				
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
	    	  

					  createExcel();

			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		
		  }
	   

	//////excel file generation report ////////   
	   
	public void createExcel() throws WriteException, IOException {

				  setOutputFile(drvnm+"\\"+repname+".xls");

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
		   workbook.createSheet("ACCOUNT_MASTER", 0);
		   WritableSheet excelSheet = workbook.getSheet(0);
		   settings = excelSheet.getSettings();
		   createLabel(excelSheet);
		   createContent(excelSheet);

		   workbook.write();
		   workbook.close();
	}


	public void createHeader(WritableSheet sheet)
			   throws WriteException {
		    
		int col=14;
		
			   sheet.mergeCells(0, 0, col, 0);
			   // Write a few headers
			   addCaption(sheet, 0, 0, "Shree Laxmi Ice & Cold Storage (P.) Ltd.",40);
			   
			   sheet.mergeCells(0, 2, col, 2);
			   addCaption(sheet, 0, 2, "Account Master",40);

			addCaption2(sheet, 0, 3, "Code",10);
			addCaption2(sheet, 1, 3, "Name",50);
			addCaption2(sheet, 2, 3, "Address1",30);
			addCaption2(sheet, 3, 3, "Address2",30);
			addCaption2(sheet, 4, 3, "City",20);
			addCaption2(sheet, 5, 3, "Pin",15);
			addCaption2(sheet, 6, 3, "State",25);
			addCaption2(sheet, 7, 3, "Contact Person",20);
			addCaption2(sheet, 8, 3, "Phone",15);
			addCaption2(sheet, 9, 3, "Mobile",20);
			addCaption2(sheet, 10, 3, "Email-Id",50);
			addCaption2(sheet, 11, 3, "Aadhar No",20);
			addCaption2(sheet, 12, 3, "Pan No",15);
			addCaption2(sheet, 13, 3, "Name",50);
			addCaption2(sheet, 14, 3, "City",20);

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
		   if (mis1!=null)
			   size=mis1.size();
		   TransportDto pdt1=null;
		   Vector c=null;
		   for (i=0;i<size;i++)
		   {
					c =(Vector) mis1.get(i);
				    pdt1 =  ((TransportDto) c.get(2));
			   if(first)
			   {
				   createHeader(sheet);
				   first=false;
			   }

				   dash=0;
				   addLabel(sheet, 0, r, pdt1.getTran_code(),dash);
				   addLabel(sheet, 1, r, pdt1.getTran_name(),dash);
				   addLabel(sheet, 2, r, pdt1.getAddress1(),dash);
				   addLabel(sheet, 3, r, pdt1.getAddress2(),dash);
				   addLabel(sheet, 4, r, pdt1.getCity(),dash);
				   addLabel(sheet, 5, r, pdt1.getMpin(),dash);
				   addLabel(sheet, 6, r, pdt1.getMstate(),dash);
				   addLabel(sheet, 7, r, pdt1.getContact_person(),dash);
				   addLabel(sheet, 8, r, pdt1.getPhone(),dash);
				   addLabel(sheet, 9, r, pdt1.getMobile(),dash);
				   addLabel(sheet, 10, r, pdt1.getEmail_id(),dash);
				   addLabel(sheet, 11, r, pdt1.getTran_gstno(),dash);
				   addLabel(sheet, 12, r, pdt1.getTran_id(),dash);
				   addLabel(sheet, 13, r, pdt1.getMac_name_hindi(),dash);
				   addLabel(sheet, 14, r, pdt1.getMcity_hindi(),dash);
				   
				   r++;
						   
		   }
	}
}
