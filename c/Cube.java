package c;

import javax.swing.*;
import java.awt.*;
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
	/* net drawing of cube */
	public void visualize() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 700);
		byte[] p = getPermutation();
		for (int i = 0; i < 48; i++) {
			frame.add(new Facelet(i, p[i]));
			frame.setVisible(true);
		}
		//center pieces
		frame.add(new Facelet(4, 4, 0));
		frame.setVisible(true);
		frame.add(new Facelet(4, 1, 1));
		frame.setVisible(true);
		frame.add(new Facelet(7, 4, 2));
		frame.setVisible(true);
		frame.add(new Facelet(10, 4, 3));
		frame.setVisible(true);
		frame.add(new Facelet(4, 7, 4));
		frame.setVisible(true);
		frame.add(new Facelet(1, 4, 5));
		frame.setVisible(true);

		return;
	}
	/* class for visualize */
	private class Facelet extends JComponent {
		private int x;
		private int y;
		private int color;

		/* indices 0-47 (weird pattern).
		 * color order is green (F), white (U), red (R), blue (B), yellow (D), orange (L)*/
		public Facelet(int slot, int piece) {
			x = getx(slot);
			y = gety(slot);
			color = pieceColors(piece);
//                setSize(50,50);
//                setLocation(x,y);
//                setOpaque(false);
		}
		
		public Facelet(int x, int y, int color) {
			this.x = x;
			this.y = y;
			this.color = color;
		}
		
		public void paint(Graphics g) {
			g.setColor(getColor(color));
			g.fillRect(x * 55 + 20, y * 55 + 20, 50, 50);
		}
		
		private int getx(int slot) {
			return new int[]{5, 6, 5, 3, 0, 11, 3, 2, 3, 5, 8, 9, 3, 3, 2, 5, 9, 8, 5, 5, 6, 3, 11,
					0, 7, 5, 7, 5, 1, 3, 1, 3, 6, 5, 2, 3, 0, 11, 8, 9, 4, 4, 4, 10, 4, 10, 4, 4}[slot];
		}
		
		private int gety(int slot) {
			return new int[]{2, 3, 3, 0, 3, 3, 6, 5, 5, 8, 5, 5, 2, 3, 3, 0, 3, 3, 6, 5, 5,
					8, 5, 5, 3, 1, 5, 7, 5, 7, 3, 1, 4, 4, 4, 4, 4, 4, 4, 4, 2, 3, 0, 3, 8, 5, 6, 5,}[slot];
		}
		
		private Color getColor(int color) {
			return new Color[]{Color.GREEN, Color.WHITE, Color.RED, Color.BLUE, Color.YELLOW, Color.ORANGE}[color];
		}
		
		//better as a static variable but java doesn't allow it until this is not a inner class
		private int pieceColors(int piece) {
			return new int[]{1, 2, 0, 1, 5, 3, 4, 5, 0, 4, 2, 3, 1, 0, 5, 1, 3, 2, 4, 0, 2, 4, 3, 5,
					2, 1, 2, 4, 5, 4, 5, 1, 2, 0, 5, 0, 5, 3, 2, 3, 1, 0, 1, 3, 4, 3, 4, 0}[piece];
		}
	}
}