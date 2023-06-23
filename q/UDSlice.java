package q;
import c.*;

import java.util.ListIterator;

/* The UDSlide coordinate, along with EO and CO, describes the kociemba coordinate as the tuple (CO, EO, UDSlice).
 * The UD slice coordinate itself represents the placement of where the UD edges are on the cube.
 */
public class UDSlice extends Coordinate {
    private EP ep;
    private static String NAME;
    private static int SIZE;
    static {
        NAME = "UDSlice";
        SIZE = 495;
    }
    
    public UDSlice(Cube cube) {ep = new EP(cube);}
    public UDSlice() {ep = new EP();}
    public UDSlice(EP ep) {this.ep = ep;}
    public void set(Cube cube) {ep = new EP(cube);}
    /* order of EP is UR DR DL UL FR FL BL BR FU BU BD FD, so UD slice edges are edges 4-7. */
    public int value(int[] epArr) {
        int value = 0;
        int k = 3;
        int n = 11;
        boolean[] occupied = new boolean[12];
        // must reorder ep so that ud slice comes last, to preserve 0=solved
        for(int i = 0; i < 8; i ++) {occupied[i] = epArr[i+4] >= 4 && epArr[i+4] <= 7;}
        for(int i = 8; i < 12; i ++) {occupied[i] = epArr[i-8] >= 4 && epArr[i-8] <= 7;}
        while(k >= 0) {
            if(occupied[n]) {
                k -= 1;
            }
            else {
                int binoCoef = 1;
                for(int i = 1, m = n; i <= k; i++, m--) {binoCoef = binoCoef * m / i;}
                value += binoCoef;
            }
            n -= 1;
        }
        return value;
    }
    public int value(Cube cube) {return value((new EP(cube)).getArray());}
    public int value() {return value(ep.getArray());}
    
    public void move(int move) {ep.move(move);}
    
    public UDSlice clone() {return new UDSlice(ep.clone());}
    public String name() {return NAME;}
    public int size() {return SIZE;}
}