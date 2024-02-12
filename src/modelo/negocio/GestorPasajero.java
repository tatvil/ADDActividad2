package modelo.negocio;

import java.util.List;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;
import modelo.persistencia.DaoCocheMySql;
import modelo.persistencia.DaoPasajeroMySql;
import modelo.persistencia.interfaces.DaoCoche;
import modelo.persistencia.interfaces.DaoPasajero;

/**
 * Clase que gestiona los pasajeros y sus reglas de negocio. Estas reglas se
 * aplicarán antes de llamar a la BBDD
 * 
 */
public class GestorPasajero {
private DaoPasajero daoPasajero = new DaoPasajeroMySql();
private DaoCoche daoCoche = new DaoCocheMySql();
	
	/**
	 * Método que da de alta un pasajero en base de datos. El id del pasajero debe de ser unico, pero lo gestiona la base de datos
	 * @param p el pasajero a dar de alta
	 * @return 0 en caso de que hayamos dado de alta al pasajero 
	 * 1 en caso de algun error de conexión con la bbdd  
	 * 2 en caso de que los datos del pasajero sean incorrectos
	 */
	public int alta(Pasajero p){
			if(p.getNombre().length() > 0 && p.getEdad() >= 0 && p.getPeso() >= 0) {
				boolean alta = daoPasajero.alta(p);
				if(alta) {
					return 0;
				}else {
					return 1;
				}
			}else {
				return 2;
			}
		}
	
	/**
	 * Método que da de baja un pasajero de la base de datos. La baja se hace a partir del id del pasajero
	 * @param id id del pasajero a dar de baja
	 * @return true en caso de que hayamos dado de baja al pasajero, false en caso de algun error de conexión con la bbdd
	 */
	public boolean baja(int id){
		boolean baja = daoPasajero.baja(id);
		return baja;
	}
	
	/**
	 * Método que da modifica un pasajero en base de datos. La modificacion sera a partir del 
	 * id del pasajero
	 * @param p el pasajero a modificar. Dentro tiene que tener el id
	 * @return 0 en caso de que hayamos modificado el pasajero, 
	 * 	1 en caso de algun error de conexion con la bbdd,
	 *  2 en caso de que los datos del pasajero sean incorrectos (peso y edad menores de 0 o nombre vacio o con caracteres extraños)
	 *  3 en caso de que el pasajero no exista
	 *  4 en caso de que el coche no exista
	 */
	public int modificar(Pasajero p){
		//aplicamos la regla de negocio
		
	    Pasajero pasajeroExiste = daoPasajero.obtener(p.getId());
	    
	    // Si el pasajero no existe, devuelve un código de error
        if (pasajeroExiste != null) {
		    // No siempre tiene que tener un coche asociado, pero si me lo pasan, lo compruebo
		    if (p.getCoche() != null) {
		    	// Verifica si el coche está dado de alta en la tabla de coches y el pasajero en la tabla de pasajeros
		    
//	            Coche cocheExiste = daoCoche.obtener(p.getCoche().getId());
		    }
			    // Si el coche no existe, devuelve un código de error
//			    if (cocheExiste == null) {
//			        return 3; // Código de error para coche o pasajero inexistente
//			    } else {   	  // Si el coche existe, se procede a modificar el pasajero
		            if(p.getNombre().length() > 0 || contieneSoloLetras(p.getNombre()) || p.getEdad() >= 0 || p.getPeso() >= 0) { // Comprueba que los datos del pasajero sean correctos
			    
						boolean modificada = daoPasajero.modificar(p);
						if(modificada) {
							return 0;   				// Se ha modificado el pasajero
						} else {
							return 1;    				// Error de conexión con la BBDD
						}
					} else {
							return 2;					// Datos del pasajero incorrectos
					}
//			    }

    } else {
    	        return 3; // Código de error para pasajero inexistente
    }

	}
	
	/** 
	 *  Método que obtiene un pasajero a partir de su id
	 * @param id del pasajero
	 * @return pasajero con ese id
	 */
	public Pasajero obtener(int id){
		Pasajero pasajero = daoPasajero.obtener(id);
		return pasajero;
	}
	
	/**
	 *  Método que lista todos los pasajeros de la base de datos
	 * @return lista de pasajeros
	 */
	public List<Pasajero> listar(){
		List<Pasajero> listaPasajeros = daoPasajero.listar();
		return listaPasajeros;
	}

	/**
	 *  Método que lista los pasajeros de un coche en concreto a partir de su id de coche 
	 *  Como se ve el metodo esta sobrecargado, es decir, tiene dos metodos con el mismo 
	 *  nombre pero con distinto numero de parametros
	 * @param idCoche id del coche
	 * @return lista de pasajeros de ese coche
	 */
	public List<Pasajero> listar(int idCoche) {
		List<Pasajero> listaPasajeros = daoPasajero.listar(idCoche);
		return listaPasajeros;
	}
	
	/**
	 * Método que comprueba si una cadena contiene solo letras
	 * 
	 * @param cadena
	 * @return true si la cadena contiene solo letras, false en caso contrario
	 */
	public boolean contieneSoloLetras(String cadena) {
	    // Expresión regular que coincide solo con letras del alfabeto (mayúsculas o minúsculas)
	    String patron = "^[a-zA-Z]+$";
	    return cadena.matches(patron);
	}
}
