/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaprestamoobjeto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author YUYAN
 */
public class SistemaPrestamoObjeto {
    
    //1/9/2008 FECHA DEFECTO DE NO VALIDO
    static final Date FECHA_NO_VAL = new Date(1220227200L * 1000);
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Usuario> list_usuario = new ArrayList<>();
        ArrayList<Objeto> list_objeto = new ArrayList<>();
        ArrayList<Prestamo> list_prestamo = new ArrayList<>();
        int opcion = 100;

        while(opcion != 7){
            printMenu();
                        
            opcion = excepcionInput();
            switch(opcion){
                case 1:
                    if(altaUsuario(list_usuario)){
                        System.out.println("El proceso se ha hecho correctamente");
                        list_usuario.get(list_usuario.size()-1).print();
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
                            list_objeto.get(list_objeto.size()-1).print();
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
                            list_prestamo.get(list_prestamo.size()-1).print();
                        }
                        else
                            System.out.println("El proceso fallado, Intenta de nuevo.");
                    }
                    break;
                case 4:
                    if(list_usuario.isEmpty())
                        System.out.println("No existe lista");
                    else
                        listarObjetos(list_usuario, list_objeto,list_prestamo, opcion);
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
                        listarObjetos(list_usuario, list_objeto,list_prestamo, opcion);
                        System.out.println("(Si no imprime nada en pantalla, es decir que no existe prestamo)");
                    }
                    break;
                case 7: 
                    System.out.println("Salir ");
                    break;
                default:
                    System.out.println("!!La opción no es valida.!!Deber ser numero entero entre 1 y 7");
            }  
        }
    }
    
    public static void printMenu(){
        System.out.println(" ---------\nMenu");
        System.out.println(" 1- Alta Usuario ");
        System.out.println(" 2- Alta Objeto ");
        System.out.println(" 3- Alquiler de objeto ");
        System.out.println(" 4- Lista todos los objetos ");
        System.out.println(" 5- Baja de objeto ");
        System.out.println(" 6- Mostrar saldos ");
        System.out.println(" 7- Salir \n---------");
        System.out.println(" \nIntroduce la opcion: ");
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
            opcion = excepcionInput();
            pos = buscarObjeto(list_objeto, opcion);     
            if(pos != -1){ 
                list_objeto.get(pos).setFechaFinal(FECHA_NO_VAL);
                return true;
            }
        }
        
        return false;
    }
    
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
     * Mostrar saldo o todos los objetos según opcion 6 o opcion 4
     * @param list_u lista de usuarios
     * @param list_o lista de objetos
     * @param list_p lista de prestamos
     * @param opcion opcion 4 o opcion 6
     */
    public static void listarObjetos(ArrayList<Usuario> list_u, ArrayList<Objeto> list_o, ArrayList<Prestamo> list_p, int opcion){
        Iterator<Usuario> it_u = list_u.iterator();
        Iterator<Objeto> it_o = list_o.iterator();
        Iterator<Prestamo> it_p = list_p.iterator();
        boolean flag_o, flag_p, flag_op6;
        Usuario usuario;
        Objeto objeto;
        Prestamo prestamo;
        float importe = 0;
        
        while (it_u.hasNext()) {
            usuario = it_u.next();
            flag_o = false; 
            flag_op6 = usuario.getPrestamo();
            if(opcion == 4 || flag_op6){
                usuario.print();
                System.out.println("\n\tOBJETOS DEL PROPIEDARIO " + usuario.getIdUsuario());
            }  
            while((it_o.hasNext()&&((opcion == 4 )|| flag_op6))) {
                objeto = it_o.next();
                flag_p = false;
                if(usuario.getIdUsuario() == objeto.getIdPropiedario()){
                    if(opcion == 4)
                        objeto.print();
                    System.out.println("\n\t\tPRESTAMOS DEL OBJETO " + objeto.getIdObjeto());
                    flag_o = true;
                    while(it_p.hasNext()){
                        prestamo = it_p.next();
                        if(objeto.getIdObjeto() == prestamo.getIdObjeto()){
                            flag_p = true;
                            if(opcion == 6)
                                importe += prestamo.getStartup();
                            prestamo.print();
                        }  
                    }
                    if(!flag_p)
                        System.out.println("\n\t\tEl objeto " + objeto.getIdObjeto()+ " no tiene prestamos asociados.");
                    it_p = list_p.iterator();
                }
            }
            
            if(!flag_o && (flag_op6 || (opcion == 4)))
                System.out.println("\n\tEl propietario " + usuario.getIdUsuario()+ " no tiene objetos asociados.");
            
            
            if(flag_op6 && opcion == 6){
                System.out.println("\nImporte total acumulado para la startup: " + importe + " euros");
                importe = 0;
            }
                
            it_o = list_o.iterator();
        }  
    }
   
    /**
     * Alta usuario
     * @param list_u lista de usuarios
     * @param id id de usuario
     * @return 
     */
    public static boolean altaUsuario(ArrayList<Usuario> list_u, int id){
        String nombre, email;
        Pattern p;
        Matcher m;
        Scanner leer = new Scanner(System.in);
        
        System.out.println("Introduce el nombre completo de usuario: ");
        nombre = leer.nextLine();
        System.out.println("Introduce el correo electronico de usuario: ");
        email = leer.nextLine();
        
        //debe tener por minimo una letra
        String formato_nombre = "^[a-zA-Z]([a-zA-z|\\s])*$";
        p = Pattern.compile(formato_nombre);
        m = p.matcher(nombre);
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
    
    /**
     * Crea un objeto para un usuario ya está creado
     * @param list_o lista de objetos
     * @param list_u lista de usuarios
     * @param id id de objeto
     * @return objeto
     */
    public static boolean altaObjeto(ArrayList<Objeto> list_o, ArrayList<Usuario> list_u, int id){
        String descripcion, fecha_inicio, fecha_final;
        int id_propiedario;
        Scanner leer = new Scanner(System.in);
        int pos;
        float coste = 0;
        
        printListaUsuarios(list_u);
        System.out.println("Elige el numero de usuario: ");
        id_propiedario = excepcionInput();
        pos = buscarUsuario(list_u, id_propiedario);
        if(pos != -1){
            System.out.println("Introduce la descripcion del objeto: ");
            descripcion = leer.nextLine();
            System.out.println("Introduce la fecha de inicio del disponibilidad del objeto (dd/mm/aaaa): ");
            fecha_inicio = leer.nextLine();
            System.out.println("Introduce la fecha de fin del disponibilidad del objeto (dd/mm/aaaa):  ");
            fecha_final = leer.nextLine();
            System.out.println("Introduce el coste del objeto: ");
            String cost = leer.nextLine();
            try{
                    try{
                    coste = Float.parseFloat(cost);
                    }catch (NumberFormatException n){
                        System.out.println("Excepcion: " + n);
                    }
                    Date f_i = toDate(fecha_inicio);
                    Date f_f = toDate(fecha_final);
                    Date actual = new Date();
                    if(((coste > 0 && f_f.after(f_i)) && (f_i.after(actual)||isSameDay(fecha_inicio, actual)))){
                        Objeto o = new Objeto(id, descripcion, f_i, f_f, coste, id_propiedario);
                        list_o.add(o);
                        return true;
                    }
                    System.out.println("Fecha de inicio debe ser igual o mayor que día actual del sistema "
                            + "\ny fecha final debe mayor que fecha de inicio");
                }catch(ParseException e ){
                    System.out.println("Excepcion: " + e);
                }
        }
        else    
            System.out.println("No existe el usuario con este id.");
        
        return false;
    }
    
    
    /**
     * Comprobar dos fechas si son misma dia 
     * @param f1 fecha de tipo string
     * @param f2 fecha de tipo Date
     * @return true o false
     */
    public static boolean isSameDay(String f1, Date f2){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = formato.format(f2);
        return fecha.equals(f1);
    }
    
    
    /**
     * Tranformar fecha de tipo String a Date
     * @param fecha fecha de tipo String
     * @return fecha de tipo Date
     * @throws ParseException 
     */
    public static Date toDate(String fecha) throws ParseException{
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formato.parse(fecha);
        return date;
    }
    
    
    /**
     * Crea un prestamo de un objeto para un usuario
     * @param list_p lista d prestamos
     * @param list_u lista de usuarios
     * @param list_o lista de objetos
     * @return prestamo
     */
    public static boolean alquilarObjeto(ArrayList<Prestamo> list_p, ArrayList<Usuario> list_u, ArrayList<Objeto> list_o){
       
        String fecha_inicio, fecha_final, nombre;
        Scanner leer = new Scanner(System.in);
        printListaUsuarios(list_u);
        int pos_u, pos_o;
        
        System.out.println("Elige el numero de usuario: ");
        int id_u = excepcionInput();
        pos_u = buscarUsuario(list_u, id_u);
        if(pos_u!= -1){
            nombre = list_u.get(pos_u).getNombre();
            if(printListaObjetosDisponible(list_o)){
                System.out.println("Elige el numero de objeto: ");
                int id_o = excepcionInput();
                pos_o = buscarObjeto(list_o, id_o);
                if(pos_o != -1){
                    System.out.println("Introduce la fecha de inicio del alquiler del objeto (dd/mm/aaaa): ");
                    fecha_inicio = leer.nextLine();
                    System.out.println("Introduce la fecha de fin del alquiler del objeto (dd/mm/aaaa):  ");
                    fecha_final = leer.nextLine();
                    try{
                        Date f_i = toDate(fecha_inicio);
                        Date f_f = toDate(fecha_final);
                        Objeto objeto = list_o.get(pos_o);
                        Date fin = objeto.getFechaFinal();
                        Date inicio = objeto.getFechaInicio();
                        if((isSameDay(fecha_inicio, inicio)||(f_i.after(inicio)))){
                            if((isSameDay(fecha_final,fin)|| f_f.before(fin))){
                                if(f_f.after(f_i)){
                                    int dia = (int) ((f_f.getTime() - f_i.getTime()) / (24 * 60 * 60 * 1000)) + 1;
                                    float importe = dia * list_o.get(pos_o).getCoste();
                                    Prestamo p = new Prestamo(f_i, f_f, importe, nombre, id_o);
                                    list_p.add(p);
                                    pos_u = buscarUsuario(list_u, objeto.getIdPropiedario());
                                    list_u.get(pos_u).setPrestamo(true);
                                    return true;
                                }
                            }
                        }
                        System.out.println("El objeto no esta disponible en esta fecha");
                    }catch(ParseException e){
                        System.out.println("Excepcion: "+ e);
                    }
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
                o.print();
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
            iterator.next().print();
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
    

