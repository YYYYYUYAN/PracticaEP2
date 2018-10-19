/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemaprestamoobjeto;

/**
 * Clase de usuario
 * @author younes
 */
public class Usuario {
    private String nombre;
    private static int id_usuario_proxi;
    private final int id_usuario;
    private String email;
    private boolean prestamo;

    /**
     * Constructor de usuario
     * @param nombre
     * @param email 
     */
    public Usuario (String nombre, String email){
        id_usuario = id_usuario_proxi;
        id_usuario_proxi = id_usuario_proxi + 1;
        this.nombre = nombre;
        this.email = email;     
        this.prestamo = false;
    }
     
    /**
     * Imprime el usuario
     * @return 
     */ 
    @Override
    public String toString(){
        String s = ("\n\nPROPIETARIO " + getIdUsuario() + 
                "\nNombre del propietario: " + nombre + 
                " \nCorreo Electronico: " + email);
        return s;
    }
    
    /**
     * Poner estado de prestado, si usuario tiene alguno prestamo o no,
     * Devuelve true si tiene prestamo, en caso contrario, devuelve false;
     * @param p true o false
     */
    public void setPrestamo(boolean p){
        this.prestamo = p;
    }
    
    /**
     * Devuelve estado de prestamo
     * @return true o false
     */
    public boolean getPrestamo()
    {
        return prestamo;
    }
    
    /**
     * Cambiar nombre
     * @param nom 
     */
    public void setNombre(String nom){
        nombre = nom;
    }

    /**
     * Devuelve nombre
     * @return 
     */
    public String getNombre ()
    {
        return nombre;
    }

    /**
     * Devuelve id de usuario
     * @return id
     */
    public int getIdUsuario (){
        return id_usuario; 
    }

    /**
     * Cambiar email
     * @param mail 
     */
    public void setEmail(String mail){
        email = mail;
    }

    /**
     * Devuelve email
     * @return email
     */
    public String getEmail (){
        return email;
    }
}