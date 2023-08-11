package com.coldstorage.dto;
import java.util.Date;
import java.util.List;

public class InvSndDto
{
	private int sdepo_code;
	private int sdoc_type;
	private int sinv_no;
	private String sinv_lo;
	private int sinv_yr;
	private Date sinv_dt;
	private String spinv_no;
	private Date spinv_dt;
	private Date sentry_dt;
	private int strn_tp;
	private String sprt_cd;
	private int sprd_cd;
	private int spd_gp;
	private int sale_type;
	private String sbatch_no;
	private String smfg_dt;
	private String sexp_dt;
	private int sqty;
	private int sfree_qty;
	private double stax_cd1;
	private double stax_cd2;
	private String oct_type;
	private double octroi;
	private double srate_net;
	private double srate_pur;
	private double srate_trd;
	private double srate_mrp;
	private double srate_hos;
	private double srate_mfg;
	private double srate_exc;
	private double sds_per;
	private double sds_spl;
	private double samnt;
	private String sdel_tg;
	private int smr_cd;
	private int stat_cd;
	private int area_cd;
	private int inv_dsm;
	private int terr_cd;
	private int inv_dist;
	private String stax_type;
	private int tdum_code;
	private String br_tag;
	private Date dd_date;
	private int ndays;
	private double int_amt;
	private Date system_dt2;
	private int case_no;
	private int box;
	private double secess;
	private double secess1;
	private int gift_code;
	private int fact_code;
	private String remark;
	private String mkt_typ;
	private int typ_cd;
	private int schm_code;
	private String trf;
	private int nsprd_cd;
	private int created_by;
	private Date created_date;
	private int modified_by;
	private Date modified_date;
	private int serialno;
	private int fin_year;
	private int mkt_year;
	private int mnth_code;
	private int div_code;
	private String taxon;
	
	private String discyn;
	private String tax_on_free;
	private double exc_rate;
	
	private String pname;
	private String pack;

	private List batRate;
	private int order_no;
	private Date order_date;
	private int order_serial;
	private String rate_type;
	private double weight1;
	private int dash;
	private String brname;
	private double balance;
	private double stax_cd3;  // igst
	private double sdis_per;
	private double sgst_amt;
	private double cgst_amt;
	private double igst_amt;
	private double taxable_amt;
	private double net_amt;
	private long hsn_code;
	private double discount_amt;
	private int scmp_cd;
	
	
	
	
	public int getScmp_cd() {
		return scmp_cd;
	}
	public void setScmp_cd(int scmp_cd) {
		this.scmp_cd = scmp_cd;
	}
	public double getDiscount_amt() {
		return discount_amt;
	}
	public void setDiscount_amt(double discount_amt) {
		this.discount_amt = discount_amt;
	}
	public double getStax_cd3() {
		return stax_cd3;
	}
	public void setStax_cd3(double stax_cd3) {
		this.stax_cd3 = stax_cd3;
	}
	public double getSdis_per() {
		return sdis_per;
	}
	public void setSdis_per(double sdis_per) {
		this.sdis_per = sdis_per;
	}
	public double getSgst_amt() {
		return sgst_amt;
	}
	public void setSgst_amt(double sgst_amt) {
		this.sgst_amt = sgst_amt;
	}
	public double getCgst_amt() {
		return cgst_amt;
	}
	public void setCgst_amt(double cgst_amt) {
		this.cgst_amt = cgst_amt;
	}
	public double getIgst_amt() {
		return igst_amt;
	}
	public void setIgst_amt(double igst_amt) {
		this.igst_amt = igst_amt;
	}
	public double getTaxable_amt() {
		return taxable_amt;
	}
	public void setTaxable_amt(double taxable_amt) {
		this.taxable_amt = taxable_amt;
	}
	public double getNet_amt() {
		return net_amt;
	}
	public void setNet_amt(double net_amt) {
		this.net_amt = net_amt;
	}
	public long getHsn_code() {
		return hsn_code;
	}
	public void setHsn_code(long hsn_code) {
		this.hsn_code = hsn_code;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getBrname() {
		return brname;
	}
	public void setBrname(String brname) {
		this.brname = brname;
	}
	public int getDash() {
		return dash;
	}
	public void setDash(int dash) {
		this.dash = dash;
	}
	public double getWeight1() {
		return weight1;
	}
	public void setWeight1(double weight1) {
		this.weight1 = weight1;
	}
	public String getRate_type() {
		return rate_type;
	}
	public void setRate_type(String rate_type) {
		this.rate_type = rate_type;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public int getOrder_serial() {
		return order_serial;
	}
	public void setOrder_serial(int order_serial) {
		this.order_serial = order_serial;
	}
	public List getBatRate() {
		return batRate;
	}
	public void setBatRate(List batRate) {
		this.batRate = batRate;
	}
	public double getExc_rate() {
		return exc_rate;
	}
	public void setExc_rate(double exc_rate) {
		this.exc_rate = exc_rate;
	}
	public String getDiscyn() {
		return discyn;
	}
	public void setDiscyn(String discyn) {
		this.discyn = discyn;
	}
	public String getTax_on_free() {
		return tax_on_free;
	}
	public void setTax_on_free(String tax_on_free) {
		this.tax_on_free = tax_on_free;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	
	
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public int getStrn_tp() {
		return strn_tp;
	}
	public void setStrn_tp(int strn_tp) {
		this.strn_tp = strn_tp;
	}
	public String getTaxon() {
		return taxon;
	}
	public void setTaxon(String taxon) {
		this.taxon = taxon;
	}
	public int getSdepo_code() {
		return sdepo_code;
	}
	public void setSdepo_code(int sdepo_code) {
		this.sdepo_code = sdepo_code;
	}
	public int getSdoc_type() {
		return sdoc_type;
	}
	public void setSdoc_type(int sdoc_type) {
		this.sdoc_type = sdoc_type;
	}
	public int getSinv_no() {
		return sinv_no;
	}
	public void setSinv_no(int sinv_no) {
		this.sinv_no = sinv_no;
	}
	public String getSinv_lo() {
		return sinv_lo;
	}
	public void setSinv_lo(String sinv_lo) {
		this.sinv_lo = sinv_lo;
	}
	public int getSinv_yr() {
		return sinv_yr;
	}
	public void setSinv_yr(int sinv_yr) {
		this.sinv_yr = sinv_yr;
	}
	public Date getSinv_dt() {
		return sinv_dt;
	}
	public void setSinv_dt(Date sinv_dt) {
		this.sinv_dt = sinv_dt;
	}
	public String getSpinv_no() {
		return spinv_no;
	}
	public void setSpinv_no(String spinv_no) {
		this.spinv_no = spinv_no;
	}
	public Date getSpinv_dt() {
		return spinv_dt;
	}
	public void setSpinv_dt(Date spinv_dt) {
		this.spinv_dt = spinv_dt;
	}
	public Date getSentry_dt() {
		return sentry_dt;
	}
	public void setSentry_dt(Date sentry_dt) {
		this.sentry_dt = sentry_dt;
	}
	public String getSprt_cd() {
		return sprt_cd;
	}
	public void setSprt_cd(String sprt_cd) {
		this.sprt_cd = sprt_cd;
	}
	public int getSprd_cd() {
		return sprd_cd;
	}
	public void setSprd_cd(int sprd_cd) {
		this.sprd_cd = sprd_cd;
	}
	public int getSpd_gp() {
		return spd_gp;
	}
	public void setSpd_gp(int spd_gp) {
		this.spd_gp = spd_gp;
	}
	public int getSale_type() {
		return sale_type;
	}
	public void setSale_type(int sale_type) {
		this.sale_type = sale_type;
	}
	public String getSbatch_no() {
		return sbatch_no;
	}
	public void setSbatch_no(String sbatch_no) {
		this.sbatch_no = sbatch_no;
	}
	public String getSmfg_dt() {
		return smfg_dt;
	}
	public void setSmfg_dt(String smfg_dt) {
		this.smfg_dt = smfg_dt;
	}
	public String getSexp_dt() {
		return sexp_dt;
	}
	public void setSexp_dt(String sexp_dt) {
		this.sexp_dt = sexp_dt;
	}
	public int getSqty() {
		return sqty;
	}
	public void setSqty(int sqty) {
		this.sqty = sqty;
	}
	public int getSfree_qty() {
		return sfree_qty;
	}
	public void setSfree_qty(int sfree_qty) {
		this.sfree_qty = sfree_qty;
	}
	public double getStax_cd1() {
		return stax_cd1;
	}
	public void setStax_cd1(double stax_cd1) {
		this.stax_cd1 = stax_cd1;
	}
	public double getStax_cd2() {
		return stax_cd2;
	}
	public void setStax_cd2(double stax_cd2) {
		this.stax_cd2 = stax_cd2;
	}
	public String getOct_type() {
		return oct_type;
	}
	public void setOct_type(String oct_type) {
		this.oct_type = oct_type;
	}
	public double getOctroi() {
		return octroi;
	}
	public void setOctroi(double octroi) {
		this.octroi = octroi;
	}
	public double getSrate_net() {
		return srate_net;
	}
	public void setSrate_net(double srate_net) {
		this.srate_net = srate_net;
	}
	public double getSrate_pur() {
		return srate_pur;
	}
	public void setSrate_pur(double srate_pur) {
		this.srate_pur = srate_pur;
	}
	public double getSrate_trd() {
		return srate_trd;
	}
	public void setSrate_trd(double srate_trd) {
		this.srate_trd = srate_trd;
	}
	public double getSrate_mrp() {
		return srate_mrp;
	}
	public void setSrate_mrp(double srate_mrp) {
		this.srate_mrp = srate_mrp;
	}
	public double getSrate_hos() {
		return srate_hos;
	}
	public void setSrate_hos(double srate_hos) {
		this.srate_hos = srate_hos;
	}
	public double getSrate_mfg() {
		return srate_mfg;
	}
	public void setSrate_mfg(double srate_mfg) {
		this.srate_mfg = srate_mfg;
	}
	public double getSrate_exc() {
		return srate_exc;
	}
	public void setSrate_exc(double srate_exc) {
		this.srate_exc = srate_exc;
	}
	public double getSds_per() {
		return sds_per;
	}
	public void setSds_per(double sds_per) {
		this.sds_per = sds_per;
	}
	public double getSds_spl() {
		return sds_spl;
	}
	public void setSds_spl(double sds_spl) {
		this.sds_spl = sds_spl;
	}
	public double getSamnt() {
		return samnt;
	}
	public void setSamnt(double samnt) {
		this.samnt = samnt;
	}
	public String getSdel_tg() {
		return sdel_tg;
	}
	public void setSdel_tg(String sdel_tg) {
		this.sdel_tg = sdel_tg;
	}
	public int getSmr_cd() {
		return smr_cd;
	}
	public void setSmr_cd(int smr_cd) {
		this.smr_cd = smr_cd;
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
	public int getInv_dsm() {
		return inv_dsm;
	}
	public void setInv_dsm(int inv_dsm) {
		this.inv_dsm = inv_dsm;
	}
	public int getTerr_cd() {
		return terr_cd;
	}
	public void setTerr_cd(int terr_cd) {
		this.terr_cd = terr_cd;
	}
	public int getInv_dist() {
		return inv_dist;
	}
	public void setInv_dist(int inv_dist) {
		this.inv_dist = inv_dist;
	}
	public String getStax_type() {
		return stax_type;
	}
	public void setStax_type(String stax_type) {
		this.stax_type = stax_type;
	}
	public int getTdum_code() {
		return tdum_code;
	}
	public void setTdum_code(int tdum_code) {
		this.tdum_code = tdum_code;
	}
	public String getBr_tag() {
		return br_tag;
	}
	public void setBr_tag(String br_tag) {
		this.br_tag = br_tag;
	}
	public Date getDd_date() {
		return dd_date;
	}
	public void setDd_date(Date dd_date) {
		this.dd_date = dd_date;
	}
	public int getNdays() {
		return ndays;
	}
	public void setNdays(int ndays) {
		this.ndays = ndays;
	}
	public double getInt_amt() {
		return int_amt;
	}
	public void setInt_amt(double int_amt) {
		this.int_amt = int_amt;
	}
	public Date getSystem_dt2() {
		return system_dt2;
	}
	public void setSystem_dt2(Date system_dt2) {
		this.system_dt2 = system_dt2;
	}
	public int getCase_no() {
		return case_no;
	}
	public void setCase_no(int case_no) {
		this.case_no = case_no;
	}
	public int getBox() {
		return box;
	}
	public void setBox(int box) {
		this.box = box;
	}
	public double getSecess() {
		return secess;
	}
	public void setSecess(double secess) {
		this.secess = secess;
	}
	public double getSecess1() {
		return secess1;
	}
	public void setSecess1(double secess1) {
		this.secess1 = secess1;
	}
	public int getGift_code() {
		return gift_code;
	}
	public void setGift_code(int gift_code) {
		this.gift_code = gift_code;
	}
	public int getFact_code() {
		return fact_code;
	}
	public void setFact_code(int fact_code) {
		this.fact_code = fact_code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMkt_typ() {
		return mkt_typ;
	}
	public void setMkt_typ(String mkt_typ) {
		this.mkt_typ = mkt_typ;
	}
	public int getTyp_cd() {
		return typ_cd;
	}
	public void setTyp_cd(int typ_cd) {
		this.typ_cd = typ_cd;
	}
	public int getSchm_code() {
		return schm_code;
	}
	public void setSchm_code(int schm_code) {
		this.schm_code = schm_code;
	}
	public String getTrf() {
		return trf;
	}
	public void setTrf(String trf) {
		this.trf = trf;
	}
	public int getNsprd_cd() {
		return nsprd_cd;
	}
	public void setNsprd_cd(int nsprd_cd) {
		this.nsprd_cd = nsprd_cd;
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



}
