package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Coche;

public interface DaoCoche {
	/**
	 * Metodo que da de alta un coche en la BBDD. Se generara el ID de manera
	 * automatica.
	 * @param c el coche a dar de alta
	 * @return true en caso de que se haya dado de alta. false en caso de error
	 * con la BBDD.
	 */
	boolean alta(Coche c);
	boolean baja(int id);
	/**
	 * Metodo que modifica un coche en la BBDD. La modificacion sera partir
	 * del ID que contenga la persona.
	 * @param c el coche a modificar
	 * @return true en caso de que se haya modificado. False en caso de error
	 * con la BBDD.
	 */
	boolean modificar(Coche c);
	Coche obtener(int id);
	List<Coche> listar();
}
