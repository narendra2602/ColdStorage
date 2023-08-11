package com.coldstorage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.coldstorage.dao.AccountDAO;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.util.NewSms;
import com.coldstorage.util.TableDataSorter;


public class SMSlOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName ;
	private JButton sendButton,exitButton;
	private KeyListener keyListener;
	String ClassNm;
	private SimpleDateFormat sdf;
	private SimpleDateFormat sd;
	YearDto yd;
//	private String stdt,eddt;
	private Date stdt,eddt;
	private JTable itemTable;
	private DefaultTableModel itemTableModel;
	private JScrollPane itemTablePane;
	private JRadioButton selective,rdbtnDaysWise,rdbtnAcBalance,rdbtnBankDetail;
	private JRadioButton all;

	private JTextField search;
	private JLabel lblSearch;
	private int optn;
	private int year,code;
	String mnthname="";
	private SwingWorker<?,?> worker;
	private ImageIcon loadingIcon;
	private JLabel lodingLabel;
	private Date sdate,edate;
	private AccountDAO accdao;
	private ArrayList prtList;
	private HashMap smsMap;
	
	public  SMSlOpt(Date sdate,Date edate,int code,String party_name,int year,int opt)
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sd = new SimpleDateFormat("yyyy-MM-dd");
		
		 
		this.year=year;
		this.sdate=sdate;
		this.edate=edate;
		this.code=code;
		this.optn=opt;
		
		//setUndecorated(true);
		setResizable(false);
		setSize(586, 475);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		accdao = new AccountDAO();
		
		reportName = new JLabel("SMS");
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(148, 21, 303, 20);
		getContentPane().add(reportName);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		keyListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					JTextField textField = (JTextField) keyEvent.getSource();
					int id = Integer.parseInt(textField.getName());
					switch (id) 
					{
					case 1: 
						  
						break;
					case 2: 
						break;
						
					}
				}
				if (key == KeyEvent.VK_ESCAPE) 
				{
					dispose();
				}
 				//keyEvent.consume();
			}
		};
		
		
		sendButton = new JButton("Send");
		sendButton.setBounds(407, 388, 70, 30);
		getContentPane().add(sendButton);
		sendButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("Excel");
					dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					exitButton.setBackground(null);
					sendButton.setBackground(null);
					evt.consume();
				}
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					sendButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			}
		});
		
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(487, 388, 70, 30);
		getContentPane().add(exitButton);
		exitButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					resetAll();
					dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					exitButton.setBackground(null);
					sendButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		
		selective = new JRadioButton("Selective ");
		selective.setBounds(42, 69, 152, 23);
		getContentPane().add(selective);
		
		all = new JRadioButton("All  ");
		all.setBounds(212, 69, 112, 23);
		getContentPane().add(all);
		
		ButtonGroup item = new ButtonGroup();
		item.add(selective);
		item.add(all);

		
		loadingIcon = new ImageIcon(getClass().getResource("/images/loader.gif"));

		lodingLabel = new JLabel(loadingIcon);
		lodingLabel.setBounds(346, 378, 48, 48);
		lodingLabel.setVisible(false);
		getContentPane().add(lodingLabel);


		
		
		//////////////invoce no table model/////////////////////
		String [] invColName=	{"","Party Name","City","Mobile",""};
		String datax[][]={{}};
		itemTableModel=  new DefaultTableModel(datax,invColName)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				if(column==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}

			public Class getColumnClass(int column) 
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

		itemTable = new JTable(itemTableModel);

		itemTable.setColumnSelectionAllowed(false);
		itemTable.setCellSelectionEnabled(false);
		itemTable.getTableHeader().setReorderingAllowed(false);
		itemTable.getTableHeader().setResizingAllowed(false);
		itemTable.setRowHeight(20);
		itemTable.getTableHeader().setPreferredSize(new Dimension(25,25));
		///////////////////////////////////////////////////////////////////////////
		itemTable.getColumnModel().getColumn(0).setPreferredWidth(30);   //contact inv no
		itemTable.getColumnModel().getColumn(1).setPreferredWidth(450);  //ladke ka name
		itemTable.getColumnModel().getColumn(2).setPreferredWidth(150);  //City
		itemTable.getColumnModel().getColumn(3).setPreferredWidth(300);  //Email
		itemTable.getColumnModel().getColumn(4).setMinWidth(0);  //dto
		itemTable.getColumnModel().getColumn(4).setMaxWidth(0);  //dto


		/////////////////////////////////////////////////////////////////////////////
		itemTablePane = new JScrollPane(itemTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		itemTablePane.setBounds(28, 125, 531, 252);
		getContentPane().add(itemTablePane);

		
		/*		MonthDto mn =(MonthDto) smonth.getSelectedItem();
		sdate.setText(sdf.format(mn.getSdate()));
		edate.setText(sdf.format(mn.getEdate()));
		 */		
		exitButton.addActionListener(this);
		sendButton.addActionListener(this);
		
		
		selective.setSelected(true);
		
		selective.addActionListener(this);
		all.addActionListener(this);
		selective.setActionCommand("selective");
		all.setActionCommand("all");
		
		search = new JTextField();
		search.setBounds(103, 95, 269, 22);
		getContentPane().add(search);
		
		TableModel myTableModel = itemTable.getModel();
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(myTableModel);
        itemTable.setRowSorter(sorter);
        search.getDocument().addDocumentListener(TableDataSorter.getTableSorter(search, sorter,itemTable, 1,false));
		
		lblSearch = new JLabel("Search:");
		lblSearch.setBounds(43, 96, 95, 20);
		getContentPane().add(lblSearch);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(28, 180, 401, 58);
		getContentPane().add(panel);
		
		rdbtnDaysWise = new JRadioButton("Day's wise");
		rdbtnDaysWise.setSelected(true);
		rdbtnDaysWise.setActionCommand("selective");
		rdbtnDaysWise.setBounds(42, 43, 152, 23);
		getContentPane().add(rdbtnDaysWise);
		
		rdbtnAcBalance = new JRadioButton("A/C Balance");
		rdbtnAcBalance.setSelected(true);
		rdbtnAcBalance.setActionCommand("selective");
		rdbtnAcBalance.setBounds(212, 43, 152, 23);
		getContentPane().add(rdbtnAcBalance);
		
		rdbtnBankDetail = new JRadioButton("Bank Detail");
		rdbtnBankDetail.setSelected(true);
		rdbtnBankDetail.setActionCommand("selective");
		rdbtnBankDetail.setBounds(382, 43, 152, 23);
		getContentPane().add(rdbtnBankDetail);
		

		ButtonGroup smsbtn = new ButtonGroup();
		smsbtn.add(rdbtnDaysWise);
		smsbtn.add(rdbtnAcBalance);
		smsbtn.add(rdbtnBankDetail);
				
		rdbtnDaysWise.addActionListener(this);
		rdbtnAcBalance.addActionListener(this);
		rdbtnBankDetail.addActionListener(this);
		
				
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 11, 560, 417);
		getContentPane().add(panel_1);
		
		
		setAlwaysOnTop(true);
		
	}

	public void fillItemDataPrd(boolean res)
	{
		// Item Detail Fill 
		itemTableModel.getDataVector().removeAllElements();
		itemTableModel.fireTableDataChanged();

		smsMap = new HashMap();
		
		int size= prtList.size();
		System.out.println("size of prtlist "+size+" optn is "+optn);
		RcpDto rcp=null;
		Vector dd = null;
		for(int i =0;i<size;i++)
		{
			rcp = (RcpDto) prtList.get(i);
			if(optn==3 && code==rcp.getVreg_cd())
			{
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(rcp.getParty_name());
				dd.add(rcp.getCity());
				dd.add(rcp.getPono());
				dd.add(rcp.getVgrp_code());
				itemTableModel.addRow(dd);
				setSmsText(rcp);
				 
			}
			else if(optn==4 && code==rcp.getVdist_cd())
			{
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(rcp.getParty_name());
				dd.add(rcp.getCity());
				dd.add(rcp.getPono());
				dd.add(rcp.getVgrp_code());
				itemTableModel.addRow(dd);
				setSmsText(rcp);

			}
			else if(optn==1 && code==rcp.getVgrp_code())
			{
				
				
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(rcp.getParty_name());
				dd.add(rcp.getCity());
				dd.add(rcp.getPono());
				dd.add(rcp.getVgrp_code());
				itemTableModel.addRow(dd);
				setSmsText(rcp);

			}
			
			else if(optn==2)
			{
				
			
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(rcp.getParty_name());
				dd.add(rcp.getCity());
				dd.add(rcp.getPono());
				dd.add(rcp.getVgrp_code());
				itemTableModel.addRow(dd);
				setSmsText(rcp);

			}
		 
			
			
		}
	}
	
	

	public void setSmsText(RcpDto rcp)
	{
		DecimalFormat df = new DecimalFormat("0.00");
		StringBuffer sb=null;
		sb = new StringBuffer(rcp.getParty_name()+"\n");
		if((rcp.getCr_amt())>0)  
			sb.append("O/s 60> day "+df.format(rcp.getCr_amt())+"\n");
		if((rcp.getBill_amt())>0)  		
			sb.append("31-60 day "+df.format(rcp.getBill_amt())+"\n");
		if((rcp.getDiscount()+rcp.getVamount())>0)  
			sb.append("Upto 30 day "+df.format(rcp.getDiscount()+rcp.getVamount())+"\n");
		
		sb.append("Total = "+df.format((rcp.getDiscount()+rcp.getVamount()+rcp.getBill_amt()+rcp.getCr_amt()))+"\n");

		sb.append(loginDt.getBrnnm().split(",")[0]+"\n");

		if(loginDt.getDepo_code()==10)  // Nakoda 
			sb.append("Kotak Mahindra Bank\nA/c No 556044013810\nIFSC-KKBK0005933");
		else if(loginDt.getDepo_code()==11) // Navkar 
			sb.append("Axis Bank,Indore Br\nA/c No 910030029239563\nIFSC-UTIB0000043");

		

		if((rcp.getDiscount()+rcp.getVamount()+rcp.getBill_amt()+rcp.getCr_amt())>0)  
			smsMap.put(rcp.getVgrp_code(), sb.toString());
	}
	
	
	 public void resetAll()
	 {

		 try
		 {
			
			 fillItemDataPrd(false);
			 selective.setSelected(true);
			 //smonth.setSelectedIndex(0);
		 }
		 catch(Exception ez)
		 {
			 ez.printStackTrace();
		 }
		 search.setEditable(true);
		 search.setText("");
	 }
	 public Vector getSelectedRow()
	 {
		 
		 Vector col = null;
		 Vector selectedSF=new Vector();
		 
		 Vector data = itemTableModel.getDataVector();
		 
		 int size=data.size();
		 for (int i = 0; i <size; i++) 
		 {
			 col = (Vector) data.get(i);
			 if ((Boolean) col.get(0)) 
			 {
				 selectedSF.add(col.get(4));
			 }
		 }
		 return selectedSF;

	 }	

	 public void callPrint(String btnnm )
	 {
		 try
		 {
				submitForm(btnnm);

		 }
		 catch(Exception ez)
		 {
			 ez.printStackTrace();
		 }

	 }

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			 resetAll();
			dispose();
		}
		
	    if(e.getActionCommand().equalsIgnoreCase("Send"))
	    {
	    	String btnnm = e.getActionCommand();

	    	callPrint(btnnm);
			
			
			
			
			

		}

	    if (e.getActionCommand().equals("selective"))
		{
			fillItemDataPrd(false);
			search.setEditable(true);
			search.setText("");
		}
		
		
		if (e.getActionCommand().equals("all"))
		{
			fillItemDataPrd(true);
			search.setEditable(false);
			search.setText("");
		}
	    
	    
	}
	
	
	

	public void submitForm(String buttonName)
	{
		lodingLabel.setVisible(true);
		sendButton.setEnabled(false);
		worker = new NumberWorker(buttonName);
		worker.execute();
	}			



	public class NumberWorker extends SwingWorker<String, Object> 
	{
		String buttoName=null;
		
		NumberWorker(String btnName)
		{
			buttoName=btnName;
		}
		
		protected String doInBackground() throws Exception 
		{
			try
			{


				 //TODO
				 Vector noOfSelectedRow = getSelectedRow();
				 System.out.println("Selected row count is "+noOfSelectedRow.size());
				 if (noOfSelectedRow.isEmpty())
				 {
					 alertMessage(SMSlOpt.this, "Please Select Party!!!!");
				 }
				 else
				 {
	 
					 NewSms sms = new NewSms();
					 int size = noOfSelectedRow.size();
					 String message="";
					 PartyDto pdto=null;
					 for(int i=0;i<size;i++)
					 {
						 Integer code = (Integer) noOfSelectedRow.get(i);
						 pdto = (PartyDto) loginDt.getPrtmap().get(String.valueOf(code));
						 message=(String) smsMap.get(code);
						 if(rdbtnAcBalance.isSelected())
							 message=getMessage(message);
						 if(rdbtnBankDetail.isSelected())
							 message=getBankSms();
							
						  
						 System.out.println(message);
						 if(pdto.getMobile().length()==10)
						    sms.sendSms(pdto.getMobile(), senderId, message);
					 }

						//Object ob = (Object)constructor.newInstance(String.valueOf(mnthcode),mnthname,String.valueOf(year),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),"SMS",loginDt.getDrvnm(),loginDt.getPrinternm(),mnthno,optn,emnthcode,loginDt.getBrnnm(),loginDt.getDivnm(),patnaoptn,noOfSelectedRow,sdate,edate);
//					 resetAll();
//					 dispose();
				}

			
			}
			catch(Exception ez)
			{
				ez.printStackTrace();
			}
			System.out.println("after sending of all SMS");

			return null;
		}


		protected void done() 
		{
			try 
			{
				worker.cancel(true);
				lodingLabel.setVisible(false);
				sendButton.setEnabled(true);
				alertMessage(SMSlOpt.this, "SMS sent successfully");
				 resetAll();
				 //dispose();

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		}
	}


	
	public void setVisible(boolean b)
	{
		prtList = accdao.getOutstandingSum(year, loginDt.getDepo_code(), loginDt.getDiv_code(), sdate, edate, 1);
		fillItemDataPrd(false);
		super.setVisible(b);
		
	}
	
	public String getMessage(String message)
	{
		int index =0;
		String msg[] = message.split("\n");
		StringBuffer sb = new StringBuffer();
		int j=0;
		
		
		 
		for(int i=0;i<msg.length;i++)
		{
			if(msg[i].startsWith("Total"))
				j=i;
			if(i==0)
			{
				sb.append(msg[i]+"\n");
			}
			if(j>0)
			{
				sb.append(msg[i]+"\n");
			}
		}
		
		    index = sb.indexOf("Total");
			sb.replace(index, index+7, "Total A/C Balance as on "+sdf.format(new Date()));
		
		     return sb.toString();
	}
	
	
	public String getBankSms()
	{
		 
		StringBuffer sb=null;
		sb = new StringBuffer(loginDt.getBrnnm()+"\n");
		if(loginDt.getDepo_code()==10)  // Nakoda
		{
			sb.append("Kotak Mahindra Bank\nA/c No 556044013810\nIFSC-KKBK0005933\n");
			sb.append("State Bank of India\nA/c No 53014328976\nIFSC-SBIN0030013\n");
		    sb.append("PAN-ADRPJ4202F");
		}
		else if(loginDt.getDepo_code()==11) // Navkar
		{
			sb.append("Axis Bank,Indore Branch\nA/c No 910030029239563\nIFSC-UTIB0000043\n");
			sb.append("State Bank of India\nA/c No 31206532443\nIFSC-SBIN0003663\n");
			sb.append("PAN-AFZPJ6479A");
		}
		 return sb.toString();

	}
	
}


