package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.coldstorage.dto.InvFstDto;
import com.coldstorage.dto.InvViewDto;
import com.coldstorage.view.BaseClass;
 

public class SalRegDAO 
{
	 
	 
	 public List salRegPrint(String smon,int year,int depo,int div,Date sdate,Date edate,ArrayList partyList,int optn,int doc_type)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   PreparedStatement ps1 =null;
		   ResultSet rs1= null; 

		   InvViewDto ivdt=null;
		   String query=null;
		   int xdoc=doc_type;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   StringBuffer pcode = new StringBuffer("f.sprt_cd in (");
		   StringBuffer pcode1 = new StringBuffer("f.vac_code in (");
		   
		   try{
			    con=ConnectionFactory.getConnection();
			   
			      int size=partyList.size();
			      int p=0;
			      for (p=0;p<size-1;p++)
			      {
			    	  pcode.append(partyList.get(p)+",");
			    	  pcode1.append(partyList.get(p)+",");
			      }
			      
			      pcode.append(partyList.get(p)+") and ");
			      pcode1.append(partyList.get(p)+") and ");
			      
			      System.out.println(year+" PCODE "+pcode);
			      System.out.println("doctype "+doc_type+" PCODE1 "+pcode1);

			      if(doc_type==700)
			      {
			      query=" select f.sinv_no inv_no ,f.sinv_dt,p.account_no,p.mac_name_hindi,p.mcity_hindi," +
		    			  " f.mark_no,f.floor_no,f.block_no,f.category,f.sqty sqty,"+
		    			  " concat(g.gp_name_hindi,' ',p1.pname_hindi) pname_hindi,f.srate_net,f.weight weight," +
		    			  " f.net_amt net_amt ,0 rcp_amt,f.sprt_cd party_Code,f.pack pack," +
		    			  " ifnull(f.vehicle_no,'') vehicle,ifnull(f.driver_name,'') drivername,ifnull(f.driver_mobile,'') drivermobile" +
		    			  " ,ifnull(f.issue_qty,0) iqty,ifnull(f.issue_weight,0) iweight,f.spinv_no chl_no," +
		    			  " f.spinv_dt chl_date,p.mobile,ifnull(f.transfer_no,0),ifnull(f.from_account,''),ifnull(f.roundoff,0),sum(ifnull(f.samnt,0)),ifnull(remark,'') "+
		    			  " from invsnd f , accountmaster p,prd p1,grpmast g  where  f.sdepo_code=? and f.div_code=? and f.sdoc_type=? "+
		    			  " and  f.fin_year=? and f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D' and ifnull(f.roundoff,0)<>0 "+  
		    			  " and "+pcode+"   f.sprt_cd=p.account_no  and p.depo_code=? and p.div_code=f.div_code " +
		    			  " and p1.div_code=f.div_Code and p1.pcode=f.sprd_cd "+ 
		    			  " and g.div_code=f.div_code and g.gp_Code=f.spd_gp group by sinv_no order by f.serialno"; 

			      }
			      else if(doc_type==10)
			      {
			      query=" select f.vou_no,f.vou_date,p.account_no,p.mac_name_hindi,p.mcity_hindi," +
		    			  " ifnull(f.remark,'') mark_no,if (f.vbook_Cd=10,'CASH','BANK') floor_no,'' block_no,'' category,0 sqty,"+
		    			  " '' pname_hindi,0 srate_net,0 weight," +
		    			  " if(vbook_cd=60,f.vamount,0) rent_amt ,if((vbook_cd=10 or vbook_cd=20),f.vamount,0) rcp_amt,f.vac_code party_Code,'' pack," +
		    			  " '' vehicle,'' drivername,'' drivermobile" +
		    			  " ,0 iqty,0 iweight,'' chl_no," +
		    			  " null chl_date,p.mobile,0 transfer_no,0 from_account,0 roundoff,0 samnt,'' remark "+
		    			  " from ledfile f , accountmaster p  where  f.vdepo_code=? and f.div_code=? and f.vbook_cd in (?,20) "+
		    			  " and  f.fin_year=?  and  ifnull(f.del_tag,'')<>'D' "+  
		    			  "  and "+pcode1+"   f.vac_code=p.account_no  and p.depo_code=? and p.div_code=f.div_code  "+ 
		    			  "  group by vou_no order by f.vou_no"; 

			      }
			      else if(doc_type==40)
			      {
			    	  query=" select f.sinv_no inv_no ,f.sinv_dt,p.account_no,p.mac_name_hindi,p.mcity_hindi," +
			    			  " f.mark_no,f.floor_no,f.block_no,f.category,f.sqty sqty,"+
			    			  " concat(g.gp_name_hindi,' ',p1.pname_hindi) pname_hindi,f.srate_net,f.weight weight," +
			    			  " f.net_amt net_amt ,0 rcp_amt,f.sprt_cd party_Code,f.pack pack," +
			    			  " ifnull(f.vehicle_no,'') vehicle,ifnull(f.driver_name,'') drivername,ifnull(f.driver_mobile,'') drivermobile" +
			    			  " ,ifnull(f.issue_qty,0) iqty,ifnull(f.issue_weight,0) iweight,f.spinv_no chl_no," +
			    			  " f.spinv_dt chl_date,p.mobile,ifnull(f.transfer_no,0),ifnull(f.from_account,''),ifnull(f.roundoff,0),ifnull(f.samnt,0),ifnull(remark,'') "+
			    			  " from invsnd f , accountmaster p,prd p1,grpmast g  where  f.sdepo_code=? and f.div_code=? and f.sdoc_type=? "+
			    			  " and  f.fin_year=? and f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D' "+  
			    			  "  and "+pcode+"   f.sprt_cd=p.account_no  and p.depo_code=? and p.div_code=f.div_code " +
			    			  " and p1.div_code=f.div_code and p1.pcode=f.sprd_cd "+ 
			    			  " and g.div_code=f.div_code and g.gp_Code=f.spd_gp order by  cast(f.spinv_no as signed),F.SERIALNO"; 
			      }
			      else
			      {
			    	  query=" select f.sinv_no inv_no ,f.sinv_dt,p.account_no,p.mac_name_hindi,p.mcity_hindi," +
			    			  " f.mark_no,f.floor_no,f.block_no,f.category,f.sqty sqty,"+
			    			  " concat(g.gp_name_hindi,' ',p1.pname_hindi) pname_hindi,f.srate_net,f.weight weight," +
			    			  " f.net_amt net_amt ,0 rcp_amt,f.sprt_cd party_Code,f.pack pack," +
			    			  " ifnull(f.vehicle_no,'') vehicle,ifnull(f.driver_name,'') drivername,ifnull(f.driver_mobile,'') drivermobile" +
			    			  " ,ifnull(f.issue_qty,0) iqty,ifnull(f.issue_weight,0) iweight,f.spinv_no chl_no," +
			    			  " f.spinv_dt chl_date,p.mobile,ifnull(f.transfer_no,0),ifnull(f.from_account,''),ifnull(f.roundoff,0),ifnull(f.samnt,0),ifnull(remark,'') "+
			    			  " from invsnd f , accountmaster p,prd p1,grpmast g  where  f.sdepo_code=? and f.div_code=? and f.sdoc_type=? "+
			    			  " and  f.fin_year=? and f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D' "+  
			    			  "  and "+pcode+"   f.sprt_cd=p.account_no   and p.depo_code=? and p.div_code=f.div_code and " +
			    			  " p1.div_code=f.div_code and p1.pcode=f.sprd_cd "+ 
			    			  " and g.div_code=f.div_code and g.gp_Code=f.spd_gp order by f.SINV_NO,F.SERIALNO"; 
			      }
			      
			      String query1="select sum(net_amt*-1) from invsnd where div_code=? and fin_year=? and spinv_no=? and sprt_cd=? and sdoc_type=41";
			      
			      ps1 = con.prepareStatement(query1);

			      
			      st = con.prepareStatement(query);
			      data = new ArrayList();
			     
			      	if(doc_type==700)
			      	{
			      		doc_type=60;
			      	}		
		   
			      	if(doc_type==10)
			      	{
			      		st.setInt(1, depo);
			      		st.setInt(2, div);
			      		st.setInt(3, doc_type);
			      		st.setInt(4, year);
			      		st.setInt(5, depo);
			      		
			      	}
			      	else
			      	{
			      		st.setInt(1, depo);
			      		st.setInt(2, div);
			      		st.setInt(3, doc_type);
			      		st.setInt(4, year);
			      		st.setDate(5, sDate);
			      		st.setDate(6, eDate);
			      		st.setInt(7, depo);
			      	}
			    	rs=st.executeQuery();
			    	boolean first=true;
			    	int winv=0;
			    	double tot1=0.00;
			    	double tot2=0.00;
			    	double tot3=0.00;
			    	double tot4=0.00;
			    	double trfamt=0.00;
			    	while(rs.next())
			    	{
			    		
			    		ivdt = new InvViewDto();
						ivdt.setNet_amt(0.00);// netamt
						if(first)
			    		{
				    		ps1.setInt(1, div);
				    		ps1.setInt(2, year);
				    		ps1.setString(3, rs.getString(1));
				    		ps1.setString(4, rs.getString(3));
				    		rs1=ps1.executeQuery();
				    		if(rs1.next())
				    		{
				    			trfamt=rs1.getDouble(1);
				    		}
				    		rs1.close();

				    		first=false;
			    			winv=rs.getInt(1);
							ivdt.setNet_amt(rs.getDouble(14)+trfamt);// netamt
							tot3+=rs.getDouble(14)+trfamt;
							trfamt=0;
			    		}
			    		if(rs.getInt(1)!=winv)
			    		{
				    		ps1.setInt(1, div);
				    		ps1.setInt(2, year);
				    		ps1.setString(3, rs.getString(1));
				    		ps1.setString(4, rs.getString(3));
				    		rs1=ps1.executeQuery();
				    		if(rs1.next())
				    		{
				    			trfamt=rs1.getDouble(1);
				    		}
				    		rs1.close();
							ivdt.setNet_amt(rs.getDouble(14)+trfamt);// netamt
			    			winv=rs.getInt(1);
							tot3+=rs.getDouble(14)+trfamt;
							trfamt=0;
			    		}
			    		
						ivdt.setInv_no(rs.getString(1));
						ivdt.setInv_date(rs.getDate(2));
						System.out.println("CODE IS "+rs.getString(3));
						ivdt.setMac_code(rs.getString(3));
						ivdt.setMac_name_hindi(rs.getString(4));
						ivdt.setMcity_hindi(rs.getString(5));
						ivdt.setMark_no(rs.getString(6));
						ivdt.setFloor_no(rs.getString(7)); 
						ivdt.setBlock_no(rs.getString(8));
						ivdt.setCategory_hindi(rs.getString(9));  // category
						ivdt.setSqty(rs.getInt(10));
						ivdt.setPname_hindi(rs.getString(11)); // PNAME
						ivdt.setSrate_net(rs.getDouble(12)); // rate
						ivdt.setWeight(rs.getDouble(13));  // weight
						ivdt.setPack(rs.getString(17)); // Pack
						ivdt.setVehicle_no(rs.getString(18));
						ivdt.setReceiver_name(rs.getString(19));
						ivdt.setMobile(rs.getString(20));
						ivdt.setIssue_qty(rs.getInt(21));
						ivdt.setIssue_weight(rs.getDouble(22));  
						ivdt.setChl_no(rs.getString(23));
						ivdt.setChl_date(rs.getDate(24));
						ivdt.setMphone(rs.getString(25));
						ivdt.setTransfer_no(rs.getInt(26));
						ivdt.setFromhq(rs.getString(27));
						ivdt.setRound_off(rs.getDouble(28)); 
						ivdt.setSamnt(rs.getDouble(29));  // rent amt
						ivdt.setRemark(rs.getString(30));
						ivdt.setDiscount_amt(rs.getDouble(15));  // rcp amt
						ivdt.setDash(0);
						tot1+=rs.getDouble(29);
						tot2+=rs.getDouble(28);
						tot4+=rs.getDouble(15);
			    		
			    		
			    		data.add(ivdt);
			    	}

			    	if((xdoc==700 || xdoc==10))
			    	{
			    		ivdt = new InvViewDto();
			    		ivdt.setSamnt(tot1);  // rent amt
			    		ivdt.setRound_off(tot2); 
			    		ivdt.setNet_amt(tot3);// netamt
			    		ivdt.setDiscount_amt(tot4);// netamt
			    		ivdt.setDash(1);
			    		data.add(ivdt);
			    	}  
					    rs.close();
					    st.close();
					    ps1.close();
			   }
			catch(Exception ee){
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
					System.out.println("-------------Exception in SalRegDAO.Connection.close "+ee);
				  }
			}
			
			return data;
	   }


	 public List salRegPrintFinal(String smon,int year,int depo,int div,Date sdate,Date edate,ArrayList partyList,int optn,int doc_type)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   PreparedStatement ps2 =null;
		   ResultSet rs2= null; 
		   PreparedStatement ps1 =null;
		   ResultSet rs1= null; 

		   
		   InvViewDto ivdt=null;
		   String query=null;
		   double tot1=0.00;
		   double tot2=0.00;
		   double tot3=0.00;

		   double gtot1=0.00;
		   double gtot2=0.00;
		   double gtot3=0.00;

		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   StringBuffer pcode = new StringBuffer("f.sprt_cd in (");
		   StringBuffer pcode1 = new StringBuffer("f.vac_code in (");
		   
		   try{
			    con=ConnectionFactory.getConnection();
			   
			      int size=partyList.size();
			      int p=0;
			      for (p=0;p<size-1;p++)
			      {
			    	  pcode.append(partyList.get(p)+",");
			    	  pcode1.append(partyList.get(p)+",");
			      }
			      
			      pcode.append(partyList.get(p)+") and ");
			      pcode1.append(partyList.get(p)+") and ");
			      
			      System.out.println("PCODE "+pcode);
			      System.out.println("PCODE1 "+pcode1);
			      

			      query="select a.inv_no ,a.sinv_dt,a.account_no,a.mac_name_hindi,a.mcity_hindi," +
			    		  "a.category,a.pname_hindi,a.srate_net,sum(a.sqty), "+
			    		  " sum(a.weight),a.iqty,a.iweight,sum(a.net_amt),a.mobile,a.sdoc_type,sum(a.rcp_amt)," +
			    		  " a.written_off_qty,a.written_off_weight,a.transfer_no,a.driver_name,a.driver_city,a.serialno " +
			    		  " from"+ 
			    		  " (select f.sinv_no inv_no ,f.sinv_dt,p.account_no,p.mac_name_hindi,p.mcity_hindi," +
			    		  " f.category,sum(f.sqty) sqty,"+
			    		  " concat(g.gp_name_hindi,' ',p1.pname_hindi) pname_hindi,f.srate_net,sum(f.weight) weight," +
			    		  " round(sum(f.net_amt)) net_amt ,0 rcp_amt,f.sprt_cd party_Code,f.pack pack," +
			    		  " 0 iqty, 0 iweight,f.sprd_cd sprd_cd,p.mobile,f.sdoc_type,1 seq," +
			    		  " ifnull(written_off_qty,0) written_off_qty, ifnull(written_off_weight,0) written_off_weight,ifnull(transfer_no,0) transfer_no, "+
			    		  " ifnull(driver_name,'') driver_name,ifnull(driver_city,'') driver_city ,f.serialno serialno "+
			    		  " from invsnd f , accountmaster p,prd p1,grpmast g  where  f.sdepo_code=? and f.div_code=? and f.sdoc_type=60 "+
			    		  " and  f.fin_year=? and f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D' "+  
			    		  " and "+pcode+"   f.sprt_cd=p.account_no  and p.depo_code=? and p.div_code=f.div_code " +
			    		  " and p1.div_code=f.div_code and p1.pcode=f.sprd_cd "+ 
			    		  " and g.div_code=f.div_code and g.gp_Code=f.spd_gp group by f.sinv_no,f.sprt_Cd,f.sprd_cd ,f.serialno" +
			    		  " union all  " +
			    		  " select f.spinv_no inv_no ,f.spinv_dt sinv_dt,p.account_no,p.mac_name_hindi,p.mcity_hindi," +
			    		  " f.category,0 sqty,"+
			    		  " concat(g.gp_name_hindi,' ',p1.pname_hindi) pname_hindi,f.srate_net,0 weight," +
			    		  " round(sum(f.net_amt)*-1) net_amt ,0 rcp_amt,f.sprt_cd party_Code,f.pack pack," +
			    		  " sum(ifnull(f.issue_qty,0)) iqty,sum(ifnull(f.issue_weight,0)) iweight,f.sprd_cd sprd_cd,p.mobile,f.sdoc_type,2 seq," +
			    		  " ifnull(written_off_qty,0) written_off_qty, ifnull(written_off_weight,0) written_off_weight,ifnull(transfer_no,0) transfer_no," +
			    		  " ifnull(driver_name,'') driver_name,ifnull(driver_city,'') driver_city ,f.serialno serialno "+
			    		  " from invsnd f , accountmaster p,prd p1,grpmast g  where  f.sdepo_code=? and f.div_code=? and f.sdoc_type in(40,41) "+
			    		  " and  f.fin_year=? and f.sinv_dt between ? and ? and  ifnull(f.del_tag,'')<>'D' "+  
			    		  " and "+pcode+"   f.sprt_cd=p.account_no  and p.depo_code=? and p.div_code=f.div_code " +
			    		  " and p1.div_code=f.div_code and p1.pcode=f.sprd_cd "+ 
			    		  " and g.div_code=f.div_code and g.gp_Code=f.spd_gp group by f.spinv_no,f.sprt_Cd,f.sprd_cd,f.serialno" +
			    		  " union all "+   
			    		  " select f.vou_no inv_no ,f.vou_date sinv_dt,p.account_no,p.mac_name_hindi,p.mcity_hindi, "+ 
			    		  " '' category,0 sqty, "+
			    		  " ''  pname_hindi,0 srate_net,0 weight, "+ 
			    		  " 0 ,vamount rcp_amt,f.vac_code party_Code,'' pack, "+ 
			    		  " 0 iqty,0 iweight,0 sprd_cd,p.mobile,f.vbook_cd,3 seq," +
			    		  " 0 written_off_qty,0 written_off_weight,0 transfer_no, "+
			    		  " '' driver_name,'' driver_city,f.txn_serialno serialno "+
			    		  " from ledfile  f , accountmaster p  where  f.vdepo_code=? and f.div_code=? and  f.vbook_cd in(10,20) "+ 
			    		  " and  f.fin_year=? and f.vou_date between ? and ? and  ifnull(f.del_tag,'')<>'D' "+   
			    		  " and "+pcode1+"   f.vac_code=p.account_no  and p.depo_code=? and p.div_code=f.div_code "+
			    		  " group by f.vou_no,f.vac_code ) a "+ 
			    		  " group by a.account_no,a.inv_no ,a.sdoc_type,a.serialno order by a.account_no,a.sinv_dt,a.inv_no,a.serialno";

			      
			    	  String query2="select sum(vamount) from ledfile where vdepo_code=? and div_Code=? and fin_year=?  " +
			    	  		"and vbook_cd in (10,20) and vac_code=? and (rcp_no=? or ifnull(rcp_no,0)=0)";			      
			      
				      

			    	  
			      st = con.prepareStatement(query);
			      ps2 = con.prepareStatement(query2);
			      data = new ArrayList();
			     
		   
			      
		    		st.setInt(1, depo);
		    		st.setInt(2, div);
		    		st.setInt(3, year);
		    		st.setDate(4, sDate);
		    		st.setDate(5, eDate);
		    		st.setInt(6, depo);
		    		st.setInt(7, depo);
		     		st.setInt(8, div);
		 		   	st.setInt(9, year);
		    		st.setDate(10, sDate);
		    		st.setDate(11, eDate);
		    		st.setInt(12, depo);
		    		st.setInt(13, depo);
		     		st.setInt(14, div);
		 		   	st.setInt(15, year);
		    		st.setDate(16, sDate);
		    		st.setDate(17, eDate);
		    		st.setInt(18, depo);
			    	 
			    	rs=st.executeQuery();
			    	boolean first=true;
			    	int winv=0;
			    	int party_code=0;
			    	double trfamt=0.00;
			    	while(rs.next())
			    	{

			    		ivdt = new InvViewDto();
//						ivdt.setNet_amt(0.00);// netamt

			    		System.out.println(rs.getDouble(13)+"  "+rs.getInt(1)+"  "+rs.getInt(15)+"SERIAL NO "+rs.getInt(22));
			    		if(first)
			    		{
			    			first=false;
			    			party_code=rs.getInt(3);
			    			winv=rs.getInt(1);
							
						
							ivdt.setNet_amt(rs.getDouble(13));// netamt
							
							if(rs.getInt(15)==60)
							{
								ps2.setInt(1, depo);
								ps2.setInt(2, div);
								ps2.setInt(3, year);
								ps2.setInt(4, rs.getInt(3));
								ps2.setInt(5, rs.getInt(1));
								rs2=ps2.executeQuery();
								if(rs2.next())
								{
									ivdt.setSamnt(rs2.getDouble(1));  // rent amt
//									ivdt.setRound_off(rs.getDouble(13)-rs2.getDouble(1));  // net due
									tot1=tot1+rs.getDouble(13);
//									tot2=tot2+rs2.getDouble(1);
//									tot3=tot1-tot2;
								}
								rs2.close();
							}
			    		}

			    		if(rs.getInt(3)!=party_code)
			    		{
			    			tot3=tot1-tot2;
				    		ivdt = new InvViewDto();
							ivdt.setNet_amt(0.00);// netamt
							ivdt.setPname_hindi("Party Total :"); // PNAME
							ivdt.setNet_amt(tot1);// netamt
							ivdt.setSamnt(tot2);// netamt
							ivdt.setRound_off(tot3);// netamt
							ivdt.setDash(1);
				    		data.add(ivdt);
			    			party_code=rs.getInt(3);
			    			gtot1+=tot1;
			    			gtot2+=tot2;
			    			gtot3+=tot1-tot2;
			    			tot1=0.00;
			    			tot2=0.00;
			    			tot3=0.00;
			    			
			    		}
			    		
			    		if(rs.getInt(1)!=winv)
			    		{
				    		ivdt = new InvViewDto();
							ivdt.setNet_amt(0.00);// netamt
							
							ivdt.setNet_amt(rs.getDouble(13));// netamt
							
							
			    			if(rs.getInt(15)==60)
			    			{
								ps2.setInt(1, depo);
								ps2.setInt(2, div);
								ps2.setInt(3, year);
								ps2.setInt(4, rs.getInt(3));
								ps2.setInt(5, rs.getInt(1));
			    				rs2=ps2.executeQuery();
			    				if(rs2.next())
			    				{
			    					ivdt.setSamnt(rs2.getDouble(1));  // rent amt
//			    					ivdt.setRound_off(rs.getDouble(13)-rs2.getDouble(1));  // net due
			    					tot1=tot1+rs.getDouble(13);
//			    					tot2=tot2+rs2.getDouble(1);
			    					tot3=tot1-tot2;
			    				}
			    				rs2.close();
			    			}
							winv=rs.getInt(1);
							
							
			    		}
			    		
						ivdt.setInv_no(rs.getString(1));
						ivdt.setInv_date(rs.getDate(2));
						ivdt.setMac_code(rs.getString(3));
						ivdt.setMac_name_hindi(rs.getString(4));
						ivdt.setMcity_hindi(rs.getString(5));
						ivdt.setCategory_hindi(rs.getString(6));  // category
						ivdt.setPname_hindi(rs.getString(7)); // PNAME
						ivdt.setSrate_net(rs.getDouble(8)); // rate
						ivdt.setSqty(rs.getInt(9));
						ivdt.setWeight(rs.getDouble(10));  // weight
						ivdt.setIssue_qty(rs.getInt(11));
						ivdt.setIssue_weight(rs.getDouble(12)); 
						ivdt.setMphone(rs.getString(14));
						ivdt.setSamnt(rs.getDouble(16));  // rcp_amt
						ivdt.setWritten_off_qty(rs.getDouble(17));  // written off qty
						ivdt.setWritten_off_weight(rs.getDouble(18));  // written off weight
						ivdt.setTransfer_no(rs.getInt(19));
						ivdt.setReceiver_name(rs.getString(20));
						ivdt.setReceiver_city(rs.getString(21));
						
						if(rs.getInt(15)==41)
						{
							ivdt.setNet_amt(rs.getDouble(13));// netamt
							tot1=tot1+rs.getDouble(13);
						}
						
						ivdt.setDoc_type(rs.getInt(15));
						tot2=tot2+rs.getDouble(16);

						data.add(ivdt);
			    	}

		    		ivdt = new InvViewDto();
		    		tot3=tot1-tot2;
	    			gtot1+=tot1;
	    			gtot2+=tot2;
					ivdt.setPname_hindi("Party Total** :"); // PNAME
					ivdt.setNet_amt(tot1);// netamt
					ivdt.setSamnt(tot2);// netamt
					ivdt.setRound_off(tot3);// netamt
					ivdt.setDash(1);
		    		data.add(ivdt);
		    		gtot3=gtot1-gtot2;
		    		ivdt = new InvViewDto();
					ivdt.setPname_hindi("Total :"); // PNAME
					ivdt.setNet_amt(gtot1);// netamt
					ivdt.setSamnt(gtot2);// netamt
					ivdt.setRound_off(gtot3);// netamt
					ivdt.setDash(3);
		    		data.add(ivdt);
			           
					    rs.close();
					    st.close();
					    ps2.close();
			   }
			catch(Exception ee){
				ee.printStackTrace();
			}		   
		  
			finally {
				try {
					    
		             if(rs != null){rs.close();}
		             if(st != null){st.close();}
		             if(rs2 != null){rs2.close();}
		             if(ps2 != null){ps2.close();}
		             if(con != null){con.close();}
				} catch (SQLException ee) {
					System.out.println("-------------Exception in SalRegDAO.Connection.close "+ee);
				  }
			}
			
			return data;
	   }
	 
	 
	 
	 
	 public List purRegPrint(String smon,int year,int depo,int div,Date sdate,Date edate,ArrayList partyList)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvFstDto inv=null;
		   String query=null;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   int doc_tp=0;
		   try{
			    con=ConnectionFactory.getConnection();
			    
			    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,left(p.mac_name,23),p.mcity, "+
				"f.gross_amt,f.bill_amt,date_add(f.mtr_dt,INTERVAL f.due_days  day) as due_date,f.mtr_dt,p.mpst_no,f.pinv_no,f.pinv_date," +
				"f.chl_no,f.chl_dt,f.cases,ifnull(f.ctax1_amt,0),ifnull(f.mtr_no,''),ifnull(left(f.transport,20),''),left(f.order_no,15)," +
				"f.aproval_no,f.aproval_dt,f.inv_no,ifnull(f.inv_type,'M'),ifnull(f.cn_val,0) "+
				"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type in (60,61,62,72) " +
				"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D'  " +
				" and p.div_code=?  and p.depo_code=? and f.party_code=? order by f.inv_no ";

			    if (doc_tp==1)
			    {
				    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,left(p.mac_name,23),p.mcity, "+
					"f.gross_amt,f.bill_amt,date_add(f.mtr_dt,INTERVAL f.due_days  day) as due_date,f.mtr_dt,p.mpst_no,f.pinv_no,f.pinv_date," +
					"f.chl_no,f.chl_dt,f.cases,ifnull(f.ctax1_amt,0),ifnull(f.mtr_no,''),ifnull(left(f.transport,20),''),left(f.order_no,15)," +
					"f.aproval_no,f.aproval_dt,f.inv_no,ifnull(f.inv_type,'M'),ifnull(f.cn_val,0)  "+
					"from invfst as f,partyfst as p where f.depo_code=? and f.doc_type in (60,61,62,72) " +
					"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D'  " +
					" and p.depo_code=? and f.party_code=? group by p.mpst_no order by left(p.mpst_no,12),p.mac_name,p.mcity";
			    }
	
			    
			    
			    st = con.prepareStatement(query);
			    
			    data = new ArrayList();

                int size=partyList.size();
                String partyCode;
                double gross=0.00;
                double bill=0.00;
                double ctax=0.00;
                int cases=0;
                boolean ok=false;
                for (int p=0;p<size;p++)
                {
                	partyCode = partyList.get(p).toString().trim();
                	System.out.println("partyCode "+partyCode);
                	if (doc_tp==1)
                	{
	    			    st.setInt(1, depo);
	    			    st.setDate(2, sDate);
	    			    st.setDate(3, eDate);
	    			    st.setInt(4, depo);
	    			    st.setString(5, partyCode);
                		
                	}
                	else
                	{
	                	st.setInt(1, div);
	    			    st.setInt(2, depo);
	    			    st.setDate(3, sDate);
	    			    st.setDate(4, eDate);
	    			    st.setInt(5, div);
	    			    st.setInt(6, depo);
	    			    st.setString(7, partyCode);
                	}
    			    rs=st.executeQuery();
            		ok=false;
    			    
                	while(rs.next())
                	{
                		ok=true;
                		inv = new InvFstDto();
                		inv.setInv_type(rs.getString(1));
                		inv.setInv_date(rs.getDate(2));
                		inv.setParty_name(rs.getString(3));
                		inv.setCity(rs.getString(4));
                		inv.setGross_amt(rs.getDouble(5));
                		inv.setBill_amt(rs.getDouble(6));
                		inv.setDue_dt(rs.getDate(7));
                		inv.setMtr_dt(rs.getDate(8));
                		inv.setTin_no(rs.getString(9));
                		inv.setPinv_no(rs.getString(10));
                		inv.setPinv_date(rs.getDate(11));
                		if (depo==61)
                		{
                    		inv.setPinv_no(rs.getString(12));
                    		inv.setPinv_date(rs.getDate(13));

                		}
                		inv.setCases(rs.getInt(14));
                		inv.setCtax1_amt(rs.getDouble(15));
                		inv.setMtr_no(rs.getString(16));
                		inv.setTransport(rs.getString(17));
                		inv.setOrder_no(rs.getString(18));  // truck no
                		inv.setAproval_no(rs.getString(19)); // road permit no
                		inv.setAproval_dt(rs.getDate(20)); // road permit date
                		inv.setChl_no(rs.getString(12));
                		inv.setChl_dt(rs.getDate(13));
                		inv.setInv_no(rs.getInt(21));
                		inv.setInv_lo(rs.getString(22));  // medicine or food
                		inv.setCn_val(rs.getDouble(23));  // excise amount
                		inv.setDash(0);
                		data.add(inv);
                		cases+=rs.getInt(14);
                		gross+=rs.getDouble(5);
                		bill+=rs.getDouble(6);
                		ctax+=rs.getDouble(15);
                	}
                	if (ok)
                	{
	            		inv = new InvFstDto();
	            		inv.setDash(1);
	            		inv.setGross_amt(gross);
	            		inv.setBill_amt(bill);
	            		inv.setCtax1_amt(ctax);
	            		inv.setCases(cases);
	            		data.add(inv);
                	}
            		gross=0.00;
            		bill=0.00;
            		ctax=0.00;
            		cases=0;
                	
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
	 
	 public List trfRegPrint(String smon,int year,int depo,int div,Date sdate,Date edate,ArrayList partyList,int optn)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvFstDto inv=null;
		   String query=null;
		   String  doctype= " in (67,68) ";
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try{
			   con=ConnectionFactory.getConnection();
			   if (depo==1)
				   doctype= " in (67,68,39,40,65) ";

			   if (div==0)   // all division
			   {
				   if (optn==1)
				   {
					   query="select f.inv_yr,f.inv_lo,f.inv_no,f.inv_date,left(p.mac_name,23),p.mcity, "+
							   "f.bill_amt,ifnull(f.mtr_no,''),f.mtr_dt,f.cases,left(f.transport,18),f.order_no,p.mpst_no "+
							   "from invfst as f,partyfst as p where f.div_code<4 and f.depo_code=? and f.doc_type "+doctype+
							   " and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
							   " and p.div_code=f.div_code  and p.depo_code=? and f.party_code=? order by f.inv_no";
				   }
				   else
				   {
					   query="select f.inv_yr,f.inv_lo,f.inv_no,f.inv_date,left(p.mac_name,23),p.mcity, "+
							   "f.bill_amt,ifnull(f.mtr_no,''),f.mtr_dt,f.cases,left(f.transport,18),f.order_no,p.mpst_no "+
							   "from invfst as f,partyfst as p where f.div_code<4 and f.depo_code=? and f.doc_type "+doctype+
							   " and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
							   " and p.div_code=f.div_code  and p.depo_code=? order by p.mpst_no,f.inv_date";
				   }
			   }
			   
			   else
			   {


				   if (optn==1)
				   {
					   query="select f.inv_yr,f.inv_lo,f.inv_no,f.inv_date,left(p.mac_name,23),p.mcity, "+
							   "f.bill_amt,ifnull(f.mtr_no,''),f.mtr_dt,f.cases,left(f.transport,18),f.order_no,p.mpst_no "+
							   "from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type "+doctype+
							   " and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
							   " and p.div_code=?  and p.depo_code=? and f.party_code=? order by f.inv_no";
				   }
				   else
				   {
					   query="select f.inv_yr,f.inv_lo,f.inv_no,f.inv_date,left(p.mac_name,23),p.mcity, "+
							   "f.bill_amt,ifnull(f.mtr_no,''),f.mtr_dt,f.cases,left(f.transport,18),f.order_no,p.mpst_no "+
							   "from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type "+doctype+
							   " and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
							   " and p.div_code=?  and p.depo_code=? order by f.inv_no";

				   }
			   }
			   st = con.prepareStatement(query);
			  
			   
			   
			   data = new ArrayList();
			   String msg;
			   int size=partyList.size();
			   String partyCode;
			   
			   if (optn==1)
			   {
				   for (int p=0;p<size;p++)
				   {
					   partyCode = partyList.get(p).toString().trim();
					   
					   if (div==0) // all division
					   {
						   st.setInt(1, depo);
						   st.setDate(2, sDate);
						   st.setDate(3, eDate);
						   st.setInt(4, depo);
						   st.setString(5, partyCode);
					   }
					   else
					   {
						   st.setInt(1, div);
						   st.setInt(2, depo);
						   st.setDate(3, sDate);
						   st.setDate(4, eDate);
						   st.setInt(5, div);
						   st.setInt(6, depo);
						   st.setString(7, partyCode);
						   
					   }
					   rs=st.executeQuery();   
					   while(rs.next())
					   {
						   msg="";
						   inv = new InvFstDto();
/*						   if (depo==1)
							   msg = "MISC/"+rs.getString(3);
						   else
							   msg = "TR"+rs.getString(1)+rs.getString(2)+rs.getString(3).substring(2);
*/
						   
						   if (depo==1 )
							   msg = div < 4 ?("CWH"+rs.getString(2)+"/"+rs.getString(3)):("CWH"+rs.getString(2)+"P/"+rs.getString(3).substring(1));
						   else if (depo==56)
							   msg = "CW"+rs.getString(2)+"/"+rs.getString(3);
						   else
							   msg = "TR"+rs.getString(1)+rs.getString(2)+rs.getString(3).substring(2);

						   
						   inv.setInv_type(msg);
						   inv.setInv_date(rs.getDate(4));
						   inv.setParty_name(rs.getString(5));
						   inv.setCity(rs.getString(6));
						   inv.setBill_amt(rs.getDouble(7));
						   inv.setMtr_no(rs.getString(8));
						   inv.setMtr_dt(rs.getDate(9));
						   inv.setCases(rs.getInt(10));
						   inv.setTransport(rs.getString(11));
						   inv.setOrder_no(rs.getString(12)); // truck no
						   inv.setTin_no(rs.getString(13)); // tin no

						   data.add(inv);
					   }
				   }
			   } // end of optn 1

			   if (optn==2)
			   {
				   
				       if (div==0)  // division
				       {
				    	   st.setInt(1, depo);
				    	   st.setDate(2, sDate);
				    	   st.setDate(3, eDate);
				    	   st.setInt(4, depo);
				    	   
				       }
				       else
				       {
				    	   st.setInt(1, div);
				    	   st.setInt(2, depo);
				    	   st.setDate(3, sDate);
				    	   st.setDate(4, eDate);
				    	   st.setInt(5, div);
				    	   st.setInt(6, depo);
				       }
					   rs=st.executeQuery();   
					   while(rs.next())
					   {
						   inv = new InvFstDto();
						   if (depo==1 )
							   msg = div < 4 ?("CWH"+rs.getString(2)+"/"+rs.getString(3)):("CWH"+rs.getString(2)+"P/"+rs.getString(3).substring(1));
						   else if (depo==56)
							   msg = "CW"+rs.getString(2)+"/"+rs.getString(3);
						   else
							   msg = "TR"+rs.getString(1)+rs.getString(2)+rs.getString(3).substring(2);
						   inv.setInv_type(msg);
						   inv.setInv_date(rs.getDate(4));
						   inv.setParty_name(rs.getString(5));
						   inv.setCity(rs.getString(6));
						   inv.setBill_amt(rs.getDouble(7));
						   inv.setMtr_no(rs.getString(8));
						   inv.setMtr_dt(rs.getDate(9));
						   inv.setCases(rs.getInt(10));
						   inv.setTransport(rs.getString(11));
						   inv.setOrder_no(rs.getString(12)); // truck no
						   inv.setTin_no(rs.getString(13)); // tin no

						   data.add(inv);
					   }
			   } // end of optn 2
			   
			   
			   rs.close();
			   st.close();
		   }
		   catch(Exception ee){}		   

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
	 
	 public List JVIPrint(int year,int depo,int div,Date sdate,Date edate,String code,int optn)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvFstDto inv=null;
		   String query=null;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try{
			    con=ConnectionFactory.getConnection();

			    query="select f.vac_code,(f.vamount-ifnull(f.tds_amt,0)),f.bill_no,f.bill_date,f.cr_no,f.cr_date," +
			    " p.mac_name,p.mcity,f.vou_no,f.vou_date "+
				"from rcpfile as f , partyfst as p where f.div_code=? and f.vdepo_code=? " +
				"and  f.vou_date between ? and ? and ifnull(f.inv_lo,'')<>'NO' and ifnull(f.del_tag,'')<>'D' and f.vbook_cd=98 and p.div_code=? and p.depo_code=? and p.mac_code=f.vac_code " +
				" order by f.vou_no ";

			    if (optn==1)
			    {
				    query="select f.vac_code,(f.vamount-ifnull(f.tds_amt,0)),f.bill_no,f.bill_date,f.cr_no,f.cr_date," +
						    " p.mac_name,p.mcity,f.vou_no,f.vou_date "+
							"from rcpfile as f , partyfst as p where f.div_code=? and f.vdepo_code=? " +
							"and  f.vou_date between ? and ? and ifnull(f.inv_lo,'')<>'NO' and ifnull(f.del_tag,'')<>'D' and f.vbook_cd=98 and f.vac_code='"+code+"' and p.div_code=? and p.depo_code=? and p.mac_code=f.vac_code " +
							" order by f.vou_no ";
			    	
			    }
			    
/*			    query="select f.cdprt_cd,f.cdnote_amt,ifnull(f.cdinv_yr,0),ifnull(f.cdinv_lo,''),ifnull(f.cdinv_no,0),f.cdinv_dt,ifnull(f.cdnote_yr,0),ifnull(f.cdnote_lo,''),f.cdnote_tp,ifnull(f.cdnote_no,0),f.cdnote_dt," +
			    " p.mac_name,p.mcity "+
				"from invtrd as f , partyfst as p where f.div_code=? and f.depo_code=? " +
				"and  f.cdinv_dt between ? and ? and ifnull(f.del_tag,'')<>'D' and f.cdnote_tp='C' and p.div_code=? and p.depo_code=? and p.mac_code=f.cdprt_cd " +
				" order by f.cdnote_no ";
*/
			    st = con.prepareStatement(query);
				   st.setInt(1, div);
				   st.setInt(2, depo);
				   st.setDate(3, sDate);
				   st.setDate(4, eDate);
				   st.setInt(5, div);
				   st.setInt(6, depo);
				   rs=st.executeQuery();   
  			    data=new ArrayList();
  			    while(rs.next())
              	{

              		inv = new InvFstDto();
              		inv.setParty_code(rs.getString(1));
              		inv.setBill_amt(rs.getDouble(2));
              		inv.setInv_type(rs.getString(3));
              		if (rs.getDate(4)!=null)
              			inv.setInv_date(rs.getDate(4));
              		inv.setPinv_no(rs.getString(5));
              		if (rs.getDate(6)!=null)
              			inv.setPinv_date(rs.getDate(6));
              		inv.setParty_name(rs.getString(7));
              		inv.setCity(rs.getString(8));
              		inv.setInv_lo("JVI"+rs.getString(9).substring(1));
              		if (rs.getDate(10)!=null)
              			inv.setOrder_dt(rs.getDate(10));
              		
              		data.add(inv);
              	}
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error hai "+ee);}		   
		  
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

	 
	 public List CRNAdj(int year,int depo,int div,Date sdate,Date edate,ArrayList partyList,int optn, int typ)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvFstDto inv=null;
		   String query=null;
		   String partyCode="";
		   String wcode="";
		   double tot1=0.00;
		   boolean first=true;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   String tp="";
		     if (typ==1)
		    	  tp=" and cdnote_tp='C' ";
		     if (typ==2)
		    	  tp=" and cdnote_tp='D' ";
		     if (typ==3)
		    	  tp=" ";
		     
		   
		   try{
			    con=ConnectionFactory.getConnection();
			    

			    if (optn==1)
			    {
				    query="select f.cdprt_cd,f.cdnote_amt,ifnull(f.cdinv_yr,0),ifnull(f.cdinv_lo,''),ifnull(f.cdinv_no,0),f.cdinv_dt,ifnull(cdnote_yr,0),ifnull(cdnote_lo,''),cdnote_tp,ifnull(cdnote_no,0),f.cdnote_dt," +
				    " p.mac_name,p.mcity "+
					"from invtrd as f , partyfst as p where f.div_code=? and f.depo_code=? " +
					"and  f.cdinv_dt between ? and ? and ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and f.cdprt_cd=?  and p.mac_code=f.cdprt_cd " +tp+
					" order by f.cdinv_no ";

			    }
			    else
			    {
				    query="select f.cdprt_cd,f.cdnote_amt,ifnull(f.cdinv_yr,0),ifnull(f.cdinv_lo,''),ifnull(f.cdinv_no,0),f.cdinv_dt,ifnull(cdnote_yr,0),ifnull(cdnote_lo,''),cdnote_tp,ifnull(cdnote_no,0),f.cdnote_dt," +
				    " p.mac_name,p.mcity "+
					"from invtrd as f , partyfst as p where f.div_code=? and f.depo_code=? " +
					"and  f.cdinv_dt between ? and ? and ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and p.mac_code=f.cdprt_cd " +tp+
					" order by f.cdinv_no ";
			    }
		    	st = con.prepareStatement(query);
			    
			    data=new ArrayList();
			    
			    
			    if (optn==1)
			    {	
			    int size=partyList.size();
			    for (int p=0;p<size;p++)
			    {
			    	partyCode = partyList.get(p).toString().trim();

			    	st.setInt(1, div);
			    	st.setInt(2, depo);
			    	st.setDate(3, sDate);
			    	st.setDate(4, eDate);
			    	st.setInt(5, div);
			    	st.setInt(6, depo);
			    	st.setString(7,partyCode);
			    	rs=st.executeQuery();   
			    	while(rs.next())
			    	{
			    		if (first)
			    		{
			    			wcode=partyCode;
			    			first=false;
			    		}
			    		if (!wcode.equals(partyCode))
			    		{
				    		inv = new InvFstDto();
				    		inv.setBill_amt(tot1);
				    		inv.setDash(1);
				    		tot1=0.00;
				    		data.add(inv);
			    			wcode=partyCode;
			    			
			    		}
			    		
			    		inv = new InvFstDto();
			    		inv.setParty_code(rs.getString(1));
			    		inv.setBill_amt(rs.getDouble(2));
			    		inv.setInv_type(rs.getString(3)+rs.getString(4)+String.format("%05d", rs.getInt(5)));
			    		if (rs.getDate(6)!=null)
			    			inv.setInv_date(rs.getDate(6));
			    		inv.setPinv_no(rs.getString(7)+rs.getString(8)+rs.getString(9)+String.format("%04d", rs.getInt(10)));
			    		if (rs.getDate(11)!=null)
			    			inv.setPinv_date(rs.getDate(11));
			    		inv.setParty_name(rs.getString(12));
			    		inv.setCity(rs.getString(13));
			    		inv.setDash(0);
			    		tot1+=rs.getDouble(2);
			    		data.add(inv);
			    	}
			    } // end of loop

			    		inv = new InvFstDto();
			    		inv.setBill_amt(tot1);
			    		inv.setDash(1);
			    		data.add(inv);
			    
			       } // end of optn1
			    
			    
			    if (optn==2)
			    {
					   st.setInt(1, div);
					   st.setInt(2, depo);
					   st.setDate(3, sDate);
					   st.setDate(4, eDate);
					   st.setInt(5, div);
					   st.setInt(6, depo);
					   rs=st.executeQuery();   
				    while(rs.next())
	            	{
	            		inv = new InvFstDto();
	            		inv.setParty_code(rs.getString(1));
	            		inv.setBill_amt(rs.getDouble(2));
	            		inv.setInv_type(rs.getString(3)+rs.getString(4)+String.format("%05d", rs.getInt(5)));
	            		if (rs.getDate(6)!=null)
	            			inv.setInv_date(rs.getDate(6));
	            		inv.setPinv_no(rs.getString(7)+rs.getString(8)+rs.getString(9)+String.format("%04d", rs.getInt(10)));
	            		if (rs.getDate(11)!=null)
	            			inv.setPinv_date(rs.getDate(11));
	            		inv.setParty_name(rs.getString(12));
	            		inv.setCity(rs.getString(13));
			    		inv.setDash(0);
	            		data.add(inv);
	            	}

			    }
			    
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error hai "+ee);}		   
		  
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


	 public List CRNUnAdj(int year,int depo,int div,Date sdate,Date edate,ArrayList partyList,int optn,int typ)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvFstDto inv=null;
		   String query=null;
		   String partyCode="";
		   String wcode="";
		   double tot1=0.00;
		   int doc_tp=0;
		   boolean first=true;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		     if (typ==1)
		    	  doc_tp=90;
		     if (typ==2)
		    	  doc_tp=95;

		     try{
			    con=ConnectionFactory.getConnection();


			    if (optn==1)
			    {
				    query="select f.vac_code,(f.vamount-f.rcp_amt1),ifnull(f.vou_yr,0),ifnull(f.vou_lo,''),ifnull(f.vou_no,0),f.vou_date," +
				    " p.mac_name,p.mcity "+
					"from ledfile as f , partyfst as p where f.div_code=? and f.vdepo_code=? and (f.vamount-f.rcp_amt1)<>0 and f.vbook_cd= ? " +
					"and  f.vou_date between ? and ? and ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and " +
					"f.vac_code=? and p.mac_code=f.vac_code " +
					" order by f.vou_no ";

			    }
			    else
			    {
				    query="select f.vac_code,(f.vamount-f.rcp_amt1),ifnull(f.vou_yr,0),ifnull(f.vou_lo,''),ifnull(f.vou_no,0),f.vou_date," +
				    " p.mac_name,p.mcity "+
					"from ledfile as f , partyfst as p where f.div_code=? and f.vdepo_code=? and (f.vamount-f.rcp_amt1)<>0 and f.vbook_cd= ? " +
					"and  f.vou_date between ? and ? and ifnull(f.del_tag,'')<>'D' and p.div_code=? and p.depo_code=? and p.mac_code=f.vac_code " +
					" order by f.vou_no ";
			    }
		    	st = con.prepareStatement(query);
			    
			    data=new ArrayList();
			    
			    
			    if (optn==1)
			    {	
			    int size=partyList.size();
			    for (int p=0;p<size;p++)
			    {
			    	partyCode = partyList.get(p).toString().trim();

			    	st.setInt(1, div);
			    	st.setInt(2, depo);
			    	st.setInt(3, doc_tp);
			    	st.setDate(4, sDate);
			    	st.setDate(5, eDate);
			    	st.setInt(6, div);
			    	st.setInt(7, depo);
			    	st.setString(8,partyCode);
			    	rs=st.executeQuery();   
			    	while(rs.next())
			    	{
			    		if (first)
			    		{
			    			wcode=partyCode;
			    			first=false;
			    		}
			    		if (!wcode.equals(partyCode))
			    		{
				    		inv = new InvFstDto();
				    		inv.setBill_amt(tot1);
				    		inv.setDash(1);
				    		tot1=0.00;
				    		data.add(inv);
			    			wcode=partyCode;
			    			
			    		}
			    		
			    		inv = new InvFstDto();
			    		inv.setParty_code(rs.getString(1));
			    		inv.setBill_amt(rs.getDouble(2));
			    		if (doc_tp==90)
			    			inv.setInv_type(rs.getString(3)+rs.getString(4)+"C"+String.format("%04d", rs.getInt(5)));
			    		if (doc_tp==95)
			    			inv.setInv_type(rs.getString(3)+rs.getString(4)+"D"+String.format("%04d", rs.getInt(5)));
			    		if (rs.getDate(6)!=null)
			    			inv.setInv_date(rs.getDate(6));
			    		inv.setParty_name(rs.getString(7));
			    		inv.setCity(rs.getString(8));
			    		inv.setDash(0);
			    		tot1+=rs.getDouble(2);
			    		data.add(inv);
			    	}
			    } // end of loop

			    		inv = new InvFstDto();
			    		inv.setBill_amt(tot1);
			    		inv.setDash(1);
			    		data.add(inv);
			    
			       } // end of optn1
			    
			    
			    if (optn==2)
			    {
					   st.setInt(1, div);
					   st.setInt(2, depo);
					   st.setInt(3, doc_tp);
					   st.setDate(4, sDate);
					   st.setDate(5, eDate);
					   st.setInt(6, div);
					   st.setInt(7, depo);
					   rs=st.executeQuery();   
				    while(rs.next())
	            	{
	            		inv = new InvFstDto();
			    		inv.setParty_code(rs.getString(1));
			    		inv.setBill_amt(rs.getDouble(2));
			    		if (doc_tp==90)
			    			inv.setInv_type(rs.getString(3)+rs.getString(4)+"C"+String.format("%04d", rs.getInt(5)));
			    		if (doc_tp==95)
			    			inv.setInv_type(rs.getString(3)+rs.getString(4)+"D"+String.format("%04d", rs.getInt(5)));
			    		if (rs.getDate(6)!=null)
			    			inv.setInv_date(rs.getDate(6));
			    		inv.setParty_name(rs.getString(7));
			    		inv.setCity(rs.getString(8));
			    		inv.setDash(0);
	            		data.add(inv);
	            	}

			    }
			    
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error hai "+ee);}		   
		  
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
	 
	 
	 public List OthRepo54(int year,int depo,int div,Date sdate,Date edate,int doc_tp,String code,int opt)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   PreparedStatement st1 =null;
		   ResultSet rs= null; 
		   ResultSet rs1= null; 
		   InvFstDto inv=null;
		   String query=null;
		   String tp="";
		   double tot1=0.00;
		   boolean first=true;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());

		     if (doc_tp==41)
		    	  tp="C";
		     if (doc_tp==51)
		    	  tp="D";
		   
		     try{
			    con=ConnectionFactory.getConnection();
			    
			    if(opt==1)
			    {
				    query="select a.invyr,a.invlo,a.invno,a.inv_date,a.bill_amt,a.mtr_no,a.mtr_dt, "+
				    "b.cdyr,b.cdlo,b.cdinvno,b.cdinv_dt,b.cdnote_amt,a.order_no,a.order_dt,a.cases,a.porder_no "+
				    " from "+
				    " (select f.party_code,f.bill_amt,ifnull(f.inv_yr,0) invyr,ifnull(f.inv_lo,'') invlo,ifnull(f.inv_no,0) invno,f.inv_date, "+ 
				    " f.mtr_no,f.mtr_dt,f.order_no,f.order_dt,f.cases,f.porder_no "+
				    " from invfst as f   where f.div_code=? and f.depo_code=? and f.doc_type= ? "+  
				    " and  f.inv_date between ? and ? and ifnull(f.del_tag,'')<>'D' and "+ 
				    "f.party_code=?) a "+
				    " left join "+ 
				    "(select f.cdprt_cd,f.cdnote_amt,ifnull(f.cdinv_yr,0) cdyr,ifnull(f.cdinv_lo,'') cdlo,ifnull(f.cdinv_no,0) cdinvno, "+
				    " f.cdinv_dt,ifnull(cdnote_yr,0) cdnyr,ifnull(cdnote_lo,'') cdnlo,cdnote_tp,ifnull(cdnote_no,0) cdnoteno,f.cdnote_dt "+
				    " from invtrd as f  where f.div_code=? and f.depo_code=? "+  
				    " and  f.cdnote_dt between ? and ? and ifnull(f.del_tag,'')<>'D'  and f.cdprt_cd=?  and cdnote_tp=?" +
				    " union all "+ 
				    " select r.vac_code,r.vamount,ifnull(r.vou_yr,0) cdyr,ifnull(r.vou_lo,'') cdlo,ifnull(right(r.bill_no,4),'') cdinvno, "+ 
				    " r.bill_date,  left(r.cr_no,1) cdnyr,substring(r.cr_no,2,2) cdnlo,substring(r.cr_no,4,1) cdnote_tp,ifnull(right(r.cr_no,4),0) cdnoteno,r.cr_date "+
				    " from rcpfile as r  where r.div_code=? "+
				    " and r.vdepo_code=? "+   
				    " and  r.cr_date  between ? and ? and ifnull(r.del_tag,'')<>'D'  and r.vac_code=? and r.inv_no<>0 "+ 
				    " and substring(r.cr_no,4,1) =? ) b "+
				    " on a.party_code=b.cdprt_cd and a.invno=b.cdnoteno and a.inv_date=b.cdnote_dt ";
			    }    

			    
			    if(opt==2)
			    {
				    query="select a.invyr,a.invlo,a.invno,a.inv_date,a.bill_amt,a.mtr_no,a.mtr_dt, "+
				    "b.cdyr,b.cdlo,b.cdinvno,b.cdinv_dt,b.cdnote_amt,a.order_no,a.order_dt,a.party_code,a.cases,a.porder_no "+
				    " from "+
				    " (select f.party_code,f.bill_amt,ifnull(f.inv_yr,0) invyr,ifnull(f.inv_lo,'') invlo,ifnull(f.inv_no,0) invno,f.inv_date, "+ 
				    " f.mtr_no,f.mtr_dt,f.order_no,f.order_dt,f.cases,f.porder_no "+
				    " from invfst as f  where f.div_code=? and f.depo_code=? and f.doc_type= ? "+  
				    " and  f.inv_date between ? and ? and ifnull(f.del_tag,'')<>'D'  ) a  "+ 
				    " left join "+ 
				    "(select f.cdprt_cd,f.cdnote_amt,ifnull(f.cdinv_yr,0) cdyr,ifnull(f.cdinv_lo,'') cdlo,ifnull(f.cdinv_no,0) cdinvno, "+
				    " f.cdinv_dt,ifnull(cdnote_yr,0) cdnyr,ifnull(cdnote_lo,'') cdnlo,cdnote_tp,ifnull(cdnote_no,0) cdnoteno,f.cdnote_dt "+
				    " from invtrd as f  where f.div_code=? and f.depo_code=? "+  
				    " and  f.cdnote_dt between ? and ? and ifnull(f.del_tag,'')<>'D'  and cdnote_tp=? " +
				    " union all "+ 
				    " select r.vac_code,r.vamount,ifnull(r.vou_yr,0) cdyr,ifnull(r.vou_lo,'') cdlo,ifnull(right(r.bill_no,4),'') cdinvno, "+ 
				    " r.bill_date,  left(r.cr_no,1) cdnyr,substring(r.cr_no,2,2) cdnlo,substring(r.cr_no,4,1) cdnote_tp,ifnull(right(r.cr_no,4),0) cdnoteno,r.cr_date "+
				    " from rcpfile as r  where r.div_code=? "+
				    " and r.vdepo_code=? "+   
				    " and  r.cr_date  between ? and ? and ifnull(r.del_tag,'')<>'D'  and r.inv_no<>0 "+ 
				    " and substring(r.cr_no,4,1) =? ) b "+
				    " on a.party_code=b.cdprt_cd and a.invno=b.cdnoteno and a.inv_date=b.cdnote_dt  order by a.party_code";
			    } 
			    
			    
			    String party="select mac_name,mcity from partyfst where div_code=? and depo_code=? and mac_code=? ";
			    
			    st = con.prepareStatement(query);
			    st1 = con.prepareStatement(party);
			    
			    data=new ArrayList();
			    	if (opt==1)
			    	{
					   st.setInt(1, div);
					   st.setInt(2, depo);
					   st.setInt(3, doc_tp);
					   st.setDate(4, sDate);
					   st.setDate(5, eDate);
					   st.setString(6, code);
					   st.setInt(7, div);
					   st.setInt(8, depo);
					   st.setDate(9, sDate);
					   st.setDate(10, eDate);
					   st.setString(11, code);
					   st.setString(12, tp);
					   st.setInt(13, div);
					   st.setInt(14, depo);
					   st.setDate(15, sDate);
					   st.setDate(16, eDate);
					   st.setString(17, code);
					   st.setString(18, tp);

					   rs=st.executeQuery();   
				    while(rs.next())
	            	{

				    	inv = new InvFstDto();
			    			inv.setCn_no(rs.getInt(1)+rs.getString(2)+tp+String.format("%04d", rs.getInt(3)));
			    		if (rs.getDate(4)!=null)
			    			inv.setCn_date(rs.getDate(4));
			    		inv.setCn_val(rs.getDouble(5));
			    		inv.setMtr_no(rs.getString(6));
			    		inv.setMtr_dt(rs.getDate(7));
			    		if (rs.getInt(10)==0)
			    			inv.setInv_type(" ");
			    		else
			    			inv.setInv_type(rs.getInt(8)+rs.getString(9)+String.format("%05d", rs.getInt(10)));
			    		if (rs.getDate(11)!=null)
			    			inv.setInv_date(rs.getDate(11));
			    		inv.setBill_amt(rs.getDouble(12));
			    		inv.setOrder_no(rs.getString(13));
			    		if (rs.getDate(14)!=null)
			    			inv.setOrder_dt(rs.getDate(14));
			    		inv.setCases(rs.getInt(15));
			    		inv.setPorder_no(rs.getString(16));
			    		
			    		inv.setDash(0);
	            		data.add(inv);
	            	}
			    	} // end of opt 1

			    	if (opt==2)
			    	{
					   st.setInt(1, div);
					   st.setInt(2, depo);
					   st.setInt(3, doc_tp);
					   st.setDate(4, sDate);
					   st.setDate(5, eDate);
					   st.setInt(6, div);
					   st.setInt(7, depo);
					   st.setDate(8, sDate);
					   st.setDate(9, eDate);
					   st.setString(10, tp);

					   st.setInt(11, div);
					   st.setInt(12, depo);
					   st.setDate(13, sDate);
					   st.setDate(14, eDate);
					   st.setString(15, tp);

					   
					   rs=st.executeQuery();   
					   String wcode="";
					   boolean cfirst=true;
					   String partyname="";
					   String city="";
					   while(rs.next())
	            	{

	            		if (cfirst)
	            		{
	            			 wcode=rs.getString(15);
	            			inv = new InvFstDto();
	            			st1.setInt(1, div);
	            			st1.setInt(2, depo);
	            			st1.setString(3,wcode);
	            			rs1=st1.executeQuery();   
	            			if (rs1.next())
	            			{
	            				inv.setParty_code(wcode);
	            				inv.setParty_name(rs1.getString(1));
	            				inv.setCity(rs1.getString(2));

	            			}
	            			rs1.close();
	            			inv.setDash(2);
	            			data.add(inv);
	            			
	            			 cfirst=false;
	            		}
	            		
	            		if (!wcode.equals(rs.getString(15)))
	            		{
	            			 wcode=rs.getString(15);
	            			  inv = new InvFstDto();
							   st1.setInt(1, div);
							   st1.setInt(2, depo);
							   st1.setString(3,wcode);
							   rs1=st1.executeQuery();   
							   if (rs1.next())
							   {
			            			inv.setParty_code(wcode);
			            			inv.setParty_name(rs1.getString(1));
						    		inv.setCity(rs1.getString(2));
								   
							   }
							   rs1.close();
				    		inv.setDash(2);
		            		data.add(inv);
	            			
	            		}
	            		
	            		inv = new InvFstDto();
	            		
			    		inv.setCn_no(rs.getInt(1)+rs.getString(2)+tp+String.format("%04d", rs.getInt(3)));
			    		
			    		if (rs.getDate(4)!=null)
			    			inv.setCn_date(rs.getDate(4));
			    		inv.setCn_val(rs.getDouble(5));
			    		inv.setMtr_no(rs.getString(6));
			    		inv.setMtr_dt(rs.getDate(7));
			    		if (rs.getInt(10)==0)
			    			inv.setInv_type(" ");
			    		else
			    			inv.setInv_type(rs.getInt(8)+rs.getString(9)+String.format("%05d", rs.getInt(10)));
			    		if (rs.getDate(11)!=null)
			    			inv.setInv_date(rs.getDate(11));
			    		inv.setBill_amt(rs.getDouble(12));
			    		inv.setOrder_no(rs.getString(13));
			    		if (rs.getDate(14)!=null)
			    			inv.setOrder_dt(rs.getDate(14));
			    		
			    		inv.setCases(rs.getInt(16));
			    		inv.setPorder_no(rs.getString(17));

			    		inv.setParty_code(wcode);
			    		//inv.setParty_name(rs.getString(15));
			    		//inv.setCity(rs.getString(16));
			    		inv.setDash(0);
	            		data.add(inv);
	            	}

			    	} // end of opt2
				    
			    rs.close();
			    st.close();
			    st1.close();
			   }
			catch(Exception ee){System.out.println("error hai "+ee);
				ee.printStackTrace();}		   
		     
			finally {
				try {
					    
		             if(rs != null){rs.close();}
		             if(st != null){st.close();}
		             if(rs1 != null){rs1.close();}
		             if(st1 != null){st1.close();}
		             if(con != null){con.close();}
				} catch (SQLException ee) {
					System.out.println("-------------Exception in SalRegDAO.Connection.close "+ee);
				  }
			}
			
			return data;
	   }
	 
	 public List freeIssue(String smon,int year,int depo,int div,Date sdate,Date edate,ArrayList partyList,int optn)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvFstDto inv=null;
		   String query=null;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   try{
			    con=ConnectionFactory.getConnection();
			    
			    if (optn==1)
			    {
				    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity, "+
					"(f.freeval1+f.freeval2+f.freeval3+f.freeval9),p.mpst_no "+
					"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type in (39,40) " +
					"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
					"and (f.freeval1+f.freeval2+f.freeval3+f.freeval9) >0  " +
					" and p.div_code=?  and p.depo_code=?  and f.party_code=? order by f.inv_no";
			    }
			    
			    if (optn==2)
			    {
				    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity, "+
					"(f.freeval1+f.freeval2+f.freeval3+f.freeval9),p.mpst_no "+
					"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type in (39,40) " +
					"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
					"and (f.freeval1+f.freeval2+f.freeval3+f.freeval9) >0  " +
					" and p.div_code=?  and p.depo_code=?  order by f.inv_no";
			    }

			    
			    st = con.prepareStatement(query);
			    data = new ArrayList();
			    int size=partyList.size();
			    String partyCode;
			    
			    ///////  optn 1 selective option ////
			    if (optn==1)
			    {	
			    for (int p=0;p<size;p++)
			    {
			    	partyCode = partyList.get(p).toString().trim();

			    	st.setInt(1, div);
			    	st.setInt(2, depo);
			    	st.setDate(3, sDate);
			    	st.setDate(4, eDate);
			    	st.setInt(5, div);
			    	st.setInt(6, depo);
			    	st.setString(7, partyCode);
			    	
			    	rs=st.executeQuery();
			    	while(rs.next())
			    	{
			    		inv = new InvFstDto();
			    		inv.setInv_type(rs.getString(1));
			    		inv.setInv_date(rs.getDate(2));
			    		inv.setParty_name(rs.getString(3));
			    		inv.setCity(rs.getString(4));
			    		inv.setGross_amt(rs.getDouble(5));
			    		inv.setTin_no(rs.getString(6));
			    		data.add(inv);
			    	}
			    } // end of option 1
			    }  // end of for loop

			    
			    ///////  optn 2 all option ////
			    if (optn==2)
			    {	
			    	st.setInt(1, div);
			    	st.setInt(2, depo);
			    	st.setDate(3, sDate);
			    	st.setDate(4, eDate);
			    	st.setInt(5, div);
			    	st.setInt(6, depo);
			    	
			    	rs=st.executeQuery();
			    	while(rs.next())
			    	{
			    		inv = new InvFstDto();
			    		inv.setInv_type(rs.getString(1));
			    		inv.setInv_date(rs.getDate(2));
			    		inv.setParty_name(rs.getString(3));
			    		inv.setCity(rs.getString(4));
			    		inv.setGross_amt(rs.getDouble(5));
			    		inv.setTin_no(rs.getString(6));

			    		data.add(inv);
			    	}
			    } // end of option 2

			    
			    
			    
			    
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){}		   
		  
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
	 

	 public List OthRepo57(int year,int depo,int div,Date sdate,Date edate,int doc_tp,String code,int opt)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   ResultSet rs1= null; 
		   InvFstDto inv=null;
		   String query=null;
		   String tp="";
		   double tot1=0.00;
		   boolean first=true;
		   double taxable1=0.00;
		   double taxable2=0.00;
		   double taxamt1=0.00;
		   double taxamt2=0.00;
		   double taxfree=0.00;
		   double taxable10=0.00;
		   double taxamt10=0.00;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());

		   
		     try{
			    con=ConnectionFactory.getConnection();
			    System.out.println("value of opt is "+opt);
			    System.out.println("value of code is "+code);

			    if (opt==1)
			    {
			    	if(doc_tp!=0)
				    {
					    query="select p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"sum(f.taxable1+f.taxable3),sum(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3)," +
								"sum(f.taxable2+f.taxable9),sum(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9)," +
								"sum(f.taxfree1+f.taxfree2+f.taxfree3+f.taxfree9+f.taxfree10),sum(f.taxable10),sum(ltax10_amt) " +
								"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type in (39,40) " +
								"and  f.inv_date between ? and ? and f.party_code='"+code+"' and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=?  and p.depo_code=? and p.mac_code='"+code+"' group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12)";
				    }    
	
				    if(doc_tp==0)
				    {
					    query="select p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"sum(f.taxable1+f.taxable3),sum(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),sum(f.taxable2+f.taxable9),sum(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),sum(f.taxfree1+" +
								"f.taxfree2+f.taxfree3+f.taxfree9+f.taxfree10),sum(f.taxable10),sum(ltax10_amt)  " +
								"from invfst as f,partyfst as p where f.depo_code=? and f.doc_type in (39,40) " +
								"and  f.inv_date between ? and ? and f.party_code=p.mac_code and f.div_code < 4 and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=f.div_code  and p.depo_code=? and p.mpst_no='"+code+"' group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12)";
				    }    
				    
			    }

			    if (opt==2)
			    {
			    	if(doc_tp!=0)
				    {
					    query="select p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"sum(f.taxable1+f.taxable3),sum(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),sum(f.taxable2+f.taxable9),sum(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),sum(f.taxfree1+" +
								"f.taxfree2+f.taxfree3+f.taxfree9+f.taxfree10),sum(f.taxable10),sum(ltax10_amt)  " +
								"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type in (39,40) " +
								"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=?  and p.depo_code=? group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12)";
				    }    
	
				    if(doc_tp==0)
				    {
					    query="select p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"sum(f.taxable1+f.taxable3),sum(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),sum(f.taxable2+f.taxable9),sum(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),sum(f.taxfree1+" +
								"f.taxfree2+f.taxfree3+f.taxfree9+f.taxfree10),sum(f.taxable10),sum(ltax10_amt)  " +
								"from invfst as f,partyfst as p where f.depo_code=? and f.doc_type in (39,40) " +
								"and  f.inv_date between ? and ? and f.div_code < 4 and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=f.div_code  and p.depo_code=? and p.mac_code=f.party_code group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12)";
				    }    
				    
			    }
			    
			    
			    
			    st = con.prepareStatement(query);
			    
			    data=new ArrayList();
			    
			    if (doc_tp==0)
			    {
					   st.setInt(1, depo);
					   st.setDate(2, sDate);
					   st.setDate(3, eDate);
					   st.setInt(4, depo);
			    }
			    else
			    {
					   st.setInt(1, div);
					   st.setInt(2, depo);
					   st.setDate(3, sDate);
					   st.setDate(4, eDate);
					   st.setInt(5, div);
					   st.setInt(6, depo);
			    }
					   rs=st.executeQuery();   
					   while(rs.next())
	            	{

	            		inv = new InvFstDto();
	            		inv.setParty_name(rs.getString(1));
	            		inv.setCity(rs.getString(2));
	            		inv.setTin_no(rs.getString(3));
	            		inv.setTaxable1(rs.getDouble(4));
	            		inv.setLtax1_amt(rs.getDouble(5));
	            		inv.setTaxable2(rs.getDouble(6));
	            		inv.setLtax2_amt(rs.getDouble(7));
	            		inv.setTaxfree1(rs.getDouble(8));
	            		inv.setTaxable10(rs.getDouble(9));
	            		inv.setLtax10_amt(rs.getDouble(10));
	            		
			    		inv.setDash(0);
	            		data.add(inv);
	            		taxable1+=rs.getDouble(4);
	            		taxamt1+=rs.getDouble(5);
	            		taxable2+=rs.getDouble(6);
	            		taxamt2+=rs.getDouble(7);
	            		taxfree+=rs.getDouble(8);
	            		taxable10=rs.getDouble(9);
	            		taxamt10+=rs.getDouble(10);
	            	}

            		inv = new InvFstDto();
            		inv.setTaxable1(taxable1);
            		inv.setLtax1_amt(taxamt1);
            		inv.setTaxable2(taxable2);
            		inv.setLtax2_amt(taxamt2);
            		inv.setTaxfree1(taxfree);
            		inv.setTaxable10(taxable10);
            		inv.setLtax10_amt(taxamt10);
		    		inv.setDash(1);
		    		data.add(inv);

			    	
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error hai "+ee);
				ee.printStackTrace();}		   
		     
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
// all division purchase regoster	 

	 public List purRegPrintAll(String smon,int year,int depo,int div,Date sdate,Date edate,ArrayList partyList)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvFstDto inv=null;
		   String query=null;
		   
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   int doc_tp=0;
		   try{
			    con=ConnectionFactory.getConnection();
			    
			    
			    query="select concat(f.inv_yr,f.inv_lo,f.inv_no),f.inv_date,p.mac_name,p.mcity, "+
						"f.gross_amt,f.bill_amt,date_add(f.mtr_dt,INTERVAL f.due_days  day) as due_date,f.mtr_dt,p.mpst_no,f.pinv_no,f.pinv_date," +
						"f.chl_no,f.chl_dt,f.cases,ifnull(f.ctax1_amt,0),ifnull(f.mtr_no,''),ifnull(left(f.transport,20),''),left(f.order_no,15)," +
						"f.aproval_no,f.aproval_dt,f.inv_no,ifnull(f.inv_type,'M'),ifnull(f.cn_val,0) "+
						"from invfst as f,partyfst as p where f.depo_code=? and f.div_code < 4  and f.doc_type in (60,61,62,72) " +
						"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D'  " +
						" and p.div_code=f.div_code  and p.depo_code=? order by p.mpst_no,f.pinv_date";

			    
			    
			    st = con.prepareStatement(query);
			    
			    data = new ArrayList();

              int size=partyList.size();
              String partyCode;
              double gross=0.00;
              double bill=0.00;
              double ctax=0.00;
              int cases=0;
              boolean ok=false;
			    st.setInt(1, depo);
			    st.setDate(2, sDate);
			    st.setDate(3, eDate);
			    st.setInt(4, depo);
  			    rs=st.executeQuery();
          		ok=false;
  			    
              	while(rs.next())
              	{
              		ok=true;
              		inv = new InvFstDto();
              		inv.setInv_type(rs.getString(1));
              		inv.setInv_date(rs.getDate(2));
              		inv.setParty_name(rs.getString(3));
              		inv.setCity(rs.getString(4));
              		inv.setGross_amt(rs.getDouble(5));
              		inv.setBill_amt(rs.getDouble(6));
              		inv.setDue_dt(rs.getDate(7));
              		inv.setMtr_dt(rs.getDate(8));
              		inv.setTin_no(rs.getString(9));
              		inv.setPinv_no(rs.getString(10));
              		inv.setPinv_date(rs.getDate(11));
              		if (depo==61)
              		{
                  		inv.setPinv_no(rs.getString(12));
                  		inv.setPinv_date(rs.getDate(13));

              		}
              		inv.setCases(rs.getInt(14));
              		inv.setCtax1_amt(rs.getDouble(15));
              		inv.setMtr_no(rs.getString(16));
              		inv.setTransport(rs.getString(17));
              		inv.setOrder_no(rs.getString(18));  // truck no
              		inv.setAproval_no(rs.getString(19)); // road permit no
              		inv.setAproval_dt(rs.getDate(20)); // road permit date
              		inv.setChl_no(rs.getString(12));
              		inv.setChl_dt(rs.getDate(13));
              		inv.setInv_no(rs.getInt(21));
              		inv.setInv_lo(rs.getString(22));  // medicine or food
            		inv.setCn_val(rs.getDouble(23));  // excise amount

              		inv.setDash(0);
              		data.add(inv);
              		cases+=rs.getInt(14);
              		gross+=rs.getDouble(5);
              		bill+=rs.getDouble(6);
              		ctax+=rs.getDouble(15);
              	}
              	if (ok)
              	{
	            		inv = new InvFstDto();
	            		inv.setDash(1);
	            		inv.setGross_amt(gross);
	            		inv.setBill_amt(bill);
	            		inv.setCtax1_amt(ctax);
	            		inv.setCases(cases);
	            		data.add(inv);
              	}
          		gross=0.00;
          		bill=0.00;
          		ctax=0.00;
          		cases=0;
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
	 

	 public List OthRepo57CR(int year,int depo,int div,Date sdate,Date edate,int doc_tp,String code,int opt)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   ResultSet rs1= null; 
		   InvFstDto inv=null;
		   String query=null;
		   String tp="";
		   double tot1=0.00;
		   boolean first=true;
		   double taxable1=0.00;
		   double taxable2=0.00;
		   double taxamt1=0.00;
		   double taxamt2=0.00;
		   double taxfree=0.00;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());

		   
		     try{
			    con=ConnectionFactory.getConnection();
			    System.out.println("value of opt is "+opt);
			    System.out.println("value of code is "+code);

			    if (opt==1)
			    {
			    	if(doc_tp!=0)
				    {
/*					    query="select p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"sum(f.taxable1+f.taxable3),sum(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),sum(f.taxable2+f.taxable9),sum(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),sum(f.taxfree1+" +
								"f.taxfree2+f.taxfree3+f.taxfree9) " +
								"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type in (60,61,62,72) " +
								"and  f.inv_date between ? and ? and f.party_code='"+code+"' and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=?  and p.depo_code=? and p.mac_code='"+code+"' group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12)";
*/
				    	query=" select p.mac_name,p.mcity,left(p.mpst_no,12),sum(f.taxableM),sum(f.taxm),sum(f.taxableF),sum(f.taxF),sum(f.taxableE) from "+
				    	" (select sprt_cd,round(sum(sqty*stax_cd1),2) taxableM,round((sum(sqty*stax_cd1)*stax_cd2)/100,2) taxM,0 taxableF,0 taxF,0 taxableE,div_code from invsnd where div_code=? and sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ? and stax_cd2<>0 and sale_type in (1,3) and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd "+
				    	" union all "+
				    	" select sprt_cd,0 taxableM,0 taxM,round(sum(sqty*stax_cd1),2) taxableF,round((sum(sqty*stax_cd1)*stax_cd2)/100,2) taxF,0 taxableE,div_code from invsnd where div_code=? and sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ?  and stax_cd2<>0 and sale_type in (2,9) and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd "+
				    	" union all "+
				    	" select sprt_cd,0 taxableM,0 taxM,0 taxableF, 0 taxF,round(sum(sqty*stax_cd1),2) taxableE,div_code from invsnd where div_code=? and sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ? and stax_cd2=0  and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd) f,partyfst as p "+ 
				    	" where p.div_code=?  and p.depo_code=? and f.sprt_Cd=p.mac_code and p.mpst_no='"+code+"' group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12) " ;

				    }    
	
				    if(doc_tp==0)
				    {
/*					    query="select p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"sum(f.taxable1+f.taxable3),sum(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),sum(f.taxable2+f.taxable9),sum(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),sum(f.taxfree1+" +
								"f.taxfree2+f.taxfree3+f.taxfree9) " +
								"from invfst as f,partyfst as p where f.depo_code=? and f.doc_type in (60,61,62,72) " +
								"and  f.inv_date between ? and ? and f.party_code=p.mac_code and f.div_code < 4 and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=f.div_code  and p.depo_code=? and p.mpst_no='"+code+"' group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12)";
*/
				    	query=" select p.mac_name,p.mcity,left(p.mpst_no,12),sum(f.taxableM),sum(f.taxm),sum(f.taxableF),sum(f.taxF),sum(f.taxableE) from "+
				    	" (select sprt_cd,round(sum(sqty*stax_cd1),2) taxableM,round((sum(sqty*stax_cd1)*stax_cd2)/100,2) taxM,0 taxableF,0 taxF,0 taxableE,div_code from invsnd where sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ? and div_code<4 and stax_cd2<>0 and sale_type in (1,3) and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd "+
				    	" union all "+
				    	" select sprt_cd,0 taxableM,0 taxM,round(sum(sqty*stax_cd1),2) taxableF,round((sum(sqty*stax_cd1)*stax_cd2)/100,2) taxF,0 taxableE,div_code from invsnd where sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ?  and div_code<4 and stax_cd2<>0 and sale_type in (2,9) and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd "+
				    	" union all "+
				    	" select sprt_cd,0 taxableM,0 taxM,0 taxableF, 0 taxF,round(sum(sqty*stax_cd1),2) taxableE,div_code from invsnd where sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ?  and div_code<4 and stax_cd2=0  and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd) f,partyfst as p "+ 
				    	" where p.div_code=f.div_code  and p.depo_code=? and f.sprt_Cd=p.mac_code and p.mpst_no='"+code+"' group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12) " ;

				    
				    }    
				    
			    }

			    if (opt==2)
			    {
			    	if(doc_tp!=0)
				    {
/*					    query="select p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"sum(f.taxable1+f.taxable3),sum(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),sum(f.taxable2+f.taxable9),sum(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),sum(f.taxfree1+" +
								"f.taxfree2+f.taxfree3+f.taxfree9) " +
								"from invfst as f,partyfst as p where f.div_code=? and f.depo_code=? and f.doc_type in (60,61,62,72) " +
								"and  f.inv_date between ? and ? and f.party_code=p.mac_code and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=?  and p.depo_code=? group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12)";

*/				    
				    	query=" select p.mac_name,p.mcity,left(p.mpst_no,12),sum(f.taxableM),sum(f.taxm),sum(f.taxableF),sum(f.taxF),sum(f.taxableE) from "+
				    	" (select sprt_cd,round(sum(sqty*stax_cd1),2) taxableM,round((sum(sqty*stax_cd1)*stax_cd2)/100,2) taxM,0 taxableF,0 taxF,0 taxableE,div_code from invsnd where div_code=? and sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ? and stax_cd2<>0 and sale_type in (1,3) and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd "+
				    	" union all "+
				    	" select sprt_cd,0 taxableM,0 taxM,round(sum(sqty*stax_cd1),2) taxableF,round((sum(sqty*stax_cd1)*stax_cd2)/100,2) taxF,0 taxableE,div_code from invsnd where div_code=? and sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ? and stax_cd2<>0 and sale_type in (2,9) and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd "+
				    	" union all "+
				    	" select sprt_cd,0 taxableM,0 taxM,0 taxableF, 0 taxF,round(sum(sqty*stax_cd1),2) taxableE,div_code from invsnd where div_code=? and sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ? and stax_cd2=0  and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd) f,partyfst as p "+ 
				    	" where p.div_code=?  and p.depo_code=? and p.mac_code=f.sprt_cd group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12) " ;

				    
				    }    
	
				    if(doc_tp==0)
				    {
				    	
				    	query=" select p.mac_name,p.mcity,left(p.mpst_no,12),sum(f.taxableM),sum(f.taxm),sum(f.taxableF),sum(f.taxF),sum(f.taxableE) from "+
				    	" (select sprt_cd,round(sum(sqty*stax_cd1),2) taxableM,round((sum(sqty*stax_cd1)*stax_cd2)/100,2) taxM,0 taxableF,0 taxF,0 taxableE,div_code from invsnd where sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ? and div_code<4 and stax_cd2<>0 and sale_type in (1,3) and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd "+
				    	" union all "+
				    	" select sprt_cd,0 taxableM,0 taxM,round(sum(sqty*stax_cd1),2) taxableF,round((sum(sqty*stax_cd1)*stax_cd2)/100,2) taxF,0 taxableE,div_code from invsnd where sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ? and div_code<4 and stax_cd2<>0 and sale_type in (2,9) and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd "+
				    	" union all "+
				    	" select sprt_cd,0 taxableM,0 taxM,0 taxableF, 0 taxF,round(sum(sqty*stax_cd1),2) taxableE,div_code from invsnd where sdepo_code=? and sdoc_type in (60,61,62,72) "+
				    	" and sinv_dt between ? and ? and div_code<4 and stax_cd2=0  and ifnull(del_tag,'')<>'D'  "+
				    	" group by sprt_cd) f,partyfst as p "+ 
				    	" where p.div_code=f.div_code  and p.depo_code=? and p.mac_code=f.sprt_cd group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12) " ;

				    	
				    	
/*					    query="select p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"sum(f.taxable1+f.taxable3),sum(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),sum(f.taxable2+f.taxable9),sum(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),sum(f.taxfree1+" +
								"f.taxfree2+f.taxfree3+f.taxfree9) " +
								"from invfst as f,partyfst as p where f.depo_code=? and f.doc_type in (60,61,62,72) " +
								"and  f.inv_date between ? and ? and f.div_code < 4 and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=f.div_code  and p.depo_code=? and p.mac_code=f.party_code group by p.mpst_no order by p.mac_name,p.mcity,left(p.mpst_no,12)";
*/				    }    
				    
			    }
			    
			    
			    
			    st = con.prepareStatement(query);
			    
			    data=new ArrayList();
			    if (opt==1)
			    {
			    	if (doc_tp==0)
			    	{
			    		st.setInt(1, depo);
			    		st.setDate(2, sDate);
			    		st.setDate(3, eDate);

			    		st.setInt(4, depo);
			    		st.setDate(5, sDate);
			    		st.setDate(6, eDate);
			    		st.setInt(7, depo);
			    		st.setDate(8, sDate);
			    		st.setDate(9, eDate);

			    		st.setInt(10, depo);
			    	}
			    	else
			    	{
			    		st.setInt(1, div);
			    		st.setInt(2, depo);
			    		st.setDate(3, sDate);
			    		st.setDate(4, eDate);
			    		st.setInt(5, div);
			    		st.setInt(6, depo);
			    		st.setDate(7, sDate);
			    		st.setDate(8, eDate);
			    		st.setInt(9, div);
			    		st.setInt(10, depo);
			    		st.setDate(11, sDate);
			    		st.setDate(12, eDate);
			    		st.setInt(13, div);
			    		st.setInt(14, depo);
			    	}
			    }

			    if (opt==2)
			    {
			    	if (doc_tp==0)
			    	{
			    		st.setInt(1, depo);
			    		st.setDate(2, sDate);
			    		st.setDate(3, eDate);
			    		st.setInt(4, depo);
			    		st.setDate(5, sDate);
			    		st.setDate(6, eDate);
			    		st.setInt(7, depo);
			    		st.setDate(8, sDate);
			    		st.setDate(9, eDate);
			    		st.setInt(10, depo);
			    	}
			    	else
			    	{

			    		st.setInt(1, div);
			    		st.setInt(2, depo);
			    		st.setDate(3, sDate);
			    		st.setDate(4, eDate);
			    		st.setInt(5, div);
			    		st.setInt(6, depo);
			    		st.setDate(7, sDate);
			    		st.setDate(8, eDate);
			    		st.setInt(9, div);
			    		st.setInt(10, depo);
			    		st.setDate(11, sDate);
			    		st.setDate(12, eDate);
			    		st.setInt(13, div);
			    		st.setInt(14, depo);

			    		
			    	}
			    }

			    
			    
			    
			    rs=st.executeQuery();   
					   while(rs.next())
	            	{

	            		inv = new InvFstDto();
	            		inv.setParty_name(rs.getString(1));
	            		inv.setCity(rs.getString(2));
	            		inv.setTin_no(rs.getString(3));
	            		inv.setTaxable1(rs.getDouble(4));
	            		inv.setLtax1_amt(rs.getDouble(5));
	            		inv.setTaxable2(rs.getDouble(6));
	            		inv.setLtax2_amt(rs.getDouble(7));
	            		inv.setTaxfree1(rs.getDouble(8));
			    		inv.setDash(0);
	            		data.add(inv);
	            		taxable1+=rs.getDouble(4);
	            		taxamt1+=rs.getDouble(5);
	            		taxable2+=rs.getDouble(6);
	            		taxamt2+=rs.getDouble(7);
	            		taxfree+=rs.getDouble(8);
	            	}

          		inv = new InvFstDto();
          		inv.setTaxable1(taxable1);
          		inv.setLtax1_amt(taxamt1);
          		inv.setTaxable2(taxable2);
          		inv.setLtax2_amt(taxamt2);
          		inv.setTaxfree1(taxfree);
		    		inv.setDash(1);
		    		data.add(inv);

			    	
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error hai "+ee);
				ee.printStackTrace();}		   
		     
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
	 

	 public List CheckCN(int year,int depo,int div,Date sdate,Date edate,int doc_tp,String code,int opt)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   PreparedStatement st1 =null;
		   ResultSet rs= null; 
		   ResultSet rs1= null; 
		   InvFstDto inv=null;
		   String query=null;
		   String tp="";
		   double tot1=0.00;
		   boolean first=true;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());

		   
		     try{
			    con=ConnectionFactory.getConnection();

			    if (opt==1)
			    {
			    	query=" SELECT C.CN_NO,C.CN_DT,C.CN_PRT,C.CN_PRD ,P.PNAME,P.PACK,C.CN_BAT,C.CN_EXP,C.CN_QTY,C.CN_TYP, "+
	    			" C.INV_NO,C.INV_DT,C.INV_MNT,C.INV_PRT,C.INV_PRD,C.INV_BAT,C.INV_QTY,c.mfgval "+
	    			" FROM ( "+
	    			" SELECT * FROM ( "+
	    			" SELECT concat(i.sinv_yr,i.sinv_lo,'C',RIGHT(i.sinv_no,4)) CN_NO,I.SINV_DT CN_DT,I.SPRT_CD CN_PRT,I.SPRD_CD CN_PRD,I.SBATCH_NO CN_BAT,I.SEXP_DT CN_EXP,I.SQTY CN_QTY,I.STRN_TP CN_TYP,(i.sqty*i.srate_mfg) mfgval "+
	    			" FROM INVSND I "+
	    			" WHERE I.DIV_CODE = ? AND I.SDEPO_CODE = ? AND I.SINV_DT BETWEEN ? AND ? AND I.SDOC_TYPE = 41 AND ifnull(I.DEL_TAG,'') <> 'D' "+
	    			" AND I.SPRT_CD = ?) A "+
	    			" LEFT JOIN "+
	    			" (SELECT concat(i.sinv_yr,i.sinv_lo,RIGHT(i.sinv_no,4)) INV_NO,I.SINV_DT INV_DT,I.MNTH_CODE INV_MNT,I.SPRT_CD INV_PRT,I.SPRD_CD INV_PRD,I.SBATCH_NO INV_BAT,I.SQTY INV_QTY "+
	    			" FROM INVSND I "+
	    			" WHERE I.DIV_CODE = ? AND I.SDEPO_CODE = ? AND I.SDOC_TYPE IN (39,40) AND ifnull(I.DEL_TAG,'') <> 'D' "+
	    			" AND I.SPRT_CD = ? "+
	    			" ) B "+
	    			" ON "+
	    			" A.CN_PRT = B.INV_PRT AND A.CN_PRD = B.INV_PRD AND A.CN_BAT = B.INV_BAT) C, PRD P "+
	    			" WHERE P.DIV_CODE = ? "+
	    			" AND C.CN_PRD = P.PCODE ";
			    }
			    
			    if (opt==2)
			    {
			    	query=" SELECT C.CN_NO,C.CN_DT,C.CN_PRT,C.CN_PRD ,P.PNAME,P.PACK,C.CN_BAT,C.CN_EXP,C.CN_QTY,C.CN_TYP, "+
	    			" C.INV_NO,C.INV_DT,C.INV_MNT,C.INV_PRT,C.INV_PRD,C.INV_BAT,C.INV_QTY,c.mfgval "+
	    			" FROM ( "+
	    			" SELECT * FROM ( "+
	    			" SELECT concat(i.sinv_yr,i.sinv_lo,'C',RIGHT(i.sinv_no,4)) CN_NO,I.SINV_DT CN_DT,I.SPRT_CD CN_PRT,I.SPRD_CD CN_PRD,I.SBATCH_NO CN_BAT,I.SEXP_DT CN_EXP,I.SQTY CN_QTY,I.STRN_TP CN_TYP,(i.sqty*i.srate_mfg) mfgval "+
	    			" FROM INVSND I "+
	    			" WHERE I.DIV_CODE = ? AND I.SDEPO_CODE = ? AND I.SINV_DT BETWEEN ? AND ? AND I.SDOC_TYPE = 41 AND ifnull(I.DEL_TAG,'') <> 'D' "+
	    			" ) A "+
	    			" LEFT JOIN "+
	    			" (SELECT concat(i.sinv_yr,i.sinv_lo,RIGHT(i.sinv_no,4)) INV_NO,I.SINV_DT INV_DT,I.MNTH_CODE INV_MNT,I.SPRT_CD INV_PRT,I.SPRD_CD INV_PRD,I.SBATCH_NO INV_BAT,I.SQTY INV_QTY "+
	    			" FROM INVSND I "+
	    			" WHERE I.DIV_CODE = ? AND I.SDEPO_CODE = ? AND I.SDOC_TYPE IN (39,40) AND ifnull(I.DEL_TAG,'') <> 'D' "+
	    			"  "+
	    			" ) B "+
	    			" ON "+
	    			" A.CN_PRT = B.INV_PRT AND A.CN_PRD = B.INV_PRD AND A.CN_BAT = B.INV_BAT) C, PRD P "+
	    			" WHERE P.DIV_CODE = ? "+
	    			" AND C.CN_PRD = P.PCODE  order by c.cn_prt,C.CN_NO ";
			    }
		    
			    String party="select mac_name,mcity from partyfst where div_code=? and depo_code=? and mac_code=? ";
			    
			    st = con.prepareStatement(query);
			    st1 = con.prepareStatement(party);
			    
			    data=new ArrayList();
			      if(opt==1)
			      {
					   st.setInt(1, div);
					   st.setInt(2, depo);
					   st.setDate(3, sDate);
					   st.setDate(4, eDate);
					   st.setString(5, code);
					   st.setInt(6, div);
					   st.setInt(7, depo);
					   st.setString(8, code);
					   st.setInt(9, div);
			      }
			      else
			      {
					   st.setInt(1, div);
					   st.setInt(2, depo);
					   st.setDate(3, sDate);
					   st.setDate(4, eDate);
					   st.setInt(5, div);
					   st.setInt(6, depo);
					   st.setInt(7, div);
			    	  
			      }
					   rs=st.executeQuery();   

					   System.out.println("yehae per 1111");
					while(rs.next())
	            	{
						    

				    	inv = new InvFstDto();
		    			inv.setCn_no(rs.getString(1));
			    		if (rs.getDate(2)!=null)
			    			inv.setCn_date(rs.getDate(2));
			    		inv.setRm_name(rs.getString(5));  /// pname
			    		inv.setMr_name(rs.getString(6));  /// pack
			    		inv.setParty_name(rs.getString(7));  /// batch
			    		inv.setCity(rs.getString(8));  /// expiry
			    		inv.setCases(rs.getInt(9));  /// qty
			    		inv.setCst_reim(rs.getString(10));  // type
			    		inv.setInv_type(rs.getString(11));
			    		if (rs.getDate(12)!=null)
			    			inv.setInv_date(rs.getDate(12));
			    		inv.setDue_days(rs.getInt(17));  /// qty
			    		inv.setParty_code(rs.getString(3)); // partycode 
			    		inv.setMfg_amt(rs.getDouble(18));

			    		inv.setDash(0);
	            		data.add(inv);
	            	}
			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error hai "+ee);
				ee.printStackTrace();}		   
		     
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
	 
	 
	 public HashMap OthRepo57Detail(int year,int depo,int div,Date sdate,Date edate,int doc_tp,String code,int opt)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   ResultSet rs1= null; 
		   InvFstDto inv=null;
		   String query=null;
		   String tp="";
		   double tot1=0.00;
		   boolean first=true;
		   double taxable1=0.00;
		   double taxable2=0.00;
		   double taxamt1=0.00;
		   double taxamt2=0.00;
		   double taxfree=0.00;
		   double taxable10=0.00;
		   double taxamt10=0.00;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   HashMap vatmap=new HashMap();
		   
		     try{
			    con=ConnectionFactory.getConnection();

					    query="select p.mac_name,p.mcity,left(p.mpst_no,12), "+
								"sum(f.taxable1+f.taxable3),sum(f.ltax1_amt+f.ltax3_amt+f.freetax1+f.freetax3),sum(f.taxable2+f.taxable9),sum(f.ltax2_amt+f.ltax9_amt+f.freetax2+f.freetax9),sum(f.taxfree1+" +
								"f.taxfree2+f.taxfree3+f.taxfree9+f.taxfree10),sum(f.taxable10),sum(ltax10_amt),concat(f.inv_yr,f.inv_lo,right(f.inv_no,5)) invno,f.inv_date,p.memail,p.mac_code,f.div_code   " +
								"from invfst as f,partyfst as p where f.depo_code=? and f.doc_type in (39,40) " +
								"and  f.inv_date between ? and ? and f.div_code < 4 and ifnull(f.del_tag,'')<>'D' " +
								" and p.div_code=f.div_code  and p.depo_code=? and p.mac_code=f.party_code group by p.mpst_no,f.div_Code,f.inv_no,f.inv_date ";
			    
			    
			    st = con.prepareStatement(query);
			    
			    data=new ArrayList();
			    
					   st.setInt(1, depo);
					   st.setDate(2, sDate);
					   st.setDate(3, eDate);
					   st.setInt(4, depo);

					   rs=st.executeQuery();
					   String pcode="";
					   boolean second=true;
					   while(rs.next())
	            	{

					   if (first)
					   {
						   code=rs.getString(3);
						   pcode=rs.getString(14);
						   first=false;
					   }
					   if (!code.equalsIgnoreCase(rs.getString(3)))
					   {
						   inv = new InvFstDto();
						   inv.setTaxable1(taxable1);
						   inv.setLtax1_amt(taxamt1);
						   inv.setTaxable2(taxable2);
						   inv.setLtax2_amt(taxamt2);
						   inv.setTaxfree1(taxfree);
						   inv.setTaxable10(taxable10);
						   inv.setLtax10_amt(taxamt10);
						   inv.setDash(1);
						   data.add(inv);


						   vatmap.put(pcode, data);
						   data=new ArrayList();
						   code=rs.getString(3);
						   pcode=rs.getString(14);
						   taxable1=0.00;
						   taxamt1=0.00;
						   taxable2=0.00;
						   taxamt2=0.00;
						   taxable10=0.00;
						   taxamt10=0.00;
						   taxfree=0.00;
						   second=true;

					   }
	            		inv = new InvFstDto();
	            		inv.setParty_name(rs.getString(1));
	            		inv.setCity(rs.getString(2));
	            		inv.setTin_no(rs.getString(3));
	            		inv.setTaxable1(rs.getDouble(4));
	            		inv.setLtax1_amt(rs.getDouble(5));
	            		inv.setTaxable2(rs.getDouble(6));
	            		inv.setLtax2_amt(rs.getDouble(7));
	            		inv.setTaxfree1(rs.getDouble(8));
	            		inv.setTaxable10(rs.getDouble(9));
	            		inv.setLtax10_amt(rs.getDouble(10));
	            		inv.setInv_lo(rs.getString(11));
	            		inv.setInv_date(rs.getDate(12));
	            		inv.setEmail(rs.getString(13));
	            		
			    		inv.setDash(0);
	            		data.add(inv);
	            		if (second)
	            		{
		            		if (rs.getInt(15)==div)
		            		{
		            			pcode=rs.getString(14);
		            			second=false;
		            		}
	            			
	            		}
	            		taxable1+=rs.getDouble(4);
	            		taxamt1+=rs.getDouble(5);
	            		taxable2+=rs.getDouble(6);
	            		taxamt2+=rs.getDouble(7);
	            		taxfree+=rs.getDouble(8);
	            		taxable10+=rs.getDouble(9);
	            		taxamt10+=rs.getDouble(10);
	            	}

					   inv = new InvFstDto();
	            	   inv.setTin_no("Grand Total");
					   inv.setTaxable1(taxable1);
					   inv.setLtax1_amt(taxamt1);
					   inv.setTaxable2(taxable2);
					   inv.setLtax2_amt(taxamt2);
					   inv.setTaxfree1(taxfree);
					   inv.setTaxable10(taxable10);
					   inv.setLtax10_amt(taxamt10);
					   inv.setDash(1);
					   data.add(inv);

					   vatmap.put(pcode, data);

			    rs.close();
			    st.close();
			   }
			catch(Exception ee){System.out.println("error hai "+ee);
				ee.printStackTrace();}		   
		     
			finally {
				try {
					    
		             if(rs != null){rs.close();}
		             if(st != null){st.close();}
		             if(con != null){con.close();}
				} catch (SQLException ee) {
					System.out.println("-------------Exception in SalRegDAO.Connection.close "+ee);
				  }
			}
			
			return vatmap;
	   }
	 

	 public List vatTaxSale(String smon,int year,int depo,int div,Date sdate,Date edate,int doc_tp)
	   {
		  
		   List data =null;
		   Connection con=null;
		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvFstDto inv=null;
		   String query=null;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   
		   try{
			    con=ConnectionFactory.getConnection();
			   

			      query="select concat(f.inv_lo,'-',f.inv_no),f.inv_date,p.mac_name,p.mcity,left(p.mpst_no,12),"	+
	    		  "round(sum(f.taxable1)),round(sum(f.tax5)),round(sum(f.taxable2)),round(sum(f.tax14)),round(sum(f.taxable3)),round(sum(f.tax15)),round(sum(f.oth_adj)),round(sum(f.bill_amt)),f.party_code "+
	    		  "from invfst f , partyfst p  where f.div_code=? and f.depo_code=? and f.doc_type between  ? and ? "+
	    		  "and  f.inv_date between ? and ? and  ifnull(f.del_tag,'')<>'D' "+  
	    		  " and f.party_code=p.mac_Code and  f.party_code=p.mac_code and p.div_code=?  and p.depo_code=? group by f.party_code order by p.mac_name ; ";

				st = con.prepareStatement(query);
			    data = new ArrayList();
			     
		    	 
		    		st.setInt(1, div);
		    		st.setInt(2, depo);
		    		st.setInt(3, doc_tp);
		    		st.setInt(4, (doc_tp+9));
		    		st.setDate(5, sDate);
		    		st.setDate(6, eDate);
		    		st.setInt(7, div);
		    		st.setInt(8, depo);
			    	 
			    	rs=st.executeQuery();
			    	while(rs.next())
			    	{
			    		if(rs.getDouble(6)> 0)
			    		{
			    			inv = new InvFstDto();
			    			inv.setParty_name(rs.getString(3));
			    			inv.setCity(rs.getString(4));
			    			inv.setTin_no(rs.getString(5));
			    			inv.setTax_1(5);
			    			inv.setGross5(rs.getDouble(6));
			    			inv.setVat5(rs.getDouble(7));
			    			data.add(inv);
			    		}
			    		if(rs.getDouble(8)> 0)
			    		{
			    			inv = new InvFstDto();
			    			inv.setParty_name(rs.getString(3));
			    			inv.setCity(rs.getString(4));
			    			inv.setTin_no(rs.getString(5));
			    			inv.setTax_1(14);
			    			inv.setGross5(rs.getDouble(8));
			    			inv.setVat5(rs.getDouble(9));
			    			data.add(inv);
			    			
			    		}
			    		if(rs.getDouble(10)> 0)
			    		{
			    			inv = new InvFstDto();
			    			inv.setParty_name(rs.getString(3));
			    			inv.setCity(rs.getString(4));
			    			inv.setTin_no(rs.getString(5));
			    			inv.setTax_1(15);
			    			inv.setGross5(rs.getDouble(10));
			    			inv.setVat5(rs.getDouble(11));
			    			data.add(inv);
			    			
			    		}

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
					System.out.println("-------------Exception in SaleTax.Connection.close "+ee);
				  }
			}
			
			return data;
	   }
	 public List prtwiseSale(int year,int depo,int div,Date sdate,Date edate,int doc_tp,int code,ArrayList prdList)
	   {
		  
		   List data =null;
		   Connection con=null;


		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvViewDto inv=null;
		   String query=null;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   
		   StringBuffer prd = new StringBuffer(" in (");
		   int size= prdList.size()-1;
		   if(size<0)
			   size=0;
		   int i=0;
		   for (i=0;i<size;i++)
		   {
			   prd.append(Integer.parseInt(prdList.get(i).toString())+",");
		   }
		   prd.append(Integer.parseInt(prdList.get(i).toString())+")");	   
		   
		   System.out.println("prd "+prd);
		   
		   try{
			    con=ConnectionFactory.getConnection();
			   if(div==99) // group wise
			   {
				   if(code!=0)
				   {
					   query="select a.sprt_Cd,a.mac_name_hindi,a.mcity_hindi,a.mobile,a.memail,a.sinv_no,a.sinv_dt,a.category," +
							   "p.pname_hindi,a.qty,a.weight,a.sprd_cd,a.mark_no,a.floor_no,a.block_no from "+ 
							   "(select s.sdoc_type,s.sprt_Cd,s.sprd_Cd,b.mac_name_hindi,b.mcity_hindi,b.mobile,b.memail,s.sinv_no,s.sinv_dt," +
							   "(sqty-ifnull(issue_qty,0)) qty,s.srate_net,s.samnt,s.category,s.weight,s.mark_no,s.floor_no,s.block_no    from invsnd s,accountmaster b "+ 
							   "where s.sdepo_code=? and s.sdoc_type=? and s.fin_year=? and s.sinv_dt between ? and ? and s.sprt_cd=? and s.spd_gp "+prd+"  and s.del_tag<>'D' "+
							   "and  b.depo_Code=? and b.account_no=s.sprt_cd ) a,prd p "+
							   "where p.depo_code=? and p.pcode=a.sprd_cd and a.qty > 0 "+
							   "order by a.sprt_cd,a.sinv_no,a.sinv_dt,a.sprd_cd";
				   }
				   else
				   {

					   query="select a.sprt_Cd,a.mac_name_hindi,a.mcity_hindi,a.mobile,a.memail,a.sinv_no,a.sinv_dt,a.category," +
							   "p.pname_hindi,a.qty,a.weight,a.sprd_cd,a.mark_no,a.floor_no,a.block_no from "+ 
							   "(select s.sdoc_type,s.sprt_Cd,s.sprd_Cd,b.mac_name_hindi,b.mcity_hindi,b.mobile,b.memail,s.sinv_no,s.sinv_dt," +
							   "(sqty-ifnull(issue_qty,0)) qty,s.srate_net,s.samnt,s.category,s.weight,s.mark_no,s.floor_no,s.block_no   from invsnd s,accountmaster b "+ 
							   "where s.sdepo_code=? and s.sdoc_type=? and s.fin_year=? and s.sinv_dt between ? and ? and s.spd_gp "+prd+"  and s.del_tag<>'D' "+
							   "and  b.depo_Code=? and b.account_no=s.sprt_cd ) a,prd p "+
							   "where p.depo_code=? and p.pcode=a.sprd_cd  and a.qty > 0 "+
							   "order by a.sprt_cd,a.sinv_no,a.sinv_dt,a.sprd_cd";
				   }
				   
			   }
			   else
			   {
				   if(code!=0)
				   {
					   query="select a.sprt_Cd,a.mac_name_hindi,a.mcity_hindi,a.mobile,a.memail,a.sinv_no,a.sinv_dt,a.category," +
							   "p.pname_hindi,a.qty,a.weight,a.sprd_cd,a.mark_no,a.floor_no,a.block_no from "+ 
							   "(select s.sdoc_type,s.sprt_Cd,s.sprd_Cd,b.mac_name_hindi,b.mcity_hindi,b.mobile,b.memail,s.sinv_no,s.sinv_dt," +
							   "(sqty-ifnull(issue_qty,0)) qty,s.srate_net,s.samnt,s.category,s.weight,s.mark_no,s.floor_no,s.block_no    from invsnd s,accountmaster b "+ 
							   "where s.sdepo_code=? and s.sdoc_type=? and s.fin_year=? and s.sinv_dt between ? and ? and s.sprt_cd=? and s.sprd_cd "+prd+"  and s.del_tag<>'D' "+
							   "and  b.depo_Code=? and b.account_no=s.sprt_cd ) a,prd p "+
							   "where p.depo_code=? and p.pcode=a.sprd_cd and a.qty > 0 "+
							   "order by a.sprt_cd,a.sinv_no,a.sinv_dt,a.sprd_cd";
				   }
				   else
				   {

					   query="select a.sprt_Cd,a.mac_name_hindi,a.mcity_hindi,a.mobile,a.memail,a.sinv_no,a.sinv_dt,a.category," +
							   "p.pname_hindi,a.qty,a.weight,a.sprd_cd,a.mark_no,a.floor_no,a.block_no from "+ 
							   "(select s.sdoc_type,s.sprt_Cd,s.sprd_Cd,b.mac_name_hindi,b.mcity_hindi,b.mobile,b.memail,s.sinv_no,s.sinv_dt," +
							   "(sqty-ifnull(issue_qty,0)) qty,s.srate_net,s.samnt,s.category,s.weight,s.mark_no,s.floor_no,s.block_no   from invsnd s,accountmaster b "+ 
							   "where s.sdepo_code=? and s.sdoc_type=? and s.fin_year=? and s.sinv_dt between ? and ? and s.sprd_cd "+prd+"  and s.del_tag<>'D' "+
							   "and  b.depo_Code=? and b.account_no=s.sprt_cd ) a,prd p "+
							   "where p.depo_code=? and p.pcode=a.sprd_cd  and a.qty > 0 "+
							   "order by a.sprt_cd,a.sinv_no,a.sinv_dt,a.sprd_cd";
				   }
			   }
			    	
			 
				st = con.prepareStatement(query);

			    data = new ArrayList();
			     
		    	 
			    if(code>0)
			    {
			    	st.setInt(1, depo);
			    	st.setInt(2, doc_tp);
			    	st.setInt(3, year);
			    	st.setDate(4, sDate);
			    	st.setDate(5, eDate);
			    	st.setInt(6, code);
			    	st.setInt(7, depo);
			    	st.setInt(8, depo);
			    }
			    else
			    {
			    	st.setInt(1, depo);
			    	st.setInt(2, doc_tp);
			    	st.setInt(3, year);
			    	st.setDate(4, sDate);
			    	st.setDate(5, eDate);
			    	st.setInt(6, depo);
			    	st.setInt(7, depo);

			    }

			    rs=st.executeQuery();
			    double amount=0.00;
			    boolean first=true;
			    int partyCode=0;
			    int qty=0;
			    while(rs.next())
			    {
			    	if(first)
			    	{
			    		partyCode=rs.getInt(1);
			    		first=false;
			    	}
			    	if(partyCode!=rs.getInt(1))
			    	{
				    	inv = new InvViewDto();
				    	inv.setMac_name_hindi("Total");
				    	inv.setSqty(qty);
				    	inv.setWeight(amount);
				    	inv.setDash(1);
				    	data.add(inv);
				    	qty=0;
				    	amount=0.00;
				    	partyCode=rs.getInt(1);
			    	}
			    	inv = new InvViewDto();
			    	inv.setMac_code(rs.getString(1));
			    	inv.setMac_name_hindi(rs.getString(2));
			    	inv.setMcity_hindi(rs.getString(3));
			    	inv.setMobile(rs.getString(4));
			    	inv.setInv_no(rs.getString(6));
			    	inv.setInv_date(rs.getDate(7));
			    	inv.setCategory_hindi(rs.getString(8));
			    	inv.setPname_hindi(rs.getString(9));
			    	inv.setSqty(rs.getInt(10));
			    	inv.setWeight(rs.getDouble(11));
			    	inv.setMark_no(rs.getString(13));
			    	inv.setFloor_no(rs.getString(14));
			    	inv.setBlock_no(rs.getString(15));
			    	inv.setDash(0);
			    	qty+=rs.getInt(10);
			    	amount+=rs.getDouble(11);
			    	data.add(inv);
			    }

			    	inv = new InvViewDto();
			    	inv.setMac_name_hindi("Total");
			    	inv.setSqty(qty);
			    	inv.setWeight(amount);
			    	inv.setDash(1);
			    	data.add(inv);
		    		
	
			    	

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
					System.out.println("-------------Exception in prtwiseSale.Connection.close "+ee);
				  }
			}
			
			return data;
	   }


	 
	 public List ItemwiseSale(int year,int depo,int div,Date sdate,Date edate,int doc_tp,int code,ArrayList prtList)
	   {
		  
		   List data =null;
		   Connection con=null;


		   PreparedStatement st =null;
		   ResultSet rs= null; 
		   InvViewDto inv=null;
		   String query=null;
		   java.sql.Date sDate = new java.sql.Date(sdate.getTime());
		   java.sql.Date eDate = new java.sql.Date(edate.getTime());
		   
		   StringBuffer prt = new StringBuffer(" in (");
		   int size= prtList.size()-1;
		   int wdiv=div;
		   if(div==99)
			   div=BaseClass.loginDt.getDiv_code();
		   if(size<0)
			   size=0;
		   int i=0;
		   for (i=0;i<size;i++)
		   {
			  
			   prt.append("'"+prtList.get(i).toString()+"',");
		   }
		   		prt.append("'"+prtList.get(i).toString()+"')");	   
		   		
		   		System.out.println("prt "+prt);
		   
		   try{
			    con=ConnectionFactory.getConnection();
			   

			    if(code!=0)
			    {
			    	if(wdiv==99)  // gor selective group
			    	{
			    		
			    		System.out.println("1......");
			    		query="select a.sprt_Cd,a.mac_name_hindi,a.mcity_hindi,a.mobile,a.memail,a.sinv_no,a.sinv_dt,a.category," +
			    				"p.pname_hindi,a.qty,a.weight,a.sprd_cd,a.mark_no,a.floor_no,a.block_no from "+ 
			    				"(select s.sdoc_type,s.sprt_Cd,s.sprd_Cd,b.mac_name_hindi,b.mcity_hindi,b.mobile,b.memail,s.sinv_no,s.sinv_dt," +
			    				"(sqty-ifnull(issue_qty,0)) qty,s.srate_net,s.samnt,s.category,s.weight,s.mark_no,s.floor_no,s.block_no    from invsnd s,accountmaster b "+ 
			    				"where s.sdepo_code=? and s.div_Code=? and s.sdoc_type=? and s.fin_year =? and s.sinv_dt between ? and ? and s.spd_gp=? and s.sprt_cd "+prt+"  and s.del_tag<>'D' "+
			    				"and  b.depo_Code=? and b.div_Code=s.div_code and b.account_no=s.sprt_cd ) a,prd p "+
			    				"where p.depo_code=? and p.div_code= ? and p.pcode=a.sprd_cd and a.qty> 0 "+
			    				"order by a.sprd_cd,a.sprt_cd,a.sinv_no,a.sinv_dt ";
			    		
			    	}
			    	else
			    	{
			    		System.out.println("2......");
			    		query="select a.sprt_Cd,a.mac_name_hindi,a.mcity_hindi,a.mobile,a.memail,a.sinv_no,a.sinv_dt,a.category," +
			    				"p.pname_hindi,a.qty,a.weight,a.sprd_cd,a.mark_no,a.floor_no,a.block_no from "+ 
			    				"(select s.sdoc_type,s.sprt_Cd,s.sprd_Cd,b.mac_name_hindi,b.mcity_hindi,b.mobile,b.memail,s.sinv_no,s.sinv_dt," +
			    				"(sqty-ifnull(issue_qty,0)) qty,s.srate_net,s.samnt,s.category,s.weight,s.mark_no,s.floor_no,s.block_no    from invsnd s,accountmaster b "+ 
			    				"where s.sdepo_code=? and s.div_Code=? and s.sdoc_type=? and s.fin_year =? and s.sinv_dt between ? and ? and s.sprd_cd=? and s.sprt_cd "+prt+"  and s.del_tag<>'D' "+
			    				"and  b.depo_Code=? and b.div_code=s.div_code and b.account_no=s.sprt_cd ) a,prd p "+
			    				"where p.depo_code=? and p.div_code=? and p.pcode=a.sprd_cd and a.qty> 0 "+
			    				"order by a.sprd_cd,a.sprt_cd,a.sinv_no,a.sinv_dt,a.sprt_Cd ";
			    	}
			    }
			    else
			    {
		    		System.out.println("3......");

					query="select a.sprt_Cd,a.mac_name_hindi,a.mcity_hindi,a.mobile,a.memail,a.sinv_no,a.sinv_dt,a.category," +
					"p.pname_hindi,a.qty,a.weight,a.sprd_cd,a.mark_no,a.floor_no,a.block_no from "+ 
					"(select s.sdoc_type,s.sprt_Cd,s.sprd_Cd,b.mac_name_hindi,b.mcity_hindi,b.mobile,b.memail,s.sinv_no,s.sinv_dt," +
					"(sqty-ifnull(issue_qty,0)) qty,s.srate_net,s.samnt,s.category,s.weight,s.mark_no,s.floor_no,s.block_no   from invsnd s,accountmaster b "+ 
					"where s.sdepo_code=? and s.div_Code=? and s.sdoc_type=? and s.fin_year =? and s.sinv_dt between ? and ? and s.sprt_cd "+prt+"  and s.del_tag<>'D' "+
					"and  b.depo_Code=? and b.div_code=s.div_code and b.account_no=s.sprt_cd ) a,prd p "+
					"where p.depo_code=? and p.div_code=? and p.pcode=a.sprd_cd and a.qty> 0 "+
					"order by a.sprd_cd,a.sprt_cd,a.sinv_no,a.sinv_dt ";
			    }

			    
	
				st = con.prepareStatement(query);

			    data = new ArrayList();
			     
		    	 
			    if(code!=0)
			    {
			    	st.setInt(1, depo);
			    	st.setInt(2, div);
			    	st.setInt(3, doc_tp);
			    	st.setInt(4, year);
			    	st.setDate(5, sDate);
			    	st.setDate(6, eDate);
			    	st.setInt(7, code);
			    	st.setInt(8, depo);
			    	st.setInt(9, depo);
			    	st.setInt(10, div);

			    }
			    else
			    {
			    	st.setInt(1, depo);
			    	st.setInt(2, div);
			    	st.setInt(3, doc_tp);
			    	st.setInt(4, year);
			    	st.setDate(5, sDate);
			    	st.setDate(6, eDate);
			    	st.setInt(7, depo);
			    	st.setInt(8, depo);
			    	st.setInt(9, div);

			    }

			    rs=st.executeQuery();
			    double amount=0.00;
			    boolean first=true;
			    int productCode=0;
			    int qty=0;
			    while(rs.next())
			    {
			    	if(first)
			    	{
			    		productCode=rs.getInt(12);
			    		first=false;
			    	}
			    	if(productCode!=rs.getInt(12))
			    	{
				    	inv = new InvViewDto();
				    	inv.setMac_name_hindi("Total");
				    	inv.setSqty(qty);
				    	inv.setWeight(amount);
				    	inv.setDash(1);
				    	data.add(inv);
				    	qty=0;
				    	amount=0.00;
				    	productCode=rs.getInt(12);
			    	}
			    	inv = new InvViewDto();
			    	inv.setMac_code(rs.getString(1));
			    	inv.setMac_name_hindi(rs.getString(2));
			    	inv.setMcity_hindi(rs.getString(3));
			    	inv.setMobile(rs.getString(4));
			    	inv.setInv_no(rs.getString(6));
			    	inv.setInv_date(rs.getDate(7));
			    	inv.setCategory_hindi(rs.getString(8));
			    	inv.setPname_hindi(rs.getString(9));
			    	inv.setSqty(rs.getInt(10));
			    	inv.setWeight(rs.getDouble(11));
			    	inv.setMark_no(rs.getString(13));
			    	inv.setFloor_no(rs.getString(14));
			    	inv.setBlock_no(rs.getString(15));
			    	inv.setDash(0);
			    	qty+=rs.getInt(10);
			    	amount+=rs.getDouble(11);
			    	data.add(inv);
			    }

			    	inv = new InvViewDto();
			    	inv.setMac_name_hindi("Total");
			    	inv.setSqty(qty);
			    	inv.setWeight(amount);
			    	inv.setDash(1);
			    	data.add(inv);
		    		

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
					System.out.println("-------------Exception in ItemwiseSale.Connection.close "+ee);
				  }
			}
			
			return data;
	   }
	 
}
