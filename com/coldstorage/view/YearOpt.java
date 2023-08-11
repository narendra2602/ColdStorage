package com.coldstorage.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.coldstorage.dao.MonthDAO;
import com.coldstorage.dao.PartyDAO;
import com.coldstorage.dao.TaxDAO;
import com.coldstorage.dto.YearDto;

public class YearOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName ;
	private JLabel sdateLeb;
	private JButton exitButton,changetButton;
 
	private JComboBox year;
	String ClassNm,ReportNm;
 
	PartyDAO prtdao=null;
	YearDto yd;
	MonthDAO mdao =null;
	TaxDAO tdao=null;
	public  YearOpt(String nm,String repNm)
	{
		 
		ClassNm=nm;
		ReportNm=repNm;
		
		System.out.println("class name is "+ClassNm);

		System.out.println("repoarty name is "+ReportNm);

		//setUndecorated(true);
		setResizable(false);
		setSize(400, 285);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		mdao = new MonthDAO();
		prtdao=  new PartyDAO();
		tdao=  new TaxDAO();
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

		


		
		///////////////////////////////////////////////////////////////////////////////////////////////

		exitButton = new JButton("Exit");
		exitButton.setIcon(exitIcon);
		exitButton.setBounds(203, 177, 95, 30);
		getContentPane().add(exitButton);


		changetButton = new JButton("Change");
		changetButton.setIcon(submitIcon);
		changetButton.setBounds(97, 177, 95, 30);
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

		
		
	    if(e.getActionCommand().equalsIgnoreCase("Change") )
	    {
			try
			{
				System.gc(); 
				java.awt.Window win[] = java.awt.Window.getWindows();
				System.out.println("no. of windows is "+win.length);
				int visibleWins=0;

				for(int i=0;i<win.length;i++)
				{ 
					if(win[i].isVisible())
					{
						visibleWins++;
					}
				}

				System.out.println("no. of windows Visible wins "+visibleWins);
				if(visibleWins>2)
				{

					int answer = JOptionPane.showOptionDialog(this, "Close all opened windows before Changing month \n or you might loose some data","Changing Month Yes/No",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,  new String[] {"Yes", "No"}, "No");

					if (answer == JOptionPane.YES_OPTION) 
					{

						for(int i=0;i<win.length;i++)
						{
							
							String className = win[i].getClass().getSimpleName().toString().trim();
							if(!className.equalsIgnoreCase("MenuFrame"))
							{	
								win[i].dispose();
								win[i]=null;
							}
						}
						
						map=null;
						map = new HashMap();
						YearDto sdt = (YearDto) year.getSelectedItem();

						prtdao.addPartyAcc(sdt.getYearcode(), loginDt.getDiv_code() , loginDt.getDepo_code());
						//mdao.addOpening(sdt.getYearcode(), loginDt.getDiv_code() , loginDt.getDepo_code());

						
//// 01/04/2018					mdao.addCmpinv(sdt.getYearcode(), loginDt.getDiv_code() , loginDt.getDepo_code());
						//sdao.changeYearExtendScheme(loginDt.getDiv_code() , loginDt.getDepo_code(),sdt.getYearcode());
						tdao.updateNewYearTax(loginDt.getDepo_code(),sdt.getYearcode());

							// else part mf/tf/genetica ke liye hai (Sales Package)	
/*							loginDt.setPrtList(prtdao.getPartyNm(loginDt.getDepo_code(),loginDt.getDiv_code(),sdt.getYearcode(),loginDt.getMkt_year(),35,1));
							loginDt.setPrtmap(prtdao.getPartyNmMap(loginDt.getDepo_code(),loginDt.getDiv_code(),sdt.getYearcode(),loginDt.getMkt_year(),35,1));
*/
						System.out.println("ayaha ak aa gaya hai ");

						int i = mdao.setYear(sdt.getYearcode(), loginDt);
						//loginDt = mdao.setYear(sdt.getYearcode(), loginDt);
						lblAccountingYear.setText(loginDt.getMessage());
						lblFinancialAccountingYear.setText(loginDt.getFooter());
						stock_status.setText(loginDt.getTotinv());

						resetAll();
						dispose();
					}
				}
				else
				{
					map=null;
					map = new HashMap();
					YearDto sdt = (YearDto) year.getSelectedItem();

					

					prtdao.addPartyAcc(sdt.getYearcode(), loginDt.getDiv_code() , loginDt.getDepo_code());
					//mdao.addOpening(sdt.getYearcode(), loginDt.getDiv_code() , loginDt.getDepo_code());



/////					mdao.addCmpinv(sdt.getYearcode(), loginDt.getDiv_code() , loginDt.getDepo_code());
				   // sdao.changeYearExtendScheme(loginDt.getDiv_code() , loginDt.getDepo_code(),sdt.getYearcode());
					tdao.updateNewYearTax(loginDt.getDepo_code(),sdt.getYearcode());

						// else part mf/tf/genetica ke liye hai (Sales Package)	
/*						loginDt.setPrtList(prtdao.getPartyNm(loginDt.getDepo_code(),loginDt.getDiv_code(),sdt.getYearcode(),loginDt.getMkt_year(),35,1));
						loginDt.setPrtmap(prtdao.getPartyNmMap(loginDt.getDepo_code(),loginDt.getDiv_code(),sdt.getYearcode(),loginDt.getMkt_year(),35,1));

*/					

					int i = mdao.setYear(sdt.getYearcode(), loginDt);
					//loginDt = mdao.setYear(sdt.getYearcode(), loginDt);
					lblAccountingYear.setText(loginDt.getMessage());
					lblFinancialAccountingYear.setText(loginDt.getFooter());
					stock_status.setText(loginDt.getTotinv());

					resetAll();
					dispose();
				}
				

			}
			catch(Exception ez)
			{
				System.out.println("error");
				ez.printStackTrace();
			}

		}

		
	}
 
	

	
	public void setVisible(boolean b)
	{
		if(ReportNm.contains("Generate Invoice"))
		{
			changetButton.setText("Generate");
			changetButton.setActionCommand("Generate");
			changetButton.setBounds(113, 177, 95, 30);
			year.setSelectedIndex(1);

		}
		super.setVisible(b);
	}
	
	
	
}


