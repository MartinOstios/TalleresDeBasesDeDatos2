package code;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class Neo4jConnection {
    
    private static Driver driver;

    public static void connect() {
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "martin123"));
    }

    public static void disconnect() {
        driver.close();
    }

    public static Driver getDriver() {
        return driver;
    }
}
