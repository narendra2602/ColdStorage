package com.coldstorage.dto;

public class CmpInvDto
{

	private int depo_code;
	private int cmp_code;
	private String cmp_name;
	private String cmp_type;
	private int inv_doc;
	private int pur_doc;
	private int cr_doc;
	private int ret_doc;
	private int vat_doc;
	private int tr_doc;
	private String remark;
	
	
	public String toString()
	{
		return cmp_name;
	}
	
	public int getDepo_code() 
	{
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
	public String getCmp_type() {
		return cmp_type;
	}
	public void setCmp_type(String cmp_type) {
		this.cmp_type = cmp_type;
	}
	public int getInv_doc() {
		return inv_doc;
	}
	public void setInv_doc(int inv_doc) {
		this.inv_doc = inv_doc;
	}
	public int getPur_doc() {
		return pur_doc;
	}
	public void setPur_doc(int pur_doc) {
		this.pur_doc = pur_doc;
	}
	public int getCr_doc() {
		return cr_doc;
	}
	public void setCr_doc(int cr_doc) {
		this.cr_doc = cr_doc;
	}
	public int getRet_doc() {
		return ret_doc;
	}
	public void setRet_doc(int ret_doc) {
		this.ret_doc = ret_doc;
	}
	public int getVat_doc() {
		return vat_doc;
	}
	public void setVat_doc(int vat_doc) {
		this.vat_doc = vat_doc;
	}
	public int getTr_doc() {
		return tr_doc;
	}
	public void setTr_doc(int tr_doc) {
		this.tr_doc = tr_doc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
}
