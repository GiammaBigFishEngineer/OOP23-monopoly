package app.card.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;
import app.card.apii.CardFactory;
import app.card.apii.StaticActionStrategy;
import app.card.apii.Unbuyable;
import app.player.apii.Player;

/**
 * Implementation of CardFactory, every method create a subinstance of Card.
 */
public final class CardFactoryImpl implements CardFactory {

    /**
     * @return the list of all cards in table
     */
    @Override
    public List<Card> cardsInitializer() throws IOException {
        return List.of();
    }

    /**
     * @param id is the id of card
     * @param name is the name of card
     * @return the card as object
     */
    @Override
    public Card createCard(final int id, final String name) {
        return new CardImpl(id, name);
    }

    /**
     * @param card is the Card base
     * @param price is the money price for buy property
     * @param housePrice is the money for build an house on property
     * @param fees is the city tax 
     * @return a Card buyable, with more property like price, housePrice
     */
    @Override
    public Buildable createProperty(final Card card, final int price, final int housePrice, final int fees) {
        return new Buildable() {

            private final Buyable buyable = new BuyableImpl(card, price, fees);

            @Override
            public int getPrice() {
                return this.buyable.getPrice();
            }

            @Override
            public boolean isOwned() {
                return this.buyable.isOwned();
            }

            @Override
            public boolean isOwnedByPlayer(final Player player) {
                return this.buyable.isOwnedByPlayer(player);
            }

            @Override
            public Player getOwner() {
                return this.buyable.getOwner();
            }

            @Override
            public int getTransitFees() {
                return this.buyable.getTransitFees();
            }

            @Override
            public void setOwner(final Player player) {
                this.buyable.setOwner(player);
            }

            @Override
            public String getName() {
                return this.buyable.getName();
            }

            @Override
            public int getCardId() {
                return this.buyable.getCardId();
            }

            @Override
            public int getHousePrice() {
                return housePrice;
            }

        };
    }

    /**
     * @param card is the Card base
     * @param price is the money price for buy property
     * @param fees is the city tax 
     * @return a Card buyable but with no housePrice
     */
    @Override
    public Buyable createStation(final Card card, final int price, final int fees) {
        return new BuyableImpl(card, price, fees);
    }

    /**
     * @param card is the Card base
     * @param action is the name of action to call
     * @param amount is the amount passed to function called
     * @return a Card unbuyable with no price but a with optional static action to call on players
     */
    @Override
    public Unbuyable createStaticCard(final Card card, final String action, final int amount) {
        final StaticActionStrategy staticAction;
        switch (action) {
            case "giveMoneyPlayer" -> {
                staticAction = (player) -> {
                    if (player == null) {
                        throw new IllegalArgumentException("Player passed can't be null");
                    }
                    player.receivePayment(amount);
                    return Optional.empty();
                };
            }
            case "payPlayer" -> {
                staticAction = (player) -> {
                    if (player == null) {
                        throw new IllegalArgumentException("Player passed can't be null");
                    }
                    if (player.getBankAccount().isPaymentAllowed(amount)) {
                        player.getBankAccount().payPlayer(null, amount);
                    }
                    return Optional.empty();
                };
            }
            case "movePlayer" -> {
                staticAction = (player) -> {
                    if (player == null) {
                        throw new IllegalArgumentException("Player passed can't be null");
                    }
                    player.setPosition(amount);
                    return Optional.empty();
                };
            }
            case "unforseen" -> {
                staticAction = (player) -> {
                    final int unforseenSize = 14;
                    final var extraction = ThreadLocalRandom.current().nextInt(unforseenSize);
                    final var myUnforseen = Unforseen.valueOf((String) "U" + extraction);
                    myUnforseen.getCard().makeAction(player);
                    return Optional.of(myUnforseen);
                };
            }
            case "" -> { 
                staticAction = (player) -> Optional.empty();
            }
            default -> throw new IllegalArgumentException("The action read isn't an action of the game: " + action);
        }
        return new UnbuyableImpl(card, staticAction);
    }

}
