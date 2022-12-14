package q;
import c.*;

import java.util.ListIterator;

/* Representing a coordinate as a tuple, or Cartesian product, of other coordinates */
public class MultiCoordinate implements Coordinate {
    Coordinate[] coords;
    int size;
    String name;
    
    public MultiCoordinate(Coordinate[] coords) {
        this.coords = coords;
        size = 1;
        name = "Multi";
        for(Coordinate coord : coords) {
            size *= coord.size();
            name += "-" + coord.name();
        }
    }
    public void set(Cube cube) {
        for(Coordinate coord : coords) {
            coord.set(cube);
        }
    }
    
    public int value(Cube cube) {
        int value = 0;
        for(Coordinate coord : coords) {
            value *= coord.size();
            value += coord.value(cube);
        }
        return value;
    }
    public int value() {
        int value = 0;
        for(Coordinate coord : coords) {
            value *= coord.size();
            value += coord.value();
        }
        return value;
    }
    
    public void move(int move) {
        for(Coordinate coord : coords) {
            coord.move(move);
        }
    }
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            move(iter.next());
        }
    }
    
    public String name() {return name;}
    public int size() {return size;}
}
