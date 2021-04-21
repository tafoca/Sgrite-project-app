package sgrite.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

//import sgriteproject.SGrite.Grite;
/**
 *
 * @author tabueu
 * @version 1.0
 * @since 11/04/2019 21:02
 */
public class UtilitaireTest {

    boolean[][] mat = new boolean[17][17];

    ;
    public UtilitaireTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

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

    @Before
    public void setUp() {
        initMat();
    }

    @After
    public void tearDown() {
    }

    /**
     * test method get ancestor
     */
    @Test
    public void testreturnAncetreMethod() {
        List<Integer> A = new ArrayList<>();
        A.add(1);
        A.add(7);
        int o = 4;
        assertEquals(A, Utilitaire.ancetre(mat, o));
    }

    @Test
    public void testreturnAncetreMethod2() {
        List<Integer> A = new ArrayList<>();
        A.add(6);
        A.add(7);
        A.add(11);
        int o = 13;
      //  Utilitaire.ancetre(mat, o).stream().forEach(System.out::println);
        assertEquals(A, Utilitaire.ancetre(mat, o));

    }

    @Test
    public void testreturnAncetreValidNulListMethod() {
        List<Integer> A = new ArrayList<>();
        // A.add(1);
        // A.add(7);
        List<Integer> B = new ArrayList<>();
        //nnnB.add(1);
        assertEquals(B, Utilitaire.ancetreValid(mat, A));
    }

    @Test
    public void testIsAncetreMethod() {
        List<Integer> A = new ArrayList<>();
        A.add(1);
        A.add(7);
        int o = 7;
        assertEquals(true, Utilitaire.isAncetre(mat, A, o));
    }

    @Test
    public void testAncetreValidMethod() {
        List<Integer> A = new ArrayList<>();
        A.add(1);
        A.add(7);
        List<Integer> B = new ArrayList<>();
        B.add(1);
       // Utilitaire.ancetreValid(mat, A).stream().forEach(System.out::println);
        assertEquals(B, Utilitaire.ancetreValid(mat, A));
    }
    
    @Test
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

    //parentDirect
    @Test
    public void testParentNode() {
        List<Integer> A = new ArrayList<>();
        A.add(6);
        A.add(7);
        A.add(11);
        int x = 13;
        int p = 11;
        //Utilitaire.parentNode(mat, x, A)
        assertEquals(p, (int)Utilitaire.parentNode(mat, x).get(0));
    }
    //parentDirect

    @Test
    public void testParentNode2() {
        List<Integer> A = new ArrayList<>();
        A.add(0);
        A.add(3);
        A.add(12);
        A.add(7);
        int x = 10;
        int p = 12;
        assertEquals(p, Utilitaire.parentNode(mat, x, A));
    }

    //generate Table of Couple.
    @Test
    public void testGenTabCouple() {
        List<Integer> A = new ArrayList<>();
        /*A.add(0);
         A.add(7);
         A.add(7);*/

        List<Integer[]> B = new ArrayList<>();
        int x = 10;
        A = Utilitaire.ancetre(mat, 10);
        // assertEquals(5, Utilitaire.genTabCouple(mat, A, x).size());
    }

    @Test
    public void testLeaf() {
        assertEquals(true, Utilitaire.isALeaf(mat, 10));
    }

    @Test
    public void testLeafsAll() {
        // Grite.affiche(mat);
        Integer[] t = {4, 5, 8, 9, 10, 13, 14, 15};
        List<Integer> list = new ArrayList<>();
        list.addAll(Arrays.asList(t));
        assertEquals(list, Utilitaire.feuillles(mat));
    }

}
