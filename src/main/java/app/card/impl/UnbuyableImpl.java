package app.card.impl;

import app.card.api.Card;
import app.card.api.StaticActionStrategy;
import app.card.api.StaticActionStrategy.TriggeredEvent;
import app.card.api.Unbuyable;
import app.player.api.Player;

/**
 * Implementation of Unbuyable.
 */
public final class UnbuyableImpl implements Unbuyable {

    private final Card card;
    private final StaticActionStrategy action;

    /**
     * protected for be used only in factory.
     * 
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
    public int getCardId() {
        return card.getCardId();
    }

    @Override
    public TriggeredEvent makeAction(final Player myPlayer) {
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
