package defunct;
/*
 * stores a permutation, and is interfaced with for both moves and rotations/symmetries
 * a permutation is a set of four coordinates:
 * CO (corner orientation) CP (corner permutation) EO (edge orientation) EP edge permutation
 * how each coordinate is defined is elaborated in their move methods.
 * all definitions are similar to those created by Kociemba in his solver
 */
import c.Scramble;

import java.util.*;

public class OldCube implements Cloneable{
    //instance variables for coordinates;
    private int[] co;
    private int[] cp;
    private int[] eo;
    private int[] ep;
    //different constructors
    public OldCube(Scramble scramble) {
        co = new int[] {0,0,0,0,0,0,0,0};
        cp = new int[] {0,1,2,3,4,5,6,7};
        eo = new int[] {0,0,0,0,0,0,0,0,0,0,0,0};
        ep = new int[] {0,1,2,3,4,5,6,7,8,9,10,11};
        ListIterator<Integer> iter = scramble.getIterator();
        while(iter.hasNext()) {
            int nextMove = iter.next();
            moveCO(nextMove);
            moveCP(nextMove);
            moveEO(nextMove);
            moveEP(nextMove);
        }
    }
    public OldCube(long[] coords) {
        co = new int[8];
        eo = new int[12];
        cp = new int[8];
        ep = new int[12];
        fromLong(coords);
    }
    public OldCube() {
        co = new int[] {0,0,0,0,0,0,0,0};
        cp = new int[] {0,1,2,3,4,5,6,7};
        eo = new int[] {0,0,0,0,0,0,0,0,0,0,0,0};
        ep = new int[] {0,1,2,3,4,5,6,7,8,9,10,11};
    }
    public OldCube(int[] co, int[] cp, int[] eo, int[] ep) {
        this.co = co;
        this.cp = cp;
        this.eo = eo;
        this.ep = ep;
    }
    public OldCube clone() {return new OldCube(co.clone(), cp.clone(), eo.clone(), ep.clone());}
    public boolean equals(OldCube other) {
        if(Arrays.equals(other.getCO(), co) && Arrays.equals(other.getEO(), eo) &&
                Arrays.equals(other.getEP(), ep) && Arrays.equals(other.getCP(), cp)) {
            return true;
        }
        return false;
    }
    //move method for performing a single move
    public void singleMove(int move) {
        moveCO(move);
        moveCP(move);
        moveEO(move);
        moveEP(move);
    }
    /* move method for performing a single move. differentiated from
     * singleMove because it will also take an int definition of a c.Scramble */
    public void move(int scramble, int length) {
        Scramble scr = new Scramble(scramble, length);
        move(scr);
    }
    /* move method which takes directly a c.Scramble object.
     * also acts as a helper method for the above move method */
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            singleMove(iter.next());
        }
    }
    /* corner orientation is defined relative to white/yellow / up/down.
     * a corner can be either oriented (0), clockwise (1), or counterclockwise (2)
     * the coordinate is a trinary number of the 8 corners stringed together in order
     * FUR BUL FDL BDR FUL BUR FDR BDL
     * there's a good chance all of these movers can be optimizable. */
    public void moveCO(int move) {
        int type = move / 3;
        int[] cycle;
        if(type == 0) {cycle = new int[]{0, 6, 2, 4};}
        else if(type == 1) {cycle = new int[]{0, 4, 1, 5};}
        else if(type == 2) {cycle = new int[]{6, 0, 5, 3};}
        else if(type == 3) {cycle = new int[]{3, 5, 1, 7};}
        else if(type == 4) {cycle = new int[]{6, 3, 7, 2};}
        else {cycle = new int[]{4, 2, 7, 1};}

        if(move % 3 == 0) {
            int saver = co[cycle[0]];
            co[cycle[0]] = co[cycle[3]];
            co[cycle[3]] = co[cycle[2]];
            co[cycle[2]] = co[cycle[1]];
            co[cycle[1]] = saver;
        }
        else if(move % 3 == 1) {
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

        if(move % 3 != 1 && !(move / 3 == 1 || move / 3 == 4)) {
            co[cycle[0]] = (co[cycle[0]] + 1) % 3;
            co[cycle[1]] = (co[cycle[1]] + 2) % 3;
            co[cycle[2]] = (co[cycle[2]] + 1) % 3;
            co[cycle[3]] = (co[cycle[3]] + 2) % 3;
        }
    }
    //CP: FUR BUL FDL BDR FUL BUR FDR BDL
    public void moveCP(int move) {
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
    /* edge orientation is similar to corner orientation, but an edge can only be
     * oriented or swapped. therefore, the coordinate is 12 binary numbers, with the order
     * UR DR DL UL FR FL BL BR FU BU BD FD
     * edge orientation was made relative to U/D, rather than standard F/B, since that helps
     * with calculating rotation.*/
    public void moveEO(int move) {
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
    public void moveEP(int move) {
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
    //overloaded methods to accept scrambles for each coordinate move
    public void moveCO(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            moveCO(iter.next());
        }
    }
    public void moveCP(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            moveCP(iter.next());
        }
    }
    public void moveEO(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            moveEO(iter.next());
        }
    }
    public void moveEP(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            moveEP(iter.next());
        }
    }

    /* to help with the transition to the permutation-based cube */
    public byte[] getPermutation() {
        byte[] p = new byte[48];
        for(int i = 0; i < 24; i+=3) {
            int k = co[i/3];
            if(k == 0) {}
            else if(k == 1) {k=2;}
            else {k=1;}
            int r = cp[i/3] * 3 + k;
            p[i] = (byte) r;
            p[i+1] = (byte) (((r-r/3*3+1)%3)+r/3*3);
            p[i+2] = (byte) (((r-r/3*3+2)%3)+r/3*3);
        }
        for(int i = 24; i < 48; i +=2) {
            int r = ep[(i-24)/2]*2 + eo[(i-24)/2];
            p[i] = (byte) (r + 24);
            p[i+1] = (byte) ((r-r/2*2+1)%2 + r/2*2 + 24);
        }
        return p;
    }

    //work in progress
    public String toString() {
        return "Work in progress";
    }
    //work in progress
    public void printOut() {
        System.out.println(toString());
    }
    /* retriever methods, except RCO & REO, which must be calculated.
     * RCO & REO are defined in the following ways: for corners, it can be in
     * one of 3 orientations, and in a correct or incorrect slot. this leads to 6 possibilities
     * for each piece. an REO edge can be either flipped or not (binary), and in its correct layer,
     * or in one of the other two layers, leading to also 6 possibilities.
     * note that this is significantly different than how the to & fromInt methods
     * work for them.
     */
    public int[] getCO() {return co;}
    public int[] getCP() {return cp;}
    public int[] getEO() {return eo;}
    public int[] getEP() {return ep;}
    public int[] getRCO() {
        int[] rtrn = new int[8];
        for(int i = 0; i < 4; i ++) {
            rtrn[i] = co[i];
            if(cp[i] > 3) {
                rtrn[i] += 3;
            }
        }
        for(int j = 4; j < 8; j++) {
            rtrn[j] = co[j];
            if(cp[j] < 4) {
                rtrn[j] += 3;
            }
        }
        return rtrn;
    }
    public int[] getREO() {
        int[] rtrn = new int[12];
        for(int i = 0; i < 4; i ++) {
            rtrn[i] += eo[i] + (ep[i] / 4) * 2;
        }
        for(int j = 4; j < 8; j ++) {
            rtrn[j] += eo[j];
            if(ep[j] < 4) {
                rtrn[j] += 4;
            }
            else if(ep[j] < 8) {} // do nothing.
            else {
                rtrn[j] += 2;
            }
        }
        for(int k = 8; k < 12; k++) {
            rtrn[k] += eo[k];
            if(ep[k] < 4) {
                rtrn[k] += 2;
            }
            else if(ep[k] < 8) {
                rtrn[k] += 4;
            }
        }
        return rtrn;
    }
    /* this returns a list with two long values; one for the orientation ints and one with
     * the permutation ints.
     * this is basically done to split it into two chunks because one is larger than max long size
     * to intrinsically account for corner and edge parity, the final value in the list is not included
     * to account for swap parity, the pLong, the final two values of EP are not included. */
//	public long[] toLong() {
//		long oLong = ((long) (coToInt()) * 2048) + (long) (eoToInt());//we can actually calculate oLong in one line
//		long pLong = (long) cpToInt() * 12;
//		/* when calculating both edge and corner permutations at the same time, swap parity can be
//		 * considered to force the final two edge pieces, which means they do not have to be considered.
//		 * below is a modified version of the EPToInt method which doesn't consider the final two
//		 * edges rather than just the final edge. */
//		ArrayList<Integer> eCheckList = new ArrayList<Integer>();
//		for(int m = 0; m < 12; m ++) {eCheckList.add(m);}
//		int edge = ep[0];
//		pLong += edge;
//		eCheckList.remove((Integer) edge);
//		for(int n = 1; n < 10; n ++) {
//			pLong *= 12 - n; // may be 12
//			edge = ep[n];
//			pLong += eCheckList.indexOf(edge);
//			eCheckList.remove((Integer) edge);
//		}
//		return new long[] {oLong, pLong};
//	}
    //effectively does the reverse of the toLong() method.
    public void fromLong(long[] coord) {
        long oCoord = coord[0];
        long pCoord = coord[1];
        eoFromInt((int) oCoord % 2048); // the % 2048 doesn't actually matter
        oCoord /= 2048;
        coFromInt((int) oCoord);
        /* just as in toLong, the final two edges are not considered, requiring a modified form
         * of the EOFromInt method */
        ArrayList<Integer> eCheckList = new ArrayList<Integer>();
        for(int k = 0; k < 12; k ++) {eCheckList.add(k);}
        int[] eToList = new int[12];
        for(int o = 9; o >= 0; o --) {
            eToList[o] = (int) (pCoord % (12 - o));
            pCoord /= (long) (12 - o);
        }
        for(int l = 0; l < 10; l++) {
            ep[l] = eCheckList.get(eToList[l]);
            eCheckList.remove(eToList[l]);
        }
        cpFromInt((int) pCoord);
        /* the following code is used to determine swap parity, which is used to determine
         * EP[10] and EP[11]. the two edges are currently held in eCheckList */
        int[] copyCP = cp.clone();
        boolean cornerParity = false;
        int o = 0;
        while(o < 7) {
            if(copyCP[o] == o) {
                o ++;
            }
            else {
                int holder = copyCP[o];
                copyCP[o] = copyCP[holder];
                copyCP[holder] = holder;
                cornerParity = !cornerParity;
            }
        }
        /* we assume one permutation of edge pieces, run the same test. if that is incorrect
         * then swap the final two edges for the proper arrangement. */
        ep[10] = eCheckList.get(0);
        ep[11] = eCheckList.get(1);
        int[] copyEP = ep.clone();
        boolean edgeParity = false;
        int p = 0;
        while(p < 11) {
            if(copyEP[p] == p) {
                p ++;
            }
            else {
                int holder = copyEP[p];
                copyEP[p] = copyEP[holder];
                copyEP[holder] = holder;
                edgeParity = !edgeParity;
            }
        }
        if(cornerParity != edgeParity) {
            int holder = ep[10];
            ep[10] = ep[11];
            ep[11] = holder;
        }
    }
    /* the following are methods for changing an int version of a coordinate back into a
     * the int[] form. they are effectively the reverse of the XXtoInt methods.
     * they all include ways of determining the last value, which in all cases was forced and
     * therefore excluded  */
    public void coFromInt(int coord) {
        int twist = 0;
        for(int j = 0; j < 7; j ++) {
            co[6 - j] = coord % 3;
            twist = (twist + coord % 3) % 3;
            coord /= 3;
        }
        co[7] = (3 - twist) % 3;
    }
    public void eoFromInt(int coord) {
        boolean flip = false;
        for(int i = 0; i < 11; i ++) {
            eo[10-i] = coord % 2;
            if(coord % 2 == 1) {
                flip = !flip;
            }
            coord /= 2;
        }
        if(flip) {eo[11] = 1;}
        else {eo[11] = 0;}
    }
    public void cpFromInt(int coord) {
        ArrayList<Integer> cCheckList = new ArrayList<Integer>();
        for(int m = 0; m < 8; m ++) {cCheckList.add(m);}
        int[] cToList = new int[8];
        for(int p = 6; p >= 0; p--) {
            cToList[p] = (int) (coord % (8 - p));
            coord /= (long) (8 - p);
        }
        for(int n = 0; n < 7; n++) {
            cp[n] = cCheckList.get(cToList[n]);
            cCheckList.remove(cToList[n]);
        }
        cp[7] = cCheckList.get(0);
    }
    public void epFromInt(int coord) {
        ArrayList<Integer> eCheckList = new ArrayList<Integer>();
        for(int k = 0; k < 12; k ++) {eCheckList.add(k);}
        int[] eToList = new int[12];
        for(int o = 10; o >= 0; o --) {
            eToList[o] = (int) (coord % (12 - o));
            coord /= (long) (12 - o);
        }
        for(int l = 0; l < 11; l++) {
            ep[l] = eCheckList.get(eToList[l]);
            eCheckList.remove(eToList[l]);
        }
        ep[11] = eCheckList.get(0);
    }
    /* since RCO and REO don't fully define permutation,
     * there is leeway in how to define permutation based no RCOFromInt and REOFromInt
     * methods. for these methods, i decided to make all corners either 0 (FUR) or 4 (FUL),
     * and all edges either 0 (UR), 4 (FR) or 8 (FU), which makes the cube unsolvable (obviously),
     * but for our purposes this is good enough.*/
    public void rcoFromInt(int coord) {
        coFromInt(coord % 2187);
        coord /= 2187;
        int i = 7;
        int k = 3;
        while(i >= 0 && k >= 0) {
            int binoCoef = 1;
            for (int j = 1, m = i; j <= k; j++, m--) {binoCoef = binoCoef * m / j;}
            if(coord >= binoCoef) {
                cp[i] = 0;
                i --;
                coord -= binoCoef;
            }
            else {
                cp[i] = 4;
                k --;
                i --;
            }
        }
        while(i >= 0) {
            cp[i] = 0;
            i --;
        }
    }
    public void reoFromInt(int coord) {
        ep = new int[] {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        eoFromInt(coord % 2048);
        coord /= 2048;
        int toListCoord = coord % 70;
        coord /= 70;
        int i = 7;
        int k = 3;
        int[] toList = new int[8];
        while(i >= 0 && k >= 0) {
            int binoCoef = 1;
            for (int j = 1, m = i; j <= k; j++, m--) {binoCoef = binoCoef * m / j;}
            if(toListCoord >= binoCoef) {
                toList[i] = 0;
                i --;
                toListCoord -= binoCoef;
            }
            else {
                toList[i] = 4;
                k --;
                i --;
            }
        }
        while(i >= 0) {
            toList[i] = 0;
            i --;
        }
        i = 11;
        k = 3;
        while(i >= 0 && k >= 0) {
            int binoCoef = 1;
            for (int j = 1, m = i; j <= k; j++, m--) {binoCoef = binoCoef * m / j;}
            if(coord >= binoCoef) {
                i --;
                coord -= binoCoef;
            }
            else {
                ep[i] = 8;
                k --;
                i --;
            }
        }
        while(i >= 0) {
//			EP[i] = 8;
            i --;
        }
        int toListCounter = 0;
        for(int j = 0; j < 12; j ++) {
            if(ep[j] == -1) {
                ep[j] = toList[toListCounter];
                toListCounter ++;
            }
        }
    }
    /* the below methods create an equivalent rotated cube.
     * rotation is a value from 0-47, and each rotation is made up of one
     * of z or x rotation to place the U face, one y move to place the F face,
     * and either a mirror along R axis or not. base system:
     * 2 (mirror) 4 (y move) 6(x / z move)
     * x / z move: 0 - nothing, 1 - z, 2 - z' 3 - x, 4 - x', 5 - x2
     * y move: 0 - nothing, 1 - y, 2 - y2, 3 - y'
     * there's probably a better way to do this, at least reduce the amount of lines */
    public void rotate(int rotation) {
        rotateCO(rotation);
        rotateCP(rotation);
        rotateEO(rotation);
        rotateEP(rotation);
    }
    public void rotateCO(int rotation) {
        int[] current = getRCO();
        int firstRot = rotation % 6;
        rotation /= 6;
        if(firstRot == 0) {} // do nothing
        else if(firstRot < 3) {
            int[] table;
            if(firstRot == 1) {table = new int[]{4,7,6,5,2,1,0,3};}
            else {table = new int[]{6,5,4,7,0,3,2,1};}
            for(int i = 0; i < 4; i ++) {
                co[i] = current[table[i]];
                if(co[i] > 2) {
                    co[i] = (co[i] + 2) % 3;
                }
            }
            for(int j = 4; j < 8; j ++) {
                co[j] = current[table[j]];
                if(co[j] > 2) {
                    co[j] = (co[j] + 1) % 3;
                }
            }
        }
        else if(firstRot < 5) {
            int table[];
            if(firstRot == 3) {table = new int[]{6,4,7,5,2,0,3,1};}
            else {table = new int[]{5,7,4,6,1,3,0,2};}
            for(int i = 0; i < 4; i ++) {
                co[i] = current[table[i]];
                if(co[i] > 2) {
                    co[i] = (co[i] + 1) % 3;
                }
            }
            for(int j = 4; j < 8; j ++) {
                co[j] = current[table[j]];
                if(co[j] > 2) {
                    co[j] = (co[j] + 2) % 3;
                }
            }
        }
        else { // x2, requires no rotation code
            int[] table = {3,2,1,0,7,6,5,4};
            for(int i = 0; i < 8; i ++) {co[i] = current[table[i]] % 3;}
        }
        int secRot = rotation % 4; // y rotation
        rotation /= 4;
        if(secRot != 0) {
            int[] table;
            if(secRot == 1) {table = new int[]{5,4,6,7,0,1,3,2};}
            else if(secRot == 2) {table = new int[]{1,0,3,2,5,4,7,6};}
            else {table = new int[]{4,5,7,6,1,0,2,3};}
            int[] copyCO = co.clone();
            for(int i = 0; i < 8; i ++) {co[i] = copyCO[table[i]];}
        }
        if(rotation == 1) { // mirror
            int[] copyCO = co.clone();
            for(int i = 0; i < 4; i ++) {
                co[i] = (copyCO[i + 4] * 2) % 3;
            }
            for(int j = 4; j < 8; j ++) {
                co[j] = (copyCO[j - 4] * 2) % 3;
            }
        }
    }
    public void rotateCP(int rotation) {
        int firstRot = rotation % 6;
        rotation /= 6;
        int[] firstTable = new int[][]{{0,1,2,3,4,5,6,7},{4,7,6,5,2,1,0,3},{6,5,4,7,0,3,2,1},{6,4,7,5,2,0,3,1},{5,7,4,6,1,3,0,2},{3,2,1,0,7,6,5,4}}[firstRot];
        int secRot = rotation % 4;
        rotation /= 4;
        int[] secTable = new int[][]{{0,1,2,3,4,5,6,7},{5,4,6,7,0,1,3,2},{1,0,3,2,5,4,7,6},{4,5,7,6,1,0,2,3}}[secRot];
        int[] thirdTable = new int[][]{{0,1,2,3,4,5,6,7},{4,5,6,7,0,1,2,3}}[rotation];
        int[] table = new int[8];
        for(int i = 0; i < 8; i ++) {
            table[i] = thirdTable[secTable[firstTable[i]]]; // prev. had this in reverse order. hopefully this is correct
        }
        int[] copyCP = cp.clone();
        for(int j = 0; j < 8; j ++) {cp[table[j]] = copyCP[j];}
        for(int k = 0; k < 8; k ++) {cp[k] = table[cp[k]];}
//		if(rotation == 1) {
//			copyCP = cp.clone();
//			cp[0]=copyCP[4];cp[1]=copyCP[5];cp[2]=copyCP[6];cp[3]=copyCP[7];
//			cp[4]=copyCP[0];cp[5]=copyCP[1];cp[6]=copyCP[2];cp[7]=copyCP[3];
//		}
    }
    public void rotateEO(int rotation) {
        int[] current = getREO();
        int firstRot = rotation % 6;
        rotation /= 6;
        if(firstRot == 0) {} // do nothing
        else if(firstRot < 3) {
            int[] table;
            if(firstRot == 1) {table = new int[]{3,0,1,2,8,11,10,9,5,6,7,4};}
            else {table = new int[]{1,2,3,0,11,8,9,10,4,7,6,5};}
            for(int i = 0; i < 4; i ++) {
                eo[i] = current[table[i]];
                if(eo[i] > 1) {
                    eo[i] = (eo[i] + 1) % 2;
                }
            }
            for(int j = 4; j < 8; j ++) {
                eo[j] = current[table[j]];
                if(eo[j] > 3) {
                    eo[j] += 1;
                }
                eo[j] %= 2;
            }
            for(int k = 8; k < 12; k ++) {
                eo[k] = current[table[k]];
                if(eo[k] > 1 && eo[k] < 4) {
                    eo[k] += 1;
                }
                eo[k] %= 2;
            }
        }
        else if(firstRot < 5) {
            int[] table;
            if(firstRot == 3) {table = new int[]{4,7,6,5,1,2,3,0,11,8,9,10};}
            else {table = new int[]{7,4,6,5,0,3,2,1,9,10,11,8};}
            for(int i = 0; i < 4; i ++) {
                eo[i] = current[table[i]];
                if(eo[i] > 3) {
                    eo[i] = eo[i] + 1;
                }
                eo[i] %= 2;
            }
            for(int j = 4; j < 8; j ++) {
                eo[j] = current[table[j]];
                if(eo[j] > 1 && eo[j] < 4) {
                    eo[j] += 1;
                }
                eo[j] %= 2;
            }
            for(int k = 8; k < 12; k ++) {
                eo[k] = current[table[k]];
                if(eo[k] > 1) {
                    eo[k] = (eo[k] + 1) % 2;
                }
            }
        }
        else {
            int[] table = {1,0,3,2,7,6,5,4,10,11,8,9};
            int[] copyEO = eo.clone();
            for(int i = 0; i < 12; i ++) {
                eo[i] = copyEO[table[i]];
            }
        }
        int secRot = rotation % 4;
        rotation /= 4;
        if(secRot != 0) {
            int[] table;
            if(secRot == 1) {table = new int[]{9,10,11,8,7,4,5,6,0,3,2,1};}
            else if(secRot == 2) {table = new int[]{3,2,1,0,6,7,4,5,9,8,11,10};}
            else {table = new int[] {8,11,10,9,5,6,7,4,3,0,1,2};}
            int[] copyEO = eo.clone();
            for(int i = 0; i < 12; i ++) {eo[i] = copyEO[table[i]];}
        }
        if(rotation == 1) {
            int[] copyEO = eo.clone();
            eo[0]=copyEO[3];eo[1]=copyEO[2];eo[2]=copyEO[1];eo[3]=copyEO[0];
            eo[4]=copyEO[5];eo[5]=copyEO[4];eo[6]=copyEO[7];eo[7]=copyEO[6];
        }
    }
    public void rotateEP(int rotation) {
        int firstRot = rotation % 6;
        rotation /= 6;
        int[] firstTable = new int[][]{{0,1,2,3,4,5,6,7,8,9,10,11},{3,0,1,2,8,11,10,9,5,6,7,4},
                {1,2,3,0,11,8,9,10,4,7,6,5},{4,7,6,5,1,2,3,0,11,8,9,10},{7,4,6,5,0,3,2,1,9,10,11,8},
                {1,0,3,2,7,6,5,4,10,11,8,9}}[firstRot];
        int secRot = rotation % 4;
        rotation /= 4;
        int[] secTable = new int[][]{{0,1,2,3,4,5,6,7,8,9,10,11},{9,10,11,8,7,4,5,6,0,3,2,1},
                {3,2,1,0,6,7,4,5,9,8,11,10},{8,11,10,9,5,6,7,4,3,0,1,2}}[secRot];
        int[] thirdTable = new int[][] {{0,1,2,3,4,5,6,7,8,9,10,11},{3,2,1,0,5,4,7,6,8,9,10,11}}[rotation];
        int[] table = new int[12];
        for(int i = 0; i < 12; i ++) {
            table[i] = thirdTable[secTable[firstTable[i]]]; // prev. had this in reverse order. hopefully this is correct
        }
        int[] copyEP = ep.clone();
        for(int j = 0; j < 12; j ++) {ep[table[j]] = copyEP[j];}
        for(int k = 0; k < 12; k ++) {ep[k] = table[ep[k]];}
//		if(rotation == 1) {
//			copyEP = ep.clone();
//			ep[0]=copyEP[3];ep[1]=copyEP[2];ep[2]=copyEP[1];ep[3]=copyEP[0];
//			ep[4]=copyEP[5];ep[5]=copyEP[4];ep[6]=copyEP[7];ep[7]=copyEP[6];
//		}
    }
}