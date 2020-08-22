package TDACola;

import Exceptions.EmptyQueueException;

/**
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Implementación de una cola con arreglo circular.
 * 
 */

public class ColaConArregloCircular<E> implements Queue<E> {
	
	private int i; //inicio
	private int f; //fin
	private E [] cola;
	private static final int longitud=10;
	
	/**
	 * Crea una Cola utilizando un arreglo circular.
	 */
	public ColaConArregloCircular(){
		cola= (E[]) new Object[longitud];
		i=0;
		f=0;
	}
	
	@Override
	public int size(){
		return ( (cola.length-i+f)%(cola.length) );
	}
	
	@Override
	public boolean isEmpty(){
		return (i==f);
	}
	
	@Override
	public E front() throws EmptyQueueException{
		if (isEmpty()) throw new EmptyQueueException("Cola vacia");
		return cola[i];
		
	}
	
	/**
	 * Copiar.
	 * @param n 
	 * crea un nuevo arreglo con mayor tamaño, y pasa todos los datos del anterior arreglo al recientemente creado.
	 */
	private E[] copiar(int n){
		E[] aux= (E[]) new Object[2*cola.length];
		for (int j=0; j<size(); j++){
			aux[j]=cola[n];
			n=(n+1)%(cola.length);
		}
		return aux;
	}
	
	@Override
	public void enqueue(E element){
		if ( cola.length-1==size() ){
			E[] aux= copiar(i);
			f=size();
			i=0;
			cola=aux;
		}
		cola[f]= element;
		f=(f+1)%(cola.length);
	}
	
	@Override
	public E dequeue() throws EmptyQueueException{
		if (isEmpty()) throw new EmptyQueueException("Cola vacia");
		E aux= cola[i];
		cola[i]= null;
		i=(i+1)%(cola.length);
		return aux;
	}
}
