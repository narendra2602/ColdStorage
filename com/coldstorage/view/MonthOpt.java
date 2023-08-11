package com.coldstorage.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.coldstorage.dao.MonthDAO;
import com.coldstorage.dao.ProductDAO;
import com.coldstorage.dto.MonthDto;
import com.coldstorage.dto.YearDto;

public class MonthOpt extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel reportName ;
	private JLabel sdateLeb;
	private JButton exitButton,changetButton;
 
	private JComboBox smonth;
	String ClassNm,repNm;
	PreparedStatement curr=null;
	ResultSet rcurr=null;
	
	YearDto yd;
	ProductDAO pd=null;
	public  MonthOpt(String nm,String repNm)
	{
		 
		ClassNm=nm;
		this.repNm=repNm;
		
		//setUndecorated(true);
		setResizable(false);
		setSize(400, 285);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(49, 46, 303, 20);
		getContentPane().add(reportName);

		sdateLeb = new JLabel("Select Month:");
		sdateLeb.setBounds(72, 111, 110, 20);
		getContentPane().add(sdateLeb);

		smonth = new JComboBox(loginDt.getFmonth());
		smonth.setBounds(199, 111, 136, 23);
		getContentPane().add(smonth);
		smonth.setActionCommand("month");
		smonth.setMaximumRowCount(12);

		
		Vector<?> v = (Vector<?>) loginDt.getFmon().get(loginDt.getFin_year());
		smonth.removeAllItems();
		MonthDto mn=null;
		for (int i=0; i<v.size();i++)
		{
			 mn = (MonthDto) v.get(i);
			 smonth.addItem(mn);
			 
		}
		mn =(MonthDto) smonth.getSelectedItem();
		System.out.println("month value is "+loginDt.getMnthName()+" MNO IS "+loginDt.getMno()+"ZZZZ "+mn.getMnthno());
		//System.out.println("month value is "+loginDt.getMnthName());
		smonth.setSelectedIndex(loginDt.getMno());
		
		
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
		smonth.addActionListener(this);
		
		setAlwaysOnTop(true);
		
	}

	 public void resetAll()
	 {
		 
		 smonth.setSelectedIndex(smonth.getSelectedIndex());
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
						
						MonthDAO mdao = new MonthDAO();
						MonthDto sdt = (MonthDto) smonth.getSelectedItem();
						int i = mdao.setMonth(sdt.getMnthcode(), loginDt.getFin_year(), loginDt);
						System.out.println("value of i is "+i+loginDt.getSdate()+" "+loginDt.getEdate());
						lblAccountingYear.setText(loginDt.getMessage());
						lblFinancialAccountingYear.setText(loginDt.getFooter());
					//	stock_status.setText(loginDt.getTotinv());

	//					PartyDAO pdao = new PartyDAO();
//						int x = pdao.allList(loginDt.getFin_year(), loginDt.getMkt_year(),loginDt.getDepo_code(),loginDt.getDiv_code(),10);

//						loginDt.setPrtList(pdao.getPartyNm(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),0,0));
//						loginDt.setPrtmap(pdao.getPartyNmMap(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),0,0));

						
//						loginDt.setAmessage(pdao.getMessage(loginDt.getDepo_code(), loginDt.getDiv_code(), loginDt.getFin_year(), sdt.getMnthcode()));
			
						
						
						System.out.println("month value is ....."+loginDt.getMnthName()+" MNO IS... "+loginDt.getMno());

						resetAll();
						dispose();
					}
					
				}
				else
				{
					MonthDAO mdao = new MonthDAO();
					MonthDto sdt = (MonthDto) smonth.getSelectedItem();

					
					System.out.println("month code is "+sdt.getMnthcode()+ "fin year is "+loginDt.getFin_year());

					
					int i = mdao.setMonth(sdt.getMnthcode(), loginDt.getFin_year(), loginDt);
					System.out.println("value of i is "+i+" "+loginDt.getMkt_year());
					System.out.println("value of i is "+i+loginDt.getSdate()+" "+loginDt.getEdate());
					lblAccountingYear.setText(loginDt.getMessage());
					lblFinancialAccountingYear.setText(loginDt.getFooter());
					//stock_status.setText(loginDt.getTotinv());



		//			PartyDAO pdao = new PartyDAO();
//					loginDt.setAmessage(pdao.getMessage(loginDt.getDepo_code(), loginDt.getDiv_code(), loginDt.getFin_year(), sdt.getMnthcode()));

					if(loginDt.getPack_code()==1)
					{
			//		loginDt.setPrtList(pdao.getPartyNm(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),0,0));
			//		loginDt.setPrtmap(pdao.getPartyNmMap(loginDt.getDepo_code(),loginDt.getDiv_code(),loginDt.getFin_year(),loginDt.getMkt_year(),0,0));
					}

//					int x = pdao.allList(loginDt.getFin_year(), loginDt.getMkt_year(),loginDt.getDepo_code(),loginDt.getDiv_code(),10);
					
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
 
	
	
	public void dispose()
	{
		smonth.setSelectedIndex(loginDt.getMno());
		super.dispose();
	}
	

	public void setVisible(boolean b)
	{
		if(repNm.contains("Excel"))
		{
			changetButton.setText("Submit");
			Vector<?> v = (Vector<?>) loginDt.getMmon().get(loginDt.getMkt_year());
			smonth.removeAllItems();
			MonthDto mn=null;
			for (int i=0; i<v.size();i++)
			{
				 mn = (MonthDto) v.get(i);
				 smonth.addItem(mn);
				 
			}
			mn =(MonthDto) smonth.getSelectedItem();
			smonth.setSelectedIndex(loginDt.getMno());
			
		}
		else
		{
			changetButton.setText("Change");
			Vector<?> v = (Vector<?>) loginDt.getFmon().get(loginDt.getFin_year());
			smonth.removeAllItems();
			MonthDto mn=null;
			for (int i=0; i<v.size();i++)
			{
				 mn = (MonthDto) v.get(i);
				 smonth.addItem(mn);
				 
			}
			mn =(MonthDto) smonth.getSelectedItem();
			smonth.setSelectedIndex(loginDt.getMno());
			
		}
		
		
		super.setVisible(b);
	}
	 
}


