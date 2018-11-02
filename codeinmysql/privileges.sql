create user 'admin'@'%' identified by 'adminpassword';
create user 'normal_user'@'%' identified by 'normalpassword';
grant all privileges on uwa_parking.* to admin@'%' identified by 'adminpassword' with grant option;
grant select privileges on uwa_parking.parkinglots_general to 'normal_user'@'%' identified by 'normalpassword' with grant option;
grant select privileges on uwa_parking.parking_record to 'normal_user'@'%' identified by 'normalpassword' with grant option;
flush privileges;