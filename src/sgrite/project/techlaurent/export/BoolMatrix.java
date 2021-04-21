/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgrite.project.techlaurent.export;

import java.io.Serializable;

/**
 *
 * @author fotso
 */
public class BoolMatrix implements Serializable{
    boolean[][] m;

    public BoolMatrix(boolean[][] m) {
        this.m = m;
    }
    
    

    public boolean[][] getM() {
        return m;
    }

    public void setM(boolean[][] m) {
        this.m = m;
    }

    public void afficherM(String après_lécriture_et_la_lecture_binaires) {
        affichage(m);
    }
     void affichage(boolean[][] m) {
        for (boolean[] m1 : m) {
            for (boolean n : m1) {
                int a = (n) ? 1 : 0;
                System.out.print(" " + a);

            }
            System.out.println();
        }
         System.out.println();
    }
    
    
    
}
