package h;
import c.Cube;
import c.IntScramble;
import c.Scramble;
import q.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/* this class handles all heuristics from coordinates as created in /q
* at this point i don't remember if i have a comment describing how this works, so i'll lay out a brief description
* here. a coordinate is basically a subset all permutations of a cube. for each coordinate value, there are multiple
* permutations it corresponds to. the table is a dictionary, (HashMap) that contains the heuristic, and then the distance
* to solved for the best permutation that has that coordinate. alternatively, you can think of the coordinate as its own
* puzzle. then the dictionary contains the distance to solved of that subpuzzle in that state. */
public class CoordHeuristic {
    Coordinate q;
    private HashMap<Integer, Byte> table;

    public CoordHeuristic(Coordinate q) {
        this.q = q;
        File[] filesList = new File(".").listFiles();
        boolean makeRawTable = true;
        String tableFile = q.name() + "Table";
        for(File j : filesList) {
            if(j.getName().equals(tableFile)) {
                makeRawTable = false;
            }
        }
        //I could use c.TableBuilder.getFile, etc. to tell the user which ones.
        if(makeRawTable) {
            System.out.println("A requested table has not been made. It will be made now. \n"
                    + "This may take a while.");
            makeTable();
        }
        table = (HashMap<Integer, Byte>) readMapFromFile(tableFile);
    }
    public byte h(Cube cube) {return table.get(q.value(cube));}
    public void makeTable() {
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
