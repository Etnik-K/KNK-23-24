-- Drop and recreate the database
DROP DATABASE IF EXISTS knk2024;
CREATE DATABASE knk2024;
USE knk2024;

-- Create fakulteti table
CREATE TABLE fakulteti (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           faculty_name VARCHAR(100) NOT NULL
);

INSERT INTO fakulteti(faculty_name)
VALUES('EAR'), ('IKS'), ('TIK');

-- Create lenda table
CREATE TABLE lenda (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       lenda_name VARCHAR(100)
);

INSERT INTO lenda (lenda_name) VALUES ('Matematike'), ('Elektro'), ('OOP'), ('KNK'), ('AK');

-- Create users table
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       firstName NVARCHAR(30),
                       lastName NVARCHAR(50),
                       email NVARCHAR(300),
                       salt NVARCHAR(100),
                       passwordHash NVARCHAR(500),
                       user_type ENUM('admin', 'professor', 'student') NOT NULL,
                       faculty_id INT,
                       is_approved BOOLEAN DEFAULT false,
                       FOREIGN KEY (faculty_id) REFERENCES fakulteti(id)
);

INSERT INTO users (
    firstName, lastName, email, salt, passwordHash, user_type, faculty_id, is_approved
) VALUES (
             'Valon', 'Raca', 'valon.raca', 'randomSalt123', 'hashedPasswordExample', 'professor', 2, true
         );

-- Create profesor table
CREATE TABLE profesor (
                          id INT PRIMARY KEY,
                          firstName NVARCHAR(30) UNIQUE,
                          lastName NVARCHAR(50) UNIQUE,
                          faculty_id INT UNIQUE,
                          lenda_id INT UNIQUE,
                          is_approved BOOLEAN,
                          FOREIGN KEY (faculty_id) REFERENCES fakulteti(id),
                          FOREIGN KEY (lenda_id) REFERENCES lenda(id)
);
select*from profesor;

-- Create trigger to insert into profesor table when a new user is inserted and is of type 'professor'
DELIMITER //
CREATE TRIGGER insert_into_profesor
    AFTER INSERT ON users
    FOR EACH ROW
BEGIN
    IF NEW.user_type = 'professor' THEN
        INSERT INTO profesor (id, firstName, lastName, faculty_id, lenda_id, is_approved)
        VALUES (NEW.id, NEW.firstName, NEW.lastName, NEW.faculty_id, NULL, NEW.is_approved);
    END IF;
END;
//
DELIMITER ;

-- Update professor record

-- Create salla table
CREATE TABLE salla (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       salla_name VARCHAR(100),
                       capacity INT,
                       UNIQUE KEY (capacity)
);

INSERT INTO salla (salla_name, capacity) VALUES ('621', 52), ('626', 60);

ALTER TABLE salla ADD INDEX idx_salla_id (id);

-- Create TimeSlot table
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
drop table Orari;
-- Create Orari table
CREATE TABLE Orari (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       fakulteti_id INT,
                       profesori_id INT,
                       lenda_id INT,
                       salla_id INT,
                       time_slot_id INT,
                       start_time TIME,
                       end_time TIME,
                       day_of_week NVARCHAR(15),
                       capacity INT,
                       FOREIGN KEY (fakulteti_id) REFERENCES profesor(faculty_id),
                       FOREIGN KEY (profesori_id) REFERENCES profesor(id),
                       FOREIGN KEY (lenda_id) REFERENCES profesor(lenda_id),
                       FOREIGN KEY (salla_id) REFERENCES salla(id),
                       FOREIGN KEY (time_slot_id) REFERENCES TimeSlot(id),
                       FOREIGN KEY (capacity) REFERENCES salla(capacity)
);

-- Create views for approved and denied users
CREATE VIEW approved_users AS
SELECT * FROM users WHERE is_approved = TRUE;

CREATE VIEW denied_users AS
SELECT * FROM users WHERE is_approved = FALSE;

-- Create a view to join users with their faculty, professor, and lenda information
DROP VIEW IF EXISTS orari_details;

CREATE VIEW orari_details AS
SELECT
    o.id AS orari_id,
    o.fakulteti_id,
    f.faculty_name,
    o.profesori_id,
    p.firstName AS professor_firstName,
    p.lastName AS professor_lastName,
    o.lenda_id,
    l.lenda_name,
    o.salla_id,
    s.salla_name,
    o.time_slot_id,
    ts.day_of_week,
    ts.start_time,
    ts.end_time,
    o.capacity
FROM Orari o
         LEFT JOIN fakulteti f ON o.fakulteti_id = f.id
         LEFT JOIN profesor p ON o.profesori_id = p.id
         LEFT JOIN lenda l ON o.lenda_id = l.id
         LEFT JOIN salla s ON o.salla_id = s.id
         LEFT JOIN TimeSlot ts ON o.time_slot_id = ts.id;

-- Example query to fetch the data
SELECT * FROM orari_details;
