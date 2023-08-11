package com.coldstorage.dto;

import java.util.Date;

public class StkledgerDto 
{

	private int code;
	private String description;
	private String pack;
	private int opening;
	private String batch_no;
	private String expiry_date;
	private String doc_no;
	private Date receipt_date;
	private int receipt_qty;
	private Date issue_date;
	private int issue_qty;
	private int issue_free;
	private double rate;
	private double value;
	private double ptsvalue;
	private String party_name;
	private String city;
	private int dash;
	
	private String party_code;
	private String hq_name;
	
	// For sample Stock Ledger Addtional fields
	private String mtr_no;
	private Date mtr_dt;
	private String transport;
	private int cases;
	private int strn_tp;
	private String spinv_no;
	private double mrp;
	private double pts;
	private double mfg;
	private String tax_type;
	private Date spinv_dt;
	private int qty[];
	private String orderno;
	
	private String rrno;
	private Date rrdate;
	private int div_code;
	///////////////////////////////

	
	
	
	
	
	
	public int getOpening() {
		return opening;
	}
	public int getDiv_code() {
		return div_code;
	}
	public void setDiv_code(int div_code) {
		this.div_code = div_code;
	}
	public String getRrno() {
		return rrno;
	}
	public void setRrno(String rrno) {
		this.rrno = rrno;
	}
	public Date getRrdate() {
		return rrdate;
	}
	public void setRrdate(Date rrdate) {
		this.rrdate = rrdate;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public int[] getQty() {
		return qty;
	}
	public void setQty(int[] qty) {
		this.qty = qty;
	}
	public Date getSpinv_dt() {
		return spinv_dt;
	}
	public void setSpinv_dt(Date spinv_dt) {
		this.spinv_dt = spinv_dt;
	}
	public String getTax_type() {
		return tax_type;
	}
	public void setTax_type(String tax_type) {
		this.tax_type = tax_type;
	}
	public double getPts() {
		return pts;
	}
	public void setPts(double pts) {
		this.pts = pts;
	}
	public double getMfg() {
		return mfg;
	}
	public void setMfg(double mfg) {
		this.mfg = mfg;
	}
	public double getMrp() {
		return mrp;
	}
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	public String getSpinv_no() {
		return spinv_no;
	}
	public void setSpinv_no(String spinv_no) {
		this.spinv_no = spinv_no;
	}
	public double getPtsvalue() {
		return ptsvalue;
	}
	public void setPtsvalue(double ptsvalue) {
		this.ptsvalue = ptsvalue;
	}
	public String getHq_name() {
		return hq_name;
	}
	public void setHq_name(String hq_name) {
		this.hq_name = hq_name;
	}
	public int getStrn_tp() {
		return strn_tp;
	}
	public void setStrn_tp(int strn_tp) {
		this.strn_tp = strn_tp;
	}
	public String getParty_code() {
		return party_code;
	}
	public void setParty_code(String party_code) {
		this.party_code = party_code;
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
	public String getTransport() {
		return transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	public int getCases() {
		return cases;
	}
	public void setCases(int cases) {
		this.cases = cases;
	}
	public void setOpening(int opening) {
		this.opening = opening;
	}
	public int getDash() {
		return dash;
	}
	public void setDash(int dash) {
		this.dash = dash;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}


	
	

	public Date getReceipt_date() {
		return receipt_date;
	}
	public void setReceipt_date(Date receipt_date) {
		this.receipt_date = receipt_date;
	}
	public Date getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}
	public int getReceipt_qty() {
		return receipt_qty;
	}
	public void setReceipt_qty(int receipt_qty) {
		this.receipt_qty = receipt_qty;
	}
	public int getIssue_qty() {
		return issue_qty;
	}
	public void setIssue_qty(int issue_qty) {
		this.issue_qty = issue_qty;
	}
	public int getIssue_free() {
		return issue_free;
	}
	public void setIssue_free(int issue_free) {
		this.issue_free = issue_free;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
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
	
	
	
}
