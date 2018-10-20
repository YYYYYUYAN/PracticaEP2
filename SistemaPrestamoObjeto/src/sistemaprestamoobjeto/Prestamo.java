/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaprestamoobjeto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase de prestamo
 */
public class Prestamo {
    private final int  id_objeto;
    private Date fecha_inicio;
    private Date fecha_final;
    private float importe;
    private float startup;
    private String nombre;
    
    /**
     * Constructor
     * @param fecha_inicio
     * @param fecha_final
     * @param importe
     * @param nombre
     * @param id_objeto 
     */
    public Prestamo(Date fecha_inicio, Date fecha_final, float importe, String nombre, int id_objeto){
    
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.importe = importe;
        startup = (float) (0.1 * importe);
        this.nombre = nombre;
        this.id_objeto = id_objeto;
    }
    
    /**
     * Devuelve id de objeto
     * @return 
     */
    public int getIdObjeto(){
        return id_objeto;
    }
    
    /**
     * Devuelve importe de startup 
     * @return 
     */
    public float getStartup(){
        return startup;
    }
    
    /**
     * Cambiar fecha inicial
     * @param d 
     */
    public void setFechaInicio(Date d){
        fecha_inicio = d;
    }
    
    /**
     * Cambiar fecha final
     * @param d 
     */
    public void setFechaFinal(Date d){
        fecha_final = d;
    }
    
    /**
     * Devuelve nombre
     * @return 
     */
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Cambiar nombre
     * @param nombre 
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    /**
     * Cambiar importe de prestamo
     * @param importe 
     */
    public void setImporte(float importe){
        this.importe = importe;
        this.startup = (float) (0.1 * importe);
    }
    
    /**
     * Devuelve importe de prestamo
     * @return 
     */
    public float getImporte(){
        return importe;
    }
    
    /**
     * Imprime prestamo en pantalla
     * @return 
     */
    @Override
    public String toString(){    
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
         String fecha_i = formato.format(fecha_inicio);
         String fecha_f = formato.format(fecha_final);
        String s = ("\n\n\t\tNombre del cliente: "+nombre+
                "\n\t\tFecha del prestamo " +
                fecha_i + " - " + fecha_f + "\n\t\tImporte para el propietario: "+
                importe + " euros\n\t\tImporte para la startup: "+startup+ " euros");
        return s;
    }
}
