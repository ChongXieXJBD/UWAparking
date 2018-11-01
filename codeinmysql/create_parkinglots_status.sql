use uwa_parking;
create table parkinglots_status (
	parkinglots_name varchar(60) not null,
	rest_red_permission int not null,
	rest_yellow_permission int not null,	
	primary key (parkinglots_name)
)
	
