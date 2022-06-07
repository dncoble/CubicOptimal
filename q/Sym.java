package q;

import c.*;
import defunct.RawCoord;

/* all Sym coords go through this class */
public class Sym implements Coordinate {
    Coordinate rawCoord;

    public Sym(Coordinate rawCoord) {
        this.rawCoord = rawCoord;
    }

    public int value(Cube cube) {
        int identityCoord = makeIdentityCoord(cube);
        return identityCoord;
    }

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
    public String name() {return "Sym" + rawCoord.name();}
}