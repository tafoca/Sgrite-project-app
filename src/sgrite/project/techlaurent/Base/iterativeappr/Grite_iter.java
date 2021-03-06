package sgrite.project.techlaurent.Base.iterativeappr;

import sgrite.project.techlaurent.Base.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import sgrite.project.techlaurent.common.CommonMethod;
import sgrite.project.techlaurent.export.AllMatrix;
import sgrite.project.techlaurent.export.BoolMatrix;

/**
 *
 * @author The class encapsulates and optimisation of implementation of the
 * Grite algorithm to compute Gradual frequent itemsets .
 * @author tabueu Fotso laurent, University of DSCHANG, 2017
 * @copyright GNU General Public License v3 No reproduction in whole or part
 * without maintaining this copyright notice and imposing this condition on any
 * subsequent users.
 *
 */
public class Grite_iter extends CommonMethod {

    private Tools myTools;
    /*
     * private static int nbitems =10;//3; private int nbtransaction = 100;//9;
     */
    private static int nbitems = 0;
    private int nbtransaction = 0;
    private double threshold = (float) 0.05; //0.1  0.4 default value
    /**
     * the list of current itemsets
     */
    public ArrayList<float[]> itemsets = new ArrayList<>();
    // static Hashtable<String, int[][]> allContengent = new Hashtable<>();
    /**
     * the name of the transcation file
     */
    private String transaFile;
    ArrayList<boolean[][]> allContengent = new ArrayList<>();
    ArrayList<boolean[][]> computeAllContengent = new ArrayList<>();
    ArrayList<ArrayList<Integer>> isolated_matixs = new ArrayList<>();
    ArrayList<Integer> tmpListIsolatedObj = new ArrayList<>();// contains any
    // moment list
    // current list
    private SolutionMap GrdItem;
    float[][] dataset;
    float[] item;
    /*
     * private static int taille = 100;// 9;
     */
    private static int taille = 0;
    int a = 0;
    private String transafile = AppConstants.TRANSACTIONFILE; //I4408-100.dat I4408-1-55-100.dat"transa.dat";//transa1.dat
    // "test.dat";//"I4408.dat" // "I4408-1-55.dat" // "I4408-56-109.dat" ;//
    // default transaction file"
    private static String[] attrList;
    private static ArrayList<String[]> semantique = new ArrayList<>();

    ArrayList<Integer> removedindex;
    private int niveau = 0;
    private int numberPatterns = 0;
    private String outputfile = "out_GNBI.dat";
   double min = AppConstants.MIN;
    double max = AppConstants.MAX;
    double pas = AppConstants.STEP;

    public Grite_iter() throws IOException {
        super();
        myTools = new Tools();
        myTools.initParameter(transafile);
        taille = myTools.nbTransaction;
        this.nbtransaction = myTools.nbTransaction;
        nbitems = myTools.itemNembers;
        this.a = nbitems;
        GrdItem = new SolutionMap();
        // construct db
        getconfig();
        this.itemsets = getDataSet();
        // end of construction db
        this.item = null;
        this.dataset = Grite_iter.duplique(itemsets);
        // Grite.affiche(dataset);
        FileWriter fw = new FileWriter(new File(outputfile));
                fw.write("seuil" + AppConstants.SEP + "items" + AppConstants.SEP + "transaction" + AppConstants.SEP + "duree" + AppConstants.SEP + "nombre de motif" + "\n"); 
        fw.flush();
        fw.write("\n");
        fw.flush();
        //exec(fw);

        dataForDrawGraphe(min, max, pas);
    }

    public boolean[][] transposition(boolean[][] adjm) {

        boolean[][] tadjm = new boolean[adjm.length][adjm.length];

        for (int i = 0; i < adjm.length; i++) {
            for (int j = 0; j < adjm.length; j++) {
                tadjm[i][j] = adjm[j][i];
            }
        }
        return tadjm;

    }

    /**
     * @category statistics forage
     * @param nbitems2
     * @param nbtransaction2
     * @param duree
     * @param numberPatterns2
     * @throws IOException
     */
    private void wrtiteStatistic(double seuil, int nbitems2, int nbtransaction2, double duree, int numberPatterns2,
            FileWriter fw) throws IOException {

        try {
            String sep = "       ";

            fw.write(seuil + sep + nbitems2 + sep + nbtransaction2 + sep + (duree / 1000.0) + sep + numberPatterns2
                    + "\n");
            fw.flush();

        } finally {
        }
    }

    public void dataForDrawGraphe(double min, double max, double pas) throws IOException {
        FileWriter fw = new FileWriter(new File(outputfile));
                fw.write("seuil" + AppConstants.SEP + "items" + AppConstants.SEP + "transaction" + AppConstants.SEP + "duree" + AppConstants.SEP + "nombre de motif" + "\n"); 
        fw.flush();
        fw.write("\n");
        fw.flush();
        for (double i = min; i <= max; i = (i + pas)) {
            threshold = i;
            exec(fw);

        }
        fw.close();
    }

    /**
     * @param fw2
     * @category forage method
     */
    public void exec(FileWriter fw2) throws IOException {
        double startTime = System.currentTimeMillis();
        allContengent = createGradualsItemsetsOfSize1(dataset, item, a, taille);
		// myTools.setSizeMat(allContengent.get(0).length);
        // myTools.initMemory();
		/*
         * int[] memory0 = myTools.memory;
         * System.out.println("sons elt 1: "+myTools.getRoots(allContengent.get(
         * 0))); System.out.println("sons elt 1: "+myTools.maximumSupport(
         * allContengent.get(0), semantique.get(0), memory0) );
         */
        // affiche(allContengent.get(0));
        //GrdItem.put("level " + getNiveau(), semantique);
        System.out.println("level " + getNiveau() + "-------");
        int i = 0;
        for (Iterator<boolean[][]> iterator = (allContengent).iterator(); iterator.hasNext();) {
            boolean[][] is = (boolean[][]) iterator.next();
            myTools.setSizeMat(is.length);
            myTools.initMemory();
            int[] memory = myTools.memory;
            System.out.println(" -------> " + myTools.printGrad_Itemset(semantique.get(i)) + "( "
                    + myTools.maximumSupport_iter(is/* , semantique.get(i) */, memory) + " )" + " <----------- ");
            // affiche(is);
            System.out.println();
            System.out.println(isolated_matixs.get(i));
            System.out.println();
            System.out.println("--------------------------------- size (" + is.length + " )");
            i++;

        }
        for (int m = 1; m < attrList.length; m++) {
            allContengent = grite_execution();
            //test 14/08/2019
            /*if (m == 1) {
             serialiseMatrix(allContengent.get(0));
             lireAfficherMatrix();
             }*/

           // serialiseListMatrix("series_level" + m);
           // lireAfficherlistMatrix("series_level" + m);

            if (allContengent.size() > 0) {
                //GrdItem.put("level " + getNiveau(), semantique);
                System.out.println("level " + getNiveau() + "-------");
                // Grite.affiche(allContengent.get(0));
                // System.out.println("---- Grite.Grite()---- " +
                // allContengent.size() + "***" + semantique.size());
                // System.out.println("***" + GrdItem.toString());

                int i1 = 0;
                for (Iterator<boolean[][]> iterator = (allContengent).iterator(); iterator.hasNext();) {
                    boolean[][] is1 = (boolean[][]) iterator.next();
                    myTools.setSizeMat(is1.length);
                    myTools.initMemory();
                    int[] memory1 = myTools.memory;
                    System.out.println(" -------> " + myTools.printGrad_Itemset(semantique.get(i1)) + "( " + myTools
                            .maximumSupport_iter(is1/* , semantique.get(i1) */, memory1) + " )" + " <----------- ");
                    // affiche(is1);
                    System.out.println("\n" + isolated_matixs.get(i1));
                    System.out.println();
                    System.out.println("---------------------------------");
                    i1++;

                }

            }
        }

        System.out.println("Grite.exec(), nombre total de motif extrait est de :" + getNumberPatterns());
        double duree = (System.currentTimeMillis() - startTime);
        System.out.println("Grite.exec() Time execution eguals :" + duree / 1000.0 + " s");
        System.out.println("Grite.exec() Memory space ");
        try {
            wrtiteStatistic(threshold, nbitems, nbtransaction, duree, getNumberPatterns(), fw2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lireAfficherMatrix() {
        // Lire l'objet teA (conserv?? pr??c??demment) ?? l'aide de la s??rialisation.
        ObjectInputStream ois = null;
        BoolMatrix teA = null;
        String leFichier = "Serie.dat";
        System.out.println("Lecture par s??rialisation: DEBUT");
        try {

            ois = new ObjectInputStream(new FileInputStream(leFichier));
        } catch (StreamCorruptedException sce) {
            System.out.println("Le fichier est corrompu.");
            System.exit(3);
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr??e-sortie");
            System.exit(2);
        }
        try {
            teA = (BoolMatrix) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException ioe) {
            System.out.println("Nom de fichier inexistant.");
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr??e-sortie");
            System.exit(2);
        }
        System.out.println("Lecture par s??rialisation: FIN");
        teA.afficherM("Apr??s l'??criture et la lecture binaires");
    }

    public void serialiseMatrix(boolean[][] m) {
        // 14/08/2019
        //test to serialize and saving of list of matrix in file
        BoolMatrix mat = new BoolMatrix(m);

        // Sauvegarder l'objet teA ?? l'aide de la s??rialisation
        ObjectOutputStream oos = null;
        String leFichier = "Serie.dat";
        System.out.println("??criture par s??rialisation: DEBUT");
        try {
            oos = new ObjectOutputStream(new FileOutputStream(leFichier));
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr??e-sortie");

        }
        try {
            oos.writeObject(mat);
            oos.flush();
            oos.close();
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr??e-sortie");
            System.exit(2);
        }
        System.out.println("??criture par s??rialisation: FIN");

    }

    public void lireAfficherlistMatrix(String leFichier) {
        // Lire l'objet teA (conserv?? pr??c??demment) ?? l'aide de la s??rialisation.
        ObjectInputStream ois = null;
        AllMatrix teA = null;
        //= "Serie.dat";
        System.out.println("Lecture par s??rialisation: DEBUT");
        try {

            ois = new ObjectInputStream(new FileInputStream(leFichier));
        } catch (StreamCorruptedException sce) {
            System.out.println("Le fichier est corrompu.");
            System.exit(3);
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr??e-sortie");
            System.exit(2);
        }
        try {
            teA = (AllMatrix) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException ioe) {
            System.out.println("Nom de fichier inexistant.");
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr??e-sortie");
            System.exit(2);
        }
        System.out.println("Lecture par s??rialisation: FIN");
        // teA.afficherTous("Apr??s l'??criture et la lecture binaires");
    }

    public void serialiseListMatrix(String leFichier) {
        // 14/08/2019
        //test to serialize and saving of list of matrix in file
        AllMatrix allMatrix = new AllMatrix();
        allMatrix.addAll(allContengent);
        // Sauvegarder l'objet teA ?? l'aide de la s??rialisation
        ObjectOutputStream oos = null;
        //String leFichier = "Serie.dat";
        System.out.println("??criture par s??rialisation: DEBUT");
        try {
            oos = new ObjectOutputStream(new FileOutputStream(leFichier));
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr??e-sortie");

        }
        try {
            oos.writeObject(allMatrix);
            oos.flush();
            oos.close();
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr??e-sortie");
            System.exit(2);
        }
        System.out.println("??criture par s??rialisation: FIN");

    }

    /**
     * @category statistics forage
     * @param nbitems2
     * @param nbtransaction2
     * @param duree
     * @param numberPatterns2
     * @throws IOException
     */
    private void wrtiteStatistic(Float seuil, int nbitems2, int nbtransaction2, double duree, int numberPatterns2,
            FileWriter fw) throws IOException {

        try {
            String sep = "        ";
            for (int i = 0; i < attrList.length; i++) {
                fw.write(seuil + sep + nbitems2 + sep + nbtransaction2 + sep + (duree / 1000.0) + sep + numberPatterns2
                        + "\n");
                fw.flush();

            }

            fw.close();
        } finally {
        }
    }

    /**
     * initialisation parameter of grite: number of item , number of transaction
     *
     * @throws IOException
     */
    public void getconfig() throws IOException {
        attrList = Tools.attributenames(nbitems);// new String[nbitems];
        // output configuration of the user
        System.out.print(
                "\n [ configuration: " + nbitems + " items,and  " + nbtransaction + " transactions,mes item sont : ");
        for (int j = 0; j < attrList.length; j++) {
            System.out.print(" ," + attrList[j]);
        }
        System.out.println("]");
        System.out.println();
        for (int i = 0; i < semantique.size(); i++) {
            System.out.println(semantique.get(i) + "  ");
        }

    }

    /**
     *
     * @return Dataset into transaction Data Source
     * @throws IOException
     */
    public ArrayList<float[]> getDataSet() throws IOException {
        BufferedReader data_in;
        String oneLine = "";

        data_in = new BufferedReader(new InputStreamReader(new FileInputStream(transafile)));
        for (int i = 0; i < nbtransaction; i++) {
            float[] tmp = new float[nbitems];
            oneLine = data_in.readLine(); // one transaction
            StringTokenizer transaction = new StringTokenizer(oneLine, " ");
            float val;
            int index = 0;
            while (transaction.hasMoreElements()) {
                Object object = (Object) transaction.nextElement();
                val = Float.parseFloat((String) object);
                tmp[index] = (val);
                index++;
            }
            itemsets.add(tmp);

        }
        data_in.close();
        return itemsets;

    }

    public static float[][] duplique(ArrayList<float[]> mat) {
        float[][] res = new float[mat.size()][];
        for (int i = 0; i < mat.size(); i++) {
            res[i] = new float[mat.get(i).length];
            for (int j = 0; j < mat.get(i).length; j++) {
                res[i][j] = mat.get(i)[j];
            }
        }
        return res;
    }

    public static void affiche(boolean[][] tab) {
        for (boolean[] d : tab) {
            System.out.println();
            for (boolean v : d) {
                System.out.print(" " + (v ? 1 : 0) + " ");
            }
        }
    }

    public static void affiche(float[][] tab) {
        for (float[] d : tab) {
            System.out.println();
            for (float v : d) {
                System.out.print(v + " ");
            }
        }
    }

    // a is item number a
    public static float[] getDataColByCol(float[][] dataset, float[] item, int a, int taille) {
        taille = Grite_iter.taille;
        item = new float[taille];
        int l = 0;
        for (int i = 0; i < dataset.length; i++) {

            for (int k = 0; k < Grite_iter.nbitems; k++) {
                if (l == i && k == a) {
                    item[i] = dataset[i][k];
                    // System.out.println(item[i]+ " ");
                }
            }
            // res.add(item);
            l++;
        }

        return item;
    }

    public static void getAllColum(float[][] dataset, float[] item, int a, int taille) {
        for (a = 0; a < Grite_iter.nbitems; a++) {
            float[] rescol = Grite_iter.getDataColByCol(dataset, item, a, taille);
            for (int i = 0; i < rescol.length; i++) {
                System.out.println(rescol[i] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * @category forage method
     * @param dataset
     * @param item
     * @param a
     * @param taille
     * @return
     */
    private ArrayList<boolean[][]> createGradualsItemsetsOfSize1(float[][] dataset, float[] item, int a, int taille) throws IOException {
        ArrayList<boolean[][]> allContengent = new ArrayList<>();
        ArrayList<String[]> semantique = new ArrayList<>();
        ArrayList<Integer> listobj = new ArrayList<>();
        ArrayList<ArrayList<Integer>> isolated_matix = new ArrayList<>();
        // ArrayList<Integer> mySupports = new ArrayList<>();
        for (int i = 0; i < nbitems; i++) {
            float[] rescol = Grite_iter.getDataColByCol(dataset, item, i, taille);
            String[] attr = new String[2];
            attr[0] = attrList[i];
            attr[1] = "-";
            // gestion objets croissant X> et creation matrice contigence
            // associe
            boolean[][] Contengence1 = new boolean[taille][taille];
            //initialisation de la matrice binaire
            creationMatriceBinaireValeurXmoins(taille, Contengence1, rescol);
            // creationMatriceBinaireValeurTriangulaireXmoins(taille, Contengence1, rescol);
            myTools.setSizeMat(Contengence1.length);
            myTools.initMemory();
            int[] memory = myTools.memory;
           //int cpt = myTools.maximumSupport(Contengence1/* , attr */, memory);

            //test deux methode calcul du support en sortir receullir le temps dexecution , 
            //numero echantillon matrice binaire, t1,t2,cpt,cpt01
//            long start1 = System.currentTimeMillis();
            affiche(Contengence1);
            int cpt = myTools.maximumSupport(Contengence1, memory);
//            long end1 = System.currentTimeMillis();

            //long start2 = System.currentTimeMillis();
            myTools.initMemory();
            memory = myTools.memory;
            int cpt01 = myTools.maximumSupport_iter(Contengence1, memory);
            //  long end2 = System.currentTimeMillis();
//            try {
//                FileWriter fw = new FileWriter(new File("calcul_comparatif.dat"), true);
//                fw.write(attr[0] + attr[1] + " " + (end1 - start1) + " " + (end2 - start2) + "  " + cpt + " " + cpt01 + "\n");
//                fw.flush();
//                fw.write("\n");
//                //fw.write("Item graduel	Tps recusif	Tps iteratif	N1	N2");
//                fw.flush();
//            } catch (Exception e) {
//            }

            float support = myTools.supportCalculation(cpt, taille);
            if (support >= this.threshold) {
                listobj = getIsolateObjet(Contengence1);
                //System.out.println("Grite.createGradualsItemsetsOfSize1()"+myTools.printGrad_Itemset(attr) +": " +listobj);
                isolated_matix.add(listobj);

                boolean[][] mmoins = MatrixNormalizer(Contengence1, listobj);
                // test display
/*
                 System.out.println(listobj + " -:+ ");

                 affiche(Contengence1);
                 System.out.println("\n m1 normalise est \n");
                 affiche(mmoins);
                 System.out.println("\n<------------------passage a m2 ------------------> \n");
                 */
                allContengent.add(mmoins);
                semantique.add(attr);
                // mySupports.add(cpt);
            }

            // gestion objets decroissant X< et creation matrice contigence
            // associe
            String[] attr1 = new String[2];
            attr1[0] = attrList[i];
            attr1[1] = "+";
            boolean[][] Contengence2 = new boolean[taille][taille];
            //initialisation de la matrice binaire
            creationMatriceBinaireValeurXPlus(taille, Contengence2, rescol);
            //creationMatriceBinaireValeurTriangulaireXPlus(taille, Contengence2, rescol);
            myTools.setSizeMat(Contengence2.length);
            myTools.initMemory();
            int[] memory1 = myTools.memory;
            int cpt1 = myTools.maximumSupport_iter(Contengence2/* , attr1 */, memory1);
            float support1 = myTools.supportCalculation(cpt1, taille);
            if (support1 >= this.threshold) {
                listobj = getIsolateObjet(Contengence2);
                //System.out.println("Grite.createGradualsItemsetsOfSize1()"+myTools.printGrad_Itemset(attr1) +": " +listobj);

                isolated_matix.add(listobj);
                boolean[][] mplus = MatrixNormalizer(Contengence2, listobj);
                allContengent.add(mplus);
                semantique.add(attr1);
                // mySupports.add(cpt1);
            }

        }
        setNiveau(getNiveau() + 1);
       //GrdItem.put("level" + getNiveau(), semantique);
        setNumberPatterns(getNumberPatterns() + semantique.size());
        isolated_matixs.clear();
        isolated_matixs = isolated_matix;
        Grite_iter.semantique = semantique;
        return allContengent;

    }

    /**
     * listes tous non participant au calcul du support
     *
     * @param objet_supp
     * @return
     */
    public ArrayList<Integer> listingParticipateObjets(ArrayList<Integer> objet_supp) {
        ArrayList<Integer> malist = new ArrayList<>();
        for (int i = 0; i < Grite_iter.taille; i++) {
            int p = myTools.Recherche(objet_supp, i);
            if (p != -1) {
                malist.add(p);
            }
        }
        return malist;
    }

    public ArrayList<Integer> listingComplement(ArrayList<Integer> objet_supp) {
        ArrayList<Integer> malist = new ArrayList<>();
        for (int i = 0; i < Grite_iter.taille; i++) {
            int p = myTools.Recherche(objet_supp, i);
            if (p == -1) {
                malist.add(i);
            }
        }
        return malist;
    }

    public ArrayList<Integer> copyDeep(ArrayList<Integer> tab) {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (Integer e : tab) {
            tmp.add(e);
        }
        return tmp;
    }

    public int findPosition(ArrayList<Integer> res, int p) {
        ArrayList<Integer> res1 = new ArrayList<>();
        // res1 = copyDeep(res);
        int pos = -1, i = 0;
        while (i < res.size()) {
            if ((int) res.get(i) == p) {
                pos = i;
                // i++;
                break;
            }
            i++;
        }
        return pos;
    }

    /**
     * prend une liste src retour une liste des index a supprimer
     *
     * @param srclist
     * @param nonParticipated
     * @return
     */
    public ArrayList<Integer> marqueurList(ArrayList<Integer> srclist, ArrayList<Integer> nonParticipated) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < nonParticipated.size(); i++) {
            int pos = findPosition(srclist, nonParticipated.get(i));
            if (pos != -1) {
                res.add(pos);
            }
        }
        return res;
    }

    /*
     * public ArrayList<Integer> makeNewListRm(ArrayList<Boolean> marqPi) {
     * ArrayList<Integer> l = new ArrayList<>(); for (int i = 0; i <
     * marqPi.size(); i++) { if (!marqPi.get(i)) { l.add(i); } } return l; }
     */
    /**
     *
     * @param m1 matrix bool 1
     * @param m2 matrix bool 2
     * @param pos1 list of isolate item of m1
     * @param pos2 list of isolate item of m2
     * @return List of 2 object :first resultant matrix ,and second listing of
     * here isolate object
     */
    /**
     * @param m1
     * @param m2
     * @param pos1
     * @param pos2
     * @param strings2
     * @param
     * @return
     *//*
     * System.out.println("\n  list 1 objets utilisee " + nonpos1 +
     * "---\n"); System.out.println("\n list 2 objets utilisee " + nonpos2 +
     * "---\n"); System.out.println("\n  ***---bs?****\n");
     */

    public ArrayList<Object> jointure(boolean[][] m1, boolean[][] m2, ArrayList<Integer> pos1, ArrayList<Integer> pos2,
            String[] itemset1, String[] itemset2) {
        ArrayList<Object> rtn = new ArrayList<>();
        /**
         * nonposi list of objets use in the adjiont matrix i
         */
        ArrayList<Integer> nonpos1, nonpos2;
        //
        ArrayList<Integer> marqP1 = new ArrayList<>(), marqP2 = new ArrayList<>();
        ArrayList<Integer> total_non_obj;
        boolean[][] fusion, mat1, mat2;

        // mat1 = m1;
        // mat2 = m2;
		/*
         * System.out.println("\n  list 1 objets non  utilisee " + pos1 +
         * "---\n"); System.out.println("\n list 2 objets non utilisee " + pos2
         * + "---\n");
         */
        nonpos1 = listingComplement(pos1);
        nonpos2 = listingComplement(pos2);
        // System.out.println(pos1 + " :- "+ pos2);
        // System.out.println(nonpos1 + " :+ "+ nonpos2);
        total_non_obj = UnionList(pos1, pos2);
        // System.out.println("\n list total des objets non utilisee " +
        // total_non_obj + "---\n");
        Tools t = new Tools();

        // System.out.println("\n list 1 objets utilisee par " +
        // t.printGrad_Itemset(itemset1) + nonpos1 + "---\n");
        // System.out.println("\n liste 2 objets utilisee par " +
        // t.printGrad_Itemset(itemset2) + nonpos2 + "---\n");
        // System.out.println("total: "+ total_non_obj);
        marqP2 = marqueurList(nonpos2, total_non_obj);

        // System.out.println("\n ***---****\n");
        marqP1 = marqueurList(nonpos1, total_non_obj);
        // System.out.println(marqP1 + " -:+ "+ marqP2);

        mat1 = MatrixNormalizer(m1, marqP1);
        mat2 = MatrixNormalizer(m2, marqP2);
        // affiche(m1);
        // System.out.println("\n m1 normalise est \n");
        // affiche(mat1);
        // System.out.println("\n <------------------passage a
        // m2------------------> \n");
        // affiche(m2);
        // System.out.println("m2 normalise est \n");
        // affiche(mat2);
        //
        // System.out.println("\n novelle matrix 1 apres normalisation :\n");
        // affiche(mat1);
        // System.out.println("\n nouvelle matrix 2 apres normalisation : \n");
        // affiche(mat2);
        // System.out.println("\n");

        int t1 = mat1.length;
        int t2 = mat2.length;
        // System.out.println("\n ---- est ce que ? " + m1.length + " == " +
        // m2.length + "\n");
        // System.out.println("\n ---- est ce que ? " + t1 + " == " + t2 +
        // "\n");
        fusion = new boolean[t1][t1];

        for (int i = 0; i < fusion.length; i++) {
            for (int j = 0; j < fusion.length; j++) {
                fusion[i][j] = mat1[i][j] & mat2[i][j];
            }
        }
        ArrayList<Integer> rmFinal = new ArrayList<>();
        // rmFinal = total_non_obj;
        ArrayList<Integer> setObjUses = listingComplement(total_non_obj);
        //affiche(fusion);		
        ArrayList<Integer> l = getIsolateObjet(fusion);
        //System.out.println("\n Grite.createGradualsItemsetsOfSize1()" + l+"\n");
        rmFinal = UnionList(total_non_obj, getEltAtPositionSet(setObjUses, l));
        fusion = MatrixNormalizer(fusion, l);

        //affiche(fusion);
        //System.out.println("\n<------------->\n");
        rtn.add(fusion);
        rtn.add(rmFinal);
        return rtn;
    }

    private ArrayList<Integer> getEltAtPositionSet(ArrayList<Integer> listeSrc, ArrayList<Integer> positions) {
        ArrayList<Integer> rep = new ArrayList<>();
        for (int e : positions) {
            rep.add(listeSrc.get(e));
        }
        return rep;
    }

    private ArrayList<Integer> UnionList(ArrayList<Integer> pos1, ArrayList<Integer> pos2) {
        ArrayList<Integer> res = new ArrayList<>();
        // apres
        for (int i = 0; i < pos1.size(); i++) {
            res.add(pos1.get(i));
        }
        for (int i = 0; i < pos2.size(); i++) {
            if (!myTools.Apartient(res, pos2.get(i))) {
                res.add(pos2.get(i));
            }

        }
        return res;
    }

    /**
     * @return the threshold
     */
    public double getThreshold() {
        return threshold;
    }

    /**
     * @param threshold the threshold to set
     */
    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    /**
     * @return the taille
     */
    public int getTaille() {
        return taille;
    }

    /**
     * @param taille the taille to set
     */
    public void setTaille(int taille) {
        this.taille = taille;
    }

    /**
     * @return the attrList
     */
    public static String[] getAttrList() {
        return attrList;
    }

    /**
     * @param attrList the attrList to set
     */
    public static void setAttrList(String[] attrList) {
        Grite_iter.attrList = attrList;
    }

    private boolean IsItIsolateItem(boolean[][] m, int objet) {
        boolean isremoved = false;
        int i = 0;
        for (i = 0; i < m.length; i++) {
            if (!m[objet][i] && !m[i][objet]) {
                continue;
            } else {
                break;
            }
        }
        if (i == m.length) {
            isremoved = true;
        } else {
            isremoved = false;
        }
        return isremoved;

    }

    private ArrayList<Integer> getIsolateObjet(boolean[][] m) {
        ArrayList<Integer> objectremovable = new ArrayList<>();
        for (int i = 0; i < m.length; i++) {
            if (IsItIsolateItem(m, i)) {
                objectremovable.add(i);
            }
        }
        return objectremovable;
    }

    /**
     * goals of this method is to remove all isolate object of boolean matrix
     * and return another matrix
     *
     * @param m matrix which contains isolate item
     * @return
     */
    @SuppressWarnings("unused")
    private boolean[][] MatrixNormalizer(boolean[][] m) {
        ArrayList<Integer> objectremovable = getIsolateObjet(m);

        boolean[][] result = new boolean[m.length - objectremovable.size()][m.length - objectremovable.size()];
        int k = 0, l = 0;

        for (int i = 0; i < m.length; i++) {
            l = 0;
            if (myTools.Apartient(objectremovable, i)) {
                continue;
            } else {
                for (int j = 0; j < m.length; j++) {
                    if (/*myTools.Apartient(objectremovable, i) ||*/myTools.Apartient(objectremovable, j)) {
                        continue;
                    } else {
                        result[k][l] = m[i][j];
                        l++;
                    }
                }

            }
            k++;
        }
        return result;

    }

    private boolean[][] MatrixNormalizer(boolean[][] m, ArrayList<Integer> objectremovable) {
        boolean[][] result = new boolean[m.length - objectremovable.size()][m.length - objectremovable.size()];
        int k = 0, l = 0;

        for (int i = 0; i < m.length; i++) {
            l = 0;
            if (myTools.Apartient(objectremovable, i)) {
                continue;
            } else {
                for (int j = 0; j < m.length; j++) {
                    if (/*myTools.Apartient(objectremovable, i) ||*/myTools.Apartient(objectremovable, j)) {
                        continue;
                    } else {
                        result[k][l] = m[i][j];

                        l++;

                    }
                }

            }
            k++;
        }
        return result;

    }

    @SuppressWarnings("unchecked")
    public ArrayList<boolean[][]> grite_execution() {
        // createGradualsItemsetsOfSize1();
        ArrayList<boolean[][]> computeAllContengent = new ArrayList<>();

        ArrayList<String[]> semantiques = new ArrayList<>();

        ArrayList<ArrayList<Integer>> isolated_matix = new ArrayList<>();

        // ArrayList<Integer> mySupport = new ArrayList<>();
        String[] tmp1;

        ArrayList<Integer> listobj;

        ArrayList<Object> tmp2 = new ArrayList<>();

        int cpt;
        float support;

        System.out.println(allContengent.size() + "\n");

        for (int i = 0; i < allContengent.size(); i++) {
            for (int j = i + 1; j < allContengent.size(); j++) {

                // System.out.println("1: " +
                // myTools.printGrad_Itemset(semantique.get(i)) + " 2: "
                // + myTools.printGrad_Itemset(semantique.get(j)));
                if (myTools.lexicalComparaison(semantique.get(i), semantique.get(j))) {
                    // System.out.println("after test 1: " +
                    // myTools.printGrad_Itemset(semantique.get(i)) + " 2: "
                    // + myTools.printGrad_Itemset(semantique.get(j)));

                    tmp2 = jointure(allContengent.get(i), allContengent.get(j), isolated_matixs.get(i),
                            isolated_matixs.get(j), semantique.get(i), semantique.get(j));

                    boolean[][] tmp = (boolean[][]) tmp2.get(0);

                    myTools.setSizeMat(tmp.length);

                    myTools.initMemory();
                    int[] memory = myTools.memory;

                    cpt = myTools.maximumSupport_iter(tmp/* , tmp1 */, memory);
                    support = myTools.supportCalculation(cpt, taille);

                    if (support >= this.threshold) {
                        computeAllContengent.add(tmp);
                        tmp1 = myTools.lexicalFusion(semantique.get(i), semantique.get(j));
                        semantiques.add(tmp1);
                        listobj = (ArrayList<Integer>) tmp2.get(1);
                        isolated_matix.add(listobj);

                    }

                }

            }
        }

        allContengent.clear();
        semantique.clear();

        isolated_matixs.clear();

        // determination frequent itemset
        allContengent = computeAllContengent;

        semantique = semantiques;

        isolated_matixs = isolated_matix;

        setNiveau(niveau + 1);

        //GrdItem.put("level" + getNiveau(), semantiques);

        setNumberPatterns(getNumberPatterns() + semantiques.size());

        return computeAllContengent;
    }

    /**
     * @return the niveau
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * @param niveau the niveau to set
     */
    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    /**
     * @return the numberPatterns
     */
    public int getNumberPatterns() {
        return numberPatterns;
    }

    /**
     * @param numberPatterns the numberPatterns to set
     */
    public void setNumberPatterns(int numberPatterns) {
        this.numberPatterns = numberPatterns;

    }

    private class SolutionMap {

        // contains graduals pattern of all level
        HashMap<String, ArrayList<String[]>> graduelSet;

        public SolutionMap() {
            super();
            graduelSet = new HashMap<>();
        }

        public HashMap<String, ArrayList<String[]>> getGraduelSet() {
            return graduelSet;
        }

        public void setGraduelSet(HashMap<String, ArrayList<String[]>> graduelSet) {
            this.graduelSet = graduelSet;
        }

        /**
         *
         * @see java.util.HashMap#clear()
         */
        public void clear() {
            graduelSet.clear();
        }

        /**
         * @param arg0
         * @return
         * @see java.util.HashMap#containsKey(java.lang.Object)
         */
        public boolean containsKey(Object arg0) {
            return graduelSet.containsKey(arg0);
        }

        /**
         * @param arg0
         * @return
         * @see java.util.HashMap#get(java.lang.Object)
         */
        public ArrayList<String[]> get(Object arg0) {
            return graduelSet.get(arg0);
        }

        /**
         * @return @see java.util.HashMap#isEmpty()
         */
        public boolean isEmpty() {
            return graduelSet.isEmpty();
        }

        /**
         * @return @see java.util.HashMap#keySet()
         */
        public Set<String> keySet() {
            return graduelSet.keySet();
        }

        /**
         * @param arg0
         * @param arg1
         * @return
         * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
         */
        public ArrayList<String[]> put(String arg0, ArrayList<String[]> arg1) {
            return graduelSet.put(arg0, arg1);
        }

        /**
         * @param arg0
         * @see java.util.HashMap#putAll(java.util.Map)
         */
        public void putAll(Map<? extends String, ? extends ArrayList<String[]>> arg0) {
            graduelSet.putAll(arg0);
        }

        /**
         * @return @see java.util.HashMap#size()
         */
        public int size() {
            return graduelSet.size();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "SolutionMap [graduelSet=" + graduelSet + ", getGraduelSet()=" + getGraduelSet() + ", isEmpty()="
                    + isEmpty() + ", keySet()=" + keySet() + ", size()=" + size() + ", getClass()=" + getClass()
                    + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
        }

    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        // float[] item = new float[9];
        Grite_iter ap = new Grite_iter();

        /*
         * ap.getconfig(); ArrayList<float[]> itemsets = ap.itemsets;
         */
        /*
         * for (ArrayList<Integer> arrayList : itemsets) { for (Integer integer
         * : arrayList) { System.out.println("< "+integer+ " />"); } }
         */

        /*
         * float[][] dataset = ap.dataset;// Grite.duplique(itemsets); //
         * Grite.affiche(ap.dataset); System.out.println(); int a = 0; //
         * float[] item = null; int taille = 100; Grite.getAllColum(dataset,
         * item, a, taille); ap.grite_execution(); ArrayList<boolean[][]>
         * allContengent = ap.createGradualsItemsetsOfSize1(dataset, item, 10,
         * taille); // ap.createGradualsItemsetsOfSize1(ap.dataset, item,
         * 3,taille); System.out.println( "Grite.main() " + allContengent.size()
         * + " nombre de regle graduel semantique :" + semantique.size());
         * System.out.println();
         */
    }
}
