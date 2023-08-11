package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import com.coldstorage.dto.LoginDto;
import com.coldstorage.dto.MonthDto;
import com.coldstorage.dto.YearDto;

public class MonthDAO 
{
	public int setMonth(int mon,int year,LoginDto ldo)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;


		Connection con=null;
		int myear=0;
		int i=0;
		int xyear=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String monQ ="select mth,fin_year,fin_desc,mnth_code,mkt_year,frdate,todate,fin_ord,yy,emmyy,mkt_ord,prevmnth from perdmast " +
			" where fin_year=? and mnth_code=?";

			String totinv = "select IFNULL(min(inv_no),0),IFNULL(max(inv_no),0)  from invfst where div_code=? and depo_code=? and doc_type=40 and inv_date between ? and ? " ;


			
			
			System.out.println("mon no is in change month is "+mon);
			////////////////////////////////////////////////
			ps = con.prepareStatement(monQ);
			ps.setInt(1, year);
			ps.setInt(2, mon);
			rs= ps.executeQuery();
			if (rs.next())
			{
				ldo.setMessage("Accounting Year: "+rs.getString(3)+"    Month: "+rs.getString(1)+"-"+rs.getInt(9));
				ldo.setFooter("Financial Accounting Year: "+rs.getString(3)+"    Processing Month: "+rs.getString(1)+"-"+rs.getInt(9));
				ldo.setFin_year(rs.getInt(2));
				ldo.setMnth_code(rs.getInt(4));
				ldo.setMkt_year(rs.getInt(5));
				ldo.setSdate(rs.getDate(6));
				ldo.setEdate(rs.getDate(7));
				ldo.setMnthName(rs.getString(1));
				ldo.setMno(rs.getInt(8)-1);
				ldo.setEmnth_code(rs.getInt(10));
				ldo.setMktmno(rs.getInt(11)-1);
//				ldo.setLmnth_code(rs.getInt(12));
				myear=rs.getInt(5);
				xyear=rs.getInt(5);
				i=1;
			}
			
			
			System.out.println("before xyear "+xyear+" "+ldo.getFooter());
			
			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in MonthDAO.setMonth " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}

				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in MonthDAO.Connection.close "+e);
			}
		}
		return i;
	}

	public int setYear(int year,LoginDto ldo)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		 
		PreparedStatement fst1 = null;
		ResultSet frs1=null;
		
		PreparedStatement ps1 = null;
		ResultSet rs1=null;
		
		PreparedStatement monps=null;
		
		Statement st3=null;

		ResultSet monrs=null;
		ResultSet rs3=null;
		HashMap hm=null;

		int fyear=0;

		
		Connection con=null;
		int i=0;
		int mno=0;
		int sinv=0;
		int einv=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
			String monQ ="select mth,fin_year,fin_desc,mnth_code,mkt_year,frdate,todate,fin_ord,yy from perdmast " +
			" where fin_year=? and mnth_code=?";

			String totinv = "select ifnull(min(inv_no),0),ifnull(max(inv_no),0),max(mnth_code)  from invfst where fin_year=? and div_code=? and depo_code=? and doc_type in (39,40)  " ;

			String monfin ="select month_nm,mnth_code,frdate,todate,mth,yy,fin_ord,mm  from perdmast where fin_year=? order by fin_ord ";

			String yearMax="select frdate,todate from yearfil where  year=? and  typ='F'";
			
		

			String finyear ="select year,description,frdate,todate from yearfil where typ='F' order by year desc ";
			String mnthfin ="select mnth_code,month_nm,frdate,todate,mth,yy,fin_ord from perdmast where fin_year=? order by fin_ord";

			


			
			ps1 = con.prepareStatement(totinv);
            ps1.setInt(1,year);
            ps1.setInt(2,ldo.getDiv_code() );
            ps1.setInt(3,ldo.getDepo_code() );
            rs1 =ps1.executeQuery();

			if (rs1.next())
				
			{
				sinv=rs1.getInt(1);
				einv=rs1.getInt(2);
				mno=rs1.getInt(3);
				if(sinv==0 && ldo.getDepo_code()!=32)
					mno=Integer.parseInt(year+"04");
				if(sinv==0 && ldo.getDepo_code()==32)
					mno=Integer.parseInt(year+"07");
					
			}
			System.out.println(year+"04");

			rs1.close();
			ps1.close();
			////////////////////////////////////////////////

			
			System.out.println(sinv +" einv " +einv +" mno " +mno );
			ps = con.prepareStatement(monQ);
			ps.setInt(1, year);
			ps.setInt(2, mno);
			rs= ps.executeQuery();
			if (rs.next())
			{
				ldo.setTotinv("From Invoice No. "+sinv+" - "+einv+ "  [ "+rs.getString(1)+" ]");

				ldo.setMessage("Accounting Year: "+rs.getString(3)+"    Month: "+rs.getString(1)+"-"+rs.getInt(9));
				ldo.setFooter("Financial Accounting Year: "+rs.getString(3)+"    Processing Month: "+rs.getString(1)+"-"+rs.getInt(9));
				ldo.setFin_year(rs.getInt(2));
				ldo.setMnth_code(rs.getInt(4));
				ldo.setMkt_year(rs.getInt(5));
				ldo.setSdate(rs.getDate(6));
				ldo.setEdate(rs.getDate(7));
				ldo.setMnthName(rs.getString(1));
				ldo.setMno(rs.getInt(8)-1);
				i=1;
			}
			////////////////////////////////////////////////
			
			Vector mf=null;
			MonthDto md=null;
			/////////// fin year month///////////
			fst1=con.prepareStatement(monfin);
			fst1.setInt(1,year);
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
				md.setMnthno(frs1.getInt(8));
				md.setMkt_ord(frs1.getInt(7));
				
				mf.add(md);

			}
			ldo.setFmonth(mf);
	
			fst1=null;
			frs1=null;
			fst1=con.prepareStatement(yearMax);
			fst1.setInt(1,year);
			frs1=fst1.executeQuery();
			
			if (frs1.next())
			{
				ldo.setFr_date(frs1.getDate(1));
				ldo.setTo_date(frs1.getDate(2));
				
			}
			frs1.close();    
			fst1.close();
			

			
			/// setting fin year in dto
			hm=null;
			monps=con.prepareStatement(mnthfin);
			st3 = con.createStatement();
			rs3 =st3.executeQuery(finyear);
			Vector sf = new Vector();
			Vector nsf = new Vector();
			hm=new HashMap();
			YearDto fyd=null;
			while (rs3.next())
			{
				fyd=new YearDto();
				fyd.setYearcode(rs3.getInt(1));
				fyd.setYeardesc(rs3.getString(2));
				fyd.setMsdate(rs3.getDate(3));
				fyd.setMedate(rs3.getDate(4));
				if (fyear==0)
				{
					fyear=rs3.getInt(1);
				}
				sf.add(fyd);
				nsf.add(fyd);

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
						md.setMnthno(monrs.getInt(7));
						md.setMkt_ord(monrs.getInt(7));
						mf.add(md);
	
					}
					  hm.put(rs3.getInt(1), mf);
						
				//////////////////////////////////
			}
			ldo.setFyear(sf);
			ldo.setNewyear(nsf);
			ldo.setFmon(hm);
			
			
			//////  this is only for sales package /////
			if (year < fyear)
			{
				ldo.getFyear().remove(0);
				YearDto ydr = (YearDto) ldo.getFyear().get(0);
				ldo.setFr_date(ydr.getMsdate());
				ldo.setTo_date(ydr.getMedate());

			}

			////////////// setting  fin year /////////////


			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();
			frs1.close();
			fst1.close();
			
		} catch (Exception ex) {System.out.println("exception in year change "+ex);
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in MonthDAO.setYear " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(rs1 != null){rs1.close();}
				if(ps1 != null){ps1.close();}
				if(con != null){con.close();}
				if(frs1 != null){frs1.close();}
				if(fst1 != null){fst1.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in MonthDAO.Connection.close "+e);
			}
		}
		return i;
	}

	
	// TODO Product Opening Balance for Change Year/New Year
		public int addOpening(int year, int div , int depo)
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


				String query1 ="select depo_code,div_code,opcode,opngmar,ratemar,fin_year  " +
				" from opening where fin_year=? and div_code=? and depo_code=? ";
				
				ps1 = con.prepareStatement(query1);
				
				
				String query2 ="insert into opening (depo_code,div_code,opcode,opngapr,rateapr,fin_year)" +
				" values (?,?,?,?,?,?)";
				ps2 = con.prepareStatement(query2);

				String query3 ="select fin_year from opening where fin_year=? and div_code=? and depo_code=? ";
				
				ps3 = con.prepareStatement(query3);
				ps3.setInt(1,year);
				ps3.setInt(2,div);
				ps3.setInt(3, depo);
				

				con.setAutoCommit(false);
				rs3=ps3.executeQuery();
			   if(rs3.next())
			   {
				   System.out.println("record already hai ");
			   }
			   else
			   {
				   	ps1.setInt(1,year-1);
					ps1.setInt(2,div);
					ps1.setInt(3, depo);
					rs1=ps1.executeQuery();
					while(rs1.next())
					{
						ps2.setInt(1,rs1.getInt(1));
						ps2.setInt(2,rs1.getInt(2));
						ps2.setInt(3,rs1.getInt(3));
						ps2.setDouble(4,rs1.getInt(4));
						ps2.setDouble(5,rs1.getDouble(5));
						ps2.setInt(6,year);
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
			} catch (SQLException ex) {System.out.println("Add New Opening "+ex);
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in MonthDAO.addOpenoing for New year " + ex);
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
					System.out.println("-------------Exception in SQLMonthDAO.Connection.close "+e);
				}
			}

			return (i+j);
		}


		// TODO Cmpinv  for Change Year/New Year
		public int addCmpinv(int year, int div , int depo)
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


				String query1 ="select depo_code,div_code,inv_yr,inv_lo,fin_year  " +
				" from cmpinv where fin_year=? and div_code=? and depo_code=? ";
				
				ps1 = con.prepareStatement(query1);
				
				
				String query2 ="insert into cmpinv (depo_code,div_code,inv_yr,inv_lo,fin_year)" +
				" values (?,?,?,?,?)";
				ps2 = con.prepareStatement(query2);

				String query3 ="select fin_year from cmpinv where fin_year=? and div_code=? and depo_code=? ";
				
				ps3 = con.prepareStatement(query3);
				ps3.setInt(1,year);
				ps3.setInt(2,div);
				ps3.setInt(3, depo);
				

				con.setAutoCommit(false);
				rs3=ps3.executeQuery();
			   if(rs3.next())
			   {
				   System.out.println("record already hai ");
			   }
			   else
			   {
				   	ps1.setInt(1,year-1);
					ps1.setInt(2,div);
					ps1.setInt(3, depo);
					rs1=ps1.executeQuery();
					while(rs1.next())
					{
						ps2.setInt(1,rs1.getInt(1));
						ps2.setInt(2,rs1.getInt(2));
						if (rs1.getInt(3)==9)
							ps2.setInt(3,1);
						else
							ps2.setInt(3,rs1.getInt(3)+1);

						ps2.setString(4,rs1.getString(4));
						ps2.setInt(5,year);
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
			} catch (SQLException ex) {System.out.println("Add New Opening "+ex);
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in MonthDAO.addCmpinv for New year " + ex);
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
					System.out.println("-------------Exception in SQLMonthDAO.Connection.close "+e);
				}
			}

			return (i+j);
		}



		// TODO Product Opening Balance for Change Year/New Year
		public int addOpeningcwh(int year, int div , int depo)
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


				String query1 ="select depo_code,div_code,opcode,opngmar,ratemar,fin_year  " +
				" from openingcwh where fin_year=? and div_code=? and depo_code=? ";
				
				ps1 = con.prepareStatement(query1);
				
				
				String query2 ="insert into openingcwh (depo_code,div_code,opcode,opngapr,rateapr,fin_year)" +
				" values (?,?,?,?,?,?)";
				ps2 = con.prepareStatement(query2);

				String query3 ="select fin_year from openingcwh where fin_year=? and div_code=? and depo_code=? ";
				
				ps3 = con.prepareStatement(query3);
				ps3.setInt(1,year);
				ps3.setInt(2,div);
				ps3.setInt(3, depo);
				

				con.setAutoCommit(false);
				rs3=ps3.executeQuery();
			   if(rs3.next())
			   {
				   System.out.println("record already hai...... ");
			   }
			   else
			   {
				   	ps1.setInt(1,year-1);
					ps1.setInt(2,div);
					ps1.setInt(3, depo);
					rs1=ps1.executeQuery();
					while(rs1.next())
					{
						ps2.setInt(1,rs1.getInt(1));
						ps2.setInt(2,rs1.getInt(2));
						ps2.setInt(3,rs1.getInt(3));
						ps2.setDouble(4,rs1.getInt(4));
						ps2.setDouble(5,rs1.getDouble(5));
						ps2.setInt(6,year);
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
			} catch (SQLException ex) {System.out.println("Add New Opening "+ex);
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("-------------Exception in MonthDAO.addOpeningcwh for New year " + ex);
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
					System.out.println("-------------Exception in SQLMonthDAO.Connection.close "+e);
				}
			}

			return (i+j);
		}
		
		
	  	public HashMap getMonthMapUnlock(int year,int div,int depo,int doctp)
	  	{
	  		PreparedStatement ps2 = null;
	  		ResultSet rs=null;
	  		Connection con=null;
	  		HashMap v=null;
	  		int i=0;
	  		
	  		try {
	  			 
	  			
	  			con=ConnectionFactory.getConnection();
	  			
				String hqmap = "select mnth_code from monthlock where fin_year=? and div_code=? and depo_code=? " +
				"and doc_type=? and mnth_lock=0  ";



	  			ps2 = con.prepareStatement(hqmap);
	  			ps2.setInt(1, year);
	  			ps2.setInt(2, div);
	  			ps2.setInt(3, depo);
	  			ps2.setInt(4, doctp);
	  			 
	  			rs= ps2.executeQuery();
	  			con.setAutoCommit(false);
	  			v = new HashMap();
	  			while (rs.next())
	  			{
	  				
	  				v.put(rs.getInt(1),rs.getInt(1));
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
	  			System.out.println("-------------Exception in SQLHQDAO.getTenMonthMap " + ex);
	  			i=-1;
	  		}
	  		finally {
	  			try {
	  				System.out.println("No. of Records Update/Insert : "+i);

	  				if(rs != null){rs.close();}
	  				if(ps2 != null){ps2.close();}
	  				if(con != null){con.close();}
	  			} catch (SQLException e) {
	  				System.out.println("-------------Exception in SQLHQDAO.Connection.close "+e);
	  			}
	  		}

	  		return v;
	  	}
		

	  	
}
 