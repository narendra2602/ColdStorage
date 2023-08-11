package com.coldstorage.dto;

public class GroupDto 
{
	private  int  depo_code  ;
	private  int  div_code  ;
	private  int  gp_code  ;
	private  String  gp_name  ;
	private String gp_type; 
	private  String  gp_name_hindi  ;
	
	private  int sub_code;

	
	
	public String toString()
	{
		return gp_name_hindi;
		
	}

	
	
	public String getGp_type() {
		return gp_type;
	}



	public void setGp_type(String gp_type) {
		this.gp_type = gp_type;
	}



	public int getSub_code() {
		return sub_code;
	}



	public void setSub_code(int sub_code) {
		this.sub_code = sub_code;
	}



	public int getDepo_code() {
		return depo_code;
	}

	public void setDepo_code(int depo_code) {
		this.depo_code = depo_code;
	}

	public int getDiv_code() {
		return div_code;
	}

	public void setDiv_code(int div_code) {
		this.div_code = div_code;
	}

	public int getGp_code() {
		return gp_code;
	}

	public void setGp_code(int gp_code) {
		this.gp_code = gp_code;
	}

	public String getGp_name() {
		return gp_name;
	}

	public void setGp_name(String gp_name) {
		this.gp_name = gp_name;
	}



	public String getGp_name_hindi() {
		return gp_name_hindi;
	}



	public void setGp_name_hindi(String gp_name_hindi) {
		this.gp_name_hindi = gp_name_hindi;
	}


	
	
}
