package TDADiccionario;

import Exceptions.InvalidPositionException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidEntryException;
import Positions.Entry;
import Positions.Entrada;
import Positions.Position;
import TDALista.*;

/**
 * 
 * Diccionario Con Lista
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 *Implementacion de Diccionario con Lista.
 * 
 */

public class DicConLista<K,V> implements Dictionary<K,V> {
	
	protected ListaDoblementeEnlazada<Entry<K,V>> D;
	
	/**
	 * Crea un Diccionario utilizamdo una lista.
	 */
	public DicConLista(){
		D=new ListaDoblementeEnlazada<Entry<K,V>>();
	}
	
	@Override
	public int size(){
		return D.size();
	}
	
	@Override
	public boolean isEmpty(){
		return D.isEmpty();
	}
	
	@Override	
	public Entry<K,V> find(K key) throws InvalidKeyException{
		if (key==null) throw new InvalidKeyException("La clave es invalida");
		Entry<K,V> toReturn=null;
		for(Position<Entry<K,V>> p: D.positions()){
			if (p.element().getKey().equals(key)){
				toReturn= p.element();		
			}
		}
		return toReturn;
	}
	
	@Override
	public Iterable<Entry<K,V>> findAll(K k) throws InvalidKeyException{
		if (k==null) throw new InvalidKeyException("Clave invalida");
		ListaDoblementeEnlazada<Entry<K,V>> l= new ListaDoblementeEnlazada<Entry<K,V>>();
		for (Position<Entry<K,V>> p: D.positions()){
			if (p.element().getKey().equals(k)){
				l.addLast(p.element());
			}
		}
		return l;		
	}
	
	@Override
	public Entry<K,V> insert(K k, V v) throws InvalidKeyException{
		if (k==null) throw new InvalidKeyException("La clave es invalida");
		Entry<K,V>e= new Entrada<K,V>(k,v);
		D.addLast(e);
		return e;		
	}
	
	@Override
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException{
		if (e==null) throw new InvalidEntryException("La entrada es invalida");
		for (Position<Entry<K,V>> p: D.positions()){
			if ( p.element().getKey().equals(e.getKey()) && p.element().getValue().equals(e.getValue())){
				try{
					D.remove(p);
					return e;
				}
				catch(InvalidPositionException s){ s.getMessage();}
			}
		}
		throw new InvalidEntryException("La entrada no está en el diccionario");
	}
	
	@Override
	public Iterable<Entry<K,V>> entries(){
		return D;
	}
	
}
