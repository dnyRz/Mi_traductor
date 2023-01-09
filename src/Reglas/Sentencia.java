
package Reglas;

import Complementos.Id;
import Final.Nodo;

import java.util.List;
import java.util.Stack;

//Asignacion
public class Sentencia extends Nodo{
    private Nodo expresion;
    private Id id;
    
    public Sentencia(Stack<Nodo> pila){
        pila.pop();//estado
        pila.pop();//;
        pila.pop();//estado
        expresion = pila.pop();
        
        //pops de asignacion
        pila.pop();//estado
        pila.pop();//asignacion
        
        id = new Id(pila);
    }
    
    @Override
    public Object validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de Sentencia (asignacion)");
    	//System.out.println(expresion.getClass());
    	
    	String tipo = id.validaSemantica(tabla, ambito);//revisa variable en ambito local (lado izquierdo)
    	String tipoExpresion;//(lado derecho)
    	
    	try {//revisar si el lado derecho es una funcion
			LlamadaFunc llamada = (LlamadaFunc)expresion;
			tipoExpresion = (String)expresion.validaSemantica(tabla, errores, ambito);
		} catch (Exception e) {//es un id o constante
			tipoExpresion = (String)expresion.validaSemantica(tabla, errores, ambito); //regresa tipo
		}
    	
    	//revisar si existe variable en ambito global(lado izquierdo
    	if(tipo.equals("")) {
    		tipo = id.validaSemantica(tabla, "");//revisa en ambito local
    	}
    	
    	//System.out.println(">>>>>>>>>>>>>>>>>>>> tipo: " + tipo);
    	//System.out.println(">>>>>>>>>>>>>>>>>>>> tipoExpresion: " + tipoExpresion);
    	
    	//Revisar que exista la variable en la tabla (lado izquierdo)
    	if(tipo.equals("")) {
    		//agregar a errores (no existe variable)
    		//System.err.println("La variable no existe");
    		errores.add(new String[] {"("+ambito+") La variable a la que se intenta asignar un valor no existe", id.token()});
    	}
    	else if(tipoExpresion.equals("1")) {//ver clase Operacion
    		//errores.add(new String[] {"("+ambito+") Error en la operacion (tipo de dato o no existe)", id.token()});
    		errores.add(new String[] {"("+ambito+") El tipo de dato no coincide", id.token()});
    	}
    	else if(tipoExpresion.equals("")) {
    		errores.add(new String[] {"("+ambito+") La variable que se intenta asignar no existe"});
    	}
    	else if(!tipo.equals(tipoExpresion) && !tipoExpresion.equals("")){
    		//System.err.println("El tipo de dato no coincide");
    		errores.add(new String[] {"("+ambito+") El tipo de dato no coincide", id.token()});
    	}
    	return null;
    }
    
    public void dameInfo() {
    	System.out.println("***Sentencia asignacion***"+"\n"+
                "Id: " + id.token() +"\n"+
                "Expresion: " + expresion.getClass());
    	expresion.dameInfo();
    }
}
