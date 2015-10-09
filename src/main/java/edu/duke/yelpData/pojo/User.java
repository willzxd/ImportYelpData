package edu.duke.yelpData.pojo;
/**
 * Pojo class for users, friends, uservotes
 * @author will
 *
 */
public class User {
    private String user_id;
    private String name;
    private int review_count;
    private float average_stars;
    private votes votes;
    //list of friends user_id
    private String[] friends;
    //list of years of elite
    private int[] elite;
    private String yelping_since;
    private compliments compliments;
    private int fans;
    private String type;
    
    public User() {
    	
    }
    
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name.replace('\'', '/');
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getReview_count() {
		return review_count;
	}
	public void setReview_count(int review_count) {
		this.review_count = review_count;
	}
	public float getAverage_stars() {
		return average_stars;
	}
	public void setAverage_stars(float average_stars) {
		this.average_stars = average_stars;
	}
	public votes getVotes() {
		return votes;
	}
	public void setVotes(votes votes) {
		this.votes = votes;
	}
	public String[] getFriends() {
		return friends;
	}
	public void setFriends(String[] friends) {
		this.friends = friends;
	}
	public int[] getElite() {
		return elite;
	}
	public void setElite(int[] elite) {
		this.elite = elite;
	}
	public String getYelping_since() {
		return yelping_since;
	}
	public void setYelping_since(String yelping_since) {
		this.yelping_since = yelping_since;
	}
	public compliments getCompliments() {
		return compliments;
	}
	public void setCompliments(compliments compliments) {
		this.compliments = compliments;
	}
	public int getFans() {
		return fans;
	}
	public void setFans(int fans) {
		this.fans = fans;
	}
	public int getYelpYear() {
		String s = this.getYelping_since().substring(0, 4);
		return Integer.valueOf(s);
	}
	public int getYelpMonth() {
		String s = this.getYelping_since().substring(5, 7);
		return Integer.valueOf(s);
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
    public static class compliments {
    	private int profile;
    	private int cute;
    	private int funny;
    	private int plain;
    	private int writer;
    	private int note;
    	private int photos;
    	private int hot;
    	private int cool;
    	private int more;
    	private int list;
    	
    	public compliments() {
    		
    	}

		public int getProfile() {
			return profile;
		}

		public void setProfile(int profile) {
			this.profile = profile;
		}

		public int getCute() {
			return cute;
		}

		public void setCute(int cute) {
			this.cute = cute;
		}

		public int getFunny() {
			return funny;
		}

		public void setFunny(int funny) {
			this.funny = funny;
		}

		public int getPlain() {
			return plain;
		}

		public void setPlain(int plain) {
			this.plain = plain;
		}

		public int getWriter() {
			return writer;
		}

		public void setWriter(int writer) {
			this.writer = writer;
		}

		public int getNote() {
			return note;
		}

		public void setNote(int note) {
			this.note = note;
		}

		public int getPhotos() {
			return photos;
		}

		public void setPhotos(int photos) {
			this.photos = photos;
		}

		public int getHot() {
			return hot;
		}

		public void setHot(int hot) {
			this.hot = hot;
		}

		public int getCool() {
			return cool;
		}

		public void setCool(int cool) {
			this.cool = cool;
		}

		public int getMore() {
			return more;
		}

		public void setMore(int more) {
			this.more = more;
		}

		public int getList() {
			return list;
		}

		public void setList(int list) {
			this.list = list;
		}
    }
}
