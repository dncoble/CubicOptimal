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
        
    }
    
    public Cube2() {
        p = new Permutation(48);
    }
    /* move method for performing a single move */
    public void singleMove(int move) {
        
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
    
    
    /*
     * The permutation class represents cube states and moves. Effectively, each Permutation object acts as a
     * permutation matrix. Row index is slot and column index is piece. Moves on a state are represented by a left
     * multiplication by a move matrix (or another state). Storing an entire matrix of boolean values is inefficient and
     * expensive, so we represent the permutation as a vector with indices representing slots and values the pieces.
     * Multiplication becomes fast if we also create a 'right' vector for multiplication to the right which has pieces
     * as indices and vals as slots.
     * Then again, just representing a boolean matrix may be faster. This implementation scales faster but for n = 48
     * it's 48^2 'and' checks.
     */
    private class Permutation {
        
        private boolean propagateRight;
        private byte[] p;
        private byte[] pR; // right
        
        public Permutation(byte[] p) {
            this.p = p;
            this.propagateRight = false;
        }
        public Permutation(byte[] p, byte[] pL) {
            this.p = p;
            this.pR = pL;
            this.propagateRight = true;
        }
        
        /* identity permutation */
        public Permutation(int size) {
            propagateRight = true;
            p = new byte[size];
            for(byte i = 0; i < size; i++) {
                p[i] = i;
            }
        }
        
        public byte[] getPermutation() {return p;}
        public byte[] getRightPermutation() {return pR;}
        public boolean isPropagateLeft() {return propagateRight;}
        
        /* left multiply by other, create new permutation */
        public Permutation multiply(Permutation other) {
            byte[] pNew = new byte[p.length];
            for(byte i = 0; i < p.length; i ++) {
                pNew[other.pR[i]] = p[i];
            }
            if(!propagateRight) {
                return new Permutation(pNew);
            }
            else {
                byte[] pRNew = new byte[p.length];
                for(byte i = 0; i < p.length; i ++) {
                    pRNew[pR[i]] = other.p[i];
                }
                return new Permutation(pNew, pRNew);
            }
        }
        
        /* left and right multiply by other in place. the jury's out on if this is any better */
        public void multiplyIP(Permutation other) {
            byte[] pNew = new byte[p.length];
            for(byte i = 0; i < p.length; i ++) {
                pNew[other.pR[i]] = p[i];
            }
            p = pNew;
            if(propagateRight) {
                byte[] pRNew = new byte[p.length];
                for(byte i = 0; i < p.length; i ++) {
                    pRNew[pR[i]] = other.p[i];
                }
                pR = pRNew;
            }
        }
        
        public void rightMultiplyIP(Permutation other) {
            byte[] pNew = new byte[p.length];
            for (byte i = 0; i < p.length; i++) {
                pNew[pR[i]] = other.p[i];
            }
            if (propagateRight) {
                byte[] pRNew = new byte[p.length];
                for (byte i = 0; i < p.length; i++) {
                    pRNew[other.pR[i]] = p[i];
                }
                pR = pRNew;
            }
            p = pNew;
        }
    }
}