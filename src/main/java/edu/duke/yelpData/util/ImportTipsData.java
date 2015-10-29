package edu.duke.yelpData.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import edu.duke.yelpData.pojo.Tips;

public class ImportTipsData {
private SQLWritter sqlwritter;
	
	public ImportTipsData() {
		sqlwritter = new SQLWritter();
	}
	@SuppressWarnings("finally")
	public boolean importTips() {
		BufferedReader reader = null;
		try{
			FileInputStream fileInputStream = new FileInputStream(System.getenv("TIPS_PATH"));
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while((tempString = reader.readLine()) != null){
				//convert String to object
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				Tips tips = mapper.readValue(tempString, Tips.class);
				sqlwritter.insertTipsTable(tips.getBusiness_id(), tips.getUser_id(), tips.getDate(), tips.getLikes(), tips.getText(), tips.getType());
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
