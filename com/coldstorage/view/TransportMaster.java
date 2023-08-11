package com.coldstorage.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.coldstorage.dao.TransportDAO;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.print.TransportPrint;
import com.coldstorage.util.JIntegerField;
import com.coldstorage.util.TableDataSorter;
import com.coldstorage.util.TextField;

public class TransportMaster extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	Font font,fontHindi,fontplan;
	private JIntegerField trans_code;
	private JTextField address2;
	private JTextField contact_person;
	private JTextField city,mpin,mstate;
	private JTextField phone;
	private JTextField mobile,email_id;
	private JTextField address1;
	
	private JLabel lblInvoiceNo ;
	private JLabel lblPartyCode; 
	private JLabel lblDispatchDate;
	private JLabel lblInvoiceDate;
	private JLabel lblLrDate;
	private JLabel lblNoOfCases;
	private JLabel lblCity,lblPin,lblState;
	private JLabel lblDispatchEntry;
	private JPanel panel_2,panel_4;
	private JButton exitButton,saveButton;
	private SimpleDateFormat sdf;

	
	private DefaultTableModel rcpNoTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable rcpNoTable;
	private JScrollPane rcpNoPane;
	String partyCd;
	String sdate,edate;
	
	NumberFormat formatter;	
	private JLabel lblbalance;
	private JTextField trans_id;
	private JTextField trans_name;
	private JTextField trans_gstno;
	private JLabel lblServiceTaxNo;
	private JButton btnAdd;
	private JFormattedTextField search;
	private TransportDAO trnDao=null;
	private JButton btnExcel;
	TableModel myTableModel;
	final TableRowSorter<TableModel> sorter;
	Vector data;
	private JLabel lblnamehindi;
	private TextField mac_name_hindi;
	private JLabel lblcityhindi;
	private TextField mcity_hindi;
	private JLabel lblName_1;
	private JPanel panel_6;
	private JLabel lblSearch;
	String option=null;
	public TransportMaster()
	{
		sdf = new SimpleDateFormat("yyyy-MM-dd");  // Date Format
		sdate=sdf.format(loginDt.getFr_date());
		edate=sdf.format(loginDt.getTo_date());
		
		trnDao = new TransportDAO();
		option="";
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format

		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		formatter = new DecimalFormat("0.00");     // Decimal Value format
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 670);		
	       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		        
	        this.setUndecorated(true);
	        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

	        arisleb.setVisible(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		// / setting default focus request
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				search.requestFocus();
			}

			public void windowClosed(WindowEvent e) {
				clearAll();
			}
		});

		// setAlwaysOnTop(true);

		
		
		fontplan =new Font("Tahoma", Font.PLAIN, 14);
		font =new Font("Tahoma", Font.BOLD, 14);
		fontHindi = new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14);
		
		
		// ///////////////////////////////////////////////


/*		
		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(540, 672, 577, 15);
		getContentPane().add(lblFinancialAccountingYear);
*/

		
		panel_6 = new JPanel();
//		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_6.setBackground(new Color(51, 102, 204));
		panel_6.setBounds(14, 126, 305, 32);
		getContentPane().add(panel_6);

		lblSearch = new JLabel("Search");
		panel_6.add(lblSearch);
		lblSearch.setForeground(Color.WHITE);
		
		
		JLabel label_1 = new JLabel("Name:");
		label_1.setBounds(19, 170, 70, 26);
		getContentPane().add(label_1);

		search = new JFormattedTextField();
		search.setBounds(75, 170, 243, 26);
		getContentPane().add(search);

		
		lblPartyCode = new JLabel("A/C No");
		lblPartyCode.setBounds(393, 151, 126, 20);
		getContentPane().add(lblPartyCode);

		trans_code = new JIntegerField();
		trans_code.setBounds(530, 150,70, 23);
		trans_code.setEditable(false);
		trans_code.setFont(font);
		getContentPane().add(trans_code);
		trans_code.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearAll();
					dispose();
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
				   if (option.equals("A"))
				   {
						try
						{
							int accountcode=setIntNumber(trans_code.getText());
							boolean chk = trnDao.checkParty(loginDt.getDepo_code(),accountcode);
							if (chk)
							{
								JOptionPane.showMessageDialog(TransportMaster.this,"Code Already Exists " ,"Duplicate Code",JOptionPane.INFORMATION_MESSAGE);						
								trans_code.setText("");
								trans_code.requestFocus();							
								
							}
							else
							{
								trans_name.requestFocus();
								trans_name.setSelectionStart(0);
							}
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
				   }
					evt.consume();
				}
				
			}
		});		

		
		trans_name = new TextField(40);
		trans_name.setBounds(606, 150, 298, 23);
		trans_name.setFont(font);
		getContentPane().add(trans_name);

		
		lblCity = new JLabel("Address:");
		lblCity.setBounds(393, 179, 126, 20);
		getContentPane().add(lblCity);

		address1 = new TextField(45);
		address1.setBounds(530, 178, 374, 23);
		address1.setFont(font);
		getContentPane().add(address1);

		lblInvoiceNo = new JLabel("Address:");
		lblInvoiceNo.setBounds(393, 207, 126, 20);
		getContentPane().add(lblInvoiceNo);


		address2 = new TextField(45);
		address2.setBounds(530, 206,374, 23);
		address2.setFont(font);
		getContentPane().add(address2);


		lblInvoiceDate = new JLabel("City:");
		lblInvoiceDate.setBounds(393, 235, 126, 20);
		getContentPane().add(lblInvoiceDate);
	
		city = new TextField(20);
		city.setBounds(530, 234, 374, 23);
		city.setFont(font);
		getContentPane().add(city);

		lblPin = new JLabel("Pin:");
		lblPin.setBounds(393, 263, 126, 20);
		getContentPane().add(lblPin);
	
		mpin =  new TextField(15);
		mpin.setBounds(530, 262, 374, 23);
		mpin.setFont(font);
		getContentPane().add(mpin);

		lblState = new JLabel("State:");
		lblState.setBounds(393, 291, 126, 20);
		getContentPane().add(lblState);
	
		mstate =  new TextField(45);
		mstate.setBounds(530, 290, 374, 23);
		mstate.setFont(font);
		getContentPane().add(mstate);
		
		
		lblDispatchDate = new JLabel("Contact Person:");
		lblDispatchDate.setBounds(393, 319, 126, 20);
		getContentPane().add(lblDispatchDate);


		contact_person = new TextField(45);
		contact_person.setBounds(530, 318, 374, 23);
		contact_person.setFont(font);
		getContentPane().add(contact_person);

		
		lblLrDate = new JLabel("Phone No.");
		lblLrDate.setBounds(393, 347, 126, 20);
		getContentPane().add(lblLrDate);

		phone = new TextField(20);
		phone.setBounds(530, 346, 374, 23);
		phone.setFont(font);
		getContentPane().add(phone);

		
		lblNoOfCases = new JLabel("Mobile:");
		lblNoOfCases.setBounds(393, 375, 126, 20);
		getContentPane().add(lblNoOfCases);

		mobile = new TextField(20);
		mobile.setBounds(530, 374, 374, 23);
		mobile.setFont(font);
		getContentPane().add(mobile);

		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(393, 403, 126, 20);
		getContentPane().add(lblEmail);
		
		email_id = new TextField(100);
		email_id.setBounds(530, 402, 374, 23);
		email_id.setFont(font);
		getContentPane().add(email_id);
		

		lblServiceTaxNo = new JLabel("Aadhar No:");
		lblServiceTaxNo.setBounds(393, 431, 126, 20);
		getContentPane().add(lblServiceTaxNo);
		
		trans_gstno = new TextField(12);
		trans_gstno.setBounds(530, 430, 374, 23);
		trans_gstno.setFont(font);
		getContentPane().add(trans_gstno);

		
	    lblbalance = new JLabel("PAN:");
		lblbalance.setBounds(393, 459, 126, 20);
		getContentPane().add(lblbalance);
		
		trans_id = new TextField(10);
		trans_id.setBounds(530, 458, 374, 23);
		trans_id.setFont(font);
		getContentPane().add(trans_id);

		
	    lblnamehindi = new JLabel("Name in Hindi:");
	    lblnamehindi.setBounds(393, 487, 126, 20);
		getContentPane().add(lblnamehindi);
		
		mac_name_hindi = new TextField(200);
		mac_name_hindi.setBounds(530, 487, 374, 23);
		mac_name_hindi.setFont(fontHindi);
		getContentPane().add(mac_name_hindi);

	    lblcityhindi = new JLabel("City in Hindi:");
	    lblcityhindi.setBounds(393, 515, 126, 20);
		getContentPane().add(lblcityhindi);
		
		mcity_hindi = new TextField(45);
		mcity_hindi.setBounds(530, 515, 374, 23);
		mcity_hindi.setFont(fontHindi);
		getContentPane().add(mcity_hindi);


		getContentPane().add(arisleb);


		 

/*		branch.setText(loginDt.getBrnnm());
		branch.setBounds(300, 35, 595, 22);
		getContentPane().add(branch);
*/
		
		lblDispatchEntry = new JLabel("Account Master");
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDispatchEntry.setBounds(450, 90, 251, 22);
		getContentPane().add(lblDispatchEntry);

		//////////////invoce no table model/////////////////////
		String [] invColName=	{"Code", "Name",""};
		String datax[][]={{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}};
		
		rcpNoTableModel=  new DefaultTableModel(datax,invColName)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				return false;
			}
		};

		rcpNoTable = new JTable(rcpNoTableModel);
		rcpNoTable.setFont(fontplan);
		rcpNoTable.setColumnSelectionAllowed(false);
		rcpNoTable.setCellSelectionEnabled(false);
		rcpNoTable.getTableHeader().setReorderingAllowed(false);
		rcpNoTable.getTableHeader().setResizingAllowed(false);
		rcpNoTable.getTableHeader().setFont(fontplan);
		rcpNoTable.setRowHeight(20);
		rcpNoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		rcpNoTable.getTableHeader().setPreferredSize(new Dimension(25,25));
		///////////////////////////////////////////////////////////////////////////
		rcpNoTable.getColumnModel().getColumn(0).setPreferredWidth(55);   //receipt/////////
		rcpNoTable.getColumnModel().getColumn(1).setPreferredWidth(290);  //party name/////
		rcpNoTable.getColumnModel().getColumn(2).setMinWidth(0);  //inv_no/////
		rcpNoTable.getColumnModel().getColumn(2).setMaxWidth(0);  //inv_no/////
		
		
		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer() 
		{
			public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) 
			{
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (rcpNoTable.getSelectedRow()==row )
				{
					  c.setBackground(Color.LIGHT_GRAY);
					  c.setForeground(Color.BLACK); 
				}
				else
				{
					  c.setBackground(Color.WHITE);
					  c.setForeground(Color.BLACK);

				}
		        return c;
			}
		};
		
		rcpNoTable.getColumnModel().getColumn(0).setCellRenderer(dtc);
		rcpNoTable.getColumnModel().getColumn(1).setCellRenderer(dtc);
		
		/////////////////////////////////////////////////////////////////////////////
		rcpNoPane = new JScrollPane(rcpNoTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		rcpNoPane.setBounds(17, 203, 300, 422);
		getContentPane().add(rcpNoPane);
		fillInvTable("");
		
/*		rcpNoTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent e) 
			{
				int row = rcpNoTable.getSelectedRow();
				TransportDto trDto = (TransportDto) rcpNoTable.getValueAt(row, 2);
				if(trDto!=null)
				{
					fillData(trDto);
				}
				
			}
		});
		
*/		

		myTableModel = rcpNoTable.getModel();
        sorter = new TableRowSorter<TableModel>(myTableModel);
        rcpNoTable.setRowSorter(sorter);
		search.getDocument().addDocumentListener(TableDataSorter.getTableSorter(search, sorter,rcpNoTable, 1,false));
		
	/*	
		rcpNoTable.getSelectionModel().addListSelectionListener(
	                new ListSelectionListener() {
	                    public void valueChanged(ListSelectionEvent event) {
	                        int viewRow = rcpNoTable.getSelectedRow();
	                        if (viewRow < 0) {
	                            //Selection got filtered away.
	                            //statusText.setText("");
	                        } else {
	                            int modelRow = 
	                            		rcpNoTable.convertRowIndexToModel(viewRow);
	            				TransportDto trDto = (TransportDto) myTableModel.getValueAt(modelRow, 2);
	        				 	clearAll();
	        				 	fillData(trDto);
	        					rcpNoPane.getHorizontalScrollBar().setValue(0);
	        					rcpNoPane.getVerticalScrollBar().setValue(0);
	        					rcpNoPane.getVerticalScrollBar().setUnitIncrement(0);
	        					//party_name.setText(pnm);
	                            //statusText.setText(String.format("Selected Row in view: %d. " +"Selected Row in model: %d.",viewRow, modelRow));
	                        }
	                    }
	                    
	                       
	                }
	        );*/

		rcpNoTable.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				int viewRow = rcpNoTable.getSelectedRow();
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
                    if (viewRow < 0) {
                        //Selection got filtered away.
                        //statusText.setText("");
                    } else
                    {
                        int modelRow = rcpNoTable.convertRowIndexToModel(viewRow);

                        TransportDto trDto = (TransportDto) myTableModel.getValueAt(modelRow, 2);
    				 	clearAll();
    				 	fillData(trDto);
    				 	rcpNoTable.changeSelection(modelRow, 0, false, false);
    					
                    }
					evt.consume();
				}
				
			}
			
			
			public void keyReleased(KeyEvent evt) 
			{
				int viewRow = rcpNoTable.getSelectedRow();
				if((evt.getKeyCode() == KeyEvent.VK_DOWN ) || (evt.getKeyCode() == KeyEvent.VK_UP ) || (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) || (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN))
				{
                    if (viewRow < 0) {
                        //Selection got filtered away.
                        //statusText.setText("");
                    } else
                    {
                        int modelRow = rcpNoTable.convertRowIndexToModel(viewRow);

                        TransportDto trDto = (TransportDto) myTableModel.getValueAt(modelRow, 2);
    				 	clearAll();
    				 	fillData(trDto);
    					//scrollPane.getHorizontalScrollBar().setValue(0);
    					//scrollPane.getVerticalScrollBar().setValue(0);
    					//scrollPane.getVerticalScrollBar().setUnitIncrement(0);
    					//party_name.setText(pnm);
                        //statusText.setText(String.format("Selected Row in view: %d. " +"Selected Row in model: %d.",viewRow, modelRow));
                    }

					evt.consume();
				}
				
			}
			
			
		});		
		
		rcpNoTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent ee) 
			{
				int viewRow = rcpNoTable.getSelectedRow();
				 
				if (viewRow < 0) {
                    //Selection got filtered away.
                    //statusText.setText("");
                } else {
                    
                    int modelRow = rcpNoTable.convertRowIndexToModel(viewRow);

                    TransportDto trDto = (TransportDto) myTableModel.getValueAt(modelRow, 2);
				 	clearAll();
				 	fillData(trDto);
				 	rcpNoTable.changeSelection(modelRow, 0, false, false);

					
                } 
				
 
				 
				ee.consume();
			}
		});
		
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(866, 602, 86, 30);
		exitButton.setMnemonic(KeyEvent.VK_E);
		getContentPane().add(exitButton);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(774, 602, 86, 30);
		saveButton.setMnemonic(KeyEvent.VK_S);
		getContentPane().add(saveButton);
		
		saveButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					saveData();
					saveButton.setBackground(null);
				}
			}
		});
		
		
		
		
		
		
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(355, 602, 86, 30);
		btnAdd.setMnemonic(KeyEvent.VK_A);
		getContentPane().add(btnAdd);
		
		
		btnExcel = new JButton("Excel");
		btnExcel.setBounds(445, 602, 86, 30);
		btnExcel.setMnemonic(KeyEvent.VK_X);
		getContentPane().add(btnExcel);

		
		panel_4 = new JPanel();
//		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_4.setBounds(322, 122, 670, 516);
//		panel_4.setBounds(8, 122, 984, 516);
		getContentPane().add(panel_4);


		
		
		panel_2 = new JPanel();
//		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 3));
//		panel_2.setBackground(SystemColor.activeCaptionBorder);
//		panel_2.setBounds(355, 657, 598, 48);
		panel_2.setBounds(8, 122, 984, 516);
		panel_2.setFont(font);
		getContentPane().add(panel_2);

	
		
		
		JPanel panel = new JPanel();
//		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBackground(new Color(51, 102, 204));
		panel.setBounds(11, 138, 313, 28);
		panel.setFont(font);
		getContentPane().add(panel);

		JLabel label_3 = new JLabel("Search");
		label_3.setForeground(Color.WHITE);
		panel.add(label_3);


		JPanel panel_3 = new JPanel();
//		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_3.setBounds(11, 137, 312, 474);
		getContentPane().add(panel_3);
		
		
		KeyListener keyListener = new KeyListener() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					JTextField textField = (JTextField) keyEvent.getSource();
					int id = Integer.parseInt(textField.getName());
					switch (id) 
					{
					case 0:
						trans_code.requestFocus();
						trans_code.setSelectionStart(0);
						break;
					case 1:
						trans_name.requestFocus();
						trans_code.setSelectionStart(0);
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
						city.requestFocus();
						city.setSelectionStart(0);
						break;
					case 5:
						mpin.requestFocus();
						mpin.setSelectionStart(0);
						break;
					case 6:
						mstate.requestFocus();
						mstate.setSelectionStart(0);
						break;
					case 7:
						contact_person.requestFocus();
						contact_person.setSelectionStart(0);
						break;
					case 8:
						phone.requestFocus();
						phone.setSelectionStart(0);
						break;
					case 9:
						mobile.requestFocus();
						mobile.setSelectionStart(0);
						break;
					case 10:
						email_id.requestFocus();
						email_id.setSelectionStart(0);
						break;
					case 11:
						trans_gstno.requestFocus();
						trans_gstno.setSelectionStart(0);
						break;
					case 12:
						trans_id.requestFocus();
						trans_id.setSelectionStart(0);
						break;
					case 13:
						mac_name_hindi.requestFocus();
						mac_name_hindi.setSelectionStart(0);
						break;
					case 14:
						mcity_hindi.requestFocus();
						mcity_hindi.setSelectionStart(0);
						break;
					case 15:
						saveButton.requestFocus();
						saveButton.setBackground(Color.BLUE);
						exitButton.setBackground(null);

						break;
					}
				}

				if (key == KeyEvent.VK_ESCAPE) {
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
		
		
		
		
		
		
		
		trans_code.setName("1");
		trans_name.setName("2");
		address1.setName("3");
		address2.setName("4");
		city.setName("5");
		mpin.setName("6");
		mstate.setName("7");
		contact_person.setName("8");
		phone.setName("9");
		mobile.setName("10");
		email_id.setName("11");
		trans_gstno.setName("12");
		trans_id.setName("13");
		mac_name_hindi.setName("14");
		mcity_hindi.setName("15");
		
		
		
//		trans_code.addKeyListener(keyListener);
		trans_name.addKeyListener(keyListener);
		address1.addKeyListener(keyListener);
		address2.addKeyListener(keyListener);
		city.addKeyListener(keyListener);
		mpin.addKeyListener(keyListener);
		mstate.addKeyListener(keyListener);
		contact_person.addKeyListener(keyListener);
		phone.addKeyListener(keyListener);
		mobile.addKeyListener(keyListener);
		email_id.addKeyListener(keyListener);
		trans_gstno.addKeyListener(keyListener);
		trans_id.addKeyListener(keyListener);
		mac_name_hindi.addKeyListener(keyListener);
		mcity_hindi.addKeyListener(keyListener);
		
		
		exitButton.addActionListener(this);
		exitButton.setActionCommand("exit");
		
		btnAdd.addActionListener(this);
		btnAdd.setActionCommand("add");
		
		saveButton.addActionListener(this);
		saveButton.setActionCommand("save");				
		
		btnExcel.addActionListener(this);
		btnExcel.setActionCommand("Excel");	
		
		
		
				
		JPanel panel_1 = new JPanel();
//		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(355, 138, 598, 428);
		getContentPane().add(panel_1);
		
		
		
		
		
	}

	
	public void clearAll()
	{

		trans_code.setText("");
		trans_name.setText("");
		address1.setText("");
		address2.setText("");
		city.setText("");
		mpin.setText("");
		mstate.setText("");
		contact_person.setText("");
		phone.setText("");
		mobile.setText("");
		email_id.setText("");
		trans_gstno.setText("");
		trans_id.setText("");
		mac_name_hindi.setText("");
		mcity_hindi.setText("");
		trans_code.setEditable(false);
		btnAdd.setEnabled(true);
		
		//rcpNoTableModel.getDataVector().removeAllElements();
	//	rcpNoTableModel.fireTableDataChanged();
		
	//	fillInvTable("");


		//rcpNoPane.getHorizontalScrollBar().setValue(0);
		//rcpNoPane.getVerticalScrollBar().setValue(0);
		

		
	}
	

	
	public TransportDto transporterAdd() 
	{
		TransportDto trnDto=null;
		try 
		{
			
			trnDto=new TransportDto();
			trnDto.setDepo_code(loginDt.getDepo_code());
			trnDto.setDiv_code(loginDt.getDiv_code());
			trnDto.setAccount_no(setIntNumber(trans_code.getText().trim()));
			trnDto.setTran_name(trans_name.getText());
			trnDto.setAddress1(address1.getText());
			trnDto.setAddress2(address2.getText());
			trnDto.setCity(city.getText());
			trnDto.setMpin(mpin.getText());
			trnDto.setMstate(mstate.getText());
			trnDto.setContact_person(contact_person.getText());
			trnDto.setPhone(phone.getText());
			trnDto.setMobile(mobile.getText());
			trnDto.setEmail_id(email_id.getText());
			trnDto.setTran_gstno(trans_gstno.getText());
			trnDto.setTran_id(trans_id.getText());
			trnDto.setMac_name_hindi(mac_name_hindi.getText());
			trnDto.setMcity_hindi(mcity_hindi.getText());
			trnDto.setFin_year(loginDt.getFin_year());
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		btnAdd.setEnabled(true);
		
		return trnDto;

	}
	
	

	
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equalsIgnoreCase("Excel"))
		{
			
			TransportPrint invp = new TransportPrint(loginDt.getDrvnm(),loginDt.getBrnnm(),data);

			clearAll();
			
		}


		
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			clearAll();
			search.setText("");
			btnAdd.setEnabled(true);
			dispose();
		}
		
		if(e.getActionCommand().equalsIgnoreCase("add"))
		{
			clearAll();
			btnAdd.setEnabled(false);
			trans_code.requestFocus();
			trans_code.setEditable(true);
			option="A";
//			trans_code.setText(String.valueOf(trnDao.getMaxNumber(loginDt.getDepo_code())));
		}
		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			saveData();
			saveButton.setBackground(null);
		}
		
		if(e.getActionCommand().equalsIgnoreCase("delete"))
		{

			int answer = 0;

			TransportDto trnDto = transporterAdd();
			
			if(trnDto.getTran_code().length()!=0)
			{
				answer = JOptionPane.showConfirmDialog(TransportMaster.this, "Are You Sure ?");
				if (answer == JOptionPane.YES_OPTION) 
				{

					trnDao.deleteTransporter(trnDto);
					clearAll();

				}
			}
		}
		

	}
	
	
	public void saveData()
	{
		if(btnAdd.isEnabled())
		{
			trnDao.updateTransport(transporterAdd());
		}
		else
		{
			String nm=trans_name.getText();
			String cit=city.getText();
			int chk = trnDao.checkParty(loginDt.getDepo_code(),nm,cit,loginDt.getDiv_code());
			if (chk>0)
			{
				JOptionPane.showMessageDialog(TransportMaster.this,"Party Already Exists in Code No "+chk ,"Duplicate Party",JOptionPane.INFORMATION_MESSAGE);						
				trans_code.setText("");
				trans_code.requestFocus();							
				
			}
			else
				trnDao.addTransport(transporterAdd());
		}
		
		clearAll();
		search.setText("");
		fillInvTable("");
	
		//trans_code.requestFocus();
	}
	
	
	
	
	
	
	
	public void fillData(TransportDto trnDt)
	{

		trans_code.setText(trnDt.getTran_code());
		trans_name.setText(trnDt.getTran_name());
		address1.setText(trnDt.getAddress1());
		address2.setText(trnDt.getAddress2());
		city.setText(trnDt.getCity());
		mpin.setText(trnDt.getMpin());
		mstate.setText(trnDt.getMstate());
		contact_person.setText(trnDt.getContact_person());
		phone.setText(trnDt.getPhone());
		mobile.setText(trnDt.getMobile());
		email_id.setText(trnDt.getEmail_id());
		trans_gstno.setText(trnDt.getTran_gstno());
		trans_id.setText(trnDt.getTran_id());
		mac_name_hindi.setText(trnDt.getMac_name_hindi());
		mcity_hindi.setText(trnDt.getMcity_hindi());
		
		btnAdd.setEnabled(true);
		
	}
		
	
	public void fillInvTable(String search)
	{
		rcpNoTableModel.getDataVector().removeAllElements();
		data = (Vector) trnDao.getTransport(loginDt.getDepo_code(),search,loginDt.getDiv_code());

		Vector c = null;
		int s = data.size();

		for(int i =0;i<s;i++)
		{
			c =(Vector) data.get(i);
			rcpNoTableModel.addRow(c);
		}
		
		if (s==0)
		{
			for(int i =0;i<30;i++)
			{
				rcpNoTableModel.addRow(new Object[2][]);
			}
		}
		
	}
	public void setVisible(boolean b)
	{
		fillInvTable("");
		search.requestFocus();
		super.setVisible(b);
	}
}


