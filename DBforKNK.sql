create database knk2024;
use knk2024;

drop table users;
select * from users;

create table fakulteti (
    faculty_id INT AUTO_INCREMENT PRIMARY KEY,
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
    is_approved BOOLEAN DEFAULT FALSE
);

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


CREATE TABLE salla (
    id INT PRIMARY KEY AUTO_INCREMENT,
    salla_name VARCHAR(100),
    capacity INT
);
drop table Class;
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

CREATE TABLE TimeSlot (
    id INT PRIMARY KEY AUTO_INCREMENT,
    day_of_week VARCHAR(20),
    start_time TIME,
    end_time TIME
);
CREATE TABLE Orari(
    id int primary key auto_increment,
    fakulteti_id int,
    profesori_id int,
    lenda_id int,
    salla_id int,
    start_time time,
    end_time time,
    day_of_week nvarchar(15),
    FOREIGN KEY (profesori_id) references profesor(id) on delete cascade ,
    FOREIGN KEY (lenda_id) references lenda(id) on delete cascade ,
    FOREIGN KEY (salla_id) references salla(id) on delete cascade ,
    FOREIGN KEY (fakulteti_id) references fakulteti(id) on delete cascade ,
    FOREIGN KEY (start_time) references TimeSlot(start_time) on delete cascade ,
    FOREIGN KEY (end_time) references TimeSlot(end_time) on delete cascade ,
    FOREIGN KEY (day_of_week) references TimeSlot(day_of_week) on delete cascade
);

