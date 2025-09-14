package pl.ptemich.jerdy.catanai;

import java.util.HashMap;
import java.util.Map;

// Klasa reprezentująca gracza
class Player {
    private String name;
    private PlayerColor color;
    private Map<Resource, Integer> resources;
    private int victoryPoints;
    private int settlementsLeft;
    private int citiesLeft;
    private int roadsLeft;

    public Player(String name, PlayerColor color) {
        this.name = name;
        this.color = color;
        this.resources = new HashMap<>();
        for (Resource r : Resource.values()) {
            if (r != Resource.DESERT) {
                resources.put(r, 0);
            }
        }
        this.victoryPoints = 0;
        this.settlementsLeft = 5;
        this.citiesLeft = 4;
        this.roadsLeft = 15;
    }

    public String getName() { return name; }
    public PlayerColor getColor() { return color; }
    public Map<Resource, Integer> getResources() { return resources; }
    public int getVictoryPoints() { return victoryPoints; }
    public void addVictoryPoints(int points) { victoryPoints += points; }
    public int getSettlementsLeft() { return settlementsLeft; }
    public int getCitiesLeft() { return citiesLeft; }
    public int getRoadsLeft() { return roadsLeft; }

    public void addResource(Resource resource, int amount) {
        if (resource != Resource.DESERT) {
            resources.put(resource, resources.get(resource) + amount);
        }
    }

    public boolean hasResources(Map<Resource, Integer> cost) {
        for (Map.Entry<Resource, Integer> entry : cost.entrySet()) {
            if (resources.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void payResources(Map<Resource, Integer> cost) {
        for (Map.Entry<Resource, Integer> entry : cost.entrySet()) {
            resources.put(entry.getKey(), resources.get(entry.getKey()) - entry.getValue());
        }
    }

    public void buildSettlement() {
        if (settlementsLeft > 0) {
            settlementsLeft--;
            addVictoryPoints(1);
        }
    }

    public void buildCity() {
        if (citiesLeft > 0) {
            citiesLeft--;
            settlementsLeft++;
            addVictoryPoints(1);
        }
    }

    public void buildRoad() {
        if (roadsLeft > 0) {
            roadsLeft--;
        }
    }

    public int getTotalResourceCount() {
        return resources.values().stream().mapToInt(Integer::intValue).sum();
    }
}
