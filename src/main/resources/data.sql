INSERT INTO geoworkers (firstname, lastname, username, password, enabled) VALUES ('Admin', 'Admin', 'admin', 'YWRtaW4=', true);
INSERT INTO roles (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO geoworkers_roles (geoworker_id, role_id) VALUES (1,1);