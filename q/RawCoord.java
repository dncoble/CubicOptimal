package q;
import c.*;
/* I wish I could make this inherit Coordinate but Java doesn't let you. I guess that's okay. Raw Coords are those
 * which are simply some mach operation on a the cube, which the heursitic will then put through the look-up table
 */
public interface RawCoord {
    public int value(Cube cube);
    public String name();
    public int size();
    //right now i'm just going to have this immediately call the fromInt methods in the Cube class
//    public Cube setCoord(Cube cube, int value);
}
