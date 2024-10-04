/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procedimientosalmacenados;


import java.sql.*;

/**
 *
 * @author izibr
 */
public class ServiciosNoPagados {

    public static void ejecutar(Connection conn) {
        try {

            CallableStatement stmt = conn.prepareCall("{ call taller6.no_pagados_mes(?)}");
            stmt.setInt(1, 8); // mes

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double total = rs.getDouble(1);
                System.out.println("Servicios no pagados: " + total);
            }
            rs.close();
            stmt.close();
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
