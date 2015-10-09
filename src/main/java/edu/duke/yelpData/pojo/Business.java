package edu.duke.yelpData.pojo;
/**
 * Pojo Class for Business Data, ignoring open hours and attributes;
 * @author will
 *
 */
public class Business {
	private String business_id;
	private String name;
	private String[] neighborhoods;
	private String full_address;
	private String city;
	private String state;
	private float latitude;
	private float longitude;
	private float stars;
	private int review_count;
	private String[] categories;
	private String open;
	
	public Business() {
		
	}

	public String getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.replace('\'', '/');
	}

	public String[] getNeighborhoods() {
		return neighborhoods;
	}

	public void setNeighborhoods(String[] neighborhoods) {
		this.neighborhoods = neighborhoods;
	}

	public String getFull_address() {
		return full_address;
	}

	public void setFull_address(String full_address) {
		this.full_address = full_address.replace('\'', '/');
	}

	public String getCity() {
		return city.replace('\'', '/');
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getStars() {
		return stars;
	}

	public void setStars(float stars) {
		this.stars = stars;
	}

	public int getReview_count() {
		return review_count;
	}

	public void setReview_count(int review_count) {
		this.review_count = review_count;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}
	
}
//'type': 'business',
//'business_id': (encrypted business id),
//'name': (business name),
//'neighborhoods': [(hood names)],
//'full_address': (localized address),
//'city': (city),
//'state': (state),
//'latitude': latitude,
//'longitude': longitude,
//'stars': (star rating, rounded to half-stars),
//'review_count': review count,
//'categories': [(localized category names)]
//'open': True / False (corresponds to closed, not business hours),
//'hours': {
//  (day_of_week): {
//      'open': (HH:MM),
//      'close': (HH:MM)
//  },
//  ...
//},
//'attributes': {
//  (attribute_name): (attribute_value),
//  ...
//},