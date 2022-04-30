package q;

import c.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/* all Sym coords go through this class, unlike raw coords */
public class Sym implements Coordinate {
    RawCoord rawCoord;
    HashMap<Integer, Integer> rtsTable;

    public Sym(RawCoord rawCoord) {
        this.rawCoord = rawCoord;
        File[] filesList = new File(".").listFiles();
        boolean makeRTS = true;
        String strFile = rtsFileName();
        for(File j : filesList) {
            if(j.getName().equals(strFile)) {
                makeRTS = false;
            }
        }
        //I could use c.TableBuilder.getFile, etc. to tell the user which ones.
        if(makeRTS) {
            System.out.println("A requested table has not been made.");
            makeRTS();
        }
        rtsTable = (HashMap<Integer, Integer>) readMapFromFile(rtsFileName());
    }

    public int value(Cube cube) {
        Cube testerCube = cube.clone();
        int sym = rawCoord.value(testerCube);
        for (int i = 1; i < 48; i++) {
            rawCoord.rotate(testerCube, i);
            int coord = rawCoord.value(testerCube);
            if (coord < sym) {
                sym = coord;
            }
            testerCube = cube.clone();
        }
        return rtsTable.get(sym);
    }

    /* the RTS (Raw-To-Sym) table converts a raw coordinate to its sym-coordinate in the following way:
     * a coordinate is rotated all 48 ways, and converted to int for each. the smallest found int
     * will be it's 'identity coordinate' and saved in the RawToSym table. unlike the other tables,
     * a RawToSym table is implemented as an ArrayList. the identity coordinate is saved onto the
     * ArrayList and the index of the identity coordinate is the sym-coordinate.
     * the RawToSym table is also saved in order ascending. */
    public void makeRTS() {
        int coord = 0;
        int max = rawCoord.size();
        int size = 0;
        int sinceLast = 0;
        HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();
        TreeSet<Integer> set = new TreeSet<Integer>();
        while(coord <= max) {
            Cube cube = new Cube();
            rawCoord.setCoord(cube, coord);
            int identity = makeIdentityCoord(cube);
            if(!set.contains(identity)) {
                set.add(identity);
                size ++;
            }
            coord ++;
            if(coord % 500000 == 0) {
                System.out.println("% complete: " + ((double) coord / (max + 1)) * 100);
                System.out.println("table size: " + size);
                System.out.println("size / coord: " + (double) size / coord);
                System.out.println("current %: " + (double) (size - sinceLast) / 5000);
                System.out.println();
                sinceLast = size;
            }
        }
        Iterator<Integer> iter = set.iterator();
        int count = 0;
        while(iter.hasNext()) {
            table.put(iter.next(), count); count ++;
        }
        System.out.println(table.size());
        writeMapToFile(table, rtsFileName());
    }

    //this method finds identity coord by rotating the cube 48 times and finding the smallest value.
    private int makeIdentityCoord(Cube cube) {
        Cube testerCube = cube.clone();
        int sym = rawCoord.value(testerCube);
        for(int i = 1; i < 48; i ++) {
            rawCoord.rotate(testerCube, i);
            int coord = rawCoord.value(testerCube);
            if(coord < sym) {
                sym = coord;
            }
            testerCube = cube.clone();
        }
        return sym;
    }
    /* sets the cube to the identity coord */
    public Cube setCoord(Cube cube, int value) {
        int rawValue = rtsTable.get(value);
        return rawCoord.setCoord(cube, rawValue);
    }

    private String rtsFileName() {
        return "RTS" + rawCoord.name() +"Table";
    }
    public String name() {return "Sym" + rawCoord.name();}
    /* i should check on this. for whatever reason i made maxSize one less than actual (so it's max index actually)
     * the true values are 36, 4347, 106, 38345286, 16930, 3477981 */
    public int size() {return rtsTable.size() - 1;}
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