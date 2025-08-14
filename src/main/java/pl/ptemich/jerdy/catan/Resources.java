package pl.ptemich.jerdy.catan;

public record Resources(
        int stones,
        int clay,
        int sheeps,
        int wood,
        int wheat
) {

    public static Resources nothing() {
        return new Resources(0, 0, 0, 0, 0);
    }

    public static Resources stones(int noOfStones) {
        return new Resources(noOfStones, 0, 0, 0, 0);
    }

}
