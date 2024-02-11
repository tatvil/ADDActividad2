package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Pasajero;

/**
 * Interfaz que define los metodos que se van a poder realizar sobre la BBDD
 * para la entidad pasajero
 * 
 */
public interface DaoPasajero {
	
	/**
	 * Metodo que da de alta un pasajero en la BBDD. Se generara el ID de manera
	 * automatica.
	 * @param p el pasajero a dar de alta
	 * @return true en caso de que se haya dado de alta. false en caso de error
	 * con la BBDD.
	 */
	boolean alta(Pasajero p);
	
	/**
	 * Metodo que da de baja un pasajero de la BBDD. La baja se hace a partir del ID
	 * del pasajero
	 * 
	 * @param id del pasajero a dar de baja
	 * @return true en caso de que se haya dado de baja al pasajero. False en caso
	 *         de error con la BBDD.
	 */
	boolean baja(int id);
	
	/**
	 * Metodo que modifica un coce en la BBDD. La modificacion sera partir
	 * del ID que contenga la persona.
	 * @param p el pasajero a modificar
	 * @return true en caso de que se haya modificado. False en caso de error
	 * con la BBDD.
	 */
	boolean modificar(Pasajero p);
	
	/**
	 * Metodo que obtiene un pasajero de la BBDD a partir de su ID
	 * 
	 * @param id del pasajero
	 * @return el pasajero en caso de que exista. Null en caso de que no exista
	 */
	Pasajero obtener(int id);
	
	/**
	 * Metodo que lista todos los pasajeros de la BBDD
	 * 
	 * @return lista de pasajeros
	 */
	List<Pasajero> listar();
	
	/**
	 * Metodo que lista todos los pasajeros de la BBDD que esten en un coche
	 * 
	 * @return lista de pasajeros
	 */
	List<Pasajero> listar(int idCoche);
}
