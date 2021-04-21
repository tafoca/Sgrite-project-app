package sgrite.project.techlaurent.blocking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import sgrite.project.techlaurent.NodeWeight;

/**
 *
 * @author fotso
 */
public class Utilitaire {

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

    public static float supportCalculation(int nb, int total) {
        return (float) nb / total;
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

    /**
     * verifier si o est un ancetre
     *
     * @param mat
     * @param A
     * @param o
     * @return
     */
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
        Collections.sort(tmp);
        for (int i = tmp.size() - 1; i >= 0; i--) {
            r.add(tmp.get(i).getNode());
        }
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
        //bodyCodeLtsCalcAlterner(feuillles, memory, mat);

    }

    /**
     *
     * @param boolMatrix
     * @param item variation between 0 to nbItems -1
     * @return
     */
    public static ArrayList<Integer> getSonNode(boolean[][] boolMatrix, int item) {
        ArrayList<Integer> sons = new ArrayList<>();
        for (int i = 0; i < boolMatrix.length; i++) {
            if (boolMatrix[item][i] == true) {
                sons.add(i);
            }
        }
        return sons;
    }

    public static void bodyCodeLtsCalc(List<Integer> feuillles, int[] memory, boolean[][] mat) {
        //map dynamic to build : node -> his sons 
        Map<Integer, List<Integer>> sonsOfAllNode = new HashMap<>();

        Stack stack = new Stack();
        // stack.addAll(feuillles);
        feuillles.stream().forEach(e -> {
            memory[(int) e] = 1;
        });

        //stack.add(feuillles.get(0));
        if (!feuillles.isEmpty()) {
            int part = (int) feuillles.get(0);
            feuillles.remove(0);

            //  if (!stack.isEmpty()) {
            //int part = (int) stack.pop();
            while (stack.size() >= 0) {
                //List<Integer> hisParts = parentNode(mat, part);
                //TODO plus tard remplacer par tous les ancetres
                List<Integer> hisParts = ancetre(mat, part);
                //add list valid ancerter/parent

                /* traitement de tous le fils adjacents d'un noeud*/
                for (Integer hisPart : hisParts) {
                    //TODO suppression des parents avec la liste des fils vide

                    //ajout a la map la liste des fils qu'a ce parent s'il n'exixte pas encore dans la map
                    if (!sonsOfAllNode.containsKey(hisPart)) {
                        sonsOfAllNode.put(hisPart, getSonNode(mat, (int) hisPart));
                    }
                    if (memory[(int) hisPart] < memory[part] + 1) {

                        MaxFilsCarrefour maxFilsCarrefour = new MaxFilsCarrefour();
                        //  MaxFilsCarrefour notValid = maxFilsCarrefour.isNotValidCarrefour(hisPart, mat, memory);
                        MaxFilsCarrefour notValid = maxFilsCarrefour.isNotValidCarrefour(hisPart, mat, memory, sonsOfAllNode);
                        //MaxFilsCarrefour notValid = maxFilsCarrefour.isNotValidCarrefour(hisPart, mat, memory, sonsOfAllNode.get(hisPart));
                        boolean notValidCarrefour = notValid.status;
                        if (!notValidCarrefour) {
                            memory[hisPart] = notValid.maximum + 1;
                            // memory[part] = notValid.maximum + 1;
                            stack.push(hisPart);
                        }
                    }

                }

                if (stack.isEmpty() && feuillles.isEmpty()) {
                    break;
                }
                //traitement de la prochaine feuille
                if (stack.isEmpty() && !feuillles.isEmpty()) {
                    // stack.add(feuillles.get(0));
                    part = (int) feuillles.get(0);
                    feuillles.remove(0);
                }
                if (!stack.isEmpty()) {
                    part = (int) stack.pop();
                }

            }
        }

        //}
    }

    /**
     * Calcul nouveau de long chemin 19/11/19 (parcourt a tour de role)
     */
    public static void bodyCodeLtsCalcAlterner(List<Integer> feuillles, int[] memory, boolean[][] mat) {
        //map dynamic to build : node -> his sons 
        Map<Integer, List<Integer>> sonsOfAllNode = new HashMap<>();

        Stack stack = new Stack();
        // stack.addAll(feuillles);
        feuillles.stream().forEach(e -> {
            memory[(int) e] = 1;
        });

        //stack.add(feuillles.get(0));
        if (!feuillles.isEmpty()) {
            int part = (int) feuillles.get(0);
           // int oldleaf = part;
            feuillles.remove(0);

            //  if (!stack.isEmpty()) {
            //int part = (int) stack.pop();
            while (stack.size() >= 0) {
                //List<Integer> hisParts = parentNode(mat, part);
                //TODO plus tard remplacer par tous les ancetres
                List<Integer> hisParts = ancetre(mat, part);
                //add list valid ancerter/parent

                /* traitement de tous le fils adjacents d'un noeud*/
                for (Integer hisPart : hisParts) {
                    //TODO suppression des parents avec la liste des fils vide

                    if (memory[(int) hisPart] < memory[part] + 1) {
                        //ajout a la map la liste des fils qu'a ce parent s'il n'exixte pas encore dans la map
                        if (!sonsOfAllNode.containsKey(hisPart)) {
                            sonsOfAllNode.put(hisPart, getSonNode(mat, (int) hisPart));
                        }
                        
                        MaxFilsCarrefour maxFilsCarrefour = new MaxFilsCarrefour();
                        boolean notValidCarrefour = maxFilsCarrefour.isNotValidCarrefourre(hisPart, mat, memory, sonsOfAllNode);
                        memory[hisPart] = memory[part] + 1;
                        if (!notValidCarrefour) {
                            stack.push(hisPart);
                        } else {
                            //cas par carreffour franchissable
                            //if (!feuillles.contains((Integer) oldleaf)) {
                               // feuillles.add(oldleaf);
                          //  }

                        }
                    } 

                }

                if (stack.isEmpty() && feuillles.isEmpty()) {
                    break;
                }
                //traitement de la prochaine feuille
                if (stack.isEmpty() && !feuillles.isEmpty()) {
                    // stack.add(feuillles.get(0));
                    part = (int) feuillles.get(0);
                   // oldleaf = part;
                    feuillles.remove(0);
                }
                if (!stack.isEmpty()) {
                    part = (int) stack.pop();
                }

            }
        }

        //}
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
        int max = 0;
        if (memory.length > 0) {
            max = memory[0];
            for (int i = 1; i < memory.length; i++) {
                if (memory[i] > max) {
                    max = memory[i];
                }
            }
//        System.out.print("The maximum value is : ");
//
//        System.out.print("->   " + max);
            //new Utilitaire().affichage(memory);
        }

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
