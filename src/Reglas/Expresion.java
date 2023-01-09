
package Reglas;

import Complementos.Id;
import Final.Nodo;

import java.util.List;
import java.util.Stack;

//asignar un id
public class Expresion extends Nodo{
    private Id id;
    
    public Expresion(Stack<Nodo> pila){
        id = new Id(pila);
    }
    
    @Override
    public String validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de Expresion");
    	
    	return id.validaSemantica(tabla, ambito);//regresa el tipo
    }
    
    public Id dameId() {
    	return id;
    }
    
    public void dameInfo() {
    	System.out.println("***Expresion (asignar id)***"+"\n"+
                "Id: " + id.token());
    }
}
