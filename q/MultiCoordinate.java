package q;
import c.*;

import java.util.ArrayList;
import java.util.ListIterator;

/* Representing a coordinate as a tuple, or Cartesian product, of other coordinates */
public class MultiCoordinate extends Coordinate {
    public ArrayList<Coordinate> coords;
    public String name;
    
    public MultiCoordinate() {
        coords = new ArrayList<Coordinate>();
        name = "";
    }
    public void addCoordinate(Coordinate coord) {
        coords.add(coord);
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
    
    public Coordinate clone() {
        MultiCoordinate copy = new MultiCoordinate();
        for(Coordinate coord : coords) {
            copy.addCoordinate(coord.clone());
        }
        return copy;
    }
    
    public void move(int move) {
        for(Coordinate coord : coords) {
            coord.move(move);
        }
    }
    
    public String name() {
        if (name.equals("")) {
            String name = "Multi";
            for(Coordinate coord : coords) {
                name += "-" + coord.name();
            }
            return name;
        }
        return name;
    }
    public int size() {
        int size = 1;
        for(Coordinate coord : coords) {
            size *= coord.size();
        }
        return size;
    }
}
