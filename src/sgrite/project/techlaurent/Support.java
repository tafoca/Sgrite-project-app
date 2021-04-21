package sgrite.project.techlaurent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import static sgrite.project.techlaurent.Base.Grite.affiche;
import sgrite.project.techlaurent.Base.Tools;
import sgrite.project.techlaurent.export.AllMatrix;

/**
 *
 * @author fotso
 */
public class Support {

    public AllMatrix allMatrix;
    public calculReasultatImpl reasultatImpl;
    public String sourceMatricefile = "series_level";
    public String outSupport1 = "outSupport1_";
    public String outSupport2 = "outSupport2_";

    public Support(int i) {
        initValue(sourceMatricefile + i);
        setOutSupport1(outSupport1 + i);
        setOutSupport2(outSupport2 + i);
    }

    public AllMatrix getAllMatrix() {
        return allMatrix;
    }

    public void setAllMatrix(AllMatrix allMatrix) {
        this.allMatrix = allMatrix;
    }

    public calculReasultatImpl getReasultatImpl() {
        return reasultatImpl;
    }

    public void setReasultatImpl(calculReasultatImpl reasultatImpl) {
        this.reasultatImpl = reasultatImpl;
    }

    public Support(calculReasultatImpl reasultatImpl) {
        this.reasultatImpl = reasultatImpl;
    }

    public String getSourceMatricefile() {
        return sourceMatricefile;
    }

    public String getOutSupport1() {
        return outSupport1;
    }

    public String getOutSupport2() {
        return outSupport2;
    }

    public void setSourceMatricefile(String sourceMatricefile) {
        this.sourceMatricefile = sourceMatricefile;
    }

    public void setOutSupport1(String outSupport1) {
        this.outSupport1 = outSupport1;
    }

    public void setOutSupport2(String outSupport2) {
        this.outSupport2 = outSupport2;
    }

    public static void main(String[] args) {

    }

    //partie fichier source chager les elements dans allmatrix
    private void initValue(String sourceMatricefile) {
        allMatrix = lireAfficherlistMatrix(sourceMatricefile);// "series_level" + 1
        System.out.println("********************************************************************************************************");
        boolean[][] get = allMatrix.getAllContengent().get(6);//0,1,2,3,4,5,6

        affiche(get);

        //System.err.println("---------------tailles de l'ensembles des matrix de test : -> " + allMatrix.getAllContengent().size());
        System.out.println("********************************************************************************************************");
        System.err.println("roots: " + new Tools().getRoots(get));
        System.err.println("leafs: " + Utilitaire.feuillles(get));
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++[" + get.length + "]");

    }

    public AllMatrix lireAfficherlistMatrix(String leFichier) {
        // Lire l'objet teA (conservé précédemment) à l'aide de la sérialisation.
        ObjectInputStream ois = null;
        AllMatrix teA = null;
        //= "Serie.dat";
        System.out.println("Lecture par sérialisation: DEBUT");
        try {

            ois = new ObjectInputStream(new FileInputStream(leFichier));
        } catch (StreamCorruptedException sce) {
            System.out.println("Le fichier est corrompu.");
            System.exit(3);
        } catch (IOException ioe) {
            System.out.println("Erreur d'entrée-sortie");
            System.exit(2);
        }
        try {
            teA = (AllMatrix) ois.readObject();
            ois.close();

        } catch (ClassNotFoundException ioe) {
            System.out.println("Nom de fichier inexistant.");
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Erreur d'entrée-sortie");
            System.exit(2);
        }
        System.out.println("Lecture par sérialisation: FIN");
        // teA.afficherTous("Après l'écriture et la lecture binaires");

        return teA;
    }

    //**************** Ecriture une ligne du fichier **********************************
    public void wrtiteStatistic(int sup, double duree, FileWriter fw) throws IOException {

        try {
            String sep = "       ";
            fw.write(sup + sep + (duree) + "\n");//en ms
            fw.flush();

        } finally {
        }
    }

    //executeur
    public void exec(FileWriter fw2, double duree, int sup) {

        try {
            wrtiteStatistic(sup, (duree / 1000.0), fw2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //**************************    enregistrer toute les lignes      ******************************
    /**
     *
     * @param outputfile1 sortie resultat algo 1 [filename]
     * @param outputfile2 sortie resultat algo 2 [filename]
     * @throws IOException
     */
    public void dataForDrawGraphe(String outputfile1, String outputfile2) throws IOException {
        FileWriter fw1 = new FileWriter(new File(outputfile1));
        FileWriter fw2 = new FileWriter(new File(outputfile2));
        entete(fw1);
        entete(fw2);

        ArrayList<boolean[][]> allContengent = allMatrix.getAllContengent();

        for (boolean[][] mat : allContengent) {

            calculReasultatImpl reasultat = new calculReasultatImpl(new Tools());
            int memory[] = new int[mat.length];
            reasultat.initMemory(memory);
            Resultat compareTimealg1 = reasultat.compareTimealg1(mat, memory);
            Resultat compareTimealg2 = reasultat.compareTimealg2(mat, memory);
            exec(fw1, compareTimealg1.duree, compareTimealg1.support); // 0 ==duree
            exec(fw2, compareTimealg2.duree, compareTimealg2.support); // 0 ==duree
        }
        fw1.close();
        fw2.close();

    }

    public void dataForDrawGraphe1(String outputfile1) throws IOException {
        FileWriter fw1 = new FileWriter(new File(outputfile1));
        entete(fw1);

        ArrayList<boolean[][]> allContengent = allMatrix.getAllContengent();

        allContengent.stream().map((boolean[][] mat) -> {
            calculReasultatImpl reasultat = new calculReasultatImpl(new Tools());
            int memory[] = new int[mat.length];
            reasultat.initMemory(memory);
            Resultat compareTimealg1 = null;
            //cas one leaf

            if (Utilitaire.feuillles(mat).size() == 1) {
                compareTimealg1 = reasultat.compareTimealg2(mat, memory);
                //compareTimealg1 = reasultat.compareTimealg1_graak(mat, memory);
            } else {
                compareTimealg1 = reasultat.compareTimealg1(mat, memory);
            }
            //compareTimealg1 = reasultat.compareTimealg1(mat, memory);

            return compareTimealg1;
        }).forEach((compareTimealg1) -> {
            exec(fw1, compareTimealg1.duree, compareTimealg1.support); // 0 ==duree
        });
        fw1.close();
    }

    public void dataForDrawGraphe_trieLeaf1(String outputfile1) throws IOException {
        FileWriter fw1 = new FileWriter(new File(outputfile1));
        entete(fw1);

        ArrayList<boolean[][]> allContengent = allMatrix.getAllContengent();

        allContengent.stream().map((boolean[][] mat) -> {
            calculReasultatImpl reasultat = new calculReasultatImpl();//new Tools()
            int memory[] = new int[mat.length];
            reasultat.initMemory(memory);
            Resultat compareTimealg1 = null;
            //cas one leaf

            /*if (Utilitaire.feuillles(mat).size() == 1) {
             //compareTimealg1 = reasultat.compareTimealg2(mat, memory);
             compareTimealg1 = reasultat.compareTimealg1_graak(mat, memory);
             } else {
             compareTimealg1 = reasultat.compareTimealg1_trie(mat, memory);
             }*/
            compareTimealg1 = reasultat.compareTimealg1_trie(mat, memory);

            return compareTimealg1;
        }).forEach((compareTimealg1) -> {
            exec(fw1, compareTimealg1.duree, compareTimealg1.support); // 0 ==duree
        });
        fw1.close();
    }

    public void dataForDrawGraphe2(String outputfile2) throws IOException {
        FileWriter fw2 = new FileWriter(new File(outputfile2));
        entete(fw2);

        ArrayList<boolean[][]> allContengent = allMatrix.getAllContengent();

        allContengent.stream().map((boolean[][] mat) -> {
            calculReasultatImpl reasultat = new calculReasultatImpl(new Tools());
            int memory[] = new int[mat.length];
            reasultat.initMemory(memory);
            Resultat compareTimealg2 = reasultat.compareTimealg2(mat, memory);
            return compareTimealg2;
        }).forEach((compareTimealg2) -> {
            exec(fw2, compareTimealg2.duree, compareTimealg2.support); // 0 ==duree
        });
        fw2.close();

    }

    public void entete(FileWriter fw1) throws IOException {
        fw1.write("support " + "     " + "Duree " + "\n");
        fw1.flush();
        fw1.write("\n");
        fw1.flush();
    }

}
