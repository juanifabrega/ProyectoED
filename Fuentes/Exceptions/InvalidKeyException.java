package Exceptions;

/**
 * InvalidKeyExceptionException
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase que modela la excepcion ante una clave invalida.
 */

public class InvalidKeyException extends Exception{
	
	/**
	 * Inicializa una nueva excepcion.
	 * El msg describe el origen de la misma. 
	 * @param m describe el origen de la excepcion
	 */
	public InvalidKeyException(String m){
		super(m);
	}

}
