package app.player.impl;

import java.util.Optional;

import app.card.apii.Buildable;
import app.card.apii.Card;
import app.card.impl.CardImpl;
import app.player.apii.Player;
import app.player.apii.PlayerPanelLogic;
import app.player.view.PlayerPanelView;

/**
 * Class which implements PlayerPanelLogic.
 */
public final class PlayerPanelLogicImpl implements PlayerPanelLogic {

    private Player currentPlayer;
    private Card currentBox = new CardImpl(0, "prova");
    private PlayerPanelView panel;

    /**
     * Constructor.
     * 
     * @param panel
     */
    public PlayerPanelLogicImpl(final PlayerPanelView panel) {
        this.panel = panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayer(final Player player, final Card currentBox) {
        this.currentPlayer = player;
        setCurrentBox(currentBox);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrentBox(final Card currentBox) {
        this.currentBox = currentBox;
        refresh();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        panel.getPlayerName().setText(this.currentPlayer.getName());
        panel.getPlayerID().setText(String.valueOf(this.currentPlayer.getID()));
        panel.getPlayerBoxes().setText(String.valueOf(this.currentPlayer.getBuyableOwned().size()));
        if (currentBox.isBuildable()) {
            Optional<Integer> housesBuilt = this.currentPlayer.getHouseBuilt((Buildable) currentBox);
            if (housesBuilt.isPresent()) {
                panel.getPlayerHouses().setText(String.valueOf(housesBuilt.get()));
            } else {
                panel.getPlayerHouses().setText("0");
            }
        } else {
            panel.getPlayerHouses().setText("0");
        }
        panel.getPlayerMoney().setText(String.valueOf(this.currentPlayer.getBankAccount().getBalance()));
        panel.getPlayerStations().setText(String.valueOf(this.currentPlayer.getNumberStationOwned()));
    }
}
