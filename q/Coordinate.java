package q;
import c.*;
/* interface for Coordinates. the static method .value takes a Cube and returns an int value.
 * CO, EO, CP, EP, REO, and RCO used exist within the Cube class. the Cube class also contained fromInt
 * methods that were the exact opposite. they weren't used in the solver so I don't know where to place them
 * for now they're staying in the Cube class.
 */
public interface Coordinate {
    public static String NAME = "";
    public static int value(Cube cube){return 0;};
}