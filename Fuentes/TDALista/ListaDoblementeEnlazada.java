package TDALista;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;
import Positions.DNodo;
import Positions.Position;

/**
 * ListaDoblementeEnlazada
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Implementación de una lista con nodos de doble enlace.
 * 
 */

public class ListaDoblementeEnlazada<E> implements PositionList<E> {

	protected DNodo<E> cabeza, cola;
	protected int longitud;
	
	/**
	 * Crea una lista generica vacia.
	 */
	public ListaDoblementeEnlazada() {
		longitud = 0;
		cabeza = new DNodo<E>();
		cola = new DNodo<E>(cabeza, null);
		cabeza.setSiguiente(cola);
	}
	
	@Override	
	public int size(){
		return longitud;
	}
	
	@Override
	public boolean isEmpty(){
		return (longitud == 0);
	}
	
	@Override
	public Position<E> first() throws EmptyListException{
		if (isEmpty()) throw new EmptyListException("Lista vacia");
		return cabeza.getSiguiente();		
	}
	
	@Override
	public Position<E> last() throws EmptyListException{
		if (isEmpty()) throw new EmptyListException("Lista vacia");
		return cola.getPrev();	
	}
	
	/**
	 * Chequea si la posición pasada por parametro es correcta. 
	 * @param p Posición a chequear.
	 * @return nodo de la lista.
	 * @throws InvalidPositionException si la posición es nula.
	 */
	
	private DNodo<E> checkPosition( Position<E> p ) throws InvalidPositionException{
		try {
			if( p == null ) throw new InvalidPositionException( "Posicion nula");
			if (p==cabeza || p==cola)  throw new InvalidPositionException("La posicion es un nodo centinela");
			return (DNodo<E>) p;
			} 
		catch( ClassCastException e ) {
			throw new InvalidPositionException("Posicion nula" );
			}
	}
	
	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> n= checkPosition(p);
		if (n.getSiguiente() == cola) throw new BoundaryViolationException("Te caiste de la lista");
		return n.getSiguiente();
	}
	
	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		DNodo<E> n= checkPosition(p);
		if (n.getPrev() == cabeza) throw new BoundaryViolationException("Te caiste de la lista");
		return n.getPrev();
	}
	
	@Override
	public void addFirst(E element){
		DNodo<E> nuevo = new DNodo<E>(element);
		DNodo<E> sgte = cabeza.getSiguiente();
		nuevo.setPrev(cabeza);
		nuevo.setSiguiente(sgte);
		cabeza.setSiguiente(nuevo);
		sgte.setPrev(nuevo);
		longitud++;
	}
	
	@Override
	public void addLast(E element){
		DNodo<E> nuevo = new DNodo<E>(element);
		DNodo<E> previo = cola.getPrev();
		nuevo.setPrev(previo);
		nuevo.setSiguiente(cola);
		cola.setPrev(nuevo);
		previo.setSiguiente(nuevo);
		longitud++;
		
	}
	
	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException{
		if (isEmpty()) throw new InvalidPositionException("La lista esta vacia");
		DNodo<E> n= checkPosition(p);
		DNodo<E> nuevo = new DNodo<E>(element);
		DNodo<E> sgte = n.getSiguiente();
		nuevo.setPrev(n);
		nuevo.setSiguiente(sgte);
		sgte.setPrev(nuevo);
		n.setSiguiente(nuevo);
		longitud++;
	}
	
	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException{
		if (isEmpty()) throw new InvalidPositionException("La lista esta vacia");
		DNodo<E> n= checkPosition(p);
		DNodo<E> nuevo = new DNodo<E>(element);
		DNodo<E> previo = n.getPrev();
		nuevo.setPrev(previo);
		nuevo.setSiguiente(n);
		n.setPrev(nuevo);
		previo.setSiguiente(nuevo);
		longitud++;
	}
	
	@Override
	public E remove(Position<E> p) throws InvalidPositionException{
		if (isEmpty()) throw new InvalidPositionException("La lista esta vacia");
		DNodo<E> n= checkPosition(p);
		E aux=p.element();
		DNodo<E> previo = n.getPrev();
		DNodo<E> sgte = n.getSiguiente();
		sgte.setPrev(previo);
		previo.setSiguiente(sgte);
		n.setPrev(null);
		n.setSiguiente(null);
		longitud--;
		return aux;
	}
	
	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException{
		if (isEmpty()) throw new InvalidPositionException("La lista esta vacia");
		DNodo<E> n=checkPosition(p);
		E aux=p.element();
		n.setElemento(element);
		return aux;		
	}
	
	@Override
	public Iterator<E> iterator(){
		return new ElementIterator<E>(this);
	}
	
	@Override
	public Iterable<Position<E>> positions(){
		PositionList<Position<E>> P= new ListaDoblementeEnlazada<Position<E>>();
		if (!isEmpty()){
			DNodo<E> p= cabeza.getSiguiente();
			while(p!=cola){
				P.addLast(p);
				p=p.getSiguiente();
			}
		}
		return P;
	}
}
