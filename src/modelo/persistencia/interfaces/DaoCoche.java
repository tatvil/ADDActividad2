package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Coche;

/**
 * Interfaz que define los metodos que se van a poder realizar sobre la BBDD
 * para la entidad coche
 * 
 */
public interface DaoCoche {
	/**
	 * Metodo que da de alta un coche en la BBDD. Se generara el ID de manera
	 * automatica.
	 * @param c el coche a dar de alta
	 * @return true en caso de que se haya dado de alta. false en caso de error
	 * con la BBDD.
	 */
	boolean alta(Coche c);
	
	/**
	 * Metodo que da de baja un coche de la BBDD. La baja se hace a partir del ID
	 * del coche
	 * 
	 * @param id del coche a dar de baja
	 * @return true en caso de que se haya dado de baja al coche. False en caso de
	 *         error con la BBDD.
	 */
	boolean baja(int id);
	
	/**
	 * Metodo que modifica un coche en la BBDD. La modificacion sera partir
	 * del ID que contenga la persona.
	 * @param c el coche a modificar
	 * @return true en caso de que se haya modificado. False en caso de error
	 * con la BBDD.
	 */
	boolean modificar(Coche c);

	/**
	 * Metodo que obtiene un coche de la BBDD a partir de su ID
	 * 
	 * @param id del coche
	 * @return el coche en caso de que exista. Null en caso de que no exista
	 */
	Coche obtener(int id);
	
	/**
	 * Metodo que lista todos los coches de la BBDD
	 * 
	 * @return lista de coches
	 */
	List<Coche> listar();
}
