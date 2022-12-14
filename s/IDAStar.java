package s;
/* implements the IDA* search algorithm */

import h.ByteHeuristic;
import java.util.Stack;
import c.*;
public class IDAStar implements Search {
    
    private Cube cube;
    private ByteHeuristic h;
    private Cube solved;
    
    public IDAStar(Cube cube, ByteHeuristic h) {
        this.cube = cube;
        this.h = h;
        solved = new Cube();
    }
    
    /* this was so much easier compared to SMA* */
    public Scramble search() {
        //create stack path, add root
        Node<Integer> root = new Node<Integer>(null, null);
        Stack<Node<Integer>> path = new Stack<Node<Integer>>(); path.add(root);
        Scramble scr = new Scramble(0);
        int threshold = h.h();
        System.out.println("Threshold: " + threshold);
        while(true) {
            int t = expand(scr, 0, threshold);
            if (t == -1) {
                // reverse the path back to root to generate scramble
//                Node<Integer> current = path.peek();
//                
//                Node parent = current;
//                Scramble scr = new Scramble();
//                while (!parent.equals(root)) {
//                    scr.addFirst(((Byte) parent.data).intValue());
//                    parent = parent.parent;
//                }
                return scr;
            }
            threshold = t;
            System.out.println("Threshold expanded: " + threshold);
        }
    }
    /* helper method for search(). -1 means return success.
     * passing it root is sloppy but eh */
//    private int expand(Stack<Node<Byte>> path, int g, int threshold, Node<Byte> root) {
//        Cube test = cube.clone();
//        Node<Byte> current = path.peek();
//        
//        Node parent = current;
//        Scramble scr = new Scramble();
//        while (!parent.equals(root)) {
//            scr.addFirst(((Byte) parent.data).intValue());
//            parent = parent.parent;
//        }
//        
//        test.move(scr);
//        int f = g + h.h(test);
//        
//        if(f > threshold) {return f;}
//        if(test.equals(solved)) {return -1;}
//        
//        int n = 15;
//        if(scr.getScramble().isEmpty()) {n = 18; scr.addLast(0);}
//        else if(scr.getScramble().getLast() > 2) {scr.addLast(0);}
//        else{scr.addLast(3);}
//        
//        test.move(scr);
//        path.push(new Node<Byte>((byte) 0, current));
//        int t = expand(path, g+1, threshold, root);
//        if(t == -1) {return -1;}
//        int min = t;
//        path.pop();
//        for(byte i = 1; i < n; i++) {
//            test.move(scr.iterate());
//            path.push(new Node<Byte>((byte) (int) scr.getScramble().getLast(), current));
//            t = expand(path, g+1, threshold, root);
//            if(t == -1) {return -1;}
//            if(t < min) {min = t;}
//            path.pop();
//        }
//        return min;
//    }
    /* helper method for search(). -1 means return success.
     * Assume cube, h.q are in the proper permutation before expand() is called.
     */
    private int expand(Scramble scr, int g, int threshold) {
        int f = g + h.h();
        // strictly in IDA*, checking solved must be after if(f > threshold). However, we can do this and achieve a
        // speedup since all edges have cost 1.
        if(cube.equals(solved)) {return -1;} // this might be slow, should look into speeding it up.
        if(f > threshold) {return f;}
        scr.addLast();
        cube.singleMove(scr.getLast()); h.move(scr.getLast());
        int t = expand(scr, g+1, threshold);
        int min = t;
        for(int i = 1; i < 15; i ++) {
            Scramble it = scr.iterate();
            cube.move(it); h.move(it);
            t = expand(scr, g+1, threshold);
            if (t == -1) {return -1;}
            if(t < min) {min = t;}
        }
        int m = Scramble.moveInverse(scr.getLast());
        cube.singleMove(m); h.move(m);
        scr.removeLast();
        return min;
    }
    
    /* small class containing some data and a link to its parent
     * building block of linked lists, or parent-linked trees (generally unidirectional graphs)
     * nodes contain two pieces of data: "data" is generally what typically goes into the node,
     * but "link" is like the direction, in my case the move, that the parent is in
     */
    private static class Node<T> {
        public T data;
        public Node parent;
        public Node(T data, Node parent) {
            this.data = data;
            this.parent = parent;
        }
    }
}
