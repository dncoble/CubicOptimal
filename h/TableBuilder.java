package h;
import c.*; import h.*; import q.*; import s.*;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/* i've made multiple algorithms for creating tables, and none is strictly better than the rest. because of that, i'm
 * making this class hold all table-making methods. like the last TableBuilder, it is likely that this becomes defuncted
 * later and the code reorganized elsewhere
 */
public class TableBuilder {
    /* this is the method that should be called by all other classes, though i am leaving all methods public. the
     * 'flavor' is the algorithm which will be used, and is immediately called by the below method.
     * flavor = 0: coordinate expansion using setCoord.
     * flavor = 1: coordinate expansion without using setCoord. this requires the cube of all unexpanded coords to be
     *             saved, which means that it requires much too much space to be used with big tables
     */
    public static void makeTable(Coordinate q, int flavor) {
        switch(flavor) {
            case 0: makeTable0(q); break;
            case 1: makeTable1(q); break;
        }
    }
    public static void makeTable0(Coordinate q) {
        int maxSize = q.size();
        Map<Integer, Byte> table = new HashMap<Integer, Byte>();
        Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
        table.put(0,(byte) 0); int tableSize = 1;
        unexpandedQ.add(0);
        int[] data = new int[21];
        while(!unexpandedQ.isEmpty()) {
            int currentQ = unexpandedQ.poll(); unexpandedSize --;
            Cube cube = q.setCoord(new Cube(), currentQ);
            int length = table.get(currentQ);
            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
            cube.move(scr);
            if(!table.containsKey(q.value(cube))) {
                table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
                unexpandedQ.add(q.value(cube)); unexpandedSize ++;
                data[length + 1] ++;
            }
            else {} // with queue, if it is already in the table, it must have length less or equal to
            for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
                cube.move(scr.iterate());
                if(!table.containsKey(q.value(cube))) {
                    table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
                    unexpandedQ.add(q.value(cube)); unexpandedSize ++;
                    data[length +1] ++;
                }
            }
            if (tableSize % 10000 == 0) {
                System.out.println("table size: " + tableSize);
                System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
                System.out.println("length: " + length);
                System.out.println("unexpanded nodes: " + unexpandedSize);
            }
        }
        for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
        writeMapToFile((Serializable) table, q.name() + "Table");
    }
    public static void makeTable1(Coordinate q) {
        int maxSize = q.size();
        Map<Integer, Byte> table = new HashMap<Integer, Byte>();
        Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
        Queue<Cube> unexpandedC = new LinkedList<Cube>();
        table.put(0,(byte) 0); int tableSize = 1;
        unexpandedQ.add(0);
        unexpandedC.add(new Cube());
        int[] data = new int[21];
        while(!unexpandedQ.isEmpty()) {
            int currentQ = unexpandedQ.poll(); unexpandedSize --;
            Cube cube = unexpandedC.poll();
            int length = table.get(currentQ);
            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
            cube.move(scr);
            if(!table.containsKey(q.value(cube))) {
                table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
                unexpandedQ.add(q.value(cube)); unexpandedSize ++;
                unexpandedC.add(cube.clone());
                data[length + 1] ++;
            }
            else {} // with queue, if it is already in the table, it must have length less or equal to
            for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
                cube.move(scr.iterate());
                if(!table.containsKey(q.value(cube))) {
                    table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
                    unexpandedQ.add(q.value(cube)); unexpandedSize ++;
                    unexpandedC.add(cube.clone());
                    data[length +1] ++;
                }
            }
            if (tableSize % 10000 == 0) {
                System.out.println("table size: " + tableSize);
                System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
                System.out.println("length: " + length);
                System.out.println("unexpanded nodes: " + unexpandedSize);
            }
        }
        for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
        writeMapToFile((Serializable) table, q.name() + "Table");
    }


    /* code for writeObjectToFile and readObjectFrom File
     * has been copied and modified to fit my purposes from
     * https://examples.javacodegeeks.com/core-java/io/fileoutputstream/how-to-write-an-object-to-file-in-java/
     * since i was never taught saving files in CompSci. */
    public static void writeMapToFile(Serializable table, String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(table);
            objectOut.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static Object readMapFromFile(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object rtrnMap = objectIn.readObject();
            objectIn.close();
            return rtrnMap;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
