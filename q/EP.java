package q;
import c.*;

import java.util.ArrayList;

public class EP implements Coordinate {
    public static String NAME;
    static {
        NAME = "EP";
    }
    public EP() {}
    public int value(Cube cube){
        int[] ep = cube.getEP();
        ArrayList<Integer> eCheckList = new ArrayList<Integer>();
        for(int m = 0; m < 12; m ++) {eCheckList.add(m);}
        int edge = ep[0];
        int rtrn = edge;
        eCheckList.remove((Integer) edge);
        for(int n = 1; n < 11; n ++) {
            rtrn *= 12 - n; // may be 12
            edge = ep[n];
            rtrn += eCheckList.indexOf(edge);
            eCheckList.remove((Integer) edge);
        }
        return rtrn;
    }
    public String name() {return NAME;}
}