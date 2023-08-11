package com.coldstorage.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.coldstorage.dto.AreaDto;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RegionDto;
import com.coldstorage.dto.YearDto;

public class SMSOpt22 extends BaseClass implements ActionListener 
{
	private static final long serialVersionUID = 1L;


	private JFormattedTextField sdate;
	private JFormattedTextField edate;
	private JLabel reportName,selectParty;
	 
	private JButton viewButton;
	 
	//private JComboBox partyList; 
	private JComboBox myear;
	String ClassNm;
	String ReportNm;
	String optn;
	YearDto yrdto;
	SimpleDateFormat sdf=null;
	private JRadioButton selectiveParty,rdbtnPartywise,rdbtnRegionwise,rdbtnDistwise;
	private JRadioButton allParty;
	private JPanel panel;
	private JPanel panel_2;


	private JTextField party_name;
	private JComboBox regdistcombo;
	private JList partyList;
	private JScrollPane partyPane;
	private PartyDto partyDto;


	private JButton exitButton;
	private JLabel hintTextLabel;
	ByteArrayOutputStream outputStream ;	
	private JTextArea smsText;
	private JScrollPane smsPane;
	
	public  SMSOpt22(String nm,String repNm)
	{

		ClassNm=nm;
		optn="1";
		ReportNm=repNm; 
		 
		//setUndecorated(true);
		setResizable(false);
		setSize(491, 362);	
		setLocationRelativeTo(null);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		hintTextLabel = new JLabel("Enter Party Code or Press Enter Key");
		hintTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		hintTextLabel.setForeground(Color.GRAY);
		hintTextLabel.setBounds(162, 135, 224, 20);
		getContentPane().add(hintTextLabel);
		
		//PartyList ////////////////////////////////////////////////////////
		partyList = new JList(loginDt.getPrtList());
		partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		partyList.setSelectedIndex(0);
		partyPane = new JScrollPane(partyList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		partyPane.setBounds(159, 157, 281, 162);
		getContentPane().add(partyPane);
		partyPane.setVisible(false);
		partyList.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) 
			{
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
				{

					partyPane.setVisible(false);
					partyList.setVisible(false);

					int partyCodeIndex = partyList.getSelectedIndex();
					partyDto = (PartyDto) loginDt.getPrtList().get(partyCodeIndex); 
					
		    		if(partyDto!=null)
					{
						party_name.setText(partyDto.getMac_name());
						myear.requestFocus();
						partyPane.setVisible(false);
						partyList.setVisible(false);
					}
		    		 
				}
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) 
				{
					partyPane.setVisible(false);
					hintTextLabel.setVisible(true);
					party_name.setText("");
					party_name.requestFocus();
					evt.consume();
				}
			}
		});
		
		partyList.addMouseListener(new MouseListener() 
		{
			public void mouseReleased(MouseEvent arg0){
			}
			public void mousePressed(MouseEvent arg0){
			}
			public void mouseExited(MouseEvent arg0){
			}
			public void mouseEntered(MouseEvent arg0){
			}
			public void mouseClicked(MouseEvent arg0) 
			{
				partyPane.setVisible(false);
				partyList.setVisible(false);

				int partyCodeIndex = partyList.getSelectedIndex();
				partyDto = (PartyDto) loginDt.getPrtList().get(partyCodeIndex); 
				
	    		if(partyDto!=null)
				{
					party_name.setText(partyDto.getMac_name());
					myear.requestFocus();
					partyPane.setVisible(false);
					partyList.setVisible(false);
				}
				
			}
		});
		
		///////////////////////////////////////////////////////////

		reportName = new JLabel(repNm);
		reportName.setHorizontalAlignment(SwingConstants.CENTER);
		reportName.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportName.setBounds(85, 27, 303, 20);
		getContentPane().add(reportName);

		 
 

		viewButton = new JButton("View");
		viewButton.setBounds(140, 282, 70, 30);
		getContentPane().add(viewButton);

	 

		exitButton = new JButton("Exit");
		exitButton.setBounds(215, 282, 70, 30);
		getContentPane().add(exitButton);
		

		 
			
		 
		viewButton.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER)
				{
					callPrint("SMS");
					evt.consume();
				}
				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) 
				{
					 
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		 
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
					 
					viewButton.setBackground(null);
					exitButton.setBackground(null);
					evt.consume();
				}
				
			}
		});
		 
		 
		
		
		//Group the radio buttons.
	    selectParty = new JLabel("Select Party:");
	    selectParty.setBounds(45, 133, 110, 20);
	    getContentPane().add(selectParty);
	    

		regdistcombo = new JComboBox();
		regdistcombo.setVisible(false);
		regdistcombo.setBounds(159, 133, 281, 23);
		getContentPane().add(regdistcombo);

	    
		party_name = new JTextField();
		party_name.setBounds(159, 133, 281, 23);
		getContentPane().add(party_name);
		party_name.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				
				
				
				if (key == KeyEvent.VK_ENTER) 
				{
					
					String partyCode = party_name.getText();
					
					partyDto = (PartyDto) loginDt.getPrtmap().get(partyCode.toUpperCase());
					
					if(partyDto!=null)
					{
						party_name.setText(partyDto.getMac_name());
						myear.requestFocus();
						partyPane.setVisible(false);
						partyList.setVisible(false);
					}
					else
					{
						hintTextLabel.setVisible(false);
						partyPane.setVisible(true);
						partyList.setVisible(true);
						partyList.requestFocus();
						partyList.setSelectedIndex(0);
						partyList.ensureIndexIsVisible(0);
					}
					
				}
			}
			
			public void keyReleased(KeyEvent keyEvent) 
			{
				if(party_name.getText().trim().length()>=1)
				{
					hintTextLabel.setVisible(false);
				}
				else
				{
					hintTextLabel.setVisible(true);
				}
				
			}
			
			
			
		});
		
	   
		
		smsText = new JTextArea();
		smsText.setLineWrap(true);
		smsText.setWrapStyleWord(true);
		smsPane = new JScrollPane(smsText, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		smsPane.setBounds(38, 157, 402, 100);
		getContentPane().add(smsPane);
		
		selectiveParty = new JRadioButton("Selective Party");
		selectiveParty.setSelected(true);
		selectiveParty.setActionCommand("3");
		selectiveParty.setBounds(42, 94, 130, 23);
		getContentPane().add(selectiveParty);
		selectiveParty.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
				{
					party_name.requestFocus();
				}
				
				if (key == KeyEvent.VK_RIGHT)
				{
					allParty.requestFocus();
					allParty.setSelected(true);
					party_name.setEnabled(false);
				}
				
			}
			
		});
		
		
		allParty = new JRadioButton("All Parties");
		allParty.setActionCommand("4");
		allParty.setBounds(180, 94, 174, 23);
		getContentPane().add(allParty);
		allParty.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent keyEvent) 
			{
				int key = keyEvent.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
				{
					myear.requestFocus();
				}
				
				if (key == KeyEvent.VK_LEFT)
				{
					selectiveParty.requestFocus();
					selectiveParty.setSelected(true);
					party_name.setEnabled(true);
				}
				
			}
			
		});
		
		
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(22, 90, 430, 32);
		getContentPane().add(panel);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(22, 125, 430, 141);
		getContentPane().add(panel_2);
		
		 

		ButtonGroup group3 = new ButtonGroup();
	    group3.add(selectiveParty);
		group3.add(allParty);
		
	 
		 
	    exitButton.addActionListener(this);
	    viewButton.addActionListener(this);
	    
		setAlwaysOnTop(true);
		

		selectiveParty.addActionListener(this);
		selectiveParty.setActionCommand("selectiveParty");

		allParty.addActionListener(this);
		allParty.setActionCommand("allParty");
		
		rdbtnPartywise = new JRadioButton("Partywise");
		rdbtnPartywise.setSelected(true);
		rdbtnPartywise.setActionCommand("party");
		rdbtnPartywise.setBounds(43, 60, 124, 23);
		getContentPane().add(rdbtnPartywise);
		
		rdbtnRegionwise = new JRadioButton("Areawise");
		rdbtnRegionwise.setActionCommand("Region");
		rdbtnRegionwise.setBounds(180, 60, 124, 23);
		getContentPane().add(rdbtnRegionwise);
		
		rdbtnDistwise = new JRadioButton("Terrwise");
		rdbtnDistwise.setActionCommand("Dist");
		rdbtnDistwise.setBounds(323, 60, 124, 23);
		getContentPane().add(rdbtnDistwise);
		
		ButtonGroup group4 = new ButtonGroup();
	    group4.add(rdbtnPartywise);
		group4.add(rdbtnRegionwise);
		group4.add(rdbtnDistwise);
		
		
		rdbtnPartywise.addActionListener(this);
		rdbtnRegionwise.addActionListener(this);
		rdbtnDistwise.addActionListener(this);
		
		 
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(22, 54, 430, 32);
		getContentPane().add(panel_3);
		
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 15, 460, 311);
		getContentPane().add(panel_1);
		
		
		//setting default focus request
		addWindowListener(new WindowAdapter() 
		{
			public void windowOpened(WindowEvent e) 
			{
				party_name.requestFocus();
			}
		});
		
	}
	
	
	 public void resetAll()
	 {
		 party_name.setText("");
		 party_name.requestFocus();
		 party_name.setEnabled(true);
		 party_name.setVisible(true);
		 hintTextLabel.setVisible(true);
		 
		 partyList.setSelectedIndex(0);		 
		  
		 
		 optn="1";
		 rdbtnPartywise.setSelected(true);
		 selectiveParty.setSelected(true);
		 regdistcombo.setVisible(false);
		 selectParty.setText("Select Party");
		 selectiveParty.setText("Seletive Party");
		 allParty.setText("All Parties");
		 
		 
			viewButton.setEnabled(true);
			 
	 		viewButton.setBackground(null);
			exitButton.setBackground(null);
			outputStream=null;


	 }
	
	public void actionPerformed(ActionEvent e) 
	{
	    System.out.println(e.getActionCommand());
	    if (e.getActionCommand().equals("1") || e.getActionCommand().equals("2") || e.getActionCommand().equals("3") || e.getActionCommand().equals("4"))
	    {
	    	optn=e.getActionCommand();
	    }
	    
	    if(e.getActionCommand().equalsIgnoreCase("Region") )
	    {
		    selectParty.setText("Select");
		    selectiveParty.setText("Selective Region");
		    allParty.setText("All Region");
		    hintTextLabel.setVisible(false);
		    party_name.setVisible(false);
		    partyPane.setVisible(false);
		    regdistcombo.setVisible(true);
		    regdistcombo.removeAllItems();
		    Vector v =  loginDt.getRegList();
		    int size = v.size();
		    for(int i=0;i<size;i++)
		    {
		    	regdistcombo.addItem(v.get(i));
		    }
		    //regdistcombo.addItem(loginDt.getRegList());
		    
	    }
	   
	    if(e.getActionCommand().equalsIgnoreCase("Dist"))
	    {
		    selectParty.setText("Select");
		    selectiveParty.setText("Selective Dist");
		    allParty.setText("All Dist");
		    hintTextLabel.setVisible(false);
		    party_name.setVisible(false);
		    partyPane.setVisible(false);
		    regdistcombo.setVisible(true);
		    regdistcombo.removeAllItems();
		    Vector v =  loginDt.getDistList();
		    int size = v.size();
		    for(int i=0;i<size;i++)
		    {
		    	regdistcombo.addItem(v.get(i));
		    }

		    
		    //regdistcombo.addItem(loginDt.getDistList());

	    }

	    if(e.getActionCommand().equalsIgnoreCase("Party"))
	    {
		    selectParty.setText("Select Party");
		    selectiveParty.setText("Selective Party");
		    allParty.setText("All Parties");
		    hintTextLabel.setVisible(true);
		    party_name.setVisible(true);
		    regdistcombo.setVisible(false);

	    }

	    if(e.getActionCommand().equalsIgnoreCase("Exit"))
	    {
	    	resetAll();
	    	dispose();
	    	
	    }
	    
	 
	    
	    if(e.getActionCommand().equalsIgnoreCase("SMS"))
	    {
	    	callPrint(e.getActionCommand());

	    }
	    
	    
	    
	    if (e.getActionCommand().equals("selectiveParty") )
	    {
	    	party_name.setEnabled(true);
	    	optn="1";
	    }
	    if (e.getActionCommand().equals("allParty") )
	    {
	    	party_name.setEnabled(false);
	    	
	    	optn="2";
	    	 
	    }
	    
	   
	     
	    
	    
	}
 
	
	
	
	public void callPrint(String btnnm )
	 {
		try
    	{
    		//String btnnm = e.getActionCommand();
			System.out.println("Button value is "+btnnm);

			 
    		PartyDto rdt = partyDto; 
    		if(partyDto==null)
    		{
    			rdt = (PartyDto) loginDt.getPrtList().get(0);	
    		}
    		
    		
				String code=rdt.getMac_code();
				String name=rdt.getMac_name();

			 
					if(rdbtnRegionwise.isSelected())
					{
						optn="3";
						RegionDto reg = (RegionDto) regdistcombo.getSelectedItem();
						code=String.valueOf(reg.getReg_code());
						name=reg.getReg_name();
					}
					else if(rdbtnDistwise.isSelected())
					{
						optn="4";
						AreaDto reg = (AreaDto) regdistcombo.getSelectedItem();
						code=String.valueOf(reg.getArea_cd());
						name=reg.getArea_name();
					}
					if(allParty.isSelected())
					{
						optn="2";
						code="0";
						name="";
					}
						
					 new SMSlOpt22(setIntNumber(code),name,loginDt.getFin_year(),setIntNumber(optn),smsText.getText() ).setVisible(true);
					 resetAll();
					 dispose();
					
			 
				 
				 
		 
			 

    	}
    	catch(Exception ez)
    	{
    		System.out.println("error");
    		ez.printStackTrace();
    	}
	 }
	

	
	
	public void setVisible(boolean b)
	{
	 
		viewButton.setText("SMS");
		super.setVisible(b);
	}
	
}


