package com.coldstorage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.coldstorage.dto.CmpMsflDto;

public class CmpMsflDAO
{
	public CmpMsflDto getCompInfo(int depo_code)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		CmpMsflDto cdo=null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);
			cdo = new CmpMsflDto();

			String compQ ="select ifnull(cmp_city,''),ifnull(cmp_add1,''),ifnull(cmp_add2,''),ifnull(cmp_phone,''),ifnull(cmp_fax,''),ifnull(cmp_email,'')," +
			"ifnull(cmp_lst,''),ifnull(cmp_gst,''),ifnull(cmp_pin,'')" +
			",cmp_name,ifnull(state_code,0),ifnull(state_name,''),ifnull(cmp_panno,'')," +
			"ifnull(cmp_add3,''),ifnull(cmp_mobile,''),ifnull(aadhar_no,''),ifnull(cmp_reg,'N') ,ifnull(cmp_subj,'')" +
			"from cmpmsfl where depo_code=?";

			////////////////////////////////////////////////
			ps = con.prepareStatement(compQ);
			ps.setInt(1, depo_code);
			rs= ps.executeQuery();
			if (rs.next())
			{
				cdo.setCmp_ct(rs.getString(1));
				cdo.setCmp_pin((rs.getString(9)));
				cdo.setCmp_add1(rs.getString(2));
//				cdo.setCmp_add2(rs.getString(3) + ", "+rs.getString(1) +" - "+rs.getString(9) );
				cdo.setCmp_add2(rs.getString(3));
				cdo.setCmp_phone(rs.getString(4));
				cdo.setCmp_fax(rs.getString(5));
				cdo.setCmp_email(rs.getString(6));
				cdo.setCmp_mpst(rs.getString(7));
				cdo.setCmp_gst(rs.getString(8));
				cdo.setCmp_name(rs.getString(10));
				cdo.setState_code(rs.getString(11));
				cdo.setState_name(rs.getString(12));
				cdo.setCmp_panno(rs.getString(13));
				cdo.setCmp_add3(rs.getString(14));
				cdo.setCmp_mobile(rs.getString(15));
				cdo.setAadhar_no(rs.getString(16));
				cdo.setCmp_reg(rs.getString(17));
				cdo.setCmp_subj(rs.getString(18));

			}

		

			
			
			con.commit();
			con.setAutoCommit(true);
			rs.close();
			 
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in LoginDAO.getLoginInfo " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){rs.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLTaxDAO.Connection.close "+e);
			}
		}
		return cdo;
	}

    public int updateCompanyInfo(CmpMsflDto cmp)
    {
  	  
  	PreparedStatement ps2 = null;
		 
		Connection con=null;
		
		int i=0;
		try 
		{
			con=ConnectionFactory.getConnection();

			String query2="update cmpmsfl set cmp_add1=?,cmp_add2=?,cmp_add3=?,cmp_pin=?,cmp_phone=?,cmp_fax=?,cmp_email=?," +
			"cmp_lst=?,cmp_cst=?,cmp_gst=?,cmp_panno=?,state_code=?,state_name=?,cmp_mobile=?,aadhar_no=?,cmp_reg=? " +
			" where depo_code=?  ";

			con.setAutoCommit(false);
			
			ps2 = con.prepareStatement(query2);
			ps2.setString(1,cmp.getCmp_add1());
			ps2.setString(2,cmp.getCmp_add2());
			ps2.setString(3,cmp.getCmp_add3());
			ps2.setString(4,cmp.getCmp_pin());
			ps2.setString(5,cmp.getCmp_phone());
			ps2.setString(6,cmp.getCmp_fax());
			ps2.setString(7,cmp.getCmp_email());
			ps2.setString(8,cmp.getCmp_mpst());
			ps2.setString(9,cmp.getCmp_cst());
			ps2.setString(10,cmp.getCmp_gst());
			ps2.setString(11,cmp.getCmp_panno());
			ps2.setString(12,cmp.getState_code());
			ps2.setString(13,cmp.getState_name());
			ps2.setString(14,cmp.getCmp_mobile());
			ps2.setString(15,cmp.getAadhar_no());
			ps2.setString(16,cmp.getCmp_reg());
			// WHERE CLAUSE
			ps2.setInt(17,cmp.getCmp_depo());
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
			System.out.println("-------------Exception in SQLTransportDAO.updateCompany " + ex);
			i=-1;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : "+i);

				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in SQLCmpmsflDAO.Connection.close "+e);
			}
		}
		return i;
	}
	

}
