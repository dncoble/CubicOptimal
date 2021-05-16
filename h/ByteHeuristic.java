package h;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import c.*;


/* This interface acts as the heuristic function used by search algorithms. Determining the heuristics
 * was done with all those tables and things.
 * h.ByteHeuristic is implemented by h.RawCoordHeuristic, h.SymCoordHeuristic, and h.MaxHeuristic. h.MaxHeuristic itself
 * has a list of ByteHeuristics (the coord heuristics) that it takes the max value of for each cube.
 * in the future I'll want to probably make types of heuristics that are not from coordinates, like ML or a
 * statistical model. those will probably return a float value, and i'll make a new interface for them
 */
public interface ByteHeuristic {
    public byte h(Cube cube);
}
