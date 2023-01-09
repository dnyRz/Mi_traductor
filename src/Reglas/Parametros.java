
package Reglas;

import Complementos.Id;
import Complementos.Tipo;
import Final.Nodo;

import java.util.Stack;

public class Parametros extends Nodo{
    private Tipo tipo;
    private Id id;
    private Nodo listaParam;
    
    public Parametros(Stack<Nodo> pila){
        pila.pop(); //estado
        listaParam = pila.pop();
        id = new Id(pila);
        tipo = new Tipo(pila);
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
    	System.out.println("***Parametros***"+"\n"+
                "Tipo: " + tipo.token() +"\n"+
                "Id: " + id.token());
        if(listaParam!=null){
            System.out.println("Tiene mas parametros");
            //listaParam.dameInfo();
        }
        else{
            System.out.println("No tiene mas parametros");
        }
    }
}
