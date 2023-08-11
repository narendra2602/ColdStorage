package com.coldstorage.dto;

import java.util.Date;

public class TaxDto {

     private int depo_code;
     private int state_code;

	 private String tax_type;
	 private String tax_desc;
	 private double tax_per;
	 private String tax_on;
	 private String tax_on_free;
	 private double add_tax1;
	 private String add_desc1;
	 private double add_tax2;
	 private String add_desc2;
	 private Date sdate;
	 private Date edate;
	 private int serialno;
	 private String disc;
	 private double exc_rate;
	 private String cst_reim;
	 private double cgst_per;
	 private double igst_per;
	 private double cess_per;
	 private String gst_type;
	 
	 
	 
	public String getGst_type() {
		return gst_type;
	}
	public void setGst_type(String gst_type) {
		this.gst_type = gst_type;
	}
	public double getCgst_per() {
		return cgst_per;
	}
	public void setCgst_per(double cgst_per) {
		this.cgst_per = cgst_per;
	}
	public double getIgst_per() {
		return igst_per;
	}
	public void setIgst_per(double igst_per) {
		this.igst_per = igst_per;
	}
	public double getCess_per() {
		return cess_per;
	}
	public void setCess_per(double cess_per) {
		this.cess_per = cess_per;
	}
	public String getCst_reim() {
		return cst_reim;
	}
	public void setCst_reim(String cst_reim) {
		this.cst_reim = cst_reim;
	}
	public String getDisc() {
		return disc;
	}
	public void setDisc(String disc) {
		this.disc = disc;
	}
	public double getExc_rate() {
		return exc_rate;
	}
	public void setExc_rate(double exc_rate) {
		this.exc_rate = exc_rate;
	}
	public int getSerialno() {
		return serialno;
	}
	public void setSerialno(int serialno) {
		this.serialno = serialno;
	}
	public String getTax_on_free() {
		return tax_on_free;
	}
	public void setTax_on_free(String tax_on_free) {
		this.tax_on_free = tax_on_free;
	}
	public double getAdd_tax1() {
		return add_tax1;
	}
	public void setAdd_tax1(double add_tax1) {
		this.add_tax1 = add_tax1;
	}
	public String getAdd_desc1() {
		return add_desc1;
	}
	public void setAdd_desc1(String add_desc1) {
		this.add_desc1 = add_desc1;
	}
	public double getAdd_tax2() {
		return add_tax2;
	}
	public void setAdd_tax2(double add_tax2) {
		this.add_tax2 = add_tax2;
	}
	public String getAdd_desc2() {
		return add_desc2;
	}
	public void setAdd_desc2(String add_desc2) {
		this.add_desc2 = add_desc2;
	}
	public String getTax_desc() {
		return tax_desc;
	}
	public void setTax_desc(String tax_desc) {
		this.tax_desc = tax_desc;
	}
	public int getDepo_code() {
		return depo_code;
	}
	public void setDepo_code(int depo_code) {
		this.depo_code = depo_code;
	}
	public int getState_code() {
		return state_code;
	}
	public void setState_code(int state_code) {
		this.state_code = state_code;
	}
	public String getTax_type() {
		return tax_type;
	}
	public void setTax_type(String tax_type) {
		this.tax_type = tax_type;
	}
	public double getTax_per() {
		return tax_per;
	}
	public void setTax_per(double tax_per) {
		this.tax_per = tax_per;
	}
	public String getTax_on() {
		return tax_on;
	}
	public void setTax_on(String tax_on) {
		this.tax_on = tax_on;
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
