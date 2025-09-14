package pl.ptemich.jerdy.catanai;

// Klasa reprezentująca drogę
class Road {
    private PlayerColor owner;

    public Road(PlayerColor owner) {
        this.owner = owner;
    }

    public PlayerColor getOwner() { return owner; }
}
