package q;
import c.*;

public class RCO implements Coordinate, RawCoord {
    public static String NAME;
    static {NAME = "RCO";}

    public RCO() {}
    /*rotational corner orientation and rotational edge orientation takes into account
     * if the pieces is oriented from all angles. this is actually determined by the piece's
     * permutation in ways described in their respective methods.
     * RCO: a corner can be in one of two pemutation types:
     * 1: FUR, BDL, FDL, BDR. 2: FUL, BUL, FDR, BDR.
     * when a corner in its own permutation type will be oriented the same regardless of rotation, while
     * corner out of its slot will have differing orientation depending on rotation
     * correctly indexing all possible values of RCO requires using binomial coefficients, and
     * was altered from https://rosettacode.org/wiki/Evaluate_binomial_coefficients#Java*/
    public int value(Cube cube) {
        int[] co = cube.getCO();
        int[] cp = cube.getCP();
        int rtrn = 0;
        int i = 7;
        int k = 3;
        while(i >= 0 && k >= 0) {
            if(cp[i] < 4) {
                int binoCoef = 1;
                for (int j = 1, m = i; j <= k; j++, m--) {binoCoef = binoCoef * m / j;}
                rtrn += binoCoef;
            }
            else {k --;}
            i --;
        }
        rtrn *= 2187;
        rtrn += (new CO()).value(cube);
        return rtrn;
    }
    public int rotate(Cube cube, int rotation) {
        cube.rotateCO(rotation);
        cube.rotateCP(rotation);
        return value(cube);
    }
    public String name() {return NAME;}
}
