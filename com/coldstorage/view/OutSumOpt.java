package com.coldstorage.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.coldstorage.dto.YearDto;
import com.coldstorage.email.EmailLedger;
import com.coldstorage.print.OutStandingPdf;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

public class OutSumOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;


	private JFormattedTextField sdate;
	private JFormattedTextField edate;
	private JLabel reportName;
	private JLabel sdateLeb;
	private JLabel edateLeb;
	private JButton viewButton,excelButton,exitButton,printButton;
	private KeyListener keyListener;
	//private JComboBox partyList; 
	private JComboBox myear;
	String ClassNm;
	String ReportNm;
	String optn;
	YearDto yrdto;
	SimpleDateFormat sdf=null;
	private JRadioButton rdbtnPartywise,rdbtnRegionwise,rdbtnDistwise;
	private JPanel panel_2;
	 


	private JButton emailButton;
	ByteArrayOutputStream outputStream ;	
	
	public  OutSumOpt(String nm,String repNm)
	{

		ClassNm=nm;
		optn="1";
		ReportNm=repNm; 
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		//setUndecorated(true);
		setResizable(false);
		setSize(480, 279);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(85, 27, 303, 20);
		getContentPane().add(reportName);

		sdateLeb = new JLabel("Starting Date:");
		sdateLeb.setBounds(44, 125, 110, 20);
		getContentPane().add(sdateLeb);

		sdate = new JFormattedTextField(sdf);
		sdate.setBounds(159, 125, 136, 23);
		checkDate(sdate);
		sdate.setName("1");
		sdate.setText(sdf.format(loginDt.getFr_date()));
		getContentPane().add(sdate);

		edateLeb = new JLabel("Ending Date:");
		edateLeb.setBounds(44, 154, 110, 20);
		getContentPane().add(edateLeb);

		edate = new JFormattedTextField(sdf);
		edate.setBounds(159, 154, 136, 23);
		checkDate(edate);
		edate.setName("2");
		edate.setText(formatDate(loginDt.getTo_date()));
//		edate.setText(sdf.format(new Date()));
		
		getContentPane().add(edate);


		///////////////////////////////////////////////////////////////////////////////////////////////
		keyListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					JTextField textField = (JTextField) keyEvent.getSource();
					int id = Integer.parseInt(textField.getName());
					switch (id) 
					{
					case 1: 
						String date=sdate.getText();
						if(isValidDate(date))
						{
							// Check the Date Range for the Financial year
							if (isValidRange(date,yrdto.getMsdate(),yrdto.getMedate()))
							{
								edate.requestFocus();
								edate.setSelectionStart(0);
							}
							else
							{
								JOptionPane.showMessageDialog(OutSumOpt.this,"Date range should be in between: "
								+ sdf.format(yrdto.getMsdate())+ " to " + sdf.format(yrdto.getMedate()),
								"Date Range",	JOptionPane.INFORMATION_MESSAGE);
								sdate.setValue(null);
								checkDate(sdate);
								sdate.requestFocus();
							}
								
						}
						else
						{
							sdate.setValue(null);
							checkDate(sdate);
							sdate.requestFocus();
						}
						break;
						
						
						
					case 2: 
						date=edate.getText();
						if(isValidDate(date))
						{
							// Check the Date Range for the Financial year
							if (isValidRange(date,yrdto.getMsdate(),yrdto.getMedate()))
							{
								viewButton.requestFocus();
								viewButton.setBackground(Color.BLUE);
							}
							else
							{
								JOptionPane.showMessageDialog(OutSumOpt.this,"Date range should be in between: "
								+ sdf.format(yrdto.getMsdate())+ " to " + sdf.format(yrdto.getMedate()),
								"Date Range",	JOptionPane.INFORMATION_MESSAGE);
								edate.setValue(null);
								checkDate(edate);
								edate.requestFocus();
							}
								
						}
						else
						{
							edate.setValue(null);
							checkDate(edate);
							edate.requestFocus();
						}
						break;
						
						
						
					}
				}
				if (key == KeyEvent.VK_ESCAPE) 
				{
					dispose();
				}
//				keyEvent.consume();
			}
		};

		sdate.addKeyListener(keyListener);
		edate.addKeyListener(keyListener);

		viewButton = new JButton("View");
		viewButton.setBounds(40, 192, 70, 30);
		getContentPane().add(viewButton);

		excelButton = new JButton("Excel");
		excelButton.setBounds(120, 192, 70, 30);
		getContentPane().add(excelButton);

		exitButton = new JButton("Exit");
		exitButton.setBounds(361, 192, 70, 30);
		getContentPane().add(exitButton);
		

		printButton = new JButton("PDF");
		printButton.setBounds(200, 192, 70, 30);
		getContentPane().add(printButton);

		
		emailButton = new JButton("Email");
		emailButton.setBounds(280, 192, 70, 30);
		getContentPane().add(emailButton);
		

	  
			
		 
		viewButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("View");
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					excelButton.requestFocus();
					excelButton.setBackground(Color.blue);
					printButton.setBackground(null);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		 
		exitButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					resetAll();
					dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					printButton.requestFocus();
					printButton.setBackground(Color.blue);
					excelButton.setBackground(null);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		 
		printButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("PDF");
					//dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					excelButton.requestFocus();
					excelButton.setBackground(Color.blue);
					viewButton.setBackground(null);
					printButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					exitButton.requestFocus();
					exitButton.setBackground(Color.blue);
					excelButton.setBackground(null);
					printButton.setBackground(null);
					viewButton.setBackground(null);
					evt.consume();
				}
			}
		});
		excelButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("Excel");
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					printButton.requestFocus();
					printButton.setBackground(Color.blue);
					excelButton.setBackground(null);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					viewButton.requestFocus();
					viewButton.setBackground(Color.blue);
					excelButton.setBackground(null);
					printButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		
	    


	    
		
	    myear = new JComboBox(loginDt.getFyear());
	    myear.setActionCommand("year");
	    myear.setBounds(159, 97, 136, 23);
		getContentPane().add(myear);
		myear.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
				{
					sdate.requestFocus();
				}
			}
			
		});
		
		JLabel lblFinancialYear = new JLabel("Financial Year:");
		lblFinancialYear.setBounds(44, 97, 110, 20);
		getContentPane().add(lblFinancialYear);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(22, 90, 430, 96);
		getContentPane().add(panel_2);
		
		 

		 
		
		yrdto =(YearDto) myear.getSelectedItem();
		/*sdate.setText(sdf.format(yrdto.getMsdate()));
		edate.setText(sdf.format(yrdto.getMedate()));
		*/
		myear.addActionListener(this);
	    exitButton.addActionListener(this);
	    viewButton.addActionListener(this);
	    excelButton.addActionListener(this);
	    printButton.addActionListener(this);
	    emailButton.addActionListener(this);
	    
	    
	    emailButton.setActionCommand("Email");
	    
	    
		setAlwaysOnTop(true);
		
		rdbtnPartywise = new JRadioButton("Partywise");
		rdbtnPartywise.setSelected(true);
		rdbtnPartywise.setActionCommand("party");
		rdbtnPartywise.setBounds(43, 60, 124, 23);
		getContentPane().add(rdbtnPartywise);
		
		rdbtnRegionwise = new JRadioButton("Areawise");
		rdbtnRegionwise.setActionCommand("Region");
		rdbtnRegionwise.setBounds(180, 60, 124, 23);
		getContentPane().add(rdbtnRegionwise);
		
		rdbtnDistwise = new JRadioButton("Terrwise");
		rdbtnDistwise.setActionCommand("Dist");
		rdbtnDistwise.setBounds(323, 60, 124, 23);
		getContentPane().add(rdbtnDistwise);
		
		ButtonGroup group4 = new ButtonGroup();
	    group4.add(rdbtnPartywise);
		group4.add(rdbtnRegionwise);
		group4.add(rdbtnDistwise);
		
		
		rdbtnPartywise.addActionListener(this);
		rdbtnRegionwise.addActionListener(this);
		rdbtnDistwise.addActionListener(this);
		
		 
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(22, 54, 430, 32);
		getContentPane().add(panel_3);
		
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 15, 460, 225);
		getContentPane().add(panel_1);
		
		
		//setting default focus request
		addWindowListener(new WindowAdapter() 
		{
			public void windowOpened(WindowEvent e) 
			{
				sdate.requestFocus();
			}
		});
		
	}
	
	
	 public void resetAll()
	 {
		 
		 yrdto =(YearDto) myear.getSelectedItem();	
		 optn="1";
		 rdbtnPartywise.setSelected(true);
		 
		 if(yrdto!=null)
		 {
			 sdate.setValue(null);
			 checkDate(sdate);
//			 edate.setValue(null);
//			 checkDate(edate);
			 sdate.setText(sdf.format(yrdto.getMsdate()));
//			 edate.setText(sdf.format(yrdto.getMedate()));
		 }

		 
			viewButton.setEnabled(true);
			printButton.setEnabled(true);
			emailButton.setEnabled(true);
	 		viewButton.setBackground(null);
			excelButton.setBackground(null);
			printButton.setBackground(null);
			exitButton.setBackground(null);
			outputStream=null;


	 }
	
	public void actionPerformed(ActionEvent e) 
	{
	    System.out.println(e.getActionCommand());
	    if (e.getActionCommand().equals("1") || e.getActionCommand().equals("2") || e.getActionCommand().equals("3") || e.getActionCommand().equals("4"))
	    {
	    	optn=e.getActionCommand();
	    }
	    
	     
	    
	    if(e.getActionCommand().equalsIgnoreCase("Exit"))
	    {
	    	resetAll();
	    	dispose();
	    	
	    }
	    if(e.getActionCommand().equalsIgnoreCase("View") || e.getActionCommand().equalsIgnoreCase("Excel") || e.getActionCommand().equalsIgnoreCase("PDF"))
	    {
	    	callPrint(e.getActionCommand());

	    }
	    
	    if(e.getActionCommand().equalsIgnoreCase("year"))
		{
			yrdto =(YearDto) myear.getSelectedItem();	
			sdate.setText(sdf.format(yrdto.getMsdate()));
//			edate.setText(sdf.format(yrdto.getMedate()));
		}
	    
	   
	    
	    if(e.getActionCommand().equalsIgnoreCase("Email"))
	    {
	    	outputStream = new ByteArrayOutputStream();
	    	callPrint(e.getActionCommand());
	    	String email="promptindore@gmail.com";
	    	Rectangle pageSize= PageSize.A4;
	    	float fontSize=8f;
	    	new EmailLedger(email,"OUTSUM.TXT",ReportNm,pageSize,fontSize,outputStream).setVisible(true);
	     
	    	resetAll();
			dispose();
	    }
	    
	     
	    
	    
	}
 
	
	
	
	public void callPrint(String btnnm )
	 {
		try
    	{
    		//String btnnm = e.getActionCommand();
			System.out.println("Button value is "+btnnm);

			yrdto =(YearDto) myear.getSelectedItem();	
    		 
    		
    		
    		Class<?> clazz = Class.forName(ClassNm);
    		
			if (isValidRange(sdate.getText(),yrdto.getMsdate(), yrdto.getMedate()) && isValidRange(edate.getText(),yrdto.getMsdate(), yrdto.getMedate()))
			{
				 

				if (ReportNm.startsWith("Outstanding") || ReportNm.startsWith("Ledger"))
				{
					
					if(rdbtnRegionwise.isSelected())
					{
						optn="3";
					}
					else if(rdbtnDistwise.isSelected())
					{
						optn="4";
					}
					 
					

					if(btnnm.equalsIgnoreCase("PDF"))
					{
							// new methor for PDF
						  String nm="";
						   new OutStandingPdf(sdate.getText(),edate.getText(),0,nm,loginDt.getDepo_code(),loginDt.getDiv_code(),yrdto.getYearcode(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm(),optn,2,null);

					}
					else
					{
						Constructor<?> constructor = clazz.getConstructor(String.class,String.class,Integer.class,Integer.class,Integer.class,String.class,String.class,String.class,String.class,String.class,String.class);
						Object ob = (Object)constructor.newInstance(sdate.getText(),edate.getText(),loginDt.getDepo_code(),loginDt.getDiv_code(),yrdto.getYearcode(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm(),optn);
						 if(btnnm.equalsIgnoreCase("Email"))
						  {
									/// new method for Email
								   new OutStandingPdf(sdate.getText(),edate.getText(),0,"",loginDt.getDepo_code(),loginDt.getDiv_code(),yrdto.getYearcode(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm(),optn,2,outputStream);
						  }
					}

				}
				else
				{
					 

					Constructor<?> constructor = clazz.getConstructor(String.class,String.class,Integer.class,Integer.class,Integer.class,String.class,String.class,String.class,String.class,String.class);
					Object ob = (Object)constructor.newInstance(sdate.getText(),edate.getText(),loginDt.getDepo_code(),loginDt.getDiv_code(),yrdto.getYearcode(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm());
				}

				if(!btnnm.equalsIgnoreCase("Email"))
				{
					resetAll();
					dispose();
				}
			}
			else
			{
				alertMessage(OutSumOpt.this, "Date Range Should be in between "+formatDate(yrdto.getMsdate())+" "+formatDate(yrdto.getMedate()));
				resetAll();
				sdate.requestFocus();

			}

    	}
    	catch(Exception ez)
    	{
    		System.out.println("error");
    		ez.printStackTrace();
    	}
	 }
}


