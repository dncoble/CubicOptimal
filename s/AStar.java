package s;/*
 * implements the A* search algorithm on a cube, hopefully for better performance.
 * this is the first search algorithm i'm making but i plan to put in a bunch more.
 * this implementation of A* only works with table heuristics though, because of assumptions it makes
 * while navigating (each node must be either -1, 0, or 1 from past nodes)
 * How hard would it be to change from <Short> to <Byte>?
 */
import h.ByteHeuristic;

import java.util.LinkedList;
import java.util.ArrayList;
import c.*;

public class AStar {

    private Cube cube;
    private ByteHeuristic h;

    public AStar(Cube cube, ByteHeuristic h){
        this.cube = cube;
        this.h = h;
    }

    /* this i think is just about as efficiently as it can be done. queues can be made actual queues (or stacks)
    * after the first move
    * only set up (and optimized for) int (short) (not float) heuristics, and the implementation is kind of wack*/
    public Scramble search() {
        Cube solved = new Cube();
        Cube test = cube.clone();
        //elements in queue. nodes contain the move required
        ArrayList<LinkedList<Node<Byte>>> queueList = new ArrayList<LinkedList<Node<Byte>>>();
        for(int i =0; i < 22; i ++) {queueList.add(new LinkedList<Node<Byte>>());}
        Node<Short> root = new Node(null, null);
        Scramble scr = new Scramble(1);
        test.move(scr);
        short f = (short) (h.h(test) + 1);
        int smallest = f;
        if(f < 21){queueList.get(f).addFirst(new Node<Byte>((byte) 0, root));}
        if(f == 1 && test.equals(solved)) {return scr;}
        for(byte i = 1; i < 18; i++) {
            test.move(scr.iterate());
            f =(short) (h.h(test) + 1);
            if(f < smallest) {smallest = f;}
            if(f < 21){queueList.get(f).addFirst(new Node<Byte>(i, root));}
            if (f == 1 && test.equals(solved)) {return scr;}
        }
        while(true) {
            test = cube.clone();
            //System.out.println("checkpoint 1");
            Node current = queueList.get(smallest).removeFirst();
            Node parent = current;
            scr = new Scramble();
            short length = 1;
            while (!parent.equals(root)) {
                //System.out.println(smallest);
                scr.addFirst(((Short) parent.data).intValue());
                length++;
                parent = parent.parent;
            }
            if(scr.getScramble().getLast() > 2) {scr.addLast(0);}
            else{scr.addLast(3);}
            //System.out.println(scr.getLength() + " " + smallest);
            test.move(scr);
            f = (short) (h.h(test) + length);
            if(f < 21){queueList.get(f).addFirst(new Node<Byte>((byte) (int) scr.getScramble().getLast(), current));}
            if(f == length && test.equals(solved)) {return scr;}
            for(short i = 1; i < 15; i++) {
                test.move(scr.iterate());
                f =(short) (h.h(test) + length);
                if(f < 21){queueList.get(f).addFirst(new Node<Byte>((byte) (int) scr.getScramble().getLast(), current));}
                if (f == length && test.equals(solved)) {return scr;}
            }
            while(queueList.get(smallest).isEmpty()) {smallest ++; System.out.println("length: " + smallest);}
        }
    }
    private static class Node<T> {
        public T data;
        public Node parent;
        public Node(T data, Node parent) {
            this.data = data;
            this.parent = parent;
        }
    }
}
