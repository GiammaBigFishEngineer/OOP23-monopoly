package game.view;

import java.util.*;

import javax.swing.*;

import app.game.apii.GameController;
import app.game.controller.GameControllerImpl;
import app.player.view.ButtonPanelView;
import app.player.view.PlayerPanelView;

/**
 * Gameview.
 */
public class GameView extends JFrame implements Observer {

    JPanel playerView;

    GameController logic;

    List<JButton> btnList;

    public GameView() {

        this.logic = new GameControllerImpl(null);

        logic.registerObserver(this);

        playerView = new PlayerPanelView();

    }

    public List<JButton> getBtnList() {
        return this.btnList;
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
