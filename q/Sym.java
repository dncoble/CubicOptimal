package q;

import c.*;
import defunct.RawCoord;
import h.TableBuilder;

import java.io.File;
import java.util.ListIterator;

/* all Sym coords go through this class. New coordinate functionality only works similar to TableCoordinate */
public class Sym extends SettableCoordinate {
    Coordinate q;
    int[][] table;
    int[] idToSym;
    int qVal;
    
    public Sym(Coordinate q) {
        this.q = q;
        File[] filesList = new File(".").listFiles();
        boolean makeCoordTable = true;
        String tableFile = "CTable" + name();
        String idsFile = "IDSTable" + name();
        for(File j : filesList) {
            if(j.getName().equals(tableFile)) {
                makeCoordTable = false;
            }
        }
        if(makeCoordTable) {
            System.out.println("A requested table has not been made. It will be made now. \n"
                    + "This may take a while.");
            makeTable();
        }
        table = (int[][]) Coordinate.readTableFromFile(tableFile);
        idToSym = (int[]) Coordinate.readTableFromFile(idsFile);
        qVal = q.value();
    }
    
    public void set(Cube cube) {q.set(cube);}
    
    public int value(Cube cube) {
        int identityCoord = makeIdentityCoord(cube);
        // binary search for identityCoord
        int max = idToSym.length-1;
        int min = 0;
        int index = max/2;
        int rtrn = idToSym[index];
        while(rtrn != identityCoord) {
            if(rtrn > identityCoord) {max = index;}
            else {min = index;}
            index = (min + max) / 2;
            rtrn = idToSym[index];
        }
        return rtrn;
    }
     /* DOESN'T WORK WITH NEW COORDINATE FUNCTIONALITY */
    public int value() {return qVal;}
    public void move(int move) {qVal = table[move][qVal];}
    public int size() {return -1;}
    public void set(int qVal){this.qVal = qVal;}
    
    /* this method finds identity coord by rotating the cube 96 times and finding the smallest value. */
    private int makeIdentityCoord(Cube cube) {
        Cube testerCube = cube.clone();
        int sym = q.value(testerCube);
        for(int i = 1; i < 96; i ++) {
            testerCube.rotate(i);
            int coord = q.value(testerCube);
            if(coord < sym) {
                sym = coord;
            }
            testerCube = cube.clone();
        }
        return sym;
    }
    private void makeTable() {TableBuilder.makeSymCoordTable(q, "CTable" + name(), "IDSTable" + name());}
    public Sym clone() {return new Sym(q.clone());}
    public String name() {return "Sym" + q.name();}
}