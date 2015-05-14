DROP DATABASE IF EXISTS notesmanager;
CREATE DATABASE notesmanager;
USE notesmanager;

CREATE USER 'notes_admin'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON `notesmanager`.* TO 'notes_admin'@'localhost'WITH GRANT OPTION;

DROP TABLE IF EXISTS NOTES;
DROP TABLE IF EXISTS USER_ROLES;
DROP TABLE IF EXISTS USER_PREFERENCES;
DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS
(
    id					INT PRIMARY KEY AUTO_INCREMENT,
    nick				VARCHAR(32) NOT NULL,
    email	   			VARCHAR(128),
    date_registered    	TIMESTAMP,
	password_hash		VARCHAR(128),
	password_encoding	VARCHAR(16),
	password_salt		VARCHAR(32),
	enabled				TINYINT(1),
	user_type			VARCHAR(32)
);

CREATE TABLE USER_ROLES
(
    id				INT PRIMARY KEY AUTO_INCREMENT,
    user_fk			INT,
    role			VARCHAR(32) NOT NULL
);

CREATE TABLE USER_PREFERENCES
(
    id				INT PRIMARY KEY AUTO_INCREMENT,
    user_fk			INT,
    theme			VARCHAR(16) NOT NULL,
    locale			VARCHAR(8) NOT NULL
);

CREATE TABLE NOTES
(
    id				INT PRIMARY KEY AUTO_INCREMENT,
    user_fk			INT,
    date_created    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified   TIMESTAMP,
    content			VARCHAR(4096)
);
