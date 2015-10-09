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

import edu.duke.yelpData.pojo.Review;
import edu.duke.yelpData.pojo.Review.votes;


public class ImportReviewData {
	
	public ImportReviewData() {
		
	}
	@SuppressWarnings("finally")
	public boolean importReview() {
		BufferedReader reader = null;
		try{
			FileInputStream fileInputStream = new FileInputStream("/Users/will/Documents/workspace/ImportYelpData/src/main/resources/yelp_academic_dataset_review.json");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
			while((tempString = reader.readLine()) != null){
				ImportReviewThread t = new ImportReviewThread(tempString);
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

class ImportReviewThread implements Runnable {
	
	private SQLWritter sqlwritter;
	private String review;
	
	public ImportReviewThread(String review) {
		sqlwritter = new SQLWritter();
		this.review = review;
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
		//convert String to object
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Review r = mapper.readValue(review, Review.class);
			sqlwritter.insertReviewTable(r.getReview_id(), r.getBusiness_id(), r.getUser_id(), r.getStars(), r.getDate(), r.getText(), r.getType());
			Class cls = r.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				Object value = fields[i].get(r);
				System.out.println(fields[i].getName());
				if (fields[i].getName().equals("votes")) {
					sqlwritter.insertReviewVotesTable(r.getReview_id(), ((votes) value).getFunny(), ((votes) value).getUseful(), ((votes) value).getCool());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally{
			sqlwritter.closeConnection();
		}
	}
}
