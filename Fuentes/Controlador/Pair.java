package Controlador;

/**
 * Pair.
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase Pair.
 */

public class Pair<K,V>{
	
	private K clave;
	private V valor;
	
	/**
	 * Crea una clase Pair.
	 * @param key clave de la clase Pair
	 * @param value valor de la calse Pair.
	 */
	public Pair(K key, V value){
		clave=key;
		valor=value;
	}
	
	/**
	 * Devuelvo el elemento clave.
	 * @return el elemento clave.
	 */
	public K getClave(){
		return clave;
	}
	
	/**
	 * Devuelvo el elemento valor.
	 * @return el elemento valor.
	 */
	public V getValor(){
		return valor;
	}
	
}
