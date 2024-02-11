package app.card.impl;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.card.apii.CardFactory;
import app.card.apii.Unbuyable;
import app.card.utils.JsonReader;

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
        final String fileName = System.getProperty("user.dir") + sep + "src" + sep + "main" 
            + sep + "java" + sep + "app" + sep + "card" + sep + "utils" + sep + "cardList.json";
        final var jsonList = JsonReader.readJson(fileName);
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
        return new BuildableImpl(card, price, housePrice, fees);
    }

    /**
     * @param card is the Card base
     * @param price is the money price for buy property
     * @param fees is the city tax 
     * @return a Card buyable but with no housePrice
     */
    @Override
    public Buyable createStation(final Card card, final int price, final int fees) {
        return CardAdapter.buyableAdapter(createProperty(card, price, 0, fees));
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
