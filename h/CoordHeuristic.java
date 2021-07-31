package h;
import c.Cube;
import c.IntScramble;
import c.Scramble;
import q.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/* this class handles all heuristics from coordinates as created in /q
* at this point i don't remember if i have a comment describing how this works, so i'll lay out a brief description
* here. a coordinate is basically a subset all permutations of a cube. for each coordinate value, there are multiple
* permutations it corresponds to. the table is a dictionary, (HashMap) that contains the heuristic, and then the distance
* to solved for the best permutation that has that coordinate. alternatively, you can think of the coordinate as its own
* puzzle. then the dictionary contains the distance to solved of that subpuzzle in that state. */
public class CoordHeuristic implements ByteHeuristic {
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
            makeTable(true);
        }
        table = (HashMap<Integer, Byte>) readMapFromFile(tableFile);
    }
    public byte h(Cube cube) {return table.get(q.value(cube));}
    /* The below algorithm generates the table in what I believe to be the most efficient algorithm. in experimenting
     * setCoord is not required but if not included then cubes must be saved while coords are unexpanded. that makes
     * them infeasible for big coordinates. so i basically just have two methods but am putting them both here. */
    public void makeTable(boolean useSetCoord) {
        if(useSetCoord) {
            int maxSize = q.size();
            Map<Integer, Byte> table = new HashMap<Integer, Byte>();
            Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
            table.put(0,(byte) 0); int tableSize = 1;
            unexpandedQ.add(0);
            int[] data = new int[21];
            while(!unexpandedQ.isEmpty()) {
                int currentQ = unexpandedQ.poll(); unexpandedSize --;
                Cube cube = q.setCoord(new Cube(), currentQ);
                int length = table.get(currentQ);
                Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
                cube.move(scr);
                if(!table.containsKey(q.value(cube))) {
                    table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
                    unexpandedQ.add(q.value(cube)); unexpandedSize ++;
                    data[length + 1] ++;
                }
                else {} // with queue, if it is already in the table, it must have length less or equal to
                for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
                    cube.move(scr.iterate());
                    if(!table.containsKey(q.value(cube))) {
                        table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
                        unexpandedQ.add(q.value(cube)); unexpandedSize ++;
                        data[length +1] ++;
                    }
                }
                if (tableSize % 10000 == 0) {
                    System.out.println("table size: " + tableSize);
                    System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
                    System.out.println("length: " + length);
                    System.out.println("unexpanded nodes: " + unexpandedSize);
                }
            }
            for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
            writeMapToFile((Serializable) table, q.name() + "Table");
        }
        else {
            int maxSize = q.size();
            Map<Integer, Byte> table = new HashMap<Integer, Byte>();
            Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
            Queue<Cube> unexpandedC = new LinkedList<Cube>();
            table.put(0,(byte) 0); int tableSize = 1;
            unexpandedQ.add(0);
            unexpandedC.add(new Cube());
            int[] data = new int[21];
            while(!unexpandedQ.isEmpty()) {
                int currentQ = unexpandedQ.poll(); unexpandedSize --;
                Cube cube = unexpandedC.poll();
                int length = table.get(currentQ);
                Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
                cube.move(scr);
                if(!table.containsKey(q.value(cube))) {
                    table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
                    unexpandedQ.add(q.value(cube)); unexpandedSize ++;
                    unexpandedC.add(cube.clone());
                    data[length + 1] ++;
                }
                else {} // with queue, if it is already in the table, it must have length less or equal to
                for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
                    cube.move(scr.iterate());
                    if(!table.containsKey(q.value(cube))) {
                        table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
                        unexpandedQ.add(q.value(cube)); unexpandedSize ++;
                        unexpandedC.add(cube.clone());
                        data[length +1] ++;
                    }
                }
                if (tableSize % 10000 == 0) {
                    System.out.println("table size: " + tableSize);
                    System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
                    System.out.println("length: " + length);
                    System.out.println("unexpanded nodes: " + unexpandedSize);
                }
            }
            for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
            writeMapToFile((Serializable) table, q.name() + "Table");
        }
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
