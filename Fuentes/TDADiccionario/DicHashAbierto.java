package TDADiccionario;

import java.util.Iterator;

import Exceptions.InvalidKeyException;
import Exceptions.InvalidEntryException;
import Positions.Entry;
import TDALista.*;

/**
 * 
 * Diccionario Con Hash Abierto.
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 *Implementacion de Diccionario con Hash Abierto.
 * 
 */

public class DicHashAbierto<K,V> implements Dictionary<K,V>{
	
	private Dictionary<K,V> []A;
	private int n; //cantidad de entrada en el diccionario
	private int N=97; //nro primo
	
	/**
	 * Crea un Diccionario.
	 */
	public DicHashAbierto(){
		n=0;
		A=(Dictionary<K,V> []) new DicConLista[N];
		for(int i=0;i<N;i++){
			A[i]=new DicConLista<K,V>();
		}
	}
	
	@Override
	public int size(){return n;}
	
	@Override
	public boolean isEmpty(){return n==0;}
	
	/**
	 * Funcion Hash.
	 * @param key clave a la cual se le aplica la funcion hash.
	 * @return Número correspondiente al aplicar la funcion hash.
	 */
	private int h(K key ){
		int i= key.hashCode();
		return (i%N);
	}
	
	/**
	 * Factor de Carga.
	 * @return Número correspondiente al factor de carga(cantidad de entradas/largo del arreglo).
	 */
	private float factorDeCarga(){
		return (n/N);
	}
	
	/**
	 * Siguiente Primo.
	 * @param n un numero entero.
	 * @return el siguiente numero primo al pasado por parametro.
	 */
	private int siguientePrimo(int n){
		int nro=n+1;
		int i=1;
		int cant=0;
		boolean encontre=false;
		while(!encontre){
			while(i<10){
				if (nro%i==0){ cant++;}	
				i++;
			}
			if (cant==2){ 
				encontre=true;
			}	
			else{
				i=1;
				cant=0;
				nro++;
			}
		}
		return nro;		
	}
	
	/**
	 * Redimensionar.
	 * Se encargar de crear un nuevo arreglo y cololar las entradas del anterior arreglo en el nuevo.
	 */
	private void redimensionar(){
		int nuevoN= siguientePrimo(2*N);
		Dictionary<K,V> [] Aaux= (Dictionary<K,V> []) new DicConLista[nuevoN];
		for (int i=0;i<nuevoN;i++){
			Aaux[i]= new DicConLista<K,V>();			
		}
		for(int i=0;i<N;i++){
			Iterator<Entry<K,V>> it=A[i].entries().iterator();
			while(it.hasNext()){
				try{
					Entry<K,V> entrada=it.next();
					A[i].remove(entrada);
					K k=entrada.getKey();
					V v=entrada.getValue();
					int u= (k.hashCode())%nuevoN;
					Aaux[u].insert(k,v);
					}
				catch(InvalidKeyException e){e.getMessage();}
				catch(InvalidEntryException ee){ee.getMessage();}
			}
		}
		
		N= nuevoN;
		A=Aaux;
	}
	
	@Override
	public Entry<K,V> find(K key) throws InvalidKeyException{
		if (key==null) throw new InvalidKeyException("Clave invalida");
		return A[h(key)].find(key);		
	}
	
	@Override
	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException{
		if (key==null) throw new InvalidKeyException("Clave invalida");
		return A[h(key)].findAll(key);
	}
	
	@Override
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException{
		if (key==null) throw new InvalidKeyException("Clave invalida");
		if (factorDeCarga()>0.5){
			redimensionar();
		}
		Entry<K,V> toReturn=A[h(key)].insert(key, value);
		n++;
		return toReturn;
	}
	
	@Override
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException{
		if(e==null) throw new InvalidEntryException("Entrada invalida");
		Entry<K,V> toReturn=A[h(e.getKey())].remove(e);
		if (toReturn!=null){ n--;}
		return toReturn;
	}
	
	@Override
	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>> l= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i=0;i<A.length;i++){
			Iterator<Entry<K,V>> it=A[i].entries().iterator();
			while(it.hasNext()){
				l.addLast(it.next());
			}
		}
		return l;		
	}

}
