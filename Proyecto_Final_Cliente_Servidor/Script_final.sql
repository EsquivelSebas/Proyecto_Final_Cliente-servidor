create database proyecto_final_cliente;
use proyecto_final_cliente; 
CREATE TABLE despachadores (
  ID int NOT NULL AUTO_INCREMENT,
  nombre varchar(45) NOT NULL,
  PRIMARY KEY (ID)
);
CREATE TABLE producto (
  id int NOT NULL AUTO_INCREMENT,
  nombre varchar(255) NOT NULL,
  precio int NOT NULL,
  cantidad int NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id)
);
CREATE TABLE venta (
  idVenta int NOT NULL AUTO_INCREMENT,
  idProducto int NOT NULL,
  CantProducto int NOT NULL,
  Precio int NOT NULL,
  fecha date DEFAULT NULL,
  PRIMARY KEY (idVenta),
  KEY idProducto_idx (idProducto)
);



