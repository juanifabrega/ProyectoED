package Exceptions;

/**
 * EmptyStackException
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase que modela la excepcion ante una operacion sobre una pila vacia
 */

public class EmptyStackException extends Exception {

	/**
	 * Inicializa una nueva excepcion.
	 * El msg describe el origen de la misma. 
	 * @param m describe el origen de la excepcion
	 */
	public EmptyStackException(String m){
		super(m);
	}
}
