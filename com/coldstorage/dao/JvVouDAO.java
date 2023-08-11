package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.coldstorage.dto.RcpDto;
import com.coldstorage.view.BaseClass;

public class JvVouDAO 
{
	NumberFormat nf=null;
	
	public List getJvList(Date invDt,int depo_code,int div,int fyear)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		 
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			nf = new DecimalFormat("0.00");
			
			
			String query2 = " select a.* from "+
			"(select f.vou_lo,f.vou_no,f.vou_date,f.vamount,f.vdbcr,f.vac_code,p.mac_name," +
			"f.vnart_2,f.vnart_1,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_no from ledfile as f ,partyfst as p "+
			"where f.vac_code=p.mac_code  and f.fin_year=? and f.div_code=? and f.vdepo_code=?  and "+ 
			"f.vbook_cd=? and f.vou_date=? and   ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=?  and ifnull(p.del_tag,'')<>'D') a  order by a.vou_no,a.serialno  ";  					
/*			" union all "+
			"select f.vou_lo,f.vou_no,f.vou_date,f.vamount,f.vdbcr,f.vac_code,p.mac_name," +
			"f.vnart_2,f.vnart_1,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_no from ledfile as f ,partyfst as p "+
			"where f.vac_code=p.mac_code  and f.fin_year=? and f.div_code=? and f.vdepo_code=?  and "+ 
			"f.vbook_cd=? and f.vou_date=? and   ifnull(f.del_tag,'')<>'D'  and p.depo_code=? and ifnull(p.del_tag,'')<>'D') a  order by a.vou_no,a.serialno ";  					
*/
			
			
		  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,30); 
			ps2.setDate(5, new java.sql.Date(invDt.getTime()));
			ps2.setInt(6,div);
			ps2.setInt(7, depo_code);
			
/*			ps2.setInt(8,fyear);
			ps2.setInt(9,div);
			ps2.setInt(10, depo_code);
			ps2.setInt(11,30); 
			ps2.setDate(12, new java.sql.Date(invDt.getTime()));
			ps2.setInt(13, depo_code);
*/
			
			rs= ps2.executeQuery();
			
			RcpDto rcp=null;
			v = new Vector();
			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;
			boolean cfirst=true;
			int vou=0;
			double total=0.00;
			while (rs.next())
			{
				
				 
				if (first)
				{
					vou=rs.getInt(2);
					inv = rs.getString(1)+rs.getString(2).substring(1);
					first=false;
					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(2));
					rcp.setVou_date(rs.getDate(3));
					rcp.setVamount(rs.getDouble(4));
					rcp.setVdbcr(rs.getString(5));
					rcp.setExp_code(rs.getString(6));
					rcp.setExp_name(rs.getString(7));
					rcp.setVnart2(rs.getString(8));
					rcp.setVnart1(rs.getString(9));
//					cfirst=false;
				}
				  
				if (vou!=rs.getInt(2))
				{
					
					col= new Vector();
					col.addElement(inv);//concat inv_no
					col.addElement(rcp.getParty_name());//party name
	                col.addElement(rcp);
	                col.addElement(row);
					v.addElement(col);
					
					vou=rs.getInt(2);
					inv = rs.getString(1)+rs.getString(2).substring(1);
					total=0.00;
					rcp= new RcpDto();
					row = new Vector();   // Create new Table row
					rcp.setVou_no(rs.getInt(2));
					rcp.setVou_date(rs.getDate(3));
					rcp.setVamount(rs.getDouble(4));
					rcp.setVdbcr(rs.getString(5));
					rcp.setExp_code(rs.getString(6));
					rcp.setExp_name(rs.getString(7));
					rcp.setVnart2(rs.getString(8));
					rcp.setVnart1(rs.getString(9));
//					cfirst=false;
				}
				
				
				
				
				if (cfirst)
				{
					
					
					colum = new Vector();
					colum.addElement(rs.getString(13));   // ref no 0
					colum.addElement("");   // tp 1
					
					colum.addElement(rs.getString(6));   // exp code 2
					colum.addElement(rs.getString(7));  //exp  name 3
					colum.addElement(rs.getString(9)); // narration 4
					if(rs.getString(5).equalsIgnoreCase("DR"))
					{
						colum.addElement(nf.format(rs.getDouble(4)));  //vamount 5
						colum.addElement("");   // hidden 6
						colum.addElement("");   // hidden 7
						colum.addElement("");   // hidden 8
						colum.addElement(0.00);  //vamount 9
					}
					else
					{
						colum.addElement(0.00);  //vamount 5
						colum.addElement("");   // hidden 6
						colum.addElement("");   // hidden 7
						colum.addElement("");   // hidden 8
						colum.addElement(nf.format(rs.getDouble(4)));  //vamount 9
						
					}
					colum.addElement(rs.getInt(12));  //serial no 10
					row.addElement(colum);
				}
				cfirst=true;
				
				 
			}

				 
			// end of file record add 
			
			if (!first)
			{
				col= new Vector();
				col.addElement(inv);//concat inv_no
				col.addElement(rcp.getParty_name());//party name
	            col.addElement(rcp);
	            col.addElement(row);
				v.addElement(col);
			}
			/// end of file record added 
			 
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				 System.out.println("yaha kuch errr hai "+ex);
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in JvVouDAO.getVouList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in JvVouDAO.Connection.close "+e);
			}
		}

		return v;
	}	
 	
////////////////JV Voucher getDetail

	public RcpDto getVouDetail(int vou,int depo_code,int div,int fyear)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=null;
		
		try {

			 
			
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");
			
			String query2 = " select a.* from "+
			"(select f.vou_lo,f.vou_no,f.vou_date,f.vamount,f.vdbcr,f.vac_code,p.mac_name," +
			"f.vnart_2,f.vnart_1,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_no from ledfile as f ,partyfst as p "+
			"where f.vac_code=p.mac_code  and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd=? and f.vou_no=? and   ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=?   and ifnull(p.del_tag,'')<>'D') a  order by a.vou_no,a.serialno  ";  					
/*			" union all "+
			"select f.vou_lo,f.vou_no,f.vou_date,f.vamount,f.vdbcr,f.vac_code,p.mac_name," +
			"f.vnart_2,f.vnart_1,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_no from ledfile as f ,partyfst as p "+
			"where f.vac_code=p.mac_code  and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd=? and f.vou_no=? and   ifnull(f.del_tag,'')<>'D' and p.depo_code=?  and ifnull(p.del_tag,'')<>'D') a  order by a.vou_no,a.serialno ";  					
*/
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,30); 
			ps2.setInt(5, vou);
			ps2.setInt(6,div);
			ps2.setInt(7, depo_code);
			
/*			ps2.setInt(8,fyear);
			ps2.setInt(9,div);
			ps2.setInt(10, depo_code);
			ps2.setInt(11,30); 
			ps2.setInt(12, vou);
			ps2.setInt(13, depo_code);
*/
			rs= ps2.executeQuery();
			
			
			
		 
			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;
			boolean cfirst=false; 
			while (rs.next())
			{
				 
				if (first)
				{
					first=false;
					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(2));
					rcp.setVou_date(rs.getDate(3));
					rcp.setVamount(rs.getDouble(4));
					rcp.setVdbcr(rs.getString(5));
					rcp.setVac_code(rs.getString(6));
					rcp.setExp_name(rs.getString(7));
					rcp.setVnart2(rs.getString(8));
					rcp.setVnart1(rs.getString(9));
				}
				
				 
				
				
				 
				
				/// Table detail set here
				 
					colum = new Vector();
					colum.addElement(rs.getString(13));   // ref no  //
					colum.addElement("");   // tp
					
					colum.addElement(rs.getString(6));   // exp code
					colum.addElement(rs.getString(7));  //exp  name
					colum.addElement(rs.getString(9)); // narration
					if(rs.getString(5).equalsIgnoreCase("DR"))
					{
						colum.addElement(nf.format(rs.getDouble(4)));  //vamount
						colum.addElement("");   // hidden
						colum.addElement("");   // hidden
						colum.addElement("");   // hidden
						colum.addElement(0.00);  //vamount
					}
					else
					{
						colum.addElement(0.00);  //vamount
						colum.addElement("");   // hidden
						colum.addElement("");   // hidden
						colum.addElement("");   // hidden
						colum.addElement(nf.format(rs.getDouble(4)));  //vamount
						
					}
					colum.addElement(rs.getInt(12));  //serial no
					row.addElement(colum);
				 
 
				

			}

			if (rcp!=null)
			{
	//			rcp.setVamount(total);
				rcp.setVdetail(row);
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
			System.out.println("-------------Exception in JvVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in JvVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}
	
	
	
	
	//// method to get JV vou no for account package 
	public int getVouNo(int depo,int div,int year)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		try {
			con=ConnectionFactory.getConnection();
			String query1 ="select max(vou_no) from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd=? and  ifnull(del_tag,'')<>'D' "; 
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setInt(4, 30);
			
			rst =ps1.executeQuery();

			if(rst.next()){
				i =rst.getInt(1); 
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
			System.out.println("-------------Exception in JVVouDAO.getVouNo " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in JVVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 
	

 
		

	
  //// method to add record in led file (Journal voucher)	
	public int addLed(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		RcpDto rcp=null;
		int size=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			

			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vou_lo,vou_no, " +
			"vou_date,vbk_code,vgrp_code,vac_code,vnart_1,vnart_2,vamount,vdbcr, " +
			"Created_by,Created_date,serialno,fin_year,mnth_code,bill_no,bill_date,bill_amt) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			

		    // 040+depo+01
			
			ledps=con.prepareStatement(led);
			
			size = cList.size();
			
			for(int j=0;j<size;j++)
			{
				rcp = (RcpDto) cList.get(j);
				ledps.setInt(1, rcp.getDiv_code());
				ledps.setInt(2, rcp.getVdepo_code());
				ledps.setInt(3, rcp.getVbook_cd());
				
				ledps.setString(4, rcp.getVou_lo());
				ledps.setInt(5, rcp.getVou_no());
				ledps.setDate(6, new java.sql.Date(rcp.getVou_date().getTime()));
				if(rcp.getVbook_cd()==30)
					ledps.setInt(7,0); // vbk_Code
				else
					ledps.setInt(7,rcp.getVbk_code()); // vbk_Code
				ledps.setInt(8,rcp.getVgrp_code()); // vgrp_Code
				ledps.setString(9, rcp.getVac_code());
				ledps.setString(10, rcp.getVnart1());
				ledps.setString(11, rcp.getVnart2());
				ledps.setDouble(12, rcp.getVamount());
				ledps.setString(13, rcp.getVdbcr());
				ledps.setInt(14, rcp.getCreated_by());
				ledps.setDate(15, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(16, rcp.getSerialno());
				ledps.setInt(17, rcp.getFin_year());
				ledps.setInt(18, rcp.getMnth_code());
				ledps.setString(19, rcp.getBill_no());
				ledps.setDate(20,  rcp.getBill_date()!=null ? new java.sql.Date(rcp.getBill_date().getTime()) : null);
				ledps.setDouble(21, rcp.getBill_amt());
				
				i=ledps.executeUpdate();

			}
       
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			
		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLJvVouDAO.addLed " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLJvVouDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	

	 //// method to add record in led file (bank voucher [Creditors Payment])	
		public int addLed1(ArrayList cList)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;
			PreparedStatement rcpps=null;
			PreparedStatement outps=null;
			RcpDto rcp=null;
			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				

				String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
				"vou_date,vgrp_code,vac_code,vnart_1,name,vamount,vdbcr,chq_no,chq_date,chq_amt,bank_chg,tp, " +
				"Created_by,Created_date,serialno,fin_year,mnth_code,vbk_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

				String rp="insert into rcpfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
				"vou_date,head_code,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date,chq_amt,bill_no,bill_date,bill_amt,vouno,voudt,tds_amt, " +
				"Created_by,Created_date,serialno,fin_year,mnth_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

				String out="update rcpfile set chq_amt=chq_amt+?,modified_by=?,modified_date=? " +
				" where div_code=? and vdepo_code=? and vbook_cd in (60,61) and vou_no=? and vou_date=?";

				
				// 040+depo+01
				
				ledps=con.prepareStatement(led);
				rcpps=con.prepareStatement(rp);
				outps=con.prepareStatement(out);
				
				for(int j=0;j<cList.size();j++)
				{
					rcp = (RcpDto) cList.get(j);
					rcpps.setInt(1, rcp.getDiv_code());
					rcpps.setInt(2, rcp.getVdepo_code());
					rcpps.setInt(3, rcp.getVbook_cd1());
					rcpps.setInt(4, 0);
					rcpps.setString(5, rcp.getVou_lo());
					rcpps.setInt(6, rcp.getVou_no());
					rcpps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					rcpps.setString(8,""); // vgrp_Code
					rcpps.setString(9, rcp.getVac_code());
					rcpps.setString(10, rcp.getVnart1());
					rcpps.setDouble(11, rcp.getVamount());
					rcpps.setString(12, "DR");
					rcpps.setString(13, rcp.getChq_no());
					rcpps.setDate(14, new java.sql.Date(rcp.getChq_date().getTime()));
					rcpps.setDouble(15, rcp.getChq_amt());
					rcpps.setString(16, rcp.getBill_no());
					rcpps.setDate(17, new java.sql.Date(rcp.getBill_date().getTime()));
					rcpps.setDouble(18, rcp.getBill_amt());
					rcpps.setInt(19, rcp.getVouno());
					rcpps.setDate(20, new java.sql.Date(rcp.getVoudt().getTime()));
					rcpps.setDouble(21, rcp.getTds_amt());
					rcpps.setInt(22, rcp.getCreated_by());
					rcpps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
					rcpps.setInt(24, rcp.getSerialno());
					rcpps.setInt(25, rcp.getFin_year());
					rcpps.setInt(26, rcp.getMnth_code());
					i=rcpps.executeUpdate();
					
					
					/// update outstanding file 
					outps.setDouble(1, rcp.getVamount());
					outps.setInt(2, rcp.getCreated_by());
					outps.setDate(3, new java.sql.Date(rcp.getCreated_date().getTime()));
					// where clause
					outps.setInt(4, rcp.getDiv_code());
					outps.setInt(5, rcp.getVdepo_code());
					//outps.setInt(6, rcp.getVbook_cd1());
					outps.setInt(6, rcp.getVouno());
					outps.setDate(7, new java.sql.Date(rcp.getVoudt().getTime()));
					i=outps.executeUpdate();
					
				}
	       
				    //  add record in led file
					if (rcp!=null)
					{	
					ledps.setInt(1, rcp.getDiv_code());
					ledps.setInt(2, rcp.getVdepo_code());
					ledps.setInt(3, rcp.getVbook_cd());
					ledps.setInt(4, rcp.getVbook_cd1());
					ledps.setString(5, rcp.getVou_lo());
					ledps.setInt(6, rcp.getVou_no());
					ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					ledps.setInt(8,rcp.getVgrp_code()); // vgrp_Code
					ledps.setString(9, rcp.getVac_code());
					ledps.setString(10, rcp.getVnart1());
					ledps.setString(11, rcp.getParty_name());
					ledps.setDouble(12, rcp.getChq_amt());
					ledps.setString(13, "DR");
					ledps.setString(14, rcp.getChq_no());
					ledps.setDate(15, new java.sql.Date(rcp.getChq_date().getTime()));
					ledps.setDouble(16, rcp.getChq_amt());
					ledps.setDouble(17, rcp.getBank_chg());
					ledps.setString(18, String.valueOf(rcp.getTp()));
					ledps.setInt(19, rcp.getCreated_by());
					ledps.setDate(20, new java.sql.Date(rcp.getCreated_date().getTime()));
					ledps.setInt(21, rcp.getSerialno());
					ledps.setInt(22, rcp.getFin_year());
					ledps.setInt(23, rcp.getMnth_code());
					ledps.setInt(24, rcp.getVbk_code());
					i=ledps.executeUpdate();
					}
					
				con.commit();
				con.setAutoCommit(true);
				ledps.close();
				rcpps.close();
				outps.close();
				
			} catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.addLed -BankPaymentCR " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);
					if(ledps != null){ledps.close();}
					if(rcpps != null){rcpps.close();}
					if(outps != null){outps.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close -BankPaymentCR "+e);
				}
			}

			return (i);
		}			
	
	
  //// method to update record in led file (cash voucher)	
		public int updateLed(ArrayList cList)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;
			PreparedStatement ledps1=null;
			RcpDto rcp=null;
			String led="";
			String ledjv="";
			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				

				
				led="update  ledfile set vbook_cd=?,vou_date=?,vgrp_code=?," +
				"vnart_1=?,vamount=?,chq_no=?,chq_date=?,chq_amt=?,bill_no=?,bill_date=?,modified_by=?,modified_date=?,vnart_2=? " +
				"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? " +
				"and vou_no=? and serialno=?";



				
				ledps=con.prepareStatement(led);
				
				ledps1=con.prepareStatement(ledjv);
				for(int j=0;j<cList.size();j++)
				{
					rcp = (RcpDto) cList.get(j);
					System.out.println("year  kya hai "+rcp.getFin_year());
					System.out.println("div  kya hai "+rcp.getDiv_code());
					System.out.println("depo  kya hai "+rcp.getVdepo_code());
					System.out.println("vno  kya hai "+rcp.getVou_no());
					System.out.println("serial kya hai "+rcp.getSerialno());
					System.out.println("vbook  kya hai "+rcp.getVbook_cd1());
						ledps.setInt(1, rcp.getVbook_cd());
						ledps.setDate(2, new java.sql.Date(rcp.getVou_date().getTime()));
						ledps.setInt(3,rcp.getVgrp_code()); // vgrp_Code
						ledps.setString(4, rcp.getVnart1());
						ledps.setDouble(5, rcp.getVamount());
						ledps.setString(6, rcp.getChq_no());
						if(rcp.getChq_date()!=null)
							ledps.setDate(7, new java.sql.Date(rcp.getChq_date().getTime()));
						else
							ledps.setDate(7, null);

						ledps.setDouble(8, rcp.getChq_amt());
						ledps.setString(9, rcp.getBill_no());
						if(rcp.getBill_date()!=null)
							ledps.setDate(10, new java.sql.Date(rcp.getBill_date().getTime()));
						else
							ledps.setDate(10, null);
						ledps.setInt(11, rcp.getModified_by());
						ledps.setDate(12, new java.sql.Date(rcp.getModified_date().getTime()));
						ledps.setString(13, rcp.getVnart2());

						// where clause
						ledps.setInt(14, rcp.getFin_year());
						ledps.setInt(15, rcp.getDiv_code());
						ledps.setInt(16, rcp.getVdepo_code());
						ledps.setInt(17, rcp.getVbook_cd1());
						ledps.setInt(18, rcp.getVou_no());
						ledps.setInt(19, rcp.getSerialno());

						i=ledps.executeUpdate();
				}
	       
				con.commit();
				con.setAutoCommit(true);
				ledps.close();
				ledps1.close();
				
			} catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.updateLed " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);
					if(ledps != null){ledps.close();}
					if(ledps1 != null){ledps1.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}

			return (i);
		}		

		
		 //// method to update record in led file (Bank voucher Direct)	
		public int deleteLed(int div,int depo,int year,int uid,int vno)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;

			
			RcpDto rcp=null;

			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				


				
				String led="update ledfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
				"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
				"and vou_no=? ";

				
				ledps=con.prepareStatement(led);
				
					 
					ledps.setString(1, "D");
					ledps.setInt(2, uid);
					//ledps.setDate(3, (new Date()).getTime());

					// where clause
					ledps.setInt(3, year);
					ledps.setInt(4, div);
					ledps.setInt(5, depo);
					ledps.setInt(6, 30);
					ledps.setInt(7, vno);
					
					
					i=ledps.executeUpdate();
	       
				   
				 
				
				
				con.commit();
				con.setAutoCommit(true);
				ledps.close();
				
			} catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLJvVouDAO.deleteLed " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);
					if(ledps != null){ledps.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLJvVouDAO.Connection.close "+e);
				}
			}

			return (i);
		}		

		
		
		 //// method to update record in led file (Bank voucher Direct)	
		public int deleteLed1(ArrayList cList)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;
			PreparedStatement rcpps=null;
			PreparedStatement outps=null;

			
			RcpDto rcp=null;

			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				


				
				String led="update ledfile set del_tag=?,modified_by=?,modified_date=?  " +
				"where div_code=? and vdepo_code=? and vbook_cd1=? " +
				"and vou_no=? and vou_date=?";

				String rp="update rcpfile set del_tag=?,modified_by=?,modified_date=?  " +
				"where div_code=? and vdepo_code=? and vbook_cd=? " +
				"and vou_no=? and vou_date=?";

				String out="update rcpfile set chq_amt=chq_amt-?,modified_by=?,modified_date=?  " +
				"where div_code=? and vdepo_code=? and vbook_cd in (60,61) " +
				"and vou_no=? and vou_date=?";

				
				ledps=con.prepareStatement(led);
				rcpps=con.prepareStatement(rp);
				outps=con.prepareStatement(out);
				
				for(int j=0;j<cList.size();j++)
				{
					rcp = (RcpDto) cList.get(j);
					 
					rcpps.setString(1, "D");
					rcpps.setInt(2, rcp.getModified_by());
					rcpps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));

					// where clause
					rcpps.setInt(4, rcp.getDiv_code());
					rcpps.setInt(5, rcp.getVdepo_code());
					rcpps.setInt(6, rcp.getVbook_cd1());
					rcpps.setInt(7, rcp.getVou_no());
					rcpps.setDate(8, new java.sql.Date(rcp.getVou_date().getTime()));
					i=rcpps.executeUpdate();
					
					
					// outstanding file record updated 
					outps.setDouble(1,rcp.getVamount() );
					outps.setInt(2, rcp.getModified_by());
					outps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));

					// where clause
					outps.setInt(4, rcp.getDiv_code());
					outps.setInt(5, rcp.getVdepo_code());
					outps.setInt(6, rcp.getVouno());
					outps.setDate(7, new java.sql.Date(rcp.getVoudt().getTime()));
					i=outps.executeUpdate();
				}
	       

					ledps.setString(1, "D");
					ledps.setInt(2, rcp.getModified_by());
					ledps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));
	  
					System.out.println(" vbook_cd 1 "+ rcp.getVbook_cd1());
					
					// where clause
					ledps.setInt(4, rcp.getDiv_code());
					ledps.setInt(5, rcp.getVdepo_code());
					ledps.setInt(6, rcp.getVbook_cd1());
					ledps.setInt(7, rcp.getVou_no());
					ledps.setDate(8, new java.sql.Date(rcp.getVou_date().getTime()));
					
					i=ledps.executeUpdate();

				 
				
				
				con.commit();
				con.setAutoCommit(true);
				ledps.close();
				rcpps.close();
				outps.close();
				
			} catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.deleteLed - Bank voucher Direct" + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);
					if(ledps != null){ledps.close();}
					if(rcpps != null){rcpps.close();}
					if(outps != null){outps.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}

			return (i);
		}				
		
	public ArrayList getReceipt(int year,int depo,int div,int sinv,int einv,int bkcd)
	{
		PreparedStatement ps1 = null;
		ResultSet rs=null;
		Connection con=null;
		int i=0;
		RcpDto rcp=null;
	    ArrayList data=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			String query1=" select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,r.vac_code,concat(p.mac_name,',',p.mcity),r.vnart_1,r.vamount,r.chq_no,r.chq_date,r.chq_amt,r.bill_no,r.bill_date,r.bill_amt "+ 
			" from rcpfile as r, partyfst as p where r.div_code=? and r.vdepo_code=? and r.vbook_cd=? "+ 
			" and r.fin_year=? and r.vou_no between ? and ? and r.vac_code=p.mac_code ";
 

			ps1 = con.prepareStatement(query1);
			ps1.setInt(1, div);
			ps1.setInt(2, depo);
			ps1.setInt(3, bkcd);
			ps1.setInt(4, year);
			ps1.setInt(5, sinv);
			ps1.setInt(6, einv);
			
			rs=ps1.executeQuery();
			
			String inv = null;
			data = new ArrayList();
			 
			while(rs.next())
			{
				
				inv = rs.getString(2).charAt(0)+rs.getString(2).replace(rs.getString(2).charAt(0), 'R')+rs.getString(3).substring(1);
				System.out.println(inv+rs.getDouble(8));
				rcp= new RcpDto();
				rcp.setVou_lo(inv);
				rcp.setVou_no(rs.getInt(3));
				rcp.setVou_date(rs.getDate(4));
				rcp.setVac_code(rs.getString(5));
				rcp.setParty_name(rs.getString(6));
				rcp.setVnart1(rs.getString(7));
				rcp.setVamount(rs.getDouble(8));
				rcp.setChq_no(rs.getString(9));
				rcp.setChq_date(rs.getDate(10));
                rcp.setChq_amt(rs.getDouble(11));				
                rcp.setBill_no(rs.getString(12));
                rcp.setBill_date(rs.getDate(13));
                data.add(rcp);
                
			}
		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.addReceipt " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}
			return data;
		}	
	
	
	
	public List getRcpList(int div,int depo,int bkcd,Date dt)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null; 
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			

			String query2=" select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,r.vac_code,concat(p.mac_name,',',p.mcity),r.vnart_1,r.vamount,r.chq_no,r.chq_date,(r.vamount-r.rcp_amt1) as bal "+ 
			" from ledfile as r, partyfst as p where r.div_code=? and r.vdepo_code=? and r.vbook_cd=? "+ 
			" and vou_date=? and (r.vamount-r.rcp_amt1)>0 and r.vac_code=p.mac_code ";

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setInt(3, bkcd);
			ps2.setDate(4, new java.sql.Date(dt.getTime()));
			rs= ps2.executeQuery();

			RcpDto rcp=null;
			v = new Vector();
			String inv=null;
			while (rs.next())
			{
				col= new Vector();
				inv = rs.getString(2).charAt(0)+rs.getString(2).replace(rs.getString(2).charAt(0), 'R')+rs.getString(3).substring(1);
				col.addElement(inv);
				col.addElement(rs.getString(6));

				rcp= new RcpDto();
				rcp.setVou_lo(inv);
				rcp.setVou_no(rs.getInt(3));
				rcp.setVou_date(rs.getDate(4));
				rcp.setVac_code(rs.getString(5));
				rcp.setParty_name(rs.getString(6));
				rcp.setVnart1(rs.getString(7));
				rcp.setVamount(rs.getDouble(8));
				rcp.setChq_no(rs.getString(9));
				rcp.setChq_date(rs.getDate(10));
				rcp.setBill_amt(rs.getDouble(11));
                col.addElement(rcp);
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
			System.out.println("-------------Exception in SQLOedDAO.oedList " + ex);
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
	
		

	
	//public RcpDto getVouDetail(int vou,int depo_code,int div,int fyear)
	public List getMultipleVouDetail(int startVoucher,int endVoucher,int depo_code,int div,int fyear,int doctp,String tp,int cmpdepo, Date sdate, Date edate)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		String query2=null;
		List voucherList=null;
		SimpleDateFormat sdf=null;
		int cmpcode=BaseClass.loginDt.getCmp_code();
		try {

			System.out.println("JV MAI AAYA ");
			
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");
			
			
			
			if(startVoucher==0)
			{
				query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.vamount,f.vdbcr,f.exp_code,p.mac_name," +
						"f.vnart_2,f.vnart_1,f.vbook_cd,f.vgrp_code,f.serialno,f.vac_code,f.sub_div,ifnull(f.bill_no,'') from ledfile as f ,partyfst as p  "+
						"where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=? and f.vou_date  between ? and ?  and f.vac_Code<>'4520000' and  ifnull(f.del_tag,'')<>'D' " +
						" and f.exp_code=p.mac_code and p.div_code in (?,f.sub_div) and p.depo_code in (?,?)  and ifnull(p.del_tag,'')<>'D' order by f.vou_no,f.serialno ";
			}
			else
			{
				query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.vamount,f.vdbcr,f.exp_code,p.mac_name," +
						"f.vnart_2,f.vnart_1,f.vbook_cd,f.vgrp_code,f.serialno,f.vac_code,f.sub_div,ifnull(f.bill_no,'') from ledfile as f ,partyfst as p  "+
						"where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=? and f.vou_no  between ? and ?  and f.vac_Code<>'4520000' and  ifnull(f.del_tag,'')<>'D' " +
						" and f.exp_code=p.mac_code and p.div_code in (?,f.sub_div) and p.depo_code in (?,?)  and ifnull(p.del_tag,'')<>'D' order by f.vou_no,f.serialno ";
			}

			ps2 = con.prepareStatement(query2);

			if(startVoucher>0)
			{
				ps2.setInt(1,fyear);
				ps2.setInt(2,div);
				ps2.setInt(3, depo_code);
				ps2.setInt(4,30); 
				ps2.setInt(5, startVoucher);
				ps2.setInt(6, endVoucher);
				ps2.setInt(7,div);
				ps2.setInt(8, cmpcode);
				ps2.setInt(9, depo_code);
			}
			else
			{
				ps2.setInt(1,fyear);
				ps2.setInt(2,div);
				ps2.setInt(3, depo_code);
				ps2.setInt(4,30); 
				ps2.setDate(5, new java.sql.Date(sdate.getTime()));
				ps2.setDate(6, new java.sql.Date(edate.getTime()));
				ps2.setInt(7,div);
				ps2.setInt(8, cmpcode);
				ps2.setInt(9, depo_code);
			}
			
			rs= ps2.executeQuery();
			
			System.out.println(fyear+" "+div+" "+depo_code+" "+startVoucher+" "+endVoucher);
			voucherList = new ArrayList(); 
			int wno = 0;
			boolean first=true;
			double rgross = 0.00; 
			
		 
			 
			String inv=null;
			String remark="";
			 
			boolean cfirst=false; 
			while (rs.next())
			{
				System.out.println("JV MAI AAYA ANDAR AUR");
				 
				if(first)
				{
					wno=rs.getInt(2);
					first=false;
				}
				
				
				if(wno!=rs.getInt(2))
				{
					rcp= new RcpDto();
					rcp.setDash(1);
					rcp.setVamount(rgross);
					rcp.setVnart2(remark);
					
					voucherList.add(rcp);
		            rgross=0.00;
		            wno=rs.getInt(2);
				}
				
				
					rcp= new RcpDto();
					rcp.setVou_lo(rs.getString(1));
					rcp.setVou_no(rs.getInt(2));
					rcp.setVou_date(rs.getDate(3));
					rcp.setVamount(rs.getDouble(4));
					rcp.setVdbcr(rs.getString(5));
					rcp.setExp_code(rs.getString(6));
					if (rs.getInt(14)>0)
						rcp.setExp_code(rs.getString(13));
					rcp.setExp_name(rs.getString(7));
					rcp.setVnart2(rs.getString(8));
					rcp.setVnart1(rs.getString(9));
					rcp.setBill_no(rs.getString(15));
					rcp.setDash(0);
					remark=rs.getString(8);
					
					rgross+=rs.getDouble(11);
					
					voucherList.add(rcp);
				

			}
 
			
			if(!first)
			{
				rcp= new RcpDto();
				rcp.setDash(1);
				rcp.setVamount(rgross);
				rcp.setVnart2(remark);
				voucherList.add(rcp);
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
			System.out.println("-------------Exception in JvVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in JvVouDAO.Connection.close "+e);
			}
		}

		return voucherList;
	}	
	
	
////method to add record in led file (Journal voucher Transfer)	05/02/2015
	public int addLedJVTransfer(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement updps=null;
		RcpDto rcp=null;
		ArrayList updList=null;
		int size=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			

			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
			"vou_date,vbk_code,vgrp_code,vac_code,exp_code,vnart_1,vnart_2,vamount,vdbcr, " +
			"Created_by,Created_date,serialno,fin_year,mnth_code,vstat_cd,varea_cd,vreg_cd,vter_cd,vmsr_cd,sub_div,mkt_year,vou_yr) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			String updled="update ledfile set rcp_amt1=rcp_amt1+? where  div_code=? and vdepo_code=? "+
			" and vbook_cd1=? and vac_code=? and vou_no=? and vou_date=? and ifnull(del_tag,'')<>'D' ";
			
		    // 040+depo+01
			
			ledps=con.prepareStatement(led);
			updps=con.prepareStatement(updled);
			
			size = cList.size();
			System.out.println("SIZE "+size);
			for(int j=0;j<size;j++)
			{
				rcp = (RcpDto) cList.get(j);
				
				System.out.println("DIV "+rcp.getDiv_code()+" tempdiv "+rcp.getDoc_type()+" recno "+j);
				updList=rcp.getChqlist();
				
				
				ledps.setInt(1, 4);
				ledps.setInt(2, rcp.getVcmp_code()); // accounts depo code
				ledps.setInt(3, rcp.getVbook_cd());
				ledps.setInt(4, rcp.getVbook_cd1());
				ledps.setString(5, rcp.getVou_lo());
				ledps.setInt(6, rcp.getVou_no());
				ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps.setInt(8,rcp.getVbk_code()); // vbk_Code
				ledps.setInt(9,rcp.getVgrp_code()); // vgrp_Code
				ledps.setString(10, rcp.getVac_code());
				ledps.setString(11, rcp.getExp_code());
				ledps.setString(12, rcp.getVnart1());
				ledps.setString(13, rcp.getVnart2());
				ledps.setDouble(14, rcp.getVamount());
				ledps.setString(15, rcp.getVdbcr());
				ledps.setInt(16, rcp.getCreated_by());
				ledps.setDate(17, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(18, (j+1));
				ledps.setInt(19, rcp.getFin_year());
				ledps.setInt(20, rcp.getMnth_code());
				ledps.setInt(21, rcp.getVstat_cd());
				ledps.setInt(22, rcp.getVarea_cd());
				ledps.setInt(23, rcp.getVreg_cd());
				ledps.setInt(24, rcp.getVter_cd());
				ledps.setInt(25, rcp.getVmsr_cd());
				ledps.setInt(26, rcp.getDiv_code());
				ledps.setInt(27, rcp.getMkt_year());
				ledps.setInt(28, rcp.getVou_yr());
				
				i=ledps.executeUpdate();
				
				ledps.setInt(1, rcp.getDiv_code());
				ledps.setInt(2, rcp.getVdepo_code());
				ledps.setInt(3, rcp.getVbook_cd1());
				ledps.setInt(4, rcp.getVbook_cd());
				ledps.setString(5, rcp.getVou_lo());
				ledps.setInt(6, rcp.getVou_no());
				ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps.setInt(8,rcp.getVbk_code()); // vbk_Code
				ledps.setInt(9,rcp.getVgrp_code()); // vgrp_Code
				ledps.setString(10, rcp.getVac_code());
				ledps.setString(11, rcp.getExp_code());
				ledps.setString(12, rcp.getVnart1());
				ledps.setString(13, rcp.getVnart2());
				ledps.setDouble(14, rcp.getVamount());
				ledps.setString(15, rcp.getVdbcr());
				ledps.setInt(16, rcp.getCreated_by());
				ledps.setDate(17, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(18, (j+1));
				ledps.setInt(19, rcp.getFin_year());
				ledps.setInt(20, rcp.getMnth_code());
				ledps.setInt(21, rcp.getVstat_cd());
				ledps.setInt(22, rcp.getVarea_cd());
				ledps.setInt(23, rcp.getVreg_cd());
				ledps.setInt(24, rcp.getVter_cd());
				ledps.setInt(25, rcp.getVmsr_cd());
				ledps.setInt(26, rcp.getDiv_code());
				ledps.setInt(27, rcp.getMkt_year());
				ledps.setInt(28, rcp.getVou_yr());
				i=ledps.executeUpdate();
				
				if (rcp.getDiv_code()!=rcp.getDoc_type())  // contra record generated
				{
					ledps.setInt(1, 4);
					ledps.setInt(2, rcp.getVcmp_code()); // accounts depo code
					ledps.setInt(3, 30);
					ledps.setInt(4, 30);
					ledps.setString(5, rcp.getVou_lo());
					ledps.setInt(6, rcp.getVou_no());
					ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					ledps.setInt(8,rcp.getVbk_code()); // vbk_Code
					ledps.setInt(9,rcp.getVgrp_code()); // vgrp_Code
					ledps.setString(10, "4520000");
					ledps.setString(11, rcp.getExp_code());
					ledps.setString(12, rcp.getVnart1());
					ledps.setString(13, rcp.getVnart2());
					ledps.setDouble(14, rcp.getVamount());
					ledps.setString(15, rcp.getVdbcr());
					ledps.setInt(16, rcp.getCreated_by());
					ledps.setDate(17, new java.sql.Date(rcp.getCreated_date().getTime()));
					ledps.setInt(18, (j+1));
					ledps.setInt(19, rcp.getFin_year());
					ledps.setInt(20, rcp.getMnth_code());
					ledps.setInt(21, rcp.getVstat_cd());
					ledps.setInt(22, rcp.getVarea_cd());
					ledps.setInt(23, rcp.getVreg_cd());
					ledps.setInt(24, rcp.getVter_cd());
					ledps.setInt(25, rcp.getVmsr_cd());
					ledps.setInt(26, rcp.getDiv_code());
					ledps.setInt(27, rcp.getMkt_year());
					ledps.setInt(28, rcp.getVou_yr());
					i=ledps.executeUpdate();

				}
			}
				size=updList.size();

/*				for(int k=0;k<size;k++)
				{
					rcp = (RcpDto) updList.get(k);
					updps.setDouble(1, rcp.getVamount());
					// where clause
					//updps.setInt(2, rcp.getFin_year());
					updps.setInt(2, rcp.getDiv_code());
					updps.setInt(3, rcp.getVdepo_code()); // sales depo code
					updps.setInt(4, rcp.getVbook_cd());
					updps.setString(5, rcp.getVac_code());
					updps.setInt(6, rcp.getVou_no());
					updps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					i=updps.executeUpdate();
				}
*/				
				i = rcp.getVou_no();
	
      
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			updps.close();
			
		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLJvVouDAO.addLed " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(updps != null){updps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLJvVouDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
		
	
	}
