package com.coldstorage.dto;

import java.util.Date;



public class PartyDto {

	private	int	depo_code	;
	private	int	mcmp_code	;
	private	int	mdiv_cd	;
	private	int	mgrp_code	;
	private	String	mac_code	;
	private	String	mac_type	;
	private	String	mac_prfx	;
	private	String	mac_name	;
	private	String	madd1	;
	private	String	madd2	;
	private	String	madd3	;
	private	String	mcity	;
	private String  mstate_name;
	private	String	mpin	;
	private	String	mphone	;
	private	String	mobile	;
	private	String	memail	;
	private String  mcontct ;
	
	private	String	mbanker	;
	private	String	mbank_add1	;
	private	String	mbank_add2	;
	private	String	bank_ifsc	;
	private	String	mtranspt	;
	private	int	mtran_code	;
	private	String	gst_no	;
	private	int	mstat_code	;
	private	int	marea_code	;
	private	int	mregion_cd	;
	private	int	mterr_code	;
	private	int	mdist_cd	;
	private	String	mtype1	;
	private	int	mdays	;

	private	String	cst_type	;
	private	int	msr_code	;
	private	double	mopng_bal	;
	private	String	mopng_db	;
	private	double	curr_bal	;
	private	String	curr_db	;
	private	String	mtype2	;
	private int Fin_year;
	private int Mkt_year;
	
	
	private	String	state_name	;
	private	String	grp_name	;
	private int msub_code;
	private String pan_no;
	private int mdiv_code;
	private int branch_code;
	private String scheme;
	
	private int distance;
	
	private double mfix_disc1;
	private String tax_type;
	
	
	
	
	public String getTax_type() {
		return tax_type;
	}

	public void setTax_type(String tax_type) {
		this.tax_type = tax_type;
	}

	public double getMfix_disc1() {
		return mfix_disc1;
	}

	public void setMfix_disc1(double mfix_disc1) {
		this.mfix_disc1 = mfix_disc1;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(int branch_code) {
		this.branch_code = branch_code;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}


	public int getMdiv_code() {
		return mdiv_code;
	}

	public void setMdiv_code(int mdiv_code) {
		this.mdiv_code = mdiv_code;
	}

	public String getMstate_name() {
		return mstate_name;
	}

	public void setMstate_name(String mstate_name) {
		this.mstate_name = mstate_name;
	}

	public double getCurr_bal() {
		return curr_bal;
	}

	public void setCurr_bal(double curr_bal) {
		this.curr_bal = curr_bal;
	}

	public String getCurr_db() {
		return curr_db;
	}

	public void setCurr_db(String curr_db) {
		this.curr_db = curr_db;
	}

	public String toString()
	     {
	         return (mac_name+","+mcity);
	     }
	
	public String getPan_no() {
		return pan_no;
	}





	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}








	public int getMsub_code() {
		return msub_code;
	}





	public void setMsub_code(int msub_code) {
		this.msub_code = msub_code;
	}





	public String getState_name() {
		return state_name;
	}




	public void setState_name(String state_name) {
		this.state_name = state_name;
	}









	public int getFin_year() {
		return Fin_year;
	}
	public void setFin_year(int fin_year) {
		Fin_year = fin_year;
	}
	public int getMkt_year() {
		return Mkt_year;
	}
	public void setMkt_year(int mkt_year) {
		Mkt_year = mkt_year;
	}
	public String getMcontct() {
		return mcontct;
	}
	public void setMcontct(String mcontct) {
		this.mcontct = mcontct;
	}
	public String getCst_type() {
		return cst_type;
	}
	public void setCst_type(String cst_type) {
		this.cst_type = cst_type;
	}
	public int getDepo_code() {
		return depo_code;
	}
	public void setDepo_code(int depo_code) {
		this.depo_code = depo_code;
	}
	public String getMac_code() {
		return mac_code;
	}
	public void setMac_code(String mac_code) {
		this.mac_code = mac_code;
	}
	public String getMac_name() {
		return mac_name;
	}
	public void setMac_name(String mac_name) {
		this.mac_name = mac_name;
	}
	public String getMac_prfx() {
		return mac_prfx;
	}
	public void setMac_prfx(String mac_prfx) {
		this.mac_prfx = mac_prfx;
	}
	public String getMac_type() {
		return mac_type;
	}
	public void setMac_type(String mac_type) {
		this.mac_type = mac_type;
	}
	public String getMadd1() {
		return madd1;
	}
	public void setMadd1(String madd1) {
		this.madd1 = madd1;
	}
	public String getMadd2() {
		return madd2;
	}
	public void setMadd2(String madd2) {
		this.madd2 = madd2;
	}
	public String getMadd3() {
		return madd3;
	}
	public void setMadd3(String madd3) {
		this.madd3 = madd3;
	}
	public int getMarea_code() {
		return marea_code;
	}
	public void setMarea_code(int marea_code) {
		this.marea_code = marea_code;
	}
	public String getMbank_add1() {
		return mbank_add1;
	}
	public void setMbank_add1(String mbank_add1) {
		this.mbank_add1 = mbank_add1;
	}
	public String getMbank_add2() {
		return mbank_add2;
	}
	public void setMbank_add2(String mbank_add2) {
		this.mbank_add2 = mbank_add2;
	}
	public String getMbanker() {
		return mbanker;
	}
	public void setMbanker(String mbanker) {
		this.mbanker = mbanker;
	}
	public String getMcity() {
		return mcity;
	}
	public void setMcity(String mcity) {
		this.mcity = mcity;
	}
	public int getMcmp_code() {
		return mcmp_code;
	}
	public void setMcmp_code(int mcmp_code) {
		this.mcmp_code = mcmp_code;
	}
	public String getGst_no() {
		return gst_no;
	}

	public void setGst_no(String gst_no) {
		this.gst_no = gst_no;
	}

	public int getMdays() {
		return mdays;
	}
	public void setMdays(int mdays) {
		this.mdays = mdays;
	}
	public int getMdist_cd() {
		return mdist_cd;
	}
	public void setMdist_cd(int mdist_cd) {
		this.mdist_cd = mdist_cd;
	}
	public int getMdiv_cd() {
		return mdiv_cd;
	}
	public void setMdiv_cd(int mdiv_cd) {
		this.mdiv_cd = mdiv_cd;
	}
	public String getMemail() {
		return memail;
	}
	public void setMemail(String memail) {
		this.memail = memail;
	}
	public int getMgrp_code() {
		return mgrp_code;
	}
	public void setMgrp_code(int mgrp_code) {
		this.mgrp_code = mgrp_code;
	}
	
	
	public String getGrp_name() {
		return grp_name;
	}

	public void setGrp_name(String grp_name) {
		this.grp_name = grp_name;
	}

	public String getMobile() {
		return mobile;
	}
	public String getBank_ifsc() {
		return bank_ifsc;
	}

	public void setBank_ifsc(String bank_ifsc) {
		this.bank_ifsc = bank_ifsc;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public double getMopng_bal() {
		return mopng_bal;
	}
	public void setMopng_bal(double mopng_bal) {
		this.mopng_bal = mopng_bal;
	}
	public String getMopng_db() {
		return mopng_db;
	}
	public void setMopng_db(String mopng_db) {
		this.mopng_db = mopng_db;
	}
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}
	public String getMpin() {
		return mpin;
	}
	public void setMpin(String mpin) {
		this.mpin = mpin;
	}
	public int getMregion_cd() {
		return mregion_cd;
	}
	public void setMregion_cd(int mregion_cd) {
		this.mregion_cd = mregion_cd;
	}
	public int getMsr_code() {
		return msr_code;
	}
	public void setMsr_code(int msr_code) {
		this.msr_code = msr_code;
	}
	public int getMstat_code() {
		return mstat_code;
	}
	public void setMstat_code(int mstat_code) {
		this.mstat_code = mstat_code;
	}
	public int getMterr_code() {
		return mterr_code;
	}
	public void setMterr_code(int mterr_code) {
		this.mterr_code = mterr_code;
	}
	public String getMtranspt() {
		return mtranspt;
	}
	public void setMtranspt(String mtranspt) {
		this.mtranspt = mtranspt;
	}
	
	
	public int getMtran_code() {
		return mtran_code;
	}

	public void setMtran_code(int mtran_code) {
		this.mtran_code = mtran_code;
	}

	public String getMtype1() {
		return mtype1;
	}
	public void setMtype1(String mtype1) {
		this.mtype1 = mtype1;
	}
	public String getMtype2() {
		return mtype2;
	}
	public void setMtype2(String mtype2) {
		this.mtype2 = mtype2;
	}
	

	
}
