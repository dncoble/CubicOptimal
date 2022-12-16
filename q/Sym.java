package q;

import c.*;
import defunct.RawCoord;

import java.util.ListIterator;

/* all Sym coords go through this class.
 * DOESN'T WORK WITH NEW COORDINATE FUNCTIONALITY */
public class Sym extends Coordinate {
    Coordinate rawCoord;

    public Sym(Coordinate rawCoord) {this.rawCoord = rawCoord;}
    public void set(Cube cube) {rawCoord.set(cube);}
    
    public int value(Cube cube) {
        int identityCoord = makeIdentityCoord(cube);
        return identityCoord;
    }
     /* DOESN'T WORK WITH NEW COORDINATE FUNCTIONALITY */
    public int value() {return 0;}
    public void move(int move) {}
    public int size() {return -1;}

    /* this method finds identity coord by rotating the cube 48 times and finding the smallest value.
     * to implement inverse change 48 to 96 */
    private int makeIdentityCoord(Cube cube) {
        Cube testerCube = cube.clone();
        int sym = rawCoord.value(testerCube);
        for(int i = 1; i < 48; i ++) {
            testerCube.rotate(i);
            int coord = rawCoord.value(testerCube);
            if(coord < sym) {
                sym = coord;
            }
            testerCube = cube.clone();
        }
        return sym;
    }
    
    public Sym clone() {return new Sym(rawCoord.clone());}
    public String name() {return "Sym" + rawCoord.name();}
}