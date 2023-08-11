package com.coldstorage.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.InvPrintDAO;
import com.coldstorage.dao.TransportDAO;
import com.coldstorage.dto.BookDto;
import com.coldstorage.dto.CategoryDto;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.InvViewDto;
import com.coldstorage.dto.PackDto;
import com.coldstorage.dto.ProductDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.print.GenerateInvoiceGST;
import com.coldstorage.util.MouseRobot;
import com.coldstorage.util.OverWriteTableCellEditor;
import com.coldstorage.util.TextField;

public class InwardEntry extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	
	Font font,fontHindi,fontHindi1; 

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
	private JPanel panel_2;
	private SimpleDateFormat sdf;
	private NumberFormat df;
	DefaultTableModel invNoTableModel,vouTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable invNoTable;
	private JScrollPane vouPane;
	String bno;
	String sdate,edate;
	int partyCd,billNo,option,rrow,vouno,categorycd,groupcd,pcode;
	InvViewDto rcp;
	BookDto bk;
	TransportDto prtyDto;
	CategoryDto catDto;
	GroupDto grpDto;
	PackDto packDto;
	ProductDto pdto;
	InvPrintDAO idao;
	TransportDAO pdao ;
	double tot,vamt;
	private JTextField vno;
	private Font fontPlan;
	private JPanel panel_3;
	private JLabel label_4;
	private JList partyList,categoryList,groupList,productList,packList;
	private JTextField vnart_1;
	private JTextField group;
	private JTextField net_amt,rate,weight,rent_amt,roundoff,quantity;
	private JLabel lblChqAmount,lblTaxable,lblCgst,lblSgst,lblIgst,lblGstTax;
	private JTextField name;
	private JButton btnSave,btnExit,btnPrint;
	NumberFormat formatter;
	int div=0;
	private JScrollPane Partypane,Categorypane,Grouppane,Productpane,Packpane; 
	private JLabel label_1;
	private JLabel label_3;

	private JLabel lblLastNo;

	private JLabel lastNo,transfer;
	private JTextField name1;

	private Vector partyNames,categoryNames,groupNames,productNames,packNames;

	private Map partyMap;
	private JLabel hintTextLabel,hintTextLabelcat,hintTextLabelgrp,hintTextLabelprd;
	private boolean addBool;

	private JTextField pname;

	private JLabel lblRemark,lblVehicleNo;

	private JTextField remark,vehicle_no;

	private JTextField pname_hindi;

	private JTextField group_hindi;

	private JTextField name1_hindi;

	private JTable vouTable;

	

	private JScrollPane vouTablePane;

	boolean addRecord=true;
	
	private boolean addNewRow;
	private int sNo;

	private JLabel hintProductTextLabel;

	private JTextField product_name;

	private String edit;


	public InwardEntry()
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

		fontPlan =new Font("Tahoma", Font.PLAIN, 11);
		fontHindi = new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14);
		fontHindi1 = new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,20);
		//setUndecorated(true);
		setResizable(false);
		
		// setOpacity(0.65f); 
		
//		setSize(1024, 768);		
		
		setSize(1024, 670);		

		//setBackground(null);
		
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, (dim.height)/2-this.getSize().height/2);
	        
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		bno="";
		pdao = new TransportDAO();
		idao = new InvPrintDAO();
		
		partyNames = (Vector) pdao.getTransportList(loginDt.getDepo_code(),loginDt.getDiv_code() );
		categoryNames = (Vector) pdao.getCategoryList();
		groupNames = (Vector) pdao.getGroupList(loginDt.getDiv_code());
		productNames = (Vector) pdao.getProductList(1);
		packNames = (Vector) pdao.getPackList();
		
		
		partyMap = (HashMap) pdao.getPartyNameMap(loginDt.getDepo_code(),loginDt.getDiv_code());

		option=1;
		addBool=true;

	
		arisleb.setVisible(false);
/*		//////////////invoce no table model/////////////////////
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
*/		
	
		
		///////////////////////////////////////////////////////
		
		
	

		partyList = new JList();
		//partyList.setFont(font);
		partyList.setSelectedIndex(0);
		partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Partypane = new JScrollPane(partyList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Partypane.setBounds(625, 217, 286, 259);
		getContentPane().add(Partypane);
		Partypane.setVisible(false);
		Partypane.setViewportView(partyList);
		bindData(partyList, partyNames);

		partyList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = partyList.getSelectedIndex();
					
					
					prtyDto = (TransportDto) partyList.getSelectedValue();
					partyCd = prtyDto.getAccount_no();

					vac_code.setText(String.valueOf(partyCd));
					name.setText(prtyDto.getMac_name());
					vnart_1.setText(prtyDto.getMac_name_hindi());
					 
					// evt.consume();
					Partypane.setVisible(false);
					hintTextLabel.setVisible(false);
					vouTableModel.getDataVector().clear();
					vouTableModel.addRow(new Object[] {"","","","","","","","",""});
					vouTablePane.requestFocus();
					vouTable.requestFocus();
					vouTable.changeSelection(0, 0, false, false);
					vouTable.editCellAt(0, 0);


				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

					 
					// evt.consume();
					Partypane.setVisible(false);
					// inv_date.requestFocus();
					vac_code.requestFocus();
				}
				
			}
		});
		

		categoryList = new JList();
		categoryList.setSelectedIndex(0);
		categoryList.setFont(fontHindi);
		categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Categorypane = new JScrollPane(categoryList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Categorypane.setBounds(40, 280, 110, 117);
		getContentPane().add(Categorypane);
		Categorypane.setVisible(false);
		Categorypane.setViewportView(categoryList);
		bindData(categoryList, categoryNames);

		categoryList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = categoryList.getSelectedIndex();
					
					
					catDto = (CategoryDto) categoryList.getSelectedValue();
					categorycd = catDto.getCategory_code();

					vouTable.setValueAt(catDto.getCategory_name_hindi(), rrow, 0);
					vouTable.setValueAt(catDto.getCategory_name(), rrow, 11);
					
					// evt.consume();
					Categorypane.setVisible(false);
					vouTable.requestFocus();
					vouTable.changeSelection(rrow, 1, false, false);
					vouTable.editCellAt(rrow,1);


					 
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

					 
					// evt.consume();
					Categorypane.setVisible(false);
					vouTable.requestFocus();
					vouTable.changeSelection(rrow, 0, false, false);
					vouTable.editCellAt(rrow,0);
				}
				
			}
		});

		groupList = new JList();
		groupList.setSelectedIndex(0);
		groupList.setFont(fontHindi);
		groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Grouppane = new JScrollPane(groupList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Grouppane.setBounds(140, 280, 110, 117);
		getContentPane().add(Grouppane);
		Grouppane.setVisible(false);
		Grouppane.setViewportView(groupList);
		bindData(groupList, groupNames);

		groupList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = groupList.getSelectedIndex();
					
					
					grpDto = (GroupDto) groupList.getSelectedValue();
					groupcd = grpDto.getGp_code();

					vouTable.setValueAt(grpDto.getGp_name_hindi(), rrow, 1);
					vouTable.setValueAt(grpDto.getGp_code(), rrow, 9);
					
					productNames = (Vector) pdao.getProductList(groupcd);
					productList.removeAll();
					productList.setListData(productNames);
					productList.setSelectedIndex(0);
					 
					// evt.consume();
					Grouppane.setVisible(false);
					vouTable.requestFocus();
					vouTable.changeSelection(rrow, 2, false, false);
					vouTable.editCellAt(rrow,2);
					 
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

					 
					// evt.consume();
					Grouppane.setVisible(false);
					vouTable.requestFocus();
					vouTable.changeSelection(rrow, 1, false, false);
					vouTable.editCellAt(rrow,1);
				}
				
			}
		});


		packList = new JList();
		packList.setSelectedIndex(0);
		packList.setFont(fontHindi);
		packList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Packpane = new JScrollPane(packList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Packpane.setBounds(350, 280, 110, 117);
		getContentPane().add(Packpane);
		Packpane.setVisible(false);
		Packpane.setViewportView(packList);
		bindData(packList, packNames);

		packList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = packList.getSelectedIndex();
					
					
					packDto = (PackDto) packList.getSelectedValue();
//					groupcd = grpDto.getGp_code();

					vouTable.setValueAt(packDto.getPack_name_hindi(), rrow, 3);
					// evt.consume();
					Packpane.setVisible(false);
					vouTable.requestFocus();
					vouTable.changeSelection(rrow, 4, false, false);
					vouTable.editCellAt(rrow,4);
					 
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

					 
					// evt.consume();
					Packpane.setVisible(false);
					vouTable.requestFocus();
					vouTable.changeSelection(rrow, 3, false, false);
					vouTable.editCellAt(rrow,3);
				}
				
			}
		});

		
		
		hintProductTextLabel = new JLabel("Search by Itemt Name");
		hintProductTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		hintProductTextLabel.setVisible(false);
		hintProductTextLabel.setForeground(Color.GRAY);
		hintProductTextLabel.setBounds(239, 280, 380, 23);
		getContentPane().add(hintProductTextLabel);

		product_name = new JTextField();
		product_name.setVisible(false);
		product_name.setBounds(235, 280, 130, 23);
		getContentPane().add(product_name);
		product_name.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
					int size = productList.getFirstVisibleIndex();
					if (size >= 0) {
						productList.requestFocus();
						productList.setSelectedIndex(0);
						productList.ensureIndexIsVisible(0);
						hintProductTextLabel.setVisible(false);
					}
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Productpane.setVisible(false);
					product_name.setVisible(false);
					product_name.setText("");
					hintProductTextLabel.setVisible(false);
					searchFilter(product_name.getText(), productList, productNames);
					vouTable.editCellAt(rrow, 1);
					vouTable.requestFocus();
					evt.consume();
				}

			}

			public void keyReleased(KeyEvent evt) {
				searchFilter(product_name.getText(), productList, productNames);
				if (product_name.getText().length() > 0)
					hintProductTextLabel.setVisible(false);
				else
					hintProductTextLabel.setVisible(true);
				// evt.consume();
			}

		});

		
		productList = new JList();
		productList.setSelectedIndex(0);
		productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Productpane = new JScrollPane(productList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Productpane.setBounds(235, 301, 130, 145);
		getContentPane().add(Productpane);
		Productpane.setVisible(false);
		Productpane.setViewportView(productList);
		bindData(productList, productNames);

		productList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = productList.getSelectedIndex();
					
					
					pdto = (ProductDto) productList.getSelectedValue();
					pcode = pdto.getPcode();
					vouTable.setValueAt(pdto.getPack_name(), rrow, 2);
					vouTable.setValueAt(pdto.getNet_rt1(), rrow, 4);
					vouTable.setValueAt(pdto.getPcode(), rrow, 10);

/*					pname.setText(pdto.getPname());
					pname_hindi.setText(pdto.getPack_name());
					rate.setText(formatter.format(pdto.getNet_rt1()));
*/					// evt.consume();
					Productpane.setVisible(false);
					product_name.setVisible(false);
					product_name.setText("");

					vouTable.requestFocus();
					vouTable.changeSelection(rrow, 3, false, false);
					vouTable.editCellAt(rrow,3);

					 
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

					 
					// evt.consume();
					Productpane.setVisible(false);
					product_name.setVisible(false);
					product_name.setText("");
					vouTable.requestFocus();
					vouTable.changeSelection(rrow, 1, false, false);
					vouTable.editCellAt(rrow,1);
				}
				
			}
		});
		
		
/*		JScrollPane scrollPane = new JScrollPane(invNoTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(16, 235, 283, 400);
		getContentPane().add(scrollPane);
*/		
		
		
		///////////////////////////////////////////////////////

/*		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(400, 150, 577, 15);
		getContentPane().add(lblFinancialAccountingYear);
*/
		lblvouno = new JLabel("Slip Number:");
		lblvouno.setBounds(403, 131, 126, 20);
		getContentPane().add(lblvouno);

		vou_no = new JTextField();
		vou_no.setBounds(529, 131,132, 23);
		getContentPane().add(vou_no);

		lblvoudate = new JLabel("Date:");
		lblvoudate.setBounds(403, 162, 126, 20);
		getContentPane().add(lblvoudate);

		vou_date = new JFormattedTextField(sdf);
		vou_date.setBounds(529, 162, 132, 23);
		checkDate(vou_date);
		vou_date.setText(sdf.format(new Date()));
		getContentPane().add(vou_date);
		
		vou_date.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				String date=vou_date.getText();
				checkDateValidity(date, vou_date, vac_code, InwardEntry.this);
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		
		
		lblvaccode = new JLabel("Vendor Code:");
		lblvaccode.setBounds(403, 195, 126, 20);
		getContentPane().add(lblvaccode);



/*		branch = new JLabel(loginDt.getBrnnm());
		branch.setForeground(Color.BLACK);
		branch.setFont(new Font("Tahoma", Font.BOLD, 22));
		branch.setBounds(340, 66, 560, 24);
		getContentPane().add(branch);
*/


		lblDispatchEntry = new JLabel("आवक रसीद एंट्री ");
		lblDispatchEntry.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchEntry.setForeground(Color.BLACK);
//		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDispatchEntry.setFont(fontHindi1);
		lblDispatchEntry.setBounds(360, 80, 382, 22);
//		lblDispatchEntry.setBounds(360, 145, 382, 22);
		getContentPane().add(lblDispatchEntry);



		// ////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////
		KeyListener keyListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					getLastNo();
					JTextField textField = (JTextField) keyEvent.getSource();
					int id = Integer.parseInt(textField.getName());

					switch (id) 
					{
					case 0:
						vou_date.requestFocus();
						vou_date.setSelectionStart(0);

						break;

					case 1:
/*						String date=vou_date.getText();
						if(isValidDate(date))
						{
							//lblinvalid.setVisible(false);
							// Check the Date Range for the Financial year
							if (isValidRange(date,loginDt.getFr_date(),loginDt.getTo_date()))
							{
								vac_code.requestFocus();
							}
							else
							{
								JOptionPane.showMessageDialog(InwardEntry.this,"Date range should be in between: "
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
*/						
						vac_code.requestFocus();
						break;

						
						
					case 2:
						vnart_1.requestFocus();
						break;
					case 3:
						break;

					case 4:
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						break;
					case 9:
						weight.setText(formatter.format(setDoubleNumber(weight.getText())));
						rent_amt.setText(formatter.format(setDoubleNumber(weight.getText())*setDoubleNumber(rate.getText())));
						roundoff.requestFocus();  // round off
						roundoff.setSelectionStart(0);
						break;
					case 10:
						roundoff.requestFocus();  // round off
						roundoff.setSelectionStart(0);
						break;
					case 11:
						roundoff.setText(formatter.format(setDoubleNumber(roundoff.getText())));
						vamt = setDoubleNumber(rent_amt.getText())+setDoubleNumber(roundoff.getText());
						net_amt.setText(formatter.format(vamt));
						net_amt.requestFocus();
						net_amt.setSelectionEnd(net_amt.getText().length());
						net_amt.setSelectionStart(0);
						//vamount.setSelectionEnd(vamount.getText().length());
						
						break;

					case 12:
						if (setDoubleNumber(net_amt.getText())==0.00)
						 {
								net_amt.requestFocus();
								net_amt.setText("");
								//checkNumber(vamount);
								net_amt.setSelectionStart(0);
								net_amt.setSelectionEnd(net_amt.getText().length());
						 }
						 else
						 {
							 vamt = setDoubleNumber(net_amt.getText());
							 net_amt.setText(formatter.format(vamt));
							 vehicle_no.requestFocus(); 						
							 vehicle_no.setSelectionStart(0);
						 }
						break;
					case 13:
							 remark.requestFocus(); 						
							 remark.setSelectionStart(0);
							 btnSave.setEnabled(true);
						break;
					case 14:

						 btnSave.setBackground(Color.BLUE); 
						 btnSave.requestFocus(); 	
						 break;


					}

				}

/*				if (key == KeyEvent.VK_ESCAPE) 
				{
					clearAll();
					dispose();

				}
*/



			}
			 

		};

	
		// ////////////////////////////////////////////////////////////////////
		String[] crDrColName = { "Category","Group","Item","Pack", "Rate","Qty", "Weight", "Amount","RST No","","",""};
		String[][] cdDrData = { {}, };
		vouTableModel = new DefaultTableModel(cdDrData, crDrColName)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{

				boolean ans=true;
				if(column==2 || column==4 || column==7)
				{
					return false;
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
				case 5 : return Integer.class;
				default:
					return String.class;
				}
			}
			
		};
		
		vouTable = new JTable(vouTableModel);
		itemTableUI();
		getContentPane().add(vouTable);
		vouTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		// ////////////////////////////////////////////////////////////////////////
		vouTablePane = new JScrollPane(vouTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		vouTablePane.setBounds(40, 255, 975, 150);
		vouTablePane.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		getContentPane().add(vouTablePane);
		vouTablePane.setVisible(true);

		
		
		KeyListener VoucherTableListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				int column = vouTable.getSelectedColumn();
				int row = vouTable.getSelectedRow();
				int totRow=vouTable.getRowCount();
				rrow = row;
				double net=0.00;
				double amt=0.00;
				int qty=0;

				if(addRecord)
				{

					if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
					{

						if (column == 0) 
						{
								
								Categorypane.setVisible(true);
								Categorypane.requestFocus();
								categoryList.requestFocus();
								categoryList.setSelectedIndex(0);
								// //// setting sroller to top//////////////
								categoryList.ensureIndexIsVisible(0);
						}
						if (column == 1) 
						{
							Grouppane.setVisible(true);
							Grouppane.requestFocus();
							groupList.requestFocus();
							groupList.setSelectedIndex(0);
							// //// setting sroller to top//////////////
							groupList.ensureIndexIsVisible(0);
						}
						else if (column == 2) 
						{
							Productpane.setVisible(true);
							productList.setVisible(true);
							product_name.setVisible(true);
							Productpane.requestFocus();
							product_name.requestFocus();
							productList.setSelectedIndex(0);
							// //// setting sroller to top//////////////
							productList.ensureIndexIsVisible(0);
						}					
						else if (column == 3) 
						{

							Packpane.setVisible(true);
							Packpane.requestFocus();
							packList.requestFocus();
							packList.setSelectedIndex(0);
							// //// setting sroller to top//////////////
							packList.ensureIndexIsVisible(0);
						}					
						else if (column == 4) 
						{
							
							vouTable.changeSelection(row, 5, false, false);
							vouTable.editCellAt(row, 5);
						}
						else if (column == 5) 
						{
							vouTable.changeSelection(row, 6, false, false);
							vouTable.editCellAt(row, 6);
							try {
								qty = setIntNumber(vouTable.getValueAt(row, 5).toString().trim());
							} catch (Exception e) {
								qty=0;
								
							}

							if(qty==0)
							{
								vouTable.changeSelection(row, 5, false, false);
								vouTable.editCellAt(row, 5);
								
							}

						}
						else if (column == 6) 
						{

							vouTable.changeSelection(row, 8, false, false);
							vouTable.editCellAt(row, 8);

								net = setDoubleNumber(vouTable.getValueAt(row, 4)
										.toString().trim());


								try {
									qty = setIntNumber(vouTable.getValueAt(row, 6).toString().trim());
								} catch (Exception e) {
									qty=0;
									
								}

								if(qty==0)
								{
									vouTable.changeSelection(row, 6, false, false);
									vouTable.editCellAt(row, 6);

								}
								else
								{
									vouTable.setValueAt(formatter.format(net * qty), row,7);
									vouTable.changeSelection(row, 8, false, false);
									vouTable.editCellAt(row, 8);
								}
						}

						else if (column == 7) 
						{

							vouTable.changeSelection(row, 8, false, false);
							vouTable.editCellAt(row, 8);

	
						}

						else if (column == 8) 
						{
							vouTable.changeSelection(row, 9, false, false);
							vouTable.editCellAt(row, 9);
							try {
								qty = setIntNumber(vouTable.getValueAt(row, 8).toString().trim());
							} catch (Exception e) {
								qty=0;
							}
							if(qty==0)
							{
								vouTable.changeSelection(row, 8, false, false);
								vouTable.editCellAt(row, 8);
							}
							
							else
							{
								totPara();
								if (row == (totRow - 1))
								{
									row = -1;
								}
								vouTableModel.addRow(new Object[] {});
								vouTable.changeSelection(totRow, 0, false, false);
								vouTable.editCellAt(totRow, 0);
							}
						}

						evt.consume();
						
					} //enter key
					
				} //add record boolean
				else
				{
					evt.consume();
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					//vouPane.setVisible(false);
					roundoff.requestFocus();
					roundoff.setSelectionStart(0);

					evt.consume();
				}
				
			}// /// keypressed

		};

		vouTable.addKeyListener(VoucherTableListener);

		vouTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			
				int column = vouTable.getSelectedColumn();
				int row = vouTable.getSelectedRow();
				int totRow=vouTable.getRowCount();
				rrow = row;
				double net=0.00;
				double amt=0.00;
				int qty=0;
				if (column == 0) 
				{
						
						Categorypane.setVisible(true);
						Categorypane.requestFocus();
						categoryList.requestFocus();
						categoryList.setSelectedIndex(0);
						// //// setting sroller to top//////////////
						categoryList.ensureIndexIsVisible(0);
				}
				if (column == 1) 
				{
					Grouppane.setVisible(true);
					Grouppane.requestFocus();
					groupList.requestFocus();
					groupList.setSelectedIndex(0);
					// //// setting sroller to top//////////////
					groupList.ensureIndexIsVisible(0);
				}
				else if (column == 2) 
				{
					Productpane.setVisible(true);
					productList.setVisible(true);
					product_name.setVisible(true);
					Productpane.requestFocus();
					product_name.requestFocus();
					productList.setSelectedIndex(0);
					// //// setting sroller to top//////////////
					productList.ensureIndexIsVisible(0);
				}					
				else if (column == 3) 
				{

					Packpane.setVisible(true);
					Packpane.requestFocus();
					packList.requestFocus();
					packList.setSelectedIndex(0);
					// //// setting sroller to top//////////////
					packList.ensureIndexIsVisible(0);
				}					
				else if (column == 4) 
				{
					vouTable.changeSelection(row, 4, false, false);
					vouTable.editCellAt(row, 4);
				}
				else if (column == 5) 
				{
					vouTable.changeSelection(row, 5, false, false);
					vouTable.editCellAt(row, 5);
					try {
						qty = setIntNumber(vouTable.getValueAt(row, 5).toString().trim());
					} catch (Exception e) {
						qty=0;
						
					}

					if(qty==0)
					{
						vouTable.changeSelection(row, 5, false, false);
						vouTable.editCellAt(row, 5);
						
					}

				}
				else if (column == 6) 
				{

					vouTable.changeSelection(row, 6, false, false);
					vouTable.editCellAt(row, 6);

						net = setDoubleNumber(vouTable.getValueAt(row, 4)
								.toString().trim());


						try {
							qty = setIntNumber(vouTable.getValueAt(row, 6).toString().trim());
						} catch (Exception e) {
							qty=0;
							
						}

						if(qty==0)
						{
							vouTable.changeSelection(row, 6, false, false);
							vouTable.editCellAt(row, 6);

						}
						else
						{
							vouTable.setValueAt(formatter.format(net * qty), row,7);
							vouTable.changeSelection(row, 8, false, false);
							vouTable.editCellAt(row, 8);
						}
				}

				else if (column == 7) 
				{

					vouTable.changeSelection(row, 7, false, false);
					vouTable.editCellAt(row, 7);


				}

				else if (column == 8) 
				{
					vouTable.changeSelection(row, 8, false, false);
					vouTable.editCellAt(row, 8);
					try {
						qty = setIntNumber(vouTable.getValueAt(row, 8).toString().trim());
					} catch (Exception e) {
						qty=0;
					}
					if(qty==0)
					{
						vouTable.changeSelection(row, 8, false, false);
						vouTable.editCellAt(row, 8);
					}
					
					else
					{
						totPara();
/*						if (row == (totRow - 1))
						{
							row = -1;
						}
						vouTableModel.addRow(new Object[] {});
						vouTable.changeSelection(totRow, 0, false, false);
						vouTable.editCellAt(totRow, 0);
*/					}
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
		

		// ///////////////////////////////////////////////
		

		
		lblNarration = new JLabel("Vendor In Hindi:");
		lblNarration.setBounds(403, 224, 126, 20);
		getContentPane().add(lblNarration);
		
		vnart_1 = new JTextField();
		vnart_1.setBounds(529, 224, 382, 23);
		vnart_1.setFont(fontHindi);
		getContentPane().add(vnart_1);
		
		
		
		hintTextLabel = new JLabel("Search by Party Name");
		hintTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		hintTextLabel.setForeground(Color.GRAY);
		hintTextLabel.setBounds(627, 194, 231, 22);
		getContentPane().add(hintTextLabel);

		
		name = new JTextField();
		name.setName("2");
		name.setBounds(625, 194, 286, 23);
		getContentPane().add(name);
		name.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				// TODO Auto-generated method stub

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if(name.getText().length()==0)
						alertMessage(InwardEntry.this, "Please select Party!");
					else
						vnart_1.requestFocus();
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
					int size = partyList.getFirstVisibleIndex();
					if (size >= 0) {
						partyList.requestFocus();
						partyList.setSelectedIndex(0);
						partyList.ensureIndexIsVisible(0);
						hintTextLabel.setVisible(false);
					}
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Partypane.setVisible(false);
					// party_code.requestFocus();
					// party_name.setText("");
					name.requestFocus();
					hintTextLabel.setVisible(true);
					evt.consume();
				}
			}

			public void keyReleased(KeyEvent evt) {

					if (!Partypane.isVisible()
							&& (evt.getKeyCode() != KeyEvent.VK_ENTER && evt
									.getKeyCode() != KeyEvent.VK_ESCAPE)) {
						Partypane.setVisible(true);
					}
					searchFilter(name.getText(), partyList, partyNames);
					if (name.getText().length() > 0)
						hintTextLabel.setVisible(false);
					else
						hintTextLabel.setVisible(true);
				}
				// evt.consume();

		});

		lblCgst = new JLabel("Total Weight:");
		lblCgst.setBounds(403, 408, 126, 20);
		getContentPane().add(lblCgst);

		weight = new JTextField();
		weight.setHorizontalAlignment(SwingConstants.RIGHT);
		weight.setEditable(false);
		weight.setBounds(529, 408, 185, 23);
		getContentPane().add(weight);

	
		lblSgst = new JLabel("Total Rent:");
		lblSgst.setBounds(403, 439, 126, 20);
		getContentPane().add(lblSgst);

		rent_amt = new JTextField();
		rent_amt.setHorizontalAlignment(SwingConstants.RIGHT);
		rent_amt.setEditable(false);
		rent_amt.setBounds(529, 439, 185, 23);
		getContentPane().add(rent_amt);

		lblIgst = new JLabel("Round Off:");
		lblIgst.setBounds(403, 474, 126, 20);
		getContentPane().add(lblIgst);

		roundoff = new JTextField();
		roundoff.setHorizontalAlignment(SwingConstants.RIGHT);
		roundoff.setBounds(529, 474, 185, 23);
		getContentPane().add(roundoff);

		
		lblChqAmount = new JLabel("Net Amount:");
		lblChqAmount.setBounds(403, 503, 126, 20);
		getContentPane().add(lblChqAmount);

		net_amt = new JTextField();
		net_amt.setHorizontalAlignment(SwingConstants.RIGHT);
		net_amt.setBounds(529, 503, 185, 23);
		getContentPane().add(net_amt);
	
		lblVehicleNo = new JLabel("Vehicle No:");
		lblVehicleNo.setBounds(403, 530, 126, 20);
		getContentPane().add(lblVehicleNo);

		vehicle_no = new TextField(45);
		vehicle_no.setBounds(529, 530, 382, 23);
		vehicle_no.setFont(fontHindi);
		getContentPane().add(vehicle_no);

		lblRemark = new JLabel("Remark:");
		lblRemark.setBounds(403, 561, 126, 20);
		getContentPane().add(lblRemark);

		remark = new TextField(254);
		remark.setBounds(529, 561, 382, 23);
		remark.setFont(fontHindi);
		getContentPane().add(remark);
		
		
		vou_no.setName("0");
		vou_date.setName("1");
//		vac_code.setName("2");
		vnart_1.setName("3");
//		name1.setName("4");
//		group.setName("5");
//		chq_date.setName("6");
//		rate.setName("7");
//		quantity.setName("8");
//		weight.setName("9");
		rent_amt.setName("10");
		roundoff.setName("11");
		net_amt.setName("12");
		vehicle_no.setName("13");
		remark.setName("14");

//		vou_no.addKeyListener(keyListener); 
		vou_date.addKeyListener(keyListener);
//		vac_code.addKeyListener(keyListener);
		vnart_1.addKeyListener(keyListener);
//		name1.addKeyListener(keyListener);
//		group.addKeyListener(keyListener);
//		chq_date.addKeyListener(keyListener);
//		rate.addKeyListener(keyListener);
//		quantity.addKeyListener(keyListener);
//		weight.addKeyListener(keyListener);
		rent_amt.addKeyListener(keyListener);
		roundoff.addKeyListener(keyListener);
		net_amt.addKeyListener(keyListener);
		remark.addKeyListener(keyListener);
		vehicle_no.addKeyListener(keyListener);
		
		vouTable.addMouseListener(MouseRobot.RobMouseListner());
		
		vou_no.addFocusListener(myFocusListener);
		vou_date.addFocusListener(myFocusListener);
		vnart_1.addFocusListener(myFocusListener);
		rent_amt.addFocusListener(myFocusListener);
		roundoff.addFocusListener(myFocusListener);
		net_amt.addFocusListener(myFocusListener);
		remark.addFocusListener(myFocusListener);
		vehicle_no.addFocusListener(myFocusListener);
		name.addFocusListener(myFocusListener);
		
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
					 
					if (vouno==0)
					{
						JOptionPane.showMessageDialog(InwardEntry.this,"Slip No. "+vouno,"Empty!!!!",JOptionPane.INFORMATION_MESSAGE);						
						vou_no.setText("");
						vou_no.requestFocus();
					}
					else
					{						
						rcp =  idao.getInvDetailNew(vouno,"",loginDt.getFin_year(),60,loginDt.getDiv_code());
						if(rcp==null)
						{
							option=1;
							clearAll();
						}

						if(rcp!=null)
						{

/*							JOptionPane.showMessageDialog(InwardEntry.this,"Slip No. "+vouno,"Already Exists!!!!!",JOptionPane.INFORMATION_MESSAGE);						
							vou_no.setText("");
							vou_no.requestFocus();*/
							option=0;
							fillData(rcp);
						    btnSave.setEnabled(true);
							vouTableModel.getDataVector().clear();
							vouTableModel.addRow(new Object[] {"","","","","","","","","","","",""});
							vouTablePane.requestFocus();
							vouTable.requestFocus();
							fillItemData(rcp);
							vouTable.changeSelection(0, 0, false, false);
							vouTable.editCellAt(0, 0);
						 

						} 
						else
							vou_date.requestFocus();

						evt.consume();
					}
				}
				
			}
		});

		vac_code = new JTextField();
		vac_code.setBounds(529, 194, 86, 23);
		getContentPane().add(vac_code);
		vac_code.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (addBool) {
						if (vac_code.getText().toString().trim().equals("")) {
							Partypane.setVisible(true);
							name.requestFocus();
							// partyList.requestFocus();
							// partyList.setSelectedIndex(0);
						} else {
							hintTextLabel.setVisible(false);
							int getText = setIntNumber(vac_code.getText().toString().trim());
							
							prtyDto = (TransportDto) partyMap.get(vac_code.getText().toString().trim());
							partyCd = getText;

							System.out.println(prtyDto);
							
							if (prtyDto == null) {
								Partypane.setVisible(true);
								partyList.requestFocus();
								partyList.setSelectedIndex(0);
							} else {
								String pname = prtyDto.getMac_name();
								vac_code.setText(String.valueOf(prtyDto.getAccount_no()));
								name.setText(prtyDto.getMac_name());
								vnart_1.setText(prtyDto.getMac_name_hindi());

								Partypane.setVisible(false);
								Partypane.setVisible(false);
								hintTextLabel.setVisible(false);
								
								if(rcp==null)
								{
									vouTableModel.getDataVector().clear();
									vouTableModel.addRow(new Object[] {"","","","","","","","",""});
								}
									vouTablePane.requestFocus();
									vouTable.requestFocus();
									vouTable.changeSelection(0, 0, false, false);
									vouTable.editCellAt(0, 0);
									
							}

						}
					} else {
						alertMessage(InwardEntry.this,
								"Please Click Add Button!!!! ");
					}

				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearAll();
					dispose();
					// System.exit(0);
				}
			}
		});

		vac_code.addFocusListener(myFocusListener);

		
/*		invNoTable.addKeyListener(new KeyAdapter() 
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
				
*/		
		
		
		btnPrint = new JButton("Print");
		btnPrint.setActionCommand("Print");
		btnPrint.setEnabled(false);
		btnPrint.setBounds(42, 605, 70, 30);
		getContentPane().add(btnPrint);
		
		
		btnSave = new JButton("Save");
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.setEnabled(false);
		btnSave.setActionCommand("Save");
		btnSave.setBounds(836, 605, 86, 30);
		getContentPane().add(btnSave);

		btnExit = new JButton("Exit");
		btnExit.setActionCommand("Exit");
		btnExit.setMnemonic(KeyEvent.VK_E);
		btnExit.setBounds(926, 605, 86, 30);
		getContentPane().add(btnExit);
		btnPrint.addActionListener(this);
		btnSave.addActionListener(this);
		btnExit.addActionListener(this);


/*		JPanel panel_4 = new JPanel();
//		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_4.setBackground(Color.LIGHT_GRAY);
		panel_4.setBounds(40, 134, 975, 48);
		getContentPane().add(panel_4);
*/		
		
		lblLastNo = new JLabel("Last No:");
		lblLastNo.setForeground(Color.RED);
		lblLastNo.setBounds(890, 130, 65, 20);
		getContentPane().add(lblLastNo);
		
		lastNo = new JLabel("");
		lastNo.setForeground(Color.RED);
		lastNo.setBounds(960, 130, 51, 20);
		getContentPane().add(lastNo);
		lastNo.setText(String.valueOf(getLastNo()));
		
		transfer = new JLabel("");
		transfer.setForeground(Color.RED);
		transfer.setBounds(660, 130, 200, 20);
		getContentPane().add(transfer);
				
				
				//chq_dt.addKeyListener(keyListener);
		
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
						JOptionPane.showMessageDialog(InwardEntry.this,"Slip No "+billNo,"Slip No",JOptionPane.INFORMATION_MESSAGE);
					}
					vou_no.requestFocus();
					vou_no.setSelectionStart(0);
				}
			}
		});

	}


	public void clearAll()
	{

		
//		vou_date.setValue(null);
//		vdate.setValue(null);
//		checkDate(vdate);
//		checkDate(vou_date);
//		chq_date.setValue(null);
//		checkDate(chq_date);
		vac_code.setText("");
		name.setText("");
		remark.setText("");
		vehicle_no.setText("");
		vnart_1.setText("");
		weight.setText("");
		net_amt.setText("");
		rent_amt.setText("");
		roundoff.setText("");
		
		hintTextLabel.setVisible(true);

		bno="";

		// /// clear item table data
		vouTableModel.getDataVector().clear(); // Remove all TableData
		vouTableModel.fireTableStructureChanged();
		// /// genrate new row in item table/////////////
		vouTableModel.addRow(new Object[] {});
		// //// calling item table ui generate
		itemTableUI();

		Partypane.setVisible(false);
		Grouppane.setVisible(false);
		Categorypane.setVisible(false);
		Packpane.setVisible(false);
		Productpane.setVisible(false);
		 btnSave.setBackground(null);
		 transfer.setText("");
		 lblLastNo.setVisible(true);
		 lastNo.setVisible(true);
		lastNo.setText(String.valueOf(getLastNo()));


	}


	public ArrayList vouAdd() {

		sdf = new SimpleDateFormat("dd/MM/yyyy"); // Date Format
		double taxper=0.00;
		Vector col = null;
		InvViewDto rcp1 = null;
		ArrayList dataList = null;
		boolean first = true;
		int size=0;
		int qty=0;
		Vector vouData = vouTableModel.getDataVector();
		size=vouData.size();
			
		System.out.println("SIZE IS "+size);
		try {
			for (int i = 0; i < size; i++) {

				if (first) {
					dataList = new ArrayList();
					first = false;
				}
				col = (Vector) vouData.get(i);
				try {
					qty=setIntNumber(col.get(5).toString().trim());
				} catch (Exception e) {
					qty=0;}
				
				if(qty>0)
				{
					
					
					
					rcp1 = new InvViewDto();
					
					billNo=setIntNumber(vou_no.getText().trim());
					rcp1.setDepo_code(loginDt.getDepo_code());
					rcp1.setDiv_code(loginDt.getDiv_code());
					rcp1.setDoc_type(60);
					rcp1.setSinv_no(setIntNumber(vou_no.getText().trim()));
					rcp1.setInv_date(sdf.parse(vou_date.getText()));
					rcp1.setMac_code(vac_code.getText());
					
					
					rcp1.setRound_off(setDoubleNumber(roundoff.getText().trim()));
					rcp1.setNet_amt(setDoubleNumber(net_amt.getText().trim()));
					rcp1.setRemark(remark.getText());
					rcp1.setCreated_by(loginDt.getLogin_id());
					rcp1.setCreated_date(new Date());
					rcp1.setFin_year(loginDt.getFin_year());
					rcp1.setVehicle_no(vehicle_no.getText());
					
					


					if (col.get(0) != null) {
						rcp1.setCategory_hindi(col.get(0).toString());
						rcp1.setCategory(col.get(11).toString());
					}

					
					if (col.get(1) != null) {
						rcp1.setSpd_gp(setIntNumber(col.get(9).toString()));
					}

					if (col.get(2) != null) {
						rcp1.setSprd_cd(setIntNumber(col.get(10).toString()));
					}


					if (col.get(3) != null)
						rcp1.setPack(col.get(3).toString());


					if (col.get(4) != null)
						rcp1.setSrate_net((setDoubleNumber(col.get(4).toString())));

					if (col.get(5) != null) {
						rcp1.setSqty(setIntNumber(col.get(5).toString()));
					}

					if (col.get(6) != null)
						rcp1.setWeight((setDoubleNumber(col.get(6).toString())));

					if (col.get(7) != null)
						rcp1.setSamnt((setDoubleNumber(col.get(7).toString())));

					if (col.get(8) != null) {
						rcp1.setRst_no(setIntNumber(col.get(8).toString()));
					}
					
					dataList.add(rcp1);
				} // end of if(Exp code zero nahi hona chahiye)
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in vouAdd " + e);
		}

		return dataList;

	}
	 

	

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		
		if(e.getActionCommand().equalsIgnoreCase("print"))
		{

/*					InvOpt copt = new InvOpt("com.coldstorage.print.GenerateInvoiceGST ","Inward Printing");
					copt.setVisible(true);

*/					
			 		new GenerateInvoiceGST(String.valueOf(loginDt.getFin_year()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),vou_no.getText(),vou_no.getText(),"PDF",loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),60,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);

			
		}

		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			int ino = saveData();
			

			if(ino!=0)
			{
				JOptionPane.showMessageDialog(InwardEntry.this,"Slip No "+billNo,"Slip No",JOptionPane.INFORMATION_MESSAGE);
			}
			vou_no.requestFocus();
			vou_no.setSelectionStart(0);

		}
		
		
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			option=0;
			btnPrint.setEnabled(false);
			vou_no.setEditable(true); 
			clearAll();
			vou_no.setText("");
			dispose();
		}

		
		
	}


	public int saveData() 
	{
		int recordNo=0;
		
		
		System.out.println("OPTION KI VALUE "+option);
		
		if ((option == 1 || option == 2)) {

			ArrayList l = vouAdd();
			int vno=setIntNumber(vou_no.getText().trim());
			System.out.println("vno ki value ***** "+vno);
			if(vno>0 )
			{
				if (l != null)
					recordNo = idao.addFstRecord(l);
			}
			else
				alertMessage(InwardEntry.this, "Can't Save this Inward Vou No is 0 !!!!1");

		}

		
		
		if (option == 0) {

			if (edit.equalsIgnoreCase("false"))
				alertMessage(InwardEntry.this, "Can't Edit this Inward");
			else {
				int vno=setIntNumber(vou_no.getText());
				System.out.println("vno ki value ***** in edit  "+vno);

				if(vno>0)
				{
				
				ArrayList l = vouAdd();
				if (l != null)
					recordNo = idao.updateFstRecord(l);
				}
				else 
					alertMessage(InwardEntry.this, "Can't Save this Inward Vou No is 0 !!!!1");
//					System.out.println("Can't Save this Inward Vou No is 0 !!!!1");

			}
		}

		
		btnPrint.setEnabled(false);
		vou_no.setEditable(true);
		option=1;
		clearAll();
		vou_no.setText("");
		return recordNo;
	}
	
	
	 

	public void fillData(InvViewDto rcp)
	{
		// voucher header
		vou_no.setText(String.valueOf(rcp.getInv_no()));
		vou_date.setText(sdf.format(rcp.getInv_date()));
		vac_code.setText(rcp.getMac_code());
		name.setText((rcp.getMac_name()+","+rcp.getMcity()));
		vnart_1.setText(rcp.getMac_name_hindi()+","+rcp.getMcity_hindi());
		rent_amt.setText(formatter.format(rcp.getSamnt()));
		roundoff.setText(formatter.format(rcp.getRound_off()));
		net_amt.setText(formatter.format(rcp.getNet_amt()));
		weight.setText(formatter.format(rcp.getTotal_weight()));
		remark.setText(rcp.getRemark());
		vehicle_no.setText(rcp.getVehicle_no());
		if(rcp.getFromhq().length()>0)
		{
			transfer.setText("Trasnfer From Khata No "+rcp.getFromhq());
			lblLastNo.setVisible(false);
			lastNo.setVisible(false);
		}
		else
		{
			transfer.setText(" ");
			lblLastNo.setVisible(true);
			lastNo.setVisible(true);
		}
		
		hintTextLabel.setVisible(false);
		btnPrint.setEnabled(true);
		edit=rcp.getMark_no();
		System.out.println("value of edit "+edit);
		
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
		
		
		vouTableModel.getDataVector().clear();
		Vector c = null;
		ArrayList data =rcp.getMarkalist();
		
		int size=data.size();
		System.out.println("size of datalist is "+size);
		for(int i =0;i<size;i++)
		{
			c = (Vector) data.get(i);
			System.out.println("category "+c.get(0).toString());
			vouTableModel.addRow(c);
		}
		
		
	}

	
	public void itemTableUI() 
	{
		
		//		vouTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		vouTable.setFont(new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14));
		vouTable.setColumnSelectionAllowed(false);
		vouTable.setCellSelectionEnabled(true);
		vouTable.getTableHeader().setReorderingAllowed(false);
		vouTable.getTableHeader().setResizingAllowed(false);
		vouTable.setRowHeight(20);
		vouTable.getTableHeader().setPreferredSize(new Dimension(25, 25));
		vouTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

		vouTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		vouTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		vouTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		vouTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

		vouTable.getColumnModel().getColumn(0).setPreferredWidth(50); // CATEGORY
		vouTable.getColumnModel().getColumn(1).setPreferredWidth(40); // GROUP
		vouTable.getColumnModel().getColumn(2).setPreferredWidth(70); // ITEM
		vouTable.getColumnModel().getColumn(3).setPreferredWidth(40); // PACK
		vouTable.getColumnModel().getColumn(4).setPreferredWidth(40); // RATE
		vouTable.getColumnModel().getColumn(5).setPreferredWidth(50); // QTY
		vouTable.getColumnModel().getColumn(6).setPreferredWidth(50); // WEIGHT
		vouTable.getColumnModel().getColumn(7).setPreferredWidth(60); // AMOUNT
		vouTable.getColumnModel().getColumn(8).setPreferredWidth(50); // RSY no

		vouTable.getColumnModel().getColumn(9).setPreferredWidth(0);
		vouTable.getColumnModel().getColumn(9).setMinWidth(0); // grp_cd
		vouTable.getColumnModel().getColumn(9).setMaxWidth(0);

		vouTable.getColumnModel().getColumn(10).setPreferredWidth(0);
		vouTable.getColumnModel().getColumn(10).setMinWidth(0); // sprd_cd
		vouTable.getColumnModel().getColumn(10).setMaxWidth(0);


		vouTable.getColumnModel().getColumn(11).setPreferredWidth(0);
		vouTable.getColumnModel().getColumn(11).setMinWidth(0); // tag
		vouTable.getColumnModel().getColumn(11).setMaxWidth(0);


		vouTable.setDefaultEditor(Double.class, new OverWriteTableCellEditor());
		vouTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());
		
		
		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer() 
		{
			public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) 
			{
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if(table.isCellSelected(row, column))
				{
					c.setBackground(new Color(171, 203, 255));
					
				}
				else if(table.isColumnSelected(column))
				{
					c.setBackground(new Color(171, 203, 255));
					
				}
				
				else if(hasFocus)
				{
					c.setBackground(new Color(171, 203, 255));
				}
				else
				{
					System.out.println("ELSE PARTY column no "+column);
					c.setBackground(Color.WHITE);
					c.setForeground(Color.BLACK);
				}
				return c;
			}
		};
		
		 
		vouTable.getColumnModel().getColumn(0).setCellRenderer(dtc);
		vouTable.getColumnModel().getColumn(1).setCellRenderer(dtc);
		vouTable.getColumnModel().getColumn(2).setCellRenderer(dtc);
		vouTable.getColumnModel().getColumn(3).setCellRenderer(dtc);
		vouTable.getColumnModel().getColumn(4).setCellRenderer(dtc);
		vouTable.getColumnModel().getColumn(5).setCellRenderer(dtc);
		vouTable.getColumnModel().getColumn(6).setCellRenderer(dtc);
		vouTable.getColumnModel().getColumn(7).setCellRenderer(dtc);
		vouTable.getColumnModel().getColumn(8).setCellRenderer(dtc);


	}

	
	public void totPara() {
		Vector col = null;
		tot = 0;
		double totweight=0.00;
		Vector vouData = vouTableModel.getDataVector();
		try {
			for (int i = 0; i < vouData.size(); i++) {
				col = (Vector) vouData.get(i);
				if (col.get(6) != null)
				{
					tot += setDoubleNumber(col.get(7).toString());
					totweight += setDoubleNumber(col.get(6).toString());
				}

			}
			
			tot=roundTwoDecimals(tot);
			totweight=roundTwoDecimals(totweight);
			rent_amt.setText(formatter.format(tot));
			weight.setText(formatter.format(totweight));

		} catch (Exception e) {
			System.out.println("Error in totPara " + e);
		}
	}

/*	private void checkDateValidity(String date)
	{
		if(isValidDate(date))
		{
			//lblinvalid.setVisible(false);
			// Check the Date Range for the Financial year
			if (isValidRange(date,loginDt.getFr_date(),loginDt.getTo_date()))
			{
				vac_code.requestFocus();
			}
			else
			{
				JOptionPane.showMessageDialog(InwardEntry.this,"Date range should be in between: "
				+ sdf.format(loginDt.getFr_date())+ " to " + sdf.format(loginDt.getTo_date()),
				"Date Range",	JOptionPane.INFORMATION_MESSAGE);

				alertMessage(InwardEntry.this,"Date range should be in between: "
				+ sdf.format(loginDt.getFr_date())+ " to " + sdf.format(loginDt.getTo_date()));
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

	}
	
*/	
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


