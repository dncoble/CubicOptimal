package q;

/* it matters for table generation if a coordinate can be set from an integer (generally it can't, but can for 
 * TabledCoordinate, TabledMultiCoordinate */
public abstract class SettableCoordinate extends Coordinate {
    public abstract void set(int qVal);
}