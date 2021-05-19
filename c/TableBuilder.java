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
	public static void makeTable(Coordinate q) {
		int maxSize = q.size();
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
				if(!table.containsKey(q.value(cube))) {
					table.put(q.value(cube), (byte) scr.getLength());
					tableSize ++;
					data[scr.getLength()] ++;
					if(tableSize % 10000 == 0) {
						System.out.println("table size: " + tableSize);
						System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
						System.out.println("length: " + length);
						System.out.println(q.value(cube));
						int[] j = scr.toInt();
						for(int k : j) {System.out.print(k + " ");}
						System.out.println(scr.toString());
						System.out.println();
					}
					if(tableSize > maxSize) {break complete;}
				}
				//some efficiency could be gained if i made it only move the coords that are relevant
				cube.move(scr.iterate());
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
		writeMapToFile((Serializable) table, q.name() + "Table");
	}
	/* the breadth-first search of makeTable only works well for small tables. this breadth first iterates through
	 * depth 9, then uses the A* search algorithm, using the partially generated table, to fill out the rest.
	 * i'm also going to use a small heuristic, provided, in case the partially generated table cannot produce a
	 * value. in that case, it will return a 0. the nasty thing is that the 0 values are actually more likely to be
	 * in truth longer, and lead the search algorithm in the wrong direction
	 * NOT FUNCTIONAL i'm commenting it all out because it was based on the old coordinate system. it won't be that
	 * hard to change when i come back to it. */
//	public static void makeBigTable(int type, ByteHeuristic smallH) {
//		int maxSize = getMaxSize(type);
//		RawCoordHeuristic experimental = new RawCoordHeuristic();
//		experimental.addToTable(0,(byte) 0);
//		int tableSize = 1;
//		Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
//		Cube cube = new Cube(scr);
//		IntScramble current = new IntScramble(new int[] {Integer.MIN_VALUE});
//		int length = 1;
//		int[] data = new int[21];
//		for(int m = 0; m < 21; m ++) {data[m] = 0;}
//		Instant start = Instant.now();
//		complete:
//		while(tableSize <= maxSize && length < 8) {
//			boolean finishedLength = false;
//			while(!finishedLength) {
//				if(!experimental.getRawTable().containsKey(getCoord(cube, type))) {
//					experimental.addToTable(getCoord(cube, type), (byte) scr.getLength());
//					tableSize ++;
//					data[scr.getLength()] ++;
//					if(tableSize % 500000 == 0) {
//						Instant finish = Instant.now();
//						long timeElapsed = Duration.between(start, finish).toMillis();
//						System.out.println("table size: " + tableSize);
//						double amtFinished = (double) tableSize / (maxSize + 1);
//						System.out.println("% complete: " + amtFinished*100);
//						System.out.println("length: " + length);
//						double l = ((double) 500000/(maxSize+1))/(timeElapsed/1000); //rate in %/sec of last round
//						System.out.println("finish at this rate: " +((1-amtFinished)/l)/60 + " mins. ");
//						System.out.println(scr.toString());
//						System.out.println();
//					}
//					if(tableSize > maxSize) {break complete;}
//				}
//				moveCoord(cube, type, scr.iterate());
//				if(current.iterate(length)) {finishedLength = true;}
//			}
//			length ++;
//			int[] intScr = new int[(length - 1) / 8 + 1];
//			for(int i = 0; i < intScr.length; i ++) {intScr[i] = Integer.MIN_VALUE;}
//			current = new IntScramble(intScr);
//			scr = new Scramble(Integer.MIN_VALUE, length); //this works
//			cube = new Cube(scr);
//		}
//		int coord = 1;
//		int max = getMaxSize(type);
//		MaxHeuristic h = new MaxHeuristic();
//		h.addHeuristic(smallH);
//		h.addHeuristic(experimental);
//		while(coord < max){
//			if(!experimental.getRawTable().containsKey(coord)) {
//				cube = new Cube();
//				setCoord(cube, type, coord);
//				IDAStar searcher = new IDAStar(cube, h); //i chose IDA* because memory might already be overwhelmed
//				experimental.addToTable(getCoord(cube, type), (byte) (searcher.search().getLength()));
//			}
//			coord ++;
//			if(tableSize % 500000 == 0) {
//				Instant finish = Instant.now();
//				long timeElapsed = Duration.between(start, finish).toMillis();
//				System.out.println("table size: " + tableSize);
//				double amtFinished = (double) tableSize / (maxSize + 1) * 100;
//				System.out.println("% complete: " + amtFinished);
//				System.out.println("length: " + length);
//				double l = ((double) 500000/(maxSize+1))/(timeElapsed/1000); //rate in %/sec of last round
//				System.out.println("finish at this rlate: " +((100-amtFinished)/l)/60 + " mins. ");
//				System.out.println(scr.toString());
//				System.out.println();
//			}
//		}
//		for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
//		writeMapToFile((Serializable) experimental.getRawTable(), getFile(type));
//	}
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