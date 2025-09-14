package pl.ptemich.jerdy.catanai;


class Building {
    private PlayerColor owner;
    private int points;

    public Building(PlayerColor owner, int points) {
        this.owner = owner;
        this.points = points;
    }

    public PlayerColor getOwner() { return owner; }
    public int getPoints() { return points; }
    public void upgrade() { if (points == 1) points = 2; }
}