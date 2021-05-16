package c;/*
 * this class is used to build the tables referenced for the solver.
 * it might be a mess. 
 */
import c.Cube;
import c.IntScramble;
import c.Scramble;
import h.ByteHeuristic;
import h.MaxHeuristic;
import h.RawCoordHeuristic;
import s.IDAStar;
import q.*;

import java.util.Map;
import java.io.*;
import java.util.*;
import java.time.Instant;
import java.time.Duration;

public class TableBuilder {
	/* this method can make tables for all raw-type subsets, based on 
	 * the int parameter: 0-CO 1-CP 2-EO 3-EP 4-RCO 5-REO*/
	public static void makeTable(int type) {
		int maxSize = getMaxSize(type);
		Map<Integer, Byte> table = new HashMap<Integer, Byte>();
		table.put(0,(byte) 0);
		int tableSize = 1;
		Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
		Cube cube = new Cube(scr);
		IntScramble current = new IntScramble(new int[] {Integer.MIN_VALUE});
		int length = 1;
		int[] data = new int[21];
		for(int m = 0; m < 21; m ++) {data[m] = 0;}
		complete:
		while(tableSize <= maxSize) {
			boolean finishedLength = false;
			while(!finishedLength) {
				if(!table.containsKey(getCoord(cube, type))) {
					table.put(getCoord(cube, type), (byte) scr.getLength());
					tableSize ++;
					data[scr.getLength()] ++;
					if(tableSize % 10000 == 0) {
						System.out.println("table size: " + tableSize);
						System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
						System.out.println("length: " + length);
						System.out.println(getCoord(cube, type));
						int[] j = scr.toInt();
						for(int k : j) {System.out.print(k + " ");}
						System.out.println(scr.toString());
						System.out.println();
					}
					if(tableSize > maxSize) {break complete;}
				}
				moveCoord(cube, type, scr.iterate());
				if(current.iterate(length)) {finishedLength = true;}
			}
			length ++;
			int[] intScr = new int[(length - 1) / 8 + 1];
			for(int i = 0; i < intScr.length; i ++) {intScr[i] = Integer.MIN_VALUE;}
			current = new IntScramble(intScr);
			scr = new Scramble(Integer.MIN_VALUE, length); //this works
			cube = new Cube(scr);
		}
		for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
		writeMapToFile((Serializable) table, getFile(type));
	}
	/* the breadth-first search of makeTable only works well for small tables. this breadth first iterates through
	 * depth 9, then uses the A* search algorithm, using the partially generated table, to fill out the rest.
	 * i'm also going to use a small heuristic, provided, in case the partially generated table cannot produce a
	 * value. in that case, it will return a 0. the nasty thing is that the 0 values are actually more likely to be
	 * in truth longer, and lead the search algorithm in the wrong direction*/
	public static void makeBigTable(int type, ByteHeuristic smallH) {
		int maxSize = getMaxSize(type);
		RawCoordHeuristic experimental = new RawCoordHeuristic();
		experimental.addToTable(0,(byte) 0);
		int tableSize = 1;
		Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
		Cube cube = new Cube(scr);
		IntScramble current = new IntScramble(new int[] {Integer.MIN_VALUE});
		int length = 1;
		int[] data = new int[21];
		for(int m = 0; m < 21; m ++) {data[m] = 0;}
		Instant start = Instant.now();
		complete:
		while(tableSize <= maxSize && length < 8) {
			boolean finishedLength = false;
			while(!finishedLength) {
				if(!experimental.getRawTable().containsKey(getCoord(cube, type))) {
					experimental.addToTable(getCoord(cube, type), (byte) scr.getLength());
					tableSize ++;
					data[scr.getLength()] ++;
					if(tableSize % 500000 == 0) {
						Instant finish = Instant.now();
						long timeElapsed = Duration.between(start, finish).toMillis();
						System.out.println("table size: " + tableSize);
						double amtFinished = (double) tableSize / (maxSize + 1);
						System.out.println("% complete: " + amtFinished*100);
						System.out.println("length: " + length);
						double l = ((double) 500000/(maxSize+1))/(timeElapsed/1000); //rate in %/sec of last round
						System.out.println("finish at this rate: " +((1-amtFinished)/l)/60 + " mins. ");
						System.out.println(scr.toString());
						System.out.println();
					}
					if(tableSize > maxSize) {break complete;}
				}
				moveCoord(cube, type, scr.iterate());
				if(current.iterate(length)) {finishedLength = true;}
			}
			length ++;
			int[] intScr = new int[(length - 1) / 8 + 1];
			for(int i = 0; i < intScr.length; i ++) {intScr[i] = Integer.MIN_VALUE;}
			current = new IntScramble(intScr);
			scr = new Scramble(Integer.MIN_VALUE, length); //this works
			cube = new Cube(scr);
		}
		int coord = 1;
		int max = getMaxSize(type);
		MaxHeuristic h = new MaxHeuristic();
		h.addHeuristic(smallH);
		h.addHeuristic(experimental);
		while(coord < max){
			if(!experimental.getRawTable().containsKey(coord)) {
				cube = new Cube();
				setCoord(cube, type, coord);
				IDAStar searcher = new IDAStar(cube, h); //i chose IDA* because memory might already be overwhelmed
				experimental.addToTable(getCoord(cube, type), (byte) (searcher.search().getLength()));
			}
			coord ++;
			if(tableSize % 500000 == 0) {
				Instant finish = Instant.now();
				long timeElapsed = Duration.between(start, finish).toMillis();
				System.out.println("table size: " + tableSize);
				double amtFinished = (double) tableSize / (maxSize + 1) * 100;
				System.out.println("% complete: " + amtFinished);
				System.out.println("length: " + length);
				double l = ((double) 500000/(maxSize+1))/(timeElapsed/1000); //rate in %/sec of last round
				System.out.println("finish at this rlate: " +((100-amtFinished)/l)/60 + " mins. ");
				System.out.println(scr.toString());
				System.out.println();
			}
		}
		for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
		writeMapToFile((Serializable) experimental.getRawTable(), getFile(type));
	}
	/* the reason i made coordinates their own class that implements coordinate is so that I wouldn't have to do stuff
	* like this anymore. but since this came from before that time, and TableBuilder is a weird class anyways, i'll keep
	* it this way for now.
	* type: 0-CO 1-CP 2-EO 3-EP 4-RCO 5-REO*/
	public static int getCoord(Cube cube, int type) {
		if(type == 0) {return CO.value(cube);}
		else if(type == 1) {return CP.value(cube);}
		else if(type == 2) {return EO.value(cube);}
		else if(type == 3) {return EP.value(cube);}
		else if(type == 4) {return RCO.value(cube);}
		else {return REO.value(cube);}
	}
	public static void moveCoord(Cube cube, int type, int move) {
		if(type == 0) {cube.moveCO(move);}
		else if(type == 1) {cube.moveCP(move);}
		else if(type == 2) {cube.moveEO(move);}
		else if(type == 3) {cube.moveEP(move);}
		else if(type == 4) {cube.moveCO(move); cube.moveCP(move);}
		else {cube.moveEO(move); cube.moveEP(move);}
	}
	//overloaded to also take full scrambles
	public static void moveCoord(Cube cube, int type, Scramble scr) {
		if(type == 0) {cube.moveCO(scr);}
		else if(type == 1) {cube.moveCP(scr);}
		else if(type == 2) {cube.moveEO(scr);}
		else if(type == 3) {cube.moveEP(scr);}
		else if(type == 4) {cube.moveCO(scr); cube.moveCP(scr);}
		else {cube.moveEO(scr); cube.moveEP(scr);}
	}
	public static String getFile(int type) {
		if(type == 0) {return "COTable";}
		else if(type == 1) {return "CPTable";}
		else if(type == 2) {return "EOTable";}
		else if(type == 3) {return "EPTable";}
		else if(type == 4) {return "RCOTable";}
		else {return "REOTable";}
	}
	/* inclusive / one less than actual size
	 * not all RCO & REO fromInts are possible on actual cubes, since they may involve
	 * having more of one type of edge/corner than exist, so the max size is not just
	 * the max toInt size. credit to Wolfgang Buchmaier for helping me find the correct
	 * because he is better at math than i am */
	public static int getMaxSize(int type) {return new int[]{2186, 40319, 2047, 479001599, 153089, 70963199}[type];}
	/* this method makes tables for all symmetric-type subsets, with the same 
	 * type parameter as use above*/
	public static void setCoord(Cube cube, int type, int coord) {
		if(type == 0) {cube.coFromInt(coord);}
		else if(type == 1) {cube.cpFromInt(coord);}
		else if(type == 2) {cube.eoFromInt(coord);}
		else if(type == 3) {cube.epFromInt(coord);}
		else if(type == 4) {cube.rcoFromInt(coord);}
		else {cube.reoFromInt(coord);}
	}
	public static void makeSymTable(int type) {
		int maxSize = getSymMaxSize(type);
		Map<Integer, Short> table = new HashMap<Integer, Short>();
		@SuppressWarnings("unchecked")
		ArrayList<Integer> rawToSymTable = (ArrayList<Integer>) readMapFromFile(getRawToSymFile(type));
		table.put(0,(short) 0);
		int tableSize = 1;
		Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
		System.out.println(scr);
		Cube cube = new Cube(scr);
		IntScramble current = new IntScramble(new int[]{Integer.MIN_VALUE});
		int length = 1;
		complete:
		while(tableSize <= maxSize) {
			boolean finishedLength = false;
			while(!finishedLength) {
				int symCoord = getSymCoord(cube, type, rawToSymTable);
				if(!table.containsKey(symCoord)) {
					table.put(symCoord, (short) scr.getLength());
					tableSize ++;
					if(tableSize > maxSize) {break complete;}
					if(tableSize % 1 == 0) {
						System.out.println("table size: " + tableSize);
						System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
						System.out.println("length: " + length);
						System.out.println(symCoord);
						int[] j = scr.toInt();
						for(int k : j) {System.out.print(k + " ");}
						System.out.println(scr);
						System.out.println();
					}
				}
				moveCoord(cube, type, scr.iterate());
				if(current.iterate(length)) {finishedLength = true;}
				if(current.getIntArray()[0] % 300000 == 0) {System.out.println("Running... Length: " + scr.getLength());}
			}
			length ++;
			System.out.println(length);
			int[] intScr = new int[(length - 1) / 8 + 1];
			for(int i = 0; i < intScr.length; i ++) {intScr[i] = Integer.MIN_VALUE;}
			scr = new Scramble(Integer.MIN_VALUE, length); //this works
			cube = new Cube(scr);
		}
		writeMapToFile((Serializable) table, getSymFile(type));
	}
	/* the RawToSym table converts a raw coordinate to its sym-coordinate in the following way:
	 * a coordinate is rotated all 48 ways, and converted to int for each. the smallest found int
	 * will be it's 'identity coordinate' and saved in the RawToSym table. unlike the other tables,
	 * a RawToSym table is implemented as an ArrayList. the identity coordinate is saved onto the
	 * ArrayList and the index of the identity coordinate is the sym-coordinate.
	 * the RawToSym table is also saved in order ascending. */
	public static void makeRawToSym(int type) {
		int coord = 0;
		int max = getMaxSize(type);
		int size = 0;
		int sinceLast = 0;
		ArrayList<Integer> table = new ArrayList<Integer>();
		TreeSet<Integer> set = new TreeSet<Integer>();
		while(coord < max) {
			Cube cube = new Cube();
			setCoord(cube, type, coord);
			int identity = makeIdentityCoord(cube, type);
			if(!set.contains(identity)) {
				set.add(identity);
				size ++;
			}
			coord ++;
//			if(coord % 500000 == 0) {
//				System.out.println("% complete: " + ((double) coord / (max + 1)) * 100);
//				System.out.println("table size: " + size);
//				System.out.println("size / coord: " + (double) size / coord);
//				System.out.println("current %: " + (double) (size - sinceLast) / 5000);
//				System.out.println();
//				sinceLast = size;
//			}
		}
		Iterator<Integer> iter = set.iterator();
		while(iter.hasNext()) {
			table.add(iter.next());
		}
		System.out.println(table.size());
		writeMapToFile(table, getRawToSymFile(type));
	}
	public static String getRawToSymFile(int type) {
		if(type == 0) {return "RTSCOTable";}
		else if(type == 1) {return "RTSCPTable";}
		else if(type == 2) {return "RTSEOTable";}
		else if(type == 3) {return "RTSEPTable";}
		else if(type == 4) {return "RTSRCOTable";}
		else {return "RTSREOTable";}
	}
	public static String getSymFile(int type) {
		if(type == 0) {return "SymCOTable";}
		else if(type == 1) {return "SymCPTable";}
		else if(type == 2) {return "SymEOTable";}
		else if(type == 3) {return "SymEPTable";}
		else if(type == 4) {return "SymRCOTable";}
		else {return "SymREOTable";}
	}
	public static int getSymCoord(Cube cube, int type, ArrayList<Integer> rawToSymTable) {
		Cube testerCube = cube.clone();
		int sym = getCoord(testerCube, type);
		for(int i = 1; i < 48; i ++) {
			rotateCoord(testerCube, type, i);
			int coord = getCoord(testerCube, type);
			if(coord < sym) {
				sym = coord;
			}
			testerCube = cube.clone();
		}
		return rawToSymTable.indexOf(sym);
	}
	//this method finds identity coord by rotating the cube 48 times and finding the smallest value. 
	public static int makeIdentityCoord(Cube cube, int type) {
		Cube testerCube = cube.clone();
		int sym = getCoord(testerCube, type);
		for(int i = 1; i < 48; i ++) {
			rotateCoord(testerCube, type, i);
			int coord = getCoord(testerCube, type);
			if(coord < sym) {
				sym = coord;
			}
			testerCube = cube.clone();
		}
		return sym;
	}
	public static void rotateCoord(Cube cube, int type, int rotation) {
		if(type == 0) {cube.rotateCO(rotation);}
		else if(type == 1) {cube.rotateCP(rotation);}
		else if(type == 2) {cube.rotateEO(rotation);}
		else if(type == 3) {cube.rotateEP(rotation);}
		else if(type == 4) {cube.rotateCO(rotation); cube.rotateCP(rotation);}
		else {cube.rotateEO(rotation); cube.rotateEP(rotation);}
	}
	public static void coordFromInt(Cube cube, int type, int coord) {
		if(type == 0) {cube.coFromInt(coord);}
		else if(type == 1) {cube.cpFromInt(coord);}
		else if(type == 2) {cube.eoFromInt(coord);}
		else if(type == 3) {cube.epFromInt(coord);}
		else if(type == 4) {cube.rcoFromInt(coord);}
		else {cube.reoFromInt(coord);}
	}
	/* inclusive / 1 less than actual size. 
	 * all of these values were found by the computer finding the size of the 
	 * RTS table, then manually entering in the size. */
	public static int getSymMaxSize(int type) {return new int[]{36, 4347, 106, 38345286, 16930, 3477981}[type];}
	/* code for writeObjectToFile and readObjectFrom File
	 * has been copied and modified to fit my purposes from
	 * https://examples.javacodegeeks.com/core-java/io/fileoutputstream/how-to-write-an-object-to-file-in-java/
	 * since i was never taught saving files in CompSci. */
	public static void writeMapToFile(Serializable table, String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(table);
            objectOut.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static Object readMapFromFile(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object rtrnMap = objectIn.readObject();
            objectIn.close();
            return rtrnMap;
 
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}