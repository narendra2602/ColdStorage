package com.coldstorage.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.AccheadDAO;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.util.OverWriteTableCellEditor;

public class AccheadMaster extends BaseClass implements ActionListener 
{
 
	private static final long serialVersionUID = 1L;
	Font font;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_12;
	private JLabel lblDivision;
	private JLabel division;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private JButton exitButton;
	private JButton btnSave,btnAdd;
	private DefaultTableModel accheadTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable accheadTable;
	private JScrollPane accheadPane;
	String option=null;
	AccheadDAO sdao=null;
	int rrow;
	boolean oneRow=false;
	public AccheadMaster()
	{

//		infoName =(String) helpImg.get(getClass().getSimpleName());
		
		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		option="";
		sdao= new AccheadDAO();
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
 
		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(314, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);
 
		
/*		label = new JLabel("ARISTO");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setForeground(new Color(220, 20, 60));
		label.setFont(new Font("Bookman Old Style", Font.BOLD, 23));
		label.setBounds(10, 48, 184, 22);
		getContentPane().add(label);
*/
		getContentPane().add(arisleb);


		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);


		
		branch.setText(loginDt.getBrnnm());
		branch.setBounds(400, 35, 395, 22);
		getContentPane().add(branch);

		lblDispatchEntry = new JLabel("Account Head Master");
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDispatchEntry.setBounds(450, 60, 251, 22);
		getContentPane().add(lblDispatchEntry);

		

		//////////////////////////////////////////////////////////////////////
		String[] crDrColName = {"Select","Group Code","Group Name","Sub Code",""};
		String cdDrData[][] = {};
		accheadTableModel = new DefaultTableModel(cdDrData, crDrColName) 
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
		
		accheadTable = new JTable(accheadTableModel);
		accheadTable.setColumnSelectionAllowed(false);
		accheadTable.setCellSelectionEnabled(false);
		accheadTable.getTableHeader().setReorderingAllowed(false);
		accheadTable.getTableHeader().setResizingAllowed(false);
		accheadTable.setRowHeight(20);
		accheadTable.getTableHeader().setPreferredSize(new Dimension(25, 25));
		accheadTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		accheadTable.getColumnModel().getColumn(0).setPreferredWidth(78); //select
		accheadTable.getColumnModel().getColumn(1).setPreferredWidth(150);//group code
		accheadTable.getColumnModel().getColumn(2).setPreferredWidth(250);//group name
		accheadTable.getColumnModel().getColumn(3).setPreferredWidth(150);//sub code
		
		accheadTable.getColumnModel().getColumn(4).setWidth(0); // hidden field
		accheadTable.getColumnModel().getColumn(4).setMinWidth(0); // hidden field
		accheadTable.getColumnModel().getColumn(4).setMaxWidth(0); // hidden field
		
		accheadTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());

		//////////////////////////////////////////////////////////////////////////
		accheadPane = new JScrollPane(accheadTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		accheadPane.setBounds(274, 93, 578, 500);
		getContentPane().add(accheadPane);
		
		fillTable();
		
		
		KeyListener accheadTableListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				int column = accheadTable.getSelectedColumn();
				int row = accheadTable.getSelectedRow();
				int totRow=accheadTable.getRowCount();
				rrow = row;
				
				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					
				   if (!option.equals("A"))	                 // put A for add and E for edit in last field
					   accheadTable.setValueAt("E", row, 4);
//				   else
//						accheadTable.setValueAt("E", row, 3);
				   
					   
					if (column == 1) 
					{
						accheadTable.changeSelection(row, 2, false, false);
						accheadTable.editCellAt(row, 2);
					}

					if (column == 2) 
					{
						accheadTable.changeSelection(row, 3, false, false);
						accheadTable.editCellAt(row, 3);
					}

					
					
					if (column == 3) 
					{
						if(row<totRow-1)
						{
							accheadTable.changeSelection(row+1, 1, false, false);
							accheadTable.editCellAt(row+1, 1);
						}
						else
						{
							accheadTable.changeSelection(row, 1, false, false);
							accheadTable.editCellAt(row, 1);
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
		
		accheadTable.addKeyListener(accheadTableListener);
		

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
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(274, 657, 578, 48);
		getContentPane().add(panel_2);
		
		exitButton.addActionListener(this);
		btnSave.addActionListener(this);
		btnAdd.addActionListener(this);
		
		/*accheadTable.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				int row = accheadTable.getSelectedRow();
				for(int x = 0, y = accheadTable.getRowCount(); x < y; x++)   
				{   
					accheadTable.setValueAt(new Boolean(false),x,0);
				}
				
				accheadTable.setValueAt(new Boolean(true),row,0);
				
				acchead_code.setText(accheadTable.getValueAt(row, 1).toString());
				acchead_name.setText(accheadTable.getValueAt(row, 2).toString());
			}
		});*/
		
		
		
		
		 
		
	}

	class MyItemListener implements ItemListener   
	  {   
	    public void itemStateChanged(ItemEvent e) {   
	      Object source = e.getSource();   
	      if (source instanceof AbstractButton == false) return;   
	      boolean checked = e.getStateChange() == ItemEvent.SELECTED;   
	      for(int x = 0, y = accheadTable.getRowCount(); x < y; x++)   
	      {   
	    	  accheadTable.setValueAt(new Boolean(checked),x,0);   
	      }   
	    }   
	  } 
	
	
 

	
	 

	public ArrayList accheadUpdate() 
	{
		  
		Vector col = null;
		GroupDto st = null;

		ArrayList accheadList = new ArrayList();
		Vector accheadData = accheadTableModel.getDataVector();
		try {
			   int s = accheadData.size();
			for (int i = 0; i < s; i++) {
				col = (Vector) accheadData.get(i);
				 
				  if(col.get(4).equals("E"))	
				  {
					st = new GroupDto();
					st.setDepo_code(loginDt.getDepo_code());
					st.setDiv_code(loginDt.getDiv_code());
					st.setGp_code(setIntNumber(col.get(1).toString()));
					st.setGp_name(col.get(2).toString());
					st.setSub_code(setIntNumber(col.get(3).toString()));
					accheadList.add(st);
				  }

				} // end of for loop 
				  
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		return accheadList;		
		
		     
	}	
	public ArrayList accheadAdd() 
	{
		  
		Vector col = null;
		GroupDto st = null;

		ArrayList accheadList = new ArrayList();
		Vector accheadData = accheadTableModel.getDataVector();
		try {
			   int s = accheadData.size();
			for (int i = 0; i < s; i++) {
				col = (Vector) accheadData.get(i);
				 
				  if(col.get(4).equals("A") && setIntNumber(col.get(1).toString())!=0)	
				  {
					st = new GroupDto();
					st.setDepo_code(loginDt.getDepo_code());
					st.setDiv_code(loginDt.getDiv_code());
					st.setGp_code(setIntNumber(col.get(1).toString()));
					st.setGp_name(col.get(2).toString());
					st.setSub_code(setIntNumber(col.get(3).toString()));
					accheadList.add(st);
				  }
				  accheadTable.setValueAt(" ", i, 4);

				} // end of for loop 
				  
		} 
		catch (Exception e) 
		{
			 
			System.out.println(e);
		}
		
		return accheadList;		
		
		     
	}
	
	
	public ArrayList getSelectedRow()
	{
		Vector col = null;
		GroupDto st = null;
		ArrayList dataList = new ArrayList();
		Vector data = accheadTableModel.getDataVector();
		int s = data.size();
		try 
		{
			for (int i = 0; i<s; i++) 
			{

				col = (Vector) data.get(i);
				if ((Boolean) col.get(0)) 
				{
					st=new GroupDto();
					st.setDepo_code(loginDt.getDepo_code());
					st.setDiv_code(loginDt.getDiv_code());
					st.setGp_code(setIntNumber(col.get(1).toString()));
					dataList.add(st);
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
		accheadTableModel.getDataVector().removeAllElements();
		Vector<?> c = null;
		 
		AccheadDAO st = new AccheadDAO();
		Vector<?> v = st.getHeadList1(loginDt.getDiv_code(),loginDt.getDepo_code());

		int s =v.size(); 
		for(int i =0;i<s;i++)
		{
			c =(Vector<?>) v.get(i);
			accheadTableModel.addRow(c);
		}

	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			 
			fillTable(); 
			dispose();
		}
		

		if(e.getActionCommand().equalsIgnoreCase("Add"))
		{
			option="A";
			int row =accheadTable.getRowCount();
			System.out.println("number of rows"+row);
			String ss =null;
			try
			{
				ss = accheadTableModel.getValueAt(row-1, 2).toString();
			}
			catch(Exception exp)
			{
				ss="";
				accheadTable.requestFocus();
				
				if(row==0)
				{
				  accheadTableModel.addRow(new Object[] {});
				}

				if(row>0)
				{
				  accheadTable.changeSelection(row, 1, false, false);
				  accheadTable.editCellAt(row, 1);
				}

				
			}
			if(!ss.equalsIgnoreCase(""))
			{
				accheadTableModel.addRow(new Object[] {});
				accheadTable.requestFocus();
				accheadTable.changeSelection(row, 1, false, false);
				accheadTable.editCellAt(row, 1);
				accheadTable.setValueAt(new Boolean(false), row, 0);
				accheadTable.setValueAt("A", row, 4);
				accheadTable.setValueAt("", row, 1);
			}
			else
			{
				accheadTable.requestFocus();
				accheadTable.changeSelection(row-1, 1, false, false);
				accheadTable.editCellAt(row-1, 1);
			}
			
		}
		
		
		if(e.getActionCommand().equalsIgnoreCase("Delete"))
		{
			option="D";
			 int h=0;
			 
			 int answer = 0;
			 
  	    	if (getSelectedRow().isEmpty())
  	    		   JOptionPane.showMessageDialog(AccheadMaster.this, "Please Select Record First!!!!");
  	    	else
  	    	{ 
  	    		answer=JOptionPane.showConfirmDialog(AccheadMaster.this, "Are You Sure : ");
			    if (answer == JOptionPane.YES_OPTION) {
			      // User clicked YES.
			    	h = sdao.deleteHead(getSelectedRow());
			    	fillTable();
			    } else if (answer == JOptionPane.NO_OPTION) {
			      // User clicked NO.
			    }
  	    	} 
			 
			 
			 System.out.println("record deleted "+h);
			 
		}	
		
		
		if(e.getActionCommand().equalsIgnoreCase("Save"))
		{
			 int h=0;
			 ArrayList l=null;
			 if (option.equals("A")){
				 l=accheadAdd();
				 if (!l.isEmpty()) 		
					 h = sdao.addHead1(l);
			 }else{
				 l=accheadUpdate();
				 if (!l.isEmpty()) 				 
					 h = sdao.updateHead1(l);
			 }
			 option="";
			 fillTable(); 
			 System.out.println("record update "+h);
			
		}				

	}
 
	
	 
}


