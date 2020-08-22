package Positions;

/**
 * Interface Entry
 * @author Cátedra de Estructuras de Datos, Departamento de Cs. e Ing. de la Computación, UNS.
 */

public interface Entry<K,V> {
	
	public K getKey(); //retorna la clave de la emtrada
	public V getValue(); //retorna el valor de la entrada
	
}