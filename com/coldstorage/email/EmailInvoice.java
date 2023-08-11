package com.coldstorage.email;

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
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.coldstorage.dao.InvPrintDAO;
import com.coldstorage.dao.InvViewDAO;
import com.coldstorage.dao.PartyDAO;
import com.coldstorage.dto.InvFstDto;
import com.coldstorage.dto.MonthDto;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.ProductDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.print.GenerateInvoiceGST;
import com.coldstorage.util.FileReaderUtility;
import com.coldstorage.util.JIntegerField;
import com.coldstorage.util.OverWriteTableCellEditor;
import com.coldstorage.util.SendMail;
import com.coldstorage.view.BaseClass;

public class EmailInvoice extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_12;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private DefaultTableModel tableModel,invNoTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable table;
	String partyCd;
	PartyDto prtyDto;
	int billNo,option,bkcode,bkcd,rrow ;
	private JButton btnSave,btnExit;
	private JComboBox month;
	ProductDto item;
	int pcode,invno,rec;
	boolean getInvoiceStatus = true;
	private MonthDto mdto;
	InvFstDto fstDto;

	private JTextField party_name;
	private JList partyList;
	private JScrollPane partyPane;
	private PartyDto partyDto;
	private JTextField email;
	private JLabel lblPartyName;
	private JLabel lblEmail;
	private JFormattedTextField sdate;
	private JFormattedTextField edate;
	private JComboBox fyear;
	private InvPrintDAO invPrintDao;
	private JCheckBox selectAll;
	private JPanel panel;
	private JButton generatePdf,btnSms,btnCSV;
	private DefaultTableModel partyTableModel;
	private JTable partyTable;
	private Font font,fontPlan;
	private JScrollPane partyTablePane;
	private JRadioButton selectiveRb;
	private JRadioButton allRb;
	private InvViewDAO ivViewDao;
	private JRadioButton retailInv,rdbtnDatewise,rdbtnInvoiceWise,rdbtnLrwise,rdbtnInvoice,rdbtnCsv;
	private JRadioButton cstInv;
	private JPanel panel_4;
	private SwingWorker<?,?> worker;
	private ImageIcon loadingIcon;
	private JLabel lodingLabel;
	private String repnm;
	private int doc_tp;
	private Vector<?> partyNames;
	private PartyDAO pdao;
	private JIntegerField sno;
	private JIntegerField eno;  
	int sinv,einv,row;
	YearDto yearDto; 
	JLabel label_1 ,label_3,lblStartingNo,lblEndingNo;
	boolean csv=true;
	public EmailInvoice(String repnm,String classnm)
	{
		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		this.repnm=classnm;
		doc_tp=40;
		String heading="Dispatch Date";
		
		if (this.repnm.trim().equalsIgnoreCase("Email Credit Note"))
		{
			doc_tp=41;
			heading = "Ref No";
		}

		pdao = new PartyDAO();
		
		invPrintDao = new InvPrintDAO();
		ivViewDao = new InvViewDAO();
		font = new Font("Tahoma", Font.BOLD, 11);
		fontPlan =new Font("Tahoma", Font.PLAIN, 11);

		///////////////////////////////////////////////////////////////////////////
		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(400, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);


		getContentPane().add(arisleb);


		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);


		branch.setText(loginDt.getBrnnm());
		branch.setBounds(400, 35, 395, 22);
		getContentPane().add(branch);

		lblDispatchEntry = new JLabel(classnm);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDispatchEntry.setBounds(450, 60, 351, 22);
		getContentPane().add(lblDispatchEntry);


		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(272, 657, 705, 48);
		getContentPane().add(panel_2);



		//left side table 
		String [] invColName=	{"","Code","Party Name","",""};
		String datax[][]={};
		partyTableModel=  new DefaultTableModel(datax,invColName)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				if(allRb.isSelected())
				{
					if(column==0)
						return true;
					else
						return false;
				}
				else
					return true;


			}
			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
				case 0: return Boolean.class;
				default:
					return String.class;
				}
			}

		};

		partyTable = new JTable(partyTableModel);
		partyTable.getTableHeader().setPreferredSize(new Dimension(25, 25));
		partyTable.setColumnSelectionAllowed(false);
		partyTable.setRowSelectionAllowed(true);
		partyTable.setCellSelectionEnabled(false);
		partyTable.getTableHeader().setReorderingAllowed(false);
		partyTable.getTableHeader().setResizingAllowed(false);
		partyTable.getTableHeader().setFont(font);
		partyTable.setRowHeight(20);
		partyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		//setting table column widths.
		partyTable.getColumnModel().getColumn(0).setPreferredWidth(20);   //Select
		partyTable.getColumnModel().getColumn(1).setPreferredWidth(60);   //code
		partyTable.getColumnModel().getColumn(2).setPreferredWidth(200);  //party name
		partyTable.getColumnModel().getColumn(3).setMinWidth(0);  //hidden vector
		partyTable.getColumnModel().getColumn(3).setMaxWidth(0);  //hidden vector

		partyTable.getColumnModel().getColumn(4).setMinWidth(0);  //hidden email
		partyTable.getColumnModel().getColumn(4).setMaxWidth(0);  //hidden email

		//PartyTable scrollbar pane
		partyTablePane = new JScrollPane(partyTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		partyTablePane.setBounds(11, 223, 248, 384);
		getContentPane().add(partyTablePane);

		final TableModel myTableModel = partyTable.getModel();

		partyTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int viewRow = partyTable.getSelectedRow();
						if (viewRow < 0) {
							//Selection got filtered away.
							//statusText.setText("");
						} 
						else {
							int modelRow = partyTable.convertRowIndexToModel(viewRow);
							row=modelRow;
							Vector<?> partyInviceList = (Vector<?>) myTableModel.getValueAt(modelRow, 3);
							//clearAll();

							//System.out.println(partyInviceList.size());
							//System.out.println(partyInviceList);

							party_name.setText(myTableModel.getValueAt(modelRow, 2).toString());
							if(loginDt.getDepo_code()==1)
							{
								if(myTableModel.getValueAt(modelRow, 4).toString().length()>0)
									email.setText(myTableModel.getValueAt(modelRow, 4).toString()+","+loginDt.getEmaiId());
								else
									email.setText(loginDt.getEmaiId());
							}
							else
								email.setText(myTableModel.getValueAt(modelRow, 4).toString());

							email.setCaretPosition(0);
							party_name.setCaretPosition(0);
							
							fillInvTable(partyInviceList);

							//statusText.setText(String.format("Selected Row in view: %d. " +"Selected Row in model: %d.",viewRow, modelRow));
						}
					}


				}
				);



		//Textfield keylisners
		KeyListener keyListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) 
				{
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


					switch (id) 
					{
					case 1:
							sdate.requestFocus();
							sdate.setSelectionStart(0);
						break;

					case 2:

							String date=sdate.getText();
							yearDto = (YearDto) (fyear.getSelectedItem());
							if(isValidDate(date) && isValidRange(date, yearDto.getMsdate(), yearDto.getMedate()))
							{
								edate.requestFocus();
								edate.setText(date);
								edate.setSelectionStart(0);
								//checkDate(edate);
							}
							else
							{
								alertMessage(EmailInvoice.this, "Invalid Date");
								sdate.setValue(null);
								checkDate(sdate);
								sdate.requestFocus();
								sdate.setSelectionStart(0);
							}

						break;

					case 3:

							String date1=edate.getText();
							yearDto = (YearDto) (fyear.getSelectedItem());
							if(isValidDate(date1) && isValidRange(date1, yearDto.getMsdate(), yearDto.getMedate()))
							{
								getDataPartyTableData();
								party_name.requestFocus();
							}
							else
							{
								if(rdbtnCsv.isSelected())
								{
									getDataPartyTableData();
									party_name.requestFocus();
								}
								else
								{
									alertMessage(EmailInvoice.this, "Invalid Date");
									edate.setValue(null);
									checkDate(edate);
									edate.requestFocus();
									edate.setSelectionStart(0);
								}
							}
						break;

					}

				}

				if (key == KeyEvent.VK_ESCAPE) 
				{
					clearAll(false);
					dispose();

				}




			}

		};




		party_name = new JTextField();
		party_name.setBounds(412, 215, 281, 23);
		getContentPane().add(party_name);
		party_name.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
				{

					String partyCode = party_name.getText();

					partyDto = (PartyDto) loginDt.getPrtmap().get(partyCode.toUpperCase());

					if(partyDto!=null)
					{
						party_name.setText(partyDto.getMac_name());
						email.setText(partyDto.getMemail());
						fillInvTable(invPrintDao.getInvList(partyDto.getMac_code(), loginDt.getFin_year(), loginDt.getDiv_code(), loginDt.getDepo_code(), formatDate(sdate.getText()),formatDate(edate.getText()),doc_tp));
						email.requestFocus();
						partyPane.setVisible(false);
						partyList.setVisible(false);
					}
					else
					{
						partyPane.setVisible(true);
						partyList.setVisible(true);
						partyList.requestFocus();
						partyList.setSelectedIndex(0);
						partyList.ensureIndexIsVisible(0);
					}

				}
			}

		});


		//PartyList ////////////////////////////////////////////////////////

		partyNames = loginDt.getPrtList();
		
		partyList = new JList(partyNames);
		partyList.setBounds(1, 1, 262, 140);
		partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		partyList.setSelectedIndex(0);
		getContentPane().add(partyList);
		partyPane = new JScrollPane(partyList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		partyPane.setBounds(412, 238, 281, 162);
		getContentPane().add(partyPane);
		partyPane.setVisible(false);
		partyList.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					partyPane.setVisible(false);
					partyList.setVisible(false);

					int partyCodeIndex = partyList.getSelectedIndex();
					partyDto = (PartyDto) partyNames.get(partyCodeIndex); 

					if(partyDto!=null)
					{

						party_name.setText(partyDto.getMac_name());
						if(loginDt.getDepo_code()==1)
							email.setText(partyDto.getMemail()+","+loginDt.getEmaiId());
						else
							email.setText(partyDto.getMemail());

						fillInvTable(invPrintDao.getInvList(partyDto.getMac_code(), loginDt.getFin_year(), loginDt.getDiv_code(), loginDt.getDepo_code(), formatDate(sdate.getText()),formatDate(edate.getText()),doc_tp ));
						email.requestFocus();
						partyPane.setVisible(false);
						partyList.setVisible(false);
					}

				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					partyPane.setVisible(false);
					party_name.requestFocus();
					evt.consume();
				}
			}
		});





		btnSave = new JButton("E-Mail");
		btnSave.setIcon(emailIcon);
		btnSave.setActionCommand("Save");
		btnSave.setBounds(799, 616, 86, 30);
		getContentPane().add(btnSave);


		btnExit = new JButton("Exit");
		btnExit.setIcon(exitIcon);
		btnExit.setActionCommand("Exit");
		btnExit.setBounds(891, 616, 86, 30);
		getContentPane().add(btnExit);



		//clearAll(false);

		sdate = new JFormattedTextField();
		sdate.setText(formatDateScreen(loginDt.getSdate(), sdate));
		sdate.setSelectionStart(0);
		sdate.setBounds(412, 156, 136, 22);
		getContentPane().add(sdate);

		label_1 = new JLabel("Starting Date:");
		label_1.setBounds(302, 156, 110, 20);
		getContentPane().add(label_1);

		label_3 = new JLabel("Ending Date:");
		label_3.setBounds(302, 187, 110, 20);
		getContentPane().add(label_3);

		edate = new JFormattedTextField();
		edate.setText(formatDateScreen(loginDt.getEdate(), edate));
		edate.setBounds(412, 187, 136, 22);
		getContentPane().add(edate);

		
		rdbtnInvoice = new JRadioButton("Invoice");
		rdbtnInvoice.setSelected(true);
		rdbtnInvoice.setVisible(false);
		rdbtnInvoice.setActionCommand("Invoice");
		rdbtnInvoice.setBounds(700, 93, 99, 23);
		getContentPane().add(rdbtnInvoice);

		
		rdbtnCsv = new JRadioButton("Delivery Challan");
		rdbtnCsv.setVisible(false);
		rdbtnCsv.setActionCommand("Challan");
		rdbtnCsv.setBounds(822, 93, 169, 23);
		getContentPane().add(rdbtnCsv);

		
		
		rdbtnDatewise = new JRadioButton("DateWise");
		rdbtnDatewise.setSelected(true);
		rdbtnDatewise.setBounds(570, 124, 99, 23);
		getContentPane().add(rdbtnDatewise);
		
		rdbtnLrwise = new JRadioButton("LrWise");
		rdbtnLrwise.setSelected(true);
		rdbtnLrwise.setBounds(700, 124, 99, 23);
		getContentPane().add(rdbtnLrwise);

		
		rdbtnInvoiceWise = new JRadioButton("No Wise");
		rdbtnInvoiceWise.setBounds(822, 124, 128, 23);
		getContentPane().add(rdbtnInvoiceWise);
		
		
		lblStartingNo = new JLabel("Starting No:");
		lblStartingNo.setBounds(720, 151, 110, 20);
		getContentPane().add(lblStartingNo);
	
		
		sno = new JIntegerField(8);
		sno.setBounds(830, 151, 136, 22);
		sno.setHorizontalAlignment(SwingConstants.LEFT);
		sno.setEnabled(false);
		sno.setSelectionStart(0);
		getContentPane().add(sno);

		
		
		lblEndingNo = new JLabel("Ending No.:");
		lblEndingNo.setBounds(720, 182, 110, 20);
		getContentPane().add(lblEndingNo);

		eno = new JIntegerField(8);
		eno.setBounds(830, 182, 136, 22);
		eno.setHorizontalAlignment(SwingConstants.LEFT);
		eno.setSelectionStart(0);
		eno.setEnabled(false);
		getContentPane().add(eno);

		
		
		
		
		ButtonGroup group2 = new ButtonGroup();
		group2.add(rdbtnDatewise);
		group2.add(rdbtnInvoiceWise);
		group2.add(rdbtnLrwise);

		
		ButtonGroup group3 = new ButtonGroup();
		group3.add(rdbtnInvoice);
		group3.add(rdbtnCsv);
	

		sno.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key=e.getKeyCode();
				if (key==KeyEvent.VK_ENTER)
				{
					eno.requestFocus();
					eno.setSelectionStart(0);
				}
			}
		});
		
		eno.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key=e.getKeyCode();
				if (key==KeyEvent.VK_ENTER)
				{
					getDataPartyTableData();
					party_name.requestFocus();
				}
				
			}
		});

		
		
		
		fyear = new JComboBox(loginDt.getFyear());
		fyear.setActionCommand("year");
		fyear.setBounds(412, 125, 136, 23);
		getContentPane().add(fyear);

		JLabel label_4 = new JLabel("Financial Year:");
		label_4.setBounds(302, 125, 110, 20);
		getContentPane().add(label_4);

		email = new JTextField();
		email.setBounds(412, 244, 281, 23);
		getContentPane().add(email);

		lblPartyName = new JLabel("Party Name:");
		lblPartyName.setBounds(302, 215, 110, 20);
		getContentPane().add(lblPartyName);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(302, 244, 110, 20);
		getContentPane().add(lblEmail);

		//adding keylistners...

		fyear.setName("1");
		sdate.setName("2");
		edate.setName("3");
		email.setName("4");

		selectAll = new JCheckBox("Select All");
		selectAll.setBounds(282, 282, 86, 25);
		selectAll.setActionCommand("selectAll");
		selectAll.addActionListener(this);
		getContentPane().add(selectAll);

		// ////////////////////////////////////////////////////////////////////////
		//Data Grid Table Start
		String[] columnNames = { " ","Bill No.", "Date",heading,"LR No.","LR Date", "Amount","" };
		String[][] data = { {}, };
		// //////////////////////////////////////////////////////////////////////////
		tableModel = new DefaultTableModel(data, columnNames) 
		{
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) 
			{
				boolean ress = false;
				if (column==0) 
				{
					ress = true;
				}
				return ress;
			}

			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
				case 0: return Boolean.class;
				default:
					return String.class;
				}
			}
		};
		table = new JTable(tableModel);


		//Scroller in grid//////////////////
		JScrollPane pane = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setVisible(true);
		pane.setBounds(272, 311, 705, 296);
		pane.setBorder(null);
		getContentPane().add(pane);

		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(272, 278, 706, 35);
		getContentPane().add(panel);

		generatePdf = new JButton("PDF");
		generatePdf.setIcon(viewIcon);
		generatePdf.setActionCommand("pdf");
		generatePdf.setBounds(706, 616, 86, 30);
		getContentPane().add(generatePdf);

		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(272, 116, 706, 160);
		getContentPane().add(panel_1);
		
		btnSms = new JButton("SMS");
		btnSms.setActionCommand("SMS");
		btnSms.setVisible(false);
		btnSms.setBounds(272, 616, 86, 30);
		getContentPane().add(btnSms);

		btnCSV = new JButton("CSV");
		btnCSV.setActionCommand("CSV");
		btnCSV.setEnabled(false);
		btnCSV.setVisible(false);
		btnCSV.setBounds(272, 616, 86, 30);
		getContentPane().add(btnCSV);

		
		selectiveRb = new JRadioButton("Selective");
		selectiveRb.setSelected(true);
		selectiveRb.setBounds(18, 191, 91, 23);
		getContentPane().add(selectiveRb);

		allRb = new JRadioButton("All");
		allRb.setBounds(130, 192, 105, 23);
		getContentPane().add(allRb);

		ButtonGroup group1 = new ButtonGroup();
		group1.add(selectiveRb);
		group1.add(allRb);


		retailInv = new JRadioButton("Tax Invoice");
		retailInv.setSelected(true);
		retailInv.setBounds(18, 154, 115, 23);
		getContentPane().add(retailInv);

		cstInv = new JRadioButton("Retail");
		cstInv.setBounds(129, 154, 115, 23);
		getContentPane().add(cstInv);

		ButtonGroup invTypeGrp = new ButtonGroup();
		invTypeGrp.add(retailInv);
		invTypeGrp.add(cstInv);


		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(11, 185, 248, 35);
		getContentPane().add(panel_3);

		panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(11, 148, 248, 35);
		getContentPane().add(panel_4);



		if((loginDt.getDepo_code()==50 || loginDt.getDepo_code()==59 || loginDt.getDepo_code()==58 || loginDt.getDepo_code()==1) && doc_tp!=67)
		{
			if (loginDt.getDepo_code()==1)
			{
				retailInv.setText("Indore Invoice");
				cstInv.setText("Nepal Invoice");
			}
			
			cstInv.setVisible(true);
			retailInv.setVisible(true);
			panel_4.setVisible(true);
		}
		else
		{
			cstInv.setVisible(false);
			retailInv.setVisible(false);
			panel_4.setVisible(false);
		}




		//table.setFont(font);
		// /// calling method for design item table/////////////
		itemTableUI();



		fyear.addKeyListener(keyListener);
		sdate.addKeyListener(keyListener);
		edate.addKeyListener(keyListener);
		email.addKeyListener(keyListener);


		btnSave.addActionListener(this);
		btnExit.addActionListener(this);
		generatePdf.addActionListener(this);
		btnSms.addActionListener(this);
		btnCSV.addActionListener(this);
		fyear.addActionListener(this);

		rdbtnInvoice.addActionListener(this);
		rdbtnCsv.addActionListener(this);

		///// item product selection all/selective
		selectiveRb.addActionListener(this);
		allRb.addActionListener(this);
		selectiveRb.setActionCommand("singleParty");
		allRb.setActionCommand("allParty");

		loadingIcon = new ImageIcon(getClass().getResource("/images/loader.gif"));

		lodingLabel = new JLabel(loadingIcon);
		lodingLabel.setBounds(653, 608, 48, 48);
		lodingLabel.setVisible(false);
		getContentPane().add(lodingLabel);
		

		rdbtnDatewise.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				clearAll(false);
				sno.setEnabled(false);
				eno.setEnabled(false);
				sdate.setEnabled(true);
				edate.setEnabled(true);
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

		rdbtnLrwise.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				clearAll(false);
				sno.setEnabled(false);
				eno.setEnabled(false);
				sdate.setEnabled(true);
				edate.setEnabled(true);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

		
		rdbtnInvoiceWise.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				sno.setEnabled(true);
				eno.setEnabled(true);
				sdate.setEnabled(false);
				edate.setEnabled(false);

				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
				//chq_dt.addKeyListener(keyListener);
		




	}


	public void clearAll(boolean edit)
	{
		//clear item table data
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
		// /// genrate new row in item table/////////////
		tableModel.addRow(new Object[] {});


		partyTableModel.getDataVector().removeAllElements();
		partyTableModel.fireTableDataChanged();

		selectiveRb.setSelected(true);
	
		sno.setEnabled(false);
		eno.setEnabled(false);
		sdate.setEnabled(true);
		edate.setEnabled(true);


		partyList.setVisible(false);




		email.setText("");
		party_name.setText("");

		sno.setText("");
		eno.setText("");

		

		sdate.setVisible(true);
		edate.setVisible(true);
		label_1.setVisible(true);
		label_3.setVisible(true);

/*		lblStartingNo.setBounds(580, 151, 110, 20);
		lblEndingNo.setBounds(580, 182, 110, 20);
		sno.setBounds(690, 151, 136, 22);
		eno.setBounds(690, 182, 136, 22);
*/
		
		if (this.repnm.trim().equalsIgnoreCase("Email Partywise Cash Discount") && (loginDt.getDepo_code()==21 || loginDt.getDepo_code()==15) )
		{
			btnSms.setVisible(true);
		}
		else
		{
			btnSms.setVisible(false);
			
		}


		if (this.repnm.trim().equalsIgnoreCase("Email Invoice") && (loginDt.getDepo_code()==21 || loginDt.getDepo_code()==15 || loginDt.getDepo_code()==50 || csv) )
		{
			btnCSV.setVisible(true);
		}
		else
		{
			btnCSV.setVisible(false);
			
		}


	}


	public void fillInvTable(List data)
	{
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();

		Vector c = null;
		int s = data.size();
		for(int i =0;i<data.size();i++)
		{
			c =(Vector) data.get(i);
			tableModel.addRow(c);
		}

		if (s==0)
		{
			for(int i =0;i<30;i++)
			{
				tableModel.addRow(new Object[2][]);
			}
		}

	}




	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		if(e.getActionCommand().equalsIgnoreCase("year"))
		{
			yearDto = (YearDto) (fyear.getSelectedItem());
		}
		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			saveData("email");
		}


		if(e.getActionCommand().equalsIgnoreCase("pdf"))
		{
			saveData("pdf");
		}


		if(e.getActionCommand().equalsIgnoreCase("sms"))
		{
			saveData("sms");
		}

		if(e.getActionCommand().equalsIgnoreCase("Challan"))
		{
			clearAll(true);
			rdbtnCsv.setSelected(true);
			rdbtnDatewise.setSelected(true);
			sdate.setEnabled(true);
			edate.setEnabled(true);
			generatePdf.setEnabled(true);
			//btnSave.setEnabled(false);
			btnCSV.setEnabled(true);
			

		}
		if(e.getActionCommand().equalsIgnoreCase("Invoice"))
		{
			clearAll(true);
			rdbtnInvoice.setSelected(true);
			rdbtnDatewise.setSelected(true);
			sno.setVisible(true);
			eno.setVisible(true);
			label_1.setVisible(true);
			label_3.setVisible(true);
			lblStartingNo.setVisible(true);
			lblEndingNo.setVisible(true);
			sdate.setEnabled(true);
			edate.setEnabled(true);
			generatePdf.setEnabled(true);
			btnSave.setEnabled(true);
			btnCSV.setEnabled(false);
		}

		 

		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			clearAll(false);
			rdbtnDatewise.setSelected(true);
			rdbtnInvoice.setSelected(true);
			generatePdf.setEnabled(true);
			btnSave.setEnabled(true);
			dispose();
		}	

		if(e.getActionCommand().equalsIgnoreCase("selectAll"))
		{

			if(selectAll.isSelected())
			{
				fillItemDataPrd(true);
			}
			else
			{
				fillItemDataPrd(false);
			}

		}	


		if (e.getActionCommand().equals("singleParty"))
		{
			setSelectionOfPartyTable(false);
			table.setEnabled(true);
			selectAll.setEnabled(true);
			generatePdf.setEnabled(true);

		}

		if (e.getActionCommand().equals("allParty"))
		{
			tableModel.getDataVector().removeAllElements();
			tableModel.fireTableDataChanged();
			tableModel.addRow(new Object[] {});
			setSelectionOfPartyTable(true);
			selectAll.setEnabled(false);
			table.setEnabled(false);
			generatePdf.setEnabled(true);
			if (rdbtnLrwise.isSelected() || repnm.contains("Email Partywise"))
				generatePdf.setEnabled(true);
			if (rdbtnCsv.isSelected() || repnm.contains("Email Partywise"))
				generatePdf.setEnabled(false);
				
		}






	}



	public void fillItemDataPrd(boolean res)
	{
		Vector prodVec =tableModel.getDataVector();
		int size= prodVec.size();

		for(int i =0;i<size;i++)
		{
			tableModel.setValueAt(res, i, 0);			
		}
	}

	public void setSelectionOfPartyTable(boolean res)
	{
		Vector prodVec =partyTableModel.getDataVector();
		int size= prodVec.size();

		for(int i =0;i<size;i++)
		{
			partyTableModel.setValueAt(res, i, 0);			
		}
	}

	public void itemTableUI() {
		table.getTableHeader().setPreferredSize(new Dimension(25, 25));
		table.setColumnSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		//	table.getTableHeader().setFont(font);
		table.setRowHeight(20);

		// ///////////// setting col width/////////////////////////
		table.getColumnModel().getColumn(0).setPreferredWidth(2);//select
		table.getColumnModel().getColumn(1).setPreferredWidth(130);//Bil no
		table.getColumnModel().getColumn(2).setPreferredWidth(70);//bill date
		table.getColumnModel().getColumn(3).setPreferredWidth(85);//dispatch date
		table.getColumnModel().getColumn(4).setPreferredWidth(120);//lr no
		table.getColumnModel().getColumn(5).setPreferredWidth(70);//lr date
		table.getColumnModel().getColumn(6).setPreferredWidth(75);//amount

		table.getColumnModel().getColumn(7).setPreferredWidth(0);//hidden serial no
		table.getColumnModel().getColumn(7).setMinWidth(0);//hidden serial no
		table.getColumnModel().getColumn(7).setMaxWidth(0);//hidden serial no

		// ////////////////////////////////////////////////////////////////
		table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);



		table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "selectNextColumnCell");
		table.setDefaultEditor(String.class, new OverWriteTableCellEditor());
	}


	public int getSelectedInvoices(Vector dataVector) 
	{
		Vector col = null;

		int size = dataVector.size();
		int selectedSize=0;
		//The string builder used to construct the string
		StringBuilder commaSepInvoiceList = new StringBuilder();

		//Looping through the list
		try
		{
			commaSepInvoiceList.append(" in (");
			for ( int i = 0; i< size; i++)
			{

				col = (Vector) dataVector.get(i);
				if ((Boolean) col.get(0))
				{

					//if the value is not the last element of the list
					//then append the comma(,) as well
					if(selectedSize > 0)
					{
						commaSepInvoiceList.append(", ");
					}
					//append the value into the builder
					commaSepInvoiceList.append("'"+col.get(1)+"'");

/*					if(rdbtnCsv.isSelected())
						commaSepInvoiceList.append("'"+col.get(1)+"'");
					else
						commaSepInvoiceList.append(col.get(7));
*/					
					selectedSize++;

				}
			}

			commaSepInvoiceList.append(")");







			System.out.println(commaSepInvoiceList.toString());

			BaseClass.commaSepratedInvoiceNo=commaSepInvoiceList.toString();

		} 
		catch (Exception e) 
		{
			System.out.println("Error in deleteRecord ");
			e.printStackTrace();
		}

		return selectedSize;

	}	 

	public void saveData(String buttoName)
	{
		try{
			submitForm(buttoName);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public void submitForm(String buttonName)
	{
		lodingLabel.setVisible(true);
		btnSave.setEnabled(false);
		
		if (repnm.trim().equalsIgnoreCase("Email Partywise Cash Discount") || repnm.trim().equalsIgnoreCase("Email Partywise Vat Statement") || repnm.trim().equalsIgnoreCase("Email Partywise Dispatch Statement"))
		{
			worker = new NewWorker(buttonName);
		}
		else
		{
		    worker = new NumberWorker(buttonName);
		}
		worker.execute();
	}			



	public class NumberWorker extends SwingWorker<String, Object> 
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		ByteArrayOutputStream outputStreamCSV[] = new ByteArrayOutputStream[50];
		HashMap csvList=new HashMap();
		String buttoName=null;
		
		NumberWorker(String btnName)
		{
			buttoName=btnName;
		}
		
		protected String doInBackground() throws Exception 
		{
			try
			{
				if(selectiveRb.isSelected())
				{
					SendMail ss = new SendMail();
					int selectSize=getSelectedInvoices(tableModel.getDataVector());
					if (selectSize==0)
					{
						alertMessage(EmailInvoice.this, "Please Select Record First!!!!");
					}
					else
					{
						if(buttoName.equalsIgnoreCase("email") || buttoName.equalsIgnoreCase("CSV")  )
						{
							if(checkInternetConnectivity("google.com"))
							{
									if(rdbtnInvoice.isSelected())
										new GenerateInvoiceGST(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),String.valueOf(0),String.valueOf(0),"Email",loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),outputStream,-5);
									else
										new GenerateInvoiceGST(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),String.valueOf(0),String.valueOf(0),"Email",loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),outputStream,-201);
									SendMail.send("Invoice Copy", outputStream, FileReaderUtility.readFile("/EmailTemplates/invoiceTemplate.txt"),email.getText().trim().toLowerCase(), "InvoiceCopy.pdf");
							}
							else
							{
									alertMessage(EmailInvoice.this, "Please Check your Internet Connectivity!!!!");
							}
						}
						else if(buttoName.equalsIgnoreCase("pdf"))
						{
//								new GenerateGSTInvoice(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),"0","0","pdf",loginDt.getDrvnm(),loginDt.getPrinternm(),"2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),null,(rdbtnCsv.isSelected()?-5:-5));
								if(rdbtnInvoice.isSelected())
								new GenerateInvoiceGST(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),String.valueOf(0),String.valueOf(0),"pdf",loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),null,-5);

								else
								new GenerateInvoiceGST(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),String.valueOf(0),String.valueOf(0),"pdf",loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),null,-202);

						}
					}

					BaseClass.commaSepratedInvoiceNo=null;				
				}
				else{

					Vector selectedPartyVct= getSelectedParty();
					int size=selectedPartyVct.size();
					Vector col = null;
					if(checkInternetConnectivity("google.com"))
					{

						GenerateInvoiceGST emailInv = new GenerateInvoiceGST();
						for(int i=0;i<size;i++)
						{
							col =(Vector) selectedPartyVct.get(i);
							outputStream = new ByteArrayOutputStream();
							getSelectedInvoices((Vector)col.get(3));
							csvList=new HashMap();
							
							System.out.println("repnm "+repnm);
							if (repnm.equalsIgnoreCase("Email Transfer Invoice"))
							{
								emailInv.GenerateEmailInvoice(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),"0","0","Email",loginDt.getDrvnm(),loginDt.getPrinternm(),"2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),outputStream,-1);
								SendMail.send("Transfer Invoice Copy", outputStream, FileReaderUtility.readFile("/EmailTemplates/invoiceTemplate.txt"),col.get(4).toString().toLowerCase(), "transferinvoiceCopy.pdf");
								
							}
							

							else
							{
								if (rdbtnLrwise.isSelected() && buttoName.equalsIgnoreCase("pdf"))
								{
									BaseClass.commaSepratedInvoiceNo=(sdate.getText()+","+edate.getText());
									new GenerateInvoiceGST(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),String.valueOf(0),String.valueOf(0),"Email",loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),null,-2);

									break;
								}
								else if (rdbtnDatewise.isSelected() && rdbtnInvoice.isSelected() && buttoName.equalsIgnoreCase("pdf"))
								{
									System.out.println("yeha per aaya kya all pdf option");
									BaseClass.commaSepratedInvoiceNo=(sdate.getText()+","+edate.getText());
//									new GenerateGSTInvoice(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),"0","0","pdf",loginDt.getDrvnm(),loginDt.getPrinternm(),"2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),null,-3);
									new GenerateInvoiceGST(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),String.valueOf(0),String.valueOf(0),"Email",loginDt.getDrvnm(),loginDt.getPrinternm(),"2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),null,-3);

									break;
								}
								else 
								{
									emailInv.GenerateEmailInvoice(String.valueOf(yearDto.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),"0","0","Email",loginDt.getDrvnm(),loginDt.getPrinternm(),"2",loginDt.getMnth_code(),doc_tp,loginDt.getBrnnm(),loginDt.getDivnm(),outputStream,-5);
									SendMail.send("Invoice Copy", outputStream, FileReaderUtility.readFile("/EmailTemplates/invoiceTemplate.txt"),col.get(4).toString().toLowerCase(), "invoiceCopy.pdf");
								}

								
							}
							BaseClass.commaSepratedInvoiceNo=null;
						}

					}
					else
					{
						alertMessage(EmailInvoice.this, "****Please Check your Internet Connectivity!!!!");
					}
				}

			}
			catch(Exception ez)
			{
				ez.printStackTrace();
			}
			System.out.println("after sending of all emails");

			return null;
		}


		protected void done() 
		{
			try 
			{
				worker.cancel(true);
				lodingLabel.setVisible(false);
				btnSave.setEnabled(true);
				if(buttoName.equalsIgnoreCase("email"))
				  alertMessage(EmailInvoice.this, "Mail sent successfully");

				if(buttoName.equalsIgnoreCase("sms"))
					  alertMessage(EmailInvoice.this, "SMS sent successfully");
				if(buttoName.equalsIgnoreCase("CSV"))
					  alertMessage(EmailInvoice.this, "CSV sent successfully");


			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		}
	}






	public int getMonthIndex(int mno)
	{
		Vector month = loginDt.getMmonth();
		int size = month.size();
		int mindex=0;
		for (int i=0;i<size;i++)
		{
			mdto = (MonthDto) loginDt.getMmonth().get(i);
			if(mdto.getMnthno()==mno)
			{
				mindex=i;
				break;
			}


		}
		return mindex;
	}

	public void fillItemData(InvFstDto fst)
	{
		// Item Detail Fill 

		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
		Vector c = null;
		Vector data =fst.getItemData();
		for(int i =0;i<data.size();i++)
		{
			c = (Vector) data.get(i);
			tableModel.addRow(c);
		}
		//moreButton.setEnabled(true);
		//btnDelete.setEnabled(true);

	}


	public void getDataPartyTableData()
	{
		String startDateStr=sdate.getText();
		String endDateStr=edate.getText();
		sinv=setIntNumber(sno.getText());
		einv=setIntNumber(eno.getText());

		yearDto = (YearDto) (fyear.getSelectedItem());


		doc_tp=40;

		Vector<?> data =null;
		
		if (rdbtnInvoiceWise.isSelected())
		{
  			   data = (Vector<?>) ivViewDao.getEmailPartyListNo(sinv,einv,loginDt.getDepo_code(),loginDt.getDiv_code(),doc_tp,yearDto.getYearcode(),loginDt.getMnth_code());
		}
		else
		{
				data = (Vector<?>) ivViewDao.getEmailPartyList(formatDate(startDateStr),formatDate(endDateStr),loginDt.getDepo_code(),loginDt.getDiv_code(),doc_tp);
		}
		
		fillPartyTable(data);





	}


	public void fillPartyTable(List<?> data)
	{
		partyTableModel.getDataVector().removeAllElements();
		partyTableModel.fireTableDataChanged();

		Vector<?> c = null;
		int s = data.size();
		for(int i =0;i<s;i++)
		{
			c =(Vector<?>) data.get(i);
			partyTableModel.addRow(c);
		}

		if (s==0)
		{
			for(int i =0;i<30;i++)
			{
				partyTableModel.addRow(new Object[2][]);
			}
		}

	}


	public Vector<?> getSelectedParty()
	{
		Vector partyVec =partyTableModel.getDataVector();
		int size= partyVec.size();
		Vector selectedVector= new Vector();
		Vector col=null;
		Vector selectedParties = new Vector();

		for(int index =0;index<size;index++)
		{
			selectedVector = (Vector) partyVec.get(index);

			if((Boolean) selectedVector.get(0))
			{
				selectedParties.add(selectedVector);
			}
		}


		return selectedParties;

	}
	
	
	
	
	public class NewWorker extends SwingWorker<String, Object> 
	{

		String buttoName=null;
		
		NewWorker(String btnName)
		{
			buttoName=btnName;
		}

		
		
		protected String doInBackground() throws Exception 
		{
			try
			{
					Vector selectedPartyVct= getSelectedParty();
					ArrayList<String> partyList = new ArrayList<String>();
					int size = selectedPartyVct.size();
					boolean check=true;
					for(int x=0;x<size;x++)
					{
						partyList.add((String) (((Vector)selectedPartyVct.get(x)).get(1)));
						check=false;
					}
					
					if(check)
					{
						partyList.add((String) partyTable.getValueAt(row, 1).toString()+ "-" + email.getText());
					}
					
					 

			}
			catch(Exception ez)
			{
				ez.printStackTrace();
			}
			System.out.println("after sending of all emails#####");

			return null;
		}

		
		protected void done() 
		{
			try 
			{
				worker.cancel(true);
				lodingLabel.setVisible(false);
				btnSave.setEnabled(true);
				if(buttoName.equalsIgnoreCase("email"))
					alertMessage(EmailInvoice.this, "Mail sent successfully");
				if(buttoName.equalsIgnoreCase("sms"))
					  alertMessage(EmailInvoice.this, "SMS sent successfully");
				if(buttoName.equalsIgnoreCase("CSV"))
					  alertMessage(EmailInvoice.this, "CSV sent successfully");

				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		}
	}

	public void setVisible(boolean b) 
	{
		doc_tp=40;
		super.setVisible(b);
	}	 
}


