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

import edu.duke.yelpData.pojo.Business;


public class ImportBusinessData {
private SQLWritter sqlwritter;
	
	public ImportBusinessData() {
		sqlwritter = new SQLWritter();
	}
	@SuppressWarnings("finally")
	public boolean importBusiness() {
		BufferedReader reader = null;
		try{
			FileInputStream fileInputStream = new FileInputStream(System.getenv("BUSINESS_PATH"));
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
			while((tempString = reader.readLine()) != null){
				ImportBusinessThread t = new ImportBusinessThread(tempString);
				fixedThreadPool.execute(t);
			}
				reader.close();
				sqlwritter.closeConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 
			finally{
				if(reader != null){
					try {
						reader.close();
						sqlwritter.closeConnection();
					} catch (final IOException e) {
						e.printStackTrace();
						return false;
					}
				}
				return true;
			}
	}
}

class ImportBusinessThread implements Runnable {
	private SQLWritter sqlwritter;
	private String str;
	
	public ImportBusinessThread(String str) {
		sqlwritter = new SQLWritter();
		this.str = str;
	}
	public void run() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Business business = mapper.readValue(str, Business.class);
			sqlwritter.insertBusinessTable(business.getBusiness_id(), business.getName(), business.getFull_address(), business.getCity(), business.getState(), business.getLatitude(), business.getLongitude(), business.getStars(), business.getReview_count(), business.getOpen());
			
			Class cls = business.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				Object value = fields[i].get(business);
				
				if (fields[i].getName().equals("neighborhoods")) {
//					System.out.println("i:" + i + "The field:" + fields[i].getName() );
					for (String s: (String[]) value) {
						
//						System.out.println("The value: " + s);
						sqlwritter.insertBusinessNeighborhoodsTable(business.getBusiness_id(), s.replace('\'', '/'));
					}
				}
				if (fields[i].getName().equals("categories")) {
					for (String s: (String[]) value) {
						sqlwritter.insertBusinessCategoriesTable(business.getBusiness_id(), s.replace('\'', '/'));
					}
				}
//				System.out.println("i:" + i + "The field:" + fields[i].getName() );
//				System.out.println("The value:" + value);
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
