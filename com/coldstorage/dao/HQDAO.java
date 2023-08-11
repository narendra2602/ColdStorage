package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.coldstorage.dto.HQDto;


public class HQDAO 
{
      public Vector getHQList(int div, int depo,int year)
      {
    	  
    	PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  		Vector v=null;
  		int i=0;
  		if (div >4 )
  			div=div-4;

  		try 
  		{
  			con=ConnectionFactory.getConnection();


			String query2 ="select h.ter_code,h.ter_name,h.txt,h.state_code,s.state_name,h.area_code,a.area_name,h.regn_code,r.reg_name, "+
			"h.oct,h.nov,h.decm,h.jan,h.feb,h.mar,h.apr,h.may,h.jun,h.jul,h.aug,h.sep "+
			"from hqmast as h, statemast as s,areamast as a,regionmast as r "+
			"where h.state_code=s.state_code and h.area_code=a.area_cd and h.regn_code=r.reg_code "+
			"and  h.depo_code=? and h.div_code=? and h.mkt_year=? "+
			"and  s.depo_code=? and s.div_code=? and s.mkt_year=? "+
			"and  a.depo_code=? and a.div_code=? and a.mkt_year=? "+
			"and  r.depo_code=? and r.div_code=? and r.mkt_year=? "+
			"and  h.del_tag<>'D' "+
			"order by h.state_code,h.area_code,h.regn_code,h.ter_code ";
			
  			ps2 = con.prepareStatement(query2);
  			ps2.setInt(1, depo);
  			ps2.setInt(2, div);
  			ps2.setInt(3, year);
  			ps2.setInt(4, depo);
  			ps2.setInt(5, div);
  			ps2.setInt(6, year);
  			ps2.setInt(7, depo);
  			ps2.setInt(8, div);
  			ps2.setInt(9, year);
  			ps2.setInt(10, depo);
  			ps2.setInt(11, div);
  			ps2.setInt(12, year);

  			rs= ps2.executeQuery();
  			con.setAutoCommit(false);
  			v = new Vector();
  			Vector col=null;
  			while (rs.next())
  			{
  				col = new Vector();
				col.add(new Boolean(false));
				col.add(rs.getInt(1));
				col.add(rs.getString(2));
				col.add(rs.getString(3));
				col.add(rs.getInt(4));
				col.add(rs.getString(5));
				col.add(rs.getInt(6));
				col.add(rs.getString(7));
				col.add(rs.getInt(8));
				col.add(rs.getString(9));
				col.add(rs.getInt(10));
				col.add(rs.getInt(11));
				col.add(rs.getInt(12));
				col.add(rs.getInt(13));
				col.add(rs.getInt(14));
				col.add(rs.getInt(15));
				col.add(rs.getInt(16));
				col.add(rs.getInt(17));
				col.add(rs.getInt(18));
				col.add(rs.getInt(19));
				col.add(rs.getInt(20));
				col.add(rs.getInt(21));
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
  			System.out.println("-------------Exception in SQLHQDAO.HQlist " + ex);
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

	
	
      
      public int addHQ(ArrayList<?> hqlist)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		HQDto hdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="insert into hqmast values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=hqlist.size();
  			for (int j=0;j<s;j++)
  			{
  				hdto= (HQDto) hqlist.get(j);
	  			ps2.setInt(1,hdto.getDepo_code());
	  			ps2.setInt(2,hdto.getDiv_code());
	  			ps2.setInt(3,hdto.getTer_code());
	  			ps2.setInt(4,hdto.getState_code());
	  			ps2.setInt(5,hdto.getArea_code());
	  			ps2.setInt(6,hdto.getRegn_code());
	  			ps2.setString(7,hdto.getTer_name());
	  			ps2.setString(8,hdto.getTxt());
	  			ps2.setInt(9,hdto.getOct());
	  			ps2.setInt(10,hdto.getNov());
	  			ps2.setInt(11,hdto.getDecm());
	  			ps2.setInt(12,hdto.getJan());
	  			ps2.setInt(13,hdto.getFeb());
	  			ps2.setInt(14,hdto.getMar());
	  			ps2.setInt(15,hdto.getApr());
	  			ps2.setInt(16,hdto.getMay());
	  			ps2.setInt(17,hdto.getJun());
	  			ps2.setInt(18,hdto.getJul());
	  			ps2.setInt(19,hdto.getAug());
	  			ps2.setInt(20,hdto.getSep());
	  			ps2.setInt(21,hdto.getMkt_year());
	  			ps2.setString(22," ");
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
  			System.out.println("-------------Exception in SQLHQDAO.addHQ " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLHQDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}


      public int updateHQ(ArrayList<?> hqlist)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		HQDto hdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update hqmast set state_code=?,area_code=?,regn_code=?,ter_name=?,txt=?,oct=?,nov=?,decm=?,jan=?,feb=?,mar=?,apr=?,may=?,jun=?,jul=?,aug=?,sep=? "+
  			" where mkt_year=? and div_code=? and depo_code=? and ter_code=? ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=hqlist.size();
  			System.out.println("size of hq list is "+s);
  			for (int j=0;j<s;j++)
  			{
  				hdto= (HQDto) hqlist.get(j);
	  			ps2.setInt(1,hdto.getState_code());
	  			ps2.setInt(2,hdto.getArea_code());
	  			ps2.setInt(3,hdto.getRegn_code());
	  			ps2.setString(4,hdto.getTer_name());
	  			ps2.setString(5,hdto.getTxt());
	  			ps2.setInt(6,hdto.getOct());
	  			ps2.setInt(7,hdto.getNov());
	  			ps2.setInt(8,hdto.getDecm());
	  			ps2.setInt(9,hdto.getJan());
	  			ps2.setInt(10,hdto.getFeb());
	  			ps2.setInt(11,hdto.getMar());
	  			ps2.setInt(12,hdto.getApr());
	  			ps2.setInt(13,hdto.getMay());
	  			ps2.setInt(14,hdto.getJun());
	  			ps2.setInt(15,hdto.getJul());
	  			ps2.setInt(16,hdto.getAug());
	  			ps2.setInt(17,hdto.getSep());
	  			ps2.setInt(18,hdto.getMkt_year());
	  			//ps2.setInt(18,2011);
	  			ps2.setInt(19,hdto.getDiv_code());
	  			ps2.setInt(20,hdto.getDepo_code());
	  			ps2.setInt(21,hdto.getTer_code());
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
  			System.out.println("-------------Exception in SQLHQDAO.updateHQ " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLHQDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}

      public int deleteHQ(ArrayList<?> hqlist)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		HQDto hdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update hqmast set del_tag=? where mkt_year=? and div_code=? and depo_code=? and ter_code=? ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=hqlist.size();
  			for (int j=0;j<s;j++)
  			{
  				hdto= (HQDto) hqlist.get(j);
	  			ps2.setString(1,"D");
	  			ps2.setInt(2,hdto.getMkt_year());
	  			ps2.setInt(3,hdto.getDiv_code());
	  			ps2.setInt(4,hdto.getDepo_code());
	  			ps2.setInt(5,hdto.getTer_code());
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
  			System.out.println("-------------Exception in SQLHQDAO.deleteHQ " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLHQDAO.Connection.close "+e);
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
      
      
}
