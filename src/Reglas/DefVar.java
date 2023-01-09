
package Reglas;

import Complementos.Id;
import Complementos.Tipo;
import Final.AnalizadorSemantico;
import Final.Nodo;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DefVar extends Nodo{
    private Tipo tipo;
    private Id id;
    private Nodo listaVar;
    
    public DefVar(Stack<Nodo> pila){//constructor completo
        pila.remove(pila.size()-1);
        pila.remove(pila.size()-1);
        pila.remove(pila.size()-1);
        listaVar = pila.remove(pila.size()-1);
        id = new Id(pila); //pops de id
        tipo = new Tipo(pila); //pops de tipo
    }
    
    @Override
    public Object validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de DefVar");
    	ListaVar aux;
    	
    	if(!AnalizadorSemantico.existeVar(tabla, tipo.token(), id.token(), ambito)) {
    		//agregar la variable a la tabla
    		tabla.add(new String[] {tipo.token(), id.token(), ambito, ""});
    	}
    	else {
    		//agregar a errores
    		System.err.println("La variable ya existe");
    		errores.add(new String[] {"La variable ya existe", id.token()});
    	}
    	
    	//analizar la lista de variables en caso int a, b, c ...    	
    	try {//siempre tiene un nodo en listaVar pero puede ser un nodo "vacio"
    		aux = (ListaVar)listaVar;
    	}catch (Exception e) {
			aux = null;
		}
    	
    	while(aux != null) {
			if(!AnalizadorSemantico.existeVar(tabla, tipo.token(), aux.dameId().token(), ambito)) {
	    		//agregar la variable a la tabla
	    		tabla.add(new String[] {tipo.token(), aux.dameId().token(), ambito, ""});
	    	}
	    	else {
	    		//agregar a errores
	    		//System.err.println("La variable ya existe");
	    		errores.add(new String[] {"La variable ya existe", aux.dameId()
	    				.token()});
	    	}
			
			try {
				aux = (ListaVar)aux.dameListaVar();
			}catch (Exception e) {
				//Si entra aqui es porque se agrego un nodo "vacio" en las reglas pila2.add(new Nodo())
				//como es de tipo Nodo no se puede convertir a ListaVar
				aux = null;
			}
		
    	}
    	//imprimirTabla(tabla);
    	return null;
    }

    public void dameInfo(){
        System.out.println("***DefVar***"+"\n"+
                "Tipo: " + tipo.token() +"\n"+
                "id: " + id.token());
        if(listaVar!=null){
            System.out.println("Tiene listaVar");
        }
        else{
            System.out.println("No tiene listaVar");
        }
    }
}
