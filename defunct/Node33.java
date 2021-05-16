package defunct;/*node class that contains 3 pieces of information, and 2 pointers to other Node32s*/

public class Node33<T> {
    public T data1;
    public T data2;
    public T data3;
    public Node33 parent;
    public Node33 sister1;
    public Node33 sister2;

    public Node33(T data1, T data2, T data3, Node33 parent, Node33 sister1, Node33 sister2) {
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.parent = parent;
        this.sister1 = sister1;
        this.sister2 = sister2;
    }

    public void reset(T data1, T data2, T data3, Node33 parent, Node33 sister1, Node33 sister2) {
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
        this.parent = parent;
        this.sister1 = sister1;
        this.sister2 = sister2;
    }
}