package q;
import c.*;

import java.util.ArrayList;
import java.util.ListIterator;

/* The idea behind RCO and REO is to modify CO and EO coordinates to contain enough information so that rotation can be
 * performed. To do that, some information from permutation must be included. RCP and REP contain that amount of
 * information. Then, RCO and REO can be implemented as multicoordinates with CO, CP and RCP, REP.
 */
public class REP implements Coordinate {
    private EP ep;
    private static String NAME;
    private static int SIZE;
    static {
        NAME = "REP";
        SIZE = 34650;
    }
    
    public REP(Cube cube) {ep = new EP(cube);}
    public REP() {ep = new EP();}
    public void set(Cube cube) {ep = new EP(cube);}
    
    /* an edge can be in one of 3 slice layers:
     * 1: UR, DR, DL, UL. 2: FR, FL, BL, BR. 3: FU, BU, BD, FD.
     * if an edge is in its own slice layer, it will be oriented the same from
     * all angles, while if it is out of its slice layer, it will be oriented
     * different orientations depending on rotation.
     * just as with RCO, REO requires binomial coefficients to correctly index,
     * but it is slightly more complicated since there are 3 different types of edges */
    public int value(int[] epArr) {
        int value = 0;
        int i = 11;
        int k = 3;
        ArrayList<Integer> toList = new ArrayList<Integer>();
        for(int j = 0; j < 12; j ++) {toList.add(epArr[j]);}
        while(i >= 0 && k >= 0) {
            if(epArr[i] < 8) {
                int binoCoef = 1;
                for (int j = 1, m = i; j <= k; j++, m--) {binoCoef = binoCoef * m / j;}
                value += binoCoef;
            }
            else {
                k --;
                toList.remove((Integer) epArr[i]);
            }
            i --;
        }
        i = 7;
        k = 3;
        value *= 70;
        while(i >= 0 && k >= 0) {
            if(toList.get(i) < 4) {
                int binoCoef = 1;
                for (int j = 1, m = i; j <= k; j++, m--) {binoCoef = binoCoef * m / j;}
                value += binoCoef;
            }
            else {k --;}
            i --;
        }
        return value;
    }
    public int value(Cube cube) {return value((new EP(cube)).getArray());}
    public int value() {return value(ep.getArray());}
    
    public void move(int move) {
        ep.move(move);
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
