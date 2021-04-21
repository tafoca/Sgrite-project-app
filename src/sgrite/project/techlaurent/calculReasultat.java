package sgrite.project.techlaurent;

/**
 *
 * @author fotso
 */
public interface calculReasultat {

    //retour des resultat
    public void afficheRes(long diff1, int i);

    public void initMemory(int memory[]);

    public void initMatMemory(boolean[][] mat, int memory[]);

    //
    public void compareTime(boolean[][] mat, int memory[]);

    public Resultat compareTimealg1(boolean[][] mat, int memory[]);

    public Resultat compareTimealg1_graak(boolean[][] mat, int memory[]);
    //computeSortLeafSupport
    public Resultat compareTimealg1_trie(boolean[][] mat, int memory[]);

    public Resultat compareTimealg2(boolean[][] mat, int memory[]);

}
