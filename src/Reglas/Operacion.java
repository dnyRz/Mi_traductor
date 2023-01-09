
package Reglas;

import java.util.List;
import java.util.Stack;

import Final.Nodo;

public class Operacion extends Nodo{
    Nodo operadorDerecho;
    String operacion;
    Nodo operadorIzquierdo;
	
    public Operacion(Stack<Nodo> pila){
        pila.pop();
        operadorDerecho = pila.pop();
        
        pila.pop();
        operacion = pila.pop().dameLexema();
        
        pila.pop();
        operadorIzquierdo = pila.pop();
    }
    
    public String validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de Expresion2");
    	String tipo1, tipo2;
    	
    	//el operadorDerecho es una constante
    	//el operadorIzquierdo pude ser constante u otra operacion
    	tipo1 = (String)operadorDerecho.validaSemantica(tabla, errores, ambito);
    	tipo2 = (String)operadorIzquierdo.validaSemantica(tabla, errores, ambito);
    	
    	//System.out.println("REGRESANDO: " + tipo1);
    	//System.out.println("REGRESANDO: " + tipo2);
    	
    	if(tipo1.equals(tipo2)) {//los tipos de dato son iguales
    		return tipo1;
    	}
    	else if(tipo1.equals("") && !tipo2.equals("")) {//si uno de los dos es vacio es porque no existe la variable
    		return "";
    	}
    	else if(tipo2.equals("") && !tipo1.equals("")) {//si uno de los dos es vacio es porque no existe la variable
    		return "";
    	}
    	/*else if(!tipo1.equals("") && !tipo2.equals("")) {
    		return "";
    	}*/
    	else if(!tipo1.equals("") || !tipo2.equals("")) {
    		return "1";
    	}
    	
    	return "";
    }
    
    public void dameInfo() {
    	System.out.println("***Operacion***"+"\n"+
                "Operador derecho: " + operadorDerecho.dameLexema()+"\n"+
                "Expresion: " + operacion + "\n"+
                "Operador izquierdo: " + operadorIzquierdo.dameLexema());
    	operadorIzquierdo.dameInfo();
    }
}
