package crearbbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearMySQL {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "AADActividad2";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        CrearMySQL app = new CrearMySQL();
        app.crearBBDDYTabla();
    }

    public void crearBBDDYTabla() {
        Connection connection = null;
        Statement statement = null;
        try {
            // Establecer conexión
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            
            // Crear la base de datos si no existe
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            
            // Seleccionar la base de datos
            statement.executeUpdate("USE " + DB_NAME);
            
            // Crear la tabla 'coches'
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS coches ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "marca VARCHAR(20), "
                    + "modelo VARCHAR(20), "
                    + "fabricacion INT, "
                    + "km INT, "
                    + "PRIMARY KEY (id))");
            
            // Crear la tabla pasajeros
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS pasajeros (" 
					+ "id INT NOT NULL AUTO_INCREMENT, "
					+ "nombre VARCHAR(20), " 
					+ "edad INT, " 
					+ "peso INT, " 
					+ "id_coche INT, "
					+ "PRIMARY KEY (id), "
					+ "FOREIGN KEY (id_coche) REFERENCES coches(id))");
            
            System.out.println("Base de datos y tabla creadas correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos y la tabla: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}