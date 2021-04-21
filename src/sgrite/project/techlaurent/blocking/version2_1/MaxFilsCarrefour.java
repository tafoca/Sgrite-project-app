package sgrite.project.techlaurent.blocking.version2_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fotso
 */
public class MaxFilsCarrefour {
    

    boolean status;
    int maximum;

    public MaxFilsCarrefour() {
    }

    public MaxFilsCarrefour(boolean status, int maximum) {
        this.status = status;
        this.maximum = maximum;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    //2/11/2019
    /**
     * approche navive a
     *
     * @param node current node
     * @param m binary matrix
     * @param memory memory table of distance
     * @return
     */
    MaxFilsCarrefour isNotValidCarrefour(int node, boolean[][] m, int[] memory) {
        boolean isntValid = false;
        int max = -1;
        for (int i = 0; i < m.length; i++) {
            final boolean m1 = m[node][i];
            final int memory1 = memory[i];
            if (m1 && memory1 == -1) {
                isntValid = true;
                break;
            }
            //recherche du maximum des fils
            if (m1 && memory1 != -1) {
                if (memory1 > max) {
                    max = memory[i];
                }

            }
        }

        return new MaxFilsCarrefour(isntValid, max);
    }
    // definir une approche previsionnel qui construit iune map dynanique pour orienter le choix 
    //sans parcourt entier de tous lensemble des noeuds donc de complexite O(n) -> O(1)
    /**
     * 
     * @param node note of traitment (case adscendent progress it is the common parent node)
     * @param m binary matrix
     * @param memory  
     * @param sons list of son node
     * @return 
     */
    MaxFilsCarrefour isNotValidCarrefour(int node, boolean[][] m, int[] memory, List<Integer> sons) {
        boolean isntValid = false;
        int max = -1;
        for (Integer son : sons) {
            final int memory1 = memory[(int) son];
            if (memory1 == -1) {
                isntValid = true;
                break;
            }
            //recherche du maximum des fils
            if (memory1 != -1) {
                if (memory1 > max) {
                    max = memory[(int) son];
                }
            }
        }
        return new MaxFilsCarrefour(isntValid, max);
    }
    
    MaxFilsCarrefour isNotValidCarrefour(int node, boolean[][] m, int[] memory,Map<Integer, List<Integer>> sonsOfAllNode) {
        boolean isntValid = false;
        int max = -1; 
        List<Integer> sons = new ArrayList<>(sonsOfAllNode.get((Integer)node));
        for (Integer son :sons) {
            final int memory1 = memory[(int) son];
            if (memory1 == -1) {
                isntValid = true;
                break;
            }
            //recherche du maximum des fils
            if (memory1 != -1) {
                if (memory1 > max) {
                    max = memory[(int) son];
                   
                } 
                //retirer des noeud fils ce infrieur au max actuel ils sont sans interet pour la suite
                if (memory1 < max) {
                    sonsOfAllNode.get((Integer)node).remove((Integer)son);
                    if (sonsOfAllNode.get((Integer)node).isEmpty()) {
                        sonsOfAllNode.remove((Integer)node);
                    }
                }
                
            }
        }
        return new MaxFilsCarrefour(isntValid, max);
    }
    
    /**
     * 
     * @param node
     * @param m
     * @param memory tab des distance a une feuille quelconque du noeud node
     * @param sonsOfAllNode map dynamique noeuds et ces fils
     * @return 
     */
     boolean isNotValidCarrefourre(int node, boolean[][] m, int[] memory,Map<Integer, List<Integer>> sonsOfAllNode) {
        boolean isntValid = false;
        int max = -1; 
        List<Integer> sons = new ArrayList<>(sonsOfAllNode.get((Integer)node));
        for (Integer son :sons) {
            final int memory1 = memory[(int) son];
            if (memory1 == -1) {
                isntValid = true;
                break;
            }else{
                 sonsOfAllNode.get((Integer)node).remove((Integer)son);
                    if (sonsOfAllNode.get((Integer)node).isEmpty()) {
                        sonsOfAllNode.remove((Integer)node);
                    }
            }
        }
        return isntValid;
    }
 
    
}
