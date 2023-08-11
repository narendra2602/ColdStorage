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

import com.coldstorage.dao.BookDAO;
import com.coldstorage.dto.BookDto;
import com.coldstorage.util.OverWriteTableCellEditor;

public class BookMaster extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	private JLabel label_12;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private JButton exitButton;
	private JButton btnSave,btnPrint,btnAdd,btnDelete;
	private DefaultTableModel accheadTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable accheadTable;
	private JScrollPane accheadPane;
	String option=null;
	BookDAO sdao=null;
	int rrow;
	boolean oneRow=false;
	public BookMaster()
	{
		
		
		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		
		option="";
		sdao= new BookDAO();
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);



		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(314, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);

	
		getContentPane().add(arisleb);


		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);


		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(65, 657, 943, 48);
		getContentPane().add(panel_2);

		branch.setText(loginDt.getBrnnm());
		branch.setBounds(400, 35, 395, 22);
		getContentPane().add(branch);

		lblDispatchEntry = new JLabel("Book Master");
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDispatchEntry.setBounds(450, 60, 251, 22);
		getContentPane().add(lblDispatchEntry);


		//////////////////////////////////////////////////////////////////////
		String[] crDrColName = {" ","<html>Book <br/>Code</html>","Book Name","<html>Book <br/>Abvr</html>","<html>Head <br/>Code<html>","PreFix","Bank Account No","Address 1 ","Address 2","City",""};
		String cdDrData[][] = {};
		accheadTableModel = new DefaultTableModel(cdDrData, crDrColName) 
		{
			private static final long serialVersionUID = 1L;
			
			public boolean isCellEditable(int row, int column) 
			{
				if(column==1)
				{
					return false;
				}
				else
				{
					return true;
				}
					
			}
			
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
		accheadTable.getTableHeader().setPreferredSize(new Dimension(40, 40));
		accheadTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		accheadTable.getColumnModel().getColumn(0).setPreferredWidth(30); //select
		accheadTable.getColumnModel().getColumn(1).setPreferredWidth(70);//book code
		accheadTable.getColumnModel().getColumn(2).setPreferredWidth(330);//book name
		accheadTable.getColumnModel().getColumn(3).setPreferredWidth(100);//book abrv
		accheadTable.getColumnModel().getColumn(4).setPreferredWidth(100);//head code
		accheadTable.getColumnModel().getColumn(5).setPreferredWidth(60);//prefex 
		accheadTable.getColumnModel().getColumn(6).setPreferredWidth(200);//account no
		accheadTable.getColumnModel().getColumn(7).setPreferredWidth(210);//address
		accheadTable.getColumnModel().getColumn(8).setPreferredWidth(210);//address 1
		accheadTable.getColumnModel().getColumn(9).setPreferredWidth(120);//city

		
		accheadTable.getColumnModel().getColumn(10).setPreferredWidth(0);//hidden
		accheadTable.getColumnModel().getColumn(10).setMinWidth(0); // hidden
		accheadTable.getColumnModel().getColumn(10).setMaxWidth(0); // hidden
		
		
		accheadTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());
		//////////////////////////////////////////////////////////////////////////
		accheadPane = new JScrollPane(accheadTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		accheadPane.setBounds(10, 136, 998, 457);
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
					   accheadTable.setValueAt("E", row, 10);
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
						accheadTable.changeSelection(row, 4, false, false);
						accheadTable.editCellAt(row, 4);
					}	   
					if (column == 4) 
					{
						accheadTable.changeSelection(row, 5, false, false);
						accheadTable.editCellAt(row, 5);
					}	   
					if (column == 5) 
					{
						accheadTable.changeSelection(row, 6, false, false);
						accheadTable.editCellAt(row, 6);
					}
					if (column == 6) 
					{
						accheadTable.changeSelection(row, 7, false, false);
						accheadTable.editCellAt(row, 7);
					}
					if (column == 7) 
					{
						accheadTable.changeSelection(row, 8, false, false);
						accheadTable.editCellAt(row, 8);
					}
					if (column == 8) 
					{
						accheadTable.changeSelection(row, 9, false, false);
						accheadTable.editCellAt(row, 9);
					}
					if (column == 9) 
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
		btnSave.setBounds(830, 604, 86, 30);
		btnSave.setActionCommand("Save");
		btnSave.setVisible(false);
		getContentPane().add(btnSave);

		exitButton = new JButton("Exit");
		exitButton.setBounds(922, 604, 86, 30);
		exitButton.setActionCommand("exit");
		getContentPane().add(exitButton);

		
		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setBounds(64, 604, 86, 30);
		btnAdd.setVisible(false);
		getContentPane().add(btnAdd);
		
		btnDelete = new JButton("Delete");
		btnDelete.setActionCommand("Delete");
		btnDelete.setBounds(155, 604, 86, 30);
		btnDelete.setVisible(false);
		getContentPane().add(btnDelete);
		
		btnPrint = new JButton("Print");
		btnPrint.setActionCommand("Print");
		btnPrint.setBounds(246, 604, 86, 30);
		btnPrint.setVisible(false);
		getContentPane().add(btnPrint);
		
		exitButton.addActionListener(this);
		btnSave.addActionListener(this);
		btnDelete.addActionListener(this);
		btnPrint.addActionListener(this);
		btnAdd.addActionListener(this);
		btnDelete.setVisible(false);
		btnPrint.setVisible(false);
		
		
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
		BookDto st = null;

		ArrayList accheadList = new ArrayList();
		Vector accheadData = accheadTableModel.getDataVector();
		try 
		{
			int s = accheadData.size();
			for (int i = 0; i < s; i++) 
			{
				col = (Vector) accheadData.get(i);
				if(col.get(10).equals("E"))	
				{
					st = new BookDto();
					st.setDepo_code(loginDt.getDepo_code());
					st.setDiv_code(loginDt.getDiv_code());
					st.setBook_code(setIntNumber(col.get(1).toString()));
					st.setBook_name(col.get(2).toString());
					st.setBook_abvr(col.get(3).toString());
					st.setHead_code(setIntNumber(col.get(4).toString()));
					st.setBook_tp(col.get(5).toString());
					st.setBank_acno(col.get(6).toString());
					st.setBank_add(col.get(7).toString());
					st.setBank_add1(col.get(8).toString());
					st.setBank_city(col.get(9).toString());

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
		BookDto st = null;

		ArrayList accheadList = new ArrayList();
		Vector accheadData = accheadTableModel.getDataVector();
		try {
			   int s = accheadData.size();
			for (int i = 0; i < s; i++) {
				col = (Vector) accheadData.get(i);
				 
				  if(col.get(6).equals("A") && setIntNumber(col.get(1).toString())!=0)	
				  {
					st = new BookDto();
					st.setDepo_code(loginDt.getDepo_code());
					st.setDiv_code(loginDt.getDiv_code());
					st.setBook_code(setIntNumber(col.get(1).toString()));
					st.setBook_name(col.get(2).toString());
					st.setBook_abvr(col.get(3).toString());
					st.setHead_code(setIntNumber(col.get(4).toString()));
					st.setBook_tp(col.get(5).toString());
                
					
					accheadList.add(st);
				  }
				  accheadTable.setValueAt(" ", i, 6);

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
		BookDto st = null;
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
					st=new BookDto();
					st.setDepo_code(loginDt.getDepo_code());
					st.setDiv_code(loginDt.getDiv_code());
					st.setBook_code(setIntNumber(col.get(1).toString()));
					st.setBook_abvr(col.get(3).toString());
					st.setHead_code(setIntNumber(col.get(4).toString()));
					st.setBook_tp(col.get(5).toString());

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
		 
		BookDAO st = new BookDAO();
		Vector<?> v = st.getBookList(loginDt.getDiv_code(),loginDt.getDepo_code());

		System.out.println(loginDt.getStateList().size());
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
			String ss =null;
			try
			{
				ss = accheadTableModel.getValueAt(row-1, 2).toString();
			}
			catch(Exception exp)
			{
				ss="";
				accheadTable.requestFocus();
				accheadTable.changeSelection(row, 1, false, false);
				accheadTable.editCellAt(row, 1);
			}
			if(!ss.equalsIgnoreCase(""))
			{
				accheadTableModel.addRow(new Object[] {});
				accheadTable.requestFocus();
				accheadTable.changeSelection(row, 1, false, false);
				accheadTable.editCellAt(row, 1);
				accheadTable.setValueAt(new Boolean(false), row, 0);
				accheadTable.setValueAt("A", row, 6);
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
  	    		   JOptionPane.showMessageDialog(BookMaster.this, "Please Select Record First!!!!");
  	    	else
  	    	{ 
  	    		answer=JOptionPane.showConfirmDialog(BookMaster.this, "Are You Sure : ");
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
			
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to save the changes?", "Save Changes",dialogButton);
			if(dialogResult==0)
			{
				int h=0;
				ArrayList l=null;
				if (option.equals("A"))
				{
					l=accheadAdd();
					if (!l.isEmpty()) 
					{
					//	h = sdao.addHead(l);
					}
				}
				else
				{
					System.out.println("else condition");
					l=accheadUpdate();
					
					if (!l.isEmpty()) 	
					{			 
						//h = sdao.updateHead(l);
					}
				}
				option="";
				fillTable(); 
				System.out.println("record update "+h);
				
				
				System.out.println("Yes Option");
			}
			else
			{
				System.out.println("No Option");
			}
			
			
			/**/
			
		}				

	}
	
	 
	 
}


