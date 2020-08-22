package TDALista;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;
import Positions.Position;

/**
 * ElementIterator
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Implementación de Iterador.
 * 
 */

public class ElementIterator<E> implements Iterator<E> {
	
	protected PositionList<E> list; // Lista a iterar
	protected Position<E> cursor; // Posición del elemento corriente
	
	public ElementIterator (PositionList <E> l ) {
		list=l;
		try{
			if (list.isEmpty()) cursor=null;
			else cursor=list.first();			
		}
		catch(EmptyListException e){
			e.getMessage();			
		}
	}
	
	/**
	 * Consulta si el iterador tiene siguiente.
	 * @return Verdadero si hay siguiente, falso en caso contrario.
	 */
	public boolean hasNext() { return cursor != null; }
	
	/**
	 * Devuelve el elemento siguiente.
	 * @return el elemento siguiente.
	 */	
	public E next () {
		E toReturn = cursor.element();
		try{
			cursor = (cursor == list.last()) ? null : list.next(cursor);
		
		}
		catch(EmptyListException e){
			e.getMessage();			
		}
		catch(BoundaryViolationException e){
			e.getMessage();			
		}
		catch(InvalidPositionException e){
			e.getMessage();			
		}
		return toReturn;
	}

}
