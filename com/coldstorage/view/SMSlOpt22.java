package com.coldstorage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.util.NewSms;
import com.coldstorage.util.TableDataSorter;


public class SMSlOpt22 extends BaseClass implements ActionListener 
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
	 
	private ArrayList prtList;
 
	private String msg;
	
	public  SMSlOpt22(int code,String party_name,int year,int opt,String msg)
	{
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		sd = new SimpleDateFormat("yyyy-MM-dd");
		
		 
		this.year=year;
		this.msg=msg; 
		this.code=code;
		this.optn=opt;
		
		//setUndecorated(true);
		setResizable(false);
		setSize(586, 475);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		 
		
		reportName = new JLabel("SMS");
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(148, 21, 303, 20);
		getContentPane().add(reportName);
		
		
		 
		
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

		 HashMap prtmap=loginDt.getSmsmap();
		System.out.println("SIE OF SMSMAP "+prtmap.size()+" OPTN "+optn);
		Vector dd = null;
	       for(Object key : prtmap.keySet())
	       {
			   PartyDto rcp = (PartyDto)prtmap.get(key);
			if(optn==3 && code==rcp.getMregion_cd())
			{
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(rcp.getMac_name());
				dd.add(rcp.getMcity());
				dd.add(rcp.getMobile());
				dd.add(Integer.parseInt(rcp.getMac_code()));
				itemTableModel.addRow(dd);
				
				 
			}
			else if(optn==4 && code==rcp.getMdist_cd())
			{
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(rcp.getMac_name());
				dd.add(rcp.getMcity());
				dd.add(rcp.getMobile());
				dd.add(Integer.parseInt(rcp.getMac_code()));
//				dd.add(rcp.getMac_code());
				
				itemTableModel.addRow(dd);

			}
			else if(optn==2 )
			{
				dd = new Vector();
				dd.add(new Boolean(res));
				dd.add(rcp.getMac_name());
				dd.add(rcp.getMcity());
				dd.add(rcp.getMobile());
				dd.add(Integer.parseInt(rcp.getMac_code()));
				//dd.add(rcp.getMac_code());
				itemTableModel.addRow(dd);
				 

			}
			
			 
			
			
		}
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
				 selectedSF.add(col.get(3));
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
					 alertMessage(SMSlOpt22.this, "Please Select Party!!!!");
				 }
				 else
				 {
	 
					 NewSms sms = new NewSms();
					 int size = noOfSelectedRow.size();
					 PartyDto pdto=null;
					 for(int i=0;i<size;i++)
					 {
						 Integer code = (Integer) noOfSelectedRow.get(i);
						 pdto = (PartyDto) loginDt.getSmsmap().get(String.valueOf(code));
						 
						 
						 if(pdto.getMobile().length()==10)
						 {
							 System.out.println(msg);
//							 sms.sendSms(pdto.getMobile(), senderId, msg);
						 }
					 }

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
				alertMessage(SMSlOpt22.this, "SMS sent successfully");
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
		prtList = new ArrayList(loginDt.getPrtList());
		fillItemDataPrd(false);
		super.setVisible(b);
		
	}
	
	
	
}


