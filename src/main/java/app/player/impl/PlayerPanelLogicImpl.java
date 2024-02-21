package app.player.impl;

import java.util.Optional;
import java.util.List;
import java.util.LinkedList;

import app.card.api.Buyable;
import app.card.api.Card;
import app.card.api.CardAdapter;
import app.player.api.Player;
import app.player.api.PlayerPanelLogic;
import app.player.view.PlayerPanelView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Class which implements PlayerPanelLogic.
 */
public final class PlayerPanelLogicImpl implements PlayerPanelLogic {

    private Player currentPlayer;
    private Card currentBox;
    private final PlayerPanelView panel;

    /**
     * Constructor.
     * 
     * @param currentPlayer
     * @param currentBox
     * @param panel
     */
    @SuppressFBWarnings(value = {
            "EI_EXPOSE_REP2" }, justification = "Voglio che gli oggetti currentPlayer e currentBox siano modificabili."
                    + "Infatti, ho bisogno dell'oggetto proprio della classe e non di una copia.")
    public PlayerPanelLogicImpl(final Player currentPlayer, final Card currentBox, final PlayerPanelView panel) {
        this.currentPlayer = currentPlayer;
        this.currentBox = currentBox;
        this.panel = panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(value = {
            "EI_EXPOSE_REP2" }, justification = "Voglio che l'oggetto Player sia modificabile da chi chiama questo metodo,"
                    + "perché e' finalizzato ad aggiornare i valori relativi al giocatore stesso."
                    + "Infatti, se ritornassi una copia del Player, "
                    + "il metodo refresh() non andrebbe ad aggiornare effettivamente i valori, ma la copia."
                    + "Per cui, non succederebbe quanto voluto.")
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
        panel.setPlayerNameText(this.currentPlayer.getName());
        panel.setPlayerIDText(String.valueOf(this.currentPlayer.getID()));
        final List<Buyable> buyableOwned = this.currentPlayer.getBuyableOwned();
        final List<String> buyableNames = new LinkedList<>();
        for (final Buyable buyable : buyableOwned) {
            buyableNames.add(buyable.getName());
        }
        if (buyableNames.isEmpty()) {
            panel.setPlayerBoxesText("Non possiedi alcuna proprieta'");
        } else {
            panel.setPlayerBoxesText(String.join(", ", buyableNames));
        }
        if (currentBox.isBuildable()) {
            final Optional<Integer> housesBuilt = this.currentPlayer
                    .getHouseBuilt(CardAdapter.buildableAdapter(currentBox));
            if (housesBuilt.isPresent()) {
                panel.setPlayerHousesText(String.valueOf(housesBuilt.get()));
            } else {
                panel.setPlayerHousesText("Non possiedi questa casella");
            }
        } else {
            panel.setPlayerHousesText("Su questa casella non si possono costruire case");
        }
        panel.setPlayerMoneyText(String.valueOf(this.currentPlayer.getBankAccount().getBalance()));
        panel.setPlayerStationsText(String.valueOf(this.currentPlayer.getNumberStationOwned()));
    }
}
