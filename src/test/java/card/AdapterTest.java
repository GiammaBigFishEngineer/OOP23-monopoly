package card;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.card.api.CardAdapter;
import app.card.api.CardFactory;
import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;
import app.card.api.Buildable;
import app.card.api.Buyable;
import app.card.api.Card;

import java.io.IOException;
import java.util.*;

public class AdapterTest {

    private CardFactory factory;
    private List<Card> list;

	@BeforeEach
	public void init() throws IOException {
		this.factory = new CardFactoryImpl();
        this.list = factory.cardsInitializer();
	}

    @Test
    public void testThrowsBuildableAdapter(){
        var card = list.get(0); /* passo il la prima cella del via */
        assertThrows(IllegalArgumentException.class,() -> CardAdapter.buildableAdapter(card));
    }

    @Test
    public void testThrowsBuyableAdapter(){
        var card = list.get(0); /* passo il la prima cella del via */
        assertThrows(IllegalArgumentException.class,() -> CardAdapter.buyableAdapter(card));
    }

    @Test
    public void testThrowsUnbuyableAdapter(){
        var card = list.get(1); /* passo la prima proprietà */
        assertThrows(IllegalArgumentException.class,() -> CardAdapter.unbuyableAdapter(card));
    }

    @Test
    public void testIteratorAdapter(){
        for(var i: list){
            if(i.isBuildable()) {
                var adapted = CardAdapter.buildableAdapter(i);
                assertTrue(adapted instanceof Buildable);
            } else if(i.isBuyable()) {
                var adapted = CardAdapter.buyableAdapter(i);
                assertTrue(adapted instanceof Buyable);
            } else if(i.isUnbuyable()){
                var adapted = CardAdapter.unbuyableAdapter(i);
                assertTrue(adapted instanceof Unbuyable);
            }
        }
    }
}
