package main;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import h.*; import c.*; import s.*;

public class Solver {
    public static ArrayList<HashMap<Integer, Short>> rawTables;
    public static ArrayList<Integer> readRawTables;
    public static ArrayList<HashMap<Integer, Short>> symTables;
    public static ArrayList<Integer> readSymTables;
    public static ArrayList<ArrayList<Integer>> rtsTables;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        rawTables = new ArrayList<HashMap<Integer, Short>>();
        readRawTables = new ArrayList<Integer>();
        symTables = new ArrayList<HashMap<Integer, Short>>();
        readSymTables = new ArrayList<Integer>();
        rtsTables = new ArrayList<ArrayList<Integer>>();
        /* asking for scramble(s), asking for tables, then making tables if they have
         * not been made already.*/
        Scanner scan = new Scanner(System.in);
        System.out.println("Print all scrambles you would like to solve. If there are multiple, \n"
                + "separate them by commas.");
        System.out.println("Scrambles: ");
        String rawScrambles = scan.nextLine();
        String[] rawListScrambles = rawScrambles.split(",");
        System.out.println("Enter what tables you wish to use. \n"
                + "		Raw:	Sym: \n"
                + "CO		1		7 \n"
                + "CP		2		8 \n"
                + "EO		3		9 \n"
                + "EP		4		10 \n"
                + "RCO		5		11 \n"
                + "REO		6 		12 \n"
                + "DONE:	0");
        System.out.println("Table: ");
        ArrayList<Integer> tablesUsed = new ArrayList<Integer>();
        String currentTable = scan.nextLine();
        while(!currentTable.equals("0")) {
            try {
                int nextTable = Integer.parseInt(currentTable);
                if(nextTable < 0 || nextTable > 12) {throw new NumberFormatException();}
                tablesUsed.add(nextTable);
                System.out.println("Enter what tables you wish to use. \n"
                        + "		Raw:	Sym: \n"
                        + "CO		1		7 \n"
                        + "CP		2		8 \n"
                        + "EO		3		9 \n"
                        + "EP		4		10 \n"
                        + "RCO		5		11 \n"
                        + "REO		6 		12 \n"
                        + "DONE:	0");
                System.out.println("Table: ");
                currentTable = scan.nextLine();
            }
            catch(NumberFormatException e) {
                System.out.println("Invalid number entered \n"
                        + "		Raw:	Sym: \n"
                        + "CO		1		7 \n"
                        + "CP		2		8 \n"
                        + "EO		3		9 \n"
                        + "EP		4		10 \n"
                        + "RCO		5		11 \n"
                        + "REO		6 		12 \n"
                        + "DONE:	0");
                System.out.println("Table: ");
                currentTable = scan.nextLine();
            }
        }
        ArrayList<Scramble> solvedScrambles = new ArrayList<Scramble>();
        MaxHeuristic h = new MaxHeuristic();
        for(int j : tablesUsed) {
            if(j < 7) {
                ByteHeuristic nextHeuristic = new RawCoordHeuristic(j);
                h.addHeuristic(nextHeuristic);
            }
            else {
                ByteHeuristic nextHeuristic = new SymCoordHeuristic(j);
                h.addHeuristic(nextHeuristic);
            }
        }
        for(String j : rawListScrambles) {
            Scramble identityScr = new Scramble(j);
            Cube cube = new Cube(identityScr);
            IDAStar searcher = new IDAStar(cube, h);
            solvedScrambles.add(searcher.search());
        }
        System.out.println();
        System.out.println("c.Scramble(s) solved! : ");
        for(int m = 0; m < solvedScrambles.size(); m ++) {System.out.println("c.Scramble " + (m + 1) + ": " + solvedScrambles.get(m));}
        scan.close();
    }
}
