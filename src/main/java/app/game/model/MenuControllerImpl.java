package app.game.model;

import app.game.api.MenuController;
import app.player.api.Player;
import app.player.impl.PlayerImpl;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MenuControllerImpl implements MenuController {
    private List<Player> players;
    private static final String FILE_NAME = "saved_games.txt";

    public MenuControllerImpl() {
        this.players = new ArrayList<>();
    }

    @Override
    public boolean startGame(List<Player> players) {
        if (players.size() < 2 || players.size() > 5) {
            System.out.println("Inserire da 2 a 5 giocatori.");
            return false;
        }

        this.players = players;
        System.out.println("Start game...");
        return true;
    }

    @Override
    public void quitGame() {
        System.out.println("...Exit game");
        System.exit(0);
    }

    @Override
    public List<Player> insertPlayers(List<String> currentPlayerNames) {
        List<Player> currentPlayers = new ArrayList<>();

        for (int i = 0; i < currentPlayerNames.size(); i++) {
            String playerName = currentPlayerNames.get(i);
            if (playerName == null || playerName.isEmpty()) {
                System.out.println("Errore: il nome del giocatore " + (i + 1) + " non e' valido.");
                return new ArrayList<>();
            }

            Player player = new PlayerImpl(i + 1, playerName);
            currentPlayers.add(player);
        }

        return currentPlayers;
    }

    @Override
    public void saveGame() {
        try {
            if (players != null && !players.isEmpty()) {
                saveGameToFile(players);
                System.out.println("Partita salvata con successo!");
            } else {
                throw new Exception("Impossibile salvare la partita. Inizia una partita prima di salvare.");
            }
        } catch (Exception e) {
            System.out.println("Errore durante il salvataggio del gioco: " + e.getMessage());
        }
    }

    private void saveGameToFile(List<Player> players) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("saved_games.txt", true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
            String timestamp = dateFormat.format(new Date());
            writer.println("*** Partita del: " + timestamp + " ***");

            for (Player currentPlayer : players) {
                writer.println("Player: " + currentPlayer.getName() + ", Id: " + currentPlayer.getId() + ", Posizione: " + currentPlayer.getCurrentPosition() + ", Denaro: " + currentPlayer.getMoney());
            }
            writer.println("\n");
            
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio del gioco: " + e.getMessage());
        }
    }

    @Override
    public List<String> viewSavedGames() {
        List<String> savedGames = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Nessuna partita salvata.");
            return savedGames;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                savedGames.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Errore durante il caricamento delle partite salvate.");
        }

        System.out.println("Caricamento partite salvate...");
        return savedGames;
    }
}
