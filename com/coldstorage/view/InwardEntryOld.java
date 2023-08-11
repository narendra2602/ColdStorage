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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
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
import com.coldstorage.dto.RcpDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.print.GenerateInvoiceGST;
import com.coldstorage.util.JIntegerField;

public class InwardEntryOld extends BaseClass implements ActionListener {


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
	private JPanel panel_2;
	private SimpleDateFormat sdf;
	private NumberFormat df;
	private DefaultTableModel invNoTableModel;
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
	private JLabel hintTextLabel,hintTextLabelcat,hintTextLabelgrp,hintTextLabelprd;
	private boolean addBool;

	private JTextField pname;

	private JLabel lblRemark;

	private JTextField remark;

	private JTextField pname_hindi;

	private JTextField group_hindi;

	private JTextField name1_hindi;


	public InwardEntryOld()
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
		
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		bno="";
		pdao = new TransportDAO();
		idao = new InvPrintDAO();
		
		partyNames = (Vector) pdao.getTransportList(loginDt.getDepo_code(),loginDt.getDiv_code() );
		categoryNames = (Vector) pdao.getCategoryList();
		groupNames = (Vector) pdao.getGroupList(loginDt.getDiv_code());
		productNames = (Vector) pdao.getProductList(1);
		
		
		partyMap = (HashMap) pdao.getPartyNameMap(loginDt.getDepo_code(),loginDt.getDiv_code());

		
//		final Vector partyNames = (Vector) loginDt.getAcctList();
//		final Map partyMap = (HashMap) loginDt.getAcctmap();
		
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
		///////////////////////////////////////////////////////
		
		
	

		partyList = new JList();
		//partyList.setFont(font);
		partyList.setSelectedIndex(0);
		partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Partypane = new JScrollPane(partyList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Partypane.setBounds(625, 282, 286, 259);
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
					name1.requestFocus();
					 
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
		categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Categorypane = new JScrollPane(categoryList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Categorypane.setBounds(529, 342, 286, 259);
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

					name1.setText(catDto.getCategory_name());
					name1_hindi.setText(catDto.getCategory_name_hindi());
					 
					// evt.consume();
					Categorypane.setVisible(false);
					hintTextLabelcat.setVisible(false);
					group.requestFocus();
					 
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

					 
					// evt.consume();
					Categorypane.setVisible(false);
					// inv_date.requestFocus();
					chq_date.requestFocus();
				}
				
			}
		});

		groupList = new JList();
		groupList.setSelectedIndex(0);
		groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Grouppane = new JScrollPane(groupList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Grouppane.setBounds(529, 373, 286, 259);
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

					group.setText(grpDto.getGp_name());
					group_hindi.setText(grpDto.getGp_name_hindi());
					productNames = (Vector) pdao.getProductList(groupcd);
					productList.removeAll();
					productList.setListData(productNames);
					productList.setSelectedIndex(0);
					 
					// evt.consume();
					Grouppane.setVisible(false);
					hintTextLabelgrp.setVisible(false);
					pname.requestFocus();
					 
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

					 
					// evt.consume();
					Grouppane.setVisible(false);
					// inv_date.requestFocus();
					chq_date.requestFocus();
				}
				
			}
		});

		productList = new JList();
		productList.setSelectedIndex(0);
		productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Productpane = new JScrollPane(productList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Productpane.setBounds(529, 404, 286, 259);
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

					pname.setText(pdto.getPname());
					pname_hindi.setText(pdto.getPack_name());
					rate.setText(formatter.format(pdto.getNet_rt1()));
					// evt.consume();
					Productpane.setVisible(false);
					hintTextLabelprd.setVisible(false);
					quantity.requestFocus();
					 
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

					 
					// evt.consume();
					Productpane.setVisible(false);
					// inv_date.requestFocus();
					quantity.requestFocus();
				}
				
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane(invNoTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(16, 235, 283, 400);
		getContentPane().add(scrollPane);
		
		
		
		///////////////////////////////////////////////////////

		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(540, 150, 577, 15);
		getContentPane().add(lblFinancialAccountingYear);

		lblvouno = new JLabel("Slip Number:");
		lblvouno.setBounds(403, 196, 126, 20);
		getContentPane().add(lblvouno);

		vou_no = new JTextField();
		vou_no.setBounds(529, 196,132, 23);
		getContentPane().add(vou_no);

		lblvoudate = new JLabel("Date:");
		lblvoudate.setBounds(403, 227, 126, 20);
		getContentPane().add(lblvoudate);

		lblvaccode = new JLabel("Vendor Code:");
		lblvaccode.setBounds(403, 260, 126, 20);
		getContentPane().add(lblvaccode);


		vou_date = new JFormattedTextField(sdf);
		vou_date.setBounds(529, 227, 132, 23);
		checkDate(vou_date);
		vou_date.setText(sdf.format(new Date()));
		getContentPane().add(vou_date);


/*		label = new JLabel(cmpname1);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setForeground(new Color(220, 20, 60));
		label.setFont(new Font("Bookman Old Style", Font.BOLD, 23));
		label.setBounds(10, 48, 184, 22);
		getContentPane().add(label);
*/

/*		JLabel promleb = new JLabel(promLogo);
		promleb.setBounds(10, 670, 35, 35);
		getContentPane().add(promleb);

		JLabel arisleb = new JLabel(arisLogo);
		arisleb.setBounds(0, 28, 200, 60);
		getContentPane().add(arisleb);
*/
/*		label_2 = new JLabel(cmpname2);
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(10, 71, 195, 14);
		getContentPane().add(label_2);

		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);
*/
		branch = new JLabel(loginDt.getBrnnm());
		branch.setForeground(Color.BLACK);
		branch.setFont(new Font("Tahoma", Font.BOLD, 22));
		branch.setBounds(400, 66, 560, 24);
		getContentPane().add(branch);



		lblDispatchEntry = new JLabel("Inward Entry");
		lblDispatchEntry.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDispatchEntry.setBounds(452, 94, 382, 22);
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
						String date=vou_date.getText();
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
								JOptionPane.showMessageDialog(InwardEntryOld.this,"Date range should be in between: "
								+ sdf.format(loginDt.getFr_date())+ " to " + sdf.format(loginDt.getTo_date()),
								"Date Range",	JOptionPane.INFORMATION_MESSAGE);
								vou_date.setValue(null);
								checkDate(vou_date);
								vou_date.requestFocus();

							}


						}
						else
						{
							/*lblinvalid.setText("invalid date");
							lblinvalid.setForeground(Color.red);
							lblinvalid.setVisible(true);*/
							vou_date.setValue(null);
							checkDate(vou_date);
							vou_date.requestFocus();
						}
						break;

						
						
					case 2:
						vnart_1.requestFocus();
						break;
					case 3:
//						party_name.requestFocus();
						break;

					case 4:
//						group.requestFocus();
//						group.setSelectionStart(0);
						break;
					case 5:
						chq_date.requestFocus();
						break;
					case 6:
						rate.requestFocus();   //rate
						rate.setSelectionStart(0);
						break;
					case 7:
						rate.setText(formatter.format(setDoubleNumber(rate.getText())));
						quantity.requestFocus();  //qty
						quantity.setSelectionStart(0);
						break;
					case 8:
						quantity.setText(formatter.format(setDoubleNumber(quantity.getText())));
						weight.requestFocus();  // weight
						weight.setSelectionStart(0);
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
							 remark.requestFocus(); 						
							 remark.setSelectionStart(0);
						 }
						break;
					case 13:
							 btnSave.setBackground(Color.BLUE); 
							 btnSave.requestFocus(); 						
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

	

		

		
		

		 



		// ///////////////////////////////////////////////
		

		lblVoucherDate = new JLabel("Slip Date:");
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
		
		lblNarration = new JLabel("Vendor In Hindi:");
		lblNarration.setBounds(403, 291, 126, 20);
		getContentPane().add(lblNarration);
		
		vnart_1 = new JTextField();
		vnart_1.setBounds(529, 289, 382, 23);
		vnart_1.setFont(fontHindi);
		getContentPane().add(vnart_1);
		
		lblPaidTo = new JLabel("Category.:");
		lblPaidTo.setBounds(403, 321, 126, 20);
		getContentPane().add(lblPaidTo);
		
		
		hintTextLabel = new JLabel("Search by Party Name");
		hintTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		hintTextLabel.setForeground(Color.GRAY);
		hintTextLabel.setBounds(627, 259, 231, 22);
		getContentPane().add(hintTextLabel);

		
		name = new JTextField();
		name.setName("2");
		name.setBounds(625, 259, 286, 23);
		getContentPane().add(name);
		name.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				// TODO Auto-generated method stub

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if(name.getText().length()==0)
						alertMessage(InwardEntryOld.this, "Please select Party!");
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


		hintTextLabelcat = new JLabel("Search by Category");
		hintTextLabelcat.setFont(new Font("Tahoma", Font.PLAIN, 11));
		hintTextLabelcat.setForeground(Color.GRAY);
		hintTextLabelcat.setBounds(531, 319, 231, 22);
		getContentPane().add(hintTextLabelcat);


		
		name1_hindi = new JTextField();
		name1_hindi.setBounds(725, 319, 185, 23);
		name1_hindi.setEditable(false);
		name1_hindi.setFont(fontHindi);
		getContentPane().add(name1_hindi);

		
		name1 = new JTextField();
		name1.setName("3");
		name1.setBounds(529, 319, 185, 23);
		name1.setFont(fontHindi);
		getContentPane().add(name1);
		name1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				// TODO Auto-generated method stub

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if(name1.getText().length()==0)
					{
						Categorypane.setVisible(true);
						name1.requestFocus();
					}
					else
						group.requestFocus();
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
					int size = categoryList.getFirstVisibleIndex();
					if (size >= 0) {
						categoryList.requestFocus();
						categoryList.setSelectedIndex(0);
						categoryList.ensureIndexIsVisible(0);
						hintTextLabelcat.setVisible(false);
					}
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Categorypane.setVisible(false);
					// party_code.requestFocus();
					// party_name.setText("");
					name1.requestFocus();
					hintTextLabelcat.setVisible(true);
					evt.consume();
				}
			}

			public void keyReleased(KeyEvent evt) {

					if (!Categorypane.isVisible()
							&& (evt.getKeyCode() != KeyEvent.VK_ENTER && evt
									.getKeyCode() != KeyEvent.VK_ESCAPE)) {
						Categorypane.setVisible(true);
					}
					searchFilterCat(name1.getText(), categoryList, categoryNames);
					if (name1.getText().length() > 0)
						hintTextLabelcat.setVisible(false);
					else
						hintTextLabelcat.setVisible(true);
				}
				// evt.consume();

		});

		

		
		lblChqNo = new JLabel("Group:");
		lblChqNo.setBounds(403, 351, 126, 20);
		getContentPane().add(lblChqNo);
		
		hintTextLabelgrp = new JLabel("Search by Group");
		hintTextLabelgrp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		hintTextLabelgrp.setForeground(Color.GRAY);
		hintTextLabelgrp.setBounds(531, 349, 382, 22);
		getContentPane().add(hintTextLabelgrp);


		group_hindi = new JTextField();
		group_hindi.setBounds(725, 349, 185, 23);
		group_hindi.setFont(fontHindi);
		group_hindi.setEditable(false);
		getContentPane().add(group_hindi);

		
		
		group = new JTextField();
		group.setBounds(529, 349, 185, 23);
		group.setFont(fontHindi);
		getContentPane().add(group);
		group.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				// TODO Auto-generated method stub

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if(group.getText().length()==0)
					{
						Grouppane.setVisible(true);
						group.requestFocus();
					}
					else
						pname.requestFocus();
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
					int size = groupList.getFirstVisibleIndex();
					if (size >= 0) {
						groupList.requestFocus();
						groupList.setSelectedIndex(0);
						groupList.ensureIndexIsVisible(0);
						hintTextLabelgrp.setVisible(false);
					}
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Grouppane.setVisible(false);
					// party_code.requestFocus();
					// party_name.setText("");
					group.requestFocus();
					hintTextLabelgrp.setVisible(true);
					evt.consume();
				}
			}

			public void keyReleased(KeyEvent evt) {

					if (!Grouppane.isVisible()
							&& (evt.getKeyCode() != KeyEvent.VK_ENTER && evt
									.getKeyCode() != KeyEvent.VK_ESCAPE)) {
						Grouppane.setVisible(true);
					}
					searchFilterCat(group.getText(), groupList, groupNames);
					if (group.getText().length() > 0)
						hintTextLabelgrp.setVisible(false);
					else
						hintTextLabelgrp.setVisible(true);
				}
				// evt.consume();

		});
		

		lblChqDate = new JLabel("Product Name:");
		lblChqDate.setBounds(403, 382, 126, 20);
		getContentPane().add(lblChqDate);

		
		hintTextLabelprd = new JLabel("Search by Product");
		hintTextLabelprd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		hintTextLabelprd.setForeground(Color.GRAY);
		hintTextLabelprd.setBounds(531, 382, 382, 22);
		getContentPane().add(hintTextLabelprd);


		
		pname_hindi = new JTextField();
		pname_hindi.setBounds(725, 382, 185, 23);
		pname_hindi.setFont(fontHindi);
		pname_hindi.setEditable(false);
		getContentPane().add(pname_hindi);

		
		pname = new JTextField();
		pname.setBounds(529, 382, 185, 23);
		pname.setFont(fontHindi);
		getContentPane().add(pname);
		pname.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				// TODO Auto-generated method stub

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if(pname.getText().length()==0)
					{
						Productpane.setVisible(true);
						pname.requestFocus();
					}
					else
						rate.requestFocus();
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
					int size = productList.getFirstVisibleIndex();
					if (size >= 0) {
						productList.requestFocus();
						productList.setSelectedIndex(0);
						productList.ensureIndexIsVisible(0);
						hintTextLabelprd.setVisible(false);
					}
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Productpane.setVisible(false);
					// party_code.requestFocus();
					// party_name.setText("");
					pname.requestFocus();
					hintTextLabelprd.setVisible(true);
					evt.consume();
				}
			}

			public void keyReleased(KeyEvent evt) {

					if (!Productpane.isVisible()
							&& (evt.getKeyCode() != KeyEvent.VK_ENTER && evt
									.getKeyCode() != KeyEvent.VK_ESCAPE)) {
						Productpane.setVisible(true);
					}
					searchFilter(pname.getText(), productList, productNames);
					if (pname.getText().length() > 0)
						hintTextLabelprd.setVisible(false);
					else
						hintTextLabelprd.setVisible(true);
				}
				// evt.consume();

		});

		
/*		chq_date = new JFormattedTextField(sdf);
		chq_date.setName("1");
		checkDate(chq_date);
		chq_date.setBounds(529, 382, 132, 23);
		getContentPane().add(chq_date);
*/		
		
		lblTaxable = new JLabel("Rate Per Kg:");
		lblTaxable.setBounds(403, 415, 126, 20);
		getContentPane().add(lblTaxable);

		rate = new JTextField();
		rate.setHorizontalAlignment(SwingConstants.RIGHT);
		rate.setEditable(false);
		rate.setBounds(529, 413, 185, 23);
		getContentPane().add(rate);

		lblGstTax = new JLabel("Quantity:");
		lblGstTax.setBounds(403, 444, 126, 20);
		getContentPane().add(lblGstTax);

		quantity = new JTextField();
		quantity.setHorizontalAlignment(SwingConstants.RIGHT);
		quantity.setBounds(529, 442, 185, 23);
		getContentPane().add(quantity);

		
		lblCgst = new JLabel("Weight:");
		lblCgst.setBounds(403, 475, 126, 20);
		getContentPane().add(lblCgst);

		weight = new JTextField();
		weight.setHorizontalAlignment(SwingConstants.RIGHT);
		weight.setBounds(529, 473, 185, 23);
		getContentPane().add(weight);
	
	
		lblSgst = new JLabel("Total Rent:");
		lblSgst.setBounds(403, 506, 126, 20);
		getContentPane().add(lblSgst);

		rent_amt = new JTextField();
		rent_amt.setHorizontalAlignment(SwingConstants.RIGHT);
		rent_amt.setEditable(false);
		rent_amt.setBounds(529, 504, 185, 23);
		getContentPane().add(rent_amt);

		lblIgst = new JLabel("Round Off:");
		lblIgst.setBounds(403, 539, 126, 20);
		getContentPane().add(lblIgst);

		roundoff = new JTextField();
		roundoff.setHorizontalAlignment(SwingConstants.RIGHT);
		roundoff.setBounds(529, 539, 185, 23);
		getContentPane().add(roundoff);

		
		lblChqAmount = new JLabel("Net Amount:");
		lblChqAmount.setBounds(403, 568, 126, 20);
		getContentPane().add(lblChqAmount);

		net_amt = new JTextField();
		net_amt.setHorizontalAlignment(SwingConstants.RIGHT);
		net_amt.setBounds(529, 568, 185, 23);
		getContentPane().add(net_amt);
	
		lblRemark = new JLabel("Remark:");
		lblRemark.setBounds(403, 597, 126, 20);
		getContentPane().add(lblRemark);

		remark = new JTextField();
		remark.setBounds(529, 595, 382, 23);
		remark.setFont(fontHindi);
		getContentPane().add(remark);

		
		
		vou_no.setName("0");
		vou_date.setName("1");
//		vac_code.setName("2");
		vnart_1.setName("3");
		name1.setName("4");
		group.setName("5");
//		chq_date.setName("6");
		rate.setName("7");
		quantity.setName("8");
		weight.setName("9");
		rent_amt.setName("10");
		roundoff.setName("11");
		net_amt.setName("12");
		remark.setName("13");

//		vou_no.addKeyListener(keyListener); 
		vou_date.addKeyListener(keyListener);
//		vac_code.addKeyListener(keyListener);
		vnart_1.addKeyListener(keyListener);
//		name1.addKeyListener(keyListener);
//		group.addKeyListener(keyListener);
//		chq_date.addKeyListener(keyListener);
		rate.addKeyListener(keyListener);
		quantity.addKeyListener(keyListener);
		weight.addKeyListener(keyListener);
		rent_amt.addKeyListener(keyListener);
		roundoff.addKeyListener(keyListener);
		net_amt.addKeyListener(keyListener);
		remark.addKeyListener(keyListener);
		
		
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
						JOptionPane.showMessageDialog(InwardEntryOld.this,"Slip No. "+vouno,"Empty!!!!",JOptionPane.INFORMATION_MESSAGE);						
						vou_no.setText("");
						vou_no.requestFocus();
					}
					else
					{						
						rcp =  idao.getInvDetail(vouno,"",loginDt.getFin_year(),60);

						if(rcp!=null)
						{

							JOptionPane.showMessageDialog(InwardEntryOld.this,"Slip No. "+vouno,"Already Exists!!!!!",JOptionPane.INFORMATION_MESSAGE);						
							vou_no.setText("");
							vou_no.requestFocus();
						} 
						else
							vou_date.requestFocus();

						evt.consume();
					}
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
					int subdiv=1;
					vno.setText("");
					 rcp = (InvViewDto) idao.getInvDetail(vouno,"",loginDt.getFin_year(),60);
					if(rcp==null)
					{
						clearAll();
						JOptionPane.showMessageDialog(InwardEntryOld.this,"No Record for Vou No. "+vouno,"Record not found",JOptionPane.INFORMATION_MESSAGE);						
						
					} 
					else
					{
						fillData(rcp);
					 
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
							
							Vector data = (Vector) idao.getVouList2(sdf.parse(vdate.getText()),loginDt.getFin_year(),60);
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
						e.printStackTrace();
					}
					evt.consume();
				}
				
			}
		});
		
/*		vac_code.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (vac_code.getText().toString().trim().equals("")) {
						Partypane.setVisible(true);
						partyList.requestFocus();
						partyList.setSelectedIndex(0);
						partyList.ensureIndexIsVisible(0);
					} else {
						int getText = setIntNumber(vac_code.getText().toString().trim());
						prtyDto = (TransportDto) partyMap.get(getText);
						

						partyCd = getText;
						
						if (prtyDto == null) {
							Partypane.setVisible(true);
							partyList.requestFocus();
							partyList.setSelectedIndex(0);
						} else {
							 
							String pname = prtyDto.getTran_name();
							

							name.setText(pname);
							Partypane.setVisible(false);
							vnart_1.requestFocus();
						}

					}

				}
			}
		});	
*/		
		

		vac_code = new JTextField();
		vac_code.setBounds(529, 259, 86, 23);
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
								name1.setCaretPosition(0);

								Partypane.setVisible(false);
								name1.requestFocus();
							}

						}
					} else {
						alertMessage(InwardEntryOld.this,
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
				
		
		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setBounds(350, 670, 70, 30);
		getContentPane().add(btnAdd);
		
		btnDelete = new JButton("Delete");
		btnDelete.setActionCommand("Delete");
		btnDelete.setBounds(422, 670, 70, 30);
		getContentPane().add(btnDelete);
		
		btnPrint = new JButton("Print");
		btnPrint.setActionCommand("Print");
		btnPrint.setBounds(422, 670, 70, 30);
		getContentPane().add(btnPrint);
		
		btnMiss = new JButton("Add Missing Voucher");
		btnMiss.setActionCommand("Miss");
		btnMiss.setBounds(576, 670, 159, 30);
		getContentPane().add(btnMiss);
		
		btnSave = new JButton("Save");
		btnSave.setActionCommand("Save");
		btnSave.setBounds(795, 670, 86, 30);
		getContentPane().add(btnSave);

		btnExit = new JButton("Exit");
		btnExit.setActionCommand("Exit");
		btnExit.setBounds(887, 670, 86, 30);
		getContentPane().add(btnExit);
		btnAdd.addActionListener(this);
		btnDelete.addActionListener(this);
		btnPrint.addActionListener(this);
		btnMiss.addActionListener(this);
		btnSave.addActionListener(this);
		btnExit.addActionListener(this);
		btnDelete.setEnabled(false);
//		btnPrint.setEnabled(false);
		btnMiss.setVisible(false);
		btnDelete.setVisible(false);
		

/*		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(346, 657, 631, 48);
		getContentPane().add(panel_2);
*/

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBackground(Color.LIGHT_GRAY);
		panel_4.setBounds(346, 134, 631, 48);
		getContentPane().add(panel_4);
		
		
		lblLastNo = new JLabel("Last No:");
		lblLastNo.setForeground(Color.RED);
		lblLastNo.setBounds(856, 195, 65, 20);
		getContentPane().add(lblLastNo);
		
		lastNo = new JLabel("");
		lastNo.setForeground(Color.RED);
		lastNo.setBounds(919, 196, 51, 20);
		getContentPane().add(lastNo);
		lastNo.setText(String.valueOf(getLastNo()));
		
				
				
				//chq_dt.addKeyListener(keyListener);
		
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				panel_1.setBounds(346, 186, 631, 518);
				getContentPane().add(panel_1);
		

		btnSave.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					
					int ino = saveData();
					if(ino!=0)
					{
						JOptionPane.showMessageDialog(InwardEntryOld.this,"Debit Note No "+ino,"Debit Note",JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
			}
		});

	}


	public void clearAll()
	{

		vou_no.setText("");
//		vou_date.setValue(null);
		vdate.setValue(null);
		checkDate(vdate);
//		checkDate(vou_date);
		group.setText("");
//		chq_date.setValue(null);
//		checkDate(chq_date);
		vac_code.setText("");
		name.setText("");
		pname.setText("");
		pname_hindi.setText("");
		remark.setText("");
		name1.setText("");
		name1_hindi.setText("");
		group.setText("");
		group_hindi.setText("");
		vnart_1.setText("");
		net_amt.setText("");
		rate.setText("");
		weight.setText("");
		rent_amt.setText("");
		roundoff.setText("");
		quantity.setText("");
		Partypane.setVisible(false);
		hintTextLabel.setVisible(true);
		hintTextLabelcat.setVisible(true);
		hintTextLabelgrp.setVisible(true);
		hintTextLabelprd.setVisible(true);

		bno="";
		invNoTableModel.getDataVector().removeAllElements();
		//vouTableModel.getDataVector().removeAllElements();
		vno.requestFocus();

		 btnSave.setBackground(null);
		lastNo.setText(String.valueOf(getLastNo()));


	}


	 

	public InvViewDto vouAdd() 
	{
		 
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		 
		InvViewDto rcp1 = null;
		try {
			 

				 
				rcp1 = new InvViewDto();
				
			
				rcp1.setDepo_code(loginDt.getDepo_code());
				rcp1.setDoc_type(60);
				rcp1.setSinv_no(setIntNumber(vou_no.getText()));
				rcp1.setInv_date(sdf.parse(vou_date.getText()));
				rcp1.setMac_code(vac_code.getText());
				rcp1.setSprd_cd(pcode);
				rcp1.setSpd_gp(groupcd);
				rcp1.setSqty(setDoubleNumber(quantity.getText()));
				rcp1.setSrate_net(setDoubleNumber(rate.getText()));
				rcp1.setSamnt(setDoubleNumber(rent_amt.getText()));
				rcp1.setRound_off(setDoubleNumber(roundoff.getText()));
				rcp1.setNet_amt(setDoubleNumber(net_amt.getText()));
				rcp1.setRemark(remark.getText());
				rcp1.setCreated_by(loginDt.getLogin_id());
				rcp1.setCreated_date(new Date());
				rcp1.setFin_year(loginDt.getFin_year());
				rcp1.setWeight((setDoubleNumber(weight.getText())));
				rcp1.setCategory_hindi(name1_hindi.getText());
				rcp1.setCategory(name1.getText());
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return rcp1;

	}

	
	public InvViewDto vouUpdate() 
	{
		 
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		 
		InvViewDto rcp1 = null;
		 
		
		 
	 
		try {
			 

				 
				rcp1 = new InvViewDto();
				rcp1.setDepo_code(loginDt.getDepo_code());
				rcp1.setDoc_type(60);
				rcp1.setSinv_no(setIntNumber(vou_no.getText()));
				rcp1.setInv_date(sdf.parse(vou_date.getText()));
				rcp1.setMac_code(vac_code.getText());
				rcp1.setSprd_cd(pcode);
				rcp1.setSpd_gp(groupcd);
				rcp1.setSqty(setDoubleNumber(quantity.getText()));
				rcp1.setSrate_net(setDoubleNumber(rate.getText()));
				rcp1.setSamnt(setDoubleNumber(rent_amt.getText()));
				rcp1.setRound_off(setDoubleNumber(roundoff.getText()));
				rcp1.setNet_amt(setDoubleNumber(net_amt.getText()));
				rcp1.setRemark(remark.getText());
				rcp1.setCreated_by(loginDt.getLogin_id());
				rcp1.setCreated_date(new Date());
				rcp1.setFin_year(loginDt.getFin_year());
				rcp1.setWeight((setDoubleNumber(weight.getText())));
				rcp1.setCategory(name1.getText());
				rcp1.setModified_by(loginDt.getLogin_id());
				rcp1.setModified_date(new Date());
				
					
			 
			 
		} catch (Exception e) {
			System.out.println("Error in vouUpdate "+e);
		}
		
		
		return rcp1;

	}


	/// Method for voucher Printing 
	public ArrayList vouPrint() 
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		RcpDto rcp1 = null;
		ArrayList bankList = new ArrayList();
		try {

				 
				rcp1 = new RcpDto();
				
				rcp1.setVdepo_code(loginDt.getDepo_code());
				rcp1.setVou_no(setIntNumber(vou_no.getText()));
				rcp1.setVou_lo(loginDt.getInv_lo()+"RA");
				rcp1.setVou_date(sdf.parse(vou_date.getText()));
				rcp1.setVac_code(vac_code.getText());
				rcp1.setParty_name(name.getText());
				rcp1.setChq_no(group.getText());
				rcp1.setVnart2(vnart_1.getText());
				try
				{
					if (!chq_date.getText().equals("__/__/____"))
						rcp1.setChq_date(sdf.parse(chq_date.getText())); 
					} catch (ParseException e) {
						rcp1.setChq_date(null);
					e.printStackTrace();
				}
				 
//				rcp1.setnet_amt(setDoubleNumber(net_amt.getText()));
				rcp1.setVamount(setDoubleNumber(net_amt.getText()));
				rcp1.setExp_code(vac_code.getText());
				
				bankList.add(rcp1);

 
			 
			 
		} catch (Exception e) {
			System.out.println("Error in vouPrint "+e);
		}
		
		
		return bankList;

	}		
 	


	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		if(e.getActionCommand().equalsIgnoreCase("add"))
		{
			option=1;
			btnAdd.setEnabled(false);
			btnDelete.setEnabled(false);
			btnMiss.setEnabled(false);
			btnPrint.setEnabled(false);
			clearAll(); 
			invNoTable.setEnabled(false);
			vno.setEnabled(false);
			vdate.setEnabled(false);
			addBool=true;
			
			vou_no.requestFocus();
			vou_no.setSelectionStart(0);

		
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
			//ArrayList vlist = vouPrint();
			

			if(e.getActionCommand().equalsIgnoreCase("print"))
			{
/*				int vbookCD = 95;

				int vouNo=setIntNumber(vou_no.getText());

					CashOpt copt = new CashOpt("Bank","Debit Note Voucher Print",vouNo,vbookCD,("D"));
					copt.setVisible(true);
*/					
				int vouNo=setIntNumber(vou_no.getText());
					
					new GenerateInvoiceGST(String.valueOf(loginDt.getFin_year()),
							String.valueOf(loginDt.getDepo_code()),
							String.valueOf(loginDt.getDiv_code()),
							String.valueOf(vouNo), String.valueOf(vouNo), "View",
							loginDt.getDrvnm(), loginDt.getPrinternm(), "2",
							loginDt.getMnth_code(), 60, loginDt.getBrnnm(),
							loginDt.getDivnm(), null, 0);

					
			}
			
		}

		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			int ino = saveData();
			int vouNo=setIntNumber(vou_no.getText());

			if(ino!=0)
			{
				JOptionPane.showMessageDialog(InwardEntryOld.this,"Slip No "+vouNo,"Slip No",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		if(e.getActionCommand().equalsIgnoreCase("delete"))
		{
			 int h=0;
			 int answer = 0;
			 
			  answer = JOptionPane.showConfirmDialog(InwardEntryOld.this, "Are You Sure : ");
			    if (answer == JOptionPane.YES_OPTION) {
			      // User clicked YES.
//			    	h = bankDao.deleteLed(loginDt.getDiv_code(),loginDt.getDepo_code(),setIntNumber(vou_no.getText()),loginDt.getFin_year(),loginDt.getLogin_id(),95);

			    	btnAdd.setEnabled(true);
			    	btnMiss.setEnabled(true);
			    	btnPrint.setEnabled(false);
			    	btnDelete.setEnabled(false);
			    	
					invNoTable.setEnabled(true);
					vno.setEnabled(true);
					vdate.setEnabled(true);
					option=0;
					clearAll();
			    } else if (answer == JOptionPane.NO_OPTION) {
			      // User clicked NO.
			    }
  	    	
			
				
		}
		
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			option=0;
			btnAdd.setEnabled(true);
			btnMiss.setEnabled(true);
			btnDelete.setEnabled(false);
			btnPrint.setEnabled(false);
			invNoTable.setEnabled(true);
			vou_no.setEditable(true); 
			vno.setEnabled(true);
			vdate.setEnabled(true);
			clearAll();
			dispose();
		}

		
		
	}


	public int saveData() 
	{
		int vouNo=0;
		
		if ((option==1 || option==2))
		{
			vouNo= idao.addFstRecordOld(vouAdd());
			 
		}

		if (option==0)
		{
			vouNo= idao.updateFstRecordOld(vouAdd());
			 
		}

		
		
/*		if ((option==1 || option==2))
		{
			vouNo= bankDao.addDebitNote(vouAdd());
			 
		}
		
		if (option==0)
		{
			bankDao.updateDebitNote(vouUpdate());
			
		}
*/
		btnAdd.setEnabled(true);
		btnMiss.setEnabled(true);
		btnDelete.setEnabled(false);
		btnPrint.setEnabled(false);
		vou_no.setEditable(false);
		invNoTable.setEnabled(true);
		vno.setEnabled(true);
		vdate.setEnabled(true);
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
		vnart_1.setText(rcp.getMac_name_hindi()+","+rcp.getMcity_hindi());
		name1.setText(rcp.getCategory());
		name1_hindi.setText(rcp.getCategory_hindi());
		group.setText(rcp.getGroup_name());
		group_hindi.setText(rcp.getGroup_name_hindi());
		pname.setText(rcp.getAproval_no());
		pname_hindi.setText(rcp.getPname_hindi());
		quantity.setText(formatter.format(rcp.getSqty()));
		weight.setText(formatter.format(rcp.getWeight()));
		rate.setText(formatter.format(rcp.getSrate_net()));
		rent_amt.setText(formatter.format(rcp.getSamnt()));
		roundoff.setText(formatter.format(rcp.getRound_off()));
		net_amt.setText(formatter.format(rcp.getNet_amt()));
		remark.setText(rcp.getRemark());
		hintTextLabel.setVisible(false);
		hintTextLabelcat.setVisible(false);
		hintTextLabelgrp.setVisible(false);
		hintTextLabelprd.setVisible(false);
		pcode=rcp.getSprd_cd();
		groupcd=rcp.getSpd_gp();
//		int idx=rcp.getInv_no()-1;
		btnAdd.setEnabled(false);
		btnMiss.setEnabled(false);
		btnPrint.setEnabled(true);
		btnDelete.setEnabled(true);
//		tot=rcp.getVamount();
	 
		
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
	
	public int getLastNo()
	{
		int vno=0;
		vno=idao.getInvoiceNo(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(), 60);
		return vno;
	}
}


