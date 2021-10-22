INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Admin', 'Admin', 'admin', 'YWRtaW4=', true);
INSERT INTO roles (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (1,1);
INSERT INTO roles (id, role) VALUES (3, 'ROLE_GUEST');
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Miklós', 'Csóri', 'csorimiklos@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO roles (id, role) VALUES (2, 'ROLE_USER');
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (2,2);
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Dániel', 'Gáncs', 'gancsdaniel@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (3,1);
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
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Levente', 'Csirkés', 'csirkeslevente@geolink3d.hu', 'dXNlcg==', true);
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
INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Zoltán', 'Nagy', 'nagyzoltan@geolink3d.hu', 'dXNlcg==', true);
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (22,1);

INSERT INTO locations (name) values ('Dunakeszi');
INSERT INTO locations (name) values ('Debrecen');
INSERT INTO locations (name) values ('Kecskemét');

INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('FARO X330 lézerszkenner', null, false, null, null,  null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('I. Trimble S6 mérőállomás + TSC3', null, false, null, null,  null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('II. Trimble S6 mérőállomás + TSC3', null, false, null, null,  null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('III. Trimble S7 mérőállomás + TSC7', null, false, null, null,  null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('IV. Trimble S5 mérőállomás + TSC?', null, false, null, null,  null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Bér-Trimble S5 mérőállomás + TSC?', null, false, null, null,  null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('FARO S150 lézerszkenner', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Leica RTC lézerszkenner', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Trimble R2 GPS', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Reach RS2 GPS', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Reach RS GPS', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Phantom4 RTK Drone', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Nanoboat', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Garmin kamera', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Osmo kamera', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('DJI (nagy) kézikamera', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('DJI (kis) kézikamera', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('DJI Spark Drone', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Omnidots rezgésmérő', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Leica Sprinter szintezőműszer', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('PLS szintezőlézer', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Hilti forgólézer', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Hilti szögbelövő', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('SonarMite BTX', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('DJI Phantom4 Pro Drone', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('DJI Inspire Drone', null, false, null, null, null, null, false, null, 0);
INSERT INTO instruments (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, frequency) 
VALUES ('Stonex GPS', null, false, null, null, null, null, false, null, 0);


INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Trimble nagy töltő', null, false, null, null,  null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Trimble 360 prizma', null, false, null, null,  null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Stonex 360 prizma', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Leica prizma', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Motorola rádió 1', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Motorola rádió 2', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Motorola rádió 3', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Drone landingpad 1', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Drone landingpad 2', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('DJI Ronin állvány', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Trimble Traverse Kit', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Miniprizma 1', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Miniprizma 2', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Nagy prizma', null, false, null, null, null, null, false, null, null, 0);
INSERT INTO additionals (name, comment, deleted, pick_up_date, pick_up_place, put_down_place, put_down_date, used, geoworker_id, instrument_id, frequency) 
VALUES ('Stonex GPS rúd', null, false, null, null, null, null, false, null, null, 0);
