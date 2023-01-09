
package Reglas;

import Complementos.Id;
import Complementos.Tipo;
import Final.Nodo;

import java.util.Stack;

public class ListaParam extends Nodo{
    private Nodo listaParam;
    private Id id;
    private Tipo tipo;
    
    public ListaParam(Stack<Nodo> pila){
        pila.pop(); //estado
        listaParam = pila.pop();
        id = new Id(pila);
        tipo = new Tipo(pila);
        pila.pop(); //estado
        pila.pop(); //,
    }
    
    public Tipo dameTipo() {
    	return tipo;
    }
    
    public Id dameId() {
    	return id;
    }
    
    public Nodo dameListaParam() {
    	return listaParam;
    }
    
    public void dameInfo() {
    	System.out.println("***ListaParam***"+"\n"+
                "Tipo: " + tipo.token() +"\n"+
                "Id: " + id.token());
        if(listaParam!=null){
            System.out.println("Tiene mas parametros");
        }
        else{
            System.out.println("No tiene mas parametros");
        }
    }
}
