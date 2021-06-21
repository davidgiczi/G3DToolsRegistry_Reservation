INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Admin', 'Admin', 'admin', 'YWRtaW4=', true);
INSERT INTO roles (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (1,1);

INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Dolgozó1', 'GeoLink3D', 'username1', 'password1', true);
INSERT INTO roles (id, role) VALUES (2, 'ROLE_USER');
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (2,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Dolgozó2', 'GeoLink3D', 'username2', 'password2', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (3,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Dolgozó3', 'GeoLink3D', 'username3', 'password3', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (4,2);

INSERT INTO locations (name) values ('Dunakeszi');
INSERT INTO locations (name) values ('Debrecen');
INSERT INTO locations (name) values ('Kecskemét');

INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('Drone', 'Test comment', false, '2020-01-01 16:05', null,  'Dunkaeszi', null, false, null);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('S7 Total Station', 'Test comment', false, '2021-06-21 06:05', null,  'Kecskemét', null, false, 3);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('FARO Laser Scanner', 'Test comment', true, null, null, 'Debrecen', null, false, null);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('Leica Total Station', 'Test comment', true, null, null, 'Debrecen', null, false, null);

