package app.game.view;

import app.card.apii.Observer;
import app.card.view.TableView;
import app.game.apii.GameObserver;
import app.player.apii.Player;
import app.player.view.BailView;
import app.player.view.PlayerPanelView;

import java.util.Map;
import java.util.HashMap;

public class GameObserverImpl implements GameObserver {

    GameView gameV;
    GameMessage popUp;

    Map<Player, Integer> map;

    public GameObserverImpl(GameView gameV) {
        this.gameV = gameV;
        popUp = new GameMessage();
        map = new HashMap<>();
    }

    @Override
    public boolean update(Integer diceValue, Player currentPlayer, String str) {
        Boolean bool = true;

        switch (str) {

            case "refreshPlayerPosition":

                if (map.containsKey(currentPlayer)) {

                    Observer<Player> removeObs = () -> gameV.getTableView().removePlayer("#fff",
                            map.get(currentPlayer));

                    notifyObs(removeObs);

                    map.remove(currentPlayer);
                }

                Observer<Player> addObs = () -> gameV.getTableView().redrawPlayer("#fff",
                        currentPlayer.getCurrentPosition());

                notifyObs(addObs);

                map.put(currentPlayer, currentPlayer.getCurrentPosition());

                break;

            case "refreshPlayerPanel":

                PlayerPanelView panelView = gameV.getPlayerPanelView();
                TableView tablePanel = gameV.getTableView();
                tablePanel.getCardList().get(currentPlayer.getCurrentPosition());

                panelView.getLogic().setPlayer(currentPlayer,
                        tablePanel.getCardList().get(currentPlayer.getCurrentPosition()));

                panelView.getLogic().setCurrentBox(tablePanel.getCardList().get(currentPlayer.getCurrentPosition()));

                break;

            case "rollDice":

                popUp.rollDice(diceValue, gameV);
                break;

            case "bail":
                BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(currentPlayer, gameV);

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
