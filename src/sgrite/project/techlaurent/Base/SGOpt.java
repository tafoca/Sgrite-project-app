package sgrite.project.techlaurent.Base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import static sgrite.project.techlaurent.Base.MemoryPerformance.bytesToMegabytes;
import sgrite.project.techlaurent.Base.composants.ScriteComposant;
import sgrite.project.techlaurent.common.CommonMethod;

/**
 * Implementtaion of Method SGOpt of SGrite
 *
 * @author The class encapsulates and optimisation of implementation of the
 * Grite algorithm to compute Gradual frequent itemsets .
 * @author tabueu Fotso laurent, University of DSCHANG, 2017
 * @copyright GNU General Public License v3 No reproduction in whole or part
 * without maintaining this copyright notice and imposing this condition on any
 * subsequent users.
 *
 */
public class SGOpt extends CommonMethod implements MemoryPerformance {

    private Tools myTools;
    /*
     * private static int nbitems =10;//3; private int nbtransaction = 100;//9;
     */
    private static int nbitems = 0;
    private int nbtransaction = 0;
    private double threshold = AppConstants.THREHOLD;//0.01, 0.05; 0.2 default value
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
    //   ArrayList<int[]> memories = new ArrayList<>(); //list prevouis memory to uss 
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
    private String transafile = AppConstants.TRANSACTIONFILE; // "transa.dat";//transa1.dat
    // "test.dat";//"gri/I4408.dat";//
    // default transaction file
    private String configfile = ""; // default configuration file
    // private String outputfile = "ouput.dat";
    private static String[] attrList;
    private static ArrayList<String[]> semantique = new ArrayList<>();
    // private static ArrayList<Integer> mySupports = new ArrayList<>();
	/*
     * 1. Génération des 1-itemsets graduels : pour chaque item i de la base DB,
     * l’item graduel i ≥ est construit en ordonnant les tO [i] selon la
     * relation d’ordre ≥,
     */
    ArrayList<Integer> removedindex;
    private int niveau = 0;
    private int numberPatterns = 0;
    private String outputfile = "TimeSGOpt";
    double min = AppConstants.MIN;
    double max = AppConstants.MAX;
    double pas = AppConstants.STEP;
    // Map<Integer,List<ScriteComposant>> mapOfLevelAndSgriteComponent= new Hashtable<>();
    public String memoryusedcsv = "MemorySGOpt";

    public SGOpt() throws IOException {
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
        this.dataset = SGOpt.duplique(itemsets);
        // Grite.affiche(dataset);
        FileWriter fw = new FileWriter(new File(outputfile));
        FileWriter fw3 = new FileWriter(new File(memoryusedcsv), true);
        fw.write("seuil" + AppConstants.SEP + "items" + AppConstants.SEP + "transaction" + AppConstants.SEP + "duree" + AppConstants.SEP + "nombre de motif" + "\n");
        fw.flush();
        fw.write("\n");
        fw.flush();
        exec(fw, fw3);

        //  dataForDrawGraphe(min, max, pas);
    }

    /**
     *
     * @param source
     * @param seuil
     * @throws IOException
     */
    public SGOpt(String[] params) throws IOException {
        super();
        this.transaFile = params[0];
        this.threshold = Double.parseDouble(params[1]);
        myTools = new Tools();
        //System.out.println("sgrite.project.techlaurent.Base.GritestrictAppr2.<init>()"+ source);
        myTools.initParameter(params[0]);
        taille = myTools.nbTransaction;
        this.nbtransaction = myTools.nbTransaction;
        nbitems = myTools.itemNembers;
        this.a = nbitems;
        GrdItem = new SolutionMap();
        // construct db
        getconfig();
        this.itemsets = getDataSet(params[0]);
        // end of construction db
        this.item = null;
        this.dataset = SGOpt.duplique(itemsets);
        // Grite.affiche(dataset);
        FileWriter fw = new FileWriter(new File(outputfile), true);
        fw.write("seuil" + AppConstants.SEP + "items" + AppConstants.SEP + "transaction" + AppConstants.SEP + "duree" + AppConstants.SEP + "nombre de motif" + "\n");
        fw.flush();
        //fw.write("\n");
        fw.flush();
        if (params.length <= 2) {
            threshold = Double.parseDouble(params[1]);
            FileWriter fw3 = new FileWriter(new File(memoryusedcsv));
            exec(fw, fw3);
        } else {
            min= Double.parseDouble(params[1]);
            max = Double.parseDouble(params[2]);
            pas = Double.parseDouble(params[3]);
            dataForDrawGraphe(min, max, pas);
        }
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

    private void wrtiteStatisticMemoryInfo(double seuil, int nbitems2, int nbtransaction2, double occupation, int numberPatterns2,
            FileWriter fw) throws IOException {

        try {
            String sep = "       ";

            fw.write(seuil + sep + occupation + sep + "\n");
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
        FileWriter fw3 = new FileWriter(new File(memoryusedcsv), true);
        for (double i = min; i <= max; i = (i + pas)) {
            threshold = i;
            exec(fw, fw3);
            AppConstants.USEDMEMORY = 0;
            //emptyGroupSet();

        }
        fw.close();
    }

    private void emptyGroupSet() {
        semantique.clear();
        allContengent.clear();
        isolated_matixs.clear();

    }

    /**
     * @param fw2
     * @category forage method
     */
    public void exec(FileWriter fw2, FileWriter fw3) {
        double startTime = System.currentTimeMillis();
        allContengent = createGradualsItemsetsOfSize1(dataset, item, a, taille);
        initialisationOfMaxMemUsed();
        printPatternConsole();
        //TODO 1 : create Graduals 2-Itemsets First with first item positif (write a method)
        allContengent = genGradual2Itemsets();
        initialisationOfMaxMemUsed();
        printPatternConsole();
        for (int m = 1; m < attrList.length; m++) {
            allContengent = grite_execution();
            initialisationOfMaxMemUsed();
            if (allContengent.size() > 0) {
                printPatternConsole();
            }
        }

        System.out.println("SGOpt.exec(), number of gradual patterns extracted: " + getNumberPatterns());
        double duree = (System.currentTimeMillis() - startTime);
        System.out.println("SGOpt.exec() execution time (s) :" + duree / 1000.0);
        System.out.println("memory Uusage (KB) : " + AppConstants.USEDMEMORY);
        try {
            wrtiteStatistic(threshold, nbitems, nbtransaction, duree, getNumberPatterns(), fw2);
            wrtiteStatisticMemoryInfo(threshold, nbitems, nbtransaction, AppConstants.USEDMEMORY, getNumberPatterns(), fw3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialisationOfMaxMemUsed() {
        if (AppConstants.USEDMEMORY < MemoryPerformance.getMemoryCurrentMB()) {
            AppConstants.USEDMEMORY = MemoryPerformance.getMemoryCurrentMB();
        }
    }

    public void printPatternConsole() {
        int i0 = 0;
        for (Iterator<boolean[][]> iterator = (allContengent).iterator(); iterator.hasNext();) {
            boolean[][] is = (boolean[][]) iterator.next();
            myTools.setSizeMat(is.length);
            myTools.initMemory();
            int[] memory = myTools.memory;
            //TODO COMMENT CONSOLE PRINT
            System.out.println(" -------> " + myTools.printGrad_Itemset(semantique.get(i0)) + "( "
                    + myTools.maximumSupport(is/* , semantique.get(i) */, memory) + " )" + " <----------- ");
            i0++;
        }
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
            String sep = AppConstants.SEP;
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
    private ArrayList<float[]> getDataSet() throws IOException {
        BufferedReader data_in;
        String oneLine = "";

        data_in = new BufferedReader(new InputStreamReader(new FileInputStream(transafile)));
        getDataSetImpl(data_in);
        data_in.close();
        return itemsets;

    }

    private ArrayList<float[]> getDataSet(String source) throws IOException {
        BufferedReader data_in;
        String oneLine = "";

        data_in = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
        getDataSetImpl(data_in);
        data_in.close();
        return itemsets;

    }

    /**
     * construction en memoire du jeux de donnees tableau de transactions
     *
     * @param data_in
     * @throws NumberFormatException
     * @throws IOException
     */
    public void getDataSetImpl(BufferedReader data_in) throws NumberFormatException, IOException {
        String oneLine;
        for (int i = 0; i < nbtransaction; i++) {
            float[] tmp = new float[nbitems];
            oneLine = data_in.readLine(); // one transaction
            StringTokenizer transaction = new StringTokenizer(oneLine, " ");
            float val;
            int index = 0;
            while (transaction.hasMoreElements()) {
                Object object = transaction.nextElement();
                val = Float.parseFloat((String) object);
                tmp[index] = (val);
                index++;
            }
            itemsets.add(tmp);

        }
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
        taille = SGOpt.taille;
        item = new float[taille];
        int l = 0;
        for (int i = 0; i < dataset.length; i++) {

            for (int k = 0; k < SGOpt.nbitems; k++) {
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
        for (a = 0; a < SGOpt.nbitems; a++) {
            float[] rescol = SGOpt.getDataColByCol(dataset, item, a, taille);
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
    private ArrayList<boolean[][]> createGradualsItemsetsOfSize1(float[][] dataset, float[] item, int a, int taille) {
        ArrayList<boolean[][]> allContengent = new ArrayList<>();
        ArrayList<int[]> memories = new ArrayList<>();
        ArrayList<String[]> semantique = new ArrayList<>();
        ArrayList<ArrayList<Integer>> isolated_matix = new ArrayList<>();
        //  List<ScriteComposant> scriteComposants = new ArrayList<>();
        ArrayList<Integer> listobj = new ArrayList<>();
        for (int i = 0; i < nbitems; i++) {
            float[] rescol = SGOpt.getDataColByCol(dataset, item, i, taille);
            String[] attr = new String[2];
            attr[0] = attrList[i];
            attr[1] = "-";
            String[] attr1 = new String[2];
            attr1[0] = attrList[i];
            attr1[1] = "+";
            boolean[][] Contengence2 = new boolean[taille][taille];
            // gestion objets croissant X> et creation matrice contigence
            // associe
            boolean[][] Contengence1 = new boolean[taille][taille];
            creationMatriceBinaireValeurXmoins(taille, Contengence1, rescol);
            myTools.setSizeMat(Contengence1.length);
            myTools.initMemory();
            int[] memory = myTools.memory;
            int cpt = determinerSupport1(Contengence1, memory);
            float support = Utilitaire.supportCalculation(cpt, Contengence1.length);
            if (support > this.threshold) {
                Contengence2 = transposition(Contengence1);
                listobj = getIsolateObjet(Contengence1);
                isolated_matix.add(listobj);
                boolean[][] mmoins = MatrixNormalizer(Contengence1, listobj);
                allContengent.add(mmoins);
                semantique.add(attr);
                ArrayList<Integer> listobj1 = getIsolateObjet(Contengence2);
                isolated_matix.add(listobj1);
                boolean[][] mplus = MatrixNormalizer(Contengence2, listobj1);
                allContengent.add(mplus);
                semantique.add(attr1);

                // initialisationOfMaxMemUsed();
                ////System.out.println("1. Used memory is bytes: " + MemoryPerformance.getMemoryCurrent());
                //System.out.println("1. Used memory is megabytes: " + MemoryPerformance.getMemoryCurrentMB());
                // insertAnSgriteComonentAlist(scriteComposants, attr, mmoins, listobj, attr1, mplus, listobj1);       
            }
        }
        //mapOfLevelAndSgriteComponent.putIfAbsent(getNiveau()+1, scriteComposants);
        setNiveau(getNiveau() + 1);
        //GrdItem.put("level" + getNiveau(), semantique);
        setNumberPatterns(getNumberPatterns() + semantique.size());
        isolated_matixs.clear();
        isolated_matixs = isolated_matix;
        SGOpt.semantique = semantique;
        return allContengent;
    }

    public void insertAnSgriteComonentAlist(List<ScriteComposant> scriteComposants, String[] attr, boolean[][] mmoins, ArrayList<Integer> listobj, String[] attr1, boolean[][] mplus, ArrayList<Integer> listobj1) {
        scriteComposants.add(new ScriteComposant(attr, mmoins, listobj));
        scriteComposants.add(new ScriteComposant(attr1, mplus, listobj1));
    }

    public void insertAnSgriteComonentAlist(List<ScriteComposant> scriteComposants, String[] motif, boolean[][] adj, ArrayList<Integer> listobj) {
        scriteComposants.add(new ScriteComposant(motif, adj, listobj));
    }

    /**
     * generation des 2-itemsets
     *
     * @param allContengent 1-itemset graduel frequent
     * @return
     */
    private ArrayList<boolean[][]> genGradual2Itemsets() {

        //List<ScriteComposant> scriteComposants = new ArrayList<>();
        ArrayList<boolean[][]> computeAllContengent = new ArrayList<>();

        ArrayList<String[]> semantiques = new ArrayList<>();

        ArrayList<ArrayList<Integer>> isolated_matix = new ArrayList<>();
     //   ArrayList<int[]> buildMemories = new ArrayList<>();

        // ArrayList<Integer> mySupport = new ArrayList<>();
        String[] tmp1;

        ArrayList<Integer> listobj;

        ArrayList<Object> tmp2 = new ArrayList<>();

        int cpt;
        float support;

        // System.out.println(allContengent.size() + "\n");
        //TODO ajouter la strategy de generation des candidats ici
        //1- utilisation d'une map pour stocker les candidats par niveau
        //2- de meme pour les objets isoles et les matrices d'adjacence
        for (int i = 0; i < allContengent.size(); i++) {
            for (int j = i + 1; j < allContengent.size(); j++) {
                String[] item_g = semantique.get(i);
                String signe = item_g[1];
                if (signe.equals("+") && myTools.lexicalComparaison(semantique.get(i), semantique.get(j))) {
                    tmp2 = jointure(allContengent.get(i), allContengent.get(j), isolated_matixs.get(i),
                            isolated_matixs.get(j), semantique.get(i), semantique.get(j));

                    boolean[][] tmp = (boolean[][]) tmp2.get(0);

                    myTools.setSizeMat(tmp.length);

                    myTools.initMemory();
                    int[] memory = myTools.memory;
                    cpt = determinerSupport1(tmp, memory);
                    support = Utilitaire.supportCalculation(cpt, taille);
                    if (support >= this.threshold) {
                        computeAllContengent.add(tmp);
                        tmp1 = myTools.lexicalFusion(semantique.get(i), semantique.get(j));
                        semantiques.add(tmp1);
                        listobj = (ArrayList<Integer>) tmp2.get(1);
                        isolated_matix.add(listobj);
//                        buildMemories.add(memory);
                        // insertAnSgriteComonentAlist(scriteComposants,tmp1,tmp,listobj); 
                        //initialisationOfMaxMemUsed();
                        ////System.out.println("1. Used memory is bytes: " + MemoryPerformance.getMemoryCurrent());
                        //  System.out.println("1. Used memory is megabytes: " + MemoryPerformance.getMemoryCurrentMB());

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
        //memories = buildMemories;

        setNiveau(niveau + 1);

        //GrdItem.put("level" + getNiveau(), semantiques);
        //  mapOfLevelAndSgriteComponent.putIfAbsent(niveau, scriteComposants);
        setNumberPatterns(getNumberPatterns() + semantiques.size());

        return computeAllContengent;
    }

    /**
     * Compute support of more than 2 items size
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList<boolean[][]> grite_execution() {
        // List<ScriteComposant> scriteComposants = new ArrayList<>();
        // createGradualsItemsetsOfSize1();
        ArrayList<boolean[][]> computeAllContengent = new ArrayList<>();

        ArrayList<String[]> semantiques = new ArrayList<>();

        ArrayList<ArrayList<Integer>> isolated_matix = new ArrayList<>();
      //  ArrayList<int[]> buildMemories = new ArrayList<>();

        // ArrayList<Integer> mySupport = new ArrayList<>();
        String[] tmp1;

        ArrayList<Integer> listobj;

        ArrayList<Object> tmp2 = new ArrayList<>();

        int cpt;
        float support;

        // System.out.println(allContengent.size() + "\n");
        for (int i = 0; i < allContengent.size(); i++) {
            for (int j = i + 1; j < allContengent.size(); j++) {
                //if concatanable the 2 patterns
                if (myTools.lexicalComparaison(semantique.get(i), semantique.get(j))) {
                    tmp2 = jointure(allContengent.get(i), allContengent.get(j), isolated_matixs.get(i),
                            isolated_matixs.get(j), semantique.get(i), semantique.get(j));
                    //adj normalise matrix of fusion
                    boolean[][] tmp = (boolean[][]) tmp2.get(0);
                    buildNextCandidat(tmp, computeAllContengent, i, j, semantiques, tmp2, isolated_matix);
                    //initialisationOfMaxMemUsed();
//                    //System.out.println("1. Used memory is bytes: " + MemoryPerformance.getMemoryCurrent());
//                    System.out.println("1. Used memory is megabytes: " + MemoryPerformance.getMemoryCurrentMB());
                }
            }
        }

        allContengent.clear();
        semantique.clear();

        isolated_matixs.clear();
 //       memories = buildMemories;

        // determination frequent itemset
        allContengent = computeAllContengent;

        semantique = semantiques;

        isolated_matixs = isolated_matix;

        setNiveau(niveau + 1);
        //   mapOfLevelAndSgriteComponent.putIfAbsent(niveau, scriteComposants);

        //GrdItem.put("level" + getNiveau(), semantiques);
        setNumberPatterns(getNumberPatterns() + semantiques.size());

        return computeAllContengent;
    }

    /**
     *
     * @param tmp fusion resultant matrix
     * @param computeAllContengent1 all adjacente matrix
     * @param i index of M1
     * @param j index position position of M2
     * @param semantiques all freq courant pattern
     * @param tmp2 struct of adjM and IsolateM
     * @param isolated_matix all isolate objects
     * @param buildMemories all memory previous
     */
    public void buildNextCandidat(boolean[][] tmp, ArrayList<boolean[][]> computeAllContengent1, int i, int j, ArrayList<String[]> semantiques, ArrayList<Object> tmp2, ArrayList<ArrayList<Integer>> isolated_matix) {
        int cpt;
        float support;
        String[] tmp1;
        ArrayList<Integer> listobj;

        //TODO integration of new approach of computation of support 
        myTools.setSizeMat(tmp.length);
        myTools.initMemory();
        int[] memory = myTools.memory;
        cpt = determinerSupport(tmp, memory);
        // System.err.println("support : " + cpt);
        support = Utilitaire.supportCalculation(cpt, taille);
        if (support >= this.threshold) {
            computeAllContengent1.add(tmp);
            tmp1 = myTools.lexicalFusion(semantique.get(i), semantique.get(j));
            semantiques.add(tmp1);
            listobj = (ArrayList<Integer>) tmp2.get(1);
            isolated_matix.add(listobj);
            // buildMemories.add(memory);
            // insertAnSgriteComonentAlist(scriteComposants,tmp1,tmp,listobj); 
        }
    }

    /**
     * determiner le support d'une matrice
     *
     * @param Contengence1
     * @param memory
     * @return
     */
    public int determinerSupport1(boolean[][] Contengence1, int[] memory) {
        //int cpt = myTools.maximumSupport(Contengence1/* , attr */, memory);
        int cpt = 0;
        /* if (Utilitaire.feuillles(Contengence1).size() == 1 || Utilitaire.feuillles(Contengence1).size() == 2) {//cas coplement de feuille a 1 elet donnera 2 cas racine permute
         cpt = myTools.maximumSupport(Contengence1, memory);
         //compareTimealg1 = reasultat.compareTimealg1_graak(mat, memory);
         } else {
         cpt = Utilitaire.computeSupport(Contengence1, memory);
         }*/

        cpt = Utilitaire.computeSupport(Contengence1, memory);
        return cpt;
    }

    public int determinerSupport(boolean[][] Contengence1, int[] memory) {
        //int cpt = myTools.maximumSupport(Contengence1/* , attr */, memory);
        int cpt = 0;
        /* if (Utilitaire.feuillles(Contengence1).size() == 1 || Utilitaire.feuillles(Contengence1).size() == 2) {
         cpt = myTools.maximumSupport(Contengence1, memory);
         //compareTimealg1 = reasultat.compareTimealg1_graak(mat, memory);
         } else {
         cpt = Utilitaire.computeSupport(Contengence1, memory);
         }*/

        cpt = Utilitaire.computeSupport(Contengence1, memory);
        return cpt;
    }

    /**
     * listes tous non participant au calcul du support
     *
     * @param objet_supp
     * @return
     */
    public ArrayList<Integer> listingParticipateObjets(ArrayList<Integer> objet_supp) {
        ArrayList<Integer> malist = new ArrayList<>();
        for (int i = 0; i < SGOpt.taille; i++) {
            int p = myTools.Recherche(objet_supp, i);
            if (p != -1) {
                malist.add(p);
            }
        }
        return malist;
    }

    public ArrayList<Integer> listingComplement(ArrayList<Integer> objet_supp) {
        ArrayList<Integer> malist = new ArrayList<>();
        for (int i = 0; i < SGOpt.taille; i++) {
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
            if (res.get(i) == p) {
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
        ArrayList<Integer> l = getIsolateObjet(fusion);
        rmFinal = UnionList(total_non_obj, getEltAtPositionSet(setObjUses, l));
        fusion = MatrixNormalizer(fusion, l);
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
        SGOpt.taille = taille;
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
        SGOpt.attrList = attrList;
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
                    if (myTools.Apartient(objectremovable, i) || myTools.Apartient(objectremovable, j)) {
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
                    if (myTools.Apartient(objectremovable, i) || myTools.Apartient(objectremovable, j)) {
                        continue;
                    } else {
                        result[k][l] = m[i][j];
                        if (l < m.length - 1) {
                            l++;
                        }
                    }
                }

            }
            k++;
        }
        return result;

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
        //GritestrictAppr2 ap = new GritestrictAppr2();
        /*
        
         */
        SGOpt ap = new SGOpt(args);
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
