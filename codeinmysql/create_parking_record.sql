use uwa_parking;
create table parking_record (
	action varchar(10) not null,
	time datetime not null,
	parkinglots_name varchar(60) not null,
	user_name varchar(20) not null
)
	
