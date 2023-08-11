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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.BankVouDAO;
import com.coldstorage.dao.InvPrintDAO;
import com.coldstorage.dao.OedDAO;
import com.coldstorage.dao.RcpDAO;
import com.coldstorage.dao.TransportDAO;
import com.coldstorage.dto.BookDto;
import com.coldstorage.dto.InvViewDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.print.GenerateReceipt;
import com.coldstorage.print.OutStandingPdf;
import com.coldstorage.util.JDoubleField;
import com.coldstorage.util.TextField;


public class CashVouAdd extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	Font font,fontHindi;
	private JTextField vou_no;
	private JDoubleField vamount;
	private JTextField vnart2;
	private JTextField vac_code;
	private JFormattedTextField vou_date;
	private JTextField vac_name;
	private JLabel lblPartyCode; 
	private JLabel lblDispatchDate;
	private JLabel lblInvoiceDate;
	private JLabel lblPartyName;
	private JLabel lblCity;
	private JLabel branch;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private SimpleDateFormat sdf;
	private JList partyList;
	private JScrollPane Partypane;
	
	private TransportDto prtyDto; 
	InvPrintDAO idao;
	TransportDAO pdao ;
	private JLabel lastNo;
	 
	int partyCd;
	String sdate,edate;
	int slno,vno,option,nop ;
	private JLabel lblinvalid;
	private JPanel panel_3;
	private JLabel label_1;
	private JLabel lblSlipDate;
//	private JFormattedTextField bslipdt;
	private JPanel panel_4;
	private JScrollPane scrollPane;
	private DefaultTableModel invNoTableModel;
	private JTable invNoTable;
	private Font fontPlan;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnPrint;
	private JButton btnMiss;
	private JButton btnSave;
	private JButton btnExit;
	private RcpDAO rd ;
	BankVouDAO bankDao;
	private OedDAO odao;
	private JLabel hintTextLabel;
	private JTextField party_name;

	private JTextField vvou_no;
	private RcpDto rcp;
	InvViewDto rcp1;
	int vouno;
	private Object s[];
	boolean dataadd=true;
	private String vdbcr;
	private Vector partyNames;
	private HashMap partyMap;
	private JLabel lblReceivedby,lblPaidby;
	private JTextField received,paid_by;
	private JLabel lblMobileNo;
	private JTextField mobile_no;
	private JLabel lblRemark;
	private JTextField remark;
	private JLabel lblRent;
	private JTextField rentamt;
	private JLabel lblType;
	private JComboBox mtype1;
	public CashVouAdd(String classnm,String repnm)
	{
		sdf = new SimpleDateFormat("yyyy-MM-dd");  // Date Format
		sdate=sdf.format(loginDt.getFr_date());
		edate=sdf.format(loginDt.getTo_date());
		
		
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
		fontPlan =new Font("Tahoma", Font.BOLD, 16);
		fontHindi = new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14);
		vdbcr="CR";
		
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
		
		rd = new RcpDAO();
		odao = new OedDAO();
		bankDao = new BankVouDAO();
		pdao = new TransportDAO();
		idao = new InvPrintDAO();
		//PartyDAO pdao = new PartyDAO();
		partyNames = (Vector) pdao.getTransportList(loginDt.getDepo_code(),loginDt.getDiv_code() );
		partyMap = (HashMap) pdao.getPartyNameMap(loginDt.getDepo_code(),loginDt.getDiv_code());

		
		

		
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
		hintTextLabel.setBounds(562, 287, 298, 23);
		getContentPane().add(hintTextLabel);

		party_name = new JTextField();
		party_name.setBounds(560, 287, 298, 23);
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

/*			scrollPane = new JScrollPane(invNoTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(16, 236, 222, 406);
			getContentPane().add(scrollPane);
*/
		partyList = new JList();
		//partyList.setFont(font);
		partyList.setSelectedIndex(0);
		partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Partypane = new JScrollPane(partyList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Partypane.setBounds(560, 311, 298, 265);
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
					prtyDto = (TransportDto) partyList.getSelectedValue();
					partyCd = prtyDto.getAccount_no();
					
					vac_code.setText(String.valueOf(partyCd));
					rcp1 =  idao.getInvDetailParty(partyCd,"",loginDt.getFin_year(),1);
					if(rcp1!=null)
						rentamt.setText(formatter.format(rcp1.getCn_val()));
					else
						rentamt.setText("0.00");
					btnMiss.setEnabled(true);
					vac_name.setText(prtyDto.getMac_name_hindi());
					paid_by.requestFocus();
					paid_by.setSelectionStart(0);
					// evt.consume();
					Partypane.setVisible(false);
					hintTextLabel.setVisible(false);
					party_name.setVisible(false);
					party_name.setText("");

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
		 


		///////////////////////////////////////////////////////

		lblCity = new JLabel("Account Code:");
		lblCity.setBounds(345, 287, 110, 20);
		getContentPane().add(lblCity);

		
		vac_code = new JTextField();
		vac_code.setBounds(474, 287, 70, 23);
		getContentPane().add(vac_code);

		vac_name = new JTextField();
		vac_name.setEditable(false);
		vac_name.setFont(fontHindi);
		vac_name.setBounds(560, 287, 298, 23);
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
						prtyDto = (TransportDto) partyMap.get(getText);
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
							String pname = prtyDto.getMac_name_hindi();
							partyCd = prtyDto.getAccount_no();
							vac_name.setText(pname);
							vac_code.setText(String.valueOf(partyCd));
							rcp1 =  idao.getInvDetailParty(partyCd,"",loginDt.getFin_year(),1);
							if(rcp1!=null)
								rentamt.setText(formatter.format(rcp1.getCn_val()));
							else
								rentamt.setText("0.00");
							btnMiss.setEnabled(true);
							party_name.setVisible(false);
							hintTextLabel.setVisible(false);
							Partypane.setVisible(false);
							paid_by.requestFocus();
						}

					}

				}
			}
		});


/*		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(419, 150, 577, 15);
		getContentPane().add(lblFinancialAccountingYear);

*/		

		lblPartyCode = new JLabel("Voucher Number:");
		lblPartyCode.setBounds(345, 248, 130, 20);
		getContentPane().add(lblPartyCode);

		vou_no = new JTextField();
		vou_no.setEditable(false);
		vou_no.setBounds(474, 248,98, 23);
		getContentPane().add(vou_no);
		vou_no.addKeyListener(new KeyAdapter() 
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
						int vvno=setIntNumber(vou_no.getText().trim());
						
						if(vvno==0)
						{
							alertMessage(CashVouAdd.this, "Voucher Number can't be Blank or Zero!!!");
							vou_no.requestFocus();
						}
						else
						{
							rcp = (RcpDto) rd.getVouList(loginDt.getFin_year(),loginDt.getDiv_code(),loginDt.getDepo_code(),10, vvno,vdbcr);

							if(option==1 && rcp!=null)
							{
								JOptionPane.showMessageDialog(CashVouAdd.this,"Voucher No. Already Exists !! ","Error",JOptionPane.INFORMATION_MESSAGE);
								vou_no.requestFocus();
								vou_no.setText("");

							}
							else if(option==1)
							{
								vou_date.requestFocus();
								vou_date.setSelectionStart(0);

							}

							else if(rcp!=null && vvno<= rcp.getVou_no())
							{

								fillData(rcp);
								btnSave.setEnabled(true);
								btnEdit.setEnabled(true);
								btnAdd.setEnabled(true);
								vou_date.setEnabled(true);
								vac_code.setEnabled(true);
								received.setEnabled(true);
								paid_by.setEnabled(true);
								remark.setEnabled(true);
								mobile_no.setEnabled(true);
								vamount.setEnabled(true);
							}
							else
							{
								JOptionPane.showMessageDialog(CashVouAdd.this,"Voucher No. Not Found!! ","Error",JOptionPane.INFORMATION_MESSAGE);
								vou_no.requestFocus();
								vou_no.setText("");
							}						
						}
					}
					catch(Exception e)
					{
						System.out.println("yaha kuch errroa haia dekho e"); 
						e.printStackTrace();
					}
					evt.consume();
				}
				
			}
		});
	
		
		lblPartyName = new JLabel("Date:");
		lblPartyName.setBounds(629, 248, 42, 20);
		getContentPane().add(lblPartyName);

		vou_date = new JFormattedTextField(sdf);
		vou_date.setBounds(706, 248, 118, 23);
		vou_date.setText(sdf.format(new Date()));
		checkDate(vou_date);
		getContentPane().add(vou_date);


		lblPaidby = new JLabel("Paid By:");
		lblPaidby.setBounds(345, 326, 98, 20);
		getContentPane().add(lblPaidby);

		
		paid_by = new TextField(50);
		paid_by.setBounds(474, 326, 380, 23);
		paid_by.setFont(fontHindi);
		getContentPane().add(paid_by);

		
		
		lblReceivedby = new JLabel("Received By:");
		lblReceivedby.setBounds(345, 365, 98, 20);
		getContentPane().add(lblReceivedby);

		
		received = new JTextField(50);
		received.setBounds(474, 365, 380, 23);
		received.setFont(fontHindi);
		getContentPane().add(received);

		
		
		lblMobileNo = new JLabel("Mobile No:");
		lblMobileNo.setBounds(345, 404, 98, 20);
		getContentPane().add(lblMobileNo);

		
		mobile_no = new TextField(45);
		mobile_no.setBounds(474, 404, 380, 23);
		getContentPane().add(mobile_no);

		
		lblRemark = new JLabel("Remark:");
		lblRemark.setBounds(345, 445, 98, 20);
		getContentPane().add(lblRemark);

		
		remark = new TextField(45);
		remark.setBounds(474, 445, 380, 23);
		remark.setFont(fontHindi);
		getContentPane().add(remark);

		
		
		lblDispatchDate = new JLabel("Amount");
		lblDispatchDate.setBounds(345, 475, 98, 20);
		getContentPane().add(lblDispatchDate);


	 	vamount = new JDoubleField();
		vamount.setBounds(474, 475, 98, 23);
		vamount.setHorizontalAlignment(SwingConstants.RIGHT);
		vamount.setMaxLength(11); //Set maximum length             
		vamount.setPrecision(2); //Set precision (2 in your case)              
		vamount.setAllowNegative(false); //Set false to disable negatives
		getContentPane().add(vamount);
		vamount.setSelectionStart(0);
		vamount.setText("0.00");
		

		lblType = new JLabel("Type :");
		lblType.setBounds(654, 474, 150, 20);
		getContentPane().add(lblType);

		mtype1 = new JComboBox();
		mtype1.setModel(new DefaultComboBoxModel(new String[] {"Cash", "Bank"}));
		mtype1.setSelectedIndex(0);
		mtype1.setName("0");
		mtype1.setBounds(704, 474, 150, 22);
		getContentPane().add(mtype1);

		
		lblRent = new JLabel("Rent Due:");
		lblRent.setForeground(Color.BLUE);
		lblRent.setFont(fontPlan);
		lblRent.setBounds(345, 505, 150, 20);
		getContentPane().add(lblRent);
		
		rentamt =  new JTextField();
		rentamt.setForeground(Color.BLUE);
		rentamt.setFont(fontPlan);
		rentamt.setBounds(474, 505, 100, 30);
		getContentPane().add(rentamt);
	 
		
		
		
		lblinvalid = new JLabel();
		lblinvalid.setBounds(690, 188, 118, 23);
		getContentPane().add(lblinvalid);
		 		

/*		branch = new JLabel(loginDt.getBrnnm());
		branch.setForeground(Color.BLACK);
		branch.setFont(new Font("Tahoma", Font.BOLD, 22));
		branch.setBounds(279, 66, 560, 24);
		getContentPane().add(branch);
*/


		lblDispatchEntry = new JLabel("Rent Entry");
		lblDispatchEntry.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDispatchEntry.setBounds(400, 155, 382, 22);
		getContentPane().add(lblDispatchEntry);




/*		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(277, 656, 651, 48);
		getContentPane().add(panel_2);
*/


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
					
					//case 0:
					//	slipno.requestFocus();
					//	break;
					case 1:
						vou_date.requestFocus();
						vou_date.setCaretPosition(0);

						break;

					case 2:
						String date = vou_date.getText();

//						System.out.println("S[2] DATE "+sdf.format((Date) s[2]));
						if (isValidDate(date)) {
							lblinvalid.setVisible(false);
							vac_code.requestFocus();

							// Check the Date Range for the Financial year
/*							if (isValidRange(date, loginDt.getFr_date(),loginDt.getTo_date())) {
								vac_code.requestFocus();
							} else {
								JOptionPane.showMessageDialog(
										CashVouAdd.this,
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
*/
						} else {
							lblinvalid.setText("invalid date");
							lblinvalid.setForeground(Color.red);
							lblinvalid.setVisible(true);
							vou_date.setValue(null);
//							vou_date.setValue(sdf.format(s[2]));
							checkDate(vou_date);
							vou_date.requestFocus();
							vou_date.setCaretPosition(0);

						}
						break;


					case 3:
						paid_by.requestFocus();
						paid_by.setSelectionStart(0);
					break;

					case 4:
							received.requestFocus();
							received.setSelectionStart(0);
						break;
						
					case 5:
						mobile_no.requestFocus();
						mobile_no.setSelectionStart(0);
					break;
					case 6:
						remark.requestFocus();
						remark.setSelectionStart(0);
					break;
					case 7:
						vamount.requestFocus();
						vamount.setSelectionStart(0);
					break;
					case 8:
						mtype1.requestFocus();
					break;
					case 9:
						if(setDoubleNumber(vamount.getText())==0)
						{
							vamount.requestFocus();
							vamount.setSelectionStart(0);
						}
						else
						{
							vamount.setText(formatter.format(setDoubleNumber(vamount.getText())));
							btnSave.setEnabled(true);
							btnSave.requestFocus();
							btnSave.setBackground(Color.BLUE);
						}

						break;
					}
					
				}

				if (key == KeyEvent.VK_ESCAPE) 
				{
					
					clearAll();
					btnAdd.setEnabled(true);
					btnEdit.setEnabled(true);
					btnSave.setEnabled(false);
					vou_date.setEnabled(false);
					vac_code.setEnabled(false);
					received.setEnabled(false);
					remark.setEnabled(false);
					mobile_no.setEnabled(false);
					vamount.setEnabled(false);
//					bslipdt.setEnabled(true);
					dispose();
					 
				}
				
				 
				
				 
			}
			 

		};
	 

		
/*		// upper panel
		JPanel panel = new JPanel();
//		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(277, 137, 651, 48);
		getContentPane().add(panel);

*/		
	
		// ///////////////////////////////////////////////
		vou_no.setName("0");
		//vou_no.setName("1");
		vou_date.setName("2");
		vac_code.setName("3");
		paid_by.setName("4");
		received.setName("5");
		mobile_no.setName("6");
		remark.setName("7");
		vamount.setName("8");
		mtype1.setName("9");
		
		vou_date.setEnabled(false);
		vac_code.setEnabled(false);
		received.setEnabled(false);
		paid_by.setEnabled(false);
		mobile_no.setEnabled(false);
		remark.setEnabled(false);
		vamount.setEnabled(false);
		vou_no.addKeyListener(keyListener); 
		vou_date.addKeyListener(keyListener);
		paid_by.addKeyListener(keyListener);
		received.addKeyListener(keyListener);
		mobile_no.addKeyListener(keyListener);
		remark.addKeyListener(keyListener);
		vamount.addKeyListener(keyListener);
		mtype1.addKeyListener(keyListener);
		
		vou_no.addFocusListener(myFocusListener);
		vou_date.addFocusListener(myFocusListener);
		vac_code.addFocusListener(myFocusListener);
		paid_by.addFocusListener(myFocusListener);
		received.addFocusListener(myFocusListener);
		mobile_no.addFocusListener(myFocusListener);
		remark.addFocusListener(myFocusListener);
		vamount.addFocusListener(myFocusListener);
		party_name.addFocusListener(myFocusListener);
		mtype1.addFocusListener(myFocusListener);

		
		
		 
		
/*		panel_3 = new JPanel();
//		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_3.setBackground(new Color(51, 102, 204));
		panel_3.setBounds(10, 137, 235, 28);
		getContentPane().add(panel_3);
		
		label_1 = new JLabel("Search");
		label_1.setForeground(Color.WHITE);
		panel_3.add(label_1);
		
*/		
		
/*		lblSlipDate = new JLabel("Voucher Date:");
		lblSlipDate.setBounds(16, 205, 110, 20);
		getContentPane().add(lblSlipDate);
		
		bslipdt = new JFormattedTextField(sdf);
		bslipdt.setBounds(120, 205, 118, 22);
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

						 
						if(isValidDate(date))
						{
							Vector data = (Vector) rd.getVouList(loginDt.getFin_year(),loginDt.getDiv_code(),loginDt.getDepo_code(),10, 0,date,vdbcr);
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
		
*/		
		
		
		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setMnemonic(KeyEvent.VK_A);
		btnAdd.setBounds(280, 574, 86, 30);
		getContentPane().add(btnAdd);
		
		btnEdit = new JButton("Edit");
		btnEdit.setActionCommand("Edit");
		btnEdit.setMnemonic(KeyEvent.VK_E);
		btnEdit.setBounds(372, 574, 86, 30);
		getContentPane().add(btnEdit);
		
		btnPrint = new JButton("Print");
		btnPrint.setActionCommand("Print");
		btnPrint.setEnabled(false);
		btnPrint.setMnemonic(KeyEvent.VK_P);
		btnPrint.setBounds(464, 574, 86, 30);
		getContentPane().add(btnPrint);
		
		btnMiss = new JButton("Ledger");
		btnMiss.setActionCommand("Ledger");
		btnMiss.setEnabled(false);
		btnMiss.setMnemonic(KeyEvent.VK_L);
		btnMiss.setBounds(556, 574, 86, 30);
		getContentPane().add(btnMiss);
		
		btnSave = new JButton("Save");
		btnSave.setActionCommand("Save");
		btnSave.setEnabled(false);
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.setBounds(744, 574, 86, 30);
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
							alertMessage(CashVouAdd.this, "Amount should not be blank");
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
		btnExit.setMnemonic(KeyEvent.VK_X);
		btnExit.setBounds(840, 574, 86, 30);
		getContentPane().add(btnExit);
		
			//chq_dt.addKeyListener(keyListener);

		btnExit.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					System.out.println("option ki valur exit per"+ option );
					saveData(true);
					
					
					clearAll();
				    dispose();
					vou_date.setEnabled(false);
					vac_code.setEnabled(false);
					vnart2.setEnabled(false);
					vamount.setEnabled(false);
//					bslipdt.setEnabled(true);
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
		
/*		JLabel lblVoucherNo = new JLabel("Voucher No.");
		lblVoucherNo.setBounds(16, 175, 100, 20);
		getContentPane().add(lblVoucherNo);
		
		vvou_no = new JTextField();
		vvou_no.setBounds(120, 175, 118, 22);
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
					 
						 
						 rcp = (RcpDto) rd.getVouList(loginDt.getFin_year(),loginDt.getDiv_code(),loginDt.getDepo_code(),10, vvno,vdbcr);
							if(rcp!=null && vvno<= rcp.getVou_no())
							{
								
								fillData(rcp);
								btnSave.setEnabled(true);
								btnDelete.setEnabled(true);
								btnAdd.setEnabled(true);
								btnMiss.setEnabled(true);
								btnPrint.setEnabled(true);
								vou_date.setEnabled(true);
								vac_code.setEnabled(true);
								received.setEnabled(true);
								remark.setEnabled(true);
								mobile_no.setEnabled(true);
								vamount.setEnabled(true);
							}
							else
							{
								JOptionPane.showMessageDialog(CashVouAdd.this,"Voucher No. Not Found!! ","Error",JOptionPane.INFORMATION_MESSAGE);
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
		
*/		

/*		panel_4 = new JPanel(); // side panel
//		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_4.setBounds(10, 136, 235, 511);
		getContentPane().add(panel_4);
*/		
		
		JPanel panel_1 = new JPanel();
//		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(277, 188, 651, 420);
		getContentPane().add(panel_1);

		
		
		btnAdd.addActionListener(this);
		btnEdit.addActionListener(this);
		btnPrint.addActionListener(this);
		btnMiss.addActionListener(this);
		btnSave.addActionListener(this);
		btnExit.addActionListener(this);
		
	}

	
	public void clearAll()
	{

		
		vac_code.setText("");
		vac_name.setText("");
		received.setText("");
		paid_by.setText("");
		remark.setText("");
		mobile_no.setText("");
		rentamt.setText("");
		vamount.setText("");
/*		vvou_no.setText("");
		bslipdt.setValue(null);
		checkDate(bslipdt);*/
		vamount.setEditable(true);
		btnAdd.setEnabled(false);
		btnSave.setBackground(null);
		btnExit.setBackground(null);
		btnPrint.setEnabled(false);
		btnMiss.setEnabled(false);
		btnSave.setEnabled(false);
		party_name.setText("");
		party_name.setVisible(true);
		hintTextLabel.setVisible(true);
		mtype1.setSelectedIndex(0);
		nop=0;
		if(option==1)
			option=1;
		else
		{
			
			vou_no.setText("");
			vou_date.setValue(null);
			checkDate(vou_date);
		}
		Partypane.setVisible(false);

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
					
					rcp.setVbook_cd(mtype1.getSelectedIndex()==0?10:20);
					rcp.setVou_no(setIntNumber(vou_no.getText().trim()));
					if (option==2)
						rcp.setVou_no(vouno);
					rcp.setVou_date(formatDate(vou_date.getText()));
					rcp.setVac_code(vac_code.getText());
					rcp.setVnart1(received.getText() );
					rcp.setBank_acno(paid_by.getText());
					rcp.setVnart2(mobile_no.getText() );
					rcp.setRcp_no(remark.getText());
					rcp.setVamount(setDoubleNumber(vamount.getText()));
					rcp.setBill_amt(setDoubleNumber(rentamt.getText()));
					rcp.setVdbcr(vdbcr);
					rcp.setFin_year(loginDt.getFin_year());
					rcp.setMnth_code(getMonthCode(vou_date.getText()));
					rcp.setCreated_by(loginDt.getLogin_id());
					rcp.setCreated_date(new Date());
					rcp.setMkt_year(loginDt.getMkt_year());
					
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
					rcp.setVbook_cd(10);
					rcp.setVou_no(setIntNumber(vou_no.getText()));
					rcp.setVou_date(formatDate(vou_date.getText()));
					rcp.setVac_code(vac_code.getText());
					rcp.setBank_acno(paid_by.getText());
					rcp.setVnart1(received.getText() );
					rcp.setVnart2(mobile_no.getText() );
					rcp.setRcp_no(remark.getText());
					
					rcp.setVamount(setDoubleNumber(vamount.getText()));
					rcp.setVdbcr(vdbcr);

					rcp.setFin_year(loginDt.getFin_year());
					rcp.setMnth_code(getMonthCode(vou_date.getText()));
					rcp.setModified_by(loginDt.getLogin_id());
					rcp.setModified_date(new Date());
					
		} catch (Exception e) {
			System.out.println("yaha phir se error hai updatemeein "+e);
		}
		    return rcp;

	}
	
	
	public void printSaveData() 
	{
	 
/*			rd.updateLed(rcpUpdate());
			invNoTableModel.getDataVector().removeAllElements();
			invNoTableModel.fireTableDataChanged();
*/		    btnSave.setEnabled(false);
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

			if (option==1)
			{
				
				rd.addLed(rcpAdd());
				vno=getLastNo();
			
				lastNo.setText(String.valueOf(vno));
				vno=vno+1;
				vou_no.setEditable(true);

				vou_no.setText(String.valueOf(vno));
				vou_no.requestFocus();
				vou_no.setCaretPosition(0);
			}

			if (option==2)
			{
				rd.updateLed(rcpUpdate());
			}
		}
		
		btnAdd.setEnabled(true);
		btnEdit.setEnabled(true);
		invNoTable.setEnabled(true);
		vou_no.setEnabled(true);
		vou_date.setEnabled(true);
		clearAll();
		 
	}
	
	
	public void fillInvTable(Vector data)
	{
		invNoTableModel.getDataVector().removeAllElements();
		btnSave.setEnabled(true);
		btnEdit.setEnabled(true);
		btnAdd.setEnabled(true);
		btnMiss.setEnabled(true);
		btnPrint.setEnabled(true);
		vou_date.setEnabled(true);
		vac_code.setEnabled(true);
		received.setEnabled(true);
		paid_by.setEnabled(true);
		mobile_no.setEnabled(true);
		remark.setEnabled(true);
		vamount.setEnabled(true);
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
//		vou_date.setText(formatDate(r.getVou_date()));
		vou_date.setText(sdf.format(r.getVou_date()));

		paid_by.setText(r.getBank_acno());
		received.setText(r.getVnart1());
		mobile_no.setText(r.getVnart2());
		remark.setText(r.getRcp_no());
		vamount.setText(formatter.format(r.getVamount()));
		
		//vamount.setEditable(false);
		prtyDto = (TransportDto) partyMap.get(r.getVac_code());
		
		/*btnSave.setEnabled(true);
		btnDelete.setEnabled(true);
		btnAdd.setEnabled(false);
		btnMiss.setEnabled(false);
		bank.setEnabled(false);
		*/
		vac_name.setCaretPosition(0);
		vou_date.setEnabled(true);
		vac_code.setEnabled(true);
		received.setEnabled(true);
		paid_by.setEnabled(true);
		mobile_no.setEnabled(true);
		remark.setEnabled(true);
		vamount.setEnabled(true);
		hintTextLabel.setVisible(false);
		vou_date.requestFocus();
		btnMiss.setEnabled(true);
		btnPrint.setEnabled(true);
	}	
	
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		if(e.getActionCommand().equalsIgnoreCase("add") )
		{
			
			vou_date.setEnabled(true);
			vac_code.setEnabled(true);
			vac_name.setText("");
			received.setEnabled(true);
			paid_by.setEnabled(true);
			remark.setEnabled(true);
			mobile_no.setEnabled(true);

			vamount.setEnabled(true);
			
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(false);
			btnMiss.setEnabled(false);
			clearAll(); 
			option=1;
			if (option==1 )
			{
				slno=1;
				lastNo.setText(String.valueOf(vno));
				vno=vno+1;
				vou_no.setText(String.valueOf(vno));
				vou_date.setText(sdf.format(new Date()));
				
			}
			vou_no.setEditable(true);
			vou_no.requestFocus();
			vou_no.setCaretPosition(0);

			
		
		}
		
		if(e.getActionCommand().equalsIgnoreCase("edit") )
		{
			
			vou_no.setEditable(true);
			vou_date.setEnabled(true);
			vac_code.setEnabled(true);
			vac_name.setText("");
			received.setEnabled(true);
			paid_by.setEnabled(true);
			remark.setEnabled(true);
			mobile_no.setEnabled(true);
			
			vamount.setEnabled(true);
			
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(false);
			btnMiss.setEnabled(false);
			
			
			clearAll(); 
			hintTextLabel.setVisible(false);
			option=2;
			vou_no.requestFocus();
			vou_no.setCaretPosition(0);

			
		
		}
		
		if(e.getActionCommand().equalsIgnoreCase("print"))
		{

		 		new GenerateReceipt(String.valueOf(loginDt.getFin_year()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),vou_no.getText(),vou_no.getText(),"PDF",loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),10,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);
		 		
			
		}

		if(e.getActionCommand().equalsIgnoreCase("ledger"))
		{
			String sdate=sdf.format(loginDt.getFr_date());				
			String edate=sdf.format(loginDt.getTo_date());				
 		    new OutStandingPdf(sdate,edate,setIntNumber(vac_code.getText()),party_name.getText(),loginDt.getDepo_code(),10,loginDt.getFin_year(),"PDF",loginDt.getDrvnm(), loginDt.getPrinternm(),loginDt.getBrnnm(),loginDt.getDivnm(),"1",3,null);
 		    
 		    
			
		}

		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			saveData(false);
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(true);
			
		}
		if(e.getActionCommand().equalsIgnoreCase("delete"))
		{
			int answer = 0;
			  answer = JOptionPane.showConfirmDialog(CashVouAdd.this, "Are You Sure : ");
			    if (answer == JOptionPane.YES_OPTION) {

				rd.deleteLed(rcpUpdate());
/*				invNoTableModel.getDataVector().removeAllElements();
				invNoTableModel.fireTableDataChanged();
*/				clearAll();
				s = odao.getVouno(10, loginDt.getDiv_code(), loginDt.getDepo_code(), edate,loginDt.getFin_year(),vdbcr);
				lastNo.setText(String.valueOf(s[0]));

//				bslipdt.setEnabled(true);
			    }
		}
		
		if(e.getActionCommand().equalsIgnoreCase("bank"))
		{
			
			if (option==1)
			{
				s = odao.getVouno(10, loginDt.getDiv_code(), loginDt.getDepo_code(), edate,loginDt.getFin_year(),vdbcr);
				vno=setIntNumber(s[0].toString());
				slno=setIntNumber(s[1].toString())+1;
				vno=vno+1;
//				vou_no.setText(String.valueOf(vno));	
				lastNo.setText(String.valueOf(s[0]));

			}

			
			if (option==2)
			{
				s = odao.getVouno(10, loginDt.getDiv_code(), loginDt.getDepo_code(), edate,loginDt.getFin_year(),vdbcr);
				vno=setIntNumber(s[0].toString());
				lastNo.setText(String.valueOf(s[0]));

			}

			
		}
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			clearAll();
			vou_date.setEnabled(false);
			vac_code.setEnabled(false);
			received.setEnabled(false);
			paid_by.setEnabled(false);
			remark.setEnabled(false);
			mobile_no.setEnabled(false);
			vamount.setEnabled(false);
//			bslipdt.setEnabled(true);
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(true);
			btnSave.setEnabled(false);
			vou_no.setEditable(false);
			vou_no.setText("");
			dispose();
		}

	}
	
	
	public int getLastNo()
	{
		vno = odao.getVounoNew(10, loginDt.getDiv_code(), loginDt.getDepo_code(), edate,loginDt.getFin_year(),vdbcr);
		return vno;
	}

	private int getHeadCode()
	{
		Vector v = loginDt.getHeadList();
		int vbkcode=0;
		BookDto bk=null;
		int size=v.size();
		for(int i=0; i<size;i++)
		{
			bk= (BookDto) v.get(i);
			if(bk.getBook_code()==10)
			{
				vbkcode=bk.getBook_code();
				break;
			}
		}
		
		return vbkcode;
	}

	
	public void setVisible(boolean bx)
	{
		System.out.println("setVisible is called"+ vdbcr);
		//setting last no.
		lastNo.setText(String.valueOf(getLastNo()));
		dataadd=true;
		
		
		if(dataadd)
			btnAdd.setEnabled(true);
		else
			btnAdd.setEnabled(false);
			
		
		
		
		super.setVisible(true);
	}
	

}


