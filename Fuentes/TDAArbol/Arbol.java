package TDAArbol;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.EmptyTreeException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidPositionException;
import Positions.Position;
import Positions.TNodo;
import TDALista.*;


/**
 * Arbol
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Implementación de un arbol general.
 * 
 */

public class Arbol<E> implements Tree<E>{
	
	protected TNodo<E> raiz;
	protected int size;
	
	/**
	 * Crea un arbol vacio.
	 */
	public Arbol(){
		raiz=null;
		size=0;
	}
	
	@Override
	public int size(){
		return size;
	}
	
	@Override
	public boolean isEmpty(){
		return size==0;
	}
	
	/**
	 * Chequea si la posición pasada por parametro es correcta. 
	 * @param v Posición a chequear.
	 * @return Nodo del arbol.
	 * @throws InvalidPositionException si la posición es invalida.
	 */
	private TNodo<E> checkPosition(Position<E> v ) throws InvalidPositionException{
		try{
			if (v==null || !(v instanceof TNodo)) throw new InvalidPositionException("Posicion invalida");
			return (TNodo<E>) v;
		}
		catch(ClassCastException e){
			throw new InvalidPositionException("Posicion invalida");
		}
	}
	
	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException{
		TNodo<E> nodo=checkPosition(v);
		E toReturn= nodo.element();
		nodo.setElemento(e);
		return toReturn;
	}
	
	@Override
	public Position<E> root() throws EmptyTreeException{
		if (isEmpty()) throw new EmptyTreeException("Arbol vacio");
		return raiz;
	}
	
	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException{
		TNodo<E> nodo=checkPosition(v);
		if (nodo==raiz) throw new BoundaryViolationException("No se le puede pedir el padre a la raiz");
		return nodo.getPadre();
	}
	
	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException{
		TNodo<E> nodo= checkPosition(v);
		PositionList<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();
		for (TNodo<E> n: nodo.getHijos() ){
			lista.addLast(n);
		}
		return lista;		
	}
	
	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException{
		TNodo<E> nodo= checkPosition(v);
		return (nodo.getHijos().size()>0);
	}
	
	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException{
		TNodo<E> nodo= checkPosition(v);
		return ( nodo.getHijos().isEmpty() );
	}
	
	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException{
		TNodo<E> nodo= checkPosition(v);
		return (nodo==raiz);		
	}
	
	@Override
	public void createRoot(E e) throws InvalidOperationException{
		if (!isEmpty()) throw new InvalidOperationException("Arbol no vacio");
		raiz= new TNodo<E>(e);
		size=1;		
	}
	
	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException{
		TNodo<E> padre=checkPosition(p);
		TNodo<E> nuevo= new TNodo<E>(e,padre);
		if (isEmpty()) throw new InvalidPositionException("El arbol esta vacio");
		padre.getHijos().addFirst(nuevo);
		size++;
		return nuevo;
	}
	
	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException{
		TNodo<E> padre=checkPosition(p);
		TNodo<E> nuevo= new TNodo<E>(e,padre);
		if (isEmpty()) throw new InvalidPositionException("El arbol esta vacio");
		padre.getHijos().addLast(nuevo);
		size++;
		return nuevo;
	}
	
	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException{
		TNodo<E> padre= checkPosition(p);
		TNodo<E> hrno= checkPosition(rb);
		if (isEmpty()) throw new InvalidPositionException("El arbol esta vacio");
		if (hrno.getPadre()!= padre) throw new InvalidPositionException("rb no es hijo de p");
		TNodo<E> nuevo= new TNodo<E>(e,padre);
		Iterable<Positions.Position<TNodo<E>>> posiciones= padre.getHijos().positions();
		Iterator<Positions.Position<TNodo<E>>> it= posiciones.iterator();
		Positions.Position<TNodo<E>> pos=null;
		boolean encontre=false;
		while (!encontre && it.hasNext()){
			pos=it.next();
			TNodo<E> aux= pos.element();
			if (aux == hrno)
				encontre=true;
		}
		try{
			padre.getHijos().addBefore(pos, nuevo );
			size++;
		}
		catch(Exceptions.InvalidPositionException j){
			throw new InvalidPositionException("Posicion invalida: Lista");
		}
		return nuevo;
	}
	
	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException{
		TNodo<E> padre= checkPosition(p);
		TNodo<E> hrno= checkPosition(lb);
		if (isEmpty()) throw new InvalidPositionException("El arbol esta vacio");
		if (hrno.getPadre()!= padre) throw new InvalidPositionException("rb no es hijo de p");
		TNodo<E> nuevo= new TNodo<E>(e, padre);
		Iterable<Positions.Position<TNodo<E>>> posiciones=  padre.getHijos().positions() ;
		Iterator<Positions.Position<TNodo<E>>> it= posiciones.iterator();
		Positions.Position<TNodo<E>> pos=null;
		boolean encontre=false;
		while (!encontre && it.hasNext()){
			pos=it.next();
			TNodo<E> aux= pos.element();
			if (aux==hrno)
				encontre=true;
		}
		try{
			padre.getHijos().addAfter(pos, nuevo );
			size++;
		}
		catch(Exceptions.InvalidPositionException j){
			throw new InvalidPositionException("Posicion invalida: Lista");
		}
		return nuevo;
	}
	
	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException{
		TNodo<E> n = checkPosition(p);
		if (size==0) throw new InvalidPositionException("Arbol vacio");
		if(!n.getHijos().isEmpty()) throw new InvalidPositionException("No es un nodo externo");
		removeNode(n);
	}
	
	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException{
		TNodo<E> n = checkPosition(p);
		if (size==0) throw new InvalidPositionException("Posicion invalida");
		if(n.getHijos().isEmpty()) throw new InvalidPositionException("No es un nodo interno");
		removeNode(n);							
	}
	
	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException{
		TNodo<E> n= checkPosition(p);
		if (isEmpty()) throw new InvalidPositionException("El arbol esta vacio");
		try{
			if(n==raiz){
				if( raiz.getHijos().size() ==1){
					TNodo<E> nRaiz= raiz.getHijos().first().element();
					nRaiz.setPadre(null);
					raiz=nRaiz;
					size--;								
				}
				else{
					if (size==1){
						raiz=null;
						size--;
					}
					else{
						throw new InvalidPositionException("Posicion invalida");
					}
				}
			}
			else{
				//eliminamos un nodo interno o nodo hoja
				TNodo<E> padre= n.getPadre();
				PositionList<TNodo<E>> listaHijosN= n.getHijos();
				PositionList<TNodo<E>> listaHijosP=padre.getHijos();
				//busco a n dentro de listaHijosP
				Position<TNodo<E>> primero=listaHijosP.first();
				while (primero.element() !=n ){
					primero=listaHijosP.next(primero);
				}
				//si n tiene hijos los recorro y se insertan en orden
				if ( !listaHijosN.isEmpty() ){
					while(!listaHijosN.isEmpty()){
						Position<TNodo<E>> hijoInsertar= listaHijosN.first();
						listaHijosP.addBefore(primero, hijoInsertar.element());
						TNodo<E> insertado= listaHijosP.prev(primero).element();
						insertado.setPadre(padre);
						listaHijosN.remove(listaHijosN.first());
					}
					//eliminamos a n de listaHijosP
				}
				listaHijosP.remove(primero);
				n.setPadre(null);
				n.setElemento(null);
				size--;
			}
		}
		catch(EmptyListException e){
			e.getMessage();
		}
		catch(BoundaryViolationException e){
			e.getMessage();			
		}
				
	}
	
	@Override
	public Iterator<E> iterator(){
		PositionList<E> l= new ListaDoblementeEnlazada<E>();
		for (Position<E> p: positions() ){
			l.addLast(p.element() );
		}
		return l.iterator();		
	}
	
	/**
	 * Muestra los rotulos del arbol en recorrido de preorden.
	 * @param l lista de hijos
	 * @param r nodo de la lista de hijos
	 */
	private void pre(PositionList<Position<E>> l, TNodo<E> r){
		l.addLast(r);
		for (TNodo<E> h: r.getHijos() ){
			pre(l,h);
		}
	}
	
	@Override
	public Iterable<Position<E>> positions(){
		PositionList<Position<E>> l= new ListaDoblementeEnlazada<Position<E>>();
		if ( !isEmpty() )
			pre(l, raiz);
		return l;
	}
	
}
