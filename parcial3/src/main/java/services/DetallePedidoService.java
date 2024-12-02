package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import models.DetallePedido;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoService {
    private final MongoCollection<Document> collection;

    public DetallePedidoService(MongoDatabase database) {
        this.collection = database.getCollection("detalle_pedidos");
    }

    public void create(DetallePedido detallePedido) {
        Document doc = new Document("_id", detallePedido.getId())
                .append("pedido_id", detallePedido.getPedidoId())
                .append("producto_id", detallePedido.getProductoId())
                .append("cantidad", detallePedido.getCantidad())
                .append("precio_unitario", detallePedido.getPrecioUnitario());
        collection.insertOne(doc);
    }

    public DetallePedido findById(String id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return documentToDetallePedido(doc);
        }
        return null;
    }

    public List<DetallePedido> findAll() {
        List<DetallePedido> detalles = new ArrayList<>();
        for (Document doc : collection.find()) {
            detalles.add(documentToDetallePedido(doc));
        }
        return detalles;
    }

    public void update(DetallePedido detallePedido) {
        Document doc = new Document("pedido_id", detallePedido.getPedidoId())
                .append("producto_id", detallePedido.getProductoId())
                .append("cantidad", detallePedido.getCantidad())
                .append("precio_unitario", detallePedido.getPrecioUnitario());
        collection.updateOne(Filters.eq("_id", detallePedido.getId()), new Document("$set", doc));
    }

    public void delete(String id) {
        collection.deleteOne(Filters.eq("_id", id));
    }

    public List<DetallePedido> findByProductoId(String productoId) {
        List<DetallePedido> detalles = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("producto_id", productoId))) {
            detalles.add(documentToDetallePedido(doc));
        }
        return detalles;
    }

    private DetallePedido documentToDetallePedido(Document doc) {
        return new DetallePedido(
            doc.getString("_id"),
            doc.getString("pedido_id"),
            doc.getString("producto_id"),
            doc.getInteger("cantidad"),
            doc.getDouble("precio_unitario")
        );
    }
} 