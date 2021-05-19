package q;
import c.*;

import java.util.ArrayList;

public class REO implements Coordinate, RawCoord {
    public static String NAME;
    static {NAME = "REO";}

    public REO() {}
    /* an edge can be in one of 3 slice layers:
     * 1: UR, DR, DL, UL. 2: FR, FL, BL, BR. 3: FU, BU, BD, FD.
     * if an edge is in its own slice layer, it will be oriented the same from
     * all angles, while if it is out of its slice layer, it will be oriented
     * different orientations depending on rotation.
     * just as with RCO, REO requires binomial coefficients to correctly index,
     * but it is slightly more complicated since there are 3 different types of edges */
    public int value(Cube cube) {
        int[] eo = cube.getEO();
        int[] ep = cube.getEP();
        int rtrn = 0;
        int i = 11;
        int k = 3;
        ArrayList<Integer> toList = new ArrayList<Integer>();
        for(int j = 0; j < 12; j ++) {toList.add(ep[j]);}
        while(i >= 0 && k >= 0) {
            if(ep[i] < 8) {
                int binoCoef = 1;
                for (int j = 1, m = i; j <= k; j++, m--) {binoCoef = binoCoef * m / j;}
                rtrn += binoCoef;
            }
            else {
                k --;
                toList.remove((Integer) ep[i]);
            }
            i --;
        }
//		while(i >= 0) {toList.remove((Integer) EP[i]); i --;}
        i = 7;
        k = 3;
        rtrn *= 70;
        while(i >= 0 && k >= 0) {
            if(toList.get(i) < 4) {
                int binoCoef = 1;
                for (int j = 1, m = i; j <= k; j++, m--) {binoCoef = binoCoef * m / j;}
                rtrn += binoCoef;
            }
            else {k --;}
            i --;
        }
        rtrn *= 2048;
        rtrn += (new EO()).value(cube);
        return rtrn;
    }
    public int rotate(Cube cube, int rotation) {
        cube.rotateEO(rotation);
        cube.rotateEP(rotation);
        return value(cube);
    }
    public String name() {return NAME;}
}
