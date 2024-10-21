CREATE TABLE taller16.factura (
    codigo_punto_venta VARCHAR(3) PRIMARY KEY,
    descripcion JSONB
);


INSERT INTO taller16.factura (codigo_punto_venta, descripcion) 
VALUES (
    '001',
    '{
        "cliente": "Nombre 1",
        "identificacion": "1234",
        "direccion": "Dirección 1",
        "codigo": "FAC001",
        "total_descuento": 0,
        "total_factura": 32000,
        "items": [
            {
                "cantidad": 2,
                "valor": 32000,
                "producto": {
                    "nombre": "Producto 1",
                    "descripcion": "Descripción 1",
                    "precio": 16000,
                    "categorias": ["Categoría 1", "Categoría 2"]
                }
            }
        ]
    }'
);

-- 1
CREATE OR REPLACE PROCEDURE taller16.insertar_factura(codigo_punto_venta VARCHAR, descripcion JSONB)
LANGUAGE plpgsql
AS $$
BEGIN
    IF (descripcion->>'total_factura')::NUMERIC > 10000 THEN
        RAISE EXCEPTION 'El valor total de la factura no puede ser más de $10000';
    END IF;

    IF (descripcion->>'total_descuento')::NUMERIC > 50 THEN
        RAISE EXCEPTION 'El descuento máximo es de $50';
    END IF;

    INSERT INTO factura (codigo_punto_venta, descripcion)
    VALUES (codigo_punto_venta, descripcion);

END;
$$ 

CALL taller16.insertar_factura(
    '002',
    '{
        "cliente": "Pepito Perez",
        "identificacion": "1235",
        "direccion": "Dirección 2",
        "codigo": "FAC002",
        "total_descuento": 30,
        "total_factura": 10001,
        "items": [
            {
                "cantidad": 1,
                "valor": 10001,
                "producto": {
                    "nombre": "Producto 2",
                    "descripcion": "Descripción 2",
                    "precio": 10001,
                    "categorias": ["Categoría 1", "Categoría 2"]
                }
            }
        ]
    }'
);


-- 2
CREATE OR REPLACE PROCEDURE taller16.actualizar_factura(codigo_factura VARCHAR, nueva_descripcion JSONB)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE factura
    SET descripcion = nueva_descripcion
    WHERE descripcion->>'codigo' = codigo_factura;
END;
$$

CALL taller16.actualizar_factura(
    '002',
    '{
        "cliente": "Pepa",
        "identificacion": "1236",
        "direccion": "Dirección X",
        "codigo": "FAC002",
        "total_descuento": 20,
        "total_factura": 1000,
        "items": [
            {
                "cantidad": 2,
                "valor": 1000,
                "producto": {
                    "nombre": "Producto 4",
                    "descripcion": "Descripción 4",
                    "precio": 500,
                    "categorias": ["Categoría 4", "Categoría 5"]
                }
            }
        ]
    }'
);


-- 3
CREATE OR REPLACE FUNCTION taller16.obtener_nombre_cliente( identificacion_cliente VARCHAR)
RETURNS VARCHAR AS $$
DECLARE
    nombre_cliente VARCHAR;
BEGIN
    SELECT descripcion->>'cliente' INTO nombre_cliente FROM factura  WHERE descripcion->>'identificacion' = identificacion_cliente;
    RETURN nombre_cliente;
END;
$$ LANGUAGE plpgsql;

SELECT taller16.obtener_nombre_cliente('1236')


-- 4
CREATE FUNCTION taller16.obtener_facturas()
RETURNS TABLE (
    cliente TEXT, 
    identificacion TEXT, 
    codigo_factura TEXT, 
    total_descuento TEXT, 
    total_factura TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        descripcion->>'cliente' AS cliente,
        descripcion->>'identificacion' AS identificacion,
        descripcion->>'codigo' AS codigo_factura,
        descripcion->>'total_descuento' AS total_descuento,
        descripcion->>'total_factura' AS total_factura
    FROM factura;
END;
$$ LANGUAGE plpgsql;



SELECT  taller16.obtener_facturas()
DROP FUNCTION taller16.obtener_facturas()



-- 5
CREATE OR REPLACE FUNCTION taller16.obtener_productos_por_factura(codigo_factura VARCHAR)
RETURNS TABLE (
    nombre_producto TEXT, 
    descripcion_producto TEXT, 
    precio_producto NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        item->'producto'->>'nombre' AS nombre_producto,
        item->'producto'->>'descripcion' AS descripcion_producto,
        (item->'producto'->>'precio')::NUMERIC AS precio_producto
    FROM factura,
    jsonb_array_elements(descripcion->'items') AS item
    WHERE descripcion->>'codigo' = codigo_factura;
END;
$$ LANGUAGE plpgsql;

SELECT  taller16.obtener_productos_por_factura('FAC001')

DROP FUNCTION taller16.obtener_productos_por_factura(codigo_factura VARCHAR)


