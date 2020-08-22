package Positions;

/**
 * 
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Implementación de la clase DNodo, con enlace al anterior y siguiente DNodo.
 * 
 */

public class DNodo<E> implements Position<E> {
	
	private E elemento;
	private DNodo<E> siguiente, prev;
	
	public DNodo() { elemento = null; siguiente = null; prev = null; }
	public DNodo( E item  ) { elemento = item; siguiente = null; prev = null; }
	public DNodo( E item, DNodo<E> next ) { elemento = item; siguiente = next; prev = null; }
	public DNodo( DNodo<E> prv, E item ) { elemento = item; siguiente = null; prev = prv; }
		
	/**
	 * Setea el elemento pasado por parametro.
	 * @param elemento Nuevo elemento a insertar.
	 */
	public void setElemento( E elemento ){
		this.elemento=elemento;
	}
	
	/**
	 * Setea el elemento pasado por parametro como siguiente.
	 * @param siguiente elemento Nuevo elemento a insertar como siguiente.
	 */
	public void setSiguiente( DNodo<E> siguiente ){
		this.siguiente = siguiente;
	}
	
	/**
	 * Setea el elemento pasado por parametro como anterior.
	 * @param prev Nuevo elemento a insertar como anterior.
	 */
	public void setPrev( DNodo<E> prev ) {
		this.prev = prev;
	}
	
	/**
	 * Devuelve el valor de la variable elemento.
	 * @return Devuelve el valor de la variable elemento.
	 */
	public E element(){
		return elemento;
	}
	
	/**
	 * Devuelve el DNodo siguiente.
	 * @return Devuelve el DNodo siguiente.
	 */
	public DNodo<E> getSiguiente(){
		return siguiente;
	}
	
	/**
	 * Devuelve el DNodo anterior.
	 * @return Devuelve el DNodo anterior.
	 */
	public DNodo<E> getPrev()
	{ return prev;}
}