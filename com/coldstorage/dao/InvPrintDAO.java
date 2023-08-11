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

import com.coldstorage.dto.InvFstDto;
import com.coldstorage.dto.InvSndDto;
import com.coldstorage.dto.InvViewDto;
import com.itextpdf.text.log.SysoCounter;

public class InvPrintDAO 
{
	
	
	private int iqty;
	private double iweight;

	// TODO Method to get Last No. (Sample Entry)
	public int getInvoiceNo(int depo,int div,int year,int doctp)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		String query1=null;
		int i=0;
		try {
			con=ConnectionFactory.getConnection();
			
			
			if(doctp==40 || doctp==66 || doctp==50)
				query1 ="select ifnull(max(cast(spinv_no as signed)),0) from invsnd where fin_year=?  and sdepo_code=? and div_code=? and  sdoc_type=? and ifnull(del_tag,'')<>'D' "; 
			else
				query1 ="select ifnull(max(sinv_no),0) from invsnd where fin_year=?  and sdepo_code=? and div_code=? and  sdoc_type=? and ifnull(del_tag,'')<>'D' "; 
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, year);
			ps1.setInt(2, depo);
			ps1.setInt(3, div);
			ps1.setInt(4, doctp);
			rst =ps1.executeQuery();

			if(rst.next()){
				i =rst.getInt(1); 
			}

			rst.close();
			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvPrintDAO  getLastInvoice No." + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps1 != null){ps1.close();}
				if(rst != null){rst.close();}

				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SAmpleEntryDAO.Connection.close "+e);
			}
		}

		return i;
	}	 

  // TODO Method to get Last Date (Sample Entry)
	public Date getInvoiceDate(int depo,int div,int doctp)
	{
		PreparedStatement ps1 = null;
		ResultSet rst=null;
		Connection con=null;
		Date idt=null;
		try {
			
			con=ConnectionFactory.getConnection();
			
			String query1 ="select max(inv_date) from invfst where depo_code=? and div_code=? and doc_type=? and  ifnull(del_tag,'')<>'D' "; 
			con.setAutoCommit(false);

			ps1 =con.prepareStatement(query1);
			ps1.setInt(1, depo);
			ps1.setInt(2, div);
			ps1.setInt(3, doctp);
			
			rst =ps1.executeQuery();

			if(rst.next()){
				
				idt= rst.getDate(1);
			}

			con.commit();
			con.setAutoCommit(true);
			ps1.close();

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SAmpleEntryDAO.getLastInvoiceDAte " + ex);
			 
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : ");

				if(ps1 != null){ps1.close();}

				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SAmpleEntryDAO.Connection.close "+e);
			}
		}

		return idt;
	}	 
	
	public InvViewDto getInvDetail(int sinv,String einv,int year,int doctp)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		PreparedStatement ps1 = null;
		ResultSet rs1=null;
		PreparedStatement ps2 = null;
		ResultSet rs2=null;
		PreparedStatement ps3 = null;
		ResultSet rs3=null;

		Connection con=null;
		InvViewDto ivdt=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int myear=0;
		String sndQ;
		
		Date sDate=null;
		Date eDate=null;
		
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


			if(doctp==40)
			{
				sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
						 " concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
						 "P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.ISSUE_QTY,P1.PACK,S.ISSUE_WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
						 "G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
						 "IFNULL(S.DRIVER_NAME,''),IFNULL(S.DRIVER_MOBILE,''),IFNULL(S.VEHICLE_NO,'')," +
						 "IFNULL(S.SPINV_NO,''),S.SPINV_DT,IFNULL(S.MARK_NO,'') "+
					" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
					" WHERE s.fin_year=? and s.sdoc_type=? and s.spinv_no = ? "+
					" and S.SPRT_cD=P.ACCOUNT_NO AND S.SPD_GP=G.GP_CODE AND S.SPRD_CD=P1.PCODE ";
					
				
			}

			else
			{
				sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
						" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
						"P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
						"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
						"IFNULL(S.DRIVER_NAME,''),IFNULL(S.DRIVER_MOBILE,''),IFNULL(S.VEHICLE_NO,'')," +
						"IFNULL(S.SPINV_NO,''),S.SPINV_DT,IFNULL(S.MARK_NO,'') "+
						" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
						" WHERE s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? "+
						" and S.SPRT_cD=P.ACCOUNT_NO AND S.SPD_GP=G.GP_CODE AND S.SPRD_CD=P1.PCODE ";

			}
			String query1="select marka_no,sqty,manjil_no,block_no from  " +
					"where fin_year=? and sinv_no=?";

			String query2="select sum(issue_qty) from invsnd  s where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ?"; 
			String query3="select sum(VAMOUNT) from ledfile  s where s.fin_year=? and s.vbook_cd=10 and s.vac_code=? and (s.rcp_no = ? or ifnull(s.rcp_no,0)=0)"; 

			
			ps = con.prepareStatement(sndQ);
			ps1 = con.prepareStatement(query1);
			ps2 = con.prepareStatement(query2);
			ps3 = con.prepareStatement(query3);
			
			if(doctp==40)
			{
				ps.setInt(1, year);
				ps.setInt(2, doctp);
				ps.setString(3, einv);
			}
			else
			{
				ps.setInt(1, year);
				ps.setInt(2, doctp);
				ps.setInt(3, sinv);
			}
			
			rs= ps.executeQuery();

			ArrayList markaList = null;  
			Vector colum = null; // Table Column
			boolean first=true;
			while (rs.next())
			{
				ivdt = new InvViewDto();
				
				if(sinv==0)
					sinv=rs.getInt(1);
				
				if(first)
			 		first=false;
				ivdt.setInv_no(rs.getString(1));
				ivdt.setInv_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setChl_no(rs.getString(9));  // group name
				ivdt.setPname(rs.getString(10));
				ivdt.setSqty(rs.getInt(11));
				ivdt.setPack(rs.getInt(11)+" "+rs.getString(12));
				ivdt.setNumword(nf.format(rs.getDouble(13)));  // weight
				ivdt.setWeight(rs.getDouble(13));  // weight
				ivdt.setRemark(rs.getString(14));
				ivdt.setMac_name(rs.getString(15));
				ivdt.setMcity(rs.getString(16));
				ivdt.setCategory_hindi(rs.getString(17));  // category
				ivdt.setGroup_name_hindi(rs.getString(18)); // group name
				ivdt.setPname_hindi(rs.getString(19)); // PNAME
				ivdt.setSrate_net(rs.getDouble(20)); // rate
				ivdt.setSamnt(rs.getDouble(21));  // rent amt
				ivdt.setRound_off(rs.getDouble(22));  // roundoff
				ivdt.setNet_amt(rs.getDouble(23));// netamt
				ivdt.setSprd_cd(rs.getInt(24));
				ivdt.setSpd_gp(rs.getInt(25));
				ivdt.setGroup_name(rs.getString(26)); // group name
				ivdt.setAproval_no(rs.getString(27)); // pname
				ivdt.setCategory(rs.getString(28));  // category
				ivdt.setReceiver_name(rs.getString(29));  // RECEIVER
				ivdt.setMobile(rs.getString(30));  // MOBILE
				ivdt.setVehicle_no(rs.getString(31));  // category
				ivdt.setPinv_no(rs.getString(32));  // category
				ivdt.setPinv_date(rs.getDate(33));  // category
				ivdt.setMark_no(rs.getString(34));  // category


				ps2.setInt(1, year);
				ps2.setInt(2, 40);
				ps2.setInt(3, sinv);
				rs2=ps2.executeQuery();
				if(rs2.next())
				{
					ivdt.setTotqty(rs2.getInt(1));
				}
				rs2.close();
				

				ps3.setInt(1, year);
				ps3.setInt(2, rs.getInt(3));
				ps3.setInt(3, sinv);
				rs3=ps3.executeQuery();
				if(rs3.next())
				{
					ivdt.setBill_amt(rs3.getDouble(1));
					ivdt.setCn_val(rs.getDouble(23)-rs3.getDouble(1));

				}
				rs3.close();

				
				
			}  //end of Invfst file
			
			
			if(!first)
			{
				ivdt.setMarka(false);
				ps1.setInt(1,year );
				ps1.setInt(2,sinv );
				rs1= ps1.executeQuery();

				first=true;
				while (rs1.next())
				{
					if(first)
					{
						first=false;
						markaList=new ArrayList();
					}
					colum = new Vector();
					ivdt.setMarka(true);

					colum.addElement(rs1.getString(1));   // marka no 0
					colum.addElement(rs1.getInt(2));   // sqy  1
					colum.addElement(rs1.getString(3));   // manjil no 2
					colum.addElement(rs1.getString(4));   // block no  3
					ivdt.setMark_no(rs1.getString(1));
					markaList.add(colum);
				}
				

				

				if(ivdt!=null && ivdt.isMarka())
					ivdt.setMarkalist(markaList);
				
				rs1.close();
			}


			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
			ps1.close();
			ps2.close();
			ps3.close();
		} catch (SQLException ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in InvViewDAO.getInvDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(rs2 != null){rs2.close();}
				if(ps2 != null){ps2.close();}
				if(rs3 != null){rs3.close();}
				if(ps3 != null){ps3.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return ivdt;
	}	



	public Vector getVouList2(Date vdate,int year,int doctp)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		InvViewDto ivdt=null;
		Vector v=null;
		Vector col=null;
		String code=null;
		NumberFormat nf=null;
		try {


			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			nf = new DecimalFormat("0.00");

			String sndQ =" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
					" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
					"P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
					"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME "+
					" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
					" WHERE s.fin_year=? and s.sdoc_type=? and s.sinv_dt = ? "+
					" and S.SPRT_cD=P.ACCOUNT_NO AND S.SPD_GP=G.GP_CODE AND S.SPRD_CD=P1.PCODE ";

			ps2 = con.prepareStatement(sndQ);
			
			
			ps2.setInt(1, year);
			ps2.setInt(2, doctp);
			ps2.setDate(3,new java.sql.Date(vdate.getTime())); 

			System.out.println("YEAR "+year+" doctp "+doctp+ "date "+vdate);
			rs= ps2.executeQuery();

			v = new Vector();
			String inv=null;

			while (rs.next())
			{

				ivdt = new InvViewDto();
				System.out.println("YEAR 2222  "+year+" doctp "+doctp+ "date "+vdate);
				
				
				ivdt.setInv_no(rs.getString(1));
				ivdt.setInv_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setChl_no(rs.getString(9));  // group name
				ivdt.setPname(rs.getString(10));
				ivdt.setSqty(rs.getInt(11));
				ivdt.setPack(rs.getInt(11)+" "+rs.getString(12));
				ivdt.setNumword(nf.format(rs.getDouble(13)));  // weight
				ivdt.setWeight(rs.getDouble(13));  // weight
				ivdt.setRemark(rs.getString(14));
				ivdt.setMac_name(rs.getString(15));
				ivdt.setMcity(rs.getString(16));
				ivdt.setCategory_hindi(rs.getString(17));  // category
				ivdt.setGroup_name_hindi(rs.getString(18)); // group name
				ivdt.setPname_hindi(rs.getString(19)); // PNAME
				ivdt.setSrate_net(rs.getDouble(20)); // rate
				ivdt.setSamnt(rs.getDouble(21));  // rent amt
				ivdt.setRound_off(rs.getDouble(22));  // roundoff
				ivdt.setNet_amt(rs.getDouble(23));// netamt
				ivdt.setSprd_cd(rs.getInt(24));
				ivdt.setSpd_gp(rs.getInt(25));
				ivdt.setGroup_name(rs.getString(26)); // group name
				ivdt.setAproval_no(rs.getString(27)); // pname
				ivdt.setCategory(rs.getString(28));  // category
				ivdt.setSinv_no(rs.getInt(1));
				
				col= new Vector();
				col.addElement(ivdt.getSinv_no());//concat inv_no
				col.addElement(ivdt.getMac_name());//party name
				col.addElement(ivdt);
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
	
	
	public int addFstRecord(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		PreparedStatement ps2=null;
		String query1= null;
		InvViewDto fst=null;
		SimpleDateFormat sdf= null;
		double net_amt=0.00;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name,pack,rst_no,vehicle_no,weight_per_qty,div_code) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			
			String led ="insert into ledfile " +
			"(VDEPO_CODE,VBOOK_CD,VOU_NO,VOU_DATE,VAC_CODE,VNART_1,VAMOUNT,VDBCR,"+
			" created_by,created_date,fin_year,rcp_no,rcp_date,div_code) " +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			ps2 = con.prepareStatement(led);
			
			ps=con.prepareStatement(query1);
			
			boolean first=true;
			for(int j=0;j<cList.size();j++)
			{
				fst = (InvViewDto) cList.get(j);
				
				if(first)
				{
					first=false;
					ps2.setInt(1, fst.getDepo_code());
					ps2.setInt(2, fst.getDoc_type());
					ps2.setInt(3,fst.getSinv_no());
					ps2.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
					ps2.setString(5,fst.getMac_code());
					ps2.setString(6,"Rent Against  Slip NO. "+fst.getSinv_no()+" & DATE "+sdf.format(fst.getInv_date()));
					ps2.setDouble(7,fst.getNet_amt());
					ps2.setString(8,"DR");
					ps2.setInt(9, fst.getCreated_by());
					ps2.setDate(10,new java.sql.Date(fst.getCreated_date().getTime()));
					ps2.setInt(11,fst.getFin_year());
					ps2.setString(12,String.valueOf(fst.getSinv_no()));
					ps2.setDate(13,new java.sql.Date(fst.getInv_date().getTime()));
					ps2.setInt(14,fst.getDiv_code());

					
					k= ps2.executeUpdate();
				}
				ps.setInt(1, fst.getDepo_code());
				ps.setInt(2,fst.getDoc_type());
				ps.setInt(3,fst.getSinv_no());
				ps.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
				ps.setString(5,fst.getMac_code());
				ps.setInt(6,fst.getSprd_cd());
				ps.setInt(7,fst.getSpd_gp());
				ps.setDouble(8,fst.getSqty());
				ps.setDouble(9,fst.getSrate_net());
				ps.setDouble(10,fst.getSamnt());
				ps.setDouble(11,fst.getRound_off());
				ps.setDouble(12,fst.getNet_amt());
				ps.setString(13,fst.getRemark());
				ps.setInt(14, fst.getCreated_by());
				ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(16, fst.getCreated_by());
				ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(18,fst.getFin_year());
				ps.setDouble(19,fst.getWeight());
				ps.setString(20,fst.getCategory_hindi());
				ps.setString(21,fst.getCategory());
				ps.setString(22,fst.getPack());
				ps.setInt(23, fst.getRst_no());
				ps.setString(24,fst.getVehicle_no());
				ps.setDouble(25,fst.getWeight()/fst.getSqty());
				ps.setInt(26, fst.getDiv_code());
				i=ps.executeUpdate();
			}
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			ps2.close();
			
		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.addFstRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		

	public int addFstRecordOld(InvViewDto fst)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		PreparedStatement ps2=null;
		String query1= null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			
			String led ="insert into ledfile " +
			"(VDEPO_CODE,VBOOK_CD,VOU_NO,VOU_DATE,VAC_CODE,VNART_1,VAMOUNT,VDBCR,"+
			" created_by,created_date,fin_year) " +
			"values (?,?,?,?,?,?,?,?,?,?,?)"; 

			ps2 = con.prepareStatement(led);
			
			ps=con.prepareStatement(query1);
			ps.setInt(1, fst.getDepo_code());
			ps.setInt(2,fst.getDoc_type());
			ps.setInt(3,fst.getSinv_no());
			ps.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
			ps.setString(5,fst.getMac_code());
			ps.setInt(6,fst.getSprd_cd());
			ps.setInt(7,fst.getSpd_gp());
			ps.setDouble(8,fst.getSqty());
			ps.setDouble(9,fst.getSrate_net());
			ps.setDouble(10,fst.getSamnt());
			ps.setDouble(11,fst.getRound_off());
			ps.setDouble(12,fst.getNet_amt());
			ps.setString(13,fst.getRemark());
			ps.setInt(14, fst.getCreated_by());
			ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
			ps.setInt(16, fst.getCreated_by());
			ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
			ps.setInt(18,fst.getFin_year());
			ps.setDouble(19,fst.getWeight());
			ps.setString(20,fst.getCategory_hindi());
			ps.setString(21,fst.getCategory());

			i=ps.executeUpdate();

			if(i>0)
			{
				ps2.setInt(1, fst.getDepo_code());
				ps2.setInt(2, fst.getDoc_type());
				ps2.setInt(3,fst.getSinv_no());
				ps2.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
				ps2.setString(5,fst.getMac_code());
				ps2.setString(6,"Rent Against  Slip NO. "+fst.getSinv_no()+" & DATE "+sdf.format(fst.getInv_date()));
				ps2.setDouble(7,fst.getNet_amt());
				ps2.setString(8,"DR");
				ps2.setInt(9, fst.getCreated_by());
				ps2.setDate(10,new java.sql.Date(fst.getCreated_date().getTime()));
				ps2.setInt(11,fst.getFin_year());
				
				k= ps2.executeUpdate();
			}

		
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			ps2.close();
			
		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.addFstRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		


	
	public int updateFstRecord(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		PreparedStatement ps2=null;
		PreparedStatement ps1=null;
		String query1= null;
		InvViewDto fst=null;
		SimpleDateFormat sdf= null;
		double net_amt=0.00;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	

			
			String query="delete from invsnd where fin_year=? and sdepo_code=? and div_code=? and sdoc_type=? and sinv_no=?";
			String query2="delete from ledfile where fin_year=? and vdepo_code=? and div_code=? and vbook_cd=? and vou_no=?";

			
			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name,pack,rst_no,vehicle_no,weight_per_qty,div_code) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			
			String led ="insert into ledfile " +
			"(VDEPO_CODE,VBOOK_CD,VOU_NO,VOU_DATE,VAC_CODE,VNART_1,VAMOUNT,VDBCR,"+
			" created_by,created_date,fin_year,rcp_no,rcp_date,div_code) " +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			ps2 = con.prepareStatement(led);
			
			ps=con.prepareStatement(query1);

			ps1 = con.prepareStatement(query);

			
			

			

			
			boolean first=true;
			for(int j=0;j<cList.size();j++)
			{
				fst = (InvViewDto) cList.get(j);
				
				if(first)
				{

					ps1.setInt(1,fst.getFin_year());
					ps1.setInt(2, fst.getDepo_code());
					ps1.setInt(3, fst.getDiv_code());
					ps1.setInt(4,fst.getDoc_type());
					ps1.setInt(5,fst.getSinv_no());
					i=ps1.executeUpdate();
					
					System.out.println("VALUES "+i+"  ** "+fst.getFin_year()+" "+fst.getDepo_code()+" "+fst.getDoc_type()+" "+fst.getSinv_no());
					ps1=null;
					ps1 = con.prepareStatement(query2);
					ps1.setInt(1,fst.getFin_year());
					ps1.setInt(2, fst.getDepo_code());
					ps1.setInt(3, fst.getDiv_code());
					ps1.setInt(4,fst.getDoc_type());
					ps1.setInt(5,fst.getSinv_no());
					i=ps1.executeUpdate();

					System.out.println("value of i is "+i);
					
					first=false;
					ps2.setInt(1, fst.getDepo_code());
					ps2.setInt(2, fst.getDoc_type());
					ps2.setInt(3,fst.getSinv_no());
					ps2.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
					ps2.setString(5,fst.getMac_code());
					ps2.setString(6,"Rent Against  Slip NO. "+fst.getSinv_no()+" & DATE "+sdf.format(fst.getInv_date()));
					ps2.setDouble(7,fst.getNet_amt());
					ps2.setString(8,"DR");
					ps2.setInt(9, fst.getCreated_by());
					ps2.setDate(10,new java.sql.Date(fst.getCreated_date().getTime()));
					ps2.setInt(11,fst.getFin_year());
					ps2.setString(12,String.valueOf(fst.getSinv_no()));
					ps2.setDate(13,new java.sql.Date(fst.getInv_date().getTime()));
					ps2.setInt(14, fst.getDiv_code());
					
					k= ps2.executeUpdate();
				}
				ps.setInt(1, fst.getDepo_code());
				ps.setInt(2,fst.getDoc_type());
				ps.setInt(3,fst.getSinv_no());
				ps.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
				ps.setString(5,fst.getMac_code());
				ps.setInt(6,fst.getSprd_cd());
				ps.setInt(7,fst.getSpd_gp());
				ps.setDouble(8,fst.getSqty());
				ps.setDouble(9,fst.getSrate_net());
				ps.setDouble(10,fst.getSamnt());
				ps.setDouble(11,fst.getRound_off());
				ps.setDouble(12,fst.getNet_amt());
				ps.setString(13,fst.getRemark());
				ps.setInt(14, fst.getCreated_by());
				ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(16, fst.getCreated_by());
				ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(18,fst.getFin_year());
				ps.setDouble(19,fst.getWeight());
				ps.setString(20,fst.getCategory_hindi());
				ps.setString(21,fst.getCategory());
				ps.setString(22,fst.getPack());
				ps.setInt(23, fst.getRst_no());
				ps.setString(24,fst.getVehicle_no());
				ps.setDouble(25,fst.getWeight()/fst.getSqty());
				ps.setInt(26, fst.getDiv_code());
				
				i=ps.executeUpdate();
			}
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			ps2.close();
			ps1.close();
			
		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.addFstRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				if(ps2 != null){ps2.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		

	
	public int updateFstRecordOld(InvViewDto fst)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		String query1= null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			

			String query="delete from invsnd where fin_year=? and sdepo_code=? and sdoc_type=? and sinv_no=?";
			String query2="delete from ledfile where fin_year=? and vdepo_code=? and vbook_cd=? and vou_no=?";

			
			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			String led ="insert into ledfile " +
			"(VDEPO_CODE,VBOOK_CD,VOU_NO,VOU_DATE,VAC_CODE,VNART_1,VAMOUNT,VDBCR,"+
			" created_by,created_date,fin_year) " +
			"values (?,?,?,?,?,?,?,?,?,?,?)"; 

			ps2 = con.prepareStatement(led);
			
			ps1 = con.prepareStatement(query);

			ps1.setInt(1,fst.getFin_year());
			ps1.setInt(2, fst.getDepo_code());
			ps1.setInt(3,fst.getDoc_type());
			ps1.setInt(4,fst.getSinv_no());
			ps1.executeUpdate();
			
			ps1=null;
			ps1 = con.prepareStatement(query2);
			ps1.setInt(1,fst.getFin_year());
			ps1.setInt(2, fst.getDepo_code());
			ps1.setInt(3,fst.getDoc_type());
			ps1.setInt(4,fst.getSinv_no());
			ps1.executeUpdate();
			

			
			ps=con.prepareStatement(query1);
			ps.setInt(1, fst.getDepo_code());
			ps.setInt(2,fst.getDoc_type());
			ps.setInt(3,fst.getSinv_no());
			ps.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
			ps.setString(5,fst.getMac_code());
			ps.setInt(6,fst.getSprd_cd());
			ps.setInt(7,fst.getSpd_gp());
			ps.setDouble(8,fst.getSqty());
			ps.setDouble(9,fst.getSrate_net());
			ps.setDouble(10,fst.getSamnt());
			ps.setDouble(11,fst.getRound_off());
			ps.setDouble(12,fst.getNet_amt());
			ps.setString(13,fst.getRemark());
			ps.setInt(14, fst.getCreated_by());
			ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
			ps.setInt(16, fst.getCreated_by());
			ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
			ps.setInt(18,fst.getFin_year());
			ps.setDouble(19,fst.getWeight());
			ps.setString(20,fst.getCategory_hindi());
			ps.setString(21,fst.getCategory());

			i=ps.executeUpdate();

			if(i>0)
			{
				ps2.setInt(1, fst.getDepo_code());
				ps2.setInt(2, fst.getDoc_type());
				ps2.setInt(3,fst.getSinv_no());
				ps2.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
				ps2.setString(5,fst.getMac_code());
				ps2.setString(6,"Rent Against  Slip NO. "+fst.getSinv_no()+" & DATE "+sdf.format(fst.getInv_date()));
				ps2.setDouble(7,fst.getNet_amt());
				ps2.setString(8,"DR");
				ps2.setInt(9, fst.getCreated_by());
				ps2.setDate(10,new java.sql.Date(fst.getCreated_date().getTime()));
				ps2.setInt(11,fst.getFin_year());
				
				k= ps2.executeUpdate();
		}

		
       
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
			System.out.println("-------------Exception in SampleEntryDAO.addFstRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				if(ps1 != null){ps1.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	
	


	
	
		public String getTotalNo(int depo,int div,int year,Date sdate,Date edate,String mnth,int doc_tp)
		{
			PreparedStatement ps1 = null;
			ResultSet rst=null;
			Connection con=null;
			int i=0;
			String totinv=null;
			String query1=null;
			String wtxt="";
			try {
				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);

				if(doc_tp==40 || doc_tp==66 || doc_tp==50)
					query1="select IFNULL(min(convert(spinv_no,signed)),0),IFNULL(max(convert(spinv_no,signed)),0)  from invsnd where  sdepo_code=? and div_code=? and sdoc_type=?  and sinv_dt between ? and ?  and  ifnull(del_tag,'')<>'D' order by convert(spinv_no , signed); " ;
				else if(doc_tp==10)
					query1="select IFNULL(min(vou_no),0),IFNULL(max(vou_no),0)  from ledfile where  fin_year=? and vdepo_code=? and div_code=? and vbook_cd in (10,20)   and  ifnull(del_tag,'')<>'D' " ;
				else
					query1="select IFNULL(min(sinv_no),0),IFNULL(max(sinv_no),0)  from invsnd where  sdepo_code=? and div_code=? and sdoc_type=?  and sinv_dt between ? and ?  and  ifnull(del_tag,'')<>'D' " ;
					
				
				ps1 =con.prepareStatement(query1);
				
				if(doc_tp==10)
				{
					ps1.setInt(1, year);
					ps1.setInt(2, depo);
					ps1.setInt(3, div);
					
				}
				else
				{
					ps1.setInt(1, depo);
					ps1.setInt(2, div);
					ps1.setInt(3, doc_tp);
					ps1.setDate(4,new java.sql.Date(sdate.getTime()));
					ps1.setDate(5,new java.sql.Date(edate.getTime()));
				}
					
				rst =ps1.executeQuery();
				wtxt="WrittenOff No. ";
				if(doc_tp==60)
					wtxt="Inward No. ";
				else if(doc_tp==40)
					wtxt="Outward No. ";
				else if(doc_tp==50)
					wtxt="Gate Pass No. ";
				else if(doc_tp==41)
					wtxt="Transfer No. ";
				else if(doc_tp==10)
					wtxt="Rent Receipt No. ";
					
				if(rst.next()){
					totinv =wtxt+rst.getInt(1)+" - "+rst.getInt(2);
				}

				con.commit();
				con.setAutoCommit(true);
				ps1.close();

			} catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in InvoiceDAO - GetTotal Invoice No.  " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert : "+i);

					if(ps1 != null){ps1.close();}

					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in SQLInvoiceDAO.Connection.close "+e);
				}
			}

			return totinv;
		}	 	
		
	

		
		// TODO SELECTED DELETE IN SAMPLE ENTRY
		public int selectDelete(ArrayList delList)
		{
			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			PreparedStatement ps4 = null;
			PreparedStatement ps5 = null;
			ResultSet rs2=null;
			Connection con=null;
			InvSndDto ivdt=null;
			int k=0;
			try 
			{

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);


				String sndQ =" update invsnd set del_tag=? where fin_year=? and  div_code=? and sdepo_code=? and sdoc_type=? " +
				" and sinv_no=? and sprd_cd=? and sqty=?  and serialno=? " ;

				String fstQ =" update invfst set del_tag=? where fin_year=? and  div_code=? and depo_code=? and doc_type=? " +
				" and inv_no=?  " ;

				String ledQ =" update ledfile set del_tag=? where fin_year=? and  div_code=? and vdepo_code=? and vbook_cd=? " +
				" and vou_no=?  " ;

				String inv="select count(*) from invsnd where fin_year=? and div_Code=? and sdepo_code=? and sdoc_type=?" +
						" and sinv_no=? and ifnull(del_tag,'')<>'D'";
				
				String prdsale="update prd set prd_stck=prd_stck+? where div_code=? and depo_code=? and pcode=?";

				String prdpurchase="update prd set prd_stck=prd_stck-? where div_code=? and depo_code=? and pcode=?";

				ps1 = con.prepareStatement(prdsale);
				ps4 = con.prepareStatement(prdpurchase);
				
				ps2 = con.prepareStatement(inv);
				ps3 = con.prepareStatement(fstQ);
				ps5 = con.prepareStatement(ledQ);
				
				ps = con.prepareStatement(sndQ);
				
				int sz=delList.size();
				for (int i=0;i<sz;i++)
				{
					ivdt = (InvSndDto) delList.get(i);
					ps.setString(1, "D");
					ps.setInt(2, ivdt.getFin_year());
					ps.setInt(3, ivdt.getDiv_code());
					ps.setInt(4, ivdt.getSdepo_code());
					ps.setInt(5, ivdt.getSdoc_type());
					ps.setInt(6, ivdt.getSinv_no());
					ps.setInt(7, ivdt.getSprd_cd());
					ps.setInt(8, ivdt.getSqty());
					ps.setInt(9, ivdt.getSerialno());
					k=ps.executeUpdate();
				}
				
				
				if(ivdt.getSdoc_type()==40)
				{
					ps1.setInt(1, ivdt.getSqty());
					// where clause
					ps1.setInt(2, ivdt.getDiv_code());
					ps1.setInt(3, ivdt.getSdepo_code());
					ps1.setInt(4, ivdt.getSprd_cd());

					k=ps1.executeUpdate();
				}
				else if(ivdt.getSdoc_type()==60)
				{
					ps4.setInt(1, ivdt.getSqty());
					// where clause
					ps4.setInt(2, ivdt.getDiv_code());
					ps4.setInt(3, ivdt.getSdepo_code());
					ps4.setInt(4, ivdt.getSprd_cd());

					k=ps4.executeUpdate();
				}

				ps2.setInt(1, ivdt.getFin_year());
				ps2.setInt(2, ivdt.getDiv_code());
				ps2.setInt(3, ivdt.getSdepo_code());
				ps2.setInt(4, ivdt.getSdoc_type());
				ps2.setInt(5, ivdt.getSinv_no());
				rs2=ps2.executeQuery();
				
				if (rs2.next())
				{
					if(rs2.getInt(1)==0)
					{
						ps3.setString(1, "D");
						// WHERE CLAUSE
						ps3.setInt(2, ivdt.getFin_year());
						ps3.setInt(3, ivdt.getDiv_code());
						ps3.setInt(4, ivdt.getSdepo_code());
						ps3.setInt(5, ivdt.getSdoc_type());
						ps3.setInt(6, ivdt.getSinv_no());
						k=ps3.executeUpdate();
						
						ps5.setString(1, "D");
						// WHERE CLAUSE
						ps5.setInt(2, ivdt.getFin_year());
						ps5.setInt(3, ivdt.getDiv_code());
						ps5.setInt(4, ivdt.getSdepo_code());
						ps5.setInt(5, ivdt.getSdoc_type());
						ps5.setInt(6, ivdt.getSinv_no());
						k=ps5.executeUpdate();

						ps5.setString(1, "D");
						// WHERE CLAUSE
						ps5.setInt(2, ivdt.getFin_year());
						ps5.setInt(3, ivdt.getDiv_code());
						ps5.setInt(4, ivdt.getSdepo_code());
						ps5.setInt(5, 90);
						ps5.setInt(6, ivdt.getSinv_no());
						k=ps5.executeUpdate();

					}
				}
				rs2.close();
				
				con.commit();
				con.setAutoCommit(true);

				ps.close();
				ps1.close();
				ps2.close();
				ps3.close();
				ps4.close();
				ps5.close();

			} 
			catch (SQLException ex) 
			{
				try 
				{
					con.rollback();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				System.out.println("-------------Exception in InvViewDAO.selectDelete " + ex);

			}
			finally {
				try {
					//				System.out.println("No. of Records Update/Insert : "+i);

					if(ps != null){ps.close();}
					if(ps1 != null){ps1.close();}
					if(rs2 != null){rs2.close();}
					if(ps2 != null){ps2.close();}
					if(ps3 != null){ps3.close();}
					if(ps4 != null){ps4.close();}
					if(ps5 != null){ps5.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
				}
			}

			return k;
		}	
		
		
// TODO WHOLE DELETE METHOD IN SAMPLE ENTRY
		public int deleteAll(int depo_code,int div_code,int doc_type,int inv_no,int fin_year)
		{
			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			PreparedStatement ps4 = null;
			PreparedStatement ps5 = null;

			
			ResultSet rs=null;
			Connection con=null;
			int k=0;
			try 
			{

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);


				String fstQ =" update invfst set del_tag=? where fin_year=? and div_code=? and  depo_code=? and doc_type=? " +
				" and inv_no=? " ;
				
				String ssndQ =" select sprd_cd,sbatch_no,sexp_dt,sqty,sfree_qty from invsnd where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? " +
				" and sinv_no=? and ifnull(del_tag,'')<>'D' " ;
				
				String sndQ =" update invsnd set del_tag=? where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? " +
				" and sinv_no=?  " ;
				
				String batlockQ =" update batch set locked=? where fin_year=? and div_code=?  and depo_code=? " ;

				String batQ =" update batch set bat_clos=bat_clos+?+? where div_code=?  and depo_code=?  " +
				" and bat_pcode=?  and bat_no=? and bat_expdt=? " ;
				
				ps1 = con.prepareStatement(batlockQ);  // batch lock prepared statement
				ps1.setInt(1, 1);
				ps1.setInt(2, fin_year);
				ps1.setInt(3, div_code);
				ps1.setInt(4, depo_code);
			
				k=ps1.executeUpdate();
				
				ps2 = con.prepareStatement(batQ);      // batch query prepared statement
				
				
				
				
				
				ps3=con.prepareStatement(ssndQ);
				ps3.setInt(1, fin_year);
				ps3.setInt(2, div_code);
				ps3.setInt(3, depo_code);
				ps3.setInt(4, doc_type);
				ps3.setInt(5, inv_no);
				
				rs=ps3.executeQuery();
				while (rs.next())  // updating batch file
				{
					ps2.setInt(1, rs.getInt(4));  // qty update
					ps2.setInt(2, rs.getInt(5));  //free update in bat_clos
					ps2.setInt(3, div_code);
					ps2.setInt(4, depo_code);
					ps2.setInt(5, rs.getInt(1));
					ps2.setString(6, rs.getString(2));
					ps2.setString(7, rs.getString(3));
					k=ps2.executeUpdate();				
					
				}
						
				   // delete all records from invsnd file
					ps = con.prepareStatement(sndQ);
					ps.setString(1, "D");
					ps.setInt(2, fin_year);
					ps.setInt(3, div_code);
					ps.setInt(4, depo_code);
					ps.setInt(5, doc_type);
					ps.setInt(6, inv_no);
					k=ps.executeUpdate();
					
					// delete all records from invfst file
					ps4 = con.prepareStatement(fstQ);
					ps4.setString(1, "D");
					ps4.setInt(2, fin_year);
					ps4.setInt(3, div_code);
					ps4.setInt(4, depo_code);
					ps4.setInt(5, doc_type);
					ps4.setInt(6, inv_no);
					k=ps4.executeUpdate();

					
					
	                // unlock batch file
					ps1.setInt(1, 0);
					ps1.setInt(2, fin_year);
					ps1.setInt(3, div_code);
					ps1.setInt(4, depo_code);
					k=ps1.executeUpdate();
				

				 


				con.commit();
				con.setAutoCommit(true);

				rs.close();
				ps.close();
				ps1.close();
				ps2.close();
				ps4.close();

			} 
			catch (SQLException ex) 
			{
				try 
				{
					con.rollback();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
				System.out.println("-------------Exception in InvViewDAO.deleteAll " + ex);

			}
			finally {
				try {
					//				System.out.println("No. of Records Update/Insert : "+i);

					if(rs != null){rs.close();}
					if(ps != null){ps.close();}
					if(ps1 != null){ps1.close();}
					if(ps2 != null){ps2.close();}
					if(ps4 != null){ps4.close();}


					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
				}
			}

			return k;
		}	


		public int updateInvfst(InvFstDto fst)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ps=null;
			PreparedStatement ps2=null;
			PreparedStatement ps3=null;
			PreparedStatement ps4=null;
			PreparedStatement ps5=null;
			String query1= null;
			try 
			{

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);


				
				
				query1= " update invfst f," +
				"(select sum(taxable_amt) taxable,sum(cgst_amt) cgst,sum(sgst_amt) sgst,sum(igst_amt) igst,sum(net_amt) net,sum(sdisc_amt) disc " +
				"from invsnd s where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? and sinv_no=? and sinv_dt=? and ifnull(del_tag,'')<>'D') s " +
				"set f.gross_amt=round((s.taxable+s.cgst+s.sgst+s.igst),2),f.bill_amt=round((s.taxable+s.cgst+s.sgst+s.igst)),discount_amt=s.disc," +
				"f.discount_round=round(s.taxable+s.cgst+s.sgst+s.igst)-round((s.taxable+s.cgst+s.sgst+s.igst),2)," +
				"f.taxable=round(s.taxable,2),f.cgst=round(s.cgst,2),f.sgst=round(s.sgst,2),f.igst=round(s.igst,2) " +
				" where f.fin_year=? and f.div_code=? and f.depo_code=? and f.doc_type=? and f.inv_no=? and f.inv_date=? and ifnull(f.del_tag,'')<>'D' ";
			

				String led= " update ledfile f," +
				"(select sum(taxable_amt) taxable,sum(cgst_amt) cgst,sum(sgst_amt) sgst,sum(igst_amt) igst,sum(net_amt) net,sum(sdisc_amt) disc " +
				"from invsnd s where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? and sinv_no=? and sinv_dt=? and ifnull(del_tag,'')<>'D') s " +
				"set f.vamount=round((s.taxable+s.cgst+s.sgst+s.igst)),f.created_by=?,f.created_date=?,f.bill_amt=round((s.taxable+s.cgst+s.sgst+s.igst)) " +
				" where f.fin_year=? and f.div_code=? and f.vdepo_code=? and f.vbook_cd=? and f.vou_no=? and f.vou_date=? and ifnull(f.del_tag,'')<>'D' ";
			
				String cgst= " update ledfile f," +
				"(select sum(taxable_amt) taxable,sum(cgst_amt) cgst,sum(sgst_amt) sgst,sum(igst_amt) igst,sum(net_amt) net,sum(sdisc_amt) disc " +
				"from invsnd s where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? and sinv_no=? and sinv_dt=? and ifnull(del_tag,'')<>'D') s " +
				"set f.vamount=round(s.cgst,2),f.created_by=?,f.created_date=?,f.bill_amt=round((s.taxable+s.cgst+s.sgst+s.igst)) " +
				" where f.fin_year=? and f.div_code=? and f.vdepo_code=? and f.vbook_cd=? and f.vou_no=? and f.vou_date=? and vac_code='77777' and ifnull(f.del_tag,'')<>'D' ";

				String sgst= " update ledfile f," +
				"(select sum(taxable_amt) taxable,sum(cgst_amt) cgst,sum(sgst_amt) sgst,sum(igst_amt) igst,sum(net_amt) net,sum(sdisc_amt) disc " +
				"from invsnd s where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? and sinv_no=? and sinv_dt=? and ifnull(del_tag,'')<>'D') s " +
				"set f.vamount=round(s.sgst,2),f.created_by=?,f.created_date=?,f.bill_amt=round((s.taxable+s.cgst+s.sgst+s.igst)) " +
				" where f.fin_year=? and f.div_code=? and f.vdepo_code=? and f.vbook_cd=? and f.vou_no=? and f.vou_date=? and vac_code='88888' and ifnull(f.del_tag,'')<>'D' ";
				
				String igst= " update ledfile f," +
				"(select sum(taxable_amt) taxable,sum(cgst_amt) cgst,sum(sgst_amt) sgst,sum(igst_amt) igst,sum(net_amt) net,sum(sdisc_amt) disc " +
				"from invsnd s where fin_year=? and div_code=? and sdepo_code=? and sdoc_type=? and sinv_no=? and sinv_dt=? and ifnull(del_tag,'')<>'D') s " +
				"set f.vamount=round(s.igst,2),f.created_by=?,f.created_date=?,f.bill_amt=round((s.taxable+s.cgst+s.sgst+s.igst)) " +
				" where f.fin_year=? and f.div_code=? and f.vdepo_code=? and f.vbook_cd=? and f.vou_no=? and f.vou_date=? and vac_code='66666' and ifnull(f.del_tag,'')<>'D' ";

				
				ps=con.prepareStatement(query1);
				ps2=con.prepareStatement(led);
				ps3=con.prepareStatement(cgst);
				ps4=con.prepareStatement(sgst);
				ps5=con.prepareStatement(igst);
				
				
				// where clause
				ps.setInt(1, fst.getFin_year());
				ps.setInt(2, fst.getDiv_code());
				ps.setInt(3, fst.getDepo_code());
				ps.setInt(4, fst.getDoc_type());
				ps.setInt(5, fst.getInv_no());
				ps.setDate(6, new java.sql.Date(fst.getInv_date().getTime()));
				ps.setInt(7, fst.getFin_year());
				ps.setInt(8, fst.getDiv_code());
				ps.setInt(9, fst.getDepo_code());
				ps.setInt(10, fst.getDoc_type());
				ps.setInt(11, fst.getInv_no());
				ps.setDate(12, new java.sql.Date(fst.getInv_date().getTime()));
			
				i=ps.executeUpdate();
				
				ps2.setInt(1,fst.getFin_year());
				ps2.setInt(2,fst.getDiv_code());
				ps2.setInt(3,fst.getDepo_code());
				ps2.setInt(4,fst.getDoc_type());
				ps2.setInt(5,fst.getInv_no());
				ps2.setDate(6, new java.sql.Date(fst.getInv_date().getTime()));
				ps2.setInt(7, fst.getCreated_by());
				ps2.setTimestamp(8, new java.sql.Timestamp(fst.getCreated_date().getTime()));
				ps2.setInt(9,fst.getFin_year());
				ps2.setInt(10,fst.getDiv_code());
				ps2.setInt(11,fst.getDepo_code());
				ps2.setInt(12,fst.getDoc_type());
				ps2.setInt(13,fst.getInv_no());
				ps2.setDate(14, new java.sql.Date(fst.getInv_date().getTime()));
				i= ps2.executeUpdate();

				if(fst.getInv_type().equalsIgnoreCase("L"))
				{
					ps3.setInt(1,fst.getFin_year());
					ps3.setInt(2,fst.getDiv_code());
					ps3.setInt(3,fst.getDepo_code());
					ps3.setInt(4,fst.getDoc_type());
					ps3.setInt(5,fst.getInv_no());
					ps3.setDate(6, new java.sql.Date(fst.getInv_date().getTime()));
					ps3.setInt(7, fst.getCreated_by());
					ps3.setTimestamp(8, new java.sql.Timestamp(fst.getCreated_date().getTime()));
					ps3.setInt(9,fst.getFin_year());
					ps3.setInt(10,fst.getDiv_code());
					ps3.setInt(11,fst.getDepo_code());
					ps3.setInt(12,90);
					ps3.setInt(13,fst.getInv_no());
					ps3.setDate(14, new java.sql.Date(fst.getInv_date().getTime()));
					i= ps3.executeUpdate();
					
					ps4.setInt(1,fst.getFin_year());
					ps4.setInt(2,fst.getDiv_code());
					ps4.setInt(3,fst.getDepo_code());
					ps4.setInt(4,fst.getDoc_type());
					ps4.setInt(5,fst.getInv_no());
					ps4.setDate(6, new java.sql.Date(fst.getInv_date().getTime()));
					ps4.setInt(7, fst.getCreated_by());
					ps4.setTimestamp(8, new java.sql.Timestamp(fst.getCreated_date().getTime()));
					ps4.setInt(9,fst.getFin_year());
					ps4.setInt(10,fst.getDiv_code());
					ps4.setInt(11,fst.getDepo_code());
					ps4.setInt(12,90);
					ps4.setInt(13,fst.getInv_no());
					ps4.setDate(14, new java.sql.Date(fst.getInv_date().getTime()));
					i= ps4.executeUpdate();

				}
				else if(fst.getInv_type().equalsIgnoreCase("I"))
				{
					ps5.setInt(1,fst.getFin_year());
					ps5.setInt(2,fst.getDiv_code());
					ps5.setInt(3,fst.getDepo_code());
					ps5.setInt(4,fst.getDoc_type());
					ps5.setInt(5,fst.getInv_no());
					ps5.setDate(6, new java.sql.Date(fst.getInv_date().getTime()));
					ps5.setInt(7, fst.getCreated_by());
					ps5.setTimestamp(8, new java.sql.Timestamp(fst.getCreated_date().getTime()));
					ps5.setInt(9,fst.getFin_year());
					ps5.setInt(10,fst.getDiv_code());
					ps5.setInt(11,fst.getDepo_code());
					ps5.setInt(12,90);
					ps5.setInt(13,fst.getInv_no());
					ps5.setDate(14, new java.sql.Date(fst.getInv_date().getTime()));
					i= ps5.executeUpdate();

				}
				con.commit();
				con.setAutoCommit(true);
				ps.close();
				ps2.close();
				ps3.close();
				ps4.close();
				ps5.close();
				
			} catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in OrderDao.updateLed " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert..... : "+i);
					if(ps != null){ps.close();}
					if(ps2 != null){ps2.close();}
					if(ps3 != null){ps3.close();}
					if(ps4 != null){ps4.close();}
					if(ps5 != null){ps5.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in OrderDao.Connection.close "+e);
				}
			}

			return (i);
		}		
		
	
		public int repairInvoice(int fyear,int div,int depo,int doctp,int sinv,int einv)
		{
			Connection con=null;
			int i=0;
			PreparedStatement ps1=null;
			PreparedStatement ps2=null;
			PreparedStatement ps3=null;
			
			ResultSet rs1=null;
			ResultSet rs2=null;
			
			String query1= null;
			String query2= null;
			String query3= null;
			double taxable1=0.00;
			double taxable2=0.00;
			double taxable3=0.00;
			double tax1=0.00;
			double tax2=0.00;
			double tax3=0.00;
			double billamt=0.00;
			double discamt1 = 0.00;
			double discamt2 = 0.00;
			double discamt3 = 0.00;
			double discamt = 0.00;
			double grossamt=0.00;

			try 
			{

				con=ConnectionFactory.getConnection();
				con.setAutoCommit(false);


				query1="select inv_no,disc_1,disc_2,cr_amt,dr_amt,freight from invfst where fin_year=? and div_code=? and depo_code=?" +
				" and doc_type=? and inv_no between ? and ? and ifnull(del_tag,'')<>'D' ";
				
				query2= "select round(sum(sqty*srate_pur),2),stax_Cd1 from invsnd where fin_year=? and div_Code=? and sdepo_code=? and sdoc_Type=? " +
				"and sinv_no=? and ifnull(del_tag,'')<>'D' group by stax_cd1";
				
				query3= " update invfst set gross_amt=?,bill_amt=?,discount_amt=?,taxable=?,taxable2=?,taxable3=?,tax5=?,tax14=?,tax15=? " +
				" where fin_year=? and div_code=? and depo_code=? and doc_type=? and inv_no=?  and ifnull(del_tag,'')<>'D';";
			
				
				ps1=con.prepareStatement(query1);
				ps2=con.prepareStatement(query2);
				ps3=con.prepareStatement(query3);

				ps1.setInt(1, fyear);
				ps1.setInt(2, div);
				ps1.setInt(3, depo);
				ps1.setInt(4, doctp);
				ps1.setInt(5, sinv);
				ps1.setInt(6, einv);
				rs1 = ps1.executeQuery();
				while (rs1.next())
				{
					ps2.setInt(1, fyear);
					ps2.setInt(2, div);
					ps2.setInt(3, depo);
					ps2.setInt(4, doctp);
					ps2.setInt(5, rs1.getInt(1));
					rs2= ps2.executeQuery();
					
					taxable1=0.00;
					taxable2=0.00;
					taxable3=0.00;
					tax1=0.00;
					tax2=0.00;
					tax3=0.00;
					billamt=0.00;
					discamt1 = 0.00;
					discamt2 = 0.00;
					discamt3 = 0.00;
					discamt = 0.00;
					grossamt=0.00;
					while(rs2.next())
					{
						if(rs2.getDouble(2)==5)
							taxable1+=rs2.getDouble(1);
						else if(rs2.getDouble(2)==14)
							taxable2+=rs2.getDouble(1);
						else if(rs2.getDouble(2)==15)
							taxable3+=rs2.getDouble(1);
					}
						rs2.close();
						
						discamt1 = roundTwoDecimals((taxable1)*rs1.getDouble(2)/100);
						discamt2 = roundTwoDecimals((taxable2)*rs1.getDouble(2)/100);
						discamt3 = roundTwoDecimals((taxable3)*rs1.getDouble(2)/100);
						discamt = discamt1+discamt2+discamt3;
						grossamt=taxable1+taxable2+taxable3-discamt;
						
						taxable1=taxable1-discamt1;
						taxable2=taxable2-discamt2;
						taxable3=taxable3-discamt3;
						
						tax1=roundTwoDecimals((taxable1)*5/100);
						tax2=roundTwoDecimals((taxable2)*14/100);
						tax3=roundTwoDecimals((taxable3)*15/100);
						billamt=grossamt+tax1+tax2+tax3-rs1.getDouble(4)+rs1.getDouble(5)+rs1.getDouble(6);
						
						

						ps3.setDouble(1,grossamt);
						ps3.setDouble(2,billamt);
						ps3.setDouble(3,discamt);
						ps3.setDouble(4,taxable1);
						ps3.setDouble(5,taxable2);
						ps3.setDouble(6,taxable3);
						ps3.setDouble(7,tax1);
						ps3.setDouble(8,tax2);
						ps3.setDouble(9,tax3);

						// where clause
						ps3.setInt(10, fyear);
						ps3.setInt(11, div);
						ps3.setInt(12, depo);
						ps3.setInt(13, doctp);
						ps3.setInt(14, rs1.getInt(1));
						i=ps3.executeUpdate();
						
					
				}
				
		 
	       
				con.commit();
				con.setAutoCommit(true);
				ps1.close();
				ps2.close();
				ps3.close();
				
			} catch (SQLException ex) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in OrderDao.updateLed " + ex);
				i=-1;
			}
			finally {
				try {
					System.out.println("No. of Records Update/Insert..... : "+i);
					if(rs1 != null){rs1.close();}
					if(ps1 != null){ps1.close();}
					if(rs2 != null){rs2.close();}
					if(ps2 != null){ps2.close();}
					if(ps3 != null){ps3.close();}
					if(con != null){con.close();}
				} catch (SQLException e) {
					System.out.println("-------------Exception in OrderDao.Connection.close "+e);
				}
			}

			return (i);
		}		

	public double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}			


	public List getInvList(String vcode,int fyear,int div,int depo,Date sdate,Date edate,int doc_tp)
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



			query2 ="select if(doc_type=41,concat(inv_yr,inv_lo,'C',right(inv_no,4)),concat(inv_yr,inv_lo,inv_no)) invno,inv_date,bill_amt,mtr_no,mtr_dt,entry_dt,inv_no from invfst where fin_year=? and div_code=? and depo_code=? and doc_type =? "+ 
					"and inv_date between ? and ? and party_code=? and ifnull(del_tag,'')<>'D' ";


			ps2 = con.prepareStatement(query2);
			ps2.setInt(1, fyear);
			ps2.setInt(2, div);
			ps2.setInt(3, depo);
			ps2.setInt(4, doc_tp);
			ps2.setDate(5, new java.sql.Date(sdate.getTime()));
			ps2.setDate(6, new java.sql.Date(edate.getTime()));
			ps2.setString(7, vcode);
			rs= ps2.executeQuery();

			v = new Vector();
			Date ss=null;
			while (rs.next())
			{
				col= new Vector();
				col.addElement(new Boolean(false));    ///0
				col.addElement(rs.getString(1));  // 1
				ss= (Date)rs.getDate(2);
				col.addElement(sdf.format(ss));  //2
				

				if(rs.getDate(6)!=null)
					col.addElement(sdf.format(rs.getDate(6))); //3
				else
					col.addElement("__/__/____");

				col.addElement(rs.getString(4));  // 3
				
				if(rs.getDate(5)!=null)
					col.addElement(sdf.format(rs.getDate(5))); //5
				else
					col.addElement("__/__/____");

				col.addElement(formatter.format(rs.getDouble(3))); // 6
				col.addElement(rs.getInt(7));  // (inv no hidden )  //7

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
			System.out.println("-------------Exception in SQLInvPrintDAO.getInvList " + ex);
			i=-1;
		}
		finally {
			try {
				//					System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLInvPrintDAO.Connection.close "+e);
			}
		}

		return v;
	}		


	public int updateInvsnd(InvViewDto fst)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps1=null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			

			String query="update invsnd set mark_no=?,floor_no=?,block_no=?,block_qty=? " +
					" where fin_year=? and sdepo_code=? and sdoc_type=? and div_code=? and sinv_no=? and serialno=?";


			
			ps1 = con.prepareStatement(query);
			
			
			List marka=fst.getMarkalist();
			
			
			int size=0;
			
			if(marka!=null)
  			   size=marka.size();

			
			for (int j=0;j<size;j++)
			{
				fst=(InvViewDto) marka.get(j);
				
				
				ps1.setString(1, fst.getMark_no());
				ps1.setString(2, fst.getFloor_no());  
				ps1.setString(3, fst.getBlock_no());
				ps1.setInt(4,fst.getBlock_qty());
				// where clause
				ps1.setInt(5,fst.getFin_year());
				ps1.setInt(6, fst.getDepo_code());
				ps1.setInt(7,fst.getDoc_type());
				ps1.setInt(8,fst.getDiv_code());
				ps1.setInt(9,fst.getSinv_no());
				ps1.setInt(10,fst.getSerialno());

				
				k=ps1.executeUpdate();
				
			}

			
			
			
       
			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			
		} catch (SQLException ex) {
			   ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in InvPrintDAO.updateInvsnd " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		

	public int addFstOutwardRecord(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		PreparedStatement ps3=null;
		PreparedStatement ps4=null;
		InvViewDto fst=null;
		String query1= null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name," +
			"spinv_no,spinv_dt,driver_name,driver_mobile,issue_qty,issue_weight,vehicle_no,mark_no,txn_serialno,pack,floor_no,block_no,weight_per_qty,block_qty,rst_no,written_off_qty,written_off_weight,div_code) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			String query3="update invsnd S set issue_qty=ifnull(issue_qty,0)+?, issue_weight=ifnull(issue_weight,0)+?   where s.sdepo_code=? and s.div_code=? and s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? "; 

			String query4="update invsnd S set written_off_qty=ifnull(written_off_qty,0)+?, written_off_weight=ifnull(written_off_weight,0)+?   where s.sdepo_code=? and s.div_code=? and s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? "; 

			String query5="update invsnd S set sqty=ifnull(sqty,0)-?, weight=ifnull(weight,0)-?   where s.sdepo_code=? and s.div_code=? and s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? "; 

			
			ps3 = con.prepareStatement(query3);

			ps4 = con.prepareStatement(query4);

			ps=con.prepareStatement(query1);
			for(int j=0;j<cList.size();j++)
			{
				fst = (InvViewDto) cList.get(j);

				
				ps.setInt(1, fst.getDepo_code());
				ps.setInt(2,fst.getDoc_type());
				ps.setInt(3,fst.getSinv_no());
				ps.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));


				ps.setString(5,fst.getMac_code());
				ps.setInt(6,fst.getSprd_cd());
				ps.setInt(7,fst.getSpd_gp());
				ps.setDouble(8,fst.getSqty());
				ps.setDouble(9,fst.getSrate_net());
				ps.setDouble(10,fst.getSamnt());
				ps.setDouble(11,fst.getRound_off());
				ps.setDouble(12,fst.getNet_amt());
				ps.setString(13,fst.getRemark());
				ps.setInt(14, fst.getCreated_by());
				ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(16, fst.getCreated_by());
				ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(18,fst.getFin_year());
				ps.setDouble(19,fst.getWeight());
				ps.setString(20,fst.getCategory_hindi());
				ps.setString(21,fst.getCategory());
				ps.setString(22, fst.getChl_no());
				ps.setDate(23,new java.sql.Date(fst.getChl_date().getTime()));
				ps.setString(24, fst.getReceiver_name());
				ps.setString(25, fst.getMobile());
				ps.setDouble(26, fst.getIssue_qty());
				ps.setDouble(27,fst.getIssue_weight());
				ps.setString(28, fst.getVehicle_no());
				ps.setString(29, fst.getMark_no());
				ps.setInt(30,fst.getSerialno());
				ps.setString(31, fst.getPack());
				ps.setString(32, fst.getFloor_no());
				ps.setString(33, fst.getBlock_no());
				ps.setDouble(34,fst.getWeight_per_qty());
				ps.setDouble(35,fst.getBlock_qty());
				ps.setDouble(36,fst.getRst_no());
				ps.setDouble(37,0);
				ps.setDouble(38,0.00);
				ps.setInt(39,fst.getDiv_code());

				i=ps.executeUpdate();

				if(fst.getDoc_type()==66)
				{
					ps.setInt(1, fst.getDepo_code());
					ps.setInt(2,60);
					ps.setInt(3,fst.getSinv_no());
					ps.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
					ps.setString(5,fst.getMac_code());
					ps.setInt(6,fst.getSprd_cd());
					ps.setInt(7,fst.getSpd_gp());
					ps.setDouble(8,fst.getBlock_qty());
					ps.setDouble(9,fst.getSrate_net());
					ps.setDouble(10,fst.getSamnt());
					ps.setDouble(11,fst.getRound_off());
					ps.setDouble(12,fst.getNet_amt());
					ps.setString(13,fst.getRemark());
					ps.setInt(14, fst.getCreated_by());
					ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
					ps.setInt(16, fst.getCreated_by());
					ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
					ps.setInt(18,fst.getFin_year());
					ps.setDouble(19,(fst.getBlock_qty()*fst.getWeight_per_qty()));
					ps.setString(20,fst.getCategory_hindi());
					ps.setString(21,fst.getCategory());
					ps.setString(22, "");
					ps.setDate(23,null);
					ps.setString(24, fst.getReceiver_name());
					ps.setString(25, fst.getMobile());
					ps.setDouble(26, 0);
					ps.setDouble(27,0.00);
					ps.setString(28, fst.getVehicle_no());
					ps.setString(29, fst.getMark_no());
					ps.setInt(30,fst.getSerialno());
					ps.setString(31, fst.getPack());
					ps.setString(32, fst.getFloor_no());
					ps.setString(33, fst.getBlock_no());
					ps.setDouble(34,fst.getWeight_per_qty());
					ps.setDouble(35,fst.getBlock_qty());
					ps.setDouble(36,fst.getRst_no());
					ps.setDouble(37, fst.getIssue_qty());
					ps.setDouble(38,fst.getIssue_weight());
					ps.setInt(39,fst.getDiv_code());

					i=ps.executeUpdate();
				}
				
				
				if(fst.getDoc_type()==40)
				{
					ps3.setDouble(1, fst.getIssue_qty());
					ps3.setDouble(2, fst.getIssue_weight());
					// where clause
					ps3.setInt(3, fst.getDepo_code());
					ps3.setInt(4, fst.getDiv_code());
					ps3.setInt(5, fst.getFin_year());
					ps3.setInt(6, 60);
					ps3.setInt(7, fst.getSinv_no());
					ps3.setInt(8, fst.getSerialno());
					i=ps3.executeUpdate();
				}
				else if(fst.getDoc_type()==66)
				{

					// update inward record with block qty for written off
					ps3.setDouble(1, fst.getBlock_qty());
					ps3.setDouble(2, fst.getBlock_qty()*fst.getWeight_per_qty());
					// where clause
					ps3.setInt(3, fst.getDepo_code());
					ps3.setInt(4, fst.getDiv_code());
					ps3.setInt(5, fst.getFin_year());
					ps3.setInt(6, 60);
					ps3.setInt(7, fst.getSinv_no());
					ps3.setInt(8, fst.getSerialno());
					i=ps3.executeUpdate();

					ps3=null;
					ps3 = con.prepareStatement(query5);

					// update inward record with block qty for written off
					ps3.setDouble(1, fst.getBlock_qty());
					ps3.setDouble(2, fst.getBlock_qty()*fst.getWeight_per_qty());
					// where clause
					ps3.setInt(3, fst.getDepo_code());
					ps3.setInt(4, fst.getDiv_code());
					ps3.setInt(5, fst.getFin_year());
					ps3.setInt(6, 60);
					ps3.setInt(7, fst.getSinv_no());
					ps3.setInt(8, fst.getSerialno());
					i=ps3.executeUpdate();

				}


			}
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			ps3.close();
			ps4.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.addFstRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				if(ps3 != null){ps3.close();}
				if(ps4 != null){ps4.close();}
				
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}	
	
	public int addFstOutwardRecordOld(InvViewDto fst)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		
		String query1= null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name," +
			"spinv_no,spinv_dt,driver_name,driver_mobile,issue_qty,issue_weight,vehicle_no) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			
			
			
			ps=con.prepareStatement(query1);
			ps.setInt(1, fst.getDepo_code());
			ps.setInt(2,fst.getDoc_type());
			ps.setInt(3,fst.getSinv_no());
			ps.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
			ps.setString(5,fst.getMac_code());
			ps.setInt(6,fst.getSprd_cd());
			ps.setInt(7,fst.getSpd_gp());
			ps.setDouble(8,fst.getSqty());
			ps.setDouble(9,fst.getSrate_net());
			ps.setDouble(10,fst.getSamnt());
			ps.setDouble(11,fst.getRound_off());
			ps.setDouble(12,fst.getNet_amt());
			ps.setString(13,fst.getRemark());
			ps.setInt(14, fst.getCreated_by());
			ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
			ps.setInt(16, fst.getCreated_by());
			ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
			ps.setInt(18,fst.getFin_year());
			ps.setDouble(19,fst.getWeight());
			ps.setString(20,fst.getCategory_hindi());
			ps.setString(21,fst.getCategory());
			
			ps.setString(22, fst.getChl_no());
			ps.setDate(23,new java.sql.Date(fst.getChl_date().getTime()));
			ps.setString(24, fst.getReceiver_name());
			ps.setString(25, fst.getMobile());
			ps.setDouble(26, fst.getIssue_qty());
			ps.setDouble(27,fst.getIssue_weight());
			ps.setString(28, fst.getVehicle_no());
			
			

			i=ps.executeUpdate();

		
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.addFstRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	
	public InvViewDto getChallanDetail(String sinv,int year,int doctp,int depo,int div)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;

		PreparedStatement ps1 = null;
		ResultSet rs1=null;

		Connection con=null;
		InvViewDto ivdt=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int myear=0;
		String sndQ;
		
		Date sDate=null;
		Date eDate=null;
		int xdoc=doctp;
		if(doctp==400)
			doctp=40;
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");

			System.out.println("doctp "+doctp);

			if(doctp==60) // inward
			{
				sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
						" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
						"P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
						"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
						"S.SPINV_NO,S.SPINV_DT,S.ISSUE_QTY,S.ISSUE_WEIGHT,S.DRIVER_NAME,S.DRIVER_MOBILE," +
						"S.VEHICLE_NO,IFNULL(S.MARK_NO,''),IFNULL(S.pack,''),IFNULL(S.txn_serialno,0),IFNULL(s.weight_per_qty,0)," +
						"IFNULL(S.RST_NO,''),IFNULL(S.FROM_ACCOUNT,''),IFNULL(s.transfer_no,0),IFNULL(S.floor_no,''),IFNULL(S.block_no,''),txn_serialno "+
						" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
						" WHERE s.sdepo_code=? and s.div_code=? and s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? "+
						" and p.div_code=s.div_code and S.SPRT_cD=P.ACCOUNT_NO and g.div_code=s.div_code AND S.SPD_GP=G.GP_CODE and p1.div_code=s.div_code AND S.SPRD_CD=P1.PCODE and IFNULL(s.transfer_no,0) > 0";
				
			}
			else if(doctp==50)  // gate pass 
			{
				sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
						" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
						"P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
						"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
						"S.SPINV_NO,S.SPINV_DT,S.ISSUE_QTY,S.ISSUE_WEIGHT,S.DRIVER_NAME,S.DRIVER_MOBILE," +
						"S.VEHICLE_NO,IFNULL(S.MARK_NO,''),IFNULL(S.pack,''),IFNULL(S.txn_serialno,0),IFNULL(s.weight_per_qty,0)," +
						"IFNULL(S.RST_NO,''),IFNULL(S.FROM_ACCOUNT,''),IFNULL(s.transfer_no,0),IFNULL(S.floor_no,''),IFNULL(S.block_no,''),txn_serialno "+
						" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
						" WHERE s.sdepo_code=? and s.div_code=? and s.fin_year=? and s.sdoc_type=? and s.spinv_no = ? "+
						" and p.div_code=s.div_code and S.SPRT_cD=P.ACCOUNT_NO and g.div_code=s.div_code AND S.SPD_GP=G.GP_CODE and p1.div_code=s.div_code AND S.SPRD_CD=P1.PCODE ";
			}
			else // outward
			{
				sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
						" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
						"P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
						"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
						"S.SPINV_NO,S.SPINV_DT,S.ISSUE_QTY,S.ISSUE_WEIGHT,S.DRIVER_NAME,S.DRIVER_MOBILE," +
						"S.VEHICLE_NO,IFNULL(S.MARK_NO,''),IFNULL(S.pack,''),IFNULL(S.txn_serialno,0),IFNULL(s.weight_per_qty,0)," +
						"IFNULL(S.RST_NO,''),IFNULL(S.FROM_ACCOUNT,''),IFNULL(s.transfer_no,0),IFNULL(S.floor_no,''),IFNULL(S.block_no,''),txn_serialno "+
						" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
						" WHERE s.sdepo_code=? and s.div_code=? and s.fin_year=? and s.sdoc_type=? and s.spinv_no = ? "+
						" and p.div_code=s.div_code  and S.SPRT_cD=P.ACCOUNT_NO and g.div_code=s.div_code AND S.SPD_GP=G.GP_CODE and p1.div_code=s.div_code AND S.SPRD_CD=P1.PCODE ";
			}

			
			String query1="select sum(ifnull(issue_qty,0)),sum(ifnull(issue_weight,0)) from invsnd  s where s.sdepo_code=? and s.div_code=? and s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and s.txn_serialno = ?"; 
			
			
			
			ps = con.prepareStatement(sndQ);
			ps1 = con.prepareStatement(query1);
			
			
			if(doctp==60)
			{
				ps.setInt(1, depo);
				ps.setInt(2, div);
				ps.setInt(3, year);
				ps.setInt(4, doctp);
				ps.setInt(5, Integer.parseInt(sinv));
				
			}
			else
			{
				ps.setInt(1, depo);
				ps.setInt(2, div);
				ps.setInt(3, year);
				ps.setInt(4, doctp);
				ps.setString(5, sinv);
			}
			
			rs= ps.executeQuery();
			ArrayList dataList = null;  
			Vector colum = null; // Table Column

			boolean first=true;
			while (rs.next())
			{
				ivdt = new InvViewDto();
				
				System.out.println("OUTWARD CHALAN "+rs.getString(1));
				
				if(first)
				{
					first=false;
					dataList=new ArrayList();
				}

				ivdt.setInv_no(rs.getString(1));
				ivdt.setInv_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setChl_no(rs.getString(9));  // group name
				ivdt.setPname(rs.getString(10));
				ivdt.setSqty(rs.getInt(11));
				ivdt.setPack(rs.getInt(11)+" "+rs.getString(12));
				ivdt.setNumword(nf.format(rs.getDouble(13)));  // weight
				ivdt.setWeight(rs.getDouble(13));  // weight
				ivdt.setRemark(rs.getString(14));
				ivdt.setMac_name(rs.getString(15));
				ivdt.setMcity(rs.getString(16));
				ivdt.setCategory_hindi(rs.getString(17));  // category
				ivdt.setGroup_name_hindi(rs.getString(18)); // group name
				ivdt.setPname_hindi(rs.getString(19)); // PNAME
				ivdt.setSrate_net(rs.getDouble(20)); // rate
				ivdt.setSamnt(rs.getDouble(21));  // rent amt
				ivdt.setRound_off(rs.getDouble(22));  // roundoff
				ivdt.setNet_amt(rs.getDouble(23));// netamt
				ivdt.setSprd_cd(rs.getInt(24));
				ivdt.setSpd_gp(rs.getInt(25));
				ivdt.setGroup_name(rs.getString(26)); // group name
				ivdt.setAproval_no(rs.getString(27)); // pname
				ivdt.setCategory(rs.getString(28));  // category
				ivdt.setChl_no(rs.getString(29));
				ivdt.setChl_date(rs.getDate(30));
				ivdt.setPinv_no(rs.getString(29));
				ivdt.setPinv_date(rs.getDate(30));

				ivdt.setIssue_qty(rs.getInt(31));
				ivdt.setIssue_weight(rs.getDouble(32));
				ivdt.setReceiver_name(rs.getString(33));
				ivdt.setMobile(rs.getString(34));
				ivdt.setVehicle_no(rs.getString(35));
				ivdt.setFromhq(rs.getString(41));
				ivdt.setTransfer_no(rs.getInt(42));
				
				iqty=rs.getInt(11);
				iweight=rs.getDouble(13);
				if(xdoc==400)
				{
					iqty=rs.getInt(31);
					iweight=rs.getDouble(32);
					
				}
				
				System.out.println("YEAR "+year+" xdox "+xdoc+" sinv "+rs.getInt(1)+" txn "+rs.getInt(45));
				
				
				if(xdoc==400)
				{
					ps1.setInt(1, depo);
					ps1.setInt(2, div);
					ps1.setInt(3, year);
					ps1.setInt(4, 50);
					ps1.setString(5, rs.getString(29));
					ps1.setInt(6, rs.getInt(45));
					
				}
				else
				{
					ps1.setInt(1, depo);
					ps1.setInt(2, div);
					ps1.setInt(3, year);
					ps1.setInt(4, 40);
					ps1.setInt(5, rs.getInt(1));
					ps1.setInt(6, rs.getInt(45));
				}
				rs1=ps1.executeQuery();
				System.out.println("iqty before "+iqty);
				if(rs1.next())
				{
					
					System.out.println("value is "+rs1.getInt(1));
					ivdt.setTotqty(rs1.getInt(1));
					if(rs1.getInt(1)>0)
					{
						iqty-=rs1.getInt(1);
						iweight-=rs1.getDouble(2);
						System.out.println("iqty middle "+iqty);
					}
				}
				rs1.close();

				System.out.println("iqty after "+iqty);

				colum = new Vector();

				colum.addElement(rs.getInt(1));   //inward no  0
				colum.addElement(sdf.format(rs.getDate(2)));  //bill_date
				colum.addElement(rs.getString(17));   // category 2
				colum.addElement(rs.getString(18));   // group  3
				colum.addElement(rs.getString(19));   // item name 4
				colum.addElement(rs.getString(37));   //pack  5
				colum.addElement(iqty);   // sqty  6
				colum.addElement(iweight);   //weights  7
				colum.addElement(rs.getString(36));   //marka no  8
				if(xdoc==60)
				{
					colum.addElement(rs.getInt(11));   //qty  9
					colum.addElement(rs.getDouble(13));   //floor no  10
					
				}
				else if(xdoc==400)  // gate pass 
				{
					colum.addElement("");   //qty  9
					colum.addElement("");   //floor no  10
					
				}
				else
				{
					colum.addElement(rs.getInt(31));   //qty  9
					colum.addElement(rs.getDouble(32));   //floor no  10
				}
				colum.addElement(rs.getInt(25));   //spd gp  11
				colum.addElement(rs.getInt(38));   //serial no  12
				colum.addElement(rs.getInt(24));   //sprd_cd  13
				colum.addElement(rs.getString(28));   //category englist  14
				colum.addElement(rs.getDouble(20)); //rate 15
				colum.addElement(rs.getString(40));   //rst no  16
				colum.addElement(rs.getDouble(39)); //weight per kg 17
				colum.addElement(rs.getString(43));   //floor no  18
				colum.addElement(rs.getString(44));   //block no  19
			
				
				if(iqty>0)
					dataList.add(colum);

				
				
				
			}  //end of Invfst file
			
			if(!first)
				ivdt.setMarkalist(dataList);



			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
		} catch (SQLException ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in InvViewDAO.getChallanDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return ivdt;
	}	

	public int updateFstOutwardRecord(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps3=null;
		PreparedStatement ps4=null;
		PreparedStatement ps5=null;
		PreparedStatement ps6=null;
		String query1= null;
		InvViewDto fst=null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name," +
			"spinv_no,spinv_dt,driver_name,driver_mobile,issue_qty,issue_weight,vehicle_no,mark_no,txn_serialno,pack,floor_no,block_no) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			
			String query="delete from invsnd where fin_year=? and sdepo_code=? and sdoc_type=? and sinv_no=? and spinv_no=?";

			String query3="update invsnd S set s.issue_qty=ifnull(s.issue_qty,0)-?, s.issue_weight=ifnull(s.issue_weight,0)-? " +
					"  where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and s.serialno=? and s.issue_qty>0"; 

			String query4="update invsnd S set written_off_qty=ifnull(written_off_qty,0)-?, written_off_weight=ifnull(written_off_weight,0)-?   " +
					"where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? and s.written_off_qty>0"; 

			String query5="update invsnd S set s.issue_qty=ifnull(s.issue_qty,0)+?, s.issue_weight=ifnull(s.issue_weight,0)+? " +
					"  where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and s.serialno=? "; 

			String query6="update invsnd S set written_off_qty=ifnull(written_off_qty,0)+?, written_off_weight=ifnull(written_off_weight,0)+?   " +
					"where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? "; 

			
			
			ps3 = con.prepareStatement(query3);

			ps4 = con.prepareStatement(query4);

			ps5 = con.prepareStatement(query5);

			ps6 = con.prepareStatement(query6);
			
			
			ps1=con.prepareStatement(query);
			
			
			
			
			ps=con.prepareStatement(query1);
			
			boolean first=true;
			for(int j=0;j<cList.size();j++)
			{
				fst = (InvViewDto) cList.get(j);

				if(first)
				{
					System.out.println("year "+fst.getFin_year()+"  "+fst.getDepo_code()+"  "+fst.getDoc_type()+"  "+fst.getSinv_no()+" "+fst.getChl_no());
					ps1.setInt(1,fst.getFin_year());
					ps1.setInt(2, fst.getDepo_code());
					ps1.setInt(3, fst.getDoc_type());
					ps1.setInt(4,fst.getSinv_no());
					ps1.setString(5, fst.getChl_no());
					i=ps1.executeUpdate();
					System.out.println("value of i "+i);
					first=false;

				}
				
				
				ps.setInt(1, fst.getDepo_code());
				ps.setInt(2,fst.getDoc_type());
				ps.setInt(3,fst.getSinv_no());
				ps.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));


				ps.setString(5,fst.getMac_code());
				ps.setInt(6,fst.getSprd_cd());
				ps.setInt(7,fst.getSpd_gp());
				ps.setDouble(8,fst.getSqty());
				ps.setDouble(9,fst.getSrate_net());
				ps.setDouble(10,fst.getSamnt());
				ps.setDouble(11,fst.getRound_off());
				ps.setDouble(12,fst.getNet_amt());
				ps.setString(13,fst.getRemark());
				ps.setInt(14, fst.getCreated_by());
				ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(16, fst.getCreated_by());
				ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(18,fst.getFin_year());
				ps.setDouble(19,fst.getWeight());
				ps.setString(20,fst.getCategory_hindi());
				ps.setString(21,fst.getCategory());
				ps.setString(22, fst.getChl_no());
				ps.setDate(23,new java.sql.Date(fst.getChl_date().getTime()));
				ps.setString(24, fst.getReceiver_name());
				ps.setString(25, fst.getMobile());
				ps.setDouble(26, fst.getIssue_qty());
				ps.setDouble(27,fst.getIssue_weight());
				ps.setString(28, fst.getVehicle_no());
				ps.setString(29, fst.getMark_no());
				ps.setInt(30,fst.getSerialno());
				ps.setString(31, fst.getPack());
				ps.setString(32, fst.getFloor_no());
				ps.setString(33, fst.getBlock_no());

				i=ps.executeUpdate();
				
				// remove qty from linked inward chl_no
				if(fst.getDoc_type()==40)
				{
					ps3.setDouble(1, fst.getIssue_qty());
					ps3.setDouble(2, fst.getIssue_weight());
					// where clause
					ps3.setInt(3, fst.getFin_year());
					ps3.setInt(4, 60);
					ps3.setInt(5, fst.getSinv_no());
					ps3.setInt(6, fst.getSerialno());
					i=ps3.executeUpdate();
				}
				else if(fst.getDoc_type()==66)
				{
					ps4.setDouble(1, fst.getIssue_qty());
					ps4.setDouble(2, fst.getIssue_weight());
					// where clause
					ps4.setInt(3, fst.getFin_year());
					ps4.setInt(4, 60);
					ps4.setInt(5, fst.getSinv_no());
					ps4.setInt(6, fst.getSerialno());
					i=ps4.executeUpdate();
					
				}

				// add qty from linked inward chl_no

				if(fst.getDoc_type()==40)
				{
					ps5.setDouble(1, fst.getIssue_qty());
					ps5.setDouble(2, fst.getIssue_weight());
					// where clause
					ps5.setInt(3, fst.getFin_year());
					ps5.setInt(4, 60);
					ps5.setInt(5, fst.getSinv_no());
					ps5.setInt(6, fst.getSerialno());
					i=ps5.executeUpdate();
				}
				else if(fst.getDoc_type()==66)
				{
					ps6.setDouble(1, fst.getIssue_qty());
					ps6.setDouble(2, fst.getIssue_weight());
					// where clause
					ps6.setInt(3, fst.getFin_year());
					ps6.setInt(4, 60);
					ps6.setInt(5, fst.getSinv_no());
					ps6.setInt(6, fst.getSerialno());
					i=ps6.executeUpdate();
					
				}

				
			}

			
		
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			ps1.close();
			ps3.close();
			ps4.close();
			ps5.close();
			ps6.close();
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.updateFstOutwardRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				if(ps1 != null){ps1.close();}
				if(ps3 != null){ps3.close();}
				if(ps4 != null){ps4.close();}
				if(ps5 != null){ps5.close();}
				if(ps6 != null){ps6.close();}
				
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		

	
	public int deleteFstOutwardRecord(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps1=null;
		PreparedStatement ps3=null;
		PreparedStatement ps4=null;
		String query1= null;
		InvViewDto fst=null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			
			
			String query="delete from invsnd where fin_year=? and sdepo_code=? and sdoc_type=? and sinv_no=? and spinv_no=?";

			String query3="update invsnd S set s.issue_qty=ifnull(s.issue_qty,0)-?, s.issue_weight=ifnull(s.issue_weight,0)-? " +
					"  where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and s.serialno=? and s.issue_qty>0"; 

			String query4="update invsnd S set written_off_qty=ifnull(written_off_qty,0)-?, written_off_weight=ifnull(written_off_weight,0)-?   " +
					"where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? and s.written_off_qty>0"; 


			
			
			ps3 = con.prepareStatement(query3);

			ps4 = con.prepareStatement(query4);

			
			
			ps1=con.prepareStatement(query);
			
			
			
			
		
			
			boolean first=true;
			for(int j=0;j<cList.size();j++)
			{
				fst = (InvViewDto) cList.get(j);

				if(first)
				{
					ps1.setInt(1,fst.getFin_year());
					ps1.setInt(2, fst.getDepo_code());
					ps1.setInt(3, fst.getDoc_type());
					ps1.setInt(4,fst.getSinv_no());
					ps1.setString(5, fst.getChl_no());
					i=ps1.executeUpdate();
					first=false;

				}
				
				// remove qty from linked inward chl_no
				if(fst.getDoc_type()==40)
				{
					ps3.setDouble(1, fst.getIssue_qty());
					ps3.setDouble(2, fst.getIssue_weight());
					// where clause
					ps3.setInt(3, fst.getFin_year());
					ps3.setInt(4, 60);
					ps3.setInt(5, fst.getSinv_no());
					ps3.setInt(6, fst.getSerialno());
					i=ps3.executeUpdate();
				}
				else if(fst.getDoc_type()==66)
				{
					ps4.setDouble(1, fst.getIssue_qty());
					ps4.setDouble(2, fst.getIssue_weight());
					// where clause
					ps4.setInt(3, fst.getFin_year());
					ps4.setInt(4, 60);
					ps4.setInt(5, fst.getSinv_no());
					ps4.setInt(6, fst.getSerialno());
					i=ps4.executeUpdate();
					
				}

				
			}

			
		
       
			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			ps3.close();
			ps4.close();
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.updateFstOutwardRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps1 != null){ps1.close();}
				if(ps3 != null){ps3.close();}
				if(ps4 != null){ps4.close();}
				
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		


	public int deleteFstTransferRecord(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		PreparedStatement ps3=null;
		PreparedStatement ps4=null;
		String query1= null;
		InvViewDto fst=null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			
			
			String query="delete from invsnd where fin_year=? and sdepo_code=? and sdoc_type=? and sinv_no=?  and IFNULL(issue_qty,0)=0";

			String query2="delete from invsnd where fin_year=? and sdepo_code=? and sdoc_type=? and sinv_no=?";

			
			String query3="update invsnd S set s.issue_qty=ifnull(s.issue_qty,0)-?, s.issue_weight=ifnull(s.issue_weight,0)-? " +
					"  where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and s.serialno=? and s.issue_qty>0"; 

			String query4="update invsnd S set written_off_qty=ifnull(written_off_qty,0)-?, written_off_weight=ifnull(written_off_weight,0)-?   " +
					"where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? and s.written_off_qty>0"; 


			
			
			ps3 = con.prepareStatement(query3);

			ps4 = con.prepareStatement(query4);

			
			
			ps1=con.prepareStatement(query);
			
			ps2 = con.prepareStatement(query2);
			
			
			
		
			
			boolean first=true;
			for(int j=0;j<cList.size();j++)
			{
				fst = (InvViewDto) cList.get(j);

					System.out.println(fst.getChl_no());
					System.out.println(fst.getPinv_no());
					System.out.println(fst.getTransfer_no());
					System.out.println(fst.getSerialno());
				if(first)
				{


					System.out.println(" FIRST "+fst.getChl_no());
					System.out.println(" FIRST "+fst.getTransfer_no());
					System.out.println(" FIRST "+fst.getSerialno());

					ps1.setInt(1,fst.getFin_year());
					ps1.setInt(2, fst.getDepo_code());
					ps1.setInt(3, 60);
					ps1.setInt(4,Integer.parseInt(fst.getChl_no()));
					i=ps1.executeUpdate();
					System.out.println("value of i after ps1 "+i);
					
					ps2.setInt(1,fst.getFin_year());
					ps2.setInt(2, fst.getDepo_code());
					ps2.setInt(3, 41);
					ps2.setInt(4,fst.getTransfer_no());
					i=ps2.executeUpdate();
					System.out.println("value of i after ps2 "+i);
					first=false;

				}
				
				// remove qty from linked inward chl_no
				if(fst.getDoc_type()==60)
				{
					ps3.setDouble(1, fst.getIssue_qty());
					ps3.setDouble(2, fst.getIssue_weight());
					// where clause
					ps3.setInt(3, fst.getFin_year());
					ps3.setInt(4, 60);
					ps3.setInt(5, Integer.parseInt(fst.getPinv_no()));
					ps3.setInt(6, fst.getSerialno());
					i=ps3.executeUpdate();
					System.out.println("value of i after ps3 "+i);
				}
				else if(fst.getDoc_type()==66)
				{
					ps4.setDouble(1, fst.getIssue_qty());
					ps4.setDouble(2, fst.getIssue_weight());
					// where clause
					ps4.setInt(3, fst.getFin_year());
					ps4.setInt(4, 60);
					ps4.setInt(5, fst.getSinv_no());
					ps4.setInt(6, fst.getSerialno());
					i=ps4.executeUpdate();
					
				}

				
			}

			
		
       
			con.commit();
			con.setAutoCommit(true);
			ps1.close();
			ps3.close();
			ps4.close();
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.updateFstOutwardRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps1 != null){ps1.close();}
				if(ps3 != null){ps3.close();}
				if(ps4 != null){ps4.close();}
				
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	
	
	public ArrayList getInwardDetail(int sinv,String einv,int year,int doctp,int div)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		InvViewDto ivdt=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int myear=0;
		String sndQ="";
		ArrayList inv=null;
		Date sDate=null;
		Date eDate=null;
		
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");
			inv = new ArrayList();

			if(doctp==60 || doctp==41)
			{

				sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
						" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
						"P1.PNAME_HINDI),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
						"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
						"IFNULL(S.DRIVER_NAME,''),IFNULL(S.DRIVER_MOBILE,''),IFNULL(S.VEHICLE_NO,''),IFNULL(S.SPINV_NO,'')," +
						"S.SPINV_DT,ifnull(s.pack,''),ifnull(s.rst_no,0),IFNULL(S.MARK_NO,''),IFNULL(S.DRIVER_CITY,''),IFNULL(S.FROM_ACCOUNT,'')," +
						"IFNULL(S.transfer_no,0),IFNULL(S.FLOOR_NO,''),IFNULL(S.BLOCK_NO,'')  "+
						" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
						" WHERE s.fin_year=? and s.div_code=? and s.sdoc_type=? and s.sinv_no = ? "+
						" and p.div_code=s.div_code and S.SPRT_cD=P.ACCOUNT_NO and s.div_code=g.div_code and S.SPD_GP=G.GP_CODE AND p1.div_code=s.div_code and S.SPRD_CD=P1.PCODE ";
			}
				if(doctp==40 || doctp==66 || doctp==50)
				{
					sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
							" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
							"P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.ISSUE_QTY,P1.PACK,S.ISSUE_WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
							"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
							"IFNULL(S.DRIVER_NAME,''),IFNULL(S.DRIVER_MOBILE,''),IFNULL(S.VEHICLE_NO,'')," +
							"IFNULL(S.SPINV_NO,''),S.SPINV_DT,ifnull(s.pack,''),ifnull(s.rst_no,0),IFNULL(S.MARK_NO,'')," +
							"IFNULL(S.DRIVER_CITY,''),IFNULL(S.FROM_ACCOUNT,''),IFNULL(S.transfer_no,0),IFNULL(S.FLOOR_NO,''),IFNULL(S.BLOCK_NO,'') "+
							" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
							" WHERE s.fin_year=? and s.div_code=? and s.sdoc_type=? and s.spinv_no = ? "+
							" and p.div_code=s.div_code and S.SPRT_cD=P.ACCOUNT_NO and s.div_code=g.div_code AND S.SPD_GP=G.GP_CODE AND p1.div_code=s.div_code AND S.SPRD_CD=P1.PCODE ";
				}
			
			ps = con.prepareStatement(sndQ);
			
			System.out.println("doctp "+doctp+" "+einv);
			
			if(doctp==40 || doctp==66 || doctp==50)
			{
				ps.setInt(1, year);
				ps.setInt(2, div);
				ps.setInt(3, doctp);
				ps.setString(4, einv);
			}
			else
			{
				ps.setInt(1, year);
				ps.setInt(2, div);
				ps.setInt(3, doctp);
				ps.setInt(4, sinv);
			}
			
			rs= ps.executeQuery();

			
			boolean first=true;
			while (rs.next())
			{
				ivdt = new InvViewDto();
				
				if(sinv==0)
					sinv=rs.getInt(1);
				
				ivdt.setInv_no(rs.getString(1));
				ivdt.setInv_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setChl_no(rs.getString(9));  // group name
				ivdt.setPname(rs.getString(10));
				ivdt.setSqty(rs.getInt(11));
				ivdt.setPack(rs.getInt(11)+" "+rs.getString(12));
				ivdt.setNumword(nf.format(rs.getDouble(13)));  // weight
				ivdt.setWeight(rs.getDouble(13));  // weight
				ivdt.setRemark(rs.getString(14));
				ivdt.setMac_name(rs.getString(15));
				ivdt.setMcity(rs.getString(16));
				ivdt.setCategory_hindi(rs.getString(17));  // category
				ivdt.setGroup_name_hindi(rs.getString(18)); // group name
				ivdt.setPname_hindi(rs.getString(19)); // PNAME
				ivdt.setSrate_net(rs.getDouble(20)); // rate
				ivdt.setSamnt(rs.getDouble(21));  // rent amt
				ivdt.setRound_off(rs.getDouble(22));  // roundoff
				ivdt.setNet_amt(rs.getDouble(23));// netamt
				ivdt.setSprd_cd(rs.getInt(24));
				ivdt.setSpd_gp(rs.getInt(25));
				ivdt.setGroup_name(rs.getString(26)); // group name
				ivdt.setAproval_no(rs.getString(27)); // pname
				ivdt.setCategory(rs.getString(28));  // category
				ivdt.setReceiver_name(rs.getString(29));  // RECEIVER
				ivdt.setMobile(rs.getString(30));  // MOBILE
				ivdt.setVehicle_no(rs.getString(31));  // category
				ivdt.setPinv_no(rs.getString(32));  // category
				ivdt.setPinv_date(rs.getDate(33));  // category
				ivdt.setPack_name(rs.getString(34));
				ivdt.setRst_no(rs.getInt(35));
				ivdt.setMark_no(rs.getString(36));
				ivdt.setReceiver_city(rs.getString(37));
				ivdt.setReceiver_account(rs.getString(38));
				ivdt.setTransfer_no(rs.getInt(39));
				ivdt.setFloor_no(rs.getString(40));
				ivdt.setBlock_no(rs.getString(41));
				
				inv.add(ivdt);

				
			}  //end of Invfst file
			
			


			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
		} catch (SQLException ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in InvViewDAO.getInvDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return inv;
	}	

	public InvViewDto getInvDetailNew(int sinv,String einv,int year,int doctp,int div)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		PreparedStatement ps2 = null;
		ResultSet rs2=null;

		Connection con=null;
		InvViewDto ivdt=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int myear=0;
		String sndQ;
		
		Date sDate=null;
		Date eDate=null;
		
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


				sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
						" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
						"P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
						"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
						"IFNULL(S.DRIVER_NAME,''),IFNULL(S.DRIVER_MOBILE,''),IFNULL(S.VEHICLE_NO,''),IFNULL(S.SPINV_NO,'')," +
						"S.SPINV_DT,ifnull(s.pack,''),ifnull(s.rst_no,0),ifnull(s.mark_no,''),ifnull(s.floor_no,'')," +
						"ifnull(s.block_no,''),ifnull(s.issue_qty,0),s.serialno,ifnull(s.block_qty,0),ifnull(s.from_account,''),ifnull(s.transfer_no,0) "+
						" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
						" WHERE s.fin_year=? and s.div_code=? and s.sdoc_type=? and s.sinv_no = ? "+
						" and p.div_code=s.div_code and S.SPRT_cD=P.ACCOUNT_NO and g.div_code=s.div_code AND S.SPD_GP=G.GP_CODE and p1.div_code=s.div_code AND S.SPRD_CD=P1.PCODE  order by s.serialno";

				String query2="select sum(issue_qty), sum(issue_weight) from invsnd  s where s.fin_year=? and s.div_code=?  and s.sdoc_type=? and s.sinv_no = ? and s.txn_serialno=? "; 

				
			
			ps = con.prepareStatement(sndQ);
			ps2 = con.prepareStatement(query2);
			
				ps.setInt(1, year);
				ps.setInt(2, div);
				ps.setInt(3, 60);
				ps.setInt(4, sinv);
			
			rs= ps.executeQuery();

			ArrayList dataList = null;  
			Vector colum = null; // Table Column
			boolean first=true;
			double rentamt=0.00;
			double discountamt=0.00;
			double weight=0.00;
			while (rs.next())
			{
				ivdt = new InvViewDto();
				
				if(sinv==0)
					sinv=rs.getInt(1);
				
				rentamt+=rs.getDouble(21);
				discountamt+=rs.getDouble(22);
				weight+=rs.getDouble(13);

				ivdt.setSamnt(rentamt);  // rent amt
				ivdt.setRound_off(discountamt);  // roundoff
				ivdt.setTotal_weight(weight);  // weight
				if(first)
				{
					first=false;
					dataList=new ArrayList();
				}
				ivdt.setInv_no(rs.getString(1));
				ivdt.setInv_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setChl_no(rs.getString(9));  // group name
				ivdt.setPname(rs.getString(10));
				ivdt.setSqty(rs.getInt(11));
				ivdt.setPack(rs.getInt(11)+" "+rs.getString(12));
				
				ivdt.setRemark(rs.getString(14));
				ivdt.setMac_name(rs.getString(15));
				ivdt.setMcity(rs.getString(16));
				ivdt.setCategory_hindi(rs.getString(17));  // category
				ivdt.setGroup_name_hindi(rs.getString(18)); // group name
				ivdt.setPname_hindi(rs.getString(19)); // PNAME
				ivdt.setSrate_net(rs.getDouble(20)); // rate
				ivdt.setNet_amt(rs.getDouble(23));// netamt
				ivdt.setSprd_cd(rs.getInt(24));
				ivdt.setSpd_gp(rs.getInt(25));
				ivdt.setGroup_name(rs.getString(26)); // group name
				ivdt.setAproval_no(rs.getString(27)); // pname
				ivdt.setCategory(rs.getString(28));  // category
				ivdt.setReceiver_name(rs.getString(29));  // RECEIVER
				ivdt.setMobile(rs.getString(30));  // MOBILE
				ivdt.setVehicle_no(rs.getString(31));  // category
				ivdt.setPinv_no(rs.getString(32));  // category
				ivdt.setPinv_date(rs.getDate(33));  // category
				ivdt.setMark_no("true"); // edit false set inthid
				if(rs.getInt(39)!=0)
					ivdt.setMark_no("false"); // edit false set inthid
				else if(rs.getInt(43)!=0)
					ivdt.setMark_no("false"); // edit false set inthid

				ivdt.setFromhq(rs.getString(42));  // from account no.
				iqty=rs.getInt(11);
				iweight=rs.getDouble(13);

				
				ps2.setInt(1, year);
				ps2.setInt(2, div);
				ps2.setInt(3, 40);
				ps2.setInt(4, sinv);
				ps2.setInt(5, rs.getInt(40));
				rs2=ps2.executeQuery();

				if(rs2.next())
				{
					iqty-=rs2.getInt(1);
					iweight-=rs2.getDouble(2);
				}
				rs2.close();
				
				colum = new Vector();

				if(doctp==60)
				{
					colum.addElement(rs.getString(17));   // category 0
					colum.addElement(rs.getString(18));   // group  1
					colum.addElement(rs.getString(19));   // item name 2
					colum.addElement(rs.getString(34));   //pack  3
					colum.addElement(rs.getDouble(20));   //rate  4
					colum.addElement(rs.getInt(11));   // sqty  5
					colum.addElement(rs.getDouble(13));   //weights  6
					colum.addElement(rs.getDouble(21));   //samnt  7
					colum.addElement(rs.getInt(35));   //rst no  8
					colum.addElement(rs.getInt(25));   //spd gp  9
					colum.addElement(rs.getInt(24));   //sprd_cd  10
					colum.addElement(rs.getString(28));   //category englist  11
				}

				if(doctp==0)  // for inward updation
				{
					colum.addElement(rs.getString(17));   // category 0
					colum.addElement(rs.getString(18));   // group  1
					colum.addElement(rs.getString(19));   // item name 2
					colum.addElement(rs.getString(34));   //pack  3
					colum.addElement(rs.getInt(11));   // sqty  4
					colum.addElement(rs.getDouble(13));   //weights  5
					colum.addElement(rs.getString(36));   //marka no  6
					if(rs.getInt(41)==0)
						colum.addElement("");   //qty  7
					else
						colum.addElement(rs.getInt(41));   //qty  7
					colum.addElement(rs.getString(37));   //floor no  8
					colum.addElement(rs.getString(38));   //gala no  9
					colum.addElement(rs.getInt(40));   //serial no  10
					colum.addElement(rs.getInt(24));   //sprd_cd  11
					colum.addElement(rs.getString(28));   //category englist  12
				}
				if(doctp==1)  // for outward purpose
				{
					colum.addElement(rs.getString(17));   // category 0
					colum.addElement(rs.getString(18));   // group  1
					colum.addElement(rs.getString(19));   // item name 2
					colum.addElement(rs.getString(34));   //pack  3
					colum.addElement(iqty);   // sqty  4
					colum.addElement(iweight);   //weights  5
					colum.addElement(rs.getString(36));   //marka no  6
					colum.addElement("");   //qty  7
					colum.addElement("");   //floor no  8
					colum.addElement(rs.getInt(25));   //spd gp  9
					colum.addElement(rs.getInt(40));   //serial no  10
					colum.addElement(rs.getInt(24));   //sprd_cd  11
					colum.addElement(rs.getString(28));   //category englist  12
				}
				if(doctp==1 && iqty>0)
					dataList.add(colum);
				else if(doctp==60 || doctp==0)
					dataList.add(colum);

				
			}  //end of Invfst file
			if(!first)
				ivdt.setMarkalist(dataList);
			
			

			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
			ps2.close();
		} catch (SQLException ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in InvViewDAO.getInvDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs2 != null){rs2.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return ivdt;
	}	
	
	public int addLedfileRecord(InvViewDto fst)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		
		String query1= null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
/*			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name," +
			"spinv_no,spinv_dt,driver_name,driver_mobile,issue_qty,issue_weight,vehicle_no) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
*/
			query1 ="insert into ledfile " +
			"(VDEPO_CODE,VBOOK_CD,VOU_NO,VOU_DATE,RCP_NO,RCP_DATE,VAC_CODE,VNART_1,VAMOUNT,VDBCR,"+
			"REMARK,created_by,created_date,fin_year,receiver_name,mobile_no)" +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
	
			
			
			
			ps=con.prepareStatement(query1);
			ps.setInt(1, fst.getDepo_code());
			ps.setInt(2,fst.getDoc_type());
			ps.setString(3, fst.getChl_no());
			ps.setDate(4,new java.sql.Date(fst.getChl_date().getTime()));
			ps.setInt(5,fst.getSinv_no());
			ps.setDate(6,new java.sql.Date(fst.getInv_date().getTime()));
			ps.setString(7,fst.getMac_code());
			ps.setString(8,"Rent Received  Slip NO. "+fst.getSinv_no()+" & DATE "+sdf.format(fst.getInv_date()));
			ps.setDouble(9,fst.getNet_amt());
			ps.setString(10,"CR");
			ps.setString(11,fst.getRemark());
			ps.setInt(12, fst.getCreated_by());
			ps.setDate(13,new java.sql.Date(fst.getCreated_date().getTime()));
			ps.setInt(14,fst.getFin_year());
			ps.setString(15,fst.getReceiver_name());
			ps.setString(16,fst.getMobile());
			
			

			i=ps.executeUpdate();

		
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.addFstRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		

	public InvViewDto getReceiptDetail(int sinv,int year,int doctp)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;

		PreparedStatement ps1 = null;
		ResultSet rs1=null;

		Connection con=null;
		InvViewDto ivdt=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int myear=0;
		String sndQ;
		
		Date sDate=null;
		Date eDate=null;
		
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");




			
			sndQ=" SELECT S.VOU_NO,S.VOU_DATE,S.VAC_CODE,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
				 "s.VAMOUNT,S.REMARK,ifnull(rcp_no,0),receiver_name,mobile_no" +
			" FROM LEDFILE S,accountmaster P "+
			" WHERE s.fin_year=? and s.VBOOK_CD=? and s.VOU_no = ? "+
			" and S.VAC_CODE=P.ACCOUNT_NO ";
			
			String query1="select sum(vamount) from ledfile  s where s.fin_year=? and s.vbook_cd=? and s.vac_code=? and (s.rcp_no = ? or ifnull(s.rcp_no,0)=0)"; 
			
			
			
			ps = con.prepareStatement(sndQ);
			ps1 = con.prepareStatement(query1);
			

			ps.setInt(1, year);
			ps.setInt(2, doctp);
			ps.setInt(3, sinv);

			
			rs= ps.executeQuery();

			boolean first=true;
			while (rs.next())
			{
				ivdt = new InvViewDto();
				
				if(first)
			 		first=false;
				ivdt.setChl_no(rs.getString(1));
				ivdt.setChl_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setNet_amt(rs.getDouble(9));// netamt
				ivdt.setRemark(rs.getString(10));
				ivdt.setReceiver_name(rs.getString(12));
				ivdt.setMobile(rs.getString(13));
				ivdt.setSinv_no(rs.getInt(11));
				
				ps1.setInt(1, year);
				ps1.setInt(2, 10);
				ps1.setInt(3, rs.getInt(3));
				ps1.setInt(4, rs.getInt(11));
				
				rs1=ps1.executeQuery();
				if(rs1.next())
				{
					ivdt.setBill_amt(rs1.getDouble(1));
					
				}
				rs1.close();

				
			}  //end of Invfst file
			
			


			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
		} catch (SQLException ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in InvViewDAO.getChallanDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return ivdt;
	}	

	public int updateLedfileRecord(InvViewDto fst)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		String query1= null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			if(fst.getSinv_no()>0)
			{
				query1 ="update ledfile set vou_date=?,vamount=?,receiver_name=?,mobile_no=?,remark=?,rcp_no=?,rcp_date=?,vnart_1=?  " +
						"where fin_year=? and vbook_cd=10 and vou_no=?  ";
			}
			else
			{
				query1 ="update ledfile set vou_date=?,vamount=?,receiver_name=?,mobile_no=?,remark=? " +
						"where fin_year=? and vbook_cd=10 and vou_no=?  ";
			}
			
			
			
			ps=con.prepareStatement(query1);

			
			if(fst.getSinv_no()>0)
			{
				ps.setDate(1,new java.sql.Date(fst.getChl_date().getTime()));
				ps.setDouble(2,fst.getNet_amt());
				ps.setString(3, fst.getReceiver_name());
				ps.setString(4, fst.getMobile());
				ps.setString(5,fst.getRemark());
				ps.setInt(6,fst.getSinv_no());
				ps.setDate(7,new java.sql.Date(fst.getInv_date().getTime()));
				ps.setString(8,"Advance Adjusted in  Slip NO. "+fst.getSinv_no()+" & DATE "+sdf.format(fst.getInv_date()));
				// where clause
				ps.setInt(9,fst.getFin_year());
				ps.setInt(10,fst.getBunch_no());
			}
			else
			{
				
				ps.setDate(1,new java.sql.Date(fst.getChl_date().getTime()));
				ps.setDouble(2,fst.getNet_amt());
				ps.setString(3, fst.getReceiver_name());
				ps.setString(4, fst.getMobile());
				ps.setString(5,fst.getRemark());
				// where
				ps.setInt(6,fst.getFin_year());
				ps.setInt(7,fst.getBunch_no());
				
			}
			
			i=ps.executeUpdate();

		
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.updateFstOutwardRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}		
	public InvViewDto getInvDetailParty(int party_code,String einv,int year,int doctp)
	{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2=null;
		PreparedStatement ps3 = null;
		ResultSet rs3=null;
		ResultSet rs=null;
		ResultSet rs1=null;
		Connection con=null;
		InvViewDto ivdt=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int myear=0;
		String sndQ;
		
		Date sDate=null;
		Date eDate=null;
		
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


				sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
						" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
						"P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
						"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
						"IFNULL(S.DRIVER_NAME,''),IFNULL(S.DRIVER_MOBILE,''),IFNULL(S.VEHICLE_NO,''),IFNULL(S.SPINV_NO,'')," +
						"S.SPINV_DT,ifnull(s.pack,''),ifnull(s.rst_no,0),ifnull(s.mark_no,''),ifnull(s.floor_no,'')," +
						"ifnull(s.block_no,''),ifnull(s.issue_qty,0),s.serialno,ifnull(s.issue_weight,0),ifnull(s.weight_per_qty,0)," +
						"ifnull(s.written_off_qty,0),ifnull(s.written_off_weight,0) "+
						" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
						" WHERE s.fin_year=? and s.sdoc_type=? and s.sprt_cd = ? "+
						" and S.SPRT_cD=P.ACCOUNT_NO AND S.SPD_GP=G.GP_CODE AND S.SPRD_CD=P1.PCODE ";

				String query="select sum(a.vamount ) from "+ 
				" (select sum(vamount) vamount from ledfile where fin_year=? and vac_code=? and vbook_Cd=60  "+
				" union all "+
				" select sum(net_amt)*-1 vamount from invsnd where fin_year=? and sprt_Cd=? and sdoc_Type=41 "+						
				" union all "+
				" select sum(vamount)*-1 vamount from ledfile where fin_year=? and vac_code=? and vbook_Cd in (10,20))  a ";

				String query1="select sum(ifnull(issue_qty,0)),sum(ifnull(issue_weight,0)) from invsnd  s where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and s.txn_serialno = ?"; 

				String query2="select sum(ifnull(issue_qty,0)),sum(ifnull(issue_weight,0)) from invsnd  s where s.fin_year=? and s.sdoc_type=? and s.spinv_no = ? and s.txn_serialno = ?"; 
				
			ps = con.prepareStatement(sndQ);
			ps1 = con.prepareStatement(query);
			ps2 = con.prepareStatement(query1);
			ps3 = con.prepareStatement(query2);
			
			
				ps.setInt(1, year);
				ps.setInt(2, 60);
				ps.setInt(3, party_code);
			
			rs= ps.executeQuery();

			ArrayList dataList = null;  
			Vector colum = null; // Table Column
			boolean first=true;
			double rent_amt=0.00;
			while (rs.next())
			{
				ivdt = new InvViewDto();
				
				
				if(first)
				{
					first=false;
					dataList=new ArrayList();

					ps1.setInt(1, year);
					ps1.setInt(2, party_code);
					ps1.setInt(3, year);
					ps1.setInt(4, party_code);
					ps1.setInt(5, year);
					ps1.setInt(6, party_code);

					rs1= ps1.executeQuery();
					ivdt.setCn_val(0.00);

					if(rs1.next())
					{
						rent_amt=rs1.getDouble(1);
					}
					rs1.close();	
				}
				ivdt.setCn_val(rent_amt);
				System.out.println("cn ki value "+ivdt.getCn_val());
				ivdt.setInv_no(rs.getString(1));
				ivdt.setInv_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setChl_no(rs.getString(9));  // group name
				ivdt.setPname(rs.getString(10));
				ivdt.setSqty(rs.getInt(11));
				ivdt.setPack(rs.getInt(11)+" "+rs.getString(12));
				ivdt.setNumword(nf.format(rs.getDouble(13)));  // weight
				ivdt.setWeight(rs.getDouble(13));  // weight
				ivdt.setRemark(rs.getString(14));
				ivdt.setMac_name(rs.getString(15));
				ivdt.setMcity(rs.getString(16));
				ivdt.setCategory_hindi(rs.getString(17));  // category
				ivdt.setGroup_name_hindi(rs.getString(18)); // group name
				ivdt.setPname_hindi(rs.getString(19)); // PNAME
				ivdt.setSrate_net(rs.getDouble(20)); // rate
				ivdt.setSamnt(rs.getDouble(21));  // rent amt
				ivdt.setRound_off(rs.getDouble(22));  // roundoff
				ivdt.setNet_amt(rs.getDouble(23));// netamt
				ivdt.setSprd_cd(rs.getInt(24));
				ivdt.setSpd_gp(rs.getInt(25));
				ivdt.setGroup_name(rs.getString(26)); // group name
				ivdt.setAproval_no(rs.getString(27)); // pname
				ivdt.setCategory(rs.getString(28));  // category
				ivdt.setReceiver_name(rs.getString(29));  // RECEIVER
				ivdt.setMobile(rs.getString(30));  // MOBILE
				ivdt.setVehicle_no(rs.getString(31));  // category
				ivdt.setPinv_no(rs.getString(32));  // category
				ivdt.setPinv_date(rs.getDate(33));  // category
				ivdt.setChl_no(rs.getString(32));  // category
				ivdt.setChl_date(rs.getDate(33));  // category
				
				

//				iqty=rs.getInt(11)-rs.getInt(39)-rs.getInt(43);
//				iweight=rs.getDouble(13)-rs.getDouble(41)-rs.getDouble(44);

				iqty=rs.getInt(11)-rs.getInt(43);;
				iweight=rs.getDouble(13)-rs.getDouble(44);
				
				System.out.println(rs.getInt(11)+"  "+rs.getDouble(13)+ " "+rs.getInt(1)+" "+rs.getInt(40)+" IQTY "+iqty);
				
					ps2.setInt(1, year);
					ps2.setInt(2, 40);
					ps2.setInt(3, rs.getInt(1));
					ps2.setInt(4, rs.getInt(40));

					rs2=ps2.executeQuery();
				if(rs2.next())
				{
					
					ivdt.setTotqty(rs2.getInt(1));
					if(rs2.getInt(1)>0)
					{
						iqty-=rs2.getInt(1);
						iweight-=rs2.getDouble(2);
					}
					System.out.println("rs2 ke andar "+iqty+" int 1 "+rs2.getInt(1));
				}
				rs2.close();

				ps3 = con.prepareStatement(query2);

				ps3.setInt(1, year);
				ps3.setInt(2, 41);
				ps3.setString(3, rs.getString(1));
				ps3.setInt(4, rs.getInt(40));

				rs3=ps3.executeQuery();
			if(rs3.next())
			{
				
				ivdt.setTotqty(rs3.getInt(1));
				if(rs3.getInt(1)>0)
				{
					iqty-=rs3.getInt(1);
					iweight-=rs3.getDouble(2);
				}
			}
			rs3.close();

				
				colum = new Vector();

					colum.addElement(rs.getInt(1));   //inward no  0
 				    colum.addElement(sdf.format(rs.getDate(2)));  //bill_date
					colum.addElement(rs.getString(17));   // category 2
					colum.addElement(rs.getString(18));   // group  3
					colum.addElement(rs.getString(19));   // item name 4
					colum.addElement(rs.getString(34));   //pack  5
					colum.addElement(iqty);   // sqty  6
					colum.addElement(iweight);   //weights  7
					if(doctp==2)
						colum.addElement("");   //marka no  8
					else
						colum.addElement(rs.getString(36));   //marka no  8
						
					colum.addElement("");   //qty  9
					colum.addElement("");   //floor no  10
					colum.addElement(rs.getInt(25));   //spd gp  11
					colum.addElement(rs.getInt(40));   //serial no  12
					colum.addElement(rs.getInt(24));   //sprd_cd  13
					colum.addElement(rs.getString(28));   //category englist  14
					colum.addElement(rs.getDouble(20)); //rate 15
					colum.addElement(rs.getString(35));   //rst no  16
					if(rs.getDouble(42)==0)
						colum.addElement(iweight/iqty); //weight per kg 17
					else
						colum.addElement(rs.getDouble(42)); //weight per kg 17
					colum.addElement(rs.getString(37));   //floor no  18
					if(doctp==2)
						colum.addElement(rs.getString(36));   //marka no  19 only for gate pass
					else 
						colum.addElement(rs.getString(38));   //gala  no  19
					
				if((doctp==1 || doctp==2) && iqty>0)
					dataList.add(colum);
				else if(doctp==60 || doctp==0)
					dataList.add(colum);

				
			}  //end of Invfst file
			if(!first)
				ivdt.setMarkalist(dataList);
			
			

			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
			ps1.close();
			ps2.close();
			ps3.close();
		} catch (SQLException ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in InvViewDAO.getInvDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(rs2 != null){rs2.close();}
				if(ps2 != null){ps2.close();}
				if(rs3 != null){rs3.close();}
				if(ps3 != null){ps3.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return ivdt;
	}	

	
	public InvViewDto getInwardDetail(int sinv,int year,int doctp)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;

		PreparedStatement ps1 = null;
		ResultSet rs1=null;

		Connection con=null;
		InvViewDto ivdt=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int myear=0;
		String sndQ;
		
		Date sDate=null;
		Date eDate=null;
		
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");




			
			sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
				 " concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
				 "P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
				 "G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
				 "S.SPINV_NO,S.SPINV_DT,S.ISSUE_QTY,S.ISSUE_WEIGHT,S.DRIVER_NAME,S.DRIVER_MOBILE,S.VEHICLE_NO,IFNULL(TXN_SERIALNO,0),IFNULL(TRANSFER_NO,0) "+
			" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
			" WHERE s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? "+
			" and S.SPRT_cD=P.ACCOUNT_NO AND S.SPD_GP=G.GP_CODE AND S.SPRD_CD=P1.PCODE ";
			
			String query1="select sum(issue_qty) from invsnd  s where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ?"; 
			
			
			
			ps = con.prepareStatement(sndQ);
			ps1 = con.prepareStatement(query1);
			

			ps.setInt(1, year);
			ps.setInt(2, doctp);
			ps.setInt(3, sinv);

			
			rs= ps.executeQuery();

			boolean first=true;
			while (rs.next())
			{
				ivdt = new InvViewDto();
				
				System.out.println("INWARD CHALAN "+rs.getString(1));
				
				if(first)
			 		first=false;
				ivdt.setInv_no(rs.getString(1));
				ivdt.setInv_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setChl_no(rs.getString(9));  // group name
				ivdt.setPname(rs.getString(10));
				ivdt.setSqty(rs.getInt(11));
				ivdt.setPack(rs.getInt(11)+" "+rs.getString(12));
				ivdt.setNumword(nf.format(rs.getDouble(13)));  // weight
				ivdt.setWeight(rs.getDouble(13));  // weight
				ivdt.setRemark(rs.getString(14));
				ivdt.setMac_name(rs.getString(15));
				ivdt.setMcity(rs.getString(16));
				ivdt.setCategory_hindi(rs.getString(17));  // category
				ivdt.setGroup_name_hindi(rs.getString(18)); // group name
				ivdt.setPname_hindi(rs.getString(19)); // PNAME
				ivdt.setSrate_net(rs.getDouble(20)); // rate
				ivdt.setSamnt(rs.getDouble(21));  // rent amt
				ivdt.setRound_off(rs.getDouble(22));  // roundoff
				ivdt.setNet_amt(rs.getDouble(23));// netamt
				ivdt.setSprd_cd(rs.getInt(24));
				ivdt.setSpd_gp(rs.getInt(25));
				ivdt.setGroup_name(rs.getString(26)); // group name
				ivdt.setAproval_no(rs.getString(27)); // pname
				ivdt.setCategory(rs.getString(28));  // category
				ivdt.setChl_no(rs.getString(29));
				ivdt.setChl_date(rs.getDate(30));
				ivdt.setIssue_qty(rs.getInt(31));
				ivdt.setIssue_weight(rs.getDouble(32));
				ivdt.setReceiver_name(rs.getString(33));
				ivdt.setMobile(rs.getString(34));
				ivdt.setVehicle_no(rs.getString(35));
				ivdt.setSerialno(rs.getInt(36));
				ivdt.setTransfer_no(rs.getInt(37));
				
				ps1.setInt(1, year);
				ps1.setInt(2, 40);
				ps1.setInt(3, rs.getInt(1));
				
				rs1=ps1.executeQuery();
				if(rs1.next())
				{
					ivdt.setTotqty(rs1.getInt(1));
					
				}
				rs1.close();

				
			}  //end of Invfst file
			
			


			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
		} catch (SQLException ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in InvViewDAO.getChallanDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return ivdt;
	}	
	public int addFstTransferRecord(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		PreparedStatement ps3=null;
		PreparedStatement ps2=null;
		InvViewDto fst=null;
		String query1= null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name," +
			"spinv_no,spinv_dt,driver_name,driver_mobile,issue_qty,issue_weight,vehicle_no,mark_no,txn_serialno,pack,rst_no,from_account,driver_city,transfer_no,floor_no,block_no,weight_per_qty) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
/*			String query3="update invsnd S set issue_qty=ifnull(issue_qty,0)+?, issue_weight=ifnull(issue_weight,0)+?," +
					"   samnt=samnt-?,roundoff=roundoff-?,net_amt=net_amt-? where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? "; 
*/
			String query3="update invsnd S set issue_qty=ifnull(issue_qty,0)+?, issue_weight=ifnull(issue_weight,0)+? " +
					"    where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? "; 

			
			String led ="insert into ledfile " +
			"(VDEPO_CODE,VBOOK_CD,VOU_NO,VOU_DATE,VAC_CODE,VNART_1,VAMOUNT,VDBCR,"+
			" created_by,created_date,fin_year,rcp_no,rcp_date) " +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			ps2 = con.prepareStatement(led);

			
			ps3 = con.prepareStatement(query3);
			ps=con.prepareStatement(query1);
			boolean first=true;
			for(int j=0;j<cList.size();j++)
			{
				fst = (InvViewDto) cList.get(j);

				if(first)
				{
					first=false;
					ps2.setInt(1, fst.getDepo_code());
					ps2.setInt(2, fst.getDoc_type());
					ps2.setInt(3,fst.getSinv_no());
					ps2.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
					ps2.setString(5,fst.getMac_code());
					ps2.setString(6,"Rent Against  Slip NO. "+fst.getChl_no()+" & DATE "+sdf.format(fst.getChl_date()));
					ps2.setDouble(7,fst.getNet_amt());
					ps2.setString(8,"DR");
					ps2.setInt(9, fst.getCreated_by());
					ps2.setDate(10,new java.sql.Date(fst.getCreated_date().getTime()));
					ps2.setInt(11,fst.getFin_year());
					ps2.setString(12,fst.getChl_no());
					ps2.setDate(13,new java.sql.Date(fst.getChl_date().getTime()));
					
					k= ps2.executeUpdate();
				}

				ps.setInt(1, fst.getDepo_code());
				ps.setInt(2,fst.getDoc_type());
				ps.setString(3, fst.getChl_no());
				ps.setDate(4,new java.sql.Date(fst.getChl_date().getTime()));

				ps.setString(5,fst.getMac_code());
				ps.setInt(6,fst.getSprd_cd());
				ps.setInt(7,fst.getSpd_gp());
				ps.setDouble(8,fst.getIssue_qty());
				ps.setDouble(9,fst.getSrate_net());
				ps.setDouble(10,fst.getSamnt());
				ps.setDouble(11,fst.getRound_off());
				ps.setDouble(12,fst.getNet_amt());
				ps.setString(13,fst.getRemark());
				ps.setInt(14, fst.getCreated_by());
				ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(16, fst.getCreated_by());
				ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(18,fst.getFin_year());
				ps.setDouble(19,fst.getIssue_weight());
				ps.setString(20,fst.getCategory_hindi());
				ps.setString(21,fst.getCategory());
				ps.setInt(22,fst.getSinv_no());
				ps.setDate(23,new java.sql.Date(fst.getInv_date().getTime()));
				ps.setString(24, fst.getMac_name_hindi());
				ps.setString(25, fst.getMphone());
				ps.setDouble(26, 0);
				ps.setDouble(27,0.00);
				ps.setString(28, fst.getVehicle_no());
				ps.setString(29, fst.getMark_no());
				ps.setInt(30,fst.getSerialno());
				ps.setString(31, fst.getPack());
				ps.setInt(32,fst.getRst_no());
				ps.setString(33, fst.getFromhq());
				ps.setString(34, fst.getMcity_hindi());
				ps.setInt(35,fst.getTerr_cd());
				ps.setString(36, fst.getFloor_no());
				ps.setString(37, fst.getBlock_no());
				ps.setDouble(38,fst.getWeight_per_qty());
				i=ps.executeUpdate();

				//// transfer entry 
				ps.setInt(1, fst.getDepo_code());
				ps.setInt(2,41);
				ps.setString(3, ""+fst.getTerr_cd());
				ps.setDate(4,new java.sql.Date(fst.getChl_date().getTime()));

				ps.setString(5,fst.getFromhq());
				ps.setInt(6,fst.getSprd_cd());
				ps.setInt(7,fst.getSpd_gp());
				ps.setDouble(8,fst.getIssue_qty());
				ps.setDouble(9,fst.getSrate_net());
				ps.setDouble(10,fst.getSamnt());
				ps.setDouble(11,fst.getRound_off());
				ps.setDouble(12,fst.getNet_amt());
				ps.setString(13,fst.getRemark());
				ps.setInt(14, fst.getCreated_by());
				ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(16, fst.getCreated_by());
				ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(18,fst.getFin_year());
				ps.setDouble(19,fst.getIssue_weight());
				ps.setString(20,fst.getCategory_hindi());
				ps.setString(21,fst.getCategory());
				ps.setInt(22,fst.getSinv_no());
				ps.setDate(23,new java.sql.Date(fst.getInv_date().getTime()));
				ps.setString(24, fst.getReceiver_name());
				ps.setString(25, fst.getMobile());
				ps.setDouble(26, fst.getIssue_qty());
				ps.setDouble(27,fst.getIssue_weight());
				ps.setString(28, fst.getVehicle_no());
				ps.setString(29, fst.getMark_no());
				ps.setInt(30,fst.getSerialno());
				ps.setString(31, fst.getPack());
				ps.setInt(32,fst.getRst_no());
				ps.setString(33, fst.getMac_code());
				ps.setString(34, fst.getReceiver_city());
				ps.setInt(35,Integer.parseInt(fst.getChl_no()));
				ps.setString(36, fst.getFloor_no());
				ps.setString(37, fst.getBlock_no());
				ps.setDouble(38,fst.getWeight_per_qty());

				i=ps.executeUpdate();
				
				//// transfer entry 
				

				ps3.setDouble(1, fst.getIssue_qty());
				ps3.setDouble(2, fst.getIssue_weight());
/*				ps3.setDouble(3, fst.getSamnt());
				ps3.setDouble(4, fst.getRound_off());
				ps3.setDouble(5, fst.getNet_amt());
*/				// where clause
				ps3.setInt(3, fst.getFin_year());
				ps3.setInt(4, 60);
				ps3.setInt(5, fst.getSinv_no());
				ps3.setInt(6, fst.getSerialno());
				int jj=ps3.executeUpdate();
				System.out.println("value of jj "+jj+" sinv "+fst.getSinv_no()+" "+fst.getSerialno());
			}
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			ps3.close();
			ps2.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.addFstRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				if(ps3 != null){ps3.close();}
				if(ps2 != null){ps2.close();}
				
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}	

	public int updateFstTransferRecord(ArrayList cList)
	{
		Connection con=null;
		int i=0;
		int k=0;
		PreparedStatement ps=null;
		PreparedStatement ps3=null;
		PreparedStatement ps2=null;
		InvViewDto fst=null;
		String query1= null;
		SimpleDateFormat sdf= null;
		try 
		{

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			
			sdf=new SimpleDateFormat("dd/MM/yyyy");
	
			
			query1 ="insert into invsnd " +
			"(SDEPO_CODE,SDOC_TYPE,SINV_NO,SINV_DT,SPRT_CD,SPRD_CD,SPD_GP,SQTY,SRATE_NET,SAMNT,roundoff,"+
			"net_amt,REMARK,created_by,created_date,modified_by,modified_date,fin_year,weight,category,category_name," +
			"spinv_no,spinv_dt,driver_name,driver_mobile,issue_qty,issue_weight,vehicle_no,mark_no,txn_serialno,pack,rst_no,from_account,driver_city,transfer_no) "+
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 

			
			String query3="update invsnd S set issue_qty=ifnull(issue_qty,0)+?, issue_weight=ifnull(issue_weight,0)+?   where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and serialno=? "; 

			String led ="insert into ledfile " +
			"(VDEPO_CODE,VBOOK_CD,VOU_NO,VOU_DATE,VAC_CODE,VNART_1,VAMOUNT,VDBCR,"+
			" created_by,created_date,fin_year) " +
			"values (?,?,?,?,?,?,?,?,?,?,?)"; 

			ps2 = con.prepareStatement(led);

			
			ps3 = con.prepareStatement(query3);
			ps=con.prepareStatement(query1);
			boolean first=true;
			for(int j=0;j<cList.size();j++)
			{
				fst = (InvViewDto) cList.get(j);

				if(first)
				{
					first=false;
					ps2.setInt(1, fst.getDepo_code());
					ps2.setInt(2, fst.getDoc_type());
					ps2.setInt(3,fst.getSinv_no());
					ps2.setDate(4,new java.sql.Date(fst.getInv_date().getTime()));
					ps2.setString(5,fst.getMac_code());
					ps2.setString(6,"Rent Against  Slip NO. "+fst.getSinv_no()+" & DATE "+sdf.format(fst.getInv_date()));
					ps2.setDouble(7,fst.getNet_amt());
					ps2.setString(8,"DR");
					ps2.setInt(9, fst.getCreated_by());
					ps2.setDate(10,new java.sql.Date(fst.getCreated_date().getTime()));
					ps2.setInt(11,fst.getFin_year());
					
					k= ps2.executeUpdate();
				}

				ps.setInt(1, fst.getDepo_code());
				ps.setInt(2,fst.getDoc_type());
				ps.setString(3, fst.getChl_no());
				ps.setDate(4,new java.sql.Date(fst.getChl_date().getTime()));

				ps.setString(5,fst.getMac_code());
				ps.setInt(6,fst.getSprd_cd());
				ps.setInt(7,fst.getSpd_gp());
				ps.setDouble(8,fst.getIssue_qty());
				ps.setDouble(9,fst.getSrate_net());
				ps.setDouble(10,fst.getSamnt());
				ps.setDouble(11,fst.getRound_off());
				ps.setDouble(12,fst.getNet_amt());
				ps.setString(13,fst.getRemark());
				ps.setInt(14, fst.getCreated_by());
				ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(16, fst.getCreated_by());
				ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(18,fst.getFin_year());
				ps.setDouble(19,fst.getIssue_weight());
				ps.setString(20,fst.getCategory_hindi());
				ps.setString(21,fst.getCategory());
				ps.setInt(22,fst.getSinv_no());
				ps.setDate(23,new java.sql.Date(fst.getInv_date().getTime()));
				ps.setString(24, fst.getMac_name_hindi());
				ps.setString(25, fst.getMphone());
				ps.setDouble(26, 0);
				ps.setDouble(27,0.00);
				ps.setString(28, fst.getVehicle_no());
				ps.setString(29, fst.getMark_no());
				ps.setInt(30,fst.getSerialno());
				ps.setString(31, fst.getPack());
				ps.setInt(32,fst.getRst_no());
				ps.setString(33, fst.getFromhq());
				ps.setString(34, fst.getMcity_hindi());
				ps.setInt(35,fst.getTerr_cd());

				i=ps.executeUpdate();

				//// transfer entry 
				ps.setInt(1, fst.getDepo_code());
				ps.setInt(2,41);
				ps.setString(3, ""+fst.getTerr_cd());
				ps.setDate(4,new java.sql.Date(fst.getChl_date().getTime()));

				ps.setString(5,fst.getFromhq());
				ps.setInt(6,fst.getSprd_cd());
				ps.setInt(7,fst.getSpd_gp());
				ps.setDouble(8,fst.getIssue_qty());
				ps.setDouble(9,fst.getSrate_net());
				ps.setDouble(10,fst.getSamnt());
				ps.setDouble(11,fst.getRound_off());
				ps.setDouble(12,fst.getNet_amt());
				ps.setString(13,fst.getRemark());
				ps.setInt(14, fst.getCreated_by());
				ps.setDate(15,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(16, fst.getCreated_by());
				ps.setDate(17,new java.sql.Date(fst.getCreated_date().getTime()));
				ps.setInt(18,fst.getFin_year());
				ps.setDouble(19,fst.getIssue_weight());
				ps.setString(20,fst.getCategory_hindi());
				ps.setString(21,fst.getCategory());
				ps.setInt(22,fst.getSinv_no());
				ps.setDate(23,new java.sql.Date(fst.getInv_date().getTime()));
				ps.setString(24, fst.getReceiver_name());
				ps.setString(25, fst.getMobile());
				ps.setDouble(26, fst.getIssue_qty());
				ps.setDouble(27,fst.getIssue_weight());
				ps.setString(28, fst.getVehicle_no());
				ps.setString(29, fst.getMark_no());
				ps.setInt(30,fst.getSerialno());
				ps.setString(31, fst.getPack());
				ps.setInt(32,fst.getRst_no());
				ps.setString(33, fst.getMac_code());
				ps.setString(34, fst.getReceiver_city());
				ps.setInt(35,Integer.parseInt(fst.getChl_no()));
				i=ps.executeUpdate();
				
				//// transfer entry 
				

				ps3.setDouble(1, fst.getIssue_qty());
				ps3.setDouble(2, fst.getIssue_weight());
				// where clause
				ps3.setInt(3, fst.getFin_year());
				ps3.setInt(4, 60);
				ps3.setInt(5, fst.getSinv_no());
				ps3.setInt(6, fst.getSerialno());
				int jj=ps3.executeUpdate();

			}
       
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			ps3.close();
			ps2.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in SampleEntryDAO.addFstRecord " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);
				if(ps != null){ps.close();}
				if(ps3 != null){ps3.close();}
				if(ps2 != null){ps2.close();}
				
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SampleEntryDAO.Connection.close "+e);
			}
		}

		return (i);
	}	

	public ArrayList getRecieptDetail(int depo,int div,int sinv,int year,int doctp)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		InvViewDto ivdt=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int myear=0;
		String sndQ="";
		ArrayList inv=null;
		Date sDate=null;
		Date eDate=null;
		
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");
			inv = new ArrayList();


				sndQ=" SELECT l.vou_no,l.vou_date,l.vac_code,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,l.vamount,"+
						" P.MAC_NAME,P.MCITY,ifnull(l.remark,''),ifnull(l.receiver_name,''),ifnull(l.chq_amt,0),ifnull(l.paid_by,'')" +
						" FROM ledfile l,accountmaster P "+
						" WHERE l.fin_year=? and l.vdepo_code=? and l.div_code=? and l.vbook_cd in (?,20) and l.vou_no = ? "+
						" and l.vac_code=P.ACCOUNT_NO and p.div_code=? ";
				ps = con.prepareStatement(sndQ);
			
			
			
				ps.setInt(1, year);
				ps.setInt(2, depo);
				ps.setInt(3, div);
				ps.setInt(4, doctp);
				ps.setInt(5, sinv);
				ps.setInt(6, div);

			rs= ps.executeQuery();

			
			boolean first=true;
			while (rs.next())
			{
				ivdt = new InvViewDto();
				
				if(sinv==0)
					sinv=rs.getInt(1);
				
				ivdt.setInv_no(rs.getString(1));
				ivdt.setInv_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setNet_amt(rs.getDouble(9));// netamt
				ivdt.setMac_name(rs.getString(10));
				ivdt.setMcity(rs.getString(11));
				ivdt.setRemark(rs.getString(12));
				ivdt.setReceiver_name(rs.getString(13));
				ivdt.setCn_val(rs.getDouble(14));
				ivdt.setBill_amt(rs.getDouble(14)-rs.getDouble(9));
				ivdt.setAproval_no(rs.getString(15)); // paid by
				inv.add(ivdt);

				
			}  //end of Invfst file
			
			


			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
		} catch (SQLException ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in InvViewDAO.getInvDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return inv;
	}	

	
	public InvViewDto getInvDetailPartyWritten(int party_code,String einv,int year,int doctp)
	{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2=null;
		ResultSet rs=null;
		ResultSet rs1=null;
		Connection con=null;
		InvViewDto ivdt=null;
		SimpleDateFormat sdf=null;
		NumberFormat nf=null;
		int myear=0;
		String sndQ;
		
		Date sDate=null;
		Date eDate=null;
		
		
		try {

			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			sdf = new SimpleDateFormat("dd/MM/yyyy");
			nf = new DecimalFormat("0.00");


				sndQ=" SELECT S.SINV_NO,S.SINV_DT,S.SPRT_CD,P.MAC_NAME_HINDI,P.MADD1,P.MADD2,P.MCITY_HINDI,P.MOBILE,"+
						" concat(G.GP_NAME_HINDI,' ',s.category),concat(g.gp_name_hindi,' '," +
						"P1.PNAME_HINDI,' (',S.CATEGORY,')'),S.SQTY,P1.PACK,S.WEIGHT,S.REMARK,P.MAC_NAME,P.MCITY,S.CATEGORY," +
						"G.GP_NAME_HINDI,P1.PNAME_HINDI,S.SRATE_NET,s.samnt,s.roundoff,s.net_amt,s.sprd_cd,s.spd_gp,g.gp_name,p1.pname,S.CATEGORY_NAME," +
						"IFNULL(S.DRIVER_NAME,''),IFNULL(S.DRIVER_MOBILE,''),IFNULL(S.VEHICLE_NO,''),IFNULL(S.SPINV_NO,'')," +
						"S.SPINV_DT,ifnull(s.pack,''),ifnull(s.rst_no,0),ifnull(s.mark_no,''),ifnull(s.floor_no,'')," +
						"ifnull(s.block_no,''),ifnull(s.issue_qty,0),s.serialno,ifnull(s.issue_weight,0),ifnull(s.weight_per_qty,0)," +
						"ifnull(s.written_off_qty,0),ifnull(s.written_off_weight,0) "+
						" FROM INVSND S,accountmaster P,grpmast G,PRD P1 "+
						" WHERE s.fin_year=? and s.sdoc_type=? and s.sprt_cd = ? "+
						" and S.SPRT_cD=P.ACCOUNT_NO AND S.SPD_GP=G.GP_CODE AND S.SPRD_CD=P1.PCODE ";

				String query="select sum(a.vamount ) from "+ 
				" (select sum(vamount) vamount from ledfile where fin_year=? and vac_code=? and vbook_Cd=60 "+
				" union all "+
				" select sum(vamount)*-1 vamount from ledfile where fin_year=? and vac_code=? and vbook_Cd=10)  a ";

				String query1="select sum(ifnull(issue_qty,0)),sum(ifnull(issue_weight,0)) from invsnd  s where s.fin_year=? and s.sdoc_type=? and s.sinv_no = ? and s.txn_serialno = ?"; 

				
			ps = con.prepareStatement(sndQ);
			ps1 = con.prepareStatement(query);
			ps2 = con.prepareStatement(query1);
			
			
				ps.setInt(1, year);
				ps.setInt(2, 60);
				ps.setInt(3, party_code);
			
			rs= ps.executeQuery();

			ArrayList dataList = null;  
			Vector colum = null; // Table Column
			boolean first=true;
			double rent_amt=0.00;
			while (rs.next())
			{
				ivdt = new InvViewDto();
				
				
				if(first)
				{
					first=false;
					dataList=new ArrayList();

					ps1.setInt(1, year);
					ps1.setInt(2, party_code);
					ps1.setInt(3, year);
					ps1.setInt(4, party_code);

					rs1= ps1.executeQuery();
					ivdt.setCn_val(0.00);

					if(rs1.next())
					{
						rent_amt=rs1.getDouble(1);
					}
					rs1.close();	
				}
				ivdt.setCn_val(rent_amt);
				System.out.println("cn ki value "+ivdt.getCn_val());
				ivdt.setInv_no(rs.getString(1));
				ivdt.setInv_date(rs.getDate(2));
				ivdt.setMac_code(rs.getString(3));
				ivdt.setMac_name_hindi(rs.getString(4));
				ivdt.setMadd1(rs.getString(5));
				ivdt.setMadd2(rs.getString(6));
				ivdt.setMcity_hindi(rs.getString(7));
				ivdt.setMphone(rs.getString(8));
				ivdt.setChl_no(rs.getString(9));  // group name
				ivdt.setPname(rs.getString(10));
				ivdt.setSqty(rs.getInt(11));
				ivdt.setPack(rs.getInt(11)+" "+rs.getString(12));
				ivdt.setNumword(nf.format(rs.getDouble(13)));  // weight
				ivdt.setWeight(rs.getDouble(13));  // weight
				ivdt.setRemark(rs.getString(14));
				ivdt.setMac_name(rs.getString(15));
				ivdt.setMcity(rs.getString(16));
				ivdt.setCategory_hindi(rs.getString(17));  // category
				ivdt.setGroup_name_hindi(rs.getString(18)); // group name
				ivdt.setPname_hindi(rs.getString(19)); // PNAME
				ivdt.setSrate_net(rs.getDouble(20)); // rate
				ivdt.setSamnt(rs.getDouble(21));  // rent amt
				ivdt.setRound_off(rs.getDouble(22));  // roundoff
				ivdt.setNet_amt(rs.getDouble(23));// netamt
				ivdt.setSprd_cd(rs.getInt(24));
				ivdt.setSpd_gp(rs.getInt(25));
				ivdt.setGroup_name(rs.getString(26)); // group name
				ivdt.setAproval_no(rs.getString(27)); // pname
				ivdt.setCategory(rs.getString(28));  // category
				ivdt.setReceiver_name(rs.getString(29));  // RECEIVER
				ivdt.setMobile(rs.getString(30));  // MOBILE
				ivdt.setVehicle_no(rs.getString(31));  // category
				ivdt.setPinv_no(rs.getString(32));  // category
				ivdt.setPinv_date(rs.getDate(33));  // category
				ivdt.setChl_no(rs.getString(32));  // category
				ivdt.setChl_date(rs.getDate(33));  // category
				
				

//				iqty=rs.getInt(11)-rs.getInt(39)-rs.getInt(43);
//				iweight=rs.getDouble(13)-rs.getDouble(41)-rs.getDouble(44);

				iqty=rs.getInt(11);
				iweight=rs.getDouble(13);
				
				System.out.println("YEAR "+year+" sinv "+rs.getInt(1)+" txn "+rs.getInt(40));
				
					ps2.setInt(1, year);
					ps2.setInt(2, 40);
					ps2.setInt(3, rs.getInt(1));
					ps2.setInt(4, rs.getInt(40));

					rs2=ps2.executeQuery();
				System.out.println("iqty before "+iqty);
				if(rs2.next())
				{
					
					System.out.println("value is "+rs2.getInt(1));
					ivdt.setTotqty(rs2.getInt(1));
					if(rs2.getInt(1)>0)
					{
						iqty-=rs2.getInt(1);
						iweight-=rs2.getDouble(2);
						System.out.println("iqty middle "+iqty);
					}
				}
				rs2.close();

				
				colum = new Vector();

					colum.addElement(rs.getInt(1));   //inward no  0
 				    colum.addElement(sdf.format(rs.getDate(2)));  //bill_date
//					colum.addElement(rs.getString(17));   // category 2
//					colum.addElement(rs.getString(18));   // group  3
					colum.addElement(rs.getString(19));   // item name 2
					colum.addElement(rs.getString(34));   //pack  3
					colum.addElement(iqty);   // sqty  4
					colum.addElement(iweight);   //weights  5
					colum.addElement("");   //block qty  6
					colum.addElement("");   //written qty  7
					colum.addElement("");   //wriiten weight  8
					colum.addElement("");   //manzil  9
					colum.addElement("");   //floor no  10

					
					colum.addElement(rs.getInt(25));   //spd gp 11
					colum.addElement(rs.getInt(40));   //serial no  12
					colum.addElement(rs.getInt(24));   //sprd_cd  13
					colum.addElement(rs.getString(28));   //category englist  14
					colum.addElement(rs.getDouble(20)); //rate 15
					colum.addElement(rs.getString(35));   //rst no  16
					colum.addElement(rs.getDouble(42)); //weight per kg 17
//					colum.addElement(rs.getString(37));   //floor no  18
					colum.addElement(rs.getString(36));   //marka no  18 only for gate pass
//					colum.addElement(rs.getString(38));   //gala  no  19
					colum.addElement(rs.getString(17));   // category 19
					
				if((doctp==1 || doctp==2) && iqty>0)
					dataList.add(colum);
				else if(doctp==60 || doctp==0)
					dataList.add(colum);

				
			}  //end of Invfst file
			if(!first)
				ivdt.setMarkalist(dataList);
			
			

			
			con.commit();
			con.setAutoCommit(true);

			rs.close();
			ps.close();
			ps1.close();
			ps2.close();
		} catch (SQLException ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in InvViewDAO.getInvDetail " + ex);

		}
		finally {
			try {
				//				System.out.println("No. of Records Update/Insert : "+i);

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(rs2 != null){rs2.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in InvViewDAO.Connection.close "+e);
			}
		}

		return ivdt;
	}	

	
}
