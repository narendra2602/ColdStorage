package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.coldstorage.view.BaseClass;
 

public class OedDAO 
{
	
	public List getCrList(String vcode,int div,int depo,Date invdate)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null; 
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		try {

			con=ConnectionFactory.getConnection();

			
			String query2 ="select vou_no,vou_date,(vamount-rcp_amt1),indicator,vbook_cd1,vou_lo,vou_yr " +
			" from ledfile where div_code=? and vdepo_code=? and vbook_cd1 in(90,95) " +
			" and vac_code=? and vou_date<=? and vamount>rcp_amt1 and (ifnull(del_tag,'')<>'D' and ifnull(del_tag,'')='X') order by vou_no,vou_date";

			
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setString(3, vcode);
			ps2.setDate(4, new java.sql.Date(invdate.getTime()));
			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			Date ss=null;
			double amt=0.00;
			while (rs.next())
			{
				col= new Vector();
				col.addElement("");
//				col.addElement(rs.getString(4).trim());
//				
//				col.addElement(formatter.format(rs.getDouble(3)));
				col.addElement(formatter.format(0.00));
				
				if(rs.getInt(5)==90)
					col.addElement("CR");
				else
					col.addElement("DR");
				
				
				col.addElement(rs.getInt(1));
				///////////////////////////////////
				ss= (Date)rs.getDate(2);
				col.addElement(sdf.format(ss));
				///////////////////////////////////
				col.addElement(formatter.format(rs.getDouble(3)));
				
				col.addElement(rs.getString(6));
				col.addElement(rs.getInt(7));
				
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
			System.out.println("-------------Exception in SQLOedDAO.batchlist " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return v;
	}


	
	public List getCrDrList(String vcode,int div,int depo,int year,int invno,Date invdt)
	{
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		ResultSet rs=null;
		ResultSet rs1=null;
		ResultSet rs5=null;
		Connection con=null;
		Vector v=null;
		Vector col=null; 
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			String query3 = "update invtrd set del_tag='D' where fin_year=? and div_code=? and depo_code=?" +
			"  and cdprt_cd=? and cdinv_no=? ";
			
			String query1 = "select cdnote_no,cdnote_tp,cdnote_dt,cdnote_amt,cdprt_cd from invtrd " +
			" where fin_year=? and div_code=? and depo_code=? and cdprt_cd=? and cdinv_no=? and ifnull(del_tag,'')<>'D'  ";
			
			String query2="update ledfile set rcp_amt1=rcp_amt1-? where div_code=? " +
			" and vdepo_code=? and vbook_cd1=? and vac_code=? and vou_no=? and vou_date=?  ";

			
			String query4 =	"select o.vou_no,o.vou_date,o.vamount,(o.vamount-o.rcp_amt1),o.indicator,o.vbook_cd1," +
			" o.vou_lo,o.vou_yr, o.rcp_amt1 from ledfile as o "+
			" where  o.div_code=? and o.vdepo_code=? and o.vbook_cd1 in (90,95) "+ 
			" and o.vac_code=? and o.vou_date<=? and o.vamount-o.rcp_amt1>0 and ifnull(o.del_tag,'')<>'D' order by o.vou_no,o.vou_date ";
			
			String query5 =	"select i.cdinv_no,i.cdnote_no,i.cdnote_dt,i.cdnote_amt,i.cdnote_tp,i.cdnote_lo,i.cdnote_yr from  invtrd as i "+ 
			" where i.fin_year=? and i.div_code=? and i.depo_code=?  and i.cdprt_cd=? and i.cdinv_no=? and ifnull(i.del_tag,'')<>'D' ";
			


		/*	String query4 =" select a.*,ifnull(b.cdinv_no,0),ifnull(b.cdnote_no,0),ifnull(b.cdnote_amt,0.00) from "+ 
			" (select o.vou_no,o.vou_date,o.vamount,(o.vamount-o.rcp_amt1),o.indicator,o.vbook_cd1," +
			" o.vou_lo,o.vou_yr,"+
			" o.rcp_amt1 from ledfile as o "+
			" where  o.div_code=? and o.vdepo_code=? and o.vbook_cd1 in (90,95) "+ 
			" and o.vac_code=? and o.vamount-o.rcp_amt1<>0 ) a "+
			" left join "+
			" (select i.cdinv_no,i.cdnote_no,i.cdnote_amt from  invtrd as i "+
			" where i.fin_year=? and i.div_code=? and i.depo_code=?  and i.cdprt_cd=? and i.cdinv_no=? and ifnull(del_tag,'')<>'D') b "+
			" on a.vou_no=b.cdnote_no order by a.vou_no,a.vou_date ";*/
			
			ps2 = con.prepareStatement(query2);
			ps3 = con.prepareStatement(query3);
			ps5 = con.prepareStatement(query5);
			

			
			
			
			
			v = new Vector();
			Date ss=null;
			double amt=0.00;
			
			ps5.setInt(1, year);
			ps5.setInt(2, div);
			ps5.setInt(3, depo);
			ps5.setString(4, vcode);
			ps5.setInt(5, invno);
			rs5= ps5.executeQuery();
			while (rs5.next())
			{
				 
				col= new Vector();
				col.addElement("Y");
				col.addElement(formatter.format(rs5.getDouble(4)));
				
				if(rs5.getString(5).equalsIgnoreCase("C"))
					col.addElement("CR");
				else
					col.addElement("DR");
				
				
				col.addElement(rs5.getInt(2));
				///////////////////////////////////
//				ss= (Date) rs5.getDate(3);
//				col.addElement(sdf.format(ss));
				col.addElement(sdf.format(rs5.getDate(3)));
				///////////////////////////////////
				col.addElement(formatter.format(rs5.getDouble(4)));
				
				col.addElement(rs5.getString(6));
				col.addElement(rs5.getInt(7));
				
				v.addElement(col);

			}
			
			
			ps4 = con.prepareStatement(query4);
			ps4.setInt(1, div);
			ps4.setInt(2, depo);
			ps4.setString(3, vcode);
			ps4.setDate(4, new java.sql.Date(invdt.getTime()));

			
			rs= ps4.executeQuery();	
			
			while (rs.next())
			{
				 
				col= new Vector();
					col.addElement("");
		 
				col.addElement(formatter.format(0.00));
				
				if(rs.getInt(6)==90)
					col.addElement("CR");
				else
					col.addElement("DR");
				
				
				col.addElement(rs.getInt(1));
				///////////////////////////////////
				ss= (Date)rs.getDate(2);
				col.addElement(sdf.format(ss));
				///////////////////////////////////
				col.addElement(formatter.format(rs.getDouble(4)));
				
				col.addElement(rs.getString(7));
				col.addElement(rs.getInt(8));
				
				v.addElement(col);

			}
			
			
			
			
			
			
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setString(4, vcode);
			ps1.setInt(5, invno);

			rs1 = ps1.executeQuery();

			while (rs1.next())
			{

				ps2.setDouble(1, rs1.getDouble(4));
				ps2.setInt(2, div);
				ps2.setInt(3, depo);
				if (rs1.getString(2).equalsIgnoreCase("C"))
					ps2.setInt(4, 90);
				if (rs1.getString(2).equalsIgnoreCase("D"))
					ps2.setInt(4, 95);
				ps2.setString(5, vcode);
				ps2.setInt(6,rs1.getInt(1));
				ps2.setDate(7, new java.sql.Date(rs1.getDate(3).getTime()));
				
				i= ps2.executeUpdate();


			}

			ps3.setInt(1, year);
			ps3.setInt(2, div);
			ps3.setInt(3, depo);
			ps3.setString(4, vcode);
			ps3.setInt(5, invno);
			i= ps3.executeUpdate();
			
			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			rs1.close();
			ps1.close();
			ps2.close();
			ps3.close();
			ps4.close();
			rs5.close();
			ps5.close();


		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLOedDAO.CrDrList " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(ps2 != null){ps2.close();}
				if(ps3 != null){ps3.close();}
				if(ps4 != null){ps4.close();}
				if(rs5 != null){rs5.close();}
				if(ps5 != null){ps5.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return v;
	}	
	
	
	public double[] getUnadjCrDr(String vcode,int depo,int div,Date invdate)
	{
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs=null;
		Connection con=null;
		double val[] = new double[2];
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
			String query2 ="select sum(vamount-rcp_amt1) from Ledfile where div_code=? and vdepo_code=? and vbook_cd1=? and vac_code=? and vou_date<=? " +
			"  and (vamount-rcp_amt1)>0 and (ifnull(del_tag,'')<>'D' and ifnull(del_tag,'')<>'X') group by vac_code";
			
			String query3 ="update ledfile set del_tag='X' " +
			" where div_code=? and vdepo_code=? and vbook_cd1 in(90,95) " +
			" and vac_code=? and vou_date<=? and vamount>rcp_amt1 and ifnull(del_tag,'')<>'D' order by vou_no,vou_date";


			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2,depo);
			ps2.setInt(3,90);
			ps2.setString(4,vcode);
			ps2.setDate(5, new java.sql.Date(invdate.getTime()));
			rs= ps2.executeQuery();
			if (rs.next())
			{
				val[0]=rs.getDouble(1);
			}
			rs.close();
			rs=null;
			
			ps2.setInt(1,div);
			ps2.setInt(2,depo);
			ps2.setInt(3,95);
			ps2.setString(4,vcode);
			ps2.setDate(5, new java.sql.Date(invdate.getTime()));
			
			rs= ps2.executeQuery();
			if (rs.next())
			{
				val[1]=rs.getDouble(1);
			}

			ps3 = con.prepareStatement(query3);
			ps3.setInt(1, div);
			ps3.setInt(2, depo);
			ps3.setString(3, vcode);
			ps3.setDate(4, new java.sql.Date(invdate.getTime()));
			ps3.executeUpdate();

			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();
			ps3.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLOedDAO.getUnadjCrDr " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(ps3 != null){ps3.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.getUnadjCrDr.Connection.close "+e);
			}
		}

		return val;
	}

	
	
	
	public List getOedList(String vcode,int div,int depo,Date vdate,int fyear)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;

		PreparedStatement ps3 = null;
		ResultSet rs3=null;

		PreparedStatement ps33 = null;
		ResultSet rs33=null;

		
		PreparedStatement ps4 = null;
		ResultSet rs4=null;

		Connection con=null;
		Vector v=null;
		Vector col=null; 
		String query2=null;
		int i=0;
		String voudate="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		int cmpdepo=BaseClass.loginDt.getCmp_code();
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			if (vdate!=null)
				voudate=" and vou_date <=? ";

					// line no 476 par substring(bill_no,4,1)<>'D' lagaya hai 09/11/2018
			query2 =" select a.*,IFNULL(b.vouno,0),b.bill_date,ifnull(b.ramt,0.00),b.vac_code from " +
					" (select if((l.sub_div=39 and  l.vdepo_code=30 ),concat(l.vou_yr,l.vou_lo,'N',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,l.vamount bamt, "+  //3
					" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
					" from ledfile l "+ 
					"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 in (?,?,81)  and l.vac_code=? and "+  
					" l.vamount>l.rcp_amt1 and l.vou_Date between '2012-04-01' and '2013-03-31' and ifnull(l.del_tag,'')<>'D' " +
					" union all "+
					" select if((l.sub_div=40 and  (l.vdepo_code=59 or l.vdepo_code=58)),concat(l.vou_yr,l.vou_lo,'L',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,if((l.vamount-ifnull(l.dr_amt,0))<0,l.vamount,(l.vamount-ifnull(l.dr_amt,0))) bamt, "+  //3
					" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
					" from ledfile l "+ 
					"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 in (?,?)  and l.vac_code=? and "+  
					" l.vou_Date between '2013-04-01' and ? and ifnull(new2,0)=0 and ifnull(l.del_tag,'')<>'D' "+
					" union all "+
					" select if((l.sub_div=40 and  (l.vdepo_code=59 or l.vdepo_code=58)),concat(l.vou_yr,l.vou_lo,'L',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,(l.vamount-ifnull(l.dr_amt,0)) bamt, "+  //3
					" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
					" from ledfile l "+ 
					"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 =81  and l.vac_code=? and "+  
					" l.vou_Date between '2016-04-01' and ? and ifnull(l.del_tag,'')<>'D') a "+
					" left join "+ 
					" (select r.inv_no vouno,r.bill_date,sum(r.vamount) ramt,r.vac_code   from rcpfile R where r.fin_year<=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in(20,21,22,98) "+//23
					" and r.vac_code=? and ifnull(inv_lo,'')<>'C'  and ifnull(del_tag,'')<>'D' group by inv_no,bill_date "+
					" union all "+
					" select r.inv_no vouno,r.cr_date bill_date ,sum(r.vamount) ramt,r.vac_code   from rcpfile R where r.fin_year<=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in(20,21,22,98) "+//23
					" and r.vac_code=? and ifnull(inv_lo,'')<>'C' and substring(bill_no,4,1)='D' and ifnull(del_tag,'')<>'D' group by INV_NO,CR_date) b "+
					" on a.vac_Code=b.vac_code and a.vou_no=b.vouno and a.vou_date=b.bill_date where (a.bamt -ifnull(b.ramt,0.00)) > 0  " ;

			        // r.vouno change to r.inv_no line no 475 on 10/01/2018
			
			if(depo==58 || depo==59)  // 01/07/2017
			{
			query2 =" select a.*,b.* from " +
					" (select if((l.sub_div=40 and  (l.vdepo_code=59 or l.vdepo_code=58)),concat(l.vou_yr,l.vou_lo,'L',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,l.vamount bamt, "+  //3
					" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
					" from ledfile l "+ 
					"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 in (?,?,81)  and l.vac_code=? and "+  
					" l.vamount>l.rcp_amt1 and l.vou_Date between '2012-04-01' and '2013-03-31' and ifnull(l.del_tag,'')<>'D' " +
					" union all "+
					" select if((l.sub_div=40 and  (l.vdepo_code=59 or l.vdepo_code=58)),concat(l.vou_yr,l.vou_lo,'L',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,if((l.vamount-ifnull(l.dr_amt,0))<0,l.vamount,(l.vamount-ifnull(l.dr_amt,0)))  bamt, "+  //3
					" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
					" from ledfile l "+ 
					"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 in (?,?)  and l.vac_code=? and "+  
					" l.vou_Date between '2013-04-01' and '2017-06-30' and ifnull(new2,0)=0 and ifnull(l.del_tag,'')<>'D' "+
					" union all "+
					" select if((l.sub_div=40 and  (l.vdepo_code=59 or l.vdepo_code=58)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,(l.vamount-ifnull(l.dr_amt,0)) bamt, "+  //3
					" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
					" from ledfile l "+ 
					"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 in (?,?)  and l.vac_code=? and "+  
					" l.vou_Date between '2017-07-01' and ? and ifnull(new2,0)=0 and ifnull(l.del_tag,'')<>'D' "+
					" union all "+
					" select if((l.sub_div=40 and  (l.vdepo_code=59 or l.vdepo_code=58)),concat(l.vou_yr,l.vou_lo,'L',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,(l.vamount-ifnull(l.dr_amt,0)) bamt, "+  //3
					" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
					" from ledfile l "+ 
					"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 =81  and l.vac_code=? and "+  
					" l.vou_Date between '2016-04-01' and ? and ifnull(l.del_tag,'')<>'D') a "+
					" left join "+ 
					" (select r.vouno vouno,r.bill_date,sum(r.vamount) ramt,r.vac_code   from rcpfile R where r.fin_year<=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in(20,21,22,98) "+//23
					" and r.vac_code=? and ifnull(inv_lo,'')<>'C'   and ifnull(del_tag,'')<>'D' group by vouno,bill_date "+
					" union all "+
					" select r.inv_no vouno,r.cr_date bill_date ,sum(r.vamount) ramt,r.vac_code   from rcpfile R where r.fin_year<=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in(20,21,22,98) "+//23
					" and r.vac_code=? and ifnull(inv_lo,'')<>'C' and substring(bill_no,4,1)='D' and ifnull(del_tag,'')<>'D' group by INV_NO,CR_date) b "+
					" on a.vac_Code=b.vac_code and a.vou_no=b.vouno and a.vou_date=b.bill_date where (a.bamt -ifnull(b.ramt,0.00)) > 0  " ;
			}
			
			if(depo==211 || depo==42)
			{
				
				query2 =" select a.*,b.* from " +
						" (select if((l.sub_div=40 and  (l.vdepo_code=59 or l.vdepo_code=58)),concat(l.vou_yr,l.vou_lo,'L',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,l.vamount bamt, "+  //3
						" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
						" from ledfile l "+ 
						"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 in (?,?,81)  and l.vac_code=? and "+  
						" l.vamount>l.rcp_amt1 and l.vou_Date between '2012-01-01' and '2013-03-31' and ifnull(l.del_tag,'')<>'D' " +
						" union all "+
						" select if((l.sub_div=40 and  (l.vdepo_code=59 or l.vdepo_code=58)),concat(l.vou_yr,l.vou_lo,'L',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,if((l.vamount-ifnull(l.dr_amt,0))<0,l.vamount,(l.vamount-ifnull(l.dr_amt,0)))  bamt, "+  //3
						" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
						" from ledfile l "+ 
						"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 in (?,?)  and l.vac_code=? and "+  
						" l.vou_Date between '2013-04-01' and ? and ifnull(new2,0)=0 and ifnull(l.del_tag,'')<>'D' "+
						" union all "+
						" select if((l.sub_div=40 and  (l.vdepo_code=59 or l.vdepo_code=58)),concat(l.vou_yr,l.vou_lo,'L',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,(l.vamount-ifnull(l.dr_amt,0)) bamt, "+  //3
						" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
						" from ledfile l "+ 
						"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 =81  and l.vac_code=? and "+  
						" l.vou_Date between '2016-04-01' and ? and ifnull(l.del_tag,'')<>'D') a "+
						" left join "+ 
						" (select r.doc_no vouno,r.doc_date bill_date,sum(r.vamount) ramt,r.vac_code   from rcpfile R where r.fin_year<=? and r.div_code=? and r.vdepo_code=? and r.doc_type in(80,81,95) "+//23
						" and r.vac_code=? and ifnull(inv_lo,'')<>'C'  and ifnull(del_tag,'')<>'D' group by doc_no,doc_date ) b "+
						" on a.vac_Code=b.vac_code and a.vou_no=b.vouno and a.vou_date=b.bill_date where (a.bamt -ifnull(b.ramt,0.00)) > 0  " ;
				
			}
			
			
			else if (depo==50)
			{
				query2 =" select a.*,b.* from " +
						" (select if((l.sub_div=39 and  l.vdepo_code=50),concat(l.vou_yr,l.vou_lo,'R',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,l.vamount bamt, "+  //3
						" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
						" from ledfile l "+ 
						"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 in (?,?,81)  and l.vac_code=? and "+  
						" l.vamount>l.rcp_amt1 and l.vou_Date between '2011-04-01' and '2013-03-31' and ifnull(l.del_tag,'')<>'D' " +
						" union all "+
						" select if((l.sub_div=39 and  l.vdepo_code=50),concat(l.vou_yr,l.vou_lo,'R',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,l.vamount bamt, "+  //3
						" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
						" from ledfile l "+ 
						"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 in (?,?)  and l.vac_code=? and "+  
						" l.vou_Date between '2013-04-01' and ? and ifnull(new2,0)=0 and ifnull(l.del_tag,'')<>'D' "+
						" union all "+
						" select if((l.sub_div=39 and  l.vdepo_code=50),concat(l.vou_yr,l.vou_lo,'R',right(l.vou_no,4)),concat(l.vou_yr,l.vou_lo,right(l.vou_no,5))) vouno ,l.vou_date,l.vamount bamt, "+  //3
						" (l.vamount-l.rcp_amt1) as outamt,l.vou_no,l.vstat_cd,l.varea_cd,l.vreg_cd,l.vter_cd,l.vdist_cd,l.vmsr_cd,l.due_date,l.sub_div,l.vou_lo,l.vbook_cd1,l.vou_yr,l.vac_code" + //17
						" from ledfile l "+ 
						"  where l.div_code=?  and l.vdepo_code=? and  l.vbook_cd1 =81  and l.vac_code=? and "+  
						" l.vou_Date between '2016-04-01' and ? and ifnull(l.del_tag,'')<>'D') a "+

						" left join "+ 
						" (select r.vouno vouno,r.bill_date,sum(r.vamount) ramt,r.vac_code   from rcpfile R where r.fin_year<=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in(20,21,22,98) "+//23
						" and r.vac_code=? and ifnull(inv_lo,'')<>'C'  and ifnull(del_tag,'')<>'D' group by vouno,bill_date "+
						" union all "+
						" select r.inv_no vouno,r.cr_date bill_date,sum(r.vamount) ramt,r.vac_code   from rcpfile R where r.fin_year<=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in(20,21,22,98) "+//23
						" and r.vac_code=? and ifnull(inv_lo,'')<>'C' and substring(bill_no,4,1)='D' and ifnull(del_tag,'')<>'D' group by INV_NO,CR_date) b "+
						" on a.vac_Code=b.vac_code and a.vou_no=b.vouno and a.vou_date=b.bill_date where (a.bamt -ifnull(b.ramt,0.00)) > 0 " ;
			}

			
			
			
		    String query4="select concat(cdnote_yr,cdnote_lo,'D',cdnote_no),cdnote_dt  from invtrd where div_code=? and depo_code=? " +
				    " and cdprt_cd=? and cdinv_no=? and cdinv_dt=? and cdnote_tp='D' and ifnull(cr_note1,'')='I' and ifnull(del_tag,'')<>'D'  ";

	      // Original Query 		
		    String query33="select cdnote_no,cdnote_dt,sum(cdnote_amt)  from invtrd where div_code=? and depo_code=? " +
				    " and cdprt_cd=? and cdnote_no=? and cdnote_dt=? and cdnote_tp='D' and ifnull(cr_note1,'')='A' and ifnull(del_tag,'')<>'D'  group by cdnote_no,cdnote_dt ";

		    String query3="select concat(cdinv_yr,cdinv_lo,LPAD(cdinv_no,5,'0')),cdinv_dt,cdinv_no  from invtrd where div_code=? and depo_code=? " +
				    " and cdprt_cd=? and cdnote_no=? and cdnote_dt=? and cdnote_tp='D' and ifnull(cr_note1,'')='I' and ifnull(del_tag,'')<>'D'  ";
			
			
			ps33 = con.prepareStatement(query33);
			ps3 = con.prepareStatement(query3);
			ps4 = con.prepareStatement(query4);
			
			
			ps2 = con.prepareStatement(query2);
			

			if(depo==58 || depo==59)
			{
				ps2.setInt(1, div);
				ps2.setInt(2, depo);
				ps2.setInt(3, 80);
				ps2.setInt(4, 95);
				ps2.setString(5, vcode);
				ps2.setInt(6, div);
				ps2.setInt(7, depo);
				ps2.setInt(8, 80);
				ps2.setInt(9, 95);
				ps2.setString(10, vcode);
				ps2.setInt(11, div);
				ps2.setInt(12, depo);
				ps2.setInt(13, 80);
				ps2.setInt(14, 95);
				ps2.setString(15, vcode);
	 		    ps2.setDate(16, new java.sql.Date(vdate.getTime()));
				ps2.setInt(17, div);
				ps2.setInt(18, cmpdepo);
				ps2.setString(19, vcode);
	 		    ps2.setDate(20, new java.sql.Date(vdate.getTime()));
				ps2.setInt(21, fyear);
				ps2.setInt(22, div);
				ps2.setInt(23, depo);
				ps2.setString(24, vcode);
				ps2.setInt(25, fyear);
				ps2.setInt(26, div);
				ps2.setInt(27, depo);
				ps2.setString(28, vcode);
			}
			else
			{
				ps2.setInt(1, div);
				ps2.setInt(2, depo);
				ps2.setInt(3, 80);
				ps2.setInt(4, 95);
				ps2.setString(5, vcode);
				ps2.setInt(6, div);
				ps2.setInt(7, depo);
				ps2.setInt(8, 80);
				ps2.setInt(9, 95);
				ps2.setString(10, vcode);
				ps2.setDate(11, new java.sql.Date(vdate.getTime()));
				ps2.setInt(12, div);
				ps2.setInt(13, depo);
				ps2.setString(14, vcode);
				ps2.setDate(15, new java.sql.Date(vdate.getTime()));


				ps2.setInt(16, fyear);
				ps2.setInt(17, div);
				ps2.setInt(18, depo);
				ps2.setString(19, vcode);

				if(depo==211 || depo==42)
				{
					// do nothing 	
				}
				else
				{
					ps2.setInt(20, fyear);
					ps2.setInt(21, div);
					ps2.setInt(22, depo);
					ps2.setString(23, vcode);
				}

			}
			rs= ps2.executeQuery();

			v = new Vector();
			Date ss=null;
			double amt=0.00;
			String invno="";
			StringBuffer dinvno=null;
			double damt=0.00;
			double bal=0.00;
			boolean first=true;
			while (rs.next())
			{

				damt=0.00;
				dinvno=new StringBuffer("");
				first=true;
				if (rs.getInt(15)==95)
				{
					System.out.println("ps33 mai"+div+"  "+depo+"  "+vcode+"  "+rs.getInt(5)+" "+rs.getDate(2)) ;
					ps33.setInt(1, div);
					ps33.setInt(2, depo);
					ps33.setString(3, vcode);
					ps33.setInt(4, rs.getInt(5));
					ps33.setDate(5, rs.getDate(2));
					rs33= ps33.executeQuery();
					if (rs33.next())
					{
						damt=rs33.getDouble(3);
					}
					rs33.close();
					
					ps3.setInt(1, div);
					ps3.setInt(2, depo);
					ps3.setString(3, vcode);
					ps3.setInt(4, rs.getInt(5));
					ps3.setDate(5, rs.getDate(2));
					rs3= ps3.executeQuery();
					if (rs3.next())
					{
						//damt=rs3.getDouble(3);
						dinvno.append("Adjusted in Invoice No.: "+rs3.getString(1)+","+rs3.getString(1)+","+sdf.format(rs3.getDate(2))+","+rs3.getInt(3));
					}
					rs3.close();
				}
				else if (rs.getInt(15)==80)
				{
					ps4.setInt(1, div);
					ps4.setInt(2, depo);
					ps4.setString(3, vcode);
					ps4.setInt(4, rs.getInt(5));
					ps4.setDate(5, rs.getDate(2));
					rs4= ps4.executeQuery();
					

					while (rs4.next())
					{
						if(first)
						{
							dinvno.append("Adjust Debit Note.: ");
							first=false;
						}
						if(rs4.isLast())
							dinvno.append(rs4.getString(1));
						else
							dinvno.append(rs4.getString(1)+",");
					}
					rs4.close();
				}
 
				System.out.println("bal is "+rs.getDouble(3)+" - "+rs.getDouble(20)+" - "+damt);
				
				bal=rs.getDouble(3)-rs.getDouble(20)-damt;
				
				
				invno=rs.getString(1);
		    	if (rs.getInt(15)==81)
					invno = rs.getString(14).charAt(0)+"P"+rs.getString(14).charAt(1)+String.format("%05d",rs.getInt(5));
		    	if (rs.getInt(15)==95)
					invno = rs.getInt(16)+rs.getString(14)+"D"+String.format("%04d",rs.getInt(5));

		    	if (bal>0)
		    	{

		    		col= new Vector();
		    		col.addElement(new Boolean(false));    ///0
		    		col.addElement(invno);  // 1
		    		ss= (Date)rs.getDate(2);
		    		col.addElement(sdf.format(ss));  //2

		    		if(rs.getDate(12)!=null)
		    			col.addElement(sdf.format(rs.getDate(12))); //3
		    		else
		    			col.addElement("__/__/____");

		    		col.addElement(formatter.format(rs.getDouble(3))); //4
		    		//				col.addElement(formatter.format(rs.getDouble(4)));	//5			
		    		col.addElement(formatter.format(bal));	//5			

		    		col.addElement(""); //6  receipt
		    		col.addElement(""); //7  interest 
		    		col.addElement(""); //8  discount
		    		col.addElement(rs.getInt(5));  //9  vouno
		    		col.addElement(rs.getInt(6)); //10  state
		    		col.addElement(rs.getInt(7)); //11 area
		    		col.addElement(rs.getInt(8)); //12 region 
		    		col.addElement(rs.getInt(9)); //13 terr 
		    		col.addElement(rs.getInt(10)); //14 dis
		    		col.addElement(rs.getInt(11)); //15 msr
		    		col.addElement(0); //16 extra
		    		col.addElement(0.00); //17 balance
		    		col.addElement(rs.getInt(13));  // (sub div for 39,40)  //18
		    		col.addElement(rs.getInt(15));  // vbook_Cd // 19
		    		col.addElement(dinvno);  // 20 hidden inv no/debit note no 

		    		 
		    		v.addElement(col);
		    	}

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();
			ps3.close();
			ps33.close();
			ps4.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLOedDAO.oedList " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(rs3 != null){rs3.close();}
				if(ps3 != null){ps3.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return v;
	}
	
	public Object[] getVouno(int book_cd, int div,int depo, String edate,int fyear,String vdbcr)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		//PreparedStatement ps4 = null;
		//ResultSet rs4=null;
		Connection con=null;
		Object s[] = new Object[3];
		int maxn=0;
		int temp=0;
		
		int wdiv=div;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			String query2 ="select max(vou_no),max(serialno),max(vou_date) from ledfile " + 
            "  where fin_year=? and div_code=? AND vdepo_Code=? and vbook_cd in (?,20) " +
            " and vou_date <= ? and vdbcr=? and ifnull(del_tag,'N')<>'D'  " ;

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, fyear);
			ps2.setInt(2, wdiv);
			ps2.setInt(3, depo);
			ps2.setInt(4, book_cd);
			ps2.setString(5, edate);
			ps2.setString(6, vdbcr);
			
			rs= ps2.executeQuery();

			if (rs.next())
			{
				if (maxn> rs.getInt(1))
					temp=maxn;
				else
				{
					temp=rs.getInt(1);
					s[2]=rs.getDate(3);
				}
				s[1]=rs.getInt(2);
				maxn=temp;
			}
			
			

			s[0]=temp;
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
			System.out.println("-------------Exception in SQLOedDAO.getVouno " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return s;
	}
	
	public List getOedDataList(String partyCode,int div,int depo,int year,Date vouDate,double rateOfInterest)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null; 
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
				System.out.println(partyCode+" "+div+" "+depo+" "+year+" "+vouDate+" "+rateOfInterest);
			
/*		String query="select a.*,b.mac_code,b.vouno,ifnull(b.rec_amount,0) rcp,b.rcpdate,TO_DAYS(b.rcpdate)-TO_DAYS(a.due_date) delay from "+ 
					" (select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,r.lr_date,r.due_date,r.vac_code,r.vamount, "+
					" ifnull((r.vamount-r.rcp_amt1),0) outamt,r.vbook_cd1,r.mkt_year  "+
					" from ledfile as r where r.mkt_year<=? and r.div_code=? and r.vdepo_code=? "+ 
					" and r.vbook_cd=? and r.vac_code=? and r.vou_date between '2012-04-01' and  ? and ifnull(r.tp,'')<>'D'  "+
					" and ifnull(r.del_tag,'')<>'D'  order by r.vac_code, r.vou_date,r.vbook_cd,r.vou_no) a "+
					" left join "+
					" (select vac_code mac_code,vouno,sum(vamount) rec_amount,MAX(chq_date) rcpdate,vbook_cd,bill_date from " +
					" rcpfile where mkt_year<=? "+ 
					" and div_code=? and vdepo_code=? and vbook_cd in (20,21,22,80,98) "+ 
					" and vou_date <=? "+
					" and ifnull(del_tag,'')<>'D' group by vac_code,vouno,bill_date) b"+
					" on a.vac_code = b.mac_code and a.vou_no = b.vouno and a.vou_date=b.bill_date "+ 
			        " having (a.vamount-rcp)<=5 and b.rcpdate>a.due_date";
*/			

			// change on 13.12.2014 
			String query="select a.*,b.mac_code,b.vouno,ifnull(b.rec_amount,0) rcp,b.rcpdate,ifnull((TO_DAYS(b.rcpdate)-TO_DAYS(a.due_date)),0) delay,b.vou_no,b.vou_date,ifnull(b.crno,'') from "+ 
					" (select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,r.lr_date,r.due_date,r.vac_code,r.vamount, "+
					" ifnull((r.vamount-r.rcp_amt1),0) outamt,r.vbook_cd1,r.mkt_year  "+
					" from ledfile as r where r.mkt_year<=? and r.div_code=? and r.vdepo_code=? "+ 
					" and r.vbook_cd=? and r.vac_code=? and r.vou_date between '2012-04-01' and  ? and ifnull(r.tp,'')<>'D'  "+
					" and ifnull(r.del_tag,'')<>'D'  order by r.vac_code, r.vou_date,r.vbook_cd,r.vou_no) a "+
					" left join "+
					" (select vac_code mac_code,vouno,vamount rec_amount,chq_date rcpdate,vbook_cd,bill_date,vou_no,vou_date,ifnull(exp_code,'') crno from " +
					" rcpfile where mkt_year<=? "+ 
					" and div_code=? and vdepo_code=? and vbook_cd in (20,21,22,80,98) and vac_code=? "+ 
					" and vou_date <=? and ifnull(inv_lo,'')<>'C'  "+
					" and ifnull(del_tag,'')<>'D' ) b"+
					" on a.vac_code = b.mac_code and a.vou_no = b.vouno and a.vou_date=b.bill_date ";
//			        " where  b.rcpdate>a.due_date";
			
			ps2 = con.prepareStatement(query);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			ps2.setInt(4, 80);
			ps2.setString(5, partyCode);
			ps2.setDate(6, new java.sql.Date(vouDate.getTime()));
			ps2.setInt(7, year);
			ps2.setInt(8, div);
			ps2.setInt(9, depo);
			ps2.setString(10, partyCode);
			ps2.setDate(11, new java.sql.Date(vouDate.getTime()));
			
			rs= ps2.executeQuery();

			v = new Vector();
			Date ss=null;
			double amt=0.00;
			double intamt=0.00;
			int invno=0;
			while (rs.next())
			{
				
				System.out.println("LOOP KE ANDAR "+rs.getString(19));
				if (invno!=rs.getInt(13))
				{
					invno=rs.getInt(13);
					intamt=0.00;
				}

				if (rs.getInt(16)< 0)
					intamt+=rs.getDouble(14);
				amt=0.00;
				
				if (rs.getInt(16)>0 && invno==rs.getInt(13))
				{
					if(!rs.getString(19).trim().equalsIgnoreCase("D"))
					{

						

						col= new Vector();
						col.addElement(new Boolean(false)); //select 0
						col.addElement(rs.getString(1)+rs.getString(2)+rs.getString(3).substring(1)); //invoice no 1
						ss= (Date)rs.getDate(4); 
						col.addElement(sdf.format(ss));//invoice date 2
						col.addElement(formatter.format(rs.getDouble(8))); //bill amount 3
						//col.addElement(formatter.format(rs.getDouble(8)-intamt)); //Balance amount 
						col.addElement(formatter.format(rs.getDouble(14))); //Balance amount 4

						ss= (Date)rs.getDate(6);
						col.addElement(sdf.format(ss)); //due date 5
						if (rs.getDate(15)!=null)
						{
							ss= (Date)rs.getDate(15);
							col.addElement(sdf.format(ss)); //dd date 6
						}
						else
							col.addElement("  /  /    "); //dd date 6

						col.addElement(rs.getInt(16)); //delay days 7

						//amt = ((((rs.getDouble(8)*rateOfInterest)/100)*rs.getInt(16))/365);
						//amt = 0.00;		
						col.addElement(formatter.format(amt)); //interest amount 8

						col.addElement(rs.getInt(17)); //rcp no  9
						ss= (Date)rs.getDate(18);
						col.addElement(sdf.format(ss)); //rcp date 10

						col.addElement(formatter.format(rs.getDouble(14))); //rcp amount 11
						col.addElement(rs.getInt(16)); //hidden delay days 12
						
						col.addElement(rs.getInt(16)); //hidden delay days 13
						col.addElement(0.00); //CGST   14
						col.addElement(0.00); //SGST   15
						col.addElement(0.00); //IGST   16
						col.addElement(0.00); //NET 17


						v.addElement(col);
					}
					intamt+=rs.getDouble(14);
				}
			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in OedDAO.getOedDataList " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in OedDAO.Connection.close "+e);
			}
		}

		return v;
	}
	
/// accounts jvi adjustment /////
	public List getOedListAcc(String vcode,int div,int depo,int fyear)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null; 
		String query2=null;
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			if (depo == 8)
			{

				query2 =" select a.vou_lo, a.vou_date,a.bill_no,a.bill_date,a.bill_amt,a.tds_amt,0 rcpamt,(a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) bal " +
						" from "+ 
						" (select l.vou_no,l.bill_no,l.bill_date,l.bill_amt,l.tds_amt,l.vou_date,concat(right(vou_lo,2),left(vou_lo,1),indicator,right(vou_no,4)) vou_lo,l.vac_code,l.due_date "+
						" from ledfile l where  l.fin_year between 2013 and ? and l.div_code=? and l.vdepo_code=? "+ 
						" and l.vbook_cd in (60,61) and l.vac_code=? and ifnull(l.mkt_year,0)<> 2016 "+
						" and  ifnull(l.del_tag,'')<>'D' ) a "+
						" left join "+

						" (select r.vou_no,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vou_date,r.vou_lo,sum(r.vamount) vamount,r.vac_code "+
						" from rcpfile r where  r.fin_year<=? and r.div_code=? and r.vdepo_code=?  "+
						" and r.vbook_cd in (20,21,22,98)  and r.vac_code=? and    ifnull(r.cr_no,'')<>'C' "+
						" and  ifnull(r.del_tag,'')<>'D' group by r.vouno,r.bill_no,r.bill_date) b "+
						" on  a.vac_code=b.vac_code and a.bill_no=b.bill_no and a.bill_date=b.bill_date and a.bill_amt=b.bill_amt "+
						" where  (a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) > 0 group by a.vou_no,a.bill_no" ;
			
			}
			else
			{
					query2 =" select vou_no,vou_date,bill_no,bill_date,sum(bamt),sum(rcpamt),sum(billamt),sum(tdsamt) from "+
							" (select vou_no,vou_date,bill_no,bill_date,vamount bamt,0 rcpamt,ifnull(bill_amt,0) billamt,ifnull(tds_amt,0) tdsamt "+ 
							" from ledfile   where fin_year<=? and div_code=?  and vdepo_code=? and  vbook_cd1 in (?,?)  and vac_code=?  "+   
							" and vou_date<'2017-07-01' and ifnull(del_tag,'')<>'D' "+ 
							" union all "+
							" select vou_no,vou_date,bill_no,bill_date,vamount bamt,0 rcpamt,ifnull(bill_amt,0) billamt,ifnull(tds_amt,0) tdsamt "+ 
							" from rcpfile   where fin_year<=? and div_code=?  and vdepo_code=? and  vbook_cd in (?,?)  and vac_code=? "+   
							" and vou_date>'2017-06-30' and ifnull(del_tag,'')<>'D' "+ 
							" union all "+
							" select vou_no,vou_date,bill_no,bill_date,vamount bamt,0 rcpamt,ifnull(bill_amt,0) billamt,ifnull(tds_amt,0) tdsamt "+ 
							" from rcpfile   where div_code=?  and vdepo_code=? and  vbook_cd in (?,?)  and vac_code=? and vou_date between '2010-04-01' and '2014-03-31' and "+   
							" (vamount-chq_amt) > 0 and ifnull(del_tag,'')<>'D' "+ 
							" union all "+
							" select vouno,voudt,bill_no,bill_date,0 bamt,sum(vamount) rcpamt,0 billamt,0 tdsamt  from rcpfile where div_code=?  and vdepo_code=? "+
							" and  vbook_cd in(20,21,22,98) and vac_code=? and ifnull(del_tag,'')<>'D' group by vouno,voudt ) a group by vou_no,vou_date ";
			}

			System.out.println("yeha per aaya jvi mai");
			
			ps2 = con.prepareStatement(query2);

			if(depo==8)
			{
				ps2.setInt(1, fyear);
				ps2.setInt(2, div);
				ps2.setInt(3, depo);
				ps2.setString(4, vcode);

				ps2.setInt(5, fyear);
				ps2.setInt(6, div);
				ps2.setInt(7, depo);
				ps2.setString(8, vcode);
				
			}
			else
			{
				ps2.setInt(1, fyear);
				ps2.setInt(2, div);
				ps2.setInt(3, depo);
				ps2.setInt(4, 60);
				ps2.setInt(5, 61);
				ps2.setString(6, vcode);

				ps2.setInt(7, fyear);
				ps2.setInt(8, div);
				ps2.setInt(9, depo);
				ps2.setInt(10, 60);
				ps2.setInt(11, 61);
				ps2.setString(12, vcode);



				ps2.setInt(13, div);
				ps2.setInt(14, depo);
				ps2.setInt(15, 60);
				ps2.setInt(16, 61);
				ps2.setString(17, vcode);


				ps2.setInt(18, div);
				ps2.setInt(19, depo);
				ps2.setString(20, vcode);
			}
			rs= ps2.executeQuery();

			v = new Vector();
			Date ss=null;
			double amt=0.00;
			while (rs.next())
			{
				if ((rs.getDouble(5)-rs.getDouble(6))>0)
				{
					
					col= new Vector();
					col.addElement(new Boolean(false));    ///0
					col.addElement(rs.getString(1));  // 1
					col.addElement(rs.getString(3));  // 2
					if(rs.getDate(4)==null)
					{
						ss= (Date)rs.getDate(2);
					}
					else
					{
						ss= (Date)rs.getDate(4);
					}
					col.addElement(sdf.format(ss));  //3
					col.addElement(formatter.format(rs.getDouble(5))); //4
					col.addElement(formatter.format((rs.getDouble(5)-rs.getDouble(6))));	//5			
					col.addElement(""); //6  receipt
					col.addElement(""); //7  interest 
					col.addElement(""); //8  discount
					col.addElement(0);  //9  vouno
					col.addElement(rs.getDouble(7)); //10  bill amt
					col.addElement(rs.getDouble(8)); //11 tdsamt
					col.addElement(0); //12 region 
					col.addElement(0); //13 terr 
					col.addElement(0); //14 dis
					ss= (Date)rs.getDate(2);
					col.addElement(sdf.format(ss));  //15
					col.addElement(0); //16 msr
					col.addElement(0.00); //17 balance
					col.addElement(0);  // (sub div for 39,40)  //18

					v.addElement(col);
				}

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
			System.out.println("-------------Exception in SQLOedDAO.oedListAcc " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return v;
	}

	public int unLockCrDr(String vcode,int depo,int div,Date invdate)
	{
		PreparedStatement ps3 = null;
		Connection con=null;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
			String query3 ="update ledfile set del_tag='N' " +
			" where div_code=? and vdepo_code=? and vbook_cd1 in(90,95,91) " +
			" and vac_code=? and vou_date<=? and vamount>new3 and ifnull(del_tag,'')='X' order by vou_no,vou_date";


			ps3 = con.prepareStatement(query3);
			ps3.setInt(1, div);
			ps3.setInt(2, depo);
			ps3.setString(3, vcode);
			ps3.setDate(4, new java.sql.Date(invdate.getTime()));
			i=ps3.executeUpdate();

			
			con.commit();
			con.setAutoCommit(true);
			ps3.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLOedDAO.getUnadjCrDr " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(ps3 != null){ps3.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.getUnadjCrDr.Connection.close "+e);
			}
		}

		return i;
	}

	public String getTotalno(int book_cd, int div,int depo, Date sdate, Date edate,int fyear)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		PreparedStatement ps3 = null;
		ResultSet rs3=null;
		PreparedStatement ps1 = null;
		ResultSet rs1=null;
		Connection con=null;
		String chqquery="";
		int maxn=0;
		int minn=0;
		String s="";
		int chqno=0;
		String vdbcr="CR";
		if(depo==8)
			vdbcr="DR";
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			String query2 ="select ifnull(min(vou_no),0),ifnull(max(vou_no),0) from ledfile " + 
            "  where fin_year=? and div_code=? AND vdepo_Code=? and vbook_Cd1=? " +
            " and vou_date between ? and ? and vdbcr=? and ifnull(del_tag,'N')<>'D'  " ;


			String query3 ="select ifnull(min(vou_no),0),ifnull(max(vou_no),0) from ledfile " + 
		            "  where fin_year=? and div_code=4 and sub_div=? AND vdepo_Code=? and vbook_Cd1=? " +
		            " and vou_date between ? and ? and vdbcr=? and ifnull(del_tag,'N')<>'D'  " ;


			chqquery="select cheque_no from chqmast "+
					" where  div_code=? and depo_code=? and bank_code=? and ifnull(status,'')<>'Y' and ifnull(issued,'')<>'Y' " +
					" and dbcr='DR' and ifnull(del_tag,'')<>'D' order by date_of_chqbook,cheque_no"; 

			ps1 = con.prepareStatement(chqquery);

			if(depo==8)
			{
				ps1.setInt(1, div);
				ps1.setInt(2, depo);
				ps1.setInt(3, book_cd);
				rs1= ps1.executeQuery();
				if (rs1.next())
				{
					chqno=rs1.getInt(1);
				}
				else
					chqno=0;
				rs1.close();
			}		

			ps3 = con.prepareStatement(query3);
			ps3.setInt(1, fyear);
			ps3.setInt(2, div);
			ps3.setInt(3, BaseClass.loginDt.getCmp_code());
			ps3.setInt(4, book_cd);
			ps3.setDate(5, new java.sql.Date(sdate.getTime()));
			ps3.setDate(6, new java.sql.Date(edate.getTime()));
			ps3.setString(7, vdbcr);
			rs3= ps3.executeQuery();
			if (rs3.next())
			{
				minn=rs3.getInt(1);
				maxn=rs3.getInt(2);
			}
			rs3.close();
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, fyear);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			ps2.setInt(4, book_cd);
			ps2.setDate(5, new java.sql.Date(sdate.getTime()));
			ps2.setDate(6, new java.sql.Date(edate.getTime()));
			ps2.setString(7, vdbcr);
			
			rs= ps2.executeQuery();

			if (rs.next())
			{
				if (minn> rs.getInt(1) || minn==0)
					minn=rs.getInt(1);
				if (maxn< rs.getInt(2))
					maxn=rs.getInt(2);
			}
			if(depo==8 && chqno>0)
				s="From "+minn+" To "+maxn+"      New Cheque No :"+chqno;
			else
				s="From "+minn+" To "+maxn;
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps1.close();
			ps2.close();
			ps3.close();
			

		} catch (SQLException ex) {ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLOedDAO.getTotalno " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(ps2 != null){ps2.close();}
				if(rs3 != null){rs3.close();}
				if(ps3 != null){ps3.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return s;
	}
	public int getOrderNo(String vcode,int div,int depo,String order_no)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		int invno=0;
		try {

			con=ConnectionFactory.getConnection();

			
			String query2 ="select inv_no,inv_date " +
			" from invfst where div_code=? and depo_code=? and doc_type in(39,40) " +
			" and party_code=? and to_days(curdate())- to_days(inv_date) < 50  and order_no=? and (ifnull(del_tag,'')<>'D' and ifnull(del_tag,'')<>'D') order by doc_type,inv_no";

			
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setString(3, vcode);
			ps2.setString(4, order_no);
			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			
			if(rs.next())
			{
				invno=rs.getInt(1);
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
			System.out.println("-------------Exception in SQLOedDAO.getOrderNo " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return invno;
	}


	
	
	
	public double[] getUnadjCrDrProforma(String vcode,int depo,int div,Date invdate)
	{
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs=null;
		Connection con=null;
		double val[] = new double[2];
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
			String query2 ="select sum(vamount-ifnull(new3,0)) from Ledfile where div_code=? and vdepo_code=? and vbook_cd1=? and vac_code=? and vou_date<=? " +
			"  and (vamount-ifnull(new3,0))>0 and (ifnull(del_tag,'')<>'D' and ifnull(del_tag,'')<>'X') group by vac_code";
			
			String query3 ="update ledfile set del_tag='X' " +
			" where div_code=? and vdepo_code=? and vbook_cd1 in(90,95,91) " +
			" and vac_code=? and vou_date<=? and vamount>new3 and ifnull(del_tag,'')<>'D' order by vou_no,vou_date";


			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2,depo);
			ps2.setInt(3,90);
			ps2.setString(4,vcode);
			ps2.setDate(5, new java.sql.Date(invdate.getTime()));
			rs= ps2.executeQuery();
			if (rs.next())
			{
				val[0]=rs.getDouble(1);
			}
			rs.close();
			rs=null;
	
			ps2.setInt(1,div);
			ps2.setInt(2,depo);
			ps2.setInt(3,91);
			ps2.setString(4,vcode);
			ps2.setDate(5, new java.sql.Date(invdate.getTime()));
			rs= ps2.executeQuery();
			if (rs.next())
			{
				val[0]+=rs.getDouble(1);
			}
			rs.close();
			rs=null;

			
			ps2.setInt(1,div);
			ps2.setInt(2,depo);
			ps2.setInt(3,95);
			ps2.setString(4,vcode);
			ps2.setDate(5, new java.sql.Date(invdate.getTime()));
			
			rs= ps2.executeQuery();
			if (rs.next())
			{
				val[1]=rs.getDouble(1);
			}

			ps3 = con.prepareStatement(query3);
			ps3.setInt(1, div);
			ps3.setInt(2, depo);
			ps3.setString(3, vcode);
			ps3.setDate(4, new java.sql.Date(invdate.getTime()));
			ps3.executeUpdate();

			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();
			ps3.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLOedDAO.getUnadjCrDrProforma " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(ps3 != null){ps3.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.getUnadjCrDr.Connection.close "+e);
			}
		}

		return val;
	}

	public List getCrDrLisProforma(String vcode,int div,int depo,int year,int invno,Date invdt)
	{
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		ResultSet rs=null;
		ResultSet rs1=null;
		ResultSet rs5=null;
		Connection con=null;
		Vector v=null;
		Vector col=null; 
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			String query3 = "update ordtrd set del_tag='D' where fin_year=? and div_code=? and depo_code=?" +
			"  and cdprt_cd=? and cdinv_no=? ";
			
			String query1 = "select cdnote_no,cdnote_tp,cdnote_dt,cdnote_amt,cdprt_cd from ordtrd " +
			" where fin_year=? and div_code=? and depo_code=? and cdprt_cd=? and cdinv_no=? and ifnull(del_tag,'')<>'D'  ";
			
			String query2="update ledfile set new3=new3-? where div_code=? " +
			" and vdepo_code=? and vbook_cd1=? and vac_code=? and vou_no=? and vou_date=?  ";

			
			String query4 =	"select o.vou_no,o.vou_date,o.vamount,(o.vamount-o.new3),o.indicator,o.vbook_cd1," +
			" o.vou_lo,o.vou_yr, o.new3 from ledfile as o "+
			" where  o.div_code=? and o.vdepo_code=? and o.vbook_cd1 in (90,95,91) "+ 
			" and o.vac_code=? and o.vou_date<=? and o.vamount-o.new3>0 and ifnull(o.del_tag,'')<>'D' order by o.vou_no,o.vou_date ";
			
			String query5 =	"select i.cdinv_no,i.cdnote_no,i.cdnote_dt,i.cdnote_amt,i.cdnote_tp,i.cdnote_lo,i.cdnote_yr from  ordtrd as i "+ 
			" where i.fin_year=? and i.div_code=? and i.depo_code=?  and i.cdprt_cd=? and i.cdinv_no=? and ifnull(i.del_tag,'')<>'D' ";
			
			
			ps2 = con.prepareStatement(query2);
			ps3 = con.prepareStatement(query3);
			ps5 = con.prepareStatement(query5);
			

			
			
			
			
			v = new Vector();
			Date ss=null;
			double amt=0.00;
			
			ps5.setInt(1, year);
			ps5.setInt(2, div);
			ps5.setInt(3, depo);
			ps5.setString(4, vcode);
			ps5.setInt(5, invno);
			rs5= ps5.executeQuery();
			while (rs5.next())
			{
				 
				col= new Vector();
				col.addElement("Y");  //0
				col.addElement(formatter.format(rs5.getDouble(4)));  //1
				
				if(rs5.getString(5).equalsIgnoreCase("C"))  
					col.addElement("CR");  //2
				else if(rs5.getString(5).equalsIgnoreCase("E"))  
					col.addElement("ER");  //2
				else
					col.addElement("DR");  //2
				
				
				col.addElement(rs5.getInt(2));  //3
				///////////////////////////////////
//				ss= (Date) rs5.getDate(3);
//				col.addElement(sdf.format(ss));
				col.addElement(sdf.format(rs5.getDate(3))); //4
				///////////////////////////////////
				col.addElement(formatter.format(rs5.getDouble(4)));  //5
				
				col.addElement(rs5.getString(6));  //6
				col.addElement(rs5.getInt(7));  //7
				if(rs5.getString(5).equalsIgnoreCase("C") )
					col.addElement(90);  //8
				if(rs5.getString(5).equalsIgnoreCase("E"))
					col.addElement(91);  //8
				else
					col.addElement(95);  //8
				v.addElement(col);

			}
			
			
			ps4 = con.prepareStatement(query4);
			ps4.setInt(1, div);
			ps4.setInt(2, depo);
			ps4.setString(3, vcode);
			ps4.setDate(4, new java.sql.Date(invdt.getTime()));

			
			rs= ps4.executeQuery();	
			
			while (rs.next())
			{
				 
				col= new Vector();
					col.addElement("");  //0
		 
				col.addElement(formatter.format(0.00));  //1
				
				if(rs.getInt(6)==90 )
					col.addElement("CR"); //2
				else if(rs.getInt(6)==91)
					col.addElement("ER"); //2
				else
					col.addElement("DR"); //2
				
				
				col.addElement(rs.getInt(1));  //3
				///////////////////////////////////
				ss= (Date)rs.getDate(2);
				col.addElement(sdf.format(ss)); //4
				///////////////////////////////////
				col.addElement(formatter.format(rs.getDouble(4)));  //5
				
				col.addElement(rs.getString(7)); //6
				col.addElement(rs.getInt(8));  //7
				col.addElement(rs.getInt(6));  //8  vbook_cd
				
				v.addElement(col);

			}
			
			
			
			
			
			
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setString(4, vcode);
			ps1.setInt(5, invno);

			rs1 = ps1.executeQuery();

			while (rs1.next())
			{

				ps2.setDouble(1, rs1.getDouble(4));
				ps2.setInt(2, div);
				ps2.setInt(3, depo);
				if (rs1.getString(2).equalsIgnoreCase("C"))
					ps2.setInt(4, 90);
				if (rs1.getString(2).equalsIgnoreCase("E"))
					ps2.setInt(4, 91);
				if (rs1.getString(2).equalsIgnoreCase("D"))
					ps2.setInt(4, 95);
				ps2.setString(5, vcode);
				ps2.setInt(6,rs1.getInt(1));
				ps2.setDate(7, new java.sql.Date(rs1.getDate(3).getTime()));
				
				i= ps2.executeUpdate();


			}

			ps3.setInt(1, year);
			ps3.setInt(2, div);
			ps3.setInt(3, depo);
			ps3.setString(4, vcode);
			ps3.setInt(5, invno);
			i= ps3.executeUpdate();
			
			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			rs1.close();
			ps1.close();
			ps2.close();
			ps3.close();
			ps4.close();
			rs5.close();
			ps5.close();


		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLOedDAO.getCrDrLisProforma " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(ps2 != null){ps2.close();}
				if(ps3 != null){ps3.close();}
				if(ps4 != null){ps4.close();}
				if(rs5 != null){rs5.close();}
				if(ps5 != null){ps5.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return v;
	}	
	
	public List getCrListProforma(String vcode,int div,int depo,Date invdate)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null; 
		int i=0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		NumberFormat formatter = new DecimalFormat("0.00");
		try {

			con=ConnectionFactory.getConnection();

			
			String query2 ="select vou_no,vou_date,(vamount-new3),indicator,vbook_cd1,vou_lo,vou_yr " +
			" from ledfile where div_code=? and vdepo_code=? and vbook_cd1 in(90,95,91) " +
			" and vac_code=? and vou_date<=? and vamount>new3 and (ifnull(del_tag,'')<>'D' and ifnull(del_tag,'')='X') order by vou_no,vou_date";

			
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setString(3, vcode);
			ps2.setDate(4, new java.sql.Date(invdate.getTime()));
			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			Date ss=null;
			double amt=0.00;
			while (rs.next())
			{
				col= new Vector();
				col.addElement("");  //0
				col.addElement(formatter.format(0.00));  //1
				
				if(rs.getInt(5)==90 )
					col.addElement("CR");  //2
				else if(rs.getInt(5)==91)
					col.addElement("ER");  //2
				else
					col.addElement("DR"); //2
				
				
				col.addElement(rs.getInt(1)); //3
				///////////////////////////////////
				ss= (Date)rs.getDate(2); 
				col.addElement(sdf.format(ss));  //4
				///////////////////////////////////
				col.addElement(formatter.format(rs.getDouble(3)));  //5
				
				col.addElement(rs.getString(6)); //6
				col.addElement(rs.getInt(7));  //7
				col.addElement(rs.getInt(5));  // VBOOK_CD1  8
				
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
			System.out.println("-------------Exception in SQLOedDAO.batchlist " + ex);
			i=-1;
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return v;
	}


	public int getOrderNoProforma(String vcode,int div,int depo,String order_no)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		int invno=0;
		try {

			con=ConnectionFactory.getConnection();

			
			String query2 ="select inv_no,inv_date " +
			" from ordfst where div_code=? and depo_code=? and doc_type in(39,40) " +
			" and party_code=? and to_days(curdate())- to_days(inv_date) < 50  and order_no=? and (ifnull(del_tag,'')<>'D' and ifnull(del_tag,'')<>'D') order by doc_type,inv_no";

			
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setString(3, vcode);
			ps2.setString(4, order_no);
			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			
			if(rs.next())
			{
				invno=rs.getInt(1);
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
			System.out.println("-------------Exception in SQLOedDAO.getOrderNo " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return invno;
	}

	
	
	
	public Object[] getSlipno(int depo, int fyear)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		PreparedStatement ps3 = null;
		ResultSet rs3=null;
		//PreparedStatement ps4 = null;
		//ResultSet rs4=null;
		Connection con=null;
		Object s[] = new Object[3];
		int maxn=0;
		int temp=0;
		
	
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			String query2 ="select max(cases),max(system_dt) from ordfst " + 
            "  where fin_year=?  and depo_Code=?  " +
            "  and ifnull(del_tag,'N')<>'D'  " ;




			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, fyear);
			ps2.setInt(2, depo);
			
			rs= ps2.executeQuery();

			if (rs.next())
			{
				s[1]=rs.getInt(1);
				s[2]=rs.getDate(2);
			}
			
			

			s[0]=temp;
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
			System.out.println("-------------Exception in SQLOedDAO.getSlipno " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return s;
	}


	public int getVounoNew(int book_cd, int div,int depo, String edate,int fyear,String vdbcr)
	{

		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Object s[] = new Object[3];
		int maxn=0;
		int temp=0;
		Date idt=null;
		int wdiv=div;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			String query2 ="select max(vou_no) from ledfile " + 
            "  where fin_year=? AND vdepo_Code=? and div_code=? and vbook_Cd in (10,20) and " +
            "  ifnull(del_tag,'N')<>'D'  " ;


			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, fyear);
			ps2.setInt(2, depo);
			ps2.setInt(3, div);
			
			rs= ps2.executeQuery();

			if (rs.next())
			{
				temp=rs.getInt(1);
				System.out.println("temp "+temp);
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
			System.out.println("-------------Exception in SQLOedDAO.getVounoNew " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLOedDAO.Connection.close "+e);
			}
		}

		return temp;
	}
	
	
	
}
