package app.game.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import app.card.api.Card;
import app.card.api.Observer;
import app.card.view.TableView;

import app.game.utils.Dice;
import app.player.api.Player;
import app.player.view.PlayerPanelView;

/**
 * this class represent the game view.
 */
public final class GameView extends JFrame {

    private static final long serialVersionUID = 11L;

    private final PlayerPanelView playerPanel;

    private final TableView tablePanel;
    private final DiceView dicePanel;

    private final Map<String, Integer> playerMapView;

    private static final int PROPORTION = 2;
    private static final int TABLE_SIZE = 7;

    /**
     * Constructor.
     * 
     * @param playerNames
     * @throws IOException
     */

    public GameView(final List<String> playerNames) throws IOException {

        final Dimension screen;
        final int screenWidth;
        final int screenHeight;

        final ButtonPanelView btnPanel;

        playerMapView = new HashMap<>();

        setLayout(new BorderLayout());

        /*
         * PlayerPanel
         */

        playerPanel = new PlayerPanelView(null, null);

        this.add(playerPanel, BorderLayout.NORTH);

        /*
         * ButtonPanelView
         */

        btnPanel = new ButtonPanelView(playerNames, new ViewObserverImpl(this));

        this.add(btnPanel, BorderLayout.SOUTH);

        /*
         * TableView
         */

        tablePanel = new TableView(btnPanel.getLogicCardList(), TABLE_SIZE);

        this.add(tablePanel, BorderLayout.CENTER);

        /*
         * these methods are called to start the game and the view
         */

        btnPanel.newTurn();

        btnPanel.initializeView();

        /*
         * DiceView
         */

        dicePanel = new DiceView();
        this.add(dicePanel, BorderLayout.EAST);

        /*
         * Setting frame size
         */
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = (int) screen.getHeight();
        screenWidth = (int) screen.getWidth();

        this.setSize(screen);
        this.setMinimumSize(new Dimension(screenWidth / PROPORTION, screenHeight / PROPORTION));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * This method is used to update player position in tablePanel.
     * The map playerMapView is used to check if a player is already present on the
     * table and if this is the case, before adding a new position the previous one
     * is removed
     * 
     * @param obj is the player
     */

    public void updateTableView(final Optional<Object> obj) {

        final Player currentPlayer = (Player) obj.get();
        final String name = currentPlayer.getName();

        if (playerMapView.containsKey(name)) {

            final Observer<Player> removeObs = () -> tablePanel.removePlayer(currentPlayer.getColor(),
                    playerMapView.get(name));

            useObs(removeObs);

            playerMapView.remove(name);
        }

        final Observer<Player> addObs = () -> tablePanel.redrawPlayer(currentPlayer.getColor(),
                currentPlayer.getCurrentPosition());

        useObs(addObs);

        playerMapView.put(name, currentPlayer.getCurrentPosition());

    }

    /**
     * This method is used to add the observer to tableView, update it, and then.
     * remove it
     * 
     * @param obs
     */

    public void useObs(final Observer<Player> obs) {

        tablePanel.addObserver(obs);

        obs.update();

        tablePanel.deleteObserver(obs);
    }

    /**
     * This method is used to update player stats in playerPanel.
     * 
     * @param obj is the player
     */

    public void updatePlayerPanelView(final Optional<Object> obj) {

        final Player player = (Player) obj.get();

        final var card = tablePanel.getCardList()
                .stream()
                .sorted(Comparator.comparingInt(Card::getCardId))
                .collect(Collectors.toList())
                .get(player.getCurrentPosition());

        playerPanel.setPlayer(player, card);

        playerPanel.setCurrentBox(card);

    }

    /**
     * This method is used to update the dice values dicePanel.
     * 
     * @param obj is the dice
     */

    public void updateDiceView(final Optional<Object> obj) {

        final Dice dice = (Dice) obj.get();
        dicePanel.updateView(dice);

    }

}
