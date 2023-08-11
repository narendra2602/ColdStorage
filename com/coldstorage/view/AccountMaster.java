package com.coldstorage.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.coldstorage.dao.PartyDAO;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.StateDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.print.PartyPrint;
import com.coldstorage.util.JDoubleField;
import com.coldstorage.util.JIntegerField;
import com.coldstorage.util.TableDataSorter;
import com.coldstorage.util.TextField;

public class AccountMaster extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private JButton exitButton;
	private JButton btnSave,btnAdd,btnExcel;
	private JTextField mgrp_code;
	private JLabel lblGroupCode;
	private JLabel lblAcCode;
	private JTextField mac_code;
	private JLabel lblName;
	private JLabel lblAddress;
	private JTextField madd1;
	private JTextField mac_name;
	private JTextField mobile;
	private JLabel lblMobileNo;
	private JTextField mphone;
	private JLabel lblPhone;
	private JLabel lblCity;
	private JComboBox state_name,transporter;
	private JTextField gstate;
	private JLabel lblAddress_1;
	private JTextField madd2;
	private JTextField mcity;
	private JTextField mbank_add2;
	private JLabel lblBankAddress;
	private JTextField mbanker,ifsc_code;
	private JLabel lblBank,lblIFSC;
	private JDoubleField distance;
	private JLabel lblEmail;
	private JTextField memail;
	private JLabel lblTransporter;
	private JLabel lblGST,lblTaxtpe;
	private JLabel lblPanNumber,lblDistance;
	private JTextField pan_no;
	private JTextField gst_no;
	private JTextField mtranspt;
	private JTextField mbank_add1;
	private JLabel lblType;
	private JComboBox ptype,crdr;
	private JLabel lblLock;
	private JComboBox mtype1,tax_type;
	private JLabel lblPinCode;
	private JLabel lblContactPerson;
	private JTextField mcontct;
	private JLabel lblDueDays;
	private JLabel lblDiscount;
	private JIntegerField mdays;
	private JDoubleField mfix_disc1;
	private JLabel lblOpeningBalance;
	private JDoubleField mopng_bal;
	private JLabel lblCrdr;
	private JTextField mopng_db;
	private JTextField mpin;
	private JTextField madd3;
	private JPanel panel_3;
	private JLabel lblAccountBalance;
	private JTextField mgrp_name;
	private JPanel panel_4;
	private JPanel panel_5;
	private JLabel lblName_1;
	private JFormattedTextField party_name;
	private JScrollPane scrollPane;
	private JPanel panel_6;
	private JLabel lblSearch;
	private JTable partyTable;
	private DefaultTableModel partyTableModel;
	private Font fontPlan;
	private JScrollPane  headPane,msrPane ;
	private JList headList,msrList;
	PartyDAO prtdao;
	PartyDto pdto;
	NumberFormat formatter;
	SimpleDateFormat sdf;
	String option=null;
	private JTextField mstate_name;
	TableModel myTableModel;
	final TableRowSorter<TableModel> sorter;
	StateDto gststate;
	TransportDto tdto;
	boolean dataedit=true;
	boolean adddata=false;
	public AccountMaster()
	{
//		infoName =(String) helpImg.get(getClass().getName());
		
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Account Master");
		getContentPane().setLayout(null);

		option="";
		formatter = new DecimalFormat("0.00");     // Decimal Value format
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		prtdao = new PartyDAO();
		
		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(400, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);

		fontPlan =new Font("Tahoma", Font.PLAIN, 11);
		
		
	
	 
		// ////////////////////////////////////////////////////////////////		
		
		// head list ////////////////////////////////////////////////////////
		headList = new JList(loginDt.getHeadList());
		headList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		headList.setSelectedIndex(0);
		getContentPane().add(headList);
		headPane = new JScrollPane(headList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		headPane.setBounds(485, 146, 250, 300);
		getContentPane().add(headPane);
		headPane.setVisible(false);
		headList.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{

					
					headPane.setVisible(false);
					headList.setVisible(false);
					 
					 
					GroupDto gp = (GroupDto) headList.getSelectedValue();
					mgrp_name.setText(gp.getGp_name());
					mgrp_code.setText(String.valueOf(gp.getGp_code()));
					if(gp.getGp_code()<45 || gp.getGp_code()>50 )
					{
						gstate.setText(String.valueOf(loginDt.getState_code()));
						state_name.setSelectedIndex(getIndex(loginDt.getState_code(), state_name, 6));
						dataedit=false;
					}
					else
					{
						dataedit=true;
						
					}
					if(gp.getGp_code()==70)
						dataedit=true;
					
					setField(dataedit);
					ptype.requestFocus();
					 
				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					headPane.setVisible(false);
					mgrp_code.requestFocus();
					evt.consume();
				}
			}
		});				
		// ////////////////////////////////////////////////////////////////	

	


	//	getContentPane().add(arisleb);


/*		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);
*/
		branch.setText(loginDt.getBrnnm());
		branch.setBounds(400, 35, 395, 22);
		getContentPane().add(branch);

		lblDispatchEntry = new JLabel("Account Master");
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDispatchEntry.setBounds(450, 60, 251, 22);
		getContentPane().add(lblDispatchEntry);



		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setBounds(263, 616, 86, 30);
		getContentPane().add(btnAdd);

		btnExcel = new JButton("Excel");
		btnExcel.setActionCommand("Excel");
		btnExcel.setBounds(357, 616, 86, 30);
		getContentPane().add(btnExcel);

		btnSave= new JButton("Save");
		btnSave.setActionCommand("Save");
		btnSave.setBounds(799, 616, 86, 30);
		getContentPane().add(btnSave);
		btnSave.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					saveData();	
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					exitButton.requestFocus();
					exitButton.setBackground(Color.blue);
					btnSave.setBackground(null);
					 
				}
			}
		});
		
		exitButton = new JButton("Exit");
		exitButton.setActionCommand("Exit");
		exitButton.setBounds(891, 616, 86, 30);
		getContentPane().add(exitButton);
		exitButton.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					saveData();	
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					btnSave.requestFocus();
					btnSave.setBackground(Color.blue);
					exitButton.setBackground(null);
				}
				
				
			}
		});		 
		
		
		mgrp_code = new JTextField();
		mgrp_code.setName("0");
		mgrp_code.setBounds(392, 123, 75, 22);
		getContentPane().add(mgrp_code);

		lblGroupCode = new JLabel("Group Code:");
		lblGroupCode.setBounds(273, 125, 108, 20);
		getContentPane().add(lblGroupCode);

		lblAcCode = new JLabel("A/c Code:");
		lblAcCode.setBounds(273, 150, 108, 20);
		getContentPane().add(lblAcCode);

		mac_code = new TextField(10);
		mac_code.setEditable(false);
		mac_code.setName("0");
		mac_code.setBounds(392, 149, 75, 22);
		getContentPane().add(mac_code);

		lblName = new JLabel("Name:");
		lblName.setBounds(485, 149, 69, 20);
		getContentPane().add(lblName);

		lblAddress = new JLabel("Address:");
		lblAddress.setBounds(273, 177, 108, 20);
		getContentPane().add(lblAddress);

		madd1 = new TextField(30);
		madd1.setBounds(392, 175, 277, 22);
		madd1.setCaretPosition(0);
		getContentPane().add(madd1);

		mac_name = new TextField(40);
		mac_name.setBounds(548, 149, 417, 22);
		getContentPane().add(mac_name);

		mobile = new TextField(20);
		mobile.setName("0");
		mobile.setBounds(687, 253, 278, 22);
		getContentPane().add(mobile);

		lblMobileNo = new JLabel("Mobile No:");
		lblMobileNo.setBounds(585, 257, 83, 20);
		getContentPane().add(lblMobileNo);

		mphone = new TextField(20);
		mphone.setBounds(392, 253, 164, 22);
		getContentPane().add(mphone);

		
		gstate = new TextField(2);
		gstate.setBounds(392, 227, 24, 22);
		gstate.setEditable(false);
		getContentPane().add(gstate);

		JLabel lblState = new JLabel("State:");
		lblState.setBounds(273, 232, 108, 20);
		getContentPane().add(lblState);

		
		state_name = new JComboBox(loginDt.getGststateList());
		state_name.setBounds(419, 227, 250, 22);
		getContentPane().add(state_name);

		state_name.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent itemEvent) {
				// TODO Auto-generated method stub
				int state = itemEvent.getStateChange();
				if(state == ItemEvent.SELECTED)
				{
					gststate = (StateDto) itemEvent.getItem();
					gstate.setText(gststate.getGststate_code());
					if(gststate.getState_code()==loginDt.getState_code())
						tax_type.setSelectedIndex(0);
					else
						tax_type.setSelectedIndex(1);
				}
				
			}
	
		});
		


		

		
		lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(273, 257, 108, 20);
		getContentPane().add(lblPhone);

		lblCity = new JLabel("City:");
		lblCity.setBounds(585, 204, 108, 20);
		getContentPane().add(lblCity);


		
		lblAddress_1 = new JLabel("Address:");
		lblAddress_1.setBounds(273, 204, 108, 20);
		getContentPane().add(lblAddress_1);

		madd2 = new TextField(30);
		madd2.setName("0");
		madd2.setBounds(392, 201, 164, 22);
		madd2.setCaretPosition(0);
		getContentPane().add(madd2);

		mcity = new TextField(15);
		mcity.setName("0");
		mcity.setBounds(687, 200, 278, 22);
		getContentPane().add(mcity);

		
		mbank_add2 = new TextField(30);
		mbank_add2.setName("0");
		mbank_add2.setBounds(687, 336, 278, 22);
		getContentPane().add(mbank_add2);

		lblBankAddress = new JLabel("Bank Address");
		lblBankAddress.setBounds(273, 342, 108, 20);
		getContentPane().add(lblBankAddress);

		mbanker = new TextField(30);
		mbanker.setName("0");
		mbanker.setBounds(392, 309, 278, 22);
		getContentPane().add(mbanker);

		lblBank = new JLabel("Bank:");
		lblBank.setBounds(273, 314, 108, 20);
		getContentPane().add(lblBank);

		
		lblIFSC = new JLabel("IFSC Code:");
		lblIFSC.setBounds(688, 309, 83, 20);
		getContentPane().add(lblIFSC);

		ifsc_code = new TextField(30);
		ifsc_code.setName("0");
		ifsc_code.setBounds(777, 309, 188, 22);
		getContentPane().add(ifsc_code);

		
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(585, 283, 83, 20);
		getContentPane().add(lblEmail);

		memail = new TextField(50);
		memail.setName("0");
		memail.setBounds(687, 279, 278, 22);
		getContentPane().add(memail);

		lblTransporter = new JLabel("Transporter:");
		lblTransporter.setBounds(273, 368, 92, 20);
		getContentPane().add(lblTransporter);

		lblTaxtpe = new JLabel("Tax Type:");
		lblTaxtpe.setBounds(273, 417, 108, 20);
		getContentPane().add(lblTaxtpe);


		tax_type = new JComboBox();
		tax_type.setModel(new DefaultComboBoxModel(new String[] {"SGST/CGST", "IGST"}));
		tax_type.setSelectedIndex(0);
		tax_type.setEnabled(false);
		tax_type.setName("0");
		tax_type.setBounds(392, 417, 278, 22);
		getContentPane().add(tax_type);

		
		
		lblGST = new JLabel("GST No.:");
		lblGST.setBounds(688, 390, 83, 20);
		getContentPane().add(lblGST);

		gst_no = new TextField(15);
		gst_no.setName("0");
		gst_no.setBounds(777, 390, 188, 22);
		getContentPane().add(gst_no);

	
		
		lblDistance = new JLabel("Distance:");
		lblDistance.setBounds(688, 363, 83, 20);
		getContentPane().add(lblDistance);

		distance = new JDoubleField(0);
		distance.setBounds(777, 363, 188, 22);
		distance.setPrecision(0);
		distance.setMaxLength(6); //Set maximum length             
		distance.setAllowNegative(false); //Set false to disable negatives

		distance.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(distance);
	
		
		lblPanNumber = new JLabel("PAN Number:");
		lblPanNumber.setBounds(273, 390, 108, 20);
		getContentPane().add(lblPanNumber);

		pan_no = new TextField(10);
		pan_no.setName("0");
		pan_no.setBounds(392, 390, 278, 22);
		getContentPane().add(pan_no);
	
		
		


		transporter = new JComboBox(loginDt.getTransporterList());
		transporter.setName("0");
		transporter.setBounds(392, 363, 278, 22);
		getContentPane().add(transporter);
		transporter.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent itemEvent) {
				// TODO Auto-generated method stub
				int state = itemEvent.getStateChange();
				if(state == ItemEvent.SELECTED)
				{
					tdto = (TransportDto) itemEvent.getItem();
					mtranspt.setText(tdto.getTran_name());
				}
				
			}
	
		});

		


		mtranspt = new TextField(30);
		mtranspt.setName("0");
		mtranspt.setBounds(392, 363, 278, 22);
		getContentPane().add(mtranspt);

		mbank_add1 = new TextField(30);
		mbank_add1.setName("0");
		mbank_add1.setBounds(392, 336, 278, 22);
		getContentPane().add(mbank_add1);

		lblType = new JLabel("Type:");
		lblType.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblType.setBounds(690, 124, 46, 20);
		getContentPane().add(lblType);
		
		
		ptype =  new JComboBox();
		ptype.setModel(new DefaultComboBoxModel(new String[] {"Registered", "Unregistered"}));
		ptype.setBounds(737, 123, 102, 22);
		getContentPane().add(ptype);
		ptype.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED)
				{
					if(ptype.getSelectedIndex()==1)
			 		{
			 			gst_no.setEnabled(false);
			 		}	
					else
						gst_no.setEnabled(true);
					
				}
				
			}
		});
		
		lblLock = new JLabel("Lock (Y/N):");
		lblLock.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLock.setBounds(847, 124, 75, 20);
		getContentPane().add(lblLock);

		mtype1 = new JComboBox();
		mtype1.setModel(new DefaultComboBoxModel(new String[] {"Y", "N"}));
		mtype1.setSelectedIndex(1);
		mtype1.setBounds(919, 123, 46, 22);
		getContentPane().add(mtype1);

		lblPinCode = new JLabel("Pin Code:");
		lblPinCode.setBounds(688, 231, 83, 20);
		getContentPane().add(lblPinCode);

		lblContactPerson = new JLabel("Contact Person:");
		lblContactPerson.setBounds(273, 284, 108, 20);
		getContentPane().add(lblContactPerson);

		mcontct = new TextField(30);
		mcontct.setBounds(392, 279, 164, 22);
		getContentPane().add(mcontct);

		lblDueDays = new JLabel("Credit Days:");
		lblDueDays.setBounds(273, 550, 83, 20);
		getContentPane().add(lblDueDays);

		lblDiscount = new JLabel("Discount %:");
		lblDiscount.setBounds(273, 575, 83, 20);
		getContentPane().add(lblDiscount);

		mdays = new JIntegerField(2);
		mdays.setBounds(392, 547, 112, 22);
		mdays.setText("0");
		getContentPane().add(mdays);

		mfix_disc1 = new JDoubleField();
		mfix_disc1.setPrecision(2);
		mfix_disc1.setAllowNegative(false);
		mfix_disc1.setMaxLength(9);
		mfix_disc1.setHorizontalAlignment(SwingConstants.RIGHT);
		mfix_disc1.setBounds(392, 573, 112, 22);
		getContentPane().add(mfix_disc1);

		lblOpeningBalance = new JLabel("Opening Balance:");
		lblOpeningBalance.setBounds(273, 497, 125, 20);
		getContentPane().add(lblOpeningBalance);

		mopng_bal = new JDoubleField();
		mopng_bal.setPrecision(2);
		mopng_bal.setAllowNegative(false);
		mopng_bal.setMaxLength(9);
		mopng_bal.setHorizontalAlignment(SwingConstants.RIGHT);
		mopng_bal.setBounds(392, 495, 112, 22);
		getContentPane().add(mopng_bal);

		lblCrdr = new JLabel("CR/DR:");
		lblCrdr.setBounds(273, 523, 86, 20);
		getContentPane().add(lblCrdr);

		
		crdr =  new JComboBox();
		crdr.setModel(new DefaultComboBoxModel(new String[] {"CR", "DR"}));
		crdr.setBounds(392, 521, 112, 22);
		getContentPane().add(crdr);

		mopng_db = new TextField(2);
		mopng_db.setName("0");
		mopng_db.setBounds(392, 521, 112, 22);
		getContentPane().add(mopng_db);

		mpin = new TextField(10);
		mpin.setName("0");
		mpin.setBounds(777, 227, 188, 22);
		getContentPane().add(mpin);

		madd3 = new TextField(30);
		madd3.setName("0");
		madd3.setBounds(687, 175, 278, 22);
		madd3.setCaretPosition(0);
		getContentPane().add(madd3);

		lblAccountBalance = new JLabel("Account Balance");
		lblAccountBalance.setForeground(Color.WHITE);
		lblAccountBalance.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAccountBalance.setBounds(387, 468, 112, 15);
		getContentPane().add(lblAccountBalance);

		panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBackground(new Color(51, 102, 204));
		panel_3.setBounds(312, 463, 257, 25);
		getContentPane().add(panel_3);

		mgrp_name = new JTextField();
		mgrp_name.setEditable(false);
		mgrp_name.setName("0");
		mgrp_name.setBounds(485, 123, 197, 22);
		getContentPane().add(mgrp_name);

		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(263, 657, 714, 48);
		getContentPane().add(panel_2);

		lblName_1 = new JLabel("Name:");
		lblName_1.setBounds(16, 161, 83, 20);
		getContentPane().add(lblName_1);

		party_name = new JFormattedTextField();
		party_name.setBounds(75, 161, 168, 22);
		getContentPane().add(party_name);
				
		
		
		
		
		////////////// invoce no table model/////////////////////
		String [] partyColName=	{"Code.", "Party Name",""};
		String datax[][]={{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}};
		partyTableModel=  new DefaultTableModel(datax,partyColName)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				return false;
			}
		};

		partyTable = new JTable(partyTableModel);
		partyTable.setFont(fontPlan);
		partyTable.setColumnSelectionAllowed(false);
		partyTable.setCellSelectionEnabled(false);
		partyTable.getTableHeader().setReorderingAllowed(false);
		partyTable.getTableHeader().setResizingAllowed(false);
		partyTable.getTableHeader().setFont(font);
		partyTable.setRowHeight(20);
		partyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		partyTable.getTableHeader().setPreferredSize(new Dimension(25,25));
		///////////////////////////////////////////////////////////////////////////
		partyTable.getColumnModel().getColumn(0).setPreferredWidth(55);   //contact inv no
		partyTable.getColumnModel().getColumn(1).setPreferredWidth(300);  //party name/////
		partyTable.getColumnModel().getColumn(2).setMinWidth(0);  //inv_no/////
		partyTable.getColumnModel().getColumn(2).setMaxWidth(0);  //inv_no/////
		
		
		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer() 
		{
			public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) 
			{
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (partyTable.getSelectedRow()==row )
				{
					  c.setBackground(Color.LIGHT_GRAY);
					  c.setForeground(Color.BLACK); 
				}
				else
				{
					  c.setBackground(Color.WHITE);
					  c.setForeground(Color.BLACK);

				}
		        return c;
			}
		};
		
		partyTable.getColumnModel().getColumn(0).setCellRenderer(dtc);
		partyTable.getColumnModel().getColumn(1).setCellRenderer(dtc);
		
		/////////////////////////////////////////////////////////////////////////////
		scrollPane = new JScrollPane(partyTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(16, 189, 230, 434);
		getContentPane().add(scrollPane);

		
		fillInvTable("");

		
		myTableModel = partyTable.getModel();
        sorter = new TableRowSorter<TableModel>(myTableModel);
		partyTable.setRowSorter(sorter);
		party_name.getDocument().addDocumentListener(TableDataSorter.getTableSorter(party_name, sorter,partyTable, 2,false));
		panel_6 = new JPanel();
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_6.setBackground(new Color(51, 102, 204));
		panel_6.setBounds(10, 126, 242, 28);
		getContentPane().add(panel_6);

		lblSearch = new JLabel("Search");
		panel_6.add(lblSearch);
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 12));

		panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setBounds(10, 126, 243, 508);
		getContentPane().add(panel_5);
		
		/// adding listner for add edit and delete save buttons
		exitButton.addActionListener(this);
		btnSave.addActionListener(this);
		btnExcel.addActionListener(this);
		btnAdd.addActionListener(this);


		mgrp_code.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String cname=getCompanyName(setIntNumber(mgrp_code.getText()), loginDt.getHeadList());
					if(adddata && cname==null)
					{

						headPane.setVisible(true);
						headList.setVisible(true);
						headList.requestFocus();
						headList.setSelectedIndex(0);
						headList.ensureIndexIsVisible(0);
					}
					else if(adddata && cname!=null)
					{
						mgrp_name.setText(cname);
						ptype.requestFocus();
					}
					else
					{
						alertMessage(AccountMaster.this, "Click on Add Button to Add New Record","Account Master",1);
					}

				}
			}
		});
		
		
		

						
		
		
		// /////////////////////////////////////////////////////////////////////////////////////////////
		KeyListener keyListener = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					int id=0;
					try
					{
						JTextField textField = (JTextField) keyEvent.getSource();
						id = Integer.parseInt(textField.getName());
					}
					catch(Exception e)
					{
						JComboBox textCombo = (JComboBox) keyEvent.getSource();
						id = Integer.parseInt(textCombo.getName());	
					}



					
					switch (id) {
					case 0:
						//ptype.requestFocus();
						headList.requestFocus();
						break;
					case 1:
						mtype1.requestFocus();
//						break;
					case 2:
				 		if(ptype.getSelectedIndex()==1)
				 		{
				 			gst_no.setEnabled(false);
				 		}	
/*						if (option.equals("A"))
						
							mac_code.requestFocus();
						else
						{
							mac_name.requestFocus();
							mac_name.setSelectionStart(0);
						}
*/						mac_name.requestFocus();
						mac_name.setSelectionStart(0);
						break;
					case 3:
						mac_name.requestFocus();
						mac_name.setSelectionStart(0);
						break;
					case 4:
						if(!dataedit)
						{
							mopng_bal.requestFocus();
							mopng_bal.setSelectionStart(0);
						}
						else
						{
							madd1.requestFocus();
							madd1.setSelectionStart(0);
						}
						break;
					case 5:
						madd2.requestFocus();
						madd2.setSelectionStart(0);
						break;
					case 6:
						madd3.requestFocus();
						madd3.setSelectionStart(0);
						break;
					case 7:
						mcity.requestFocus();
						mcity.setSelectionStart(0);
						break;
					case 8:
						state_name.requestFocus();
//						mstate_name.setSelectionStart(0);
						break;
					case 9:
				 		mpin.requestFocus();
						mpin.setSelectionStart(0);
						break;
					case 10:
				 		mphone.requestFocus();
						mphone.setSelectionStart(0);
						break;
					case 11:
				 		mobile.requestFocus();
						mobile.setSelectionStart(0);
						break;
					case 12:
				 		mcontct.requestFocus();
						mcontct.setSelectionStart(0);
						break;
					case 13:
				 		memail.requestFocus();
						memail.setSelectionStart(0);
						break;
					case 14:
						boolean email=isValidEmailAddress(memail.getText());
						email=true;
						if(!email)
						{
							alertMessage(AccountMaster.this, "Email Id is not correct","Account Master",2);
							memail.setText("");
							memail.requestFocus();
							memail.setSelectionStart(0);
							break;
						}
						else
						{
							mbanker.requestFocus();
							mbanker.setSelectionStart(0);
							break;
						}
					case 15:
				 		ifsc_code.requestFocus();
				 		ifsc_code.setSelectionStart(0);
						break;
					case 16:
				 		mbank_add1.requestFocus();
						mbank_add1.setSelectionStart(0);
						break;
					case 17:
				 		mbank_add2.requestFocus();
						mbank_add2.setSelectionStart(0);
						break;
					case 18:
				 		transporter.requestFocus();
						break;
					case 19:
				 		distance.requestFocus();
				 		distance.setSelectionStart(0);
				 		if(ptype.getSelectedIndex()==1)
				 		{
				 			gst_no.setEnabled(false);
				 			pan_no.requestFocus();
				 			pan_no.setSelectionStart(0);
				 			
				 		}
						break;
					case 20:
				 		pan_no.requestFocus();
						pan_no.setSelectionStart(0);
						break;
					case 21:
						String gst=gstate.getText()+pan_no.getText();
						int gstlength=0;
						gstlength=gst.length();
						if(gst_no.getText().length()<15)
							 gst_no.setText(gst);
				 		gst_no.requestFocus();
						gst_no.setSelectionStart(gstlength);
						break;
					case 22:
						if(gst_no.getText().length()<15)
						{
							alertMessage(AccountMaster.this, "GST No is not correct","Account Master",2);
							gst=gstate.getText()+pan_no.getText();
							gstlength=gst.length();
							gst_no.setText(gst);
							gst_no.requestFocus();
							gst_no.setSelectionStart(gstlength);
							break;
						}
						else
						{
							mopng_bal.requestFocus();
							mopng_bal.setSelectionStart(0);
							break;
						}
					case 23:
				 		crdr.requestFocus();
//						mopng_db.setSelectionStart(0);
						break;
					case 24:
				 		mdays.requestFocus();
						mdays.setSelectionStart(0);
						break;
					case 25:
				 		mfix_disc1.requestFocus();
						mfix_disc1.setSelectionStart(0);
						break;
					case 26:
				 		btnSave.requestFocus();
				 		btnSave.setBackground(Color.BLUE);
						break;
					}
				}

				if (key == KeyEvent.VK_ESCAPE) {
					clearAll();
					dispose();
					//System.exit(0);
					
				}
			}

			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		};

		// ///////////////////////////////////////////////		
		
		mgrp_code.setName("0");
		ptype.setName("1");
		mtype1.setName("2");
		mac_code.setName("3");
		mac_name.setName("4");
		madd1.setName("5");
		madd2.setName("6");
		madd3.setName("7");
		mcity.setName("8");
		state_name.setName("9");
		mpin.setName("10");
		mphone.setName("11");
		mobile.setName("12");
		mcontct.setName("13");
		memail.setName("14");
		mbanker.setName("15");
		ifsc_code.setName("16");
		mbank_add1.setName("17");
		mbank_add2.setName("18");
		transporter.setName("19");
		distance.setName("20");
		pan_no.setName("21");
		gst_no.setName("22");
		mopng_bal.setName("23");
		crdr.setName("24");
//		mopng_db.setName("24");
		mdays.setName("25");
		mfix_disc1.setName("26");
						
						
		addKeyListener(AccountMaster.this,keyListener);
		
		setFocusListener(AccountMaster.this);

		// / setting default focus request
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				party_name.requestFocus();
			}

			public void windowClosed(WindowEvent e) {
				clearAll();
			}
		});

		// setAlwaysOnTop(true);


		
		
		panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(263, 103, 714, 508);
		getContentPane().add(panel_4);


		
		mac_code.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearAll();
					dispose();
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
				   if (option.equals("A"))
				   {
						try
						{
							String date=mac_code.getText();
							boolean chk = prtdao.checkParty(loginDt.getDepo_code(), loginDt.getDiv_code(), date);
							if (chk)
							{
								JOptionPane.showMessageDialog(AccountMaster.this,"Code Already Exists " ,"Duplicate Code",JOptionPane.INFORMATION_MESSAGE);						
								mac_code.setText("");
								mac_code.requestFocus();							
								
							}
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
				   }
					evt.consume();
				}
				
			}
		});		
			

		/*party_name.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					//clearAll();
					dispose();
					//System.exit(0);
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{

					try
					{
						String date=party_name.getText();
						partyTableModel.getDataVector().removeAllElements();
						fillInvTable(date); 
						 
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					evt.consume();
				}
				
			}
		});		*/
		
		
		partyTable.addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent evt) 
			{

				int key=evt.getKeyCode();
				if(key==KeyEvent.VK_ENTER || key==KeyEvent.VK_UP|| key==KeyEvent.VK_DOWN)
				{
					setPartyCurrentRow();
					evt.consume();
    				
				}
				
			}
		});
	
		
		partyTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e)
			{
				
				setPartyCurrentRow();
				e.consume();

			}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent ee){} 

		
		});
		

		
	}


   public void setPartyCurrentRow()
   {
		
		int viewRow = partyTable.getSelectedRow();
       if (viewRow < 0) {
           //Selection got filtered away.
           //statusText.setText(""); DO Nothing
       } else {
           int modelRow = partyTable.convertRowIndexToModel(viewRow);
           
           PartyDto p = (PartyDto) myTableModel.getValueAt(modelRow, 2);
		 	clearAll();
			fillData(p);
			partyTable.changeSelection(modelRow, 0, false, false);
       }			


   }


	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			clearAll();
			mac_code.setEditable(false);
			btnAdd.setEnabled(true);
			btnExcel.setEnabled(true);
			party_name.setText("");
			fillInvTable("");
			dispose();
		}

		if(e.getActionCommand().equalsIgnoreCase("Add"))
		{
			option="A";
			clearAll();
			adddata=true;
			mgrp_code.requestFocus();
			mac_code.setEditable(true);
			btnAdd.setEnabled(false);
			btnExcel.setEnabled(false);
			
		}

		if(e.getActionCommand().equalsIgnoreCase("Excel"))
		{
			
			PartyPrint invp = new PartyPrint(loginDt.getMkt_year(),loginDt.getDepo_code(),loginDt.getDiv_code(),"Excel",loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm());

			clearAll();
			
		}

		
		
		if(e.getActionCommand().equalsIgnoreCase("Delete"))
		{

			System.out.println("record deleted ");

		}		

		if(e.getActionCommand().equalsIgnoreCase("Save"))
		{
			saveData();	
		}		
	}




	public void fillData(PartyDto p)
	{
		// invoice header
		mac_code.setText(p.getMac_code());
		mac_name.setText(p.getMac_name());
		madd1.setText(p.getMadd1());
		madd2.setText(p.getMadd2());
		madd3.setText(p.getMadd3());
		mcity.setText(p.getMcity());
		gstate.setText(String.valueOf(p.getMstat_code()));
		
		System.out.println("STATE CODE IS "+p.getMstat_code());
		if(p.getMgrp_code()<45 || p.getMgrp_code()>50  )
		{
			gstate.setText(String.valueOf(loginDt.getState_code()));
			state_name.setSelectedIndex(getIndex(p.getMstat_code(), state_name, 6));
			dataedit=false;
			if(p.getMgrp_code()==70)
				dataedit=true;
			setField(dataedit);
			

		}
		else
		{
			state_name.setSelectedIndex(getIndex(p.getMstat_code(), state_name, 6));
			setField(true);
			dataedit=true;
		}
		mpin.setText(p.getMpin());
		mphone.setText(p.getMphone());
		mobile.setText(p.getMobile());
		memail.setText(p.getMemail());
		mcontct.setText(p.getMcontct());
		mbanker.setText(p.getMbanker());
		mbank_add2.setText(p.getMbank_add1());
		mbank_add1.setText(p.getMbank_add2());
//		mtranspt.setText(p.getMtranspt());
		if(p.getMtran_code()!=0)
			try {
				transporter.setSelectedIndex(getTransporterIndex(p.getMtran_code()));
			} catch (Exception e) {
			
				transporter.setSelectedIndex(0);
			}


		
		gst_no.setText(p.getGst_no());
		pan_no.setText(p.getPan_no());
		distance.setText(String.valueOf(p.getDistance()));

		
		if(p.getTax_type().equals("I"))
			tax_type.setSelectedIndex(1);
		else
			tax_type.setSelectedIndex(0);
		
		//	mtype1.setText(p.getMtype1()) party lock;
		if(p.getMtype2().equals("Y"))
			mtype1.setSelectedIndex(0);
		if(p.getMtype2().equals("N"))
			mtype1.setSelectedIndex(1);
		
		mgrp_code.setText(String.valueOf(p.getMgrp_code()));
		mgrp_name.setText(p.getGrp_name());
		
		mdays.setText(String.valueOf(p.getMdays()));
//		mopng_bal.setText(formatter.format(p.getMopng_bal()));
		System.out.println("balance is "+p.getMopng_bal());
		mopng_bal.setText(formatter.format(p.getMopng_bal()));
		mopng_db.setText(p.getMopng_db());
		
		if(p.getMopng_db().equals("CR"))
			crdr.setSelectedIndex(0);
		else if(p.getMopng_db().equals("DR"))
			crdr.setSelectedIndex(1);

		
		mfix_disc1.setText(formatter.format(p.getMfix_disc1()));
		
		if(p.getMtype1().equals("R"))
			ptype.setSelectedIndex(0);
		if(p.getMtype1().equals("U"))
			ptype.setSelectedIndex(1);
		//ptype.setText(p.getMtype2());

		setCaretPositionTextField(AccountMaster.this);
		adddata=true;
		option="";
		// invoice footer
	}
	
	public PartyDto saveParty() {
		PartyDto p = new PartyDto();
		gststate=(StateDto) state_name.getSelectedItem();
		p.setMac_code(mac_code.getText());
		p.setMac_name(mac_name.getText());
		p.setMadd1(madd1.getText());
		p.setMadd2(madd2.getText());
		p.setMadd3(madd3.getText());
		p.setMcity(mcity.getText());
		p.setMstat_code(setIntNumber(gstate.getText()));
		p.setMstate_name(gststate.getState_name());
		p.setMpin(mpin.getText());
		p.setMphone(mphone.getText());
		p.setMobile(mobile.getText());
		p.setMemail(memail.getText());
		p.setMcontct(mcontct.getText());
		p.setMbanker(mbanker.getText());
		p.setMbank_add1(mbank_add2.getText());
		p.setMbank_add2(mbank_add1.getText());
		p.setMtranspt(mtranspt.getText());
		if(tdto!=null)
			p.setMtran_code(setIntNumber(tdto.getTran_code()));
		p.setGst_no(gst_no.getText());
		p.setPan_no(pan_no.getText());

		
		
		//p.setMtype1(mtype1.getText());
		p.setMtype2(mtype1.getSelectedItem().toString());  // lock
		
		if(ptype.getSelectedIndex()==0)   // registered unregisterd
			p.setMtype1("R");
		else if(ptype.getSelectedIndex()==1)
			p.setMtype1("U");
		
		if(tax_type.getSelectedIndex()==0)   // tax type
			p.setTax_type("L");
		else if(tax_type.getSelectedIndex()==1)
			p.setTax_type("I");
		
		if(crdr.getSelectedIndex()==0)   // crdr
			mopng_db.setText("CR");
		else if(crdr.getSelectedIndex()==1)
			mopng_db.setText("DR");

		
		p.setDistance(setIntNumber(distance.getText()));
		p.setMgrp_code(setIntNumber(mgrp_code.getText()));
		p.setMdays(setIntNumber(mdays.getText()));
		p.setMopng_bal(setDoubleNumber(mopng_bal.getText()));
		
		p.setMopng_db(mopng_db.getText());
		p.setMfix_disc1(setDoubleNumber(mfix_disc1.getText()));
		
		p.setDepo_code(loginDt.getDepo_code());
		p.setMdiv_cd(loginDt.getDiv_code());
		p.setFin_year(loginDt.getFin_year());
		p.setMkt_year(loginDt.getMkt_year());
		

		
		 

		return p;
	}// // PartyDto update method end///////////////	
	
	
	public void fillInvTable(String search)
	{
/*		partyTableModel.getDataVector().removeAllElements();
		partyTableModel.fireTableDataChanged();
		Vector data = (Vector) prtdao.getPartyList(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getMkt_year(),loginDt.getFin_year(),search);
		 
		Vector c = null;
		int s = data.size();
		System.out.println("size of liust is "+s);
		for(int i =0;i<data.size();i++)
		{
			c =(Vector) data.get(i);
			partyTableModel.addRow(c);
		}
           if (s==0)
           {
       		for(int i =0;i<30;i++)
    		{
       			partyTableModel.addRow(new Object[2][]);
    		}
        	   
        	   
           }
*/		
	}


	public void saveData() 
	{
		 
		String gstno="";
		if(gst_no.getText().trim().length()>=15)
			gstno=gst_no.getText().trim().substring(0, 2);
		
		System.out.println("gstno "+gstno);
		int gpcode=setIntNumber(mgrp_code.getText());
		
		
		boolean email=isValidEmailAddress(memail.getText());
		
		if(memail.getText().trim().length()==0)
			email=true;
		
		if(gpcode>=45 && gpcode<=50 && !email)
		{
			alertMessage(AccountMaster.this, "Email Id is not correct","Account Master",2);
			memail.setText("");
			memail.requestFocus();
			memail.setSelectionStart(0);

		}

		else if(gpcode>=45 && gpcode<=50 && ptype.getSelectedIndex()==0 && gst_no.getText().trim().length()!=15)
		{
			alertMessage(AccountMaster.this, "GST NO is NOT CORRECT. It should be of 15 Character","Account Master",2);
			String gst=gstate.getText()+pan_no.getText();
			int gstlength=gst.length();
			gst_no.setText(gst);
			gst_no.requestFocus();
			gst_no.setSelectionStart(gstlength);
		}			
		else if(gpcode>=45 && gpcode<=50 && ptype.getSelectedIndex()==0 && !gstno.equals(gstate.getText()))
		{
			alertMessage(AccountMaster.this, "GST NO is NOT CORRECT. It should be start with "+gstate.getText(),"Account Master",2);
			String gst=gstate.getText()+pan_no.getText();
			int gstlength=gst.length();
			gst_no.setText(gst);
			gst_no.requestFocus();
			gst_no.setSelectionStart(gstlength);
			
		}			
		else if (option.equals("A"))
		{
			int i =prtdao.addParty(saveParty());
			if(i>0)
			{

			mac_code.setText(String.valueOf(i));
//			loginDt.setPrtList(prtdao.getPartyNm(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),45,1));
			//Vector v = loginDt.getPrtList();
			//v.add(saveParty());

//			loginDt.setPrtmap(prtdao.getPartyNmMap(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),45,1));
			alertMessage(AccountMaster.this, "Record Added Successfully. A/c Code is "+i,"Account Master",1);
			}
			else
				alertMessage(AccountMaster.this, "Error While Saving!!!","Account Master",2);

			option="";
			
			
			clearAll();
			fillInvTable("");

		}
		else
		{
			int i =prtdao.updateParty(saveParty());
			if(i>0)
			{
//				loginDt.setPrtList(prtdao.getPartyNm(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),45,1));
//				loginDt.setPrtmap(prtdao.getPartyNmMap(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),45,1));
				alertMessage(AccountMaster.this, "Record Saved Successfully","Account Master",1);
			}
			else
				alertMessage(AccountMaster.this, "Error While Saving!!!","Account Master",2);
			mac_code.setEditable(false);
			btnAdd.setEnabled(true);
			btnExcel.setEnabled(true);
			
			clearAll();
			fillInvTable("");

		}
		
		

	}	
		 
		
	public int getTransporterIndex(int code)
	{
		 int i=0;
		 Vector v = loginDt.getTransporterList();
		 int size=v.size();
		 
		 for(i=0; i<size;i++)
		 {
			 tdto = (TransportDto) v.get(i);
			 if(tdto.getTran_code().equals(String.valueOf(code)))
			 {
				 mtranspt.setText(tdto.getTran_name());
				 break;
			 }
		 }
		 
		 return i;
		
	}
	public void setField(boolean dataedit)
	{
		mac_code.setEnabled(false);
		madd1.setEnabled(dataedit);
		madd2.setEnabled(dataedit);
		madd3.setEnabled(dataedit);
		mcity.setEnabled(dataedit);
		gstate.setEnabled(dataedit);
		state_name.setEnabled(dataedit);
		mpin.setEnabled(dataedit);
		mphone.setEnabled(dataedit);
		mobile.setEnabled(dataedit);
		mcontct.setEnabled(dataedit);
		memail.setEnabled(dataedit);
		mbanker.setEnabled(dataedit);
		ifsc_code.setEnabled(dataedit);
		mbank_add1.setEnabled(dataedit);
		mbank_add2.setEnabled(dataedit);
		transporter.setEnabled(dataedit);
		distance.setEnabled(dataedit);
		gst_no.setEnabled(dataedit);
		pan_no.setEnabled(dataedit);

	}
	
	
	public void clearAll() {
		mac_code.setText("");
		mac_name.setText("");
		madd1.setText("");
		madd2.setText("");
		madd3.setText("");
		mcity.setText("");
//		mstate_name.setText("");
		mpin.setText("");
		mphone.setText("");
		mobile.setText("");
		memail.setText("");
		mcontct.setText("");
		mbanker.setText("");
		mbank_add2.setText("");
		mbank_add1.setText("");
//		mtranspt.setText("");
		gst_no.setText("");
		pan_no.setText("");
		distance.setText("");
		ifsc_code.setText("");
		
		//mtype1.setText("");
		mgrp_code.setText("");
		mfix_disc1.setText("");
		mdays.setText("");
		mopng_bal.setText("");
		mopng_db.setText("");
		ptype.setSelectedIndex(0);
		crdr.setSelectedIndex(0);
		btnSave.setBackground(null);
		exitButton.setBackground(null);
		party_name.setText("");
//		party_name.requestFocus();
		adddata=false;
		btnAdd.setEnabled(true);		
	//	party_name.setText("");
	//	partyTableModel.getDataVector().removeAllElements();
	//	fillInvTable("");


	//	scrollPane.getHorizontalScrollBar().setValue(0);
	//	scrollPane.getVerticalScrollBar().setValue(0);
		

	}		
}
 