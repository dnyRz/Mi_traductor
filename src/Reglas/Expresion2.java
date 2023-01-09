
package Reglas;

import java.util.List;
import java.util.Stack;

import Final.Nodo;

//asignar una constante
public class Expresion2 extends Nodo{
    Nodo lexema;
	String strLexema;
    
    public Expresion2(Stack<Nodo> pila){
        pila.pop();//estado
        lexema = pila.pop(); //lexema
        strLexema = lexema.dameLexema();
    }
    
    @Override
    public String validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito) {
    	System.out.println("*****Esta en valida semantica de Expresion2");
    	
    	try {
    		Integer.parseInt(strLexema);
    		return "int";
		} catch (Exception e) {
			try {
				Float.parseFloat(strLexema);
				return "float";
			} catch (Exception e2) {
				//aqui va otro tipo de dato
			}
		}
    	
    	//System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    	return "";//si el tipo de dato no existe no tiene tipo(int, float)
    }
    
    public String dameLexema() {
    	return strLexema;
    }
    
    public void dameInfo() {
    	System.out.println("***Expresion 2 (asignar constante)***"+"\n"+
                "lexema: " + strLexema);
    }
}
