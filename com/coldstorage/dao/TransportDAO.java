package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.coldstorage.dto.CategoryDto;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.HQDto;
import com.coldstorage.dto.PackDto;
import com.coldstorage.dto.ProductDto;
import com.coldstorage.dto.TransportDto;
import com.coldstorage.view.BaseClass;


public class TransportDAO 
{
	
	public List getTransport(int depo_code,String nm,int div_code)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		TransportDto t=null;

		try {

			con=ConnectionFactory.getConnection();

			String query2 ="select account_no,mac_name,mac_name_hindi,madd1,madd2,madd3,mcity,mcity_hindi,mpin,mstate,mphone,mobile,memail,mcontact,aadhar_no," +
					"pan_no from accountmaster where  mac_name  like ? and div_code=? order by account_no";  
			
			
	
			
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setString(1, nm.trim()+"%");
			ps2.setInt(2,div_code);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			while (rs.next())
			{

				col= new Vector();
				col.addElement(rs.getString(1));//tran_code
				col.addElement(rs.getString(2));//tran name
				
				t = new TransportDto();
				t.setTran_code(rs.getString(1));
				t.setTran_name(rs.getString(2));
				t.setMac_name_hindi(rs.getString(3));
				t.setAddress1(rs.getString(4));
				t.setAddress2(rs.getString(5));
				t.setAddress2(rs.getString(6));
				t.setCity(rs.getString(7));
				t.setMcity_hindi(rs.getString(8));
				t.setMpin(rs.getString(9));
				t.setMstate(rs.getString(10));
				t.setPhone(rs.getString(11));
				t.setMobile(rs.getString(12));
				t.setEmail_id(rs.getString(13)); 
				t.setContact_person(rs.getString(14));
				t.setTran_gstno(rs.getString(15));
				t.setTran_id(rs.getString(16));
				
				col.addElement(t);/// add TransportDto in hidden column
				v.addElement(col);

			}

			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) { ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in TransportDAO.getTransport " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in TransportDAO.Connection.close "+e);
			}
		}

		return v;
	}


	
	
	
	public Vector getTransportListOLD(int depo)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		TransportDto t=null;
		int i=0;
		try {


		 
				
			
			con=ConnectionFactory.getConnection();

			String query2 ="select account_no,mac_name,mac_name_hindi,madd1,madd2,madd3,mcity,mcity_hindi,mpin,mstate,mphone,mobile,memail,mcontact,aadhar_no," +
					"pan_no from accountmaster  ";  

			
			ps2 = con.prepareStatement(query2);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			while (rs.next())
			{
	
				
				t = new TransportDto();
				t.setTran_code(rs.getString(1));
				t.setTran_name(rs.getString(2)+","+rs.getString(7));
				t.setMac_name_hindi(rs.getString(3)+","+rs.getString(8));
				t.setAddress1(rs.getString(4));
				t.setAddress2(rs.getString(5));
				t.setAddress2(rs.getString(6));
				t.setCity(rs.getString(7));
				t.setMcity_hindi(rs.getString(8));
				t.setMpin(rs.getString(9));
				t.setMstate(rs.getString(10));
				t.setPhone(rs.getString(11));
				t.setMobile(rs.getString(12));
				t.setEmail_id(rs.getString(13)); 
				t.setContact_person(rs.getString(14));
				t.setTran_gstno(rs.getString(15));
				t.setTran_id(rs.getString(16));
				t.setAccount_no(rs.getInt(1));
				
				v.add(t);
			}
			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPartyDAO.getTransportList " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return v;
	}

	public HashMap getPartyNameMap(int depo,int div)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		HashMap v=null;
		TransportDto t=null;
		int i=0;

		try {

			con=ConnectionFactory.getConnection();
			String query2 ="select account_no,mac_name,mac_name_hindi,madd1,madd2,madd3,mcity,mcity_hindi,mpin,mstate,mphone,mobile,memail,mcontact,aadhar_no," +
					"pan_no from accountmaster where depo_code=? and div_code=? ";  
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, depo);
			ps2.setInt(2, div);
			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new HashMap();
			while (rs.next())
			{
				t = new TransportDto();
				t.setTran_code(rs.getString(1));
				t.setTran_name(rs.getString(2));
				t.setMac_name(rs.getString(2));
				t.setMac_name_hindi(rs.getString(3));
				t.setAddress1(rs.getString(4));
				t.setAddress2(rs.getString(5));
				t.setAddress2(rs.getString(6));
				t.setCity(rs.getString(7));
				t.setMcity_hindi(rs.getString(8));
				t.setMpin(rs.getString(9));
				t.setMstate(rs.getString(10));
				t.setPhone(rs.getString(11));
				t.setMobile(rs.getString(12));
				t.setEmail_id(rs.getString(13)); 
				t.setContact_person(rs.getString(14));
				t.setTran_gstno(rs.getString(15));
				t.setTran_id(rs.getString(16));
				t.setAccount_no(rs.getInt(1));

				v.put(rs.getString(1),t);
				
			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPartyDAO.partylist " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return v;
	}


	
	
	
      
      public int addTransport(TransportDto tdto)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="insert into accountmaster values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			 
  				 
	  			ps2.setInt(1,0);
	  			ps2.setString(2,tdto.getTran_name());
	  			ps2.setString(3,tdto.getMac_name_hindi());
	  			ps2.setString(4,tdto.getAddress1());
	  			ps2.setString(5,tdto.getAddress2());
	  			ps2.setString(6,"");
	  			ps2.setString(7,tdto.getCity());
	  			ps2.setString(8,tdto.getMcity_hindi());
	  			ps2.setString(9,tdto.getMpin());
	  			ps2.setString(10,tdto.getMstate());
	  			ps2.setString(11,tdto.getPhone());
	  			ps2.setString(12,tdto.getMobile());
	  			ps2.setString(13,tdto.getEmail_id());
	  			ps2.setString(14,tdto.getContact_person());
	  			ps2.setString(15,tdto.getTran_gstno());
	  			ps2.setString(16,tdto.getTran_id());
	  			ps2.setString(17,"N");
	  			ps2.setInt(18,tdto.getAccount_no());
	  			ps2.setInt(19,tdto.getFin_year());
	  			ps2.setString(20,"");
	  			ps2.setDouble(21,0.00);
	  			ps2.setDouble(22,0.00);
	  			ps2.setDouble(23,0.00);
	  			ps2.setString(24,"");
	  			ps2.setInt(25,tdto.getDepo_code());
	  			ps2.setInt(26,tdto.getDiv_code());
	  		
	  			i=ps2.executeUpdate();
  			 

  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();

  		} catch (SQLException ex) {
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLTransportDAO.addTransport " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLTransportDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}


      public int updateTransport(TransportDto tdto)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update accountmaster set mac_name=?,madd1=?,madd2=?,mcity=?,mpin=?,mphone=?,mobile=?,memail=?," +
  			"mcontact=?,mstate=?,aadhar_no=?,pan_no=?,mac_name_hindi=?,mcity_hindi=? where  account_no=? and div_code=? ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			ps2.setString(1,tdto.getTran_name());
  			ps2.setString(2,tdto.getAddress1());
  			ps2.setString(3,tdto.getAddress2());
  			ps2.setString(4,tdto.getCity());
  			ps2.setString(5,tdto.getMpin());
  			ps2.setString(6,tdto.getPhone());
  			ps2.setString(7,tdto.getMobile());
  			ps2.setString(8,tdto.getEmail_id());
  			ps2.setString(9,tdto.getContact_person());
  			ps2.setString(10,tdto.getMstate());
  			ps2.setString(11,tdto.getTran_gstno());
  			ps2.setString(12,tdto.getTran_id());
  			ps2.setString(13, tdto.getMac_name_hindi());
  			ps2.setString(14, tdto.getMcity_hindi());
  			
  			// where clause
  			ps2.setInt(15,tdto.getAccount_no());
 			ps2.setInt(16,tdto.getDiv_code());
	  		i=ps2.executeUpdate();

  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();

  		} catch (SQLException ex) {ex.printStackTrace();
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLTransportDAO.updateAccountMaster " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLTransportDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}

      public int deleteTransporter(TransportDto tdto)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update transport set del_tag=? where  depo_code=? and tran_code=? ";

  			con.setAutoCommit(false);
			
  				ps2 = con.prepareStatement(query2);
	  			ps2.setString(1,"D");
	  			ps2.setInt(2,tdto.getDepo_code());
	  			ps2.setString(3,tdto.getTran_code());
	  			i=ps2.executeUpdate();

  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();

  		} catch (SQLException ex) {
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLTransportDAO.deleteTransport " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLTransportDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}
	      
     
      public int getMaxNumber(int depo_code)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="select  ifnull(max(cast(tran_code as unsigned Int)),0) code from transport where  depo_code=? ";

  			con.setAutoCommit(false);

  			ps2 = con.prepareStatement(query2);
  			ps2.setInt(1,depo_code);
  			ResultSet rs = ps2.executeQuery();
  			if(rs.next())
  				i=Integer.parseInt(rs.getString(1))+1;
  					
  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();
  			rs.close();
  			

  		} 
  		catch (SQLException ex) 
  		{
  			try 
  			{
  				con.rollback();
  			} 
  			catch (SQLException e) 
  			{
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLTransportDAO.getMaxNumber " + ex);
  			i=-1;
  		}
  		finally 
  		{
  			try 
  			{
  				System.out.println("No. of Records Update/Insert : "+i);
  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} 
  			catch (SQLException e) 
  			{
  				System.out.println("-------------Exception in SQLTransportDAO.Connection.close "+e);
  			}
  		}
  		
  		return i;
  		
  	}  
      
      
  	public HashMap getHQMap(int depo,int div,int myear )
  	{
  		PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  		HashMap v=null;
  		HQDto h=null;
  		int i=0;
  		
  		try {
  			 
  			
  			con=ConnectionFactory.getConnection();
  			
			String hqmap = "select ter_code,ter_name,state_code,area_code,regn_code from hqmast " +
			"where mkt_year=? and div_code=? and depo_code=? and ifnull(del_tag,'')<>'D' order by ter_name";


  			ps2 = con.prepareStatement(hqmap);
  			ps2.setInt(1, myear);
  			ps2.setInt(2, div);
  			ps2.setInt(3, depo);
  			 
  			rs= ps2.executeQuery();
  			con.setAutoCommit(false);
  			v = new HashMap();
  			while (rs.next())
  			{
  				h = new HQDto();
  				h.setTer_code(rs.getInt(1));
  				h.setTer_name(rs.getString(2));
  				h.setState_code(rs.getInt(3));
  				h.setArea_code(rs.getInt(4));
  				h.setRegn_code(rs.getInt(5));

  				v.put(rs.getString(1),h);
  			}
  			con.commit();
  			con.setAutoCommit(true);
  			rs.close();
  			ps2.close();

  		} catch (SQLException ex) {
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLHQDAO.HQMap " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(rs != null){rs.close();}
  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLHQDAO.Connection.close "+e);
  			}
  		}

  		return v;
  	}
      
    public Vector getGroupList(int div)
    {
  	  
  	PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector  v=null;
		GroupDto g=null;
		int i=0;
		String query2=null;
		try 
		{
			con=ConnectionFactory.getConnection();

			
				query2 ="select gp_code,gp_name,gp_name_hindi from grpmast where div_code=? and    ifnull(del_tag,'')<>'D' order by gp_name ";
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);

			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector ();
			while (rs.next())
			{

				
				g = new GroupDto();

				g.setGp_code(rs.getInt(1));
				g.setGp_name(rs.getString(2));
				g.setGp_name_hindi(rs.getString(3));
				v.add(g);
				

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLGroupDAO.GroupList " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
			}
		}
		return v;
	}

    public Vector getCategoryList()
    {
  	  
  	PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector  v=null;
		CategoryDto g=null;
		int i=0;
		String query2=null;
		try 
		{
			con=ConnectionFactory.getConnection();

			
				query2 ="select category_code,category_name,category_name_hindi from category order by category_name ";
			
			ps2 = con.prepareStatement(query2);


			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector ();
			while (rs.next())
			{

				
				g = new CategoryDto();

				g.setCategory_code(rs.getInt(1));
				g.setCategory_name(rs.getString(2));
				g.setCategory_name_hindi(rs.getString(3));
				v.add(g);
				

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLGroupDAO.CategoryList " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
			}
		}
		return v;
	}

    public Vector getProductList(int group_code)
    {
  	  
  	PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector  v=null;
		ProductDto p=null;
		int i=0;
		String query2=null;
		try 
		{
			con=ConnectionFactory.getConnection();

			
			query2 ="select p.pcode,p.pname,p.pack,ifnull(p.pname_hindi,''),p.pd_group,gg.gp_name,"+
			" p.rate "+
			" from prd as p , grpmast as gg where p.div_code=? and p.pd_group=? and "+ 
			" p.pd_group=gg.gp_code  order by p.pname";
			
			ps2 = con.prepareStatement(query2);

			ps2.setInt(1, BaseClass.loginDt.getDiv_code());
			ps2.setInt(2, group_code);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector ();
			while (rs.next())
			{

				
				p = new ProductDto();

				p.setPcode(rs.getInt(1));
				p.setPname(rs.getString(2));
				p.setPack(rs.getString(3));
				p.setPack_name(rs.getString(4));  // short name
				p.setGroup_code(rs.getInt(5));
				p.setGroup_name(rs.getString(6));
				p.setNet_rt1(rs.getDouble(7));
				v.add(p);
				

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLGroupDAO.ProductList " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
			}
		}
		return v;
	}

    public Vector getProductListAll(int group_code)
    {
  	  
  	PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector  v=null;
		ProductDto p=null;
		int i=0;
		String query2=null;
		try 
		{
			con=ConnectionFactory.getConnection();

			
			query2 ="select p.pcode,p.pname,p.pack,ifnull(p.pname_hindi,''),p.pd_group,gg.gp_name,"+
			" p.rate "+
			" from prd as p , grpmast as gg where  p.div_code=? and p.pd_group=gg.gp_code  order by p.pname";
			
			ps2 = con.prepareStatement(query2);

			ps2.setInt(1, BaseClass.loginDt.getDiv_code());
			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector ();
			while (rs.next())
			{

				
				p = new ProductDto();

				p.setPcode(rs.getInt(1));
				p.setPname(rs.getString(2));
				p.setPack(rs.getString(3));
				p.setPack_name(rs.getString(4));  // short name
				p.setGroup_code(rs.getInt(5));
				p.setGroup_name(rs.getString(6));
				p.setNet_rt1(rs.getDouble(7));
				v.add(p);
				

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLGroupDAO.ProductList " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
			}
		}
		return v;
	}

    public Vector getTransportList(int depo,int div)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		TransportDto t=null;
		int i=0;
		try {


		 
				
			
			con=ConnectionFactory.getConnection();

			String query2 ="select account_no,mac_name,mac_name_hindi,madd1,madd2,madd3,mcity,mcity_hindi,mpin,mstate,mphone,mobile,memail,mcontact,aadhar_no," +
					"pan_no from accountmaster where depo_code=? and div_code=?  ";  

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, depo);
			ps2.setInt(2, div);
			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			while (rs.next())
			{
	
				
				t = new TransportDto();
				t.setTran_code(rs.getString(1));
				t.setTran_name(rs.getString(1)+","+rs.getString(2)+","+rs.getString(7));
				t.setMac_name_hindi(rs.getString(3)+","+rs.getString(8));
				t.setAddress1(rs.getString(4));
				t.setAddress2(rs.getString(5));
				t.setAddress2(rs.getString(6));
				t.setCity(rs.getString(7));
				t.setMcity_hindi(rs.getString(8));
				t.setMpin(rs.getString(9));
				t.setMstate(rs.getString(10));
				t.setPhone(rs.getString(11));
				t.setMobile(rs.getString(12));
				t.setEmail_id(rs.getString(13)); 
				t.setContact_person(rs.getString(14));
				t.setTran_gstno(rs.getString(15));
				t.setTran_id(rs.getString(16));
				t.setAccount_no(rs.getInt(1));
				t.setMac_name(rs.getString(2)+","+rs.getString(7));
				
				v.add(t);
			}
			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPartyDAO.getTransportList " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return v;
	}
    public Vector getPackList()
    {
  	  
  	PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector  v=null;
		PackDto g=null;
		int i=0;
		String query2=null;
		try 
		{
			con=ConnectionFactory.getConnection();

			
				query2 ="select pack_code,pack_name,pack_name_hindi from packmast order by pack_name ";
			
			ps2 = con.prepareStatement(query2);


			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector ();
			while (rs.next())
			{

				
				g = new PackDto();

				g.setPack_code(rs.getInt(1));
				g.setPack_name(rs.getString(2));
				g.setPack_name_hindi(rs.getString(3));
				v.add(g);
				

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLGroupDAO.getPackList " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
			}
		}
		return v;
	}
	public boolean checkParty(int depo_code,int nm)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		boolean check=false;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 = "select account_no from accountmaster where  depo_code=? and account_no=?" ;
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, depo_code);
			ps2.setInt(2, nm);
			
			rs= ps2.executeQuery();
			if (rs.next())
			{
				 check=true;

			}

			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in PartyDAO.checkParty " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in PartyDAO.Connection.close "+e);
			}
		}

		return check;
	}

	public int  checkParty(int depo_code,String  nm,String city,int div_code)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		int check=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 = "select account_no from accountmaster where  depo_code=? and mac_name=? and mcity=? and div_code=1 " ;
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, depo_code);
			ps2.setString(2, nm);
			ps2.setString(3, city);
			ps2.setInt(4, div_code);
			
			rs= ps2.executeQuery();
			if (rs.next())
			{
				 check=rs.getInt(1);

			}

			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in PartyDAO.checkParty " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in PartyDAO.Connection.close "+e);
			}
		}

		return check;
	}
  
}
