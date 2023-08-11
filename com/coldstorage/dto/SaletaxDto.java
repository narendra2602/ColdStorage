package com.coldstorage.dto;

import java.util.Date;

public class SaletaxDto 
{
	private int dash;
	private String inv_no;
	private Date inv_date;
	private String city;
	private String tin;
	private double sale1;
	private double sale2;
	private double sale3;
	private double sale9;
	private double sale10;

	private double st5;
	private double st3;
	private double st13;
	private double exempted;
	private double disc5;
	private double disc10;
	private double cramt;
	private double dramt;
	private double netamt;
	private double grossamt;
	private Date due_dt;
	
	private double ltax1_per;
	private double ltax2_per;
	private double ltax3_per;
	private double ltax9_per;
	private double ltax10_per;

	private double taxable1;
	private double taxable2;
	private double taxable3;
	private double taxable9;
	private double taxable10;

	private double cst_amt;
	private double strn1;
	private double strn2;
	private double strn3;
	private double strn4;
	private double strn5;
	private double strn6;
	private double strn7;
	private double strn8;
	private double strn9;
	private double taxablef1;
	private double taxablef2;
	private double taxablef3;
	private double taxablef9;
	private double taxablef10;

	private double discexempted;
	private double ctax1_amt;
	private double ctax2_amt;
	private double freight;
	private String remark;
	private double sc_amt;
	private double sc_amt1;
	private int cases;
	
	private double tax_free1;
	private double tax_free2;
	private double tax_free3;
	private double tax_free9;
	private double tax_free10;

	private double freeval1;
	private double freeval2;
	private double freeval3;
	private double freeval9;
	private double freeval10;

	private double paise;
	private double mfgval;
	
	private int code;
	private String pname;
	private String pack;
	
	private int stp1;
	private int stp2;
	private int stp3;
	private int stp4;
	private int stp5;
	private int stp6;
	private int stp7;
	private int stp8;
	private int stp9;
	private int tqty;
	
	private double srate_net;
	private double srate_mfg;
	private double srate_exc;
	private double srate_pur;
	private double srate_hos;
	private double spl_rate;
	private double mfgvalue;
	private double instvalueied;
	private double instvalueed;
	
	private int sqty;
	private int sfree_qty;
	private String spinv_no;
	private Date   spinv_date;
	
	private double ltax10_amt;
	private double discount_amt10;
	private double  freetax10_amt;
	private String party_code;
	
	private double st51;
	private double taxable51;
	private double scamt51;

	private String state_name;
	private String service_type;
	private long hsn_code;
	
	private double cgst_rate;
	private double cgst_amount;
	private double sgst_rate;
	private double sgst_amount;
	private double igst_rate;
	private double igst_amount;
	private double cess_rate;
	private double cess_amount;

	private String mygst_no;

	private String address;
	private String mstate_name;
	private String type;
	private String reason;
	private String pregst;
	
	
	private double taxable33;
	private double sgst3;
	private double cgst3;
	private double igst3;
	
	
	private double taxable6;
	private double sgst6;
	private double cgst6;
	private double igst6;

	private String company_name;
	private String company_gst;
	private String company_add1;
	private String company_add2;
	private String company_city;
	private String company_pin;
	private String company_state;

	private String party_name;
	private String party_gst;
	private String party_add1;
	private String party_add2;
	private String party_city;
	private String party_pin;
	private String party_state;
	private String tax_rate;
	private double total_value;
	
	private String distance;
	private String trasnport;
	private String transportId;
	
	private String transportdoc;
	private Date transportdate;
	private String vehicleno;
	
	private int sno;
	private int mnth_code;
	private String divname;
	private String doc_type;
	private String sale_desc;
	
	
	
	
	public String getSale_desc() {
		return sale_desc;
	}
	public void setSale_desc(String sale_desc) {
		this.sale_desc = sale_desc;
	}
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public int getMnth_code() {
		return mnth_code;
	}
	public void setMnth_code(int mnth_code) {
		this.mnth_code = mnth_code;
	}
	public String getDivname() {
		return divname;
	}
	public void setDivname(String divname) {
		this.divname = divname;
	}
	public String getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}
	public String getTransportdoc() {
		return transportdoc;
	}
	public void setTransportdoc(String transportdoc) {
		this.transportdoc = transportdoc;
	}
	public Date getTransportdate() {
		return transportdate;
	}
	public void setTransportdate(Date transportdate) {
		this.transportdate = transportdate;
	}
	public String getVehicleno() {
		return vehicleno;
	}
	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}
	public String getTax_rate() {
		return tax_rate;
	}
	public void setTax_rate(String tax_rate) {
		this.tax_rate = tax_rate;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_gst() {
		return company_gst;
	}
	public void setCompany_gst(String company_gst) {
		this.company_gst = company_gst;
	}
	public String getCompany_add1() {
		return company_add1;
	}
	public void setCompany_add1(String company_add1) {
		this.company_add1 = company_add1;
	}
	public String getCompany_add2() {
		return company_add2;
	}
	public void setCompany_add2(String company_add2) {
		this.company_add2 = company_add2;
	}
	public String getCompany_city() {
		return company_city;
	}
	public void setCompany_city(String company_city) {
		this.company_city = company_city;
	}
	public String getCompany_pin() {
		return company_pin;
	}
	public void setCompany_pin(String company_pin) {
		this.company_pin = company_pin;
	}
	public String getCompany_state() {
		return company_state;
	}
	public void setCompany_state(String company_state) {
		this.company_state = company_state;
	}
	public String getParty_gst() {
		return party_gst;
	}
	public void setParty_gst(String party_gst) {
		this.party_gst = party_gst;
	}
	public String getParty_add1() {
		return party_add1;
	}
	public void setParty_add1(String party_add1) {
		this.party_add1 = party_add1;
	}
	public String getParty_add2() {
		return party_add2;
	}
	public void setParty_add2(String party_add2) {
		this.party_add2 = party_add2;
	}
	public String getParty_city() {
		return party_city;
	}
	public void setParty_city(String party_city) {
		this.party_city = party_city;
	}
	public String getParty_pin() {
		return party_pin;
	}
	public void setParty_pin(String party_pin) {
		this.party_pin = party_pin;
	}
	public String getParty_state() {
		return party_state;
	}
	public void setParty_state(String party_state) {
		this.party_state = party_state;
	}
	public double getTotal_value() {
		return total_value;
	}
	public void setTotal_value(double total_value) {
		this.total_value = total_value;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getTrasnport() {
		return trasnport;
	}
	public void setTrasnport(String trasnport) {
		this.trasnport = trasnport;
	}
	public String getTransportId() {
		return transportId;
	}
	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}
	public double getTaxable6() {
		return taxable6;
	}
	public void setTaxable6(double taxable6) {
		this.taxable6 = taxable6;
	}
	public double getSgst6() {
		return sgst6;
	}
	public void setSgst6(double sgst6) {
		this.sgst6 = sgst6;
	}
	public double getCgst6() {
		return cgst6;
	}
	public void setCgst6(double cgst6) {
		this.cgst6 = cgst6;
	}
	public double getIgst6() {
		return igst6;
	}
	public void setIgst6(double igst6) {
		this.igst6 = igst6;
	}
	public double getTaxable33() {
		return taxable33;
	}
	public void setTaxable33(double taxable33) {
		this.taxable33 = taxable33;
	}
	public double getSgst3() {
		return sgst3;
	}
	public void setSgst3(double sgst3) {
		this.sgst3 = sgst3;
	}
	public double getCgst3() {
		return cgst3;
	}
	public void setCgst3(double cgst3) {
		this.cgst3 = cgst3;
	}
	public double getIgst3() {
		return igst3;
	}
	public void setIgst3(double igst3) {
		this.igst3 = igst3;
	}
	public String getPregst() {
		return pregst;
	}
	public void setPregst(String pregst) {
		this.pregst = pregst;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMstate_name() {
		return mstate_name;
	}
	public void setMstate_name(String mstate_name) {
		this.mstate_name = mstate_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMygst_no() {
		return mygst_no;
	}
	public void setMygst_no(String mygst_no) {
		this.mygst_no = mygst_no;
	}
	public double getCgst_rate() {
		return cgst_rate;
	}
	public void setCgst_rate(double cgst_rate) {
		this.cgst_rate = cgst_rate;
	}
	public double getCgst_amount() {
		return cgst_amount;
	}
	public void setCgst_amount(double cgst_amount) {
		this.cgst_amount = cgst_amount;
	}
	public double getSgst_rate() {
		return sgst_rate;
	}
	public void setSgst_rate(double sgst_rate) {
		this.sgst_rate = sgst_rate;
	}
	public double getSgst_amount() {
		return sgst_amount;
	}
	public void setSgst_amount(double sgst_amount) {
		this.sgst_amount = sgst_amount;
	}
	public double getIgst_rate() {
		return igst_rate;
	}
	public void setIgst_rate(double igst_rate) {
		this.igst_rate = igst_rate;
	}
	public double getIgst_amount() {
		return igst_amount;
	}
	public void setIgst_amount(double igst_amount) {
		this.igst_amount = igst_amount;
	}
	public double getCess_rate() {
		return cess_rate;
	}
	public void setCess_rate(double cess_rate) {
		this.cess_rate = cess_rate;
	}
	public double getCess_amount() {
		return cess_amount;
	}
	public void setCess_amount(double cess_amount) {
		this.cess_amount = cess_amount;
	}
	
	public long getHsn_code() {
		return hsn_code;
	}
	public void setHsn_code(long hsn_code) {
		this.hsn_code = hsn_code;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public double getSale9() {
		return sale9;
	}
	public void setSale9(double sale9) {
		this.sale9 = sale9;
	}
	public double getScamt51() {
		return scamt51;
	}
	public void setScamt51(double scamt51) {
		this.scamt51 = scamt51;
	}
	public double getTaxable51() {
		return taxable51;
	}
	public void setTaxable51(double taxable51) {
		this.taxable51 = taxable51;
	}
	public double getSt51() {
		return st51;
	}
	public void setSt51(double st51) {
		this.st51 = st51;
	}
	public String getParty_code() {
		return party_code;
	}
	public void setParty_code(String party_code) {
		this.party_code = party_code;
	}
	public double getLtax10_amt() {
		return ltax10_amt;
	}
	public void setLtax10_amt(double ltax10_amt) {
		this.ltax10_amt = ltax10_amt;
	}
	public double getDiscount_amt10() {
		return discount_amt10;
	}
	public void setDiscount_amt10(double discount_amt10) {
		this.discount_amt10 = discount_amt10;
	}
	public double getFreetax10_amt() {
		return freetax10_amt;
	}
	public void setFreetax10_amt(double freetax10_amt) {
		this.freetax10_amt = freetax10_amt;
	}
	public double getSale10() {
		return sale10;
	}
	public void setSale10(double sale10) {
		this.sale10 = sale10;
	}
	public double getLtax10_per() {
		return ltax10_per;
	}
	public void setLtax10_per(double ltax10_per) {
		this.ltax10_per = ltax10_per;
	}
	public double getTaxable10() {
		return taxable10;
	}
	public void setTaxable10(double taxable10) {
		this.taxable10 = taxable10;
	}
	public double getTaxablef10() {
		return taxablef10;
	}
	public void setTaxablef10(double taxablef10) {
		this.taxablef10 = taxablef10;
	}
	public double getTax_free10() {
		return tax_free10;
	}
	public void setTax_free10(double tax_free10) {
		this.tax_free10 = tax_free10;
	}
	public double getFreeval10() {
		return freeval10;
	}
	public void setFreeval10(double freeval10) {
		this.freeval10 = freeval10;
	}
	public String getSpinv_no() {
		return spinv_no;
	}
	public void setSpinv_no(String spinv_no) {
		this.spinv_no = spinv_no;
	}
	public Date getSpinv_date() {
		return spinv_date;
	}
	public void setSpinv_date(Date spinv_date) {
		this.spinv_date = spinv_date;
	}
	public double getSrate_hos() {
		return srate_hos;
	}
	public void setSrate_hos(double srate_hos) {
		this.srate_hos = srate_hos;
	}
	public double getSpl_rate() {
		return spl_rate;
	}
	public void setSpl_rate(double spl_rate) {
		this.spl_rate = spl_rate;
	}
	public double getMfgvalue() {
		return mfgvalue;
	}
	public void setMfgvalue(double mfgvalue) {
		this.mfgvalue = mfgvalue;
	}
	public double getInstvalueied() {
		return instvalueied;
	}
	public void setInstvalueied(double instvalueied) {
		this.instvalueied = instvalueied;
	}
	public double getInstvalueed() {
		return instvalueed;
	}
	public void setInstvalueed(double instvalueed) {
		this.instvalueed = instvalueed;
	}
	public double getSrate_net() {
		return srate_net;
	}
	public void setSrate_net(double srate_net) {
		this.srate_net = srate_net;
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
	public double getSrate_pur() {
		return srate_pur;
	}
	public void setSrate_pur(double srate_pur) {
		this.srate_pur = srate_pur;
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
	public int getTqty() {
		return tqty;
	}
	public void setTqty(int tqty) {
		this.tqty = tqty;
	}
	public int getStp1() {
		return stp1;
	}
	public void setStp1(int stp1) {
		this.stp1 = stp1;
	}
	public int getStp2() {
		return stp2;
	}
	public void setStp2(int stp2) {
		this.stp2 = stp2;
	}
	public int getStp3() {
		return stp3;
	}
	public void setStp3(int stp3) {
		this.stp3 = stp3;
	}
	public int getStp4() {
		return stp4;
	}
	public void setStp4(int stp4) {
		this.stp4 = stp4;
	}
	public int getStp5() {
		return stp5;
	}
	public void setStp5(int stp5) {
		this.stp5 = stp5;
	}
	public int getStp6() {
		return stp6;
	}
	public void setStp6(int stp6) {
		this.stp6 = stp6;
	}
	public int getStp7() {
		return stp7;
	}
	public void setStp7(int stp7) {
		this.stp7 = stp7;
	}
	public int getStp8() {
		return stp8;
	}
	public void setStp8(int stp8) {
		this.stp8 = stp8;
	}
	public int getStp9() {
		return stp9;
	}
	public void setStp9(int stp9) {
		this.stp9 = stp9;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
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
	public double getMfgval() {
		return mfgval;
	}
	public void setMfgval(double mfgval) {
		this.mfgval = mfgval;
	}
	public double getPaise() {
		return paise;
	}
	public void setPaise(double paise) {
		this.paise = paise;
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
	public double getTax_free1() {
		return tax_free1;
	}
	public void setTax_free1(double tax_free1) {
		this.tax_free1 = tax_free1;
	}
	public double getTax_free2() {
		return tax_free2;
	}
	public void setTax_free2(double tax_free2) {
		this.tax_free2 = tax_free2;
	}
	public double getTax_free3() {
		return tax_free3;
	}
	public void setTax_free3(double tax_free3) {
		this.tax_free3 = tax_free3;
	}
	public double getTax_free9() {
		return tax_free9;
	}
	public void setTax_free9(double tax_free9) {
		this.tax_free9 = tax_free9;
	}
	public int getCases() {
		return cases;
	}
	public void setCases(int cases) {
		this.cases = cases;
	}
	public double getLtax9_per() {
		return ltax9_per;
	}
	public void setLtax9_per(double ltax9_per) {
		this.ltax9_per = ltax9_per;
	}
	public double getSt3() {
		return st3;
	}
	public void setSt3(double st3) {
		this.st3 = st3;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getFreight() {
		return freight;
	}
	public void setFreight(double freight) {
		this.freight = freight;
	}
	public double getCtax1_amt() {
		return ctax1_amt;
	}
	public void setCtax1_amt(double ctax1_amt) {
		this.ctax1_amt = ctax1_amt;
	}
	public double getCtax2_amt() {
		return ctax2_amt;
	}
	public void setCtax2_amt(double ctax2_amt) {
		this.ctax2_amt = ctax2_amt;
	}
	public double getDiscexempted() {
		return discexempted;
	}
	public void setDiscexempted(double discexempted) {
		this.discexempted = discexempted;
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
	public double getTaxablef1() {
		return taxablef1;
	}
	public void setTaxablef1(double taxablef1) {
		this.taxablef1 = taxablef1;
	}
	public double getCst_amt() {
		return cst_amt;
	}
	public void setCst_amt(double cst_amt) {
		this.cst_amt = cst_amt;
	}
	public double getStrn1() {
		return strn1;
	}
	public void setStrn1(double strn1) {
		this.strn1 = strn1;
	}
	public double getStrn2() {
		return strn2;
	}
	public void setStrn2(double strn2) {
		this.strn2 = strn2;
	}
	public double getStrn3() {
		return strn3;
	}
	public void setStrn3(double strn3) {
		this.strn3 = strn3;
	}
	public double getStrn4() {
		return strn4;
	}
	public void setStrn4(double strn4) {
		this.strn4 = strn4;
	}
	public double getStrn5() {
		return strn5;
	}
	public void setStrn5(double strn5) {
		this.strn5 = strn5;
	}
	public double getStrn6() {
		return strn6;
	}
	public void setStrn6(double strn6) {
		this.strn6 = strn6;
	}
	public double getStrn7() {
		return strn7;
	}
	public void setStrn7(double strn7) {
		this.strn7 = strn7;
	}
	public double getStrn8() {
		return strn8;
	}
	public void setStrn8(double strn8) {
		this.strn8 = strn8;
	}
	public double getStrn9() {
		return strn9;
	}
	public void setStrn9(double strn9) {
		this.strn9 = strn9;
	}
	public int getDash() {
		return dash;
	}
	public void setDash(int dash) {
		this.dash = dash;
	}
	public String getInv_no() {
		return inv_no;
	}
	public void setInv_no(String inv_no) {
		this.inv_no = inv_no;
	}
	public Date getInv_date() {
		return inv_date;
	}
	public void setInv_date(Date inv_date) {
		this.inv_date = inv_date;
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
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public double getSale1() {
		return sale1;
	}
	public void setSale1(double sale1) {
		this.sale1 = sale1;
	}
	public double getSale2() {
		return sale2;
	}
	public void setSale2(double sale2) {
		this.sale2 = sale2;
	}
	public double getSale3() {
		return sale3;
	}
	public void setSale3(double sale3) {
		this.sale3 = sale3;
	}
	public double getSt5() {
		return st5;
	}
	public void setSt5(double st5) {
		this.st5 = st5;
	}
	public double getSt13() {
		return st13;
	}
	public void setSt13(double st13) {
		this.st13 = st13;
	}
	public double getExempted() {
		return exempted;
	}
	public void setExempted(double exempted) {
		this.exempted = exempted;
	}
	public double getDisc5() {
		return disc5;
	}
	public void setDisc5(double disc5) {
		this.disc5 = disc5;
	}
	public double getDisc10() {
		return disc10;
	}
	public void setDisc10(double disc10) {
		this.disc10 = disc10;
	}
	public double getCramt() {
		return cramt;
	}
	public void setCramt(double cramt) {
		this.cramt = cramt;
	}
	public double getDramt() {
		return dramt;
	}
	public void setDramt(double dramt) {
		this.dramt = dramt;
	}
	public double getNetamt() {
		return netamt;
	}
	public void setNetamt(double netamt) {
		this.netamt = netamt;
	}
	public double getGrossamt() {
		return grossamt;
	}
	public void setGrossamt(double grossamt) {
		this.grossamt = grossamt;
	}
	public Date getDue_dt() {
		return due_dt;
	}
	public void setDue_dt(Date due_dt) {
		this.due_dt = due_dt;
	}
	public double getLtax1_per() {
		return ltax1_per;
	}
	public void setLtax1_per(double ltax1_per) {
		this.ltax1_per = ltax1_per;
	}
	public double getLtax2_per() {
		return ltax2_per;
	}
	public void setLtax2_per(double ltax2_per) {
		this.ltax2_per = ltax2_per;
	}
	public double getLtax3_per() {
		return ltax3_per;
	}
	public void setLtax3_per(double ltax3_per) {
		this.ltax3_per = ltax3_per;
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
	
	
	
	
	
}
