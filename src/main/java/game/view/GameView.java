package game.view;

import java.util.*;

import javax.swing.*;

import app.game.apii.GameController;
import app.game.controller.GameControllerImpl;
import app.game.view.ButtonPanelView;
import app.player.apii.Player;
import app.player.view.BailView;
import app.player.view.PlayerPanelView;

/**
 * Gameview.
 */
public class GameView extends JFrame implements Observer {

    JPanel playerView;
    JPanel btnPanel;

    GameController logic;

    public GameView(List<Player> playersList) {

        playerView = new PlayerPanelView();
        btnPanel = new ButtonPanelView(playersList, this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public boolean update(Player currentPlayer) {

        BailView bailMessage = new BailView();
        return bailMessage.showMenuBail(currentPlayer);

    }

}
