package com.coldstorage.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.StckprnDto;
import com.coldstorage.dto.StkledgerDto;
 
 

public class StckledgerDao 
{

	 public ArrayList stckPrint(String mon,int year,int depo,int div,Date sdate,Date edate,ArrayList Prd,int saletype,GroupDto cmp)
	   {
		  
		   Connection con=null;
		   PreparedStatement ps=null;
		   ResultSet rs= null;

		   String prdfile="prd";
		   if (year==2020)
			   prdfile="prd2020";
		   StckprnDto stk =null;
		   String query=null;
		    
		   ArrayList data=null;
		   

		     StringBuffer pcodein= new StringBuffer(" in (");
			 int q=0;
			 
			    int z=Prd.size()-1;
			    for(q=0;q<z;q++)
			    {
			    	pcodein.append((Integer) Prd.get(q)+",");
			    	 
			    }
			    	pcodein.append((Integer) Prd.get(q)+")");
		   
		   
		   try{
			    con=ConnectionFactory.getConnection();
			    con.setAutoCommit(false);
 
			    query ="select  p.pcode,p.pname,p.pack,p.prd_opng opng,rcp,sr,sal,trf,p.pur_rt1,opn,p.net_rt1 from "+prdfile +" p LEFT JOIN  "+
			    "(select  s.sprd_Cd,ifnull(sum(rcp),0) rcp,ifnull(sum(sr),0) sr,ifnull(sum(sal),0) sal,ifnull(sum(trf),0) trf,ifnull(sum(opn),0) opn from   "+
			    "(select  sprd_Cd,0 opng,sum(sqty) rcp,0 sr,0 sal,0 trf,0 opn from invsnd  where fin_year=? and div_Code=? and sdepo_code=? and sdoc_type =? "+ 
				"and sinv_dt between ? and ? and ifnull(del_Tag,'')<>'D' group by sprd_cd "+
				"union all "+
				"select  sprd_Cd,0 opng,sum(sqty)*-1 rcp,0 sr,0 sal,0 trf,0 opn from invsnd  where fin_year=? and div_Code=? and sdepo_code=? and sdoc_type =? "+ 
				"and sinv_dt between ? and ? and ifnull(del_Tag,'')<>'D'  group by sprd_cd "+
				"union all "+
				"select  sprd_Cd,0 opng,0 rcp,sum(sqty) sr,0 sal,0 trf,0 opn from invsnd  where fin_year=? and div_Code=? and sdepo_code=? and sdoc_type = ? "+ 
				"and sinv_dt between ? and ? and ifnull(del_Tag,'')<>'D'  group by sprd_cd "+
				"union all "+
				"select  sprd_Cd,0 opng,0 rcp,0 sr,round(sum(if(ifnull(sdel_Tg,'F')='H',(sqty/2),sqty)),2) sal,0 trf,0 opn from invsnd  where fin_year=? and div_Code=? and sdepo_code=? and sdoc_type = ? "+ 
				"and sinv_dt between ? and ? and ifnull(del_Tag,'')<>'D'  group by sprd_cd "+
				"union all "+
				"select  sprd_Cd,0 opng,0 rcp,0 sr,0 sal,sum(sqty) trf,0 opn from invsnd  where fin_year=? and div_Code=? and sdepo_code=? and sdoc_type = ? "+ 
				"and sinv_dt between ? and ? and ifnull(del_Tag,'')<>'D'   group by sprd_cd " +
				"union all "+
				"select  sprd_Cd,0 opng,0 rcp,0 sr,0 sal,0 trf,round(sum(if(sdoc_type in (60,51),sqty,if(ifnull(sdel_Tg,'F')='H',(sqty/2)*-1,sqty*-1))),2) opn from invsnd  where fin_year=? and div_Code=? and sdepo_code=? "+  
				"and sinv_dt <  ?  and ifnull(del_tag,'')<>'D' group by sprd_cd ) s group by sprd_cd) s "+
				"ON  s.sprd_Cd=p.pcode where p.div_code=? and p.depo_code=? and p.pcode "+pcodein+"  order by pname " ;
			    
			    
		    
			    
			    ps= con.prepareStatement(query);
			   

			    
			    data = new ArrayList();
            	ps.setInt(1, year);
            	ps.setInt(2, div);
            	ps.setInt(3,depo);
            	ps.setInt(4, 60); // purchase
            	ps.setDate(5, new java.sql.Date(sdate.getTime()));
            	ps.setDate(6, new java.sql.Date(edate.getTime()));
            	ps.setInt(7, year);
            	ps.setInt(8, div);
            	ps.setInt(9,depo);
            	ps.setInt(10, 70); // return doc
            	ps.setDate(11, new java.sql.Date(sdate.getTime()));
            	ps.setDate(12, new java.sql.Date(edate.getTime()));
            	ps.setInt(13, year);
            	ps.setInt(14, div);
            	ps.setInt(15,depo);
            	ps.setInt(16, 51); // cr doc
            	ps.setDate(17, new java.sql.Date(sdate.getTime()));
            	ps.setDate(18, new java.sql.Date(edate.getTime()));
            	ps.setInt(19, year);
            	ps.setInt(20, div);
            	ps.setInt(21,depo);
            	ps.setInt(22, 40); // sales doc
            	ps.setDate(23, new java.sql.Date(sdate.getTime()));
            	ps.setDate(24, new java.sql.Date(edate.getTime()));
            	ps.setInt(25, year);
            	ps.setInt(26, div);
            	ps.setInt(27,depo);
            	ps.setInt(28, 0); // trf doc 
            	ps.setDate(29, new java.sql.Date(sdate.getTime()));
            	ps.setDate(30, new java.sql.Date(edate.getTime()));
            	ps.setInt(31, year);
            	ps.setInt(32, div);
            	ps.setInt(33,depo);
            	ps.setDate(34, new java.sql.Date(sdate.getTime()));
            	ps.setInt(35,div);
            	ps.setInt(36,depo);

            	rs = ps.executeQuery();
            	double clos=0;
            	double totstock=0.00;
            	double totstocknet=0.00;
            	double open=0.00;
            	while(rs.next())
            	{
            		if ((rs.getInt(4)+rs.getDouble(10)+rs.getInt(5)+rs.getInt(6)+rs.getDouble(7)+rs.getInt(8))>0)
            		{
            			open=rs.getDouble(4)+rs.getDouble(10);
            			
            			// SEPTEMBER
 /*                  		if(rs.getInt(1)==778 || rs.getInt(1)==779)
                			open=open-15;
                		else if(rs.getInt(1)==785 || rs.getInt(1)==786)
                			open=open-25;
 */
                   		// OCTOBER
/*                   		if(rs.getInt(1)==778 || rs.getInt(1)==779)
                			open=open-17;
                		else if(rs.getInt(1)==785 || rs.getInt(1)==786)
                			open=open-27;
*/            			
  
            			// FEBRUARY 2021
/*                   		if(rs.getInt(1)==778 || rs.getInt(1)==779)
                			open=open-15;
                		else if(rs.getInt(1)==785 || rs.getInt(1)==786)
                			open=open-50;
*/ 
            			// MARCH 2021
/*                   		if(rs.getInt(1)==778 || rs.getInt(1)==779)
                			open=open-25;
                		else if(rs.getInt(1)==785 || rs.getInt(1)==786)
                			open=open-70;
*/ 
            			// april/may/june 2021
                   		if(rs.getInt(1)==778 || rs.getInt(1)==779)
                			open=open-85;
                		else if(rs.getInt(1)==785 || rs.getInt(1)==786)
                			open=open-145;

                   		
            			stk = new StckprnDto();
            			stk.setCode(rs.getInt(1));
            			stk.setProduct(rs.getString(2));
            			stk.setPacking(rs.getString(3));
            			stk.setOpenqty(open);
            			stk.setReceipt(rs.getInt(5));
            			stk.setSalereturn(rs.getInt(6));
            			stk.setSaleqty(rs.getDouble(7));
            			stk.setTrf(rs.getInt(8));
            			clos=open+rs.getInt(5)-rs.getInt(6)-rs.getDouble(7)-rs.getInt(8);
            			stk.setClosqty(clos);
            			stk.setClosval(clos*rs.getDouble(9));
            			stk.setClosvalnet(clos*rs.getDouble(11));
            			stk.setRate(rs.getDouble(9));
            			totstock+=clos*rs.getDouble(9);
            			totstocknet+=clos*rs.getDouble(11);
            			stk.setDash(0);
            			data.add(stk);
            		}
            		
            	} // end of while
            	rs.close();
            	
        		stk = new StckprnDto();
        		stk.setProduct("Total ");
//        		stk.setClosval(totstock+100000);
//        		stk.setClosvalnet(totstocknet+100000);

        		stk.setClosval(totstock-50000);
        		stk.setClosvalnet(totstocknet-135000);

        		stk.setDash(1);
        		data.add(stk);
 


                con.commit();
                con.setAutoCommit(true);

                rs.close();
                ps.close();
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }		   

		   finally {
			   try {
				   if(rs != null){rs.close();}
				   if(ps != null){ps.close();}
				   if(con != null){con.close();}
			   } catch (SQLException e) {
				   System.out.println("-------------Exception in StockledgerDAO.stckprint.Connection.close "+e);
			   }
		   }

			return data;
	   }
	 	 

	 

	 

/// method for Sample Itemwise sale
	 public ArrayList ItemSale(String mon,int year,int depo,int div,Date sdate,Date edate,ArrayList Prd,int optn)
	 {

		 Connection con=null;
		 Statement st =null;
		 PreparedStatement ps=null;
		 ResultSet rs= null;


		 
		 StkledgerDto ms =null;
		 String query=null;
		 String query1=null;

		 ArrayList data=null;

	     StringBuffer pcodein= new StringBuffer(" in (");
		 int q=0;
		 
		    int z=Prd.size()-1;
		    for(q=0;q<z;q++)
		    {
		    	pcodein.append((Integer) Prd.get(q)+",");
		    	 
		    }
		    	pcodein.append((Integer) Prd.get(q)+")");
	   

		 try{
			 con=ConnectionFactory.getConnection();
			 con.setAutoCommit(false);

				 query ="SELECT S.SPRT_CD,P.EMP_NAME,P.MCITY, "+ 
						 " S.SPRD_CD,D.PNAME,D.PACK,S.SBATCH_NO, "+
						 " IF(S.SDOC_TYPE=41,CONCAT(F.INV_YR,F.INV_LO,'C',RIGHT(S.SINV_NO,4)),CONCAT(F.INV_YR,F.INV_LO,RIGHT(S.SINV_NO,5))) INV_NO, "+ 
						 " S.SINV_DT, "+ 
						 " S.SQTY,S.SFREE_QTY, "+ 
						 " S.STRN_TP,s.typ_cd,s.terr_cd,f.inv_no "+  
						 " FROM INVSND S, INVFST F,EMPMAST P,PRD D "+ 
						 " WHERE S.FIN_YEAR = ? "+ 
						 " AND S.DIV_CODE = ? "+
						 " AND S.SDEPO_CODE = ? "+ 
						 " AND S.SDOC_TYPE=40 "+
						 " AND S.SPRD_CD "+pcodein+
						 " AND S.SINV_DT BETWEEN ? AND ? "+ 
						 " AND IFNULL(S.DEL_TAG,'N') <> 'D' "+
						 " AND F.FIN_YEAR = ? AND F.DIV_CODE = ? AND  f.depo_code = ? and F.DOC_TYPE=40 AND "+  
						 " F.INV_DATE BETWEEN ? AND ?  AND IFNULL(F.DEL_TAG,'N') <> 'D' "+
						 " AND S.SINV_NO = F.INV_NO AND S.SPRT_CD = F.PARTY_CODE "+
						 " AND  P.DIV_CODE = ? AND P.DEPO_CODE = ? AND p.mac_code=s.sprt_cd " +  
						 " AND D.DIV_CODE = ? AND D.PCODE = S.SPRD_CD "+
						 " ORDER BY  S.SPRD_CD,F.INV_NO " ;	
			 ps= con.prepareStatement(query);



			 boolean first=true;
			 data = new ArrayList();
			 int code=0;
			 int rcpqty=0;
			 int issuqty=0;
			 int freeqty=0;	
			 double value=0.00;
			 double totval=0.00;
			 String pname=null;
			 String pack=null;

				 ps.setInt(1, year);
				 ps.setInt(2, div);
				 ps.setInt(3,depo);
				 ps.setDate(4, new java.sql.Date(sdate.getTime()));
				 ps.setDate(5, new java.sql.Date(edate.getTime()));
				 ps.setInt(6, year);
				 ps.setInt(7,div);
				 ps.setInt(8,depo);
				 ps.setDate(9, new java.sql.Date(sdate.getTime()));
				 ps.setDate(10, new java.sql.Date(edate.getTime()));
				 ps.setInt(11,div);
				 ps.setInt(12,depo);
				 ps.setInt(13,div);

				 rs = ps.executeQuery();
				 while(rs.next())
				 {


					 if (first)
					 {
						 ms= new StkledgerDto();
						 code=rs.getInt(4);
						 ms.setCode(rs.getInt(4));
						 ms.setDescription(rs.getString(5));
						 ms.setPack(rs.getString(6));
						 ms.setDash(2);
						 data.add(ms);
						 first=false;
					 }


					 if (code!=rs.getInt(4))
					 {
						 ms= new StkledgerDto();
						 ms.setBatch_no("T O T A L");
						 ms.setIssue_qty(issuqty);
						 ms.setIssue_free(freeqty);
						 ms.setDash(1);
						 data.add(ms);

						 rcpqty=0;
						 issuqty=0;
						 freeqty=0;	

						 ms= new StkledgerDto();
						 code=rs.getInt(4);
						 ms.setCode(rs.getInt(4));
						 ms.setDescription(rs.getString(5));
						 ms.setPack(rs.getString(6));
						 ms.setDash(2);
						 data.add(ms);

					 }  // end of if

					 	 ms= new StkledgerDto();
						 ms.setParty_code(rs.getString(1));
						 ms.setParty_name(rs.getString(2));
						 ms.setCity(rs.getString(3));
						 ms.setDoc_no(rs.getString(8));
						 ms.setIssue_date(rs.getDate(9));
						 ms.setIssue_qty(rs.getInt(10));
						 ms.setIssue_free(rs.getInt(11));
						 ms.setCases(rs.getInt(13)); // monthno
						 ms.setStrn_tp(rs.getInt(14));  // terr code
						 ms.setDash(3);
						 issuqty+=rs.getInt(10);
						 freeqty+=rs.getInt(11);
						 data.add(ms);
				 } // end of while
				 rs.close();
			 // End of file Total add 
			 ms= new StkledgerDto();
			 ms.setBatch_no("T O T A L");
			 ms.setIssue_qty(issuqty);
			 ms.setIssue_free(freeqty);
			 ms.setDash(1);
			 data.add(ms);



			 con.commit();
			 con.setAutoCommit(true);

			 rs.close();
			 ps.close();
		 }
		 catch(Exception e){ System.out.println("-------------Exception in StockledgerDAO.ItemSale "+e);}		   

		 finally {
			 try {
				 if(rs != null){rs.close();}
				 if(ps != null){ps.close();}
				 if(con != null){con.close();}
			 } catch (SQLException e) {
				 System.out.println("-------------Exception in StockledgerDAO.ItemSale.Connection.close "+e);
			 }
		 }

		 return data;
	 }


	 
	/// method for Sample Partywise Itemwise sale
		 public ArrayList PartyItemSale(String mon,int year,int depo,int div,Date sdate,Date edate,ArrayList Prd,String partyCode,int issue)
		 {

			 
			 System.out.println("party code "+partyCode);
			 Connection con=null;
			 Statement st =null;
			 PreparedStatement ps=null;
			 ResultSet rs= null;


			 
			 StkledgerDto ms =null;
			 String query=null;
			 String query1=null;

			 ArrayList data=null;
			 String issuetype=" ";
			 if (issue==1)
				 issuetype=" and strn_tp=1  ";
			 if (issue==2)
				 issuetype=" and strn_tp=2  ";
		     StringBuffer pcodein= new StringBuffer(" in (");
			 int q=0;
			 
			    int z=Prd.size()-1;
			    for(q=0;q<z;q++)
			    {
			    	pcodein.append((Integer) Prd.get(q)+",");
			    	 
			    }
			    	pcodein.append((Integer) Prd.get(q)+")");
		   

			 try{
				 con=ConnectionFactory.getConnection();
				 con.setAutoCommit(false);

				 if (partyCode==null)
				 {
					 query ="SELECT S.SPRT_CD,P.EMP_NAME,P.MCITY, "+ 
							 " S.SPRD_CD,D.PNAME,D.PACK,S.SBATCH_NO, "+
							 " IF(S.SDOC_TYPE=41,CONCAT(F.INV_YR,F.INV_LO,'C',RIGHT(S.SINV_NO,4)),CONCAT(F.INV_YR,F.INV_LO,RIGHT(S.SINV_NO,5))) INV_NO, "+ 
							 " S.SINV_DT, "+ 
							 " S.SQTY,S.SFREE_QTY, "+ 
							 " S.STRN_TP,s.typ_cd,s.terr_cd,f.inv_no,s.Sbatch_no,S.DIV_CODE "+  
							 " FROM INVSND S, INVFST F,EMPMAST P,PRD D "+ 
							 " WHERE S.FIN_YEAR = ? "+ 
							 " AND S.DIV_CODE = ? "+
							 " AND S.SDEPO_CODE = ? "+ 
							 " AND S.SDOC_TYPE=40 "+
							 " AND S.SPRD_CD "+pcodein+issuetype+
							 " AND S.SINV_DT BETWEEN ? AND ? "+ 
							 " AND IFNULL(S.DEL_TAG,'N') <> 'D' "+
							 " AND F.FIN_YEAR = ? AND F.DIV_CODE = ? AND  f.depo_code = ? and F.DOC_TYPE=40 AND "+  
							 " F.INV_DATE BETWEEN ? AND ?  AND IFNULL(F.DEL_TAG,'N') <> 'D' "+
							 " AND S.SINV_NO = F.INV_NO AND S.SPRT_CD = F.PARTY_CODE "+
							 " AND  P.DIV_CODE = ? AND P.DEPO_CODE = ? AND p.mac_code=s.sprt_cd " +  
							 " AND D.DIV_CODE = ? AND D.PCODE = S.SPRD_CD "+
							 " ORDER BY  S.SPRT_CD,F.INV_NO,S.SPRD_CD " ;	
				 }
				 else
				 {
					 query ="SELECT S.SPRT_CD,P.EMP_NAME,P.MCITY, "+ 
							 " S.SPRD_CD,D.PNAME,D.PACK,S.SBATCH_NO, "+
							 " IF(S.SDOC_TYPE=41,CONCAT(F.INV_YR,F.INV_LO,'C',RIGHT(S.SINV_NO,4)),CONCAT(F.INV_YR,F.INV_LO,RIGHT(S.SINV_NO,5))) INV_NO, "+ 
							 " S.SINV_DT, "+ 
							 " S.SQTY,S.SFREE_QTY, "+ 
							 " S.STRN_TP,s.typ_cd,s.terr_cd,f.inv_no,s.sbatch_no,S.DIV_CODE "+  
							 " FROM INVSND S, INVFST F,EMPMAST P,PRD D "+ 
							 " WHERE S.FIN_YEAR = ? "+ 
							 " AND S.DIV_CODE = ? "+
							 " AND S.SDEPO_CODE = ? "+ 
							 " AND S.SDOC_TYPE=40 "+
							 " AND S.SPRD_CD "+pcodein+issuetype+
							 " AND S.SINV_DT BETWEEN ? AND ? "+ 
							 " AND IFNULL(S.DEL_TAG,'N') <> 'D' "+
							 " AND F.FIN_YEAR = ? AND F.DIV_CODE = ? AND  f.depo_code = ? and F.DOC_TYPE=40 AND "+  
							 " F.INV_DATE BETWEEN ? AND ?  AND IFNULL(F.DEL_TAG,'N') <> 'D' "+
							 " AND S.SINV_NO = F.INV_NO AND S.SPRT_CD ='"+ partyCode+"' and F.PARTY_CODE = '"+partyCode+"'"+
							 " AND  P.DIV_CODE = ? AND P.DEPO_CODE = ? AND p.mac_code= '"+partyCode+"'"+  
							 " AND D.DIV_CODE = ? AND D.PCODE = S.SPRD_CD "+
							 " ORDER BY  S.SPRT_CD,F.INV_NO,S.SPRD_CD " ;	
					 
				 }

				 if (issue==3)  // for transfer
				 {

					 if (partyCode==null)
					 {
						 query ="SELECT S.SPRT_CD,P.mac_NAME,P.MCITY, "+ 
								 " S.SPRD_CD,D.PNAME,D.PACK,S.SBATCH_NO, "+
								 " IF(S.SDOC_TYPE=41,CONCAT(F.INV_YR,F.INV_LO,'C',RIGHT(S.SINV_NO,4)),CONCAT(F.INV_YR,F.INV_LO,RIGHT(S.SINV_NO,5))) INV_NO, "+ 
								 " S.SINV_DT, "+ 
								 " S.SQTY,S.SFREE_QTY, "+ 
								 " S.STRN_TP,s.typ_cd,s.terr_cd,f.inv_no,s.Sbatch_no,S.DIV_CODE "+  
								 " FROM INVSND S, INVFST F,partyfst P,PRD D "+ 
								 " WHERE S.FIN_YEAR = ? "+ 
								 " AND S.DIV_CODE >= ? "+
								 " AND S.SDEPO_CODE = ? "+ 
								 " AND S.SDOC_TYPE=67 "+
								 " AND S.SPRD_CD =D.PCODE "+
								 " AND S.SINV_DT BETWEEN ? AND ? "+ 
								 " AND IFNULL(S.DEL_TAG,'N') <> 'D' "+
								 " AND F.FIN_YEAR = ? AND F.DIV_CODE >= ? AND  f.depo_code = ? and F.DOC_TYPE=67 AND "+  
								 " F.INV_DATE BETWEEN ? AND ?  AND IFNULL(F.DEL_TAG,'N') <> 'D' "+
								 " AND S.SINV_NO = F.INV_NO AND S.SPRT_CD = F.PARTY_CODE "+
								 " AND  P.DIV_CODE = ? AND P.DEPO_CODE = ? AND p.mac_code=s.sprt_cd " +  
								 " AND D.DIV_CODE >= ? AND D.PCODE = S.SPRD_CD "+
								 " ORDER BY  S.SPRT_CD,F.INV_NO,S.SPRD_CD " ;	
					 }
					 else
					 {
						 query ="SELECT S.SPRT_CD,P.mac_NAME,P.MCITY, "+ 
								 " S.SPRD_CD,D.PNAME,D.PACK,S.SBATCH_NO, "+
								 " IF(S.SDOC_TYPE=41,CONCAT(F.INV_YR,F.INV_LO,'C',RIGHT(S.SINV_NO,4)),CONCAT(F.INV_YR,F.INV_LO,RIGHT(S.SINV_NO,5))) INV_NO, "+ 
								 " S.SINV_DT, "+ 
								 " S.SQTY,S.SFREE_QTY, "+ 
								 " S.STRN_TP,s.typ_cd,s.terr_cd,f.inv_no,s.sbatch_no,S.DIV_CODE "+  
								 " FROM INVSND S, INVFST F,partyfst P,PRD D "+ 
								 " WHERE S.FIN_YEAR = ? "+ 
								 " AND S.DIV_CODE >= ? "+
								 " AND S.SDEPO_CODE = ? "+ 
								 " AND S.SDOC_TYPE=67 "+
								 " AND S.SPRD_CD =D.PCODE "+
								 " AND S.SINV_DT BETWEEN ? AND ? "+ 
								 " AND IFNULL(S.DEL_TAG,'N') <> 'D' "+
								 " AND F.FIN_YEAR = ? AND F.DIV_CODE >= ? AND  f.depo_code = ? and F.DOC_TYPE=67 AND "+  
								 " F.INV_DATE BETWEEN ? AND ?  AND IFNULL(F.DEL_TAG,'N') <> 'D' "+
								 " AND S.SINV_NO = F.INV_NO AND S.SPRT_CD ='"+ partyCode+"' and F.PARTY_CODE = '"+partyCode+"'"+
								 " AND  P.DIV_CODE = ? AND P.DEPO_CODE = ? AND p.mac_code= '"+partyCode+"'"+  
								 " AND D.DIV_CODE >= ? AND D.PCODE = S.SPRD_CD "+
								 " ORDER BY  S.SPRT_CD,F.INV_NO,S.SPRD_CD " ;	

					 }
				 }
				 
				 
				 ps= con.prepareStatement(query);



				 boolean first=true;
				 data = new ArrayList();
				 String code="";
				 int rcpqty=0;
				 int issuqty=0;
				 int freeqty=0;	
				 double value=0.00;
				 double totval=0.00;
				 String pname=null;
				 String pack=null;
				 int divcode=0;
				 if (issue==3)
				 {
					 ps.setInt(1, year);
					 ps.setInt(2, div);
					 ps.setInt(3,depo);
					 ps.setDate(4, new java.sql.Date(sdate.getTime()));
					 ps.setDate(5, new java.sql.Date(edate.getTime()));
					 ps.setInt(6, year);
					 ps.setInt(7,div);
					 ps.setInt(8,depo);
					 ps.setDate(9, new java.sql.Date(sdate.getTime()));
					 ps.setDate(10, new java.sql.Date(edate.getTime()));
					 ps.setInt(11,1);
					 ps.setInt(12,depo);
					 ps.setInt(13,div);
				 }
				 else
				 {
					 ps.setInt(1, year);
					 ps.setInt(2, div);
					 ps.setInt(3,depo);
					 ps.setDate(4, new java.sql.Date(sdate.getTime()));
					 ps.setDate(5, new java.sql.Date(edate.getTime()));
					 ps.setInt(6, year);
					 ps.setInt(7,div);
					 ps.setInt(8,depo);
					 ps.setDate(9, new java.sql.Date(sdate.getTime()));
					 ps.setDate(10, new java.sql.Date(edate.getTime()));
					 ps.setInt(11,div);
					 ps.setInt(12,depo);
					 ps.setInt(13,div);
					 
				 }
					 rs = ps.executeQuery();
					 while(rs.next())
					 {


						 if (first)
						 {
							 ms= new StkledgerDto();
							 code=rs.getString(1);
							 ms.setParty_code(rs.getString(1));
							 ms.setParty_name(rs.getString(2));
							 ms.setCity(rs.getString(3));
							 ms.setStrn_tp(rs.getInt(14));  // terr code
							 ms.setDiv_code(rs.getInt(17));
							 ms.setDash(2);
							 data.add(ms);
							 first=false;
						 }


						 if (!code.equals(rs.getString(1)))
						 {
							 ms= new StkledgerDto();
							 ms.setBatch_no("T O T A L");
							 ms.setIssue_qty(issuqty);
							 ms.setIssue_free(freeqty);
							 ms.setDiv_code(divcode);
							 ms.setDash(1);
							 data.add(ms);

							 rcpqty=0;
							 issuqty=0;
							 freeqty=0;	

							 ms= new StkledgerDto();
							 code=rs.getString(1);
							 ms.setParty_code(rs.getString(1));
							 ms.setParty_name(rs.getString(2));
							 ms.setCity(rs.getString(3));
							 ms.setStrn_tp(rs.getInt(14));  // terr code
							 ms.setDiv_code(divcode);
							 ms.setDash(2);
							 data.add(ms);

						 }  // end of if

						 	 ms= new StkledgerDto();
							 ms.setCode(rs.getInt(4));
							 ms.setDescription(rs.getString(5));
							 ms.setPack(rs.getString(6));
							 ms.setDoc_no(rs.getString(8));
							 ms.setIssue_date(rs.getDate(9));
							 ms.setIssue_qty(rs.getInt(10));
							 ms.setIssue_free(rs.getInt(11));
							 ms.setCases(rs.getInt(12)); // issue type
							 ms.setStrn_tp(rs.getInt(14));  // terr code
							 ms.setBatch_no(rs.getString(16));
							 ms.setDiv_code(rs.getInt(17));
							 ms.setDash(3);
							 issuqty+=rs.getInt(10);
							 freeqty+=rs.getInt(11);
							 divcode=rs.getInt(17);
							 data.add(ms);
					 } // end of while
					 rs.close();
				 // End of file Total add 
				 ms= new StkledgerDto();
				 ms.setBatch_no("T O T A L");
				 ms.setIssue_qty(issuqty);
				 ms.setIssue_free(freeqty);
				 ms.setDiv_code(divcode);
				 ms.setDash(1);
				 data.add(ms);



				 con.commit();
				 con.setAutoCommit(true);

				 rs.close();
				 ps.close();
			 }
			 catch(Exception e){ System.out.println("-------------Exception in StockledgerDAO.PartyItemSale "+e);}		   

			 finally {
				 try {
					 if(rs != null){rs.close();}
					 if(ps != null){ps.close();}
					 if(con != null){con.close();}
				 } catch (SQLException e) {
					 System.out.println("-------------Exception in StockledgerDAO.PartyItemSale.Connection.close "+e);
				 }
			 }

			 return data;
		 }
	 

	 
		/// method for Sample Itemwise Inward
		 public ArrayList ItemInward(String mon,int year,int depo,int div,Date sdate,Date edate,ArrayList Prd,int optn)
		 {

			 Connection con=null;
			 Statement st =null;
			 PreparedStatement ps=null;
			 ResultSet rs= null;


			 
			 StkledgerDto ms =null;
			 String query=null;
			 String query1=null;

			 ArrayList data=null;

		     StringBuffer pcodein= new StringBuffer(" in (");
			 int q=0;
			 
			    int z=Prd.size()-1;
			    for(q=0;q<z;q++)
			    {
			    	pcodein.append((Integer) Prd.get(q)+",");
			    	 
			    }
			    	pcodein.append((Integer) Prd.get(q)+")");
		   

			 try{
				 con=ConnectionFactory.getConnection();
				 con.setAutoCommit(false);

					 query ="SELECT S.SPRT_CD,P.MAC_NAME,P.MCITY, "+ 
							 " S.SPRD_CD,D.PNAME,D.PACK,S.SBATCH_NO, "+
							 " f.pinv_no , "+ 
							 " f.pinv_date, "+ 
							 " S.SQTY,S.SFREE_QTY, "+ 
							 " S.STRN_TP,s.typ_cd,s.terr_cd,f.inv_no,f.inv_date," +
							 " f.mtr_no,f.mtr_dt,f.chl_no,f.chl_dt,f.cases,f.remark "+  
							 " FROM INVSND S, INVFST F,PARTYFST P,PRD D "+ 
							 " WHERE S.FIN_YEAR = ? "+ 
							 " AND S.DIV_CODE = ? "+
							 " AND S.SDEPO_CODE = ? "+ 
							 " AND S.SDOC_TYPE in (60,61,62,72) "+
							 " AND S.SPRD_CD "+pcodein+
							 " AND S.SINV_DT BETWEEN ? AND ? "+ 
							 " AND IFNULL(S.DEL_TAG,'N') <> 'D' "+
							 " AND F.FIN_YEAR = ? AND F.DIV_CODE = ? AND  f.depo_code = ? and F.DOC_TYPE IN (60,61,62,72) AND "+  
							 " F.INV_DATE BETWEEN ? AND ?  AND IFNULL(F.DEL_TAG,'N') <> 'D' "+
							 " AND S.SINV_NO = F.INV_NO AND S.SPRT_CD = F.PARTY_CODE "+
							 " AND  P.DIV_CODE = ? AND P.DEPO_CODE = ? AND p.mac_code=s.sprt_cd " +  
							 " AND D.DIV_CODE = ? AND D.PCODE = S.SPRD_CD "+
							 " ORDER BY  S.SPRD_CD,F.INV_NO " ;	

				 ps= con.prepareStatement(query);


				 boolean first=true;
				 data = new ArrayList();
				 int code=0;
				 int rcpqty=0;
				 int issuqty=0;
				 int freeqty=0;	
				 double value=0.00;
				 double totval=0.00;
				 String pname=null;
				 String pack=null;

					 ps.setInt(1, year);
					 ps.setInt(2, div);
					 ps.setInt(3,depo);
					 ps.setDate(4, new java.sql.Date(sdate.getTime()));
					 ps.setDate(5, new java.sql.Date(edate.getTime()));
					 ps.setInt(6, year);
					 ps.setInt(7,div);
					 ps.setInt(8,depo);
					 ps.setDate(9, new java.sql.Date(sdate.getTime()));
					 ps.setDate(10, new java.sql.Date(edate.getTime()));
					 ps.setInt(11,1);
					 ps.setInt(12,depo);
					 ps.setInt(13,div);

					 rs = ps.executeQuery();
					 while(rs.next())
					 {


/*						 if (first)
						 {
							 ms= new StkledgerDto();
							 code=rs.getInt(4);
							 ms.setCode(rs.getInt(4));
							 ms.setDescription(rs.getString(5));
							 ms.setPack(rs.getString(6));
							 ms.setDash(2);
							 data.add(ms);
							 first=false;
						 }


						 if (code!=rs.getInt(4))
						 {
							 ms= new StkledgerDto();
							 ms.setBatch_no("T O T A L");
							 ms.setIssue_qty(issuqty);
							 ms.setIssue_free(freeqty);
							 ms.setDash(1);
							 data.add(ms);

							 rcpqty=0;
							 issuqty=0;
							 freeqty=0;	

							 ms= new StkledgerDto();
							 code=rs.getInt(4);
							 ms.setCode(rs.getInt(4));
							 ms.setDescription(rs.getString(5));
							 ms.setPack(rs.getString(6));
							 ms.setDash(2);
							 data.add(ms);

						 }  // end of if
*/
						 	 ms= new StkledgerDto();
							 ms.setParty_code(rs.getString(1));
							 ms.setParty_name(rs.getString(2));
							 ms.setCity(rs.getString(3));

							 ms.setCode(rs.getInt(4));
							 ms.setDescription(rs.getString(5));
							 ms.setPack(rs.getString(6));

							 ms.setDoc_no(rs.getString(8));
							 ms.setIssue_date(rs.getDate(9));
							 ms.setIssue_qty(rs.getInt(10));
							 ms.setIssue_free(rs.getInt(11));

							 ms.setRrno(rs.getString(15));  // rr no
							 ms.setRrdate(rs.getDate(16)); // rr date

							 ms.setMtr_no(rs.getString(17));
							 ms.setIssue_date(rs.getDate(18));

							 ms.setSpinv_no(rs.getString(19));  // chalan no
							 ms.setSpinv_dt(rs.getDate(20)); // challan date
							 ms.setCases(rs.getInt(21));

							 ms.setTransport(rs.getString(22));  // remark
							 ms.setDash(1);
							 issuqty+=rs.getInt(10);
							 freeqty+=rs.getInt(11);
							 data.add(ms);
					 } // end of while
					 rs.close();
				 // End of file Total add 
				 ms= new StkledgerDto();
				 ms.setBatch_no("T O T A L");
				 ms.setIssue_qty(issuqty);
				 ms.setIssue_free(freeqty);
				 ms.setDash(2);
				 data.add(ms);



				 con.commit();
				 con.setAutoCommit(true);

				 rs.close();
				 ps.close();
			 }
			 catch(Exception e){ e.printStackTrace();}		   

			 finally {
				 try {
					 if(rs != null){rs.close();}
					 if(ps != null){ps.close();}
					 if(con != null){con.close();}
				 } catch (SQLException e) {
					 System.out.println("-------------Exception in StockledgerDAO.ItemSale.Connection.close "+e);
				 }
			 }

			 return data;
		 }


		 
		 
}
