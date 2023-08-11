package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.coldstorage.dto.GroupDto;


public class GroupDAO 
{
      public Vector<?> getGroupList()
      {
    	  
    	PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  		Vector  v=null;
  		int i=0;
  		String query2=null;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			
  				query2 ="select gp_code,gp_name,gp_name_hindi from grpmast where    ifnull(del_tag,'')<>'D' order by gp_name ";
 			
  			ps2 = con.prepareStatement(query2);


  			rs= ps2.executeQuery();
  			con.setAutoCommit(false);
  			v = new Vector ();
  			Vector  col=null;
  			while (rs.next())
  			{
  				col = new Vector();
				col.add(new Boolean(false));
				col.add(rs.getInt(1));
				col.add(rs.getString(2));
				col.add(rs.getString(3));
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


  	public HashMap getGrpMap(int div)
  	{
  		PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  		HashMap v=null;
  		GroupDto g=null;
  		int i=0;

  		try {

  			con=ConnectionFactory.getConnection();

			String query2 ="select gp_code,gp_name from grpmast where  div_code=? and  ifnull(del_tag,'')<>'D' order by gp_name ";
  			
  			ps2 = con.prepareStatement(query2);
  			ps2.setInt(1, div);
  			rs= ps2.executeQuery();
  			con.setAutoCommit(false);
  			v = new HashMap();
  			while (rs.next())
  			{
  				g = new GroupDto();
  				g.setGp_code(rs.getInt(1));
  				g.setGp_name(rs.getString(2));
 				v.put(rs.getInt(1),g);
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
  			System.out.println("-------------Exception in SQLProductDAO.groupMAp " + ex);
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

      
      
      
      public Vector<?> getGrpList(int div)
      {
    	  
    	PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  		Vector  v=null;
  		GroupDto gp=null;
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();


			String query2 ="select gp_code,gp_name from grpmast where  div_code=? and  del_tag<>'D' order by gp_name ";
			
  			ps2 = con.prepareStatement(query2);
  			ps2.setInt(1, div);

  			rs= ps2.executeQuery();
  			con.setAutoCommit(false);
  			v = new Vector ();
  			
  			while (rs.next())
  			{
  				gp = new GroupDto();
  				gp.setGp_code(rs.getInt(1));
  				gp.setGp_name(rs.getString(2));
				v.add(gp);
  				

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
  			System.out.println("-------------Exception in SQLGroupDAO.GrpList for Marketing Reports " + ex);
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

      
      public int addGroup(ArrayList<?> grplist,int type)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		GroupDto gdto=null;
  		String query2=null;
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			if(type==1)
  				query2="insert into grpmast  (gp_code,gp_name,gp_name_hindi) values (?,?,?)";
  			if(type==2)
  				query2="insert into cmpmast (cmp_code,cmp_name,del_tag) values (?,?,?)";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=grplist.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) grplist.get(j);
	  			ps2.setInt(1,gdto.getGp_code());
	  			ps2.setString(2,gdto.getGp_name());
	  			ps2.setString(3,gdto.getGp_name_hindi());
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
  			System.out.println("-------------Exception in SQLGroupDAO.addGroup " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}


      
      public int addSampleCategory(ArrayList<?> grplist)
      {
    	  
    	PreparedStatement ps2 = null;
  		Connection con=null;
  		GroupDto gdto=null;
  		
  		int i=0;
  		int lastDocCode=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();
  			con.setAutoCommit(false);
  			
  			
  			String query2="insert into docmast  values (?,?,?,?)";
  			
  			String lastNo = "select ifnull(max(doc_code),0) from docmast ";
  					
  			ps2 = con.prepareStatement(lastNo);
  			ResultSet rs = ps2.executeQuery();
  			if(rs.next())
  			{
  				lastDocCode=rs.getInt(1)+1;
  			}
  			rs.close();
  			rs=null;
  			
  			
  			ps2.close();
  			ps2=null;
  			ps2 = con.prepareStatement(query2);
  			int s=grplist.size();
  			
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) grplist.get(j);
  				ps2.setInt(1,gdto.getDiv_code());
	  			ps2.setInt(2,lastDocCode+j);
	  			ps2.setString(3,gdto.getGp_name());
	  			ps2.setString(4,gdto.getGp_type());
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
  			System.out.println("-------------Exception in SQLGroupDAO.addSampleCategory " + ex);
  			i=-1;
  			ex.printStackTrace();
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}

      
      public int updateGroup(ArrayList<?> grplist,int type)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		GroupDto gdto=null;
  		
  		int i=0;
  		String query2=null;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			if(type==1)
  				query2="update grpmast set gp_name=?,gp_name_hindi=? where gp_code=? ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=grplist.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) grplist.get(j);
	  			ps2.setString(1,gdto.getGp_name());
	  			ps2.setString(2,gdto.getGp_name_hindi());
	  			// where clause
	  			ps2.setInt(3,gdto.getGp_code());
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
  			System.out.println("-------------Exception in SQLGroupDAO.updateGroup " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}

      
      public int updateSampleCategory(ArrayList<?> grplist)
      {
    	PreparedStatement ps2 = null;
  		Connection con=null;
  		GroupDto gdto=null;
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update docmast set doc_name=?,doc_tp=? where div_code=? and doc_code=? ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=grplist.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) grplist.get(j);
	  			ps2.setString(1,gdto.getGp_name());
	  			ps2.setString(2,gdto.getGp_type());
	  			ps2.setInt(3,gdto.getDiv_code());
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
  			System.out.println("-------------Exception in SQLGroupDAO.updateSampleCategory " + ex);
  			i=-1;
  			
  			ex.printStackTrace();
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	} 
      
      public int deleteGroup(ArrayList<?> grplist)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		GroupDto gdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update grpmast set del_tag=? where div_code=? and gp_code=? ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=grplist.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (GroupDto) grplist.get(j);
	  			ps2.setString(1,"D");
	  			ps2.setInt(2,gdto.getDiv_code());
	  			ps2.setInt(3,gdto.getGp_code());
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
  			System.out.println("-------------Exception in SQLGroupDAO.deleteGroup " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLGroupDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}
	            
     /**
      * 
      * @param div
      * @return  data vector
      * method for getting category list for sample program.
      */
      public Vector<?> getCategoryList(int div)
      {
    	PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  		Vector  v=null;
  		int i=0;
  		
  		try 
  		{
  			con=ConnectionFactory.getConnection();

			String query2 ="select doc_code,doc_name,doc_tp from docmast order by doc_tp ";
			
  			ps2 = con.prepareStatement(query2);


  			rs= ps2.executeQuery();
  			con.setAutoCommit(false);
  			v = new Vector ();
  			Vector  col=null;
  			
  			while (rs.next())
  			{
  				col = new Vector();
				col.add(new Boolean(false));
				col.add(rs.getString(3));
				col.add(rs.getString(2));
				col.add(rs.getInt(1));
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
  			System.out.println("-------------Exception in SQLGroupDAO.Category " + ex);
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
      
      
      
}
