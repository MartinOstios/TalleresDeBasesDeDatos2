package models;

import java.util.Date;

public class Reserva {
    private String _id;
    private Cliente cliente;
    private Habitacion habitacion;
    private Date fecha_entrada;
    private Date fecha_salida;
    private double total;
    private String estado_pago;
    private String metodo_pago;
    private Date fecha_reserva;

    public Reserva() {}

    public Reserva(String _id, Cliente cliente, Habitacion habitacion, 
                  Date fecha_entrada, Date fecha_salida, double total,
                  String estado_pago, String metodo_pago, Date fecha_reserva) {
        this._id = _id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
        this.total = total;
        this.estado_pago = estado_pago;
        this.metodo_pago = metodo_pago;
        this.fecha_reserva = fecha_reserva;
    }

    public String getId() { return _id; }
    public void setId(String _id) { this._id = _id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Habitacion getHabitacion() { return habitacion; }
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }

    public Date getFechaEntrada() { return fecha_entrada; }
    public void setFechaEntrada(Date fecha_entrada) { this.fecha_entrada = fecha_entrada; }

    public Date getFechaSalida() { return fecha_salida; }
    public void setFechaSalida(Date fecha_salida) { this.fecha_salida = fecha_salida; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEstadoPago() { return estado_pago; }
    public void setEstadoPago(String estado_pago) { this.estado_pago = estado_pago; }

    public String getMetodoPago() { return metodo_pago; }
    public void setMetodoPago(String metodo_pago) { this.metodo_pago = metodo_pago; }

    public Date getFechaReserva() { return fecha_reserva; }
    public void setFechaReserva(Date fecha_reserva) { this.fecha_reserva = fecha_reserva; }
} 