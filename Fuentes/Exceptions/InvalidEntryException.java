package Exceptions;

/**
 * InvalidEntryException
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase que modela la excepcion ante una entrada invalida.
 */

public class InvalidEntryException extends Exception{
	
	/**
	 * Inicializa una nueva excepcion.
	 * El msg describe el origen de la misma. 
	 * @param m describe el origen de la excepcion
	 */
	public InvalidEntryException(String m){
		super(m);
	}

}
