package modelo.negocio;

import java.util.List;

import modelo.entidad.Pasajero;
import modelo.persistencia.DaoPasajeroMySql;
import modelo.persistencia.interfaces.DaoPasajero;

public class GestorPasajero {
private DaoPasajero daoPasajero = new DaoPasajeroMySql();
	
	/**
	 * Método que da de alta un coche en base de datos. El id del coche debe de ser unico, pero lo gestiona la base de datos
	 * @param c el coche a dar de alta
	 * @return 0 en caso de que hayamos dado de alta al coche y 1 en caso
	 * de algun error de conexión con la bbdd 
	 */
	public int alta(Pasajero p){
			boolean alta = daoPasajero.alta(p);
			if(alta) {
				return 0;
			}else {
				return 1;
			}
		}
	
	public boolean baja(int id){
		boolean baja = daoPasajero.baja(id);
		return baja;
	}
	
	/**
	 * Método que da modifica una persona en base de datos. El nombre de la persona
	 * debe de tener al menos 3 caracteres. La modificarci�n sera a partir del 
	 * id de la persona
	 * @param p la persona a modificar. Dentro tiene que tener el id
	 * @return 0 en caso de que hayamos modificado la persona, 1 en caso
	 * de algun error de conexi�n con la bbdd y 2 en caso de que la persona
	 * tenga menos de 3 caracteres
	 */
	public int modificar(Pasajero p){
		//aplicamos la regla de negocio
			boolean modificada = daoPasajero.modificar(p);
			if(modificada) {
				return 0;
			}else {
				return 1;
			}

		}
	
	public Pasajero obtener(int id){
		Pasajero pasajero = daoPasajero.obtener(id);
		return pasajero;
	}
	
	public List<Pasajero> listar(){
		List<Pasajero> listaPasajeros = daoPasajero.listar();
		return listaPasajeros;
	}

	public List<Pasajero> listar(int idCoche) {
		List<Pasajero> listaPasajeros = daoPasajero.listar(idCoche);
		return listaPasajeros;
	}
	
}
