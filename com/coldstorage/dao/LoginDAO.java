package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.coldstorage.dto.AreaDto;
import com.coldstorage.dto.BookDto;
import com.coldstorage.dto.BranchDto;
import com.coldstorage.dto.GroupDto;
import com.coldstorage.dto.LoginDto;
import com.coldstorage.dto.MonthDto;
import com.coldstorage.dto.RegionDto;
import com.coldstorage.dto.StateDto;
import com.coldstorage.dto.YearDto;
import com.coldstorage.view.BaseClass;

public class LoginDAO
{
 
	private int myear=0;
	private int zyear=0;
	private int fyear=0;
	
	
	public LoginDto getLoginInfo(String lname,String pass,int division,int depo_code,int pack,int uid,int sales_div)
	{
		PreparedStatement monps=null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement areaps = null;

		Statement st1=null;
		Statement st2=null;
		Statement st3=null;

		ResultSet rs=null;
		
		ResultSet rs1=null;
		ResultSet rs2=null;
		ResultSet rs3=null;

		ResultSet rst=null;
		ResultSet arears=null;
		ResultSet monrs=null;
		
		int myear=0;
		int fyear=0;
		
		Connection con=null;
		String drvnm=null;
		String printernm=null;
		String btnnm=null;
		
		 
		LoginDto ldo=null;
		 
		UserDAO udao=null;
		try 
		{
			drvnm=ConnectionFactory.getDrvnm();
			printernm=ConnectionFactory.getPrinternm();
			btnnm=ConnectionFactory.getBtnnm();
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			ldo = new LoginDto();
			
			/// nmot required
			 
			udao=new UserDAO();
			/////////////
			
			
			
			  
				
			ldo.setDrvnm(drvnm);
			ldo.setPrinternm(printernm);
			ldo.setBtnnm(btnnm);
			
			
/*			ldo.setDivList((Vector) udao.getDivision (pack, uid)[0]);
			ldo.setDivMap((HashMap) udao.getDivision (pack, uid)[1]);
*/
			String loginQ ="select login_id,login_mnth,login_year from login where login_name=? and login_pass=md5(?) and status=? ";

			String yearQ ="select p.mnth_code, p.frdate, p.todate, f.frdate, f.todate,p.fin_year,0 emmyy,0 prevmnth from perdmast as p, yearfil as f " +
					"where p.fin_ord=? and p.fin_year=? and p.fin_year=f.year  ";


//			String mnthfil ="select mnth_code,month_nm,frdate,todate,mth,yy,mm,emmyy,mkt_ord from perdmast where mkt_year=? order by mkt_ord";

			String mnthfin ="select mnth_code,month_nm,frdate,todate,mth,yy,fin_ord,mm from perdmast where fin_year=? order by fin_ord";


//			String monmkt ="select month_nm,mnth_code,frdate,todate,mth,mm,yy,emmyy,mkt_ord,fin_ord from perdmast where mkt_year=(select " +
//					" max(year) from yearfil where typ='M') order by mkt_ord ";
			
//			String yearfil ="select year,description,frdate,todate from yearfil where typ='M' order by year desc ";

			String finyear ="select year,description,frdate,todate from yearfil  order by year desc ";


			
			String brnQ = "select depo_code,cmp_add1,cmp_add2,cmp_add3,cmp_city,cmp_phone,cmp_fax,cmp_email,cmp_lst,cmp_cst," +
					"cmp_code from cmpmsfl where cmp_code>0 order by cmp_city ";


			String grplist = "select gp_code,gp_name from grpmast  order by gp_code";
			////////////////////////////////////////////////
			ps2 = con.prepareStatement(loginQ);
			ps2.setString(1, lname);
			ps2.setString(2, pass);
			ps2.setString(3, "Y");
			rs= ps2.executeQuery();
			if (rs.next())
			{
				ldo.setLogin_id(rs.getInt(1));
				ldo.setLogin_mnth(rs.getInt(2));
				ldo.setLogin_year(rs.getInt(3));
				ldo.setDepo_code(depo_code);
				ldo.setDiv_code(division);
				ldo.setLogin_pass(pass);
			}

			//////////////////////////////////////////////////
			ps3 = con.prepareStatement(yearQ);
			ps3.setInt(1, rs.getInt(2));
			ps3.setInt(2, rs.getInt(3));
			rst= ps3.executeQuery();
			if (rst.next())
			{
				ldo.setMnth_code(rst.getInt(1));
				ldo.setSdate(rst.getDate(2));
				ldo.setEdate(rst.getDate(3));
				ldo.setFr_date(rst.getDate(4));
				ldo.setTo_date(rst.getDate(5));
				ldo.setFin_year(rst.getInt(6));
				ldo.setEmnth_code(rst.getInt(7));

				this.myear=rst.getInt(6);
			}			

/*			st1 = con.createStatement();
			rs1 =st1.executeQuery(monmkt);

			Vector mf = new Vector();
			MonthDto md=null;
			while (rs1.next())
			{
				md=new MonthDto();
				md.setMnthname(rs1.getString(1)+"-"+rs1.getString(7));
				//md.setMnthname(rs1.getString(1));
				md.setMnthcode(rs1.getInt(2));
				md.setSdate(rs1.getDate(3));
				md.setEdate(rs1.getDate(4));
				md.setMnthabbr(rs1.getString(5));
				md.setMnthno(rs1.getInt(6));
				md.setMnthyear(rs1.getInt(7));
				md.setEmnthcode(rs1.getInt(8));
				md.setMkt_ord(rs1.getInt(9));
				md.setFin_ord(rs1.getInt(10));
				mf.add(md);

			}
			ldo.setMmonth(mf);
*/			
/*			monps=null;
			monrs=null;
			monps=con.prepareStatement(mnthfil);
			st2 = con.createStatement();
			rs2 =st2.executeQuery(yearfil);
			Vector yf = new Vector();
			HashMap hm=new HashMap();
			YearDto yd=null;
			md=null;
			while (rs2.next())
			{
				yd=new YearDto();
				yd.setYearcode(rs2.getInt(1));
				yd.setYeardesc(rs2.getString(2));
				yd.setMsdate(rs2.getDate(3));
				yd.setMedate(rs2.getDate(4));
				yf.add(yd);
				if (myear==0)
					myear=rs2.getInt(1);
				
					zyear=myear;
				//// setting month accourding to year 
				monps.setInt(1, rs2.getInt(1));
				monrs =monps.executeQuery();
				mf = new Vector();
				while (monrs.next())
				{
					md=new MonthDto();
					md.setMnthcode(monrs.getInt(1));
					md.setMnthname(monrs.getString(2)+"-"+ monrs.getString(6));
					md.setSdate(monrs.getDate(3));
					md.setEdate(monrs.getDate(4));
					md.setMnthabbr(monrs.getString(5));
					md.setMnthyear(monrs.getInt(6));
					md.setMnthno(monrs.getInt(7));
					md.setEmnthcode(monrs.getInt(8));
					md.setMkt_ord(monrs.getInt(9));

					mf.add(md);

				}
				  hm.put(rs2.getInt(1), mf);
				  
					
			//////////////////////////////////				

			}
			ldo.setMyear(yf);
			ldo.setMmon(hm);
			ldo.setMkt_year(myear); // for FS 
			
*/
			
			
			
			
			/// setting fin year in dto
			HashMap hm=new HashMap();
			monps=con.prepareStatement(mnthfin);
			st3 = con.createStatement();
			rs3 =st3.executeQuery(finyear);
			Vector sf = new Vector();
			Vector nsf = new Vector();
			Vector mf = new Vector();
			MonthDto md=null;
			hm=new HashMap();
			YearDto fyd=null;
			while (rs3.next())
			{
				fyd=new YearDto();
				fyd.setYearcode(rs3.getInt(1));
				fyd.setYeardesc(rs3.getString(2));
				fyd.setMsdate(rs3.getDate(3));
				fyd.setMedate(rs3.getDate(4));
				sf.add(fyd);
				nsf.add(fyd);
				if (fyear==0)
					fyear=rs3.getInt(1);
				
				this.fyear=fyear;
				
				//// setting month accourdint to year 
					monps.setInt(1, rs3.getInt(1));
					monrs =monps.executeQuery();
					mf = new Vector();
					while (monrs.next())
					{
						md=new MonthDto();
						md.setMnthcode(monrs.getInt(1));
						md.setMnthname(monrs.getString(2)+"-"+ monrs.getString(6));
						md.setSdate(monrs.getDate(3));
						md.setEdate(monrs.getDate(4));
						md.setMnthabbr(monrs.getString(5));
						md.setMnthno(monrs.getInt(8));
						md.setMkt_ord(monrs.getInt(7));
//						md.setMnthyear(monrs.getInt(9));
						mf.add(md);
	
					}
					  hm.put(rs3.getInt(1), mf);
						
				//////////////////////////////////
			}
			ldo.setFyear(sf);
			ldo.setNewyear(nsf);
			ldo.setFmon(hm);
			////////////// setting  fin year /////////////


			
			// create branch list ////////
			areaps=null;
			arears=null;

			
			areaps = con.prepareStatement(brnQ);
			rs =areaps.executeQuery();
			BranchDto brn=null;
			Vector v=new Vector();
			while (rs.next())
			{
				brn = new BranchDto();
				brn.setDepo_code(rs.getInt(1));
				brn.setDepo_name(rs.getString(5));
				brn.setCmp_add1(rs.getString(2));
				brn.setCmp_add2(rs.getString(3));
				brn.setCmp_add3(rs.getString(4));
				brn.setCmp_city(rs.getString(5));
				brn.setCmp_phone(rs.getString(6));
				brn.setCmp_fax(rs.getString(7));
				brn.setCmp_email(rs.getString(8));
				brn.setCmp_lst(rs.getString(9));
				brn.setCmp_cst(rs.getString(10));
				brn.setCmp_code(rs.getInt(11));
				v.add(brn);
			}

//			ldo.setBrnList(v);
			// end of branch Lists

			
		
			
			
			

			// create Group list ////////
			areaps=null;
			arears=null;
			areaps = con.prepareStatement(grplist);
			arears= areaps.executeQuery();
			Vector grp=new Vector();
			GroupDto gpdt =null;
			while (arears.next())
			{
				gpdt = new GroupDto();
				gpdt.setGp_code(arears.getInt(1));
				gpdt.setGp_name(arears.getString(2));
				grp.add(gpdt);

			}	
			arears.close();
			areaps.close();

			ldo.setGrplist(grp);
			// end of group product list
	
			
			
			ldo.setBranchList(udao.getBranch(ldo.getLogin_id()));
//			ldo.setCmpList(udao.getCmpInfo(depo_code));
			
			switch(pack)
			{
				case 1:
					ldo=getSalesPackage(division, depo_code, con, ldo,sales_div);
					break;

			}
			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();
			rs3.close();
			st3.close();
			rst.close();
			ps3.close();
			
			arears.close();
			areaps.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in LoginDAO.getLoginInfo " );
			ex.printStackTrace();
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(rs3!= null){rs3.close();}
				if(rst != null){rst.close();}
				if(arears != null){arears.close();}
				if(st3 != null){st3.close();}
				if(ps2 != null){ps2.close();}
				if(ps3 != null){ps3.close();}
				if(areaps != null){areaps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in LoginDAO.Connection.close "+e);
			}
		}
		return ldo;
	}


	
	
	
	public boolean authenticateUser(String lname,String pass)
	{
		PreparedStatement ps2 = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		boolean login=false;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
//			String loginQ ="select login_id,login_mnth,login_year from login where login_name=? and login_pass=md5(?) and status=? and (ifnull(login_mac,'')='' or login_mac=?) ";

			String loginQ ="select login_id,login_mnth,login_year from login where login_name=? and login_pass=md5(?) and status=?  ";

			String loginUpdate ="update login set login_mac=? where login_name=? and login_pass=md5(?) and status=?  ";

			ps = con.prepareStatement(loginUpdate);
			ps2 = con.prepareStatement(loginQ);

			ps2.setString(1, lname);
			ps2.setString(2, pass);
			ps2.setString(3, "Y");
//			ps2.setString(4, BaseClass.ipmac);
			rs= ps2.executeQuery();
			if (rs.next())
			{
				login=true;
				
			}
			
/*			if(BaseClass.ipmac.equals(""))
				login=true;
			
			if(login)
			{
				ps.setString(1, BaseClass.ipmac);
				// where clause 
				ps.setString(2, lname);
				ps.setString(3, pass);
				ps.setString(4, "Y");

				ps.executeUpdate();
			}
*/			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();
			ps.close();

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in LoginDAO.authenticateUser " + ex);

		}
		finally {
			try {

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in LoginDAO.authenticateUser.Connection.close "+e);
			}
		}
		return login;
	}

	public boolean checkVersion()
	{
		PreparedStatement ps1 = null;
		ResultSet rs1=null;
		Connection con=null;
		boolean login=false;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

		  	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		 	Date sdate=null;
			try {
				// TODO VERSION CHANGE

				sdate = sdf.parse("01/06/2016");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			String verdate= "select br_ver_date from versiondate";

			ps1 = con.prepareStatement(verdate);
			rs1= ps1.executeQuery();

			if (rs1.next())
			{
				
				if (rs1.getDate(1).equals(sdate))
					login=true;
				else if (sdate.after(rs1.getDate(1)))
					login=true;

				
			}
			rs1.close();
			ps1.close();
			
			con.commit();
			con.setAutoCommit(true);

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in LoginDAO.checkVersion " + ex);

		}
		finally {
			try {

				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in LoginDAO.authenticateUser.Connection.close "+e);
			}
		}
		return login;
	}

	
	public boolean unlockBatch(int div_code)
	{
		Statement st = null;
		Connection con=null;
		boolean login=false;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String product ="update prd set locked=0 where div_code="+div_code;
			String batch ="update batch set locked=0 where div_code="+div_code;

			st = con.createStatement();
			st.executeUpdate(product);
			
			st=null;
			st = con.createStatement();
			st.executeUpdate(batch);
			
			
			con.commit();
			con.setAutoCommit(true);
			st.close();

		} 
		catch (Exception ex) 
		{
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in LoginDAO.unlockBatch " + ex);

		}
		finally {
			try {
				if(st != null){st.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in LoginDAO.unlockBatch.Connection.close "+e);
			}
		}
		return login;
	}	
	
	


public LoginDto getSalesPackage(int division,int depo_code,Connection con,LoginDto ldto,int sales_div)
{
	PreparedStatement ps4 = null;
	PreparedStatement areaps = null;
	PreparedStatement regps = null;
	PreparedStatement bookps = null;
	PreparedStatement facps = null;
	PreparedStatement curr=null;
	PreparedStatement fst1 = null;
	ResultSet frs1=null;

	
	PreparedStatement st1=null;  // for set current mkt month  01/10/2015
	ResultSet rs1=null;  // for set current mkt month 01/10/2015
	
	
	ResultSet rs4=null;
	ResultSet rcurr=null;

	ResultSet rstt=null;
	ResultSet arears=null;
	ResultSet regrs=null;
	ResultSet bookrs=null;
	ResultSet facrs=null;
	
	int fyear=this.fyear;
	int myear=this.myear;
	int xdiv=0;
	
	String currmonth =null;
	
	LoginDto ldo=ldto;
	ProductDAO pd=null;
	PartyDAO prtdao=null;
	TransportDAO tdao=null;
	 
	try 
	{
		pd= new ProductDAO();
		prtdao=  new PartyDAO();
        tdao=new TransportDAO();	 
		xdiv=division;
		
		xdiv=sales_div;
		String yearMax="select frdate,todate,year  from yearfil where  year= (select max(year) from yearfil where typ='F')";
		String monfin ="select month_nm,mnth_code,frdate,todate,mth,yy,mm,fin_ord,0 mkt_year from perdmast where fin_year=? order by fin_ord ";
//		String cmpQ= "select cmp_code,cmp_type from cmpinv where depo_code=? and div_code=? and fin_year=? ";

		String monmkt ="select month_nm,mnth_code,frdate,todate,mth,mm,yy,0 emmyy,0 mkt_ord,fin_ord from perdmast where fin_year=? order by fin_ord ";
		
		
		String distlst = "select dist_code,dist_name,reg_code from distmast where  fin_year=? and div_code=? and depo_code=? and ifnull(del_tag,'')<>'D' order by dist_code";
		String reglst = "select reg_code,reg_name from regionmast where  fin_year=? and div_code=? and depo_code=? and ifnull(del_tag,'')<>'D' and reg_name <>'' order by  reg_code";
		
		

		
		

		
		currmonth = "select mth,fin_year,fin_desc,mnth_code,0 mkt_year,frdate,todate,fin_ord,yy,0 emmyy,0 mkt_ord,0 prevmnth from perdmast where fin_year=(select max(year) from yearfil )  "+
		" and mm=(select month(curDate()) from dual ) " ;



		
		
		
        ////  CURRENT BILLING MONTH AND FINANCIAL YEAR AT LOGIN TIME //////////////
		curr = con.prepareStatement(currmonth) ;
		rcurr =curr.executeQuery();

		if (rcurr.next())
		{
			ldo.setMessage("Accounting Year: "+rcurr.getString(3));
			ldo.setFooter("Financial Accounting Year: "+rcurr.getString(3));
//			ldo.setMessage("Accounting Year: "+rcurr.getString(3)+"    Month: "+rcurr.getString(1)+"-"+rcurr.getInt(9));
//			ldo.setFooter("Financial Accounting Year: "+rcurr.getString(3)+"    Processing Month: "+rcurr.getString(1)+"-"+rcurr.getInt(9));
			ldo.setFin_year(rcurr.getInt(2));
			ldo.setMnth_code(rcurr.getInt(4));
			ldo.setMkt_year(rcurr.getInt(5));
			ldo.setSdate(rcurr.getDate(6));
			ldo.setEdate(rcurr.getDate(7));
			ldo.setMnthName(rcurr.getString(1));
			ldo.setMno(rcurr.getInt(8)-1);
			ldo.setMktmno(rcurr.getInt(11)-1);

			//////  this is only for sales package /////
			if (rcurr.getInt(2)< fyear)
			{
				ldo.getFyear().remove(0);
				YearDto ydr = (YearDto) ldo.getFyear().get(0);
				ldo.setFr_date(ydr.getMsdate());
				ldo.setTo_date(ydr.getMedate());

			}
			if (rcurr.getInt(5)< myear)
			{
				ldo.getMyear().remove(0);

			}



			fyear=rcurr.getInt(2);
			myear=rcurr.getInt(5);

			ldo.setEmnth_code(rcurr.getInt(10));
			
			System.out.println("value of mno in logindao "+ldo.getMno());
		}

		curr.close();
		rcurr.close();
		curr=null;
		rcurr=null;
        ////  END CURRENT BILLING MONTH AND FINANCIA YEAR AT LOGIN TIME //////////////
		
		
		
		
		
		
		
		


		////////////////////////////////////////

		


		

		

		
/// yeha se cutt kiya hai curr

           ////  CURRENT BILLING MONTH AND FINANCIAL YEAR AT LOGIN TIME //////////////
			curr = con.prepareStatement(currmonth) ;
			rcurr =curr.executeQuery();

			if (rcurr.next())
			{
				ldo.setMessage("Accounting Year: "+rcurr.getString(3));
				ldo.setFooter("Financial Accounting Year: "+rcurr.getString(3));
//				ldo.setFooter("Financial Accounting Year: "+rcurr.getString(3)+"    Processing Month: "+rcurr.getString(1)+"-"+rcurr.getInt(9));
				ldo.setFin_year(rcurr.getInt(2));
				ldo.setMnth_code(rcurr.getInt(4));
				ldo.setMkt_year(rcurr.getInt(5));
				ldo.setSdate(rcurr.getDate(6));
				ldo.setEdate(rcurr.getDate(7));
				ldo.setMnthName(rcurr.getString(1));
				ldo.setMno(rcurr.getInt(8)-1);
				ldo.setMktmno(rcurr.getInt(11)-1);
				
				//////  this is only for sales package /////
				if (rcurr.getInt(2)< fyear)
				{
					ldo.getFyear().remove(0);
					YearDto ydr = (YearDto) ldo.getFyear().get(0);
					ldo.setFr_date(ydr.getMsdate());
					ldo.setTo_date(ydr.getMedate());

				}
 
				if (rcurr.getInt(5)< zyear)
				{
					ldo.getMyear().remove(0);

				}

				
				
				st1 = con.prepareStatement(monmkt);
				st1.setInt(1, myear);
				rs1 =st1.executeQuery();

				Vector mf = new Vector();
				MonthDto md=null;
				while (rs1.next())
				{
					md=new MonthDto();
					md.setMnthname(rs1.getString(1)+"-"+rs1.getString(7));
					//md.setMnthname(rs1.getString(1));
					md.setMnthcode(rs1.getInt(2));
					md.setSdate(rs1.getDate(3));
					md.setEdate(rs1.getDate(4));
					md.setMnthabbr(rs1.getString(5));
					md.setMnthno(rs1.getInt(6));
					md.setMnthyear(rs1.getInt(7));
					md.setEmnthcode(rs1.getInt(8));
					md.setMkt_ord(rs1.getInt(9));
					md.setFin_ord(rs1.getInt(10));
					mf.add(md);

				}
				ldo.setMmonth(mf);

				
				

				fyear=rcurr.getInt(2);
				myear=rcurr.getInt(5);

				ldo.setEmnth_code(rcurr.getInt(10));
//				ldo.setLmnth_code(rcurr.getInt(12));

				System.out.println("value of mno in logindao "+ldo.getMno());
			}
			else
			{
				
				st1 = con.prepareStatement(monmkt);
				st1.setInt(1, myear);
				rs1 =st1.executeQuery();

				Vector mf = new Vector();
				MonthDto md=null;
				while (rs1.next())
				{
					md=new MonthDto();
					md.setMnthname(rs1.getString(1)+"-"+rs1.getString(7));
					//md.setMnthname(rs1.getString(1));
					md.setMnthcode(rs1.getInt(2));
					md.setSdate(rs1.getDate(3));
					md.setEdate(rs1.getDate(4));
					md.setMnthabbr(rs1.getString(5));
					md.setMnthno(rs1.getInt(6));
					md.setMnthyear(rs1.getInt(7));
					md.setEmnthcode(rs1.getInt(8));
					md.setMkt_ord(rs1.getInt(9));
					md.setFin_ord(rs1.getInt(10));
					mf.add(md);

				}
				ldo.setMmonth(mf);


			}
			
            ////  END CURRENT BILLING MONTH AND FINANCIA YEAR AT LOGIN TIME //////////////
		
		Vector mf = null;
		MonthDto md=null;
		/////////// fin year month///////////
		fst1=con.prepareStatement(monfin);
		fst1.setInt(1,fyear);
		frs1=fst1.executeQuery();
		
		mf = new Vector();
		while (frs1.next())
		{
			md=new MonthDto();
			md.setMnthname(frs1.getString(1)+"-"+frs1.getString(6));
			md.setMnthcode(frs1.getInt(2));
			md.setSdate(frs1.getDate(3));
			md.setEdate(frs1.getDate(4));
			md.setMnthabbr(frs1.getString(5));
			md.setMnthno(frs1.getInt(7));
			md.setMkt_ord(frs1.getInt(8));
			md.setMnthyear(frs1.getInt(9));   // mkt year
			mf.add(md);
		}
		ldo.setFmonth(mf);
//		ldo.setMno(0);

	//	System.out.println("fyear @@@@@@"+fyear+" mncode "+md.getMnthcode());
		
		/////////////////////////////////////////
		fst1=null;
		frs1=null;
		fst1=con.prepareStatement(yearMax);
		frs1=fst1.executeQuery();
		
		if (frs1.next())
		{
			if (frs1.getInt(3)<= fyear)
			{
				ldo.setFr_date(frs1.getDate(1));
				ldo.setTo_date(frs1.getDate(2));
			}
			
		}
		
		
		
		// set Product List for Sales Package
		
 /*		ldo.setSmsList(prtdao.getSmsNm(depo_code,division,fyear,myear,45,sales_div));
		ldo.setSmsmap(prtdao.getSmsNmMap(depo_code,division,fyear,myear,45,sales_div));
 		ldo.setPrtList(prtdao.getPartyNm(depo_code,division,fyear,myear,45,sales_div));
 		ldo.setPrtList1(prtdao.getPartyNm(depo_code,division,fyear,myear,99,sales_div));
		ldo.setPrtmap(prtdao.getPartyNmMap(depo_code,division,fyear,myear,45,sales_div));
		ldo.setPrdlist(pd.getPrdList(division,depo_code,0));
		ldo.setPrdmap(pd.getPrdMap(division,depo_code,0));
		ldo.setTransporterList(tdao.getTransportList(depo_code));
*/
		curr.close();
		rcurr.close();
		fst1.close();
		frs1.close();
		st1.close();
		rs1.close();

	} catch (Exception ex) { ex.printStackTrace();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("-------------Exception in LoginDAO.getLoginInfo " );
		ex.printStackTrace();
	}
	finally {
		try {
			System.out.println("No. of Records Update/Insert : " );

			if(rstt != null){rstt.close();}
			if(rcurr != null){rcurr.close();}
			if(arears != null){arears.close();}
			if(facrs != null){facrs.close();}
			if(curr != null){curr.close();}
			if(ps4 != null){ps4.close();}
			if(areaps != null){areaps.close();}
			if(facps != null){facps.close();}
//			if(con != null){con.close();}
			if(fst1 != null){fst1.close();}
			if(frs1 != null){frs1.close();}
			if(st1 != null){st1.close();}
			if(rs1 != null){rs1.close();}

		} catch (SQLException e) {
			System.out.println("-------------Exception in LoginDAO.Connection.close "+e);
		}
	}
	return ldo;
}





}
