package q;
import c.*;
/* interface for Coordinates. the static method .value takes a Cube and returns an int value.
 * CO, EO, CP, EP, REO, and RCO used exist within the Cube class
 */
public interface Coordinate {
    public String name();
    public int value(Cube cube);
    /*returns one less than the actual size of the coordinate, which is the max value/max index
     * these values have to be calculated by-hand. that will be a problem when i want to try to create coordinates
     * in the machine. Help from Wolfgang Buchmaier in finding some values */
    public int size();
    /*calls the fromInt methods in the Cube class for Raw Values. */
    public Cube setCoord(Cube cube, int value);
}