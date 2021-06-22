INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Admin', 'Admin', 'admin', 'YWRtaW4=', true);
INSERT INTO roles (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (1,1);

INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Dolgozó1', 'GeoLink3D', 'user1', 'b25l', true);
INSERT INTO roles (id, role) VALUES (2, 'ROLE_USER');
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (2,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Dolgozó2', 'GeoLink3D', 'user2', 'dHdv', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (3,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Dolgozó3', 'GeoLink3D', 'user3', 'dGhyZWU=', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (4,2);

INSERT INTO locations (name) values ('Dunakeszi');
INSERT INTO locations (name) values ('Debrecen');
INSERT INTO locations (name) values ('Kecskemét');

INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('Drone', null, false, null, null,  null, null, false, null);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('S7 Total Station', 'Hello World!', false, '2021-06-21 06:05', 'Dunakeszi',  null, null, true, 3);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('FARO Laser Scanner', null, true, null, null, null, null, false, null);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('Leica Total Station', null, true, null, null, 'Debrecen', null, false, null);

