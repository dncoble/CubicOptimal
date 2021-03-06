package q;
import c.*;

public class CO implements Coordinate {
    private static String NAME;
    static{
        NAME = "CO";
    }
    public CO() {}
    /* the following methods turn each int[] coordinate int a single integer coordinate
     * which is useful is saving tables of scrambles to permutation, since a single integer
     * is much smaller than a list of integers.
     * CO can be thought of as an 8 digit base three number. actually, because of corner twist
     * parity, it can thought of as a 7 digit number, since the final CO is forced.
     * for this reason, the CO[7] is not included in calculation */
    public int value(Cube cube) {
        int[] co = cube.getCO();
        int rtrn = co[0];
        for(int i = 1; i < 7; i ++) {
            rtrn *= 3;
            rtrn += co[i];
        }
        return rtrn;
    }
//    public Cube setCoord(Cube cube, int value) {cube.coFromInt(value); return cube;}
    public String name() {return NAME;}
}
