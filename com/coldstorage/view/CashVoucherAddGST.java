package com.coldstorage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.CashVouDAO;
import com.coldstorage.dao.PartyDAO;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.util.JDoubleField;
import com.coldstorage.util.NumberTableCellEditor;
import com.coldstorage.util.OverWriteTableCellEditor;
import com.coldstorage.util.TextField;

public class CashVoucherAddGST extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	Font font;
	private JFormattedTextField f1 ;

	private JScrollPane Partypane; 
	private JScrollPane subCPane; 
	private JTextField vou_no;
	private JTextField name,gstno;
	private JFormattedTextField vou_date,vdate;
	private JLabel lblVoucher_no; 
	private JLabel lblVou_date;
	private JLabel lblVoucherNo,lblVoucherDate;
	private JLabel lblName,lblbrandname,lblGst;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_12;
	private JLabel branch;
	private JLabel lblDivision;
	private JLabel division;
	private JLabel lblDispatchEntry;
	private JPanel panel_2,panel_22;
	private SimpleDateFormat sdf;
	private NumberFormat df;
	private PartyDto prtyDto;
	private DefaultTableModel oedTableModel,invNoTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable oedTable,invNoTable;
	private JScrollPane oedPane;
	String partyCd;
	String sdate,edate;
	int billNo,option,rrow;
	RcpDto rcp;
	CashVouDAO CashDao;
	PartyDAO pdao ;
	Vector subv;
	private JLabel lblinvalid;
	private JTextField vno;
	private Font fontPlan;
	private JPanel panel_3;
	private JLabel label_4;
	private JList partyList,subCList;
	private JButton btnSave,btnAdd,btnDelete,btnPrint,btnExit,returnButton;
	NumberFormat formatter;
	private JTextField total;
	private JLabel lblCashBalance,lblTotal,lblMessage;
	private JTextField cashbal;
	double tot,xtot;
	double d;
	private JButton btnMiss;
	private JButton btnAddMore;
	private int vouNo;
	private boolean addRecord;
	private boolean addNewRow;
	private int sNo;
	private JLabel lblLastNo;
	private JLabel lastNo;
	private TextField nart1;
	private double tds=0.00;
	double servicetax, sertax_billper, sertax_per;
	private String sertax_apply = "";
    private JTextArea narration;
    private JScrollPane narrationpane;
    private JFormattedTextField table_pcode;
    private JDoubleField dobl;
	public CashVoucherAddGST()
	{
		infoName =(String) helpImg.get(getClass().getSimpleName());
		
		formatter = new DecimalFormat("0.00");
		sdf = new SimpleDateFormat("yyyy-MM-dd");  // Date Format
		sdate=sdf.format(loginDt.getFr_date());
		edate=sdf.format(loginDt.getTo_date());

		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
		df= new DecimalFormat("##########.00");
		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		fontPlan =new Font("Tahoma", Font.PLAIN, 11);
		
		f1 = new JFormattedTextField();
		checkDate(f1);
		f1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					
					String date=f1.getText();
					 
					 
					if(isValidDate(date))
					{
						 
					   System.out.println("Date format is correct");
							
					}
					else
					{
						 
						f1.setValue(null);
						checkDate(f1);
						f1.requestFocus();
					}
 
			}
		  }
		});
		

		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		CashDao = new CashVouDAO();
		
		pdao = new PartyDAO();
		 
		d = CashDao.getCashBalance(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
		//		final Vector partyNames = (Vector) pdao.getPartyName();
		final Vector partyNames = (Vector) loginDt.getPrtList();

		//		final Map partyMap = (HashMap) pdao.getPartyNameMap();
		final Map partyMap = (HashMap) loginDt.getPrtmap();
		
		
		/// Narration Panel
		panel_22 = new JPanel();
		panel_22.setLayout( null );
		panel_22.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_22.setBackground(SystemColor.inactiveCaptionText);
		panel_22.setBounds(350, 380, 468, 175);
		panel_22.setVisible(false);
		getContentPane().add(panel_22);
		
		lblbrandname = new JLabel( "Narration " );
		lblbrandname.setBounds( 10, 11, 150, 20 );
		panel_22.add( lblbrandname );
		
		narration= new JTextArea();
		narration.setLineWrap(true);
		narration.setWrapStyleWord(true);
		narrationpane = new JScrollPane(narration,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		narrationpane.setBounds(100, 10, 350, 126);
		panel_22.add( narrationpane );				
		narration.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent e) {
	            if(narration.getText().length() == 254)
	            {
	                alertMessage(CashVoucherAddGST.this, "You can enter only 254 Charaters!!!");
	            	e.consume();
	            }

			}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					int row=oedTable.getSelectedRow();
					int col=oedTable.getSelectedColumn();
					panel_22.setVisible(false);
					oedTable.setValueAt(narration.getText().trim(),row, col);
					oedTable.requestFocus();
				    narration.setText("");

	            	e.consume();
				}

				if (e.getKeyCode()>=112 && e.getKeyCode()<=123)
				{
//						narration.setText(getFunctionKeyValue(e));
				}

			}

			
		});

				
		returnButton = new JButton("Return");
		returnButton.setBounds(182, 145, 88, 20);
		returnButton.setActionCommand("Return");
		returnButton.setMnemonic(KeyEvent.VK_R);
		panel_22.add(returnButton);

		
		returnButton.addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_R) 
				{
					  int row=oedTable.getSelectedRow();
					  int col=oedTable.getSelectedColumn();
					  panel_22.setVisible(false);
					  oedTable.setValueAt(narration.getText(),row, col);
					  oedTable.requestFocus();
					  narration.setText("");
					
				}

			}
		});

		

		
		/// SUPPLIER Panel
		
		
		
		
		//////////////invoce no table model/////////////////////
		String [] invColName=	{"Vou No.", "Name","",""};
		String datax[][]={{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}};
		invNoTableModel=  new DefaultTableModel(datax,invColName)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				return false;
			}
		};

		invNoTable = new JTable(invNoTableModel);
		invNoTable.setFont(fontPlan);
		invNoTable.setColumnSelectionAllowed(false);
		invNoTable.setCellSelectionEnabled(false);
		invNoTable.getTableHeader().setReorderingAllowed(false);
		invNoTable.getTableHeader().setResizingAllowed(false);
		invNoTable.getTableHeader().setFont(font);
		invNoTable.setRowHeight(20);
		invNoTable.getTableHeader().setPreferredSize(new Dimension(25,25));
		///////////////////////////////////////////////////////////////////////////
		invNoTable.getColumnModel().getColumn(0).setPreferredWidth(80);   //contact inv no
		invNoTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //party name/////
		invNoTable.getColumnModel().getColumn(2).setMinWidth(0);  //header detail /////
		invNoTable.getColumnModel().getColumn(2).setMaxWidth(0);  //header detail/////
		invNoTable.getColumnModel().getColumn(3).setMinWidth(0);  //detail  table/////
		invNoTable.getColumnModel().getColumn(3).setMaxWidth(0);  //detail table/////

		JScrollPane scrollPane = new JScrollPane(invNoTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(16, 235, 184, 400);
		getContentPane().add(scrollPane);
		///////////////////////////////////////////////////////
		
		
		partyList = new JList(loginDt.getPrtList());
		//partyList.setFont(font);
		partyList.setSelectedIndex(0);
		partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Partypane = new JScrollPane(partyList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Partypane.setBounds(314, 270, 298, 295);
		getContentPane().add(Partypane);
		Partypane.setVisible(false);
		///////////////////////////////////////////////////////

		subCList = new JList(loginDt.getPrtList());
		//partyList.setFont(font);
		subCList.setSelectedIndex(0);
		subCList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		subCPane = new JScrollPane(subCList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		subCPane.setBounds(525, 270, 286, 259);
		getContentPane().add(subCPane);
		subCPane.setVisible(false);
		
		///////////////////////////////////////////////////////

		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(314, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);

		


		label = new JLabel("ARISTO");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setForeground(new Color(220, 20, 60));
		label.setFont(new Font("Bookman Old Style", Font.BOLD, 23));
		label.setBounds(10, 48, 184, 22);
		getContentPane().add(label);


/*		JLabel promleb = new JLabel(promLogo);
		promleb.setBounds(10, 670, 35, 35);
		getContentPane().add(promleb);

		JLabel arisleb = new JLabel(arisLogo);
		arisleb.setBounds(10, 11, 35, 37);
*/		getContentPane().add(arisleb);

		label_2 = new JLabel("PHARMACEUTICALS PVT. LTD.");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(10, 71, 195, 14);
		getContentPane().add(label_2);

		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);

		branch = new JLabel(loginDt.getBrnnm());
		branch.setForeground(Color.BLACK);
		branch.setFont(new Font("Tahoma", Font.BOLD, 11));
		branch.setBounds(10, 86, 195, 14);
		getContentPane().add(branch);

		lblDivision = new JLabel("DIVISION:");
		lblDivision.setForeground(Color.BLACK);
		lblDivision.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDivision.setBounds(10, 101, 60, 14);
		getContentPane().add(lblDivision);

		division = new JLabel(loginDt.getDivnm());
		division.setForeground(Color.BLACK);
		division.setFont(new Font("Tahoma", Font.BOLD, 11));
		division.setBounds(75, 101, 60, 14);
		getContentPane().add(division);

		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(214, 657, 790, 48);
		getContentPane().add(panel_2);

		lblDispatchEntry = new JLabel("Petty Cash Voucher");
		lblDispatchEntry.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDispatchEntry.setBounds(421, 90, 251, 22);
		getContentPane().add(lblDispatchEntry);


		lblVoucher_no = new JLabel("Voucher Number:");
		lblVoucher_no.setBounds(365, 150, 111, 20);
		getContentPane().add(lblVoucher_no);

		vou_no = new JTextField();
		vou_no.setEditable(false);
		vou_no.setBounds(481, 149,98, 23);
		getContentPane().add(vou_no);

		lblVou_date = new JLabel("Date:");
		lblVou_date.setBounds(620, 150, 57, 20);
		getContentPane().add(lblVou_date);

		lblName = new JLabel("Name:");
		lblName.setBounds(365, 181, 111, 20);
		getContentPane().add(lblName);

		name = new TextField(50);
		name.setBounds(481, 181, 324, 23);
		getContentPane().add(name);

		lblGst = new JLabel("GST No:");
		lblGst.setBounds(365, 210, 111, 20);
		getContentPane().add(lblGst);

		gstno = new TextField(50);
		gstno.setBounds(481, 210, 324, 23);
		getContentPane().add(gstno);

		
		lblCashBalance = new JLabel("Cash Balance:");
		lblCashBalance.setHorizontalAlignment(SwingConstants.LEFT);
		lblCashBalance.setForeground(Color.RED);
		lblCashBalance.setBounds(815, 151, 115, 20);
		getContentPane().add(lblCashBalance);
		
		cashbal = new JTextField();
		cashbal.setHorizontalAlignment(SwingConstants.RIGHT);
		cashbal.setEditable(false);
		cashbal.setText(formatter.format(d));
		cashbal.setBounds(815, 181, 115, 22);
		getContentPane().add(cashbal);


		vou_date = new JFormattedTextField(sdf);
		vou_date.setBounds(687, 147, 118, 23);
		checkDate(vou_date);
		vou_date.setText(sdf.format(new Date()));
		getContentPane().add(vou_date);

		

		
		
		lblinvalid = new JLabel();
		lblinvalid.setBounds(690, 188, 118, 23);
		getContentPane().add(lblinvalid);
		//////////////////
	
		invNoTable.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				int row = invNoTable.getSelectedRow();
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					 
					
					rcp = (RcpDto) invNoTable.getValueAt(row, 2);
					Vector v = (Vector) invNoTable.getValueAt(row,3);
					fillData(rcp);
					fillItemData(v);
					evt.consume();
				}
				
			}
		});
		
		
		invNoTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent e) 
			{
				
				int row = invNoTable.getSelectedRow();
				 
					
					rcp = (RcpDto) invNoTable.getValueAt(row, 2);
					Vector v = (Vector) invNoTable.getValueAt(row,3);
					fillData(rcp);
					fillItemData(v);
				 
				
			 
			}
		});
				
		 
		///////////////////////////////////////////////////////////////////////////////////////////////
		KeyListener keyListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) 
				{

					JTextField textField = (JTextField) keyEvent.getSource();
					int id = Integer.parseInt(textField.getName());

					switch (id) 
					{
					case 0:
						vou_date.requestFocus();
						vou_date.selectAll();
						break;

					case 1:
						String date=vou_date.getText();

						if(isValidDate(date))
						{
							lblinvalid.setVisible(false);
							// Check the Date Range for the Financial year
							if (isValidRange(date,loginDt.getFr_date(),loginDt.getTo_date()))
							{
								name.requestFocus();
							}
							else
							{
								JOptionPane.showMessageDialog(CashVoucherAddGST.this,"Date range should be in between: "
								+ sdf.format(loginDt.getFr_date())+ " to " + sdf.format(loginDt.getTo_date()),
								"Date Range",	JOptionPane.INFORMATION_MESSAGE);
								vou_date.setValue(null);
								checkDate(vou_date);
								vou_date.requestFocus();

							}


						}
						else
						{
							lblinvalid.setText("invalid date");
							lblinvalid.setForeground(Color.red);
							lblinvalid.setVisible(true);
							vou_date.setValue(null);
							checkDate(vou_date);
							vou_date.requestFocus();
						}
						break;
					case 2:
						gstno.requestFocus();
						gstno.setSelectionStart(0);
						break;

					case 3:
						oedTable.requestFocus();
						oedTable.changeSelection(0,1, false, false);
						oedTable.editCellAt(0, 1);
						break;

					}

				}

				if (key == KeyEvent.VK_ESCAPE) 
				{
					clearAll();
					dispose();

				}




			}

		};


		// / setting default focus request
				addWindowListener(new WindowAdapter() {
					public void windowOpened(WindowEvent e) {
						vno.requestFocus();
					}
				});

		//////////////////////////////////////////////////////////////////////
		String[] crDrColName = {"","Expenses Code","Account Head","Sub Head","Supplier Name","Bill No","Bill Date","Narration","Amount","Taxable","RCM","ITC","GST %","CGST","SGST","IGST","CESS %","CESS AMT","TOTAL","TDS Amt","Balance","","","","","","","HSN CODE"};
		Object cdDrData[][] = {{false,"","","","","","  /  /    ","",0.00,0.00,"","",0.00,0.00,0.00,0.00,0.0,00.00,0.00,0.00,0.00,"","","","","",""}};
		oedTableModel = new DefaultTableModel(cdDrData, crDrColName) 
		{
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) 
			{

				boolean ans=true;
				if (option==1 || option==2)
				{
					return ans;
				}
				if (oedTable.getValueAt(row, 0)!=null)
				{
				 if((Boolean) (oedTable.getValueAt(row, 0)))	
				  {
						if(column!=0)
						{
							ans=false;
						}
				  }
				}
			    return ans;
			}
			
			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
				case 0:  return Boolean.class;
				case 8 : return Double.class;
				case 9 : return Double.class;
				case 12 : return Double.class;
				case 13 : return Double.class;
				case 14 : return Double.class;
				case 15 : return Double.class;
				case 16 : return Double.class;
				case 17 : return Double.class;
				case 18 : return Double.class;
				case 19 : return Double.class;
				case 20 : return Double.class;
				default: return String.class;
				}
			}
			
			 
		};

		oedTable = new JTable(oedTableModel);
		
		oedTableItemUI();
		oedTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		//////////////////////////////////////////////////////////////////////////
		oedPane = new JScrollPane(oedTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		oedPane.setBounds(214, 245, 790, 339);
		getContentPane().add(oedPane);
		oedPane.setVisible(true);


		/////////////////////////////////////////////////////////////////////////
		KeyListener crTableListener = new KeyAdapter() 
		{
			private int recordNo;
			double n1 = 0.00;
			double n2 = 0.00;
			double n3 = 0.00;
			double net = 0.00;
			double cgst = 0.00;
			double sgst = 0.00;
			double igst = 0.00;
			double tdsa = 0.00;
			double tax = 0.00;
			double taxamt = 0.00;
			double netamt = 0.00;
			double tdsamt = 0.00;

			public void keyPressed(KeyEvent evt) 
			{
				int column = oedTable.getSelectedColumn();
				int row = oedTable.getSelectedRow();
				int totRow=oedTable.getRowCount();
				rrow = row;

				if (evt.getKeyCode()>=112 && evt.getKeyCode()<=123)
				{
					if (column == 7) 
					{
						oedTable.changeSelection(row, 4, false, false);
						oedTable.editCellAt(row, 4);
//						oedTable.setValueAt(getFunctionKeyValue(evt),row, 7 );
						oedTable.changeSelection(row, 7, false, false);
						oedTable.editCellAt(row, 7);
					}					
				}

				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) 
				{
					if (column == 1) 
					{
						String s="";
						try
						{
							oedTable.changeSelection(row, 3, false, false);
							oedTable.editCellAt(row,3);
							s = oedTable.getValueAt(row, 1).toString().trim();
							
						}
						catch(NullPointerException e)
						{
							s="";
							System.out.println("Exception in 1st column null pointer "+e );
						}
						
						if (s.equals(""))
						{
								Partypane.setVisible(true);
								partyList.setVisible(true);
								Partypane.requestFocus();
								partyList.requestFocus();
								partyList.setSelectedIndex(0);
								// //// setting sroller to top//////////////
								partyList.ensureIndexIsVisible(0);
							
						}
						else
						{
							 
							prtyDto = (PartyDto) partyMap.get(s);
							if (prtyDto!=null)
							{
							partyCd=prtyDto.getMac_code();
							
/*							sertax_apply = prtyDto.getSertax_apply();
							sertax_billper = prtyDto.getSertax_billper();
							sertax_per = prtyDto.getSertax_per();

							subv = (Vector) pdao.getSubList(loginDt.getDepo_code(),loginDt.getDiv_code(),partyCd);
*/							if (subv!=null)
								subCList.setListData(subv);
							
							oedTable.changeSelection(row, 3, false, false);
							oedTable.editCellAt(row,3);
							oedTable.setValueAt(prtyDto.getMac_code(), row, 1);
							oedTable.setValueAt(prtyDto.getMac_name(), row, 2);
							oedTable.setValueAt(prtyDto.getMsub_code(), row, 23);
							oedTable.setValueAt(prtyDto.getMgrp_code(), row, 22);
							oedTable.setValueAt(0.00, row, 25);
							oedTable.setValueAt(0.00, row, 26);

							
							oedTable.changeSelection(row, 2, false, false);
							oedTable.editCellAt(row, 2);
							}
							else
							{
								oedTable.changeSelection(row, 2, false, false);
								oedTable.editCellAt(row, 2);
								oedTable.setValueAt("", row, 1);
								oedTable.changeSelection(row, 1, false, false);
								oedTable.editCellAt(row, 1);
								
							}
						}
					}
					if (column == 2) 
					{
						String s="";
						try
						{
							s = oedTable.getValueAt(row, 1).toString().trim();
							
						}
						catch(NullPointerException e)
						{
							s="";
							System.out.println("Exception in 1st column null pointer "+e );
						}
						if (s.equals(""))
						{
							oedTable.changeSelection(row, 1, false, false);
							oedTable.editCellAt(row, 1);
						}
						else
						{
							oedTable.changeSelection(row, 3, false, false);
							oedTable.editCellAt(row, 3);
						}
					}
					if (column == 3) 
					{

						if (subv!=null)
						{
							if (oedTable.getValueAt(row, 3).equals(""))
							{
								subCPane.setVisible(true);
								subCList.setVisible(true);
								subCPane.requestFocus();
								subCList.requestFocus();
								subCList.setSelectedIndex(0);
								// //// setting sroller to top//////////////
								subCList.ensureIndexIsVisible(0);
							}
							else
							{
								oedTable.changeSelection(row, 4, false, false);
								oedTable.editCellAt(row, 4);
								lblbrandname.setText("Supplier");
								panel_22.setVisible(true);
								panel_22.requestFocus();
								narration.requestFocus();
								narration.setText(oedTable.getValueAt(row, 4).toString());

							}
						}
						else
						{
							oedTable.changeSelection(row, 4, false, false);
							oedTable.editCellAt(row, 4);
							panel_22.setVisible(true);
							lblbrandname.setText("Supplier");
							panel_22.requestFocus();
							narration.requestFocus();
							if(oedTable.getValueAt(row, 4)!=null)
								narration.setText(oedTable.getValueAt(row, 4).toString());
							else
								narration.setText("");

						}
					}
					if (column == 4) 
					{
						oedTable.changeSelection(row, 5, false, false);
						oedTable.editCellAt(row, 5);

					}
					if (column == 5) 
					{
						oedTable.changeSelection(row, 6, false, false);
						oedTable.editCellAt(row, 6);
					}

					if (column == 6) 
					{
						
						oedTable.changeSelection(row, 7, false, false);
						oedTable.editCellAt(row, 7);
						panel_22.setVisible(true);
						lblbrandname.setText("Narration");
						panel_22.requestFocus();
						narration.requestFocus();
						if(oedTable.getValueAt(row, 7)!=null)
							narration.setText(oedTable.getValueAt(row, 7).toString());
						else
							narration.setText("");
					}

					if (column == 7) 
					{
						oedTable.changeSelection(row, 8, false, false);
						oedTable.editCellAt(row, 8);
					}
					if (column == 8) // bill_amt
					{
						oedTable.changeSelection(row, 2, false, false);
						oedTable.editCellAt(row, 2);
						dbval=setDoubleNumber(oedTable.getValueAt(row, 8).toString());
						oedTable.setValueAt(formatter.format(dbval), row, 8);
						oedTable.changeSelection(row, 9, false, false);
						oedTable.editCellAt(row, 9);
					}
					if (column == 9) // taxable
					{

						oedTable.changeSelection(row, 2, false, false);
						oedTable.editCellAt(row, 2);

						oedTable.setValueAt("N",row, 10);
						oedTable.setValueAt("N",row, 11);

						
						dbval = setDoubleNumber(oedTable.getValueAt(row, 9).toString());
						oedTable.setValueAt(formatter.format(dbval), row, 9);
						oedTable.changeSelection(row, 10, false, false);
						oedTable.editCellAt(row, 10);
					}


					if (column == 10)  // rcm y/n 
					{  
						oedTable.changeSelection(row, 12, false, false);
						oedTable.editCellAt(row, 12);
						if (oedTable.getValueAt(row, 10).toString().equals("Y"))
							oedTable.setValueAt("Y",row, 11);
						oedTable.changeSelection(row, 11, false, false);
						oedTable.editCellAt(row, 11);
							
					}

					if (column == 11)  // itc y/n
					{
						oedTable.changeSelection(row, 12, false, false);
						oedTable.editCellAt(row, 12);
						if(oedTable.getValueAt(row, 11).toString().equals("XX"))
						{
							oedTable.setValueAt(formatter.format(0.00),row, 12);
							oedTable.setValueAt(formatter.format(0.00),row, 13);
							oedTable.setValueAt(formatter.format(0.00),row, 14);
							oedTable.setValueAt(formatter.format(0.00),row, 15);
							oedTable.setValueAt(formatter.format(0.00),row, 16);
							oedTable.setValueAt(formatter.format(0.00),row, 17);
							oedTable.setValueAt(formatter.format(dbval),row, 18);
							oedTable.setValueAt(formatter.format(dbval),row, 20);
							oedTable.changeSelection(row, 19, false, false); // tds
							oedTable.editCellAt(row, 19);
							
						}

						else
						{
							oedTable.changeSelection(row, 12, false, false);
							oedTable.editCellAt(row, 12);
						}

					
					}


					if (column == 12)  // tax %
					{
						oedTable.changeSelection(row, 4, false, false);
						oedTable.editCellAt(row, 4);
						dbval = setDoubleNumber(oedTable.getValueAt(row, 12).toString());
						oedTable.setValueAt(formatter.format(dbval), row,12);

						double t = setDoubleNumber(oedTable.getValueAt(row, 12).toString().trim());
						oedTable.changeSelection(row, 26, false, false);
						oedTable.editCellAt(row, 26);
						net = setDoubleNumber(oedTable.getValueAt(row, 9).toString());
						tax = setDoubleNumber(oedTable.getValueAt(row, 12).toString());
						taxamt=(net*tax)/100;
						netamt=net+taxamt;
						
						oedTable.setValueAt(formatter.format((taxamt/2)),row, 13);
						oedTable.setValueAt(formatter.format((taxamt/2)),row, 14);
						oedTable.setValueAt(formatter.format(0.00),row, 15);
						oedTable.setValueAt(formatter.format(0.00),row, 16);
						oedTable.setValueAt(formatter.format(0.00),row, 17);
						oedTable.setValueAt(formatter.format(netamt),row, 18);
						oedTable.setValueAt(formatter.format(netamt),row, 20);
						oedTable.changeSelection(row, 13, false, false);
						oedTable.editCellAt(row, 13);
					}

					if (column == 13) // CGST amt
					{
						oedTable.changeSelection(row, 14, false, false);
						oedTable.editCellAt(row, 14);
						dbval = setDoubleNumber(oedTable.getValueAt(row, 13).toString());
						oedTable.setValueAt(formatter.format(dbval), row,13);

					}
					if (column == 14) // SGST amt
					{
						oedTable.changeSelection(row, 26, false, false);
						oedTable.editCellAt(row, 26);
						dbval = setDoubleNumber(oedTable.getValueAt(row, 14).toString());
						oedTable.setValueAt(formatter.format(dbval), row,14);

						net= setDoubleNumber(oedTable.getValueAt(row, 9).toString());
						cgst = setDoubleNumber(oedTable.getValueAt(row, 13).toString());
						sgst = setDoubleNumber(oedTable.getValueAt(row, 14).toString());
						netamt=net+cgst+sgst;
						oedTable.setValueAt(formatter.format(netamt),row, 18);
						oedTable.setValueAt(formatter.format(netamt),row, 20);
						oedTable.changeSelection(row, 16, false, false);
						oedTable.editCellAt(row, 16);

					}
					if (column == 15) // IGST amt
					{
						oedTable.changeSelection(row, 26, false, false);
						oedTable.editCellAt(row, 26);
						dbval = setDoubleNumber(oedTable.getValueAt(row, 15).toString());
						oedTable.setValueAt(formatter.format(dbval), row,15);

						net= setDoubleNumber(oedTable.getValueAt(row, 9).toString());
						cgst = setDoubleNumber(oedTable.getValueAt(row, 13).toString());
						sgst = setDoubleNumber(oedTable.getValueAt(row, 14).toString());
						igst = setDoubleNumber(oedTable.getValueAt(row, 15).toString());
						netamt=net+cgst+sgst+igst;

						oedTable.setValueAt(formatter.format(netamt),row, 18);
						oedTable.setValueAt(formatter.format(netamt),row, 20);
						oedTable.changeSelection(row, 16, false, false);
						oedTable.editCellAt(row, 16);
	


					}
					if (column == 16) // CESS PER
					{
						oedTable.changeSelection(row, 19, false, false);
						oedTable.editCellAt(row, 19);
						net= setDoubleNumber(oedTable.getValueAt(row, 9).toString());
						cgst = setDoubleNumber(oedTable.getValueAt(row, 13).toString());
						sgst = setDoubleNumber(oedTable.getValueAt(row, 14).toString());
						igst = setDoubleNumber(oedTable.getValueAt(row, 15).toString());
						netamt=net+cgst+sgst+igst;
						oedTable.setValueAt(formatter.format(netamt), row, 18);
						oedTable.setValueAt(formatter.format(netamt),row, 20);
						tax = setDoubleNumber(oedTable.getValueAt(row, 16).toString());
						taxamt=(net*tax)/100;
						oedTable.setValueAt(formatter.format(taxamt),row, 17);  // CESS AMOUNT

						
						oedTable.changeSelection(row, 17, false, false);
						oedTable.editCellAt(row, 17);
						
					}
					if (column == 17) // CESS amt
					{
						oedTable.changeSelection(row, 20, false, false);
						oedTable.editCellAt(row, 20);
						netamt = setDoubleNumber(oedTable.getValueAt(row, 18).toString());
						n3 = setDoubleNumber(oedTable.getValueAt(row, 17).toString());
						netamt=netamt+n3;
						oedTable.setValueAt(formatter.format(netamt), row, 18);
						netamt=netamt-tdsa;
						oedTable.setValueAt(formatter.format(netamt), row, 20);
						oedTable.changeSelection(row, 19, false, false);
						oedTable.editCellAt(row, 19);
						
					}

					
					if (column == 18) // net amt
					{
						oedTable.changeSelection(row, 19, false, false);
						oedTable.editCellAt(row, 19);
					}


					if (column == 19) // tds
					{
						oedTable.changeSelection(row, 2, false, false);
						oedTable.editCellAt(row, 2);
						tds=setDoubleNumber(oedTable.getValueAt(row, 19).toString());
						oedTable.setValueAt(formatter.format(tds), row, 19);
						dbval=setDoubleNumber(oedTable.getValueAt(row, 8).toString());
						oedTable.setValueAt(formatter.format((dbval-tds)), row, 20);
						oedTable.changeSelection(row, 20, false, false);
						oedTable.editCellAt(row, 20);
					}

					if (column == 20) // BALANCE
					{
						oedTable.changeSelection(row, 2, false, false);
						oedTable.editCellAt(row, 2);
						dbval=setDoubleNumber(oedTable.getValueAt(row, 8).toString());
						oedTable.setValueAt(formatter.format((dbval-tds)), row, 20);
					 	totPara();
						double tot=setDoubleNumber(total.getText().toString());
						if(tot>9999)
						{
							alertMessage(CashVoucherAddGST.this, "##Voucher Amount Exceeds Rs. 9999.00") ;							
							oedTable.changeSelection(row, 2, false, false);
							oedTable.editCellAt(row, 2);
							oedTable.setValueAt(0.00, row, 8);
							oedTable.setValueAt(0.00, row, 20);
							oedTable.changeSelection(row, 8, false, false);
							oedTable.editCellAt(row, 8);
							totPara();
						}
						else
						{
							oedTable.changeSelection(row, 27, false, false);
							oedTable.editCellAt(row, 27);
						}
					}

					if (column == 27) // balance 
					{
							if (option==1 || option==2 || addNewRow)  // for add button
							{

								if(addNewRow)
								{
									ArrayList l = vouAddNewRow(); 
									recordNo= CashDao.addLedGST(l);
								}


								if (row == (totRow - 1))
									row = -1;
								//oedTableModel.addRow(new Object[] {});
								if(addNewRow)
								{
									oedTableModel.addRow(new Object[] {false,"","","","","","  /  /    ","",0.00,0.00,0.00,0.00,0.00,"","","","",++sNo,0.00,0.00});
								}
								else
								{
									oedTableModel.addRow(new Object[] {false,"","","","","","  /  /    ","",0.00,0.00,0.00,0.00,0.00,"","","","",0.00,0.00});	
								}

								oedTable.changeSelection(totRow, 1, false, false);
								oedTable.editCellAt(totRow, 1);
							}

							if (option==0)  // for Edit & Save
							{
								if(row<totRow-1)
								{
									oedTable.changeSelection(row+1, 1, false, false);
									oedTable.editCellAt(row+1, 1);
								}
								else
								{
									oedTable.changeSelection(row, 1, false, false);
									oedTable.editCellAt(row, 1);
								}
							}
							totPara();
					}

					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					//oedPane.setVisible(false);
					btnSave.requestFocus();
					btnSave.setBackground(Color.blue);

					evt.consume();
				}

			}// /// keypressed

		};

		oedTable.addKeyListener(crTableListener);

		//TODO VOUTABLE MOUSE LISTNER
		oedTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e)
			{
				int row = oedTable.getSelectedRow();
				int col = oedTable.getSelectedColumn();
				  if(col==7 || col==4)
				  {
					  panel_22.setVisible(true);
					  panel_22.requestFocus();
					  if(col==7)
						  lblbrandname.setText("Narration");
					  if(col==4)
						  lblbrandname.setText("Supplier");

					  narration.requestFocus();
					  narration.setText(oedTable.getValueAt(row, col).toString());
				  }
		}
			public void mousePressed(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent e) 
			{
				int row = oedTable.getSelectedRow();
				int col = oedTable.getSelectedColumn();
				  if(col==7 || col==4)
				  {
					  panel_22.setVisible(true);
					  if(col==7)
						  lblbrandname.setText("Narration");
					  if(col==4)
						  lblbrandname.setText("Supplier");

					  panel_22.requestFocus();
					  narration.requestFocus();
					  narration.setText(oedTable.getValueAt(row, col).toString());
				  }
			}
			
			
		});			


		// ////////////////////////////////////////////////////////////////
		partyList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = partyList.getSelectedIndex();
					prtyDto = (PartyDto) partyNames.get(idx);
					partyCd = prtyDto.getMac_code();

/*					sertax_apply = prtyDto.getSertax_apply();
					sertax_billper = prtyDto.getSertax_billper();
					sertax_per = prtyDto.getSertax_per();

					subv = (Vector) pdao.getSubList(loginDt.getDepo_code(),loginDt.getDiv_code(),partyCd);
*/					if (subv!=null)
						subCList.setListData(subv);
					
					oedTable.changeSelection(rrow, 3, false, false);
					oedTable.editCellAt(rrow,3);
					oedTable.setValueAt(prtyDto.getMac_code(), rrow, 1);
					oedTable.setValueAt(prtyDto.getMac_name(), rrow, 2);
					oedTable.setValueAt(prtyDto.getMsub_code(), rrow, 23);
					oedTable.setValueAt(prtyDto.getMgrp_code(), rrow, 22);
					oedTable.setValueAt(0.00, rrow, 25);
					oedTable.setValueAt(0.00, rrow, 26);

					// ///////////////////////////////////////////////////
					 

					 

					// TODO
					// evt.consume();
					Partypane.setVisible(false);
					// inv_date.requestFocus();
					oedTable.requestFocus();
					oedTable.changeSelection(rrow, 3, false, false);
					oedTable.editCellAt(rrow,3);
				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Partypane.setVisible(false);
					oedTable.requestFocus();
					oedTable.changeSelection(rrow, 1, false, false);
					oedTable.editCellAt(rrow,1);
				}

			}
		});
		// ////////////////////////////////////////////////////////////////
		
		// ////////////////////////////////////////////////////////////////
		subCList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = subCList.getSelectedIndex();
					prtyDto = (PartyDto) subv.get(idx);
					partyCd = prtyDto.getMac_code();
					
					oedTable.changeSelection(rrow, 4, false, false);
					oedTable.editCellAt(rrow,4);
					oedTable.setValueAt(prtyDto.getMac_name(), rrow, 3);
					oedTable.setValueAt(prtyDto.getMac_code(), rrow, 15);
					// ///////////////////////////////////////////////////
					 

					 

					// TODO
					// evt.consume();
					subCPane.setVisible(false);
					// inv_date.requestFocus();
					oedTable.requestFocus();
					oedTable.changeSelection(rrow, 4, false, false);
					oedTable.editCellAt(rrow,4);
					
					panel_22.setVisible(true);
				    lblbrandname.setText("Supplier");
					panel_22.requestFocus();
					narration.requestFocus();
					narration.setText(oedTable.getValueAt(rrow, 4).toString());

				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					subCPane.setVisible(false);
					oedTable.requestFocus();
					oedTable.changeSelection(rrow, 3, false, false);
					oedTable.editCellAt(rrow,3);
				}

			}
		});
		// ////////////////////////////////////////////////////////////////
		// ///////////////////////////////////////////////
		vou_no.setName("0");
		vou_date.setName("1");
		name.setName("2");
		gstno.setName("3");

		vou_no.addKeyListener(keyListener); 
		vou_date.addKeyListener(keyListener);
		name.addKeyListener(keyListener);
		gstno.addKeyListener(keyListener);

		btnSave = new JButton("Save");
		btnSave.setActionCommand("Save");
		btnSave.setBounds(828, 623, 86, 30);
		getContentPane().add(btnSave);
		btnSave.addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent evt) 
			{
				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int recordNo=saveData();
					if (option==1 && recordNo>0)
					{
						//JOptionPane.showMessageDialog(CashVoucherAdd.this,"Voucher No is  "+recordNo," Voucher No.",JOptionPane.INFORMATION_MESSAGE);
						alertMessage(CashVoucherAddGST.this, "Voucher No is  "+recordNo);
						vou_date.requestFocus();
						vou_date.selectAll();

					}
					//option=0;		
				}

				
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					btnExit.requestFocus();
					btnExit.setBackground(Color.blue);
					btnSave.setBackground(null);
					evt.consume();
				}
				 
				
			}
		});
		
		btnExit = new JButton("Exit");
		btnExit.setActionCommand("Exit");
		btnExit.setBounds(917, 623, 86, 30);
		getContentPane().add(btnExit);
		btnExit.addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					btnSave.requestFocus();
					btnSave.setBackground(Color.blue);
					btnExit.setBackground(null);
					evt.consume();
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{	
					option=0;
					btnAdd.setEnabled(true);
					btnMiss.setEnabled(true);
					btnDelete.setEnabled(true);
					btnPrint.setEnabled(true);
					invNoTable.setEnabled(true);
					btnPrint.setEnabled(true);
					vno.setEnabled(true);
					vdate.setEnabled(true);
					clearAll();
					vou_date.setText(formatDate(new Date()));
					dispose();
				}
				
			}
		});
		
		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setBounds(214, 623, 86, 30);
		btnAdd.setMnemonic(KeyEvent.VK_A);
		getContentPane().add(btnAdd);
		
		btnAdd.addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_A) 
				{
					option=1;
					clearAll(); 
					btnAdd.setEnabled(false);
					btnDelete.setEnabled(false);
					btnPrint.setEnabled(false);
					invNoTable.setEnabled(false);
					vno.setEnabled(false);
					vdate.setEnabled(false);
					vou_date.requestFocus();
					vou_date.selectAll();
					
				}

			}
		});

		btnDelete = new JButton("Delete");
		btnDelete.setActionCommand("Delete");
		btnDelete.setBounds(302, 623, 86, 30);
		getContentPane().add(btnDelete);
		
		btnPrint = new JButton("Print");
		btnPrint.setActionCommand("Print");
		btnPrint.setBounds(390, 623, 86, 30);
		getContentPane().add(btnPrint);
		
		lblVoucherNo = new JLabel("Voucher No:");
		lblVoucherNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVoucherNo.setBounds(16, 172, 83, 20);
		getContentPane().add(lblVoucherNo);
		
		vno = new JTextField();
		vno.setFont(new Font("Tahoma", Font.BOLD, 11));
		vno.setBounds(106, 172, 94, 22);
		getContentPane().add(vno);
		
		lblVoucherDate = new JLabel("Voucher Date:");
		lblVoucherDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVoucherDate.setBounds(16, 202, 83, 20);
		getContentPane().add(lblVoucherDate);

		vdate = new JFormattedTextField(sdf);
		vdate.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkDate(vdate);
		vdate.setBounds(106, 202, 94, 22);
		getContentPane().add(vdate);
	
		
		vou_no.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
 					clearAll();
					dispose();
					//System.exit(0);
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					int vouno = setIntNumber(vou_no.getText().toString());
					 
					if (vouno==0)
					{
						vou_no.setText("");
						vou_no.requestFocus();
					}
						
					rcp = (RcpDto) CashDao.getVouDetail(vouno, loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
					if(rcp!=null)
					{
						
						JOptionPane.showMessageDialog(CashVoucherAddGST.this,"Vou No. "+vouno,"Already Exists!!!!!",JOptionPane.INFORMATION_MESSAGE);						
						vou_no.setText("");
						vou_no.requestFocus();
					} 
					 
					evt.consume();
				}
				
			}
		});

		vno.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
 					clearAll();
					dispose();
					//System.exit(0);
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					int vouno = setIntNumber(vno.getText().toString());
					 
					vno.setText("");
					rcp = (RcpDto) CashDao.getVouDetailGST(vouno, loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
					if(rcp==null)
					{
						clearAll();
						JOptionPane.showMessageDialog(CashVoucherAddGST.this,"No Record for Vou No. "+vouno,"Record not found",JOptionPane.INFORMATION_MESSAGE);						
						
					} 
					else
					{
						fillData(rcp);
						fillItemData(rcp);
					}
					evt.consume();
				}
				
			}
		});
		
		

		
		vdate.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearAll();
					dispose();
					//System.exit(0);
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{

					try
					{
						String date=vdate.getText();
						invNoTableModel.getDataVector().removeAllElements();
						if(isValidDate(date))
						{
							
							Vector data = (Vector) CashDao.getVouListGST(sdf.parse(vdate.getText()), loginDt.getDepo_code(),loginDt.getDiv_code());
							fillInvTable(data);
								
						}
						else
						{
							vdate.setValue(null);
							checkDate(vdate);
							vdate.requestFocus();
						}						
						
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					evt.consume();
				}
				
			}
		});
		
	

		
		
		panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBackground(new Color(51, 102, 204));
		panel_3.setBounds(10, 134, 194, 28);
		getContentPane().add(panel_3);
		
		label_4 = new JLabel("Search");
		label_4.setForeground(Color.WHITE);
		panel_3.add(label_4);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 133, 195, 511);
		getContentPane().add(panel);

		lblMessage = new JLabel("Voucher Amount Should Not Exceed Rs. 9999.00/-");
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMessage.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMessage.setForeground(Color.RED);
		lblMessage.setBounds(217, 589, 373, 20);
		getContentPane().add(lblMessage);

		
		lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setForeground(Color.RED);
		lblTotal.setBounds(764, 589, 115, 20);
		getContentPane().add(lblTotal);
		
		total = new JTextField();
		total.setHorizontalAlignment(SwingConstants.RIGHT);
		total.setEditable(false);
		total.setText(formatter.format(xtot));
		total.setBounds(889, 589, 115, 22);
		getContentPane().add(total);
		
		
		btnMiss = new JButton("Add Missing Voucher");
		btnMiss.setActionCommand("Miss");
		btnMiss.setBounds(478, 623, 170, 30);
		getContentPane().add(btnMiss);

		btnAddMore = new JButton("Add Rows");
		btnAddMore.setMnemonic(KeyEvent.VK_A);
		btnAddMore.setEnabled(false);
		btnAddMore.setActionCommand("newRow");
		btnAddMore.setBounds(651, 623, 104, 30);
		getContentPane().add(btnAddMore);
		btnAddMore.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					if(vouNo!=0)
					{
						addNewRecord();
					}
				}
			}
			
		});

		
		
		lblLastNo = new JLabel("Last No:");
		lblLastNo.setForeground(Color.RED);
		lblLastNo.setBounds(227, 149, 65, 20);
		getContentPane().add(lblLastNo);
		
		lastNo = new JLabel("");
		lastNo.setForeground(Color.RED);
		lastNo.setBounds(290, 150, 51, 20);
		getContentPane().add(lastNo);
		lastNo.setText(String.valueOf(getLastNo()));
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(214, 133, 790, 112);
		getContentPane().add(panel_1);
				


		btnAdd.addActionListener(this);
		btnDelete.addActionListener(this);
		btnPrint.addActionListener(this);
		btnMiss.addActionListener(this);
		btnSave.addActionListener(this);
		btnExit.addActionListener(this);
		btnAddMore.addActionListener(this);
		returnButton.addActionListener(this);
	}


	public void clearAll()
	{

		btnAdd.setEnabled(true);
		btnDelete.setEnabled(true);
		btnPrint.setEnabled(true);
		narration.setText("");
		vou_no.setText("");
//		vou_date.setValue(null);
//		checkDate(vou_date);
		vno.setText("");
		vdate.setValue(null);
		checkDate(vdate);
		name.setText("");
		gstno.setText("");
		total.setText("");
		xtot=0.00;
		subv=null;
		
		invNoTableModel.getDataVector().clear();
		//oedTableModel.getDataVector().removeAllElements();

		// /// clear item table data
		 
		
		// /// genrate new row in item table/////////////
		//vouTableModel.addRow(new Object[] {});
		
		oedTableModel.getDataVector().clear(); // Remove all TableData
		oedTableModel.fireTableDataChanged();
		oedTableModel.addRow(new Object[] {});
		oedTableModel.fireTableStructureChanged();
		
		oedTableItemUI();
		//tableModel.fireTableStructureChanged();
		// /// genrate new row in item table/////////////
		
		
		addRecord=false;

		//vno.requestFocus();
		
		addNewRow=false;
		btnSave.setBackground(null);
		btnExit.setBackground(null);

		//setting last no.
		lastNo.setText(String.valueOf(getLastNo()));
		vouNo=0;
		Partypane.setVisible(false);
		subCPane.setVisible(false);
	}



	public ArrayList vouAdd() 
	{
		 
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		Vector col = null;
		RcpDto rcp1 = null;
		ArrayList cashList = new ArrayList();
		int vou= CashDao.getVouNo(loginDt.getDepo_code(),loginDt.getDiv_code(), loginDt.getFin_year(), 10,"CR");
		System.out.println("vou no is "+vou);
		if (vou<0)
		{
			cashList=null;
			return cashList;
		}
		
		Vector oedData = oedTableModel.getDataVector();
		int size=oedData.size();
		try {
			for (int i = 0; i < size-1; i++) {
				col = (Vector) oedData.get(i);

				//trd.setCdnote_amt(setDoubleNumber(col.get(1).toString()));
				rcp1 = new RcpDto();
				rcp1.setDiv_code(loginDt.getDiv_code());
				rcp1.setVdepo_code(loginDt.getDepo_code());
				
				rcp1.setVbook_cd1(10);
//				rcp1.setVmsr_cd(setIntNumber(loginDt.getState_code()));
				
				if (option==2)
				{
					rcp1.setVou_no(setIntNumber(vou_no.getText()));
				}
				else
				{
					rcp1.setVou_no(vou+1);
					vou_no.setText(String.valueOf(vou));
				}
				
				rcp1.setVou_lo("PC");
				rcp1.setVou_date(sdf.parse(vou_date.getText()));
				rcp1.setVac_code("040"+String.format("%02d", loginDt.getDepo_code())+"01");

				if (col.get(1)!=null)
					rcp1.setExp_code(col.get(1).toString());
				
				if (col.get(4)!=null)
					rcp1.setVnart1(col.get(4).toString());
				
				if (col.get(5)!=null)
					rcp1.setBill_no(col.get(5).toString());
				if (col.get(6)!=null)
					rcp1.setBill_date(formatDate(col.get(6).toString()));

				if (col.get(7)!=null)
					rcp1.setVnart2(col.get(7).toString());
				
				if (col.get(8)!=null)
				rcp1.setBill_amt(setDoubleNumber(col.get(8).toString()));

				if (col.get(9)!=null)  // SERVICE TAX PAYABLE
				rcp1.setCr_amt(setDoubleNumber(col.get(9).toString()));

				if (col.get(10) != null && col.get(10).toString().trim().equals("Y"))
					rcp1.setVreg_cd(1); // rcm ='Y'

				if (col.get(11) != null && col.get(11).toString().trim().equals("Y"))
					rcp1.setVarea_cd(1); // itc ='Y'

/*				rcp1.setCgstamt((setDoubleNumber(col.get(13).toString())));
				rcp1.setSgstamt((setDoubleNumber(col.get(14).toString())));
				rcp1.setIgstamt((setDoubleNumber(col.get(15).toString())));

				if(rcp1.getIgstamt()>0)
				{
					rcp1.setItaxper((setDoubleNumber(col.get(12).toString())));
					rcp1.setStaxper(0.00);
					rcp1.setCtaxper(0.00);
				}
				else
				{
					rcp1.setCtaxper((setDoubleNumber(col.get(12).toString()))/2);
					rcp1.setStaxper((setDoubleNumber(col.get(12).toString()))/2);
					rcp1.setItaxper(0.00);

				}

				rcp1.setCgstamt((setDoubleNumber(col.get(13).toString())));
				rcp1.setSgstamt((setDoubleNumber(col.get(14).toString())));
				rcp1.setIgstamt((setDoubleNumber(col.get(15).toString())));

				rcp1.setCess_per((setDoubleNumber(col.get(16).toString())));
				rcp1.setCess_amt((setDoubleNumber(col.get(17).toString())));

				rcp1.setNetamt((setDoubleNumber(col.get(18).toString())));
				rcp1.setHsn_code((setIntNumber(col.get(27).toString())));

*/				
				if (col.get(19)!=null)
				rcp1.setTds_amt(setDoubleNumber(col.get(19).toString()));
				if (col.get(20)!=null)
				rcp1.setVamount(setDoubleNumber(col.get(20).toString()));

				if (col.get(23)!=null)	rcp1.setVbook_cd(25);

				rcp1.setSertax_billper(0.00);
				rcp1.setSertax_per(0.00);

				if (col.get(22)!=null)
					rcp1.setVgrp_code(setIntNumber(col.get(22).toString()));

				if (col.get(23)!=null)
					rcp1.setSub_code(col.get(23).toString());

				if (col.get(23)!=null && col.get(23).toString().equals("25"))
					rcp1.setSub_code("");

				rcp1.setFin_year(loginDt.getFin_year());
				rcp1.setMnth_code(loginDt.getMnth_code());
				rcp1.setCreated_by(loginDt.getLogin_id());
				rcp1.setCreated_date(new Date());
				rcp1.setParty_name(name.getText());
//				rcp1.setGst_no(gstno.getText());
				rcp1.setSerialno(i+1);
				cashList.add(rcp1);

				}
 
			 
			 
		} catch (Exception e) {
			System.out.println("Error in vouAdd "+e);
		}
		
		
		return cashList;

	}


	public ArrayList vouAddNewRow() 
	{
		RcpDto rcp = null;
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
		
		RcpDto rcp1 = null;
		ArrayList cashList = new ArrayList();
		int row = oedTable.getSelectedRow();
		
		try 
		{
			//trd.setCdnote_amt(setDoubleNumber(col.get(1).toString()));
			rcp1 = new RcpDto();
			rcp1.setDiv_code(loginDt.getDiv_code());
			rcp1.setVdepo_code(loginDt.getDepo_code());
			
			rcp1.setVbook_cd1(10);
			rcp1.setVou_no(setIntNumber(vou_no.getText()));
			rcp1.setVou_lo("PC");
			rcp1.setVou_date(sdf.parse(vou_date.getText()));
			rcp1.setVac_code("040"+String.format("%02d", loginDt.getDepo_code())+"01");

//			rcp1.setVmsr_cd(setIntNumber(loginDt.getState_code()));
			
			//getting the value from oed table (mainTable)
			Object expCode = oedTable.getValueAt(row, 1);
			Object suplirName =  oedTable.getValueAt(row,4);
			Object narration1 =  oedTable.getValueAt(row,7);
			double billAmount = setDoubleNumber(oedTable.getValueAt(row,8).toString().trim());
			double taxableamt = setDoubleNumber(oedTable.getValueAt(row,9).toString().trim());
			double sertax =0.00;

			
			double tdsAmount = setDoubleNumber(oedTable.getValueAt(row,19).toString().trim());
			double rowAmount = setDoubleNumber(oedTable.getValueAt(row,20).toString().trim());
			//int vVookCD = setIntNumber(oedTable.getValueAt(row,11).toString().trim());
			int vGrpCode = setIntNumber(oedTable.getValueAt(row,22).toString().trim());
			Object vSubCode = oedTable.getValueAt(row,23);
			


			if (expCode!=null)
				rcp1.setExp_code(expCode.toString().trim());
			
			if (suplirName!=null)
				rcp1.setVnart1(suplirName.toString().trim());
			
			if (vSubCode!=null)
				rcp1.setSub_code(vSubCode.toString().trim());
			
			if (vSubCode!=null && vSubCode.toString().equals("25"))
				rcp1.setSub_code("");

			
			if (narration1!=null)
				rcp1.setVnart2(narration1.toString().trim());
			
				rcp1.setBill_amt(billAmount);
				rcp1.setCr_amt(taxableamt);
				rcp1.setSertax_amt(sertax);

				
				rcp1.setTds_amt(tdsAmount);
				rcp1.setVamount(rowAmount);

				
				

				if (oedTable.getValueAt(row,5)!=null)
					rcp1.setBill_no(oedTable.getValueAt(row,5).toString());
				if (oedTable.getValueAt(row,6)!=null)
					rcp1.setBill_date(formatDate(oedTable.getValueAt(row,6).toString()));
				if (oedTable.getValueAt(row,10)!= null && oedTable.getValueAt(row,10).toString().trim().equals("Y"))
					rcp1.setVreg_cd(1); // rcm ='Y'

				if (oedTable.getValueAt(row,11) != null && oedTable.getValueAt(row,11).toString().trim().equals("Y"))
					rcp1.setVarea_cd(1); // itc ='Y'

/*				rcp1.setCgstamt((setDoubleNumber(oedTable.getValueAt(row,13).toString())));
				rcp1.setSgstamt((setDoubleNumber(oedTable.getValueAt(row,14).toString())));
				rcp1.setIgstamt((setDoubleNumber(oedTable.getValueAt(row,15).toString())));

				if(rcp1.getIgstamt()>0)
				{
					rcp1.setItaxper((setDoubleNumber(oedTable.getValueAt(row,12).toString())));
					rcp1.setStaxper(0.00);
					rcp1.setCtaxper(0.00);
				}
				else
				{
					rcp1.setCtaxper((setDoubleNumber(oedTable.getValueAt(row,12).toString()))/2);
					rcp1.setStaxper((setDoubleNumber(oedTable.getValueAt(row,12).toString()))/2);
					rcp1.setItaxper(0.00);

				}


				rcp1.setCess_per((setDoubleNumber(oedTable.getValueAt(row,16).toString())));
				rcp1.setCess_amt((setDoubleNumber(oedTable.getValueAt(row,17).toString())));

				rcp1.setNetamt((setDoubleNumber(oedTable.getValueAt(row,18).toString())));
				rcp1.setHsn_code((setIntNumber(oedTable.getValueAt(row,27).toString())));
*/
				
				if (oedTable.getValueAt(row,23)!=null)	rcp1.setVbook_cd(25);

				rcp1.setSertax_billper(0.00);
				rcp1.setSertax_per(0.00);

				if (oedTable.getValueAt(row,22)!=null)
					rcp1.setVgrp_code(setIntNumber(oedTable.getValueAt(row,22).toString()));

				if (oedTable.getValueAt(row,23)!=null)
					rcp1.setSub_code(oedTable.getValueAt(row,23).toString());

				if (oedTable.getValueAt(row,23)!=null && oedTable.getValueAt(row,23).toString().equals("25"))
					rcp1.setSub_code("");
			
			rcp1.setFin_year(loginDt.getFin_year());
			rcp1.setMnth_code(loginDt.getMnth_code());
			rcp1.setCreated_by(loginDt.getLogin_id());
			rcp1.setCreated_date(new Date());
			rcp1.setParty_name(name.getText());
//			rcp1.setGst_no(gstno.getText());
			rcp1.setSerialno(sNo+1);
			cashList.add(rcp1);
			 
			 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Error in vouAdd "+e);
			e.printStackTrace();
		}
		
		
		return cashList;

	}
	
	
	
	public ArrayList vouUpdate() 
	{
		RcpDto rcp = null;
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		Vector col = null;
		RcpDto rcp1 = null;
		ArrayList cashList = new ArrayList();
		 
		 
		Vector oedData = oedTableModel.getDataVector();
		try {
			for (int i = 0; i < oedData.size(); i++) {
				col = (Vector) oedData.get(i);

				 
				rcp1 = new RcpDto();
				rcp1.setDiv_code(loginDt.getDiv_code());
				rcp1.setVdepo_code(loginDt.getDepo_code());
				rcp1.setVbook_cd1(10);
 				rcp1.setVou_no(setIntNumber(vou_no.getText()));
				rcp1.setVou_date(sdf.parse(vou_date.getText()));
				 
				if (col.get(1)!=null)
					rcp1.setExp_code(col.get(1).toString());
				if (col.get(4)!=null)
					rcp1.setVnart1(col.get(4).toString());

				if (col.get(5)!=null)
					rcp1.setBill_no(col.get(5).toString());
				if (col.get(6)!=null)
					rcp1.setBill_date(formatDate(col.get(6).toString()));

				if (col.get(7)!=null)
					rcp1.setVnart2(col.get(7).toString());

				if (col.get(8)!=null)
				rcp1.setBill_amt(setDoubleNumber(col.get(8).toString()));

				if (col.get(9)!=null)
				rcp1.setCr_amt(setDoubleNumber(col.get(9).toString()));

				
				if (col.get(10) != null && col.get(10).toString().trim().equals("Y"))
					rcp1.setVreg_cd(1); // rcm ='Y'

				if (col.get(11) != null && col.get(11).toString().trim().equals("Y"))
					rcp1.setVarea_cd(1); // itc ='Y'

/*				rcp1.setCgstamt((setDoubleNumber(col.get(13).toString())));
				rcp1.setSgstamt((setDoubleNumber(col.get(14).toString())));
				rcp1.setIgstamt((setDoubleNumber(col.get(15).toString())));

				if(rcp1.getIgstamt()>0)
				{
					rcp1.setItaxper((setDoubleNumber(col.get(12).toString())));
					rcp1.setStaxper(0.00);
					rcp1.setCtaxper(0.00);
				}
				else
				{
					rcp1.setCtaxper((setDoubleNumber(col.get(12).toString()))/2);
					rcp1.setStaxper((setDoubleNumber(col.get(12).toString()))/2);
					rcp1.setItaxper(0.00);

				}

				rcp1.setCgstamt((setDoubleNumber(col.get(13).toString())));
				rcp1.setSgstamt((setDoubleNumber(col.get(14).toString())));
				rcp1.setIgstamt((setDoubleNumber(col.get(15).toString())));

				rcp1.setCess_per((setDoubleNumber(col.get(16).toString())));
				rcp1.setCess_amt((setDoubleNumber(col.get(17).toString())));

				rcp1.setNetamt((setDoubleNumber(col.get(18).toString())));
				rcp1.setHsn_code((setIntNumber(col.get(27).toString())));				

*/				if (col.get(19)!=null)
				rcp1.setTds_amt(setDoubleNumber(col.get(19).toString()));
				if (col.get(20)!=null)
				rcp1.setVamount(setDoubleNumber(col.get(20).toString()));

				rcp1.setVbook_cd(setIntNumber(col.get(21).toString()));

				
				if (col.get(22)!=null)
					rcp1.setVgrp_code(setIntNumber(col.get(22).toString()));
				if (col.get(23)!=null)	rcp1.setSub_code(col.get(23).toString());

				rcp1.setFin_year(loginDt.getFin_year());
				rcp1.setModified_by(loginDt.getLogin_id());
				rcp1.setModified_date(new Date());
				rcp1.setParty_name(name.getText());
//				rcp1.setGst_no(gstno.getText());
				rcp1.setSerialno(setIntNumber(col.get(24).toString()));
				cashList.add(rcp1);

				}
 
			 
			 
		} catch (Exception e) {
			System.out.println("Error in vouUpdate "+e);
		}
		
		
		return cashList;

	}


	public ArrayList vouDelete() 
	{
		RcpDto rcp = null;
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		Vector col = null;
		RcpDto rcp1 = null;
		ArrayList cashList = new ArrayList();
		 
		 
		Vector oedData = oedTableModel.getDataVector();
		try {
			for (int i = 0; i < oedData.size(); i++) {
				col = (Vector) oedData.get(i);

				 
					if ((Boolean) col.get(0))
					{
						rcp1 = new RcpDto();
						rcp1.setDiv_code(loginDt.getDiv_code());
						rcp1.setVdepo_code(loginDt.getDepo_code());
						rcp1.setVbook_cd1(10);
		 				rcp1.setVou_no(setIntNumber(vou_no.getText()));
						rcp1.setFin_year(loginDt.getFin_year());
						rcp1.setModified_by(loginDt.getLogin_id());
						rcp1.setModified_date(new Date());
						rcp1.setSerialno(setIntNumber(col.get(24).toString()));
						cashList.add(rcp1);
					} // end of if 

				} // end of for loop 
 
			 
			 
		} catch (Exception e) {
			System.out.println("Error in vouDelete "+e);
		}
		
		
		return cashList;

	}


	public ArrayList vouPrint() 
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		Vector col = null;
		RcpDto rcp1 = null;
		ArrayList<RcpDto> cashList = new ArrayList<RcpDto>();
		
		 
		Vector oedData = oedTableModel.getDataVector();
		try {
			for (int i = 0; i < oedData.size(); i++) {
				col = (Vector) oedData.get(i);

				//trd.setCdnote_amt(setDoubleNumber(col.get(1).toString()));
				rcp1 = new RcpDto();
				rcp1.setVdepo_code(loginDt.getDepo_code());
				rcp1.setVou_no(setIntNumber(vou_no.getText()));
				rcp1.setVou_lo("PC");
				rcp1.setVou_date(sdf.parse(vou_date.getText()));
				rcp1.setParty_name(name.getText());
				
				if (col.get(1)!=null)
					rcp1.setExp_code(col.get(1).toString());
				if (col.get(2)!=null)
					rcp1.setExp_name(col.get(2).toString());
				
				if (col.get(4)!=null)
					rcp1.setVnart1(col.get(4).toString());
				if (col.get(5)!=null)
					rcp1.setBill_no(col.get(5).toString());
				if (col.get(6)!=null)
					rcp1.setBill_date(formatDate(col.get(6).toString()));
				
				
				
				if (col.get(7)!=null)
					rcp1.setVnart2(col.get(7).toString());

				if (col.get(8)!=null)
				rcp1.setBill_amt(setDoubleNumber(col.get(8).toString()));

				if (col.get(9)!=null)
				rcp1.setCr_amt(setDoubleNumber(col.get(9).toString()));

				if (col.get(10)!=null)
				rcp1.setSertax_amt(setDoubleNumber(col.get(10).toString()));

				
				if (col.get(11)!=null)
				rcp1.setTds_amt(setDoubleNumber(col.get(11).toString()));

				if (col.get(12)!=null)
				rcp1.setVamount(setDoubleNumber(col.get(12).toString()));
				
				cashList.add(rcp1);

				}
 
			 
			 
		} catch (Exception e) {
			System.out.println("Error in vouAdd "+e);
		}
		
		
		return cashList;

	}

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		if(e.getActionCommand().equalsIgnoreCase("add"))
		{
			option=1;
			clearAll(); 
			btnAdd.setEnabled(false);
			btnDelete.setEnabled(false);
			btnPrint.setEnabled(false);
			invNoTable.setEnabled(false);
			vno.setEnabled(false);
			vdate.setEnabled(false);
			vou_date.requestFocus();
			vou_date.selectAll();
		
		}
		
		if(e.getActionCommand().equalsIgnoreCase("miss"))
		{
			
			btnAdd.setEnabled(false);
			btnMiss.setEnabled(false);
			btnDelete.setEnabled(false);
			btnPrint.setEnabled(false);
			clearAll(); 
			invNoTable.setEnabled(false);
			vno.setEnabled(false);
			vdate.setEnabled(false);
			
			vou_no.setEditable(true);
			vou_no.requestFocus();
			option=2;
		
		}		
		
		if(e.getActionCommand().equalsIgnoreCase("print"))
		{
			int vbookCD = 10; // cashbook
			
			CashOpt copt = new CashOpt("Cash ","Cash Voucher Print",vouNo,vbookCD,"CR");
			copt.setVisible(true);
			
			
			
			//ArrayList vlist = vouPrint();
			//CashVouPrint1 cp = new CashVouPrint1(loginDt.getDrvnm(), loginDt.getPrinternm(),vlist,loginDt.getDepo_code());
		}


		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			int recordNo=saveData();
			if (option==1 && recordNo>0)
			{
				//JOptionPane.showMessageDialog(CashVoucherAdd.this,"Voucher No is  "+recordNo," Voucher No.",JOptionPane.INFORMATION_MESSAGE);
				alertMessage(CashVoucherAddGST.this, "Voucher No is  "+recordNo);
				vou_date.requestFocus();
				vou_date.selectAll();

			}
			//option=0;
		}
		
		if (e.getActionCommand().equalsIgnoreCase("Return")) {

			  int row=oedTable.getSelectedRow();
			  int col=oedTable.getSelectedColumn();
			  panel_22.setVisible(false);
			  oedTable.setValueAt(narration.getText(),row, col);
			  oedTable.requestFocus();
		}

		
		if(e.getActionCommand().equalsIgnoreCase("delete"))
		{
			 int h=0;
			 int answer = 0;
			 
  	    	if (vouDelete().isEmpty())
  	    		   JOptionPane.showMessageDialog(CashVoucherAddGST.this, "Please Select Record First!!!!");
  	    	else
  	    	{ 
			      answer = JOptionPane.showConfirmDialog(CashVoucherAddGST.this, "Are You Sure : ");
			    if (answer == JOptionPane.YES_OPTION) {
			      // User clicked YES.
			    	h = CashDao.deleteLed(vouDelete());
			    	d = CashDao.getCashBalance(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
					cashbal.setText(formatter.format(d));

			    	btnAdd.setEnabled(true);
					invNoTable.setEnabled(true);
					vno.setEnabled(true);
					vdate.setEnabled(true);
					option=0;
					clearAll();
			    } else if (answer == JOptionPane.NO_OPTION) {
			      // User clicked NO.
			    }
  	    	
			
				
  	    	}  // else part end 
		}
		
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			option=0;
			 
			btnAdd.setEnabled(true);
			btnMiss.setEnabled(true);
			btnDelete.setEnabled(true);
			btnPrint.setEnabled(true);
			invNoTable.setEnabled(true);
			btnPrint.setEnabled(true);
			vno.setEnabled(true);
			vdate.setEnabled(true);
			clearAll();
			vou_date.setText(formatDate(new Date()));
			dispose();
		}
		
		if (e.getActionCommand().equalsIgnoreCase("newRow"))
		{
			System.out.println("voucher No is "+vouNo);
			
			if(vouNo!=0)
			{
				addNewRecord();
			}
		}
		

	}



	public void fillInvTable(Vector data)
	{
		//invNoTableModel.getDataVector().removeAllElements();
		Vector c = null;
		int s = data.size();
		for(int i =0;i<s;i++)
		{
			c =(Vector) data.get(i);
			invNoTableModel.addRow(c);
		}
		
           if (s==0)
           {
       		for(int i =0;i<30;i++)
    		{
    			invNoTableModel.addRow(new Object[2][]);
    		}
        	   
        	   
           }
		
	}
	
	public int saveData()
	{
		int recordNo=0;
		
		if (option==1 || option==2)
		{

			double tot=setDoubleNumber(total.getText().toString());
			int row=oedTable.getRowCount()-1;
			if(tot>9999)
			{
				alertMessage(CashVoucherAddGST.this, "Voucher Amount Exceeds Rs. 9999.00") ;							
				
				oedTable.changeSelection(row, 2, false, false);
				oedTable.editCellAt(row, 2);
				oedTable.setValueAt(0.00, row, 8);
				oedTable.setValueAt(0.00, row, 9);
				oedTable.setValueAt(0.00, row, 10);
				oedTable.setValueAt(0.00, row, 11);
				oedTable.setValueAt(0.00, row, 12);
				oedTable.requestFocus();
				oedTable.changeSelection(row, 8, false, false);
				oedTable.editCellAt(row, 8);
				totPara();
				
			}
			else
			{
				ArrayList l = vouAdd();
				recordNo= l!=null ? CashDao.addLedGST(l) :0;
				tot=tot-xtot;
				int j = CashDao.updateBalance(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),tot);
				d = CashDao.getCashBalance(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
				cashbal.setText(formatter.format(d));

				btnAdd.setEnabled(false);
				btnMiss.setEnabled(false);
				btnDelete.setEnabled(false);
				btnPrint.setEnabled(true);
				vou_no.setEditable(false);
				invNoTable.setEnabled(true);
				vno.setEnabled(true);
				vdate.setEnabled(true);
				//option=0;
				clearAll();

			}
		}
		
		if (option==0)
		{
			ArrayList l = vouUpdate();
			CashDao.updateLedGST(l);
			tot=tot-xtot;
			int j = CashDao.updateBalance(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),tot);
			d = CashDao.getCashBalance(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
			cashbal.setText(formatter.format(d));

			btnAdd.setEnabled(false);
			btnMiss.setEnabled(false);
			btnDelete.setEnabled(false);
			btnPrint.setEnabled(true);
			vou_no.setEditable(false);
			invNoTable.setEnabled(true);
			vno.setEnabled(true);
			vdate.setEnabled(true);
			//option=0;
			clearAll();

			
		}
		
		
		return recordNo;

	}
	
	
	public void totPara() 
	{
		Vector col = null;
		tot=0; 
		Vector oedData = oedTableModel.getDataVector();
		int size=0;
		size = oedData.size();
		try {
			for (int i = 0; i < size; i++) 
			{
				col = (Vector) oedData.get(i);
				if (col.get(20)!=null)
				  tot+=setDoubleNumber(col.get(20).toString());
			}
				total.setText(formatter.format(tot));
 
		} catch (Exception e) {
			System.out.println("Error in totPara "+e);
			e.printStackTrace();
		}
	}
	
	

	public void fillData(RcpDto rcp)
	{
		// voucher header
		
		vouNo=rcp.getVou_no();
		
		vou_no.setText(String.valueOf(rcp.getVou_no()));
		vou_date.setText(sdf.format(rcp.getVou_date()));
		name.setText(rcp.getParty_name());
//		gstno.setText(rcp.getGst_no());
		xtot=rcp.getVamount();
		tot=rcp.getVamount();
		total.setText(formatter.format(xtot));
		

		btnAdd.setEnabled(true);
		btnDelete.setEnabled(true);
		btnPrint.setEnabled(true);
		btnSave.setEnabled(true);
		btnMiss.setEnabled(false);
		btnAddMore.setEnabled(true);
		option=0;
		addRecord=true;
		
	}	

	
	public void fillItemData(RcpDto rp)
	{
		// Vou Detail Fill 
		
		oedTableModel.getDataVector().clear();
		
		Vector c = null;
		Vector data =rp.getVdetail();
		for(int i =0;i<data.size();i++)
		{
			c = (Vector) data.get(i);
			oedTableModel.addRow(c);
			//gettig the serial no for adding new row in table
			sNo=setIntNumber(c.get(16).toString());
		}
		
		
	}
	public void fillItemData(Vector data)
	{
		// Vou Detail Fill 
		
		
		oedTableModel.getDataVector().clear();
		Vector c = null;
		int size=data.size();
		for(int i =0;i<size;i++)
		{
			c = (Vector) data.get(i);
			oedTableModel.addRow(c);
			
			//gettig the serial no for adding new row in table
			sNo=setIntNumber(c.get(16).toString());
		}
		
		
	}
	
	
	public void addNewRecord()
	{
		try
		{
			addNewRow=true;
			addRecord=true;
			option=3; 
			oedTableModel.addRow(new Object[] {});
			
			int rowCount =oedTableModel.getRowCount(); 

			oedTable.requestFocus();
			oedTable.changeSelection(rowCount-1, 1, false, false);
			oedTable.editCellAt(rowCount-1, 1);

			
			btnAddMore.setEnabled(false); 
			btnSave.setEnabled(true);
			btnAdd.setEnabled(false);
			btnAddMore.setEnabled(false);
			btnDelete.setEnabled(false);
			btnPrint.setEnabled(false);
		}
		catch(Exception ex)
		{

		}
		
	}
	
	
	
	public void oedTableItemUI()
	{
		oedTable.setColumnSelectionAllowed(false);
		oedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		oedTable.setCellSelectionEnabled(false);
		oedTable.getTableHeader().setReorderingAllowed(false);
		oedTable.getTableHeader().setResizingAllowed(false);
		oedTable.setRowHeight(20);
//		oedTable.getTableHeader().setPreferredSize(new Dimension(25, 25));
//		oedTable.setFont(new Font("Tahoma", Font.PLAIN, 11));


		
		oedTable.getColumnModel().getColumn(0).setPreferredWidth(15); // del_tag
		oedTable.getColumnModel().getColumn(1).setPreferredWidth(100); // ex code
		oedTable.getColumnModel().getColumn(2).setPreferredWidth(140); // ex name
		oedTable.getColumnModel().getColumn(3).setPreferredWidth(100); // sub code
		oedTable.getColumnModel().getColumn(4).setPreferredWidth(150); // ssup name
		oedTable.getColumnModel().getColumn(5).setPreferredWidth(80); // bill no
		oedTable.getColumnModel().getColumn(6).setPreferredWidth(100); // bill date

		oedTable.getColumnModel().getColumn(7).setPreferredWidth(140); // nar
		oedTable.getColumnModel().getColumn(8).setPreferredWidth(100); // amont
		oedTable.getColumnModel().getColumn(9).setPreferredWidth(100); // TAXABLE
		oedTable.getColumnModel().getColumn(10).setPreferredWidth(40); //rcm y/n
		oedTable.getColumnModel().getColumn(11).setPreferredWidth(40); //itc y/n
		oedTable.getColumnModel().getColumn(12).setPreferredWidth(60); // GST tax%
		oedTable.getColumnModel().getColumn(13).setPreferredWidth(70); // CGST
		oedTable.getColumnModel().getColumn(14).setPreferredWidth(70); // SGST
		oedTable.getColumnModel().getColumn(15).setPreferredWidth(70); // IGST
		oedTable.getColumnModel().getColumn(16).setPreferredWidth(70); // cess %
		oedTable.getColumnModel().getColumn(17).setPreferredWidth(70); // cess amt
		oedTable.getColumnModel().getColumn(18).setPreferredWidth(90); // TOTAL

		oedTable.getColumnModel().getColumn(19).setPreferredWidth(100); // tds
		oedTable.getColumnModel().getColumn(20).setPreferredWidth(100); // balance
		
		oedTable.getColumnModel().getColumn(21).setPreferredWidth(0); //hidden
		oedTable.getColumnModel().getColumn(21).setMinWidth(0); //vbook_cd
		oedTable.getColumnModel().getColumn(21).setMaxWidth(0);   
		
		oedTable.getColumnModel().getColumn(22).setPreferredWidth(0); //hidden
		oedTable.getColumnModel().getColumn(22).setMinWidth(0); // vgrp_code 
		oedTable.getColumnModel().getColumn(22).setMaxWidth(0);  
		
		oedTable.getColumnModel().getColumn(23).setPreferredWidth(0); //hidden
		oedTable.getColumnModel().getColumn(23).setMinWidth(0); // subcode/
		oedTable.getColumnModel().getColumn(23).setMaxWidth(0);   

		oedTable.getColumnModel().getColumn(24).setPreferredWidth(0); //hidden
		oedTable.getColumnModel().getColumn(24).setMinWidth(0); // serialno/
		oedTable.getColumnModel().getColumn(24).setMaxWidth(0);   

		
		oedTable.getColumnModel().getColumn(25).setPreferredWidth(0); //bill_per
		oedTable.getColumnModel().getColumn(25).setMinWidth(0); // bill_per
		oedTable.getColumnModel().getColumn(25).setMaxWidth(0);   

		oedTable.getColumnModel().getColumn(26).setPreferredWidth(0); // servicetax_per
		oedTable.getColumnModel().getColumn(26).setMinWidth(0); //  servicetax_per
		oedTable.getColumnModel().getColumn(26).setMaxWidth(0);   
		
		oedTable.getColumnModel().getColumn(27).setPreferredWidth(90); // HSN CODE

		
		oedTable.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(12).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(13).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(14).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(15).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(16).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(17).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(18).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(19).setCellRenderer(rightRenderer);
		oedTable.getColumnModel().getColumn(20).setCellRenderer(rightRenderer);
		oedTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());
		oedTable.setDefaultEditor(Double.class, new OverWriteTableCellEditor());
		
		
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"YN");
		table_pcode.setSelectionStart(0);
		oedTable.getColumnModel().getColumn(10).setCellEditor(new NumberTableCellEditor(table_pcode));

		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"YN");
		table_pcode.setSelectionStart(0);
		oedTable.getColumnModel().getColumn(11).setCellEditor(new NumberTableCellEditor(table_pcode));

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(8).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(9).setCellEditor(new NumberTableCellEditor(dobl));	
	
		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(12).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(13).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(14).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(15).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(16).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(17).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(18).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(19).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		oedTable.getColumnModel().getColumn(20).setCellEditor(new NumberTableCellEditor(dobl));	

		oedTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(f1));

		
		nart1 = new TextField(254);
		nart1.setSelectionStart(0);
		oedTable.getColumnModel().getColumn(7).setCellEditor(new NumberTableCellEditor(nart1));
		
		oedTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "selectNextColumnCell");
		
		
	}
	
	public double getService(double dbval) {

		if (sertax_apply.equalsIgnoreCase("Y"))
			return servicetax = ((dbval * sertax_per)/ 100);
		else
			return 0.00;
	}


	
	public int getLastNo()
	{
		return CashDao.getVouNo(loginDt.getDepo_code(),loginDt.getDiv_code(), loginDt.getFin_year(), 10,"CR");
	}
	
}


