package com.coldstorage.view;

import java.awt.Color;
import java.awt.Dimension;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.UserDAO;
import com.coldstorage.dto.BranchDto;
import com.coldstorage.dto.LoginDto;

public class CopyUser extends BaseClass  implements ActionListener 
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
	private JPanel panel_2;
	private DefaultTableModel branchTableModel;
	private DefaultTableModel EntryTableModel;
	private DefaultTableModel ReportTableModel;
	private JComboBox isActivated;
	UserDAO userDao=null;
	 
	private Vector<Object> userDataList;
	private JComboBox prefex;
	private JComboBox userToCopy;

	public CopyUser() 
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
		setTitle("New User ");
	
/*		JLabel promleb = new JLabel(promLogo);
		promleb.setBounds(32, 681, 35, 35);
		getContentPane().add(promleb);
*/
		userDao = new UserDAO();
        userDataList =userDao.getUserForUpdation();
		
		// /////////////// form buttons//////////////////////
		saveButton = new JButton("Save");
		saveButton.setBounds(798, 686, 88, 30);
		
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
		exitButton.setBounds(896, 686, 88, 30);
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
		lblNewLabel.setBounds(19, 45, 140, 20);
		getContentPane().add(lblNewLabel);

		
		prefex = new JComboBox();
		prefex.setModel(new DefaultComboBoxModel(new String[] {"Mr.", "Ms."}));
		prefex.setBounds(166, 45, 59, 20);
		getContentPane().add(prefex);
		
		
		
		
		lblInoviceNo = new JLabel("First Name:");
		lblInoviceNo.setBounds(19, 71, 140, 20);
		getContentPane().add(lblInoviceNo);

		
		
		
		fname = new JTextField();
		fname.setBounds(166, 71, 234, 20);
		getContentPane().add(fname);
		
		
		
		
		lblInvoiceDate = new JLabel("Designation:");
		lblInvoiceDate.setBounds(19, 123, 140, 20);
		getContentPane().add(lblInvoiceDate);

		lblBankers = new JLabel("Department:");
		lblBankers.setBounds(19, 150, 140, 20);
		getContentPane().add(lblBankers);

		department = new JTextField();
		department.setBounds(166, 150, 234, 20);
		getContentPane().add(department);

		lblOrderNo = new JLabel("Last Name:");
		lblOrderNo.setBounds(19, 97, 140, 20);
		getContentPane().add(lblOrderNo);

		lname = new JTextField();
		lname.setBounds(166, 97, 234, 20);
		getContentPane().add(lname);

		emailId = new JTextField();
		emailId.setBounds(166, 176, 490, 20);
		getContentPane().add(emailId);


		lblChallanNo = new JLabel("Email Id:");
		lblChallanNo.setBounds(19, 176, 140, 20);
		getContentPane().add(lblChallanNo);

	
		


		
		

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 668, 998, 60);
		getContentPane().add(panel);
		
		 
		designation = new JTextField();
		designation.setBounds(166, 123, 234, 20);
		getContentPane().add(designation);
		
		JLabel lblLoginId = new JLabel("Username:");
		lblLoginId.setBounds(19, 202, 140, 20);
		getContentPane().add(lblLoginId);
		
		username = new JTextField();
		username.setBounds(166, 202, 234, 20);
		getContentPane().add(username);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(19, 228, 140, 20);
		getContentPane().add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(166, 228, 234, 20);
		getContentPane().add(password);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setBounds(19, 254, 140, 20);
		getContentPane().add(lblConfirmPassword);
		
		cpassword = new JPasswordField();
		cpassword.setBounds(166, 254, 234, 20);
		getContentPane().add(cpassword);
		
		lblPersonalInformation = new JLabel("Personal Information");
		lblPersonalInformation.setForeground(Color.WHITE);
		lblPersonalInformation.setBounds(23, 14, 133, 20);
		getContentPane().add(lblPersonalInformation);
		
		JLabel lblStatusActive = new JLabel("Status Active:");
		lblStatusActive.setBounds(19, 281, 140, 20);
		getContentPane().add(lblStatusActive);
		
		isActivated = new JComboBox();
		isActivated.setModel(new DefaultComboBoxModel(new String[] {"Yes", "No"}));
		isActivated.setBounds(166, 281, 59, 20);
		getContentPane().add(isActivated);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBackground(SystemColor.textHighlight);
		panel_2.setBounds(10, 11, 998, 27);
		getContentPane().add(panel_2);


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
					case 9: 
						userToCopy.requestFocus();
						break;
					case 10: 
						saveButton.requestFocus();
						saveButton.setBackground(Color.blue);
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
					 
					
				}
			}

			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		};
		
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

		fillTable();
		
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
		
		userToCopy = new JComboBox(userDataList);
		userToCopy.setBounds(166, 309, 234, 20);
		getContentPane().add(userToCopy);
		
		JLabel lblCopyRightsFrom = new JLabel("Copy Rights from:");
		lblCopyRightsFrom.setBounds(19, 309, 140, 20);
		getContentPane().add(lblCopyRightsFrom);
		
		
		
	 
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(10, 11, 998, 329);
		getContentPane().add(panel_3);

		
	
		// / setting default focus request
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) 
			{
			 
					//party_code.requestFocus();
				
			}
			
			public void windowClosed(WindowEvent e) {
				clearAll();
			}
		});


		prefex.addKeyListener(keyListener);
		fname.addKeyListener(keyListener);
		lname.addKeyListener(keyListener);
		designation.addKeyListener(keyListener);
		department.addKeyListener(keyListener);
		emailId.addKeyListener(keyListener);
		username.addKeyListener(keyListener);
		password.addKeyListener(keyListener);
		cpassword.addKeyListener(keyListener);
		isActivated.addKeyListener(keyListener);
		userToCopy.addKeyListener(keyListener);
		
		
		prefex.setName("0");
		fname.setName("1");
		lname.setName("2");
		designation.setName("3");
		department.setName("4");
		emailId.setName("5");
		username.setName("6");
		password.setName("7");
		cpassword.setName("8");
		isActivated.setName("9");
		userToCopy.setName("10");
		
		
		
		
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

		
		EntryTableModel.getDataVector().removeAllElements();
		EntryTableModel.fireTableDataChanged();

		ReportTableModel.getDataVector().removeAllElements();
		ReportTableModel.fireTableDataChanged();


		//fill default branch table..
		fillTable();
		
	}

	public void fillTable()
	{
		branchTableModel.getDataVector().removeAllElements();
		Vector<?> v = new UserDAO().getBranch(99);
		BranchDto brnDt = null;
		int s =v.size(); 
		for(int i =0;i<s;i++)
		{
			brnDt =(BranchDto) v.get(i);
			branchTableModel.addRow(new Object[]{false,brnDt.getDepo_code(),brnDt.getDepo_name(),brnDt});
		} 
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		
		String command = e.getActionCommand();
		 
		 
		 
		if(command.equalsIgnoreCase("saveButton"))
		{
			boolean check=false;
			LoginDto loginDto = validatePersonalInfo();
			
			if(loginDto!=null)
			{
				int userId= userDao.copyUserRightFromExistingUser(loginDto);
				if(userId!=0)
				{
					JOptionPane.showMessageDialog(this, "User Created Successfully");
					clearAll();
				}
			}
			
			
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
		loginDto.setPrefix(prefex.getSelectedIndex()==0 ? "Mr." : "Ms.");
		loginDto.setFname(fname.getText());
		loginDto.setLname(lname.getText());
		loginDto.setDesignation(designation.getText());
		loginDto.setDepartment(department.getText());
		loginDto.setEmaiId(emailId.getText());
		loginDto.setLogin_name(username.getText());
		loginDto.setLogin_pass(password.getText());
		loginDto.setIsActive(isActivated.getSelectedIndex()==0 ? "Y" : "N");
		loginDto.setLogin_id(((LoginDto)userToCopy.getSelectedItem()).getLogin_id());
		System.out.println("copy records from this user id "+loginDto.getLogin_id());
		return loginDto;

	}
 
	
	
	
	
	public Object[] validateBranchSelection()
	{
		
		Object[] response=new Object[2];

		Vector col = null;
		List<BranchDto> dataList = new ArrayList<BranchDto>();
		Vector data = branchTableModel.getDataVector();
		int size=0;
		size=data.size();
		boolean selection=false;
		BranchDto brnDto=null;
		for (int i = 0; i < size; i++) 
		{
			col = (Vector) data.get(i);
			brnDto = (BranchDto)col.get(3);
			
			if ((Boolean) col.get(0)) 
			{
				brnDto.setStatus("Y");		
				selection=true;
			}
			else
			{
				brnDto.setStatus("N");
			}
			
			dataList.add(brnDto);//return branchdto object
		}
		
		response[1]=dataList;
		response[0]=selection;
		
		
		return response;
	}
} 




