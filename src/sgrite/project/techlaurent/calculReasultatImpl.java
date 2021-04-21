package sgrite.project.techlaurent;

import sgrite.project.techlaurent.Base.Tools;

/**
 *
 * @author fotso
 */
public class calculReasultatImpl implements calculReasultat {

    public Tools tools;
    // public Utilitaire utilitaire;

    public calculReasultatImpl() {
    }

    public calculReasultatImpl(Tools tools) {
        this.tools = tools;
        //  this.utilitaire = utilitaire;
    }

    @Override
    public void afficheRes(long diff1, int i) {
        System.out.println("------------------------------------------");
        System.out.println("difference " + i + " : " + diff1);
        System.out.println("------------------------------------------");
    }

    @Override
    public void initMemory(int[] memory) {
        for (int i = 0; i < memory.length; i++) {
            memory[i] = -1;
        }
    }

    @Override
    public void initMatMemory(boolean[][] mat, int[] memory) {
        //TODO initialiser matrice courrante et memory

        initMemory(memory);
    }

    @Override
    public void compareTime(boolean[][] mat, int[] memory) {
        initMatMemory(mat, memory);

        long start1 = System.currentTimeMillis();
        int max1 = Utilitaire.computeSupport(mat, memory);
        long end1 = System.currentTimeMillis();

        Tools tools = new Tools();
        tools.setSizeMat(mat.length);
        tools.initMemory();

        long start2 = System.currentTimeMillis();
        int max2 = tools.maximumSupport(mat, tools.getMemory());
        long end2 = System.currentTimeMillis();

        long diff1 = end1 - start1;
        long diff2 = end2 - start2;

        afficheRes(diff1, 1);
        afficheRes(diff2, 2);
    }

    @Override
    public Resultat compareTimealg1(boolean[][] mat, int[] memory) {
        initMatMemory(mat, memory);
        long start1 = System.currentTimeMillis();
        int max1 = Utilitaire.computeSupport(mat, memory);
        long end1 = System.currentTimeMillis();
        long diff1 = end1 - start1;

        //afficheRes(diff1, 1);
        return new Resultat(diff1, max1);

    }

    @Override
    public Resultat compareTimealg2(boolean[][] mat, int[] memory) {
        //Tools tools = new Tools();
        tools.setSizeMat(mat.length);
        tools.initMemory();

        long start2 = System.currentTimeMillis();
        int max2 = tools.maximumSupport(mat, tools.getMemory());
        long end2 = System.currentTimeMillis();

        long diff2 = end2 - start2;

        // afficheRes(diff2, 2);
        return new Resultat(diff2, max2);

    }

    @Override
    public Resultat compareTimealg1_graak(boolean[][] mat, int[] memory) {
        int som = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (i != j && mat[i][j]) {
                    som += 1;
                }

            }

        }
        int diviseur = mat.length - 1;
        diviseur /= 2;
        som = som / diviseur;
        long end = System.currentTimeMillis();

        long diff = end - start;

        return new Resultat(diff, som);
    }

    @Override
    public Resultat compareTimealg1_trie(boolean[][] mat, int[] memory) {
        initMatMemory(mat, memory);
        long start1 = System.currentTimeMillis();
        int max1 = Utilitaire.computeSortLeafSupport(mat, memory);
        long end1 = System.currentTimeMillis();
        long diff1 = end1 - start1;

        //afficheRes(diff1, 1);
        return new Resultat(diff1, max1);

    }

}
