 package TDAMapeo;

import Positions.Entry;
import Positions.Entrada;
import TDALista.*;
import Exceptions.InvalidKeyException;

/**
 * 
 * MapeoConHashAbierto
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 *Implementacion de Mapeo utilizando Hash Cerrado.
 * 
 */

public class MapeoConHashCerrado<K,V> implements Map<K,V> {
	
	int cantEntradas;
	//e
	protected Entrada<K,V> [] datos;
	protected final Entrada<K,V> bucketNoUsado= new Entrada<K,V>(null,null);
	protected final Entrada<K,V> bucketDisponible= new Entrada<K,V>(null,null);
	
	/**
	 * Crea una Mapeo con HashCerrado.
	 */
	public MapeoConHashCerrado(){
		cantEntradas=0;
		datos=(Entrada<K,V> []) new Entrada[97];
		for(int i=0;i<datos.length;i++){
			datos[i]= bucketNoUsado;
		}
	}
	
	/**
	 * Funcion Hash.
	 * @param key clave a la cual se le aplica la funcion hash.
	 * @return Número correspondiente al aplicar la funcion hash.
	 */
	private int h(K key ){
		int h = key.hashCode();
		if (h<0) h= h*(-1);
		return (h % datos.length);
	}
	
	/**
	 * Factor de Carga.
	 * @return Número correspondiente al factor de carga(cantidad de entradas/largo del arreglo).
	 */	
	private float factorDeCarga(){
		return (cantEntradas/ datos.length);
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
		int cant=datos.length;
		Entrada<K,V>[] datosAnterior=datos;
		datos= (Entrada<K,V>[]) new Entrada[siguientePrimo(cant*2)];
		for(int i=0; i<datos.length;i++){
			datos[i]=bucketNoUsado;			
		}
		for(int j=0;j<datosAnterior.length;j++){
			if(datosAnterior[j]!=bucketNoUsado && datosAnterior[j]!=bucketDisponible){
				Entrada<K,V> entrada= new Entrada<K,V>(datosAnterior[j].getKey(),datosAnterior[j].getValue());
				boolean encontre=false;
				int e=0;
				int hash= h(entrada.getKey());
				while(!encontre){
					if(datos[(hash+e)%datos.length]== bucketNoUsado){
						encontre=true;
						datos[(hash+e)%datos.length]= entrada;
					}
					e++;
				}
				
			}
		}
	}
	
	/**
	 * @see Map#size()
	 */
	public int size(){
		return cantEntradas;
	}
	
	/**
	 * @see Map#isEmpty()
	 */
	public boolean isEmpty(){
		return (cantEntradas==0);
	}
	
	/**
	 * busca el primer lugar donde puede insertar la entrada, y la inserta.
	 * @param entrada a insertar.
	 * @return el valor de la vieja entrada, si no habia una entrada con la misma key devuelve null.
	 */
	private V buscarInsertar(Entrada<K,V> entrada){
		V toReturn=null;
		K key= entrada.getKey();
		int hash= h(key);
		int primerDisponible=-1;
		int cantVueltas=0;
		boolean encontre=false;
		while(!encontre && cantVueltas<datos.length){
			 cantVueltas++;
			 if (datos[hash]== bucketNoUsado){
				 encontre=true;
				 if(primerDisponible==-1){
					 datos[hash]=entrada;
					 cantEntradas++;
				 }
				 else{
					 datos[primerDisponible]=entrada;
					 cantEntradas++;
				 }
			 }
			 else{
				 if( datos[hash]==bucketDisponible){
					 if(primerDisponible==-1){
						 primerDisponible=hash;
					 }
				 }
				 else{
					 if(datos[hash].getKey().equals(key)){
						 encontre=true;
						 toReturn=datos[hash].getValue();
						 datos[hash]=entrada;						 
					 }
				 }				 
			}
			 hash=(hash+1)%datos.length;
		}
		if(!encontre){
			datos[primerDisponible]=entrada;
			cantEntradas++;
		}
		return toReturn;		
	}
	
	/**
	 * @see Map#put(Object, Object)
	 */
	public V put(K key, V value) throws InvalidKeyException{
		//Si el mapeo no tiene una entrada con clave key, inserta una entrada con clave key y valor value en el mapeo y devuelve null. 
		//Si el mapeo ya tiene una entrada con clave key, entonces remplaza su valor y retorna el viejo valor.
				
		if (key==null) throw new InvalidKeyException("Clave invalida");
		if (factorDeCarga()>0.5){
			redimensionar();			
		}
		Entrada<K,V> entrada= new Entrada<K,V>(key,value);
		return buscarInsertar(entrada);
	}
	
	/**
	 * @see Map#get(Object)
	 */
	public V get(K key)throws InvalidKeyException{
		//Busca una entrada con clave igual a una clave dada y devuelve el valor asociado, si no existe retorna nulo.
		
		if(key==null) throw new InvalidKeyException("Clave Invalida");
		V toReturn=null;
		int hash=h(key);
		boolean encontre=false;
		int cantVueltas=0;
		while(!encontre && cantVueltas<datos.length){
			cantVueltas++;
			if(datos[hash]==bucketNoUsado){
				encontre=true;
			}
			if(datos[hash]!=bucketNoUsado && datos[hash]!=bucketDisponible){
				if(datos[hash].getKey().equals(key)){
					encontre=true;
					toReturn=datos[hash].getValue();
				}				
			}
			hash=(hash+1)%datos.length;
		}
		return toReturn;
	}
	
	/**
	 * @see Map#remove(Object)
	 */
	public V remove(K key) throws InvalidKeyException{
		//Remueve la entrada con la clave dada en el mapeo y devuelve su valor, o nulo si no fue encontrada.
		
		if(key==null) throw new InvalidKeyException("Clave Invalida");
		V toReturn=null;
		int hash=h(key);
		boolean encontre=false;
		int cantVueltas=0;
		while(!encontre && cantVueltas<datos.length){
			cantVueltas++;
			if(datos[hash]==bucketNoUsado){
				encontre=true;
			}
			if(datos[hash]!=bucketNoUsado && datos[hash]!=bucketDisponible){
				if(datos[hash].getKey().equals(key)){
					encontre=true;
					toReturn=datos[hash].getValue();
					datos[hash]=bucketDisponible;
					cantEntradas--;
				}
			}
			hash= (hash+1)%datos.length;
		}
		return toReturn;		
	}
	
	/**
	 * @see Map#keys()
	 */
	public Iterable<K> keys(){
		PositionList<K> l= new ListaDoblementeEnlazada<K>();
		for(int i=0;i<datos.length;i++){
			if(datos[i]!=bucketDisponible && datos[i]!=bucketNoUsado){
				K key=datos[i].getKey();
				l.addLast(key);
			}
		}
		return l;
	}
	
	/**
	 * @see Map#values()
	 */
	public Iterable<V> values(){
		PositionList<V> l= new ListaDoblementeEnlazada<V>();
		for(int i=0;i<datos.length;i++){
			if(datos[i]!=bucketDisponible && datos[i]!=bucketNoUsado){
				V value=datos[i].getValue();
				l.addLast(value);
			}
		}
		return l;
	}
	
	/**
	 * @see Map#entries()
	 */
	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>> l= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i=0;i<datos.length;i++){
			if(datos[i]!=bucketDisponible && datos[i]!=bucketNoUsado){
				l.addLast(datos[i]);
			}
		}
		return l;
	}

}
