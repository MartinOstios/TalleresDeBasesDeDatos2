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
public class GenerarAuditoria {

    public static void ejecutar(Connection conn) {
        try {
            
            CallableStatement stmt = conn.prepareCall("call taller5.generar_auditoria(?,?)");
            stmt.setDate(1, Date.valueOf("2024-10-15"));
            stmt.setDate(2, Date.valueOf("2024-10-20"));

            stmt.execute();
            
            stmt.close();
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
