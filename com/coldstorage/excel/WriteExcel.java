package com.coldstorage.excel;

import java.util.Date;

import com.coldstorage.util.WritePDF;

import jxl.CellView;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel extends WritePDF {

  protected static WritableCellFormat timesBoldUnderline;
  protected static WritableCellFormat times,timesnew;
  protected static WritableCellFormat timesdt;

  
  protected static WritableCellFormat cellFormat,cellFormatnew;
  protected static WritableCellFormat cellFormat1;
  protected static WritableCellFormat cellFormat2;
  protected static WritableCellFormat cellFormat4;
  protected static WritableCellFormat cellFormat5;
  protected static WritableCellFormat cellFormat6;
  protected static WritableCellFormat cellFormat7;

  protected static WritableCellFormat head1;
  protected static WritableCellFormat head2;
  protected static WritableCellFormat head3;
  protected static WritableCellFormat head4;
  
  protected static WritableCellFormat dp2cell; 
  
  public static void createLabel(WritableSheet sheet)
      throws WriteException {
    // Lets create a times font
    WritableFont times10pt = new WritableFont(WritableFont.ARIAL, 9);
    // Define the cell format
    times = new WritableCellFormat(times10pt);
    // Lets automatically wrap the cells
    times.setBorder(Border.ALL, BorderLineStyle.THIN);
    times.setWrap(true);
    times.setVerticalAlignment(VerticalAlignment.TOP);
    

    timesnew = new WritableCellFormat(times10pt);
    // Lets automatically wrap the cells
    timesnew.setBorder(Border.ALL, BorderLineStyle.THIN);
    timesnew.setWrap(true);
    timesnew.setVerticalAlignment(VerticalAlignment.TOP);
    timesnew.setAlignment(jxl.format.Alignment.RIGHT);
    
    // Create create a bold font with unterlines
    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false,
        UnderlineStyle.NO_UNDERLINE);
    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
    // Lets automatically wrap the cells
    //timesBoldUnderline.setWrap(true);

    WritableFont head1Bold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE);
        head1 = new WritableCellFormat(head1Bold);

    
    WritableFont head2Bold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE);
        head2 = new WritableCellFormat(head2Bold);
        head2.setBackground(Colour.PERIWINKLE);
        head2.setWrap(true); 
        
        head2.setBorder(Border.ALL, BorderLineStyle.THIN);
        head3 = new WritableCellFormat(head2Bold);
        head3.setWrap(true); 
        head4 = new WritableCellFormat(head2Bold);
        head4.setWrap(true); 
    	head4.setBorder(Border.ALL, BorderLineStyle.THIN);     

        //    
        
 // Create cell font and format
    NumberFormat dp2 = new NumberFormat("0.00");        
    WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 9);
    cellFont.setColour(Colour.BLACK);
    
    cellFormat = new WritableCellFormat(dp2);
    cellFormat.setBackground(Colour.ICE_BLUE);
    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    cellFormat.setFont(cellFont);

    cellFormatnew = new WritableCellFormat(dp2);
    cellFormatnew.setBackground(Colour.ICE_BLUE);
    cellFormatnew.setBorder(Border.ALL, BorderLineStyle.THIN);
    cellFormatnew.setFont(cellFont);
    cellFormatnew.setAlignment(jxl.format.Alignment.RIGHT);
    
    DateFormat df=new DateFormat("dd/MM/yyyy");
    
    timesdt = new WritableCellFormat(df);
    timesdt.setBorder(Border.ALL, BorderLineStyle.THIN);
    timesdt.setVerticalAlignment(VerticalAlignment.TOP);
    
    
    cellFormat1 = new WritableCellFormat(dp2);
//    cellFormat1.setBackground(Colour.LAVENDER);
    cellFormat1.setBackground(Colour.LIGHT_GREEN);
    cellFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);
    cellFormat1.setFont(cellFont);
    

    
    cellFormat2 = new WritableCellFormat(dp2);
    cellFormat2.setBackground(Colour.PERIWINKLE);
    cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
    cellFormat2.setFont(cellFont);
    
    cellFormat4 = new WritableCellFormat(dp2);
    cellFormat4.setBackground(Colour.PERIWINKLE);
    cellFormat4.setBorder(Border.ALL, BorderLineStyle.THIN);
    cellFormat4.setFont(head2Bold);
    
    cellFormat5 = new WritableCellFormat(dp2);
    cellFormat5.setBackground(Colour.IVORY);
    cellFormat5.setBorder(Border.ALL, BorderLineStyle.THIN);
    cellFormat5.setFont(head2Bold);

    
    cellFormat6 = new WritableCellFormat();
    cellFormat6.setBackground(Colour.PERIWINKLE);
    cellFormat6.setBorder(Border.ALL, BorderLineStyle.THIN);
    cellFormat6.setFont(head2Bold);

    cellFormat7 = new WritableCellFormat(dp2);
    cellFormat7.setBackground(Colour.PERIWINKLE);
    cellFormat7.setBorder(Border.ALL, BorderLineStyle.THIN);
    cellFormat7.setFont(head2Bold);

    timesBoldUnderline.setAlignment(jxl.format.Alignment.CENTRE);
    head1.setAlignment(jxl.format.Alignment.CENTRE);
    head2.setAlignment(jxl.format.Alignment.CENTRE);
    head3.setAlignment(jxl.format.Alignment.LEFT);
    cellFormat4.setAlignment(jxl.format.Alignment.CENTRE);
    
    
    dp2cell = new WritableCellFormat(dp2);
    dp2cell.setBorder(Border.ALL, BorderLineStyle.THIN);
    dp2cell.setWrap(true);
    dp2cell.setVerticalAlignment(VerticalAlignment.TOP);
    
    
    CellView cv = new CellView();
    cv.setFormat(times);
    cv.setFormat(timesBoldUnderline);
    cv.setAutosize(true);
    //cv.setSize(55);
  

  }

 
  
  public static void addCaption(WritableSheet sheet, int column, int row, String s,int widthInChars)
      throws RowsExceededException, WriteException {
    Label label;
    sheet.setColumnView(column, widthInChars);
    label = new Label(column, row, s, timesBoldUnderline);
    sheet.addCell(label);
  }


  public static void addCaption1(WritableSheet sheet, int column, int row, String s,int widthInChars)
	      throws RowsExceededException, WriteException {
	    Label label;
	    sheet.setColumnView(column, widthInChars);
	    label = new Label(column, row, s, head1);
	    sheet.addCell(label);
	  }

  
  public static void addCaption2(WritableSheet sheet, int column, int row, String s,int widthInChars)
	      throws RowsExceededException, WriteException {
	    Label label;
	    sheet.setColumnView(column, widthInChars);
	    label = new Label(column, row, s, head2);
	    sheet.addCell(label);
	  }
  public static void addCaption3(WritableSheet sheet, int column, int row, String s,int widthInChars, int bord)
	      throws RowsExceededException, WriteException {
	    Label label;

	    sheet.setColumnView(column, widthInChars);

	    if (bord==2)
	    label = new Label(column, row, s, head4);
	    else
	    label = new Label(column, row, s, head3);
	    sheet.addCell(label);
	  }
  
  public static void addNumber(WritableSheet sheet, int column, int row,
      Integer integer,int dash) throws WriteException, RowsExceededException {
    Number number ;
    if (dash==1 )
    	number = new Number(column, row, integer, cellFormat);
    else
    if (dash==2 )
    	number = new Number(column, row, integer, cellFormat1);
    else
    if (dash==3)
       	number = new Number(column, row, integer, cellFormat2);
    else
    if (dash==5)
       	number = new Number(column, row, integer, cellFormat5);
    else
    if (dash==6)
           	number = new Number(column, row, integer, cellFormat6);
    else
    number = new Number(column, row, integer, times);
    	
    sheet.addCell(number);
  }

  
  public static void addDate(WritableSheet sheet, int column, int row,
	      Date dt,int dash) throws WriteException, RowsExceededException {
	    DateTime date ;

	    date = new DateTime(column, row, dt, timesdt);
	    	
	    sheet.addCell(date);
	  }

  
  
  public static void addDouble(WritableSheet sheet, int column, int row,
	      Double doubnumber,int dash) throws WriteException, RowsExceededException {
	  Number number;
	 
	    if (dash==1 )
	    	number = new Number(column, row, doubnumber, cellFormat);
	    else
	    if (dash==2 )
	    	number = new Number(column, row, doubnumber, cellFormat1);
	    else
	    if (dash==3)
	    	number = new Number(column, row, doubnumber, cellFormat2);
	    else
	        if (dash==5)
	           	number = new Number(column, row, doubnumber, cellFormat5);
	    else
	    if (dash==6)
           	number = new Number(column, row, doubnumber, cellFormat6);
	    else
	    if (dash==7)
           	number = new Number(column, row, doubnumber, cellFormat7);

	    else
		    number = new Number(column, row, doubnumber, dp2cell);
	       
	    sheet.addCell(number);
	  }
  
  public static void addLabel(WritableSheet sheet, int column, int row, String s,int dash)
      throws WriteException, RowsExceededException {
    Label label;
    if (dash==1 )
    	label = new Label(column, row, s, cellFormat);
    else
    if (dash==2)
    	label = new Label(column, row, s, cellFormat1);
    else
    if (dash==3)
      	label = new Label(column, row, s, cellFormat2);
    else
    if (dash==4)
      	label = new Label(column, row, s, cellFormat4);
    else
    if (dash==5)
       	label = new Label(column, row, s, cellFormat5);
    else
    if (dash==6)
       	label = new Label(column, row, s, cellFormat6);
    else
        label = new Label(column, row, s, times);
    	
    
    sheet.addCell(label);
  }


  public static void addLabel1(WritableSheet sheet, int column, int row, String s,int dash)
	      throws WriteException, RowsExceededException {
	    Label label;
	    if (dash==1 )
	    	label = new Label(column, row, s, cellFormatnew);
	    else
	    	label = new Label(column, row, s, timesnew);
	    
	    sheet.addCell(label);
	  }
  
  
  
} 