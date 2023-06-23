package main;
import c.*;
import h.*;
import q.*;
import s.*;

public class ExpSolver {
    public static void main(String[] args) {
        Scramble scr;
        
        scr = new Scramble("F L2 D' F2 D' L2 F2 U2");
//        scr = new Scramble("F L2 D' F2 D' L2 F2 U2 F2 U' R2 D");
//        scr = new Scramble("F L2 D' F2 D' L2 F2 U2 F2 U' R2 D F2 B R U L' R D' U2 R'");
        int scrambleLength = scr.getLength();
        
        Cube cube = new Cube(scr);
        
//        Coordinate reo = new TabledREO(cube);
        Coordinate rco = new Sym(new RCO());
//        Coordinate cp = new TableCoordinate(new CP(cube));
//        Coordinate eo = new TableCoordinate(new EO(cube));
        
        MaxHeuristic h = new MaxHeuristic();
//        h.addHeuristic(new CoordHeuristic(reo, false));
        h.addHeuristic(new CoordHeuristic(rco, true));
//        h.addHeuristic(new CoordHeuristic(cp, false));
//        h.addHeuristic(new CoordHeuristic(eo, false));
        
        
        
        Search solver = new IDAStar(cube, h);
        long startTime = System.currentTimeMillis();
        Scramble sol = solver.search();
        long endTime = System.currentTimeMillis();
        System.out.println("solved: " + sol);
        System.out.println("scramble of length " + scrambleLength + " solved in " + (endTime-startTime)/1000 + " secs.");
    }
}
