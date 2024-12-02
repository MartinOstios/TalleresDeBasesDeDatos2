package models;

public class Persona {
    private String nombre;
    private String correo;
    private int edad;
    private String ciudad;

    public Persona() {}

    public Persona(String nombre, String correo, int edad, String ciudad) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.ciudad = ciudad;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
} 