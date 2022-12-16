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
        
        Cube cube = new Cube(scr);
        
        Coordinate q = new TabledREO(cube);
        
        
        ByteHeuristic h = new CoordHeuristic(q, false);
        
        Search solver = new IDAStar(cube, h);
        Scramble sol = solver.search();

        System.out.println("solved: " + sol);
    }
}
