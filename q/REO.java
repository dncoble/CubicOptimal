package q;
import c.*;
/* The idea behind RCO and REO is to modify CO and EO coordinates to contain enough information so that rotation can be
 * performed. To do that, some information from permutation must be included. RCP and REP contain that amount of
 * information. Then, RCO and REO can be implemented as multicoordinates with CO, CP and RCP, REP.
 */
import java.util.ArrayList;
import java.util.ListIterator;

public class REO implements Coordinate {
    private MultiCoordinate multicoord;
    private Coordinate rep;
    private Coordinate eo;
    private static String NAME;
    static {
        NAME = "RCO";
    }
    
    public REO(Cube cube) {
        rep = new REP(cube);
        eo = new CO(cube);
        multicoord = new MultiCoordinate(new Coordinate[]{rep, eo});
    }
    public REO() {
        rep = new RCP();
        eo = new CO();
        multicoord = new MultiCoordinate(new Coordinate[]{rep, eo});
    }
    public void set(Cube cube) {
        rep = new REP(cube);
        eo = new CO(cube);
        multicoord = new MultiCoordinate(new Coordinate[]{rep, eo});
    }
    
    public int value(Cube cube) {
        return multicoord.value(cube);
    }
    public int value() {return multicoord.value();}
    
    public void move(int move) {
        multicoord.move(move);
    }
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            move(iter.next());
        }
    }
    
    public String name() {return NAME;}
    public int size() {return multicoord.size();}
}
