package Exceptions;

/**
 * EmptyListException
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase que modela la excepcion ante una operacion sobre una lista vacia
 */

public class EmptyListException extends Exception{
	
	/**
	 * Inicializa una nueva excepcion.
	 * El msg describe el origen de la misma. 
	 * @param m describe el origen de la excepcion
	 */
	public EmptyListException(String m){
		super(m);
	}

}
