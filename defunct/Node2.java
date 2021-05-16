package defunct;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/* node that contains 2 pieces of information */
public class Node2<T> {
    public T data1;
    public T data2;
    public Node2 parent;
    public Node2(T data1, T data2, Node2 parent) {
        this.data1 = data1;
        this.data2 = data2;
        this.parent = parent;
    }

}
