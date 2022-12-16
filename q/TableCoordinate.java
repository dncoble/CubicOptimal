package q;
import c.*;
import h.TableBuilder;

import java.io.File;
import java.util.ListIterator;

/* A TableCoordinate implements exactly in the same way the coordinate which is passed to it. TableCooridinate
 * generates a table with all coordinate movements recorded, so that coordinate lookup is quicker during solving
 * the cube. */
public class TableCoordinate extends SettableCoordinate {
    public int[][] table;
    private Coordinate q;
    private int qVal;
    
    public TableCoordinate(Coordinate q) {
        this.q = q;
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
        table = (int[][]) Coordinate.readTableFromFile(tableFile);
        qVal = q.value();
    }
    public TableCoordinate(Coordinate q, int qVal, int[][] table) {
        this.q = q; this.qVal = qVal; this.table = table;
    }
    public void set(Cube cube) {qVal = value(cube);}
    public void set(int qVal) {this.qVal = qVal;}
    
    public int value(Cube cube) {return q.value(cube);}
    public int value() {return qVal;}
    // look how fast it is!
    public void move(int move) {qVal = table[move][qVal];}
    
    private void makeTable() {TableBuilder.makeCoordTable(q, "CTable" + q.name());}
    
    // Not a good idea to use because all of table is copied
    public TableCoordinate clone() {return new TableCoordinate(q, qVal, table);}
    public String name() {return q.name();}
    public int size() {return q.size();}
}
