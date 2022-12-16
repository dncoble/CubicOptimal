package q;

import c.Cube;

public class TabledRCO extends TabledMultiCoordinate {
    private static String NAME;
    static {
        NAME = "RCO";
    }

    public TabledRCO(Cube cube) {
        super();
        super.name = NAME;
        addCoordinate(new TableCoordinate(new RCP(cube)));
        addCoordinate(new TableCoordinate(new CO(cube)));
    }
    public TabledRCO(boolean tabled) {
        super();
        super.name = NAME;
        addCoordinate(new TableCoordinate(new RCP()));
        addCoordinate(new TableCoordinate(new CO()));
    }

    public String name() {return NAME;}
}
