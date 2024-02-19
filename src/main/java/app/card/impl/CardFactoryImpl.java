package app.card.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;
import java.net.URL;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;
import app.card.apii.CardFactory;
import app.card.apii.StaticActionStrategy;
import app.card.apii.Unbuyable;
import app.card.apii.StaticActionStrategy.TriggeredEvent;
import app.card.utils.JsonReader;
import app.card.utils.UseGetResource;
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
        final var allCards = new ArrayList<Card>();
        final URL url = Objects.requireNonNull(UseGetResource.loadResource("list/cardList.json"));
        final var jsonList = JsonReader.readJson(url);
        jsonList.forEach(i -> {
            final var type = i.getString("tipology");
            final var id = Integer.valueOf(i.getString("id"));
            final var card = createCard(id, i.getString("name"));
            switch (type) {
                case "static" -> allCards.add(createStaticCard(
                        card,
                        i.getString("action"),
                        Integer.parseInt(i.getString("actionAmount"))));
                case "property" -> allCards.add(createProperty(
                        card,
                        Integer.parseInt(i.getString("price")),
                        Integer.parseInt(i.getString("housePrice")),
                        Integer.parseInt(i.getString("fees"))));
                case "station" -> allCards.add(createStation(
                        card,
                        Integer.parseInt(i.getString("price")),
                        Integer.parseInt(i.getString("fees"))));
                default -> throw new IllegalArgumentException("the type read isn't a type card of the game");
            }
        });
        return allCards;
    }

    /**
     * @param id   is the id of card
     * @param name is the name of card
     * @return the card as object
     */
    @Override
    public Card createCard(final int id, final String name) {
        return new CardImpl(id, name);
    }

    /**
     * @param card       is the Card base
     * @param price      is the money price for buy property
     * @param housePrice is the money for build an house on property
     * @param fees       is the city tax
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

            @Override
            public void clearOwner() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'clearOwner'");
            }

        };
    }

    /**
     * @param card  is the Card base
     * @param price is the money price for buy property
     * @param fees  is the city tax
     * @return a Card buyable but with no housePrice
     */
    @Override
    public Buyable createStation(final Card card, final int price, final int fees) {
        return new BuyableImpl(card, price, fees);
    }

    /**
     * @param card   is the Card base
     * @param action is the name of action to call
     * @param amount is the amount passed to function called
     * @return a Card unbuyable with no price but a with optional static action to
     *         call on players
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
                    return TriggeredEvent.PERFORMED.update(player + " ha ricevuto " + amount);
                };
            }
            case "payPlayer" -> {
                staticAction = (player) -> {
                    if (player == null) {
                        throw new IllegalArgumentException("Player passed can't be null");
                    }
                    if (!player.getBankAccount().isPaymentAllowed(amount)) {
                        return TriggeredEvent.UNPERFORMED.update("Il giocatore non ha abbasta denaro");
                    }
                    player.payPlayer(null, amount);
                    return TriggeredEvent.PERFORMED.update(player + " ha pagato " + amount);
                };
            }
            case "movePlayer" -> {
                staticAction = (player) -> {
                    if (player == null) {
                        throw new IllegalArgumentException("Player passed can't be null");
                    }
                    player.setPosition(amount);
                    if (player.getCurrentPosition() == 6) {
                        player.setInJail(true);
                    }
                    return TriggeredEvent.PERFORMED.update(player + " si sposta");
                };
            }
            case "unforseen" -> {
                staticAction = (player) -> {
                    final int unforseenSize = 14;
                    final var extraction = ThreadLocalRandom.current().nextInt(unforseenSize);
                    final var myUnforseen = Unforseen.valueOf((String) "U" + extraction);
                    myUnforseen.getCard().makeAction(player);
                    return TriggeredEvent.PERFORMED.update(myUnforseen.getDescription());
                };
            }
            case "" -> {
                staticAction = (player) -> TriggeredEvent.UNPERFORMED;
            }
            default -> throw new IllegalArgumentException("The action read isn't an action of the game: " + action);
        }
        TriggeredEvent.clearMessages();
        return new UnbuyableImpl(card, staticAction);
    }

}
