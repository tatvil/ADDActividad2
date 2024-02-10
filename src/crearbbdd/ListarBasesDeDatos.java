package crearbbdd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListarBasesDeDatos {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establecer la conexión con el servidor de base de datos
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");

            // Crear una declaración
            statement = connection.createStatement();

            // Obtener todas las bases de datos
            resultSet = statement.executeQuery("SHOW DATABASES");

            // Imprimir el nombre de cada base de datos
            System.out.println("Bases de datos disponibles:");
            while (resultSet.next()) {
                String dbName = resultSet.getString(1);
                System.out.println("    "+dbName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar ResultSet, Statement y Connection
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
