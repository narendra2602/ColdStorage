package com.coldstorage.view;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.coldstorage.dao.LoginDAO;
import com.coldstorage.dao.UserDAO;
import com.coldstorage.dto.BranchDto;

import de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel;

public class DivOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	 
	private SwingWorker<?,?> worker;
	private JPanel panel_1;
	//	private JLabel lblAccountingYear;
	LoginDAO ldao;

	 
    
    private JPanel panel_2;
    private JPanel panel_3;
    private JLabel lblPackageSelection;
    private BranchDto brn;
	private String uname,pass,divnm; 
    private int uid,div;
    private Icon loadingIcon;
	private JLabel lodingLabel;
	private JProgressBar pb;
	final int MAX=1;
    public DivOpt(String uname,String pass,BranchDto brn,int div,String divnm)
	{
		this.uname=uname;
		this.pass=pass;
		this.div=div;
		this.divnm=divnm;
		
		System.out.println("div ki value "+div);
		System.out.println("div name ki value "+divnm);
		
		//this.brn=brn;
		//this.uid=uid;
		
		///////// frame setting////////////////////////////////////////////////		
		 
		UserDAO udao = new UserDAO();
		uid=udao.getUserId(uname,pass);

		Vector packlst = udao.getBranch(uid);
		
		brn = (BranchDto)packlst.get(0); 
		this.uid=uid;
		this.brn=brn;
		
		//setTitle("Aristo Pharmaceuticals Pvt. Ltd.");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximize both the horizontal and vertical directions

                                		
//		setSize(1024, 768);
//		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		 arisleb.setVisible(false);

		
		pb = new JProgressBar();
//		pb.setBounds(370, 320, 310, 20);
		pb.setBounds((screenWidth/2)-110,(screenHeight/2)-113, 310, 20);
        pb.setMinimum(0);
        pb.setMaximum(MAX);
        pb.setStringPainted(true);
        pb.setVisible(false);
        getContentPane().add(pb);
        
		/////////////////////////////////////
		arisLogo = new ImageIcon(getClass().getResource("/images/aris_log.png"));
//		arisLogo1 = new ImageIcon(getClass().getResource("/images/Nakoda.png"));
		loadingIcon = new ImageIcon(getClass().getResource("/images/loader.gif"));
		
		lodingLabel = new JLabel(loadingIcon);
//		lodingLabel.setBounds(500, 377, 48, 48);
		lodingLabel.setBounds((screenWidth/2)+20,(screenHeight/2)-56, 48, 48);
		lodingLabel.setVisible(false);
		getContentPane().add(lodingLabel);
		
		lblPackageSelection = new JLabel("Branch Selection");
		lblPackageSelection.setForeground(Color.WHITE);
		lblPackageSelection.setFont(new Font("Verdana", Font.BOLD, 13));
//		lblPackageSelection.setBounds(341, 249, 180, 20);
		lblPackageSelection.setBounds((screenWidth/2)-139,(screenHeight/2)-178, 180, 20);
//		panel_3.setBounds(330, 244, 368, 37);

		getContentPane().add(lblPackageSelection);

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
		
		
		
		
		
		
		 
		
		///////////////////////////////////////////////////////////////////////////////////////////////

			pb.setVisible(true);
			lblPackageSelection.setText("Loading Please Wait.....");
			submitForm();
			setAlwaysOnTop(true);
		


	}
 
	
	 

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			dispose();
		}
		
	    if(e.getActionCommand().equalsIgnoreCase("Submit") )
	    {
	    	
	    	submitForm();

		}

		
	}
	
	public class NumberWorker extends SwingWorker<String, Object> 
	{
		protected String doInBackground() throws Exception 
		{
			try
			{
					// update progressbar
					for (int i = 0; i <= MAX; i++) {
						final int currentValue = i;
						try {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									pb.setValue(currentValue);

								}
							});
							java.lang.Thread.sleep(50);
						} catch (InterruptedException e) {
							JOptionPane.showMessageDialog(DivOpt.this, e.getMessage());
						}
					}
				 

//				int div=10;
				int depo=brn.getDepo_code();
				
				ldao = new LoginDAO();
				
				loginDt = ldao.getLoginInfo(uname,pass, div, depo,1,uid,0);
				loginDt.setDivnm(divnm);
				loginDt.setDiv_code(div);
				loginDt.setCmp_code(10);
				//loginDt.setBdto(br);
				
//				loginDt.setBrnnm(brn.getDepo_name()+","+brn.getCmp_city());
				loginDt.setBrnnm(brn.getDepo_name());
				loginDt.setState_code(brn.getState_Code());
				loginDt.setLogin_name(uname);
				loginDt.setLogin_pass(pass);
				loginDt.setPack_code(1);
				senderId=brn.getCmp_abvr();
				
				
					UIManager.setLookAndFeel(new SyntheticaBlueMoonLookAndFeel());

				new MenuFrame(brn,loginDt.getLogin_id()).setVisible(true);
				lodingLabel.setVisible(false);
				dispose();
			}
			catch(Exception ez)
			{
				ez.printStackTrace();
			}
			
			return null;
		}
 
		 
		protected void done() 
		{
			try 
			{
				worker = null;
				worker.cancel(true);
				lodingLabel.setVisible(false);
			} 
			catch (Exception ignore) 
			{
				
			}
		}
	}
	
	public void submitForm()
	{
		
		
		lodingLabel.setVisible(true);
		worker = new NumberWorker();
		worker.execute();
		
	}			
	
}