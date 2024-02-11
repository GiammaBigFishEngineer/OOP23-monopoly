package game.view;

import java.util.*;

import javax.swing.*;

import app.game.apii.GameController;
import app.game.controller.GameControllerImpl;
import app.game.view.ButtonPanelView;
import app.player.view.PlayerPanelView;

/**
 * Gameview.
 */
public class GameView extends JFrame implements Observer {

    JPanel playerView;
    JPanel btnPanel;

    GameController logic;

    public GameView() {

        playerView = new PlayerPanelView();
        btnPanel = new ButtonPanelView(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public boolean update() {

        int result = JOptionPane.showOptionDialog(this,
                "You are in prison, do you want to pay bail?",
                "Bail option",
                JOptionPane.NO_OPTION,
                JOptionPane.OK_OPTION,
                null,
                null,
                null);

        if (result == JOptionPane.OK_OPTION) {
            return true;
        } else {
            return false;
        }

    }

}
