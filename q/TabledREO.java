package q;

import c.Cube;

public class TabledREO extends TabledMultiCoordinate {
    private static String NAME;
    static {
        NAME = "REO";
    }

    public TabledREO(Cube cube) {
        super();
        super.name = NAME;
        addCoordinate(new TableCoordinate(new REP(cube)));
        addCoordinate(new TableCoordinate(new EO(cube)));
    }
    public TabledREO() {
        super();
        super.name = NAME;
        addCoordinate(new TableCoordinate(new REP()));
        addCoordinate(new TableCoordinate(new EO()));
    }

    public String name() {return NAME;}
}
