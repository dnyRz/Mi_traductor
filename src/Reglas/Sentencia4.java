
package Reglas;

import java.util.List;
import java.util.Stack;

import Final.Nodo;

//return
public class Sentencia4 extends Nodo{
    private Nodo expresion;
    
    public Sentencia4(Stack<Nodo> pila){
        pila.pop();
        pila.pop();
        
        pila.pop();
        expresion = pila.pop();
        
        pila.pop();
        pila.pop();
        
        //pops de ;
        
        //pops de Expresion
        
        //pops de Return
    }
    
    @Override
    public Object validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de Sentencia4 (return)");
    	String tipoReturn = (String)expresion.validaSemantica(tabla, errores, ambito);
    	String tipoFuncion = "";
    	
    	for(String[] row: tabla) {
    		if(row[1].equals(ambito) && row[2].equals("")) {
    			tipoFuncion = row[0];
    		}
    	}
    	
    	if(!tipoReturn.equals(tipoFuncion)) {
    		errores.add(new String[] {"El tipo de retorn no conicide con el de la funcion", ambito});
    	}
    	
    	return null;
    }
}
