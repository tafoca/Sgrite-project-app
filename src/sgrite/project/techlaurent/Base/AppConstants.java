package sgrite.project.techlaurent.Base;
/**
 *
 * @author fotso
 */
public class AppConstants {
    public static final String TRANSACTIONFILE = "test.dat";//bdsalaire.csv C250-A100-50.dat meteo3-4-11-2020_600Li.dat TMET10I10.dat C250-A100-50.dat;winequality-red.data"dataset4.dat";//; "F30Att100Li.dat" ;"F20Att200Li.dat";//"dataset4.dat";//test.dat";
    public static final double MIN = (float) 0.05;//0.05 0.04  0.02 0.05  0.02; 0.03;0.08
    public static final double MAX = (float) 0.21;//0.16 0.21 0.32 0.8, 0.5; 0.6
    public static final double STEP = (float) 0.01;//0.01
    public static final double THREHOLD = (float) 0.05;// 0.02 0.1 0.15;;0.05 ;0.03; 0.01; 0.09
    public static String OUPUTMFSFILE = "TimeMPSGrite";
    public static String SEP = ";";
    public static int NBMAXEXTRAIT = 0; //10
    public static final int MEMORY = 8;
    public static int NBTRANS = 0;
    public static int NBTITEMPART1=35;//8 20;//8 //10
    public static int NBTITEMPART2=28;//4 7;//2 //2
    //memory used saving
     public static double USEDMEMORY=0;//7;//2 //2
}
