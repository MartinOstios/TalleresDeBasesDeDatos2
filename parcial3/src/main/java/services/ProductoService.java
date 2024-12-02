package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import models.Producto;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final MongoCollection<Document> collection;

    public ProductoService(MongoDatabase database) {
        this.collection = database.getCollection("productos");
    }

    public void create(Producto producto) {
        Document doc = new Document("_id", producto.getId())
                .append("nombre", producto.getNombre())
                .append("descripcion", producto.getDescripcion())
                .append("precio", producto.getPrecio())
                .append("stock", producto.getStock());
        collection.insertOne(doc);
    }

    public Producto findById(String id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return documentToProducto(doc);
        }
        return null;
    }

    public List<Producto> findAll() {
        List<Producto> productos = new ArrayList<>();
        for (Document doc : collection.find()) {
            productos.add(documentToProducto(doc));
        }
        return productos;
    }

    public void update(Producto producto) {
        Document doc = new Document("nombre", producto.getNombre())
                .append("descripcion", producto.getDescripcion())
                .append("precio", producto.getPrecio())
                .append("stock", producto.getStock());
        collection.updateOne(Filters.eq("_id", producto.getId()), new Document("$set", doc));
    }

    public void delete(String id) {
        collection.deleteOne(Filters.eq("_id", id));
    }

    public List<Producto> findByPrecioMayorA(double precio) {
        List<Producto> productos = new ArrayList<>();
        for (Document doc : collection.find(Filters.gt("precio", precio))) {
            productos.add(documentToProducto(doc));
        }
        return productos;
    }

    private Producto documentToProducto(Document doc) {
        return new Producto(
            doc.getString("_id"),
            doc.getString("nombre"),
            doc.getString("descripcion"),
            doc.getDouble("precio"),
            doc.getInteger("stock")
        );
    }
} 