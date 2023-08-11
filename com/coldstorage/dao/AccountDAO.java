package com.coldstorage.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.coldstorage.dto.AreaDto;
import com.coldstorage.dto.InvFstDto;
import com.coldstorage.dto.PartyDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.dto.RegionDto;
import com.coldstorage.view.BaseClass;

public class AccountDAO {

 


	public List ledger(int year, int depo, int div, Date sdate, Date edate,	int code,int opt) 
	{
		List data = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		CallableStatement cs4 = null;
		RcpDto rcp = null;
		double balance = 0.00;
		double tot1 = 0.00;
		double tot2 = 0.00;
		
		try {
			con = ConnectionFactory.getConnection();

			cs4 =  con.prepareCall("{call CurrBalQuery(?,?)}");
			
			cs4.setInt(1,depo);
			cs4.setInt(2, year);
			cs4.executeUpdate();
			cs4.close();

			
/*			String query2 = " select vou_date,vou_no,a.vnart_1,if(opng<0,(opng*-1),opng),vdbcr,vbook_cd,a.rcp_no,a.rcp_date,mcode,gcode,a.curr,a.vnart_2 from  "+ 
			"(select  null vou_date,0 vou_no,'Opening Balance' vnart_1,ifnull(sum(opng),0.00) opng,if(sum(opng)<0,'CR','DR') vdbcr,0 vbook_cd, '' rcp_no, null rcp_date,mcode,gcode,sum(o.curr) curr,o.vnart_2 from "+ 
			"(select if(a.mopng_db='DR',a.mopng_bal,(a.mopng_bal*-1)) opng,a.mac_code mcode,0 gcode,a.curr_bal curr,'' vnart_2 "+
			"from  accountmaster as a where a.fin_year=?  and a.depo_code=?  and a.div_code=? and a.mac_code=? "+ 
			"union all "+
			"SELECT if(vdbcr='DR',sum(VAMOUNT),sum(vamount*-1) ) opng, ifnull(vac_code,0) mcode,0 gcode,0 curr,'' vnart_2 "+
			"FROM LEDfile WHERE FIN_YEAR=? AND VDEPO_CODE=? and div_code=? and vac_code=? "+
			"and vou_date < ? and ifnull(del_tag,'')<>'D') o "+
			" union all "+  
			" SELECT sinv_dt vou_date,sinv_no vou_no,concat('Transfer To Slip No ',transfer_no,' of A/c ',from_Account) VNART_1,net_amt vamount,'CR' vdbcr,41 vbook_cd,transfer_no rcp_no,sinv_dt rcp_date,sprt_cd mcode,0 gcode,0 curr,'' vnart_2 "+  
			"FROM invsnd WHERE FIN_YEAR<=? AND SDEPO_CODE=? and div_code=? AND SDOC_tYPE=41 and SPRT_CD=? "+ 
			" and SINV_DT between ? and ? and ifnull(del_tag,'')<>'D' "+
			"union all  "+
			"SELECT VOU_DATE,VOU_NO,ifnull(remark,'') VNART_1,VAMOUNT,ifnull(VDBCR,'DR') vdbcr,vbook_cd,rcp_no,rcp_date,vac_Code mcode,0 gcode,0 curr,'' vnart_2 "+ 
			"FROM LEDfile WHERE FIN_YEAR<=?  AND VDEPO_CODE=? and div_code=? and vac_code=? "+
			"and vou_date between ? and ? and ifnull(del_tag,'')<>'D') a order by vou_date,vdbcr desc,vou_no ";
*/
			
			System.out.println("opt ki value "+opt);
			
			String query2 = " select vou_date,vou_no,a.vnart_1,vamount,vdbcr,vbook_cd,a.rcp_no,a.rcp_date,mcode,gcode,a.curr,a.vnart_2 from  "+ 
			"( SELECT sinv_dt vou_date,sinv_no vou_no,concat('Transfer To Slip No ',transfer_no,' of A/c ',from_Account) VNART_1,net_amt vamount,'CR' vdbcr,41 vbook_cd,transfer_no rcp_no,sinv_dt rcp_date,sprt_cd mcode,0 gcode,0 curr,'' vnart_2 "+  
			" FROM invsnd WHERE FIN_YEAR=? AND SDEPO_CODE=? and div_code=? AND SDOC_tYPE=41 and SPRT_CD=? "+ 
			"  and ifnull(del_tag,'')<>'D' "+
			" union all  "+
			" SELECT VOU_DATE,VOU_NO,ifnull(remark,'') VNART_1,VAMOUNT,ifnull(VDBCR,'DR') vdbcr,vbook_cd,rcp_no,rcp_date,vac_Code mcode,0 gcode,0 curr,'' vnart_2 "+ 
			" FROM LEDfile WHERE FIN_YEAR=?  AND VDEPO_CODE=? and div_code=? and vac_code=? "+
			" and ifnull(del_tag,'')<>'D') a order by vou_date,vdbcr desc,vou_no ";

			
			if(opt==2)  // all parties Ledger 
			{
				query2 = " select vou_date,vou_no,a.vnart_1,if(opng<0,(opng*-1),opng),vdbcr,vbook_cd,chq_no,chq_date,mcode,gcode,a.curr,a.vnart_2,p.mac_name,p.mcity from  partyfst p,  "+ 
						"(select  null vou_date,0 vou_no,'Opening Balance' vnart_1,ifnull(sum(opng),0.00) opng,if(sum(opng)<0,'CR','DR') vdbcr,0 vbook_cd, '' chq_no, null chq_date,mcode,gcode,sum(o.curr) curr,o.vnart_2 from "+ 
						"(select if(a.mopng_db='DR',a.mopng_bal,(a.mopng_bal*-1)) opng,a.mac_code mcode,0 gcode,a.curr_bal curr,'' vnart_2 "+
						"from  accountmaster as a where a.fin_year=? and a.div_code=? and a.depo_code=?  "+ 
						"union all "+
						"SELECT if(vdbcr='DR',sum(VAMOUNT),sum(vamount*-1) ) opng, ifnull(vac_code,0) mcode,0 gcode,0 curr,'' vnart_2 "+
						"FROM LEDfile WHERE FIN_YEAR=?  AND VDEPO_CODE=? and div_code=?  "+
						"and vou_date < ? and ifnull(del_tag,'')<>'D') o "+
						"union all  "+
						"SELECT VOU_DATE,VOU_NO,VNART_1,VAMOUNT,ifnull(VDBCR,'DR') vdbcr,vbook_cd,chq_no,chq_date,vac_Code mcode,0 gcode,0 curr,'' vnart_2 "+ 
						"FROM LEDfile WHERE FIN_YEAR<=?  AND VDEPO_CODE=? and div_code=?  "+
						"and vou_date between ? and ? and ifnull(del_tag,'')<>'D') a " +
						"where  p.depo_code=? and p.div_code=? and p.mac_code=a.mcode order by vou_date,vdbcr desc,vou_no ";
			}
				 
			
			data = new ArrayList();

			ps2 = con.prepareStatement(query2);
			if(opt==1)
			{
				ps2.setInt(1, year);
				ps2.setInt(2, depo);
				ps2.setInt(3, div);
				ps2.setInt(4, code);
				ps2.setInt(5, year);
				ps2.setInt(6, depo);
				ps2.setInt(7, div);
				ps2.setInt(8, code);
				
/*				ps2.setInt(1, year);
				ps2.setInt(2, depo);
				ps2.setInt(3, div);
				ps2.setInt(4, code);
				ps2.setInt(5, year);
				ps2.setInt(6, depo);
				ps2.setInt(7, div);
				ps2.setInt(8, code);
				ps2.setDate(9, new java.sql.Date(sdate.getTime()));
				ps2.setInt(10, ++year);
				ps2.setInt(11, depo);
				ps2.setInt(12, div);
				ps2.setInt(13, code);
				ps2.setDate(14, new java.sql.Date(sdate.getTime()));
				ps2.setDate(15, new java.sql.Date(edate.getTime()));
				ps2.setInt(16, ++year);
				ps2.setInt(17, depo);
				ps2.setInt(18, div);
				ps2.setInt(19, code);
				ps2.setDate(20, new java.sql.Date(sdate.getTime()));
				ps2.setDate(21, new java.sql.Date(edate.getTime()));
*/
			}
			else if(opt==2)  // all parties
			{
				ps2.setInt(1, year);
				ps2.setInt(2, depo);
				ps2.setInt(3, div);
				ps2.setInt(4, year);
				ps2.setInt(5, depo);
				ps2.setInt(6, div);
				ps2.setDate(7, new java.sql.Date(sdate.getTime()));
				ps2.setInt(8, ++year);
				ps2.setInt(9, depo);
				ps2.setInt(10, div);
				ps2.setDate(11, new java.sql.Date(sdate.getTime()));
				ps2.setDate(12, new java.sql.Date(edate.getTime()));
				ps2.setInt(13, depo);
				ps2.setInt(14, div);

			}
			rs = ps2.executeQuery();
			
			boolean first=true;
			int wcode=0;
			boolean due=false;
			while (rs.next())
			{
				 

				if(first)
				{
					wcode=rs.getInt(9);
					first=false;

				}
				
				
				if(wcode!=rs.getInt(9))
				{
					  if((tot1+tot2)>0 )
					  {
						rcp = new RcpDto();
						rcp.setVou_lo(" ");
						rcp.setVnart1("Total : ");
						rcp.setVamount(tot1);
						rcp.setBill_amt(tot2);
						rcp.setAdvance(tot1 - tot2);
						if ((tot1-tot2) > 0)
							rcp.setVdbcr("DR");
						else
							rcp.setVdbcr("CR");
						rcp.setDash(2);
						data.add(rcp);
					  }
					 
					balance=0.00;
					wcode=rs.getInt(9);
					tot1=0.00;
					tot2=0.00;
				}
				
				
				
				if((rs.getInt(2)>0 || rs.getDouble(4)>0) )
				{
					
					rcp = new RcpDto();
					rcp.setDash(0);
					rcp.setVou_date(rs.getDate(1));
					rcp.setVou_no(rs.getInt(2));

					rcp.setRcp_no(rs.getString(7));
					rcp.setVou_date(rs.getDate(8));
					
					System.out.println(rs.getString(7)+" type "+rs.getInt(6));
					rcp.setVac_code(rs.getString(9));
					if (rs.getInt(6) == 40)
					{
						//rcp.setVou_lo("SAL/"+ String.format("%05d", rs.getInt(2)));
						rcp.setVou_lo("OUT");
						rcp.setVnart1(rs.getString(3));
					} 
					else if (rs.getInt(6) ==41)
					{
						rcp.setVou_lo("TRF");
						rcp.setVnart1(rs.getString(3));
					} 
					else if (rs.getInt(6) ==60)
					{
						rcp.setVou_lo("INW");
						rcp.setVnart1(rs.getString(3));
					} 
					else if (rs.getInt(6)>= 20 && rs.getInt(6)< 30)
					{
						rcp.setVou_lo("BANK");
						rcp.setVnart1(rs.getString(3));
					}
					else if (rs.getInt(6) == 10)
					{
						rcp.setVou_lo("CASH");
						rcp.setVnart1(rs.getString(3));
					}
					else 
					{
						rcp.setVou_lo("");
						rcp.setVnart1(rs.getString(3));
						rcp.setDash(1);
					}



					if (rs.getString(5).equals("DR")) 
					{
						rcp.setVamount(rs.getDouble(4));
						rcp.setBill_amt(0.00);
						balance += (rs.getDouble(4));
						tot1 += rs.getDouble(4);

					}
					if (rs.getString(5).equals("CR")) {
						rcp.setVamount(0.00);
						rcp.setBill_amt(rs.getDouble(4));
						balance -= (rs.getDouble(4));
						tot2 += rs.getDouble(4);
					}

					if (balance > 0)
						rcp.setVdbcr("DR");
					else
						rcp.setVdbcr("CR");
					rcp.setAdvance(balance<0?(balance*-1):balance);
					rcp.setVnart2(rs.getString(12));
					data.add(rcp);
				}

			}
			
			  if((tot1+tot2)>0 )
			  {
				rcp = new RcpDto();
				rcp.setVou_lo(" ");
				rcp.setVnart1("Total : ");
				rcp.setVamount(tot1);
				rcp.setBill_amt(tot2);
				rcp.setAdvance(tot1 - tot2);
				if ((tot1-tot2) > 0)
					rcp.setVdbcr("DR");
				else
					rcp.setVdbcr("CR");
				rcp.setDash(2);
				data.add(rcp);
			  }

			

			rs.close();
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO Ledger " + ee);
			ee.printStackTrace();
		}

		finally {
			try {
				if (rs != null) {rs.close();}
				if (ps2 != null) {ps2.close();}
				if (con != null) {con.close();}
			} catch (SQLException ee) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "+ ee);
			}
		}

		return data;
	}

	
	public List getTrialBalance(int year, int depo, int div, Date sdate, Date edate) 
	{
		List data = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		int i=0;
		RcpDto rcp = null;
		try {
			con = ConnectionFactory.getConnection();


			String query2 = " select pcode,p.mac_name,round(vamt,2) vamt,vdbcr from partyfst p, "+
			"(select pcode,if(sum(vamt)<0,sum(vamt)*-1,sum(vamt)) vamt ,if(sum(vamt)<0,'CR','DR') vdbcr from  "+
			"(select mac_Code pcode,if(mopng_db='DR',mopng_bal,(mopng_bal)*-1) vamt from partyacc where fin_year=? "+
			"and div_code=? and depo_code=? and ifnull(del_tag,'')<>'D'  "+
			"union all "+
			"select vac_Code pcode,sum(vamount) vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and vdbcr='DR' and ifnull(del_tag,'')<>'D' group by vac_code "+
			"union all "+
			"select vac_Code pcode,(sum(vamount)*-1) vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and vdbcr='CR' and ifnull(del_tag,'')<>'D' group by vac_code "+
			"union all "+
			"select vbk_Code pcode,sum(vamount) vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and  vbook_cd between 60 and 69 and vdbcr='CR' and ifnull(del_tag,'')<>'D' group by vbk_code "+
			"union all "+
			"select vbk_Code pcode,sum(vamount)*-1 vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and  vbook_cd between 90 and 99 and vdbcr='DR' and ifnull(del_tag,'')<>'D' group by vbk_code "+
			"union all "+
			"select vbk_Code pcode,sum(vamount)*-1 vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and  vbook_cd between 40 and 49 and vdbcr='DR' and ifnull(del_tag,'')<>'D' group by vbk_code "+
			"union all "+
			"select vbk_Code pcode,sum(vamount) vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and  vbook_cd between 90 and 99 and vdbcr='CR' and ifnull(del_tag,'')<>'D' group by vbk_code "+
			"union all "+
			"select vbk_Code pcode,sum(vamount) vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and  vbook_cd between 70 and 79 and vdbcr='CR' and ifnull(del_tag,'')<>'D' group by vbk_code "+
			"union all "+
			"select vbk_Code pcode,sum(vamount)*-1 vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and  vbook_cd between 80 and 89 and vdbcr='DR' and ifnull(del_tag,'')<>'D' group by vbk_code "+
			"union all "+
			"select vbk_Code pcode,sum(vamount)*-1 vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and (vbook_cd=10 or vbook_cd between 20 and 26) and vdbcr='DR' and ifnull(del_tag,'')<>'D' group by vbk_code "+
			"union all "+
			"select vbk_Code pcode,(sum(vamount)) vamt from ledfile where fin_year=? and div_code=? and vdepo_code=? "+
			"and vou_date between ? and ? and (vbook_cd=10 or vbook_cd between 20 and 26) and vdbcr='CR' and ifnull(del_tag,'')<>'D' group by vbk_code) a "+
			"group by pcode) t where t.pcode=p.mac_code  and p.div_code=? and p.depo_code=? and  t.vamt<>0 order by p.mgrp_code,pcode "; 
		 
			
			String partyupdateAll="update partyacc set curr_bal=? where fin_year=? and div_code=? and depo_code=?  ";
			String partyupdate="update partyacc set curr_bal=?,curr_db=? where fin_year=? and div_code=? and depo_code=? and mac_code=? ";

			data = new ArrayList();

			// update all mcurrbal with 0.00
			ps3 = con.prepareStatement(partyupdateAll);
			ps3.setDouble(1, 0.00);
			ps3.setInt(2, year);
			ps3.setInt(3, div);
			ps3.setInt(4, depo);
			i=ps3.executeUpdate();
			ps3.close();
			
			ps4 = con.prepareStatement(partyupdate);
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			ps2.setInt(4, year);
			ps2.setInt(5, div);
			ps2.setInt(6, depo);
			ps2.setDate(7, new java.sql.Date(sdate.getTime()));
			ps2.setDate(8, new java.sql.Date(edate.getTime()));
			ps2.setInt(9, year);
			ps2.setInt(10, div);
			ps2.setInt(11, depo);
			ps2.setDate(12, new java.sql.Date(sdate.getTime()));
			ps2.setDate(13, new java.sql.Date(edate.getTime()));
			ps2.setInt(14, year);
			ps2.setInt(15, div);
			ps2.setInt(16, depo);
			ps2.setDate(17, new java.sql.Date(sdate.getTime()));
			ps2.setDate(18, new java.sql.Date(edate.getTime()));
			ps2.setInt(19, year);
			ps2.setInt(20, div);
			ps2.setInt(21, depo);
			ps2.setDate(22, new java.sql.Date(sdate.getTime()));
			ps2.setDate(23, new java.sql.Date(edate.getTime()));
			ps2.setInt(24, year);
			ps2.setInt(25, div);
			ps2.setInt(26, depo);
			ps2.setDate(27, new java.sql.Date(sdate.getTime()));
			ps2.setDate(28, new java.sql.Date(edate.getTime()));
			ps2.setInt(29, year);
			ps2.setInt(30, div);
			ps2.setInt(31, depo);
			ps2.setDate(32, new java.sql.Date(sdate.getTime()));
			ps2.setDate(33, new java.sql.Date(edate.getTime()));
			ps2.setInt(34, year);
			ps2.setInt(35, div);
			ps2.setInt(36, depo);
			ps2.setDate(37, new java.sql.Date(sdate.getTime()));
			ps2.setDate(38, new java.sql.Date(edate.getTime()));
			ps2.setInt(39, year);
			ps2.setInt(40, div);
			ps2.setInt(41, depo);
			ps2.setDate(42, new java.sql.Date(sdate.getTime()));
			ps2.setDate(43, new java.sql.Date(edate.getTime()));
			ps2.setInt(44, year);
			ps2.setInt(45, div);
			ps2.setInt(46, depo);
			ps2.setDate(47, new java.sql.Date(sdate.getTime()));
			ps2.setDate(48, new java.sql.Date(edate.getTime()));
			ps2.setInt(49, year);
			ps2.setInt(50, div);
			ps2.setInt(51, depo);
			ps2.setDate(52, new java.sql.Date(sdate.getTime()));
			ps2.setDate(53, new java.sql.Date(edate.getTime()));
			ps2.setInt(54, div);
			ps2.setInt(55, depo);
			rs = ps2.executeQuery();

			double ctot=0.00;
			double dtot=0.00;
			
			
			while (rs.next()) {

				rcp = new RcpDto();

				rcp.setVac_code(rs.getString(1));
				rcp.setParty_name(rs.getString(2));
				
				if(rs.getString(4).equals("CR"))
				{
					rcp.setCr_amt(rs.getDouble(3));
					ctot+=rs.getDouble(3);
				}
				else
				{
					rcp.setVamount(rs.getDouble(3));
					dtot+=rs.getDouble(3);
				}
				    rcp.setDash(0);
				    data.add(rcp);
				    
				    
				// update partyacc mcurrbal and mcurr_db    
			    ps4.setDouble(1, rs.getDouble(3));
			    ps4.setString(2, rs.getString(4));
			    //where clause 
			    ps4.setInt(3, year);
			    ps4.setInt(4, div);
			    ps4.setInt(5, depo);
			    ps4.setString(6, rs.getString(1));// mac_code
			    i=ps4.executeUpdate();
				    
				    
				

			}
			rcp = new RcpDto();
			rcp.setParty_name("Total : ");
			rcp.setVamount(dtot);
			rcp.setCr_amt(ctot);
			rcp.setDash(1);
			data.add(rcp);

			rs.close();
			ps2.close();
			ps4.close();
			
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO Trial Balance " + ee);
			ee.printStackTrace();
		}

		finally {
			try {
				if (rs != null) {rs.close();}
				if (ps2 != null) {ps2.close();}
				if (ps3 != null) {ps3.close();}
				if (ps4 != null) {ps4.close();}
				if (con != null) {con.close();}
			} catch (SQLException ee) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "+ ee);
			}
		}

		return data;
	}
	

	public List getBalanceSheet(int year, int depo, int div, Date sdate, Date edate) 
	{
		List data = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		int i=0;
		RcpDto rcp = null;
		try {
			con = ConnectionFactory.getConnection();

//			year=2019;
			
			String query2 = "select a.mgrp_code,h.gp_name,h.sub_code,p.mac_code,if(a.mgrp_code=70,'SUNDRY DEBTORS',if(a.mgrp_Code=50,'SUNDRY CREDITORS',p.mac_name)) name," +
			"if(a.curr_db='DR',a.curr_bal,0.00) dr,if(a.curr_db='CR',a.curr_bal,0.00) cr,a.curr_db,a.dash,h.head_name from partyfst p,acchead h, "+ 
			"(select 1 dash,mgrp_code,mac_code,curr_bal,curr_db from partyacc "+
			"where fin_year=? and div_code=? and depo_code=? and mgrp_code<19 and curr_bal>0 "+ 
			"union all  "+
			"select 2 dash,mgrp_code,mac_code,curr_bal,curr_db from partyacc "+
			"where fin_year=? and div_code=? and depo_code=? and mgrp_code between 20 and 25 and curr_bal>0 "+ 
			"union all  "+
			"select 3 dash, mgrp_code,mac_code,curr_bal,curr_db from partyacc "+
			"where fin_year=? and div_code=? and depo_code=? and mgrp_code>25 and mgrp_code not in (50,70) and curr_bal>0 "+ 
			"union all "+
			"select 3 dash,50 mgrp_code,mac_code,sum(curr_bal) curr_bal, curr_db from partyacc "+
			"where fin_year=? and div_code=? and depo_code=? and mgrp_code in (50,70) and curr_db='CR' and curr_bal>0 "+ 
			"union all "+
			"select 3 dash,70 mgrp_code,mac_code,sum(curr_bal) curr_bal, curr_db from partyacc "+
			"where fin_year=? and div_code=? and depo_code=?  and mgrp_code in (50,70) and curr_db='DR' and curr_bal>0  ) a "+
			"where p.div_code=? and p.depo_code=?  and p.mac_code=a.mac_code "+
			"and h.gp_code=a.mgrp_Code order by a.dash,a.mgrp_code,a.mac_code ";
			

			data = new ArrayList();

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			ps2.setInt(4, year);
			ps2.setInt(5, div);
			ps2.setInt(6, depo);
			ps2.setInt(7, year);
			ps2.setInt(8, div);
			ps2.setInt(9, depo);
			ps2.setInt(10, year);
			ps2.setInt(11, div);
			ps2.setInt(12, depo);
			ps2.setInt(13, year);
			ps2.setInt(14, div);
			ps2.setInt(15, depo);
			ps2.setInt(16, div);
			ps2.setInt(17, depo);
			rs = ps2.executeQuery();

			double ctot=0.00;
			double dtot=0.00;
			
			int dash=0;
			int subcode=0;
			double scamt=0.00;
			double sdamt=0.00;
			int mgrp=0;
			double profit=0.00;
			boolean first=true;
			String grpname=null;
			String headname=null;
			String wdbcr=null;
			while (rs.next()) {

				
				if(first)
				{
					dash=rs.getInt(9);
					subcode=rs.getInt(3);
					mgrp=rs.getInt(1);
					grpname=rs.getString(2);
					headname=rs.getString(10);
					first=false;
				}
				if(dash!=rs.getInt(9))
				{
					// print profit
					
					rcp = new RcpDto();
					rcp.setVac_code("");
					profit=ctot-dtot;
					if(profit<0)
					{
						rcp.setParty_name(dash==1?"Gross Profit":"Net Profit");
						rcp.setCr_amt(profit*-1);
						ctot+=(profit*-1);
					}
					else
					{
						rcp.setBank_name(dash==1?"Gross Loss":"Net Loss");
						rcp.setVamount(profit);
						dtot+=profit;
					}
					rcp.setDash(0);
					data.add(rcp);
					
					rcp = new RcpDto();
					rcp.setVac_code("");
					rcp.setParty_name("Total");
					rcp.setCr_amt(ctot);
					rcp.setBank_name("Total");
					rcp.setVamount(dtot);
					rcp.setDash(1);
					data.add(rcp);


					
					ctot=0.00;
					dtot=0.00;

					if(dash==1)
					{
						rcp = new RcpDto();
						rcp.setVac_code("");
						if(profit<0)
						{
							rcp.setBank_name("Gross Profit B/d from Trading");
							rcp.setVamount(profit*-1);
							dtot+=(profit*-1);
						}
						else
						{
							rcp.setParty_name("Gross Loss B/d from Trading");
							rcp.setCr_amt(profit);
							ctot+=profit;
						}
						rcp.setDash(0);
						data.add(rcp);
					}
					
					dash=rs.getInt(9);
					subcode=rs.getInt(3);
					mgrp=rs.getInt(1);
					grpname=rs.getString(2);
					headname=rs.getString(10);

					
				}
				
				if(dash==3 && subcode==1 && mgrp!=rs.getInt(1))
				{
					rcp = new RcpDto();
					rcp.setVac_code(rs.getString(1));
//					System.out.println("mgrp "+mgrp+"grpname "+grpname+" vdbcr "+wdbcr+ "amt "+scamt+" damt "+sdamt);
					if(wdbcr.equals("CR"))
					{
						rcp.setBank_acno(headname);
						rcp.setParty_name(grpname);
						rcp.setCr_amt(sdamt);
						ctot+=sdamt;
					}
					else
					{
						rcp.setBank_ifsc(headname);
						rcp.setBank_name(grpname);
						rcp.setVamount(scamt);
						dtot+=scamt;
					}
					rcp.setDoc_type(1); // subcode 
					scamt=0.00;
					sdamt=0.00;
					subcode=rs.getInt(3);
					wdbcr=rs.getString(8);
					mgrp=rs.getInt(1);
					grpname=rs.getString(2);
					headname=rs.getString(10);

					rcp.setDash(3);
					data.add(rcp);
					
				}
				if(subcode==1 && mgrp!=rs.getInt(1))
				{
					rcp = new RcpDto();
					rcp.setVac_code(rs.getString(1));

					if(wdbcr.equals("DR"))
					{
						rcp.setParty_name(grpname);
						rcp.setCr_amt(scamt);
						ctot+=scamt;
					}
					else
					{
						rcp.setBank_name(grpname);
						rcp.setVamount(sdamt);
						dtot+=sdamt;
					}
					scamt=0.00;
					sdamt=0.00;
					subcode=rs.getInt(3);
					wdbcr=rs.getString(8);
					mgrp=rs.getInt(1);
					grpname=rs.getString(2);
					headname=rs.getString(10);

					profit=0.00;
					rcp.setDash(0);
					data.add(rcp);
					
				}
				else if(dash==3 && rs.getInt(3)==0)
				{
					rcp = new RcpDto();
					rcp.setVac_code(rs.getString(1));
					
//					System.out.println(" SUBCODE IS 0 mgrp "+mgrp+"grpname "+rs.getString(5)+" vdbcr "+rs.getString(8)+ "amt "+rs.getDouble(7)+" damt "+rs.getDouble(6));
					if(rs.getString(8).equals("CR"))
					{
						rcp.setBank_acno(rs.getString(10));
						rcp.setParty_name(rs.getString(5));
						rcp.setCr_amt(rs.getDouble(7)+(profit*-1));
						ctot+=rs.getDouble(7)+(profit*-1);
					}
					else
					{
						rcp.setBank_ifsc(rs.getString(10));
						rcp.setBank_name(rs.getString(5));
						rcp.setVamount(rs.getDouble(6)+(profit));
						dtot+=rs.getDouble(6)+(profit);
					}
					profit=0.00;
					rcp.setDash(3);
					if(rs.getInt(1)==26)
						rcp.setDoc_type(1);
						
					data.add(rcp);
					subcode=rs.getInt(3);
				}
				else if(dash<3 && rs.getInt(3)==0)
				{
					rcp = new RcpDto();
					rcp.setVac_code(rs.getString(1));
					

					if(rs.getString(8).equals("DR"))
					{
						rcp.setParty_name(rs.getString(5));
						rcp.setCr_amt(rs.getDouble(6));
						ctot+=rs.getDouble(6);
					}
					else
					{
						rcp.setBank_name(rs.getString(5));
						rcp.setVamount(rs.getDouble(7));
						dtot+=rs.getDouble(7);
					}
					rcp.setDash(0);
					data.add(rcp);
					wdbcr=rs.getString(8);
					subcode=rs.getInt(3);
				}
				else // subcode==1
				{
					if(rs.getString(8).equals("DR"))
					{
						scamt+=rs.getDouble(6);
					}
					else
					{
						sdamt+=rs.getDouble(7);
					}

					if(mgrp==52)
					{
						System.out.println("value * "+rs.getDouble(7)+" drcr "+rs.getString(8)+" sdamt "+sdamt);
					}

					wdbcr=rs.getString(8);
					subcode=rs.getInt(3);
					mgrp=rs.getInt(1);
					grpname=rs.getString(2);
					headname=rs.getString(10);

				}
				    
			}

		    
		    
			rcp = new RcpDto();
			rcp.setVac_code("");
			rcp.setParty_name("Total");
			rcp.setCr_amt(ctot);
			rcp.setBank_name("Total");
			rcp.setVamount(dtot);
			rcp.setDash(1);
			data.add(rcp);

			rs.close();
			ps2.close();
			
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO Balance Sheet " + ee);
			ee.printStackTrace();
		}

		finally {
			try {
				if (rs != null) {rs.close();}
				if (ps2 != null) {ps2.close();}
				if (con != null) {con.close();}
			} catch (SQLException ee) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "+ ee);
			}
		}

		return data;
	}
	

	
	public List getSchedule(int year, int depo, int div, Date sdate, Date edate) 
	{
		List data = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		int i=0;
		RcpDto rcp = null;
		try {
			con = ConnectionFactory.getConnection();

//			year=2019;
			
			String query2 = "select a.mgrp_code,h.head_name,p.mac_code,p.mac_name, "+
			"if(a.curr_db='DR',a.curr_bal,0.00) dr,if(a.curr_db='CR',a.curr_bal,0.00) cr,a.curr_db from partyfst p,acchead h,partyacc a "+
			"where a.fin_year=? and a.div_code=? and a.depo_code=? and a.mgrp_code>25  and a.curr_bal>0  "+
			"and p.div_code=? and p.depo_code=?  and p.mac_code=a.mac_code  "+
			"and h.gp_code=a.mgrp_Code and a.mgrp_code=p.mgrp_code " +
			"and (h.sub_code=1 or h.gp_code=26) and h.head_name is not null order by a.mgrp_code,a.mac_code ";
			

			data = new ArrayList();

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			ps2.setInt(4, div);
			ps2.setInt(5, depo);
			rs = ps2.executeQuery();

			double ctot=0.00;
			double dtot=0.00;
			
			int mgrp=0;
			boolean first=true;
			String headname=null;
			while (rs.next()) 
			{
				if(first)
				{
					mgrp=rs.getInt(1);
					headname=rs.getString(2);
					first=false;
				}
				
				if(mgrp!=rs.getInt(1))
				{
					rcp = new RcpDto();
					rcp.setVac_code("");
					rcp.setParty_name("Total");
					rcp.setCr_amt(dtot);
					rcp.setVamount(ctot);
					
					mgrp=rs.getInt(1);
					headname=rs.getString(2);
					ctot=0.00;
					dtot=0.00;
					rcp.setDash(1);
					data.add(rcp);
					
				}
				
				rcp = new RcpDto();
				rcp.setVac_code(rs.getString(3));
				rcp.setBank_name(headname);
				rcp.setParty_name(rs.getString(4));
				rcp.setCr_amt(rs.getDouble(5));
				rcp.setVamount(rs.getDouble(6));
				rcp.setDash(0);
				dtot+=rs.getDouble(5);
				ctot+=rs.getDouble(6);
				data.add(rcp);

				    
			}

		    
		    
			rcp = new RcpDto();
			rcp.setVac_code("");
			rcp.setParty_name("Total");
			rcp.setCr_amt(dtot);
			rcp.setVamount(ctot);
			rcp.setDash(1);
			data.add(rcp);

			rs.close();
			ps2.close();
			
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO Schedule " + ee);
			ee.printStackTrace();
		}

		finally {
			try {
				if (rs != null) {rs.close();}
				if (ps2 != null) {ps2.close();}
				if (con != null) {con.close();}
			} catch (SQLException ee) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "+ ee);
			}
		}

		return data;
	}
	

	public List getOutstanding(int year, int depo, int div, Date sdate, Date edate,	int code,int opt) 
	{
		List data = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;


		PreparedStatement ps1 = null;
		ResultSet rs1 = null;

		int nyear=year+1;

		RcpDto rcp = null;
		double tot1 = 0.00;
		double gtot1 = 0.00;
		try {
			con = ConnectionFactory.getConnection();

			// and (vbook_cd=17 or vbook_cd between 40 and 49)
			
			String query2 = " select vou_no,vou_date,vamount vamt,0 outamt,vac_code,vbook_cd,vnart_1,(to_days(curdate())-to_days(vou_date)) duedays,vnart_2,vbook_cd,IFNULL(vou_lo,'') from ledfile "+
			"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=17 and vdbcr='DR'  " +
			"and vac_code=? and vou_date <= ? and ifnull(del_tag,'')<>'D' "+
			"union all "+
			" select vou_no,vou_date,vamount vamt,0 outamt,vac_code,vbook_cd,vnart_1,(to_days(curdate())-to_days(vou_date)) duedays,vnart_2,vbook_cd,IFNULL(vou_lo,'') from ledfile "+
			"where fin_year<=? and div_code=? and vdepo_code=? and (vbook_cd=10 or vbook_cd=30 or vbook_cd between 40 and 49 or vbook_cd between 20 and 26) and vdbcr='DR'  " +
			"and vac_code=? and vou_date between ? and ? and ifnull(del_tag,'')<>'D' order by vac_code,vou_date,vou_no ";

			if(opt==3) // region wise
			{
				query2 = " select l.* from (select l.vou_no,l.vou_date,l.vamount vamt,0 outamt,l.vac_code,l.vbook_cd,l.vnart_1,(to_days(curdate())-to_days(l.vou_date)) duedays,l.vnart_2,l.vbook_cd vbk,IFNULL(l.vou_lo,'') from ledfile l "+
				"where l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd=17  and l.vdbcr='DR'  " +
				"and vreg_cd=? and l.vou_date <= ?  and ifnull(l.del_tag,'')<>'D'  "+
				"union all "+
				" select l.vou_no,l.vou_date,l.vamount vamt,0 outamt,l.vac_code,l.vbook_cd,l.vnart_1,(to_days(curdate())-to_days(l.vou_date)) duedays,l.vnart_2,l.vbook_cd vbk,IFNULL(l.vou_lo,'') from ledfile l "+
				"where l.fin_year<=? and l.div_code=? and l.vdepo_code=? and (l.vbook_cd=10 or l.vbook_cd=30 or l.vbook_cd between 40 and 49 or l.vbook_cd between 20 and 26 ) and l.vdbcr='DR'  " +
				"and vreg_cd=? and l.vou_date between ? and ?  and ifnull(l.del_tag,'')<>'D') l,partyfst p  " +
				"where p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code order by p.mcity,p.mac_name,l.vac_code,l.vou_date,l.vou_no ";

			}
			else if(opt==4) // dist wise
			{
				query2 = " select l.* from (select l.vou_no,l.vou_date,l.vamount vamt,0 outamt,l.vac_code,l.vbook_cd,l.vnart_1,(to_days(curdate())-to_days(l.vou_date)) duedays,l.vnart_2,l.vbook_cd vbk,IFNULL(l.vou_lo,''),l.vdist_cd from ledfile l "+
				"where l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd=17  and l.vdbcr='DR'  " +
				"and vdist_cd=? and l.vou_date <= ?  and ifnull(l.del_tag,'')<>'D'  "+
				"union all "+
				" select l.vou_no,l.vou_date,l.vamount vamt,0 outamt,l.vac_code,l.vbook_cd,l.vnart_1,(to_days(curdate())-to_days(l.vou_date)) duedays,l.vnart_2,l.vbook_cd vbk,IFNULL(l.vou_lo,''),l.vdist_cd from ledfile l "+
				"where l.fin_year<=? and l.div_code=? and l.vdepo_code=? and (l.vbook_cd=10 or l.vbook_cd=30 or l.vbook_cd between 40 and 49 or l.vbook_cd between 20 and 26 ) and l.vdbcr='DR'  " +
				"and vdist_cd=? and l.vou_date between ? and ?  and ifnull(l.del_tag,'')<>'D') l, partyfst p " +
				"where p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code order by l.vdist_cd,p.mcity,p.mac_name,l.vac_code,l.vou_date,l.vou_no ";

			}
			else if (opt==2)
			{
				query2 = "select l.* from (select 0 vou_no,'2019-04-01' vou_date,mopng_bal vamt,0 outamt,mac_code vac_code,17  vbk,'opening' vnart_1,0  duedays,'' vnart_2,17 vbook_cd,'' vou_lo,0 vdist_cd from partyacc p "+ 
				" where fin_year=? and div_code=? and depo_code=?   and mopng_db='DR' "+   
				" and mgrp_code in (45,46,51,70) and ifnull(del_tag,'')<>'D'  "+
				"union all "+
				" select l.vou_no,l.vou_date,l.vamount vamt,0 outamt,l.vac_code,l.vbook_cd,l.vnart_1,(to_days(curdate())-to_days(l.vou_date)) duedays,l.vnart_2,l.vbook_cd vbk,IFNULL(l.vou_lo,''),l.vdist_cd from ledfile l "+
				"where l.fin_year<=? and l.div_code=? and l.vdepo_code=? and (l.vbook_cd=10 or l.vbook_cd=30 or l.vbook_cd between 40 and 49 or l.vbook_cd between 20 and 26 ) and l.vdbcr='DR'  " +
				"and l.vou_date between ? and ? and l.vgrp_code in (45,46,51,70) and ifnull(l.del_tag,'')<>'D') l,partyfst p " +
				"where p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code order by l.vdist_cd,p.mcity,p.mac_name,l.vac_code,l.vou_date,l.vou_no ";
			}

			
			
			String query1="select sum(outamt),mcode from "+ 
			"(select ifnull(round(sum(mopng_bal),2),0) outamt,ifnull(mac_code,0) mcode from partyacc "+  
			"where fin_year=? and div_code=? and depo_code=?  and mac_code=? "+  
			"and  mopng_db='CR' and ifnull(del_tag,'')<>'D'  "+
			"union all "+
			"select round(sum(vamount),2) outamt,vac_code mcode from ledfile "+  
			"where fin_year<=? and div_code=? and vdepo_code=?  and vbook_cd<>17 and vac_code=? "+  
			"and vou_date between ? and ? and  vdbcr='CR' and ifnull(del_tag,'')<>'D') a group by mcode order by mcode desc ";
			

			data = new ArrayList();

			ps1 = con.prepareStatement(query1);
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);

			if(opt==2)
			{
				//ps2.setDate(4, new java.sql.Date(sdate.getTime()));
				ps2.setInt(4, nyear);
				ps2.setInt(5, div);
				ps2.setInt(6, depo);
				ps2.setDate(7, new java.sql.Date(sdate.getTime()));
				ps2.setDate(8, new java.sql.Date(edate.getTime()));
				ps2.setInt(9, div);
				ps2.setInt(10, depo);
			}
			else if(opt==3 || opt==4)
			{
				ps2.setInt(4, code);
				ps2.setDate(5, new java.sql.Date(sdate.getTime()));
				ps2.setInt(6, nyear);
				ps2.setInt(7, div);
				ps2.setInt(8, depo);
				ps2.setInt(9, code);
				ps2.setDate(10, new java.sql.Date(sdate.getTime()));
				ps2.setDate(11, new java.sql.Date(edate.getTime()));
				ps2.setInt(12, div);
				ps2.setInt(13, depo);
			}
			else
			{
				ps2.setInt(4, code);
				ps2.setDate(5, new java.sql.Date(sdate.getTime()));
				ps2.setInt(6, nyear);
				ps2.setInt(7, div);
				ps2.setInt(8, depo);
				ps2.setInt(9, code);
				ps2.setDate(10, new java.sql.Date(sdate.getTime()));
				ps2.setDate(11, new java.sql.Date(edate.getTime()));


			}
			
			rs = ps2.executeQuery();
			
			double outamt=0.00;
			int wcode=0;
			boolean print=false;
			boolean first=true;
			int bkcd=0;
			while (rs.next())
			{
				 
				
				if(wcode!=rs.getInt(5))
				{
					outamt=0.00;

					ps1.setInt(1, year);
					ps1.setInt(2, div);
					ps1.setInt(3, depo);
					ps1.setInt(4, rs.getInt(5));
					ps1.setInt(5, nyear);
					ps1.setInt(6, div);
					ps1.setInt(7, depo);
					ps1.setInt(8, rs.getInt(5));
					ps1.setDate(9, new java.sql.Date(sdate.getTime()));
					ps1.setDate(10, new java.sql.Date(edate.getTime()));
					rs1 = ps1.executeQuery();
					if(rs1.next())
					{
						outamt=rs1.getDouble(1);
					}
					System.out.println("VACCODE "+rs.getInt(5)+" outamt is "+outamt+" year "+year+" nyear "+nyear);
					rs1.close();
					if(print)
					{
						rcp = new RcpDto();
						rcp.setVou_lo(" ");
						rcp.setVnart1("Total : ");
						rcp.setVamount(tot1);
						rcp.setDash(2);
						rcp.setVac_code(String.valueOf(wcode));
						data.add(rcp);
						print=false;
					}
					wcode=rs.getInt(5);
					first=false;
					bkcd=rs.getInt(10);
					
					tot1=0;
				}
				
				  if(bkcd==17 || (bkcd >39 && bkcd<50) || (bkcd >19 && bkcd<27) || bkcd==30)
					   first=true;
				 
				 if(rs.getDouble(3)>=outamt && rs.getDouble(3)>0 && first)
				 {
					 if((rs.getDouble(3)-outamt)>0)
					 {
						 rcp = new RcpDto();
						 rcp.setVou_lo(rs.getString(11));
						 rcp.setVnart1(rs.getString(7));
						 rcp.setVou_no(rs.getInt(1));
						 rcp.setVou_date(rs.getDate(2));
						 rcp.setDash(0);
						 rcp.setSerialno(rs.getInt(8)); // duedays
						 rcp.setBill_amt(rs.getDouble(3)); // Bill Amount
						 rcp.setVamount(rs.getDouble(3)-outamt); // OutStanding Amount
						 rcp.setChq_amt(outamt); // payment
						 rcp.setVac_code(rs.getString(5));
						 rcp.setParty_name(rs.getString(9));
						 data.add(rcp);
						 tot1 += rcp.getVamount();
						 gtot1 += rcp.getVamount();
						 print=true;
					 }
					 	 outamt=0.00;


				 }
				 else
				 {
					 outamt-=rs.getDouble(3);
				 }

			}
			rcp = new RcpDto();
			rcp.setVou_lo(" ");
			rcp.setVnart1("Total : ");
			rcp.setVamount(tot1);
			rcp.setVac_code(String.valueOf(wcode));
			rcp.setDash(2);
			data.add(rcp);

			rcp = new RcpDto();
			rcp.setVou_lo(" ");
			rcp.setVnart1("Grand Total : ");
			rcp.setVamount(gtot1);
			rcp.setVac_code(String.valueOf(wcode));
			rcp.setDash(3);
			data.add(rcp);
			
			rs.close();
			ps2.close();
			ps1.close();
			
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO Outstanding " + ee);
			ee.printStackTrace();
		}

		finally {
			try {
				if (rs != null) {rs.close();}
				if (ps2 != null) {ps2.close();}
				if (rs1 != null) {rs1.close();}
				if (ps1 != null) {ps1.close();}
				if (con != null) {con.close();}
			} catch (SQLException ee) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "+ ee);
			}
		}

		return data;
	}

	

	
	public ArrayList<RcpDto> getOutstandingSum(int year, int depo, int div, Date sdate, Date edate,int opt) 
	{
		ArrayList<RcpDto> data = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;


		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		HashMap map = null;
		PartyDto pdto=null;
		RegionDto rdto=null;
		AreaDto ddto=null;
		
		Date stdate=BaseClass.loginDt.getFr_date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd = sdf.format(stdate);
		String ed = sdf.format(edate);
		System.out.println("SD IS "+sd);
		
		int nyear=year+1;
		
		if(opt==1)
			map = BaseClass.loginDt.getPrtmap();
		else if(opt==3)
			map = BaseClass.loginDt.getRegmap();
		else if(opt==4)
			map = BaseClass.loginDt.getDistmap();
	 

		RcpDto rcp = null;
	 
		try {
			con = ConnectionFactory.getConnection();

/*			" select concat(l.vou_lo,'-',l.vou_no),l.vou_date,l.vamount vamt,0 outamt,l.vac_code,l.vbook_cd,l.vnart_1,(to_days(curdate())-to_days(l.vou_date)) duedays,l.vnart_2,l.vbook_cd vbk,l.vac_code vcode,l.vdist_cd,l.vreg_cd,l.vou_no  from ledfile l "+
			"where l.fin_year=? and l.div_code=? and l.vdepo_code=? and (l.vbook_cd=10 or l.vbook_cd=30 or l.vbook_cd between 40 and 49 or l.vbook_cd between 20 and 26) and l.vdbcr='DR'  " +
			" and l.vou_date < ?  and ifnull(l.del_tag,'')<>'D' " +
			"union all "+
*/
			
			String query2 = "select l.* from (select '0' vno,null vou_date,mopng_bal vamt,0 outamt,mac_code vac_code,0 vbook_cd,'' vnart_1,(to_days('"+ed+"')-to_days('"+sd+"')) duedays,'' vnart_2,17 vbk,mac_code vcode,0 vdist_cd, 0 vreg_cd,0 vou_no from partyacc p "+
			"where fin_year=? and div_code=? and depo_code=?  and mopng_db='DR'  " +
			" and mopng_bal>0 and ifnull(del_tag,'')<>'D'   "+
			"union all "+
			" select concat(l.vou_lo,'-',l.vou_no),l.vou_date,l.vamount vamt,0 outamt,l.vac_code,l.vbook_cd,l.vnart_1,(to_days('"+ed+"')-to_days(l.vou_date)) duedays,l.vnart_2,l.vbook_cd vbk,l.vac_code vcode,l.vdist_cd,l.vreg_cd,l.vou_no  from ledfile l "+
			"where l.fin_year=? and l.div_code=? and l.vdepo_code=? and (l.vbook_cd=10 or l.vbook_cd=30 or l.vbook_cd between 40 and 49 or l.vbook_cd between 20 and 26) and l.vdbcr='DR'  " +
			" and l.vou_date between  ? and ? and ifnull(l.del_tag,'')<>'D') l,partyfst p " +
			"where  p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code and p.mgrp_code in (45,46,51,70) and ifnull(P.locked,'N')<>'Y' and ifnull(P.del_tag,'')<>'D'  order by l.vdist_cd,p.mac_name,l.vac_code,l.vou_date,l.vou_no ";
//			"where  p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code and p.mgrp_code in (45,46,51,70)  and ifnull(P.del_tag,'')<>'D'  order by l.vdist_cd,p.mac_name,l.vac_code,l.vou_date,l.vou_no ";

			if(opt==3)
			{
				query2 = " select vou_no,vou_date,vamount vamt,0 outamt,vac_code,vbook_cd,vnart_1,(to_days(curdate())-to_days(vou_date)) duedays,vnart_2,vbook_cd vbk,vreg_cd vreg,vdist_cd,vreg_cd from ledfile "+
				"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=17 and vdbcr='DR'  " +
				" and vou_date <= ? and vreg_cd<>0 and ifnull(del_tag,'')<>'D'  "+
				"union all "+
				" select vou_no,vou_date,vamount vamt,0 outamt,vac_code,vbook_cd,vnart_1,(to_days(curdate())-to_days(vou_date)) duedays,vnart_2,vbook_cd vbk,vreg_cd vreg,vdist_cd,vreg_cd from ledfile "+
				"where fin_year<=? and div_code=? and vdepo_code=? and (vbook_cd=10 or vbook_cd=30 or vbook_cd between 40 and 49 or vbook_cd between 20 and 26) and vdbcr='DR'  " +
				" and vou_date between  ? and ? and vreg_cd<>0 and ifnull(del_tag,'')<>'D' order by vreg_cd,vdist_cd,vac_code,vou_date,vou_no ";
			}
			else if(opt==4)
			{
				query2 = " select vou_no,vou_date,vamount vamt,0 outamt,vac_code,vbook_cd,vnart_1,(to_days(curdate())-to_days(vou_date)) duedays,vnart_2,vbook_cd vbk,vdist_cd vdis,vdist_cd,vreg_cd from ledfile "+
				"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=17  and vdbcr='DR'  " +
				" and vou_date <= ? and vdist_cd<>0 and ifnull(del_tag,'')<>'D'  "+
				"union all"+
				" select vou_no,vou_date,vamount vamt,0 outamt,vac_code,vbook_cd,vnart_1,(to_days(curdate())-to_days(vou_date)) duedays,vnart_2,vbook_cd vbk,vdist_cd,vdist_cd vdis,vreg_cd from ledfile "+
				"where fin_year<=? and div_code=? and vdepo_code=? and (vbook_cd=10 or vbook_cd=30 or vbook_cd between 40 and 49 or vbook_cd between 20 and 26) and vdbcr='DR'  " +
				" and vou_date between  ? and ? and vdist_cd<>0 and ifnull(del_tag,'')<>'D' order by vdist_cd,vac_code,vou_date,vou_no ";
			}

			
			
			String query1="select sum(outamt),mcode,vdate from "+ 
			"(select ifnull(round(sum(mopng_bal),2),0) outamt,ifnull(mac_code,0) mcode,'"+sd+"' vdate from partyacc "+  
			"where fin_year=? and div_code=? and depo_code=?  and mac_code=? "+  
			"and  mopng_db='CR' and ifnull(del_tag,'')<>'D'  "+
			"union all "+
			"select round(sum(vamount),2) outamt,vac_code mcode,max(vou_date) vdate from ledfile "+  
			"where fin_year<=? and div_code=? and vdepo_code=?  and vbook_cd<>17 and vac_code=? "+  
			"and vou_date between ? and ? and  vdbcr='CR' and ifnull(del_tag,'')<>'D') a group by mcode order by mcode desc ";
			 

			data = new ArrayList<RcpDto>();

			ps1 = con.prepareStatement(query1);
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			//ps2.setDate(4, new java.sql.Date(sdate.getTime()));
//			ps2.setInt(4, nyear);
			ps2.setInt(4, year);
			ps2.setInt(5, div);
			ps2.setInt(6, depo);
			ps2.setDate(7, new java.sql.Date(sdate.getTime()));
			ps2.setDate(8, new java.sql.Date(edate.getTime()));
			
			if(opt<3)
			{
				ps2.setInt(9, div);
				ps2.setInt(10, depo);
			}
			
			rs = ps2.executeQuery();
			
			double outamt=0.00;
			int wcode=0;
			int xcode=0;
			Date wdate=null;
			boolean print=false;
			boolean dprint=false;			
			boolean first=false;
			boolean dfirst=true;
			double tot15=0.00;
			double tot30=0.00;
			double tot60=0.00;
			double tot90=0.00;
			double gtot15=0.00;
			double gtot30=0.00;
			double gtot60=0.00;
			double gtot90=0.00;
			double dtot15=0.00;
			double dtot30=0.00;
			double dtot60=0.00;
			double dtot90=0.00;
			
			int bkcd=0;
			int distcd=0;
			int vreg_cd=0;
			int vdist_cd=0;
			while (rs.next())
			{
				 
				if(dfirst)
				{
					xcode=rs.getInt(11);
					distcd=rs.getInt(12);
					dfirst=false;
				}
				 
				
				if(xcode!=rs.getInt(11))
				{
					if(print)
					{
						rcp = new RcpDto();
						rcp.setVou_lo(" ");
						rcp.setVnart1("Total : ");
						rcp.setDiscount(tot15); // 15 days
						rcp.setVamount(tot30);  // 30 days
						rcp.setBill_amt(tot60);  //60 days
						rcp.setCr_amt(tot90);  // 90 days
						rcp.setVgrp_code(wcode); // partycode/region/dist code
						rcp.setDash(2);
						rcp.setVac_code(String.valueOf(wcode));
						rcp.setCity("");

						if(opt==1)
						{
							pdto = (PartyDto) map.get(String.valueOf(xcode));
							if(pdto==null)
							{
								rcp.setParty_name("Wrong Code "+xcode);
								rcp.setCity("");
								rcp.setPono("");
								rcp.setVdist_cd(0);
								rcp.setVreg_cd(0);
								
							}
							else
							{
								rcp.setParty_name(pdto.getMac_name());
								rcp.setCity(pdto.getMcity()+pdto.getMac_code());
								rcp.setPono(pdto.getMobile());
								rcp.setVdist_cd(vdist_cd);
								rcp.setVreg_cd(vreg_cd);
								rcp.setVou_date(wdate);
							}
						}
						else if(opt==3)
						{
							    rcp.setParty_name((String) map.get(xcode));
								
						}
						else if(opt==4)
						{
								rcp.setParty_name((String) map.get(xcode));
						}

						data.add(rcp);
						dprint=true;
						
					} // end  of if (print)
						print=false;
						xcode=rs.getInt(11);
						tot15=0;
						tot30=0;
						tot60=0;
						tot90=0;

				}
				
				if(distcd!=rs.getInt(12))
				{
					if(dprint)
					{
						rcp = new RcpDto();
						rcp.setVou_lo(" ");
						rcp.setVnart1("Total : ");
						rcp.setDiscount(dtot15); // 15 days
						rcp.setVamount(dtot30);  // 30 days
						rcp.setBill_amt(dtot60);  //60 days
						rcp.setCr_amt(dtot90);  // 90 days
						rcp.setVgrp_code(wcode); // partycode/region/dist code
						rcp.setDash(4);
						rcp.setVac_code(String.valueOf(wcode));
						rcp.setCity("");
						data.add(rcp);
						
						distcd=rs.getInt(12);
						
						dtot15=0;
						dtot30=0;
						dtot60=0;
						dtot90=0;
						
					} // end  of if (print)
					dprint=false;
				}  // end if distcd change 
				
				if(wcode!=rs.getInt(5))
				{
					outamt=0.00;
					wdate=null;
					ps1.setInt(1, year);
					ps1.setInt(2, div);
					ps1.setInt(3, depo);
					ps1.setInt(4, rs.getInt(5));
					ps1.setInt(5, nyear);
					ps1.setInt(6, div);
					ps1.setInt(7, depo);
					ps1.setInt(8, rs.getInt(5));
					ps1.setDate(9, new java.sql.Date(sdate.getTime()));
					ps1.setDate(10, new java.sql.Date(edate.getTime()));
					rs1 = ps1.executeQuery();
					if(rs1.next())
					{
						outamt=rs1.getDouble(1);
						wdate=rs1.getDate(3);
					}
					rs1.close();
					
					wcode=rs.getInt(5);
					vdist_cd=rs.getInt(12);
					vreg_cd=rs.getInt(13);
					 
					bkcd=rs.getInt(10);
					first=false;
					

				}
				
				  if(bkcd==17 || (bkcd >39 && bkcd<50) || (bkcd >19 && bkcd<27) || bkcd==30)
					   first=true;
				 
				 
				 if(rs.getDouble(3)>=outamt && rs.getDouble(3)>0 && first)
				 {
					  
					 if((rs.getDouble(3)-outamt)>0)
					 {
//						 if(rs.getInt(8)<31)
						// if(rs.getInt(8)<91)
							 if(rs.getInt(8)<240)
						 {
							 tot15 += rs.getDouble(3)-outamt;
							 gtot15 += rs.getDouble(3)-outamt;
							 dtot15 += rs.getDouble(3)-outamt;
						 }
/*						 else if(rs.getInt(8)>=31 && rs.getInt(8)<61)
						 {
							 tot30 += rs.getDouble(3)-outamt;
							 gtot30 += rs.getDouble(3)-outamt;
							 dtot30 += rs.getDouble(3)-outamt;
						 }
						 else if(rs.getInt(8)>=61 && rs.getInt(8)<121)
						 {
							 tot60 += rs.getDouble(3)-outamt;
							 gtot60 += rs.getDouble(3)-outamt;
							 dtot60 += rs.getDouble(3)-outamt;
						 }
*/						 else if(rs.getInt(8)>240)
						 {
							 tot90 += rs.getDouble(3)-outamt;
							 gtot90 += rs.getDouble(3)-outamt;
							 dtot90 += rs.getDouble(3)-outamt;
						 }

						 print=true;
					 }
					 	 outamt=0.00;


				 }
				 else
				 {
					 outamt-=rs.getDouble(3);
				 }

			}
			
			
		

			
			rcp = new RcpDto();
			rcp.setVou_lo(" ");
			rcp.setVnart1("Total : ");
			rcp.setDiscount(tot15);
			rcp.setVamount(tot30);
			rcp.setBill_amt(tot60);
			rcp.setCr_amt(tot90);
			rcp.setVac_code(String.valueOf(wcode));
			rcp.setDash(2);
			rcp.setCity("Z");
			if(opt==1)
			{
				pdto = (PartyDto) map.get(String.valueOf(wcode));
				rcp.setParty_name(pdto.getMac_name());
				rcp.setCity(pdto.getMcity());
				rcp.setPono(pdto.getMobile());
			}
			else if(opt==3)
			{
				    rcp.setParty_name((String) map.get(xcode));
			}
			else if(opt==4)
			{
					rcp.setParty_name((String) map.get(xcode));
			}

			data.add(rcp);
			
			
			rcp = new RcpDto();
			rcp.setVou_lo(" ");
			rcp.setVnart1("Total : ");
			rcp.setDiscount(dtot15); // 15 days
			rcp.setVamount(dtot30);  // 30 days
			rcp.setBill_amt(dtot60);  //60 days
			rcp.setCr_amt(dtot90);  // 90 days
			rcp.setVgrp_code(wcode); // partycode/region/dist code
			rcp.setDash(4);
			rcp.setVac_code(String.valueOf(wcode));
			rcp.setCity("");
			data.add(rcp);
			

			rcp = new RcpDto();
			rcp.setVou_lo(" ");
			rcp.setVnart1("Grand Total : ");
			rcp.setDiscount(gtot15);
			rcp.setVamount(gtot30);
			rcp.setBill_amt(gtot60);
			rcp.setCr_amt(gtot90);
			rcp.setVac_code(String.valueOf(wcode));
			rcp.setDash(3);
			rcp.setCity("ZZ");
			data.add(rcp);
			
			rs.close();
			ps2.close();
			ps1.close();
			
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO Outstanding " + ee);
			ee.printStackTrace();
		}

		finally {
			try {
				if (rs != null) {rs.close();}
				if (ps2 != null) {ps2.close();}
				if (rs1 != null) {rs1.close();}
				if (ps1 != null) {ps1.close();}
				if (con != null) {con.close();}
			} catch (SQLException ee) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "+ ee);
			}
		}

		return data;
	}


	

	public List partystat(int year, int depo, int div, Date sdate, Date edate,String code)
	{
		List data = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		InvFstDto inv = null;
		try {
			con = ConnectionFactory.getConnection();


			String query2 = " select invno,inv_date,gross_amt,tax1,tax2,totalamt,dbno,dbdate,dbamt,cnno,cndate,cnamt,taxamt,doc_type,party_code from "
					+ " (select concat(inv_yr,inv_lo,right(inv_no,5))invno ,inv_date,(lsale1+lsale2+lsale3+lsale9+lsale10) gross_amt,(ltax1_amt+ltax3_amt+freetax1+freetax3) tax1,(ltax2_amt+ltax9_amt+ltax10_amt+freetax2+freetax9) tax2, "
					+ " (lsale1+lsale2+lsale3+lsale9+lsale10+ltax1_amt+ltax3_amt+ltax2_amt+ltax9_amt+ltax10_amt+freetax1+freetax3+freetax2+freetax9) totalamt,0 dbno,null dbdate,0 dbamt,0 cnno,null cndate,0 cnamt,0 taxamt, doc_type,party_code "
					+ " from invfst where fin_year=? and div_Code=? and depo_code=? and doc_type in (39,40) and party_code = ? and inv_date between ? and ?  and ifnull(del_tag,'')<>'D' "
					+ " union all "
					+ " select concat(inv_yr,inv_lo,'C',right(inv_no,4)) invno ,inv_date inv_date,(lsale1+lsale2+lsale3+lsale9+lsale10)  gross_amt,(ltax1_amt+ltax3_amt+freetax1+freetax3) tax1,(ltax2_amt+ltax9_amt+ltax10_amt+freetax2+freetax9) tax2, "
					+ " 0 totalamt,0 dbno,null dbdate,0 dbamt,concat(inv_lo,inv_yr,'C',right(inv_no,4)) cnno,inv_date cndate,(taxable1+taxable2+taxable3+taxable9+taxable10) cnamt,(ltax1_amt+ltax3_amt) taxamt,doc_type,party_code  "
					+ " from invfst where fin_year=? and div_Code=? and depo_code=? and doc_type=41 and party_code = ? and inv_date between ? and ? and ifnull(del_tag,'')<>'D' "
					+ " union all "
					+ " select concat(inv_lo,inv_yr,'D',right(inv_no,4)) invno ,inv_date inv_date,bill_amt gross_amt,0 tax1,0 tax2, "
					+ " 0 totalamt,concat(inv_yr,inv_lo,'D',right(inv_no,4)) dbno,inv_date dbdate,bill_amt dbamt,0 cnno,null cndate,0 cnamt,(ltax1_amt+ltax3_amt+freetax1+freetax3) taxamt,doc_type,party_code "
					+ " from invfst where fin_year=? and div_Code=? and depo_code=? and doc_type=51 and party_code = ? and inv_date between ? and ? and ifnull(del_tag,'')<>'D') a "
					+ " group by inv_date,invno ";
			
			if(code==null)
			{
			query2 = " select invno,inv_date,gross_amt,tax1,tax2,totalamt,dbno,dbdate,dbamt,cnno,cndate,cnamt,taxamt,doc_type,party_code from "
					+ " (select concat(inv_yr,inv_lo,right(inv_no,5))invno ,inv_date,(lsale1+lsale2+lsale3+lsale9+lsale10) gross_amt,(ltax1_amt+ltax3_amt+freetax1+freetax3) tax1,(ltax2_amt+ltax9_amt+ltax10_amt+freetax2+freetax9) tax2, "
					+ " (lsale1+lsale2+lsale3+lsale9+lsale10+ltax1_amt+ltax3_amt+ltax2_amt+ltax9_amt+ltax10_amt+freetax1+freetax3+freetax2+freetax9) totalamt,0 dbno,null dbdate,0 dbamt,0 cnno,null cndate,0 cnamt,0 taxamt, doc_type,party_code "
					+ " from invfst where fin_year=? and div_Code=? and depo_code=? and doc_type in (39,40)  and inv_date between ? and ?  and ifnull(del_tag,'')<>'D' "
					+ " union all "
					+ " select concat(inv_yr,inv_lo,'C',right(inv_no,4)) invno ,inv_date inv_date,(lsale1+lsale2+lsale3+lsale9+lsale10)  gross_amt,(ltax1_amt+ltax3_amt+freetax1+freetax3) tax1,(ltax2_amt+ltax9_amt+ltax10_amt+freetax2+freetax9) tax2, "
					+ " 0 totalamt,0 dbno,null dbdate,0 dbamt,concat(inv_lo,inv_yr,'C',right(inv_no,4)) cnno,inv_date cndate,(taxable1+taxable2+taxable3+taxable9+taxable10) cnamt,(ltax1_amt+ltax3_amt) taxamt,doc_type,party_code  "
					+ " from invfst where fin_year=? and div_Code=? and depo_code=? and doc_type=41  and inv_date between ? and ? and ifnull(del_tag,'')<>'D' "
					+ " union all "
					+ " select concat(inv_lo,inv_yr,'D',right(inv_no,4)) invno ,inv_date inv_date,bill_amt gross_amt,0 tax1,0 tax2, "
					+ " 0 totalamt,concat(inv_yr,inv_lo,'D',right(inv_no,4)) dbno,inv_date dbdate,bill_amt dbamt,0 cnno,null cndate,0 cnamt,(ltax1_amt+ltax3_amt+freetax1+freetax3) taxamt,doc_type,party_code "
					+ " from invfst where fin_year=? and div_Code=? and depo_code=? and doc_type=51  and inv_date between ? and ? and ifnull(del_tag,'')<>'D') a "
					+ " group by party_code,inv_date,invno ";
			}

			data = new ArrayList();

			ps2 = con.prepareStatement(query2);
			
			if(code!=null)
			{
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			ps2.setString(4, code);
			ps2.setDate(5, new java.sql.Date(sdate.getTime()));
			ps2.setDate(6, new java.sql.Date(edate.getTime()));
			ps2.setInt(7, year);
			ps2.setInt(8, div);
			ps2.setInt(9, depo);
			ps2.setString(10, code);
			ps2.setDate(11, new java.sql.Date(sdate.getTime()));
			ps2.setDate(12, new java.sql.Date(edate.getTime()));
			ps2.setInt(13, year);
			ps2.setInt(14, div);
			ps2.setInt(15, depo);
			ps2.setString(16, code);
			ps2.setDate(17, new java.sql.Date(sdate.getTime()));
			ps2.setDate(18, new java.sql.Date(edate.getTime()));
			}
			else
			{
				ps2.setInt(1, year);
				ps2.setInt(2, div);
				ps2.setInt(3, depo);
				ps2.setDate(4, new java.sql.Date(sdate.getTime()));
				ps2.setDate(5, new java.sql.Date(edate.getTime()));
				ps2.setInt(6, year);
				ps2.setInt(7, div);
				ps2.setInt(8, depo);
				ps2.setDate(9, new java.sql.Date(sdate.getTime()));
				ps2.setDate(10, new java.sql.Date(edate.getTime()));
				ps2.setInt(11, year);
				ps2.setInt(12, div);
				ps2.setInt(13, depo);
				ps2.setDate(14, new java.sql.Date(sdate.getTime()));
				ps2.setDate(15, new java.sql.Date(edate.getTime()));
			}

			
			rs = ps2.executeQuery();

			double gross = 0.00;
			double tax1 = 0.00;
			double tax2 = 0.00;
			double total = 0.00;
			 
			String wcode="";
			boolean first=true;
			while (rs.next()) {
				
				if (first)
				{
					wcode=rs.getString(15);
					first=false;
				}
				if (!wcode.equals(rs.getString(15)))
				{
					inv = new InvFstDto();
					inv.setGross_amt(gross);
					inv.setLtax1_amt(tax1);
					inv.setLtax2_amt(tax2);
					inv.setBill_amt(total);
					inv.setParty_code(wcode);
					inv.setDash(1);
					data.add(inv);

					gross =0.00;
					tax1 = 0.00;
					tax2 = 0.00;
					total= 0.00;
					wcode=rs.getString(15);
					
				}
				
				inv = new InvFstDto();
				inv.setInv_lo(rs.getString(1));
				inv.setInv_date(rs.getDate(2));
				inv.setGross_amt(rs.getDouble(3));
				inv.setLtax1_amt(rs.getDouble(4));
				inv.setLtax2_amt(rs.getDouble(5));
				inv.setBill_amt(rs.getDouble(6));
				inv.setDoc_type(rs.getInt(14));
				inv.setParty_code(rs.getString(15));
				inv.setDash(0);
				data.add(inv);
				gross += rs.getDouble(3);
				tax1 += rs.getDouble(4);
				tax2 += rs.getDouble(5);
				total += rs.getDouble(6);


			}
			
/*			inv = new InvFstDto();
			inv.setGross_amt(gross);
			inv.setLtax1_amt(tax1);
			inv.setLtax2_amt(tax2);
			inv.setBill_amt(total);
			inv.setParty_code(wcode);
			inv.setDash(1);
			data.add(inv);
			
*/			rs.close();
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO PartyStatement " + ee);
			ee.printStackTrace();
		}

		finally {
			try {

				if (rs != null) {
					rs.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ee) {
				System.out
						.println("-------------Exception in AccountDAO.Connection.close "
								+ ee);
			}
		}

		return data;
	}

	
	
	public int  closingTrfAccounts(int year, int depo, int div, Date sdate, Date edate)
	{
		Connection con = null;
		PreparedStatement ps2 = null;
		int i=0;
		SimpleDateFormat sdf = null;
		try {
			con = ConnectionFactory.getConnection();

			sdf = new SimpleDateFormat("dd/MM/yyyy");

			
			String query2 = " update partyacc p, "+
			" (select code,sum(bal) bal from "+
			" (select mac_code code,if(ifnull(mopng_db,'DR')='DR',ifnull(mopng_bal,0.00),(ifnull(mopng_bal,0.00)*-1)) bal  from partyacc "+
			" WHERE FIN_YEAR=? AND DIV_CODE=? AND DEPO_CODE=?  and ifnull(del_tag,'')<>'D' group by mac_code "+
			" union all "+
			" select vac_code code,sum(vamount+ifnull(cr_amt,0)-ifnull(dr_amt,0))*-1 bal  from ledfile WHERE FIN_YEAR=? AND DIV_CODE=? AND VDEPO_CODE=? "+  
			" AND VBOOK_CD1 in (60,61) and vou_date between ? and ?  and ifnull(del_tag,'')<>'D' group by vac_code "+
			" union all "+
			" select vac_code code,sum(vamount) bal  from ledfile WHERE FIN_YEAR=? AND DIV_CODE=? AND VDEPO_CODE=? "+  
			" AND VBOOK_CD1 in(94,30,95) and vou_date between ? and ?  and ifnull(del_tag,'')<>'D' group by vac_code "+
			" union all "+
			" select vac_code code,sum(vamount-ifnull(bank_chg,0)) bal  from ledfile WHERE FIN_YEAR=? AND DIV_CODE=? AND VDEPO_CODE=? "+  
			" AND VBOOK_CD1 in(20,21,22,90) AND VDBCR='DR' and vou_date between ? and ?  and ifnull(del_tag,'')<>'D' group by vac_code "+
			" union all "+

			" select vac_code code, sum(vamount-ifnull(bank_chg,0))*-1 bal  from ledfile WHERE FIN_YEAR=? AND DIV_CODE=? AND VDEPO_CODE=? "+  
			" AND VBOOK_CD1 in(20,21,22,90) AND VDBCR='CR' and vou_date between ? and ?  and ifnull(del_tag,'')<>'D' group by vac_code "+
			" union all "+

			
			
			" select exp_code code,sum(vamount) bal  from ledfile WHERE FIN_YEAR=? AND DIV_CODE=? AND VDEPO_CODE=? "+  
			" AND VBOOK_CD1 =10 and vou_date between ? and ?  and ifnull(del_tag,'')<>'D' and exp_code like '350%' group by exp_code "+
			" union all "+
			" select vac_code code,sum(if(vdbcr='CR',vamount,(vamount*-1))) bal  from rcpfile WHERE FIN_YEAR=? AND DIV_CODE=? AND VDEPO_CODE=? "+  
			" AND VBOOK_CD in(20,21,22) and vou_date between ? and ?  and vnart_1 in ('Discount','Interest') and ifnull(del_tag,'')<>'D' group by vac_code) a "+
			" group by a.code) p1 "+ 
			" set p.mopng_bal=if(p1.bal>=0,p1.bal,(p1.bal*-1)),p.mopng_db=if(p1.bal>=0,'DR','CR') "+ 
			" where p.fin_year=? and p.div_Code=? and p.depo_code=? and p.msub_code=50 and p.mac_Code=p1.code ";
			
			

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			
			ps2.setInt(4, year);
			ps2.setInt(5, div);
			ps2.setInt(6, depo);
			ps2.setDate(7, new java.sql.Date(sdate.getTime()));
			ps2.setDate(8, new java.sql.Date(edate.getTime()));


			ps2.setInt(9, year);
			ps2.setInt(10, div);
			ps2.setInt(11, depo);
			ps2.setDate(12, new java.sql.Date(sdate.getTime()));
			ps2.setDate(13, new java.sql.Date(edate.getTime()));

			ps2.setInt(14, year);
			ps2.setInt(15, div);
			ps2.setInt(16, depo);
			ps2.setDate(17, new java.sql.Date(sdate.getTime()));
			ps2.setDate(18, new java.sql.Date(edate.getTime()));

			
			ps2.setInt(19, year);
			ps2.setInt(20, div);
			ps2.setInt(21, depo);
			ps2.setDate(22, new java.sql.Date(sdate.getTime()));
			ps2.setDate(23, new java.sql.Date(edate.getTime()));

			
			ps2.setInt(24, year);
			ps2.setInt(25, div);
			ps2.setInt(26, depo);
			ps2.setDate(27, new java.sql.Date(sdate.getTime()));
			ps2.setDate(28, new java.sql.Date(edate.getTime()));

			ps2.setInt(29, year);
			ps2.setInt(30, div);
			ps2.setInt(31, depo);
			ps2.setDate(32, new java.sql.Date(sdate.getTime()));
			ps2.setDate(33, new java.sql.Date(edate.getTime()));

			
			ps2.setInt(34, (year+1));
			ps2.setInt(35, div);
			ps2.setInt(36, depo);

			ps2.executeUpdate();

		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO Clsoing TRf Accounts" + ee);
			ee.printStackTrace();
		}

		finally {
			try {

				if (ps2 != null) {
					ps2.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ee) {
				System.out
						.println("-------------Exception in AccountDAO.Connection.close "
								+ ee);
				i=-1;
			}
		}

		return i;
	}

	

	public List partySaleAmt(int year, int depo, int div, Date sdate, Date edate)
	{
		List data = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		InvFstDto inv = null;
		try {
			con = ConnectionFactory.getConnection();


			String query2 = " select f.party_code,f.mac_name,f.mcity,sum(f.mfamt),sum(f.tfamt),sum(f.genamt), sum(f.mfamt+f.tfamt+f.genamt) total, "+ 
			" f.mpst_no,sum(f.fmfamt),sum(f.ftfamt),sum(f.fgenamt), sum(f.fmfamt+f.ftfamt+f.fgenamt) ftotal from "+
			" (select i.party_code,sum(i.bill_amt) mfamt, 0 tfamt,0 genamt,sum(freeval1+freeval2+freeval3+freeval9) fmfamt, 0 ftfamt,0 fgenamt,p.mpst_no,p.mac_name,p.mcity from invfst i,partyfst p where fin_year=? and i.div_code=1 and i.depo_Code=? "+ 
			" and doc_type=? and inv_Date between ? and ? and ifnull(i.del_tag,'')<>'D' and p.div_code=1 and p.depo_code=? and p.mac_code=i.party_code group by party_code "+
			" union all "+ 
			" select i.party_code,0 mfamt, sum(i.bill_amt) tfamt,0 genamt,0 fmfamt, sum(freeval1+freeval2+freeval3+freeval9) ftfamt,0 fgenamt,p.mpst_no,p.mac_name,p.mcity  from invfst i, partyfst p where fin_year=? and i.div_code=2 and i.depo_Code=? "+
			" and doc_type=? and inv_Date between ? and ? and ifnull(i.del_tag,'')<>'D' and p.div_code=2 and p.depo_code=? and p.mac_code=i.party_code group by party_code "+
			" union all  "+
			" select i.party_code,0 mfamt, 0 tfamt,sum(i.bill_amt) genamt,0 fmfamt, 0 ftfamt,sum(freeval1+freeval2+freeval3+freeval9)  fgenamt,p.mpst_no,p.mac_name,p.mcity from invfst i,partyfst p  where fin_year=? and i.div_code=3 and i.depo_Code=? "+
			" and doc_type=? and inv_Date between ? and ? and ifnull(i.del_tag,'')<>'D' and p.div_code=3 and p.depo_code=? and p.mac_code=i.party_code group by party_code) f "+
			" group by f.mpst_no order by f.party_code ";

			
			if (depo==40)
			{
				query2 = " select f.party_code,f.mac_name,f.mcity,sum(f.mfamt),sum(f.tfamt),sum(f.genamt), sum(f.mfamt+f.tfamt+f.genamt) total, "+ 
						" f.mdlno1,sum(f.fmfamt),sum(f.ftfamt),sum(f.fgenamt), sum(f.fmfamt+f.ftfamt+f.fgenamt) ftotal from "+
						" (select i.party_code,sum(i.bill_amt) mfamt, 0 tfamt,0 genamt,sum(freeval1+freeval2+freeval3+freeval9) fmfamt, 0 ftfamt,0 fgenamt,p.mdlno1,p.mac_name,p.mcity from invfst i,partyfst p where fin_year=? and i.div_code=1 and i.depo_Code=? "+ 
						" and doc_type=? and inv_Date between ? and ? and ifnull(i.del_tag,'')<>'D' and p.div_code=1 and p.depo_code=? and p.mac_code=i.party_code group by party_code "+
						" union all "+ 
						" select i.party_code,0 mfamt, sum(i.bill_amt) tfamt,0 genamt,0 fmfamt, sum(freeval1+freeval2+freeval3+freeval9) ftfamt,0 fgenamt,p.mdlno1,p.mac_name,p.mcity  from invfst i, partyfst p where fin_year=? and i.div_code=2 and i.depo_Code=? "+
						" and doc_type=? and inv_Date between ? and ? and ifnull(i.del_tag,'')<>'D' and p.div_code=2 and p.depo_code=? and p.mac_code=i.party_code group by party_code "+
						" union all  "+
						" select i.party_code,0 mfamt, 0 tfamt,sum(i.bill_amt) genamt,0 fmfamt, 0 ftfamt,sum(freeval1+freeval2+freeval3+freeval9)  fgenamt,p.mdlno1,p.mac_name,p.mcity from invfst i,partyfst p  where fin_year=? and i.div_code=3 and i.depo_Code=? "+
						" and doc_type=? and inv_Date between ? and ? and ifnull(i.del_tag,'')<>'D' and p.div_code=3 and p.depo_code=? and p.mac_code=i.party_code group by party_code) f "+
						" group by f.mdlno1 order by f.party_code ";

			}
			


			data = new ArrayList();

			ps2 = con.prepareStatement(query2);
			
			ps2.setInt(1, year);
			ps2.setInt(2, depo);
			ps2.setInt(3, 40);
			ps2.setDate(4, new java.sql.Date(sdate.getTime()));
			ps2.setDate(5, new java.sql.Date(edate.getTime()));
			ps2.setInt(6, depo);
			ps2.setInt(7, year);
			ps2.setInt(8, depo);
			ps2.setInt(9, 40);
			ps2.setDate(10, new java.sql.Date(sdate.getTime()));
			ps2.setDate(11, new java.sql.Date(edate.getTime()));
			ps2.setInt(12, depo);
			ps2.setInt(13, year);
			ps2.setInt(14, depo);
			ps2.setInt(15, 40);
			ps2.setDate(16, new java.sql.Date(sdate.getTime()));
			ps2.setDate(17, new java.sql.Date(edate.getTime()));
			ps2.setInt(18, depo);

			
			
			rs = ps2.executeQuery();

			double gross = 0.00;
			double tax1 = 0.00;
			double tax2 = 0.00;
			double tax3 = 0.00;
			 
			double fgross = 0.00;
			double ftax1 = 0.00;
			double ftax2 = 0.00;
			double ftax3 = 0.00;

			while (rs.next())
			{
				inv = new InvFstDto();
				inv.setParty_code(rs.getString(1));
				inv.setParty_name(rs.getString(2));
				inv.setCity(rs.getString(3));
				inv.setLtax1_amt(rs.getDouble(4));
				inv.setLtax2_amt(rs.getDouble(5));
				inv.setLtax3_amt(rs.getDouble(6));
				inv.setBill_amt(rs.getDouble(7));
				inv.setTin_no(rs.getString(8));
				inv.setLsale1(rs.getDouble(9));
				inv.setLsale2(rs.getDouble(10));
				inv.setLsale3(rs.getDouble(11));
				inv.setLsale9(rs.getDouble(12));

				
				inv.setDash(0);
				tax1 += rs.getDouble(4);
				tax2 += rs.getDouble(5);
				tax3 += rs.getDouble(6);
				gross += rs.getDouble(7);
				ftax1 += rs.getDouble(9);
				ftax2 += rs.getDouble(10);
				ftax3 += rs.getDouble(11);
				fgross += rs.getDouble(12);

				
				data.add(inv);
			}
			inv = new InvFstDto();
			inv.setParty_code("");
			inv.setParty_name("Total");
			inv.setCity("");
			inv.setLtax1_amt(tax1);
			inv.setLtax2_amt(tax2);
			inv.setLtax3_amt(tax3);
			inv.setBill_amt(gross);
			inv.setLsale1(ftax1);
			inv.setLsale2(ftax2);
			inv.setLsale3(ftax3);
			inv.setLsale9(fgross);
			inv.setDash(1);
			data.add(inv);
			rs.close();
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO partySaleAmt " + ee);
			ee.printStackTrace();
		}

		finally {
			try {

				if (rs != null) {
					rs.close();
				}
				if (ps2 != null) {
					ps2.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ee) {
				System.out
						.println("-------------Exception in AccountDAO.Connection.close "
								+ ee);
			}
		}

		return data;
	}
	
	public List getChqeueList (int year,int div,int depo,int bkcd,Date sdate,Date edate, int optn)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		String order=" order by vou_date ";
		String order1="";
		if (optn==2)
				order=" order by l.vbook_cd,vou_date ";
		if (optn==3)
		{
			order=" order by l.vreg_cd,p.mcity,p.mac_name,l.vou_date ";
//			order1=" order by d.reg_name ";
		}
		ArrayList v=null;
		
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			
		try 
		{
			
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
			
			String query="select a.*,d.reg_name from  " +
			"(select l.vou_date,l.vou_no,l.vnart_1,l.vac_code,p.mac_name,p.mcity," +
			"if(l.vdbcr='CR',l.vamount,(l.vamount*-1)),l.vbook_cd,l.vreg_cd from ledfile l,partyfst p "+
			" where l.fin_year=? and l.div_code=? and l.vdepo_code=? and (l.vbook_cd=? or l.vbook_cd between 20 and 30) and l.vgrp_code in (45,46,51) " +
			" and l.vou_date between ? and ? "+
			" and p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code "+order+") a ,regionmast d" +
			" where d.fin_year=? and d.div_code=? and d.depo_code=? and d.reg_code=a.vreg_cd  " +order1;

			
			
			double tot=0.00;

			ps = con.prepareStatement(query);
			ps.setInt(1, year);
			ps.setInt(2, div);
			ps.setInt(3, depo);
			ps.setInt(4, 10);
	    	ps.setDate(5, sDate);
	    	ps.setDate(6, eDate);
			ps.setInt(7, div);
			ps.setInt(8, depo);
			ps.setInt(9, year);
			ps.setInt(10, div);
			ps.setInt(11, depo);

 
	    	rs= ps.executeQuery();

			RcpDto rcp=null;
			v = new ArrayList();
			int sno=0;
			boolean first=true;
			int code=0;
			double total=0.00;
			while (rs.next())
			{
				
					if(first)
					{
						if(optn==2)
							code=rs.getInt(8);   // book wise 
						if(optn==3)
							code=rs.getInt(4);   // party wise
						first=false;
					}
				
					if(optn==2 && code!=rs.getInt(8))
					{
						rcp= new RcpDto();
						rcp.setChq_amt(total);
						rcp.setDash(1);
						v.add(rcp);
						total=0.00;
						code=rs.getInt(8);
					}

					if(optn==3 && code!=rs.getInt(4))
					{
						rcp= new RcpDto();
						rcp.setChq_amt(total);
						rcp.setDash(1);
						v.add(rcp);
						total=0.00;
						code=rs.getInt(4);
					}

					sno++;
					rcp= new RcpDto();
					rcp.setChq_no(rs.getString(3));
					rcp.setChq_date(rs.getDate(1));
					rcp.setChq_amt(rs.getDouble(7));
					rcp.setParty_name(rs.getString(5));
					rcp.setVou_date(rs.getDate(1));
					rcp.setCity(rs.getString(6));
					rcp.setExp_name(rs.getString(10));   // area name
					if(rs.getInt(8)==10)
						rcp.setSub_name("CASH");
					else if(rs.getInt(8)==21)
						rcp.setSub_name("UCO");
					else if(rs.getInt(8)==22)
						rcp.setSub_name("SBI");
					else if(rs.getInt(8)==26)
						rcp.setSub_name("KOTAK");
					else if(rs.getInt(8)==30)
						rcp.setSub_name("J.V.");
					
					tot+=rs.getDouble(7);
					total+=rs.getDouble(7);
					rcp.setDash(0);
					v.add(rcp);
			}

			if(optn==2 || optn==3)
			{
				rcp= new RcpDto();
				rcp.setChq_amt(total);
				rcp.setDash(1);
				v.add(rcp);
				total=0.00;
			}

			rcp= new RcpDto();
			rcp.setChq_no("Total :");
			rcp.setChq_amt(tot);
			rcp.setDash(2);
			v.add(rcp);
			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();


		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in AccountDAO.getChqeueList " + ex);
		}
		finally {
			try {
				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "+e);
			}
		}

		return v;
	}

	public List getCashBook(int year, int depo, int div, Date sdate, Date edate,int doctp) 
	{
		List data = null;
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		RcpDto rcp = null;
		double balance = 0.00;
		double tot1 = 0.00;
		double tot2 = 0.00;
		int  opcode=13002;  // cash code
		int wdoctp=0;
		
		if(doctp==26 )   // ing
			opcode=1619;  // ing vysya code  book_code26
		else if(doctp==20 )   // sbi
		{
			opcode=14015;  // sbi book_code22
			wdoctp=10;
		}
		else if(doctp==30 )   // jvi
			opcode=0;  // journal book

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
	
		try {
			con = ConnectionFactory.getConnection();

			String query1 = "select ifnull(head_code,0) from bookmast where book_cd=? ";
			
			String query2 = " select 0 vou_no,'"+sdf.format(sdate)+"' vou_date,'0' vac_code,'Opening Balance' mac_name,'' vnart_1,'' vnart_2,if(sum(opng)<0,sum(opng*-1),sum(opng)),if(sum(opng)<0,'DR','CR') vdbcr,'' chq_no,null chq_date from "+
			" (select if(mopng_db='DR',mopng_bal,(mopng_bal*-1)) opng from partyacc where fin_year=? and div_code=? and depo_code=? and mac_code=? "+
			" union all "+
			" select ifnull(sum(if(vdbcr='CR',l.vamount,(vamount*-1))),0) opng from ledfile l "+
			" where l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd=? and l.vou_date < ? "+  
			" and ifnull(l.del_tag,'')<>'D') a "+
			" union all "+
			" select l.vou_no,l.vou_date,l.vac_code,p.mac_name,ifnull(l.vnart_1,''),l.vnart_2,l.vamount,l.vdbcr,ifnull(l.chq_no,''),l.chq_date from ledfile l, partyfst p "+ 
			" where l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd=? and l.vou_date between ? and ? and l.vac_code=? "+ 
			"and ifnull(l.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code  "+
			" union all "+
			" select l.vou_no,l.vou_date,l.vac_code,p.mac_name,ifnull(l.vnart_1,''),l.vnart_2,l.vamount,l.vdbcr,ifnull(l.chq_no,''),l.chq_date from ledfile l, partyfst p "+ 
			" where l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd=? and l.vou_date between ? and ? "+ 
			"and ifnull(l.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code order by vou_date,vou_no,vdbcr ";

			
			data = new ArrayList();

			
			ps1 = con.prepareStatement(query1);
			ps1.setInt(1, doctp);
			rs1=ps1.executeQuery();
			if(rs1.next())
				opcode=rs1.getInt(1);
			
			System.out.println("OP CODE "+opcode);
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			ps2.setInt(4, opcode);
			ps2.setInt(5, year);
			ps2.setInt(6, div);
			ps2.setInt(7, depo);
			ps2.setInt(8, doctp);
			ps2.setDate(9, new java.sql.Date(sdate.getTime()));
			ps2.setInt(10, year);
			ps2.setInt(11, div);
			ps2.setInt(12, depo);
			ps2.setInt(13, wdoctp);
			ps2.setDate(14, new java.sql.Date(sdate.getTime()));
			ps2.setDate(15, new java.sql.Date(edate.getTime()));
			ps2.setInt(16, opcode);
			ps2.setInt(17, div);
			ps2.setInt(18, depo);
			ps2.setInt(19, year);
			ps2.setInt(20, div);
			ps2.setInt(21, depo);
			ps2.setInt(22, doctp);
			ps2.setDate(23, new java.sql.Date(sdate.getTime()));
			ps2.setDate(24, new java.sql.Date(edate.getTime()));
			ps2.setInt(25, div);
			ps2.setInt(26, depo);

			rs = ps2.executeQuery();
			
			boolean first=true;
			Date wdate=null;
			while (rs.next())
			{
				 
				if(doctp==30)
					balance=0;
/*				if(first)
				{
					wdate=sdate;
					rcp = new RcpDto();
					rcp.setVou_lo(" ");
					rcp.setVnart1("Date : ");
					rcp.setVou_date(wdate);
					rcp.setDash(3);
					data.add(rcp);
					balance=0.00;
					first=false;
				}
*/				
				wdate=rs.getDate(2);
				if(!wdate.equals(rs.getDate(2)))
				{
					if((tot1+tot2)>=0)
					{
						if(balance>0)
							tot1+=balance;
						else
							tot2+=(balance*-1);
						rcp = new RcpDto();
						rcp.setVou_lo(" ");
						rcp.setVnart1("Total : ");
						rcp.setVamount(tot1);
						rcp.setBill_amt(tot2);
						rcp.setDash(1);
						data.add(rcp);

						if(doctp!=30)
						{
							rcp = new RcpDto();
							rcp.setVou_lo(" ");
							rcp.setVnart1("Closing Balance on Date : ");
							rcp.setVou_date(wdate);
							rcp.setVamount(0.00);
							rcp.setBill_amt(0.00);
							if ((tot1-tot2) > 0)
								rcp.setBill_amt((tot1-tot2));
							else
								rcp.setVamount(tot2-tot1);
							rcp.setDash(2);
							data.add(rcp);
						}
						wdate=rs.getDate(2);
						balance=tot1-tot2;
						rcp = new RcpDto();
						rcp.setVou_lo(" ");
						rcp.setVnart1("Date : ");
						rcp.setVou_date(wdate);
						rcp.setDash(3);
						data.add(rcp);
					}

					if(doctp!=30)
					{
						rcp = new RcpDto();
						rcp.setVou_lo(" ");
						rcp.setVnart1("Opening Balance on Date : ");
						rcp.setVou_date(wdate);

						rcp.setVamount(0.00);
						rcp.setBill_amt(0.00);
						if ((tot1-tot2) > 0)
						{
							rcp.setVamount((tot1-tot2));
						}
						else
						{
							rcp.setBill_amt(tot2-tot1);
						}
						rcp.setDash(2);
						data.add(rcp);
					}

					tot1=0.00;
					tot2=0.00;
				}
				
				if(doctp==30 && rs.getString(4).equals("Opening Balance"))
				{

				}
				else
				{
					rcp = new RcpDto();
					rcp.setDash(0);
					rcp.setVou_no(rs.getInt(1));
					rcp.setVou_date(rs.getDate(2));
					rcp.setVac_code(rs.getString(3));
					rcp.setParty_name(rs.getString(4));
					rcp.setVnart1(rs.getString(5));
					if(doctp >=20 && doctp<=25)
						rcp.setVnart2(rs.getString(5)+" "+rs.getString(9)+" "+(rs.getDate(10)!=null?rs.getDate(10):"")+" "+ rs.getString(6));
					else
						rcp.setVnart2(rs.getString(6));
					if(rs.getString(8).equalsIgnoreCase("CR"))
					{
						rcp.setVamount(rs.getDouble(7));
						rcp.setBill_amt(0.00);
						tot1 += rs.getDouble(7);
					}
					if(rs.getString(8).equalsIgnoreCase("DR"))
					{
						rcp.setBill_amt(rs.getDouble(7));
						rcp.setVamount(0.00);
						tot2 += rs.getDouble(7);
					}
					balance=tot1-tot2;
					rcp.setVdbcr("Dr");
					if(balance<0)
					{
						balance=balance*-1;
						rcp.setVdbcr("Cr");	
					}
					rcp.setAdvance(balance); // balance
					data.add(rcp);
				}
			}
			
			rcp = new RcpDto();
			rcp.setVou_lo(" ");
			rcp.setVnart1("Total : ");
			rcp.setVamount(tot1);
			rcp.setBill_amt(tot2);
			rcp.setAdvance(balance); // balance
			rcp.setVdbcr("Dr");
			if(balance<0)
			{
				rcp.setVdbcr("Cr");	
			}
			rcp.setDash(1);
			data.add(rcp);
			
/*			if(balance>0)
				tot1+=balance;
			else
				tot2+=(balance*-1);

			rcp = new RcpDto();
			rcp.setVou_lo(" ");
			rcp.setVnart1("Closing Balance on Date : ");
			rcp.setVou_date(wdate);
			rcp.setVamount(0.00);
			rcp.setBill_amt(0.00);
			if ((tot1-tot2) > 0)
				rcp.setBill_amt((tot1-tot2));
			else
				rcp.setVamount(tot2-tot1);
			rcp.setDash(2);
			data.add(rcp);

*/			rs.close();
		    rs1.close();
		    ps1.close();
		   
			
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO CashBook " + ee);
			ee.printStackTrace();
		}

		finally {
			try {
				if (rs != null) {rs.close();}
				if (ps2 != null) {ps2.close();}
				if (rs1 != null) {rs1.close();}
				if (ps1 != null) {ps1.close();}
				if (con != null) {con.close();}
			} catch (SQLException ee) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "+ ee);
			}
		}

		return data;
	}

	
	
	public List getSaleBook(int year, int depo, int div, Date sdate, Date edate,int doctp) 
	{
		List data = null;
		Connection con = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		RcpDto rcp = null;
		double tot1 = 0.00;

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
	
		try {
			con = ConnectionFactory.getConnection();


				 String query2 = " select l.vou_date,l.vou_no,p.mac_name,p.mcity,l.vamount,l.bill_no,l.bill_date from ledfile l, partyfst p "+
			     " where l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd=? and l.vou_date between ? and ? and ifnull(l.del_tag,'')<>'D' " +
			     " and p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code and ifnull(p.del_tag,'')<>'D' order by l.vou_date,l.vou_no " ;
			
			data = new ArrayList();

			ps2 = con.prepareStatement(query2);
			
				ps2.setInt(1, year);
				ps2.setInt(2, div);
				ps2.setInt(3, depo);
				ps2.setInt(4, doctp);
				ps2.setDate(5, new java.sql.Date(sdate.getTime()));
				ps2.setDate(6, new java.sql.Date(edate.getTime()));
				ps2.setInt(7, div);
				ps2.setInt(8, depo);
  			    rs = ps2.executeQuery();
			
			while (rs.next())
			{
				 
					rcp = new RcpDto();
					rcp.setDash(0);
					rcp.setVou_no(rs.getInt(2));
					rcp.setVou_date(rs.getDate(1));
					rcp.setParty_name(rs.getString(3));
					rcp.setCity(rs.getString(4));
					rcp.setVamount(rs.getDouble(5));
					rcp.setBill_no(rs.getString(6));
					rcp.setBill_date(rs.getDate(7));
					tot1 += rs.getDouble(5);
					data.add(rcp);
			}
			rcp = new RcpDto();
			rcp.setVou_no(0);
			rcp.setParty_name("Total : ");
			rcp.setVamount(tot1);
			rcp.setDash(1);
			data.add(rcp);


			rs.close();
			
		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO SalesBook " + ee);
			ee.printStackTrace();
		}

		finally {
			try {
				if (rs != null) {rs.close();}
				if (ps2 != null) {ps2.close();}
				if (con != null) {con.close();}
			} catch (SQLException ee) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "+ ee);
			}
		}

		return data;
	}
	

	public int  closingTrf(int year, int depo, int div, Date sdate, Date edate)
	{
		Connection con = null;
		PreparedStatement ps2 = null;
		int i=0;
		try {
			con = ConnectionFactory.getConnection(); 

		 
			
			String query2 = " update partyacc p, "+
			" (select code,sum(bal) bal from "+
			"(select mac_code code,if(ifnull(mopng_db,'DR')='DR',ifnull(mopng_bal,0.00),(ifnull(mopng_bal,0.00)*-1)) bal  from partyacc "+ 
			" WHERE FIN_YEAR=? AND DIV_CODE=? AND DEPO_CODE=?  and mgrp_code in (45,50,70,80) and ifnull(del_tag,'')<>'D' group by mac_code "+ 
			" UNION ALL "+
			" select vac_code code,SUM(if(vdbcr='DR',vamount,(vamount*-1))) bal  from ledfile WHERE FIN_YEAR=? AND DIV_CODE=? AND VDEPO_CODE=? "+ 
			" AND vou_date between ? and ?  and vgrp_code in (45,50,70,80) and ifnull(del_tag,'')<>'D' group by vac_code ) a "+
			" group by a.code) p1 "+ 
			" set p.mopng_bal=if(p1.bal>=0,p1.bal,(p1.bal*-1)),p.mopng_db=if(p1.bal>=0,'DR','CR') "+ 
			" where p.fin_year=? and p.div_Code=? and p.depo_code=? and p.mac_Code=p1.code ";
			
			

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			
			ps2.setInt(4, year);
			ps2.setInt(5, div);
			ps2.setInt(6, depo);
			ps2.setDate(7, new java.sql.Date(sdate.getTime()));
			ps2.setDate(8, new java.sql.Date(edate.getTime()));

			ps2.setInt(9, (year+1));
			ps2.setInt(10, div);
			ps2.setInt(11, depo);

			ps2.executeUpdate();

		} catch (Exception ee) {
			System.out.println("error ini ub AccountDAO Clsoing TRf " + ee);
			ee.printStackTrace();
		}

		finally {
			try {

				if (ps2 != null) {
					ps2.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ee) {
				System.out.println("-------------Exception in AccountDAO.Connection.close "	+ ee);
				i=-1;
			}
		}

		return i;
	}

	
}
