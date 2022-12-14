package q;
import c.*;
import h.TableBuilder;

import java.io.File;
import java.util.ListIterator;

/* A TableCoordinate implements exactly in the same way the coordinate which is passed to it. TableCooridinate
 * generates a table with all coordinate movements recorded, so that coordinate lookup is quicker during solving
 * the cube.
 */
public class TableCoordinate implements Coordinate {
    private int[][] table;
    private Coordinate q;
    private String name;
    private int qVal;
    
    public TableCoordinate(Coordinate q) {
        q = q;
        name = "Table" + q.name();
        File[] filesList = new File(".").listFiles();
        boolean makeCoordTable = true;
        String tableFile = "CTable" + q.name();
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
        table = (int[][]) Coordinate.readTableFromFile(name);
    }
    public void set(Cube cube) {qVal = value(cube);}
    
    public int value(Cube cube) {return q.value(cube);}
    public int value() {return q.value();} // shouldn't ever be used.
    // look how fast it is!
    public void move(int move) {qVal = table[move][qVal];}
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            move(iter.next());
        }
    }
    
    private void makeTable() {
        TableBuilder.makeCoordTable(q, name);
    }
    
    public String name() {return name;}
    public int size() {return q.size();}
}
