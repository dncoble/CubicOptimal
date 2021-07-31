package h;
import c.Cube;
import c.IntScramble;
import c.Scramble;
import q.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

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
        ArrayList<Integer> lastValues = new ArrayList<Integer>();
        table.put(0,(byte) 0);
        lastValues.add(0);
        int length = 1;
        int[] data = new int[21];
        while(!lastValues.isEmpty()) {
            ArrayList<Integer> currentValues = new ArrayList<Integer>();
            for(int value : lastValues) {
                Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
                Cube cube = new Cube();
                q.setCoord(cube, value);
                cube.move(scr);
                if(table.containsKey(q.value(cube))) { //if table contains q.value(cube), that value must be length - 1
                    table.put(value, (byte) (length));
                    currentValues.add(value);
                    data[length] ++;
                }
                //loop, and remember about 18/15 problems
            }
            lastValues = currentValues;
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
