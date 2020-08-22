package Exceptions;

/**
 * InvalidPositionException
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase que modela la excepcion ante una posicion invalida.
 */

public class InvalidPositionException extends Exception{
	
	/**
	 * Inicializa una nueva excepcion.
	 * El msg describe el origen de la misma. 
	 * @param m describe el origen de la excepcion
	 */
	public InvalidPositionException(String m){
		super(m);
	}

}
