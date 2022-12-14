package q;
import c.*;

import java.util.ListIterator;

public class EO implements Coordinate {
    private int[] eo;
    private static String NAME;
    private static int SIZE;
    static {
        NAME = "EO";
        SIZE = 2048;
    }
    /* EO is calculated in much a similar way to CO, but each edge only has two possible
     * orientations, and there are 12 edges in total. with the final edge excluded because
     * of edge flip parity, it is an 11 digit base 2 number. */
    public EO(Cube cube) {eo = cube.getEO();}
    public EO() {eo = new int[] {0,0,0,0,0,0,0,0,0,0,0,0};}
    public void set(Cube cube) {eo = cube.getEO();}
    public int value(int[] eo){
        int rtrn = eo[0];
        for(int j = 1; j < 11; j++) {
            rtrn *= 2;
            rtrn += eo[j];
        }
        return rtrn;
    }
    public int value(Cube cube) {return value(cube.getEO());}
    public int value() {return value(eo);}
    public void move(int move) {
        int type = move / 3;
        int[] cycle;
        if(type == 0) {cycle = new int[]{8, 4, 11, 5};}
        else if(type == 1) {cycle = new int[]{8, 3, 9, 0};}
        else if(type == 2) {cycle = new int[]{4, 0, 7, 1};}
        else if(type == 3) {cycle = new int[]{9, 6, 10, 7};}
        else if(type == 4) {cycle = new int[]{11, 1, 10, 2};}
        else {cycle = new int[]{3, 5, 2, 6};}

        if(move % 3 == 0) {
            int saver = eo[cycle[0]];
            eo[cycle[0]] = eo[cycle[3]];
            eo[cycle[3]] = eo[cycle[2]];
            eo[cycle[2]] = eo[cycle[1]];
            eo[cycle[1]] = saver;
        }
        else if(move % 3 == 1) {
            int saver0 = eo[cycle[0]];
            int saver1 = eo[cycle[1]];
            eo[cycle[0]] = eo[cycle[2]];
            eo[cycle[1]] = eo[cycle[3]];
            eo[cycle[2]] = saver0;
            eo[cycle[3]] = saver1;
        }
        else {
            int saver = eo[cycle[0]];
            eo[cycle[0]] = eo[cycle[1]];
            eo[cycle[1]] = eo[cycle[2]];
            eo[cycle[2]] = eo[cycle[3]];
            eo[cycle[3]] = saver;
        }
        if(move == 3 || move == 5 || move == 12 || move == 14) {
            eo[cycle[0]] = (eo[cycle[0]] + 1) % 2;
            eo[cycle[1]] = (eo[cycle[1]] + 1) % 2;
            eo[cycle[2]] = (eo[cycle[2]] + 1) % 2;
            eo[cycle[3]] = (eo[cycle[3]] + 1) % 2;
        }
    }
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            move(iter.next());
        }
    }
    
    public String name() {return NAME;}
    public int size() {return SIZE;}
}