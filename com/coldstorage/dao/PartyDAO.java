package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.coldstorage.dto.PartyDto;
import com.coldstorage.view.BaseClass;

public class PartyDAO 
{
	public int addParty(PartyDto p)
	{

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int i=0;
		int j=0;
		int code=0;

		try {

			con=ConnectionFactory.getConnection();


			String query1 ="insert into partyfst (mgrp_code,mtype1,locked,mac_code,mac_name,madd1,madd2,madd3,mcity," +
			"state_code,mstate_name,mpin, "+
			" mphone,mobile,mcontct,memail,mbanker,mbank_add1,mbank_add2,bank_ifsc, "+
			" mtranspt,mtran_code,distance,gst_no,pan_no,div_code,depo_code,tax_type) " +
			" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps1 = con.prepareStatement(query1);
			String query2 ="insert into partyacc (mgrp_code,mfix_disc1,mdays," +
			" mopng_bal,mopng_db, " +
			" div_code,depo_code,mac_code,fin_year) values (?,?,?,?,?,?,?,?,?)";
			ps2 = con.prepareStatement(query2);

			String query = "select ifnull(max(mac_code),0) from partyfst where  div_code=? and " +
			"depo_code=? and mgrp_code=? and ifnull(del_tag,'')<>'D' "  ;
			
			
			ps = con.prepareStatement(query);
			
			con.setAutoCommit(false);

			ps.setInt(1,p.getMdiv_cd());
			ps.setInt(2,p.getDepo_code());

			ps.setInt(3, p.getMgrp_code());
			
			rs= ps.executeQuery();
			if (rs.next())
			{
				if(rs.getInt(1)==0)
					code=Integer.parseInt(p.getMgrp_code()+"0001");
				else
					 code=rs.getInt(1)+1;
					
				 p.setMac_code(String.valueOf(code));
			}

			rs.close();
			
			
			ps1.setInt(1,p.getMgrp_code());
			ps1.setString(2,p.getMtype1());  //type
			ps1.setString(3,p.getMtype2());  // locked
			ps1.setString(4,p.getMac_code());
			ps1.setString(5,p.getMac_name());
			ps1.setString(6,p.getMadd1());
			ps1.setString(7,p.getMadd2());
			ps1.setString(8,p.getMadd3());
			ps1.setString(9,p.getMcity());
			ps1.setInt(10,p.getMstat_code());
			ps1.setString(11,p.getMstate_name());
			ps1.setString(12,p.getMpin());
			ps1.setString(13,p.getMphone());
			ps1.setString(14,p.getMobile());
			ps1.setString(15,p.getMcontct());
			ps1.setString(16,p.getMemail());
			ps1.setString(17,p.getMbanker());
			ps1.setString(18,p.getMbank_add1());
			ps1.setString(19,p.getMbank_add2());
			ps1.setString(20,p.getBank_ifsc());
			ps1.setString(21,p.getMtranspt());
			ps1.setInt(22,p.getMtran_code());
			ps1.setInt(23,p.getDistance());
			ps1.setString(24,p.getGst_no());
			ps1.setString(25,p.getPan_no());
			ps1.setInt(26,p.getMdiv_cd());
			ps1.setInt(27,p.getDepo_code());
			ps1.setString(28,p.getTax_type());
			i= ps1.executeUpdate();

			ps2.setInt(1,p.getMgrp_code());
			ps2.setDouble(2,p.getMfix_disc1());
			ps2.setInt(3,p.getMdays());
			ps2.setDouble(4,p.getMopng_bal());
			ps2.setString(5,p.getMopng_db());
			ps2.setInt(6,p.getMdiv_cd());
			ps2.setInt(7,p.getDepo_code());
			ps2.setString(8,p.getMac_code());
			ps2.setInt(9,p.getFin_year());

			j= ps2.executeUpdate();


			con.commit();
			con.setAutoCommit(true);
			ps.close();
			ps1.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPartyDAO.update " + ex);
			i=-1;
			code=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				if(rs != null){ps1.close();}
				if(ps1 != null){ps1.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return (code);
	}


	
	
	
	
	// method for Account Package
	public int addParty1(PartyDto p)
	{

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		Connection con=null;

		int i=0;
		int j=0;

		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String query1 ="insert into partyfst (mac_name,madd1,madd2,madd3,mcity,mpin, "+
			" mphone,mobile,memail,mcontct, mpst_no,pan_no,tds_per,tds_limit,tds_apply," +
			" div_code,depo_code,mac_code,mgrp_code,mtype2) " +
			" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps1 = con.prepareStatement(query1);
			
			String query2 ="insert into partyacc (mgrp_code,cst_type,mopng_bal,mopng_db, " +
			" div_code,depo_code,mac_code,fin_year,msub_code) values (?,?,?,?,?,?,?,?,?)";
			ps2 = con.prepareStatement(query2);


			ps1.setString(1,p.getMac_name());
			ps1.setString(2,p.getMadd1());
			ps1.setString(3,p.getMadd2());
			ps1.setString(4,p.getMadd3());
			ps1.setString(5,p.getMcity());
			ps1.setString(6,p.getMpin());
			ps1.setString(7,p.getMphone());
			ps1.setString(8,p.getMobile());
			ps1.setString(9,p.getMemail());
			ps1.setString(10,p.getMcontct());
		
			ps1.setString(12,p.getPan_no());
			ps1.setInt(16,p.getMdiv_cd());
			ps1.setInt(17,p.getDepo_code());
			ps1.setString(18,p.getMac_code());
			ps1.setInt(19,p.getMgrp_code());
			ps1.setString(20,p.getMtype2());
			
			i= ps1.executeUpdate();


			ps2.setInt(1,p.getMgrp_code());
			ps2.setString(2,p.getCst_type());
			ps2.setDouble(3,p.getMopng_bal());
			ps2.setString(4,p.getMopng_db());
			ps2.setInt(5,p.getMdiv_cd());
			ps2.setInt(6,p.getDepo_code());
			ps2.setString(7,p.getMac_code());
			ps2.setInt(8,p.getFin_year());
			ps2.setInt(9,p.getMdist_cd());  // sub code of account head
			
			j= ps2.executeUpdate();




			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPartyDAO.addParty -- Account Package  " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return (i+j);
	}

	
	
	
	public int updateParty(PartyDto p)
	{

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		Connection con=null;

		int i=0;
		int j=0;

		try {

			con=ConnectionFactory.getConnection();


			String query1 ="update partyfst set mac_name=?,madd1=?,madd2=?,madd3=?,mcity=?,mpin=?," +
			"mphone=?,mobile=?,memail=?,mcontct=?,mbanker=?,mbank_add1=?,mbank_add2=?,bank_ifsc=?," +
			"mtranspt=?,gst_no=?,pan_no=?,mtype1=?,distance=?,state_code=?,mstate_name=?,mgrp_code=?," +
			"locked=?,mtran_code=?,tax_type=? " +
			"where div_code=? and depo_code=? and mac_code=? ";
			
			ps1 = con.prepareStatement(query1);
			String query2 ="update partyacc set mgrp_code=?,mfix_disc1=?,mdays=?," +
			" mopng_bal=?,mopng_db=? " +
			" where div_code=? and depo_code=? and mac_code=? and fin_year=? ";
			
			ps2 = con.prepareStatement(query2);

			con.setAutoCommit(false);

			
			 
			ps1.setString(1,p.getMac_name());
			ps1.setString(2,p.getMadd1());
			ps1.setString(3,p.getMadd2());
			ps1.setString(4,p.getMadd3());
			ps1.setString(5,p.getMcity());
			ps1.setString(6,p.getMpin());
			ps1.setString(7,p.getMphone());
			ps1.setString(8,p.getMobile());
			ps1.setString(9,p.getMemail());
			ps1.setString(10,p.getMcontct());
			ps1.setString(11,p.getMbanker());
			ps1.setString(12,p.getMbank_add1());
			ps1.setString(13,p.getMbank_add2());
			ps1.setString(14,p.getBank_ifsc());
			ps1.setString(15,p.getMtranspt());
			ps1.setString(16,p.getGst_no());
			ps1.setString(17,p.getPan_no());
			ps1.setString(18,p.getMtype1()); // party type
			ps1.setInt(19,p.getDistance());
			ps1.setInt(20,p.getMstat_code());
			ps1.setString(21,p.getMstate_name());
			ps1.setInt(22,p.getMgrp_code());
			ps1.setString(23, p.getMtype2()); // lock
			ps1.setInt(24,p.getMtran_code());
			ps1.setString(25, p.getTax_type()); // tax type
			// where clause
			ps1.setInt(26,p.getMdiv_cd());
			ps1.setInt(27,p.getDepo_code());
			ps1.setString(28,p.getMac_code());
			 
			i= ps1.executeUpdate();


			ps2.setInt(1,p.getMgrp_code());
			ps2.setDouble(2,p.getMfix_disc1());
			ps2.setInt(3,p.getMdays());
			ps2.setDouble(4,p.getMopng_bal());
			ps2.setString(5,p.getMopng_db());
			// where clause
			ps2.setInt(6,p.getMdiv_cd());
			ps2.setInt(7,p.getDepo_code());
			ps2.setString(8,p.getMac_code());
			ps2.setInt(9,p.getFin_year());


			j= ps2.executeUpdate();


			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			ps2.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPartyDAO.update " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i+j);

				if(ps1 != null){ps1.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return (i+j);
	}	

	
	
	
	
	

	public List getPartyList(int depo_code,int div,int myear,int fyear,String nm)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		PartyDto p=null;

		try {

			con=ConnectionFactory.getConnection();

			String query2 =" select a.* from "+ 
			"(select f.mac_code,f.mac_name,f.madd1,f.madd2,f.madd3,f.mcity, "+
			"f.mpin,f.mphone,f.mobile,f.memail,f.mcontct, "+
			"f.mbanker,f.mbank_add1,f.mbank_add2,ifnull(f.bank_ifsc,''),f.mtranspt,f.gst_no,ifnull(f.mtype1,'N'),f.mstate_name,f.state_code, "+
			"a.mgrp_code,hd.gp_name,ifnull(f.locked,'N'),f.distance,a.mdays, "+
			"a.mopng_bal,a.mopng_db,ifnull(f.pan_no,''),ifnull(f.mtran_code,0),ifnull(f.tax_type,'L'),ifnull(a.mfix_disc1,0.00) "+ 
			"from partyfst as f left join partyacc as a on f.mac_code=a.mac_code and a.div_code=? and a.depo_code=? and a.fin_year=? and ifnull(a.del_tag,'')<>'D'" +
			",acchead as hd where "+
			"a.mgrp_code=hd.gp_code and f.div_code=? and f.depo_code=? and ifnull(f.del_tag,'')<>'D' "+
			"and ifnull(hd.del_tag,'')<>'D') a ";
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,fyear);
			ps2.setInt(4,div);
			ps2.setInt(5,depo_code);
			
			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			while (rs.next())
			{

				col= new Vector();
				col.addElement(rs.getString(1));//mac_code
				col.addElement(rs.getString(2)+","+rs.getString(6));//party name
				
				p = new PartyDto();
				p.setMac_code(rs.getString(1));
				p.setMac_name(rs.getString(2));
				p.setMadd1(rs.getString(3));
				p.setMadd2(rs.getString(4));
				p.setMadd3(rs.getString(5));
				p.setMcity(rs.getString(6));
				p.setMpin(rs.getString(7));
				p.setMphone(rs.getString(8));
				p.setMobile(rs.getString(9));
				p.setMemail(rs.getString(10));
				p.setMcontct(rs.getString(11));
				p.setMbanker(rs.getString(12));
				p.setMbank_add1(rs.getString(13));
				p.setMbank_add2(rs.getString(14));
				p.setBank_ifsc(rs.getString(15));
				p.setMtranspt(rs.getString(16));
				p.setGst_no(rs.getString(17));
				p.setMtype1(rs.getString(18));
				p.setMstate_name(rs.getString(19));
				p.setMstat_code(rs.getInt(20));
				p.setMgrp_code(rs.getInt(21));
				p.setGrp_name(rs.getString(22));
				p.setMtype2(rs.getString(23));  // lock
				p.setDistance(rs.getInt(24));
				p.setMdays(rs.getInt(25));
				p.setMopng_bal(rs.getDouble(26));
				p.setMopng_db(rs.getString(27));
				p.setPan_no(rs.getString(28));
				p.setMtran_code(rs.getInt(29));
				p.setTax_type(rs.getString(30));
				p.setMfix_disc1(rs.getDouble(31));
				col.addElement(p);/// add partyDto in hidden column
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
			System.out.println("-------------Exception in PartyDAO.getPartyList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in PartyDAO.Connection.close "+e);
			}
		}

		return v;
	}

	
	
	
	
	public boolean checkParty(int depo_code,int div,String nm)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		boolean check=false;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 = "select mac_code from partyfst where  div_code=? and " +
			"depo_code=? and mac_code=?" ;
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setString(3, nm);
			
			rs= ps2.executeQuery();
			if (rs.next())
			{
				 check=true;

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
			System.out.println("-------------Exception in PartyDAO.checkParty " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in PartyDAO.Connection.close "+e);
			}
		}

		return check;
	}


	
	
// TODO Party Acc for Change Year/New Year
	public int addPartyAcc(int year, int div , int depo)
	{

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		
		PreparedStatement ps3 = null;

		ResultSet rs1=null;
		ResultSet rs3=null;
		Connection con=null;

		int i=0;
		int j=0;

		try {

			con=ConnectionFactory.getConnection();


			String query1 ="select DEPO_CODE, MCMP_CODE, MDIV_CD, MGRP_CODE, MAC_CODE, MFIX_DISC1, MDAYS, MOPNG_BAL, "+ 
	        "MOPNG_DB, PTYPE, FIN_YEAR, div_code, del_tag, curr_bal, curr_db from partyacc where fin_year=? and div_code=? and depo_code=? ";
			
			ps1 = con.prepareStatement(query1);
			
			
			String query2 ="insert into partyacc (DEPO_CODE, MCMP_CODE, MDIV_CD, MGRP_CODE, MAC_CODE, MFIX_DISC1, MDAYS, MOPNG_BAL, "+ 
			"MOPNG_DB, PTYPE, FIN_YEAR, div_code, del_tag, curr_bal, curr_db) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps2 = con.prepareStatement(query2);

			String query3 ="select fin_year from partyacc where fin_year=? and div_code=? and depo_code=? ";
			
			ps3 = con.prepareStatement(query3);
			ps3.setInt(1,year);
			ps3.setInt(2,div);
			ps3.setInt(3, depo);
			
			System.out.println("year "+year +" div "+ div+ "depo "+depo);
			System.out.println("paty add new year "+year);
			con.setAutoCommit(false);
			rs3=ps3.executeQuery();
		   if(rs3.next())
		   {
			   System.out.println("record already hai ");
		   }
		   else
		   {
			   System.out.println("not found else part ");
			   	ps1.setInt(1,year-1);
				ps1.setInt(2,div);
				ps1.setInt(3, depo);
				rs1=ps1.executeQuery();
				while(rs1.next())
				{
					ps2.setInt(1,rs1.getInt(1));
					ps2.setInt(2,rs1.getInt(2));
					ps2.setInt(3,rs1.getInt(3));
					ps2.setInt(4,rs1.getInt(4));
					ps2.setInt(5,rs1.getInt(5));
					ps2.setDouble(6,rs1.getDouble(6));
					ps2.setInt(7,rs1.getInt(7));
					ps2.setDouble(8,rs1.getDouble(8));
					ps2.setString(9,rs1.getString(9));
					ps2.setString(10,rs1.getString(10));
					ps2.setInt(11,year);
					ps2.setInt(12,rs1.getInt(12));
					ps2.setString(13,rs1.getString(13));
					ps2.setDouble(14,rs1.getDouble(14));
					ps2.setString(15,rs1.getString(15));
					j= ps2.executeUpdate();
				} // end of while
				    rs1.close();

		   }// end of if

		   
			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			ps2.close();
			rs3.close();
			ps3.close();
		} catch (SQLException ex) {System.out.println("Add New PArty acc "+ex);
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPartyDAO.addPartyACc for New year " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rs1 != null){rs1.close();}
				if(ps2 != null){ps2.close();}
				if(ps3 != null){ps3.close();}
				if(rs3 != null){rs3.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return (i+j);
	}


	// TODO for Party Master Sales Package
	public ArrayList getPartyList(int depo_code,int div)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		ArrayList v=null;
		PartyDto p=null;
		int mgrp=0;
		try {

			con=ConnectionFactory.getConnection();
			
			//mgrp_code = 30+depo_code+00

			switch(div)
			{
				case 1:
					mgrp = Integer.parseInt("30"+depo_code+"00");
					break;
				case 2:
					mgrp = Integer.parseInt("33"+depo_code+"00");
					break;
				case 3:
					mgrp = Integer.parseInt("36"+depo_code+"00");
					break;
					
			}
			
//			String query2="select mac_code,mac_name,madd1,madd2,madd3,mcity,mstate_name,mpin,mphone,mtranspt,mpst_no,mcst_no,memail,mtype1 "+ 
//			"from partyfst where div_code=? and depo_code=? and mgrp_code=? ";
			
			
			 
			String query2 =" select a.*  from "+ 
			"(select f.mac_code,f.mac_name,f.madd1,f.madd2,f.madd3,f.mcity,f.mstate_name, "+
			"f.mpin,f.mphone,f.mtranspt,f.gst_no,'' mcst_no,f.memail,f.mtype1 "+
			"from partyfst as f left join partyacc as a on f.mac_code=a.mac_code and a.div_code=? and a.depo_code=? and a.fin_year=? and ifnull(a.del_tag,'')<>'D'" +
			",acchead as hd where "+
			"a.mgrp_code=hd.gp_code and f.div_code=? and f.depo_code=? and ifnull(f.del_tag,'')<>'D'  ) a  "+
			" order by a.mac_name ";

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,BaseClass.loginDt.getFin_year());
			ps2.setInt(4,div);
			ps2.setInt(5,depo_code);
			
			
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new ArrayList();
			while (rs.next())
			{

				p = new PartyDto();
				p.setMac_code(rs.getString(1));
				p.setMac_name(rs.getString(2));
				p.setMadd1(rs.getString(3));
				p.setMadd2(rs.getString(4));
				p.setMadd3(rs.getString(5));
				p.setMcity(rs.getString(6));
				p.setMstate_name(rs.getString(7));
				p.setMpin(rs.getString(8));
				p.setMphone(rs.getString(9));
				p.setMtranspt(rs.getString(10));
				p.setMemail(rs.getString(13));
				p.setMtype1(rs.getString(14));
				v.add(p);

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
			System.out.println("-------------Exception in PartyDAO.getPartyList - Sales Package " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in PartyDAO.Connection.close "+e);
			}
		}

		return v;
	}		


	
	public int deleteParty1(PartyDto p)
	{

		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int i=0;
		int j=0;

		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
			String query="select vac_code from ledfile where fin_year=? and div_code=? and vdepo_code=? and vac_code=? "+
			" and ifnull(del_tag,'')<>'D' ";

			
			
			String query1 ="update partyfst set del_tag=? where div_code=? and depo_code=? and mac_code=? ";
			
			String query2 ="update partyacc set del_tag=? where fin_year=? and div_code=? and depo_code=? and mac_code=? ";

			ps = con.prepareStatement(query);
			ps1 = con.prepareStatement(query1);
			ps2 = con.prepareStatement(query2);

			
			ps.setInt(1,p.getFin_year());
			ps.setInt(2,p.getMdiv_cd());
			ps.setInt(3,p.getDepo_code());
			ps.setString(4,p.getMac_code());
			rs=ps.executeQuery();
			if (rs.next())
			{
				System.out.println("yeha hai "+rs.getString(1));
			}
			else
			{
				System.out.println("yeha hai KYA ..........");
				ps1.setString(1,"D");
				ps1.setInt(2,p.getMdiv_cd());
				ps1.setInt(3,p.getDepo_code());
				ps1.setString(4,p.getMac_code());
				
				i= ps1.executeUpdate();

				ps2.setString(1,"D");
				ps2.setInt(2,p.getFin_year());
				ps2.setInt(3,p.getMdiv_cd());
				ps2.setInt(4,p.getDepo_code());
				ps2.setString(5,p.getMac_code());

				j= ps2.executeUpdate();
			}
			 


			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();
			ps1.close();
			ps2.close();
			 
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPartyDAO.delete -- Account Package " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i+j);
				if(rs != null){rs.close();}
				if(ps != null){ps.close();}

				if(ps1 != null){ps1.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return (i+j);
	}	
	

	public Vector getSmsNm(int depo,int div,int fyear,int myear,int code,int sdiv)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		PartyDto p=null;
		int i=0;
		String grp=null;
		try {


		 
				
			
			con=ConnectionFactory.getConnection();
			String query2 ="select f.mac_code,f.mac_name,f.mcity,a.mfix_disc1,a.mdays,"+ 
			"f.mbanker,f.mtranspt,a.ptype,f.gst_no,f.mdiv_cd,f.memail,f.mobile,0 mregion_cd,0 mdist_cd "+ 
			"from partyfst as f,partyacc as a  where f.mac_code=a.mac_code  "+
			"and ifnull(f.mtype1,'N')<>'Y'  "+
			"and f.div_code=? and f.depo_code=? and a.fin_year=? and ifnull(f.del_tag,'')<>'D' "+  
			"and a.div_code=? and a.depo_code=? and a.mgrp_code in (?,51) and ifnull(a.del_tag,'')<>'D'  "+
			"order by f.mac_name ";
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setInt(3, fyear);
			ps2.setInt(4, div);
			ps2.setInt(5, depo);
			ps2.setInt(6, code);
			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new Vector();
			while (rs.next())
			{
	
				
				p = new PartyDto();
				p.setMac_code(rs.getString(1));
				p.setMac_name(rs.getString(2)+","+rs.getString(3));
				p.setMcity(rs.getString(3));
				p.setMdays(rs.getInt(5));
				p.setMbanker(rs.getString(6));
				p.setMtranspt(rs.getString(7));
				p.setMtype1(rs.getString(8));
				p.setBranch_code(rs.getInt(10));
				p.setMemail(rs.getString(11));
				p.setMobile(rs.getString(12));
				p.setMregion_cd(rs.getInt(13));
				p.setMdist_cd(rs.getInt(14));
				
				v.add(p);
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
			System.out.println("-------------Exception in SQLPartyDAO.partylist " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return v;
	}
	
	public HashMap getSmsNmMap(int depo,int div,int fyear,int myear,int code,int sdiv)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		HashMap v=null;
		PartyDto p=null;
		int i=0;
		String grp=null;
		
		try {
			
			con=ConnectionFactory.getConnection();
			String query2 ="select f.mac_code,f.mac_name,f.mcity,a.mfix_disc1,a.mdays,"+ 
			"f.mbanker,f.mtranspt,a.ptype,f.gst_no,f.mdiv_cd,f.memail,f.mobile,0 mregion_cd,0 mdist_cd," +
			"f.madd1,f.madd2,f.madd3,f.mpin "+ 
			"from partyfst as f,partyacc as a  where f.mac_code=a.mac_code  "+
			"and ifnull(f.mtype1,'N')<>'Y'  "+
			"and f.div_code=? and f.depo_code=? and a.fin_year=? and ifnull(f.del_tag,'')<>'D' "+  
			"and a.div_code=? and a.depo_code=?  and ifnull(a.del_tag,'')<>'D'  "+
			"order by f.mac_name ";

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setInt(3, fyear);
			ps2.setInt(4, div);
			ps2.setInt(5, depo);
			//ps2.setInt(6, code);

			rs= ps2.executeQuery();
			con.setAutoCommit(false);
			v = new HashMap();
			while (rs.next())
			{
				p = new PartyDto();
				p.setMac_code(rs.getString(1));
				p.setMac_name(rs.getString(2)+","+rs.getString(3));
				p.setMcity(rs.getString(3));
				p.setMdays(rs.getInt(5));
				p.setMbanker(rs.getString(6));
				p.setMtranspt(rs.getString(7));
				p.setMtype1(rs.getString(8));
				p.setBranch_code(rs.getInt(10));
				p.setMemail(rs.getString(11));
				p.setMobile(rs.getString(12));
				p.setMregion_cd(rs.getInt(13));
				p.setMdist_cd(rs.getInt(14));
				p.setMadd1(rs.getString(15));
				p.setMadd2(rs.getString(16));
				p.setMadd3(rs.getString(17));
				p.setMpin(rs.getString(18));
				

				v.put(rs.getString(12),p);
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
			System.out.println("-------------Exception in SQLPartyDAO.partylist " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return v;
	}


	public int getPartyCode(int depo_code,int div,int grpcode)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		int code=0;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 = "select max(mac_code) from partyfst where  div_code=? and " +
			"depo_code=? and mgrp_code=?" ;
			
			
			ps = con.prepareStatement(query2);
			ps.setInt(1,div);
			ps.setInt(2, depo_code);
			ps.setInt(3, grpcode);
			
			rs= ps.executeQuery();
			if (rs.next())
			{
				 code=rs.getInt(1)+1;
			}

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
			System.out.println("-------------Exception in PartyDAO.getPartyCode " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in PartyDAO.Connection.close "+e);
			}
		}

		return code;
	}

	
	public String getMessage(int depo,int div,int fyear,int code)
	{

		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		String message=null; 


		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String currmonth = "select count(sinv_no)  from invsnd  where div_code=? and sdepo_code=? and sdoc_type=70  and schm_code=? and ifnull(del_tag,'')<>'D' " ;

			ps = con.prepareStatement(currmonth) ;
//			ps.setInt(1,fyear);
			ps.setInt(1,div );
			ps.setInt(2,depo);
			ps.setInt(3,code);
			rs =ps.executeQuery();
			
			 
			int count=0;	
			if (rs.next())
			{
				  count=rs.getInt(1);
			}
			
			   if(count<25)
			   {	
					  message="Allocation not Entered, Please Enter Allocation First";
			   }
			   else
			   {
				   	  message="";
			   }
			
			
			 
			 


			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();
			 
			 
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLPartyDAO.getMessage -- Sample Package " + ex);
			 
		}
		finally {
			try {
				 
				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLPartyDAO.Connection.close "+e);
			}
		}

		return message;
	}		

}
