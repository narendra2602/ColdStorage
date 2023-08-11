package com.coldstorage.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.coldstorage.dao.AccountDAO;
import com.coldstorage.dao.MonthDAO;
import com.coldstorage.dto.YearDto;

public class OpeningTrf extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName ;
	private JLabel sdateLeb;
	private JButton exitButton,changetButton;
 
	private JComboBox year;
	String ClassNm;
 
	AccountDAO accdao=null;
	YearDto yd;
	MonthDAO mdao =null;
	public  OpeningTrf(String nm,String repNm)
	{
		 
		ClassNm=nm;

		
		//setUndecorated(true);
		setResizable(false);
		setSize(400, 285);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		mdao = new MonthDAO();
		accdao=new AccountDAO();
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(49, 46, 303, 20);
		getContentPane().add(reportName);

		sdateLeb = new JLabel("Select Financial Year:");
		sdateLeb.setBounds(45, 119, 150, 20);
		getContentPane().add(sdateLeb);

		year = new JComboBox(loginDt.getNewyear());
		year.setBounds(199, 119, 136, 23);
		getContentPane().add(year);
		year.setActionCommand("year");
		year.setMaximumRowCount(12);

		
/*		Vector<?> v = (Vector<?>) loginDt.getFmon().get(loginDt.getFin_year());
		year.removeAllItems();
		YearDto yr=null;
		for (int i=0; i<v.size();i++)
		{
			 yr = (YearDto) v.get(i);
			 year.addItem(yr);
			 
		}
		yr =(YearDto) year.getSelectedItem();
*/

		
		
		///////////////////////////////////////////////////////////////////////////////////////////////

		exitButton = new JButton("Exit");
		exitButton.setBounds(215, 177, 70, 30);
		getContentPane().add(exitButton);


		changetButton = new JButton("Submit");
		changetButton.setBounds(113, 177, 82, 30);
		getContentPane().add(changetButton);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(15, 23, 366, 208);
		getContentPane().add(panel_1);

		exitButton.addActionListener(this);
	    changetButton.addActionListener(this);
		year.addActionListener(this);
		
		setAlwaysOnTop(true);
		
	}

	 public void resetAll()
	 {
		 
				 year.setSelectedIndex(0);
	 }		 
	 

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equalsIgnoreCase("Exit"))
		{
			dispose();
		}
		
	    if(e.getActionCommand().equalsIgnoreCase("Submit") )
	    {
			try
			{
						int i=0;
						YearDto sdt = (YearDto) year.getSelectedItem();
						System.out.println("yerr code kya hai" +sdt.getYearcode());
						System.out.println("yerr code LOGIN KA kya hai" +loginDt.getFin_year());
						if (loginDt.getFin_year()-sdt.getYearcode()==1)
						{
							if (confirmationDialong()==1)
							{
									i = accdao.closingTrf(sdt.getYearcode(),loginDt.getDepo_code(),loginDt.getDiv_code(),sdt.getMsdate(),sdt.getMedate());
							}		
							resetAll();
							dispose();

						}
						else
						{
							alertMessage(OpeningTrf.this, "Please select Correct Financial Year");
							year.setSelectedIndex(0);
						}
			}
			catch(Exception ez)
			{
				System.out.println("error");
			}

		}

		
	}
 
	
	
	
}


