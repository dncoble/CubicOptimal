package defunct;

import c.Cube;
import c.TableBuilder;
import q.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/* funny story-- i refactored my code and couldn't find this class, so i had to remake it. then i
 * out that IntelliJ had decided to nest it within the ByteHeuristic Interface. I don't know which
 * one I want to keep, and i'll have to look deeper at the changes I've done since then. for now it goes
 * here
 */
public class OldSymCoordHeuristic {
    private HashMap<Integer, Byte> symTable;
    private ArrayList<Integer> rtsTable; //should rtsTable be implemented as a primitive list?
    private int type;
    //type is indexed according to:
    //CO		1
    //CP		2
    //EO		3
    //EP		4
    //RCO		5
    //REO		6
    public OldSymCoordHeuristic(int type) {
        this.type = type;
        File[] filesList = new File(".").listFiles();
        boolean makeSymTable = true; boolean makeRTSTable = true;
        String tableFile = TableBuilder.getSymFile(type - 1);
        String rtsFile = TableBuilder.getRawToSymFile(type - 1);
        for(File j : filesList) {
            if(j.getName().equals(tableFile)) {
                makeSymTable = false;
            }
            if(j.getName().equals(rtsFile)) {
                makeRTSTable = false;
            }
        }
        //I could use c.TableBuilder.getFile, etc. to tell the user which ones.
        if(makeSymTable) {
            System.out.println("A requested table has not been made. It will be made now. \n"
                    + "This may take a while.");
            TableBuilder.makeSymTable(type - 1);
        }
        if(makeRTSTable) {
            System.out.println("A requested table has not been made. It will be made now. \n"
                    + "This may take a while.");
            TableBuilder.makeRawToSym(type - 1);
        }
        symTable = (HashMap<Integer, Byte>) TableBuilder.readMapFromFile(TableBuilder.getSymFile(type - 1));
        rtsTable = (ArrayList<Integer>) TableBuilder.readMapFromFile(TableBuilder.getRawToSymFile(type - 1));

    }
    public byte h(Cube cube) {
        return symTable.get(getSymCoord(cube));
    }
    /* this will be changed to take advantage of the /q restructuring when i need to*/
    public int getCoord(Cube cube) {
        if(type == 0) {return (new CO()).value(cube);}
        else if(type == 1) {return (new CP()).value(cube);}
        else if(type == 2) {return (new EO()).value(cube);}
        else if(type == 3) {return (new EP()).value(cube);}
        else if(type == 4) {return (new RCO()).value(cube);}
        else {return (new REO()).value(cube);}
    }
    /* code for this could be potentially optimized when considering the size of the
     * RTS Table, if it is better to constantly check or made identity coord then find
     * where it contains.*/
    public int getSymCoord(Cube cube) {
        Cube testerCube = cube.clone();
        int sym = getCoord(testerCube);
        for(int i = 1; i < 48; i ++) {
            rotateCoord(testerCube, i);
            int coord = getCoord(testerCube);
            if(coord < sym) {
                sym = coord;
            }
            testerCube = cube.clone();
        }
        return rtsTable.indexOf(sym);
    }
    public void rotateCoord(Cube cube, int rotation) {
        if(type == 0) {cube.rotateCO(rotation);}
        else if(type == 1) {cube.rotateCP(rotation);}
        else if(type == 2) {cube.rotateEO(rotation);}
        else if(type == 3) {cube.rotateEP(rotation);}
        else if(type == 4) {cube.rotateCO(rotation); cube.rotateCP(rotation);}
        else {cube.rotateEO(rotation); cube.rotateEP(rotation);}
    }
}
