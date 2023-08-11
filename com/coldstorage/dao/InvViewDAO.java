
package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.coldstorage.dto.InvViewDto;
import com.coldstorage.view.BaseClass;

public class InvViewDAO 
{

	public List getInvList(Date invDt,int depo_code,int div,int doc_tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;

		String query2="";
		try {

			con=ConnectionFactory.getConnection();

			if (depo_code==50)
			{
			query2 ="select concat(f.inv_yr,f.inv_lo,f.inv_no) as invno, p.mac_name,p.mcity,f.inv_no "+
						" from invfst as f,partyfst as p where f.party_code=p.mac_code and f.div_Code=? "+ 
						" and f.depo_code=? and f.doc_type=? and f.inv_date=?  and f.bill_amt >=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? order by f.inv_no ";
				
			}
			else
			{
			query2 ="select concat(f.inv_yr,f.inv_lo,f.inv_no) as invno, p.mac_name,p.mcity,f.inv_no "+
					" from invfst as f,partyfst as p where f.party_code=p.mac_code and f.div_Code=? "+ 
					" and f.depo_code=? and f.doc_type=? and f.inv_date=? and f.bill_amt >=1  and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? order by p.mac_name,p.mcity,f.inv_no ";
			}
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3, doc_tp);
			ps2.setDate(4, new java.sql.Date(invDt.getTime()));
			ps2.setInt(5,div);
			ps2.setInt(6,depo_code);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			while (rs.next())
			{

				col= new Vector();
				col.addElement(rs.getString(1));//concat inv_no
				col.addElement(rs.getString(2)+","+rs.getString(3));//party name
				col.addElement(rs.getString(4));///inv_no
				v.addElement(col);

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
			System.out.println("-------------Exception in InvViewDAO.getInvList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return v;
	}

// TODO get Invoice List for selected party.... (Party Search in Invoice View)
	public List getInvList(String partycd,int depo_code,int div,int year,Date sdate,Date edate)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		

		try {

			con=ConnectionFactory.getConnection();

			String query2 ="select concat(f.inv_yr,f.inv_lo,f.inv_no) as invno, p.mac_name,p.mcity,f.inv_no "+
			" from invfst as f,partyfst as p where f.fin_year=? and f.div_Code=? "+ 
			" and f.depo_code=? and f.doc_type in (39,40) and f.party_code=? and f.inv_date between ? and ? and f.bill_amt >=1 and  ifnull(f.del_tag,'')<>'D' " +
			" and p.div_code=? and p.depo_code=? and p.mac_code=?  order by f.inv_no ";

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,year);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setString(4, partycd);
			ps2.setDate(5, new java.sql.Date(sdate.getTime()));
			ps2.setDate(6, new java.sql.Date(edate.getTime()));
			ps2.setInt(7,div);
			ps2.setInt(8,depo_code);
			ps2.setString(9, partycd);
			
			
			 
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			while (rs.next())
			{

				col= new Vector();
				col.addElement(rs.getString(1));//concat inv_no
				col.addElement(rs.getString(2)+","+rs.getString(3));//party name
				col.addElement(rs.getString(4));///inv_no
				v.addElement(col);

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
			System.out.println("-------------Exception in InvViewDAO.getInvList for partySearch" + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return v;
	}

	
	


	
	
	public String getTotalNo(int div,int depo,int year,Date sdate,Date edate,String mnth,int doc_tp)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		String totinv=null;
		try {
			con=ConnectionFactory.getConnection();
			String query1="select min(inv_no),max(inv_no)  from invfst where div_code=? and depo_code=? and doc_type=?  and inv_date between ? and ?  and  ifnull(del_tag,'')<>'D' " ;
			con.setAutoCommit(false);
			if (doc_tp==0)
				doc_tp=40;

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, div);
			ps1.setInt(2, depo);
			ps1.setInt(3, doc_tp);
            ps1.setDate(4,new java.sql.Date(sdate.getTime()));
            ps1.setDate(5,new java.sql.Date(edate.getTime()));
			rst =ps1.executeQuery();

			if(rst.next()){
				totinv ="From Invoice No. "+rst.getInt(1)+" - "+rst.getInt(2)+ "  [ "+mnth+" ]";
			}

			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvViewDAO - GetTotal Invoice Note No.  " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLInvViewDAO.Connection.close "+e);
			}
		}

		return totinv;
	}	 	
	
	
	public String getMissingInv(int inv_no,int depo_code, int div, int year, Date sdate, Date edate,int doctp )
	{
		PreparedStatement ps1 = null;
		
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		String totinv=null;
		boolean check=true;
		try 
		{
			con=ConnectionFactory.getConnection();
//			String query1="select max(inv_no) from invfst where div_code=? and depo_code=? and doc_type=? and inv_date between ? and ?  and  ifnull(del_tag,'')<>'D' " ;
			String query1="select max(inv_no) from invfst where fin_year=? and div_code=? and depo_code=? and doc_type=?  and  ifnull(del_tag,'')<>'D' " ;
//			String query2="select inv_no  from invfst where div_code=? and depo_code=? and doc_type=?  and inv_no=? and inv_date between ? and ?  and  ifnull(del_tag,'')<>'D' " ;
			String query2="select inv_no  from invfst where fin_year=? and div_code=? and depo_code=? and doc_type=?  and inv_no=?  and  ifnull(del_tag,'')<>'D' " ;

			if(doctp==60 || doctp==61 || doctp==62 || doctp==72 || doctp==73)
			{
				query1="select max(inv_no) from invfst where fin_year=? and div_code=? and depo_code=? and ((doc_type between 60 and 62) or (doc_type between 72 and 73))  and  ifnull(del_tag,'')<>'D' " ;
			    query2="select inv_no  from invfst where fin_year=? and div_code=? and depo_code=? and ((doc_type between 60 and 62) or (doc_type between 72 and 73))  and inv_no=?  and  ifnull(del_tag,'')<>'D' " ;
			}

			
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo_code);
			if(doctp<60 || doctp==67 )
				ps1.setInt(4, doctp);
//            ps1.setDate(4,new java.sql.Date(sdate.getTime()));
//           ps1.setDate(5,new java.sql.Date(edate.getTime()));
			rst =ps1.executeQuery();

			if(rst.next())
			{
				if(inv_no>rst.getInt(1))
				{
					if(doctp==40 || doctp==39)
					{
						totinv ="Invoice No. Not in Missing Range, Last Invoice No. is "+rst.getInt(1);
					}
					else if(doctp==67)
					{
						totinv ="Transfer Invoice No. Not in Missing Range, Last TRF Invoice No. is "+rst.getInt(1);
					}
					else if(doctp==41) //for 41 doc_type
					{
						totinv ="Credit Note No. Not in Missing Range, Last CN No. is "+rst.getInt(1);
					}
					else if(doctp==51) //for 51 doc_type for debit note
					{
						totinv ="Debit Note No. Not in Missing Range, Last DN No. is "+rst.getInt(1);
					}
					else  //(60 61 62 72 73)
					{
						totinv ="Inward No. Not in Missing Range, Last Inward No. is "+rst.getInt(1);
					}
					check=false;
				}
			}
			
			
			if(check)
			{
				rst.close();
				ps1.close();

				ps1 =con.prepareStatement(query2);
				if(doctp<60 || doctp==67 )
				{
					ps1.setInt(1, year);
					ps1.setInt(2, div);
					ps1.setInt(3, depo_code);
					ps1.setInt(4, doctp);
					ps1.setInt(5, inv_no);
				}
				else
				{
					ps1.setInt(1, year);
					ps1.setInt(2, div);
					ps1.setInt(3, depo_code);
					ps1.setInt(4, inv_no);
				}
				//ps1.setDate(5,new java.sql.Date(sdate.getTime()));
				//ps1.setDate(6,new java.sql.Date(edate.getTime()));
				rst =ps1.executeQuery();

				if(rst.next())
				{
					if(doctp==40 || doctp==39)
					{
						totinv ="Invoice No. "+rst.getInt(1)+" Already Exist" ;
					}
					else if(doctp==67)
					{
						totinv ="Transfer Invoice No. "+rst.getInt(1)+" Already Exist" ;
					}
					else if(doctp==41) //for 41 doc_type
					{
						totinv ="Credit Note No. "+rst.getInt(1)+" Already Exist" ;
					}
					else if(doctp==51) //for 51 doc_type Debit note
					{
						totinv ="Debit Note No. "+rst.getInt(1)+" Already Exist" ;
					}
					else //(60 61 62 72 73)
					{
						totinv ="Inward No. "+rst.getInt(1)+" Already Exist" ;
					}
					
					check=false;

				}
				else
				{
					totinv ="1";
					check=false;
				}
			}

			
			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvViewDAO - GetTotal Invoice Note No.  " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLInvViewDAO.Connection.close "+e);
			}
		}

		return totinv;
	}	 	
	
	
	public String getSkipInv(int inv_no,int depo_code, int div, int year, Date sdate, Date edate,int doctp )
	{
		PreparedStatement ps1 = null;
		
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		String totinv=null;
		
		try 
		{
			con=ConnectionFactory.getConnection();

			String query2="select inv_no  from invfst where fin_year=? and div_code=? and depo_code=? and doc_type=?  and inv_no=?  and  ifnull(del_tag,'')<>'D' " ;
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query2);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo_code);
			ps1.setInt(4, doctp);
			ps1.setInt(5, inv_no);
			rst =ps1.executeQuery();

			if(rst.next())
			{
				if(doctp==40 || doctp==39)
				{
					totinv ="Invoice No. "+rst.getInt(1)+" Already Exist" ;
				}
				else if(doctp==67)
				{
					totinv ="Transfer Invoice No. "+rst.getInt(1)+" Already Exist" ;
				}
				else if(doctp==41) //for 41 doc_type
				{
					totinv ="Credit Note No. "+rst.getInt(1)+" Already Exist" ;
				}
				else if(doctp==51) //for 51 doc_type Debit note
				{
					totinv ="Debit Note No. "+rst.getInt(1)+" Already Exist" ;
				}
				else //(60 61 62 72)
				{
					totinv ="Inward No. "+rst.getInt(1)+" Already Exist" ;
				}

				
			}
			else
			{
				totinv ="1";
			}


			
			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvViewDAO.skip invoice - GetTotal Invoice Note No.  " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLInvViewDAO.Connection.close "+e);
			}
		}

		return totinv;
	}	 	
	
	

	public String getMissingInvCWH(int inv_no,int depo_code, int div, int year, Date sdate, Date edate,int doctp )
	{
		PreparedStatement ps1 = null;
		
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		String totinv=null;
		boolean check=true;
		try 
		{
			con=ConnectionFactory.getConnection();
//			String query1="select max(inv_no) from invfst where div_code=? and depo_code=? and doc_type=? and inv_date between ? and ?  and  ifnull(del_tag,'')<>'D' " ;
			String query1="select max(inv_no) from invfst where fin_year=? and div_code=? and depo_code=? and doc_type=?  and  ifnull(del_tag,'')<>'D' " ;
//			String query2="select inv_no  from invfst where div_code=? and depo_code=? and doc_type=?  and inv_no=? and inv_date between ? and ?  and  ifnull(del_tag,'')<>'D' " ;
			String query2="select inv_no  from invfst where fin_year=? and div_code=? and depo_code=? and doc_type=?  and inv_no=?  and  ifnull(del_tag,'')<>'D' " ;
			con.setAutoCommit(false);

			
			ps1 =con.prepareStatement(query2);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo_code);
			ps1.setInt(4, doctp);
			ps1.setInt(5, inv_no);
			//ps1.setDate(5,new java.sql.Date(sdate.getTime()));
			//ps1.setDate(6,new java.sql.Date(edate.getTime()));
			rst =ps1.executeQuery();

			if(rst.next())
			{
				if(doctp==40 || doctp==39)
				{
					totinv ="Invoice No. "+rst.getInt(1)+" Already Exist" ;
				}
				else if(doctp==67)
				{
					totinv ="Transfer Invoice No. "+rst.getInt(1)+" Already Exist" ;
				}
				else if(doctp==41) //for 41 doc_type
				{
					totinv ="Credit Note No. "+rst.getInt(1)+" Already Exist" ;
				}
				else if(doctp==51) //for 51 doc_type Debit note
				{
					totinv ="Debit Note No. "+rst.getInt(1)+" Already Exist" ;
				}
				else //(60 61 62 72)
				{
					totinv ="Inward No. "+rst.getInt(1)+" Already Exist" ;
				}
				
				check=false;

			}
			
			
			
		if(check)
		{
			rst.close();
			ps1.close();		
			
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo_code);
			ps1.setInt(4, doctp);
//            ps1.setDate(4,new java.sql.Date(sdate.getTime()));
//           ps1.setDate(5,new java.sql.Date(edate.getTime()));
			rst =ps1.executeQuery();

			if(rst.next())
			{
				if(inv_no>rst.getInt(1))
				{
					if(doctp==40 || doctp==39)
					{
						totinv ="Invoice No. Not in Missing Range, Last Invoice No. is "+rst.getInt(1);
					}
					else if(doctp==67)
					{
						totinv ="Transfer Invoice No. Not in Missing Range, Last TRF Invoice No. is "+rst.getInt(1);
					}
					else if(doctp==41) //for 41 doc_type
					{
						totinv ="Credit Note No. Not in Missing Range, Last CN No. is "+rst.getInt(1);
					}
					else if(doctp==51) //for 51 doc_type for debit note
					{
						totinv ="Debit Note No. Not in Missing Range, Last DN No. is "+rst.getInt(1);
					}
					else  //(60 61 62 72)
					{
						totinv ="Inward No. Not in Missing Range, Last Inward No. is "+rst.getInt(1);
					}
					check=false;
				}
			}
			else
			{
				totinv ="1";
				check=false;
			}
		}
			
			

			
			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvViewDAO - GetTotal Invoice Note No.  " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLInvViewDAO.Connection.close "+e);
			}
		}

		return totinv;
	}	 	
		
	
	
	public int getMaxBunch(int div,int depo,int year,int doctp)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		int bunch=0;
		try {
			con=ConnectionFactory.getConnection();
			String query1="select max(bunch_no)  from invfst where fin_year=? and div_code=? and depo_code=? and doc_type=?  and  ifnull(del_tag,'')<>'D' " ;
			con.setAutoCommit(false);

			
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setInt(4, doctp);
			rst =ps1.executeQuery();

			if(rst.next()){
				bunch=rst.getInt(1);
			}

			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvViewDAO - GetMax Bunch No.  " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLInvViewDAO.Connection.close "+e);
			}
		}

		return bunch;
	}	 	
		
	
	
	public double roundTwoDecimals(double d) {
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}	
	
	
	
	public boolean checkInvno(int inv_no,int depo_code,int div,int year, Date sdate, Date edate,int doctp)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;

		PreparedStatement ps1 = null;
		ResultSet rs1=null;

		boolean chk=false; 
		Connection con=null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String fstQ = "select f.inv_no,f.inv_date from invfst as f " +
					" where  f.fin_year=? and f.div_code =? and f.depo_code=? and f.doc_type =? and f.inv_no=? and f.inv_date between ? and ? and  ifnull(f.del_tag,'')<>'D'" +
					" and to_days(curdate())- to_days(f.inv_date) < 29 and f.mtr_dt is null" ;

			
			if (depo_code==21 || depo_code==25 || depo_code==15 || doctp==67)
			{
				fstQ = "select f.inv_no,f.inv_date from invfst as f " +
						" where  f.fin_year=? and f.div_code =? and f.depo_code=? and f.doc_type =? and f.inv_no=? and f.inv_date between ? and ? and  ifnull(f.del_tag,'')<>'D'" +
						" and to_days(curdate())- to_days(f.inv_date) < 29 " ;
			
			}

			String rcp="select vouno from rcpfile where div_code=? and vdepo_code=? and vbook_cd in(20,21,22,98)" +
					" and vac_code=? and inv_no=? and bill_date=? and ifnull(del_tag,'')<>'D' ";
			
			
			ps = con.prepareStatement(fstQ);
			ps.setInt(1, year);
			ps.setInt(2, div);
			ps.setInt(3, depo_code);
			ps.setInt(4, doctp);
			ps.setInt(5, inv_no);
			ps.setDate(6, new java.sql.Date(sdate.getTime()));
			ps.setDate(7, new java.sql.Date(edate.getTime()));

			rs= ps.executeQuery();
			if (rs.next())
			{
				chk=true;
			}

			
			ps1=con.prepareStatement(rcp);
			ps1.setInt(1, div);
			ps1.setInt(2, depo_code);
			ps1.setString(3, BaseClass.commaSepratedInvoiceNo);
			ps1.setInt(4, inv_no);
			ps1.setDate(5, new java.sql.Date(sdate.getTime()));
			rs1= ps1.executeQuery();
			if (rs1.next())
			{
				chk=false;
			}
			rs1.close();

			
			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
			ps1.close();
			

		} 
		catch (SQLException ex) 
		{  ex.printStackTrace();
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvViewDAO.getcheckInvno " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}


				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return chk;
	}	


	/// for edit party code in invoice bill adjusted/cn adjusted in bill 14/10/2015
	public boolean checkInvoiceStatus(int inv_no,int depo_code,int div,int year, Date sdate, Date edate,int doctp)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;

		PreparedStatement ps1 = null;
		ResultSet rs1=null;

		boolean chk=false; 
		Connection con=null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String fstQ = "select f.inv_no,f.inv_date from invfst as f " +
					" where  f.fin_year=? and f.div_code =? and f.depo_code=? and f.doc_type =? and f.inv_no=? and f.inv_date between ? and ? and  ifnull(f.del_tag,'')<>'D'" ;

			String Crq="select cdnote_no from invtrd where "+
			"div_code=? and depo_code=? and cdinv_no=? and cdinv_dt= ?  and cdnote_tp='C' and ifnull(del_tag,'')<>'D' ";

			
			String rcpq="select cr_no from rcpfile where "+
			"div_code=? and vdepo_code=? and vbook_cd in (20,21,22,98) and inv_no=? and bill_date= ?   and ifnull(del_tag,'')<>'D' ";

			

			ps1 = con.prepareStatement(rcpq);

			
			ps = con.prepareStatement(fstQ);
			ps.setInt(1, year);
			ps.setInt(2, div);
			ps.setInt(3, depo_code);
			ps.setInt(4, doctp);
			ps.setInt(5, inv_no);
			ps.setDate(6, new java.sql.Date(sdate.getTime()));
			ps.setDate(7, new java.sql.Date(edate.getTime()));

			rs= ps.executeQuery();
			if (rs.next())
			{
				chk=true;
			}

			
			
			
			
			if (chk) // in rcpfile
			{
			ps1.setInt(1, div);
			ps1.setInt(2, depo_code);
			ps1.setInt(3, rs.getInt(1));
			ps1.setDate(4, rs.getDate(2));

			rs1= ps1.executeQuery();
			if (rs1.next())
			{
				chk=false;
			}
			rs1.close();

			}

			ps1 = con.prepareStatement(Crq);


			if (chk)  // in invtrd
			{
			ps1.setInt(1, div);
			ps1.setInt(2, depo_code);
			ps1.setInt(3, rs.getInt(1));
			ps1.setDate(4, rs.getDate(2));

			rs1= ps1.executeQuery();
			if (rs1.next())
			{
				chk=false;
			}
			rs1.close();

			}
			ps1.close();

			
			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
			

		} 
		catch (SQLException ex) 
		{  ex.printStackTrace();
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvViewDAO.getcheckInvno " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}


				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return chk;
	}	

	
	
	
	
	public List getEmailPartyList(Date startDate,Date endDate,int depo_code,int div,int doc_tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;

		int wdiv=div;
		
		int pdiv=div;
		
		String query2="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		
		int wdoc_tp=doc_tp+1;
		 
		try {

			con=ConnectionFactory.getConnection();


			query2 ="select p.mac_code,p.mac_name,p.mcity,f.inv_no,concat(f.inv_lo,right(f.inv_no,4)) as invno," +
			" f.inv_date,if(f.doc_type=41,f.order_no,f.dispatch_date),IFNULL(f.mtr_no,''),f.mtr_dt,f.bill_amt,p.memail "+
			" from invfst as f,partyfst as p where f.party_code=p.mac_code  "+ 
			" and f.depo_code=? and f.doc_type in (?,?) and f.inv_date between ? and ? and  ifnull(f.del_tag,'')<>'D' " +
			" and p.div_code=? and p.depo_code=? order by p.mac_name,p.mcity,f.inv_no ";

			

			ps2 = con.prepareStatement(query2);
  			
			ps2.setInt(1, depo_code);
			ps2.setInt(2, doc_tp);
			ps2.setInt(3, wdoc_tp);
			ps2.setDate(4, new java.sql.Date(startDate.getTime()));
			ps2.setDate(5, new java.sql.Date(endDate.getTime()));
			ps2.setInt(6,pdiv);
			ps2.setInt(7,depo_code);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			String partyCode="";
			Vector invVector=null; 
			Vector listVector=null;
			Date ss=null;
			
			while (rs.next())
			{
				 

				{	
					if(!partyCode.equalsIgnoreCase(rs.getString(1).trim()))
					{
						partyCode=rs.getString(1);

						col= new Vector();
						col.addElement(false);
						col.addElement(rs.getString(1));//party code
						col.addElement(rs.getString(2)+", "+rs.getString(3));//party name
						listVector = new Vector();
						col.addElement(listVector);
						col.addElement(rs.getString(11));

						v.addElement(col);
					}
				}
				invVector= new Vector();
				invVector.addElement(true);    ///0
				invVector.addElement(rs.getString(4));  //inv no
				ss= (Date)rs.getDate(6); //inv date
				invVector.addElement(sdf.format(ss));  //inv date 

				
				try
				{
					if(rs.getString(7).contains("RR") || doc_tp==41)
						invVector.addElement(rs.getString(7));
					else if(rs.getDate(7)!=null)
						invVector.addElement(sdf.format(rs.getDate(7))); //dispatch date
					else
						invVector.addElement("__/__/____"); //disptach date
				}
				catch(Exception e)
				{
					if(rs.getDate(7)!=null)
						invVector.addElement(sdf.format(rs.getDate(7))); //dispatch date
					else
						invVector.addElement("__/__/____"); //disptach date
				}

				invVector.addElement(rs.getString(8));  // lr no

				if(rs.getDate(9)!=null)//lr date
					invVector.addElement(sdf.format(rs.getDate(9))); //lr date
				else
					invVector.addElement("__/__/____");// lr date

				invVector.addElement(formatter.format(rs.getDouble(10))); // amount
				invVector.addElement(rs.getInt(4));  // (inv no hidden 
				
				listVector.add(invVector);
				//adding inv vector to left partylist.
				
				

			}

			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvViewDAO.getEmailPartyList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return v;
	}
	
	public List getEmailReceiptList(Date startDate,Date endDate,int depo_code,int div,int doc_tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;

		String query2="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		try {

			con=ConnectionFactory.getConnection();

			query2 ="select p.mac_code,p.mac_name,p.mcity,l.vou_no,concat(left(l.vou_lo,1),'R',right(l.vou_lo,1),right(l.vou_no,5)) as invno," +
			" l.vou_date,l.vnart_1,IFNULL(l.chq_no,''),l.chq_date,l.vamount,p.memail "+
			" from ledfile as l,partyfst as p where l.vac_code=p.mac_code and l.div_Code=? "+ 
			" and l.vdepo_code=? and l.vbook_cd1=? and l.vou_date between ? and ? and  ifnull(l.del_tag,'')<>'D' " +
			" and p.div_code=? and p.depo_code=? order by p.mac_name,p.mcity ";
				
						
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3, doc_tp);
			ps2.setDate(4, new java.sql.Date(startDate.getTime()));
			ps2.setDate(5, new java.sql.Date(endDate.getTime()));
			ps2.setInt(6,div);
			ps2.setInt(7,depo_code);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			String partyCode="";
			Vector invVector=null; 
			Vector listVector=null;
			Date ss=null;
			
			while (rs.next())
			{

				if(!partyCode.equalsIgnoreCase(rs.getString(1)))
				{
					partyCode=rs.getString(1);

					col= new Vector();
					col.addElement(false);
					col.addElement(rs.getString(1));//party code
					col.addElement(rs.getString(2)+", "+rs.getString(3));//party name
					listVector = new Vector();
					col.addElement(listVector);
					col.addElement(rs.getString(11));
					
					v.addElement(col);
				}

				invVector= new Vector();
				invVector.addElement(true);    ///0
				invVector.addElement(rs.getString(5));  //rcp no
				ss= (Date)rs.getDate(6); //inv date
				invVector.addElement(sdf.format(ss));  //rcp date 
				invVector.addElement(rs.getString(7));  //drawn on


				invVector.addElement(rs.getString(8));  // chq no

				if(rs.getDate(9)!=null)//lr date
					invVector.addElement(sdf.format(rs.getDate(9))); //chq date
				else
					invVector.addElement("__/__/____");// lr date

				invVector.addElement(formatter.format(rs.getDouble(10))); // amount
				invVector.addElement(rs.getInt(4));  // (inv no hidden 

				listVector.add(invVector);
				//adding inv vector to left partylist.
				
				

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
			System.out.println("-------------Exception in InvViewDAO.getEmailPartyList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return v;
	}
	
	
	public List getEmailPartyListNo(int sno,int eno,int depo_code,int div,int doc_tp,int fyear,int mnth_code)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;

		String query2="";
		int wdiv=div;

		int pdiv=div;
		int wdoc_tp=doc_tp+1;
		

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		int pack_code=BaseClass.loginDt.getPack_code();
		int sale_div=10;

		try {

			con=ConnectionFactory.getConnection();

			query2 ="select p.mac_code,p.mac_name,p.mcity,f.inv_no,concat(f.inv_lo,right(f.inv_no,4)) as invno," +
			" f.inv_date,f.dispatch_date,IFNULL(f.mtr_no,''),f.mtr_dt,f.bill_amt,p.memail "+
			" from invfst as f,partyfst as p where f.fin_year=? and f.party_code=p.mac_code "+ 
			" and f.depo_code=? and f.doc_type in (?,?) and f.inv_no between ? and ? and  f.mnth_code=? and ifnull(f.del_tag,'')<>'D' " +
			" and p.div_code=? and p.depo_code=? order by p.mac_name,p.mcity,f.inv_no ";
				
			
			ps2 = con.prepareStatement(query2);
			
			ps2.setInt(1,fyear);
			ps2.setInt(2, depo_code);
			ps2.setInt(3, doc_tp);
			ps2.setInt(4, wdoc_tp);
			ps2.setInt(5, sno);
			ps2.setInt(6, eno);
			ps2.setInt(7, mnth_code);
			ps2.setInt(8,pdiv);
			ps2.setInt(9,depo_code);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			String partyCode="";
			Vector invVector=null; 
			Vector listVector=null;
			Date ss=null;
			
			while (rs.next())
			{

				
				if(!partyCode.equalsIgnoreCase(rs.getString(1)))
				{
					partyCode=rs.getString(1);

					col= new Vector();
					col.addElement(false);
					col.addElement(rs.getString(1));//party code
					col.addElement(rs.getString(2)+", "+rs.getString(3));//party name
					listVector = new Vector();
					col.addElement(listVector);
					col.addElement(rs.getString(11));
					
					v.addElement(col);
				}

				invVector= new Vector();
				invVector.addElement(true);    ///0
				invVector.addElement(rs.getString(5));  //inv no
				ss= (Date)rs.getDate(6); //inv date
				invVector.addElement(sdf.format(ss));  //inv date 


				if(rs.getDate(7)!=null)
					invVector.addElement(sdf.format(rs.getDate(7))); //dispatch date
				else
					invVector.addElement("__/__/____"); //disptach date

				invVector.addElement(rs.getString(8));  // lr no

				if(rs.getDate(9)!=null)//lr date
					invVector.addElement(sdf.format(rs.getDate(9))); //lr date
				else
					invVector.addElement("__/__/____");// lr date

				invVector.addElement(formatter.format(rs.getDouble(10))); // amount
				invVector.addElement(rs.getInt(4));  // (inv no hidden 

				listVector.add(invVector);
				//adding inv vector to left partylist.
				
				

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
			System.out.println("-------------Exception in InvViewDAO.getEmailPartyList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return v;
	}

	// for check invoice not converted in cn
	public boolean checkInvnocrn(int inv_no,int depo_code,int div,int year, Date sdate, Date edate,int doctp)
	{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs=null;
		ResultSet rs1=null;
		boolean chk=false; 
		Connection con=null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String fstQ = "select f.inv_no from invfst as f " +
			" where  f.fin_year=? and f.div_code =? and f.depo_code=? and f.doc_type =? and f.inv_no=? and f.inv_date = ? and  ifnull(f.del_tag,'')<>'D'" +
			" and ifnull(prt_type,'')='C'" ;

			String rcpQ = "select f.inv_no from rcpfile as f " +
			" where  f.fin_year=? and f.div_code =? and f.vdepo_code=? and f.vbook_cd in (20,21,22,98) and f.inv_no=? and ifnull(f.inv_lo,'')<>'C' and f.bill_date = ? and  ifnull(f.del_tag,'')<>'D'";

			
			ps1 = con.prepareStatement(rcpQ);
			
			ps = con.prepareStatement(fstQ);
			ps.setInt(1, year);
			ps.setInt(2, div);
			ps.setInt(3, depo_code);
			ps.setInt(4, doctp);
			ps.setInt(5, inv_no);
			ps.setDate(6, new java.sql.Date(sdate.getTime()));

			rs= ps.executeQuery();
			if (rs.next())
			{
				chk=true;
			}
			
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo_code);
			ps1.setInt(4, inv_no);
			ps1.setDate(5, new java.sql.Date(sdate.getTime()));

			rs1= ps1.executeQuery();
			if (rs1.next())
			{
				chk=true;
			}



			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
			rs1.close();
			ps1.close();
			

		} 
		catch (SQLException ex)  
		{  ex.printStackTrace();
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvViewDAO.getcheckInvnoforCN " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}


				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return chk;
	}	
	// TODO get Invoice List for selected party.... (Party Search in Inward Invoice View)
		public List getPurInvList(String partycd,int depo_code,int div,int year,Date sdate,Date edate)
		{
			PreparedStatement ps2 = null;
			ResultSet rs=null;
			Connection con=null;
			Vector v=null;
			Vector col=null;


			try {

				con=ConnectionFactory.getConnection();

				String query2 ="select concat(f.inv_yr,f.inv_lo,f.inv_no) as invno, p.mac_name,p.mcity,f.inv_no "+
				" from invfst as f,partyfst as p where f.fin_year=? and f.div_Code=? "+ 
				" and f.depo_code=? and f.doc_type in (60,61,62,72) and f.party_code=? and f.inv_date between ? and ? and  ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=? and p.depo_code=? and p.mac_code=?  order by f.inv_no ";

				ps2 = con.prepareStatement(query2);
				ps2.setInt(1,year);
				ps2.setInt(2,div);
				ps2.setInt(3, depo_code);
				ps2.setString(4, partycd);
				ps2.setDate(5, new java.sql.Date(sdate.getTime()));
				ps2.setDate(6, new java.sql.Date(edate.getTime()));
				ps2.setInt(7,div);
				ps2.setInt(8,depo_code);
				ps2.setString(9, partycd);
				
				
				 
				rs= ps2.executeQuery();
				con.setAutoCommit(false);
				v = new Vector();
				while (rs.next())
				{

					col= new Vector();
					col.addElement(rs.getString(4));//concat inv_no
					col.addElement(rs.getString(2)+","+rs.getString(3));//party name
					col.addElement(rs.getString(4));///inv_no
					v.addElement(col);

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
				System.out.println("-------------Exception in InvViewDAO.getInvList for partySearch" + ex);

			}
			finally {
				try {
					//				System.out.println("No. of Records Update/Insert : "+i);

					if(rs != null){rs.close();}
					if(ps2 != null){ps2.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
				}
			}

			return v;
		}
	
		public List getPurInvList(Date invDt,int depo_code,int div,int doc_tp)
		{
			PreparedStatement ps2 = null;
			ResultSet rs=null;
			Connection con=null;
			Vector v=null;
			Vector col=null;

			String query2="";
			try {

				con=ConnectionFactory.getConnection();

				query2 ="select concat(f.inv_yr,f.inv_lo,f.inv_no) as invno, p.mac_name,p.mcity,f.inv_no "+
						" from invfst as f,partyfst as p where f.party_code=p.mac_code and f.div_Code=? "+ 
						" and f.depo_code=? and f.doc_type in (60,61,62,72) and f.inv_date=?  and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? order by p.mac_name,p.mcity,f.inv_no ";

				ps2 = con.prepareStatement(query2);
				ps2.setInt(1,div);
				ps2.setInt(2, depo_code);
				ps2.setDate(3, new java.sql.Date(invDt.getTime()));
				ps2.setInt(4,div);
				ps2.setInt(5,depo_code);
				rs= ps2.executeQuery();
				con.setAutoCommit(false);
				v = new Vector();
				while (rs.next())
				{

					col= new Vector();
					col.addElement(rs.getString(4));//concat inv_no
					col.addElement(rs.getString(2)+","+rs.getString(3));//party name
					col.addElement(rs.getString(4));///inv_no
					v.addElement(col);

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
				System.out.println("-------------Exception in InvViewDAO.getInvList " + ex);

			}
			finally {
				try {
					//				System.out.println("No. of Records Update/Insert : "+i);

					if(rs != null){rs.close();}
					if(ps2 != null){ps2.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
				}
			}

			return v;
		}

		

}

