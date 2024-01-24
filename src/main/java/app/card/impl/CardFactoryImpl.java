package app.card.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.io.File;
import java.io.IOException;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;
import app.card.apii.CardFactory;
import app.card.apii.StaticAction;
import app.card.apii.Unbuyable;
import app.card.utils.JsonReader;
import app.player.apii.NotEnoughMoneyException;
import app.player.apii.Player;

/**
 * Implementation of CardFactory, every method create a subinstance of Card.
 */
public class CardFactoryImpl implements CardFactory {

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
            final var card = createCard(Integer.valueOf(i.getString("id")), i.getString("name"));
            switch (type) {
                case "static":
                    allCards.add(createStaticCard(
                        card,
                        i.getString("action"),
                        Integer.valueOf(i.getString("actionAmount"))
                    ));
                    break;
                case "property":
                    allCards.add(createProperty(
                        card,
                        Integer.valueOf(i.getString("price")),
                        Integer.valueOf(i.getString("housePrice")),
                        Integer.valueOf(i.getString("fees"))
                    ));
                    break;
                case "station":
                    allCards.add(createStation(
                        card,
                        Integer.valueOf(i.getString("price")),
                        Integer.valueOf(i.getString("fees"))
                    ));
                    break;

                default:
                    break;
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
        return new Card() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public int getId() {
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
                return this.getId() == c.getId();
            }

            @Override
            public int hashCode() {
                return this.getId() * this.getName().hashCode();
            }

            @Override
            public String toString() {
                return this.getName();
            }
            
        };
    }

    /**
     * @param id is the id in table of cards
     * @param name is the name of property
     * @param price is the money price for buy property
     * @param housePrice is the money for build an house on property
     * @param fees is the city tax 
     * @return a Card buyable, with more property like price, housePrice
     */
    @Override
    public Buildable createProperty(final Card card, final int price, final int housePrice, final int fees) {
        return new Buildable() {

            private Optional<Player> owner = Optional.empty();

            @Override
            public int getPrice() {
                return price;
            }

            @Override
            public boolean isOwned() {
                return this.owner.isPresent();
            }

            @Override
            public boolean isOwnedByPlayer(final Player player) {
                return isOwned() && owner.get().equals(player);
            }

            @Override
            public Player getOwner() {
                return this.owner.get();
            }

            @Override
            public int getTransitFees() {
                return fees;
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
            public int getHousePrice() {
                return price;
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
                return card.equals(card);
            }

            @Override
            public int hashCode() {
                return card.hashCode();
            }

        };
    }

    /**
     * @param id is the id in table of cards
     * @param name is the name of property
     * @param price is the money price for buy property
     * @param fees is the city tax 
     * @return a Card buyable but with no housePrice
     */
    @Override
    public Buyable createStation(final Card card, final int price, final int fees) {
        return createProperty(card, price, 0, fees);
    }

    /**
     * @param id is the id in table of cards
     * @param name is the name of property
     * @param func is the name of function to call
     * @param amount is the argument passed to function called by reflection
     * @return a Card unbuyable with no price but a with optional static action to call on players
     */
    @Override
    public Unbuyable createStaticCard(final Card card, final String action, final int myAmount) {
        return new Unbuyable() {

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
                final Optional<Unforseen> unforseen = Optional.empty();
                switch (action) {
                    case "giveMoneyPlayer":
                        staticAction = (player,amount) -> {
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
                        staticAction = (player, amount) -> Optional.empty();
                        break;
                }
                staticAction.myAction(myPlayer, myAmount);
                return unforseen;
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

        };
    }

}
