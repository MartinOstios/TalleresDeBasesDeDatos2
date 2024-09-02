CREATE TABLE "Taller5".clientes (
	nombre varchar NULL,
	identificacion varchar NOT NULL,
	edad int NULL,
	correo varchar NOT NULL,
	CONSTRAINT clientes_pk PRIMARY KEY (identificacion)
);

CREATE TABLE "Taller5".productos (
	codigo varchar NOT NULL,
	nombre varchar NULL,
	stock int NULL,
	valor_unitario float8 NULL,
	CONSTRAINT productos_pk PRIMARY KEY (codigo)
);

CREATE TYPE estados_p AS ENUM ('PENDIENTE', 'ENTREGADO', 'BLOQUEADO');

CREATE TABLE "Taller5".facturas (
	id VARCHAR NOT NULL,
	fecha date NULL,
	cantidad int NULL,
	valor_total float8 NULL,
	producto_id varchar NOT NULL,
	cliente_id varchar NOT NULL,
	pedido_estado estados_p NULL,
	CONSTRAINT pedidos_pk PRIMARY KEY (id),
	CONSTRAINT pedidos_clientes_fk FOREIGN KEY (cliente_id) REFERENCES "Taller5".clientes(identificacion) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT pedidos_productos_fk FOREIGN KEY (producto_id) REFERENCES "Taller5".productos(codigo) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE "Taller5".auditorias (
	fecha_inicio date NULL,
	fecha_final date NULL,
	factura_id varchar NOT NULL,
	pedido_estado "Taller5"."estados_p" NULL,
	id serial NOT NULL,
	CONSTRAINT auditorias_pk PRIMARY KEY (id),
	CONSTRAINT auditorias_facturas_fk FOREIGN KEY (factura_id) REFERENCES "Taller5".facturas(id) ON DELETE CASCADE ON UPDATE CASCADE
);




INSERT INTO "Taller5".clientes (nombre, identificacion,edad,correo) VALUES ('Majho','1056121086',20,'Majho1456@gmail.com');
INSERT INTO "Taller5".productos (codigo, nombre, stock, valor_unitario) VALUES ('0001', 'Camisa', 120, 32000);
INSERT INTO "Taller5".productos (codigo, nombre, stock, valor_unitario) VALUES ('0002', 'Zapatos', 30, 40000);

-- Parte 1

CREATE OR REPLACE PROCEDURE calcular_stock_total()
LANGUAGE plpgsql
AS $$
DECLARE 
	total_stock INTEGER := 0;
	v_nombre VARCHAR;
	v_stock INTEGER;
BEGIN
	FOR v_nombre, v_stock IN SELECT nombre, stock FROM "Taller5".productos
	LOOP
		RAISE NOTICE 'El nombre del producto es: %', v_nombre;
		RAISE NOTICE 'El stock actual del producto es: %', v_stock;
		total_stock := total_stock + v_stock;
	END LOOP;
	RAISE NOTICE 'El stock total es de: %', total_stock;
END
$$;

CALL "Taller5".CALCULAR_STOCK_TOTAL();


-- Parte 2
CREATE OR REPLACE PROCEDURE generar_auditoria(fecha_inicio DATE, fecha_final DATE)
LANGUAGE plpgsql
AS $$
DECLARE 
	v_factura_id VARCHAR;
	v_pedido_estado estados_p;
	v_fecha DATE;

BEGIN
	FOR v_factura_id, v_pedido_estado, v_fecha IN SELECT id, pedido_estado, fecha FROM "Taller5".facturas
	LOOP
		IF v_fecha BETWEEN fecha_inicio AND fecha_final THEN
			INSERT INTO "Taller5".auditorias (fecha_inicio, fecha_final, factura_id, pedido_estado) VALUES (fecha_inicio, fecha_final, v_factura_id, v_pedido_estado);
			RAISE NOTICE 'Se agregó a la tabla auditorias la factura id: %', v_factura_id;
		END IF;
	END LOOP;
	
END
$$;

INSERT INTO "Taller5".facturas (id, fecha, cantidad, valor_total, producto_id, cliente_id, pedido_estado) values ('1','2024/08/04', 2, 2000, '0001', '1056121086', 'PENDIENTE');
INSERT INTO "Taller5".facturas (id, fecha, cantidad, valor_total, producto_id, cliente_id, pedido_estado) values ('2','2024/07/28', 5, 10000, '0001', '1056121086', 'PENDIENTE');
INSERT INTO "Taller5".facturas (id, fecha, cantidad, valor_total, producto_id, cliente_id, pedido_estado) values ('3','2024/08/10', 1, 1000, '0001', '1056121086', 'PENDIENTE');
SELECT * FROM "Taller5".facturas;

CALL "Taller5".GENERAR_AUDITORIA('2024/06/01', '2024/08/31');




-- Parte 3 --
CREATE OR REPLACE PROCEDURE simular_ventas_mes()
LANGUAGE plpgsql
AS $$
DECLARE
	v_dia INTEGER := 1;
	v_random INTEGER;
	v_random_codigo VARCHAR;
	v_identificacion VARCHAR;

BEGIN
	WHILE v_dia <= 30 LOOP
		FOR v_identificacion IN SELECT identificacion FROM "Taller5".clientes
		LOOP
			SELECT FLOOR(random() * (99999 - 10000 + 1) + 10000)::int INTO v_random;
			SELECT FLOOR(random() * (1000 - 10 + 1) + 10)::varchar INTO v_random_codigo;
		
			RAISE NOTICE 'La cantidad generada fue: %',v_random;
			RAISE NOTICE 'El código generado fue: %', v_random_codigo;
		
			
			INSERT INTO "Taller5".facturas (id, fecha, cantidad, valor_total, producto_id, cliente_id, pedido_estado) VALUES (v_random_codigo, '2024/09/02', v_random, 4500, '0001', v_identificacion, 'PENDIENTE');
		END LOOP;
		v_dia := v_dia + 1;
	END LOOP;
END
$$;

CALL "Taller5".SIMULAR_VENTAS_MES(); 





