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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
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
import com.coldstorage.dto.TransportDto;
import com.coldstorage.util.OverWriteTableCellEditor;

public class WrittenOffEntryOld extends BaseClass implements ActionListener {


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
	private JLabel hintTextLabel;
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
	NumberFormat formatter,numderformatter;
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

	private JTextField chl_no;

	private JLabel lblchlDate;

	private JFormattedTextField chl_date;

	private JLabel lblKatte;

	private JTextField katte;

	private JLabel lblwajan;

	private JTextField weightout;

	private JLabel lblReceiver;

	private JTextField receiver_name;

	private JLabel lblMobile;

	private JTextField mobile_no;

	private JLabel lblVehicle;

	private JTextField vehicle_no;

	private JLabel lblChlNo;

	private JLabel lblRent;

	private JLabel rentamt;

	public WrittenOffEntryOld()
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
		setSize(1024, 670);		
	       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		        
	        this.setUndecorated(true);
	        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

	        arisleb.setVisible(false);
		
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


		
		
		partyList = new JList();
		//partyList.setFont(font);
		partyList.setSelectedIndex(0);
		partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Partypane = new JScrollPane(partyList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Partypane.setBounds(504, 153, 286, 259);
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
					vac_code.requestFocus();
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

					 
					// evt.consume();
					Partypane.setVisible(false);
					// inv_date.requestFocus();
					vac_code.requestFocus();
				}
				
			}
		});

		
		// ////////////////////////////////////////////////////////////////////
//		String[] crDrColName = { "Marka No", "Qty", "Manzil", "Gala No"};
		String[] crDrColName = { "Slip No","Date","Category","Group","Item","Pack","Balance Qty", "Balance Weight","Marka No", "WrittenOff Qty", "Writtenoff Weight", "","","","","","",""};
		String cdDrData[][] = {{}};
		CrTableModel = new DefaultTableModel(cdDrData, crDrColName) {
			private static final long serialVersionUID = 1L;
		
			public boolean isCellEditable(int row, int column) 
			{

				boolean ans=false;
				if(column==9 || column==10 )
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
		cddrPane.setBounds(40, 245, 975, 230);
		cddrPane.setBorder(new LineBorder(new Color(0, 0, 0), 3));
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
		lblLastNo = new JLabel("Last Outward No:");
		lblLastNo.setForeground(Color.RED);
		lblLastNo.setBounds(800, 131, 150, 20);
		getContentPane().add(lblLastNo);
		
		lastNo = new JLabel("");
		lastNo.setForeground(Color.RED);
		lastNo.setBounds(950, 131, 51, 20);
		getContentPane().add(lastNo);
		lastNo.setText(String.valueOf(getLastNo()));


		lblRent = new JLabel("Rent Due:");
		lblRent.setForeground(Color.BLUE);
		lblRent.setBounds(800, 199, 150, 20);
		getContentPane().add(lblRent);
		
		rentamt = new JLabel("");
		rentamt.setForeground(Color.BLUE);
		rentamt.setBounds(895, 199, 100, 20);
		getContentPane().add(rentamt);
		

		
		lblvouno = new JLabel("Slip Number:");
		lblvouno.setBounds(282, 131, 126, 20);
		lblvouno.setVisible(false);
		getContentPane().add(lblvouno);

		vou_no = new JTextField();
		vou_no.setVisible(false);
		vou_no.setBounds(408, 131,132, 23);
		getContentPane().add(vou_no);
		



		lblvoudate = new JLabel("Date:");
		lblvoudate.setBounds(282, 162, 126, 20);
		lblvoudate.setVisible(false);
		getContentPane().add(lblvoudate);



		vou_date = new JFormattedTextField(sdf);
		vou_date.setBounds(408, 162, 132, 23);
		checkDate(vou_date);
		vou_date.setText(sdf.format(new Date()));
		vou_date.setVisible(false);
		getContentPane().add(vou_date);


/*		branch = new JLabel(loginDt.getBrnnm());
		branch.setForeground(Color.BLACK);
		branch.setFont(new Font("Tahoma", Font.BOLD, 22));
		branch.setBounds(279, 66, 560, 24);
		getContentPane().add(branch);
*/


		lblDispatchEntry = new JLabel("WrittenOff Entry");
		lblDispatchEntry.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDispatchEntry.setBounds(331, 94, 382, 22);
		getContentPane().add(lblDispatchEntry);


		
		

		// ////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////
	

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
						chl_date.requestFocus();
						chl_date.setSelectionStart(0);

						break;

					case 1:
						String date=chl_date.getText();
						if(isValidDate(date))
						{
							//lblinvalid.setVisible(false);
							// Check the Date Range for the Financial year
							if (isValidRange(date,loginDt.getFr_date(),loginDt.getTo_date()))
							{
								cddrPane.requestFocus();
								CrDrTable.requestFocus();
								CrDrTable.changeSelection(0, 9, false, false);
								CrDrTable.editCellAt(0, 9);
							}
							else
							{
								JOptionPane.showMessageDialog(WrittenOffEntryOld.this,"Date range should be in between: "
								+ sdf.format(loginDt.getFr_date())+ " to " + sdf.format(loginDt.getTo_date()),
								"Date Range",	JOptionPane.INFORMATION_MESSAGE);
								chl_date.setValue(null);
								checkDate(chl_date);
								chl_date.requestFocus();

							}


						}
						else
						{
							chl_date.setValue(null);
							checkDate(chl_date);
							chl_date.requestFocus();
						}
						break;

					case 2:
						break;
					case 3:
						receiver_name.requestFocus();
						receiver_name.setSelectionStart(0);
						break;
					case 4:
						mobile_no.requestFocus();
						mobile_no.setSelectionStart(0);
						break;
					case 5:
						vehicle_no.requestFocus();   //rate
						vehicle_no.setSelectionStart(0);
						break;
					case 6:
						 remark.requestFocus(); 						
						 remark.setSelectionStart(0);
						break;
					case 7:
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
		

		
		lblvaccode = new JLabel("Vendor Code:");
		lblvaccode.setBounds(282, 131, 126, 20);
		getContentPane().add(lblvaccode);

		vac_code = new JTextField();
		vac_code.setBounds(408, 131, 86, 23);
		getContentPane().add(vac_code);
		vac_code.addKeyListener(new KeyAdapter() 
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
					int party = setIntNumber(vac_code.getText().toString());
					
					if (vac_code.getText().toString().trim().equals("")) {
						Partypane.setVisible(true);
						name.requestFocus();
					} else 
					{
						hintTextLabel.setVisible(false);
						 party = setIntNumber(vac_code.getText().toString().trim());
						
						prtyDto = (TransportDto) partyMap.get(vac_code.getText().toString().trim());

						if (prtyDto == null)
						{
							Partypane.setVisible(true);
							partyList.requestFocus();
							partyList.setSelectedIndex(0);
						} else {

							rcp =  idao.getInvDetailParty(party,"",loginDt.getFin_year(),1);
						}
					if(rcp==null)
					{
						clearAll();
						JOptionPane.showMessageDialog(WrittenOffEntryOld.this,"No Record for Khata No. "+party,"Record not found",JOptionPane.INFORMATION_MESSAGE);						
						
					} 
					else
					{
						fillData(rcp);
						fillItemData(rcp);


						chl_no.requestFocus();
						chl_no.setSelectionStart(0);
						option=1;


						
					}
					}
					evt.consume();
				}
				
			}
		});
		


		
		lblNarration = new JLabel("Vendor In Hindi:");
		lblNarration.setBounds(282, 165, 126, 20);
		getContentPane().add(lblNarration);
		
		vnart_1 = new JTextField();
		vnart_1.setBounds(408, 165, 382, 23);
		vnart_1.setFont(fontHindi);
		getContentPane().add(vnart_1);
		
		
		
		
		hintTextLabel = new JLabel("Search by Party Name");
		hintTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		hintTextLabel.setForeground(Color.GRAY);
		hintTextLabel.setBounds(506, 131, 231, 22);
		getContentPane().add(hintTextLabel);

		
		name = new JTextField();
		name.setName("2");
		name.setBounds(504, 131, 286, 23);
		getContentPane().add(name);
		name.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				// TODO Auto-generated method stub

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if(name.getText().length()==0)
						alertMessage(WrittenOffEntryOld.this, "Please select Party!");
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

		
		

		lblChlNo = new JLabel("WrittenOff No:");
		lblChlNo.setBounds(282, 199, 130, 25);
		getContentPane().add(lblChlNo);

		
		chl_no = new JTextField();
		chl_no.setHorizontalAlignment(SwingConstants.RIGHT);
		chl_no.setBounds(408, 199, 125, 23);
		getContentPane().add(chl_no);
		chl_no.addKeyListener(new KeyAdapter() 
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
					int vouno = setIntNumber(chl_no.getText().toString());
					 
					if (vouno==0)
					{
						JOptionPane.showMessageDialog(WrittenOffEntryOld.this,"Challan No. "+vouno,"Empty!!!!",JOptionPane.INFORMATION_MESSAGE);						
						chl_no.setText("");
						chl_no.requestFocus();
					}
					else
					{						
						rcp =  idao.getChallanDetail(chl_no.getText(),loginDt.getFin_year(),40,loginDt.getDepo_code(),loginDt.getDiv_code());

						System.out.println("option ki value "+option);
						if(rcp!=null)
						{
							if(option==1)
							{
							JOptionPane.showMessageDialog(WrittenOffEntryOld.this,"Challan No. "+vouno,"Already Exists!!!!!",JOptionPane.INFORMATION_MESSAGE);						
							chl_no.setText("");
							chl_no.requestFocus();
							}
							else
							{
								chl_no.setEditable(false);
								lblLastNo.setVisible(true);
								lastNo.setVisible(true);
								fillData(rcp);
								chl_date.requestFocus();
								btnPrint.setEnabled(true);
							}
						} 
						else if(option==1) 
							chl_date.requestFocus();
						else
						{
							JOptionPane.showMessageDialog(WrittenOffEntryOld.this,"Challan No."+billNo," Not Found!!!!!",JOptionPane.INFORMATION_MESSAGE);						
							chl_no.setText("");
							chl_no.requestFocus();
						}
						evt.consume();
					}
				}
				
			}
		});


		lblchlDate = new JLabel("Date:");
		lblchlDate.setBounds(540, 199, 70, 20);
		getContentPane().add(lblchlDate);


		chl_date = new JFormattedTextField(sdf);
		chl_date.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkDate(chl_date);
		chl_date.setBounds(604, 199, 185, 22);
		getContentPane().add(chl_date);

		
		lblReceiver = new JLabel("Receiver Name:");
		lblReceiver.setBounds(282, 495, 126, 20);
		getContentPane().add(lblReceiver);

		
		receiver_name = new JTextField();
		receiver_name.setBounds(408, 495, 382, 23);
		receiver_name.setFont(fontHindi);
		getContentPane().add(receiver_name);

		lblMobile = new JLabel("Mobile No:");
		lblMobile.setBounds(282, 524, 126, 20);
		getContentPane().add(lblMobile);

		
		mobile_no = new JTextField();
		mobile_no.setBounds(408, 524, 382, 23);
		getContentPane().add(mobile_no);

		lblVehicle = new JLabel("Vehicle No:");
		lblVehicle.setBounds(282, 553, 126, 20);
		getContentPane().add(lblVehicle);

		
		vehicle_no = new JTextField();
		vehicle_no.setBounds(408, 553, 382, 23);
		getContentPane().add(vehicle_no);

		lblRemark = new JLabel("Remark:");
		lblRemark.setBounds(282, 582, 126, 20);
		getContentPane().add(lblRemark);

		
		remark = new JTextField();
		remark.setBounds(408, 582, 382, 23);
		remark.setFont(fontHindi);
		getContentPane().add(remark);
		
		
		chl_no.setName("0");
		chl_date.setName("1");
//		katte.setName("2");
//		weightout.setName("3");
		receiver_name.setName("4");
		mobile_no.setName("5");
		vehicle_no.setName("6");
		remark.setName("7");

		chl_date.addKeyListener(keyListener);
//		katte.addKeyListener(keyListener);
//		weightout.addKeyListener(keyListener);
		receiver_name.addKeyListener(keyListener);
		mobile_no.addKeyListener(keyListener);
		vehicle_no.addKeyListener(keyListener);
		remark.addKeyListener(keyListener);
		

		
		
		
		

		final KeyListener crTableListener = new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				int column = CrDrTable.getSelectedColumn();
				int row = CrDrTable.getSelectedRow();
				int totRow=CrDrTable.getRowCount();
				
				double weight=0.00;
				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (column == 9)
					{
							CrDrTable.changeSelection(row, 10, false, false);
							CrDrTable.editCellAt(row, 10);
							sqty = setIntNumber(CrDrTable.getValueAt(row, 6).toString());
							rqty = setIntNumber(CrDrTable.getValueAt(row, 9).toString());
							if(rqty>sqty)
							{
								alertMessage(WrittenOffEntryOld.this, "Quantity should not be greater than "+sqty);
								CrDrTable.setValueAt("", row, 9);
								CrDrTable.changeSelection(row, 9, false, false);
								CrDrTable.editCellAt(row, 9);
								
							}
							else
							{
								weight=rqty*setDoubleNumber(CrDrTable.getValueAt(row, 17).toString());

								CrDrTable.changeSelection(row, 11, false, false);
								CrDrTable.editCellAt(row, 11);
								CrDrTable.setValueAt(weight, row, 10);
								CrDrTable.changeSelection(row, 10, false, false);
								CrDrTable.editCellAt(row, 10);

								
							}

					} 
					if (column == 10)
					{
							CrDrTable.changeSelection(row, 11, false, false);
							CrDrTable.editCellAt(row, 11);
							if(row+1==totRow)
							{

								double sweight = setDoubleNumber(CrDrTable.getValueAt(row, 7).toString());
								double rweight = setDoubleNumber(CrDrTable.getValueAt(row, 10).toString());
								if(rweight>sweight)
								{
									alertMessage(WrittenOffEntryOld.this, "Weight should not be greater than "+sweight);
									CrDrTable.setValueAt("", row, 10);
									CrDrTable.changeSelection(row, 10, false, false);
									CrDrTable.editCellAt(row, 10);
									
								}
								else
								{
									receiver_name.requestFocus();
									receiver_name.setSelectionStart(0);
								}
							}
							else
							{
								double sweight = setDoubleNumber(CrDrTable.getValueAt(row, 7).toString());
								double rweight = setDoubleNumber(CrDrTable.getValueAt(row, 10).toString());
								if(rweight>sweight)
								{
									alertMessage(WrittenOffEntryOld.this, "Weight should not be greater than "+sweight);
									CrDrTable.setValueAt("", row, 10);
									CrDrTable.changeSelection(row, 10, false, false);
									CrDrTable.editCellAt(row, 10);
									
								}
								else
								{
									CrDrTable.changeSelection(row+1, 9, false, false);
									CrDrTable.editCellAt(row+1, 9);
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

		CrDrTable.addMouseListener(new MouseListener() {
			
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
				if (column == 9)
				{
							CrDrTable.changeSelection(row, 9, false, false);
							CrDrTable.editCellAt(row, 9);
							sqty = setIntNumber(CrDrTable.getValueAt(row, 6).toString());
							rqty = setIntNumber(CrDrTable.getValueAt(row, 9).toString());
							if(rqty>sqty)
							{
								alertMessage(WrittenOffEntryOld.this, "Quantity should not be greater than "+sqty);
								CrDrTable.setValueAt("", row, 9);
								CrDrTable.changeSelection(row, 9, false, false);
								CrDrTable.editCellAt(row, 9);
								
							}
							else
							{
								weight=rqty*setDoubleNumber(CrDrTable.getValueAt(row, 17).toString());

								CrDrTable.changeSelection(row, 11, false, false);
								CrDrTable.editCellAt(row, 11);
								CrDrTable.setValueAt(weight, row, 10);
								CrDrTable.changeSelection(row, 10, false, false);
								CrDrTable.editCellAt(row, 10);

								
							}

				} 
				if (column == 10)
				{
					CrDrTable.changeSelection(row, 10, false, false);
					CrDrTable.editCellAt(row, 10);
					if(row+1==totRow)
					{
/*						CrDrTable.changeSelection(row, 11, false, false);
						CrDrTable.editCellAt(row, 11);
*/
						double sweight = setDoubleNumber(CrDrTable.getValueAt(row, 7).toString());
						double rweight = setDoubleNumber(CrDrTable.getValueAt(row, 10).toString());
						if(rweight>sweight)
						{
							alertMessage(WrittenOffEntryOld.this, "Weight should not be greater than "+sweight);
							CrDrTable.setValueAt("", row, 10);
							CrDrTable.changeSelection(row, 10, false, false);
							CrDrTable.editCellAt(row, 10);
							
						}
						else
						{
							receiver_name.requestFocus();
							receiver_name.setSelectionStart(0);
						}
					}
					else
					{
						CrDrTable.changeSelection(row, 10, false, false);
						CrDrTable.editCellAt(row, 10);
						double sweight = setDoubleNumber(CrDrTable.getValueAt(row, 7).toString());
						double rweight = setDoubleNumber(CrDrTable.getValueAt(row, 10).toString());
						if(rweight>sweight)
						{
							alertMessage(WrittenOffEntryOld.this, "Weight should not be greater than "+sweight);
							CrDrTable.setValueAt("", row, 10);
							CrDrTable.changeSelection(row, 10, false, false);
							CrDrTable.editCellAt(row, 10);
							
						}
						else
						{
/*							CrDrTable.changeSelection(row+1, 9, false, false);
							CrDrTable.editCellAt(row+1, 9);
*/						}
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
		btnSave.setBounds(834, 605, 86, 30);
		getContentPane().add(btnSave);

		btnExit = new JButton("Exit");
		btnExit.setActionCommand("Exit");
		btnExit.setBounds(926, 605, 86, 30);
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
		getContentPane().add(panel_1);
		

		btnSave.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					
					int ino = saveData();
					if(ino!=0)
					{
						JOptionPane.showMessageDialog(WrittenOffEntryOld.this,"WrittenOff No "+billNo,"WrittenOff No",JOptionPane.INFORMATION_MESSAGE);
					}
					vac_code.requestFocus();
					vac_code.setSelectionStart(0);
					lastNo.setText(String.valueOf(getLastNo()));
					
				}
			}
		});

	}


	public void clearAll()
	{

		vou_no.setText("");
		vac_code.setText("");
		name.setText("");
		vnart_1.setText("");
		chl_no.setText("");
		chl_date.setValue(null);
		checkDate(chl_date);
		receiver_name.setText("");
		mobile_no.setText("");
		vehicle_no.setText("");
		remark.setText("");


		bno="";
		invNoTableModel.getDataVector().removeAllElements();
		
		CrTableModel.getDataVector().clear();
		CrTableModel.addRow(new Object[] {"","","","","","","","","","","",""});


		 btnSave.setBackground(null);


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
		Vector vouData = CrTableModel.getDataVector();
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
					qty=setIntNumber(col.get(9).toString().trim());
				} catch (Exception e) {
					qty=0;}
				
				if(qty>0)
				{
					
					
					
					rcp1 = new InvViewDto();
					
					billNo=setIntNumber(chl_no.getText());
					rcp1.setDepo_code(loginDt.getDepo_code());
					rcp1.setDoc_type(66);
					rcp1.setSinv_no(setIntNumber(col.get(0).toString()));
					
					try {
						rcp1.setInv_date(sdf.parse(col.get(1).toString()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						rcp1.setInv_date(null);
					}
					rcp1.setMac_code(vac_code.getText());
					rcp1.setFin_year(loginDt.getFin_year());
					rcp1.setCreated_by(loginDt.getLogin_id());
					rcp1.setCreated_date(new Date());
					
					
					rcp1.setRemark(remark.getText());
					rcp1.setVehicle_no(vehicle_no.getText());
					
					rcp1.setChl_no(chl_no.getText());
					rcp1.setChl_date(sdf.parse(chl_date.getText()));
					rcp1.setReceiver_name(receiver_name.getText());
					rcp1.setMobile((mobile_no.getText()));
					rcp1.setCategory_hindi(col.get(2).toString());
					rcp1.setPack(col.get(5).toString());
					rcp1.setSqty(setIntNumber(col.get(6).toString()));
					rcp1.setWeight(setDoubleNumber(col.get(7).toString()));
					rcp1.setMark_no((col.get(8).toString()));
					rcp1.setIssue_qty(setIntNumber(col.get(9).toString()));
					rcp1.setIssue_weight(setDoubleNumber(col.get(10).toString()));
					rcp1.setVehicle_no(vehicle_no.getText());
					rcp1.setRemark(remark.getText());
					rcp1.setSpd_gp(setIntNumber(col.get(11).toString()));
					rcp1.setSerialno(setIntNumber(col.get(12).toString()));
					rcp1.setSprd_cd(setIntNumber(col.get(13).toString()));
					rcp1.setCategory(col.get(14).toString());

					
					dataList.add(rcp1);
				} // end of if(Exp code zero nahi hona chahiye)
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in vouAdd " + e);
		}

		return dataList;

	}
	 
 

	public InvViewDto vouAddOLD() 
	{
		 
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		 
		InvViewDto rcp1 = null;
		try {
			 

				 
				rcp1 = new InvViewDto();
				rcp1.setDepo_code(loginDt.getDepo_code());
				rcp1.setDoc_type(66);
				rcp1.setSinv_no(setIntNumber(vou_no.getText()));
				rcp1.setFin_year(loginDt.getFin_year());
				rcp1.setModified_by(loginDt.getLogin_id());
				rcp1.setModified_date(new Date());
				rcp1.setSqty(setDoubleNumber(quantity.getText()));
				rcp1.setChl_no(chl_no.getText());
				rcp1.setChl_date(sdf.parse(chl_date.getText()));
				rcp1.setReceiver_name(receiver_name.getText());
				rcp1.setMobile((mobile_no.getText()));
				rcp1.setIssue_qty(setDoubleNumber(katte.getText()));
				rcp1.setIssue_weight(setDoubleNumber(weightout.getText()));
				rcp1.setVehicle_no(vehicle_no.getText());
				rcp1.setRemark(remark.getText());


				
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

			if(ino!=0)
			{
				JOptionPane.showMessageDialog(WrittenOffEntryOld.this,"WrittenOff No "+billNo,"WrittenOff No",JOptionPane.INFORMATION_MESSAGE);
			}
			vac_code.requestFocus();
			vac_code.setSelectionStart(0);
			lastNo.setText(String.valueOf(getLastNo()));

		}
		
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			option=0;
	//		invNoTable.setEnabled(true);
			vac_code.setEditable(true); 
			vac_code.setEnabled(true);
			clearAll();
			dispose();
		}

		
		
	}


	public int saveData() 
	{
		int recordNo=0;
		
		if ((option==1 || option==2))
		{
			
			ArrayList l = vouAdd();
			if (l != null)
				recordNo = idao.addFstOutwardRecord(l);


			 
		}

/*		if (option==0)
		{
			ArrayList l = vouAdd();
			if (l != null)
				recordNo = idao.updateFstRecord(l);
			 
		}
*/
		
//		btnPrint.setEnabled(false);
		vou_no.setEditable(true);
		option=1;
		clearAll();
		return recordNo;
	}
	
	 

	public void fillData(InvViewDto rcp)
	{
		// voucher header
//		vou_no.setText(String.valueOf(rcp.getInv_no()));
//		vou_date.setText(sdf.format(rcp.getInv_date()));
		vac_code.setText(rcp.getMac_code());
		name.setText((rcp.getMac_name()+","+rcp.getMcity()));
		vnart_1.setText(rcp.getMac_name_hindi()+","+rcp.getMcity_hindi());
/*		name1.setText(rcp.getCategory());
		name1_hindi.setText(rcp.getCategory_hindi());
		group.setText(rcp.getGroup_name());
		group_hindi.setText(rcp.getGroup_name_hindi());
		pname.setText(rcp.getAproval_no());
		pname_hindi.setText(rcp.getPname_hindi());
		quantity.setText(formatter.format(rcp.getSqty()));
		weight.setText(formatter.format(rcp.getWeight()));
*/		pcode=rcp.getSprd_cd();
		groupcd=rcp.getSpd_gp();
		sqty=rcp.getSqty();
		cddrPane.requestFocus();
		CrDrTable.requestFocus();
		billNo=setIntNumber((rcp.getInv_no()));
		rentamt.setText(formatter.format(rcp.getCn_val()));

		
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
				
				
				if (setIntNumber(col.get(9).toString().trim())!=0 ) {
					 
					if(first)
					{
						markaList = new ArrayList();
						first=false;
					}
					trd = new InvViewDto();
					

					trd.setDepo_code(loginDt.getDepo_code());
					trd.setDoc_type(66);
					trd.setSinv_no(setIntNumber(vou_no.getText()));
					trd.setInv_date(sdf.parse(vou_date.getText()));
					trd.setMac_code(vac_code.getText());
					trd.setFin_year(loginDt.getFin_year());
					trd.setModified_by(loginDt.getLogin_id());
					trd.setModified_date(new Date());
					trd.setChl_no(chl_no.getText());
					trd.setChl_date(sdf.parse(chl_date.getText()));
					trd.setReceiver_name(receiver_name.getText());
					trd.setMobile((mobile_no.getText()));
					trd.setCategory_hindi(col.get(2).toString());
					trd.setPack(col.get(5).toString());
					trd.setSqty(setIntNumber(col.get(6).toString()));
					trd.setWeight(setDoubleNumber(col.get(7).toString()));
					trd.setMark_no((col.get(8).toString()));
					trd.setIssue_qty(setIntNumber(col.get(9).toString()));
					trd.setIssue_weight(setDoubleNumber(col.get(10).toString()));
					trd.setVehicle_no(vehicle_no.getText());
					trd.setRemark(remark.getText());
					trd.setSpd_gp(setIntNumber(col.get(11).toString()));
					trd.setSerialno(setIntNumber(col.get(12).toString()));
					trd.setSprd_cd(setIntNumber(col.get(13).toString()));
					markaList.add(trd);

				}

			}
			 
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

		CrDrTable.getColumnModel().getColumn(0).setPreferredWidth(50); // slip no
		CrDrTable.getColumnModel().getColumn(1).setPreferredWidth(50); // date
		CrDrTable.getColumnModel().getColumn(2).setPreferredWidth(30); // CATEGORY
		CrDrTable.getColumnModel().getColumn(3).setPreferredWidth(30); // GROUP
		CrDrTable.getColumnModel().getColumn(4).setPreferredWidth(60); // ITEM
		CrDrTable.getColumnModel().getColumn(5).setPreferredWidth(30); // PACK
		CrDrTable.getColumnModel().getColumn(6).setPreferredWidth(60); // balance qty
		CrDrTable.getColumnModel().getColumn(7).setPreferredWidth(70); // balance weight
		CrDrTable.getColumnModel().getColumn(8).setPreferredWidth(50); // marka
		CrDrTable.getColumnModel().getColumn(9).setPreferredWidth(50); // qty
		CrDrTable.getColumnModel().getColumn(10).setPreferredWidth(70); // weight


		
		CrDrTable.getColumnModel().getColumn(11).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(11).setMinWidth(0); // sprd_cd
		CrDrTable.getColumnModel().getColumn(11).setMaxWidth(0);

		
		CrDrTable.getColumnModel().getColumn(12).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(12).setMinWidth(0); // sprd_cd
		CrDrTable.getColumnModel().getColumn(12).setMaxWidth(0);


		CrDrTable.getColumnModel().getColumn(13).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(13).setMinWidth(0); // tag
		CrDrTable.getColumnModel().getColumn(13).setMaxWidth(0);


		CrDrTable.getColumnModel().getColumn(14).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(14).setMinWidth(0); // tag
		CrDrTable.getColumnModel().getColumn(14).setMaxWidth(0);

		CrDrTable.getColumnModel().getColumn(15).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(15).setMinWidth(0); // rate
		CrDrTable.getColumnModel().getColumn(15).setMaxWidth(0);

		CrDrTable.getColumnModel().getColumn(16).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(16).setMinWidth(0); // rst
		CrDrTable.getColumnModel().getColumn(16).setMaxWidth(0);
		
		CrDrTable.getColumnModel().getColumn(17).setPreferredWidth(0);
		CrDrTable.getColumnModel().getColumn(17).setMinWidth(0); // weight per qty
		CrDrTable.getColumnModel().getColumn(17).setMaxWidth(0);
		
		CrDrTable.setDefaultEditor(Double.class, new OverWriteTableCellEditor());
		CrDrTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());


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
		vno=idao.getInvoiceNo(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(), 66);
		return vno;
	}


}


