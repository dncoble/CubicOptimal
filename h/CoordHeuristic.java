package h;
import q.*;
import c.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

/*
* this class handles all heuristics from coordinates as created in /q
* at this point i don't remember if i have a comment describing how this works, so i'll lay out a brief description
* here. a coordinate is basically a subset all permutations of a cube. for each coordinate value, there are multiple
* permutations it corresponds to. the table is a dictionary, (HashMap) that contains the heuristic, and then the distance
* to solved for the best permutation that has that coordinate. alternatively, you can think of the coordinate as its own
* puzzle. then the dictionary contains the distance to solved of that subpuzzle in that state. */
public class CoordHeuristic implements ByteHeuristic {
    Coordinate q;
    private HashMap<Integer, Byte> table;
    private boolean isSym;

    public CoordHeuristic(Coordinate q, boolean isSym) {
        this.q = q;
        this.isSym = isSym;
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
            makeTable(isSym);
        }
        table = (HashMap<Integer, Byte>) readMapFromFile(tableFile);
    }
    public byte h(Cube cube) {
        try {
            return table.get(q.value(cube));
        }
        catch (NullPointerException e){
            System.out.println("Cannot find value " + q.value(cube) + " in table.");
            throw e;
        }
    }
    /* The below algorithm generates the table in what I believe to be the most efficient algorithm. in experimenting
     * setCoord is not required but if not included then cubes must be saved while coords are unexpanded. that makes
     * them infeasible for big coordinates. so i basically just have two methods but am putting them both here. */
    public void makeTable(boolean isSym) {
        if(!isSym) {
            TableBuilder.makeTable(q, 1);
        }
        else {
            TableBuilder.makeTable(q, 4);
        }
    }
    
    /* the distribution of elements at each distance */
    public int[] getDistribution() {
        int[] rtrn = new int[21];
        Iterator<Byte> iter = table.values().iterator();
        while(iter.hasNext()) {rtrn[iter.next()] ++;}
        return rtrn;
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