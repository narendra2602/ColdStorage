package com.coldstorage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.coldstorage.dao.CmpMsflDAO;
import com.coldstorage.dto.CmpMsflDto;
import com.coldstorage.dto.StateDto;
import com.coldstorage.util.JIntegerField;
import com.coldstorage.util.TextField;

public class CompanyMaster extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	Font font;
	private JIntegerField cmp_code;
	private JTextField address2;
	private JTextField address3;
	private JTextField city;
	private JTextField phone,gstate;
	private JTextField fax,panno,email_id,pin,gstno,mobile_no,aadharno;
	private JTextField address1;
	private JComboBox mstate_name,gstreg;
	private JLabel lblInvoiceNo ;
	private JLabel lblPartyCode; 
	private JLabel lblDispatchDate;
	private JLabel lblInvoiceDate;
	private JLabel lblLrDate,lblFaxNo;
	private JLabel lblCity;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private JButton exitButton,saveButton;
	private JTextField cmp_name;
	private CmpMsflDAO cmpDao=null;
	private CmpMsflDto cmpDto;
	
	StateDto gststate;
	public CompanyMaster()
	{
		 
		
		cmpDao = new CmpMsflDAO();
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 670);	
	       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		        
	        this.setUndecorated(true);
	        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

	        arisleb.setVisible(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Company Information Master");
		getContentPane().setLayout(null);
		
		// ///////////////////////////////////////////////

		
/*		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(380, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);
*/

		lblInvoiceNo = new JLabel("Address:");
		lblInvoiceNo.setBounds(267, 197, 126, 20);
		getContentPane().add(lblInvoiceNo);

		lblLrDate = new JLabel("Phone No.");
		lblLrDate.setBounds(267, 343, 126, 20);
		getContentPane().add(lblLrDate);

		lblFaxNo = new JLabel("Fax No.");
		lblFaxNo.setBounds(267, 371, 126, 20);
		getContentPane().add(lblFaxNo);



		
		lblCity = new JLabel("Address:");
		lblCity.setBounds(267, 169, 126, 20);
		getContentPane().add(lblCity);



		lblPartyCode = new JLabel("Code");
		lblPartyCode.setBounds(267, 112, 126, 20);
		getContentPane().add(lblPartyCode);

		cmp_code = new JIntegerField();
		cmp_code.setEditable(false);
		cmp_code.setBounds(404, 111,70, 23);
		getContentPane().add(cmp_code);

		lblDispatchDate = new JLabel("Address:");
		lblDispatchDate.setBounds(267, 225, 126, 20);
		getContentPane().add(lblDispatchDate);

		lblInvoiceDate = new JLabel("City:");
		lblInvoiceDate.setBounds(267, 253, 126, 20);
		getContentPane().add(lblInvoiceDate);


		address1 = new TextField(40);
		address1.setBounds(404, 168, 400, 23);
		getContentPane().add(address1);

		
		address2 = new TextField(40);
		address2.setBounds(404, 196,400, 23);
		getContentPane().add(address2);

		
		address3 = new TextField(40);
		address3.setBounds(404, 225, 400, 23);
		getContentPane().add(address3);
		
		
		city = new TextField(20);
		city.setEditable(false);
		city.setBounds(404, 252, 400, 23);
		getContentPane().add(city);

		phone = new TextField(30);
		phone.setBounds(404, 342, 208, 23);
		getContentPane().add(phone);

		
		getContentPane().add(arisleb);

		
		
/*		gstate = new TextField(2);
		gstate.setBounds(404, 310, 24, 22);
		gstate.setEditable(false);
		getContentPane().add(gstate);

		JLabel lblState = new JLabel("State:");
		lblState.setBounds(267, 310, 108, 20);
		getContentPane().add(lblState);

*/		
/*		mstate_name = new JComboBox(loginDt.getGststateList());
		mstate_name.setBounds(432, 310, 178, 22);
		getContentPane().add(mstate_name);

		mstate_name.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent itemEvent) {
				// TODO Auto-generated method stub
				int state = itemEvent.getStateChange();
				if(state == ItemEvent.SELECTED)
				{
					gststate = (StateDto) itemEvent.getItem();
					gstate.setText(gststate.getGststate_code());
				}
				
			}
	
		});
		

*/		 
/*		branch.setText(loginDt.getBrnnm());
		branch.setBounds(400, 35, 395, 22);
		getContentPane().add(branch);

*/		lblDispatchEntry = new JLabel("Company Information");
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDispatchEntry.setBounds(450, 60, 251, 22);
		getContentPane().add(lblDispatchEntry);
		
		fax = new TextField(30);
		fax.setBounds(404, 371, 208, 23);
		getContentPane().add(fax);

		JLabel lblPanNo = new JLabel("Pan No:");
		lblPanNo.setBounds(267, 493, 126, 20);
		getContentPane().add(lblPanNo);
		
		panno = new TextField(15);
		panno.setBounds(404, 493, 208, 23);
		getContentPane().add(panno);


		JLabel lblAddharNo = new JLabel("Aadhar No:");
		lblAddharNo.setBounds(267, 462, 126, 20);
		getContentPane().add(lblAddharNo);
		
		aadharno = new TextField(15);
		aadharno.setBounds(404, 462, 208, 23);
		getContentPane().add(aadharno);

		
		JLabel lblMobile = new JLabel("Mobile No.:");
		lblMobile.setBounds(267, 397, 126, 20);
		getContentPane().add(lblMobile);

		
		mobile_no = new TextField(21);
		mobile_no.setBounds(404, 399, 208, 23);
		getContentPane().add(mobile_no);

		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(267, 430, 126, 20);
		getContentPane().add(lblEmail);

		
		email_id = new TextField(50);
		email_id.setBounds(404, 430, 208, 23);
		getContentPane().add(email_id);
		
		
		
		
		
		
		
		 
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(740, 613, 86, 30);
		getContentPane().add(exitButton);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(648, 613, 86, 30);
		getContentPane().add(saveButton);
		
		saveButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					saveData();
				}
			}
		});
		
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(267, 139, 126, 20);
		getContentPane().add(lblName);

		cmp_name = new TextField(40);
		cmp_name.setFont(new Font("Tahoma", Font.BOLD, 16));
		cmp_name.setEditable(false);
		cmp_name.setForeground(Color.RED);
		cmp_name.setBounds(404, 139, 400, 23);
		getContentPane().add(cmp_name);
		
		
		JLabel lblPin = new JLabel("Pin:");
		lblPin.setBounds(267, 281, 70, 20);
		getContentPane().add(lblPin);
		
		pin = new TextField(6);
		pin.setBounds(404, 281, 79, 23);
		getContentPane().add(pin);

		
		JLabel lblGstReg = new JLabel("GST Registered :");
		lblGstReg.setBounds(267, 524, 126, 20);
		getContentPane().add(lblGstReg);

		gstreg = new JComboBox();
		gstreg.setModel(new DefaultComboBoxModel(new String[] {"Y", "N"}));
		gstreg.setSelectedIndex(0);
		gstreg.setName("0");
		gstreg.setBounds(404, 524, 46, 22);
		getContentPane().add(gstreg);

		
		JLabel lblGstNo = new JLabel("GST No:");
		lblGstNo.setBounds(267, 555, 126, 20);
		getContentPane().add(lblGstNo);
		
		gstno = new TextField(15);
		gstno.setBounds(404, 555, 208, 23);
		getContentPane().add(gstno);

/*		
		panel_2 = new JPanel();
//		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(229, 657, 598, 48);
		getContentPane().add(panel_2);
*/		
		
		KeyListener keyListener = new KeyListener() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
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


					switch (id) 
					{
					case 0:
						cmp_code.requestFocus();
						cmp_code.setSelectionStart(0);
						break;
					case 1:
						cmp_name.requestFocus();
						cmp_code.setSelectionStart(0);
						break;
					case 2:
						address1.requestFocus();
						address1.setSelectionStart(0);
						break;
					case 3:
						address2.requestFocus();
						address2.setSelectionStart(0);
						break;
					case 4:
						address3.requestFocus();
						address3.setSelectionStart(0);
						break;
					case 5:
						city.requestFocus();
						city.setSelectionStart(0);
						break;
					case 6:
						pin.requestFocus();
						pin.setSelectionStart(0);
						break;
					case 7:
						phone.requestFocus();
						phone.setSelectionStart(0);
						break;
					case 8:
						fax.requestFocus();
						fax.setSelectionStart(0);
						break;
					case 9:
						mobile_no.requestFocus();
						mobile_no.setSelectionStart(0);
						break;
					case 10:
						email_id.requestFocus();
						email_id.setSelectionStart(0);
					case 11:
						aadharno.requestFocus();
						aadharno.setSelectionStart(0);
						break;
					case 12:
						panno.requestFocus();
						panno.setSelectionStart(0);
						break;
					case 13:
						gstreg.requestFocus();
						break;
					case 14:
						if(gstreg.getSelectedIndex()==1)
						{
							gstno.setEnabled(false);
							saveButton.requestFocus();
							saveButton.setBackground(Color.BLUE);
							exitButton.setBackground(null);
							
							
						}
						else
						{
							gstno.setEnabled(true);
							gstno.requestFocus();
							gstno.setSelectionStart(0);
						}
				 		break;
					case 15:
						saveButton.requestFocus();
						saveButton.setBackground(Color.BLUE);
						exitButton.setBackground(null);

						break;
					}
				}

				if (key == KeyEvent.VK_ESCAPE) {
					dispose();
					//System.exit(0);
					
				}
			}

			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		};
		
		
		
		
		
		
		
		cmp_code.setName("1");
		cmp_name.setName("2");
		address1.setName("3");
		address2.setName("4");
		address3.setName("5");
		city.setName("6");
		pin.setName("7");
		phone.setName("8");
		fax.setName("9");
		mobile_no.setName("10");
		email_id.setName("11");
		aadharno.setName("12");
		panno.setName("13");
		gstreg.setName("14");
		gstno.setName("15");

		addKeyListener(CompanyMaster.this,keyListener);
		setFocusListener(CompanyMaster.this);
		
		exitButton.addActionListener(this);
		exitButton.setActionCommand("exit");
		
		saveButton.addActionListener(this);
		saveButton.setActionCommand("save");
		
		
		
		
		
				
		JPanel panel_1 = new JPanel();
//		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(229, 99, 598, 506);
		getContentPane().add(panel_1);
		
		
		
		
		
	}

	
 

	
	
	

	
	
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			dispose();
		}
		
		 
		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			saveData();
		}
		
		 
		

	}
	
	
	public void saveData()
	{
	 
			getData();
			
/*			boolean email=isValidEmailAddress(email_id.getText());
			
			if(!email)
			{
				alertMessage(CompanyMaster.this, "Email Id is not correct","Company Master",2);
				email_id.setText("");
				email_id.requestFocus();
				email_id.setSelectionStart(0);
			}
			else
*/			{
				int i=cmpDao.updateCompanyInfo(cmpDto);
				if(i==1)
					alertMessage(CompanyMaster.this, "Record has been saved successfully","Company Master",1);
				else
					alertMessage(CompanyMaster.this, "Error while saving","Company Master",2);
			}	
		 
		
	 
		 
		 
		//trans_code.requestFocus();
	}
	
 
	
	
	
	
	
	public void fillData()
	{

		cmp_code.setText(String.valueOf(loginDt.getDepo_code()));
		cmp_name.setText(cmpDto.getCmp_name());
		address1.setText(cmpDto.getCmp_add1());
		address2.setText((cmpDto.getCmp_add2().split(",")[0]));
		System.out.println("fill data "+cmpDto.getCmp_add2());
		city.setText(cmpDto.getCmp_ct());
		address3.setText(cmpDto.getCmp_add3());
		pin.setText(cmpDto.getCmp_pin());
		phone.setText(cmpDto.getCmp_phone());
		fax.setText(cmpDto.getCmp_fax());
		email_id.setText(cmpDto.getCmp_email());
		mobile_no.setText(cmpDto.getCmp_mobile());
		aadharno.setText(cmpDto.getAadhar_no());
		panno.setText(cmpDto.getCmp_panno());
		gstno.setText(cmpDto.getCmp_gst());
		//mstate_name.sette
	}
		

	public void getData()
	{

		cmpDto.setCmp_depo(loginDt.getDepo_code());
		cmpDto.setCmp_add1(address1.getText());
		cmpDto.setCmp_add2(address2.getText());
		cmpDto.setCmp_add3(address3.getText());
		cmpDto.setCmp_ct(city.getText());
		cmpDto.setCmp_pin(pin.getText());
		cmpDto.setCmp_phone(phone.getText());
		cmpDto.setCmp_fax(fax.getText());
		cmpDto.setCmp_email(email_id.getText());
		cmpDto.setCmp_panno(panno.getText());
		cmpDto.setCmp_gst(gstno.getText());
		cmpDto.setCmp_mobile(mobile_no.getText());
		cmpDto.setAadhar_no(aadharno.getText());
		if(gstreg.getSelectedIndex()==0)
			cmpDto.setCmp_reg("Y");
		else
			cmpDto.setCmp_reg("N");
	}
	
	 
	public void setVisible(boolean b)
	{
		cmpDto = cmpDao.getCompInfo(loginDt.getDepo_code());
		fillData();
		super.setVisible(b);
	}
}


