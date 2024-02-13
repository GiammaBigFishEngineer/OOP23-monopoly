package app.card.impl;

import java.util.Optional;

import app.card.apii.Card;
import app.card.apii.StaticActionStrategy;
import app.card.apii.Unbuyable;
import app.player.apii.Player;

/**
 * Implementation of Unbuyable.
 */
public final class UnbuyableImpl implements Unbuyable {

    private final Card card;
    private final StaticActionStrategy action;

    /**
     * protected for be used only in factory.
     * @param card
     * @param staticAction
     */
    protected UnbuyableImpl(final Card card, final StaticActionStrategy staticAction) {
        this.card = card;
        this.action = staticAction;
    }

    @Override
    public String getName() {
        return card.getName();
    }

    @Override
    public int getId() {
        return card.getId();
    }

    @Override
    public Optional<Unforseen> makeAction(final Player myPlayer) {
        return this.action.myAction(myPlayer);
    }

    @Override
    public String toString() {
        return card.getName();
    }

    @Override
    public boolean equals(final Object newCard) {
        return card.equals(newCard);
    }

    @Override
    public int hashCode() {
        return card.hashCode();
    }
}
