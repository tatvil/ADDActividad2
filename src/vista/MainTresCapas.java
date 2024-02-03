package vista;

import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.negocio.GestorCoche;

public class MainTresCapas {

	public static void main(String[] args) {
		
		System.out.println("Bienvenidos a nuestra CRUD de coches");
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		GestorCoche gc = new GestorCoche();
		
		do {
			menu();
			int opcion = sc.nextInt();
			switch (opcion) {
				case 1:
					System.out.println("Introduzca los datos del coche (marca, modelo, fabricacion y km)");
					String marca = sc.next();
					String modelo = sc.next();
					int fabricacion = sc.nextInt();
					int km = sc.nextInt();
					
					Coche c = new Coche();
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
					System.out.println("Introduzca los datos del coche a consultar (id)");
					id = sc.nextInt();

					Coche coche = gc.obtener(id);
					if (coche != null) {
						System.out.println(coche);
					} else {
						System.out.println("Error de conexión con la BBDD");
					}
					break;
					
				case 4:
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
					System.out.println("Listado de coches");
					System.out.println(gc.listar());
					break;
					
				case 0:
					fin = true;
					break;
				}
		}while(!fin);
		
		System.out.println("Fin de programa");

	}

	private static void menu() {
		System.out.println("Elija una opcion:");
		System.out.println("1. Añadir nuevo coche");
		System.out.println("2. Borrar coche por ID");
		System.out.println("3. Consulta coche por ID");
		System.out.println("4. Modificar coche por ID");
		System.out.println("5. Listado de coches");
		System.out.println("0. Terminar el programa");
	}

}