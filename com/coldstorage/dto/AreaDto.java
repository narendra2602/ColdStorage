package com.coldstorage.dto;

public class AreaDto
{
		private  int  depo_code  ;
		private  int  div_code  ;
		private  int  state_code  ;
		private int area_cd;
		private String area_name;
		private  int  mkt_year  ;
		
		public String toString()
		{
			return area_name;
			
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

		public int getArea_cd() {
			return area_cd;
		}

		public void setArea_cd(int area_cd) {
			this.area_cd = area_cd;
		}

		public String getArea_name() {
			return area_name;
		}

		public void setArea_name(String area_name) {
			this.area_name = area_name;
		}

		public int getMkt_year() {
			return mkt_year;
		}

		public void setMkt_year(int mkt_year) {
			this.mkt_year = mkt_year;
		}
		

		
}
