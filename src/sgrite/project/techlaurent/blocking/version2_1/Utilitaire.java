package sgrite.project.techlaurent.blocking.version2_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import sgrite.project.techlaurent.Base.Tools;
import sgrite.project.techlaurent.NodeWeight;
import static sgrite.project.techlaurent.blocking.Utilitaire.getSonNode;

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
        //bodyCodeLtsCalc(feuillles, memory, mat);
        if (feuillles.size() > 5) {
            // System.out.println("racines : " + new Tools().getRoots(mat));
            bodyCodeLtsCalc(feuillles, memory, mat);
        } else {
            //traitement cas a une feuille
            sgrite.project.techlaurent.blocking.Utilitaire.bodyCodeLtsCalc(feuillles, memory, mat);
        }

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
        Map<Integer, List<Integer>> directParentsLeafs = new HashMap<>();
        Stack stack = initMatriceEtPile(feuillles, memory);
        int nb = 0;

        Integer h = -1;
        int part = (int) feuillles.get(0);
        int f = part;
        feuillles.remove(0);
        while (feuillles.size() >= 0) {

            if (f == part) {
                List<Integer> tmpListParent = new ArrayList();
                if (!directParentsLeafs.containsKey(f)) {
                    tmpListParent = parentNode(mat, f);
                } else {
                    tmpListParent = directParentsLeafs.get((Integer) f);
                }

                if (tmpListParent.size() > 0) {
                    h = tmpListParent.get(0);
                    MaxFilsCarrefour maxFilsCarrefour = new MaxFilsCarrefour();
                    if (!sonsOfAllNode.containsKey(h)) {
                        sonsOfAllNode.put(h, getSonNode(mat, (int) h));
                    }
                    MaxFilsCarrefour notValid = maxFilsCarrefour.isNotValidCarrefour(h, mat, memory, sonsOfAllNode);
                    boolean notValidCarrefour = notValid.status;
                    if (true) {
                        tmpListParent.remove(h);
                        if (memory[(int) h] < memory[f] + 1) {
                            if (!notValidCarrefour) {
                                memory[h] = notValid.maximum + 1;
                                part = h;
                            } else {
                                //traitement pour les cas ou les parents ne corresponde pas initialement
                                while (notValidCarrefour && feuillles.size() > 0) {

                                    while (notValidCarrefour && tmpListParent.size() > 0) {
                                        h = tmpListParent.get(0);
                                        if (!sonsOfAllNode.containsKey(h)) {
                                            sonsOfAllNode.put(h, getSonNode(mat, (int) h));
                                        }
                                        notValid = maxFilsCarrefour.isNotValidCarrefour(h, mat, memory, sonsOfAllNode);
                                        notValidCarrefour = notValid.status;
                                        tmpListParent.remove(h);
                                    }

                                    if (!notValidCarrefour) {//cas on eu un parent de cettte feuille f favorable
                                        memory[h] = notValid.maximum + 1;
                                        part = h;
                                    } else if (tmpListParent.size() == 0) {//cas ou toute les feuilles on ete epuise sans aucun success
                                        //TODO passer a la feuille suivante
                                        if (!feuillles.isEmpty()) {
                                            part = (int) feuillles.get(0);
                                            f = part;
                                            feuillles.remove(0);

                                            if (!directParentsLeafs.containsKey(f)) {
                                                tmpListParent = parentNode(mat, f);
                                            } else {
                                                tmpListParent = directParentsLeafs.get((Integer) f);
                                            }
                                            if (tmpListParent.size() > 0) {
                                                h = tmpListParent.get(0);
                                                if (!sonsOfAllNode.containsKey(h)) {
                                                    sonsOfAllNode.put(h, getSonNode(mat, (int) h));
                                                }
                                                notValid = maxFilsCarrefour.isNotValidCarrefour(h, mat, memory, sonsOfAllNode);
                                                notValidCarrefour = notValid.status;
                                                tmpListParent.remove(h);

                                            }

                                        }

                                    }

                                }

                            }

                        }

                        directParentsLeafs.remove((Integer) f);
                        //tmpListParent.remove(h);
                        if (tmpListParent.size() > 0) {
                            directParentsLeafs.put(f, tmpListParent);
                            feuillles.add(f);
                            
                        }

                    }

                }

            }
            while (stack.size() >= 0) {
                List<Integer> hisParts = parentNode(mat, part);
                MaxFilsCarrefour maxFilsCarrefour = new MaxFilsCarrefour();
                /* traitement de tous le fils adjacents d'un noeud*/
                for (Integer p : hisParts) {
                    //ajout a la map la liste des fils qu'a ce parent s'il n'exixte pas encore dans la map
                    if (!sonsOfAllNode.containsKey(p)) {
                        sonsOfAllNode.put(p, getSonNode(mat, (int) p));
                    }
                    if (memory[(int) p] < memory[part] + 1) {

                        //  MaxFilsCarrefour notValid = maxFilsCarrefour.isNotValidCarrefour(hisPart, mat, memory);
                        MaxFilsCarrefour notValid = maxFilsCarrefour.isNotValidCarrefour(p, mat, memory, sonsOfAllNode);
                        boolean notValidCarrefour = notValid.status;
                        if (!notValidCarrefour) {
                            memory[p] = notValid.maximum + 1;
                            stack.push(p);
                        }
                    }

                }

                if (stack.isEmpty()) {
                    break;
                }
                if (!stack.isEmpty()) {
                    part = (int) stack.pop();
                }

            }

            if (!feuillles.isEmpty()) {
                part = (int) feuillles.get(0);
                f = part;
                feuillles.remove(0);
                
               // System.out.println("f = "+f);
            }
            if (feuillles.isEmpty()) {
                nb++;
            }
            if (nb >= 1) {
                break;
            }

        }

    }

    /**
     * creation pile vide et retour et initialisation memory de toute les
     * feuilles
     *
     * @param feuillles
     * @param memory
     * @return
     */
    public static Stack initMatriceEtPile(List<Integer> feuillles, int[] memory) {
        Stack stack = new Stack();
        feuillles.stream().forEach(e -> {
            memory[(int) e] = 1;
        });
        return stack;
    }

    static Status parcourir(boolean[][] mat, int[] memory, List<Integer> feuillles, Map<Integer, List<Integer>> sonsOfAllNode, Map<Integer, List<Integer>> directParentsLeafs,
            Stack<Integer> stack, Integer hisPart1, Integer hisPart, int part, int f, MaxFilsCarrefour maxFilsCarrefour) {
        Status s = null;
        //ajout a la map la liste des fils qu'a ce parent s'il n'exixte pas encore dans la map
        if (!sonsOfAllNode.containsKey(hisPart1)) {
            sonsOfAllNode.put(hisPart1, getSonNode(mat, (int) hisPart1));
        }
        if (memory[(int) hisPart1] < memory[part] + 1) {

            s = avance1_parcourt(part, hisPart, maxFilsCarrefour, hisPart1, mat, memory, sonsOfAllNode, stack, directParentsLeafs, f, feuillles);
        }
        return s;

    }

    public static Status avance1_parcourt(int part, Integer hisPart, MaxFilsCarrefour maxFilsCarrefour, Integer hisPart1, boolean[][] mat, int[] memory, Map<Integer, List<Integer>> sonsOfAllNode, Stack<Integer> stack, Map<Integer, List<Integer>> directParentsLeafs, int f, List<Integer> feuillles) {

        MaxFilsCarrefour notValid = maxFilsCarrefour.isNotValidCarrefour(hisPart1, mat, memory, sonsOfAllNode);
        boolean notValidCarrefour = notValid.status;
        if (!notValidCarrefour) {
            memory[hisPart1] = notValid.maximum + 1;
            // memory[part] = notValid.maximum + 1;
            stack.push(hisPart1);
        } else {
            //cas progression envisable et mais bloquante
            if (directParentsLeafs.get((Integer) f).size() > 0) {
                feuillles.add((Integer) f);

            } //traitement de la prochaine feuille
            else if (stack.isEmpty() && !feuillles.isEmpty()) {
                // stack.add(feuillles.get(0));
                part = (int) feuillles.get(0);
                f = part;
                if (!directParentsLeafs.containsKey(feuillles.get(0))) {
                    directParentsLeafs.put(feuillles.get(0), parentNode(mat, feuillles.get(0)));
                }
                feuillles.remove(0);
                hisPart = directParentsLeafs.get((Integer) part).get(0);
                directParentsLeafs.get((Integer) part).remove(hisPart);

            } else if (!stack.isEmpty()) {
                part = (int) stack.pop();
            }
        }
        return new Status(part, f);
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
