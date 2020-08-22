package Exceptions;

/**
 * EmptyQueueException
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase que modela la excepcion ante una operacion sobre una cola vacia
 */

public class EmptyQueueException extends Exception{
	
	/**
	 * Inicializa una nueva excepcion.
	 * El msg describe el origen de la misma. 
	 * @param m describe el origen de la excepcion
	 */
	public EmptyQueueException(String m){
		super(m);
	}

}
