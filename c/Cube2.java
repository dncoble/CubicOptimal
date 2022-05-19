package c;

import java.util.ListIterator;
/*
 * stores a permutation, and is interfaced with for both moves and rotations/symmetries
 * a permutation is a set of four coordinates:
 * CO (corner orientation) CP (corner permutation) EO (edge orientation) EP edge permutation
 * how each coordinate is defined is elaborated in their move methods.
 * all definitions are similar to those created by Kociemba in his solver
 *
 * reworking with permutation-based datatypes that should be faster and easier to work with
 */
public class Cube2 implements Cloneable {
    
    private Permutation p;
    
    public Cube2(Scramble scramble) {
        p = new Permutation(48);
        move(scramble);
    }
    
    public Cube2(Permutation p) {this.p = p;}
    
    public Cube2() {p = new Permutation(48);}
    
    public Cube2 clone() {
        return new Cube2(p.clone());
    }
    /* move method for performing a single move */
    public void singleMove(int move) {
        p.multiplyIP(MoveTables.getMove(move));
    }
    /* move method for performing a single move. differentiated from
     * singleMove because it will also take an int definition of a c.Scramble */
    public void move(int scramble, int length) {
        Scramble scr = new Scramble(scramble, length);
        move(scr);
    }
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            singleMove(iter.next());
        }
    }
    
    public int[] getCO() {
        int[] co = new int[8];
        for(int i =0; i < 8; i++) {
            co[i] = p.getPermutation()[i*3]%3;
        }
        return co;
    }

    public int[] getCP() {
        int[] cp = new int[8];
        for(int i =0; i < 8; i++) {
            cp[i] = p.getPermutation()[i*3]/3;
        }
        return cp;
    }
    
    public int[] getEO() {
        int[] eo = new int[12];
        for(int i =0; i < 12; i++) {
            eo[i] = p.getPermutation()[24+i*2]%2;
        }
        return eo;
    }
    
    public int[] getEP() {
        int[] ep = new int[12];
        for(int i =0; i < 12; i++) {
            ep[i] = p.getPermutation()[24+i*2]/2;
        }
        return ep;
    }
}