
package Reglas;

import Complementos.Id;
import Final.AnalizadorSemantico;
import Final.Nodo;

import java.util.List;
import java.util.Stack;

public class ListaVar extends Nodo{
    private Id id;
    private Nodo listaVar;
    
    public ListaVar(Stack<Nodo> pila){
        pila.remove(pila.size()-1);//estado
        listaVar = pila.remove(pila.size()-1);
        id = new Id(pila);
        pila.pop();//estado
        pila.pop();//,
    }
    
    public Id dameId(){
        return id;
    }
    
    public Nodo dameListaVar(){
        return listaVar;
    }
    
    public void dameInfo(){
        System.out.println("***ListaVar***");
        System.out.println("id = " + id.token());
        if(listaVar != null) {
        	System.out.println("Tiene listaVar");
        }
        else {
        	System.out.println("NO tiene listaVar");
        }
    }
}
