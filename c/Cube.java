package c;

import java.util.Arrays;
import java.util.ListIterator;
/*
 * stores a permutation, and is interfaced with for both moves and rotations/symmetries
 * reworking with permutation-based datatypes that should be faster and easier to work with
 */
public class Cube implements Cloneable {

	private Permutation p;

	public Cube(Scramble scramble) {
		p = new Permutation(48, true);
		move(scramble);
	}

	public Cube(Permutation p) {this.p = p;}

	public Cube() {p = new Permutation(48, true);}
	
	@Override
	public Cube clone() {return new Cube(p.clone());}
	
	public boolean equals(Cube other) {
		return Arrays.equals(p.getPermutation(), other.getPermutation());
	}
	
	public void reset() {p = new Permutation(48, true);}
	
	/* returns the byte[] permutation */
	public byte[] getPermutation() {return p.getPermutation();}
	
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
	public void rotate(int rotation) {
		if(rotation < 48) {
			p.rightMultiplyIP(MoveTables.getInverseRotation(rotation));
			p.multiplyIP(MoveTables.getRotation(rotation));
		}
		else {
			invert();
			int r = rotation - 48;
			p.rightMultiplyIP(MoveTables.getInverseRotation(r));
			p.multiplyIP(MoveTables.getRotation(r));
		}
	}

	public void invert() {
		p.invertIP();
	}

	/* corner orientation is defined relative to white/yellow / up/down.
	 * a corner can be either oriented (0), clockwise (1), or counterclockwise (2)
	 * the coordinate is a trinary number of the 8 corners stringed together in order
	 * FUR BUL FDL BDR FUL BUR FDR BDL. */
	public int[] getCO() {
		int[] co = new int[8];
		for(int i =0; i < 8; i++) {
			co[i] = p.getPermutation()[i*3]%3;
		}
		return co;
	}
	/* CP: FUR BUL FDL BDR FUL BUR FDR BDL */
	public int[] getCP() {
		int[] cp = new int[8];
		for(int i =0; i < 8; i++) {
			cp[i] = p.getPermutation()[i*3]/3;
		}
		return cp;
	}
	/* edge orientation is similar to corner orientation, but an edge can only be
	 * oriented or swapped. therefore, the coordinate is 12 binary numbers, with the order
	 * UR DR DL UL FR FL BL BR FU BU BD FD
	 * for some reason, when I first did this, I defined edge orientation relative to U/D moves. IDK why */
	public int[] getEO() {
		int[] eo = new int[12];
		for(int i =0; i < 12; i++) {
			eo[i] = p.getPermutation()[24+i*2]%2;
		}
		return eo;
	}

	public int[] getEP() {
		int[] ep = new int[12];
		for(int i = 0; i < 12; i++) {
			ep[i] = (p.getPermutation()[24+i*2]-24)/2;
		}
		return ep;
	}
}