package app.game.view;

import app.card.apii.Observer;
import app.card.view.TableView;
import app.card.view.UnforseenView;
import app.game.apii.GameObserver;
import app.player.apii.Player;
import app.player.view.BailView;
import app.player.view.PlayerPanelView;

import java.util.Map;
import java.util.HashMap;

public class GameObserverImpl implements GameObserver {

    GameView gameV;

    PlayerPanelView panelView;
    TableView tablePanel;

    GameMessage popUp;

    Map<Player, Integer> map;

    public GameObserverImpl(GameView gameV) {
        this.gameV = gameV;
        popUp = new GameMessage();
        map = new HashMap<>();

        panelView = gameV.getPlayerPanelView();
        tablePanel = gameV.getTableView();
    }

    @Override
    public boolean update(Integer diceValue, Player currentPlayer, String str) {
        Boolean bool = true;

        switch (str) {

            case "refreshPlayerPosition":

                if (map.containsKey(currentPlayer)) {

                    Observer<Player> removeObs = () -> tablePanel.removePlayer("#fff",
                            map.get(currentPlayer));

                    notifyObs(removeObs);

                    map.remove(currentPlayer);
                }

                Observer<Player> addObs = () -> tablePanel.redrawPlayer("#fff",
                        currentPlayer.getCurrentPosition());

                notifyObs(addObs);

                map.put(currentPlayer, currentPlayer.getCurrentPosition());

                break;

            case "refreshPlayerPanel":

                var card = tablePanel.getCardList().get(currentPlayer.getCurrentPosition());

                panelView.getLogic().setPlayer(currentPlayer, card);

                panelView.getLogic().setCurrentBox(card);

                break;

            case "rollDice":

                popUp.rollDice(diceValue, gameV);
                break;

            case "bail":
                BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(currentPlayer, gameV);

                break;

            case "Unforseen":

                UnforseenView unforseenView;

                break;

        }

        return bool;
    }

    public void notifyObs(Observer<Player> obs) {

        gameV.getTableView().addObserver(obs);

        obs.update();

        gameV.getTableView().deleteObserver(obs);
    }

}
