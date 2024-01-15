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
     * @param id id of box
     * @param name of box
     * @param price of box
     * @param housePrice of box
     * @param fees of box
	 * @return a normal property where can build
	 */
    Buildable createProperty(int id, String name, int price, 
        int housePrice, int fees);
    
    /**
     * @param id id of box
     * @param name of box
     * @param price of box
     * @param fees of box
	 * @return a station with particular transit fees
	 */
    Buyable createStation(int id, String name, int price, int fees);

    /**
     * @param id id of box
     * @param name of box
     * @param func of method to be called
	 * @return the static card like Go and Prison
	 */
    Unbuyable createStaticCard(int id, String name, String func);
}
