package app.game.model;

import app.game.api.MenuController;
import app.player.api.Player;
import app.player.impl.PlayerImpl;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<Player> insertPlayers(List<String> playerNames) {
        List<Player> currentPlayers = new ArrayList<>();

        for (int i = 0; i < playerNames.size(); i++) {
            Player player = new PlayerImpl(i + 1, playerNames.get(i));
            currentPlayers.add(player);
        }

        return currentPlayers;
    }

    @Override
    public void saveGame() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'alle' HH.mm.ss");
        String timestamp = dateTime.format(formatter);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write("--- Partita del: " + timestamp + " ---");
            writer.newLine();

            for (Player player : players) {
                final String playerData = player.getName() + "," + player.getId() + "," + player.getMoney() + "," + player.getCurrentPosition();
                writer.write(playerData);
                writer.newLine();
            }

            System.out.println("Partita salvata con successo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Errore durante il salvataggio della partita.");
        }
    }

    @Override
    public List<String> viewSavedGames() {
        List<String> savedGames = new ArrayList<>();
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
