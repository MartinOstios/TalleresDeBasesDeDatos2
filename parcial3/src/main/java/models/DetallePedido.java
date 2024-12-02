package models;

public class DetallePedido {
    private String _id;
    private String pedido_id;
    private String producto_id;
    private int cantidad;
    private double precio_unitario;

    public DetallePedido() {}

    public DetallePedido(String _id, String pedido_id, String producto_id, int cantidad, double precio_unitario) {
        this._id = _id;
        this.pedido_id = pedido_id;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public String getId() { return _id; }
    public void setId(String _id) { this._id = _id; }
    
    public String getPedidoId() { return pedido_id; }
    public void setPedidoId(String pedido_id) { this.pedido_id = pedido_id; }
    
    public String getProductoId() { return producto_id; }
    public void setProductoId(String producto_id) { this.producto_id = producto_id; }
    
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    
    public double getPrecioUnitario() { return precio_unitario; }
    public void setPrecioUnitario(double precio_unitario) { this.precio_unitario = precio_unitario; }
} 