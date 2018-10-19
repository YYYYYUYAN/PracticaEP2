/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemaprestamoobjeto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author youne
 */
public class ComprobarDatos {
    
        /**
     * Devuelve opcion de menu con excepcion controlada de tipo de variable de entrada
     * @return opcion
     */
    public static int excepcionInput(){
        Scanner leer = new Scanner(System.in);
        int opcion;
        opcion = -1;
        try{
            opcion = leer.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Excepcion: " + e);
        }
        return opcion;
    }
    
    
    public static boolean comprobarFormato(String nombre, String formato){
    //debe tener por minimo una letra
        Pattern p;
        Matcher m;
        p = Pattern.compile(formato);
        m = p.matcher(nombre);
        return m.matches();
    }
    
    public static boolean comprobarFecha(Date f1, Date f2){

        return (isSameDay(f1, f2) || f2.after(f1));
    }
    

    public static boolean isSameDay(Date f1, Date f2){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecha2 = formato.format(f2);
        String fecha1 = formato.format(f1);
        return fecha2.equals(fecha1);
    }
}
