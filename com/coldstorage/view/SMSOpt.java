package com.coldstorage.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.coldstorage.util.JIntegerField;
import com.coldstorage.util.NewSms;


public class SMSOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName ;
	private JButton sendButton,exitButton;
	 
	String ClassNm;
	 
 

	private JTextField search;
	private JLabel lblSearch;
 
	String mnthname="";
	private SwingWorker<?,?> worker;
	private ImageIcon loadingIcon;
	private JLabel lodingLabel,labelcount;
	 
	private JTextArea smsText;
	private JScrollPane smsPane;
	private int numOfCharactersInt;
	private String characterTyped;
	
	public  SMSOpt(String classnm,String repNm)
	{
		 
		//setUndecorated(true);
		setResizable(false);
		setSize(586, 475);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		 
		reportName = new JLabel("SMS");
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(132, 44, 303, 20);
		getContentPane().add(reportName);
		
		
	 
		
		sendButton = new JButton("Send");
		sendButton.setBounds(407, 388, 70, 30);
		getContentPane().add(sendButton);
		sendButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("Excel");
					dispose();
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_LEFT) 
				{
					exitButton.setBackground(null);
					sendButton.setBackground(null);
					evt.consume();
				}
			
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					sendButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
			}
		});
		
		
		exitButton = new JButton("Exit");
		exitButton.setBounds(487, 388, 70, 30);
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
					exitButton.setBackground(null);
					sendButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		
	 
		
		loadingIcon = new ImageIcon(getClass().getResource("/images/loader.gif"));

		lodingLabel = new JLabel(loadingIcon);
		lodingLabel.setBounds(346, 378, 48, 48);
		lodingLabel.setVisible(false);
		getContentPane().add(lodingLabel);


		
	 
		
		smsText = new JTextArea();
		smsText.setLineWrap(true);
		smsText.setWrapStyleWord(true);
		smsPane = new JScrollPane(smsText, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		smsPane.setBounds(28, 125, 531, 252);
		getContentPane().add(smsPane);
		smsText.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				    characterTyped = smsText.getText();//whatever the user types
	                numOfCharactersInt = characterTyped.length();//length of the string
	               // numOfCharactersString = Integer.toString(numOfCharactersInt);//converts the int to string
	                //password.setText(numOfCharactersString);//sets the textfield to the number of characters entered
	                int smscount=0;
	                double scount=0;
	                smscount=numOfCharactersInt/160;
	                scount = numOfCharactersInt/10;
	                
	                if(smscount==0)
	                	smscount=1;
	                if(numOfCharactersInt>160 && scount>0 )
	                	  smscount++;
	                	
	                	
	                	
	                //if(scount>0)
	                //	smscount++;
	                //if(smscount==0)
	                //	smscount=1;
	                
	                labelcount.setText("No of Characters : "+numOfCharactersInt+"/"+smscount);
			}
			
			 
			public void keyReleased(KeyEvent e) {}
			
			 
			public void keyPressed(KeyEvent e) {}
		});
		 
		exitButton.addActionListener(this);
		sendButton.addActionListener(this);
		
		search = new JIntegerField(10);
		search.setBounds(189, 85, 269, 22);
		getContentPane().add(search);
		
	 
		
		lblSearch = new JLabel("Mobile No:");
		lblSearch.setBounds(117, 86, 95, 20);
		getContentPane().add(lblSearch);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(28, 180, 401, 58);
		getContentPane().add(panel);
		
		labelcount = new JLabel("");
		labelcount.setBounds(38, 386, 198, 20);
		getContentPane().add(labelcount);
		

		 	
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 11, 560, 415);
		getContentPane().add(panel_1);
		
		
		setAlwaysOnTop(true);
		
	}

	 
 
	
	 public void resetAll()
	 {

		 try
		 {
			
			 
			 //smonth.setSelectedIndex(0);
		 }
		 catch(Exception ez)
		 {
			 ez.printStackTrace();
		 }
		 search.setEditable(true);
		 search.setText("");
	 }
	 

	 public void callPrint(String btnnm )
	 {
		 try
		 {
				submitForm(btnnm);

		 }
		 catch(Exception ez)
		 {
			 ez.printStackTrace();
		 }

	 }

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			 resetAll();
			dispose();
		}
		
	    if(e.getActionCommand().equalsIgnoreCase("Send"))
	    {
	    	String btnnm = e.getActionCommand();

	    	callPrint(btnnm);
			
  
		}

	    
	    
	    
	}
	
	
	

	public void submitForm(String buttonName)
	{
		lodingLabel.setVisible(true);
		sendButton.setEnabled(false);
		worker = new NumberWorker(buttonName);
		worker.execute();
	}			



	public class NumberWorker extends SwingWorker<String, Object> 
	{
		String buttoName=null;
		
		NumberWorker(String btnName)
		{
			buttoName=btnName;
		}
		
		protected String doInBackground() throws Exception 
		{
			try
			{
					 NewSms sms = new NewSms();
					 String mobile=search.getText();
					 String message=smsText.getText();
						  
						 System.out.println(message);
						 if(mobile.length()==10)
						    sms.sendSms(mobile, senderId, message);
					 

			
			}
			catch(Exception ez)
			{
				ez.printStackTrace();
			}
			System.out.println("after sending of all SMS");

			return null;
		}


		protected void done() 
		{
			try 
			{
				worker.cancel(true);
				lodingLabel.setVisible(false);
				sendButton.setEnabled(true);
				alertMessage(SMSOpt.this, "SMS sent successfully");
				 resetAll();
				 //dispose();

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

		}
	}
}


