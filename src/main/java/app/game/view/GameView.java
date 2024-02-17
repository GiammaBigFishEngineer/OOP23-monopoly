package app.game.view;

import java.io.IOException;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import app.card.view.TableView;
import app.player.view.PlayerPanelView;

/**
 * Gameview.
 */
public class GameView extends JFrame {

    final private PlayerPanelView playerPanel;
    final private ButtonPanelView btnPanel;
    final private TableView tablePanel;

    private static final int PROPORTION = 2;

    public GameView(final List<String> playerNames) throws IOException {

        final Dimension screen;
        final int screenWidth;
        final int screenHeight;

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

        tablePanel = new TableView(btnPanel.getLogic().getTableList(), 7);

        btnPanel.initializeView();

        this.add(tablePanel, BorderLayout.CENTER);

        /*
         * Board Panels
         */

        final JPanel bar1 = new JPanel();
        bar1.setBackground(Color.lightGray);
        this.add(bar1, BorderLayout.EAST);

        final JPanel bar2 = new JPanel();
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

    public ButtonPanelView getButtonView() {
        return this.btnPanel;
    }

}
