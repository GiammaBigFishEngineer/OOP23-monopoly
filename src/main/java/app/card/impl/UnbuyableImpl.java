package app.card.impl;

import java.util.Optional;
import java.util.Random;

import app.card.apii.Card;
import app.card.apii.StaticAction;
import app.card.apii.Unbuyable;
import app.player.apii.NotEnoughMoneyException;
import app.player.apii.Player;

/**
 * Implementation of Unbuyable.
 */
public final class UnbuyableImpl implements Unbuyable {

    private final Card card;
    private final String action;
    private final int myAmount;

    /**
     * protected for be used only in factory.
     * @param card
     * @param action
     * @param myAmount
     */
    protected UnbuyableImpl(final Card card, final String action, final int myAmount) {
        this.card = card;
        this.action = action;
        this.myAmount = myAmount;
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
        final StaticAction staticAction;
        switch (action) {
            case "giveMoneyPlayer":
                staticAction = (player, amount) -> {
                    if (player != null) {
                        player.getBankAccount().receivePayment(amount);
                    }
                    return Optional.empty();
                };
                break;
            case "payPlayer":
                staticAction = (player, amount) -> {
                    if (player != null) {
                        try {
                            player.getBankAccount().payPlayer(null, amount);
                        } catch (NotEnoughMoneyException e) {
                            /* inserire log.error() */
                            e.printStackTrace();
                        }
                    }
                    return Optional.empty();
                };
                break;
            case "movePlayer":
                staticAction = (player, amount) -> {
                    if (player != null) {
                        player.setPosition(amount);
                    }
                    return Optional.empty();
                };
            break;
            case "unforseen":
                staticAction = (player, amount) -> {
                    final int unforseenSize = 14;
                    final var extraction = new Random().nextInt(unforseenSize);
                    final var myUnforseen = Unforseen.valueOf((String) "U" + extraction);
                    myUnforseen.getCard().makeAction(player);
                    return Optional.of(myUnforseen);
                };
            break;
            default:
                throw new IllegalArgumentException("The action read isn't an action of the game");
        }
        return staticAction.myAction(myPlayer, myAmount);
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
