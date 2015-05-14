INSERT INTO USERS
(
	id, nick, email, date_registered, password_hash, password_encoding, password_salt, enabled, user_type
)
VALUES
(
	1, 'Adam', 'adam.arczynski@gmail.com', '1990-01-01 00:00:00', '6a8e7f5f2b4ebf1a219d551e46e1338cf149ef175d48de64e33322b5704963b4', 'sha-256', '25ffed', 1, 'RegisteredUser'
);

INSERT INTO USER_ROLES
(
	id, user_fk, role
)
VALUES
(
	1, 1, 'ROLE_USER'
),
(
	2, 1, 'ROLE_ADMIN'
);

INSERT INTO USER_PREFERENCES
(
	id, user_fk, theme, locale
)
VALUES
(
	1, 1, 'blue', 'en_EN'
);

INSERT INTO NOTES
(
	user_fk, date_created, content
)
VALUES
(
	1, '2008-10-05 00:00:00', 'moja notatka ble ble ble'
),
(
	1, '2014-03-04 00:00:00', 'moja notatka ble ble ble'
),
(
	1, '2011-05-25 00:00:00', 'moja notatka 2 bla bla'
),
(
	1, '1997-01-01 00:00:00', 'wpis aaa'
),
(
	1, '1998-10-12 00:00:00', 'wpis aaa 2'
),
(
	1, '2013-08-22 00:00:00', 'rey rey rey rey rey'
),
(
	1, '2013-04-17 00:00:00', 'and and and and and and'
),
(
	1, '2007-02-04 00:00:00', 'ilona ilona ilona ilona ilona'
),
(
	1, '2011-10-30 00:00:00', 'jjjj jjjjjjjjjjjjj jjj jjjjjjjjjj j jj'
),
(
	1, '2005-04-18 00:00:00', 'Maciek Maciek Maciek Maciek Maciek'
),
(
	1, '2000-01-01 00:00:00', 'rererererer erererere ererere'
),
(
	1, '2004-05-21 00:00:00', 'ola ola ola ola'
),
(
	1, '2014-12-01 00:00:00', 'o2o2o2o2o2o2'
);