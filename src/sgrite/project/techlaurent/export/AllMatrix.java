/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgrite.project.techlaurent.export;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author fotso
 */
public class AllMatrix implements Serializable{
    
    ArrayList<boolean[][]> allContengent = new ArrayList<>();

    public AllMatrix() {
    }

    public ArrayList<boolean[][]> getAllContengent() {
        return allContengent;
    }

    public void setAllContengent(ArrayList<boolean[][]> allContengent) {
        this.allContengent = allContengent;
    }

    public boolean add(boolean[][] e) {
        return allContengent.add(e);
    }

    public boolean addAll(Collection<? extends boolean[][]> c) {
        return allContengent.addAll(c);
    }

    public void afficherTous(String après_lécriture_et_la_lecture_binaires) {
        allContengent.stream().forEach((m) -> {
            affichage(m);
        });
        
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
