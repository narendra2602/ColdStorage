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
import javax.swing.JRootPane;
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

import com.coldstorage.dao.PartyDAO;
import com.coldstorage.dao.TransportDAO;
import com.coldstorage.dto.MonthDto;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.util.TableDataSorter;

public class MisOpt21 extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName,lblMarketingYear ;
	private JLabel sdateLeb, lblStartingDate;
	private JButton excelButton,exitButton;
	private KeyListener keyListener;
	private JComboBox fyear,smonth;
	String ClassNm,repNm;
	private ButtonGroup item; 
	private JFormattedTextField sdate;
	private JFormattedTextField edate;
	private JLabel lblEndingDate;
	private SimpleDateFormat sdf;
	 
	YearDto yd;
 
	private Vector partyNames;
	TransportDAO pdao ;
	private JTable itemTable;
	private DefaultTableModel itemTableModel;
	private JScrollPane itemTablePane;
	private JRadioButton selective;
	private JRadioButton all;
	int optn;
	private JTextField search;
	private JLabel lblSearch;
	public  MisOpt21(String nm,String repNm)
	{
		
		
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		 
		 optn=1;
		ClassNm=nm;
		this.repNm=repNm;
		//setUndecorated(true);
		setResizable(false);
		
		setSize(461, 475);	
		
		
		
		setLocationRelativeTo(null);

		int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (1024-w)/2;
        int y = (768-h)/2;
        pdao = new TransportDAO();
        partyNames = (Vector) pdao.getTransportList(loginDt.getDepo_code(),loginDt.getDiv_code() );
        
        // Move the window
        //this.setLocation(x, y);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
//        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		arisleb.setVisible(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(66, 21, 303, 20);
		getContentPane().add(reportName);
		
		lblMarketingYear = new JLabel("Financial Year:");
		lblMarketingYear.setBounds(49, 68, 110, 20);
		getContentPane().add(lblMarketingYear);


		
		fyear = new JComboBox(loginDt.getFyear());
		fyear.setBounds(207, 68, 169, 23);
		getContentPane().add(fyear);
		fyear.setActionCommand("year");

/*		sdateLeb = new JLabel("Select Month:");
		sdateLeb.setBounds(49, 89, 110, 20);
		getContentPane().add(sdateLeb);

		smonth = new JComboBox(loginDt.getFmonth());
		smonth.setBounds(207, 89, 169, 23);
		getContentPane().add(smonth);
		smonth.setActionCommand("month");
		smonth.setSelectedIndex(loginDt.getMno());
		smonth.setMaximumRowCount(12);
*/	
	    lblStartingDate = new JLabel("Starting Date:");
		lblStartingDate.setBounds(49, 102, 110, 20);
		getContentPane().add(lblStartingDate);
		
		sdate = new JFormattedTextField(sdf);
		sdate.setBounds(207, 103, 169, 22);
		checkDate(sdate);
		sdate.setName("1");
		sdate.setText(sdf.format(loginDt.getFr_date()));
		sdate.setSelectionStart(0);
		getContentPane().add(sdate);
		
		lblEndingDate = new JLabel("Ending Date:");
		lblEndingDate.setBounds(49, 139, 95, 20);
		getContentPane().add(lblEndingDate);
		
		edate = new JFormattedTextField(sdf);
		edate.setBounds(207, 140, 169, 22);
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
								JOptionPane.showMessageDialog(MisOpt21.this,"Date range should be in between: "
								+ sdf.format(yd.getMsdate())+ " to " + sdf.format(yd.getMedate()),
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
								excelButton.requestFocus();
								excelButton.setBackground(Color.blue);
							}
							else
							{
								JOptionPane.showMessageDialog(MisOpt21.this,"Date range should be in between: "
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
		
		
		excelButton = new JButton("Excel");
		excelButton.setBounds(135, 394, 90, 30);
		excelButton.setMnemonic(KeyEvent.VK_X);
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
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					exitButton.requestFocus();
					exitButton.setBackground(Color.blue);
					excelButton.setBackground(null);
					evt.consume();
				}
			}
		});
		
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(236, 394, 90, 30);
		exitButton.setMnemonic(KeyEvent.VK_E);
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
					excelButton.requestFocus();
					excelButton.setBackground(Color.blue);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});


		
		selective = new JRadioButton("Selective Parties");
		selective.setBounds(42, 183, 172, 23);
		getContentPane().add(selective);
		
		all = new JRadioButton("All Parties");
		all.setBounds(220, 183, 112, 23);
		getContentPane().add(all);

		item = new ButtonGroup();
		item.add(selective);
		item.add(all);
		
		//////////////invoce no table model/////////////////////
		String [] invColName=	{"", "Party Name",""};
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
		itemTablePane.setBounds(28, 240, 401, 144);
		getContentPane().add(itemTablePane);

		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(28, 56, 401, 121);
		getContentPane().add(panel_2);

		/*		MonthDto mn =(MonthDto) smonth.getSelectedItem();
		sdate.setText(sdf.format(mn.getSdate()));
		edate.setText(sdf.format(mn.getEdate()));
		 */		
		exitButton.addActionListener(this);
		excelButton.addActionListener(this);
		fyear.addActionListener(this);
		
		fillItemDataPrd(false);
		selective.setSelected(true);
		
		selective.addActionListener(this);
		all.addActionListener(this);
		selective.setActionCommand("selective");
		all.setActionCommand("all");
		
		search = new JTextField();
		search.setBounds(103, 209, 269, 22);
		getContentPane().add(search);
		
		TableModel myTableModel = itemTable.getModel();
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(myTableModel);
        itemTable.setRowSorter(sorter);
        search.getDocument().addDocumentListener(TableDataSorter.getTableSorter(search, sorter,itemTable, 1,false));
		
		lblSearch = new JLabel("Search:");
		lblSearch.setBounds(43, 210, 95, 20);
		getContentPane().add(lblSearch);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(28, 180, 401, 58);
		getContentPane().add(panel);
		

		
				
		
				
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(10, 11, 435, 421);
		getContentPane().add(panel_1);
		
		
		setAlwaysOnTop(true);
		
	}

	public void fillItemDataPrd(boolean res)
	{
		// Item Detail Fill 
		itemTableModel.getDataVector().removeAllElements();
		itemTableModel.fireTableDataChanged();
		TransportDto prt = null;
		TransportDAO pdao = new TransportDAO();
		int groupCode=0;
		Vector prtVec =null;

		prtVec = (Vector) partyNames;
		
		
		
		int size= prtVec.size();
		Vector dd = null;
		for(int i =0;i<size;i++)
		{
			prt = (TransportDto) prtVec.get(i);
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(prt.getAccount_no()+"-"+prt.getMac_name());
				dd.add(prt.getAccount_no());
				itemTableModel.addRow(dd);
		}
	}
	
	 public void resetAll()
	 {

		 try
		 {
			 optn=1;
			 fillItemDataPrd(false);
			 selective.setSelected(true);
			 fyear.setSelectedIndex(0);
		 }
		 catch(Exception ez)
		 {
			 System.out.println("error");
		 }
		 search.setEditable(true);
		 search.setText("");
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
				 dataList.add(col.get(2).toString());
			 }
		 }
		 return dataList;

	 }	

	 public void callPrint(String btnnm )
	 {
		 try
		 {

			 ArrayList noOfSelectedRow = getSelectedRow();
			 if (noOfSelectedRow.isEmpty())
			 {
				 JOptionPane.showMessageDialog(this, "Please Select Party!!!!");
			 }
			 else
			 {
//				 MonthDto sdt = (MonthDto) smonth.getSelectedItem(); 
				 YearDto yd = (YearDto) fyear.getSelectedItem();
				 Class<?> clazz = Class.forName(ClassNm);
				 // create an instance
				 int div=loginDt.getDiv_code();
				 int doc_type=60;
				if(repNm.contains("Outward Register"))
					doc_type=40;
				else if(repNm.contains("Final Register"))
					doc_type=600;
				else if(repNm.contains("Transfer Register"))
					doc_type=41;
				else if(repNm.contains("WrittenOff Register"))
					doc_type=66;
				else if(repNm.contains("Rent Register"))
					doc_type=10;
				else if(repNm.contains("Gate Pass Register"))
					doc_type=50;
				else if(repNm.contains("Discount Register"))
					doc_type=700;
				 
				System.out.println("doctype "+doc_type+" REPNM "+repNm);
				 Constructor<?> constructor = clazz.getConstructor(String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,Integer.class,ArrayList.class,Integer.class);
				 Object ob = (Object)constructor.newInstance("","",String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(div),sdate.getText(),edate.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),doc_type,noOfSelectedRow,optn);
				 resetAll();
				 dispose();
			 }

		 }
		 catch(Exception ez)
		 {
			 System.out.println("error "+ez);
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
	    	String btnnm = e.getActionCommand();
			callPrint(btnnm);
			

		}

	    if (e.getActionCommand().equals("selective"))
		{
			fillItemDataPrd(false);
			optn=1;
			search.setEditable(true);
			search.setText("");
		}
		
		
		if (e.getActionCommand().equals("all"))
		{
			fillItemDataPrd(true);
			optn=2;
			search.setEditable(false);
			search.setText("");
		}


	    
		
		if(e.getActionCommand().equalsIgnoreCase("year"))
		{
		    yd = (YearDto) fyear.getSelectedItem();
			System.out.println(yd.getYearcode());
			
			Vector v = (Vector) loginDt.getFmon().get(yd.getYearcode());
			System.out.println(v.size());
			
/*			smonth.removeAllItems();
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
*/			
		}

	}
}


