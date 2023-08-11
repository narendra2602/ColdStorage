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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateReceipt extends WritePDF
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
	private int row,y2;
	public GenerateReceipt()
	{
		
	}
	public GenerateReceipt (String year,String depo,String div,String sinv,String einv,String btnnm,String drvnm,String printernm,String optn,Integer mncode,Integer doc_tp,String brnnm,String divnm,OutputStream emailPdfStream,Integer bunch)	
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
    	pdfFilename=divnm+"-RECEIPT #"+sinv+" - "+einv+" "+sdf2.format(new Date())+".pdf";
    	

    	
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


			invList= (ArrayList<?>) inv.getRecieptDetail(depo,div,sinv, year,doctp);

			
			int lsize=invList.size();

			
			System.out.println("SIZE IS "+lsize);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			df = new DecimalFormat("0.00");
			 
			boolean beginPage = true;
			boolean first=true;
			
			doc.newPage();
			y2=0;
			first=true;
			for(int i=0; i < lsize; i++ )
			{
				in= (InvViewDto) invList.get(i);

				if(beginPage)
				{
					beginPage = false;
					generateLayout(doc, cb); 
					generateHeader(doc, cb);
					generateFooter(doc, cb);
					y = 625; 
					y2=220;
				}

				generateDetail(doc, cb, i, y);
				y = y -11;
				y2 = y2 -11;

			} // end of product for loop 

		    word = fg.numWord(in.getNet_amt());


		    tp=createImage(word, cb,14,Color.BLACK);
			cb.addTemplate(tp, 22 , 100);
			cb.addTemplate(tp, 22 , 510);

/*			 Translator http = new Translator();
			  String wordnew = http.callUrlAndParseResult("en", "hi",word);
			   
			   PdfTemplate tp = cb.createTemplate(200, 50);
			   
			   Graphics2D g2 = tp.createGraphicsShapes(100, 50);
			   g2.drawString(wordnew, 0, 40);
			   g2.dispose();
			   cb.addTemplate(tp, 22, 540);
*/		    
			  
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

			
 			cb.moveTo(20,130);  // horizontal lines at the bottom
			cb.lineTo(590,130);

			
			cb.moveTo(420,270);
			cb.lineTo(420,130);
			cb.moveTo(500,270);
			cb.lineTo(500,130);
			
/*			cb.moveTo(20,80);
			cb.lineTo(590,80);
*/
			cb.moveTo(20,245);   // DESCRIPTION HEADING KE BADD
			cb.lineTo(590,245);

			
			
			//////  second part end

			
 			cb.moveTo(5,395);  // partion line horizontal lines at the middle
			cb.lineTo(610,395);

			
 			cb.moveTo(20,680);  // horizontal lines at the bottom
			cb.lineTo(590,680);

			
 			cb.moveTo(20,540);  // horizontal lines at the bottom
			cb.lineTo(590,540);

			
			
			cb.moveTo(420,680);
			cb.lineTo(420,540);
			cb.moveTo(500,680);
			cb.lineTo(500,540);
			
/*			cb.moveTo(20,490);
			cb.lineTo(590,490);
*/
			cb.moveTo(20,655);   // DESCRIPTION HEADING KE BADD
			cb.lineTo(590,655);
			

			
			cb.stroke();
			
			text="खातेदार  का नाम  ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 102, 660);

			text="विगत   ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 435, 660);

			text="रुपए    ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 530, 660);

			// second part
			text="खातेदार  का नाम  ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 102, 250);

			text="विगत  ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 435, 250);

			text="रुपए  ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 530, 250);


/*			text="खातेदार  का नाम एवं पार्टी का मार्का ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 102, 660);

			text="कट्टे  ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 435, 660);

			text="वजन   ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 530, 660);

			text="व्यापारी का नाम   :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 560);

			tp=createImage(in.getReceiver_name(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 560);

			text="मो .:";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 545 );

			tp=createImage(in.getMobile(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 545);


			text="माल  :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 530 );

			tp=createImage(in.getGroup_name_hindi(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 530);

			
			text="वाहन  क्रमांक :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 515 );

			tp=createImage(in.getVehicle_no(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 515);
			
			
			text="रिमार्क  :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 500);

			tp=createImage(in.getRemark(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 500);

			// second part
			text="खातेदार  का नाम एवं पार्टी का मार्का ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 102, 250);

			text="कट्टे  ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 435, 250);

			text="वजन   ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 530, 250);

			text="व्यापारी का नाम   :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 150);

			tp=createImage(in.getReceiver_name(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 150);

			text="मो .:";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 135 );

			tp=createImage(in.getMobile(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 135);


			text="माल  :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 120 );

			tp=createImage(in.getGroup_name_hindi(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 120);

			
			text="वाहन  क्रमांक :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 105 );

			tp=createImage(in.getVehicle_no(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 105);
			
			
			text="रिमार्क  :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 90);

			tp=createImage(in.getRemark(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 100, 90);

*/			
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

				text=" भाड़ा  जमा  रसीद  ";
				tp=createImage(text, cb,15,Color.BLACK);
				cb.addTemplate(tp, 225, 705);

				text="रसीद क्र.:";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 25, 680);
				createtext2(cb,70,685,in.getInv_no());

				text=" दिनांक:";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 470, 680);
				createtext2(cb,514,685,sdf.format(in.getInv_date()));

				// second part
			
				text="श्री लक्ष्मी आईस एण्ड  कोल्ड स्टोरेज प्रा.लि.";
				tp=createImage(text, cb,20,Color.BLUE);
				cb.addTemplate(tp, 160, 355);

				text="ए.बी. रोड, डोंगर गांव, महू, फोन : 271326, Mob.: 9826065606";
				tp=createImage(text, cb,10,Color.BLUE);
				cb.addTemplate(tp, 190, 340);

				text="रजि. ऑफिस : 1418, पतीबाज़ार , महू , मो : S. K.  - 9826075812  , D. K.  - 9977227016 ";
				tp=createImage(text, cb,10,Color.BLUE);
				cb.addTemplate(tp, 120, 325);

				text=" भाड़ा  जमा  रसीद  ";
				tp=createImage(text, cb,15,Color.BLACK);
				cb.addTemplate(tp, 225, 295);

				text="रसीद क्र.:";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 25, 270);
				createtext2(cb,70,275,in.getInv_no());

				text=" दिनांक:";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 470, 270);
				createtext2(cb,514,275,sdf.format(in.getInv_date()));
	 
		
		}

		catch (Exception ex){
			ex.printStackTrace();
		}

	}

	
	private void generateFooter(Document doc, PdfContentByte cb)  {

		try 
		{

			// first part

			text=in.getAproval_no();
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 22, 460);
			cb.addTemplate(tp, 22, 60);

			
			text=in.getReceiver_name();
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 500, 460);
			cb.addTemplate(tp, 500, 60);

			text="हस्त  के हस्ताक्षर";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 20, 420);

			text="प्राप्तकर्ता के हस्ताक्षर       ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 512, 420);

/*			text="व्ययस्थापक       ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 522, 420);

*/			// second part
			text="हस्त  के हस्ताक्षर";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 22, 20);

			text="प्राप्तकर्ता के हस्ताक्षर       ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 512, 20);

			
/*			text="व्ययस्थापक       ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 522, 20);
			
*/			
//			createHeadings(cb,250,10,"This is a computer generated document.");  
			
			
		}

		catch (Exception ex){
			ex.printStackTrace();
		}

	}	
	
	
private void generateDetail(Document doc, PdfContentByte cb, int index, int y1)  {
		

		try {
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


						tp=createImage("कुल भाड़ा  ", cb,14,Color.BLACK);
						cb.addTemplate(tp, 435 , y);
					    createContent3(cb,575,y+5, String.format("%10s",df.format(in.getCn_val())),PdfContentByte.ALIGN_RIGHT);

						tp=createImage("कुल  भाड़ा ", cb,14,Color.BLACK);
						cb.addTemplate(tp, 435 , y2);
					    createContent3(cb,575,y2+5, String.format("%10s",df.format(in.getCn_val())),PdfContentByte.ALIGN_RIGHT);

					y=y-11;
					y2=y2-11;
					
					y=y-11;
					y2=y2-11;
					tp=createImage(in.getRemark(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 22, y);
					cb.addTemplate(tp, 22, y2);
					tp=createImage("भाड़ा  प्राप्त ", cb,14,Color.BLACK);
					cb.addTemplate(tp, 435 , y);
				    createContent3(cb,575,y+5, String.format("%10s",df.format(in.getNet_amt())),PdfContentByte.ALIGN_RIGHT);

					tp=createImage("भाड़ा  प्राप्त ", cb,14,Color.BLACK);
					cb.addTemplate(tp, 435 , y2);
				    createContent3(cb,575,y2+5, String.format("%10s",df.format(in.getNet_amt())),PdfContentByte.ALIGN_RIGHT);


					y=y-11;
					y2=y2-11;

					y=y-11;
					y2=y2-11;

					tp=createImage("भाड़ा  शेष ", cb,14,Color.BLACK);
					cb.addTemplate(tp, 435 , y);
				    createContent3(cb,575,y+5, String.format("%10s",df.format(in.getBill_amt())),PdfContentByte.ALIGN_RIGHT);

					tp=createImage("भाड़ा  शेष ", cb,14,Color.BLACK);
					cb.addTemplate(tp, 435 , y2);
				    createContent3(cb,575,y2+5, String.format("%10s",df.format(in.getBill_amt())),PdfContentByte.ALIGN_RIGHT);
					y=y-11;
					y2=y2-11;

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