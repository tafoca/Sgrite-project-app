/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgrite.project;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author fotso
 */
public class Main {
     boolean[][] mat = new boolean[17][17];
     void initMat() {
        int p = 0;
        for (boolean[] mat1 : mat) {
            if (p == 7) {
                for (int j = 0; j < mat.length; j++) {
                    mat1[j] = true;

                }
            } else {
                for (int j = 0; j < mat.length; j++) {
                    mat1[j] = false;

                }
            }

            p++;
        }
        mat[0][2] = true;
        mat[0][3] = true;
        mat[0][8] = true;
        mat[0][9] = true;
        mat[0][10] = true;
        mat[0][12] = true;
        mat[1][4] = true;
        mat[1][5] = true;
        mat[2][9] = true;
        mat[3][10] = true;
        mat[3][12] = true;
        mat[6][11] = true;
        mat[6][13] = true;
        mat[6][14] = true;
        mat[6][15] = true;
        mat[6][16] = true;
        mat[7][7] = false;
        mat[11][13] = true;
        mat[11][14] = true;
        mat[12][10] = true;
        mat[16][15] = true;

    }
     
     public void testAncetreValidMethod() {
        List<Integer> A = new ArrayList<>();
        A.add(1);
        A.add(7);
        List<Integer> B = new ArrayList<>();
        B.add(1);
         List<Integer> ancetreValid = Utilitaire.ancetreValid(mat, A);
       Utilitaire.ancetreValid(mat, A).stream().forEach(System.out::println);
       // assertEquals(B, Utilitaire.ancetreValid(mat, A));
    }

    public static void main(String[] args) {
         //Utilitaire u=new Utilitaire();
         Main main = new Main();
         
         main.initMat();
         main.testAncetreValidMethod();
        
    }
    
}
