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

import com.coldstorage.dao.SalRegDAO;
import com.coldstorage.dto.InvFstDto;
import com.coldstorage.dto.InvViewDto;
import com.coldstorage.excel.WriteExcel;


public class SaleReg  extends WriteExcel
{    
   /**   
	 * 
	 */
	private static final long serialVersionUID = 1L;
   int r,i,col;
   int dash;
   double pgross=0.00;
   double pnet=0.00;
   double cgross=0.00;
   double cnet=0.00;
   double gross5=0.00;
   double vat5=0.00;
   double gross14=0.00;
   double vat14=0.00;
   double gross15=0.00;
   double vat15=0.00;
   
   double pgross5=0.00;
   double pvat5=0.00;
   double pgross14=0.00;
   double pvat14=0.00;
   double pgross15=0.00;
   double pvat15=0.00;
   
   SimpleDateFormat sdf,sdf2;
   DecimalFormat df;
   ArrayList<?> salreg; 
   InvViewDto inv;
   String wtxt,monNm,sdate,edate, smon,inputFile,btnname,drvnm,printernm,brnm;
   int year,depo,div,optn,doc_type;
   private Date stdt,eddt;
   ArrayList partyList;
   SheetSettings settings;
   String flnm,flnm1;
   public SaleReg(String smon,String monnm,String year,String depo,String div,String sdate,String edate,String btnname,String drvnm,String printernm,String brnm,Integer doc_type,ArrayList partyList,Integer optn)
   { 
    try {
    	
    	this.partyList=partyList; 
    	this.r=0;
    	this.i=0;
    	this.optn=optn;
    	this.dash=180;
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
    	this.doc_type=doc_type;
    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mma");
    	flnm="Outward_Register-"+sdf2.format(new Date());
    	flnm1="Outward_Register";
    	if(doc_type==60)
    	{
        	flnm="Inward_Register-"+sdf2.format(new Date());
        	flnm1="Inward_Register";
    	}
    	else if(doc_type==600)
    	{
        	flnm="Final_Register-"+sdf2.format(new Date());
        	flnm1="Final_Register";
    	}
    	else if(doc_type==700)
    	{
        	flnm="Discount_Register-"+sdf2.format(new Date());
        	flnm1="Discount_Register";
    	}
    	else if(doc_type==41)
    	{
        	flnm="Transfer_Register-"+sdf2.format(new Date());
        	flnm1="Transfer_Register";
    	}
    	else if(doc_type==66)
    	{
        	flnm="WritttenOff_Register-"+sdf2.format(new Date());
        	flnm1="WrittenOff_Register";
    	}
    	else if(doc_type==10)
    	{
        	flnm="Rent_Register-"+sdf2.format(new Date());
        	flnm1="Rent_Register";
    	}

    	else if(doc_type==50)
    	{
        	flnm="Gate_Pass_Register-"+sdf2.format(new Date());
        	flnm1="Gate_Pass__Register";
    	}

    	jbInit();
    	
        File file=null;

  		if (Desktop.isDesktopSupported()) 
  		{
  		
  			if (btnname.equalsIgnoreCase("Excel"))
  			{
  				file=new File(drvnm+"\\"+flnm+".xls");
  				Desktop.getDesktop().open(file);
  				
  			}
  		}
    	
    	
   

    }
    catch(Exception e) {
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
    	   
 
    	
           SalRegDAO srg = new SalRegDAO();
           if(doc_type==600)
    		   salreg =   (ArrayList<?>) srg.salRegPrintFinal(smon,year,depo,div,stdt,eddt,partyList,optn,(doc_type-540));
           else
        	   salreg =   (ArrayList<?>) srg.salRegPrint(smon,year,depo,div,stdt,eddt,partyList,optn,doc_type);
		   
		   createExcel();

		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	
	  }
	  /////// print file option //////////////  
   
   
   
   
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

	   WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);

	   workbook.createSheet(flnm1, 0);
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
	   sheet.mergeCells(0, 0, 13, 0);
	   // Write a few headers
	   
	   addCaption(sheet, 0, 0, "श्री लक्ष्मी आईस एण्ड  कोल्ड स्टोरेज प्रा.लि.",10);
	   
	   sheet.mergeCells(0, 1, 13, 1);
	   if(doc_type==60)
		   addCaption1(sheet, 0, 1, "आवक रजिस्टर "  +sdate+" - "+edate,30);
	   else if(doc_type==600)
		   addCaption1(sheet, 0, 1, "रजिस्टर "  +sdate+" - "+edate,30);
	   else if(doc_type==700)
		   addCaption1(sheet, 0, 1, "डिस्काउंट रजिस्टर "  +sdate+" - "+edate,30);
	   else if(doc_type==41)
		   addCaption1(sheet, 0, 1, "ट्रांसफर रजिस्टर "  +sdate+" - "+edate,30);
	   else if(doc_type==66)
		   addCaption1(sheet, 0, 1, "छाटन  रजिस्टर "  +sdate+" - "+edate,30);
	   else if(doc_type==10)
		   addCaption1(sheet, 0, 1, "किराया   रजिस्टर "  +sdate+" - "+edate,30);
	   else if(doc_type==50)
		   addCaption1(sheet, 0, 1, "गेट  पास   रजिस्टर "  +sdate+" - "+edate,30);
	   else 
		   addCaption1(sheet, 0, 1, "जावक रजिस्टर"+sdate+" - "+edate,30);

	      
	   if(doc_type==60)
	   {
		   addCaption2(sheet, 0, 2, "दिनाक • ",12 );
		   addCaption2(sheet, 1, 2, "रसीद क्रमांक ",10 );
		   addCaption2(sheet, 2, 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 3 , 2, "व्यापारी का नाम  ",35 );
		   addCaption2(sheet, 4, 2, "शहर ° ",15);
		   addCaption2(sheet, 5, 2, "मोबाईल न  ° ",15);
		   addCaption2(sheet, 6 , 2, "मार्का  ",15);
		   addCaption2(sheet, 7 , 2, "कट्टे ",10 );
		   addCaption2(sheet, 8 , 2, "मंजिल ",10 );
		   addCaption2(sheet, 9 , 2, "गाला   ",10 );
		   addCaption2(sheet, 10 , 2, "किस्म ",10 );
		   addCaption2(sheet, 11 , 2, "बारदान ",15);
		   addCaption2(sheet, 12   , 2, "नाम ",15);
		   addCaption2(sheet, 13  , 2, "वजन ",15);
		   addCaption2(sheet, 14   , 2, "किराया  ",15);
	   }
	   if(doc_type==700)
	   {
		   addCaption2(sheet, 0, 2, "दिनाक • ",12 );
		   addCaption2(sheet, 1, 2, "रसीद क्रमांक ",10 );
		   addCaption2(sheet, 2, 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 3 , 2, "व्यापारी का नाम  ",35 );
		   addCaption2(sheet, 4, 2, "शहर ° ",15);
		   addCaption2(sheet, 5, 2, "मोबाईल न  ° ",15);
		   addCaption2(sheet, 6   , 2, "किराया  ",15);
		   addCaption2(sheet, 7 , 2, "डिस्काउंट ",10 );
		   addCaption2(sheet,8 , 2, "नेट किराया ",10 );
	   }
	   else if(doc_type==10)
	   {
		   addCaption2(sheet, 0, 2, "दिनाक • ",12 );
		   addCaption2(sheet, 1, 2, "रसीद क्रमांक ",10 );
		   addCaption2(sheet, 2, 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 3 , 2, "व्यापारी का नाम  ",35 );
		   addCaption2(sheet, 4, 2, "शहर ° ",15);
		   addCaption2(sheet, 5, 2, "मोबाईल न  ° ",15);
		   addCaption2(sheet, 6   , 2, "किराया  ",15);
		   addCaption2(sheet, 7   , 2, "किराया आया  ",15);
		   addCaption2(sheet, 8   , 2, "टाइप  ",10);
		   addCaption2(sheet, 9   , 2, "रीमार्क ",50);
	   }
	   else if(doc_type==66)
	   {
		   addCaption2(sheet, 0, 2, "दिनाक • ",12 );
		   addCaption2(sheet, 1, 2, "रसीद क्रमांक ",10 );
		   addCaption2(sheet, 2, 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 3 , 2, "व्यापारी का नाम  ",35 );
		   addCaption2(sheet, 4, 2, "शहर ° ",15);
		   addCaption2(sheet, 5, 2, "मोबाईल न  ° ",15);
		   addCaption2(sheet, 6 , 2, "किस्म ",10 );
		   addCaption2(sheet, 7 , 2, "बारदान ",15);
		   addCaption2(sheet, 8 , 2, "नाम ",15);
		   addCaption2(sheet, 9 , 2, "मार्का  ",15);
		   addCaption2(sheet, 10 , 2, "कट्टे ",10 );
		   addCaption2(sheet, 11  , 2, "वजन ",15);
	   }
	   else if(doc_type==600)
	   {
		   addCaption2(sheet, 0, 2, "दिनाक • ",12 );
		   addCaption2(sheet, 1, 2, "रसीद क्रमांक ",10 );
		   addCaption2(sheet, 2, 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 3 , 2, "व्यापारी का नाम  ",35 );
		   addCaption2(sheet, 4, 2, "शहर ° ",15);
		   addCaption2(sheet, 5, 2, "मोबाईल न . ° ",15);
		   addCaption2(sheet, 6    , 2, "नाम ",15);
		   addCaption2(sheet, 7 , 2, "कट्टे आए ",10 );
		   addCaption2(sheet, 8  , 2, "वजन  आया ",15);
		   addCaption2(sheet, 9 , 2, "कट्टे  गए ",10 );
		   addCaption2(sheet, 10  , 2, "वजन गया  ",15);
		   addCaption2(sheet, 11  , 2, "छाटन कट्टे ",15);
		   addCaption2(sheet, 12  , 2, "छाटन वजन  ",15);
		   addCaption2(sheet, 13   , 2, "किराया  ",15);
		   addCaption2(sheet, 14   , 2, "किराया  आया  ",15);
		   addCaption2(sheet, 15   , 2, "किराया  बाकि ",15);
	   }

	   else if(doc_type==40)
	   {
		   addCaption2(sheet, 0, 2, "जावक दिनाक • ",12 );
		   addCaption2(sheet, 1, 2, "जावक रसीद क्रमांक ",10 );
		   addCaption2(sheet, 2, 2, "आवक दिनाक • ",12 );
		   addCaption2(sheet, 3 , 2, "आवक रसीद क्रमांक ",10 );
		   addCaption2(sheet, 4, 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 5 , 2, "व्यापारी का नाम  ",35 );
		   addCaption2(sheet, 6, 2, "शहर ° ",15);
		   addCaption2(sheet, 7, 2, "मोबाईल न . ° ",15);
		   addCaption2(sheet, 8 , 2, "मार्का  ",15);
		   addCaption2(sheet, 9 , 2, "कट्टे ",10 );
		   addCaption2(sheet, 10 , 2, "वजन  ",10 );
		   addCaption2(sheet, 11 , 2, "व्यापरी  का नाम  ",30 );
		   addCaption2(sheet, 12 , 2, "मोबाईल न . ",20 );
		   addCaption2(sheet, 13 , 2, "माल  ",15);
		   addCaption2(sheet, 14   , 2, "वाहन  क्रमांक  ",20);
		   addCaption2(sheet, 15  , 2, "रीमार्क ",20);
	   }
	   else if(doc_type==50)
	   {
		   addCaption2(sheet, 0, 2, "गेट पास  दिनाक • ",12 );
		   addCaption2(sheet, 1 , 2, "गेट पास  क्रमांक ",10 );
		   addCaption2(sheet, 2, 2, "जावक दिनाक • ",12 );
		   addCaption2(sheet, 3, 2, "जावक रसीद क्रमांक ",10 );
		   addCaption2(sheet, 4, 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 5 , 2, "व्यापारी का नाम  ",35 );
		   addCaption2(sheet, 6, 2, "शहर ° ",15);
		   addCaption2(sheet, 7, 2, "मोबाईल न . ° ",15);
		   addCaption2(sheet, 8 , 2, "मार्का  ",15);
		   addCaption2(sheet, 9 , 2, "कट्टे ",10 );
		   addCaption2(sheet, 10 , 2, "वजन  ",10 );
		   addCaption2(sheet, 11 , 2, "व्यापरी  का नाम  ",30 );
		   addCaption2(sheet, 12 , 2, "मोबाईल न . ",20 );
		   addCaption2(sheet, 13 , 2, "माल  ",15);
		   addCaption2(sheet, 14   , 2, "वाहन  क्रमांक  ",20);
		   addCaption2(sheet, 15  , 2, "रीमार्क ",20);
	   }
	   else if(doc_type==41)
	   {
		   addCaption2(sheet, 0, 2, "ट्रांसफर दिनाक • ",12 );
		   addCaption2(sheet, 1, 2, "ट्रांसफर रसीद क्रमांक ",10 );
		   addCaption2(sheet, 2, 2, "आवक दिनाक • ",12 );
		   addCaption2(sheet, 3 , 2, "आवक रसीद क्रमांक ",10 );
		   addCaption2(sheet, 4, 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 5 , 2, "ट्रांसफर करने वाले व्यापारी का नाम  ",35 );
		   addCaption2(sheet, 6, 2, "शहर ° ",15);
		   addCaption2(sheet, 7, 2, "मोबाईल न . ° ",15);
		   addCaption2(sheet, 8 , 2, "मार्का  ",15);
		   addCaption2(sheet, 9 , 2, "कट्टे ",10 );
		   addCaption2(sheet, 10 , 2, "वजन  ",10 );
		   addCaption2(sheet, 11 , 2, "खाता क्रमांक   ",10 );
		   addCaption2(sheet, 12 , 2, "पाने वाले व्यापरी  का नाम  ",30 );
		   addCaption2(sheet, 13 , 2, "मोबाईल न . ",20 );
		   addCaption2(sheet, 14 , 2, "माल  ",15);
		   addCaption2(sheet, 15   , 2, "वाहन  क्रमांक  ",20);
		   addCaption2(sheet, 16  , 2, "रीमार्क ",20);
	   }

//	   settings.setHorizontalFreeze(2);
	   settings.setVerticalFreeze(3);

}  


public void createContent(WritableSheet sheet) throws WriteException,RowsExceededException
{

	   boolean first=true;
	   col=0;
	   r=3;
	   pgross=0.00;
	   pnet=0.00;
	   int dash=0;
	   // detail header
	   int size=salreg.size();
	   for (i=0;i<size;i++)
	   {
		   inv = (InvViewDto) salreg.get(i);
		   if(first)
		   {
			   createHeader(sheet);
			   first=false;
		   }
		   
		   
			   dash=inv.getDash();

			   if(doc_type==60)
			   {
				   addLabel(sheet, 0, r, sdf.format(inv.getInv_date()).toString(),dash);
				   addLabel(sheet, 1, r, inv.getInv_no(),dash);
				   addLabel(sheet, 2, r, inv.getMac_code(),dash);
				   addLabel(sheet, 3, r, inv.getMac_name_hindi(),dash);
				   addLabel(sheet, 4, r, inv.getMcity_hindi(),dash);
				   addLabel(sheet, 5, r, inv.getMphone(),dash);
				   addLabel(sheet, 6, r, inv.getMark_no(),dash);
				   addDouble(sheet, 7, r, inv.getSqty(),dash);
				   addLabel(sheet, 8, r, inv.getFloor_no(),dash);
				   addLabel(sheet, 9, r, inv.getBlock_no(),dash);
				   addLabel(sheet, 10, r, inv.getCategory_hindi(),dash);
				   addLabel(sheet, 11, r, inv.getPack(),dash);
				   addLabel(sheet, 12, r, inv.getPname_hindi(),dash);
				   addDouble(sheet, 13, r, inv.getWeight(),dash);
				   addDouble(sheet, 14, r, inv.getNet_amt(),dash);

				   cnet=cnet+inv.getNet_amt();
			   }
			   else if(doc_type==66)
			   {
				   addLabel(sheet, 0, r, sdf.format(inv.getInv_date()).toString(),dash);
				   addLabel(sheet, 1, r, inv.getInv_no(),dash);
				   addLabel(sheet, 2, r, inv.getMac_code(),dash);
				   addLabel(sheet, 3, r, inv.getMac_name_hindi(),dash);
				   addLabel(sheet, 4, r, inv.getMcity_hindi(),dash);
				   addLabel(sheet, 5, r, inv.getMphone(),dash);
				   addLabel(sheet, 6, r, inv.getCategory_hindi(),dash);
				   addLabel(sheet, 7, r, inv.getPack(),dash);
				   addLabel(sheet, 8, r, inv.getPname_hindi(),dash);
				   addLabel(sheet, 9, r, inv.getMark_no(),dash);
				   addDouble(sheet, 10, r, inv.getIssue_qty(),dash);
				   addDouble(sheet, 11, r, inv.getIssue_weight(),dash);
			   }
			   else if(doc_type==700)
			   {
				   if(dash==0)
				   {
				   addLabel(sheet, 0, r, sdf.format(inv.getInv_date()).toString(),dash);
				   addLabel(sheet, 1, r, inv.getInv_no(),dash);
				   addLabel(sheet, 2, r, inv.getMac_code(),dash);
				   addLabel(sheet, 3, r, inv.getMac_name_hindi(),dash);
				   addLabel(sheet, 4, r, inv.getMcity_hindi(),dash);
				   addLabel(sheet, 5, r, inv.getMphone(),dash);
				   addDouble(sheet, 6, r, inv.getSamnt(),dash);
				   addDouble(sheet, 7, r, inv.getRound_off(),dash);
				   addDouble(sheet, 8, r, inv.getNet_amt(),dash);
				   }
				   if(dash==1)
				   {
					   addLabel(sheet, 0, r, "",dash);
					   addLabel(sheet, 1, r, "",dash);
					   addLabel(sheet, 2, r, "",dash);
					   addLabel(sheet, 3, r, "",dash);
					   addLabel(sheet, 4, r, "",dash);
					   addLabel(sheet, 5, r, "",dash);
					   addDouble(sheet, 6, r, inv.getSamnt(),dash);
					   addDouble(sheet, 7, r, inv.getRound_off(),dash);
					   addDouble(sheet, 8, r, inv.getNet_amt(),dash);
				   }
			   }
			   else if(doc_type==10)
			   {
				   if(dash==0)
				   {
				   addLabel(sheet, 0, r, sdf.format(inv.getInv_date()).toString(),dash);
				   addLabel(sheet, 1, r, inv.getInv_no(),dash);
				   addLabel(sheet, 2, r, inv.getMac_code(),dash);
				   addLabel(sheet, 3, r, inv.getMac_name_hindi(),dash);
				   addLabel(sheet, 4, r, inv.getMcity_hindi(),dash);
				   addLabel(sheet, 5, r, inv.getMphone(),dash);
				   addDouble(sheet, 6, r, inv.getNet_amt(),dash);
				   addDouble(sheet, 7, r, inv.getDiscount_amt(),dash);
				   addLabel(sheet, 8, r, inv.getFloor_no(),dash);
				   addLabel(sheet, 9, r, inv.getMark_no(),dash);
				   }
				   if(dash==1)
				   {
					   addLabel(sheet, 0, r, "",dash);
					   addLabel(sheet, 1, r, "",dash);
					   addLabel(sheet, 2, r, "",dash);
					   addLabel(sheet, 3, r, "",dash);
					   addLabel(sheet, 4, r, "",dash);
					   addLabel(sheet, 5, r, "",dash);
					   addDouble(sheet, 6, r, inv.getNet_amt(),dash);
					   addDouble(sheet, 7, r, inv.getDiscount_amt(),dash);
					   addLabel(sheet, 8, r, "",dash);
					   addLabel(sheet, 9, r, "",dash);
				   }
			   }

			   else if(doc_type==600)
			   {
				   if(dash==0)
				   {
					   if(inv.getDoc_type()==10)
						   dash=5;
					   else if(inv.getDoc_type()==40)
						   dash=2;
					   else if(inv.getDoc_type()==41)
						   dash=2;
					   addLabel(sheet, 0, r, sdf.format(inv.getInv_date()).toString(),dash);
					   
					   if(inv.getDoc_type()==41)
					   {
						   addLabel(sheet, 1, r, String.valueOf(inv.getTransfer_no()),dash);
						   addLabel(sheet, 2, r, inv.getMac_code(),dash);
						   addLabel(sheet, 3, r, "TRANSFER - "+inv.getReceiver_name(),dash);
						   addLabel(sheet, 4, r, inv.getReceiver_city(),dash);
					   }
					   else
					   {
						   addLabel(sheet, 1, r, inv.getInv_no(),dash);
						   addLabel(sheet, 2, r, inv.getMac_code(),dash);
						   addLabel(sheet, 3, r, inv.getMac_name_hindi(),dash);
						   addLabel(sheet, 4, r, inv.getMcity_hindi(),dash);
					   }
					   addLabel(sheet, 5, r, inv.getMphone(),dash);
					   addLabel(sheet, 6, r, inv.getPname_hindi(),dash);
					   addDouble(sheet, 7, r, inv.getSqty(),dash);
					   addDouble(sheet, 8, r, inv.getWeight(),dash);
					   addDouble(sheet, 9, r, inv.getIssue_qty(),dash);
					   addDouble(sheet, 10, r, inv.getIssue_weight(),dash);
					   addDouble(sheet, 11, r, inv.getWritten_off_qty(),dash);
					   addDouble(sheet, 12, r, inv.getWritten_off_weight(),dash);
					   addDouble(sheet, 13, r, inv.getNet_amt(),dash);
					   addDouble(sheet, 14, r, inv.getSamnt(),dash);
					   addDouble(sheet, 15, r, inv.getRound_off(),dash);
				   }
				   if(dash==1)
				   {
					   addLabel(sheet, 0, r, "",dash);
					   addLabel(sheet, 1, r, "",dash);
					   addLabel(sheet, 2, r, "",dash);
					   addLabel(sheet, 3, r, "",dash);
					   addLabel(sheet, 4, r, "",dash);
					   addLabel(sheet, 5, r, "",dash);
					   addLabel(sheet, 6, r, inv.getPname_hindi(),dash);
					   addLabel(sheet, 7, r, "",dash);
					   addLabel(sheet, 8, r, "",dash);
					   addLabel(sheet, 9, r, "",dash);
					   addLabel(sheet, 10, r, "",dash);
					   addLabel(sheet, 11, r, "",dash);
					   addLabel(sheet, 12, r, "",dash);
					   addDouble(sheet, 13, r, inv.getNet_amt(),dash);
					   addDouble(sheet, 14, r, inv.getSamnt(),dash);
					   addDouble(sheet, 15, r, inv.getRound_off(),dash);
				   }
				   if(dash==3)
				   {
					   addLabel(sheet, 0, r, "",dash);
					   addLabel(sheet, 1, r, "",dash);
					   addLabel(sheet, 2, r, "",dash);
					   addLabel(sheet, 3, r, "",dash);
					   addLabel(sheet, 4, r, "",dash);
					   addLabel(sheet, 5, r, "",dash);
					   addLabel(sheet, 6, r, inv.getPname_hindi(),dash);
					   addLabel(sheet, 7, r, "",dash);
					   addLabel(sheet, 8, r, "",dash);
					   addLabel(sheet, 9, r, "",dash);
					   addLabel(sheet, 10, r, "",dash);
					   addLabel(sheet, 11, r, "",dash);
					   addLabel(sheet, 12, r, "",dash);
					   addDouble(sheet, 13, r, inv.getNet_amt(),dash);
					   addDouble(sheet, 14, r, inv.getSamnt(),dash);
					   addDouble(sheet, 15, r, inv.getRound_off(),dash);
				   }
			   }

			   else if(doc_type==40)
			   {
				   addLabel(sheet, 0, r, sdf.format(inv.getChl_date()).toString(),dash);
				   addLabel(sheet, 1, r, inv.getChl_no(),dash);
				   addLabel(sheet, 2, r, sdf.format(inv.getInv_date()).toString(),dash);
				   addLabel(sheet, 3, r, inv.getInv_no(),dash);
				   addLabel(sheet, 4, r, inv.getMac_code(),dash);
				   addLabel(sheet, 5, r, inv.getMac_name_hindi(),dash);
				   addLabel(sheet, 6, r, inv.getMcity_hindi(),dash);
				   addLabel(sheet, 7, r, inv.getMphone(),dash);
				   addLabel(sheet, 8, r, inv.getMark_no(),dash);
				   addDouble(sheet, 9, r, inv.getIssue_qty(),dash);
				   addDouble(sheet, 10, r, inv.getIssue_weight(),dash);
				   addLabel(sheet, 11, r, inv.getReceiver_name(),dash);
				   addLabel(sheet, 12, r, inv.getMobile(),dash);
				   addLabel(sheet, 13, r, inv.getPname_hindi(),dash);
				   addLabel(sheet, 14, r, inv.getVehicle_no(),dash);
				   addLabel(sheet, 15, r, inv.getRemark(),dash);
			   }
			   else if(doc_type==50)
			   {
				   addLabel(sheet, 0, r, sdf.format(inv.getChl_date()).toString(),dash);
				   addLabel(sheet, 1, r, inv.getChl_no(),dash);
				   addLabel(sheet, 2, r, sdf.format(inv.getInv_date()).toString(),dash);
				   addLabel(sheet, 3, r, inv.getInv_no(),dash);
				   addLabel(sheet, 4, r, inv.getMac_code(),dash);
				   addLabel(sheet, 5, r, inv.getMac_name_hindi(),dash);
				   addLabel(sheet, 6, r, inv.getMcity_hindi(),dash);
				   addLabel(sheet, 7, r, inv.getMphone(),dash);
				   addLabel(sheet, 8, r, inv.getMark_no(),dash);
				   addDouble(sheet, 9, r, inv.getIssue_qty(),dash);
				   addDouble(sheet, 10, r, inv.getIssue_weight(),dash);
				   addLabel(sheet, 11, r, inv.getReceiver_name(),dash);
				   addLabel(sheet, 12, r, inv.getMobile(),dash);
				   addLabel(sheet, 13, r, inv.getPname_hindi(),dash);
				   addLabel(sheet, 14, r, inv.getVehicle_no(),dash);
				   addLabel(sheet, 15, r, inv.getRemark(),dash);
			   }
			   else if(doc_type==41)
			   {
				   addLabel(sheet, 0, r, sdf.format(inv.getInv_date()).toString(),dash);
				   addNumber(sheet, 1, r, inv.getTransfer_no(),dash);
				   addLabel(sheet, 2, r, sdf.format(inv.getChl_date()).toString(),dash);
				   addLabel(sheet, 3, r, inv.getChl_no(),dash);
				   addLabel(sheet, 4, r, inv.getMac_code(),dash);
				   addLabel(sheet, 5, r, inv.getMac_name_hindi(),dash);
				   addLabel(sheet, 6, r, inv.getMcity_hindi(),dash);
				   addLabel(sheet, 7, r, inv.getMphone(),dash);
				   addLabel(sheet, 8, r, inv.getMark_no(),dash);
				   addDouble(sheet, 9, r, inv.getIssue_qty(),dash);
				   addDouble(sheet, 10, r, inv.getIssue_weight(),dash);
				   addLabel(sheet, 11, r, inv.getFromhq(),dash);
				   addLabel(sheet, 12, r, inv.getReceiver_name(),dash);
				   addLabel(sheet, 13, r, inv.getMobile(),dash);
				   addLabel(sheet, 14, r, inv.getPname_hindi(),dash);
				   addLabel(sheet, 15, r, inv.getVehicle_no(),dash);
				   addLabel(sheet, 16, r, inv.getRemark(),dash);
			   }


		   r++;
	   }	   

	   	   dash=3;
	   	   if(doc_type==60)
	   	   {
		   addLabel(sheet, 0, r, "",dash);
		   addLabel(sheet, 1, r, "",dash);
		   addLabel(sheet, 2, r, "",dash);
		   addLabel(sheet, 3, r, "",dash);
		   addLabel(sheet, 4, r, "Grand Total",dash);
		   addLabel(sheet, 5, r, "",dash);
		   addLabel(sheet, 6, r, "",dash);
		   addLabel(sheet, 7, r, "",dash);
		   addLabel(sheet, 8, r, "",dash);
		   addLabel(sheet, 9, r, "",dash);
		   addLabel(sheet, 10, r, "",dash);
		   addLabel(sheet, 11, r, "",dash);
		   addLabel(sheet, 12, r, "",dash);
		   addLabel(sheet, 13, r, "",dash);
		   addDouble(sheet, 14, r, cnet,dash);
		   r++;
	   	   }

}



}
