package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.ProductDto;

public class ProductDAO 
{
	public int addProduct(ProductDto p)
	{

		PreparedStatement ps1 = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int code=0;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();

			String query1 ="insert into prd (depo_code,pname,pack,pname_hindi,pd_group,rate,div_code )" +
			" value (?,?,?,?,?,?,?) "	;	
			
			String query = "select ifnull(max(pcode),0) from prd where depo_code=? and div_code=? "  ;

			
			ps1 = con.prepareStatement(query1);
			ps = con.prepareStatement(query);


			// create query here
			con.setAutoCommit(false);

			
			ps.setInt(1,p.getDepo_code());
			ps.setInt(2, p.getDiv_code());
			
			rs= ps.executeQuery();
			if (rs.next())
			{
				 code=rs.getInt(1)+1;
					
			}

			rs.close();

			
			ps1.setInt(1,p.getDepo_code());
			ps1.setString(2,p.getPname());
			ps1.setString(3, p.getPack());
			ps1.setString(4, p.getPack_name());
			ps1.setInt(5,p.getGroup_code());
			ps1.setDouble(6,p.getNet_rt1());
			ps1.setInt(7,p.getDiv_code());
			i= ps1.executeUpdate();
			

			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in ProductDAO.addProduct " + ex);
			i=-1;
			code=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in ProductDAO.Connection.close "+e);
			}
		}

		return (code);
	}


	public Vector getPrdList(int div,int depo,int cmpcd)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		ProductDto p=null;
		int i=0;
		String cmp= "";
		if(cmpcd>0)
			cmp= " and cmp_cd="+cmpcd;
		
		try {

			con=ConnectionFactory.getConnection();
 			String query2 ="select pcode,pname,pack,rate,ifnull(pname_hindi,''),pd_group" +
			"  from prd    order by pname ";
			
			
			ps2 = con.prepareStatement(query2);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			while (rs.next())
			{
				p = new ProductDto();
				p.setPcode(rs.getInt(1));
				p.setPname(rs.getString(2));
				p.setPack(rs.getString(3));
				p.setNet_rt1(rs.getDouble(4));
				p.setPack_name(rs.getString(5));
				p.setGroup_code(rs.getInt(6));  // group code
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
			System.out.println("-------------Exception in SQLPProductDAO.productlist " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return v;
	}


		
	
		
	
	
	public HashMap getPrdMap(int div,int depo,int cmpcd)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		HashMap v=null;
		ProductDto p=null;
		int i=0;
		
		String cmp= "";
		if(cmpcd>0)
			cmp= " and cmp_cd="+cmpcd;


		try {

			con=ConnectionFactory.getConnection();
 			String query2 ="select pcode,pname,pack,rate,ifnull(pname_hindi,''),pd_group" +
 			"  from prd    order by pname ";

			
			ps2 = con.prepareStatement(query2);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new HashMap();
			while (rs.next())
			{
				p = new ProductDto();
				p.setPcode(rs.getInt(1));
				p.setPname(rs.getString(2));
				p.setPack(rs.getString(3));
				p.setNet_rt1(rs.getDouble(4));
				p.setPack_name(rs.getString(5));
				p.setGroup_code(rs.getInt(6));  

				v.put(rs.getString(1),p);
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
			System.out.println("-------------Exception in SQLProductDAO.productMAp " + ex);
			i=-1;
		}
		finally {
			try {

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return v;
	}

	
	public int getStock(int div,int depo,int pcode)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		int i=0;
		

		try {

			con=ConnectionFactory.getConnection();
 			String query2 ="select ifnull(prd_stck,0)  from prd  where div_code=? and depo_code=?  and pcode =?   ";

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setInt (3, pcode);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			
			if (rs.next())
			{
				i=rs.getInt(1);
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
			System.out.println("-------------Exception in SQLProductDAO.getStock " + ex);
			i=-1;
		}
		finally {
			try {

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return i;
	}

	

	public Vector getPrdDetail(int depo,int div_code)
	{
		
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		ProductDto p=null;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select p.pcode,p.pname,p.pack,ifnull(p.pname_hindi,''),p.pd_group,gg.gp_name,"+
			" p.rate "+
			" from prd as p , grpmast as gg where "+ 
			" p.pd_group=gg.gp_code and p.div_code=? and gg.div_code=?  order by p.pname";

			

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div_code);
			ps2.setInt(2, div_code);
			
			rs= ps2.executeQuery();
			v = new Vector();
			while (rs.next())
			{
				
				col= new Vector();
				col.addElement(rs.getString(1));//pcode
				col.addElement(rs.getString(2));//pname
				
				p = new ProductDto();
				p.setPcode(rs.getInt(1));
				p.setPname(rs.getString(2));
				p.setPack(rs.getString(3));
				p.setPack_name(rs.getString(4));  // short name
				p.setGroup_code(rs.getInt(5));
				p.setGroup_name(rs.getString(6));
				p.setNet_rt1(rs.getDouble(7));
				
				col.addElement(p);/// add productDto in hidden column
				v.addElement(col);
				 

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} 
		catch(SQLException ex) 
		{
			System.out.println("error in getPrdDetail "+ex );
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPProductDAO.prdDetail " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return v;
	}
	
	public int lastPcode(int depo,int div)
	{
		
		PreparedStatement ps1 = null;
		ResultSet rs1=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		ProductDto p=null;
		int i=0;
		int lastno=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query1="select max(ifnull(pcode,0)) from prd where div_code=?";
			

			ps1 = con.prepareStatement(query1);
			ps1.setInt(1, div);
			
			rs1= ps1.executeQuery();
			if(rs1.next())
			{
				lastno=rs1.getInt(1);
			}
			
			rs1.close();

			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} 
		catch(SQLException ex) 
		{
			System.out.println("error in getPrdDetail "+ex );
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPProductDAO.lastPcode " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return lastno;
	}

	public boolean checkProduct(int div,int pcode)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		boolean check=false;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 = "select pcode from prd where  div_code=? and pcode=?" ;
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, pcode);
			
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
			System.out.println("-------------Exception in ProductDAO.checkProduct" + ex);

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
	

	public int updateProduct(ProductDto p)
	{

		PreparedStatement ps1 = null;
		Connection con=null;

		int i=0;
		int j=0;
		 

		try {

			con=ConnectionFactory.getConnection();


			String query1 ="update prd set pname=?,pack=?,pname_hindi=?,pd_group=?," +
			" rate=? " +
			" where  pcode=? and div_code=? " ;
			
			ps1 = con.prepareStatement(query1);

			con.setAutoCommit(false);

			ps1.setString(1,p.getPname());
			ps1.setString(2, p.getPack());
			ps1.setString(3, p.getPack_name());
			ps1.setInt(4,p.getGroup_code());
			ps1.setDouble(5,p.getNet_rt1());
			// where clause
			ps1.setInt(6,p.getPcode());
			ps1.setInt(7,p.getDiv_code());
			 
			i= ps1.executeUpdate();



			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLProductDAO.update " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i+j);

				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return (i+j);
	}	

	
	
	

	
	public Vector getGroupDetail(int depo,int div)
	{
		
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		GroupDto p=null;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select gp_code,gp_name,ifnull(gp_name_hindi,'')  from grpmast where div_code=? "; 

			

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			
			rs= ps2.executeQuery();
			v = new Vector();
			while (rs.next())
			{
				
				col= new Vector();
				col.addElement(rs.getString(1));//pcode
				col.addElement(rs.getString(2));//pname
				
				p = new GroupDto();
				p.setGp_code(rs.getInt(1));
				p.setGp_name(rs.getString(2));
				p.setGp_name_hindi(rs.getString(3));  // short name
				
				col.addElement(p);/// add productDto in hidden column
				v.addElement(col);
				 

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} 
		catch(SQLException ex) 
		{
			System.out.println("error in getGroupDetail "+ex );
			ex.printStackTrace();
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPProductDAO.GroupDetail " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return v;
	}
	
	
	public int lastGpcode(int depo,int div)
	{
		
		PreparedStatement ps1 = null;
		ResultSet rs1=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		ProductDto p=null;
		int i=0;
		int lastno=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query1="select max(ifnull(gp_code,0)) from grpmast where div_code=? ";
			

			ps1 = con.prepareStatement(query1);
			
			ps1.setInt(1, div);
			rs1= ps1.executeQuery();
			if(rs1.next())
			{
				lastno=rs1.getInt(1);
			}
			
			rs1.close();

			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} 
		catch(SQLException ex) 
		{
			System.out.println("error in lastGpcode "+ex );
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPProductDAO.lastPcode " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return lastno;
	}

	public int addGroup(GroupDto p)
	{

		PreparedStatement ps1 = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int code=0;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();

			String query1 ="insert into grpmast  (gp_name,gp_name_hindi,div_code) values (?,?,?) ";
			
			
			String query = "select ifnull(max(gp_code),0) from grpmast  where div_code=?  "  ;

			
			ps1 = con.prepareStatement(query1);
			ps = con.prepareStatement(query);
			ps.setInt(1, p.getDiv_code());

			// create query here
			con.setAutoCommit(false);

			
			
			
			rs= ps.executeQuery();
			if (rs.next())
			{
				 code=rs.getInt(1)+1;
					
			}

			rs.close();

			
			
			ps1.setString(1,p.getGp_name());
			ps1.setString(2, p.getGp_name_hindi());
			ps1.setInt(3,p.getDiv_code());
			i= ps1.executeUpdate();
			

			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			ps.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in ProductDAO.addGroup" + ex);
			i=-1;
			code=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(ps != null){ps.close();}
				if(rs != null){rs.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in ProductDAO.Connection.close "+e);
			}
		}

		return (code);
	}
	
	public int updateGroup(GroupDto p)
	{

		PreparedStatement ps1 = null;
		Connection con=null;

		int i=0;
		int j=0;
		 

		try {

			con=ConnectionFactory.getConnection();


			String query1 ="update grpmast set gp_name=?,gp_name_hindi=? " +
			" where  gp_code=? and div_code=? " ;
			
			ps1 = con.prepareStatement(query1);

			con.setAutoCommit(false);

			ps1.setString(1,p.getGp_name());
			ps1.setString(2, p.getGp_name_hindi());
			// where clause
			ps1.setInt(3,p.getGp_code());
			ps1.setInt(4, p.getDiv_code());
			 
			i= ps1.executeUpdate();



			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLProductDAO.update " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i+j);

				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return (i+j);
	}	
	public Vector getPackDetail(int depo)
	{
		
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		GroupDto p=null;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select pack_code,pack_name,ifnull(pack_name_hindi,'')  from packmast  "; 

			

			ps2 = con.prepareStatement(query2);
			
			rs= ps2.executeQuery();
			v = new Vector();
			while (rs.next())
			{
				
				col= new Vector();
				col.addElement(rs.getString(1));//pcode
				col.addElement(rs.getString(2));//pname
				
				p = new GroupDto();
				p.setGp_code(rs.getInt(1));
				p.setGp_name(rs.getString(2));
				p.setGp_name_hindi(rs.getString(3));  // short name
				
				col.addElement(p);/// add productDto in hidden column
				v.addElement(col);
				 

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} 
		catch(SQLException ex) 
		{
			System.out.println("error in getPackDetail "+ex );
			ex.printStackTrace();
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPProductDAO.PackDetail " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return v;
	}
	public int lastPackcode(int depo)
	{
		
		PreparedStatement ps1 = null;
		ResultSet rs1=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		ProductDto p=null;
		int i=0;
		int lastno=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query1="select max(ifnull(pack_code,0)) from packmast ";
			

			ps1 = con.prepareStatement(query1);
			rs1= ps1.executeQuery();
			if(rs1.next())
			{
				lastno=rs1.getInt(1);
			}
			
			rs1.close();

			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} 
		catch(SQLException ex) 
		{
			System.out.println("error in lastPackcode "+ex );
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPProductDAO.lastPackcode " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return lastno;
	}
	public int addPack(GroupDto p)
	{

		PreparedStatement ps1 = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int code=0;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();

			String query1 ="insert into packmast  (pack_name,pack_name_hindi) values (?,?) ";
			
			
			String query = "select ifnull(max(pack_code),0) from packmast  "  ;

			
			ps1 = con.prepareStatement(query1);
			ps = con.prepareStatement(query);


			// create query here
			con.setAutoCommit(false);

			
			
			
			rs= ps.executeQuery();
			if (rs.next())
			{
				 code=rs.getInt(1)+1;
					
			}

			rs.close();

			
			
			ps1.setString(1,p.getGp_name());
			ps1.setString(2, p.getGp_name_hindi());
			i= ps1.executeUpdate();
			

			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			ps.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in ProductDAO.addPack" + ex);
			i=-1;
			code=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(ps != null){ps.close();}
				if(rs != null){rs.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in ProductDAO.Connection.close "+e);
			}
		}

		return (code);
	}
	public int updatePack(GroupDto p)
	{

		PreparedStatement ps1 = null;
		Connection con=null;

		int i=0;
		int j=0;
		 

		try {

			con=ConnectionFactory.getConnection();


			String query1 ="update packmast set pack_name=?,pack_name_hindi=? " +
			" where  pack_code=? " ;
			
			ps1 = con.prepareStatement(query1);

			con.setAutoCommit(false);

			ps1.setString(1,p.getGp_name());
			ps1.setString(2, p.getGp_name_hindi());
			// where clause
			ps1.setInt(3,p.getGp_code());
			 
			i= ps1.executeUpdate();



			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLProductDAO.update " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i+j);

				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return (i+j);
	}	

	public int addCategory(GroupDto p)
	{

		PreparedStatement ps1 = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int code=0;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();

			String query1 ="insert into category  (category_name,category_name_hindi) values (?,?) ";
			
			
			String query = "select ifnull(max(category_code),0) from category  "  ;

			
			ps1 = con.prepareStatement(query1);
			ps = con.prepareStatement(query);


			// create query here
			con.setAutoCommit(false);

			
			
			
			rs= ps.executeQuery();
			if (rs.next())
			{
				 code=rs.getInt(1)+1;
					
			}

			rs.close();
			System.out.println("code ki value "+code);
			
			
			ps1.setString(1,p.getGp_name());
			ps1.setString(2, p.getGp_name_hindi());
			i= ps1.executeUpdate();
			System.out.println("i ki value "+i);
			

			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			ps.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in ProductDAO.addCategory" + ex);
			i=-1;
			code=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(ps != null){ps.close();}
				if(rs != null){rs.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in ProductDAO.Connection.close "+e);
			}
		}

		return (code);
	}
	public int updateCategory(GroupDto p)
	{

		PreparedStatement ps1 = null;
		Connection con=null;

		int i=0;
		int j=0;
		 

		try {

			con=ConnectionFactory.getConnection();


			String query1 ="update category set category_name=?,category_name_hindi=? " +
			" where  category_code=? " ;
			
			ps1 = con.prepareStatement(query1);

			con.setAutoCommit(false);

			ps1.setString(1,p.getGp_name());
			ps1.setString(2, p.getGp_name_hindi());
			// where clause
			ps1.setInt(3,p.getGp_code());
			 
			i= ps1.executeUpdate();



			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLProductDAO.update " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i+j);

				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return (i+j);
	}	
	
	public Vector getCategoryDetail(int depo)
	{
		
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		GroupDto p=null;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select category_code,category_name,ifnull(category_name_hindi,'')  from category  "; 

			

			ps2 = con.prepareStatement(query2);
			
			rs= ps2.executeQuery();
			v = new Vector();
			while (rs.next())
			{
				
				col= new Vector();
				col.addElement(rs.getString(1));//pcode
				col.addElement(rs.getString(2));//pname
				
				p = new GroupDto();
				p.setGp_code(rs.getInt(1));
				p.setGp_name(rs.getString(2));
				p.setGp_name_hindi(rs.getString(3));  // short name
				
				col.addElement(p);/// add productDto in hidden column
				v.addElement(col);
				 

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} 
		catch(SQLException ex) 
		{
			System.out.println("error in getCategoryDetail "+ex );
			ex.printStackTrace();
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPProductDAO.CategoryDetail " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return v;
	}
	public int lastCategorycode(int depo)
	{
		
		PreparedStatement ps1 = null;
		ResultSet rs1=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		ProductDto p=null;
		int i=0;
		int lastno=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query1="select max(ifnull(category_code,0)) from category ";
			

			ps1 = con.prepareStatement(query1);
			rs1= ps1.executeQuery();
			if(rs1.next())
			{
				lastno=rs1.getInt(1);
			}
			
			rs1.close();

			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} 
		catch(SQLException ex) 
		{
			System.out.println("error in lastCategorycode "+ex );
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPProductDAO.categorycode " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLProductDAO.Connection.close "+e);
			}
		}

		return lastno;
	}
	
}
