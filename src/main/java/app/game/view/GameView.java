package app.game.view;

import java.io.IOException;
import java.util.*;

import app.card.apii.Card;
import javax.swing.*;

import app.card.view.TableView;
import app.game.apii.GameController;
import app.game.apii.Observer;
import app.game.controller.GameControllerImpl;
import app.player.apii.Player;
import app.player.view.BailView;
import app.player.view.PlayerPanelView;

/**
 * Gameview.
 */
public class GameView extends JFrame implements Observer {

    JPanel playerPanel;
    JPanel btnPanel;
    TableView tablePanel;

    public GameView(List<Player> playersList) throws IOException {

        playerPanel = new PlayerPanelView();

        tablePanel = new TableView(10);

        List<Card> cardList = tablePanel.getCardList();

        btnPanel = new ButtonPanelView(playersList, cardList, this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public boolean update(Player currentPlayer) {

        BailView bailMessage = new BailView();
        return bailMessage.showMenuBail(currentPlayer);

    }

}
