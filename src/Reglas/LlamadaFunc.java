
package Reglas;

import Complementos.Id;
import Final.AnalizadorSemantico;
import Final.Nodo;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LlamadaFunc extends Nodo{
    private Id id;
    private Nodo argumentos;
    
    public LlamadaFunc(Stack<Nodo> pila){
        pila.pop(); //estado
        pila.pop(); //)
        
        pila.pop(); //estado
        argumentos = pila.pop(); //argumentos
        
        pila.pop(); //estado
        pila.pop(); //(
        
        id = new Id(pila);
    }
    
    @Override
    public Object validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de LlamadaFunc");
    	String cadenaParametros = "";
    	String tipo = "";
    	Nodo aux = argumentos;
    	
    	//crear cadena de parametros
    	while(aux != null) {
    		tipo = (String)aux.validaSemantica(tabla, errores, ambito);
    		
    		if(tipo == null) {
    			break;
    		}
    		else if(tipo.equals("null")) {
    			break;
    		}
    		else {
    			cadenaParametros += tipo + ".";
    		}
    		aux = aux.dameSiguiente();
    	}
    	
    	if(cadenaParametros.length()>1) {
    		cadenaParametros = cadenaParametros.substring(0, cadenaParametros.length()-1);
    	}    	
    	
    	//revisar si la funcion existe, el ambito siempre es global
    	if(!AnalizadorSemantico.existeVar(tabla, "", id.token(), "")) {
    		errores.add(new String[] {"La funcion no existe", id.token()});
    	}//revisar si los parametros son correctos
    	else if(!AnalizadorSemantico.existeFunc(tabla, "", id.token(), "", cadenaParametros)) {
    		errores.add(new String[] {"Los parametros no corresponden con el tipo de dato", id.token()});
    	}
    	
    	return AnalizadorSemantico.dameTipoFuncion(tabla, id.token(), cadenaParametros);
    }
}
