package s;
/* the SMA* graph/tree search algorithm
 * only works with table-generated heuristics (int numbers)
 */

import java.util.LinkedList;
import java.util.ArrayList;
import c.*;
import h.*;

public class SMAStar implements Search {


    private Cube cube;
    private ByteHeuristic h;
    private int memory;

    public SMAStar(Cube cube, ByteHeuristic h, int memory){
        this.cube = cube;
        this.h = h;
        this.memory = memory;
    }
    /*
     *
     */
//    public c.Scramble search() {
//        c.Cube solved = new c.Cube();
//        c.Cube test = cube.clone();
//
//        ArrayList<ArrayList<LinkedList<DEFUNCT.Node33<Byte>>>> queue = new ArrayList<ArrayList<LinkedList<DEFUNCT.Node33<Byte>>>>();
//        for (int i = 0; i < 22; i++) {
//            ArrayList<LinkedList<DEFUNCT.Node33<Byte>>> next = new ArrayList<LinkedList<DEFUNCT.Node33<Byte>>>();
//            for (int j = 0; j < 22; j++) {
//                next.add(new LinkedList<DEFUNCT.Node33<Byte>>());
//            }
//            queue.add(next);
//        }
//        //i could make this code shorter because the loop is also equipt to handle the root node, but why bother?
//        DEFUNCT.Node33<Byte> root = new DEFUNCT.Node33(null, h.h(test), null, null, null, null);
//        c.Scramble scr = new c.Scramble(1);
//        test.move(scr);
//        byte f = (byte) (h.h(test) + 1);
//        byte smallest = f;
//        byte depth = 1;
//
//        DEFUNCT.Node33<Byte> prevChild = new DEFUNCT.Node33<Byte>((byte) 0, f, null, root, null, null);
//
//        if (f < 21) {queue.get(f).get(1).addFirst(prevChild);}
//        if (f == 1 && test.equals(solved)) {return scr;}
//
//        for (byte i = 1; i < 18; i++) {
//            test.move(scr.iterate());
//            f = (byte) (h.h(test) + 1);
//            if (f < smallest) {smallest = f;}
//
//            DEFUNCT.Node33<Byte> nextChild = new DEFUNCT.Node33<Byte>(i, f, null, root, prevChild, null);
//            prevChild.sister2 = nextChild;
//
//            if (f < 21) {queue.get(f).get(1).addFirst(prevChild);}
//            if (f == 1 && test.equals(solved)) {return scr;}
//        }
//        root.data2 = smallest;
//        //fill end of queue up to memory.
//        for (int i = 0; i < memory - 19; i++) {
//            queue.get(21).get(21).add(new DEFUNCT.Node33(null, null, null, null, null, null)); //
//        }
//        int largest = 21;
//        int topdepth = 21;
//        while (true) {
//            test = cube.clone();
//            Node32 current = queue.get(smallest).get(depth).removeFirst();
//            Node32 parent = current;
//            scr = new c.Scramble();
//            byte length = depth;
//            length++; //java hates depth + 1 apparently
//            while (!parent.equals(root)) {
//                scr.addFirst(((Byte) parent.data1).intValue());
//                length++;
//                parent = parent.parent;
//            }
//            if (scr.getScramble().getLast() > 2) {
//                scr.addLast(0);
//            } else {
//                scr.addLast(3);
//            }
//
//            test.move(scr);
//            f = (byte) (h.h(test) + length);
//            byte childsmallest = f;
//            if (f < 21) {
//                while (queue.get(largest).get(topdepth).isEmpty()) {
//                    if (topdepth == 0) {
//                        topdepth = 21;
//                        largest--;
//                    } else {
//                        topdepth--;
//                    }
//                }
//                Node32<Byte> newNode = queue.get(largest).get(topdepth).removeFirst(); //
//                newNode.reset((byte) (int) scr.getScramble().getLast(), f, null, null, current); //
//                queue.get(f).get(length).addFirst(newNode);
//            }
//            if (f == length && test.equals(solved)) {
//                return scr;
//            }
//            for (byte i = 1; i < 15; i++) { //add root situation
//                test.move(scr.iterate());
//                f = (byte) (h.h(test) + length);
//                if (f < smallest) {
//                    smallest = f;
//                }
//                if (f < 21) {
//                    while (queue.get(largest).get(topdepth).isEmpty()) {
//                        if (topdepth == 0) {
//                            topdepth = 21;
//                            largest--;
//                        } else {
//                            topdepth--;
//                        }
//                    }
//                    Node32<Byte> newNode = queue.get(largest).get(topdepth).removeFirst();
//                    newNode.reset((byte) (int) scr.getScramble().getLast(), f, null, null, current); //
//                    queue.get(f).get(length).addFirst(newNode);
//                }
//                if (f == length && test.equals(solved)) {
//                    return scr;
//                }
//            }
//            current.data2 = childsmallest;
//            while (current.parent.data2 == true) { //
//                //change current, move in queue
//                //deal with largest, topdepth
//                while (queue.get(smallest).isEmpty()) {
//                    smallest++;
//                    System.out.println("length: " + smallest);
//                }
//            }
//        }
//    }
    /*public s.SMAStar.CubeNode(s.SMAStar.CubeNode parent, byte move, byte f, byte forgotten)
     * make an option for this to be verbose
     *
     * find max f, depth for each loop
     */
    public Scramble search() {
        Cube solved = new Cube();
        Cube test = cube.clone();

        ArrayList<ArrayList<LinkedList<CubeNode>>> queue = new ArrayList<ArrayList<LinkedList<CubeNode>>>();
        for (int i = 0; i < 22; i++) {
            ArrayList<LinkedList<CubeNode>> next = new ArrayList<LinkedList<CubeNode>>();
            for (int j = 0; j < 22; j++) {
                next.add(new LinkedList<CubeNode>());
            }
            queue.add(next);
        }
        //i could make this code shorter because the loop is also equipt to handle the root node, but why bother?
        CubeNode root = new CubeNode(null, (byte) 127, (byte) h.h(test), (byte)127);

        Scramble scr = new Scramble(1);
        test.move(scr);
        byte depth = 1;
        byte f = (byte) (h.h(test) + depth);
        byte smallest = f;

        CubeNode child = new CubeNode(root, (byte)0, f, (byte)127);

        int openSize = 0;
        if (f < 21) {
            queue.get(f).get(1).addFirst(child);
            openSize ++;
        }
        if (f == 1 && test.equals(solved)) {return scr;}

        for (byte i = 1; i < 18; i++) {
            test.move(scr.iterate());
            f = (byte) (h.h(test) + depth);
            if (f < smallest) {smallest = f;}

            child = new CubeNode(root, i, f, (byte)127);

            if (f < 21) {
                queue.get(f).get(1).addFirst(child);
                openSize ++;
            }
            if (f == 1 && test.equals(solved)) {return scr;}
        }
        root.f = smallest;
        //in a prev. implementation i filled the entire queue with blank nodes at this point, but i choose not to.
        //begin infinite loop
        int largest = 21;
        int topdepth = 21;

        return null;
    }

    /* this is a way smarter idea than what i was doing
     * a node designed for cubes, & specifically SMA*.
     * it has links to all its children
     */
    private static class CubeNode {
        byte move;
        byte f;
        byte forgotten;
        public CubeNode[] children;
        public CubeNode parent;

        public CubeNode(CubeNode parent, byte move, byte f, byte forgotten) {
            this.parent = parent;
            this.move = move;
            this.f = f;
            this.forgotten = forgotten;
            if(parent != null) {
                parent.children[move] = this;
            }
        }
        public void expand() {
            children = new CubeNode[18];
        }
        public void reset(CubeNode parent, byte move, byte f, byte forgotten) {
            this.parent.children[move] = null;
            children = null;

            this.parent = parent;
            this.move = move;
            this.f = f;
            this.forgotten = forgotten;
            if(parent != null) {
                parent.children[move] = this;
            }
        }

    }
}
