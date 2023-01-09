
package Complementos;

import java.util.List;
import java.util.Stack;

import Final.AnalizadorSemantico;
import Final.Nodo;

public class Id{
    private String token;
    private String clase;
    
    public Id(Stack<Nodo> pila){
        pila.pop();//pop del estado
        token = pila.pop().dameLexema();//pop del token
        clase = "id";
    }
    
    public String validaSemantica(List<String[]> tabla, String ambito) {
    	int totalFilas = tabla.size();
		int i;
		
		for(i=0; i<totalFilas; i++) {
			if((tabla.get(i)[1].equals(token)) && (tabla.get(i)[2].equals(ambito))) {
				return tabla.get(i)[0]; //regresa el tipo
			}
		}
    	return "";//si el id no existe no tiene tipo
    }
    
    public String token() {
    	return token;
    }
}
