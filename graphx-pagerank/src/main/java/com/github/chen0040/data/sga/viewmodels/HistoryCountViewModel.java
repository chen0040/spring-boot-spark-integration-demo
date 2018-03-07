package com.github.chen0040.data.sga.viewmodels;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class HistoryCountViewModel{
	
	private Long count;
	private String time="";
	
	public HistoryCountViewModel(Long count, java.util.Date time){
		this.count = count;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.time = f.format(time);
	}
	
	public HistoryCountViewModel(){
		
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
