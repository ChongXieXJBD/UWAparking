use uwa_parking;
create table parking_record (
	name varchar(20) not null,
	password varchar(40) not null,
	email varchar(40) not null,
	role varchar(10) not null,
	permission_type varchar(10) not null,
	create_date datetime not null,
	primary key (name)
)
	
