package Final;

import java.util.Arrays;
import java.util.List;

public class AnalizadorSemantico {
	
	public static boolean existeVar(List<String[]> tabla, String tipo, String id, String ambito) {
		int totalFilas = tabla.size();
		int i;
		
		for(i=0; i<totalFilas; i++) {
			if((tabla.get(i)[1].equals(id)) && (tabla.get(i)[2].equals(ambito))) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean existeFunc(List<String[]> tabla, String tipo, String id, String ambito, String parametros) {
		int totalFilas = tabla.size();
		int i;
		
		for(i=0; i<totalFilas; i++) {
			if((tabla.get(i)[1].equals(id)) && (tabla.get(i)[2].equals(ambito)) && tabla.get(i)[3].equals(parametros)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static String dameTipoFuncion(List<String[]> tabla, String id, String parametros) {
		int totalFilas = tabla.size();
		int i;
		
		for(i=0; i<totalFilas; i++) {
			if((tabla.get(i)[1].equals(id)) && (tabla.get(i)[2].equals("")) && tabla.get(i)[3].equals(parametros)) {
				return tabla.get(i)[0];
			}
		}
		
		return "";
	}
	
	public static void imprimirTabla(List<String[]> tabla) {
    	System.out.println("****************************Tabla actual:****************************");
    	System.out.println("Tipo, Id, ambito, parametros");
    	for(String row[]: tabla) {
    		System.out.println(Arrays.toString(row));
    	}
    }
	
	public static void imprimirErrores(List<String[]> errores) {
    	System.out.println("****************************Errores:****************************");
    	for(String row[]: errores) {
    		System.out.println(Arrays.toString(row));
    	}
    	for(int i=0; i<10; i++) {
    		System.out.println();
    	}
    }
}
