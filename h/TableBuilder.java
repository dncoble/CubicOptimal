package h;
import c.*; import h.*; import q.*; import s.*;

import java.io.*;
import java.util.*;

/* i've made multiple algorithms for creating tables, and none is strictly better than the rest. because of that, i'm
 * making this class hold all table-making methods. like the last TableBuilder, it is likely that this becomes defuncted
 * later and the code reorganized elsewhere
 */
public class TableBuilder {
    /* this is the method that should be called by all other classes, though i am leaving all methods public. the
     * 'flavor' is the algorithm which will be used, and is immediately called by the below method.
     * flavor = 0: coordinate expansion using setCoord. Depricated since setCoord was removed
     * flavor = 1: coordinate expansion without using setCoord. this requires the cube of all unexpanded coords to be
     *             saved, which means that it requires too much space to be used with big tables
     * flavor = 2: coordinate expansion without using setCoord, saves the scramble tree to regenerate cubes.
     *             not yet implemented.
     * flavor = 4: used with x96 expansion.
     * flavor = 5: for raw coords, like flavor = 1, but only saving identity cubes.
     * flavor = 6: use for new coords (>0.3.1)
     * flavor = 7: use for SettableCoordinates (TabledCoordinate and TabledMultiCoordinate)
     */
    public static void makeTable(Coordinate q, int flavor) {
        switch(flavor) {
//            case 0: makeTable0(q); break;
            case 1: makeTable1(q); break;
//            case 2: makeTable2(q); break;
//            case 3: makeTable3(q); break;
            case 4: makeTable4(q); break;
//            case 5: makeTable5(q); break;
            case 6 : makeTable6(q); break;
            case 7 : makeTable7((SettableCoordinate) q); break;
        }
    }
//    public static void makeTable0(Coordinate q) {
//        int maxSize = q.size();
//        Map<Integer, Byte> table = new HashMap<Integer, Byte>();
//        Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
//        table.put(0,(byte) 0); int tableSize = 1;
//        unexpandedQ.add(0);
//        int[] data = new int[21]; data[0] = 1;
//        while(!unexpandedQ.isEmpty()) {
//            int currentQ = unexpandedQ.poll(); unexpandedSize --;
//            Cube cube = q.setCoord(new Cube(), currentQ);
//            int length = table.get(currentQ);
//            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
//            cube.move(scr);
//            if(!table.containsKey(q.value(cube))) {
//                table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
//                unexpandedQ.add(q.value(cube)); unexpandedSize ++;
//                data[length + 1] ++;
//            }
//            else {} // with queue, if it is already in the table, it must have length less or equal to
//            for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
//                cube.move(scr.iterate());
//                if(!table.containsKey(q.value(cube))) {
//                    table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
//                    unexpandedQ.add(q.value(cube)); unexpandedSize ++;
//                    data[length +1] ++;
//                }
//            }
//            if (tableSize % 10000 == 0) {
//                System.out.println("table size: " + tableSize);
//                System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
//                System.out.println("length: " + length);
//                System.out.println("unexpanded nodes: " + unexpandedSize);
//            }
//        }
//        for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
//        writeMapToFile((Serializable) table, q.name() + "Table");
//    }
    public static void makeTable1(Coordinate q) {
        Map<Integer, Byte> table = new HashMap<Integer, Byte>();
        Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
        Queue<Cube> unexpandedC = new LinkedList<Cube>();
        table.put(0,(byte) 0); int tableSize = 1;
        unexpandedQ.add(0); // do i need this?
        unexpandedC.add(new Cube());
        int nodesExpanded = 0; // only for controlling printing
        int[] data = new int[21]; data[0] = 1;
        while(!unexpandedQ.isEmpty()) {
            nodesExpanded ++;
            int currentQ = unexpandedQ.poll(); unexpandedSize --;
            Cube cube = unexpandedC.poll();
            int length = table.get(currentQ);
            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
            cube.move(scr);
            int val = q.value(cube);
            if(!table.containsKey(val)) { //does run faster with table(q.value)==null?
//            if(table.get(val) == null)
                table.put(val, (byte) (length + 1)); tableSize ++;
                unexpandedQ.add(val); unexpandedSize ++;
                unexpandedC.add(cube.clone());
                data[length + 1] ++;
            }
            else {} // with queue, if it is already in the table, it must have length less or equal to
            for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
                cube.move(scr.iterate());
                val = q.value(cube);
                if(!table.containsKey(q.value(cube))) {
                    table.put(val, (byte) (length + 1)); tableSize ++;
                    unexpandedQ.add(val); unexpandedSize ++;
                    unexpandedC.add(cube.clone());
                    data[length +1] ++;
                }
            }
            if (nodesExpanded % 50000 == 0) {
                System.out.println("nodes expanded: " + nodesExpanded);
                System.out.println("table size: " + tableSize);
                System.out.println("length: " + length);
                System.out.println("unexpanded nodes: " + unexpandedSize);
            }
        }
        for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
        byte[] byteTable = new byte[table.size()];
        Iterator<Integer> iter = table.keySet().iterator();
        while(iter.hasNext()) {
            int qVal = iter.next();
            byteTable[qVal] = table.get(qVal);
        }
        writeMapToFile(byteTable, q.name() + "Table");
    }
    
//    public static void makeTable2(Coordinate q) {
//        int maxSize = q.size();
//        Map<Integer, Byte> table = new HashMap<Integer, Byte>();
//        Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
//        table.put(0,(byte) 0); int tableSize = 1;
//        unexpandedQ.add(0);
//        int[] data = new int[21]; data[0] = 1;
//        while(!unexpandedQ.isEmpty()) {
//            int currentQ = unexpandedQ.poll(); unexpandedSize --;
//            Cube cube = q.setCoord(new Cube(), currentQ);
//            int length = table.get(currentQ);
//            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
//            cube.move(scr);
//            if(!table.containsKey(q.value(cube))) {
//                table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
//                unexpandedQ.add(q.value(cube)); unexpandedSize ++;
//                data[length + 1] ++;
//            }
//            else {} // with queue, if it is already in the table, it must have length less or equal to
//            for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
//                cube.move(scr.iterate());
//                if(!table.containsKey(q.value(cube))) {
//                    table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
//                    unexpandedQ.add(q.value(cube)); unexpandedSize ++;
//                    data[length +1] ++;
//                }
//            }
//            if (tableSize % 10000 == 0) {
//                System.out.println("table size: " + tableSize);
//                System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
//                System.out.println("length: " + length);
//                System.out.println("unexpanded nodes: " + unexpandedSize);
//            }
//        }
//        for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
//        writeMapToFile((Serializable) table, q.name() + "Table");
//    }
    // this doesn't work. do i need it?
//    public static void makeTable3(Coordinate q) {
//        int maxSize = q.size();
//        Map<Integer, Byte> table = new HashMap<Integer, Byte>();
//        Queue<Node> unexpandedQ = new LinkedList<Node>(); int unexpandedSize = 0;
//        table.put(0,(byte) 0); int tableSize = 1;
//        unexpandedQ.add(new Node(0, (byte) -1, null)); //coord and move will be accessed
//        int[] data = new int[21]; data[0] = 1;
//        while(!unexpandedQ.isEmpty()) {
//            Node currentQ = unexpandedQ.poll(); unexpandedSize --;
//            int length = table.get(currentQ.coord);
//            Cube cube = new Cube();
//            Scramble scr = new Scramble();
//            Node iterQ = currentQ;
//            while(iterQ.hasParent()) {
//                scr.addFirst((int) currentQ.move);
//                iterQ = iterQ.parent;
//            }
//            cube.move(scr);
//            scr = new Scramble(Integer.MIN_VALUE, 1);
//            cube.move(scr);
//            int value = q.value(cube);
//            if(!table.containsKey(value)) {
//                table.put(value, (byte) (length + 1)); tableSize ++;
//                unexpandedQ.add(new Node(value, (byte) 0, currentQ)); unexpandedSize ++;
//                data[length + 1] ++;
//            }
//            else {} // with queue, if it is already in the table, it must have length less or equal to
//            for(int i = 1; i < 18; i ++) { //some efficiency could be gained by only checking 15
//                cube.move(scr.iterate());
//                value = q.value(cube);
//                if(!table.containsKey(value)) {
//                    table.put(q.value(cube), (byte) (length + 1)); tableSize ++;
//                    unexpandedQ.add(new Node(value, (byte) i, currentQ)); unexpandedSize ++;
//                    data[length + 1] ++;
//                }
//            }
//            if (tableSize % 10000 == 0) {
//                System.out.println("table size: " + tableSize);
//                System.out.println("% complete: " + ((double) tableSize / (maxSize + 1)) * 100);
//                System.out.println("length: " + length);
//                System.out.println("unexpanded nodes: " + unexpandedSize);
//            }
//        }
//        for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
//        writeMapToFile((Serializable) table, q.name() + "Table");
//    }
    
    public static void makeTable4(Coordinate q) {
        Map<Integer, Byte> table = new HashMap<Integer, Byte>();
        Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
        Queue<Cube> unexpandedC = new LinkedList<Cube>();
        table.put(0,(byte) 0); int tableSize = 1;
        unexpandedQ.add(0);
        unexpandedC.add(new Cube());
        int nodesExpanded = 0; // only for controlling printing
        int[] data = new int[21]; data[0] = 1;
        while(!unexpandedQ.isEmpty()) {
            nodesExpanded ++;
            int currentQ = unexpandedQ.poll(); unexpandedSize --;
            Cube cube = unexpandedC.poll();
            Cube cubeinv = cube.clone();
            cubeinv.invert();
            int length = table.get(currentQ);
            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
            cube.move(scr);
            int val = q.value(cube);
            if(!table.containsKey(val)) { //does run faster with table(q.value)==null?
                table.put(val, (byte) (length + 1)); tableSize ++;
                unexpandedQ.add(val); unexpandedSize ++;
                unexpandedC.add(cube.clone());
                data[length + 1] ++;
            }
            else {} // with queue, if it is already in the table, it must have length less or equal to
            for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
                cube.move(scr.iterate());
                val = q.value(cube);
                if(!table.containsKey(val)) {
                    table.put(val, (byte) (length + 1)); tableSize ++;
                    unexpandedQ.add(val); unexpandedSize ++;
                    unexpandedC.add(cube.clone());
                    data[length +1] ++;
                }
            }
            scr = new Scramble(Integer.MIN_VALUE, 1); //just copying code for inverse section.
            cubeinv.move(scr);
            val = q.value(cubeinv);
            if(!table.containsKey(val)) { //does run faster with table(q.value)==null?
                table.put(val, (byte) (length + 1)); tableSize ++;
                unexpandedQ.add(val); unexpandedSize ++;
                unexpandedC.add(cubeinv.clone());
                data[length + 1] ++;
            }
            else {} // with queue, if it is already in the table, it must have length less or equal to
            for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
                cubeinv.move(scr.iterate());
                val = q.value(cubeinv);
                if(!table.containsKey(q.value(cubeinv))) {
                    table.put(val, (byte) (length + 1)); tableSize ++;
                    unexpandedQ.add(val); unexpandedSize ++;
                    unexpandedC.add(cubeinv.clone());
                    data[length +1] ++;
                }
            }
            if (nodesExpanded % 50000 == 0) {
                System.out.println("nodes expanded: " + nodesExpanded);
                System.out.println("table size: " + tableSize);
                System.out.println("length: " + length);
                System.out.println("unexpanded nodes: " + unexpandedSize);
            }
        }
        for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
        byte[] byteTable = new byte[table.size()];
        Iterator<Integer> iter = table.keySet().iterator();
        while(iter.hasNext()) {
            int qVal = iter.next();
            byteTable[qVal] = table.get(qVal);
        }
        writeMapToFile(byteTable, q.name() + "Table");
    }
    /* problems with creating/using Coordinate.clone */
    public static void makeTable6(Coordinate q) {
        Map<Integer, Byte> table = new HashMap<Integer, Byte>();
        Queue<Coordinate> unexpandedQ = new LinkedList<Coordinate>(); int unexpandedSize = 0;
        table.put(0,(byte) 0); int tableSize = 1;
        unexpandedQ.add(q);
        int nodesExpanded = 0; // only for controlling printing
        int[] data = new int[21]; data[0] = 1;
        while(!unexpandedQ.isEmpty()) {
            nodesExpanded ++;
            Coordinate currentQ = unexpandedQ.poll(); unexpandedSize --;
            int length = table.get(currentQ);
            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
            currentQ.move(scr);
            int val = q.value();
            if(!table.containsKey(val)) { //does run faster with table(q.value)==null?
                table.put(val, (byte) (length + 1)); tableSize ++;
//                unexpandedQ.add(q.clone()); unexpandedSize ++;
                data[length + 1] ++;
            }
            else {} // with queue, if it is already in the table, it must have length less or equal to
            for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
                q.move(scr.iterate());
                val = q.value();
                if(!table.containsKey(val)) {
                    table.put(val, (byte) (length + 1)); tableSize ++;
//                    unexpandedQ.add(q.clone()); unexpandedSize ++;
                    data[length +1] ++;
                }
            }
            if (nodesExpanded % 50000 == 0) {
                System.out.println("nodes expanded: " + nodesExpanded);
                System.out.println("table size: " + tableSize);
                System.out.println("length: " + length);
                System.out.println("unexpanded nodes: " + unexpandedSize);
            }
        }
        for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
        byte[] byteTable = new byte[table.size()];
        Iterator<Integer> iter = table.keySet().iterator();
        while(iter.hasNext()) {
            int qVal = iter.next();
            byteTable[qVal] = table.get(qVal);
        }
        writeMapToFile(byteTable, q.name() + "Table");
    }
    /* make coordinate with TableCoordinate, requires a lot less space since the cube or long version of the
     * coordinate does not have to be saved. */
    public static void makeTable7(SettableCoordinate q) {
        Map<Integer, Byte> table = new HashMap<Integer, Byte>();
        Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
        table.put(0,(byte) 0); int tableSize = 1;
        System.out.println(q.value());
        int nodesExpanded = 0; // only for controlling printing
        int[] data = new int[21]; data[0] = 1;
        while(!unexpandedQ.isEmpty()) {
            nodesExpanded ++;
            int qVal = unexpandedQ.poll(); q.set(qVal); unexpandedSize --;
            int length = table.get(qVal);
            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
            q.move(scr);
            int val = q.value();
            if(!table.containsKey(val)) { //does run faster with table(q.value)==null?
                table.put(val, (byte) (length + 1)); tableSize ++;
                unexpandedQ.add(val); unexpandedSize ++;
                data[length + 1] ++;
            }
            else {} // with queue, if it is already in the table, it must have length less or equal to
            for(int i = 1; i < 18; i ++) { //since we don't have the scramble this comes from, we must check all 18
                q.move(scr.iterate());
                val = q.value();
                if(!table.containsKey(val)) {
                    table.put(val, (byte) (length + 1)); tableSize ++;
                    unexpandedQ.add(val); unexpandedSize ++;
                    data[length +1] ++;
                }
            }
            if (nodesExpanded % 50000 == 0) {
                System.out.println("nodes expanded: " + nodesExpanded);
                System.out.println("table size: " + tableSize);
                System.out.println("length: " + length);
                System.out.println("unexpanded nodes: " + unexpandedSize);
            }
        }
        for(int o = 0; o < 21; o++) {System.out.println(o + ": " + data[o]);}
        byte[] byteTable = new byte[table.size()];
        Iterator<Integer> iter = table.keySet().iterator();
        while(iter.hasNext()) {
            int qVal = iter.next();
            byteTable[qVal] = table.get(qVal);
        }
        writeMapToFile(byteTable, q.name() + "Table");
    }
    
    /*  function for making the lookup table for coordinate movement under every move. 
     * Table is indexed as [move][coordinate]. */
    public static void makeCoordTable(Coordinate q, String name) {
        int[][] table = new int[18][q.size()];
        Queue<Integer> unexpandedQ = new LinkedList<Integer>(); int unexpandedSize = 0;
        Queue<Cube> unexpandedC = new LinkedList<Cube>();
        TreeSet<Integer> expandedQ = new TreeSet<Integer>();
        unexpandedQ.add(0); // do i need this?
        unexpandedC.add(new Cube());
        while(!unexpandedQ.isEmpty()) {
            int currentQ = unexpandedQ.poll(); unexpandedSize --;
            Cube cube = unexpandedC.poll();
            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
            cube.move(scr);
            int qVal = q.value(cube);
            table[0][currentQ] = qVal;
            if(!expandedQ.contains(qVal)) {
                unexpandedC.add(cube.clone());
                unexpandedQ.add(qVal);
            }
            for(int i = 1; i < 18; i ++) { // go through all moves and add to table
                cube.move(scr.iterate());
                qVal = q.value(cube);
                table[i][currentQ] = qVal;
                if(!expandedQ.contains(qVal)) {
                    unexpandedC.add(cube.clone());
                    unexpandedQ.add(qVal);
                }
            }
            expandedQ.add(currentQ);
        }
        System.out.println("Writing table to file.");
        writeMapToFile((Serializable) table, name);
    }
    /* function for making sym coord tables. */
    public static void makeSymCoordTable(Coordinate q, String name, String idsName) {
        HashMap<Integer, Integer[]> rTable = new HashMap<Integer, Integer[]>(); // hate this data structure
        TreeMap<Integer, Cube> unexpanded = new TreeMap<Integer, Cube>(); int unexpandedSize = 0;
        TreeSet<Integer> expandedQ = new TreeSet<Integer>();
        unexpanded.put(0, new Cube()); // do i need this?
        while(!unexpanded.isEmpty()) {
            int currentQ = unexpanded.firstKey(); unexpandedSize --;
            Cube cube = unexpanded.get(currentQ); unexpanded.pollFirstEntry();
            Cube cubeinv = cube.clone();
            cubeinv.invert();
            Scramble scr = new Scramble(Integer.MIN_VALUE, 1);
            cube.move(scr);
            rTable.put(currentQ, new Integer[18]);
            int qVal = makeIdentityCoord(q, cube);
            rTable.get(currentQ)[0] = qVal;
            if(!expandedQ.contains(qVal) && !unexpanded.containsKey(qVal)) {
                unexpanded.put(qVal, cube.clone());
                unexpandedSize ++;
            }
            for(int i = 1; i < 18; i ++) { // go through all moves and add to table
                cube.move(scr.iterate());
                qVal = makeIdentityCoord(q, cube);
                rTable.get(currentQ)[i] = qVal;
                if(!expandedQ.contains(qVal) && !unexpanded.containsKey(qVal)) {
                    unexpanded.put(qVal, cube.clone());
                    unexpandedSize ++;
                }
            }
            expandedQ.add(currentQ);
            // do the same for the inverse cube
            currentQ = makeIdentityCoord(q, cubeinv);
            scr = new Scramble(Integer.MIN_VALUE, 1);
            cubeinv.move(scr);
            rTable.put(currentQ, new Integer[18]);
            qVal = makeIdentityCoord(q, cubeinv);
            rTable.get(currentQ)[0] = qVal;
            if(!expandedQ.contains(qVal) && !unexpanded.containsKey(qVal)) {
                unexpanded.put(qVal, cubeinv.clone());
                unexpandedSize ++;
            }
            rTable.get(currentQ)[0] = makeIdentityCoord(q, cubeinv);
            for(int i = 1; i < 18; i ++) { // go through all moves and add to table
                cubeinv.move(scr.iterate());
                qVal = makeIdentityCoord(q, cubeinv);
                rTable.get(currentQ)[i] = qVal;
                if(!expandedQ.contains(qVal) && !unexpanded.containsKey(qVal)) {
                    unexpanded.put(qVal, cubeinv.clone());
                    unexpandedSize ++;
                }
            }
            expandedQ.add(currentQ);
            if(expandedQ.size() % 200 == 0) {
                System.out.println("nodes expanded: " + expandedQ.size());
                System.out.println("unexpanded nodes: " + unexpanded.size());
            }
        }
        System.out.println("Sorting table coordinates.");
        ArrayList<Integer> ids = new ArrayList<Integer>(rTable.keySet().size());
        Iterator<Integer> iter = rTable.keySet().iterator();
        for(int i = 0; i < rTable.keySet().size(); i ++) {ids.add(iter.next());}
        ids.sort(null);
        int[] idsTable = new int[ids.size()];
        for(int i = 0; i < idsTable.length; i ++) {idsTable[i] = ids.get(i);}
        ids = null;
        int[][] table = new int[18][idsTable.length];
        for(int i = 0; i < idsTable.length; i ++) {
            int r = idsTable[i];
            for(int j = 0; j < 18; j ++) {
                int m = rTable.get(r)[j];
                // binary search for identity coordinate
                int max = idsTable.length;
                int min = 0;
                int index = max/2;
                int g = idsTable[index];
                while(g != m) {
                    if(g > m) {max = index;}
                    else {min = index;}
                    index = (min + max) / 2;
                    g = idsTable[index];
                }
                table[j][i] = index;
            }
        }
        System.out.println("Writing table to file.");
        writeMapToFile((Serializable) table, name);
        writeMapToFile((Serializable) idsTable, idsName);
    }
    public static int makeIdentityCoord(Coordinate q, Cube cube) {
        Cube testerCube = cube.clone();
        int sym = q.value(testerCube);
        for(int i = 1; i < 96; i ++) {
            testerCube.rotate(i);
            int coord = q.value(testerCube);
            if(coord < sym) {
                sym = coord;
            }
            testerCube = cube.clone();
        }
        return sym;
    }
    
    /* my old standard used RTSTables as arraylists with the sym value being the index of the raw value. this is slow,
     * so i'm changing it to a HashMap int -> int. */
    public static void arrayListToMap(String filePath) {
        ArrayList<Integer> arr = (ArrayList<Integer>) readMapFromFile(filePath);
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        Iterator<Integer> iter = arr.iterator();
        int count = 0;
        while(iter.hasNext()) {
            map.put(iter.next(), count);
            count += 1;
        }
        writeMapToFile(map, filePath);
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
    private static class Node {
        public int coord;
        public byte move;
        public TableBuilder.Node parent;
        public Node(int coord, byte move, TableBuilder.Node parent) {
            this.coord = coord;
            this.move = move;
            this.parent = parent;
        }
        public boolean hasParent() {return parent != null;}
    }
}