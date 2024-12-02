package models;

public class Habitacion {
    private String tipo;
    private int numero;
    private double precio_noche;
    private int capacidad;
    private String descripcion;

    public Habitacion() {}

    public Habitacion(String tipo, int numero, double precio_noche, int capacidad, String descripcion) {
        this.tipo = tipo;
        this.numero = numero;
        this.precio_noche = precio_noche;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public double getPrecioNoche() { return precio_noche; }
    public void setPrecioNoche(double precio_noche) { this.precio_noche = precio_noche; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
} 