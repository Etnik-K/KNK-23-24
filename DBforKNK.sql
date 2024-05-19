drop database knk2024;
create database knk2024;
use knk2024;

/*drop table users;*/
/*select * from users;*/

create table fakulteti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    faculty_name VARCHAR(100) NOT NULL
);
-- drop table users;
create table users (
	id integer primary key auto_increment,
    firstName nvarchar(30),
    lastName nvarchar(50),
    email nvarchar(300),
    salt nvarchar(100),
    passwordHash nvarchar(500),
    user_type ENUM('admin', 'professor', 'student') NOT NULL,
    faculty_id INT,
    is_approved BOOLEAN DEFAULT false
);
ALTER TABLE users MODIFY COLUMN is_approved BOOLEAN NULL DEFAULT NULL;

select * from users;

CREATE TABLE profesor (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          firstName nvarchar(30),
                          lastName nvarchar(50),
                          faculty_id int,
                          is_approved BOOLEAN
);

CREATE TABLE lenda (
    id INT PRIMARY KEY AUTO_INCREMENT,
    lenda_name VARCHAR(100),
    has_lab BOOLEAN
);

delimiter //
create trigger insert_into_profesor
    after insert on users
    for each row
begin
    if new.user_type = 'professor' then
        insert into profesor (firstName, lastName, faculty_id, is_approved)
        values (new.firstName, new.lastName, new.faculty_id, new.is_approved);
    end if;
end;
//
delimiter ;

/*drop table salla;*/
CREATE TABLE salla (
    id INT PRIMARY KEY AUTO_INCREMENT,
    salla_name VARCHAR(100),
    capacity INT,
    unique key(capacity)
);
INSERT INTO salla (salla_name, capacity) VALUES ('621', 52);
INSERT INTO salla (salla_name, capacity) VALUES ('626', 60);
select * from salla;


ALTER TABLE salla ADD INDEX idx_salla_id (id);
/* table class;*/


CREATE TABLE TimeSlot (
    id INT PRIMARY KEY AUTO_INCREMENT,
    day_of_week VARCHAR(20),
    start_time TIME,
    end_time TIME
);
INSERT INTO TimeSlot (day_of_week, start_time, end_time)
VALUES
('Monday', '09:00:00', '10:00:00'),
('Monday', '10:00:00', '11:00:00'),
('Monday', '11:00:00', '12:00:00'),
('Monday', '12:00:00', '13:00:00'),
('Monday', '13:00:00', '14:00:00'),
('Monday', '14:00:00', '15:00:00'),
('Monday', '15:00:00', '16:00:00'),
('Monday', '16:00:00', '17:00:00'),

('Tuesday', '09:00:00', '10:00:00'),
('Tuesday', '10:00:00', '11:00:00'),
('Tuesday', '11:00:00', '12:00:00'),
('Tuesday', '12:00:00', '13:00:00'),
('Tuesday', '13:00:00', '14:00:00'),
('Tuesday', '14:00:00', '15:00:00'),
('Tuesday', '15:00:00', '16:00:00'),
('Tuesday', '16:00:00', '17:00:00'),

('Wednesday', '09:00:00', '10:00:00'),
('Wednesday', '10:00:00', '11:00:00'),
('Wednesday', '11:00:00', '12:00:00'),
('Wednesday', '12:00:00', '13:00:00'),
('Wednesday', '13:00:00', '14:00:00'),
('Wednesday', '14:00:00', '15:00:00'),
('Wednesday', '15:00:00', '16:00:00'),
('Wednesday', '16:00:00', '17:00:00'),

('Thursday', '09:00:00', '10:00:00'),
('Thursday', '10:00:00', '11:00:00'),
('Thursday', '11:00:00', '12:00:00'),
('Thursday', '12:00:00', '13:00:00'),
('Thursday', '13:00:00', '14:00:00'),
('Thursday', '14:00:00', '15:00:00'),
('Thursday', '15:00:00', '16:00:00'),
('Thursday', '16:00:00', '17:00:00'),

('Friday', '09:00:00', '10:00:00'),
('Friday', '10:00:00', '11:00:00'),
('Friday', '11:00:00', '12:00:00'),
('Friday', '12:00:00', '13:00:00'),
('Friday', '13:00:00', '14:00:00'),
('Friday', '14:00:00', '15:00:00'),
('Friday', '15:00:00', '16:00:00'),
('Friday', '16:00:00', '17:00:00');

ALTER TABLE TimeSlot ADD INDEX idx_id (id);

/*drop table Class;*/
CREATE TABLE Class (
    id INT PRIMARY KEY AUTO_INCREMENT,
    profesor_id INT,
    lenda_id INT,
    salla_id INT,
    time_slot_id INT,
    FOREIGN KEY (profesor_id) REFERENCES profesor(id),
    FOREIGN KEY (lenda_id) REFERENCES lenda(id),
    FOREIGN KEY (salla_id) REFERENCES salla(id),
    FOREIGN KEY (time_slot_id) REFERENCES TimeSlot(id)
);
/*drop table Orari;*/
CREATE TABLE Orari(
    id int primary key auto_increment,
    fakulteti_id int,
    profesori_id int,
    lenda_id int,
    salla_id int,
    time_slot_id INT, -- Change this to INT
    start_time time,
    end_time time,
    day_of_week nvarchar(15),
    capacity int,
    FOREIGN KEY (profesori_id) references profesor(id) ,
    FOREIGN KEY (lenda_id) references lenda(id)  ,
    FOREIGN KEY (salla_id) references salla(id)  ,
    FOREIGN KEY (fakulteti_id) references fakulteti(id) ,
    FOREIGN KEY (time_slot_id) references TimeSlot(id), -- Change this to TimeSlot(id)
    foreign key  (capacity) references salla(capacity)
);
select * from orari;


/*Ndryshimet e fundit*/
/*CREATE TABLE approved_users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName NVARCHAR(30),
    lastName NVARCHAR(50),
    email NVARCHAR(300),
    user_type ENUM('admin', 'professor', 'student') NOT NULL,
    faculty_id INT,
    is_approved BOOLEAN DEFAULT TRUE
);*/

/*CREATE TABLE denied_users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName NVARCHAR(30),
    lastName NVARCHAR(50),
    email NVARCHAR(300),
    user_type ENUM('admin', 'professor', 'student') NOT NULL,
    faculty_id INT,
    is_approved BOOLEAN DEFAULT FALSE
);*/

select * from approved_users;
select * from denied_users;
select * from users;
use knk2024;