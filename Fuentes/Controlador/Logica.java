package Controlador;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyQueueException;
import Exceptions.EmptyTreeException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidPositionException;
import TDAArbol.*;
import TDALista.*;
import TDACola.*;
import TDADiccionario.*;
import TDAMapeo.*;
 
import Positions.*;

/**
 * Logica
 * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 * Clase logica, la cual nos permite comunicar las datos enconcretos con la InterfazGrafica
 * 
 */

public class Logica {
	
	private Tree<Pair<String,PositionList<String>>> miArbol;
	
	public Logica(){
		
		/*
		 * CREACION DE FORMA MANUAL DEL CONJUNTO DE ARCHIVOS Y CARPETAS.
		 * SE CREA EL SISTEMA DE ARCHIVOS QUE SE ENCUENTRA EN EL ENUNCIADO DEL PROYECTO
		*/
		miArbol=new Arbol<Pair<String,PositionList<String>>>();
		/*
		PositionList<String> listaVacia= new ListaDoblementeEnlazada<String>();
		Pair<String,PositionList<String>> parEdProyecto= new Pair<String,PositionList<String>>("ED-Proyecto",listaVacia);
		PositionList<String> lista2= new ListaDoblementeEnlazada<String>();
		lista2.addFirst("ED-Drive-Ejecutable.jar");
		Pair<String,PositionList<String>> parJars= new Pair<String,PositionList<String>>("Jars",lista2);
		Pair<String,PositionList<String>> parFuentes= new Pair<String,PositionList<String>>("Fuentes",new ListaDoblementeEnlazada<String>());
		PositionList<String> lista3= new ListaDoblementeEnlazada<String>();
		lista3.addFirst("Manual-de-usuario.pdf");
		Pair<String,PositionList<String>> parDocumentacion= new Pair<String,PositionList<String>>("Documentacion",lista3);
		PositionList<String> lista4= new ListaDoblementeEnlazada<String>();
		lista4.addLast("Stack.java");
		lista4.addLast("PilaConArreglo.java");
		lista4.addLast("EmptyStackException.java");
		Pair<String,PositionList<String>> parTDAPila= new Pair<String,PositionList<String>>("TDAPila",lista4);
		PositionList<String> lista5= new ListaDoblementeEnlazada<String>();
		lista5.addLast("Index.html");
		Pair<String,PositionList<String>> parJavadoc= new Pair<String,PositionList<String>>("Javadoc",lista5);
						
		try{
			miArbol.createRoot(parEdProyecto);
			miArbol.addLastChild(miArbol.root(), parJars);
			Position<Pair<String,PositionList<String>>> posFuentes=miArbol.addLastChild(miArbol.root(), parFuentes);
			Position<Pair<String,PositionList<String>>> posDocumentacion=miArbol.addLastChild(miArbol.root(), parDocumentacion);
			miArbol.addFirstChild(posFuentes, parTDAPila);
			miArbol.addLastChild(posDocumentacion, parJavadoc);
		}
		catch(InvalidOperationException e){e.getMessage();}
		catch(InvalidPositionException f){f.getMessage();}
		catch(EmptyTreeException g){g.getMessage();}
		*/
	}
	
	/**
	 * Genera la jerarquia en casao de que sea posible.
	 * @param ruta del archivo .xml
	 * @return Verdadero en caso que se haya podido crear la jerarquia, Falso en caso contrario.
	 */
	public boolean generarJerarquia(String ruta){
		boolean toReturn=false;
		Parsear archivo= new Parsear(ruta);
		archivo.archivoEnCola();
		archivo.crearArbol();
		archivo.archivoEnCola();
		if(archivo.validarCola()){
			miArbol= archivo.getTree();	
			toReturn=true;
		}
		return toReturn; 
	}
	
	/**
	 * Cheque si la ruta que me pasan por parametros es valida.
	 * @param ruta del directorio en formato String.
	 * @return null si la ruta no existe, la posicion del directorio en caso contrario.
	 */
	public  Position<Pair<String,PositionList<String>>> esRutaValida(String ruta){
		
		Position<Pair<String,PositionList<String>>> pos=null;
		boolean existe=false;
		String carpeta="";
		boolean hayError=false;
		boolean encontre=false;
		Queue<String> colaRuta= new ColaConArregloCircular<String>();
		for(int i=0; i<ruta.length();i++){
			Character c=ruta.charAt(i);
			if (c!='\\'){
				carpeta=carpeta+c;
			}
			else{
				colaRuta.enqueue(carpeta);
				carpeta="";
			}
		}
		if (carpeta!=""){
			colaRuta.enqueue(carpeta);
		}
		try{
			pos=miArbol.root();
			//Position<Pair<String,PositionList<String>>> pos=miArbol.root();
			String nombre= miArbol.root().element().getClave();
			Position<Pair<String,PositionList<String>>> nuevaPos;
			if (nombre.equals(colaRuta.dequeue())){
				while(!colaRuta.isEmpty() && !hayError){
					Iterable<Position<Pair<String,PositionList<String>>>> listaHijos= miArbol.children(pos);
					Iterator<Position<Pair<String,PositionList<String>>>> it2= listaHijos.iterator();
					while(it2.hasNext() && !encontre){
						nuevaPos= it2.next();
						if(nuevaPos.element().getClave().equals(colaRuta.front())){
							colaRuta.dequeue();
							encontre=true;
							pos = nuevaPos;
						}
					}
					if(!encontre){
						hayError=true;
					}
					encontre=false;
				}
				if(colaRuta.isEmpty() && !hayError){
					existe=true;
				}
				
			}
			if (!existe){
				pos=null;				
			}
		}
		catch(EmptyTreeException e){e.getMessage();}
		catch(EmptyQueueException f){f.getMessage();}
		catch(InvalidPositionException g){g.getMessage();}
	
		return pos;
	}
	
		
	/**
	 * Agrega un archivo.
	 * @param nombre nombre del nuevo archivo
	 * @param ruta posicion de la ruta donde se quiere agregar el archivo.
	 */
	public void agregarArchivo(String nombre, Position<Pair<String,PositionList<String>>> ruta){
		
		ruta.element().getValor().addLast(nombre);
	}
			
	/**
	 * Elimina un archivo
	 * @param nombre del archivo que se desea eliminar.
	 * @param ruta posicion de la ruta donde se quiere eliminar el archivo.
	 * @return True si pudo eliminar el arhcivo, Falso en caso contrario.
	 */
	public boolean eliminarArchivo(String nombre, Position<Pair<String,PositionList<String>>> ruta){
		boolean encontre= false;
		try{
			Iterator<Position<String>> it=ruta.element().getValor().positions().iterator();
			while(it.hasNext() && !encontre){
				Position<String> pos=it.next();
				if (pos.element().equals(nombre)){
					encontre=true;
					ruta.element().getValor().remove(pos);
				}
			}
		}
		catch(InvalidPositionException e ){e.getMessage();}
		
		return encontre;
	}
	
	/**
	 * Agrega un nuevo directorio
	 * @param nombre nombre del nuevo Directorio.
	 * @param ruta posicion de ruta donde se quiere agregar el nuevo directorio.
	 */
	public void agregarDirectorio(String nombre, Position<Pair<String,PositionList<String>>> ruta){
		
		try{
			PositionList<String> lista= new ListaDoblementeEnlazada<String>();
			Pair<String,PositionList<String>> nuevaCarpeta= new Pair<String,PositionList<String>>(nombre,lista);
			if(ruta==miArbol.root()){
				miArbol.addLastChild(miArbol.root(), nuevaCarpeta);
				
			}
			else{
				miArbol.addLastChild(ruta, nuevaCarpeta);
			}
		}
		catch (EmptyTreeException e){e.getMessage();}
		catch (InvalidPositionException e2) {e2.getMessage();}
	}
	
	/**
	 * Elimina el directorio pasado por parametro
	 * @param ruta posicion de la ruta del directorio que se desea eliminar.
	 */
	public void eliminarDirectorio(Position<Pair<String,PositionList<String>>> ruta){
		eliminarDirectorioAux(ruta);		
	}
	
	/**
	 * Metodo privado para eliminar un Directorio.
	 * @param pos La posicion del directorio que se quiere eliminar.
	 */
	private void eliminarDirectorioAux(Position<Pair<String,PositionList<String>>> pos){
		
		try{
			
			if (miArbol.isExternal(pos)){
				miArbol.removeExternalNode(pos);
			}
			else{
				for (Position<Pair<String,PositionList<String>>> p: miArbol.children(pos)){
					eliminarDirectorioAux(p);
				}
				miArbol.removeNode(pos);
			}
		}
		catch(InvalidPositionException e){e.getMessage();}
	}
	
	/**
	 * Devuelve un objeto par.
	 * @return devuelvo un objeto de tipo Pair, que como clave tiene la cantidad de directorios y como valor la cantidad de archivos.
	 */
	public Pair<String,String> cantidad(){
		int cantDirectorios=miArbol.size();
		int cantArchivos=0;
		Iterator<Pair<String,PositionList<String>>> it =miArbol.iterator();
		while(it.hasNext()){
			cantArchivos=cantArchivos+ it.next().getValor().size();
		}
		Pair<String,String> cant= new Pair<String,String>(Integer.toString(cantDirectorios),Integer.toString(cantArchivos));
		return cant;		
	}
	
	/**
	 * Retorna la cantidad de Directorios.
	 * @return cantidad de directorio que existen actualmente.
	 */
	public int size(){
		return miArbol.size();
	}	
	
	/**
	 * Retorna el listado por niveles en forma de string
	 * @return listado por niveles
	 */
	public String porNiveles(){
		String str=generarString(miArbol);
		return str;		
	}
	
	/**
	 * Retorna el listado por niveles en forma de string
	 * @param t arbol el cual se va a recorrer
	 * @return listado por niveles en forma de string
	 */
	private static<E> String generarString(Tree<Pair<String,PositionList<String>>> t) {
		String s = "";
		try {
			Queue<Position<Pair<String,PositionList<String>>>> cola = new ColaConArregloCircular<Position<Pair<String,PositionList<String>>>>();
			//Queue<String> colaArchivos= new ColaConArregloCircular<String>();
			cola.enqueue(t.root());
			cola.enqueue(null);
			s=t.root().element().getClave()+"\n";
			Position<Pair<String,PositionList<String>>> v = null;
			while(!cola.isEmpty()) {
				v = cola.dequeue();
				if(v!=null) {
					Iterator<String> ittt= v.element().getValor().iterator();
					while (ittt.hasNext()){
						s = s + ittt.next()+" ";						
					}
					for(Position<Pair<String,PositionList<String>>> w : t.children(v)){
						s = s + w.element().getClave()+" ";
						cola.enqueue(w);
					}
				} else {
					if(!cola.isEmpty()){
						cola.enqueue(null);
						s = s + "\n";
					}
				}
			}
			
		}catch(EmptyTreeException e) {
			System.out.println(e.getMessage());
		}catch(EmptyQueueException e) {
			e.printStackTrace();		}catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		return s;
	}
	
	/**
	 * Metodo que devuelve de forma ordenada un todos los archivos que tienen la misma extension. 
	 * @return un string que contiene el nombre de todos los archivos que tienen la mism extension.
	 */
	public String porExtension(){
		String toReturn="";
		try{
			PositionList<String> extensiones= new ListaDoblementeEnlazada<String>();
			Dictionary<String,String> dic=porExtensionAux();
			Iterator<Entry<String,String>> it= dic.entries().iterator();
			while(it.hasNext()){
				Entry<String,String> entrada= it.next();
				String clave=entrada.getKey();
				Iterator<String> itExt=extensiones.iterator();
				Boolean encontre=false;
				while (!encontre && itExt.hasNext()){
					if (itExt.next().equals(clave)){
						encontre=true;
					}
				}
				if (!encontre){
					extensiones.addFirst(clave);
					Iterator<Entry<String,String>> it2= dic.findAll(clave).iterator();
					String nombreArchivos="";
					while (it2.hasNext()){
						nombreArchivos=nombreArchivos+it2.next().getValue()+", ";				
					}
					toReturn=toReturn+"\n"+clave+"= "+nombreArchivos;
				}
							
			}
		}
		catch(InvalidKeyException e){e.getMessage();}
		return toReturn;		
	}
	
	/**
	 * Crea un diccionario que tiene como clave las extensiones de los archivos y como valor el nombre de los archivos
	 * @return un diccionario que tiene como clave las extensiones de los archivos y como valor el nombre de los archivos
	 */
	private Dictionary<String,String> porExtensionAux(){
		
		Iterator<Pair<String,PositionList<String>>> it=miArbol.iterator();
		Dictionary<String,String> dic= new DicHashAbierto<String,String>();
		String nombreArchivo="";
		String extension="";
		while (it.hasNext()){
			Iterator<String> it2=it.next().getValor().iterator();
			while(it2.hasNext()){
				nombreArchivo=it2.next();
				extension="";
				boolean encontre= false;
				int i=0;
				while (!encontre && i<nombreArchivo.length() ){
					Character c= nombreArchivo.charAt(i);
					if (c=='.'){
						encontre=true;
						for(int nro=i; nro<nombreArchivo.length(); nro++){
							extension=extension+nombreArchivo.charAt(nro);
						}
					}
					i++;					
				}
				if (encontre){
					try {
						dic.insert(extension, nombreArchivo);
					} catch (InvalidKeyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return dic;
	}
	
	/**
	 * Metodo que me devuelve un string que indica la ruta del directorio y su profundidad.
	 * @return un string que indica la ruta del directorio y su profundidad.
	 */
	public String porProfundidad(){
		String s="";
		Map<String,Integer> map= new MapeoConHashCerrado<String,Integer>();
		String toReturn="";
		try {
			porProfundidadAux(miArbol,miArbol.root(),0,s,map);
			Iterator<Entry<String,Integer>>it=map.entries().iterator();
			while(it.hasNext()){
				Entry<String,Integer> entrada= it.next();
				toReturn=toReturn+"\n"+ entrada.getKey()+" ====> "+entrada.getValue();
			}
		} catch (EmptyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;	
	}
	
	/**
	 * Metodo recursivo que introduce dentro m , una entrada con clave la ruta de una carpeta y como valor su profundidad
	 * @param a arbol donde se encuentras las carpetas.
	 * @param p posicion de una carpeta dentro del arbol a.
	 * @param i entero que represean la profundidad actual de la carpeta.
	 * @param s String 
	 * @param m Map donde se van a encontrar todas las carpetas y sus respectivas profundidades.
	 */
	private void porProfundidadAux(Tree<Pair<String,PositionList<String>>> a,Position<Pair<String,PositionList<String>>> p,int i, String s, Map<String,Integer> m){
		String ss= s+p.element().getClave()+"/";
		try {
			m.put(s+p.element().getClave()+"/", i);
			for(Position<Pair<String,PositionList<String>>> w: a.children(p)){
				porProfundidadAux(a,w,i+1,ss,m);
			}
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvalidPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Devuelve Verdadero en caso de que el parametro sea la raiz, Falso caso contrario.
	 * @param ruta a chequear
	 * @return Verdadero en caso que el parametro sea la raiz, Falso caso contrario
	 */
	public boolean esLaRaiz(String ruta){
		boolean toReturn=false;
		try{
			String carpeta="";
			Queue<String> colaRuta= new ColaConArregloCircular<String>();
			for(int i=0; i<ruta.length();i++){
				Character c=ruta.charAt(i);
				if (c!='\\'){
					carpeta=carpeta+c;
				}
				else{
					colaRuta.enqueue(carpeta);
					carpeta="";
				}
			}
			if (carpeta!=""){
				colaRuta.enqueue(carpeta);
			}
			if (colaRuta.size()==1 && colaRuta.front().equals(miArbol.root().element().getClave())){
				toReturn= true;
			}
		}
		catch(EmptyTreeException e){e.getMessage();}
		catch(EmptyQueueException e1){e1.getMessage();}
		return toReturn;
	}
	
	/**
	 * Mueve un directorio dentro de otro.
	 * @param rutaD1 ruta del directorio que se desea mover.
	 * @param rutaD2 ruta del directorio donde se desea mover el directorio.
	 */
	public void moverDirectorio(String rutaD1, String rutaD2){
		Position<Pair<String,PositionList<String>>> posD1= esRutaValida(rutaD1);
		Position<Pair<String,PositionList<String>>> posD2= esRutaValida(rutaD2);
		moverDirectorioAux(posD1, posD2);
		eliminarDirectorioAux(posD1);
	}
	
	/**
	 * Mueve un directorio dentro de otro.
	 * @param posD1 posicion del directorio que se desea mover.
	 * @param posD2 posicion del directorio en donde se desea mover al directorio.
	 */
	private void moverDirectorioAux(Position<Pair<String,PositionList<String>>> posD1, Position<Pair<String,PositionList<String>>> posD2){
		
		try {
			String clave= posD1.element().getClave();
			Iterator<String> it=posD1.element().getValor().iterator();
			PositionList<String> lista= new ListaDoblementeEnlazada<String>();
			while(it.hasNext()){
				lista.addLast(it.next());
			}
			Pair<String,PositionList<String>> e= new Pair<String,PositionList<String>>(clave,lista);
			Position<Pair<String,PositionList<String>>> nuevoPadre= miArbol.addLastChild(posD2, e);
			for(Position<Pair<String,PositionList<String>>> p: miArbol.children(posD1)){
				moverDirectorioAux(p,nuevoPadre);
			}
		} 
		catch (InvalidPositionException e1) {e1.printStackTrace(); }
	}
	
}
