package com.coldstorage.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.coldstorage.dto.BranchDto;
import com.coldstorage.dto.CmpInvDto;
import com.coldstorage.dto.DivisionDto;
import com.coldstorage.dto.LoginDto;
import com.coldstorage.dto.UserDto;
import com.coldstorage.view.BaseClass;


public class UserDAO 
{
	public Object[] getDivision(int pakcode,int uid)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		Object ob[] = new Object[2];
		Vector v =null;
		HashMap divMap=null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);



/*			String divQ = "select div_code,div_name  from divmast where pack_code=? and div_code in "+ 
					"(select div_id from userdiv where user_id=? and status='Y') ";
*/
			
			String divQ = "select div_code,div_name  from divmast "; 

			
			ps = con.prepareStatement(divQ);
//			ps.setInt(1, pakcode);
//			ps.setInt(2, uid);
			rs =ps.executeQuery();

			DivisionDto dv=null;
			v = new Vector();
			divMap = new HashMap();
			while (rs.next())
			{
				dv = new DivisionDto();
				dv.setDiv_code(rs.getInt(1));
				dv.setDiv_name(rs.getString(2));
				v.add(dv);
				divMap.put(rs.getInt(1),rs.getString(2));
			}

				ob[0]=v;
				ob[1]=divMap;

			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.getDivision " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return ob;
	}



	public Vector getPackage(int uid)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v =null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			if(uid==0)
				uid=1;

			String divQ = "select pack_code,pack_name from packagemast where pack_code in "+ 
					"(select pack_id from userpack where user_id=? and status='Y') ";
			ps = con.prepareStatement(divQ);
			ps.setInt(1, uid);
			rs =ps.executeQuery();
			DivisionDto dv=null;
			v = new Vector();
			while (rs.next())
			{
				dv = new DivisionDto();
				dv.setDiv_code(rs.getInt(1));
				dv.setDiv_name(rs.getString(2));
				v.add(dv);
			}



			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.getPackage " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close --- getPackage "+e);
			}
		}
		return v;
	}


	public Vector getBranch(int uid)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v =null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String brnQ = "select depo_code,cmp_add1,cmp_add2,cmp_add3,cmp_city," +
					"cmp_phone,cmp_fax,cmp_email,cmp_lst,cmp_cst," +
					"cmp_code,cmp_name,cmp_abvr,state_code from cmpmsfl where depo_code in " +
					"(select depo_code from userdepo where user_id=? and status='Y') "+
					"order by cmp_city" ;



			ps = con.prepareStatement(brnQ);
			ps.setInt(1, uid);
			rs =ps.executeQuery();
			BranchDto brn=null;
			v = new Vector();
			while (rs.next())
			{
				brn = new BranchDto();
				brn.setDepo_code(rs.getInt(1));
				brn.setDepo_name(rs.getString(12));
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
				brn.setCmp_abvr(rs.getString(13));
				brn.setState_Code(rs.getInt(14));
				v.add(brn);
			}



			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.getBranch " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return v;
	}


	public Vector getCmpInfo(int depo)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v =null;
		CmpInvDto cmp=null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String brnQ = "select cmp_code, cmp_name, cmp_type, inv_doc, pur_doc, cr_doc, ret_doc, vat_doc, tr_doc,ifnull(remark,'') "+ 
			"from  cmpinv where depo_code=? ";




			ps = con.prepareStatement(brnQ);
			ps.setInt(1, depo);
			rs =ps.executeQuery();
			v = new Vector();
			while (rs.next())
			{
				cmp = new CmpInvDto();
				cmp.setCmp_code(rs.getInt(1));
				cmp.setCmp_name(rs.getString(2));
				cmp.setCmp_type(rs.getString(3));
				cmp.setInv_doc(rs.getInt(4));
				cmp.setPur_doc(rs.getInt(5));
				cmp.setCr_doc(rs.getInt(6));
				cmp.setRet_doc(rs.getInt(7));
				cmp.setVat_doc(rs.getInt(8));
				cmp.setTr_doc(rs.getInt(9));
				cmp.setRemark(rs.getString(10));
				v.add(cmp);
			}



			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.getCmpInv(Company Names) " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return v;
	}
	
	

	public int getUserId(String lname,String pass)
	{
		PreparedStatement ps2 = null;
		ResultSet rs=null;
		Connection con=null;
		int uid=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String loginQ ="select login_id,login_mnth,login_year from login where login_name=? and login_pass=md5(?) and status=? ";
			ps2 = con.prepareStatement(loginQ);
			ps2.setString(1, lname);
			ps2.setString(2, pass);
			ps2.setString(3, "Y");
			rs= ps2.executeQuery();
			if (rs.next())
			{
				uid=rs.getInt(1);
			}

			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps2.close();

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.getUserId " + ex);

		}
		finally {
			try {

				if(rs != null){rs.close();}
				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.getUserId.Connection.close "+e);
			}
		}
		return uid;
	}

	public Vector getUserRights(String tp)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		Vector v =null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String menu="select aa.*,p.pack_name from (select a.*,s.smenu_code,ifnull(s.smenu_name,'') from " + 
					"(select m.pack_code,t.tab_code,t.tab_name,m.menu_code,m.menu_name from tabmaster t,menumaster m " +
					"where t.tab_code=m.tab_code and m.pack_code < 5 and m.tran_type=? and m.class_name is not null  order by m.pack_code,m.tab_code,m.menu_code) a " +
					"left join submenumaster s on  a.pack_code=s.pack_Code and a.tab_code=s.tab_code and a.menu_code=s.menu_code "+
					"and s.tran_type=? ) aa, packagemast p where aa.pack_code=p.pack_code and p.status='Y' ";

			ps = con.prepareStatement(menu);
			ps.setString(1, tp);
			ps.setString(2, tp);
			rs =ps.executeQuery();
			UserDto usr=null;
			v = new Vector();
			while (rs.next())
			{
				usr = new UserDto();
				usr.setPack_code(rs.getInt(1));
				usr.setTab_code(rs.getInt(2));
				usr.setTab_name(rs.getString(3));
				usr.setMenu_code(rs.getInt(4));
				usr.setMenu_name(rs.getString(5).trim()+(rs.getString(7).equals("") ? "" : "  ["+rs.getString(7).trim()+"]"));
				usr.setSmenu_code(rs.getInt(6));
				usr.setSmenu_name(rs.getString(7));
				usr.setPack_name(rs.getString(8));

				v.add(usr);
			}



			con.commit();
			con.setAutoCommit(true);
			rs.close();
			ps.close();

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.getUserRights " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return v;
	}


	public int createNewUser(LoginDto ldto)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int userID=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String loginTable=" insert into login " +
					" (login_name,login_pass,status,prefix,fname,lname,designation,department,emailid,login_mnth,login_year) " +
					" values(?,md5(?),?,?,?,?,?,?,?,MONTH(CURDATE()),(select 0 mkt_year from perdmast where fin_year=YEAR(CURDATE()) and mm=MONTH(CURDATE())) ) ";


			ps= con.prepareStatement(loginTable, Statement.RETURN_GENERATED_KEYS);  
			ps.setString(1,ldto.getLogin_name());
			ps.setString(2,ldto.getLogin_pass());
			ps.setString(3,ldto.getIsActive());
			ps.setString(4,ldto.getPrefix());
			ps.setString(5,ldto.getFname());
			ps.setString(6,ldto.getLname());
			ps.setString(7,ldto.getDesignation());
			ps.setString(8,ldto.getDepartment());
			ps.setString(9,ldto.getEmaiId());

			ps.executeUpdate();  
			rs = ps.getGeneratedKeys();    
			rs.next();  
			userID = rs.getInt(1);

			rs.close();
			ps.close();
			rs=null;

			//insert user branches
			ps.close();
			ps=null;

			String userBranch = "insert into userdepo values(?,?,?)";
			ps=con.prepareStatement(userBranch);

				ps.setInt(1,userID);
//				ps.setInt(2,ldto.getDepo_code());
				ps.setInt(2,BaseClass.loginDt.getDepo_code());
				ps.setString(3,"Y");
				ps.executeUpdate();
			

			//insert all reports for user (sales/sample/accounts).
			ps.close();
			ps=null;
			
			String insertAllReports="insert into userrights (select "+userID+" as uid,m.pack_code,m.tab_code,m.menu_code,ifnull(s.smenu_code,0)" +
			" smenu_code,'N' stattus from menumaster m "+
			" left join submenumaster s on "+ 
			" m.pack_code=s.pack_code and m.tab_code=s.tab_code and m.menu_code=s.menu_code "+
			" where m.pack_code<5 order by m.pack_code,m.tab_code,m.menu_code,smenu_code)";
			ps=con.prepareStatement(insertAllReports);
			ps.executeUpdate();
			
			//updated user reports
			ps.close();
			ps=null;

			
			String userReport = "update userrights set status=? where user_id=? and pack_code=? and tab_code=? and menu_code=? and smenu_code=?";
			ps=con.prepareStatement(userReport);
			for(UserDto udt : ldto.getReportList())
			{
				ps.setString(1,udt.getStatus());
				ps.setInt(2,userID);
				ps.setInt(3,udt.getPack_code());
				ps.setInt(4,udt.getTab_code());
				ps.setInt(5,udt.getMenu_code());
				ps.setInt(6,udt.getSmenu_code());
			
				ps.executeUpdate();
			}


			con.commit();
			con.setAutoCommit(true);

			ps.close();

		} 
		catch (Exception ex) 
		{   ex.printStackTrace();
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.createNewUser " + ex);
			userID=0;
		}
		finally 
		{
			try 
			{
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} 
			catch (SQLException e) 
			{
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return userID;
	}

	public int copyUserRightFromExistingUser(LoginDto ldto)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int userID=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String loginTable=" insert into login " +
					" (login_name,login_pass,status,prefix,fname,lname,designation,department,emailid,login_mnth,login_year) " +
					" values(?,md5(?),?,?,?,?,?,?,?,MONTH(CURDATE()),(select mkt_year from perdmast where fin_year=YEAR(CURDATE()) and mm=MONTH(CURDATE())) ) ";


			ps= con.prepareStatement(loginTable, Statement.RETURN_GENERATED_KEYS);  
			ps.setString(1,ldto.getLogin_name());
			ps.setString(2,ldto.getLogin_pass());
			ps.setString(3,ldto.getIsActive());
			ps.setString(4,ldto.getPrefix());
			ps.setString(5,ldto.getFname());
			ps.setString(6,ldto.getLname());
			ps.setString(7,ldto.getDesignation());
			ps.setString(8,ldto.getDepartment());
			ps.setString(9,ldto.getEmaiId());

			ps.executeUpdate();  
			rs = ps.getGeneratedKeys();    
			rs.next();  
			userID = rs.getInt(1);

			rs.close();
			rs=null;


			
			CallableStatement callableStatement = con.prepareCall("{ call copyUserRights(?,?) }");
			callableStatement.setInt(1, ldto.getLogin_id()); //existin user
			callableStatement.setInt(2, userID);//new user
			
			callableStatement.execute();


			con.commit();
			con.setAutoCommit(true);

			ps.close();

		} 
		catch (Exception ex) 
		{
			try 
			{
				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.createNewUser " + ex);
			ex.printStackTrace();
			userID=0;
		}
		finally 
		{
			try 
			{
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} 
			catch (SQLException e) 
			{
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return userID;
	}
	
	
	
	public Vector<Object> getUserForUpdation()
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		ResultSet rs1=null;

		Connection con=null;
		Vector<Object> userList=null;
		List<BranchDto> branchVector=null;
		List<Integer> packList=null;
		List<Integer> divList=null;
		List<UserDto> reportList=null;
		int count=0;
		int pack[]=new int[10];
		try   
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String loginTable="select login_id,login_name,login_pass,status,prefix,fname,lname,designation,department,emailid from login where login_name <> 'admin'";

			String brnQ = "select c.depo_code,c.cmp_add1,c.cmp_add2,c.cmp_add3,c.cmp_city," +
					"c.cmp_phone,c.cmp_fax,c.cmp_email,c.cmp_lst,c.cmp_cst,c.cmp_panno,c.aadhar_no," +
					"c.cmp_code,ifnull(d.status,'N') from cmpmsfl c left join userdepo d on c.depo_code=d.depo_code and  d.user_id=? " +
					"order by c.cmp_city" ;

//			String packQ="select * from userpack where user_id=? and status='Y'";

			String packQ="select u.user_id,u.pack_id,ifnull(u.status,'N')  from packagemast p left join userpack u on p.pack_code=u.pack_id and u.user_id=? and u.status='Y'";


			
/*			String userRightsQ= "select t.*,ifnull(u.status,'N')  from  "+
			"(select aa.*,p.pack_name from "+ 
			"(select a.*,ifnull(s.smenu_code,0) smenu_code,ifnull(s.smenu_name,'') smenu_name from "+   
			"(select m.pack_code,t.tab_code,t.tab_name,m.menu_code,m.menu_name,m.tran_type from tabmaster t,menumaster m "+  
			"where t.tab_code=m.tab_code and m.pack_code < 4  ) a   "+
			"left join submenumaster s on  a.pack_code=s.pack_Code and a.tab_code=s.tab_code and a.menu_code=s.menu_code) aa "+
			",packagemast p where aa.pack_code=p.pack_code and p.status='Y') t left join userrights u "+
			" on  u.pack_code=t.pack_code and u.tab_code=t.tab_code and u.menu_code=t.menu_code and u.smenu_code=t.smenu_code and u.user_id=? "+
			" order by t.pack_code,t.tab_code,t.menu_code,t.smenu_code ";  
*/

			
			String userRightsQ="select t.*,ifnull(u.status,'N')  from "+  
			" (select t.tab_code,t.tab_name,m.menu_code,m.menu_name,m.tran_type from tabmaster t,menumaster m "+   
			" where t.tab_code=m.tab_code )t"+
			" left join userrights u "+
			" on  u.tab_code=t.tab_code and u.menu_code=t.menu_code  and u.user_id=?"+ 
			" order by t.tab_code,t.menu_code ";  

	
			Statement st=con.createStatement();
			rs = st.executeQuery(loginTable);
			userList = new Vector<Object>();
			LoginDto ldto =null;
			while(rs.next())
			{

				ldto = new LoginDto();

				
				ldto.setLogin_id(rs.getInt(1));
				ldto.setLogin_name(rs.getString(2));
				ldto.setLogin_pass(rs.getString(3));
				ldto.setIsActive(rs.getString(4));
				ldto.setPrefix(rs.getString(5));
				ldto.setFname(rs.getString(6));
				ldto.setLname(rs.getString(7));
				ldto.setDesignation(rs.getString(8));
				ldto.setDepartment(rs.getString(9));
				ldto.setEmaiId(rs.getString(10));


				//User Selected Reports...

				ps = con.prepareStatement(userRightsQ);
				ps.setInt(1, rs.getInt(1));
				rs1 =ps.executeQuery();
				UserDto udto=null;
				reportList = new ArrayList<UserDto>();
				boolean addRecord=false;
				while (rs1.next())
				{
						udto = new UserDto();
						udto.setTab_code(rs1.getInt(1));
						udto.setTab_name(rs1.getString(2));
						udto.setMenu_code(rs1.getInt(3));
						udto.setMenu_name(rs1.getString(4));
						udto.setTran_type(rs1.getString(5));
						udto.setStatus(rs1.getString(6));
						reportList.add(udto);
					
					
				}
				rs1.close();
				ps.close();

				ps=null;
				rs1=null;
				ldto.setReportList(reportList);
				
				
				//adding logindto object in userlist...
				userList.add(ldto);

			}




			con.commit();
			con.setAutoCommit(true);

			 

		} 
		catch (Exception ex) 
		{
			try 
			{

				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.createNewUser " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return userList;
	}


	public int updateUser(LoginDto ldto)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;
		List<UserDto> data =null;
		int userID=0;
		UserDto udto=null;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String loginTable=" update login set status=?,prefix=?,fname=?,lname=?,designation=?,department=?,emailid=? where login_id=?  ";

			ps= con.prepareStatement(loginTable);  
			
			ps.setString(1,ldto.getIsActive());
			ps.setString(2,ldto.getPrefix());
			ps.setString(3,ldto.getFname());
			ps.setString(4,ldto.getLname());
			ps.setString(5,ldto.getDesignation());
			ps.setString(6,ldto.getDepartment());
			ps.setString(7,ldto.getEmaiId());
			ps.setInt(8,ldto.getLogin_id());
			
			userID=ps.executeUpdate();  
			

			

			//insert user branches
			ps.close();
			ps=null;

			
			String userDepoStatusN = "delete from userdepo where user_id=? ";
			ps=con.prepareStatement(userDepoStatusN);
			ps.setInt(1,ldto.getLogin_id());
			ps.executeUpdate();
			

			ps.close();
			ps=null;
			
			String userBranch = "insert into userdepo (user_id,depo_code,status) values (?,?,?) ";
			ps=con.prepareStatement(userBranch);
				ps.setInt(1,ldto.getLogin_id());
				ps.setInt(2,BaseClass.loginDt.getDepo_code());
				ps.setString(3,"Y");
				
				ps.executeUpdate();


			//update user report
			ps.close();
			ps=null;

			
			String userFavourite = "select pack_Code,tab_code,menu_code,smenu_code from  userrights " +
					"where user_id=? and ifnull(status,'')='Y' and ifnull(fav_type,'')='F' ";
			ps=con.prepareStatement(userFavourite);
			ps.setInt(1,ldto.getLogin_id());
			rs=ps.executeQuery();
			data = new ArrayList<UserDto>();

			while (rs.next())
			{
				udto=new UserDto();
				udto.setPack_code(rs.getInt(1));
				udto.setTab_code(rs.getInt(2));
				udto.setMenu_code(rs.getInt(3));
				udto.setSmenu_code(rs.getInt(4));
				data.add(udto);
			}
			rs.close();
			ps.close();
			ps=null;

			
			
			String userRightsStatusN = "delete from  userrights where user_id=? and menu_code not in(108,111) ";
			ps=con.prepareStatement(userRightsStatusN);
			ps.setInt(1,ldto.getLogin_id());
			ps.executeUpdate();
			

			ps.close();
			ps=null;
			
			
			String userReport = "insert into userrights (USER_ID,PACK_CODE,TAB_CODE,MENU_CODE,SMENU_CODE,STATUS) VALUES (?,?,?,?,?,?) ";
			ps=con.prepareStatement(userReport);
			for(UserDto udt : ldto.getReportList())
			{
				if(udt.getStatus().equalsIgnoreCase("Y"))
				{
					ps.setInt(1,ldto.getLogin_id());
					ps.setInt(2,1);
					ps.setInt(3,udt.getTab_code());
					ps.setInt(4,udt.getMenu_code());
					ps.setInt(5,udt.getSmenu_code());
					ps.setString(6,udt.getStatus());

					ps.executeUpdate();
				}
			}

			ps=null;
			String updateuserFavourite = "update userrights set fav_type='F' " +
					"where USER_ID=? and PACK_CODE =? and TAB_CODE=? and MENU_CODE= ? and SMENU_CODE=? and status='Y'";
			ps=con.prepareStatement(updateuserFavourite);
	
			
			for(UserDto udt : data )
			{
					ps.setInt(1,ldto.getLogin_id());
					ps.setInt(2,1);
					ps.setInt(3,udt.getTab_code());
					ps.setInt(4,udt.getMenu_code());
					ps.setInt(5,udt.getSmenu_code());

					ps.executeUpdate();
			}


			con.commit();
			con.setAutoCommit(true);

			ps.close();

		} 
		catch (Exception ex) 
		{ ex.printStackTrace();
			try 
			{

				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.updateUser " + ex);
			userID=0;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return userID;
	}

	public int updateUserOld(LoginDto ldto)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int userID=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String loginTable=" update login set status=?,prefix=?,fname=?,lname=?,designation=?,department=?,emailid=? where login_id=?  ";

			ps= con.prepareStatement(loginTable);  
			
			ps.setString(1,ldto.getIsActive());
			ps.setString(2,ldto.getPrefix());
			ps.setString(3,ldto.getFname());
			ps.setString(4,ldto.getLname());
			ps.setString(5,ldto.getDesignation());
			ps.setString(6,ldto.getDepartment());
			ps.setString(7,ldto.getEmaiId());
			ps.setInt(8,ldto.getLogin_id());
			
			userID=ps.executeUpdate();  
			

			

			//insert user branches
			ps.close();
			ps=null;

			
			String userDepoStatusN = "update userdepo set status='N' where user_id=? ";
			ps=con.prepareStatement(userDepoStatusN);
			ps.setInt(1,ldto.getLogin_id());
			ps.executeUpdate();
			

			ps.close();
			ps=null;
			
			String userBranch = "update userdepo set status=? where user_id=? and depo_code=? ";
			ps=con.prepareStatement(userBranch);
			for(BranchDto bdo : ldto.getBranchList())
			{
				ps.setString(1,"Y");
				ps.setInt(2,ldto.getLogin_id());
				ps.setInt(3,bdo.getDepo_code());
				
				ps.executeUpdate();
			}

			//update user pack
			ps.close();
			ps=null;

			String userPackageStatusN = "update userpack set status='N' where user_id=? ";
			ps=con.prepareStatement(userPackageStatusN);
			ps.setInt(1,ldto.getLogin_id());
			ps.executeUpdate();
			

			ps.close();
			ps=null;
			
			String userPackage = "update userpack set status=? where user_id=? and pack_id=?  ";
			ps=con.prepareStatement(userPackage);
			for(int value : ldto.getPackageList())
			{
				ps.setString(1,"Y");
				ps.setInt(2,ldto.getLogin_id());
				ps.setInt(3,value);
				ps.executeUpdate();
			}

			
			
			//insert user packaes
			ps.close();
			ps=null;

			
			String userDivisionStatusN = "update userdiv set status='N' where user_id=? ";
			ps=con.prepareStatement(userDivisionStatusN);
			ps.setInt(1,ldto.getLogin_id());
			ps.executeUpdate();
			

			ps.close();
			ps=null;
			
			
			String userDivision = "update userdiv set status=? where user_id=? and div_id=? ";
			ps=con.prepareStatement(userDivision);
			for(int value : ldto.getDivisionList())
			{
				ps.setString(1,"Y");
				ps.setInt(2,ldto.getLogin_id());
				ps.setInt(3,value);
				ps.executeUpdate();
			}


			//update user report
			ps.close();
			ps=null;

			
			String userRightsStatusN = "update userrights set status='N' where user_id=? ";
			ps=con.prepareStatement(userRightsStatusN);
			ps.setInt(1,ldto.getLogin_id());
			ps.executeUpdate();
			

			ps.close();
			ps=null;
			
			
			String userReport = "update userrights set status=? where user_id=? and pack_code=? and tab_code=? and menu_code=? and smenu_code=? ";
			ps=con.prepareStatement(userReport);
			for(UserDto udt : ldto.getReportList())
			{
				ps.setString(1,udt.getStatus());
				ps.setInt(2,ldto.getLogin_id());
				ps.setInt(3,udt.getPack_code());
				ps.setInt(4,udt.getTab_code());
				ps.setInt(5,udt.getMenu_code());
				ps.setInt(6,udt.getSmenu_code());
				
				ps.executeUpdate();
			}



			con.commit();
			con.setAutoCommit(true);

			ps.close();

		} 
		catch (Exception ex) 
		{
			try 
			{

				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.updateUser " + ex);
			userID=0;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return userID;
	}

	
	
	


	

	public int changePasword(int loginid,String pass)
	{
		PreparedStatement ps2 = null;
		Connection con=null;
		int i=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String loginQ ="update login set login_pass=md5(?) where login_id=? ";
			ps2 = con.prepareStatement(loginQ);
			ps2.setString(1, pass);
			ps2.setInt(2, loginid);
			i=ps2.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
			ps2.close();

		} catch (Exception ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.changePassowrd " + ex);

		}
		finally {
			try {

				if(ps2 != null){ps2.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.getUserId.Connection.close "+e);
			}
		}
		return i;
	}
  
	public Vector<Object> getUserForFavourite(int loginId)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		ResultSet rs1=null;

		Connection con=null;
		Vector<Object> userList=null;
		List<BranchDto> branchVector=null;
		List<Integer> packList=null;
		List<Integer> divList=null;
		List<UserDto> reportList=null;
		int count=0;
		int pack[]=new int[10];
		try   
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			String loginTable="select login_id,login_name,login_pass,status,prefix,fname,lname,designation,department,emailid from login where login_id="+loginId;

			String brnQ = "select c.depo_code,c.cmp_add1,c.cmp_add2,c.cmp_add3,c.cmp_city," +
					"c.cmp_phone,c.cmp_fax,c.cmp_email,c.cmp_lst,c.cmp_cst,c.cmp_panno,c.aadhar_no," +
					"c.cmp_code,ifnull(d.status,'N') from cmpmsfl c left join userdepo d on c.depo_code=d.depo_code and  d.user_id=? " +
					"order by c.cmp_city" ;


			String packQ="select u.user_id,u.pack_id,ifnull(u.status,'N')  from packagemast p left join userpack u on p.pack_code=u.pack_id and u.user_id=? and u.status='Y'";

			
			String userRightsQ="select t.*,ifnull(u.status,'N'),ifnull(u.fav_type,'N')  from "+  
			" (select t.tab_code,t.tab_name,m.menu_code,m.menu_name,m.tran_type from tabmaster t,menumaster m "+   
			" where t.tab_code=m.tab_code )t"+
			" inner join userrights u "+
			" on  u.tab_code=t.tab_code and u.menu_code=t.menu_code  and u.user_id=? and u.status='Y' "+ 
			" order by t.tab_code,t.menu_code ";  

	
			Statement st=con.createStatement();
			rs = st.executeQuery(loginTable);
			userList = new Vector<Object>();
			LoginDto ldto =null;
			while(rs.next())
			{

				ldto = new LoginDto();

				
				ldto.setLogin_id(rs.getInt(1));
				ldto.setLogin_name(rs.getString(2));
				ldto.setLogin_pass(rs.getString(3));
				ldto.setIsActive(rs.getString(4));
				ldto.setPrefix(rs.getString(5));
				ldto.setFname(rs.getString(6));
				ldto.setLname(rs.getString(7));
				ldto.setDesignation(rs.getString(8));
				ldto.setDepartment(rs.getString(9));
				ldto.setEmaiId(rs.getString(10));


				//User Selected Reports...

				ps = con.prepareStatement(userRightsQ);
				ps.setInt(1, loginId);
				rs1 =ps.executeQuery();
				UserDto udto=null;
				reportList = new ArrayList<UserDto>();
				boolean addRecord=false;
				while (rs1.next())
				{
						udto = new UserDto();
						udto.setTab_code(rs1.getInt(1));
						udto.setTab_name(rs1.getString(2));
						udto.setMenu_code(rs1.getInt(3));
						udto.setMenu_name(rs1.getString(4));
						udto.setTran_type(rs1.getString(5));
						udto.setStatus(rs1.getString(6));
						udto.setFav_type(rs1.getString(7));
						reportList.add(udto);
					
					
				}
				rs1.close();
				ps.close();

				ps=null;
				rs1=null;
				ldto.setReportList(reportList);
				
				
				//adding logindto object in userlist...
				userList.add(ldto);

			}




			con.commit();
			con.setAutoCommit(true);

			 

		} 
		catch (Exception ex) 
		{
			try 
			{

				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.createNewUser " + ex);

		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return userList;
	}
	public int updateFavourite(LoginDto ldto)
	{
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection con=null;

		int userID=0;
		try 
		{
			con=ConnectionFactory.getConnection();
			con.setAutoCommit(false);

			
			String userReport = "update userrights set fav_type=? where USER_ID=? and PACK_CODE=? and TAB_CODE=? and MENU_CODE=? and SMENU_CODE=? ";
			ps=con.prepareStatement(userReport);
			for(UserDto udt : ldto.getReportList())
			{
				if(udt.getStatus().equalsIgnoreCase("Y"))
				{
					ps.setString(1,udt.getFav_type());
					// where clause
					ps.setInt(2,ldto.getLogin_id());
					ps.setInt(3,1);
					ps.setInt(4,udt.getTab_code());
					ps.setInt(5,udt.getMenu_code());
					ps.setInt(6,udt.getSmenu_code());

					userID=	ps.executeUpdate();
				}
			}



			con.commit();
			con.setAutoCommit(true);

			ps.close();

		} 
		catch (Exception ex) 
		{ ex.printStackTrace();
			try 
			{

				con.rollback();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			System.out.println("-------------Exception in UserDAO.updateUser " + ex);
			userID=0;
		}
		finally {
			try {
				System.out.println("No. of Records Update/Insert : " );

				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
				if(con != null){con.close();}
			} catch (SQLException e) {
				System.out.println("-------------Exception in UserDAO.Connection.close "+e);
			}
		}
		return userID;
	}
    
}
