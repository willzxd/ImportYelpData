package edu.duke.yelpData.pojo;

public class Review {
	private String type;
	private String review_id;
	private String business_id;
	private String user_id;
	private String text;
	private String date;
	private int stars;
	private votes votes;
	
	public Review() {
		
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReview_id() {
		return review_id;
	}

	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}

	public String getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text.replace('\'', '/');
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public votes getVotes() {
		return votes;
	}

	public void setVotes(votes votes) {
		this.votes = votes;
	}

	public static class votes {
    	private int funny;
    	private int useful;
    	private int cool;
    	
    	public votes() {
    		
    	}

		public int getFunny() {
			return funny;
		}

		public void setFunny(int funny) {
			this.funny = funny;
		}

		public int getUseful() {
			return useful;
		}

		public void setUseful(int useful) {
			this.useful = useful;
		}

		public int getCool() {
			return cool;
		}

		public void setCool(int cool) {
			this.cool = cool;
		}
    	
    }
}
