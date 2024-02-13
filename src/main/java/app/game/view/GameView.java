package app.game.view;

import java.io.IOException;
import java.awt.*;
import java.util.List;

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

        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        playerPanel = new PlayerPanelView();

        this.add(playerPanel, BorderLayout.NORTH);

        tablePanel = new TableView(7);

        this.add(tablePanel, BorderLayout.CENTER);

        List<Card> cardList = tablePanel.getCardList();

        btnPanel = new ButtonPanelView(playersList, cardList, this);

        this.add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    @Override
    public boolean update(Player currentPlayer) {

        BailView bailMessage = new BailView();
        return bailMessage.showMenuBail(currentPlayer);

    }

}
