package Exceptions;

/**
 * BoundaryViolationException
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase que modela la excepcion de violacion de limites.
 */

public class BoundaryViolationException extends Exception{
	
	/**
	 * Inicializa una nueva excepcion.
	 * El msg describe el origen de la misma. 
	 * @param m describe el origen de la excepcion
	 */
	public BoundaryViolationException(String m){
		super(m);
		}
}
