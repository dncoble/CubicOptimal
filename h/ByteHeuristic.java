package h;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import c.*;
/* h.ByteHeuristic is implemented by h.RawCoordHeuristic, h.SymCoordHeuristic, and h.MaxHeuristic. h.MaxHeuristic itself
 * has a list of ByteHeuristics (the coord heuristics) that it takes the max value of for each cube.
 * in the future i'll want to probably make types of heuristics that are not from coordinates, like ML or a
 * statistical model. those will probably return a float value, and i'll make a new interface for them
 * 
 * Old functionality (0.3.1) is done with h(Cube cube), new functionality (>0.3.1) is done with h()
 */
public interface ByteHeuristic {
    public void set(Cube cube);
    public byte h(Cube cube);
    public byte h();
    public void move(int move);
    public void move(Scramble scr);
}
