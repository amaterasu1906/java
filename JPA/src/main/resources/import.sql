insert into clientes(nombre, apellido, email, create_at, foto) values('Kokomi', 'Teruhashi', 'terukoko@gmail.com', '2021-01-01', '');
insert into clientes(nombre, apellido, email, create_at, foto) values('Chisato', 'Chiyou', 'chisato@gmail.com', '2021-01-01', '');
insert into clientes(nombre, apellido, email, create_at, foto) values('Kokomi', 'Teruhashi', 'terukoko@gmail.com', '2021-01-01', '');
insert into clientes(nombre, apellido, email, create_at, foto) values('Chisato', 'Chiyou', 'chisato@gmail.com', '2021-01-01', '');
insert into clientes(nombre, apellido, email, create_at, foto) values('Kokomi', 'Teruhashi', 'terukoko@gmail.com', '2021-01-01', '');
insert into clientes(nombre, apellido, email, create_at, foto) values('Chisato', 'Chiyou', 'chisato@gmail.com', '2021-01-01', '');
insert into clientes(nombre, apellido, email, create_at, foto) values('Kokomi', 'Teruhashi', 'terukoko@gmail.com', '2021-01-01', '');
insert into clientes(nombre, apellido, email, create_at, foto) values('Chisato', 'Chiyou', 'chisato@gmail.com', '2021-01-01', '');
insert into clientes(nombre, apellido, email, create_at, foto) values('Kokomi', 'Teruhashi', 'terukoko@gmail.com', '2021-01-01', '');

insert into productos (nombre, precio, create_at) values ('Bocina', 200, now())
insert into productos (nombre, precio, create_at) values ('Regulador', 330, now())
insert into productos (nombre, precio, create_at) values ('Pantalla hp', 3666.66, now())
insert into productos (nombre, precio, create_at) values ('Teclado mecanico', 1199, now())
insert into productos (nombre, precio, create_at) values ('Silla gamer', 1600, now())
insert into productos (nombre, precio, create_at) values ('Xiaomi Mi A2', 4000, now())

insert into facturas(descripcion, observacion, cliente_id, create_at) values('Factura 1', null, 1, now())
insert into facturas_items(cantidad, factura_id, producto_id) values(1,1,1)
insert into facturas_items(cantidad, factura_id, producto_id) values(1,1,4)