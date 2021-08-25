INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Admin', 'Admin', 'admin', 'YWRtaW4=', true);
INSERT INTO roles (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (1,1);
INSERT INTO roles (id, role) VALUES (3, 'ROLE_GUEST');
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Miklós', 'Csóri', 'csorimiklos@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO roles (id, role) VALUES (2, 'ROLE_USER');
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (2,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Dániel', 'Gáncs', 'gancsdaniel@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (3,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Dávid', 'Giczi', 'giczidavid@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (4,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('István', 'Halász', 'halaszistvan@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (5,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Tamás', 'Hodosán', 'hodosantamas@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (6,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Tibor Balázs', 'Kalmár', 'kalmartibor@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (7,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Bence', 'Kovács', 'kovacsbence@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (8,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Márton', 'Mészáros', 'mesz.marc@gmail.com', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (9,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Ambrus', 'Mistéth', 'mistethambrus@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (10,1);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Attila', 'Nagy', 'nagyattila@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (11,1);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('András', 'Okolicsányi', 'okolicsanyiandras@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (12,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('András', 'Oroszi', 'orosziandras@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (13,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Tibor Péter', 'Pertl', 'pertltibor@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (14,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Máté Dániel', 'Petróczy', 'petroczymate@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (15,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Ákos', 'Sengel', 'sengelakos@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (16,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Máté I.', 'Szabó', 'szabomate@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (17,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Szilárd', 'Székely Laczkó', 'szekelyszilard@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (18,1);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Attila I.', 'Varga', 'vargaattila@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (19,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Attila Roland', 'Varga', 'vargaattilaroland@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (20,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Irma', 'Fejős', 'fejosirma@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (21,1);


INSERT INTO locations (name) values ('Dunakeszi');
INSERT INTO locations (name) values ('Debrecen');
INSERT INTO locations (name) values ('Kecskemét');

/*INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('Drone', null, false, null, null,  null, null, false, null);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('S7 Total Station', 'Hello World!', false, null, null,  null, null, false, null);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('FARO Laser Scanner', null, true, null, null, null, null, false, null);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id) 
VALUES ('Leica Total Station', null, true, null, null, null, null, false, null);

INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id) 
VALUES ('FARO balls', null, false, null, null,  null, null, false, null, null);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id) 
VALUES ('Trimble műszerállvány', 'Hello World!', false, null, null,  null, null, false, null, null);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id) 
VALUES ('FARO prizma', null, true, null, null, null, null, false, null, null);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id) 
VALUES ('Leica prizma', null, true, null, null, null, null, false, null, null);*/


