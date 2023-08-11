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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.coldstorage.dao.TransportDAO;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.email.EmailLedger;
import com.coldstorage.print.OutStandingPdf;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

public class AccountOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;


	private JFormattedTextField sdate;
	private JFormattedTextField edate;
	private JLabel reportName,selectParty;
	private JLabel sdateLeb;
	private JLabel edateLeb;
	private JButton viewButton,exitButton,printButton;
	private KeyListener keyListener;
	//private JComboBox partyList; 
	private JComboBox myear;
	String ClassNm;
	String ReportNm;
	String optn;
	YearDto yrdto;
	SimpleDateFormat sdf=null;
	private JRadioButton selectiveParty;
	private JRadioButton allParty;
	private JPanel panel;
	private JPanel panel_2;

	private JTextField party_name;
	private JComboBox regdistcombo;
	private JList partyList;
	private JScrollPane partyPane;
	private TransportDto partyDto;
	private Vector partyNames;
	TransportDAO pdao ;

	private JButton emailButton;
	private JLabel hintTextLabel;
	ByteArrayOutputStream outputStream ;	
	public  AccountOpt(String nm,String repNm)
	{
 
		ClassNm=nm;
		optn="1";
		ReportNm=repNm; 
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		//setUndecorated(true);
		setResizable(false);
		setSize(491, 340);	
		setLocationRelativeTo(null);

		
	       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	        
	        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

			arisleb.setVisible(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		hintTextLabel = new JLabel("Search by Party Name");
		hintTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		hintTextLabel.setForeground(Color.GRAY);
		hintTextLabel.setBounds(162, 117, 224, 20);
		getContentPane().add(hintTextLabel);
		
		pdao = new TransportDAO();
		
		partyNames = (Vector) pdao.getTransportList(loginDt.getDepo_code(),loginDt.getDiv_code());
		//PartyList ////////////////////////////////////////////////////////
		partyList = new JList();
		partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		partyList.setSelectedIndex(0);
		partyPane = new JScrollPane(partyList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		partyPane.setBounds(159, 139, 281, 105);
		getContentPane().add(partyPane);
		partyPane.setVisible(false);
		partyPane.setViewportView(partyList);
		bindData(partyList, partyNames);
		
	

		
		
		partyList.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{

					partyPane.setVisible(false);
					hintTextLabel.setVisible(false);
					 
					
					//int partyCodeIndex = partyList.getSelectedIndex();
					partyDto = (TransportDto) partyList.getSelectedValue();
					
		    		if(partyDto!=null)
					{
						party_name.setText(partyDto.getTran_name());
						party_name.setText(partyDto.getMac_name());
						myear.requestFocus();
						partyPane.setVisible(false);
						
					}
		    		 
				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					partyPane.setVisible(false);
					hintTextLabel.setVisible(true);
					party_name.setText("");
					party_name.requestFocus();
					evt.consume();
				}
			}
		});
		
		partyList.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent arg0){
			}
			public void mousePressed(MouseEvent arg0){
			}
			public void mouseExited(MouseEvent arg0){
			}
			public void mouseEntered(MouseEvent arg0){
			}
			public void mouseClicked(MouseEvent arg0) 
			{
				partyPane.setVisible(false);
				hintTextLabel.setVisible(false);
				//int partyCodeIndex = partyList.getSelectedIndex();
				partyDto = (TransportDto) partyList.getSelectedValue();
				
	    		if(partyDto!=null)
				{
					party_name.setText(partyDto.getTran_name());
					party_name.setText(partyDto.getMac_name());

					myear.requestFocus();
					partyPane.setVisible(false);

				}
				
			}
		});
		
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(85, 27, 303, 20);
		getContentPane().add(reportName);

		sdateLeb = new JLabel("Starting Date:");
		sdateLeb.setBounds(44, 172, 110, 20);
		getContentPane().add(sdateLeb);

		sdate = new JFormattedTextField(sdf);
		sdate.setBounds(159, 172, 136, 23);
		checkDate(sdate);
		sdate.setName("1");
		sdate.setText(sdf.format(loginDt.getFr_date()));
		getContentPane().add(sdate);

		edateLeb = new JLabel("Ending Date:");
		edateLeb.setBounds(44, 201, 110, 20);
		getContentPane().add(edateLeb);

		edate = new JFormattedTextField(sdf);
		edate.setBounds(159, 201, 136, 23);
		checkDate(edate);
		edate.setName("2");
		edate.setText(sdf.format(loginDt.getTo_date()));
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
								JOptionPane.showMessageDialog(AccountOpt.this,"Date range should be in between: "
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
								JOptionPane.showMessageDialog(AccountOpt.this,"Date range should be in between: "
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
		viewButton.setActionCommand("PDF");
		viewButton.setIcon(viewIcon);
		viewButton.setBounds(50, 253, 90, 30);
		getContentPane().add(viewButton);

		exitButton = new JButton("Exit");
		exitButton.setIcon(exitIcon);
		exitButton.setBounds(335, 253, 90, 30);
		getContentPane().add(exitButton);
		

		printButton = new JButton("Print");
		printButton.setActionCommand("Print");
		printButton.setIcon(printIcon);
		printButton.setBounds(145, 253, 90, 30);
		getContentPane().add(printButton);

		
		emailButton = new JButton("Email");
		emailButton.setIcon(emailIcon);
		emailButton.setBounds(240, 253, 90, 30);
		getContentPane().add(emailButton);
		

	  
			
		 
		viewButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("PDF");
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					printButton.requestFocus();
					printButton.setBackground(Color.blue);
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
					callPrint("Pdf");
					//dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					viewButton.requestFocus();
					viewButton.setBackground(Color.blue);
					printButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					exitButton.requestFocus();
					exitButton.setBackground(Color.blue);
					printButton.setBackground(null);
					viewButton.setBackground(null);
					evt.consume();
				}
			}
		});
		//Group the radio buttons.
	    selectParty = new JLabel("Select Party:");
	    selectParty.setBounds(45, 115, 110, 20);
	    getContentPane().add(selectParty);
	    

		regdistcombo = new JComboBox();
		regdistcombo.setVisible(false);
		regdistcombo.setBounds(159, 115, 281, 23);
		getContentPane().add(regdistcombo);

	    
		
	   


	    
		
	    myear = new JComboBox(loginDt.getFyear());
	    myear.setActionCommand("year");
	    myear.setBounds(159, 144, 136, 23);
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
		lblFinancialYear.setBounds(44, 144, 110, 20);
		getContentPane().add(lblFinancialYear);
		
		selectiveParty = new JRadioButton("Selective Party");
		selectiveParty.setSelected(true);
		selectiveParty.setActionCommand("1");
		selectiveParty.setBounds(42, 76, 150, 23);
		getContentPane().add(selectiveParty);
		selectiveParty.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
				{
					party_name.requestFocus();
				}
				
				if (key == KeyEvent.VK_RIGHT)
				{
					allParty.requestFocus();
					allParty.setSelected(true);
					party_name.setEnabled(false);
				}
				
			}
			
		});
		
		
		allParty = new JRadioButton("All Parties");
		allParty.setActionCommand("2");
		allParty.setBounds(200, 76, 174, 23);
		getContentPane().add(allParty);
		allParty.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
				{
					myear.requestFocus();
				}
				
				if (key == KeyEvent.VK_LEFT)
				{
					selectiveParty.requestFocus();
					selectiveParty.setSelected(true);
					party_name.setEnabled(true);
				}
				
			}
			
		});

		
		party_name = new JTextField();
		party_name.setBounds(159, 115, 281, 23);
		getContentPane().add(party_name);
		party_name.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				
				if (key == KeyEvent.VK_ENTER) 
				{
					partyPane.setVisible(false);
					keyEvent.consume();
				}
				if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
					int size = partyList.getFirstVisibleIndex();
					if (size >= 0) {
						partyList.requestFocus();
						partyList.setSelectedIndex(0);
						partyList.ensureIndexIsVisible(0);
						hintTextLabel.setVisible(false);
					}
					keyEvent.consume();
				}
				

				if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
					partyPane.setVisible(false);
					// party_code.requestFocus();
					//party_name.setText("");
					//hintTextLabel.setVisible(true);
					
					party_name.requestFocus();
					hintTextLabel.setVisible(true);
					keyEvent.consume();
				}
				
			}
			
			public void keyReleased(KeyEvent keyEvent) 
			{
				if (!partyPane.isVisible()
						&& (keyEvent.getKeyCode() != KeyEvent.VK_ENTER && keyEvent
								.getKeyCode() != KeyEvent.VK_ESCAPE)) {
					partyPane.setVisible(true);
				}
				
				searchFilter(party_name.getText(), partyList, partyNames);
				
				if (party_name.getText().length() > 0)
					hintTextLabel.setVisible(false);
				else
					hintTextLabel.setVisible(true);
			}
			
			
			
		});

		
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(22, 72, 430, 32);
		getContentPane().add(panel);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(22, 107, 430, 141);
		getContentPane().add(panel_2);
		
		 

		ButtonGroup group3 = new ButtonGroup();
	    group3.add(selectiveParty);
		group3.add(allParty);
		
		yrdto =(YearDto) myear.getSelectedItem();
		/*sdate.setText(sdf.format(yrdto.getMsdate()));
		edate.setText(sdf.format(yrdto.getMedate()));
		*/
		myear.addActionListener(this);
	    exitButton.addActionListener(this);
	    viewButton.addActionListener(this);
	    printButton.addActionListener(this);
	    emailButton.addActionListener(this);
	    
	    
	    emailButton.setActionCommand("Email");
	    
	    
		setAlwaysOnTop(true);

		 

		

		selectiveParty.addActionListener(this);
		selectiveParty.setActionCommand("selectiveParty");

		allParty.addActionListener(this);
		allParty.setActionCommand("allParty");
		
		 
		
		
		
		
		JPanel panel_1 = new JPanel();
//		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(10, 15, 460, 277);
		getContentPane().add(panel_1);
		
		
		//setting default focus request
		addWindowListener(new WindowAdapter() 
		{
			public void windowOpened(WindowEvent e) 
			{
				party_name.requestFocus();
			}
		});
		
	}
	
	
	 public void resetAll()
	 {
		 party_name.setText("");
		 party_name.setEnabled(true);
		 hintTextLabel.setVisible(true);
		 partyPane.setVisible(false);
		
		// partyList.setSelectedIndex(0);		 
		 myear.setSelectedIndex(0);
		 yrdto =(YearDto) myear.getSelectedItem();	
		 optn="1";

		 selectiveParty.setSelected(true);
		 regdistcombo.setVisible(false);
		 selectParty.setText("Select Party");
		 selectiveParty.setText("Selective Party");
		 allParty.setText("All Parties");
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
			printButton.setBackground(null);
			exitButton.setBackground(null);
			outputStream=null;
			 party_name.requestFocus();
			

	 }
	
	public void actionPerformed(ActionEvent e) 
	{
	    System.out.println(e.getActionCommand());
	    if (e.getActionCommand().equals("1") || e.getActionCommand().equals("2") || e.getActionCommand().equals("3") || e.getActionCommand().equals("4"))
	    {
	    	optn=e.getActionCommand();
	    }
	    

	    if(e.getActionCommand().equalsIgnoreCase("Party"))
	    {
		    selectParty.setText("Select Party");
		    selectiveParty.setText("Selective Party");
		    allParty.setText("All Parties");
		    hintTextLabel.setVisible(true);
		    party_name.setVisible(true);
		    regdistcombo.setVisible(false);

	    }

	    if(e.getActionCommand().equalsIgnoreCase("Exit"))
	    {
	    	resetAll();
	    	dispose();
	    	
	    }
	    
	 
	    
	    if(e.getActionCommand().equalsIgnoreCase("Email"))
	    {
	    	callPrint(e.getActionCommand());

	    }

	    if(e.getActionCommand().equalsIgnoreCase("Print") || e.getActionCommand().equalsIgnoreCase("PDF"))
	    {
	    	callPrint(e.getActionCommand());

	    }

	    
	    if(e.getActionCommand().equalsIgnoreCase("year"))
		{
			yrdto =(YearDto) myear.getSelectedItem();	
			sdate.setText(sdf.format(yrdto.getMsdate()));
			edate.setText(sdf.format(yrdto.getMedate()));
		}
	    
	    if (e.getActionCommand().equals("selectiveParty") )
	    {
	    	party_name.setEnabled(true);
	    	regdistcombo.setEnabled(true);
	    	optn="1";
	    }
	    if (e.getActionCommand().equals("allParty") )
	    {
	    	party_name.setEnabled(false);
	    	regdistcombo.setEnabled(false);
	    	optn="2";
	    	 
	    }
	    
/*	    if(e.getActionCommand().equalsIgnoreCase("Email"))
	    {
			outputStream = new ByteArrayOutputStream();

	    	callPrint(e.getActionCommand());
	    	if(ReportNm.equals("Outstanding"))
	    	{
	    		String email="promptindore@gmail.com";
	    		if(partyDto!=null)
	    		  email=partyDto.getMemail();
	    		Rectangle pageSize= PageSize.A4;
	    		float fontSize=8f;
	    		new EmailLedger(email,"OUT.TXT",ReportNm,pageSize,fontSize,outputStream).setVisible(true);
	    	}
	    	if(ReportNm.equals("Ledger"))
	    	{
	    		Rectangle pageSize= PageSize.A4;
	    		float fontSize=8f;
	    		
	    		new EmailLedger(partyDto.getMemail(),"LEDGER.TXT",ReportNm,pageSize,fontSize,outputStream).setVisible(true);
	    	}
	    	
	    		
	    	
	    	resetAll();
			dispose();
	    }
*/	    
	     
	    
	    
	}
 
	
	
	
	public void callPrint(String btnnm )
	 {
		try
    	{
    		//String btnnm = e.getActionCommand();
			System.out.println("Button value is "+btnnm);

			yrdto =(YearDto) myear.getSelectedItem();	
			TransportDto rdt = partyDto; 
    		if(partyDto==null)
    		{
    			rdt = (TransportDto) partyNames.get(0);	
    		}
    		
    		int repno=1;
    		if (ReportNm.startsWith("Ledger"))
    			repno=3;
    		
    		Class<?> clazz = Class.forName(ClassNm);
    		
			if (isValidRange(sdate.getText(),yrdto.getMsdate(), yrdto.getMedate()) && isValidRange(edate.getText(),yrdto.getMsdate(), yrdto.getMedate()))
			{
				int  code=rdt.getAccount_no();
				String name=rdt.getMac_name();

				if (ReportNm.startsWith("Outstanding") || ReportNm.startsWith("Ledger"))
				{
					
					if(allParty.isSelected())
					{
						optn="2";
						code=0;
						name="";
					}
						
					
/*				   if(btnnm.equalsIgnoreCase("SMS"))
				    {
					     
					     new SMSlOpt(formatDate(sdate.getText()),formatDate(edate.getText()),setIntNumber(code),name,yrdto.getYearcode(),setIntNumber(optn) ).setVisible(true);
						 resetAll();
						 dispose();
				    	
				    }
*/				   if(btnnm.equalsIgnoreCase("PDF") || btnnm.equalsIgnoreCase("Print"))
					{
					   int div=loginDt.getDiv_code();
//						 if(rdbtnDue.isSelected())
//							 div+=50;
						 
						 System.out.println("value od fiv is "+div+" OPTN IS "+optn+" repo no "+repno);
							/// new methor for PDF
						   new OutStandingPdf(sdate.getText(),edate.getText(),code,name,loginDt.getDepo_code(),div,yrdto.getYearcode(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm(),optn,repno,null);
					}
				 
					else
					{
						 int div=loginDt.getDiv_code();
//						 if(rdbtnDue.isSelected())
//							 div+=50;
						 
						 System.out.println("value of div is "+div);
						Constructor<?> constructor = clazz.getConstructor(String.class,String.class,String.class,String.class,Integer.class,Integer.class,Integer.class,String.class,String.class,String.class,String.class,String.class,String.class);
						Object ob = (Object)constructor.newInstance(sdate.getText(),edate.getText(),code,name,loginDt.getDepo_code(),div,yrdto.getYearcode(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm(),optn);
/*						 if(btnnm.equalsIgnoreCase("Email"))
						  {
									/// new method for Email
								   new OutStandingPdf(sdate.getText(),edate.getText(),code,name,loginDt.getDepo_code(),loginDt.getDiv_code(),yrdto.getYearcode(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm(),optn,repno,outputStream);
						  }
*/					}

				}
				else
				{
					if (allParty.isSelected())
						rdt.setTran_code(null);

					Constructor<?> constructor = clazz.getConstructor(String.class,String.class,String.class,String.class,Integer.class,Integer.class,Integer.class,String.class,String.class,String.class,String.class,String.class);
					Object ob = (Object)constructor.newInstance(sdate.getText(),edate.getText(),code,name,loginDt.getDepo_code(),loginDt.getDiv_code(),yrdto.getYearcode(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm());
				}

				if(!btnnm.equalsIgnoreCase("Email"))
				{
					resetAll();
					dispose();
				}
			}
			else
			{
				alertMessage(AccountOpt.this, "Date Range Should be in between "+formatDate(yrdto.getMsdate())+" "+formatDate(yrdto.getMedate()));
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
	

	
	
/*	public void setVisible(boolean b)
	{
		//party_name.requestFocus();
		System.out.println("yeha aaya kya accountopt");
		super.setVisible(b);
	}
*/	
}


