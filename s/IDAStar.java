package s;
/* implements the IDA* search algorithm */

import h.ByteHeuristic;
import java.util.Stack;
import c.*;
public class IDAStar {
    
    private Cube cube;
    private ByteHeuristic h;
    private Cube solved;
    
    public IDAStar(Cube cube, ByteHeuristic h) {
        this.cube = cube;
        this.h = h;
        solved = new Cube();
    }
    
    /* this was so much easier compared to SMA*
    * ngl i don't understand this completely; i just wrote out the pseudocode from Wikipedia */
    public Scramble search() {
        Cube test = cube.clone();
        
        //create stack path, add root
        Node<Byte> root = new Node<Byte>(null, null);
        Stack<Node<Byte>> path = new Stack<Node<Byte>>(); path.add(root);
        int threshold = h.h(test);
        System.out.println("Threshold: " + threshold);
        while(true) {
            int t = expand(path, 0, threshold, root);
            if (t == -1) {
                Node<Byte> current = path.peek();

                Node parent = current;
                Scramble scr = new Scramble();
                while (!parent.equals(root)) {
                    //System.out.println(smallest);
                    scr.addFirst(((Byte) parent.data).intValue());
                    parent = parent.parent;
                }
                return scr;
            }
            threshold = t;
            System.out.println("Threshold expanded: " + threshold);
        }
    }
    /* helper method for search(). -1 means return success.
     * passing it root is sloppy but eh */
    private int expand(Stack<Node<Byte>> path, int g, int threshold, Node<Byte> root) {
        Cube test = cube.clone();
        Node<Byte> current = path.peek();
        
        Node parent = current;
        Scramble scr = new Scramble();
        while (!parent.equals(root)) {
            //System.out.println(smallest);
            scr.addFirst(((Byte) parent.data).intValue());
            parent = parent.parent;
        }
        
        test.move(scr);
        int f = g + h.h(test);
        
        if(f > threshold) {return f;}
        if(test.equals(solved)) {return -1;}
        
        int n = 15;
        if(scr.getScramble().isEmpty()) {n = 18; scr.addLast(0);}
        else if(scr.getScramble().getLast() > 2) {scr.addLast(0);}
        else{scr.addLast(3);}
        
        test.move(scr);
        path.push(new Node<Byte>((byte) 0, current));
        int t = expand(path, g+1, threshold, root);
        if(t == -1) {return -1;}
        int min = t;
        path.pop();
        for(byte i = 1; i < n; i++) {
            test.move(scr.iterate());
            path.push(new Node<Byte>((byte) (int) scr.getScramble().getLast(), current));
            t = expand(path, g+1, threshold, root);
            if(t == -1) {return -1;}
            if(t < min) {min = t;}
            path.pop();
        }
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
