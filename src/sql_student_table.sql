DROP TABLE Student;

CREATE TABLE Student (
	id INT auto_increment,
    name VARCHAR(20) NOT NULL, 
    gender VARCHAR(10) NOT NULL,
    email VARCHAR(50),
    birth_date DATE,
    photo VARCHAR(100),
    mark DOUBLE,
    comments VARCHAR(100),
	PRIMARY KEY (id)
);

INSERT INTO Student (name, gender, email, birth_date, photo, mark, comments) 
VALUES ("Marc", "Male", "marc.he@edu.devinci.fr", "2001-11-11", "blabla", 17.3, "comments");
INSERT INTO Student (name, gender, email, birth_date, photo, mark, comments) 
VALUES ("Celine", "Female", "celine.liu@edu.devinci.fr", "2001-11-16", "blablabli", 15.3, "comments");

UPDATE Student SET email = "haha" WHERE id = 1;

SELECT * FROM Student;

SELECT AVG(mark) as avg FROM Student WHERE mark >= 0;