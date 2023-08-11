package com.coldstorage.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class WritePDF 
{
	/**
	 * 
	 * @param fileContent  Reader file text for writing content of PDF
	 * @param fontSize	   Size of font 	
	 * @param pageType	   Page size (landscape/portrait)	
	 * @param stream	   ByteArrayOutputStream to create PDF stream for email 	
	 */
	
    private BaseFont bfBold;
    private BaseFont bf;
    private BaseFont ar;
	   
	public static void writeEmailPDFStream(String fileContent,float fontSize,Rectangle pageType,ByteArrayOutputStream stream )
	{
	
		Document doc =new Document(pageType);
		doc.setMargins(3, 3, 3, 3);

		PdfWriter docWriter = null;

		try
		{
			FontFactory.registerDirectories();
			
			com.itextpdf.text.Font lucia = FontFactory.getFont("lucida console", fontSize);
			docWriter = PdfWriter.getInstance(doc , stream);
			doc.open();
			
			Paragraph para = new Paragraph(fileContent,lucia);
			doc.add(para);

			doc.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
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

	
	public void createHeadings(PdfContentByte cb, float x, float y, String text){


		cb.beginText();
		cb.setFontAndSize(bfBold, 8);
		cb.setRGBColorFill(0, 0, 0);
		cb.setTextMatrix(x,y);
		cb.showText(text.trim());
		cb.endText(); 

	}
	public void createHeadings11(PdfContentByte cb, float x, float y, String text,int fsize){


		cb.beginText();
		cb.setFontAndSize(bfBold, fsize);
		cb.setRGBColorFill(0, 0, 0);
		cb.setTextMatrix(x,y);
		cb.showText(text.trim());
		cb.endText(); 

	}

	public void createHeadings1(PdfContentByte cb, float x, float y, String text){


		cb.beginText();
//		cb.setFontAndSize(bfBold, 10);
		cb.setFontAndSize(bf, 11);
		cb.setRGBColorFill(0, 0, 0);
		cb.setTextMatrix(x,y);
		cb.showText(text.trim());
		cb.endText(); 

	}

	public void createtext(PdfContentByte cb, float x, float y, String text){


		cb.beginText();
		cb.setFontAndSize(bfBold, 6);
		cb.setRGBColorFill(0, 0, 0);
		cb.setTextMatrix(x,y);
		cb.showText(text.trim());
		cb.endText(); 

	}
	public void createtext1(PdfContentByte cb, float x, float y, String text){


		cb.beginText();
		cb.setFontAndSize(bfBold, 8);
		cb.setRGBColorFill(0, 0, 0);
		cb.setTextMatrix(x,y);
		cb.showText(text.trim());
		cb.endText(); 

	}

	public void createtext2(PdfContentByte cb, float x, float y, String text){


		cb.beginText();
		cb.setFontAndSize(bfBold, 14);
		cb.setRGBColorFill(0, 0, 0);
		cb.setTextMatrix(x,y);
		cb.showText(text.trim());
		cb.endText(); 

	}
	public void redtext(PdfContentByte cb, float x, float y, String text){


		cb.beginText();
		cb.setFontAndSize(ar, 16);
	//	cb.setRGBColorFill(220, 20, 60);   // red color
//		cb.setRGBColorFill(0, 0, 0);  // black color

	if(text.startsWith("PROMPT"))
			cb.setRGBColorFill(220, 20, 60);   // red color
		else
			cb.setRGBColorFill(3, 98, 193);   // blue  color
		cb.setTextMatrix(x,y);
		cb.showText(text);
		cb.endText(); 

	}


	/*	public void printPageNumber(PdfContentByte cb){


		cb.beginText();
		cb.setFontAndSize(bfBold, 8);
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber+1), 570 , 25, 0);
		cb.endText(); 

		pageNumber++;

	} 
	*/
	public void createContent11(PdfContentByte cb, float x, float y, String text, int align,int fsize){


		cb.beginText();
		cb.setFontAndSize(bf, fsize);
		cb.setRGBColorFill(0, 0, 0);
		cb.showTextAligned(align, text.trim(), x , y, 0);
		cb.endText(); 

	}

	public void createContent(PdfContentByte cb, float x, float y, String text, int align){


		cb.beginText();
		cb.setFontAndSize(bf, 9);
		cb.setRGBColorFill(0, 0, 0);
		cb.showTextAligned(align, text.trim(), x , y, 0);
		cb.endText(); 

	}
	public void createContent1(PdfContentByte cb, float x, float y, String text, int align){


		cb.beginText();
		cb.setFontAndSize(bf, 8);
		cb.setRGBColorFill(0, 0, 0);
		cb.showTextAligned(align, text.trim(), x , y, 0);
		cb.endText(); 

	}     
	public void createContent2(PdfContentByte cb, float x, float y, String text, int align){


		cb.beginText();
		cb.setFontAndSize(bfBold, 9);
//		cb.setRGBColorFill(220, 20, 60); // RED
		cb.showTextAligned(align, text.trim(), x , y, 0);
		cb.endText(); 

	}

	public void createContent3(PdfContentByte cb, float x, float y, String text, int align){


		cb.beginText();
		cb.setFontAndSize(bfBold, 14);
		cb.showTextAligned(align, text.trim(), x , y, 0);
		cb.endText(); 

	}
	public void createContent4(PdfContentByte cb, float x, float y, String text, int align){


		cb.beginText();
		cb.setFontAndSize(bfBold, 8);
		cb.setRGBColorFill(104, 20, 169);  // PURPLE
		cb.showTextAligned(align, text.trim(), x , y, 0);
		cb.endText(); 

	}

	
	protected void initializeFonts(){


		try {
			bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			ar=BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
}
