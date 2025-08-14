package pl.ptemich.jerdy.catan;

public record Coordinates (
        int row,
        int col
)  implements Comparable<Coordinates> {

    public Coordinates moveTo(CoordinateDirections direction) {
        return switch (direction) {
            case EAST -> new Coordinates(row, col + 2);
            case NORTH_EAST -> new Coordinates(row - 1, col + 1);
            case NORTH_WEST -> new Coordinates(row - 1, col - 1);
            case WEST -> new Coordinates(row, col - 2);
            case SOUTH_WEST -> new Coordinates(row + 1, col - 1);
            case SOUTH_EAST -> new Coordinates(row + 1, col + 1);
        };
    }

    @Override
    public int compareTo(Coordinates o) {
        int rowComparison = row - o.row;
        if (rowComparison != 0) {
            return rowComparison;
        }
        int colComparision = col - o.col;
        return colComparision;
    }

}
