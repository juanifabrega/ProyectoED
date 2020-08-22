package Positions;

/**
 * 
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Implementación de la clase Entrada.
 * 
 */

public class Entrada<K,V> implements Entry<K,V> {
	
	private K clave;
	private V valor;
	
	public Entrada(K k, V v){
		clave=k;
		valor=v;
	}
	
	/**
	 * getKey.
	 * @return el valor del elemento clave.
	 */
	public K getKey(){return clave;	}
	
	/**
	 * getValue.
	 * @return el valor del elemento valor.
	 */
	public V getValue(){return valor; }
	
	/**
	 * setKey.
	 *@param k valor para asignarle al elemento clave.
	 */
	public void setKey(K k){clave=k; }
	
	/**
	 * setValue.
	 *@param v valor para asignarle al elemento valor.
	 */
	public void setValue(V v){valor=v; }

}
