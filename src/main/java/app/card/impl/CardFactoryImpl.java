package app.card.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.io.File;
import java.io.IOException;

import app.card.api.Buildable;
import app.card.api.Buyable;
import app.card.api.Card;
import app.card.api.CardFactory;
import app.card.api.Unbuyable;
import app.card.utils.JsonReader;
import app.card.utils.StaticActions;
import app.player.api.Player;

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
            switch (type) {
                case "static":
                    allCards.add(createStaticCard(
                        Integer.valueOf(i.getString("id")),
                        i.getString("name"),
                        i.getString("action"),
                        Integer.valueOf(i.getString("actionAmount"))
                    ));
                    break;
                case "property":
                    allCards.add(createProperty(
                        Integer.valueOf(i.getString("id")),
                        i.getString("name"),
                        Integer.valueOf(i.getString("price")),
                        Integer.valueOf(i.getString("housePrice")),
                        Integer.valueOf(i.getString("fees"))
                    ));
                    break;
                case "station":
                    allCards.add(createStation(
                        Integer.valueOf(i.getString("id")),
                        i.getString("name"),
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
    * @return a Card buyable, with more property like price, housePrice
    */
    @Override
    public Buildable createProperty(final int id, final String name, final int price, final int housePrice, final int fees) {
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
                return name;
            }

            @Override
            public int getId() {
                return id;
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

        };
    }

    /**
    * @return a Card buyable but with no housePrice
    */
    @Override
    public Buyable createStation(final int id, final String name, final int price, final int fees) {
        return createProperty(id, name, price, 0, fees);
    }

    /**
    * @return a Card unbuyable with no price but a with optional static action to call on players
    */
    @Override
    public Unbuyable createStaticCard(final int id, final String name, final String func, final int amount) {
        return new Unbuyable() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public int getId() {
                return id;
            }

            @Override
            public String getAction() {
                return func;
            }

            @Override
            public Optional<Unforseen> makeAction(final Player player) {
                try {
                    final String methodName = func;
                    final Class<?> clazz = StaticActions.class;
                    if ("unforseen".equals(func)) {
                        final Method method = clazz.getMethod(methodName, Player.class);
                        final Unforseen unforseen = (Unforseen) method.invoke(Unforseen.class, player);
                        return Optional.of(unforseen);
                    } else {
                        final Method method = clazz.getMethod(methodName, Player.class, int.class);
                        method.invoke(null, player, amount);
                        return Optional.empty();
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    return Optional.empty();
                }
            }

            @Override
            public String toString() {
                return this.getName();
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

        };
    }

}
