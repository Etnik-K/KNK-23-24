create database knk2024; 
use knk2024;

drop table users;
select * from users;

create table faculties (
    faculty_id INT AUTO_INCREMENT PRIMARY KEY,
    faculty_name VARCHAR(100) NOT NULL
);
create table users (
	id integer primary key auto_increment,
    firstName nvarchar(30),
    lastName nvarchar(50),
    email nvarchar(100),
    salt nvarchar(100),
    passwordHash nvarchar(500),
    user_type ENUM('admin', 'professor', 'student') NOT NULL,
    faculty_id INT,
    is_approved BOOLEAN DEFAULT FALSE
);
create table schedules (
     schedule_id INT AUTO_INCREMENT PRIMARY KEY,
     faculty_id INT,
     course_name VARCHAR(100) NOT NULL,
     professor_id INT,
     day_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday') NOT NULL,
     start_time TIME NOT NULL,
     end_time TIME NOT NULL,
     CONSTRAINT fk_faculty FOREIGN KEY (faculty_id) REFERENCES faculties(faculty_id),
     CONSTRAINT fk_professor FOREIGN KEY (professor_id) REFERENCES users(id)
 );
 -- profesori table, lenda table edhe salla table