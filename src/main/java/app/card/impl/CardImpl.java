package app.card.impl;

import app.card.apii.Card;

/**
 * Implementation of Card.
 */
public final class CardImpl implements Card {

    private final int id;
    private final String name;

    /**
     * protected for be used only in factory.
     * @param id is the id in order on table
     * @param name is the name of box
     */
    protected CardImpl(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCardId() {
        return id;
    }

    @Override
    public boolean equals(final Object card) {
        if (card == this) {
            return true;
        }
        if (!(card instanceof Card)) {
            return false;
        }
        final Card c = (Card) card;
        return this.getCardId() == c.getCardId();
    }

    @Override
    public int hashCode() {
        return this.getCardId() * this.getName().hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
