

INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('arcadio','$2a$10$v4RgozYXiSyb8ckZb41QDuUorAI8YzFeOqZkyUdL3hKE/e7Fh3PeC',1,'Arcadio','Lopez','arcadio@gmail.com');
INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('admin','$2a$10$W143tnyFIwDOhaGhPF2p8eK225fmycCIos5cgGWQevh3EMDBXHUYG',1,'Jesus','Lopez','jesus@gmail.com');


INSERT INTO roles (nombre) values ('ROLE_USER');
INSERT INTO roles (nombre) values ('ROLE_ADMIN');


insert into usuario_roles (usuario_id,role_id) values (1,1); 
#insert into usuario_roles (usuario_id,role_id) values (1,3); 
insert into usuario_roles (usuario_id,role_id) values (2,2);
insert into usuario_roles (usuario_id,role_id) values (2,1);  


insert into sexo(sexo) values ("hombre"); 
insert into sexo(sexo) values ("mujer"); 	

insert into categoria_producto(tipo) values ("playeras"); 
insert into categoria_producto(tipo) values ("pantalones");
insert into categoria_producto(tipo) values ("camisas"); 
insert into categoria_producto(tipo) values ("blusas");
insert into categoria_producto(tipo) values ("vestidos");   


insert into perfil(altura,edad,foto,talla_camisa,talla_pantalon,sexo_id,usuario_id) values (1.75,12,"",34,32,1,1);
insert into direccion(colonia,cp,delegacion,num_ext,num_int) values ("Benito Juarez",54469,"Nicolás Romero",12,12);
insert into perfil_direcciones(perfil_id,direccion_id) values (1,1);

#producto
insert into producto(descripcion,nombre,precio,categoria_id) values ("no se","playera verde",10,1); 
insert into producto(descripcion,nombre,precio,categoria_id) values ("no se","pantalon verde",10,2);
insert into producto(descripcion,nombre,precio,categoria_id) values ("no se","camisa verde",10,3);

insert into colores(color) values ("rojo"); 
insert into colores(color) values ("verde"); 
insert into colores(color) values ("azul"); 

insert into tallas(talla) values ("L");
insert into tallas(talla) values ("XL");
insert into tallas(talla) values ("S");
insert into tallas(talla) values ("XS");
insert into tallas(talla) values ("M");


insert into detalle_producto(color_id,producto_id,talla_id) values (1,1,1);
insert into detalle_producto(color_id,producto_id,talla_id) values (2,1,2);
insert into detalle_producto(color_id,producto_id,talla_id) values (3,1,1);

insert into detalle_producto(color_id,producto_id) values (1,2);
insert into detalle_producto(color_id,producto_id) values (2,2);
insert into detalle_producto(color_id,producto_id) values (3,2);

insert into detalle_producto(color_id,producto_id) values (1,3);
insert into detalle_producto(color_id,producto_id) values (2,3);
insert into detalle_producto(color_id,producto_id) values (3,3); 


  
#bolsa
 
insert into bolsa(cantidad,detalle_producto_id,usuario_id) values (2,1,1); 
insert into bolsa(cantidad,detalle_producto_id,usuario_id) values (3,3,1); 


insert into bolsa(cantidad,detalle_producto_id,usuario_id) values (2,2,2); 




insert into comentarios(producto_id,usuario_id,comentario) values (1,1,"Muy buen producto");
insert into comentarios(producto_id,usuario_id,comentario) values (1,2,"Muy mal producto");

insert into comentarios(producto_id,usuario_id,comentario) values (2,2,"Muy buen producto");


#insert into valoracion_producto(producto_id,usuario_id,valoracion) values (1,1,5); 
#insert into valoracion_producto(producto_id,usuario_id,valoracion) values (1,2,3.5); 


