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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.InvPrintDAO;
import com.coldstorage.dao.TransportDAO;
import com.coldstorage.dto.BookDto;
import com.coldstorage.dto.CategoryDto;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.InvViewDto;
import com.coldstorage.dto.ProductDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.util.MouseRobot;
import com.coldstorage.util.NumberTableCellEditor;
import com.coldstorage.util.OverWriteTableCellEditor;

public class InwardUpdation extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	
	Font font,fontHindi;

	private JTextField vou_no;
	private JTextField vac_code;
	private JFormattedTextField vou_date,chq_date,vdate;
	private JLabel lblvouno,lblVoucherNo ; 
	 
	private JLabel lblvoudate,lblVoucherDate,lblNarration,lblPaidTo ;
	private JLabel lblvaccode,lblChqNo,lblChqDate;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_12;
	private JLabel branch;
	private JLabel lblDivision;
	private JLabel division;
	private JLabel lblDispatchEntry;
	private JFormattedTextField table_pcode;
	private JPanel panel_2;
	private SimpleDateFormat sdf;
	private NumberFormat df;
	private DefaultTableModel invNoTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable invNoTable;
	private JScrollPane vouPane;
	String bno;
	String sdate,edate;
	int partyCd,billNo,option,rrow,vouno,categorycd,groupcd,pcode,rqty,tqty;
	InvViewDto rcp;
	BookDto bk;
	TransportDto prtyDto;
	CategoryDto catDto;
	GroupDto grpDto;
	ProductDto pdto;
	InvPrintDAO idao;
	TransportDAO pdao ;
	double tot,vamt;
	private JTextField vno;
	private Font fontPlan;
	private JPanel panel_3;
	private JLabel label_4;
	private JList partyList,categoryList,groupList,productList;
	private JTextField vnart_1;
	private JTextField group;
	private JTextField net_amt,rate,weight,rent_amt,roundoff,quantity;
	private JLabel lblChqAmount,lblTaxable,lblCgst,lblSgst,lblIgst,lblGstTax;
	private JTextField name;
	private JButton btnSave,btnExit,btnAdd,btnPrint,btnMiss,btnDelete;
	NumberFormat formatter;
	int div=0;
	private JScrollPane Partypane,Categorypane,Grouppane,Productpane; 
	private JLabel label_1;
	private JLabel label_3;

	private JLabel lblLastNo;

	private JLabel lastNo;
	private JTextField name1;

	private Vector partyNames,categoryNames,groupNames,productNames;

	private Map partyMap;
	
	private boolean addBool;

	private JTextField pname;

	private JLabel lblRemark;

	private JTextField remark;

	private JTextField pname_hindi;

	private JTextField group_hindi;

	private JTextField name1_hindi;

	private DefaultTableModel CrTableModel;
	private JTable CrDrTable;
	private JScrollPane cddrPane,scrollPane;

	private double sqty;

	private ArrayList markaList;
	OverWriteTableCellEditor cellEditor;

	public InwardUpdation()
	{
		//infoName =(String) helpImg.get(getClass().getSimpleName());
		
		formatter = new DecimalFormat("0.00");
		sdf = new SimpleDateFormat("yyyy-MM-dd");  // Date Format
		sdate=sdf.format(loginDt.getFr_date());
		edate=sdf.format(loginDt.getTo_date());

		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
		df= new DecimalFormat("##########.00");
		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		cellEditor= new OverWriteTableCellEditor();
		
		fontPlan =new Font("Tahoma", Font.PLAIN, 11);
		fontHindi = new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14);
		
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 670);		

		
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	        
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        //this.pack();
		
		//setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		bno="";
		pdao = new TransportDAO();
		idao = new InvPrintDAO();
		
		partyNames = (Vector) pdao.getTransportList(loginDt.getDepo_code(),loginDt.getDiv_code() );
		categoryNames = (Vector) pdao.getCategoryList();
		groupNames = (Vector) pdao.getGroupList(loginDt.getDiv_code());
		productNames = (Vector) pdao.getProductList(1);
		
		
		partyMap = (HashMap) pdao.getPartyNameMap(loginDt.getDepo_code(),loginDt.getDiv_code());

		arisleb.setVisible(false);
		// ////////////////////////////////////////////////////////////////////
//		String[] crDrColName = { "Marka No", "Qty", "Manzil", "Gala No"};
		String[] crDrColName = { "Category","Group","Item","Pack","Qty", "Weight","Marka No", "", "Manzil", "Gala No","","",""};
		String cdDrData[][] = {{}};
		CrTableModel = new DefaultTableModel(cdDrData, crDrColName) {
			private static final long serialVersionUID = 1L;
		
			public boolean isCellEditable(int row, int column) 
			{

				boolean ans=false;
				if(column==6 || column==7 || column==8 || column==9 )
				{
					return true;
				}
				else
					return ans;
			}
			
	
			
			
			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
				case 0 : return String.class;
				case 1 : return String.class;
				case 2 : return String.class;
				case 3 : return String.class;
				
				
				default:
					return String.class;
				}
			}
			
		};
		
		CrDrTable = new JTable(CrTableModel);
		itemTableUI();
		getContentPane().add(CrDrTable);
		CrDrTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		// ////////////////////////////////////////////////////////////////////////
		cddrPane = new JScrollPane(CrDrTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		cddrPane.setBounds(279, 480, 530, 140);
		cddrPane.setBounds(40, 255, 975, 250);
		cddrPane.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		cddrPane.setFont(new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14));

		getContentPane().add(cddrPane);
		cddrPane.setVisible(true);
		
		
		
		
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
		invNoTable.setRowHeight(20);
		invNoTable.getTableHeader().setPreferredSize(new Dimension(25,25));
		///////////////////////////////////////////////////////////////////////////
		invNoTable.getColumnModel().getColumn(0).setPreferredWidth(70);   //contact inv no
		invNoTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //party name/////
		invNoTable.getColumnModel().getColumn(2).setMinWidth(0);  //inv_no/////
		invNoTable.getColumnModel().getColumn(2).setMaxWidth(0);  //inv_no/////
		invNoTable.getColumnModel().getColumn(3).setMinWidth(0);  //table data/////
		invNoTable.getColumnModel().getColumn(3).setMaxWidth(0);  //tabel data/////

		scrollPane = new JScrollPane(invNoTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(16, 210, 283, 500);
		getContentPane().add(scrollPane);
		scrollPane.setVisible(false);

		
		///////////////////////////////////////////////////////
		
		
	
		
		


		
		
		///////////////////////////////////////////////////////

/*		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(419, 150, 577, 15);
		getContentPane().add(lblFinancialAccountingYear);
*/
		
		lblLastNo = new JLabel("Last No:");
		lblLastNo.setForeground(Color.RED);
		lblLastNo.setBounds(856, 130, 65, 20);
		getContentPane().add(lblLastNo);
		
		lastNo = new JLabel("");
		lastNo.setForeground(Color.RED);
		lastNo.setBounds(919, 130, 51, 20);
		getContentPane().add(lastNo);
		lastNo.setText(String.valueOf(getLastNo()));

		
		lblvouno = new JLabel("Slip Number:");
		lblvouno.setBounds(282, 131, 126, 20);
		getContentPane().add(lblvouno);

		vou_no = new JTextField();
		vou_no.setBounds(408, 131,132, 23);
		vou_no.setFont(fontHindi);
		getContentPane().add(vou_no);
		



		lblvoudate = new JLabel("Date:");
		lblvoudate.setBounds(282, 162, 126, 20);
		getContentPane().add(lblvoudate);



		vou_date = new JFormattedTextField(sdf);
		vou_date.setBounds(408, 162, 132, 23);
		checkDate(vou_date);
		vou_date.setText(sdf.format(new Date()));
		getContentPane().add(vou_date);


/*		branch = new JLabel(loginDt.getBrnnm());
		branch.setForeground(Color.BLACK);
		branch.setFont(new Font("Tahoma", Font.BOLD, 22));
		branch.setBounds(279, 66, 560, 24);
		getContentPane().add(branch);

*/

		lblDispatchEntry = new JLabel("Inward Updation");
		lblDispatchEntry.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDispatchEntry.setBounds(331, 80, 382, 22);
//		lblDispatchEntry.setBounds(331, 94, 382, 22);
		getContentPane().add(lblDispatchEntry);


		
		

		// ////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////
	

		

		
		

		 



		// ///////////////////////////////////////////////
		

/*		lblVoucherDate = new JLabel("Slip Date:");
		lblVoucherDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVoucherDate.setBounds(16, 202, 83, 20);
		getContentPane().add(lblVoucherDate);
		
		vno = new JTextField();
		vno.setFont(new Font("Tahoma", Font.BOLD, 11));
		vno.setBounds(116, 172, 150, 22);
		getContentPane().add(vno);
		
		vdate = new JFormattedTextField(sdf);
		vdate.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkDate(vdate);
		vdate.setBounds(116, 202, 150, 22);
		getContentPane().add(vdate);
		
		lblVoucherNo = new JLabel("Slip No:");
		lblVoucherNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVoucherNo.setBounds(16, 172, 83, 20);
		getContentPane().add(lblVoucherNo);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBackground(new Color(51, 102, 204));
		panel_3.setBounds(10, 134, 296, 28);
		getContentPane().add(panel_3);
		
		label_4 = new JLabel("Search");
		label_4.setForeground(Color.WHITE);
		panel_3.add(label_4);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 133, 297, 575);
		getContentPane().add(panel);
*/
		
		lblvaccode = new JLabel("Vendor Code:");
		lblvaccode.setBounds(282, 194, 126, 20);
		getContentPane().add(lblvaccode);

		vac_code = new JTextField();
		vac_code.setBounds(408, 194, 86, 23);
		getContentPane().add(vac_code);
		

		
		lblNarration = new JLabel("Vendor In Hindi:");
		lblNarration.setBounds(282, 226, 126, 20);
		getContentPane().add(lblNarration);
		
		vnart_1 = new JTextField();
		vnart_1.setBounds(408, 224, 382, 23);
		vnart_1.setFont(fontHindi);
		getContentPane().add(vnart_1);
		
		lblPaidTo = new JLabel("Category.:");
		lblPaidTo.setBounds(282, 256, 126, 20);
		getContentPane().add(lblPaidTo);
		
		
		
		name = new JTextField();
		name.setName("2");
		name.setBounds(504, 194, 286, 23);
		
		getContentPane().add(name);


		
		name1_hindi = new JTextField();
		name1_hindi.setBounds(604, 254, 185, 23);
		name1_hindi.setEditable(false);
		name1_hindi.setFont(fontHindi);
		name1_hindi.setCaretPosition(0);
		getContentPane().add(name1_hindi);

		
		name1 = new JTextField();
		name1.setName("3");
		name1.setBounds(408, 254, 185, 23);
		name1.setFont(fontHindi);
		name1.setCaretPosition(0);
		getContentPane().add(name1);
		

		
		lblChqNo = new JLabel("Group:");
		lblChqNo.setBounds(282, 286, 126, 20);
		getContentPane().add(lblChqNo);
		


		group_hindi = new JTextField();
		group_hindi.setBounds(604, 284, 185, 23);
		group_hindi.setFont(fontHindi);
		group_hindi.setEditable(false);
		getContentPane().add(group_hindi);

		
		
		group = new JTextField();
		group.setBounds(408, 284, 185, 23);
		group.setFont(fontHindi);
		getContentPane().add(group);
		

		lblChqDate = new JLabel("Product Name:");
		lblChqDate.setBounds(282, 317, 126, 20);
		getContentPane().add(lblChqDate);

		
		
		pname_hindi = new JTextField();
		pname_hindi.setBounds(604, 317, 185, 23);
		pname_hindi.setFont(fontHindi);
		pname_hindi.setEditable(false);
		getContentPane().add(pname_hindi);

		
		pname = new JTextField();
		pname.setBounds(408, 317, 185, 23);
		pname.setFont(fontHindi);
		getContentPane().add(pname);
		

		lblGstTax = new JLabel("Quantity:");
		lblGstTax.setBounds(282, 350, 126, 20);
		getContentPane().add(lblGstTax);

		quantity = new JTextField();
		quantity.setHorizontalAlignment(SwingConstants.RIGHT);
		quantity.setBounds(408, 350, 185, 23);
		getContentPane().add(quantity);

		
		lblCgst = new JLabel("Weight:");
		lblCgst.setBounds(282, 379, 126, 20);
		getContentPane().add(lblCgst);

		weight = new JTextField();
		weight.setHorizontalAlignment(SwingConstants.RIGHT);
		weight.setBounds(408, 379, 185, 23);
		getContentPane().add(weight);
	
	
		
		
		vou_no.setName("0");
		vou_date.setName("1");
//		vac_code.setName("2");
		vnart_1.setName("3");
		name1.setName("4");
		group.setName("5");
//		chq_date.setName("6");
//		rate.setName("7");
		quantity.setName("8");
		weight.setName("9");
/*		rent_amt.setName("10");
		roundoff.setName("11");
		net_amt.setName("12");
		remark.setName("13");
*/
		
		

//		CrDrTable.addMouseListener(MouseRobot.RobMouseListner());

		vou_no.addFocusListener(myFocusListener);
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
					int vouno = setIntNumber(vou_no.getText().trim().toString());
					vou_no.setText("");
//					 rcp = (InvViewDto) idao.getInvDetail(vouno,"",loginDt.getFin_year(),60);
					 
					 rcp =  idao.getInvDetailNew(vouno,"",loginDt.getFin_year(),0,loginDt.getDiv_code());
					if(rcp==null)
					{
						clearAll();
						JOptionPane.showMessageDialog(InwardUpdation.this,"No Record for Slip No. "+vouno,"Record not found",JOptionPane.INFORMATION_MESSAGE);						
						
					} 
					else
					{
						fillData(rcp);
						CrTableModel.getDataVector().clear();
						CrTableModel.addRow(new Object[] {"","","","","","","","","","","","",""});
						cddrPane.requestFocus();
						CrDrTable.requestFocus();
						fillItemData(rcp);
						CrDrTable.changeSelection(0, 6, false, false);
						CrDrTable.editCellAt(0, 6);
					 
					}
					evt.consume();
				}
				
			}
		});
		
		

		final KeyListener crTableListener = new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				int column = CrDrTable.getSelectedColumn();
				int row = CrDrTable.getSelectedRow();
				int totRow=CrDrTable.getRowCount();
				
				int qty=0;
				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (column == 6)
					{
						   
//							CrDrTable.setValueAt("", row, 8);
							CrDrTable.changeSelection(row, 8, false, false);
							CrDrTable.editCellAt(row, 8);
							String marka = CrDrTable.getValueAt(row, 6).toString();
							if(marka.equalsIgnoreCase("") || marka.length()> 20 )
							{
								CrDrTable.changeSelection(row, 6, false, false);
								CrDrTable.editCellAt(row, 6);
								
							}
							else
							{
								CrDrTable.changeSelection(row, 8, false, false);
								CrDrTable.editCellAt(row, 8);
							}

					} 
/*					if (column == 7)
					{
							CrDrTable.changeSelection(row, 8, false, false);
							CrDrTable.editCellAt(row, 8);
							try {
								qty = setIntNumber(CrDrTable.getValueAt(row, 7).toString().trim());
							} catch (Exception e) {
								qty=0;
								
							}

							if(qty==0)
							{
								CrDrTable.changeSelection(row, 7, false, false);
								CrDrTable.editCellAt(row, 7);
								
							}
							else
							{

								sqty = setIntNumber(CrDrTable.getValueAt(row, 4).toString());
								rqty = setIntNumber(CrDrTable.getValueAt(row, 7).toString());
								if(rqty>sqty)
								{
									alertMessage(InwardUpdation.this, "Quantity should not be greater than "+sqty);
									CrDrTable.setValueAt("", row, 7);
									CrDrTable.changeSelection(row, 7, false, false);
									CrDrTable.editCellAt(row, 7);

								}
								else
								{
									CrDrTable.changeSelection(row, 8, false, false);
									CrDrTable.editCellAt(row, 8);
								}
							}
					} 
*/					if (column == 8)
					{
						CrDrTable.changeSelection(row, 9, false, false);
						CrDrTable.editCellAt(row, 9);
						String manzil = CrDrTable.getValueAt(row, 8).toString();
						if(manzil.equalsIgnoreCase("") || manzil.length()> 30)
						{
							CrDrTable.changeSelection(row, 8, false, false);
							CrDrTable.editCellAt(row, 8);
							
						}
						else
						{
							CrDrTable.changeSelection(row, 9, false, false);
							CrDrTable.editCellAt(row, 9);
						}
					} 
					if (column == 9) {

						CrDrTable.changeSelection(row, 9, false, false);
						CrDrTable.editCellAt(row, 9);
						String gala = CrDrTable.getValueAt(row, 9).toString();
						if(gala.equalsIgnoreCase("") || gala.length()> 30)
						{
							CrDrTable.changeSelection(row, 9, false, false);
							CrDrTable.editCellAt(row, 9);
							
						}
						else
						{
							if(row+1==totRow)
							{
								btnSave.setBackground(Color.BLUE);
								btnSave.requestFocus();
							}
							else
							{
								
								System.out.println("yeha per aaya kya");
	//							CrTableModel.addRow(new Object[] {"","","",""});
								CrDrTable.changeSelection(row+1, 6, false, false);
								CrDrTable.editCellAt(row+1, 6);

							}
						}
					}
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					btnSave.setBackground(Color.BLUE);
					btnSave.requestFocus();
					cdDrCal();

				}

			}// /// keypressed

		};

		CrDrTable.addKeyListener(crTableListener);
///**

/*		CrDrTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			
				int column = CrDrTable.getSelectedColumn();
				int row = CrDrTable.getSelectedRow();
				int totRow=CrDrTable.getRowCount();
				rrow = row;
				double net=0.00;
				double amt=0.00;
				int qty=0;
				double weight=0.00;
				
				
				if (column == 6)
				{
					   
//						CrDrTable.setValueAt("", row, 8);
						CrDrTable.changeSelection(row, 8, false, false);
						CrDrTable.editCellAt(row, 8);
						String marka = CrDrTable.getValueAt(row, 6).toString();
						if(marka.equalsIgnoreCase("") || marka.length()> 20 )
						{
							CrDrTable.changeSelection(row, 6, false, false);
							CrDrTable.editCellAt(row, 6);
							
						}
						else
						{
							CrDrTable.changeSelection(row, 8, false, false);
							CrDrTable.editCellAt(row, 8);
						}

				} 
				if (column == 8)
				{
					CrDrTable.changeSelection(row, 9, false, false);
					CrDrTable.editCellAt(row, 9);
					String manzil = CrDrTable.getValueAt(row, 8).toString();
					if(manzil.equalsIgnoreCase("") || manzil.length()> 30)
					{
						CrDrTable.changeSelection(row, 8, false, false);
						CrDrTable.editCellAt(row, 8);

					}
					else
					{
						CrDrTable.changeSelection(row, 9, false, false);
						CrDrTable.editCellAt(row, 9);
					}
				} 
				
				if (column == 9) {

					CrDrTable.changeSelection(row, 9, false, false);
					CrDrTable.editCellAt(row, 9);
					String gala = CrDrTable.getValueAt(row, 9).toString();
					if(gala.equalsIgnoreCase("") || gala.length()> 30)
					{
						CrDrTable.changeSelection(row, 9, false, false);
						CrDrTable.editCellAt(row, 9);

					}
					else
					{
						if(row+1==totRow)
						{
							btnSave.setBackground(Color.BLUE);
							btnSave.requestFocus();
						}
						else
						{

							System.out.println("yeha per aaya kya");
							CrDrTable.changeSelection(row+1, 6, false, false);
							CrDrTable.editCellAt(row+1, 6);

						}
					}
				}

			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

*/		
		
		
		invNoTable.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				int row = invNoTable.getSelectedRow();
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					rcp = (InvViewDto) invNoTable.getValueAt(row, 2);
					fillData(rcp);
					 
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
				rcp = (InvViewDto) invNoTable.getValueAt(row, 2);
				fillData(rcp); 
			 
			}
		});
				
		
		
		btnSave = new JButton("Save");
		btnSave.setActionCommand("Save");
		btnSave.setEnabled(false);
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.setBounds(834, 605, 86, 30);
		getContentPane().add(btnSave);

		btnExit = new JButton("Exit");
		btnExit.setActionCommand("Exit");
		btnExit.setMnemonic(KeyEvent.VK_E);
		btnExit.setBounds(926, 605, 86, 30);
		btnExit.setActionCommand("Exit");
		getContentPane().add(btnExit);
		
		
		btnSave.addActionListener(this);
		btnExit.addActionListener(this);


/*		JPanel panel_4 = new JPanel();
//		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_4.setBackground(Color.LIGHT_GRAY);
		panel_4.setBounds(40, 134, 975, 48);
		getContentPane().add(panel_4);
*/
	JPanel panel_1 = new JPanel();
//		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(40, 121, 975, 518);
//		panel_1.setBounds(40, 186, 975, 518);
		getContentPane().add(panel_1);
		

		btnSave.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					
					int ino = saveData();
					if(ino!=0)
					{
						JOptionPane.showMessageDialog(InwardUpdation.this,"Inward No "+ino,"Inward No",JOptionPane.INFORMATION_MESSAGE);
					}
					vou_no.requestFocus();
					vou_no.setSelectionStart(0);
					
				}
			}
		});

	}


	public void clearAll()
	{

		vou_no.setText("");
//		vou_date.setValue(null);
//		vdate.setValue(null);
//		checkDate(vdate);
//		checkDate(vou_date);
		group.setText("");
		vac_code.setText("");
		name.setText("");
		pname.setText("");
		pname_hindi.setText("");
		name1.setText("");
		name1_hindi.setText("");
		group.setText("");
		group_hindi.setText("");
		vnart_1.setText("");
		weight.setText("");
		quantity.setText("");

		bno="";
		invNoTableModel.getDataVector().removeAllElements();
		
		CrTableModel.getDataVector().clear();
		CrTableModel.addRow(new Object[] {"","","","","","","","","","","",""});


		 btnSave.setBackground(null);


	}


	 

	public InvViewDto vouAdd() 
	{
		 
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		 
		InvViewDto rcp1 = null;
		try {
			 

				 
				rcp1 = new InvViewDto();
				rcp1.setDepo_code(loginDt.getDepo_code());
				rcp1.setDiv_code(loginDt.getDiv_code());
				rcp1.setDoc_type(60);
				rcp1.setSinv_no(setIntNumber(vou_no.getText()));
				rcp1.setFin_year(loginDt.getFin_year());
				rcp1.setModified_by(loginDt.getLogin_id());
				rcp1.setModified_date(new Date());
				rcp1.setSqty(setDoubleNumber(quantity.getText()));
				

				
				cdDrCal();
				rcp1.setMarkalist(markaList);

			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return rcp1;

	}

	
	

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			int ino = saveData();
			int vouNo=setIntNumber(vou_no.getText());

			if(ino!=0)
			{
				JOptionPane.showMessageDialog(InwardUpdation.this,"Slip No "+vouNo,"Slip No",JOptionPane.INFORMATION_MESSAGE);
			}
			vou_no.requestFocus();
			vou_no.setSelectionStart(0);

		}
		
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			option=0;
	//		invNoTable.setEnabled(true);
			vou_no.setEditable(true); 
			vou_no.setEnabled(true);
			clearAll();
			dispose();
		}

		
		
	}


	public int saveData() 
	{
		int vouNo=0;
		int vno=setIntNumber(vou_no.getText());
		if(vno>0)
			vouNo= idao.updateInvsnd(vouAdd());
		else
			alertMessage(InwardUpdation.this, "No is Empty !!!! ");
//		invNoTable.setEnabled(true);
		vou_no.setEnabled(true);
//		vdate.setEnabled(true);
		option=0;
		clearAll();
		return vouNo;
	}
	
	
	 

	public void fillData(InvViewDto rcp)
	{
		// voucher header
		vou_no.setText(String.valueOf(rcp.getInv_no()));
		vou_date.setText(sdf.format(rcp.getInv_date()));
		vac_code.setText(rcp.getMac_code());
		name.setText((rcp.getMac_name()+","+rcp.getMcity()));
		name.setCaretPosition(0);
		vnart_1.setText(rcp.getMac_name_hindi()+","+rcp.getMcity_hindi());
		name1.setText(rcp.getCategory());
		name1_hindi.setText(rcp.getCategory_hindi());
		group.setText(rcp.getGroup_name());
		group_hindi.setText(rcp.getGroup_name_hindi());
		pname.setText(rcp.getAproval_no());
		pname_hindi.setText(rcp.getPname_hindi());
		quantity.setText(formatter.format(rcp.getSqty()));
		weight.setText(formatter.format(rcp.getWeight()));
		pcode=rcp.getSprd_cd();
		groupcd=rcp.getSpd_gp();
		sqty=rcp.getSqty();
		cddrPane.requestFocus();
		CrDrTable.requestFocus();
		btnSave.setEnabled(true);
		
	}	

	public void cdDrCal() 
	{
		double cval = 0.00;
		double dval = 0.00;
		Vector col = null;
		InvViewDto trd = null;
		boolean first=true;

		Vector crDrData = CrTableModel.getDataVector();
		int size=crDrData.size();
		
		
		try {
			for (int i = 0; i < size; i++) {
				col = (Vector) crDrData.get(i);
				
				
//				if (setIntNumber(col.get(7).toString().trim())!=0 ) {
					 
					if(first)
					{
						markaList = new ArrayList();
						first=false;
					}
					trd = new InvViewDto();
					
					trd.setDepo_code(loginDt.getDepo_code());
					trd.setDiv_code(loginDt.getDiv_code());
					trd.setDoc_type(60);
					trd.setSinv_no(setIntNumber(vou_no.getText()));
					trd.setFin_year(loginDt.getFin_year());
					trd.setModified_by(loginDt.getLogin_id());
					trd.setModified_date(new Date());
					trd.setSqty(setDoubleNumber(quantity.getText()));
					
					trd.setMark_no(col.get(6).toString());
					trd.setBlock_qty(setIntNumber(col.get(5).toString()));
					trd.setFloor_no(col.get(8).toString());
					trd.setBlock_no(col.get(9).toString());
					trd.setSerialno(setIntNumber(col.get(10).toString()));
					markaList.add(trd);

				}

//			}
			 
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void itemTableUI() 
	{
		
		//		vouTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		CrDrTable.setFont(new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14));
		CrDrTable.setColumnSelectionAllowed(false);
		CrDrTable.setCellSelectionEnabled(false);
		CrDrTable.getTableHeader().setReorderingAllowed(false);
		CrDrTable.getTableHeader().setResizingAllowed(false);
		CrDrTable.setRowHeight(20);
		CrDrTable.getTableHeader().setPreferredSize(new Dimension(25, 25));
		CrDrTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

		CrDrTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		CrDrTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		CrDrTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		CrDrTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

		CrDrTable.getColumnModel().getColumn(0).setPreferredWidth(50); // CATEGORY
		CrDrTable.getColumnModel().getColumn(1).setPreferredWidth(40); // GROUP
		CrDrTable.getColumnModel().getColumn(2).setPreferredWidth(70); // ITEM
		CrDrTable.getColumnModel().getColumn(3).setPreferredWidth(40); // PACK
		CrDrTable.getColumnModel().getColumn(4).setPreferredWidth(40); // RATE
		CrDrTable.getColumnModel().getColumn(5).setPreferredWidth(50); // QTY
		CrDrTable.getColumnModel().getColumn(6).setPreferredWidth(50); // marka

		CrDrTable.getColumnModel().getColumn(7).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(7).setMinWidth(0); // block qty
		CrDrTable.getColumnModel().getColumn(7).setMaxWidth(0);

		CrDrTable.getColumnModel().getColumn(8).setPreferredWidth(50); // floor
		CrDrTable.getColumnModel().getColumn(9).setPreferredWidth(50); // gala


		CrDrTable.getColumnModel().getColumn(10).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(10).setMinWidth(0); // sprd_cd
		CrDrTable.getColumnModel().getColumn(10).setMaxWidth(0);


		CrDrTable.getColumnModel().getColumn(11).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(11).setMinWidth(0); // tag
		CrDrTable.getColumnModel().getColumn(11).setMaxWidth(0);


		CrDrTable.getColumnModel().getColumn(12).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(12).setMinWidth(0); // tag
		CrDrTable.getColumnModel().getColumn(12).setMaxWidth(0);

		
		CrDrTable.setDefaultEditor(Double.class, new OverWriteTableCellEditor());
		CrDrTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());
		CrDrTable.getColumnModel().getColumn(7).setCellEditor(cellEditor);	
		CrDrTable.getColumnModel().getColumn(8).setCellEditor(cellEditor);
		
		table_pcode = new JFormattedTextField();
		table_pcode.setSelectionStart(0);
		table_pcode.setFont(fontHindi);
		CrDrTable.getColumnModel().getColumn(9).setCellEditor(new NumberTableCellEditor(table_pcode));


	}

	
	 



	public void fillInvTable(Vector data)
	{
		//invNoTableModel.getDataVector().removeAllElements();
		Vector c = null;
		int s = data.size();
		for(int i =0;i<data.size();i++)
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
	
	public void fillItemData(InvViewDto rcp)
	{
		// Vou Detail Fill 
		
		
		CrTableModel.getDataVector().clear();
		Vector c = null;
		ArrayList data =rcp.getMarkalist();
		int size=data.size();
		for(int i =0;i<size;i++)
		{
			c = (Vector) data.get(i);
			System.out.println("markno "+c.get(0).toString());
			CrTableModel.addRow(c);
		}
		
		
	}

	public int getLastNo()
	{
		int vno=0;
		vno=idao.getInvoiceNo(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(), 60);
		
		return vno;
	}	

	public void setVisible(boolean b)
	{
		btnSave.setEnabled(false);
		super.setVisible(b);
	}
	
}


