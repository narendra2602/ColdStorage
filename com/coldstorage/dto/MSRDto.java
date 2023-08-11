package com.coldstorage.dto;

public class MSRDto
{
	private  int  depo_code  ;
	private  int  div_code  ;
	private  int  rm_cd  ;
	private  int  msr_cd  ;
	private  int st_cd;
	private int tr_cd;
	private int ar_cd;
	private int rg_cd;
	private int joining_mm;
	private int mkt_year;
	private  String  msr_name  ;
	private  String  state_name  ;
	private  String  area_name  ;
	private  String  reg_name  ;
	private  String  terr_name  ;
	
	
	public String toString()
	{
		return msr_name;
	}

	
	
	
	
	public String getState_name() {
		return state_name;
	}





	public void setState_name(String state_name) {
		this.state_name = state_name;
	}





	public String getArea_name() {
		return area_name;
	}





	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}





	public String getReg_name() {
		return reg_name;
	}





	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}





	public String getTerr_name() {
		return terr_name;
	}





	public void setTerr_name(String terr_name) {
		this.terr_name = terr_name;
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

	public int getRm_cd() {
		return rm_cd;
	}

	public void setRm_cd(int rm_cd) {
		this.rm_cd = rm_cd;
	}

	public int getMsr_cd() {
		return msr_cd;
	}

	public void setMsr_cd(int msr_cd) {
		this.msr_cd = msr_cd;
	}

	public int getSt_cd() {
		return st_cd;
	}

	public void setSt_cd(int st_cd) {
		this.st_cd = st_cd;
	}

	public int getTr_cd() {
		return tr_cd;
	}

	public void setTr_cd(int tr_cd) {
		this.tr_cd = tr_cd;
	}

	public int getAr_cd() {
		return ar_cd;
	}

	public void setAr_cd(int ar_cd) {
		this.ar_cd = ar_cd;
	}

	public int getRg_cd() {
		return rg_cd;
	}

	public void setRg_cd(int rg_cd) {
		this.rg_cd = rg_cd;
	}

	public int getJoining_mm() {
		return joining_mm;
	}

	public void setJoining_mm(int joining_mm) {
		this.joining_mm = joining_mm;
	}

	public int getMkt_year() {
		return mkt_year;
	}

	public void setMkt_year(int mkt_year) {
		this.mkt_year = mkt_year;
	}

	public String getMsr_name() {
		return msr_name;
	}

	public void setMsr_name(String msr_name) {
		this.msr_name = msr_name;
	}

	

}
