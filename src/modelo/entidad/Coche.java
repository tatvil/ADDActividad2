package modelo.entidad;

import java.util.Objects;

/**
 * Clase que representa un coche
 * Cada coche tiene:
 *     - id
 *     - marca
 *     - modelo
 *     - fabricacion: año de fabricacion
 *     - km: numero de km
 * 
 * Esta clase se utiliza para mapear la tabla coches de la base de datos
 * 
 */
public class Coche {
	int id;
	String marca;
	String modelo;
	int fabricacion;
	int km;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public void setId() {
		// Buscar el id mas alto en  la base de datos y sumarle 1
		
		
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public int getFabricacion() {
		return fabricacion;
	}


	public void setFabricacion(int fabricacion) {
		this.fabricacion = fabricacion;
	}


	public int getKm() {
		return km;
	}


	public void setKm(int km) {
		this.km = km;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coche other = (Coche) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return "coche [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", fabricacion=" + fabricacion + ", km="
				+ km + "]\n";
	}
	
	
}
