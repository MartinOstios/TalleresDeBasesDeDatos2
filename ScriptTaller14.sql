

CREATE TABLE secuencias.factura (
	id varchar NOT NULL,
	codigo serial NOT NULL,
	cliente varchar NOT NULL,
	producto varchar NOT NULL,
	descuento float4 NOT NULL,
	valor_total double PRECISION NOT NULL,
	numero_fe serial NOT NULL,
	CONSTRAINT factura_pk PRIMARY KEY (id)
);

CREATE SEQUENCE codigo_facturacion
	START WITH 1
	INCREMENT BY 1;
	
CREATE SEQUENCE codigo_fe
	START WITH 100
	INCREMENT BY 100;
	
CREATE OR REPLACE PROCEDURE secuencias.poblar ()
LANGUAGE plpgsql
AS $$
BEGIN 
	FOR i IN 1..10 LOOP
		INSERT INTO secuencias.factura (id, codigo,cliente,producto,descuento,valor_total,numero_fe) VALUES ('Factura: ' || i, nextval('codigo_facturacion'), 'Cliente ' || i,'Producto ' || i, i*0.05, i*4, nextval('codigo_fe'));
	END LOOP;
END
$$;

CALL secuencias.poblar();