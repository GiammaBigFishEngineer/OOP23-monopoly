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
    public boolean update(Optional<Object> obj, String str) {
        Boolean bool = true;

        switch (str) {

            case "refreshPlayerPosition":

                Player currentPlayer = (Player) obj.get();

                if (map.containsKey(currentPlayer)) {

                    Observer<Player> removeObs = () -> tablePanel.removePlayer("#fff",
                            map.get(currentPlayer));

                    useObs(removeObs);

                    map.remove(currentPlayer);
                }

                Observer<Player> addObs = () -> tablePanel.redrawPlayer("#fff",
                        currentPlayer.getCurrentPosition());

                useObs(addObs);

                map.put(currentPlayer, currentPlayer.getCurrentPosition());

                break;

            case "refreshPlayerPanel":

                Player player = (Player) obj.get();

                var card = tablePanel.getCardList().get(player.getCurrentPosition());

                panelView.setPlayer(player, card);

                panelView.setCurrentBox(card);

                break;

            case "bail":

                Player crntPlayer = (Player) obj.get();

                BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(crntPlayer, gameV);

                break;

            case "RollDice":

                int diceValue = (int) obj.get();

                popUp.rollDice(diceValue);
                break;

            case "NotDoubleDice":

                popUp.remainPrison();
                break;

            case "DoubleDice":

                popUp.exitPrison();
                break;

            case "NoBuy":

                popUp.noBuyPropriety();
                break;

            case "NoBuild":

                popUp.noBuilHouse();
                break;

            case "Eliminate":

                String EliminatedName = (String) obj.get();

                popUp.eliminatePlayer(EliminatedName);
                break;

            case "Win":

                String WinnerName = (String) obj.get();

                popUp.winnerPlayer(WinnerName);
                break;

            case "Save":

                popUp.saveGame();

                break;

            case "UnbuyableAction":

                String message = (String) obj.get();
                new UnforseenView(message);

                break;

        }

        return bool;
    }

    public void useObs(Observer<Player> obs) {

        tablePanel.addObserver(obs);

        obs.update();

        tablePanel.deleteObserver(obs);
    }

}
