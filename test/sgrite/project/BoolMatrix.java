package sgriteproject.SGrite.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * class de matrice booleen implemnete par les tableau dynamique vecteur; sous
 * la forme unidimensionnel taille de la matrice, constructeur, affectation a
 * une position de valeur en consultation et en modification, verification ligne
 * null ou tous les elts sont a false
 *
 * @author fotso
 */
public class BoolMatrix {

    private int N;
    List<Boolean> content;

    /**
     * Create a new boolean matrix of size NxN
     *
     * @param N num of rows or num of cols.
     * @param fill the matrix with this value.
     */
    public BoolMatrix(int N, boolean fill) {
        this.N = N;
        this.content = new ArrayList<>(N);
        for (int i = 0; i < this.content.size(); i++) {
            this.content.add(i, fill);
        }
    }

    public BoolMatrix(boolean fill[][]) {
        this.N = fill.length;
        this.content = new ArrayList<>(fill.length * fill.length);
        for (int i = 0; i < fill.length; i++) {
            for (int j = 0; j < fill.length; j++) {
                this.set_value(i, i, fill);
            }
        }

    }

    /**
     * \brief Assignment operator. / surchage de l'affecttion
     *
     * @param other
     * @return
     */
    public BoolMatrix operatorAffecter(BoolMatrix other) {
        this.N = other.N;
        this.content.clear();
        this.content.addAll(other.content);
        return this;
    }

    /**
     * \brief Set matrix value [\col, \row] to \v. affectation du booleen v a
     * M[row,col] = b car (row,col)----> row +N*col
     *
     * @param col
     * @param row
     */
    public void set_value(int col, int row, boolean[][] mat) {
        this.content.add(col * N + row, mat[row][col]);
    }

    /**
     * \brief Return matrix value at [\col, \row].
     */
    public boolean get_value(int col, int row) {
        return this.content.get(col * N + row);
    }

    /**
     * \brief Return true if no bit is set in Row \row. / recherche d'une
     * feuille ds la matrice adjacence
     *
     * @param row
     * @return
     */
    public boolean null_row_p(int row) {
        assert (row < N);
        int range_start = N * row;//inndice concerne dans notre vecteur 1er elt de la ligne
        int range_end = N * (row + 1);//inndice dans notre vecteur du dernier elt de la ligne en question
        for (int i = range_start; i < range_end; i++) {
            if (this.content.get(i)) {
                return false;
            }
        }
        return true;
    }
    

}
