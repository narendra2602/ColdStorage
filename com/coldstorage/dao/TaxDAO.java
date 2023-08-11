package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.coldstorage.dto.TaxDto;

public class TaxDAO
{

	public Map getTaxType(int stateCode,int depo,Date taxdate)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Map m=null;
		TaxDto t=null;
		int i=0;
		int mst=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			 
			String query2 ="select tax_type,tax_per,tax_on,add_tax1,add_tax2,tax_on_free,disc,exc_rate,cst_reim,ctax_per,itax_per,cess_per,tax_desc " +
			" from taxmast where depo_code=? and state_code=?  and ? between sdate and edate ";
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, depo);
			ps2.setInt(2, stateCode);
			ps2.setDate(3, new java.sql.Date(taxdate.getTime()));

			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			m = new HashMap();
			
			
			while (rs.next())
			{
				System.out.println("TAXTYPE IN TAXDAO IS "+rs.getString(1));
				 
				t = new TaxDto();
				t.setTax_per(rs.getDouble(2));
				t.setTax_on(rs.getString(3));
				t.setAdd_tax1(rs.getDouble(4));
				t.setAdd_tax2(rs.getDouble(5));
				t.setTax_on_free(rs.getString(6));
				t.setDisc(rs.getString(7));
				t.setExc_rate(rs.getDouble(8));
				t.setCst_reim(rs.getString(9));
				t.setCgst_per(rs.getDouble(10));
				t.setIgst_per(rs.getDouble(11));
				t.setCess_per(rs.getDouble(12));
				t.setTax_desc(rs.getString(13));
				
				
				m.put(rs.getString(1),t);

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
			System.out.println("-------------Exception in SQLTaxDAO.getTaxType " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLTaxDAO.Connection.close "+e);
			}
		}
		return m;
	}



	public Vector getTaxList(int div, int depo,int year,String gsttype)
	{

		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
		int i=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			
			String query2 ="select t.state_code,s.state_name,t.tax_type,t.tax_desc,t.tax_per,"+
			"t.tax_on ,ifnull(t.tax_on_free,''),t.add_tax1,ifnull(t.add_desc1,''),t.add_tax2," +
			"ifnull(t.add_desc2,''),t.sdate,t.edate,t.serialno,t.disc,t.exc_rate,t.cst_reim," +
			"t.ctax_per,t.itax_per,t.cess_per "+
			"from  statemast as s,taxmast as t "+
			"where t.state_code=s.state_code  "+
			"and  s.depo_code=? and s.div_code=? and s.mkt_year=? "+ 
			"and  t.depo_code=?  and t.gst_type=? "+
			"order by t.state_code,t.tax_type,t.edate";

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, depo);
			ps2.setInt(2, div);
			ps2.setInt(3, year);
			ps2.setInt(4, depo);
			ps2.setString(5, gsttype);

			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			Vector col=null;
			while (rs.next())
			{
				
				col = new Vector();
				
				col.add(rs.getInt(1));     // state code 0
				col.add(rs.getString(2));  // state name 1
				col.add(rs.getString(3));  // tax type 2
				col.add(rs.getString(4)); // tax desc 3
				col.add(rs.getDouble(5));  // tax per 4
				if(gsttype.equalsIgnoreCase("N"))
				{
					col.add(rs.getString(6)); // tax on 5
					col.add(rs.getString(7)); // tax on free 6
					col.add(rs.getString(15)); // disc y/n 7
					col.add(rs.getDouble(8)); // add tax1 8
					col.add(rs.getString(9)); // add tax1 desc 9
					col.add(rs.getDouble(10)); // add tax2 10
					col.add(rs.getString(11));  // add tax2 desc 11
					col.add(rs.getString(17));  // cst reimburse 12
				}
				else
				{

					col.add(rs.getDouble(18));  // ctax per 5
					col.add(rs.getDouble(19));  // itax per 6
					col.add(rs.getDouble(20));  // cess per 7
					col.add(rs.getString(6)); // tax on 8
					col.add(rs.getString(7)); // tax on free 9
					col.add(rs.getString(15)); // disc y/n 10
					col.add(rs.getDouble(8)); // add tax1 11
					col.add(rs.getDouble(10)); // add tax2 12
					
				}
				col.add(rs.getDouble(16));  // exchange  rate 13
				
				
				try
				{
				 col.add(sdf.format(rs.getDate(12))); // starting date 14
				 col.add(sdf.format(rs.getDate(13)));  // ending date 15
				}
				catch(Exception e){
					 col.add("  /  /    ");
					 col.add("  /  /    ");
					//System.out.println("parsing eroorrr "+e);
				}
				col.add(rs.getInt(14));  // serial no 16
				col.add(""); 
				v.add(col);


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
			System.out.println("-------------Exception in SQLTaxDAO.Taxlist " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLTaxDAO.Connection.close "+e);
			}
		}
		return v;
	}



	public int addTax(ArrayList<?> taxlist)
	{

		PreparedStatement ps2 = null;

		Connection con=null;
		TaxDto taxdto=null;

		int i=0;
		try 
		{
			con=ConnectionFactory.getConnection();

			String query2="insert into taxmast values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			con.setAutoCommit(false);

			ps2 = con.prepareStatement(query2);
			int s=taxlist.size();
			for (int j=0;j<s;j++)
			{
				taxdto= (TaxDto) taxlist.get(j);
				ps2.setInt(1,taxdto.getDepo_code());
				ps2.setInt(2,taxdto.getState_code());
				ps2.setString(3,taxdto.getTax_type());
				ps2.setString(4,taxdto.getTax_desc());
				ps2.setDouble(5,taxdto.getTax_per());
				ps2.setString(6,taxdto.getTax_on());
				ps2.setString(7,taxdto.getTax_on_free());
				ps2.setDouble(8,taxdto.getAdd_tax1());
				ps2.setString(9,taxdto.getAdd_desc1());
				ps2.setDouble(10,taxdto.getAdd_tax2());
				ps2.setString(11,taxdto.getAdd_desc2());
				ps2.setString(12,"");
				ps2.setDate(13,new java.sql.Date(taxdto.getSdate().getTime()));
				ps2.setDate(14,new java.sql.Date(taxdto.getEdate().getTime()));
				ps2.setInt(15,taxdto.getSerialno());
				ps2.setString(16,taxdto.getDisc());
				ps2.setDouble(17,taxdto.getExc_rate());
				ps2.setString(18,taxdto.getCst_reim());
				ps2.setDouble(19,taxdto.getCgst_per());
				ps2.setDouble(20,taxdto.getIgst_per());
				ps2.setDouble(21,taxdto.getCess_per());
				ps2.setString(22,taxdto.getGst_type());
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
			System.out.println("-------------Exception in SQLTAXDAO.addTax " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLTAXDAO.Connection.close "+e);
			}
		}
		return i;
	}


	
    public int updateTax(ArrayList<?> taxlist)
    {
  	  
  	PreparedStatement ps2 = null;
		 
		Connection con=null;
		TaxDto taxdto=null;
		
		int i=0;
		try 
		{
			con=ConnectionFactory.getConnection();

			String query2="update taxmast set tax_desc=?,tax_per=?, " +
			" tax_on=?,tax_on_free=?,add_tax1=?,add_desc1=?,add_tax2=?,add_desc2=?, " +
			" sdate=?,edate=?,disc=?,exc_rate=?,tax_type=?,state_code=?,cst_reim=?,ctax_per=?,itax_per=?,cess_per=?  "+
			" where depo_code=?  and serialno=? ";

			con.setAutoCommit(false);
			
			ps2 = con.prepareStatement(query2);
			int s=taxlist.size();
			for (int j=0;j<s;j++)
			{
				taxdto= (TaxDto) taxlist.get(j);
				ps2.setString(1, taxdto.getTax_desc());
				ps2.setDouble(2, taxdto.getTax_per());
				ps2.setString(3, taxdto.getTax_on());
				ps2.setString(4, taxdto.getTax_on_free());
				ps2.setDouble(5, taxdto.getAdd_tax1());
				ps2.setString(6, taxdto.getAdd_desc1());
				ps2.setDouble(7, taxdto.getAdd_tax2());
				ps2.setString(8, taxdto.getAdd_desc2());
				ps2.setDate(9, new java.sql.Date(taxdto.getSdate().getTime()));
				ps2.setDate(10, new java.sql.Date(taxdto.getEdate().getTime()));
				ps2.setString(11, taxdto.getDisc());
				ps2.setDouble(12, taxdto.getExc_rate());
	  			ps2.setString(13,taxdto.getTax_type());
				ps2.setInt(14,taxdto.getState_code());
	  			ps2.setString(15,taxdto.getCst_reim());
				ps2.setDouble(16, taxdto.getCgst_per());
				ps2.setDouble(17, taxdto.getIgst_per());
				ps2.setDouble(18, taxdto.getCess_per());
				
	  			// where clause
				ps2.setInt(19,taxdto.getDepo_code());
	  			ps2.setInt(20,taxdto.getSerialno());
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
			System.out.println("-------------Exception in SQLTAXDAO.updateTax " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLTAXDAO.Connection.close "+e);
			}
		}
		return i;
	}	

    
    public int updateNewYearTax(int depo,int year)
    {
  	  
  	PreparedStatement ps2 = null;
		 
		Connection con=null;
		Date sdate=null;
		Date edate=null;
		
		int i=0;
		try 
		{
			con=ConnectionFactory.getConnection();

			
		  	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				sdate = sdf.parse("31/03/2022");
				edate = sdf.parse("31/03/2023");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			
			String query2="update taxmast set edate=? where depo_code=?  and edate=? ";

			con.setAutoCommit(false);
			
			ps2 = con.prepareStatement(query2);
			ps2.setDate(1, new java.sql.Date(edate.getTime()));
			ps2.setInt(2,depo);
			ps2.setDate(3, new java.sql.Date(sdate.getTime()));
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
			System.out.println("-------------Exception in SQLTAXDAO.updateNewyearTax " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLTAXDAO.Connection.close "+e);
			}
		}
		return i;
	}	
  
    
}
