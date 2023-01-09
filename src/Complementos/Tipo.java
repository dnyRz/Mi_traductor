
package Complementos;

import java.util.Stack;

import Final.Nodo;

public class Tipo {
    private String token;
    
    public Tipo(Stack<Nodo> pila){
        pila.pop();//pop del estado
        token = pila.pop().dameLexema();//pop del token
    }
    
    public String token() {
    	return token;
    }
}
