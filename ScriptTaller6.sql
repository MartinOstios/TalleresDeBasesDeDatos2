CREATE TABLE "Taller6".clientes (
	identificacion varchar NOT NULL,
	nombre varchar NULL,
	email varchar NULL,
	direccion varchar NULL,
	telefono varchar NULL,
	CONSTRAINT cliente_pk PRIMARY KEY (identificacion)
);

CREATE TYPE estado_servicio AS ENUM ('PAGO', 'NO PAGO', 'PENDIENTE PAGO')


CREATE TABLE "Taller6".servicios (
	codigo varchar NOT NULL,
	monto double precision NULL,
	cuota integer NULL,
	intereses double precision NULL,
	valor_total double precision NULL,
	cliente_id varchar NULL,
	tipo varchar NULL,
	estado estado_servicio NOT NULL,
	CONSTRAINT servicios_pk PRIMARY KEY (codigo),
	CONSTRAINT servicios_cliente_fk FOREIGN KEY (cliente_id) REFERENCES "Taller6".clientes(identificacion)
);


CREATE TABLE "Taller6".pagos (
	codigo_transaccion varchar NOT NULL,
	fecha_pago date NULL,
	total double precision NULL,
	servicio_id varchar NULL,
	CONSTRAINT pagos_pk PRIMARY KEY (codigo_transaccion),
	CONSTRAINT pagos_servicios_fk FOREIGN KEY (servicio_id) REFERENCES "Taller6".servicios(codigo)
);



CREATE OR REPLACE PROCEDURE poblar()
LANGUAGE plpgsql
AS $$

BEGIN
	FOR i IN 1..50 LOOP
		INSERT INTO "Taller6".clientes (identificacion, nombre, email, direccion, telefono) VALUES (i, 'Cliente ' || i, 'cliente' || i || '@gmail.com', 'Calle ' || i, '123-456-' || i );
	END LOOP;

	FOR i IN 1..50 LOOP
		FOR j IN 1..3 LOOP
			INSERT INTO "Taller6".servicios (codigo, monto, cuota, intereses, valor_total, cliente_id, tipo, estado) VALUES (i::varchar || j::varchar, i*j, j, i*j-12*j, j*j*5, i, 'Luz', 'PENDIENTE PAGO');
		END LOOP;
	END LOOP;
	
	FOR i IN 1..50 LOOP
		INSERT INTO "Taller6".pagos (codigo_transaccion, fecha_pago, total, servicio_id) VALUES (i, '2024/06/15', i*i*150, i*10+1);
	END LOOP;
END
$$;


CALL "Taller6".POBLAR();

CREATE OR REPLACE FUNCTION transacciones_total_mes (mes int, identificacion varchar )
RETURNS double precision AS 
$$
DECLARE 
	v_total_pago double precision := 0;
	v_total double precision :=0;
	v_id_servicio varchar; 	
BEGIN
	FOR v_id_servicio IN SELECT codigo FROM "Taller6".servicios WHERE cliente_id = identificacion
	LOOP
		FOR v_total IN SELECT total FROM "Taller6".pagos WHERE EXTRACT(MONTH FROM fecha_pago) = mes AND servicio_id = v_id_servicio
			LOOP
				v_total_pago := v_total_pago + v_total;
		END LOOP;
	END LOOP;	
	RETURN v_total_pago;
END;
$$
LANGUAGE plpgsql;

INSERT INTO "Taller6".pagos (codigo_transaccion, fecha_pago, total, servicio_id) VALUES ('1342', '2024/06/11', 17850, '152');
SELECT "Taller6".transacciones_total_mes(6,'15');


