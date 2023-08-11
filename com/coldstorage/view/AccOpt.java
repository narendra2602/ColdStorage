package com.coldstorage.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.coldstorage.dto.BookDto;
import com.coldstorage.dto.YearDto;

public class AccOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName,lblMarketingYear ;
	private JLabel lblStartingDate;
	private JButton viewButton,excelButton,exitButton,printButton;
	private KeyListener keyListener;
	private JComboBox fyear;
	String ClassNm,repNm;
	private JFormattedTextField sdate;
	private JFormattedTextField edate;
	private JLabel lblEndingDate,lblbank;
	private SimpleDateFormat sdf;
	private SimpleDateFormat sd;
	YearDto yd;
//	private String stdt,eddt;
	private Date stdt,eddt;
	private YearDto yrdto;
	private JComboBox bankCombo;

	public  AccOpt(String nm,String repNm)
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sd = new SimpleDateFormat("yyyy-MM-dd");
		 
		ClassNm=nm;
		this.repNm=repNm;
		//setUndecorated(true);
		setResizable(false);
		setSize(400, 300);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(48, 36, 303, 20);
		getContentPane().add(reportName);
		
		lblMarketingYear = new JLabel("Financial Year:");
		lblMarketingYear.setBounds(49, 92, 110, 20);
		getContentPane().add(lblMarketingYear);


		
		fyear = new JComboBox(loginDt.getFyear());
		fyear.setBounds(165, 92, 136, 23);
		getContentPane().add(fyear);
		fyear.setActionCommand("year");
		
		yrdto =(YearDto) fyear.getSelectedItem();
		
	    lblStartingDate = new JLabel("Starting Date:");
		lblStartingDate.setBounds(49, 122, 95, 20);
		getContentPane().add(lblStartingDate);
		
		sdate = new JFormattedTextField(sdf);
		sdate.setBounds(165, 123, 136, 22);
		checkDate(sdate);
		sdate.setName("1");
		sdate.setText(sdf.format(loginDt.getFr_date()));
		getContentPane().add(sdate);
		
		lblEndingDate = new JLabel("Ending Date:");
		lblEndingDate.setBounds(49, 153, 95, 20);
		getContentPane().add(lblEndingDate);
		
		edate = new JFormattedTextField(sdf);
		edate.setBounds(165, 154, 136, 22);
		checkDate(edate);
		edate.setName("2");
		//edate.setText(loginDt.getTo_date().after(new Date())?sdf.format(new Date()):sdf.format(loginDt.getEdate()));
		edate.setText(formatDate(loginDt.getTo_date()));
		getContentPane().add(edate);

		
		lblbank = new JLabel("Select Bank");
		lblbank.setVisible(false);
		lblbank.setBounds(48, 63, 152, 23);
		getContentPane().add(lblbank);
		
		bankCombo = new JComboBox(loginDt.getBooklist());
		bankCombo.setVisible(false);
		bankCombo.setActionCommand("bank");
		bankCombo.setBounds(165, 63, 208, 23);
		getContentPane().add(bankCombo);
		

		
		
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
								JOptionPane.showMessageDialog(AccOpt.this,"Date range should be in between: "
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
								JOptionPane.showMessageDialog(AccOpt.this,"Date range should be in between: "
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
 				//keyEvent.consume();
			}
		};

		sdate.addKeyListener(keyListener);
		edate.addKeyListener(keyListener);
		
		viewButton = new JButton("View");
		viewButton.setBounds(15, 209, 85, 30);
		viewButton.setIcon(viewIcon);
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
					excelButton.requestFocus();
					excelButton.setBackground(Color.blue);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					printButton.setBackground(null);
					evt.consume();
				}
				
			}
		});

		excelButton = new JButton("Excel");
		excelButton.setBounds(107, 209, 85, 30);
		excelButton.setIcon(excelIcon);
		getContentPane().add(excelButton);
		excelButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("Excel");
					dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					viewButton.requestFocus();
					viewButton.setBackground(Color.blue);
					printButton.setBackground(null);
					exitButton.setBackground(null);
					excelButton.setBackground(null);
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
			}
		});

		exitButton = new JButton("Exit");
		exitButton.setBounds(291, 209, 85, 30);
		exitButton.setIcon(exitIcon);
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
					excelButton.setBackground(null);
					evt.consume();
				}
				
			}
		});


		printButton = new JButton("Print");
		printButton.setBounds(199, 209, 85, 30);
		printButton.setIcon(printIcon);
		getContentPane().add(printButton);
		printButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("Print");
					dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					excelButton.requestFocus();
					excelButton.setBackground(Color.blue);
					printButton.setBackground(null);
					exitButton.setBackground(null);
					viewButton.setBackground(null);
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
		

		
				
		
	
		
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 11, 374, 246);
		getContentPane().add(panel_1);

		exitButton.addActionListener(this);
		viewButton.addActionListener(this);
		excelButton.addActionListener(this);
	    printButton.addActionListener(this);
	    
		fyear.addActionListener(this);
		
		
		 
		 
		 
		setAlwaysOnTop(true);
		
		
		
	}

	 public void resetAll()
	 {
		 
			try
			{
				fyear.setSelectedIndex(0);
				bankCombo.setSelectedIndex(0);
//				sdate.setText(sdf.format(loginDt.getSdate()));
//				edate.setText(sdf.format(loginDt.getEdate()));
		  		exitButton.setBackground(null);
		  		printButton.setBackground(null);
				viewButton.setBackground(null);;
				excelButton.setBackground(null);;
/*				selecteddiv.setVisible(false);
				alldivision.setVisible(false);
*/
			}
			catch(Exception ez)
			{
				System.out.println("error");
			}
		 
	 }
	 

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			resetAll();
			dispose();
		}
		
	    if(e.getActionCommand().equalsIgnoreCase("View") || e.getActionCommand().equalsIgnoreCase("Excel") || e.getActionCommand().equalsIgnoreCase("Print"))
	    {
			try
			{
				String btnnm = e.getActionCommand();
				callPrint(btnnm);

			}
			catch(Exception ez)
			{
				System.out.println("error "+ ez);
			}

		}

		  
		
		if(e.getActionCommand().equalsIgnoreCase("year"))
		{
			yrdto =(YearDto) fyear.getSelectedItem();
			sdate.setText(sdf.format(yrdto.getMsdate()));
			edate.setText(sdf.format(yrdto.getMedate()));
			
		}

	}
 
	 public void callPrint(String btnnm )
	 {
		 try
			{
				YearDto yd = (YearDto) fyear.getSelectedItem();
				Class<?> clazz = Class.forName(ClassNm);
				// create an instance
				
				int div=loginDt.getDiv_code();
				String bankname="";
				int doc_tp=0;
				if(repNm.contains("Sale"))
					doc_tp=40;
				if(repNm.contains("Purchase"))
					doc_tp=60;
				
				if (repNm.contains("Cash"))
					doc_tp=10;
				if (repNm.contains("Bank"))
				{
					BookDto bk = (BookDto) bankCombo.getSelectedItem();
					doc_tp=bk.getBook_code();
					bankname=bk.getBook_name()+" - "+bk.getBank_acno();
				}
				if (repNm.contains("Journal"))
					doc_tp=30;
				
				System.out.println("doc tp "+doc_tp+ " "+repNm);
				Constructor<?> constructor = clazz.getConstructor(Integer.class,Integer.class,         Integer.class,        String.class,   String.class,   String.class,String.class,      String.class,          String.class,      String.class,Integer.class);
				Object ob = (Object)constructor.newInstance(yd.getYearcode(),   loginDt.getDepo_code(),div,sdate.getText(),edate.getText(),btnnm,       loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),bankname,doc_tp);
				resetAll();
				dispose();
	 

			 
			}
			catch(Exception ez)
			{
				System.out.println(ez);
				System.out.println("error in vattax "+ez.getStackTrace());
				
			}

	 }
	 
	 
	 public void setVisible(boolean b)
	 {
			if (repNm.contains("Bank"))
			{
				lblbank.setVisible(true);
				bankCombo.setVisible(true);
				
			}
			else
			{
				lblbank.setVisible(false);
				bankCombo.setVisible(false);
				
			}
			super.setVisible(b);
	 }
}


