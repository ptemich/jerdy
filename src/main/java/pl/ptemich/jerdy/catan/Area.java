package pl.ptemich.jerdy.catan;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class Area {

    private Coordinates coordinates;
    private Resources generates;

    @Builder.Default
    private Map<CoordinateDirections, Area> neighbours = new HashMap<>();

    @Override
    public String toString() {
        return String.format("[%d, %d]", coordinates.row(), coordinates.col());
    }

    public void register(CoordinateDirections coordinateDirections, Area area) {
        neighbours.put(coordinateDirections, area);
    }

}
