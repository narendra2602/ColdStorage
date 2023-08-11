package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.coldstorage.dto.BookDto;
import com.coldstorage.dto.ChequeDto;
import com.coldstorage.dto.RcpDto;
import com.coldstorage.view.BaseClass;

public class BankVouDAO 
{

	public List getVouList(Date invDt,Date endDate,int depo_code,int div,int bkcd,String tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
					"s.mac_name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno," +
					"f.chq_no,f.chq_date,chq_amt,f.bill_no,f.bill_date,ifnull(f.tds_amt,0),ifnull(f.bill_amt,f.vamount)," +
					"ifnull(f.cr_amt,0),ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0),ifnull(f.tax_type,'H')," +
					" f.taxable_amt,(f.ctax_per+f.stax_per+f.itax_per), "+
					" f.cgst_amt, f.sgst_amt, f.igst_amt,f.net_amt,f.addl_taxper,f.addl_taxamt,f.vreg_cd," +
					" f.varea_cd,ifnull(f.gst_no,''),ifnull(f.hsn_code,0) "+						

					" from ledfile as f "+
					"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and  ifnull(s.del_tag,'')<>'D' "+ 
					",partyfst as p "+
					"where f.exp_code=p.mac_code  and f.div_code=? and f.vdepo_code=? and "+ 
					"f.vbook_cd1=? and f.vou_date between ? and ? and vdbcr='DR' and f.tp=? and f.sub_div=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' order by f.vou_no ";  

			 if (depo_code==8)  // for ho only
			 {
				 query2 ="select concat('VP',indicator,right(vou_no,5)) vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
						 "s.mac_name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno," +
						 "f.chq_no,f.chq_date,chq_amt,f.bill_no,f.bill_date,ifnull(f.tds_amt,0),ifnull(f.bill_amt,f.vamount)," +
						 "ifnull(f.cr_amt,0),ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0),ifnull(f.tax_type,'H')," +
						 " f.taxable_amt,(f.ctax_per+f.stax_per+f.itax_per), "+
						 " f.cgst_amt, f.sgst_amt, f.igst_amt,f.net_amt,f.addl_taxper,f.addl_taxamt,f.vreg_cd," +
						 " f.varea_cd,ifnull(f.gst_no,''),ifnull(f.hsn_code,0) "+						
						 " from ledfile as f "+
						 "left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and  ifnull(s.del_tag,'')<>'D' "+ 
						 ",partyfst as p "+
						 "where f.exp_code=p.mac_code  and f.div_code=? and f.vdepo_code=? and "+ 
						 "f.vbook_cd1=? and f.vou_date between ? and ? and vdbcr='DR' and f.tp='D' and f.indicator=? and f.sub_div=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' order by f.vou_no ";  
			 }
//TODO


			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,div);
			ps2.setInt(4, depo_code);
			ps2.setInt(5,bkcd); 
			ps2.setDate(6, new java.sql.Date(invDt.getTime()));
			ps2.setDate(7, new java.sql.Date(endDate.getTime()));
			ps2.setString(8,tp); 
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
					if(depo_code==8)
						inv = rs.getString(1);
					else
						inv = rs.getString(1)+"A"+rs.getString(3).substring(1);

					System.out.println(inv);
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
					if(depo_code==8)
						inv = rs.getString(1);
					else
						inv = rs.getString(1)+"A"+rs.getString(3).substring(1);

					total=0.00;
					rcp= new RcpDto();
					row = new Vector();   // Create new Table row



				}



				rcp.setVou_no(rs.getInt(3));
				rcp.setVou_date(rs.getDate(4));
				rcp.setParty_name(rs.getString(2));
				rcp.setChq_no(rs.getString(15));
				rcp.setChq_date(rs.getDate(16));
				rcp.setChq_amt(rs.getDouble(17));
				rcp.setVnart2(rs.getString(9));
				if(rs.getString(26).equalsIgnoreCase("R"))
					rcp.setPay_mode(1);  // rtgs
				else
					rcp.setPay_mode(0); // ho chq book

				/// Table detail set here
				colum = new Vector();
				colum.addElement(false);   // del tag
				colum.addElement(rs.getString(5));   // exp code
				colum.addElement(rs.getString(6));  //exp  name
				colum.addElement(rs.getString(8));  //sub_head
				colum.addElement(rs.getString(18));  //bill_no
				if (rs.getDate(19)!=null)
					colum.addElement(sdf.format(rs.getDate(19)));  //bill_date
				else
					colum.addElement("");  //bill_date

				
				colum.addElement(rs.getString(10)); // narration 6
				colum.addElement(rs.getDouble(21));  //vamount 7 
				colum.addElement(rs.getInt(12));  //vbook_cd 8
				colum.addElement(rs.getInt(13));  // vgrpcode 9
				colum.addElement(rs.getString(7));  // subcode 10
				colum.addElement(rs.getInt(14));  // serialno 11
				colum.addElement(nf.format(rs.getDouble(27)));  //taxable 12
				

				colum.addElement(rs.getInt(35)==0?"N":"Y");  // vreg_cd      13 rcm
				colum.addElement(rs.getInt(36)==0?"N":"Y");  // varea_cd     14 itc
				colum.addElement(nf.format(rs.getDouble(28)));  // gst %  15
				colum.addElement(nf.format(rs.getDouble(29)));  // cgst %  16
				colum.addElement(nf.format(rs.getDouble(30)));  // sgst %  17
				colum.addElement(nf.format(rs.getDouble(31)));  // igst %  18
				colum.addElement(nf.format(rs.getDouble(33)));  // cess %  19
				colum.addElement(nf.format(rs.getDouble(34)));  // cess amount  20
				colum.addElement(nf.format(rs.getDouble(32)));  // next amount  21
				colum.addElement(nf.format(rs.getDouble(20)));  //tds 22
				colum.addElement(nf.format(rs.getDouble(11)));  // balance amountvamount 23
				colum.addElement(nf.format(rs.getDouble(24)));  //billper 24
				colum.addElement(nf.format(rs.getDouble(25)));  //servicetax per 25
				row.addElement(colum);

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

		} catch (SQLException ex) {
			try {
				System.out.println("yaha kuch errr hai "+ex);
				ex.printStackTrace();
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getVouList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return v;
	}	

	////////////////bank paymen direct method

	public RcpDto getVouDetail(int vou,int depo_code,int div,int fyear,int bkcd,String tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int maxSerialNo=0;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"s.mac_name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno," +
			"f.chq_no,if(f.chq_date='0000-00-00',null,f.chq_date),f.chq_amt,f.bill_no,f.bill_date,ifnull(f.tds_amt,0),ifnull(f.bill_amt,f.vamount)," +
			"ifnull(f.cr_amt,0),ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0)," +
			" f.taxable_amt,(f.ctax_per+f.stax_per+f.itax_per), "+
			" f.cgst_amt, f.sgst_amt, f.igst_amt,f.net_amt,f.addl_taxper,f.addl_taxamt,f.vreg_cd," +
			" f.varea_cd,ifnull(f.gst_no,''),ifnull(f.hsn_code,0) "+						

			" from ledfile as f "+
			"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D'"+ 
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=? and f.vou_no=? and f.vdbcr='DR' and f.tp=? and f.sub_div=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' order by f.vou_no,serialno ";  


			String maxSrial = "select ifnull(max(serialno),0) from ledfile where fin_year=? and div_code=? and vdepo_code=? and "+ 
			" vbook_cd1=? and vou_no=? and vdbcr='DR' and tp=?  ";

			ps2 = con.prepareStatement(maxSrial);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,vou); 
			ps2.setString(6,tp); 
			
			rs= ps2.executeQuery();
			if(rs.next())
			{
				maxSerialNo = rs.getInt(1);
			}
			rs.close();
			ps2.close();
			rs=null;
			ps2=null;
			

			/////////////////////////////////
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,fyear);
			ps2.setInt(4,div);
			ps2.setInt(5, depo_code);
			ps2.setInt(6,bkcd); 
			ps2.setInt(7,vou); 
			ps2.setString(8,tp); 
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
					rcp.setChq_no(rs.getString(15));
					rcp.setChq_date(rs.getDate(16));
					rcp.setChq_amt(rs.getDouble(17));
					rcp.setVnart2(rs.getString(9));
					rcp.setSerialno(maxSerialNo);

				}






				/// Table detail set here
				colum = new Vector();
				colum.addElement(false);   // del tag
				colum.addElement(rs.getString(5));   // exp code
				colum.addElement(rs.getString(6));  //exp  name
				colum.addElement(rs.getString(8));  //sub_head
				colum.addElement(rs.getString(18));  //bill_no
				if (rs.getDate(19)!=null)
				{
				   try
					{
					  colum.addElement(sdf.format(rs.getDate(19)));  //bill_date
					}catch(Exception e)
					{
						colum.addElement("");  //bill_date
					}
				}
				else
				{
					colum.addElement("");  //bill_date
				}

//				colum.addElement(nf.format(rs.getDouble(22)));  //taxable
//				colum.addElement(nf.format(rs.getDouble(23)));  //servicetax

				
				
				colum.addElement(rs.getString(10)); // narration
				colum.addElement(nf.format(rs.getDouble(21)));  //billamt
				colum.addElement(rs.getInt(12));  //vbook_cd
				colum.addElement(rs.getInt(13));  // vgrpcode
				colum.addElement(rs.getString(7));  // subcode 
				colum.addElement(rs.getInt(14));  // serialno
				colum.addElement(nf.format(rs.getDouble(26)));  //taxable
				
				colum.addElement(rs.getInt(34)==0?"N":"Y");  // vreg_cd      10 rcm
				colum.addElement(rs.getInt(35)==0?"N":"Y");  // varea_cd     11 itc
				colum.addElement(nf.format(rs.getDouble(27)));  // gst %  12
				colum.addElement(nf.format(rs.getDouble(28)));  // cgst %  13
				colum.addElement(nf.format(rs.getDouble(29)));  // sgst %  14
				colum.addElement(nf.format(rs.getDouble(30)));  // igst %  15
				colum.addElement(nf.format(rs.getDouble(32)));  // cess %  16
				colum.addElement(nf.format(rs.getDouble(33)));  // cess amount  17
				colum.addElement(nf.format(rs.getDouble(31)));  // next amount  17
				colum.addElement(nf.format(rs.getDouble(20)));  //tds
				colum.addElement(nf.format(rs.getDouble(11)));  // balance amountvamount
				colum.addElement(nf.format(rs.getDouble(24)));  //bill_per
				colum.addElement(nf.format(rs.getDouble(25)));  //servicetax_per

				
				
				row.addElement(colum);
				total+=rs.getDouble(11);


			}

			if (rcp!=null)
			{
				rcp.setVamount(total);
				rcp.setVdetail(row);
				System.out.println("aftger hsis fill ref data is callledsssss SIZE HAI DAO MEIN "+row.size());
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		
	//////////////// bank payment creditors method
	public RcpDto getVouDetail1(int vou,int depo_code,int wdepo,int fyear,int bkcd,String tp,Date voudt,int mnthcode)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=null;
		String tp1=tp;
		int xdepo=depo_code;
		boolean advance=false;
		if(depo_code>200)
		{
			depo_code-=199;
			advance=true;
			
		}
		else
			depo_code-=99;
		
		int tran=99;
		int div=4;
		if (depo_code < 0)
		{
			depo_code=xdepo;
			tran=0;
		}
		try {



			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");

			System.out.println("DEPO CODE "+depo_code+" tran "+tran+" tp "+tp+" mnthcode "+mnthcode);
			String query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,l.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
					" r.vouno,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,if(r.vamount=0,r.chq_amt,r.vamount),r.voudt,r.inv_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),l.vou_lo,ifnull(l.tax_type,'H') "+
					" from ledfile as l,rcpfile as r "+
					" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd1=? and l.tp=?  and l.vou_no=? "+
					" and  l.vdbcr='DR' and ifnull(l.tran_type,0)=? and ifnull(l.del_tag,'')<>'D' and r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd=? and r.vou_no=? " +
					" and ifnull(r.tran_type,0)=? and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date" ;

			if(tran==99 && wdepo==8)   // for ho credit entry monthwise 
			{
				if(advance)
				{
					query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,l.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
							" '' inv_lo,'' bill_no,null bill_date,0 bill_amt,0 tds_amt,0 vamount,null voudt,'' inv_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),concat('VP',indicator,right(l.vou_no,5)) vou_lo,ifnull(l.tax_type,'H') "+
							" from ledfile as l "+
							" where l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd1=? and l.tp='C' and l.indicator=?  and l.vou_no=? "+
							" and  l.vdbcr='DR' and ifnull(l.tran_type,0)=? and ifnull(l.del_tag,'')<>'D'  " +
							" and l.mnth_code="+mnthcode+" " ;
					
				}
				else
				{
				query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,l.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
						" r.inv_lo,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vamount,r.voudt,r.inv_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),concat('VP',indicator,right(l.vou_no,5)) vou_lo,ifnull(l.tax_type,'H') "+
						" from ledfile as l,rcpfile as r "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd1=? and l.tp='C' and l.indicator=?  and l.vou_no=? "+
						" and  l.vdbcr='DR' and ifnull(l.tran_type,0)=? and ifnull(l.del_tag,'')<>'D' and r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd=? and r.vou_no=? " +
						" and ifnull(r.tran_type,0)=? and r.mnth_code="+mnthcode+" and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date" ;
				}
				
			}
			if(tran==99 && wdepo!=8)   // for ho credit entry monthwise 
			{
				tran=0;
				query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,l.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
						" r.vouno,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vamount,r.voudt,r.inv_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),l.vou_lo,ifnull(l.tax_type,'H') "+
						" from ledfile as l,rcpfile as r "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd1=? and l.tp='C' and l.vter_cd=?  and l.vou_no=? "+
						" and  l.vdbcr='DR' and ifnull(l.tran_type,0)=? and ifnull(l.del_tag,'')<>'D' and r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd=? and r.vou_no=? " +
						" and ifnull(r.tran_type,0)=? and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date" ;
				
				
			}

			if (tp.equalsIgnoreCase("X"))
			{
				tp="C";
				query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,l.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
						" r.vouno,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vamount,r.voudt,r.inv_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),l.vou_lo,ifnull(l.tax_type,'H') "+
						" from ledfile as l,rcpfile as r "+
						" where l.vac_code=r.vac_code  and l.div_code=? and l.vdepo_code=? and l.vbook_cd1=? and l.tp=?  and l.vou_no=? and l.vou_date=?"+
						" and  l.vdbcr='DR' and ifnull(l.tran_type,0)=? and ifnull(l.del_tag,'')<>'D'  and r.div_code=? and r.vdepo_code=? " +
						" and r.vbook_cd=? and r.vou_no=?  and ifnull(r.cr_no,'')<>'C' " +
						" and ifnull(r.tran_type,0)=? and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date" ;
				
			}
			
			ps2 = con.prepareStatement(query2);

			if (tp1.equalsIgnoreCase("X"))
			{
				ps2.setInt(1,div);
				ps2.setInt(2, depo_code);
				ps2.setInt(3,bkcd);
				ps2.setString(4,tp);
				ps2.setInt(5,vou); 
				ps2.setDate(6,new java.sql.Date(voudt.getTime())); 
				ps2.setInt(7,tran);

				ps2.setInt(8,div);
				ps2.setInt(9, depo_code);
				ps2.setInt(10,bkcd); 
				ps2.setInt(11,vou); 
				ps2.setInt(12,tran);

			}
			else
			{
				ps2.setInt(1,fyear);
				ps2.setInt(2,div);
				ps2.setInt(3, depo_code);
				ps2.setInt(4,bkcd);

				if(tran==99 && wdepo!=8)
					ps2.setInt(5,wdepo);
				else
					ps2.setString(5,tp);

				ps2.setInt(6,vou);
				ps2.setInt(7,tran);
				if(!advance)
				{
					ps2.setInt(8,fyear);
					ps2.setInt(9,div);
					ps2.setInt(10, depo_code);
					ps2.setInt(11,bkcd); 
					ps2.setInt(12,vou); 
					ps2.setInt(13,tran);
				}
			}	
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
					rcp.setVou_no(rs.getInt(1));
					rcp.setVou_date(rs.getDate(2));
					rcp.setVac_code(rs.getString(3));
					rcp.setParty_name(rs.getString(4));
					rcp.setVnart1(rs.getString(5));
					rcp.setChq_no(rs.getString(6));
					rcp.setChq_date(rs.getDate(7));
					rcp.setChq_amt(rs.getDouble(8));
					rcp.setBank_chg(rs.getDouble(9));
					rcp.setRcp_no(rs.getString(17));
					rcp.setVnart2(rs.getString(18));
					rcp.setTds_amt(rs.getDouble(19));
					
					if (tran==99)
						inv = rs.getString(20);
					else
						inv = rs.getString(20)+"A"+rs.getString(1).substring(1);

					rcp.setVou_lo(inv);
					if(rs.getString(21).equalsIgnoreCase("R"))
						rcp.setPay_mode(1);  // rtgs
					else
						rcp.setPay_mode(0); // ho chq book

				}

				/// Table detail set here
				colum = new Vector();
				if(depo_code==8) // ONLY FOR HO PACKAGE
					colum.addElement(false);   // check boolean 
				
				colum.addElement(rs.getString(10));   // ref no
				colum.addElement(rs.getString(11));  //bill no
				//				colum.addElement(sdf.format(rs.getDate(12)));  //bill_date
				//	colum.addElement(rs.getDate(12));  //bill_date
				if(rs.getDate(12)!=null)
					colum.addElement(sdf.format(rs.getDate(12)));  //bill_date
				else
					colum.addElement("");  //bill_date

				colum.addElement(rs.getDouble(13));  //bill_amt
				colum.addElement(rs.getDouble(14));  //tds_amt
				colum.addElement(0.00);  //paid amount
				colum.addElement(rs.getDouble(15));  //paid amount
				if(rs.getDate(16)!=null)
					colum.addElement(sdf.format(rs.getDate(16)));  //voudt
				else
					colum.addElement("");  //voudt

				row.addElement(colum);
				total+=rs.getDouble(15);


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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail1 " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		


	////////////////bank Receipt Voucher (get vou detail method for (Direct/Creditors)

	public RcpDto getVouDetail2(int vou,int depo_code,int div,int fyear,int bkcd,int subdiv)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		String code=null;
		try {


			code="040"+depo_code+"01";
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			System.out.println("cash ka code "+code);
			
			String query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.vac_code,f.name," +
				" f.vnart_1,f.vamount,f.chq_no,f.chq_date,f.sub_div from ledfile as f "+
				" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
				" f.vbook_cd1=? and f.vou_no=? and f.vac_code<>? and f.sub_div=? and f.vdbcr='CR' and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  
//			" f.vbook_cd1=? and f.vou_no=? and f.vdbcr='CR' and f.vac_code<>? and f.sub_div=? and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,vou); 
			ps2.setString(6,code); 
			ps2.setInt(7,subdiv); 

			rs= ps2.executeQuery();

			if (rs.next())
			{
				System.out.println("yeha per aaya");
				rcp= new RcpDto();
				rcp.setVou_no(rs.getInt(2));
				rcp.setVou_date(rs.getDate(3));
				rcp.setVac_code(rs.getString(4));
				rcp.setParty_name(rs.getString(5));
				rcp.setVnart1(rs.getString(6));
				rcp.setChq_no(rs.getString(8));
				rcp.setChq_date(rs.getDate(9));
				rcp.setChq_amt(rs.getDouble(7));
				rcp.setVouno(rs.getInt(10));
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail2 Bank Receipt Voucher " + ex);

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

		return rcp;
	}			



	////////////////bank Receipt Voucher (Parameter Date get vou detail method for (Direct/Creditors)

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
					"f.vnart_1,f.vamount,f.chq_no,f.chq_date,f.sub_div from ledfile as f "+
					"where  f.div_code=? and f.vdepo_code=? and "+ 
					"f.vbook_cd1=? and f.vou_date=? and vac_code<>? and  ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

//					"f.vbook_cd1=? and f.vou_date=? and vdbcr='CR' and vac_code<>? and  ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,bkcd); 
			ps2.setDate(4,new java.sql.Date(vdate.getTime())); 
			ps2.setString(5,code); 

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
				rcp.setChq_no(rs.getString(8));
				rcp.setChq_date(rs.getDate(9));
				rcp.setChq_amt(rs.getDouble(7));
				rcp.setVouno(rs.getInt(10));
				
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail2 Bank Receipt Voucher " + ex);

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

	//////////////// bank payment creditors method (Parameter Date)
	public List getVouList1(Date voudt,Date edate,int depo_code,int div,int bkcd,String tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		Vector v=null;
		Vector col=null;
		String query2=null;
		SimpleDateFormat sdf=null;
		int xdepo=depo_code;
		int tran=99;
		int vter_cd=0;
		boolean advance=false;
		if(depo_code>200)
		{
			advance=true;
			depo_code-=199;
			vter_cd=depo_code;
			depo_code=8;
			
		}
		else
			depo_code-=99;

		if (depo_code < 0)
		{
			depo_code=xdepo;
			tran=0;
		}
			
		try {



			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");

			query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,l.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
					" r.vouno,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vamount,l.vou_lo,r.voudt,ifnull(l.vou_lo,'H') "+
					" from ledfile as l,rcpfile as r "+
					" where l.div_code=? and l.vdepo_code=? and l.vbook_cd1=?  and l.vdbcr='DR' and l.tp='C' and l.vou_date between ? and ? "+
					" and ifnull(l.tran_type,0)=?  and  ifnull(l.del_tag,'')<>'D' and  r.div_code=? and r.vdepo_code=? and r.vou_date between ? and ? and l.vou_no=r.vou_no " +
					" and ifnull(r.tran_type,0)=? and  ifnull(r.del_tag,'')<>'D' " ;

			if(tran==99)
			{
				if(advance)
				{
					query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,l.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
							" '' inv_lo,'' bill_no,null bill_date,0 bill_amt,0 tds_amt,0 vamount,concat('VP',indicator,right(l.vou_no,5)) vou_lo,null voudt,ifnull(l.vou_lo,'H') "+
							" from ledfile as l "+
							" where l.div_code=? and l.vdepo_code=? and l.vbook_cd1=?  and l.vdbcr='DR' and l.tp='C' and l.indicator='"+tp+"' and l.vou_date between ? and ? and vter_cd="+vter_cd+
							" and ifnull(l.tran_type,0)=?  and  ifnull(l.del_tag,'')<>'D' ";
				}
				else
				{
					query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,l.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
							" r.inv_lo,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vamount,concat('VP',indicator,right(l.vou_no,5)) vou_lo,r.voudt,ifnull(l.vou_lo,'H') "+
							" from ledfile as l,rcpfile as r "+
							" where l.vac_code=r.vac_code and l.div_code=? and l.vdepo_code=? and l.vbook_cd1=?  and l.vdbcr='DR' and l.tp='C' and l.indicator='"+tp+"' and l.vou_date between ? and ? "+
							" and ifnull(l.tran_type,0)=?  and  ifnull(l.del_tag,'')<>'D' and  r.div_code=? and r.vdepo_code=? and r.vou_date between ? and ? and l.vou_no=r.vou_no " +
							" and ifnull(r.tran_type,0)=? and  ifnull(r.del_tag,'')<>'D' " ;
				}
			}
			ps2 = con.prepareStatement(query2);

			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,bkcd); 
			ps2.setDate(4,new java.sql.Date(voudt.getTime()));
			ps2.setDate(5,new java.sql.Date(edate.getTime()));
			ps2.setInt(6,tran);
			if(!advance)
			{
				ps2.setInt(7,div);
				ps2.setInt(8, depo_code);
				ps2.setDate(9,new java.sql.Date(voudt.getTime())); 
				ps2.setDate(10,new java.sql.Date(edate.getTime()));
				ps2.setInt(11,tran);
			}
			rs= ps2.executeQuery();


			//TODO  fsdfs 



			v = new Vector();
			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;
			int vou=0; 
			while (rs.next())
			{


				if (first)
				{
					vou=rs.getInt(1);
					if (tran==99)
						inv = rs.getString(16);
					else
						inv = rs.getString(16)+"A"+rs.getString(1).substring(1);
					first=false;
					rcp= new RcpDto();
				}

				if (vou!=rs.getInt(1))
				{

					col= new Vector();
					col.addElement(inv);//concat inv_no
					col.addElement(rcp.getParty_name());//party name
					col.addElement(rcp);
					col.addElement(row);
					v.addElement(col);

					vou=rs.getInt(1);
					if (tran==99)
						inv = rs.getString(16);
					else
						inv = rs.getString(16)+"A"+rs.getString(1).substring(1);
					total=0.00;
					rcp= new RcpDto();
					row = new Vector();   // Create new Table row



				}


				rcp.setVou_no(rs.getInt(1));
				rcp.setVou_date(rs.getDate(2));
				rcp.setVac_code(rs.getString(3));
				rcp.setParty_name(rs.getString(4));
				rcp.setVnart1(rs.getString(5));
				rcp.setChq_no(rs.getString(6));
				rcp.setChq_date(rs.getDate(7));
				rcp.setChq_amt(rs.getDouble(8));
				rcp.setBank_chg(rs.getDouble(9));
				if(rs.getString(18).equalsIgnoreCase("R"))
					rcp.setPay_mode(1);  // rtgs
				else
					rcp.setPay_mode(0); // ho chq book


				/// Table detail set here
				colum = new Vector();
				if(depo_code==8) // ONLY FOR HO PACKAGE
					colum.addElement(false);   // check boolean 

				colum.addElement(rs.getString(10));   // ref no
				colum.addElement(rs.getString(11));  //bill no

				if(rs.getDate(12)!=null)
					colum.addElement(sdf.format(rs.getDate(12)));  //bill_date
				else
					colum.addElement("__/__/____");  //bill_date
				
				colum.addElement(rs.getDouble(13));  //bill_amt
				colum.addElement(rs.getDouble(14));  //tds_amt
				colum.addElement(0.00);  //paid amount
				colum.addElement(rs.getDouble(15));  //paid amount
				
				if(rs.getDate(17)!=null)
					colum.addElement(sdf.format(rs.getDate(17)));  //voudt
				else
					colum.addElement("__/__/____");  //voudt
				
				
				
				
				row.addElement(colum);
				total+=rs.getDouble(15);
				rcp.setVamount(total);
			}


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
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getVouList1 " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return v;
	}		

	///////////////////// GET OUTSTAND DETAIL OF CREDITORS
	public RcpDto getBillDetail(int depo_code,int wdepo,int fyear,String code,Date due_date)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		java.sql.Date duedate=null;
		int div=4;
		try {
			duedate = new java.sql.Date(due_date.getTime());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		try {

			
				System.out.println("DEPO "+depo_code+" wdepo "+wdepo);


			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");
			String vlo="";
 
			String query2 =" select a.vou_no,a.bill_no,a.bill_date,a.bill_amt,a.tds_amt,(a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) bal,a.vou_date,a.vou_lo from "+ 
			" (select l.vou_no,l.bill_no,l.bill_date,l.bill_amt,l.tds_amt,l.vou_date,l.vou_lo,l.vac_code "+
			" from rcpfile l where   l.div_code=? and l.vdepo_code=? "+ 
			" and l.vbook_cd in (60,61) and l.vou_date between '2011-04-01' and '2014-03-31' " +
			" and l.vac_code=? and (vamount-chq_amt)>0 and  ifnull(l.del_tag,'')<>'D' " +
			" union all " +
			" select l.vou_no,l.bill_no,l.bill_date,l.bill_amt,l.tds_amt,l.vou_date,l.vou_lo,l.vac_code "+
			" from ledfile l where  l.fin_year between 2014 and ? and l.div_code=? and l.vdepo_code=? "+ 
			" and l.vbook_cd1 in (60,61) and l.vac_code=? and l.vou_date<'2017-07-01' and ifnull(l.indicator,'')<>'A' "+
			" and  ifnull(l.del_tag,'')<>'D' "+
			" union all " +
			" select l.vou_no,l.bill_no,l.bill_date,l.bill_amt,l.tds_amt,l.vou_date,l.vou_lo,l.vac_code "+
			" from rcpfile l where  l.fin_year between 2017 and ? and l.div_code=? and l.vdepo_code=? "+ 
			" and l.vbook_cd in (60,61) and l.vac_code=? and l.vou_date>'2017-06-30'  "+
			" and  ifnull(l.del_tag,'')<>'D' ) a "+

			" left join "+

			" (select r.vouno vou_no,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vou_date,r.vou_lo,sum(r.vamount) vamount,r.vac_code "+
			" from rcpfile r where  r.fin_year<=? and r.div_code=? and r.vdepo_code=?  "+
			" and r.vbook_cd in (20,21,22,98)  and r.vac_code=? and    ifnull(r.cr_no,'')<>'C' "+
			" and  ifnull(r.del_tag,'')<>'D' group by r.vouno,r.bill_no,r.bill_date) b "+
			" on  a.vac_code=b.vac_code and a.vou_no=b.vou_no and a.bill_date=b.bill_date  "+  ///and a.bill_amt=b.bill_amt
			" where  (a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) > 0 " ;
			
			if (depo_code==8 && wdepo==8)
			{
				
				// mkt_year<>2016 for old bills locked forcefully 29/09/2017 // 
				query2 =" select a.vou_no,a.bill_no,a.bill_date,a.bill_amt,a.tds_amt,(a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) bal," +
						" a.vou_date,a.vou_lo,ifnull(a.due_date,a.vou_date),a.vnart_1 from "+ 
						" (select l.vou_no,l.bill_no,l.bill_date,l.bill_amt,l.tds_amt,l.vou_date,concat(right(vou_lo,2),left(vou_lo,1),indicator,right(vou_no,4)) vou_lo,l.vac_code,l.due_date,l.vnart_1 "+
						" from ledfile l where  l.fin_year between 2013 and ? and l.div_code=? and l.vdepo_code=? "+ 
						" and l.vbook_cd in (60,61) and l.vac_code=? and ifnull(l.mkt_year,0)<> 2016 "+
						" and  ifnull(l.del_tag,'')<>'D' ) a "+
						" left join "+

						" (select r.vou_no,r.inv_lo,r.bill_date,r.bill_amt,r.tds_amt,r.vou_date,r.vou_lo,sum(r.vamount) vamount,r.vac_code "+
						" from rcpfile r where  r.fin_year<=? and r.div_code=? and r.vdepo_code=?  "+
						" and r.vbook_cd in (20,21,22,98)  and r.vac_code=? and    ifnull(r.cr_no,'')<>'C' "+
						" and  ifnull(r.del_tag,'')<>'D' group by r.inv_lo,r.bill_date) b "+
						" on  a.vac_code=b.vac_code and a.vou_lo=b.inv_lo and a.bill_date=b.bill_date and a.bill_amt=b.bill_amt "+
						" where  (a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) > 0 group by a.vou_date,a.vou_no,a.vou_lo" ;
				
			}
			else if (depo_code==8 && wdepo!=8)
			{
				query2 =" select a.vou_no,a.bill_no,a.bill_date,a.bill_amt,a.tds_amt,(a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) bal," +
						" a.vou_date,a.vou_lo,ifnull(a.due_date,a.vou_date) from "+ 
						" (select l.vou_no,l.bill_no,l.bill_date,l.bill_amt,l.tds_amt,l.vou_date,concat(right(vou_lo,2),left(vou_lo,1),indicator,right(vou_no,4)) vou_lo,l.vac_code,l.due_date "+
						" from ledfile l where  l.fin_year between 2013 and ? and l.div_code=? and l.vdepo_code=? and l.vter_cd = "+wdepo+ 
						" and l.vbook_cd in (60,61) and l.vac_code=? and ifnull(l.mkt_year,0)<> 2016 "+
						" and  ifnull(l.del_tag,'')<>'D' ) a "+
						" left join "+

						" (select r.vou_no,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vou_date,r.vou_lo,sum(r.vamount) vamount,r.vac_code "+
						" from rcpfile r where  r.fin_year<=? and r.div_code=? and r.vdepo_code=?  "+
						" and r.vbook_cd in (20,21,22,98)  and r.vac_code=? and    ifnull(r.cr_no,'')<>'C' "+
						" and  ifnull(r.del_tag,'')<>'D' group by r.vouno,r.bill_no,r.bill_date) b "+
						" on  a.vac_code=b.vac_code and a.bill_no=b.bill_no and a.bill_date=b.bill_date and a.bill_amt=b.bill_amt "+
						" where  (a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) > 0 group by a.vou_date,a.vou_no,a.bill_no" ;
				
			}
			ps2 = con.prepareStatement(query2);

			if(depo_code==8)
			{
				ps2.setInt(1,fyear);
				ps2.setInt(2,div);
				ps2.setInt(3, depo_code);
				ps2.setString(4,code); 

				ps2.setInt(5,fyear);
				ps2.setInt(6,div);
				ps2.setInt(7, depo_code);
				ps2.setString(8,code); 
				
			}
			else
			{
				ps2.setInt(1,div);
				ps2.setInt(2, depo_code);
				ps2.setString(3,code); 
				ps2.setInt(4,fyear);
				ps2.setInt(5,div);
				ps2.setInt(6, depo_code);
				ps2.setString(7,code); 
				ps2.setInt(8,fyear);
				ps2.setInt(9,div);
				ps2.setInt(10, depo_code);
				ps2.setString(11,code); 

				ps2.setInt(12,fyear);
				ps2.setInt(13,div);
				ps2.setInt(14, depo_code);
				ps2.setString(15,code); 
			}
			
			rs= ps2.executeQuery();



			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;

			while (rs.next())
			{

				/// Table detail set here
				if(depo_code==8 && (rs.getDate(9)!=null && (rs.getDate(9).before(duedate) || rs.getDate(9).equals(duedate))))
				{
					
					System.out.println("narration "+rs.getString(10));
					colum = new Vector();
					colum.addElement(false);   // heck box
					colum.addElement(rs.getString(8));   // ref no
//					colum.addElement(rs.getInt(1));   // ref no
								colum.addElement(rs.getString(2));  //bill no
					if(rs.getDate(3)!=null)
					{
						colum.addElement(sdf.format(rs.getDate(3)));  //bill_date
					}
					else
					{
						colum.addElement("__/__/____");  //bill_date
					}
					//colum.addElement(rs.getDate(3));  //bill_date
					colum.addElement(nf.format(rs.getDouble(4)));  //bill_amt
					colum.addElement(nf.format(rs.getDouble(5)));  //tds_amt
					colum.addElement(nf.format(rs.getDouble(6)));  //balance amt
					colum.addElement(0.00);  //balance amt
					colum.addElement(sdf.format(rs.getDate(7)));  //vou_date
					colum.addElement(rs.getString(10));   // narration
					vlo=rs.getString(8);
					row.addElement(colum);
				}
				else if(depo_code!=8)
				{
					colum = new Vector();
					colum.addElement(rs.getInt(1));   // ref no
					colum.addElement(rs.getString(2));  //bill no
					if(rs.getDate(3)!=null)
					{
						colum.addElement(sdf.format(rs.getDate(3)));  //bill_date
					}
					else
					{
						colum.addElement("__/__/____");  //bill_date
					}
					//colum.addElement(rs.getDate(3));  //bill_date
					colum.addElement(nf.format(rs.getDouble(4)));  //bill_amt
					colum.addElement(nf.format(rs.getDouble(5)));  //tds_amt
					colum.addElement(nf.format(rs.getDouble(6)));  //balance amt
					colum.addElement(0.00);  //balance amt
					colum.addElement(sdf.format(rs.getDate(7)));  //vou_date
					vlo=rs.getString(8);
					row.addElement(colum);
				}
			}
			rcp= new RcpDto();
			rcp.setVdetail(row);
			rcp.setVou_lo(vlo);
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
			System.out.println("-------------Exception in BankVouDAO.getBillDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		




	//// method to get bank vou no for account package 
	public int getVouNo(int depo,int div,int year,int bkcd,String dbcr)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		PreparedStatement ps2 = null;
		ResultSet rst2=null;
		PreparedStatement ps4 = null;
		ResultSet  rs4 = null;
		Connection con=null;
		int i=0;
		int ii=0;
		int iii=0;
		int sdiv=div;
		int wbk=bkcd;
		if(wbk==95)
			wbk=94;

		if(wbk==955)
			wbk=95;

		if (div==40)
			sdiv=1;
		try {
			con=ConnectionFactory.getConnection();
			String query1 ="select IFNULL(max(vou_no),0) from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd1=? and vdbcr=? and  ifnull(del_tag,'')<>'D' "; 
			
			String query2 ="select IFNULL(max(vou_no),0) from ledfile where fin_year=? and  div_code=4 and sub_div=? and vdepo_code=? and vbook_cd1=? and vdbcr=? and  ifnull(del_tag,'')<>'D' ";

			
			String query3 ="select IFNULL(max(vou_no),0) from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd1=? and vdbcr=? and tran_type=99 and  ifnull(del_tag,'')<>'D' "; 

			
			con.setAutoCommit(false);
			
			
			
			if (div==80)  // for ho entry
			{
				ps1 =con.prepareStatement(query3);
				ps1.setInt(1, year);
				ps1.setInt(2, 4);
				ps1.setInt(3, depo);
				ps1.setInt(4, bkcd);
				ps1.setString(5, dbcr);

				rst =ps1.executeQuery();

				if(rst.next())
				{
					i =rst.getInt(1); 
				}

				System.out.println("value of i si "+i);
			}
			else
			{
				ps2 =con.prepareStatement(query2);
				ps2.setInt(1, year);
				ps2.setInt(2, sdiv); // sub div
				ps2.setInt(3, depo);
				ps2.setInt(4, wbk);
				ps2.setString(5, dbcr);

				rst2 =ps2.executeQuery();

				if(rst2.next())
				{
					ii =rst2.getInt(1); 
					System.out.println("YEHA PER AAYA KYA "+ii);
				}
				rst2.close();

				ps2.close();


				ps1 =con.prepareStatement(query1);
				ps1.setInt(1, year);
				ps1.setInt(2, div);
				ps1.setInt(3, depo);
				ps1.setInt(4, bkcd);
				ps1.setString(5, dbcr);

				rst =ps1.executeQuery();

				if(rst.next())
				{
					i =rst.getInt(1); 
					System.out.println("YEHA PER BHI AAYA KYA "+i);
				}

				if (ii>i)
					i=ii;

			}
			
			
			
			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {System.out.println("error hai "+ex);
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in BankVouDAO.getVouNo " + ex);
		i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(ps2 != null){ps2.close();}
				if(rst2 != null){rst2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 






	//// method to add record in led file (bank voucher [Direct Payment])	
	public int addLed(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		RcpDto rcp=null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,exp_code,sub_code,vnart_1,name,vamount,vdbcr,chq_no,chq_date,chq_amt,bill_no,bill_date,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,vnart_2,sub_div,tds_amt,bill_amt,cr_amt,sertax_amt,sertax_billper,sertax_per) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			// 040+depo+01

			ledps=con.prepareStatement(led);

			for(int j=0;j<cList.size();j++)
			{
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

				ledps.setString(13, rcp.getParty_name());
				ledps.setDouble(14, rcp.getVamount());
				ledps.setString(15, "DR");
				ledps.setString(16, rcp.getChq_no());

				if (rcp.getChq_date()!=null)
					ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(17, null);

				ledps.setDouble(18, rcp.getChq_amt());
				ledps.setString(19, rcp.getBill_no());
				if (rcp.getBill_date()!=null)
					ledps.setDate(20, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(20, null);

				ledps.setString(21, String.valueOf(rcp.getTp()));
				ledps.setInt(22, rcp.getCreated_by());
				ledps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(24, rcp.getSerialno());
				ledps.setInt(25, rcp.getFin_year());
				ledps.setInt(26, rcp.getMnth_code());
				ledps.setString(27, rcp.getVnart2());
				ledps.setInt(28, 1); // sub div
				ledps.setDouble(29, rcp.getTds_amt());
				ledps.setDouble(30, rcp.getBill_amt());
				ledps.setDouble(31, rcp.getCr_amt());  // taxable service tax
				ledps.setDouble(32, rcp.getSertax_amt());
				ledps.setDouble(33, rcp.getSertax_billper());
				ledps.setDouble(34, rcp.getSertax_per());


				i=ledps.executeUpdate();
			}

			//voucher no 
			i=rcp.getVou_no();
			System.out.println("voucher no "+i);
			con.commit();
			con.setAutoCommit(true);
			ledps.close();

		} catch (SQLException ex) {
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


	//// method to add record in led file (bank voucher [Creditors Payment])	
	public int addLed1(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;
		PreparedStatement outps=null;
		RcpDto rcp=null;
		int expcode=0;
		ChequeDto chque=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,vnart_1,name,vamount,vdbcr,chq_no,chq_date,chq_amt,bank_chg,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,vbk_code,sub_div,tran_type,exp_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			String rp="insert into rcpfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
					"vou_date,head_code,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date,chq_amt,bill_no,bill_date,bill_amt,vouno,voudt,tds_amt, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,inv_lo,tran_type) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

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
				if (rcp.getChq_date()!=null)
				rcpps.setDate(14, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					rcpps.setDate(14, null);
					
				rcpps.setDouble(15, rcp.getChq_amt());
				rcpps.setString(16, rcp.getBill_no());

				if(rcp.getBill_date()!=null)
					rcpps.setDate(17, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					rcpps.setDate(17,null);

				rcpps.setDouble(18, rcp.getBill_amt());
				rcpps.setInt(19, rcp.getVouno());

				if(rcp.getVoudt()!=null)
					rcpps.setDate(20, new java.sql.Date(rcp.getVoudt().getTime()));
				else
					rcpps.setDate(20, null);

				rcpps.setDouble(21, rcp.getTds_amt());
				rcpps.setInt(22, rcp.getCreated_by());
				rcpps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
				rcpps.setInt(24, rcp.getSerialno());
				rcpps.setInt(25, rcp.getFin_year());
				rcpps.setInt(26, rcp.getMnth_code());
				rcpps.setString(27, rcp.getInv_lo());
				rcpps.setInt(28, rcp.getTran_ype());
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

				if(rcp.getVoudt()!=null)
					outps.setDate(7, new java.sql.Date(rcp.getVoudt().getTime()));
				else
					outps.setDate(7, null);


				i=outps.executeUpdate();

				i=rcp.getVou_no();

			} // end of loop

			//  add record in led file
			if (rcp!=null)
			{	
				expcode=0;
				if(rcp.getBank_chg()>0)
					expcode=Integer.parseInt("700"+rcp.getVdepo_code()+"23");

//				expcode=Integer.parseInt("703"+rcp.getVdepo_code()+"23");  //tf
//				expcode=Integer.parseInt("706"+rcp.getVdepo_code()+"23");  // genetica 

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
				if (rcp.getChq_date()!=null)
					ledps.setDate(15, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(15, null);
					
				ledps.setDouble(16, rcp.getChq_amt());
				ledps.setDouble(17, rcp.getBank_chg());
				ledps.setString(18, String.valueOf(rcp.getTp()));
				ledps.setInt(19, rcp.getCreated_by());
				ledps.setDate(20, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(21, rcp.getSerialno());
				ledps.setInt(22, rcp.getFin_year());
				ledps.setInt(23, rcp.getMnth_code());
				ledps.setInt(24, rcp.getVbk_code());
				ledps.setInt(25, 1); // sub diviiosn
				ledps.setInt(26, rcp.getTran_ype());
				ledps.setInt(27, expcode);
				i=ledps.executeUpdate();

				i=rcp.getVou_no();
				
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
			System.out.println("-------------Exception in SQLRcpDAO.addLed -HOBankPaymentCR " + ex);
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
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close HOBankPaymentCR "+e);
			}
		}

		return (i);
	}			



	//// method to add record in led file (bank Receipt voucher [Direct/Creditors])	
	public int addLed2(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,exp_code,vnart_1,vnart_2,name,vamount,vdbcr,chq_no,chq_date,chq_amt,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,sub_div,vou_yr) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";



			String rp="insert into rcpfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
					"vou_date,head_code,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date,chq_amt, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			
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
			ledps.setString(12, rcp.getVnart2());

//			ledps.setString(13, rcp.getParty_name());
			ledps.setString(13, rcp.getVnart2());  // paid to
			ledps.setDouble(14, rcp.getVamount());
			ledps.setString(15, "CR");
			ledps.setString(16, rcp.getChq_no());
			if (rcp.getChq_date()!=null)
				ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(17, null);
				
			ledps.setDouble(18, rcp.getChq_amt());
			ledps.setString(19, String.valueOf(rcp.getTp()));
			ledps.setInt(20, rcp.getCreated_by());
			ledps.setDate(21, new java.sql.Date(rcp.getCreated_date().getTime()));
			ledps.setInt(22, rcp.getSerialno());
			ledps.setInt(23, rcp.getFin_year());
			ledps.setInt(24, rcp.getMnth_code());
			ledps.setInt(25, rcp.getVouno()); // div code of mf/tf/div
			ledps.setInt(26, rcp.getVou_yr()); 


			i=ledps.executeUpdate();


			
			rcpps=con.prepareStatement(rp);
			rcpps.setInt(1, rcp.getDiv_code());
			rcpps.setInt(2, rcp.getVdepo_code());
			rcpps.setInt(3, rcp.getVbook_cd());
			rcpps.setInt(4, rcp.getVou_yr());
			rcpps.setString(5, rcp.getVou_lo());
			rcpps.setInt(6, rcp.getVou_no());
			rcpps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
			rcpps.setInt(8,rcp.getVgrp_code()); // vgrp_Code
			rcpps.setString(9, rcp.getVac_code());
			rcpps.setString(10, rcp.getVnart1());
			rcpps.setDouble(11, rcp.getVamount());
			rcpps.setString(12, "CR");
			rcpps.setString(13, rcp.getChq_no());
			if (rcp.getChq_date()!=null)
				rcpps.setDate(14, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				rcpps.setDate(14, null);
				
			rcpps.setDouble(15, rcp.getChq_amt());
			rcpps.setInt(16, rcp.getCreated_by());
			rcpps.setDate(17, new java.sql.Date(rcp.getCreated_date().getTime()));
			rcpps.setInt(18, rcp.getSerialno());
			rcpps.setInt(19, rcp.getFin_year());
			rcpps.setInt(20, rcp.getMnth_code());


			i=rcpps.executeUpdate();

			
			
			i=rcp.getVou_no();
			
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			rcpps.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.addLed2 Bank Receipt Voucher" + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(rcpps != null){rcpps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close Bank Receipt Voucher "+e);
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
					"sub_code=?,vnart_1=?,name=?,vamount=?,chq_no=?,chq_date=?,chq_amt=?,bill_no=?,bill_date=?," +
					"modified_by=?,modified_date=?,vnart_2=?,bank_chg=?,tds_amt=?,vac_code=?,bill_amt=?,cr_amt=?,sertax_amt=?,sertax_billper=?,sertax_per=?,   " +
					" taxable_amt=?,ctax_per=?, stax_per=?, itax_per=?, "+
					" cgst_amt=?, sgst_amt=?, igst_amt=?,net_amt=?,addl_taxper=?,addl_taxamt=?,vreg_cd=?," +
					" varea_cd=?,gst_no=?,hsn_code=? "+						

					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR' and serialno=?";


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
				ledps.setString(7, rcp.getParty_name());
				ledps.setDouble(8, rcp.getVamount());
				ledps.setString(9, rcp.getChq_no());
				if(rcp.getChq_date()!=null)
					ledps.setDate(10, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(10, null);

				ledps.setDouble(11, rcp.getChq_amt());
				ledps.setString(12, rcp.getBill_no());

				if(rcp.getBill_date()!=null)
					ledps.setDate(13, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(13, null);

				ledps.setInt(14, rcp.getModified_by());
				ledps.setDate(15, new java.sql.Date(rcp.getModified_date().getTime()));
				ledps.setString(16, rcp.getVnart2());
				ledps.setDouble(17, rcp.getBank_chg());
//				if (rcp.getVdepo_code()==11 || rcp.getVdepo_code()==3 || rcp.getVdepo_code()==12)
				ledps.setDouble(18, rcp.getTds_amt());

				ledps.setString(19, rcp.getVac_code());
				ledps.setDouble(20, rcp.getBill_amt());
				ledps.setDouble(21, rcp.getCr_amt());  // taxable service tax
				ledps.setDouble(22, rcp.getSertax_amt());
				ledps.setDouble(23, rcp.getSertax_billper());
				ledps.setDouble(24, rcp.getSertax_per());
				ledps.setDouble(25, rcp.getCr_amt());  // taxable   
				ledps.setDouble(26, rcp.getCtaxper());   
				ledps.setDouble(27, rcp.getStaxper());   
				ledps.setDouble(28, rcp.getItaxper());   
				ledps.setDouble(29, rcp.getCgstamt());   
				ledps.setDouble(30, rcp.getSgstamt());   
				ledps.setDouble(31, rcp.getIgstamt());   
				ledps.setDouble(32, rcp.getNetamt());   
				ledps.setDouble(33, rcp.getCess_per());  // cess per   
				ledps.setDouble(34, rcp.getCess_amt());  // cess amt   
				ledps.setInt(35, rcp.getVreg_cd()); // rcm y/n y=1   
				ledps.setInt(36, rcp.getVarea_cd()); // itc y/n/ y=1   
				ledps.setString(37, ""); //gst no
				ledps.setLong(38, 0);  // hsn code

				// where clause
				ledps.setInt(39, rcp.getFin_year());
				ledps.setInt(40, rcp.getDiv_code());
				ledps.setInt(41, rcp.getVdepo_code());
				ledps.setInt(42, rcp.getVbook_cd1());
				ledps.setInt(43, rcp.getVou_no());
				ledps.setInt(44, rcp.getSerialno());


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


	//// method to update record in led file (Bank Receipt voucher)	
	public int updateLed1(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



			String led="update  ledfile set vou_date=?,vgrp_code=?,exp_code=?," +
					"vnart_1=?,name=?,vamount=?,chq_no=?,chq_date=?,chq_amt=?,modified_by=?,modified_date=?,vnart_2=? " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='CR'";


			ledps=con.prepareStatement(led);

			ledps.setDate(1, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setInt(2,rcp.getVgrp_code()); // vgrp_Code
			ledps.setString(3, rcp.getExp_code());
			ledps.setString(4, rcp.getVnart1());
///			ledps.setString(5, rcp.getParty_name());
			ledps.setString(5, rcp.getVnart2());
			ledps.setDouble(6, rcp.getVamount());
			ledps.setString(7, rcp.getChq_no());
			if(rcp.getChq_date()!=null)
				ledps.setDate(8, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(8, null);
			ledps.setDouble(9, rcp.getChq_amt());
			ledps.setInt(10, rcp.getModified_by());
			ledps.setDate(11, new java.sql.Date(rcp.getModified_date().getTime()));

			ledps.setString(12, rcp.getVnart2());

			// where clause
			ledps.setInt(13, rcp.getFin_year());
			ledps.setInt(14, rcp.getDiv_code());
			ledps.setInt(15, rcp.getVdepo_code());
			ledps.setInt(16, rcp.getVbook_cd1());
			ledps.setInt(17, rcp.getVou_no());


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
			System.out.println("-------------Exception in SQLRcpDAO.updateLed1 Bank Receipt Voucher " + ex);
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


	//// method to update record in led file (Bank voucher Direct)	
	public int deleteLed(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;


		RcpDto rcp=null;

		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String led="update ledfile set del_tag=?,modified_by=?,modified_date=?  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR' and serialno=?";


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
					"and vou_no=? and vdbcr='DR' and vou_date=?";

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
				if (rcp.getVoudt()!=null)
					outps.setDate(7, new java.sql.Date(rcp.getVoudt().getTime()));
				else
					outps.setDate(7, null);
					
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
			ex.printStackTrace();
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



	//// method to update record in led file (Bank Receipt voucher Direct/Creditors)	
	public int deleteLed(int div,int depo,int vou, int fyear,int uid,int bkcd)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;




		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String led="update ledfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='CR'  ";

			String rcp="update rcpfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? " +
					"and vou_no=? and vdbcr='CR'  ";

			if (bkcd==95)
			{
				led="update ledfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR'  ";
				
				rcp="update rcpfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
						"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? " +
						"and vou_no=? and vdbcr='DR'  ";


			}



			
			
			ledps=con.prepareStatement(led);
			rcpps=con.prepareStatement(rcp);



			ledps.setString(1, "D");
			ledps.setInt(2, uid);
			// where clause
			ledps.setInt(3,fyear); 
			ledps.setInt(4,div);
			ledps.setInt(5, depo);
			ledps.setInt(6, bkcd);
			ledps.setInt(7, vou);

			i=ledps.executeUpdate();
//////////////////// rcpfile ////////////////////
			rcpps.setString(1, "D");
			rcpps.setInt(2, uid);
			// where clause
			rcpps.setInt(3,fyear); 
			rcpps.setInt(4,div);
			rcpps.setInt(5, depo);
			rcpps.setInt(6, bkcd);
			rcpps.setInt(7, vou);

			i=rcpps.executeUpdate();


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
			System.out.println("-------------Exception in SQLRcpDAO.deleteLed " + ex);
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

	

	//// New method to update record in led file (HO Bank Receipt voucher Direct/Creditors)	
	public int deleteLedHO(int div,int depo,int vou, int fyear,int uid,int bkcd,RcpDto rcpDto)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;

		PreparedStatement ledup=null;
		PreparedStatement chqps=null;



		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String led="update ledfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='CR'  ";

			String rcp="update rcpfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? " +
					"and vou_no=? and vdbcr='CR'  ";

			if (bkcd==95)
			{
				led="update ledfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR'  ";

			}

			String chq="update chqmast set del_tag='N' " +
					" where div_code=? and depo_code=? and bank_code=? and cheque_no=? " +
					" and vou_no=? and vou_date=? and reconcil<>'Y' ";

			
			
			String ledupd="update rcpfile set cr_no='' where div_code=? and vdepo_code=? and vac_code=? " +
					" and vbook_cd=? and vou_no=? and vou_date=? and ifnull(del_tag,'')<>'D' ";


			ledup=con.prepareStatement(ledupd);
			chqps=con.prepareStatement(chq);

			
			
			ledps=con.prepareStatement(led);
			rcpps=con.prepareStatement(rcp);



			ledps.setString(1, "D");
			ledps.setInt(2, uid);
			// where clause
			ledps.setInt(3,fyear); 
			ledps.setInt(4,div);
			ledps.setInt(5, depo);
			ledps.setInt(6, bkcd);
			ledps.setInt(7, vou);

			i=ledps.executeUpdate();
//////////////////// rcpfile ////////////////////
			rcpps.setString(1, "D");
			rcpps.setInt(2, uid);
			// where clause
			rcpps.setInt(3,fyear); 
			rcpps.setInt(4,div);
			rcpps.setInt(5, depo);
			rcpps.setInt(6, bkcd);
			rcpps.setInt(7, vou);

			i=rcpps.executeUpdate();

			/// only in case of creditors
			
			chqps.setInt(1, div);
			chqps.setInt(2, depo);
			chqps.setInt(3, bkcd);
	//		chqps.setString(4, rcpDto.getChq_no());

			
			try {
				chqps.setInt(4, Integer.parseInt(rcpDto.getChq_no()));
			} catch (Exception e) {
				chqps.setInt(4, 0);
			}
			
			chqps.setInt(5, rcpDto.getInv_no());  // REF NO
			chqps.setDate(6, setSqlDate(rcpDto.getDue_Date())); // Ref Date
			i=chqps.executeUpdate();
			
			
			ledup.setInt(1, div);
			ledup.setInt(2, depo);
			ledup.setString(3, rcpDto.getVac_code());
			ledup.setInt(4, bkcd);
			ledup.setInt(5, rcpDto.getInv_no());
			ledup.setDate(6, setSqlDate(rcpDto.getDue_Date())); // Ref Date
		
			i=ledup.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			rcpps.close();
			chqps.close();
			ledup.close();
			

		} catch (SQLException ex) { ex.printStackTrace();
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
				if(rcpps != null){rcpps.close();}
				if(chqps != null){chqps.close();}
				if(ledup != null){ledup.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}		

	
	//// method to update record in led file (Bank payment  voucher Debtors)	
	public int deleteLed1(int div,int depo,int vou, int fyear,int uid,int bkcd,int subdiv)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;




		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String led="update ledfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR' and sub_div=?  ";

			String rcp="update rcpfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? " +
					"and vou_no=? and vdbcr='DR'  ";


			ledps=con.prepareStatement(led);
			rcpps=con.prepareStatement(rcp);



			ledps.setString(1, "D");
			ledps.setInt(2, uid);
			// where clause
			ledps.setInt(3,fyear); 
			ledps.setInt(4,div);
			ledps.setInt(5, depo);
			ledps.setInt(6, bkcd);
			ledps.setInt(7, vou);
			ledps.setInt(8,subdiv);

			i=ledps.executeUpdate();


			
			ledps.setString(1, "D");
			ledps.setInt(2, uid);
			// where clause
			ledps.setInt(3,fyear); 
			ledps.setInt(4,subdiv);
			ledps.setInt(5, BaseClass.loginDt.getCmp_code());
			ledps.setInt(6, 81);
			ledps.setInt(7, vou);
			ledps.setInt(8,subdiv);

			i=ledps.executeUpdate();

			
			
			rcpps.setString(1, "D");
			rcpps.setInt(2, uid);
			// where clause
			rcpps.setInt(3,fyear); 
			rcpps.setInt(4,div);
			rcpps.setInt(5, depo);
			rcpps.setInt(6, bkcd);
			rcpps.setInt(7, vou);
		

			i=rcpps.executeUpdate();

			
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
				if(rcpps != null){rcpps.close();}
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
					" and r.vou_date=? and (r.vamount-r.rcp_amt1)>0 and r.vac_code=p.mac_code and ifnull(r.del_tag,'')<>'D' ";


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



	
	
	
	
	
	public List getChequeReturnDetail(int startVoucher,int endVoucher,int depo_code,int div,int fyear,int doctp,String tp,int cmp_code)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;

		PreparedStatement ps3 = null;
		ResultSet rs3=null;

		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		List voucherList=null;
		String query2=null; 
		int subdiv=0;
		try {

			subdiv=Integer.parseInt(tp);
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			
				query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,'' as subcode, '' as subname, " +
				"ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.chq_no,f.chq_date,f.vamount,f.bank_chg," +
				"f.vac_code,ifnull(f.bill_no,0),f.bill_date,f.interest,ifnull(f.name,'')  from ledfile as f "+
				",partyfst as p "+
				"where f.vac_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
				"f.vbook_cd1=? and sub_div=? and f.vou_no between ? and ?  and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' order by f.vou_no,f.serialno,f.vamount desc";  
	

				String query3="select if(bill_amt=0,vnart_1,bill_no),bill_date,if(bill_amt=0,vamount,bill_amt),vou_lo,vou_no  from rcpfile where div_code=?" +
						" and vdepo_code=? and vbook_cd in (?,99)  and vac_Code=? and vou_no=? and vou_Date=? and  ifnull(del_tag,'')<>'D' " ;

			
			ps3 = con.prepareStatement(query3);
			
			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4, doctp);
			ps2.setInt(5, subdiv);
			ps2.setInt(6,startVoucher); 
			ps2.setInt(7,endVoucher);
			ps2.setInt(8,(subdiv));
			ps2.setInt(9, cmp_code);
			
		 
			
			rs= ps2.executeQuery();
			
			voucherList = new ArrayList(); 
			int wno = 0;
			boolean first=true;
			double rgross = 0.00;
			int vou_no=0;
			boolean check=true;
			boolean print=true;
			
			while (rs.next())
			{
				 check=true;
				 try {
					vou_no=Integer.parseInt(rs.getString(20));
				} catch (NumberFormatException e) {

					vou_no=0;
					e.printStackTrace();
				}
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
		            print=true;
				}
				
				
				
				rgross+=rs.getDouble(11);

				if(print)
				{
					
					System.out.println(subdiv+" "+cmp_code+"  "+doctp+"  "+rs.getString(19)+" "+vou_no+"  "+rs.getDate(21));
					ps3.setInt(1,(subdiv));
					ps3.setInt(2, cmp_code);
					ps3.setInt(3, doctp);
					ps3.setString(4, rs.getString(19));
					ps3.setInt(5,vou_no); 
					ps3.setDate(6,rs.getDate(21));
					rs3= ps3.executeQuery();


					while (rs3.next())
					{
						rcp= new RcpDto();
						rcp.setVou_no(rs.getInt(3));
						rcp.setVou_date(rs.getDate(4));
						rcp.setParty_name(rs.getString(6));
						rcp.setVou_lo(rs.getString(1));
						rcp.setExp_code(rs.getString(19));
						rcp.setExp_code(rs.getString(19));
						rcp.setVac_code(rs.getString(19));
						rcp.setSub_code(rs.getString(7));
						rcp.setSub_name(rs.getString(8));
						rcp.setVnart1(rs.getString(9));
						rcp.setVnart2(rs.getString(10));
						rcp.setVamount(rs.getDouble(11));
						rcp.setDash(0);
						rcp.setChq_no(rs.getString(15));
						rcp.setChq_date(rs.getDate(16));
						rcp.setChq_amt(rs.getDouble(17));
						rcp.setBank_chg(rs.getDouble(18));
						rcp.setInterest(rs.getDouble(22));
						rcp.setInt_code(rs.getString(23));
						rcp.setBill_no(rs3.getString(1));
						rcp.setBill_date(rs3.getDate(2));
						rcp.setBill_amt(rs3.getDouble(3));
						rcp.setRcp_no(rs3.getString(4).charAt(0)+"R"+rs3.getString(4).charAt(1)+rs3.getString(5).substring(1));
						voucherList.add(rcp);
						check=false;
					}
					rs3.close();
					if(!check)
					print=false;
				}

					
				if(check && print)
				{
					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(3));
					rcp.setVou_date(rs.getDate(4));
					rcp.setParty_name(rs.getString(6));
					rcp.setVou_lo(rs.getString(1));
					rcp.setExp_code(rs.getString(19));
					rcp.setExp_code(rs.getString(19));
					rcp.setVac_code(rs.getString(19));
					rcp.setSub_code(rs.getString(7));
					rcp.setSub_name(rs.getString(8));
					rcp.setVnart1(rs.getString(9));
					rcp.setVnart2(rs.getString(10));
					rcp.setVamount(rs.getDouble(11));
					rcp.setDash(0);
					rcp.setChq_no(rs.getString(15));
					rcp.setChq_date(rs.getDate(16));
					rcp.setChq_amt(rs.getDouble(11));
					rcp.setBank_chg(rs.getDouble(18));
					rcp.setBill_no("");
					rcp.setBill_date(null);
					rcp.setBill_amt(0.00);
					rcp.setRcp_no("");
					voucherList.add(rcp);
					check=true;

				}
				
			}

			
			if(!first)
			{
				rcp= new RcpDto();
				rcp.setDash(2);
				rcp.setVamount(rgross);
				voucherList.add(rcp);
			}
            
            
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();
			ps3.close();

		} catch (SQLException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getMultiVouDetail " + ex);

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
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return voucherList;
	}		

	//// method to add record in led file (bank Payment voucher [Debtors])	
	public int addLed3(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		int cmpdepo=BaseClass.loginDt.getCmp_code();
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,exp_code,vnart_1,vnart_2,name,vamount,vdbcr,chq_no,chq_date,chq_amt,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,sub_div,vou_yr,vter_cd,bank_chg,vstat_cd,varea_cd,vreg_cd,vmsr_cd,mkt_year) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";



			String rp="insert into rcpfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
					"vou_date,head_code,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date,chq_amt, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			
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
			ledps.setString(12, rcp.getVnart2());

			ledps.setString(13, rcp.getParty_name());
			ledps.setDouble(14, rcp.getVamount());
			ledps.setString(15, "DR");
			ledps.setString(16, rcp.getChq_no());
			if (rcp.getChq_date()!=null)
				ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(17, null);
				
			ledps.setDouble(18, rcp.getChq_amt());
			ledps.setString(19, String.valueOf(rcp.getTp()));
			ledps.setInt(20, rcp.getCreated_by());
			ledps.setDate(21, new java.sql.Date(rcp.getCreated_date().getTime()));
			ledps.setInt(22, rcp.getSerialno());
			ledps.setInt(23, rcp.getFin_year());
			ledps.setInt(24, rcp.getMnth_code());
			ledps.setInt(25, rcp.getVouno()); // div code of mf/tf/div
			ledps.setInt(26, rcp.getVou_yr()); 
			ledps.setInt(27, rcp.getVouno()); 
			ledps.setDouble(28, rcp.getBank_chg());
			ledps.setInt(29, 0); 
			ledps.setInt(30, 0); 
			ledps.setInt(31, 0); 
			ledps.setInt(32, 0); 
			ledps.setInt(33, 0); 


			i=ledps.executeUpdate();

			////generate record for outstanding /// record
			
			Calendar cal = Calendar.getInstance();
		    cal.setTime(rcp.getVou_date());
		    int year = cal.get(Calendar.YEAR);
		    int month = cal.get(Calendar.MONTH)+1;
//		    int day = cal.get(Calendar.DAY_OF_MONTH);
		    char wlo='A';
		    if (rcp.getVouno()==2)
		    	wlo='T';
		    if (rcp.getVouno()==3)
		    	wlo='G';
		    
		    int mon=Integer.parseInt(year+String.format("%02d", month));
		    System.out.println("value of month " +mon);
			ledps.setInt(1, rcp.getVouno()); // sales div code
			ledps.setInt(2, cmpdepo);
			ledps.setInt(3, 81);
			ledps.setInt(4, 81);
			ledps.setString(5, ""+rcp.getVou_lo().charAt(0)+wlo);
			ledps.setInt(6, rcp.getVou_no());
			ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setInt(8,rcp.getVgrp_code()); // vgrp_Code
			ledps.setString(9, rcp.getVac_code());
			ledps.setString(10, rcp.getExp_code());
			ledps.setString(11, rcp.getChq_no()+ " "+ sdf.format(rcp.getChq_date()));
			ledps.setString(12, "");

			ledps.setString(13, rcp.getParty_name());
			ledps.setDouble(14, rcp.getVamount());
			ledps.setString(15, "DR");
			ledps.setString(16, rcp.getChq_no());
			if (rcp.getChq_date()!=null)
				ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(17, null);
				
			ledps.setDouble(18, rcp.getChq_amt());
			ledps.setString(19, String.valueOf(rcp.getTp()));
			ledps.setInt(20, rcp.getCreated_by());
			ledps.setDate(21, new java.sql.Date(rcp.getCreated_date().getTime()));
			ledps.setInt(22, rcp.getSerialno());
			ledps.setInt(23, rcp.getFin_year());
			ledps.setInt(24, mon);
			ledps.setInt(25, rcp.getVouno()); // div code of mf/tf/div
			ledps.setInt(26, rcp.getVou_yr()); 
			ledps.setInt(27, rcp.getVter_cd()); 
			ledps.setDouble(28, rcp.getBank_chg());
			ledps.setInt(29, rcp.getVstat_cd()); 
			ledps.setInt(30, rcp.getVarea_cd()); 
			ledps.setInt(31, rcp.getVreg_cd()); 
			ledps.setInt(32, rcp.getVmsr_cd()); 
			ledps.setInt(33, rcp.getMkt_year()); 


			i=ledps.executeUpdate();

			
			
			rcpps=con.prepareStatement(rp);
			rcpps.setInt(1, rcp.getDiv_code());
			rcpps.setInt(2, rcp.getVdepo_code());
			rcpps.setInt(3, rcp.getVbook_cd());
			rcpps.setInt(4, rcp.getVou_yr());
			rcpps.setString(5, rcp.getVou_lo());
			rcpps.setInt(6, rcp.getVou_no());
			rcpps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
			rcpps.setInt(8,rcp.getVgrp_code()); // vgrp_Code
			rcpps.setString(9, rcp.getVac_code());
			rcpps.setString(10, rcp.getVnart1());
			rcpps.setDouble(11, rcp.getVamount());
			rcpps.setString(12, "DR");
			rcpps.setString(13, rcp.getChq_no());
			if (rcp.getChq_date()!=null)
				rcpps.setDate(14, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				rcpps.setDate(14, null);
	
			rcpps.setDouble(15, rcp.getChq_amt());
			rcpps.setInt(16, rcp.getCreated_by());
			rcpps.setDate(17, new java.sql.Date(rcp.getCreated_date().getTime()));
			rcpps.setInt(18, rcp.getSerialno());
			rcpps.setInt(19, rcp.getFin_year());
			rcpps.setInt(20, rcp.getMnth_code());


			i=rcpps.executeUpdate();

			
			
			i=rcp.getVou_no();
			
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
			System.out.println("-------------Exception in SQLRcpDAO.addLed3 Bank Payment Voucher" + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(rcpps != null){rcpps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close Bank Payment Voucher "+e);
			}
		}

		return (i);
	}				
	

////method to update record in led file (Bank Payment voucher Debtors)	
	public int updateLed4(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



			String led="update  ledfile set vou_date=?,vgrp_code=?,exp_code=?," +
					"vnart_1=?,name=?,vamount=?,chq_no=?,chq_date=?,chq_amt=?,modified_by=?,modified_date=?,vnart_2=?,bank_chg=? " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR' and sub_div=? ";


			ledps=con.prepareStatement(led);

			ledps.setDate(1, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setInt(2,rcp.getVgrp_code()); // vgrp_Code
			ledps.setString(3, rcp.getExp_code());
			ledps.setString(4, rcp.getVnart1());
			ledps.setString(5, rcp.getParty_name());
			ledps.setDouble(6, (rcp.getVamount()));
			ledps.setString(7, rcp.getChq_no());
			if(rcp.getChq_date()!=null)
				ledps.setDate(8, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(8, null);
			ledps.setDouble(9, rcp.getChq_amt());
			ledps.setInt(10, rcp.getModified_by());
			if(rcp.getModified_date()!=null)
				ledps.setDate(11, new java.sql.Date(rcp.getModified_date().getTime()));
			else
				ledps.setDate(11, null);
				
			ledps.setString(12, rcp.getVnart2());
			ledps.setDouble(13, rcp.getBank_chg());

			// where clause
			ledps.setInt(14, rcp.getFin_year());
			ledps.setInt(15, rcp.getDiv_code());
			ledps.setInt(16, rcp.getVdepo_code());
			ledps.setInt(17, rcp.getVbook_cd1());
			ledps.setInt(18, rcp.getVou_no());
			ledps.setInt(19, rcp.getVouno()); // sub div


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
			System.out.println("-------------Exception in SQLRcpDAO.updateLed1 Bank Receipt Voucher " + ex);
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


	
	//// method to get bank vou no for account package 
	public int getDebtorsVouNo(int depo,int div,int year,int bkcd,String dbcr)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		PreparedStatement ps2 = null;
		ResultSet rst2=null;
		Connection con=null;
		int i=0;
		int ii=0;
		try {
			con=ConnectionFactory.getConnection();
//			String query1 ="select IFNULL(max(vou_no),0) from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd1=? and vdbcr=? and  ifnull(del_tag,'')<>'D' "; 
			
			String query2 ="select IFNULL(max(vou_no),0) from ledfile where fin_year=? and  div_code=4 and sub_div=? and vdepo_code=? and vbook_cd1=? and vdbcr=? and  ifnull(del_tag,'')<>'D' ";
			
			con.setAutoCommit(false);

			ps2 =con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			ps2.setInt(4, bkcd);
			ps2.setString(5, dbcr);

			rst2 =ps2.executeQuery();

			if(rst2.next())
			{
				ii =rst2.getInt(1); 
			}
			rst2.close();

			
			
			
/*			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setInt(4, bkcd);
			ps1.setString(5, dbcr);

			rst =ps1.executeQuery();

			if(rst.next())
			{
				i =rst.getInt(1); 
			}
*/

			
			if (ii>i)
				i=ii;
			con.commit();
			con.setAutoCommit(true);
//			rst.close();
//			ps1.close();
			ps2.close();

		} catch (SQLException ex) {System.out.println("error hai "+ex);
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in BankVouDAO.getVouNo " + ex);
		i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

//				if(ps1 != null){ps1.close();}
//				if(rst != null){rst.close();}
				if(ps2 != null){ps2.close();}
				if(rst2 != null){rst2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 



	////////////////bank Paymentt Voucher (get vou detail method for (Debtors)

	public RcpDto getVouDetailDrNo(int vou,int depo_code,int div,int fyear,int bkcd,int subdiv)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		String code=null;
		try {


			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.vac_code,f.name," +
					" f.vnart_1,f.vamount,f.chq_no,f.chq_date,f.sub_div,ifnull(f.bank_chg,0) from ledfile as f "+
					" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
					" f.vbook_cd1=? and f.vou_no=? and f.vdbcr='DR' and f.sub_div=? and f.vbook_cd=f.vbook_cd1 and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,vou); 
			ps2.setInt(6,subdiv); 

			rs= ps2.executeQuery();

			if (rs.next())
			{
				rcp= new RcpDto();
				rcp.setVou_no(rs.getInt(2));
				rcp.setVou_date(rs.getDate(3));
				rcp.setVac_code(rs.getString(4));
				rcp.setParty_name(rs.getString(5));
				rcp.setVnart1(rs.getString(6));
				rcp.setChq_no(rs.getString(8));
				rcp.setChq_date(rs.getDate(9));
				rcp.setChq_amt(rs.getDouble(7));
				rcp.setVouno(rs.getInt(10));
				rcp.setBank_chg(rs.getDouble(11));

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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail2 Bank Receipt Voucher " + ex);

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

		return rcp;
	}			



	////////////////bank Payment Voucher (Parameter Date get vou detail method for (Debtors)

	public Vector getVouListDrDate(Date vdate,Date edate,int depo_code,int div,int bkcd)
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
					"f.vnart_1,f.vamount,f.chq_no,f.chq_date,f.sub_div from ledfile as f "+
					"where  f.div_code=? and f.vdepo_code=? and "+ 
					"f.vbook_cd1=? and f.vou_date between ? and ?  and f.vdbcr='DR' and f.vac_code<>? and f.vbook_cd=f.vbook_Cd1 and  ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,bkcd); 
			ps2.setDate(4,new java.sql.Date(vdate.getTime()));
			ps2.setDate(5,new java.sql.Date(edate.getTime())); 
			ps2.setString(6,code); 

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
				rcp.setChq_no(rs.getString(8));
				rcp.setChq_date(rs.getDate(9));
				rcp.setChq_amt(rs.getDouble(7));
				rcp.setVouno(rs.getInt(10));
				
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail2 Bank Receipt Voucher " + ex);

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
	

/// bank receipt voucher print for baddi only
	public List getMultipleVouDetailBaddi(int startVoucher,int endVoucher,int depo_code,int div,int fyear,int doctp,String tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		List voucherList=null;
		String query2=null; 
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
				
			query2 ="select concat(f.vou_lo,'A'),f.name,f.vou_no,f.vou_date,f.vac_code,p.mac_name,ifnull(f.sub_code,'0')," +
			"ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,ifnull(f.chq_no,''),f.chq_date,f.vamount,f.bank_chg,f.chq_amt,ifnull(f.bill_no,''),f.bill_date from ledfile as f "+
			",partyfst as p "+
			"where f.vac_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=?  and f.vou_no between ? and ?  and f.vdbcr='CR' and   ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' order by f.vou_no,f.serialno";  

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4, doctp);
//			ps2.setString(5, "C"); tp=?
			ps2.setInt(5,startVoucher); 
			ps2.setInt(6,endVoucher);
			ps2.setInt(7,div);
			ps2.setInt(8, depo_code);
			
			
			rs= ps2.executeQuery();


			
			voucherList = new ArrayList(); 
			int wno = 0;
			boolean first=true;
			double rgross = 0.00;
			
			while (rs.next())
			{
				 System.out.println("vou lo "+rs.getString(1));

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
				if (rs.getString(2)==null)
					rcp.setParty_name(rs.getString(6));
					
				rcp.setVou_lo(rs.getString(1));
				if ((depo_code==11 || depo_code==3 ) && doctp==21)
					rcp.setVou_lo(rs.getString(1)+BaseClass.loginDt.getInv_yr());

				if ((depo_code==11 || depo_code==3 ) && doctp==21)
					rcp.setVou_lo(rs.getString(1)+9);

				
				if (depo_code==12 && doctp==21)
					rcp.setVou_lo(rs.getString(1)+"5");

				if (depo_code==12 && doctp==22)
					rcp.setVou_lo(rs.getString(1)+"8");
				
				rcp.setExp_code(rs.getString(5));
				rcp.setExp_name(rs.getString(6));
				rcp.setSub_code(rs.getString(7));
				rcp.setVnart1(rs.getString(9));
				rcp.setVnart2(rs.getString(8));
				if(!rs.getString(7).equals("0"))
				{
					if(depo_code==11)
						rcp.setVnart2("PEA"+rs.getString(7));
					else
						rcp.setVnart2(rs.getString(7));
						
				}	
				rcp.setVamount(rs.getDouble(10));
				rcp.setDash(0);
				rcp.setChq_no(rs.getString(14));
				rcp.setChq_date(rs.getDate(15));
				rcp.setChq_amt(rs.getDouble(18));
				rcp.setBank_chg(rs.getDouble(17));
				rcp.setBill_no(rs.getString(19));
				rcp.setBill_date(rs.getDate(20));
				
				
				rgross+=rs.getDouble(10);
				
				voucherList.add(rcp);
			}

			
			if(!first)
			{
				rcp= new RcpDto();
				rcp.setDash(2);
				rcp.setVamount(rgross);
				voucherList.add(rcp);
			}
            
            
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (SQLException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getMultiVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return voucherList;
	}	


	
	
	////////////////GET DETAIL FOR HO VOUHER ENTRY 11-FEB-2014

	public RcpDto getHOVouDetail(String vou,int depo_code,int div,int fyear,int bkcd,String tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int maxSerialNo=0;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"f.name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno," +
			"f.chq_no,if(f.chq_date='0000-00-00',null,f.chq_date),f.chq_amt,f.bill_no,f.bill_date from ledfile as f "+
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=? and f.sub_code=? and f.vdbcr='DR' and f.tp=? and f.sub_div=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? order by f.vou_no,serialno ";  


			String maxSrial = "select ifnull(max(serialno),0) from ledfile where fin_year=? and div_code=? and vdepo_code=? and "+ 
			" vbook_cd1=? and sub_code=? and vdbcr='DR' and tp=?  ";

			ps2 = con.prepareStatement(maxSrial);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setString(5,vou); 
			ps2.setString(6,tp); 
			
			rs= ps2.executeQuery();
			if(rs.next())
			{
				maxSerialNo = rs.getInt(1);
			}
			rs.close();
			ps2.close();
			rs=null;
			ps2=null;
			

			/////////////////////////////////
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setString(5,vou); 
			ps2.setString(6,tp); 
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
					rcp.setSub_code(rs.getString(7));
					rcp.setVou_date(rs.getDate(4));
					rcp.setParty_name(rs.getString(2));
					rcp.setChq_no(rs.getString(15));
					rcp.setChq_date(rs.getDate(16));
					rcp.setChq_amt(rs.getDouble(17));
					rcp.setVnart2(rs.getString(9));
					rcp.setSerialno(maxSerialNo);

				}






				/// Table detail set here
				colum = new Vector();
				colum.addElement(false);   // del tag
				colum.addElement(rs.getString(5));   // exp code
				colum.addElement(rs.getString(6));  //exp  name
//				colum.addElement(rs.getString(8));  //sub_head
				colum.addElement("");  //sub_head
				colum.addElement(rs.getString(18));  //bill_no
				if (rs.getDate(19)!=null)
				{
				   try
					{
					  colum.addElement(sdf.format(rs.getDate(19)));  //bill_date
					}catch(Exception e)
					{
						colum.addElement("");  //bill_date
					}
				}
				else
				{
					colum.addElement("");  //bill_date
				}

				colum.addElement(rs.getString(10)); // narration
				colum.addElement(nf.format(rs.getDouble(11)));  //vamount
				colum.addElement(rs.getInt(12));  //vbook_cd
				colum.addElement(rs.getInt(13));  // vgrpcode
				colum.addElement(rs.getString(7));  // subcode 
				colum.addElement(rs.getInt(14));  // serialno
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

		} catch (SQLException ex) { ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getHOVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}			

	
	
	//// method to delete put 'D' in del_tag in led file (HO Voucher Entry)	
	public int deleteHOVoucher(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;


		RcpDto rcp=null;

		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String led="update ledfile set del_tag=?,modified_by=?,modified_date=?  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and sub_code=? and vdbcr='DR' and serialno=?";


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
				ledps.setString(8, rcp.getSub_code());
				ledps.setInt(9, rcp.getSerialno());


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
			System.out.println("-------------Exception in SQLRcpDAO.deleteHOVoucher " + ex);
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
	
	//// method to get Current Bank Balance
	public double getBankBalance(int depo,int div,int year,int vbook_cd,int code,Date voudt)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		String bankcharges="700"+String.format("%02d",depo)+"23";
		
		double bal=0;
		try {
			con=ConnectionFactory.getConnection();

			con.setAutoCommit(false);

			String query1 ="select mopng_bal,ifnull(mopng_db,'CR') from partyacc where fin_year=? and  div_code=? and depo_code=? and mac_code like ? and  ifnull(del_tag,'')<>'D' "; 
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setString(4, "%"+code);
			rst =ps1.executeQuery();

			if(rst.next())
			{
				bal =rst.getDouble(1) ; 
				if (rst.getString(2).equals("DR"))
					bal =bal*-1 ; 
			}
			rst.close();
			ps1=null;
			
			query1 ="select sum(bal) from (select sum(if(vdbcr='CR',vamount,(vamount*-1))) bal from ledfile " +
			"where fin_year=? and div_Code=? and vdepo_code=? "+  
			"and vbook_cd1=? and vou_date <= ? AND VAC_CODE<>?  and ifnull(del_tag,'')<>'D' " +
			"union all "+
			" select sum(if(vdbcr='CR',vamount,(vamount*-1))) bal from ledfile " +
			" where fin_year=? and div_Code=? and vdepo_code=? "+  
			" and vbook_cd1=? and vou_date <= ? AND VAC_CODE=? and tp='D'  and ifnull(del_tag,'')<>'D' ) a " ; 
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setInt(4, vbook_cd);
			ps1.setDate(5, new java.sql.Date(new Date().getTime()));
			ps1.setString(6, bankcharges);
			ps1.setInt(7, year);
			ps1.setInt(8, div);
			ps1.setInt(9, depo);
			ps1.setInt(10, vbook_cd);
			ps1.setDate(11, new java.sql.Date(new Date().getTime()));
			ps1.setString(12, bankcharges);

			
			rst =ps1.executeQuery();

			if(rst.next()){
				bal+=rst.getDouble(1); 
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
	
	//// method to add record in led file (bank voucher ho/mandideep)	
	public int addLed1HO(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;
		PreparedStatement outps=null;
		PreparedStatement chqps=null;
		RcpDto rcp=null;
		ChequeDto chque=null;
		String bankcharges="";

		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,vnart_1,name,vamount,vdbcr,chq_no,chq_date,chq_amt,bank_chg,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,vbk_code,sub_div,vnart_2,tds_amt,exp_code,tran_type,indicator,vter_cd,tax_type) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			

			
			String rp="insert into rcpfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
					"vou_date,head_code,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date,chq_amt,bill_no,bill_date,bill_amt,vouno,voudt,tds_amt, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,inv_lo,tran_type,cr_no) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			String out="update rcpfile set chq_amt=chq_amt+?,modified_by=?,modified_date=? " +
					" where div_code=? and vdepo_code=? and vbook_cd in (60,61) and vou_no=? and vou_date=?";

			String chq="update chqmast set vou_no=?,vou_Date=?,party_code=?,chq_amt=?,chq_date=?,favourof=?," +
					"invno=?,modified_by=?,modified_date=?,issued=? " +
					" where div_code=? and depo_code=? and bank_code=? and cheque_no=? and dbcr='DR' ";

			

			// 040+depo+01

			ledps=con.prepareStatement(led);
			rcpps=con.prepareStatement(rp);
			outps=con.prepareStatement(out);
			chqps=con.prepareStatement(chq);

			for(int j=0;j<cList.size();j++)
			{
				rcp = (RcpDto) cList.get(j);
				bankcharges="700"+String.format("%02d",rcp.getVdepo_code())+"23";
				System.out.println("inv lo is "+rcp.getInv_lo());
				rcpps.setInt(1, rcp.getDiv_code());
				rcpps.setInt(2, rcp.getVdepo_code());
				rcpps.setInt(3, rcp.getVbook_cd1());
				rcpps.setInt(4, 0);
				rcpps.setString(5, rcp.getVou_lo());
				rcpps.setInt(6, rcp.getVou_no());
				rcpps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
				rcpps.setString(8,""); // vgrp_Code
				rcpps.setString(9, rcp.getVac_code());
				rcpps.setString(10, rcp.getVnart2());
				rcpps.setDouble(11, rcp.getVamount());
				rcpps.setString(12, "DR");
				rcpps.setString(13, rcp.getChq_no());
				if (rcp.getChq_date()!=null)
				rcpps.setDate(14, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					rcpps.setDate(14, null);
					
				rcpps.setDouble(15, rcp.getChq_amt());
				rcpps.setString(16, rcp.getBill_no());

				if(rcp.getBill_date()!=null)
					rcpps.setDate(17, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					rcpps.setDate(17,null);

				rcpps.setDouble(18, rcp.getBill_amt());
				rcpps.setInt(19, rcp.getVouno());

				if(rcp.getVoudt()!=null)
					rcpps.setDate(20, new java.sql.Date(rcp.getVoudt().getTime()));
				else
					rcpps.setDate(20, null);
				rcpps.setDouble(21, 0);
				if (rcp.getBill_amt()!=0)
					rcpps.setDouble(21, rcp.getTds_amt());
				rcpps.setInt(22, rcp.getCreated_by());
				rcpps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
				rcpps.setInt(24, rcp.getSerialno());
				rcpps.setInt(25, rcp.getFin_year());
				rcpps.setInt(26, rcp.getMnth_code());
				rcpps.setString(27, rcp.getInv_lo());
				rcpps.setInt(28, 0);
				if (rcp.getVdepo_code()==8)
					rcpps.setInt(28, 99);  // tran_type
				rcpps.setString(29, rcp.getCn_no());
				
				i=rcpps.executeUpdate();

				System.out.println("RCPPS I "+i);
				/// update outstanding file 
				outps.setDouble(1, rcp.getVamount());
				outps.setInt(2, rcp.getCreated_by());
				outps.setDate(3, new java.sql.Date(rcp.getCreated_date().getTime()));
				// where clause
				outps.setInt(4, rcp.getDiv_code());
				outps.setInt(5, rcp.getVdepo_code());
				//outps.setInt(6, rcp.getVbook_cd1());
				outps.setInt(6, rcp.getVouno());

				if(rcp.getVoudt()!=null)
					outps.setDate(7, new java.sql.Date(rcp.getVoudt().getTime()));
				else
					outps.setDate(7, null);


				i=outps.executeUpdate();

				System.out.println("outps I "+i);
				
				i=rcp.getVou_no();

			} // end of loop

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
				if (rcp.getChq_date()!=null)
					ledps.setDate(15, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(15, null);
					
				ledps.setDouble(16, rcp.getChq_amt());
				ledps.setDouble(17, rcp.getBank_chg());
				ledps.setString(18, String.valueOf(rcp.getTp()));
				ledps.setInt(19, rcp.getCreated_by());
				ledps.setDate(20, new java.sql.Date(rcp.getCreated_date().getTime()));
//				ledps.setInt(21, rcp.getSerialno());
				ledps.setInt(21, 1);
				ledps.setInt(22, rcp.getFin_year());
				ledps.setInt(23, rcp.getMnth_code());
				ledps.setInt(24, rcp.getVbk_code());
				ledps.setInt(25, 1); // sub diviiosn
				ledps.setString(26, rcp.getVnart2());
				ledps.setDouble(27, rcp.getInterest());  // tds @ time of payment
				ledps.setString(28, rcp.getVac_code());
				ledps.setInt(29, 0);
				if (rcp.getVdepo_code()==8)
					ledps.setInt(29, 99);  // tran_type

				
				ledps.setString(30, ""); // indicator (Month No)
				if(rcp.getVdepo_code()==8)
					ledps.setString(30, rcp.getInt_code()); // indicator (Month No)
				ledps.setInt(31, 0); // branch code
				if(rcp.getVdepo_code()==8)
					ledps.setInt(31, rcp.getVter_cd()); // branch code

				ledps.setString(32, rcp.getTaxtype()); 	// chqtype other/HO
				i=ledps.executeUpdate();

				System.out.println("ledps I "+i);
				// for bank charges
				
				if (rcp.getBank_chg()>0)
				{
					ledps.setInt(1, rcp.getDiv_code());
					ledps.setInt(2, rcp.getVdepo_code());
					ledps.setInt(3, 25);
					ledps.setInt(4, rcp.getVbook_cd1());
					ledps.setString(5, rcp.getVou_lo());
					ledps.setInt(6, rcp.getVou_no());
					ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					ledps.setInt(8,70011); // vgrp_Code
//					ledps.setString(9, "7001123");
					ledps.setString(9, bankcharges);
					ledps.setString(10, "BANK CHARGES");
					ledps.setString(11, rcp.getParty_name());
					ledps.setDouble(12, rcp.getBank_chg());
					ledps.setString(13, "DR");
					ledps.setString(14, rcp.getChq_no());
					if (rcp.getChq_date()!=null)
						ledps.setDate(15, new java.sql.Date(rcp.getChq_date().getTime()));
					else
						ledps.setDate(15, null);
						
					ledps.setDouble(16, rcp.getChq_amt());
					ledps.setDouble(17, 0.00);
					ledps.setString(18, String.valueOf(rcp.getTp()));
					ledps.setInt(19, rcp.getCreated_by());
					ledps.setDate(20, new java.sql.Date(rcp.getCreated_date().getTime()));
					ledps.setInt(21, 2);  // serial no
					ledps.setInt(22, rcp.getFin_year());
					ledps.setInt(23, rcp.getMnth_code());
					ledps.setInt(24, rcp.getVbk_code());
					ledps.setInt(25, 1); // sub diviiosn
					ledps.setString(26, rcp.getVnart2());
					ledps.setDouble(27, 0.00); // tds
					ledps.setString(28, bankcharges);
					ledps.setInt(29, 0);
					if (rcp.getVdepo_code()==8)
						ledps.setInt(29, 99);  // tran_type
					ledps.setString(30, ""); // indicator (Month No)
					if(rcp.getVdepo_code()==8)
						ledps.setString(30, rcp.getInt_code()); // indicator (Month No)

					ledps.setInt(31, 0); // branch code
					if(rcp.getVdepo_code()==8)
						ledps.setInt(31, rcp.getVter_cd()); // branch code

					ledps.setString(32, rcp.getTaxtype()); 	// chqtype other/HO

					
					i=ledps.executeUpdate();
					System.out.println("ledps iiI "+i);
				}
				
				if(rcp.getVdepo_code()!=8)
				{
					int size=rcp.getChqlist().size();
					for(int j=0;j<size;j++)
					{

						chque= (ChequeDto)rcp.getChqlist().get(j);
						chqps.setInt(1,chque.getVou_no());
						chqps.setDate(2,new java.sql.Date(chque.getVou_date().getTime()));
						chqps.setString(3, chque.getParty_code());
						chqps.setDouble(4,chque.getChq_amt());
						chqps.setDate(5,new java.sql.Date(chque.getChq_date().getTime()));
						chqps.setString(6, chque.getFavourof());
						chqps.setString(7, chque.getInvno());
						chqps.setInt(8, chque.getModified_by());
						chqps.setDate(9, new java.sql.Date(chque.getModified_date().getTime()));
						chqps.setString(10, "Y");
						chqps.setInt(11, chque.getDiv_code());
						chqps.setInt(12, chque.getDepo_code());
						chqps.setInt(13, chque.getBank_code());
						chqps.setInt(14, chque.getCheque_no());
						i=chqps.executeUpdate();
						System.out.println("chqps I "+i);

					}				
				}
				i=rcp.getVou_no();
				
			}

			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			rcpps.close();
			outps.close();
			chqps.close();

		} catch (SQLException ex) {
			 ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.addLed1HO -HOBankPaymentCR " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(rcpps != null){rcpps.close();}
				if(outps != null){outps.close();}
				if(chqps != null){chqps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close HOBankPaymentCR "+e);
			}
		}

		return (i);
	}			

	
	//// method to add record in led file (bank voucher [Direct Payment])	
	public int addLedHO(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement chqps=null;
		RcpDto rcp=null;
		ChequeDto chque=null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,exp_code,sub_code,vnart_1,name,vamount,vdbcr,chq_no,chq_date,chq_amt,bill_no,bill_date,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,vnart_2,sub_div," +
					" taxable_amt,ctax_per, stax_per, itax_per, "+
					" cgst_amt, sgst_amt, igst_amt,net_amt,addl_taxper,addl_taxamt,vreg_cd,varea_cd,vmsr_cd,gst_no,hsn_code )"+						
					" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			String chq="update chqmast set vou_no=?,vou_Date=?,party_code=?,chq_amt=?,chq_date=?,favourof=?," +
					"invno=?,modified_by=?,modified_date=?,issued=? " +
					" where  div_code=? and depo_code=? and bank_code=? and cheque_no=? and dbcr='DR' ";


			
			ledps=con.prepareStatement(led);
			chqps=con.prepareStatement(chq);

			for(int j=0;j<cList.size();j++)
			{
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

				ledps.setString(13, rcp.getParty_name());
				ledps.setDouble(14, rcp.getVamount());
				ledps.setString(15, "DR");
				ledps.setString(16, rcp.getChq_no());

				if (rcp.getChq_date()!=null)
					ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(17, null);

				ledps.setDouble(18, rcp.getChq_amt());
				ledps.setString(19, rcp.getBill_no());
				if (rcp.getBill_date()!=null)
					ledps.setDate(20, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(20, null);

				ledps.setString(21, String.valueOf(rcp.getTp()));
				ledps.setInt(22, rcp.getCreated_by());
				ledps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(24, rcp.getSerialno());
				ledps.setInt(25, rcp.getFin_year());
				ledps.setInt(26, rcp.getMnth_code());
				ledps.setString(27, rcp.getVnart2());
				ledps.setInt(28, 1); // sub div
				ledps.setDouble(29, rcp.getCr_amt());  // taxable   
				ledps.setDouble(30, rcp.getCtaxper());   
				ledps.setDouble(31, rcp.getStaxper());   
				ledps.setDouble(32, rcp.getItaxper());   
				ledps.setDouble(33, rcp.getCgstamt());   
				ledps.setDouble(34, rcp.getSgstamt());   
				ledps.setDouble(35, rcp.getIgstamt());   
				ledps.setDouble(36, rcp.getNetamt());   
				ledps.setDouble(37, rcp.getCess_per());  // cess per   
				ledps.setDouble(38, rcp.getCess_amt());  // cess amt   
				ledps.setInt(39, rcp.getVreg_cd()); // rcm y/n y=1   
				ledps.setInt(40, rcp.getVarea_cd()); // itc y/n/ y=1   
				ledps.setInt(41, rcp.getVmsr_cd()); // state code   
				ledps.setString(42, ""); // gst no
				ledps.setLong(43, 0); // hsn code


				i=ledps.executeUpdate();
				
				int size=rcp.getChqlist().size();
				for(int k=0;k<size;k++)
				{
					chque= (ChequeDto)rcp.getChqlist().get(k);
					chqps.setInt(1,chque.getVou_no());
					chqps.setDate(2,new java.sql.Date(chque.getVou_date().getTime()));
					chqps.setString(3, chque.getParty_code());
					chqps.setDouble(4,chque.getChq_amt());
					chqps.setDate(5,new java.sql.Date(chque.getChq_date().getTime()));
					chqps.setString(6, chque.getFavourof());
					chqps.setString(7, chque.getInvno());
					chqps.setInt(8, chque.getModified_by());
					chqps.setDate(9, new java.sql.Date(chque.getModified_date().getTime()));
					chqps.setString(10, "Y");
					chqps.setInt(11, chque.getDiv_code());
					chqps.setInt(12, chque.getDepo_code());
					chqps.setInt(13, chque.getBank_code());
					chqps.setInt(14, chque.getCheque_no());
					i=chqps.executeUpdate();
				}				

			}

			//voucher no 
			i=rcp.getVou_no();
			System.out.println("voucher no "+i);
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			chqps.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.addLedHO " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(chqps != null){chqps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	
	//// method to update record in led file (Bank voucher Direct)	
	public int deleteLed1HO(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;
		PreparedStatement outps=null;
		PreparedStatement chqps=null;


		RcpDto rcp=null;

		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String led="update ledfile set del_tag=?,modified_by=?,modified_date=?  " +
					"where div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=?  and vou_date=? and vdbcr='DR' " ;

			String rp="update rcpfile set del_tag=?,modified_by=?,modified_date=?  " +
					"where div_code=? and vdepo_code=? and vbook_cd=? " +
					"and vou_no=? and vou_date=?";

			String out="update rcpfile set chq_amt=chq_amt-?,modified_by=?,modified_date=?  " +
					"where div_code=? and vdepo_code=? and vbook_cd in (60,61) " +
					"and vou_no=? and vou_date=? and ifnull(del_tag,'')<>'D' ";

			String chq="update chqmast set issued='N' ,print='N', modified_by=?,modified_date=?," +
					"vou_no=0, vou_date=null,party_code=null,chq_amt=0.00,chq_date=null,favourof=null," +
					"invno=null,reconcil='N',rcon_date=null,bank_amt=0.00,diff_amt=0.00  " +
					"where div_code=? and depo_code=? and bank_code=? " +
					"and vou_no=? and vou_date=?  ";

			
			
			ledps=con.prepareStatement(led);
			rcpps=con.prepareStatement(rp);
			outps=con.prepareStatement(out);
			chqps=con.prepareStatement(chq);

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
				System.out.println("vaue of i "+i);

				// outstanding file record updated 
				outps.setDouble(1,rcp.getVamount() );
				outps.setInt(2, rcp.getModified_by());
				outps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));

				// where clause
				outps.setInt(4, rcp.getDiv_code());
				outps.setInt(5, rcp.getVdepo_code());
				outps.setInt(6, rcp.getVouno());
				if (rcp.getVoudt()!=null)
					outps.setDate(7, new java.sql.Date(rcp.getVoudt().getTime()));
				else
					outps.setDate(7, null);
					
				i=outps.executeUpdate();
			}


			ledps.setString(1, "D");
			ledps.setInt(2, rcp.getModified_by());
			ledps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));


			// where clause
			ledps.setInt(4, rcp.getDiv_code());
			ledps.setInt(5, rcp.getVdepo_code());
			ledps.setInt(6, rcp.getVbook_cd1());
			ledps.setInt(7, rcp.getVou_no());
			ledps.setDate(8, new java.sql.Date(rcp.getVou_date().getTime()));

			i=ledps.executeUpdate();

//////// cheque master update
			chqps.setInt(1, rcp.getModified_by());
			chqps.setDate(2, new java.sql.Date(rcp.getModified_date().getTime()));


			// where clause
			chqps.setInt(3, rcp.getDiv_code());
			chqps.setInt(4, rcp.getVdepo_code());
			chqps.setInt(5, rcp.getVbook_cd1());
			chqps.setInt(6, rcp.getVou_no());
			chqps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));

			i=chqps.executeUpdate();


			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			rcpps.close();
			outps.close();
			chqps.close();

		} catch (SQLException ex) {ex.printStackTrace();
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
				if(chqps != null){chqps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}				


	//// method to update record in led file (Bank voucher Direct)	
	public int deleteLedHODirect(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement chqps=null;


		RcpDto rcp=null;

		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String led="update ledfile set del_tag=?,modified_by=?,modified_date=?  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR' and serialno=?";


			String chq="update chqmast set issued='N' ,print='N', modified_by=?,modified_date=?," +
					"vou_no=0, vou_date=null,party_code=null,chq_amt=0.00,chq_date=null,favourof=null," +
					"invno=null,reconcil='N',rcon_date=null,bank_amt=0.00,diff_amt=0.00  " +
					"where div_code=? and depo_code=? and bank_code=? " +
					"and vou_no=? and vou_date=?";

			

			ledps=con.prepareStatement(led);
			chqps=con.prepareStatement(chq);

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

	//////// cheque master update
				chqps.setInt(1, rcp.getModified_by());
				chqps.setDate(2, new java.sql.Date(rcp.getModified_date().getTime()));


				// where clause
				chqps.setInt(3, rcp.getDiv_code());
				chqps.setInt(4, rcp.getVdepo_code());
				chqps.setInt(5, rcp.getVbook_cd1());
				chqps.setInt(6, rcp.getVou_no());
				chqps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));

				i=chqps.executeUpdate();





			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			chqps.close();

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
				if(chqps != null){chqps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	

	//// method to add record in led file (bank Receipt voucher [Direct/Creditors])	
	public int addDebitNote(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement invps=null;
		int depo=0;
		boolean add=true;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,exp_code,vnart_1,vnart_2,name,vamount,vdbcr,bill_no,bill_date,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,sub_div,vou_yr,taxable_amt,ctax_per,stax_per,itax_per,cgst_amt,sgst_amt,igst_amt,net_amt) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			String inv ="insert into invfst " +
			"(Div_code,Depo_code, Doc_type, Party_code, Inv_no, Inv_lo, Inv_yr, Inv_date, " +
			" prod_type,taxable1,ltax1_per,ltax1_amt,bill_amt,remark,aproval_no,fin_year,mnth_code," +
			" inv_type,prt_type,gross_Amt) " +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			invps=con.prepareStatement(inv);
			depo=BaseClass.loginDt.getCmp_code();
			
			if(depo==11 || depo==15 || depo==12 || depo==18 || depo==3)
				add=false;
			
			if(add)
			{
				invps.setInt(1, 1);
				invps.setInt(2, depo);
				if(rcp.getVbook_cd()==95)
					invps.setInt(3, 51);
				else
					invps.setInt(3, 41);
				invps.setString(4, rcp.getVac_code());
				invps.setInt(5, rcp.getVou_no());
				invps.setString(6, rcp.getVou_lo());
				invps.setInt(7, rcp.getVou_yr());
				invps.setDate(8, new java.sql.Date(rcp.getVou_date().getTime()));
				invps.setString(9, "N");
				invps.setDouble(10, rcp.getTaxable());
				invps.setDouble(11, rcp.getCtaxper());
				invps.setDouble(12, rcp.getCgstamt());
				invps.setDouble(13, rcp.getVamount());
				invps.setString(14, rcp.getVnart1());
				invps.setString(15, rcp.getParty_name());  // paid to
				invps.setInt(16, rcp.getFin_year());
				invps.setInt(17, rcp.getMnth_code());
				if(rcp.getVbook_cd()==95)
					invps.setString(18, "D");
				else
					invps.setString(18, "C");
					
				invps.setString(19, "2");
				invps.setDouble(20, rcp.getVamount());
				i=invps.executeUpdate();
			}

			
			
			
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
			ledps.setString(10, rcp.getVac_code());
			ledps.setString(11, rcp.getVnart1());
			ledps.setString(12, rcp.getVnart2());

			ledps.setString(13, rcp.getParty_name());  // paid to
			ledps.setDouble(14, rcp.getVamount());
			if(rcp.getVbook_cd()==95)
				ledps.setString(15, "DR");
			else
				ledps.setString(15, "CR");
				
			ledps.setString(16, rcp.getChq_no());
			if(rcp.getChq_date()!=null)
				ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(17, null);
			if(rcp.getVbook_cd()==95)
				ledps.setString(18, "D");
			else
				ledps.setString(18, "C");
				
			ledps.setInt(19, rcp.getCreated_by());
			ledps.setDate(20, new java.sql.Date(rcp.getCreated_date().getTime()));
			ledps.setInt(21, rcp.getSerialno());
			ledps.setInt(22, rcp.getFin_year());
			ledps.setInt(23, rcp.getMnth_code());
			ledps.setInt(24, 1); // div code of mf/tf/div
			ledps.setInt(25, rcp.getVou_yr()); 
			ledps.setDouble(26, rcp.getTaxable());
			ledps.setDouble(27, rcp.getCtaxper());
			ledps.setDouble(28, rcp.getStaxper());
			ledps.setDouble(29, rcp.getItaxper());
			ledps.setDouble(30, rcp.getCgstamt());
			ledps.setDouble(31, rcp.getSgstamt());
			ledps.setDouble(32, rcp.getIgstamt());
			ledps.setDouble(33, rcp.getVamount());
			
			i=ledps.executeUpdate();


			
			
			i=rcp.getVou_no();
			
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			invps.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.addDebitNote Voucher" + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(invps != null){invps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close Debit Note Voucher "+e);
			}
		}

		return (i);
	}				
	

	////////////////DEbit Note Voucher (get vou detail method 

	public RcpDto getDebitNoteDetail(int vou,int depo_code,int div,int fyear,int bkcd)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		String code=null;
		try {


			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.vac_code,f.name," +
					" f.vnart_1,f.vamount,f.bill_no,f.bill_date,f.vnart_2, " +
					" f.taxable_amt,f.ctax_per,f.stax_per,f.itax_per,f.cgst_amt,f.sgst_amt,f.igst_amt " +
					" from ledfile as f "+
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
				rcp.setChq_no(rs.getString(8));
				rcp.setChq_date(rs.getDate(9));
				rcp.setVnart2(rs.getString(10));
				rcp.setTaxable(rs.getDouble(11));
				rcp.setCtaxper(rs.getDouble(12));
				rcp.setStaxper(rs.getDouble(13));
				rcp.setItaxper(rs.getDouble(14));
				rcp.setCgstamt(rs.getDouble(15));
				rcp.setSgstamt(rs.getDouble(16));
				rcp.setIgstamt(rs.getDouble(17));

				
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
	
	//// method to update record in led file (Bank Receipt voucher)	
	public int updateDebitNote(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



			String led="update  ledfile set vou_date=?,vac_code=?,exp_code=?," +
					"vnart_1=?,name=?,vamount=?,bill_no=?,bill_date=?,modified_by=?,modified_date=?,vnart_2=?," +
					"taxable_amt=?,ctax_per=?,stax_per=?,itax_per=?,cgst_amt=?,sgst_amt=?,igst_amt=?,net_amt=? " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr=? ";


			ledps=con.prepareStatement(led);

			ledps.setDate(1, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setString(2, rcp.getVac_code());
			ledps.setString(3, rcp.getVac_code());
			ledps.setString(4, rcp.getVnart1());
			ledps.setString(5, rcp.getParty_name());
			ledps.setDouble(6, rcp.getVamount());
			ledps.setString(7, rcp.getChq_no());
			if(rcp.getChq_date()!=null)
				ledps.setDate(8, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(8, null);
			ledps.setInt(9, rcp.getModified_by());
			ledps.setDate(10, new java.sql.Date(rcp.getModified_date().getTime()));

			ledps.setString(11, rcp.getVnart2());
			ledps.setDouble(12, rcp.getTaxable());
			ledps.setDouble(13, rcp.getCtaxper());
			ledps.setDouble(14, rcp.getStaxper());
			ledps.setDouble(15, rcp.getItaxper());
			ledps.setDouble(16, rcp.getCgstamt());
			ledps.setDouble(17, rcp.getSgstamt());
			ledps.setDouble(18, rcp.getIgstamt());
			ledps.setDouble(19, rcp.getVamount());

			// where clause
			ledps.setInt(20, rcp.getFin_year());
			ledps.setInt(21, rcp.getDiv_code());
			ledps.setInt(22, rcp.getVdepo_code());
			ledps.setInt(23, rcp.getVbook_cd1());
			ledps.setInt(24, rcp.getVou_no());
			if(rcp.getVbook_cd()==95)
				ledps.setString(25, "DR");
			else
				ledps.setString(25, "CR");
				
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
	

	//// method to add record in led file (bank Receipt voucher [Direct/Creditors])	
	public int addLed2HO(ArrayList rcplist)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement ledup=null;
		PreparedStatement chqps=null;

		PreparedStatement ps1=null;
		RcpDto rcp=null;
		int size=rcplist.size();
		double chqamt=0.00;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,exp_code,vnart_1,vnart_2,name,vamount,vdbcr,chq_no,chq_date,chq_amt,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,sub_div,vou_yr,bill_no,bill_date,sub_code,due_date) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


			String query1 ="insert into chqmast " +
			"(Div_code,Depo_code,  Bank_code, Cheque_no, status,vou_no,vou_date,party_code,chq_amt,chq_date," +
			" created_by,created_date,del_tag,fin_year,dbcr,issued,print ) " +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
			
			
			String chq="update chqmast set del_tag='R',modified_by=?,modified_date=? " +
					" where div_code=? and depo_code=? and bank_code=? and cheque_no=? " +
					" and vou_no=? and vou_date=? and reconcil<>'Y' ";

			
			
			String ledupd="update rcpfile set cr_no='C' where div_code=? and vdepo_code=? and vac_code=? " +
					" and vbook_cd=? and vou_no=? and vou_date=? and ifnull(del_tag,'')<>'D' ";
			
			
			ps1=con.prepareStatement(query1);
			ledps=con.prepareStatement(led);
			ledup=con.prepareStatement(ledupd);

			chqps=con.prepareStatement(chq);

			
			for(int j=0; j<size;j++)
			{
				rcp = (RcpDto) rcplist.get(j);
				chqamt+=rcp.getVamount();
				
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
				ledps.setString(12, rcp.getVnart2());

				//			ledps.setString(13, rcp.getParty_name());
				ledps.setString(13, rcp.getVnart2());  // paid to
				ledps.setDouble(14, rcp.getVamount());
				ledps.setString(15, "CR");
				ledps.setString(16, rcp.getChq_no());
				if (rcp.getChq_date()!=null)
					ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(17,null);

//				ledps.setDouble(18, chqamt);
				ledps.setDouble(18, rcp.getChq_amt());
				ledps.setString(19, String.valueOf(rcp.getTp()));
				ledps.setInt(20, rcp.getCreated_by());
				ledps.setDate(21, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(22, rcp.getSerialno());
				ledps.setInt(23, rcp.getFin_year());
				ledps.setInt(24, rcp.getMnth_code());
				ledps.setInt(25, rcp.getVouno()); // div code of mf/tf/div
				ledps.setInt(26, rcp.getVou_yr()); 
				ledps.setString(27, rcp.getBill_no());
				if (rcp.getBill_date()!=null)
					ledps.setDate(28, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(28,null);
					
				ledps.setString(29, String.valueOf(rcp.getInv_no()));   //reff no
				if (rcp.getRcon_date()!=null)
					ledps.setDate(30, new java.sql.Date(rcp.getRcon_date().getTime()));  // ref date
				else
					ledps.setDate(30,null);
				
				i=ledps.executeUpdate();

			}

			if (rcp.getVdepo_code()==15 || rcp.getVdepo_code()==11) // for baddi & mandideep
			{

				if (rcp.getInv_no()==0)  // case of Direct
				{
					ps1.setInt(1, rcp.getDiv_code());
					ps1.setInt(2, rcp.getVdepo_code());
					ps1.setInt(3, rcp.getVbook_cd());
					try
					{
						ps1.setInt(4, Integer.parseInt(rcp.getChq_no()));
					}
					catch (Exception e)
					{
						ps1.setInt(4, 0);

					}
					ps1.setString(5, "Y");
					ps1.setInt(6, rcp.getVou_no());
					ps1.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
					ps1.setString(8, rcp.getVac_code());
					ps1.setDouble(9, rcp.getChq_amt()); // Chq Amount 
					if (rcp.getChq_date()!=null)
						ps1.setDate(10, new java.sql.Date(rcp.getChq_date().getTime()));
					else
						ps1.setDate(10,null);
					ps1.setInt(11, rcp.getCreated_by());
					ps1.setDate(12, new java.sql.Date(rcp.getCreated_date().getTime()));
					ps1.setString(13, "N");
					ps1.setInt(14, rcp.getFin_year());
					ps1.setString(15, "CR");
					ps1.setString(16, "Y");
					ps1.setString(17, "Y");
					i=ps1.executeUpdate();
				}
				else   // case of creditors 
				{

					System.out.println("chq no is "+rcp.getChq_no());
					
					chqps.setInt(1, rcp.getModified_by());
					chqps.setDate(2, new java.sql.Date(rcp.getVou_date().getTime()));

					// where clause
					chqps.setInt(3, rcp.getDiv_code());
					chqps.setInt(4, rcp.getVdepo_code());
					chqps.setInt(5, rcp.getVbook_cd1());
					chqps.setString(6, rcp.getChq_no());
					chqps.setInt(7, rcp.getInv_no());  // REF NO
					chqps.setDate(8, new java.sql.Date(rcp.getRcon_date().getTime()));
					i=chqps.executeUpdate();
					System.out.println("value od i is "+i);
					
				}
			}
			
			
			ledup.setInt(1, rcp.getDiv_code());
			ledup.setInt(2, rcp.getVdepo_code());
			ledup.setString(3, rcp.getVac_code());
			ledup.setInt(4, rcp.getVbook_cd());
			ledup.setInt(5, rcp.getInv_no());
			ledup.setDate(6, new java.sql.Date(rcp.getRcon_date().getTime()));

			i=ledup.executeUpdate();
			
			i=rcp.getVou_no();
			
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			chqps.close();
			ledup.close();
			ps1.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.addLed2HO Bank Receipt Voucher" + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(ledup != null){ledps.close();}
				if(chqps != null){chqps.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close Bank Receipt Voucher HO "+e);
			}
		}

		return (i);
	}				
	

	//// method to update record in led file (Bank Receipt voucher)	
	public int updateLed1HO(ArrayList rcpList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement chqps=null;
		RcpDto rcp=null;
		double total=0.00;
		try {

			int size=rcpList.size();
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



			String led="update  ledfile set vou_date=?,vgrp_code=?,exp_code=?," +
					"vnart_1=?,name=?,vamount=?,chq_no=?,chq_date=?,chq_amt=?,modified_by=?,modified_date=?,vnart_2=? " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='CR' and serialno=? ";
			
			String chq="update chqmast set cheque_no=?,vou_Date=?,party_code=?,chq_amt=?,chq_date=?," +
					"modified_by=?,modified_date=? " +
					" where fin_year=? and div_code=? and depo_code=? and bank_code=? and vou_no=? and dbcr='CR' ";

			chqps=con.prepareStatement(chq);

			
			
			
			ledps=con.prepareStatement(led);

			for(int j=0; j<size; j++)
			{
				rcp = (RcpDto) rcpList.get(j);
				ledps.setDate(1, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps.setInt(2,rcp.getVgrp_code()); // vgrp_Code
				ledps.setString(3, rcp.getExp_code());
				ledps.setString(4, rcp.getVnart1());
				///			ledps.setString(5, rcp.getParty_name());
				ledps.setString(5, rcp.getVnart2());
				ledps.setDouble(6, rcp.getVamount());
				ledps.setString(7, rcp.getChq_no());
				if(rcp.getChq_date()!=null)
					ledps.setDate(8, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(8, null);
				ledps.setDouble(9, rcp.getChq_amt());
				ledps.setInt(10, rcp.getModified_by());
				ledps.setDate(11, new java.sql.Date(rcp.getModified_date().getTime()));

				ledps.setString(12, rcp.getVnart2());

				// where clause
				ledps.setInt(13, rcp.getFin_year());
				ledps.setInt(14, rcp.getDiv_code());
				ledps.setInt(15, rcp.getVdepo_code());
				ledps.setInt(16, rcp.getVbook_cd1());
				ledps.setInt(17, rcp.getVou_no());
				ledps.setInt(18, rcp.getSerialno());
				
				total+=rcp.getVamount();
				
				i=ledps.executeUpdate();
				
				
			}

			
			if (rcp.getVdepo_code()==15 || rcp.getVdepo_code()==11 )   // baddi and mandideep
			{

				if (rcp.getInv_no()==0)  // case of direct
				{

					try
					{
						chqps.setInt(1, Integer.parseInt(rcp.getChq_no()));
					}
					catch (Exception e)
					{
						chqps.setInt(1, 0);
					}


					chqps.setDate(2, new java.sql.Date(rcp.getVou_date().getTime()));
					chqps.setString(3, rcp.getVac_code());
					chqps.setDouble(4, total);
					if(rcp.getChq_date()!=null)
						chqps.setDate(5, new java.sql.Date(rcp.getChq_date().getTime()));
					else
						chqps.setDate(5, null);
					chqps.setInt(6, rcp.getModified_by());
					chqps.setDate(7, new java.sql.Date(rcp.getModified_date().getTime()));

					// where clause
					chqps.setInt(8, rcp.getFin_year());
					chqps.setInt(9, rcp.getDiv_code());
					chqps.setInt(10, rcp.getVdepo_code());
					chqps.setInt(11, rcp.getVbook_cd1());
					chqps.setInt(12, rcp.getVou_no());
					i=chqps.executeUpdate();
				}
			}
			
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			chqps.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.updateLed1 Bank Receipt Voucher " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(chqps != null){chqps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}		

	public List getBankReco(int depo,int div,int year,int vbook_cd,String code,Date sdate,Date voudt)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		PreparedStatement ps2 = null;
		ResultSet rs2=null;
		Connection con=null;
		List bank=null;
		RcpDto rcp=null;
		double retamt=0.00;
		String bankcharges="700"+String.format("%02d",depo)+"23";
		try {
			con=ConnectionFactory.getConnection();

			con.setAutoCommit(false);

			
			String query1 =" select 'Balance As per Ledger ' nar,ifnull(sum(a.bal),0.00) opng,a.dbcr,1 dash from "+
			" (select if(ifnull(mopng_db,'CR')='DR',mopng_bal,(mopng_bal*-1)) bal,ifnull(mopng_db,'CR') dbcr from partyacc where fin_year=? and  div_code=? and depo_code=? and mac_code like ?  and  ifnull(del_tag,'')<>'D' "+ 
			" union all "+ 
			" select sum(if(vdbcr='DR',vamount,(vamount*-1))) bal,if(sum(if(vdbcr='CR',vamount,(vamount*-1)))<0,'CR','DR') vdbcr from ledfile  where fin_year=? and div_Code=? " +
			" and vdepo_code=?  and vbook_cd1=? and vou_date <= ?  AND VAC_CODE<>?  and ifnull(del_tag,'')<>'D'  "+
			" union all "+ 
			" select sum(if(vdbcr='DR',vamount,(vamount*-1))) bal,if(sum(if(vdbcr='CR',vamount,(vamount*-1)))<0,'CR','DR') vdbcr from ledfile  where fin_year=? and div_Code=? " +
			" and vdepo_code=?  and vbook_cd1=? and vou_date <= ?  and  vac_code=? and tp='D' and ifnull(del_tag,'')<>'D' ) a "+
			" union all "+
			" select concat('Add : ',vnart_2) nar,ifnull(vamount,0.00) camt,'' vdbcr,2 dash from ledfile where div_Code=? and vdepo_code=? and vbook_cd=? and vbook_Cd1=90 and vdbcr='DR' " +
			" and vou_date=? and ifnull(del_tag,'')<>'D' "+
			" union all "+
			" select 'Cheques issued but not present in Bank (List Enclosed)' nar,ifnull(sum(chq_amt),0.00) camt,dbcr,3 dash from chqmast where div_Code=? and depo_code=? and bank_code=?" +
			" and vou_date <= ? and (rcon_date is null or ifnull(rcon_date,'0000-00-00')>?) and dbcr='DR' and issued='Y' and ifnull(del_tag,'')<>'D' "+
			" union all "+
			" select concat('Less : ',vnart_2) nar,ifnull(vamount,0.00) camt,'' vdbcr,4 dash from ledfile where div_Code=? and vdepo_code=? and vbook_cd=? and vbook_Cd1=90 and vdbcr='CR' " +
			" and vou_date=? and ifnull(del_tag,'')<>'D' " ;


			
			
			String query2 =" select ifnull(sum(a.chq_amt),0) from "+ 
			" (select chq_amt,cheque_no,party_Code from chqmast "+
			" where div_code=? and depo_code=? and bank_code=? and ifnull(rcon_date,curdate())>? "+ 
			" and CHQ_date <= ? and dbcr='DR' "+
			" and issued='Y' and ifnull(del_tag,'')<>'D' and favourof is not null   ) a "+
			" inner join "+
			" (select vou_date,vou_no,vac_Code,chq_no,chq_amt from ledfile where div_code=? and vdepo_code= ? "+
			" and vbook_cd=? and vdbcr='CR'  and ifnull(del_tag,'')<>'D' group by chq_no) b "+
			" on a.cheque_no=b.chq_no and a.party_code=b.vac_code and a.chq_amt=b.chq_amt "+ 
			" where ifnull(b.vou_Date,?) <= ?  " ; 


			
			ps2 =con.prepareStatement(query2);
			ps2.setInt(1, div);
			ps2.setInt(2, depo);
			ps2.setInt(3, vbook_cd);
			ps2.setDate(4, new java.sql.Date(voudt.getTime()));
			ps2.setDate(5, new java.sql.Date(voudt.getTime()));

			ps2.setInt(6, div);
			ps2.setInt(7, depo);
			ps2.setInt(8, vbook_cd);
			ps2.setDate(9, new java.sql.Date(voudt.getTime()));
			ps2.setDate(10, new java.sql.Date(voudt.getTime()));

			rs2 =ps2.executeQuery();
			
			if (rs2.next())
			{
				retamt=rs2.getDouble(1);
			}
			rs2.close();
			ps2.close();
			
			System.out.println("RETAMT IS "+retamt);
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setString(4, "%"+code);
			ps1.setInt(5, year);
			ps1.setInt(6, div);
			ps1.setInt(7, depo);
			ps1.setInt(8, vbook_cd);
			ps1.setDate(9, new java.sql.Date(voudt.getTime()));
			ps1.setString(10, bankcharges);
			ps1.setInt(11, year);
			ps1.setInt(12, div);
			ps1.setInt(13, depo);
			ps1.setInt(14, vbook_cd);
			ps1.setDate(15, new java.sql.Date(voudt.getTime()));
			ps1.setString(16, bankcharges);
			
			ps1.setInt(17, div);
			ps1.setInt(18, depo);
			ps1.setInt(19, vbook_cd);
			ps1.setDate(20, new java.sql.Date(voudt.getTime()));
			ps1.setInt(21, div);
			ps1.setInt(22, depo);
			ps1.setInt(23, vbook_cd);
			ps1.setDate(24, new java.sql.Date(voudt.getTime()));
			ps1.setDate(25, new java.sql.Date(voudt.getTime()));
			ps1.setInt(26, div);
			ps1.setInt(27, depo);
			ps1.setInt(28, vbook_cd);
			ps1.setDate(29, new java.sql.Date(voudt.getTime()));

			rst =ps1.executeQuery();
			bank=new ArrayList();
			boolean first=true;
			boolean tfirst=true;
			double total=0.00;
			String wdbcr="";
			while (rst.next())
			{

				
			
				if (rst.getInt(4)==3 && !rst.getString(3).equals(wdbcr))
				{
					if ((rst.getInt(4)==4 || rst.getInt(4)==3 )  && tfirst)
					{
						rcp = new RcpDto();
						rcp.setDash(33);
						rcp.setVnart1(" ");
						rcp.setVamount(total);
						rcp.setVdbcr("");
						total=0.00;
						bank.add(rcp);
						tfirst=false;
					}
				}

				
				if (rst.getInt(4)==4  && tfirst)
				{
					rcp = new RcpDto();
					rcp.setDash(33);
					rcp.setVnart1(" ");
					rcp.setVamount(total);
					rcp.setVdbcr("");
					total=0.00;
					bank.add(rcp);
					tfirst=false;
				}

				
				rcp = new RcpDto();
				rcp.setDash(rst.getInt(4));
				rcp.setVnart1(rst.getString(1));
				rcp.setVamount(rst.getDouble(2));  
				if (rst.getInt(4)==3)
				{
					rcp.setVamount(rst.getDouble(2)-retamt);  
					retamt=0.00;
				}

				System.out.println("daeh "+rst.getInt(4)+ " amount "+rst.getDouble(2));
				if (rst.getString(3)!=null)
					rcp.setVdbcr(rst.getString(3));
				else
					rcp.setVdbcr("");
					
				if(first)
				{
					if(rst.getDouble(2)<0)
					{
						rcp.setVamount(rst.getDouble(2)*-1);
						rcp.setVdbcr("DR");
					}
					else
					{
						rcp.setVamount(rst.getDouble(2));
						rcp.setVdbcr("CR");

					}
					first=false;
					total+=rcp.getVamount();

					wdbcr=rcp.getVdbcr();
				}
				
				if (rst.getInt(4)==3 || rst.getInt(4)==2)
					total+=rcp.getVamount();


				
				bank.add(rcp);
			}

			
			if (tfirst)
			{
				rcp = new RcpDto();
				rcp.setDash(33);
				rcp.setVnart1(" ");
				rcp.setVamount(total);  
				rcp.setVdbcr("");
				total=0.00;
				bank.add(rcp);
			}

			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in CashVouDAO.getBankReco " + ex);
		}
		finally {
			try {

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return bank;
	}	 
	

	public List getMultipleVouDetailAll(int startVoucher,int endVoucher,int depo_code,int div,int fyear,int doctp,String tp,int cmpcode)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		PreparedStatement ps3 = null;
		ResultSet rs3=null;
		
		Connection con=null;
		RcpDto rcp=null;
		RcpDto rcp1=null;
		double total=0.00;
		List voucherList=null;
		String query2=null; 
		int wdiv=4;
		int mnthcode=0;
		int vtercd=0;
		int wdepo=depo_code;
		
		System.out.println("DIV KI VALUE "+div+" wdiv ki value "+wdiv);
		if(depo_code==8)
		{
		  mnthcode=Integer.parseInt(tp.split(",")[1]);
		  vtercd=Integer.parseInt(tp.split(",")[2]);
		  tp="D";
		  
		  
		  System.out.println("mnthcode "+mnthcode+" vtercd "+vtercd);
		}
		else
		{
			tp="D";
		}
		
		if (div==51)
		{
			div=4;
			wdiv=1;
		}
			if (div==52)
			{
				div=4;
				wdiv=2;
			}
		if (div==53)
		{
				div=4;
				wdiv=3;
		}

		
		if (div==60)
		{
				div=4;
				wdiv=10;
		}
		if (div==70)
		{
				div=4;
				wdiv=20;
		}

		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
			if (depo_code==3 || depo_code== 11 || depo_code== 12 || depo_code== 18 )
			{
				query2 ="select a.* from (select f.vou_lo,f.name,f.vou_no,f.vou_date,f.vac_code,p.mac_name pname,f.sub_code," +
						"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.chq_no,f.chq_date,f.chq_amt chqamt ,f.bank_chg, "+
						" 0 vno,0 bno,null bdate,0 bamt,0 tamt,0 vamt,null vdt,'' vlo,'' voulo,'' vnart2,0 tds,f.tp  "+
						"  from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D' "+ 
						",partyfst as p "+
						"where f.vac_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=? and ifnull(tp,'D')=? and f.vou_no between ? and ? and f.sub_div="+wdiv+" and   ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=?  and ifnull(p.del_tag,'')<>'D' "+  
						" union all "+
						"select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name pname,f.sub_code," +
						"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.chq_no,f.chq_date,f.chq_amt,f.bank_chg,  "+
						" 0 vno,0 bno,null bdate,0 bamt,0 tamt,0 vamt,null vdt,'' vlo,'' voulo,'' vnart2,0 tds,f.tp  "+
						" from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D' "+ 
						",partyfst as p "+
						"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=?  and ifnull(tp,'D')=? and f.vou_no between ? and ? and f.sub_div in (0,?) and f.vdbcr='DR' and ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' "+  
						" union all"+
						" select l.vou_lo,l.name,l.vou_no,l.vou_date,l.vac_code,'' pname,0 scode,'' sname," +
						"l.vnart_1,l.vnart_2,l.vamount,l.vbook_cd1,l.vgrp_code,l.serialno,l.chq_no,l.chq_date,l.vamount chqamt ,l.bank_chg, "+
						" r.vouno,r.bill_no,r.bill_date,ifnull(r.bill_amt,0),ifnull(r.tds_amt,0),ifnull(r.vamount,0),r.voudt," +
						" ifnull(r.inv_lo,concat(left(r.vou_lo,1),'4')),r.vou_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),l.tp  "+
						" from ledfile as l,rcpfile as r "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=?" +
						" and l.vbook_cd1=? and l.tp='C' and l.vdbcr='DR' and l.vou_no between ? and ? "+
						" and ifnull(l.del_tag,'')<>'D' and r.fin_year=? and r.div_code=? " +
						" and r.vdepo_code=? and r.vbook_cd=? and r.vou_no=l.vou_no and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date " +  
						" union all"+
						" select l.vou_lo,l.name,l.vou_no,l.vou_date,l.vac_code,p.mac_name,0 scode,'' sname, "+ 
						" l.vnart_1,l.vnart_2,l.vamount,l.vbook_cd1,l.vgrp_code,l.serialno,l.chq_no,l.chq_date,l.vamount chqamt ,l.bank_chg, "+ 
						" 0 vouno ,r.bill_no,r.bill_date,ifnull(r.bill_amt,0),ifnull(r.tds_amt,0),ifnull(r.vamount,0),r.voudt, "+
						" concat(left(r.vou_lo,1),'R',right(r.vou_lo,1),right(r.vou_no,5)),r.vou_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),'R' tp "+  
						" from ledfile as l,rcpfile as r,partyfst as p "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=? "+
						" and l.vbook_cd1=?  and l.vou_no between ? and ? "+
						" and ifnull(l.del_tag,'')<>'D' and r.div_code=? "+  
						" and r.vdepo_code=? and r.vbook_cd=? and sub_div=? and l.bill_no=r.vou_no and  ifnull(r.del_tag,'')<>'D' and l.bill_date=r.vou_date "+
						" and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' and p.mac_Code=l.vac_code and p.mac_code=r.vac_Code ) a order by vou_no,serialno ";
			}

			else if (depo_code== 8 && vtercd==8)
			{
				query2 ="select a.* from (select concat(f.vou_lo,f.indicator,right(f.vou_no,5)),f.name,f.vou_no,f.vou_date,f.vac_code,p.mac_name pname,f.sub_code," +
						"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.chq_no,f.chq_date,f.chq_amt chqamt ,f.bank_chg, "+
						" 0 vno,ifnull(f.bill_no,'') bno,f.bill_date bdate,0 bamt,0 tamt,0 vamt,null vdt,'' vlo,'' voulo,'' vnart2,0 tds,f.tp,ifnull(f.tax_type,'')  "+
						"  from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D' "+ 
						",partyfst as p "+
						"where f.vac_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=? and ifnull(tp,'D')=? and f.vou_no between ? and ? and f.mnth_code="+mnthcode+" and   ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' "+  
						" union all "+
						"select concat(f.vou_lo,f.indicator,right(f.vou_no,5)),f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name pname,f.sub_code," +
						"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.chq_no,f.chq_date,f.chq_amt,f.bank_chg,  "+
						" 0 vno,0 bno,null bdate,0 bamt,0 tamt,0 vamt,null vdt,'' vlo,'' voulo,'' vnart2,0 tds,f.tp ,ifnull(f.tax_type,'') "+
						" from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D' "+ 
						",partyfst as p "+
						"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=?  and ifnull(tp,'D')=? and f.vou_no between ? and ? and f.sub_div in (0,?) and f.mnth_code="+mnthcode+" and f.vdbcr='DR' and ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' "+  
						" union all"+
						" select concat(l.vou_lo,l.indicator,right(l.vou_no,5)),l.name,l.vou_no,l.vou_date,l.vac_code,'' pname,0 scode,'' sname," +
						" l.vnart_1,r.vnart_1,l.vamount,l.vbook_cd1,l.vgrp_code,l.serialno,l.chq_no,l.chq_date,l.vamount chqamt ,l.bank_chg, "+
						" r.vouno,ifnull(r.bill_no,''),r.bill_date,ifnull(r.bill_amt,0),ifnull(r.tds_amt,0),ifnull(r.vamount,0),r.voudt," +
						" ifnull(r.inv_lo,concat(left(r.vou_lo,1),'4')),r.vou_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),l.tp,ifnull(l.tax_type,'')  "+
						" from ledfile as l,rcpfile as r "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=?" +
						" and l.vbook_cd1=? and l.tp='C' and l.vdbcr='DR' and l.vou_no between ? and ? and l.mnth_code="+mnthcode+" "+
						" and ifnull(l.del_tag,'')<>'D' and r.fin_year=? and r.div_code=? " +
						" and r.vdepo_code=? and r.vbook_cd=? and r.vou_no=l.vou_no and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date " +  
						" union all"+
						" select concat(l.vou_lo,l.indicator,right(l.vou_no,5)),l.name,l.vou_no,l.vou_date,l.vac_code,p.mac_name,0 scode,'' sname, "+ 
						" l.vnart_1,l.vnart_2,l.vamount,l.vbook_cd1,l.vgrp_code,l.serialno,l.chq_no,l.chq_date,l.vamount chqamt ,l.bank_chg, "+ 
						" 0 vouno ,ifnull(r.bill_no,''),r.bill_date,ifnull(r.bill_amt,0),ifnull(r.tds_amt,0),ifnull(r.vamount,0),r.voudt, "+
						" concat(left(r.vou_lo,1),'R',right(r.vou_lo,1),right(r.vou_no,5)),r.vou_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),'R' tp ,ifnull(l.tax_type,'')"+  
						" from ledfile as l,rcpfile as r,partyfst as p "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=? "+
						" and l.vbook_cd1=?  and l.vou_no between ? and ? and l.mnth_code="+mnthcode+
						" and ifnull(l.del_tag,'')<>'D' and r.div_code=? "+  
						" and r.vdepo_code=? and r.vbook_cd=? and sub_div=? and l.bill_no=r.vou_no and  ifnull(r.del_tag,'')<>'D' and l.bill_date=r.vou_date "+
						" and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' and p.mac_Code=l.vac_code and p.mac_code=r.vac_Code ) a order by vou_no,serialno ";
			}
			else if (depo_code== 8 && vtercd!=8)
			{
				query2 ="select a.* from (select concat(f.vou_lo,'AH',right(f.vou_no,4)),f.name,f.vou_no,f.vou_date,f.vac_code,p.mac_name pname,f.sub_code," +
						"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.chq_no,f.chq_date,f.chq_amt chqamt ,f.bank_chg, "+
						" 0 vno,0 bno,null bdate,0 bamt,0 tamt,0 vamt,null vdt,'' vlo,'' voulo,'' vnart2,0 tds,f.tp  "+
						"  from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D' "+ 
						",partyfst as p "+
						"where f.vac_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=? and ifnull(tp,'D')=? and f.vou_no between ? and ? and f.vter_cd="+vtercd+" and   ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' "+  
						" union all "+
						"select concat(f.vou_lo,'AH',right(f.vou_no,4)),f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name pname,f.sub_code," +
						"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.chq_no,f.chq_date,f.chq_amt,f.bank_chg,  "+
						" 0 vno,0 bno,null bdate,0 bamt,0 tamt,0 vamt,null vdt,'' vlo,'' voulo,'' vnart2,0 tds,f.tp  "+
						" from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D' "+ 
						",partyfst as p "+
						"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=?  and ifnull(tp,'D')=? and f.vou_no between ? and ? and f.sub_div in (0,?) and f.vter_cd="+vtercd+" and f.vdbcr='DR' and ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' "+  
						" union all"+
						" select concat(l.vou_lo,'AH',right(l.vou_no,4)),l.name,l.vou_no,l.vou_date,l.vac_code,'' pname,0 scode,'' sname," +
						"l.vnart_1,l.vnart_2,l.vamount,l.vbook_cd1,l.vgrp_code,l.serialno,l.chq_no,l.chq_date,l.vamount chqamt ,l.bank_chg, "+
						" r.vouno,r.bill_no,r.bill_date,ifnull(r.bill_amt,0),ifnull(r.tds_amt,0),ifnull(r.vamount,0),r.voudt," +
						" ifnull(r.inv_lo,concat(left(r.vou_lo,1),'4')),r.vou_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),l.tp  "+
						" from ledfile as l,rcpfile as r "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=?" +
						" and l.vbook_cd1=? and l.tp='C' and l.vdbcr='DR' and l.vou_no between ? and ? and l.vter_cd="+vtercd+" "+
						" and ifnull(l.del_tag,'')<>'D' and r.fin_year=? and r.div_code=? " +
						" and r.vdepo_code=? and r.vbook_cd=? and r.vou_no=l.vou_no and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date " +  
						" union all"+
						" select concat(l.vou_lo,'AH',right(l.vou_no,4)),l.name,l.vou_no,l.vou_date,l.vac_code,p.mac_name,0 scode,'' sname, "+ 
						" l.vnart_1,l.vnart_2,l.vamount,l.vbook_cd1,l.vgrp_code,l.serialno,l.chq_no,l.chq_date,l.vamount chqamt ,l.bank_chg, "+ 
						" 0 vouno ,r.bill_no,r.bill_date,ifnull(r.bill_amt,0),ifnull(r.tds_amt,0),ifnull(r.vamount,0),r.voudt, "+
						" concat(left(r.vou_lo,1),'R',right(r.vou_lo,1),right(r.vou_no,5)),r.vou_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),'R' tp "+  
						" from ledfile as l,rcpfile as r,partyfst as p "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=? "+
						" and l.vbook_cd1=?  and l.vou_no between ? and ? and l.vter_cd="+vtercd+
						" and ifnull(l.del_tag,'')<>'D' and r.div_code=? "+  
						" and r.vdepo_code=? and r.vbook_cd=? and sub_div=? and l.bill_no=r.vou_no and  ifnull(r.del_tag,'')<>'D' and l.bill_date=r.vou_date "+
						" and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' and p.mac_Code=l.vac_code and p.mac_code=r.vac_Code ) a order by vou_no,serialno ";
			}

			
			else
			{

				query2 ="select a.* from (select f.vou_lo,f.name,f.vou_no,f.vou_date,f.vac_code,p.mac_name pname,f.sub_code," +
						"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.chq_no,f.chq_date,IFNULL(f.chq_amt,F.VAMOUNT) chqamt ,f.bank_chg, "+
						" 0 vno,0 bno,null bdate,0 bamt,0 tamt,0 vamt,null vdt,'' vlo,'' voulo,'' vnart2,0 tds,f.tp  "+
						"  from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D' "+ 
						",partyfst as p "+
						"where f.vac_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=? and ifnull(tp,'D')=? and f.vdbcr='DR' and f.vou_no between ? and ? and f.sub_div="+wdiv+" and ifnull(f.tran_type,0)=0 and   ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' "+  
						" union all "+
						"select f.vou_lo,f.name,f.vou_no,f.vou_date,ifnull(f.exp_code,f.vac_code),p.mac_name pname,f.sub_code," +
						"s.mac_name,ifnull(f.vnart_1,''),ifnull(f.vnart_2,''),f.vamount,f.vbook_cd,f.vgrp_code,f.serialno,f.chq_no,f.chq_date,ifnull(f.chq_amt,f.vamount),f.bank_chg,  "+
						" 0 vno,f.bill_no,f.bill_date,0 bamt,0 tamt,0 vamt,null vdt,'' vlo,'' voulo,'' vnart2,0 tds,f.tp  "+
						" from ledfile as f "+
						"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D' "+ 
						",partyfst as p "+
						"where ifnull(f.exp_code,f.vac_code)=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
						"f.vbook_cd1=?  and ifnull(tp,'D')=? and f.vdbcr='DR' and f.vou_no between ? and ? and f.sub_div in (0,?) and f.vdbcr='DR' and ifnull(f.tran_type,0)=0 and ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' "+  
						" union all"+
						" select l.vou_lo,l.name,l.vou_no,l.vou_date,l.vac_code,'' pname,0 scode,'' sname," +
						"l.vnart_1,l.vnart_2,l.vamount,l.vbook_cd1,l.vgrp_code,l.serialno,l.chq_no,l.chq_date,l.vamount chqamt ,l.bank_chg, "+
						" r.vouno,r.bill_no,r.bill_date,ifnull(r.bill_amt,0),ifnull(r.tds_amt,0),ifnull(r.vamount,0),r.voudt," +
						" ifnull(r.inv_lo,concat(left(r.vou_lo,1),'4')),r.vou_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),l.tp  "+
						" from ledfile as l,rcpfile as r "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=?" +
						" and l.vbook_cd1=? and l.tp='C' and l.vdbcr='DR' and l.vou_no between ? and ? and l.sub_div="+wdiv+
						" and ifnull(l.tran_type,0)=0 and ifnull(l.del_tag,'')<>'D' and r.fin_year=? and r.div_code=? " +
						" and r.vdepo_code=? and r.vbook_cd=? and r.vou_no=l.vou_no and ifnull(r.tran_type,0)=0 and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date " +  
						"  ) a order by vou_no,serialno,vamount desc";

			}
			
			
			String query3="select if(bill_amt=0,vnart_1,bill_no),bill_date,if(bill_amt=0,vamount,bill_amt),vou_lo,vou_no  from rcpfile where div_code=?" +
			" and vdepo_code=? and vbook_cd in (?,99)  and vac_Code=? and vou_no=? and vou_Date=? and  ifnull(del_tag,'')<>'D' " ;

			ps3 = con.prepareStatement(query3);

			
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,fyear);
			ps2.setInt(4,div);
			ps2.setInt(5, depo_code);
			ps2.setInt(6, doctp);
			ps2.setString(7, tp);
			ps2.setInt(8,startVoucher); 
			ps2.setInt(9,endVoucher);
			ps2.setInt(10,div);
			ps2.setInt(11, wdepo);
			
			ps2.setInt(12,div);
			ps2.setInt(13, depo_code);
			ps2.setInt(14,fyear);
			ps2.setInt(15,div);
			ps2.setInt(16, depo_code);
			ps2.setInt(17, doctp);
			ps2.setString(18, tp);
			ps2.setInt(19,startVoucher); 
			ps2.setInt(20,endVoucher);
			ps2.setInt(21,wdiv);
			ps2.setInt(22,wdiv);
			ps2.setInt(23, cmpcode);
			

			ps2.setInt(24,fyear);
			ps2.setInt(25,div);
			ps2.setInt(26, depo_code);
			ps2.setInt(27, doctp);
			ps2.setInt(28,startVoucher); 
			ps2.setInt(29,endVoucher);
			ps2.setInt(30,fyear);
			ps2.setInt(31,div);
			ps2.setInt(32, depo_code);
			ps2.setInt(33, doctp);

		if (depo_code==3 || depo_code==11 || depo_code==12 || depo_code==8 || depo_code==18)
		{
			ps2.setInt(34,fyear);
			ps2.setInt(35,div);
			ps2.setInt(36, depo_code);
			ps2.setInt(37, doctp);
			ps2.setInt(38,startVoucher); 
			ps2.setInt(39,endVoucher);
			ps2.setInt(40,wdiv);
			ps2.setInt(41, cmpcode);
			ps2.setInt(42, doctp);
			ps2.setInt(43,wdiv);
			ps2.setInt(44,wdiv);
			ps2.setInt(45, cmpcode);

		}	
			
			rs= ps2.executeQuery();


			
			voucherList = new ArrayList(); 
			int wno = 0;
			boolean first=true;
			double rgross = 0.00;
			int vouno=0;
			String wtp="";
			double tds=0.00;
			int j=0;
			int r=0;
			int vou_no=0;
			boolean check=true;
			boolean print=true;
			while (rs.next())
			{
				System.out.println(rs.getInt(3)+"  "+rs.getDouble(11)+"  "+rs.getDouble(17)+" 20.. "+(rs.getString(20)));
				check=true;
				
				 try {
					vou_no=Integer.parseInt(rs.getString(20));
				} catch (NumberFormatException e) {

					vou_no=0;
				}

				
				if(first)
				{
					wno=rs.getInt(3);
					first=false;
					tds+=rs.getDouble(29);
				}
				
				if(wno!=rs.getInt(3))
				{
					rcp= new RcpDto();
					rcp.setDash(1);
					rcp.setTp(wtp.charAt(0));
					rcp.setVamount(rgross);
					rcp.setVouno(vouno);
					voucherList.add(rcp);
					rgross=0.00;
					wno=rs.getInt(3);
					j++;

					rcp1= (RcpDto) voucherList.get(r);
					rcp1.setInterest(tds);
					voucherList.set(r, rcp1);


					tds=0.00;
					tds+=rs.getDouble(29);
					r=j;
					print=true;
				}
				
				System.out.println("VOU NO "+vou_no+" date "+rs.getDate(21));
				if(print)
				{
					ps3.setInt(1,(wdiv));
					ps3.setInt(2, cmpcode);
					ps3.setInt(3, doctp);
					ps3.setString(4,rs.getString(5));
					ps3.setInt(5,vou_no); 
					ps3.setDate(6,rs.getDate(21));
					rs3= ps3.executeQuery();

					while (rs3.next())
					{

							rcp= new RcpDto();

							rcp.setVou_no(rs.getInt(3));
							rcp.setVou_date(rs.getDate(4));
							rcp.setVac_code(rs.getString(5));
							rcp.setParty_name(rs.getString(2));
							if (rs.getString(2)==null)
								rcp.setParty_name(rs.getString(6));

							rcp.setVou_lo(rs.getString(1));
							rcp.setExp_code(rs.getString(5));
							rcp.setExp_name(rs.getString(6));
							rcp.setSub_code(rs.getString(7));
							rcp.setSub_name(rs.getString(8));
							rcp.setVnart1(rs.getString(9));
							rcp.setVnart2(rs.getString(10));
							rcp.setVamount(rs.getDouble(11));
							rcp.setDash(0);
							rcp.setChq_no(rs.getString(15));
							rcp.setChq_date(rs.getDate(16));
							rcp.setChq_amt(rs.getDouble(17));
							rcp.setBank_chg(rs.getDouble(18));
							rcp.setBill_no(rs3.getString(1));
							rcp.setBill_date(rs3.getDate(2));
							rcp.setBill_amt(rs3.getDouble(3));
							rcp.setTds_amt(rs.getDouble(23));
							rcp.setRcp_no(rs3.getString(4).charAt(0)+"R"+rs3.getString(4).charAt(1)+rs3.getString(5).substring(1));

							rcp.setVouno(rs.getInt(19));
							rcp.setDiscount(rs.getDouble(24));  // vamount paid of a particular bill in rcp file
							rcp.setInterest(rs.getDouble(29)); // tds amount for advance payment
							//					rcp.setTp(rs.getString(30).charAt(0));
							rcp.setTp('R');

							vouno=rs.getInt(19);
							if (rcp.getTp()=='D' )
								rgross+=rs.getDouble(11);

							if (rcp.getTp()=='C' || rcp.getTp()=='R')
								rgross+=rs.getDouble(24);

							tds+=rs.getDouble(23);
							j++;

							wtp=rs.getString(30);
							voucherList.add(rcp);
							check=false;
					}
					rs3.close();
					if(!check)
					print=false;
				}

					
				if(check && print)
				{
					System.out.println("vnart21 "+rs.getString(9));
					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(3));
					rcp.setVou_date(rs.getDate(4));
					rcp.setVac_code(rs.getString(5));
					rcp.setParty_name(rs.getString(2));
					if (rs.getString(2)==null)
						rcp.setParty_name(rs.getString(6));

					rcp.setVou_lo(rs.getString(1));
					rcp.setExp_code(rs.getString(5));
					rcp.setExp_name(rs.getString(6));
					rcp.setSub_code(rs.getString(7));
					rcp.setSub_name(rs.getString(8));
					rcp.setVnart1(rs.getString(9));
					rcp.setVnart2(rs.getString(10));
					rcp.setVamount(rs.getDouble(11));
					rcp.setDash(0);
					rcp.setChq_no(rs.getString(15));
					rcp.setChq_date(rs.getDate(16));
					rcp.setChq_amt(rs.getDouble(17));
					rcp.setBank_chg(rs.getDouble(18));
					rcp.setBill_no(rs.getString(20));
					rcp.setBill_date(rs.getDate(21));
					rcp.setBill_amt(rs.getDouble(22));
					rcp.setTds_amt(rs.getDouble(23));
					rcp.setRcp_no(rs.getString(26));
					rcp.setVouno(rs.getInt(19));
					rcp.setDiscount(rs.getDouble(24));  // vamount paid of a particular bill in rcp file
					rcp.setInterest(rs.getDouble(29)); // tds amount for advance payment
					rcp.setTp(rs.getString(30).charAt(0));

					rcp.setTaxtype("");
					if (depo_code== 8 && vtercd==8)
						rcp.setTaxtype(rs.getString(31));
					vouno=rs.getInt(19);
					if (rcp.getTp()=='D' )
						rgross+=rs.getDouble(11);

					if (rcp.getTp()=='C' || rcp.getTp()=='R')
						rgross+=rs.getDouble(24);

					tds+=rs.getDouble(23);
					j++;

					wtp=rs.getString(30);
					voucherList.add(rcp);
					check=true;
				}
			}

			
			if(!first)
			{
				rcp= new RcpDto();
				rcp.setTp(wtp.charAt(0));
				rcp.setDash(2);
				rcp.setVamount(rgross);
				voucherList.add(rcp);
	          	rcp1= (RcpDto) voucherList.get(r);
            	rcp1.setInterest(tds);
            	voucherList.set(r, rcp1);
			}
            
            
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();
			ps3.close();

		} catch (SQLException ex) {
			System.out.println(ex);
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getMultiVouDetail " + ex);

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
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return voucherList;
	}	
	
	
	////////////////bank paymen direct method

	public boolean checkVouNo(int vou,int depo_code,int div,int fyear,int bkcd)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		int maxSerialNo=0;
		boolean found=false;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String maxNo = "select ifnull(max(vou_no),0) from ledfile where fin_year=? and div_code=? and vdepo_code=? and "+ 
			" vbook_cd1=? and vdbcr='DR' and ifnull(del_tag,'') <>'D' ";

			
			
			String maxSrial = "select vou_no from ledfile where fin_year=? and div_code=? and vdepo_code=? and "+ 
			" vbook_cd1=? and vou_no=? and vdbcr='DR' and ifnull(del_tag,'') <>'D' ";

			
			ps2 = con.prepareStatement(maxNo);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			
			rs= ps2.executeQuery();
			if(rs.next())
			{
				maxSerialNo = rs.getInt(1);
			}
			rs.close();
			ps2.close();

			rs=null;
			ps2=null;
			
			
			
			ps2 = con.prepareStatement(maxSrial);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,vou); 
			
			rs= ps2.executeQuery();
			if(rs.next())
			{
				if (maxSerialNo >= rs.getInt(1))
					found=true;
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return found;
	}		


	public List getPendingChq(int depo,int div,int year,int vbook_cd,Date sdate,Date edate)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		List bank=null;
		ChequeDto chq=null;
		Date endate= new java.sql.Date(edate.getTime());
		try {
			con=ConnectionFactory.getConnection();

			con.setAutoCommit(false);

			
/*			String query1 =" select vou_date,invno,cheque_no,favourof,chq_amt from chqmast "+
			" where div_code=? and depo_code=? and bank_code=? and ifnull(rcon_date,curdate())>? "+
			" and vou_date <= ? "+
			" and issued='Y' and ifnull(del_tag,'')<>'D' and favourof is not null order by vou_date,vou_no ";
*/			
			

/*			String query1 =" select c.vou_date,c.invno,c.cheque_no,c.favourof,c.chq_amt from chqmast c "+
			" left join ledfile l "+
			" on c.cheque_no=l.chq_no and c.party_code=l.vac_Code and c.chq_amt=l.vamount and c.vou_date<=l.vou_date "+
			" where c.div_Code=? and c.depo_code=? and c.bank_code=? and ifnull(c.rcon_date,curdate())>? "+
			" and ? >= c.chq_date and ?<l.vou_date "+
			" and c.issued='Y' and ifnull(c.del_tag,'')<>'D' and c.favourof is not null and l.div_Code=? and l.vdepo_Code=? " +
			" and l.vbook_cd1=? and l.vdbcr='CR' order by c.vou_date,c.vou_no ";
*/			
			
		 	
			String query1 =" select a.*,b.* from "+ 
			" (select vou_date,invno,cheque_no,favourof,chq_amt,CHQ_DATE,RCON_dATE,party_code,ifnull(del_tag,'') from chqmast "+ 
			" where div_code=? and depo_code=? and bank_code=? and ifnull(rcon_date,curdate())>? "+ 
			" and CHQ_date <= ? "+
			" and issued='Y' and ifnull(del_tag,'')<>'D' and favourof is not null  ) a "+
			" left join "+

			" (select vou_date,vou_no,vac_Code,chq_no,chq_amt  from ledfile where div_code=? and vdepo_code= ? "+
			" and vbook_cd=? and vdbcr='CR'  and ifnull(del_tag,'')<>'D' group by chq_no) b "+
			" on a.cheque_no=b.chq_no and a.party_code=b.vac_code and a.chq_amt=b.chq_amt  "; 
//			" where a.vou_date <= ifnull(b.vou_date,a.vou_date) " ; 
			
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, div);
			ps1.setInt(2, depo);
			ps1.setInt(3, vbook_cd);
			ps1.setDate(4, new java.sql.Date(edate.getTime()));
			ps1.setDate(5, new java.sql.Date(edate.getTime()));
			ps1.setInt(6, div);
			ps1.setInt(7, depo);
			ps1.setInt(8, vbook_cd);


			rst =ps1.executeQuery();
			boolean first=true;
			while (rst.next())
			{

				if (first)
				{
					bank=new ArrayList();
					first=false;
				}

				if(rst.getString(9).equals("R") && rst.getDate(1).before(edate))
				{
					chq = new ChequeDto();
					chq.setVou_date(rst.getDate(1));
					chq.setInvno(rst.getString(2));
					chq.setCheque_no(rst.getInt(3));
					chq.setFavourof(rst.getString(4));  
					chq.setChq_amt(rst.getDouble(5));
					bank.add(chq);
					
				}
				if ((rst.getDate(10)==null || rst.getDate(10).after(endate)) && !rst.getString(9).equals("R") )
				{
					chq = new ChequeDto();
					chq.setVou_date(rst.getDate(1));
					chq.setInvno(rst.getString(2));
					chq.setCheque_no(rst.getInt(3));
					chq.setFavourof(rst.getString(4));  
					chq.setChq_amt(rst.getDouble(5));
					bank.add(chq);
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
			System.out.println("-------------Exception in CashVouDAO.getBankReco " + ex);
		}
		finally {
			try {

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return bank;
	}	 
	
	public List getReconcile(int depo,int div,int year,int vbook_cd,Date sdate,Date edate)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		List bank=null;
		ChequeDto chq=null;
		try {
			con=ConnectionFactory.getConnection();

			con.setAutoCommit(false);

			
			String query1 =" select vou_date,invno,cheque_no,favourof,chq_amt,rcon_date,chq_Date from chqmast "+
			" where div_code=? and depo_code=? and bank_code=? and ifnull(rcon_date,?)<=? "+
			" and rcon_date between ?  and ? "+
			" and reconcil='Y' and ifnull(del_tag,'')<>'D' and favourof is not null order by rcon_no ";
			
			


			
			
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, div);
			ps1.setInt(2, depo);
			ps1.setInt(3, vbook_cd);
			ps1.setDate(4, new java.sql.Date(sdate.getTime()));
			ps1.setDate(5, new java.sql.Date(edate.getTime()));
			ps1.setDate(6, new java.sql.Date(sdate.getTime()));
			ps1.setDate(7, new java.sql.Date(edate.getTime()));

			rst =ps1.executeQuery();
			
			boolean first=true;
			while (rst.next())
			{
				if (first)
				{
					bank=new ArrayList();
					first=false;
				}
				chq = new ChequeDto();
				chq.setVou_date(rst.getDate(1));
				chq.setInvno(rst.getString(2));
				chq.setCheque_no(rst.getInt(3));
				chq.setFavourof(rst.getString(4));  
				chq.setChq_amt(rst.getDouble(5));
				chq.setRcon_date(rst.getDate(6));
				chq.setChq_date(rst.getDate(7));
				bank.add(chq);
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
			System.out.println("-------------Exception in CashVouDAO.getBankReco " + ex);
		}
		finally {
			try {

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in CashVouDAO.Connection.close "+e);
			}
		}

		return bank;
	}	 

	
	
	////////////////bank Receipt Voucher for HO (Mandideep/CWH/HO) (get vou detail method for (Direct/Creditors)

	public RcpDto getVouDetail2HO(int vou,int depo_code,int div,int fyear,int bkcd,int subdiv)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		String code=null;
		try {


			code="040"+depo_code+"01";
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.vac_code,f.name," +
					" f.vnart_1,f.vamount,f.chq_no,f.chq_date,f.sub_div,f.serialno,cast(ifnull(sub_code,0) as unsigned) scode,f.due_date from ledfile as f "+
					" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
					" f.vbook_cd1=? and f.vou_no=? and f.vac_code<>? and f.sub_div<=? and f.vdbcr='CR' and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  
			//" f.vbook_cd1=? and f.vou_no=? and f.vdbcr='CR' and f.vac_code<>? and f.sub_div=? and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,vou); 
			ps2.setString(6,code); 
			ps2.setInt(7,subdiv); 

			rs= ps2.executeQuery();

			boolean first=true;
			Vector row = null;
			Vector col = null;
			while (rs.next())
			{
				
				
				if(first)
				{
					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(2));
					rcp.setVou_date(rs.getDate(3));
					rcp.setVac_code(rs.getString(4));
					rcp.setParty_name(rs.getString(5));
					rcp.setVnart1(rs.getString(6));
					rcp.setChq_no(rs.getString(8));
					rcp.setChq_date(rs.getDate(9));
					rcp.setChq_amt(rs.getDouble(7));
					rcp.setVouno(rs.getInt(10)); // serialno 
					rcp.setInv_no(rs.getInt(12));    // ref no
					rcp.setDue_Date(rs.getDate(13));  // ref date
					first=false;
					row = new Vector();
					
				}
				   
				    col = new Vector();
				    col.add(false);
				    col.add(rs.getString(4));
				    col.add(rs.getString(5));
				    col.add(rs.getString(6));
				    col.add(rs.getBigDecimal(7));
				    col.add(rs.getInt(11));   // serial no
				    row.add(col);
				    
				    
			} // end of while

			if (rcp!=null)
			{
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail2 Bank Receipt Voucher " + ex);

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

		return rcp;
	}	
	
	
////method to get bank vou no for account package bank receipt voucher
	public int getVouNoRcp(int depo,int div,int year,int bkcd,String dbcr,int cmpdepo)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		PreparedStatement ps2 = null;
		ResultSet rst2=null;
		//PreparedStatement ps3 = null;
		//ResultSet rst3=null;

		
		Connection con=null;
		int i=0;
		int ii=0;
		int sdiv=div;
		if (div==40)
			sdiv=1;
		try {
			con=ConnectionFactory.getConnection();
			String query1 ="select IFNULL(max(vou_no),0) from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd1=? and vdbcr=? and  ifnull(del_tag,'')<>'D' "; 
			
			String query2 ="select IFNULL(max(vou_no),0) from ledfile where fin_year=? and  div_code=4 and sub_div=? and vdepo_code=? and vbook_cd1=? and vdbcr=? and  ifnull(del_tag,'')<>'D' ";

			//String query3 ="select IFNULL(max(vou_no),0) from ledfile where fin_year=? and  div_code=? and sub_div=1 and vdepo_code=? and vbook_cd1=? and vdbcr=? and  ifnull(del_tag,'')<>'D' ";

			
			con.setAutoCommit(false);

			
			
			ps2 =con.prepareStatement(query2);
			ps2.setInt(1, year);
			ps2.setInt(2, sdiv); // sub div
			ps2.setInt(3, cmpdepo);
			ps2.setInt(4, bkcd);
			ps2.setString(5, dbcr);

			rst2 =ps2.executeQuery();

			if(rst2.next())
			{
				ii =rst2.getInt(1); 
			}
			rst2.close();

			
			
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setInt(4, bkcd);
			ps1.setString(5, dbcr);

			rst =ps1.executeQuery();

			if(rst.next())
			{
				i =rst.getInt(1); 
			}

			if (ii>i)
				i=ii;
			
			/*ps3 =con.prepareStatement(query3);
			ps3.setInt(1, year);
			ps3.setInt(2, 9); // co mkt div
			ps3.setInt(3, depo);
			ps3.setInt(4, bkcd);
			ps3.setString(5, dbcr);

			rst3 =ps3.executeQuery();

			if(rst3.next() && sdiv==1)
			{
				if(rst3.getInt(1)>i)
					i =rst3.getInt(1); 
			}
			rst3.close();*/

			
		
				
			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();
			ps2.close();
			//ps3.close();

		} catch (SQLException ex) {System.out.println("error hai "+ex);
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in BankVouDAO.getVouNo " + ex);
		i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(ps2 != null){ps2.close();}
				if(rst2 != null){rst2.close();}
				//if(ps3 != null){ps2.close();}
				//if(rst3 != null){rst2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 

	// New method for Bank receipt voucher Search (with Starting date and Ending Date)
	public Vector getVouList22(Date vdate,Date edate,int depo_code,int div,int bkcd,int subdiv)
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
					"f.vnart_1,f.vamount,f.chq_no,f.chq_date,f.sub_div from ledfile as f "+
					"where  f.div_code=? and f.vdepo_code=? and "+ 
					"f.vbook_cd1=? and f.vou_date between ? and ? and vac_code<>? and f.sub_div=? and f.vdbcr='CR' and  ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,bkcd); 
			ps2.setDate(4,new java.sql.Date(vdate.getTime())); 
			ps2.setDate(5,new java.sql.Date(edate.getTime())); 
			ps2.setString(6,code); 
			ps2.setInt(7,subdiv); 

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
				rcp.setChq_no(rs.getString(8));
				rcp.setChq_date(rs.getDate(9));
				rcp.setChq_amt(rs.getDouble(7));
				rcp.setVouno(rs.getInt(10));
				
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail2 DateWiseSearch Bank Receipt Voucher " + ex);

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
	
	
	public Vector getVouDetail22HO(Date vdate,Date edate,int depo_code,int div,int fyear,int bkcd,int subdiv)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		Vector c=null;
		String code=null;
		Vector v=null;
		try {


			code="040"+depo_code+"01";
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.vac_code,f.name," +
					" f.vnart_1,f.vamount,f.chq_no,f.chq_date,f.sub_div,f.serialno from ledfile as f "+
					" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
					" f.vbook_cd1=? and f.vou_date between ? and ? and f.vac_code<>? and f.sub_div=? and f.vdbcr='CR' and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  
			//" f.vbook_cd1=? and f.vou_no=? and f.vdbcr='CR' and f.vac_code<>? and f.sub_div=? and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  

			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setDate(5,new java.sql.Date(vdate.getTime())); 
			ps2.setDate(6,new java.sql.Date(edate.getTime())); 
			ps2.setString(7,code); 
			ps2.setInt(8,subdiv); 

			rs= ps2.executeQuery();

			boolean first=true;
			Vector row = null;
			Vector col = null;
			String wname="";
			int vno=0;
			v=new Vector();
			while (rs.next())
			{
				if (rs.getInt(2)!=vno && !first)
				{
					first=true;
					rcp.setVdetail(row);
					
				}
					
				if(first)
				{
					vno=rs.getInt(2);
					wname=rs.getString(5);
					rcp= new RcpDto();
					rcp.setVou_no(rs.getInt(2));
					rcp.setVou_date(rs.getDate(3));
					rcp.setVac_code(rs.getString(4));
					rcp.setParty_name(rs.getString(5));
					rcp.setVnart1(rs.getString(6));
					rcp.setChq_no(rs.getString(8));
					rcp.setChq_date(rs.getDate(9));
					rcp.setChq_amt(rs.getDouble(7));
					rcp.setVouno(rs.getInt(10));
					rcp.setVdetail(row);
					
					first=false;
					row = new Vector();
				    c= new Vector();
					if ((depo_code==11 || depo_code==3 || depo_code==12) && bkcd==21)
						c.addElement(rs.getString(1)+"A7"+String.format("%04d",rs.getInt(2)));//concat inv_no
					else
						c.addElement(rs.getString(1)+"A"+String.format("%05d",rs.getInt(2)));//concat inv_no
					c.addElement(rcp.getParty_name());//party name
					c.addElement(rcp);
					v.add(c);
					
					
				}
				   
			    col = new Vector();
				    col.add(false);
				    col.add(rs.getString(4));
				    col.add(rs.getString(5));
				    col.add(rs.getString(6));
				    col.add(rs.getDouble(7));
				    col.add(rs.getInt(11));   // serial no
				    row.add(col);

			    
				
			} // end of while

			if (rcp!=null)
			{
				rcp.setVdetail(row);
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail2 Bank Receipt Voucher " + ex);

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
	

	
////method to check voucher no in sales and account pacakge
	public int checkVouNo(int depo,int div,int year,int bkcd,String dbcr,int cmpdepo,int vouno)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		int sdiv=div;
		try {
			con=ConnectionFactory.getConnection();
			String query1 ="select vou_no from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd=? and vdbcr=? " +
			"and vou_no=? and  ifnull(del_tag,'')<>'D' "; 
			
			
			con.setAutoCommit(false);

			
			
			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, depo);
			ps1.setInt(4, bkcd);
			ps1.setString(5, dbcr);
			ps1.setInt(6, vouno);

			rst =ps1.executeQuery();

			if(rst.next())
			{
			   	i =rst.getInt(1); 
			}

			
				
			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();
			 

		} catch (SQLException ex) {System.out.println("error hai "+ex);
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in BankVouDAO.getVouNo " + ex);
		i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 

	
////method to get bank list for ho entry
	public Vector getBookList(int depo,int div,int fyear)
	{
		PreparedStatement bookps = null;
		ResultSet bookrs=null;

		PreparedStatement ps = null;
		ResultSet rs=null;

		Connection con=null;
		Vector book=null;
		String cmplo="";
		try {
			con=ConnectionFactory.getConnection();
			
			
//			String booklst = "select book_cd,book_nm,book_tp,ifnull(head_code,0),ifnull(bank_acno,''),book_abvr from bookmast where  div_code=? and depo_code=? and  book_cd in(20,21,22)   ";

			String booklst = "select book_cd,book_nm,book_tp,ifnull(head_code,0),ifnull(bank_acno,''),book_abvr from bookmast where  div_code=? and depo_code=? and  book_cd in(20,21,22)   ";

			String cmpQ= "select inv_yr,inv_lo from cmpinv where fin_year=? and div_code=? and depo_code=?  ";

			
			con.setAutoCommit(false);


			
			
			ps = con.prepareStatement(cmpQ);
			ps.setInt(1, fyear);
			ps.setInt(2, div);
			ps.setInt(3, depo);
		
			rs= ps.executeQuery();
		
			if (rs.next())
			{
	              cmplo=rs.getString(2);
			}	
			
			rs.close();
			ps.close();
			
			
			
			bookps = con.prepareStatement(booklst);
			bookps.setInt(1, div);
			bookps.setInt(2, depo);
		
			bookrs= bookps.executeQuery();

		book=new Vector();
		BookDto bkdt=null;
		
		while (bookrs.next())
		{
			bkdt = new BookDto();
			bkdt.setBook_code(bookrs.getInt(1));
			bkdt.setBook_name(bookrs.getString(2));
			bkdt.setBook_tp(bookrs.getString(3));
			bkdt.setHead_code(bookrs.getInt(4));
			bkdt.setBank_acno(bookrs.getString(5));
			bkdt.setBook_abvr(cmplo);
			book.add(bkdt);

		}	
			
				
			con.commit();
			con.setAutoCommit(true);
			bookrs.close();
			bookps.close();

		} catch (SQLException ex) {System.out.println("error hai "+ex);
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in BankVouDAO.getBookList " + ex);
		}
		finally {
			try {

				if(bookps != null){bookps.close();}
				if(bookrs != null){bookrs.close();}
				if(ps != null){ps.close();}
				if(rs != null){rs.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return book;
	}	 
	
	
	//// method to get HO Ppayment vou no for account package 
	public int getVouNoHO(int depo,int div,int year,int bkcd, int brncode)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		String query1=null; 
		try {
			con=ConnectionFactory.getConnection();
			if (brncode==99)
			{
				query1 ="select ifnull(max(vou_no),0) from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd1=? " +
						"and vter_cd=? and  ifnull(del_tag,'')<>'D' ";
				brncode=depo;
			}
			else
			{

				query1 ="select ifnull(max(vou_no),0) from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and mnth_code=? and  ifnull(del_tag,'')<>'D' ";
				
			}
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, 8);
			ps1.setInt(4, bkcd);
			ps1.setInt(5, brncode);
			
			rst =ps1.executeQuery();

			if(rst.next()){
				i =rst.getInt(1); 
			}

			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getVouNo " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 
		

	
	
	//// method to add record in led file (bank voucher HO [Direct Payment])	
	public int addLedHODirect(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement chqps=null;
		RcpDto rcp=null;
		ChequeDto chque=null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,exp_code,sub_code,vnart_1,name,vamount,vdbcr,chq_no,chq_date,chq_amt,bill_no,bill_date,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,vnart_2,sub_div,tds_amt,bill_amt,cr_amt,sertax_amt,sertax_billper,sertax_per,indicator,vter_cd,tax_type)" +
					" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			String chq="update chqmast set vou_no=?,vou_Date=?,party_code=?,chq_amt=?,chq_date=?,favourof=?," +
					"invno=?,modified_by=?,modified_date=?,issued=? " +
					" where div_code=? and depo_code=? and bank_code=? and cheque_no=? and dbcr='DR' ";			

			chqps=con.prepareStatement(chq);
			ledps=con.prepareStatement(led);

			System.out.println("SIZE OF LIST "+cList.size());
			for(int j=0;j<cList.size();j++)
			{
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

				ledps.setString(13, rcp.getParty_name());
				ledps.setDouble(14, rcp.getVamount());
				ledps.setString(15, "DR");
				ledps.setString(16, rcp.getChq_no());

				if (rcp.getChq_date()!=null)
					ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(17, null);

				ledps.setDouble(18, rcp.getChq_amt());
				ledps.setString(19, rcp.getBill_no());
				if (rcp.getBill_date()!=null)
					ledps.setDate(20, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(20, null);

				ledps.setString(21, String.valueOf(rcp.getTp()));
				ledps.setInt(22, rcp.getCreated_by());
				ledps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(24, rcp.getSerialno());
				ledps.setInt(25, rcp.getFin_year());
				ledps.setInt(26, rcp.getMnth_code());
				ledps.setString(27, rcp.getVnart2());
				ledps.setInt(28, 1); // sub div
				ledps.setDouble(29, rcp.getTds_amt());
				ledps.setDouble(30, rcp.getBill_amt());
				ledps.setDouble(31, rcp.getCr_amt());  // taxable service tax
				ledps.setDouble(32, rcp.getSertax_amt());
				ledps.setDouble(33, rcp.getSertax_billper());
				ledps.setDouble(34, rcp.getSertax_per());
				ledps.setString(35, rcp.getInt_code()); // indicator (Month no)
				ledps.setInt(36, 0); // branch code
				if(rcp.getVdepo_code()==8)
					ledps.setInt(36, rcp.getVter_cd()); // branch code
				ledps.setString(37, rcp.getTaxtype()); // chqtype other/HO

				

				i=ledps.executeUpdate();
			}

			
			
/*			int size=rcp.getChqlist().size();
			for(int j=0;j<size;j++)
			{
				
				chque= (ChequeDto)rcp.getChqlist().get(j);
				chqps.setInt(1,chque.getVou_no());
				chqps.setDate(2,new java.sql.Date(chque.getVou_date().getTime()));
				chqps.setString(3, chque.getParty_code());
				chqps.setDouble(4,chque.getChq_amt());
				chqps.setDate(5,new java.sql.Date(chque.getChq_date().getTime()));
				chqps.setString(6, chque.getFavourof());
				chqps.setString(7, chque.getInvno());
				chqps.setInt(8, chque.getModified_by());
				chqps.setDate(9, new java.sql.Date(chque.getModified_date().getTime()));
				chqps.setString(10, "Y");
				chqps.setInt(11, chque.getDiv_code());
				chqps.setInt(12, chque.getDepo_code());
				chqps.setInt(13, chque.getBank_code());
				chqps.setInt(14, chque.getCheque_no());
				i=chqps.executeUpdate();
				
				
			}				
*/			
			//voucher no 
			i=rcp.getVou_no();
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			chqps.close();

		} catch (SQLException ex) {
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
				if(chqps != null){chqps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}		

	public RcpDto getVouDetailHODirect(int vou,int depo_code,int wdepo,int fyear,int bkcd,String tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int maxSerialNo=0;
		int div = 4;
		String query2=null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


			if(wdepo==8)
			{
				query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
				"s.mac_name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno," +
				"f.chq_no,if(f.chq_date='0000-00-00',null,f.chq_date),f.chq_amt,f.bill_no,f.bill_date,ifnull(f.tds_amt,0),ifnull(f.bill_amt,f.vamount)," +
				"ifnull(f.cr_amt,0),ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0),ifnull(f.tax_type,'H') from ledfile as f "+
				"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D'"+ 
				",partyfst as p "+
				"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
				"f.vbook_cd1=? and f.vou_no=? and f.vdbcr='DR' and f.tp='D' and f.indicator=? and f.sub_div=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' order by f.vou_no,serialno ";
			}
			else 
			{
				query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
				"s.mac_name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno," +
				"f.chq_no,if(f.chq_date='0000-00-00',null,f.chq_date),f.chq_amt,f.bill_no,f.bill_date,ifnull(f.tds_amt,0),ifnull(f.bill_amt,f.vamount)," +
				"ifnull(f.cr_amt,0),ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0),ifnull(f.tax_type,'H') from ledfile as f "+
				"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D'"+ 
				",partyfst as p "+
				"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=8 and f.vter_cd=? and "+ 
				"f.vbook_cd1=? and f.vou_no=? and f.vdbcr='DR' and f.tp='D' and f.sub_div=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' order by f.vou_no,serialno ";
			}


			String maxSrial = "select ifnull(max(serialno),0) from ledfile where fin_year=? and div_code=? and vdepo_code=? and "+ 
			" vbook_cd1=? and vou_no=? and vdbcr='DR' and tp=?  ";

			ps2 = con.prepareStatement(maxSrial);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,vou); 
			ps2.setString(6,"D"); 
			
			rs= ps2.executeQuery();
			if(rs.next())
			{
				maxSerialNo = rs.getInt(1);
			}
			rs.close();
			ps2.close();
			rs=null;
			ps2=null;
			

			/////////////////////////////////
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,fyear);
			ps2.setInt(4,div);
			ps2.setInt(5, wdepo);
			ps2.setInt(6,bkcd); 
			ps2.setInt(7,vou); 
			if(wdepo==8)
			{
				ps2.setString(8,tp); 
				ps2.setInt(9,div);
				ps2.setInt(10, depo_code);
			}
			if(wdepo!=8)
			{
				ps2.setInt(8,div);
				ps2.setInt(9, depo_code);
			}
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
					rcp.setChq_no(rs.getString(15));
					rcp.setChq_date(rs.getDate(16));
					rcp.setChq_amt(rs.getDouble(17));
					rcp.setVnart2(rs.getString(9));
					rcp.setSerialno(maxSerialNo);
					if(rs.getString(26).equalsIgnoreCase("R"))
						rcp.setPay_mode(1);  // rtgs
					else
						rcp.setPay_mode(0); // ho chq book

				}






				/// Table detail set here
				colum = new Vector();
				colum.addElement(false);   // del tag
				colum.addElement(rs.getString(5));   // exp code
				colum.addElement(rs.getString(6));  //exp  name
				colum.addElement(rs.getString(8));  //sub_head
				colum.addElement(rs.getString(18));  //bill_no
				if (rs.getDate(19)!=null)
				{
				   try
					{
					  colum.addElement(sdf.format(rs.getDate(19)));  //bill_date
					}catch(Exception e)
					{
						colum.addElement("");  //bill_date
					}
				}
				else
				{
					colum.addElement("");  //bill_date
				}

				colum.addElement(rs.getString(10)); // narration
				colum.addElement(nf.format(rs.getDouble(21)));  //billamt
				colum.addElement(rs.getInt(12));  //vbook_cd
				colum.addElement(rs.getInt(13));  // vgrpcode
				colum.addElement(rs.getString(7));  // subcode 
				colum.addElement(rs.getInt(14));  // serialno
				colum.addElement(nf.format(rs.getDouble(22)));  //taxable
				colum.addElement(nf.format(rs.getDouble(23)));  //servicetax
				colum.addElement(nf.format(rs.getDouble(20)));  //tds
				colum.addElement(nf.format(rs.getDouble(11)));  // balance amountvamount
				colum.addElement(nf.format(rs.getDouble(24)));  //bill_per
				colum.addElement(nf.format(rs.getDouble(25)));  //servicetax_per
				row.addElement(colum);
				total+=rs.getDouble(11);


			}

			if (rcp!=null)
			{
				rcp.setVamount(total);
				rcp.setVdetail(row);
				System.out.println("aftger hsis fill ref data is callledsssss SIZE HAI DAO MEIN "+row.size());
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		

	public int cancelVoucher(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;
		PreparedStatement outps=null;
		PreparedStatement chqps=null;


		RcpDto rcp=null;

		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String led="update ledfile set del_tag=?,modified_by=?,modified_date=?,vamount=?,vnart_1=?  " +
					"where div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=?  and vou_date=? and vdbcr='DR' " ;

			String rp="update rcpfile set del_tag=?,modified_by=?,modified_date=? ,vamount=? " +
					"where div_code=? and vdepo_code=? and vbook_cd=? " +
					"and vou_no=? and vou_date=?";

			String out="update rcpfile set chq_amt=chq_amt-?,modified_by=?,modified_date=?,vamount=?  " +
					"where div_code=? and vdepo_code=? and vbook_cd in (60,61) " +
					"and vou_no=? and vou_date=? and ifnull(del_tag,'')<>'D' ";


			
			
			ledps=con.prepareStatement(led);
			rcpps=con.prepareStatement(rp);
			outps=con.prepareStatement(out);

			int size = cList.size();
			
			for(int j=0;j<size;j++)
			{
				rcp = (RcpDto) cList.get(j);

				
				rcpps.setString(1, "C");
				rcpps.setInt(2, rcp.getModified_by());
				rcpps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));
				rcpps.setDouble(4,0.00);
				// where clause
				rcpps.setInt(5, rcp.getDiv_code());
				rcpps.setInt(6, rcp.getVdepo_code());
				rcpps.setInt(7, rcp.getVbook_cd1());
				rcpps.setInt(8, rcp.getVou_no());
				rcpps.setDate(9, new java.sql.Date(rcp.getVou_date().getTime()));
				i=rcpps.executeUpdate();


				// outstanding file record updated 
				outps.setDouble(1,rcp.getVamount() );
				outps.setInt(2, rcp.getModified_by());
				outps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));
				outps.setDouble(4,0.00);

				// where clause
				outps.setInt(5, rcp.getDiv_code());
				outps.setInt(6, rcp.getVdepo_code());
				outps.setInt(7, rcp.getVouno());
				if (rcp.getVoudt()!=null)
					outps.setDate(8, new java.sql.Date(rcp.getVoudt().getTime()));
				else
					outps.setDate(8, null);
					
				i=outps.executeUpdate();
			}


			ledps.setString(1, "C");
			ledps.setInt(2, rcp.getModified_by());
			ledps.setDate(3, new java.sql.Date(rcp.getModified_date().getTime()));
			ledps.setDouble(4,0.00);
			ledps.setString(5, "Voucher Cancelled");


			// where clause
			ledps.setInt(6, rcp.getDiv_code());
			ledps.setInt(7, rcp.getVdepo_code());
			ledps.setInt(8, rcp.getVbook_cd1());
			ledps.setInt(9, rcp.getVou_no());
			ledps.setDate(10, new java.sql.Date(rcp.getVou_date().getTime()));

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

	public boolean checkVouNoHO(int vou,int depo_code,int div,int fyear,int bkcd,int mnth_code)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		int maxSerialNo=0;
		boolean found=false;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String maxNo = "select ifnull(max(vou_no),0) from ledfile where fin_year=? and div_code=? and vdepo_code=? and "+ 
			" vbook_cd1=? and vdbcr='DR' and mnth_code=? and ifnull(del_tag,'') <>'D' ";

			
			
			String maxSrial = "select vou_no from ledfile where fin_year=? and div_code=? and vdepo_code=? and "+ 
			" vbook_cd1=? and vou_no=? and vdbcr='DR' and mnth_code=? and ifnull(del_tag,'') <>'D' ";

			
			ps2 = con.prepareStatement(maxNo);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,mnth_code); 
			
			rs= ps2.executeQuery();
			if(rs.next())
			{
				maxSerialNo = rs.getInt(1);
			}
			rs.close();
			ps2.close();

			rs=null;
			ps2=null;
			
			
			
			ps2 = con.prepareStatement(maxSrial);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,vou); 
			ps2.setInt(6,mnth_code); 

			rs= ps2.executeQuery();
			if(rs.next())
			{
				
				if (maxSerialNo > rs.getInt(1))
					found=true;
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return found;
	}		

	//// method to add record in led file (bank Receipt voucher [Direct/Creditors])	
	public int addTDS(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +  //6
					"vou_date,vac_code,name,vamount,vdbcr,bill_no,bill_date,bill_amt,tds_amt,sub_code, " +  //16
					"Created_by,Created_date,serialno,fin_year,mnth_code,sub_div,vou_yr,vmsr_cd,taxable_amt) " + //24
					" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


			
			ledps=con.prepareStatement(led);
			ledps.setInt(1, rcp.getDiv_code());
			ledps.setInt(2, rcp.getVdepo_code());
			ledps.setInt(3, rcp.getVbook_cd());
			ledps.setInt(4, rcp.getVbook_cd1());
			ledps.setString(5, rcp.getVou_lo());
			ledps.setInt(6, rcp.getVou_no());
			ledps.setDate(7, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setString(8, rcp.getVac_code());
			ledps.setString(9, rcp.getParty_name());  // paid to
			ledps.setDouble(10, rcp.getVamount());
			ledps.setString(11, "DR");
			ledps.setString(12, rcp.getChq_no());
			ledps.setDate(13, new java.sql.Date(rcp.getChq_date().getTime()));
			ledps.setDouble(14, rcp.getBill_amt());
			ledps.setDouble(15, rcp.getTds_amt());
			ledps.setString(16, rcp.getVnart2());  // ref no
			ledps.setInt(17, rcp.getCreated_by());
			ledps.setDate(18, new java.sql.Date(rcp.getCreated_date().getTime()));
			ledps.setInt(19, rcp.getSerialno());
			ledps.setInt(20, rcp.getFin_year());
			ledps.setInt(21, rcp.getMnth_code());
			ledps.setInt(22, 1); // div code of mf/tf/div
			ledps.setInt(23, rcp.getVou_yr()); 
			ledps.setInt(24, rcp.getVmsr_cd());  // pur ref no 
			ledps.setDouble(25, rcp.getVamount()); // taxable_amt


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
			System.out.println("-------------Exception in SQLRcpDAO.addDebitNote Voucher" + ex);
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
	
	public RcpDto getTdsDetail(int vou,int depo_code,int div,int fyear,int bkcd)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		String code=null;
		try {


			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String query2 ="select f.vou_lo,f.vou_no,f.vou_date,f.vac_code,f.name," +
					" f.vnart_1,f.bill_amt,f.bill_no,f.bill_date,f.sub_code,f.tds_amt,ifnull(f.vmsr_cd,0) from ledfile as f "+
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
				rcp.setChq_no(rs.getString(8));
				rcp.setChq_date(rs.getDate(9));
				rcp.setVnart2(rs.getString(10));
				rcp.setTds_amt(rs.getDouble(11));
				rcp.setVmsr_cd(rs.getInt(12));  // purchase ref no
				
				
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

	public Vector getTdsDetail1(int fyear, Date vdate,int depo_code,int div,int bkcd)
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
					" f.vnart_1,f.bill_amt,f.bill_no,f.bill_date,f.sub_code,f.tds_amt,ifnull(f.vdist_cd,0) from ledfile as f "+
					" where  f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
					" f.vbook_cd1=? and f.vou_date=? and f.vdbcr='DR' and ifnull(f.del_tag,'')<>'D'  order by f.vou_no ";  


			ps2 = con.prepareStatement(query2);
			
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setDate(5,new java.sql.Date(vdate.getTime())); 

			rs= ps2.executeQuery();

			v = new Vector();
			String inv=null;

			while (rs.next())
			{
				inv = rs.getString(2);

				rcp= new RcpDto();
				rcp.setVou_no(rs.getInt(2));
				rcp.setVou_date(rs.getDate(3));
				rcp.setVac_code(rs.getString(4));
				rcp.setParty_name(rs.getString(5));
				rcp.setVnart1(rs.getString(6));
				rcp.setChq_amt(rs.getDouble(7));
				rcp.setChq_no(rs.getString(8));
				rcp.setChq_date(rs.getDate(9));
				rcp.setVnart2(rs.getString(10));
				rcp.setTds_amt(rs.getDouble(11));
				rcp.setVdist_cd(rs.getInt(12));
				
	
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail2 Bank Receipt Voucher " + ex);

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

	
	
	public int updateTDS(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



			String led="update  ledfile set vou_date=?,vac_code=?,sub_code=?," +
					"name=?,vamount=?,bill_no=?,bill_date=?,bill_amt=?,tds_amt=?," +
					"modified_by=?,modified_date=?,vmsr_cd=?,taxable_amt=? " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR'";


			ledps=con.prepareStatement(led);

			ledps.setDate(1, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setString(2, rcp.getVac_code());
			ledps.setString(3, rcp.getVnart2());  // ref no
			ledps.setString(4, rcp.getParty_name());
			ledps.setDouble(5, rcp.getVamount());
			ledps.setString(6, rcp.getChq_no());
			if(rcp.getChq_date()!=null)
				ledps.setDate(7, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(7, null);
			ledps.setDouble(8, rcp.getBill_amt());
			ledps.setDouble(9, rcp.getTds_amt());
			ledps.setInt(10, rcp.getModified_by());
			ledps.setDate(11, new java.sql.Date(rcp.getModified_date().getTime()));
			ledps.setInt(12, rcp.getVmsr_cd());
			ledps.setDouble(13, rcp.getVamount());

			// where clause
			ledps.setInt(14, rcp.getFin_year());
			ledps.setInt(15, rcp.getDiv_code());
			ledps.setInt(16, rcp.getVdepo_code());
			ledps.setInt(17, rcp.getVbook_cd1());
			ledps.setInt(18, rcp.getVou_no());


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
			System.out.println("-------------Exception in SQLRcpDAO.updateTDS " + ex);
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
	
	
	
	
	public int deleteTDS(int div,int depo,int vou, int fyear,int uid,int bkcd)
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
			System.out.println("-------------Exception in SQLRcpDAO.deleteTDS " + ex);
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


	public java.sql.Date setSqlDate(Date javadate)
	{
		      java.sql.Date sqlDate = null;
		      try {
				sqlDate = new java.sql.Date(javadate.getTime());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				sqlDate=null;
			}
		      return sqlDate;  
	}

	
	public int getVouNoHOCash(int depo,int div,int year,int bkcd, int brncode,int type)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		int i=0;
		String query1=null; 
		try {
			con=ConnectionFactory.getConnection();

				query1 ="select ifnull(max(vou_no),0) from ledfile where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and mnth_code=? and vter_cd=? and  ifnull(del_tag,'')<>'D' ";
				
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, div);
			ps1.setInt(3, 8);
			ps1.setInt(4, bkcd);
			ps1.setInt(5, brncode);
			ps1.setInt(6, type);
			rst =ps1.executeQuery();

			if(rst.next()){
				i =rst.getInt(1); 
			}

			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getVouNo " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 
	
  



	
	
	//// method to add record in led file (Debit Note GST for Bank Charges Sales Package )	
	public int addDebitNoteGST(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			
			String query1 ="insert into invfst (Div_code,Depo_code, Doc_type, Party_code, " +
			" Inv_no, Inv_date,disc_2,bill_amt,Remark," +
			" fin_year, mnth_code, created_by, created_date, " +
			" mr_cd, stat_cd, area_cd, regn_cd, terr_cd, dist_cd, mkt_year,inv_lo,inv_yr,prt_type," +
			"inv_type,discount_round ,interest_round,prod_type,taxable1,ltax1_per,ltax1_amt,ltax2_per,ltax2_amt,ltax3_per,ltax3_amt) "+
			" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			
			String query2 =" insert into invsnd (div_code,sDepo_code, sDoc_type, sInv_no," +
			" sinv_dt,spinv_no,spinv_dt,ndays,int_amt,dd_date,samnt,sprt_cd,serialno," +
			" fin_year,mnth_code,created_by,created_date, " +
			" smr_cd,stat_cd,area_cd,inv_dsm,terr_cd,inv_dist,sentry_dt,mkt_year,sinv_lo,sinv_yr,srate_hos," +
			" stax_type,stax_cd1,stax_cd2,stax_cd3,taxable_amt,cgst_amt,sgst_amt,igst_amt,net_amt) "+
			" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
			"vou_date,vgrp_code,vac_code,exp_code,vnart_1,vnart_2,name,vamount,vdbcr,bill_no,bill_date,tp, " +
			"Created_by,Created_date,serialno,fin_year,mnth_code,sub_div,vou_yr,taxable_amt,ctax_per,stax_per,itax_per," +
			"cgst_amt,sgst_amt,igst_amt,net_amt,vmsr_cd, vstat_cd, varea_cd, vreg_cd, vter_cd, vdist_cd, mkt_year) " +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


			
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
			ledps.setString(10, rcp.getVac_code());
			ledps.setString(11, rcp.getVnart1());
			ledps.setString(12, rcp.getVnart2());

			ledps.setString(13, rcp.getParty_name());  // paid to
			ledps.setDouble(14, rcp.getVamount());
			ledps.setString(15, "DR");
			ledps.setString(16, rcp.getChq_no());
			if(rcp.getChq_date()!=null)
				ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(17, null);
			
			ledps.setString(18, "D");
			ledps.setInt(19, rcp.getCreated_by());
			ledps.setDate(20, new java.sql.Date(rcp.getCreated_date().getTime()));
			ledps.setInt(21, rcp.getSerialno());
			ledps.setInt(22, rcp.getFin_year());
			ledps.setInt(23, rcp.getMnth_code());
			ledps.setInt(24, 1); // div code of mf/tf/div
			ledps.setInt(25, rcp.getVou_yr()); 
			ledps.setDouble(26, rcp.getTaxable());
			ledps.setDouble(27, rcp.getCtaxper());
			ledps.setDouble(28, rcp.getStaxper());
			ledps.setDouble(29, rcp.getItaxper());
			ledps.setDouble(30, rcp.getCgstamt());
			ledps.setDouble(31, rcp.getSgstamt());
			ledps.setDouble(32, rcp.getIgstamt());
			ledps.setDouble(33, rcp.getVamount());
			ledps.setInt(34,rcp.getVmsr_cd());
			ledps.setInt(35,rcp.getVstat_cd());
			ledps.setInt(36,rcp.getVarea_cd());
			ledps.setInt(37,rcp.getVreg_cd());
			ledps.setInt(38,rcp.getVter_cd());
			ledps.setInt(39,rcp.getVdist_cd());
			ledps.setInt(40,rcp.getMkt_year());

			i=ledps.executeUpdate();


			
			ps=con.prepareStatement(query1);
			ps.setInt(1,rcp.getDiv_code());
			ps.setInt(2, rcp.getVdepo_code());
			ps.setInt(3,51);
			ps.setString(4,rcp.getVac_code());
			ps.setInt(5,rcp.getVou_no());
			ps.setDate(6,new java.sql.Date(rcp.getVou_date().getTime()));
			ps.setDouble(7,0.00);
			ps.setDouble(8,rcp.getVamount());
			ps.setString(9,rcp.getVnart1());
			ps.setInt(10,rcp.getFin_year());
			ps.setInt(11,rcp.getMnth_code());
			ps.setInt(12, rcp.getCreated_by());
			ps.setDate(13,new java.sql.Date(rcp.getCreated_date().getTime()));
			ps.setInt(14,rcp.getVmsr_cd());
			ps.setInt(15,rcp.getVstat_cd());
			ps.setInt(16,rcp.getVarea_cd());
			ps.setInt(17,rcp.getVreg_cd());
			ps.setInt(18,rcp.getVter_cd());
			ps.setInt(19,rcp.getVdist_cd());
			ps.setInt(20,rcp.getMkt_year());
			ps.setString(21, rcp.getVou_lo());
			ps.setInt(22,rcp.getVou_yr());
			ps.setString(23,"2");
			ps.setString(24,"D");
			ps.setDouble(25,0.00);
			ps.setDouble(26,0.00);
			ps.setDouble(25,0.00);
			ps.setDouble(26,0.00);
			ps.setString(27,"N");
			ps.setDouble(28,rcp.getTaxable());
			ps.setDouble(29,rcp.getCtaxper());
			ps.setDouble(30,rcp.getCgstamt());
			ps.setDouble(31,rcp.getStaxper());
			ps.setDouble(32,rcp.getSgstamt());
			ps.setDouble(33, rcp.getItaxper());
			ps.setDouble(34,rcp.getIgstamt());
			i=ps.executeUpdate();
			

			
			ps1=con.prepareStatement(query2);

			ps1.setInt(1, rcp.getDiv_code());
			ps1.setInt(2, rcp.getVdepo_code());
			ps1.setInt(3, 51);
			ps1.setInt(4, rcp.getVou_no());
			ps1.setDate(5, new java.sql.Date(rcp.getVou_date().getTime()));
			ps1.setString(6, rcp.getChq_no());
			if(rcp.getChq_date()!=null)
				ps1.setDate(7, new java.sql.Date(rcp.getChq_date().getTime()));
			else	
				ps1.setDate(7, null);
			ps1.setInt(8, 0);
			ps1.setDouble(9, 0.00); 
			ps1.setDate(10, null);

			ps1.setDouble(11, rcp.getVamount());
			ps1.setString(12, rcp.getVac_code());
			ps1.setInt(13, rcp.getSerialno());
			ps1.setInt(14, rcp.getFin_year());
			ps1.setInt(15,rcp.getMnth_code());
			ps1.setInt(16, rcp.getCreated_by());
			ps1.setDate(17, new java.sql.Date(rcp.getCreated_date().getTime()));
			ps1.setInt(18,rcp.getVmsr_cd());
			ps1.setInt(19,rcp.getVstat_cd());
			ps1.setInt(20,rcp.getVarea_cd());
			ps1.setInt(21,rcp.getVreg_cd());
			ps1.setInt(22,rcp.getVter_cd());
			ps1.setInt(23,rcp.getVdist_cd());
			ps1.setDate(24, null);
			ps1.setInt(25,rcp.getMkt_year());
			ps1.setString(26, rcp.getVou_lo());
			ps1.setInt(27,rcp.getVou_yr());
			ps1.setDouble(28, 0.00); // srate_hos is usered for balance amount on which interest calculated

			ps1.setString(29,"D");
			ps1.setDouble(30, rcp.getCtaxper());
			ps1.setDouble(31, rcp.getStaxper());
			ps1.setDouble(32, rcp.getItaxper());
			ps1.setDouble(33, rcp.getTaxable());
			ps1.setDouble(34, rcp.getCgstamt());
			ps1.setDouble(35, rcp.getSgstamt());
			ps1.setDouble(36, rcp.getIgstamt());
			ps1.setDouble(37, rcp.getNetamt());

			i=ps1.executeUpdate();

			
			
			
			i=rcp.getVou_no();
			
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			ps.close();
			ps1.close();
			

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SQLRcpDAO.addDebitNote Voucher" + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(ps != null){ps.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close Debit Note Voucher "+e);
			}
		}

		return (i);
	}				

	//// method to update record in led file (DEBIT NOTE)	
	public int updateDebitNoteGST(RcpDto rcp)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



			String led="update  ledfile set vnart_1=?,vamount=?,modified_by=?,modified_date=?," +
					"taxable_amt=?,cgst_amt=?,sgst_amt=?,igst_amt=?,net_amt=?," +
					"vmsr_cd=?, vstat_cd=?, varea_cd=?, vreg_cd=?, vter_cd=?, vdist_cd=?, mkt_year=?,vnart_2=? " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR' and ifnull(del_tag,'')<>'D' ";

			
			String query1 ="update invfst set bill_amt=?,Remark=?," +
			" taxable1=?,ltax1_amt=?,ltax2_amt=?,ltax3_amt=? " +
			" where fin_year=? and div_code=? and depo_code=? and doc_type=? " +
			" and inv_no=? and inv_date=? and ifnull(del_tag,'')<>'D' ";

			
			
			String query2 =" update invsnd set samnt=? , " +
			" taxable_amt=?,cgst_amt=?,sgst_amt=?,igst_amt=?,net_amt=?,spinv_no=?,spinv_dt=? "+
			" where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? " +
			" and sinv_no=? and sinv_dt=? and ifnull(del_tag,'')<>'D' ";

			

			ledps=con.prepareStatement(led);

			
			
			ledps.setString(1, rcp.getVnart1());
			ledps.setDouble(2, rcp.getVamount());
			ledps.setInt(3, rcp.getModified_by());
			ledps.setDate(4, new java.sql.Date(rcp.getModified_date().getTime()));
			ledps.setDouble(5, rcp.getTaxable());
			ledps.setDouble(6, rcp.getCgstamt());
			ledps.setDouble(7, rcp.getSgstamt());
			ledps.setDouble(8, rcp.getIgstamt());
			ledps.setDouble(9, rcp.getNetamt());
			ledps.setInt(10,rcp.getVmsr_cd());
			ledps.setInt(11,rcp.getVstat_cd());
			ledps.setInt(12,rcp.getVarea_cd());
			ledps.setInt(13,rcp.getVreg_cd());
			ledps.setInt(14,rcp.getVter_cd());
			ledps.setInt(15,rcp.getVdist_cd());
			ledps.setInt(16,rcp.getMkt_year());
			ledps.setString(17, rcp.getVnart2());
			// where clause
			ledps.setInt(18, rcp.getFin_year());
			ledps.setInt(19, rcp.getDiv_code());
			ledps.setInt(20, rcp.getVdepo_code());
			ledps.setInt(21, rcp.getVbook_cd1());
			ledps.setInt(22, rcp.getVou_no());

			i=ledps.executeUpdate();

			///   invfst update method

			
			ledps=null;
			ledps=con.prepareStatement(query1);

			ledps.setDouble(1, rcp.getNetamt());
			ledps.setString(2, rcp.getVnart1());
			ledps.setDouble(3, rcp.getTaxable());
			ledps.setDouble(4, rcp.getCgstamt());
			ledps.setDouble(5, rcp.getSgstamt());
			ledps.setDouble(6, rcp.getIgstamt());

			// where clause
			ledps.setInt(7, rcp.getFin_year());
			ledps.setInt(8, rcp.getDiv_code());
			ledps.setInt(9, rcp.getVdepo_code());
			ledps.setInt(10, 51);
			ledps.setInt(11, rcp.getVou_no());
			ledps.setDate(12, new java.sql.Date(rcp.getVou_date().getTime()));


			i=ledps.executeUpdate();


			
			///   invsnd update method
			ledps=null;
			ledps=con.prepareStatement(query2);

			ledps.setDouble(1, rcp.getVamount());
			ledps.setDouble(2, rcp.getTaxable());
			ledps.setDouble(3, rcp.getCgstamt());
			ledps.setDouble(4, rcp.getSgstamt());
			ledps.setDouble(5, rcp.getIgstamt());
			ledps.setDouble(6, rcp.getNetamt());
			ledps.setString(7, rcp.getChq_no());
			if(rcp.getChq_date()!=null)
				ledps.setDate(8, new java.sql.Date(rcp.getChq_date().getTime()));
			else
				ledps.setDate(8, null);

			// where clause
			ledps.setInt(9, rcp.getFin_year());
			ledps.setInt(10, rcp.getDiv_code());
			ledps.setInt(11, rcp.getVdepo_code());
			ledps.setInt(12, 51);
			ledps.setInt(13, rcp.getVou_no());
			ledps.setDate(14, new java.sql.Date(rcp.getVou_date().getTime()));
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

	
	
	//// method to update record in led file (Bank Receipt voucher Direct/Creditors)	
	public int deleteLedGST(int div,int depo,int vou, int fyear,int uid,int bkcd,Date vdate)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement fstps=null;
		PreparedStatement sndps=null;




		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);




			String fst="update invfst set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and depo_code=? and doc_type=? " +
					"and inv_no=? and inv_date=? and ifnull(del_tag,'')<>'D'  ";

			String snd="update invsnd set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? " +
					"and sinv_no=? and sinv_dt=? and ifnull(del_tag,'')<>'D'  ";

			String 	led="update ledfile set del_tag=?,modified_by=?,modified_date=curdate()  " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR'  ";



			
			
			ledps=con.prepareStatement(led);
			fstps=con.prepareStatement(fst);
			sndps=con.prepareStatement(snd);



			ledps.setString(1, "D");
			ledps.setInt(2, uid);
			// where clause
			ledps.setInt(3,fyear); 
			ledps.setInt(4,div);
			ledps.setInt(5, depo);
			ledps.setInt(6, bkcd);
			ledps.setInt(7, vou);

			i=ledps.executeUpdate();
			
//////////////////// fstfile ////////////////////
			fstps.setString(1, "D");
			fstps.setInt(2, uid);
			// where clause
			fstps.setInt(3,fyear); 
			fstps.setInt(4,div);
			fstps.setInt(5, depo);
			fstps.setInt(6, 51);
			fstps.setInt(7, vou);
			fstps.setDate(8, new java.sql.Date(vdate.getTime()));

			i=fstps.executeUpdate();

			////////////////////sndfile ////////////////////
			sndps.setString(1, "D");
			sndps.setInt(2, uid);
			// where clause
			sndps.setInt(3,fyear); 
			sndps.setInt(4,div);
			sndps.setInt(5, depo);
			sndps.setInt(6, 51);
			sndps.setInt(7, vou);
			sndps.setDate(8, new java.sql.Date(vdate.getTime()));

			i=sndps.executeUpdate();

			
			con.commit();
			con.setAutoCommit(true);
			ledps.close();
			fstps.close();
			sndps.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
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
				if(fstps != null){fstps.close();}
				if(sndps != null){sndps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	// direct method 10/02/2018
	public int addLedGST(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		RcpDto rcp=null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,exp_code,sub_code,vnart_1,name,vamount,vdbcr,chq_no,chq_date,chq_amt,bill_no,bill_date,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,vnart_2,sub_div,tds_amt,bill_amt,cr_amt,sertax_amt,sertax_billper,sertax_per," +
					" taxable_amt,ctax_per, stax_per, itax_per, "+
					" cgst_amt, sgst_amt, igst_amt,net_amt,addl_taxper,addl_taxamt,vreg_cd,varea_cd,vmsr_cd,gst_no,hsn_code "+						
					") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			// 040+depo+01

			ledps=con.prepareStatement(led);

			for(int j=0;j<cList.size();j++)
			{
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

				ledps.setString(13, rcp.getParty_name());
				ledps.setDouble(14, rcp.getVamount());
				ledps.setString(15, "DR");
				ledps.setString(16, rcp.getChq_no());

				if (rcp.getChq_date()!=null)
					ledps.setDate(17, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(17, null);

				ledps.setDouble(18, rcp.getChq_amt());
				ledps.setString(19, rcp.getBill_no());
				if (rcp.getBill_date()!=null)
					ledps.setDate(20, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(20, null);

				ledps.setString(21, String.valueOf(rcp.getTp()));
				ledps.setInt(22, rcp.getCreated_by());
				ledps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(24, rcp.getSerialno());
				ledps.setInt(25, rcp.getFin_year());
				ledps.setInt(26, rcp.getMnth_code());
				ledps.setString(27, rcp.getVnart2());
				ledps.setInt(28, 1); // sub div
				ledps.setDouble(29, rcp.getTds_amt());
				ledps.setDouble(30, rcp.getBill_amt());
				ledps.setDouble(31, rcp.getCr_amt());  // taxable service tax
				ledps.setDouble(32, rcp.getSertax_amt());
				ledps.setDouble(33, rcp.getSertax_billper());
				ledps.setDouble(34, rcp.getSertax_per());
				
				ledps.setDouble(35, rcp.getCr_amt());  // taxable   
				ledps.setDouble(36, rcp.getCtaxper());   
				ledps.setDouble(37, rcp.getStaxper());   
				ledps.setDouble(38, rcp.getItaxper());   
				ledps.setDouble(39, rcp.getCgstamt());   
				ledps.setDouble(40, rcp.getSgstamt());   
				ledps.setDouble(41, rcp.getIgstamt());   
				ledps.setDouble(42, rcp.getNetamt());   
				ledps.setDouble(43, rcp.getCess_per());  // cess per   
				ledps.setDouble(44, rcp.getCess_amt());  // cess amt   
				ledps.setInt(45, rcp.getVreg_cd()); // rcm y/n y=1   
				ledps.setInt(46, rcp.getVarea_cd()); // itc y/n/ y=1   
				ledps.setInt(47, rcp.getVmsr_cd()); // state code   
				ledps.setString(48, ""); // gst no
				ledps.setLong(49, 0); // hsn code


				i=ledps.executeUpdate();
			}

			//voucher no 
			i=rcp.getVou_no();
			System.out.println("voucher no "+i);
			con.commit();
			con.setAutoCommit(true);
			ledps.close();

		} catch (SQLException ex) {
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
					"sub_code=?,vnart_1=?,name=?,vamount=?,chq_no=?,chq_date=?,chq_amt=?,bill_no=?,bill_date=?," +
					"modified_by=?,modified_date=?,vnart_2=?,bank_chg=?,tds_amt=?,vac_code=?,bill_amt=?,cr_amt=?,sertax_amt=?,sertax_billper=?,sertax_per=? ,  " +
					" taxable_amt=?,ctax_per=?, stax_per=?, itax_per=?, "+
					" cgst_amt=?, sgst_amt=?, igst_amt=?,net_amt=?,addl_taxper=?,addl_taxamt=?,vreg_cd=?," +
					" varea_cd=?,gst_no=?,hsn_code=? "+						
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR' and serialno=?";


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
				ledps.setString(7, rcp.getParty_name());
				ledps.setDouble(8, rcp.getVamount());
				ledps.setString(9, rcp.getChq_no());
				if(rcp.getChq_date()!=null)
					ledps.setDate(10, new java.sql.Date(rcp.getChq_date().getTime()));
				else
					ledps.setDate(10, null);

				ledps.setDouble(11, rcp.getChq_amt());
				ledps.setString(12, rcp.getBill_no());

				if(rcp.getBill_date()!=null)
					ledps.setDate(13, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					ledps.setDate(13, null);

				ledps.setInt(14, rcp.getModified_by());
				ledps.setDate(15, new java.sql.Date(rcp.getModified_date().getTime()));
				ledps.setString(16, rcp.getVnart2());
				ledps.setDouble(17, rcp.getBank_chg());
//				if (rcp.getVdepo_code()==11 || rcp.getVdepo_code()==3 || rcp.getVdepo_code()==12)
				ledps.setDouble(18, rcp.getTds_amt());

				ledps.setString(19, rcp.getVac_code());
				ledps.setDouble(20, rcp.getBill_amt());
				ledps.setDouble(21, rcp.getCr_amt());  // taxable service tax
				ledps.setDouble(22, rcp.getSertax_amt());
				ledps.setDouble(23, rcp.getSertax_billper());
				ledps.setDouble(24, rcp.getSertax_per());
				ledps.setDouble(25, rcp.getCr_amt());  // taxable   
				ledps.setDouble(26, rcp.getCtaxper());   
				ledps.setDouble(27, rcp.getStaxper());   
				ledps.setDouble(28, rcp.getItaxper());   
				ledps.setDouble(29, rcp.getCgstamt());   
				ledps.setDouble(30, rcp.getSgstamt());   
				ledps.setDouble(31, rcp.getIgstamt());   
				ledps.setDouble(32, rcp.getNetamt());   
				ledps.setDouble(33, rcp.getCess_per());  // cess per   
				ledps.setDouble(34, rcp.getCess_amt());  // cess amt   
				ledps.setInt(35, rcp.getVreg_cd()); // rcm y/n y=1   
				ledps.setInt(36, rcp.getVarea_cd()); // itc y/n/ y=1   
				ledps.setString(37, ""); //gst no
				ledps.setLong(38, 0);  // hsn code

				// where clause
				ledps.setInt(39, rcp.getFin_year());
				ledps.setInt(40, rcp.getDiv_code());
				ledps.setInt(41, rcp.getVdepo_code());
				ledps.setInt(42, rcp.getVbook_cd1());
				ledps.setInt(43, rcp.getVou_no());
				ledps.setInt(44, rcp.getSerialno());


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


	public RcpDto getVouDetailGST(int vou,int depo_code,int div,int fyear,int bkcd,String tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int maxSerialNo=0;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
			"s.mac_name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno," +
			"f.chq_no,if(f.chq_date='0000-00-00',null,f.chq_date),f.chq_amt,f.bill_no,f.bill_date,ifnull(f.tds_amt,0),ifnull(f.bill_amt,f.vamount)," +
			"ifnull(f.cr_amt,0),ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0)," +
			" f.taxable_amt,(f.ctax_per+f.stax_per+f.itax_per), "+
			" f.cgst_amt, f.sgst_amt, f.igst_amt,f.net_amt,f.addl_taxper,f.addl_taxamt,f.vreg_cd," +
			" f.varea_cd,ifnull(f.gst_no,''),ifnull(f.hsn_code,0) "+						

			" from ledfile as f "+
			"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and ifnull(s.del_tag,'')<>'D'"+ 
			",partyfst as p "+
			"where f.exp_code=p.mac_code and f.fin_year=? and f.div_code=? and f.vdepo_code=? and "+ 
			"f.vbook_cd1=? and f.vou_no=? and f.vdbcr='DR' and f.tp=? and f.sub_div=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and ifnull(p.del_tag,'')<>'D' order by f.vou_no,serialno ";  


			String maxSrial = "select ifnull(max(serialno),0) from ledfile where fin_year=? and div_code=? and vdepo_code=? and "+ 
			" vbook_cd1=? and vou_no=? and vdbcr='DR' and tp=?  ";

			ps2 = con.prepareStatement(maxSrial);
			ps2.setInt(1,fyear);
			ps2.setInt(2,div);
			ps2.setInt(3, depo_code);
			ps2.setInt(4,bkcd); 
			ps2.setInt(5,vou); 
			ps2.setString(6,tp); 
			
			rs= ps2.executeQuery();
			if(rs.next())
			{
				maxSerialNo = rs.getInt(1);
			}
			rs.close();
			ps2.close();
			rs=null;
			ps2=null;
			

			/////////////////////////////////
			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,fyear);
			ps2.setInt(4,div);
			ps2.setInt(5, depo_code);
			ps2.setInt(6,bkcd); 
			ps2.setInt(7,vou); 
			ps2.setString(8,tp); 
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
					rcp.setChq_no(rs.getString(15));
					rcp.setChq_date(rs.getDate(16));
					rcp.setChq_amt(rs.getDouble(17));
					rcp.setVnart2(rs.getString(9));
					rcp.setSerialno(maxSerialNo);

				}






				/// Table detail set here
				colum = new Vector();
				colum.addElement(false);   // del tag
				colum.addElement(rs.getString(5));   // exp code
				colum.addElement(rs.getString(6));  //exp  name
				colum.addElement(rs.getString(8));  //sub_head
				colum.addElement(rs.getString(18));  //bill_no
				if (rs.getDate(19)!=null)
				{
				   try
					{
					  colum.addElement(sdf.format(rs.getDate(19)));  //bill_date
					}catch(Exception e)
					{
						colum.addElement("");  //bill_date
					}
				}
				else
				{
					colum.addElement("");  //bill_date
				}

				colum.addElement(rs.getString(10)); // narration
				colum.addElement(nf.format(rs.getDouble(21)));  //billamt
				colum.addElement(rs.getInt(12));  //vbook_cd
				colum.addElement(rs.getInt(13));  // vgrpcode
				colum.addElement(rs.getString(7));  // subcode 
				colum.addElement(rs.getInt(14));  // serialno
				colum.addElement(nf.format(rs.getDouble(26)));  //taxable
				
				colum.addElement(rs.getInt(34)==0?"N":"Y");  // vreg_cd      10 rcm
				colum.addElement(rs.getInt(35)==0?"N":"Y");  // varea_cd     11 itc
				colum.addElement(nf.format(rs.getDouble(27)));  // gst %  12
				colum.addElement(nf.format(rs.getDouble(28)));  // cgst %  13
				colum.addElement(nf.format(rs.getDouble(29)));  // sgst %  14
				colum.addElement(nf.format(rs.getDouble(30)));  // igst %  15
				colum.addElement(nf.format(rs.getDouble(32)));  // cess %  16
				colum.addElement(nf.format(rs.getDouble(33)));  // cess amount  17
				colum.addElement(nf.format(rs.getDouble(31)));  // next amount  17
				colum.addElement(nf.format(rs.getDouble(20)));  //tds
				colum.addElement(nf.format(rs.getDouble(11)));  // balance amountvamount
				colum.addElement(nf.format(rs.getDouble(24)));  //bill_per
				colum.addElement(nf.format(rs.getDouble(25)));  //servicetax_per
				row.addElement(colum);
				total+=rs.getDouble(11);


			}

			if (rcp!=null)
			{
				rcp.setVamount(total);
				rcp.setVdetail(row);
				System.out.println("aftger hsis fill ref data is callledsssss SIZE HAI DAO MEIN "+row.size());
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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		
	
	public List getVouListGST(Date invDt,Date endDate,int depo_code,int div,int bkcd,String tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v=null;
		Vector col=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


			String query2 ="select f.vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
					"s.mac_name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno," +
					"f.chq_no,f.chq_date,chq_amt,f.bill_no,f.bill_date,ifnull(f.tds_amt,0),ifnull(f.bill_amt,f.vamount)," +
					"ifnull(f.cr_amt,0),ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0),ifnull(f.tax_type,'H')," +
					" f.taxable_amt,(f.ctax_per+f.stax_per+f.itax_per), "+
					" f.cgst_amt, f.sgst_amt, f.igst_amt,f.net_amt,f.addl_taxper,f.addl_taxamt,f.vreg_cd," +
					" f.varea_cd,ifnull(f.gst_no,''),ifnull(f.hsn_code,0) "+						
					" from ledfile as f "+
					"left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and  ifnull(s.del_tag,'')<>'D' "+ 
					",partyfst as p "+
					"where f.exp_code=p.mac_code  and f.div_code=? and f.vdepo_code=? and "+ 
					"f.vbook_cd1=? and f.vou_date between ? and ? and vdbcr='DR' and f.tp=? and f.sub_div=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' order by f.vou_no ";  

			 if (depo_code==8)  // for ho only
			 {
				 query2 ="select concat('VP',indicator,right(vou_no,5)) vou_lo,f.name,f.vou_no,f.vou_date,f.exp_code,p.mac_name,f.sub_code," +
						 "s.mac_name,f.vnart_2,f.vnart_1,f.vamount,f.vbook_cd,f.vgrp_code,f.serialno," +
						 "f.chq_no,f.chq_date,chq_amt,f.bill_no,f.bill_date,ifnull(f.tds_amt,0),ifnull(f.bill_amt,f.vamount)," +
						 "ifnull(f.cr_amt,0),ifnull(f.sertax_amt,0),ifnull(f.sertax_billper,0),ifnull(f.sertax_per,0),ifnull(f.tax_type,'H')," +
						 " f.taxable_amt,(f.ctax_per+f.stax_per+f.itax_per), "+
						 " f.cgst_amt, f.sgst_amt, f.igst_amt,f.net_amt,f.addl_taxper,f.addl_taxamt,f.vreg_cd," +
						 " f.varea_cd,ifnull(f.gst_no,''),ifnull(f.hsn_code,0) "+						
						 " from ledfile as f "+
						 "left join  subaccmaster as s on f.sub_code=s.mac_code and f.exp_code=s.mgrp_code and s.div_code=? and s.depo_code=? and  ifnull(s.del_tag,'')<>'D' "+ 
						 ",partyfst as p "+
						 "where f.exp_code=p.mac_code  and f.div_code=? and f.vdepo_code=? and "+ 
						 "f.vbook_cd1=? and f.vou_date between ? and ? and vdbcr='DR' and f.tp='D' and f.indicator=? and f.sub_div=1 and  ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and  ifnull(p.del_tag,'')<>'D' order by f.vou_no ";  
			 }
//TODO


			ps2 = con.prepareStatement(query2);
			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,div);
			ps2.setInt(4, depo_code);
			ps2.setInt(5,bkcd); 
			ps2.setDate(6, new java.sql.Date(invDt.getTime()));
			ps2.setDate(7, new java.sql.Date(endDate.getTime()));
			ps2.setString(8,tp); 
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
					if(depo_code==8)
						inv = rs.getString(1);
					else
						inv = rs.getString(1)+"A"+rs.getString(3).substring(1);

					System.out.println(inv);
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
					if(depo_code==8)
						inv = rs.getString(1);
					else
						inv = rs.getString(1)+"A"+rs.getString(3).substring(1);

					total=0.00;
					rcp= new RcpDto();
					row = new Vector();   // Create new Table row



				}



				rcp.setVou_no(rs.getInt(3));
				rcp.setVou_date(rs.getDate(4));
				rcp.setParty_name(rs.getString(2));
				rcp.setChq_no(rs.getString(15));
				rcp.setChq_date(rs.getDate(16));
				rcp.setChq_amt(rs.getDouble(17));
				rcp.setVnart2(rs.getString(9));
				if(rs.getString(26).equalsIgnoreCase("R"))
					rcp.setPay_mode(1);  // rtgs
				else
					rcp.setPay_mode(0); // ho chq book

				/// Table detail set here
				colum = new Vector();
				colum.addElement(false);   // del tag   0
				colum.addElement(rs.getString(5));   // exp code  1
				colum.addElement(rs.getString(6));  //exp  name 2
				colum.addElement(rs.getString(8));  //sub_head 3
				colum.addElement(rs.getString(18));  //bill_no 4
				if (rs.getDate(19)!=null)
					colum.addElement(sdf.format(rs.getDate(19)));  //bill_date 5
				else
					colum.addElement("");  //bill_date 5

				colum.addElement(rs.getString(10)); // narration 6
				colum.addElement(rs.getDouble(21));  //vamount 7 
				colum.addElement(rs.getInt(12));  //vbook_cd 8
				colum.addElement(rs.getInt(13));  // vgrpcode 9
				colum.addElement(rs.getString(7));  // subcode 10
				colum.addElement(rs.getInt(14));  // serialno 11
				colum.addElement(nf.format(rs.getDouble(27)));  //taxable 12
				

				colum.addElement(rs.getInt(35)==0?"N":"Y");  // vreg_cd      13 rcm
				colum.addElement(rs.getInt(36)==0?"N":"Y");  // varea_cd     14 itc
				colum.addElement(nf.format(rs.getDouble(28)));  // gst %  15
				colum.addElement(nf.format(rs.getDouble(29)));  // cgst %  16
				colum.addElement(nf.format(rs.getDouble(30)));  // sgst %  17
				colum.addElement(nf.format(rs.getDouble(31)));  // igst %  18
				colum.addElement(nf.format(rs.getDouble(33)));  // cess %  19
				colum.addElement(nf.format(rs.getDouble(34)));  // cess amount  20
				colum.addElement(nf.format(rs.getDouble(32)));  // next amount  21
				colum.addElement(nf.format(rs.getDouble(20)));  //tds 22
				colum.addElement(nf.format(rs.getDouble(11)));  // balance amountvamount 23
				colum.addElement(nf.format(rs.getDouble(24)));  //billper 24
				colum.addElement(nf.format(rs.getDouble(25)));  //servicetax per 25
				row.addElement(colum);

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

		} catch (SQLException ex) {
			try {
				System.out.println("yaha kuch errr hai "+ex);
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getVouList " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return v;
	}	

	
	///////////////////// GET OUTSTAND DETAIL OF CREDITORS
	public RcpDto getPaidBillDetail(int depo_code,int wdepo,int fyear,String code,Date due_date)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		java.sql.Date duedate=null;
		int div=4;
		
		System.out.println("KYA HAI YEHA PER #######");
		
		try {
			duedate = new java.sql.Date(due_date.getTime());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
		try {



			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");
			String vlo="";
 
			String query2 =" select a.vou_no,a.bill_no,a.bill_date,a.bill_amt,a.tds_amt,(a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) bal,a.vou_date,a.vou_lo from "+ 
			" (select l.vou_no,l.bill_no,l.bill_date,l.bill_amt,l.tds_amt,l.vou_date,l.vou_lo,l.vac_code "+
			" from rcpfile l where   l.div_code=? and l.vdepo_code=? "+ 
			" and l.vbook_cd in (60,61) and l.vou_date between '2011-04-01' and '2014-03-31' " +
			" and l.vac_code=? and (vamount-chq_amt)>0 and  ifnull(l.del_tag,'')<>'D' " +
			" union all " +
			" select l.vou_no,l.bill_no,l.bill_date,l.bill_amt,l.tds_amt,l.vou_date,l.vou_lo,l.vac_code "+
			" from ledfile l where  l.fin_year between 2014 and ? and l.div_code=? and l.vdepo_code=? "+ 
			" and l.vbook_cd1 in (60,61) and l.vac_code=? and l.vou_date<'2017-07-01' and ifnull(l.indicator,'')<>'A' "+
			" and  ifnull(l.del_tag,'')<>'D' "+
			" union all " +
			" select l.vou_no,l.bill_no,l.bill_date,l.bill_amt,l.tds_amt,l.vou_date,l.vou_lo,l.vac_code "+
			" from rcpfile l where  l.fin_year between 2017 and ? and l.div_code=? and l.vdepo_code=? "+ 
			" and l.vbook_cd in (60,61) and l.vac_code=? and l.vou_date>'2017-06-30'  "+
			" and  ifnull(l.del_tag,'')<>'D' ) a  "+

			" left join "+

			" (select r.vouno vou_no,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vou_date,r.vou_lo,sum(r.vamount) vamount,r.vac_code "+
			" from rcpfile r where  r.fin_year<=? and r.div_code=? and r.vdepo_code=?  "+
			" and r.vbook_cd in (20,21,22,98)  and r.vac_code=? and    ifnull(r.cr_no,'')<>'C' "+
			" and  ifnull(r.del_tag,'')<>'D' group by r.vouno,r.bill_no,r.bill_date) b "+
			" on  a.vac_code=b.vac_code and a.vou_no=b.vou_no and a.bill_date=b.bill_date  "+  ///and a.bill_amt=b.bill_amt
			" order by a.vou_date desc,a.vou_no " ;

//			" where  (a.bill_amt-a.tds_amt-ifnull(b.vamount,0.00)) =0 order by a.vou_date desc,a.vou_no " ;

			ps2 = con.prepareStatement(query2);

				ps2.setInt(1,div);
				ps2.setInt(2, depo_code);
				ps2.setString(3,code); 
				ps2.setInt(4,fyear);
				ps2.setInt(5,div);
				ps2.setInt(6, depo_code);
				ps2.setString(7,code); 
				ps2.setInt(8,fyear);
				ps2.setInt(9,div);
				ps2.setInt(10, depo_code);
				ps2.setString(11,code); 

				ps2.setInt(12,fyear);
				ps2.setInt(13,div);
				ps2.setInt(14, depo_code);
				ps2.setString(15,code); 
			
			rs= ps2.executeQuery();



			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;

			while (rs.next())
			{

					colum = new Vector();
					colum.addElement(rs.getString(8)+String.format("%06d",rs.getInt(1)));   // ref no
					colum.addElement(rs.getString(2));  //bill no
					if(rs.getDate(3)!=null)
					{
						colum.addElement(sdf.format(rs.getDate(3)));  //bill_date
					}
					else
					{
						colum.addElement("__/__/____");  //bill_date
					}
					//colum.addElement(rs.getDate(3));  //bill_date
					colum.addElement(nf.format(rs.getDouble(4)));  //bill_amt
					colum.addElement(nf.format(rs.getDouble(5)));  //tds_amt
					colum.addElement(nf.format(rs.getDouble(6)));  //balance amt
					colum.addElement(0.00);  //balance amt
					colum.addElement(sdf.format(rs.getDate(7)));  //vou_date
					vlo=rs.getString(8);
					row.addElement(colum);
			}
			rcp= new RcpDto();
			rcp.setVdetail(row);
			rcp.setVou_lo(vlo);
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
			System.out.println("-------------Exception in BankVouDAO.getBillDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		


	//// method to add record in led file (debit note)	
	public int addLedDebitNoteGST(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		PreparedStatement rcpps=null;
		RcpDto rcp=null;
		int expcode=0;
		ChequeDto chque=null;
		double totamt=0.00;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);


			String led="insert into ledfile (div_code,vdepo_code,vbook_cd,vbook_cd1,vou_lo,vou_no, " +
					"vou_date,vgrp_code,vac_code,vnart_1,name,vamount,vdbcr,chq_no,chq_date,chq_amt,bank_chg,tp, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,vbk_code,sub_div,tran_type,exp_code," +
					"taxable_amt,ctax_per,stax_per,itax_per,cgst_amt,sgst_amt,igst_amt,net_amt,vou_yr) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			String rp="insert into rcpfile (div_code,vdepo_code,vbook_cd,vou_yr,vou_lo,vou_no, " +
					"vou_date,head_code,vac_code,vnart_1,vamount,vdbcr,chq_no,chq_date,chq_amt,bill_no,bill_date,bill_amt,vouno,voudt,tds_amt, " +
					"Created_by,Created_date,serialno,fin_year,mnth_code,inv_lo,tran_type,cr_no) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


			

			// 040+depo+01

			ledps=con.prepareStatement(led);
			rcpps=con.prepareStatement(rp);

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
				rcpps.setString(13, "");
				rcpps.setDate(14, null);
					
				rcpps.setDouble(15, rcp.getChq_amt());
				rcpps.setString(16, rcp.getBill_no());

				if(rcp.getBill_date()!=null)
					rcpps.setDate(17, new java.sql.Date(rcp.getBill_date().getTime()));
				else
					rcpps.setDate(17,null);

				rcpps.setDouble(18, rcp.getBill_amt());
				rcpps.setInt(19, rcp.getVouno());

				if(rcp.getVoudt()!=null)
					rcpps.setDate(20, new java.sql.Date(rcp.getVoudt().getTime()));
				else
					rcpps.setDate(20, null);

				rcpps.setDouble(21, rcp.getTds_amt());
				rcpps.setInt(22, rcp.getCreated_by());
				rcpps.setDate(23, new java.sql.Date(rcp.getCreated_date().getTime()));
				rcpps.setInt(24, rcp.getSerialno());
				rcpps.setInt(25, rcp.getFin_year());
				rcpps.setInt(26, rcp.getMnth_code());
				rcpps.setString(27, rcp.getInv_lo());
				rcpps.setInt(28, rcp.getTran_ype());
				rcpps.setString(29, rcp.getCn_no());  // vou ref no
				i=rcpps.executeUpdate();
				i=rcp.getVou_no();

			} // end of loop

			//  add record in led file
			if (rcp!=null)
			{	

				expcode=0;

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
				ledps.setDouble(12, rcp.getNetamt());
				ledps.setString(13, "DR");
				ledps.setString(14, "");
				ledps.setDate(15, null);
					
				ledps.setDouble(16, rcp.getNetamt());
				ledps.setDouble(17, rcp.getBank_chg());
				ledps.setString(18, String.valueOf(rcp.getTp()));
				ledps.setInt(19, rcp.getCreated_by());
				ledps.setDate(20, new java.sql.Date(rcp.getCreated_date().getTime()));
				ledps.setInt(21, rcp.getSerialno());
				ledps.setInt(22, rcp.getFin_year());
				ledps.setInt(23, rcp.getMnth_code());
				ledps.setInt(24, rcp.getVbk_code());
				ledps.setInt(25, 1); // sub diviiosn
				ledps.setInt(26, rcp.getTran_ype());
				ledps.setString(27, rcp.getVac_code());
				
				ledps.setDouble(28, rcp.getTaxable());
				ledps.setDouble(29, rcp.getCtaxper());
				ledps.setDouble(30, rcp.getStaxper());
				ledps.setDouble(31, rcp.getItaxper());
				ledps.setDouble(32, rcp.getCgstamt());
				ledps.setDouble(33, rcp.getSgstamt());
				ledps.setDouble(34, rcp.getIgstamt());
				ledps.setDouble(35, rcp.getNetamt());
				ledps.setInt(36, rcp.getVou_yr());
				i=ledps.executeUpdate();

				i=rcp.getVou_no();
				
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
			System.out.println("-------------Exception in SQLRcpDAO.addLed -HOBankPaymentCR " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ledps != null){ledps.close();}
				if(rcpps != null){rcpps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLRcpDAO.Connection.close HOBankPaymentCR "+e);
			}
		}

		return (i);
	}			

	//////////////// debit note voucher 
	public RcpDto getDebitDetail(int vou,int depo_code,int wdepo,int fyear,int bkcd,String tp,Date voudt,int mnthcode)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=null;
		String tp1=tp;
		int xdepo=depo_code;
		int tran=99;
		int div=4;
		
		NumberFormat nf=null;

		
		
		try {



			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");

			String query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,l.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
					" r.cr_no,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vamount,r.voudt,r.inv_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),l.vou_lo,ifnull(l.tax_type,'H')," +
					" ifnull(l.taxable_amt,0),ifnull(l.cgst_amt,0),ifnull(l.sgst_amt,0),ifnull(l.igst_amt,0),ifnull(l.net_amt,0),r.serialno "+
					" from ledfile as l,rcpfile as r "+
					" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd1=?  and l.vou_no=? "+
					" and  l.vdbcr='DR' and ifnull(l.del_tag,'')<>'D' and r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd=? and r.vou_no=? " +
					"  and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date" ;

			ps2 = con.prepareStatement(query2);

				ps2.setInt(1,fyear);
				ps2.setInt(2,div);
				ps2.setInt(3, depo_code);
				ps2.setInt(4,bkcd);
				ps2.setInt(5,vou);
				ps2.setInt(6,fyear);
				ps2.setInt(7,div);
				ps2.setInt(8, depo_code);
				ps2.setInt(9,bkcd);
				ps2.setInt(10,vou);
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
					rcp.setVou_no(rs.getInt(1));
					rcp.setVou_date(rs.getDate(2));
					rcp.setVac_code(rs.getString(3));
					rcp.setParty_name(rs.getString(4));
					rcp.setVnart1(rs.getString(5));
					rcp.setChq_no(rs.getString(6));
					rcp.setChq_date(rs.getDate(7));
					rcp.setChq_amt(rs.getDouble(8));
					rcp.setBank_chg(rs.getDouble(9));
					rcp.setRcp_no(rs.getString(17));
					rcp.setVnart2(rs.getString(18));
					rcp.setTds_amt(rs.getDouble(19));
					
					if (tran==99)
						inv = rs.getString(20);
					else
						inv = rs.getString(20)+"A"+rs.getString(1).substring(1);

					rcp.setVou_lo(inv);
					if(rs.getString(21).equalsIgnoreCase("R"))
						rcp.setPay_mode(1);  // rtgs
					else
						rcp.setPay_mode(0); // ho chq book

					rcp.setTaxable(rs.getDouble(22));
					rcp.setCgstamt(rs.getDouble(23));
					rcp.setSgstamt(rs.getDouble(24));
					rcp.setIgstamt(rs.getDouble(25));
					rcp.setNetamt(rs.getDouble(26));

				}

				/// Table detail set here
				colum = new Vector();
				if(depo_code==8) // ONLY FOR HO PACKAGE
					colum.addElement(false);   // check boolean 
				
				colum.addElement(rs.getString(10));   // ref no
				colum.addElement(rs.getString(11));  //bill no
				if(rs.getDate(12)!=null)
					colum.addElement(sdf.format(rs.getDate(12)));  //bill_date
				else
					colum.addElement("");  //bill_date

				colum.addElement(nf.format(rs.getDouble(13)));  //bill_amt
				colum.addElement(rs.getDouble(14));  //tds_amt
				colum.addElement(0.00);  //paid amount
				colum.addElement(nf.format(rs.getDouble(15)));  //paid amount
				if(rs.getDate(16)!=null)
					colum.addElement(sdf.format(rs.getDate(16)));  //voudt
				else
					colum.addElement("");  //voudt
				
				colum.addElement(rs.getInt(27));  //serial no
				row.addElement(colum);
				total+=rs.getDouble(15);


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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail1 " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		

	//// method to update record in led file (debit note)	
	public int updateDebitNoteGST(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		RcpDto rcp=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



			String led="update  ledfile SET vou_date=?," +
					"vnart_1=?,vamount=?,taxable_amt=?,cgst_amt=?,sgst_amt=?,igst_amt=?,net_amt=?," +
					"modified_by=?,modified_date=?   " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd1=? " +
					"and vou_no=? and vdbcr='DR' ";

			String rcp1="update  rcpfile SET vou_date=?," +
					"vamount=?," +
					"modified_by=?,modified_date=? " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? " +
					"and vou_no=? and vdbcr='DR' and serialno=?";



			ledps=con.prepareStatement(rcp1);

			for(int j=0;j<cList.size();j++)
			{
				rcp = (RcpDto) cList.get(j);
				
				ledps.setDate(1, new java.sql.Date(rcp.getVou_date().getTime()));
				ledps.setDouble(2, rcp.getVamount());
				ledps.setInt(3, rcp.getModified_by());
				ledps.setDate(4, new java.sql.Date(rcp.getModified_date().getTime()));
				// where clause
				ledps.setInt(5, rcp.getFin_year());
				ledps.setInt(6, rcp.getDiv_code());
				ledps.setInt(7, rcp.getVdepo_code());
				ledps.setInt(8, rcp.getVbook_cd1());
				ledps.setInt(9, rcp.getVou_no());
				ledps.setInt(10, rcp.getSerialno());


				i=ledps.executeUpdate();
			}
			
			ledps=null;
			ledps=con.prepareStatement(led);
			
			ledps.setDate(1, new java.sql.Date(rcp.getVou_date().getTime()));
			ledps.setString(2, rcp.getVnart1());
			ledps.setDouble(3, rcp.getNetamt());
			ledps.setDouble(4, rcp.getTaxable());
			ledps.setDouble(5, rcp.getCgstamt());
			ledps.setDouble(6, rcp.getSgstamt());
			ledps.setDouble(7, rcp.getIgstamt());
			ledps.setDouble(8, rcp.getNetamt());

			ledps.setInt(9, rcp.getModified_by());
			ledps.setDate(10, new java.sql.Date(rcp.getModified_date().getTime()));
			// where clause
			ledps.setInt(11, rcp.getFin_year());
			ledps.setInt(12, rcp.getDiv_code());
			ledps.setInt(13, rcp.getVdepo_code());
			ledps.setInt(14, rcp.getVbook_cd1());
			ledps.setInt(15, rcp.getVou_no());
			


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
			System.out.println("-------------Exception in SQLRcpDAO.updateDebitNoteGST " + ex);
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

	
	//////////////// bank payment creditors method
	public RcpDto getVouDetail1Correction(int vou,int depo_code,int wdepo,int fyear,int bkcd,String tp,Date voudt,int mnthcode)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		SimpleDateFormat sdf=null;
		String tp1=tp;
		int xdepo=depo_code;
		boolean advance=false;
		if(depo_code>200)
		{
			depo_code-=199;
			advance=true;
			
		}
		else
			depo_code-=99;
		
		int tran=99;
		int div=4;
		if (depo_code < 0)
		{
			depo_code=xdepo;
			tran=0;
		}
		try {



			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");

			System.out.println("DEPO CODE "+depo_code+" tran "+tran+" tp "+tp+" mnthcode "+mnthcode);
					
					System.out.println("kya yeh query chali");
				String query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,r.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
						" r.inv_lo,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vamount,r.voudt,r.inv_lo,ifnull(l.vnart_2,''),ifnull(l.tds_amt,0),concat('VP',indicator,right(l.vou_no,5)) vou_lo,ifnull(l.tax_type,'H'),r.serialno,l.mnth_code "+
						" from ledfile as l,rcpfile as r "+
						" where l.vac_code=r.vac_code and l.fin_year=? and l.div_code=? and l.vdepo_code=? and l.vbook_cd1=? and l.tp='C' and l.indicator=?  and l.vou_no=? "+
						" and  l.vdbcr='DR' and ifnull(l.tran_type,0)=? and ifnull(l.del_tag,'')<>'D' and r.fin_year=? and r.div_code=? and r.vdepo_code=? and r.vbook_cd=? and r.vou_no=? " +
						" and ifnull(r.tran_type,0)=? and r.mnth_code="+mnthcode+" and  ifnull(r.del_tag,'')<>'D' and l.vou_date=r.vou_date" ;
			
			ps2 = con.prepareStatement(query2);

				ps2.setInt(1,fyear);
				ps2.setInt(2,div);
				ps2.setInt(3, depo_code);
				ps2.setInt(4,bkcd);

				if(tran==99 && wdepo!=8)
					ps2.setInt(5,wdepo);
				else
					ps2.setString(5,tp);

				ps2.setInt(6,vou);
				ps2.setInt(7,tran);
				ps2.setInt(8,fyear);
				ps2.setInt(9,div);
				ps2.setInt(10, depo_code);
				ps2.setInt(11,bkcd); 
				ps2.setInt(12,vou); 
				ps2.setInt(13,tran);
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
					rcp.setVou_no(rs.getInt(1));
					rcp.setVou_date(rs.getDate(2));
					rcp.setVac_code(rs.getString(3));
					rcp.setParty_name(rs.getString(4));
					rcp.setVnart1(rs.getString(5));
					rcp.setChq_no(rs.getString(6));
					rcp.setChq_date(rs.getDate(7));
					rcp.setChq_amt(rs.getDouble(8));
					rcp.setBank_chg(rs.getDouble(9));
					rcp.setRcp_no(rs.getString(17));
					rcp.setVnart2(rs.getString(18));
					rcp.setTds_amt(rs.getDouble(19));
					rcp.setMnth_code(rs.getInt(23));
					if (tran==99)
						inv = rs.getString(20);
					else
						inv = rs.getString(20)+"A"+rs.getString(1).substring(1);

					rcp.setVou_lo(inv);
					if(rs.getString(21).equalsIgnoreCase("R"))
						rcp.setPay_mode(1);  // rtgs
					else
						rcp.setPay_mode(0); // ho chq book

				}

				/// Table detail set here
				colum = new Vector();
				if(depo_code==8) // ONLY FOR HO PACKAGE
					colum.addElement(false);   // check boolean 0
				
				colum.addElement(rs.getString(10));   // ref no 1
				colum.addElement(rs.getString(11));  //bill no 2
				//				colum.addElement(sdf.format(rs.getDate(12)));  //bill_date
				//	colum.addElement(rs.getDate(12));  //bill_date
				if(rs.getDate(12)!=null)
					colum.addElement(sdf.format(rs.getDate(12)));  //bill_date 3
				else
					colum.addElement("");  //bill_date 3

				colum.addElement(rs.getDouble(13));  //bill_amt 4
				colum.addElement(rs.getDouble(14));  //tds_amt 5
				colum.addElement(0.00);  //paid amount 6
				colum.addElement(rs.getDouble(15));  //paid amount 7
				if(rs.getDate(16)!=null)
					colum.addElement(sdf.format(rs.getDate(16)));  //voudt 8
				else
					colum.addElement("");  //voudt 8
				colum.addElement(rs.getString(5));  //narration 9
				colum.addElement(rs.getInt(22));  //serial no
				
				
				
				row.addElement(colum);
				total+=rs.getDouble(15);


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
			System.out.println("-------------Exception in BankVouDAO.getVouDetail1 " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return rcp;
	}		
	

	//// method to update record in led file (cash voucher)	
	public int updateLedPay(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		PreparedStatement ledps=null;
		RcpDto rcp=null;
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



			String led="update  rcpfile set vnart_1=?,bill_no=? " +
					"where fin_year=? and div_code=? and vdepo_code=? and vbook_cd=? " +
					"and vou_no=? and vou_date=? and mnth_code=? and vac_code=? and vdbcr='DR' and serialno=? and ifnull(del_tag,'')<>'D'";


			ledps=con.prepareStatement(led);

			for(int j=0;j<cList.size();j++)
			{
				rcp = (RcpDto) cList.get(j);
				
				
				System.out.println(rcp.getFin_year());
				System.out.println(rcp.getDiv_code());
				System.out.println(rcp.getVdepo_code());
				System.out.println(rcp.getVbook_cd1());	
				System.out.println(rcp.getVou_no());
				System.out.println(new java.sql.Date(rcp.getVou_date().getTime())); 
				System.out.println(rcp.getMnth_code());
				System.out.println(rcp.getVac_code());	
				System.out.println(rcp.getSerialno());
				System.out.println(rcp.getVnart1());		

				
				ledps.setString(1, rcp.getVnart1());
				ledps.setString(2, rcp.getBill_no());
				// where clause
				ledps.setInt(3, rcp.getFin_year());
				ledps.setInt(4, rcp.getDiv_code());
				ledps.setInt(5, rcp.getVdepo_code());
				ledps.setInt(6, rcp.getVbook_cd1());
				ledps.setInt(7, rcp.getVou_no());
				ledps.setDate(8,new java.sql.Date(rcp.getVou_date().getTime())); 
				ledps.setInt(9, rcp.getMnth_code());
				ledps.setString(10, rcp.getVac_code());
				ledps.setInt(11, rcp.getSerialno());


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

	
	

	public List getVouList1Payment(Date voudt,Date edate,int depo_code,int div,int bkcd,String tp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		RcpDto rcp=null;
		double total=0.00;
		Vector v=null;
		Vector col=null;
		String query2=null;
		SimpleDateFormat sdf=null;
		int xdepo=depo_code;
		int tran=99;
		int vter_cd=0;
		boolean advance=false;
		if(depo_code>200)
		{
			advance=true;
			depo_code-=199;
			vter_cd=depo_code;
			depo_code=8;
			
		}
		else
			depo_code-=99;

		if (depo_code < 0)
		{
			depo_code=xdepo;
			tran=0;
		}
			
		try {



			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			sdf = new SimpleDateFormat("dd/MM/yyyy");

					query2 =" select l.vou_no,l.vou_date,l.vac_code,l.name,r.vnart_1,l.chq_no,l.chq_date,l.vamount,l.bank_chg, "+
							" r.inv_lo,r.bill_no,r.bill_date,r.bill_amt,r.tds_amt,r.vamount,concat('VP',indicator,right(l.vou_no,5)) vou_lo,r.voudt,ifnull(l.vou_lo,'H'),r.serialno,l.mnth_code "+
							" from ledfile as l,rcpfile as r "+
							" where l.vac_code=r.vac_code and l.div_code=? and l.vdepo_code=? and l.vbook_cd1=?  and l.vdbcr='DR' and l.tp='C' and l.indicator='"+tp+"' and l.vou_date between ? and ? "+
							" and ifnull(l.tran_type,0)=?  and  ifnull(l.del_tag,'')<>'D' and  r.div_code=? and r.vdepo_code=? and r.vou_date between ? and ? and l.vou_no=r.vou_no " +
							" and ifnull(r.tran_type,0)=? and  ifnull(r.del_tag,'')<>'D' " ;
					
			ps2 = con.prepareStatement(query2);

			ps2.setInt(1,div);
			ps2.setInt(2, depo_code);
			ps2.setInt(3,bkcd); 
			ps2.setDate(4,new java.sql.Date(voudt.getTime()));
			ps2.setDate(5,new java.sql.Date(edate.getTime()));
			ps2.setInt(6,tran);
			ps2.setInt(7,div);
			ps2.setInt(8, depo_code);
			ps2.setDate(9,new java.sql.Date(voudt.getTime())); 
			ps2.setDate(10,new java.sql.Date(edate.getTime()));
			ps2.setInt(11,tran);
			rs= ps2.executeQuery();


			//TODO  fsdfs 



			v = new Vector();
			Vector row = new Vector();   // Table row
			Vector colum = null; // Table Column
			String inv=null;
			boolean first=true;
			int vou=0; 
			while (rs.next())
			{


				if (first)
				{
					vou=rs.getInt(1);
					if (tran==99)
						inv = rs.getString(16);
					else
						inv = rs.getString(16)+"A"+rs.getString(1).substring(1);
					first=false;
					rcp= new RcpDto();
				}

				if (vou!=rs.getInt(1))
				{

					col= new Vector();
					col.addElement(inv);//concat inv_no
					col.addElement(rcp.getParty_name());//party name
					col.addElement(rcp);
					col.addElement(row);
					v.addElement(col);

					vou=rs.getInt(1);
					if (tran==99)
						inv = rs.getString(16);
					else
						inv = rs.getString(16)+"A"+rs.getString(1).substring(1);
					total=0.00;
					rcp= new RcpDto();
					row = new Vector();   // Create new Table row



				}


				rcp.setVou_no(rs.getInt(1));
				rcp.setVou_date(rs.getDate(2));
				rcp.setVac_code(rs.getString(3));
				rcp.setParty_name(rs.getString(4));
				rcp.setVnart1(rs.getString(5));
				rcp.setChq_no(rs.getString(6));
				rcp.setChq_date(rs.getDate(7));
				rcp.setChq_amt(rs.getDouble(8));
				rcp.setBank_chg(rs.getDouble(9));
				rcp.setMnth_code(rs.getInt(20));
				
				if(rs.getString(18).equalsIgnoreCase("R"))
					rcp.setPay_mode(1);  // rtgs
				else
					rcp.setPay_mode(0); // ho chq book


				/// Table detail set here
				colum = new Vector();
				if(depo_code==8) // ONLY FOR HO PACKAGE
					colum.addElement(false);   // check boolean 

				colum.addElement(rs.getString(10));   // ref no
				colum.addElement(rs.getString(11));  //bill no

				if(rs.getDate(12)!=null)
					colum.addElement(sdf.format(rs.getDate(12)));  //bill_date
				else
					colum.addElement("__/__/____");  //bill_date
				
				colum.addElement(rs.getDouble(13));  //bill_amt
				colum.addElement(rs.getDouble(14));  //tds_amt
				colum.addElement(0.00);  //paid amount
				colum.addElement(rs.getDouble(15));  //paid amount
				
				if(rs.getDate(17)!=null)
					colum.addElement(sdf.format(rs.getDate(17)));  //voudt
				else
					colum.addElement("__/__/____");  //voudt
				
				colum.addElement(rs.getString(5));  //narration 9
				colum.addElement(rs.getInt(19));  //serial no

				
				row.addElement(colum);
				total+=rs.getDouble(15);
				rcp.setVamount(total);
			}


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
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in BankVouDAO.getVouList1 " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return v;
	}		

	
	public int getDebitNoteNo(int depo,int div,int year,int bkcd,String dbcr)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		PreparedStatement ps2 = null;
		ResultSet rst2=null;
		PreparedStatement ps4 = null;
		ResultSet  rs4 = null;
		Connection con=null;
		int i=0;
		int ii=0;
		int iii=0;
		int sdiv=div;
		int wbk=bkcd;
		
		if(wbk==95)
			bkcd=51;
		
		if(wbk==90)
			bkcd=41;
		depo=BaseClass.loginDt.getCmp_code();
		try {
			con=ConnectionFactory.getConnection();
			String query1 ="select IFNULL(max(inv_no),0) from invfst where fin_year=? and  div_code=1 and depo_code=? and doc_type=? and  ifnull(del_tag,'')<>'D' "; 
			
			
			con.setAutoCommit(false);
			
			
			

				ps1 =con.prepareStatement(query1);
				ps1.setInt(1, year);
				ps1.setInt(2, depo);
				ps1.setInt(3, bkcd);
				rst =ps1.executeQuery();

				if(rst.next())
				{
					i =rst.getInt(1); 
					System.out.println("YEHA PER BHI AAYA KYA "+i);
				}

			
			
			con.commit();
			con.setAutoCommit(true);
			rst.close();
			ps1.close();

		} catch (SQLException ex) {System.out.println("error hai "+ex);
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in BankVouDAO.getVouNo " + ex);
		i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}
				if(ps2 != null){ps2.close();}
				if(rst2 != null){rst2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in BankVouDAO.Connection.close "+e);
			}
		}

		return i;
	}	 

	
	
}
