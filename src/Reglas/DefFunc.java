
package Reglas;

import Complementos.Id;
import Complementos.Tipo;
import Final.AnalizadorSemantico;
import Final.Nodo;
import Reglas.Parametros;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DefFunc extends Nodo{
    private Tipo tipo;
    private Id id;
    private Nodo parametros;
    private Nodo bloqueFunc; //no existe clase BloqueFunc
    //private Nodo siguiente;
    
    public DefFunc(Stack<Nodo> pila){//constructor completo
        pila.pop();
        bloqueFunc = pila.remove(pila.size()-1);
        pila.remove(pila.size()-1);
        pila.remove(pila.size()-1);
        pila.remove(pila.size()-1);
        parametros = pila.remove(pila.size()-1);
        pila.remove(pila.size()-1);
        pila.remove(pila.size()-1);
        id = new Id(pila); //pops de id
        tipo = new Tipo(pila); //pops de tipo
    }
    
    @Override
    public Object validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de DefFunc: " + id.token());
    	String cadenaParametros = "";
    	Parametros aux; //guarda el primer parametro
    	ListaParam listaParametros; //guarda los parametros que le siguien
    	
    	//revisar los parametros de la funcion
    	try {//agrega el primer parametro en caso de que exista
    		aux = (Parametros)parametros;
    		if(!AnalizadorSemantico.existeVar(tabla, aux.dameTipo().token(), aux.dameId().token(), id.token())) {
    			tabla.add(new String[] {aux.dameTipo().token(), aux.dameId().token(), id.token(), ""});
    			cadenaParametros += aux.dameTipo().token(); 
    		}
    		else {
    			//System.err.println("La variable ya existe");
    			errores.add(new String[] {"La variable ya existe", aux.dameId().token()});
    		}
		} catch (Exception e) {
			aux = null;
		}
    	
    	try {//agrega los siguientes parametros en caso de que existan
    		listaParametros = (ListaParam)aux.dameListaParam();
    	}catch (Exception e) {
			listaParametros = null;
		}
    	
    	while(listaParametros != null) {
    		if(!AnalizadorSemantico.existeVar(tabla, listaParametros.dameTipo().token(), listaParametros.dameId().token(), id.token())) {
    			tabla.add(new String[] {listaParametros.dameTipo().token(), listaParametros.dameId().token(), id.token(), ""});
    			cadenaParametros += "." + listaParametros.dameTipo().token();
    		}
    		else {
    			//System.err.println("La variable ya existe");
    			errores.add(new String[] {"La variable ya existe", listaParametros.dameId().token()});
    		}
    		
    		try {
        		listaParametros = (ListaParam)listaParametros.dameListaParam();
    		} catch (Exception e) {
    			listaParametros = null;
    		}
    	}
    	
    	//revisar la funcion(el ambito siempre es "" vacio porque se declaran en el ambtio global)
    	if(!AnalizadorSemantico.existeFunc(tabla, tipo.token(), id.token(), "", cadenaParametros)) {
    		tabla.add(new String[] {tipo.token(), id.token(), "", cadenaParametros});
    	}
    	else {
    		System.err.println("Ya existe la funcion");
    		errores.add(new String[] {"La funcion ya existe", id.token()});
    	}
    	
    	//Revisar el bloque de funcion (es un Nodo de algun tipo) (el utlmo Nodo que revisa es un nodo vacio)
    	System.out.println("Bloque de la funcion: " + id.token());
    	while(bloqueFunc != null) {
    		System.out.println("Revisando: " + bloqueFunc.getClass());
    		//bloqueFunc.dameInfo();
    		bloqueFunc.validaSemantica(tabla, errores, id.token());
    		bloqueFunc = bloqueFunc.dameSiguiente();
    	}
    	
    	return null;
    }
    
    public void dameInfo(){
        System.out.println("***DefFunc***"+"\n"+
                "Tipo: " + tipo.token() +"\n"+
                "Id: " + id.token());
        if(bloqueFunc!=null){
            System.out.println("Bloque de funcion");
            bloqueFunc.dameInfo();
        }
        else{
            System.out.println("No tiene bloque de funcion :(");
        }
    }
}
