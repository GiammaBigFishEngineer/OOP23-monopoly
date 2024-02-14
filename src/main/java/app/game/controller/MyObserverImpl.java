package app.game.controller;

import java.awt.Color;

import app.card.apii.Observer;
import app.game.apii.GameObserver;
import app.game.view.GameMessage;
import app.game.view.GameView;
import app.player.apii.Player;
import app.player.view.BailView;

public class MyObserverImpl implements GameObserver {

    GameView gameV;
    GameMessage popUp;

    public MyObserverImpl(GameView gameV) {
        this.gameV = gameV;
        popUp = new GameMessage();
    }

    @Override
    public boolean update(Player currentPlayer, String str) {
        Boolean bool = false;

        switch (str) {
            case "bail":
                BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(currentPlayer, gameV);

                break;

            case "refreshPlayerPosition":

                System.out.println("aggiorno posizione");
                Observer<Player> tableObs = () -> gameV.getTableView().redrawPlayer("#fff",
                        currentPlayer.getCurrentPosition());
                gameV.getTableView().addObserver(tableObs);

                gameV.getTableView().notifyObservers();

                break;

            case "refreshPlayerPanel":

                var box = gameV.getTableView().getCardList().get(currentPlayer.getCurrentPosition());
                gameV.getPlayerPanelView().getLogic().setPlayer(currentPlayer, box);
                gameV.getPlayerPanelView().getLogic().setCurrentBox(box);

                gameV.getPlayerPanelView().getLogic().refresh();

                break;

            case "rollDice":

                popUp.rollDice(gameV);
                break;

        }

        return bool;
    }

}
