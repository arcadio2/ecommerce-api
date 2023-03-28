

INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('arcadio','$2a$10$v4RgozYXiSyb8ckZb41QDuUorAI8YzFeOqZkyUdL3hKE/e7Fh3PeC',1,'Arcadio','Lopez','arcadio@gmail.com');
INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('admin','$2a$10$W143tnyFIwDOhaGhPF2p8eK225fmycCIos5cgGWQevh3EMDBXHUYG',1,'Jesus','Lopez','jesus@gmail.com');
INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('jesus','$2a$10$P7NdGgsEiWdPoCYs7dbOYO62kv9ey6dfddQxue6/E8yPItmGkZQWO',1,'Luis','Lopez','luis@gmail.com');
INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('luis','$2a$10$MPD0VH9TkPNjoZk.nRXgU.4n7UguXIrXrEuYQ8lohs8xXJsDf6LCi',1,'Galilea','Lopez','gali@gmail.com');
INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('lalo','$2a$10$MPD0VH9TkPNjoZk.nRXgU.4n7UguXIrXrEuYQ8lohs8xXJsDf6LCi',1,'Eduardo','Torres','eduardo@gmail.com');
INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('fede','$2a$10$MPD0VH9TkPNjoZk.nRXgU.4n7UguXIrXrEuYQ8lohs8xXJsDf6LCi',1,'Federico','López','fede@gmail.com');


INSERT INTO roles (nombre) values ('ROLE_USER');
INSERT INTO roles (nombre) values ('ROLE_ADMIN');
INSERT INTO roles (nombre) values ('ROLE_INSTRUCTOR');


insert into usuario_roles (usuario_id,role_id) values (1,1); 
#insert into usuario_roles (usuario_id,role_id) values (1,3); 
insert into usuario_roles (usuario_id,role_id) values (2,2);
insert into usuario_roles (usuario_id,role_id) values (2,1);  
insert into usuario_roles (usuario_id,role_id) values (3,1);  
insert into usuario_roles (usuario_id,role_id) values (5,3);  
insert into usuario_roles (usuario_ide,role_id) values (6,1); 

