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


insert into users(username, password, enabled) values('amat','$2a$10$1i3UPF2hU1.FT7Vz7XldE.N915i0Lc/nyMWyfxP36oy9Kw3jQWnjm',1);
insert into users(username, password, enabled) values('admin','$2a$10$rSaXcj5Cl/DVjlQ0fr.qTuhkfFbwZ8JXrIeTLPlVhabEWR.tzFw0S',1);
insert into authorities(user_id, authority)values(1, 'ROLE_USER');
insert into authorities(user_id, authority)values(2, 'ROLE_USER');
insert into authorities(user_id, authority)values(2, 'ROLE_ADMIN');