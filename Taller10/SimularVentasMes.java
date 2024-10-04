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
public class SimularVentasMes {

    public static void ejecutar(Connection conn) {
        try {
            CallableStatement stmt = conn.prepareCall("call taller5.simular_ventas_mes()");

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
