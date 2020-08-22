package Exceptions;

/**
 * EmptyTreeException
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase que modela la excepcion ante una operacion sobre un arbol vacio.
 */

public class EmptyTreeException extends Exception {
	
	/**
	 * Inicializa una nueva excepcion.
	 * El msg describe el origen de la misma. 
	 * @param m describe el origen de la excepcion
	 */
	public EmptyTreeException(String m){
		super(m);
	}

}
