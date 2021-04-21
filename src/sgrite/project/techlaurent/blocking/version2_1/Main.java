package sgrite.project.techlaurent.blocking.version2_1;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import sgrite.project.techlaurent.Base.Tools;

/**
 *
 * @author fotso
 */
public class Main {

    boolean[][] mat = new boolean[17][17];
    int memory[] = new int[17];
    boolean[][] adjacence2 = new boolean[14][14];
    static int memory2[] = new int[14];

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

    void initMemory() {
        for (int i = 0; i < memory.length; i++) {
            memory[i] = -1;
        }
    }

    static void initMemory(int taille) {
        for (int i = 0; i < taille; i++) {
            memory2[i] = -1;
        }
    }

    public static boolean[][] initbinarymarix2(boolean[][] adjacence2) {
        initadjacenceMatrix(adjacence2);
        adjacence2[0][3] = true;
        adjacence2[0][4] = true;
        adjacence2[0][5] = true;
        adjacence2[0][6] = true;
        adjacence2[0][7] = true;
        adjacence2[0][8] = true;
        adjacence2[0][10] = true;
        adjacence2[0][11] = true;
        adjacence2[0][13] = true;

        //1->?
        adjacence2[1][4] = true;
        adjacence2[1][5] = true;
        adjacence2[1][6] = true;
        adjacence2[1][7] = true;
        adjacence2[1][8] = true;
        adjacence2[1][10] = true;
        adjacence2[1][11] = true;
        adjacence2[1][13] = true;

        //2->?
        adjacence2[2][4] = true;
        adjacence2[2][5] = true;
        adjacence2[2][6] = true;
        adjacence2[2][7] = true;
        adjacence2[2][8] = true;
        adjacence2[2][10] = true;
        adjacence2[2][11] = true;
        adjacence2[2][13] = true;

        //3->?
        adjacence2[3][6] = true;
        adjacence2[3][7] = true;
        adjacence2[3][8] = true;
        adjacence2[3][10] = true;
        adjacence2[3][11] = true;
        adjacence2[3][13] = true;

        adjacence2[5][7] = true;
        adjacence2[5][8] = true;

        adjacence2[6][8] = true;
        adjacence2[6][11] = true;
        adjacence2[6][13] = true;

        adjacence2[9][10] = true;
        adjacence2[9][11] = true;
        adjacence2[9][13] = true;

        adjacence2[12][11] = true;
        adjacence2[12][13] = true;
        initMemory(adjacence2.length);
        return adjacence2;
    }

    public static void initadjacenceMatrix(boolean[][] adjacence2) {
        //init adjacence 2
        adjacence2[0][1] = true;
        adjacence2[0][2] = true;
        adjacence2[1][3] = true;
        adjacence2[2][3] = true;
        adjacence2[3][4] = true;
        adjacence2[3][5] = true;
        adjacence2[4][8] = true;
        adjacence2[5][6] = true;
        adjacence2[6][7] = true;
        adjacence2[6][10] = true;
        adjacence2[7][8] = true;
        adjacence2[9][6] = true;
        adjacence2[12][10] = true;
        adjacence2[10][11] = true;
        adjacence2[10][13] = true;
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

    public void testAncetreValidMethod2() {
        List<Integer> A = new ArrayList<>();
        A.add(6);
        A.add(7);
        A.add(11);
        List<Integer> ancetreValid = Utilitaire.ancetreValid(mat, A);
        //List<Integer> B = Utilitaire.ancetreValid(mat, A).stream().collect(Collectors.toList());
        List<Integer> B = new ArrayList<>();
        B.add(11);
        int o = 13;
        Utilitaire.ancetreValid(mat, A).stream().forEach(System.out::println);
        assertEquals(B, ancetreValid);

    }

    public void testParentNode() {
        List<Integer> A = new ArrayList<>();
        A.add(6);
        A.add(7);
        A.add(11);
        int x = 13;
        int p = 11;
        List<Integer> parentNode = Utilitaire.parentNode(mat, x);
        assertEquals(parentNode, Utilitaire.parentNode(mat, x));
    }

    //parentDirect
    public void testParentNode2() {
        List<Integer> A = new ArrayList<>();
        A.add(0);
        A.add(3);
        A.add(12);
        A.add(7);
        int x = 10;
        int p = 12;
        List<Integer> parentNode = Utilitaire.parentNode(mat, x);
        System.out.println("  " + parentNode);
        assertEquals(parentNode, Utilitaire.parentNode(mat, x));
    }

    //    static List<Integer> feuilllesSort(boolean[][] mat) {
    public void testLeafsAllSort() {

    }

    public void testLeafsAll() {
        long start1 = System.currentTimeMillis();
        List<Integer> feuillles = Utilitaire.feuillles(mat);
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        List<Integer> feuilllesSort = Utilitaire.feuilllesSort(mat);
        long end2 = System.currentTimeMillis();

        long diff1 = end1 - start1;
        long diff2 = end2 - start2;

        afficheRes(diff1, 1);
        afficheRes(diff2, 2);
        System.err.println("1. " + feuillles);
        System.err.println("2. " + feuilllesSort);
    }

    public static void main(String[] args) {
        Utilitaire u = new Utilitaire();
        Main main = new Main();
        // u.affichage(main.mat);
        System.out.println("----");
        initMatMemory(main);
      //  u.affichage(main.mat);
        /*main.testAncetreValidMethod();
         main.testAncetreValidMethod2();
         //test parent:
         main.testParentNode();
         main.testParentNode2();
         Utilitaire.computeLTS(main.mat, main.memory);

         compareTime(main);*/
        //main.testLeafsAll();
        //compareTimeBotomUpAndUpBotom(main);
        //Integer[] t1 = new Integer[10];
        //int[] t2 = new int[10];
        // System.err.println("taille tab de 10 integer: "+);
        boolean[][] adjacence2 = new boolean[14][14];
        boolean[][] initbinarymarix2 = initbinarymarix2(adjacence2);
        int computeSupport = Utilitaire.computeSupport(main.mat, main.memory);
        //  affiche(initbinarymarix2);
        int computeSupport1 = Utilitaire.computeSupport(initbinarymarix2, main.memory2);

    }

    public static void affiche(boolean[][] tab) {
        for (boolean[] d : tab) {
            System.out.println();
            for (boolean v : d) {
                System.out.print(" " + (v ? 1 : 0) + " ");
            }
        }
    }

    public static void compareTime(Main main) {

        initMatMemory(main);

        long start1 = System.currentTimeMillis();
        int max1 = Utilitaire.computeSupport(main.mat, main.memory);
        long end1 = System.currentTimeMillis();

        Tools tools = new Tools();
        tools.setSizeMat(main.mat.length);
        tools.initMemory();

        long start2 = System.currentTimeMillis();
        int max2 = tools.maximumSupport(main.mat, tools.getMemory());
        long end2 = System.currentTimeMillis();

        long diff1 = end1 - start1;
        long diff2 = end2 - start2;

        afficheRes(diff1, 1);
        afficheRes(diff2, 2);
    }

    // 10-09-19  test bottom up computation support
    public static void compareTimeBotomUpAndUpBotom(Main main) {

        initMatMemory(main);

        long start1 = System.currentTimeMillis();
        int max1 = Utilitaire.computeSupport(main.mat, main.memory);
        long end1 = System.currentTimeMillis();

        initMatMemory(main);

        /* long start2 = System.currentTimeMillis();
         int max2 = UtilTopBottomAppr.computeSupport(main.mat, main.memory);
         long end2 = System.currentTimeMillis();*/
        long diff1 = end1 - start1;
        // long diff2 = end2 - start2;

        afficheRes(diff1, 1);
        System.err.println("le max vaut : " + max1);
        /*afficheRes(diff2, 2);
         System.err.println("la difference de temps entre Method 1 et methode 2: "+(diff1-diff2));*/

    }

    public static void afficheRes(long diff1, int i) {
        System.out.println("------------------------------------------");
        System.out.println("difference " + i + " : " + diff1);
        System.out.println("------------------------------------------");
    }

    public static void initMatMemory(Main main) {
        main.initMat();
        main.initMemory();
    }

    //autre exemple de cas de graphique de test
    public static void initbinarymarix(boolean[][] adjacence2) {
        //init adjacence 2
        adjacence2[0][1] = true;
        adjacence2[0][2] = true;
        adjacence2[1][3] = true;
        adjacence2[2][3] = true;
        adjacence2[3][4] = true;
        adjacence2[3][5] = true;
        adjacence2[4][8] = true;
        adjacence2[5][6] = true;
        adjacence2[6][7] = true;
        adjacence2[6][10] = true;
        adjacence2[7][8] = true;
        adjacence2[9][6] = true;
        adjacence2[12][10] = true;
        adjacence2[10][11] = true;
        adjacence2[10][13] = true;

    }

}
