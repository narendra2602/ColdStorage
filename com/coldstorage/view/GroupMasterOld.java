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
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
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

import com.coldstorage.dao.GroupDAO;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.util.TableDataSorter;

public class GroupMasterOld extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	Font font,fontHindi;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_12;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private JButton exitButton;
	private JButton btnSave,btnPrint,btnAdd,btnDelete;
	private DefaultTableModel groupTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable groupTable;
	private JScrollPane groupPane;
	String option=null;
	GroupDAO gdao=null;
	int rrow,type;
	boolean oneRow=false;
	private JTextField search;
	private String repname,filename;
	public GroupMasterOld(String classname,String repname)
	{

		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		this.repname=repname;
		option="";
		gdao= new GroupDAO();
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

		fontHindi = new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14);

/*		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(440, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);
*/
		getContentPane().add(arisleb);


		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);

/*		branch.setText(loginDt.getBrnnm());
		branch.setBounds(300, 35, 595, 22);
		getContentPane().add(branch);
*/
		lblDispatchEntry = new JLabel(repname);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDispatchEntry.setBounds(450, 90, 251, 22);
		getContentPane().add(lblDispatchEntry);

/*
		panel_2 = new JPanel();
//		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(274, 657, 578, 48);
		getContentPane().add(panel_2);
*/

		//////////////////////////////////////////////////////////////////////
		String[] crDrColName = {"Select","Code","Group Name","Group Name Hindi",""};
		String cdDrData[][] = {};
		groupTableModel = new DefaultTableModel(cdDrData, crDrColName) 
		{
			private static final long serialVersionUID = 1L;
			
			public Class<?> getColumnClass(int column) 
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
		
		groupTable = new JTable(groupTableModel);
		groupTable.setColumnSelectionAllowed(false);
		groupTable.setCellSelectionEnabled(false);
		groupTable.getTableHeader().setReorderingAllowed(false);
		groupTable.getTableHeader().setResizingAllowed(false);
		groupTable.setRowHeight(20);
		groupTable.getTableHeader().setPreferredSize(new Dimension(25, 25));
//		groupTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		groupTable.setFont(new Font("c://windows//fonts//ARIALUNI.ttf",Font.BOLD,14));
		
		groupTable.getColumnModel().getColumn(0).setPreferredWidth(78);
		groupTable.getColumnModel().getColumn(1).setPreferredWidth(78);
		groupTable.getColumnModel().getColumn(2).setPreferredWidth(225);
		groupTable.getColumnModel().getColumn(3).setPreferredWidth(225);
		
		groupTable.getColumnModel().getColumn(4).setWidth(0); // hidden field
		groupTable.getColumnModel().getColumn(4).setMinWidth(0); // no
		groupTable.getColumnModel().getColumn(4).setMaxWidth(0); // no
		
		//////////////////////////////////////////////////////////////////////////
		groupPane = new JScrollPane(groupTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		groupPane.setBounds(274, 160, 578, 441);
		groupPane.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		getContentPane().add(groupPane);
		

		
		
		KeyListener groupTableListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				int column = groupTable.getSelectedColumn();
				int row = groupTable.getSelectedRow();
				int totRow=groupTable.getRowCount();
				rrow = row;
				
				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					
				   if (!option.equals("A"))	                 // put A for add and E for edit in last field
					   groupTable.setValueAt("E", row, 4);
//				   else
//						groupTable.setValueAt("E", row, 4);
				   
				        System.out.println(" COLOR 4 KI VALUE "+groupTable.getValueAt(row, 4)+" option "+option);
					   
					if (column == 1) 
					{
						groupTable.changeSelection(row, 2, false, false);
						groupTable.editCellAt(row, 2);
					}
					if (column == 2) 
					{
						groupTable.changeSelection(row, 3, false, false);
						groupTable.editCellAt(row, 3);
					}


					if (column == 3) 
					{
						if(row<totRow-1)
						{
							groupTable.changeSelection(row+1, 1, false, false);
							groupTable.editCellAt(row+1, 1);
						}
						else
						{
							groupTable.changeSelection(row, 1, false, false);
							groupTable.editCellAt(row, 1);
						}
					}
					
					evt.consume();
				}

				/*if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					oedPane.setVisible(false);
					btnSave.requestFocus();
				}*/

			}// /// keypressed

		};
		
		groupTable.addKeyListener(groupTableListener);
		

		btnSave= new JButton("Save");
		btnSave.setBounds(674, 604, 86, 30);
		btnSave.setActionCommand("Save");
		getContentPane().add(btnSave);

		exitButton = new JButton("Exit");
		exitButton.setBounds(766, 604, 86, 30);
		exitButton.setActionCommand("exit");
		getContentPane().add(exitButton);

		
		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setBounds(274, 604, 86, 30);
		getContentPane().add(btnAdd);
		
		btnDelete = new JButton("Delete");
		btnDelete.setActionCommand("Delete");
		btnDelete.setBounds(456, 604, 86, 30);
		btnDelete.setVisible(false);
		getContentPane().add(btnDelete);
		
		btnPrint = new JButton("Excel");
		btnPrint.setVisible(false);
		btnPrint.setActionCommand("Print");
		btnPrint.setBounds(365, 604, 86, 30);
		getContentPane().add(btnPrint);
		
		JLabel lblSearchByGroup = new JLabel("Search By Name:");
		lblSearchByGroup.setForeground(new Color(0, 51, 255));
		lblSearchByGroup.setBounds(281, 129, 140, 14);
		getContentPane().add(lblSearchByGroup);
		
		search = new JTextField();
		search.setBounds(420, 124, 207, 23);
		getContentPane().add(search);
		
		TableModel myTableModel = groupTable.getModel();
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(myTableModel);
        groupTable.setRowSorter(sorter);
        
        search.getDocument().addDocumentListener(TableDataSorter.getTableSorter(search, sorter,groupTable, 2,true));
		
		
		
		JPanel panel = new JPanel();
//		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBounds(274, 118, 578, 42);
		getContentPane().add(panel);
		
		exitButton.addActionListener(this);
		btnSave.addActionListener(this);
		btnDelete.addActionListener(this);
		btnPrint.addActionListener(this);
		btnAdd.addActionListener(this);
		
 
		
		//TODO MOUSE LISTNER
		groupTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e)
			{
				
				int row = groupTable.getSelectedRow();
				
				   if (!option.equals("A"))	                 // put A for add and E for edit in last field
					   groupTable.setValueAt("E", row, 4);
				   groupTableModel.fireTableCellUpdated(row, 4);
				   
				 
			}
			public void mousePressed(MouseEvent e) 
			{
				int row = groupTable.getSelectedRow();
				 
				   if (!option.equals("A"))	                 // put A for add and E for edit in last field
					   groupTable.setValueAt("E", row, 4);

			}

			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent e) 
			{
				int row = groupTable.getSelectedRow();
				 
				   if (!option.equals("A"))	                 // put A for add and E for edit in last field
					   groupTable.setValueAt("E", row, 4);
			}
		});			
		 
		 
		
	}

	
	 

	public ArrayList groupUpdate() 
	{
		  
		Vector col = null;
		GroupDto gp = null;
		
		

		ArrayList GroupList = new ArrayList();
		Vector gpData = groupTableModel.getDataVector();
		try {
			   int s = gpData.size();
			    
			for (int i = 0; i < s; i++) {
				col = (Vector) gpData.get(i);
				 
				  if(col.get(4).equals("E"))	
				  {
					   
					  gp = new GroupDto();
					  gp.setDiv_code(loginDt.getDiv_code());
					  gp.setGp_code(setIntNumber(col.get(1).toString()));
					  gp.setGp_name(col.get(2).toString());
					  gp.setGp_name_hindi(col.get(3).toString());
					  GroupList.add(gp);
				  }

				} // end of for loop 
				  
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		return GroupList;		
		
		     
	}	
	public ArrayList groupAdd() 
	{
		  
		Vector col = null;
		GroupDto gp = null;
		 
		ArrayList GroupList = new ArrayList();
		Vector gpData = groupTableModel.getDataVector();
		try {
			   int s = gpData.size();
			for (int i = 0; i < s; i++) {
				col = (Vector) gpData.get(i);
				 
				  if(col.get(4).equals("A") && setIntNumber(col.get(1).toString())!=0)	
				  {
					   
					  gp = new GroupDto();
					  gp.setDepo_code(loginDt.getDepo_code());
					  gp.setDiv_code(loginDt.getDiv_code());
					  gp.setGp_code(setIntNumber(col.get(1).toString()));
					  gp.setGp_name(col.get(2).toString());
					  gp.setGp_name_hindi(col.get(3).toString());
					  GroupList.add(gp);					  
					 
				  }
				  groupTable.setValueAt(" ", i, 4);

				} // end of for loop 
				  
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		return GroupList;		
		
		     
	}
	
	
	public ArrayList getSelectedRow()
	{
		Vector col = null;
		GroupDto gp = null;
		ArrayList dataList = new ArrayList();
		Vector data = groupTableModel.getDataVector();
		int s = data.size();
		try 
		{
			for (int i = 0; i<s; i++) 
			{

				col = (Vector) data.get(i);
				if ((Boolean) col.get(0)) 
				{
					 
				  gp = new GroupDto();
				  gp.setDiv_code(loginDt.getDiv_code());
				  gp.setGp_code(setIntNumber(col.get(1).toString()));
				  dataList.add(gp);				                    
					
				}
				 
			}
			return dataList;
			

		}

		catch(Exception e)
		{
			System.out.println(e);
		}
		 return null;
		
	}		
	
	public void fillTable()
	{
		groupTableModel.getDataVector().removeAllElements();
		Vector<?> c = null;
		 
		Vector<?> v = gdao.getGroupList();

		int s =v.size(); 
		for(int i =0;i<s;i++)
		{
			c =(Vector<?>) v.get(i);
			groupTableModel.addRow(c);
		}

	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			search.setText("");
			fillTable(); 
			dispose();
		}
		

		if(e.getActionCommand().equalsIgnoreCase("Add"))
		{
			option="A";
			int row =groupTable.getRowCount(); 
			String ss =null;
			try
			{
				ss = groupTableModel.getValueAt(row-1, 2).toString();
			}
			catch(Exception exp)
			{
				ss="";
				groupTable.requestFocus();
				groupTable.changeSelection(row, 1, false, false);
				groupTable.editCellAt(row, 1);
			}
			if(!ss.equalsIgnoreCase(""))
			{
				groupTableModel.addRow(new Object[] {});
				groupTable.requestFocus();
				groupTable.changeSelection(row, 1, false, false);
				groupTable.editCellAt(row, 1);
				groupTable.setValueAt(new Boolean(false), row, 0);
				groupTable.setValueAt("A", row, 4);
				groupTable.setValueAt("", row, 1);
			}
			else
			{
				groupTable.requestFocus();
				groupTable.changeSelection(row-1, 1, false, false);
				groupTable.editCellAt(row-1, 1);
			}
			
		}
		
		
		if(e.getActionCommand().equalsIgnoreCase("Delete"))
		{
			option="D";
			 int h=0;
			 
			 int answer = 0;
			 
  	    	if (getSelectedRow().isEmpty())
  	    		   JOptionPane.showMessageDialog(GroupMasterOld.this, "Please Select Record First!!!!");
  	    	else
  	    	{ 
  	    		answer=JOptionPane.showConfirmDialog(GroupMasterOld.this, "Are You Sure : ");
			    if (answer == JOptionPane.YES_OPTION) {
			      // User clicked YES.
			    	h = gdao.deleteGroup(getSelectedRow());
			    	fillTable();
			    } else if (answer == JOptionPane.NO_OPTION) {
			      // User clicked NO.
			    }
  	    	} 
			 
			 
			 System.out.println("record deleted "+h);
			 
		}	
		
		
		if(e.getActionCommand().equalsIgnoreCase("Save"))
		{
			 int row=groupTable.getSelectedRow(); 
			 groupTable.changeSelection(row, 4, false, false);
			 groupTable.editCellAt(row,4);
			 
			 int h=0;
			 ArrayList l=null;
			 if (option.equals("A")){
				   l=groupAdd();
				 if (!l.isEmpty()) 		
					 h = gdao.addGroup(l,type);
			 }else{
				   l=groupUpdate();
				 if (!l.isEmpty()) 				 
					 h = gdao.updateGroup(l,type);
			 }
			 
			 option="";
			 fillTable(); 
			 System.out.println("record update "+h);
			
		}				

	}
	
	public void setVisible(boolean b)
	{
		
		if(repname.contains("Product Group Master"))
		{
			type=1;
		}
		else if(repname.contains("Product Company Master"))
		{
			type=2;
		}

		
		fillTable();
		
		super.setVisible(b);
	}

	
	 
}


