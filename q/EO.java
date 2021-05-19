package q;
import c.*;

public class EO implements Coordinate, RawCoord {
    public static String NAME;
    private static int MAX_SIZE;
    static {
        NAME = "EO";
        MAX_SIZE = 2047;
    }
    /* EO is calculated in much a similar way to CO, but each edge only has two possible
     * orientations, and there are 12 edges in total. with the final edge excluded because
     * of edge flip parity, it is an 11 digit base 2 number. */
    public EO() {}
    public int value(Cube cube){
        int[] eo = cube.getEO();
        int rtrn = eo[0];
        for(int j = 1; j < 11; j++) {
            rtrn *= 2;
            rtrn += eo[j];
        }
        return rtrn;
    }
    public int rotate(Cube cube, int rotation) {
        cube.rotateEO(rotation);
        return value(cube);
    }
    public void setCoord(Cube cube, int value) {cube.eoFromInt(value);}
    public String name() {return NAME;}
    public int size() {return MAX_SIZE;}
}