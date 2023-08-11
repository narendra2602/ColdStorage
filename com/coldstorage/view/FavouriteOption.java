package com.coldstorage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.coldstorage.dao.InvPrintDAO;
import com.coldstorage.dto.CmpInvDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.print.GenerateGatePass;
import com.coldstorage.print.GenerateInvoiceGST;
import com.coldstorage.print.GenerateOutward;
import com.coldstorage.print.GenerateReceipt;
import com.coldstorage.util.JIntegerField;

public class FavouriteOption extends BaseClass 
{
	private static final long serialVersionUID = 1L;
	String ClassNm,repname;
	String optn;
	YearDto yd;
	int doctp;
	public  FavouriteOption()
	{
		 
		//setUndecorated(true);
		setResizable(false);
		setSize(427, 314);	
		setLocationRelativeTo(null);
		
	       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	        
	        
	        this.setLocation(screenWidth-(screenWidth/6), (screenHeight/4)+30);
	        
//	        this.setUndecorated(true);
	      //  this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);


		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		

		 
		 
		 
		setAlwaysOnTop(true);
		
		
		
	}
	

	
	
/*	 public static void main(String[] args) {
		new FavouriteOption().setVisible(true);
		
	}*/
	
}




	 