package com.coldstorage.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

import com.coldstorage.dao.BookDAO;
import com.coldstorage.dto.BookDto;
import com.coldstorage.print.TransportPrint;
import com.coldstorage.util.JIntegerField;
import com.coldstorage.util.TableDataSorter;
import com.coldstorage.util.TextField;

public class BankMaster extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	Font font;
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
	private JPanel panel_2;
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
	private BookDAO bDao;
	private JButton btnExcel;
	TableModel myTableModel;
	final TableRowSorter<TableModel> sorter;
	Vector data;
	public BankMaster()
	{
		sdf = new SimpleDateFormat("yyyy-MM-dd");  // Date Format
		sdate=sdf.format(loginDt.getFr_date());
		edate=sdf.format(loginDt.getTo_date());
		
		bDao = new BookDAO();
		
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format

		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		formatter = new DecimalFormat("0.00");     // Decimal Value format
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		
		
		
		// ///////////////////////////////////////////////


		
		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(400, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);


		lblPartyCode = new JLabel("Code");
		lblPartyCode.setBounds(393, 151, 126, 20);
		getContentPane().add(lblPartyCode);

		trans_code = new JIntegerField();
		trans_code.setEditable(false);
		trans_code.setBounds(530, 150,70, 23);
		getContentPane().add(trans_code);
		
		trans_name = new TextField(40);
		trans_name.setBounds(606, 150, 298, 23);
		getContentPane().add(trans_name);

		
		lblCity = new JLabel("Address:");
		lblCity.setBounds(393, 179, 126, 20);
		getContentPane().add(lblCity);

		address1 = new TextField(40);
		address1.setBounds(530, 178, 374, 23);
		getContentPane().add(address1);

		lblInvoiceNo = new JLabel("Address:");
		lblInvoiceNo.setBounds(393, 207, 126, 20);
		getContentPane().add(lblInvoiceNo);


		address2 = new TextField(40);
		address2.setBounds(530, 206,374, 23);
		getContentPane().add(address2);


		lblInvoiceDate = new JLabel("City:");
		lblInvoiceDate.setBounds(393, 235, 126, 20);
		getContentPane().add(lblInvoiceDate);
	
		city = new TextField(20);
		city.setBounds(530, 234, 208, 23);
		getContentPane().add(city);

		lblPin = new JLabel("Account No.:");
		lblPin.setBounds(393, 263, 126, 20);
		getContentPane().add(lblPin);
	
		mpin =  new TextField(20);
		mpin.setBounds(530, 262, 208, 23);
		getContentPane().add(mpin);

		lblState = new JLabel("IFSC Code:");
		lblState.setBounds(393, 291, 126, 20);
		getContentPane().add(lblState);
	
		mstate =  new TextField(20);
		mstate.setBounds(530, 290, 208, 23);
		getContentPane().add(mstate);
		
		
		lblDispatchDate = new JLabel("Bank Abbv:");
		lblDispatchDate.setBounds(393, 319, 126, 20);
		getContentPane().add(lblDispatchDate);


		contact_person = new TextField(30);
		contact_person.setBounds(530, 318, 208, 23);
		getContentPane().add(contact_person);

		

		getContentPane().add(arisleb);


		 

		branch.setText(loginDt.getBrnnm());
		branch.setBounds(400, 35, 395, 22);
		getContentPane().add(branch);

		
		lblDispatchEntry = new JLabel("Bank Master");
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDispatchEntry.setBounds(450, 60, 251, 22);
		getContentPane().add(lblDispatchEntry);

		//////////////invoce no table model/////////////////////
		String [] invColName=	{"Code", "Bank Name",""};
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
		rcpNoTable.setColumnSelectionAllowed(false);
		rcpNoTable.setCellSelectionEnabled(false);
		rcpNoTable.getTableHeader().setReorderingAllowed(false);
		rcpNoTable.getTableHeader().setResizingAllowed(false);
		rcpNoTable.getTableHeader().setFont(font);
		rcpNoTable.setRowHeight(20);
		rcpNoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		rcpNoTable.getTableHeader().setPreferredSize(new Dimension(25,25));
		///////////////////////////////////////////////////////////////////////////
		rcpNoTable.getColumnModel().getColumn(0).setPreferredWidth(45);   //receipt/////////
		rcpNoTable.getColumnModel().getColumn(1).setPreferredWidth(300);  //party name/////
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
		rcpNoPane.setBounds(17, 203, 300, 400);
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
		JLabel label_1 = new JLabel("Name:");
		label_1.setBounds(19, 175, 70, 20);
		getContentPane().add(label_1);

		search = new JFormattedTextField();
		search.setBounds(109, 174, 208, 22);
		getContentPane().add(search);

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

                        BookDto bDto = (BookDto) myTableModel.getValueAt(modelRow, 2);
    				 	clearAll();
    				 	fillData(bDto);
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

                        BookDto bDto = (BookDto) myTableModel.getValueAt(modelRow, 2);
    				 	clearAll();
    				 	fillData(bDto);
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

                    BookDto bDto = (BookDto) myTableModel.getValueAt(modelRow, 2);
				 	clearAll();
				 	fillData(bDto);
				 	rcpNoTable.changeSelection(modelRow, 0, false, false);

					
                } 
				
 
				 
				ee.consume();
			}
		});
		
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(866, 613, 86, 30);
		getContentPane().add(exitButton);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(774, 613, 86, 30);
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
		btnAdd.setBounds(355, 616, 86, 30);
		getContentPane().add(btnAdd);
		
		
		btnExcel = new JButton("Excel");
		btnExcel.setBounds(445, 617, 86, 30);
		getContentPane().add(btnExcel);
		
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(355, 657, 598, 48);
		getContentPane().add(panel_2);

		
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBackground(new Color(51, 102, 204));
		panel.setBounds(11, 138, 313, 28);
		getContentPane().add(panel);

		JLabel label_3 = new JLabel("Search");
		label_3.setForeground(Color.WHITE);
		panel.add(label_3);


		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
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

		
		
		
		trans_code.addKeyListener(keyListener);
		trans_name.addKeyListener(keyListener);
		address1.addKeyListener(keyListener);
		address2.addKeyListener(keyListener);
		city.addKeyListener(keyListener);
		mpin.addKeyListener(keyListener);
		mstate.addKeyListener(keyListener);
		contact_person.addKeyListener(keyListener);

		
		
		exitButton.addActionListener(this);
		exitButton.setActionCommand("exit");
		
		btnAdd.addActionListener(this);
		btnAdd.setActionCommand("add");
		
		saveButton.addActionListener(this);
		saveButton.setActionCommand("save");				
		
		btnExcel.addActionListener(this);
		btnExcel.setActionCommand("Excel");	
		
		
		
				
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
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
		//rcpNoTableModel.getDataVector().removeAllElements();
	//	rcpNoTableModel.fireTableDataChanged();
		
	//	fillInvTable("");


		//rcpNoPane.getHorizontalScrollBar().setValue(0);
		//rcpNoPane.getVerticalScrollBar().setValue(0);
		

		
	}
	

	
	public BookDto bankAdd() 
	{
		BookDto bdto=null;
		try 
		{
			
			bdto=new BookDto();
			bdto.setDiv_code(loginDt.getDiv_code());
			bdto.setDepo_code(loginDt.getDepo_code());
			bdto.setBook_name(trans_name.getText());
			bdto.setBank_add(address1.getText());
			bdto.setBank_add1(address2.getText());
			bdto.setBank_city(city.getText());
			bdto.setIfsc_code(mstate.getText());
			bdto.setBank_acno(mpin.getText());
			bdto.setBook_abvr(contact_person.getText());
			bdto.setHead_code(setIntNumber(trans_code.getText()));
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		btnAdd.setEnabled(true);
		
		return bdto;

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
			dispose();
		}
		
		if(e.getActionCommand().equalsIgnoreCase("add"))
		{
			clearAll();
			btnAdd.setEnabled(false);
			trans_name.requestFocus();
			//trans_code.setText(String.valueOf(trnDao.getMaxNumber(loginDt.getDepo_code())));
		}
		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			saveData();
			saveButton.setBackground(null);
		}
		
		if(e.getActionCommand().equalsIgnoreCase("delete"))
		{

			int answer = 0;

			BookDto bDto = bankAdd();
			
			if(bDto.getBook_code()!=0)
			{
				answer = JOptionPane.showConfirmDialog(BankMaster.this, "Are You Sure ?");
				if (answer == JOptionPane.YES_OPTION) 
				{

					//trnDao.deleteTransporter(trnDto);
					clearAll();

				}
			}
		}
		

	}
	
	
	public void saveData()
	{
		if(btnAdd.isEnabled())
		{
			bDao.updateBank(bankAdd());
		}
		else
		{
			//trnDao.addTransport(transporterAdd());
			BookDto bdto = bankAdd();
			bDao.addBank(bdto);
			Vector v = loginDt.getBooklist();
			v.add(bdto);
			loginDt.setBooklist(v);
		}
		
		clearAll();
		fillInvTable("");
	
		//trans_code.requestFocus();
	}
	
	
	
	
	
	
	
	public void fillData(BookDto bdto)
	{

		trans_code.setText(String.valueOf(bdto.getHead_code()));
		trans_name.setText(bdto.getBook_name());
		address1.setText(bdto.getBank_add());
		address2.setText(bdto.getBank_add1());
		city.setText(bdto.getBank_city());
		mpin.setText(bdto.getBank_acno());
		mstate.setText(bdto.getIfsc_code());
		contact_person.setText(bdto.getBook_abvr());
		
		btnAdd.setEnabled(true);
		
	}
		
	
	public void fillInvTable(String search)
	{
		rcpNoTableModel.getDataVector().removeAllElements();
		data = (Vector) bDao.getBookList(loginDt.getDiv_code(), loginDt.getDepo_code());

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
		super.setVisible(b);
	}
}


