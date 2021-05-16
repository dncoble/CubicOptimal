package defunct;/* node that contains 4 pieces of information */

public class Node4<T> {
    public T data1; public T data2;
    public T data3; public T data4;
    public Node4 parent;

    public Node4(T data1, T data2, T data3, T data4, Node4 parent) {
        this.data1 = data1; this.data2 = data2;
        this.data3 = data3; this.data4 = data4;
        this.parent = parent;
    }
    public void reset(T data1, T data2, T data3, T data4, Node4 parent) {
        this.data1 = data1; this.data2 = data2;
        this.data3 = data3; this.data4 = data4;
        this.parent = parent;
    }
}