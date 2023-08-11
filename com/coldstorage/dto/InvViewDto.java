package com.coldstorage.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Vector;

/**
 * @author Singh
 *
 */
public class InvViewDto 
{
		// First File info
		private int depo_code;
		private int doc_type;
		private String mac_code;
		private String mac_name;
		private String madd1;
		private String madd2;
		private String madd3;
		private String mcity;
		private String mdlno1;
		private String mdlno2;
		private String mpst_no;
		private String mcst_no;
		private String inv_no;
		private Date inv_date;
		private Date due_date;
		private  double  gross_amt;
		private  double  cn_val;
		private  double  dn_val;
		private  double  bill_amt;
		private String numword;
		private String frg_tag;
		private double discount_amt;
		private double spldiscount_amt;
		private String mpin;
		private String mphone;
		// Second file info
		private String pname;
		
		private String sbatch_no;
		private String sexp_dt;
		private double srate_mrp;
		private double srate_trd;
		private double srate_net;
		private double sqty;
		private int sfree_qty;
		private double samnt;
		private Date disptach_date;
		private Date entry_date;
		private double freetax1;
		private double freetax2;
		private double tdsamt;
		
		// Store product info in vector
		private Vector item;
		
		// Store Creditnote info in ArrayList
		private ArrayList crdetail;
		
		// Third file info
		private String cdnote_no;
		private Date cdinv_dt;
		private double cdnote_amt;
		private String cdnote_tp;
	
	
		private int bunch_no;
		private String bank;
		private String remark;
		private double disc_per;
		private double discount_amt2;
		private double discount_amt3;
		private String mbank_add1;
		private String mbank_add2;
		private double octroi;
		
		private String porder_no;
		private Date porder_date;
		private String chl_no;
		private Date chl_date;
		private String aproval_no;
		private Date aproval_date;
		private int terr_cd;
		
		private String docnm;
		private String prt_type;
		private String fromhq;
		private String tohq;
		private int stat_code;
		private double weight;
		
		
		private int regn_cd;
		private int area_cd;
		
		private ArrayList rcpdetail;
		
		
		private String mac_name_hindi;
		private String mcity_hindi;
		private int sinv_no;
		private double round_off;
		private double net_amt;
		private String category;
		private String group_name;
		private int sprd_cd;
		private int spd_gp;
		
		private  int  created_by;
		private  Date  created_date;
		private  int  modified_by;
		private  Date  modified_date;
		private  int fin_year;
		private String group_name_hindi;
		private String pname_hindi;
		private String category_hindi;
		private ArrayList markalist;
		private String mark_no;
		private String floor_no;
		private String block_no;
		private String receiver_name;
		private String mobile;
		private int block_qty;
        private boolean marka;
        private double issue_qty;
        private double issue_weight;
        private String vehicle_no;
        private int totqty;
		private String pinv_no;
		private Date pinv_date;
        private String pack;
        private int rst_no;
        private String pack_name;
        private int serialno;
        private int dash;
        private String receiver_city;
        private String receiver_account;
        private int transfer_no;
        private double written_off_qty;
        private double written_off_weight;
        private double total_weight;
        private double weight_per_qty;
        private int div_code;
        
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
		public String getMcity() {
			return mcity;
		}
		public void setMcity(String mcity) {
			this.mcity = mcity;
		}
		public String getMdlno1() {
			return mdlno1;
		}
		public void setMdlno1(String mdlno1) {
			this.mdlno1 = mdlno1;
		}
		public String getMdlno2() {
			return mdlno2;
		}
		public void setMdlno2(String mdlno2) {
			this.mdlno2 = mdlno2;
		}
		public String getMpst_no() {
			return mpst_no;
		}
		public void setMpst_no(String mpst_no) {
			this.mpst_no = mpst_no;
		}
		public String getMcst_no() {
			return mcst_no;
		}
		public void setMcst_no(String mcst_no) {
			this.mcst_no = mcst_no;
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
		public Date getDue_date() {
			return due_date;
		}
		public void setDue_date(Date due_date) {
			this.due_date = due_date;
		}
		public double getGross_amt() {
			return gross_amt;
		}
		public void setGross_amt(double gross_amt) {
			this.gross_amt = gross_amt;
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
		public double getBill_amt() {
			return bill_amt;
		}
		public void setBill_amt(double bill_amt) {
			this.bill_amt = bill_amt;
		}
		public String getNumword() {
			return numword;
		}
		public void setNumword(String numword) {
			this.numword = numword;
		}
		public String getFrg_tag() {
			return frg_tag;
		}
		public void setFrg_tag(String frg_tag) {
			this.frg_tag = frg_tag;
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
		public String getMpin() {
			return mpin;
		}
		public void setMpin(String mpin) {
			this.mpin = mpin;
		}
		public String getMphone() {
			return mphone;
		}
		public void setMphone(String mphone) {
			this.mphone = mphone;
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
		public String getSbatch_no() {
			return sbatch_no;
		}
		public void setSbatch_no(String sbatch_no) {
			this.sbatch_no = sbatch_no;
		}
		public String getSexp_dt() {
			return sexp_dt;
		}
		public void setSexp_dt(String sexp_dt) {
			this.sexp_dt = sexp_dt;
		}
		public double getSrate_mrp() {
			return srate_mrp;
		}
		public void setSrate_mrp(double srate_mrp) {
			this.srate_mrp = srate_mrp;
		}
		public double getSrate_trd() {
			return srate_trd;
		}
		public void setSrate_trd(double srate_trd) {
			this.srate_trd = srate_trd;
		}
		public double getSrate_net() {
			return srate_net;
		}
		public void setSrate_net(double srate_net) {
			this.srate_net = srate_net;
		}
		public double getSqty() {
			return sqty;
		}
		public void setSqty(double sqty) {
			this.sqty = sqty;
		}
		public int getSfree_qty() {
			return sfree_qty;
		}
		public void setSfree_qty(int sfree_qty) {
			this.sfree_qty = sfree_qty;
		}
		public double getSamnt() {
			return samnt;
		}
		public void setSamnt(double samnt) {
			this.samnt = samnt;
		}
		public Date getDisptach_date() {
			return disptach_date;
		}
		public void setDisptach_date(Date disptach_date) {
			this.disptach_date = disptach_date;
		}
		public Date getEntry_date() {
			return entry_date;
		}
		public void setEntry_date(Date entry_date) {
			this.entry_date = entry_date;
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
		public double getTdsamt() {
			return tdsamt;
		}
		public void setTdsamt(double tdsamt) {
			this.tdsamt = tdsamt;
		}
		public Vector getItem() {
			return item;
		}
		public void setItem(Vector item) {
			this.item = item;
		}
		public ArrayList getCrdetail() {
			return crdetail;
		}
		public void setCrdetail(ArrayList crdetail) {
			this.crdetail = crdetail;
		}
		public String getCdnote_no() {
			return cdnote_no;
		}
		public void setCdnote_no(String cdnote_no) {
			this.cdnote_no = cdnote_no;
		}
		public Date getCdinv_dt() {
			return cdinv_dt;
		}
		public void setCdinv_dt(Date cdinv_dt) {
			this.cdinv_dt = cdinv_dt;
		}
		public double getCdnote_amt() {
			return cdnote_amt;
		}
		public void setCdnote_amt(double cdnote_amt) {
			this.cdnote_amt = cdnote_amt;
		}
		public String getCdnote_tp() {
			return cdnote_tp;
		}
		public void setCdnote_tp(String cdnote_tp) {
			this.cdnote_tp = cdnote_tp;
		}
		public int getBunch_no() {
			return bunch_no;
		}
		public void setBunch_no(int bunch_no) {
			this.bunch_no = bunch_no;
		}
		public String getBank() {
			return bank;
		}
		public void setBank(String bank) {
			this.bank = bank;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public double getDisc_per() {
			return disc_per;
		}
		public void setDisc_per(double disc_per) {
			this.disc_per = disc_per;
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
		public double getOctroi() {
			return octroi;
		}
		public void setOctroi(double octroi) {
			this.octroi = octroi;
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
		public String getPorder_no() {
			return porder_no;
		}
		public void setPorder_no(String porder_no) {
			this.porder_no = porder_no;
		}
		public Date getPorder_date() {
			return porder_date;
		}
		public void setPorder_date(Date porder_date) {
			this.porder_date = porder_date;
		}
		public String getChl_no() {
			return chl_no;
		}
		public void setChl_no(String chl_no) {
			this.chl_no = chl_no;
		}
		public Date getChl_date() {
			return chl_date;
		}
		public void setChl_date(Date chl_date) {
			this.chl_date = chl_date;
		}
		public String getAproval_no() {
			return aproval_no;
		}
		public void setAproval_no(String aproval_no) {
			this.aproval_no = aproval_no;
		}
		public Date getAproval_date() {
			return aproval_date;
		}
		public void setAproval_date(Date aproval_date) {
			this.aproval_date = aproval_date;
		}
		public int getTerr_cd() {
			return terr_cd;
		}
		public void setTerr_cd(int terr_cd) {
			this.terr_cd = terr_cd;
		}
		public String getDocnm() {
			return docnm;
		}
		public void setDocnm(String docnm) {
			this.docnm = docnm;
		}
		public String getPrt_type() {
			return prt_type;
		}
		public void setPrt_type(String prt_type) {
			this.prt_type = prt_type;
		}
		public String getFromhq() {
			return fromhq;
		}
		public void setFromhq(String fromhq) {
			this.fromhq = fromhq;
		}
		public String getTohq() {
			return tohq;
		}
		public void setTohq(String tohq) {
			this.tohq = tohq;
		}
		public int getStat_code() {
			return stat_code;
		}
		public void setStat_code(int stat_code) {
			this.stat_code = stat_code;
		}
		public double getWeight() {
			return weight;
		}
		public void setWeight(double weight) {
			this.weight = weight;
		}
		public int getRegn_cd() {
			return regn_cd;
		}
		public void setRegn_cd(int regn_cd) {
			this.regn_cd = regn_cd;
		}
		public int getArea_cd() {
			return area_cd;
		}
		public void setArea_cd(int area_cd) {
			this.area_cd = area_cd;
		}
		public ArrayList getRcpdetail() {
			return rcpdetail;
		}
		public void setRcpdetail(ArrayList rcpdetail) {
			this.rcpdetail = rcpdetail;
		}
		public String getMac_name_hindi() {
			return mac_name_hindi;
		}
		public void setMac_name_hindi(String mac_name_hindi) {
			this.mac_name_hindi = mac_name_hindi;
		}
		public String getMcity_hindi() {
			return mcity_hindi;
		}
		public void setMcity_hindi(String mcity_hindi) {
			this.mcity_hindi = mcity_hindi;
		}
		public int getSinv_no() {
			return sinv_no;
		}
		public void setSinv_no(int sinv_no) {
			this.sinv_no = sinv_no;
		}
		public double getRound_off() {
			return round_off;
		}
		public void setRound_off(double round_off) {
			this.round_off = round_off;
		}
		public double getNet_amt() {
			return net_amt;
		}
		public void setNet_amt(double net_amt) {
			this.net_amt = net_amt;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getGroup_name() {
			return group_name;
		}
		public void setGroup_name(String group_name) {
			this.group_name = group_name;
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
		public int getFin_year() {
			return fin_year;
		}
		public void setFin_year(int fin_year) {
			this.fin_year = fin_year;
		}
		public String getGroup_name_hindi() {
			return group_name_hindi;
		}
		public void setGroup_name_hindi(String group_name_hindi) {
			this.group_name_hindi = group_name_hindi;
		}
		public String getPname_hindi() {
			return pname_hindi;
		}
		public void setPname_hindi(String pname_hindi) {
			this.pname_hindi = pname_hindi;
		}
		public String getCategory_hindi() {
			return category_hindi;
		}
		public void setCategory_hindi(String category_hindi) {
			this.category_hindi = category_hindi;
		}
		public ArrayList getMarkalist() {
			return markalist;
		}
		public void setMarkalist(ArrayList markalist) {
			this.markalist = markalist;
		}
		public String getMark_no() {
			return mark_no;
		}
		public void setMark_no(String mark_no) {
			this.mark_no = mark_no;
		}
		public String getFloor_no() {
			return floor_no;
		}
		public void setFloor_no(String floor_no) {
			this.floor_no = floor_no;
		}
		public String getBlock_no() {
			return block_no;
		}
		public void setBlock_no(String block_no) {
			this.block_no = block_no;
		}
		public String getReceiver_name() {
			return receiver_name;
		}
		public void setReceiver_name(String receiver_name) {
			this.receiver_name = receiver_name;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public int getBlock_qty() {
			return block_qty;
		}
		public void setBlock_qty(int block_qty) {
			this.block_qty = block_qty;
		}
		public boolean isMarka() {
			return marka;
		}
		public void setMarka(boolean marka) {
			this.marka = marka;
		}
		public double getIssue_qty() {
			return issue_qty;
		}
		public void setIssue_qty(double issue_qty) {
			this.issue_qty = issue_qty;
		}
		public double getIssue_weight() {
			return issue_weight;
		}
		public void setIssue_weight(double issue_weight) {
			this.issue_weight = issue_weight;
		}
		public String getVehicle_no() {
			return vehicle_no;
		}
		public void setVehicle_no(String vehicle_no) {
			this.vehicle_no = vehicle_no;
		}
		public int getTotqty() {
			return totqty;
		}
		public void setTotqty(int totqty) {
			this.totqty = totqty;
		}
		public int getRst_no() {
			return rst_no;
		}
		public void setRst_no(int rst_no) {
			this.rst_no = rst_no;
		}
		public String getPack_name() {
			return pack_name;
		}
		public void setPack_name(String pack_name) {
			this.pack_name = pack_name;
		}
		public int getSerialno() {
			return serialno;
		}
		public void setSerialno(int serialno) {
			this.serialno = serialno;
		}
		public int getDash() {
			return dash;
		}
		public void setDash(int dash) {
			this.dash = dash;
		}
		public String getReceiver_city() {
			return receiver_city;
		}
		public void setReceiver_city(String receiver_city) {
			this.receiver_city = receiver_city;
		}
		public String getReceiver_account() {
			return receiver_account;
		}
		public void setReceiver_account(String receiver_account) {
			this.receiver_account = receiver_account;
		}
		public int getTransfer_no() {
			return transfer_no;
		}
		public void setTransfer_no(int transfer_no) {
			this.transfer_no = transfer_no;
		}
		public double getWritten_off_qty() {
			return written_off_qty;
		}
		public void setWritten_off_qty(double written_off_qty) {
			this.written_off_qty = written_off_qty;
		}
		public double getWritten_off_weight() {
			return written_off_weight;
		}
		public void setWritten_off_weight(double written_off_weight) {
			this.written_off_weight = written_off_weight;
		}
		public double getTotal_weight() {
			return total_weight;
		}
		public void setTotal_weight(double total_weight) {
			this.total_weight = total_weight;
		}
		public double getWeight_per_qty() {
			return weight_per_qty;
		}
		public void setWeight_per_qty(double weight_per_qty) {
			this.weight_per_qty = weight_per_qty;
		}
		public int getDiv_code() {
			return div_code;
		}
		public void setDiv_code(int div_code) {
			this.div_code = div_code;
		}
        
        
		
}
