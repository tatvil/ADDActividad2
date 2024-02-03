package modelo.negocio;

import java.util.List;

import modelo.entidad.Coche;
import modelo.persistencia.DaoCocheMySql;
import modelo.persistencia.interfaces.DaoCoche;


//import modelo.persistencia.DaoPersonaDerby;
////import modelo.persistencia.PersonaDaoDerby;
//import modelo.persistencia.DaoPersonaMySql;
//import modelo.persistencia.interfaces.DaoPersona;

//Aquí irian todas las reglas de negocio de nuestra aplicacion, se aplicarían
//antes de llamar a la BBDD. 

public class GestorCoche {
	

	private DaoCoche daoCoche = new DaoCocheMySql();
	
	/**
	 * Método que da de alta un coche en base de datos. El id del coche debe de ser unico, pero lo gestiona la base de datos
	 * @param c el coche a dar de alta
	 * @return 0 en caso de que hayamos dado de alta al coche y 1 en caso
	 * de algun error de conexión con la bbdd 
	 */
	public int alta(Coche c){
			boolean alta = daoCoche.alta(c);
			if(alta) {
				return 0;
			}else {
				return 1;
			}
		}
	
	public boolean baja(int id){
		boolean baja = daoCoche.baja(id);
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
	public int modificar(Coche c){
		//aplicamos la regla de negocio
			boolean modificada = daoCoche.modificar(c);
			if(modificada) {
				return 0;
			}else {
				return 1;
			}

		}
	
	public Coche obtener(int id){
		Coche coche = daoCoche.obtener(id);
		return coche;
	}
	
	public List<Coche> listar(){
		List<Coche> listaCoches = daoCoche.listar();
		return listaCoches;
	}
	
}
