
package Reglas;

import java.util.List;
import java.util.Stack;

import Final.Nodo;

//while
public class Sentencia3 extends Nodo{
    private Nodo bloque;
    private Nodo expresion;
    private Nodo auxwhile; //falta valida semantica
    
    public Sentencia3(Stack<Nodo> pila){
        pila.pop();
        bloque = pila.pop();
        
        pila.pop();
        pila.pop();//)
        
        pila.pop();
        expresion = pila.pop();
        
        pila.pop();
        pila.pop();//(
        
        pila.pop();
        auxwhile = pila.pop();
    }
    
    @Override
    public Object validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de Sentencia2 (if)");
    	//this.dameInfo();
    	
    	//revisar expresion
    	if(expresion.validaSemantica(tabla, errores, ambito).equals("")) {
    		errores.add(new String[] {"Los tipos no son iguales", "while"});
    	}
    	
    	//revisar bloque
    	while(bloque != null) {
    		bloque.validaSemantica(tabla, errores, ambito);
    		bloque = bloque.dameSiguiente();
    	}
    	
    	return null;
    }
    
    public void dameInfo() {
    	System.out.println("***Sentencia while***");
    	//bloque.dameInfo();
    	//expresion.dameInfo();
    	//auxwhile.dameInfo();
    }
}
