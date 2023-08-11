package com.coldstorage.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import com.coldstorage.dao.ConnectionFactory;
import com.coldstorage.dao.LoginDAO;
import com.coldstorage.dto.AreaDto;
import com.coldstorage.dto.BranchDto;
import com.coldstorage.dto.CategoryDto;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.HQDto;
import com.coldstorage.dto.LoginDto;
import com.coldstorage.dto.MonthDto;
import com.coldstorage.dto.ProductDto;
import com.coldstorage.dto.RegionDto;
import com.coldstorage.dto.StateDto;
import com.coldstorage.dto.TransportDto;

public class BaseClass extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static  LoginDto loginDt;
	public static JLabel lblAccountingYear;
	public static JLabel lblFinancialAccountingYear;
	public static Icon arisLogo;
	public static Icon arisLogo1;
	public static Icon promLogo1; 
	public static JLabel stock_status;
	protected NumberFormat formatter;
	protected double dbval=0.00;  
	protected static Map map=new HashMap();
	LoginDAO ldao;
	public static int userID = 0;
	/*GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice device = env.getScreenDevices()[0];
	DisplayMode oldMode = device.getDisplayMode();
	DisplayMode newMode = new DisplayMode(1024,768,oldMode.getBitDepth(),oldMode.getRefreshRate());
	 */

	public static String ipaddress=null;
	public static String ipmac=null;
	protected static String infoName=null;
	protected static HashMap helpImg=null;
	protected static boolean updateTable=true;	
	public static SimpleDateFormat simpleDateFormat;
	public static String commaSepratedInvoiceNo;
	public static String smsText;
	public static String senderId;
	public static JLabel branch;
	public static JLabel arisleb;
	public static JLabel lblPromptSoftwareConsultants,lblAddress,lblPhone,contact;
	public static Font font;
	public FocusListener myFocusListener;
	private Color bcolor;
	protected  Color buttoncolor,panelColor,disableColour;
	public static Icon viewIcon,printIcon,excelIcon,exitIcon,saveIcon,addIcon,moreIcon,deleteIcon,mprintIcon,missingIcon,cancelIcon,submitIcon;
	public static Icon masterIcon,transactionIcon,accountsIcon,reportsIcon,GSTRIcon,emailIcon,toolsIcon; 
	public HashMap iconMap;
	protected DefaultListModel defaultListModel;
	public JLabel backLabel;
	public static int screenHeight; 
	public static int screenWidth; 
	private SimpleDateFormat sdf;

	public BaseClass() 
	{
		promLogo1 = new ImageIcon(getClass().getResource("/images/plogo.png"));
		viewIcon = new ImageIcon(getClass().getResource("/images/icons8-pdf-16.png"));
		printIcon = new ImageIcon(getClass().getResource("/images/icons8-print-16.png"));
		excelIcon = new ImageIcon(getClass().getResource("/images/icons8-excel-16.png"));
		exitIcon = new ImageIcon(getClass().getResource("/images/icons8-exit-16.png"));
		saveIcon = new ImageIcon(getClass().getResource("/images/icons8-save-16.png"));
		addIcon = new ImageIcon(getClass().getResource("/images/icons8-edit-16.png"));
		deleteIcon = new ImageIcon(getClass().getResource("/images/icons8-delete-16.png"));
		moreIcon = new ImageIcon(getClass().getResource("/images/icons8-plus-16.png"));
		mprintIcon = new ImageIcon(getClass().getResource("/images/icons8-mprint-16.png"));
		missingIcon = new ImageIcon(getClass().getResource("/images/icons8-missing-16.png"));
		cancelIcon = new ImageIcon(getClass().getResource("/images/icons8-cancel-16.png"));
		submitIcon = new ImageIcon(getClass().getResource("/images/icons8-login-16.png"));

		masterIcon = new ImageIcon(getClass().getResource("/images/master_icon.png"));
		transactionIcon = new ImageIcon(getClass().getResource("/images/transaction_icon.png"));
		accountsIcon = new ImageIcon(getClass().getResource("/images/accounts_icon.png"));
		reportsIcon = new ImageIcon(getClass().getResource("/images/reports_icon.png"));
		GSTRIcon = new ImageIcon(getClass().getResource("/images/gstr_icon.png"));
		emailIcon = new ImageIcon(getClass().getResource("/images/email_icon.png"));
		toolsIcon = new ImageIcon(getClass().getResource("/images/tools_icon.png"));

		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
		
		iconMap=new HashMap();
		iconMap.put("masters",masterIcon);
		iconMap.put("transaction",transactionIcon);
		iconMap.put("accounts",accountsIcon);
		iconMap.put("reports",reportsIcon);
		iconMap.put("gstr reports",GSTRIcon);
		iconMap.put("email",emailIcon);
		iconMap.put("tools", toolsIcon);

		backLabel=new JLabel(new ImageIcon(getClass().getResource("/images/background_test1.jpg")));

		setContentPane(backLabel); 


		
	       myFocusListener = new FocusListener() {
	           public void focusGained(FocusEvent focusEvent) {
	               try {
	                   JTextField src = (JTextField)focusEvent.getSource();
	                   src.setBackground(new Color(170, 181, 157));
	                   src.selectAll();
	               } catch (ClassCastException ignored) {
	                   /* I only listen to JTextFields */
	                   try {
	    					JComboBox src = (JComboBox)focusEvent.getSource();
	    					src.setBackground(new Color(139, 153, 122));
	    				} catch (ClassCastException ignored1) {
	    					// TODO Auto-generated catch block
	    					
	    				}
	               }
	           }

	           public void focusLost(FocusEvent focusEvent) {
	               try {
	                   JTextField src = (JTextField)focusEvent.getSource();
	                   src.setBackground(null);
	               } catch (ClassCastException ignored) {
	                   /* I only listen to JTextFields */
	                   try {
	    					JComboBox src = (JComboBox)focusEvent.getSource();
	    					src.setBackground(null);
	    				} catch (ClassCastException ignored1) {
	    					// TODO Auto-generated catch block
	    					
	    				}
	               }
	           }
	       };

		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight = screenSize.height;
		screenWidth = screenSize.width;

		System.out.println("screen height "+screenHeight);
		System.out.println("screen width "+screenWidth);
		
		branch = new JLabel();
		branch.setForeground(Color.DARK_GRAY);
		branch.setFont(new Font("Arial", Font.BOLD , 22));

		//		bcolor=new Color(255,255,204); // yello
		bcolor=new Color(207,227,252); // LIGHT BLUE
		disableColour= new Color(220,220,220); // Light Gray 
		//		buttoncolor=new Color(145,47,64);
		buttoncolor=new Color(145,47,64);
		panelColor=new Color(235,242,250);


		arisleb = new JLabel(promLogo1);
		arisleb.setBounds(14, 22, 35, 37);
		getContentPane().add(arisleb);

		formatter = new DecimalFormat("0.00");


		
		myFocusListener = new FocusListener() {
			public void focusGained(FocusEvent focusEvent) {
				try {
					JTextField src = (JTextField)focusEvent.getSource();
					src.setBackground(bcolor);
				} catch (ClassCastException ignored) {
					/* I only listen to JTextFields */
					try {
						JComboBox src = (JComboBox)focusEvent.getSource();
						src.setBackground(bcolor);
					} catch (ClassCastException ignored1) {
						// TODO Auto-generated catch block
						try {
							JButton src = (JButton)focusEvent.getSource();
							src.setBackground(Color.BLUE);

						} catch (ClassCastException ignored2) {
							// TODO Auto-generated catch block

						}

					}
				}
			}

			public void focusLost(FocusEvent focusEvent) {
				try {
					JTextField src = (JTextField)focusEvent.getSource();
					src.setBackground(Color.WHITE);
					//src.setBackground(null);
				} catch (ClassCastException ignored) {
					/* I only listen to JTextFields */
					try {
						JComboBox src = (JComboBox)focusEvent.getSource();
						src.setBackground(Color.WHITE);
						//src.setBackground(null);
					} catch (ClassCastException ignored1) {
						// TODO Auto-generated catch block
						try {
							JButton src = (JButton)focusEvent.getSource();
							src.setBackground(null);

						} catch (ClassCastException ignored2) {
							// TODO Auto-generated catch block

						}

					}
				}
			}
		};



	}



	public int setIntNumber(String s){   
		int i = 0;
		try{
			i= Integer.parseInt(s);

		}
		catch(Exception e){
			i=0;
		}

		return i;
	}


	public double setDoubleNumber(String s)
	{   
		double d = 0.00;
		try
		{
			d= Double.parseDouble(s);

		}
		catch(Exception e)
		{
			d=0.00;
		}

		return d;
	}

	public long setLongNumber(String s){   


		long i = 0;
		try{
			i= Long.parseLong(s.trim());

		}
		catch(Exception e){
			i=0;
		}

		return i;
	}

	public Date formatDate(String responseDate)
	{
		Date returnDate=null;

		try
		{
			returnDate = simpleDateFormat.parse(responseDate);
		}
		catch(Exception e)
		{
			returnDate =null;
		}

		return returnDate;
	}

	public String formatDate(Date responseDate)
	{
		String returnDate=null;

		try
		{
			returnDate = simpleDateFormat.format(responseDate);
		}
		catch(Exception e)
		{
			returnDate ="__/__/____";
		}

		return returnDate;
	}
	public String formatDateScreen(Date responseDate,JFormattedTextField txtField)
	{
		String returnDate=null;
		simpleDateFormat.setLenient(true);

		try
		{
			returnDate = simpleDateFormat.format(responseDate);
		}
		catch(Exception e)
		{
			returnDate ="__/__/____";
		}

		checkDate(txtField);


		return returnDate;
	}





	public String setString(String s){   
		try{
			if(s==null)
				s="";

		}
		catch(Exception e){
			s="";
		}

		return s;
	}

	public void checkDate(JFormattedTextField tf)
	{
		try 
		{
			MaskFormatter dateMask = new MaskFormatter("##/##/####");
			dateMask.setPlaceholderCharacter('_');
			DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(dateMask);
			tf.setFormatterFactory(formatterFactory);
			dateMask.install(tf);
		} 
		catch (ParseException ex) 
		{
			ex.printStackTrace();
		}
	}


	public void checkNumber(JFormattedTextField tf)
	{
		try 
		{
			MaskFormatter numMask = new MaskFormatter("#########0.00");
			DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(numMask);
			tf.setFormatterFactory(formatterFactory);
			numMask.install(tf);
		} 
		catch (ParseException ex) 
		{
			Logger.getLogger(BaseClass.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void checkInteger(JFormattedTextField tf)
	{
		try 
		{
			MaskFormatter numMask = new MaskFormatter("##########");
			DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(numMask);
			tf.setFormatterFactory(formatterFactory);
			numMask.install(tf);
		} 
		catch (ParseException ex) 
		{
			Logger.getLogger(BaseClass.class.getName()).log(Level.SEVERE, null, ex);
		}
	}



	public void checkIntegerWithLength(JFormattedTextField tf,int len)
	{
		try 
		{
			StringBuffer sbf = new StringBuffer();
			for(int i=0;i<len;i++)
			{
				sbf.append("#");
			}

			MaskFormatter numMask = new MaskFormatter(sbf.toString());
			DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(numMask);
			tf.setFormatterFactory(formatterFactory);
			numMask.install(tf);
		} 
		catch (ParseException ex) 
		{
			Logger.getLogger(BaseClass.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * @param tf 
	 * @param len is not using
	 * @param characters 
	 */
	public void checkYN(JFormattedTextField tf,int len,String characters)
	{
		try 
		{
			//StringBuffer sbf = new StringBuffer();
			MaskFormatter numMask = new MaskFormatter("A");
			numMask.setValidCharacters(characters);
			DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(numMask);
			tf.setFormatterFactory(formatterFactory);
			numMask.install(tf);
		} 
		catch (ParseException ex) 
		{
			Logger.getLogger(BaseClass.class.getName()).log(Level.SEVERE, null, ex);
		}
	}



	public boolean isValidDate(String inDate) 
	{

		if (inDate == null)
			return false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);

		try {
			dateFormat.parse(inDate.trim());
		}
		catch (ParseException pe) {
			return false;
		}
		return true;
	} 

	public boolean isValidBlankDate(String inDate) 
	{

		if (inDate.equals("__/__/____"))
			return true;

		if (inDate == null)
			return false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);

		try {
			dateFormat.parse(inDate.trim());
		}
		catch (ParseException pe) {
			return false;
		}
		return true;
	} 


	public  boolean isValidRange(String dt,Date sdate,Date edate)
	{

		//	    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// declare and initialize testDate variable, this is what will hold
		// our converted string

		Date inDate1 = null;
		Date Date1 = null;
		Date Date2 = null;

		// we will now try to parse the string into date form
		try
		{
			inDate1 = sdf.parse(dt);
			Date1 = sdf.parse(sdf.format(sdate));
			Date2 = sdf.parse(sdf.format(edate));

		}

		// if the format of the string provided doesn't match the format we 
		// declared in SimpleDateFormat() we will get an exception

		catch (ParseException e)
		{
			System.out.println("the date you provided is in an invalid date format.");
			return false;
		}



		if (inDate1.before(Date1)) 
		{
			System.out.println("The enter date is less then Starting date.");
			return false;
		}

		if (inDate1.after(Date2)) 
		{
			System.out.println("The enter date is greater then ending date.");
			return false;
		}


		// if we make it to here without getting an error it is assumed that
		// the date was a valid one and that it's in the proper format

		return true;

	} // end isValidRange



	public double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}	

	public boolean checkingMissingDate(Date gtDate,Date maxDate,Date minDate)
	{

		if(gtDate.after(maxDate) && gtDate.before(minDate))
		{
			return true;
		}
		else if(gtDate.compareTo(maxDate)==0)
		{
			return true;
		}
		else if(gtDate.compareTo(minDate)==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	public String checkNull(String value)
	{
		System.out.println(value);
		try
		{
			if(!value.equals(null))
			{
				return value;
			}
			else
			{
				return value="";
			}
		}
		catch(Exception e)
		{
			return value="";
		}

	}

	public int getSerialNo(int div,int depo,int year,int invno,int doctp)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		int sno=0;
		try {
			con=ConnectionFactory.getConnection();
			String query1="select max(serialno)  from invsnd where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? and sinv_no=?  " ;
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setInt(4, doctp);
			ps1.setInt(5, invno);
			rst =ps1.executeQuery();

			if(rst.next()){
				sno=rst.getInt(1);
			}

			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in PurchaseDAO - GetSerial no   " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}

				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPurchaseDAO.Connection.close "+e);
			}
		}

		return sno;
	}	 	



	public int confirmationDialongHO()
	{
		Object[] options = {"Single", "Multiple"};
		return JOptionPane.showOptionDialog(this, "Are you sure?","Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null, options, options[0]);		
	}


	public int confirmationDialong()
	{
		Object[] options = {"No", "Yes"};
		return JOptionPane.showOptionDialog(this, "Are you sure?","Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null, options, options[0]);		
	}

	public int confirmationDialongYes()
	{
		Object[] options = {"Yes", "No"};
		return JOptionPane.showOptionDialog(this, "Are you sure?","Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null, options, options[0]);		
	}


	public void alertMessage(Component classObject,String messageBody)
	{
		JOptionPane.showMessageDialog(classObject, messageBody);
	}

	public void alertMessage(Component classObject,String messageBody,String header,int itype)
	{
		if(itype==1)
			JOptionPane.showMessageDialog(classObject,messageBody, header,JOptionPane.INFORMATION_MESSAGE);
		else if(itype==2)
			JOptionPane.showMessageDialog(classObject,messageBody, header,JOptionPane.ERROR_MESSAGE);
		else if(itype==3)
			JOptionPane.showMessageDialog(classObject,messageBody, header,JOptionPane.WARNING_MESSAGE);
	}


	public static boolean checkInternetConnectivity(String site) {
		Socket sock = new Socket();
		InetSocketAddress addr = new InetSocketAddress(site,80);
		try {
			sock.connect(addr,12000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {sock.close();}
			catch (IOException e) {}
		}
	}

	/**
	 * 
	 * @param container : reference of classed the method called from
	 * @param buttonIndex : index of button where to set requestFocus
	 */
	public void setButtonBackgroundColor(JFrame container,int buttonIndex,KeyEvent keyEvent)
	{
		Component component[] = container.getContentPane().getComponents();
		List<JButton> buttonList=new ArrayList<JButton>();
		int buttonListSize=0;
		int currentButtonIndex=buttonIndex;
		for(int i=0; i<component.length; i++)
		{
			if (component[i] instanceof JButton)
			{
				JButton button = (JButton)component[i];
				buttonList.add(button);
			}
		}

		buttonListSize=buttonList.size();

		if(keyEvent.getKeyCode()==KeyEvent.VK_LEFT){
			buttonIndex= buttonIndex==1 ? buttonIndex : --buttonIndex;
		}
		if(keyEvent.getKeyCode()==KeyEvent.VK_RIGHT){

			buttonIndex= buttonListSize==buttonIndex ? buttonIndex : ++buttonIndex;
		}



		for(int i=0;i<buttonListSize;i++)
		{
			JButton button = buttonList.get(i);

			if(currentButtonIndex==i+1)
			{
				button.setBackground(null);
			}

			if(keyEvent.getKeyCode()==KeyEvent.VK_LEFT)
			{
				if(buttonIndex==i+1)
				{
					button.requestFocus();
					button.setBackground(Color.blue);
				}
			}

			if(keyEvent.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				if(buttonIndex==i+1)
				{
					button.requestFocus();
					button.setBackground(Color.blue);
				}
			}

		}



	}


	public static String getHqName(int ter_code)
	{
		Vector hqVector=(Vector) loginDt.getTerList();
		int size=hqVector.size();
		String terName=null;
		HQDto hq=null;
		for(int i=0;i<size;i++)
		{
			hq= (HQDto) hqVector.get(i);
			if(hq.getTer_code()==ter_code)
			{
				terName=hq.getTer_name();
				break;
			}

		}

		return terName;
	}



	public static String getRegionName(int reg_code)
	{
		Vector regVector=(Vector) loginDt.getRegList();
		int size=regVector.size();
		String regName=null;
		RegionDto reg=null;
		for(int i=0;i<size;i++)
		{
			reg= (RegionDto) regVector.get(i);
			if(reg.getReg_code()==reg_code)
			{
				regName=reg.getReg_name();
				break;
			}

		}

		return regName;
	}

	public static String getAreaName(int area_code)
	{
		Vector areaVector=(Vector) loginDt.getAreaList();
		int size=areaVector.size();
		String areaName=null;
		AreaDto reg=null;
		for(int i=0;i<size;i++)
		{
			reg= (AreaDto) areaVector.get(i);
			if(reg.getArea_cd()==area_code)
			{
				areaName=reg.getArea_name();
				break;
			}

		}

		return areaName;
	}

	public static String getBranchName(int depo_code)
	{
		Vector brVector=(Vector) loginDt.getBranchList();
		int size=brVector.size();
		String brName=null;
		BranchDto brn=null;
		for(int i=0;i<size;i++)
		{
			brn= (BranchDto) brVector.get(i);
			if(brn.getDepo_code()==depo_code)
			{
				brName=brn.getDepo_name();
				break;
			}

		}

		return brName;
	}

	public static String getCompanyName(int cmp_code,Vector brVector)
	{
		//		Vector brVector=(Vector) loginDt.getCmpproductList();
		int size=brVector.size();
		String brName=null;
		GroupDto grp=null;
		for(int i=0;i<size;i++)
		{
			grp= (GroupDto) brVector.get(i);
			if(grp.getGp_code()==cmp_code)
			{
				brName=grp.getGp_name();
				break;
			}

		}

		return brName;
	}



	public String getMonthName(int monno)
	{
		Vector monthlist=loginDt.getFmonth();
		int s=monthlist.size();
		String monthname="";
		MonthDto mdt=null;

		for (int i=0;i<s;i++)
		{
			mdt = (MonthDto) monthlist.get(i);
			if (mdt.getMnthno()==monno)
			{
				monthname=mdt.getMnthabbr()+mdt.getMnthno();
				break;
			}

		}
		return monthname;
	}


	public int getMonthCode(String inDate) {
		int mcode = 0;
		if (inDate != null && inDate.length() > 9)
			mcode = setIntNumber(inDate.substring(6) + inDate.substring(3, 5));

		System.out.println("mcode " + mcode);

		return mcode;
	}




	public String[] getIpAdress()
	{

		InetAddress ip;
		String ipadd="";
		String [] ipmac=new String[2];
		try {

			ip = InetAddress.getLocalHost();
			ipadd=ip.getHostAddress();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}
			System.out.println(sb.toString());
			ipmac[0]=ipadd;
			ipmac[1]=sb.toString();

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e){

			e.printStackTrace();

		}

		return ipmac;
	}


	/**
	 * 
	 * @param container : reference of classed the method called from
	 * @param buttonIndex : index of button where to set requestFocus
	 */
	public void setCaretPositionTextField(JFrame container)
	{
		Component component[] = container.getContentPane().getComponents();

		for(int i=0; i<component.length; i++)
		{
			if (component[i] instanceof JTextField)
			{
				JTextField textfield = (JTextField)component[i];
				textfield.setCaretPosition(0);
			}
		}

	}	    	


	public int getIndex(int value, JComboBox combo, int type) {
		int size = combo.getItemCount();
		int retval = 0;
		MonthDto mon = null;
		StateDto state = null;
		for (int i = 0; i < size; i++) {
			if (type == 5) {
				mon = (MonthDto) combo.getItemAt(i);

				if (mon.getMnthcode() == value) {
					retval = i;
					break;
				}

			}

			if (type == 6) {
				state = (StateDto) combo.getItemAt(i);

				if (state.getState_code() == value) {
					retval = i;
					break;
				}

			}

		}

		return retval;
	}


	public void setFocusListener(JFrame container)
	{

		Component component[] = container.getContentPane().getComponents();
		for(int i=0; i<component.length; i++)
		{
			if (component[i] instanceof JTextField)
			{
				JTextField textfield = (JTextField)component[i];
				textfield.addFocusListener(myFocusListener);
			}
			else  if (component[i] instanceof JComboBox)
			{
				JComboBox combox = (JComboBox)component[i];
				combox.addFocusListener(myFocusListener);
			}
			else  if (component[i] instanceof JTextArea)
			{
				JTextArea textarea = (JTextArea) component[i];
				textarea.addFocusListener(myFocusListener);

			}
			else  if (component[i] instanceof JButton)
			{
				JButton button = (JButton) component[i];
				button.addFocusListener(myFocusListener);
			}
			else  if (component[i] instanceof JScrollPane)
			{
				JScrollPane pane = (JScrollPane) component[i];
				pane.addFocusListener(myFocusListener);
				System.out.println("PANEADD "+i);
			}

		}

	}

	public void addKeyListener(JFrame container,KeyListener keylistener)
	{

		Component component[] = container.getContentPane().getComponents();
		for(int i=0; i<component.length; i++)
		{
			if (component[i] instanceof JTextField)
			{
				JTextField textfield = (JTextField)component[i];
				textfield.addKeyListener(keylistener);
			}
			else  if (component[i] instanceof JComboBox)
			{
				JComboBox combox = (JComboBox)component[i];
				combox.addKeyListener(keylistener);
			}
			else  if (component[i] instanceof JTextArea)
			{
				JTextArea textarea = (JTextArea) component[i];
				textarea.addKeyListener(keylistener);

			}

		}

	}


	protected void bindData(JList bindList,Vector list)
	{
		defaultListModel=new DefaultListModel();
		Vector party=list;
		int size=party.size();
		for (int i=0;i<size;i++)
		{
			defaultListModel.addElement(party.get(i));
		}

		bindList.setModel(defaultListModel);
		bindList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	}

	protected void searchFilter(String searchname,JList bindList,Vector list)
	{

		DefaultListModel filteredItems=new DefaultListModel();
		//PartyDto p=null;
		TransportDto p=null;
		ProductDto item=null;
		Vector party=list;
		Vector filterList=new Vector();
		int type=2;
		try {
			if((TransportDto) list.get(0) instanceof TransportDto)
			{
				type=1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("productdto is coming ");
			type=2;
		}

		int size=party.size();

		String[] str=searchname.toLowerCase().split(" ");


		for (int i=0;i<size;i++)
		{
			if(type==1)
			{
				p=(TransportDto) party.get(i);
				if(p.getTran_name().toLowerCase().contains(str[0]))
					filterList.addElement(party.get(i));
			}
			else
			{
				item=(ProductDto) party.get(i);




				if(item.getPname().toLowerCase().contains(str[0]))					
					filterList.addElement(party.get(i));


			}
		}
		for(int j=1;j<str.length;j++){
			int length=filterList.size();
			for(int k=0;k<length;k++){
				if(type==1){
					p=(TransportDto) filterList.get(k);					
					if(!p.getTran_name().toLowerCase().contains(str[j])){					
						filterList.remove(k);
						length--;
						k--;
					}
				}
				else{
					item=(ProductDto) filterList.get(k);					
					if(!item.getPname().toLowerCase().contains(str[j])){					
						filterList.remove(k);
						length--;
						k--;
					}	
				}

			}


		}
		for(int j=0;j<filterList.size();j++)
			filteredItems.addElement(filterList.get(j));
		defaultListModel=filteredItems;
		bindList.setModel(defaultListModel);


	}


	public void setEditable(JFrame container,boolean edit)
	{

		Component component[] = container.getContentPane().getComponents();
		for(int i=0; i<component.length; i++)
		{
			if (component[i] instanceof JTextField)
			{
				JTextField textfield = (JTextField)component[i];
				   textfield.setEditable(edit);
				   textfield.setBackground(edit?Color.WHITE:null);				   
			}
			else  if (component[i] instanceof JComboBox)
			{
				JComboBox combox = (JComboBox)component[i];
				combox.setEnabled(edit);
				combox.setBackground(edit?Color.WHITE:null);
			}
			else  if (component[i] instanceof JTextArea)
			{
				JTextArea textarea = (JTextArea) component[i];
				textarea.setEditable(edit);
				textarea.setBackground(edit?Color.WHITE:null);
				
				System.out.println("area "+i);
			}  
		}

	}


	
	protected void searchFilterCat(String searchname,JList bindList,Vector list)
	{

		DefaultListModel filteredItems=new DefaultListModel();
		//PartyDto p=null;
		CategoryDto p=null;
		GroupDto item=null;
		Vector party=list;
		Vector filterList=new Vector();
		int type=2;
		try {
			if((CategoryDto) list.get(0) instanceof CategoryDto)
			{
				type=1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("groupdto is coming ");
			type=2;
		}

		int size=party.size();

		String[] str=searchname.toLowerCase().split(" ");


		for (int i=0;i<size;i++)
		{
			if(type==1)
			{
				p=(CategoryDto) party.get(i);
				if(p.getCategory_name().toLowerCase().contains(str[0]))
					filterList.addElement(party.get(i));
			}
			else
			{
				item=(GroupDto) party.get(i);




				if(item.getGp_name().toLowerCase().contains(str[0]))					
					filterList.addElement(party.get(i));


			}
		}
		for(int j=1;j<str.length;j++){
			int length=filterList.size();
			for(int k=0;k<length;k++){
				if(type==1){
					p=(CategoryDto) filterList.get(k);					
					if(!p.getCategory_name().toLowerCase().contains(str[j])){					
						filterList.remove(k);
						length--;
						k--;
					}
				}
				else{
					item=(GroupDto) filterList.get(k);					
					if(!item.getGp_name().toLowerCase().contains(str[j])){					
						filterList.remove(k);
						length--;
						k--;
					}	
				}

			}


		}
		for(int j=0;j<filterList.size();j++)
			filteredItems.addElement(filterList.get(j));
		defaultListModel=filteredItems;
		bindList.setModel(defaultListModel);


	}

	

	
	
	public static boolean isValidEmailAddress(String email) {
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	
	
	public void checkDateValidity(String date, JFormattedTextField field, Component nextField,Component classObject)
	{
		if(isValidDate(date))
		{
			// Check the Date Range for the Financial year
			if (isValidRange(date,loginDt.getFr_date(),loginDt.getTo_date()))
			{

				if (nextField instanceof JTable)
				{
				  JTable table = (JTable) nextField;
				  table.requestFocus();
				  if( classObject instanceof WrittenOffEntry )
				  {
					  table.changeSelection(0, 6, false, false);
					  table.editCellAt(0, 6);
					  
				  }
				  else
				  {
					  table.changeSelection(0, 9, false, false);
					  table.editCellAt(0, 9);
				  }

				}
				else
				{
					nextField.requestFocus();
				}
					
			}
			else
			{
				
				alertMessage(classObject,"Date range should be in between: "
				+ simpleDateFormat.format(loginDt.getFr_date())+ " to " + simpleDateFormat.format(loginDt.getTo_date()));

				field.setValue(sdf.format(new Date()));
				checkDate(field);
				field.requestFocus();

			}


		}
		else
		{
			/*lblinvalid.setText("invalid date");
			lblinvalid.setForeground(Color.red);
			lblinvalid.setVisible(true);*/
			field.setValue(null);
			checkDate(field);
			field.requestFocus();
		}

	}

	
}
