package com.coldstorage.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import java.text.Format;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.coldstorage.dao.GroupDAO;
import com.coldstorage.dao.ProductDAO;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.ProductDto;
import com.coldstorage.print.ProductPrint;
import com.coldstorage.util.JDoubleField;
import com.coldstorage.util.TableDataSorter;
import com.coldstorage.util.TextField;

public class PackMaster extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_12;
	private JLabel label_10;
	private JLabel lblDispatchEntry,lblLock;
	private JLabel lbldisc,lblopen,lblcurrent;
	private JLabel lblmrprate;
	private JLabel lblmfgrate;
	private JLabel lblstkrate;
	private JLabel lbltaxtype;
	private JLabel lblTaxesasPer;
	private JLabel lblScheme,lblStock;
	private JLabel lblName_1;
	private JLabel lblSearch;
	private JLabel lblHsnCode;
	private JLabel lblProductCode,lblCompanyCode;

	
	
	private JLabel lblProductName,lblCompanyName;
	private JLabel lblpack,lblshortname;
	private JLabel lblgroup;
	private JLabel lblgroupname;

	private JButton exitButton;
	private JButton btnSave,btnAdd,btnPrint;
	private JTable productTable;

	private JPanel panel_2;
	private JPanel panel_5;
	private JPanel panel_6;
	 
	private JFormattedTextField prd_name;
	private JScrollPane scrollPane;
	

	private DefaultTableModel productTableModel;
	private Font fontPlan,font,fontHindi;
	ProductDAO prddao;
	NumberFormat formatter;
	String option=null;
	private JList cmpList,grpList;
	private JScrollPane  cmpPane,grpPane;
	private JTextField pcode,cpcode;
	private JTextField pname,cpname;
	private JTextField pack,shortname;
	private JTextField pd_group;
	private JTextField grp_name;
	private JTextField stock,opening;
	private JDoubleField pur_rate;
	private JDoubleField mrp_rate;
	private JDoubleField sale_rate;
	private JComboBox tax_type1,status;
	private JDoubleField disc_cd1,tax_per;
	GroupDAO gdao ;
	GroupDto gp;
	private Vector groupNames;
	private TableModel myTableModel;
	private TableRowSorter<TableModel> sorter;
	boolean adddata=false;
	private int lastno;
	public PackMaster()
	{
		
		setResizable(false);
		setSize(1024, 670);		
		
	       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		        
	        this.setUndecorated(true);
	        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

	        arisleb.setVisible(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Pack Master");
		getContentPane().setLayout(null);
		
		option="";
		formatter = new DecimalFormat("0.00");     // Decimal Value format
		gdao = new GroupDAO();
		prddao = new ProductDAO();
		
/*		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(450, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);
*/
		fontPlan =new Font("Tahoma", Font.PLAIN, 14);
		font = new Font("Tahoma", Font.BOLD, 11);
		fontHindi = new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14);
		// ////////////////////////////////////////////////////////////////	

		


//		getContentPane().add(arisleb);

		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);

/*		branch.setText(loginDt.getBrnnm());
		branch.setBounds(300, 35, 595, 22);
		getContentPane().add(branch);
*/		
		lblDispatchEntry = new JLabel("Pack Master");
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDispatchEntry.setBounds(450, 90, 251, 22);
		getContentPane().add(lblDispatchEntry);

		


		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setBounds(273, 602, 86, 30);
		btnAdd.setMnemonic(KeyEvent.VK_A);
		getContentPane().add(btnAdd);
		btnAdd.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_A) {
					clearAll();
					option="1";
					adddata=true;
					cpcode.requestFocus();

				}
			}
		});
	

		
		btnPrint = new JButton("Excel");
		btnPrint.setVisible(false);
		btnPrint.setActionCommand("Print");
		btnPrint.setMnemonic(KeyEvent.VK_X);
		btnPrint.setBounds(367, 602, 86, 30);
		getContentPane().add(btnPrint);

		btnSave= new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.setActionCommand("Save");
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.setBounds(799, 602, 86, 30);
		getContentPane().add(btnSave);
		btnSave.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					saveData();	
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					exitButton.requestFocus();
					exitButton.setBackground(Color.blue);
					btnSave.setBackground(null);
					 
				}
			}
		});

		exitButton = new JButton("Exit");
		exitButton.setActionCommand("Exit");
		exitButton.setMnemonic(KeyEvent.VK_E);
		exitButton.setBounds(891, 602, 86, 30);
		getContentPane().add(exitButton);
		exitButton.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					saveData();	
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					btnSave.requestFocus();
					btnSave.setBackground(Color.blue);
					exitButton.setBackground(null);
				}
				
				
			}
		});		 

		lblName_1 = new JLabel("Search:");
		lblName_1.setForeground(Color.BLUE);
		lblName_1.setBounds(16, 164, 83, 20);
		getContentPane().add(lblName_1);

		prd_name = new JFormattedTextField((Format) null);
		prd_name.setFont(new Font("Tahoma", Font.BOLD, 11));
		prd_name.setBounds(75, 164, 188, 24);
		getContentPane().add(prd_name);
				
		
		////////////// invoce no table model/////////////////////
		String [] partyColName=	{"Code.", "Pack Name",""};
		String datax[][]={{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}};
		productTableModel=  new DefaultTableModel(datax,partyColName)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				return false;
			}
		};


		
		productTable = new JTable(productTableModel);
		productTable.setFont(fontPlan);
		productTable.setColumnSelectionAllowed(false);
		productTable.setCellSelectionEnabled(false);
		productTable.getTableHeader().setReorderingAllowed(false);
		productTable.getTableHeader().setResizingAllowed(false);
		productTable.getTableHeader().setFont(font);
		productTable.setRowHeight(20);
		productTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		productTable.getTableHeader().setPreferredSize(new Dimension(25,25));
		///////////////////////////////////////////////////////////////////////////
		productTable.getColumnModel().getColumn(0).setPreferredWidth(75);   //contact inv no
		productTable.getColumnModel().getColumn(1).setPreferredWidth(300);  //party name/////
		productTable.getColumnModel().getColumn(2).setMinWidth(0);  //inv_no/////
		productTable.getColumnModel().getColumn(2).setMaxWidth(0);  //inv_no/////
		fillInvTable(); 
		
		scrollPane = new JScrollPane(productTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(16, 194, 247, 432);
		getContentPane().add(scrollPane);

		panel_6 = new JPanel();
//		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_6.setBackground(new Color(51, 102, 204));
		panel_6.setBounds(10, 126, 253, 28);
		getContentPane().add(panel_6);

		lblSearch = new JLabel("Search");
		panel_6.add(lblSearch);
		lblSearch.setForeground(Color.WHITE);

		panel_5 = new JPanel();
//		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_5.setBounds(10, 126, 261, 508);
		getContentPane().add(panel_5);
		
		
/*		lblCompanyCode = new JLabel("Company Code:");
		lblCompanyCode.setBounds(291, 130, 100, 20);
		getContentPane().add(lblCompanyCode);
		
		lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setBounds(291, 160, 100, 20);
		getContentPane().add(lblCompanyName);
*/
		
		lblProductCode = new JLabel("Pack Code:");
		lblProductCode.setBounds(291, 190, 140, 20);
		getContentPane().add(lblProductCode);
		
		lblProductName = new JLabel("Pack Name:");
		lblProductName.setBounds(291, 220, 140, 20);
		getContentPane().add(lblProductName);

		lblshortname = new JLabel("Pack Name Hindi:");
		lblshortname.setBounds(291, 250, 162, 20);
		getContentPane().add(lblshortname);

		
		pcode = new TextField(10);
		pcode.setEditable(true);
		pcode.setBounds(471, 190, 120, 22);
		getContentPane().add(pcode);
		
		pname = new TextField(40);
		pname.setBounds(471, 220, 327, 22);
		getContentPane().add(pname);
		
		shortname = new TextField(45);
		shortname.setBounds(471, 250, 327, 22);
		shortname.setFont(fontHindi);
		getContentPane().add(shortname);


		/// adding listner for add edit and delete save buttons
		exitButton.addActionListener(this);
		btnSave.addActionListener(this);
		btnAdd.addActionListener(this);
		btnPrint.addActionListener(this);
		
		
		// /////////////////////////////////////////////////////////////////////////////////////////////
		KeyListener keyListener = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {
				int key = keyEvent.getKeyCode();

				if (key == KeyEvent.VK_ENTER) {
					int id=0;
					try
					{
						JTextField textField = (JTextField) keyEvent.getSource();
						id = Integer.parseInt(textField.getName());
					}
					catch(Exception e)
					{
						try {
							JComboBox textCombo = (JComboBox) keyEvent.getSource();
							id = Integer.parseInt(textCombo.getName());
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							 
						}	
					}

					switch (id) {
					case 0:
					case 1:
						break;
					case 2:
						break;
					case 3:
						pname.requestFocus();
						pname.setSelectionStart(0);
						break;
					case 4:
						shortname.requestFocus();
						shortname.setSelectionStart(0);
						break;
					case 5:
				 		btnSave.requestFocus();
				 		btnSave.setEnabled(true);
						break;
					 
					}
				}
				
				if (key == KeyEvent.VK_UP) {

					int id=0;
					try
					{
						JTextField textField = (JTextField) keyEvent.getSource();
						id = Integer.parseInt(textField.getName());
					}
					catch(Exception e)
					{
						try {
							JComboBox textCombo = (JComboBox) keyEvent.getSource();
							id = Integer.parseInt(textCombo.getName());
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							 
						}	
					}
					switch (id) {
					case 4:
						pname.requestFocus();
						pname.setSelectionStart(0);
						break;
					case 5:
						shortname.requestFocus();
						shortname.setSelectionStart(0);
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
		pcode.setName("3");
		pname.setName("4");
		shortname.setName("5");
		 

		
		
		addKeyListener(PackMaster.this,keyListener);
		
		setFocusListener(PackMaster.this);
		// / setting default focus request
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				prd_name.requestFocus();
			}

			public void windowClosed(WindowEvent e) {
				clearAll();
			}
		});

		// setAlwaysOnTop(true);


		
/*		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(279, 416, 704, 180);
		getContentPane().add(panel);
*/
// 		tax_type1.addActionListener(this);
 		
 		
 		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer() 
		{
			public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) 
			{
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				if (productTable.getSelectedRow()==row )
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
		
		productTable.getColumnModel().getColumn(0).setCellRenderer(dtc);
		productTable.getColumnModel().getColumn(1).setCellRenderer(dtc);
		
 		
 		myTableModel = productTable.getModel();
 		sorter = new TableRowSorter<TableModel>(myTableModel);
 		productTable.setRowSorter(sorter);
 		prd_name.getDocument().addDocumentListener(TableDataSorter.getTableSorter(prd_name, sorter,productTable, 1,false));
 		// ////////////////////////////////////////////////////////////////	
 		
 
 				panel_2 = new JPanel();
 				panel_2.setForeground(Color.WHITE);
 				//		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
 						panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 3));
 //						panel_2.setBackground(SystemColor.activeCaptionBorder);
 						panel_2.setBounds(8, 124, 980, 512);
 						getContentPane().add(panel_2);

 		
 
 		productTable.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					setPartyCurrentRow();
					btnSave.setEnabled(true);
					evt.consume();
				}
				
			}
			
			
			public void keyReleased(KeyEvent evt) 
			{
				if((evt.getKeyCode() == KeyEvent.VK_DOWN ) || (evt.getKeyCode() == KeyEvent.VK_UP ) || (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) || (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN))
				{
					setPartyCurrentRow();
					btnSave.setEnabled(true);
					evt.consume();
				}
				
			}
			
			
		});		
		
 		productTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent ee) 
			{
				setPartyCurrentRow();
				btnSave.setEnabled(true);
				ee.consume();
			}
		});
		


		
 		

	}


	  public void setPartyCurrentRow()
	   {
			
			int viewRow = productTable.getSelectedRow();
	       if (viewRow < 0) {
	           //Selection got filtered away.
	           //statusText.setText(""); DO Nothing
	       } else {
	           int modelRow = productTable.convertRowIndexToModel(viewRow);
	           
				GroupDto p = (GroupDto) myTableModel.getValueAt(modelRow, 2);
				clearAll();
				fillData(p);

			 	productTable.changeSelection(modelRow, 0, false, false);
				
	       }			


	   }




	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getActionCommand().equalsIgnoreCase("Add"))
		{
			clearAll();
			option="1";
			adddata=true;
			pcode.setEditable(false);
			pcode.setText(String.valueOf(lastno+1));
			pname.requestFocus();
			System.out.println("yeha per aaya ");
			
		}


		
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			clearAll(); 
			dispose();
		}

	
		if(e.getActionCommand().equalsIgnoreCase("Save"))
		{
			saveData();
		}		

		if(e.getActionCommand().equalsIgnoreCase("Print"))
		{
	
					ProductPrint invp = new ProductPrint(1,loginDt.getDepo_code(),loginDt.getDiv_code(),"",loginDt.getDrvnm(),loginDt.getPrinternm(),loginDt.getPrdmap());

					
					clearAll();
//					party_code.requestFocus();
				
		}	
	}

	public void saveData()
	{
		int i=0;
		if(option.equals("1"))
		{
			if(pname.getText().length()>0)
				i=prddao.addPack(saveProduct());
			if(i>0)
				alertMessage(PackMaster.this, "Record Added Successfully. Pack Code is "+i,"Pack Master",1);
			else
				alertMessage(PackMaster.this, "Error While Saving!!!","Pack Master",2);


		}

		
		if(option.equals("2"))
		{
			i =prddao.updatePack(saveProduct());
			if(i>0)
				alertMessage(PackMaster.this, "Record Updated Successfully. ","Pack Master",1);
			else
				alertMessage(PackMaster.this, "Error While Saving!!!","Pack Master",2);


		}
//		loginDt.setPrdlist(prddao.getPrdList(loginDt.getDiv_code(),loginDt.getDepo_code(),0));
		clearAll();
		fillInvTable();

	}

	
	public void fillData(GroupDto g)
	{
		// invoice header
		pcode.setText(String.valueOf(g.getGp_code()));
		pname.setText(g.getGp_name());
		shortname.setText(g.getGp_name_hindi());
		
		setCaretPositionTextField(PackMaster.this);
		adddata=true;
		option="2";
		
		 
	}
	
	public GroupDto saveProduct() {
		GroupDto g = new GroupDto();

		g.setGp_code(setIntNumber(pcode.getText()));
		g.setGp_name(pname.getText());
		g.setGp_name_hindi(shortname.getText());
			
		 

		return g;
	}// // GroupDto update method end///////////////	
	
	

	
	
	 
	public void fillInvTable()
	{
		productTableModel.getDataVector().removeAllElements();
		productTableModel.fireTableDataChanged();
		Vector data = (Vector) prddao.getPackDetail(loginDt.getDepo_code());
		 
		Vector c = null;
		int s = data.size();
		 
		for(int i =0;i<s;i++)
		{
			c =(Vector) data.get(i);
			productTableModel.addRow(c);
		}
           if (s==0)
           {
       		for(int i =0;i<30;i++)
    		{
       			productTableModel.addRow(new Object[2][]);
    		}
        	   
        	   
           }
		
	}


	
	public void clearAll() 
	{
		pcode.setText("");
		pname.setText("");
		shortname.setText("");
		prd_name.setText("");
		btnSave.setEnabled(false);
		adddata=false;

	}
	public void setVisible(boolean b)
	{
		fillInvTable();
		lastno=prddao.lastPackcode(loginDt.getDepo_code());
		prd_name.requestFocus();
		super.setVisible(b);
	}
	
}


