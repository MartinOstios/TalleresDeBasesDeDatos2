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
public class TotalPorContratoOracle {

    public static void ejecutar(Connection conn) {
        try {

            CallableStatement stmt = conn.prepareCall("{ CALL FUNCIONES.TOTAL_POR_CONTRATO(?)}");

            stmt.setString(1, "4"); // tipo contrato    

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("v_nombre"));
                System.out.println("Fecha pago: " + rs.getString("v_fecha_pago"));
                System.out.println("Año: " + rs.getString("v_año"));
                System.out.println("Mes: " + rs.getString("v_mes"));
                System.out.println("Total devengado es: " + rs.getString("v_total_devengado"));
                System.out.println("Total deducciones es: " + rs.getString("v_total_deducciones"));
                System.out.println("Total  es: " + rs.getString("v_total"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
