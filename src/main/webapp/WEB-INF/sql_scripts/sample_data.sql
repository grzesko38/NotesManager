INSERT INTO USERS
(
	id, nick, email, date_registered, password_hash, password_encoding, password_salt, enabled, user_type
)
VALUES
(
	1, 'Adam', 'adam.arczynski@gmail.com', '1990-01-01 00:00:00', '0264abd1fc2a47a9d938201ae4429594882fb9887c8b7b3e8041c9784f8ac85c', 'sha-256', 'cfd6a7346064d78b38d9a5a2f19f2963', 1, 'RegisteredUser'
),
(
	2, 'Adam', NULL, NULL, NULL, NULL, NULL, NULL, 'AnonymousUser'
),
(
	3, 'Olka', NULL, NULL, NULL, NULL, NULL, NULL, 'AnonymousUser'
),
(
	4, 'Alka', NULL, NULL, NULL, NULL, NULL, NULL, 'AnonymousUser'
),
(
	5, 'Misiek', NULL, NULL, NULL, NULL, NULL, NULL, 'AnonymousUser'
),
(
	6, 'Witek', NULL, NULL, NULL, NULL, NULL, NULL, 'AnonymousUser'
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
),
(
	3, 2, 'ROLE_ANONYMOUS'
),
(
	4, 3, 'ROLE_ANONYMOUS'
),
(
	5, 4, 'ROLE_ANONYMOUS'
),
(
	6, 5, 'ROLE_ANONYMOUS'
),
(
	7, 6, 'ROLE_ANONYMOUS'
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
	user_fk, date_created, last_modified, deadline, title, content, latitude, longitude
)
VALUES
(
	1, '2008-10-05 00:00:00', NULL, '2008-10-25 00:00:00', 'Wpis 123 abc', 'moja notatka ble ble ble ble bla bla', 50.4822855, 17.32958610000003
),
(
	1, '2008-11-07 00:00:00', NULL, '2011-10-25 00:00:00', 'Wpis 123 abc', 'wpis aaa bbb', 50.4822855, 17.32958610000003
),
(
	2, '2009-01-01 00:00:00', NULL, '2010-10-30 00:00:00', 'Wpis Adama', 'wpis aaa bbb', 50.4822855, 17.32958610000003
),
(
	2, '2005-01-01 00:00:00', NULL, '2006-05-23 00:00:00', 'Adam Adam Adam', 'wpis aaa bbb text text text text abc 123 123', 50.4822855, 17.32958610000003
),
(
	2, '2009-01-01 00:00:00', NULL, '2011-10-30 00:00:00', 'XSS hax0R', '<script>alert("hacked!")</script>', 51.1329635, 17.065819000000033
),
(
	2, '1999-01-01 00:00:00', NULL, '2001-10-30 00:00:00', 'Life in Texas', 'Texas 1234566789', 31.9685988, -99.90181310000003
),
(
	2, '2009-01-01 00:00:00', NULL, '2011-10-30 00:00:00', 'test no map attached', 'ble ble', NULL, NULL
),
(
	3, '2009-04-01 00:00:00', NULL, '2015-10-30 00:00:00', 'notka Olki', 'xxxx cccc vvvvvv dddd fffffff dddd yyy', NULL, NULL
),
(
	3, '2012-04-01 00:00:00', NULL, '2016-10-30 00:00:00', 'Blizzard Entertaiment, Irvine', 'World of Warcraft', 33.658161, -117.767091
),
(
	3, '2010-09-07 00:00:00', NULL, '2011-05-14 00:00:00', 'Wawel', 'Kraków', 50.06465009999999, 19.94497990000002
),
(
	4, '2009-03-07 00:00:00', NULL, '2014-10-30 00:00:00', 'Ala ma kota', 'tttteeeeeeeeeexxxxxxxxxxxxeeeeettttt mooooreee teeexxxtttt', 50.06465009999999, 19.94497990000002
),
(
	5, '2005-03-11 00:00:00', NULL, '2005-05-14 00:00:00', 'Miś Koala', 'brrrrrrr brrr', 73.03757499999999, -85.148031
),
(
	5, '2004-04-07 00:00:00', NULL, '2012-12-14 00:00:00', 'HTML test', 'test <b>bold</b>', 73.03757499999999, -85.148031
),
(
	5, '2003-04-07 00:00:00', NULL, '2003-05-21 00:00:00', 'gg teeeesst gggg', 'hhhhh hhhhh hhhhh', NULL, NULL
),
(
	5, '2011-04-07 00:00:00', NULL, '2013-03-08 00:00:00', 'Dzień kobiet', '8. marca', 50.662814, 17.919937000000004
),
(
	6, '2001-04-07 00:00:00', NULL, '2008-12-08 00:00:00', 'Morze', 'Kołobrzeg', 54.17591729999999, 15.583266699999967
),
(
	6, '2011-04-07 00:00:00', NULL, '2011-10-10 00:00:00', 'Urodzinki', 'Urodziny Tomka', NULL, NULL
),
(
	6, '2010-04-07 00:00:00', NULL, '2010-09-08 00:00:00', 'pwr wiz studia', 'w8 wiz', 51.108673, 17.060158
);
