
package Complementos;

import Final.Nodo;

public class Estado extends Nodo{
    int valor;
    
    public Estado(){
        valor = 0;
    }
    
    public void fijaValor(int e){
        valor = e;
    }
    
    public int dameValor(){
        return valor;
    }
    
    public void dameInfo(){
        System.out.println("***Estado***"+"\n"+
                "valor: " + valor);
    }
}
