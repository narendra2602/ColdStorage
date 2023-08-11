package com.coldstorage.dto;

import java.util.Date;
import java.util.List;

public class ProductDto 
{
	private int  pcode;
	private String  pname;
	private String  pack;
	private int pack_code;
	private String  pack_name;
	private int  group_code;
	private String group_name;
	private int  sale_type;;
	private String  shelf_life;
	private String identity;
	private int thpt_code;
	private String  thpt_name;
	private String  speciality;
	private String  composite;
	private String  tax_type;
	private int  launch_mm;
	private int  sales_mm;
	private String  prd_bud_type;
	private int mgrp;
	private int mcode;
	private String mname;
	private String mgrp_name;
	private double trd_rt1;
	private double deal_rate;
	private double net_rt1;
	private double mfg_rt1;
	private double mrp_rt1;
	
	private int formula;
	private String  tnd;
	private int  old_div;
	private int  old_code;
	private String  status;
	private String np;
	private double tax_per;
	private double disc_cd1;
	private int schm_qty;
	private int schm_free;
	private String mfg_licno;
	private int ncase;
	private int box;
	private String box_type;
	private int dash;
	private int div_code;
	private int mkt_year;
	
	private String factory_name;
	private List batchList;
	
	// varaible for near expiry List
	private String Bat_no;
	private Date rcpdate;
	private String expiry;
	private int stock;
	private int estock;
	private int estock90;
	private int estock360;
	private int estock365;
	private double tvalue;
	private int bat_case;
	private double weight;
 
	private Date refdate;
	
	private long hsn_code;
	
	private int prd_opng;
	private int cmp_code;
	private String cmp_name;
	
	private int depo_code;
	
	private int prd_stck;
	
	
	
	
	 public int getPrd_stck() {
		return prd_stck;
	}



	public void setPrd_stck(int prd_stck) {
		this.prd_stck = prd_stck;
	}



	public int getDepo_code() {
		return depo_code;
	}



	public void setDepo_code(int depo_code) {
		this.depo_code = depo_code;
	}



	public int getCmp_code() {
		return cmp_code;
	}



	public void setCmp_code(int cmp_code) {
		this.cmp_code = cmp_code;
	}



	public String getCmp_name() {
		return cmp_name;
	}



	public void setCmp_name(String cmp_name) {
		this.cmp_name = cmp_name;
	}



	public int getPrd_opng() {
		return prd_opng;
	}



	public void setPrd_opng(int prd_opng) {
		this.prd_opng = prd_opng;
	}



	public long getHsn_code() {
		return hsn_code;
	}



	public void setHsn_code(long hsn_code) {
		this.hsn_code = hsn_code;
	}



	public Date getRefdate() {
		return refdate;
	}



	public void setRefdate(Date refdate) {
		this.refdate = refdate;
	}



	public int getEstock365() {
		return estock365;
	}



	public void setEstock365(int estock365) {
		this.estock365 = estock365;
	}



	public int getBat_case() {
		return bat_case;
	}



	public void setBat_case(int bat_case) {
		this.bat_case = bat_case;
	}



	public double getWeight() {
		return weight;
	}



	public void setWeight(double weight) {
		this.weight = weight;
	}



	public int getDash() {
		return dash;
	}



	public void setDash(int dash) {
		this.dash = dash;
	}



	public String getFactory_name() {
		return factory_name;
	}



	public void setFactory_name(String factory_name) {
		this.factory_name = factory_name;
	}



	public double getTvalue() {
		return tvalue;
	}



	public void setTvalue(double tvalue) {
		this.tvalue = tvalue;
	}



	public String getBat_no() {
		return Bat_no;
	}



	public void setBat_no(String bat_no) {
		Bat_no = bat_no;
	}



	public Date getRcpdate() {
		return rcpdate;
	}



	public void setRcpdate(Date rcpdate) {
		this.rcpdate = rcpdate;
	}



	public String getExpiry() {
		return expiry;
	}



	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}



	public int getStock() {
		return stock;
	}



	public void setStock(int stock) {
		this.stock = stock;
	}



	public int getEstock() {
		return estock;
	}



	public void setEstock(int estock) {
		this.estock = estock;
	}



	public int getEstock90() {
		return estock90;
	}



	public void setEstock90(int estock90) {
		this.estock90 = estock90;
	}



	public int getEstock360() {
		return estock360;
	}



	public void setEstock360(int estock360) {
		this.estock360 = estock360;
	}



	public List getBatchList() {
		return batchList;
	}



	public void setBatchList(List batchList) {
		this.batchList = batchList;
	}



	public int getMkt_year() {
		return mkt_year;
	}



	public void setMkt_year(int mkt_year) {
		this.mkt_year = mkt_year;
	}



	public int getDiv_code() {
		return div_code;
	}



	public void setDiv_code(int div_code) {
		this.div_code = div_code;
	}



	public int getNcase() {
		return ncase;
	}



	public void setNcase(int ncase) {
		this.ncase = ncase;
	}



	public int getBox() {
		return box;
	}



	public void setBox(int box) {
		this.box = box;
	}



	public String getBox_type() {
		return box_type;
	}



	public void setBox_type(String box_type) {
		this.box_type = box_type;
	}



	public int getPack_code() {
		return pack_code;
	}



	public void setPack_code(int pack_code) {
		this.pack_code = pack_code;
	}



	public String getGroup_name() {
		return group_name;
	}



	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}



	public int getSale_type() {
		return sale_type;
	}



	public void setSale_type(int sale_type) {
		this.sale_type = sale_type;
	}



	public String getIdentity() {
		return identity;
	}



	public void setIdentity(String identity) {
		this.identity = identity;
	}



	public int getThpt_code() {
		return thpt_code;
	}



	public void setThpt_code(int thpt_code) {
		this.thpt_code = thpt_code;
	}



	public String getThpt_name() {
		return thpt_name;
	}



	public void setThpt_name(String thpt_name) {
		this.thpt_name = thpt_name;
	}



	public String getComposite() {
		return composite;
	}



	public void setComposite(String composite) {
		this.composite = composite;
	}



	public int getLaunch_mm() {
		return launch_mm;
	}



	public void setLaunch_mm(int launch_mm) {
		this.launch_mm = launch_mm;
	}



	public int getSales_mm() {
		return sales_mm;
	}



	public void setSales_mm(int sales_mm) {
		this.sales_mm = sales_mm;
	}



	public String getMname() {
		return mname;
	}



	public void setMname(String mname) {
		this.mname = mname;
	}



	public String getMgrp_name() {
		return mgrp_name;
	}



	public void setMgrp_name(String mgrp_name) {
		this.mgrp_name = mgrp_name;
	}



	public double getTrd_rt1() {
		return trd_rt1;
	}



	public void setTrd_rt1(double trd_rt1) {
		this.trd_rt1 = trd_rt1;
	}



	public double getDeal_rate() {
		return deal_rate;
	}



	public void setDeal_rate(double deal_rate) {
		this.deal_rate = deal_rate;
	}



	public double getNet_rt1() {
		return net_rt1;
	}



	public void setNet_rt1(double net_rt1) {
		this.net_rt1 = net_rt1;
	}



	public double getMfg_rt1() {
		return mfg_rt1;
	}



	public void setMfg_rt1(double mfg_rt1) {
		this.mfg_rt1 = mfg_rt1;
	}



	public double getMrp_rt1() {
		return mrp_rt1;
	}



	public void setMrp_rt1(double mrp_rt1) {
		this.mrp_rt1 = mrp_rt1;
	}



	public String getNp() {
		return np;
	}



	public void setNp(String np) {
		this.np = np;
	}



	public double getTax_per() {
		return tax_per;
	}



	public void setTax_per(double tax_per) {
		this.tax_per = tax_per;
	}



	public double getDisc_cd1() {
		return disc_cd1;
	}



	public void setDisc_cd1(double disc_cd1) {
		this.disc_cd1 = disc_cd1;
	}



	public int getSchm_free() {
		return schm_free;
	}



	public void setSchm_free(int schm_free) {
		this.schm_free = schm_free;
	}



	public String getMfg_licno() {
		return mfg_licno;
	}



	public void setMfg_licno(String mfg_licno) {
		this.mfg_licno = mfg_licno;
	}



	public int getFormula() {
		return formula;
	}



	public void setFormula(int formula) {
		this.formula = formula;
	}



	public String toString()
     {
         return pname;
     }	
	 
	 
	 
	public int getMgrp() {
		return mgrp;
	}



	public void setMgrp(int mgrp) {
		this.mgrp = mgrp;
	}



	public int getMcode() {
		return mcode;
	}



	public void setMcode(int mcode) {
		this.mcode = mcode;
	}

	public int getSchm_qty() {
		return schm_qty;
	}


	public void setSchm_qty(int schm_qty) {
		this.schm_qty = schm_qty;
	}


	public int getPcode() {
		return pcode;
	}
	public void setPcode(int pcode) {
		this.pcode = pcode;
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
	public String getPack_name() {
		return pack_name;
	}
	public void setPack_name(String pack_name) {
		this.pack_name = pack_name;
	}
	public int getGroup_code() {
		return group_code;
	}
	public void setGroup_code(int group_code) {
		this.group_code = group_code;
	}
	public String getShelf_life() {
		return shelf_life;
	}
	public void setShelf_life(String shelf_life) {
		this.shelf_life = shelf_life;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getTax_type() {
		return tax_type;
	}
	public void setTax_type(String tax_type) {
		this.tax_type = tax_type;
	}

	public String getTnd() {
		return tnd;
	}
	public void setTnd(String tnd) {
		this.tnd = tnd;
	}
	public int getOld_div() {
		return old_div;
	}
	public void setOld_div(int old_div) {
		this.old_div = old_div;
	}
	public int getOld_code() {
		return old_code;
	}
	public void setOld_code(int old_code) {
		this.old_code = old_code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrd_bud_type() {
		return prd_bud_type;
	}
	public void setPrd_bud_type(String prd_bud_type) {
		this.prd_bud_type = prd_bud_type;
	}

	
	
	
	
	
	
}
