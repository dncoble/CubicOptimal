package q;
import c.*;

import java.util.ArrayList;
import java.util.ListIterator;

public class CP implements Coordinate {
    public int[] cp;
    private static String NAME;
    private static int SIZE;
    static {
        NAME = "CP";
        SIZE = 40321;
    }
    public CP(Cube cube) {cp = cube.getCP();}
    public CP() {cp = new int[] {0,1,2,3,4,5,6,7};}
    public void set(Cube cube) {cp = cube.getCP();}
    /*the concept of turning an int[] into an int with a base system is also used in
     * since for every index in the permutation, the options are reduced by one, it is effectively
     * a base system where each subsequent place value has one less power. (i.e. corners are 765432)
     * they are converted by saying input value is index in an ArrayList which starts out with all values
     * (either 0-7 or 0-11) and when a piece is put in removing it from the list
     * the final corner/edge is excluded because it is forced. */
    public int value(int[] cp) {
        ArrayList<Integer> cCheckList = new ArrayList<Integer>();
        for(int k = 0; k < 8; k ++) {cCheckList.add(k);}
        int corner  = cp[0];
        int rtrn = corner;
        cCheckList.remove((Integer) corner);
        for(int l = 1; l < 7; l ++) {
            rtrn *= 8 - l; // may be 8
            corner = cp[l];
            rtrn += cCheckList.indexOf(corner);
            cCheckList.remove((Integer) corner);
        }

        return rtrn;
    }
    public int value(Cube cube) {return value(cube.getCP());}
    public int value() {return value(cp);}
    
    //CP: FUR BUL FDL BDR FUL BUR FDR BDL
    public void move(int move) {
        int type = move / 3;
        int[] cycle;
        if(type == 0) {cycle = new int[]{0, 6, 2, 4};}
        else if(type == 1) {cycle = new int[]{0, 4, 1, 5};}
        else if(type == 2) {cycle = new int[]{6, 0, 5, 3};}
        else if(type == 3) {cycle = new int[]{3, 5, 1, 7};}
        else if(type == 4) {cycle = new int[]{6, 3, 7, 2};}
        else {cycle = new int[]{4, 2, 7, 1};}

        if(move % 3 == 0) {
            int saver = cp[cycle[0]];
            cp[cycle[0]] = cp[cycle[3]];
            cp[cycle[3]] = cp[cycle[2]];
            cp[cycle[2]] = cp[cycle[1]];
            cp[cycle[1]] = saver;
        }
        else if(move % 3 == 1) {
            int saver0 = cp[cycle[0]];
            int saver1 = cp[cycle[1]];
            cp[cycle[0]] = cp[cycle[2]];
            cp[cycle[1]] = cp[cycle[3]];
            cp[cycle[2]] = saver0;
            cp[cycle[3]] = saver1;
        }
        else {
            int saver = cp[cycle[0]];
            cp[cycle[0]] = cp[cycle[1]];
            cp[cycle[1]] = cp[cycle[2]];
            cp[cycle[2]] = cp[cycle[3]];
            cp[cycle[3]] = saver;
        }
    }
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            move(iter.next());
        }
    }
    /* a proposed value() which should work much faster by using base-8 numbers and bitshift. 
     * the result being that all coords are not filled between 0 and max value, and solved is not 0 */
    
//    public int value(Cube cube) {
//        int rtrn = 0;
//        int[] cp = cube.getCP();
//        for(int i = 0; i < 8; i ++) {
//            rtrn += cp[i];
//            rtrn <<= 3;
//        }
//        return rtrn;
//    }
    
    public int[] getArray() {
        return cp;
    }
    
    public String name() {return NAME;}
    public int size() {return SIZE;}
}