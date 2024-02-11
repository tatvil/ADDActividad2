package modelo.negocio;

import java.util.List;

import modelo.entidad.Coche;
import modelo.persistencia.DaoCocheMySql;
import modelo.persistencia.interfaces.DaoCoche;

/**
 * Clase que gestiona los coches y sus reglas de negocio. Estas reglas se aplicarán antes de llamar a la BBDD
 * 
 */
public class GestorCoche {
	

	private DaoCoche daoCoche = new DaoCocheMySql();
	
	/**
	 * Método que da de alta un coche en base de datos. El id del coche debe de ser unico, pero lo gestiona la base de datos. El 
	 * modelo y la marca no pueden estar vacios.
	 * @param c el coche a dar de alta
	 * @return 0 en caso de que hayamos dado de alta al coche, 
	 * 		1 en caso de algun error de conexión con la bbdd y 
	 * 		2 si la marca o modelo tienen estan vacios
	 */
	public int alta(Coche c){
		if(c.getMarca().length() > 0 && c.getModelo().length() > 0) {
			boolean alta = daoCoche.alta(c);
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
	 * Método que da de baja un coche de la base de datos. La baja se hace a partir
	 * del id del coche
	 * 
	 * @param id del coche a dar de baja
	 * @return true en caso de que hayamos dado de baja al coche, false en caso de
	 *         algun error de conexión con la bbdd
	 */
	public boolean baja(int id){
		boolean baja = daoCoche.baja(id);
		return baja;
	}
	
	/**
	 * Método que da modifica un coche en base de datos. La marca y el modelo no pueden estar vacios
	 * La modificacion sera a partir del id del coche
	 * @param c el coche a modificar. Dentro tiene que tener el id
	 * @return 0 en caso de que hayamos modificado el coche, 
	 *         1 en caso de algun error de conexion con la bbdd y 
	 *         2 en caso de que la marca o el modelo esten vacios
	 */
	public int modificar(Coche c){
			//aplicamos la regla de negocio
			if(c.getMarca().length() > 0 && c.getModelo().length() > 0) {
				boolean modificada = daoCoche.modificar(c);
				if(modificada) {
					return 0;
				}else {
					return 1;
				}
			} else {
				return 2;
			}
		}

		/**
		 * Método que obtiene un coche de la base de datos a partir de su id
		 * 
		 * @param id del coche
		 * @return el coche si lo encuentra, null si no lo encuentra
		 */
		public Coche obtener(int id){
			Coche coche = daoCoche.obtener(id);
			return coche;
		}
			
		/**
		 * Método que lista todos los coches de la base de datos
		 * 
		 * @return lista de coches
		 */
		public List<Coche> listar(){
			List<Coche> listaCoches = daoCoche.listar();
			return listaCoches;
		}
	
}
