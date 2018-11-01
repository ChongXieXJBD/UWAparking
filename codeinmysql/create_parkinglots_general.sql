use uwa_parking;
create table parkinglots_general (
	id int not null auto_increment,
	name varchar(60) not null,
	CoorNW varchar(60) not null,
	CoorSE varchar(60) not null,
	CoorSW varchar(60) not null,
	CoorNE varchar(60) not null,
	Capacity int not null,
	yellow_permission int not null,
	red_permission int not null,
	represent_loc varchar(60),
	tickets varchar(10),
	primary key (id)
)
	
