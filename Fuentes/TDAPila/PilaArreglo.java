package TDAPila;

import Exceptions.EmptyStackException;

/**
 * 
 * PilaConEnlaces
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 *Implementacion de una pila generica usando un arreglo.
 * 
 */

public class PilaArreglo<E> implements Stack<E>{
	
	private int tama�o;
	private E[] datos;
	
	/**
	 * Crea una pila de tama�o indicado por parametro
	 * @param MAX masimo tama�o que puede tener la pila
	 */
	public PilaArreglo(int MAX){
		tama�o=0;
		datos=(E[]) new Object[MAX];
	}
	
	/**
	 * Crea una pila con un tama�o de 20 componentes
	 */
	public PilaArreglo(){
		this(20);
	}
	
	@Override	
	public int size(){
		return tama�o;
	}
	
	@Override
	public boolean isEmpty(){
		return (tama�o == 0) ;
	}
	
	
	@Override
	public E top() throws EmptyStackException{
		if ( isEmpty() ) throw new EmptyStackException("Pila vacia");
		return datos[tama�o-1];		
	}
	
	@Override	
	public void push(E element){
		if (tama�o==datos.length){
			
			E[] aux= (E[]) new Object[2*datos.length];
			for(int i=0;i<datos.length;i++){
				aux[i]=datos[i];				
			}
			datos=aux;
		}
		datos[tama�o]=element;
		tama�o++;
	}
	
	@Override	
	public E pop() throws EmptyStackException{
		if ( isEmpty() ) throw new EmptyStackException("Pila vacia");
		E aux=datos[tama�o-1];
		datos[tama�o-1]=null;
		tama�o--;
		return aux;
	}
	
}