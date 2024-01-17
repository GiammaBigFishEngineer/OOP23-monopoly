package app.card.api;

/**
 * An interface that represents each individual box.
 */
public interface Card {
     /**
	 * @param 
	 * @return name of card
	 */
    String getName();

     /**
	 * @param 
	 * @return id of card on table
	 */
    int getId();

	default boolean isBuildable(){
		return this instanceof Buildable;
	}

	default boolean isBuyable(){
		return this instanceof Buyable;
	}

	default boolean isUnbuyable(){
		return this instanceof Unbuyable;
	}
}
