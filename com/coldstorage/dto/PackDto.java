package com.coldstorage.dto;

public class PackDto 
{
	
	private  int  pack_code  ;
	private  String  pack_name  ;
	private  String  pack_name_hindi  ;

	
	public String toString()
	{
		return pack_name_hindi;
		
	}


	public int getPack_code() {
		return pack_code;
	}


	public void setPack_code(int pack_code) {
		this.pack_code = pack_code;
	}


	public String getPack_name() {
		return pack_name;
	}


	public void setPack_name(String pack_name) {
		this.pack_name = pack_name;
	}


	public String getPack_name_hindi() {
		return pack_name_hindi;
	}


	public void setPack_name_hindi(String pack_name_hindi) {
		this.pack_name_hindi = pack_name_hindi;
	}

	
	
	
	
}
