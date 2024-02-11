package modelo.persistencia;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import modelo.entidad.Coche;
import modelo.persistencia.interfaces.DaoCoche;

/**
 * Clase que gestiona la persistencia de los coches en la base de datos MySQL
 * La clase implementa la interfaz DaoCoche
 * Persistencia significa que se encarga de dar de alta, baja, modificar, obtener y listar coches en la base de datos
 * La persistencia de los coches se hará en una tabla llamada coches
 * La tabla coches tendrá los siguientes campos: id, marca, modelo, fabricacion, km
 * La tabla coches tendrá un campo id que será la clave primaria y se generará de manera automática
 * 
 */
public class DaoCocheMySql implements DaoCoche{

	private Connection conexion;
	
	/**
	 * Método que abre la conexión con la BBDD
	 * 
	 * @return true en caso de que se haya abierto. False en caso de error con la
	 *         BBDD.
	 */
	public boolean abrirConexion(){
		
		Properties properties = new Properties();
		String url;
		String usuario;
		String password;

		try {
		    // Cargar el archivo de configuración
		    properties.load(new FileInputStream("config.properties"));

		    // Obtener los valores de la configuración
		    String dbUrl = properties.getProperty("db.url");
		    String dbName = properties.getProperty("db.dbname");
		    usuario = properties.getProperty("db.usuario");
		    password = properties.getProperty("db.contrasena");

		    // Concatenar la URL de la base de datos con el nombre de la base de datos
		    url = dbUrl + dbName;
		    
		} catch (IOException e) {
		    e.printStackTrace();
		    return false; // Indica que no se pudo establecer la conexión debido a un error de configuración
		}

		try {
		    conexion = DriverManager.getConnection(url, usuario, password);
		} catch (SQLException e) {
		    e.printStackTrace();
		    return false; // Indica que no se pudo establecer la conexión debido a un error de conexión a la base de datos
		}

		// La conexión se estableció correctamente
		return true;
	}
	
	/**
	 * Método que cierra la conexión con la BBDD
	 * 
	 * @return true en caso de que se haya cerrado. False en caso de error con la
	 *         BBDD.
	 */
	public boolean cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * Método que da de alta un coche en la base de datos
	 * 
	 * @param c el coche a dar de alta
	 * @return true en caso de que se haya dado de alta. False en caso de error con
	 *         la BBDD.
	 */
	@Override
	public boolean alta(Coche c) {
		if(!abrirConexion()){
			return false;
		}
		boolean alta = true;
		
		String query = "insert into coches (MARCA,MODELO,FABRICACION,KM) "
				+ " values(?,?,?,?)";
		try {
			//preparamos la query con valores parametrizables(?)
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, c.getMarca());
			ps.setString(2, c.getModelo());
			ps.setDouble(3, c.getFabricacion());
			ps.setDouble(4, c.getKm());
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0) {
				alta = false;
			}
		} catch (SQLException e) {
			System.out.println("alta -> Error al insertar: " + c);
			alta = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return alta;
	}

	/**
	 * Método que da de baja un coche de la base de datos a partir de su id
	 * 
	 * @param id del coche
	 * @return true en caso de que se haya dado de baja. False en caso de error con
	 *         la BBDD.
	 */
	@Override
	public boolean baja(int id) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean borrado = true;
		String query = "delete from coches where id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			//sustituimos la primera interrgante por la id
			ps.setInt(1, id);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				borrado = false;
		} catch (SQLException e) {
			System.out.println("baja -> No se ha podido dar de baja"
					+ " el id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return borrado; 
	}

	/**
	 * Método que modifica un coche en la base de datos
	 * 
	 * @param c el coche a modificar
	 * @return true en caso de que se haya modificado. False en caso de error con la
	 *         BBDD.
	 */
	@Override
	public boolean modificar(Coche c) {
		if(!abrirConexion()){
			return false;
		}
		boolean modificado = true;
		String query = "update COCHES set MARCA=?, MODELO=?, FABRICACION=?, KM=? WHERE ID=?";

		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, c.getMarca());
			ps.setString(2, c.getModelo());
            ps.setDouble(3, c.getFabricacion());
			ps.setDouble(4, c.getKm());
			ps.setInt(5, c.getId());
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				modificado = false;
			else
				modificado = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("modificar -> error al modificar el coche " + c);
			modificado = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return modificado;
	}

	/**
	 * Método que devuelve un coche a partir de su id
	 * 
	 * @param id del coche
	 * @return coche coche con el id pasado por parametro
	 */
	@Override
	public Coche obtener(int id) {
		if(!abrirConexion()){
			return null;
		}		
		Coche coche = null;
		
		String query = "SELECT id,marca,modelo,fabricacion,km FROM coches "
				+ "WHERE id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMarca(rs.getString(2));
				coche.setModelo(rs.getString(3));
				coche.setFabricacion(rs.getInt(4));
				coche.setKm(rs.getInt(5));
			}
		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener el coche con id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return coche;
	}

	/**
	 * Método que devuelve un listado completo de coches
	 * 
	 * @return List<Coche> listado de coches
	 */
	@Override
	public List<Coche> listar() {
		if(!abrirConexion()){
			return null;
		}		
		List<Coche> listaCoches = new ArrayList<>();
		
		String query = "select ID,MARCA,MODELO,FABRICACION,KM from coches";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Coche coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMarca(rs.getString(2));
				coche.setModelo(rs.getString(3));
				coche.setFabricacion(rs.getInt(4));
				coche.setKm(rs.getInt(5));
				
				listaCoches.add(coche);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los coches");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		return listaCoches;
	}
}
