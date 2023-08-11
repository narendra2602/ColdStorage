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
import com.coldstorage.print.GenerateOutward;

public class RentEntry extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	
	Font font,fontHindi;

	private JTextField vou_no;
	private JTextField vac_code;
	private JFormattedTextField vou_date,chq_date,vdate,chl_date;
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

	private JLabel lblMarka;

	private JTextField markno;

	private JLabel lblKatte;

	private JTextField katte;

	private JLabel lblReceiver;

	private JTextField receiver_name;

	private JLabel lblwajan;

	private JTextField weightout;

	private JLabel lblMobile;

	private JTextField mobile_no;

	private JLabel lblVehicle;

	private JTextField vehicle_no;

	private JLabel lblChlNo;

	private JTextField chl_no;

	private JLabel lblchlDate;

	private JLabel lblBalance;

	private JLabel balqty;



	public RentEntry()
	{
		//infoName =(String) helpImg.get(getClass().getSimpleName());
		
		formatter = new DecimalFormat("0.00");
		numderformatter = new DecimalFormat("0");
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

		option=1;
		
		
		
		


		
		
		///////////////////////////////////////////////////////

/*		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(419, 150, 577, 15);
		getContentPane().add(lblFinancialAccountingYear);
*/
		
		lblLastNo = new JLabel("Total Rent Received:");
		lblLastNo.setVisible(false);
		lblLastNo.setForeground(Color.RED);
		lblLastNo.setBounds(590, 130, 165, 20);
		getContentPane().add(lblLastNo);
		
		lastNo = new JLabel("");
		lastNo.setVisible(false);
		lastNo.setForeground(Color.RED);
		lastNo.setBounds(750, 130, 70, 20);
		getContentPane().add(lastNo);

		
		lblBalance = new JLabel("Balance Rent:");
		lblBalance.setForeground(Color.RED);
		lblBalance.setBounds(590, 408, 165, 20);
		getContentPane().add(lblBalance);
		
		balqty = new JLabel("");
		balqty.setForeground(Color.RED);
		balqty.setBounds(750, 408, 70, 20);
		getContentPane().add(balqty);

		
		
		
		lblvouno = new JLabel("Slip Number:");
		lblvouno.setBounds(282, 131, 126, 20);
		getContentPane().add(lblvouno);

		vou_no = new JTextField();
		vou_no.setBounds(408, 131,132, 23);
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

		lblDispatchEntry = new JLabel("Rent Entry");
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
								weightout.requestFocus();
								weightout.setSelectionStart(0);
							}
							else
							{
								JOptionPane.showMessageDialog(RentEntry.this,"Date range should be in between: "
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
						katte.setText(numderformatter.format(setIntNumber(katte.getText())));
						if(setDoubleNumber(katte.getText())>(setIntNumber(balqty.getText())))
						{
							alertMessage(RentEntry.this, "Issue Qty is Wrong !!! It should not be more than "+setIntNumber(balqty.getText()));
							katte.setText("");
							katte.requestFocus();
						}
						else
						{
							weightout.requestFocus();
							weightout.setSelectionStart(0);
						}
						break;

					case 3:
						weightout.setText(formatter.format(setDoubleNumber(weightout.getText())));
						receiver_name.requestFocus();
						receiver_name.setSelectionStart(0);
						break;
					case 4:
						mobile_no.requestFocus();
						mobile_no.setSelectionStart(0);
						break;
					case 5:
						
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
		lblvaccode.setBounds(282, 195, 126, 20);
		getContentPane().add(lblvaccode);

		vac_code = new JTextField();
		vac_code.setEditable(false);
		vac_code.setBounds(408, 195, 86, 23);
		getContentPane().add(vac_code);
		

		
		lblNarration = new JLabel("Vendor In Hindi:");
		lblNarration.setBounds(282, 226, 126, 20);
		getContentPane().add(lblNarration);
		
		vnart_1 = new JTextField();
		vnart_1.setBounds(408, 226, 382, 23);
		vnart_1.setEditable(false);
		vnart_1.setFont(fontHindi);
		getContentPane().add(vnart_1);
		
		lblPaidTo = new JLabel("Category.:");
		lblPaidTo.setBounds(282, 256, 126, 20);
		getContentPane().add(lblPaidTo);
		
		
		
		name = new JTextField();
		name.setEditable(false);
		name.setBounds(504, 194, 286, 23);
		getContentPane().add(name);


		
		name1_hindi = new JTextField();
		name1_hindi.setBounds(604, 254, 185, 23);
		name1_hindi.setEditable(false);
		name1_hindi.setFont(fontHindi);
		getContentPane().add(name1_hindi);

		
		name1 = new JTextField();
		name1.setEditable(false);
		name1.setBounds(408, 254, 185, 23);
		name1.setFont(fontHindi);
		getContentPane().add(name1);
		

		
		lblChqNo = new JLabel("Group:");
		lblChqNo.setBounds(282, 286, 126, 20);
		getContentPane().add(lblChqNo);
		


		group_hindi = new JTextField();
		group_hindi.setBounds(604, 286, 185, 23);
		group_hindi.setFont(fontHindi);
		group_hindi.setEditable(false);
		getContentPane().add(group_hindi);

		
		
		group = new JTextField();
		group.setBounds(408, 286, 185, 23);
		group.setFont(fontHindi);
		group.setEditable(false);
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
		pname.setEditable(false);
		getContentPane().add(pname);
		

		lblGstTax = new JLabel("Inward Quantity:");
		lblGstTax.setBounds(282, 350, 126, 20);
		getContentPane().add(lblGstTax);

		quantity = new JTextField();
		quantity.setHorizontalAlignment(SwingConstants.RIGHT);
		quantity.setBounds(408, 350, 125, 23);
		quantity.setEditable(false);
		getContentPane().add(quantity);

		
		lblCgst = new JLabel("Weight:");
		lblCgst.setBounds(540, 350, 70, 20);
		getContentPane().add(lblCgst);

		weight = new JTextField();
		weight.setHorizontalAlignment(SwingConstants.RIGHT);
		weight.setEditable(false);
		weight.setBounds(604, 350, 185, 23);
		getContentPane().add(weight);
	
		lblMarka = new JLabel("Rent Amount:");
		lblMarka.setBounds(282, 379, 126, 20);
		getContentPane().add(lblMarka);

		markno = new JTextField();
		markno.setHorizontalAlignment(SwingConstants.RIGHT);
		markno.setEditable(false);
		markno.setBounds(408, 379, 185, 23);
		getContentPane().add(markno);
	

		lblChlNo = new JLabel("Receipt No:");
		lblChlNo.setBounds(282, 437, 130, 25);
		getContentPane().add(lblChlNo);

		
		chl_no = new JTextField();
		chl_no.setHorizontalAlignment(SwingConstants.RIGHT);
		chl_no.setBounds(408, 437, 125, 23);
		chl_no.setEditable(false);
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
						JOptionPane.showMessageDialog(RentEntry.this,"Receipt No. "+vouno,"Empty!!!!",JOptionPane.INFORMATION_MESSAGE);						
						chl_no.setText("");
						chl_no.requestFocus();
					}
					else
					{						
						rcp =  idao.getReceiptDetail(vouno,loginDt.getFin_year(),10);

						if(rcp!=null)
						{
							if(rcp.getSinv_no()==0) // for advance entry
								option=0;
							if(option==1)
							{
							JOptionPane.showMessageDialog(RentEntry.this,"Receipt No. "+vouno,"Already Exists!!!!!",JOptionPane.INFORMATION_MESSAGE);						
							chl_no.setText("");
							chl_no.requestFocus();
							}
							else
							{
								chl_no.setEditable(false);
								lblLastNo.setVisible(true);
								lastNo.setVisible(true);
								fillData1(rcp);
								chl_date.requestFocus();
								btnPrint.setEnabled(true);
							}
						} 
						else if(option==1) 
							{
							if(setDoubleNumber(balqty.getText())==0)
							{
								alertMessage(RentEntry.this, "Due Rent is 0  "+setDoubleNumber(balqty.getText()));
								chl_no.setText("");
								chl_no.requestFocus();
							}
							else
								chl_date.requestFocus();
							}
						else
						{
							JOptionPane.showMessageDialog(RentEntry.this,"Receipt No."+billNo," Not Found!!!!!",JOptionPane.INFORMATION_MESSAGE);						
							chl_no.setText("");
							chl_no.requestFocus();
						}
						evt.consume();
					}
				}
				
			}
		});


		lblchlDate = new JLabel("Date:");
		lblchlDate.setBounds(282, 466, 70, 20);
		getContentPane().add(lblchlDate);


		chl_date = new JFormattedTextField(sdf);
		chl_date.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkDate(chl_date);
		chl_date.setBounds(408, 466, 126, 22);
		getContentPane().add(chl_date);

		
/*		lblKatte = new JLabel("Outward Quantity:");
		lblKatte.setBounds(282, 531, 130, 25);
		getContentPane().add(lblKatte);

		
		katte = new JTextField();
		katte.setHorizontalAlignment(SwingConstants.RIGHT);
		katte.setBounds(408, 531, 125, 23);
		getContentPane().add(katte);
*/	

		lblwajan = new JLabel("Amount:");
		lblwajan.setBounds(282, 495, 70, 20);
		getContentPane().add(lblwajan);

		
		weightout = new JTextField();
		weightout.setHorizontalAlignment(SwingConstants.RIGHT);
		weightout.setBounds(408, 495, 185, 23);
		getContentPane().add(weightout);

		lblReceiver = new JLabel("Receiver Name:");
		lblReceiver.setBounds(282, 524, 126, 20);
		getContentPane().add(lblReceiver);

		
		receiver_name = new JTextField();
		receiver_name.setBounds(408, 524, 382, 23);
		receiver_name.setFont(fontHindi);
		getContentPane().add(receiver_name);

		lblMobile = new JLabel("Mobile No:");
		lblMobile.setBounds(282, 553, 126, 20);
		getContentPane().add(lblMobile);

		
		mobile_no = new JTextField();
		mobile_no.setBounds(408, 553, 382, 23);
		getContentPane().add(mobile_no);

/*		lblVehicle = new JLabel("Vehicle No:");
		lblVehicle.setBounds(282, 618, 126, 20);
		getContentPane().add(lblVehicle);

		
		vehicle_no = new JTextField();
		vehicle_no.setBounds(408, 618, 382, 23);
		getContentPane().add(vehicle_no);
*/
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
		weightout.setName("3");
		receiver_name.setName("4");
		mobile_no.setName("5");
//		vehicle_no.setName("6");
		remark.setName("7");

		chl_date.addKeyListener(keyListener);
//		katte.addKeyListener(keyListener);
		weightout.addKeyListener(keyListener);
		receiver_name.addKeyListener(keyListener);
		mobile_no.addKeyListener(keyListener);
//		vehicle_no.addKeyListener(keyListener);
		remark.addKeyListener(keyListener);
		
		chl_date.addFocusListener(myFocusListener);
		weightout.addFocusListener(myFocusListener);
		receiver_name.addFocusListener(myFocusListener);
		mobile_no.addFocusListener(myFocusListener);
		remark.addFocusListener(myFocusListener);

		

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
					vou_no.setText("");
					 rcp = (InvViewDto) idao.getInvDetail(vouno,"",loginDt.getFin_year(),60);
					if(rcp==null)
					{
						clearAll();
						JOptionPane.showMessageDialog(RentEntry.this,"No Record for Vou No. "+vouno,"Record not found",JOptionPane.INFORMATION_MESSAGE);						
						
					} 
					else
					{
						lblLastNo.setVisible(true);
						lastNo.setVisible(true);
						chl_no.setEditable(true);
						fillData(rcp);
						chl_no.requestFocus();
						chl_no.setSelectionStart(0);
						option=1;
						
					}
					evt.consume();
				}
				
			}
		});
		
		

	
		
		
		
				
		btnAdd = new JButton("Edit by Receipt No");
		btnAdd.setActionCommand("Edit");
		btnAdd.setBounds(230, 615, 170, 30);
		getContentPane().add(btnAdd);

		
		btnPrint = new JButton("Print");
		btnPrint.setActionCommand("Print");
		btnPrint.setEnabled(false);
		btnPrint.setBounds(405, 615, 70, 30);
		getContentPane().add(btnPrint);

		
		btnSave = new JButton("Save");
		btnSave.setActionCommand("Save");
		btnSave.setBounds(674, 615, 86, 30);
		getContentPane().add(btnSave);

		btnExit = new JButton("Exit");
		btnExit.setActionCommand("Exit");
		btnExit.setBounds(766, 615, 86, 30);
		getContentPane().add(btnExit);
		
		
		btnSave.addActionListener(this);
		btnExit.addActionListener(this);
		btnAdd.addActionListener(this);
		btnPrint.addActionListener(this);

/*		JPanel panel_4 = new JPanel();
//		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_4.setBackground(Color.LIGHT_GRAY);
		panel_4.setBounds(225, 134, 631, 48);
		getContentPane().add(panel_4);
*/
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(225, 121, 631, 530);
		getContentPane().add(panel_1);
		

		btnSave.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					
					int ino = saveData();
					
					if(ino!=0)
					{
						JOptionPane.showMessageDialog(RentEntry.this,"Receipt No "+billNo,"Receipt No",JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
			}
		});

	}


	public void clearAll()
	{

		chl_no.setText("");
		chl_date.setValue(null);
		checkDate(chl_date);
		vou_no.setText("");
		vou_date.setValue(null);
		checkDate(vou_date);

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
		weightout.setText("");
		balqty.setText("");
		markno.setText("");

//		katte.setText("");
		weightout.setText("");
		receiver_name.setText("");
		mobile_no.setText("");
//		vehicle_no.setText("");
		remark.setText("");

		
		lblLastNo.setVisible(false);
		lastNo.setVisible(false);

		bno="";
		
		
		chl_no.setEditable(false);

		 btnSave.setBackground(null);


	}


	 

	public InvViewDto vouAdd() 
	{
		 
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
        
		 
		InvViewDto rcp1 = null;
		try {
			 

				 
				rcp1 = new InvViewDto();
				
				int voun=setIntNumber(vou_no.getText());
				rcp1.setDepo_code(loginDt.getDepo_code());
				rcp1.setDoc_type(10);
				if(voun>0)
				{
					rcp1.setSinv_no(setIntNumber(vou_no.getText()));
					rcp1.setInv_date(sdf.parse(vou_date.getText()));
				}
				rcp1.setMac_code(vac_code.getText());
				rcp1.setCreated_by(loginDt.getLogin_id());
				rcp1.setCreated_date(new Date());
				rcp1.setFin_year(loginDt.getFin_year());
				rcp1.setChl_no(chl_no.getText());
				rcp1.setBunch_no(setIntNumber(chl_no.getText()));
				rcp1.setChl_date(sdf.parse(chl_date.getText()));
				rcp1.setReceiver_name(receiver_name.getText());
				rcp1.setMobile((mobile_no.getText()));
				rcp1.setNet_amt(setDoubleNumber(weightout.getText()));
				rcp1.setRemark(remark.getText());
				
				billNo=setIntNumber(chl_no.getText());
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
	
	

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			int ino = saveData();
			
			if(ino!=0)
			{
				JOptionPane.showMessageDialog(RentEntry.this,"Receipt No "+billNo,"Receipt No",JOptionPane.INFORMATION_MESSAGE);

			}
		}
		
		if(e.getActionCommand().equalsIgnoreCase("edit"))
		{
			option=0;
			chl_no.requestFocus(); 
			vou_no.setEditable(false);
			chl_no.setEditable(true);
			
		}

		
		if(e.getActionCommand().equalsIgnoreCase("print"))
		{
			//ArrayList vlist = vouPrint();
			

			if(e.getActionCommand().equalsIgnoreCase("print"))
			{
					
				String  vouNo=chl_no.getText();
					
					new GenerateOutward(String.valueOf(loginDt.getFin_year()),
							String.valueOf(loginDt.getDepo_code()),
							String.valueOf(loginDt.getDiv_code()),
							String.valueOf(0), String.valueOf(vouNo), "View",
							loginDt.getDrvnm(), loginDt.getPrinternm(), "2",
							loginDt.getMnth_code(), 60, loginDt.getBrnnm(),
							loginDt.getDivnm(), null, 0);

					
			}
			
		}


		
		
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			option=0;
	//		invNoTable.setEnabled(true);
			vou_no.setEditable(true); 
			chl_no.setEditable(true);
			clearAll();
			dispose();
		}

		
		
	}


		
		public int saveData() 
		{
			int vouNo=0;
			
			if ((option==1 || option==2))
			{
				vouNo= idao.addLedfileRecord(vouAdd());
				 
			}

			if (option==0)
			{
				vouNo= idao.updateLedfileRecord(vouAdd());
				 
			}

			
//			btnPrint.setEnabled(false);
			vou_no.setEditable(false);
			
			option=1;
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
		quantity.setText(numderformatter.format(rcp.getSqty()));
		weight.setText(formatter.format(rcp.getWeight()));
		markno.setText(formatter.format(rcp.getNet_amt()));
		lastNo.setText(formatter.format(rcp.getBill_amt()));
		balqty.setText(formatter.format(rcp.getCn_val()));
		pcode=rcp.getSprd_cd();
		groupcd=rcp.getSpd_gp();
		sqty=rcp.getSqty();
		double  iqty=rcp.getTotqty();
		double  balqty1=sqty-iqty;
		rcp.setCn_val(balqty1);
		
	}	

	public void fillData1(InvViewDto rcp)
	{
		// voucher header
			lastNo.setText(formatter.format(rcp.getBill_amt()));
			chl_no.setText(rcp.getChl_no());
			chl_date.setText(sdf.format(rcp.getChl_date()));
			weightout.setText(formatter.format(rcp.getNet_amt()));
			receiver_name.setText(rcp.getReceiver_name());
			mobile_no.setText(rcp.getMobile());
			remark.setText(rcp.getRemark());
		
	}	
	 

}


