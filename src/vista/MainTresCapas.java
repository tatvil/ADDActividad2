package vista;

import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.entidad.Pasajero;
import modelo.negocio.GestorCoche;
import modelo.negocio.GestorPasajero;

/**
 * Clase que gestiona la vista de la aplicación
 * mediante el uso de la consola de comandos de Java (System.out) y la entrada de datos (System.in)
 * Presenta la dinamica de la interaccion con el usuario y recoge sus respuestas, 
 * segun las opciones que se le presenten en el menu principal y el menu de pasajeros, realizara las acciones oportunas
 * Se comunica con la capa de negocio y la capa de persistencia
 * 
 */
public class MainTresCapas {

	public static void main(String[] args) {
		
		System.out.println("Bienvenidos a nuestra CRUD de coches");
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		GestorCoche gc = new GestorCoche();
		GestorPasajero gp = new GestorPasajero();
		Coche c = new Coche();
		Pasajero p = new Pasajero();
		
		do {
			menu();
			int opcion = sc.nextInt();
			switch (opcion) {
				case 1:
					System.out.println("1. AÑADIR NUEVO COCHE");
					System.out.println("Introduzca los datos del coche (marca, modelo, fabricacion y km)");
					String marca = sc.next();
					String modelo = sc.next();
					int fabricacion = sc.nextInt();
					int km = sc.nextInt();
					
					c.setMarca(marca);
					c.setModelo(modelo);
					c.setFabricacion(fabricacion);
					c.setKm(km);
					
					int alta = gc.alta(c);
					if(alta == 0) {
						System.out.println("Coche dado de alta");
					}else if(alta == 1) {
						System.out.println("Error de conexión con la BBDD");
					}else if(alta == 2){
						System.out.println("El usuario tiene menos de tres carateres");
					}
					break;
					
				case 2:
					System.out.println("2. BORRAR COCHE");
					System.out.println("Introduzca los datos del coche a borrar (id)");
					int id = sc.nextInt();
					
				
					boolean baja = gc.baja(id);
					if(baja) {
						System.out.println("Coche dado de baja");
					}else {
						System.out.println("Error de conexión con la BBDD");
					}
					break;
					
				case 3:
					System.out.println("3. CONSULTAR COCHE");
					System.out.println("Introduzca los datos del coche a consultar (id)");
					id = sc.nextInt();

					Coche coche = gc.obtener(id);
					if (coche != null) {
						System.out.println(coche);
					} else {
						System.out.println("El coche no existe");
					}
					break;
					
				case 4:
					System.out.println("4. MODIFICAR COCHE");
					System.out.println(
							"Introduzca los datos del coche a modificar (id, marca, modelo, fabricacion y km)");
					id = sc.nextInt();
					marca = sc.next();
					modelo = sc.next();
					fabricacion = sc.nextInt();
					km = sc.nextInt();

					c = new Coche();
					c.setId(id);
					c.setMarca(marca);
					c.setModelo(modelo);
					c.setFabricacion(fabricacion);
					c.setKm(km);

					int modificado = gc.modificar(c);
					if (modificado == 0) {
						System.out.println("Coche modificado");
					} else {
						System.out.println("Error no se ha podido modificar el registro");
                    }
					break;
					
				case 5:
					System.out.println("5. LISTADO DE COCHES");
					System.out.println(gc.listar());
					break;
					
				case 6:
					crudPasajeros();
					break;
					
				case 0:
					fin = true;
					break;
				}
		}while(!fin);
		
		System.out.println("Fin de programa");

	}

	/**
	 * Método que muestra el menú principal de opciones de la aplicación
	 */
	private static void menu() {
		System.out.println("Elija una opcion:");
		System.out.println("1. Añadir nuevo coche");
		System.out.println("2. Borrar coche por ID");
		System.out.println("3. Consulta coche por ID");
		System.out.println("4. Modificar coche por ID");
		System.out.println("5. Listado de coches");
		System.out.println("6. Asignar pasajero a coche");
		System.out.println("0. Terminar el programa");
	}
	
	/**
	 * Método que muestra el menú de opciones de la gestión de pasajeros
	 */
	private static void menuPasajeros() {
		System.out.println("Elija una opcion:");
		System.out.println("1. Crear nuevo pasajero");
		System.out.println("2. Borrar pasajero por id");
		System.out.println("3. Consulta pasajero por id");
		System.out.println("4. Listar todos los pasajeros");
		System.out.println("5. Añadir pasajero a coche"); // El programa nos pedirá un id de un pasajero y el id de un coche, y lo añadirá al coche a nivel de base de datos. Sería una buena opción mostrar todos los coches disponibles.
		System.out.println("6. Eliminar pasajero de un coche"); // El programa nos pedirá un id de un pasajero y lo eliminará del coche a nivel de base de datos. Sería una buena opción mostrar todos los coches y sus pasajeros asociados.
		System.out.println("7. Listar todos los pasajeros de un coche"); // El programa pedirá el id de un coche, y nos mostrará todos los pasajeros asociados a él.
		System.out.println("0. Atras (menu coches)");
	}
	
	/**
	 * Método que gestiona la vista de la aplicación para la gestión de pasajeros,
	 * es decir, la creación, modificación, eliminación y listado de pasajeros
	 * es llamado desde el menú principal
	 */
	private static void crudPasajeros() {
		Scanner sp = new Scanner(System.in);
		boolean finPasajeros = false;
		GestorPasajero gp = new GestorPasajero();
		do {
			
			menuPasajeros();
			
			int opcionP = sp.nextInt();
			
			switch (opcionP) {
				case 1:
					System.out.println("1. CREAR NUEVO PASAJERO");
	                System.out.println("Introduzca los datos del pasajero (nombre, edad, peso)");
	                String nombre = sp.next();
	                int edad = sp.nextInt();
	                int peso = sp.nextInt();
	                
	                Pasajero p = new Pasajero();
	                p.setNombre(nombre);
	                p.setEdad(edad);
	                p.setPeso(peso);
	                
	                int altaP = gp.alta(p);
	                if(altaP == 0) {
	                    System.out.println("Alta de " + p);
	                }else if(altaP == 1) {
	                    System.out.println("Error de conexión con la BBDD");
	                }else if(altaP == 2){
	                    System.out.println("Los datos del pasajero son incorrectos");
	                }
	                break;
	                
				case 2:
					System.out.println("2. BORRAR PASAJERO");
					System.out.println("Introduzca los datos del pasajero a borrar (id)");
					
					int id = sp.nextInt();
					
				
					boolean baja = gp.baja(id);
					if(baja) {
						System.out.println("Baja de " + id);
					}else {
						System.out.println("Error de conexión con la BBDD");
					}
					break;
					
				case 3:
					System.out.println("3. CONSULTAR PASAJERO");
					System.out.println("Introduzca los datos del pasajero a consultar (id)");
					id = sp.nextInt();

					Pasajero pasajero = gp.obtener(id);
					if (pasajero != null) {
						System.out.println(pasajero);
					} else {
						System.out.println("Error de conexión con la BBDD");
					}
					break;
	                
				case 4:
					System.out.println("4. LISTAR PASAJEROS");
					System.out.println("Listado de pasajeros");
					System.out.println(gp.listar());
					break;
				
				case 5:
					System.out.println("5. AÑADIR PASAJERO A COCHE");
					System.out.println("Introduzca el id del pasajero y el id del coche");
					int idPasajero = sp.nextInt();
					int idCoche = sp.nextInt();

					p = new Pasajero();
					gp = new GestorPasajero();
					GestorCoche gc = new GestorCoche();
					
					Coche coche = gc.obtener(idCoche);
					p = gp.obtener(idPasajero);
					
					p.setCoche(coche);
					
					
					int modificado = gp.modificar(p);
					if (modificado == 0) {
						System.out.println("Modificado " + p);
					} else if (modificado == 1) {
						System.out.println("Error de conexión con la BBDD");
					} else if (modificado == 2) {
						System.out.println("Los datos del pasajero son incorrectos");
					} else if (modificado == 3) {
						System.out.println("El pasajero no existe");
					} else if (modificado == 4) {
						System.out.println("No se puede modificar, porque dicho coche no existe");
					}
					break;
					
				case 6:
					System.out.println("6. ELIMINAR PASAJERO DE COCHE");
					System.out.println("Introduzca el id del pasajero");
					idPasajero = sp.nextInt();

					p = gp.obtener(idPasajero);
					p.setCoche(null);

					modificado = gp.modificar(p);
					if (modificado == 0) {
						System.out.println("Coche modificado");
					} else {
						System.out.println("Error no se ha podido modificar el registro");
					}
					break;
					
				case 7:
					System.out.println("7. LISTAR PASAJEROS DE UN COCHE");
					System.out.println("Introduzca el id del coche");
					idCoche = sp.nextInt();

					System.out.println("Listado de pasajeros");
					System.out.println(gp.listar(idCoche));

					break;
					
				case 0:
					finPasajeros = true;
					break;
			}
			
			}while(!finPasajeros);
		}

}