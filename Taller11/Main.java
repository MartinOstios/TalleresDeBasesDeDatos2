/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package funcionesalmacenadas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author izibr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connPostgres = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "martin");

            ObtenerNominaEmpleadoPostgres.ejecutar(connPostgres);
            TotalPorContratoPostgres.ejecutar(connPostgres);

            connPostgres.close();
            
            
            // -------------------------------------------------------
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connOracle = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "martin");
            
            GenerarAuditoriaOracle.ejecutar(connOracle);
            SimularVentasMesOracle.ejecutar(connOracle);
            ObtenerNominaEmpleadoOracle.ejecutar(connOracle);
            TotalPorContratoOracle.ejecutar(connOracle);
            
            connOracle.close();
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
