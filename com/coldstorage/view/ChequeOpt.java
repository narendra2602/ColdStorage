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
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

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
import javax.swing.border.LineBorder;

import com.coldstorage.dto.BookDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.util.SendMail;

public class ChequeOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName,lblMarketingYear ;
	private JLabel lblStartingDate;
	private JButton viewButton,exitButton,printButton;
	private KeyListener keyListener;
	private JComboBox fyear,bank;
	String ClassNm;
	private JFormattedTextField sdate;
	private JFormattedTextField edate;
	private JLabel lblEndingDate;
	private SimpleDateFormat sdf;
	private SimpleDateFormat sd;
	YearDto yrdto;
	BookDto bk;
//	private String stdt,eddt;
	private Date stdt,eddt;
	private JButton btnExcel;
	private Vector bankList;
	private JRadioButton rdbtndate;
	private JRadioButton rdbtnbank,rdbtnparty;
	public  ChequeOpt(String nm,String repNm)
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sd = new SimpleDateFormat("yyyy-MM-dd");
		 
		ClassNm=nm;
		//setUndecorated(true);
		setResizable(false);
		setSize(400, 300);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		bankList = loginDt.getBooklist();
		
		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(48, 29, 303, 20);
		getContentPane().add(reportName);
		
		
		
		rdbtndate = new JRadioButton("Date Wise");
		rdbtndate.setBounds(39, 49, 95, 23);
		rdbtndate.setSelected(true);
		getContentPane().add(rdbtndate);
		
		rdbtnbank = new JRadioButton("Bank Wise");
		rdbtnbank.setBounds(166, 49, 95, 23);
		getContentPane().add(rdbtnbank);

		rdbtnparty = new JRadioButton("Party Wise");
		rdbtnparty.setBounds(269, 49, 104, 23);
		getContentPane().add(rdbtnparty);

		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdbtndate);
	    group.add(rdbtnbank);
	    group.add(rdbtnparty);
	     
	    rdbtndate.addActionListener(this);
	    rdbtndate.setActionCommand("1");
	    rdbtnbank.addActionListener(this);
	    rdbtnbank.setActionCommand("2");
	    rdbtnparty.addActionListener(this);
	    rdbtnparty.setActionCommand("3");

		
		
		lblMarketingYear = new JLabel("Financial Year:");
		lblMarketingYear.setBounds(48, 80, 110, 20);
		getContentPane().add(lblMarketingYear);

		fyear = new JComboBox(loginDt.getFyear());
		fyear.setBounds(178, 80, 136, 23);
		getContentPane().add(fyear);
		fyear.setActionCommand("year");
		
		yrdto =(YearDto) fyear.getSelectedItem();
		
	    lblStartingDate = new JLabel("Starting Date:");
		lblStartingDate.setBounds(48, 139, 95, 20);
		getContentPane().add(lblStartingDate);
		

		sdate = new JFormattedTextField(sdf);
		sdate.setBounds(178, 140, 136, 22);
		checkDate(sdate);
		sdate.setName("1");
		sdate.setText(sdf.format(loginDt.getFr_date()));
		getContentPane().add(sdate);
		
		

		
		
		lblEndingDate = new JLabel("Ending Date:");
		lblEndingDate.setBounds(48, 170, 95, 20);
		getContentPane().add(lblEndingDate);
		
		edate = new JFormattedTextField(sdf);
		edate.setBounds(178, 171, 136, 22);
		checkDate(edate);
		edate.setName("2");
		edate.setText(sdf.format(loginDt.getTo_date()));
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
								JOptionPane.showMessageDialog(ChequeOpt.this,"Date range should be in between: "
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
								viewButton.setBackground(Color.blue);
							}
							else
							{
								JOptionPane.showMessageDialog(ChequeOpt.this,"Date range should be in between: "
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
					resetAll();
					dispose();
					keyEvent.consume();
				}
 				//keyEvent.consume();
			}
		};

		sdate.addKeyListener(keyListener);
		edate.addKeyListener(keyListener);
		
		viewButton = new JButton("View");
		viewButton.setBounds(54, 209, 70, 30);
		getContentPane().add(viewButton);
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
					btnExcel.requestFocus();
					btnExcel.setBackground(Color.blue);
					viewButton.setBackground(null);
					printButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});

		btnExcel = new JButton("Excel");
		btnExcel.setBounds(132, 209, 70, 30);
		getContentPane().add(btnExcel);
		btnExcel.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("Excel");
					evt.consume();
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					viewButton.requestFocus();
					viewButton.setBackground(Color.blue);
					printButton.setBackground(null);
					btnExcel.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					printButton.requestFocus();
					printButton.setBackground(Color.blue);
					viewButton.setBackground(null);
					btnExcel.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(293, 209, 70, 30);
		getContentPane().add(exitButton);
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

		printButton = new JButton("Email");
		printButton.setBounds(213, 209, 70, 30);
		getContentPane().add(printButton);
		printButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("Email");
					dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					btnExcel.requestFocus();
					btnExcel.setBackground(Color.blue);
					printButton.setBackground(null);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					exitButton.requestFocus();
					exitButton.setBackground(Color.blue);
					printButton.setBackground(null);
					btnExcel.setBackground(null);
					viewButton.setBackground(null);
					evt.consume();
				}
			}
		});
		 
		
		
		
		JLabel lblbank = new JLabel("Select Bank:");
		lblbank.setBounds(48, 108, 110, 20);
		getContentPane().add(lblbank);
		
		bank = new JComboBox(bankList);
		bank.setActionCommand("bank");
		bank.setBounds(178, 110, 193, 23);
		getContentPane().add(bank);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 11, 374, 246);
		getContentPane().add(panel_1);
		
		exitButton.addActionListener(this);
		viewButton.addActionListener(this);
		btnExcel.addActionListener(this);
		printButton.addActionListener(this);
		fyear.addActionListener(this);
		bank.addActionListener(this);
		
		

		// / setting default focus request
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				sdate.requestFocus();
				sdate.setSelectionStart(0);
			}
		});

		setAlwaysOnTop(true);
		
	}

	 public void resetAll()
	 {
		 
		 fyear.setSelectedIndex(0);
		 
//		 sdate.setText(sdf.format(loginDt.getSdate()));
//		 edate.setText(sdf.format(loginDt.getEdate()));
		 sdate.requestFocus();
		 sdate.setSelectionStart(0);
		 viewButton.setBackground(null);
		 printButton.setBackground(null);
		 exitButton.setBackground(null);
		 btnExcel.setBackground(null);
		 rdbtndate.setSelected(true);

	 }
	 
	 
	 public void callPrint(String btnnm )
	 {
		 try
			{
				bk = (BookDto) bank.getSelectedItem();
				yrdto = (YearDto) fyear.getSelectedItem();
				Class<?> clazz = Class.forName(ClassNm);
				int optn=1;
				if (rdbtnbank.isSelected())
					optn=2;
				if (rdbtnparty.isSelected())
					optn=3;
				OutputStream email = null;
				if(btnnm.equalsIgnoreCase("Email"))
					email = new ByteArrayOutputStream();
				// create an instance
				Constructor<?> constructor = clazz.getConstructor(Integer.class,Integer.class,Integer.class,String.class,String.class,Integer.class,String.class,String.class,String.class,String.class,String.class,Integer.class,OutputStream.class);
				Object ob = (Object)constructor.newInstance(yrdto.getYearcode(),loginDt.getDepo_code(),loginDt.getDiv_code(),sdate.getText(),edate.getText(),bk.getBook_code(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),bk.getBook_name(),optn,email);

				if(btnnm.equalsIgnoreCase("Email"))
				{
					SendMail.send(loginDt.getBrnnm()+" Collection List from "+sdate.getText()+" TO "+edate.getText() , (ByteArrayOutputStream) email, "PFA ", "teamnakoda@gmail.com,maclifes@gmail.com", "collection.xls");
					alertMessage(ChequeOpt.this, "Mail Sent Successfully");
				}
				
				
				resetAll();
				dispose();
			}
			catch(Exception ez)
			{
				ez.printStackTrace();
				System.out.println("error "+ez);
			}
	 }	 

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			dispose();
		}
		if(e.getActionCommand().equalsIgnoreCase("View") ||  e.getActionCommand().equalsIgnoreCase("Email") ||  e.getActionCommand().equalsIgnoreCase("Excel"))
		{
			try
			{
				String btnnm = e.getActionCommand();
				callPrint(btnnm);
			}
			catch(Exception ez)
			{
				System.out.println("error");
			}
		}

		if(e.getActionCommand().equalsIgnoreCase("month"))
		{
		}
		
		if(e.getActionCommand().equalsIgnoreCase("year"))
		{
		    yrdto = (YearDto) fyear.getSelectedItem();
			sdate.setText(formatDate(yrdto.getMsdate()));
			edate.setText(formatDate(yrdto.getMedate()));

		}

		

	}
	
	public void setVisible(boolean b)
	{
		bankList = new Vector(loginDt.getBooklist());
		
		BookDto bk = new BookDto();
		bk.setBook_code(10);
		bk.setBook_name("CASH");
		bankList.add(bk);

		bank.removeAllItems();
		int size = bankList.size();
		for (int i=0;i<size;i++)
		{
			bank.addItem(bankList.get(i));
		}
		rdbtndate.setSelected(true);
		
		super.setVisible(b);
	}
}


