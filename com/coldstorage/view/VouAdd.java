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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.BankVouDAO;
import com.coldstorage.dao.OedDAO;
import com.coldstorage.dao.PartyDAO;
import com.coldstorage.dao.RcpDAO;
import com.coldstorage.dto.BookDto;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.util.JDoubleField;
import com.coldstorage.util.JIntegerField;


public class VouAdd extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	Font font;
	
	private JComboBox bank; 
	private JTextField vou_no;
	private JTextField vnart1;
	private JDoubleField vamount;
	private JTextField vnart2;
	private JTextField chq_no;
	private JFormattedTextField chq_dt;
	private JTextField vac_code;
	private JFormattedTextField vou_date;
	private JTextField vac_name;
	private JLabel lblInvoiceNo ;
	private JLabel lblPartyCode; 
	private JLabel lblDispatchDate;
	private JLabel lblInvoiceDate;
	private JLabel lblLrDate;
	private JLabel lblNoOfCases;
	private JLabel lblPartyName;
	private JLabel lblCity;
	private JLabel branch;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private JLabel lblSelectBank;
	private SimpleDateFormat sdf;
	private JList partyList;
	private JScrollPane Partypane;
	private PartyDto prtyDto;
	 
	private JLabel lastNo;
	 
	String partyCd;
	String sdate,edate;
	int slno,vno,option,nop ;
	BookDto bk;
	private JLabel lblinvalid;
	private JIntegerField slipno;
	private JLabel lblBankSlipNp;
	private JPanel panel_3;
	private JLabel label_1;
	private JLabel lblBankSlipNo;
	private JTextField bslipno;
	private JLabel lblSlipDate;
	private JFormattedTextField bslipdt;
	private JPanel panel_4;
	private JScrollPane scrollPane;
	private DefaultTableModel invNoTableModel;
	private JTable invNoTable;
	private Font fontPlan;
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnPrint;
	private JButton btnMiss;
	private JButton btnSave;
	private JButton btnExit;
	private RcpDAO rd ;
	BankVouDAO bankDao;
	private OedDAO odao;
	private JTextField vvou_no;
	private JLabel hintTextLabel;
	private JTextField party_name;
	private RcpDto rcp;
	int vouno;
	private Object s[];
	boolean dataadd=true;
	private String vdbcr;
	public VouAdd(String classnm,String repnm)
	{
		sdf = new SimpleDateFormat("yyyy-MM-dd");  // Date Format
		sdate=sdf.format(loginDt.getFr_date());
		edate=sdf.format(loginDt.getTo_date());
		
		
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
		fontPlan =new Font("Tahoma", Font.PLAIN, 11);
		vdbcr="DR";
		if(repnm.contains("Receipt"))
			vdbcr="CR";
		
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		rd = new RcpDAO();
		odao = new OedDAO();
		bankDao = new BankVouDAO();

		
		PartyDAO pdao = new PartyDAO();
//		final Vector partyNames = (Vector) pdao.getPartyName();
		final Vector partyNames = (Vector) loginDt.getPrtList1();

//		final Map partyMap = (HashMap) pdao.getPartyNameMap();
		final Map partyMap = (HashMap) loginDt.getPrtmap();
		///////////////////////////////////////////////////////
			
		//////////////invoce no table model/////////////////////
		String [] invColName=	{"Vou No.", "Name",""};
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
		//invNoTable.getColumnModel().getColumn(2).setMinWidth(0);  //inv_no/////
		//invNoTable.getColumnModel().getColumn(2).setMaxWidth(0);  //inv_no/////
		invNoTable.getColumnModel().getColumn(2).setMinWidth(0);  //table data/////
		invNoTable.getColumnModel().getColumn(2).setMaxWidth(0);  //tabel data/////
					
			hintTextLabel = new JLabel("Search by Party Name");
			hintTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
			hintTextLabel.setForeground(Color.GRAY);
			hintTextLabel.setBounds(528, 287, 298, 23);
			getContentPane().add(hintTextLabel);
	
			party_name = new JTextField();
			party_name.setBounds(526, 287, 298, 23);
			getContentPane().add(party_name);
			party_name.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent evt) {
					// TODO Auto-generated method stub

					if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
						Partypane.setVisible(false);
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
						party_name.setText("");
						vac_code.requestFocus();
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
						searchFilter(party_name.getText(), partyList, partyNames);
						if (party_name.getText().length() > 0)
							hintTextLabel.setVisible(false);
						else
							hintTextLabel.setVisible(true);
					// evt.consume();
				}

			});

			scrollPane = new JScrollPane(invNoTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(16, 264, 222, 378);
			getContentPane().add(scrollPane);
			
			
		partyList = new JList();
		//partyList.setFont(font);
		partyList.setSelectedIndex(0);
		partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Partypane = new JScrollPane(partyList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Partypane.setBounds(526, 311, 298, 265);
		getContentPane().add(Partypane);
		Partypane.setVisible(false);
		Partypane.setViewportView(partyList);
		bindData(partyList, partyNames);

		// ////////////////////////////////////////////////////////////////
		partyList.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					//int idx = partyList.getSelectedIndex();
					prtyDto = (PartyDto) partyList.getSelectedValue();
					partyCd = prtyDto.getMac_code();
					
					vac_code.setText(partyCd);
					vac_name.setText(prtyDto.getMac_name());
					// evt.consume();
					Partypane.setVisible(false);
					hintTextLabel.setVisible(false);
					party_name.setVisible(false);
					party_name.setText("");
					vnart1.requestFocus();
				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					Partypane.setVisible(false);
					hintTextLabel.setVisible(true);
					party_name.setVisible(true);
					party_name.setText("");

					vac_code.requestFocus();
				}
			}
		});
		// ///////////////////////////////////////////////

		chq_no = new JTextField();
		chq_no.setBounds(474, 365, 98, 23);
		getContentPane().add(chq_no);
		 


		///////////////////////////////////////////////////////
		vac_code = new JTextField();
		vac_code.setBounds(474, 287, 45, 23);
		getContentPane().add(vac_code);

		vac_name = new JTextField();
		vac_name.setEditable(false);
		vac_name.setBounds(526, 287, 298, 23);
		getContentPane().add(vac_name);
		

		vac_code.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (vac_code.getText().toString().trim().equals("")) 
					{
						Partypane.setVisible(true);
						party_name.setVisible(true);
						hintTextLabel.setVisible(true);
						party_name.setText("");
						party_name.requestFocus();
						//partyList.requestFocus();
						//partyList.setSelectedIndex(0);
						//partyList.ensureIndexIsVisible(0);
					} 
					else 
					{
						String getText = vac_code.getText().toString().trim();
						prtyDto = (PartyDto) partyMap.get(getText);
						if (prtyDto == null) 
						{
							Partypane.setVisible(true);
							//partyList.requestFocus();
							//partyList.setSelectedIndex(0);
							party_name.setVisible(true);
							hintTextLabel.setVisible(true);
							party_name.setText("");
							party_name.requestFocus();
							
						} 
						else 
						{
							String pname = prtyDto.getMac_name();
							partyCd = prtyDto.getMac_code();
							
							vac_name.setText(pname);
							vac_code.setText(partyCd);
							Partypane.setVisible(false);
							party_name.setVisible(false);
							hintTextLabel.setVisible(false);
							vnart1.requestFocus();
						}

					}

				}
			}
		});


		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(314, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);


		lblInvoiceNo = new JLabel("Bank Name:");
		lblInvoiceNo.setBounds(345, 326, 98, 20);
		getContentPane().add(lblInvoiceNo);


		vnart1 = new JTextField();
		vnart1.setBounds(474, 326,350, 23);
		getContentPane().add(vnart1);


		lblPartyCode = new JLabel("Voucher Number:");
		lblPartyCode.setBounds(345, 248, 119, 20);
		getContentPane().add(lblPartyCode);

		vou_no = new JTextField();
		vou_no.setEditable(false);
		vou_no.setBounds(474, 248,98, 23);
		getContentPane().add(vou_no);
		vou_no.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
 					clearAll();
					dispose();
					//System.exit(0);
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER && option==2)
				{
					
					vouno = setIntNumber(vou_no.getText().toString());
					 
					if (vouno==0 )
					{
						vou_no.setText("");
						vou_no.requestFocus();
					}

					if (vouno>=setIntNumber(lastNo.getText()))
					{
						alertMessage(VouAdd.this, " Vou No. "+vouno+" is equal/greater than  Last Voucher No  "+setIntNumber(lastNo.getText()));
						vou_no.setText("");
						vou_no.requestFocus();

					}
					else
					{
						int vou= bankDao.checkVouNo(loginDt.getDepo_code(),loginDt.getDiv_code(), loginDt.getFin_year(), bk.getBook_code(),vdbcr,loginDt.getCmp_code(),vouno);


						if (vou==0)
						{
							vou_date.setEnabled(true);
							vac_code.setEnabled(true);
							vnart1.setEnabled(true);
							vnart2.setEnabled(true);
							vamount.setEnabled(true);
							chq_no.setEnabled(true);
							chq_dt.setEnabled(true);
							slipno.setEnabled(true);
							slipno.requestFocus();
							//bslipno.setEnabled(true);
							//bslipdt.setEnabled(true);
						}
						else
						{
							alertMessage(VouAdd.this, "Already Prepared");
						}
					}

					 
					evt.consume();
				}
				
			}
		});
		
		
		lblDispatchDate = new JLabel("Amount");
		lblDispatchDate.setBounds(345, 404, 98, 20);
		getContentPane().add(lblDispatchDate);

		lblInvoiceDate = new JLabel("Narration:");
		lblInvoiceDate.setBounds(347, 443, 98, 20);
		getContentPane().add(lblInvoiceDate);

	 	vamount = new JDoubleField();
		vamount.setBounds(474, 404, 98, 23);
		vamount.setHorizontalAlignment(SwingConstants.RIGHT);
		vamount.setMaxLength(11); //Set maximum length             
		vamount.setPrecision(2); //Set precision (2 in your case)              
		vamount.setAllowNegative(false); //Set false to disable negatives
		getContentPane().add(vamount);
		vamount.setSelectionStart(0);
		vamount.setText("0.00");
		
		vnart2 = new JTextField();
		vnart2.setBounds(474, 443, 350, 23);
		getContentPane().add(vnart2);

		lblLrDate = new JLabel("Chq No.:");
		lblLrDate.setBounds(345, 365, 98, 20);
		getContentPane().add(lblLrDate);
				
		
		lblNoOfCases = new JLabel("Chq Date:");
		lblNoOfCases.setBounds(629, 365, 70, 20);
		getContentPane().add(lblNoOfCases);

		lblPartyName = new JLabel("Date:");
		lblPartyName.setBounds(629, 248, 42, 20);
		getContentPane().add(lblPartyName);

		lblCity = new JLabel("Account Code:");
		lblCity.setBounds(345, 287, 98, 20);
		getContentPane().add(lblCity);



		
		lblBankSlipNp = new JLabel("Bank Slip No.:");
		lblBankSlipNp.setBounds(347, 209, 98, 20);
		getContentPane().add(lblBankSlipNp);
		
		slipno = new JIntegerField();
		slipno.setBounds(474, 209, 98, 23);
		slipno.setHorizontalAlignment(SwingConstants.LEFT);
		slipno.setSelectionStart(0);
		getContentPane().add(slipno);
		

		 
		
		
		vou_date = new JFormattedTextField(sdf);
		vou_date.setBounds(706, 248, 118, 23);
		checkDate(vou_date);
		getContentPane().add(vou_date);
		
		lblinvalid = new JLabel();
		lblinvalid.setBounds(690, 188, 118, 23);
		getContentPane().add(lblinvalid);
		 		
		


//		JLabel promleb = new JLabel(promLogo);
//		promleb.setBounds(10, 670, 35, 35);
//		getContentPane().add(promleb);

//		JLabel arisleb = new JLabel(arisLogo);
//		arisleb.setBounds(10, 11, 35, 37);
//		getContentPane().add(arisleb);

		//branch.setText(loginDt.getBrnnm());
		//branch.setBounds(400, 35, 395, 22);
		//getContentPane().add(branch);
 

		branch = new JLabel(loginDt.getBrnnm());
		branch.setForeground(Color.BLACK);
		branch.setFont(new Font("Tahoma", Font.BOLD, 11));
		branch.setBounds(10, 86, 195, 14);
		getContentPane().add(branch);


		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(277, 656, 651, 48);
		getContentPane().add(panel_2);

		lblDispatchEntry = new JLabel(repnm);
		lblDispatchEntry.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDispatchEntry.setBounds(419, 90, 251, 22);
		getContentPane().add(lblDispatchEntry);


		label_1 = new JLabel("Last No:");
		label_1.setForeground(Color.RED);
		label_1.setBounds(780, 209, 60, 20);
		getContentPane().add(label_1);
		
		lastNo = new JLabel("");
		lastNo.setForeground(Color.RED);
		lastNo.setBounds(850, 209, 90, 20);
		getContentPane().add(lastNo);
//		lastNo.setText(String.valueOf(getLastNo()));
		


		///////////////////////////////////////////////////////////////////////////////////////////////
		KeyListener keyListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) 
				{

					JTextField textField = (JTextField) keyEvent.getSource();
					int id = setIntNumber(textField.getName());
					
					switch (id) 
					{
					
					//case 0:
					//	slipno.requestFocus();
					//	break;
					case 1:
						vou_date.requestFocus();
						vou_date.setCaretPosition(0);
						break;

					case 2:
						String date = vou_date.getText();

						if (isValidDate(date)) {
							lblinvalid.setVisible(false);
							// Check the Date Range for the Financial year
							if (isValidRange(date, loginDt.getFr_date(),	loginDt.getTo_date())) {
								chq_dt.setText(date);
								vac_code.requestFocus();
							} else {
								JOptionPane.showMessageDialog(
										VouAdd.this,
										"Date range should be in between: "
												+ sdf.format((Date) s[2])
												+ " to "
												+ sdf.format(loginDt
														.getTo_date()),
										"Date Range",
										JOptionPane.INFORMATION_MESSAGE);
								vou_date.setValue(null);
								vou_date.setValue(sdf.format(s[2]));
								checkDate(vou_date);
								vou_date.requestFocus();
								vou_date.setCaretPosition(0);

							}

						} else {
							lblinvalid.setText("invalid date");
							lblinvalid.setForeground(Color.red);
							lblinvalid.setVisible(true);
							vou_date.setValue(null);
							vou_date.setValue(sdf.format(s[2]));
							checkDate(vou_date);
							vou_date.requestFocus();
							vou_date.setCaretPosition(0);
						}
						break;

					case 3:
						vnart1.requestFocus();
						vnart1.setSelectionStart(0);
						break;
					case 4:
						 chq_no.requestFocus();
						 chq_no.setSelectionStart(0);
						break;
					case 5:
						 chq_dt.requestFocus();
						 chq_dt.setSelectionStart(0);
						break;
					case 6:
						date=chq_dt.getText();
						if(isValidDate(date))
						{
							vamount.requestFocus();
							vamount.setSelectionStart(0);
						}
						else
						{
							 chq_dt.requestFocus();
							 chq_dt.setSelectionStart(0);
						}
						break;
						
					case 7:
						vamount.setText(formatter.format(setDoubleNumber(vamount.getText())));
						vnart2.requestFocus();
						vnart2.setSelectionStart(0);
						break;
					case 8:
						btnSave.requestFocus();
						btnSave.setBackground(Color.BLUE);

						break;
					}
					
				}

				if (key == KeyEvent.VK_ESCAPE) 
				{
					option=0;
					clearAll();
					btnAdd.setEnabled(true);
					btnMiss.setEnabled(true);
					btnDelete.setEnabled(true);
					btnPrint.setEnabled(true);
					btnSave.setEnabled(false);
					vou_date.setEnabled(false);
					vac_code.setEnabled(false);
					vnart1.setEnabled(false);
					vnart2.setEnabled(false);
					vamount.setEnabled(false);
					chq_no.setEnabled(false);
					chq_dt.setEnabled(false);
					slipno.setEnabled(false);
					bslipno.setEnabled(true);
					bslipdt.setEnabled(true);
					dispose();
					 
				}
				
				 
				
				 
			}
			 

		};

		// ///////////////////////////////////////////////

		


		lblSelectBank = new JLabel("Select Bank:");
		lblSelectBank.setBounds(347, 151, 98, 20);
		getContentPane().add(lblSelectBank);

		
		bank = new JComboBox(new Vector(loginDt.getBooklist()));
		bank.setActionCommand("bank");
		bank.setBounds(474, 151, 217, 23);
		getContentPane().add(bank);
		
	 

		
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(277, 137, 651, 48);
		getContentPane().add(panel);
		
		chq_dt = new JFormattedTextField(sdf);
		chq_dt.setBounds(706, 365, 118, 23);
		checkDate(chq_dt);
		getContentPane().add(chq_dt);

		
	
		// ///////////////////////////////////////////////
		vou_no.setName("0");
		slipno.setName("1");
		//vou_no.setName("1");
		vou_date.setName("2");
		vac_code.setName("3");
		vnart1.setName("4");
		chq_no.setName("5");
		chq_dt.setName("6");
		vamount.setName("7");
		vnart2.setName("8");
		
		vou_date.setEnabled(false);
		vac_code.setEnabled(false);
		vnart1.setEnabled(false);
		vnart2.setEnabled(false);
		vamount.setEnabled(false);
		chq_no.setEnabled(false);
		chq_dt.setEnabled(false);
		slipno.setEnabled(false);
		
		slipno.addKeyListener(keyListener); 
		vou_no.addKeyListener(keyListener); 
		vou_date.addKeyListener(keyListener);
		vnart1.addKeyListener(keyListener);
		vnart2.addKeyListener(keyListener);
		vamount.addKeyListener(keyListener);
		chq_no.addKeyListener(keyListener);
		chq_dt.addKeyListener(keyListener);
		
		
		
		
		
		 
		
		panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBackground(new Color(51, 102, 204));
		panel_3.setBounds(10, 137, 235, 28);
		getContentPane().add(panel_3);
		
		label_1 = new JLabel("Search");
		label_1.setForeground(Color.WHITE);
		panel_3.add(label_1);
		
		lblBankSlipNo = new JLabel("Bank Slip No.");
		lblBankSlipNo.setBounds(16, 175, 83, 20);
		getContentPane().add(lblBankSlipNo);
		
		bslipno = new JTextField();
		bslipno.setBounds(106, 175, 132, 22);
		getContentPane().add(bslipno);
		bslipno.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					clearAll();
					dispose();
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{

					try
					{
						int slno=setIntNumber(bslipno.getText().trim());
						option=0;
						bk = (BookDto) bank.getSelectedItem();

							Vector data = (Vector) rd.getVouList(loginDt.getFin_year(),loginDt.getDiv_code(),loginDt.getDepo_code(),bk.getBook_code(), slno,null,vdbcr);
							if(data!=null)
							{
								fillInvTable(data);
								invNoTable.requestFocus();
								invNoTable.changeSelection(0,0, false, false);
								invNoTable.editCellAt(0, 0);
							}
							else
							{
								bslipdt.setValue(null);
								checkDate(bslipdt);
								bslipdt.requestFocus();
							}						
						
					}
					catch(Exception e)
					{
						System.out.println("yaha kuch errroa haia dekho e" +e);
					}
					evt.consume();
				}
				
			}
		});
		
		
		
		lblSlipDate = new JLabel("Slip Date:");
		lblSlipDate.setBounds(16, 205, 83, 20);
		getContentPane().add(lblSlipDate);
		
		bslipdt = new JFormattedTextField(sdf);
		bslipdt.setBounds(106, 205, 132, 22);
		checkDate(bslipdt);
		getContentPane().add(bslipdt);
		bslipdt.addKeyListener(new KeyAdapter() 
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
						String date=bslipdt.getText();
						option=0;
						bk = (BookDto) bank.getSelectedItem();

						 
						if(isValidDate(date))
						{
							Vector data = (Vector) rd.getVouList(loginDt.getFin_year(),loginDt.getDiv_code(),loginDt.getDepo_code(),bk.getBook_code(), 0,date,vdbcr);
							if(data!=null)
							{
								fillInvTable(data);
								invNoTable.requestFocus();
								invNoTable.changeSelection(0,0, false, false);
								invNoTable.editCellAt(0, 0);
							}
								
						}
						else
						{
							bslipdt.setValue(null);
							checkDate(bslipdt);
							bslipdt.requestFocus();
						}						
						
					}
					catch(Exception e)
					{
						System.out.println("yaha kuch errroa haia dekho e" +e);
					}
					evt.consume();
				}
				
			}
		});
		
		
		
		
		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setBounds(276, 616, 86, 30);
		getContentPane().add(btnAdd);
		
		btnDelete = new JButton("Delete");
		btnDelete.setActionCommand("Delete");
		btnDelete.setBounds(372, 616, 86, 30);
		getContentPane().add(btnDelete);
		
		btnPrint = new JButton("Print");
		btnPrint.setActionCommand("Print");
		btnPrint.setBounds(468, 616, 86, 30);
		getContentPane().add(btnPrint);
		
		btnMiss = new JButton("Add Missing Voucher");
		btnMiss.setActionCommand("Miss");
		btnMiss.setBounds(564, 616, 170, 30);
		getContentPane().add(btnMiss);
		
		btnSave = new JButton("Save");
		btnSave.setActionCommand("Save");
		btnSave.setBounds(744, 616, 86, 30);
		getContentPane().add(btnSave);
		btnSave.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{

					try 
					{
						if(setDoubleNumber(vamount.getText())==0.00)
						{
							alertMessage(VouAdd.this, "Amount can not be zero");
							vamount.requestFocus();
						}
						else
							saveData(false);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}

				}
				
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					btnExit.requestFocus();
					btnExit.setBackground(Color.blue);
					btnSave.setBackground(null);
					 
				}

			}
		});
		
		btnExit = new JButton("Exit");
		btnExit.setActionCommand("Exit");
		btnExit.setBounds(840, 616, 86, 30);
		getContentPane().add(btnExit);
		
			//chq_dt.addKeyListener(keyListener);

		btnExit.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					System.out.println("option ki valur exit per"+ option );
					if(setDoubleNumber(vamount.getText())==0.00)
					{
						alertMessage(VouAdd.this, "Amount can not be zero");
						vamount.requestFocus();
					}
					else
						saveData(true);
					
					option=0;
					clearAll();
				    dispose();
					vou_date.setEnabled(false);
					vac_code.setEnabled(false);
					vnart1.setEnabled(false);
					vnart2.setEnabled(false);
					vamount.setEnabled(false);
					chq_no.setEnabled(false);
					chq_dt.setEnabled(false);
					slipno.setEnabled(false);
					bslipno.setEnabled(true);
					bslipdt.setEnabled(true);
					btnAdd.setEnabled(true);

					
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					btnSave.requestFocus();
					btnSave.setBackground(Color.blue);
					btnExit.setBackground(null);
					 
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
					rcp = (RcpDto) invNoTable.getValueAt(row, 2);
					fillData(rcp);
					evt.consume();
				}
				
			}
			
			public void keyReleased(KeyEvent evt) 
			{
				int row = invNoTable.getSelectedRow();
				if(evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN)
				{
					rcp = (RcpDto) invNoTable.getValueAt(row, 2);
					fillData(rcp);
					evt.consume();
				}
				
			}
		});
		
		
		invNoTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e){
				
				 
				
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent e) 
			{
				int row = invNoTable.getSelectedRow();
				rcp = (RcpDto) invNoTable.getValueAt(row, 2);
				fillData(rcp);
			}
		});			

		btnSave.setEnabled(false);
		
		JLabel lblVoucherNo = new JLabel("Voucher No.");
		lblVoucherNo.setBounds(16, 234, 83, 20);
		getContentPane().add(lblVoucherNo);
		
		vvou_no = new JTextField();
		vvou_no.setBounds(106, 234, 132, 22);
		getContentPane().add(vvou_no);
		vvou_no.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					clearAll();
					dispose();
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{

					try
					{
						int vvno=setIntNumber(vvou_no.getText().trim());
						option=0;
					 
						bk = (BookDto) bank.getSelectedItem();
						 
						 rcp = (RcpDto) rd.getVouList(loginDt.getFin_year(),loginDt.getDiv_code(),loginDt.getDepo_code(),bk.getBook_code(), vvno,vdbcr);
							if(rcp!=null && vvno<= rcp.getVou_no())
							{
								
								fillData(rcp);
								btnSave.setEnabled(true);
								btnDelete.setEnabled(true);
								btnAdd.setEnabled(true);
								btnMiss.setEnabled(true);
								btnPrint.setEnabled(true);
								bank.setEnabled(false);
								vou_date.setEnabled(true);
								vac_code.setEnabled(true);
								vnart1.setEnabled(true);
								vnart2.setEnabled(true);
								vamount.setEnabled(true);
								chq_no.setEnabled(true);
								chq_dt.setEnabled(true);
								slipno.setEnabled(true);
								slipno.requestFocus();
							}
							else
							{
								JOptionPane.showMessageDialog(VouAdd.this,"Voucher No. Not Found!! ","Error",JOptionPane.INFORMATION_MESSAGE);
								vvou_no.requestFocus();
								vvou_no.setText("");
							}						
						
					}
					catch(Exception e)
					{
						System.out.println("yaha kuch errroa haia dekho e" +e);
					}
					evt.consume();
				}
				
			}
		});
		
		

		panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(10, 136, 235, 511);
		getContentPane().add(panel_4);
		
/*		lblRecordNo = new JLabel("Record No.");
		lblRecordNo.setBounds(629, 206, 70, 20);
		getContentPane().add(lblRecordNo);
		
		recno = new JFormattedTextField((Format) null);
		recno.setEnabled(false);
		recno.setBounds(706, 206, 118, 23);
		getContentPane().add(recno);*/
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(277, 188, 651, 299);
		getContentPane().add(panel_1);
		bank.addActionListener(this);
		btnAdd.addActionListener(this);
		btnDelete.addActionListener(this);
		btnPrint.addActionListener(this);
		btnMiss.addActionListener(this);
		btnSave.addActionListener(this);
		btnExit.addActionListener(this);
		
	}

	
	public void clearAll()
	{

		
		vac_code.setText("");
		vac_name.setText("");
		vnart1.setText("");
		vnart2.setText("");
		vamount.setText("");
		chq_no.setText("");
		vvou_no.setText("");
		chq_dt.setValue(null);
		checkDate(chq_dt);
		bslipno.setText("");
		bslipdt.setValue(null);
		checkDate(bslipdt);
		vamount.setEditable(true);
		btnAdd.setEnabled(false);
		btnPrint.setEnabled(false);
		btnMiss.setEnabled(true);
		btnSave.setBackground(null);
		btnExit.setBackground(null);
		bank.setEnabled(true);
		party_name.setText("");
		party_name.setVisible(true);
		hintTextLabel.setVisible(true);
		nop=0;
		if(option==1)
			option=1;
		else
		{
			option=0;
			vou_no.setText("");
			vou_date.setValue(null);
			checkDate(vou_date);
			slipno.setText("");
//			bank.setSelectedIndex(0);

		}

		invNoTableModel.getDataVector().removeAllElements();
		invNoTableModel.fireTableDataChanged();
//		lastNo.setText(String.valueOf(getLastNo()));

	}
	

	
	public RcpDto rcpAdd() 
	{
		RcpDto rcp = null;

		try {
					rcp = new RcpDto();
					rcp.setDiv_code(loginDt.getDiv_code());
					rcp.setVdepo_code(loginDt.getDepo_code());
					rcp.setVbook_cd(bk.getBook_code());
//					rcp.setVou_no(setIntNumber(vou_no.getText()));
					rcp.setVou_no(vno);
					if (option==2)
						rcp.setVou_no(vouno);

						
					rcp.setVou_yr(loginDt.getInv_yr());
					rcp.setVou_date(formatDate(vou_date.getText()));
					rcp.setVac_code(vac_code.getText());
					rcp.setVnart1(vnart1.getText());
					System.out.println("VNART2 "+vnart2.getText());
					rcp.setVnart1(vnart2.getText());
					rcp.setChq_no(chq_no.getText());
					rcp.setChq_date(formatDate(chq_dt.getText()));
					
					rcp.setVamount(setDoubleNumber(vamount.getText()));
					rcp.setVdbcr(vdbcr);
					rcp.setFin_year(loginDt.getFin_year());
					rcp.setMnth_code(getMonthCode(vou_date.getText()));
					rcp.setCreated_by(loginDt.getLogin_id());
					rcp.setCreated_date(new Date());
					rcp.setVreg_cd(prtyDto.getMregion_cd());
					rcp.setVdist_cd(prtyDto.getMdist_cd());
					rcp.setMkt_year(loginDt.getMkt_year());
					rcp.setSerialno(setIntNumber(slipno.getText()));
					
		} catch (Exception e) {
			System.out.println(e);
		}
		    return rcp;

	}
	
	public RcpDto rcpUpdate() 
	{
		RcpDto rcp = null;
		

		try {
					rcp = new RcpDto();
					rcp.setDiv_code(loginDt.getDiv_code());
					rcp.setVdepo_code(loginDt.getDepo_code());
					rcp.setVbook_cd(bk.getBook_code());
					rcp.setVou_no(setIntNumber(vou_no.getText()));
					rcp.setVou_date(formatDate(vou_date.getText()));
					rcp.setVac_code(vac_code.getText());
					rcp.setVnart1(vnart1.getText() );
					rcp.setVnart2(vnart2.getText() );
					rcp.setChq_no(chq_no.getText());
					rcp.setChq_date(formatDate(chq_dt.getText()));
					
					rcp.setVamount(setDoubleNumber(vamount.getText()));
					rcp.setVdbcr(vdbcr);

					rcp.setFin_year(loginDt.getFin_year());
					rcp.setMnth_code(getMonthCode(vou_date.getText()));
					rcp.setModified_by(loginDt.getLogin_id());
					rcp.setModified_date(new Date());
					rcp.setVreg_cd(prtyDto.getMregion_cd());
					rcp.setVdist_cd(prtyDto.getMdist_cd());
					rcp.setSerialno(setIntNumber(slipno.getText()));
					
		} catch (Exception e) {
			System.out.println("yaha phir se error hai updatemeein "+e);
		}
		    return rcp;

	}
	
	
	public void printSaveData() 
	{
	 
			slno=setIntNumber(slipno.getText().trim());
			rd.updateLed(rcpUpdate());
			invNoTableModel.getDataVector().removeAllElements();
			invNoTableModel.fireTableDataChanged();
			bslipno.setEnabled(true);
			bslipdt.setEnabled(true);
			bslipno.requestFocus();
		    btnSave.setEnabled(false);
		    slipno.setText("");
			vou_no.setText("");
			vou_date.setValue(null);
			checkDate(vou_date);
		 
	}
	
	public void saveData(boolean exit) 
	{
		int dialogResult=1;
		if(exit)
		{
			int dialogButton = JOptionPane.YES_NO_OPTION;
			dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to save the changes?", "Save Changes",dialogButton);
		}
		else
		{
			dialogResult=0;
		}
		
		if(dialogResult==0)
		{

			if ((option==1 || option==2))
			{
				
				rd.addLed(rcpAdd());
//				vno=getLastNo();
				vno=1;
			
				lastNo.setText(String.valueOf(vno));
				vno=vno+1;

				//				vou_no.setText(String.valueOf(vno));
				slno=setIntNumber(slipno.getText().trim());
				slipno.setText(String.valueOf(slno));
				vou_date.requestFocus();
				vou_date.setCaretPosition(0);
				bslipno.setEnabled(true);
				bslipdt.setEnabled(true);
				vou_no.setEditable(true);
//				  nop++;
//				  recno.setText(String.valueOf(nop));
				if(option==2)
					vvou_no.requestFocus();

			}

			if (option==0)
			{
				slno=setIntNumber(slipno.getText().trim());
				rd.updateLed(rcpUpdate());
				invNoTableModel.getDataVector().removeAllElements();
				invNoTableModel.fireTableDataChanged();
				bslipno.setEnabled(true);
				bslipdt.setEnabled(true);
				bslipno.requestFocus();
			}
		}
		
		btnAdd.setEnabled(true);
		btnMiss.setEnabled(true);
		btnDelete.setEnabled(true);
		btnPrint.setEnabled(true);
		vou_no.setEditable(false);
		invNoTable.setEnabled(true);
		vou_no.setEnabled(true);
		vou_date.setEnabled(true);
		vou_date.setCaretPosition(0);
//		option=0;
		clearAll();
		 
	}
	
	
	public void fillInvTable(Vector data)
	{
		invNoTableModel.getDataVector().removeAllElements();
		btnSave.setEnabled(true);
		btnDelete.setEnabled(true);
		btnAdd.setEnabled(true);
		btnMiss.setEnabled(true);
		btnPrint.setEnabled(true);
		bank.setEnabled(false);
		vou_date.setEnabled(true);
		vac_code.setEnabled(true);
		vnart1.setEnabled(true);
		vnart2.setEnabled(true);
		vamount.setEnabled(true);
		chq_no.setEnabled(true);
		chq_dt.setEnabled(true);
		slipno.setEnabled(true);
		
		slipno.setText("");
		vou_no.setText("");
		vou_date.setValue(null);
		checkDate(vou_date);
		
		
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
           else
           {
        	    rcp = (RcpDto) invNoTable.getValueAt(0, 2);
				fillData(rcp);
           }
         invNoTable.setEnabled(true); 
		
	}
	
	
	
	public void fillData(RcpDto r)
	{
		vac_code.setText(r.getVac_code());
		vac_name.setText(r.getParty_name());
		party_name.setText(r.getParty_name());
		vou_no.setText(String.valueOf(r.getVou_no()));
		vou_date.setText(sdf.format(r.getVou_date()));
		vnart1.setText(r.getVnart1());
		vnart2.setText(r.getVnart2());
		chq_no.setText(r.getChq_no());
		chq_dt.setText(formatDate(r.getChq_date()));
		vamount.setText(formatter.format(r.getVamount()));
		slipno.setText(String.valueOf(r.getSerialno()));
		
		//vamount.setEditable(false);
		prtyDto = (PartyDto) loginDt.getPrtmap().get(r.getVac_code());
		
		/*btnSave.setEnabled(true);
		btnDelete.setEnabled(true);
		btnAdd.setEnabled(false);
		btnMiss.setEnabled(false);
		bank.setEnabled(false);
		*/
		vac_name.setCaretPosition(0);
		vou_date.setEnabled(true);
		vac_code.setEnabled(true);
		vnart1.setEnabled(true);
		vnart2.setEnabled(true);
		vamount.setEnabled(true);
		chq_no.setEnabled(true);
		chq_dt.setEnabled(true);
		slipno.setEnabled(true);
		hintTextLabel.setVisible(false);
		
	}	
	
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		if(e.getActionCommand().equalsIgnoreCase("add") )
		{
			
			vou_date.setEnabled(true);
			vac_code.setEnabled(true);
			vac_name.setText("");
			vnart1.setEnabled(true);
			vnart2.setEnabled(true);
			vamount.setEnabled(true);
			chq_no.setEnabled(true);
			chq_dt.setEnabled(true);
			slipno.setEnabled(true);
			
			btnAdd.setEnabled(false);
			btnDelete.setEnabled(false);
			btnMiss.setEnabled(false);
			btnPrint.setEnabled(false);
			btnSave.setEnabled(true);
			clearAll(); 
			option=1;
			invNoTable.setEnabled(false);
			bslipno.setEnabled(true);
			bslipdt.setEnabled(true);
			invNoTableModel.getDataVector().removeAllElements();
			invNoTableModel.fireTableDataChanged();
			if (option==1 )
			{
				bk = (BookDto) bank.getSelectedItem();
//				s = odao.getVounoNew(bk.getBook_code(), loginDt.getDiv_code(), loginDt.getDepo_code(), edate,loginDt.getFin_year(),vdbcr);
				vno=setIntNumber(s[0].toString());
				slno=setIntNumber(s[1].toString())+1;
				vno=vno+1;
				if((Date) s[2]==null)
				{
					s[2]=(Date) loginDt.getFr_date();
				}
				vou_date.setText(sdf.format(s[2]));

//				vou_no.setText(String.valueOf(vno));	
			    slipno.setText(String.valueOf(slno));
			    slipno.requestFocus();
				lastNo.setText(String.valueOf(s[0]));
			}
			
			vou_date.requestFocus();
			vou_date.setCaretPosition(0);
			
		
		}
		
		if(e.getActionCommand().equalsIgnoreCase("miss"))
		{
			
			btnAdd.setEnabled(false);
			btnMiss.setEnabled(false);
			btnDelete.setEnabled(false);
			btnPrint.setEnabled(false);
			btnSave.setEnabled(true);
			clearAll(); 
			invNoTable.setEnabled(false);
			bslipno.setEnabled(false);
			bslipdt.setEnabled(false);
			
			vou_no.setEditable(true);
			vou_no.requestFocus();
			option=2;
			s[2]=(Date) loginDt.getFr_date();

		
		}		
		
		if(e.getActionCommand().equalsIgnoreCase("print"))
		{
			printSaveData();
			//slno=setIntNumber(bslipno.getText().trim());
		//	BankSlipPrint bp = new BankSlipPrint(loginDt.getFin_year(),loginDt.getDepo_code(),loginDt.getDiv_code(),slno,bk.getBook_code(),loginDt.getBtnnm(),loginDt.getDrvnm(), loginDt.getPrinternm() );
			clearAll();
		}

		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			if(setDoubleNumber(vamount.getText())==0.00)
			{
				alertMessage(VouAdd.this, "Amount can not be zero");
				vamount.requestFocus();
			}
			else
				saveData(false);
			
		}
		if(e.getActionCommand().equalsIgnoreCase("delete"))
		{
			int answer = 0;
			  answer = JOptionPane.showConfirmDialog(VouAdd.this, "Are You Sure : ");
			    if (answer == JOptionPane.YES_OPTION) {

				slno=setIntNumber(slipno.getText().trim());
				rd.deleteLed(rcpUpdate());
				invNoTableModel.getDataVector().removeAllElements();
				invNoTableModel.fireTableDataChanged();
				clearAll();
				bk = (BookDto) bank.getSelectedItem();
				s = odao.getVouno(bk.getBook_code(), loginDt.getDiv_code(), loginDt.getDepo_code(), edate,loginDt.getFin_year(),vdbcr);
//				lastNo.setText(String.valueOf(s[0]));

				bslipno.setEnabled(true);
				bslipdt.setEnabled(true);
				bslipno.requestFocus();
			    }
		}
		
		if(e.getActionCommand().equalsIgnoreCase("bank"))
		{
			
			if (option==1)
			{
				bk = (BookDto) bank.getSelectedItem();
				s = odao.getVouno(bk.getBook_code(), loginDt.getDiv_code(), loginDt.getDepo_code(), edate,loginDt.getFin_year(),vdbcr);
				vno=setIntNumber(s[0].toString());
				slno=setIntNumber(s[1].toString())+1;
				vno=vno+1;
//				vou_no.setText(String.valueOf(vno));	
			    slipno.setText(String.valueOf(slno));
			    slipno.requestFocus();
//				lastNo.setText(String.valueOf(s[0]));

			}

			
			if (option==2)
			{
				bk = (BookDto) bank.getSelectedItem();
				s = odao.getVouno(bk.getBook_code(), loginDt.getDiv_code(), loginDt.getDepo_code(), edate,loginDt.getFin_year(),vdbcr);
				vno=setIntNumber(s[0].toString());
			    slipno.requestFocus();
//				lastNo.setText(String.valueOf(s[0]));

			}

			
		}
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			clearAll();
			vou_date.setEnabled(false);
			vac_code.setEnabled(false);
			vnart1.setEnabled(false);
			vnart2.setEnabled(false);
			vamount.setEnabled(false);
			chq_no.setEnabled(false);
			chq_dt.setEnabled(false);
			slipno.setEnabled(false);
			bslipno.setEnabled(true);
			bslipdt.setEnabled(true);
			btnAdd.setEnabled(true);
			btnSave.setEnabled(false);
			dispose();
		}

	}
	
	
	public int getLastNo1()
	{
		bk=(BookDto) bank.getSelectedItem();
//		s = odao.getVounoNew(bk.getBook_code(), loginDt.getDiv_code(), loginDt.getDepo_code(), edate,loginDt.getFin_year(),vdbcr);
//		if((Date) s[2]==null)
//		{
//			s[2]=(Date) loginDt.getFr_date();
//		}
//		else
//			s[2]=(Date) s[2];
//		 
//		vou_date.setText(sdf.format(s[2]));
		return setIntNumber(s[0].toString());
	}

	public void setVisible(boolean bx)
	{
		System.out.println("setVisible is called"+ vdbcr);
		//setting last no.
	

			dataadd=true;
		///
		if(dataadd)
			btnAdd.setEnabled(true);
		else
			btnAdd.setEnabled(false);
			
		option=0;
		Vector bankNames = new Vector(loginDt.getBooklist());
		
		bank.removeAllItems();
		BookDto bk=null;
		for (int i=0; i<bankNames.size();i++)
		{
			
			 bk = (BookDto) bankNames.get(i);
			 bank.addItem(bk);
			 
		}

	 
		bk=(BookDto) bank.getSelectedItem();		
//		lastNo.setText(String.valueOf(getLastNo()));
		
		super.setVisible(true);
	}
	

}


