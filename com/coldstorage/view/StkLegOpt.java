package com.coldstorage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.MonthDto;
import com.coldstorage.dto.ProductDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.util.TableDataSorter;

public class StkLegOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName,lblMarketingYear ;
	private JLabel sdateLeb, lblStartingDate;
	private JButton viewButton,excelButton,exitButton,printButton;
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
	private JRadioButton singleItem;
	private JRadioButton allItem;
	private JPanel panel;
	private JPanel panel_2;
	int groupOrProduct;
	private JTextField search;
	private JLabel label;
	private JRadioButton rdbtnSelectiveCompany;
	private JRadioButton rdbtnAll;
	private JLabel lblSelect;
	private JComboBox companyCombo;
	private GroupDto cmp;
	public  StkLegOpt(String nm,String repNm)
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		 
		ClassNm=nm;
		//setUndecorated(true);
		setResizable(false);
		setSize(474, 555);	
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
		lblMarketingYear.setBounds(49, 64, 110, 20);
		getContentPane().add(lblMarketingYear);

		
		fyear = new JComboBox(loginDt.getFyear());
		fyear.setBounds(199, 64, 136, 23);
		getContentPane().add(fyear);
		fyear.setActionCommand("year");

		sdateLeb = new JLabel("Select Month:");
		sdateLeb.setBounds(49, 93, 110, 20);
		getContentPane().add(sdateLeb);

		smonth = new JComboBox(loginDt.getFmonth());
		smonth.setBounds(199, 93, 136, 23);
		getContentPane().add(smonth);
		smonth.setActionCommand("month");
		smonth.setSelectedIndex(loginDt.getMno());
		
		
	    lblStartingDate = new JLabel("Starting Date:");
		lblStartingDate.setBounds(49, 122, 95, 20);
		getContentPane().add(lblStartingDate);
		
		sdate = new JFormattedTextField(sdf);
		sdate.setBounds(199, 123, 136, 22);
		checkDate(sdate);
		sdate.setName("1");
		sdate.setText(sdf.format(loginDt.getSdate()));
		getContentPane().add(sdate);
		
		lblEndingDate = new JLabel("Ending Date:");
		lblEndingDate.setBounds(49, 153, 95, 20);
		getContentPane().add(lblEndingDate);
		
		edate = new JFormattedTextField(sdf);
		edate.setBounds(199, 154, 136, 22);
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
								JOptionPane.showMessageDialog(StkLegOpt.this,"Date range should be in between: "
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
								viewButton.requestFocus();
								viewButton.setBackground(Color.BLUE);
							}
							else
							{
								JOptionPane.showMessageDialog(StkLegOpt.this,"Date range should be in between: "
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
		
		viewButton = new JButton("View");
		viewButton.setIcon(viewIcon);
		viewButton.setBounds(40, 469, 90, 30);
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
		excelButton.setIcon(excelIcon);
		excelButton.setBounds(140, 469, 90, 30);
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
		exitButton.setIcon(exitIcon);
		exitButton.setBounds(340, 469, 90, 30);
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
		printButton.setIcon(printIcon);
		printButton.setBounds(240, 469, 90, 30);
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

		itemTable = new JTable(itemTableModel);

		itemTable.setColumnSelectionAllowed(false);
		itemTable.setCellSelectionEnabled(false);
		itemTable.getTableHeader().setReorderingAllowed(false);
		itemTable.getTableHeader().setResizingAllowed(false);
		itemTable.setRowHeight(20);
		itemTable.getTableHeader().setPreferredSize(new Dimension(25,25));
		///////////////////////////////////////////////////////////////////////////
		itemTable.getColumnModel().getColumn(0).setPreferredWidth(30);   //contact inv no
		itemTable.getColumnModel().getColumn(1).setPreferredWidth(400);  //party name/////
		itemTable.getColumnModel().getColumn(2).setMinWidth(0);  //inv_no/////
		itemTable.getColumnModel().getColumn(2).setMaxWidth(0);  //inv_no/////


		/////////////////////////////////////////////////////////////////////////////
		itemTablePane = new JScrollPane(itemTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		itemTablePane.setBounds(23, 313, 429, 151);
		getContentPane().add(itemTablePane);
		
		singleItem = new JRadioButton("Selective Products");
		singleItem.setSelected(true);
		singleItem.setBounds(44, 252, 141, 23);
		getContentPane().add(singleItem);
		
		allItem = new JRadioButton("All Products");
		allItem.setBounds(187, 252, 112, 23);
		getContentPane().add(allItem);

		
		ButtonGroup item = new ButtonGroup();
		item.add(singleItem);
		item.add(allItem);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(23, 58, 429, 124);
		getContentPane().add(panel_2);
		
		
		
		rdbtnSelectiveCompany = new JRadioButton("Selective Company");
		rdbtnSelectiveCompany.setSelected(true);
		rdbtnSelectiveCompany.setActionCommand("selective");
		rdbtnSelectiveCompany.setBounds(43, 187, 141, 23);
		getContentPane().add(rdbtnSelectiveCompany);
		
		rdbtnAll = new JRadioButton("All ");
		rdbtnAll.setActionCommand("all");
		rdbtnAll.setBounds(187, 187, 112, 23);
		getContentPane().add(rdbtnAll);
		
		ButtonGroup comp = new ButtonGroup();
		comp.add(rdbtnSelectiveCompany);
		comp.add(rdbtnAll);
		
		lblSelect = new JLabel("Select:");
		lblSelect.setBounds(47, 215, 95, 20);
		getContentPane().add(lblSelect);
		
		companyCombo = new JComboBox(loginDt.getCmpproductList());
		companyCombo.setActionCommand("company");
		companyCombo.setBounds(106, 215, 269, 22);
		getContentPane().add(companyCombo);
		companyCombo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED)
				{
					cmp = (GroupDto) e.getItem();
			    	fillItemDataPrd(false,cmp.getGp_code());
					
				}
		    	
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(23, 185, 429, 58);
		getContentPane().add(panel_3);
		

/*		MonthDto mn =(MonthDto) smonth.getSelectedItem();
		sdate.setText(sdf.format(mn.getSdate()));
		edate.setText(sdf.format(mn.getEdate()));
*/		
		exitButton.addActionListener(this);
		viewButton.addActionListener(this);
		excelButton.addActionListener(this);
	    printButton.addActionListener(this);
		smonth.addActionListener(this);
		fyear.addActionListener(this);
		companyCombo.addActionListener(this);
		
		///// item product selection all/selective
		singleItem.addActionListener(this);
		allItem.addActionListener(this);
		singleItem.setActionCommand("singleItem");
		allItem.setActionCommand("allItem");
		
		///// company selection all/selective
		rdbtnSelectiveCompany.addActionListener(this);
		rdbtnAll.addActionListener(this);
		
		search = new JTextField();
		search.setBounds(108, 278, 269, 22);
		getContentPane().add(search);
		

		TableModel myTableModel = itemTable.getModel();
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(myTableModel);
        itemTable.setRowSorter(sorter);
        search.getDocument().addDocumentListener(TableDataSorter.getTableSorter(search, sorter,itemTable, 1,false));
		
		
		label = new JLabel("Search:");
		label.setBounds(48, 279, 95, 20);
		getContentPane().add(label);
		
		
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(23, 248, 429, 58);
		getContentPane().add(panel);
		
		
		
		
		
						


		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 15, 456, 499);
		getContentPane().add(panel_1);
			 
		 
		 
		setAlwaysOnTop(true);
		cmp = (GroupDto) companyCombo.getSelectedItem();
		fillItemDataPrd(false,cmp.getGp_code());
		
		groupOrProduct=1; 
		
		
	}

	public void resetAll()
	{

		try
		{
			
			search.setText("");
			rdbtnSelectiveCompany.setSelected(true);
			singleItem.setSelected(true);
			fillItemDataPrd(false,0);
			companyCombo.setEnabled(true);
			
			
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
		
		if (e.getActionCommand().equals("singleItem"))
	    {
			System.out.println("singleItem called");
			search.setText("");
			if(rdbtnSelectiveCompany.isSelected())
	    	{
	    		cmp = (GroupDto) companyCombo.getSelectedItem();
		    	fillItemDataPrd(false,cmp.getGp_code());
	    	}
	    	else
	    	{
	    	  fillItemDataPrd(false,0);
	    	}
	    }
		
	    if (e.getActionCommand().equals("allItem"))
	    {
	    	System.out.println("allItem called");
	    	search.setText("");
	    	if(rdbtnSelectiveCompany.isSelected())
	    	{
	    		cmp = (GroupDto) companyCombo.getSelectedItem();
		    	fillItemDataPrd(true,cmp.getGp_code());
	    	}
	    	else
	    	{
	    	  fillItemDataPrd(true,0);
	    	}
	    }
		
		if (e.getActionCommand().equals("selective"))
	    {
	    	companyCombo.setEnabled(true);
			cmp = (GroupDto) companyCombo.getSelectedItem();
			fillItemDataPrd(false,cmp.getGp_code());

	    }
	    if (e.getActionCommand().equals("all"))
	    {
	    	companyCombo.setEnabled(false);
	    	fillItemDataPrd(false,0);
	    }
		if (e.getActionCommand().equals("company"))
	    {
			cmp = (GroupDto) companyCombo.getSelectedItem();
			fillItemDataPrd(false,cmp.getGp_code());

	    }
	    
		

	}
	
	
	public void fillItemDataPrd(boolean res,int cmpcd)
	{
		// Item Detail Fill 
		itemTableModel.getDataVector().removeAllElements();
		ProductDto prd = null;
		Vector prodVec =loginDt.getPrdlist();
		int size= prodVec.size();
		Vector dd = null;
		for(int i =0;i<size;i++)
		{
			prd = (ProductDto) prodVec.get(i);
			if(cmpcd==0)
			{
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(prd.getPname());
				dd.add(prd.getPcode());
				itemTableModel.addRow(dd);
			}
			else if(prd.getCmp_code()==cmpcd)
			{
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(prd.getPname());
				dd.add(prd.getPcode());
				itemTableModel.addRow(dd);
			}
			
		}
	}

	
	public ArrayList getSelectedRow()
	{
		Vector col = null;
		ArrayList dataList = new ArrayList();
		Vector data = itemTableModel.getDataVector();
		int size=data.size();
			for (int i = 0; i <size; i++) 
			{
				col = (Vector) data.get(i);
				if ((Boolean) col.get(0)) 
				{
					dataList.add(setIntNumber(col.get(2).toString()));
				}
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
				 if (a.isEmpty())
	  	    		   JOptionPane.showMessageDialog(StkLegOpt.this, "Please Select Product!!!!");
	  	    	else
	  	    	{ 
					Constructor<?> constructor = clazz.getConstructor(String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,ArrayList.class,Integer.class,GroupDto.class);
					Object ob = (Object)constructor.newInstance(sdt.getMnthabbr(),sdt.getMnthname(),String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sdate.getText(),edate.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm(),a,0,cmp);
					resetAll();
					dispose();
	  	    	}
			 
			}
			catch(Exception ez)
			{
				System.out.println("error");
			}

	 }
}


 