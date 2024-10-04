/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcionesalmacenadas;

import java.sql.*;

/**
 *
 * @author Martin
 */
public class ObtenerNominaEmpleadoOracle {

    public static void ejecutar(Connection conn) {
        try {
            CallableStatement stmt = conn.prepareCall("{ call FUNCIONES.OBTENER_NOMINA_EMPLEADO(?,?,?)}");
            stmt.setString(1, "1");
            stmt.setInt(2, 10);
            stmt.setInt(3, 2024);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("v_nombre"));
                System.out.println("Total Devengado: " + rs.getString("v_total_devengado"));
                System.out.println("Total deducciones: " + rs.getString("v_total_deducciones"));
                System.out.println("Total: " + rs.getString("v_total"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
