package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ConnectionFactory {

	public static Connection getConnection(){
		Connection con = null;
		ResourceBundle rb=null;
		String host=null;
		String port=null;
		String dbname=null;
		String user=null;
		String password=null;
		
		Connection connection=null;
		
/*		   String url = "jdbc:mysql://localhost:3406/coldstorage?autoReconnect=true&useSSL=false";
	        String username = "root";
	        password = "root";
*/	       
	        
	        try  {
	        	
				rb = ResourceBundle.getBundle("Database");
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				host=rb.getString("host");
				port=rb.getString("port");
				dbname=rb.getString("dbname");
				user=rb.getString("user");
				password=rb.getString("password");
				
				//con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbname+"?autoReconnect=true&useSSL=false",""+user+"",""+password+"");			  
				con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbname+"?autoReconnect=true&useSSL=false",""+user+"",""+password+"");			  

//	        	con = DriverManager.getConnection(url, username, password);
	        } catch (SQLException e) {
	            System.err.println("Error saving Hindi text: " + e.getMessage());
	        }
	   
		
        return con;
	}

	public static String getDrvnm(){
		ResourceBundle rb=null;
		String drvnm=null;
		try {
			rb = ResourceBundle.getBundle("Database");
			drvnm=rb.getString("netnm");
			  
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        return drvnm;
	}

	public static String getPrinternm(){
		ResourceBundle rb=null;
		String drvnm=null;
		try {
			rb = ResourceBundle.getBundle("Database");
			drvnm=rb.getString("printernm");
			  
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        return drvnm;
	}
	
	public static String getBtnnm(){
		ResourceBundle rb=null;
		String drvnm=null;
		try {
			rb = ResourceBundle.getBundle("Database");
			drvnm=rb.getString("btnnm");
			  
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        return drvnm;
	}
	
	public static boolean  getRepair(){
		ResourceBundle rb=null;
		String repair=null;
		boolean check=false;
		try {
			rb = ResourceBundle.getBundle("Database");
			repair=rb.getString("repair");
			if(repair!=null)
				check=repair.trim().equalsIgnoreCase("true")?true:false;
				
			System.out.println("value of repair is "+repair);  
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			repair=null;
			check=true;
		}
//        return (repair==null?true:false);
        return check;

	}
	
	
	public static String[] getUINamePassword()
	{
		ResourceBundle rb=null;
		String UINamePassword[] = new String[2];
		try 
		{
			rb = ResourceBundle.getBundle("Database");
			UINamePassword[0]=rb.getString("UIname");
			UINamePassword[1]=rb.getString("UIpass");
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			UINamePassword[0]=null;
		}
        return UINamePassword;
	}
	
	public static String[] getEmailID()
	{
		ResourceBundle rb=null;
		String EmailID[] = new String[3];
		try 
		{
			rb = ResourceBundle.getBundle("Database");
			EmailID[0]=rb.getString("emailId1");
			EmailID[1]=rb.getString("emailId2");
			EmailID[2]=rb.getString("emailId3");
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			EmailID[0]=null;
		}
        return EmailID;
	}

	public static Connection getConnectionHO(){
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
//			con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbname+"",""+user+"",""+password+"");			  
			con = DriverManager.getConnection("jdbc:mysql://202.71.138.217:3306/aris","root","spk67890");
			
			  
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        return con;
	}
	
	
	public static java.sql.Date setSqlDate(Date javadate)
	{
		      java.sql.Date sqlDate = null;
		      try {
				sqlDate = new java.sql.Date(javadate.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				sqlDate=null;
			}
		      return sqlDate;  
	}

	
}
 