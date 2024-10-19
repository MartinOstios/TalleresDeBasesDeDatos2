/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabajoxml;

import java.sql.*;

/**
 *
 * @author Martin
 */
public class TrabajoXML {

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "martin");
          
            //GuardarLibro.ejecutar(conn);
            //ActualizarLibro.ejecutar(conn);
            
            ObtenerAutorLibroPorISBN.ejecutar(conn);
            ObtenerAutorLibroPorTitulo.ejecutar(conn);
            ObtenerLibrosPorAnio.ejecutar(conn);
        
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
