package Positions;

import TDALista.*;

/**
 * TNodo.
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Implementación de la clase TNodo, con enlace al padre y una lista de hijos.
 * 
*/

public class TNodo<E> implements Position<E> {
	
	protected E elemento;
	protected TNodo<E> padre;
	protected PositionList<TNodo<E>> hijos;
	
	//Constructores
	public TNodo(E el,TNodo<E> p){
		elemento=el;
		padre=p;
		hijos= new ListaDoblementeEnlazada<TNodo<E>>();
	}
	public TNodo(E el){
		this(el,null);
	}
	
	/**
	 * Setea el elemento p pasado por parameto como padre.
	 * @param p elemento a setear como padre.
	 */
	public void setPadre(TNodo<E> p){
		padre=p;
	}
	
	/**
	 * Setea el elemento p pasado por parameto.
	 * @param el elemento a setear como elemento.
	 */
	public void setElemento(E el){
		elemento=el;
	}
	
	/**
	 * Devuelve el valor del elemento padre.
	 * @return devuelve el valor del elemento padre.
	 */
	public TNodo<E> getPadre(){
		return padre;
	}
	
	/**
	 * Devuelve la lista de hijos.
	 * @return devuelve la lista de hijos.
	 */
	public PositionList<TNodo<E>> getHijos(){
		return hijos;
	}
	
	/**
	 * Devuelve el valor del elemento elemento.
	 * @return devuelve el elemento.
	 */
	public E element(){
		return elemento;
	}
	
}
