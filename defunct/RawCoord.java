package defunct;
import c.*;
/* I wish I could make this inherit Coordinate but Java doesn't let you. I guess that's okay. Raw Coords are those
 * which are simply some mach operation on a the cube, which the heursitic will then put through the look-up table
 */
public interface RawCoord {
    public int value(Cube cube);
    public String name();
}
