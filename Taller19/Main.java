package com.uam;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Updates.set;
//import static com.mongodb.client.model.Sorts.ascending;

public class Main {
    public static void main(String[] args) {
        MongoCollection<Document> collection = null;
        /**
        collection = ConexionMongo.setCollection("categorias");
        Document categoria1 = new Document("categoria_id", 1)
                .append("nombre_categoria", "Ropa");
        Document categoria2 = new Document("categoria_id", 2)
                .append("nombre_categoria", "Electrónica");
        Document categoria3 = new Document("categoria_id", 3)
                .append("nombre_categoria", "Hogar");

        collection.insertMany(Arrays.asList(categoria1, categoria2, categoria3));

        collection = ConexionMongo.setCollection("comentarios");
        Document comentario1 = new Document("comentario_id", 1)
                .append("texto", "Muy cómoda")
                .append("cliente", "Juan");
        Document comentario2 = new Document("comentario_id", 2)
                .append("texto", "Buena calidad")
                .append("cliente", "Ana");
        Document comentario3 = new Document("comentario_id", 3)
                .append("texto", "Excelente precio")
                .append("cliente", "Luis");

        collection.insertMany(Arrays.asList(comentario1, comentario2, comentario3));

        collection = ConexionMongo.setCollection("productos");
        Document producto1 = new Document("producto_id", 1)
                .append("nombre", "camiseta")
                .append("descripcion", "Camiseta de algodon")
                .append("precio", 20)
                .append("categoria", categoria1)
                .append("comentarios", Arrays.asList(comentario1, comentario2));
        Document producto2 = new Document("producto_id", 2)
                .append("nombre", "laptop")
                .append("descripcion", "Laptop de alta gama")
                .append("precio", 1500)
                .append("categoria", categoria2)
                .append("comentarios", Arrays.asList(comentario3));
        Document producto3 = new Document("producto_id", 3)
                .append("nombre", "silla")
                .append("descripcion", "Silla ergonómica")
                .append("precio", 100)
                .append("categoria", categoria3)
                .append("comentarios", Arrays.asList(comentario1, comentario3));

        collection.insertMany(Arrays.asList(producto1, producto2, producto3));

        */
        
        collection = ConexionMongo.setCollection("productos");
        // Actualizar dos productos
        //collection.updateOne(eq("producto_id", 1), set("descripcion", "Camiseta de algodon de alta calidad"));
        //collection.updateOne(eq("producto_id", 2), set("descripcion", "Laptop de alta gama con 16GB de RAM"));

        // Eliminar un producto
        //collection.deleteOne(eq("producto_id", 3));

        // Actualizar un comentario
        //collection.updateOne(eq("comentarios.comentario_id", 1), set("comentarios.$.texto", "Muy cómoda y fresca"));

        // Eliminar un comentario
        //collection.updateOne(eq("comentarios.comentario_id", 2), set("comentarios", Arrays.asList()));

        // Actualizar dos categorías
        //collection.updateOne(eq("categoria.categoria_id", 1), set("categoria.nombre_categoria", "Ropa de moda"));
        //collection.updateOne(eq("categoria.categoria_id", 2), set("categoria.nombre_categoria", "Electrónica de consumo"));

        // Eliminar una categoría
        //collection.updateOne(eq("categoria.categoria_id", 3), set("categoria", new Document()));
        
        MongoCursor<Document> cursor = collection.find(gt("precio", 10)).iterator();
        while(cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }

        MongoCursor<Document> cursor2 = collection.find(and(eq("categoria.nombre_categoria", "Ropa"), gt("precio", 50))).iterator();
        while(cursor2.hasNext()) {
            System.out.println(cursor2.next().toJson());
        }

    }
}