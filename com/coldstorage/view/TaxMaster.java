package com.coldstorage.view;

import java.awt.Color;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.TaxDAO;
import com.coldstorage.dto.AreaDto;
import com.coldstorage.dto.TaxDto;
import com.coldstorage.util.JDoubleField;
import com.coldstorage.util.NumberTableCellEditor;
import com.coldstorage.util.OverWriteTableCellEditor;

public class TaxMaster extends BaseClass implements ActionListener 
{


	private static final long serialVersionUID = 1L;
	
	Font font;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_12;
	private JLabel division;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private JButton exitButton;
	private JButton btnSave,btnAdd;
	private DefaultTableModel taxTableModel,gstTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable taxTable,gstTable;
	private JScrollPane  statePane,areaPane,gstPane ;
	private JList stateList ;
	boolean oneRow=false;
	int rrow;
	String option=null;
	TaxDAO taxdao=null;
	private SimpleDateFormat sdf; 
	private JFormattedTextField f1 ;
	private JFormattedTextField f2 ;
	private JLabel lblNCst;
	private JFormattedTextField table_pcode;
	private JDoubleField dobl;

	private JRadioButton vat,gst;

	private JLabel lblAFood,lblYCst,lblBMedicine,lblETax,lblBTax,lblCTax,lblTaxType;

	public TaxMaster()
	{
		
		
		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
		
		option="";
		taxdao = new TaxDAO();
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
		panel_2.setBounds(210, 657, 734, 48);
		getContentPane().add(panel_2);

		
		branch.setText(loginDt.getBrnnm());
		branch.setBounds(400, 35, 395, 22);
		getContentPane().add(branch);

		lblDispatchEntry = new JLabel("GST Master");
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDispatchEntry.setBounds(450, 60, 251, 22);
		getContentPane().add(lblDispatchEntry);

		
		
		vat = new JRadioButton("VAT");
		vat.setVisible(false);
		vat.setActionCommand("VAT");
		vat.setBounds(873, 90, 66, 23);
		getContentPane().add(vat);
		
		gst = new JRadioButton("GST");
		gst.setVisible(false);
		gst.setSelected(true);
		gst.setActionCommand("GST");
		gst.setBounds(942, 90, 66, 23);
		getContentPane().add(gst);

		ButtonGroup group1 = new ButtonGroup();
	    group1.add(vat);
		group1.add(gst);

		
		
		f1 = new JFormattedTextField();
		checkDate(f1);
		f1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					
					String date=f1.getText();
					 
					 
					if(isValidDate(date))
					{
						 
					   System.out.println("Date format is correct");
							
					}
					else
					{
						 
						f1.setValue(null);
						checkDate(f1);
						f1.requestFocus();
						f1.selectAll();
						f1.setSelectionStart(0);
					}
 
			}
		  }
		});
		
		
		f2 = new JFormattedTextField();
		checkDate(f2);
		f2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					
					String date=f2.getText();
					if(isValidDate(date))
					{
					   System.out.println("Date format is correct");
					}
					else
					{
						 
						f2.setValue(null);
						checkDate(f2);
						f2.requestFocus();
						f2.selectAll();
						f2.setSelectionStart(0);
					}
 
			}
		  }
		});
		
		// state list ////////////////////////////////////////////////////////
		stateList = new JList(loginDt.getStateList());
		stateList.setLocation(10, 11);
		stateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stateList.setSelectedIndex(0);
		getContentPane().add(stateList);
		statePane = new JScrollPane(stateList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		statePane.setBounds(165, 161, 190, 200);
		getContentPane().add(statePane);
		statePane.setVisible(false);
		stateList.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{

					statePane.setVisible(false);
					stateList.setVisible(false);
					 
					int idx = stateList.getSelectedIndex();
					// same Areadto is used for StateDto /////// 
					AreaDto ar = (AreaDto) loginDt.getStateList().get(idx);
					if(vat.isSelected())
					{
						taxTable.requestFocus();
						taxTableModel.setValueAt(ar.getArea_cd(), rrow, 0);
						taxTableModel.setValueAt(ar.getArea_name(), rrow, 1);
						taxTable.changeSelection(rrow, 2, false, false);
						taxTable.editCellAt(rrow, 2);
					}
					else
					{
						gstTable.requestFocus();
						gstTableModel.setValueAt(ar.getArea_cd(), rrow, 0);
						gstTableModel.setValueAt(ar.getArea_name(), rrow, 1);
						gstTable.changeSelection(rrow, 2, false, false);
						gstTable.editCellAt(rrow, 2);
						
					}
					 
				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					statePane.setVisible(false);
					if(vat.isSelected())
					{
						taxTable.requestFocus();
						taxTable.changeSelection(rrow, 3, false, false);
						taxTable.editCellAt(rrow, 3);
					}
					else
					{
						gstTable.requestFocus();
						gstTable.changeSelection(rrow, 3, false, false);
						gstTable.editCellAt(rrow, 3);
						
					}
					evt.consume();
				}
			}
		});

		// ///////////////////////GST //////////////////////////////////

		Object[] gstName = {"State","State Name","<html>GST<br/>Type</html>","Description","<html>SGST<br/>/UTGST %</html>","<html>CGST<br/> %</html>","<html>IGST<br/> %</html>","<html>CESS<br/> %</html>","GST On","<html>GST on<br/>DiscQty Y/N</html>","<html>Discount <br/>    Y/N</html>","<html>Add <br/>GST 1</html>","<html>Add <br/>GST 2</html>","<html>Exchange <br/> Rate</html>","From","To","",""};
		String gstData[][] = {};
		gstTableModel = new DefaultTableModel(gstData, gstName) 
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				boolean ress = true;
				int col=gstTable.getSelectedColumn();
				if (col==1) 
				{
					ress = false;
				}
				return ress;
			}
			
			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
				default:
					return String.class;
				}
			}
		};
		gstTable = new JTable(gstTableModel);
		
		// /// calling method for design item table/////////////
		gstTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		itemTableUIGST();
		
		gstPane = new JScrollPane(gstTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		gstPane.setBounds(10, 136, 998, 349);
		getContentPane().add(gstPane);
		

		// /////////////////////////GST////////////////////////////////

		
		// /////////////////////////VAT////////////////////////////////

		
		Object[] taxName = {"State","State Name","<html>Tax<br/>Type</html>","Description","<html>Tax<br/> %</html>","Tax On","<html>Tax on<br/>Free Y/N</html>","<html>Discount <br/>    Y/N</html>","<html>Add <br/>Tax 1</html>","Desc 1","<html>Add <br/>Tax 2</html>","Desc 2","<html>Cst <br/> Reimburse<br/>   Y/N</html>","<html>Exchange <br/> Rate</html>","From","To","",""};
		String taxData[][] = {};
		taxTableModel = new DefaultTableModel(taxData, taxName) 
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				boolean ress = true;
				int col=taxTable.getSelectedColumn();
				if (col==1) 
				{
					ress = false;
				}
				return ress;
			}
			
			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
				/*case 11: 
					return Date.class;
				case 12: 
					return Date.class;*/
				default:
					return String.class;
				}
			}
		};
		
		taxTable = new JTable(taxTableModel);
		
		// /// calling method for design item table/////////////
		taxTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		itemTableUI();

		// /////////////////////////////////////////////////////////

				
		//////////////////////////////////////////////////////////////////////////
		areaPane = new JScrollPane(taxTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		areaPane.setBounds(10, 136, 998, 349);
		getContentPane().add(areaPane);
		areaPane.setVisible(false);
		
		fillGSTTable();
				
		KeyListener taxTableListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				int column = taxTable.getSelectedColumn();
				int row = taxTable.getSelectedRow();
				int totRow=taxTable.getRowCount();
				rrow = row;
				if (!option.equals("A"))	                 // put A for add and E for edit in last field
					   taxTable.setValueAt("E", row, 17);
				
				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					
				   
//				   else
//						taxTable.setValueAt("E", row, 5);
				   
				   if (column == 0) 
					{
						int val=0;
						try
						{
							taxTable.changeSelection(row, 1, false, false);
							taxTable.editCellAt(row, 1);
							val = setIntNumber(taxTable.getValueAt(row, 0).toString());
							
						}catch(Exception e)
						{
						     val=0;
								taxTable.changeSelection(row, 0, false, false);
								taxTable.editCellAt(row, 0);
						}
                       			
					   if (val==0)
					   {
							statePane.setVisible(true);
							stateList.setVisible(true);
							statePane.requestFocus();
							stateList.requestFocus();
							stateList.setSelectedIndex(0);
					   }
					   else
					   {
						taxTable.changeSelection(row, 1, false, false);
						taxTable.editCellAt(row, 1);
					   }
						
					}   
					if (column == 1) 
					{
						taxTable.changeSelection(row, 2, false, false);
						taxTable.editCellAt(row, 2);
					}
					if (column == 2) 
					{
						taxTable.changeSelection(row, 3, false, false);
						taxTable.editCellAt(row, 3);
					}
					if (column == 3) 
					{
						taxTable.changeSelection(row, 4, false, false);
						taxTable.editCellAt(row, 4);
					}
					
					if (column == 4) 
					{
						taxTable.changeSelection(row, 5, false, false);
						taxTable.editCellAt(row, 5);
					}
					if (column == 5) 
					{
						taxTable.changeSelection(row, 6, false, false);
						taxTable.editCellAt(row, 6);
					}
					if (column == 6) 
					{
						taxTable.changeSelection(row, 7, false, false);
						taxTable.editCellAt(row, 7);
					}
					if (column == 7) 
					{
						taxTable.changeSelection(row, 8, false, false);
						taxTable.editCellAt(row, 8);
					}
					if (column == 8) 
					{
						taxTable.changeSelection(row, 9, false, false);
						taxTable.editCellAt(row, 9);
					}
					if (column == 9) 
					{
						taxTable.changeSelection(row, 10, false, false);
						taxTable.editCellAt(row, 10);
					}
					if (column == 10) 
					{
						taxTable.changeSelection(row, 11, false, false);
						taxTable.editCellAt(row, 11);
					}

					if (column == 11) 
					{
						taxTable.changeSelection(row, 12, false, false);
						taxTable.editCellAt(row, 12);
					}
					if (column == 12) 
					{
						taxTable.changeSelection(row, 13, false, false);
						taxTable.editCellAt(row, 13);
					}
					if (column == 13) 
					{
						taxTable.changeSelection(row, 14, false, false);
						taxTable.editCellAt(row, 14);
					}
					if (column == 14) 
					{
						taxTable.changeSelection(row, 15, false, false);
						taxTable.editCellAt(row, 15);
					  
						String date=null;
						try
						{
							date=taxTable.getValueAt(row, 14).toString();
							f1.setValue(null);
							checkDate(f1);
						}catch(Exception e)
						{
							System.out.println("date ki value null hai....");
						}
						System.out.println("date 13 is "+date);
						if(isValidDate(date))
						{
							taxTable.changeSelection(row, 15, false, false);
							taxTable.editCellAt(row, 15);

						}
						else
						{
							taxTable.setValueAt(f1,row, 14);
							taxTable.changeSelection(row, 14, false, false);
							taxTable.editCellAt(row, 14);
						}						
						
					}
					if (column == 15) 
					{
						taxTable.changeSelection(row, 16, false, false);
						taxTable.editCellAt(row, 16);
						String date=taxTable.getValueAt(row, 15).toString();
						f2.setValue(null);
						checkDate(f2);
						if(isValidDate(date))
						{
							if(row<totRow-1)
							{
								taxTable.changeSelection(row+1, 0, false, false);
								taxTable.editCellAt(row+1, 0);
								
							}
							else
							{
								
								taxTable.changeSelection(row, 0, false, false);
								taxTable.editCellAt(row, 0);
							}


						}
						else
						{
							taxTable.setValueAt(f2,row, 15);
							taxTable.changeSelection(row, 15, false, false);
							taxTable.editCellAt(row, 15);
						}						
						
						 
					}
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					btnSave.requestFocus();
					btnSave.setBackground(Color.blue);
					taxTable.clearSelection();
					evt.consume();
				}

			}// /// keypressed
			 
		
		};
		
		taxTable.addKeyListener(taxTableListener);
		
		

		
		KeyListener gstTableListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				int column = gstTable.getSelectedColumn();
				int row = gstTable.getSelectedRow();
				int totRow=gstTable.getRowCount();
				rrow = row;
				if (!option.equals("A"))	                 // put A for add and E for edit in last field
					   gstTable.setValueAt("E", row, 17);
				
				
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					
				   
//				   else
//						gstTable.setValueAt("E", row, 5);
				   
				   if (column == 0) 
					{
						int val=0;
						try
						{
							gstTable.changeSelection(row, 1, false, false);
							gstTable.editCellAt(row, 1);
							val = setIntNumber(gstTable.getValueAt(row, 0).toString());
							
						}catch(Exception e)
						{
						     val=0;
								gstTable.changeSelection(row, 0, false, false);
								gstTable.editCellAt(row, 0);
						}
                       			
					   if (val==0)
					   {
							statePane.setVisible(true);
							stateList.setVisible(true);
							statePane.requestFocus();
							stateList.requestFocus();
							stateList.setSelectedIndex(0);
					   }
					   else
					   {
						gstTable.changeSelection(row, 1, false, false);
						gstTable.editCellAt(row, 1);
					   }
						
					}   
					if (column == 1) 
					{
						gstTable.changeSelection(row, 2, false, false);
						gstTable.editCellAt(row, 2);
					}
					if (column == 2) 
					{
						gstTable.changeSelection(row, 3, false, false);
						gstTable.editCellAt(row, 3);
					}
					if (column == 3) 
					{
						gstTable.changeSelection(row, 4, false, false);
						gstTable.editCellAt(row, 4);
					}
					
					if (column == 4) 
					{
						gstTable.changeSelection(row, 5, false, false);
						gstTable.editCellAt(row, 5);
					}
					if (column == 5) 
					{
						gstTable.changeSelection(row, 6, false, false);
						gstTable.editCellAt(row, 6);
					}
					if (column == 6) 
					{
						gstTable.changeSelection(row, 7, false, false);
						gstTable.editCellAt(row, 7);
					}
					if (column == 7) 
					{
						gstTable.changeSelection(row, 8, false, false);
						gstTable.editCellAt(row, 8);
					}
					if (column == 8) 
					{
						gstTable.changeSelection(row, 9, false, false);
						gstTable.editCellAt(row, 9);
					}
					if (column == 9) 
					{
						gstTable.changeSelection(row, 10, false, false);
						gstTable.editCellAt(row, 10);
					}
					if (column == 10) 
					{
						gstTable.changeSelection(row, 11, false, false);
						gstTable.editCellAt(row, 11);
					}

					if (column == 11) 
					{
						gstTable.changeSelection(row, 12, false, false);
						gstTable.editCellAt(row, 12);
					}
					if (column == 12) 
					{
						gstTable.changeSelection(row, 13, false, false);
						gstTable.editCellAt(row, 13);
					}
					if (column == 13) 
					{
						gstTable.changeSelection(row, 14, false, false);
						gstTable.editCellAt(row, 14);
					}
					if (column == 14) 
					{
						gstTable.changeSelection(row, 15, false, false);
						gstTable.editCellAt(row, 15);
					  
						String date=null;
						try
						{
							date=gstTable.getValueAt(row, 14).toString();
							f1.setValue(null);
							checkDate(f1);
						}catch(Exception e)
						{
							System.out.println("date ki value null hai....");
						}
						System.out.println("date 13 is "+date);
						if(isValidDate(date))
						{
							gstTable.changeSelection(row, 15, false, false);
							gstTable.editCellAt(row, 15);

						}
						else
						{
							gstTable.setValueAt(f1,row, 14);
							gstTable.changeSelection(row, 14, false, false);
							gstTable.editCellAt(row, 14);
						}						
						
					}
					if (column == 15) 
					{
						gstTable.changeSelection(row, 16, false, false);
						gstTable.editCellAt(row, 16);
						String date=gstTable.getValueAt(row, 15).toString();
						f2.setValue(null);
						checkDate(f2);
						if(isValidDate(date))
						{
							if(row<totRow-1)
							{
								gstTable.changeSelection(row+1, 0, false, false);
								gstTable.editCellAt(row+1, 0);
								
							}
							else
							{
								
								gstTable.changeSelection(row, 0, false, false);
								gstTable.editCellAt(row, 0);
							}


						}
						else
						{
							gstTable.setValueAt(f2,row, 15);
							gstTable.changeSelection(row, 15, false, false);
							gstTable.editCellAt(row, 15);
						}						
						
						 
					}
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					btnSave.requestFocus();
					btnSave.setBackground(Color.blue);
					gstTable.clearSelection();
					evt.consume();
				}

			}// /// keypressed
			 
		
		};
		
		gstTable.addKeyListener(gstTableListener);

		
		
		
		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setBounds(737, 604, 86, 30);
		getContentPane().add(btnAdd);
		
		btnSave= new JButton("Save");
		btnSave.setActionCommand("Save");
		btnSave.setBounds(830, 604, 86, 30);
		getContentPane().add(btnSave);
		btnSave.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(vat.isSelected())
						callSave();
					else
						callSave1();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					exitButton.requestFocus();
					exitButton.setBackground(Color.blue);
					btnSave.setBackground(null);
					evt.consume();
				}
				
			}
		});
		
		exitButton = new JButton("Exit");
		exitButton.setActionCommand("Exit");
		exitButton.setBounds(922, 604, 86, 30);
		getContentPane().add(exitButton);
		exitButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					fillTable(); 
					 if (option.equals("A"))
					 {
						 taxTableModel.removeRow(rrow);
						 taxTableModel.fireTableRowsDeleted(rrow, rrow);
						 option="";
						 

					 }
					 
					dispose();
					taxTable.requestFocus();
					 taxTable.changeSelection(0, 0, false, false);
					 taxTable.editCellAt(0, 0);
					
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					btnSave.requestFocus();
					btnSave.setBackground(Color.blue);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		
		lblTaxType = new JLabel("GST Type:");
		lblTaxType.setBounds(15, 492, 190, 20);
		getContentPane().add(lblTaxType);
	

	
		
		lblAFood = new JLabel("A = 5% GST");
		lblAFood.setForeground(new Color(0, 51, 255));
		lblAFood.setBounds(15, 514, 190, 20);
		getContentPane().add(lblAFood);
		
		
		lblBMedicine = new JLabel("D = 12% GST");
		lblBMedicine.setForeground(new Color(0, 51, 255));
		lblBMedicine.setBounds(15, 537, 190, 20);
		getContentPane().add(lblBMedicine);
		
		lblETax = new JLabel("E = GST Exempted");
		lblETax.setForeground(new Color(0, 51, 255));
		lblETax.setBounds(15, 607, 190, 20);
		getContentPane().add(lblETax);

		
		lblCTax = new JLabel("");
		lblCTax.setForeground(new Color(0, 51, 255));
		lblCTax.setBounds(15, 627, 190, 20);
		getContentPane().add(lblCTax);

		lblBTax = new JLabel("");
		lblBTax.setForeground(new Color(0, 51, 255));
		lblBTax.setBounds(15, 646, 190, 20);
		getContentPane().add(lblBTax);

		lblYCst = new JLabel("B = 18% GST");
		lblYCst.setForeground(new Color(0, 51, 255));
		lblYCst.setBounds(15, 560, 190, 20);
		getContentPane().add(lblYCst);

		
		lblNCst = new JLabel("C = 28% GST");
		lblNCst.setForeground(new Color(0, 51, 255));
		lblNCst.setBounds(15, 584, 190, 20);
		getContentPane().add(lblNCst);

		exitButton.addActionListener(this);
		btnSave.addActionListener(this);
		btnAdd.addActionListener(this);
		vat.addActionListener(this);
		gst.addActionListener(this);
		
		//TODO MOUSE LISTNER
		taxTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e)
			{
				
				int row = taxTable.getSelectedRow();
				
				   if (!option.equals("A"))	                 // put A for add and E for edit in last field
					   taxTable.setValueAt("E", row, 17);
				   taxTableModel.fireTableCellUpdated(row, 17);
				   
				 
			}
			public void mousePressed(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent e) 
			{
				int row = taxTable.getSelectedRow();
				 
				   if (!option.equals("A"))	                 // put A for add and E for edit in last field
					   taxTable.setValueAt("E", row, 17);
			}
		});			
		 
		 
		
	}

	
	

	
	public ArrayList taxUpdate() 
	{
		  
		Vector col = null;
		TaxDto tax = null;

		ArrayList TaxList = new ArrayList();
		Vector taxData = taxTableModel.getDataVector();
		try {
			   int s = taxData.size();
			for (int i = 0; i < s; i++) {
				col = (Vector) taxData.get(i);
				 
				  if(col.get(17).equals("E"))	
				  {
					  
					
					tax = new TaxDto();
					tax.setDepo_code(loginDt.getDepo_code());
					tax.setGst_type("N");

					tax.setCgst_per(0.00);
					tax.setIgst_per(0.00);
					tax.setCess_per(0.00);

					if (col.get(0)!=null)
						tax.setState_code(setIntNumber(col.get(0).toString()));
					if (col.get(2)!=null)
					{
						tax.setTax_type(col.get(2).toString().trim());
					}
					if (col.get(3)!=null)
						tax.setTax_desc(col.get(3).toString());
					if (col.get(4)!=null)
						tax.setTax_per(setDoubleNumber(col.get(4).toString()));
					if (col.get(5)!=null)
						tax.setTax_on(col.get(5).toString());
					if (col.get(6)!=null)
						tax.setTax_on_free(col.get(6).toString());
					if (col.get(7)!=null)
						tax.setDisc(col.get(7).toString());
					if (col.get(8)!=null)
						tax.setAdd_tax1(setDoubleNumber(col.get(8).toString()));
					if (col.get(9)!=null)
						tax.setAdd_desc1(col.get(9).toString());
					if (col.get(10)!=null)
						tax.setAdd_tax2(setDoubleNumber(col.get(10).toString()));
					if (col.get(11)!=null)
						tax.setAdd_desc2(col.get(11).toString());
					if (col.get(12)!=null)
						tax.setCst_reim(col.get(12).toString());
					if (col.get(13)!=null)
						tax.setExc_rate(setDoubleNumber(col.get(13).toString()));
					if (col.get(14)!=null)
					{
					  try{	
						tax.setSdate(sdf.parse(col.get(14).toString()));
					  }catch(ParseException p)
					  {
						  System.out.println("date canot be parsed 14 "+p);
					  }
					  
					}
					if (col.get(15)!=null)
					{
						try{	
							tax.setEdate(sdf.parse(col.get(15).toString()));
						  }catch(ParseException p)
						  {
							  System.out.println("date canot be parsed 15 "+p);
						  }
						 
					  
					}
					tax.setSerialno(setIntNumber(col.get(16).toString()));
					TaxList.add(tax);
					 
				  }

				} // end of for loop 
				  
		} 
		catch (Exception e) 
		{
			System.out.println("UPDATE MEIN ERROR HAI "+e);
		}
		
		return TaxList;		
		
		     
	}
	
	public ArrayList gstUpdate() 
	{
		  
		Vector col = null;
		TaxDto tax = null;

		ArrayList TaxList = new ArrayList();
		Vector taxData = gstTableModel.getDataVector();
		try {
			   int s = taxData.size();
			for (int i = 0; i < s; i++) {
				col = (Vector) taxData.get(i);
				 
				  if(col.get(17).equals("E"))	
				  {
					  
					System.out.println("GST UODATE MAI AAYA KYA");
					tax = new TaxDto();
					tax.setDepo_code(loginDt.getDepo_code());
					tax.setGst_type("Y");
					if (col.get(0)!=null)
						tax.setState_code(setIntNumber(col.get(0).toString()));
					if (col.get(2)!=null)
					{
						tax.setTax_type(col.get(2).toString().trim());
					}
					if (col.get(3)!=null)
						tax.setTax_desc(col.get(3).toString());
					if (col.get(4)!=null)
						tax.setTax_per(setDoubleNumber(col.get(4).toString()));

					if (col.get(5)!=null)
						tax.setCgst_per(setDoubleNumber(col.get(5).toString()));
					if (col.get(6)!=null)
						tax.setIgst_per(setDoubleNumber(col.get(6).toString()));
					if (col.get(7)!=null)
						tax.setCess_per(setDoubleNumber(col.get(7).toString()));

					
					if (col.get(8)!=null)
						tax.setTax_on(col.get(8).toString());
					if (col.get(9)!=null)
						tax.setTax_on_free(col.get(9).toString());
					if (col.get(10)!=null)
						tax.setDisc(col.get(10).toString());
					if (col.get(11)!=null)
						tax.setAdd_tax1(setDoubleNumber(col.get(11).toString()));
					tax.setAdd_desc1("");
					if (col.get(12)!=null)
						tax.setAdd_tax2(setDoubleNumber(col.get(12).toString()));
					tax.setAdd_desc2("");
					tax.setCst_reim("");
					if (col.get(13)!=null)
						tax.setExc_rate(setDoubleNumber(col.get(13).toString()));
					if (col.get(14)!=null)
					{
					  try{	
						tax.setSdate(sdf.parse(col.get(14).toString()));
					  }catch(ParseException p)
					  {
						  System.out.println("date canot be parsed 14 "+p);
					  }
					  
					}
					if (col.get(15)!=null)
					{
						try{	
							tax.setEdate(sdf.parse(col.get(15).toString()));
						  }catch(ParseException p)
						  {
							  System.out.println("date canot be parsed 15 "+p);
						  }
						 
					  
					}
					tax.setSerialno(setIntNumber(col.get(16).toString()));
					TaxList.add(tax);
					 
				  }

				} // end of for loop 
				  
		} 
		catch (Exception e) 
		{
			System.out.println("UPDATE MEIN ERROR HAI "+e);
		}
		
		return TaxList;		
		
		     
	}

	public ArrayList taxAdd() 
	{
		  
		Vector col = null;
		TaxDto tax = null;

		ArrayList TaxList = new ArrayList();
		Vector taxData = taxTableModel.getDataVector();
		try {
			   int s = taxData.size();
			for (int i = 0; i < s; i++) {
				col = (Vector) taxData.get(i);
				 
				  if(col.get(17).equals("A") && col.get(14)!=null)	
				  {
					  	tax = new TaxDto();
						tax.setDepo_code(loginDt.getDepo_code());
						tax.setGst_type("N");

						tax.setCgst_per(0.00);
						tax.setIgst_per(0.00);
						tax.setCess_per(0.00);
						if (col.get(0)!=null)
							tax.setState_code(setIntNumber(col.get(0).toString()));
						if (col.get(2)!=null)
							tax.setTax_type(col.get(2).toString());
						if (col.get(3)!=null)
							tax.setTax_desc(col.get(3).toString());
						if (col.get(4)!=null)
							tax.setTax_per(setDoubleNumber(col.get(4).toString()));
						if (col.get(5)!=null)
							tax.setTax_on(col.get(5).toString());
						if (col.get(6)!=null)
							tax.setTax_on_free(col.get(6).toString());
						if (col.get(7)!=null)
							tax.setDisc(col.get(7).toString());
						if (col.get(8)!=null)
							tax.setAdd_tax1(setDoubleNumber(col.get(8).toString()));
						if (col.get(9)!=null)
							tax.setAdd_desc1(col.get(9).toString());
						if (col.get(10)!=null)
							tax.setAdd_tax2(setDoubleNumber(col.get(10).toString()));
						if (col.get(11)!=null)
							tax.setAdd_desc2(col.get(11).toString());
						if (col.get(12)!=null)
							tax.setCst_reim(col.get(12).toString());
						if (col.get(13)!=null)
							tax.setExc_rate(setDoubleNumber(col.get(13).toString()));
						if (col.get(14)!=null)
						{
							
						  try{	
							tax.setSdate(sdf.parse(col.get(14).toString()));
						  }catch(ParseException p)
						  {
							  System.out.println("date canot be parsed 14 "+p);
							  TaxList=null;
							  break;

						  }
						  
						}
						if (col.get(15)!=null)
						{
							try{	
								tax.setEdate(sdf.parse(col.get(15).toString()));
							  }catch(ParseException p)
							  {
								  System.out.println("date canot be parsed 14 "+p);
								  TaxList=null;
								  break;
							  }
							 
						  
						}
						//tax.setSerialno(setIntNumber(col.get(15).toString()));
						
						TaxList.add(tax);
				  }
				  
				  taxTable.setValueAt(" ", i, 17);
				  
				} // end of for loop 
				  
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		return TaxList;		
		
		     
	}

	
	public ArrayList GSTAdd() 
	{
		  
		Vector col = null;
		TaxDto tax = null;

		ArrayList TaxList = new ArrayList();
		Vector taxData = gstTableModel.getDataVector();
		try {
			   int s = taxData.size();
			for (int i = 0; i < s; i++) {
				col = (Vector) taxData.get(i);
				 
				  if(col.get(17).equals("A") && col.get(14)!=null)	
				  {
					  	tax = new TaxDto();
						tax.setDepo_code(loginDt.getDepo_code());
						tax.setGst_type("Y");
						if (col.get(0)!=null)
							tax.setState_code(setIntNumber(col.get(0).toString()));
						if (col.get(2)!=null)
							tax.setTax_type(col.get(2).toString());
						if (col.get(3)!=null)
							tax.setTax_desc(col.get(3).toString());
						if (col.get(4)!=null)
							tax.setTax_per(setDoubleNumber(col.get(4).toString()));
						if (col.get(5)!=null)
							tax.setCgst_per((setDoubleNumber(col.get(5).toString())));
						if (col.get(6)!=null)
							tax.setIgst_per((setDoubleNumber(col.get(6).toString())));
						if (col.get(7)!=null)
							tax.setCess_per(setDoubleNumber(col.get(7).toString()));

						if (col.get(8)!=null)
							tax.setTax_on(col.get(8).toString());
						if (col.get(9)!=null)
							tax.setTax_on_free(col.get(9).toString());
						if (col.get(10)!=null)
							tax.setDisc(col.get(10).toString());

						if (col.get(11)!=null)
							tax.setAdd_tax1(setDoubleNumber(col.get(11).toString()));
						tax.setAdd_desc1("");
						if (col.get(12)!=null)
							tax.setAdd_tax2(setDoubleNumber(col.get(12).toString()));
						tax.setAdd_desc2("");
						tax.setCst_reim("");
						if (col.get(13)!=null)
							tax.setExc_rate(setDoubleNumber(col.get(13).toString()));
						if (col.get(14)!=null)
						{
							
						  try{	
							tax.setSdate(sdf.parse(col.get(14).toString()));
						  }catch(ParseException p)
						  {
							  System.out.println("date canot be parsed 14 "+p);
							  TaxList=null;
							  break;

						  }
						  
						}
						if (col.get(15)!=null)
						{
							try{	
								tax.setEdate(sdf.parse(col.get(15).toString()));
							  }catch(ParseException p)
							  {
								  System.out.println("date canot be parsed 14 "+p);
								  TaxList=null;
								  break;
							  }
							 
						  
						}
						//tax.setSerialno(setIntNumber(col.get(15).toString()));
						
						TaxList.add(tax);
				  }
				  
				  gstTable.setValueAt(" ", i, 17);
				  
				} // end of for loop 
				  
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
		
		return TaxList;		
		
		     
	}
	
	
	public ArrayList getSelectedRow()
	{
		Vector col = null;
		AreaDto area = null;
		ArrayList dataList = new ArrayList();
		Vector data = taxTableModel.getDataVector();
		int s =data.size();
		try 
		{
			for (int i = 0; i < s; i++) 
			{

				col = (Vector) data.get(i);
				if ((Boolean) col.get(0)) 
				{
					area=new AreaDto();
					area.setDepo_code(loginDt.getDepo_code());
					area.setDiv_code(loginDt.getDiv_code());
					area.setArea_cd(setIntNumber(col.get(1).toString()));
					area.setMkt_year(loginDt.getMkt_year());					
					dataList.add(area);
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
		taxTableModel.getDataVector().removeAllElements();
		taxTableModel.fireTableDataChanged();
		Vector<?> c = null;
		 
	 	Vector<?> v = taxdao.getTaxList(loginDt.getDiv_code(),loginDt.getDepo_code(),loginDt.getMkt_year(),"N");
		int rows = v.size();
		int totalRow = 16-rows;
	 	for(int i =0;i<rows;i++)
		{
			c =(Vector<?>) v.get(i);
			taxTableModel.addRow(c);
		}
	 	/*c =null;
	 	for(int i =0;i<totalRow;i++)
		{
			taxTableModel.addRow(c);
		}*/

	 	
		taxTable.requestFocus();
		taxTable.changeSelection(0, 0, false, false);
		taxTable.editCellAt(0, 0);
	}
	public void fillGSTTable()
	{
		

		gstTableModel.getDataVector().removeAllElements();
		gstTableModel.fireTableDataChanged();
		Vector<?> c = null;
		 
	 	Vector<?> v = taxdao.getTaxList(loginDt.getDiv_code(),loginDt.getDepo_code(),loginDt.getMkt_year(),"Y");
		int rows = v.size();
		
	 	for(int i =0;i<rows;i++)
		{
			c =(Vector<?>) v.get(i);
			gstTableModel.addRow(c);
		}
		gstTable.requestFocus();
		gstTable.changeSelection(0, 0, false, false);
		gstTable.editCellAt(0, 0);
	}
	

	public void itemTableUI() {
		taxTable.setColumnSelectionAllowed(false);
		taxTable.setCellSelectionEnabled(false);
		//taxTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		taxTable.getTableHeader().setReorderingAllowed(false);
		taxTable.getTableHeader().setResizingAllowed(false);
		taxTable.setRowHeight(20);
		taxTable.getTableHeader().setPreferredSize(new Dimension(60, 60));
		taxTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		taxTable.getColumnModel().getColumn(0).setPreferredWidth(60);//sc
		taxTable.getColumnModel().getColumn(1).setPreferredWidth(140);//sm
		taxTable.getColumnModel().getColumn(2).setPreferredWidth(70);//tax tp
		taxTable.getColumnModel().getColumn(3).setPreferredWidth(100);//tax disc
		taxTable.getColumnModel().getColumn(4).setPreferredWidth(60);//tax%
		taxTable.getColumnModel().getColumn(5).setPreferredWidth(80);//tax on
		taxTable.getColumnModel().getColumn(6).setPreferredWidth(60);//tax on free
		taxTable.getColumnModel().getColumn(7).setPreferredWidth(70);//disc y/n
		taxTable.getColumnModel().getColumn(8).setPreferredWidth(60);//add tax1
		taxTable.getColumnModel().getColumn(9).setPreferredWidth(70);//add dics
		taxTable.getColumnModel().getColumn(10).setPreferredWidth(60);//add tax2
		taxTable.getColumnModel().getColumn(11).setPreferredWidth(70);//add disc
		taxTable.getColumnModel().getColumn(12).setPreferredWidth(85);//cst reimburse
		taxTable.getColumnModel().getColumn(13).setPreferredWidth(80);//exc rate
		taxTable.getColumnModel().getColumn(14).setPreferredWidth(90);//frm
		taxTable.getColumnModel().getColumn(15).setPreferredWidth(90);//to
		
		taxTable.getColumnModel().getColumn(16).setPreferredWidth(0);
		taxTable.getColumnModel().getColumn(16).setMinWidth(0); // serialno 
		taxTable.getColumnModel().getColumn(16).setMaxWidth(0);  
		
		taxTable.getColumnModel().getColumn(17).setPreferredWidth(0);
		taxTable.getColumnModel().getColumn(17).setMinWidth(0); // option 
		taxTable.getColumnModel().getColumn(17).setMaxWidth(0);  
		
		taxTable.getColumnModel().getColumn(14).setCellEditor(new DefaultCellEditor(f1));
		taxTable.getColumnModel().getColumn(15).setCellEditor(new DefaultCellEditor(f2));
		
		taxTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		taxTable.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		taxTable.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
		taxTable.getColumnModel().getColumn(13).setCellRenderer(rightRenderer);
 

		table_pcode = new JFormattedTextField();
		checkIntegerWithLength(table_pcode,1);
		table_pcode.setSelectionStart(0);
		taxTable.getColumnModel().getColumn(0).setCellEditor(new NumberTableCellEditor(table_pcode));


		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(11); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		taxTable.getColumnModel().getColumn(4).setCellEditor(new NumberTableCellEditor(dobl));	
		taxTable.getColumnModel().getColumn(8).setCellEditor(new NumberTableCellEditor(dobl));	
		taxTable.getColumnModel().getColumn(10).setCellEditor(new NumberTableCellEditor(dobl));	
		taxTable.getColumnModel().getColumn(13).setCellEditor(new NumberTableCellEditor(dobl));	
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"YN");
		table_pcode.setSelectionStart(0);
		taxTable.getColumnModel().getColumn(6).setCellEditor(new NumberTableCellEditor(table_pcode));
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"YN");
		table_pcode.setSelectionStart(0);
		taxTable.getColumnModel().getColumn(7).setCellEditor(new NumberTableCellEditor(table_pcode));
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"YN");
		table_pcode.setSelectionStart(0);
		taxTable.getColumnModel().getColumn(12).setCellEditor(new NumberTableCellEditor(table_pcode));

		
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"SMX");
		table_pcode.setSelectionStart(0);
		taxTable.getColumnModel().getColumn(5).setCellEditor(new NumberTableCellEditor(table_pcode));
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"ABYNECJ");
		table_pcode.setSelectionStart(0);
		taxTable.getColumnModel().getColumn(2).setCellEditor(new NumberTableCellEditor(table_pcode));
		
		// table.setBounds(112, 310, 766, 193);

		// ////////////////////////////////////////////////////////////////
		taxTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());
		taxTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "selectNextColumnCell");
	}
	

	public void itemTableUIGST() {
		gstTable.setColumnSelectionAllowed(false);
		gstTable.setCellSelectionEnabled(false);
		//gstTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		gstTable.getTableHeader().setReorderingAllowed(false);
		gstTable.getTableHeader().setResizingAllowed(false);
		gstTable.setRowHeight(20);
		gstTable.getTableHeader().setPreferredSize(new Dimension(60, 60));
		gstTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		gstTable.getColumnModel().getColumn(0).setPreferredWidth(60);//sc
		gstTable.getColumnModel().getColumn(1).setPreferredWidth(140);//sm
		gstTable.getColumnModel().getColumn(2).setPreferredWidth(70);//tax tp
		gstTable.getColumnModel().getColumn(3).setPreferredWidth(100);//tax disc
		gstTable.getColumnModel().getColumn(4).setPreferredWidth(60);//sgsttax%
		gstTable.getColumnModel().getColumn(5).setPreferredWidth(60);//cgst %
		gstTable.getColumnModel().getColumn(6).setPreferredWidth(60);//igst tax%
		gstTable.getColumnModel().getColumn(7).setPreferredWidth(60);//cess tax%
		gstTable.getColumnModel().getColumn(8).setPreferredWidth(80);//tax on
		gstTable.getColumnModel().getColumn(9).setPreferredWidth(60);//tax on free
		gstTable.getColumnModel().getColumn(10).setPreferredWidth(70);//disc y/n
		gstTable.getColumnModel().getColumn(11).setPreferredWidth(60);//add tax1
		gstTable.getColumnModel().getColumn(12).setPreferredWidth(60);//add tax2
		gstTable.getColumnModel().getColumn(13).setPreferredWidth(80);//exc rate
		gstTable.getColumnModel().getColumn(14).setPreferredWidth(90);//frm
		gstTable.getColumnModel().getColumn(15).setPreferredWidth(90);//to
		
		gstTable.getColumnModel().getColumn(16).setPreferredWidth(0);
		gstTable.getColumnModel().getColumn(16).setMinWidth(0); // serialno 
		gstTable.getColumnModel().getColumn(16).setMaxWidth(0);  
		
		gstTable.getColumnModel().getColumn(17).setPreferredWidth(0);
		gstTable.getColumnModel().getColumn(17).setMinWidth(0); // option 
		gstTable.getColumnModel().getColumn(17).setMaxWidth(0);  
		
		gstTable.getColumnModel().getColumn(14).setCellEditor(new DefaultCellEditor(f1));
		gstTable.getColumnModel().getColumn(15).setCellEditor(new DefaultCellEditor(f2));
		
		gstTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		gstTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		gstTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		gstTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		gstTable.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
		gstTable.getColumnModel().getColumn(12).setCellRenderer(rightRenderer);
		gstTable.getColumnModel().getColumn(13).setCellRenderer(rightRenderer);
 

		table_pcode = new JFormattedTextField();
		checkIntegerWithLength(table_pcode,1);
		table_pcode.setSelectionStart(0);
		gstTable.getColumnModel().getColumn(0).setCellEditor(new NumberTableCellEditor(table_pcode));


		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(11); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		gstTable.getColumnModel().getColumn(4).setCellEditor(new NumberTableCellEditor(dobl));	
		gstTable.getColumnModel().getColumn(5).setCellEditor(new NumberTableCellEditor(dobl));	
		gstTable.getColumnModel().getColumn(6).setCellEditor(new NumberTableCellEditor(dobl));	
		gstTable.getColumnModel().getColumn(7).setCellEditor(new NumberTableCellEditor(dobl));	
		gstTable.getColumnModel().getColumn(11).setCellEditor(new NumberTableCellEditor(dobl));	
		gstTable.getColumnModel().getColumn(12).setCellEditor(new NumberTableCellEditor(dobl));	
		gstTable.getColumnModel().getColumn(13).setCellEditor(new NumberTableCellEditor(dobl));	
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"YN");
		table_pcode.setSelectionStart(0);
		gstTable.getColumnModel().getColumn(9).setCellEditor(new NumberTableCellEditor(table_pcode));
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"YN");
		table_pcode.setSelectionStart(0);
		gstTable.getColumnModel().getColumn(10).setCellEditor(new NumberTableCellEditor(table_pcode));
		
		
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"S");
		table_pcode.setSelectionStart(0);
		gstTable.getColumnModel().getColumn(8).setCellEditor(new NumberTableCellEditor(table_pcode));
		
		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"ABECJD");
		table_pcode.setSelectionStart(0);
		gstTable.getColumnModel().getColumn(2).setCellEditor(new NumberTableCellEditor(table_pcode));
		
		// table.setBounds(112, 310, 766, 193);

		// ////////////////////////////////////////////////////////////////
		gstTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());
		gstTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "selectNextColumnCell");
	}
	
	
	
	
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{   
			
			if (option.equals("A"))
			{
				taxTableModel.removeRow(rrow);
				taxTableModel.fireTableRowsDeleted(rrow, rrow);
				option="";


			}
			fillTable();
		    gst.setSelected(true);

			dispose();
			taxTable.requestFocus();
			taxTable.changeSelection(0, 0, false, false);
			taxTable.editCellAt(0, 0);
		}

		if(e.getActionCommand().equalsIgnoreCase("Add") && vat.isSelected())
		{
			option="A";
			int row =taxTable.getRowCount(); 
			rrow=row;
			String ss =null;
			try
			{
				ss = taxTableModel.getValueAt(row-1, 2).toString();
				 
			}
			catch(Exception exp)
			{
				ss="";
				taxTable.requestFocus();
				if(row==0)
				{
				  taxTableModel.addRow(new Object[] {});
				  taxTable.setValueAt("A", 0, 17);
				  taxTable.changeSelection(0, 0, false, false);
				  taxTable.editCellAt(0, 0);
				}
				if(row>0)
				{
					taxTable.changeSelection(row, 0, false, false);
					taxTable.editCellAt(row, 0);
				}
			}
			if(!ss.equalsIgnoreCase(""))
			{
				taxTableModel.addRow(new Object[] {});
				taxTable.requestFocus();
				taxTable.changeSelection(row, 0, false, false);
				taxTable.editCellAt(row, 0);
				taxTable.setValueAt("  /  /    ", row, 14);
				taxTable.setValueAt("  /  /    ", row, 15);
				taxTable.setValueAt("A", row, 17);
				
			}
			else
			{
				taxTable.requestFocus();
				taxTable.changeSelection(row-1, 0, false, false);
				taxTable.editCellAt(row-1, 0);
			}
		}

		
		if(e.getActionCommand().equalsIgnoreCase("Add") && gst.isSelected())
		{
			option="A";
			int row =gstTable.getRowCount(); 
			rrow=row;
			String ss =null;
			try
			{
				ss = gstTableModel.getValueAt(row-1, 2).toString();
				 
			}
			catch(Exception exp)
			{
				ss="";
				gstTable.requestFocus();
				if(row==0)
				{
				  gstTableModel.addRow(new Object[] {});
				  gstTable.setValueAt("A", 0, 17);
				  gstTable.changeSelection(0, 0, false, false);
				  gstTable.editCellAt(0, 0);
				}
				if(row>0)
				{
					gstTable.changeSelection(row, 0, false, false);
					gstTable.editCellAt(row, 0);
				}
			}
			if(!ss.equalsIgnoreCase(""))
			{
				gstTableModel.addRow(new Object[] {});
				gstTable.requestFocus();
				gstTable.changeSelection(row, 0, false, false);
				gstTable.editCellAt(row, 0);
				gstTable.setValueAt("  /  /    ", row, 14);
				gstTable.setValueAt("  /  /    ", row, 15);
				gstTable.setValueAt("A", row, 17);
				
			}
			else
			{
				gstTable.requestFocus();
				gstTable.changeSelection(row-1, 0, false, false);
				gstTable.editCellAt(row-1, 0);
			}
		}
		
		
		if(e.getActionCommand().equalsIgnoreCase("VAT"))
		{
			lblDispatchEntry.setText("Tax Master");
			lblAFood.setText("A = Medicine");
			lblBMedicine.setText("B = Food Products");
			lblYCst.setText("Y = CST Tax (With CForm)");
			lblNCst.setText("N = CST Tax (Without CForm)");
			lblETax.setText("E = Tax Exempted");
			lblCTax.setText("C = Cosmetic");
			lblBTax.setText("J = Beverage");
			lblTaxType.setText("Tax Type:");
			areaPane.setVisible(true);
			gstPane.setVisible(false);
			fillTable();
		}
		
		if(e.getActionCommand().equalsIgnoreCase("GST"))
		{
		
			lblDispatchEntry.setText("GST Master");
			lblAFood.setText("A = 5% GST");
			lblBMedicine.setText("D = 12% GST");
			lblYCst.setText("B = 18% GST");
			lblNCst.setText("C = 28% GST");
			lblETax.setText("E = GST Exempted");
			lblCTax.setText("");
			lblBTax.setText("");
			lblTaxType.setText("GST Type:");
			areaPane.setVisible(false);
			gstPane.setVisible(true);
			fillGSTTable();
		}
		
		if(e.getActionCommand().equalsIgnoreCase("Save"))
		{
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to save the changes?", "Save Changes",dialogButton);
			if(dialogResult==0)
			{
				if(vat.isSelected())
					callSave();
				else
					callSave1();
			}
			else
			{
				System.out.println("no is option select");
			}
		}		
	}
	
	
	 public void callSave()
	 {
		 int h=0;
		 
			/*taxTable.requestFocus();
			taxTable.changeSelection(0, 15, false, false);
			taxTable.editCellAt(0, 15); */
			 
			
			 ArrayList l=null;
			 if (option.equals("A")){
				 l=taxAdd();
				 if (l==null)
				 {
					 taxTableModel.removeRow(rrow);
					 taxTableModel.fireTableRowsDeleted(rrow, rrow);
				 }
				 else
				 {
					 h = taxdao.addTax(l);
				 }
				// if (l!=null) 				 
					 
			 }else{
				 l=taxUpdate();
				if (!l.isEmpty()) 
				 h = taxdao.updateTax(l);
			 }
			 option="";
			 fillTable();
			 gst.setSelected(true);

			 System.out.println("record update "+h);  
	 }
	 
	 public void callSave1()
	 {
		 int h=0;
		 
			
			 ArrayList l=null;
			 if (option.equals("A")){
				 l=GSTAdd();
				 if (l==null)
				 {
					 gstTableModel.removeRow(rrow);
					 gstTableModel.fireTableRowsDeleted(rrow, rrow);
				 }
				 else
				 {
					 h = taxdao.addTax(l);
				 }
				// if (l!=null) 				 
					 
			 }else{
				 l=gstUpdate();
				if (!l.isEmpty()) 
				 h = taxdao.updateTax(l);
			 }
			 option="";
			 gst.setSelected(true);
			 fillGSTTable();
			 System.out.println("record update "+h);  
	 } 	

}


