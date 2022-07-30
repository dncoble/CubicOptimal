package q;
import c.*;

import java.util.ArrayList;

public class CP implements Coordinate {
    private static String NAME;
    static {
        NAME = "CP";
    }
    public CP() {}
    /*the concept of turning an int[] into an int with a base system is also used in
     * since for every index in the permutation, the options are reduced by one, it is effectively
     * a base system where each subsequent place value has one less power. (i.e. corners are 765432)
     * they are converted by saying input value is index in an ArrayList which starts out with all values
     * (either 0-7 or 0-11) and when a piece is put in removing it from the list
     * the final corner/edge is excluded because it is forced. */
    public int value(Cube cube) {
        int[] cp = cube.getCP();
        ArrayList<Integer> cCheckList = new ArrayList<Integer>();
        for(int k = 0; k < 8; k ++) {cCheckList.add(k);}
        int corner  = cp[0];
        int rtrn = corner;
        cCheckList.remove((Integer) corner);
        for(int l = 1; l < 7; l ++) {
            rtrn *= 8 - l; // may be 8
            corner = cp[l];
            rtrn += cCheckList.indexOf(corner);
            cCheckList.remove((Integer) corner);
        }

        return rtrn;
    }
    /* a proposed value() which should work much faster by using base-8 numbers and bitshift. 
     * the result being that all coords are not filled between 0 and max value, and solved is not 0 */
    
//    public int value(Cube cube) {
//        int rtrn = 0;
//        int[] cp = cube.getCP();
//        for(int i = 0; i < 8; i ++) {
//            rtrn += cp[i];
//            rtrn <<= 3;
//        }
//        return rtrn;
//    }
    
    public String name() {return NAME;}
}