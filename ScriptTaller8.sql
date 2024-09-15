CREATE TABLE "Excepciones1".usuarios (
	identificacion varchar NOT NULL,
	nombre varchar NULL,
	edad integer NULL,
	CONSTRAINT usuarios_pk PRIMARY KEY (identificacion)
);


CREATE TABLE "Excepciones1".facturas (
	producto varchar NOT NULL,
	fecha date NULL,
	cantidad integer NULL,
	id varchar NOT NULL,
	valor_unitario integer NULL,
	valor_total integer NULL,
	usuario_id varchar NULL,
	CONSTRAINT facturas_pk PRIMARY KEY (id),
	CONSTRAINT facturas_usuarios_fk FOREIGN KEY (usuario_id) REFERENCES "Excepciones1".usuarios(identificacion) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE OR REPLACE PROCEDURE "Excepciones1".poblar()
LANGUAGE plpgsql

AS $$
BEGIN
	FOR i IN 1..50 LOOP
		INSERT INTO "Excepciones1".usuarios (identificacion, nombre, edad) VALUES (i, 'Nombre ' || i, 20 + i);
	END LOOP;

	FOR i IN 1..25 LOOP
		INSERT INTO "Excepciones1".facturas (producto, fecha, cantidad, id, valor_unitario, valor_total, usuario_id) VALUES ('Producto ' || i, '2024/08/20', i * 10, i, 100 * i, 100 * 20 * i, i);
	END LOOP;
END
$$;


CALL "Excepciones1".poblar(); 
-- not_null_violation
create or replace procedure "Excepciones1".prueba_producto_vacio()
language plpgsql

as $$
BEGIN
	INSERT INTO "Excepciones1".facturas (producto, fecha, cantidad, id, valor_unitario, valor_total, usuario_id) VALUES ('Producto Prueba', '2024/08/20', 3, '1234', 4545, 67676, '1');
	INSERT INTO "Excepciones1".facturas (fecha, cantidad, id, valor_unitario, valor_total, usuario_id) VALUES ('2024/08/20', 3, '1235', 4545, 67676, '1');

EXCEPTION
	WHEN not_null_violation THEN
		-- No es necesario poner Rollback
		RAISE NOTICE 'Se intentó agregar una factura sin nombre de producto';
END
$$;


call "Excepciones1".prueba_producto_vacio();

-- unique_violation
create or replace procedure "Excepciones1".prueba_identificacion_unica()
language plpgsql

as $$
BEGIN
	INSERT INTO "Excepciones1".usuarios (identificacion, nombre, edad) VALUES (1, 'Nombre Prueba', 25);

EXCEPTION
	WHEN unique_violation THEN
		-- No es necesario poner Rollback
		RAISE NOTICE 'La identificación que se intentó agregar ya existe';
		INSERT INTO "Excepciones1".usuarios (identificacion, nombre, edad) VALUES (100, 'Nombre Prueba', 25);
		RAISE NOTICE 'Se agregó el usuario con una identificación válida';
END
$$;

call "Excepciones1".prueba_identificacion_unica();


-- foreign_key_violation
create or replace procedure "Excepciones1".prueba_producto_vacio()
language plpgsql

as $$
BEGIN
	INSERT INTO "Excepciones1".facturas (producto, fecha, cantidad, id, valor_unitario, valor_total, usuario_id) VALUES ('Producto Prueba', '2024/08/20', 3, '1234', 4545, 67676, '1');
	INSERT INTO "Excepciones1".facturas (producto, fecha, cantidad, id, valor_unitario, valor_total, usuario_id) VALUES ('Producto Prueba 2','2024/08/20', 3, '1235', 4545, 67676, '86');

EXCEPTION
	WHEN foreign_key_violation THEN
		-- No es necesario poner Rollback
		RAISE NOTICE 'La identificación del cliente ingresado no existe';
END
$$;

call "Excepciones1".prueba_producto_vacio();






