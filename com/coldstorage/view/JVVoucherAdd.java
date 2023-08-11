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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.coldstorage.dao.JvVouDAO;
import com.coldstorage.dao.PartyDAO;
import com.coldstorage.dto.BookDto;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.util.JDoubleField;
import com.coldstorage.util.NumberTableCellEditor;
import com.coldstorage.util.OverWriteTableCellEditor;

public class JVVoucherAdd extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	
	private JDoubleField vou_no;
	private JFormattedTextField vou_date,vdate;
	private JLabel lblVoucherNo,lblVoucherDate; 
	private JLabel lblinvalid;
	private JLabel lblPartyCode; 
	private JLabel lblPartyName;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_12;
	private JLabel branch;
	private JLabel lblDivision;
	private JLabel division;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private SimpleDateFormat sdf;
	private NumberFormat df;
	private DefaultTableModel vouTableModel,invNoTableModel;
	private DefaultTableCellRenderer rightRenderer;
	private JTable vouTable,invNoTable;
	private JScrollPane vouPane;
	String partyCd;
	PartyDto prtyDto;
	String sdate,edate;
	int billNo,option,bkcode,bkcd,rrow ;
	BookDto bk;
	RcpDto rcp;
	JvVouDAO jvDao;
	private JTextField vno;
	private Font fontPlan;
	private JPanel panel_3;
	private JLabel label_4;
	private JList expList;
	private JLabel lblBillNo;
	private JTextField vnart_2;
	private JButton btnSave,btnExit,btnAdd,btnDelete,btnPrint,btnAddMore;
	private JLabel lastNo;
	private JLabel label_3;
	private int vouNo;
	private JTextField totalcr;
	double totcr,totdr;
	private JTextField totaldr;
	private double crval;
	private JTextField diffamt;
	private Vector partyNames;

	private HashMap partyMap;
	private PartyDAO pdao ;
	private JFormattedTextField table_pcode;	
	private boolean addRecord;
	private boolean addNewRow;
	private int sNo,lastno;
	  private JDoubleField dobl;
	public JVVoucherAdd()
	{
		
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");  // Date Format
		sdate=sdf.format(loginDt.getFr_date());
		edate=sdf.format(loginDt.getTo_date());

		sdf = new SimpleDateFormat("dd/MM/yyyy");  // Date Format
		df= new DecimalFormat("#########0.00");
		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		fontPlan =new Font("Tahoma", Font.PLAIN, 11);
		
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		jvDao = new JvVouDAO();
		pdao = new PartyDAO();

		expList = new JList(loginDt.getPrtList());
		//partyList.setFont(font);
		expList.setSelectedIndex(0);
		expList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final JScrollPane expPane = new JScrollPane(expList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		expPane.setBounds(388, 258, 286, 259);
		getContentPane().add(expPane);
		expPane.setVisible(false);
		
		
		//////////////invoce no table model/////////////////////
		String [] invColName=	{"Vou No.", "Name","",""};
		String datax[][]={{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}};
		invNoTableModel=  new DefaultTableModel(datax,invColName)
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) 
			{
				return false;
			}
		};

		invNoTable = new JTable(invNoTableModel);
		invNoTable.setFont(fontPlan);
		invNoTable.setColumnSelectionAllowed(false);
		invNoTable.setCellSelectionEnabled(false);
		invNoTable.getTableHeader().setReorderingAllowed(false);
		invNoTable.getTableHeader().setResizingAllowed(false);
		invNoTable.setRowHeight(20);
		invNoTable.getTableHeader().setPreferredSize(new Dimension(25,25));
		///////////////////////////////////////////////////////////////////////////
		invNoTable.getColumnModel().getColumn(0).setPreferredWidth(70);   //contact inv no
		invNoTable.getColumnModel().getColumn(1).setPreferredWidth(120);  //party name/////
		invNoTable.getColumnModel().getColumn(2).setMinWidth(0);  //inv_no/////
		invNoTable.getColumnModel().getColumn(2).setMaxWidth(0);  //inv_no/////
		invNoTable.getColumnModel().getColumn(3).setMinWidth(0);  //Detail List/////
		invNoTable.getColumnModel().getColumn(3).setMaxWidth(0);  //Detail List/////
		///////////////////////////////////////////////////////
		JScrollPane scrollPane = new JScrollPane(invNoTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(16, 235, 184, 400);
		getContentPane().add(scrollPane);

		///////////////////////////////////////////////////////////////////////////
		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(314, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);

		lblPartyCode = new JLabel("J.V. Number:");
		lblPartyCode.setBounds(343, 149, 110, 20);
		getContentPane().add(lblPartyCode);

		vou_no = new JDoubleField();
		vou_no.setEditable(false);
		vou_no.setHorizontalAlignment(SwingConstants.RIGHT);
		vou_no.setMaxLength(5);
		vou_no.setAllowNegative(false);
		vou_no.setBounds(453, 149,132, 23);
		getContentPane().add(vou_no);

		lblPartyName = new JLabel("Date:");
		lblPartyName.setBounds(614, 149, 79, 20);
		getContentPane().add(lblPartyName);


		vou_date = new JFormattedTextField(sdf);
		vou_date.setBounds(703, 149, 132, 23);
		checkDate(vou_date);
		vou_date.setText(sdf.format(new Date()));
		getContentPane().add(vou_date);


		lblinvalid = new JLabel();
		lblinvalid.setBounds(845, 150, 118, 23);
		getContentPane().add(lblinvalid);

/*		label_2 = new JLabel(cmpname2);
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(10, 71, 195, 14);
		getContentPane().add(label_2);
*/
		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);

		branch = new JLabel(loginDt.getBrnnm());
		branch.setForeground(Color.BLACK);
		branch.setFont(new Font("Tahoma", Font.BOLD, 11));
		branch.setBounds(10, 86, 195, 14);
		getContentPane().add(branch);

		lblDivision = new JLabel("DIVISION:");
		lblDivision.setForeground(Color.BLACK);
		lblDivision.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDivision.setBounds(10, 101, 60, 14);
		getContentPane().add(lblDivision);

		division = new JLabel(loginDt.getDivnm());
		division.setForeground(Color.BLACK);
		division.setFont(new Font("Tahoma", Font.BOLD, 11));
		division.setBounds(75, 101, 60, 14);
		getContentPane().add(division);

		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(214, 657, 763, 48);
		getContentPane().add(panel_2);

		lblDispatchEntry = new JLabel("Journal Voucher");
		lblDispatchEntry.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDispatchEntry.setBounds(401, 89, 334, 22);
		getContentPane().add(lblDispatchEntry);




		///////////////////////////////////////////////////////////////////////////////////////////////
		KeyListener keyListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) 
				{

					JTextField textField = (JTextField) keyEvent.getSource();
					int id = Integer.parseInt(textField.getName());

					switch (id) 
					{
					case 0:
						//vou_date.requestFocus();
						break;

					case 1:
						String date=vou_date.getText();
						if(isValidDate(date))
						{
							lblinvalid.setVisible(false);
							// Check the Date Range for the Financial year
							if (isValidRange(date,loginDt.getFr_date(),loginDt.getTo_date()))
							{
								vnart_2.requestFocus();
							}
							else
							{
								JOptionPane.showMessageDialog(JVVoucherAdd.this,"Date range should be in between: "
								+ sdf.format(loginDt.getFr_date())+ " to " + sdf.format(loginDt.getTo_date()),
								"Date Range",	JOptionPane.INFORMATION_MESSAGE);
								vou_date.setValue(null);
								checkDate(vou_date);
								vou_date.requestFocus();

							}


						}
						else
						{
							lblinvalid.setText("invalid date");
							lblinvalid.setForeground(Color.red);
							lblinvalid.setVisible(true);
							vou_date.setValue(null);
							checkDate(vou_date);
							vou_date.requestFocus();
						}
						break;

				 


					case 2:
						vouTable.requestFocus();
						vouTable.changeSelection(0,0, false, false);
						vouTable.editCellAt(0, 0);
						break;

					}

				}

				if (key == KeyEvent.VK_ESCAPE) 
				{
					clearAll(false);
					dispose();

				}




			}

		};

		//////////////////////////////////////////////////////////////////////
		String[] crDrColName = {"Ref No","","A/c Code","Account Head","Narration","DR Amt","","","","CR Amt",""};
		String cdDrData[][] = {{}};
		vouTableModel = new DefaultTableModel(cdDrData, crDrColName) 
		{
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) 
			{
				boolean ans=false;
				if (option==1 || option==2 || addNewRow)
				{
				    if (column==3)
  					  ans=false;
				    else
				      ans=true;	
				}
				if (option==0)  // edit mode
				{
///					if (column==3 || column==5 || column==9)
					if (column==3 )
						ans=false;
					else
						ans=true;
				}
				 return ans;
			}
			
			public Class<?> getColumnClass(int column) 
			{
				switch (column) 
				{
					case 5 : return Double.class;
					case 9 : return Double.class;
					case 10 : return Integer.class;
					default: return String.class;
				}
			}
			
		};

		vouTable = new JTable(vouTableModel);
		vouTableUI();
		vouTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		//////////////////////////////////////////////////////////////////////////
		vouPane = new JScrollPane(vouTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		vouPane.setBounds(214, 235, 763, 320);
		getContentPane().add(vouPane);
		vouPane.setVisible(true);

		
		// ////////////////////////////////////////////////////////////////
		vouTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());
		vouTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "selectNextColumnCell");
		

		
		

		/////////////////////////////////////////////////////////////////////////
		KeyListener crTableListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				int column = vouTable.getSelectedColumn();
				int row = vouTable.getSelectedRow();
				int totRow=vouTable.getRowCount();
				rrow=row;
			
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					if (column == 0) 
					{
						vouTable.changeSelection(row, 2, false, false);
						vouTable.editCellAt(row, 2);
					}

					if (column == 1) 
					{
						vouTable.changeSelection(row, 2, false, false);
						vouTable.editCellAt(row, 2);
						String tp = vouTable.getValueAt(row,1).toString().trim();
						System.out.println(" TP "+tp);
/*							 partyNames = (Vector) pdao.getPartyNm(loginDt.getDepo_code(), loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),99,1 );
							 partyMap = (HashMap) pdao.getPartyNmMap(loginDt.getCmp_code(), loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),99,1);
							 
*/							 
						 expList.removeAll();
						  
						 expList.setListData(partyNames);
						
					}

					if (column == 2) 
					{
						String s="";
/*						 partyNames = (Vector) pdao.getPartyNm(loginDt.getDepo_code(), loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),99,1 );
						 partyMap = (HashMap) pdao.getPartyNmMap(loginDt.getCmp_code(), loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),99,1);
*/						 vouTable.setValueAt(formatter.format(0.00),row, 5);
						 vouTable.setValueAt(formatter.format(0.00),row, 9);
						 
					 ///expList.removeAll();
					  
					 //expList.setListData(partyNames);

						try
						{
							vouTable.changeSelection(row, 4, false, false);
							vouTable.editCellAt(row,4);
							s = vouTable.getValueAt(row, 2).toString().trim();
							
						}
						catch(NullPointerException e)
						{
							s="";
							System.out.println("Exception in 1st column null pointer "+e );
						}
						
						if (s.equals(""))
						{
								expPane.setVisible(true);
								expList.setVisible(true);
								expPane.requestFocus();
								expList.requestFocus();
								expList.setSelectedIndex(0);
								// //// setting sroller to top//////////////
								expList.ensureIndexIsVisible(0);
							
						}
						else
						{
							 
							prtyDto = (PartyDto) partyMap.get(s);
							 
							if(prtyDto!=null)
							{
								vouTable.changeSelection(row, 4, false, false);
								vouTable.editCellAt(row,4);
								vouTable.setValueAt(prtyDto.getMac_code(), row, 2);
								vouTable.setValueAt(prtyDto.getMac_name(), row, 3);
								vouTable.setValueAt(prtyDto.getMsub_code(), rrow, 6);
								vouTable.setValueAt(prtyDto.getMgrp_code(), rrow, 7);
								vouTable.setValueAt(prtyDto.getMgrp_code(), rrow, 8);

								vouTable.changeSelection(row, 4, false, false);
								vouTable.editCellAt(row, 4);
							}
							else
							{
								expPane.setVisible(true);
								expList.setVisible(true);
								expPane.requestFocus();
								expList.requestFocus();
								expList.setSelectedIndex(0);
								// //// setting sroller to top//////////////
								expList.ensureIndexIsVisible(0);
							}
						}
					}
					
					if (column == 4) 
					{
						vouTable.changeSelection(row, 5, false, false);
						vouTable.editCellAt(row, 5);
					}
					if (column == 5) 
					{
						vouTable.changeSelection(row, 6, false, false);
						vouTable.editCellAt(row,6);
						dbval=setDoubleNumber(vouTable.getValueAt(row, 5).toString());
						vouTable.setValueAt(formatter.format(dbval), row, 5);
						vouTable.setValueAt(formatter.format(0.00), row, 9);
						
						if(dbval==0.00)
						{
							vouTable.changeSelection(row, 9, false, false);
							vouTable.editCellAt(row, 9);
						}
						else
						{
							
							String expName = vouTable.getValueAt(row, 3).toString().trim();
							totPara();
							
							if(!expName.equals(""))
							{
								if (option==1 || option==2 || addNewRow)  // for add button
								{
									if (row == (totRow - 1))
										row = -1;
									//vouTableModel.addRow(new Object[] {});
									vouTableModel.addRow(new Object[] {"","","","","",0.00,"","","",0.00});

									
									vouTable.changeSelection(totRow, 0, false, false);
									vouTable.editCellAt(totRow, 0);
								}

								if (option==0)  // for Edit & Save
								{
									if(row<totRow-1)
									{
										vouTable.changeSelection(row+1, 0, false, false);
										vouTable.editCellAt(row+1, 0);
									}
									else
									{
										vouTable.changeSelection(row, 0, false, false);
										vouTable.editCellAt(row, 0);
									}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(JVVoucherAdd.this,"Expense code cannot be blank" ,"Blank Expense Code!",JOptionPane.INFORMATION_MESSAGE);
								vouTable.requestFocus();
								vouTable.changeSelection(row, 0, false, false);
								vouTable.editCellAt(row, 0);
								//vouTable.setValueAt(formatter.format(dbval), row, 7);
							}

						}

					}
					if (column == 9) 
					{

						vouTable.changeSelection(row, 6, false, false);
						vouTable.editCellAt(row, 6);
						crval=setDoubleNumber(vouTable.getValueAt(row, 9).toString());
						
						
						String expName = vouTable.getValueAt(row, 3).toString().trim();
						
						totPara();
						if(!expName.equals(""))
						{
							vouTable.setValueAt(formatter.format(crval), row, 9);
							if((dbval+crval)>0)
							{
								
								if (option==1 || option==2 || addNewRow)  // for add button
								{

										if (row == (totRow - 1))
											row = -1;
										//vouTableModel.addRow(new Object[] {});
										vouTableModel.addRow(new Object[] {"","","","","",0.00,"","","",0.00,""});

										vouTable.changeSelection(totRow, 0, false, false);
										vouTable.editCellAt(totRow, 0);
								}
								
								  if (option==0)  // for Edit & Save
								  {
									if(row<totRow-1)
									{
										vouTable.changeSelection(row+1, 0, false, false);
										vouTable.editCellAt(row+1, 0);
									}
									else
									{
										vouTable.changeSelection(row, 0, false, false);
										vouTable.editCellAt(row, 0);
									}
								  }
						  
							}
							else
							{
								JOptionPane.showMessageDialog(JVVoucherAdd.this,"Check CR/Dr Amount " ,"!",JOptionPane.INFORMATION_MESSAGE);
								vouTable.requestFocus();
								vouTable.setValueAt(0.00, row, 5);
								vouTable.changeSelection(row, 5, false, false);
								vouTable.editCellAt(row, 5);
								totPara();
							}
						
						}
						else
						{
							JOptionPane.showMessageDialog(JVVoucherAdd.this,"Expense code cannot be blank" ,"Blank Expense Code!",JOptionPane.INFORMATION_MESSAGE);
							vouTable.requestFocus();
							vouTable.changeSelection(row, 0, false, false);
							vouTable.editCellAt(row, 0);
							//vouTable.setValueAt(formatter.format(dbval), row, 7);
						}
					}

					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					//vouPane.setVisible(false);
					 totPara();
					if(totcr==totdr)
					{
						btnSave.requestFocus();
						btnSave.setBackground(Color.BLUE);
						if(btnSave.hasFocus())
							vouTable.clearSelection();
					}
					else
					{
						alertMessage(JVVoucherAdd.this, "Debit and Credit Amount Totals are not matched Check...?");
					}
					evt.consume();
				}
					
					
					
			}// /// keypressed

		};

		vouTable.addKeyListener(crTableListener);
		

		
		// ////////////////////////////////////////////////////////////////
		expList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					int idx = expList.getSelectedIndex();
//					prtyDto = (PartyDto) partyNames.get(idx);
					prtyDto = (PartyDto) expList.getSelectedValue();
					partyCd = prtyDto.getMac_code();
					
					vouTable.setValueAt(prtyDto.getMac_code(), rrow, 2);
					vouTable.setValueAt(prtyDto.getMac_name(), rrow, 3);
					vouTable.setValueAt(prtyDto.getMsub_code(), rrow, 6);
					vouTable.setValueAt(prtyDto.getMgrp_code(), rrow, 7);
					vouTable.setValueAt(prtyDto.getMgrp_code(), rrow, 8);
//					bkcode=prtyDto.getMgrp_code();
					
					 
					// evt.consume();
					expPane.setVisible(false);
					vouTable.requestFocus();
					vouTable.changeSelection(rrow, 4, false, false);
					vouTable.editCellAt(rrow, 4);
					
				}
				
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					 
					// evt.consume();
					expPane.setVisible(false);
					vouTable.changeSelection(rrow, 2, false, false);
					vouTable.editCellAt(rrow, 2);
					 
				}				
				  //  evt.consume();
			}
		});
		
		
		
		panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBackground(new Color(51, 102, 204));
		panel_3.setBounds(10, 134, 194, 28);
		getContentPane().add(panel_3);
		
		label_4 = new JLabel("Search");
		label_4.setForeground(Color.WHITE);
		panel_3.add(label_4);
		
		lblVoucherNo = new JLabel("Voucher No:");
		lblVoucherNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVoucherNo.setBounds(16, 172, 83, 20);
		getContentPane().add(lblVoucherNo);
		
		vno = new JTextField();
		vno.setFont(new Font("Tahoma", Font.BOLD, 11));
		vno.setBounds(106, 172, 94, 22);
		getContentPane().add(vno);
		
		lblVoucherDate = new JLabel("Voucher Date:");
		lblVoucherDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVoucherDate.setBounds(16, 202, 83, 20);
		getContentPane().add(lblVoucherDate);

		vdate = new JFormattedTextField(sdf);
		vdate.setFont(new Font("Tahoma", Font.BOLD, 11));
		checkDate(vdate);
		vdate.setBounds(106, 202, 94, 22);
		getContentPane().add(vdate);
		
		lblBillNo = new JLabel("Remark:");
		lblBillNo.setBounds(343, 191, 110, 20);
		getContentPane().add(lblBillNo);
		
		vnart_2 = new JTextField();
		vnart_2.setBounds(453, 190, 382, 23);
		getContentPane().add(vnart_2);
		
		
		btnAdd = new JButton("Add");
		btnAdd.setActionCommand("Add");
		btnAdd.setBounds(214, 616, 86, 30);
		getContentPane().add(btnAdd);
		
		btnDelete = new JButton("Delete");
		btnDelete.setActionCommand("Delete");
		btnDelete.setBounds(308, 616, 86, 30);
		getContentPane().add(btnDelete);
		
		btnPrint = new JButton("Print");
		btnPrint.setActionCommand("Print");
		btnPrint.setBounds(402, 616, 86, 30);
		getContentPane().add(btnPrint);
		
		 

		btnAddMore = new JButton("Add Rows");
		btnAddMore.setMnemonic(KeyEvent.VK_A);
		btnAddMore.setEnabled(false);
		btnAddMore.setActionCommand("newRow");
		btnAddMore.setBounds(496, 616, 170, 30);
		getContentPane().add(btnAddMore);
		btnAddMore.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					if(vouNo!=0)
					{
						addNewRecord();
					}
				}
			}
			
		});
		
		
		btnSave = new JButton("Save");
		btnSave.setActionCommand("Save");
		btnSave.setBounds(791, 616, 86, 30);
		getContentPane().add(btnSave);
		btnSave.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{

					int recrodNo = saveData();
					recrodNo=lastno;
					
					if(option==1)
					{
						JOptionPane.showMessageDialog(JVVoucherAdd.this,"JV No. is "+recrodNo,"JV No.",JOptionPane.INFORMATION_MESSAGE);
					}

					option=0;
				}
			}
		});
		
		
		btnExit = new JButton("Exit");
		btnExit.setActionCommand("Exit");
		btnExit.setBounds(880, 616, 86, 30);
		getContentPane().add(btnExit);

	 	
		vou_no.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
 					clearAll(false);
					dispose();
					//System.exit(0);
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					int vouno = setIntNumber(vou_no.getText().toString());
					 
					if (vouno==0)
					{
						vou_no.setText("");
						vou_no.requestFocus();
					}
					
					 rcp = (RcpDto) jvDao.getVouDetail(vouno, loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
					
					//rcp = (RcpDto) bankDao.getVouDetail(vouno, loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
					if(rcp!=null)
					{
						
						JOptionPane.showMessageDialog(JVVoucherAdd.this,"Vou No. "+vouno,"Already Exists!!!!!",JOptionPane.INFORMATION_MESSAGE);						
						vou_no.setText("");
						vou_no.requestFocus();
					} 
					else
					{
						vou_date.requestFocus();
						vou_date.setSelectionStart(0);
					}
					 
					evt.consume();
				}
				
			}
		});
		
		
		vno.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
 					clearAll(false);
					dispose();
					//System.exit(0);
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					int vouno = setIntNumber(vno.getText().toString());
					 
					
					vno.setText("");
					rcp = (RcpDto) jvDao.getVouDetail(vouno, loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
					if(rcp==null)
					{
						clearAll(false);
						JOptionPane.showMessageDialog(JVVoucherAdd.this,"No Record for Vou No. "+vouno,"Record not found",JOptionPane.INFORMATION_MESSAGE);						
						
					} 
					else
					{
						fillData(rcp);
						fillItemData(rcp);
						totPara();
					}
					evt.consume();
				}
				
			}
		});
		
		

		
		vdate.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearAll(false);
					dispose();
					//System.exit(0);
					
				}
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{

					try
					{
						String date=vdate.getText();
						//bk = (BookDto) bank.getSelectedItem();
						invNoTableModel.getDataVector().removeAllElements();
						if(isValidDate(date))
						{
							
							Vector data = (Vector) jvDao.getJvList(sdf.parse(vdate.getText()), loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year());
							 
							if (data==null)
							{
								clearAll(false);
								JOptionPane.showMessageDialog(JVVoucherAdd.this,"No Record for Vou Date "+date,"Record not found",JOptionPane.INFORMATION_MESSAGE);							
							}
							else
							{
								fillInvTable(data);
							}
								
						}
						else
						{
							vdate.setValue(null);
							checkDate(vdate);
							vdate.requestFocus();
						}						
						
					}
					catch(Exception e)
					{
						System.out.println("vdate mein error hai "+e);
					}
					evt.consume();
				}
				
			}
		});		
		
		
		
		invNoTable.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				int row = invNoTable.getSelectedRow();
				if(evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					 
					
					rcp = (RcpDto) invNoTable.getValueAt(row, 2);
					Vector v = (Vector) invNoTable.getValueAt(row,3);
					fillData(rcp);
					fillItemData(v);
					evt.consume();
				}
				
			}
		});
		
		
		invNoTable.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}

			public void mouseClicked(MouseEvent e) 
			{
				
				int row = invNoTable.getSelectedRow();
				 
					
					rcp = (RcpDto) invNoTable.getValueAt(row, 2);
					Vector v = (Vector) invNoTable.getValueAt(row,3);
					fillData(rcp);
					fillItemData(v);
				 
				
			 
			}
		});		
		// ///////////////////////////////////////////////
		vou_no.setName("0");
		vou_date.setName("1");
		vnart_2.setName("2");
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 133, 195, 511);
		getContentPane().add(panel);
		

		vou_no.addKeyListener(keyListener); 
		vou_date.addKeyListener(keyListener);
		vnart_2.addKeyListener(keyListener);
		
		
		
		
		lastNo = new JLabel("0");
		lastNo.setForeground(Color.RED);
		lastNo.setBounds(291, 150, 51, 20);
		getContentPane().add(lastNo);
		//setting last no.
		lastNo.setText(String.valueOf(getLastNo()));
		
		
		
		label_3 = new JLabel("Last No:");
		label_3.setForeground(Color.RED);
		label_3.setBounds(228, 149, 65, 20);
		getContentPane().add(label_3);
		//chq_dt.addKeyListener(keyListener);

		
		
		
		JLabel label_1 = new JLabel("Total:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setForeground(Color.RED);
		label_1.setBounds(662, 559, 115, 20);
		getContentPane().add(label_1);
		
		totalcr = new JTextField();
		totalcr.setHorizontalAlignment(SwingConstants.RIGHT);
		totalcr.setEditable(false);
		totalcr.setBounds(880, 559, 83, 22);
		getContentPane().add(totalcr);
		
		
		totaldr = new JTextField();
		totaldr.setHorizontalAlignment(SwingConstants.RIGHT);
		totaldr.setEditable(false);
		totaldr.setBounds(791, 559, 83, 22);
		getContentPane().add(totaldr);
		
		
		JLabel lblDiff = new JLabel("Difference Amount:");
		lblDiff.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiff.setForeground(Color.RED);
		lblDiff.setBounds(678, 585, 184, 20);
		getContentPane().add(lblDiff);
		
		diffamt = new JTextField();
		diffamt.setText("");
		diffamt.setHorizontalAlignment(SwingConstants.RIGHT);
		diffamt.setEditable(false);
		diffamt.setBounds(880, 585, 83, 22);
		getContentPane().add(diffamt);
		
		clearAll(false);
		btnDelete.setEnabled(false);
		btnPrint.setEnabled(false);
		btnSave.setEnabled(false);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(214, 134, 763, 91);
		getContentPane().add(panel_1);
		
		
		
	

		
		btnAdd.addActionListener(this);
		btnDelete.addActionListener(this);
		btnPrint.addActionListener(this);
		btnAddMore.addActionListener(this);
		btnSave.addActionListener(this);
		btnExit.addActionListener(this);
	 

	}


	public void clearAll(boolean edit)
	{

		vou_no.setText("");
		vnart_2.setText("");
		vdate.setValue(null);
		checkDate(vdate);
		totalcr.setText("");
		totaldr.setText("");
		diffamt.setText("");
		
		vou_no.setEditable(edit);
		vou_date.setEditable(edit);
		vnart_2.setEditable(edit);
		
		btnSave.setBackground(null);
		
		
	 
		
		invNoTableModel.getDataVector().removeAllElements();
//		vouTableModel.getDataVector().removeAllElements();

		// /// clear item table data
//		vouTableModel.getDataVector().clear(); // Remove all TableData
		vouTableModel.getDataVector().removeAllElements();
		vouTableModel.fireTableDataChanged();

//		vouTableModel.fireTableStructureChanged();
		// /// genrate new row in item table/////////////
		vouTableModel.addRow(new Object[] {});
//		vouTableModel.addRow(new Object[] {"","","","","",0.00,"","","",0.00,""});

		vouTableModel.fireTableStructureChanged();
		
		vouTableUI();

		
		
		
		
		//setting last no.
		lastno=getLastNo();
		lastNo.setText(String.valueOf(lastno));
		vou_no.setText(String.valueOf(lastno+1));
		

		addRecord=false;
		addNewRow=false;
		sNo=0;
		
		
	}


	public void totPara() 
	{
		Vector col = null;
		totcr=0.00; 
		totdr=0.00; 
		Vector vouData = vouTableModel.getDataVector();
		try 
		{
			for (int i = 0; i < vouData.size(); i++) 
			{
				col = (Vector) vouData.get(i);
				if (col.get(5)!=null)
				  totdr+=setDoubleNumber(col.get(5).toString());
				if (col.get(9)!=null)
					  totcr+=setDoubleNumber(col.get(9).toString());
			}
			totalcr.setText(formatter.format(totcr));
			totaldr.setText(formatter.format(totdr));
			diffamt.setText(formatter.format(totdr-totcr));
 
		} 
		catch (Exception e) 
		{
			System.out.println("Error in totPara "+e);
		}
		
	}	
	
	public void fillData(RcpDto rcp)
	{
		// voucher header
		vouNo=rcp.getVou_no();
		vou_no.setText(String.valueOf(rcp.getVou_no()));
		vou_date.setText(sdf.format(rcp.getVou_date()));
//		vamount.setText(formatter.format(rcp.getVamount()));
		if(rcp.getVdbcr().equals("CR"))
		{
//			vdbcr.setSelectedIndex(0);
		}
		else
		{
//			vdbcr.setSelectedIndex(1);
		}

//		exp_code.setText(rcp.getExp_code());
//		name.setText(rcp.getExp_name());
		vnart_2.setText(rcp.getVnart2());
		btnAdd.setEnabled(false);
		btnAddMore.setEnabled(true);
		btnSave.setEnabled(false);
		btnDelete.setEnabled(true);
		btnPrint.setEnabled(true);

		
		
	}	

	
	public void fillItemData(RcpDto rp)
	{
		// Vou Detail Fill 
		
		vouTableModel.getDataVector().removeAllElements();
		Vector c = null;
		Vector data =rp.getVdetail();
		int size=data.size();
		for(int i =0;i<size;i++)
		{
			c = (Vector) data.get(i);
			vouTableModel.addRow(c);
			sNo=i;
			option=2;
			btnSave.setEnabled(true);
			vnart_2.setEditable(true);
		}
		
		
	}
	public void fillItemData(Vector data)
	{
		// Vou Detail Fill 
		
		vouTableModel.getDataVector().removeAllElements();
		Vector c = null;
		for(int i =0;i<data.size();i++)
		{
			c = (Vector) data.get(i);
			vouTableModel.addRow(c);
		}
		
		
	}



	public void fillInvTable(Vector data)
	{
		//invNoTableModel.getDataVector().removeAllElements();
		Vector c = null;
		int s = data.size();
		for(int i =0;i<data.size();i++)
		{
			c =(Vector) data.get(i);
			invNoTableModel.addRow(c);
		}
		
           if (s==0)
           {
       		for(int i =0;i<30;i++)
    		{
    			invNoTableModel.addRow(new Object[2][]);
    		}
        	   
        	   
           }
		
	}
		

	public ArrayList vouAdd() 
	{
		 
		Vector col = null;
		RcpDto rcp1 = null;
		ArrayList bankList = new ArrayList();
		
		
		 
		int vou = setIntNumber(vou_no.getText().trim());
		Vector vouData = vouTableModel.getDataVector();
		int size=vouData.size();
		if(option==1)
			size=size-1;
		try {
			for (int i = 0; i < size; i++) {
				col = (Vector) vouData.get(i);

				if((addNewRow && i>sNo) || (option==1 || option==2))
				{
					rcp1 = new RcpDto();
					rcp1.setDiv_code(loginDt.getDiv_code());
					rcp1.setVdepo_code(loginDt.getDepo_code());
					rcp1.setVou_no(vou);
					rcp1.setVou_lo("JV");
					rcp1.setVou_date(formatDate(vou_date.getText()));
					rcp1.setVnart2(vnart_2.getText());


					if (col.get(0)!=null)
					{
						rcp1.setBill_no(col.get(0).toString());
					}

					if (col.get(2)!=null)
					{
						rcp1.setVac_code(col.get(2).toString());
						rcp1.setExp_code(col.get(2).toString());
					}
					if (col.get(4)!=null)
						rcp1.setVnart1(col.get(4).toString());
					if (col.get(5)!=null)
					{
						rcp1.setVamount(setDoubleNumber(col.get(5).toString()));
						rcp1.setVdbcr("DR");
					}
					if (col.get(6)!=null)
						rcp1.setVbook_cd(setIntNumber(col.get(6).toString()));
					if (col.get(7)!=null)
						rcp1.setVgrp_code(setIntNumber(col.get(7).toString()));
					if (col.get(8)!=null)
						rcp1.setVbk_code(setIntNumber(col.get(8).toString()));
					if (col.get(9)!=null)
					{	if(rcp1.getVamount()==0.00)
					{
						rcp1.setVamount(setDoubleNumber(col.get(9).toString()));
						rcp1.setVdbcr("CR");
					}
					}

					rcp1.setVbook_cd(30);
					rcp1.setVbook_cd1(30);
					rcp1.setFin_year(loginDt.getFin_year());
					rcp1.setMkt_year(loginDt.getMkt_year());
					rcp1.setMnth_code(loginDt.getMnth_code());
					rcp1.setCreated_by(loginDt.getLogin_id());
					rcp1.setCreated_date(new Date());
					rcp1.setModified_by(loginDt.getLogin_id());
					rcp1.setModified_date(new Date());
					if(option==2)
						rcp1.setSerialno(setIntNumber(col.get(10).toString()));
					else
						rcp1.setSerialno(i+1);
						
					// new fields for jv generation for bills
					rcp1.setDoc_type(1); // sub div

					if (col.get(1)!=null)
					{
						rcp1.setDoc_type(col.get(1).toString().equalsIgnoreCase("T") ? 2 :col.get(1).toString().equalsIgnoreCase("G") ? 3 :1); // subdiv
					}
					rcp1.setBill_date(null);
					rcp1.setBill_amt(0.00);

					bankList.add(rcp1);
				}
			
				}
 
			 
			 
		} catch (Exception e) {
			System.out.println("Error in vouAdd "+e);
		}
		
		
		return bankList;

	}



		
		
		
		public int saveData() 
		{
			int recordNo=0;
			ArrayList l = vouAdd();
			if (option==1  || addNewRow)
			{
				recordNo = jvDao.addLed(l);
				btnAdd.setEnabled(true);
				btnAddMore.setEnabled(false);
				btnDelete.setEnabled(false);
				btnPrint.setEnabled(false);
				btnSave.setEnabled(false);
				vou_no.setEditable(false);
				invNoTable.setEnabled(true);
				vno.setEnabled(true);
				vdate.setEnabled(true);

				clearAll(false);
				vouTable.setEnabled(true);

			}
			if(option==2)
			{
				totPara();
				if(totcr==totdr)
				{
					recordNo = jvDao.updateLed(l);
					btnAdd.setEnabled(true);
					btnAddMore.setEnabled(false);
					btnDelete.setEnabled(false);
					btnPrint.setEnabled(false);
					btnSave.setEnabled(false);
					vou_no.setEditable(false);
					invNoTable.setEnabled(true);
					vno.setEnabled(true);
					vdate.setEnabled(true);

					clearAll(false);

				}
				else
				{
					alertMessage(JVVoucherAdd.this, "Debit and Credit Amount Totals are not matched Check...?");
					vouTable.requestFocus();
					vouTable.setValueAt(0.00, 0, 5);
					vouTable.changeSelection(0, 5, false, false);
					vouTable.editCellAt(0, 5);
					option=2;
				}
			}
			return recordNo;


		}	

		
		
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		if(e.getActionCommand().equalsIgnoreCase("add"))
		{
			
			btnAdd.setEnabled(false);
			btnDelete.setEnabled(false);
			btnPrint.setEnabled(false);
			btnAddMore.setEnabled(false);
			btnSave.setEnabled(true);
			clearAll(true); 
			invNoTable.setEnabled(false);
			vno.setEnabled(false);
			vdate.setEnabled(false);
			
			//vou_date.requestFocus();
			vou_no.setEditable(true);
			vou_no.requestFocus();
			option=1;
		
		}
		
		if(e.getActionCommand().equalsIgnoreCase("newrow"))
		{
			if(vouNo!=0)
			{
				addNewRecord();
			}
		}		

		
		if(e.getActionCommand().equalsIgnoreCase("delete"))
		{
			 int h=0;
			 int answer = 0;
			      answer = JOptionPane.showConfirmDialog(JVVoucherAdd.this, "Are You Sure : ");
			    if (answer == JOptionPane.YES_OPTION) {
			      // User clicked YES.
			    	h = jvDao.deleteLed(loginDt.getDiv_code(),loginDt.getDepo_code(),loginDt.getFin_year(),loginDt.getLogin_id(),setIntNumber(vou_no.getText()));
			    	 

			    	btnAdd.setEnabled(true);
					invNoTable.setEnabled(true);
					vno.setEnabled(true);
					vdate.setEnabled(true);
					option=0;
					clearAll(false);
			    } else if (answer == JOptionPane.NO_OPTION) {
			      // User clicked NO.
			
			
				
  	    	}  // else part end 
		}
		
		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			int recrodNo = saveData();
			
			if(option==1)
			{
				JOptionPane.showMessageDialog(JVVoucherAdd.this,"JV No. is "+recrodNo,"JV No.",JOptionPane.INFORMATION_MESSAGE);
			}
			
			option=0;
		}
		
 
		if(e.getActionCommand().equalsIgnoreCase("print"))
		{
			int vbookCD = 30; // cashbook
			
			CashOpt copt = new CashOpt("JV ","JV Voucher Print",vouNo,vbookCD,"");
			copt.setVisible(true);
			
		}

		
		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			option=0;
			 
			btnAdd.setEnabled(true);
			btnAddMore.setEnabled(true);
			btnDelete.setEnabled(false);
			btnPrint.setEnabled(false);
			invNoTable.setEnabled(true);
			btnPrint.setEnabled(true);
			vno.setEnabled(true);
			vdate.setEnabled(true);
			clearAll(false);
			dispose();
		}		

	}
	
	
	
	public void addNewRecord()
	{
		try
		{
			addNewRow=true;
			addRecord=true;
			option=3; 
			vouTableModel.addRow(new Object[] {});
			
			int rowCount =vouTableModel.getRowCount(); 

			vouTable.requestFocus();
			vouTable.changeSelection(rowCount-1, 0, false, false);
			vouTable.editCellAt(rowCount-1, 0);

			
			btnAddMore.setEnabled(false); 
			btnSave.setEnabled(true);
			btnAdd.setEnabled(false);
			btnAddMore.setEnabled(false);
			btnDelete.setEnabled(false);
			btnPrint.setEnabled(false);
		}
		catch(Exception ex)
		{

		}
		
	}

	
	public void vouTableUI()
	{
		
		vouTable.setColumnSelectionAllowed(false);
		vouTable.setCellSelectionEnabled(false);
		vouTable.getTableHeader().setReorderingAllowed(false);
		vouTable.getTableHeader().setResizingAllowed(false);
		vouTable.setRowHeight(20);
		vouTable.getTableHeader().setPreferredSize(new Dimension(25, 25));
		vouTable.setFont(new Font("Tahoma", Font.PLAIN, 11));

		vouTable.getColumnModel().getColumn(0).setPreferredWidth(80); // ref no
//		vouTable.getColumnModel().getColumn(1).setPreferredWidth(40); // account tyoe
		vouTable.getColumnModel().getColumn(1).setPreferredWidth(0);
		vouTable.getColumnModel().getColumn(1).setMinWidth(0); // account type 
		vouTable.getColumnModel().getColumn(1).setMaxWidth(0);  

//		vouTable.getColumnModel().getColumn(1).setPreferredWidth(40); // account tyoe
		vouTable.getColumnModel().getColumn(2).setPreferredWidth(100); // account
		vouTable.getColumnModel().getColumn(3).setPreferredWidth(250); // account name
		vouTable.getColumnModel().getColumn(4).setPreferredWidth(300); // nar
		vouTable.getColumnModel().getColumn(5).setPreferredWidth(100); // dr amt
		
		vouTable.getColumnModel().getColumn(6).setPreferredWidth(0);
		vouTable.getColumnModel().getColumn(6).setMinWidth(0); // vbook_cd 
		vouTable.getColumnModel().getColumn(6).setMaxWidth(0);  
		
		vouTable.getColumnModel().getColumn(7).setPreferredWidth(0);
		vouTable.getColumnModel().getColumn(7).setMinWidth(0); // vgrp_code
		vouTable.getColumnModel().getColumn(7).setMaxWidth(0);   

		vouTable.getColumnModel().getColumn(8).setPreferredWidth(0);
		vouTable.getColumnModel().getColumn(8).setMinWidth(0); // vbk_code
		vouTable.getColumnModel().getColumn(8).setMaxWidth(0);
		
		vouTable.getColumnModel().getColumn(9).setPreferredWidth(100); // cr amt
		
		

		vouTable.getColumnModel().getColumn(10).setPreferredWidth(0);
		vouTable.getColumnModel().getColumn(10).setMinWidth(0); // serial no
		vouTable.getColumnModel().getColumn(10).setMaxWidth(0);

		
		vouTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		vouTable.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
		
		vouTable.setDefaultEditor(String.class, new OverWriteTableCellEditor());
		//vouTable.setDefaultEditor(Double.class, new OverWriteTableCellEditor());
		
		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		vouTable.getColumnModel().getColumn(5).setCellEditor(new NumberTableCellEditor(dobl));	

		dobl = new JDoubleField();
		dobl.setHorizontalAlignment(SwingConstants.RIGHT);
		dobl.setMaxLength(15); //Set maximum length             
		dobl.setPrecision(2); //Set precision (1 in your case)              
		dobl.setAllowNegative(false); //Set false to disable negatives
		vouTable.getColumnModel().getColumn(9).setCellEditor(new NumberTableCellEditor(dobl));	

/*		table_pcode = new JFormattedTextField();
		checkYN(table_pcode,2,"DCMTG");
		table_pcode.setSelectionStart(0);
		vouTable.getColumnModel().getColumn(1).setCellEditor(new NumberTableCellEditor(table_pcode));
*/
		
	}
	
	
	public int getLastNo()
	{
		return jvDao.getVouNo(loginDt.getDepo_code(),loginDt.getDiv_code(), loginDt.getFin_year());
	}
}


