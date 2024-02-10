package app.player.view;

import javax.swing.JPanel;

import app.game.apii.GameController;
import app.game.controller.GameControllerImpl;

/**
 * ButtonsView.
 */
public class ButtonPanelView extends JPanel {

    GameController logic;

    public ButtonPanelView() {
        this.logic = new GameControllerImpl(null);
    }

    public GameController getLogic() {
        return this.logic;
    }

}
