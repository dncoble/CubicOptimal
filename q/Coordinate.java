package q;
import c.*;
/* interface for Coordinates. the static method .value takes a Cube and returns an int value.
 * CO, EO, CP, EP, REO, and RCO used exist within the Cube class
 */
public interface Coordinate {
    public String name();
    public int value(Cube cube);
}