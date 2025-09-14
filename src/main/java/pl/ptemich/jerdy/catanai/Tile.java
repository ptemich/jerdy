package pl.ptemich.jerdy.catanai;


// Klasa reprezentująca pole na planszy
class Tile {
    private Resource resource;
    private int number;
    private boolean hasRobber;

    public Tile(Resource resource, int number) {
        this.resource = resource;
        this.number = number;
        this.hasRobber = resource == Resource.DESERT;
    }

    public Resource getResource() { return resource; }
    public int getNumber() { return number; }
    public boolean hasRobber() { return hasRobber; }
    public void setRobber(boolean hasRobber) { this.hasRobber = hasRobber; }
}

// Klasa reprezentująca budynek (osada/miasto)
