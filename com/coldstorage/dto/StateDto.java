package com.coldstorage.dto;

public class StateDto 
{
	private  int  depo_code  ;
	private  int  div_code  ;
	private  int  state_code  ;
	private  String  state_name  ;
	private  int  mkt_year  ;
	private String gststate_code;
	
	
	
	public String toString()
	{
		return state_name;
		
	}

	
	public String getGststate_code() {
		return gststate_code;
	}


	public void setGststate_code(String gststate_code) {
		this.gststate_code = gststate_code;
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

	public int getState_code() {
		return state_code;
	}

	public void setState_code(int state_code) {
		this.state_code = state_code;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public int getMkt_year() {
		return mkt_year;
	}

	public void setMkt_year(int mkt_year) {
		this.mkt_year = mkt_year;
	}

	
	
	
}
