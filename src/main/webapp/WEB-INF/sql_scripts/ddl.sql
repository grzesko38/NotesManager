DROP DATABASE If EXISTS notesmanager;
CREATE DATABASE notesmanager;
USE notesmanager
GRANT ALL ON notesmanager TO Adam@localhost;

DROP TABLE IF EXISTS NOTES;
CREATE TABLE NOTES
(
    id				INT PRIMARY KEY AUTO_INCREMENT,
    author			VARCHAR(30),
    email	   		VARCHAR(30),
    date_created    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified   TIMESTAMP,
    content			VARCHAR(4096)
);

DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
    id					INT PRIMARY KEY AUTO_INCREMENT,
    nick				VARCHAR(32) NOT NULL,
    email	   			VARCHAR(128) NOT NULL,
    date_registered    	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	password_hash		VARCHAR(128) NOT NULL,
	password_encoding	VARCHAR(16) NOT NULL,
	password_salt		VARCHAR(32) NOT NULL
);