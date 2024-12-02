package models;

public class Producto {
    private String _id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;

    public Producto() {}

    public Producto(String _id, String nombre, String descripcion, double precio, int stock) {
        this._id = _id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public String getId() { return _id; }
    public void setId(String _id) { this._id = _id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
} 