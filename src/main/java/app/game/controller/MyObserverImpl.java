package app.game.controller;

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
    public boolean update(Integer diceValue, Player currentPlayer, String str) {
        Boolean bool = false;

        switch (str) {
            case "bail":
                BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(currentPlayer, gameV);

                break;

            case "refreshPlayerPosition":

                var tableView = gameV.getTableView();

                System.out.println("aggiorno posizione");
                Observer<Player> tableObs = () -> tableView.redrawPlayer("#fff",
                        currentPlayer.getCurrentPosition());
                tableView.addObserver(tableObs);

                tableView.notifyObservers();

                break;

            case "refreshPlayerPanel":

                System.out.println("aggiorno panel");

                break;

            case "rollDice":

                popUp.rollDice(diceValue, gameV);
                break;

        }

        return bool;
    }

}
