package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import models.Pedido;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private final MongoCollection<Document> collection;

    public PedidoService(MongoDatabase database) {
        this.collection = database.getCollection("pedidos");
    }

    public void create(Pedido pedido) {
        Document doc = new Document("_id", pedido.getId())
                .append("cliente", pedido.getCliente())
                .append("fecha_pedido", pedido.getFechaPedido())
                .append("estado", pedido.getEstado())
                .append("total", pedido.getTotal());
        collection.insertOne(doc);
    }

    public Pedido findById(String id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return documentToPedido(doc);
        }
        return null;
    }

    public List<Pedido> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
        for (Document doc : collection.find()) {
            pedidos.add(documentToPedido(doc));
        }
        return pedidos;
    }

    public void update(Pedido pedido) {
        Document doc = new Document("cliente", pedido.getCliente())
                .append("fecha_pedido", pedido.getFechaPedido())
                .append("estado", pedido.getEstado())
                .append("total", pedido.getTotal());
        collection.updateOne(Filters.eq("_id", pedido.getId()), new Document("$set", doc));
    }

    public void delete(String id) {
        collection.deleteOne(Filters.eq("_id", id));
    }
    public List<Pedido> findByTotalMayorA(double total) {
        List<Pedido> pedidos = new ArrayList<>();
        for (Document doc : collection.find(Filters.gt("total", total))) {
            pedidos.add(documentToPedido(doc));
        }
        return pedidos;
    }

    private Pedido documentToPedido(Document doc) {
        return new Pedido(
            doc.getString("_id"),
            doc.getString("cliente"),
            doc.getDate("fecha_pedido"),
            doc.getString("estado"),
            doc.getDouble("total")
        );
    }
} 