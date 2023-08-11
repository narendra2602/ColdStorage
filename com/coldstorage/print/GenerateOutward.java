package com.coldstorage.print;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.coldstorage.dao.CmpMsflDAO;
import com.coldstorage.dao.InvPrintDAO;
import com.coldstorage.dto.CmpMsflDto;
import com.coldstorage.dto.InvViewDto;
import com.coldstorage.util.FigToWord;
import com.coldstorage.util.WritePDF;
import com.coldstorage.view.BaseClass;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateOutward extends WritePDF
{
   

	FigToWord fg; 
	ArrayList<?> invList;
	String s="";
	int csize;
	InvViewDto vd;
	CmpMsflDAO cmpd ;
	CmpMsflDto cmpDto;
	private Vector<?> col;
    SimpleDateFormat sdf,sdf2;

	DecimalFormat df = null;
	private String word;
	Calendar cal;
	InvViewDto ic=null;
	private String btnname,drvnm,remark,pdfFilename,einv; 
	int year,depo,div,sinv,doc_tp,mncode,doctp,bunch;
 	Date sdate=null;
 	double sgstamt,cgstamt,igstamt,taxable;
 	int y=0;
	String text="";
	PdfTemplate tp=null;
	InvViewDto in=null;
	private boolean second=true;
	private int row,y2,printsize;
	public GenerateOutward()
	{
		
	}
	public GenerateOutward (String year,String depo,String div,String sinv,String einv,String btnnm,String drvnm,String printernm,String optn,Integer mncode,Integer doc_tp,String brnnm,String divnm,OutputStream emailPdfStream,Integer bunch)	
	{
		this.sinv=Integer.parseInt(sinv.trim());
		this.einv=einv;
		this.depo=Integer.parseInt(depo);
		this.div=Integer.parseInt(div);
		this.year=Integer.parseInt(year);
		this.bunch=bunch;
		this.mncode=mncode;
		this.btnname=btnnm;
		this.drvnm=drvnm;
		this.remark=printernm;
		this.doctp=doc_tp;
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mma");
    	pdfFilename=divnm+"-OUTWARD #"+sinv+" - "+einv+" "+sdf2.format(new Date())+".pdf";
    	

    	
    	createPDF(pdfFilename,emailPdfStream);
		///// View Report on screen
		File file=null;

		if(emailPdfStream==null)
		{
			if (Desktop.isDesktopSupported()) {
				file=new File(drvnm+"\\"+ pdfFilename);
				try 
				{
					if (btnnm.equalsIgnoreCase("Print"))
						Desktop.getDesktop().print(file);
					else
						Desktop.getDesktop().open(file);
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		}

	}

	
	public void GenerateEmailInvoice (String year,String depo,String div,String sinv,String einv,String btnnm,String drvnm,String printernm,String optn,Integer mncode,Integer doc_tp,String brnnm,String divnm,OutputStream emailPdfStream,Integer bunch)	
	{
		this.sinv=Integer.parseInt(sinv.trim());
		this.einv=einv;
		this.depo=Integer.parseInt(depo);
		this.div=Integer.parseInt(div);
		this.year=Integer.parseInt(year);
		this.mncode=mncode;
		this.btnname=btnnm;
		this.drvnm=drvnm;
		this.remark=printernm;
		this.doctp=doc_tp;
		this.bunch=bunch;
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf2 = new SimpleDateFormat("dd-MM-yy_HH-mma");
    	pdfFilename=divnm+"-INV #"+sinv+" - "+einv+" "+sdf2.format(new Date())+".pdf";

		//		GenerateInvoice generateInvoice = new GenerateInvoice();

    	createPDF(pdfFilename,emailPdfStream);
		///// View Report on screen
		File file=null;

		if(emailPdfStream==null)
		{
			if (Desktop.isDesktopSupported()) {
				file=new File(drvnm+"\\"+ pdfFilename);
				try 
				{
					Desktop.getDesktop().open(file);
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		}

	}

	
	private void createPDF (String pdfFilename,OutputStream emailPdfStream){

		Document doc = new Document();
		PdfWriter docWriter = null;
		initializeFonts();
//		btnname="pdf";	
//		drvnm="c:\\Report";
		try {
			String path = drvnm+"\\"+ pdfFilename;
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
			doc.addCreator("prompt");
			doc.addTitle("Invoice");
			doc.setPageSize(PageSize.LETTER);
			//doc.setMargins(0.5f, 0.5f, 0.5f, 0.5f);
			 
			
			doc.open();
			PdfContentByte cb = docWriter.getDirectContent();
			
			fg = new FigToWord();
			cmpd = new CmpMsflDAO();
			cmpDto = (CmpMsflDto) (cmpd.getCompInfo(depo));
			 
			
			InvPrintDAO inv = new InvPrintDAO();
			int jj=0;


			invList= (ArrayList<?>) inv.getInwardDetail(0,einv, year,doctp,BaseClass.loginDt.getDiv_code());

			
			int lsize=invList.size();

			
			System.out.println("SIZE IS "+lsize);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			df = new DecimalFormat("0.00");
			 
			boolean beginPage = true;
			boolean first=true;
			
//			doc.newPage();
			y2=0;
			first=true;
			printsize=1;
			int loop=1;
			int w=0;
			if(lsize>7)
			{
				printsize=2;
				loop=2;
			}
			for(w=0; w<loop;w++)
			{
				doc.newPage();
				beginPage = true;
				for(int i=0; i < lsize; i++ )
				{
					in= (InvViewDto) invList.get(i);

					if(beginPage)
					{
						beginPage = false;
						generateLayout(doc, cb); 
						generateHeader(doc, cb);
						generateFooter(doc, cb);
						y = 635; 
						y2=230;
					}

					generateDetail(doc, cb, i, y);
					y = y -11;
					y2 = y2 -11;

				} // end of product for loop 
			}
				cb.stroke();

				
				

		}
		catch (DocumentException dex)
		{
			dex.printStackTrace();
		}
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


		private void generateLayout(Document doc, PdfContentByte cb)  {

		try {

			cb.setLineWidth(1f);
			cb.stroke();
			
			if(printsize==2)  // full length
			{

				// squre second part
/*				cb.moveTo(20,10);   // BOTTOM LINE HORIZONTAL
				cb.lineTo(680,10);
				cb.moveTo(20,110);
				cb.lineTo(20,10);
				cb.moveTo(680,110);
				cb.lineTo(680,10);
*/
				cb.moveTo(20,10);   // BOTTOM LINE HORIZONTAL
				cb.lineTo(590,10);
				cb.moveTo(20,680);
				cb.lineTo(20,10);
				cb.moveTo(590,680);
				cb.lineTo(590,10);


				
/*				cb.moveTo(20,270);  // horizontal lines at the bottom
				cb.lineTo(590,270);
*/

				
				cb.moveTo(20,680);  // horizontal lines at the bottom
				cb.lineTo(590,680);

				// FIRST PART HORIZONTAL LINE AT END OF VERTICAL LINE


//				cb.moveTo(20,505);  // horizontal lines at the bottom
//				cb.lineTo(590,505);

				// FIRST PART PART VERTICAL LINE

				cb.moveTo(290,680);
				cb.lineTo(290,110);
				cb.moveTo(360,680);
				cb.lineTo(360,110);
				cb.moveTo(410,680);
				cb.lineTo(410,110);
				cb.moveTo(480,680);
				cb.lineTo(480,110);
				cb.moveTo(530,680);
				cb.lineTo(530,110);

				cb.moveTo(20,110);  // horizontal lines at the bottom
				cb.lineTo(590,110);

				cb.moveTo(20,70);
				cb.lineTo(590,70);

				


				cb.moveTo(20,655);   // DESCRIPTION HEADING KE BADD horizontal line
				cb.lineTo(590,655);

				cb.stroke();

				text="खातेदार  का नाम  ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 25, 660);

				text="मार्का ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 310, 660);
				text="मंज़िल ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 370, 660);

				text="गाला  ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 430, 660);


				text="कट्टे  ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 490, 660);

				text="वजन   ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 550, 660);
				text="व्यापारी का नाम   :";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 25, 90);

				tp=createImage(in.getReceiver_name(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 110, 90);

				text="मो .:";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 275, 90);

				tp=createImage(in.getMobile(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 300, 90);


				text="वाहन  क्रमांक :";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 400, 90 );

				tp=createImage(in.getVehicle_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 475, 90);



				text="रिमार्क  :";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 25, 70);

				tp=createImage(in.getRemark(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 60, 70);


			}
			
			
			if(printsize==1)
			{
				// squre first part
				cb.moveTo(20,410);   // BOTTOM LINE HORIZONTAL
				cb.lineTo(590,410);
				cb.moveTo(20,680);
				cb.lineTo(20,410);
				cb.moveTo(590,680);
				cb.lineTo(590,410);

				// squre second part
				cb.moveTo(20,10);   // BOTTOM LINE HORIZONTAL
				cb.lineTo(590,10);
				cb.moveTo(20,270);
				cb.lineTo(20,10);
				cb.moveTo(590,270);
				cb.lineTo(590,10);

				cb.moveTo(20,270);  // horizontal lines at the bottom
				cb.lineTo(590,270);



				// SECOND PART VERTICAL LINE

				cb.moveTo(290,270);
				cb.lineTo(290,110);
				cb.moveTo(360,270);
				cb.lineTo(360,110);
				cb.moveTo(410,270);
				cb.lineTo(410,110);
				cb.moveTo(480,270);
				cb.lineTo(480,110);
				cb.moveTo(530,270);
				cb.lineTo(530,110);


				// SECOND PART HORIZONTAL LINE AT END OF VERTICAL LINE
				cb.moveTo(20,110);  // horizontal lines at the bottom
				cb.lineTo(590,110);

				cb.moveTo(20,70);
				cb.lineTo(590,70);

				cb.moveTo(20,245);   // DESCRIPTION HEADING KE BADD
				cb.lineTo(590,245);



				//////  second part end


				cb.moveTo(5,395);  // partion line horizontal lines at the middle
				cb.lineTo(610,395);


				cb.moveTo(20,680);  // horizontal lines at the bottom
				cb.lineTo(590,680);

				// FIRST PART HORIZONTAL LINE AT END OF VERTICAL LINE


				cb.moveTo(20,505);  // horizontal lines at the bottom
				cb.lineTo(590,505);

				// FIRST PART PART VERTICAL LINE

				cb.moveTo(290,680);
				cb.lineTo(290,505);
				cb.moveTo(360,680);
				cb.lineTo(360,505);
				cb.moveTo(410,680);
				cb.lineTo(410,505);
				cb.moveTo(480,680);
				cb.lineTo(480,505);
				cb.moveTo(530,680);
				cb.lineTo(530,505);

				cb.moveTo(20,470);
				cb.lineTo(590,470);

				cb.moveTo(20,655);   // DESCRIPTION HEADING KE BADD
				cb.lineTo(590,655);



				cb.stroke();

				text="खातेदार  का नाम  ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 25, 660);

				text="मार्का ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 310, 660);
				text="मंज़िल ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 370, 660);

				text="गाला  ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 430, 660);


				text="कट्टे  ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 490, 660);

				text="वजन   ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 550, 660);

				text="व्यापारी का नाम   :";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 25, 485);

				tp=createImage(in.getReceiver_name(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 110, 485);


				text="मो .:";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 275, 485 );

				tp=createImage(in.getMobile(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 300, 485);

				text="वाहन  क्रमांक :";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 400, 485 );

				tp=createImage(in.getVehicle_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 475, 485);


				text="रिमार्क  :";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 25, 470);

				tp=createImage(in.getRemark(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 60, 470);

				// second part
				text="खातेदार  का नाम ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 25, 250);

				text="मार्का ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 310, 250);
				text="मंज़िल ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 370, 250);

				text="गाला  ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 430, 250);


				text="कट्टे  ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 490, 250);

				text="वजन   ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 550, 250);

				text="व्यापारी का नाम   :";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 25, 90);

				tp=createImage(in.getReceiver_name(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 110, 90);

				text="मो .:";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 275, 90);

				tp=createImage(in.getMobile(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 300, 90);


				text="वाहन  क्रमांक :";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 400, 90 );

				tp=createImage(in.getVehicle_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 475, 90);



				text="रिमार्क  :";
				tp=createImage(text, cb,12,Color.BLACK);
				cb.addTemplate(tp, 25, 70);

				tp=createImage(in.getRemark(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 60, 70);

			}
			//add the images
			Image companyLogo = Image.getInstance(getClass().getResource("/images/inv.png"));
			
			
		}

		catch (DocumentException dex){
			dex.printStackTrace();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

	}

	private void generateHeader(Document doc, PdfContentByte cb)  {

		try 
		{
			
				// first part
				String text="";
				PdfTemplate tp=null;
			
				text="श्री लक्ष्मी आईस एण्ड  कोल्ड स्टोरेज प्रा.लि.";
				tp=createImage(text, cb,20,Color.BLUE);
				cb.addTemplate(tp, 160, 765);

				text="ए.बी. रोड, डोंगर गांव, महू, फोन : 271326, Mob.: 9826065606";
				tp=createImage(text, cb,10,Color.BLUE);
				cb.addTemplate(tp, 190, 750);

				text="रजि. ऑफिस : 1418, पतीबाज़ार , महू , मो : S. K.  - 9826075812  , D. K.  - 9977227016 ";
				tp=createImage(text, cb,10,Color.BLUE);
				cb.addTemplate(tp, 120, 735);

				text=in.getChl_no()+" जावक चिट्टी  ";
				tp=createImage(text, cb,15,Color.BLACK);
				cb.addTemplate(tp, 225, 705);

				text="रसीद क्र.:";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 25, 680);
				createtext2(cb,70,685,in.getPinv_no());

				text=" दिनांक:";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 470, 680);
				createtext2(cb,514,685,sdf.format(in.getPinv_date()));

				// second part
			
				if(printsize==1)
				{
					text="श्री लक्ष्मी आईस एण्ड  कोल्ड स्टोरेज प्रा.लि.";
					tp=createImage(text, cb,20,Color.BLUE);
					cb.addTemplate(tp, 160, 355);

					text="ए.बी. रोड, डोंगर गांव, महू, फोन : 271326, Mob.: 9826065606";
					tp=createImage(text, cb,10,Color.BLUE);
					cb.addTemplate(tp, 190, 340);

					text="रजि. ऑफिस : 1418, पतीबाज़ार , महू , मो : S. K.  - 9826075812  , D. K.  - 9977227016 ";
					tp=createImage(text, cb,10,Color.BLUE);
					cb.addTemplate(tp, 120, 325);

					text=in.getChl_no()+" जावक गेट पास ";
					tp=createImage(text, cb,15,Color.BLACK);
					cb.addTemplate(tp, 225, 295);

					text="रसीद क्र.:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 25, 270);
					createtext2(cb,70,275,in.getPinv_no());

					text=" दिनांक:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 470, 270);
					createtext2(cb,514,275,sdf.format(in.getPinv_date()));
				}
		
		}

		catch (Exception ex){
			ex.printStackTrace();
		}

	}

	
	private void generateFooter(Document doc, PdfContentByte cb)  {

		try 
		{

			if(printsize==1)
			{
				// first part
				text="प्राप्तकर्ता के हस्ताक्षर       ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 22, 420);

				text="व्ययस्थापक       ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 522, 420);
			}

			// second part
			text="प्राप्तकर्ता के हस्ताक्षर       ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 22, 20);

			text="व्ययस्थापक       ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 522, 20);
			
//			createHeadings(cb,250,10,"This is a computer generated document.");  
			
			
		}

		catch (Exception ex){
			ex.printStackTrace();
		}

	}	
	
	
private void generateDetail(Document doc, PdfContentByte cb, int index, int y1)  {
		

		try {

			if(printsize==2)
			{
				if(second)
				{
					text="खाता क्र.:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 22, y);

					tp=createImage(in.getMac_code(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 60, y);

					tp=createImage(in.getMac_name_hindi()+","+in.getMcity_hindi(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 80, y);
					second=false;
				}
				if(in.getGroup_name_hindi().equalsIgnoreCase(in.getPname_hindi()))
				{
					tp=createImage(in.getPname_hindi(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 200, y);
				}
				else
				{
					tp=createImage(in.getPname(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 200, y);

				}

				tp=createImage(in.getMark_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 290, y);

				tp=createImage(in.getFloor_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 380, y);

				tp=createImage(in.getBlock_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 412, y);


				tp=createImage(in.getPack(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 490, y);


				tp=createImage(String.valueOf(in.getNumword()), cb,10,Color.BLACK);
				cb.addTemplate(tp, 550, y);
				y=y-11;
				
			}
			if(printsize==1)
			{
				if(second)
				{
					text="खाता क्र.:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 22, y);
					cb.addTemplate(tp, 22, y2);

					tp=createImage(in.getMac_code(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 60, y);
					cb.addTemplate(tp, 60, y2);

					tp=createImage(in.getMac_name_hindi()+","+in.getMcity_hindi(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 80, y);
					cb.addTemplate(tp, 80, y2);
					second=false;
				}
				if(in.getGroup_name_hindi().equalsIgnoreCase(in.getPname_hindi()))
				{
					tp=createImage(in.getPname_hindi(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 200, y);
					cb.addTemplate(tp, 200, y2);
				}
				else
				{
					tp=createImage(in.getPname(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 200, y);
					cb.addTemplate(tp, 200, y2);

				}

				tp=createImage(in.getMark_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 290, y);
				cb.addTemplate(tp, 290, y2);

				tp=createImage(in.getFloor_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 380, y);
				cb.addTemplate(tp, 380, y2);

				tp=createImage(in.getBlock_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 412, y);
				cb.addTemplate(tp, 412, y2);


				tp=createImage(in.getPack(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 490, y);
				cb.addTemplate(tp, 490, y2);


				tp=createImage(String.valueOf(in.getNumword()), cb,10,Color.BLACK);
				cb.addTemplate(tp, 550, y);
				cb.addTemplate(tp, 550, y2);
				y=y-11;
				y2=y2-11;
			}

		}

		catch (Exception ex){
			ex.printStackTrace();
		}

	}

	
	
	public int setIntNumber(String s){   
		int i = 0;
		try{
			i= Integer.parseInt(s);

		}
		catch(Exception e){
			i=0;
			System.out.println("error hai"+e);
		}

		return i;
	}

	
	public double setDoubleNumber(String s){   
		double  i = 0;
		try{
			i= Double.parseDouble(s);

		}
		catch(Exception e){
			i=0;
			System.out.println("error hai"+e);
		}

		return i;
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

 /*     public static void main(String[] args) {
		new GenerateOutward().createPDF ("inv.pdf",null);
      }	*/
}