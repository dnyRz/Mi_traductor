
package Reglas;

import java.util.List;
import java.util.Stack;

import Final.Nodo;

//if
public class Sentencia2 extends Nodo{
    private Nodo otro;
    private Nodo sentenciaBloque;
    private Nodo expresion;
    private Nodo auxif; //falta valida semantica
    
    public Sentencia2(Stack<Nodo> pila){
        pila.pop();
        otro = pila.pop();
        
        pila.pop();
        sentenciaBloque = pila.pop();
        
        pila.pop();
        pila.pop();//)
        
        pila.pop();
        expresion = pila.pop();
        
        pila.pop();
        pila.pop();//(
        
        pila.pop();
        auxif = pila.pop();
 
    }
    
    @Override
    public Object validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de Sentencia2 (if)");
    	//this.dameInfo();
    	
    	//revisar expresion
    	//expresion.dameInfo();
    	if(expresion.validaSemantica(tabla, errores, ambito).equals("")) {
    		errores.add(new String[] {"Los tipos no son iguales", "if"});
    	}
    	
    	//revisar bloque de if
    	//sentenciaBloque.dameInfo();
    	while(sentenciaBloque != null) {
    		sentenciaBloque.validaSemantica(tabla, errores, ambito);
    		sentenciaBloque = sentenciaBloque.dameSiguiente();
    	}
    	
    	//revisar else
    	while(otro!=null) {
    		otro.validaSemantica(tabla, errores, ambito);
    		otro = otro.dameSiguiente();
    	}
    	
    	return null;
    }
    
    public void dameInfo() {
    	System.out.println("***Sentencia if***");
    	//otro.dameInfo();
    	//sentenciaBloque.dameInfo();
    	//expresion.dameInfo();
    	//auxif.dameInfo();
    }
}
