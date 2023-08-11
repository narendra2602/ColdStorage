package com.coldstorage.view;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.coldstorage.dao.LoginDAO;
import com.coldstorage.dao.UserDAO;
import com.coldstorage.dto.BranchDto;
import com.coldstorage.dto.DivisionDto;

public class PackageOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	 
	 
	private JPanel panel_1;
	//	private JLabel lblAccountingYear;
	LoginDAO ldao;
	private JButton exitButton,submitButton;
	private JLabel lblselect;
	private JComboBox spackage ;
	private String uname,pass; 
    private Vector<?> packlst,divlst;
    private JPanel panel_2;
    private JPanel panel_3;
    private JLabel lblCompanySelection;
    int uid;
    BranchDto brn;
    public PackageOpt(String uname,String pass)
	{
		///////// frame setting////////////////////////////////////////////////		
//		infoName =(String) helpImg.get(getClass().getSimpleName());
		
		this.uname=uname;
		this.pass=pass;
		
		UserDAO udao = new UserDAO();
		uid=udao.getUserId(uname,pass);

		packlst = udao.getBranch(uid);
		divlst = (Vector) udao.getDivision(1,uid)[0];
		
		
		brn = (BranchDto)packlst.get(0); 

		
		//setTitle("Aristo Pharmaceuticals Pvt. Ltd.");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximize both the horizontal and vertical directions

		
/*		setSize(1024, 768);
		setResizable(false);
*///		setLocationRelativeTo(null);
		
		getContentPane().setLayout(null);
		arisleb.setVisible(false);

		/////////////////////////////////////
		arisLogo = new ImageIcon(getClass().getResource("/images/aris_log.png"));
		//arisLogo1 = new ImageIcon(getClass().getResource("/images/Nakoda.png"));
		
		///////////////////////////////////////////////////////////////////////////////////////////////

		exitButton = new JButton("Exit");
		exitButton.setBounds((screenWidth/2)+80,(screenHeight/2)-20, 82, 28);
		getContentPane().add(exitButton);
		exitButton.addActionListener(this);
		
		
		lblselect = new JLabel("Select:");
		lblselect.setBounds((screenWidth/2)-130,(screenHeight/2)-90, 100, 28);
		getContentPane().add(lblselect);
		
		spackage = new JComboBox(divlst);
		spackage.setActionCommand("division");
		spackage.setBounds((screenWidth/2)-70,(screenHeight/2)-90, 280, 35);
		getContentPane().add(spackage);
		spackage.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent evt)
			{
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					submitButton.requestFocus();
				}
				evt.consume();
			}
		});

		
		submitButton = new JButton("Submit");
		submitButton.setBounds((screenWidth/2)-60,(screenHeight/2)-20, 106, 28);
		getContentPane().add(submitButton);
		submitButton.addActionListener(this);
		submitButton.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent evt)
			{
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					submitForm();
					dispose();
				}
				evt.consume();
			}
		});
		
		
		lblCompanySelection = new JLabel("Group Selection");
		lblCompanySelection.setForeground(Color.WHITE);
		lblCompanySelection.setFont(new Font("Verdana", Font.BOLD, 13));
		lblCompanySelection.setBounds((screenWidth/2)-139,(screenHeight/2)-178, 180, 20);
		getContentPane().add(lblCompanySelection);

		
//		getContentPane().add(arisleb);


		/////////// footer///////////////////////////////////////////////////////////

		promLogo1= new ImageIcon(getClass().getResource("/images/plogo.png"));
		JLabel promleb = new JLabel(promLogo1);
//		promleb.setBounds(12, 680, 35, 35);
		promleb.setBounds(10, screenHeight-(screenHeight/8)+30, 35, 35);
		getContentPane().add(promleb);


		getContentPane().add(contact);


		getContentPane().add(lblPromptSoftwareConsultants);

		getContentPane().add(lblAddress);

		getContentPane().add(lblPhone);

		panel_1 = new JPanel();  // bottom
		panel_1.setBackground(UIManager.getColor("TextPane.selectionBackground"));
		panel_1.setBounds(0, (screenHeight-(screenHeight/8)), screenWidth, 3);
		getContentPane().add(panel_1);

		 


		

		
		////////////////////////////


 
		JLabel label_2 = new JLabel(brn.getDepo_name());
		label_2.setForeground(Color.DARK_GRAY);
		label_2.setFont(new Font("Arial", Font.BOLD , 24));
		label_2.setBounds(5, 30, 650, 29);
		getContentPane().add(label_2);

		JPanel panel = new JPanel();  // top
		panel.setBackground(new Color(0, 96, 192));
		panel.setBounds(0, 60, screenWidth, 3);
		getContentPane().add(panel);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
//		panel_2.setBounds(330, 277, 368, 173);
		panel_2.setBounds((screenWidth/2)-150,(screenHeight/2)-150, 368, 173);

		getContentPane().add(panel_2);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBackground(SystemColor.textHighlight);
		panel_3.setBounds((screenWidth/2)-150,(screenHeight/2)-183, 368, 37);
//		panel_3.setBounds(330, 244, 368, 37);
		getContentPane().add(panel_3);
		
		

		setAlwaysOnTop(true);


//		BranchDto brn = (BranchDto) spackage.getSelectedItem();
//		new DivOpt(uname, pass,null,0).setVisible(true);
		
//		DivisionDto div= (DivisionDto) spackage.getSelectedItem();
//		new DivOpt(uname, pass,null,div.getDiv_code(),div.getDiv_name()).setVisible(true);


	}
 
	
	public void resetAll()
	 {
		 
				 spackage.setSelectedIndex(0);
	 }		 
	 

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			dispose();
		}
		
	    if(e.getActionCommand().equalsIgnoreCase("Submit") )
	    {
			try
			{
				submitForm();
				dispose();
			}
			catch(Exception ez)
			{
				System.out.println("error "+ez.getStackTrace());
			}

		}

		
	}
	
	public void submitForm()
	{
//		BranchDto brn = (BranchDto) spackage.getSelectedItem();
	
		DivisionDto div= (DivisionDto) spackage.getSelectedItem();
		new DivOpt(uname, pass,null,div.getDiv_code(),div.getDiv_name()).setVisible(true);
		resetAll();
	}
	
}