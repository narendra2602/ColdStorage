package com.coldstorage.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.coldstorage.dao.InvPrintDAO;
import com.coldstorage.dto.CmpInvDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.print.GenerateGatePass;
import com.coldstorage.print.GenerateInvoiceGST;
import com.coldstorage.print.GenerateOutward;
import com.coldstorage.print.GenerateReceipt;
import com.coldstorage.util.JIntegerField;

public class InvOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName,lblMarketingYear ;
	private JLabel lblStartingNo;
	private JButton viewButton,exitButton,printButton;
	private KeyListener keyListener;
	private JComboBox fyear;
	String ClassNm,repname;
	String optn;
	private JIntegerField sno;
	private JIntegerField eno;  
	private JLabel lblEndingNo;
	private JRadioButton rdbtnpack,rdbtninv,doc39,doc40;
	private JLabel totinv;
	private JLabel lblSelect;
	private JComboBox companyCombo;	 
	private CmpInvDto cmp;
	InvPrintDAO ido;  
	private NumberFormat nf;
	YearDto yd;
	int doctp;
	public  InvOpt(String nm,String repNm)
	{
		 
		 //nf = new DecimalFormat("##########");
		// nf= NumberFormat.get
		optn="2";
		doctp=43;
		ClassNm=nm;
		repname=repNm;
		//setUndecorated(true);
		setResizable(false);
		setSize(427, 314);	
		setLocationRelativeTo(null);
		ido = new InvPrintDAO();
		System.out.println("nm and repnm "+nm+" repnm "+repNm);
		
	       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	        
//	        this.setUndecorated(true);
	        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

			arisleb.setVisible(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 16));
		reportName.setBounds(48, 31, 303, 20);
		getContentPane().add(reportName);
		
		lblMarketingYear = new JLabel("Financial Year:");
		lblMarketingYear.setBounds(65, 116, 110, 20);
		getContentPane().add(lblMarketingYear);


		
		fyear = new JComboBox(loginDt.getFyear());
		fyear.setBounds(215, 116, 136, 23);
		getContentPane().add(fyear);
		fyear.setActionCommand("year");
		
		lblStartingNo = new JLabel();
		lblStartingNo.setBounds(65, 147, 125, 20);
		getContentPane().add(lblStartingNo);
		
 	
		sno = new JIntegerField(8);
		sno.setBounds(215, 148, 136, 22);
		sno.setName("1");
		sno.setHorizontalAlignment(SwingConstants.LEFT);
		sno.setSelectionStart(0);
		getContentPane().add(sno);
		
		 
		
/*		lblEndingNo = new JLabel("Ending No:");
		lblEndingNo.setBounds(65, 178, 95, 20);
		getContentPane().add(lblEndingNo);
		
		eno = new JIntegerField(8);
		eno.setBounds(215, 179, 136, 22);
		eno.setName("2");
		eno.setHorizontalAlignment(SwingConstants.LEFT);
		eno.setSelectionStart(0);
		getContentPane().add(eno);
*/		
		
		totinv = new JLabel("");
		totinv.setForeground(Color.RED);
		totinv.setBounds(65, 70, 310, 20);
		//totinv.setBounds(67, 19, 334, 20);
		getContentPane().add(totinv);
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		keyListener = new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					JIntegerField textField = (JIntegerField) keyEvent.getSource();
					int id = Integer.parseInt(textField.getName());
					switch (id) 
					{
					case 1: 
						
	/*					eno.requestFocus();
						break;
*/
					case 2: 
					 	int vno=setIntNumber(sno.getText());
					 	if(vno==0)
					 		sno.requestFocus();
					 	else
					 	{
					 		viewButton.requestFocus();
					 		viewButton.setBackground(Color.blue);
					 	}
						break;

					}
					keyEvent.consume();
				}
				if (key == KeyEvent.VK_ESCAPE) 
				{
					resetAll();
					dispose();
					keyEvent.consume();
				}
 				
			}
		};

		sno.addKeyListener(keyListener);
//		eno.addKeyListener(keyListener);
		
		sno.addFocusListener(myFocusListener);
		
		viewButton = new JButton("View");
		viewButton.setBounds(55, 224, 90, 30);
		viewButton.setMnemonic(KeyEvent.VK_V);
		viewButton.setIcon(viewIcon);
		getContentPane().add(viewButton);
		viewButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
				 	int vno=setIntNumber(sno.getText());
				 	if(vno==0)
				 		sno.requestFocus();
				 	else
				 		callPrint("View");
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					printButton.setBackground(null);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(276, 224, 90, 30);
		exitButton.setMnemonic(KeyEvent.VK_E);
		exitButton.setIcon(exitIcon);
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
					printButton.requestFocus();
					printButton.setBackground(Color.blue);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});

		printButton = new JButton("Print");
		printButton.setBounds(166, 224, 90, 30);
		printButton.setMnemonic(KeyEvent.VK_P);
		printButton.setIcon(printIcon);
		getContentPane().add(printButton);
		printButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("Print");
					//dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					viewButton.setBackground(null);
					printButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					printButton.setBackground(null);
					viewButton.setBackground(null);
					evt.consume();
				}
			}
		});
		

		
		lblSelect = new JLabel("Select:");
		lblSelect.setVisible(false);
		lblSelect.setBounds(65, 88, 95, 20);
		getContentPane().add(lblSelect);
 		 
/*		companyCombo = new JComboBox(loginDt.getCmpList());
		companyCombo.setVisible(false);
		companyCombo.setActionCommand("company");
		companyCombo.setBounds(215, 86, 136, 23);
		getContentPane().add(companyCombo);
		
*/	     
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(10, 11, 401, 262);
		getContentPane().add(panel_1);

		
		exitButton.addActionListener(this);
		viewButton.addActionListener(this);
	    printButton.addActionListener(this);
		fyear.addActionListener(this);
		
		// / setting default focus request
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e)
			{
				sno.requestFocus();
			}
			
			public void windowClosed(WindowEvent e)
			{
				resetAll();
			}

		});		
		 
		 
		 
		setAlwaysOnTop(true);
		
		
		
	}

	 public void resetAll()
	 {
		  		fyear.setSelectedIndex(0);
		  		sno.setText("");
//		  		eno.setText("");
		  		exitButton.setBackground(null);
		  		printButton.setBackground(null);
				viewButton.setBackground(null);;
				optn="2";
				doctp=43;
				sno.requestFocus();
				sno.selectAll();

		 
	 }
	 
	 public void callPrint(String btnnm )
	 {
		 try
			{
			 	YearDto yd = (YearDto) fyear.getSelectedItem();
//			 	cmp = (CmpInvDto) companyCombo.getSelectedItem();
			 	int vno=setIntNumber(sno.getText());
			 	if(vno==0)
			 		sno.requestFocus();
			 	else
			 	{
			 		doctp=40;
			 		if(repname.contains("Inward Printing"))
			 		{
			 			doctp=60;
			 			new GenerateInvoiceGST(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sno.getText(),sno.getText(),btnnm,loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);
			 		}
			 		else if(repname.contains("Transfer Printing"))
			 		{
			 			doctp=41;
			 			new GenerateInvoiceGST(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sno.getText(),sno.getText(),btnnm,loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);
			 		}
			 		else if(repname.contains("Outward Printing"))
			 		{
			 			doctp=40;
			 			new GenerateOutward(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sno.getText(),sno.getText(),btnnm,loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);
			 		}
			 		else if(repname.contains("Gate Pass Printing"))
			 		{
			 			doctp=50;
			 			new GenerateGatePass(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sno.getText(),sno.getText(),btnnm,loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);
			 		}
			 		else if(repname.contains("WrittenOff Printing"))
			 		{
			 			doctp=66;
			 			new GenerateInvoiceGST(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sno.getText(),sno.getText(),btnnm,loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);
			 		}
			 		else if(repname.contains("Receipt Printing"))
			 		{
			 			doctp=10;
			 			new GenerateReceipt(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sno.getText(),sno.getText(),btnnm,loginDt.getDrvnm(),"","2",loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);
			 		}
			 	}
	 			dispose();
			}
		 catch(Exception e)
		 {
			 System.out.println("error "+e); 
			 e.printStackTrace();
		 }
	 }
	 	 

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
			
		if (e.getActionCommand().equals("1") || e.getActionCommand().equals("2") )
		{
			optn=e.getActionCommand();
		}
		
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			resetAll();
			dispose();
		}
		

		if(e.getActionCommand().equalsIgnoreCase("Print"))
		 {
		 	int vno=setIntNumber(sno.getText());
		 	if(vno==0)
		 		sno.requestFocus();
		 	else
				callPrint("Print");
		 }

		if(e.getActionCommand().equalsIgnoreCase("View"))
		 {
		 	int vno=setIntNumber(sno.getText());
		 	if(vno==0)
		 		sno.requestFocus();
		 	else
				callPrint("View");
		 }

		
		 
	}
	
	public void setTotinv() {
		if(repname.contains("Inward")) 
		{
			doctp=60;
			lblStartingNo.setText("Inward No:");
		}
		else if(repname.contains("Transfer")) 
		{
			doctp=41;
			lblStartingNo.setText("Transfer No:");
		}
		else if(repname.contains("Outward")) 
		{
			doctp=40;
			lblStartingNo.setText("Outward No:");
		}
		else if(repname.contains("Gate Pass")) 
		{
			doctp=50;
			lblStartingNo.setText("Gate Pass No:");
		}
		else if(repname.contains("WrittenOff"))
		{
			doctp=66;
			lblStartingNo.setText("WrittenOff No:");

		}

		else if(repname.contains("Receipt"))
		{
			doctp=10;
			lblStartingNo.setText("Receipt No:");

		}
 		
		String inv = ido.getTotalNo(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getFr_date(),loginDt.getTo_date(),loginDt.getMnthName(),doctp);
		totinv.setText(inv.trim());
	}	

	
	public void setVisible(boolean b)
	{
		
		setTotinv();

		sno.requestFocus();
		sno.selectAll();

		super.setVisible(b);
	}

	
}




	 