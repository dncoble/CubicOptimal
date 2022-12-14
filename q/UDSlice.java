package q;
import c.*;

import java.util.ListIterator;

/* The UDSlide coordinate, along with EO and CO, describes the kociemba coordinate as the tuple (CO, EO, UDSlice).
 * The UD slice coordinate itself represents the placement of where the UD edges are on the cube.
 */
public class UDSlice implements Coordinate {
    private EP ep;
    private static String NAME;
    private static int SIZE;
    static {
        NAME = "UDSlice";
        SIZE = 495;
    }
    
    public UDSlice(Cube cube) {ep = new EP(cube);}
    public UDSlice() {ep = new EP();}
    public void set(Cube cube) {ep = new EP(cube);}
    /* order of EP is UR DR DL UL FR FL BL BR FU BU BD FD, so UD slice edges are edges 4-7. */
    public int value(int[] epArr) {
        int value = 0;
        int k = 3;
        int n = 11;
        while(k >= 0) {
            if(epArr[n] >=4 && epArr[n] <= 7) {
                k -= 1;
            }
            else {
                int binoCoef = 1;
                for(int i = 1, m = n; i <= k; i++, m--) {binoCoef = binoCoef * m / i;}
                value += binoCoef;
            }
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
