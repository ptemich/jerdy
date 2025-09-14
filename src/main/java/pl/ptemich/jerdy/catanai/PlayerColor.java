package pl.ptemich.jerdy.catanai;

// Enum dla kolorów graczy
enum PlayerColor {
    RED("Czerwony", "R"),
    BLUE("Niebieski", "N"),
    WHITE("Biały", "B"),
    ORANGE("Pomarańczowy", "P");

    private final String name;
    private final String symbol;

    PlayerColor(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
}