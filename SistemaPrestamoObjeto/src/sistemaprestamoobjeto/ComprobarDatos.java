/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemaprestamoobjeto;

import java.util.InputMismatchException;
import java.util.Scanner;
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
    public static String Nombre(){
    //debe tener por minimo una letra
        String formato_nombre = "^[a-zA-Z]([a-zA-z|\\s])*$";
        p = Pattern.compile(formato_nombre);
        m = p.matcher(nombre);
    }
    
    public static String Nombre(){
        if(m.matches()){
            String formato_correo = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            p = Pattern.compile(formato_correo);
            m = p.matcher(email);
            if(m.matches()){
                Usuario u = new Usuario(id, nombre, email);
                list_u.add(u);
                return true;
            }
            System.out.println("El formato de correo incorrecto");
        }
        else
            System.out.println("El formato de nombre incorrecto, solo puede ser letras");
        
        return false;
    }
    
}
