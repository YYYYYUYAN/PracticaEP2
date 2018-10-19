/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaprestamoobjeto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author YUYAN
 */
public class SistemaPrestamoObjeto {
    
    //1/9/2008 FECHA DEFECTO DE NO VALIDO
    static final Date FECHA_NO_VAL = new Date(1220227200L * 1000);
    //Formato de correo 
    static final int MAX_INTENTO = 3;
    static final int MISMAFECHAMENOR = 0;
    static final int  NOMISMAFECHAMENOR = 1;
    static final int MISMAFECHAMAYOR = 2;
    
    static final String NOMBRE = "^[a-zA-Z]([a-zA-z|\\s])*$";
    static final String CORREO = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Usuario> list_usuario = new ArrayList<>();
        ArrayList<Objeto> list_objeto = new ArrayList<>();
        ArrayList<Prestamo> list_prestamo = new ArrayList<>();
        int opcion = 100;
        String s;
        while(opcion != 7){
            printMenu();
            opcion = ComprobarDatos.excepcionInput();
            switch(opcion){
                case 1:
                    if(altaUsuario(list_usuario)){
                        System.out.println("El proceso se ha hecho correctamente");
                        System.out.println(list_usuario.get(list_usuario.size()-1).toString());
                    }
                    else
                        System.out.println("El proceso fallado, Intenta de nuevo.");
                    break;
                case 2:
                    if(list_usuario.isEmpty())
                        System.out.println("No puede hacer operacion, porque no existe usuario");
                    else{
                        if(altaObjeto(list_objeto, list_usuario)){
                            System.out.println("El proceso se ha hecho correctamente");
                            System.out.println(list_objeto.get(list_objeto.size()-1).toString());
                        }
                        else
                            System.out.println("El proceso fallado");
                    }
                    break;
                case 3:
                    if(list_usuario.isEmpty()||list_objeto.isEmpty())
                        System.out.println("No puede hacer operacion, porque no existe usuario o objeto");
                    else{
                        
                        if(alquilarObjeto(list_prestamo, list_usuario, list_objeto)){
                            System.out.println("El proceso se ha hecho correctamente");
                            System.out.println(list_prestamo.get(list_prestamo.size()-1).toString());
                        }
                        else
                            System.out.println("El proceso fallado, Intenta de nuevo.");
                    }
                    break;
                case 4:
                    if(list_usuario.isEmpty())
                        System.out.println("No existe lista");
                    else{
                        s = listarObjetos(list_usuario, list_objeto,list_prestamo, opcion);
                        System.out.println(s);
                    }
                    break;
                case 5:
                    if(list_objeto.isEmpty())
                        System.out.println("No puede hacer operacion, porque no existe objeto");
                    else
                    {
                        if(bajaObjeto(list_objeto))
                            System.out.println("El proceso se ha hecho correctamente");
                        else
                            System.out.println("Error: No existe el objeto disponible");
                    }
                    break;
                case 6: 
                    if(list_usuario.isEmpty())
                        System.out.println("No existe lista");
                    else{
                        s = listarObjetos(list_usuario, list_objeto,list_prestamo, opcion);
                        System.out.println(s);
                        System.out.println("(Si no imprime nada en pantalla, es decir que no existe prestamo)");
                    }
                    break;
                case 7: 
                    System.out.println("Salir ");
                    break;
                case 8: 
                    if(modificarImporte(list_objeto))
                        System.out.println("El proceso se ha hecho correctamente");
                    else
                        System.out.println("El proceso fallado, Intenta de nuevo.");
                    break;
                case 9: 
                    if(generarFicheroPrestamo(list_usuario, list_objeto, list_prestamo))
                        System.out.println("El proceso se ha hecho correctamente");
                    else
                        System.out.println("El proceso fallado, Intenta de nuevo.");
                    break;
                default:
                    System.out.println("!!La opción no es valida.!!Deber ser numero entero entre 1 y 7");
            }  
        }
    }
    
    public static boolean modificarImporte(ArrayList<Objeto> list_objeto)
    {
        int opcion, pos;
        if(printListaObjetosDisponible(list_objeto)){
            System.out.println("Introduce identificador de objeto: ");
            opcion = ComprobarDatos.excepcionInput();
            pos = buscarObjeto(list_objeto, opcion);     
            if(pos != -1){ 
                String s1 = "Introduce el nuevo coste del objeto: ";
                float coste = getCosteTeclado(s1);
                if(coste >= 0){
                    list_objeto.get(pos).setCoste(coste);
                    return true;
                }
                else
                    System.out.println("El coste debe ser mayor que 0");
            }
            else
                System.out.println("No existe objeto en la lista de objetos");
        }
        else
            System.out.println("No existe objeto disponible");
        
        return false;       
    }
    
    public static void printMenu(){
        System.out.println(" ---------\nMenu");
        System.out.println(" 1- Alta Usuario ");
        System.out.println(" 2- Alta Objeto ");
        System.out.println(" 3- Alquiler de objeto ");
        System.out.println(" 4- Lista todos los objetos ");
        System.out.println(" 5- Baja de objeto ");
        System.out.println(" 6- Mostrar saldos ");
        System.out.println(" 8- Modificar el importe ");
        System.out.println(" 9- Guardar saldos en fichero");
        System.out.println(" 7- Salir \n---------");
        System.out.println(" \nIntroduce la opcion: ");
    }
    
    
    public static boolean generarFicheroPrestamo(ArrayList<Usuario> list_u, ArrayList<Objeto> list_o, ArrayList<Prestamo> list_p){
         String s = listarObjetos(list_u, list_o, list_p, 6);
         return generarFichero(s);
    }

    public static boolean generarFichero(String s){
        try{
            BufferedWriter salida = new BufferedWriter(new FileWriter(new File("src/saldo.txt")));
            salida.write(s);
            salida.flush();
            salida.close();
            return true;
        } catch(IOException e) {
            System.out.println("Excepcion: " + e);
        }   
        return false;
    }
    /**
     * Baja objeto del lista cambiando su fecha de disponible a no disponible
     * @param list_objeto
     * @return true o false
     */
    public static boolean bajaObjeto(ArrayList<Objeto> list_objeto){
        int opcion, pos;
        
        if(printListaObjetosDisponible(list_objeto)){
            System.out.println("Introduce identificador de objeto: ");
            opcion = ComprobarDatos.excepcionInput();
            pos = buscarObjeto(list_objeto, opcion);     
            if(pos != -1){ 
                list_objeto.get(pos).setFechaFinal(FECHA_NO_VAL);
                return true;
            }
        }
        
        return false;
    }
    

    /**
     * Mostrar saldo o todos los objetos según opcion 6 o opcion 4
     * @param list_u lista de usuarios
     * @param list_o lista de objetos
     * @param list_p lista de prestamos
     * @param opcion opcion 4 o opcion 6
     * @return 
     */
    public static String listarObjetos(ArrayList<Usuario> list_u, ArrayList<Objeto> list_o, ArrayList<Prestamo> list_p, int opcion){
        Iterator<Usuario> it_u = list_u.iterator();
        Iterator<Objeto> it_o = list_o.iterator();
        Iterator<Prestamo> it_p = list_p.iterator();
        boolean flag_o, flag_p, flag_op6;
        Usuario usuario;
        Objeto objeto;
        Prestamo prestamo;
        float importe = 0;
        String text = "";
        String text4 = "";
        while (it_u.hasNext()) {
            usuario = it_u.next();
            flag_o = false; 
            flag_op6 = usuario.getPrestamo();
            if(opcion == 4 || flag_op6){
                
                text += usuario.toString();
                text += "\n\n\tOBJETOS DEL PROPIEDARIO " + usuario.getIdUsuario();
                text4 += usuario.toString();
                text4 += "\n\n\tOBJETOS DEL PROPIEDARIO " + usuario.getIdUsuario();
                
               // System.out.println(usuario.toString());
               // System.out.println("\n\tOBJETOS DEL PROPIEDARIO " + usuario.getIdUsuario());
            }  
            while((it_o.hasNext()&&((opcion == 4 )|| flag_op6))) {
                objeto = it_o.next();
                flag_p = false;
                if(usuario.getIdUsuario() == objeto.getIdPropiedario()){
                    if(opcion == 4)
                        text4 += objeto.toString();
                       // System.out.println(objeto.toString());
                    
                    text += "\n\n\t\tPRESTAMOS DEL OBJETO " + objeto.getIdObjeto();
                    text4 += "\n\n\t\tPRESTAMOS DEL OBJETO " + objeto.getIdObjeto();
                    //System.out.println("\n\t\tPRESTAMOS DEL OBJETO " + objeto.getIdObjeto());
                    flag_o = true;
                    while(it_p.hasNext()){
                        prestamo = it_p.next();
                        if(objeto.getIdObjeto() == prestamo.getIdObjeto()){
                            flag_p = true;
                            if(opcion == 6)
                                importe += prestamo.getStartup();
                           
                          //  System.out.println(prestamo.toString());
                            text += prestamo.toString();
                            text4 += prestamo.toString();
                        }  
                    }
                    if(!flag_p){
                        text += "\n\n\t\tEl objeto " + objeto.getIdObjeto()+ " no tiene prestamos asociados.";
                        text4 += "\n\n\t\tEl objeto " + objeto.getIdObjeto()+ " no tiene prestamos asociados.";
                        //System.out.println("\n\t\tEl objeto " + objeto.getIdObjeto()+ " no tiene prestamos asociados.");
                    }
                    it_p = list_p.iterator();
                }
            }
            
            if(!flag_o && (flag_op6 || (opcion == 4))){
                text += "\n\n\tEl propietario " + usuario.getIdUsuario()+ " no tiene objetos asociados.";
                text4 += "\n\n\tEl propietario " + usuario.getIdUsuario()+ " no tiene objetos asociados.";
                //System.out.println("\n\tEl propietario " + usuario.getIdUsuario()+ " no tiene objetos asociados.");
            }
            
            if(flag_op6 && opcion == 6){
                text += "\n\nImporte total acumulado para la startup: " + importe + " euros";
               // System.out.println("\nImporte total acumulado para la startup: " + importe + " euros");
                importe = 0;
            }
                
            it_o = list_o.iterator();
        }  
        
        if(opcion == 4)
            return text4;
        else
            return text;
    }
   
    public static String getNombreOCorreo(String preg, String formato)
    {
        Scanner leer = new Scanner(System.in);
        String res;
        int i = 0;
        do{
            System.out.println(preg);
            res = leer.nextLine();
            if(!ComprobarDatos.comprobarFormato(res, formato))
            {
                System.out.println("Formato incorrecto ");  
                res = "";
            }
            else
                i = MAX_INTENTO;
           i++;
        }while(i < MAX_INTENTO);
        
        return res;    
    }
    /**
     * Alta usuario
     * @param list_u lista de usuarios
     * @return 
     */
    public static boolean altaUsuario(ArrayList<Usuario> list_u){
        String nombre, email;
        String s1 = "Introduce el nombre del usuario: ";
        String s2 = "Introduce el correo del usuario: ";
        nombre = getNombreOCorreo(s1, NOMBRE);
        if(nombre.equals(""))
            return false;
        email = getNombreOCorreo(s2, CORREO);
        if(email.equals(""))
            return false;
        //debe tener por minimo una letra
        Usuario u = new Usuario(nombre, email);
        list_u.add(u);
        return true;
    }
    
    
    public static Date getDateTeclado(String preg, Date f, int tipo )
    {
        Date fecha;
        int i = 0;
        do{
            System.out.println(preg);
            fecha = ComprobarDatos.excepcionInputDate();
            if(fecha != null){
                if(tipo == MISMAFECHAMAYOR){
                    if(ComprobarDatos.comprobarFecha(fecha,f, tipo))
                        i = MAX_INTENTO;
                    else
                    {
                        System.out.println("El rango de fecha no es valido");
                        fecha = null;
                    }
                }
                else
                    if(ComprobarDatos.comprobarFecha(f, fecha, tipo))
                        i = MAX_INTENTO;
                    else
                    {
                        System.out.println("El rango de fecha no es valido");
                        fecha = null;
                    }
            }
            i++;
        }while(i < MAX_INTENTO);
        
        return fecha;
    }
   
    public static float getCosteTeclado(String preg){
        float res;
        int i = 0;
        do{
            System.out.println(preg);
            res = ComprobarDatos.excepcionInputFloat();
            if(res < 0)
                System.out.println("El numero introducido debe ser mayor o igual que 0");
            else
                 i = MAX_INTENTO;
            i++;
        }while(i < MAX_INTENTO);
        
        return res;
    }
    
    /**
     * Crea un objeto para un usuario ya está creado
     * @param list_o lista de objetos
     * @param list_u lista de usuarios
     * @return objeto
     */
    public static boolean altaObjeto(ArrayList<Objeto> list_o, ArrayList<Usuario> list_u){
        String descripcion;
        int id_propiedario;
        Scanner leer = new Scanner(System.in);
        int pos;
        
        printListaUsuarios(list_u);
        System.out.println("Elige el numero de usuario: ");
        id_propiedario = ComprobarDatos.excepcionInput();
        pos = buscarUsuario(list_u, id_propiedario);
        if(pos != -1){
            System.out.println("Introduce la descripcion del objeto: ");
            descripcion = leer.nextLine();
            
            String s1 = "Introduce la fecha de inicio del disponibilidad del objeto (dd/mm/aaaa): ";
            Date actual = new Date();
            Date fecha_inicio = getDateTeclado(s1, actual, MISMAFECHAMENOR);
            if(fecha_inicio == null)
                return false;
            String s2 = "Introduce la fecha de fin del disponibilidad del objeto (dd/mm/aaaa):  ";
            Date fecha_final = getDateTeclado(s2, fecha_inicio, NOMISMAFECHAMENOR);
            if(fecha_final == null)
                return false;
            String s3 = "Introduce el coste del objeto: ";
            float coste = getCosteTeclado(s3);
            if(coste < 0)
                return false;
            Objeto o = new Objeto(descripcion, fecha_inicio, fecha_final, coste, id_propiedario);
            list_o.add(o);
                return true;
        }
        else    
            System.out.println("No existe el usuario con este id.");
        
        return false;
    }
   
    
    
    
    
    /**
     * Crea un prestamo de un objeto para un usuario
     * @param list_p lista d prestamos
     * @param list_u lista de usuarios
     * @param list_o lista de objetos
     * @return prestamo
     */
    public static boolean alquilarObjeto(ArrayList<Prestamo> list_p, ArrayList<Usuario> list_u, ArrayList<Objeto> list_o){
       

        Scanner leer = new Scanner(System.in);
        printListaUsuarios(list_u);
        int pos_u, pos_o;
        
        System.out.println("Elige el numero de usuario: ");
        int id_u = ComprobarDatos.excepcionInput();
        pos_u = buscarUsuario(list_u, id_u);
        if(pos_u!= -1){
            String nombre = list_u.get(pos_u).getNombre();
            if(printListaObjetosDisponible(list_o)){
                System.out.println("Elige el numero de objeto: ");
                int id_o = ComprobarDatos.excepcionInput();
                pos_o = buscarObjeto(list_o, id_o);
                if(pos_o != -1){
                    Objeto objeto = list_o.get(pos_o);
                    String s1 = "Introduce la fecha de inicio del alquiler del objeto (dd/mm/aaaa): ";
                    Date inicio = objeto.getFechaInicio();
                    Date fecha_inicio = getDateTeclado(s1, inicio, MISMAFECHAMENOR);
                    if(fecha_inicio == null)
                        return false;
                    String s2 = "Introduce la fecha de fin del alquiler del objeto (dd/mm/aaaa):  ";
                    Date fin = objeto.getFechaFinal();
                    Date fecha_final = getDateTeclado(s2, fin, MISMAFECHAMAYOR);
                    if(fecha_final == null)
                        return false;
                    if(fecha_final.after(fecha_inicio)){
                        int dia = (int) ((fecha_final.getTime() - fecha_inicio.getTime()) / (24 * 60 * 60 * 1000)) + 1;
                        float importe = dia * list_o.get(pos_o).getCoste();
                        Prestamo p = new Prestamo(fecha_inicio, fecha_final, importe, nombre, id_o);
                        list_p.add(p);
                        pos_u = buscarUsuario(list_u, objeto.getIdPropiedario());
                        list_u.get(pos_u).setPrestamo(true);
                        return true;
                    }
                    else
                        System.out.println("Fecha final debe ser mayor que fecha inicio");
                }
                else
                    System.out.println("No existe el objeto con este id." );
            }
            else
             System.out.println("No existe objeto disponible");
        }
        else
            System.out.println("No existe el usuario con este id.");
        return false;
    }
    
    
    /**
     * Imprime en pantalla los objetos disponibles
     * @param lista lista de todos objetos
     * @return si existe objeto en lista disponible
     */
    public static boolean printListaObjetosDisponible(ArrayList<Objeto> lista){
        Iterator<Objeto> iterator = lista.iterator();
        Objeto o;
        Date actual = new Date();
        boolean existe = false;
	while (iterator.hasNext())
        {   
            o = iterator.next();
            if(o.getFechaFinal().after(actual)){
                System.out.println(o.toString());
                existe = true;
            }
        }
        System.out.println("\n");
        
        return existe;
    }
    
    
    /**
     * Imprime en pantalla todos usuarios 
     * @param lista lista de usuarios
     */
    public static void printListaUsuarios(ArrayList<Usuario> lista){
        Iterator<Usuario> iterator = lista.iterator();
        while (iterator.hasNext()) 
            System.out.println(iterator.next().toString());
        System.out.println("\n");
    }

    
    /**
     * Buscar la posicion de un objeto en una lista de objetos, si encuentra 
     * devuelve posicion y si no, devuelve -1, se aplica busqueda binaria
     * @param list_o lista de objetos
     * @param id id de objeto que desea buscar en la lista 
     * @return posicion
     */
    public static int buscarObjeto(ArrayList<Objeto> list_o, int id){
        int inicio = 0;
        int fin = list_o.size() - 1;
        int pos;
        
        while(inicio <= fin) {
            pos = (inicio+fin) / 2;
            if (list_o.get(pos).getIdObjeto()== id)
                return pos;
            else if (id < list_o.get(pos).getIdObjeto())
                fin = pos - 1;
            else
                inicio = pos + 1;
        }

        return -1;
    }
    
    
    /**
     * Buscar la posicion de un usuario en una lista de usuarios, si encuentra 
     * devuelve posicion y si no, devuelve -1, se aplica busqueda binaria
     * @param list_u lista de usuarios
     * @param id id de usuario
     * @return posicion
     */
    public static int buscarUsuario(ArrayList<Usuario> list_u, int id){
        int inicio = 0;
        int fin = list_u.size() - 1;
        int pos;
        
        while(inicio <= fin) {
            pos = (inicio+fin) / 2;
            if (list_u.get(pos).getIdUsuario() == id)
                return pos;
            else if (id < list_u.get(pos).getIdUsuario())
                fin = pos - 1;
            else
                inicio = pos + 1;
        }
        
        return -1;
    }
}
    

