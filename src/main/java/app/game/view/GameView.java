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

import app.card.apii.Card;
import app.card.apii.Observer;
import app.card.view.TableView;
import app.game.utils.Dice;
import app.player.apii.Player;
import app.player.view.PlayerPanelView;

/**
 * Gameview.
 */
public final class GameView extends JFrame {

    private final PlayerPanelView playerPanel;
    private final ButtonPanelView btnPanel;
    private final TableView tablePanel;
    private final DiceView dicePanel;

    private final Map<String, Integer> map;

    private static final int PROPORTION = 2;
    private static final int TABLE_SIZE = 7;

    /**
     * 
     * @param playerNames
     * @throws IOException
     */

    public GameView(final List<String> playerNames) throws IOException {

        final Dimension screen;
        final int screenWidth;
        final int screenHeight;

        map = new HashMap<>();

        setLayout(new BorderLayout());

        /*
         * PlayerPanelView
         */

        playerPanel = new PlayerPanelView(null, null);

        this.add(playerPanel, BorderLayout.NORTH);

        /*
         * ButtonPanelView
         */

        btnPanel = new ButtonPanelView(playerNames, new GameObserverImpl(this));

        this.add(btnPanel, BorderLayout.SOUTH);

        /*
         * TableView
         */

        tablePanel = new TableView(btnPanel.getLogicCardList(), TABLE_SIZE);

        btnPanel.newTurn();

        btnPanel.initializeView();

        this.add(tablePanel, BorderLayout.CENTER);

        dicePanel = new DiceView();
        this.add(dicePanel, BorderLayout.EAST);

        /*
         * PlayerPanelView
         */

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = (int) screen.getHeight();
        screenWidth = (int) screen.getWidth();

        this.setSize(screen);
        this.setMinimumSize(new Dimension(screenWidth / PROPORTION, screenHeight / PROPORTION));
        this.setVisible(true);

    }

    /**
     * 
     * @param obj
     */

    public void updateTableView(final Optional<Object> obj) {

        final Player currentPlayer = (Player) obj.get();
        final String name = currentPlayer.getName();

        if (map.containsKey(name)) {

            final Observer<Player> removeObs = () -> tablePanel.removePlayer(currentPlayer.getColor(),
                    map.get(name));

            useObs(removeObs);

            map.remove(name);
        }

        final Observer<Player> addObs = () -> tablePanel.redrawPlayer(currentPlayer.getColor(),
                currentPlayer.getCurrentPosition());

        useObs(addObs);

        map.put(name, currentPlayer.getCurrentPosition());

    }

    /**
     * 
     * @param obs
     */

    public void useObs(final Observer<Player> obs) {

        tablePanel.addObserver(obs);

        obs.update();

        tablePanel.deleteObserver(obs);
    }

    /**
     * 
     * @param obj
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
     * 
     * @param obj
     */

    public void updateDiceView(final Optional<Object> obj) {

        final Dice dice = (Dice) obj.get();
        dicePanel.updateView(dice);

    }

}
