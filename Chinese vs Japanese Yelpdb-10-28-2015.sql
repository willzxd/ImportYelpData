select * from bcategories limit 10;
select * from bneighborhoods limit 10;
select * from business limit 10;
select * from elite limit 10;
select * from friends limit 10;
select * from review limit 10;
select * from reviewvotes limit 10;
select * from tips limit 10;
select * from users limit 10;
select * from uservotes limit 10;

create view JCrestaurants as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Chinese' and B.category='Japanese';

create view Jrestaurants as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Japanese' and B.category='Restaurants'
and A.business_id not in (select * from JCrestaurants);

create view Crestaurants as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Chinese' and B.category='Restaurants'
and A.business_id not in (select * from JCrestaurants);

create view JrestaurantsF as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Japanese' and B.category='Restaurants';;

create view CrestaurantsF as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Chinese' and B.category='Restaurants';

/*test average score difference*/
select sum(review_count*stars)/sum(review_count) as avg_rate
from business
where business_id in (select * from Jrestaurants); /*3.7846390446897051*/

select sum(review_count*stars)/sum(review_count) as avg_rate
from business
where business_id in (select * from Crestaurants); /*3.4835038245323012*/

/*find users rated Japanese restaurants not Chinese restaurants*/
create view Jusers as
select distinct user_id from review where business_id in (select * from Jrestaurants);

/*find users rated Chinese restaurants not Japanese restaurants*/
create view Cusers as
select distinct user_id from review where business_id in (select * from Crestaurants);

/*1. Review*/
select sum(review_count*average_stars)/sum(review_count) from users where
user_id in (select * from Jusers); /*3.7386057142807134*/
select sum(review_count*average_stars)/sum(review_count) from users where
user_id in (select * from Cusers); /*3.7120427239705256*/  
/*<-------------Similar-------------->*/


select sum(review_count) from users where
user_id in (select * from Jusers); /*2285292*/
select sum(review_count) from users where
user_id in (select * from Cusers); /*2284151*/ 
/*<-------------Similar-------------->*/

/*2. Friends*/
select avg(friend_count) from
(select user_id, count(*) as friend_count from friends where user_id in (select * from Jusers) group by user_id) as temp; 
/*30.0041927776024525*/

select avg(friend_count) from
(select user_id, count(*) as friend_count from friends where user_id in (select * from Cusers) group by user_id) as temp; 
/*29.8860038610038610*/
/*<-------------Similar---------------->*/

select stddev(friend_count) from
(select user_id, count(*) as friend_count from friends where user_id in (select * from Jusers) group by user_id) as temp; 
/*98.4843738572364808*/

select stddev(friend_count) from
(select user_id, count(*) as friend_count from friends where user_id in (select * from Cusers) group by user_id) as temp; 
/*95.2483709650243161*/
/*<-------------Distribution Similar---------------->*/

/*3. Elite*/
select count(*) from elite where user_id in (select * from Jusers); /*17843*/
select count(*) from elite where user_id in (select * from Cusers); /*17505*/
/*<-------------Similar---------------->*/

select avg(year) from elite where user_id in (select * from Jusers); /*2012.1127052625679538*/
select avg(year) from elite where user_id in (select * from Cusers); /*2012.1932019423021994*/
/*<-------------Similar---------------->*/

/*4.review cool/useful/funny*/
select sum(funny) as funny_sum, sum(userful) as useful_sum, sum(cool) as cool_sum from reviewvotes where review_id in (select review_id from review where business_id in (select * from Jrestaurants)) 
/*23177;55086;32282*/

select sum(funny) as funny_sum, sum(userful) as useful_sum, sum(cool) as cool_sum from reviewvotes where review_id in (select review_id from review where business_id in (select * from Crestaurants)) 
/*20856;48558;25866*/
/*<---------------Have some difference------------>*/

/*5.user cool/useful/funny*/
select avg(funny) as funny_avg, avg(userful) as useful_avg, avg(cool) as cool_avg from uservotes where user_id in (select * from Jusers);
/*78.9716816329032456;149.7882251984431028;92.9573079162708020*/

select avg(funny) as funny_avg, avg(userful) as useful_avg, avg(cool) as cool_avg from uservotes where user_id in (select * from Cusers);
/*78.7880971025841817;149.9432732967893500;91.1697102584181676*/
/*<-------------Similar---------------->*/

/*6.review date*/

select count(*) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)<=B.year and A.business_id in (select * from Jrestaurants);
/*37323*/
select count(*) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)>B.year and A.business_id in (select * from Jrestaurants);
/*10595*/

select count(*) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)<=B.year and A.business_id in (select * from Crestaurants);
/*38548*/
select count(*) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)>B.year and A.business_id in (select * from Crestaurants);
/*10388*/
/*<--------------------Slightly different, Chinese restaurant raters rate more before joining elite--------------->*/

select avg(stars) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)<=B.year and A.business_id in (select * from Jrestaurants);
/*3.7234681027784476*/
select avg(stars) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)>B.year and A.business_id in (select * from Jrestaurants);
/*3.7884851344974044*/

select avg(stars) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)<=B.year and A.business_id in (select * from Crestaurants);
/*3.4539794541869877*/
select avg(stars) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)>B.year and A.business_id in (select * from Crestaurants);
/*3.4765113592606854*/
/*<--------------------Matters, especially when people gives higher ratings after they join elite--------------->*/

/*7.location*/
select state, count(*) as count from business where business_id in (select * from Jrestaurants) group by state order by count;
"MLN";2
"SC";2
"BW";5
"IL";10
"ON";14
"EDH";16
"WI";22
"PA";25
"NC";55
"QC";90
"AZ";214
"NV";243
select state, count(*) as count from business where business_id in (select * from Crestaurants) group by state order by count;
"MLN";3
"SC";6
"BW";22
"IL";23
"ON";29
"WI";69
"EDH";70
"PA";89
"NC";125
"QC";132
"NV";357
"AZ";523

/*<-----------------NC,QC,NV,AZ------------------*/
select state, sum(review_count*stars)/sum(review_count) as avg_stars 
from business
where business_id in (select * from Jrestaurants) 
group by state order by avg_stars;
"SC";2.9107142857142857
"BW";2.9733333333333333
"NC";3.5482929020664870
"ON";3.5837104072398190
"WI";3.6074270557029178
"IL";3.6231707317073171
"PA";3.6506024096385542
"AZ";3.6607481525080112
"EDH";3.6833333333333333
"QC";3.7320926966292135
"NV";3.8774840474020055
"MLN";4.5000000000000000


select state, sum(review_count*stars)/sum(review_count) as avg_stars 
from business
where business_id in (select * from Crestaurants) 
group by state order by avg_stars;

"WI";3.3855540897097625
"NV";3.3862944603878598
"NC";3.4355981659024594
"SC";3.5416666666666667
"AZ";3.5581881369464143
"PA";3.5838837516512550
"ON";3.5921568627450980
"QC";3.6021566401816118
"EDH";3.6198156682027650
"BW";3.7100000000000000
"IL";3.8323057953144266
"MLN";4.5384615384615385


select state, sum(review_count*stars)/sum(review_count) as avg_stars 
from business
where state in 
(select distinct state from business
where business_id in (select * from Crestaurants))
group by state order by avg_stars;
"ON";3.6236281471917366
"SC";3.6651508887970236
"NC";3.6986096882774770
"IL";3.7006991412721331
"NV";3.7072482016299555
"BW";3.7419428571428571
"WI";3.7477875178256858
"PA";3.7631440842989131
"AZ";3.7643138357263666
"QC";3.8170847917315692
"EDH";3.8528504047050603
"MLN";3.8870449678800857

/*8 users parameters*/
select avg(fans) from users where user_id in (select * from Jusers); /*4.0042600140978884*/
select avg(fans) from users where user_id in (select * from Cusers); /*3.8098042286609240*/

select stddev(fans) from users where user_id in (select * from Jusers); /*22.1924683581712201*/
select stddev(fans) from users where user_id in (select * from Cusers); /*18.9334486322625839*/

select max(fans) from users where user_id in (select * from Jusers); /*1298*/
select max(fans) from users where user_id in (select * from Cusers); /*803*/

/*<------------------Slightly different, Japanese Restaurant raters have more fans------------>*/

select avg(yelping_since_year) from users where user_id in (select * from Jusers); /*2010.7834441754267676*/
select avg(yelping_since_year) from users where user_id in (select * from Cusers); /*2010.8778073610023493*/
/*<-------------Similar---------------->*/

/*tips*/
select count(*) from tips where business_id in (select * from Jrestaurants); /*17368*/
select count(*) from tips where business_id in (select * from Crestaurants); /*14777*/
/*<-------------Chinese restaurant raters give less tips---------------->*/


/*test*/
select distinct business_id from business where business_id in (select * from Crestaurants);

select count(*) from review where business_id in (select * from JrestaurantsF);
select count(*) from review where business_id in (select * from CrestaurantsF);

select * from CrestaurantsF;

select sum(review_count) from business; /*1729825*/
select count(*) from review; /*1569252*/
select sum(review_count) from users; /*11813654*/

select avg(stars) from review where business_id in (select * from Jrestaurants); /*3.8032754381790784*/
select avg(stars) from review where business_id in (select * from Crestaurants); /*3.5054898306458148*/

select count(*) from Jusers; /*32629*/
select count(*) from Cusers; /*31925*/


