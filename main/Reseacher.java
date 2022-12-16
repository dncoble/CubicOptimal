package main;

import defunct.OldCube;
import h.ByteHeuristic;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import c.*; import h.*;
import q.*;

import javax.swing.*;
import javax.swing.JFrame;
import java.util.LinkedList;

public class Reseacher {
    public static void main(String[] args) throws IOException {
        
        Coordinate reo = new TabledREO();
        TableBuilder.makeTable(reo, 7);
        
//        int i = 0;
//        while(i < reo.size()) {
//            reo.set(i);
//            int j = reo.value();
//            if(i != j) {
//                System.out.println("problem on " + i + " produces " + j);
//            }
//            if(i % 10000 == 0) {
//                System.out.println("checked up to " + i);
//            }
//            i++;
//        }
        
        
//        Scramble scr = new Scramble("F U R");
//        Cube cube = new Cube(scr);
//        Coordinate q1 = new CP(cube);
//        Coordinate q2 = new CP();
//        q2.move(scr);
//        ByteHeuristic h = new CoordHeuristic(q2, false);
//        h.move(new Scramble("R' U' F'"));
//        q.move(new Scramble("R'"));
//        System.out.println(h.h());
//        System.out.println(q1.value());
//        System.out.println(q2.value());
        
//        System.out.println(h.h());
        
//        generatePermutationMoves();
//        System.out.println("CO timing: " + testCoordinateTiming(new CO()));
//        System.out.println("CP timing: " + testCoordinateTiming(new CP()));
//        System.out.println("EO timing: " + testCoordinateTiming(new EO()));
//        System.out.println("EP timing: " + testCoordinateTiming(new EP()));
//        System.out.println("RCO timing: " + testCoordinateTiming(new RCO()));
//        System.out.println("REO timing: " + testCoordinateTiming(new REO()));
        
//        System.out.println("Scramble iteration timing: " + testScrambleIteration());
//        System.out.println("Cube iteration timing: " + testCubeTiming());
//        System.out.println("Total iteration timing: " + testIterationTiming());
        
//        int coord = 12;
//        Coordinate q;
//        boolean isSym;
//        switch(coord) {
//            case 1: q = new CO(); isSym = false; break;
//            case 2: q = new CP(); isSym = false; break;
//            case 3: q = new EO(); isSym = false; break;
//            case 4: q = new EP(); isSym = false; break;
//            case 5: q = new RCO(); isSym = false; break;
//            case 6: q = new REO(); isSym = false; break;
////                case 7: q = new Sym(new CO()); isSym = true; break;
//            case 8: q = new Sym(new CP()); isSym = true; break;
////                case 9: q = new Sym(new EO()); isSym = true; break;
//            case 10: q = new Sym(new EP()); isSym = true; break;
//            case 11: q = new Sym(new RCO()); isSym = true; break;
//            case 12: q = new Sym(new REO()); isSym = true; break;
//            default: q = null; isSym = false; break;
//        }
//        CoordHeuristic h = new CoordHeuristic(q, isSym);
//        int[] data = h.getDistribution();
//        for(int o = 0; o < 21; o++) {System.out.println("|" + o + "|" + data[o] + "|");}
//        System.out.println("size: " + h.size());
//        System.out.println("avg. PP: " + h.avgPredPower());
        
//        System.out.println("Heuristic timing: " + testHeuristicTiming(h));
        
//        generatePermutationRotations();
        
//        ArrayList<String> stringAList = new ArrayList<String>();
//        try {
//            Scanner scanner = new Scanner(new File("useless10"));
//            while (scanner.hasNextLine()) {
//                stringAList.add(scanner.nextLine());
//            }
//            scanner.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        ArrayList<c.Scramble> allScrs = new ArrayList<c.Scramble>();
//        while(stringAList.size() != 0) {
//            allScrs.append(new c.Scramble(stringAList.get(0)));
//            allScrs.remove(0);
//        }
        
//        Cube cube = new Cube();
//      
//        Cube testerCube = cube.clone();
//
//        testerCube.move(new Scramble("D"));
//        
//        testerCube.rotate(24);
//        visualize(testerCube);
//
//        int[] co = testerCube.getCO();
//        for(int i = 0; i < co.length; i ++) {
//            System.out.println(co[i]);
//        }
//        
//        testerCube.rotate(0);
//        System.out.println("rotated; ");
//        co = testerCube.getCO();
//        for(int i = 0; i < co.length; i ++) {
//            System.out.println(co[i]);
//        }
        
//        int[][] xwise = {{4,36,23},{31,28},{14,34,7},{3,31,12,13,35,8,6,29,21},{42,40,41,47,46,44},{15,25,0,2,33,19,18,27,9},
//                {1,32,20},{24,26},{17,38,10},{16,39,11},{43,45},{5,37,22}};
//        int[][] ywise = {{3,42,15},{31,25},{12,40,0},{4,30,14,13,41,2,1,24,17,16,43,5},{36,34,35,33,32,38,39,37},
//                {23,28,7,8,47,19,20,26,10,11,45,22},{6,46,18},{29,27},{21,44,9}};
//
//
//        int[] xpositions = new int[48];
//        int[] ypositions = new int[48];
//
//        for(int i = 0; i < 48; i ++) {
//            int x = -1;
//            int y = -1;
//            for(int j = 0; j < xwise.length; j++) {
//                for(int k = 0; k < xwise[j].length; k++) {
//                    if(xwise[j][k] == i) {
//                        x = j;
//                    }
//                }
//            }
//            for(int j = 0; j < ywise.length; j++) {
//                for(int k = 0; k < ywise[j].length; k++) {
//                    if(ywise[j][k] == i) {
//                        y = j;
//                    }
//                }
//            }
//            xpositions[i] = x;
//            ypositions[i] = y;
//        }
//        System.out.print("\n{");
//        for(int i = 0; i < 48; i ++){
//            System.out.print(xpositions[i] + ",");
//        }
//        System.out.println('}');
//        System.out.print('{');
//        for(int i = 0; i < 48; i ++){
//            System.out.print(ypositions[i] + ",");
//        }
//        System.out.print('}');
//        testerCube = cube.clone();
//        testerCube.move(new Scramble("F"));
//        testerCube.rotate(1);
//
//        visualize(testerCube);
        
        
//        Scramble fMove = new Scramble("F");
//        Scramble f_Move = new Scramble("F'");
//        Scramble rMove = new Scramble("R");
//        Scramble f2Move = new Scramble("F2");
//        Cube f = new Cube(fMove);
//        Cube f_ = new Cube(f_Move);
//        Cube r = new Cube(rMove);
//        Cube f2 = new Cube(f2Move);
//        Coordinate sym = new Sym(new REO());
//        System.out.println("F: " + sym.value(f));
//        System.out.println("F': " + sym.value(f_));
//        System.out.println("R: " + sym.value(r));
//        System.out.println("F2: " + sym.value(f2));
//
//        f.rotate(1);
//        f_.rotate(11);
//        r.rotate(21);
//        f2.rotate(32);
//
//        System.out.println("F: " + sym.value(f));
//        System.out.println("F': " + sym.value(f_));
//        System.out.println("R: " + sym.value(r));
//        System.out.println("F2: " + sym.value(f2));


// making big coordinates
//        ByteHeuristic coH = new RawCoordHeuristic(1);
//        ByteHeuristic cpH = new RawCoordHeuristic(2);
//        ByteHeuristic eoH = new RawCoordHeuristic(3);
//        MaxHeuristic smallH = new MaxHeuristic();
//        smallH.addHeuristic(coH);
//        smallH.addHeuristic(cpH);
//        smallH.addHeuristic(eoH);
//
//        TableBuilder.makeBigTable(3, smallH);

//        int testDistance = 7;
//        String[] scrs = fileLineByLine("bigSet" + testDistance + ".txt");
//        int size = scrs.length;
//        System.out.println("length: " + size);
//        String cubeCoordsFinal = ""; //co, cp, eo, ep, distance
//        c.Cube cube;
//        String coords = "";
//        int count = 0;
//        BufferedWriter writer = new BufferedWriter(new FileWriter("bigCoords7Sys2.txt"));
//        for(String stringScr:scrs) {
//            c.Scramble scr = new c.Scramble(stringScr);
//            cube = new c.Cube(scr);
//            for(int i : cube.getCO()) {coords += i + ",";}
//            for(int i : cube.getCP()) {
//                for(int j=0; j < 8; j++) {
//                    if(j == i) {coords += "1,";}
//                    else {coords += "0,";}
//                }
//            }
//            for(int i : cube.getEO()) {coords += i + ",";}
//            for(int i : cube.getEP()) {
//                for(int j=0; j < 12; j++) {
//                    if(j == i) {coords += "1,";}
//                    else {coords += "0,";}
//                }
//            }
//            count ++;
//            coords += scr.getLength();
//            writer.write(coords);
//            if(count < scrs.length) {writer.write('\n');}
//            coords = "";
//            if(count % 20000 == 0) {System.out.println("working... " +((double) count / size) * 100 + "%" );}
//        }
//        System.out.println(count);
//        writer.close();
    }

    public static double testScrambleIteration() {
        Scramble scr = new Scramble(Integer.MIN_VALUE, 7);
        long startTime = System.nanoTime();
        int n = 25000000;
        for(int i = 0; i < n; i ++) {
            scr.iterate();
        }
        long stopTime = System.nanoTime();
        return (double) (stopTime - startTime) / n;
    }
    //an iteration is basically 2 moves.
    public static double testCubeTiming() {
        long elapsedTime = 0;
        Scramble scr = new Scramble(Integer.MIN_VALUE, 7);
        Cube cube = new Cube();
        int n = 25000000;
        for(int i = 0; i < n; i ++) {
            Scramble prt = scr.iterate();
            long startTime = System.nanoTime();
            cube.move(prt);
            long stopTime = System.nanoTime();
            elapsedTime += stopTime - startTime;
        }
        return (double) elapsedTime/n;
    }
    
    public static double testIterationTiming() {
        long elapsedTime = 0;
        Scramble scr = new Scramble(Integer.MIN_VALUE, 7);
        Cube cube = new Cube();
        int n = 25000000;
        for(int i = 0; i < n; i ++) {
            long startTime = System.nanoTime();
            cube.move(scr.iterate());
            long stopTime = System.nanoTime();
            elapsedTime += stopTime - startTime;
        }
        return (double) elapsedTime/n;
    }
    
    public static double testCoordinateTiming(Coordinate c) {
        long elapsedTime = 0;
        Scramble scr = new Scramble(Integer.MIN_VALUE, 7);
        Cube cube = new Cube();
        int n = 25000000;
        for(int i = 0; i < n; i ++) {
            cube.move(scr.iterate());
            long startTime = System.nanoTime();
            int j = c.value(cube);
            long stopTime = System.nanoTime();
            elapsedTime += stopTime - startTime;
        }
        return (double) elapsedTime/n;
    }
    // must subtract coord timing to get lookup timing
    public static double testHeuristicTiming(ByteHeuristic h) {
        long elapsedTime = 0;
        Scramble scr = new Scramble(Integer.MIN_VALUE, 7);
        Cube cube = new Cube();
        int n = 250000;
        for(int i = 0; i < n; i ++) {
            cube.move(scr.iterate());
            long startTime = System.nanoTime();
            byte j = h.h(cube);
            long stopTime = System.nanoTime();
            elapsedTime += stopTime - startTime;
        }
        return (double) elapsedTime/n;
    }
    
    /*this code just takes a text file and reads it into a String[]
     * it was taken and modified from journaldev.com */
    public static String[] fileLineByLine(String filepath) {
        ArrayList<String> stringAList = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(new File(filepath));
            while (scanner.hasNextLine()) {
                stringAList.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] stringArray = new String[stringAList.size()];
        for (int i = 0; i < stringAList.size(); i++) {
            stringArray[i] = stringAList.get(i);
        }
        return stringArray;
    }

    public static void toFile(String str, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(str);
        writer.close();
    }

    public static Scramble[] stringToScrambleList(String[] stringList) {
        Scramble[] scrList = new Scramble[stringList.length];
        for (int i = 0; i < stringList.length; i++) {
            scrList[i] = new Scramble(stringList[i]);
        }
        return scrList;
    }

    /* this finds all the useless algorithms of a given length by simple iteration
     * my use of longs here means that this implementation only works for up to like 16 or 17 i think*/
    public static void findUseless(int length, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        Cube cube = new Cube();
        Cube solved = new Cube();
        Scramble scr = new Scramble(Integer.MIN_VALUE, length);
        System.out.println(scr);
        cube.move(scr);
        int found = 0;
        long i = 0;
        long max = 18 * (long) Math.pow(15, length - 1) - 1;
        while (i < max) {
            cube.move(scr.iterate());
            if (cube.equals(solved)) {
                writer.write(scr.toString());
                writer.newLine();
                found++;
                System.out.println("Found one!" + scr.toString() + " (" + found + " found)");
            }
            i++;
            if (i % 100000000 == 0) {
                System.out.println("continuing..." + ((double) i / max) * 100 + "%");
            }
        }
        writer.close();
        System.out.println(scr);
    }
    // use old cube class to generate permutations for new cube class
    public static void generatePermutationMoves() {
        OldCube cube = new OldCube();
        OldCube testerCube = cube.clone();
        Scramble scr = new Scramble("F");
        testerCube.move(scr);
        for(int i=0; i < 17; i ++) {
            System.out.print('{');
            byte[] p = testerCube.getPermutation();
            for (int j = 0; j < p.length; j++) {
                System.out.print(p[j] + ",");
            }
            System.out.println('}');
            testerCube.move(scr.iterate());
        }
        byte[] p = testerCube.getPermutation();
        for (int j = 0; j < p.length; j++) {
            System.out.print(p[j] + ",");
        }
        System.out.println('}');
        
    }
//    
//    public static void generatePermutationRotations() {
//
//        Cube cube = new Cube();
//        Cube testerCube = cube.clone();
//
//        testerCube.move(new Scramble("L R'"));
//
//        int[] co = testerCube.getCO();
//        int[] cp = testerCube.getCP();
//        int[] eo = testerCube.getEO();
//        int[] ep = testerCube.getEP();
//
//        ep[8] = 9; ep[9] = 10; ep[10] = 11; ep[11] = 8;
//        eo[8] = 1; eo[9] = 1; eo[10] = 1; eo[11] = 1;
//
//        Cube testerCube1 = new Cube(co, cp, eo, ep);
//
//        Permutation x = new Permutation(testerCube1.getPermutation());
//        Permutation x2 = x.multiply(x);
//        Permutation xPrime = x2.multiply(x);
//
//        testerCube = cube.clone();
//
//        testerCube.move(new Scramble("U' D"));
//
//        co = testerCube.getCO();
//        cp = testerCube.getCP();
//        eo = testerCube.getEO();
//        ep = testerCube.getEP();
//
//        ep[4] = 5; ep[5] = 6; ep[6] = 7; ep[7] = 4;
//        eo[4] = 1; eo[5] = 1; eo[6] = 1; eo[7] = 1;
//
//        Cube testerCube2 = new Cube(co, cp, eo, ep);
//
//        Permutation y = new Permutation(testerCube2.getPermutation());
//        Permutation y2 = y.multiply(y);
//        Permutation yPrime= y2.multiply(y);
//
//        testerCube = cube.clone();
//
//        testerCube.move(new Scramble("F' B"));
//
//        co = testerCube.getCO();
//        cp = testerCube.getCP();
//        eo = testerCube.getEO();
//        ep = testerCube.getEP();
//
//        ep[0] = 1; ep[1] = 2; ep[2] = 3; ep[3] = 0;
//        eo[0] = 1; eo[1] = 1; eo[2] = 1; eo[3] = 1;
//
//        Cube testerCube3 = new Cube(co, cp, eo, ep);
//
//        Permutation z = new Permutation (testerCube3.getPermutation());
//        Permutation zPrime = z.multiply(z.multiply(z));
//
//        byte[] mirList = {12,14,13,15,17,16,18,20,19,21,23,22,0,2,1,3,5,4,6,8,7,9,11,10,30,31,28,29,26,27,24,25,34,35,32,33,38,39,36,37,40,41,42,43,44,45,46,47};
//
//        Permutation mirror = new Permutation(mirList);
//
//        Permutation identity = new Permutation(48);
//
//        Permutation[] firstRotList = {identity, z, zPrime, x, xPrime, x2};
//        Permutation[] secondRotList = {identity, y, y2, yPrime};
//        Permutation[] thirdRotList = {identity, mirror};
//
//        for(int rotation = 0; rotation < 48; rotation ++) {
//            int rotCopy = rotation;
//            Permutation firstRot = firstRotList[rotCopy % 6];
//            rotCopy /= 6;
//            Permutation secRot = secondRotList[rotCopy % 4];
//            rotCopy /= 4;
//            Permutation thirdRot = thirdRotList[rotCopy];
//
//            Permutation rot = firstRot.multiply(secRot).multiply(thirdRot);
//
//            System.out.print("{");
//            for(int i = 0; i < 48; i ++){
//                System.out.print(rot.getPermutation()[i] + ",");
//            }
//            System.out.println("},");
//        }
//    }
//    public static void searchRotationInverse() {
//        Permutation[] allRotations = MoveTables.rotations;
//        Permutation[] allInverses = new Permutation[48];
//        for(int i = 0; i < 48; i ++) {
//            allInverses[i] = allRotations[i].invert();
//            if (i == 47) {
//                System.out.println(allInverses[i]);
//            }
//        }
//        int[] inverseIndices = new int[48];
//        for(int i = 0; i < 48; i ++) {inverseIndices[i] = -1;}
//        for(int i = 0; i < 48; i ++) { // iterate across rotations
//            for(int j = 0; j < 48; j ++) { //iterate across inverses
//                boolean areSame = true;
//                for(int k = 0; k < 48; k ++) {
//                    if(allRotations[i].getPermutation()[k] != allInverses[j].getPermutation()[k]) {
//                        areSame = false;
//                    }
//                }
//                if(areSame) {
//                    inverseIndices[i] = j;
//                }
//            }
//        }
//        System.out.print("{");
//        for(int i = 0; i < 48; i ++){
//            System.out.print(inverseIndices[i] + ",");
//        }
//        System.out.println("}");
//    }
}

