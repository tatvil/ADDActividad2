package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Pasajero;

public interface DaoPasajero {
	/**
	 * Metodo que da de alta un pasajero en la BBDD. Se generara el ID de manera
	 * automatica.
	 * @param p el pasajero a dar de alta
	 * @return true en caso de que se haya dado de alta. false en caso de error
	 * con la BBDD.
	 */
	boolean alta(Pasajero p);
	boolean baja(int id);
	/**
	 * Metodo que modifica un coce en la BBDD. La modificacion sera partir
	 * del ID que contenga la persona.
	 * @param p el pasajero a modificar
	 * @return true en caso de que se haya modificado. False en caso de error
	 * con la BBDD.
	 */
	boolean modificar(Pasajero p);
	Pasajero obtener(int id);
	List<Pasajero> listar();
	List<Pasajero> listar(int idCoche);
}
