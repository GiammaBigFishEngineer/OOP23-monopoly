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

    PlayerPanelView playerPanel;
    JPanel btnPanel;
    TableView tablePanel;

    GameMessage popUp;

    public GameView(List<Player> playersList) throws IOException {

        setLayout(new BorderLayout());

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        playerPanel = new PlayerPanelView(null, null);

        this.add(playerPanel, BorderLayout.NORTH);

        tablePanel = new TableView(7);

        this.add(tablePanel, BorderLayout.CENTER);

        List<Card> cardList = tablePanel.getCardList();

        btnPanel = new ButtonPanelView(playersList, cardList, this);

        popUp = new GameMessage();

        this.add(btnPanel, BorderLayout.SOUTH);

        JPanel bar1 = new JPanel();
        bar1.setBackground(Color.lightGray);
        this.add(bar1, BorderLayout.EAST);

        JPanel bar2 = new JPanel();
        bar2.setBackground(Color.lightGray);
        this.add(bar2, BorderLayout.WEST);

        this.pack();
        this.setMinimumSize(new Dimension(700, 700));

        setVisible(true);

    }

    @Override
    public boolean update(Player currentPlayer, String str) {

        Boolean bool = false;

        switch (str) {
            case "bail":
                BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(currentPlayer, this);

                break;

            case "refreshPlayerPosition":

                System.out.println("aggiorno posizione");

                break;

            case "refreshPlayerPanel":

                System.out.println("aggiorno panel");

                break;

            case "rollDice":

                popUp.rollDice(this);
                break;

        }

        return bool;

    }

}
