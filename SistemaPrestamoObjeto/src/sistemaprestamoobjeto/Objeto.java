/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaprestamoobjeto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase para guardar informacion de un objeto
 * @author YUYAN
 */
public class Objeto {
    private final int id_objeto;
    private final int id_propiedario;
    private String descripcion;
    private Date fecha_inicio;
    private Date fecha_final;
    private float coste;
    
    /**
     * Constructor
     * @param id_objeto 
     * @param descripcion
     * @param fecha_inicio
     * @param fecha_final
     * @param coste
     * @param id_propiedario 
     */
    public Objeto(int id_objeto,String descripcion, Date fecha_inicio, Date fecha_final, float coste, int id_propiedario){
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_final = fecha_final;
        this.coste = coste;
        this.id_objeto = id_objeto;   
        this.id_propiedario = id_propiedario;
    }
    
    /**
     * Cambiar la descripcion
     * @param descripcion 
     */
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    /**
     * Cambiar fecha inicio del objeto
     * @param fecha 
     */
    public void setFechaInicio(Date fecha){
        this.fecha_inicio = fecha;
    }
    
    /**
     * cambiar fecha final del objeto
     * @param fecha 
     */
    public void setFechaFinal(Date fecha){
        this.fecha_final = fecha;
    }
    
    /**
     * Cambiar coste por dia del objeto
     * @param coste 
     */
    public void setCoste(float coste){
        this.coste = coste;
    }
    
    /**
     * Devuelve coste del objeto
     * @return 
     */
    public float getCoste(){
        return coste;
    }
    
    /**
     * Devuelve id de objeto
     * @return id
     */
    public int getIdObjeto(){
        return id_objeto;
    }
    
    /**
     * Devuelve fecha inicio de objeto
     * @return fecha
     */
    public Date getFechaInicio(){
        return fecha_inicio;
    }
    
    /**
     * Devuelve fecha final de objeto
     * @return fecha
     */
    public Date getFechaFinal(){
        return fecha_final;
    }
    
    /**
     * Devuelve id de propiedario
     * @return id
     */
    public int getIdPropiedario(){
        return id_propiedario;
    }
    
    /**
     * Imprime el objeto
     */
    public String toString(){
        String id = String.format("%03d", id_objeto);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecha_i = formato.format(fecha_inicio);
        String fecha_f = formato.format(fecha_final);
        //System.out.println("\tOBJETOS DEL PROPIEDARIO: "+id_propiedario);
        String s = ("\n\tCódigo de objeto: "+id+
                "\n\tDescripcion: "+ descripcion + "\n\tFecha de disponibilidad: " +
                fecha_i + " - " + fecha_f + "\n\tCoste del préstamo por día: "+
                coste + " euros");
        return s;
    }
}
