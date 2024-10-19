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
public class GuardarLibro {
    public static void ejecutar(Connection conn) {
        try {
            CallableStatement stmt = conn.prepareCall("call taller15.guardar_libro(?, ?, ?, ?)");
            stmt.setString(1, "0009");
            stmt.setString(2, "Libro 9");
            stmt.setString(3, "Autor 9");
            stmt.setInt(4, 2024);
            
            
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}