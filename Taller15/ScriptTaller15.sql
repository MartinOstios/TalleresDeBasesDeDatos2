 CREATE TABLE taller15.libros (
    isbn VARCHAR(13) PRIMARY KEY,
    descripcion XML
);

CREATE OR REPLACE PROCEDURE taller15.guardar_libro(p_isbn VARCHAR, p_titulo VARCHAR,  p_autor VARCHAR, p_anio INT)
LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM taller15.libros WHERE isbn = p_isbn OR descripcion::TEXT LIKE '%' || p_titulo || '%') THEN
        INSERT INTO taller15.libros (isbn, descripcion) VALUES (p_isbn, XMLPARSE(CONTENT '<libro><titulo>' || p_titulo || '</titulo><autor>' || p_autor || '</autor><anio>'|| p_anio ||'</anio></libro>'));
    ELSE
        RAISE NOTICE 'ISBN o Título ya existe';
    END IF;
END;
$$;

CALL taller15.guardar_libro('0001', 'Libro 1', 'Autor 1', 2024)
CALL taller15.guardar_libro('0002', 'Libro 2', 'Autor 2', 2023)
CALL taller15.guardar_libro('0003', 'Libro 3', 'Autor 3', 2024)
CALL taller15.guardar_libro('0004', 'Libro 4', 'Autor 4', 2024)
CALL taller15.guardar_libro('0005', 'Libro 4', 'Autor 4', 2024)

CREATE OR REPLACE PROCEDURE taller15.actualizar_libro(p_isbn VARCHAR, p_titulo VARCHAR,  p_autor VARCHAR, p_anio INT)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE taller15.libros SET descripcion = XMLPARSE(CONTENT '<libro><titulo>' || p_titulo || '</titulo><autor>' || p_autor || '</autor><anio>'|| p_anio ||'</anio></libro>')  WHERE isbn = p_isbn;
END;
$$ 

CALL taller15.actualizar_libro('0001','Libro 10','Autor 1', 2024)


CREATE OR REPLACE FUNCTION taller15.obtener_autor_libro_por_isbn(p_isbn VARCHAR)
RETURNS VARCHAR AS $$
DECLARE
    autor VARCHAR;
BEGIN
    SELECT xpath('//autor/text()', descripcion) INTO autor FROM taller15.libros WHERE isbn = p_isbn;
    RETURN autor;
END;
$$ LANGUAGE plpgsql;

SELECT taller15.obtener_autor_libro_por_isbn('0001')

CREATE OR REPLACE FUNCTION taller15.obtener_autor_libro_por_titulo(p_titulo VARCHAR)
RETURNS VARCHAR AS $$
DECLARE
    autor VARCHAR;
BEGIN
    SELECT xpath('//autor/text()', descripcion) INTO autor FROM taller15.libros  WHERE descripcion::TEXT LIKE '%' || p_titulo || '%';
    RETURN autor;
END;
$$ LANGUAGE plpgsql;

SELECT  taller15.obtener_autor_libro_por_titulo('Libro 10')

CREATE OR REPLACE FUNCTION taller15.obtener_libros_por_anio(p_anio INT)
RETURNS TABLE(isbn VARCHAR, titulo VARCHAR, autor VARCHAR, anio INT) AS $$
BEGIN
    RETURN QUERY
    SELECT l.isbn AS isbn, xm.titulo AS titulo, xm.autor AS autor, xm.anio as anio
    FROM taller15.libros l,
    XMLTABLE(
        '//libro' 
        PASSING l.descripcion
        COLUMNS 
            titulo VARCHAR PATH '//titulo',
            autor  VARCHAR PATH '//autor',
            anio   INT PATH '//anio'
    ) AS xm
    WHERE xm.anio = p_anio;
END;
$$ LANGUAGE plpgsql;

SELECT  taller15.obtener_libros_por_anio(2024)

