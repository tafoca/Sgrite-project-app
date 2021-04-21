package sgrite.project.techlaurent.blocking;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author fotso
 */
public class WriteOutPutManager {

    /**
     *
     * @param seuil
     * @param nbitems2
     * @param nbtransaction2
     * @param duree
     * @param numberPatterns2
     * @param fw
     * @throws IOException
     */
    public void wrtiteStatistic(double seuil, int nbitems2, int nbtransaction2, double duree, int numberPatterns2,
            FileWriter fw) throws IOException {

        try {
            String sep = "       ";

            fw.write(seuil + sep + nbitems2 + sep + nbtransaction2 + sep + (duree / 1000.0) + sep + numberPatterns2
                    + "\n");
            fw.flush();

        } finally {
        }
    }

    public static void wrtiteSupportItem(double support, String item) throws IOException {
        try (FileWriter fw = new FileWriter("output.csv", true)) {
            String sep = ",";
            fw.write(item + sep + support + "\n");
            fw.flush();

        }

    }

    public static void wrtiteSupportItem(double support, String item, float seuil,String outfile) throws IOException {
        try (FileWriter fw = new FileWriter(outfile, true)) {
            String sep = ",";
            fw.write(item + sep + support + sep + seuil + "\n");
            fw.flush();

        }

    }

}
