CREATE DATABASE laindain_project4;

USE laindain_project4;

CREATE TABLE Event (
    EVENT_ID INT PRIMARY KEY AUTO_INCREMENT,    
    EVENT_NAME VARCHAR(255)
    );
    
insert INTO Event values (12,'marrage');
CREATE TABLE Attendee (
    ATTENDEE_ID INT PRIMARY KEY AUTO_INCREMENT,      
    NAME VARCHAR(255),               
    Amount DECIMAL(10,2),       
    EVENT_ID INT,                    
    FOREIGN KEY (EVENT_ID) REFERENCES Event(EVENT_ID)  
);