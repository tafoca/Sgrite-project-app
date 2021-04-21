package sgrite.project.techlaurent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author fotso
 */
class Utilitaire_order {

    public Utilitaire_order() {
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
       // List<Integer> sortNodes = sortNodes(mat, valid);
        return sortNodes(mat, valid);
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

    /**
     * mes parents directs
     *
     * @param mat
     * @param x
     * @param A
     * @return
     */
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
            if (tab[j]) {
                break;
            }
        }
        if (j >= tab.length - 1) {
            b = true;
        }
        return b;
    }

    static List<Integer> feuillles(boolean[][] mat) {
        List<Integer> r = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            if (isALeaf(mat, i)) {
                r.add(i);
            }
        }
        return r;
    }

    static List<Integer> feuilllesSort(boolean[][] mat) {
        List<Integer> r = new ArrayList<>();
        List<NodeWeight> tmp = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            if (isALeaf(mat, i)) {
                tmp.add(new NodeWeight(i, getColumSum(mat, i)));

            }
        }
        inverseOder(tmp, r);
        return r;
    }

    public static void inverseOder(List<NodeWeight> tmp, List<Integer> r) {
        Collections.sort(tmp);
        for (int i = tmp.size() - 1; i >= 0; i--) {
            r.add(tmp.get(i).getNode());
        }

    }

    static List<Integer> sortNodes(boolean[][] mat, List<Integer> src) {
        List<Integer> r = new ArrayList<>();
        List<NodeWeight> tmp = new ArrayList<>();
        src.forEach(e -> {
            tmp.add(new NodeWeight(e, getColumSum(mat, e)));
        });
        inverseOder(tmp, r);
        return r;
    }

    static Integer getColumSum(boolean[][] dataset, int col) {
        int sum = 0;
        for (int i = 0; i < dataset.length; i++) {
            if (dataset[i][col]) {
                sum += 1;
            }
        }

        return sum;
    }

    static Object genTabCouple(boolean[][] mat, List<Integer> A, int x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static void computeLTS(boolean[][] mat, int[] memory) {
        List<Integer> feuillles = feuillles(mat);
        bodyCodeLtsCalc(feuillles, memory, mat);

    }

    public static void bodyCodeLtsCalc(List<Integer> feuillles, int[] memory, boolean[][] mat) {
        // System.out.println("feuilles : " + feuillles);
        Stack stack = new Stack();
        stack.addAll(feuillles);

        stack.stream().forEach(e -> {
            memory[(int) e] = 1;
        });
        int part = (int) stack.pop();

        while (stack.size() > 0) {
            List<Integer> hisParts = parentNode(mat, part);
            //add list valid ancerter/parent
            for (int i = 0; i < hisParts.size(); i++) {
                int a = memory[part];
                int b = memory[(int) hisParts.get(i)];
                if (memory[(int) hisParts.get(i)] < memory[part] + 1) {
                    memory[(int) hisParts.get(i)] = memory[part] + 1;
                    stack.push(hisParts.get(i));
                }

            }
//            hisParts.forEach(e->{
//
//            
//            });
//            hisParts.stream().filter(e->memory[part] < memory[e] +1).forEach(p->{
//                memory[part] = memory[p] +1;
//                stack.push(p);
//            });
            part = (int) stack.pop();

        }
    }

    /**
     * calcul ensemble trier au preallable
     *
     * @param mat
     * @param memory
     */
    static void computeLTSForSortLeafs(boolean[][] mat, int[] memory) {
        List<Integer> feuillles = feuilllesSort(mat);
        bodyCodeLtsCalc(feuillles, memory, mat);

    }

    static int maxElementMemory(int[] memory) {
        int max = memory[0];
        for (int i = 1; i < memory.length; i++) {
            if (memory[i] > max) {
                max = memory[i];
            }
        }
//        System.out.print("The maximum value is : ");
//
//        System.out.print("->   " + max);
        //new Utilitaire().affichage(memory);
        return max;
    }

    static int computeSupport(boolean[][] mat, int[] memory) {
        computeLTS(mat, memory);
        return maxElementMemory(memory);
    }

    static int computeSortLeafSupport(boolean[][] mat, int[] memory) {
        computeLTSForSortLeafs(mat, memory);
        return maxElementMemory(memory);
    }

    void affichage(boolean[][] m) {
        for (boolean[] m1 : m) {
            for (boolean n : m1) {
                int a = (n) ? 1 : 0;
                System.out.print(" " + a);

            }
            System.out.println();
        }
    }

    void affichage(int m[]) {
        for (int n : m) {
            System.out.print(" " + n);
        }
        System.out.println();
    }

}
