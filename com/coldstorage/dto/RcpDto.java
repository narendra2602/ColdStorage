package com.coldstorage.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author Singh
 *
 */
public class RcpDto 

{

	private int div_code;
	private int vdepo_code;
	private int vbook_cd;
	private int vbook_cd1;
	private int vou_yr;
	private String vou_lo;
	private int vou_no;
	private Date vou_date;
	private Date rcon_date;
	private String vac_code;
	private int vgrp_code;
	private int vbk_code;
	private String vnart1;
	private String vnart2;
	private double vamount;
	private double interest;
	private double discount;
	private double advance;
	private String vdbcr;
	private String chq_no;
	private Date chq_date;
	private double chq_amt;
	private String bill_no;
	private Date bill_date;
	private Date doc_date;
	private Double bill_amt;
	private  int  created_by;
	private  Date  created_date;
	private  int  modified_by;
	private  Date  modified_date;
	private  int  serialno;
	private  int  fin_year;
	private  int  mnth_code;
	private  String int_code;
	private String disc_code;
	private int inv_no;
	private int dash;
	
	private int vstat_cd;
	private int varea_cd;
	private int vreg_cd;
	private int vter_cd;
	private int vdist_cd;
	private int vmsr_cd;
	
	private double tds_amt;
	private double bank_chg;
	private int vouno;
	private Date voudt;
	private int vcmp_code;
	private String pan_no;
	private String city;
	private String tin_no;
	private String State;
///////////// for rceipt printing  \\\\\\\\\\\\\\\\\
	
	private String party_name;
	
	private String exp_code;
	private String exp_name;
	private String sub_code;
	private String sub_name;
	private char tp;
	private String cn_no;
	private Date cn_date;

	private String pono;
	private Date podate;

	
	/// only for crled //////
	private String rcp_no;
	
	private Vector vdetail;
	private int mkt_year;
	private int doc_type;
	private String inv_lo;
	
	private int taxcode;
	private int taxcode1;
	private int taxcode2;
	
	private double taxable;
	private double taxable1;
	private double taxable2;
	

	private double taxamt;
	private double taxamt1;
	private double taxamt2;

	private ArrayList chqlist;
	
	private HashMap taxmap;
	
	private Date due_Date;
	
	
	private double sertax_billper;
	private double sertax_per;
	private double sertax_amt;
	
	private double cr_amt;
	
	private int tran_ype;
	private int depo_code;
	
	private double freight;
	
	private double oth_chg;
	
	private String bank_name;
	private String bank_ifsc;
	private String bank_acno;
    private String emp_code;
    private String indicator;
    private double tds_per;
    private double excess;
    private double mfg_amt;
    
	
	private double netamt;
	private double ctaxper;
	private double staxper;
	private double itaxper;
	private double cgstamt;
	private double sgstamt;
	private double igstamt;
	
	private long hsn_code;
	private String gst_no;
    private String taxtype;
    private double round_off;
	private double cess_per;
	private double cess_amt;
    private int pay_mode;
    
	private int txn_serialno;
    
    private int proformano;
    private Date proformdate;
    
    private int pdiv_code;
    
    
    
    
	public int getPdiv_code() {
		return pdiv_code;
	}
	public void setPdiv_code(int pdiv_code) {
		this.pdiv_code = pdiv_code;
	}
	public int getProformano() {
		return proformano;
	}
	public void setProformano(int proformano) {
		this.proformano = proformano;
	}
	public Date getProformdate() {
		return proformdate;
	}
	public void setProformdate(Date proformdate) {
		this.proformdate = proformdate;
	}
	public int getTxn_serialno() {
		return txn_serialno;
	}
	public void setTxn_serialno(int txn_serialno) {
		this.txn_serialno = txn_serialno;
	}
	public Date getDoc_date() {
		return doc_date;
	}
	public void setDoc_date(Date doc_date) {
		this.doc_date = doc_date;
	}
	public int getPay_mode() {
		return pay_mode;
	}
	public void setPay_mode(int pay_mode) {
		this.pay_mode = pay_mode;
	}
	public double getCess_per() {
		return cess_per;
	}
	public void setCess_per(double cess_per) {
		this.cess_per = cess_per;
	}
	public double getCess_amt() {
		return cess_amt;
	}
	public void setCess_amt(double cess_amt) {
		this.cess_amt = cess_amt;
	}
	public double getRound_off() {
		return round_off;
	}
	public void setRound_off(double round_off) {
		this.round_off = round_off;
	}
	public long getHsn_code() {
		return hsn_code;
	}
	public void setHsn_code(long hsn_code) {
		this.hsn_code = hsn_code;
	}
	public String getTaxtype() {
		return taxtype;
	}
	public void setTaxtype(String taxtype) {
		this.taxtype = taxtype;
	}
	public String getGst_no() {
		return gst_no;
	}
	public void setGst_no(String gst_no) {
		this.gst_no = gst_no;
	}
	public double getCgstamt() {
		return cgstamt;
	}
	public void setCgstamt(double cgstamt) {
		this.cgstamt = cgstamt;
	}
	public double getSgstamt() {
		return sgstamt;
	}
	public void setSgstamt(double sgstamt) {
		this.sgstamt = sgstamt;
	}
	public double getIgstamt() {
		return igstamt;
	}
	public void setIgstamt(double igstamt) {
		this.igstamt = igstamt;
	}
	public double getCtaxper() {
		return ctaxper;
	}
	public void setCtaxper(double ctaxper) {
		this.ctaxper = ctaxper;
	}
	public double getStaxper() {
		return staxper;
	}
	public void setStaxper(double staxper) {
		this.staxper = staxper;
	}
	public double getItaxper() {
		return itaxper;
	}
	public void setItaxper(double itaxper) {
		this.itaxper = itaxper;
	}
	public double getNetamt() {
		return netamt;
	}
	public void setNetamt(double netamt) {
		this.netamt = netamt;
	}
	public void setMfg_amt(double mfg_amt) {
		this.mfg_amt = mfg_amt;
	}
	public double getMfg_amt() {
		return mfg_amt;
	}
	public void setMfgamt(double mfg_amt) {
		this.mfg_amt = mfg_amt;
	}
	public double getExcess() {
		return excess;
	}
	public void setExcess(double excess) {
		this.excess = excess;
	}
	public double getTds_per() {
		return tds_per;
	}
	public void setTds_per(double tds_per) {
		this.tds_per = tds_per;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_ifsc() {
		return bank_ifsc;
	}
	public void setBank_ifsc(String bank_ifsc) {
		this.bank_ifsc = bank_ifsc;
	}
	public String getBank_acno() {
		return bank_acno;
	}
	public void setBank_acno(String bank_acno) {
		this.bank_acno = bank_acno;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	public double getOth_chg() {
		return oth_chg;
	}
	public void setOth_chg(double oth_chg) {
		this.oth_chg = oth_chg;
	}
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public String getPono() {
		return pono;
	}
	public void setPono(String pono) {
		this.pono = pono;
	}
	public int getDepo_code() {
		return depo_code;
	}
	public void setDepo_code(int depo_code) {
		this.depo_code = depo_code;
	}
	public Date getPodate() {
		return podate;
	}
	public void setPodate(Date podate) {
		this.podate = podate;
	}
	public int getTran_ype() {
		return tran_ype;
	}
	public void setTran_ype(int tran_ype) {
		this.tran_ype = tran_ype;
	}
	public double getCr_amt() {
		return cr_amt;
	}
	public void setCr_amt(double cr_amt) {
		this.cr_amt = cr_amt;
	}
	public double getSertax_billper() {
		return sertax_billper;
	}
	public void setSertax_billper(double sertax_billper) {
		this.sertax_billper = sertax_billper;
	}
	public double getSertax_per() {
		return sertax_per;
	}
	public void setSertax_per(double sertax_per) {
		this.sertax_per = sertax_per;
	}
	public double getSertax_amt() {
		return sertax_amt;
	}
	public void setSertax_amt(double sertax_amt) {
		this.sertax_amt = sertax_amt;
	}
	public Date getRcon_date() {
		return rcon_date;
	}
	public void setRcon_date(Date rcon_date) {
		this.rcon_date = rcon_date;
	}
	
	
	
	public Date getDue_Date() {
		return due_Date;
	}
	public void setDue_Date(Date due_Date) {
		this.due_Date = due_Date;
	}
	public HashMap getTaxmap() {
		return taxmap;
	}
	public void setTaxmap(HashMap taxmap) {
		this.taxmap = taxmap;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getTin_no() {
		return tin_no;
	}
	public void setTin_no(String tin_no) {
		this.tin_no = tin_no;
	}
	public ArrayList getChqlist() {
		return chqlist;
	}
	public void setChqlist(ArrayList chqlist) {
		this.chqlist = chqlist;
	}
	public int getTaxcode() {
		return taxcode;
	}
	public void setTaxcode(int taxcode) {
		this.taxcode = taxcode;
	}
	public int getTaxcode1() {
		return taxcode1;
	}
	public void setTaxcode1(int taxcode1) {
		this.taxcode1 = taxcode1;
	}
	public int getTaxcode2() {
		return taxcode2;
	}
	public void setTaxcode2(int taxcode2) {
		this.taxcode2 = taxcode2;
	}
	public double getTaxable() {
		return taxable;
	}
	public void setTaxable(double taxable) {
		this.taxable = taxable;
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
	public double getTaxamt() {
		return taxamt;
	}
	public void setTaxamt(double taxamt) {
		this.taxamt = taxamt;
	}
	public double getTaxamt1() {
		return taxamt1;
	}
	public void setTaxamt1(double taxamt1) {
		this.taxamt1 = taxamt1;
	}
	public double getTaxamt2() {
		return taxamt2;
	}
	public void setTaxamt2(double taxamt2) {
		this.taxamt2 = taxamt2;
	}
	public String getInv_lo() {
		return inv_lo;
	}
	public void setInv_lo(String inv_lo) {
		this.inv_lo = inv_lo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(int doc_type) {
		this.doc_type = doc_type;
	}
	public String getPan_no() {
		return pan_no;
	}
	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}
	public String getCn_no() {
		return cn_no;
	}
	public void setCn_no(String cn_no) {
		this.cn_no = cn_no;
	}
	public Date getCn_date() {
		return cn_date;
	}
	public void setCn_date(Date cn_date) {
		this.cn_date = cn_date;
	}
	public int getVcmp_code() {
		return vcmp_code;
	}
	public void setVcmp_code(int vcmp_code) {
		this.vcmp_code = vcmp_code;
	}
	public int getMkt_year() {
		return mkt_year;
	}
	public void setMkt_year(int mkt_year) {
		this.mkt_year = mkt_year;
	}
	public double getAdvance() {
		return advance;
	}
	public void setAdvance(double advance) {
		this.advance = advance;
	}
	public String getRcp_no() {
		return rcp_no;
	}
	public void setRcp_no(String rcp_no) {
		this.rcp_no = rcp_no;
	}
	public int getVbk_code() {
		return vbk_code;
	}
	public void setVbk_code(int vbk_code) {
		this.vbk_code = vbk_code;
	}
	public int getVouno() {
		return vouno;
	}
	public void setVouno(int vouno) {
		this.vouno = vouno;
	}
	public Date getVoudt() {
		return voudt;
	}
	public void setVoudt(Date voudt) {
		this.voudt = voudt;
	}
	public double getBank_chg() {
		return bank_chg;
	}
	public void setBank_chg(double bank_chg) {
		this.bank_chg = bank_chg;
	}
	public double getTds_amt() {
		return tds_amt;
	}
	public void setTds_amt(double tds_amt) {
		this.tds_amt = tds_amt;
	}
	public char getTp() {
		return tp;
	}
	public void setTp(char tp) {
		this.tp = tp;
	}
	public Vector getVdetail() {
		return vdetail;
	}
	public void setVdetail(Vector vdetail) {
		this.vdetail = vdetail;
	}
	public int getVgrp_code() {
		return vgrp_code;
	}
	public void setVgrp_code(int vgrp_code) {
		this.vgrp_code = vgrp_code;
	}
	public int getVbook_cd1() {
		return vbook_cd1;
	}
	public void setVbook_cd1(int vbook_cd1) {
		this.vbook_cd1 = vbook_cd1;
	}
	public String getExp_code() {
		return exp_code;
	}
	public void setExp_code(String exp_code) {
		this.exp_code = exp_code;
	}
	public String getExp_name() {
		return exp_name;
	}
	public void setExp_name(String exp_name) {
		this.exp_name = exp_name;
	}
	public String getSub_code() {
		return sub_code;
	}
	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public String getVnart2() {
		return vnart2;
	}
	public int getDash() {
		return dash;
	}
	public void setDash(int dash) {
		this.dash = dash;
	}
	public void setVnart2(String vnart2) {
		this.vnart2 = vnart2;
	}
	public String getParty_name() {
		return party_name;
	}
	public void setParty_name(String party_name) {
		this.party_name = party_name;
	}
	
	
	
	
	
	
	public int getVstat_cd() {
		return vstat_cd;
	}
	public void setVstat_cd(int vstat_cd) {
		this.vstat_cd = vstat_cd;
	}
	public int getVarea_cd() {
		return varea_cd;
	}
	public void setVarea_cd(int varea_cd) {
		this.varea_cd = varea_cd;
	}
	public int getVreg_cd() {
		return vreg_cd;
	}
	public void setVreg_cd(int vreg_cd) {
		this.vreg_cd = vreg_cd;
	}
	public int getVter_cd() {
		return vter_cd;
	}
	public void setVter_cd(int vter_cd) {
		this.vter_cd = vter_cd;
	}
	public int getVdist_cd() {
		return vdist_cd;
	}
	public void setVdist_cd(int vdist_cd) {
		this.vdist_cd = vdist_cd;
	}
	public int getVmsr_cd() {
		return vmsr_cd;
	}
	public void setVmsr_cd(int vmsr_cd) {
		this.vmsr_cd = vmsr_cd;
	}
	public int getInv_no() {
		return inv_no;
	}
	public void setInv_no(int inv_no) {
		this.inv_no = inv_no;
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
	public double getChq_amt() {
		return chq_amt;
	}
	public void setChq_amt(double chq_amt) {
		this.chq_amt = chq_amt;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getDiv_code() {
		return div_code;
	}
	public void setDiv_code(int div_code) {
		this.div_code = div_code;
	}
	public int getVdepo_code() {
		return vdepo_code;
	}
	public void setVdepo_code(int vdepo_code) {
		this.vdepo_code = vdepo_code;
	}
	public int getVbook_cd() {
		return vbook_cd;
	}
	public void setVbook_cd(int vbook_cd) {
		this.vbook_cd = vbook_cd;
	}
	public int getVou_yr() {
		return vou_yr;
	}
	public void setVou_yr(int vou_yr) {
		this.vou_yr = vou_yr;
	}
	public String getVou_lo() {
		return vou_lo;
	}
	public void setVou_lo(String vou_lo) {
		this.vou_lo = vou_lo;
	}
	public int getVou_no() {
		return vou_no;
	}
	public void setVou_no(int vou_no) {
		this.vou_no = vou_no;
	}
	public Date getVou_date() {
		return vou_date;
	}
	public void setVou_date(Date vou_date) {
		this.vou_date = vou_date;
	}
	public String getVac_code() {
		return vac_code;
	}
	public void setVac_code(String vac_code) {
		this.vac_code = vac_code;
	}
	public String getVnart1() {
		return vnart1;
	}
	public void setVnart1(String vnart1) {
		this.vnart1 = vnart1;
	}
	public double getVamount() {
		return vamount;
	}
	public void setVamount(double vamount) {
		this.vamount = vamount;
	}
	public String getVdbcr() {
		return vdbcr;
	}
	public void setVdbcr(String vdbcr) {
		this.vdbcr = vdbcr;
	}
	public String getChq_no() {
		return chq_no;
	}
	public void setChq_no(String chq_no) {
		this.chq_no = chq_no;
	}
	public Date getChq_date() {
		return chq_date;
	}
	public void setChq_date(Date chq_date) {
		this.chq_date = chq_date;
	}
	public String getBill_no() {
		return bill_no;
	}
	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}
	public Date getBill_date() {
		return bill_date;
	}
	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}
	public Double getBill_amt() {
		return bill_amt;
	}
	public void setBill_amt(Double bill_amt) {
		this.bill_amt = bill_amt;
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
	public int getMnth_code() {
		return mnth_code;
	}
	public void setMnth_code(int mnth_code) {
		this.mnth_code = mnth_code;
	}
    
	










	
}
