/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoxml;

import java.sql.*;

/**
 *
 * @author Martin
 */
public class ObtenerLibrosPorAnio {
     public static void ejecutar(Connection conn) {
        try {
            CallableStatement stmt = conn.prepareCall("select  taller15.obtener_libros_por_anio(?)");
            stmt.setInt(1, 2024);

            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                System.out.println(resultado.getString(1));
            }

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     }
}
