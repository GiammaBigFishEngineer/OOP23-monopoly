package app.game.view;

import app.card.view.UnforseenView;
import app.game.apii.GameObserver;
import app.player.apii.Player;
import app.player.view.BailView;
import java.util.Optional;

/**
 * 
 */

public final class GameObserverImpl implements GameObserver {

    private final GameView gameV;

    private final GameMessage popUp;

    /**
     * 
     * @param gameV
     */

    public GameObserverImpl(final GameView gameV) {
        this.gameV = gameV;
        popUp = new GameMessage();

    }

    @Override
    public boolean update(final Optional<Object> obj, final String str) {

        Boolean bool = true;

        switch (str) {

            case "refreshPlayerPosition":

                gameV.updateTableView(obj);

                break;

            case "refreshPlayerPanel":

                gameV.updatePlayerPanelView(obj);

                break;

            case "bail":

                final Player crntPlayer = (Player) obj.get();

                final BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(crntPlayer);

                break;

            case "RollDice":

                gameV.updateDiceView(obj);

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

                final String eliminatedName = (String) obj.get();

                popUp.eliminatePlayer(eliminatedName);
                break;

            case "Win":

                final String winnerName = (String) obj.get();

                popUp.winnerPlayer(winnerName);
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

}
