package crearbbdd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CrearMySQL {
    static Properties properties = new Properties();
    String dbUrl;
    String dbName;
    String url;
    String usuario;
    String password;

    public static void main(String[] args) {
        CrearMySQL app = new CrearMySQL();
        app.crearBBDDYTabla();
    }
    
    /**
     * Verifica si existe una base de datos con el nombre especificado
     * @param nombreBaseDatos
     * @return true si existe, false en caso contrario
     */
    public boolean existeBaseDatos(String nombreBaseDatos) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        boolean existe = false;

        try {
            // Establecer la conexión con el servidor de base de datos
            connection = DriverManager.getConnection(dbUrl, usuario, password);

            // Crear una declaración
            statement = connection.createStatement();

            // Consulta para verificar si la base de datos existe
            String query = "SHOW DATABASES LIKE '" + nombreBaseDatos + "'";
            resultSet = statement.executeQuery(query);

            // Verificar si se encontró alguna coincidencia
            existe = resultSet.next();
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

        return existe;
    }

    public void crearBBDDYTabla() {
        // Cargar el archivo de configuración
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Obtener los valores de la configuración
        dbUrl = properties.getProperty("db.url");
        dbName = properties.getProperty("db.dbname");
        usuario = properties.getProperty("db.usuario");
        password = properties.getProperty("db.contrasena");

        // Concatenar la URL de la base de datos con el nombre de la base de datos
        this.url = dbUrl + dbName;

        Connection connection = null;
        Statement statement = null;
        try {
            // Establecer conexión
            connection = DriverManager.getConnection(this.dbUrl, usuario, password);
            statement = connection.createStatement();

            // Verificar si la base de datos ya existe
            boolean existe = existeBaseDatos(this.dbName);
			boolean databaseExists;
			if (existe) {
				System.out.println("La base de datos " + this.dbName + " ya existe.");
				databaseExists = true;
				return;
			} else {
				System.out.println("La base de datos " + this.dbName + " no existe.");
				databaseExists = false;
			}
			
            ResultSet resultSet = connection.getMetaData().getCatalogs();
/*            boolean databaseExists = false;
            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.contains(this.dbName)) {
                    databaseExists = true;
                    break;
                }
            }
            
  */
            resultSet.close();

            // Crear la base de datos si no existe
            if (!databaseExists) {
                statement.executeUpdate("CREATE DATABASE " + this.dbName);
                System.out.println("Base de datos " + this.dbName + " creada correctamente.");
            } else {
                System.out.println("La base de datos " + this.dbName + " ya existía.");
            }

            // Seleccionar la base de datos
            statement.executeUpdate("USE " + this.dbName);
            System.out.println("Base de datos " + this.dbName + " seleccionada correctamente.\n");

            // Crear la tabla 'coches' si no existe
            crearTablaSiNoExiste(statement, "coches",
                    "CREATE TABLE coches (id INT NOT NULL AUTO_INCREMENT, marca VARCHAR(20), modelo VARCHAR(20), fabricacion INT, km INT, PRIMARY KEY (id))");

            // Crear la tabla 'pasajeros' si no existe
            crearTablaSiNoExiste(statement, "pasajeros",
                    "CREATE TABLE pasajeros (id INT NOT NULL AUTO_INCREMENT, nombre VARCHAR(20), edad INT, peso INT, id_coche INT, PRIMARY KEY (id), FOREIGN KEY (id_coche) REFERENCES coches(id))");

            System.out.println("Base de datos y tablas creadas correctamente.");
        } catch (SQLException e) {
            System.out.println("La base de datos y las tablas ya existian: " + e.getMessage());
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
/** 
 * Método que crea una tabla si no existe
 * @param statement Sentencia SQL para ejecutar la creacion de la tabla
 * @param tableName Nombre de la tabla a crear verificando si existe
 * @param createTableQuery Sentencia SQL para crear la tabla
 * @throws SQLException
 */
    private void crearTablaSiNoExiste(Statement statement, String tableName, String createTableQuery) throws SQLException {
    	System.out.println("Nombre de la tabla a crear: " + tableName + "\nSentencia de creacion de Tabla: \n" + createTableQuery);
    	
        ResultSet tables = statement.executeQuery("SHOW TABLES LIKE '" + tableName + "'");
        if (!tables.next()) {
            statement.executeUpdate(createTableQuery);
            System.out.println("Tabla '" + tableName + "' creada correctamente. \n");
        } else {
            System.out.println("La tabla '" + tableName + "' ya existía.");
        }
    }
}
