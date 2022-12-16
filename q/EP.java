package q;
import c.*;

import java.util.ArrayList;
import java.util.ListIterator;

public class EP extends Coordinate {
    public int[] ep;
    private static String NAME;
    private static int SIZE;
    static {
        NAME = "EP";
        SIZE = 479001599;
    }
    public EP(Cube cube) {ep = cube.getEP();}
    public EP() {ep = new int[] {0,1,2,3,4,5,6,7,8,9,10,11};}
    public EP(int[] ep) {this.ep = ep;}
    public void set(Cube cube) {ep = cube.getEP();}
    public int value(int[] ep){
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
    public int value(Cube cube) {return value(cube.getEP());}
    public int value() {return value(ep);}
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
            int saver = ep[cycle[0]];
            ep[cycle[0]] = ep[cycle[3]];
            ep[cycle[3]] = ep[cycle[2]];
            ep[cycle[2]] = ep[cycle[1]];
            ep[cycle[1]] = saver;
        }
        else if(move % 3 == 1) {
            int saver0 = ep[cycle[0]];
            int saver1 = ep[cycle[1]];
            ep[cycle[0]] = ep[cycle[2]];
            ep[cycle[1]] = ep[cycle[3]];
            ep[cycle[2]] = saver0;
            ep[cycle[3]] = saver1;
        }
        else {
            int saver = ep[cycle[0]];
            ep[cycle[0]] = ep[cycle[1]];
            ep[cycle[1]] = ep[cycle[2]];
            ep[cycle[2]] = ep[cycle[3]];
            ep[cycle[3]] = saver;
        }
    }
    public EP clone() {return new EP(ep);}
    public int[] getArray() {return ep;}
    public String name() {return NAME;}
    public int size() {return SIZE;}
}