package edu.duke.yelpData.util;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.Statement;
/**
 * This Class write SQL queries to PostgreSQL database
 * @author will
 *
 */
public class SQLWritter {
	Connection conn = null;
	public static String DATABASE = System.getenv("DB_NAME");
	public static String USER = System.getenv("DB_USERNAME");
	public static String PASSWORD = System.getenv("DB_PASSWORD");
	public static String CONNECTION = System.getenv("DB_CONNECTION");

	public SQLWritter() {
		try { 
			Class.forName("org.postgresql.Driver"); 
			String url = "jdbc:postgresql://localhost/" + DATABASE + "?user=" + USER + "&password=" + PASSWORD; 
		try { 
				conn = DriverManager.getConnection(url);
			} 
			catch (SQLException e) { 
				e.printStackTrace(); 
			} 
		} 
		catch (ClassNotFoundException e) { 
			e.printStackTrace(); 
		}
		//System.out.println("Successfully connect to PostgreSQL!");
	}
	
	public boolean closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void createTables() {
		createUserTable();
		createFriendsTable();
		createUserVotesTable();
		createComplimentsTable();
		createEliteTable();
		createBusinessTable();
		createBusinessCategoriesTable();
		createBusinessNeighborhoodsTable();
		createTipsTable();
		createReviewTable();
		createReviewVotesTable();
		createBusinessAttributesTable();
		//createCheckInfoTable();
		System.out.println("All tables have been Created!");
	}
	

	public boolean dropTables() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS reviewvotes, review, tips, bneighborhoods, bcategories, battributes, business, elite, uservotes, friends, users CASCADE");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}

	private void printSQLInformation(SQLException ex) {
		System.out.println("SQLException:" + ex.getMessage());
		System.out.println("SQLState:" + ex.getSQLState());
		System.out.println("VendorError:" + ex.getErrorCode());
		ex.printStackTrace();
	}
	
	private void createCheckInfoTable() {
		// TODO Auto-generated method stub
		
	}	

	private boolean createBusinessAttributesTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS battributes" 
					+ "(business_id varchar(255) REFERENCES business(business_id), "
					+ "attribute varchar(255), "
					+ "value varchar(255))");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertBusinessAttributesTable(String bid, String attr, String value) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO battributes(business_id, attribute, value) VALUES(" 
					+ "'" + bid +"', "
					+ "'" + attr.replace('\'', '/') +"', "
					+ "'" + value.replace('\'', '/') +"')");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}

	private void createOpenHoursTable() {
		// TODO Auto-generated method stub
		
	}

	private boolean createUserTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" 
					+ "(user_id varchar(255) PRIMARY KEY, "
					+ "name varchar(255), "
					+ "review_count int, "
					+ "average_stars decimal(4,3), "
					+ "yelping_since_year int, "
					+ "yelping_since_month int, "
					+ "fans int, "
					+ "type varchar(255))");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertUserTable(String user_id, String name, int review_count, float average_stars, int yelping_since_year, int yelping_since_month, int fans, String type) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO Users(user_id, name, review_count, average_stars, yelping_since_year, yelping_since_month, fans, type) VALUES(" 
					+ "'" + user_id +"', "
					+ "'" + name +"', "
					+ review_count +", "
					+ average_stars +", "
					+ yelping_since_year +", "
					+ yelping_since_month +", "
					+ fans +", "
					+ "'" + type +"')");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	private boolean createUserVotesTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS uservotes " 
					+ "(user_id varchar(255) REFERENCES users(user_id), "
					+ "funny int, "
					+ "useful int,"
					+ "cool int)");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertUserVotesTable(String user_id, int funny, int useful, int cool) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO uservotes VALUES (" 
					+ "'" + user_id +"',"
					+ funny + ", "
					+ useful + ", "
					+ cool + ")");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}

	private boolean createFriendsTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS friends" 
					+ "(user_id varchar(255) REFERENCES users(user_id), "
					+ "friend_id varchar(255)) ");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertFriendsTable(String user_id, String friend_id) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO friends VALUES (" 
					+ "'" + user_id + "', "
					+ "'" + friend_id + "') ");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	private boolean createEliteTable() {
		// TODO Auto-generated method stub
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS elite ("
					+ "user_id varchar(255) REFERENCES users(user_id), "
					+ "year int)");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertEliteTable(String user_id, int year) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO elite VALUES ("
					+ "'" + user_id + "', "
					+ year + ")");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	private boolean createBusinessTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS business ("
					+ "business_id varchar(255) PRIMARY KEY, "
					+ "name varchar(255), "
					+ "full_address varchar(511), "
					+ "city varchar(255), "
					+ "state varchar(255), "
					+ "latitude decimal(5,2), "
					+ "longitude decimal(5, 2), "
					+ "stars decimal(3,2), "
					+ "review_count int, "
					+ "open varchar(255))");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertBusinessTable(String business_id, String name, 
			String full_address, String city, String state,float latitude, 
			float longitude, float stars, int review_count, String open) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO business VALUES ("
					+ "'" + business_id + "', "
					+ "'" + name + "', "
					+ "'" + full_address + "', "
					+ "'" + city + "', "
					+ "'" + state + "', "
					+ latitude + ", "
					+ longitude + ", "
					+ stars + ", "
					+ review_count + ", "
					+ "'" + open + "')");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}

	private boolean createBusinessNeighborhoodsTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS bneighborhoods ("
					+ "business_id varchar(255) REFERENCES business(business_id), "
					+ "neighbor varchar(255))");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertBusinessNeighborhoodsTable(String business_id, String neighbor) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO bneighborhoods VALUES ("
					+ "'" + business_id + "', "
					+ "'" + neighbor + "')");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	private boolean createBusinessCategoriesTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS bcategories ("
					+ "business_id varchar(255) REFERENCES business(business_id), "
					+ "category varchar(255))");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertBusinessCategoriesTable(String business_id, String category) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO bcategories VALUES ("
					+ "'" + business_id + "', "
					+ "'" + category + "')");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	private boolean createTipsTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS tips ("
					+ "business_id varchar(255) REFERENCES business(business_id), "
					+ "user_id varchar(255) REFERENCES users(user_id), "
					+ "date date, "
					+ "like_count int, "
					+ "text text, "
					+ "type varchar(255))");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertTipsTable(String business_id, String user_id, String date, int like, String text, String type) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO tips VALUES ("
					+ "'" + business_id + "', "
					+ "'" + user_id + "', "
					+ "'" + date + "', "
					+ like + ", "
					+ "'" + text + "', "
					+ "'" + type + "')");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	private boolean createReviewTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS review ("
					+ "review_id varchar(255) PRIMARY KEY, "
					+ "business_id varchar(255) REFERENCES business(business_id), "
					+ "user_id varchar(255) REFERENCES users(user_id), "
					+ "stars int, "
					+ "date date, "
					+ "text text, "
					+ "type varchar(255))");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}

	public boolean insertReviewTable(String review_id, String business_id, String user_id, int stars, String date, String text, String type) {
		Statement statement;
		try {
			statement = conn.createStatement();
//			String s = "INSERT INTO review VALUES ("
//					+ "'" + review_id + "', "
//					+ "'" + business_id + "', "
//					+ "'" + user_id + "', "
//					+ stars + ", "
//					+ "'" + date + "', "
//					+ "'" + text + "', "
//					+ "'" + type + "')";
//			System.out.println(s);
			statement.executeUpdate("INSERT INTO review VALUES ("
					+ "'" + review_id + "', "
					+ "'" + business_id + "', "
					+ "'" + user_id + "', "
					+ stars + ", "
					+ "'" + date + "', "
					+ "'" + text + "', "
					+ "'" + type + "')");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	private boolean createReviewVotesTable() {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS reviewvotes " 
					+ "(review_id varchar(255) REFERENCES review(review_id), "
					+ "funny int, "
					+ "useful int,"
					+ "cool int)");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}
	
	public boolean insertReviewVotesTable(String user_id, int funny, int useful, int cool) {
		Statement statement;
		try {
			statement = conn.createStatement();
			statement.executeUpdate("INSERT INTO reviewvotes VALUES (" 
					+ "'" + user_id +"',"
					+ funny + ", "
					+ useful + ", "
					+ cool + ")");
		}
		catch (SQLException e) {
			printSQLInformation(e);
			return false;
		}
		return true;
	}

	private void createComplimentsTable() {
		// TODO Auto-generated method stub
		
	}
	
}
