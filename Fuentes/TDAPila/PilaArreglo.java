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
	
	private int tamaño;
	private E[] datos;
	
	/**
	 * Crea una pila de tamaño indicado por parametro
	 * @param MAX masimo tamaño que puede tener la pila
	 */
	public PilaArreglo(int MAX){
		tamaño=0;
		datos=(E[]) new Object[MAX];
	}
	
	/**
	 * Crea una pila con un tamaño de 20 componentes
	 */
	public PilaArreglo(){
		this(20);
	}
	
	@Override	
	public int size(){
		return tamaño;
	}
	
	@Override
	public boolean isEmpty(){
		return (tamaño == 0) ;
	}
	
	
	@Override
	public E top() throws EmptyStackException{
		if ( isEmpty() ) throw new EmptyStackException("Pila vacia");
		return datos[tamaño-1];		
	}
	
	@Override	
	public void push(E element){
		if (tamaño==datos.length){
			
			E[] aux= (E[]) new Object[2*datos.length];
			for(int i=0;i<datos.length;i++){
				aux[i]=datos[i];				
			}
			datos=aux;
		}
		datos[tamaño]=element;
		tamaño++;
	}
	
	@Override	
	public E pop() throws EmptyStackException{
		if ( isEmpty() ) throw new EmptyStackException("Pila vacia");
		E aux=datos[tamaño-1];
		datos[tamaño-1]=null;
		tamaño--;
		return aux;
	}
	
}