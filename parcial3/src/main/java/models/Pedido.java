package models;

import java.util.Date;

public class Pedido {
    private String _id;
    private String cliente;
    private Date fecha_pedido;
    private String estado;
    private double total;

    public Pedido() {}

    public Pedido(String _id, String cliente, Date fecha_pedido, String estado, double total) {
        this._id = _id;
        this.cliente = cliente;
        this.fecha_pedido = fecha_pedido;
        this.estado = estado;
        this.total = total;
    }

    public String getId() { return _id; }
    public void setId(String _id) { this._id = _id; }
    
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    
    public Date getFechaPedido() { return fecha_pedido; }
    public void setFechaPedido(Date fecha_pedido) { this.fecha_pedido = fecha_pedido; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
} 