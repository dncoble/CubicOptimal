package q;
import c.*;

import java.util.ListIterator;

/* The idea behind RCO and REO is to modify CO and EO coordinates to contain enough information so that rotation can be
 * performed. To do that, some information from permutation must be included. RCP and REP contain that amount of 
 * information. Then, RCO and REO can be implemented as multicoordinates with CO, CP and RCP, REP.
 */
public class RCP implements Coordinate {
    private CP cp;
    private static String NAME;
    private static int SIZE;
    static {
        NAME = "RCP";
        SIZE = 70;
    }
    
    public RCP(Cube cube) {cp = new CP(cube);}
    public RCP() {cp = new CP();}
    public void set(Cube cube) {cp = new CP(cube);}
    
    /* rotatable corner orientation and rotatable edge orientation takes into account
     * if the pieces is oriented from all angles. this is actually determined by the piece's
     * permutation in ways described in their respective methods.
     * RCO: a corner can be in one of two pemutation types:
     * 1: FUR, BDL, FDL, BDR. 2: FUL, BUL, FDR, BDR.
     * when a corner in its own permutation type will be oriented the same regardless of rotation, while
     * corner out of its slot will have differing orientation depending on rotation
     * correctly indexing all possible values of RCO requires using binomial coefficients, and
     * was altered from https://rosettacode.org/wiki/Evaluate_binomial_coefficients#Java*/
    public int value(int cpArr[]) {
        int value = 0;
        int i = 7;
        int k = 3;
        while(i >= 0 && k >= 0) {
            if(cpArr[i] < 4) {
                int binoCoef = 1;
                for (int j = 1, m = i; j <= k; j++, m--) {binoCoef = binoCoef * m / j;}
                value += binoCoef;
            }
            else {k --;}
            i --;
        }
        return value;
    }
    public int value(Cube cube) {return value((new CP(cube)).getArray());}
    public int value() {return value(cp.getArray());}
    
    public void move(int move) {
        cp.move(move);
    }
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            move(iter.next());
        }
    }
    
    public String name() {return NAME;}
    public int size() {return SIZE;}
}