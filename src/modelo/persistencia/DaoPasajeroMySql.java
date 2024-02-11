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
import modelo.entidad.Pasajero;
import modelo.persistencia.interfaces.DaoCoche;
import modelo.persistencia.interfaces.DaoPasajero;

/**
 * Clase que gestiona la persistencia de los pasajeros en la base de datos MySQL
 * La clase implementa la interfaz DaoPasajero 
 * Persistencia significa que seencarga de dar de alta, baja, modificar, obtener y listar pasajeros en la base de datos 
 * La persistencia de los pasajeros se hará en una tabla llamada pasajeros 
 * La tabla pasajeros tendrá los siguientes campos: id, nombre, edad, peso, id_coche 
 * El id_coche es una clave foranea que hace referencia al id de la tabla coches
 * La tabla pasajeros tendrá un campo id que será la clave primaria y se generará de manera automática
 * 
 */
public class DaoPasajeroMySql implements DaoPasajero {
	private Connection conexion;
	
	public boolean abrirConexion(){
//		String url = "jdbc:mysql://localhost:3306/AADActividad2";
//		String usuario = "root";
//		String password = "";
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
	
	public boolean cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	@Override
	public boolean alta(Pasajero p) {
		if(!abrirConexion()){
			return false;
		}
		boolean alta = true;
		
		String query = "insert into pasajeros (nombre,edad,peso,id_coche) "
				+ " values(?,?,?,?)";
		try {
			//preparamos la query con valores parametrizables(?)
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, p.getNombre());
			ps.setInt(2, p.getEdad());
			ps.setInt(3, p.getPeso());
			ps.setObject(4, p.getCoche());
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0) {
				alta = false;
			}
		} catch (SQLException e) {
			System.out.println("alta -> Error al insertar: " + p);
			alta = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return alta;
	}

	@Override
	public boolean baja(int id) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean borrado = true;
		String query = "delete from pasajeros where id = ?";
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

	@Override
	public boolean modificar(Pasajero p) {
		if(!abrirConexion()){
			return false;
		}
		boolean modificado = true;
		String query = "update PASAJEROS set NOMBRE=?, EDAD=?, PESO=?, ID_COCHE=? WHERE ID=?";

		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, p.getNombre());
			ps.setInt(2, p.getEdad());
			ps.setInt(3, p.getPeso());
			ps.setObject(4, p.getCoche().getId());
			ps.setInt(5, p.getId());
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				modificado = false;
			else
				modificado = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("modificar -> error al modificar " + p);
			modificado = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return modificado;
	}

	@Override
	public Pasajero obtener(int id) {
		if(!abrirConexion()){
			return null;
		}		
		Pasajero pasajero = null;
		Coche coche = null;
		
		String query = "SELECT p.id,p.nombre,p.edad,p.peso,p.id_coche,c.marca,c.modelo,c.fabricacion,c.km"
				+ " FROM pasajeros AS p"
				+ " LEFT JOIN coches AS c ON p.id_coche = c.id"
				+ " WHERE p.id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pasajero = new Pasajero();
				coche = new Coche();
				
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getInt(4));
				
				coche.setId(rs.getInt(5));
				coche.setMarca(rs.getString(6));
				coche.setModelo(rs.getString(7));
				coche.setFabricacion(rs.getInt(8));
				coche.setKm(rs.getInt(9));
				
				pasajero.setCoche(coche);
			}
		} catch (SQLException e) {
			System.out.println("obtener -> error al obtener el pasajero con id " + id);
			System.out.println(e.getMessage());
			System.out.println(query);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return pasajero;
	}

	@Override
	public List<Pasajero> listar() {
		if(!abrirConexion()){
			return null;
		}		
		List<Pasajero> listaPasajeros = new ArrayList<>();
		
		String query = "SELECT p.id,p.nombre,p.edad,p.peso,p.id_coche,c.marca,c.modelo,c.fabricacion,c.km"
				+ " FROM pasajeros AS p"
				+ " LEFT JOIN coches AS c ON p.id_coche = c.id";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Pasajero pasajero = new Pasajero();
				Coche coche = new Coche();
				
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getInt(4));
				
				coche.setId(rs.getInt(5));
				coche.setMarca(rs.getString(6));
				coche.setModelo(rs.getString(7));
				coche.setFabricacion(rs.getInt(8));
				coche.setKm(rs.getInt(9));
				
				pasajero.setCoche(coche);
				
				listaPasajeros.add(pasajero);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los pasajeros");
			System.out.println(e.getMessage());
			System.out.println(query);
			
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		
		return listaPasajeros;
	}
	
	public List<Pasajero> listar(int idCoche) {
		if(!abrirConexion()){
			return null;
		}		
		List<Pasajero> listaPasajeros = new ArrayList<>();
		
		String query = "SELECT p.id,p.nombre,p.edad,p.peso,p.id_coche,c.marca,c.modelo,c.fabricacion,c.km"
				+ " FROM pasajeros AS p"
				+ " LEFT JOIN coches AS c ON p.id_coche = c.id"
				+ " WHERE c.id = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, idCoche);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Pasajero pasajero = new Pasajero();
				Coche coche = new Coche();
				
				pasajero.setId(rs.getInt(1));
				pasajero.setNombre(rs.getString(2));
				pasajero.setEdad(rs.getInt(3));
				pasajero.setPeso(rs.getInt(4));
				
				coche.setId(rs.getInt(5));
				coche.setMarca(rs.getString(6));
				coche.setModelo(rs.getString(7));
				coche.setFabricacion(rs.getInt(8));
				coche.setKm(rs.getInt(9));
				
				pasajero.setCoche(coche);
				
				listaPasajeros.add(pasajero);
			}
		} catch (SQLException e) {
			System.out.println("listar -> error al obtener los pasajeros");
			System.out.println(e.getMessage());
			System.out.println(query);
			
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		
		return listaPasajeros;
	}
	
	
	//Bloque estatico, los bloques estaticos son ejecutados
	//por java JUSTO ANTES de ejecutar el metodo main()
	//Java busca todos los metodos staticos que haya en el programa
	//y los ejecuta
	/*
	static{
		try {
			//Esta sentencia carga del jar que hemos importado
			//una clase que se llama Driver que esta en el paqueta
			//com.mysql.jdbc. Esta clase se carga previamente en
			//java para m�s adelante ser llamada
			//Esto SOLO es necesario si utilizamos una versi�n java anterior
			//a la 1.7 ya que desde esta versi�n java busca automaticamente 
			//los drivers
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver cargado");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver NO cargado");
			e.printStackTrace();
		}
	}*/

}
