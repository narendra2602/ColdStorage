package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.coldstorage.dto.BookDto;
import com.coldstorage.view.BaseClass;



public class BookDAO 
{
      public Vector<?> getBookList(int div, int depo)
      {
    	  
    	PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  		Vector  v=null;
  		BookDto bdto=null;
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();
  			con.setAutoCommit(false);
  			
			String query2 ="select book_cd,book_nm,book_abvr,head_code,book_tp,bank_acno,bank_add,bank_add1,bank_city,ifsc_code from bookmast where  depo_code=? " +
			" and div_code=? and book_tp='B' and ifnull(del_tag,'')<>'D'  order by book_cd ";
			
  			ps2 = con.prepareStatement(query2);
  			ps2.setInt(1, depo);
  			ps2.setInt(2, div);

  			rs= ps2.executeQuery();

  			v = new Vector ();
  			Vector  col=null;
  			while (rs.next())
  			{
  				col = new Vector();
				col.add(rs.getInt(1));
				col.add(rs.getString(2));
				bdto = new BookDto();
				bdto.setBook_code(rs.getInt(1));
				bdto.setBook_name(rs.getString(2));
				bdto.setBook_abvr(rs.getString(3));
				bdto.setHead_code(rs.getInt(4));
				bdto.setBook_tp(rs.getString(5));
				bdto.setBank_acno(rs.getString(6));
				bdto.setBank_add(rs.getString(7));
				bdto.setBank_add1(rs.getString(8));
				bdto.setBank_city(rs.getString(9));
				bdto.setIfsc_code(rs.getString(10));
				
				col.add(bdto);
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
  			System.out.println("-------------Exception in SQLBookDAO.BookList " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(rs != null){rs.close();}
  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLBookDAO.Connection.close "+e);
  			}
  		}
  		return v;
  	}
////////////// petty cash account book master //////////////////
      public Vector<?> getBookList1(int div, int depo)
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
  			
			String query2 ="select book_cd,book_nm,book_tp from bookmast where  depo_code=? " +
			" and div_code=? and ifnull(del_tag,'')<>'D'  order by book_cd ";
			
  			ps2 = con.prepareStatement(query2);
  			ps2.setInt(1, depo);
  			ps2.setInt(2, div);

  			rs= ps2.executeQuery();

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
  			System.out.println("-------------Exception in SQLBookDAO.BookList1" + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(rs != null){rs.close();}
  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLBookDAO.Connection.close "+e);
  			}
  		}
  		return v;
  	}
	
	
      
      public int addBank(BookDto bdto)
      {
    	  
    	PreparedStatement ps = null;
      	ResultSet rs=null;
      	PreparedStatement ps1 = null;
      	PreparedStatement ps2 = null;
      	PreparedStatement ps3 = null;
      	ResultSet rs3=null;
    	PreparedStatement ps4 = null;
  		 
  		Connection con=null;
  		int bookcd=20;
  		int i=0;
  		int j=0;
  		int code=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			
			String query1 ="insert into partyfst (mgrp_code,mac_code,mac_name,madd1,madd2,mcity,bank_ifsc,div_code,depo_code) " +
			" values (?,?,?,?,?,?,?,?,?)";
			ps1 = con.prepareStatement(query1);

			String query2 ="insert into partyacc (mgrp_code,mopng_bal,mopng_db,div_code,depo_code,mac_code,fin_year) values (?,?,?,?,?,?,?) ";
			ps2 = con.prepareStatement(query2);

			String query = "select ifnull(max(mac_code),0) from partyfst where  div_code=? and " +
			"depo_code=? and mgrp_code=? and ifnull(del_tag,'')<>'D' "  ;

			
  			String query3="select ifnull(max(book_cd),19) from bookmast where book_cd between 20 and 25 and ifnull(del_tag,'')<>'D' ";

  			String query4="insert into Bookmast values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

  			con.setAutoCommit(false);

			

			ps = con.prepareStatement(query);
			
			ps.setInt(1,bdto.getDiv_code());
			ps.setInt(2,bdto.getDepo_code());
			ps.setInt(3, 80);
			rs= ps.executeQuery();
			if (rs.next())
			{
				if(rs.getInt(1)==0)
					code=Integer.parseInt(80+"001");
				else
					 code=rs.getInt(1)+1;
					
				 bdto.setHead_code(code);
			}

			rs.close();
			ps.close();
  			
			
  			ps3 = con.prepareStatement(query3);
  			rs3 = ps3.executeQuery();
  			if(rs3.next())
  				bookcd=rs3.getInt(1)+1;
  			rs3.close();
  			ps3.close();

  			
  			
  			ps4 = con.prepareStatement(query4);
  			ps4.setInt(1,bdto.getDiv_code());
  			ps4.setInt(2,bdto.getDepo_code());
  			ps4.setInt(3,bookcd);
  			ps4.setString(4,bdto.getBook_name());
  			ps4.setString(5,bdto.getBook_abvr());
  			ps4.setInt(6,bdto.getHead_code());
  			ps4.setString(7,bdto.getBank_acno());
  			ps4.setString(8,bdto.getBank_add()	);
  			ps4.setString(9,bdto.getBank_city());
  			ps4.setString(10,"N");
  			ps4.setString(11,bdto.getBank_add1());
  			ps4.setString(12,bdto.getIfsc_code());
  			ps4.setString(13,"B");
  			i=ps4.executeUpdate();

			
			ps1.setInt(1,80);
			ps1.setString(2,String.valueOf(code));
			ps1.setString(3,bdto.getBook_name());
			ps1.setString(4,bdto.getBank_add());
			ps1.setString(5,bdto.getBank_add1());
			ps1.setString(6,bdto.getBank_city());
			ps1.setString(7,bdto.getIfsc_code());
			ps1.setInt(8,bdto.getDiv_code());
			ps1.setInt(9,bdto.getDepo_code());
			i= ps1.executeUpdate();

			ps2.setInt(1,80);
			ps2.setDouble(2,0.00);
			ps2.setString(3,"DR");
			ps2.setInt(4,bdto.getDiv_code());
			ps2.setInt(5,bdto.getDepo_code());
			ps2.setString(6,String.valueOf(code));
			ps2.setInt(7,BaseClass.loginDt.getFin_year());

			j= ps2.executeUpdate();

  			
  			con.commit();
  			con.setAutoCommit(true);
  			ps1.close();
  			ps2.close();
  			ps3.close();
  			ps4.close();

  		} catch (SQLException ex) { ex.printStackTrace();
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLBookDAO.addHead " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps != null){ps.close();}
  				if(rs != null){rs.close();}
  				if(ps1 != null){ps1.close();}
  				if(ps2 != null){ps2.close();}
  				if(ps3 != null){ps3.close();}
  				if(rs3 != null){rs3.close();}
  				if(ps4 != null){ps4.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLBookDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}

    ///////////////////// petty cash book add /////////////////
      public int addHead1(ArrayList<?> BookList)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		BookDto gdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="insert into Bookmast (div_code,depo_code,book_cd,book_nm,book_tp,del_tag) values (?,?,?,?,?,?)";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=BookList.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (BookDto) BookList.get(j);
	  			ps2.setInt(1,gdto.getDiv_code());
	  			ps2.setInt(2,gdto.getDepo_code());
	  			ps2.setInt(3,gdto.getBook_code());
	  			ps2.setString(4,gdto.getBook_name());
	  			ps2.setString(5,gdto.getBook_tp());
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
  			System.out.println("-------------Exception in SQLBookDAO.addHead1" + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLBookDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}

      
      

      public int updateBank(BookDto bdto)
      {
    	  
    	PreparedStatement ps2 = null;
    	PreparedStatement ps3 = null;
  		 
  		Connection con=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update Bookmast set book_nm=?,book_abvr=?,bank_acno=?,bank_add=?,bank_add1=?,bank_city=?,ifsc_code=? " +
  			" where div_code=? and depo_code=? and head_code=? and book_tp='B' and ifnull(del_tag,'')<>'D' ";

  			String query3="update partyfst set mac_name=?,madd1=?,madd2=?,mcity=?,bank_ifsc=? " +
  			" where div_code=? and depo_code=? and mac_code=? and ifnull(del_tag,'')<>'D' ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			ps2.setString(1,bdto.getBook_name());
  			ps2.setString(2,bdto.getBook_abvr());
  			ps2.setString(3,bdto.getBank_acno());
  			ps2.setString(4,bdto.getBank_add());
  			ps2.setString(5,bdto.getBank_add1());
  			ps2.setString(6,bdto.getBank_city());
  			ps2.setString(7,bdto.getIfsc_code());
  			ps2.setInt(8,bdto.getDiv_code());
  			ps2.setInt(9,bdto.getDepo_code());
  			ps2.setInt(10,bdto.getHead_code());
  			i=ps2.executeUpdate();


  			ps3 = con.prepareStatement(query3);
  			ps3.setString(1,bdto.getBook_name());
  			ps3.setString(2,bdto.getBank_add());
  			ps3.setString(3,bdto.getBank_add1());
  			ps3.setString(4,bdto.getBank_city());
  			ps3.setString(5,bdto.getIfsc_code());
  			ps3.setInt(6,bdto.getDiv_code());
  			ps3.setInt(7,bdto.getDepo_code());
  			ps3.setString(8,String.valueOf(bdto.getHead_code()));
  			i=ps3.executeUpdate();

  			con.commit();
  			con.setAutoCommit(true);
  			ps2.close();
  			ps3.close();

  		} catch (SQLException ex) {
  			try {
  				con.rollback();
  			} catch (SQLException e) {
  				e.printStackTrace();
  			}
  			System.out.println("-------------Exception in SQLBookDAO.updateHead " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(ps3 != null){ps3.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLBookDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}
////////////////  petty cash update book /////////////////////
      public int updateHead1(ArrayList<?> BookList)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		BookDto gdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update Bookmast set book_nm=?,book_tp=? where " +
  			" div_code=? and depo_code=? and book_cd=? and ifnull(del_tag,'')<>'D' ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=BookList.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (BookDto) BookList.get(j);
	  			ps2.setString(1,gdto.getBook_name());
	  			ps2.setString(2,gdto.getBook_tp());
	  			ps2.setInt(3,gdto.getDiv_code());
	  			ps2.setInt(4,gdto.getDepo_code());
	  			ps2.setInt(5,gdto.getBook_code());
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
  			System.out.println("-------------Exception in SQLBookDAO.updateHead1 " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLBookDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}

      
      
      public int deleteHead(ArrayList<?> BookList)
      {
    	  
    	PreparedStatement ps2 = null;
  		 
  		Connection con=null;
  		BookDto gdto=null;
  		
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();

  			String query2="update Bookmast set del_tag=? where div_code=? and depo_code=? and book_cd=? ";

  			con.setAutoCommit(false);
			
  			ps2 = con.prepareStatement(query2);
  			int s=BookList.size();
  			for (int j=0;j<s;j++)
  			{
  				gdto= (BookDto) BookList.get(j);
	  			ps2.setString(1,"D");
	  			ps2.setInt(2,gdto.getDiv_code());
	  			ps2.setInt(3,gdto.getDepo_code());
	  			ps2.setInt(4,gdto.getBook_code());
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
  			System.out.println("-------------Exception in SQLBookDAO.deleteHead " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in BookDAO.Connection.close "+e);
  			}
  		}
  		return i;
  	}
/// HO PURCHASE VOUCER BOOK CODE LIST	            
      public Vector<?> getBookListHO(int div, int depo)
      {
    	  
    	PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  		BookDto b =null;
  		Vector  v=null;
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();
  			con.setAutoCommit(false);
  			
			String query2 ="select book_cd,concat(book_abvr,'-',substring(book_nm,19,20)),book_abvr,head_code,book_tp,bank_acno,bank_add,bank_add1,bank_city from bookmast where  depo_code=? " +
			" and div_code=? and ifnull(del_tag,'')<>'D'  order by book_cd ";
			
  			ps2 = con.prepareStatement(query2);
  			ps2.setInt(1, 8);
  			ps2.setInt(2, 4);

  			rs= ps2.executeQuery();

  			v = new Vector ();
  			while (rs.next())
  			{
  				b= new BookDto();
  				b.setBook_code(rs.getInt(1));
  				b.setBook_name(rs.getString(2));
  				b.setBook_abvr(rs.getString(3));
  				b.setHead_code(rs.getInt(4));
				v.add(b);
  				

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
  			System.out.println("-------------Exception in SQLBookDAO.BookListHO " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(rs != null){rs.close();}
  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLBookDAO.Connection.close "+e);
  			}
  		}
  		return v;
  	}
//////    
    /// HO PURCHASE VOUCER BOOK CODE LIST	            
      public HashMap getBookListHOMap(int div, int depo)
      {
    	  
    	PreparedStatement ps2 = null;
  		ResultSet rs=null;
  		Connection con=null;
  		BookDto b =null;
  		HashMap  v=null;
  		int i=0;
  		try 
  		{
  			con=ConnectionFactory.getConnection();
  			con.setAutoCommit(false);
  			
			String query2 ="select book_cd,substring(book_nm,19,20),book_abvr,head_code,book_tp,bank_acno,bank_add,bank_add1,bank_city from bookmast where  depo_code=? " +
			" and div_code=? and ifnull(del_tag,'')<>'D'  order by book_cd ";
			
  			ps2 = con.prepareStatement(query2);
  			ps2.setInt(1, 8);
  			ps2.setInt(2, 4);

  			rs= ps2.executeQuery();

  			v = new HashMap ();
  			while (rs.next())
  			{
  				b= new BookDto();
  				b.setBook_code(rs.getInt(1));
  				b.setBook_name(rs.getString(2));
  				b.setBook_abvr(rs.getString(3));
  				b.setHead_code(rs.getInt(4));
  				v.put(rs.getString(3),b);
  				

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
  			System.out.println("-------------Exception in SQLBookDAO.BookListHOMap " + ex);
  			i=-1;
  		}
  		finally {
  			try {
  				System.out.println("No. of Records Update/Insert : "+i);

  				if(rs != null){rs.close();}
  				if(ps2 != null){ps2.close();}
  				if(con != null){con.close();}
  			} catch (SQLException e) {
  				System.out.println("-------------Exception in SQLBookDAO.Connection.close "+e);
  			}
  		}
  		return v;
  	}
}
