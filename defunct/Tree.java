package defunct;/*
 * a tree class, used for tree traversal and search algorithms.
 * T is generic, but i only use Short
 *
 */
import java.util.LinkedList;
import java.util.ListIterator;

public class Tree<T> {
    public T data;
    private LinkedList<Tree<T>> children;
    private ListIterator<Tree<T>> iter;

    public Tree(T rootData) {
        data = rootData;
        children = new LinkedList<Tree<T>>();
        iter = children.listIterator();
    }

    public void addChild(Tree child){children.addLast(child);}
    public void removeChild(Tree<T> child){
        iter = children.listIterator();
        while(iter.hasNext()){if(iter.next() == child) {iter.remove();}}
    }
    public LinkedList<Tree<T>> getChildren(){return children;}
}