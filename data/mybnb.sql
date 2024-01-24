drop database if exists mybnb;
create database mybnb;
use mybnb;

create table acc_occupancy (
   acc_id varchar(10) not null,
   vacancy int(64) not null,

   primary key(acc_id)
);

create table reservations  (
   resv_id varchar(8) not null,
   name varchar(128) not null,
   email varchar(128) not null,
   acc_id varchar(10) not null,
   arrival_date date not null,
   duration int not null,

   primary key(resv_id),
   foreign key (acc_id) references acc_occupancy(acc_id)
);

grant all privileges on mybnb.* to celine@'%';

flush privileges;