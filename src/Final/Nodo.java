
package Final;

import java.util.List;

public class Nodo {
    private String lexema;
    private static String ambito;
    private String tipoDato;
    private String clase;
    private Nodo siguiente;
    
    public Nodo(){
        lexema = "";
        ambito = "";
        tipoDato = "";
        clase = "";
        siguiente = null;
    }

    public Object validaSemantica(List<String[]> tabla, List<String[]> errores, String ambito ) {
    	System.out.println("Esta en valida semantica de Nodo");
    	System.out.println("Fin de valida semantica");
    	return null;
    }
    
    public void fijaLexema(String l){
        lexema = l;
    }
    
    public void fijaAmbito(String a){
        ambito = a;
    }
    
    public void fijaTipoDato(String t){
        tipoDato = t;
    }
    
    public void fijaClase(String c){
        clase = c;
    }
    
    public void fijaSiguiente(Nodo n){
        siguiente = n;
    }
    
    public String dameLexema(){
        return lexema;
    }
    
    public String dameAmbito(){
        return ambito;
    }
    
    public String dameTipoDato(){
        return tipoDato;
    }
    
    public String dameClase(){
        return clase;
    }
    
    public Nodo dameSiguiente(){
        return siguiente;
    }
    
    public void dameInfo(){
        System.out.println("***Nodo***"+"\n"+
                "Lexema: " + lexema +"\n"+
                "Ambito: " + ambito +"\n"+
                "Tipo dato: " + tipoDato +"\n"+
                "Clase: " + clase);
        if(siguiente!=null){
            System.out.println("Tiene siguiente");
        }
        else{
            System.out.println("No tiene siguiente");
        }
    }
}
