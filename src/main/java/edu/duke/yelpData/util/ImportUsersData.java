package edu.duke.yelpData.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import edu.duke.yelpData.pojo.User;
import edu.duke.yelpData.pojo.User.votes;

/**
 * This Class convert Yelp User data to three tables: users, friends, uservotes, 
 * Temporarily ignore compliments
 * @author will
 *
 */
public class ImportUsersData {
	
	
	@SuppressWarnings("finally")
	public boolean importUsers() {
		BufferedReader reader = null;
		try{
			FileInputStream fileInputStream = new FileInputStream("/Users/will/Documents/workspace/ImportYelpData/src/main/resources/yelp_academic_dataset_user.json");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
			while((tempString = reader.readLine()) != null){
				ImportUserThread t = new ImportUserThread(tempString);
				fixedThreadPool.execute(t);
			}
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 
			finally{
				if(reader != null){
					try {
						reader.close();
					} catch (final IOException e) {
						e.printStackTrace();
						return false;
					}
				}
				return true;
			}
	}
}

class ImportUserThread implements Runnable {
	private SQLWritter sqlwritter;
	private String userStr;
	
	public ImportUserThread(String userStr) {
		sqlwritter = new SQLWritter();
		this.userStr = userStr;
	}
	public void run() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			User user = mapper.readValue(userStr, User.class);
			sqlwritter.insertUserTable(user.getUser_id(), user.getName(), user.getReview_count(), user.getAverage_stars(), user.getYelpYear(), user.getYelpMonth(), user.getFans(), user.getType());
			
			Class cls = user.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				Object value = fields[i].get(user);
				if (fields[i].getName().equals("votes")) {
	//					System.out.println("The field:" + fields[i].getName() );
	//					System.out.println("The value:" + ((votes) value).getFunny());
	//					System.out.println("The value:" + ((votes) value).getUseful());
	//					System.out.println("The value:" + ((votes) value).getCool());
						sqlwritter.insertUserVotesTable(user.getUser_id(), ((votes) value).getFunny(), ((votes) value).getUseful(), ((votes) value).getCool());
				}
				if (fields[i].getName().equals("friends")) {
	//				System.out.println("i:" + i + "The field:" + fields[i].getName() );
					for (String s: (String[]) value) {
	//					System.out.println("The value: " + s);
						s.replace('\'', '/');
						sqlwritter.insertFriendsTable(user.getUser_id(), s);
					}
				}
				if (fields[i].getName().equals("elite")) {
					for (Integer year: (int[]) value) {
						sqlwritter.insertEliteTable(user.getUser_id(), year);
					}
				}
	//			System.out.println("i:" + i + "The field:" + fields[i].getName() );
	//			System.out.println("The value:" + value);
			}
			sqlwritter.closeConnection();		
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
		finally{
			sqlwritter.closeConnection();		
		}
		
	}
}
