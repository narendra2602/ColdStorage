package com.coldstorage.dto;

public class CategoryDto 
{
	private  int  category_code  ;
	private  String  category_name  ;
	private  String  category_name_hindi  ;
	
	
	
	public String toString()
	{
		return category_name_hindi;
		
	}

	public int getCategory_code() {
		return category_code;
	}

	public void setCategory_code(int category_code) {
		this.category_code = category_code;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_name_hindi() {
		return category_name_hindi;
	}

	public void setCategory_name_hindi(String category_name_hindi) {
		this.category_name_hindi = category_name_hindi;
	}

	
}
