package game.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import app.game.apii.GameController;
import app.game.controller.GameControllerImpl;
import app.player.view.ButtonPanelView;
import app.player.view.PlayerPanelView;

/**
 * Gameview.
 */
public class GameView extends JFrame implements Observer {

    ButtonPanelView btnView;
    JPanel playerView;

    public GameView() {

        btnView = new ButtonPanelView();
        btnView.getLogic().registerObserver(this);

        playerView = new PlayerPanelView();

    }

    @Override
    public void update() {
        JOptionPane.showMessageDialog(null, "prova");
    }

}
