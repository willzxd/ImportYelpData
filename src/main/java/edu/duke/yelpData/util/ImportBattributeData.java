package edu.duke.yelpData.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import edu.duke.yelpData.pojo.Business;

public class ImportBattributeData {
	
	public ImportBattributeData() {
	}
	@SuppressWarnings("finally")
	public boolean importBattribute() {
		BufferedReader reader = null;
		try{
			FileInputStream fileInputStream = new FileInputStream(System.getenv("BUSINESS_PATH"));
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
			while((tempString = reader.readLine()) != null){
				ImportBattributeThread t = new ImportBattributeThread(tempString);
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

class ImportBattributeThread implements Runnable {
	private SQLWritter sqlwritter;
	private String str;
	
	public ImportBattributeThread(String str) {
		sqlwritter = new SQLWritter();
		this.str = str;
	}
	public void run() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Business business = mapper.readValue(str, Business.class);
			//sqlwritter.insertBusinessTable(business.getBusiness_id(), business.getName(), business.getFull_address(), business.getCity(), business.getState(), business.getLatitude(), business.getLongitude(), business.getStars(), business.getReview_count(), business.getOpen());
			
			Class cls = business.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				Object value = fields[i].get(business);
				
				if (fields[i].getName().equals("attributes")) {
					//deal with attributes
					//System.out.println(value.toString());
					for (String s: ((LinkedHashMap<String, Object>) value).keySet()) {
						if (s == "Good For") {
							for (String sub: ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).keySet()) {
								sqlwritter.insertBusinessAttributesTable(business.getBusiness_id(), s + " " + sub, ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).get(sub).toString());
								//System.out.println(business.getBusiness_id() + " : " + s+sub + " : " + ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).get(sub).toString());
							}
						}
						else if (s == "Parking") {
							for (String sub: ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).keySet()) {
								sqlwritter.insertBusinessAttributesTable(business.getBusiness_id(), s + " " + sub, ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).get(sub).toString());
								//System.out.println(business.getBusiness_id() + " : " + s +sub+ " : " + ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).get(sub).toString());
							}
						}
						else if (s == "Music") {
							for (String sub: ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).keySet()) {
								sqlwritter.insertBusinessAttributesTable(business.getBusiness_id(), s + " " + sub, ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).get(sub).toString());
								//System.out.println(business.getBusiness_id() + " : " + s +sub+ " : " + ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).get(sub).toString());
							}
						}
						else if (s == "Ambience") {
							for (String sub: ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).keySet()) {
								sqlwritter.insertBusinessAttributesTable(business.getBusiness_id(), s + " " + sub, ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).get(sub).toString());
								//System.out.println(business.getBusiness_id() + " : " + s +sub+ " : " + ((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) value).get(s)).get(sub).toString());
							}
						}
						else {
							sqlwritter.insertBusinessAttributesTable(business.getBusiness_id(), s, ((LinkedHashMap<String, Object>) value).get(s).toString());
							//System.out.println(business.getBusiness_id() + " : " + s + " : " + ((LinkedHashMap<String, Object>) value).get(s).toString());
						}
					}
				}
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

