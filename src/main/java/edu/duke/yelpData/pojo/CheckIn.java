package edu.duke.yelpData.pojo;

public class CheckIn {

	private String type;
	private String business_id;
	private checkinInfo checkinInfo;
	
	public CheckIn() {
		
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	public checkinInfo getCheckinInfo() {
		return checkinInfo;
	}

	public void setCheckinInfo(checkinInfo checkinInfo) {
		this.checkinInfo = checkinInfo;
	}

	private static class checkinInfo {
		
	}
}
