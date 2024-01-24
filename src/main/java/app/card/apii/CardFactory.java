package app.card.apii;

import java.io.IOException;
import java.util.List;

/**
 * A box factory.
 */
public interface CardFactory {

    /**
     * 
     * @return list of all cards in table
     * @throws IOException 
     */
    List<Card> cardsInitializer() throws IOException;

    /**
     * @return a new card with id and name
     */
    Card createCard(int id, String name);

    /**
     * @param id id of box
     * @param name of box
     * @param price of box
     * @param housePrice of box
     * @param fees of box
     * @return a normal property where can build
     */
    Buildable createProperty(Card card, int price, int housePrice, int fees);

    /**
     * @param id id of box
     * @param name of box
     * @param price of box
     * @param fees of box
     * @return a station with particular transit fees
     */
    Buyable createStation(Card card, int price, int fees);

    /**
     * @param id id of box
     * @param name of box
     * @param func of method to be called
     * @param amount is the positive num for the action like giveMoney or movePlayer
     * @return the static card like Go and Prison
     */
    Unbuyable createStaticCard(Card card, String func, int amount);
}
