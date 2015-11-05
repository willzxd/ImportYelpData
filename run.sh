#!/bin/bash

rm -r target/

export BUSINESS_PATH="/Users/will/Downloads/yelp_dataset_challenge_academic_dataset/yelp_academic_dataset_business.json"

export USERS_PATH="/Users/will/Downloads/yelp_dataset_challenge_academic_dataset/yelp_academic_dataset_user.json"

export TIPS_PATH="/Users/will/Downloads/yelp_dataset_challenge_academic_dataset/yelp_academic_dataset_tip.json"

export REVIEW_PATH="/Users/will/Downloads/yelp_dataset_challenge_academic_dataset/yelp_academic_dataset_review.json"

export DB_NAME="yelpdb"

export DB_USERNAME="yelpdbuser"

export DB_PASSWORD="yelp"

export DB_CONNECTION="jdbc:postgresql://localhost/"

#update mode only imports the missing part of the last version
export RUNMODE="update"


mvn install

echo "BUSINESS_FILE_PATH: "$BUSINESSPATH
echo "USERS_FILE_PATH: "$USERS_PATH
echo "TIPS_FILE_PATH: "$TIPS_PATH
echo "REVIEW_FILE_PATH: "$REVIEW_PATH
echo "DB_NAME: "$DB_NAME
echo "DB_USERNAME: "$DB_USERNAME
echo "DB_PASSWORD: "$DB_PASSWORD
echo "DB_CONNECTION: "$DB_CONNECTION

echo "====================================================="
echo "Begin to import data to psql..."
echo "====================================================="
java -cp target/yelpData-0.0.1-SNAPSHOT-jar-with-dependencies.jar edu.duke.yelpData.util.ImportData
