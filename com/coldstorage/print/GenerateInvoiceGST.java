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

public class GenerateInvoiceGST extends WritePDF
{
   

	FigToWord fg; 
	ArrayList<?> invList;
	String s="";
	int csize;
	
	CmpMsflDAO cmpd ;
	CmpMsflDto cmpDto;
	private Vector<?> col;
    SimpleDateFormat sdf,sdf2;

	DecimalFormat df = null;
	private String word;
	Calendar cal;
	InvViewDto ic=null;
	private String btnname,drvnm,remark,pdfFilename; 
	int year,depo,div,sinv,einv,doc_tp,mncode,doctp,bunch;
 	Date sdate=null;
 	double sgstamt,cgstamt,igstamt,taxable;
 	int y=0;
	String text="";
	PdfTemplate tp=null;
	InvViewDto in=null;
	private double totalweight;
	public GenerateInvoiceGST()
	{
		
	}
	public GenerateInvoiceGST (String year,String depo,String div,String sinv,String einv,String btnnm,String drvnm,String printernm,String optn,Integer mncode,Integer doc_tp,String brnnm,String divnm,OutputStream emailPdfStream,Integer bunch)	
	{
		this.sinv=Integer.parseInt(sinv.trim());
		this.einv=Integer.parseInt(einv.trim());
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
    	pdfFilename=divnm+"-INV #"+sinv+" - "+einv+" "+sdf2.format(new Date())+".pdf";
    	

    	
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
		this.einv=Integer.parseInt(einv.trim());
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
			 
			String winv=""+sinv;
			InvPrintDAO inv = new InvPrintDAO();
			int jj=0;
					if(doctp==66)
						invList= (ArrayList<?>) inv.getInwardDetail(0,winv, year,doctp,div);
					else
						invList= (ArrayList<?>) inv.getInwardDetail(sinv,"", year,doctp,div);

//			in= inv.getInwardDetail(sinv,"",year,doctp);

			

					int lsize=invList.size();

			
			System.out.println("SIZE IS "+lsize);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			df = new DecimalFormat("0.00");
			 
			boolean beginPage = true;
			boolean first=true;
			int psize=0; 
			y=0;
			beginPage = true;
			doc.newPage();

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
						y = 530; 
					}

					generateDetail(doc, cb, i, y);
					y = y -11;

				} // end of product for loop 

				tp=createImage("कुल वजन ", cb,14,Color.BLACK);
				cb.addTemplate(tp, 450 , 235);

   			    createContent3(cb,580,240, String.format("%10s",df.format(totalweight)),PdfContentByte.ALIGN_RIGHT);


/*			}
*/				cb.stroke();
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
			//cb.rectangle(15,2,585,784);   // FULL PAGE BORDER

			cb.roundRectangle(20,5,570,784,10);
			
			
 			cb.moveTo(20,570);  // horizontal lines at the bottom
			cb.lineTo(590,570);


 			cb.moveTo(20,255);  // horizontal lines at the bottom
			cb.lineTo(590,255);

			
 			cb.moveTo(20,550);  // horizontal lines at the bottom
			cb.lineTo(590,550);

			
			cb.moveTo(250,570);
			cb.lineTo(250,255);
			cb.moveTo(310,570);
			cb.lineTo(310,255);
			cb.moveTo(375,570);
			cb.lineTo(375,255);
			cb.moveTo(440,570);
			cb.lineTo(440,255);
			cb.moveTo(500,570);
			cb.lineTo(500,255);
			
			cb.moveTo(20,89);
			cb.lineTo(590,89);

			cb.moveTo(20,230);
			cb.lineTo(590,230);
			

			
			cb.stroke();

			text="वस्तु  का नाम एवं पार्टी का मार्का ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 75, 552);

			text="बारदान  ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 270, 552);

			text="किस्म   ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 330, 552);

			
			text="तादाद ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 400, 552);

			
			text="RST NO ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 445, 552);

			text="वजन   ";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 540, 552);

			text="रिमार्क  :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 210);

			tp=createImage(in.getRemark(), cb,10,Color.BLACK);
			cb.addTemplate(tp, 75, 210);

			
			text="वाहन  क्रमांक :";
			tp=createImage(text, cb,12,Color.BLACK);
			cb.addTemplate(tp, 25, 180 );

			if(in.getVehicle_no()!=null || in.getVehicle_no().length()>1)
			{
				tp=createImage(in.getVehicle_no(), cb,10,Color.BLACK);
				cb.addTemplate(tp, 100, 180);
			}
		
			cb.moveTo(20,150);  // horizontal lines at the bottom
			cb.lineTo(590,150);

			
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
			
			String text="";
			PdfTemplate tp=null;
			
				text="श्री लक्ष्मी आईस एण्ड  कोल्ड स्टोरेज प्रा.लि.";
				tp=createImage(text, cb,22,Color.BLUE);
				cb.addTemplate(tp, 160, 765);

				text="ए.बी. रोड, डोंगर गांव, महू, फोन : 271326, Mob.: 9826065606";
				tp=createImage(text, cb,12,Color.BLUE);
				cb.addTemplate(tp, 190, 750);

				text="रजि. ऑफिस : 1418, पतीबाज़ार , महू , मो : S. K.  - 9826075812  , D. K.  - 9977227016 ";
				tp=createImage(text, cb,12,Color.BLUE);
				cb.addTemplate(tp, 120, 735);

				
				if(doctp==60)
				{
//					text=in.getChl_no()+" आवक वजन चिटठी";
					text="आलू  आवक वजन चिटठी";
					tp=createImage(text, cb,18,Color.BLACK);
					cb.addTemplate(tp, 225, 705);
				}
				else if(doctp==66)
				{
					text=in.getChl_no()+" छाटन    चिटठी";
					tp=createImage(text, cb,18,Color.BLACK);
					cb.addTemplate(tp, 225, 705);
				}
					
				else
				{
					text=in.getChl_no()+" ट्रांसफ़र  चिटठी";
					tp=createImage(text, cb,18,Color.BLACK);
					cb.addTemplate(tp, 225, 705);
				}

				
				tp=createImage(in.getMac_name_hindi(), cb,16,Color.BLACK);
				cb.addTemplate(tp, 25, 650);

				tp=createImage(in.getMcity_hindi(), cb,14,Color.BLACK);
				cb.addTemplate(tp, 25, 630);

				tp=createImage("Mobile No.: "+in.getMphone(), cb,14,Color.BLACK);
				cb.addTemplate(tp, 25, 610);
				
				if(doctp==41)
				{

					text="From:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 25, 670);

					text="To:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 325, 670);

					
					text="ट्रांसफ़र क्र.:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 25, 690);
					createtext2(cb,70,695,in.getInv_no());
					
					
					text=" दिनांक:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 470, 690);
					createtext2(cb,514,695,sdf.format(in.getInv_date()));


					tp=createImage(in.getReceiver_name(), cb,16,Color.BLACK);
					cb.addTemplate(tp, 325, 650);

					tp=createImage(in.getReceiver_city(), cb,14,Color.BLACK);
					cb.addTemplate(tp, 325, 630);

					tp=createImage("Mobile No.: "+in.getMobile(), cb,14,Color.BLACK);
					cb.addTemplate(tp, 325, 610);

					tp=createImage("Inward No.: "+in.getTransfer_no(), cb,14,Color.BLACK);
					cb.addTemplate(tp, 325, 590);
					tp=createImage("Inward No.: "+in.getPinv_no(), cb,14,Color.BLACK);
					cb.addTemplate(tp, 25, 590);


					text="खाता क्र.:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 325, 570);
					createtext2(cb,370,575,in.getReceiver_account());
					
				}
				else
				{
					text="रसीद क्र.:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 25, 680);
					if(doctp==66)
						createtext2(cb,70,685,in.getPinv_no());
					else
						createtext2(cb,70,685,in.getInv_no());
					
					text=" दिनांक:";
					tp=createImage(text, cb,10,Color.BLACK);
					cb.addTemplate(tp, 470, 680);
					createtext2(cb,514,685,sdf.format(in.getInv_date()));


				}
			text="खाता क्र.:";
			tp=createImage(text, cb,10,Color.BLACK);
			cb.addTemplate(tp, 25, 570);
			createtext2(cb,70,575,in.getMac_code());

			
	 
		
		}

		catch (Exception ex){
			ex.printStackTrace();
		}

	}

	
	private void generateFooter(Document doc, PdfContentByte cb)  {

		try 
		{
			
			
			if(doctp==60)
			{

				cb.moveTo(20,150);  // horizontal lines at the bottom
				cb.lineTo(590,150);


				
				text="माल मालिक या प्रतिनिधि     ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 22, 133);


				text="सही ठेकेदार     ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 322, 133);

				text="सुपरवायज़र      ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 522, 133);


				text="नोट - रसीद सम्हालकर रखें।  गुम होने पर होने वाली हानि की जवाबदारी स्वयं खातेदार की होगी । जो मुझे मान्य है ।   ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 22, 73);


				text="क़ानूनी विवाद के लिए न्याय क्षेत्र इंदौर रहेगा ।   ";
				tp=createImage(text, cb,10,Color.BLACK);
				cb.addTemplate(tp, 22, 63);

			}
			createHeadings(cb,250,10,"This is a computer generated document.");  
			
			
		}

		catch (Exception ex){
			ex.printStackTrace();
		}

	}	
	
	
private void generateDetail(Document doc, PdfContentByte cb, int index, int y1)  {
		

		try {
			
					tp=createImage(in.getPname(), cb,14,Color.BLACK);
					cb.addTemplate(tp, 22, y);

					tp=createImage(in.getPack_name(), cb,14,Color.BLACK);
					cb.addTemplate(tp, 270 , y);

					tp=createImage(in.getCategory_hindi(), cb,14,Color.BLACK);
					cb.addTemplate(tp, 330 , y);

					
					tp=createImage(in.getPack(), cb,10,Color.BLACK);
					cb.addTemplate(tp, 400, y);

					tp=createImage(String.valueOf(in.getRst_no()), cb,14,Color.BLACK);
					cb.addTemplate(tp, 460, y);

					tp=createImage(String.valueOf(in.getNumword()), cb,14,Color.BLACK);
					cb.addTemplate(tp, 530, y);
					totalweight+=in.getWeight();
					
					System.out.println("TOTAL WEIGHT "+totalweight);
					y=y-11;

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
			System.out.println("error hai");
			e.printStackTrace();
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
			e.printStackTrace();
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
		new GenerateInvoiceGST().createPDF ("inv.pdf",null);
      }	*/
}