package app.card.impl;

import java.util.Optional;

import app.card.apii.Buildable;
import app.card.apii.Card;
import app.player.apii.Player;
/**
 * The implementation of Buildable.
 */
public final class BuildableImpl implements Buildable {

    private final Card card;
    private transient Optional<Player> owner = Optional.empty();
    private final int price;
    private final int housePrice;
    private final int fees;
    private static final long serialVersionUID = 2298666777798069846L;

    /**
     * protected for be used only in factory.
     * @param card
     * @param price
     * @param housePrice
     * @param fees
     */
    protected BuildableImpl(final Card card,
     final int price, final int housePrice, final int fees) {
        this.card = card;
        this.price = price;
        this.housePrice = housePrice;
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
    public int getId() {
        return this.card.getId();
    }

    @Override
    public int getHousePrice() {
        return this.housePrice;
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
