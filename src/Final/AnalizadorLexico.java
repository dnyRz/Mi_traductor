    
package Final;

import java.util.ArrayList;


public class AnalizadorLexico {
    private final ArrayList<String> tokens;
    private final ArrayList<String> lexemas;
    private final ArrayList<Integer> numeros;
    
    private String cadena;
    private int indice;
    private int estado;
    private String token;
    private String lexema;
    //private int numero;
    
    private static final int ESTADO_INICIAL = 0;
    /*
    private static final int ESTADO_UNO = 1; //analizar caracteres (a-z, A-Z, 0-9)
    private static final int ESTADO_DOS = 2; //analizar los operadores (=, !, <=, ...)
    private static final int ESTADO_TRES = 3; //analizar que operacion es
    private static final int ESTADO_CUATRO = 4; //analizar los caracteres especiales
    private static final int ESTADO_CINCO = 5; //analizar constantes (1, 2, 4.5, 9.9999, ...)
    */
    private static final int ESTADO_FINAL = 9;
    
    public AnalizadorLexico(){
        //this.cadena = cadena += "$";
        cadena = "";
        tokens = new ArrayList<>();
        lexemas = new ArrayList<>();
        numeros = new ArrayList<>();
    }
    
    public void analizarCadena(){
        int tamCadena;
        String caracter;
        
        estado = ESTADO_INICIAL;
        indice = 0;
        tamCadena = cadena.length();
        caracter = "$";
        limpiarDatos();
        
        while((indice<=tamCadena) && estado==ESTADO_INICIAL){
            token = "error";
            lexema = "";
            //numero = -1;
            while((indice<tamCadena) && estado!=ESTADO_FINAL){
                if(estado == ESTADO_INICIAL){
                    caracter = dameCaracter(indice);
                    if(esEspacio(caracter)){//comparar con espacio, tabulador, salto de linea
                        estado = ESTADO_INICIAL;
                    }else if(esLetra(caracter)){//cadena debe iniciar con letra o _
                        estadoUno(caracter);
                    }else if(esOperador(caracter)){//operadores (=, ==, <, &&, ...)
                        estadoDos(caracter);
                    }else if(esOperacion(caracter)){//operaciones (+, -, /, *)
                        estadoTres(caracter);
                    }else if(esCaracterEspecial(caracter)){//caracteres especiales (;, (, {, ...)
                        estadoCuatro(caracter);
                    }else if(esNumero(caracter) || caracter.equals(".")){//numeros (1, 999, 1.5, 0.333, .9)
                        estadoCinco(caracter);
                    }else if(caracter.equals("$")){//Final de la entrada (cadena, archivo)
                        estado = ESTADO_FINAL;
                        lexema += caracter;
                        token = "$";
                    }else{//ERROR, el caracter es desconocido
                        estado = ESTADO_FINAL;
                        lexema += caracter;
                        token = "error";
                        //numero = -1;
                    }
                    indice++;
                }
            }
            
            if(!caracter.equals("$")){
                //tokens.add(token);
                //lexemas.add(lexema);
                estado = ESTADO_INICIAL;
            }
            //incluye el simbolo $
            tokens.add(token);
            lexemas.add(lexema);
        }
        identificarPalabrasReservadas();
        identificarTipoDato();
        for(int i=0; i<tokens.size(); i++){
            numeros.add(asignarNumero(tokens.get(i)));
        }
        
        //ver resultados en consola
        /*for(int i=0,total=tokens.size(); i<total; i++){
            System.out.println("token = " + tokens.get(i));
            System.out.println("lexema = " + lexemas.get(i));
            System.out.println("*************************+++++++++");
        }*/
    }
    
    public void fijaCadena(String cadena){
        this.cadena = cadena+"$";
    }
    
    private String dameCaracter(int indice){
        return cadena.substring(indice, indice+1);
        //return cadena.charAt(indice)
    }
    
    private void limpiarDatos(){
        tokens.clear();
        lexemas.clear();
        numeros.clear();
    }
    
    //cadena debe iniciar con letra o _ (recorre caracteres)
    private void estadoUno(String caracter){
        do{
            lexema += caracter;
            caracter = dameCaracter(++indice);
        }while(esLetra(caracter) || esNumero(caracter));
        indice--;
        token = "id";
        estado = ESTADO_FINAL;
    }
    
    //operadores (=, ==, <, &&, ...)
    private void estadoDos(String caracter){
        String operadoresLogicos = "|| &&";
        String operadoresRelacionales = "== <= >= !=";
        String sigCaracter = dameCaracter(indice+1);
        String lexemaAux = caracter;
        lexema += caracter;
        
        //es un solo operador puede ser = < > 
        //System.out.println("analizando: " + caracter);
        if(caracter.equals("=")){
            token = caracter;
        }else if(caracter.equals("<") || caracter.equals(">")){
            token = "opRelacional";
        }else{//entra con &, |, !
            token = "error";
        }
        
        //son dos operadores
        lexemaAux += sigCaracter;
        if(lexemaAux.equals("==") || lexemaAux.equals("<=") || lexemaAux.equals(">=")|| lexemaAux.equals(">)") || 
                lexemaAux.equals("!=")){
            indice++;
            lexema = lexemaAux;
            token = "opRelacional";
        }
        else if(lexemaAux.equals("||") || lexemaAux.equals("&&")){
            indice++;
            lexema = lexemaAux;
            token = "opLogico";
        }
        
        
        /*System.out.println("siguiente: " + sigCaracter);
        if(operadoresRelacionales.contains(lexemaAux)){
            indice++;
            lexema = lexemaAux;
            token = "opRelacional";
            System.out.println("cambio**********************************");
        }
        else if(operadoresLogicos.contains(lexemaAux)){
            indice++;
            lexema = lexemaAux;
            token = "opLogico";
        }*/
        
        estado = ESTADO_FINAL;
    }
    
    //operaciones (+, -, /, *)
    private void estadoTres(String caracter){
        String suma = "+-";//, mult = "*/";
        lexema += caracter;
        
        if(suma.contains(caracter)){
            token = "opSuma";
        }else{
            token = "opMultiplicacion";
        }
        
        estado = ESTADO_FINAL;
    }
    
    //caracteres especiales (;, (, {, ...)
    private void estadoCuatro(String caracter){
        lexema += caracter;
        token = caracter;
        estado = ESTADO_FINAL;//CAMBIAR POR ESTADO_CUATRO
    }
    
    //numeros (1, 999, 1.5, 0.333, .9)
    private void estadoCinco(String caracter){
        if(caracter.equals(".")){//Puede ser un numero como .9 o dos como 3.44.5 = 1.44 y 0.5?
            if(esNumero(dameCaracter(indice+1))){
                token = "constante";
                lexema += "0";//para agregar un cero antes del punto 
                do{
                    lexema += caracter;
                    caracter = dameCaracter(++indice);
                }while(esNumero(caracter));
                indice--;
            }
            else{
                lexema += caracter;
                token = "error";
            }
        }else{
            do{
                lexema += caracter;
                caracter = dameCaracter(++indice);
            }while(esNumero(caracter) || (caracter.equals(".")&&!lexema.contains(".")));
            if(lexema.lastIndexOf(".")>=(lexema.length()-1)){//si el ultimo caracter es . es error
                token = "error";
            }
            else{
                token = "constante";
            }
            indice--;
        }
        estado = ESTADO_FINAL;
    }
    
    private int asignarNumero(String t){
        int numero;
        String caracteresSimples = ";,(){}="; //numeros del 2 al 8
        
        //System.out.println("llego: " + t);
        if(t.equals("tipo dato")){
            numero = 0;
        }else if(t.equals("id")){
            numero = 1;
        }else if(caracteresSimples.contains(t)){
            numero = 2+caracteresSimples.indexOf(t);
        }
        else if(t.equals("if")){
            numero = 9;
        }else if(t.equals("while")){
            numero = 10;
        }else if(t.equals("return")){
            numero = 11;
        }else if(t.equals("else")){
            numero = 12;
        }else if(t.equals("constante")){
            numero = 13;
        }else if(t.equals("opSuma")){
            numero = 14;
        }else if(t.equals("opLogico")){
            numero = 15;
        }else if(t.equals("opMultiplicacion")){
            numero = 16;
        }else if(t.equals("opRelacional")){
            numero = 17;
        }else if(t.equals("$")){
            numero = 18;
        }else{//En caso de error
            numero = -1;
        }
        
        //System.out.println("asigno: " + numero);
        
        return numero;
    }
    
    private void identificarPalabrasReservadas(){
        int i, totalElementos;
        String lexemaActual;
        
        totalElementos = lexemas.size();
        for(i=0; i<totalElementos; i++){
            lexemaActual = lexemas.get(i);
            if(lexemaActual.equals("if")){//if
                tokens.set(i, "if");
            }else if(lexemaActual.equals("else")){//else
                tokens.set(i, "else");
            }else if(lexemaActual.equals("while")){//while
                tokens.set(i, "while");
            }else if(lexemaActual.equals("return")){//return
                tokens.set(i, "return");
            }//else if()...
        }
    }
    
    private void identificarTipoDato(){
        int i, totalElementos;
        String lexemaActual;
        
        totalElementos = lexemas.size();
        for(i=0; i<totalElementos; i++){
            lexemaActual = lexemas.get(i);
            if(lexemaActual.equals("int")){//int
                tokens.set(i, "tipo dato");
            }else if(lexemaActual.equals("float")){//float
                tokens.set(i, "tipo dato");
            }else if(lexemaActual.equals("char")){//char
                tokens.set(i, "tipo dato");
            }else if(lexemaActual.equals("void")){//string
                tokens.set(i, "tipo dato");
            }//else if()...
        }
    }
    
    private boolean esEspacio(String caracter){
        return caracter.equals(" ") || caracter.equals(" ") || caracter.equals("\n");
    }
    
    public boolean esLetra(String caracter){
        char c = caracter.charAt(0);
        return ((c>='a' && c<='z') || (c>='A' && c<='Z') || c=='_');
    }
    
    private boolean esNumero(String caracter){
        try{
            Integer.parseInt(caracter);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    private boolean esOperacion(String caracter){
        String operaciones = "+-*/";
        return operaciones.contains(caracter);
    }
    
    private boolean esCaracterEspecial(String caracter){
        String especiales = ";,(){}";
        return especiales.contains(caracter);
    }
    
    private boolean esOperador(String caracter){
        String logicos = "|&";
        String relacionales = "=<>!";
        return logicos.contains(caracter) || relacionales.contains(caracter);
    }
    
    public ArrayList<String> dameTokens(){
        return tokens;
    }
    
    public ArrayList<String> dameLexemas(){
        return lexemas;
    }
    
    public ArrayList<Integer> dameNumeros(){
        return numeros;
    }
    
    public String[][] dameMatriz(){
        String matriz[][];
        int i, totalFilas, totalColumnas;
        
        totalFilas = tokens.size();
        totalColumnas = 3;
        matriz = new String[totalFilas][totalColumnas];
        
        for(i=0; i<totalFilas; i++){
            matriz[i][0] = tokens.get(i);
            matriz[i][1] = lexemas.get(i);
            matriz[i][2] = numeros.get(i).toString();
        }
        
        return matriz;
    }
}

