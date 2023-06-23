package q;
import c.*;

/* The idea behind RCO and REO is to modify CO and EO coordinates to contain enough information so that rotation can be
 * performed. To do that, some information from permutation must be included. RCP and REP contain that amount of
 * information. Then, RCO and REO can be implemented as multicoordinates with CO, CP and RCP, REP. */

public class RCO extends MultiCoordinate {
    private static String NAME;
    static {
        NAME = "RCO";
    }
    
    public RCO(Cube cube) {
        super();
        super.name = NAME;
        addCoordinate(new RCP(cube));
        addCoordinate(new CO(cube));
    }
    public RCO() {
        super();
        super.name = NAME;
        addCoordinate(new RCP());
        addCoordinate(new CO());
    }
    
    public String name() {return NAME;}
}