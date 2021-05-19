package q;
import c.*;
/* I wish I could make this inherit Coordinate but Java doesn't let you. I guess that's okay. Raw Coords are those
 * which are simply some mach operation on a the cube, which the heursitic will then put through the look-up table
 */
public interface RawCoord {
    /* has the effect of rotating the cube by the given rotation then returning the coordinate result.
     * a description of how to generate the int rotation value can be found in the Cube class.
     * all implementations of this will actually rotate (and destroy the usefulness of) the cube that is passed to it,
     * so pass it always a copy of the cube. maybe it is quicker to rotate, collect value, unrotate? */
    public int rotate(Cube cube, int rotation);
    public int value(Cube cube);
    public String name();
    public int size();
    //right now i'm just going to have this immediately call the fromInt methods in the Cube class
    public void setCoord(Cube cube, int value);
}
