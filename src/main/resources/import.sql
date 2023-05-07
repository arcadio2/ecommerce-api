

INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('arcadio','$2a$10$v4RgozYXiSyb8ckZb41QDuUorAI8YzFeOqZkyUdL3hKE/e7Fh3PeC',1,'Arcadio','Lopez','arcadio@gmail.com');
INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('admin','$2a$10$W143tnyFIwDOhaGhPF2p8eK225fmycCIos5cgGWQevh3EMDBXHUYG',1,'Jesus','Lopez','jesus@gmail.com');
INSERT INTO usuarios (username,password,enabled,nombre,apellido,email) values ('cristobal','$2a$10$W143tnyFIwDOhaGhPF2p8eK225fmycCIos5cgGWQevh3EMDBXHUYG',1,'Crsitobal','De la Huerta','cristobalavalos09@gmail.com');


INSERT INTO roles (nombre) values ('ROLE_USER');
INSERT INTO roles (nombre) values ('ROLE_ADMIN');


insert into usuario_roles (usuario_id,role_id) values (1,1); 
#insert into usuario_roles (usuario_id,role_id) values (1,3); 
insert into usuario_roles (usuario_id,role_id) values (2,2);
insert into usuario_roles (usuario_id,role_id) values (2,1);  
insert into usuario_roles (usuario_id,role_id) values (3,1);  

insert into sexo(sexo) values ("Hombre"); 
insert into sexo(sexo) values ("Mujer"); 	

	

insert into categoria_producto(tipo,is_hombre,is_mujer) values ("Playeras",true,true); 
insert into categoria_producto(tipo,is_hombre,is_mujer) values ("Pantalones",true,true);
insert into categoria_producto(tipo,is_hombre,is_mujer) values ("Camisas",true,false); 
insert into categoria_producto(tipo,is_hombre,is_mujer) values ("Blusas",false,true);
insert into categoria_producto(tipo,is_hombre,is_mujer) values ("Vestidos",false,true);   
insert into categoria_producto(tipo,is_hombre,is_mujer) values ("Tenis",true,true);  
#insert into categoria_producto(tipo,is_hombre,is_mujer) values ("Relojes",true,false);
insert into categoria_producto(tipo,is_hombre,is_mujer) values ("Sueteres",true,true);    
/*
#playeras
insert into categoria_sexo(categoria_producto_id,sexo_id) values(1,1); 
insert into categoria_sexo(categoria_producto_id,sexo_id) values(1,2); 

#pantalones
insert into categoria_sexo(categoria_producto_id,sexo_id) values(2,1); 
insert into categoria_sexo(categoria_producto_id,sexo_id) values(2,2); 

#camisas
insert into categoria_sexo(categoria_producto_id,sexo_id) values(3,1); 
#blusas
insert into categoria_sexo(categoria_producto_id,sexo_id) values(4,2); 
#vestidos
insert into categoria_sexo(categoria_producto_id,sexo_id) values(5,2); 
*/


insert into perfil(altura,edad,foto,talla_camisa,talla_pantalon,sexo_id,usuario_id) values (1.75,12,"",34,32,1,1);
insert into direccion(colonia,cp,delegacion,num_ext,num_int) values ("Benito Juarez",54469,"Nicolás Romero",12,12);
insert into perfil_direcciones(perfil_id,direccion_id) values (1,1);

#producto
insert into producto(descripcion,nombre,precio,categoria_id,is_hombre) values ("no se","playera moderna",367,1,true); 


insert into producto(descripcion,nombre,precio,categoria_id,is_hombre) values ("no se","pantalon moderno",345,2,true);
insert into producto(descripcion,nombre,precio,categoria_id,is_hombre) values ("no se","camisa shine",287,3,true);

insert into producto(descripcion,nombre,precio,categoria_id,is_hombre) values ("no se","playera clash",569,1,false); 
insert into producto(descripcion,nombre,precio,categoria_id,is_hombre) values ("no se","playera shine",10000,1,true); 

#prueba
insert into producto(descripcion,nombre,precio,categoria_id,is_hombre) values ("Camisa cómoda, útil para salir de fiesta","camisa clasica",10000,3,true); 


insert into colores(color,hexadecimal) values ("rojo","#d89f97"); 
insert into colores(color,hexadecimal) values ("verde","#afbfab"); 
insert into colores(color,hexadecimal) values ("azul","#91a5bb"); 
insert into colores(color,hexadecimal) values ("blanco","#d2d3d4");
insert into colores(color,hexadecimal) values ("negro","#0A0A0A");  


insert into tallas(talla) values ("L");
insert into tallas(talla) values ("XL");
insert into tallas(talla) values ("S");
insert into tallas(talla) values ("XS");
insert into tallas(talla) values ("M");


insert into detalle_producto(color_id,producto_id,talla_id,stock) values (1,1,1,3);
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (2,1,2,4);
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (3,1,1,5);


insert into detalle_producto(color_id,producto_id,talla_id,stock) values (1,2,1,3);
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (2,2,2,4);
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (3,2,1,5);

insert into detalle_producto(color_id,producto_id,talla_id,stock) values (1,3,1,1);
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (2,3,2,2);
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (3,3,3,3); 

insert into detalle_producto(color_id,producto_id,talla_id,stock) values (3,4,3,4); 
  
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (3,5,3,4); 
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (1,5,1,5); 
#prueba
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (4,6,5,10); 
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (5,6,5,8); 
insert into detalle_producto(color_id,producto_id,talla_id,stock) values (5,6,1,11); 
#bolsa 
 
#insert into bolsa(cantidad,detalle_producto_id,usuario_id) values (2,1,1); 
#insert into bolsa(cantidad,detalle_producto_id,usuario_id) values (3,8,1); 
#insert into bolsa(cantidad,detalle_producto_id,usuario_id) values (1,5,1); 
#insert into bolsa(cantidad,detalle_producto_id,usuario_id) values (1,13,1); 
#insert into bolsa(cantidad,detalle_producto_id,usuario_id) values (1,14,1); 

insert into bolsa(cantidad,detalle_producto_id,usuario_id) values (2,2,2); 




insert into comentarios(producto_id,usuario_id,valoracion,comentario,titulo) values (1,1,5,"Muy buen producto, es el mejor del mercado, debido a que tiene una buena relación de calidad y precio","Mejor relacion calidad precio");
insert into comentarios(producto_id,usuario_id,valoracion,comentario,titulo) values (1,2,2,"Muy mal producto, es el peor del mercado, debido a que tiene una mala relación de calidad y precio","Peor relación calidad precio");

insert into comentarios(producto_id,usuario_id,valoracion,comentario,titulo) values (2,2,4,"Muy buen producto","XD");


#insert into valoracion_producto(producto_id,usuario_id,valoracion) values (1,1,5); 
#insert into valoracion_producto(producto_id,usuario_id,valoracion) values (1,2,3.5); 


