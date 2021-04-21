/*
 */
package sgrite.project.techlaurent.Base.composants;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fotso
 */
public class VirtualizePosition {

    ArrayList<Integer> effObjetM1;
    ArrayList<Integer> effObjetM;
    
    List<Kernel> coupleeffObjeM1; 
    List<Kernel> coupleeffObjeM;

    int i; //contient la position de o dans effObjetM1 et -1 sinon
    int j; //contient la position de o dans effObjetM et -1 sinon

    public VirtualizePosition(ArrayList<Integer> effObjetM1, ArrayList<Integer> effObjetM) {
        this.effObjetM1 = effObjetM1;
        this.effObjetM = effObjetM;
    }

    public VirtualizePosition(List<Kernel> coupleeffObjeM1, List<Kernel> coupleeffObjeM) {
        this.coupleeffObjeM1 = coupleeffObjeM1;
        this.coupleeffObjeM = coupleeffObjeM;
    }

   

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public VirtualizePosition getReelPosition(Integer o) {
        // CouplePosition couplePosition = new CouplePosition();
//        couplePosition.i = effObjetM1.indexOf(o);
//        couplePosition.j = effObjetM.indexOf(o);
        i = effObjetM1.indexOf(o);
        j = effObjetM.indexOf(o);
        return this;
    }

    /**
     * recupere les index des objets dans l'ensemble d'objets
     *
     * @param ensembles
     * @param objets
     * @return
     */
    public static List<Integer> getIndexes(List<Integer> ensembles, List<Integer> objets) {
        List<Integer> l = new ArrayList<>();
        objets.stream().forEach(o -> {
            l.add(ensembles.indexOf(o));
        });
        return l;
    }
    
    
     public static List<Integer> getIndexesCouple(List<Kernel> ensembles, List<Integer> objets) {
        List<Integer> l = new ArrayList<>();
        int beginAt, p = 0;
        beginAt = p;
        for (Integer o : objets) {
            p = indexOfBeginAtPositionCouple(ensembles, o, beginAt);
            if (p != -1) {
                beginAt = p;
            }
            l.add(p);
        }
        return l;
    }


    //utile pour la recherche ou les objets dont on cherche les indices sont ordonnees
    public static List<Integer> getIndexesPosition(List<Integer> ensembles, List<Integer> objets) {
        List<Integer> l = new ArrayList<>();
        int beginAt, p = 0;
        beginAt = p;
        for (Integer o : objets) {
            p = indexOfBeginAtPosition(ensembles, o, beginAt);
            if (p != -1) {
                beginAt = p;
            }
            l.add(p);
        }
        return l;
    }

    public static List<Kernel> getSetKernel(List<Integer> ensembles, List<Integer> objets) {
        List<Kernel> l = new ArrayList<>();
        objets.stream().forEach(o -> {
            l.add(new Kernel(o, ensembles.indexOf(o)));
        });
        return l;
    }

    /**
     * recherche dans l'ensemble a partit de la position beginPosition et
     * retourne la position de l'objet ou -1 sinon
     *
     * @param ensemble
     * @param node
     * @param beginPosition
     * @return
     */
    static int indexOfBeginAtPosition(List<Integer> ensemble, Integer node, int beginPosition) {
        int position = -1;
        for (int k = beginPosition; k < ensemble.size(); k++) {
            if (ensemble.get(k).equals(node)) {
                position = k;
                break;
            }
        }
        return position;
    }
    
    static int indexOfBeginAtPositionCouple(List<Kernel> ensemble, Integer node, int beginPosition) {
        int position = -1;
        for (int k = beginPosition; k < ensemble.size(); k++) {
            if (ensemble.get(k).getNode().equals(node)) {
                position = k;
                break;
            }
        }
        return position;
    }

    List<Integer> nodeInList1notIn2(List<Integer> feuilllesM, List<Integer> feuilllesM1) {
        List<Integer> res = new ArrayList<>();
        feuilllesM.stream().forEach((Integer e) -> {
            if (!feuilllesM1.contains(e)) {
                res.add(e);
            }
        });
        return res;
    }
    
    

    @Override
    public String toString() {
        return "VirtualizePosition{" + "effObjetM1=" + effObjetM1 + ", effObjetM=" + effObjetM + ", i=" + i + ", j=" + j + '}';
    }

    /* public static void main(String[] args) {
     ArrayList<Integer> effObjetM1 = new ArrayList<>();
     ArrayList<Integer> effObjetM = new ArrayList<>();
     //        effObjetM1.add(0);
     //        effObjetM1.add(2);
     //        effObjetM1.add(3);
     //        effObjetM1.add(1);
     //        effObjetM.add(3);
     //        effObjetM.add(2);
     //        effObjetM.add(0);

     VirtualizePosition instance = new VirtualizePosition(effObjetM1, effObjetM);
     // System.out.println(" o case : " + instance.getReelPosition(0).toString());
     // System.out.println(" 2 case : " + instance.getReelPosition(2).toString());
     // System.out.println(" 3 case : " + instance.getReelPosition(3).toString());
     //System.out.println(" 1 case : " + instance.getReelPosition(1).toString());
     //System.out.println(" feuilles nouvelles : " + instance.nodeInList1notIn2(effObjetM1, effObjetM));
     int i = 0;
     int n = 1000;
     while (i <= n) {
     effObjetM1.add(i);
     i++;
     }
     i = 0;
     while (i <= n) {
     effObjetM.add(i);
     i += 2;
     }
        
     /*
     List<Integer> re = VirtualizePosition.getIndexes(effObjetM1, effObjetM);
     System.out.println(" feuilles index a prendre getIndexes : " + re + "\n (" + re.size() + ")");

     //List<Integer> re1 = VirtualizePosition.getIndexesPosition(effObjetM1, effObjetM);
     // System.out.println(" feuilles index a prendre getIndexes : " + re1+ "\n (" +re1.size()+")");
        
     for (int j = 0; j < 1000; j++) {
     List<Integer> re = VirtualizePosition.getIndexes(effObjetM1, effObjetM);
     System.out.println(" feuilles index a prendre getIndexes : "+ " (" + re.size() + ")");

     //List<Integer> re1 = VirtualizePosition.getIndexesPosition(effObjetM1, effObjetM);
     // System.out.println(" feuilles index a prendre getIndexes : " + + "(" +re1.size()+")");
     }

     }*/
}
