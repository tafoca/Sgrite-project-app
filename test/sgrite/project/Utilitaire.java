package sgrite.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
/**
 *
 * @author fotso
 */
class Utilitaire {

    public Utilitaire() {
    }

    /**
     * les ancetre d'un noeud
     *
     * @param mat
     * @param node
     * @return
     */
    static List<Integer> ancetre(boolean[][] mat, int node) {
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            if (mat[i][node]) {
                l.add(i);
            }
        }
        return l;
    }

    /**
     * ancetre qui sont les panents
     *
     * @param mat
     * @param A
     * @return
     */
    static List<Integer> ancetreValid(boolean[][] mat, List<Integer> inits) {
        List<Integer> valid = new ArrayList<>();

        inits.forEach(e -> {
            findIsparentElt(inits, mat, e, valid);
        });

        /*for (int i = 0; i < inits.size(); i++) {
         findIsParentNode(inits, i, mat, valid);
         }*/
        return valid;
    }

    public static void findIsparentElt(List<Integer> inits, boolean[][] mat, Integer e, List<Integer> valid) {
        int k = 0;
        boolean corespond = false;
        for (int j = 0; j < inits.size(); j++) {
            int cible = inits.get(j);
            boolean b = mat[e][cible];
            k = j;
            if (b) {
                corespond = true;
                break;
            }
        }
        if (k == inits.size() - 1 && !corespond) {
            valid.add(e);
        }
    }

    public static void findIsParentNode(List<Integer> inits, int i, boolean[][] mat, List<Integer> valid) {
        int e = inits.get(i);
        int k = 0;
        boolean corespond = false;
        for (int j = 0; j < inits.size(); j++) {
            int cible = inits.get(j);
            boolean b = mat[e][cible];
            k = j;
            if (b) {
                corespond = true;
                break;
            }
        }
        if (k == inits.size() - 1 && !corespond) {
            valid.add(inits.get(i));
        }
    }

    static boolean isAncetre(boolean[][] mat, List<Integer> A, int o) {
        return A.contains(o);
    }

    static int parentNode(boolean[][] mat, int x, List<Integer> A) {
        return 0;
        // List<Integer> ancetreValid = ancetreValid(mat,ancetre(mat,x));
    }

    static List<Integer> parentNode(boolean[][] mat, int x) {
        return ancetreValid(mat, ancetre(mat, x));
    }

    /**
     * verifie si un noeud est une feuille
     */
    static boolean isALeaf(boolean[][] mat, int i) {
        boolean b = false;
        boolean[] tab = mat[i];
        int j = 0;
        for (; j < tab.length; j++) {
            if (tab[i]) {
                break;
            }
        }
        if (j >= tab.length) {
            b = true;
        }
        return b;
    }

    static List<Integer> feuillles(boolean[][] mat) {
        List<Integer> r =new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            if (isALeaf(mat, i)) {
                r.add(i);
            }
            
        }
        return r;
    }

    static Object genTabCouple(boolean[][] mat, List<Integer> A, int x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     static void computeLTS(boolean[][] mat,int memory[]) {
        List<Integer> feuillles = feuillles(mat);
       // System.out.println("feuilles : " + feuillles);
        Stack stack = new Stack();
        stack.addAll(feuillles);
        stack.stream().forEach(e->  {
            memory[(int)e] = 1;
        });
        System.out.println("stack : " + stack);
        System.out.println("elt 1: " + stack.pop());  
        System.out.println("stack : " + stack);
         System.out.println("elt 1: " + stack.pop()); 
        System.out.println("stack : " + stack); //[4, 5, 8, 9, 10, 13]
        stack.push(20);//[4, 5, 8, 9, 10, 13, 20]
        stack.addAll(stack.size(), Arrays.asList(21,22,23,24,25)); // -->  [4, 5, 8, 9, 10, 13, 20, 21, 22, 23, 24, 25]
        System.out.println("stack : " + stack);// [4, 5, 8, 9, 10, 13, 20, 21, 22, 23, 24, 25]
        stack.addAll(1, Arrays.asList(-1,-2,-3,-4,-5));//[4, -1, -2, -3, -4, -5, 5, 8, 9, 10, 13, 20, 21, 22, 23, 24, 25]
        System.out.println("stack : " + stack);
    }

}
