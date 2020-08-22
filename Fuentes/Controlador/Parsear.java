package Controlador;

import java.io.*;

import TDAPila.*;
import TDACola.*;
import TDALista.*;
import TDAArbol.*;
import Exceptions.*;
import Positions.*;

/**
 * Clase Parsear
 * * @author Juan Ignacio Fabrega - Diego Villarroel - Luciano Alberto Arroyo.
 */
public class Parsear {
	
	private String ruta;
	private Stack<String> pila_tokens;	
	private Queue<String> cola_tokens;
	private Tree<Pair<String,PositionList<String>>> arbol;
	
	/**
	 * 
	 * Leyenda Strings:
	 * 
	 * 		Primera letra (minuscula) es la primera letra de la etiqueta.
	 * 		Segunda letra (mayuscula) es "A" si abre etiqueta o es "C" si cierra etiqueta.
	 * 
	 * 		Ejemplo: "cA" es Carpeta que Abre.
	 * 
	 */
	static final String cA = "<carpeta>";
	static final String cC = "</carpeta>";
	static final String nA = "<nombre>";
	static final String nC = "</nombre>";
	static final String lA = "<lista_sub_carpetas>";
	static final String lC = "</lista_sub_carpetas>";
	static final String aA = "<archivo>";
	static final String aC = "</archivo>";
	
	/**
	 * Crea la calse Parsear
	 * @param ruta string.
	 */
	public Parsear(String ruta) {
		
		this.ruta = ruta;
		pila_tokens = new PilaArreglo<String>();		
		cola_tokens = new ColaConArregloCircular<String>();
	}
	
	/**
	 * getCola
	 * @return objeto de tipo cola.
	 */
	public Queue<String> getCola() {
		return cola_tokens;
	}

	/**
	 * getTree
	 * @return objeto de tipo Tree.
	 */
	public Tree<Pair<String,PositionList<String>>> getTree() {
		return arbol;
	}
	
	/**
	 * Verifica que la cola basada en el archivo XML sea correcta.
	 * @return True si la cola es valida. False en caso contrario.
	 */
	public boolean validarCola() {
		boolean esValido = true;
		try {
			if(cola_tokens.front().equals(cA)) 
				esValido = secuenciaCarpeta(); 
		}catch(EmptyQueueException e) {
			esValido = false;
		}
		if(esValido && !cola_tokens.isEmpty())
			esValido = checkEstructura();		
		if(esValido)
			esValido = cola_tokens.isEmpty() && pila_tokens.isEmpty();
		return esValido;
	}
	
	/**
	 * Procedimiento para verificar que la cola basada en el archivo XML sea correcta.
	 * @return Verdadero si la cola es correcta, Falso en caso contrario.
	 */
	private boolean checkEstructura() {
		boolean esValido = true;
		try {
			while(esValido && !cola_tokens.isEmpty() && cola_tokens.front().equals(aA))
				esValido = secuenciaArchivo();
			if(esValido && !cola_tokens.isEmpty() && cola_tokens.front().equals(lA)) 
				pila_tokens.push(cola_tokens.dequeue());
			while(esValido && !cola_tokens.isEmpty() && cola_tokens.front().equals(cA)) {
				esValido = secuenciaCarpeta();
				while(esValido && !cola_tokens.isEmpty() && cola_tokens.front().equals(aA))
					esValido = secuenciaArchivo();
				if(esValido && !cola_tokens.isEmpty() && cola_tokens.front().equals(lA)) {
					pila_tokens.push(cola_tokens.dequeue());
					esValido = checkEstructura();
				}					
				if (esValido)
					esValido = validarCierre();
			}				
			if (esValido)
				esValido = validarCierre();
		}catch(EmptyQueueException e) {
			System.out.println("ERROR: COLA VACIA EN checkEstructura()");
			esValido = false;
		}
		return esValido;
	}
	
	/**
	 * Procedimiento que se ejecuta para verificar la existencia de "carpeta" y "lista_sub_carpetas", ademas revisa si hay "archivo" para verificar.
	 * @return Verdadero si existen los tokens de cierre correctos,  Falso en caso contrario.
	 */
	private boolean validarCierre() {
		boolean esValido = true;
		try {
			while(esValido && !cola_tokens.isEmpty() && !pila_tokens.isEmpty() 
												     && ((cola_tokens.front().equals(cC) && pila_tokens.top().equals(cA)) 
													  || (cola_tokens.front().equals(lC) && pila_tokens.top().equals(lA))
													  || cola_tokens.front().equals(aA))) {
				
					if(cola_tokens.front().equals(cC) && pila_tokens.top().equals(cA)) {
						cola_tokens.dequeue();
						pila_tokens.pop();
					}
					while(esValido && !cola_tokens.isEmpty() && cola_tokens.front().equals(aA)) 
						esValido = secuenciaArchivo();
					if(esValido && !cola_tokens.isEmpty() && cola_tokens.front().equals(lA)) {
						pila_tokens.push(cola_tokens.dequeue());
						esValido = checkEstructura();
					}					
					if(!cola_tokens.isEmpty() && !pila_tokens.isEmpty() && cola_tokens.front().equals(lC) && pila_tokens.top().equals(lA)) {
						cola_tokens.dequeue();
						pila_tokens.pop();
					}
			}
		}catch(EmptyQueueException e) {
			esValido = false;
		}catch(EmptyStackException e) {
			esValido = false;
		}
		return esValido;
	}

	/**
	 * Se ejecuta cuando en el frente de la cola hay un "carpeta"
	 * @return Verdadero si luego de "carpeta", aparece "nombre", luego hay un nombre dado por el usuario, y finalmente aparece "nombre". Falso en caso contrario.
	 */
	private boolean secuenciaCarpeta() {
		boolean esValido;
		try {
			pila_tokens.push(cola_tokens.dequeue()); 
			esValido = cola_tokens.dequeue().equals(nA); 
			if (esValido) {
				cola_tokens.dequeue();
				esValido = cola_tokens.dequeue().equals(nC); 
			}
		} catch(EmptyQueueException e) {
			esValido = false;
		}
		return esValido;
	}
	
	/**
	 * Procedimiento que se ejecuta cuando en el frente de la cola hay un "archivo"
	 * @return Verdadero si luego de "archivo", hay un nombre para el archivo, y luego un "archivo", Falso en caso contrario.
	 */
	private boolean secuenciaArchivo() {
		boolean esValido;
		try {
			cola_tokens.dequeue(); 
			cola_tokens.dequeue(); 
			esValido = cola_tokens.dequeue().equals(aC); 
		}catch(EmptyQueueException e) {
			esValido = false;
		}
		return esValido;
	}
	
	
	/**
	 * Lee todo el archivo XML, cada etiqueta que encuentra, es un "token" de tipo String
	 * Encola cada "token" en la cola "cola_tokens"
	 */
	public void archivoEnCola() {
		String token = "";
		String ultimoAgregado = "";
		boolean cumple = false;
		try {
			FileReader fileReader = new FileReader (ruta);
	        int caracterLeido = fileReader.read(); 
	        while(caracterLeido!= -1) {
	            char caracter = (char) caracterLeido;
            	if(ultimoAgregado.equals(nA) || ultimoAgregado.equals(aA)) {
            		while(caracterLeido!=-1 && !cumple) {  
            			token = token + caracter;
            	        caracterLeido = fileReader.read();
        	            caracter = (char) caracterLeido;
            			cumple = caracter == '<'; 
            			if(cumple) {
            				cola_tokens.enqueue(token);
            				ultimoAgregado = new String(token);
    	            		token = "";
            			}
            		} cumple = false;
            	}
	            if(caracter=='<') {
	            	token = token.replaceAll("\\s", "");
	            	if(token.equals(""))
	            		token = "<";
	            	else
	            		token = token + caracter;
	            }
	            else {
	            	token = token + caracter;
	            	if(caracter=='>') {   
	            		cola_tokens.enqueue(token); 
	            		ultimoAgregado = new String(token);  
	            		token = "";
	            	}
	            }
	        caracterLeido = fileReader.read();
		}
	        if (!token.equals(""))
	        		cola_tokens.enqueue(token);
	        fileReader.close();
		} catch (IOException e)  {
			e.getMessage();
		} 
	}	
	
	/**
	 * Creacion del arbol con los datos del xml.
	 * @return onjeto de tipo Tree.
	 */
	public Tree<Pair<String,PositionList<String>>> crearArbol() {
		
		arbol = new Arbol <Pair <String, PositionList <String> > >();
		Stack<Position<Pair<String,PositionList<String>>>> pila = new PilaArreglo<Position<Pair<String,PositionList<String>>>>();
		try {			
			cola_tokens.dequeue();
			cola_tokens.dequeue(); 
			PositionList<String> listaArchivos = new ListaDoblementeEnlazada<String>(); 
			Pair<String, PositionList<String>> pair = new Pair<String,PositionList<String>>(cola_tokens.dequeue(), listaArchivos);
			arbol.createRoot(pair); 
			cola_tokens.dequeue(); 
			Position<Pair<String,PositionList<String>>> pos = arbol.root();
			pila.push(pos);
			llenarArbol(pos,pila);	
			
		} catch(EmptyQueueException e) {
			e.getMessage();			
		} catch(InvalidOperationException e) {
			e.getMessage();
		} catch(EmptyTreeException e) {
			e.getMessage();
		}
		return arbol;
	}
	
	/**
	 * Procedimiento en el cual se llena el arcbol.
	 * @param pos
	 * @param pila
	 */
	private void llenarArbol(Position<Pair<String,PositionList<String>>> pos, Stack<Position<Pair<String,PositionList<String>>>> pila ) {
		try {	
			while(!cola_tokens.isEmpty() && (cola_tokens.front().equals("<archivo>")))
				cargarArchivo(pila.top());
			if(!cola_tokens.isEmpty() && (cola_tokens.front().equals("<lista_sub_carpetas>"))) {
				cola_tokens.dequeue();
				while(!cola_tokens.isEmpty() && (cola_tokens.front().equals("<carpeta>"))) {
					pila.push(cargarCarpeta(pila.top()));
					while(!cola_tokens.isEmpty() && (cola_tokens.front().equals("<archivo>"))) 
						cargarArchivo(pila.top());
					if(!cola_tokens.isEmpty() && (cola_tokens.front().equals("<lista_sub_carpetas>")))
						llenarArbol(pila.top(),pila);
					while(!cola_tokens.isEmpty() && (cola_tokens.front().equals("</carpeta>") || cola_tokens.front().equals("</lista_sub_carpetas>"))) {
						if(cola_tokens.front().equals("</carpeta>"))
							pila.pop();
						cola_tokens.dequeue();
					}
				}
			}
		}catch(EmptyQueueException e) {
			e.getMessage();
		} catch(EmptyStackException e) {
			e.getMessage();
		}
	}	
	
	/**
	 * CargarCarpeta.
	 * @param pos
	 * @return
	 */
	private Position<Pair<String,PositionList<String>>> cargarCarpeta(Position<Pair<String,PositionList<String>>> pos) {
		Position<Pair<String,PositionList<String>>> toReturn = null;
		try {
			cola_tokens.dequeue(); 
			cola_tokens.dequeue(); 
			PositionList<String> listaArchivos = new ListaDoblementeEnlazada<String>(); 
			Pair<String, PositionList<String>> pair = new Pair<String, PositionList<String>>(cola_tokens.dequeue(), listaArchivos);
			toReturn = arbol.addLastChild(pos, pair);
			cola_tokens.dequeue(); 
		}catch(EmptyQueueException e) {
			e.getMessage();
		} catch(InvalidPositionException e) {
			e.getMessage();
		}
		return toReturn;		
	}
	
	/**
	 * Cargar archivo.
	 * @param pos
	 */
	private void cargarArchivo(Position<Pair<String,PositionList<String>>> pos) {
		try {
			cola_tokens.dequeue(); 
			pos.element().getValor().addLast(cola_tokens.dequeue());
			cola_tokens.dequeue();
		}catch(EmptyQueueException e) {
			e.getMessage();
		}
	}
}