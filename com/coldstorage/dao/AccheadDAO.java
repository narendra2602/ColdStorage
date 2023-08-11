package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.coldstorage.dto.GroupDto;



public class AccheadDAO 
{
      public Vector<?> getHeadList(int div, int depo)
      {
    	  
    	PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  
  		Vector  v=null;
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();
  			con.setAutoCommit(false);

			String query2 ="select gp_code,gp_name from acchead where   " +
			" ifnull(del_tag,'')<>'D'  order by gp_code ";
			
  			ps2 = con.prepareStatement(query2);

  			rs= ps2.executeQuery();

  			v = new Vector ();
  			Vector  col=null;
  			while (rs.next())
  			{
  				col = new Vector();
				col.add(new Boolean(false));
				col.add(rs.getInt(1));
				col.add(rs.getString(2));
				col.add("");
				v.add(col);
  				

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
  			System.out.println("-------------Exception in SQLAccheadDAO.Headlist " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(rs != null){rs.close();}
  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLAccheadDAO.Connection.close "+e);
  			}
  		}
  		return v;
  	}

	
      // method for Account Package 
      public Vector<?> getHeadList1(int div, int depo)
      {
    	  
    	PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  
  		Vector  v=null;
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();
  			con.setAutoCommit(false);

			String query2 ="select gp_code,gp_name,sub_code from acchead where   " +
			" ifnull(del_tag,'')<>'D'  order by gp_code ";
			
  			ps2 = con.prepareStatement(query2);
  
  			rs= ps2.executeQuery();

  			v = new Vector ();
  			Vector  col=null;
  			while (rs.next())
  			{
  				col = new Vector();
				col.add(new Boolean(false));
				col.add(rs.getInt(1));
				col.add(rs.getString(2));
				col.add(rs.getInt(3));
				col.add("");
				v.add(col);
  				

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
  			System.out.println("-------------Exception in SQLAccheadDAO.Headlist -- Account Package " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(rs != null){rs.close();}
  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLAccheadDAO.Connection.close "+e);
  			}
  		}
  		return v;
  	}

      
      
      public int addHead(ArrayList<?> headlist)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		GroupDto gdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="insert into acchead values (?,?,?,?)";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=headlist.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) headlist.get(j);
	  			ps2.setInt(1,gdto.getGp_code());
	  			ps2.setString(2,gdto.getGp_name());
	  			ps2.setInt(3,0);
	  			ps2.setString(4,"N");
	  			i=ps2.executeUpdate();
  			}

  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();

  		} catch (SQLException ex) {
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLAccheadDAO.addHead " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLAccheadDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}


      // method used for Account Package
      public int addHead1(ArrayList<?> headlist)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		GroupDto gdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="insert into acchead values (?,?,?,?,?,?)";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=headlist.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) headlist.get(j);
	  			ps2.setInt(1,gdto.getDiv_code());
	  			ps2.setInt(2,gdto.getDepo_code());
	  			ps2.setInt(3,gdto.getGp_code());
	  			ps2.setString(4,gdto.getGp_name());
	  			ps2.setInt(5,gdto.getSub_code());
	  			ps2.setString(6,"");
	  			i=ps2.executeUpdate();
  			}

  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();

  		} catch (SQLException ex) {
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLAccheadDAO.addHead ---- Account Package" + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLAccheadDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}      
      

      public int updateHead(ArrayList<?> headlist)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		GroupDto gdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update acchead set gp_name=? where " +
  			" div_code=? and depo_code=? and gp_code=? and ifnull(del_tag,'')<>'D' ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=headlist.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) headlist.get(j);
	  			ps2.setString(1,gdto.getGp_name());
	  			ps2.setInt(2,gdto.getDiv_code());
	  			ps2.setInt(3,gdto.getDepo_code());
	  			ps2.setInt(4,gdto.getGp_code());
	  			i=ps2.executeUpdate();
  			}

  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();

  		} catch (SQLException ex) {
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLAccheadDAO.updateHead " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLAccheadDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}

      /// method for Account Package
      public int updateHead1(ArrayList<?> headlist)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		GroupDto gdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update acchead set gp_name=?,sub_code=? where " +
  			" div_code=? and depo_code=? and gp_code=? and ifnull(del_tag,'')<>'D' ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=headlist.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) headlist.get(j);
	  			ps2.setString(1,gdto.getGp_name());
	  			ps2.setInt(2,gdto.getSub_code());
	  			ps2.setInt(3,gdto.getDiv_code());
	  			ps2.setInt(4,gdto.getDepo_code());
	  			ps2.setInt(5,gdto.getGp_code());
	  			i=ps2.executeUpdate();
  			}

  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();

  		} catch (SQLException ex) {
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLAccheadDAO.updateHead -- Account Package " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLAccheadDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}
      
      
      public int deleteHead(ArrayList<?> headlist)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		GroupDto gdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update acchead set del_tag=? where  and gp_code=? ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=headlist.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) headlist.get(j);
	  			ps2.setString(1,"D");
	  			ps2.setInt(2,gdto.getGp_code());
	  			i=ps2.executeUpdate();
  			}

  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();

  		} catch (SQLException ex) {
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLAccheadDAO.deleteHead " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in AccheadDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}
	            
      
}
