package com.coldstorage.email;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.coldstorage.util.FileReaderUtility;
import com.coldstorage.util.NewSms;
import com.coldstorage.util.SendMail;
import com.coldstorage.view.BaseClass;
import com.itextpdf.text.Rectangle;

public class EmailLedger extends BaseClass implements ActionListener {


	private static final long serialVersionUID = 1L;
	private JLabel label_12;
	private JLabel branch;
	private JLabel lblDispatchEntry;
	private JPanel panel_2;
	private JButton sendButton,exitButton,smsButton;
	private JTextField email;
	private JLabel lblEmail;
	private JTextArea textArea;
	private ByteArrayOutputStream outputStream; 
	private String reportName;
	public EmailLedger(String partyEmail,String fileName,String reportName,Rectangle pageSize,float fontSize,ByteArrayOutputStream outputStream)
	{
		
		
		
		this.reportName=reportName;
		this.outputStream=outputStream;
		//new AccountOpt(null,null).setVisible(false);
    	//new AccountOpt().dispose();
		//setUndecorated(true);
		setResizable(false);
		setSize(1024, 768);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		///////////////////////////////////////////////////////////////////////////
		lblFinancialAccountingYear = new JLabel(loginDt.getFooter());
		lblFinancialAccountingYear.setForeground(Color.BLACK);
		lblFinancialAccountingYear.setBounds(400, 672, 477, 15);
		getContentPane().add(lblFinancialAccountingYear);


//		JLabel promleb = new JLabel(promLogo);
//		promleb.setBounds(10, 670, 35, 35);
		getContentPane().add(arisleb);

		label_12 = new JLabel((Icon) null);
		label_12.setBounds(10, 649, 35, 35);
		getContentPane().add(label_12);

		branch = new JLabel(loginDt.getBrnnm());
		branch.setForeground(Color.BLACK);
		branch.setFont(new Font("Tahoma", Font.BOLD, 11));
		branch.setBounds(10, 36, 195, 14);
		getContentPane().add(branch);

		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setBackground(SystemColor.activeCaptionBorder);
		panel_2.setBounds(272, 657, 705, 48);
		getContentPane().add(panel_2);

		lblDispatchEntry = new JLabel(reportName);
		lblDispatchEntry.setHorizontalAlignment(SwingConstants.CENTER);
		lblDispatchEntry.setForeground(Color.BLACK);
		lblDispatchEntry.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDispatchEntry.setBounds(414, 29, 346, 22);
		getContentPane().add(lblDispatchEntry);


		email = new JTextField(partyEmail);
		email.setBounds(364, 80, 281, 23);
		getContentPane().add(email);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(302, 80, 110, 20);
		getContentPane().add(lblEmail);
		

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("lucida console", Font.PLAIN, 14));
		getContentPane().add(textArea);

		JScrollPane pane = new JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(43, 126, 934, 479);
		getContentPane().add(pane);

		
		
		
//		outputStream= new ByteArrayOutputStream();
		String fileContent= FileReaderUtility.readLocalFile("C:\\report\\"+fileName);
//		WritePDF.writeEmailPDFStream(fileContent,fontSize,pageSize,outputStream);
		
		textArea.setText(fileContent);
		textArea.setCaretPosition(0); 
		

		smsButton = new JButton("SMS");
		smsButton.setVisible(false);
		smsButton.setActionCommand("SMS");
		smsButton.setBounds(700, 616, 86, 30);
		getContentPane().add(smsButton);

		sendButton = new JButton("Send");
		sendButton.setActionCommand("Save");
		sendButton.setBounds(799, 616, 86, 30);
		getContentPane().add(sendButton);
		

		exitButton = new JButton("Exit");
		exitButton.setActionCommand("Exit");
		exitButton.setBounds(891, 616, 86, 30);
		getContentPane().add(exitButton);

		

		//chq_dt.addKeyListener(keyListener);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(272, 68, 705, 48);
		getContentPane().add(panel_1);
		
		
		
		sendButton.addActionListener(this);
		exitButton.addActionListener(this);
		smsButton.addActionListener(this);


	}


	public void clearAll(boolean edit)
	{

		email.setText("");
		
		

		 
	}


	

	
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());

		if(e.getActionCommand().equalsIgnoreCase("save"))
		{
			saveData();
		}

		if(e.getActionCommand().equalsIgnoreCase("SMS"))
		{
			if(smsText!=null)
			 sendSMS(smsText);
			else
				alertMessage(EmailLedger.this, "No Message for SMS");
		}

		if(e.getActionCommand().equalsIgnoreCase("exit"))
		{
			clearAll(false);
			dispose();
		}	
				

	}


	public void sendSMS(String text)
	{

		
		String mobileno=email.getText();
		
		System.out.println(text);
		
		
		//send_sms smsObj = new send_sms();
//		smsObj.setparams("http://alerts.sinfini.com/api/web2sms.php","Aa6a050cee318eec84052e249570fdb38","SIDEMO");

		//smsObj.setparams("alerts.solutionsinfini.com","sms","Ace1e719870fb620571afb05430ca8863","Aristo");

			  if (mobileno.length()>9 )
			  {	  
				  try {
						//smsObj.send_sms(mobileno,text,"");
						NewSms.sendSms(mobileno, "NAKODA", text);

				  } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			  }

		
	}
	
	public void saveData()
	{
		try
		{
			
			String emailAddress=email.getText().trim().toLowerCase();
			if(emailAddress.length()>10)
			{

				if(checkInternetConnectivity("google.com"))
				{
					//byte [] byt= outputStream.toByteArray();
//					SendMail.send("Ledger Copy", outputStream, FileReaderUtility.readFile(file.getAbsolutePath()),emailAddress, "ledger.pdf");

					if (reportName.startsWith("54"))
						SendMail.send("Credit Note Detail", outputStream, FileReaderUtility.readFile("/EmailTemplates/invoiceTemplate.txt"),emailAddress, "crdetail.pdf");

					else if (reportName.startsWith("Outstanding"))
						SendMail.send("Outstanding", outputStream, FileReaderUtility.readFile("/EmailTemplates/outTemplate.txt"),emailAddress, "out.pdf");

					else
						SendMail.send("Ledger Copy", outputStream, FileReaderUtility.readFile("/EmailTemplates/ledgerTemplate.txt"),emailAddress, "ledger.pdf");
					JOptionPane.showMessageDialog(this, "Mail sent successfully");
					clearAll(true);
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Please Check your Internet Connectivity!!!!");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Please enter valid email address");
				email.requestFocus();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}



	public void setVisible(boolean b)
	{
		if (reportName.startsWith("Outstanding"))
			smsButton.setVisible(true);
		else
			smsButton.setVisible(false);
		
		super.setVisible(b);
	}
	
}


