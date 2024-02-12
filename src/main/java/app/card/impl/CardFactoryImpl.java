package app.card.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;
import app.card.apii.CardFactory;
import app.card.apii.Unbuyable;
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
        final String sep = File.separator;
        final URL url = Objects.requireNonNull(UseGetResource.loadResource("list" + sep + "cardList.json"));
        final var jsonList = JsonReader.readJson(url);
        jsonList.forEach(i -> {
            final var type = i.getString("tipology");
            final var id = Integer.valueOf(i.getString("id"));
            final var card = createCard(id, i.getString("name"));
            switch (type) {
                case "static" -> allCards.add(createStaticCard(
                    card,
                    i.getString("action"),
                    Integer.parseInt(i.getString("actionAmount"))
                ));
                case "property" -> allCards.add(createProperty(
                    card,
                    Integer.parseInt(i.getString("price")),
                    Integer.parseInt(i.getString("housePrice")),
                    Integer.parseInt(i.getString("fees"))
                ));
                case "station" -> allCards.add(createStation(
                    card,
                    Integer.parseInt(i.getString("price")),
                    Integer.parseInt(i.getString("fees"))
                ));
                default -> throw new IllegalArgumentException("the type read isn't a type card of the game");
            }
        });
        return allCards;
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
            public int getId() {
                return this.buyable.getId();
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
     * @param myAmount is the argument passed to function called
     * @return a Card unbuyable with no price but a with optional static action to call on players
     */
    @Override
    public Unbuyable createStaticCard(final Card card, final String action, final int myAmount) {
        return new UnbuyableImpl(card, action, myAmount);
    }

}
