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

public class RcpDAO 
{
	public int addReceipt(ArrayList list)
	{
		PreparedStatement ps1 = null;
		PreparedStatement ps3 = null;
		Connection con=null;
		int i=0;
		RcpDto rcp=null;
		PreparedStatement oedps=null;
		PreparedStatement ledps=null;
		PreparedStatement ledgerps=null;
		PreparedStatement finps=null;
		ResultSet finrs=null;
 
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			String query1="insert into rcpfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
			"vou_date,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date,chq_amt,bill_no,bill_date,bill_amt," +
			"vstat_cd,varea_cd,vreg_cd,vterr_cd,vdist_cd,vmsr_cd, "+
			"Created_by,Created_date,serialno,fin_year,mnth_code,head_code,inv_no,vouno,mkt_year,cr_no,cr_date,crno,exp_code,doc_type,doc_no,doc_date,doc_year,voudt) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			
			String led="update ledfile set rcp_amt1=rcp_amt1+? where  div_code=? and vdepo_code=? and vbook_cd =? and vac_code=? "+
			" and vou_no=? and vou_date=? " ;
			
			String invtrd="update invtrd set cr_note1='A'  where  div_code=? and depo_code=? and cdnote_tp='D'  and cdprt_cd=? "+
					" and cdnote_no=? and cdnote_dt=? and cdnote_amt=? and ifnull(del_tag,'')<>'D'" ;

			
			String oed="update ledfile set rcp_amt1=rcp_amt1+?+?,interest=interest+?,new3=new3+? where div_code=? and vdepo_code=? and vbook_cd in (80,95,81) and vac_code=? "+
			" and vou_no=? and vou_date=? " ;
			

			
			String ledger="insert into ledfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
			"vou_date,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date,chq_amt," +
			"vstat_cd,varea_cd,vreg_cd,vter_cd,vdist_cd,vmsr_cd, "+
			"Created_by,Created_date,serialno,fin_year,mnth_code,vbook_cd1,mkt_year,bill_amt) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	
			String finyear="select year from yearfil where ? between frdate and todate and typ='F'";
			
			
			
			finps=con.prepareStatement(finyear);

			ps3 = con.prepareStatement(invtrd);
			ps1 = con.prepareStatement(query1);
			oedps = con.prepareStatement(oed);
			ledps = con.prepareStatement(led);
			ledgerps = con.prepareStatement(ledger);

			
			int x=list.size();
			double rcpamt=0.00;
			String indct=" ";
			int fyear=0;
			for (int j=0;j<x;j++)
			{
				rcp = (RcpDto) list.get(j);
				fyear=rcp.getFin_year();
				
				finps.setDate(1, new java.sql.Date(rcp.getBill_date().getTime()));
				finrs=finps.executeQuery();

				if(finrs.next())
					fyear=finrs.getInt(1);
				
				finrs.close();

				
				
				
				rcpamt=rcpamt+rcp.getVamount();
			if (rcp.getVamount()!=0)
			{
				ps1.setInt(1, rcp.getDiv_code());
				ps1.setInt(2, rcp.getVdepo_code());
				ps1.setInt(3, rcp.getVbook_cd());
				ps1.setInt(4, rcp.getVou_yr());
				ps1.setString(5, rcp.getVou_lo());
				ps1.setInt(6, rcp.getVou_no());
				ps1.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ps1.setString(8, rcp.getVac_code());
				ps1.setString(9, rcp.getVnart1());
//				ps1.setString(9, "Received Against Bill");
				ps1.setDouble(10, (rcp.getVamount()+rcp.getDiscount()));
				ps1.setString(11, "CR");
				ps1.setString(12, rcp.getChq_no());
				ps1.setDate(13, new java.sql.Date(rcp.getChq_date().getTime()));
				ps1.setDouble(14, rcp.getChq_amt());
				ps1.setString(15, rcp.getBill_no());
				ps1.setDate(16, new java.sql.Date(rcp.getBill_date().getTime()));
				ps1.setDouble(17, rcp.getBill_amt());
				ps1.setInt(18, rcp.getVstat_cd());
				ps1.setInt(19, rcp.getVarea_cd());
				ps1.setInt(20, rcp.getVreg_cd());
				ps1.setInt(21, rcp.getVter_cd());
				ps1.setInt(22, rcp.getVdist_cd());
				ps1.setInt(23, rcp.getVmsr_cd());
				ps1.setInt(24, rcp.getCreated_by());
				ps1.setDate(25, new java.sql.Date(rcp.getCreated_date().getTime()));
				ps1.setInt(26, 0);
				ps1.setInt(27, rcp.getFin_year());
				ps1.setInt(28, rcp.getMnth_code());
				ps1.setString(29, rcp.getVac_code());
				ps1.setInt(30, rcp.getInv_no());
				ps1.setInt(31, rcp.getInv_no());
				ps1.setInt(32, rcp.getMkt_year());
				if (rcp.getBill_no().substring(3,4).equals("D"))
				{
					ps1.setString(15, rcp.getCn_no());
					ps1.setDate(16, new java.sql.Date(rcp.getCn_date().getTime()));
					ps1.setInt(31, rcp.getTaxcode());

					
					ps1.setString(33, rcp.getBill_no());
					ps1.setDate(34, new java.sql.Date(rcp.getBill_date().getTime()));
					ps1.setInt(35, rcp.getInv_no());



				}
				else
				{
					ps1.setString(33, "");
					ps1.setDate(34, null);
					ps1.setInt(35, 0);
					
				}
				ps1.setString(36, "");
				if (rcp.getDue_Date()==null)
					ps1.setString(36, "D");
					
				else if (rcp.getVou_date().before(rcp.getDue_Date()) || rcp.getVou_date().equals(rcp.getDue_Date()))
						ps1.setString(36, "D");
				
				
				ps1.setInt(37, rcp.getVbook_cd1());	
				ps1.setInt(38, rcp.getInv_no());	
				ps1.setDate(39, new java.sql.Date(rcp.getBill_date().getTime()));	
				ps1.setInt(40, fyear);	
				ps1.setDate(41, new java.sql.Date(rcp.getBill_date().getTime()));
				
				
				i= ps1.executeUpdate();
				
				/// invtrd updation
				if (rcp.getVbook_cd1()==95)
				{
					ps3.setInt(1,rcp.getDiv_code());
					ps3.setInt(2, rcp.getVdepo_code());
					ps3.setString(3,rcp.getVac_code());
					ps3.setInt(4, rcp.getInv_no());	
					ps3.setDate(5, new java.sql.Date(rcp.getBill_date().getTime()));	
					ps3.setDouble(6, rcp.getVamount());
					i=ps3.executeUpdate();

				}

				
				
			}	
				if(rcp.getDiscount()!=0)
				{
					ps1.setInt(1, rcp.getDiv_code());
					ps1.setInt(2, rcp.getVdepo_code());
					ps1.setInt(3, rcp.getVbook_cd());
					ps1.setInt(4, rcp.getVou_yr());
					ps1.setString(5, rcp.getVou_lo());
					ps1.setInt(6, rcp.getVou_no());
					ps1.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					ps1.setString(8, rcp.getVac_code());
					ps1.setString(9, "Discount");
					ps1.setDouble(10, rcp.getDiscount());
					ps1.setString(11, "DR");
					ps1.setString(12, rcp.getChq_no());
					ps1.setDate(13, new java.sql.Date(rcp.getChq_date().getTime()));
					ps1.setDouble(14, rcp.getChq_amt());
					ps1.setString(15, rcp.getBill_no());
					ps1.setDate(16, new java.sql.Date(rcp.getBill_date().getTime()));
					ps1.setDouble(17, rcp.getBill_amt());
					ps1.setInt(18, rcp.getVstat_cd());
					ps1.setInt(19, rcp.getVarea_cd());
					ps1.setInt(20, rcp.getVreg_cd());
					ps1.setInt(21, rcp.getVter_cd());
					ps1.setInt(22, rcp.getVdist_cd());
					ps1.setInt(23, rcp.getVmsr_cd());
					ps1.setInt(24, rcp.getCreated_by());
					ps1.setDate(25, new java.sql.Date(rcp.getCreated_date().getTime()));
					ps1.setInt(26, 0);
					ps1.setInt(27, rcp.getFin_year());
					ps1.setInt(28, rcp.getMnth_code());
					ps1.setString(29, rcp.getDisc_code());
					ps1.setInt(30, rcp.getInv_no());
					ps1.setInt(31, rcp.getInv_no());
					ps1.setInt(32, rcp.getMkt_year());
					ps1.setString(33, "");
					ps1.setDate(34, null);
					ps1.setInt(35, 0);
					ps1.setString(36, "");
					
					ps1.setInt(37, rcp.getVbook_cd1());	
					ps1.setInt(38, rcp.getInv_no());	
					ps1.setDate(39, new java.sql.Date(rcp.getBill_date().getTime()));	
					ps1.setInt(40, fyear);	
					ps1.setDate(41, new java.sql.Date(rcp.getBill_date().getTime()));
					i= ps1.executeUpdate();
					
				}


				
				if(rcp.getInterest()!=0)
				{
					ps1.setInt(1, rcp.getDiv_code());
					ps1.setInt(2, rcp.getVdepo_code());
					ps1.setInt(3, rcp.getVbook_cd());
					ps1.setInt(4, rcp.getVou_yr());
					ps1.setString(5, rcp.getVou_lo());
					ps1.setInt(6, rcp.getVou_no());
					ps1.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					ps1.setString(8, rcp.getVac_code());
					ps1.setString(9, "Interest");
					ps1.setDouble(10, rcp.getInterest());
					ps1.setString(11, "CR");
					ps1.setString(12, rcp.getChq_no());
					ps1.setDate(13, new java.sql.Date(rcp.getChq_date().getTime()));
					ps1.setDouble(14, rcp.getChq_amt());
					ps1.setString(15, rcp.getBill_no());
					ps1.setDate(16, new java.sql.Date(rcp.getBill_date().getTime()));
					ps1.setDouble(17, rcp.getBill_amt());
					ps1.setInt(18, rcp.getVstat_cd());
					ps1.setInt(19, rcp.getVarea_cd());
					ps1.setInt(20, rcp.getVreg_cd());
					ps1.setInt(21, rcp.getVter_cd());
					ps1.setInt(22, rcp.getVdist_cd());
					ps1.setInt(23, rcp.getVmsr_cd());
					ps1.setInt(24, rcp.getCreated_by());
					ps1.setDate(25, new java.sql.Date(rcp.getCreated_date().getTime()));
					ps1.setInt(26, 0);
					ps1.setInt(27, rcp.getFin_year());
					ps1.setInt(28, rcp.getMnth_code());
					ps1.setString(29, rcp.getInt_code());
					ps1.setInt(30, rcp.getInv_no());
					ps1.setInt(31, rcp.getInv_no());
					ps1.setInt(32, rcp.getMkt_year());
					ps1.setString(33, "");
					ps1.setDate(34, null);
					ps1.setInt(35, 0);
					ps1.setString(36, "");
					ps1.setInt(37, rcp.getVbook_cd1());	
					ps1.setInt(38, rcp.getInv_no());	
					ps1.setDate(39, new java.sql.Date(rcp.getBill_date().getTime()));	
					ps1.setInt(40, fyear);	
					ps1.setDate(41, new java.sql.Date(rcp.getBill_date().getTime()));

					rcpamt=rcpamt+rcp.getInterest();
					i= ps1.executeUpdate();
					
					
				}

				
				
			if (rcp.getVamount()!=0)
			{
			    oedps.setDouble(1, rcp.getVamount());
			    oedps.setDouble(2, rcp.getDiscount());
			    oedps.setDouble(3, rcp.getInterest());
			    oedps.setDouble(4, rcp.getVamount());
			    // where clause
				oedps.setInt(5,rcp.getDiv_code());
			    oedps.setInt(6, rcp.getVdepo_code());
				oedps.setString(7,rcp.getVac_code());
				oedps.setInt(8, rcp.getInv_no());
				oedps.setDate(9, new java.sql.Date(rcp.getBill_date().getTime()));
				oedps.executeUpdate();
			}
				
			}  // end of for loop
			
			
			// TODO ADVANCE GENERATAION
			if(rcp.getAdvance()!=0)
			{
				ps1.setInt(1, rcp.getDiv_code());
				ps1.setInt(2, rcp.getVdepo_code());
				ps1.setInt(3, 99);
				ps1.setInt(4, rcp.getVou_yr());
				ps1.setString(5, rcp.getVou_lo());
				ps1.setInt(6, rcp.getVou_no());
				ps1.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ps1.setString(8, rcp.getVac_code());
				ps1.setString(9, "Advance");
				ps1.setDouble(10, rcp.getAdvance());
				ps1.setString(11, "CR");
				ps1.setString(12, rcp.getChq_no());
				ps1.setDate(13, new java.sql.Date(rcp.getChq_date().getTime()));
				ps1.setDouble(14, rcp.getChq_amt());
				ps1.setString(15, "");
				ps1.setDate(16, null);
				ps1.setDouble(17, 0.00);
				ps1.setInt(18, rcp.getVstat_cd());
				ps1.setInt(19, rcp.getVarea_cd());
				ps1.setInt(20, rcp.getVreg_cd());
				ps1.setInt(21, rcp.getVter_cd());
				ps1.setInt(22, rcp.getVdist_cd());
				ps1.setInt(23, rcp.getVmsr_cd());
				ps1.setInt(24, rcp.getCreated_by());
				ps1.setDate(25, new java.sql.Date(rcp.getCreated_date().getTime()));
				ps1.setInt(26, 0);
				ps1.setInt(27, rcp.getFin_year());
				ps1.setInt(28, rcp.getMnth_code());
				ps1.setString(29, rcp.getVac_code());
				ps1.setInt(30, rcp.getInv_no());
				ps1.setInt(31, rcp.getInv_no());
				ps1.setInt(32, rcp.getMkt_year());
				ps1.setString(33, "");
				ps1.setDate(34, null);
				ps1.setInt(35, 0);
				ps1.setString(36, "");
				ps1.setInt(37, rcp.getVbook_cd());	
				ps1.setInt(38, rcp.getVou_no());	
				ps1.setDate(39, new java.sql.Date(rcp.getVou_date().getTime()));	
				ps1.setInt(40, fyear);	
				ps1.setDate(41, new java.sql.Date(rcp.getBill_date().getTime()));

				rcpamt=rcpamt+rcp.getAdvance();
				i= ps1.executeUpdate();
				
				
				
				// Advacne REcord for JVI Entry 
				ledgerps.setInt(1, rcp.getDiv_code());
				ledgerps.setInt(2, rcp.getVdepo_code());
				ledgerps.setInt(3, 99);
				ledgerps.setInt(4, rcp.getVou_yr());
				ledgerps.setString(5, rcp.getVou_lo());
				ledgerps.setInt(6, rcp.getVou_no());
				ledgerps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ledgerps.setString(8, rcp.getVac_code());
				ledgerps.setString(9, "Advance");
				ledgerps.setDouble(10, rcp.getAdvance());
				ledgerps.setString(11, "CR");
				ledgerps.setString(12, rcp.getChq_no());
				ledgerps.setDate(13, new java.sql.Date(rcp.getChq_date().getTime()));
				ledgerps.setDouble(14, rcp.getChq_amt());
				ledgerps.setInt(15, rcp.getVstat_cd());
				ledgerps.setInt(16, rcp.getVarea_cd());
				ledgerps.setInt(17, rcp.getVreg_cd());
				ledgerps.setInt(18, rcp.getVter_cd());
				ledgerps.setInt(19, rcp.getVdist_cd());
				ledgerps.setInt(20, rcp.getVmsr_cd());
				ledgerps.setInt(21, rcp.getCreated_by());
				ledgerps.setDate(22, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledgerps.setInt(23, 0);
				ledgerps.setInt(24, rcp.getFin_year());
				ledgerps.setInt(25, rcp.getMnth_code());
				ledgerps.setInt(26, 99);
				ledgerps.setInt(27, rcp.getMkt_year());
				ledgerps.setDouble(28, rcp.getAdvance());
				i= ledgerps.executeUpdate();
				
				
			}			
			

			if (x>0)
			{
				
				
			    ledps.setDouble(1, rcpamt);

			    
			    // where clause
				ledps.setInt(2,rcp.getDiv_code());
			    ledps.setInt(3, rcp.getVdepo_code());
				ledps.setInt(4,rcp.getVbook_cd());
				ledps.setString(5,rcp.getVac_code());
				ledps.setInt(6, rcp.getVou_no());
				ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps.executeUpdate();
				
				
				 ledps.setDouble(1, rcpamt);
				    // where clause
				 ledps.setInt(2,rcp.getDiv_code());
				 ledps.setInt(3, rcp.getVdepo_code());
				 ledps.setInt(4,97);
				 ledps.setString(5,rcp.getVac_code());
				 ledps.setInt(6, rcp.getVou_no());
				 ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				 ledps.executeUpdate();
				
			}

       
			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			ps3.close();
			oedps.close();
			ledps.close();
			ledgerps.close();
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
				if(ps3 != null){ps3.close();}
				if(oedps != null){oedps.close();}
				if(ledps != null){ledps.close();}
				if(ledgerps != null){ledgerps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}	


	
	public int addLed(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		
		
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			

			String led="insert into ledfile (vdepo_code,vbook_cd,vou_no, " +
			"vou_date,vac_code,vnart_1,vamount,vdbcr," +
			"Created_by,Created_date,fin_year,receiver_name,mobile_no,remark,chq_amt,paid_by,rcp_no,rcp_date,div_code)" +
			" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


			ledps=con.prepareStatement(led);
			ledps.setInt(1, rcp.getVdepo_code());
			ledps.setInt(2, rcp.getVbook_cd());
			ledps.setInt(3, rcp.getVou_no());
			ledps.setDate(4, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setString(5, rcp.getVac_code());
			ledps.setString(6, "Advance");
			ledps.setDouble(7, rcp.getVamount());
			ledps.setString(8, rcp.getVdbcr());
			ledps.setInt(9, rcp.getCreated_by());
			ledps.setTimestamp(10, new java.sql.Timestamp(rcp.getCreated_date().getTime()));
			ledps.setInt(11, rcp.getFin_year());
			ledps.setString(12, rcp.getVnart1());
			ledps.setString(13, rcp.getVnart2());
			ledps.setString(14, rcp.getRcp_no());
			ledps.setDouble(15, rcp.getBill_amt());
			ledps.setString(16, rcp.getBank_acno());
			ledps.setString(17, String.valueOf(rcp.getVou_no()));
			ledps.setDate(18, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setInt(19, rcp.getDiv_code());

			i=ledps.executeUpdate();



			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			
		} catch (SQLException ex) {ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.addLed " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	
	//TODO Method to update Voucher Entry //////////////////////
	public int updateLed(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			

			String led="update ledfile set vou_date=?,vac_code=?,receiver_name=?,vamount=?," +
			"Modified_by=?,modified_date=?,mobile_no=?,remark=?,paid_by=? " +
			"where fin_year=? and vdepo_code=? and div_code=? and vbook_cd=? and vou_no=? and vdbcr=? ";


			ledps=con.prepareStatement(led);
			
			ledps.setDate(1, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setString(2, rcp.getVac_code());
			ledps.setString(3, rcp.getVnart1());
			ledps.setDouble(4, rcp.getVamount());
			ledps.setInt(5, rcp.getModified_by());
			ledps.setTimestamp(6, new java.sql.Timestamp(rcp.getModified_date().getTime()));
			ledps.setString(7, rcp.getVnart2());
			ledps.setString(8, rcp.getRcp_no());
			ledps.setString(9, rcp.getBank_acno());
			
			// where clause
			ledps.setInt(10, rcp.getFin_year());
			ledps.setInt(11, rcp.getVdepo_code());
			ledps.setInt(12, rcp.getDiv_code());
			ledps.setInt(13, rcp.getVbook_cd());
			ledps.setInt(14, rcp.getVou_no());
			ledps.setString(15, rcp.getVdbcr());
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
			System.out.println("-------------Exception in SQLRcpDAO.updateLed for voucher update " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	

	//TODO Method to delete Voucher Entry //////////////////////
	public int deleteLed(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			

			String led="update ledfile set del_tag=?,Modified_by=?,modified_date=? " +
			"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? and vou_no=? and vdbcr=? and ifnull(del_tag,'N')<>'D'";

			
			
			ledps=con.prepareStatement(led);
			
			// ledfile delete vbook_cd=22 
			ledps.setString(1, "D");
			ledps.setInt(2, rcp.getModified_by());
			ledps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));
			ledps.setInt(4, rcp.getFin_year());
			ledps.setInt(5, rcp.getDiv_code());
			ledps.setInt(6, rcp.getVdepo_code());
			ledps.setInt(7, rcp.getVbook_cd());
			ledps.setInt(8, rcp.getVou_no());
			ledps.setString(9, rcp.getVdbcr());
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
			System.out.println("-------------Exception in SQLRcpDAO.deleteLed for voucher delete " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
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
		int subdiv=0;
		String wcode="";
		String query1=null;
		RcpDto rcp=null;
	    ArrayList data=null;
	    String code=null;
	    try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			code="040"+depo+"01";
			
			System.out.println("value of div "+div);
			
			if (div==41 || div==51)
			{   
				div=4;
				subdiv=1;
			}	

			if (div==42 || div==52)
			{   
				div=4;
				subdiv=2;
			}	

			if (div==43 || div==53)
			{   
				div=4;
				subdiv=3;
			}	

			
			if (div==4)
			{
				query1 ="select f.vou_yr,f.vou_lo,f.vou_no,f.vou_date,f.vac_code,f.name," +
				" f.vnart_1,f.vamount,ifnull(f.chq_no,''),f.chq_date,ifnull(f.chq_amt,f.vamount),ifnull(f.bill_no,''),f.bill_date,ifnull(f.bill_amt,0),f.vdbcr,f.vac_code,f.vbook_cd1 from ledfile as f "+
				" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
				" f.vbook_cd1=? and f.vou_no between ? and ? and f.sub_div=? and f.vdbcr='CR' and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			}
			else
			{
				if (depo==50)
				{
				query1=" select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,if(r.div_code=4,r.vac_code,r.head_code),concat(p.mac_name,',',p.mcity),left(r.vnart_1,24),r.vamount,ifnull(r.chq_no,''),r.chq_date,ifnull(r.chq_amt,0),concat(left(r.bill_no,3),right(r.bill_no,5)),r.bill_date,r.bill_amt,r.vdbcr,r.vac_code,r.vbook_cd  "+ 
				" from rcpfile as r, partyfst as p where r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in (?,?) "+ 
				" and  r.vou_no between ? and ? and r.vac_code=p.mac_code and ifnull(r.del_tag,'')<>'D' and r.vamount<>0 and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' order by r.vou_no,r.vbook_cd,r.inv_no,r.head_code desc ";
				}
	
				else
				{
					query1=" select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,if(r.div_code=4,r.vac_code," +
					"r.head_code),concat(p.mac_name,',',p.mcity),left(r.vnart_1,24),r.vamount,ifnull(r.chq_no,'')," +
					"r.chq_date,ifnull(r.chq_amt,0),concat(left(r.bill_no,3),right(r.bill_no,5))," +
					"r.bill_date,r.bill_amt,r.vdbcr,r.vac_code,r.vbook_cd  "+ 
					" from rcpfile as r, partyfst as p where r.fin_year=? and r.div_code=? and " +
					"r.vdepo_code=? and r.vbook_cd in (?,?) "+ 
					" and  r.vou_no between ? and ? and r.vac_code=p.mac_code and ifnull(r.del_tag,'')<>'D' and r.vamount<>0 and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' order by r.vou_no,r.vbook_cd,r.inv_no,r.head_code desc ";
				}

				if (sinv==0)
				{
					if (depo==50)
					{
					query1=" select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,if(r.div_code=4,r.vac_code,r.head_code),concat(p.mac_name,',',p.mcity),left(r.vnart_1,24),r.vamount,ifnull(r.chq_no,''),r.chq_date,ifnull(r.chq_amt,0),concat(left(r.bill_no,3),right(r.bill_no,5)),r.bill_date,r.bill_amt,r.vdbcr,r.vac_code,r.vbook_cd  "+ 
					" from rcpfile as r, partyfst as p where r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in (?,?) "+ 
					" and  r.vou_no "+BaseClass.commaSepratedInvoiceNo+" and r.vac_code=p.mac_code and ifnull(r.del_tag,'')<>'D' and r.vamount<>0 and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' order by r.vou_no,r.vbook_cd,r.inv_no,r.head_code desc ";
					}
					else
					{
					query1=" select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,if(r.div_code=4,r.vac_code,r.head_code),concat(p.mac_name,',',p.mcity),left(r.vnart_1,24),r.vamount,ifnull(r.chq_no,''),r.chq_date,ifnull(r.chq_amt,0),concat(left(r.bill_no,3),right(r.bill_no,5)),r.bill_date,r.bill_amt,r.vdbcr,r.vac_code,r.vbook_cd  "+ 
					" from rcpfile as r, partyfst as p where r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in (?,?) "+ 
					" and  r.vou_no "+BaseClass.commaSepratedInvoiceNo+" and r.vac_code=p.mac_code and ifnull(r.del_tag,'')<>'D' and r.vamount<>0 and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' order by r.vou_no,r.vbook_cd,r.inv_no,r.head_code desc ";
					}
				}
			}

			


			
			ps1 = con.prepareStatement(query1);
			if (div==4)
			{
				ps1.setInt(1,year);
				ps1.setInt(2,div);
				ps1.setInt(3, depo);
				ps1.setInt(4,bkcd); 
				ps1.setInt(5,sinv); 
				ps1.setInt(6,einv); 
				ps1.setInt(7,subdiv); 
				System.out.println("yeha aaya kya..."+year+" "+div+" "+depo+" "+bkcd+" "+sinv+" "+einv+" "+subdiv);
				rs=ps1.executeQuery();

			}
			
			else
			{
				if (sinv==0)
				{
					ps1.setInt(1, year);
					ps1.setInt(2, div);
					ps1.setInt(3, depo);
					ps1.setInt(4, bkcd);
					ps1.setInt(5, 99);
					ps1.setInt(6, div);
					ps1.setInt(7, depo);
					rs=ps1.executeQuery();

				}
				else
				{
					ps1.setInt(1, year);
					ps1.setInt(2, div);
					ps1.setInt(3, depo);
					ps1.setInt(4, bkcd);
					ps1.setInt(5, 99);
					ps1.setInt(6, sinv);
					ps1.setInt(7, einv);
					ps1.setInt(8, div);
					ps1.setInt(9, depo);
					rs=ps1.executeQuery();
				}
	    	}
			
			String inv = null;
			data = new ArrayList();
			 double rgross=0.00;
			 boolean first=true;
			 int sno=0;
			while(rs.next())
			{

				
				System.out.println("chq no "+rs.getString(9)+" amount "+rs.getDouble(11));

				if (first)
				{
					sno=rs.getInt(3);
					first=false;
					wcode=rs.getString(16);
				}
					if (rs.getInt(3)!=sno)
					{
						rcp= new RcpDto();
						rcp.setDash(1);
						rcp.setVamount(rgross);
			            data.add(rcp);
			            rgross=0.00;
						sno=rs.getInt(3);
						wcode=rs.getString(16);
					}

					if (rs.getString(16).equalsIgnoreCase(wcode))
					{
	//				inv = rs.getString(2).charAt(0)+rs.getString(2).replace(rs.getString(2).charAt(0), 'R')+rs.getString(3).substring(1);
					inv = rs.getString(2).charAt(0)+"R"+rs.getString(2).charAt(1)+rs.getString(3).substring(1);
					rcp= new RcpDto();
					rcp.setVou_lo(inv);
					rcp.setVou_no(rs.getInt(3));
					rcp.setVou_date(rs.getDate(4));
					rcp.setVac_code(rs.getString(5));
					rcp.setParty_name(rs.getString(6));
				
					rcp.setVnart1(rs.getString(7));
					rcp.setVamount(rs.getDouble(8));
					rcp.setChq_no(rs.getString(9));
					rcp.setChq_date(rs.getDate(4));
					if (rs.getDate(10)!=null)
						rcp.setChq_date(rs.getDate(10));
	                rcp.setChq_amt(rs.getDouble(11));
	                if (rs.getInt(17)==99)
	                {
		                rcp.setBill_no(null);
		                rcp.setBill_date(null);
	                	
	                }
	                else
	                {
	                rcp.setBill_no(rs.getString(12));
	                rcp.setBill_date(rs.getDate(13));
	                }
	                if (rs.getString(15).equals("CR"))
	                {
	//                    rcp.setBill_amt(rs.getDouble(14));
	//                	rgross+=rs.getDouble(14);
	                	
	                    rcp.setBill_amt(rs.getDouble(8));
	                	rgross+=rs.getDouble(8);
	
	                }
	                if (rs.getString(15).equals("DR"))
	                {
	                    rcp.setBill_amt(rs.getDouble(8));
	                	rgross-=rs.getDouble(8);
	                }
	    			rcp.setDash(0);
	    			rcp.setDiv_code(div);
	                data.add(rcp);
				} 
			}   // end of while
			rcp= new RcpDto();
			rcp.setDash(1);
			rcp.setVamount(rgross);
			rcp.setDiv_code(div);
            data.add(rcp);
            rs.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
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
				if(rs != null){rs.close();}
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
			

			String query2=" select r.vou_yr,ifnull(r.vou_lo,'**'),r.vou_no,r.vou_date,r.vac_code,concat(p.mac_name,',',p.mcity),r.vnart_1,r.vamount,r.chq_no,r.chq_date,(r.vamount-r.rcp_amt1) as bal,r.vnart_2  "+ 
			" from ledfile as r, partyfst as p where r.div_code=? and r.vdepo_code=? and r.vbook_cd=? "+ 
			" and r.vou_date=? and (r.vamount-r.rcp_amt1)>0 and ifnull(r.del_tag,'')<>'D' and r.vac_code=p.mac_code and p.div_code=? and p.depo_code=? " +
			" order by r.vou_date,r.vou_no ";

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setInt(3, bkcd);
			ps2.setDate(4, new java.sql.Date(dt.getTime()));
			ps2.setInt(5, div);
			ps2.setInt(6, depo);
			
			rs= ps2.executeQuery();

			RcpDto rcp=null;
			v = new Vector();
			String inv=null;
			while (rs.next())
			{
				col= new Vector();
				
				if(rs.getString(2).length()<2)
					inv="**"+String.format("%05d",rs.getInt(3));
				else
					inv = rs.getString(2).charAt(0)+"R"+rs.getString(2).charAt(1)+String.format("%05d",rs.getInt(3));
//				inv = rs.getString(2).charAt(0)+rs.getString(2).replace(rs.getString(2).charAt(0), 'R')+rs.getString(3).substring(1);
				col.addElement(inv);
				col.addElement(rs.getString(6));

				rcp= new RcpDto();
				rcp.setVou_lo(inv);
				rcp.setVou_no(rs.getInt(3));
				rcp.setVou_date(rs.getDate(4));
				rcp.setVac_code(rs.getString(5));
				rcp.setParty_name(rs.getString(6));
				rcp.setVnart1(rs.getString(7));
				rcp.setVnart2(rs.getString(12));
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
			ex.printStackTrace();
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
	

	// TODO Method used to search Voucher by Name or by slip Date in VouAdd
	public Vector getVouList(int year,int div,int depo,int bkcd,int slno,String date,String vdbcr)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null; 
		SimpleDateFormat sf= null;
		String query2=null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sf=new SimpleDateFormat("dd/MM/yyyy");

			if(slno==0)
			{
				query2=" select r.vou_no,r.vou_date,r.vac_code," +
				"concat(p.mac_name,',',p.mcity),r.receiver_name,r.vamount,mobile_no,remark "+ 
				" from ledfile as r, accountmaster as p where  r.vdepo_code=? and r.vbook_cd=? "+ 
				" and r.vou_date=? and p.depo_code=? and r.vac_code=p.account_no" +
				" and r.vdbcr=? and ifnull(r.del_tag,'')<>'D' order by r.vou_no ";
			}
			else
			{
				query2=" select r.vou_no,r.vou_date,r.vac_code," +
				"concat(p.mac_name,',',p.mcity),r.receiver_name,r.vamount,mobile_no,remark "+ 
				" from ledfile as r, partyfst as p where r.fin_year=?  and r.vdepo_code=? and r.vbook_cd=? "+ 
				" and p.depo_code=? and r.vac_code=p.mac_code " +
				" and r.vdbcr=? and ifnull(r.del_tag,'')<>'D' order by r.vou_no ";
				
			}

			
			ps2 = con.prepareStatement(query2);
			if(slno==0)
			{
				ps2.setInt(1, depo);
				ps2.setInt(2, bkcd);
				ps2.setDate(3, new java.sql.Date(sf.parse(date).getTime()));
				ps2.setInt(4, depo);
				ps2.setString(5,vdbcr);
			}
			else
			{
				ps2.setInt(1, year);
				ps2.setInt(2, depo);
				ps2.setInt(3, bkcd);
				ps2.setInt(4, slno);
				ps2.setInt(5, depo);
				ps2.setString(6,vdbcr);
			}

			rs= ps2.executeQuery();

			RcpDto rcp=null;
			v = new Vector();
			String inv=null;
			while (rs.next())
			{
				col= new Vector();
			    inv=rs.getString(3);
				 
				col.addElement(rs.getInt(1));
				col.addElement(rs.getString(4));

				rcp= new RcpDto();
				rcp.setVou_lo(inv);
				rcp.setVou_no(rs.getInt(1));
				rcp.setVou_date(rs.getDate(2));
				rcp.setVac_code(rs.getString(3));
				rcp.setParty_name(rs.getString(4));
				rcp.setVnart1(rs.getString(5));
				rcp.setVamount(rs.getDouble(6));
				rcp.setVnart2(rs.getString(7));
				rcp.setRcp_no(rs.getString(8));
                col.addElement(rcp);
				v.addElement(col);

			}
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (Exception ex) {  
			ex.printStackTrace();
			System.out.println("yaha kuch error hai aaaaaa   ");
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.vouList " + ex);
		}
		finally {
			try {
//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return v;
	}	
	
	
	// TODO Method used to search Voucher by voucher no
		public RcpDto getVouList(int year,int div,int depo,int bkcd,int vouno,String vdbcr)
		{
			PreparedStatement ps2 = null;
			ResultSet rs=null;

			Connection con=null;
			RcpDto rcp=null;
			String query2=null;
			String query3=null;
			
			try 
			{
				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);


				query2=" select r.vou_no,r.vou_date,r.vac_code," +
				"concat(p.mac_name,',',p.mcity),r.receiver_name,r.vamount,r.mobile_no,r.remark,r.paid_by "+ 
				" from ledfile as r, accountmaster as p where r.fin_year=? and r.vdepo_code=? and r.vbook_cd in(?,20) "+ 
				" and r.vou_no=?  and p.depo_code=? and r.vac_code=p.account_no and r.vdbcr=? and ifnull(r.del_tag,'')<>'D' ";
					

				
				
				
				
				ps2 = con.prepareStatement(query2);
				ps2.setInt(1, year);
				ps2.setInt(2, depo);
				ps2.setInt(3, 10);
				ps2.setInt(4, vouno);
				ps2.setInt(5, depo);
				ps2.setString(6, "CR");

				rs= ps2.executeQuery();

				if (rs.next())
				{

					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(1));
					rcp.setVou_date(rs.getDate(2));
					rcp.setVac_code(rs.getString(3));
					rcp.setParty_name(rs.getString(4));
					rcp.setVnart1(rs.getString(5));
					rcp.setVamount(rs.getDouble(6));
					rcp.setVnart2(rs.getString(7));
					rcp.setRcp_no(rs.getString(8));
					rcp.setBank_acno(rs.getString(9));
				}
				con.commit();
				con.setAutoCommit(true);
				rs.close();
				ps2.close();

			} catch (Exception ex) {  System.out.println("yaha kuch error hai aaaaaa   ") ;
					ex.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.vouList " + ex);
			}
			finally {
				try {
//					System.out.println("No. of Records Update/Insert : "+i);

					if(rs != null){rs.close();}
					if(ps2 != null){ps2.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}

			return rcp;
		}	
			

		
		// TODO Method used to get Detail for Cheque Return Voucher by voucher no
		public RcpDto getChequeReturn(int year,int div,int depo,int bkcd,int vouno)
		{
			PreparedStatement ps2 = null;
			ResultSet rs=null;
			Connection con=null;
			RcpDto rcp=null;
			SimpleDateFormat sf= null;
			NumberFormat nf = null;
			String query2=null;

			PreparedStatement ps3 = null;
			ResultSet rs3=null;

			String query3=null;
			boolean result=false;
			
			try 
			{
				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);

				sf=new SimpleDateFormat("dd/MM/yyyy");
				nf= new DecimalFormat("0.00");
				
				query2=" select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,r.vac_code," +
				"concat(p.mac_name,',',p.mcity),r.vnart_1,r.vamount,r.chq_no,r.chq_date,r.vnart_2,r.serialno "+ 
				" from ledfile as r, partyfst as p where r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd=? "+ 
				" and r.vou_no=?  and p.div_code=? and p.depo_code=? and r.vac_code=p.mac_code and ifnull(r.del_tag,'')<>'D' ";

				query3=" select bill_no,bill_date,bill_amt,inv_no,vac_code " +
				" from rcpfile  where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=?  and head_code=? "+ 
				" and vou_no=? and ifnull(del_tag,'')<>'D' and ifnull(inv_lo,'')<>'C' "+
				" union all "+
				" select bill_no,bill_date,bill_amt,inv_no,vac_code " +
				" from rcpfile  where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=?  and head_code=? "+ 
				" and crno=? and ifnull(del_tag,'')<>'D' and ifnull(inv_lo,'')<>'C' ";
				
				//and ifnull(inv_lo,'')<>'C'
				ps3 = con.prepareStatement(query3);

				
				ps2 = con.prepareStatement(query2);
				ps2.setInt(1, year);
				ps2.setInt(2, div);
				ps2.setInt(3, depo);
				ps2.setInt(4, bkcd);
				ps2.setInt(5, vouno);
				ps2.setInt(6, div);
				ps2.setInt(7, depo);

				rs= ps2.executeQuery();
				Vector v = new Vector();
				Vector col =null;
				double tot=0.00;
				if (rs.next())
				{

					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(3));
					rcp.setVou_date(rs.getDate(4));
					rcp.setVac_code(rs.getString(5));
					rcp.setParty_name(rs.getString(6));
					rcp.setVnart1(rs.getString(7));
					rcp.setVamount(rs.getDouble(8));
					rcp.setChq_no(rs.getString(9));
					rcp.setChq_date(rs.getDate(10));
					rcp.setVnart2(rs.getString(11));
					rcp.setSerialno(rs.getInt(12));
					
					ps3.setInt(1, year);
					ps3.setInt(2, div);
					ps3.setInt(3, depo);
					ps3.setInt(4, bkcd);
					ps3.setString(5, rs.getString(5));
					ps3.setInt(6, vouno);
					ps3.setInt(7, year);
					ps3.setInt(8, div);
					ps3.setInt(9, depo);
					ps3.setInt(10, 98);
					ps3.setString(11, rs.getString(5));
					ps3.setInt(12, vouno);

					rs3= ps3.executeQuery();
					//result=rs3.next(); 
					while(rs3.next())
					{ 
						result=true;
						col = new Vector();
						col.addElement(rs3.getString(1));  // 01
						
						if(rs3.getDate(2)!=null)
						    col.addElement(sf.format(rs3.getDate(2))); //1
						else
							col.addElement("__/__/____"); //1
						
						col.addElement(nf.format(rs3.getDouble(3))); //2
						col.addElement(rs3.getInt(4)); //3
						v.add(col);
						tot+=rs3.getDouble(3);
					}
					if(rs.getDouble(8)> tot)
						rcp.setInterest(rs.getDouble(8)-tot);
					else
						rcp.setDiscount(tot-rs.getDouble(8));
						
						rcp.setVdetail(v);
						
					rs3.close();
					ps3.close();


				}
				rs.close();
				if(!result)
				{
					ps2.setInt(1, year); 
					ps2.setInt(2, div);
					ps2.setInt(3, depo);
					ps2.setInt(4, 99);
					ps2.setInt(5, vouno);
					ps2.setInt(6, div);
					ps2.setInt(7, depo);

					rs= ps2.executeQuery();
					if(rs.next())
					{

					}
					else
					rcp=null;
				}
				con.commit();
				con.setAutoCommit(true);
				rs.close();
				ps2.close();
				
				

			} catch (Exception ex) {  System.out.println("yaha kuch error hai aaaaaa   ");
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.ChequeReturn " + ex);
			}
			finally {
				try {
//					System.out.println("No. of Records Update/Insert : "+i);

					if(rs != null){rs.close();}
					if(ps2 != null){ps2.close();}
					if(rs3 != null){rs3.close();}
					if(ps3 != null){ps3.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}

			return rcp;
		}	
					
	
		public int deleteCheckReturn(RcpDto rcp, int fyear)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;
			PreparedStatement ledupdps=null;
			PreparedStatement ledupdps1=null;
			PreparedStatement rcpps=null;
			PreparedStatement ps2=null;
			PreparedStatement ledps1 =null;
			PreparedStatement invtrd =null;
			ResultSet rs1=null;
			ResultSet rs=null;
			int sub=0;
			
			try {

			
//				sub=rcp.getDiv_code()-1;
				sub=rcp.getDiv_code();
							
				
				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				

				String ledfile1="select vac_code,inv_no,bill_date,bill_amt,vamount from rcpfile "+
				"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? "+
				"and vou_no=? and ifnull(del_tag,'N')<>'D' "; 

				String UpdLedfile="update ledfile set rcp_amt1 = rcp_amt1-? "+ 
				"where div_code=? and vdepo_code=? and vbook_cd=? and vac_code =? and vou_no = ? and "+
				"vou_date = ? and ifnull(del_tag,'N')<>'D'";

				String UpdInvtrd="update invtrd set cr_note1 ='' "+ 
				"where div_code=? and depo_code=? and doc_type=40 and cdprt_cd =? and cdinv_no = ? and "+
				"cdinv_dt = ? and cdnote_tp='D' and ifnull(del_tag,'N')<>'D'";

				
				
				String UpdAdvance="update ledfile set del_tag='D' "+ 
				"where fin_year =? and div_code=? and vdepo_code=? and vbook_cd=? and vac_code =? and vou_no = ? and "+
				" ifnull(del_tag,'N')<>'D'";
				
				String rcpfile="update rcpfile r,"+
				" (select fin_year, div_code,vdepo_code ,vbook_cd,vou_no  from ledfile where  "+ 
				" fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? and vou_no=? and ifnull(del_tag,'N')<>'D') l "+
				" set r.inv_lo = 'C' "+
				" where r.vdepo_code=l.vdepo_code and r.div_code=l.div_code and r.fin_year=l.fin_year "+ 
				" and r.vou_no=l.vou_no and r.vbook_cd=l.vbook_cd and ifnull(r.del_tag,'N')<>'D'";

				
				String advance="update rcpfile r,"+
				" (select fin_year, div_code,vdepo_code ,vbook_cd,vou_no  from ledfile where  "+ 
				" fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? and vou_no=? and ifnull(del_tag,'N')<>'D') l "+
				" set r.inv_lo = 'C' "+
				" where r.vdepo_code=l.vdepo_code and r.div_code=l.div_code and r.fin_year=l.fin_year "+ 
				" and r.crno=l.vou_no and r.vbook_cd=98 and ifnull(r.del_tag,'N')<>'D'";


				
				String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
						"vou_date,vbk_code,vgrp_code,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date," +
						"vstat_cd,varea_cd,vreg_cd,vter_cd,vdist_cd,vmsr_cd, "+
						"Created_by,Created_date,serialno,fin_year,mnth_code,rcp_amt1,vnart_2,vbook_cd1,mkt_year,sub_div,tp,bill_no,bill_date,interest,name) " +
						"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

				String query2 ="select ifnull(max(vou_no),0) from ledfile " + 
				            "  where fin_year=? and div_code=? AND vdepo_Code=? and vbook_Cd1=? " +
				            " and vdbcr=? and sub_div=? and ifnull(del_tag,'N')<>'D'  " ;
						
				
				 
				rcpps = con.prepareStatement(rcpfile);
//				rcpps.setInt(1, rcp.getFin_year());
				rcpps.setInt(1, fyear);
				rcpps.setInt(2, rcp.getDiv_code());
				rcpps.setInt(3, rcp.getVdepo_code());
				rcpps.setInt(4, rcp.getVbook_cd());
				rcpps.setInt(5, rcp.getVou_no());
				i=rcpps.executeUpdate();

				rcpps.setInt(1, fyear);
				rcpps.setInt(2, rcp.getDiv_code());
				rcpps.setInt(3, rcp.getVdepo_code());
				rcpps.setInt(4, 99);
				rcpps.setInt(5, rcp.getVou_no());
				i=rcpps.executeUpdate();

				rcpps=null;

				rcpps = con.prepareStatement(advance);
				rcpps.setInt(1, fyear);
				rcpps.setInt(2, rcp.getDiv_code());
				rcpps.setInt(3, rcp.getVdepo_code());
				rcpps.setInt(4, rcp.getVbook_cd());
				rcpps.setInt(5, rcp.getVou_no());
				i=rcpps.executeUpdate();
				
				
				
				

				rcpps=null;
				rcpps = con.prepareStatement(UpdAdvance);
				rcpps.setInt(1, fyear);
				rcpps.setInt(2, rcp.getDiv_code());
				rcpps.setInt(3, rcp.getVdepo_code());
				rcpps.setInt(4, 99);
				rcpps.setString(5, rcp.getVac_code());
				rcpps.setInt(6, rcp.getVou_no());
				i=rcpps.executeUpdate();

	
				
				
				invtrd=con.prepareStatement(UpdInvtrd);
				ledupdps=con.prepareStatement(UpdLedfile);
				
				ledupdps1=con.prepareStatement(ledfile1);
//				ledupdps1.setInt(1, rcp.getFin_year());
				ledupdps1.setInt(1, fyear);
				ledupdps1.setInt(2, rcp.getDiv_code());
				ledupdps1.setInt(3, rcp.getVdepo_code());
				ledupdps1.setInt(4, rcp.getVbook_cd());
				ledupdps1.setInt(5, rcp.getVou_no());
				rs1 = ledupdps1.executeQuery();
				
				while (rs1.next())
				{
					// Ledfile update Outstanding revert
					ledupdps.setDouble(1,rs1.getDouble(5));
					ledupdps.setInt(2,rcp.getDiv_code());
					ledupdps.setInt(3,rcp.getVdepo_code());
					ledupdps.setInt(4,80); //vbook_cd
					ledupdps.setString(5,rs1.getString(1)); // vac_code
					ledupdps.setInt(6,rs1.getInt(2)); // vou_no i.e inv_no
					ledupdps.setDate(7,rs1.getDate(3)); // vou_date i.e inv_date
//					ledupdps.setDouble(8,rs1.getDouble(4)); // vamount i.e. bill_amt
					i=ledupdps.executeUpdate();
					
					
					
					// Invtrd update Invoice third file debit note revert
					
					invtrd.setInt(1,rcp.getDiv_code());
					invtrd.setInt(2,rcp.getVdepo_code());
					invtrd.setString(3,rs1.getString(1)); // vac_code
					invtrd.setInt(4,rs1.getInt(2)); // vou_no i.e inv_no
					invtrd.setDate(5,rs1.getDate(3)); // vou_date i.e inv_date
					i=invtrd.executeUpdate();

				}
				 
				ps2 = con.prepareStatement(query2);
				ps2.setInt(1, rcp.getFin_year());
				ps2.setInt(2, 4);  //  last no of petty cash
				ps2.setInt(3, rcp.getVcmp_code());
				ps2.setInt(4, rcp.getVbook_cd());
				ps2.setString(5, "DR");
				ps2.setInt(6, sub);

				rs= ps2.executeQuery();

				if (rs.next())
				{
					i=rs.getInt(1)+1;
				}
				

				ledps1 = con.prepareStatement(led);
				
				ledps1.setInt(1, 4);
				ledps1.setInt(2, rcp.getVcmp_code());
				ledps1.setInt(3, rcp.getVbook_cd());
				ledps1.setInt(4, rcp.getVou_yr());
				ledps1.setString(5, rcp.getVou_lo().substring(0,1)+"P");
				ledps1.setInt(6, i);
				ledps1.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps1.setInt(8, 0); // vbk_code
				ledps1.setInt(9,0); // vgrp_Code
				ledps1.setString(10, rcp.getVac_code());
				ledps1.setString(11, rcp.getVnart1());
				ledps1.setDouble(12, rcp.getVamount());
				ledps1.setString(13, "DR");
				ledps1.setString(14, rcp.getChq_no());
				ledps1.setDate(15, new java.sql.Date(rcp.getChq_date().getTime()));
				ledps1.setInt(16, rcp.getVstat_cd());
				ledps1.setInt(17, rcp.getVarea_cd());
				ledps1.setInt(18, rcp.getVreg_cd());
				ledps1.setInt(19, rcp.getVter_cd());
				ledps1.setInt(20, rcp.getVdist_cd());
				ledps1.setInt(21, rcp.getVmsr_cd());
				ledps1.setInt(22, rcp.getCreated_by());
				ledps1.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps1.setInt(24, rcp.getSerialno());
				ledps1.setInt(25, rcp.getFin_year());
				ledps1.setInt(26, rcp.getMnth_code());
				ledps1.setDouble(27, 0.00);
				ledps1.setString(28, rcp.getVnart2());
				ledps1.setInt(29, rcp.getVbook_cd());
				ledps1.setInt(30, rcp.getMkt_year());
				ledps1.setInt(31, sub);
				ledps1.setString(32,"D");
				ledps1.setString(33, String.valueOf(rcp.getVou_no())); // RP NO
				ledps1.setDate(34, new java.sql.Date(rcp.getBill_date().getTime()));  // RP DATE
				ledps1.setDouble(35, rcp.getInterest());
				ledps1.setString(36,rcp.getInt_code());
				
				ledps1.executeUpdate();
				
				if(rcp.getInterest()>0)
				{
					ledps1.setInt(1, 4);
					ledps1.setInt(2, rcp.getVcmp_code());
					ledps1.setInt(3, rcp.getVbook_cd());
					ledps1.setInt(4, rcp.getVou_yr());
					ledps1.setString(5, rcp.getVou_lo().substring(0,1)+"P");
					ledps1.setInt(6, i);
					ledps1.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					ledps1.setInt(8, 0); // vbk_code
					ledps1.setInt(9,0); // vgrp_Code
					ledps1.setString(10, rcp.getVac_code());
					ledps1.setString(11, "Interest");
					ledps1.setDouble(12, rcp.getInterest());
					ledps1.setString(13, "DR");
					ledps1.setString(14, rcp.getChq_no());
					ledps1.setDate(15, new java.sql.Date(rcp.getChq_date().getTime()));
					ledps1.setInt(16, rcp.getVstat_cd());
					ledps1.setInt(17, rcp.getVarea_cd());
					ledps1.setInt(18, rcp.getVreg_cd());
					ledps1.setInt(19, rcp.getVter_cd());
					ledps1.setInt(20, rcp.getVdist_cd());
					ledps1.setInt(21, rcp.getVmsr_cd());
					ledps1.setInt(22, rcp.getCreated_by());
					ledps1.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
					ledps1.setInt(24, rcp.getSerialno());
					ledps1.setInt(25, rcp.getFin_year());
					ledps1.setInt(26, rcp.getMnth_code());
					ledps1.setDouble(27, 0.00);
					ledps1.setString(28, rcp.getVnart2());
					ledps1.setInt(29, rcp.getVbook_cd());
					ledps1.setInt(30, rcp.getMkt_year());
					ledps1.setInt(31, sub);
					ledps1.setString(32,"D");
					ledps1.setString(33, String.valueOf(rcp.getVou_no())); // RP NO
					ledps1.setDate(34, new java.sql.Date(rcp.getBill_date().getTime()));  // RP DATE
					ledps1.setDouble(35, 0.00);
					ledps1.setString(36,null);
					
					ledps1.executeUpdate();

				}
				
				
				
				con.commit();
				con.setAutoCommit(true);
				
				ps2.close();
				rs.close();
				rs1.close();
				ledupdps1.close();
				ledps1.close();
				
			} catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.deleteLed for voucher delete " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);
					if(ps2 != null){ps2.close();}
					if(rs != null){rs.close();}
					if(ledps != null){ledps.close();}
					if(rcpps != null){rcpps.close();}
					if(ledps1 != null){ledps1.close();}
					if(ledupdps != null){ledupdps.close();}
					if(rs1 != null){rs1.close();}
					if(ledupdps1 != null){ledupdps1.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}

			return (i);
		}	
		

		
		public int addExcessAdvance(ArrayList list)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;
			PreparedStatement rcpps=null;
			RcpDto rcp=null;
			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				

				String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
				"vou_date,vbk_code,vgrp_code,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date," +
				"vstat_cd,varea_cd,vreg_cd,vter_cd,vdist_cd,vmsr_cd,chq_amt,bill_no,bill_date,bill_amt, "+
				"Created_by,Created_date,serialno,fin_year,mnth_code,rcp_amt1,vnart_2,vbook_cd1,mkt_year) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


				String receipt="insert into rcpfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
				"vou_date,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date," +
				"vstat_cd,varea_cd,vreg_cd,vterr_cd,vdist_cd,vmsr_cd,chq_amt,bill_no,bill_date,bill_amt, "+
				"Created_by,Created_date,serialno,fin_year,mnth_code,mkt_year,vouno,inv_no) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

				
				ledps=con.prepareStatement(led);
				rcpps=con.prepareStatement(receipt);

				int x=list.size();
				
				for (int j=0;j<x;j++)
				{
					rcp = (RcpDto) list.get(j);
				
				ledps.setInt(1, rcp.getDiv_code());
				ledps.setInt(2, rcp.getVdepo_code());
				ledps.setInt(3, 96);
				ledps.setInt(4, rcp.getVou_yr());
				ledps.setString(5, rcp.getVou_lo());
				ledps.setInt(6, rcp.getVou_no());
				ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps.setInt(8, 0); // vbk_code
				ledps.setInt(9,0); // vgrp_Code
				ledps.setString(10, rcp.getVac_code());
				ledps.setString(11, rcp.getVnart1());
				ledps.setDouble(12, rcp.getVamount());
				ledps.setString(13, "CR");
				ledps.setString(14, rcp.getChq_no());
				
				ledps.setDate(15, rcp.getChq_date()==null?null:new java.sql.Date(rcp.getChq_date().getTime()));
				ledps.setInt(16, rcp.getVstat_cd());
				ledps.setInt(17, rcp.getVarea_cd());
				ledps.setInt(18, rcp.getVreg_cd());
				ledps.setInt(19, rcp.getVter_cd());
				ledps.setInt(20, rcp.getVdist_cd());
				ledps.setInt(21, rcp.getVmsr_cd());
				ledps.setDouble(22, rcp.getChq_amt());
				ledps.setString(23, rcp.getBill_no());
				ledps.setDate(24, rcp.getBill_date()==null?null:new java.sql.Date(rcp.getBill_date().getTime()));
				ledps.setDouble(25, rcp.getBill_amt());
				
				
				ledps.setInt(26, rcp.getCreated_by());
				ledps.setDate(27, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(28, rcp.getSerialno());
				ledps.setInt(29, rcp.getFin_year());
				ledps.setInt(30, rcp.getMnth_code());
				ledps.setDouble(31, 0.00);
				ledps.setString(32, rcp.getVnart2());
				ledps.setInt(33, 96);
				ledps.setInt(34, rcp.getMkt_year());
				
				
				i=ledps.executeUpdate();

 
				rcpps.setInt(1, rcp.getDiv_code());
				rcpps.setInt(2, rcp.getVdepo_code());
				rcpps.setInt(3, 96);
				rcpps.setInt(4, rcp.getVou_yr());
				rcpps.setString(5, rcp.getVou_lo());
				rcpps.setInt(6, rcp.getVou_no());
				rcpps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));

				rcpps.setString(8, rcp.getVac_code());
				rcpps.setString(9, rcp.getVnart1());
				rcpps.setDouble(10, rcp.getVamount());
				rcpps.setString(11, "CR");
				rcpps.setString(12, rcp.getChq_no());
				rcpps.setDate(13, rcp.getChq_date()==null?null:new java.sql.Date(rcp.getChq_date().getTime()));
				rcpps.setInt(14, rcp.getVstat_cd());
				rcpps.setInt(15, rcp.getVarea_cd());
				rcpps.setInt(16, rcp.getVreg_cd());
				rcpps.setInt(17, rcp.getVter_cd());
				rcpps.setInt(18, rcp.getVdist_cd());
				rcpps.setInt(19, rcp.getVmsr_cd());
				rcpps.setDouble(20, rcp.getChq_amt());
//				rcpps.setString(21, rcp.getBill_no());
//				rcpps.setDate(22, new java.sql.Date(rcp.getBill_date().getTime()));
//				rcpps.setDouble(23, rcp.getBill_amt());
				rcpps.setString(21, null);
				rcpps.setDate(22, null);
				rcpps.setDouble(23, 0.00);
				
				
				rcpps.setInt(24, rcp.getCreated_by());
				rcpps.setDate(25, new java.sql.Date(rcp.getCreated_date().getTime()));
				rcpps.setInt(26, rcp.getSerialno());
				rcpps.setInt(27, rcp.getFin_year());
				rcpps.setInt(28, rcp.getMnth_code());
				rcpps.setInt(29, rcp.getMkt_year());
//				rcpps.setInt(30, rcp.getInv_no());
//				rcpps.setInt(31, rcp.getInv_no());
				rcpps.setInt(30, 0);
				rcpps.setInt(31, 0);
				
				i=rcpps.executeUpdate();

				rcpps.setInt(1, rcp.getDiv_code());
				rcpps.setInt(2, rcp.getVdepo_code());
				rcpps.setInt(3, 93);
				rcpps.setInt(4, rcp.getVou_yr());
				rcpps.setString(5, rcp.getVou_lo());
				rcpps.setInt(6, rcp.getVou_no());
				rcpps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));

				rcpps.setString(8, rcp.getVac_code());
				rcpps.setString(9, rcp.getVnart1());
				rcpps.setDouble(10, (rcp.getVamount())*-1);
				rcpps.setString(11, "CR");
				rcpps.setString(12, rcp.getChq_no());
				rcpps.setDate(13, rcp.getChq_date()==null?null:new java.sql.Date(rcp.getChq_date().getTime()));
				rcpps.setInt(14, rcp.getVstat_cd());
				rcpps.setInt(15, rcp.getVarea_cd());
				rcpps.setInt(16, rcp.getVreg_cd());
				rcpps.setInt(17, rcp.getVter_cd());
				rcpps.setInt(18, rcp.getVdist_cd());
				rcpps.setInt(19, rcp.getVmsr_cd());
				rcpps.setDouble(20, rcp.getChq_amt());
				rcpps.setString(21, rcp.getBill_no());
				rcpps.setDate(22, rcp.getBill_date()==null?null:new java.sql.Date(rcp.getBill_date().getTime()));
				rcpps.setDouble(23, rcp.getBill_amt());
				
				
				rcpps.setInt(24, rcp.getCreated_by());
				rcpps.setDate(25, new java.sql.Date(rcp.getCreated_date().getTime()));
				rcpps.setInt(26, rcp.getSerialno());
				rcpps.setInt(27, rcp.getFin_year());
				rcpps.setInt(28, rcp.getMnth_code());
				rcpps.setInt(29, rcp.getMkt_year());
				rcpps.setInt(30, rcp.getInv_no());
				rcpps.setInt(31, rcp.getInv_no());
				
				i=rcpps.executeUpdate();


				
				
				}				
				con.commit();
				con.setAutoCommit(true);
				ledps.close();
				rcpps.close();
				
			} catch (SQLException ex) {
				ex.printStackTrace();
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
					if(ledps != null){ledps.close();}
					if(rcpps != null){rcpps.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}

			return (i);
		}		
		

		public ArrayList getReceipt1(int year,int depo,int div,int sinv,int einv,int bkcd)
		{
			PreparedStatement ps1 = null;
			ResultSet rs=null;
			Connection con=null;
			int i=0;
			int subdiv=0;
			String wcode="";
			String query1=null;
			RcpDto rcp=null;
		    ArrayList data=null;
		    String code=null;
		    try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);

				code="040"+String.format("%02d", depo)+"01";
				System.out.println("value of div "+div);
				
				if (div==41 )
				{   
					div=4;
					subdiv=1;
				}	

				if (div==42)
				{   
					div=4;
					subdiv=2;
				}	

				if (div==43)
				{   
					div=4;
					subdiv=3;
				}	

				
/*				subdiv=div;
				if (div==41 )
				{   
					div=1;
					subdiv=1;
				}	

				if (div==42)
				{   
					div=2;
					subdiv=2;
				}	

				if (div==43)
				{   
					div=3;
					subdiv=3;
				}	

*/				
/*					query1 = " select a.* from "+
					"( select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,if(r.div_code=4,r.vac_code,r.head_code)," +
					" concat(p.mac_name,',',p.mcity),left(r.vnart_1,24),r.vamount,r.chq_no,r.chq_date,ifnull(r.chq_amt,0)," +
					" concat(left(r.bill_no,3),right(r.bill_no,5)),r.bill_date,r.bill_amt,r.vdbcr,r.vac_code  "+ 
					" from rcpfile as r, partyfst as p where r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd=? "+ 
					" and  r.vou_no between ? and ? and r.vac_code=p.mac_code and ifnull(r.del_tag,'')<>'D' and r.vamount<>0" +
					" and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' " +
					" order by r.vou_no,r.vbook_cd,r.inv_no,r.head_code desc "+
					" union all "+
					
					" select f.vou_yr,f.vou_lo,f.vou_no,f.vou_date,f.vac_code,ifnull(f.name,'')," +
					" f.vnart_1,f.vamount,ifnull(f.chq_no,''),f.chq_date,ifnull(f.chq_amt,0),ifnull(f.bill_no,''),f.bill_date,ifnull(f.bill_amt,0),f.vdbcr from ledfile as f "+
					" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
					" f.vbook_cd1=? and f.vou_no between ? and ? and f.sub_div=? and f.vdbcr='CR' and ifnull(f.del_tag,'')<>'D' ) a " +
					" order by vou_no  ";  
*/

					
					query1 = " select f.vou_yr,f.vou_lo,f.vou_no,f.vou_date,f.vac_code,ifnull(f.name,'')," +
					" f.vnart_1,f.vamount,ifnull(f.chq_no,''),f.chq_date,ifnull(f.chq_amt,0),ifnull(f.bill_no,''),f.bill_date,ifnull(f.bill_amt,0),f.vdbcr from ledfile as f "+
					" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
					" f.vbook_cd1=? and f.vou_no between ? and ? and f.sub_div=? and f.vdbcr='CR' and ifnull(f.del_tag,'')<>'D'  " +
					" order by vou_no  ";  


					
					

				
				ps1 = con.prepareStatement(query1);
					ps1.setInt(1,year); // Sales Parameter
					ps1.setInt(2,div);
					ps1.setInt(3, depo);
					ps1.setInt(4,bkcd); 
					ps1.setInt(5,sinv); 
					ps1.setInt(6,einv); 
					ps1.setInt(7,subdiv);
					
					
/*					ps1.setInt(1,year); // Accounts Parameter
					ps1.setInt(2,4);
					ps1.setInt(3, depo);
					ps1.setInt(4,bkcd); 
					ps1.setInt(5,sinv); 
					ps1.setInt(6,einv); 
					ps1.setInt(7,subdiv);
*/
					
					rs=ps1.executeQuery();

				String inv = null;
				data = new ArrayList();
				 double rgross=0.00;
				 boolean first=true;
				 int sno=0;
				while(rs.next())
				{


					if (first)
					{
						sno=rs.getInt(3);
						first=false;
						wcode=rs.getString(5);
					}
						if (rs.getInt(3)!=sno)
						{
							rcp= new RcpDto();
							rcp.setDash(1);
							rcp.setVamount(rgross);
				            data.add(rcp);
				            rgross=0.00;
							sno=rs.getInt(3);
							wcode=rs.getString(5);
						}

						if (rs.getString(5).equalsIgnoreCase(wcode))
						{
		//				inv = rs.getString(2).charAt(0)+rs.getString(2).replace(rs.getString(2).charAt(0), 'R')+rs.getString(3).substring(1);
						inv = rs.getString(2).charAt(0)+"R"+rs.getString(2).charAt(1)+rs.getString(3).substring(1);
						rcp= new RcpDto();
						rcp.setVou_lo(inv);
						rcp.setVou_no(rs.getInt(3));
						rcp.setVou_date(rs.getDate(4));
						rcp.setVac_code(rs.getString(5));
						rcp.setParty_name(rs.getString(6));
					
						rcp.setVnart1(rs.getString(7));
						rcp.setVamount(rs.getDouble(8));
						rcp.setChq_no(rs.getString(9));
						rcp.setChq_date(rs.getDate(4));
						if (rs.getDate(10)!=null)
							rcp.setChq_date(rs.getDate(10));
		                rcp.setChq_amt(rs.getDouble(11));				
		                rcp.setBill_no(rs.getString(12));
		                rcp.setBill_date(rs.getDate(13));
		                if (rs.getString(15).equals("CR"))
		                {
		//                    rcp.setBill_amt(rs.getDouble(14));
		//                	rgross+=rs.getDouble(14);
		                	
		                    rcp.setBill_amt(rs.getDouble(8));
		                	rgross+=rs.getDouble(8);
		
		                }
		                if (rs.getString(15).equals("DR"))
		                {
		                    rcp.setBill_amt(rs.getDouble(8));
		                	rgross-=rs.getDouble(8);
		                }
		    			rcp.setDash(0);
		    			rcp.setDiv_code(div);
		                data.add(rcp);
					} 
				}   // end of while
				rcp= new RcpDto();
				rcp.setDash(1);
				rcp.setVamount(rgross);
				rcp.setDiv_code(div);
	            data.add(rcp);
	            rs.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
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
					if(rs != null){rs.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}
				return data;
			}	
		

		
		public ArrayList getReceiptAll(int year,int depo,int div,int sinv,int einv,int bkcd,int cmpdepo)
		{
			PreparedStatement ps1 = null;
			ResultSet rs=null;
			Connection con=null;
			int i=0;
			int subdiv=0;
			int wdiv=div;
			int xdiv=0;
			String wcode="";
			String query1=null;
			RcpDto rcp=null;
		    ArrayList data=null;
		    String code=null;
		    try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);

				code="040"+String.format("%02d", depo)+"01";
				
				
				System.out.println("receipt printing yeh hai kya????  & SINV "+sinv);
				subdiv=div;
				/*
				if(div==9 || div==1)
				{
					wdiv=1;
					xdiv=9;
				}*/

				query1 = " select a.* from "+
					"( select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,if(r.div_code=4,r.vac_code,r.head_code) vcode ," +
					" concat(p.mac_name,',',p.mcity),left(r.vnart_1,24),r.vamount,IFNULL(r.chq_no,''),r.chq_date,ifnull(r.chq_amt,0)," +
					" if((r.cr_no='' or r.cr_date is null),concat(left(r.bill_no,3),right(r.bill_no,5)),r.cr_no),if(r.cr_no='' or r.cr_date is null,r.bill_date,r.cr_date),r.bill_amt,r.vdbcr,r.vac_code,r.vbook_cd vbook,r.inv_no,r.div_code,IFNULL(r.bill_no,'')  "+ 
					" from rcpfile as r, partyfst as p where r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in (?,99) "+ 
					" and  r.vou_no between ? and ? and r.vac_code=p.mac_code and ifnull(r.del_tag,'')<>'D' and r.vamount<>0" +
					" and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' " +
					" union all "+
					
					" select f.vou_yr,f.vou_lo,f.vou_no,f.vou_date,f.vac_code vcode,ifnull(f.name,'')," +
					" f.vnart_1,f.vamount,ifnull(f.chq_no,''),f.chq_date,ifnull(f.chq_amt,0),ifnull(f.bill_no,''),f.bill_date," +
					" ifnull(f.bill_amt,0),f.vdbcr,f.vac_code,f.vbook_cd1 vbook,vou_no inv_no,f.div_code,f.bill_no from ledfile as f "+
					" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
					" f.vbook_cd1=? and f.vou_no between ? and ? and f.sub_div in(0,?) and f.vdbcr='CR' and ifnull(f.del_tag,'')<>'D') a  " +
					" order by vou_no,vbook,inv_no,vcode desc " ;

					if (sinv==0)
					{
						query1 = " select a.* from "+
								"( select r.vou_yr,r.vou_lo,r.vou_no,r.vou_date,if(r.div_code=4,r.vac_code,r.head_code) vcode ," +
								" concat(p.mac_name,',',p.mcity),left(r.vnart_1,24),r.vamount,IFNULL(r.chq_no,''),r.chq_date,ifnull(r.chq_amt,0)," +
								" if((r.cr_no='' or r.cr_date is null),concat(left(r.bill_no,3),right(r.bill_no,5)),r.cr_no),r.bill_date,r.bill_amt,r.vdbcr,r.vac_code,r.vbook_cd vbook,r.inv_no,r.div_code,IFNULL(r.bill_no,'')  "+ 
								" from rcpfile as r, partyfst as p where r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd in (?,99) "+ 
								" and  r.vou_no "+BaseClass.commaSepratedInvoiceNo+"  and r.vac_code=p.mac_code and ifnull(r.del_tag,'')<>'D' and r.vamount<>0" +
								" and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' " +
								" union all "+
								
								" select f.vou_yr,f.vou_lo,f.vou_no,f.vou_date,f.vac_code vcode,ifnull(f.name,'')," +
								" f.vnart_1,f.vamount,ifnull(f.chq_no,''),f.chq_date,ifnull(f.chq_amt,0),ifnull(f.bill_no,''),f.bill_date," +
								" ifnull(f.bill_amt,0),f.vdbcr,f.vac_code,f.vbook_cd1 vbook,vou_no inv_no,f.div_code,f.bill_no from ledfile as f "+
								" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
								" f.vbook_cd1=? and f.vou_no "+BaseClass.commaSepratedInvoiceNo+"  and f.sub_div=? and f.vdbcr='CR' and ifnull(f.del_tag,'')<>'D' ) a " +
								" order by vou_no,vbook,inv_no,vcode desc " ;
						
					}
				
					
				
				ps1 = con.prepareStatement(query1);
				
			if (sinv==0)
			{
				ps1.setInt(1,year); // Sales Parameter
				ps1.setInt(2,div);
				ps1.setInt(3, depo);
				ps1.setInt(4,bkcd); 
				ps1.setInt(5,div);
				ps1.setInt(6, depo);					
				
				ps1.setInt(7,year); // Accounts Parameter
				ps1.setInt(8,4);
				ps1.setInt(9, cmpdepo);
				ps1.setInt(10,bkcd); 
				ps1.setInt(11,subdiv);
				

				
			}
			else
			{
				
				
				ps1.setInt(1,year); // Sales Parameter
				ps1.setInt(2,wdiv);
				ps1.setInt(3, depo);
				ps1.setInt(4,bkcd); 
				ps1.setInt(5,sinv); 
				ps1.setInt(6,einv); 
				ps1.setInt(7,wdiv);
				ps1.setInt(8, depo);					
				
				ps1.setInt(9,year); // Accounts Parameter
				ps1.setInt(10,4);
				ps1.setInt(11, cmpdepo);
				ps1.setInt(12,bkcd); 
				ps1.setInt(13,sinv); 
				ps1.setInt(14,einv); 
				ps1.setInt(15,wdiv);
				

			}
					
					rs=ps1.executeQuery();

				String inv = null;
				data = new ArrayList();
				 double rgross=0.00;
				 boolean first=true;
				 int sno=0;
				while(rs.next())
				{
					div=rs.getInt(19);
					
					System.out.println("RCP PARTY NAME "+rs.getString(6));
					if (first)
					{
						sno=rs.getInt(3);
						first=false;
						wcode=rs.getString(16);
					}
						if (rs.getInt(3)!=sno)
						{
							rcp= new RcpDto();
							rcp.setDash(1);
							rcp.setVamount(rgross);
				            data.add(rcp);
				            rgross=0.00;
							sno=rs.getInt(3);
							wcode=rs.getString(16);
						}

						if (rs.getString(16).equalsIgnoreCase(wcode))
						{
		//				inv = rs.getString(2).charAt(0)+rs.getString(2).replace(rs.getString(2).charAt(0), 'R')+rs.getString(3).substring(1);
						inv = rs.getString(2).charAt(0)+"R"+rs.getString(2).charAt(1)+String.format("%05d",rs.getInt(3));
						
						if (cmpdepo==15 || cmpdepo==18)
							inv = rs.getString(2)+"A"+String.format("%05d",rs.getInt(3));
						
						if (depo==3 || depo==11 || depo==12 )
							inv = "R"+rs.getString(2).charAt(0)+rs.getString(2).charAt(1)+String.format("%05d",rs.getInt(3));

						
						rcp= new RcpDto();
						rcp.setVou_lo(inv);
						rcp.setVou_no(rs.getInt(3));
						rcp.setVou_date(rs.getDate(4));
						rcp.setVac_code(rs.getString(5));
						rcp.setParty_name(rs.getString(6));
					
						System.out.println("RCP PARTY NAME "+rcp.getParty_name());
						rcp.setVnart1(rs.getString(7));
						rcp.setVamount(rs.getDouble(8));
						rcp.setChq_no(rs.getString(9));
						rcp.setChq_date(rs.getDate(4));
						if (rs.getDate(10)!=null)
							rcp.setChq_date(rs.getDate(10));
		                rcp.setChq_amt(rs.getDouble(11));
		                
	
		                if (rs.getInt(17)==99)
		                {
			                rcp.setBill_no(null);
			                rcp.setBill_date(null);
		                	
		                }
		                else
		                {
		                	rcp.setBill_no(rs.getString(12));
		                	rcp.setBill_date(rs.getDate(13));
		                }

		                if (rs.getString(15).equals("CR"))
		                {
		//                    rcp.setBill_amt(rs.getDouble(14));
		//                	rgross+=rs.getDouble(14);
		                	
		                    rcp.setBill_amt(rs.getDouble(8));
		                	rgross+=rs.getDouble(8);
		
		                }
		                if (rs.getString(15).equals("DR"))
		                {
		                    rcp.setBill_amt(rs.getDouble(8));
		                	rgross-=rs.getDouble(8);
		                }
		    			rcp.setDash(0);
		    			rcp.setDiv_code(div);
		                data.add(rcp);
					} 
				}   // end of while
				rcp= new RcpDto();
				rcp.setDash(1);
				rcp.setVamount(rgross);
				rcp.setDiv_code(div);
    		    data.add(rcp);
	            rs.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.getReceiptAll " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);
					if(ps1 != null){ps1.close();}
					if(rs != null){rs.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}
				return data;
			}	
 		
		public ArrayList getAdvice(int year,int depo,int div,int sinv,int einv,int bkcd,int cmpdepo)
		{
			PreparedStatement ps1 = null;
			ResultSet rs=null;
			PreparedStatement ps2 = null;
			ResultSet rs2=null;
			Connection con=null;
			int i=0;
			int subdiv=0;
			int wdiv=div;
			int xdiv=0;
			String wcode="";
			String query1=null;
			String query2=null;
			RcpDto rcp=null;
		    ArrayList data=null;
		    String code=null;
		    try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);

				
				query1=" select cheque_no,chq_date,chq_amt from chqmast " +
						" where div_code=? and depo_code=? and bank_code=? and advice_no=? and print='Y' and status='N' and ifnull(del_tag,'')<>'D' " ;


				query2=" select ifnull(p.bankifsc_code,'')ifsc,SUM(l.VAMOUNT),ifnull(bank_accno,'') acno,mac_name,concat(mcity),l.taxable_amt  " +
				" from ledfile l,partyfst p "+  
				" where l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_Cd1=? and l.hsn_code=?  and l.chq_no <>' ' and ifnull(l.del_tag,'')<>'D' " +
				" and p.div_code=? and p.depo_code=? and p.mac_code=l.vac_code and ifnull(p.del_tag,'')<>'D' and l.taxable_amt<>0 and l.vamount> 0 group by l.vou_no ";

				ps2 = con.prepareStatement(query2);
						
				ps1 = con.prepareStatement(query1);
				
				ps1.setInt(1,div);
				ps1.setInt(2, depo);
				ps1.setInt(3,bkcd); 
				ps1.setInt(4,sinv);
					
				rs=ps1.executeQuery();

				data = new ArrayList();
				 int sno=0;
				while(rs.next())
				{
						rcp= new RcpDto();
						sno++;
						rcp.setParty_name(sno+" Cheque No");
						rcp.setChq_no(rs.getString(1));
						rcp.setChq_date(rs.getDate(2));
		                rcp.setChq_amt(rs.getDouble(3));
		    			rcp.setDash(0);
		                data.add(rcp);
				}   // end of while
	            rs.close();

				ps2.setInt(1,year);
				ps2.setInt(2,div);
				ps2.setInt(3, depo);
				ps2.setInt(4,bkcd); 
				ps2.setInt(5,sinv);
				ps2.setInt(6,div);
				ps2.setInt(7, depo);
					
				rs2=ps2.executeQuery();
				sno=0;
				while(rs2.next())
				{
						rcp= new RcpDto();
						sno++;
						rcp.setSerialno(sno);
						rcp.setBank_ifsc(rs2.getString(1));
		                rcp.setChq_amt(rs2.getDouble(2));
						rcp.setBank_acno(rs2.getString(3));
						rcp.setParty_name(rs2.getString(4));
						rcp.setCity(rs2.getString(5));
						rcp.setBank_chg(rs2.getDouble(6));
		    			rcp.setDash(1);
		                data.add(rcp);
				}   // end of while
	            rs2.close();
	            
	            
			} catch (SQLException ex) {
				ex.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.getAdvice " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);
					if(ps1 != null){ps1.close();}
					if(rs != null){rs.close();}
					if(ps2 != null){ps2.close();}
					if(rs2 != null){rs2.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}
				return data;
			}	
	
		
		public int addProforma(RcpDto rcp)
		{
			Connection con=null;
			int i=0;
			
			
			PreparedStatement ps1=null;
			PreparedStatement ps2=null;
			PreparedStatement ps3=null;
			ResultSet rs3=null;
			int size=0;
			boolean exist=false;
			ArrayList rcpList=rcp.getChqlist();
			if(rcpList==null)
				size=1;
			else
			{
				size=rcpList.size();
				exist=true;
			}
			
			System.out.println("SIZE OF RCPLIST IS "+size);
			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				


				String ordfst="update ordfst set new2=?,system_dt=?,cases=? where div_code=? and inv_no=? and inv_date=?  and new2 =0 and ifnull(del_tag,'')<>'D' ";
				String ordsnd="update ordsnd set new2=? where div_code=? and sinv_no=? and sinv_dt=? and new2 =0 and ifnull(del_tag,'')<>'D' ";

				String maxquery="select ifnull(max(new2),0) from ordfst where fin_year=? and div_code=? and depo_Code=? and doc_type=? and ifnull(del_tag,'')<>'D'";
				
				
				ps1=con.prepareStatement(ordfst);
				ps2=con.prepareStatement(ordsnd);
				ps3=con.prepareStatement(maxquery);
				
				
				
				for(int j=0;j<size;j++)
				{

					if(exist)
						rcp=(RcpDto) rcpList.get(j);
					
					rcp.setProformano(0);
					ps3.setInt(1, rcp.getFin_year());
					ps3.setInt(2, rcp.getPdiv_code());
					ps3.setInt(3, rcp.getVdepo_code());
					ps3.setInt(4, 40);
					rs3=ps3.executeQuery();
					if(rs3.next())
					{
						rcp.setProformano(rs3.getInt(1)+1);
					}
					rs3.close();
					
					if(rcp.getVbook_cd()==0)
					{
					}
					
					else if(rcp.getVbook_cd()==40 && rcp.getProformano()>0)
					{
						ps1.setInt(1, rcp.getProformano());
						ps1.setDate(2, new java.sql.Date(rcp.getProformdate().getTime()));
						ps1.setInt(3, rcp.getSerialno());
						// where clause
						ps1.setInt(4, rcp.getPdiv_code());
						ps1.setInt(5, rcp.getInv_no());
						ps1.setDate(6, new java.sql.Date(rcp.getCn_date().getTime()));
						
						i=ps1.executeUpdate();
						ps2.setInt(1, rcp.getProformano());
						// where clause
						ps2.setInt(2, rcp.getPdiv_code());
						ps2.setInt(3, rcp.getInv_no());
						ps2.setDate(4, new java.sql.Date(rcp.getCn_date().getTime()));
						
						i=ps2.executeUpdate();
						
					}
					
					
				}

				
				
				con.commit();
				con.setAutoCommit(true);
				
				ps1.close();
				ps2.close();
				ps3.close();
				
			} catch (SQLException ex) {ex.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.addLed " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);
					
					if(ps1 != null){ps1.close();}
					if(ps2 != null){ps2.close();}
					if(ps3 != null){ps3.close();}
					if(rs3 != null){rs3.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}

			return (i);
		}		
		
		public int addLedPro(RcpDto rcp)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;
			
			PreparedStatement ps1=null;
			PreparedStatement ps2=null;
			PreparedStatement ps3=null;
			PreparedStatement ps4=null;
			PreparedStatement ps5=null;
			PreparedStatement ps6=null;
			ResultSet rs3=null;
			ResultSet rs4=null;
			ResultSet rs6=null;
			StringBuffer wnart=new StringBuffer();
			int size=0;
			boolean exist=false;
			ArrayList rcpList=rcp.getChqlist();
			if(rcpList==null)
				size=1;
			else
			{
				size=rcpList.size();
				exist=true;
			}
			
			System.out.println("SIZE OF RCPLIST IS "+size);
			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				

				String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
				"vou_date,vbk_code,vgrp_code,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date," +
				"vstat_cd,varea_cd,vreg_cd,vter_cd,vdist_cd,vmsr_cd, "+
				"Created_by,Created_date,serialno,fin_year,mnth_code,rcp_amt1,vnart_2,vbook_cd1,mkt_year,sub_div,bill_amt) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

				String ordfst="update ordfst set new2=?,system_dt=?,bunch_no=?,oct_per1=1 where div_code=? and inv_no=? and inv_date=?  and new2 =0 and ifnull(del_tag,'')<>'D' ";
				String ordsnd="update ordsnd set new2=? where div_code=? and sinv_no=? and sinv_dt=? and new2 =0 and ifnull(del_tag,'')<>'D' ";

				
				String party="select mac_code from partyfst where depo_code=? and div_code=? and gst_no=? and ifnull(del_tag,'')<>'D' ";

				String partysetup="select mstat_code,marea_code,mregion_cd,mterr_code,mdist_cd,msr_cd from partysetup where depo_code=? and div_code=? and mkt_year=? and mac_code=? and ifnull(del_tag,'')<>'D' ";

				
				String ledfile="update ledfile set vnart_2=? where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1 in(?,97) and vou_no=? ";

				String maxquery="select ifnull(max(new2),0) from ordfst where fin_year=? and div_code=? and depo_Code=? and doc_type=? and ifnull(del_tag,'')<>'D'";

				ledps=con.prepareStatement(led);
				ps1=con.prepareStatement(ordfst);
				ps2=con.prepareStatement(ordsnd);
				ps3=con.prepareStatement(party);
				ps4=con.prepareStatement(partysetup);
				ps5=con.prepareStatement(ledfile);
				ps6=con.prepareStatement(maxquery);
				
				boolean first=true;
				boolean addrecord=true;
				int wdiv=0;
				int xdiv=0;
				
				int wno=0;
				
				for(int j=0;j<size;j++)
				{

					if(exist)
						rcp=(RcpDto) rcpList.get(j);
					
					rcp.setProformano(0);
					ps6.setInt(1, rcp.getFin_year());
					ps6.setInt(2, rcp.getPdiv_code());
					ps6.setInt(3, rcp.getVdepo_code());
					ps6.setInt(4, 40);
					rs6=ps6.executeQuery();
					if(rs6.next())
					{
						rcp.setProformano(rs6.getInt(1)+1);
						
					}
					rs6.close();

					
					if(rcp.getVamount()>0)
					{
					
					
					if(rcp.getVamount()>0 && first)
					{
						wno=rcp.getVou_no();
						xdiv=rcp.getDiv_code();
						first=false;
					}
					
					if(xdiv!=rcp.getDiv_code())
					{
						wnart.delete(wnart.length()-1, wnart.length());
						ps5.setString(1, wnart.toString());
						// where clause
						ps5.setInt(2, rcp.getFin_year());
						ps5.setInt(3, xdiv);
						ps5.setInt(4, rcp.getVdepo_code());
						ps5.setInt(5, rcp.getVbook_cd1());
						ps5.setInt(6, wno);
						i=ps5.executeUpdate();

						wno=rcp.getVou_no();
						wnart.delete(0, wnart.length());
						xdiv=rcp.getDiv_code();


					}
					
					if(rcp.getVamount()>0)
						{
							if(rcp.getVbook_cd()==40)
								wnart.append(rcp.getProformano()+ ",");
							else if(rcp.getInv_no()>0)
								wnart.append(rcp.getInv_no()+ ",");
						}
					if(rcp.getVamount()>0 && rcp.getVbook_cd()==40)
					{
						ps1.setInt(1, rcp.getProformano());
						ps1.setDate(2, new java.sql.Date(rcp.getProformdate().getTime()));
						ps1.setInt(3, rcp.getSerialno());
						// where clause
						ps1.setInt(4, rcp.getPdiv_code());
						ps1.setInt(5, rcp.getInv_no());
						ps1.setDate(6, new java.sql.Date(rcp.getCn_date().getTime()));
						
						i=ps1.executeUpdate();
						ps2.setInt(1, rcp.getProformano());
						// where clause
						ps2.setInt(2, rcp.getPdiv_code());
						ps2.setInt(3, rcp.getInv_no());
						ps2.setDate(4, new java.sql.Date(rcp.getCn_date().getTime()));
						
						i=ps2.executeUpdate();
						
					}
					
					ps5.setString(1, wnart.toString());
					// where clause
					ps5.setInt(2, rcp.getFin_year());
					ps5.setInt(3, xdiv);
					ps5.setInt(4, rcp.getVdepo_code());
					ps5.setInt(5, rcp.getVbook_cd1());
					ps5.setInt(6, wno);
					i=ps5.executeUpdate();

					if(rcp.getVamount()>0 && rcp.getPdiv_code()!=wdiv )
					{
						wdiv=rcp.getPdiv_code();
						
						
						ps3.setInt(1, rcp.getVdepo_code());
						ps3.setInt(2, rcp.getDiv_code());
						ps3.setString(3, rcp.getGst_no());
						rs3=ps3.executeQuery();
						if(rs3.next())
						{
							rcp.setVac_code(rs3.getString(1));
							
						}
						rs3.close();
						ps4.setInt(1, rcp.getVdepo_code());
						ps4.setInt(2, rcp.getDiv_code());
						ps4.setInt(3, rcp.getMkt_year());
						ps4.setString(4, rcp.getVac_code());
						rs4=ps4.executeQuery();
						if(rs4.next())
						{
							
							rcp.setVstat_cd(rs4.getInt(1));
							rcp.setVarea_cd(rs4.getInt(2));
							rcp.setVreg_cd(rs4.getInt(3));
							rcp.setVter_cd(rs4.getInt(4));
							rcp.setVdist_cd(rs4.getInt(5));
							rcp.setVmsr_cd(rs4.getInt(6));
						}
						rs4.close();
						
						
						ledps.setInt(1, rcp.getDiv_code());
						ledps.setInt(2, rcp.getVdepo_code());
						ledps.setInt(3, rcp.getVbook_cd1());
						ledps.setInt(4, rcp.getVou_yr());
						ledps.setString(5, rcp.getVou_lo());
						ledps.setInt(6, rcp.getVou_no());
						ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
						ledps.setInt(8, 0); // vbk_code
						ledps.setInt(9,0); // vgrp_Code
						ledps.setString(10, rcp.getVac_code());
						ledps.setString(11, rcp.getVnart1());
						ledps.setDouble(12, rcp.getNetamt());
						ledps.setString(13, "CR");
						ledps.setString(14, rcp.getChq_no());
						if(rcp.getChq_date()!=null)
							ledps.setDate(15, new java.sql.Date(rcp.getChq_date().getTime()));
						else
							ledps.setDate(15, new java.sql.Date(rcp.getVou_date().getTime()));

						ledps.setInt(16, rcp.getVstat_cd());
						ledps.setInt(17, rcp.getVarea_cd());
						ledps.setInt(18, rcp.getVreg_cd());
						ledps.setInt(19, rcp.getVter_cd());
						ledps.setInt(20, rcp.getVdist_cd());
						ledps.setInt(21, rcp.getVmsr_cd());
						ledps.setInt(22, rcp.getCreated_by());
						ledps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
						ledps.setInt(24, rcp.getSerialno());
						ledps.setInt(25, rcp.getFin_year());
						ledps.setInt(26, rcp.getMnth_code());
						ledps.setDouble(27, 0.00);
						ledps.setString(28, rcp.getVnart2());
						ledps.setInt(29, rcp.getVbook_cd1());
						ledps.setInt(30, rcp.getMkt_year());
						ledps.setInt(31, 0);
						if (rcp.getDiv_code()==9)  // co mkt division
							ledps.setInt(31, 1);
						ledps.setDouble(32, rcp.getNetamt());


						i=ledps.executeUpdate();


						ledps.setInt(1, rcp.getDiv_code());
						ledps.setInt(2, rcp.getVdepo_code());
						ledps.setInt(3, 97);
						ledps.setInt(4, rcp.getVou_yr());
						ledps.setString(5, rcp.getVou_lo());
						ledps.setInt(6, rcp.getVou_no());
						ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
						ledps.setInt(8, 0); // vbk_code
						ledps.setInt(9,0); // vgrp_Code
						ledps.setString(10, rcp.getVac_code());
						ledps.setString(11, rcp.getVnart1());
						ledps.setDouble(12, rcp.getNetamt());
						ledps.setString(13, "CR");
						ledps.setString(14, rcp.getChq_no());
						if(rcp.getChq_date()!=null)
							ledps.setDate(15, new java.sql.Date(rcp.getChq_date().getTime()));
						else
							ledps.setDate(15, new java.sql.Date(rcp.getVou_date().getTime()));

						ledps.setInt(16, rcp.getVstat_cd());
						ledps.setInt(17, rcp.getVarea_cd());
						ledps.setInt(18, rcp.getVreg_cd());
						ledps.setInt(19, rcp.getVter_cd());
						ledps.setInt(20, rcp.getVdist_cd());
						ledps.setInt(21, rcp.getVmsr_cd());
						ledps.setInt(22, rcp.getCreated_by());
						ledps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
						ledps.setInt(24, rcp.getSerialno());
						ledps.setInt(25, rcp.getFin_year());
						ledps.setInt(26, rcp.getMnth_code());
						ledps.setDouble(27, 0.00);
						ledps.setString(28, rcp.getVnart2());
						ledps.setInt(29, 97);
						ledps.setInt(30, rcp.getMkt_year());
						ledps.setInt(31, 0);
						if (rcp.getDiv_code()==9)  // co mkt division
							ledps.setInt(31, 1);
						ledps.setDouble(32, rcp.getNetamt());
						i=ledps.executeUpdate();
					}
					} // eof of if
					
				}

/*				wnart.delete(wnart.length()-1, wnart.length());
				ps5.setString(1, wnart.toString());
				// where clause
				ps5.setInt(2, rcp.getFin_year());
				ps5.setInt(3, xdiv);
				ps5.setInt(4, rcp.getVdepo_code());
				ps5.setInt(5, rcp.getVbook_cd1());
				ps5.setInt(6, wno);
				i=ps5.executeUpdate();

*/				
				con.commit();
				con.setAutoCommit(true);
				ledps.close();
				ps1.close();
				ps2.close();
				ps3.close();
				ps4.close();
				ps5.close();
				ps6.close();
				
			} catch (SQLException ex) {ex.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SQLRcpDAO.addLed " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);
					if(ledps != null){ledps.close();}
					if(ps1 != null){ps1.close();}
					if(ps2 != null){ps2.close();}
					if(ps3 != null){ps2.close();}
					if(ps4 != null){ps4.close();}
					if(ps5 != null){ps5.close();}
					if(ps6 != null){ps6.close();}
					if(rs3 != null){rs3.close();}
					if(rs4 != null){rs4.close();}
					if(rs6 != null){rs6.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
				}
			}

			return (i);
		}		
		
}
