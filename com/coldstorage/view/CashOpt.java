package com.coldstorage.view;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import com.coldstorage.dto.YearDto;
import com.coldstorage.util.JIntegerField;

public class CashOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName,lblMarketingYear ;
	private JLabel lblStartingNo;
	private JButton viewButton,exitButton,printButton,fileButton,pdfButton;
	private KeyListener keyListener;
	private JComboBox fyear;
	String ClassNm,repnm;
	String optn;
	private JIntegerField sno;
	private JIntegerField eno;  
	private JLabel lblEndingNo;
	 
	
	private NumberFormat nf;
	YearDto yd;
	int doctp,depo,wdepo;
	String type;
	public  CashOpt(String nm,String reportNm,int invNo,int vbookCd,String type)
	{
		 
		this.type = type;
		doctp=vbookCd;
		ClassNm=nm;
		depo=invNo;
		repnm=reportNm;
		//setUndecorated(true);
		setResizable(false);
		setSize(402, 267);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		reportName = new JLabel(reportNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(48, 19, 303, 20);
		getContentPane().add(reportName);
		
		lblMarketingYear = new JLabel("Financial Year:");
		lblMarketingYear.setBounds(49, 70, 110, 20);
		getContentPane().add(lblMarketingYear);


		
		fyear = new JComboBox(loginDt.getFyear());
		fyear.setBounds(199, 70, 136, 23);
		getContentPane().add(fyear);
		fyear.setActionCommand("year");
		
	    lblStartingNo = new JLabel("Starting No:");
		lblStartingNo.setBounds(49, 101, 95, 20);
		getContentPane().add(lblStartingNo);
 	
		
		
		sno = new JIntegerField(5);
		sno.setBounds(199, 102, 136, 22);
		sno.setHorizontalAlignment(SwingConstants.LEFT);
		sno.setName("1");
		sno.setSelectionStart(0);
		getContentPane().add(sno);
		
		 
		
		lblEndingNo = new JLabel("Ending No:");
		lblEndingNo.setBounds(49, 132, 95, 20);
		getContentPane().add(lblEndingNo);
		
		eno = new JIntegerField(5);
		eno.setBounds(199, 133, 136, 22);
		eno.setHorizontalAlignment(SwingConstants.LEFT);
		eno.setName("2");
		eno.setSelectionStart(0);
		getContentPane().add(eno);
		
		
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
						
						eno.requestFocus();
						break;

					case 2: 
						viewButton.requestFocus();
						viewButton.setBackground(Color.blue);
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
		eno.addKeyListener(keyListener);
		
		viewButton = new JButton("View");
		viewButton.setBounds(14, 187, 70, 30);
		getContentPane().add(viewButton);
		viewButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("View");
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					fileButton.requestFocus();
					fileButton.setBackground(Color.blue);
					printButton.setBackground(null);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});

		
		fileButton = new JButton("File");
		fileButton.setBounds(88, 187, 70, 30);
		getContentPane().add(fileButton);
		fileButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("File");
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					viewButton.requestFocus();
					viewButton.setBackground(Color.blue);
					fileButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}

				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					printButton.requestFocus();
					printButton.setBackground(Color.blue);
					fileButton.setBackground(null);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(307, 187, 70, 30);
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
					fileButton.setBackground(null);
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(24, 55, 346, 115);
		getContentPane().add(panel);

		
		printButton = new JButton("Print");
		printButton.setBounds(162, 187, 70, 30);
		getContentPane().add(printButton);
		printButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("Print");
					dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					fileButton.requestFocus();
					fileButton.setBackground(Color.blue);
					viewButton.setBackground(null);
					printButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					pdfButton.requestFocus();
					pdfButton.setBackground(Color.blue);
					fileButton.setBackground(null);
					printButton.setBackground(null);
					viewButton.setBackground(null);
					evt.consume();
				}
			}
		});
		
		
		pdfButton = new JButton("PDF");
		pdfButton.setBounds(236, 187, 70, 30);
		getContentPane().add(pdfButton);
		
		pdfButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("PDF");
					dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					printButton.requestFocus();
					printButton.setBackground(Color.blue);
					viewButton.setBackground(null);
					pdfButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					exitButton.requestFocus();
					exitButton.setBackground(Color.blue);
					fileButton.setBackground(null);
					pdfButton.setBackground(null);
					viewButton.setBackground(null);
					evt.consume();
				}
			}
		});

		
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
		
		 
		
		//Group the radio buttons.
	    ButtonGroup igroup = new ButtonGroup();

 
		fileButton.addActionListener(this);
		exitButton.addActionListener(this);
		viewButton.addActionListener(this);
	    printButton.addActionListener(this);
	    pdfButton.addActionListener(this);
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
		  		eno.setText("");
		  		exitButton.setBackground(null);
		  		printButton.setBackground(null);
		  		pdfButton.setBackground(null);
				viewButton.setBackground(null);;
				sno.requestFocus(); 
				optn="2";
 	 
	 }
	 
	 public void callPrint(String btnnm )
	 {
		 try
		 {
			 YearDto yd = (YearDto) fyear.getSelectedItem();
			 System.out.println("button name "+btnnm+" doctp "+doctp);
			 int div=loginDt.getDiv_code();
			 if (btnnm.equalsIgnoreCase("PDF") && (doctp<31 || doctp==195 || doctp==95))
				{
				 if(doctp==30)
					 div=4;
				 else if(doctp==95 || doctp==195)
					 div=4;
				 else if(doctp>10)
					 div=51;
				 if(repnm.contains("Cheque Return"))
					 div=4;
					 
				  else if(loginDt.getDepo_code()!=8)
					 type="D";
				 
					
				 
				 System.out.println("TYPE IS IN PDF  "+type);
/*				 if(doctp==195 || doctp==95)
					new GenerateDebitNotePrint(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(div),sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),type,loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,loginDt.getCmp_code());
					 else
					new GeneratePaymentReceipt(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(div),sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),type,loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,loginDt.getCmp_code());
*/
				}
			 else if(doctp==10)
			 {
//				 new CashVouPrint(yd.getYearcode(),loginDt.getDiv_code(),loginDt.getDepo_code(),sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),doctp,type);
			 }
				 
			 else if(doctp==30)
			 {
//				 new JvVouPrint(yd.getYearcode(),loginDt.getDiv_code(),loginDt.getDepo_code(),sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),doctp,type);
			 }
			 else if(doctp==300)
			 {
//				 new JvVouPrint(yd.getYearcode(),4,loginDt.getCmp_code(),sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),30,type);
			 }
				 
			 
			 else if(doctp==20 || doctp==21 || doctp==22 || doctp==99)
			 {
				 wdepo=loginDt.getDepo_code();
				 if (type.equalsIgnoreCase("CC"))  // for ho office entry
				 {
					 wdepo=depo+99;
					 type="C";
				 }

				 if (type.startsWith("ZZ") || type.startsWith("YY") || type.startsWith("XX"))  // for ho office entry
				 {
					 wdepo=depo+99;
				 }

				 System.out.println("TYPE IS IN PRINT  "+type);
//				 new BankVouPrint(yd.getYearcode(),loginDt.getDiv_code(),wdepo,sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),doctp,type,loginDt.getCmp_code());
			 }

			 else if(doctp==60 || doctp==61 || doctp==95 || doctp==195)  // 195 for new debit note gst
			 {
//				 new PurVouPrint(yd.getYearcode(),loginDt.getDiv_code(),loginDt.getDepo_code(),sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),doctp,type);
			 }
			 
			 else if(doctp==56 || doctp==58)
			 {
//				 new InfiltrationPrint(yd.getYearcode()+"", loginDt.getDepo_code()+"", loginDt.getDiv_code()+"", sno.getText(),eno.getText(), btnnm, loginDt.getDrvnm(), loginDt.getPrinternm(), doctp, loginDt.getBrnnm(), loginDt.getDivnm());
			 }
			 
			 else if(doctp==40 || doctp==67 || doctp==66 || doctp==38 ) // Sample Invoice/Transfer/Written  Invoice Printing 
			 {
///				 new GenerateSampleInvoice(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),optn,loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);

			 }
			 
			 else if(doctp==50 ) // order requisition  Invoice Printing 
			 {
///				 new GenerateOrderInvoice(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),optn,loginDt.getMnth_code(),doctp,loginDt.getBrnnm(),loginDt.getDivnm(),null,0);

			 }
		 
			 if(btnnm.equalsIgnoreCase("File"))
				 JOptionPane.showMessageDialog(CashOpt.this,"Voucher File is Generated : c:\\Report\\"+sno.getText()+".txt","Voucher File",JOptionPane.INFORMATION_MESSAGE);

			// if(!btnnm.equalsIgnoreCase("Print"))
				

			 
			 resetAll();
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
		System.out.println("Enter key pressed "+e.getActionCommand());
		
		
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			resetAll();
			dispose();
		}
		
	    if(e.getActionCommand().equalsIgnoreCase("View") ||  e.getActionCommand().equalsIgnoreCase("File") ||  e.getActionCommand().equalsIgnoreCase("Print") ||  e.getActionCommand().equalsIgnoreCase("PDF"))
	    {
			try
			{
				String btnnm = e.getActionCommand();
				callPrint(btnnm);
				
				//CashVouPrint cp = new CashVouPrint(loginDt.getDrvnm(), loginDt.getPrinternm(),vlist,loginDt.getDepo_code());
/*				YearDto yd = (YearDto) fyear.getSelectedItem();
				Class<?> clazz = Class.forName(ClassNm);
				// create an instance
				 
				Constructor<?> constructor = clazz.getConstructor(String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class);
				Object ob = (Object)constructor.newInstance(String.valueOf(yd.getYearcode()),String.valueOf(loginDt.getDepo_code()),String.valueOf(loginDt.getDiv_code()),sno.getText(),eno.getText(),btnnm,loginDt.getDrvnm(),loginDt.getPrinternm(),optn);
				resetAll();
				dispose();*/
			}
			catch(Exception ez)
			{
				System.out.println("error");
			}

		}
		
		 
	}
	
	
	public void setVisible(boolean b)
	{
		if(doctp<31)
			pdfButton.setEnabled(true);
		else if(doctp==195)
			pdfButton.setEnabled(true);
		else if(doctp==95)
			pdfButton.setEnabled(true);
		else
			pdfButton.setEnabled(false);
		super.setVisible(b);
			
			
	}
}




	 