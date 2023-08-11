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

public class CashVouDAO 
{
	
	public List getVouList(Date invDt,int depo_code,int div)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

/*			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"s.mac_name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno from ledfile as f "+
			"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and  ifnull(s.del_tag,'')<>'D' "+ 
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=10 and f.vou_date=? and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? order by f.vou_no ";  
*/

			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"s.mac_name,f.vnart_1,f.vnart_2,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_amt,f.tds_amt,f.bill_no,f.bill_date,ifnull(f.cr_amt,0)," +
			"ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0) from ledfile as f "+
			"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<> 'D' "+ 
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=10 and f.vou_date=? and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? order by f.vou_no ";  
					
					

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,div);
			ps2.setInt(4, depo_code);
			ps2.setDate(5, new java.sql.Date(invDt.getTime()));
			ps2.setInt(6,div);
			ps2.setInt(7, depo_code);
			rs= ps2.executeQuery();
			
			RcpDto rcp=null;
			v = new Vector();
			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;
			int vou=0;
			double total=0.00;
			while (rs.next())
			{
				 
				if (first)
				{
					vou=rs.getInt(3);
					inv = rs.getString(1)+"V"+rs.getString(3).substring(1);
					first=false;
					rcp= new RcpDto();
				}
				
				if (vou!=rs.getInt(3))
				{
					col= new Vector();
					col.addElement(inv);//concat inv_no
					col.addElement(rcp.getParty_name());//party name
	                col.addElement(rcp);
	                col.addElement(row);
					v.addElement(col);

					vou=rs.getInt(3);
					inv = rs.getString(1)+"V"+rs.getString(3).substring(1);
					total=0.00;
					rcp= new RcpDto();
					row = new Vector();   // Create new Table row
					
				}
				
				
				
				rcp.setVou_no(rs.getInt(3));
				rcp.setVou_date(rs.getDate(4));
				rcp.setParty_name(rs.getString(2));
				
				
				/// Table detail set here
/*				colum = new Vector();
				colum.addElement(false);   // del tag
				colum.addElement(rs.getString(5));   // exp code
				colum.addElement(rs.getString(6));  //exp  name
				colum.addElement(rs.getString(8));  //sub_head
				colum.addElement(rs.getString(9));  // supp name
				colum.addElement(rs.getString(10)); // narration
				colum.addElement(rs.getDouble(11));  //vamount
				colum.addElement(rs.getInt(12));  //vbook_cd
				colum.addElement(rs.getInt(13));  // vgrpcode
				colum.addElement(rs.getString(7));  // subcode 
				colum.addElement(rs.getInt(14));  // serialno
				row.addElement(colum);
*/				
				
				
				/////////
				colum = new Vector();
				colum.addElement(false);   // del tag   0
				colum.addElement(rs.getString(5));   // exp code 1
				colum.addElement(rs.getString(6));  //exp  name 2
				colum.addElement(rs.getString(8));  //sub_head 3
				colum.addElement(rs.getString(9));  // supp name 4
				colum.addElement(rs.getString(17)); // bill no 5
				try
				{
					colum.addElement(sdf.format(rs.getDate(18))); // bill date 6
					
				}
				catch (Exception e)
				{
					colum.addElement("  /  /    "); // bill date 6
					
				}
				colum.addElement(rs.getString(10)); // narration 7
				colum.addElement(rs.getDouble(15));  //bill_amt 8

				colum.addElement(rs.getDouble(19));  //taxable 9
				colum.addElement(rs.getDouble(20));  //service tax 10

				colum.addElement(rs.getDouble(16));  //tds 11
				colum.addElement(rs.getDouble(11));  //vamount 12
				colum.addElement(rs.getInt(12));  //vbook_cd 13
				colum.addElement(rs.getInt(13));  // vgrpcode 14
				colum.addElement(rs.getString(7));  // subcode 15
				colum.addElement(rs.getInt(14));  // serialno 16
				colum.addElement(rs.getDouble(21));  //bill_per      17
				colum.addElement(rs.getDouble(22));  //service tax per 18
				row.addElement(colum);

				////////////
				
				
				total+=rs.getDouble(11);
				rcp.setVamount(total);
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

		} catch (SQLException ex) { ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in CashVouDAO.getVouList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return v;
	}	
	
	

	public RcpDto getVouDetail(int startVoucher,int depo_code,int div,int fyear)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"s.mac_name,f.vnart_1,f.vnart_2,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_amt,f.tds_amt,f.bill_no,f.bill_date," +
			"ifnull(f.cr_amt,0)," +
			"ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0)" +
			" from ledfile as f "+
			"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<> 'D' "+ 
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=10 and f.vou_no=? and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? order by f.vou_no ";  

					
					

			ps2 = con.prepareStatement(query2);
			
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,fyear);
			ps2.setInt(4,div);
			ps2.setInt(5, depo_code);
			ps2.setInt(6,startVoucher); 
			ps2.setInt(7,div);
			ps2.setInt(8, depo_code);
			rs= ps2.executeQuery();
			
			
		 
			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;
			 
			while (rs.next())
			{
				 
				if (first)
				{
					first=false;
					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(3));
					rcp.setVou_date(rs.getDate(4));
					rcp.setParty_name(rs.getString(2));
				}
				
				 
				
				
				 
				
				/// Table detail set here
				colum = new Vector();
				colum.addElement(false);   // del tag   0
				colum.addElement(rs.getString(5));   // exp code 1
				colum.addElement(rs.getString(6));  //exp  name 2
				colum.addElement(rs.getString(8));  //sub_head 3
				colum.addElement(rs.getString(9));  // supp name 4
				colum.addElement(rs.getString(17)); // bill no 5
				try
				{
					colum.addElement(sdf.format(rs.getDate(18))); // bill date 6
					
				}
				catch (Exception e)
				{
					colum.addElement("  /  /    "); // bill date 6
					
				}
				colum.addElement(rs.getString(10)); // narration 7
				colum.addElement(rs.getDouble(15));  //bill_amt 8

				colum.addElement(rs.getDouble(19));  //taxable      9
				colum.addElement(rs.getDouble(20));  //service tax  10

				colum.addElement(rs.getDouble(16));  //tds 11
				colum.addElement(rs.getDouble(11));  //vamount 12
				colum.addElement(rs.getInt(12));  //vbook_cd 13
				colum.addElement(rs.getInt(13));  // vgrpcode 14
				colum.addElement(rs.getString(7));  // subcode 15
				colum.addElement(rs.getInt(14));  // serialno 16
				colum.addElement(rs.getDouble(21));  //bill_per      17
				colum.addElement(rs.getDouble(22));  //service tax per 18
				row.addElement(colum);
				total+=rs.getDouble(11);
				

			}

			if (rcp!=null)
			{
				rcp.setVamount(total);
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
			System.out.println("-------------Exception in CashVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		
	
	
	public List getMultipleVouDetail(int startVoucher,int endVoucher,int depo_code,int div,int fyear,int doctp,String vdbcr)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		List voucherList=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.tds_amt,f.bill_no,f.bill_date from ledfile as f "+
			"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and  ifnull(s.del_tag,'')<>'D' "+ 
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=? and f.vou_no between ? and ? and f.vdbcr=? and  ifnull(f.del_tag,'')<>'D' " +
			"and p.div_code=? and p.depo_code=? order by f.vou_no,f.serialno";  


			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,fyear);
			ps2.setInt(4,div);
			ps2.setInt(5, depo_code);
			ps2.setInt(6, doctp);
			ps2.setInt(7,startVoucher); 
			ps2.setInt(8,endVoucher);
			ps2.setString(9, vdbcr);
			ps2.setInt(10,div);
			ps2.setInt(11, depo_code);
			
			rs= ps2.executeQuery();
			
			voucherList = new ArrayList(); 
			int wno = 0;
			boolean first=true;
			double rgross = 0.00;
			
			while (rs.next())
			{
				 
				if(first)
				{
					wno=rs.getInt(3);
					first=false;
				}
				
				if(wno!=rs.getInt(3))
				{
					rcp= new RcpDto();
					rcp.setDash(1);
					rcp.setVamount(rgross);
					voucherList.add(rcp);
		            rgross=0.00;
		            wno=rs.getInt(3);
				}
				
				rcp= new RcpDto();
				rcp.setVou_no(rs.getInt(3));
				rcp.setVou_date(rs.getDate(4));
				rcp.setParty_name(rs.getString(2));
//				rcp.setVou_lo("PC");
				rcp.setVou_lo(rs.getString(1));
				rcp.setExp_code(rs.getString(5));
				rcp.setExp_name(rs.getString(6));
				rcp.setVnart1(rs.getString(9));
				rcp.setVnart2(rs.getString(10));
				rcp.setVamount(rs.getDouble(11));
				rcp.setTds_amt(rs.getDouble(15));
				rcp.setBill_no(rs.getString(16));
				rcp.setBill_date(rs.getDate(17));
				rcp.setDash(0);
				rgross+=rs.getDouble(11);
				
				voucherList.add(rcp);
			}

			
			rcp= new RcpDto();
			rcp.setDash(2);
			rcp.setVamount(rgross);
			voucherList.add(rcp);
            
            
            
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
			System.out.println("-------------Exception in CashVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return voucherList;
	}		
	
	
	
	
	
	//// method to get cash/pur/bank vou no for account package 
	public int getVouNo(int depo,int div,int year,int bkcd,String vdbcr)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		try {
			con=ConnectionFactory.getConnection();
			String query1 ="select ifnull(max(vou_no),0) from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vdbcr=? and  ifnull(del_tag,'')<>'D' "; 
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setInt(4, bkcd);
			ps1.setString(5, vdbcr);
			
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
			System.out.println("-------------Exception in CashVouDAO.getVouNo " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 
	

	//// method to get Current cash Balance
	public double getCashBalance(int depo,int div,int year)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		double bal=0;
		System.out.println("depo code "+depo);
		String code="040"+depo+"01";
		try {
			con=ConnectionFactory.getConnection();
//			String query1 ="select curr_bal from partyacc where fin_year=? and  div_code=? and depo_code=? and mac_code=? and  ifnull(del_tag,'')<>'D' "; 
			String query1 ="select sum(if(vdbcr='DR',vamount,(vamount*-1))) bal from ledfile " +
			"where fin_year=? and div_Code=? and vdepo_code=? "+  
			"and vac_code=? and vbook_cd1<>30 and ifnull(del_tag,'')<>'D'" ; 
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setString(4, code);
			
			rst =ps1.executeQuery();

			if(rst.next()){
				bal =rst.getDouble(1); 
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
			System.out.println("-------------Exception in CashVouDAO.getCashBalance " + ex);
			bal=0;
		}
		finally {
			try {
				System.out.println("Current Balance is  : "+bal);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return bal;
	}	 
	
	
	//// method to update Current cash Balance
	public int updateBalance(int depo,int div,int year,double tot)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		String code="040"+depo+"01";
		try {
			con=ConnectionFactory.getConnection();
			String query1 ="update partyacc set curr_bal=curr_bal-? where fin_year=? and  div_code=? and depo_code=? and mac_code=? and  ifnull(del_tag,'')<>'D' "; 
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setDouble(1, tot);
			ps1.setInt(2, year);
			ps1.setInt(3, div);
			ps1.setInt(4, depo);
			ps1.setString(5, code);
			i=ps1.executeUpdate();
			

			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in CashVouDAO.updateBalance " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 
		

	
  //// method to add record in led file (cash voucher)	
	public int addLed(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		RcpDto rcp=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			

			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
			"vou_date,vgrp_code,vac_code,exp_code,sub_code,vnart_1,vnart_2,name,vamount,vdbcr, " +
			"Created_by,Created_date,serialno,fin_year,mnth_code,bill_amt,tds_amt,bill_no,bill_date,cr_amt,sertax_amt,sertax_billper,sertax_per) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

		    // 040+depo+01
			
			ledps=con.prepareStatement(led);
			int size = cList.size();
			for(int j=0;j<size;j++)
			{
				System.out.println("size of list "+cList.size());
				rcp = (RcpDto) cList.get(j);
				ledps.setInt(1, rcp.getDiv_code());
				ledps.setInt(2, rcp.getVdepo_code());
				ledps.setInt(3, rcp.getVbook_cd());
				ledps.setInt(4, rcp.getVbook_cd1());
				ledps.setString(5, rcp.getVou_lo());
				ledps.setInt(6, rcp.getVou_no());
				ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps.setInt(8,rcp.getVgrp_code()); // vgrp_Code
				ledps.setString(9, rcp.getVac_code());
				ledps.setString(10, rcp.getExp_code());
				ledps.setString(11, rcp.getSub_code());
				ledps.setString(12, rcp.getVnart1());
				ledps.setString(13, rcp.getVnart2());
				ledps.setString(14, rcp.getParty_name());
				ledps.setDouble(15, rcp.getVamount());
				ledps.setString(16, "CR");
				ledps.setInt(17, rcp.getCreated_by());
				ledps.setDate(18, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(19, rcp.getSerialno());
				ledps.setInt(20, rcp.getFin_year());
				ledps.setInt(21, rcp.getMnth_code());
				ledps.setDouble(22, rcp.getBill_amt());
				ledps.setDouble(23, rcp.getTds_amt());
				ledps.setString(24, rcp.getBill_no());
				if (rcp.getBill_date()!=null)
					ledps.setDate(25, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(25, null);
				ledps.setDouble(26, rcp.getCr_amt());  // taxable service tax
				ledps.setDouble(27, rcp.getSertax_amt());
				ledps.setDouble(28, rcp.getSertax_billper());
				ledps.setDouble(29, rcp.getSertax_per());

				i=ledps.executeUpdate();
			}
			i=rcp.getVou_no();
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
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
	
	
	
	
  //// method to update record in led file (cash voucher)	
		public int updateLed(ArrayList cList)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;
			RcpDto rcp=null;
			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				

				String led="update  ledfile set vbook_cd=?,vou_date=?,vgrp_code=?,exp_code=?," +
				"sub_code=?,vnart_1=?,vnart_2=?,name=?,vamount=?,modified_by=?," +
				"modified_date=?,bill_amt=?,tds_amt=?,bill_no=?,bill_date=?,cr_amt=?,sertax_amt=?,sertax_billper=?,sertax_per=?  " +
				"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
				"and vou_no=? and serialno=?";

				
				ledps=con.prepareStatement(led);
				
				for(int j=0;j<cList.size();j++)
				{
					rcp = (RcpDto) cList.get(j);
					ledps.setInt(1, rcp.getVbook_cd());
					ledps.setDate(2, new java.sql.Date(rcp.getVou_date().getTime()));
					ledps.setInt(3,rcp.getVgrp_code()); // vgrp_Code
					ledps.setString(4, rcp.getExp_code());
					ledps.setString(5, rcp.getSub_code());
					ledps.setString(6, rcp.getVnart1());
					ledps.setString(7, rcp.getVnart2());
					ledps.setString(8, rcp.getParty_name());
					ledps.setDouble(9, rcp.getVamount());
					ledps.setInt(10, rcp.getModified_by());
					ledps.setDate(11, new java.sql.Date(rcp.getModified_date().getTime()));
					ledps.setDouble(12, rcp.getBill_amt());
					ledps.setDouble(13, rcp.getTds_amt());
					ledps.setString(14, rcp.getBill_no());
					if (rcp.getBill_date()!=null)
						ledps.setDate(15, new java.sql.Date(rcp.getBill_date().getTime()));
					else
						ledps.setDate(15, null);
					ledps.setDouble(16, rcp.getCr_amt());  // taxable service tax
					ledps.setDouble(17, rcp.getSertax_amt());
					ledps.setDouble(18, rcp.getSertax_billper());
					ledps.setDouble(19, rcp.getSertax_per());
						
					// where clause
					ledps.setInt(20, rcp.getFin_year());
					ledps.setInt(21, rcp.getDiv_code());
					ledps.setInt(22, rcp.getVdepo_code());
					ledps.setInt(23, rcp.getVbook_cd1());
					ledps.setInt(24, rcp.getVou_no());
					ledps.setInt(25, rcp.getSerialno());
					
					
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
				System.out.println("-------------Exception in SQLRcpDAO.updateLed " + ex);
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

		
		 //// method to update record in led file (cash voucher)	
		public int deleteLed(ArrayList cList)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;
			PreparedStatement totps=null;
			ResultSet totrs=null;
			
			RcpDto rcp=null;
			double tot=0.00;
			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				


				String totquery="select vamount from  ledfile " +
				"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
				"and vou_no=? and serialno=?";
				

				String led="update ledfile set del_tag=?,modified_by=?,modified_date=?  " +
				"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
				"and vou_no=? and serialno=?";

				
				totps=con.prepareStatement(totquery);
				for(int j=0;j<cList.size();j++)
				{
					rcp = (RcpDto) cList.get(j);				
					totps.setInt(1, rcp.getFin_year());
					totps.setInt(2, rcp.getDiv_code());
					totps.setInt(3, rcp.getVdepo_code());
					totps.setInt(4, rcp.getVbook_cd1());
					totps.setInt(5, rcp.getVou_no());
					totps.setInt(6, rcp.getSerialno());
					
					totrs=totps.executeQuery();
					
					if(totrs.next())
					{
						tot+=totrs.getDouble(1);
						
					}
				
					totrs.close();
					
				} //end of for 
					totps.close();
				
				ledps=con.prepareStatement(led);
				
				for(int j=0;j<cList.size();j++)
				{
					rcp = (RcpDto) cList.get(j);
					 
					ledps.setString(1, "D");
					ledps.setInt(2, rcp.getModified_by());
					ledps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));

					// where clause
					ledps.setInt(4, rcp.getFin_year());
					ledps.setInt(5, rcp.getDiv_code());
					ledps.setInt(6, rcp.getVdepo_code());
					ledps.setInt(7, rcp.getVbook_cd1());
					ledps.setInt(8, rcp.getVou_no());
					ledps.setInt(9, rcp.getSerialno());
					
					
					i=ledps.executeUpdate();
				}
	       
				   
				if (rcp!=null)
				{
					updateBalance(rcp.getVdepo_code(),rcp.getDiv_code(),rcp.getFin_year(),(tot*-1));
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
				System.out.println("-------------Exception in SQLRcpDAO.deleteLed " + ex);
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
	

	public int addCashRcp(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,exp_code,vnart_1,name,vamount,vdbcr, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,sub_div,vou_yr) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


			
			ledps=con.prepareStatement(led);
			ledps.setInt(1, rcp.getDiv_code());
			ledps.setInt(2, rcp.getVdepo_code());
			ledps.setInt(3, rcp.getVbook_cd());
			ledps.setInt(4, rcp.getVbook_cd1());
			ledps.setString(5, rcp.getVou_lo());
			ledps.setInt(6, rcp.getVou_no());
			ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setInt(8,rcp.getVgrp_code()); // vgrp_Code
			ledps.setString(9, rcp.getVac_code());
			ledps.setString(10, rcp.getExp_code());
			ledps.setString(11, rcp.getVnart1());

			ledps.setString(12, rcp.getParty_name());  // paid to
			ledps.setDouble(13, rcp.getVamount());
			ledps.setString(14, "DR");
			ledps.setInt(15, rcp.getCreated_by());
			ledps.setDate(16, new java.sql.Date(rcp.getCreated_date().getTime()));
			ledps.setInt(17, rcp.getSerialno());
			ledps.setInt(18, rcp.getFin_year());
			ledps.setInt(19, rcp.getMnth_code());
			ledps.setInt(20, 1); // div code of mf/tf/div
			ledps.setInt(21, rcp.getVou_yr()); 
		

			i=ledps.executeUpdate();


			
			
			i=rcp.getVou_no();
			
			con.commit();
			con.setAutoCommit(true);
			ledps.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLCashDAO.addCashRcp Voucher" + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close Debit Note Voucher "+e);
			}
		}

		return (i);
	}				
	
	
	public int updateCashRcp(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



			String led="update  ledfile set vou_date=?,exp_code=?," +
					"vnart_1=?,name=?,vamount=?,modified_by=?,modified_date=? " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR' and ifnull(del_tag,'')<>'D' ";


			ledps=con.prepareStatement(led);

			ledps.setDate(1, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setString(2, rcp.getExp_code());
			ledps.setString(3, rcp.getVnart1());
			ledps.setString(4, rcp.getParty_name());
			ledps.setDouble(5, rcp.getVamount());
			ledps.setInt(6, rcp.getModified_by());
			ledps.setDate(7, new java.sql.Date(rcp.getModified_date().getTime()));


			// where clause
			ledps.setInt(8, rcp.getFin_year());
			ledps.setInt(9, rcp.getDiv_code());
			ledps.setInt(10, rcp.getVdepo_code());
			ledps.setInt(11, rcp.getVbook_cd1());
			ledps.setInt(12, rcp.getVou_no());


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
			System.out.println("-------------Exception in SQLRcpDAO.updateDebitNote " + ex);
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
	
	public RcpDto getCashRcpDetail(int vou,int depo_code,int div,int fyear,int bkcd)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		String code=null;
		try {


			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.exp_code,f.name," +
					" f.vnart_1,f.vamount from ledfile as f "+
					" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
					" f.vbook_cd1=? and f.vou_no=? and f.vdbcr='DR' and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,vou); 

			rs= ps2.executeQuery();

			if (rs.next())
			{
				rcp= new RcpDto();
				rcp.setVou_no(rs.getInt(2));
				rcp.setVou_date(rs.getDate(3));
				rcp.setVac_code(rs.getString(4));
				rcp.setParty_name(rs.getString(5));
				rcp.setVnart1(rs.getString(6));
				rcp.setChq_amt(rs.getDouble(7));
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
			System.out.println("-------------Exception in BankVouDAO.getDebitNoteDetail Bank Receipt Voucher " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close Debit Note Detail Voucher  "+e);
			}
		}

		return rcp;
	}			

	public int deleteLed(int div,int depo,int vou, int fyear,int uid,int bkcd)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;




		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String led="update ledfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR'  ";

			
			ledps=con.prepareStatement(led);



			ledps.setString(1, "D");
			ledps.setInt(2, uid);
			// where clause
			ledps.setInt(3,fyear); 
			ledps.setInt(4,div);
			ledps.setInt(5, depo);
			ledps.setInt(6, bkcd);
			ledps.setInt(7, vou);

			i=ledps.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			ledps.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLCashVouDAO.deleteLed " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLCashVouDAO.Connection.close "+e);
			}
		}

		return (i);
	}		

	public Vector getVouList2(Date vdate,int depo_code,int div,int bkcd)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		Vector v=null;
		Vector col=null;
		String code=null;
		try {


			code="040"+depo_code+"01";
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.vac_code,f.name," +
					"f.vnart_1,f.vamount from ledfile as f "+
					"where  f.div_code=? and f.vdepo_code=? and "+ 
					"f.vbook_cd1=? and f.vou_date=? and vdbcr='DR' and  ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

//					"f.vbook_cd1=? and f.vou_date=? and vdbcr='CR' and vac_code<>? and  ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,bkcd); 
			ps2.setDate(4,new java.sql.Date(vdate.getTime())); 

			rs= ps2.executeQuery();

			v = new Vector();
			String inv=null;

			while (rs.next())
			{
				inv = rs.getString(1).substring(0,1)+"R"+rs.getString(1).substring(1,2)+rs.getString(2).substring(1);

				rcp= new RcpDto();
				rcp.setVou_no(rs.getInt(2));
				rcp.setVou_date(rs.getDate(3));
				rcp.setVac_code(rs.getString(4));
				rcp.setParty_name(rs.getString(5));
				rcp.setVnart1(rs.getString(6));
				rcp.setChq_amt(rs.getDouble(7));
				
				col= new Vector();
				col.addElement(inv);//concat inv_no
				col.addElement(rcp.getParty_name());//party name
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
			System.out.println("-------------Exception in CashVouDAO.getVouDetail2 Bank Receipt Voucher " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close Bank Receipt Voucher  "+e);
			}
		}

		return v;
	}			

	public int addLedHO(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		RcpDto rcp=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			

			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
			"vou_date,vgrp_code,vac_code,exp_code,sub_code,vnart_1,vnart_2,name,vamount,vdbcr, " +
			"Created_by,Created_date,serialno,fin_year,mnth_code,bill_amt,tds_amt,bill_no,bill_date,cr_amt,sertax_amt,sertax_billper,sertax_per,vter_cd) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

		    // 040+depo+01
			
			ledps=con.prepareStatement(led);
			int size = cList.size();
			for(int j=0;j<size;j++)
			{
				System.out.println("size of list "+cList.size());
				rcp = (RcpDto) cList.get(j);
				ledps.setInt(1, rcp.getDiv_code());
				ledps.setInt(2, rcp.getVdepo_code());
				ledps.setInt(3, rcp.getVbook_cd());
				ledps.setInt(4, rcp.getVbook_cd1());
				ledps.setString(5, rcp.getVou_lo());
				ledps.setInt(6, rcp.getVou_no());
				ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps.setInt(8,rcp.getVgrp_code()); // vgrp_Code
				ledps.setString(9, rcp.getVac_code());
				ledps.setString(10, rcp.getExp_code());
				ledps.setString(11, rcp.getSub_code());
				ledps.setString(12, rcp.getVnart1());
				ledps.setString(13, rcp.getVnart2());
				ledps.setString(14, rcp.getParty_name());
				ledps.setDouble(15, rcp.getVamount());
				ledps.setString(16, "CR");
				ledps.setInt(17, rcp.getCreated_by());
				ledps.setDate(18, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(19, rcp.getSerialno());
				ledps.setInt(20, rcp.getFin_year());
				ledps.setInt(21, rcp.getMnth_code());
				ledps.setDouble(22, rcp.getBill_amt());
				ledps.setDouble(23, rcp.getTds_amt());
				ledps.setString(24, rcp.getBill_no());
				if (rcp.getBill_date()!=null)
					ledps.setDate(25, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(25, null);
				ledps.setDouble(26, rcp.getCr_amt());  // taxable service tax
				ledps.setDouble(27, rcp.getSertax_amt());
				ledps.setDouble(28, rcp.getSertax_billper());
				ledps.setDouble(29, rcp.getSertax_per());
				ledps.setInt(30, rcp.getVter_cd()); // type ho/mumbai/genetica

				i=ledps.executeUpdate();
			}
			i=rcp.getVou_no();
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
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

	public int getCashLock(int div_code,int bank_code)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		int locked=0;
		try {

			con=ConnectionFactory.getConnection();

			con.setAutoCommit(false);

			String query2 ="select locked from locktable where div_code=? and doc_type=? ";  
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, div_code);
			ps2.setInt(2, bank_code);
			
			rs= ps2.executeQuery();
			
			if(rs.next())
			{
				locked=rs.getInt(1);
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
			System.out.println("-------------Exception in ChequeDAO.getChequelock " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in ChequeDAO.Connection.close "+e);
			}
		}

		return locked;
	}
	public int updateCashLock(int div_code,int bank_code,int lock)
	{
		PreparedStatement ps2 = null;
		Connection con=null;
		int i=0;
		try {

			con=ConnectionFactory.getConnection();

			con.setAutoCommit(false);

			String query2 ="update locktable set locked=?  where div_code=? and doc_type=? ";  
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, lock);
			ps2.setInt(2, div_code);
			ps2.setInt(3, bank_code);
			
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
			System.out.println("-------------Exception in ChequeDAO.getChequelock " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in ChequeDAO.Connection.close "+e);
			}
		}

		return i;
	}

	public RcpDto getVouDetailHO(int startVoucher,int depo_code,int div,int fyear,int type,int mnthcode)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"s.mac_code,f.vnart_1,f.vnart_2,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_amt,f.tds_amt,f.bill_no,f.bill_date," +
			"ifnull(f.cr_amt,0)," +
			"ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0) from ledfile as f "+
			"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<> 'D' "+ 
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=10 and f.vou_no=? and f.vter_cd=? and f.mnth_code =? and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? order by f.vou_no ";  

					
					

			ps2 = con.prepareStatement(query2);
			
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,fyear);
			ps2.setInt(4,div);
			ps2.setInt(5, depo_code);
			ps2.setInt(6,startVoucher); 
			ps2.setInt(7,type);
			ps2.setInt(8,mnthcode);
			ps2.setInt(9,div);
			ps2.setInt(10, depo_code);
			rs= ps2.executeQuery();
			
			
		 
			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;
			 
			while (rs.next())
			{
				 
				if (first)
				{
					first=false;
					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(3));
					rcp.setVou_date(rs.getDate(4));
					rcp.setParty_name(rs.getString(2));
					rcp.setVou_lo(rs.getString(1)+rs.getString(3).substring(1));
					
				}
				
				 
				
				
				 
				
				/// Table detail set here
				colum = new Vector();
				colum.addElement(false);   // del tag   0
				colum.addElement(rs.getString(5));   // exp code 1
				colum.addElement(rs.getString(6));  //exp  name 2
				colum.addElement(rs.getString(8));  //sub_head 3
				colum.addElement(rs.getString(9));  // supp name 4
				colum.addElement(rs.getString(17)); // bill no 5
				try
				{
					colum.addElement(sdf.format(rs.getDate(18))); // bill date 6
					
				}
				catch (Exception e)
				{
					colum.addElement("  /  /    "); // bill date 6
					
				}
				colum.addElement(rs.getString(10)); // narration 7
				colum.addElement(rs.getDouble(15));  //bill_amt 8

				colum.addElement(rs.getDouble(19));  //taxable      9
				colum.addElement(rs.getDouble(20));  //service tax  10

				colum.addElement(rs.getDouble(16));  //tds 11
				colum.addElement(rs.getDouble(11));  //vamount 12
				colum.addElement(rs.getInt(12));  //vbook_cd 13
				colum.addElement(rs.getInt(13));  // vgrpcode 14
				colum.addElement(rs.getString(7));  // subcode 15
				colum.addElement(rs.getInt(14));  // serialno 16
				colum.addElement(rs.getDouble(21));  //bill_per      17
				colum.addElement(rs.getDouble(22));  //service tax per 18
				row.addElement(colum);
				total+=rs.getDouble(11);
				

			}

			if (rcp!=null)
			{
				rcp.setVamount(total);
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
			System.out.println("-------------Exception in CashVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		
	public List getVouListHO(Date invDt,Date endDt,int depo_code,int div,int type,int mnthcode)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"s.mac_code,f.vnart_1,f.vnart_2,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_amt,f.tds_amt,f.bill_no,f.bill_date,ifnull(f.cr_amt,0)," +
			"ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0) from ledfile as f "+
			"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<> 'D' "+ 
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=10 and f.vou_date between ? and ? and f.vter_cd=? and f.mnth_Code=? and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? order by f.vou_no ";  
					
					

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,div);
			ps2.setInt(4, depo_code);
			ps2.setDate(5, new java.sql.Date(invDt.getTime()));
			ps2.setDate(6, new java.sql.Date(endDt.getTime()));
			ps2.setInt(7,type);
			ps2.setInt(8,mnthcode);
			ps2.setInt(9,div);
			ps2.setInt(10, depo_code);
			rs= ps2.executeQuery();
			
			RcpDto rcp=null;
			v = new Vector();
			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;
			int vou=0;
			double total=0.00;
			while (rs.next())
			{
				 
				if (first)
				{
					vou=rs.getInt(3);
					inv = rs.getString(1)+rs.getString(3).substring(1);
					first=false;
					rcp= new RcpDto();
				}
				
				if (vou!=rs.getInt(3))
				{
					col= new Vector();
					col.addElement(inv);//concat inv_no
					col.addElement(rcp.getParty_name());//party name
	                col.addElement(rcp);
	                col.addElement(row);
					v.addElement(col);

					vou=rs.getInt(3);
					inv = rs.getString(1)+rs.getString(3).substring(1);
					total=0.00;
					rcp= new RcpDto();
					row = new Vector();   // Create new Table row
					
				}
				
				
				
				rcp.setVou_no(rs.getInt(3));
				rcp.setVou_date(rs.getDate(4));
				rcp.setParty_name(rs.getString(2));
				rcp.setVou_lo(inv);
				
				/// Table detail set here
/*				colum = new Vector();
				colum.addElement(false);   // del tag
				colum.addElement(rs.getString(5));   // exp code
				colum.addElement(rs.getString(6));  //exp  name
				colum.addElement(rs.getString(8));  //sub_head
				colum.addElement(rs.getString(9));  // supp name
				colum.addElement(rs.getString(10)); // narration
				colum.addElement(rs.getDouble(11));  //vamount
				colum.addElement(rs.getInt(12));  //vbook_cd
				colum.addElement(rs.getInt(13));  // vgrpcode
				colum.addElement(rs.getString(7));  // subcode 
				colum.addElement(rs.getInt(14));  // serialno
				row.addElement(colum);
*/				
				
				
				/////////
				colum = new Vector();
				colum.addElement(false);   // del tag   0
				colum.addElement(rs.getString(5));   // exp code 1
				colum.addElement(rs.getString(6));  //exp  name 2
				colum.addElement(rs.getString(8));  //sub_head 3
				colum.addElement(rs.getString(9));  // supp name 4
				colum.addElement(rs.getString(17)); // bill no 5
				try
				{
					colum.addElement(sdf.format(rs.getDate(18))); // bill date 6
					
				}
				catch (Exception e)
				{
					colum.addElement("  /  /    "); // bill date 6
					
				}
				colum.addElement(rs.getString(10)); // narration 7
				colum.addElement(rs.getDouble(15));  //bill_amt 8

				colum.addElement(rs.getDouble(19));  //taxable 9
				colum.addElement(rs.getDouble(20));  //service tax 10

				colum.addElement(rs.getDouble(16));  //tds 11
				colum.addElement(rs.getDouble(11));  //vamount 12
				colum.addElement(rs.getInt(12));  //vbook_cd 13
				colum.addElement(rs.getInt(13));  // vgrpcode 14
				colum.addElement(rs.getString(7));  // subcode 15
				colum.addElement(rs.getInt(14));  // serialno 16
				colum.addElement(rs.getDouble(21));  //bill_per      17
				colum.addElement(rs.getDouble(22));  //service tax per 18
				row.addElement(colum);

				////////////
				
				
				total+=rs.getDouble(11);
				rcp.setVamount(total);
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

		} catch (SQLException ex) { ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in CashVouDAO.getVouList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return v;
	}	

	public int updateLedHO(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		RcpDto rcp=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			

			String led="update  ledfile set vbook_cd=?,vou_date=?,vgrp_code=?,exp_code=?," +
			"sub_code=?,vnart_1=?,vnart_2=?,name=?,vamount=?,modified_by=?," +
			"modified_date=?,bill_amt=?,tds_amt=?,bill_no=?,bill_date=?,cr_amt=?,sertax_amt=?,sertax_billper=?,sertax_per=?  " +
			"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
			"and vou_no=? and serialno=? and vter_cd=? and mnth_code=? ";

			
			ledps=con.prepareStatement(led);
			
			for(int j=0;j<cList.size();j++)
			{
				rcp = (RcpDto) cList.get(j);
				ledps.setInt(1, rcp.getVbook_cd());
				ledps.setDate(2, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps.setInt(3,rcp.getVgrp_code()); // vgrp_Code
				ledps.setString(4, rcp.getExp_code());
				ledps.setString(5, rcp.getSub_code());
				ledps.setString(6, rcp.getVnart1());
				ledps.setString(7, rcp.getVnart2());
				ledps.setString(8, rcp.getParty_name());
				ledps.setDouble(9, rcp.getVamount());
				ledps.setInt(10, rcp.getModified_by());
				ledps.setDate(11, new java.sql.Date(rcp.getModified_date().getTime()));
				ledps.setDouble(12, rcp.getBill_amt());
				ledps.setDouble(13, rcp.getTds_amt());
				ledps.setString(14, rcp.getBill_no());
				if (rcp.getBill_date()!=null)
					ledps.setDate(15, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(15, null);
				ledps.setDouble(16, rcp.getCr_amt());  // taxable service tax
				ledps.setDouble(17, rcp.getSertax_amt());
				ledps.setDouble(18, rcp.getSertax_billper());
				ledps.setDouble(19, rcp.getSertax_per());
					
				// where clause
				ledps.setInt(20, rcp.getFin_year());
				ledps.setInt(21, rcp.getDiv_code());
				ledps.setInt(22, rcp.getVdepo_code());
				ledps.setInt(23, rcp.getVbook_cd1());
				ledps.setInt(24, rcp.getVou_no());
				ledps.setInt(25, rcp.getSerialno());
				ledps.setInt(26, rcp.getVter_cd());
				ledps.setInt(27, rcp.getMnth_code());
				
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
			System.out.println("-------------Exception in SQLRcpDAO.updateLed " + ex);
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

	 //// method to update record in led file (cash voucher)	
	public int deleteLedHO(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement totps=null;
		ResultSet totrs=null;
		
		RcpDto rcp=null;
		double tot=0.00;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			


			String totquery="select vamount from  ledfile " +
			"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
			"and vou_no=? and serialno=? and vter_cd=? and mnth_Code=? and ifnull(del_tag,'')<>'D'";
			

			String led="update ledfile set del_tag=?,modified_by=?,modified_date=?  " +
			"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
			"and vou_no=? and serialno=? and vter_cd=? and mnth_Code=? and ifnull(del_tag,'')<>'D'";

			
			totps=con.prepareStatement(totquery);
			for(int j=0;j<cList.size();j++)
			{
				rcp = (RcpDto) cList.get(j);				
				totps.setInt(1, rcp.getFin_year());
				totps.setInt(2, rcp.getDiv_code());
				totps.setInt(3, rcp.getVdepo_code());
				totps.setInt(4, rcp.getVbook_cd1());
				totps.setInt(5, rcp.getVou_no());
				totps.setInt(6, rcp.getSerialno());
				totps.setInt(7, rcp.getVter_cd());
				totps.setInt(8, rcp.getMnth_code());
				
				totrs=totps.executeQuery();
				
				if(totrs.next())
				{
					tot+=totrs.getDouble(1);
					
				}
			
				totrs.close();
				
			} //end of for 
				totps.close();
			
			ledps=con.prepareStatement(led);
			
			for(int j=0;j<cList.size();j++)
			{
				rcp = (RcpDto) cList.get(j);

				System.out.println(rcp.getFin_year());
				System.out.println(rcp.getDiv_code());
				System.out.println(rcp.getVdepo_code());
				System.out.println(rcp.getVbook_cd1());
				System.out.println(rcp.getVou_no());
				System.out.println(rcp.getSerialno());
				System.out.println(rcp.getVter_cd());
				System.out.println(rcp.getMnth_code());
				
				ledps.setString(1, "D");
				ledps.setInt(2, rcp.getModified_by());
				ledps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));

				// where clause
				ledps.setInt(4, rcp.getFin_year());
				ledps.setInt(5, rcp.getDiv_code());
				ledps.setInt(6, rcp.getVdepo_code());
				ledps.setInt(7, rcp.getVbook_cd1());
				ledps.setInt(8, rcp.getVou_no());
				ledps.setInt(9, rcp.getSerialno());
				ledps.setInt(10, rcp.getVter_cd());
				ledps.setInt(11, rcp.getMnth_code());
				
				
				i=ledps.executeUpdate();
			}
      
			   
			if (rcp!=null)
			{
				updateBalance(rcp.getVdepo_code(),rcp.getDiv_code(),rcp.getFin_year(),(tot*-1));
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
			System.out.println("-------------Exception in SQLRcpDAO.deleteLedHO " + ex);
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

	public List getMultipleVouDetailHO(int startVoucher,int endVoucher,int depo_code,int div,int fyear,int doctp,String vdbcr,int type,int mnthcode)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		List voucherList=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.tds_amt,f.bill_no,f.bill_date from ledfile as f "+
			"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and  ifnull(s.del_tag,'')<>'D' "+ 
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=? and f.vou_no between ? and ? and f.vdbcr=? and f.vter_cd=? and f.mnth_code=? and  ifnull(f.del_tag,'')<>'D' " +
			"and p.div_code=? and p.depo_code=? order by f.vou_no,f.serialno";  


			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,fyear);
			ps2.setInt(4,div);
			ps2.setInt(5, depo_code);
			ps2.setInt(6, doctp);
			ps2.setInt(7,startVoucher); 
			ps2.setInt(8,endVoucher);
			ps2.setString(9, vdbcr);
			ps2.setInt(10,type);
			ps2.setInt(11,mnthcode);
			ps2.setInt(12,div);
			ps2.setInt(13, depo_code);
			
			rs= ps2.executeQuery();
			
			voucherList = new ArrayList(); 
			int wno = 0;
			boolean first=true;
			double rgross = 0.00;
			
			while (rs.next())
			{
				 
				if(first)
				{
					wno=rs.getInt(3);
					first=false;
				}
				
				if(wno!=rs.getInt(3))
				{
					rcp= new RcpDto();
					rcp.setDash(1);
					rcp.setVamount(rgross);
					voucherList.add(rcp);
		            rgross=0.00;
		            wno=rs.getInt(3);
				}
				
				rcp= new RcpDto();
				rcp.setVou_no(rs.getInt(3));
				rcp.setVou_date(rs.getDate(4));
				rcp.setParty_name(rs.getString(2));
//				rcp.setVou_lo("PC");
				rcp.setVou_lo(rs.getString(1));
				rcp.setExp_code(rs.getString(5));
				rcp.setExp_name(rs.getString(6));
				rcp.setVnart1(rs.getString(9));
				rcp.setVnart2(rs.getString(10));
				rcp.setVamount(rs.getDouble(11));
				rcp.setTds_amt(rs.getDouble(15));
				rcp.setBill_no(rs.getString(16));
				rcp.setBill_date(rs.getDate(17));
				rcp.setDash(0);
				rgross+=rs.getDouble(11);
				
				voucherList.add(rcp);
			}

			
			rcp= new RcpDto();
			rcp.setDash(2);
			rcp.setVamount(rgross);
			voucherList.add(rcp);
            
            
            
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
			System.out.println("-------------Exception in CashVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return voucherList;
	}		
	
	
	  //// method to add record in led file (cash voucher)	
		public int addLedGST(ArrayList cList)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ledps=null;
			RcpDto rcp=null;
			try {

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);
				

				String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
				"vou_date,vgrp_code,vac_code,exp_code,sub_code,vnart_1,vnart_2,name,vamount,vdbcr, " +
				"Created_by,Created_date,serialno,fin_year,mnth_code,bill_amt,tds_amt,bill_no,bill_date,cr_amt,sertax_amt,sertax_billper,sertax_per," +
				" taxable_amt,ctax_per, stax_per, itax_per, "+
				" cgst_amt, sgst_amt, igst_amt,net_amt,addl_taxper,addl_taxamt,vreg_cd,varea_cd,vmsr_cd,gst_no,hsn_code "+						

				") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			    // 040+depo+01
				
				ledps=con.prepareStatement(led);
				int size = cList.size();
				for(int j=0;j<size;j++)
				{
					System.out.println("size of list "+cList.size());
					rcp = (RcpDto) cList.get(j);
					ledps.setInt(1, rcp.getDiv_code());
					ledps.setInt(2, rcp.getVdepo_code());
					ledps.setInt(3, rcp.getVbook_cd());
					ledps.setInt(4, rcp.getVbook_cd1());
					ledps.setString(5, rcp.getVou_lo());
					ledps.setInt(6, rcp.getVou_no());
					ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					ledps.setInt(8,rcp.getVgrp_code()); // vgrp_Code
					ledps.setString(9, rcp.getVac_code());
					ledps.setString(10, rcp.getExp_code());
					ledps.setString(11, rcp.getSub_code());
					ledps.setString(12, rcp.getVnart1());
					ledps.setString(13, rcp.getVnart2());
					ledps.setString(14, rcp.getParty_name());
					ledps.setDouble(15, rcp.getVamount());
					ledps.setString(16, "CR");
					ledps.setInt(17, rcp.getCreated_by());
					ledps.setDate(18, new java.sql.Date(rcp.getCreated_date().getTime()));
					ledps.setInt(19, rcp.getSerialno());
					ledps.setInt(20, rcp.getFin_year());
					ledps.setInt(21, rcp.getMnth_code());
					ledps.setDouble(22, rcp.getBill_amt());
					ledps.setDouble(23, rcp.getTds_amt());
					ledps.setString(24, rcp.getBill_no());
					if (rcp.getBill_date()!=null)
						ledps.setDate(25, new java.sql.Date(rcp.getBill_date().getTime()));
					else
						ledps.setDate(25, null);
					ledps.setDouble(26, 0.00);  // taxable service tax
					ledps.setDouble(27, rcp.getSertax_amt());
					ledps.setDouble(28, rcp.getSertax_billper());
					ledps.setDouble(29, rcp.getSertax_per());
					ledps.setDouble(30, rcp.getCr_amt());  // taxable   
/*					ledps.setDouble(31, rcp.getCtaxper());   
					ledps.setDouble(32, rcp.getStaxper());   
					ledps.setDouble(33, rcp.getItaxper());   
					ledps.setDouble(34, rcp.getCgstamt());   
					ledps.setDouble(35, rcp.getSgstamt());   
					ledps.setDouble(36, rcp.getIgstamt());   
					ledps.setDouble(37, rcp.getNetamt());   
					ledps.setDouble(38, rcp.getCess_per());  // cess per   
					ledps.setDouble(39, rcp.getCess_amt());  // cess amt   
*/					ledps.setInt(40, rcp.getVreg_cd()); // rcm y/n y=1   
					ledps.setInt(41, rcp.getVarea_cd()); // itc y/n/ y=1   
					ledps.setInt(42, rcp.getVmsr_cd()); // state code   
/*					ledps.setString(43, rcp.getGst_no());
					ledps.setLong(44, rcp.getHsn_code());
*/
					i=ledps.executeUpdate();
				}
				i=rcp.getVou_no();
				con.commit();
				con.setAutoCommit(true);
				ledps.close();
				
			} catch (SQLException ex) {
				ex.printStackTrace();
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
		
	
		  //// method to update record in led file (cash voucher)	
				public int updateLedGST(ArrayList cList)
				{
					Connection con=null;
					int i=0;
					PreparedStatement ledps=null;
					RcpDto rcp=null;
					try {

						con=ConnectionFactory.getConnection();
						con.setAutoCommit(false);
						

						String led="update  ledfile set vbook_cd=?,vou_date=?,vgrp_code=?,exp_code=?," +
						"sub_code=?,vnart_1=?,vnart_2=?,name=?,vamount=?,modified_by=?," +
						"modified_date=?,bill_amt=?,tds_amt=?,bill_no=?,bill_date=?,cr_amt=?," +
						"sertax_amt=?,sertax_billper=?,sertax_per=?,  " +
						" taxable_amt=?,ctax_per=?, stax_per=?, itax_per=?, "+
						" cgst_amt=?, sgst_amt=?, igst_amt=?,net_amt=?,addl_taxper=?,addl_taxamt=?,vreg_cd=?," +
						" varea_cd=?,gst_no=?,hsn_code=? "+						
						" where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
						" and vou_no=? and serialno=?";

						
						ledps=con.prepareStatement(led);
						
						for(int j=0;j<cList.size();j++)
						{
							rcp = (RcpDto) cList.get(j);
							ledps.setInt(1, rcp.getVbook_cd());
							ledps.setDate(2, new java.sql.Date(rcp.getVou_date().getTime()));
							ledps.setInt(3,rcp.getVgrp_code()); // vgrp_Code
							ledps.setString(4, rcp.getExp_code());
							ledps.setString(5, rcp.getSub_code());
							ledps.setString(6, rcp.getVnart1());
							ledps.setString(7, rcp.getVnart2());
							ledps.setString(8, rcp.getParty_name());
							ledps.setDouble(9, rcp.getVamount());
							ledps.setInt(10, rcp.getModified_by());
							ledps.setDate(11, new java.sql.Date(rcp.getModified_date().getTime()));
							ledps.setDouble(12, rcp.getBill_amt());
							ledps.setDouble(13, rcp.getTds_amt());
							ledps.setString(14, rcp.getBill_no());
							if (rcp.getBill_date()!=null)
								ledps.setDate(15, new java.sql.Date(rcp.getBill_date().getTime()));
							else
								ledps.setDate(15, null);
							ledps.setDouble(16, rcp.getCr_amt());  // taxable service tax
							ledps.setDouble(17, rcp.getSertax_amt());
							ledps.setDouble(18, rcp.getSertax_billper());
							ledps.setDouble(19, rcp.getSertax_per());
							ledps.setDouble(20, rcp.getCr_amt());  // taxable   
/*							ledps.setDouble(21, rcp.getCtaxper());   
							ledps.setDouble(22, rcp.getStaxper());   
							ledps.setDouble(23, rcp.getItaxper());   
							ledps.setDouble(24, rcp.getCgstamt());   
							ledps.setDouble(25, rcp.getSgstamt());   
							ledps.setDouble(26, rcp.getIgstamt());   
							ledps.setDouble(27, rcp.getNetamt());   
							ledps.setDouble(28, rcp.getCess_per());  // cess per   
							ledps.setDouble(29, rcp.getCess_amt());  // cess amt   
*/							ledps.setInt(30, rcp.getVreg_cd()); // rcm y/n y=1   
							ledps.setInt(31, rcp.getVarea_cd()); // itc y/n/ y=1   
/*							ledps.setString(32, rcp.getGst_no());
							ledps.setLong(33, rcp.getHsn_code());
*/								
							// where clause
							ledps.setInt(34, rcp.getFin_year());
							ledps.setInt(35, rcp.getDiv_code());
							ledps.setInt(36, rcp.getVdepo_code());
							ledps.setInt(37, rcp.getVbook_cd1());
							ledps.setInt(38, rcp.getVou_no());
							ledps.setInt(39, rcp.getSerialno());
							
							
							i=ledps.executeUpdate();
						}
			       
						con.commit();
						con.setAutoCommit(true);
						ledps.close();
						
					} catch (SQLException ex) {
						ex.printStackTrace();
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
							if(con != null){con.close();}
						} catch (SQLException e) {
							System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
						}
					}

					return (i);
				}		

				public RcpDto getVouDetailGST(int startVoucher,int depo_code,int div,int fyear)
				{
					PreparedStatement ps2 = null;
					ResultSet rs=null;
					Connection con=null;
					RcpDto rcp=null;
					double total=0.00;
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					NumberFormat nf=null;
					try {

						con=ConnectionFactory.getConnection();
						con.setAutoCommit(false);
						nf= new DecimalFormat("0.00");

						String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
						"s.mac_name,f.vnart_1,f.vnart_2,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_amt,f.tds_amt,f.bill_no,f.bill_date," +
						"ifnull(f.cr_amt,0)," +
						"ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0)," +
						" f.taxable_amt,(f.ctax_per+f.stax_per+f.itax_per), "+
						" f.cgst_amt, f.sgst_amt, f.igst_amt,f.net_amt,f.addl_taxper,f.addl_taxamt,f.vreg_cd,f.varea_cd,ifnull(f.gst_no,''),ifnull(f.hsn_code,0) "+						
						" from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<> 'D' "+ 
						",partyfst as p "+
						"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=10 and f.vou_no=? and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' order by f.vou_no ";  

								
								

						ps2 = con.prepareStatement(query2);
						
						ps2.setInt(1,div);
						ps2.setInt(2, depo_code);
						ps2.setInt(3,fyear);
						ps2.setInt(4,div);
						ps2.setInt(5, depo_code);
						ps2.setInt(6,startVoucher); 
						ps2.setInt(7,div);
						ps2.setInt(8, depo_code);
						rs= ps2.executeQuery();
						
						
					 
						Vector row = new Vector();   // Table row
						Vector colum = null; // Table Column
						String inv=null;
						boolean first=true;
						 
						while (rs.next())
						{
							 
							if (first)
							{
								first=false;
								rcp= new RcpDto();
								rcp.setVou_no(rs.getInt(3));
								rcp.setVou_date(rs.getDate(4));
								rcp.setParty_name(rs.getString(2));
//								rcp.setGst_no(rs.getString(33));
							}
							
							 
							
							
							 
							
							/// Table detail set here
							colum = new Vector();
							colum.addElement(false);   // del tag   0
							colum.addElement(rs.getString(5));   // exp code 1
							colum.addElement(rs.getString(6));  //exp  name 2
							colum.addElement(rs.getString(8));  //sub_head 3
							colum.addElement(rs.getString(9));  // supp name 4
							colum.addElement(rs.getString(17)); // bill no 5
							try
							{
								colum.addElement(sdf.format(rs.getDate(18))); // bill date 6
								
							}
							catch (Exception e)
							{
								colum.addElement("  /  /    "); // bill date 6
								
							}
							colum.addElement(rs.getString(10)); // narration 7
							colum.addElement(rs.getDouble(15));  //bill_amt 8

							colum.addElement(rs.getDouble(23));  //taxable      9

							colum.addElement(rs.getInt(31)==0?"N":"Y");  // vreg_cd      10 rcm
							colum.addElement(rs.getInt(32)==0?"N":"Y");  // varea_cd     11 itc
							colum.addElement(nf.format(rs.getDouble(24)));  // gst %  12
							colum.addElement(nf.format(rs.getDouble(25)));  // cgst %  13
							colum.addElement(nf.format(rs.getDouble(26)));  // sgst %  14
							colum.addElement(nf.format(rs.getDouble(27)));  // igst %  15
							colum.addElement(nf.format(rs.getDouble(29)));  // cess %  16
							colum.addElement(nf.format(rs.getDouble(30)));  // cess amount  17

							colum.addElement(nf.format(rs.getDouble(28)));  // total  18

							colum.addElement(rs.getDouble(16));  //tds 19
							colum.addElement(rs.getDouble(11));  //vamount 20
							colum.addElement(rs.getInt(12));  //vbook_cd 21
							colum.addElement(rs.getInt(13));  // vgrpcode 22
							colum.addElement(rs.getString(7));  // subcode 23
							colum.addElement(rs.getInt(14));  // serialno 24
							colum.addElement(rs.getDouble(21));  //bill_per      25
							colum.addElement(rs.getDouble(22));  //service tax per 26
							colum.addElement(rs.getInt(34));  // hsn code 27
							row.addElement(colum);
							total+=rs.getDouble(11);
							

						}

						if (rcp!=null)
						{
							rcp.setVamount(total);
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
						System.out.println("-------------Exception in CashVouDAO.getVouDetail " + ex);

					}
					finally {
						try {
							//				System.out.println("No. of Records Update/Insert : "+i);

							if(rs != null){rs.close();}
							if(ps2 != null){ps2.close();}
							if(con != null){con.close();}
						} catch (SQLException e) {
							System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
						}
					}

					return rcp;
				}		
				public List getVouListGST(Date invDt,int depo_code,int div)
				{
					PreparedStatement ps2 = null;
					ResultSet rs=null;
					Connection con=null;
					Vector v=null;
					Vector col=null;
					SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
					NumberFormat nf=null;
					try {

						con=ConnectionFactory.getConnection();
						con.setAutoCommit(false);
						nf= new DecimalFormat("0.00");

						
						String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
						"s.mac_name,f.vnart_1,f.vnart_2,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.bill_amt,f.tds_amt,f.bill_no,f.bill_date,ifnull(f.cr_amt,0)," +
						"ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0)," +
						" f.taxable_amt,(f.ctax_per+f.stax_per+f.itax_per), "+
						" f.cgst_amt, f.sgst_amt, f.igst_amt,f.net_amt,f.addl_taxper,f.addl_taxamt,f.vreg_cd,f.varea_cd,ifnull(f.gst_no,''),ifnull(f.hsn_code,0)  "+						
						" from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<> 'D' "+ 
						",partyfst as p "+
						"where f.exp_code=p.mac_code and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=10 and f.vou_date=? and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' order by f.vou_no ";  
								
								

						ps2 = con.prepareStatement(query2);
						ps2.setInt(1,div);
						ps2.setInt(2, depo_code);
						ps2.setInt(3,div);
						ps2.setInt(4, depo_code);
						ps2.setDate(5, new java.sql.Date(invDt.getTime()));
						ps2.setInt(6,div);
						ps2.setInt(7, depo_code);
						rs= ps2.executeQuery();
						
						RcpDto rcp=null;
						v = new Vector();
						Vector row = new Vector();   // Table row
						Vector colum = null; // Table Column
						String inv=null;
						boolean first=true;
						int vou=0;
						double total=0.00;
						while (rs.next())
						{
							 
							if (first)
							{
								vou=rs.getInt(3);
								inv = rs.getString(1)+"V"+rs.getString(3).substring(1);
								first=false;
								rcp= new RcpDto();
							}
							
							if (vou!=rs.getInt(3))
							{
								col= new Vector();
								col.addElement(inv);//concat inv_no
								col.addElement(rcp.getParty_name());//party name
				                col.addElement(rcp);
				                col.addElement(row);
								v.addElement(col);

								vou=rs.getInt(3);
								inv = rs.getString(1)+"V"+rs.getString(3).substring(1);
								total=0.00;
								rcp= new RcpDto();
								row = new Vector();   // Create new Table row
								
							}
							
							
							
							rcp.setVou_no(rs.getInt(3));
							rcp.setVou_date(rs.getDate(4));
							rcp.setParty_name(rs.getString(2));
//							rcp.setGst_no(rs.getString(33));
							
							
							/////////
							colum = new Vector();
							colum.addElement(false);   // del tag   0
							colum.addElement(rs.getString(5));   // exp code 1
							colum.addElement(rs.getString(6));  //exp  name 2
							colum.addElement(rs.getString(8));  //sub_head 3
							colum.addElement(rs.getString(9));  // supp name 4
							colum.addElement(rs.getString(17)); // bill no 5
							try
							{
								colum.addElement(sdf.format(rs.getDate(18))); // bill date 6
								
							}
							catch (Exception e)
							{
								colum.addElement("  /  /    "); // bill date 6
								
							}
							colum.addElement(rs.getString(10)); // narration 7
							colum.addElement(rs.getDouble(15));  //bill_amt 8

							colum.addElement(rs.getDouble(23));  //taxable 9

							colum.addElement(rs.getInt(31)==0?"N":"Y");  // vreg_cd      10 rcm
							colum.addElement(rs.getInt(32)==0?"N":"Y");  // varea_cd     11 itc
							colum.addElement(nf.format(rs.getDouble(24)));  // gst %  12
							colum.addElement(nf.format(rs.getDouble(25)));  // cgst %  13
							colum.addElement(nf.format(rs.getDouble(26)));  // sgst %  14
							colum.addElement(nf.format(rs.getDouble(27)));  // igst %  15
							colum.addElement(nf.format(rs.getDouble(29)));  // cess %  16
							colum.addElement(nf.format(rs.getDouble(30)));  // cess amount  17

							colum.addElement(nf.format(rs.getDouble(28)));  // total  18

							colum.addElement(rs.getDouble(16));  //tds 19
							colum.addElement(rs.getDouble(11));  //vamount 20
							colum.addElement(rs.getInt(12));  //vbook_cd 21
							colum.addElement(rs.getInt(13));  // vgrpcode 22
							colum.addElement(rs.getString(7));  // subcode 23
							colum.addElement(rs.getInt(14));  // serialno 24
							colum.addElement(rs.getDouble(21));  //bill_per      25
							colum.addElement(rs.getDouble(22));  //service tax per 26
							colum.addElement(rs.getInt(34));  // hsn code 27
							row.addElement(colum);

							////////////
							
							
							total+=rs.getDouble(11);
							rcp.setVamount(total);
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

					} catch (SQLException ex) { ex.printStackTrace();
						try {
							con.rollback();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						System.out.println("-------------Exception in CashVouDAO.getVouList " + ex);

					}
					finally {
						try {
							//				System.out.println("No. of Records Update/Insert : "+i);

							if(rs != null){rs.close();}
							if(ps2 != null){ps2.close();}
							if(con != null){con.close();}
						} catch (SQLException e) {
							System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
						}
					}

					return v;
				}	
				
				
	
	}
