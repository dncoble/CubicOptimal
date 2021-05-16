package h;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import c.*;

public class RawCoordHeuristic implements ByteHeuristic {
    private HashMap<Integer, Byte> rawTable;
    private int type;
    //type is indexed according to:
    //CO		1
    //CP		2
    //EO		3
    //EP		4
    //RCO		5
    //REO		6
    public RawCoordHeuristic(int type) {
        this.type = type;
        File[] filesList = new File(".").listFiles();
        boolean makeRawTable = true;
        String tableFile = TableBuilder.getFile(type - 1);
        for(File j : filesList) {
            if(j.getName().equals(tableFile)) {
                makeRawTable = false;
            }
        }
        //I could use c.TableBuilder.getFile, etc. to tell the user which ones.
        if(makeRawTable) {
            System.out.println("A requested table has not been made. It will be made now. \n"
                    + "This may take a while.");
            TableBuilder.makeTable(type - 1);
        }
        rawTable = (HashMap<Integer, Byte>) TableBuilder.readMapFromFile(TableBuilder.getFile(type - 1));
    }

    /* creating an empty rawTable for makeBigTable in c.TableBuilder. the table is used as a heuristic while expanding
     * itself. other specialty methods are also only used for makeBigTable: getRawTable, addToTable, and expH*/
    public RawCoordHeuristic() {rawTable = new HashMap<Integer, Byte>();}

    public byte h(Cube cube) {return rawTable.get(getCoord(cube));}

    public int getCoord(Cube cube) {
        if(type == 0) {return cube.coToInt();}
        else if(type == 1) {return cube.cpToInt();}
        else if(type == 2) {return cube.eoToInt();}
        else if(type == 3) {return cube.epToInt();}
        else if(type == 4) {return cube.rcoToInt();}
        else {return cube.reoToInt();}
    }

    public HashMap<Integer, Byte> getRawTable() {return rawTable;}
    public void addToTable(int coord, byte h) {rawTable.put(coord, h);}

    /* whereas the normal h() method is expected to always return a value, expH (experimental H) might not, because the
     * table is not yet completed. if it cannot, it returns 0 */
    public byte expH(Cube cube) {
        if(rawTable.containsKey(getCoord(cube))) {return rawTable.get(getCoord(cube));}
        else {return 0;}
    }

}