package app.card.api;

/**
 * An interface that represents a tile that can be bought and built upon.
 */
public interface Buildable extends Buyable {
    /**
	 * @param 
	 * @return price for build one house
	 */
    int getHousePrice();
}
