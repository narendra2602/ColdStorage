package com.coldstorage.dto;

import java.util.Date;
import java.util.Vector;

public class InvFstDto 
{

	private  int  depo_code;
	private  int  doc_type;
	private  String  party_code;
	private  int  inv_no;
	private  String  inv_lo;
	private  int  inv_yr;
	private  Date  inv_date;
	private  String  pinv_no;
	private  Date  pinv_date;
	private  Date  entry_dt;
	private  String  chl_no;
	private  Date  chl_dt;
	private  String  mtr_no;
	private  Date  mtr_dt;
	private  String  order_no;
	private  Date  order_dt;
	private  String  porder_no;
	private  Date  porder_dt;
	private  String  prt_type;
	private  int  cases;
	private  int  due_days;
	private  Date  due_dt;
	private  String  transport;
	private  String  bank;
	private  String  drug_lc1;
	private  String  drug_lc2;
	private  String  crdr_yn;
	private  double  gross_amt;
	private  double  disc_1;
	private  double  disc_2;
	private  double  tax_1;
	private  double  tax_2;
	private  int  mr_cd;
	private  int  stat_cd;
	private  int  area_cd;
	private  int  regn_cd;
	private  int  terr_cd;
	private  int  dist_cd;
	private  String  frg_tag;
	private  double  freight;
	private  double  oth_adj;
	private  double  bill_amt;
	private  double  lsale1;
	private  double  lsale2;
	private  double  lsale3;
	private  double  lsale9;

	private  double  ltax1_per;
	private  double  ltax1_amt;
	private  double  ltax2_per;
	private  double  ltax2_amt;
	private  double  ltax3_per;
	private  double  ltax3_amt;
	private  double  ctax1_per;
	private  double  ctax1_amt;
	private  double  ctax2_per;
	private  double  ctax2_amt;
	private  double  ctax3_per;
	private  double  ctax3_amt;
	private  double  octroi;
	private  double  oct_per1;
	private  double  oct_per2;
	private  double  oct_per3;
	private  double  oct_per4;
	private  double  oct_free;
	
	private  double  mfg_amt;
	private  String  inv_type;
	private  String  prod_type;
	private  double  mr_mfg;
	private  double  sc_amt1;
	private  double  sc_amt;
	private  double  sc_per;
	private  double  sc_per1;
	private  Date  system_dt;
	private  String  aproval_tg;
	private  String  aproval_no;
	private  Date  aproval_dt;
	private  double  ltax9_per;
	private  double  ltax9_amt;
	
	private  double  ctax9_amt;
	private  double  ctax9_per;
	private  double  cn_val;
	private  double  dn_val;
	private  double  discount_amt;
	private  double  discount_amt2;
	private  double  discount_amt3;
	private  double  spldiscount_amt;
	private  double  discount_round;
	private  double  interest_round;
	private  int  created_by;
	private  Date  created_date;
	private  int  modified_by;
	private  Date  modified_date;
	private  int  serialno;
	private  int  fin_year;
	private  int  mkt_year;
	private  int  mnth_code;
	private  int  div_code;
	private  String  posted_by;
	private double taxable1;
	private double taxable2;
	private double taxable3;
	private double taxable9;
	
	private double taxfree1;
	private double taxfree2;
	private double taxfree3;
	private double taxfree9;
	
	private double freeval1;
	private double freeval2;
	private double freeval3;
	private double freeval9;
	
	private double taxablef1;
	private double taxablef2;
	private double taxablef3;
	private double taxablef9;
	
	private double freetax1;
	private double freetax2;
	private double freetax3;
	private double freetax9;
	
	private String remark;
	private int dash;
	private String phone;
	private String cn_no;
	private String db_no;
	private Date cn_date;
	private Date db_date;
	private String add1;
	private String add2;
	private String mr_name;
	private String rm_name;
	
	// extra varaible for infiltration
	private  int  stat_cd1;
	private  int  area_cd1;
	private  int  regn_cd1;
	private  int  terr_cd1;
	private  int  dist_cd1;
	
	//// printing variable ///
	private Vector itemData; 
	private String party_name;
	private String city;
	private String cst_reim;
	 
 
	private String tin_no;
	 
	 private int book_code;
	 private int grp_code;
	
	 
	 
	public int getBook_code() {
		return book_code;
	}
	public void setBook_code(int book_code) {
		this.book_code = book_code;
	}
	public int getGrp_code() {
		return grp_code;
	}
	public void setGrp_code(int grp_code) {
		this.grp_code = grp_code;
	}
	private String month_name[];
	private double sval[];
	private int sqty[];
	private double weight;
	private int level;
	private int hcode;
	private int rcode;
	private int acode;
	
	private  double  lsale10;
	private  double  ltax10_per;
	private  double  ltax10_amt;
	private  double  taxable10;
	private  double  taxfree10;
	private  double  freeval10;
	private  double  taxablef10;
	private  double  freetax10;
	private  double  discount_amt10;

	private double exchange_rate;
	private String email;
	
	private String mobile;
	
	private double gross5;
	private double vat5;
	private double gross14;
	private double vat14;
	private double gross15;
	private double vat15;
	
	private int qty;
	private double rate;
	private double amount;
	
	
	
	
	
	

	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getAdd2() {
		return add2;
	}
	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	public double getGross5() {
		return gross5;
	}
	public void setGross5(double gross5) {
		this.gross5 = gross5;
	}
	public double getVat5() {
		return vat5;
	}
	public void setVat5(double vat5) {
		this.vat5 = vat5;
	}
	public double getGross14() {
		return gross14;
	}
	public void setGross14(double gross14) {
		this.gross14 = gross14;
	}
	public double getVat14() {
		return vat14;
	}
	public void setVat14(double vat14) {
		this.vat14 = vat14;
	}
	public double getGross15() {
		return gross15;
	}
	public void setGross15(double gross15) {
		this.gross15 = gross15;
	}
	public double getVat15() {
		return vat15;
	}
	public void setVat15(double vat15) {
		this.vat15 = vat15;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getExchange_rate() {
		return exchange_rate;
	}
	public void setExchange_rate(double exchange_rate) {
		this.exchange_rate = exchange_rate;
	}
	public double getLsale10() {
		return lsale10;
	}
	public void setLsale10(double lsale10) {
		this.lsale10 = lsale10;
	}
	public double getLtax10_per() {
		return ltax10_per;
	}
	public void setLtax10_per(double ltax10_per) {
		this.ltax10_per = ltax10_per;
	}
	public double getLtax10_amt() {
		return ltax10_amt;
	}
	public void setLtax10_amt(double ltax10_amt) {
		this.ltax10_amt = ltax10_amt;
	}
	public double getTaxable10() {
		return taxable10;
	}
	public void setTaxable10(double taxable10) {
		this.taxable10 = taxable10;
	}
	public double getTaxfree10() {
		return taxfree10;
	}
	public void setTaxfree10(double taxfree10) {
		this.taxfree10 = taxfree10;
	}
	public double getFreeval10() {
		return freeval10;
	}
	public void setFreeval10(double freeval10) {
		this.freeval10 = freeval10;
	}
	public double getTaxablef10() {
		return taxablef10;
	}
	public void setTaxablef10(double taxablef10) {
		this.taxablef10 = taxablef10;
	}
	public double getFreetax10() {
		return freetax10;
	}
	public void setFreetax10(double freetax10) {
		this.freetax10 = freetax10;
	}
	public double getDiscount_amt10() {
		return discount_amt10;
	}
	public void setDiscount_amt10(double discount_amt10) {
		this.discount_amt10 = discount_amt10;
	}
	public int getHcode() {
		return hcode;
	}
	public void setHcode(int hcode) {
		this.hcode = hcode;
	}
	public int getRcode() {
		return rcode;
	}
	public void setRcode(int rcode) {
		this.rcode = rcode;
	}
	public int getAcode() {
		return acode;
	}
	public void setAcode(int acode) {
		this.acode = acode;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public int[] getSqty() {
		return sqty;
	}
	public void setSqty(int[] sqty) {
		this.sqty = sqty;
	}
	public String[] getMonth_name() {
		return month_name;
	}
	public void setMonth_name(String[] month_name) {
		this.month_name = month_name;
	}
	public double[] getSval() {
		return sval;
	}
	public void setSval(double[] sval) {
		this.sval = sval;
	}
	 
	public String getTin_no() {
		return tin_no;
	}
	public void setTin_no(String tin_no) {
		this.tin_no = tin_no;
	}
	 
	public double getOct_per1() {
		return oct_per1;
	}
	public void setOct_per1(double oct_per1) {
		this.oct_per1 = oct_per1;
	}
	public double getOct_per2() {
		return oct_per2;
	}
	public void setOct_per2(double oct_per2) {
		this.oct_per2 = oct_per2;
	}
	public double getOct_per3() {
		return oct_per3;
	}
	public void setOct_per3(double oct_per3) {
		this.oct_per3 = oct_per3;
	}
	public double getOct_per4() {
		return oct_per4;
	}
	public void setOct_per4(double oct_per4) {
		this.oct_per4 = oct_per4;
	}
	public double getOct_free() {
		return oct_free;
	}
	public void setOct_free(double oct_free) {
		this.oct_free = oct_free;
	}
	public String getCst_reim() {
		return cst_reim;
	}
	public void setCst_reim(String cst_reim) {
		this.cst_reim = cst_reim;
	}
	
	public int getStat_cd1() {
		return stat_cd1;
	}

	public void setStat_cd1(int stat_cd1) {
		this.stat_cd1 = stat_cd1;
	}

	public int getArea_cd1() {
		return area_cd1;
	}

	public void setArea_cd1(int area_cd1) {
		this.area_cd1 = area_cd1;
	}

	public int getRegn_cd1() {
		return regn_cd1;
	}

	public void setRegn_cd1(int regn_cd1) {
		this.regn_cd1 = regn_cd1;
	}

	public int getTerr_cd1() {
		return terr_cd1;
	}

	public void setTerr_cd1(int terr_cd1) {
		this.terr_cd1 = terr_cd1;
	}

	public int getDist_cd1() {
		return dist_cd1;
	}

	public void setDist_cd1(int dist_cd1) {
		this.dist_cd1 = dist_cd1;
	}

	public double getFreetax1() {
		return freetax1;
	}

	public void setFreetax1(double freetax1) {
		this.freetax1 = freetax1;
	}

	public double getFreetax2() {
		return freetax2;
	}

	public void setFreetax2(double freetax2) {
		this.freetax2 = freetax2;
	}

	public double getFreetax3() {
		return freetax3;
	}

	public void setFreetax3(double freetax3) {
		this.freetax3 = freetax3;
	}

	public double getFreetax9() {
		return freetax9;
	}

	public void setFreetax9(double freetax9) {
		this.freetax9 = freetax9;
	}

	public double getTaxablef1() {
		return taxablef1;
	}

	public void setTaxablef1(double taxablef1) {
		this.taxablef1 = taxablef1;
	}

	public double getTaxablef2() {
		return taxablef2;
	}

	public void setTaxablef2(double taxablef2) {
		this.taxablef2 = taxablef2;
	}

	public double getTaxablef3() {
		return taxablef3;
	}

	public void setTaxablef3(double taxablef3) {
		this.taxablef3 = taxablef3;
	}

	public double getTaxablef9() {
		return taxablef9;
	}

	public void setTaxablef9(double taxablef9) {
		this.taxablef9 = taxablef9;
	}

	public Vector getItemData() {
		return itemData;
	}

	public void setItemData(Vector itemData) {
		this.itemData = itemData;
	}

	public String toString()
	{
		return remark;
		
	}
	
	public String getMr_name() {
		return mr_name;
	}

	public void setMr_name(String mr_name) {
		this.mr_name = mr_name;
	}
	public String getRm_name() {
		return rm_name;
	}

	public void setRm_name(String rm_name) {
		this.rm_name = rm_name;
	}

	public String getAdd1() {
		return add1;
	}




	public void setAdd1(String add1) {
		this.add1 = add1;
	}




	public double getFreeval1() {
		return freeval1;
	}




	public void setFreeval1(double freeval1) {
		this.freeval1 = freeval1;
	}




	public double getFreeval2() {
		return freeval2;
	}




	public void setFreeval2(double freeval2) {
		this.freeval2 = freeval2;
	}




	public double getFreeval3() {
		return freeval3;
	}




	public void setFreeval3(double freeval3) {
		this.freeval3 = freeval3;
	}




	public double getFreeval9() {
		return freeval9;
	}




	public void setFreeval9(double freeval9) {
		this.freeval9 = freeval9;
	}




	public String getCn_no() {
		return cn_no;
	}



	public void setCn_no(String cn_no) {
		this.cn_no = cn_no;
	}



	public String getDb_no() {
		return db_no;
	}



	public void setDb_no(String db_no) {
		this.db_no = db_no;
	}



	public Date getCn_date() {
		return cn_date;
	}



	public void setCn_date(Date cn_date) {
		this.cn_date = cn_date;
	}



	public Date getDb_date() {
		return db_date;
	}



	public void setDb_date(Date db_date) {
		this.db_date = db_date;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public double getDiscount_amt2() {
		return discount_amt2;
	}
	public void setDiscount_amt2(double discount_amt2) {
		this.discount_amt2 = discount_amt2;
	}
	public double getDiscount_amt3() {
		return discount_amt3;
	}
	public void setDiscount_amt3(double discount_amt3) {
		this.discount_amt3 = discount_amt3;
	}
	public int getDash() {
		return dash;
	}
	public void setDash(int dash) {
		this.dash = dash;
	}
	public String getParty_name() {
		return party_name;
	}
	public void setParty_name(String party_name) {
		this.party_name = party_name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
 
	public double getTaxfree1() {
		return taxfree1;
	}
	public void setTaxfree1(double taxfree1) {
		this.taxfree1 = taxfree1;
	}
	public double getTaxfree2() {
		return taxfree2;
	}
	public void setTaxfree2(double taxfree2) {
		this.taxfree2 = taxfree2;
	}
	public double getTaxfree3() {
		return taxfree3;
	}
	public void setTaxfree3(double taxfree3) {
		this.taxfree3 = taxfree3;
	}
	public double getTaxfree9() {
		return taxfree9;
	}
	public void setTaxfree9(double taxfree9) {
		this.taxfree9 = taxfree9;
	}
	public double getTaxable1() {
		return taxable1;
	}
	public void setTaxable1(double taxable1) {
		this.taxable1 = taxable1;
	}
	public double getTaxable2() {
		return taxable2;
	}
	public void setTaxable2(double taxable2) {
		this.taxable2 = taxable2;
	}
	public double getTaxable3() {
		return taxable3;
	}
	public void setTaxable3(double taxable3) {
		this.taxable3 = taxable3;
	}
	public double getTaxable9() {
		return taxable9;
	}
	public void setTaxable9(double taxable9) {
		this.taxable9 = taxable9;
	}
	public int getDepo_code() {
		return depo_code;
	}
	public void setDepo_code(int depo_code) {
		this.depo_code = depo_code;
	}
	public int getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(int doc_type) {
		this.doc_type = doc_type;
	}
	public String getParty_code() {
		return party_code;
	}
	public void setParty_code(String party_code) {
		this.party_code = party_code;
	}
	public int getInv_no() {
		return inv_no;
	}
	public void setInv_no(int inv_no) {
		this.inv_no = inv_no;
	}
	public String getInv_lo() {
		return inv_lo;
	}
	public void setInv_lo(String inv_lo) {
		this.inv_lo = inv_lo;
	}
	public int getInv_yr() {
		return inv_yr;
	}
	public void setInv_yr(int inv_yr) {
		this.inv_yr = inv_yr;
	}
	public Date getInv_date() {
		return inv_date;
	}
	public void setInv_date(Date inv_date) {
		this.inv_date = inv_date;
	}
	public String getPinv_no() {
		return pinv_no;
	}
	public void setPinv_no(String pinv_no) {
		this.pinv_no = pinv_no;
	}
	public Date getPinv_date() {
		return pinv_date;
	}
	public void setPinv_date(Date pinv_date) {
		this.pinv_date = pinv_date;
	}
	public Date getEntry_dt() {
		return entry_dt;
	}
	public void setEntry_dt(Date entry_dt) {
		this.entry_dt = entry_dt;
	}
	public String getChl_no() {
		return chl_no;
	}
	public void setChl_no(String chl_no) {
		this.chl_no = chl_no;
	}
	public Date getChl_dt() {
		return chl_dt;
	}
	public void setChl_dt(Date chl_dt) {
		this.chl_dt = chl_dt;
	}
	public String getMtr_no() {
		return mtr_no;
	}
	public void setMtr_no(String mtr_no) {
		this.mtr_no = mtr_no;
	}
	public Date getMtr_dt() {
		return mtr_dt;
	}
	public void setMtr_dt(Date mtr_dt) {
		this.mtr_dt = mtr_dt;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public Date getOrder_dt() {
		return order_dt;
	}
	public void setOrder_dt(Date order_dt) {
		this.order_dt = order_dt;
	}
	public String getPorder_no() {
		return porder_no;
	}
	public void setPorder_no(String porder_no) {
		this.porder_no = porder_no;
	}
	public Date getPorder_dt() {
		return porder_dt;
	}
	public void setPorder_dt(Date porder_dt) {
		this.porder_dt = porder_dt;
	}
	public String getPrt_type() {
		return prt_type;
	}
	public void setPrt_type(String prt_type) {
		this.prt_type = prt_type;
	}
	public int getCases() {
		return cases;
	}
	public void setCases(int cases) {
		this.cases = cases;
	}
	public int getDue_days() {
		return due_days;
	}
	public void setDue_days(int due_days) {
		this.due_days = due_days;
	}
	public Date getDue_dt() {
		return due_dt;
	}
	public void setDue_dt(Date due_dt) {
		this.due_dt = due_dt;
	}
	public String getTransport() {
		return transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getDrug_lc1() {
		return drug_lc1;
	}
	public void setDrug_lc1(String drug_lc1) {
		this.drug_lc1 = drug_lc1;
	}
	public String getDrug_lc2() {
		return drug_lc2;
	}
	public void setDrug_lc2(String drug_lc2) {
		this.drug_lc2 = drug_lc2;
	}
	public String getCrdr_yn() {
		return crdr_yn;
	}
	public void setCrdr_yn(String crdr_yn) {
		this.crdr_yn = crdr_yn;
	}
	public double getGross_amt() {
		return gross_amt;
	}
	public void setGross_amt(double gross_amt) {
		this.gross_amt = gross_amt;
	}
	public double getDisc_1() {
		return disc_1;
	}
	public void setDisc_1(double disc_1) {
		this.disc_1 = disc_1;
	}
	public double getDisc_2() {
		return disc_2;
	}
	public void setDisc_2(double disc_2) {
		this.disc_2 = disc_2;
	}
	public double getTax_1() {
		return tax_1;
	}
	public void setTax_1(double tax_1) {
		this.tax_1 = tax_1;
	}
	public double getTax_2() {
		return tax_2;
	}
	public void setTax_2(double tax_2) {
		this.tax_2 = tax_2;
	}
	public int getMr_cd() {
		return mr_cd;
	}
	public void setMr_cd(int mr_cd) {
		this.mr_cd = mr_cd;
	}
	public int getStat_cd() {
		return stat_cd;
	}
	public void setStat_cd(int stat_cd) {
		this.stat_cd = stat_cd;
	}
	public int getArea_cd() {
		return area_cd;
	}
	public void setArea_cd(int area_cd) {
		this.area_cd = area_cd;
	}
	public int getRegn_cd() {
		return regn_cd;
	}
	public void setRegn_cd(int regn_cd) {
		this.regn_cd = regn_cd;
	}
	public int getTerr_cd() {
		return terr_cd;
	}
	public void setTerr_cd(int terr_cd) {
		this.terr_cd = terr_cd;
	}
	public int getDist_cd() {
		return dist_cd;
	}
	public void setDist_cd(int dist_cd) {
		this.dist_cd = dist_cd;
	}
	public String getFrg_tag() {
		return frg_tag;
	}
	public void setFrg_tag(String frg_tag) {
		this.frg_tag = frg_tag;
	}
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public double getOth_adj() {
		return oth_adj;
	}
	public void setOth_adj(double oth_adj) {
		this.oth_adj = oth_adj;
	}
	public double getBill_amt() {
		return bill_amt;
	}
	public void setBill_amt(double bill_amt) {
		this.bill_amt = bill_amt;
	}
	public double getLsale1() {
		return lsale1;
	}
	public void setLsale1(double lsale1) {
		this.lsale1 = lsale1;
	}
	public double getLsale2() {
		return lsale2;
	}
	public void setLsale2(double lsale2) {
		this.lsale2 = lsale2;
	}
	public double getLsale3() {
		return lsale3;
	}
	public void setLsale3(double lsale3) {
		this.lsale3 = lsale3;
	}
	public double getLtax1_per() {
		return ltax1_per;
	}
	public void setLtax1_per(double ltax1_per) {
		this.ltax1_per = ltax1_per;
	}
	public double getLtax1_amt() {
		return ltax1_amt;
	}
	public void setLtax1_amt(double ltax1_amt) {
		this.ltax1_amt = ltax1_amt;
	}
	public double getLtax2_per() {
		return ltax2_per;
	}
	public void setLtax2_per(double ltax2_per) {
		this.ltax2_per = ltax2_per;
	}
	public double getLtax2_amt() {
		return ltax2_amt;
	}
	public void setLtax2_amt(double ltax2_amt) {
		this.ltax2_amt = ltax2_amt;
	}
	public double getLtax3_per() {
		return ltax3_per;
	}
	public void setLtax3_per(double ltax3_per) {
		this.ltax3_per = ltax3_per;
	}
	public double getLtax3_amt() {
		return ltax3_amt;
	}
	public void setLtax3_amt(double ltax3_amt) {
		this.ltax3_amt = ltax3_amt;
	}
	public double getCtax1_per() {
		return ctax1_per;
	}
	public void setCtax1_per(double ctax1_per) {
		this.ctax1_per = ctax1_per;
	}
	public double getCtax1_amt() {
		return ctax1_amt;
	}
	public void setCtax1_amt(double ctax1_amt) {
		this.ctax1_amt = ctax1_amt;
	}
	public double getCtax2_per() {
		return ctax2_per;
	}
	public void setCtax2_per(double ctax2_per) {
		this.ctax2_per = ctax2_per;
	}
	public double getCtax2_amt() {
		return ctax2_amt;
	}
	public void setCtax2_amt(double ctax2_amt) {
		this.ctax2_amt = ctax2_amt;
	}
	public double getCtax3_per() {
		return ctax3_per;
	}
	public void setCtax3_per(double ctax3_per) {
		this.ctax3_per = ctax3_per;
	}
	public double getCtax3_amt() {
		return ctax3_amt;
	}
	public void setCtax3_amt(double ctax3_amt) {
		this.ctax3_amt = ctax3_amt;
	}
	public double getOctroi() {
		return octroi;
	}
	public void setOctroi(double octroi) {
		this.octroi = octroi;
	}
	public double getMfg_amt() {
		return mfg_amt;
	}
	public void setMfg_amt(double mfg_amt) {
		this.mfg_amt = mfg_amt;
	}
	public String getInv_type() {
		return inv_type;
	}
	public void setInv_type(String inv_type) {
		this.inv_type = inv_type;
	}
	public String getProd_type() {
		return prod_type;
	}
	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}
	public double getMr_mfg() {
		return mr_mfg;
	}
	public void setMr_mfg(double mr_mfg) {
		this.mr_mfg = mr_mfg;
	}
	public double getSc_amt1() {
		return sc_amt1;
	}
	public void setSc_amt1(double sc_amt1) {
		this.sc_amt1 = sc_amt1;
	}
	public double getSc_amt() {
		return sc_amt;
	}
	public void setSc_amt(double sc_amt) {
		this.sc_amt = sc_amt;
	}
	public double getSc_per() {
		return sc_per;
	}
	public void setSc_per(double sc_per) {
		this.sc_per = sc_per;
	}
	public double getSc_per1() {
		return sc_per1;
	}
	public void setSc_per1(double sc_per1) {
		this.sc_per1 = sc_per1;
	}
	public Date getSystem_dt() {
		return system_dt;
	}
	public void setSystem_dt(Date system_dt) {
		this.system_dt = system_dt;
	}
	public String getAproval_tg() {
		return aproval_tg;
	}
	public void setAproval_tg(String aproval_tg) {
		this.aproval_tg = aproval_tg;
	}
	public String getAproval_no() {
		return aproval_no;
	}
	public void setAproval_no(String aproval_no) {
		this.aproval_no = aproval_no;
	}
	public Date getAproval_dt() {
		return aproval_dt;
	}
	public void setAproval_dt(Date aproval_dt) {
		this.aproval_dt = aproval_dt;
	}
	public double getLsale9() {
		return lsale9;
	}
	public void setLsale9(double lsale9) {
		this.lsale9 = lsale9;
	}
	
	public double getLtax9_per() {
		return ltax9_per;
	}
	public void setLtax9_per(double ltax9_per) {
		this.ltax9_per = ltax9_per;
	}
	public double getLtax9_amt() {
		return ltax9_amt;
	}
	public void setLtax9_amt(double ltax9_amt) {
		this.ltax9_amt = ltax9_amt;
	}
 
	public double getCtax9_per() {
		return ctax9_per;
	}
	public void setCtax9_per(double ctax9_per) {
		this.ctax9_per = ctax9_per;
	}
	public double getCtax9_amt() {
		return ctax9_amt;
	}
	public void setCtax9_amt(double ctax9_amt) {
		this.ctax9_amt = ctax9_amt;
	}
	public double getCn_val() {
		return cn_val;
	}
	public void setCn_val(double cn_val) {
		this.cn_val = cn_val;
	}
	public double getDn_val() {
		return dn_val;
	}
	public void setDn_val(double dn_val) {
		this.dn_val = dn_val;
	}
	public double getDiscount_amt() {
		return discount_amt;
	}
	public void setDiscount_amt(double discount_amt) {
		this.discount_amt = discount_amt;
	}
	public double getSpldiscount_amt() {
		return spldiscount_amt;
	}
	public void setSpldiscount_amt(double spldiscount_amt) {
		this.spldiscount_amt = spldiscount_amt;
	}
	public double getDiscount_round() {
		return discount_round;
	}
	public void setDiscount_round(double discount_round) {
		this.discount_round = discount_round;
	}
	public double getInterest_round() {
		return interest_round;
	}
	public void setInterest_round(double interest_round) {
		this.interest_round = interest_round;
	}
	public int getCreated_by() {
		return created_by;
	}
	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	 
	public int getModified_by() {
		return modified_by;
	}
	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}
	public Date getModified_date() {
		return modified_date;
	}
	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}
	public int getSerialno() {
		return serialno;
	}
	public void setSerialno(int serialno) {
		this.serialno = serialno;
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
	public int getMnth_code() {
		return mnth_code;
	}
	public void setMnth_code(int mnth_code) {
		this.mnth_code = mnth_code;
	}
	public int getDiv_code() {
		return div_code;
	}
	public void setDiv_code(int div_code) {
		this.div_code = div_code;
	}
	public String getPosted_by() {
		return posted_by;
	}
	public void setPosted_by(String posted_by) {
		this.posted_by = posted_by;
	}
	

	
}
