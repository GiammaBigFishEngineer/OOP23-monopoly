package app.game.view;

import app.card.apii.Observer;
import app.card.view.TableView;
import app.card.view.UnforseenView;
import app.game.apii.GameObserver;
import app.player.apii.Player;
import app.player.view.BailView;
import app.player.view.PlayerPanelView;

import java.util.Map;
import java.util.Optional;
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
    public boolean update(Integer diceValue, Optional<Player> currentPlayer, String str) {
        Boolean bool = true;

        switch (str) {

            case "refreshPlayerPosition":

                if (map.containsKey(currentPlayer.get())) {

                    Observer<Player> removeObs = () -> tablePanel.removePlayer("#fff",
                            map.get(currentPlayer.get()));

                    notifyObs(removeObs);

                    map.remove(currentPlayer.get());
                }

                Observer<Player> addObs = () -> tablePanel.redrawPlayer("#fff",
                        currentPlayer.get().getCurrentPosition());

                notifyObs(addObs);

                map.put(currentPlayer.get(), currentPlayer.get().getCurrentPosition());

                break;

            case "refreshPlayerPanel":

                var card = tablePanel.getCardList().get(currentPlayer.get().getCurrentPosition());

                panelView.getLogic().setPlayer(currentPlayer.get(), card);

                panelView.getLogic().setCurrentBox(card);

                break;

            case "bail":
                BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(currentPlayer.get(), gameV);

                break;

            case "RollDice":

                popUp.rollDice(gameV, diceValue);
                break;

            case "NotDoubleDice":

                popUp.remainPrison(gameV);
                break;

            case "DoubleDice":

                popUp.exitPrison(gameV);
                break;

            case "NoBuy":

                popUp.noBuyPropriety(gameV);
                break;

            case "NoBuild":

                popUp.noBuilHouse(gameV);
                break;

            case "Eliminate":

                popUp.eliminatePlayer(gameV, currentPlayer.get().getName());
                break;

            case "Win":

                popUp.winnerPlayer(gameV, currentPlayer.get().getName());
                break;

            case "Save":

                popUp.saveGame(gameV);
                ;
                break;

            case "Unforseen":

                UnforseenView unforseenView;

                break;

        }

        return bool;
    }

    public void notifyObs(Observer<Player> obs) {

        tablePanel.addObserver(obs);

        obs.update();

        tablePanel.deleteObserver(obs);
    }

}
