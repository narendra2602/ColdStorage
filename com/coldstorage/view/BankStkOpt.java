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
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.MonthDto;
import com.coldstorage.dto.ProductDto;
import com.coldstorage.dto.YearDto;

public class BankStkOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName,lblMarketingYear ;
	private JLabel sdateLeb, lblStartingDate;
	private JButton excelButton,exitButton,printButton;
	private KeyListener keyListener;
	private JComboBox fyear,smonth;
	String ClassNm;
	private JFormattedTextField sdate;
	private JFormattedTextField edate;
	private JLabel lblEndingDate;
	private SimpleDateFormat sdf;
	YearDto yd;
//	private String stdt,eddt;
	private DefaultTableModel itemTableModel;
	private JTable itemTable;
	private JScrollPane itemTablePane;
	private JPanel panel_2;
	int groupOrProduct;
	private GroupDto cmp;
	public  BankStkOpt(String nm,String repNm)
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		 
		ClassNm=nm;
		//setUndecorated(true);
		setResizable(false);
		setSize(474, 300);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(88, 29, 303, 20);
		getContentPane().add(reportName);
		
		lblMarketingYear = new JLabel("Financial Year:");
		lblMarketingYear.setBounds(97, 64, 110, 20);
		getContentPane().add(lblMarketingYear);

		
		fyear = new JComboBox(loginDt.getFyear());
		fyear.setBounds(247, 64, 136, 23);
		getContentPane().add(fyear);
		fyear.setActionCommand("year");

		sdateLeb = new JLabel("Select Month:");
		sdateLeb.setBounds(97, 93, 110, 20);
		getContentPane().add(sdateLeb);

		smonth = new JComboBox(loginDt.getFmonth());
		smonth.setBounds(247, 93, 136, 23);
		getContentPane().add(smonth);
		smonth.setActionCommand("month");
		smonth.setSelectedIndex(loginDt.getMno());
		
		
	    lblStartingDate = new JLabel("Starting Date:");
		lblStartingDate.setBounds(97, 122, 95, 20);
		getContentPane().add(lblStartingDate);
		
		sdate = new JFormattedTextField(sdf);
		sdate.setBounds(247, 123, 136, 22);
		checkDate(sdate);
		sdate.setName("1");
		sdate.setText(sdf.format(loginDt.getSdate()));
		getContentPane().add(sdate);
		
		lblEndingDate = new JLabel("Ending Date:");
		lblEndingDate.setBounds(97, 153, 95, 20);
		getContentPane().add(lblEndingDate);
		
		edate = new JFormattedTextField(sdf);
		edate.setBounds(247, 154, 136, 22);
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
								JOptionPane.showMessageDialog(BankStkOpt.this,"Date range should be in between: "
								+ sdf.format(yd.getMsdate())+ " to " + sdf.format(yd.getMedate()),
								"Date Range",	JOptionPane.INFORMATION_MESSAGE);
								sdate.setValue(null);
								checkDate(sdate);
								sdate.requestFocus();
								sdate.setSelectionStart(0);
							}
								
						}
						else
						{
							sdate.setValue(null);
							checkDate(sdate);
							sdate.requestFocus();
							sdate.setSelectionStart(0);
						}
						break;
						
						
						
					case 2: 
						date=edate.getText();
						if(isValidDate(date))
						{
							// Check the Date Range for the Financial year
							if (isValidRange(date,yd.getMsdate(),yd.getMedate()))
							{
								excelButton.requestFocus();
								excelButton.setBackground(Color.BLUE);
							}
							else
							{
								JOptionPane.showMessageDialog(BankStkOpt.this,"Date range should be in between: "
								+ sdf.format(yd.getMsdate())+ " to " + sdf.format(yd.getMedate()),
								"Date Range",	JOptionPane.INFORMATION_MESSAGE);
								edate.setValue(null);
								checkDate(edate);
								edate.requestFocus();
								edate.setSelectionStart(0);
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
		

		excelButton = new JButton("Excel");
		excelButton.setIcon(excelIcon);
		excelButton.setBounds(95, 193, 90, 30);
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
					excelButton.requestFocus();
					excelButton.setBackground(Color.blue);
					printButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					printButton.requestFocus();
					printButton.setBackground(Color.blue);
					excelButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			}
		});

		exitButton = new JButton("Exit");
		exitButton.setIcon(exitIcon);
		exitButton.setBounds(295, 193, 90, 30);
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
					exitButton.setBackground(null);
					excelButton.setBackground(null);
					evt.consume();
				}
				
			}
		});



		printButton = new JButton("Print");
		printButton.setIcon(printIcon);
		printButton.setBounds(195, 193, 90, 30);
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
					evt.consume();
				}
			}
		});
		
		//////////////invoce no table model/////////////////////
		String [] invColName=	{"", "Description",""};
		String datax[][]={{}};
		itemTableModel=  new DefaultTableModel(datax,invColName)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				if(column==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}

			public Class getColumnClass(int column) 
			{
				switch (column) 
				{
				case 0:
					return Boolean.class;
				default:
					return String.class;
				}
			}

		};

		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(23, 58, 429, 181);
		getContentPane().add(panel_2);
		
			
		exitButton.addActionListener(this);
		excelButton.addActionListener(this);
	    printButton.addActionListener(this);
		smonth.addActionListener(this);
		fyear.addActionListener(this);
		

		
		
		
		
		
						


		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 15, 456, 239);
		getContentPane().add(panel_1);
			 
		 
		 
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
			excelButton.setBackground(null);;

		}
		catch(Exception ez)
		{
			System.out.println("error");
			ez.printStackTrace();
		}

	}
	 

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			dispose();
			resetAll();
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
				System.out.println("error");
			}

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
	
	
	
	public ArrayList getSelectedRow()
	{
		ArrayList dataList = new ArrayList();
		ProductDto prd = null;
		Vector prodVec =loginDt.getPrdlist();
		int size= prodVec.size();

			for (int i = 0; i <size; i++) 
			{
				prd = (ProductDto) prodVec.get(i);
					dataList.add(prd.getPcode());
			}
			return dataList;
		
	}	
	
	
	 public void callPrint(String btnnm )
	 {
		 try
			{

				MonthDto sdt = (MonthDto) smonth.getSelectedItem(); 
				YearDto yd = (YearDto) fyear.getSelectedItem();
				Class<?> clazz = Class.forName(ClassNm);
				// create an instance
				 ArrayList a = getSelectedRow();

				 Constructor<?> constructor = clazz.getConstructor(String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,ArrayList.class,Integer.class,GroupDto.class);
					Object ob = (Object)constructor.newInstance(sdt.getMnthabbr(),sdt.getMnthname(),String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sdate.getText(),edate.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm(),a,99,cmp);
					resetAll();
					dispose();
			 
			}
			catch(Exception ez)
			{
				System.out.println("error");
			}

	 }
}


 