/*
Cambiar las pilas de ArrayList a Stack
Stack s = new Stack();

No se necesitan tantas actualizaciones de estado
*/

package Final;

import Complementos.Estado;
import Reglas.DefFunc;
import Reglas.DefVar;
import Reglas.Expresion;
import Reglas.Expresion2;
import Reglas.ListaParam;
import Reglas.ListaVar;
import Reglas.LlamadaFunc;
import Reglas.Operacion;
import Reglas.Parametros;
import Reglas.Sentencia;
import Reglas.Sentencia2;
import Reglas.Sentencia3;
import Reglas.Sentencia4;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class AnalizadorSintactico {
    private int[][] tabla;
    private int[][] reglas;
    private ArrayList<Integer> pila;
    private Stack<Nodo> pila2;
    
    private ArrayList<Integer> numeros; //los numeros de los tokens (de la cadena)
    private ArrayList<String> tokens; //los tokens que consiguio el analizador lexico
    private ArrayList<String> lexemas; //los lexemas que consiguio el analizador lexico
    
    //para la actividad 4 (Anlizador sintactico)
    private Nodo raiz;
    private List<String[]> tablaSemantica;
    private List<String[]> errores;
    
    public AnalizadorSintactico(ArrayList<Integer> numeros, ArrayList<String> tokens, ArrayList<String> lexemas){
        crearTabla();
        crearReglas();
        pila = new ArrayList<>();
        pila2 = new Stack<>();
        this.numeros = numeros;
        this.tokens = tokens;
        this.lexemas = lexemas;
        
        tablaSemantica = new ArrayList<>();
        errores = new ArrayList<>();
    }
    
    //pila de numeros enteros
    public boolean esCadenaValida(){
        /*
            resultado > 0 = desplazamiento
            resultado < 0 = regla desplazada (-1 = regla 0, -2 = regla 1)
        */
        
        //el indice es el elemento de la cadena (numeros)
        boolean esValido = false, finalizo = false;
        int i, tope, resultado, num, indice;
        int j, totalElementos, ladoDerecho, ladoIzquierdo;
        
        //imprimir numeros
        /*for(int k=0; k<numeros.size(); k++){
            System.out.println("num: " + numeros.get(k));
        }*/
        
        tope = indice = 0;
        pila.add(tope);
        
        //num = numeros.get(indice);
        while(!finalizo){//num!=18
            num = numeros.get(indice);//num es token
            resultado = tabla[tope][num];//tope es fila, num columna
            
            if(resultado >0){//desplazamiento (solo aqui hace un recorrido indice++)
                pila.add(num); //agrega el token
                pila.add(resultado); //agrega el desplazamiento
                tope = resultado; //actualizar tope (fila)
                
                indice++;
            }
            else if(resultado < 0){//regla 
                //saber si el codigo esta en la regla 0 (es correcto)
                if(resultado == -1){
                    esValido = true;
                    break;
                }
                
                resultado += 1; //para corregir el desplazamiento
                resultado *= -1; //convertir a positivo
                
                //obtener lado derecho e izquierdo de la regla
                ladoIzquierdo = reglas[resultado][0];
                ladoDerecho = reglas[resultado][1];
                
                //sacar el doble de elmentos de los que produce la fila(lado derecho)
                totalElementos = ladoDerecho*2;
                for(j=0 ; j<totalElementos; j++){
                    pila.remove(pila.size()-1);
                }
                tope = pila.get(pila.size()-1); //actualizar tope (esto es necesario???SI)
                resultado = tabla[tope][ladoIzquierdo];//hacer una nueva busqueda en la tabla
                
                //agregar lado izquierdo y resultado
                pila.add(ladoIzquierdo);
                pila.add(resultado);
                tope = pila.get(pila.size()-1); //actualizar tope
            }
            else if(resultado == -1){//codigo valido (esto es necesario???)
                finalizo = true;
                break;
            }
            else{//casilla vacia (0)
                finalizo = true;
                break;
            }
            
            /*System.out.println("************Pila actual");
            for(int k=0; k<pila.size(); k++){
                System.out.println(pila.get(k));
            }
            System.out.println("************");*/
        }
        
        //System.out.println(">>>>> Finalizo con: " + esValido);
        
        return esValido;
    }
    
    //pila de Nodos
    public boolean esCadenaValidaV2(){
        boolean salir = false; //para debug de las reglas (ELIMINAR)
        
        boolean esValido = false, finalizo = false;
        int i, resultado, num, indice;
        int j, totalElementos, ladoDerecho, ladoIzquierdo;
        
        Nodo nodo;
        Estado estado = new Estado(); //tope, desplazamiento, transicion
        
        indice = 0;
        pila2.add(estado);
        
        while(!finalizo){
            num = numeros.get(indice);//num es el numero del token, para buscar en la tabla
            resultado = tabla[estado.dameValor()][num];//estado es fila, num es columna
            //System.out.println("----------------------> Resultado: " + resultado);
            if(resultado > 0){//desplazamiento (solo aqui hace un recorrido indice++)
                nodo = new Nodo();
                estado = new Estado();
                
                //agregar dato al nodo
                nodo.fijaLexema(lexemas.get(indice));
                
                //actualizar el tope
                estado.fijaValor(resultado);
                
                //agregar a la pila
                pila2.add(nodo); //agrega el token(Nodo)
                pila2.add(estado); //agrega el desplazamiento
                
                indice++;
                //break;///////////////////////////////////////////////ELIMINAR***********************************+
            }
            else if(resultado < 0){//regla (cada regla hace sus pops)
                //saber si el codigo esta en la regla 0 (es correcto)
                //cambiar esto al caso 0
                //pero, da problemas porque intenta actualizar el estado despues del switch
                if(resultado == -1){
                	Nodo siguiente;
                	
                	//System.out.println("Salio con regla 0");
                    raiz = pila2.get(1);
                    //System.out.println("La raiz es:");
                    
                    //raiz.dameInfo();
                    raiz.validaSemantica(tablaSemantica, errores, raiz.dameAmbito());
                    siguiente = raiz.dameSiguiente();
                    
                    while(siguiente!=null) {
                        //siguiente.dameInfo();
                        siguiente.validaSemantica(tablaSemantica, errores, siguiente.dameAmbito());
                        siguiente = siguiente.dameSiguiente();
                    }
                    
                    AnalizadorSemantico.imprimirTabla(tablaSemantica);
                    AnalizadorSemantico.imprimirErrores(errores);
                    
                    esValido = true;
                    finalizo = true;
                    break;
                }
                
                resultado += 1; //para corregir el desplazamiento
                resultado *= -1; //convertir a positivo
                
                //obtener lado derecho e izquierdo de la regla
                ladoIzquierdo = reglas[resultado][0];
                ladoDerecho = reglas[resultado][1];
                
                switch(resultado){//revisar las reglas, hacer los pops y meter el lado izquierdo
                    case 0:{//Programa -> Definiciones
                        
                    }break;
                    case 1:{//Definiciones -> ''
                        pila2.add(new Nodo());
                    }break;
                    case 2:{//Definiciones -> Definicion Definiciones
                        casoReglaGeneraDos();
                    }break;
                    case 3:{//Definicion -> DefVar
                        casoReglaGeneraUno();
                    }break;
                    case 4:{//Definicion -> DefFunc
                        casoReglaGeneraUno();
                    }break;
                    case 5:{//DefVar -> tipo id ListaVar ;
                        pila2.add(new DefVar(pila2));
                    }break;
                    case 6:{//ListaVar -> ''
                        pila2.add(new Nodo());
                    }break;
                    case 7:{//ListaVar -> , id ListaVar
                        pila2.add(new ListaVar(pila2));
                    }break;
                    case 8:{//DefFunc -> tipo id ( Parametros ) BloqFunc
                        pila2.add(new DefFunc(pila2));
                    }break;
                    case 9:{//Parametros -> ''
                        pila2.add(new Nodo());
                    }break;
                    case 10:{//Parametros -> tipo id ListaParam
                        pila2.add(new Parametros(pila2));
                    }break;
                    case 11:{//ListaParam -> ''
                        pila2.add(new Nodo());
                    }break;
                    case 12:{//ListaParam -> , tipo id ListaParam
                        pila2.add(new ListaParam(pila2));
                    }break;
                    case 13:{//BloqFunc -> { DefLocales }
                        //Produce 1 elemento, pero se sacan 6 elementos por los {}
                        
                        pila2.pop();//estado
                        pila2.pop();//}
                        pila2.pop();//estado
                        nodo = pila2.pop();//defLocales
                        pila2.pop();//estado
                        pila2.pop();//{
                        
                        pila2.add(nodo);
                    }break;
                    case 14:{//DefLocales -> ''
                        pila2.add(new Nodo());
                    }break;
                    case 15:{//DefLocales -> DefLocal DefLocales
                        casoReglaGeneraDos();
                    }break;
                    case 16:{//DefLocal -> DefVar
                        casoReglaGeneraUno();
                    }break;
                    case 17:{//DefLocal -> Sentencia
                        casoReglaGeneraUno();
                    }break;
                    case 18:{//Sentencias -> ''
                        pila2.add(new Nodo());
                    }break;
                    case 19:{//Sentencias ->Sentencia Sentencias
                        casoReglaGeneraDos();
                    }break;
                    case 20:{//Sentencia -> id = Expresion ;
                        pila2.add(new Sentencia(pila2));
                    }break;
                    case 21:{//Sentencia -> if ( Expresion ) SentenciaBloque Otro
                        pila2.add(new Sentencia2(pila2));
                    }break;
                    case 22:{//Sentencia ->while ( Expresion ) Bloque
                        pila2.add(new Sentencia3(pila2));
                    }break;
                    case 23:{//Sentencia -> return Expresion ;
                        pila2.add(new Sentencia4(pila2));
                    }break;
                    case 24:{//Sentencia -> LlamadaFunc ;
                        //Produce 1 elemento, pero se sacan 4 por el ;
                        pila2.pop();
                        pila2.pop();
                        pila2.pop();
                        nodo = pila2.pop();
                        
                        pila2.add(nodo);
                    }break;
                    case 25:{//Otro -> ''
                        pila2.add(new Nodo());
                    }break;
                    case 26:{//Otro -> else SentenciaBloque
                        //Produce 1 elemento, pero se sacan 4 por el else
                        pila2.pop();
                        nodo = pila2.pop();
                        pila2.pop();
                        pila2.pop();
                        
                        pila2.add(nodo);
                    }break;
                    case 27:{//Bloque -> { Sentencias }
                        //Produce 1 elementos, pero se sacan 6 por las {}
                        pila2.pop();
                        pila2.pop();
                        pila2.pop();
                        nodo = pila2.pop();
                        pila2.pop();
                        pila2.pop();
                        
                        pila2.add(nodo);
                    }break;
                    case 28:{//Argumentos -> ''
                        pila2.add(new Nodo());
                    }break;
                    case 29:{//Argumentos -> Expresion ListaArgumentos
                        casoReglaGeneraDos();
                    }break;
                    case 30:{//ListaArgumentos -> ''
                        pila2.add(new Nodo());
                    }break;
                    case 31:{//ListaArgumentos ->, Expresion ListaArgumentos
                        //Produce 2 elementos, pero se sacan 6 por la ,
                        Nodo aux;
                        
                        pila2.pop();
                        aux = pila2.pop();
                        pila2.pop();
                        nodo = pila2.pop();
                        pila2.pop();
                        pila2.pop();
                        
                        nodo.fijaSiguiente(aux);
                        
                        pila2.add(nodo);
                    }break;
                    case 32:{//Expresion -> LlamadaFunc
                        casoReglaGeneraUno();
                    }break;
                    case 33:{//Expresion -> id
                        pila2.add(new Expresion(pila2));
                    }break;
                    case 34:{//Expresion -> constante
                        pila2.add(new Expresion2(pila2));
                    }break;
                    case 35:{//LlamadaFunc -> id ( Argumentos )
                        pila2.add(new LlamadaFunc(pila2));
                    }break;
                    case 36:{//SentenciaBloque -> Sentencia
                        casoReglaGeneraUno();
                    }break;
                    case 37:{//SentenciaBloque -> Bloque
                        casoReglaGeneraUno();
                    }break;
                    case 38:{//Expresion -> ( Expresion )
                        //Produce 1 elemento, pero se sacan 6 por los ()
                        pila2.pop();
                        pila2.pop();
                        pila2.pop();
                        nodo = pila2.pop();
                        pila2.pop();
                        pila2.pop();
                        
                        pila2.add(nodo);
                    }break;
                    case 39:{//Expresion -> Expresion opSuma Expresion
                        pila2.add(new Operacion(pila2));
                    }break;
                    case 40:{
                        pila2.add(new Operacion(pila2));
                    }break;
                    case 41:{
                        pila2.add(new Operacion(pila2));
                    }break;
                    case 42:{
                        pila2.add(new Operacion(pila2));
                    }break;
                    default: System.out.println("Regla desconocida :(");
                }
               
                //actualizar el estado (por si saco elementos de la pila)
                estado = new Estado();
                estado = (Estado)pila2.get(pila2.size()-2);
                
                //hacer la transicion
                resultado = tabla[estado.dameValor()][ladoIzquierdo];
                
                //acttualizar estado despues de insertar la transicion
                estado = new Estado();
                estado.fijaValor(resultado);
                pila2.add(estado);
                
                //System.out.println("Tope quedo con: " + estado.dameValor());
                //System.out.println("Regla: " + ladoIzquierdo);
                if(salir){
                    //indice = 90;
                    break;
                }
            }
            else{//casilla vacia (0)
                finalizo = true;
                break;
            }
        }
        
        //System.out.println(">>>>> Finalizo con: " + esValido);
        /*System.out.println("************Pila actual total: " + pila2.size());
        Nodo n = new Nodo();
        for(int k=0; k<pila2.size(); k++){
            n = pila2.get(k);
            n.dameInfo();
            //System.out.println(n.dameInfo());
        }*/
        
        return esValido;
    }
    
    private void casoReglaGeneraUno(){
        pila2.pop();
        Nodo n = pila2.pop();
        pila2.add(n);
    }
    
    private void casoReglaGeneraDos(){
        Nodo nodo; //= new Nodo();
        
        pila2.pop(); //poop del estado
        Nodo aux = pila2.pop();
        pila2.pop(); //pop del estado
        nodo = pila2.pop();

        nodo.fijaSiguiente(aux);

        pila2.add(nodo);
    }
    
    private void crearTabla(){
        String directorio, linea, c, aux;
        ArrayList<String> lineas;
        int i, j, totalFilas, totalColumnas;
        int[] filasEstadosTabla; //filas (no es necesario)
        
        directorio = "/home/dany/Escritorio/CUCEI/semestre/sem traductores 2/semana 4/";
        lineas = DAO.cargarArchivo(directorio+"GR2sirTable.txt");
        
        //obtener filas y columnas para crear tabla
        linea = lineas.get(0);
        totalColumnas = 0;
        totalFilas = lineas.size();
        for(i=0; i<linea.length(); i++){
            c = linea.substring(i,i+1);
            if(esNumero(c)){
                totalColumnas++;
            }
        }
        totalColumnas--;//quitar la primera columna(estados)
        tabla = new int[totalFilas][totalColumnas];
        
        //llenar tabla
        char tab = 9;//9 es el tabulador
        String arregloCaracteres[];//guarda los numeros de la tabla de cada fila para despues convertirlos a enteros
        filasEstadosTabla = new int[totalFilas];
        for(i=0; i<totalFilas; i++){
            linea = lineas.get(i);
            linea = linea.replace(String.valueOf(tab), ",");
            arregloCaracteres = linea.split(",");
            filasEstadosTabla[i] = Integer.parseInt(arregloCaracteres[0]);
            for(j=0; j<totalColumnas; j++){
                tabla[i][j] = Integer.parseInt(arregloCaracteres[j+1]);
            }
            //System.out.println(linea);
        }
        
        //imprimir tabla
        
        /*for(i=0; i<totalFilas; i++){
            for(j=0; j<totalColumnas; j++){
                System.out.print(tabla[i][j] + ", ");
            }
            System.out.println();
        }*/
        /*
        for(i=0; i<totalFilas; i++){
            System.out.println(estadosTabla[i]);
        }*/
    }
    
    private void crearReglas(){
        String directorio, linea, c, aux;
        ArrayList<String> lineas;
        int i, j, totalFilas, totalColumnas;
        
        directorio = "/home/dany/Escritorio/CUCEI/semestre/sem traductores 2/semana 4/";
        lineas = DAO.cargarArchivo(directorio+"GR2sirRulesId.txt");
        
        //obtener filas y columnas para crear tabla de reglas
        linea = lineas.get(0);
        totalColumnas = 0;
        totalFilas = lineas.size();
        for(i=0; i<linea.length(); i++){
            c = linea.substring(i,i+1);
            if(esNumero(c)){
                totalColumnas++;
            }
        }
        totalColumnas--;
        reglas = new int[totalFilas][totalColumnas];
        
        //llenar tabla reglas
        char tab = 9;//9 es el tabulador
        String arregloCaracteres[];
        for(i=0; i<totalFilas; i++){
            linea = lineas.get(i);
            linea = linea.replace(String.valueOf(tab), ",");
            arregloCaracteres = linea.split(",");
            for(j=0; j<totalColumnas; j++){
                reglas[i][j] = Integer.parseInt(arregloCaracteres[j]);
            }
           //System.out.println(linea);
        }
        
        //imprimir tabla
        /*
        for(i=0; i<totalFilas; i++){
            for(j=0; j<totalColumnas; j++){
                System.out.print(reglas[i][j] + ", ");
            }
            System.out.println();
        }*/
    }
    
    private boolean esNumero(String caracter){
        try{
            Integer.parseInt(caracter);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    public List<String[]> dameMatriz(){
    	return tablaSemantica;
    }
    
    public List<String[]> dameErrores(){
    	return errores;
    }
}






















/*
package Act_3;

import Act_3.Complementos.Estado;
import Act_3.Reglas.DefFunc;
import Act_3.Reglas.DefVar;
import java.util.ArrayList;


public class AnalizadorSintactico {
    private int[][] tabla;
    private int[][] reglas;
    private int[] filasEstadosTabla; //filas
    //private int[] columnasNumeros;//columnas (tokens)
    private ArrayList<Integer> pila;
    private ArrayList<Nodo> pila2; //solo guarda objetod tipo Nodo (no sus derivados)
    private ArrayList<Integer> numeros; //los numeros de los tokens (de la cadena)
    private ArrayList<String> tokens; //los tokens que consigui el analizador lexico
    
    public AnalizadorSintactico(ArrayList<Integer> numeros, ArrayList<String> tokens){
        crearTabla();
        crearReglas();
        //crearNumeros();
        pila = new ArrayList<>();
        pila2 = new ArrayList<>();
        this.numeros = numeros;
        this.tokens = tokens;
    }
    
    //pila de numeros enteros
    public boolean esCadenaValida(){
        /*
            resultado > 0 = desplazamiento
            resultado < 0 = regla desplazada (-1 = regla 0, -2 = regla 1)
        *
        
        //el indice es el elemento de la cadena (numeros)
        boolean esValido = false, finalizo = false;
        int i, tope, resultado, num, indice;
        int j, totalElementos, ladoDerecho, ladoIzquierdo;
        
        //imprimir numeros
        /*for(int k=0; k<numeros.size(); k++){
            System.out.println("num: " + numeros.get(k));
        }*
        
        tope = indice = 0;
        pila.add(tope);
        
        //num = numeros.get(indice);
        while(!finalizo){//num!=18
            num = numeros.get(indice);//num es token
            resultado = tabla[tope][num];//tope es fila, num columna
            
            if(resultado >0){//desplazamiento (solo aqui hace un recorrido indice++)
                pila.add(num); //agrega el token
                pila.add(resultado); //agrega el desplazamiento
                tope = resultado; //actualizar tope (fila)
                
                indice++;
            }
            else if(resultado < 0){//regla 
                //saber si el codigo esta en la regla 0 (es correcto)
                if(resultado == -1){
                    esValido = true;
                    break;
                }
                
                resultado += 1; //para corregir el desplazamiento
                resultado *= -1; //convertir a positivo
                
                //obtener lado derecho e izquierdo de la regla
                ladoIzquierdo = reglas[resultado][0];
                ladoDerecho = reglas[resultado][1];
                
                //sacar el doble de elmentos de los que produce la fila(lado derecho)
                totalElementos = ladoDerecho*2;
                for(j=0 ; j<totalElementos; j++){
                    pila.remove(pila.size()-1);
                }
                tope = pila.get(pila.size()-1); //actualizar tope (esto es necesario???SI)
                resultado = tabla[tope][ladoIzquierdo];//hacer una nueva busqueda en la tabla
                
                //agregar lado izquierdo y resultado
                pila.add(ladoIzquierdo);
                pila.add(resultado);
                tope = pila.get(pila.size()-1); //actualizar tope
            }
            else if(resultado == -1){//codigo valido (esto es necesario???)
                finalizo = true;
                break;
            }
            else{//casilla vacia (0)
                finalizo = true;
                break;
            }
            
            /*System.out.println("************Pila actual");
            for(int k=0; k<pila.size(); k++){
                System.out.println(pila.get(k));
            }
            System.out.println("************");*
        }
        
        //System.out.println(">>>>> Finalizo con: " + esValido);
        
        return esValido;
    }
    
    //pila de Nodos
    public boolean esCadenaValidaV2(){
        //el indice es el elemento de la cadena (numeros)
        boolean salir = false;
        
        boolean esValido = false, finalizo = false;
        int i, resultado, num, indice;
        int j, totalElementos, ladoDerecho, ladoIzquierdo;
        
        Nodo nodo = new Nodo();
        Estado estado = new Estado(); //tope, desplazamiento, transicion
        
        indice = 0;
        pila2.add(estado);
        
        while(indice<10){//num!=18
            num = numeros.get(indice);//num es el numero del token
            resultado = tabla[estado.dameValor()][num];//tope es fila, num columna
            System.out.println("----------------------> Resultado: " + resultado);
            if(resultado > 0){//desplazamiento (solo aqui hace un recorrido indice++)
                nodo = new Nodo();
                estado = new Estado();
                
                nodo.fijaLexema(tokens.get(indice));
                estado.fijaValor(resultado);//este estado es el tope
                
                pila2.add(nodo); //agrega el token(Nodo)
                pila2.add(estado); //agrega el desplazamiento
                
                indice++;
            }
            else if(resultado < 0){//regla (cada regla hace sus pops)
                //saber si el codigo esta en la regla 0 (es correcto)
                if(resultado == -1){
                    System.out.println("Salio con regla 0");
                    esValido = true;
                    break;
                }
                
                resultado += 1; //para corregir el desplazamiento
                resultado *= -1; //convertir a positivo
                
                //obtener lado derecho e izquierdo de la regla
                ladoIzquierdo = reglas[resultado][0];
                ladoDerecho = reglas[resultado][1];
                
                switch(resultado){//revisar las reglas, hacer los pops y meter el lado izquierdo
                    case 1:{
                        //la regla no produce nada (meter nodo vacio como lado izq)
                        pila2.add(new Nodo());
                        //salir = true;
                    }break;
                    case 2:{
                        //la regla produce 2 elementos
                        nodo = new Nodo();
                        
                        pila2.remove(pila2.size()-1); //poop del estado
                        Nodo aux = pila2.remove(pila2.size()-1);
                        pila2.remove(pila2.size()-1); //pop del estado
                        nodo = pila2.remove(pila2.size()-1);
                        
                        nodo.fijaSiguiente(aux);
                        
                        pila2.add(nodo);
                        
                        estado = new Estado();
                        estado = (Estado)pila2.get(pila2.size()-2);
                        
                        //salir = true;
                    }break;
                    case 4:{
                        //la regla produce 1 elemento
                        pila2.remove(pila2.size()-1);
                        Nodo n = pila2.remove(pila2.size()-1);
                        pila2.add(n);
                        
                        estado = new Estado();
                        estado = (Estado)pila2.get(pila2.size()-2);
                        
                        //salir = true;
                        
                    }break;
                    case 5:{
                        pila2.add(new DefVar(pila2));
                        estado = new Estado();
                        estado = (Estado)pila2.get(pila2.size()-2);
                    }break;
                    case 6:{
                        //la regla no produce nada (meter nodo vacio como lado izq)
                        pila2.add(new Nodo());
                    }break;
                    case 8:{
                        pila2.add(new DefFunc(pila2));
                        
                        estado = new Estado();
                        estado = (Estado)pila2.get(pila2.size()-2);
                        
                        //salir = true;
                    }break;
                    case 9:{
                        //la regla no produce nada (meter nodo vacio como lado izq)
                        pila2.add(new Nodo());
                    }break;
                    case 13:{
                        //la regla produce 1 elemento
                        nodo = new Nodo();
                        
                        pila2.remove(pila2.size()-1);
                        pila2.remove(pila2.size()-1);
                        pila2.remove(pila2.size()-1);
                        nodo = pila2.remove(pila2.size()-1);
                        pila2.remove(pila2.size()-1);
                        pila2.remove(pila2.size()-1);
                        
                        pila2.add(nodo);
                        
                        estado = new Estado();
                        estado = (Estado)pila2.get(pila2.size()-2);
                        
                        
                        //salir = true;
                    }break;
                    case 14:{
                        //la regla no produce nada (meter nodo vacio como lado izq)
                        pila2.add(new Nodo());
                        //salir = true;
                    }break;
                    case 15:{
                        //la regla produce 2 elementos
                        nodo = new Nodo();
                        
                        pila2.remove(pila2.size()-1); //poop del estado
                        Nodo aux = pila2.remove(pila2.size()-1);
                        pila2.remove(pila2.size()-1); //pop del estado
                        nodo = pila2.remove(pila2.size()-1);
                        
                        nodo.fijaSiguiente(aux);
                        
                        pila2.add(nodo);
                        
                        estado = new Estado();
                        estado = (Estado)pila2.get(pila2.size()-2);
                        
                        //salir = true;
                    }break;
                    case 16:{
                        //la regla produce 1 elemento, solo necesitas sacar 2
                        //pero el ultimo que sale se vuelve a meter
                        pila2.remove(pila2.size()-1);
                        Nodo n = pila2.remove(pila2.size()-1);
                        pila2.add(n);
                        
                        estado = new Estado();
                        estado = (Estado)pila2.get(pila2.size()-2);
                        
                        //System.out.println("Al salir es: " + estado.dameValor());
                        //System.out.println("Y el lado izq: " + ladoIzquierdo);
                        
                        //salir = true;
                    }break;
                    default: System.out.println("Regla desconocida :(");
                }
                
                //if(indice<80){

                //hacer el desplazamiento
                resultado = tabla[estado.dameValor()][ladoIzquierdo];
                estado = new Estado();
                estado.fijaValor(resultado);
                pila2.add(estado);
                
                //actualizar tope
                estado = new Estado();
                estado.fijaValor(resultado);
                //}
                
                System.out.println("Tope quedo con: " + estado.dameValor());
                System.out.println("Regla: " + ladoIzquierdo);
                if(salir){
                    //indice = 90;
                    break;
                }
            }
        }
        
        //System.out.println(">>>>> Finalizo con: " + esValido);
        System.out.println("************Pila actual total: " + pila2.size());
        Nodo n = new Nodo();
        for(int k=0; k<pila2.size(); k++){
            n = pila2.get(k);
            n.dameInfo();
            //System.out.println(n.dameInfo());
        }
        
        return esValido;
    }
    
    private void casoReglaGeneraUno(){
        
    }
    
    private Estado casoReglaGeneraDos(){
        Nodo nodo = new Nodo();
        Estado estado = new Estado();
        
        pila2.remove(pila2.size()-1); //poop del estado
        Nodo aux = pila2.remove(pila2.size()-1);
        pila2.remove(pila2.size()-1); //pop del estado
        nodo = pila2.remove(pila2.size()-1);

        nodo.fijaSiguiente(aux);

        pila2.add(nodo);

        estado = (Estado)pila2.get(pila2.size()-2);
        
        return estado;
    }
    
    private void crearTabla(){
        String directorio, linea, c, aux;
        ArrayList<String> lineas;
        int i, j, totalFilas, totalColumnas;
        
        directorio = "/home/dany/Escritorio/CUCEI/semestre/sem traductores 2/semana 4/";
        lineas = DAO.cargarArchivo(directorio+"GR2sirTable.txt");
        
        //obtener filas y columnas para crear tabla
        linea = lineas.get(0);
        totalColumnas = 0;
        totalFilas = lineas.size();
        for(i=0; i<linea.length(); i++){
            c = linea.substring(i,i+1);
            if(esNumero(c)){
                totalColumnas++;
            }
        }
        totalColumnas--;//quitar la primera columna(estados)
        tabla = new int[totalFilas][totalColumnas];
        
        //llenar tabla
        char tab = 9;//9 es el tabulador
        String arregloCaracteres[];//guarda los numeros de la tabla
        filasEstadosTabla = new int[totalFilas];
        for(i=0; i<totalFilas; i++){
            linea = lineas.get(i);
            linea = linea.replace(String.valueOf(tab), ",");
            arregloCaracteres = linea.split(",");
            filasEstadosTabla[i] = Integer.parseInt(arregloCaracteres[0]);
            for(j=0; j<totalColumnas; j++){
                tabla[i][j] = Integer.parseInt(arregloCaracteres[j+1]);
            }
            //System.out.println(linea);
        }
        
        //imprimir tabla
        
        /*for(i=0; i<totalFilas; i++){
            for(j=0; j<totalColumnas; j++){
                System.out.print(tabla[i][j] + ", ");
            }
            System.out.println();
        }*/
        /*
        for(i=0; i<totalFilas; i++){
            System.out.println(estadosTabla[i]);
        }*
    }
    
    private void crearReglas(){
        String directorio, linea, c, aux;
        ArrayList<String> lineas;
        int i, j, totalFilas, totalColumnas;
        
        directorio = "/home/dany/Escritorio/CUCEI/semestre/sem traductores 2/semana 4/";
        lineas = DAO.cargarArchivo(directorio+"GR2sirRulesId.txt");
        
        //obtener filas y columnas para crear tabla de reglas
        linea = lineas.get(0);
        totalColumnas = 0;
        totalFilas = lineas.size();
        for(i=0; i<linea.length(); i++){
            c = linea.substring(i,i+1);
            if(esNumero(c)){
                totalColumnas++;
            }
        }
        totalColumnas--;
        reglas = new int[totalFilas][totalColumnas];
        
        //llenar tabla reglas
        char tab = 9;//9 es el tabulador
        String arregloCaracteres[];
        for(i=0; i<totalFilas; i++){
            linea = lineas.get(i);
            linea = linea.replace(String.valueOf(tab), ",");
            arregloCaracteres = linea.split(",");
            for(j=0; j<totalColumnas; j++){
                reglas[i][j] = Integer.parseInt(arregloCaracteres[j]);
            }
           //System.out.println(linea);
        }
        
        //imprimir tabla
        /*
        for(i=0; i<totalFilas; i++){
            for(j=0; j<totalColumnas; j++){
                System.out.print(reglas[i][j] + ", ");
            }
            System.out.println();
        }*
    }
    
    private boolean esNumero(String caracter){
        try{
            Integer.parseInt(caracter);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
*/





























































/*

public boolean esCadenaValidaV2(){
        //el indice es el elemento de la cadena (numeros)
        boolean esValido = false, finalizo = false;
        int i, resultado, num, indice;
        int j, totalElementos, ladoDerecho, ladoIzquierdo;
        
        Nodo nodo = new Nodo();
        
        Tope tope;// = new Tope();
        Nodo desplazamiento = new Desplazamiento();
        
        tope = new Tope();
        indice = 0;
        pila2.add(tope);
        
        while(indice<1){//num!=18
            num = numeros.get(indice);//num es el numero del token
            resultado = tabla[tope.dameValor()][num];//tope es fila, num columna
            System.out.println("----------------------> Resultado: " + resultado);
            if(resultado > 0){//desplazamiento (solo aqui hace un recorrido indice++)
                nodo = new Nodo();
                desplazamiento = new Desplazamiento();
                tope = new Tope();
                
                nodo.fijaLexema(tokens.get(indice));
                desplazamiento.fijaValor(resultado);
                
                pila2.add(nodo); //agrega el token(Nodo)
                pila2.add(desplazamiento); //agrega el desplazamiento
                tope.fijaValor(resultado);
                //tope.fijaValor(resultado); //actualizar tope (fila)
                
                indice++;
            }/*
            else if(resultado < 0){//regla (cada regla hace sus pops)
                //saber si el codigo esta en la regla 0 (es correcto)
                if(resultado == -1){
                    System.out.println("Salio con regla 0");
                    esValido = true;
                    break;
                }
                
                resultado += 1; //para corregir el desplazamiento
                resultado *= -1; //convertir a positivo
                
                //obtener lado derecho e izquierdo de la regla
                ladoIzquierdo = reglas[resultado][0];
                ladoDerecho = reglas[resultado][1];
                
                switch(resultado){//revisar las reglas y hacer los pops
                    case 1:{}break;
                    case 5:{
                        System.out.println("Entro");
                        pila2.add(new DefVar(pila2));
                        indice = 90;
                        System.out.println("Salio");
                    }break;
                    case 6:{
                        //la regla no produce nada (meter nodo vacio como lado izq)
                        pila2.add(new Nodo());
                    }break;
                    case 9:{
                        //la regla no produce nada (meter nodo vacio como lado izq)
                        pila2.add(new Nodo());
                    }break;
                    default: System.out.println("Regla desconocida :(");
                }
                if(indice<80){
                    
                
                //hacer el desplazamiento
                desplazamiento = new Desplazamiento();
                resultado = tabla[tope.dameValor()][ladoIzquierdo];
                desplazamiento.fijaValor(resultado);
                pila2.add(desplazamiento);
                
                //actualizar tope
                tope = new Tope();
                tope.fijaValor(resultado);
                }
                
            }
            /*else if(resultado < 0){//regla 
                //saber si el codigo esta en la regla 0 (es correcto)
                if(resultado == -1){
                    esValido = true;
                    break;
                }
                
                resultado += 1; //para corregir el desplazamiento
                resultado *= -1; //convertir a positivo
                
                //obtener lado derecho e izquierdo de la regla
                ladoIzquierdo = reglas[resultado][0];
                ladoDerecho = reglas[resultado][1];
                
                //sacar el doble de elmentos de los que produce la fila(lado derecho)
                totalElementos = ladoDerecho*2;
                for(j=0 ; j<totalElementos; j++){
                    pila.remove(pila.size()-1);
                }
                tope.fijaValor(pila.get(pila.size()-1)); //actualizar tope (esto es necesario???SI)
                resultado = tabla[tope.dameValor()][ladoIzquierdo];//hacer una nueva busqueda en la tabla
                
                //agregar lado izquierdo y resultado
                pila.add(ladoIzquierdo);
                pila.add(resultado);
                tope.fijaValor(pila.get(pila.size()-1)); //actualizar tope
            }
            else if(resultado == -1){//codigo valido (esto es necesario???)
                finalizo = true;
                break;
            }
            else{//casilla vacia (0)
                finalizo = true;
                break;
            }*/
            
            /*System.out.println("************Pila actual");
            for(int k=0; k<pila.size(); k++){
                System.out.println(pila.get(k));
            }
            System.out.println("************");*
        }
        
        //System.out.println(">>>>> Finalizo con: " + esValido);
        System.out.println("************Pila actual total: " + pila2.size());
        Nodo n = new Nodo();
        for(int k=0; k<pila2.size(); k++){
            n = pila2.get(k);
            n.dameInfo();
            //System.out.println(n.dameInfo());
        }
        
        return esValido;
    }

*/