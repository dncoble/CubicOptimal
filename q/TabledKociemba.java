package q;

import c.Cube;

public class TabledKociemba extends TabledMultiCoordinate {
    private static String NAME;
    static {
        NAME = "Kociemba";
    }
    public TabledKociemba(Cube cube) {
        super();
        super.name = NAME;
        addCoordinate(new TableCoordinate(new CO(cube)));
        addCoordinate(new TableCoordinate(new EO(cube)));
        addCoordinate(new TableCoordinate(new UDSlice(cube)));
    }
    public TabledKociemba() {
        super();
        super.name = NAME;
        addCoordinate(new TableCoordinate(new CO()));
        addCoordinate(new TableCoordinate(new EO()));
        addCoordinate(new TableCoordinate(new UDSlice()));
    }

    public String name() {return NAME;}
}