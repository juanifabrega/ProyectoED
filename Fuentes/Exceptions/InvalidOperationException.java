package Exceptions;

/**
 * InvalidOperationException
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase que modela la excepcion de una operacion invalida.
 */

public class InvalidOperationException extends Exception {
	
	/**
	 * Inicializa una nueva excepcion.
	 * El msg describe el origen de la misma. 
	 * @param m describe el origen de la excepcion
	 */
	public InvalidOperationException(String m){
		super(m);
	}

}
