CREATE TABLE "Taller1".clientes (
	nombre varchar NULL,
	identificacion varchar NOT NULL,
	edad int NULL,
	correo varchar NOT NULL,
	CONSTRAINT clientes_pk PRIMARY KEY (identificacion),
	CONSTRAINT clientes_unique UNIQUE (correo)
);

CREATE TABLE "Taller1".productos (
	codigo varchar NOT NULL,
	nombre varchar NULL,
	stock int NULL,
	valor_unitario float8 NULL,
	CONSTRAINT productos_pk PRIMARY KEY (codigo)
);

CREATE TABLE "Taller1".pedidos (
	id serial NOT NULL,
	fecha date NULL,
	cantidad int NULL,
	valor_total float8 NULL,
	producto_id varchar NOT NULL,
	cliente_id varchar NOT NULL,
	CONSTRAINT pedidos_pk PRIMARY KEY (id),
	CONSTRAINT pedidos_unique UNIQUE (producto_id),
	CONSTRAINT pedidos_unique_1 UNIQUE (cliente_id),
	CONSTRAINT pedidos_clientes_fk FOREIGN KEY (cliente_id) REFERENCES "Taller1".clientes(identificacion) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT pedidos_productos_fk FOREIGN KEY (producto_id) REFERENCES "Taller1".productos(codigo) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Valores iniciales
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('Martín', 20, 'martin.ostiosa@autonoma.edu.co', '1055751017');
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('María', 20, 'mariaj.munozp@autonoma.edu.co', '1056121086');


insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0001', 'Manzana', 45, 5000);
insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0002', 'Banano', 32, 2000);

insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/01', 1, 2000, '0002', '1055751017');
insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/02', 2, 10000, '0001', '1056121086');




-- Taller 2
begin;
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('Pepito', 15, 'pepito@autonoma.edu.co', '123');
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('José', 18, 'jose@autonoma.edu.co', '124');
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('Andrés', 20, 'andres@autonoma.edu.co', '125');

insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0003', 'Doritos', 10, 3600);
insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0004', 'Zanahorias', 22, 7600);
insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0005', 'Arroz', 41, 4200);

insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/04', 2, 2000, '0003', '123');
insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/05', 3, 10000, '0004', '124');
insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/06', 4, 2000, '0005', '125');


update "Taller1".clientes set nombre='Julio', edad=5, correo='julio@autonoma.edu.co' where identificacion='125';
update "Taller1".clientes set nombre='Pedro', edad=32, correo='pedro@autonoma.edu.co' where identificacion='123';

update "Taller1".productos set nombre='Lentejas' where codigo='0005';
update "Taller1".productos set nombre='Detodito' where codigo='0003';

update "Taller1".pedidos set cantidad=200 where id=2;
update "Taller1".pedidos set valor_total where id=3;

delete from "Taller1".pedidos where id=3;

delete from "Taller1".clientes where identificacion='1056121086';

delete from "Taller1".productos where codigo='0001';

commit;


-- Taller 3 - Parte 1

SELECT * FROM clientes;
BEGIN;
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('Pepito', 15, 'pepito@autonoma.edu.co', '123');
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('José', 18, 'jose@autonoma.edu.co', '124');
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('Andrés', 20, 'andres@autonoma.edu.co', '125');

insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0003', 'Doritos', 10, 3600);
insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0004', 'Zanahorias', 22, 7600);
insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0005', 'Arroz', 41, 4200);

insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/04', 2, 2000, '0003', '123');
insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/05', 3, 10000, '0004', '124');
insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/06', 4, 2000, '0005', '125');


update "Taller1".clientes set nombre='Julio', edad=5, correo='julio@autonoma.edu.co' where identificacion='125';
update "Taller1".clientes set nombre='Pedro', edad=32, correo='pedro@autonoma.edu.co' where identificacion='123';

update "Taller1".productos set nombre='Lentejas' where codigo='0005';
update "Taller1".productos set nombre='Detodito' where codigo='0003';

update "Taller1".pedidos set cantidad=200 where producto_id = '0002';
update "Taller1".pedidos set valor_total=1000 where producto_id ='0003';

delete from "Taller1".pedidos where producto_id='0001';

delete from "Taller1".clientes where identificacion='1056121086';

delete from "Taller1".productos where codigo='0001';
ROLLBACK;
SELECT * FROM clientes;


-- Taller 3 - Parte 2

SELECT * FROM clientes;
BEGIN;
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('Pepito', 15, 'pepito@autonoma.edu.co', '123');
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('José', 18, 'jose@autonoma.edu.co', '124');
insert into "Taller1".clientes  (nombre, edad, correo, identificacion) values ('Andrés', 20, 'andres@autonoma.edu.co', '125');

insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0003', 'Doritos', 10, 3600);
insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0004', 'Zanahorias', 22, 7600);
insert into "Taller1".productos (codigo, nombre, stock, valor_unitario) values ('0005', 'Arroz', 41, 4200);

insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/04', 2, 2000, '0003', '123');
insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/05', 3, 10000, '0004', '124');
insert into "Taller1".pedidos (fecha, cantidad, valor_total, producto_id, cliente_id) values ('2024/08/06', 4, 2000, '0005', '125');

SAVEPOINT punto_restauracion;

update "Taller1".clientes set nombre='Julio', edad=5, correo='julio@autonoma.edu.co' where identificacion='125';
update "Taller1".clientes set nombre='Pedro', edad=32, correo='pedro@autonoma.edu.co' where identificacion='123';

update "Taller1".productos set nombre='Lentejas' where codigo='0005';
update "Taller1".productos set nombre='Detodito' where codigo='0003';

update "Taller1".pedidos set cantidad=200 where producto_id = '0002';
update "Taller1".pedidos set valor_total=1000 where producto_id ='0003';

delete from "Taller1".pedidos where producto_id='0001';

delete from "Taller1".clientes where identificacion='1056121086';

delete from "Taller1".productos where codigo='0001';
ROLLBACK TO SAVEPOINT punto_restauracion;
COMMIT;
SELECT * FROM clientes;