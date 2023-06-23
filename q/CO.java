package q;
import c.*;

import java.util.ListIterator;

public class CO extends Coordinate {
    public int[] co;
    private static String NAME;
    private static int SIZE;
    static{
        NAME = "CO";
        SIZE = 2187;
    }
    public CO(Cube cube) {co = cube.getCO();}
    public CO() {co = new int[] {0,0,0,0,0,0,0,0};}
    public CO(int[] co) {this.co = co;}
    public void set (Cube cube) {co = cube.getCO();}
    /* the following methods turn each int[] coordinate int a single integer coordinate
     * which is useful is saving tables of scrambles to permutation, since a single integer
     * is much smaller than a list of integers.
     * CO can be thought of as an 8 digit base three number. actually, because of corner twist
     * parity, it can thought of as a 7 digit number, since the final CO is forced.
     * for this reason, the CO[7] is not included in calculation */
    public int value(int[] co) {
        int rtrn = co[0];
        for (int i = 1; i < 7; i++) {
            rtrn *= 3;
            rtrn += co[i];
        }
        return rtrn;
    }
    public int value(Cube cube) {return value(cube.getCO());}
    public int value() {return value(co);}
    
    public void move(int move) {
        int type = move / 3;
        int dir = move % 3;
        int[] cycle;
        if(type == 0) {cycle = new int[]{0, 6, 2, 4};}
        else if(type == 1) {cycle = new int[]{0, 4, 1, 5};}
        else if(type == 2) {cycle = new int[]{6, 0, 5, 3};}
        else if(type == 3) {cycle = new int[]{3, 5, 1, 7};}
        else if(type == 4) {cycle = new int[]{6, 3, 7, 2};}
        else {cycle = new int[]{4, 2, 7, 1};}

        if(dir == 0) {
            int saver = co[cycle[0]];
            co[cycle[0]] = co[cycle[3]];
            co[cycle[3]] = co[cycle[2]];
            co[cycle[2]] = co[cycle[1]];
            co[cycle[1]] = saver;
        }
        else if(dir == 1) {
            int saver0 = co[cycle[0]];
            int saver1 = co[cycle[1]];
            co[cycle[0]] = co[cycle[2]];
            co[cycle[1]] = co[cycle[3]];
            co[cycle[2]] = saver0;
            co[cycle[3]] = saver1;
        }
        else {
            int saver = co[cycle[0]];
            co[cycle[0]] = co[cycle[1]];
            co[cycle[1]] = co[cycle[2]];
            co[cycle[2]] = co[cycle[3]];
            co[cycle[3]] = saver;
        }

        if(dir != 1 && !(type == 1 || type == 4)) {
            co[cycle[0]] = (co[cycle[0]] + 1) % 3;
            co[cycle[1]] = (co[cycle[1]] + 2) % 3;
            co[cycle[2]] = (co[cycle[2]] + 1) % 3;
            co[cycle[3]] = (co[cycle[3]] + 2) % 3;
        }
    }
    public CO clone() {return new CO(co);}
    
    public String name() {return NAME;}
    public int size() {return SIZE;}
}
