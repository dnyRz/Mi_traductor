
package Final;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class DAO {

    
    public static ArrayList<String> cargarArchivo(String direccion){//direccion del archivo
        BufferedReader bf;
        String cadena="", leido;
        ArrayList<String> lineas = new ArrayList<>();
        try {
            bf = new BufferedReader(new FileReader(direccion));
            while((leido = bf.readLine()) != null){
                lineas.add(leido);
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error al leer el archivo: " + e);
        }
        return lineas;
    }
}
