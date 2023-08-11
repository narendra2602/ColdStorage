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
import java.util.Vector;

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

import com.coldstorage.dto.MonthDto;
import com.coldstorage.dto.YearDto;

public class DateOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName, lblMarketingYear,lblNote;
	private JLabel sdateLeb, lblStartingDate;
	private JButton viewButton,excelButton,exitButton,printButton;
	private KeyListener keyListener;
	private JComboBox fyear,smonth;
	String ClassNm,repNm;
	private JFormattedTextField sdate;
	private JFormattedTextField edate;
	private JLabel lblEndingDate;
	private SimpleDateFormat sdf;
	YearDto yd;
	public  DateOpt(String nm,String repNm)
	
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		 
		ClassNm=nm;
		this.repNm=repNm;
		//setUndecorated(true);
		setResizable(false);
		setSize(432, 316);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(48, 29, 303, 20);
		getContentPane().add(reportName);
		
		
	    
		lblNote = new JLabel("Note: Please verify this report with GSTR Summery. ");
		lblNote.setForeground(Color.RED);
		lblNote.setVisible(false);
		lblNote.setBounds(30, 56, 380, 20);
		getContentPane().add(lblNote);

	    
		lblMarketingYear = new JLabel("Financial Year:");
		lblMarketingYear.setBounds(49, 96, 110, 20);
		getContentPane().add(lblMarketingYear);


		
		fyear = new JComboBox(loginDt.getFyear());
		fyear.setBounds(199, 96, 136, 23);
		getContentPane().add(fyear);
		fyear.setActionCommand("year");

		sdateLeb = new JLabel("Select Month:");
		sdateLeb.setBounds(49, 127, 110, 20);
		getContentPane().add(sdateLeb);

		smonth = new JComboBox(loginDt.getFmonth());
		smonth.setBounds(199, 127, 136, 23);
		getContentPane().add(smonth);
		smonth.setActionCommand("month");
		smonth.setMaximumRowCount(12);
		smonth.setSelectedIndex(loginDt.getMno());
		
		
	    lblStartingDate = new JLabel("Starting Date:");
		lblStartingDate.setBounds(49, 158, 95, 20);
		getContentPane().add(lblStartingDate);
		
		sdate = new JFormattedTextField(sdf);
		sdate.setBounds(199, 159, 136, 22);
		checkDate(sdate);
		sdate.setName("1");
		sdate.setText(sdf.format(loginDt.getSdate()));
		sdate.setSelectionStart(0);
		getContentPane().add(sdate);
		
		lblEndingDate = new JLabel("Ending Date:");
		lblEndingDate.setBounds(49, 189, 95, 20);
		getContentPane().add(lblEndingDate);
		
		edate = new JFormattedTextField(sdf);
		edate.setBounds(199, 190, 136, 22);
		checkDate(edate);
		edate.setName("2");
		edate.setText(sdf.format(loginDt.getEdate()));
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
					yd = (YearDto) fyear.getSelectedItem();
					switch (id) 
					{
					case 1: 
						  
						String date=sdate.getText();
						if(isValidDate(date))
						{
							// Check the Date Range for the Financial year
							if (isValidRange(date,yd.getMsdate(),yd.getMedate()))
							{
								edate.requestFocus();
								edate.setSelectionStart(0);
							}
							else
							{
								JOptionPane.showMessageDialog(DateOpt.this,"Date range should be in between: "
								+ formatDate(yd.getMsdate())+ " to " + formatDate(yd.getMedate()),
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
							
							if (isValidRange(date,yd.getMsdate(),yd.getMedate()))
							{
								viewButton.requestFocus();
								viewButton.setBackground(Color.blue);
							}
							else
							{
								JOptionPane.showMessageDialog(DateOpt.this,"Date range should be in between: "
								+ sdf.format(yd.getMsdate())+ " to " + sdf.format(yd.getMedate()),
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
		
/*		viewButton = new JButton("View");
		viewButton.setBounds(41, 233, 70, 30);
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
*/		
		
		excelButton = new JButton("Excel");
		excelButton.setBounds(51, 233, 90, 30);
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
		exitButton.setBounds(270, 233, 90, 30);
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
		printButton.setBounds(160, 233, 90, 30);
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
		panel_1.setBounds(10, 11, 406, 262);
		getContentPane().add(panel_1);

/*		MonthDto mn =(MonthDto) smonth.getSelectedItem();
		sdate.setText(sdf.format(mn.getSdate()));
		edate.setText(sdf.format(mn.getEdate()));
*/		
		exitButton.addActionListener(this);
		//viewButton.addActionListener(this);
		excelButton.addActionListener(this);
	    printButton.addActionListener(this);
		smonth.addActionListener(this);
		fyear.addActionListener(this);
		
		
		 
		 
		 
		setAlwaysOnTop(true);
		
		
		
	}

	 public void resetAll()
	 {
		 
			try
			{

				 fyear.setSelectedIndex(0);
				 //smonth.setSelectedIndex(0);
				 smonth.setSelectedIndex(loginDt.getMno());
				 MonthDto mn =(MonthDto) smonth.getSelectedItem();
					if(mn!=null)
					{
						 sdate.setValue(null);
						 checkDate(sdate);
						 edate.setValue(null);
						 checkDate(edate);
					     sdate.setText(sdf.format(mn.getSdate()));
					     edate.setText(sdf.format(mn.getEdate()));
					}
			  		exitButton.setBackground(null);
			  		printButton.setBackground(null);
					viewButton.setBackground(null);;
					excelButton.setBackground(null);;
					viewButton.setVisible(true);
					excelButton.setVisible(true);
					printButton.setText("Print");
					printButton.setBounds(201, 233, 70, 30);
					exitButton.setBounds(281, 233, 70, 30);


			}
			catch(Exception ez)
			{
				System.out.println("error");
			}
		 
	 }
	 
	 
	 public void callPrint(String btnnm )
	 {
		 try
			{
				 
				MonthDto sdt = (MonthDto) smonth.getSelectedItem(); 
				YearDto yd = (YearDto) fyear.getSelectedItem();
				Class<?> clazz = Class.forName(ClassNm);
				

				// create an instance
				 int div=loginDt.getDiv_code();

				 if(repNm.contains("DDNR"))
						 div=div+1000;
					
				 System.out.println("repNM "+repNm+"  "+div);
				 
				 
				 Constructor<?> constructor = clazz.getConstructor(String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class);
				 Object ob = (Object)constructor.newInstance(sdt.getMnthabbr(),sdt.getMnthname(),String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(div),sdate.getText(),edate.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm());
				resetAll();
				dispose();
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
			dispose();
		}
		
	    if(e.getActionCommand().equalsIgnoreCase("View") || e.getActionCommand().equalsIgnoreCase("Excel") || e.getActionCommand().equalsIgnoreCase("Print") || e.getActionCommand().equalsIgnoreCase("Submit"))
	    {
	    	String btnnm = e.getActionCommand();
			callPrint(btnnm);
			

		}

		if(e.getActionCommand().equalsIgnoreCase("month"))
		{
			
			MonthDto mn =(MonthDto) smonth.getSelectedItem();
			if(mn!=null)
			{
				 sdate.setValue(null);
				 checkDate(sdate);
				 edate.setValue(null);
				 checkDate(edate);
			     sdate.setText(sdf.format(mn.getSdate()));
			     edate.setText(sdf.format(mn.getEdate()));
			}
			
		}
		
		if(e.getActionCommand().equalsIgnoreCase("year"))
		{
		    yd = (YearDto) fyear.getSelectedItem();
			System.out.println(yd.getYearcode());
			
			Vector v = (Vector) loginDt.getFmon().get(yd.getYearcode());
			System.out.println(v.size());
			
			smonth.removeAllItems();
			MonthDto mn=null;
			for (int i=0; i<v.size();i++)
			{
				 mn = (MonthDto) v.get(i);
				 smonth.addItem(mn);
				 
			}
			 
			mn =(MonthDto) smonth.getSelectedItem();
			sdate.setValue(null);
			checkDate(sdate);
			edate.setValue(null);
			checkDate(edate);
			sdate.setText(sdf.format(mn.getSdate()));
			edate.setText(sdf.format(mn.getEdate()));
			
		}

	}
 
	
	public void setVisible(boolean b)
	{
		
		if(repNm.contains("GSTR SUMMERY PRODUCT WISE"))
		{
			lblNote.setVisible(true);
		}
		else
		{
			lblNote.setVisible(false);
			
		}
		
		
		super.setVisible(b);
		
	}	
	

	
}


