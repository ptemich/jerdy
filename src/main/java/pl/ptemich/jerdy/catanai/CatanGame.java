package pl.ptemich.jerdy.catanai;

import java.util.*;
import java.util.stream.Collectors;

// Główna klasa gry
public class CatanGame {
    private static final int BOARD_SIZE = 19;
    private List<Player> players;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private Tile[] board;
    private Building[] settlements;
    private Road[] roads;
    private Scanner scanner;
    private Random random;
    private boolean gameStarted;
    private int round;
    
    // Koszty budowy
    private static final Map<Resource, Integer> SETTLEMENT_COST = Map.of(
        Resource.BRICK, 1, Resource.WOOD, 1, Resource.SHEEP, 1, Resource.WHEAT, 1
    );
    private static final Map<Resource, Integer> CITY_COST = Map.of(
        Resource.WHEAT, 2, Resource.ORE, 3
    );
    private static final Map<Resource, Integer> ROAD_COST = Map.of(
        Resource.BRICK, 1, Resource.WOOD, 1
    );
    
    public CatanGame() {
        this.players = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.board = new Tile[BOARD_SIZE];
        this.settlements = new Building[54]; // 54 możliwe miejsca na osady
        this.roads = new Road[72]; // 72 możliwe miejsca na drogi
        this.gameStarted = false;
        this.round = 0;
        
        initializeBoard();
    }
    
    private void initializeBoard() {
        // Standardowa plansza Catan - uproszczona wersja
        Resource[] resources = {
            Resource.WHEAT, Resource.SHEEP, Resource.WOOD,
            Resource.BRICK, Resource.ORE, Resource.WHEAT,
            Resource.SHEEP, Resource.DESERT, Resource.WOOD,
            Resource.WHEAT, Resource.WOOD, Resource.BRICK,
            Resource.SHEEP, Resource.ORE, Resource.BRICK,
            Resource.WHEAT, Resource.SHEEP, Resource.WOOD,
            Resource.ORE
        };
        
        int[] numbers = {10, 2, 9, 12, 6, 4, 10, 0, 9, 11, 7, 3, 8, 8, 3, 4, 5, 5, 6};
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = new Tile(resources[i], numbers[i]);
        }
    }
    
    public void startGame() {
        System.out.println("=== WITAMY W GRZE CATAN ===");
        setupPlayers();
        
        while (!isGameOver()) {
            round++;
            System.out.println("\n=== RUNDA " + round + " ===");
            
            for (Player player : players) {
                currentPlayer = player;
                playTurn();
                
                if (isGameOver()) {
                    break;
                }
            }
        }
        
        announceWinner();
    }
    
    private void setupPlayers() {
        System.out.println("Podaj liczbę graczy (2-4): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();
        
        PlayerColor[] colors = PlayerColor.values();
        
        for (int i = 0; i < Math.min(numPlayers, 4); i++) {
            System.out.print("Podaj imię gracza " + (i + 1) + ": ");
            String name = scanner.nextLine();
            players.add(new Player(name, colors[i]));
        }
        
        gameStarted = true;
        System.out.println("\nGra rozpoczyna się!");
        
        // Faza początkowa - każdy gracz stawia 2 osady i 2 drogi
        initialPlacement();
    }
    
    private void initialPlacement() {
        System.out.println("\n=== FAZA POCZĄTKOWA ===");
        System.out.println("Każdy gracz stawia 2 osady i 2 drogi");
        
        // Pierwsza runda - kolejność normalna
        for (Player player : players) {
            currentPlayer = player;
            System.out.println("\nKolej gracza: " + player.getName() + " (" + player.getColor().getName() + ")");
            displayBoard();
            placeInitialSettlement();
            placeInitialRoad();
        }
        
        // Druga runda - kolejność odwrotna
        for (int i = players.size() - 1; i >= 0; i--) {
            currentPlayer = players.get(i);
            System.out.println("\nKolej gracza: " + currentPlayer.getName() + " (" + currentPlayer.getColor().getName() + ")");
            displayBoard();
            placeInitialSettlement();
            placeInitialRoad();
            
            // Gracz otrzymuje zasoby z drugiej osady
            giveInitialResources();
        }
    }
    
    private void placeInitialSettlement() {
        System.out.println("Wybierz miejsce na osadę (0-53): ");
        int position = scanner.nextInt();
        
        if (position >= 0 && position < 54 && settlements[position] == null) {
            settlements[position] = new Building(currentPlayer.getColor(), 1);
            currentPlayer.buildSettlement();
            System.out.println("Osada postawiona na pozycji " + position);
        } else {
            System.out.println("Nieprawidłowa pozycja!");
            placeInitialSettlement();
        }
    }
    
    private void placeInitialRoad() {
        System.out.println("Wybierz miejsce na drogę (0-71): ");
        int position = scanner.nextInt();
        
        if (position >= 0 && position < 72 && roads[position] == null) {
            roads[position] = new Road(currentPlayer.getColor());
            currentPlayer.buildRoad();
            System.out.println("Droga postawiona na pozycji " + position);
        } else {
            System.out.println("Nieprawidłowa pozycja!");
            placeInitialRoad();
        }
    }
    
    private void giveInitialResources() {
        // Uproszczona wersja - dajemy po 1 zasobie każdego typu
        currentPlayer.addResource(Resource.WHEAT, 1);
        currentPlayer.addResource(Resource.WOOD, 1);
        currentPlayer.addResource(Resource.BRICK, 1);
        currentPlayer.addResource(Resource.SHEEP, 1);
        currentPlayer.addResource(Resource.ORE, 1);
    }
    
    private void playTurn() {
        System.out.println("\n--- Tura gracza: " + currentPlayer.getName() + " ---");
        
        // 1. Rzut kostką
        rollDice();
        
        // 2. Menu akcji gracza
        boolean turnEnded = false;
        while (!turnEnded) {
            displayPlayerStatus();
            System.out.println("\nWybierz akcję:");
            System.out.println("1. Zbuduj osadę");
            System.out.println("2. Zbuduj miasto");
            System.out.println("3. Zbuduj drogę");
            System.out.println("4. Handel z bankiem");
            System.out.println("5. Pokaż planszę");
            System.out.println("6. Zakończ turę");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    buildSettlement();
                    break;
                case 2:
                    buildCity();
                    break;
                case 3:
                    buildRoad();
                    break;
                case 4:
                    tradeWithBank();
                    break;
                case 5:
                    displayBoard();
                    break;
                case 6:
                    turnEnded = true;
                    break;
                default:
                    System.out.println("Nieprawidłowy wybór!");
            }
        }
    }
    
    private void rollDice() {
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        int total = die1 + die2;
        
        System.out.println("Rzut kostką: " + die1 + " + " + die2 + " = " + total);
        
        if (total == 7) {
            handleRobber();
        } else {
            distributeResources(total);
        }
    }
    
    private void distributeResources(int number) {
        System.out.println("Dystrybucja zasobów za liczbę " + number);
        
        for (int i = 0; i < board.length; i++) {
            Tile tile = board[i];
            if (tile.getNumber() == number && !tile.hasRobber()) {
                Resource resource = tile.getResource();
                if (resource != Resource.DESERT) {
                    // Znajdź graczy, którzy mają osady/miasta przy tym polu
                    giveResourceToPlayersAtTile(i, resource);
                }
            }
        }
    }
    
    private void giveResourceToPlayersAtTile(int tileIndex, Resource resource) {
        // Uproszczona wersja - dajemy zasób wszystkim graczom
        for (Player player : players) {
            player.addResource(resource, 1);
        }
        System.out.println("Wszyscy gracze otrzymali: " + resource.getName());
    }
    
    private void handleRobber() {
        System.out.println("Wyrzucono 7 - aktywacja rozbójnika!");
        
        // Gracze z więcej niż 7 kartami muszą odrzucić połowę
        for (Player player : players) {
            int resourceCount = player.getTotalResourceCount();
            if (resourceCount > 7) {
                int toDiscard = resourceCount / 2;
                System.out.println(player.getName() + " musi odrzucić " + toDiscard + " kart");
                // Uproszczona wersja - automatycznie odrzucamy losowe karty
                discardRandomResources(player, toDiscard);
            }
        }
        
        // Przesunięcie rozbójnika
        moveRobber();
    }
    
    private void discardRandomResources(Player player, int count) {
        List<Resource> availableResources = new ArrayList<>();
        for (Map.Entry<Resource, Integer> entry : player.getResources().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                availableResources.add(entry.getKey());
            }
        }
        
        for (int i = 0; i < count && !availableResources.isEmpty(); i++) {
            Resource toRemove = availableResources.remove(random.nextInt(availableResources.size()));
            player.getResources().put(toRemove, player.getResources().get(toRemove) - 1);
        }
    }
    
    private void moveRobber() {
        System.out.println("Wybierz pole dla rozbójnika (0-18): ");
        int position = scanner.nextInt();
        
        if (position >= 0 && position < board.length) {
            // Usuń rozbójnika ze starego miejsca
            for (Tile tile : board) {
                tile.setRobber(false);
            }
            
            // Postaw rozbójnika w nowym miejscu
            board[position].setRobber(true);
            System.out.println("Rozbójnik przesunięty na pole " + position);
        } else {
            System.out.println("Nieprawidłowa pozycja!");
            moveRobber();
        }
    }
    
    private void buildSettlement() {
        if (currentPlayer.getSettlementsLeft() == 0) {
            System.out.println("Nie masz więcej osad do budowy!");
            return;
        }
        
        if (!currentPlayer.hasResources(SETTLEMENT_COST)) {
            System.out.println("Nie masz wystarczających zasobów!");
            displayCost("Osada", SETTLEMENT_COST);
            return;
        }
        
        System.out.println("Wybierz miejsce na osadę (0-53): ");
        int position = scanner.nextInt();
        
        if (position >= 0 && position < 54) {
            if (settlements[position] == null) {
                currentPlayer.payResources(SETTLEMENT_COST);
                settlements[position] = new Building(currentPlayer.getColor(), 1);
                currentPlayer.buildSettlement();
                System.out.println("Osada zbudowana!");
                
                if (currentPlayer.getVictoryPoints() >= 10) {
                    return;
                }
            } else {
                System.out.println("To miejsce jest już zajęte!");
            }
        } else {
            System.out.println("Nieprawidłowa pozycja!");
        }
    }
    
    private void buildCity() {
        if (currentPlayer.getCitiesLeft() == 0) {
            System.out.println("Nie masz więcej miast do budowy!");
            return;
        }
        
        if (!currentPlayer.hasResources(CITY_COST)) {
            System.out.println("Nie masz wystarczających zasobów!");
            displayCost("Miasto", CITY_COST);
            return;
        }
        
        System.out.println("Wybierz osadę do ulepszenia (0-53): ");
        int position = scanner.nextInt();
        
        if (position >= 0 && position < 54) {
            Building building = settlements[position];
            if (building != null && building.getOwner() == currentPlayer.getColor() && building.getPoints() == 1) {
                currentPlayer.payResources(CITY_COST);
                building.upgrade();
                currentPlayer.buildCity();
                System.out.println("Miasto zbudowane!");
            } else {
                System.out.println("Nie możesz zbudować miasta w tym miejscu!");
            }
        } else {
            System.out.println("Nieprawidłowa pozycja!");
        }
    }
    
    private void buildRoad() {
        if (currentPlayer.getRoadsLeft() == 0) {
            System.out.println("Nie masz więcej dróg do budowy!");
            return;
        }
        
        if (!currentPlayer.hasResources(ROAD_COST)) {
            System.out.println("Nie masz wystarczających zasobów!");
            displayCost("Droga", ROAD_COST);
            return;
        }
        
        System.out.println("Wybierz miejsce na drogę (0-71): ");
        int position = scanner.nextInt();
        
        if (position >= 0 && position < 72) {
            if (roads[position] == null) {
                currentPlayer.payResources(ROAD_COST);
                roads[position] = new Road(currentPlayer.getColor());
                currentPlayer.buildRoad();
                System.out.println("Droga zbudowana!");
            } else {
                System.out.println("To miejsce jest już zajęte!");
            }
        } else {
            System.out.println("Nieprawidłowa pozycja!");
        }
    }
    
    private void tradeWithBank() {
        System.out.println("Handel z bankiem (4:1)");
        System.out.println("Którym zasobem chcesz handlować?");
        
        int i = 1;
        List<Resource> availableResources = new ArrayList<>();
        for (Resource resource : Resource.values()) {
            if (resource != Resource.DESERT && currentPlayer.getResources().get(resource) >= 4) {
                System.out.println(i + ". " + resource.getName() + " (masz: " + currentPlayer.getResources().get(resource) + ")");
                availableResources.add(resource);
                i++;
            }
        }
        
        if (availableResources.isEmpty()) {
            System.out.println("Nie masz wystarczających zasobów do handlu!");
            return;
        }
        
        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < availableResources.size()) {
            Resource giving = availableResources.get(choice);
            
            System.out.println("Jaki zasób chcesz otrzymać?");
            List<Resource> targetResources = Arrays.stream(Resource.values())
                .filter(r -> r != Resource.DESERT && r != giving)
                .collect(Collectors.toList());
            
            for (i = 0; i < targetResources.size(); i++) {
                System.out.println((i + 1) + ". " + targetResources.get(i).getName());
            }
            
            int targetChoice = scanner.nextInt() - 1;
            if (targetChoice >= 0 && targetChoice < targetResources.size()) {
                Resource receiving = targetResources.get(targetChoice);
                
                currentPlayer.getResources().put(giving, currentPlayer.getResources().get(giving) - 4);
                currentPlayer.addResource(receiving, 1);
                
                System.out.println("Wymieniono 4x " + giving.getName() + " na 1x " + receiving.getName());
            }
        }
    }
    
    private void displayCost(String item, Map<Resource, Integer> cost) {
        System.out.println("Koszt " + item + ": ");
        for (Map.Entry<Resource, Integer> entry : cost.entrySet()) {
            System.out.println("- " + entry.getValue() + "x " + entry.getKey().getName());
        }
    }
    
    private void displayPlayerStatus() {
        System.out.println("\n=== Status gracza: " + currentPlayer.getName() + " ===");
        System.out.println("Punkty zwycięstwa: " + currentPlayer.getVictoryPoints());
        System.out.println("Zasoby:");
        for (Map.Entry<Resource, Integer> entry : currentPlayer.getResources().entrySet()) {
            System.out.println("- " + entry.getKey().getName() + ": " + entry.getValue());
        }
        System.out.println("Pozostało - Osady: " + currentPlayer.getSettlementsLeft() + 
                         ", Miasta: " + currentPlayer.getCitiesLeft() + 
                         ", Drogi: " + currentPlayer.getRoadsLeft());
    }
    
    private void displayBoard() {
        System.out.println("\n=== PLANSZA CATAN ===");
        System.out.println("Pola (zasób-liczba, R=rozbójnik):");
        
        // Wyświetl pola w formie siatki hex (uproszczonej)
        for (int row = 0; row < 5; row++) {
            displayBoardRow(row);
        }
        
        System.out.println("\nOsady/Miasta:");
        for (int i = 0; i < settlements.length; i++) {
            if (settlements[i] != null) {
                String type = settlements[i].getPoints() == 1 ? "Osada" : "Miasto";
                System.out.println("[" + i + "] " + type + " - " + settlements[i].getOwner().getName());
            }
        }
        
        System.out.println("\nDrogi:");
        for (int i = 0; i < Math.min(20, roads.length); i++) {
            if (roads[i] != null) {
                System.out.println("[" + i + "] Droga - " + roads[i].getOwner().getName());
            }
        }
    }
    
    private void displayBoardRow(int row) {
        String indent = "";
        int start, end;
        
        switch (row) {
            case 0:
                indent = "      ";
                start = 0; end = 3;
                break;
            case 1:
                indent = "   ";
                start = 3; end = 7;
                break;
            case 2:
                indent = "";
                start = 7; end = 12;
                break;
            case 3:
                indent = "   ";
                start = 12; end = 16;
                break;
            case 4:
                indent = "      ";
                start = 16; end = 19;
                break;
            default:
                return;
        }
        
        System.out.print(indent);
        for (int i = start; i < end && i < board.length; i++) {
            Tile tile = board[i];
            String display = tile.getResource().getSymbol();
            if (tile.getNumber() > 0) {
                display += tile.getNumber();
            }
            if (tile.hasRobber()) {
                display += "R";
            }
            System.out.printf("[%2d:%-3s] ", i, display);
        }
        System.out.println();
    }
    
    private boolean isGameOver() {
        for (Player player : players) {
            if (player.getVictoryPoints() >= 10) {
                return true;
            }
        }
        return false;
    }
    
    private void announceWinner() {
        Player winner = players.stream()
            .max(Comparator.comparingInt(Player::getVictoryPoints))
            .orElse(null);
        
        if (winner != null) {
            System.out.println("\n🎉 KONIEC GRY! 🎉");
            System.out.println("Zwycięzca: " + winner.getName() + " (" + winner.getColor().getName() + ")");
            System.out.println("Punkty zwycięstwa: " + winner.getVictoryPoints());
            
            System.out.println("\nKlasyfikacja końcowa:");
            players.stream()
                .sorted(Comparator.comparingInt(Player::getVictoryPoints).reversed())
                .forEach(p -> System.out.println(p.getName() + ": " + p.getVictoryPoints() + " pkt"));
        }
    }
    
    public static void main(String[] args) {
        CatanGame game = new CatanGame();
        game.startGame();
    }
}