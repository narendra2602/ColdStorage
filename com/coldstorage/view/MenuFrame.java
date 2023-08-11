package com.coldstorage.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.coldstorage.dao.LoginDAO;
import com.coldstorage.dao.MenuDAO;
import com.coldstorage.dto.BranchDto;
import com.coldstorage.dto.ChildMenuDto;
import com.coldstorage.dto.MenuDto;
import com.coldstorage.dto.SubMenuDto;

public class MenuFrame extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar;
	private JToolBar toolbar;
	private ImageIcon exitIcon,switchIcon,favIcon;
	private JButton exitButton,switchButton,favButton;
	private JLabel lblAristoPharmaceuticalsPvt;
	Font font,fontHindi;
	private JPanel panel_1;
//	private JLabel lblAccountingYear;
	
	private JLabel clockLab; 
	private JLabel lblVersion;
	 
	private JButton newButton []=new JButton[10];
	private JPanel panel_2;
	private JPanel panel;
	
	private JLabel favHeaderLbl;
	int jindex=0;
	private boolean favpanel=false;
	private ActionListener listener;

	private Point currentLocation;
	private JLabel group_name;
	
	public MenuFrame(BranchDto brn,int uid)
	{
		
		 System.out.println("MenuFrame called");
		
		 
		
		///////// frame setting////////////////////////////////////////////////		


		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximize both the horizontal and vertical directions

		fontHindi = new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,18);
		
//		setSize(1024, 768);
		setResizable(false);
		//setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		 arisleb.setVisible(false);


		/////////////////////////////////////

			
			
		 
/*			panel = new JPanel();
			panel.setBorder(new LineBorder(new Color(2, 136, 209), 3));
			panel.setBounds(screenWidth-(screenWidth/6), (screenHeight/4)+30, 200, 350);
			getContentPane().add(panel);
*/
		 
	
		panel_2 = new JPanel();
		//panel_2.setBounds(screenWidth-(screenWidth/6), (screenHeight/4), 200, 31);
		panel_2.setBackground(SystemColor.textHighlight);
		panel_2.setPreferredSize(new Dimension(195, 30));
		getContentPane().add(panel_2);

		favHeaderLbl = new JLabel("Favorite");
		panel_2.add(favHeaderLbl);
		favHeaderLbl.setForeground(new Color(255, 255, 255));
		favHeaderLbl.setFont(new Font("Bodoni MT Black", Font.PLAIN, 15));


		

		listener = new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            if (e.getSource() instanceof JButton) {
		                String repName = ((JButton) e.getSource()).getText();
		                String text = ((JButton) e.getSource()).getActionCommand();
		                String c =text.split(" | ")[0];
		                String subc=text.split(" | ")[2];
	

//		                ((JButton) e.getSource()).setBackground(Color.BLUE);
//		                JOptionPane.showMessageDialog(null, repName+": "+text);
		                try
		                {
						JFrame invoker=(JFrame) map.get(repName);
						infoName=null;
						
						if(invoker==null)
						{
							Class<?> clazz = Class.forName(c);
							
								if(subc==null || subc.equals("null"))
								{
									invoker =(JFrame) clazz.newInstance();
									System.out.println("No Name invoker");
									map.put(repName, invoker);
									System.out.println("yaha aaya button favroite 142 line no");

								}
								else
								{
									Constructor<?> constructor = clazz.getConstructor(String.class,String.class);
									//create an instance
									
									invoker =(JFrame) constructor.newInstance(subc,repName);
									map.put(repName, invoker);
									System.out.println("yaha aaya button favoirint line ni 153 *****");
								}
						}
						
						invoker.repaint();
						invoker.setVisible(true);
						invoker.toFront();
						
		                }catch(Exception ee)
		                {
		                	ee.printStackTrace();
		                }

		                
		            }
		        }
		    };

		
		
		String msg = "";
		String footer =""; 
		String totalInvoice="";
		msg = loginDt.getMessage();
		footer =loginDt.getFooter();
		totalInvoice = loginDt.getTotinv();
		
		
		
		lblAristoPharmaceuticalsPvt = new JLabel(brn.getDepo_name());
		lblAristoPharmaceuticalsPvt.setForeground(Color.DARK_GRAY);
		lblAristoPharmaceuticalsPvt.setFont(new Font("Arial", Font.BOLD , 24));
		lblAristoPharmaceuticalsPvt.setBounds(5, 30, 650, 29);
		getContentPane().add(lblAristoPharmaceuticalsPvt);

		//lblAccountingYear = new JLabel("Accounting Year 2012-2013    Month: Apr-2012");
		
		lblAccountingYear = new JLabel(msg);
		lblAccountingYear.setForeground(Color.darkGray);
		lblAccountingYear.setFont(new Font("Arial", Font.BOLD, 16));
//		lblAccountingYear.setBounds(694, 34, 394, 20);
		lblAccountingYear.setBounds(screenWidth-(screenWidth/4), 34, 394, 20);
		getContentPane().add(lblAccountingYear);

		
		
		lblFinancialAccountingYear = new JLabel(footer);
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(314, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);
		lblFinancialAccountingYear.setFont(new Font("Tahoma", Font.BOLD, 13));

		
   		group_name = new JLabel(loginDt.getDivnm().toUpperCase());
  		group_name.setForeground(Color.RED);
   		group_name.setBounds(screenWidth-(screenWidth/2)-200, 34, 394, 20);
   		group_name.setFont(new Font("Arial", Font.BOLD, 22));
		getContentPane().add(group_name);	

		
   		stock_status = new JLabel("User : "+loginDt.getLogin_name().toUpperCase());
		stock_status.setForeground(Color.BLUE);
		stock_status.setBounds(screenWidth-(screenWidth/3), 34, 194, 20);
		stock_status.setFont(new Font("Arial", Font.BOLD, 22));
		getContentPane().add(stock_status);	
		
		//getContentPane().add(arilogo);

		///// add some glue so subsequent items are pushed to the right
		toolbar = new JToolBar();
		toolbar.setSize(1018, 65);
		toolbar.setLocation(0, 90);
		toolbar.setFloatable(false);


//		exitIcon = new ImageIcon(getClass().getResource("/images/exit.png"));
//		switchIcon = new ImageIcon(getClass().getResource("/images/switch.png"));
		favIcon = new ImageIcon(getClass().getResource("/images/fav.png"));
		exitIcon = new ImageIcon(getClass().getResource("/images/icons8-exit-40.png"));
		switchIcon = new ImageIcon(getClass().getResource("/images/icons8-change-user-40.png"));


		exitButton = new JButton(exitIcon);
//		exitButton.setBounds(962, 4, 40, 40);
		exitButton.setBounds(screenWidth-100, 4, 40, 40);
		exitButton.setBackground(null);
		getContentPane().add(exitButton);
		
		switchButton = new JButton(switchIcon);
		switchButton.setVisible(true);
//		switchButton.setBounds(906,4,40,40);
		switchButton.setBounds(screenWidth-150, 4, 40, 40);
		switchButton.setBackground(null);
		favButton = new JButton(favIcon);
		//////////////////////////////////////////////////////////////////
		toolbar.add(Box.createHorizontalGlue());   
		
		
		if(userID!=1) ///for admin no need to switch button.
		{
			//toolbar.add(favButton);
			//toolbar.add(switchButton);
			getContentPane().add(switchButton);
		}
		
		//toolbar.add(exitButton);
//		getContentPane().add(toolbar, null);


		lblFinancialAccountingYear.setVisible(false);




		///////////menubar/////////////////////////////
		menubar = new JMenuBar();
		//menubar.setBounds(0, 65, 1018, 30);
		menubar.setSize(screenWidth, 30);
		menubar.setLocation(0, 60);

		MenuDAO mdao = new MenuDAO();
		MenuDto mdto =null;
		ArrayList<?> list = mdao.getMainMenu(1,uid);

		int favsize = ((MenuDto) list.get(0)).getFavCount();
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(2, 136, 209), 3));
		panel.setBounds(screenWidth-(screenWidth/6), (screenHeight/4)+30, 200, favsize);
		getContentPane().add(panel);

		panel.add(panel_2);
		
		
		
		
		panel.addMouseMotionListener(new MouseAdapter() {
		    public void mouseDragged(MouseEvent e) {
		    	 e.translatePoint(e.getComponent().getLocation().x, e.getComponent().getLocation().y);
		    	    panel.setLocation(e.getX(), e.getY());
		    	    
		    	    repaint();
		    }
		});
		
		
		int ls=list.size();
		for(int i =0;i<ls;i++)
		{
			mdto = (MenuDto) list.get(i);
			JMenu myMenu = getFileMenu(mdto.getTab_name(),mdto.getMenu_name());
			
			menubar.add(myMenu);
			//setJMenuBar(menubar);
		}

		getContentPane().add(menubar);



		
		
		/////////// footer///////////////////////////////////////////////////////////
		promLogo1= new ImageIcon(getClass().getResource("/images/plogo.png"));
		JLabel promleb = new JLabel(promLogo1);
		promleb.setBounds(10, screenHeight-(screenHeight/8)+30, 35, 35);
		getContentPane().add(promleb);


		getContentPane().add(contact);

		getContentPane().add(lblPromptSoftwareConsultants);

		getContentPane().add(lblAddress);

		getContentPane().add(lblPhone);


		panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("TextPane.selectionBackground"));
		panel_1.setBounds(0, (screenHeight-(screenHeight/8)), screenWidth, 3);
		getContentPane().add(panel_1);

		exitButton.setActionCommand("Exit");
		switchButton.setActionCommand("Switch");
		favButton.setActionCommand("Favorite");
		
		
		clockLab = new JLabel();
		clockLab.setFont(new Font("Verdana", Font.BOLD, 14));
		clockLab.setBounds(screenWidth-(screenWidth/6), screenHeight-(screenHeight/8)+39, 217, 24);
		getContentPane().add(clockLab);
		// TODO VERSION CHANGE
		lblVersion = new JLabel("Version : 11.08.2023-V1");
		lblVersion.setForeground(new Color(0, 102, 0));
		lblVersion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVersion.setBounds(screenWidth-(screenWidth/6), screenHeight-(screenHeight/8)+10, 159, 14);
		getContentPane().add(lblVersion);
		
		

		
		
		exitButton.addActionListener(this);
		switchButton.addActionListener(this);
		favButton.addActionListener(this);
 	

		ActionListener updateClockAction = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Assumes clock is a custom component
				clockLab.setText(System.currentTimeMillis()+""); 
				// OR
				// Assumes clock is a JLabel
				Date dd = new Date();
				SimpleDateFormat sdff = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");
				clockLab.setText(sdff.format(dd)); 
			}
		};
		 
		Timer t = new Timer(1000, updateClockAction);
		t.start();

		panel.setVisible(favpanel);
		panel_2.setVisible(favpanel);
		
		
	}
	


	private JMenu getFileMenu(String tab,List<?> menuItem) 
	{
		int len=tab.length();
		len=(30-len)/2;
		StringBuilder tabspace1=new StringBuilder("");
		for(int i=0;i<len;i++)
			tabspace1.append(" ");
			
		JMenu myMenu = new JMenu();
		myMenu.setIcon((ImageIcon)iconMap.get(tab.toLowerCase()));
		myMenu.setHorizontalAlignment(SwingConstants.CENTER);
		myMenu.setHorizontalTextPosition(SwingConstants.RIGHT);
		myMenu.setText(tab+tabspace1.toString()+tabspace1.toString());

		
		
		SubMenuDto smdto=null;
		ChildMenuDto cmdto=null;
		int smenusize=0;
		
		myMenu.setMnemonic(tab.charAt(0));
	 
		if (tab.startsWith("Tools"))
			myMenu.setMnemonic(tab.charAt(1));

		
		int sz=menuItem.size();
		for(int j=0;j<sz;j++) 
		{
			smdto = (SubMenuDto) menuItem.get(j);
			smenusize=smdto.getSmenu_name().size();
			if (smenusize==0)
			{
				MyMenuItem myItem = new MyMenuItem(smdto.getMenu_name(),smdto.getClass_name(),smdto.getSubclass_name());
				if (smdto.getClass_name().startsWith("com"))
				{
				  myItem.setForeground(Color.black);
				  myMenu.add(myItem);
				  
				  if(smdto.getFav_type().equalsIgnoreCase("F") && jindex <10)
				  {
					newButton[jindex] = new JButton(smdto.getMenu_name());
					newButton[jindex].setActionCommand(smdto.getClass_name()+" | "+smdto.getSubclass_name());
					newButton[jindex].addActionListener(listener);
					newButton[jindex].setPreferredSize(new Dimension(180, 29));
					newButton[jindex].setFont(fontHindi);
					newButton[jindex].setBackground(Color.BLUE);
					panel.add(newButton[jindex]);
					favpanel=true;
					jindex++;
				  }


				  
					if(smdto.getMenu_name().equalsIgnoreCase("A/C Master"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Product Master"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
					else if(smdto.getClass_name().equalsIgnoreCase("com.coldstorage.view.InwardEntry"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Inward Updation"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,ActionEvent.CTRL_MASK));
					else if(smdto.getClass_name().equalsIgnoreCase("com.coldstorage.view.OutwardUpdation"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Gate Pass Entry"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Transfer Entry"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Inward Printing"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Outward Printing"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Gate Pass Printing"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Transfer Printing"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("WrittenOff Printing"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Rent Receipt Printing"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("WrittenOff Entry"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Rent Entry"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Inward Register"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.SHIFT_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Outward Register"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.SHIFT_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Gate Pass Register"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.SHIFT_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Transfer Register"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.SHIFT_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("WrittenOff Register"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.SHIFT_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Final Register"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.SHIFT_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Rent Register"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.SHIFT_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Discount Register"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.SHIFT_MASK));
					else if(smdto.getMenu_name().equalsIgnoreCase("Ledger"))
						myItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.SHIFT_MASK));

				  
				}
//				else
//				  myItem.setForeground(Color.GRAY);
					
			}
			else
			{
				MyMenu menu = new MyMenu(smdto.getMenu_name());;
				for(int k=0;k<smenusize;k++)
				{
					cmdto =(ChildMenuDto) smdto.getSmenu_name().get(k);
					MyMenuItem myItem = new MyMenuItem(cmdto.getSmenu_name(),cmdto.getClass_name(),cmdto.getSubclass_name());
					 
					if (cmdto.getClass_name().startsWith("com"))
					{
					    myItem.setForeground(Color.black);
						menu.add(myItem);
					}
//					else
//						myItem.setForeground(Color.RED);

				}
				myMenu.add(menu);

			}
		}
		return myMenu;
	}


	private class MyMenu extends JMenu implements ActionListener 
	{
		private static final long serialVersionUID = 1L;
		public MyMenu(String text) 
		{
			super(text);
			addActionListener(this);
		}
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println("Menu clicked: "+e.getActionCommand());
		}
	}

	private class MyMenuItem extends JMenuItem implements ActionListener 
	{
		private static final long serialVersionUID = 1L;
		private String nam;
		private String repName;
		public MyMenuItem(String text,String code,String subclass) 
		{
			super(text);
			setActionCommand(code);
			setFont(fontHindi);
			nam=subclass;
			repName=text;
			addActionListener(this);
		}
		public void actionPerformed(ActionEvent evt) 
		{
			System.out.println("Item clicked: "+evt.getActionCommand());
			String c=evt.getActionCommand();
			try 
			{
				//map.get(c);
				//String nam="com.aristo.print.MISRepo19";

				
//				panel.setVisible(true);
//				panel_2.setVisible(true);
				
				
				JFrame invoker=(JFrame) map.get(repName);
				infoName=null;
				
				if(invoker==null)
				{
					Class<?> clazz = Class.forName(c);
					
						if(nam==null )
						{
							invoker =(JFrame) clazz.newInstance();
							System.out.println("No Name invoker");
							map.put(repName, invoker);
							System.out.println("yaha aaya 3");

						}
						else
						{
							Constructor<?> constructor = clazz.getConstructor(String.class,String.class);
							//create an instance
							
							invoker =(JFrame) constructor.newInstance(nam,repName);
							map.put(repName, invoker);
							System.out.println("yaha aaya 4*****");
						}
				}
				
				//setting keyboard caps lock button 
				if(!Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
				{
					Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
				} 
				
				
				invoker.repaint();
				invoker.setVisible(true);
				invoker.toFront();
				
			} 
			catch(Exception e) 
			{
			    	e.printStackTrace();
			} 
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		
		
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			ipmac="";
			ldao=new LoginDAO();
			boolean res =ldao.authenticateUser(loginDt.getLogin_name(), loginDt.getLogin_pass());
			dispose();
			System.exit(0);
		}
		
		
		
		if(e.getActionCommand().equalsIgnoreCase("Switch"))
		{
			map=null;
			map = new HashMap();
			System.gc(); 
			java.awt.Window win[] = java.awt.Window.getWindows();
			System.out.println("no. of windows is "+win.length);
			int visibleWins=0;

			for(int i=0;i<win.length;i++)
			{ 
				if(win[i].isVisible())
				{
					visibleWins++;
				}
			}
			
			System.out.println("no. of windows Visible wins "+visibleWins);
			if(visibleWins>1)
			{
				int answer = JOptionPane.showOptionDialog(this, "Close all opened windows before switching \n or you might loose some data","Switching Yes/No",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,  new String[] {"Yes", "No"}, "No");

				if (answer == JOptionPane.YES_OPTION) 
				{
					for(int i=0;i<win.length;i++)
					{
						win[i].dispose();
						win[i]=null;
					}
					//DivOpt divOpt=null;
					new PackageOpt(loginDt.getLogin_name(), loginDt.getLogin_pass()).setVisible(true);
//					divOpt =new DivOpt(loginDt.getLogin_name(), loginDt.getLogin_pass(),loginDt.getPack_code(),loginDt.getLogin_id());
//					divOpt.setVisible(true);
					dispose();
				}
			}
			else
			{
				//DivOpt divOpt=null;
				new PackageOpt(loginDt.getLogin_name(), loginDt.getLogin_pass()).setVisible(true);
//				divOpt =new DivOpt(loginDt.getLogin_name(), loginDt.getLogin_pass(),loginDt.getPack_code(),loginDt.getLogin_id());
//				divOpt.setVisible(true);
				dispose();
			}
			
			
			
		}
		
	}
	
	
	public void dispose()
	{
		super.dispose();
		//setting keyboard caps lock button 
		Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);
	}
}