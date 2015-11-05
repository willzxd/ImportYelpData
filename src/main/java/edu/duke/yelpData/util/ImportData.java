package edu.duke.yelpData.util;


public class ImportData {

	public static void main(String[] args) {
		//1. Create Table
		SQLWritter sqlwritter = new SQLWritter();
		
		//if update
		if (System.getenv("RUNMODE").equals("update")) {
			//update attribute of business table
			System.out.println("Begin Update...");
			sqlwritter.createTables();
			ImportBattributeData importBattribute = new ImportBattributeData();
			if(importBattribute.importBattribute()) {
				System.out.println("Successfully imported Business Attributes Data");
			}
			return;
		}
		
		System.out.println("Working on fully importing data...");
		sqlwritter.dropTables();
		sqlwritter.createTables();

		ImportUsersData importUsers = new ImportUsersData();
		if(importUsers.importUsers()) {
			System.out.println("Successfully imported Users Data");
		}
		
		ImportBusinessData importBusiness = new ImportBusinessData();
		if(importBusiness.importBusiness()) {
			System.out.println("Successfully imported Business Data");
		}
		
		ImportBattributeData importBattribute = new ImportBattributeData();
		if(importBattribute.importBattribute()) {
			System.out.println("Successfully imported Business Attributes Data");
		}
		
		ImportReviewData importReview = new ImportReviewData();
		if (importReview.importReview()) {
			System.out.println("Successfully import Review Data");
		}
		
		ImportTipsData importTips = new ImportTipsData();
		if (importTips.importTips()) {
			System.out.println("Successfully imported Tips Data");
		}
		
	}

}
