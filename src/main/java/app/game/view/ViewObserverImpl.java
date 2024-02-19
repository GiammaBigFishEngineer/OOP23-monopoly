package app.game.view;

import app.card.view.UnforseenView;
import app.game.apii.ViewObserver;
import app.game.utils.ObserverCodeEnum;
import app.player.apii.Player;
import app.player.view.BailView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Optional;

/**
 * This class is the implementation of ViewObserver.
 */

public final class ViewObserverImpl implements ViewObserver {

    private final GameView gameV;

    private final GameMessage popUp;

    /**
     * 
     * @param gameV is the general view of the game on which the changes will be
     *              applied
     */

    @SuppressFBWarnings(value = {
            "EI_EXPOSE_REP2" }, justification = "Ho bisogno del frame di gioco e non di una copia difensiva."
                    + "Infatti devo chiamare dei metodi che andranno a modificare i pannelli presenti sul frame stesso.")

    public ViewObserverImpl(final GameView gameV) {
        this.gameV = gameV;
        popUp = new GameMessage();

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean update(final Optional<Object> obj, final ObserverCodeEnum code) {

        Boolean bool = true;

        switch (code) {

            case REFRESH_PLAYER_POSITION:

                gameV.updateTableView(obj);

                break;

            case REFRESH_PLAYER_PANEL:

                gameV.updatePlayerPanelView(obj);

                break;

            case BAIL:

                final Player crntPlayer = (Player) obj.get();

                final BailView bailMessage = new BailView();
                bool = bailMessage.showMenuBail(crntPlayer);

                break;

            case ROLL_DICE:

                gameV.updateDiceView(obj);

                break;

            case NOT_DOUBLE_DICE:

                popUp.remainPrison();
                break;

            case DOUBLE_DICE:

                popUp.exitPrison();
                break;

            case NO_BUY:

                popUp.noBuyPropriety();
                break;

            case NO_BUILD:

                popUp.noBuilHouse();
                break;

            case ELIMINATE:

                final String eliminatedName = (String) obj.get();

                popUp.eliminatePlayer(eliminatedName);
                break;

            case WIN:

                final String winnerName = (String) obj.get();

                popUp.winnerPlayer(winnerName);
                break;

            case SAVE:

                popUp.saveGame();

                break;

            case UNBUYABLE_ACTION:

                final String message = (String) obj.get();
                new UnforseenView(message);

                break;

            case FEES:
                final String owner = (String) obj.get();

                popUp.fees(owner);

                break;

            case QUIT:

                bool = popUp.quitGame();

                break;

            default:
                bool = false;
                break;

        }

        return bool;
    }

}
