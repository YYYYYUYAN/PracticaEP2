/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaprestamoobjeto;

import java.util.Comparator;

/**
 *
 * @author YUYAN
 */
public class SortArrayList implements Comparator<Usuario>{
    @Override
    public int compare(Usuario t, Usuario t1) {
        if(t.getPrestamo() > t1.getPrestamo())
            return -1;
        if(t.getPrestamo() < t1.getPrestamo())
            return 1;
        
        return 0;
    }
}
