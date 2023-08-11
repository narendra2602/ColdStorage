package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.coldstorage.dto.InvFstDto;
import com.coldstorage.dto.InvSndDto;
import com.coldstorage.dto.SaletaxDto;
import com.coldstorage.view.BaseClass;
 

public class SaleTaxDAO 
{
	 
	 
	 
	 public List saleTaxPrint(int year,int depo,int div,Date sdate,Date edate,int doc_tp)
	   {
		  
		 List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   SaletaxDto inv=null;
		   String query=null;
		   int wdepo=depo;
		   Double gsale1,gsale2,gsale3,gsale10,st5,st13,st10,exem,exem10,dis5,dis13,dis10,cramt,dramt,net,disex,cst1,cst2,freight,st3,scamt,scamt1,st51,taxable51,scamt51,gsale9;
		   Double gtaxable1,gtaxable2,gtaxable3,gtaxable9,gtaxable10,gtaxablef1,gtaxablef2,gtaxablef3,gtaxablef9,gtaxablef10,gross,ltax1per,ltax2per,ltax3per,ltax9per;
		   double gfreeval1,gfreeval2,gfreeval3,gfreeval9,gfreeval10,gfreetax10;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try{
			    con=ConnectionFactory.getConnection();
			    
			    if (depo==61 || depo == 82 || depo == 40  )
			    {
			    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+ //5
				"(f.lsale1-f.taxfree1),((f.lsale2-f.taxfree2)+(f.lsale9-f.taxfree9)),(f.lsale3-f.taxfree3),(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3, "+
				"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," +
				"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9, "+  
				"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
				" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code,f.prt_type,(f.ltax9_amt+f.freetax9),f.sc_per,f.sc_per1,f.lsale9   " +
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=?  and p.depo_code=? order by f.inv_no";
			    	
			    }
			    else if (depo==20 || depo==25  )
			    {
				    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
							"(f.lsale1-f.taxfree1),(f.lsale2+f.lsale9-f.taxfree2-f.taxfree9),(f.lsale3-f.taxfree3),(f.ltax1_amt+f.freetax1),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
							"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
							"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +
							"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,(f.ltax3_amt+f.freetax3)," +
							"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9,   "+
							"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round, "+  
							" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code,f.prt_type,(f.ltax9_amt+f.freetax9),f.sc_per,f.sc_per1,f.lsale9   " +
							"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
							"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
							" and p.div_code=?  and p.depo_code=? order by f.inv_no";

			    }
			    else if (depo==1031)
			    {
			    	depo=31;
			    	
			    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
				"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +
				"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," +
				"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
				"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
				" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code,f.prt_type,(f.ltax9_amt+f.freetax9),f.sc_per,f.sc_per1,f.lsale9   " +
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=?  and p.depo_code=? order by f.party_code,f.inv_no";
			    }

			    
			    else if (depo==2031)
			    {
			    	depo=31;
			    	
			    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
				"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +
				"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," +
				"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
				"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
				" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code,f.prt_type,(f.ltax9_amt+f.freetax9),f.sc_per,f.sc_per1,f.lsale9   " +
				"from invfst as f,partyfst as p where f.depo_code=? and f.doc_type = ? " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=f.div_code  and p.depo_code=? order by f.div_code,f.inv_date,f.inv_no";
			    }

	  		    
			    else if (depo==1)
			    {
			    query="select concat('CWH',f.inv_lo,'/',RIGHT(f.inv_no,5)),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
				"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +
				"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," +
				"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
				"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
				" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code,f.div_code,(f.ltax9_amt+f.freetax9),f.sc_per,f.sc_per1,f.lsale9   " +
				"from invfst as f,partyfst as p where f.div_code in (1,2,3,9,10,20) and f.depo_code=? and f.doc_type = ? " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=f.div_code  and p.depo_code=? order by f.div_code,f.inv_no";
			    }

			    

			    else if (depo==58 || depo==59)
			    {
			    query="select concat(f.inv_yr,f.inv_lo,right(f.inv_no,5)),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
				"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +  // upto 26
				"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," + // upto 38
				"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
				"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
				" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code,f.prt_type,(f.ltax9_amt+f.freetax9),f.sc_per,f.sc_per1,f.lsale9   " +
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=?  and p.depo_code=? order by f.inv_no";
			    }

			    else if(depo==21)
			    {
			    query="select if(f.doc_type=40,concat(f.inv_yr,f.inv_lo,f.inv_no),concat(f.inv_yr,f.inv_lo,'C',right(f.inv_no,4))),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
				"f.lsale1,f.lsale2,f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +  // upto 26
				"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," + // upto 38
				"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
				"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
				" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code,f.prt_type,(f.ltax9_amt+f.freetax9),f.sc_per,f.sc_per1,f.lsale9  " +
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=?  and p.depo_code=? order by f.inv_no ";
			    }

			    
			    else
			    {
			    query="select if(f.doc_type=40,concat(f.inv_yr,f.inv_lo,f.inv_no),concat(f.inv_yr,f.inv_lo,'C',right(f.inv_no,4))),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
				"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +  // upto 26
				"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," + // upto 38
				"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
				"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
				" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code,f.prt_type,(f.ltax9_amt+f.freetax9),f.sc_per,f.sc_per1,f.lsale9   " +
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=?  and p.depo_code=? order by f.inv_no ";
			    }
			      st = con.prepareStatement(query);
			      if (depo==1)
			      {
			    	  st.setInt(1, depo);
			    	  st.setInt(2, doc_tp);
			    	  st.setDate(3, sDate);
			    	  st.setDate(4, eDate);
			    	  st.setInt(5, depo);
			    	  
			      }

			      else if (wdepo==2031)
			      {
			    	  st.setInt(1, depo);
			    	  st.setInt(2, doc_tp);
			    	  st.setDate(3, sDate);
			    	  st.setDate(4, eDate);
			    	  st.setInt(5, depo);
			      }

			      else
			      {
			    	  st.setInt(1, div);
			    	  st.setInt(2, depo);
			    	  st.setInt(3, doc_tp);
			    	  st.setDate(4, sDate);
			    	  st.setDate(5, eDate);
			    	  st.setInt(6, div);
			    	  st.setInt(7, depo);
			      }
			      rs=st.executeQuery();
                  data = new ArrayList();
                  gsale1=0.00;
                  gsale2=0.00;
                  gsale3=0.00;
                  gsale9=0.00;
                  gsale10=0.00;
                  ltax1per=0.00;
                  ltax2per=0.00;
                  ltax3per=0.00;
                  ltax9per=0.00;
                  
                  st5=0.00;
                  st51=0.00;   // 5% food tax olyte
                  taxable51=0.00;
                  st13=0.00;
                  st10=0.00;
                  exem=0.00;
                  exem10=0.00;
                  
                  dis5=0.00;
                  dis13=0.00;
                  dis10=0.00;
                  
                  disex=0.00;
                  cramt=0.00;
                  dramt=0.00;
                  cst1=0.00;
                  cst2=0.00;
                  freight=0.00;
                  net=0.00;
                  gtaxable1=0.00;
                  gtaxable2=0.00;
                  gtaxable3=0.00;
                  gtaxable9=0.00;
                  gtaxable10=0.00;
                  gfreeval1=0.00;
                  gfreeval2=0.00;
                  gfreeval3=0.00;
                  gfreeval9=0.00;
                  gfreeval10=0.00;

                  gtaxablef1=0.00;
                  gtaxablef2=0.00;
                  gtaxablef3=0.00;
                  gtaxablef9=0.00;
                  gtaxablef10=0.00;
                  gfreetax10=0.00;
                  gross=0.00;
                  st3=0.00;
                  scamt=0.00;
                  scamt51=0.00;
                  scamt1=0.00;
			    while(rs.next())
			    {
			    	inv = new SaletaxDto();
			    	inv.setInv_no(rs.getString(1));
			    	inv.setInv_date(rs.getDate(2));
			    	inv.setParty_name(rs.getString(3));
			    	inv.setCity(rs.getString(4));
			    	inv.setTin(rs.getString(5));
			    	inv.setSale1(rs.getDouble(6));
			    	inv.setSale2(rs.getDouble(7));
			    	inv.setSale3(rs.getDouble(8));
			    	inv.setSale9(rs.getDouble(68));
			    	inv.setSt5(rs.getDouble(9));
			    	inv.setSt51(0.00);
			    	inv.setTaxable51(0.00);
			    	inv.setTaxable2(rs.getDouble(18));
			    	inv.setSc_amt(rs.getDouble(36));
			    	inv.setSc_amt1(rs.getDouble(37));

			    	
/*			    	if (rs.getDouble(40)==5.00 || rs.getDouble(40)==5.50 || rs.getDouble(40)==6.00)  // food tax-per
			    	 {
					    	inv.setSt5(rs.getDouble(9)+rs.getDouble(10));
					    	st51+=rs.getDouble(10);
					    	taxable51+=rs.getDouble(18);
			    		    inv.setSt13(0.00);

			    	 }
			    	 else
*/
			    	inv.setSt13(rs.getDouble(10));
			    	if(rs.getString(64)!=null && rs.getString(64).equals("J"))
			    	{
				    	inv.setSt51(rs.getDouble(10)-rs.getDouble(65));
				    	inv.setTaxable51(rs.getDouble(18));
//				    	inv.setScamt51(rs.getDouble(37));

				    	st51+=rs.getDouble(10)-rs.getDouble(65);
////				    	scamt51+=rs.getDouble(37);
				    	taxable51+=rs.getDouble(18);
		    		    inv.setSt13(rs.getDouble(65));  //TAX9
		    		    inv.setTaxable2(0.00);
		    		    inv.setSc_amt1(0.00);
		    		    if(rs.getDouble(65)>0)
		    		    {
		    		    	if(depo==59 || depo==55)
		    		    	{
		    		    		inv.setSc_amt1(roundTwoDecimals(rs.getDouble(65)*rs.getDouble(67))/100);
		    		    		scamt1+=inv.getSc_amt1();
		    		    	}
		    		    	else
		    		    	{
		    		    		inv.setSc_amt1(roundTwoDecimals(rs.getDouble(20)*rs.getDouble(67))/100);
		    		    		scamt1+=inv.getSc_amt1();
		    		    		
		    		    	}
		    		    }
	    		    	if(depo==59 || depo==55)
	    		    	{
		    		    	inv.setScamt51(roundTwoDecimals((rs.getDouble(10)-rs.getDouble(65))*rs.getDouble(67))/100);
		    		    	scamt51+=inv.getScamt51();
	    		    	}
	    		    	else
	    		    	{
		    		    	inv.setScamt51(roundTwoDecimals(rs.getDouble(18)*rs.getDouble(67))/100);
		    		    	scamt51+=inv.getScamt51();
	    		    		
	    		    	}
			    	}
			    	else
			    	{
				    	gtaxable2+=rs.getDouble(18);
				    	scamt1+=rs.getDouble(37);
			    	}
			    	inv.setExempted(rs.getDouble(11));
			    	inv.setDisc5(rs.getDouble(12));
			    	inv.setDisc10(rs.getDouble(13));
			    	inv.setCramt(rs.getDouble(14));
			    	inv.setDramt(rs.getDouble(15)); 
			    	inv.setNetamt(rs.getDouble(16));
			    	inv.setTaxable1(rs.getDouble(17));
			    	
			    	inv.setTaxable3(rs.getDouble(19));
			    	inv.setTaxable9(rs.getDouble(20));
			    	inv.setTaxablef1(rs.getDouble(21));
			    	inv.setTaxablef2(rs.getDouble(22));
			    	inv.setTaxablef3(rs.getDouble(23));
			    	inv.setTaxablef9(rs.getDouble(24));
			    	inv.setGrossamt(rs.getDouble(25));
			    	inv.setDiscexempted(rs.getDouble(26));
			    	inv.setCtax1_amt(rs.getDouble(31));
			    	inv.setCtax2_amt(rs.getDouble(32));
			    	inv.setFreight(rs.getDouble(35));

			    	inv.setSt3(rs.getDouble(38));
			    	inv.setLtax1_per(rs.getDouble(39));
			    	inv.setLtax2_per(rs.getDouble(40));
			    	inv.setLtax3_per(rs.getDouble(41));
			    	inv.setLtax9_per(rs.getDouble(42));
			    	inv.setCases(rs.getInt(43));
			    	inv.setTax_free1(rs.getDouble(44));
			    	inv.setTax_free2(rs.getDouble(45));
			    	inv.setTax_free3(rs.getDouble(46));
			    	inv.setTax_free9(rs.getDouble(47));
			    	inv.setFreeval1(rs.getDouble(48));
			    	inv.setFreeval2(rs.getDouble(49));
			    	inv.setFreeval3(rs.getDouble(50));
			    	inv.setFreeval9(rs.getDouble(51));
					if (rs.getDouble(52)!=0)
						inv.setPaise(rs.getDouble(52));
					if (rs.getDouble(53)!=0)
						inv.setPaise(rs.getDouble(53));
					
					
					inv.setSale10(rs.getDouble(54));
			    	inv.setLtax10_per(rs.getDouble(55));
			    	inv.setLtax10_amt(rs.getDouble(56));
			    	inv.setTaxable10(rs.getDouble(57));
			    	inv.setDiscount_amt10(rs.getDouble(58));
			    	inv.setTaxablef10(rs.getDouble(59));
			    	inv.setFreetax10_amt(rs.getDouble(60));
			    	inv.setFreeval10(rs.getDouble(61));
			    	inv.setTax_free10(rs.getDouble(62));
			    	inv.setParty_code(rs.getString(63));
					inv.setCode(0);   // div code
					inv.setRemark("");
					if (depo==1)
						inv.setCode(rs.getInt(64));   // div code
					else
						inv.setRemark(rs.getString(64)); // prt_type 'J' for Olyte
			    	
			    	
			    	gsale1+=rs.getDouble(6);
			    	gsale2+=rs.getDouble(7);
			    	gsale3+=rs.getDouble(8);
			    	gsale9+=rs.getDouble(68);
			    	gsale10+=rs.getDouble(54);

//			    	st5+=rs.getDouble(9);
//			    	st13+=rs.getDouble(10);
			    	
			    	st5+=inv.getSt5();
			    	st13+=inv.getSt13();
			    	st10+=rs.getDouble(56);

			    	
			    	st3+=rs.getDouble(38);
			    	exem+=rs.getDouble(11);
			    	exem10+=rs.getDouble(62);

			    	dis5+=rs.getDouble(12);
			    	dis13+=rs.getDouble(13);
			    	dis10+=rs.getDouble(58);
			    	
			    	
			    	cramt+=rs.getDouble(14);
			    	dramt+=rs.getDouble(15);
			    	net+=rs.getDouble(16);
			    	gtaxable1+=rs.getDouble(17);

			    	gtaxable3+=rs.getDouble(19);
			    	gtaxable9+=rs.getDouble(20);
			    	gtaxable10+=rs.getDouble(57);
			    	
			    	gtaxablef1+=rs.getDouble(21);
			    	gtaxablef2+=rs.getDouble(22);
			    	gtaxablef3+=rs.getDouble(23);
			    	gtaxablef9+=rs.getDouble(24);
			    	gtaxablef10+=rs.getDouble(59);

			    	
			    	gfreeval1+=rs.getDouble(48);
			    	gfreeval2+=rs.getDouble(49);
			    	gfreeval3+=rs.getDouble(50);
			    	gfreeval9+=rs.getDouble(51);
			    	gfreeval10+=rs.getDouble(61);
			    	
			    	gfreetax10+=rs.getDouble(60);

			    	gross+=rs.getDouble(25);
			    	disex+=rs.getDouble(26);
			    	cst1+=rs.getDouble(31);
			    	cst2+=rs.getDouble(32);
			    	freight+=rs.getDouble(35);
			    	
			    	scamt+=rs.getDouble(36);
			    	if(rs.getDouble(39)>0)
			    		ltax1per=(rs.getDouble(39));
			    	if(rs.getDouble(40)>0)
			    		ltax2per=(rs.getDouble(40));
			    	if(rs.getDouble(41)>0)
			    		ltax3per=(rs.getDouble(41));
			    	if(rs.getDouble(42)>0)
			    		ltax2per=(rs.getDouble(42));

			    	
			    	inv.setDash(0);
			    	data.add(inv);
			    }

		    	inv = new SaletaxDto();
		    	inv.setTin("Grand Total");
		    	inv.setSale1(gsale1);
		    	inv.setSale2(gsale2);
		    	inv.setSale3(gsale3);
		    	inv.setSale9(gsale9);
		    	inv.setSale10(gsale10);

		    	inv.setSt5(st5);
		    	inv.setSt51(st51);  // 5% tax on olyte
		    	inv.setSt13(st13);
		    	inv.setSt3(st3);
		    	inv.setLtax10_amt(st10);
		    	
		    	inv.setExempted(exem);
		    	inv.setTax_free10(exem10);
		    	
		    	inv.setDisc5(dis5);
		    	inv.setDisc10(dis13);
		    	inv.setDiscount_amt10(dis10);
		    	
		    	inv.setCramt(cramt);
		    	inv.setDramt(dramt);
		    	inv.setNetamt(net);
		    	inv.setTaxable1(gtaxable1);
		    	inv.setTaxable2(gtaxable2);
		    	inv.setTaxable3(gtaxable3);
		    	inv.setTaxable9(gtaxable9);
		    	inv.setTaxable10(gtaxable10);

		    	inv.setTaxablef1(gtaxablef1);
		    	inv.setTaxablef2(gtaxablef2);
		    	inv.setTaxablef3(gtaxablef3);
		    	inv.setTaxablef9(gtaxablef9);
		    	inv.setTaxablef10(gtaxablef10);
		    	inv.setFreetax10_amt(gfreetax10);

		    	inv.setDiscexempted(disex);
		    	inv.setCtax1_amt(cst1);
		    	inv.setCtax2_amt(cst2);
		    	inv.setFreight(freight);
		    	inv.setGrossamt(gross);
		    	inv.setFreeval1(gfreeval1);
		    	inv.setFreeval2(gfreeval2);
		    	inv.setFreeval3(gfreeval3);
		    	inv.setFreeval9(gfreeval9);
		    	inv.setFreeval10(gfreeval10);

		    	inv.setLtax1_per(ltax1per);
		    	inv.setLtax2_per(ltax2per);
		    	inv.setLtax3_per(ltax3per);
		    	inv.setLtax9_per(ltax9per);
		    	inv.setTaxable51(taxable51);
		    	inv.setScamt51(scamt51);
		    	inv.setSc_amt(scamt);
		    	inv.setSc_amt1(scamt1);
		    	inv.setDash(3);

		    	

		    	
		    	
		    	
		    	data.add(inv);
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error "+ee);}		   
		  
			finally {
				try {
					    
		             if(rs != null){rs.close();}
		             if(st != null){st.close();}
		             if(con != null){con.close();}
				} catch (SQLException ee) {
					System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
				  }
			}
			
			return data;
	   }

	 
	 
	 
	 public List saleTaxPrint1(String smon,int year,int depo,int div,Date sdate,Date edate)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   SaletaxDto inv=null;
		   String query=null;
		   String invno;
		   Double gsale1,gsale2,gsale3,st5,st13,exem,dis5,dis13,cramt,dramt,net;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try{
			    con=ConnectionFactory.getConnection();
			    
			    if (depo==61)
			    {
			    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,p.mpst_no, "+
				"(f.lsale1-f.taxfree1),((f.lsale2-f.taxfree2)+(f.lsale9-f.taxfree9)),(f.lsale3-f.taxfree3),(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt "+
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type =41 " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=?  and p.depo_code=? order by f.inv_no";
			    	
			    }
			    else
			    {
			    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,p.mpst_no, "+
				"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt "+
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type =41 " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=?  and p.depo_code=? order by f.inv_no";
			    }
			      st = con.prepareStatement(query);
			      st.setInt(1, div);
			      st.setInt(2, depo);
			      st.setDate(3, sDate);
			      st.setDate(4, eDate);
			      st.setInt(5, div);
			      st.setInt(6, depo);
			      rs=st.executeQuery();
                data = new ArrayList();
                gsale1=0.00;
                gsale2=0.00;
                gsale3=0.00;
                st5=0.00;
                st13=0.00;
                exem=0.00;
                dis5=0.00;
                dis13=0.00;
                cramt=0.00;
                dramt=0.00;
                net=0.00;
                
			    while(rs.next())
			    {
			    	inv = new SaletaxDto();
					invno= rs.getString(1)+rs.getString(2)+"C"+rs.getString(3).substring(1);

			    	inv.setInv_no(rs.getString(1));
			    	inv.setInv_date(rs.getDate(2));
			    	inv.setParty_name(rs.getString(3));
			    	inv.setCity(rs.getString(4));
			    	inv.setTin(rs.getString(5));
			    	inv.setSale1(rs.getDouble(6));
			    	inv.setSale2(rs.getDouble(7));
			    	inv.setSale3(rs.getDouble(8));
			    	inv.setSt5(rs.getDouble(9));
			    	inv.setSt13(rs.getDouble(10));
			    	inv.setExempted(rs.getDouble(11));
			    	inv.setDisc5(rs.getDouble(12));
			    	inv.setDisc10(rs.getDouble(13));
			    	inv.setCramt(rs.getDouble(14));
			    	inv.setDramt(rs.getDouble(15));
			    	inv.setNetamt(rs.getDouble(16));
			    	inv.setTaxable1(rs.getDouble(17));
			    	inv.setTaxable2(rs.getDouble(18));
			    	inv.setTaxable3(rs.getDouble(19));
			    	inv.setTaxable9(rs.getDouble(20));
			    	inv.setTaxablef1(rs.getDouble(21));
			    	inv.setTaxablef2(rs.getDouble(22));
			    	inv.setTaxablef3(rs.getDouble(23));
			    	inv.setTaxablef9(rs.getDouble(24));
			    	inv.setGrossamt(rs.getDouble(25));
			    	gsale1+=rs.getDouble(6);
			    	gsale2+=rs.getDouble(7);
			    	gsale3+=rs.getDouble(8);
			    	st5+=rs.getDouble(9);
			    	st13+=rs.getDouble(10);
			    	exem+=rs.getDouble(11);
			    	dis5+=rs.getDouble(12);
			    	dis13+=rs.getDouble(13);
			    	cramt+=rs.getDouble(14);
			    	dramt+=rs.getDouble(15);
			    	net+=rs.getDouble(16);
			    	inv.setDash(0);
			    	data.add(inv);
			    }

		    	inv = new SaletaxDto();
		    	inv.setTin("Grand Total");
		    	inv.setSale1(gsale1);
		    	inv.setSale2(gsale2);
		    	inv.setSale3(gsale3);
		    	inv.setSt5(st5);
		    	inv.setSt13(st13);
		    	inv.setExempted(exem);
		    	inv.setDisc5(dis5);
		    	inv.setDisc10(dis13);
		    	inv.setCramt(cramt);
		    	inv.setDramt(dramt);
		    	inv.setNetamt(net);
		    	inv.setDash(3);
		    	data.add(inv);
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error "+ee);}		   
		  
			finally {
				try {
					    
		             if(rs != null){rs.close();}
		             if(st != null){st.close();}
		             if(con != null){con.close();}
				} catch (SQLException ee) {
					System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
				  }
			}
			
			return data;
	   }
	 
	 
	 
	 
	 public List crnPrint(String smon,int year,int depo,int div,Date sdate,Date edate,int doctp)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   PreparedStatement ps2 =null;
		   PreparedStatement ps3 =null;
		   ResultSet rs= null; 
		   ResultSet rs2= null; 
		   ResultSet rs3= null; 
		   SaletaxDto inv=null;
		   double tax1=0.00;
		   double tax2=0.00;
		   
		   String query=null;
		   double gsale1,gsale2,gsale3,st5,st13,exem,dis5,dis13,cramt,dramt,net,scamt,gfreight;
		   double tp1,tp2,tp3,tp4,tp5,tp6,tp7,tp8,tp9;
		   int winv=0;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try{
			    con=ConnectionFactory.getConnection();
			    
			    query="select concat(ifnull(f.inv_yr,0),ifnull(f.inv_lo,'00'),f.inv_no),f.inv_date,p.mac_name,p.mcity,p.mpst_no, "+
				"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),(f.discount_amt+f.discount_amt3),f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.inv_no,ifnull(f.remark,'')," +
				"f.ltax1_per,f.ltax2_per,"+
				"f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.sc_amt,f.sc_amt1, "+
				" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,(f.discount_amt10-f.discount_round-f.interest_round),ifnull(f.freight,0)  " +
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and f.inv_date< '2017-07-01' and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code in (?,4)  and p.depo_code=? and ifnull(p.del_tag,'')<>'D' order by f.inv_no";

			    
				String sndQ =" select s.strn_tp,s.sqty,s.srate_net from invsnd as s where s.fin_year=?  "+ 
						" and s.div_code=?  and s.sdepo_code=? and s.sdoc_type =? and s.sinv_no=? and ifnull(s.del_tag,'')<>'D'" +
						" order by s.sinv_no ";
				ps2 = con.prepareStatement(sndQ);

				String tax=" select tax_per,tax_type from taxmast where depo_code=? and state_code=? and sdate >= ? and edate <=? ";

				ps3 = con.prepareStatement(tax);
			    ps3.setInt(1, depo);
			    ps3.setInt(2, 1);
			    ps3.setDate(3, sDate);
			    ps3.setDate(4, eDate);
				rs3=ps3.executeQuery();

				while (rs3.next())
			    {
			    	if (rs3.getString(2).equals("A"))
			    		tax1=rs3.getDouble(1);
			    	
			    	if (rs3.getString(2).equals("B"))
			    		tax2=rs3.getDouble(1);
			    	
			    }
			    rs3.close();
			    
			      st = con.prepareStatement(query);
			      st.setInt(1, div);
			      st.setInt(2, depo);
			      st.setInt(3, doctp);
			      st.setDate(4, sDate);
			      st.setDate(5, eDate);
			      st.setInt(6, div);
			      st.setInt(7, depo);
			      rs=st.executeQuery();
                data = new ArrayList();
                gfreight=0.00;
                gsale1=0.00;
                gsale2=0.00;
                gsale3=0.00;
                st5=0.00;
                st13=0.00;
                scamt=0.00;
                exem=0.00;
                dis5=0.00;
                dis13=0.00;
                cramt=0.00;
                dramt=0.00;
                net=0.00;
                tp1=0.00;
                tp2=0.00;
                tp3=0.00;
                tp4=0.00;
                tp5=0.00;
                tp6=0.00;
                tp7=0.00;
                tp8=0.00;
                tp9=0.00;
                
                
			    while(rs.next())
			    {
					if (rs.getInt(26)!=winv)
					{
						winv=rs.getInt(26);

					ps2.setInt(1, year);
					ps2.setInt(2, div);
					ps2.setInt(3, depo);
					ps2.setInt(4, doctp);
					ps2.setInt(5, rs.getInt(26));
					rs2= ps2.executeQuery();
					Vector row=new Vector();
					while (rs2.next())
					{
						switch (rs2.getInt(1))
						{
							case 1: tp1+=rs2.getInt(2)*rs2.getDouble(3);
									break;
							case 2: tp2+=rs2.getInt(2)*rs2.getDouble(3);
									break;
							case 3: tp3+=rs2.getInt(2)*rs2.getDouble(3);
									break;
							case 4: tp4+=rs2.getInt(2)*rs2.getDouble(3);
									break;
							case 5: tp5+=rs2.getInt(2)*rs2.getDouble(3);
									break;
							case 6: tp6+=rs2.getInt(2)*rs2.getDouble(3);
									break;
							case 7: tp7+=rs2.getInt(2)*rs2.getDouble(3);
									break;
							case 8: tp8+=rs2.getInt(2)*rs2.getDouble(3);
									break;
							case 9: tp9+=rs2.getInt(2)*rs2.getDouble(3);
									break;
						}
					}
					rs2.close();
			    	
						inv = new SaletaxDto();
						inv.setInv_no(rs.getString(1));
						inv.setInv_date(rs.getDate(2));
						inv.setParty_name(rs.getString(3));
						inv.setCity(rs.getString(4));
						inv.setTin(rs.getString(5));
						inv.setSale1(rs.getDouble(6));
						inv.setSale2(rs.getDouble(7));
						inv.setSale3(rs.getDouble(8));
						inv.setSt5(rs.getDouble(9));
						inv.setSt13(rs.getDouble(10));
						inv.setExempted(rs.getDouble(11));
						inv.setDisc5(rs.getDouble(12));
						inv.setDisc10(rs.getDouble(13));
						inv.setCramt(rs.getDouble(14));
						inv.setDramt(rs.getDouble(15));
						inv.setNetamt(rs.getDouble(16));
						inv.setTaxable1(rs.getDouble(17)+rs.getDouble(19)+rs.getDouble(21)+rs.getDouble(23));
						inv.setTaxable2(rs.getDouble(18)+rs.getDouble(20)+rs.getDouble(22)+rs.getDouble(24));
						inv.setGrossamt(rs.getDouble(25));
						inv.setRemark(rs.getString(27));
						inv.setLtax1_per(tax1);
						inv.setLtax2_per(tax2);
						inv.setCtax1_amt(rs.getDouble(30));
						inv.setCtax2_amt(rs.getDouble(31));
						inv.setCst_amt(rs.getDouble(30)+rs.getDouble(31));
						inv.setSc_amt(rs.getDouble(34));
						inv.setSc_amt1(rs.getDouble(35));
						inv.setSale10(rs.getDouble(36));
						inv.setLtax10_per(rs.getDouble(37));
						inv.setLtax10_amt(rs.getDouble(38));
						inv.setTaxable10(rs.getDouble(39));
						inv.setDiscount_amt10(rs.getDouble(40));
						inv.setFreight(rs.getDouble(41));
						inv.setStrn1(tp1);
						inv.setStrn2(tp2);
						inv.setStrn3(tp3);
						inv.setStrn4(tp4);
						inv.setStrn5(tp5);
						inv.setStrn6(tp6);
						inv.setStrn7(tp7);
						inv.setStrn8(tp8);
						inv.setStrn9(tp9);
						inv.setRemark(rs.getString(27));
						gfreight+=rs.getDouble(41);
						gsale1+=rs.getDouble(6);
						gsale2+=rs.getDouble(7);
						gsale3+=rs.getDouble(8);
						st5+=rs.getDouble(9);
						st13+=rs.getDouble(10);
						scamt+=rs.getDouble(34)+rs.getDouble(35);
						
						exem+=rs.getDouble(11);
						dis5+=rs.getDouble(12);
						dis13+=rs.getDouble(13);
						cramt+=rs.getDouble(14);
						dramt+=rs.getDouble(15);
						net+=rs.getDouble(16);
						inv.setDash(0);
						data.add(inv);
						tp1=0.00;
						tp2=0.00;
						tp3=0.00;
						tp4=0.00;
						tp5=0.00;
						tp6=0.00;
						tp7=0.00;
						tp8=0.00;
						tp9=0.00;
					} // end of if inv
			    	
			    }

		    	inv = new SaletaxDto();
		    	inv.setTin("Grand Total");
		    	inv.setFreight(gfreight);
		    	inv.setSale1(gsale1);
		    	inv.setSale2(gsale2);
		    	inv.setSale3(gsale3);
		    	inv.setSt5(st5);
		    	inv.setSt13(st13);
		    	inv.setSc_amt(scamt);
		    	inv.setExempted(exem);
		    	inv.setDisc5(dis5);
		    	inv.setDisc10(dis13);
		    	inv.setCramt(cramt);
		    	inv.setDramt(dramt);
		    	inv.setNetamt(net);
		    	inv.setDash(3);
		    	data.add(inv);
			    ps2.close();
			    ps3.close();
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){
				System.out.println("error "+ee);
				ee.printStackTrace();
			}		   
		  
			finally {
				try {
		             if(rs2!= null){rs2.close();}
		             if(ps2 != null){ps2.close();}
		             if(rs3!= null){rs3.close();}
		             if(ps3 != null){ps3.close();}
					    
		             if(rs != null){rs.close();}
		             if(st != null){st.close();}
		             if(con != null){con.close();}
				} catch (SQLException ee) {
					System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
				  }
			}
			
			return data;
	   }
	 
	 public List RegRepo8(int year,int depo,int div,Date sdate,Date edate,int doc_tp)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   SaletaxDto inv=null;
		   String query=null;
		   double gsale1,gsale2,gsale3,st5,st13,exem,dis5,dis13,cramt,dramt,net,disex,cst1,cst2,freight,st3;
		   double gtaxable1,gtaxable2,gtaxable3,gtaxable9,gtaxablef1,gtaxablef2,gtaxablef3,gtaxablef9,gross;
		   double octroi=0.00;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try{
			    con=ConnectionFactory.getConnection();
			    
			    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+ //5
				"(f.lsale1-f.taxfree1),(f.lsale2+f.lsale9-f.taxfree2-f.taxfree9),(f.lsale3-f.taxfree3),(f.ltax1_amt+f.freetax1),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
				"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," + //16
				"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.mfg_amt,f.discount_amt3," + //26
				"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," + //38
				"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +  //47
				"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,(f.ltax3_amt+f.freetax3),octroi  "+  //55
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
				" and p.div_code=?  and p.depo_code=? order by f.inv_no";

			    
			    st = con.prepareStatement(query);
			      st.setInt(1, div);
			      st.setInt(2, depo);
			      st.setInt(3, doc_tp);
			      st.setDate(4, sDate);
			      st.setDate(5, eDate);
			      st.setInt(6, div);
			      st.setInt(7, depo);
			      rs=st.executeQuery();
                data = new ArrayList();
                gsale1=0.00;
                gsale2=0.00;
                gsale3=0.00;
                st5=0.00;
                st13=0.00;
                exem=0.00;
                dis5=0.00;
                dis13=0.00;
                disex=0.00;
                cramt=0.00;
                dramt=0.00;
                cst1=0.00;
                cst2=0.00;
                freight=0.00;
                net=0.00;
                gtaxable1=0.00;
                gtaxable2=0.00;
                gtaxable3=0.00;
                gtaxable9=0.00;
                gtaxablef1=0.00;
                gtaxablef2=0.00;
                gtaxablef3=0.00;
                gtaxablef9=0.00;
                gross=0.00;
                st3=0.00;
			    while(rs.next())
			    {
			    	inv = new SaletaxDto();
			    	inv.setInv_no(rs.getString(1));
			    	inv.setInv_date(rs.getDate(2));
			    	inv.setParty_name(rs.getString(3));
			    	inv.setCity(rs.getString(4));
			    	inv.setTin(rs.getString(5));
		    		inv.setSale1(rs.getDouble(6));
		    		inv.setSale2(rs.getDouble(7));
		    		inv.setSale3(rs.getDouble(8));
			    	inv.setSt5(rs.getDouble(9));
			    	inv.setSt13(rs.getDouble(10));
			    	inv.setExempted(rs.getDouble(11));
			    	inv.setDisc5(rs.getDouble(12));
			    	inv.setDisc10(rs.getDouble(13));
			    	inv.setCramt(rs.getDouble(14));
			    	inv.setDramt(rs.getDouble(15));
			    	inv.setNetamt(rs.getDouble(16));
			    	inv.setTaxable1(rs.getDouble(17));
			    	inv.setTaxable2(rs.getDouble(18));
			    	inv.setTaxable3(rs.getDouble(19));
			    	inv.setTaxable9(rs.getDouble(20));
			    	inv.setTaxablef1(rs.getDouble(21));
			    	inv.setTaxablef2(rs.getDouble(22));
			    	inv.setTaxablef3(rs.getDouble(23));
			    	inv.setTaxablef9(rs.getDouble(24));
			    	inv.setGrossamt(rs.getDouble(25));
			    	inv.setDiscexempted(rs.getDouble(26));
			    	inv.setCtax1_amt(rs.getDouble(31));
			    	inv.setCtax2_amt(rs.getDouble(32));
			    	inv.setFreight(rs.getDouble(35));
			    	inv.setSc_amt(rs.getDouble(36));
			    	inv.setSc_amt1(rs.getDouble(37));
			    	inv.setSt3(rs.getDouble(38));
			    	inv.setLtax1_per(rs.getDouble(39));
			    	inv.setLtax2_per(rs.getDouble(40));
			    	inv.setLtax3_per(rs.getDouble(41));
			    	inv.setLtax9_per(rs.getDouble(42));
			    	inv.setCases(rs.getInt(43));
			    	inv.setTax_free1(rs.getDouble(44));
			    	inv.setTax_free2(rs.getDouble(45));
			    	inv.setTax_free3(rs.getDouble(46));
			    	inv.setTax_free9(rs.getDouble(47));
			    	inv.setFreeval1(rs.getDouble(48));
			    	inv.setFreeval2(rs.getDouble(49));
			    	inv.setFreeval3(rs.getDouble(50));
			    	inv.setFreeval9(rs.getDouble(51));
					if (rs.getDouble(52)!=0)
						inv.setPaise(rs.getDouble(52));
					if (rs.getDouble(53)!=0)
						inv.setPaise(rs.getDouble(53));
					
					inv.setSt3(rs.getDouble(54)); // ltax_amt3
					inv.setLtax10_amt(rs.getDouble(55));// octroi
					
					gsale1+=rs.getDouble(6);
			    	gsale2+=rs.getDouble(7);
			    	gsale3+=rs.getDouble(8);
			    	st5+=rs.getDouble(9);
			    	st13+=rs.getDouble(10);
			    	//st3+=rs.getDouble(38);
			    	exem+=rs.getDouble(11);
			    	dis5+=rs.getDouble(12);
			    	dis13+=rs.getDouble(13);
			    	cramt+=rs.getDouble(14);
			    	dramt+=rs.getDouble(15);
			    	net+=rs.getDouble(16);
			    	gtaxable1+=rs.getDouble(17);
			    	gtaxable2+=rs.getDouble(18);
			    	gtaxable3+=rs.getDouble(19);
			    	gtaxable9+=rs.getDouble(20);
			    	gtaxablef1+=rs.getDouble(21);
			    	gtaxablef2+=rs.getDouble(22);
			    	gtaxablef3+=rs.getDouble(23);
			    	gtaxablef9+=rs.getDouble(24);
			    	gross+=rs.getDouble(25);
			    	disex+=rs.getDouble(26);
			    	cst1+=rs.getDouble(31);
			    	cst2+=rs.getDouble(32);
			    	freight+=rs.getDouble(35);
			    	st3+=rs.getDouble(54);
			    	octroi+=rs.getDouble(55);
			    	inv.setDash(0);
			    	data.add(inv);
			    }

		    	inv = new SaletaxDto();
		    	inv.setTin("Grand Total");
		    	inv.setSale1(gsale1);
		    	inv.setSale2(gsale2);
		    	inv.setSale3(gsale3);
		    	inv.setSt5(st5);
		    	inv.setSt13(st13);
		    	inv.setSt3(st3);
		    	inv.setExempted(exem);
		    	inv.setDisc5(dis5);
		    	inv.setDisc10(dis13);
		    	inv.setCramt(cramt);
		    	inv.setDramt(dramt);
		    	inv.setNetamt(net);
		    	inv.setTaxable1(gtaxable1);
		    	inv.setTaxable2(gtaxable2);
		    	inv.setTaxable3(gtaxable3);
		    	inv.setTaxable9(gtaxable9);
		    	inv.setTaxablef1(gtaxablef1);
		    	inv.setTaxablef2(gtaxablef2);
		    	inv.setTaxablef3(gtaxablef3);
		    	inv.setTaxablef9(gtaxablef9);
		    	inv.setDiscexempted(disex);
		    	inv.setCtax1_amt(cst1);
		    	inv.setCtax2_amt(cst2);
		    	inv.setFreight(freight);
		    	inv.setGrossamt(gross);
		    	inv.setLtax10_amt(octroi);
		    	inv.setDash(3);

		    	

		    	
		    	
		    	
		    	data.add(inv);
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error "+ee);}		   
		  
			finally {
				try {
					    
		             if(rs != null){rs.close();}
		             if(st != null){st.close();}
		             if(con != null){con.close();}
				} catch (SQLException ee) {
					System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
				  }
			}
			
			return data;
	   }

	 
	 public List MisRepo32(String smon,int year,int depo,int div,Date sdate,Date edate,int optn,int terCode)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement ps2 =null;
		   ResultSet rs2= null; 
		   PreparedStatement ps3 =null;
		   ResultSet rs3= null; 
		   SaletaxDto inv=null;
		   String order="";
		   double tp1,tp2,tp3,tp4,tp5,tp6,tp7,tp8,tp9;
		   int qtp1,qtp2,qtp3,qtp4,qtp5,qtp6,qtp7,qtp8,qtp9,hqcode,code;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try
		   {
			   con=ConnectionFactory.getConnection();
			   String columName ="";
			   order =" order by s.area_cd,s.inv_dsm,s.terr_cd,s.sprd_cd ";

			   if(optn==1)
			   columName = " and s.terr_cd="+terCode;

			   if(optn==3)
				   order =" order by s.sprd_cd ";

			   
			   String sndQ =" select s.sprd_cd,p.pname,p.pack ,s.strn_tp,if(sdoc_type=56,(s.sqty*-1),s.sqty),s.srate_mfg,s.terr_cd,s.mkt_year  from invsnd as s, prd as p where s.mkt_year=?  "+ 
					   " and s.div_code=?  and s.sdepo_code=? and s.sdoc_type in (41,56,57) and s.sinv_dt between ? and ? and ifnull(s.del_tag,'')<>'D' " +columName +
					   " and p.div_code=? and p.pcode=s.sprd_cd " +order;
			   
			   String hq=" select ter_name from hqmast where mkt_year =? and div_code=? and depo_Code=? " +
			   		" and ter_code=?";
			   
			   ps3 = con.prepareStatement(hq);
			   
			   
			   
			   ps2 = con.prepareStatement(sndQ);


			   data = new ArrayList();
			   tp1=0.00;
			   tp2=0.00;
			   tp3=0.00;
			   tp4=0.00;
			   tp5=0.00;
			   tp6=0.00;
			   tp7=0.00;
			   tp8=0.00;
			   tp9=0.00;
			   qtp1=0;
			   qtp2=0;
			   qtp3=0;
			   qtp4=0;
			   qtp5=0;
			   qtp6=0;
			   qtp7=0;
			   qtp8=0;
			   qtp9=0;
			   hqcode=0;
			   code=0;
			   String wname="";
			   String wpack="";
			   double gval=0.00;
			   int tqty=0;
			   double tval=0.00;
			   String hqname="";
			   boolean first=true;
			   ps2.setInt(1, year);
			   ps2.setInt(2, div);
			   ps2.setInt(3, depo);
			   ps2.setDate(4, sDate);
			   ps2.setDate(5, eDate);
			   ps2.setInt(6, div);
			   rs2= ps2.executeQuery();
			   Vector row=new Vector();
			   while (rs2.next())
			   {

				   
				   if (first)
				   {
					   code=rs2.getInt(1);
					   wname=rs2.getString(2);
					   wpack=rs2.getString(3);
					   hqcode=rs2.getInt(7);
					   first=false;

					   ps3.setInt(1, rs2.getInt(8));
					   ps3.setInt(2, div);
					   ps3.setInt(3, depo);
					   ps3.setInt(4, rs2.getInt(7));
					   rs3= ps3.executeQuery();
					   if (rs3.next())
					   {
						   hqname=rs3.getString(1);
					   }
					   
					   rs3.close();

				   
				   }

				   
				   if (code!=rs2.getInt(1))
				   {
					   inv = new SaletaxDto();
					   inv.setCode(code);
					   inv.setPname(wname);
					   inv.setPack(wpack);

					   inv.setStp1(qtp1);
					   inv.setStp2(qtp2);
					   inv.setStp3(qtp3);
					   inv.setStp4(qtp4);
					   inv.setStp5(qtp5);
					   inv.setStp6(qtp6);
					   inv.setStp7(qtp7);
					   inv.setStp8(qtp8);
					   inv.setStp9(qtp9);
					   inv.setTqty(tqty);
					   inv.setGrossamt(tval);
					   inv.setDash(0);
					   inv.setParty_name("H.Q.:"+hqname);
					   
					   data.add(inv);
					   tqty=0;
					   tval=0.00;
					   qtp1=0;
					   qtp2=0;
					   qtp3=0;
					   qtp4=0;
					   qtp5=0;
					   qtp6=0;
					   qtp7=0;
					   qtp8=0;
					   qtp9=0;

					   code=rs2.getInt(1);
					   wname=rs2.getString(2);
					   wpack=rs2.getString(3);


				   }

				   
				   
				   
				   if (hqcode!=rs2.getInt(7) && optn < 3 )
				   {

					   
					   hqcode=rs2.getInt(7);
					   ps3.setInt(1, rs2.getInt(8));
					   ps3.setInt(2, div);
					   ps3.setInt(3, depo);
					   ps3.setInt(4, rs2.getInt(7));
					   rs3= ps3.executeQuery();
					   if (rs3.next())
					   {
						   hqname=rs3.getString(1);
					   }
					   
					   rs3.close();
					   
					   
					   inv = new SaletaxDto();
					   inv.setCode(0);
					   inv.setParty_name("H.Q.:"+hqname);
					   inv.setPname("Total");
					   inv.setPack("");
					   inv.setStrn1(tp1);
					   inv.setStrn2(tp2);
					   inv.setStrn3(tp3);
					   inv.setStrn4(tp4);
					   inv.setStrn5(tp5);
					   inv.setStrn6(tp6);
					   inv.setStrn7(tp7);
					   inv.setStrn8(tp8);
					   inv.setStrn9(tp9);
					   inv.setGrossamt(gval);
					   inv.setDash(1);
					   data.add(inv);
					   
					   tp1=0.00;
					   tp2=0.00;
					   tp3=0.00;
					   tp4=0.00;
					   tp5=0.00;
					   tp6=0.00;
					   tp7=0.00;
					   tp8=0.00;
					   tp9=0.00;
					   gval=0.00;
					   

				   }
				   



				   switch (rs2.getInt(4))
				   {
				   case 1:
					   qtp1+=rs2.getInt(5);
					   tp1+=rs2.getInt(5)*rs2.getDouble(6);
					   break;
				   case 2: 
					   qtp2+=rs2.getInt(5);
					   tp2+=rs2.getInt(5)*rs2.getDouble(6);
					   break;
				   case 3: 
					   qtp3+=rs2.getInt(5);
					   tp3+=rs2.getInt(5)*rs2.getDouble(6);
					   break;
				   case 4: 
					   qtp4+=rs2.getInt(5);
					   tp4+=rs2.getInt(5)*rs2.getDouble(6);
					   break;
				   case 5: 
					   qtp5+=rs2.getInt(5);
					   tp5+=rs2.getInt(5)*rs2.getDouble(6);
					   break;
				   case 6: 
					   qtp6+=rs2.getInt(5);
					   tp6+=rs2.getInt(5)*rs2.getDouble(6);
					   break;
				   case 7: 
					   qtp7+=rs2.getInt(5);
					   tp7+=rs2.getInt(5)*rs2.getDouble(6);
					   break;
				   case 8: 
					   qtp8+=rs2.getInt(5);
					   tp8+=rs2.getInt(5)*rs2.getDouble(6);
					   break;
				   case 9: 
					   qtp9+=rs2.getInt(5);
					   tp9+=rs2.getInt(5)*rs2.getDouble(6);
					   break;
				   } // end of switch

				   tqty+=rs2.getInt(5);
				   tval+=rs2.getInt(5)*rs2.getDouble(6);
				   gval+=rs2.getInt(5)*rs2.getDouble(6);

			   }
			   rs2.close();

			   inv = new SaletaxDto();
			   inv.setCode(0);
			   inv.setParty_name("H.Q.:"+hqname);
			   inv.setPname("Total");
			   inv.setPack("");
			   inv.setStrn1(tp1);
			   inv.setStrn2(tp2);
			   inv.setStrn3(tp3);
			   inv.setStrn4(tp4);
			   inv.setStrn5(tp5);
			   inv.setStrn6(tp6);
			   inv.setStrn7(tp7);
			   inv.setStrn8(tp8);
			   inv.setStrn9(tp9);
			   inv.setGrossamt(gval);
			   inv.setDash(1);
			   data.add(inv);
			   ps2.close();
			   ps3.close();
		   }
		   catch(Exception ee){
			   System.out.println("error "+ee);
			   ee.printStackTrace();
		   }		   

		   finally {
			   try {
				   if(rs2!= null){rs2.close();}
				   if(ps2 != null){ps2.close();}
				   if(rs3!= null){rs3.close();}
				   if(ps3 != null){ps3.close();}
				   if(con != null){con.close();}
			   } catch (SQLException ee) {
				   System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
			   }
		   }

		   return data;
	   }

	 public List MisRepo28(int year,int depo,int div,Date sdate,Date edate,int optn)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement ps2 =null;
		   ResultSet rs2= null; 
		   SaletaxDto inv=null;
		   
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try
		   {
			   con=ConnectionFactory.getConnection();
			   String columName ="";

			   if(optn==1)
			   columName = " b.sinv_no,b.sinv_dt ";

			   if(optn==2)
			   columName = " b.sprd_cd,b.sinv_no,b.sinv_dt ";
/*				   
			   String sndQ =" select f.mac_name,f.mcity,s.sprd_cd,p.pname,p.pack ,concat(s.sinv_yr,s.sinv_lo,right(s.sinv_no,5)),s.sinv_dt,s.srate_exc," +
			   "  s.srate_hos,s.srate_pur,s.srate_net,s.sqty,s.sfree_qty,s.srate_mfg,s.sprt_cd  from invsnd as s, prd as p , partyfst as f " +
			   "  where s.fin_year=?  "+ 
			   " and s.div_code=?  and s.sdepo_code=? and s.sdoc_type in (39,40) and s.sinv_dt between ? and ? and ifnull(s.del_tag,'')<>'D' "+
			   " and p.div_code=? and p.pcode=s.sprd_cd " +
			   " and f.div_code=? and f.depo_code=? and f.mac_code=s.sprt_cd and s.srate_pur<>s.srate_mfg " +
			   " order by "+columName ;*/
			   

			   String sndQ =" select a.*,b.* from "+ 
			   " (select f.mac_code,f.mac_name,f.mcity,i.inv_no from partyfst f, invfst i "+
			   " where f.div_code=? and f.depo_code=? and i.fin_year=? and i.div_code=? and i.depo_code=? "+
			   " and i.doc_type in(39,40) and i.splrate='Y' and ifnull(i.del_tag,'')<>'D' and f.mac_code=i.party_code) a, "+
			   " (select s.sprd_cd,p.pname,p.pack ,concat(s.sinv_yr,s.sinv_lo,right(s.sinv_no,5)) inv,s.sinv_dt,s.srate_exc, "+ 
			   " s.srate_hos,s.srate_pur,s.srate_net,s.sqty,s.sfree_qty,s.srate_mfg,s.sprt_cd,s.sinv_no,s.sdoc_type  from invsnd as s, prd as p "+   
			   " where s.fin_year=?  and s.div_code=?  and s.sdepo_code=? and s.sdoc_type in (39,40) "+
			   " and s.sinv_dt between ? and ? and ifnull(s.del_tag,'')<>'D' "+
			   " and p.div_code=? and p.pcode=s.sprd_cd ) b "+ 
			   " where a.mac_code=b.sprt_cd and a.inv_no=b.sinv_no order by "+ columName ;
			   
			   
			   
			   ps2 = con.prepareStatement(sndQ);


			   data = new ArrayList();
			   ps2.setInt(1, div);
			   ps2.setInt(2, depo);
			   ps2.setInt(3, year);
			   ps2.setInt(4, div);
			   ps2.setInt(5, depo);
			   ps2.setInt(6, year);
			   ps2.setInt(7, div);
			   ps2.setInt(8, depo);
			   ps2.setDate(9, sDate);
			   ps2.setDate(10, eDate);
			   ps2.setInt(11, div);
			   rs2= ps2.executeQuery();
			   Vector row=new Vector();
			   boolean first=true;
			   String wcode="";
			   int pcode=0;
			   double tot1=0.00;
			   double tot2=0.00;
			   double tot3=0.00;
			   double gtot1=0.00;
			   double gtot2=0.00;
			   double gtot3=0.00;
			   int tqty=0;
			   int tfreeqty=0;
			   while (rs2.next())
			   {

				   if (first)
				   {
					   wcode=rs2.getString(1);
					   pcode=rs2.getInt(5);
					   first=false;
				   }

				   if (pcode!=rs2.getInt(5) && optn==2)
				   {
					   
					   inv = new SaletaxDto();
					   inv.setSqty(tqty);
					   inv.setSfree_qty(tfreeqty);
					   inv.setMfgval(tot1);
					   inv.setInstvalueied(tot2);
					   inv.setInstvalueed(tot3);
					   tqty=0;
					   tfreeqty=0;
					   tot1=0.00;
					   tot2=0.00;
					   tot3=0.00;
					   inv.setDash(1);
					   data.add(inv);
					   pcode=rs2.getInt(5);

				   }

				   if (!wcode.equals(rs2.getString(1)) && optn==1)
				   {
					   
					   inv = new SaletaxDto();
					   inv.setSqty(tqty);
					   inv.setSfree_qty(tfreeqty);
					   inv.setMfgval(tot1);
					   inv.setInstvalueied(tot2);
					   inv.setInstvalueed(tot3);
					   tqty=0;
					   tfreeqty=0;
					   tot1=0.00;
					   tot2=0.00;
					   tot3=0.00;
					   inv.setDash(2);
					   data.add(inv);
					   wcode=rs2.getString(1);

				   }

				   
				   
				   inv = new SaletaxDto();
				   inv.setCode(0);
				   inv.setParty_name(rs2.getString(2));
				   inv.setCity(rs2.getString(3));
				   inv.setCode(rs2.getInt(5));
				   inv.setPname(rs2.getString(6));
				   inv.setPack(rs2.getString(7));
				   inv.setInv_no(rs2.getString(8));
				   if (depo==50 && rs2.getInt(19)==40)
					   inv.setInv_no(rs2.getString(8).substring(0,3)+"R"+rs2.getString(8).substring(4,8));
				   if (depo==59 && rs2.getInt(19)==39)
					   inv.setInv_no(rs2.getString(8).substring(0,3)+"L"+rs2.getString(8).substring(4,8));
					   
				   
				   inv.setInv_date(rs2.getDate(9));
				   inv.setSrate_exc(rs2.getDouble(10));
				   inv.setSrate_hos(rs2.getDouble(11));
				   inv.setSrate_pur(rs2.getDouble(12));
				   inv.setSrate_net(rs2.getDouble(13));
				   inv.setSqty(rs2.getInt(14));
				   inv.setSfree_qty(rs2.getInt(15));
				   inv.setSrate_mfg(rs2.getDouble(16));
				   inv.setMfgval(rs2.getDouble(12)*rs2.getInt(14));
				   inv.setInstvalueied(rs2.getDouble(13)*rs2.getInt(14));
				   inv.setInstvalueed(rs2.getDouble(16)*rs2.getInt(14));
				   tqty+=rs2.getInt(14);
				   tfreeqty+=rs2.getInt(15);
				   tot1+=rs2.getInt(14)*rs2.getDouble(12);
				   tot2+=rs2.getInt(14)*rs2.getDouble(13);
				   tot3+=rs2.getInt(14)*rs2.getDouble(16);
				   
				   gtot1+=rs2.getInt(14)*rs2.getDouble(12);
				   gtot2+=rs2.getInt(14)*rs2.getDouble(13);
				   gtot3+=rs2.getInt(14)*rs2.getDouble(16);
				   inv.setDash(0);
				   data.add(inv);
			   }
			   
			   inv = new SaletaxDto();
			   inv.setDash(1);
			   if (optn==1)
			   {
			   inv.setSqty(tqty);
			   inv.setSfree_qty(tfreeqty);
			   inv.setDash(2);
			   }
			   inv.setMfgval(tot1);
			   inv.setInstvalueied(tot2);
			   inv.setInstvalueed(tot3);
			   data.add(inv);

			   
			   inv = new SaletaxDto();
			   inv.setDash(3);
			   inv.setMfgval(gtot1);
			   inv.setInstvalueied(gtot2);
			   inv.setInstvalueed(gtot3);
			   data.add(inv);

			   ps2.close();
			   }


		   
		   catch(Exception ee){
			   System.out.println("error "+ee);
			   ee.printStackTrace();
		   }		   

		   finally {
			   try {
				   if(rs2!= null){rs2.close();}
				   if(ps2 != null){ps2.close();}
				   if(con != null){con.close();}
			   } catch (SQLException ee) {
				   System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
			   }
		   }

		   return data;
	   }

	 public List vatTaxPrint(int year,int depo,int div,Date sdate,Date edate,int doc_tp)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   SaletaxDto inv=null;
		   String query=null;
		   Double gsale1,gsale2,gsale3;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try{
			    con=ConnectionFactory.getConnection();

			    query=" select s.sprt_cd,p.mac_name,p.mcity,p.mpst_no,s.sdoc_type,s.sale_type,s.sqty,s.sfree_qty," +
			    "s.srate_net,s.srate_mrp,s.stax_cd1,s.strn_tp,s.ndays from invsnd s,partyfst p "+ 
			    " where s.div_code=? and s.sdepo_code=? and s.sdoc_type in (40,41) and s.sinv_dt between ? and ? "+
			    " and ifnull(s.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and p.mac_code=s.sprt_cd "+
			    " order by p.mac_name,s.sprt_cd " ;
			    
/*			    query=" select s.party_code,p.mac_name,p.mcity,p.mpst_no,sum(s.ltax1_amt+s.ltax3_amt) tax5, sum(s.ltax2_amt+s.ltax9_amt) tax14, sum(s.ltax1_amt+s.ltax3_amt+s.ltax2_amt+s.ltax9_amt) total from invfst s,partyfst p "+ 
			    " where s.div_code=? and s.depo_code=? and s.doc_type=? and s.inv_Date between ? and ? "+
			    " and ifnull(s.del_tag,'')<>'D' and p.div_code=? and p.mac_code=s.party_code group by s.party_code "+
			    " order by p.mac_name " ;
*/
			    
			    
			      st = con.prepareStatement(query);
			      st.setInt(1, div);
			      st.setInt(2, depo);
			      st.setDate(3, sDate);
			      st.setDate(4, eDate);
			      st.setInt(5, div);
			      st.setInt(6, depo);
			      rs=st.executeQuery();
                data = new ArrayList();
                gsale1=0.00;
                gsale2=0.00;
                gsale3=0.00;
                double pts5=0.00;
                double mrp5=0.00;
                double pts14=0.00;
                double cnpts5=0.00;
                double cnmrp5=0.00;
                double cnpts14=0.00;
                double cntax5=0.00;
                double cntax14=0.00;
                boolean first=true;
                String wname="";
                String wcity="";
                String wtin="";
                String wcode="";
                int scheme=0;
                int qty=0;
                int free=0;
                int doc_type=0;
                int sale_type=0;
                int strn_tp=0;
                double net=0.00;
                double mrp=0.00;
                double tax=0.00;
                double mrp1=0.00;
                double tax5=0.00;
                double tax14=0.00;
                double gpts5=0.00;
                double gmrp5=0.00;
                double gtax5=0.00;
                double gcnpts5=0.00;
                double gpts14=0.00;
                double gtax14=0.00;
                double gcnpts14=0.00;
                
                while(rs.next())
			    {
			    	
			    	doc_tp=rs.getInt(5);
			    	sale_type=rs.getInt(6);
			    	qty=rs.getInt(7);
			    	free=rs.getInt(8);
			    	net=rs.getDouble(9);
			    	mrp=rs.getDouble(10);
			    	tax=rs.getDouble(11);
			    	strn_tp=rs.getInt(12);
			    	scheme=rs.getInt(13);

			    	if (first)
			    	{
			    		wcode=rs.getString(1);
				    	wname=rs.getString(2);
				    	wcity=rs.getString(3);
				    	wtin=rs.getString(4);
			    		first=false;
			    	}
			    	if (!wcode.equals(rs.getString(1)))
			    	{
				    	inv = new SaletaxDto();
				    	inv.setParty_name(wname);
				    	inv.setCity(wcity);
				    	inv.setTin(wtin);
				    	inv.setSale1(pts5);
				    	inv.setSale3(mrp5);
				    	inv.setLtax1_per(tax5);
				    	inv.setCtax1_amt(cnpts5);
				    	inv.setSale2(pts14);
				    	inv.setLtax2_per(tax14);
				    	inv.setCtax2_amt(cnpts14);
				    	inv.setDash(0);
				    	data.add(inv);
			    		wcode=rs.getString(1);
				    	wname=rs.getString(2);
				    	wcity=rs.getString(3);
				    	wtin=rs.getString(4);
				    	pts5=0.00;
				    	mrp5=0.00;
				    	tax5=0.00;
				    	cnpts5=0.00;
				    	pts14=0.00;
				    	tax14=0.00;
				    	cnpts14=0.00;
			    	}
			    	if (doc_tp==40 && (sale_type==1 || sale_type==3))
		    			{
			    		
			    		
		    				pts5+=roundTwoDecimals(qty*net);
//		    				mrp1=roundTwoDecimals(mrp-((mrp*tax)/(100+tax)));
							mrp1= (mrp- roundTwoDecimals((mrp*tax)/(tax+100)));
							
		    				mrp5+=roundTwoDecimals((qty+free)*mrp1);
		    				tax5+=roundTwoDecimals((((qty+free)*mrp1)*tax)/100);
		    				gpts5+=roundTwoDecimals(qty*net);
		    				gmrp5+=roundTwoDecimals((qty+free)*mrp1);
		    				gtax5+=roundTwoDecimals((((qty+free)*mrp1)*tax)/100);
		    			}

			    	if (doc_tp==41 && (sale_type==1 || sale_type==3) && strn_tp==1)
	    			{
	    				mrp1=mrp;
			    		if (scheme!=0)
			    		{
			    			mrp1=(mrp1*scheme)/(scheme+free);
			    			free=0;
			    		}
	    				mrp1=roundTwoDecimals(mrp1-((mrp1*tax)/(100+tax)));
	    				cnpts5+=roundTwoDecimals(qty*net);
	    				cnmrp5+=(qty+free)*mrp1;
	    				cntax5+=roundTwoDecimals((((qty+free)*mrp1)*tax)/100);
	    				gcnpts5+=roundTwoDecimals(qty*net);
	    			}
			    	

			    	if (doc_tp==40 && (sale_type==2 || sale_type==9))
	    			{
	    				pts14+=roundTwoDecimals(qty*net);
	    				tax14+=roundTwoDecimals(((qty*net)*tax)/100);
	    				gpts14+=roundTwoDecimals(qty*net);
	    				gtax14+=roundTwoDecimals(((qty*net)*tax)/100);
	    			}

			    	if (doc_tp==41 && (sale_type==2 || sale_type==9) && strn_tp==1)
	    			{
			    		mrp1=net;
			    		if (scheme!=0)
			    		{
			    			mrp1=roundTwoDecimals(mrp1*scheme)/(scheme+free);
			    			free=0;
			    		}
	    				cnpts14+=roundTwoDecimals((qty+free)*mrp1);  
	    				cntax14+=roundTwoDecimals((((qty+free)*mrp1)*tax)/100);
	    				gcnpts14+=roundTwoDecimals((qty+free)*mrp1);  
	    			}
			    	
			    }
		    	inv = new SaletaxDto();
		    	inv.setParty_name(wname);
		    	inv.setCity(wcity);
		    	inv.setTin(wtin);
		    	inv.setSale1(pts5);
		    	inv.setSale3(mrp5);
		    	inv.setLtax1_per(tax5);
		    	inv.setCtax1_amt(cnpts5);
		    	inv.setSale2(pts14);
		    	inv.setLtax2_per(tax14);
		    	inv.setCtax2_amt(cnpts14);
		    	inv.setDash(0);
		    	data.add(inv);

		    	inv = new SaletaxDto();
		    	inv.setTin("Grand Total");
		    	inv.setSale1(gpts5);
		    	inv.setSale3(gmrp5);
		    	inv.setLtax1_per(gtax5);
		    	inv.setCtax1_amt(gcnpts5);
		    	inv.setSale2(gpts14);
		    	inv.setLtax2_per(gtax14);
		    	inv.setCtax2_amt(gcnpts14);
		    	inv.setDash(3);
		    	
		    	data.add(inv);
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error "+ee);}		   
		  
			finally {
				try {
					    
		             if(rs != null){rs.close();}
		             if(st != null){st.close();}
		             if(con != null){con.close();}
				} catch (SQLException ee) {
					System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
				  }
			}
			
			return data;
	   }
	 

	 public List vatTaxPrint1(int year,int depo,int div,Date sdate,Date edate,int doc_tp)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   SaletaxDto inv=null;
		   String query=null;
		   Double gsale1,gsale2,gsale3;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try{
			    con=ConnectionFactory.getConnection();

			    query=" select s.party_code,p.mac_name,p.mcity,p.mpst_no,sum(s.ltax1_amt+s.ltax3_amt) tax5, sum(s.ltax2_amt+s.ltax9_amt) tax14, sum(s.ltax1_amt+s.ltax3_amt+s.ltax2_amt+s.ltax9_amt) total from invfst s,partyfst p "+ 
			    " where s.div_code=? and s.depo_code=? and s.doc_type=? and s.inv_Date between ? and ? "+
			    " and ifnull(s.del_tag,'')<>'D' and p.div_code=? and p.mac_code=s.party_code group by s.party_code "+
			    " order by p.mac_name " ;
			    

			    
			    
			      st = con.prepareStatement(query);
			      st.setInt(1, div);
			      st.setInt(2, depo);
			      st.setInt(3, doc_tp);
			      st.setDate(4, sDate);
			      st.setDate(5, eDate);
			      st.setInt(6, div);
			      rs=st.executeQuery();
              data = new ArrayList();
              gsale1=0.00;
              gsale2=0.00;
              gsale3=0.00;
			    while(rs.next())
			    {
			    	
			    	inv = new SaletaxDto();
			    	inv.setParty_name(rs.getString(2));
			    	inv.setCity(rs.getString(3));
			    	inv.setTin(rs.getString(4));
			    	inv.setSale1(rs.getDouble(5));
			    	inv.setSale2(rs.getDouble(6));
			    	inv.setSale3((rs.getDouble(5)+rs.getDouble(6)));
			    	gsale1+=rs.getDouble(5);
			    	gsale2+=rs.getDouble(6);
			    	inv.setDash(0);
			    	data.add(inv);
			    }

		    	inv = new SaletaxDto();
		    	inv.setTin("Grand Total");
		    	inv.setSale1(gsale1);
		    	inv.setSale2(gsale2);
		    	inv.setSale3(gsale1+gsale2);
		    	inv.setDash(3);

		    	

		    	
		    	
		    	
		    	data.add(inv);
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error "+ee);}		   
		  
			finally {
				try {
					    
		             if(rs != null){rs.close();}
		             if(st != null){st.close();}
		             if(con != null){con.close();}
				} catch (SQLException ee) {
					System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
				  }
			}
			
			return data;
	   }
	 
	 
	 



			 public List CNRebatePrint(int fyear,int depo,int div,Date sdate,Date edate,int doctp)
			   {

			List data =null;
			PreparedStatement ips = null;
			PreparedStatement ps2 = null;
		    SaletaxDto inv=null;
			Date invdt=null; 
			Date sinvdt=null; 
			ResultSet irs = null;
			ResultSet rs = null;
			Connection con=null;
			int i=0;
			double paise=0.00;
			double bamt=0.00;
			int amount=0;
			int invno=0;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());


			   Double gsale1,gsale2,gsale3,st5,st13,exem,dis5,dis13,cramt,dramt,net,disex,cst1,cst2,freight,st3,scamt,scamt1;
			   Double gtaxable1,gtaxable2,gtaxable3,gtaxable9,gtaxablef1,gtaxablef2,gtaxablef3,gtaxablef9,gross;
			   double gfreeval1,gfreeval2,gfreeval3,gfreeval9;
 
			   
			   try 
			{
				if (depo==61)
					invdt=sdf.parse("19/06/2013");
				else if (depo==51)
					invdt=sdf.parse("01/04/2013");
				else if (depo==31)
					invdt=sdf.parse("01/06/2013");

				else
					invdt=sdf.parse("26/06/2013");
			} 
			catch (ParseException e1) 
			{
				e1.printStackTrace();
			}

			
			
			try {

				con=ConnectionFactory.getConnection();

				con.setAutoCommit(false);
				
				
				String inv1="select i.inv_no,i.doc_type,i.disc_1,i.cn_val,i.dn_val,i.prod_type,i.oct_per1,i.oct_per2,i.oct_per3,i.oct_per4,i.freight,i.sc_per,i.sc_per1,i.bill_amt, "+
				" p.mac_name,p.mcity,concat(i.inv_yr,i.inv_lo,'C',i.inv_no),i.inv_date,p.mpst_no  from invfst i,partyfst p "+ 
				" where i.fin_year=? and i.div_code=? and i.depo_code=? and i.doc_type=? and i.inv_date between ? and ? "+  
				" and i.prt_type='1' and  ifnull(i.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and p.mac_code=i.party_code ";
				

				
				String query2 = "select s.sale_type,s.sprd_cd,s.sqty,if(s.ndays=0,s.sfree_qty,0) fqty,s.sbatch_no,s.sexp_dt, "+ 
				"s.srate_net,s.srate_trd,s.srate_mfg,s.srate_mrp,s.samnt,s.sprt_cd,s.stax_cd1,s.stax_type,s.tax_on,s.stax_cd2, "+
				"s.discyn,s.tax_on_free,s.ndays,s.sinv_dt,s.sinv_no,s.spinv_no,if(s.spinv_no=0,null,s.spinv_dt) spinvdt,p.pname,p.pack from invsnd s, prd p "+  
				"where s.fin_year=?  and s.div_code=?  and s.sdepo_code=? and s.sdoc_type=? and s.sinv_no=? and s.strn_tp=1 "+  
				"and  ifnull(s.del_tag,'')<>'D' and p.div_code=? and p.pcode=s.sprd_cd ";
				
				
				int qty=0;
				int free=0;
				int scheme=0;
				double wdisc = 0.00;
				double cnval = 0.00;
				double dnval = 0.00;
				double mrp,mfg,mamt,amt,totalDisc,taxable,taxAmt,disc1,disc2,disc3;
				 
				double lsale1,taxable1,ltax1_per,ltax1_amt;
				double lsale2,taxable2,ltax2_per,ltax2_amt;
				double lsale3,taxable3,ltax3_per,ltax3_amt;
				double lsale9,taxable9,ltax9_per,ltax9_amt;
				double mfgAmt,grossAmt,bill_amt,taxper;
				double taxfree1,taxfree2,taxfree3,taxfree9;
				double freeval,freeval1,freeval2,freeval3,freeval9;
				double taxablef1,taxablef2,taxablef3,taxablef9;
				double freetax1,freetax2,freetax3,freetax9;
				double sc_per=0.00;
				double sc_per1=0.00;
				double sc_amt=0.00;
				double sc_amt1=0.00;
				double xmrp=0.00;
				double mrp1=0.00;
				
				double taxablefree =0.00;
				double ftaxamt=0.00;
				double famt=0.00;
				double oct1=0.00;
				double oct2=0.00;
				double oct3=0.00;
				double oct4=0.00;
				double netamt=0.00;
				double net1=0.00;
                data = new ArrayList();
				
				String taxtyp=""; 
				
				ps2 = con.prepareStatement(query2);

				ips = con.prepareStatement(inv1);
				ips.setInt(1, fyear);
				ips.setInt(2, div);
				ips.setInt(3, depo);
				ips.setInt(4, doctp);
				ips.setDate(5, sDate);
				ips.setDate(6, eDate);
				ips.setInt(7, div);
				ips.setInt(8, depo);
				irs = ips.executeQuery();
				
				while(irs.next()) // invfst first file loop start 
				{

					
					ps2.setInt(1, fyear);
					ps2.setInt(2, div);
					ps2.setInt(3, depo);
					ps2.setInt(4, doctp);
					ps2.setInt(5, irs.getInt(1));
					ps2.setInt(6, div);
				
				rs = ps2.executeQuery();
				scheme=0;
				wdisc = irs.getDouble(3);
				cnval = irs.getDouble(4);
				dnval = irs.getDouble(5);
				
				net=0.00;
				mrp=0.00;
				mfg=0.00;
				mamt=0.00;
				amt=0.00;
				taxable=0.00;
				taxAmt=0.00;
				 
				qty=0;
				free=0;

				sc_per=0.00;
				sc_per1=0.00;
				sc_amt=0.00;
				sc_amt1=0.00;
				xmrp=0.00;
				taxtyp=""; 
				
				taxablef1=0.00;
				taxablef2=0.00;
				taxablef3=0.00;
				taxablef9=0.00;
				freetax1=0.00;
				freetax2=0.00;
				freetax3=0.00;
				freetax9=0.00;
				mfgAmt=0.00;
				totalDisc=0.00;
				grossAmt=0.00;
				bill_amt=0.00;
				taxper=0.00;
				disc1=0.00;
				disc2=0.00;
				disc3=0.00;
				freeval=0.00;
				freeval1=0.00;
				freeval2=0.00;
				freeval3=0.00;
				freeval9=0.00;
				taxablefree=0.00;
				oct1=0.00;
				oct2=0.00;
				oct3=0.00;
				oct4=0.00;
				
				ftaxamt=0.00;
				famt=0.00; 
				
				
				
				lsale1=lsale2=lsale3=lsale9=0.00;
				taxable1=taxable2=taxable3=taxable9=0.00;
				ltax1_amt=ltax2_amt=ltax3_amt=ltax9_amt=0.00;
				ltax1_per=ltax2_per=ltax3_per=ltax9_per=0.00;
				taxfree1=taxfree2=taxfree3=taxfree9=0.00;
				
				
				mrp1=0.00;
				
				net1=0.00;
                gsale1=0.00;
                gsale2=0.00;
                gsale3=0.00;
                st5=0.00;
                st13=0.00;
                exem=0.00;
                dis5=0.00;
                dis13=0.00;
                disex=0.00;
                cramt=0.00;
                dramt=0.00;
                cst1=0.00;
                cst2=0.00;
                freight=0.00;
                net=0.00;
                gtaxable1=0.00;
                gtaxable2=0.00;
                gtaxable3=0.00;
                gtaxable9=0.00;
                gfreeval1=0.00;
                gfreeval2=0.00;
                gfreeval3=0.00;
                gfreeval9=0.00;

                gtaxablef1=0.00;
                gtaxablef2=0.00;
                gtaxablef3=0.00;
                gtaxablef9=0.00;
                gross=0.00;
                st3=0.00;
                scamt=0.00;
                scamt1=0.00;
                
				
				while (rs.next())
				{
				

					taxablef1=0.00;
					taxablef2=0.00;
					taxablef3=0.00;
					taxablef9=0.00;
					lsale1=lsale2=lsale3=lsale9=0.00;
					taxable1=taxable2=taxable3=taxable9=0.00;
					ltax1_amt=ltax2_amt=ltax3_amt=ltax9_amt=0.00;
					ltax1_per=ltax2_per=ltax3_per=ltax9_per=0.00;
					taxfree1=taxfree2=taxfree3=taxfree9=0.00;
					freetax1=freetax2=freetax3=freetax9=0.00;
	                disex=0.00;
	                disc1=0.00;
	                disc2=0.00;
	                netamt=0.00;
	                sc_amt=0.00;
	                sc_amt1=0.00;
					
					sinvdt=rs.getDate(20);
					taxablefree=0.00;					
					taxtyp=rs.getString(14);
					taxper=rs.getDouble(13);
					qty = rs.getInt(3);
					free = rs.getInt(4);
					net = rs.getDouble(7);
					mfg = rs.getDouble(9);
					mrp= rs.getDouble(10);
					mamt = qty* mfg;
					amt = qty * net;
					taxable = qty * net;
					scheme=rs.getInt(19);
					mrp1=mrp;

					if (scheme!=0)
					{
						mrp1=(mrp1*scheme)/(scheme+free);
						free=0;
					}

					
					if(rs.getString(18).equals("Y") &&  sinvdt.after(invdt))
						mrp1=mrp;

					
					freeval = free*net;
					
					
					if(rs.getString(18).equals("Y"))// tax on free
					{
						if(scheme==0)
						  {
							taxablefree = free * net;
							famt= free*net;
						  }
					}
					
					if (rs.getString(15).equals("X"))  // excluding tax
					{
						xmrp = (mrp1- ((mrp1*taxper)/(taxper+100)));
						taxable = qty * xmrp;
						freeval= free*xmrp;
						if(rs.getString(18).equals("Y"))
							if(scheme==0)
							{
								taxablefree = free * xmrp;
							}

					} 
					
					if (rs.getString(15).equals("M"))  // including tax  
					{
						taxable = qty * mrp;
						freeval= free*mrp;
						if(rs.getString(18).equals("Y"))
							if(scheme==0)
							{
								taxablefree = free * mrp;
							}

					} 
					
					if (rs.getString(15).equals("S") && rs.getString(17).equals("Y")) 
					{
						taxable = amt - roundTwoDecimals((amt * wdisc) / 100);
						freeval = free*net;
						if(rs.getString(18).equals("Y"))
							if(scheme==0)
							{
								taxablefree = famt - roundTwoDecimals(((famt * wdisc) / 100));
							}
							
					}
					 

					 
					taxAmt = roundTwoDecimals((taxable * taxper) / 100);				
					ftaxamt = roundTwoDecimals((taxablefree*taxper)/100);

					 
					 switch (rs.getInt(1))
						{
						case 1:
							
							sc_per=rs.getDouble(16);
							//	sc_per=ifd.getSc_per();
							lsale1 += amt;
							ltax1_per = rs.getDouble(13);
							ltax1_amt += taxAmt;
							
							if(depo==59 || depo==55)
								
								sc_amt+=roundTwoDecimals((taxAmt*sc_per)/100);
							else
								sc_amt+=roundTwoDecimals(((taxable*sc_per)/100));
							
							
							if (rs.getDouble(13)==0.00)
							{
								taxfree1 += taxable;
								disc3+= roundTwoDecimals((amt * wdisc) / 100);
							}
							else
							{
								disc1+= roundTwoDecimals((amt * wdisc) / 100);
								taxable1 += taxable;
								freeval1 += freeval;
							}

							taxablef1 += roundTwoDecimals(taxablefree);
							freetax1 += ftaxamt;
							
							 

							break;

						case 2:

							sc_per1=rs.getDouble(16);
//							sc_per1=ifd.getSc_per1();
							lsale2 += amt;
							ltax2_per = rs.getDouble(13);
							ltax2_amt += taxAmt;
							if(depo==59 || depo==55 )
								sc_amt1+=roundTwoDecimals((taxAmt*sc_per1)/100);
							else
								sc_amt1+= roundTwoDecimals(((taxable*sc_per1)/100));

							
							if (rs.getDouble(13)==0.00)
							{
								taxfree2 += taxable;
								disc3+= roundTwoDecimals((amt * wdisc) / 100);
								
							}
							else
							{
								disc2+= roundTwoDecimals((amt * wdisc) / 100);
								taxable2 += taxable;
								freeval2 += freeval;
							}
							taxablef2 += roundTwoDecimals(taxablefree);
							freetax2 += ftaxamt;

							break;

						case 3:

							sc_per=rs.getDouble(16);
							lsale3 += amt;
							ltax3_per = rs.getDouble(13);
							ltax3_amt += taxAmt;
							if(depo==59 || depo==55 )
								sc_amt+=roundTwoDecimals((taxAmt*sc_per)/100);
							else
								sc_amt+= roundTwoDecimals(((taxable*sc_per)/100));
							
							

							if (rs.getDouble(13)==0.00)
							{
								disc3+= roundTwoDecimals((amt * wdisc) / 100);
								taxfree3 += taxable;
							}
							else
							{
								disc1+= roundTwoDecimals((amt * wdisc) / 100);
								taxable3 += taxable;
								freeval3 += freeval;
							}

							taxablef3 += roundTwoDecimals(taxablefree);
							freetax3 += ftaxamt;
							
							break;

						case 9:
							sc_per1=rs.getDouble(16);
							lsale9 += amt;
							ltax9_per = rs.getDouble(13);
							ltax9_amt += taxAmt;
							if(depo==59 || depo==55 )
								sc_amt1+=roundTwoDecimals((taxAmt*sc_per1)/100);
							else
								sc_amt1+= roundTwoDecimals(((taxable*sc_per1)/100));

							
							if (rs.getDouble(13)==0.00)
							{
								disc3+= roundTwoDecimals((amt * wdisc) / 100);
								taxfree9 += taxable;
							}
							else
							{
								disc2+= roundTwoDecimals((amt * wdisc) / 100);
								taxable9 += taxable;
								freeval9 += freeval;
				 			}

							taxablef9 += roundTwoDecimals(taxablefree);
							freetax9 += ftaxamt;
							
							 break;

						case 10:

							sc_per1=rs.getDouble(16);
//							sc_per1=ifd.getSc_per1();
							lsale2 += amt;
							ltax2_per = rs.getDouble(13);
							ltax2_amt += taxAmt;
							if(depo==59 || depo==55 )
								sc_amt1+=roundTwoDecimals((taxAmt*sc_per1)/100);
							else
								sc_amt1+= roundTwoDecimals(((taxable*sc_per1)/100));

							
							if (rs.getDouble(13)==0.00)
							{
								taxfree2 += taxable;
								disc3+= roundTwoDecimals((amt * wdisc) / 100);
								
							}
							else
							{
								disc2+= roundTwoDecimals((amt * wdisc) / 100);
								taxable2 += taxable;
								freeval2 += freeval;
							}
							taxablef2 += roundTwoDecimals(taxablefree);
							freetax2 += ftaxamt;

							break;

 
						}
					
					mfgAmt += mamt;
					grossAmt = grossAmt + taxAmt + amt + ftaxamt;
					totalDisc += roundTwoDecimals((amt * wdisc) / 100);
					 
					// bill_amt.setText(formatter.format(grossAmt-totalDisc));
					
					
					netamt=lsale1+lsale2+lsale3+lsale9+ltax1_amt+ltax2_amt+ltax3_amt+ltax9_amt-disc1-disc2;

					
			    	gsale1+=lsale1;
			    	gsale2+=lsale2+lsale9;
			    	gsale3+=lsale3;
//			    	st3+=rs.getDouble(38);
			    	exem+=(taxfree1+taxfree2+taxfree3+taxfree9);
			    	dis5+=disc1;
			    	dis13+=disc2;
			    	net1+=netamt;
			    	scamt+=sc_amt;
			    	scamt1+=sc_amt1;

			    	if (taxtyp.equalsIgnoreCase("Y"))
				    	cst1+=(ltax1_amt+ltax3_amt+ltax2_amt+ltax9_amt);
			    	else if (taxtyp.equalsIgnoreCase("N"))
				    	cst2+=(ltax1_amt+ltax3_amt+ltax2_amt+ltax9_amt);
			    	else
			    	{
				    	st5+=ltax1_amt+ltax3_amt+freetax1+freetax3;
				    	st13+=ltax2_amt+ltax9_amt+freetax2+freetax9;
			    	}

			    	
					inv = new SaletaxDto();
			    	inv.setInv_no(irs.getString(17));
			    	inv.setInv_date(irs.getDate(18));
			    	inv.setParty_name(irs.getString(15));
			    	inv.setCity(irs.getString(16));
			    	inv.setTin(irs.getString(19));
			    	inv.setPname(rs.getString(24));
			    	inv.setPack(rs.getString(25));
			    	inv.setSqty(rs.getInt(3));
			    	inv.setSfree_qty(rs.getInt(4));
			    	inv.setSrate_net(rs.getDouble(7));
			    	inv.setSale1(lsale1);
			    	inv.setSale2(lsale2+lsale9);
			    	inv.setSale3(lsale3);
			    	if (taxtyp.equalsIgnoreCase("Y"))
				    	inv.setCtax1_amt(ltax1_amt+ltax3_amt+ltax2_amt+ltax9_amt);
			    	else if (taxtyp.equalsIgnoreCase("N"))
				    	inv.setCtax2_amt(ltax1_amt+ltax3_amt+ltax2_amt+ltax9_amt);
			    	else
			    	{
				    	inv.setSt5((ltax1_amt+ltax3_amt+freetax1+freetax3));
				    	inv.setSt13((ltax2_amt+ltax9_amt+freetax2+freetax9));
			    	}
			    	inv.setExempted((taxfree1+taxfree2+taxfree3+taxfree9));
			    	inv.setDisc5(disc1);
			    	inv.setDisc10(disc2);
			    	inv.setSpinv_no(rs.getString(22));
			    	inv.setSpinv_date(rs.getDate(23));
			    	inv.setGrossamt(amt+inv.getSt5()+inv.getSt13());
			    	inv.setNetamt(netamt);
			    	inv.setTaxable1(taxable1);
			    	inv.setTaxable2(taxable2);
			    	inv.setTaxable3(taxable3);
			    	inv.setTaxable9(taxable9);
			    	inv.setTaxablef1(taxablef1);
			    	inv.setTaxablef2(taxablef2);
			    	inv.setTaxablef3(taxablef3);
			    	inv.setTaxablef9(taxablef9);
			    	inv.setDiscexempted(disc3);
			    	inv.setSc_amt(sc_amt);
			    	inv.setSc_amt1(sc_amt1);

			    	gtaxable1+=taxable1;
			    	gtaxable2+=taxable2;
			    	gtaxable3+=taxable3;
			    	gtaxable9+=taxable9;

			    	gtaxablef1+=taxablef1;
			    	gtaxablef2+=taxablef2;
			    	gtaxablef3+=taxablef3;
			    	gtaxablef9+=taxablef9;

			    	
			    	
			    	/*   	inv.setGrossamt(rs.getDouble(25));
			    	
			    	inv.setFreight(rs.getDouble(35));
			    	inv.setSt3(rs.getDouble(38));
			    	inv.setLtax1_per(rs.getDouble(39));
			    	inv.setLtax2_per(rs.getDouble(40));
			    	inv.setLtax3_per(rs.getDouble(41));
			    	inv.setLtax9_per(rs.getDouble(42));
			    	inv.setCases(rs.getInt(43));
			    	inv.setTax_free1(rs.getDouble(44));
			    	inv.setTax_free2(rs.getDouble(45));
			    	inv.setTax_free3(rs.getDouble(46));
			    	inv.setTax_free9(rs.getDouble(47));
			    	inv.setFreeval1(rs.getDouble(48));
			    	inv.setFreeval2(rs.getDouble(49));
			    	inv.setFreeval3(rs.getDouble(50));
			    	inv.setFreeval9(rs.getDouble(51));
					if (rs.getDouble(52)!=0)
						inv.setPaise(rs.getDouble(52));
					if (rs.getDouble(53)!=0)
						inv.setPaise(rs.getDouble(53));
			    	gsale1+=rs.getDouble(6);
			    	gsale2+=rs.getDouble(7);
			    	gsale3+=rs.getDouble(8);
			    	st5+=rs.getDouble(9);
			    	st13+=rs.getDouble(10);
			    	st3+=rs.getDouble(38);
			    	exem+=rs.getDouble(11);
			    	dis5+=rs.getDouble(12);
			    	dis13+=rs.getDouble(13);
			    	cramt+=rs.getDouble(14);
			    	dramt+=rs.getDouble(15);
			    	net+=rs.getDouble(16);
			    	gtaxable1+=rs.getDouble(17);
			    	gtaxable2+=rs.getDouble(18);
			    	gtaxable3+=rs.getDouble(19);
			    	gtaxable9+=rs.getDouble(20);
			    	gfreeval1+=rs.getDouble(48);
			    	gfreeval2+=rs.getDouble(49);
			    	gfreeval3+=rs.getDouble(50);
			    	gfreeval9+=rs.getDouble(51);

			    	gross+=rs.getDouble(25);
			    	disex+=rs.getDouble(26);
			    	cst1+=rs.getDouble(31);
			    	cst2+=rs.getDouble(32);
			    	freight+=rs.getDouble(35);
			    	
			    	scamt+=rs.getDouble(36);
			    	scamt1+=rs.getDouble(37);
*/
			    	inv.setDash(0);
			    	data.add(inv);

					
				} // end of second file(rs)
				rs.close();

				
		    	inv = new SaletaxDto();
		    	inv.setTin("Total");
		    	inv.setSale1(gsale1);
		    	inv.setSale2(gsale2);
		    	inv.setSale3(gsale3);
		    	inv.setSt5(st5);
		    	inv.setSt13(st13);
		    	inv.setSt3(st3);
		    	inv.setExempted(exem);
		    	inv.setDisc5(dis5);
		    	inv.setDisc10(dis13);
		    	inv.setCramt(cramt);
		    	inv.setDramt(dramt);
		    	inv.setNetamt(net1);
		    	inv.setTaxable1(gtaxable1);
		    	inv.setTaxable2(gtaxable2);
		    	inv.setTaxable3(gtaxable3);
		    	inv.setTaxable9(gtaxable9);
		    	inv.setTaxablef1(gtaxablef1);
		    	inv.setTaxablef2(gtaxablef2);
		    	inv.setTaxablef3(gtaxablef3);
		    	inv.setTaxablef9(gtaxablef9);
		    	inv.setDiscexempted(disex);
		    	inv.setCtax1_amt(cst1);
		    	inv.setCtax2_amt(cst2);
		    	inv.setFreight(freight);
		    	inv.setGrossamt(grossAmt);
		    	inv.setFreeval1(gfreeval1);
		    	inv.setFreeval2(gfreeval2);
		    	inv.setFreeval3(gfreeval3);
		    	inv.setFreeval9(gfreeval9);
		    	inv.setSc_amt(scamt);
		    	inv.setSc_amt1(scamt1);
		    	inv.setDash(3);

		    	data.add(inv);

					

				
				
				
				
				
				grossAmt = roundTwoDecimals(grossAmt);
				totalDisc = roundTwoDecimals(totalDisc);
				disc1 = roundTwoDecimals(disc1);
				disc2 = roundTwoDecimals(disc2);
				disc3 = roundTwoDecimals(disc3);
				dnval = roundTwoDecimals(dnval);
				cnval = roundTwoDecimals(cnval);
				
				freeval1 = roundTwoDecimals(freeval1);
				freeval2 = roundTwoDecimals(freeval2);
				freeval3 = roundTwoDecimals(freeval3);
				freeval9 = roundTwoDecimals(freeval9);
				sc_amt = roundTwoDecimals(sc_amt);
				sc_amt1 = roundTwoDecimals(sc_amt1);
				
				
				//totalDisc = disc1+disc2+disc3;
				//bill_amt = grossAmt+dnval-cnval-totalDisc; 

//				if (irs.getString(6).equalsIgnoreCase("Y"))
//					totalDisc+=(ltax1_amt+ltax2_amt+ltax3_amt+ltax9_amt); // cst reimbursement guwahati branch				
				
				grossAmt+= sc_amt+sc_amt1;

			////octroi calculation ////
				if (irs.getDouble(7)!=0)
					oct1=((taxable1+taxable3+ltax1_amt+ltax3_amt)*irs.getDouble(7))/100;
				if (irs.getDouble(8)!=0)
					oct2=((taxable2+taxable9+ltax2_amt+ltax9_amt)*irs.getDouble(8))/100;

				if (irs.getDouble(9)!=0)  // free medicine
					oct3=((freeval1+freeval3)*irs.getDouble(9))/100;
				if (irs.getDouble(10)!=0) // free food
					oct4=((freeval2+freeval9)*irs.getDouble(10))/100;

//				ifd.setOctroi(oct1+oct2);
//				ifd.setOct_free(oct3+oct4);

				grossAmt= grossAmt-(totalDisc+oct1+oct2+oct3+oct4)+irs.getDouble(11);
//				grossAmt= grossAmt-(totalDisc)+irs.getDouble(11);
				bamt=roundTwoDecimals(grossAmt);			
				
//				bill_amt = amount+dnval-cnval;
				
				amount=(int) (bamt+.50);  // round off gross amount
				paise=amount-roundTwoDecimals(bamt);

				bill_amt = amount; 
				
				
			} // end of irs (result set)
		 
				
				con.commit();
				con.setAutoCommit(true);
				ps2.close();
				irs.close();
				ips.close();
			} catch (SQLException ex) { ex.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in SaleTAxDAO.CNRebate " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);

					if(rs != null){rs.close();}
					if(irs != null){irs.close();}
					if(ips != null){ips.close();}
					if(ps2 != null){ps2.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLSaleTaxtDAO.Connection.close "+e);
				}
			}

			return data;
		}


			 public List writtenOffList(String smon,int year,int depo,int div,Date sdate,Date edate,int doctp)
			   {
				  
				   List data =null;
				   Connection con=null;
				   PreparedStatement st =null;
				   ResultSet rs= null; 

				   PreparedStatement ps4 =null;
				   ResultSet rs4= null; 

				   InvSndDto inv=null;
				   
				   String query=null;
				   Double gsale1;
				   
				   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
				   java.sql.Date eDate = new java.sql.Date(edate.getTime());
				   try{
					    con=ConnectionFactory.getConnection();
	
					    
					    query="select f.*,m.mac_name,m.mcity from " +
					   	" (select f.inv_no,f.inv_date,f.order_no,f.order_dt,b.*,f.mtr_no,f.mtr_dt,f.transport,f.party_code from invfst f,"+
					    "(select LEFT(p.pname,20),LEFT(p.pack,5),s.sqty,LEFT(s.sbatch_no,12),LEFT(s.sexp_dt,7),s.srate_net," +
					    "s.srate_mfg,s.srate_mrp,s.samnt,s.sinv_no,s.sinv_dt,s.fact_code from invsnd s,prd p "+
					    "where s.fin_year=? and s.div_code=? and s.sdepo_code=? and s.sdoc_type=? and s.sinv_dt between ? and ? "+
					    "and ifnull(s.del_tag,'')<>'D' and p.div_code=? and p.pcode=s.sprd_cd) b "+
					    "where f.fin_year=? and f.div_Code=? and f.depo_code=? and f.doc_type=? and f.inv_no=b.sinv_no and f.inv_date=b.sinv_dt "+
					    "and ifnull(f.del_tag,'')<>'D') f ,partyfst m where m.div_code=1 and m.depo_code=? and m.mac_code=f.party_code and ifnull(m.del_tag,'')<>'D' "; 

						String facQ =" select concat(fac_name,',',fac_city) from factmast where fac_code=?";

					      ps4 = con.prepareStatement(facQ);

						
					      st = con.prepareStatement(query);
					      st.setInt(1, year);
					      st.setInt(2, div);
					      st.setInt(3, depo);
					      st.setInt(4, doctp);
					      st.setDate(5, sDate);
					      st.setDate(6, eDate);
					      st.setInt(7, div);
					      st.setInt(8, year);
					      st.setInt(9, div);
					      st.setInt(10, depo);
					      st.setInt(11, doctp);
					      st.setInt(12, depo);
					      
					      rs=st.executeQuery();
		                data = new ArrayList();
		                gsale1=0.00;
		                String facname="";
					    while(rs.next())
					    {
					    	facname="";
							ps4.setInt(1, rs.getInt(16));
							rs4= ps4.executeQuery();
							if(rs4.next())
							{
								facname=rs4.getString(1);
							}
					    	rs4.close();
					    	inv = new InvSndDto();
					    	inv.setSinv_lo(rs.getString(1));
					    	inv.setSinv_dt(rs.getDate(2));
					    	inv.setSpinv_no(rs.getString(3));
					    	inv.setSpinv_dt(rs.getDate(4));
					    	inv.setPname(rs.getString(5));
					    	inv.setPack(rs.getString(6));
					    	inv.setSqty(rs.getInt(7));
					    	inv.setSbatch_no(rs.getString(8));
					    	inv.setSexp_dt(rs.getString(9));
					    	inv.setSrate_net(rs.getDouble(10));
					    	inv.setSrate_mfg(rs.getDouble(11));
					    	inv.setSrate_mrp(rs.getDouble(12));
					    	inv.setSamnt(rs.getDouble(13));
					    	inv.setBrname(facname);
					    	inv.setRate_type(rs.getString(17));  // lr no
					    	inv.setOrder_date(rs.getDate(18));  // lr date
					    	inv.setRemark(rs.getString(19)); // transport
					    	inv.setBr_tag(rs.getString(21)+','+rs.getString(22));
					    	inv.setDash(0);
					    	gsale1+=rs.getDouble(13);
					    	data.add(inv);
					    }

				    	inv = new InvSndDto();
				    	inv.setDash(1);
				    	inv.setSexp_dt("Grand Total");
				    	inv.setSamnt(gsale1);
				    	data.add(inv);
					    rs.close();
					    st.close();
					    ps4.close();
					   }
					catch(Exception ee){
						System.out.println("error "+ee);
						ee.printStackTrace();
					}		   
				  
					finally {
						try {
				             if(rs != null){rs.close();}
				             if(st != null){st.close();}
				             if(rs4 != null){rs4.close();}
				             if(ps4 != null){ps4.close();}

				             if(con != null){con.close();}
						} catch (SQLException ee) {
							System.out.println("-------------Exception in WriitenOffList.Connection.close "+ee);
						  }
					}
					
					return data;
			   }
			 
			 
			 
		 public double roundTwoDecimals(double d) {
			    DecimalFormat twoDForm = new DecimalFormat("#.##");
			    return Double.valueOf(twoDForm.format(d));
			}	 
		
		
		 public List DayWiseStock(int year,int depo,int div,Date sdate,Date edate,String smon)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement ps1 =null;
			   ResultSet rs1= null; 
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SaletaxDto inv=null;
			   String query=null;
			   String opng=null;
			   double gsale1,gsale2,gsale3,mfopng,tfopng,geopng,mfclos,tfclos,geclos;
               gsale1=0.00;
               gsale2=0.00;
               gsale3=0.00;
               mfopng=0.00;
               tfopng=0.00;
               geopng=0.00;

               mfclos=0.00;
               tfclos=0.00;
               geclos=0.00;

			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();

				    
				    

				    opng=" select  sum(mfopng),sum(tfopng),sum(genopng) from "+
				    " (select sum(opng"+smon+"*rate"+smon+") mfopng,0 tfopng,0 genopng from opening where fin_year=? and div_code=? and depo_code=? group by fin_year,div_code,depo_code "+
				    " union all "+ 
				    " select 0 mfopng,sum(opng"+smon+"*rate"+smon+") tfopng,0 genopng from opening where fin_year=? and div_code=? and depo_code=? group by fin_year,div_code,depo_code "+
				    " union all "+
				    " select 0 mfopng,0 tfopng,sum(opng"+smon+"*rate"+smon+") genopng from opening where fin_year=? and div_code=? and depo_code=? group by fin_year,div_code,depo_code) a ";



				query=" select b.sinv_dt,sum(b.mf*a.mfrate),sum(b.tf*a.tfrate),sum(b.gen*a.genrate) from "+ 
				" (select div_code,opcode,ifnull(ratenov,0.00) mfrate,0 tfrate,0 genrate from opening where fin_year=? and div_code=? and depo_code=? group by fin_year,div_code,depo_code,opcode "+
				" union all "+
				" select div_code,opcode,0 mfrate,ifnull(ratenov,0.00) tfrate,0 genrate from opening where fin_year=? and div_code=? and depo_code=? group by fin_year,div_code,depo_code,opcode "+
				" union all "+
				" select div_code,opcode,0 mfrate,0 tfrate,ifnull(ratenov,0.00) genrate from opening where fin_year=? and div_code=? and depo_code=? group by fin_year,div_code,depo_code,opcode) a, "+
				" (select div_code,sdepo_code,sinv_dt,sprd_cd,sum(mfg_val) MF,sum(tf) tf ,sum(gen) gen from "+
				" (select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,sum((sqty+sfree_qty)*-1) MFG_Val ,0 tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (39,40) and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+
				" union all "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,sum((sqty+sfree_qty)) MFG_Val ,0 tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =41 and strn_tp = 1 and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+
				" union all "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,sum((sqty+sfree_qty)) MFG_Val ,0 tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (60,61,62,72) and del_tag <> 'D'  "+
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+
				" union all "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,sum((sqty+sfree_qty)) MFG_Val ,0 tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =67 and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+
				" union all "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,sum((sqty+sfree_qty)) MFG_Val ,0 tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (65,66) and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+ 
				
				" union all "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,sum((sqty+sfree_qty)*-1) tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (39,40) and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+
				" union all "+ 
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,sum((sqty+sfree_qty))  tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =41 and strn_tp=1  and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+
				" union all "+ 
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,sum((sqty+sfree_qty)) tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (60,61,62,72) and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+
				" union all "+ 
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,sum((sqty+sfree_qty)) tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =67 and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+
				" union all "+ 
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,sum((sqty+sfree_qty)) tf,0 gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (65,66) and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+ 
				
				" union all  "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,0 tf,sum((sqty+sfree_qty)*-1) gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (39,40) and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+ 
				" union all "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,0  tf,sum((sqty+sfree_qty)) gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =41 and strn_tp=1  and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+
				" union all "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,0 tf,sum((sqty+sfree_qty)) gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (60,61,62.72) and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+ 
				" union all "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,0 tf,sum((sqty+sfree_qty)) gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =67 and del_tag <> 'D' "+ 
				 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd "+ 
				" union all "+
				" select div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd,0 MFG_Val ,0 tf,sum((sqty+sfree_qty)) gen "+ 
				" from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (65,66) and del_tag <> 'D' "+ 
				" group by div_code,sdepo_code,sdoc_type,sinv_dt,sprd_cd " +
				
				" ) a  "+
				" group by a.div_code,a.sdepo_code,a.sinv_dt,sprd_cd) b "+
				" where a.div_code=b.div_code and a.opcode=b.sprd_Cd group by b.sdepo_code,b.sinv_dt ";
				    
				    
/*				    query=" select div_code,sdepo_code,sinv_dt,round(sum(mfg_val),2) MF,round(sum(tf),2) tf ,round(sum(gen),2) gen from "+
				    " (select div_code,sdepo_code,sdoc_type,sinv_dt,round(sum(srate_mfg*(-sqty-sfree_qty)),2) MFG_Val ,0 tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (39,40) and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,round(sum(srate_mfg*(sqty+sfree_qty)),2) MFG_Val ,0 tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =41 and strn_tp = 1 and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,round(sum(srate_mfg*(sqty+sfree_qty)),2) MFG_Val ,0 tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (60,61,62,72) and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,round(sum(srate_mfg*(sqty+sfree_qty)),2) MFG_Val ,0 tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =67 and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,round(sum(srate_mfg*(sqty+sfree_qty)),2) MFG_Val ,0 tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (65,66) and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+

				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,round(sum(srate_mfg*(-sqty-sfree_qty)),2) tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (39,40) and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,round(sum(srate_mfg*(sqty+sfree_qty)),2)  tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (41) and strn_tp = 1 and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,round(sum(srate_mfg*(sqty+sfree_qty)),2) tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (60,61,62,72) and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,round(sum(srate_mfg*(sqty+sfree_qty)),2) tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =67 and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,round(sum(srate_mfg*(sqty+sfree_qty)),2) tf,0 gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (65,66) and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+

				    " union all "+ 
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,0 tf,round(sum(srate_mfg*(-sqty-sfree_qty)),2) gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (39,40) and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,0  tf,round(sum(srate_mfg*(sqty+sfree_qty)),2) gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =41 and strn_tp = 1  and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,0 tf,round(sum(srate_mfg*(sqty+sfree_qty)),2) gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (60,61,62,72) and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,0 tf,round(sum(srate_mfg*(sqty+sfree_qty)),2) gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type =67 and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+
				    " union all "+
				    " select div_code,sdepo_code,sdoc_type,sinv_dt,0 MFG_Val ,0 tf,round(sum(srate_mfg*(sqty+sfree_qty)),2) gen "+
				    " from invsnd where fin_year=? and div_code = ? and sdepo_code = ? and sinv_dt between ? and ? and sdoc_type in (65,66) and del_tag <> 'D' "+
				    " group by sdepo_code,sdoc_type,sinv_dt "+

				    " ) a " +
				    " group by a.sdepo_code,a.sinv_dt ";
*/				    
				    
				    
				    ps1 = con.prepareStatement(opng);
				      ps1.setInt(1, year);
				      ps1.setInt(2, 1);
				      ps1.setInt(3, depo);
				      ps1.setInt(4, year);
				      ps1.setInt(5, 2);
				      ps1.setInt(6, depo);
				      ps1.setInt(7, year);
				      ps1.setInt(8, 3);
				      ps1.setInt(9, depo);
				      rs1=ps1.executeQuery();
				      if (rs1.next())
				      {
				    	  mfopng=rs1.getDouble(1);
				    	  tfopng=rs1.getDouble(2);
				    	  geopng=rs1.getDouble(3);
				      }
				      rs1.close();
				      
				      
				      st = con.prepareStatement(query);
				      st.setInt(1, year);
				      st.setInt(2, 1);
				      st.setInt(3, depo);
				      st.setInt(4, year);
				      st.setInt(5, 2);
				      st.setInt(6, depo);
				      st.setInt(7, year);
				      st.setInt(8, 3);
				      st.setInt(9, depo);

				      st.setInt(10, year);
				      st.setInt(11, 1);
				      st.setInt(12, depo);
				      st.setDate(13, sDate);
				      st.setDate(14, eDate);
				      st.setInt(15, year);
				      st.setInt(16, 1);
				      st.setInt(17, depo);
				      st.setDate(18, sDate);
				      st.setDate(19, eDate);
				      st.setInt(20, year);
				      st.setInt(21, 1);
				      st.setInt(22, depo);
				      st.setDate(23, sDate);
				      st.setDate(24, eDate);
				      st.setInt(25, year);
				      st.setInt(26, 1);
				      st.setInt(27, depo);
				      st.setDate(28, sDate);
				      st.setDate(29, eDate);
				      st.setInt(30, year);
				      st.setInt(31, 1);
				      st.setInt(32, depo);
				      st.setDate(33, sDate);
				      st.setDate(34, eDate);
				      
				      st.setInt(35, year);
				      st.setInt(36, 2);
				      st.setInt(37, depo);
				      st.setDate(38, sDate);
				      st.setDate(39, eDate);
				      st.setInt(40, year);
				      st.setInt(41, 2);
				      st.setInt(42, depo);
				      st.setDate(43, sDate);
				      st.setDate(44, eDate);
				      st.setInt(45, year);
				      st.setInt(46, 2);
				      st.setInt(47, depo);
				      st.setDate(48, sDate);
				      st.setDate(49, eDate);
				      st.setInt(50, year);
				      st.setInt(51, 2);
				      st.setInt(52, depo);
				      st.setDate(53, sDate);
				      st.setDate(54, eDate);
				      st.setInt(55, year);
				      st.setInt(56, 2);
				      st.setInt(57, depo);
				      st.setDate(58, sDate);
				      st.setDate(59, eDate);
				      
				      st.setInt(60, year);
				      st.setInt(61, 3);
				      st.setInt(62, depo);
				      st.setDate(63, sDate);
				      st.setDate(64, eDate);
				      st.setInt(65, year);
				      st.setInt(66, 3);
				      st.setInt(67, depo);
				      st.setDate(68, sDate);
				      st.setDate(69, eDate);
				      st.setInt(70, year);
				      st.setInt(71, 3);
				      st.setInt(72, depo);
				      st.setDate(73, sDate);
				      st.setDate(74, eDate);
				      st.setInt(75, year);
				      st.setInt(76, 3);
				      st.setInt(77, depo);
				      st.setDate(78, sDate);
				      st.setDate(79, eDate);
				      st.setInt(80, year);
				      st.setInt(81, 3);
				      st.setInt(82, depo);
				      st.setDate(83, sDate);
				      st.setDate(84, eDate);

				      rs=st.executeQuery();
	                data = new ArrayList();
	                gsale1=0.00;
	                gsale2=0.00;
	                gsale3=0.00;
	                Date mf=null;
	                Date tf=null;
	                Date ge=null;
	                boolean first=true;
	                while(rs.next())
				    {
				    	inv = new SaletaxDto();
				    	mfclos=mfopng-rs.getDouble(2);
				    	tfclos=tfopng-rs.getDouble(3);
				    	geclos=geopng-rs.getDouble(4);
				    	inv.setInv_date(rs.getDate(1));
			    		inv.setSale1(mfclos);
			    		inv.setSale2(tfclos);
			    		inv.setSale3(geclos);
						mfopng=mfclos;
						tfopng=tfclos;
						geopng=geclos;
				    	inv.setDash(0);
				    	
				    	if(first)
				    	{
				    		first=false;
				    		gsale1=mfopng;
				    		gsale2=tfopng;
				    		gsale3=geopng;
				    		mf=rs.getDate(1);
				    		tf=rs.getDate(1);
				    		ge=rs.getDate(1);
				    	}
				    	if (mfopng>gsale1)
				    	{
				    		gsale1=mfopng;
				    		mf=rs.getDate(1);
				    	}
				    	if (tfopng>gsale2)
				    	{
				    		gsale2=tfopng;
				    		tf=rs.getDate(1);
				    	}
				    	if (geopng>gsale3)
				    	{
				    		gsale3=geopng;
				    		ge=rs.getDate(1);
				    	}
				    	
				    	data.add(inv);
				    }

			    	inv = new SaletaxDto();
			    	inv.setInv_date(mf);
			    	inv.setSale1(gsale1);
			    	inv.setSale2(0);
			    	inv.setSale3(0);
			    	inv.setDash(1);
			    	data.add(inv);
			    	inv = new SaletaxDto();
			    	inv.setInv_date(tf);
			    	inv.setSale1(0);
			    	inv.setSale2(gsale2);
			    	inv.setSale3(0);
			    	inv.setDash(1);
			    	data.add(inv);

			    	inv = new SaletaxDto();
			    	inv.setInv_date(ge);
			    	inv.setSale1(0);
			    	inv.setSale2(0);
			    	inv.setSale3(gsale3);
			    	inv.setDash(1);
			    	data.add(inv);
			    	
			    	rs.close();
				    st.close();
				    ps1.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
					ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(rs1 != null){rs1.close();}
			             if(ps1 != null){ps1.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }

		 
		 public List saleTaxPrintPartywise(int year,int depo,int div,Date sdate,Date edate,int doc_tp,ArrayList partyList,int optn)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SaletaxDto inv=null;
			   String query=null;
			   int wdepo=depo;
			   String orderby=" f.inv_no";
			   StringBuffer partyCode=null;
			   int size=0;
			   if (doc_tp==400)
			   {
				   orderby=" f.party_code,f.inv_no";
				   doc_tp=40;
			   }
			   Double gsale1,gsale2,gsale3,gsale10,st5,st13,st10,exem,exem10,dis5,dis13,dis10,cramt,dramt,net,disex,cst1,cst2,freight,st3,scamt,scamt1;
			   Double gtaxable1,gtaxable2,gtaxable3,gtaxable9,gtaxable10,gtaxablef1,gtaxablef2,gtaxablef3,gtaxablef9,gtaxablef10,gross;
			   double gfreeval1,gfreeval2,gfreeval3,gfreeval9,gfreeval10,gfreetax10;
			   
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();
				    
				    if(partyList!=null)
				    {
				    	partyCode=new StringBuffer(" f.party_code in (");
				    	size=partyList.size();
				    	int p=0;
				    	for (p=0;p<size-1;p++)
				    	{
				    		partyCode.append("'"+partyList.get(p)+"',");
				    	}
				    	partyCode.append("'"+partyList.get(p)+"') and ");
				    }
				    else
				    {
				    	partyCode=new StringBuffer(" ");	
				    }
				    
				    
				    
				    
				    if (depo==61 || depo == 82 || depo == 40  )
				    {
				    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+ //5
					"(f.lsale1-f.taxfree1),((f.lsale2-f.taxfree2)+(f.lsale9-f.taxfree9)),(f.lsale3-f.taxfree3),(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
					"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
					"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3, "+
					"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," +
					"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9, "+  
					"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
					" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code " +
					"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
					"and  f.inv_date between ? and ? and "+partyCode+" f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
					" and p.div_code=?  and p.depo_code=? order by f.inv_no";
				    	
				    }
				    else if (depo==20 || depo==25  )
				    {
					    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"(f.lsale1-f.taxfree1),(f.lsale2+f.lsale9-f.taxfree2-f.taxfree9),(f.lsale3-f.taxfree3),(f.ltax1_amt+f.freetax1),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
								"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
								"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +
								"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,(f.ltax3_amt+f.freetax3)," +
								"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9,   "+
								"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round, "+  
								" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code " +
								"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
								"and  f.inv_date between ? and ? and  "+partyCode+" f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=?  and p.depo_code=? order by f.inv_no";

				    }
				    else if (depo==1031)
				    {
				    	depo=31;
				    	
				    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
					"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
					"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
					"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +
					"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," +
					"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
					"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
					" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code " +
					"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
					"and  f.inv_date between ? and ? and  "+partyCode+" f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
					" and p.div_code=?  and p.depo_code=? order by f.party_code,f.inv_no";
				    }

				    
				    else if (depo==2031)
				    {
				    	depo=31;
				    	
				    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
					"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
					"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
					"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +
					"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," +
					"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
					"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
					" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code " +
					"from invfst as f,partyfst as p where f.depo_code=? and f.doc_type = ? " +
					"and  f.inv_date between ? and ? and  "+partyCode+" f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
					" and p.div_code=f.div_code  and p.depo_code=? order by f.div_code,f.inv_date,f.inv_no";
				    }

				    
				    else if (depo==1)
				    {
				    query="select concat('CWH',f.inv_lo,'/',RIGHT(f.inv_no,5)),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
					"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
					"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
					"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +
					"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," +
					"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
					"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
					" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code,f.div_code " +
					"from invfst as f,partyfst as p where (f.div_code < 4 or f.div_code=10) and f.depo_code=? and f.doc_type = ? " +
					"and  f.inv_date between ? and ? and  "+partyCode+"  f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
					" and p.div_code=f.div_code  and p.depo_code=? order by f.div_code,f.inv_no";
				    }

				    

				    else if (depo==58 || depo==59)
				    {
				    query="select concat(f.inv_yr,f.inv_lo,right(f.inv_no,5)),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
					"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
					"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
					"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +  // upto 26
					"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," + // upto 38
					"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
					"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
					" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code " +
					"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
					"and  f.inv_date between ? and ? and  "+partyCode+" f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
					" and p.div_code=?  and p.depo_code=? order by f.inv_no";
				    }

				    
				    else
				    {
				    query="select if(f.doc_type=40,concat(f.inv_yr,f.inv_lo,f.inv_no),concat(f.inv_yr,f.inv_lo,'C',right(f.inv_no,4))),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12), "+
					"f.lsale1,(f.lsale2+f.lsale9),f.lsale3,(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),(f.taxfree1+" +
					"f.taxfree2+f.taxfree3+f.taxfree9),f.discount_amt,f.discount_amt2,f.cn_val,f.dn_val,f.bill_amt," +
					"f.taxable1,f.taxable2,f.taxable3,f.taxable9,f.taxablef1,f.taxablef2,f.taxablef3,f.taxablef9,f.gross_amt,f.discount_amt3," +  // upto 26
					"f.ctax1_per,f.ctax2_per,f.ctax3_per,f.ctax9_per,f.ctax1_amt,f.ctax2_amt,f.ctax3_amt,f.ctax9_amt,f.freight,f.sc_amt,f.sc_amt1,f.ltax3_amt," + // upto 38
					"f.ltax1_per,f.ltax2_per,f.ltax3_per,f.ltax9_per,f.cases,f.taxfree1,f.taxfree2,f.taxfree3,f.taxfree9," +
					"f.freeval1,f.freeval2,f.freeval3,f.freeval9,f.discount_round,f.interest_round,  "+  
					" f.lsale10,f.ltax10_per,f.ltax10_amt,f.taxable10,f.discount_amt10,f.taxablef10,f.freetax10,f.freeval10,f.taxfree10,f.party_code " +
					"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type = ? " +
					"and  f.inv_date between ? and ? and  "+partyCode+" f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
					" and p.div_code=?  and p.depo_code=? order by "+orderby;
				    }
				      st = con.prepareStatement(query);
				      if (depo==1)
				      {
				    	  st.setInt(1, depo);
				    	  st.setInt(2, doc_tp);
				    	  st.setDate(3, sDate);
				    	  st.setDate(4, eDate);
				    	  st.setInt(5, depo);
				    	  
				      }

				      else if (wdepo==2031)
				      {
				    	  st.setInt(1, depo);
				    	  st.setInt(2, doc_tp);
				    	  st.setDate(3, sDate);
				    	  st.setDate(4, eDate);
				    	  st.setInt(5, depo);
				      }

				      else
				      {
				    	  st.setInt(1, div);
				    	  st.setInt(2, depo);
				    	  st.setInt(3, doc_tp);
				    	  st.setDate(4, sDate);
				    	  st.setDate(5, eDate);
				    	  st.setInt(6, div);
				    	  st.setInt(7, depo);
				      }
				      rs=st.executeQuery();
	                  data = new ArrayList();
	                  gsale1=0.00;
	                  gsale2=0.00;
	                  gsale3=0.00;
	                  gsale10=0.00;
	                  
	                  st5=0.00;
	                  st13=0.00;
	                  st10=0.00;
	                  exem=0.00;
	                  exem10=0.00;
	                  
	                  dis5=0.00;
	                  dis13=0.00;
	                  dis10=0.00;
	                  
	                  disex=0.00;
	                  cramt=0.00;
	                  dramt=0.00;
	                  cst1=0.00;
	                  cst2=0.00;
	                  freight=0.00;
	                  net=0.00;
	                  gtaxable1=0.00;
	                  gtaxable2=0.00;
	                  gtaxable3=0.00;
	                  gtaxable9=0.00;
	                  gtaxable10=0.00;
	                  gfreeval1=0.00;
	                  gfreeval2=0.00;
	                  gfreeval3=0.00;
	                  gfreeval9=0.00;
	                  gfreeval10=0.00;

	                  gtaxablef1=0.00;
	                  gtaxablef2=0.00;
	                  gtaxablef3=0.00;
	                  gtaxablef9=0.00;
	                  gtaxablef10=0.00;
	                  gfreetax10=0.00;
	                  gross=0.00;
	                  st3=0.00;
	                  scamt=0.00;
	                  scamt1=0.00;
				    while(rs.next())
				    {
				    	inv = new SaletaxDto();
				    	inv.setInv_no(rs.getString(1));
				    	inv.setInv_date(rs.getDate(2));
				    	inv.setParty_name(rs.getString(3));
				    	inv.setCity(rs.getString(4));
				    	inv.setTin(rs.getString(5));
				    	inv.setSale1(rs.getDouble(6));
				    	inv.setSale2(rs.getDouble(7));
				    	inv.setSale3(rs.getDouble(8));
				    	inv.setSt5(rs.getDouble(9));
				    	inv.setSt13(rs.getDouble(10));
				    	inv.setExempted(rs.getDouble(11));
				    	inv.setDisc5(rs.getDouble(12));
				    	inv.setDisc10(rs.getDouble(13));
				    	inv.setCramt(rs.getDouble(14));
				    	inv.setDramt(rs.getDouble(15));
				    	inv.setNetamt(rs.getDouble(16));
				    	inv.setTaxable1(rs.getDouble(17));
				    	inv.setTaxable2(rs.getDouble(18));
				    	inv.setTaxable3(rs.getDouble(19));
				    	inv.setTaxable9(rs.getDouble(20));
				    	inv.setTaxablef1(rs.getDouble(21));
				    	inv.setTaxablef2(rs.getDouble(22));
				    	inv.setTaxablef3(rs.getDouble(23));
				    	inv.setTaxablef9(rs.getDouble(24));
				    	inv.setGrossamt(rs.getDouble(25));
				    	inv.setDiscexempted(rs.getDouble(26));
				    	inv.setCtax1_amt(rs.getDouble(31));
				    	inv.setCtax2_amt(rs.getDouble(32));
				    	inv.setFreight(rs.getDouble(35));
				    	inv.setSc_amt(rs.getDouble(36));
				    	inv.setSc_amt1(rs.getDouble(37));
				    	inv.setSt3(rs.getDouble(38));
				    	inv.setLtax1_per(rs.getDouble(39));
				    	inv.setLtax2_per(rs.getDouble(40));
				    	inv.setLtax3_per(rs.getDouble(41));
				    	inv.setLtax9_per(rs.getDouble(42));
				    	inv.setCases(rs.getInt(43));
				    	inv.setTax_free1(rs.getDouble(44));
				    	inv.setTax_free2(rs.getDouble(45));
				    	inv.setTax_free3(rs.getDouble(46));
				    	inv.setTax_free9(rs.getDouble(47));
				    	inv.setFreeval1(rs.getDouble(48));
				    	inv.setFreeval2(rs.getDouble(49));
				    	inv.setFreeval3(rs.getDouble(50));
				    	inv.setFreeval9(rs.getDouble(51));
						if (rs.getDouble(52)!=0)
							inv.setPaise(rs.getDouble(52));
						if (rs.getDouble(53)!=0)
							inv.setPaise(rs.getDouble(53));
						
						
						inv.setSale10(rs.getDouble(54));
				    	inv.setLtax10_per(rs.getDouble(55));
				    	inv.setLtax10_amt(rs.getDouble(56));
				    	inv.setTaxable10(rs.getDouble(57));
				    	inv.setDiscount_amt10(rs.getDouble(58));
				    	inv.setTaxablef10(rs.getDouble(59));
				    	inv.setFreetax10_amt(rs.getDouble(60));
				    	inv.setFreeval10(rs.getDouble(61));
				    	inv.setTax_free10(rs.getDouble(62));
				    	inv.setParty_code(rs.getString(63));
						inv.setCode(0);   // div code
						if (depo==1)
							inv.setCode(rs.getInt(64));   // div code
				    	
				    	
				    	gsale1+=rs.getDouble(6);
				    	gsale2+=rs.getDouble(7);
				    	gsale3+=rs.getDouble(8);
				    	gsale10+=rs.getDouble(54);

				    	
				    	st5+=rs.getDouble(9);
				    	st13+=rs.getDouble(10);
				    	st10+=rs.getDouble(56);

				    	
				    	st3+=rs.getDouble(38);
				    	exem+=rs.getDouble(11);
				    	exem10+=rs.getDouble(62);

				    	dis5+=rs.getDouble(12);
				    	dis13+=rs.getDouble(13);
				    	dis10+=rs.getDouble(58);
				    	
				    	
				    	cramt+=rs.getDouble(14);
				    	dramt+=rs.getDouble(15);
				    	net+=rs.getDouble(16);
				    	gtaxable1+=rs.getDouble(17);
				    	gtaxable2+=rs.getDouble(18);
				    	gtaxable3+=rs.getDouble(19);
				    	gtaxable9+=rs.getDouble(20);
				    	gtaxable10+=rs.getDouble(57);

				    	
				    	gtaxablef1+=rs.getDouble(21);
				    	gtaxablef2+=rs.getDouble(22);
				    	gtaxablef3+=rs.getDouble(23);
				    	gtaxablef9+=rs.getDouble(24);
				    	gtaxablef10+=rs.getDouble(59);

				    	
				    	gfreeval1+=rs.getDouble(48);
				    	gfreeval2+=rs.getDouble(49);
				    	gfreeval3+=rs.getDouble(50);
				    	gfreeval9+=rs.getDouble(51);
				    	gfreeval10+=rs.getDouble(61);
				    	
				    	gfreetax10+=rs.getDouble(60);

				    	gross+=rs.getDouble(25);
				    	disex+=rs.getDouble(26);
				    	cst1+=rs.getDouble(31);
				    	cst2+=rs.getDouble(32);
				    	freight+=rs.getDouble(35);
				    	
				    	scamt+=rs.getDouble(36);
				    	scamt1+=rs.getDouble(37);
				    	inv.setDash(0);
				    	data.add(inv);
				    }

			    	inv = new SaletaxDto();
			    	inv.setTin("Grand Total");
			    	inv.setSale1(gsale1);
			    	inv.setSale2(gsale2);
			    	inv.setSale3(gsale3);
			    	inv.setSale10(gsale10);

			    	inv.setSt5(st5);
			    	inv.setSt13(st13);
			    	inv.setSt3(st3);
			    	inv.setLtax10_amt(st10);
			    	
			    	inv.setExempted(exem);
			    	inv.setTax_free10(exem10);
			    	
			    	inv.setDisc5(dis5);
			    	inv.setDisc10(dis13);
			    	inv.setDiscount_amt10(dis10);
			    	
			    	inv.setCramt(cramt);
			    	inv.setDramt(dramt);
			    	inv.setNetamt(net);
			    	inv.setTaxable1(gtaxable1);
			    	inv.setTaxable2(gtaxable2);
			    	inv.setTaxable3(gtaxable3);
			    	inv.setTaxable9(gtaxable9);
			    	inv.setTaxable10(gtaxable10);

			    	inv.setTaxablef1(gtaxablef1);
			    	inv.setTaxablef2(gtaxablef2);
			    	inv.setTaxablef3(gtaxablef3);
			    	inv.setTaxablef9(gtaxablef9);
			    	inv.setTaxablef10(gtaxablef10);
			    	inv.setFreetax10_amt(gfreetax10);

			    	inv.setDiscexempted(disex);
			    	inv.setCtax1_amt(cst1);
			    	inv.setCtax2_amt(cst2);
			    	inv.setFreight(freight);
			    	inv.setGrossamt(gross);
			    	inv.setFreeval1(gfreeval1);
			    	inv.setFreeval2(gfreeval2);
			    	inv.setFreeval3(gfreeval3);
			    	inv.setFreeval9(gfreeval9);
			    	inv.setFreeval10(gfreeval10);

			    	
			    	inv.setSc_amt(scamt);
			    	inv.setSc_amt1(scamt1);
			    	inv.setDash(3);

			    	

			    	
			    	
			    	
			    	data.add(inv);
				    rs.close();
				    st.close();
				   }
				catch(Exception ee){ ee.printStackTrace(); System.out.println("error "+ee);}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in SaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }


		 public List gstTaxPrint(int year,int depo,int div,Date sdate,Date edate,int doc_tp)
		   { 
			  
			   List data =null;
			   List exmdata =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SaletaxDto inv=null;
			   String query=null;
			   int wdepo=depo;
			   SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

			   if(BaseClass.loginDt.getPack_code()==2 && doc_tp==40)
				   doc_tp=68;

			   
			   String invlo="A";
				if (div==2)
					invlo="T";
				if (div==3)
					invlo="G";
				if (div==9)
					invlo="D";
				if (div==10)
					invlo="M";
				if (div==20)
					invlo="B";

				
				if (div==5)
					invlo="AP";
				if (div==6)
					invlo="TP";
				if (div==7)
					invlo="GP";
				if (div==8)
					invlo="DP";
				if (div==11)
					invlo="MP";
				if (div==21)
					invlo="BP";

			   String sdiv=" and div_code=? ";
			   String pdiv=" p.div_code=s.div_code ";

			   
//			   if(div==0)
//				    sdiv="";

			   
			   if(div==0 && BaseClass.loginDt.getPack_code()==1) // sales
				    sdiv=" and div_code in(1,2,3,10,20)";
			   if(div==0 && BaseClass.loginDt.getPack_code()==2)  // sample
				    sdiv=" and div_code in(5,6,7,11,21)";

			   if(div==0 && BaseClass.loginDt.getPack_code()==4) // cwh
			    sdiv="";

			   
			   
			   if((doc_tp==67 || doc_tp==0 || doc_tp==68) && BaseClass.loginDt.getPack_code()==2 )
				   pdiv=" p.div_code=1 ";

			   if((doc_tp==67 || doc_tp==0 || doc_tp==68) && depo==50 && div==7 && BaseClass.loginDt.getPack_code()==2 )
				   pdiv=" p.div_code=3 ";

			   
			   String doctp=" and sdoc_type="+doc_tp;
			   String fdoctp=" and doc_type="+doc_tp;
			   if(doc_tp==0)
				   doctp=" and sdoc_type in (60,61,62,72,73)";
			   
			   System.out.println(" DOCTP "+doctp+" pdiv "+pdiv+" SDIV "+sdiv);
			   Double gsale1,gsale2,gsale3,gsale10,st5,st13,st10,exem,exem10,dis5,dis13,dis10,cramt,dramt,net,disex,cst1,cst2,freight,st3,scamt,scamt1,st51,taxable51,scamt51,gsale9;
			   Double egsale1,egsale2,egsale3,egsale10,egsale9,egtaxable1,egfreetax1,egtaxablef1,egfreeval1;
			   
			   Double gtaxable1,gtaxable2,gtaxable3,gtaxable9,gtaxable10,gtaxablef1,gtaxablef2,gtaxablef3,gtaxablef9,gtaxablef10,gross,ltax1per,ltax2per,ltax3per,ltax9per;
			   double gfreeval1,gfreeval2,gfreeval3,gfreeval9,gfreeval10;
			   double gfreetax1,gfreetax2,gfreetax3,gfreetax9,gfreetax10,gdiff;
			   double gtaxable33,gsgst3,gcgst3,gigst3;
			   double gtaxable6,gsgst6,gcgst6,gigst6;
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   
			   
			   try{
				    con=ConnectionFactory.getConnection();


		   if(doc_tp==68 && BaseClass.loginDt.getPack_code()==2)
		   {
			   query=" select s.invno,s.sinv_dt,p.mac_name,p.mstate_name,p.gststate_code,p.gst_no,sum(s.sale1) sale1,sum(s.sale2) sale2,sum(s.sale3) sale3,sum(s.sale9) sale9,sum(s.sale10) sale10, "+
		    			" sum(s.taxable0) taxable0,sum(s.taxable5) taxable5,sum(s.taxable12) taxable12,sum(s.taxable18) taxable18,round(sum(s.taxable28),2) taxable28,"+
		    			" sum(s.sgst0) sgst0,sum(s.sgst5) sgst5,sum(s.sgst12) sgst12,sum(s.sgst18) sgst18,sum(s.sgst28) sgst28, "+
		    			" sum(s.cgst0) cgst0,sum(s.cgst5) cgst5,sum(s.cgst12) cgst12,sum(s.cgst18) cgst18,sum(s.cgst28) cgst28, "+
		    			" sum(s.igst0) igst0,sum(s.igst5) igst5,sum(s.igst12) igst12,sum(s.igst18) igst18,sum(s.igst28) igst28,round(sum(s.net),2) net,s.typ_cd,ifnull(s.stax_type,''),s.spinv_no,s.spinv_dt,s.div_code,s.sinv_no,s.strn_tp,s.diff,sum(s.taxable3) taxable3,sum(s.sgst3) sgst3 ,sum(s.cgst3) cgst3,sum(s.igst3) igst3,sum(s.taxable6) taxable6,sum(s.sgst6) sgst6 ,sum(s.cgst6) cgst6,sum(s.igst6) igst6 from "+
		    			" (select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,sum(taxable_amt) sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6 "+
		    			" from invsnd  where fin_year=? "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and sale_type=1 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,sum(taxable_amt) sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6  "+
		    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and sale_type=2 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,sum(taxable_amt) sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6    "+
		    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and sale_type=3 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,sum(taxable_amt) sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6   "+
		    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and sale_type=9 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,sum(taxable_amt) sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6   "+
		    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and sale_type=10 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,sum(taxable_amt) taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,sum(sgst_amt) sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" sum(cgst_amt) cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,sum(igst_amt) igst0,0 igst5,0 igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6   "+
		    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and STAX_TYPE='E' and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,sum(taxable_amt) taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,sum(sgst_amt) sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,sum(cgst_amt) cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,sum(igst_amt) igst5,0 igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6    "+
		    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and STAX_TYPE='A' and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,sum(taxable_amt) taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,sum(sgst_amt) sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,sum(cgst_amt) cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,sum(igst_amt) igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6   "+
		    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and STAX_TYPE='D' and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,sum(taxable_amt) taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,sum(sgst_amt) sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,sum(cgst_amt) cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,sum(igst_amt) igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff ,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6  "+
		    			" from invsnd  where fin_year=? "+sdiv+"  and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and STAX_TYPE='B' and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,sum(taxable_amt)  taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,sum(sgst_amt) sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,sum(cgst_amt) cgst28,0 igst0,0 igst5,0 igst12,0 igst18,sum(igst_amt) igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6   "+
		    			" from invsnd  where fin_year=? "+sdiv+"  and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and STAX_TYPE='F' and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(inv_yr,inv_lo,right(inv_no,5)) invno,inv_date,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,sum(bill_amt) net,party_code sprt_cd,inv_no,0 typ_cd,'E' stax_type,div_code,'' spinv_no,null spinv_dt,0 strn_tp,0 diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3 , 0 taxable6,0 sgst6 ,0 cgst6,0 igst6   "+
		    			" from invfst  where fin_year=? "+sdiv+"  and depo_code=? "+fdoctp+" and inv_date between ? and ? "+
		    			" and prod_type='N' and ifnull(del_tag,'')<>'D'  group by div_code,inv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,sum(taxable_amt) taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,sum(sgst_amt) sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,sum(cgst_amt) cgst28,0 igst0,0 igst5,0 igst12,0 igst18,sum(igst_amt) igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6  "+
		    			" from invsnd  where fin_year=? "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and STAX_TYPE='C' and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff,sum(taxable_amt) taxable3,sum(sgst_amt)sgst3 ,sum(cgst_amt)cgst3,sum(igst_amt)igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6 "+
		    			" from invsnd  where fin_year=? "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and STAX_TYPE='G' and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
		    			" union all "+
		    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
		    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3 "+
		    			",sum(taxable_amt) taxable6,sum(sgst_amt)sgst6 ,sum(cgst_amt)cgst6,sum(igst_amt)igst6 "+
		    			" from invsnd  where fin_year=? "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
		    			" and STAX_TYPE='H' and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no) s,PARTYFST p "+

		    			" where  "+pdiv+" and p.depo_code=? and p.mac_code=s.sprt_cd and ifnull(p.del_tag,'')<>'D' group by s.div_code,s.sinv_no order by s.div_code,s.sinv_no ";
			   
						   
			   }

		   else
		   {
						   // change on 14/12/2017 due to change of tax% 28 to 18 from 16/11/2017
			   query=" select s.invno,s.sinv_dt,p.mac_name,p.mstate_name,p.gststate_code,ifnull(s.new1,p.gst_no),sum(s.sale1) sale1,sum(s.sale2) sale2,sum(s.sale3) sale3,sum(s.sale9) sale9,sum(s.sale10) sale10, "+
    			" sum(s.taxable0) taxable0,sum(s.taxable5) taxable5,sum(s.taxable12) taxable12,sum(s.taxable18) taxable18,round(sum(s.taxable28),2) taxable28,"+
    			" sum(s.sgst0) sgst0,sum(s.sgst5) sgst5,sum(s.sgst12) sgst12,sum(s.sgst18) sgst18,sum(s.sgst28) sgst28, "+
    			" sum(s.cgst0) cgst0,sum(s.cgst5) cgst5,sum(s.cgst12) cgst12,sum(s.cgst18) cgst18,sum(s.cgst28) cgst28, "+
    			" sum(s.igst0) igst0,sum(s.igst5) igst5,sum(s.igst12) igst12,sum(s.igst18) igst18,sum(s.igst28) igst28,round(sum(s.net),2) net,s.typ_cd,ifnull(s.stax_type,''),s.spinv_no,s.spinv_dt,s.div_code,s.sinv_no,s.strn_tp,s.diff,sum(s.taxable3) taxable3,sum(s.sgst3) sgst3 ,sum(s.cgst3) cgst3,sum(s.igst3) igst3,sum(s.taxable6) taxable6,sum(s.sgst6) sgst6 ,sum(s.cgst6) cgst6,sum(s.igst6) igst6 ,substring(p.gst_no,3,10) pan_no from "+
    			" (select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,sum(taxable_amt) sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6,0 cgst6,0 igst6,new1 "+
    			" from invsnd  where fin_year=? "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and sale_type=1 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,sum(taxable_amt) sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff ,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6,0 cgst6,0 igst6,new1  "+
    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and sale_type=2 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,sum(taxable_amt) sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6,0 cgst6,0 igst6 ,new1    "+
    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and sale_type=3 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,sum(taxable_amt) sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6,new1   "+
    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and sale_type=9 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,sum(taxable_amt) sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,0 net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6,0 cgst6,0 igst6,new1    "+
    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and sale_type=10 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,sum(taxable_amt) taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,sum(sgst_amt) sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" sum(cgst_amt) cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,sum(igst_amt) igst0,0 igst5,0 igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6,new1   "+
    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and (STAX_CD1+STAX_CD2+STAX_CD3)=0 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,sum(taxable_amt) taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,sum(sgst_amt) sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,sum(cgst_amt) cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,sum(igst_amt) igst5,0 igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6,new1   "+
    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and (STAX_CD1+STAX_CD2+STAX_CD3)=5 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,sum(taxable_amt) taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,sum(sgst_amt) sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,sum(cgst_amt) cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,sum(igst_amt) igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6,new1  "+
    			" from invsnd  where fin_year=?  "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and (STAX_CD1+STAX_CD2+STAX_CD3)=12 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,sum(taxable_amt) taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,sum(sgst_amt) sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,sum(cgst_amt) cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,sum(igst_amt) igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3, 0 taxable6,0 sgst6,0 cgst6,0 igst6,new1   "+
    			" from invsnd  where fin_year=? "+sdiv+"  and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and (STAX_CD1+STAX_CD2+STAX_CD3)=18 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,sum(taxable_amt)  taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,sum(sgst_amt) sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,sum(cgst_amt) cgst28,0 igst0,0 igst5,0 igst12,0 igst18,sum(igst_amt) igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6,new1   "+
    			" from invsnd  where fin_year=? "+sdiv+"  and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and (STAX_CD1+STAX_CD2+STAX_CD3)=28 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(inv_yr,inv_lo,right(inv_no,5)) invno,inv_date,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,sum(bill_amt) net,party_code sprt_cd,inv_no,0 typ_cd,'E' stax_type,div_code,'' spinv_no,null spinv_dt,0 strn_tp,0 diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6,new1    "+
    			" from invfst  where fin_year=? "+sdiv+"  and depo_code=? "+fdoctp+" and inv_date between ? and ? "+
    			" and prod_type='N' and ifnull(del_tag,'')<>'D'  group by div_code,inv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,sum(taxable_amt) taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,sum(sgst_amt) sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,sum(cgst_amt) cgst28,0 igst0,0 igst5,0 igst12,0 igst18,sum(igst_amt) igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6,new1  "+
    			" from invsnd  where fin_year=? "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and (STAX_CD1+STAX_CD2+STAX_CD3)=99 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no  "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff,sum(taxable_amt) taxable3,sum(sgst_amt)sgst3 ,sum(cgst_amt)cgst3,sum(igst_amt)igst3,0 taxable6,0 sgst6 ,0 cgst6,0 igst6,new1  "+
    			" from invsnd  where fin_year=? "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and (STAX_CD1+STAX_CD2+STAX_CD3)=3 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no "+
    			" union all "+
    			" select concat(sinv_yr,sinv_lo,right(sinv_no,5)) invno,sinv_dt,0 sale1,0 sale2,0 sale3,0 sale9,0 sale10,0 taxable0,0 taxable5,0 taxable12,0 taxable18,0 taxable28,0 sgst0,0 sgst5,0 sgst12,0 sgst18,0 sgst28, "+
    			" 0 cgst0,0 cgst5,0 cgst12,0 cgst18,0 cgst28,0 igst0,0 igst5,0 igst12,0 igst18,0 igst28,sum(net_amt) net,sprt_cd,sinv_no,typ_cd,stax_type,div_code,ifnull(spinv_no,'') spinv_no,spinv_dt,ifnull(strn_tp,0) strn_tp ,ifnull(ftaxable,0) diff,0 taxable3,0 sgst3 ,0 cgst3,0 igst3 "+
    			",sum(taxable_amt) taxable6,sum(sgst_amt)sgst6 ,sum(cgst_amt)cgst6,sum(igst_amt)igst6,new1  "+
    			" from invsnd  where fin_year=? "+sdiv+" and sdepo_code=? "+doctp+" and sinv_dt between ? and ? "+
    			" and (STAX_CD1+STAX_CD2+STAX_CD3)=6 and ifnull(del_tag,'')<>'D'  group by div_code,sinv_no) s,partyfst p "+

    			" where "+pdiv+" and p.depo_code=? and p.mac_code=s.sprt_cd and ifnull(p.del_tag,'')<>'D' group by s.div_code,s.sinv_no order by s.div_code,s.sinv_no ";

		   }
				    st = con.prepareStatement(query);
				    
				    System.out.println("doctp "+doc_tp+"div "+div);
				      if(div==0)
				      {
				    	  st.setInt(1, year);
				    	  st.setInt(2, depo);
				    	  st.setDate(3, sDate);
				    	  st.setDate(4, eDate);
				    	  st.setInt(5, year);
				    	  st.setInt(6, depo);
				    	  st.setDate(7, sDate);
				    	  st.setDate(8, eDate);
				    	  st.setInt(9, year);
				    	  st.setInt(10, depo);
				    	  st.setDate(11, sDate);
				    	  st.setDate(12, eDate);
				    	  st.setInt(13, year);
				    	  st.setInt(14, depo);
				    	  st.setDate(15, sDate);
				    	  st.setDate(16, eDate);
				    	  st.setInt(17, year);
				    	  st.setInt(18, depo);
				    	  st.setDate(19, sDate);
				    	  st.setDate(20, eDate);
				    	  st.setInt(21, year);
				    	  st.setInt(22, depo);
				    	  st.setDate(23, sDate);
				    	  st.setDate(24, eDate);
				    	  st.setInt(25, year);
				    	  st.setInt(26, depo);
				    	  st.setDate(27, sDate);
				    	  st.setDate(28, eDate);
				    	  st.setInt(29, year);
				    	  st.setInt(30, depo);
				    	  st.setDate(31, sDate);
				    	  st.setDate(32, eDate);
				    	  st.setInt(33, year);
				    	  st.setInt(34, depo);
				    	  st.setDate(35, sDate);
				    	  st.setDate(36, eDate);
				    	  st.setInt(37, year);
				    	  st.setInt(38, depo);
				    	  st.setDate(39, sDate);
				    	  st.setDate(40, eDate);
				    	  st.setInt(41, year);
				    	  st.setInt(42, depo);
				    	  st.setDate(43, sDate);
				    	  st.setDate(44, eDate);
				    	  st.setInt(45, year);
				    	  st.setInt(46, depo);
				    	  st.setDate(47, sDate);
				    	  st.setDate(48, eDate);
				    	  st.setInt(49, year);
				    	  st.setInt(50, depo);
				    	  st.setDate(51, sDate);
				    	  st.setDate(52, eDate);
				    	  st.setInt(53, year);
				    	  st.setInt(54, depo);
				    	  st.setDate(55, sDate);
				    	  st.setDate(56, eDate);
				    	  st.setInt(57, depo);
				    	  
				      }
				      else
				      {
				    	  st.setInt(1, year);
				    	  st.setInt(2, div);
				    	  st.setInt(3, depo);
				    	  st.setDate(4, sDate);
				    	  st.setDate(5, eDate);
				    	  st.setInt(6, year);
				    	  st.setInt(7, div);
				    	  st.setInt(8, depo);
				    	  st.setDate(9, sDate);
				    	  st.setDate(10, eDate);
				    	  st.setInt(11, year);
				    	  st.setInt(12, div);
				    	  st.setInt(13, depo);
				    	  st.setDate(14, sDate);
				    	  st.setDate(15, eDate);
				    	  st.setInt(16, year);
				    	  st.setInt(17, div);
				    	  st.setInt(18, depo);
				    	  st.setDate(19, sDate);
				    	  st.setDate(20, eDate);
				    	  st.setInt(21, year);
				    	  st.setInt(22, div);
				    	  st.setInt(23, depo);
				    	  st.setDate(24, sDate);
				    	  st.setDate(25, eDate);
				    	  st.setInt(26, year);
				    	  st.setInt(27, div);
				    	  st.setInt(28, depo);
				    	  st.setDate(29, sDate);
				    	  st.setDate(30, eDate);
				    	  st.setInt(31, year);
				    	  st.setInt(32, div);
				    	  st.setInt(33, depo);
				    	  st.setDate(34, sDate);
				    	  st.setDate(35, eDate);
				    	  st.setInt(36, year);
				    	  st.setInt(37, div);
				    	  st.setInt(38, depo);
				    	  st.setDate(39, sDate);
				    	  st.setDate(40, eDate);
				    	  st.setInt(41, year);
				    	  st.setInt(42, div);
				    	  st.setInt(43, depo);
				    	  st.setDate(44, sDate);
				    	  st.setDate(45, eDate);
				    	  st.setInt(46, year);
				    	  st.setInt(47, div);
				    	  st.setInt(48, depo);
				    	  st.setDate(49, sDate);
				    	  st.setDate(50, eDate);
				    	  st.setInt(51, year);
				    	  st.setInt(52, div);
				    	  st.setInt(53, depo);
				    	  st.setDate(54, sDate);
				    	  st.setDate(55, eDate);
				    	  st.setInt(56, year);
				    	  st.setInt(57, div);
				    	  st.setInt(58, depo);
				    	  st.setDate(59, sDate);
				    	  st.setDate(60, eDate);
				    	  st.setInt(61, year);
				    	  st.setInt(62, div);
				    	  st.setInt(63, depo);
				    	  st.setDate(64, sDate);
				    	  st.setDate(65, eDate);
				    	  st.setInt(66, year);
				    	  st.setInt(67, div);
				    	  st.setInt(68, depo);
				    	  st.setDate(69, sDate);
				    	  st.setDate(70, eDate);
				    	  st.setInt(71, depo);
				      }
				      rs=st.executeQuery();
	                  data = new ArrayList();
	                  exmdata = new ArrayList();
	                  
	                  gtaxable6=0.00;
	                  gsgst6=0.00;
	                  gcgst6=0.00;
	                  gigst6=0.00;

	                  gtaxable33=0.00;
	                  gsgst3=0.00;
	                  gcgst3=0.00;
	                  gigst3=0.00;
	                  gsale1=0.00;
	                  gsale2=0.00;
	                  gsale3=0.00;
	                  gsale9=0.00;
	                  gsale10=0.00;

	                  // exempted seprate sheet
	                  egsale1=0.00;
	                  egsale2=0.00;
	                  egsale3=0.00;
	                  egsale9=0.00;
	                  egsale10=0.00;
	                  egtaxable1=0.00;
	                  egfreetax1=0.00;
	                  egtaxablef1=0.00;
	                  egfreeval1=0.00;
	                  // exempted seprate sheet
	                  
	                  net=0.00;
	                  gtaxable1=0.00;
	                  gtaxable2=0.00;
	                  gtaxable3=0.00;
	                  gtaxable9=0.00;
	                  gtaxable10=0.00;
	                  gfreeval1=0.00;
	                  gfreeval2=0.00;
	                  gfreeval3=0.00;
	                  gfreeval9=0.00;
	                  gfreeval10=0.00;
	                  
	                  gfreetax1=0.00;
	                  gfreetax2=0.00;
	                  gfreetax3=0.00;
	                  gfreetax9=0.00;
	                  gfreetax10=0.00;
	                  
	                  gtaxablef1=0.00;
	                  gtaxablef2=0.00;
	                  gtaxablef3=0.00;
	                  gtaxablef9=0.00;
	                  gtaxablef10=0.00;
	                  gfreetax10=0.00;
	                  gdiff=0.00;
	                  boolean dataadd=true;
				    while(rs.next())
				    {
/*				    	if(doc_tp==0 && !rs.getString(49).equals("AAACA4495N") && rs.getDate(2).after(sdf.parse("30/06/2018")))
				    		dataadd=false;
				    	else
				    		dataadd=true;
*/				    	
				    	dataadd=true;
				    	
				    	if(dataadd)
				    	{
				    		inv = new SaletaxDto();
				    		div=rs.getInt(37);
				    		invlo="A";
				    		if (div==2)
				    			invlo="T";
				    		if (div==3)
				    			invlo="G";
				    		if (div==9)
				    			invlo="D";
				    		if (div==10)
				    			invlo="M";
				    		if (div==20)
				    			invlo="B";


				    		if (div==5)
				    			invlo="AP";
				    		if (div==6)
				    			invlo="TP";
				    		if (div==7)
				    			invlo="GP";
				    		if (div==8)
				    			invlo="DP";
				    		if (div==11)
				    			invlo="MP";
				    		if (div==21)
				    			invlo="BP";


				    		if(depo==1 || depo==56 || depo==5)
				    		{
				    			if (depo==1)
				    				inv.setInv_no("CWH"+invlo+rs.getString(1).substring(3));
				    			if (depo==1 && (div==5 || div==6 || div==7 || div==8 || div==11 || div==21) && doc_tp==67)
				    				inv.setInv_no("CWH"+invlo+rs.getString(1).substring(5));
				    			if (depo==56)
				    				inv.setInv_no("CWA"+invlo+rs.getString(1).substring(5));
				    			if (depo==56 && doc_tp==67)
				    			{								
				    				if(rs.getInt(38)<1000)
				    					inv.setInv_no("TR"+rs.getString(1).substring(0,3)+rs.getString(1).substring(5));
				    				else
				    					inv.setInv_no("T"+rs.getString(1).substring(0,3)+rs.getString(1).substring(4));
				    			}
				    			if (depo==56 && (div==5 || div==6 || div==7 || div==8 || div==11 || div==21) && doc_tp==67)
				    				inv.setInv_no("CWA"+invlo+rs.getString(1).substring(5));
				    			if (depo==5 && doc_tp==67)
				    				inv.setInv_no("BW/D/17-18/"+String.format("%05d",rs.getInt(38)));
				    			if (depo==5 && (div==5 || div==6 || div==7 || div==8 || div==11 || div==21) && doc_tp==67)
				    				inv.setInv_no("CWB"+invlo+rs.getString(1).substring(3));

				    		}
				    		else
				    		{

				    			if(doc_tp==67 && rs.getInt(33)==65)
				    				inv.setInv_no("SS"+rs.getString(1).substring(0,3)+rs.getString(1).substring(5));
				    			else if (doc_tp==67 && rs.getInt(33)==66 && (div==5 || div==6 || div==7 || div==8 || div==11 || div==21))
				    				inv.setInv_no("WS"+rs.getString(1).substring(0,3)+rs.getString(1).substring(5));
				    			else if (doc_tp==67 && rs.getInt(33)==66)
				    				inv.setInv_no("WO"+rs.getString(1).substring(0,3)+rs.getString(1).substring(5));
				    			else if (doc_tp==67  && rs.getInt(38)>999 && (div==5 || div==6 || div==7 || div==8 || div==11 || div==21))
				    				inv.setInv_no("S"+rs.getString(1).substring(0,3)+rs.getString(1).substring(4));
				    			else if (doc_tp==68  && rs.getInt(38)>999 && (div==5 || div==6 || div==7 || div==8 || div==11 || div==21))
				    				inv.setInv_no("S"+rs.getString(1).substring(0,3)+rs.getString(1).substring(4));

				    			else if (doc_tp==67 && (div==5 || div==6 || div==7 || div==8 || div==11 || div==21))
				    				inv.setInv_no("ST"+rs.getString(1).substring(0,3)+rs.getString(1).substring(5));
				    			else if (doc_tp==67 )
				    				inv.setInv_no("TR"+rs.getString(1).substring(0,3)+rs.getString(1).substring(5));
				    			else if (doc_tp==68 )
				    				inv.setInv_no("SI"+rs.getString(1).substring(0,3)+rs.getString(1).substring(5));
				    			else if(doc_tp==0 && (div==5 || div==6 || div==7 || div==11 || div==21 ))
				    				inv.setInv_no("SR"+rs.getString(1).substring(0,3)+rs.getString(1).substring(5));
				    			else if(doc_tp==0)
				    				inv.setInv_no("IN"+rs.getString(1).substring(0,3)+rs.getString(1).substring(5));
				    			else if (doc_tp==41 )
				    				inv.setInv_no(rs.getString(1).substring(0,3)+"C"+rs.getString(1).substring(4));
				    			else if (doc_tp==43 )
				    				inv.setInv_no(rs.getString(1).substring(0,3)+"E"+rs.getString(1).substring(4));
				    			else if (doc_tp==51 )
				    				inv.setInv_no(rs.getString(1).substring(0,3)+"D"+rs.getString(1).substring(4));
				    			else
				    				inv.setInv_no(rs.getString(1));
				    		}

/////				    		else if(doc_tp==41 && rs.getDate(36).after(sdf.parse("30/06/2017")))

				    		
				    		if(doc_tp==41 && rs.getDate(36)==null)
				    			inv.setPregst("Y");
				    		else if(doc_tp==41 && rs.getDate(36).after(sdf.parse("30/06/2017")) && rs.getDate(2).before(sdf.parse("01/10/2018")))
				    			inv.setPregst("N");
				    		else if(doc_tp==41 && rs.getDate(36).after(sdf.parse("31/03/2018")))
				    			inv.setPregst("N");
				    		else
				    			inv.setPregst("Y");

				    		inv.setInv_date(rs.getDate(2));
				    		inv.setParty_name(rs.getString(3));
				    		inv.setCity(rs.getString(4));
				    		inv.setTin(rs.getString(6));
				    		inv.setSale1(rs.getDouble(7));
				    		inv.setSale2(rs.getDouble(8));
				    		inv.setSale3(rs.getDouble(9));
				    		inv.setSale9(rs.getDouble(10));
				    		inv.setSale10(rs.getDouble(11));

				    		inv.setTaxable1(rs.getDouble(12)); //taxable0 
				    		inv.setTaxable2(rs.getDouble(13)); //taxable5
				    		inv.setTaxable3(rs.getDouble(14)); //taxable12
				    		inv.setTaxable9(rs.getDouble(15)); //taxable18
				    		inv.setTaxable10(rs.getDouble(16)); //taxable28

				    		inv.setTax_free1(rs.getDouble(17)); //sgst0
				    		inv.setTax_free2(rs.getDouble(18)); //sgst5
				    		inv.setTax_free3(rs.getDouble(19)); //sgst12
				    		inv.setTax_free9(rs.getDouble(20)); //sgst18
				    		inv.setTax_free10(rs.getDouble(21)); //sgst28

				    		inv.setTaxablef1(rs.getDouble(22)); //cgst0
				    		inv.setTaxablef2(rs.getDouble(23)); //cgst5
				    		inv.setTaxablef3(rs.getDouble(24)); //cgst12
				    		inv.setTaxablef9(rs.getDouble(25)); //cgst18
				    		inv.setTaxablef10(rs.getDouble(26)); //cgst28


				    		inv.setFreeval1(rs.getDouble(27)); //igst0
				    		inv.setFreeval2(rs.getDouble(28)); //igst5
				    		inv.setFreeval3(rs.getDouble(29)); //igst12
				    		inv.setFreeval9(rs.getDouble(30)); //igst18
				    		inv.setFreeval10(rs.getDouble(31)); //igst28

				    		inv.setNetamt(rs.getDouble(32)); //net amt


				    		inv.setTaxable33(rs.getDouble(41));  // taxable 3% G type
				    		inv.setSgst3(rs.getDouble(42));  // sgst 3% G type
				    		inv.setCgst3(rs.getDouble(43));  // cgst 3% G type
				    		inv.setIgst3(rs.getDouble(44));  // igst 3% G type

				    		inv.setTaxable6(rs.getDouble(45));  // taxable 6% H type
				    		inv.setSgst6(rs.getDouble(46));  // sgst 6% H type
				    		inv.setCgst6(rs.getDouble(47));  // cgst 6% H type
				    		inv.setIgst6(rs.getDouble(48));  // igst 6% H type


				    		System.out.println("NET AMT "+rs.getDouble(32));

				    		if(rs.getString(34).equals("A"))
				    			inv.setPack("5.00%");
				    		else if(rs.getString(34).equals("B"))
				    			inv.setPack("18.00%");
				    		else if(rs.getString(34).equals("C"))
				    			inv.setPack("28.00%");
				    		else if(rs.getString(34).equals("D"))
				    			inv.setPack("12.00%");
				    		else if(rs.getString(34).equals("E"))
				    			inv.setPack("0.00%");
				    		else if(rs.getString(34).equals("G"))
				    			inv.setPack("3.00%");
				    		else if(rs.getString(34).equals("H"))
				    			inv.setPack("6.00%");

				    		inv.setSpinv_no(rs.getString(35));
				    		inv.setSpinv_date(rs.getDate(36));
				    		if(doc_tp==41 || doc_tp==43)
				    			inv.setRemark(rs.getInt(39)==1?"Saleable":"UnSaleable");
				    		else
				    			inv.setRemark("");

				    		inv.setDisc10(rs.getDouble(40));
				    		gdiff+=rs.getDouble(40);  // diff amt
				    		gsale1+=rs.getDouble(7);
				    		gsale2+=rs.getDouble(8);
				    		gsale3+=rs.getDouble(9);
				    		gsale9+=rs.getDouble(10);
				    		gsale10+=rs.getDouble(11);

				    		
				    		gtaxable1+=rs.getDouble(12);
				    		gtaxable2+=rs.getDouble(13);
				    		gtaxable3+=rs.getDouble(14);
				    		gtaxable9+=rs.getDouble(15);
				    		gtaxable10+=rs.getDouble(16);


				    		gfreetax1+=rs.getDouble(17);
				    		gfreetax2+=rs.getDouble(18);
				    		gfreetax3+=rs.getDouble(19);
				    		gfreetax9+=rs.getDouble(20);
				    		gfreetax10+=rs.getDouble(21);

				    		gtaxablef1+=rs.getDouble(22);
				    		gtaxablef2+=rs.getDouble(23);
				    		gtaxablef3+=rs.getDouble(24);
				    		gtaxablef9+=rs.getDouble(25);
				    		gtaxablef10+=rs.getDouble(26);


				    		gfreeval1+=rs.getDouble(27);
				    		gfreeval2+=rs.getDouble(28);
				    		gfreeval3+=rs.getDouble(29);
				    		gfreeval9+=rs.getDouble(30);
				    		gfreeval10+=rs.getDouble(31);

				    		net+=rs.getDouble(32);

				    		gtaxable33+=rs.getDouble(41);
				    		gsgst3+=rs.getDouble(42);
				    		gcgst3+=rs.getDouble(43);
				    		gigst3+=rs.getDouble(44);

				    		gtaxable6+=rs.getDouble(45);
				    		gsgst6+=rs.getDouble(46);
				    		gcgst6+=rs.getDouble(47);
				    		gigst6+=rs.getDouble(48);

				    		inv.setDash(0);
				    		data.add(inv);
				    		if(rs.getDouble(12)>0)
				    		{
					    		egtaxable1+=rs.getDouble(12);
					    		inv.setDash(12);
					    		exmdata.add(inv);
				    			
				    		}
				    		
				    	} // eof of dataadd
				    }

			    	inv = new SaletaxDto();
			    	inv.setTin("Grand Total");
			    	inv.setSale1(gsale1);
			    	inv.setSale2(gsale2);
			    	inv.setSale3(gsale3);
			    	inv.setSale9(gsale9);
			    	inv.setSale10(gsale10);


			    	inv.setTaxable1(gtaxable1); //taxable0 
			    	inv.setTaxable2(gtaxable2); //taxable5
			    	inv.setTaxable3(gtaxable3); //taxable12
			    	inv.setTaxable9(gtaxable9); //taxable18
			    	inv.setTaxable10(gtaxable10); //taxable28

			    	inv.setTax_free1(gfreetax1); //sgst0
			    	inv.setTax_free2(gfreetax2); //sgst5
			    	inv.setTax_free3(gfreetax3); //sgst12
			    	inv.setTax_free9(gfreetax9); //sgst18
			    	inv.setTax_free10(gfreetax10); //sgst28
			    	
			    	inv.setTaxablef1(gtaxablef1); //cgst0
			    	inv.setTaxablef2(gtaxablef2); //cgst5
			    	inv.setTaxablef3(gtaxablef3); //cgst12
			    	inv.setTaxablef9(gtaxablef9); //cgst18
			    	inv.setTaxablef10(gtaxablef10); //cgst28
			    	

			    	inv.setFreeval1(gfreeval1); //igst0
			    	inv.setFreeval2(gfreeval2); //igst5
			    	inv.setFreeval3(gfreeval3); //igst12
			    	inv.setFreeval9(gfreeval9); //igst18
			    	inv.setFreeval10(gfreeval10); //igst28
			    	
			    	inv.setTaxable33(gtaxable33);  // taxable 3% G type
			    	inv.setSgst3(gsgst3);  // sgst 3% G type
			    	inv.setCgst3(gcgst3);  // cgst 3% G type
			    	inv.setIgst3(gigst3);  // igst 3% G type

			    	inv.setTaxable6(gtaxable6);  // taxable 3% G type
			    	inv.setSgst6(gsgst6);  // sgst 3% G type
			    	inv.setCgst6(gcgst6);  // cgst 3% G type
			    	inv.setIgst6(gigst6);  // igst 3% G type

			    	
			    	inv.setNetamt(net); //net amt

			    	inv.setDash(3);

			    	
			    	data.add(inv);
/*			    	data.addAll(exmdata);
			    	if(egtaxable1>0)
			    	{
			    	inv = new SaletaxDto();
			    	inv.setTin("Grand Total");
			    	inv.setTaxable1(egtaxable1); //taxable0 
			    	inv.setDash(13);
			    	data.add(inv);
			    	}
*/			    	rs.close();
				    st.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }

		 
		 
		 public List gstr1Report(int year,int depo,Date sdate,Date edate)
		   {
			  
			 System.out.println("yeha per ayaya gst1 sample ke case mai bhi");

			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SaletaxDto inv=null;
			   String query=null;
			   int wdepo=depo;
			   
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();


				    query="select sinv_dt Invoice_Date,inv_no Invoice_Number,mac_name Customer_Billing_Name,"+
				    " gst_no Customer_Billing_GSTIN, party_state_name State_Place_of_Supply,'G' Is_the_item_a_GOOD_G_or_SERVICE_S,"+
				    " pname Item_Description,hsn_code HSN_or_SAC_code,qty Item_Quantity,"+
				    " pack Item_Unit_of_Measurement,srate_net Item_Rate,discount Total_Item_Discount_Amount,"+
				    " taxable Item_Taxable_Value,cgst_rate CGST_Rate,cgst CGST_Amount,sgst_rate SGST_Rate,"+
				    " sgst SGST_Amount,igst_rate IGST_Rate,igst IGST_Amount, 0 Cess_rate,0 Cess_amount,"+
				    " 'N' Is_this_a_Bill_of_Supply,'N' Is_Reverse_Charge_Applicable,"+
				    " ''  Is_this_a_Nil_Rated_Exempt_NonGST_item,''  Original_Invoice_Date_In_case_of_amendment,"+
				    " ''  Original_Invoice_Number_In_case_of_amendment,''  Original_Customer_Billing_GSTIN_In_case_of_amendment,"+
				    " ''  GSTIN_of_Ecommerce_Marketplace,''  Date_of_Linked_Advance_Receipt,''  Voucher_Number_of_Linked_Advance_Receipt,"+
				    " ''  Adjustment_Amount_of_the_Linked_Advance_Receipt,''  Type_of_Export,"+
				    " ''  Shipping_Port_Code_Export,''  Shipping_Bill_Number_Export,''  Shipping_Bill_Date_Export,"+
				    " ''  Has_GST_IDT_TDS_been_deducted,my_gstno My_GSTIN,address Customer_Billing_Address,"+
				    " mcity Customer_Billing_City,party_state_name Customer_Billing_State,'N' Is_this_document_cancelled"+
				    " from ("+
				    " select i.sdepo_code,concat(i.sinv_yr,i.sinv_lo,substr(i.sinv_no,2,5)) inv_no,"+
				    " i.sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    " concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,"+
				    " i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty,"+
				    " i.stax_cd1 cgst_rate,i.stax_cd2 sgst_rate,i.stax_cd3 igst_rate,"+
				    " sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    " sum(i.discamount) discount,sum(i.net_amt) net,"+
				    " c.cmp_gst my_gstno,c.state_name,r.pname,r.pack "+
				    " from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r"+
				    " where i.div_code = 1 and i.fin_year = ? and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    " and i.sdoc_type  = 40 and i.del_tag <> 'D' and i.sqty <> 0"+
				    " and P.div_code = 1 and p.depo_code = ? and i.sprt_cd = p.mac_code "+
				    " and i.sdepo_code=c.depo_code "+ 
				    " and substr(p.gst_no,1,2) = g.state_code"+
				    " and r.div_code = 1 and i.sprd_cd = r.pcode "+
				    " group by i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3"+
				    " union all"+
				    " select i.sdepo_code,concat('TR',i.sinv_yr,i.sinv_lo,substr(i.sinv_no,4,3)) inv_no,"+
				    " i.sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    " concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,"+
				    " i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty,"+
				    " i.stax_cd1,i.stax_cd2,i.stax_cd3,"+
				    " sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    " sum(i.discamount) discount,sum(i.net_amt) net,"+
				    " c.cmp_gst my_gstno,c.state_name,r.pname,r.pack "+
				    " from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r"+
				    " where i.div_code = 1 and i.fin_year = ? and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    " and i.sdoc_type  = 67 and i.del_tag <> 'D' and i.sqty <> 0"+
				    " and P.div_code = 1 and p.depo_code = ? and i.sprt_cd = p.mac_code"+
				    " and i.sdepo_code=c.depo_code"+ 
				    " and substr(p.gst_no,1,2) = g.state_code"+
				    " and r.div_code = 1 and i.sprd_cd = r.pcode "+
				    " group by i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3) a"+ 
				    " union all"+

				    " select sinv_dt Invoice_Date,inv_no Invoice_Number,mac_name Customer_Billing_Name,"+
				    " gst_no Customer_Billing_GSTIN, party_state_name State_Place_of_Supply,'G' Is_the_item_a_GOOD_G_or_SERVICE_S,"+
				    " pname Item_Description,hsn_code HSN_or_SAC_code,qty Item_Quantity,"+
				    " pack Item_Unit_of_Measurement,srate_net Item_Rate,discount Total_Item_Discount_Amount,"+
				    " taxable Item_Taxable_Value,cgst_rate CGST_Rate,cgst CGST_Amount,sgst_rate SGST_Rate,"+
				    " sgst SGST_Amount,igst_rate IGST_Rate,igst IGST_Amount, 0 Cess_rate,0 Cess_amount,"+
				    " 'N' Is_this_a_Bill_of_Supply,'N' Is_Reverse_Charge_Applicable,"+
				    " ''  Is_this_a_Nil_Rated_Exempt_NonGST_item,''  Original_Invoice_Date_In_case_of_amendment,"+
				    " ''  Original_Invoice_Number_In_case_of_amendment,''  Original_Customer_Billing_GSTIN_In_case_of_amendment,"+
				    " ''  GSTIN_of_Ecommerce_Marketplace,''  Date_of_Linked_Advance_Receipt,''  Voucher_Number_of_Linked_Advance_Receipt,"+
				    " ''  Adjustment_Amount_of_the_Linked_Advance_Receipt,''  Type_of_Export,"+
				    " ''  Shipping_Port_Code_Export,''  Shipping_Bill_Number_Export,''  Shipping_Bill_Date_Export,"+
				    " ''  Has_GST_IDT_TDS_been_deducted,my_gstno My_GSTIN,address Customer_Billing_Address,"+
				    " mcity Customer_Billing_City,party_state_name Customer_Billing_State,'N' Is_this_document_cancelled"+
				    " from ("+
				    " select i.sdepo_code,concat(i.sinv_yr,i.sinv_lo,substr(i.sinv_no,2,5)) inv_no,"+
				    " i.sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    " concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,"+
				    " i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty,"+
				    " i.stax_cd1 cgst_rate,i.stax_cd2 sgst_rate,i.stax_cd3 igst_rate,"+
				    " sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    " sum(i.discamount) discount,sum(i.net_amt) net,"+
				    " c.cmp_gst my_gstno,c.state_name,r.pname,r.pack"+
				    " from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r"+
				    " where i.div_code = 2 and i.fin_year = ? and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    " and i.sdoc_type  = 40 and i.del_tag <> 'D' and i.sqty <> 0"+
				    " and P.div_code = 2 and p.depo_code = ? and i.sprt_cd = p.mac_code "+
				    " and i.sdepo_code=c.depo_code "+ 
				    " and substr(p.gst_no,1,2) = g.state_code"+
				    " and r.div_code = 2 and i.sprd_cd = r.pcode "+
				    " group by i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3"+
				    " union all"+
				    " select i.sdepo_code,concat('TR',i.sinv_yr,i.sinv_lo,substr(i.sinv_no,4,3)) inv_no,"+
				    " i.sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    " concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,"+
				    " i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty,"+
				    " i.stax_cd1,i.stax_cd2,i.stax_cd3,"+
				    " sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    " sum(i.discamount) discount,sum(i.net_amt) net,"+
				    " c.cmp_gst my_gstno,c.state_name,r.pname,r.pack "+
				    " from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r"+
				    " where i.div_code = 2 and i.fin_year = ? and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    " and i.sdoc_type  = 67 and i.del_tag <> 'D' and i.sqty <> 0"+
				    " and P.div_code = 2 and p.depo_code = ? and i.sprt_cd = p.mac_code"+
				    " and i.sdepo_code=c.depo_code"+ 
				    " and substr(p.gst_no,1,2) = g.state_code"+
				    " and r.div_code = 2 and i.sprd_cd = r.pcode"+
				    " group by i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3) a"+ 
				    " union all"+

				    " select sinv_dt Invoice_Date,inv_no Invoice_Number,mac_name Customer_Billing_Name,"+
				    " gst_no Customer_Billing_GSTIN, party_state_name State_Place_of_Supply,'G' Is_the_item_a_GOOD_G_or_SERVICE_S,"+
				    " pname Item_Description,hsn_code HSN_or_SAC_code,qty Item_Quantity,"+
				    " pack Item_Unit_of_Measurement,srate_net Item_Rate,discount Total_Item_Discount_Amount,"+
				    " taxable Item_Taxable_Value,cgst_rate CGST_Rate,cgst CGST_Amount,sgst_rate SGST_Rate,"+
				    " sgst SGST_Amount,igst_rate IGST_Rate,igst IGST_Amount, 0 Cess_rate,0 Cess_amount,"+
				    " 'N' Is_this_a_Bill_of_Supply,'N' Is_Reverse_Charge_Applicable,"+
				    " ''  Is_this_a_Nil_Rated_Exempt_NonGST_item,''  Original_Invoice_Date_In_case_of_amendment,"+
				    " ''  Original_Invoice_Number_In_case_of_amendment,''  Original_Customer_Billing_GSTIN_In_case_of_amendment,"+
				    " ''  GSTIN_of_Ecommerce_Marketplace,''  Date_of_Linked_Advance_Receipt,''  Voucher_Number_of_Linked_Advance_Receipt,"+
				    " ''  Adjustment_Amount_of_the_Linked_Advance_Receipt,''  Type_of_Export,"+
				    " ''  Shipping_Port_Code_Export,''  Shipping_Bill_Number_Export,''  Shipping_Bill_Date_Export,"+
				    " ''  Has_GST_IDT_TDS_been_deducted,my_gstno My_GSTIN,address Customer_Billing_Address,"+
				    " mcity Customer_Billing_City,party_state_name Customer_Billing_State,'N' Is_this_document_cancelled"+
				    " from ("+
				    " select i.sdepo_code,concat(i.sinv_yr,i.sinv_lo,substr(i.sinv_no,2,5)) inv_no,"+
				    " i.sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    " concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,"+
				    " i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty,"+
				    " i.stax_cd1 cgst_rate,i.stax_cd2 sgst_rate,i.stax_cd3 igst_rate,"+
				    " sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    " sum(i.discamount) discount,sum(i.net_amt) net,"+
				    " c.cmp_gst my_gstno,c.state_name,r.pname,r.pack"+
				    " from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r"+
				    " where i.div_code = 3 and i.fin_year = ? and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    " and i.sdoc_type  = 40 and i.del_tag <> 'D' and i.sqty <> 0"+
				    " and P.div_code = 3 and p.depo_code = ? and i.sprt_cd = p.mac_code "+
				    " and i.sdepo_code=c.depo_code "+ 
				    " and substr(p.gst_no,1,2) = g.state_code"+
				    " and r.div_code = 3 and i.sprd_cd = r.pcode "+
				    " group by i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3"+
				    " union all"+

				    " select i.sdepo_code,concat('TR',i.sinv_yr,i.sinv_lo,substr(i.sinv_no,4,3)) inv_no,"+
				    " i.sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    " concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,"+
				    " i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty,"+
				    " i.stax_cd1,i.stax_cd2,i.stax_cd3,"+
				    " sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    " sum(i.discamount) discount,sum(i.net_amt) net,"+
				    " c.cmp_gst my_gstno,c.state_name,r.pname,r.pack"+
				    " from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r"+
				    " where i.div_code = 3 and i.fin_year = ? and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    " and i.sdoc_type  = 67 and i.del_tag <> 'D' and i.sqty <> 0"+
				    " and P.div_code = 3 and p.depo_code = ? and i.sprt_cd = p.mac_code"+
				    " and i.sdepo_code=c.depo_code"+ 
				    " and substr(p.gst_no,1,2) = g.state_code"+
				    " and r.div_code = 3 and i.sprd_cd = r.pcode"+
				    " group by i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3) a"+ 
				    " union all "+
				    " select sinv_dt Invoice_Date,inv_no Invoice_Number,mac_name Customer_Billing_Name,"+
				    " gst_no Customer_Billing_GSTIN, party_state_name State_Place_of_Supply,'G' Is_the_item_a_GOOD_G_or_SERVICE_S,"+
				    " pname Item_Description,hsn_code HSN_or_SAC_code,qty Item_Quantity,"+
				    " pack Item_Unit_of_Measurement,srate_net Item_Rate,discount Total_Item_Discount_Amount,"+
				    " taxable Item_Taxable_Value,cgst_rate CGST_Rate,cgst CGST_Amount,sgst_rate SGST_Rate,"+
				    " sgst SGST_Amount,igst_rate IGST_Rate,igst IGST_Amount, 0 Cess_rate,0 Cess_amount,"+
				    " 'N' Is_this_a_Bill_of_Supply,'N' Is_Reverse_Charge_Applicable,"+
				    " ''  Is_this_a_Nil_Rated_Exempt_NonGST_item,''  Original_Invoice_Date_In_case_of_amendment,"+
				    " ''  Original_Invoice_Number_In_case_of_amendment,''  Original_Customer_Billing_GSTIN_In_case_of_amendment,"+
				    " ''  GSTIN_of_Ecommerce_Marketplace,''  Date_of_Linked_Advance_Receipt,''  Voucher_Number_of_Linked_Advance_Receipt,"+
				    " ''  Adjustment_Amount_of_the_Linked_Advance_Receipt,''  Type_of_Export,"+
				    " ''  Shipping_Port_Code_Export,''  Shipping_Bill_Number_Export,''  Shipping_Bill_Date_Export,"+
				    " ''  Has_GST_IDT_TDS_been_deducted,my_gstno My_GSTIN,address Customer_Billing_Address,"+
				    " mcity Customer_Billing_City,party_state_name Customer_Billing_State,'N' Is_this_document_cancelled"+
				    " from ("+
				    " select i.sdepo_code,concat(i.sinv_yr,i.sinv_lo,substr(i.sinv_no,2,5)) inv_no,"+
				    " i.sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    " concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,"+
				    " i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty,"+
				    " i.stax_cd1 cgst_rate,i.stax_cd2 sgst_rate,i.stax_cd3 igst_rate,"+
				    " sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    " sum(i.discamount) discount,sum(i.net_amt) net,"+
				    " c.cmp_gst my_gstno,c.state_name,r.pname,r.pack"+
				    " from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r"+
				    " where i.div_code = 10 and i.fin_year = ? and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    " and i.sdoc_type  = 40 and i.del_tag <> 'D' and i.sqty <> 0"+
				    " and P.div_code = 10 and p.depo_code = ? and i.sprt_cd = p.mac_code "+
				    " and i.sdepo_code=c.depo_code "+ 
				    " and substr(p.gst_no,1,2) = g.state_code"+
				    " and r.div_code = 10 and i.sprd_cd = r.pcode "+
				    " group by i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3"+
				    " union all"+
				    " select i.sdepo_code,concat('TR',i.sinv_yr,i.sinv_lo,substr(i.sinv_no,4,3)) inv_no,"+
				    " i.sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    " concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,"+
				    " i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty,"+
				    " i.stax_cd1,i.stax_cd2,i.stax_cd3,"+
				    " sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    " sum(i.discamount) discount,sum(i.net_amt) net,"+
				    " c.cmp_gst my_gstno,c.state_name,r.pname,r.pack"+
				    " from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r"+
				    " where i.div_code = 10 and i.fin_year = ? and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    " and i.sdoc_type  = 67 and i.del_tag <> 'D' and i.sqty <> 0"+
				    " and P.div_code = 10 and p.depo_code = ? and i.sprt_cd = p.mac_code"+
				    " and i.sdepo_code=c.depo_code"+ 
				    " and substr(p.gst_no,1,2) = g.state_code"+
				    " and r.div_code = 10 and i.sprd_cd = r.pcode"+
				    " group by i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3) a";

				    
				    
				    st = con.prepareStatement(query);

				    st.setInt(1, year);
		    	    st.setInt(2, depo);
		    	    st.setDate(3, sDate);
		    	    st.setDate(4, eDate);
		    	    st.setInt(5, depo);
				    st.setInt(6, year);
		    	    st.setInt(7, depo);
		    	    st.setDate(8, sDate);
		    	    st.setDate(9, eDate);
		    	    st.setInt(10, depo);
				    st.setInt(11, year);
		    	    st.setInt(12, depo);
		    	    st.setDate(13, sDate);
		    	    st.setDate(14, eDate);
		    	    st.setInt(15, depo);
				    st.setInt(16, year);
		    	    st.setInt(17, depo);
		    	    st.setDate(18, sDate);
		    	    st.setDate(19, eDate);
		    	    st.setInt(20, depo);
				    st.setInt(21, year);
		    	    st.setInt(22, depo);
		    	    st.setDate(23, sDate);
		    	    st.setDate(24, eDate);
		    	    st.setInt(25, depo);
				    st.setInt(26, year);
		    	    st.setInt(27, depo);
		    	    st.setDate(28, sDate);
		    	    st.setDate(29, eDate);
		    	    st.setInt(30, depo);
				    st.setInt(31, year);
		    	    st.setInt(32, depo);
		    	    st.setDate(33, sDate);
		    	    st.setDate(34, eDate);
		    	    st.setInt(35, depo);
				    st.setInt(36, year);
		    	    st.setInt(37, depo);
		    	    st.setDate(38, sDate);
		    	    st.setDate(39, eDate);
		    	    st.setInt(40, depo);


		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
				    while(rs.next())
				    {
					    	inv = new SaletaxDto();
					    	inv.setInv_date(rs.getDate(1));
					    	inv.setInv_no(rs.getString(2));
					    	inv.setParty_name(rs.getString(3)); //type 
					    	inv.setTin(rs.getString(4));		
					    	inv.setState_name(rs.getString(5)); 
					    	inv.setService_type(rs.getString(6)); 
					    	inv.setPname(rs.getString(7));
					    	inv.setHsn_code(rs.getLong(8));
					    	inv.setSqty(rs.getInt(9));
					    	inv.setPack(rs.getString(10));
					    	inv.setSrate_net(rs.getDouble(11));
					    	inv.setDiscount_amt10(rs.getDouble(12));
					    	inv.setTaxable1(rs.getDouble(13));
					    	inv.setCgst_rate(rs.getDouble(14));
					    	inv.setCgst_amount(rs.getDouble(15));
					    	inv.setSgst_rate(rs.getDouble(16));
					    	inv.setSgst_amount(rs.getDouble(17));
					    	inv.setIgst_rate(rs.getDouble(18));
					    	inv.setIgst_amount(rs.getDouble(19));
					    	inv.setCess_rate(rs.getDouble(20));
					    	inv.setCess_amount(rs.getDouble(21));

					    	inv.setMygst_no(rs.getString(37));
					    	inv.setAddress(rs.getString(38));
					    	inv.setCity(rs.getString(39));
					    	inv.setMstate_name(rs.getString(40));
					    	inv.setDash(40);
					    	data.add(inv);
				    }
				    rs.close();
				    st.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }

		 public List gstrB2B(int year,int depo,Date sdate,Date edate)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 


			   
			   SaletaxDto inv=null;
			   String query=null;
			   int div=BaseClass.loginDt.getDiv_code();
			   int wdiv=1;
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();


				   query=" select s.sdoc_type,ifnull(p.gst_no,'NA'), s.sinv_no, "+
		    		"s.sinv_dt,s1.net, concat(left(p.gst_no,2),'-',g.state_name) as Place_Of_Supply,  'N' Reverse_Charge, 'Regular' Invoice_Type,"+
		    		"'' E_Commerce_GSTIN, "+
		    		"(s.stax_Cd1+s.stax_Cd2+s.stax_Cd3) tax_rate, sum(s.taxable_Amt) taxable,"+
		    		"0.00 Cess_Amount ,left(p.gst_no,2),g.state_name,s.div_code,p.mac_name "+
		    		"from invsnd s,partyfst p,gststate g,   "+
		    		"(select s.div_code,s.sdoc_type,sinv_no,s.sinv_dt,sum(s.net_amt) net from invsnd s "+ 
		    		"where s.fin_year=? and s.div_code = ? and sdepo_code=? and sdoc_type=40 "+ 
		    		"and sinv_dt between ? and ?  and ifnull(s.del_tag,'')<>'D'   "+
		    		"group by s.fin_year, s.div_code,s.sdoc_type,s.sinv_no) s1 "+
		    		"where s.fin_year=? and s.div_code =? and s.sdepo_code=? and s.sdoc_type=40 "+ 
		    		"and s.sinv_dt between ? and ?  and ifnull(s.del_tag,'')<>'D'   "+
		    		"and s.div_code=s1.div_code and s.sdoc_Type=s1.sdoc_type and s.sinv_no=s1.sinv_no and s.sinv_dt=s1.sinv_dt "+
		    		"and p.div_code = s.div_code and p.depo_code=? and p.mac_code=s.sprt_cd and ifnull(p.mtype1,'')='R' "+
		    		"and left(p.gst_no,2) = g.state_code "+
		    		"group by s.fin_year, s.div_code,s.sdoc_type,s.sinv_no,s.stax_Cd1,s.stax_Cd2,s.stax_Cd3 "; 

			  	   


				    
				    st = con.prepareStatement(query);

				    st.setInt(1, year);
				    st.setInt(2, div);
		    	    st.setInt(3, depo);
		    	    st.setDate(4, sDate);
		    	    st.setDate(5, eDate);
		    	    st.setInt(6, year);
				    st.setInt(7, div);
		    	    st.setInt(8, depo);
		    	    st.setDate(9, sDate);
		    	    st.setDate(10, eDate);
		    	    st.setInt(11, depo);

		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
				    while(rs.next())
				    {

				    	
				    	inv = new SaletaxDto();
				    	inv.setTin(rs.getString(2)); //type 
				    	inv.setInv_no(rs.getString(3)); //type
				    	div=rs.getInt(15);

				    	
				    	inv.setInv_date(rs.getDate(4)); //type
				    	inv.setNetamt(rs.getDouble(5)); //net amt
				    	inv.setCity(rs.getString(6)); //place of supply
				    	inv.setRemark(rs.getString(7)); // revers charge
				    	inv.setPack(rs.getString(8)); // invoice typee
				    	inv.setCgst_rate(rs.getDouble(10)); //cgst rate
				    	inv.setTaxable1(rs.getDouble(11)); //taxable
				    	inv.setCess_amount(rs.getDouble(12)); //cesst amt
				    	inv.setParty_name(rs.getString(16)); // party name
				    	inv.setAddress("");
				    	inv.setCases(rs.getInt(15));/// div code
				    	inv.setDash(0);
				    	
				    	data.add(inv);
				    }

			    	
				    rs.close();
				    st.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstB2BSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }

		 
		 public List gstrHSN(int year,int depo,Date sdate,Date edate)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 

			   SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			   SaletaxDto inv=null;
			   String query=null;
			   int div=BaseClass.loginDt.getDiv_code();
			   
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();

				
				    	query="select left(s.hsn_code,4) hsn,'' descp, 'NOS-NUMBERS' UQC, sum(s.sqty+IFNULL(s.sfree_qty,0)) totalqty, "+
		    			"sum(s.net_amt) total_value, sum(s.taxable_Amt) taxable, "+
		    			"sum(s.igst_amt) igst,sum(s.cgst_amt) cgst,sum(s.sgst_amt) sgst,0.00 cess, 0 dash1 "+
		    			"from invsnd s "+
		    			"where s.fin_year=? and s.div_code=? and sdepo_code=? and sdoc_type=40 "+ 
		    			"and sinv_dt between ? and ? "+
		    			"and ifnull(s.del_tag,'')<>'D'   "+
		    			"group by s.fin_year, left(s.hsn_code,4) "+
		    			"union all "+
		    			"select left(s.hsn_code,4) hsn,'' descp, 'NOS-NUMBERS' UQC, sum(s.sqty+ifnull(s.sfree_qty,0)) totalqty, "+
		    			"sum(s.net_amt) total_value, sum(s.taxable_Amt) taxable, "+
		    			"sum(s.igst_amt) igst,sum(s.cgst_amt) cgst,sum(s.sgst_amt) sgst,0.00 cess,1 dash1 "+
		    			"from invsnd s "+
		    			"where s.fin_year=? and s.div_code=? and sdepo_code=? and sdoc_type=60 "+ 
		    			"and sinv_dt between ? and ? "+
		    			"and ifnull(s.del_tag,'')<>'D'   "+
		    			"group by s.fin_year, left(s.hsn_code,4)";
				
				    	st = con.prepareStatement(query);

				    	st.setInt(1, year);
				    	st.setInt(2, div);
				    	st.setInt(3, depo);
				    	st.setDate(4, sDate);
				    	st.setDate(5, eDate);
				    	st.setInt(6, year);
				    	st.setInt(7, div);
				    	st.setInt(8, depo);
				    	st.setDate(9, sDate);
				    	st.setDate(10, eDate);
				    	
				    
		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
				    while(rs.next())
				    {

				    		inv = new SaletaxDto();
				    		inv.setHsn_code(rs.getLong(1)); //hsn code 
				    		inv.setPname(rs.getString(2)); //description 
				    		inv.setPack(rs.getString(3)); // UQC
				    		inv.setSqty(rs.getInt(4)); // total qty
				    		inv.setNetamt(rs.getDouble(5)); //net
				    		inv.setTaxable1(rs.getDouble(6)); //taxable
				    		inv.setIgst_amount(rs.getDouble(7)); //igst rate
				    		inv.setCgst_amount(rs.getDouble(8)); //cgst rate
				    		inv.setSgst_amount(rs.getDouble(9)); //sgst rate
				    		inv.setCess_amount(rs.getDouble(10)); //cesst amt
				    		inv.setDash(rs.getInt(11));
				    		data.add(inv);
				    }

			    	
				    rs.close();
				    st.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstHSNSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }

		 public List gstrDOC(int year,int depo,Date sdate,Date edate)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SaletaxDto inv=null;
			   String query=null;
			   int div=BaseClass.loginDt.getDiv_code();
			   
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();

				query="select div_code,doc_type,'Invoices for outward supply' docname," +
				"min(right(inv_no,5)) sno,max(right(inv_no,5)) eno," +
				"count(*), 0 cancelled "+
				"from invfst where  fin_year=? and div_code=? and depo_code=? and doc_type in (40,41,51)   "+
				"and inv_date between ? and ? and ifnull(del_tag,'')<>'D'   group by div_code,doc_type " ;

			    

				    st = con.prepareStatement(query);

				    st.setInt(1, year);
				    st.setInt(2, div);
		    	    st.setInt(3, depo);
		    	    st.setDate(4, sDate);
		    	    st.setDate(5, eDate);
		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
				    while(rs.next())
				    {

				    	inv = new SaletaxDto();
				    	if(rs.getInt(2)==41)
				    		inv.setPname("Credit Note");
				    	else if(rs.getInt(2)==51)
				    		inv.setPname("Debit Note");
				    	else
				    		inv.setPname(rs.getString(3)); //description 
				    	inv.setPack(rs.getString(4)); // sno
				    	inv.setCity(rs.getString(5)); // eno
				    	inv.setSqty(rs.getInt(6)); // no of inovices
				    	inv.setDash(0);
				    	data.add(inv);
				    }

			    	
				    rs.close();
				    st.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstDOCSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }
		 public List gstrB2C(int year,int depo,Date sdate,Date edate)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SaletaxDto inv=null;
			   String query=null;
//			   String stateName = ((String) BaseClass.loginDt.getGstMap().get(BaseClass.loginDt.getState_code()));
			   String stateName = "Madhya Pradesh";
			   
			   int div=BaseClass.loginDt.getDiv_code();

			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();




				    query=" select s.sdoc_type,'OE' type, concat(left(p.state_code,2),'-',UPPER(p.mstate_name))  Place_Of_Supply, "+ 
				    " (s.stax_Cd1+s.stax_Cd2+s.stax_Cd3) tax_rate, sum(s.taxable_Amt) taxable,"+
				    " 0.00 Cess_Amount, '' E_Commerce_GSTIN"+
				    " from invsnd s,partyfst p"+
				    " where s.fin_year=? and s.div_code =? and s.sdepo_code=? and s.sdoc_type=40 "+ 
				    " and s.sinv_dt between ? and ?  and ifnull(s.del_tag,'')<>'D'"+   
				    " and p.div_code = s.div_code and p.depo_code=? and p.mac_code=s.sprt_cd  "+
				    " and ifnull(p.mtype1,'') = 'U' "+
				    " group by s.fin_year,(s.stax_Cd1+s.stax_Cd2+s.stax_Cd3)" ; 			    

				    
				    st = con.prepareStatement(query);

				    st.setInt(1, year);
				    st.setInt(2, div);
		    	    st.setInt(3, depo);
		    	    st.setDate(4, sDate);
		    	    st.setDate(5, eDate);
	    	    	st.setInt(6, depo);

		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
				    while(rs.next())
				    {

				    	inv = new SaletaxDto();
				    	inv.setTin(rs.getString(2)); //type 
				    	inv.setCity(rs.getString(3)); //place of supply
				    	inv.setCgst_rate(rs.getDouble(4)); //cgst rate
				    	inv.setTaxable1(rs.getDouble(5)); //taxable
				    	inv.setCess_amount(rs.getDouble(6)); //cesst amt
				    	inv.setDash(0);
				    	data.add(inv);
				    }

			    	
				    rs.close();
				    st.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstB2BSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }

		 public List gstrEXM(int year,int depo,Date sdate,Date edate)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SaletaxDto inv=null;
			   String query=null;
//			   String state=BaseClass.loginDt.getState_code();
			   String state="23";
			   
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();

				    query=" select 'Inter-State supplies to registered persons' description,0.00 NilRated, ifnull(sum(s.taxable_amt),0.00) Exempted,0.00 NonGST from invsnd s,partyfst p " +
				    " where s.fin_year=? and s.div_code in (1,2,3,10,20) and s.sdepo_code=? "+
		    		" and s.sdoc_type in (40,67) and s.sinv_dt between ? and ? and s.stax_type='E' and ifnull(s.del_tag,'')<>'D' "+
		    		" and p.div_code=s.div_code and p.depo_code=? and p.mac_code=s.sprt_cd and p.gststate_code<>? "+
		    		" union all "+
		    		" select 'Intra-State supplies to registered persons' description,0.00 NilRated, ifnull(sum(s.taxable_amt),0.00) Exempted,0.00 NonGST from invsnd s,partyfst p " +
		    		" where s.fin_year=? and s.div_code in (1,2,3,10,20) and s.sdepo_code=? "+
		    		" and s.sdoc_type in (40,67) and s.sinv_dt between ? and ? and s.stax_type='E' and ifnull(s.del_tag,'')<>'D' "+
		    		"and p.div_code=s.div_code and p.depo_code=? and p.mac_code=s.sprt_cd and p.gststate_code=?";

				    if(BaseClass.loginDt.getPack_code()==2)
				    {
					    query=" select 'Inter-State supplies to registered persons' description,0.00 NilRated, ifnull(sum(s.taxable_amt),0.00) Exempted,0.00 NonGST from invsnd s,partyfst p " +
							    " where s.fin_year=? and s.div_code in (5,6,7,11,21) and s.sdepo_code=? "+
					    		" and s.sdoc_type in (68,67) and s.sinv_dt between ? and ? and (s.stax_cd1+s.stax_cd2+s.stax_cd3)=0 and ifnull(s.del_tag,'')<>'D' "+
					    		" and p.div_code=1 and p.depo_code=? and p.mac_code=s.sprt_cd and p.gststate_code<>? "+
					    		" union all "+
					    		" select 'Intra-State supplies to registered persons' description,0.00 NilRated, ifnull(sum(s.taxable_amt),0.00) Exempted,0.00 NonGST from invsnd s,partyfst p " +
					    		" where s.fin_year=? and s.div_code in (5,6,7,11,21) and s.sdepo_code=? "+
					    		" and s.sdoc_type in (68,67) and s.sinv_dt between ? and ? and (s.stax_cd1+s.stax_cd2+s.stax_cd3)=0 and ifnull(s.del_tag,'')<>'D' "+
					    		"and p.div_code=1  and p.depo_code=? and p.mac_code=s.sprt_cd and p.gststate_code=?";

				    }

				    st = con.prepareStatement(query);

				    st.setInt(1, year);
		    	    st.setInt(2, depo);
		    	    st.setDate(3, sDate);
		    	    st.setDate(4, eDate);
		    	    st.setInt(5, depo);
		    	    st.setString(6, state);

				    st.setInt(7, year);
		    	    st.setInt(8, depo);
		    	    st.setDate(9, sDate);
		    	    st.setDate(10, eDate);
		    	    st.setInt(11, depo);
		    	    st.setString(12, state);
		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
				    while(rs.next())
				    {

				    	inv = new SaletaxDto();
				    	inv.setCity(rs.getString(1)); // description
				    	inv.setCgst_rate(rs.getDouble(2)); //nil rate
				    	inv.setTaxable1(rs.getDouble(3)); //exempted
				    	inv.setCess_amount(rs.getDouble(4)); //non gst
				    	inv.setDash(0);
				    	data.add(inv);
				    }

			    	
				    rs.close();
				    st.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstB2BSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }
		 
		 public List gstr2(int year,int depo,Date sdate,Date edate)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SaletaxDto inv=null;
			   String query=null;
			   int div=BaseClass.loginDt.getDiv_code();
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();

//5187				    " ( select i.sdepo_code,concat(i.sinv_yr,i.sinv_lo,substr(i.sinv_no,2,5)) inv_no,"+

				    if(BaseClass.loginDt.getPack_code()==1)
				    {
				    	query ="select sinv_dt Invoice_Date,inv_no Invoice_Number,"+
				    			" mac_name Customer_Billing_Name,gst_no Customer_Billing_GSTIN,"+
				    			" party_state_name State_Place_of_Supply,'G' Is_the_item_a_GOOD_G_or_SERVICE_S,"+
				    			" pname Item_Description,hsn_code HSN_or_SAC_code,"+
				    			" qty Item_Quantity,pack Item_Unit_of_Measurement,srate_net Item_Rate,"+
				    			" discount Total_Item_Discount_Amount,taxable Item_Taxable_Value,cgst_rate CGST_Rate,"+
				    			" cgst CGST_Amount,sgst_rate SGST_Rate,sgst SGST_Amount,igst_rate IGST_Rate,igst IGST_Amount,"+
				    			" 0 Cess_rate, 0 Cess_amount,'' ITC_Claim_Type,'' CGST_ITC_Claim_Amount,'' SGST_ITC_Claim_Amount,"+
				    			" '' IGST_ITC_Claim_Amount,'' CESS_ITC_Claim_Amount,'N' Is_this_a_Bill_of_Supply,'N' Is_Reverse_Charge_Applicable,"+
				    			" ''  Is_this_a_Nil_Rated_Exempt_NonGST_item,''  Original_Invoice_Date_In_case_of_amendment,"+
				    			" ''  Original_Invoice_Number_In_case_of_amendment,''  Original_Supplier_GSTIN_In_case_of_amendment,"+
				    			" ''  Date_of_Linked_Advance_Payment,''  Voucher_Number_of_Linked_Advance_Payment,"+
				    			" ''  Adjustment_Amount_of_the_Linked_Advance_payment,''  Type_of_import,"+
				    			" ''  Bill_of_Entry_Port_Code, ''  Bill_of_Entry_Number,''  Bill_of_Entry_Date,"+
				    			" my_gstno My_GSTIN,address Supplier_Address,mcity Supplier_City,party_state_name supplier_State,"+
				    			" '' Goods_Receipt_Note_Number,'' Goods_Receipt_Note_Date,'' Goods_Receipt_Quantity,"+
				    			" '' Goods_Receipt_Amount,'N' Is_this_document_cancelled,inw_no Inward_no from "+
				    			" ( select i.sdepo_code,spinv_no inv_no,"+
				    			" i.spinv_dt sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    			" concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty, "+
				    			" i.stax_cd1 cgst_rate,i.stax_cd2 sgst_rate,i.stax_cd3 igst_rate,"+
				    			" sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    			" sum(i.discamount) discount,sum(i.net_amt) net, c.cmp_gst my_gstno,c.state_name,r.pname,r.pack,concat(i.sinv_yr,i.sinv_lo,substr(i.sinv_no,2,5)) inw_no"+
				    			" from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r "+
				    			" where i.fin_year = ? and i.div_code in (1,2,3,10,20) and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    			" and i.sdoc_type  in (60,61,62,72,73) and ifnull(i.del_tag,'') <> 'D' and i.sqty <> 0 "+
				    			" AND P.div_code = i.div_code and p.depo_code = ? and i.sprt_cd = p.mac_code and ifnull(p.del_tag,'') <> 'D' "+
				    			" and i.sdepo_code=c.depo_code "+
				    			" and substr(p.gst_no,1,2) = g.state_code "+
				    			" and r.div_code = i.div_code and i.sprd_cd = r.pcode "+
				    			" group by i.div_code,i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3) a" ;
				    }
				    
				    if(BaseClass.loginDt.getPack_code()==2)
				    {
				    	query ="select sinv_dt Invoice_Date,inv_no Invoice_Number,"+
				    			" mac_name Customer_Billing_Name,gst_no Customer_Billing_GSTIN,"+
				    			" party_state_name State_Place_of_Supply,'G' Is_the_item_a_GOOD_G_or_SERVICE_S,"+
				    			" pname Item_Description,hsn_code HSN_or_SAC_code,"+
				    			" qty Item_Quantity,pack Item_Unit_of_Measurement,srate_net Item_Rate,"+
				    			" discount Total_Item_Discount_Amount,taxable Item_Taxable_Value,cgst_rate CGST_Rate,"+
				    			" cgst CGST_Amount,sgst_rate SGST_Rate,sgst SGST_Amount,igst_rate IGST_Rate,igst IGST_Amount,"+
				    			" 0 Cess_rate, 0 Cess_amount,'' ITC_Claim_Type,'' CGST_ITC_Claim_Amount,'' SGST_ITC_Claim_Amount,"+
				    			" '' IGST_ITC_Claim_Amount,'' CESS_ITC_Claim_Amount,'N' Is_this_a_Bill_of_Supply,'N' Is_Reverse_Charge_Applicable,"+
				    			" ''  Is_this_a_Nil_Rated_Exempt_NonGST_item,''  Original_Invoice_Date_In_case_of_amendment,"+
				    			" ''  Original_Invoice_Number_In_case_of_amendment,''  Original_Supplier_GSTIN_In_case_of_amendment,"+
				    			" ''  Date_of_Linked_Advance_Payment,''  Voucher_Number_of_Linked_Advance_Payment,"+
				    			" ''  Adjustment_Amount_of_the_Linked_Advance_payment,''  Type_of_import,"+
				    			" ''  Bill_of_Entry_Port_Code, ''  Bill_of_Entry_Number,''  Bill_of_Entry_Date,"+
				    			" my_gstno My_GSTIN,address Supplier_Address,mcity Supplier_City,party_state_name supplier_State,"+
				    			" '' Goods_Receipt_Note_Number,'' Goods_Receipt_Note_Date,'' Goods_Receipt_Quantity,"+
				    			" '' Goods_Receipt_Amount,'N' Is_this_document_cancelled,inw_no Inward_no from "+
				    			" ( select i.sdepo_code,spinv_no inv_no,"+
				    			" i.spinv_dt sinv_dt,i.sprt_cd,p.mac_name,p.gst_no,substr(p.gst_no,1,2) party_state_code,g.state_name party_state_name,"+
				    			" concat(p.madd1,',',p.madd2,',',p.madd3) address,p.mcity,i.sprd_cd,i.hsn_code,i.srate_net,sum(i.sqty) qty, "+
				    			" i.stax_cd1 cgst_rate,i.stax_cd2 sgst_rate,i.stax_cd3 igst_rate,"+
				    			" sum(i.taxable_amt) taxable,sum(i.cgst_amt) cgst,sum(i.sgst_amt) sgst,sum(i.igst_amt) igst,"+
				    			" sum(i.discamount) discount,sum(i.net_amt) net, c.cmp_gst my_gstno,c.state_name,r.pname,r.pack,concat(i.sinv_yr,i.sinv_lo,substr(i.sinv_no,2,5)) inw_no"+
				    			" from invsnd i,partyfst p,cmpmsfl c,gststate g,prd r "+
				    			" where i.fin_year = ? and i.div_code in (5,6,7,11,21) and i.sdepo_code = ? and i.sinv_dt between ? and ? "+
				    			" and i.sdoc_type  in (60,61,62,72,73) and ifnull(i.del_tag,'') <> 'D' and i.sqty <> 0 "+
				    			" AND P.div_code = 1 and p.depo_code = ? and i.sprt_cd = p.mac_code and ifnull(p.del_tag,'') <> 'D' "+
				    			" and i.sdepo_code=c.depo_code "+
				    			" and substr(p.gst_no,1,2) = g.state_code "+
				    			" and r.div_code = i.div_code and i.sprd_cd = r.pcode "+
				    			" group by i.div_code,i.sdepo_code,i.sinv_no,i.sinv_dt,i.sprt_cd,i.sprd_cd,i.hsn_code,i.srate_net,i.stax_cd1,i.stax_cd2,i.stax_cd3) a" ;
				    }

				    st = con.prepareStatement(query);

				    st.setInt(1, year);
		    	    st.setInt(2, depo);
		    	    st.setDate(3, sDate);
		    	    st.setDate(4, eDate);
		    	    st.setInt(5, depo);

		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
				    while(rs.next())
				    {


				    	inv = new SaletaxDto();

				    	inv.setInv_date(rs.getDate(1)); 
				    	inv.setSpinv_no(rs.getString(2));  
				    	inv.setParty_name(rs.getString(3));  
				    	inv.setTin(rs.getString(4)); 
				    	inv.setState_name(rs.getString(5)); //place of supply
				    	inv.setPname(rs.getString(7));
				    	inv.setHsn_code(rs.getInt(8));
				    	inv.setSqty(rs.getInt(9));
				    	inv.setPack((rs.getString(10)));
				    	inv.setSrate_net(rs.getDouble(11));
				    	inv.setDiscount_amt10(rs.getDouble(12));
				    	inv.setTaxable1(rs.getDouble(13));
				    	inv.setCgst_rate(rs.getDouble(14));
				    	inv.setCgst_amount(rs.getDouble(15));
				    	inv.setSgst_rate(rs.getDouble(16));
				    	inv.setSgst_amount(rs.getDouble(17));
				    	inv.setIgst_rate(rs.getDouble(18));
				    	inv.setIgst_amount(rs.getDouble(19));
				    	inv.setCgst_rate(rs.getDouble(14));
				    	inv.setCgst_amount(rs.getDouble(15));
				    	inv.setCess_rate(rs.getDouble(16));
				    	inv.setCess_amount(rs.getDouble(17));
				    	inv.setMygst_no(rs.getString(40));
				    	inv.setAddress(rs.getString(41));
				    	inv.setCity(rs.getString(42));
				    	inv.setInv_no(rs.getString(49));
				    	
/*				    	if(depo==1 || depo==56 || depo==5)
				    	{
							if (depo==1)
								inv.setInv_no("CWH"+rs.getString(3).substring(3,4)+String.format("%04d",Integer.parseInt(rs.getString(3).substring(4))));
							if (depo==1 && (div==5 || div==6 || div==7 || div==8 || div==11))
								inv.setInv_no("CWH"+rs.getString(3).substring(3,4)+"P"+String.format("%03d",Integer.parseInt(rs.getString(3).substring(4))));
							if (depo==56 && (div==5 || div==6 || div==7 || div==8 || div==11) )
								inv.setInv_no("CWA"+rs.getString(3).substring(4,5)+"P"+String.format("%03d",Integer.parseInt(rs.getString(3).substring(5))));
							if (depo==5 && (div==5 || div==6 || div==7 || div==8 || div==11) )
								inv.setInv_no("CWB"+rs.getString(3).substring(4,5)+"P"+String.format("%03d",Integer.parseInt(rs.getString(3).substring(5))));
				    	}
*/
				    	
				    	inv.setDash(0);
				    	data.add(inv);
				    }

			    	
				    rs.close();
				    st.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstB2BSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }

		 public List gstrCDNR(int year,int depo,Date sdate,Date edate,int doctype)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   PreparedStatement ps1 =null;
			   ResultSet rs1= null; 
			   int wdoc=90;
			   if(doctype==51)
				   wdoc=95;
			   
			   SaletaxDto inv=null;
			   String query=null;
			   int cmpdepo=BaseClass.loginDt.getCmp_code();
			   SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();


				    if(sdate.before(sdf.parse("01/10/2018")))  // for 30/06/2017
				    {				    
				    	query=" select ifnull(s.new1,p.gst_no),s.spinv_no,s.spinv_dt,if(sdoc_type=41,concat(s.sinv_yr,s.sinv_lo,'C',right(s.sinv_no,4)),concat(s.sinv_yr,s.sinv_lo,'D',right(s.sinv_no,4))),s.sinv_dt," +
				    			" if(sdoc_type=41,'C','D') doctype,'01-Sales Return' reason,concat(p.gststate_code,'-',p.mstate_name) state,SUM(s.net_amt),(s.stax_cd1+s.stax_cd2+s.stax_cd3) rate,SUM(s.taxable_amt),"+
				    			" 0.00 cess_amt,if(s.spinv_dt<'2017-07-01' or s.spinv_dt is null,'Y','N') pregst,p.mac_name,s.div_code,s.sinv_no "+
				    			" from invsnd s,partyfst p  where s.fin_year=? and s.sdepo_code=? and s.sdoc_type=? and ifnull(s.sprd_cd,0)<>0 and s.sinv_dt between ? and ? and s.spinv_Dt is not null "+
				    			" and ifnull(s.del_tag,'')<>'D' and p.div_code=s.div_code and p.depo_code=? and p.mac_Code=s.sprt_cd GROUP BY S.DIV_CODE,S.SINV_NO,S.SINV_DT,(S.STAX_cD1+S.STAX_cD2+S.STAX_CD3) "+
				    			" union all "+
				    			" select ifnull(s.new1,p.gst_no),s.spinv_no,s.spinv_dt,if(sdoc_type=41,concat(s.sinv_yr,s.sinv_lo,'C',right(s.sinv_no,4)),concat(s.sinv_yr,s.sinv_lo,'D',right(s.sinv_no,4))),s.sinv_dt," +
				    			" if(sdoc_type=41,'C','D') doctype,'07-Others' reason,concat(p.gststate_code,'-',p.mstate_name) state,SUM(s.net_amt),(s.stax_cd1+s.stax_cd2+s.stax_cd3) rate,SUM(s.taxable_amt),"+
				    			" 0.00 cess_amt,if(s.spinv_dt<'2017-07-01' or s.spinv_dt is null,'Y','N') pregst,p.mac_name,s.div_code,s.sinv_no "+
				    			" from invsnd s,partyfst p  where s.fin_year=? and s.sdepo_code=? and s.sdoc_type=? and ifnull(s.sprd_cd,0)=0 and s.net_amt > 0 and  s.sinv_dt between ? and ?  "+
				    			" and ifnull(s.del_tag,'')<>'D' and p.div_code=s.div_code and p.depo_code=? and p.mac_Code=s.sprt_cd GROUP BY S.DIV_CODE,S.SINV_NO,S.SINV_DT,(S.STAX_cD1+S.STAX_cD2+S.STAX_CD3) "+
				    			" union all "+
				    			" select ifnull(s.new1,p.gst_no),s.spinv_no,s.spinv_dt,if(sdoc_type=41,concat(s.sinv_yr,s.sinv_lo,'C',right(s.sinv_no,4)),concat(s.sinv_yr,s.sinv_lo,'D',right(s.sinv_no,4))),s.sinv_dt," +
				    			" if(sdoc_type=41,'C','D') doctype,'01-Sales Return' reason,concat(p.gststate_code,'-',p.mstate_name) state,SUM(s.net_amt),(s.stax_cd1+s.stax_cd2+s.stax_cd3) rate,SUM(s.taxable_amt),"+
				    			" 0.00 cess_amt,if(s.spinv_dt<'2017-07-01' or s.spinv_dt is null,'Y','N') pregst,p.mac_name,s.div_code,s.sinv_no "+
				    			" from invsnd s,partyfst p  where s.fin_year=? and s.sdepo_code=? and s.sdoc_type=? and ifnull(s.sprd_cd,0)<>0 and s.sinv_dt between ? and ?  "+
				    			" and ifnull(s.del_tag,'')<>'D' and p.div_code=4 and p.depo_code=? and p.mac_Code=s.sprt_cd GROUP BY S.DIV_CODE,S.SINV_NO,S.SINV_DT,(S.STAX_cD1+S.STAX_cD2+S.STAX_CD3) "+
				    			" union all "+ 
				    			" select p.gst_no,s.bill_no spinv_no,s.bill_date spinv_dt,if(vbook_cd1=90,concat(s.vou_yr,s.vou_lo,'C',right(s.vou_no,4)),concat(s.vou_yr,s.vou_lo,'D',right(s.vou_no,4))),s.vou_date,"+ 
				    			" if(vbook_cd1=90,'C','D') doctype,'01-Sales Return' reason,concat(p.gststate_code,'-',p.mstate_name) state,SUM(s.net_amt),(s.stax_per+s.ctax_per+s.itax_per) rate,SUM(s.taxable_amt),"+
				    			" 0.00 cess_amt,'N' pregst,p.mac_name,s.div_code,s.vou_no "+ 
				    			" from ledfile s,partyfst p  where s.fin_year=? and s.vdepo_code=? and s.vbook_cd1=? and ifnull(s.taxable_amt,0)<>0 and s.vou_date between ? and ? "+ 
				    			" and ifnull(s.del_tag,'')<>'D' and p.div_code=4 and p.depo_code=? and p.mac_Code=s.vac_code GROUP BY S.DIV_CODE,S.vou_NO,S.VOU_DATE,(S.cgst_amt+S.sgst_amt+S.igst_amt) ";
				    }
				    else
				    {
				    	query=" select ifnull(s.new1,p.gst_no),s.spinv_no,s.spinv_dt,if(sdoc_type=41,concat(s.sinv_yr,s.sinv_lo,'C',right(s.sinv_no,4)),concat(s.sinv_yr,s.sinv_lo,'D',right(s.sinv_no,4))),s.sinv_dt," +
				    			" if(sdoc_type=41,'C','D') doctype,'01-Sales Return' reason,concat(p.gststate_code,'-',p.mstate_name) state,SUM(s.net_amt),(s.stax_cd1+s.stax_cd2+s.stax_cd3) rate,SUM(s.taxable_amt),"+
				    			" 0.00 cess_amt,if(s.spinv_dt<'2018-04-01' or s.spinv_dt is null,'Y','N') pregst,p.mac_name,s.div_code,s.sinv_no "+
				    			" from invsnd s,partyfst p  where s.fin_year=? and s.sdepo_code=? and s.sdoc_type=? and ifnull(s.sprd_cd,0)<>0 and s.sinv_dt between ? and ? and s.spinv_Dt is not null "+
				    			" and ifnull(s.del_tag,'')<>'D' and p.div_code=s.div_code and p.depo_code=? and p.mac_Code=s.sprt_cd GROUP BY S.DIV_CODE,S.SINV_NO,S.SINV_DT,(S.STAX_cD1+S.STAX_cD2+S.STAX_CD3) "+
				    			" union all "+
				    			" select ifnull(s.new1,p.gst_no),s.spinv_no,s.spinv_dt,if(sdoc_type=41,concat(s.sinv_yr,s.sinv_lo,'C',right(s.sinv_no,4)),concat(s.sinv_yr,s.sinv_lo,'D',right(s.sinv_no,4))),s.sinv_dt," +
				    			" if(sdoc_type=41,'C','D') doctype,'07-Others' reason,concat(p.gststate_code,'-',p.mstate_name) state,SUM(s.net_amt),(s.stax_cd1+s.stax_cd2+s.stax_cd3) rate,SUM(s.taxable_amt),"+
				    			" 0.00 cess_amt,if(s.spinv_dt<'2018-04-01' or s.spinv_dt is null,'Y','N') pregst,p.mac_name,s.div_code,s.sinv_no "+
				    			" from invsnd s,partyfst p  where s.fin_year=? and s.sdepo_code=? and s.sdoc_type=? and ifnull(s.sprd_cd,0)=0 and s.net_amt > 0 and  s.sinv_dt between ? and ?  "+
				    			" and ifnull(s.del_tag,'')<>'D' and p.div_code=s.div_code and p.depo_code=? and p.mac_Code=s.sprt_cd GROUP BY S.DIV_CODE,S.SINV_NO,S.SINV_DT,(S.STAX_cD1+S.STAX_cD2+S.STAX_CD3) "+
				    			" union all "+
				    			" select ifnull(s.new1,p.gst_no),s.spinv_no,s.spinv_dt,if(sdoc_type=41,concat(s.sinv_yr,s.sinv_lo,'C',right(s.sinv_no,4)),concat(s.sinv_yr,s.sinv_lo,'D',right(s.sinv_no,4))),s.sinv_dt," +
				    			" if(sdoc_type=41,'C','D') doctype,'01-Sales Return' reason,concat(p.gststate_code,'-',p.mstate_name) state,SUM(s.net_amt),(s.stax_cd1+s.stax_cd2+s.stax_cd3) rate,SUM(s.taxable_amt),"+
				    			" 0.00 cess_amt,if(s.spinv_dt<'2018-04-01' or s.spinv_dt is null,'Y','N') pregst,p.mac_name,s.div_code,s.sinv_no "+
				    			" from invsnd s,partyfst p  where s.fin_year=? and s.sdepo_code=? and s.sdoc_type=? and ifnull(s.sprd_cd,0)<>0 and s.sinv_dt between ? and ?  "+
				    			" and ifnull(s.del_tag,'')<>'D' and p.div_code=4 and p.depo_code=? and p.mac_Code=s.sprt_cd GROUP BY S.DIV_CODE,S.SINV_NO,S.SINV_DT,(S.STAX_cD1+S.STAX_cD2+S.STAX_CD3) "+
				    			" union all "+ 
				    			" select p.gst_no,s.bill_no spinv_no,s.bill_date spinv_dt,if(vbook_cd1=90,concat(s.vou_yr,s.vou_lo,'C',right(s.vou_no,4)),concat(s.vou_yr,s.vou_lo,'D',right(s.vou_no,4))),s.vou_date,"+ 
				    			" if(vbook_cd1=90,'C','D') doctype,'01-Sales Return' reason,concat(p.gststate_code,'-',p.mstate_name) state,SUM(s.net_amt),(s.stax_per+s.ctax_per+s.itax_per) rate,SUM(s.taxable_amt),"+
				    			" 0.00 cess_amt,'N' pregst,p.mac_name,s.div_code,s.vou_no "+ 
				    			" from ledfile s,partyfst p  where s.fin_year=? and s.vdepo_code=? and s.vbook_cd1=? and ifnull(s.taxable_amt,0)<>0 and s.vou_date between ? and ? "+ 
				    			" and ifnull(s.del_tag,'')<>'D' and p.div_code=4 and p.depo_code=? and p.mac_Code=s.vac_code GROUP BY S.DIV_CODE,S.vou_NO,S.VOU_DATE,(S.cgst_amt+S.sgst_amt+S.igst_amt) ";
			    	
				    }
				    String query1="select sum(net_amt) from invsnd where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? and sinv_no=? and sinv_dt=? and ifnull(del_tag,'')<>'D'";
				    
				    st = con.prepareStatement(query);
				    ps1 = con.prepareStatement(query1);

 
				    st.setInt(1, year);
		    	    st.setInt(2, depo);
		    	    st.setInt(3, doctype);
		    	    st.setDate(4, sDate);
		    	    st.setDate(5, eDate); 
		    	    st.setInt(6, depo);
				    st.setInt(7, year);
		    	    st.setInt(8, depo);
		    	    st.setInt(9, doctype);
		    	    st.setDate(10, sDate);
		    	    st.setDate(11, eDate);
		    	    st.setInt(12, depo);
				    st.setInt(13, year);
		    	    st.setInt(14, cmpdepo);
		    	    st.setInt(15, doctype);
		    	    st.setDate(16, sDate);
		    	    st.setDate(17, eDate); 
		    	    st.setInt(18, depo);
				    st.setInt(19, year);
		    	    st.setInt(20, cmpdepo);
		    	    st.setInt(21, wdoc);  // for ledfile 90/95
		    	    st.setDate(22, sDate);
		    	    st.setDate(23, eDate); 
		    	    st.setInt(24, cmpdepo);

		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
				    while(rs.next())
				    {
				    	inv = new SaletaxDto();
				    	
					    ps1.setInt(1, year);
			    	    ps1.setInt(2, rs.getInt(15));
			    	    ps1.setInt(3, depo);
			    	    ps1.setInt(4, doctype);
			    	    ps1.setInt(5, rs.getInt(16));
			    	    ps1.setDate(6, rs.getDate(5));
			    	    rs1=ps1.executeQuery();
			    	    if(rs1.next())
			    	    {
			    	    	inv.setNetamt(rs1.getDouble(1)); //taxable
			    	    }
				    	rs1.close();
				    	inv.setTin(rs.getString(1)); //type 
				    	inv.setSpinv_no(rs.getString(2));
				    	inv.setSpinv_date(rs.getDate(3));
				    	inv.setInv_no(rs.getString(4));
				    	inv.setInv_date(rs.getDate(5));
				    	inv.setType(rs.getString(6));
				    	inv.setReason(rs.getString(7));
				    	inv.setCity(rs.getString(8)); //place of supply
				    	
//				    	inv.setNetamt(rs.getDouble(9)); //taxable
				    	
				    	inv.setCgst_rate(rs.getDouble(10)); //cgst rate
				    	inv.setTaxable1(rs.getDouble(11)); //taxable
				    	inv.setCess_amount(rs.getDouble(12)); //cesst amt
				    	inv.setPregst(rs.getString(13)); //pregst 
				    	inv.setParty_name(rs.getString(14)); //party name 
				    	inv.setDash(0);
				    	data.add(inv);
				    }

			    	
				    rs.close();
				    st.close();
				    ps1.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(rs1 != null){rs1.close();}
			             if(ps1 != null){ps1.close();}

			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstB2BSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }
	
		 
		 
		 
		 
		 public List DocumentList(int year,int depo,Date sdate,Date edate)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SaletaxDto inv=null;
			   String query=null;

			   int salesdepo=BaseClass.loginDt.getCmp_code(); // sales depo code
			   
			   String divname[]={"","[MF]","[TF]","[GE]","","","","","","","[MF2]","","","","","","","","","","[MF3]"};
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();



				query=" select div_code,doc_type,'Sales Invoice ' docname,if(doc_type=40,concat(inv_yr,inv_lo,RIGHT(min(inv_no),5)),concat('TR',inv_yr,inv_lo,right(min(inv_no),3))) sno,if(doc_type=40,concat(inv_yr,inv_lo,RIGHT(max(inv_no),5)),concat('TR',inv_yr,inv_lo,right(max(inv_no),3))) eno,count(*), 0 cancelled "+ 
				 " from invfst where  fin_year=? and div_code in (1,2,3,10,20) and depo_code=? and doc_type =40 "+  
				 " and inv_date between ? and ? and ifnull(del_tag,'')<>'D' group by div_code,doc_type  "+   
				 " union all "+ 
				 " select div_code,doc_type,'Credit Note' docname,concat(inv_yr,inv_lo,'C',RIGHT(min(inv_no),4)) sno,concat(inv_yr,inv_lo,'C',RIGHT(max(inv_no),4)) eno,count(*), 0 cancelled "+ 
				 " from invfst where  fin_year=? and div_code in (1,2,3,10,20) and depo_code=? and doc_type =41  "+  
				 " and inv_date between ? and ? and ifnull(del_tag,'')<>'D'   group by div_code,doc_type  "+ 
				 " union all "+ 
				 " select div_code,doc_type,'Debit Note' docname,concat(inv_yr,inv_lo,'D',RIGHT(min(inv_no),4)) sno,concat(inv_yr,inv_lo,'D',RIGHT(max(inv_no),4)) eno,count(*), 0 cancelled "+ 
				 " from invfst where  fin_year=? and div_code in (1,2,3,10,20) and depo_code=? and doc_type =51 "+   
				 " and inv_date between ? and ? and ifnull(del_tag,'')<>'D'   group by div_code,doc_type  "+ 
				 " union all "+ 
				 " select div_code,Vbook_cd,'Purchase Accounts' docname,concat(vou_lo,lpad(min(vou_no),6,0)) sno,concat(vou_lo,lpad(max(vou_no),6,0)) eno,count(*), 0 cancelled "+ 
				 " from rcpfile where  fin_year=? and div_code =4 and vdepo_code=? and vbook_cd =60  "+  
				 " and vou_date between ? and ? and vamount > 0 and ifnull(del_tag,'')<>'D'   group by div_code,vbook_cd  "+ 
				 " union all "+ 
				 " select l.div_code,l.Vbook_cd1,concat(b.book_nm,' Receipts') docname,concat(left(l.vou_lo,1),'R',right(l.vou_lo,1),right(min(l.vou_no),5)) sno,concat(left(l.vou_lo,1),'R',right(l.vou_lo,1),right(max(l.vou_no),5)) eno,(max(l.vou_no)-min(l.vou_no))+1 cnt, 0 cancelled "+ 
				 " from ledfile l,bookmast b where  l.fin_year=? and l.div_code in (1,4) and l.vdepo_code in(?,?) and l.vbook_cd1 in (20,21,22)  "+  
				 " and l.vou_date between ? and ? and l.vamount > 0 and l.vdbcr='CR' and ifnull(l.del_tag,'')<>'D' " +
				 " and b.div_code=l.div_code and b.depo_Code=vdepo_code and b.book_cd=l.vbook_cd1 and ifnull(b.del_tag,'')<>'D' "+
				 "  group by l.vbook_cd1  "+ 
				 " union all "+ 
				 " select l.div_code,l.Vbook_cd1,concat(b.book_nm,' Receipts') docname,concat(left(l.vou_lo,1),'R',right(l.vou_lo,1),right(min(l.vou_no),5)) sno,concat(left(l.vou_lo,1),'R',right(l.vou_lo,1),right(max(l.vou_no),5)) eno,(max(l.vou_no)-min(l.vou_no))+1 cnt, 0 cancelled "+ 
				 " from ledfile l,bookmast b where  l.fin_year=? and l.div_code in (2,3,10,20) and l.vdepo_code=? and l.vbook_cd1 in (20,21,22)  "+  
				 " and l.vou_date between ? and ? and l.vamount > 0 and l.vdbcr='CR' and ifnull(l.del_tag,'')<>'D' " +
				 " and b.div_code=l.div_code and b.depo_Code=vdepo_code and b.book_cd=l.vbook_cd1 and ifnull(b.del_tag,'')<>'D' "+
				 "  group by div_code,vbook_cd1  "+ 
				 " union all "+ 
				 " select l.div_code,l.Vbook_cd1,concat(b.book_nm,' Payments') docname,concat(l.vou_lo,'A',RIGHT(min(l.vou_no),5))  sno,concat(l.vou_lo,'A',RIGHT(max(l.vou_no),5)) eno,(max(l.vou_no)-min(l.vou_no))+1 cnt , 0 cancelled  "+
				 " from ledfile l,bookmast b where  l.fin_year=? and l.div_code =4 and l.vdepo_code=? and l.vbook_cd1 in (20,21,22) "+   
				 " and l.vou_date between ? and ? and l.vamount > 0 and l.vdbcr='DR' and l.sub_div=1 and ifnull(l.del_tag,'')<>'D' and l.vou_no in "+
				 " (select distinct(vou_no) from ledfile where  fin_year=? and div_code =4 and vdepo_code=? and vbook_cd1 in (20,21,22) "+    
				 " and vou_date between ? and ? and vamount > 0 and vdbcr='DR' and sub_div=1 and ifnull(del_tag,'')<>'D' order by vou_no  ) "+  
				 " and b.div_code=l.div_code and b.depo_Code=vdepo_code and b.book_cd=l.vbook_cd1 and ifnull(b.del_tag,'')<>'D' "+
				 " group by l.div_code,l.vbook_cd1 "+  
				 " union all "+ 
				 " select l.div_code,l.Vbook_cd1,concat(b.book_nm,' Payments [TF]') docname,concat(l.vou_lo,'T',RIGHT(min(l.vou_no),5))  sno,concat(l.vou_lo,'T',RIGHT(max(l.vou_no),5)) eno,(max(l.vou_no)-min(l.vou_no))+1 cnt , 0 cancelled  "+
				 " from ledfile l,bookmast b where  l.fin_year=? and l.div_code =4 and l.vdepo_code=? and l.vbook_cd1 in (20,21,22) "+   
				 " and l.vou_date between ? and ? and l.vamount > 0 and l.vdbcr='DR' and l.sub_div=2 and ifnull(l.del_tag,'')<>'D' "+
				 " and b.div_code=l.div_code and b.depo_Code=vdepo_code and b.book_cd=l.vbook_cd1 and ifnull(b.del_tag,'')<>'D' "+
				 " group by l.div_code,l.vbook_cd1 "+  
				 " union all "+ 
				 " select l.div_code,l.Vbook_cd1,concat(b.book_nm,' Payments [GE]') docname,concat(vou_lo,'G',RIGHT(min(vou_no),5))  sno,concat(vou_lo,'G',RIGHT(max(vou_no),5)) eno,(max(vou_no)-min(vou_no))+1 cnt , 0 cancelled  "+
				 " from ledfile l ,bookmast b where  l.fin_year=? and l.div_code =4 and l.vdepo_code=? and l.vbook_cd1 in (20,21,22) "+   
				 " and l.vou_date between ? and ? and l.vamount > 0 and l.vdbcr='DR' and l.sub_div=3 and ifnull(l.del_tag,'')<>'D' "+
				 " and b.div_code=l.div_code and b.depo_Code=vdepo_code and b.book_cd=l.vbook_cd1 and ifnull(b.del_tag,'')<>'D' "+
				 " group by l.div_code,l.vbook_cd1 "+  
				 " union all "+ 
				 " select l.div_code,l.Vbook_cd1,concat(b.book_nm,' Payments [MF2]') docname,concat(vou_lo,'M',RIGHT(min(vou_no),5))  sno,concat(vou_lo,'M',RIGHT(max(vou_no),5)) eno,(max(vou_no)-min(vou_no))+1 cnt , 0 cancelled  "+
				 " from ledfile l ,bookmast b where  l.fin_year=? and l.div_code =4 and l.vdepo_code=? and l.vbook_cd1 in (20,21,22) "+   
				 " and l.vou_date between ? and ? and l.vamount > 0 and l.vdbcr='DR' and l.sub_div=10 and ifnull(l.del_tag,'')<>'D' "+
				 " and b.div_code=l.div_code and b.depo_Code=vdepo_code and b.book_cd=l.vbook_cd1 and ifnull(b.del_tag,'')<>'D' "+
				 " group by l.div_code,l.vbook_cd1 "+  
				 " union all "+ 
				 " select l.div_code,l.Vbook_cd1,concat(b.book_nm,' Payments [MF3]') docname,concat(vou_lo,'M',RIGHT(min(vou_no),5))  sno,concat(vou_lo,'M',RIGHT(max(vou_no),5)) eno,(max(vou_no)-min(vou_no))+1 cnt , 0 cancelled  "+
				 " from ledfile l ,bookmast b where  l.fin_year=? and l.div_code =4 and l.vdepo_code=? and l.vbook_cd1 in (20,21,22) "+   
				 " and l.vou_date between ? and ? and l.vamount > 0 and l.vdbcr='DR' and l.sub_div=20 and ifnull(l.del_tag,'')<>'D' "+
				 " and b.div_code=l.div_code and b.depo_Code=vdepo_code and b.book_cd=l.vbook_cd1 and ifnull(b.del_tag,'')<>'D' "+
				 " group by l.div_code,l.vbook_cd1 "+  
				 " union all "+ 
				 " select div_code,Vbook_cd1,'JV' docname,concat(vou_lo,'A',right(min(vou_no),5)) sno,concat(vou_lo,'A',right(max(vou_no),5)) eno,count(*), 0 cancelled "+ 
				 " from ledfile where  fin_year=? and div_code in (1,2,3,10,4,20) and vdepo_code IN (?,?) and vbook_cd1 =30   "+ 
				 " and vou_date between  ? and ? and vamount > 0  and vdbcr='CR' and ifnull(del_tag,'')<>'D'   group by div_code,vbook_cd1  "+ 
				 " union all "+ 
				 " select div_code,Vbook_cd1,'JVI' docname,concat('JVI',RIGHT(min(vou_no),5))  sno,concat('JVI',RIGHT(max(vou_no),5)) eno,(max(vou_no)-min(vou_no))+1 cnt , 0 cancelled "+  
				 " from ledfile where  fin_year=? and div_code in (1,2,3,10,4,20) and vdepo_code IN(?,?) and vbook_cd1=98   "+
				 " and vou_date between ? and ? and vamount > 0 and ifnull(del_tag,'')<>'D' and vou_no in "+
				 " (select distinct(vou_no) from ledfile where  fin_year=? and DIV_CODE in (1,2,3,10,4,20) and vdepo_code IN (?,?) and vbook_cd1 =98 "+    
				 " and vou_date between ? and ? and vamount > 0 and ifnull(del_tag,'')<>'D' order by vou_no  ) "+
				 " group by div_code,vbook_cd1 ";  

				
				    st = con.prepareStatement(query);

				    st.setInt(1, year);
		    	    st.setInt(2, salesdepo);
		    	    st.setDate(3, sDate);
		    	    st.setDate(4, eDate);
		    	    
				    st.setInt(5, year);
		    	    st.setInt(6, salesdepo);
		    	    st.setDate(7, sDate);
		    	    st.setDate(8, eDate);

				    st.setInt(9, year);
		    	    st.setInt(10, salesdepo);
		    	    st.setDate(11, sDate);
		    	    st.setDate(12, eDate);

				    st.setInt(13, year);
		    	    st.setInt(14, depo);
		    	    st.setDate(15, sDate);
		    	    st.setDate(16, eDate);

				    st.setInt(17, year);
		    	    st.setInt(18, depo);
		    	    st.setInt(19, salesdepo);
		    	    st.setDate(20, sDate);
		    	    st.setDate(21, eDate);

				    st.setInt(22, year);
		    	    st.setInt(23, salesdepo);
		    	    st.setDate(24, sDate);
		    	    st.setDate(25, eDate);

		    	    st.setInt(26, year);
		    	    st.setInt(27, depo);
		    	    st.setDate(28, sDate);
		    	    st.setDate(29, eDate);

		    	    st.setInt(30, year);
		    	    st.setInt(31, depo);
		    	    st.setDate(32, sDate);
		    	    st.setDate(33, eDate);

		    	    st.setInt(34, year);
		    	    st.setInt(35, depo);
		    	    st.setDate(36, sDate);
		    	    st.setDate(37, eDate);

		    	    st.setInt(38, year);
		    	    st.setInt(39, depo);
		    	    st.setDate(40, sDate);
		    	    st.setDate(41, eDate);
		    	    st.setInt(42, year);
		    	    st.setInt(43, depo);
		    	    st.setDate(44, sDate);
		    	    st.setDate(45, eDate);
		    	    st.setInt(46, year);
		    	    st.setInt(47, depo);
		    	    st.setDate(48, sDate);
		    	    st.setDate(49, eDate);
		    	    
		    	    st.setInt(50, year);
		    	    st.setInt(51, salesdepo);
		    	    st.setInt(52, depo);
		    	    st.setDate(53, sDate);
		    	    st.setDate(54, eDate);
		    	    st.setInt(55, year);
		    	    st.setInt(56, salesdepo);
		    	    st.setInt(57, depo);
		    	    st.setDate(58, sDate);
		    	    st.setDate(59, eDate);
		    	    st.setInt(60, year);
		    	    st.setInt(61, salesdepo);
		    	    st.setInt(62, depo);
		    	    st.setDate(63, sDate);
		    	    st.setDate(64, eDate);

		    	    
		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
				    while(rs.next())
				    {

				    	inv = new SaletaxDto();
				    	
				    	inv.setPname(rs.getString(3)+" "+divname[rs.getInt(1)]); //description 
				    	inv.setPack(rs.getString(4)); // sno
				    	inv.setCity(rs.getString(5)); // eno
				    	inv.setSqty(rs.getInt(6)); // no of inovices
				    	inv.setDash(0);
				    	data.add(inv);
				    }

			    	
				    rs.close();
				    st.close();
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstDOCSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }


		 public List gstrITEMWISE(int year,int depo,Date sdate,Date edate)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			   SaletaxDto inv=null;
			   String query=null;
			   int cmpdepo=BaseClass.loginDt.getCmp_code();
			   
			   String [] sale_desc ={"","Aristo Medicine","Outside Food Medicine","Outside Medicine","","","","","","Aristo Food (LL)","Cosmetic"};

			   String [] div_name ={"","MF","TF","GENETICA","ACCOUNTS","MF SAMPLE","TF SAMPLE","GEN SAMPLE","CO MKT SAMPLE","CO MKT","MF2","MF2 SAMPLE ","","","","","","","","","MF3","MF3 SAMPLE"};

			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   try{
				    con=ConnectionFactory.getConnection();


				    
				    if(sdate.before(sdf.parse("01/10/2018")))  // for 30/06/2017
				    {				    
				    	query=" select a.Doc_type,a.Mnth_code,a.div_code,a.sprd_cd,a.sale_type,a.Qty,a.Free_qty, "+
				    			" a.Taxable_amt,a.SGST,a.CGST,a.IGST,a.Net,a.sdepo_code,a.party_code ,a.mdiv_cd,a.Transfer_to,a.Inward_From,p.pname,p.pack "+
				    			" from "+ 
				    			"(select 'SALES' as Doc_type, Mnth_code,div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,'' party_code,'' mdiv_cd,'' Transfer_to,'' inward_from "+
				    			" from invsnd "+
				    			" where fin_year=? and sdepo_code=? and div_code in (1,2,3,10,20) and sdoc_type in (39,40) and IFNULL(del_tag,'N')<>'D' "+
				    			" and sinv_dt between ? and ? "+
				    			" group by div_code,sprd_cd,sale_type "+
				    			" Union all "+
				    			" select 'INWARD' as Doc_type,Mnth_code,i.div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,sprt_cd as party_code ,p.mdiv_cd,'' as Transfer_to,concat('INWARD FROM - ',p.mcity) as Inward_From "+
				    			" from invsnd i  ,partyfst p "+
				    			" where fin_year=? and sdepo_code=?  and i.div_code in (1,2,3,10,20) and sdoc_type in (60,61,62,72,73) and IFNULL(i.del_tag,'N')<>'D' "+
				    			" and sinv_dt between ? and ?  "+
				    			" and i.sdepo_code=p.depo_code and i.div_code=p.div_code and i.sprt_cd=p.mac_code and ifnull(p.del_tag,'N')<>'D' "+
				    			" group by i.div_code,sprd_cd,sale_type,sprt_cd "+
				    			" Union all "+
				    			" select 'TRANSFER' as Doc_type,Mnth_code,i.div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,sprt_cd as party_code,p.mdiv_cd,'' as Transfer_to,concat('TRANSFER TO - ',p.mcity ) as Inward_From "+
				    			" from invsnd i,partyfst p  "+
				    			" where fin_year=? and sdepo_code=?  and i.div_code in (1,2,3,10,20) and i.sdoc_type=67 and IFNULL(i.del_tag,'N')<>'D' "+
				    			" and sinv_dt between ? and ?  "+
				    			" and i.sdepo_code=p.depo_code and i.div_code=p.div_code and i.sprt_cd=p.mac_code and ifnull(p.del_tag,'N')<>'D' "+
				    			" group by i.div_code,sprd_cd,sale_type,sprt_cd "+
				    			" Union all "+
				    			" select if(spinv_dt<'2017-07-01' or spinv_dt is null,'CREDIT NOTE PRE-GST','CREDIT NOTE POST-GST') as Doc_type,Mnth_code,div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc" + 
				    			" ,'','','' as GST_type,if(spinv_dt<'2017-07-01' or spinv_dt is null,'PRE-GST','POST-GST') as Inward_From "+
				    			" from invsnd "+
				    			" where fin_year=? and sdepo_code=?  and div_code in (1,2,3,10,20) and sdoc_type=41 and IFNULL(del_tag,'N')<>'D' "+
				    			" and sinv_dt  between ? and ?  "+
				    			" group by doc_type,div_code,sprd_cd,sale_type,SPINV_DT "+
				    			" Union all "+
				    			" select 'DEBIT NOTE' as Doc_type,Mnth_code,div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,'' party_code,'' mdiv_cd,'' Transfer_to,'' inward_from "+
				    			" from invsnd "+
				    			" where fin_year=? and sdepo_code=?  and div_code in (1,2,3,10,20) and sdoc_type=51 and IFNULL(del_tag,'N')<>'D' "+
				    			" and sinv_dt  between ? and ?  "+
				    			" group by div_code,sprd_cd,sale_type "+
				    			" Union all "+
				    			" select 'SAMPLE SELF INVOICE' as Doc_type,Mnth_code,div_code,sprd_cd,0 sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,'' party_code,'' mdiv_cd,'' Transfer_to,'' inward_from "+
				    			" from invsnd "+
				    			" where fin_year=? and sdepo_code=?  and div_code in (5,6,7,11,21) and sdoc_type=68 and IFNULL(del_tag,'N')<>'D' "+
				    			" and sinv_dt  between ? and ?  "+
				    			" group by div_code,sprd_cd "+
				    			" UNION ALL "+
				    			" select 'Purchase Voucher' as doc_type,Mnth_code,div_code,0 as sprd_cd,0 as sale_type,0 as qty,0 as free_qty "+
				    			" ,sum(taxable_amt) as taxable_amt,sum(sgst_amt) as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as NET,vdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack ,'' as saletype_desc "+
				    			" ,'' party_code,'' mdiv_cd,'' Transfer_to,'' inward_from "+
				    			" from LEDFILE where fin_year=? and vdepo_code=?  and div_code=4 and vou_date   between ? and ?  "+
				    			" AND IFNULL(varea_cd,0)=1 "+
				    			" and vbook_cd1 in (60,61) and IFNULL(del_tag,'N')<>'D' ) a left join prd p " +
				    			" on p.div_code=a.div_Code and p.pcode=a.sprd_cd  ";

				    }
				    else
				    {
				    	query=" select a.Doc_type,a.Mnth_code,a.div_code,a.sprd_cd,a.sale_type,a.Qty,a.Free_qty, "+
				    			" a.Taxable_amt,a.SGST,a.CGST,a.IGST,a.Net,a.sdepo_code,a.party_code ,a.mdiv_cd,a.Transfer_to,a.Inward_From,p.pname,p.pack "+
				    			" from "+ 
				    			"(select 'SALES' as Doc_type, Mnth_code,div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,'' party_code,'' mdiv_cd,'' Transfer_to,'' inward_from "+
				    			" from invsnd "+
				    			" where fin_year=? and sdepo_code=? and div_code in (1,2,3,10,20) and sdoc_type in (39,40) and IFNULL(del_tag,'N')<>'D' "+
				    			" and sinv_dt between ? and ? "+
				    			" group by div_code,sprd_cd,sale_type "+
				    			" Union all "+
				    			" select 'INWARD' as Doc_type,Mnth_code,i.div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,sprt_cd as party_code ,p.mdiv_cd,'' as Transfer_to,concat('INWARD FROM - ',p.mcity) as Inward_From "+
				    			" from invsnd i  ,partyfst p "+
				    			" where fin_year=? and sdepo_code=?  and i.div_code in (1,2,3,10,20) and sdoc_type in (60,61,62,72,73) and IFNULL(i.del_tag,'N')<>'D' "+
				    			" and sinv_dt between ? and ?  "+
				    			" and i.sdepo_code=p.depo_code and i.div_code=p.div_code and i.sprt_cd=p.mac_code and ifnull(p.del_tag,'N')<>'D' "+
				    			" group by i.div_code,sprd_cd,sale_type,sprt_cd "+
				    			" Union all "+
				    			" select 'TRANSFER' as Doc_type,Mnth_code,i.div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,sprt_cd as party_code,p.mdiv_cd,'' as Transfer_to,concat('TRANSFER TO - ',p.mcity ) as Inward_From "+
				    			" from invsnd i,partyfst p  "+
				    			" where fin_year=? and sdepo_code=?  and i.div_code in (1,2,3,10,20) and i.sdoc_type=67 and IFNULL(i.del_tag,'N')<>'D' "+
				    			" and sinv_dt between ? and ?  "+
				    			" and i.sdepo_code=p.depo_code and i.div_code=p.div_code and i.sprt_cd=p.mac_code and ifnull(p.del_tag,'N')<>'D' "+
				    			" group by i.div_code,sprd_cd,sale_type,sprt_cd "+
				    			" Union all "+
				    			" select if(spinv_dt<'2018-04-01' or spinv_dt is null,'CREDIT NOTE PRE-GST','CREDIT NOTE POST-GST') as Doc_type,Mnth_code,div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc" + 
				    			" ,'','','' as GST_type,if(spinv_dt<'2018-04-01' or spinv_dt is null,'PRE-GST','POST-GST') as Inward_From "+
				    			" from invsnd "+
				    			" where fin_year=? and sdepo_code=?  and div_code in (1,2,3,10,20) and sdoc_type=41 and IFNULL(del_tag,'N')<>'D' "+
				    			" and sinv_dt  between ? and ?  "+
				    			" group by doc_type,div_code,sprd_cd,sale_type,SPINV_DT "+
				    			" Union all "+
				    			" select 'DEBIT NOTE' as Doc_type,Mnth_code,div_code,sprd_cd,sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,'' party_code,'' mdiv_cd,'' Transfer_to,'' inward_from "+
				    			" from invsnd "+
				    			" where fin_year=? and sdepo_code=?  and div_code in (1,2,3,10,20) and sdoc_type=51 and IFNULL(del_tag,'N')<>'D' "+
				    			" and sinv_dt  between ? and ?  "+
				    			" group by div_code,sprd_cd,sale_type "+
				    			" Union all "+
				    			" select 'SAMPLE SELF INVOICE' as Doc_type,Mnth_code,div_code,sprd_cd,0 sale_type,sum(sqty) as Qty,sum(sfree_qty) as Free_qty, "+
				    			" sum(taxable_amt) as Taxable_amt,sum(sgst_amt)as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as Net,sdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack,'' as saletype_desc "+
				    			" ,'' party_code,'' mdiv_cd,'' Transfer_to,'' inward_from "+
				    			" from invsnd "+
				    			" where fin_year=? and sdepo_code=?  and div_code in (5,6,7,11,21) and sdoc_type=68 and IFNULL(del_tag,'N')<>'D' "+
				    			" and sinv_dt  between ? and ?  "+
				    			" group by div_code,sprd_cd "+
				    			" UNION ALL "+
				    			" select 'Purchase Voucher' as doc_type,Mnth_code,div_code,0 as sprd_cd,0 as sale_type,0 as qty,0 as free_qty "+
				    			" ,sum(taxable_amt) as taxable_amt,sum(sgst_amt) as SGST,sum(cgst_amt) as CGST,sum(igst_amt) as IGST,sum(net_amt) as NET,vdepo_code,'' as branch_name,'' as division,'' as pname,'' as pack ,'' as saletype_desc "+
				    			" ,'' party_code,'' mdiv_cd,'' Transfer_to,'' inward_from "+
				    			" from LEDFILE where fin_year=? and vdepo_code=?  and div_code=4 and vou_date   between ? and ?  "+
				    			" AND IFNULL(varea_cd,0)=1 "+
				    			" and vbook_cd1 in (60,61) and IFNULL(del_tag,'N')<>'D' ) a left join prd p " +
				    			" on p.div_code=a.div_Code and p.pcode=a.sprd_cd  ";
				    	
				    }
									    
				    st = con.prepareStatement(query);


				    st.setInt(1, year);
		    	    st.setInt(2, depo);
		    	    st.setDate(3, sDate);
		    	    st.setDate(4, eDate); 
				    st.setInt(5, year);
		    	    st.setInt(6, depo);
		    	    st.setDate(7, sDate);
		    	    st.setDate(8, eDate); 
				    st.setInt(9, year);
		    	    st.setInt(10, depo);
		    	    st.setDate(11, sDate);
		    	    st.setDate(12, eDate); 
				    st.setInt(13, year);
		    	    st.setInt(14, depo);
		    	    st.setDate(15, sDate);
		    	    st.setDate(16, eDate); 
				    st.setInt(17, year);
		    	    st.setInt(18, depo);
		    	    st.setDate(19, sDate);
		    	    st.setDate(20, eDate); 
				    st.setInt(21, year);
		    	    st.setInt(22, depo);
		    	    st.setDate(23, sDate);
		    	    st.setDate(24, eDate); 
				    st.setInt(25, year);
		    	    st.setInt(26, cmpdepo);
		    	    st.setDate(27, sDate);
		    	    st.setDate(28, eDate); 

		    	    rs=st.executeQuery();
	                
		    	    data = new ArrayList();
		    	    int sno=0;
		    	    boolean first=true;
		    	    String doc_type="";
		    	    double taxable=0.00;
		    	    double sgst=0.00;
		    	    double cgst=0.00;
		    	    double igst=0.00;
		    	    double net=0.00;
		    	    int mnthcode=0;
				    while(rs.next())
				    {
				    	if(first)
				    	{
				    		doc_type=rs.getString(1);
				    		mnthcode=rs.getInt(2);
				    		sno++;
				    		first=false;
				    	}
				    	
				    	if(!rs.getString(1).equalsIgnoreCase(doc_type))
				    	{
				    		sno++;
					    	inv = new SaletaxDto();
					    	inv.setSno(sno);
					    	inv.setMnth_code(mnthcode);
					    	inv.setDoc_type(doc_type+" - Total");
					    	inv.setDivname("");
					    	inv.setSale_desc("");
					    	inv.setPname("");
					    	inv.setPack("");
					    	inv.setSqty(0);
					    	inv.setSfree_qty(0);
					    	inv.setTaxable1(taxable); //taxable
					    	inv.setSgst_amount(sgst); //sgst 
					    	inv.setCgst_amount(cgst); //cgst 
					    	inv.setIgst_amount(igst); //igst 
					    	inv.setNetamt(net); //net 
					    	inv.setRemark("");
					    	inv.setDash(6);
					    	data.add(inv);
					    	sno++;
					    	doc_type=rs.getString(1);
				    	    taxable=0.00;
				    	    sgst=0.00;
				    	    cgst=0.00;
				    	    igst=0.00;
				    	    net=0.00;

				    	
				    	}
				    	inv = new SaletaxDto();
				    	inv.setSno(sno);
				    	inv.setMnth_code(mnthcode);
				    	inv.setDoc_type(doc_type);
				    	inv.setDivname(div_name[(rs.getInt(3))]);
				    	inv.setSale_desc(sale_desc[(rs.getInt(5))]);
				    	inv.setPname(rs.getString(18));
				    	inv.setPack(rs.getString(19));
				    	inv.setSqty(rs.getInt(6));
				    	inv.setSfree_qty(rs.getInt(7));
				    	inv.setTaxable1(rs.getDouble(8)); //taxable
				    	inv.setSgst_amount(rs.getDouble(9)); //sgst 
				    	inv.setCgst_amount(rs.getDouble(10)); //cgst 
				    	inv.setIgst_amount(rs.getDouble(11)); //igst 
				    	inv.setNetamt(rs.getDouble(12)); //net 
				    	inv.setRemark(rs.getString(17));
				    	inv.setDash(0);
				    	
				    	taxable+=rs.getDouble(8);
				    	sgst+=rs.getDouble(9);
				    	cgst+=rs.getDouble(10);
				    	igst+=rs.getDouble(11);
				    	net+=rs.getDouble(12);

				    	data.add(inv);
				    	
				    }

		    		sno++;
			    	inv = new SaletaxDto();
			    	inv.setSno(sno);
			    	inv.setMnth_code(mnthcode);
			    	inv.setDoc_type(doc_type+" - Total");
			    	inv.setDivname("");
			    	inv.setSale_desc("");
			    	inv.setPname("");
			    	inv.setPack("");
			    	inv.setSqty(0);
			    	inv.setSfree_qty(0);
			    	inv.setTaxable1(taxable); //taxable
			    	inv.setSgst_amount(sgst); //sgst 
			    	inv.setCgst_amount(cgst); //cgst 
			    	inv.setIgst_amount(igst); //igst 
			    	inv.setNetamt(net); //net 
			    	inv.setRemark("");
			    	inv.setDash(6);
			    	data.add(inv);

				    rs.close();
				    st.close();
				    
				   }
				catch(Exception ee){System.out.println("error "+ee);
				ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}

			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in gstB2BSaleTaxDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }
		 
		 public List salRegPrint(String smon,int year,int depo,int div,Date sdate,Date edate,ArrayList partyList,int optn,int doc_type)
		   {
			  
			   List data =null;
			   Connection con=null;
			   PreparedStatement st =null;
			   ResultSet rs= null; 
			   InvFstDto inv=null;
			   String query=null;
			   
			   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
			   java.sql.Date eDate = new java.sql.Date(edate.getTime());
			   StringBuffer pcode = new StringBuffer("f.sprt_cd in (");
			   
			   try{
				    con=ConnectionFactory.getConnection();
				   
				     
			      query=" select '' BILL_NO,'' BILL_DATE,'' mac_name,'' ,left(p.gst_no,15),	"+
			    	  " round(sum(f.taxable_amt),2),round(sum(f.sgst_amt),2),round(sum(f.cgst_amt),2)," +
			    	  " round(sum(f.igst_amt),2),round(sum(f.net_amt)), "+
				      " f.sprt_cd,f.stax_cd1,f.stax_cd2,f.stax_cd3,1 dash "+
				      " from invsnd f , partyfst p  where f.fin_year=? and f.div_code=? and " +
				      " f.sdepo_code=? and f.sdoc_type=? and f.stax_cd3=0 "+
				      " and  f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D' " +
				      " and (p.gst_no is null or p.gst_no='' or length(p.gst_no)<15  ) "+
				      " and  f.sprt_cd=p.mac_code and p.div_code=?  and p.depo_code=?  " +
				      " group by mnth_code,(f.stax_cd1+f.stax_cd2) "+ 
				      " union all "+
				      " select f.sinv_no BILL_NO,f.sinv_dt BILL_DATE,p.mac_name,p.mcity," +
				      " left(p.gst_no,15),"+	
				      " round(sum(f.taxable_amt),2),round(sum(f.sgst_amt),2)," +
				      " round(sum(f.cgst_amt),2),round(sum(f.igst_amt),2),round(sum(f.net_amt)),"+
				      " f.sprt_cd,f.stax_cd1,f.stax_cd2,f.stax_cd3,2 dash "+
				      " from invsnd f , partyfst p  where f.fin_year=? and f.div_code=? and f.sdepo_code=? " +
				      " and f.sdoc_type=? "+
				      " and  f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D'" +
				      "  and length(p.gst_no)=15   and f.stax_cd3=0 "+
				      " and  f.sprt_cd=p.mac_code and p.div_code=?  and p.depo_code=?  " +
				      " group by f.sinv_dt,f.sinv_no,(f.stax_cd1+f.stax_cd2) "+  
				      " union all "+
				      " select f.sinv_no BILL_NO,f.sinv_dt BILL_DATE,p.mac_name," +
				      " p.mcity,left(p.gst_no,15),"+	
				      " round(sum(f.taxable_amt),2),round(sum(f.sgst_amt),2)," +
				      " round(sum(f.cgst_amt),2),round(sum(f.igst_amt),2),round(sum(f.net_amt)),"+
				      " f.sprt_cd,f.stax_cd1,f.stax_cd2,f.stax_cd3,3 dash "+
				      " from invsnd f , partyfst p  where f.fin_year=? and f.div_code=? and " +
				      " f.sdepo_code=? and f.sdoc_type=? "+ 
				      " and  f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D'" +
				      "  and length(p.gst_no)=15   and f.stax_cd3>0 "+
				      " and  f.sprt_cd=p.mac_code and p.div_code=?  and p.depo_code=?" +
				      "  group by f.sinv_dt,f.sinv_no,f.stax_cd3 "+  
				      " union all "+
				      " select f.sinv_no BILL_NO,f.sinv_dt BILL_DATE,p.mac_name,p.mcity," +
				      " left(p.gst_no,15),"+	
				      " round(sum(f.taxable_amt),2),round(sum(f.sgst_amt),2)," +
				      " round(sum(f.cgst_amt),2),round(sum(f.igst_amt),2),round(sum(f.net_amt)),"+
				      " f.sprt_cd,f.stax_cd1,f.stax_cd2,f.stax_cd3,4 dash "+
				      " from invsnd f , partyfst p  where f.fin_year=? and f.div_code=? and " +
				      " f.sdepo_code=? and f.sdoc_type=? "+ 
				      " and  f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D'  " +
				      " and (p.gst_no is null or p.gst_no='' or length(p.gst_no)<15  )" +
				      " and f.stax_cd3>0 "+
				      " and  f.sprt_cd=p.mac_code and p.div_code=?  and p.depo_code=?  " +
				      " group by f.sinv_dt,f.sinv_no,f.stax_cd3 " ;

			      
				      

/*				      query="select if(f.sdoc_type=60,ifnull(f.spinv_no,''),f.sinv_no),if(f.sdoc_type=60,f.spinv_dt,f.sinv_dt),p.mac_name,p.mcity,left(p.gst_no,15),"	+
		    		  "round(sum(f.taxable_amt),2),round(sum(f.sgst_amt),2),round(sum(f.cgst_amt),2),round(sum(f.igst_amt),2),round(sum(f.net_amt)),f.sprt_cd,f.stax_cd1,f.stax_cd2,f.stax_cd3 "+
		    		  "from invsnd f , partyfst p  where f.div_code=? and f.sdepo_code=? and f.sdoc_type=? "+
		    		  "and  f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D' "+  
		    		  " and "+pcode+"  f.sprt_cd=p.mac_code and p.div_code=?  and p.depo_code=?  group by f.sinv_dt,f.sinv_no,(f.stax_cd1+f.stax_cd2+f.stax_cd3)  ";
*/
				      st = con.prepareStatement(query);
				      data = new ArrayList();
				     
			    	 
				        st.setInt(1, year);
			    		st.setInt(2, div);
			    		st.setInt(3, depo);
			    		st.setInt(4, doc_type);
			    		st.setDate(5, sDate);
			    		st.setDate(6, eDate);
			    		st.setInt(7, div);
			    		st.setInt(8, depo);
				        st.setInt(9, year);
			    		st.setInt(10, div);
			    		st.setInt(11, depo);
			    		st.setInt(12, doc_type);
			    		st.setDate(13, sDate);
			    		st.setDate(14, eDate);
			    		st.setInt(15, div);
			    		st.setInt(16, depo);
				        st.setInt(17, year);
			    		st.setInt(18, div);
			    		st.setInt(19, depo);
			    		st.setInt(20, doc_type);
			    		st.setDate(21, sDate);
			    		st.setDate(22, eDate);
			    		st.setInt(23, div);
			    		st.setInt(24, depo);
				        st.setInt(25, year);
			    		st.setInt(26, div);
			    		st.setInt(27, depo);
			    		st.setInt(28, doc_type);
			    		st.setDate(29, sDate);
			    		st.setDate(30, eDate);
			    		st.setInt(31, div);
			    		st.setInt(32, depo);
				    	 
				    	rs=st.executeQuery();
				    	while(rs.next())
				    	{
				    		inv = new InvFstDto();
				    		if(rs.getInt(15)>1)
				    		{
				    			inv.setInv_type(rs.getString(1));
				    			inv.setInv_date(rs.getDate(2));
				    			inv.setParty_name(rs.getString(3));
				    			inv.setCity(rs.getString(4));
				    			inv.setTin_no(rs.getString(5));
				    		}
				    		inv.setGross5(rs.getDouble(6));
				    		inv.setVat5(rs.getDouble(7));
				    		inv.setVat14(rs.getDouble(8));
				    		inv.setVat15(rs.getDouble(9));
				    		inv.setOth_adj(rs.getDouble(10)-(rs.getDouble(6)+rs.getDouble(7)+rs.getDouble(8)+rs.getDouble(9)));
				    		inv.setBill_amt(rs.getDouble(10));
				    		inv.setLtax1_per(rs.getDouble(12));
				    		inv.setLtax2_per(rs.getDouble(13));
				    		inv.setLtax3_per(rs.getDouble(14));
				    		inv.setDash(rs.getInt(15));
				    		data.add(inv);
				    		
				    	}

				           
						    rs.close();
						    st.close();
				   }
				catch(Exception ee){
					ee.printStackTrace();
				}		   
			  
				finally {
					try {
						    
			             if(rs != null){rs.close();}
			             if(st != null){st.close();}
			             if(con != null){con.close();}
					} catch (SQLException ee) {
						System.out.println("-------------Exception in SalRegDAO.Connection.close "+ee);
					  }
				}
				
				return data;
		   }

	 
		 
}
