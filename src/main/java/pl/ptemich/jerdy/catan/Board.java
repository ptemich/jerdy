package pl.ptemich.jerdy.catan;

import java.util.*;

public class Board {

    private final int NO_OF_CIRCLES = 5;

    private Map<Coordinates, Area> areasByCoordinates = new HashMap<>();
    private Area center;

    public Board() {
        center = Area.builder()
                    .coordinates(new Coordinates(0,0))
                    .generates(Resources.nothing())
                    .build();

        areasByCoordinates.put(center.getCoordinates(), center);

        Arrays.stream(CoordinateDirections.values()).
                forEach(direction -> registerNeighbour(center, direction));
    }

    private void registerNeighbour(Area currentArea, CoordinateDirections direction) {
        Coordinates targetCoordinates = currentArea.getCoordinates().moveTo(direction);
        Area targetArea = areasByCoordinates.get(targetCoordinates);
        if (targetArea == null) {
            targetArea = Area.builder()
                    .coordinates(targetCoordinates)
                    .generates(Resources.stones(1))
                    .build();
            areasByCoordinates.put(targetCoordinates, targetArea);
            currentArea.register(direction, targetArea);
        }
    }

    public void show() {
        areasByCoordinates.values().stream()
                .sorted(Comparator.comparing(Area::getCoordinates))
                .forEach(System.out::print);
    }
}
