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

public class TransaccionesTotalMes {

    public static void ejecutar(Connection conn) {
        try {

            CallableStatement stmt = conn.prepareCall("{ call taller6.transacciones_total_mes(?,?)}");
            stmt.setInt(1, 8); // mes
            stmt.setString(2, "1"); // identificacion

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double total = rs.getDouble(1);
                System.out.println("Total transacciones: " + total);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
