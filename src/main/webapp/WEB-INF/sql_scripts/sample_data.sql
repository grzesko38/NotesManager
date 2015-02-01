INSERT INTO USERS
(
	id, nick, email, date_registered, password_hash, password_encoding, password_salt
)
VALUES
(
	1, 'Adam', 'adam.arczynski@gmail.com', '1990-01-01 00:00:00', 'hash', 'sha-256', 'salt'
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
	null, '2014-03-04 00:00:00', 'moja notatka ble ble ble'
),
(
	1, '2011-05-25 00:00:00', 'moja notatka 2 bla bla'
),
(
	null, '1997-01-01 00:00:00', 'wpis anki'
),
(
	null, '1998-10-12 00:00:00', 'wpis anki 2'
),
(
	null, '2013-08-22 00:00:00', 'rey rey rey rey rey'
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
	null, '2005-04-18 00:00:00', 'Maciek Maciek Maciek Maciek Maciek'
),
(
	null, '2000-01-01 00:00:00', 'rererererer erererere ererere'
);