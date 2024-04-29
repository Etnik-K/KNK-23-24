create database knk2024; 
use knk2024;

create table knk2024.users(
	id integer primary key auto_increment,
    firstName nvarchar(30),
    lastName nvarchar(50),
    email nvarchar(100),
    salt nvarchar(100),
    passwordHash nvarchar(500)
);
select * from knk2024.users;