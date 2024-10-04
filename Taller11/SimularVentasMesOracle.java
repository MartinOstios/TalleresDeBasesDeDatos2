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
public class SimularVentasMesOracle {

    public static void ejecutar(Connection conn) {
        try {
            CallableStatement stmt = conn.prepareCall("{ CALL PROCEDIMIENTOS.SIMULAR_VENTAS_MES(); }");

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
