package com.coldstorage.view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.coldstorage.dto.YearDto;

public class BackupOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName ;
	private JLabel sdateLeb;
	private JButton exitButton,changetButton;
  
	String ClassNm,ReportNm;
 
	File filePath=null;
	YearDto yd;
	 
	public  BackupOpt(String nm,String repNm)
	{
		 
		ClassNm=nm;
		ReportNm=repNm;
		
	 

		//setUndecorated(true);
		setResizable(false);
		setSize(400, 285);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		arisleb.setVisible(false);

		getContentPane().setLayout(null);
		 
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(49, 46, 303, 20);
		getContentPane().add(reportName);

		sdateLeb = new JLabel("Backup for the Financial Year :"+(loginDt.getFin_year()+"-"+(loginDt.getFin_year()+1)));
		sdateLeb.setBounds(60, 119, 300, 20);
		sdateLeb.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(sdateLeb);


		

		
		
		///////////////////////////////////////////////////////////////////////////////////////////////

		exitButton = new JButton("Exit");
		exitButton.setIcon(exitIcon);
		exitButton.setBounds(203, 177, 95, 30);
		getContentPane().add(exitButton);


		changetButton = new JButton("Backup");
		changetButton.setIcon(submitIcon);
		changetButton.setBounds(97, 177, 105, 30);
		getContentPane().add(changetButton);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(15, 23, 366, 208);
		getContentPane().add(panel_1);

		exitButton.addActionListener(this);
	    changetButton.addActionListener(this);
		
		setAlwaysOnTop(true);
		
	}

	 

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			dispose();
		}

		
		
	    if(e.getActionCommand().equalsIgnoreCase("Backup") )
	    {

    		//Run batch file using java
	    	int i=0;
/*    		if(loginDt.getFin_year()==2020)
    			filePath = new File("C:\\webBackup\\ftpdata\\prompt\\promptsqlback2020.bat");
    		else if(loginDt.getFin_year()==2021)
    			filePath = new File("C:\\webBackup\\ftpdata\\prompt\\promptsqlback2021.bat");
    		else 
*/    			filePath = new File("D:\\backup\\promptsqlback.bat");
    		try {
    			Desktop.getDesktop().open(filePath);
    			System.out.println("PROCESS START "+i);
    			i=1;
    			System.out.println("PROCESS end "+i);
    			
    		} catch (Exception ex) {
    			i=0;
    			ex.printStackTrace();
    		}
    		
	          if(i==1)
	    		dispose();
	          else
	        	alertMessage(BackupOpt.this, "Error in Backup!!!");  
		}
		
	}
 
	

	
	 
	
	
}


