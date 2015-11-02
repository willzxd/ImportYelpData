/*overview*/
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

/*same business in the two category*/
create view SChrestaurants as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Chicken Wings' and B.category='Salad';

/*business has categories of restaurants and Salad but do not have Chicken Wings*/
create view Srestaurants as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Salad' and B.category='Restaurants'
and A.business_id not in (select * from SChrestaurants);

/* business has categories of restaurants and Chicken Wings but do not have Salad*/
create view Chrestaurants as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Chicken Wings' and B.category='Restaurants'
and A.business_id not in (select * from SChrestaurants);

/* business has categories of restaurants and Salad */
create view SrestaurantsF as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Salad' and B.category='Restaurants';

/* business has categories of restaurants and Chicken Wings*/
create view ChrestaurantsF as
select distinct A. business_id from
bcategories as A inner join bcategories as B
on A.business_id=B.business_id
where A.category='Chicken Wings' and B.category='Restaurants';

/*test average score difference*/
select sum(review_count*stars)/sum(review_count) as avg_rate
from business
where business_id in (select * from Srestaurants); /*3.8560454754954678*/

select sum(review_count*stars)/sum(review_count) as avg_rate
from business
where business_id in (select * from Chrestaurants); /*3.3440614158116188*/

/*find users rated Salad restaurants not Chicken Wings restaurants*/
create view Susers as
select distinct user_id from review where business_id in (select * from Srestaurants);

/*find users rated Chicken Wings restaurants not Salad restaurants*/
create view Chusers as
select distinct user_id from review where business_id in (select * from Chrestaurants);

/*1. Review*/
select sum(review_count*average_stars)/sum(review_count) from users where
user_id in (select * from Susers); /*3.7475738297712172*/
select sum(review_count*average_stars)/sum(review_count) from users where
user_id in (select * from Chusers); /*3.7129081188662412*/  
/*<-------------Lower-------------->*/


select sum(review_count) from users where
user_id in (select * from Susers); /*757181*/
select sum(review_count) from users where
user_id in (select * from Chusers); /*728550*/ 
/*<-------------Lower-------------->*/

/*2. Friends*/
select avg(friend_count) from
(select user_id, count(*) as friend_count from friends where user_id in (select * from Susers) group by user_id) as temp; 
/*36.9732880755608028*/

select avg(friend_count) from
(select user_id, count(*) as friend_count from friends where user_id in (select * from Chusers) group by user_id) as temp; 
/*36.8122815017479860*/
/*<-------------Similar---------------->*/

select stddev(friend_count) from
(select user_id, count(*) as friend_count from friends where user_id in (select * from Susers) group by user_id) as temp; 
/*124.716964384108*/

select stddev(friend_count) from
(select user_id, count(*) as friend_count from friends where user_id in (select * from Chusers) group by user_id) as temp; 
/*119.748825739486*/
/*<-------------Distribution Similar---------------->*/

/*3. Elite*/
select count(*) from elite where user_id in (select * from Susers); /*6271*/
select count(*) from elite where user_id in (select * from Chusers); /*5680*/
/*<-------------More user, but much fewer elites---------------->*/

select avg(year) from elite where user_id in (select * from Susers); /*2012.4126933503428480*/
select avg(year) from elite where user_id in (select * from Chusers); /*2012.4269366197183099*/
/*<-------------Similar---------------->*/
/* not very useful */

/*4.review cool/useful/funny*/
select sum(funny) as funny_sum, sum(userful) as useful_sum, sum(cool) as cool_sum from reviewvotes where review_id in (select review_id from review where business_id in (select * from Srestaurants)) 
/*4850;11732;6583*/
select avg(funny) as funny_avg, avg(useful) as useful_avg, avg(cool) as cool_avg 
from reviewvotes 
where review_id in 
(select review_id from review where business_id in (select * from Srestaurants))
/*do it avg*/
select sum(funny) as funny_sum, sum(userful) as useful_sum, sum(cool) as cool_sum from reviewvotes where review_id in (select review_id from review where business_id in (select * from Chrestaurants)) 
/*5456;10956;5706*/
/*<---------------ambiguous------------>*/

/*DIFFERENCE ONEEEEEEEEEEEEEEEEEEEEEEEEEEEE*/
/*5.user cool/useful/funny*/
select avg(funny) as funny_avg, avg(userful) as useful_avg, avg(cool) as cool_avg from uservotes where user_id in (select * from Susers);
/*89.7165112227118991;180.3462129752997848;108.8408322230193707*/

select avg(funny) as funny_avg, avg(userful) as useful_avg, avg(cool) as cool_avg from uservotes where user_id in (select * from Chusers);
/*83.6970736629667003;162.8814328960645812;98.0995963673057518*/
/*<-------------much lower cool/useful/funny---------------->*/

/*DIFFERENCE TWOOOOOOOOOOOOOOOOOOOOOOOOO*/
/*6.review date*/
create view elite_yearss as select A.user_id, count(year) as yearsum from elite as A, Susers as B where A.user_id = B.user_id group by A.user_id;
	select avg(yearsum) from elite_yearss;
/*3.52*/
create view elite_yearsc as select A.user_id, count(year) as yearsum from elite as A, Chusers as B where A.user_id = B.user_id group by A.user_id;
		select avg(yearsum) from elite_yearsc;
/*3.50*/



select count(*) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)<=B.year and A.business_id in (select * from Srestaurants);
/*8671*/
select count(*) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)>B.year and A.business_id in (select * from Srestaurants);
/*2744*/
/*2744/8671=0.3165*/

select count(*) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)<=B.year and A.business_id in (select * from Chrestaurants);
/*8571*/
select count(*) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)>B.year and A.business_id in (select * from Chrestaurants);
/*2316*/
/*2316/8571=0.2702*/
/*<--------------------Slightly different, Chicken Wings restaurant raters rate more before joining elite--------------->*/

select avg(stars) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)<=B.year and A.business_id in (select * from Srestaurants);
/*3.7751124437781109*/
select avg(stars) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)>B.year and A.business_id in (select * from Srestaurants);
/*3.8032069970845481*/

select avg(stars) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)<=B.year and A.business_id in (select * from Chrestaurants);
/*3.5257262863143157*/
select avg(stars) from review as A, elite as B where A.user_id=B.user_id and EXTRACT(YEAR FROM A.date)>B.year and A.business_id in (select * from Chrestaurants);
/*3.4956822107081174*/
/*<--------------------Matters, especially when Chicken wings people gives lower ratings after they join elite--------------->*/

/*7.location*/
select state, count(*) as count from business where business_id in (select * from Srestaurants) group by state order by count;
"ON";1
"SC";2
"BW";2
"IL";3
"QC";7
"WI";16
"PA";20
"NC";36
"NV";41
"AZ";95

select state, count(*) as count from business where business_id in (select * from Chrestaurants) group by state order by count;
"MLN";1
"IL";4
"ON";4
"EDH";4
"SC";6
"QC";9
"WI";25
"PA";28
"NC";80
"NV";111
"AZ";239

select state, sum(review_count*stars)/sum(review_count) as avg_stars 
from business
where business_id in (select * from Srestaurants) 
group by state order by avg_stars;
"IL";2.9000000000000000
"WI";3.3660714285714286
"BW";3.4117647058823529
"ON";3.5000000000000000
"NC";3.6550196850393701
"PA";3.7790697674418605
"NV";3.7902059992771955
"QC";3.9365079365079365
"AZ";3.9448075974346325
"SC";4.5000000000000000

select state, sum(review_count*stars)/sum(review_count) as avg_stars 
from business
where business_id in (select * from Chrestaurants) 
group by state order by avg_stars;

"QC";2.6095890410958904
"ON";2.6304347826086957
"IL";2.7222222222222222
"WI";3.1391184573002755
"AZ";3.1416666666666667
"SC";3.4906542056074766
"NC";3.4954812471757795
"NV";3.5170285714285714
"PA";3.5272058823529412
"MLN";4.0000000000000000
"EDH";4.2142857142857143

select state, sum(review_count*stars)/sum(review_count) as avg_stars 
from business
where state in 
(select distinct state from business
where business_id in (select * from Chrestaurants) or business_id in (select * from Srestaurants))
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

/*DIFFERENCE THREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE*/

/*8 users parameters*/
select avg(fans) from users where user_id in (select * from Susers); /*4.3748078302756995*/
select avg(fans) from users where user_id in (select * from Chusers); /*3.6751765893037336*/

select stddev(fans) from users where user_id in (select * from Susers); /*24.0674583415322461*/
select stddev(fans) from users where user_id in (select * from Chusers); /*17.0765446025157097*/

select max(fans) from users where user_id in (select * from Susers); /*937*/
select max(fans) from users where user_id in (select * from Chusers); /*743*/

/*<------------------Very different, Salad Restaurant raters have more fans------------>*/

select avg(yelping_since_year) from users where user_id in (select * from Susers); /*2010.9240545249564415*/
select avg(yelping_since_year) from users where user_id in (select * from Chusers); /*2011.1811301715438951*/
/*<-------------Similar---------------->*/

/*9. tips*/
select avg(count_tips) from (select user_id, count(tips) as count_tips from tips where user_id in (select * from Susers) group by user_id) as temp; /*21.4479895104895105*/
select avg(count_tips) from (select user_id, count(tips) as count_tips from tips where user_id in (select * from Chusers) group by user_id) as temp; /*21.2639687756778965*/

/*<-------------Similar---------------->*/


/*10. number of users*/
select count(*) from Susers; /*9757*/
select count(*) from Chusers; /*9910*/

select avg(count) as avg_review_num from
(select business_id, user_id, count(*) from review where business_id in (select * from Srestaurants) group by business_id, user_id ) as temp;
/*1.03087743374217342825*/

select avg(count) as avg_review_num from
(select business_id, user_id, count(*) from review where business_id in (select * from Chrestaurants) group by business_id, user_id ) as temp;
/*1.03332510491236731671*/


/*11. number of users reviewed multiple times*/
select count(*) as return_user_count, sum(count) as total_return_review_count, avg(count) as avg_return_review_count from
(select business_id, user_id, count(*) from review where business_id in (select * from Srestaurants) group by business_id, user_id having count(*) >1) as temp;
/*319;679;2.1285266457680251*/

select count(*) as return_user_count, sum(count) as total_return_review_count, avg(count) as avg_return_review_count from
(select business_id, user_id, count(*) from review where business_id in (select * from Chrestaurants) group by business_id, user_id having count(*) >1) as temp;
/*366;771;2.1065573770491803*/


/*latitude, longitude*/
select latitude, longitude from business where business_id in (select * from Srestaurants);
select latitude, longitude from business where business_id in (select * from Chrestaurants);


/*test*/
select distinct business_id from business where business_id in (select * from Chrestaurants);

select count(*) from review where business_id in (select * from SrestaurantsF); /*12177*/
select count(*) from review where business_id in (select * from ChrestaurantsF); /*12716*/

select * from ChrestaurantsF;

select sum(review_count) from business; /*1729825*/
select count(*) from review; /*1569252*/
select sum(review_count) from users; /*11813654*/

select avg(stars) from review where business_id in (select * from Srestaurants); /*3.8032754381790784*/
select avg(stars) from review where business_id in (select * from Chrestaurants); /*3.5054898306458148*/

select count(*) from Susers; /*32629*/
select count(*) from Chusers; /*31925*/


