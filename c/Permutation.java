package c;
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
class Permutation implements Cloneable {

    private boolean propagateRight;
    private byte[] p;
    private byte[] pR; // right

    public Permutation(byte[] p) {
        this.p = p;
        this.propagateRight = false;
    }
    public Permutation(byte[] p, byte[] pR) {
        this.p = p;
        this.pR = pR;
        this.propagateRight = true;
    }
    
    /* only reason to call this if propagateRight = true. generate pR */
    public Permutation(byte[] p, boolean propagateRight) {
        this.p = p;
        this.propagateRight = propagateRight;
        if(propagateRight) {
            this.pR = getPR();
        }
    }
    
    
    /* identity permutation */
    public Permutation(int size) {
        propagateRight = true;
        p = new byte[size];
        for(byte i = 0; i < size; i++) {
            p[i] = i;
        }
    }
    
    public Permutation clone() {
        if(propagateRight) {
            return new Permutation(p, pR);
        }
        return new Permutation(p);
    }
    
    public byte[] getPermutation() {return p;}
    public byte[] getPR() {
        if(propagateRight) {return pR;}
        //else
        pR = new byte[p.length];
        for(byte i = 0; i < p.length; i++) {
            pR[p[i]] = i;
        }
        return pR;
    }
    public boolean isPropagateLeft() {return propagateRight;}

    /* left multiply by other, create new permutation */
    public Permutation multiply(Permutation other) {
        byte[] pNew = new byte[p.length];
        for(byte i = 0; i < p.length; i ++) {
            pNew[other.getPR()[i]] = p[i];
        }
        if(!propagateRight) {
            return new Permutation(pNew);
        }
        else {
            byte[] pRNew = new byte[p.length];
            for(byte i = 0; i < p.length; i ++) {
                pRNew[getPR()[i]] = other.p[i];
            }
            return new Permutation(pNew, pRNew);
        }
    }

    /* left and right multiply by other in place. there isn't a performance increase */
    public void multiplyIP(Permutation other) {
        byte[] pNew = new byte[p.length];
        for(byte i = 0; i < p.length; i ++) {
            pNew[other.getPR()[i]] = p[i];
        }
        p = pNew;
        if(propagateRight) {
            byte[] pRNew = new byte[p.length];
            for(byte i = 0; i < p.length; i ++) {
                pRNew[getPR()[i]] = other.p[i];
            }
            pR = pRNew;
        }
    }

    public void rightMultiplyIP(Permutation other) {
        byte[] pNew = new byte[p.length];
        for (byte i = 0; i < p.length; i++) {
            pNew[getPR()[i]] = other.p[i];
        }
        if (propagateRight) {
            byte[] pRNew = new byte[p.length];
            for (byte i = 0; i < p.length; i++) {
                pRNew[other.getPR()[i]] = p[i];
            }
            pR = pRNew;
        }
        p = pNew;
    }
    /* inverts the permutation (same as transpose) */
    public void invert() {
        if(!propagateRight) {
            p = getPR();
        }
        else {
            byte[] pCopy = p.clone();
            p = pR;
            pR = pCopy;
        }
    }
}