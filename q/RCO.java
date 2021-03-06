package q;
import c.*;

public class RCO implements Coordinate {
    public static String NAME;
    private Coordinate co;
    static {
        NAME = "RCO";
    }

    public RCO() {
        co = new CO();
    }
    /* rotatable corner orientation and rotatable edge orientation takes into account
     * if the pieces is oriented from all angles. this is actually determined by the piece's
     * permutation in ways described in their respective methods.
     * RCO: a corner can be in one of two pemutation types:
     * 1: FUR, BDL, FDL, BDR. 2: FUL, BUL, FDR, BDR.
     * when a corner in its own permutation type will be oriented the same regardless of rotation, while
     * corner out of its slot will have differing orientation depending on rotation
     * correctly indexing all possible values of RCO requires using binomial coefficients, and
     * was altered from https://rosettacode.org/wiki/Evaluate_binomial_coefficients#Java*/
    public int value(Cube cube) {
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
        rtrn += co.value(cube);
        return rtrn;
    }
    public String name() {return NAME;}
}
