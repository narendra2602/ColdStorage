package com.coldstorage.dto;

public class RegionDto
{
		private  int  depo_code  ;
		private  int  div_code  ;
		private int area_code;
		private int reg_code;
		private String reg_name;
		private  int  mkt_year  ;
		
		public String toString()
		{
			return reg_name;
			
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

		public int getArea_code() {
			return area_code;
		}

		public void setArea_code(int area_code) {
			this.area_code = area_code;
		}

		public int getReg_code() {
			return reg_code;
		}

		public void setReg_code(int reg_code) {
			this.reg_code = reg_code;
		}

		public String getReg_name() {
			return reg_name;
		}

		public void setReg_name(String reg_name) {
			this.reg_name = reg_name;
		}

		public int getMkt_year() {
			return mkt_year;
		}

		public void setMkt_year(int mkt_year) {
			this.mkt_year = mkt_year;
		}

		
}
