package defunct;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import c.Cube;
import c.TableBuilder;

/* DEFUNCT -- USE the other h classes for everything. i'm keeping this code here because i don't want to
 * version manage and it may still contain something i want to scavenge. i think everything ended up in h.RawCoordHeuristic,
 * h.ByteHeuristic.SymCoordHeuristic, and h.MaxHeuristic, though.
 */
public class Heuristic {
    public ArrayList<HashMap<Integer, Byte>> rawTables;
    public ArrayList<Integer> readRawTables;
    public ArrayList<HashMap<Integer, Byte>> symTables;
    public ArrayList<Integer> readSymTables;
    public ArrayList<ArrayList<Integer>> rtsTables;

    //tablesUsed contains all the tables that will be used, according to:
    //          Raw 	Sym"
    //CO		1		7
    //CP		2		8
    //EO		3		9
    //EP		4		10
    //RCO		5		11
    //REO		6 		12
    public Heuristic(ArrayList<Integer> tablesUsed) {
        rawTables = new ArrayList<HashMap<Integer, Byte>>();
        readRawTables = new ArrayList<Integer>();
        symTables = new ArrayList<HashMap<Integer, Byte>>();
        readSymTables = new ArrayList<Integer>();
        rtsTables = new ArrayList<ArrayList<Integer>>();
        File[] filesList = new File(".").listFiles();
        ArrayList<Integer> makingTables = new ArrayList<Integer>();
        for (int i : tablesUsed) {
            if (i < 7) {
                String tableFile = TableBuilder.getFile(i - 1);
                boolean contains = false;
                for (File j : filesList) {
                    if (j.getName().equals(tableFile)) {
                        contains = true;
                    }
                }
                if (!contains) {
                    makingTables.add(i);
                }
            } else {
                String tableFile = TableBuilder.getRawToSymFile(i - 7);
                boolean contains = false;
                for (File j : filesList) {
                    if (j.getName().equals(tableFile)) {
                        contains = true;
                    }
                }
                if (!contains) {
                    makingTables.add(i + 6);
                }
                tableFile = TableBuilder.getSymFile(i - 7);
                contains = false;
                for (File j : filesList) {
                    if (j.getName().equals(tableFile)) {
                        contains = true;
                    }
                }
                if (!contains) {
                    makingTables.add(i);
                }
            }
        }
        if (makingTables.size() > 0) {
            System.out.println("Some tables have not been made. They will be made now. \n"
                    + "This may take a while.");
            for (int k : makingTables) {
                if (k < 7) {
                    TableBuilder.makeTable(k - 1);
                } else if (k < 13) {
                    TableBuilder.makeSymTable(k - 7);
                } else {
                    TableBuilder.makeRawToSym(k - 13);
                }
            }
        }
        for (int l : tablesUsed) {
            if (l < 7) {
                rawTables.add((HashMap<Integer, Byte>) TableBuilder.readMapFromFile(TableBuilder.getFile(l - 1)));
                readRawTables.add(l - 1);
            } else {
                symTables.add((HashMap<Integer, Byte>) TableBuilder.readMapFromFile(TableBuilder.getSymFile(l - 7)));
                rtsTables.add((ArrayList<Integer>) TableBuilder.readMapFromFile(TableBuilder.getRawToSymFile(l - 7)));
                readSymTables.add(l - 7);
            }
        }
    }

    //get heuristic
    public byte h(Cube cube) {
        byte depth = 0;
        for (int i = 0; i < rawTables.size(); i++) {
            byte current = rawTables.get(i).get(getCoord(cube, readRawTables.get(i)));
            if (current > depth) {
                depth = current;
            }
        }
        for (int j = 0; j < symTables.size(); j++) {
            byte current = symTables.get(j).get(getSymCoord(cube, readRawTables.get(j), rtsTables.get(j)));
            if (current > depth) {
                depth = current;
            }
        }
        return depth;
    }

    public static int getCoord(Cube cube, int type) {
        if (type == 0) {
            return cube.coToInt();
        } else if (type == 1) {
            return cube.cpToInt();
        } else if (type == 2) {
            return cube.eoToInt();
        } else if (type == 3) {
            return cube.epToInt();
        } else if (type == 4) {
            return cube.rcoToInt();
        } else {
            return cube.reoToInt();
        }
    }

    /* code for this could be potentially optimized when considering the size of the
     * RTS Table, if it is better to constantly check or made identity coord then find
     * where it contains.*/
    public static int getSymCoord(Cube cube, int type, ArrayList<Integer> rawToSymTable) {
        Cube testerCube = cube.clone();
        int sym = getCoord(testerCube, type);
        for (int i = 1; i < 48; i++) {
            rotateCoord(testerCube, type, i);
            int coord = getCoord(testerCube, type);
            if (coord < sym) {
                sym = coord;
            }
            testerCube = cube.clone();
        }
        return rawToSymTable.indexOf(sym);
    }

    public static void rotateCoord(Cube cube, int type, int rotation) {
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
}
