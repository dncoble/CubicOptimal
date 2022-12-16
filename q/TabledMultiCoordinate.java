package q;

import c.Cube;

import java.util.ArrayList;

/* class for both tabled and multicoordinates */
public class TabledMultiCoordinate extends SettableCoordinate {
    public ArrayList<TableCoordinate> coords;
    public String name;

    public TabledMultiCoordinate() {
        coords = new ArrayList<TableCoordinate>();
        name = "";
    }
    public void addCoordinate(TableCoordinate coord) {coords.add(coord);}
    public void set(Cube cube) {
        for(Coordinate coord : coords) {
            coord.set(cube);
        }
    }
    public void set(int[] qVals) {
        for(int i = 0; i < coords.size(); i++) {
            coords.get(i).set(qVals[i]);
        }
    }
    public void set(int qVal) {
        for(int i = coords.size()-1; i>=0; i--) {
            int y = qVal % coords.get(i).size();
            coords.get(i).set(y);
            qVal /= coords.get(i).size();
        }
    }
    
    public int value(Cube cube) {
        int value = 0;
        for(Coordinate coord : coords) {
            value *= coord.size();
            value += coord.value(cube);
        }
        if(value > size()) {
            System.out.println("problem with REO");
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
    /* not recommended since this copies all tables */
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
            System.out.println(coord.size());
            size *= coord.size();
        }
        return size;
    }
}
