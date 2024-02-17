package app.game.view;

import app.card.apii.Card;
import app.card.apii.Observer;
import app.card.view.TableView;
import app.card.view.UnforseenView;
import app.game.apii.GameObserver;
import app.game.utils.Dice;
import app.player.apii.Player;
import app.player.view.BailView;
import app.player.view.PlayerPanelView;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;

public class GameObserverImpl implements GameObserver {

    GameView gameV;

    GameMessage popUp;

    Map<Player, Integer> map;

    public GameObserverImpl(final GameView gameV) {
        this.gameV = gameV;
        popUp = new GameMessage();
        map = new HashMap<>();

    }

    @Override
    public boolean update(final Optional<Object> obj, final String str) {
        final PlayerPanelView panelView = gameV.getPlayerPanelView();
        final TableView tablePanel = gameV.getTableView();
        Boolean bool = true;

        switch (str) {

            case "refreshPlayerPosition":

                final Player currentPlayer = (Player) obj.get();

                if (map.containsKey(currentPlayer)) {

                    final Observer<Player> removeObs = () -> tablePanel.removePlayer(currentPlayer.getColor(),
                            map.get(currentPlayer));

                    useObs(removeObs);

                    map.remove(currentPlayer);
                }

                final Observer<Player> addObs = () -> tablePanel.redrawPlayer(currentPlayer.getColor(),
                        currentPlayer.getCurrentPosition());

                useObs(addObs);

                map.put(currentPlayer, currentPlayer.getCurrentPosition());

                break;

            case "refreshPlayerPanel":

                final Player player = (Player) obj.get();

                final var card = tablePanel.getCardList()
                        .stream()
                        .sorted(Comparator.comparingInt(Card::getCardId))
                        .collect(Collectors.toList())
                        .get(player.getCurrentPosition());

                panelView.setPlayer(player, card);

                panelView.setCurrentBox(card);

                break;

            case "bail":

                final Player crntPlayer = (Player) obj.get();

                final BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(crntPlayer, gameV);

                break;

            case "RollDice":

                final Dice dice = (Dice) obj.get();
                gameV.getDiceView().updateView(dice);

                // call gio method
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

                final String EliminatedName = (String) obj.get();

                popUp.eliminatePlayer(EliminatedName);
                break;

            case "Win":

                final String WinnerName = (String) obj.get();

                popUp.winnerPlayer(WinnerName);
                break;

            case "Save":

                popUp.saveGame();

                break;

            case "UnbuyableAction":

                final String message = (String) obj.get();
                new UnforseenView(message);

                break;

            case "Fees":
                final String owner = (String) obj.get();

                popUp.fees(owner);

            default:
                bool = false;
                break;

        }

        return bool;
    }

    public void useObs(final Observer<Player> obs) {

        gameV.getTableView().addObserver(obs);

        obs.update();

        gameV.getTableView().deleteObserver(obs);
    }

}
