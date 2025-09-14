package pl.ptemich.jerdy.catanai;

// Enum dla typów zasobów
enum Resource {
    WHEAT("Pszenica", "W"),
    WOOD("Drewno", "D"),
    BRICK("Cegła", "C"),
    SHEEP("Owca", "O"),
    ORE("Ruda", "R"),
    DESERT("Pustynia", " ");

    private final String name;
    private final String symbol;

    Resource(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
}
