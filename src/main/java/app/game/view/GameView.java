package app.game.view;

import java.io.IOException;
import java.awt.*;

import java.util.List;

import app.card.apii.Card;
import javax.swing.*;

import app.card.view.TableView;

import app.game.controller.MyObserverImpl;
import app.game.model.ListConverter;
import app.player.apii.Player;

import app.player.view.PlayerPanelView;

/**
 * Gameview.
 */
public class GameView extends JFrame {

    private PlayerPanelView playerPanel;
    private JPanel btnPanel;
    private TableView tablePanel;

    private final Dimension screen;
    private final int screenWidth;
    private final int screenHeight;
    private final int PROPORTION = 2;

    public GameView(List<String> playerNames) throws IOException {

        setLayout(new BorderLayout());

        /*
         * PlayerPanelView
         */

        playerPanel = new PlayerPanelView(null, null);

        this.add(playerPanel, BorderLayout.NORTH);

        /*
         * TableView
         */

        tablePanel = new TableView(7);

        this.add(tablePanel, BorderLayout.CENTER);

        List<Card> cardList = tablePanel.getCardList();
        List<Player> playersList;

        playersList = ListConverter.convert(playerNames, cardList);

        /*
         * ButtonPanelView
         */

        btnPanel = new ButtonPanelView(playersList, cardList, new MyObserverImpl(this));

        this.add(btnPanel, BorderLayout.SOUTH);

        /*
         * Board Panels
         */

        JPanel bar1 = new JPanel();
        bar1.setBackground(Color.lightGray);
        this.add(bar1, BorderLayout.EAST);

        JPanel bar2 = new JPanel();
        bar2.setBackground(Color.lightGray);
        this.add(bar2, BorderLayout.WEST);

        /*
         * PlayerPanelView
         */

        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = (int) screen.getHeight();
        screenWidth = (int) screen.getWidth();

        this.setSize(screen);
        this.setMinimumSize(new Dimension(screenWidth / PROPORTION, screenHeight / PROPORTION));
        this.setVisible(true);

    }

    public PlayerPanelView getPlayerPanelView() {
        return this.playerPanel;
    }

    public TableView getTableView() {
        return this.tablePanel;
    }

}
