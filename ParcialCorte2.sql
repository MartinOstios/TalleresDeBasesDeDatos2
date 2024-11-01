CREATE TABLE parcial.usuarios (
	id varchar NOT NULL,
	nombre varchar NULL,
	direccion varchar NULL,
	email varchar NULL,
	fecha_registro date NULL,
	estado varchar NULL,
	CONSTRAINT usuarios_pk PRIMARY KEY (id)
);


CREATE TABLE parcial.tarjetas (
	id varchar NOT NULL,
	numero_tarjeta varchar NULL,
	fecha_expiracion date NULL,
	cvv int NULL,
	tipo_tarjeta varchar NULL,
	CONSTRAINT tarjetas_pk PRIMARY KEY (id)
);

CREATE TABLE parcial.productos (
	id varchar NOT NULL,
	codigo_producto varchar NULL,
	nombre varchar NULL,
	categoria varchar NULL,
	porcentaje_impuesto float4 NULL,
	precio float4 NULL,
	CONSTRAINT productos_pk PRIMARY KEY (id)
);

CREATE TABLE parcial.pagos (
	id varchar NOT NULL,
	codigo_pago varchar NULL,
	fecha date NULL,
	estado varchar NULL,
	monto float4 NULL,
	producto_id varchar NULL,
	tarjeta_id varchar NULL,
	usuario_id varchar NULL,
	CONSTRAINT pagos_pk PRIMARY KEY (id),
	CONSTRAINT pagos_productos_fk FOREIGN KEY (producto_id) REFERENCES parcial.productos(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT pagos_tarjetas_fk FOREIGN KEY (tarjeta_id) REFERENCES parcial.tarjetas(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT pagos_usuarios_fk FOREIGN KEY (usuario_id) REFERENCES parcial.usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE parcial.comprobantes_pago (
	id varchar NOT NULL,
	detalle_xml xml NULL,
	detalle_json jsonb NULL,
	CONSTRAINT comprobantes_pago_pk PRIMARY KEY (id)
);



-- Primera pregunta parte 1
CREATE OR REPLACE FUNCTION parcial.obtener_pagos(v_usuario_id VARCHAR, v_fecha DATE)
RETURNS TABLE (
	codigo_pago VARCHAR,
	nombre_producto VARCHAR,
	monto FLOAT4,
	estado VARCHAR
)

AS $$
BEGIN
	RETURN QUERY
	SELECT pa.codigo_pago AS codigo_pago, pr.nombre AS nombre_producto, pa.monto AS monto, pa.estado AS estado 
	FROM parcial.pagos pa 
	JOIN parcial.productos pr ON pa.producto_id = pr.id
	WHERE pa.usuario_id = v_usuario_id AND pa.fecha = v_fecha;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION obtener_pagos(character varying,date)


INSERT INTO parcial.usuarios
(id, nombre, direccion, email, fecha_registro, estado)
VALUES('1', 'Pepe', 'Calle 1', 'pepe@gmail.com', '11/1/2024', 'activo');

INSERT INTO parcial.tarjetas
(id, numero_tarjeta, fecha_expiracion, cvv, tipo_tarjeta)
VALUES('10', '1234', '11/1/2025', 789, 'visa');

INSERT INTO parcial.productos
(id, codigo_producto, nombre, categoria, porcentaje_impuesto, precio)
VALUES('100', '9999', 'Arroz', 'Alimentos', 0.19, 3000);

INSERT INTO parcial.pagos
(id, codigo_pago, fecha, estado, monto, producto_id, tarjeta_id, usuario_id)
VALUES('1000', '5784', '11/1/2024', 'exitoso', 3400, '100', '10', '1');

INSERT INTO parcial.pagos
(id, codigo_pago, fecha, estado, monto, producto_id, tarjeta_id, usuario_id)
VALUES('1001', '5785', '11/1/2024', 'exitoso', 800, '100', '10', '1');

SELECT parcial.obtener_pagos('1', '2024-11-01');


-- Primera pregunta parte 2
CREATE OR REPLACE FUNCTION parcial.obtener_tarjetas_usuario(v_usuario_id VARCHAR)
RETURNS TABLE (
	nombre_usuario VARCHAR,
	email VARCHAR,
	numero_tarjeta VARCHAR,
	cvv INT,
	tipo_tarjeta VARCHAR
)

AS $$

BEGIN
	RETURN QUERY
	SELECT us.nombre, us.email, ta.numero_tarjeta, ta.cvv, ta.tipo_tarjeta
	FROM parcial.pagos pa
	JOIN parcial.usuarios us ON pa.usuario_id = us.id
	JOIN parcial.tarjetas ta ON pa.tarjeta_id = ta.id
	WHERE pa.usuario_id = v_usuario_id AND pa.monto > 1000;
END;
$$ LANGUAGE plpgsql;

SELECT parcial.obtener_tarjetas_usuario('1');



-- Segunda pregunta punto 1
CREATE OR REPLACE FUNCTION obtener_tarjetas_cursor(v_usuario_id VARCHAR)
RETURNS VARCHAR
AS $$
DECLARE
	v_cursor CURSOR FOR 
		SELECT ta.numero_tarjeta, ta.fecha_expiracion, us.nombre, us.email
		FROM parcial.pagos pa
		JOIN parcial.usuarios us ON pa.usuario_id = us.id
		JOIN parcial.tarjetas ta ON pa.tarjeta_id = ta.id
		WHERE pa.usuario_id = v_usuario_id;
	v_numero_tarjeta VARCHAR;
	v_fecha_expiracion DATE;
	v_nombre VARCHAR;
	v_email VARCHAR;
	

BEGIN
	OPEN v_cursor;
	LOOP
		FETCH v_cursor INTO v_numero_tarjeta, v_fecha_expiracion, v_nombre, v_email;
		EXIT WHEN NOT FOUND;
		RETURN 'Numero: ' || v_numero_tarjeta || 'Expiración: ' || v_fecha_expiracion || 'Nombre: ' || v_nombre || 'Email: ' || v_email;
	END LOOP;
	CLOSE v_cursor;
	RETURN 'No encontrado';
END;
$$ LANGUAGE plpgsql;


SELECT parcial.obtener_tarjetas_cursor('1');

-- Segunda pregunta punto 2
CREATE OR REPLACE FUNCTION obtener_pagos_menores(v_fecha DATE)
RETURNS VARCHAR
AS $$
DECLARE
	v_cursor CURSOR FOR 
		SELECT 
		pa.monto, pa.estado,
		pr.nombre, pr.porcentaje_impuesto,
		us.nombre, us.direccion, us.email
		
		FROM parcial.pagos pa
		JOIN parcial.productos pr ON pa.producto_id = pr.id
		JOIN parcial.usuarios us ON pa.usuario_id = us.id
		WHERE pa.fecha = v_fecha;

	v_monto FLOAT;
	v_estado VARCHAR;
	v_nombre_producto VARCHAR;
	v_porcentaje_impuesto FLOAT;
	v_nombre_usuario VARCHAR;
	v_direccion VARCHAR;
	v_email VARCHAR;
	

BEGIN
	OPEN v_cursor;
	LOOP
		FETCH v_cursor INTO v_monto, v_estado, v_nombre_producto, v_porcentaje_impuesto, v_nombre_usuario, v_direccion, v_email;
		EXIT WHEN NOT FOUND;
		
		IF v_monto < 1000 THEN	
			RETURN 'Monto: ' || v_monto || ' Estado: ' || v_estado || ' Nombre producto: ' || v_nombre_producto || ' Porcentaje impuesto: ' || v_porcentaje_impuesto || ' Nombre usuario: ' || v_nombre_usuario || ' Dirección: ' || v_direccion || ' Email: ' || v_email;
		END IF;
	END LOOP;
	CLOSE v_cursor;

	RETURN 'No encontrado';
END;
$$ LANGUAGE plpgsql;

SELECT parcial.obtener_pagos_menores('2024-11-01');


CREATE OR REPLACE PROCEDURE parcial.guardar_xml(v_id VARCHAR, v_detalle_xml VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
	INSERT INTO parcial.comprobantes_pago (id, detalle_xml) VALUES (v_id, XMLPARSE(CONTENT v_detalle_xml));
END;
$$;





CALL parcial.guardar_xml('123', '<pago><codigoPago>123</codigoPago><nombreUsuario>Daniel</nombreUsuario><numeroTarjeta>1412</numeroTarjeta><nombreProducto>Panela</nombreProducto><montoPago>4500</montoPago></pago>');


CREATE OR REPLACE PROCEDURE parcial.guardar_json(v_id VARCHAR, v_detalle_json JSONB)
LANGUAGE plpgsql
AS $$
BEGIN 
	INSERT INTO parcial.comprobantes_pago (id, detalle_json) VALUES (v_id, v_detalle_json);
END;
$$;

CALL parcial.guardar_json('125', 
'{
	"emailUsuario": "antonio@gmail.com",
	"numeroTarjeta": "3466",
	"codigoProducto": "432",
	"codigoPago": "123",
	"montoPago": "2500"
}');


-- Pregunta 3 punto 1
CREATE OR REPLACE FUNCTION parcial.validar_precio()
RETURNS TRIGGER
AS $$
BEGIN
	IF NEW.precio < 0 OR NEW.precio > 20000 THEN
		RAISE EXCEPTION 'Precio inválido';
	END IF;
	
	IF NEW.porcentaje_impuesto < 0.01 OR NEW.porcentaje_impuesto > 0.2 THEN
		RAISE EXCEPTION 'Porcentaje inválido';	
	END IF; 

	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER validaciones_producto
BEFORE INSERT ON parcial.productos
FOR EACH ROW
EXECUTE FUNCTION parcial.validar_precio()



-- Pregunta 3 punto 2


CREATE OR REPLACE PROCEDURE parcial.insertar_json_xml()
LANGUAGE plpgsql;

BEGIN
	
END;
$$;



CREATE OR REPLACE TRIGGER trigger_xml
AFTER INSERT ON parcial.pagos
FOR EACH ROW
EXECUTE FUNCTION parcial.insertar_json_xml()


-- Pregunta 4 parte 1
CREATE SEQUENCE seq_codigo_producto
	START WITH 5
	INCREMENT BY 5
	
-- Pregunta 4 parte 2
CREATE SEQUENCE seq_codigo_pago
	START WITH 1
	INCREMENT BY 100

-- Pregunta 4 parte 3
	
CREATE OR REPLACE FUNCTION obtener_info_xml(p_id VARCHAR)
RETURNS VARCHAR
AS $$
DECLARE
	v_usuario VARCHAR;
	v_producto VARCHAR;
	v_monto_pago VARCHAR;

BEGIN
	SELECT xpath('//nombreUsuario/text()', detalle_xml) INTO v_usuario FROM parcial.comprobantes_pago WHERE id = p_id;
	SELECT xpath('//nombreProducto/text()', detalle_xml) INTO v_producto FROM parcial.comprobantes_pago WHERE id = p_id;
	SELECT xpath('//montoPago/text()', detalle_xml) INTO v_monto_pago FROM parcial.comprobantes_pago WHERE id = p_id;
	RETURN 'Usuario: ' || v_usuario || ' Producto: ' || v_producto || ' Monto pago: ' || v_monto_pago;

END;
$$ LANGUAGE plpgsql;

SELECT parcial.obtener_info_xml('123');


-- Pregunta 4 parte 4

CREATE OR REPLACE FUNCTION obtener_info_json(p_id VARCHAR)
RETURNS VARCHAR
AS $$
DECLARE
	v_email_usuario VARCHAR;
	v_codigo_producto VARCHAR;
	v_monto_pago VARCHAR;

BEGIN
	SELECT detalle_json->'emailUsuario' INTO v_email_usuario FROM parcial.comprobantes_pago WHERE id = p_id;
	SELECT detalle_json->'codigoProducto' INTO v_codigo_producto FROM parcial.comprobantes_pago WHERE id = p_id;
	SELECT detalle_json->'montoPago' INTO v_monto_pago FROM parcial.comprobantes_pago WHERE id = p_id;
	RETURN 'Email: ' || v_email_usuario || ' Codigo producto: ' || v_codigo_producto || ' Monto pago: ' || v_monto_pago;
END;
$$ LANGUAGE plpgsql;


SELECT parcial.obtener_info_json('125');
