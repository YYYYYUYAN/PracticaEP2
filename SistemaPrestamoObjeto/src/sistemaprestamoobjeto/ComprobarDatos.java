/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemaprestamoobjeto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Younes Narjis
 * @author Yuyan Wu
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
    /**
     * Comprueba si es un float
     * @return 
     */
    
    public static float excepcionInputFloat(){
        
        Scanner leer = new Scanner(System.in);
        float res = -1;
        
        try{
            res = leer.nextFloat();
        }catch(InputMismatchException e){
            System.out.println("Excepcion: " + e);
        }
        
        return res;
    }
    /**
     * Funcion para tratar la excepcion del formato de las fechas
     * @return 
     */
    public static Date excepcionInputDate(){
        String fecha;
        Scanner leer = new Scanner(System.in);
        fecha = leer.nextLine();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formato.parse(fecha);
        } catch (ParseException ex) {
            System.out.println("Exception: " + ex);
        }
       
       return date;
    }
    /**
     * Comprueba el formato de la fecha
     * @param nombre
     * @param formato
     * @return 
     */
    public static boolean comprobarFormato(String nombre, String formato){
    //debe tener por minimo una letra
        Pattern p;
        Matcher m;
        p = Pattern.compile(formato);
        m = p.matcher(nombre);
        return m.matches();
    }
    
    /**
     *  Funcion para comprobar la fecha
     * @param f1
     * @param f2
     * @param tipo
     * @return 
     */
    public static boolean comprobarFecha(Date f1, Date f2, int tipo){
        
        if(tipo == 1)
            return f2.after(f1);
        else
            return (isSameDay(f1, f2) || f2.after(f1));
    }
    
/**
 * Comprueba si es el misma fecha
 * @param f1
 * @param f2
 * @return 
 */
    public static boolean isSameDay(Date f1, Date f2){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecha2 = formato.format(f2);
        String fecha1 = formato.format(f1);
        return fecha2.equals(fecha1);
    }
}
