package com.coldstorage.print;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import jxl.SheetSettings;

import com.coldstorage.dao.AccountDAO;
import com.coldstorage.dao.TransportDAO;
import com.coldstorage.dto.InvFstDto;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.util.WritePDF;
import com.coldstorage.view.BaseClass;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


public class OutStandingPdf  extends WritePDF
{    
   /**   
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
   int r,i,col,y;
   int dash;
  
   SimpleDateFormat sdf,sdf2;
   DecimalFormat df;
    
   String wtxt,monNm,sdate,edate, smon,inputFile,btnname,drvnm,printernm,brnm,divnm,name;
   int year,depo,div,optn,typ,rep,doc_tp,opt,repno,code;
   private Date stdt,eddt;
  
   SheetSettings settings;
   HashMap prtMap;
   private String datetime,party_name,mobile;
 
   TransportDAO pdao ;
   OutputStream emailPdfStream;
	
   Document doc;
   PdfWriter docWriter;
   PdfContentByte cb;
   InvFstDto inv1 =null;
   ArrayList<RcpDto> mis; 
   RcpDto mdt;
   TransportDto pdto = null;
   String pcity;
   boolean first=true;
   boolean more=false;
   String flnm;
	String text="";
	PdfTemplate tp=null;
   
   public OutStandingPdf(String sdate,String edate,Integer code,String party_name,Integer depo,Integer div,Integer year,String btnnm,String drvnm,String printernm,String brnm,String divnm,String opt,Integer repno,ByteArrayOutputStream outputStream)
   { 
	   
    try {
    	
    	this.r=0;
    	this.i=0;
    	this.dash=110;
    	this.depo=depo;
    	this.div=div;
    	this.repno=repno; 
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
    	this.emailPdfStream=outputStream;
    	pdao = new TransportDAO();
    	prtMap = (HashMap) pdao.getPartyNameMap(depo,div);
    	wtxt=wtxt.replace("/", "-");
    	//flnm =brnm.substring(0, 6)+"-"+wtxt+"OUT.pdf";
    	
    	sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mm-ssa");
    	flnm="LEDGER-"+sdf2.format(new Date())+".pdf";
    
     	
    		
    	
    	jbInit();
    	
        File file=null;

    			 
			if (Desktop.isDesktopSupported()) {
				file = new File(drvnm + "\\" + flnm);
				try {
					if (btnnm.equalsIgnoreCase("Print"))
						Desktop.getDesktop().print(file);
					else
						Desktop.getDesktop().open(file);
				} catch (IOException e) {

					e.printStackTrace();
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
   	   
   	       AccountDAO ms = new AccountDAO();
   	       
   	       
   	    mis =   (ArrayList<RcpDto>) ms.ledger(year,depo, div, stdt, eddt,code,opt);
   	   
 /*  	       if (repno==1)
   	    	   mis =   (ArrayList<RcpDto>) ms.getOutstanding(year,depo, div, stdt, eddt,code,opt);
   	       else if (repno==2)
   	       {
   	    	   mis =   (ArrayList<RcpDto>) ms.getOutstandingSum(year,depo, div, stdt, eddt,opt);
   	    	   //Collections.sort(mis,new CityComparator());
   	       }
   	       else if (repno==3)
   	       		mis =   (ArrayList<RcpDto>) ms.ledger(year,depo, div, stdt, eddt,code,opt);
*/
   	       mdt=null;
			  

//   	       edate=sdf.format(new Date());			 

		   createPDF();
			  
			  
  
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	
	  }

//create pdf for Email Report No pata nahi partywise discount

private void createPDF (){


	try {


		 
		df = new DecimalFormat("0.00");
//		df = new DecimalFormat("0");
		 
		 
		y = 0;
		int pg=0;
		String emailid="";;
		String code=null;
		String email="";
		more=false;
		 
		 
			generateDocument();

			
		   first=true;
		   
		   pcity="";
		   double b30=0.00;
		   double b60=0.00;
		   double b90=0.00;
		   col=0;
		   r=4;
		   y=661;
		   int dash=0;
		   
		   // detail header
		   int size=mis.size();
		   
		   for (i=0;i<size;i++)
		   {
			   mdt = (RcpDto) mis.get(i);

			   if(repno==1)
				   generateDetail1(doc, cb);
			   else if(repno==2)
				   generateDetail2(doc, cb);
			   else if(repno==3)
				   generateDetail3(doc, cb);
			   
			 if (y < 70)
			 {
	   			 LinePrint(cb, 43, 605);
				 createContent1(cb,300,30, "Page # "+(++pg),PdfContentByte.ALIGN_LEFT);
				 doc.newPage();
				 generateHeader(doc, cb);
				 y=661;
				 if(repno==3)
					 y=580;
			 }


	   }	
		   		  if (repno==1)
		   		  {
		   			  LinePrint(cb, 3, 605);
		   			  y=y-10;
		   			  createContent3(cb,410,y, mdt.getVnart1(),PdfContentByte.ALIGN_LEFT);
		   			  createContent3(cb,520,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
		   			  y=y-8;
		   			  LinePrint(cb, 3, 605);
		   			  y=y-10;
		   		  }
		   			createContent1(cb,300,30, "Page # "+(++pg),PdfContentByte.ALIGN_LEFT);
	  
					 

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

public void LinePrint(PdfContentByte cb,int start,int end )
{
	cb.moveTo(start,y);  // horizontal lines 
	cb.lineTo(end,y);
	cb.stroke();
}


private void generateDetail1(Document doc, PdfContentByte cb)  {

	try 
	{

			 if (code==0)
			 {

				 if(party_name!=null)
					 party_name=mdt.getParty_name()+"-"+mdt.getVac_code();;
				 	 mobile=mdt.getPono();
			 }		


			 pdto =(TransportDto) prtMap.get(mdt.getVac_code());

			 if(pdto!=null)
			 {
				 party_name=pdto.getMac_name().split(",")[0];
				 pcity=pdto.getCity();
				 mobile=pdto.getMobile();
			 }
			 else
			 {
				 party_name="Wrong Code "+mdt.getVac_code();
				 pcity="";
				 mobile="";

			 }

			 if(mdt.getVac_code().equals("5000"))
			 {
				 party_name=mdt.getParty_name();
				 pcity="";
				 mobile="";
			 }

			 if(first)
			 {
				 generateHeader(doc, cb);
				 first=false;
			 }


			 if(mdt.getVac_code().equals("5000"))
			 {
				 party_name=mdt.getParty_name();
				 pcity="";
				 mobile="";
			 }


			 if (mdt.getDash()==2)
			 {
				// createContent2(cb,260,y, "Mobile : "+mobile,PdfContentByte.ALIGN_LEFT);
				// createContent2(cb,450,y, mdt.getVnart1(),PdfContentByte.ALIGN_LEFT);
				// createContent2(cb,540,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
				 y=y-14;
			 }


			 if (mdt.getDash()==0 && mdt!=null )
			 {
				 createContent1(cb,10,y, pcity,PdfContentByte.ALIGN_LEFT);
				 createContent1(cb,100,y, party_name,PdfContentByte.ALIGN_LEFT);
				 createContent1(cb,260,y, sdf.format(mdt.getVou_date()).toString(),PdfContentByte.ALIGN_LEFT);
				 createContent1(cb,315,y, (mdt.getVou_lo().equals("")? "":(mdt.getVou_lo()+"-"+String.valueOf(mdt.getVou_no()))),PdfContentByte.ALIGN_LEFT);
				 createContent1(cb,370,y, String.valueOf(mdt.getSerialno()),PdfContentByte.ALIGN_RIGHT);
				 createContent1(cb,430,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);
				 createContent1(cb,485,y, String.format("%10s",df.format(mdt.getChq_amt())),PdfContentByte.ALIGN_RIGHT);
				 createContent1(cb,540,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
				 //LinePrint(cb, 540, 610);

				 y=y-14;
			 }
		 

		
	}

	catch (Exception ex){
		ex.printStackTrace();
	}

}

private void generateDetail2(Document doc, PdfContentByte cb)  
{
	try 
	{
	 
			 if(first)
			 {
				 generateHeader(doc, cb);
				 first=false;
			 }
		   
		 
		 
			 if (mdt.getDash()==2)
			 {
				 if(opt==1)
				 {
					 party_name=mdt.getParty_name().split(",")[0];
					 pcity=mdt.getParty_name().split(",")[1];
					 createContent1(cb,10,y, pcity,PdfContentByte.ALIGN_LEFT);
					 createContent1(cb,105,y, party_name,PdfContentByte.ALIGN_LEFT);
					 createContent1(cb,285,y, mdt.getPono(),PdfContentByte.ALIGN_LEFT);
					 createContent1(cb,405,y, String.format("%10s",df.format(mdt.getDiscount())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,465,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,525,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,585,y, String.format("%10s",df.format(mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
				 }
				 else
				 {
					 createContent1(cb,10,y, mdt.getParty_name(),PdfContentByte.ALIGN_LEFT);
					 createContent1(cb,355,y, String.format("%10s",df.format(mdt.getDiscount())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,415,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,475,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,535,y, String.format("%10s",df.format(mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,585,y, String.format("%10s",df.format(mdt.getDiscount()+mdt.getVamount()+mdt.getBill_amt()+mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
				 }
				 y=y-14;
			 }
			 
			 if (mdt.getDash()==4 && opt==1)
			 {
				 createContent2(cb,10,y, mdt.getVnart1(),PdfContentByte.ALIGN_LEFT);
				 createContent2(cb,405,y, String.format("%10s",df.format(mdt.getDiscount())),PdfContentByte.ALIGN_RIGHT);
				 createContent2(cb,465,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
				 createContent2(cb,525,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);
				 createContent2(cb,585,y, String.format("%10s",df.format(mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
	   			  y=y-14;
	   			 //// Final Total
	   			 
		   			 createContent3(cb,10,y, "Total   ",PdfContentByte.ALIGN_LEFT);
					 createContent3(cb,585,y, String.format("%10s",df.format((mdt.getDiscount()+mdt.getVamount()+mdt.getBill_amt()+mdt.getCr_amt()))),PdfContentByte.ALIGN_RIGHT);
					 y=y-6;
					 LinePrint(cb, 3, 605);
		   			 y=y-14;
			 }
			 
			 if (mdt.getDash()==3)
			 {
	   			  LinePrint(cb, 3, 605);
	   			  y=y-10;
				 createContent1(cb,10,y, mdt.getVnart1(),PdfContentByte.ALIGN_LEFT);
				 if(opt==1)
				 {
					 createContent1(cb,405,y, String.format("%10s",df.format(mdt.getDiscount())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,465,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,525,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,585,y, String.format("%10s",df.format(mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
				 }
				 else
				 {
					 createContent1(cb,355,y, String.format("%10s",df.format(mdt.getDiscount())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,415,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,475,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,535,y, String.format("%10s",df.format(mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
					 createContent1(cb,585,y, String.format("%10s",df.format(mdt.getDiscount()+mdt.getVamount()+mdt.getBill_amt()+mdt.getCr_amt())),PdfContentByte.ALIGN_RIGHT);
				 }
				 y=y-8;
				 
	   			  LinePrint(cb, 3, 605);
	   			  y=y-10;
	   			  
	   			  //// Final Total
	   			  
	   			 //createContent1(cb,10,y, "Total Oustanding ",PdfContentByte.ALIGN_LEFT);
				 //createContent1(cb,460,y, String.format("%10s",df.format((mdt.getDiscount()+mdt.getVamount()+mdt.getBill_amt()+mdt.getCr_amt()))),PdfContentByte.ALIGN_RIGHT);
				 //y=y-8;
	   			  //LinePrint(cb, 3, 605);
	   			  //y=y-10;
	   			  
	   			  
			 }
		 

		
		
	}catch (Exception ex)
	{
			ex.printStackTrace();
	}
	
	
}


private void generateDetail3(Document doc, PdfContentByte cb)  {

	try 
	{
		 if(more)
		 {
			 doc.newPage();
			 more=false;
		 }
		
		 if(first)
   	     {
			 
   			  pdto = (TransportDto) prtMap.get(mdt.getVac_code());
   			  if(pdto!=null)
   			  {
   				  party_name=pdto.getTran_name()+"-"+pdto.getAccount_no();;;
   			  }
  			  else
   			  {
   				  party_name="";
   			  }
   			 
			  y=580;
			  generateHeader(doc, cb);
			  first=false;
			  
    	    }
		 
	      if (mdt.getDash()==1)  	  
	      {
     	        createContent11(cb,185,y,mdt.getVnart1(),PdfContentByte.ALIGN_LEFT,9);
				createContent11(cb,445,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT,10);
				createContent11(cb,515,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT,10);
				createContent11(cb,600,y, String.format("%10s",df.format(mdt.getAdvance()))+" "+mdt.getVdbcr(),PdfContentByte.ALIGN_RIGHT,10);
			    y=y-14;
	      }


	      if (mdt.getDash()==0)  	  
	      {
	    	  
	    	  createContent11(cb,40,y,("  "+mdt.getVou_lo()),PdfContentByte.ALIGN_LEFT,10);
    		  createContent11(cb,90,y,(mdt.getRcp_no()),PdfContentByte.ALIGN_LEFT,9);

	    	  if(mdt.getVou_date()!=null)
	    		  createContent11(cb,125,y,sdf.format(mdt.getVou_date()),PdfContentByte.ALIGN_LEFT,10);
	    	  

				tp=createImage(mdt.getVnart1(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 185, (y-5));

/*	    	  if (mdt.getVnart1()!=null && mdt.getVnart1().length()>0)
	    		  createContent11(cb,185,y,mdt.getVnart1(),PdfContentByte.ALIGN_LEFT,9);
	    	  else
	    	  {
	    		  createContent11(cb,185,y,mdt.getVnart2(),PdfContentByte.ALIGN_LEFT,9);
	    		  mdt.setVnart2(null);
	    	  }
*/
	    	  createContent11(cb,445,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT,10);
	    	  createContent11(cb,515,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT,10);
	    	  createContent11(cb,600,y, String.format("%10s",df.format(mdt.getAdvance()))+" "+mdt.getVdbcr(),PdfContentByte.ALIGN_RIGHT,10);
	    	  y=y-14;

	    	  if (mdt.getVnart2()!=null && mdt.getVnart2().length()>0)
	    	  {
	    		  createContent11(cb,185,y,mdt.getVnart2(),PdfContentByte.ALIGN_LEFT,9);
	    		  y=y-14;
	    	  }
	      }

	      if (mdt.getDash()==2)  	  
	      {
	    	  
	    	  LinePrint(cb, 43, 605);
   			  y=y-10;
			  createContent11(cb,185,y, mdt.getVnart1(),PdfContentByte.ALIGN_LEFT,10);
			  createContent11(cb,445,y, String.format("%10s",df.format(mdt.getVamount())),PdfContentByte.ALIGN_RIGHT,10);
			  createContent11(cb,515,y, String.format("%10s",df.format(mdt.getBill_amt())),PdfContentByte.ALIGN_RIGHT,10);
			  createContent11(cb,600,y, String.format("%10s",df.format(mdt.getAdvance()))+" "+mdt.getVdbcr(),PdfContentByte.ALIGN_RIGHT,10);
			  y=y-8;
   			  LinePrint(cb, 43, 605);
   			  y=y-10;
   			  
   			  more=true;
	          first=true;
	         
	          
	      }



		
	}

	catch (Exception ex){
		ex.printStackTrace();
	}

}



private void generateHeader(Document doc, PdfContentByte cb)  {
	try 
	{
		if (repno==1)
			 generateHeader1(doc, cb);
		else if (repno==2)
			 generateHeader2(doc, cb);
		else if (repno==3)
			 generateHeader3(doc, cb);
		

	}

	catch (Exception ex){
		ex.printStackTrace();
	}

}

private void generateHeader1(Document doc, PdfContentByte cb)  {

	try 
	{
		//add the images
		//Image companyLogo = Image.getInstance(getClass().getResource("/images/aris_log.png"));

		redtext(cb,200,732,brnm);

		cb.setLineWidth(1f);

			cb.moveTo(3,700); // DESCRIPTION : Vertical lines
			cb.lineTo(605,700);
			cb.stroke();
			cb.moveTo(3,675); // DESCRIPTION : Vertical lines
			cb.lineTo(605,675);
			cb.stroke();

			if (opt==1)
			 createHeadings1(cb,225,712,"OUTSTANDING OF "+party_name+" AS ON - "+edate);
			else
			 {
			 createHeadings1(cb,10,705,wtxt);
			 createHeadings1(cb,225,712,"OUTSTANDING AS ON  - "+edate);
			 }
			 createHeadings(cb,10,688,"City");
			createHeadings(cb,100,688,"Party Name");
			createHeadings(cb,260,688,"Date");
			createHeadings(cb,315,688,"Bill");
			createHeadings(cb,355,688,"Delay");
			createHeadings(cb,400,688,"Bill");
			createHeadings(cb,450,688,"Payment");
			createHeadings(cb,510,688,"Diff");
			createHeadings(cb,560,688,"Remark");

			
			createHeadings(cb,315,680,"No");
			createHeadings(cb,355,680,"Days");
			createHeadings(cb,400,680,"Amount");
			createHeadings(cb,450,680,"Amount");
			createHeadings(cb,510,680,"Amount");

	}

	catch (Exception ex){
		ex.printStackTrace();
	}

}


private void generateHeader2(Document doc, PdfContentByte cb)  {

	try 
	{
		//add the images
	//	Image companyLogo = Image.getInstance(getClass().getResource("/images/aris_log.png"));

		redtext(cb,200,732,brnm);

		cb.setLineWidth(1f);

			cb.moveTo(3,700); // DESCRIPTION : Vertical lines
			cb.lineTo(605,700);
			cb.stroke();
			cb.moveTo(3,675); // DESCRIPTION : Vertical lines
			cb.lineTo(605,675);
			cb.stroke();
              if(opt==1)
            	  wtxt="PARTY WISE";
              else if(opt==3)
            	  wtxt="AREA WISE";
              else if(opt==4)
            	  wtxt="TERRITORY WISE";
			 //createHeadings1(cb,10,705,wtxt);
			 createHeadings1(cb,200,717,wtxt+" OUTSTANDING SUMMERY AS ON  - "+edate);
	
			if (opt==1)
			{
				createHeadings(cb,10,688,"City");
				createHeadings(cb,105,688,"Name");
				createHeadings(cb,285,688,"Mobile");
				createHeadings(cb,375,688,"30 Days");
				createHeadings(cb,435,688,"31-60 Days");
				createHeadings(cb,495,688,"61-120 Days");
				createHeadings(cb,555,688,"Above 120");
				createHeadings(cb,560,678," Days");

			}
			else
			{
				createHeadings(cb,10,688,"Name");
				createHeadings(cb,335,688,"0-30 ");
				createHeadings(cb,395,688,"31-60");
				createHeadings(cb,452,688,"61-120");
				createHeadings(cb,497,688,"Above 120 ");
				createHeadings(cb,565,688,"Total");
				createHeadings(cb,335,678,"Days");
				createHeadings(cb,395,678,"Days");
				createHeadings(cb,455,678,"Days");
				createHeadings(cb,510,678," Days");
			}

			

	}

	catch (Exception ex){
		ex.printStackTrace();
	}

}


private void generateHeader3(Document doc, PdfContentByte cb)  {

	try 
	{
		//add the images
		//Image companyLogo = Image.getInstance(getClass().getResource("/images/aris_log.png"));

//		redtext(cb,150,722,brnm);

		text="";
		tp=null;
	
		text="श्री लक्ष्मी आईस एण्ड  कोल्ड स्टोरेज प्रा.लि.";
		tp=createImage(text, cb,20,Color.BLUE);
		cb.addTemplate(tp, 160, 765);

		text="ए.बी. रोड, डोंगर गांव, महू, फोन : 271326, Mob.: 9826065606";
		tp=createImage(text, cb,10,Color.BLUE);
		cb.addTemplate(tp, 190, 750);

		text="रजि. ऑफिस : 1418, पतीबाज़ार , महू , मो : S. K.  - 9826075812  , D. K.  - 9977227016 ";
		tp=createImage(text, cb,10,Color.BLUE);
		cb.addTemplate(tp, 120, 735);

		
		cb.setLineWidth(1f);

		cb.moveTo(40,615); // DESCRIPTION : Vertical lines
		cb.lineTo(605,615);
		cb.moveTo(40,590); // DESCRIPTION : Vertical lines
		cb.lineTo(605,590);
		cb.stroke();

			
			 createHeadings1(cb,180,710,"Account Statement From "+sdate+" - "+edate);
/*			 if(pdto!=null)
		     {
		    	 createHeadings1(cb,60,685,party_name);
		    	 createHeadings1(cb,60,673,pdto.getMadd1());
		    	 createHeadings1(cb,60,661,pdto.getMadd2());

		    	 createHeadings1(cb,60,649,pdto.getCity());
//		    	 
//		    	 createHeadings1(cb,60,637,pdto.getMemail());
		    	 createHeadings1(cb,60,625,pdto.getMobile());
//		    	 createHeadings1(cb,460,625,"GST No. "+pdto.getGst_no());

		     }
		     else
		     {
			   createHeadings1(cb,60,685,party_name);
		     }
			 if(opt>1)
			 {
				 createHeadings1(cb,520,635,wtxt);
			 }
*/	
				text="खाता क्र.:";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 40, 665);
				tp=createImage(String.valueOf(pdto.getAccount_no()), cb,14,Color.BLACK);
				cb.addTemplate(tp, 80, 665);

				tp=createImage(pdto.getMac_name_hindi(), cb,14,Color.BLACK);
				cb.addTemplate(tp, 40, 650);
				tp=createImage(pdto.getMcity_hindi(), cb,14,Color.BLACK);
				cb.addTemplate(tp, 40, 635);

				text="Mobile:";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 40, 615);
				tp=createImage(pdto.getMobile(), cb,14,Color.BLACK);
				cb.addTemplate(tp, 80, 615);

			 
				createHeadings11(cb,40,600,"Type",10);
				createHeadings11(cb,90,600,"No",10);
				createHeadings11(cb,125,600,"Date",10);
				createHeadings11(cb,190,600,"Particulars",10);
				createHeadings11(cb,418,600," Debit",10);
				createHeadings11(cb,482,600,"Credit",10);
				createHeadings11(cb,545,600,"Balance",10);
				
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
		PdfWriter docWriter = null;
		initializeFonts();

		String path = drvnm+"\\"+flnm;
		doc = new Document();
/*		if(btnname.equalsIgnoreCase("pdf") || btnname.equalsIgnoreCase("print"))
			docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));
		else
		{
//			emailPdfStream = new ByteArrayOutputStream();
			docWriter = PdfWriter.getInstance(doc , emailPdfStream);
		}
*/		
		if(emailPdfStream==null && (btnname.equalsIgnoreCase("view") || btnname.equalsIgnoreCase("pdf") || btnname.equalsIgnoreCase("print")))
		{
			docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));
		}
		else // generating outputstream for emaill attach
		{
			docWriter = PdfWriter.getInstance(doc , emailPdfStream);
		}

		
		
		doc.addAuthor("Nakoda");
		doc.addCreationDate();
		doc.addProducer();
		doc.addCreator("nakoda.com");
		doc.addTitle("Report");
		doc.setPageSize(PageSize.LETTER);

		doc.open();
		cb = docWriter.getDirectContent();

	} catch (Exception e) {
		e.printStackTrace();
	}

	

	
}

public class CityComparator implements Comparator<RcpDto> {
	public int compare(RcpDto p1, RcpDto p2) {
		     return p1.getCity().compareTo(p2.getCity());
			//return (p1.getNet_level()>p2.getNet_level()) ? -1 : (p1.getNet_level()<p2.getNet_level()) ? 1 : 0;
	}
}

private PdfTemplate createImage(String text,PdfContentByte cb,int fontsize,Color color)
{
	
	   PdfTemplate tp = cb.createTemplate(440, 400);
	 
	   Graphics2D g2 = tp.createGraphicsShapes(595, 50);
	   g2.setColor(color);
	   g2.setFont(new Font(BaseFont.CP1252, Font.PLAIN, fontsize));
	   g2.drawString(text, 0, 45);
	   g2.dispose();
	   
	   
	   return tp;

}



}

