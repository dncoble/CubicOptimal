package q;
import c.*;
/* The idea behind RCO and REO is to modify CO and EO coordinates to contain enough information so that rotation can be
 * performed. To do that, some information from permutation must be included. RCP and REP contain that amount of
 * information. Then, RCO and REO can be implemented as multicoordinates with CO, CP and RCP, REP. */

public class REO extends MultiCoordinate {
    private static String NAME;
    static {
        NAME = "REO";
    }
    
    public REO(Cube cube) {
        super();
        super.name = NAME;
        addCoordinate(new REP(cube));
        addCoordinate(new EO(cube));
    }
    public REO() {
        super();
        super.name = NAME;
        addCoordinate(new REP());
        addCoordinate(new EO());
    }
    
    public String name() {return NAME;}
}