package h;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import c.*;
import q.*;

public class SymCoordHeuristic implements ByteHeuristic {
    private HashMap<Integer, Byte> symTable;
    private ArrayList<Integer> rtsTable;
    private int type;
    //type is indexed according to:
    //CO		1
    //CP		2
    //EO		3
    //EP		4
    //RCO		5
    //REO		6
    public SymCoordHeuristic(int type) {
        this.type = type;
        File[] filesList = new File(".").listFiles();
        boolean makeSymTable = true; boolean makeSTR = true;
        String tableFile = TableBuilder.getSymFile(type - 1);
        String strFile = TableBuilder.getRawToSymFile(type -1);
        for(File j : filesList) {
            if(j.getName().equals(tableFile)) {
                makeSymTable = false;
            }
            if(j.getName().equals(strFile)) {
                makeSTR = false;
            }
        }
        //I could use c.TableBuilder.getFile, etc. to tell the user which ones.
        if(makeSymTable) {
            System.out.println("A requested table has not been made. It will be made now. \n"
                    + "This may take a while.");
            TableBuilder.makeTable(type - 1);
        }
        if(makeSTR) {
            System.out.println("A requested table has not been made. It will be made now. \n"
                    + "This may take a while.");
            TableBuilder.makeSymTable(type - 1);
        }
        symTable = (HashMap<Integer, Byte>) TableBuilder.readMapFromFile(TableBuilder.getFile(type - 1));
        rtsTable = (ArrayList<Integer>) TableBuilder.readMapFromFile(TableBuilder.getFile(type -1));
    }

    /* makeBigTable stuff, although it only works on RawCoordHeuristic I think*/
    public SymCoordHeuristic() {
        symTable = new HashMap<Integer, Byte>();
        rtsTable = new ArrayList<Integer>();
    }

    public byte h(Cube cube) {return symTable.get(getSymCoord(cube));}
    /* to be honest heursitic is going to see a lot of changes soon, so I don't feel that bad
     * about this little workaround, which will probably be completely obsoleced*/
    public int getCoord(Cube cube) {
        if(type == 0) {return (new CO()).value(cube);}
        else if(type == 1) {return (new CP()).value(cube);}
        else if(type == 2) {return (new EO()).value(cube);}
        else if(type == 3) {return (new EP()).value(cube);}
        else if(type == 4) {return (new RCO()).value(cube);}
        else {return (new REO()).value(cube);}
    }
    public int getSymCoord(Cube cube) {
        Cube testerCube = cube.clone();
        int sym = getRawCoord(testerCube);
        for (int i = 1; i < 48; i++) {
            rotateCoord(testerCube, i);
            int coord = getCoord(testerCube);
            if (coord < sym) {
                sym = coord;
            }
            testerCube = cube.clone();
        }
        return rtsTable.indexOf(sym);
    }

    public int getRawCoord(Cube cube) {
        if(type == 0) {return (new CO()).value(cube);}
        else if(type == 1) {return (new CP()).value(cube);}
        else if(type == 2) {return (new EO()).value(cube);}
        else if(type == 3) {return (new EP()).value(cube);}
        else if(type == 4) {return (new RCO()).value(cube);}
        else {return (new REO()).value(cube);}
    }

    public void rotateCoord(Cube cube, int rotation) {
        if (type == 0) {
            cube.rotateCO(rotation);
        } else if (type == 1) {
            cube.rotateCP(rotation);
        } else if (type == 2) {
            cube.rotateEO(rotation);
        } else if (type == 3) {
            cube.rotateEP(rotation);
        } else if (type == 4) {
            cube.rotateCO(rotation);
            cube.rotateCP(rotation);
        } else {
            cube.rotateEO(rotation);
            cube.rotateEP(rotation);
        }
    }

    public HashMap<Integer, Byte> getRawTable() {return symTable;}
    public void addToTable(int coord, byte h) {symTable.put(coord, h);}

    /* whereas the normal h() method is expected to always return a value, expH (experimental H) might not, because the
     * table is not yet completed. if it cannot, it returns 0 */
    public byte expH(Cube cube) {
        int symCoord = getSymCoord(cube);
        if(symTable.containsKey(symCoord)) {return symTable.get(symCoord);}
        else {return 0;}
    }

}