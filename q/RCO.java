package q;
import c.*;

import java.util.ListIterator;

/* The idea behind RCO and REO is to modify CO and EO coordinates to contain enough information so that rotation can be
 * performed. To do that, some information from permutation must be included. RCP and REP contain that amount of
 * information. Then, RCO and REO can be implemented as multicoordinates with CO, CP and RCP, REP.
 */
public class RCO implements Coordinate {
    private MultiCoordinate multicoord;
    private Coordinate rcp;
    private Coordinate co;
    private static String NAME;
    static {
        NAME = "RCO";
    }
    
    public RCO(Cube cube) {
        rcp = new RCP(cube);
        co = new CO(cube);
        multicoord = new MultiCoordinate(new Coordinate[]{rcp, co});
    }
    public RCO() {
        rcp = new RCP();
        co = new CO();
        multicoord = new MultiCoordinate(new Coordinate[]{rcp, co});
    }
    public void set(Cube cube) {
        rcp = new RCP(cube);
        co = new CO(cube);
        multicoord = new MultiCoordinate(new Coordinate[]{rcp, co});
    }
    
    public int value(Cube cube) {return multicoord.value(cube);}
    public int value() {return multicoord.value();}
    
    public void move(int move) {multicoord.move(move);}
    public void move(Scramble scr) {
        ListIterator<Integer> iter = scr.getIterator();
        while(iter.hasNext()) {
            move(iter.next());
        }
    }
    
    public String name() {return NAME;}
    public int size() {return multicoord.size();}
}