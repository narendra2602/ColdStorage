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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.coldstorage.dao.ConnectionFactory;
import com.coldstorage.dao.LoginDAO;
import com.coldstorage.util.SendMail;

import de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel;

public class LoginNew extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	 
	 
	//private JLabel lblPromptSoftwareConsultants;
	//private JLabel lblNewLabel;
	//private JLabel lblPhone;
	private JPanel panel_1;
	//	private JLabel lblAccountingYear;
	private Font font; 
	

	private JLabel userName ,password;
	private JTextField usernameF;
	private JPasswordField passwordF;
	private JButton loginButton,cancelButton;
	LoginDAO ldao;
	private JPanel panel_3;
	private Icon loadingIcon;
	private JLabel lodingLabel;
	  
	  
	public LoginNew()
	{
		font =new Font("Tahoma", Font.PLAIN, 11);
		///////// frame setting////////////////////////////////////////////////		
		//infoName="/helpimgs/p1.png";
		ldao = new LoginDAO();
		
		  formatter = new DecimalFormat("0.00");
		  
		  simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
//		  ipaddress =getIpAdress()[0];
//		  ipmac =getIpAdress()[1];
		 
		  font =new Font("Tahoma", Font.PLAIN, 11);
		  arisleb.setVisible(false);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		


	       //APPROACH - 2 : Using MAXIMIZED_BOTH
		setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximize both the horizontal and vertical directions

		
//		setSize(1024, 768);
		setResizable(false);
		getContentPane().setLayout(null);


		/////////////////////////////////////

		

		/////////// footer///////////////////////////////////////////////////////////
		
		promLogo1= new ImageIcon(getClass().getResource("/images/plogo.png"));
		JLabel promleb = new JLabel(promLogo1);
//		promleb.setBounds(12, 680, 35, 35);
		promleb.setBounds(10, screenHeight-(screenHeight/8)+30, 35, 35);
		getContentPane().add(promleb);

		
		contact = new JLabel("Contact: ");
		contact.setBounds(4, screenHeight-(screenHeight/8)+10, 236, 14);
		getContentPane().add(contact);
		

		lblPromptSoftwareConsultants = new JLabel("Prompt Software Consultants");
		lblPromptSoftwareConsultants.setBounds(70, screenHeight-(screenHeight/8)+10, 236, 14);
		getContentPane().add(lblPromptSoftwareConsultants);
		

		lblAddress = new JLabel("Aashish Vihar, 17/3 Old Palasia Indore 452001.");
		lblAddress.setFont(font);
		lblAddress.setBounds(70, screenHeight-(screenHeight/8)+36, 236, 14);
		getContentPane().add(lblAddress);
		

		lblPhone = new JLabel("Phone: 0731-2539888, 4069098   Email: promptindore@gmail.com ");
		lblPhone.setFont(font);
		lblPhone.setBounds(70, screenHeight-(screenHeight/8)+51, 347, 14);
		getContentPane().add(lblPhone);

		
		panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("TextPane.selectionBackground"));
		panel_1.setBounds(0, (screenHeight-(screenHeight/8)), screenWidth, 3);
		getContentPane().add(panel_1);



		////////////////////////////
		

		userName = new JLabel("Username: ");
		userName.setBounds((screenWidth/2)-80,(screenHeight/2)-110, 85, 20);
		getContentPane().add(userName);

		usernameF = new JTextField();
		usernameF.setBounds((screenWidth/2)+6,(screenHeight/2)-108, 192, 25);
		getContentPane().add(usernameF);

		password = new JLabel("Password: ");
		password.setBounds((screenWidth/2)-80,(screenHeight/2)-74, 85, 20);
		getContentPane().add(password);

		passwordF = new JPasswordField();
		passwordF.setBounds((screenWidth/2)+6,(screenHeight/2)-74, 192, 25);
		getContentPane().add(passwordF);

		
		/**
		 * setting default password for developers (prompt) 
		 */
		String uiNamePassword[] = ConnectionFactory.getUINamePassword();
		if(uiNamePassword[0]!=null)
		{
			usernameF.setText(uiNamePassword[0]);
			passwordF.setText(uiNamePassword[1]);
		}
		
		
		
		usernameF.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent evt)
			{
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					passwordF.requestFocus();
				}
				evt.consume();
			}
		});


		passwordF.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent evt)
			{
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					loginButton.requestFocus();

				}
				evt.consume();
			}
		});

  

		Icon icon = new ImageIcon(getClass().getResource("/images/key.png"));
		JLabel label = new JLabel(icon);
		label.setBounds((screenWidth/2)-141,(screenHeight/2)-113, 64, 64);
		getContentPane().add(label);

		loadingIcon = new ImageIcon(getClass().getResource("/images/loader.gif"));
		lodingLabel = new JLabel(loadingIcon);
//		lodingLabel.setBounds(474, 383, 48, 48);
		lodingLabel.setBounds((screenWidth/2)+20,(screenHeight/2)-56, 48, 48);
		lodingLabel.setVisible(false);
		getContentPane().add(lodingLabel);
		
		
		
		loginButton = new JButton(" Login ");
		loginButton.setBounds((screenWidth/2)-74,(screenHeight/2)-27, 106, 28);
		getContentPane().add(loginButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds((screenWidth/2)+70,(screenHeight/2)-27, 106, 28);
		getContentPane().add(cancelButton);

 
		JLabel label_2 = new JLabel("Cold Storage Goods Management System");
		label_2.setForeground(Color.DARK_GRAY);
		label_2.setFont(new Font("Arial", Font.BOLD, 24));
		label_2.setBounds(5, 28, 504, 31);
		getContentPane().add(label_2);

		JPanel panel = new JPanel(); // top panel
		panel.setBackground(new Color(0, 96, 192));
		panel.setBounds(0, 60, screenWidth, 3);
		getContentPane().add(panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBounds((screenWidth/2)-150,(screenHeight/2)-150, 368, 173);
		//panel_2.setBackground(new Color(23,55,83));
		getContentPane().add(panel_2);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBackground(SystemColor.text);
		lblLogin.setForeground(SystemColor.text);
		lblLogin.setFont(new Font("Arial", Font.BOLD, 14));
//		lblLogin.setBounds(341, 251, 72, 20);
		lblLogin.setBounds((screenWidth/2)-139,(screenHeight/2)-178, 180, 20);
		getContentPane().add(lblLogin);
		
		panel_3 = new JPanel();
		//panel_3.setBackground(SystemColor.activeCaption);
		panel_3.setBackground(new Color(3,98,193));
		
		panel_3.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBounds((screenWidth/2)-150,(screenHeight/2)-183, 368, 37);
		getContentPane().add(panel_3);
		


		loginButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				lodingLabel.setVisible(true);
				String uname = usernameF.getText().trim();
				String pass = passwordF.getText().trim();
				if(authentification(uname, pass))
				{
					if(uname.equalsIgnoreCase("admin"))
					{
						BaseClass.userID = 1;
						//DivOpt worker = new DivOpt(uname, pass,5,99);
						//NumberWorker w = worker.new NumberWorker();
						//w.execute();
						try 
						{
							UIManager.setLookAndFeel(new SyntheticaBlueMoonLookAndFeel());
							//new MenuFrame(5,99).setVisible(true);
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
					else
					{
						new PackageOpt(uname, pass).setVisible(true);
						//new DivOpt(uname, pass,null,0).setVisible(true);
					}
					
					//		new DivOpt(uname, pass).setVisible(true);
					//new MenuFrame().setVisible(true);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(LoginNew.this,"Incorrect Username/Password ","Login fail",JOptionPane.INFORMATION_MESSAGE);
					usernameF.requestFocus();
				}

			}
		});

		loginButton.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent evt)
			{
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String uname = usernameF.getText().trim();
					String pass = passwordF.getText().trim();
					if(authentification(uname, pass))
					{
						if(uname.equalsIgnoreCase("admin"))
						{
							BaseClass.userID = 1;
							//DivOpt worker = new DivOpt(uname, pass,5,99);
							//NumberWorker w = worker.new NumberWorker();
							//w.execute();
							try 
							{
								UIManager.setLookAndFeel(new SyntheticaBlueMoonLookAndFeel());
								new MenuFrame(null,99).setVisible(true);
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}
						}
						else
						{

							new PackageOpt(uname, pass).setVisible(true);
							//new DivOpt(uname, pass,null,2,"Gajar").setVisible(true);
						}
						
						//		new DivOpt(uname, pass).setVisible(true);
						//new MenuFrame().setVisible(true);
						dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(LoginNew.this,"Incorrect Username/Password ","Login fail",JOptionPane.INFORMATION_MESSAGE);
						usernameF.requestFocus();
					}

					evt.consume();
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
			}
		});


		new SendMail();
		

	}

	public boolean authentification(String userNm,String Pass) 
	{
		boolean res;
		res =ldao.authenticateUser(userNm, Pass);
		/*		if (res)
		{
			 BaseClass.loginDt = ldao.getLoginInfo(userNm, Pass, 1, 21);

		}*/
		return res;
	}


	public static void main(String ars[])
	{
		
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  try 
		  		{
		  			UIManager.setLookAndFeel(new SyntheticaBlueMoonLookAndFeel());
		  		} 
		  		catch (Exception e) 
		  		{
		  			e.printStackTrace();
		  		}		    	  
		    		 
		        new LoginNew().setVisible(true);
		      }
		    });
		
		/*try 
		{
			UIManager.setLookAndFeel(new SyntheticaBlueMoonLookAndFeel());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		UserLogin tc =new UserLogin();
		tc.setVisible(true);*/
	}




	
	public void actionPerformed(ActionEvent arg0) 
	{

	}
}