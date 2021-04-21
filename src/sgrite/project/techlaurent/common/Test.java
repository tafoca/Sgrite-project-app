package sgrite.project.techlaurent.common;

import java.util.List;
import java.util.Map;
import sgrite.project.techlaurent.Base.Grite;
import sgrite.project.techlaurent.Base.Tools;

/**
 *
 * @author fotso
 */
public class Test {

    public static void main(String[] args) {
        float data[] = new float[9];
        dataItemIniit(data);

        boolean[][] adjacence = new boolean[9][9];
        boolean[][] adjacence2 = new boolean[14][14];
        Grite.affiche(adjacence2);
        initbinarymarix(adjacence2);
        System.err.println("----------------------------------------");
        Grite.affiche(adjacence2);
        Tools tools = new Tools();
        tools.setSizeMat(adjacence2.length);
        tools.initMemory();
        int sizeMaxWay = tools.sizeMaxWay(adjacence2, 0);
        
        tools.initMemory();
        int taille = tools.sizeMaxWayIterative(adjacence2, 0);
        // CommonMethod commonMethod = new CommonMethod();
        //  commonMethod.creationMatriceBinaireAdjacence(9, adjacence, data);

    }

    public static void initbinarymarix(boolean[][] adjacence2) {
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

    public static void dataItemIniit(float[] data) {
        data[0] = 10;
        data[1] = 12;
        data[2] = 0;
        data[3] = 1;
        data[4] = 6;
        data[5] = 7;
        data[6] = 4;
        data[7] = 5;
        data[8] = 0;
    }

}
