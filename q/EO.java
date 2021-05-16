package q;
import c.*;

public class EO implements Coordinate {
    /* EO is calculated in much a similar way to CO, but each edge only has two possible
     * orientations, and there are 12 edges in total. with the final edge excluded because
     * of edge flip parity, it is an 11 digit base 2 number. */
    public static int value(Cube cube){
        int[] eo = cube.getEO();
        int rtrn = eo[0];
        for(int j = 1; j < 11; j++) {
            rtrn *= 2;
            rtrn += eo[j];
        }
        return rtrn;
    }
}