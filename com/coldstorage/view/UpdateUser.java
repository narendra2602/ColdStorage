package com.coldstorage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.UserDAO;
import com.coldstorage.dto.BranchDto;
import com.coldstorage.dto.LoginDto;
import com.coldstorage.dto.UserDto;

public class UpdateUser extends BaseClass  implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel lblBankers;
	private JLabel lblOrderNo;
	private JLabel lblChallanNo;
	private JLabel lblInvoiceDate;
	// //////////// textField on Screen//////////////////////////
	private JTextField fname;
	private JTextField lname;
	private JTextField department;
	private JTextField emailId;
	private JLabel lblNewLabel;
	private JLabel lblInoviceNo;
	private JButton saveButton;
	private JButton exitButton;

	
	private JTextField designation;
	private JTextField username;
	private JPasswordField password;
	private JPasswordField cpassword;
	private JLabel lblPersonalInformation;
	private JLabel lblUserType;
	private JLabel lblUserPackage;
	private JCheckBox sampleMF,sampleMF2,sampleMF3,sampleMF4;
	private JCheckBox sampleTF;
	private JCheckBox sampleGenetica;
	private JLabel lblUserDivision;
	private JPanel panel_1;
	private JPanel panel_8;
	private JPanel panel_2;
	private DefaultTableModel branchTableModel;
	private JScrollPane reportPane;
	private JPanel panel_11;
	private JLabel lblReports;
	private JScrollPane entryPane;
	private DefaultTableModel EntryTableModel;
	private JTable entryTable;
	private DefaultTableModel ReportTableModel;
	private JTable reportTable;
	private JRadioButton entrySelective;
	private JRadioButton entryAll;
	private JRadioButton reportSelective;
	private JRadioButton reportAll;
	private JComboBox isActivated;
	UserDAO userDao=null;
	private Vector reportVector;
	private Vector entryVector;
	
	private Vector tempReportVector;
	private Vector tempEntryVector;
	
	private JCheckBox saleMF,saleMF2,saleMF3,saleMF4;
	private JCheckBox saleTF;
	private JCheckBox saleGenetica;
	private JComboBox prefex;
	private JTextField userSearch;
	private JScrollPane userListPane;
	private JList userList;
	private Vector<Object> userDataList;
	private int loginID;
	private List<UserDto> allReportList;
	
	Font font,fontHindi;
	//Constructor ////////////////////////
	public UpdateUser() 
	{
		getContentPane().setLayout(null);
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 670);	
//		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		arisleb.setVisible(false);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Edit User");
		
		
		fontHindi = new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14);

		
		
/*		JLabel promleb = new JLabel(promLogo);
		promleb.setBounds(32, 681, 35, 35);
		getContentPane().add(promleb);
*/
		userDao = new UserDAO();
		tempReportVector = new Vector();
        tempEntryVector = new Vector();
        
        userDataList =userDao.getUserForUpdation();
        
		// /////////////// form buttons//////////////////////
		saveButton = new JButton("Save");
		saveButton.setBounds(798, 530, 88, 30);
		
		getContentPane().add(saveButton);
		saveButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					//clearAll();
				}
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					saveButton.setBackground(null);
					exitButton.setBackground(null);
				}
			}
		});
		
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(896, 530, 88, 30);
		getContentPane().add(exitButton);
		saveButton.addActionListener(this);
		saveButton.setActionCommand("saveButton");
		
		
 		exitButton.addActionListener(new ActionListener() 
 		{
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
				clearAll();
			}
		});


 		
 		
 		lblNewLabel = new JLabel("Title:");
		lblNewLabel.setBounds(342, 45, 110, 20);
		getContentPane().add(lblNewLabel);

		
		prefex = new JComboBox();
		prefex.setModel(new DefaultComboBoxModel(new String[] {"Mr.", "Ms."}));
		prefex.setBounds(440, 45, 59, 20);
		getContentPane().add(prefex);
		
		JLabel lblSelectUser = new JLabel("Select User");
		lblSelectUser.setBounds(19, 45, 110, 20);
		getContentPane().add(lblSelectUser);
		
		
		userSearch = new JTextField();
		userSearch.setBounds(140, 45, 190, 22);
		getContentPane().add(userSearch);
		

		userList = new JList(userDataList);
		userList.setSelectedIndex(0);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		userListPane = new JScrollPane(userList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		userListPane.setBounds(140, 67, 359, 260);
		getContentPane().add(userListPane);
		userListPane.setVisible(false);
 		
		userSearch.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					userListPane.setVisible(true);
					userList.requestFocus();
					userList.setSelectedIndex(0);

					
					
					//String searchText = userSearch.getText().toString().trim();
					
				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					userListPane.setVisible(false);
					
				}
			}
		});
		
		userList.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					userListPane.setVisible(false);
					//String searchText = userSearch.getText().toString().trim();
					
					LoginDto ldto = (LoginDto) userDataList.get(userList.getSelectedIndex());
					
					setDataOnScreen(ldto);
					
					
				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					userListPane.setVisible(true);
				}
			}
		});
		
		lblInoviceNo = new JLabel("First Name:");
		lblInoviceNo.setBounds(19, 70, 110, 20);
		getContentPane().add(lblInoviceNo);

		
		
		
		fname = new JTextField();
		fname.setBounds(140, 71, 190, 22);
		getContentPane().add(fname);
		
		
		
		
		lblInvoiceDate = new JLabel("Designation:");
		lblInvoiceDate.setBounds(19, 96, 110, 20);
		getContentPane().add(lblInvoiceDate);

		lblBankers = new JLabel("Department:");
		lblBankers.setBounds(342, 96, 98, 20);
		getContentPane().add(lblBankers);

		department = new JTextField();
		department.setBounds(440, 98, 190, 22);
		getContentPane().add(department);

		lblOrderNo = new JLabel("Last Name:");
		lblOrderNo.setBounds(342, 70, 98, 20);
		getContentPane().add(lblOrderNo);

		lname = new JTextField();
		lname.setBounds(440, 71, 190, 22);
		getContentPane().add(lname);

		emailId = new JTextField();
		emailId.setBounds(140, 125, 490, 22);
		getContentPane().add(emailId);


		lblChallanNo = new JLabel("Email Id:");
		lblChallanNo.setBounds(19, 124, 110, 20);
		getContentPane().add(lblChallanNo);

	
		


		
		

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 516, 998, 60);
		getContentPane().add(panel);
		
		 
		designation = new JTextField();
		designation.setBounds(140, 98, 190, 22);
		getContentPane().add(designation);
		
		JLabel lblLoginId = new JLabel("Username:");
		lblLoginId.setBounds(662, 45, 110, 20);
		getContentPane().add(lblLoginId);
		
		username = new JTextField();
		username.setBounds(783, 44, 190, 22);
		getContentPane().add(username);
		username.setEditable(false);
		
		
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(662, 70, 110, 20);
		getContentPane().add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(783, 71, 190, 22);
		getContentPane().add(password);
		password.setEditable(false);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setBounds(662, 96, 133, 20);
		getContentPane().add(lblConfirmPassword);
		
		cpassword = new JPasswordField();
		cpassword.setBounds(783, 98, 190, 22);
		getContentPane().add(cpassword);
		cpassword.setEditable(false);
		
		JLabel lblLoginInformation = new JLabel("Login Information");
		lblLoginInformation.setForeground(Color.WHITE);
		lblLoginInformation.setBounds(670, 16, 133, 20);
		getContentPane().add(lblLoginInformation);
		
		lblPersonalInformation = new JLabel("Personal Information");
		lblPersonalInformation.setForeground(Color.WHITE);
		lblPersonalInformation.setBounds(23, 14, 133, 20);
		getContentPane().add(lblPersonalInformation);
		
		lblUserType = new JLabel("Entry");
		lblUserType.setForeground(Color.WHITE);
		lblUserType.setBounds(23, 164, 149, 20);
		getContentPane().add(lblUserType);
		
		JLabel lblStatusActive = new JLabel("Status Active:");
		lblStatusActive.setBounds(662, 124, 110, 20);
		getContentPane().add(lblStatusActive);
		
		isActivated = new JComboBox();
		isActivated.setModel(new DefaultComboBoxModel(new String[] {"Yes", "No"}));
		isActivated.setBounds(783, 125, 59, 20);
		getContentPane().add(isActivated);

		
	
		
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBackground(SystemColor.textHighlight);
		panel_1.setBounds(10, 160, 495, 27);
		getContentPane().add(panel_1);
		
		panel_8 = new JPanel();
		panel_8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_8.setBackground(SystemColor.textHighlight);
		panel_8.setBounds(653, 11, 354, 27);
		getContentPane().add(panel_8);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBackground(SystemColor.textHighlight);
		panel_2.setBounds(10, 11, 635, 27);
		getContentPane().add(panel_2);
		
		prefex.setName("0");
		fname.setName("1");
		lname.setName("2");
		designation.setName("3");
		department.setName("4");
		emailId.setName("5");
		username.setName("6");
		password.setName("7");
		cpassword.setName("8");
		
		
		// /////////////////////////////////////////////////////////////////////////////////////////////
		KeyListener keyListener = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				int key = keyEvent.getKeyCode();
				int id =0;
				try
				{
					JTextField textField = (JTextField) keyEvent.getSource();
					id= Integer.parseInt(textField.getName());
				}
				catch(Exception e)
				{
					JComboBox textField = (JComboBox) keyEvent.getSource();
					id= Integer.parseInt(textField.getName());	
				}
				
				
				if (key == KeyEvent.VK_ENTER) 
				{
					switch (id) 
					{
					case 0:
						fname.requestFocus();
						fname.setSelectionStart(0);
						break;
					case 1:
						lname.requestFocus();
						lname.setSelectionStart(0);
						break;
					case 2:
						designation.requestFocus();
						designation.setSelectionStart(0);
						break;
					case 3:
						department.requestFocus();
						department.setSelectionStart(0);
						break;
					case 4:
						emailId.requestFocus();
						emailId.setSelectionStart(0);
						break;
					case 5:
						username.requestFocus();
						username.setSelectionStart(0);
						break;	
					case 6:
						password.requestFocus();
						password.setSelectionStart(0);
						break;	
					case 7:
						cpassword.requestFocus();
						cpassword.setSelectionStart(0);
						break;	
					case 8:
				 		isActivated.requestFocus();
						break;
					}
				}

				///// up aerrow  ///   
				if (key == KeyEvent.VK_UP)
				{
					switch (id)
					{
					case 1:
						prefex.requestFocus();
						break;
					case 2:
						fname.requestFocus();
						break;
					case 3:
						lname.requestFocus();
						break;
					case 4:
						designation.requestFocus();
						break;
					case 5:
						department.requestFocus();
						break;
					case 6:
						emailId.requestFocus();
						break;
					case 7:
						username .requestFocus();
						break;

					case 8:
						password.requestFocus();
						password.setSelectionStart(0);
						break;
					 
					 
					}
				}
				
				if (key == KeyEvent.VK_ESCAPE) 
				{
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
		prefex.addKeyListener(keyListener);
		fname.addKeyListener(keyListener);
		lname.addKeyListener(keyListener);
		designation.addKeyListener(keyListener);
		department.addKeyListener(keyListener);
		emailId.addKeyListener(keyListener);
		username.addKeyListener(keyListener);
		password.addKeyListener(keyListener);
		cpassword.addKeyListener(keyListener);

		//////////////////////////////////////////////////////////////////////
		String[] crDrColName = {"","Branch Code","Branch Name",""};
		String cdDrData[][] = {};
		branchTableModel = new DefaultTableModel(cdDrData, crDrColName) 
		{
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
				case 0:
					return Boolean.class;
				default:
					return String.class;
				}
			}
		};

		//fillTable();
		
		//////////////////////////////////////////////////////////////////////
		String[] entryColo = {"","Pack Name","Tab Name","Report Name",""};
		String entryData[][] = {};
		EntryTableModel = new DefaultTableModel(entryData, entryColo) 
		{
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
				case 0:
					return Boolean.class;
				default:
					return String.class;
				}
			}
		};

		entryVector = userDao.getUserRights("E");
		
		entryTable = new JTable(EntryTableModel);
		entryTable.setColumnSelectionAllowed(false);
		entryTable.setCellSelectionEnabled(false);
		entryTable.getTableHeader().setReorderingAllowed(false);
		entryTable.getTableHeader().setResizingAllowed(false);
		entryTable.setRowHeight(20);
		entryTable.setFont(new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14));
		entryTable.getTableHeader().setPreferredSize(new Dimension(25, 25));

		entryTable.getColumnModel().getColumn(0).setPreferredWidth(50);
//		entryTable.getColumnModel().getColumn(1).setPreferredWidth(170);
		entryTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		entryTable.getColumnModel().getColumn(3).setPreferredWidth(700);


		entryTable.getColumnModel().getColumn(1).setWidth(0); // hidden field
		entryTable.getColumnModel().getColumn(1).setMinWidth(0); // no
		entryTable.getColumnModel().getColumn(1).setMaxWidth(0); // no

		
		entryTable.getColumnModel().getColumn(4).setWidth(0); // hidden field
		entryTable.getColumnModel().getColumn(4).setMinWidth(0); // no
		entryTable.getColumnModel().getColumn(4).setMaxWidth(0); // no

		//fillEntryTable();
		
		
		//////////////////////////////////////////////////////////////////////////
		entryPane = new JScrollPane(entryTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		entryPane.setBounds(10, 225, 495, 285);
		getContentPane().add(entryPane);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_9.setBounds(653, 11, 354, 144);
		getContentPane().add(panel_9);
		
	 
		//////////////////////////////////////////////////////////////////////
		String[] reportCol = {"","Pack Name","Tab Name","Report Name",""};
		String reportData[][] = {};
		ReportTableModel = new DefaultTableModel(reportData, reportCol) 
		{
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
				case 0:
					return Boolean.class;
				default:
					return String.class;
				}
			}
		};
		
		reportVector = userDao.getUserRights("R");
		
		reportTable = new JTable(ReportTableModel);
		reportTable.setColumnSelectionAllowed(false);
		reportTable.setCellSelectionEnabled(false);
		reportTable.getTableHeader().setReorderingAllowed(false);
		reportTable.getTableHeader().setResizingAllowed(false);
		reportTable.setRowHeight(20);
		reportTable.setFont(new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14));
		reportTable.getTableHeader().setPreferredSize(new Dimension(25, 25));

		reportTable.getColumnModel().getColumn(0).setPreferredWidth(50);
//		reportTable.getColumnModel().getColumn(1).setPreferredWidth(170);
		reportTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		reportTable.getColumnModel().getColumn(3).setPreferredWidth(700);


		
		reportTable.getColumnModel().getColumn(1).setWidth(0); // hidden field
		reportTable.getColumnModel().getColumn(1).setMinWidth(0); // no
		reportTable.getColumnModel().getColumn(1).setMaxWidth(0); // no

		
		reportTable.getColumnModel().getColumn(4).setWidth(0); // hidden field
		reportTable.getColumnModel().getColumn(4).setMinWidth(0); // no
		reportTable.getColumnModel().getColumn(4).setMaxWidth(0); // no

		//fillReportTable();
		
		reportPane = new JScrollPane(reportTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		reportPane.setBounds(511, 225, 495, 285);
		getContentPane().add(reportPane);
		/////
		
		lblReports = new JLabel("Reports");
		lblReports.setForeground(Color.WHITE);
		lblReports.setBounds(523, 164, 149, 20);
		getContentPane().add(lblReports);
		
		panel_11 = new JPanel();
		panel_11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_11.setBackground(SystemColor.textHighlight);
		panel_11.setBounds(511, 159, 495, 27);
		getContentPane().add(panel_11);
		
		entrySelective = new JRadioButton("Selective");
		entrySelective.setSelected(true);
		entrySelective.setBounds(20, 191, 109, 23);
		getContentPane().add(entrySelective);
		
		entryAll = new JRadioButton("All");
		entryAll.setBounds(138, 191, 109, 23);
		getContentPane().add(entryAll);
		
		
		//Group the radio buttons.
	    ButtonGroup entryGrp = new ButtonGroup();
	    entryGrp.add(entrySelective);
	    entryGrp.add(entryAll);
		
		
		
		reportAll = new JRadioButton("All");
		reportAll.setBounds(639, 191, 109, 23);
		getContentPane().add(reportAll);
		
		reportSelective = new JRadioButton("Selective");
		reportSelective.setSelected(true);
		reportSelective.setBounds(521, 191, 109, 23);
		getContentPane().add(reportSelective);
		
		//Group the radio buttons.
	    ButtonGroup reportGrp = new ButtonGroup();
	    reportGrp.add(reportSelective);
	    reportGrp.add(reportAll);
		
	    
	    entrySelective.addActionListener(this);
	    entryAll.addActionListener(this);
	    entrySelective.setActionCommand("entrySelective");
	    entryAll.setActionCommand("entryAll");
	    
	    
	    reportSelective.addActionListener(this);
	    reportAll.addActionListener(this);
	    reportSelective.setActionCommand("reportSelective");
	    reportAll.setActionCommand("reportAll");


	    /*saleMF.setEnabled(false);
		saleTF.setEnabled(false);
		saleGenetica.setEnabled(false);
		
		sampleMF.setEnabled(false);
		sampleTF.setEnabled(false);
		sampleGenetica.setEnabled(false);*/
	    
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(10, 186, 495, 35);
		getContentPane().add(panel_4);
	    
	    
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_10.setBounds(511, 186, 495, 35);
		getContentPane().add(panel_10);
		
		
				
		
		/////////////////////////////////////////////////
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(10, 11, 635, 144);
		getContentPane().add(panel_3);

		
	
		// / setting default focus request
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) 
			{
			 
					//party_code.requestFocus();
				clearAll();
				
			}
			
			public void windowClosed(WindowEvent e) {
				clearAll();
			}
		});

		// setAlwaysOnTop(true);
	 

	}

	 
	

  

	

	public void clearAll() 
	{
		prefex.setSelectedIndex(0);
		fname.setText("");
		lname.setText("");
		designation.setText("");
		department.setText("");
		emailId.setText("");
		username.setText("");
		password.setText("");
		cpassword.setText("");
		isActivated.setSelectedIndex(0);
		
		
		branchTableModel.getDataVector().removeAllElements();
		branchTableModel.fireTableDataChanged();

		tempReportVector.removeAllElements();
		tempEntryVector.removeAllElements();

		EntryTableModel.getDataVector().removeAllElements();
		EntryTableModel.fireTableDataChanged();

		ReportTableModel.getDataVector().removeAllElements();
		ReportTableModel.fireTableDataChanged();


		//fill default branch table..
		//fillTable();
		userSearch.requestFocus();
		
		
	}

	
	public void setDataOnScreen(LoginDto ldto)
	{
		
		
		fname.setText(ldto.getFname());
		lname.setText(ldto.getLname());
		designation.setText(ldto.getDesignation());
		department.setText(ldto.getDepartment());
		emailId.setText(ldto.getEmaiId());
		username.setText(ldto.getLogin_name());
		password.setText(ldto.getLogin_pass());
		cpassword.setText(ldto.getLogin_pass());
		isActivated.setSelectedIndex(ldto.getIsActive().equals("Y") ? 0 : 1);
		prefex.setSelectedIndex(ldto.getPrefix().equals("Mr.") ? 0 : 1 );

		//tempReportVector.removeAllElements();
		//tempReportVector.addAll(ldto.getReportList());
		
		allReportList = ldto.getReportList();
		
		loginID=ldto.getLogin_id();
		
		//Filling Branch Table..
/*		fillTable(ldto.getBranchList());
		packageList(ldto.getPackageList());
		divisionList(ldto.getDivisionList());
*/		fillEntryAndReportTable(ldto.getReportList());
		
	}
	
	
	
	public void fillTable(List<BranchDto> branchList)
	{
		
		branchTableModel.getDataVector().removeAllElements();
		branchTableModel.fireTableDataChanged();
		
		BranchDto brnDt = null;
		int s =branchList.size(); 
		for(int i =0;i<s;i++)
		{
			brnDt =(BranchDto) branchList.get(i);
			branchTableModel.addRow(new Object[]{brnDt.getStatus().equals("Y") ? true : false,brnDt.getDepo_code(),brnDt.getDepo_name(),brnDt});
		} 
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		
		String command = e.getActionCommand();
		
		//Entries
		if(command.equalsIgnoreCase("entrySelective"))
		{
			entryTableSelection(false);
		}
		if(command.equalsIgnoreCase("entryAll"))
		{
			entryTableSelection(true);
		}
		
		//Reports
		if(command.equalsIgnoreCase("reportSelective"))
		{
			 
			reportTableSelection(false);
			
		}
		if(command.equalsIgnoreCase("reportAll"))
		{
			reportTableSelection(true);
		}

		//TODO
		//submit
		if(command.equalsIgnoreCase("saveButton"))
		{
			boolean check=false;
			LoginDto loginDto = validatePersonalInfo();
			
			if(loginDto!=null)
			{
				Object[] reportResponse = validateReportSelection();
				loginDto.setReportList((List)reportResponse[1]);
				int userId=userDao.updateUser(loginDto);
				if(userId!=0)
				{
					JOptionPane.showMessageDialog(this, "User Update Successfully ");
					userDataList =userDao.getUserForUpdation();
					userList.removeAll();
					userList.setListData(userDataList);
					clearAll();
				}

				
/*				Object[] reportResponse = validateReportSelection();
				if((Boolean)reportResponse[0]  && check)
				{
					loginDto.setReportList((List)reportResponse[1]);
					System.out.println("save ke andar ");
					int userId=userDao.updateUser(loginDto);
					if(userId!=0)
					{
						JOptionPane.showMessageDialog(this, "User Update Successfully ");
						userDataList =userDao.getUserForUpdation();
						userList.removeAll();
						userList.setListData(userDataList);
						clearAll();
					}
					
				}
				else
				{
					if(check)
					{
						JOptionPane.showMessageDialog(this, "Select entry/report");
					}
				}
*/			}
			
			
		}
		
		
	     
	}
	
	
/*	public List packageList()
	{
		List packList=new ArrayList();

		if(salePack.isSelected())
			packList.add(1);

		if(samplePack.isSelected())
			packList.add(2);

		if(accountPack.isSelected())
			packList.add(3);
		
		return packList; 
	}
	
*/	
	
	public void filterData()
	{
		
		tempReportVector.removeAllElements();
        tempEntryVector.removeAllElements();
		
		Vector<?> v = reportVector;
		UserDto userDto = null;
		int s =v.size(); 
		for(int i =0;i<s;i++)
		{
			userDto =(UserDto) v.get(i);
			
		} 
        
		Vector<?> v1 = entryVector;
		UserDto userDto1 = null;
		int s1 =v1.size(); 
		for(int i =0;i<s1;i++)
		{
			userDto1 =(UserDto) v1.get(i);
			
		} 
		
		
		
	}
	
	
	
	public void fillEntryAndReportTable(List<UserDto> reportList)
	{
		EntryTableModel.getDataVector().removeAllElements();
		EntryTableModel.fireTableDataChanged();
		
		ReportTableModel.getDataVector().removeAllElements();
		ReportTableModel.fireTableDataChanged();

		
		
		int size= reportList.size();
		UserDto userDto = null;
		
		for(int i =0;i<size;i++)
		{
			userDto =(UserDto) reportList.get(i);
			if(userDto.getTran_type().equals("E"))
			{
				EntryTableModel.addRow(new Object[]{userDto.getStatus().equals("Y") ? true : false,userDto.getPack_name(),userDto.getTab_name(),userDto.getMenu_name(),userDto});
			}
			if(userDto.getTran_type().equals("R"))
			{
				ReportTableModel.addRow(new Object[]{userDto.getStatus().equals("Y") ? true : false,userDto.getPack_name(),userDto.getTab_name(),userDto.getMenu_name(),userDto});
			}
		}
	}
	
	
	
	
	
	public void entryTableSelection(boolean res)
	{
		EntryTableModel.getDataVector().removeAllElements();
		EntryTableModel.fireTableDataChanged();
		
		Vector v = tempEntryVector;
		int size= v.size();
		UserDto userDto = null;
		
		for(int i =0;i<size;i++)
		{
			userDto =(UserDto) v.get(i);
			EntryTableModel.addRow(new Object[]{res,userDto.getPack_name(),userDto.getTab_name(),userDto.getMenu_name(),userDto});
		}
	}
	
	public void reportTableSelection(boolean res)
	{
		ReportTableModel.getDataVector().removeAllElements();
		ReportTableModel.fireTableDataChanged();

		Vector v = tempReportVector;
		int size= v.size();
		UserDto userDto = null;
		
		for(int i =0;i<size;i++)
		{
			userDto =(UserDto) v.get(i);
			
			
			ReportTableModel.addRow(new Object[]{res,userDto.getPack_name(),userDto.getTab_name(),userDto.getMenu_name(),userDto});
		}
	}
	
	
	public void reportTableSelection()
	{
		ReportTableModel.getDataVector().removeAllElements();
		ReportTableModel.fireTableDataChanged();
		
		EntryTableModel.getDataVector().removeAllElements();
		EntryTableModel.fireTableDataChanged();
		
		int size= allReportList.size();
		System.out.println("Origional size of list is "+size);
		
		UserDto userDto = null;
		
		
		for(int i =0;i<size;i++)
		{
			userDto =(UserDto) allReportList.get(i);
			
				
		}
	}
	
	public LoginDto validatePersonalInfo()
	{
		if(fname.getText().trim().length()==0)
		{
			JOptionPane.showMessageDialog(this, "First Name cannot be blank");
			fname.requestFocus();
			return null;
		}
		if(lname.getText().trim().length()==0)
		{
			JOptionPane.showMessageDialog(this, "Last Name cannot be blank");
			lname.requestFocus();
			return null;
		}
		if(designation.getText().trim().length()==0)
		{
			JOptionPane.showMessageDialog(this, "Designation cannot be blank");
			designation.requestFocus();
			return null;
		}
		if(department.getText().trim().length()==0)
		{
			JOptionPane.showMessageDialog(this, "Department cannot be blank");
			department.requestFocus();
			return null;
		}
		if(emailId.getText().trim().length()==0)
		{
			JOptionPane.showMessageDialog(this, "Email Id cannot be blank");
			emailId.requestFocus();
			return null;
		}
		if(username.getText().trim().length()==0)
		{
			JOptionPane.showMessageDialog(this, "Username cannot be blank");
			username.requestFocus();
			return null;
		}
		if(password.getText().trim().length()==0)
		{
			JOptionPane.showMessageDialog(this, "Password cannot be blank");
			password.requestFocus();
			return null;
		}
		if(cpassword.getText().trim().length()==0)
		{
			JOptionPane.showMessageDialog(this, "Confirm password cannot be blank");
			cpassword.requestFocus();
			return null;
		}
		if(!password.getText().trim().equals(cpassword.getText().trim()))
		{
			JOptionPane.showMessageDialog(this, "Password and confirm password mismatch!");
			return null;
		}
		
		LoginDto loginDto=new LoginDto();
		loginDto.setLogin_id(loginID);
		loginDto.setPrefix(prefex.getSelectedIndex()==0 ? "Mr." : "Ms.");
		loginDto.setFname(fname.getText());
		loginDto.setLname(lname.getText());
		loginDto.setDesignation(designation.getText());
		loginDto.setDepartment(department.getText());
		loginDto.setEmaiId(emailId.getText());
		loginDto.setLogin_name(username.getText());
		loginDto.setLogin_pass(password.getText());
		loginDto.setIsActive(isActivated.getSelectedIndex()==0 ? "Y" : "N");


		return loginDto;

	}
 
	
	
	public Object[] validateReportSelection()
	{
		Object[] response=new Object[2];
		Vector col = null;
		ArrayList dataList = new ArrayList();
		boolean selection=false;
		int size=0;
		Vector data =null;
		
		
		data = ReportTableModel.getDataVector();
		size=data.size();
		for (int i = 0; i < size; i++) 
		{
			col = (Vector) data.get(i);
			
			UserDto udt = (UserDto)col.get(4);
			if ((Boolean) col.get(0)) 
			{
				udt.setStatus("Y");
				selection=true;
			}
			else
				udt.setStatus("N");
			
			dataList.add(udt);//return UserDTO object
			
		}
		
		data = EntryTableModel.getDataVector();
		size=data.size();
		
		for (int i = 0; i < size; i++) 
		{
			col = (Vector) data.get(i);
			UserDto udt = (UserDto)col.get(4);
			if ((Boolean) col.get(0)) 
			{
				udt.setStatus("Y");
				selection=true;
			}
			else
				udt.setStatus("N");
			
			dataList.add(udt);//return UserDTO object
		}
		
		System.out.println("Selected Report count is "+dataList.size());
		
		response[1]=dataList;
		response[0]=selection;
		
		
		return response;
	}
	
	
	
		
}// / class end////////////////




