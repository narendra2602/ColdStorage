package com.coldstorage.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class LoginDto {
	
	private int login_id;
	private String login_name;
	private String login_pass;
	private int login_mnth;
	private int mnth_code;
	private int login_year;
	private int depo_code;
	private int div_code;
	private Date fr_date;
	private Date to_date;
	private Date sdate;
	private Date edate;
	private int inv_yr;
	private String inv_lo;
	private int mkt_year;
	private int fin_year;
	private Vector<?> myear;
	private Vector<?> mmonth;
	private Vector<?> fmonth;
	private HashMap<?,?> fmon;
	private HashMap<?,?> mmon;
	private Vector<?> fyear;
	private String mnthName;
	private String yearDesc;
	private Vector<?> gststateList;
	private Vector<?> stateList;
	private Vector<?> distList;
	private HashMap<?,?> distmap;
	private Vector<?> areaList;
	private HashMap<?,?> areamap;
	private Vector<?> regList;
	private HashMap<?,?> regmap;
	private Vector<?> hqList;
	private HashMap<?,?> hqmap;
	private Vector<?> terList;
	private Vector<?> rmList;
	private Vector<?> prtList;
	private Vector<?> prtList1;
	private Vector<?> headList;
	private Vector<?> smsList;
	 
	private HashMap<?,?> smsmap;
	
	private HashMap<?,?> prtmap;
	private HashMap<?,?> prtmap1;
	private String divnm;
	private String brnnm;
	
	private BranchDto bdto;

	
	private Vector<?> booklist;
 
	private Vector<?> prdlist;
	private HashMap<?,?> prdmap;
 
	private String int_code;
	private String disc_code;
	private String message;
	private String footer;
	private String drvnm;
	private String printernm;
	private String btnnm;
	private String totinv;
	
	private int mno;
	private int cmp_code;
	
	private String prefix;
	private String fname;
	private String lname;
	private String designation;
	private String department;
	private String emaiId;
	private String isActive;
	
	private List<BranchDto> branchList;
	private List<Integer> packageList;
	private List<Integer> divisionList;
	private List<UserDto> reportList;
	
	/// most important /////
	private int pack_code;
	/////////////////////////

	private Vector<?> acctList;
	private HashMap<?,?> acctmap;
 
	
	private Vector<?> crList;
	private HashMap<?,?> crmap;
	private HashMap<?,?> grpmap;
	 
	private Vector<?> grplist;
	private Vector<?> doclist;
	
	
	private int emnth_code;
	private int mktmno;
	
	
	
	
	private Vector<?> newyear;
	private Vector<?> cmpList;
	private Vector<?> transporterList;
	private Vector<?> cmpproductList;
	
	
	
	private int state_code;
	
	
	
	
	
	
	
	public Vector<?> getCmpproductList() {
		return cmpproductList;
	}


	public void setCmpproductList(Vector<?> cmpproductList) {
		this.cmpproductList = cmpproductList;
	}


	public int getState_code() {
		return state_code;
	}


	public void setState_code(int state_code) {
		this.state_code = state_code;
	}


	public Vector<?> getTransporterList() {
		return transporterList;
	}


	public void setTransporterList(Vector<?> transporterList) {
		this.transporterList = transporterList;
	}


	public Vector<?> getGststateList() {
		return gststateList;
	}


	public void setGststateList(Vector<?> gststateList) {
		this.gststateList = gststateList;
	}


	public HashMap<?, ?> getSmsmap() {
		return smsmap;
	}


	public void setSmsmap(HashMap<?, ?> smsmap) {
		this.smsmap = smsmap;
	}


	public Vector<?> getSmsList() {
		return smsList;
	}


	public void setSmsList(Vector<?> smsList) {
		this.smsList = smsList;
	}


	public HashMap<?, ?> getDistmap() {
		return distmap;
	}


	public void setDistmap(HashMap<?, ?> distmap) {
		this.distmap = distmap;
	}


	public HashMap<?, ?> getRegmap() {
		return regmap;
	}


	public void setRegmap(HashMap<?, ?> regmap) {
		this.regmap = regmap;
	}


	public Vector<?> getDistList() {
		return distList;
	}


	public void setDistList(Vector<?> distList) {
		this.distList = distList;
	}


	public Vector<?> getCmpList() {
		return cmpList;
	}


	public void setCmpList(Vector<?> cmpList) {
		this.cmpList = cmpList;
	}


	public HashMap<?, ?> getGrpmap() {
		return grpmap;
	}


	public void setGrpmap(HashMap<?, ?> grpmap) {
		this.grpmap = grpmap;
	}


	 


	 

	public Vector<?> getNewyear() {
		return newyear;
	}


	public void setNewyear(Vector<?> newyear) {
		this.newyear = newyear;
	}


	 


	 

 

	 

	 


	public String toString()
	{
		return fname+" "+lname;
	}
	
	
	public Vector<?> getPrtList1() {
		return prtList1;
	}


	public void setPrtList1(Vector<?> prtList1) {
		this.prtList1 = prtList1;
	}


	public HashMap<?, ?> getPrtmap1() {
		return prtmap1;
	}


	public void setPrtmap1(HashMap<?, ?> prtmap1) {
		this.prtmap1 = prtmap1;
	}


	 

	public List<BranchDto> getBranchList() {
		return branchList;
	}
	public void setBranchList(List<BranchDto> branchList) {
		this.branchList = branchList;
	}
	public List<Integer> getPackageList() {
		return packageList;
	}
	public void setPackageList(List<Integer> packageList) {
		this.packageList = packageList;
	}
	public List<Integer> getDivisionList() {
		return divisionList;
	}
	public void setDivisionList(List<Integer> divisionList) {
		this.divisionList = divisionList;
	}
	public List<UserDto> getReportList() {
		return reportList;
	}
	public void setReportList(List<UserDto> reportList) {
		this.reportList = reportList;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmaiId() {
		return emaiId;
	}
	public void setEmaiId(String emaiId) {
		this.emaiId = emaiId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public int getMktmno() {
		return mktmno;
	}
	public void setMktmno(int mktmno) {
		this.mktmno = mktmno;
	}
	public int getCmp_code() {
		return cmp_code;
	}
	public void setCmp_code(int cmp_code) {
		this.cmp_code = cmp_code;
	}
	public int getEmnth_code() {
		return emnth_code;
	}
	public void setEmnth_code(int emnth_code) {
		this.emnth_code = emnth_code;
	}
	public int getPack_code() {
		return pack_code;
	}
	public void setPack_code(int pack_code) {
		this.pack_code = pack_code;
	}
	public Vector<?> getDoclist() {
		return doclist;
	}
	public void setDoclist(Vector<?> doclist) {
		this.doclist = doclist;
	}
	public HashMap<?, ?> getHqmap() {
		return hqmap;
	}
	public void setHqmap(HashMap<?, ?> hqmap) {
		this.hqmap = hqmap;
	}
	public Vector<?> getGrplist() {
		return grplist;
	}
	public void setGrplist(Vector<?> grplist) {
		this.grplist = grplist;
	}
	public String getBtnnm() {
		return btnnm;
	}
	public void setBtnnm(String btnnm) {
		this.btnnm = btnnm;
	}
	public Vector<?> getCrList() {
		return crList;
	}
	public void setCrList(Vector<?> crList) {
		this.crList = crList;
	}
	public HashMap<?, ?> getCrmap() {
		return crmap;
	}
	public void setCrmap(HashMap<?, ?> crmap) {
		this.crmap = crmap;
	}
	public Vector<?> getAcctList() {
		return acctList;
	}
	public void setAcctList(Vector<?> acctList) {
		this.acctList = acctList;
	}
	public HashMap<?, ?> getAcctmap() {
		return acctmap;
	}
	public void setAcctmap(HashMap<?, ?> acctmap) {
		this.acctmap = acctmap;
	}
 
	public Vector<?> getHeadList() {
		return headList;
	}
	public void setHeadList(Vector<?> headList) {
		this.headList = headList;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	
	public String getTotinv() {
		return totinv;
	}
	public void setTotinv(String totinv) {
		this.totinv = totinv;
	}
	public HashMap<?, ?> getPrtmap() {
		return prtmap;
	}
	public void setPrtmap(HashMap<?, ?> prtmap) {
		this.prtmap = prtmap;
	}
	public String getBrnnm() {
		return brnnm;
	}
	public void setBrnnm(String brnnm) {
		this.brnnm = brnnm;
	}
	public String getDivnm() {
		return divnm;
	}
	public void setDivnm(String divnm) {
		this.divnm = divnm;
	}
	public BranchDto getBdto() {
		return bdto;
	}
	public void setBdto(BranchDto bdto) {
		this.bdto = bdto;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public String getPrinternm() {
		return printernm;
	}
	public void setPrinternm(String printernm) {
		this.printernm = printernm;
	}
	public String getDrvnm() {
		return drvnm;
	}
	public void setDrvnm(String drvnm) {
		this.drvnm = drvnm;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Vector<?> getRmList() {
		return rmList;
	}
	public void setRmList(Vector<?> rmList) {
		this.rmList = rmList;
	}
	public Vector<?> getTerList() {
		return terList;
	}
	public void setTerList(Vector<?> terList) {
		this.terList = terList;
	}
	public HashMap<?, ?> getAreamap() {
		return areamap;
	}
	public void setAreamap(HashMap<?, ?> areamap) {
		this.areamap = areamap;
	}
	public HashMap<?, ?> getFmon() {
		return fmon;
	}
	public void setFmon(HashMap<?, ?> fmon) {
		this.fmon = fmon;
	}
	public Vector<?> getStateList() {
		return stateList;
	}
	public void setStateList(Vector<?> stateList) {
		this.stateList = stateList;
	}
	
	public Vector<?> getHqList() {
		return hqList;
	}
	public void setHqList(Vector<?> hqList) {
		this.hqList = hqList;
	}
	public Vector<?> getPrtList() {
		return prtList;
	}
	public void setPrtList(Vector<?> prtList) {
		this.prtList = prtList;
	}
	public Vector<?> getPrdlist() {
		return prdlist;
	}
	public void setPrdlist(Vector<?> prdlist) {
		this.prdlist = prdlist;
	}
	public HashMap<?, ?> getPrdmap() {
		return prdmap;
	}
	public void setPrdmap(HashMap<?, ?> prdmap) {
		this.prdmap = prdmap;
	}
	public HashMap<?, ?> getMmon() {
		return mmon;
	}
	public void setMmon(HashMap<?, ?> mmon) {
		this.mmon = mmon;
	}
	
	 
	public String getInt_code() {
		return int_code;
	}
	public void setInt_code(String int_code) {
		this.int_code = int_code;
	}
	public String getDisc_code() {
		return disc_code;
	}
	public void setDisc_code(String disc_code) {
		this.disc_code = disc_code;
	}
	public Vector<?> getFmonth() {
		return fmonth;
	}
	public void setFmonth(Vector<?> fmonth) {
		this.fmonth = fmonth;
	}
	 
	public Vector<?> getBooklist() {
		return booklist;
	}
	public void setBooklist(Vector<?> booklist) {
		this.booklist = booklist;
	}
	public Vector<?> getRegList() {
		return regList;
	}
	public void setRegList(Vector<?> regList) {
		this.regList = regList;
	}
	public Vector<?> getAreaList() {
		return areaList;
	}
	public void setAreaList(Vector<?> areaList) {
		this.areaList = areaList;
	}
	public String getMnthName() {
		return mnthName;
	}
	public void setMnthName(String mnthName) {
		this.mnthName = mnthName;
	}
	public String getYearDesc() {
		return yearDesc;
	}
	public void setYearDesc(String yearDesc) {
		this.yearDesc = yearDesc;
	}
	public Vector<?> getFyear() {
		return fyear;
	}
	public void setFyear(Vector<?> fyear) {
		this.fyear = fyear;
	}
	public Vector<?> getMyear() {
		return myear;
	}
	public void setMyear(Vector<?> myear) {
		this.myear = myear;
	}
	public Vector<?> getMmonth() {
		return mmonth;
	}
	public void setMmonth(Vector<?> mmonth) {
		this.mmonth = mmonth;
	}
	public int getFin_year() {
		return fin_year;
	}
	public void setFin_year(int fin_year) {
		this.fin_year = fin_year;
	}
	public int getMkt_year() {
		return mkt_year;
	}
	public void setMkt_year(int mkt_year) {
		this.mkt_year = mkt_year;
	}
	public int getInv_yr() {
		return inv_yr;
	}
	public void setInv_yr(int inv_yr) {
		this.inv_yr = inv_yr;
	}
	public String getInv_lo() {
		return inv_lo;
	}
	public void setInv_lo(String inv_lo) {
		this.inv_lo = inv_lo;
	}
	public int getLogin_id() {
		return login_id;
	}
	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getLogin_pass() {
		return login_pass;
	}
	public void setLogin_pass(String login_pass) {
		this.login_pass = login_pass;
	}
	public int getLogin_mnth() {
		return login_mnth;
	}
	public void setLogin_mnth(int login_mnth) {
		this.login_mnth = login_mnth;
	}
	public int getMnth_code() {
		return mnth_code;
	}
	public void setMnth_code(int mnth_code) {
		this.mnth_code = mnth_code;
	}
	public int getLogin_year() {
		return login_year;
	}
	public void setLogin_year(int login_year) {
		this.login_year = login_year;
	}
	public int getDepo_code() {
		return depo_code;
	}
	public void setDepo_code(int depo_code) {
		this.depo_code = depo_code;
	}
	public int getDiv_code() {
		return div_code;
	}
	public void setDiv_code(int div_code) {
		this.div_code = div_code;
	}
	public Date getFr_date() {
		return fr_date;
	}
	public void setFr_date(Date fr_date) {
		this.fr_date = fr_date;
	}
	public Date getTo_date() {
		return to_date;
	}
	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	
	
	
}
