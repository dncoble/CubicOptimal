package main;

import h.ByteHeuristic;
import h.MaxHeuristic;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import c.*; import h.*; import s.*; import q.*;

public class Reseacher {
    public static void main(String[] args) throws IOException {
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
        Scramble fMove = new Scramble("F");
        Scramble f_Move = new Scramble("F'");
        Scramble rMove = new Scramble("R");
        Scramble f2Move = new Scramble("F2");
        Cube f = new Cube(fMove);
        Cube f_ = new Cube(f_Move);
        Cube r = new Cube(rMove);
        Cube f2 = new Cube(f2Move);
        Coordinate sym = new Sym(new REO());
        System.out.println("F: " + sym.value(f));
        System.out.println("F': " + sym.value(f_));
        System.out.println("R: " + sym.value(r));
        System.out.println("F2: " + sym.value(f2));

        f.rotate(1);
        f_.rotate(11);
        r.rotate(21);
        f2.rotate(32);

        System.out.println("F: " + sym.value(f));
        System.out.println("F': " + sym.value(f_));
        System.out.println("R: " + sym.value(r));
        System.out.println("F2: " + sym.value(f2));


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
//    public static ArrayList<c.Scramble> delFakeUseless (ArrayList<c.Scramble>) {
//
//    }
}
