package app.card.impl;

import java.util.Optional;

import app.card.apii.Buyable;
import app.card.apii.Card;
import app.player.apii.Player;
/**
 * The implementation of Buildable.
 */
public final class BuyableImpl implements Buyable {

    private final Card card;
    private Optional<Player> owner = Optional.empty();
    private final int price;
    private final int fees;

    /**
     * protected for be used only in factory.
     * @param card
     * @param price
     * @param fees
     */
    protected BuyableImpl(final Card card,
     final int price, final int fees) {
        this.card = card;
        this.price = price;
        this.fees = fees;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public boolean isOwned() {
        return this.owner.isPresent();
    }

    @Override
    public boolean isOwnedByPlayer(final Player player) {
        return isOwned() && this.owner.get().equals(player);
    }

    @Override
    public Player getOwner() {
        return this.owner.get();
    }

    @Override
    public int getTransitFees() {
        return this.fees;
    }

    @Override
    public String getName() {
        return this.card.getName();
    }

    @Override
    public int getCardId() {
        return this.card.getCardId();
    }

    @Override
    public void setOwner(final Player player) {
        this.owner = Optional.of(player);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(final Object card) {
        return this.card.equals(card);
    }

    @Override
    public int hashCode() {
        return this.card.hashCode();
    }
}
