package app.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;
import app.card.impl.CardFactoryImpl;
import app.player.apii.Player;
import app.player.impl.PlayerImpl;

/**
 * Simple test for {@link app.player.impl.PlayerImpl} class. 
 */
public class TestPlayerImpl { 

    private static final int DEFAULT_ID = 1;
    private static final int DEFAULT_FEES = 10;
    private static final int DEFAULT_PRICE = 50;
    private static final int DEFAULT_PAYMENT = 100;

    private Player player;
    private List<Card> cards;
    private List<Buyable> buyables;
    private List<Buildable> buildables;

    private final CardFactoryImpl cardFactory = new CardFactoryImpl();
    private final Card firstCard = cardFactory.createCard(DEFAULT_ID, "ExampleCard1");
    private final Card secondCard = cardFactory.createCard(DEFAULT_ID * 2, "ExampleCard2");
    private final Card thirdCard = cardFactory.createCard(DEFAULT_ID * 3, "ExampleCard3");
    private final Card fourthCard = cardFactory.createCard(DEFAULT_ID * 4, "ExampleCard4");

    private final Buyable buyable = cardFactory.createStation(firstCard, DEFAULT_PRICE, DEFAULT_FEES);
    private final Buildable buildable = cardFactory.createProperty(secondCard, DEFAULT_PRICE, DEFAULT_PAYMENT, DEFAULT_FEES);
    private final Buyable eastStation = cardFactory.createStation(thirdCard, DEFAULT_PRICE, DEFAULT_FEES);
    private final Buyable westStation = cardFactory.createStation(fourthCard, DEFAULT_PRICE, DEFAULT_FEES);
    /**
     * Configuration step: this is performed before each test. 
     */
    @BeforeEach 
    public void setUp() {
        this.cards = new LinkedList<>();
        this.buyables = new LinkedList<>(); 
        this.buildables = new LinkedList<>();
        this.cards.add(buyable);
        this.cards.add(buildable);
        this.cards.add(eastStation);
        this.cards.add(westStation);
        this.player = new PlayerImpl("Player", DEFAULT_ID, cards, DEFAULT_PAYMENT * 1000);
    }

    /**
     * Check the method buyBox().
     */
    @Test
    void testBuyBox() {
        this.buyables.add(buyable);
        this.player.buyBox(buyable);
        List<Buyable> buyableOwned = this.player.getBuyableOwned();
        Assertions.assertEquals(buyables.size(), buyableOwned.size());
        Assertions.assertTrue(this.player.getMap().get(buyable).isPresent());
    }

    /**
     * Check the method buildHouse().
     */
    @Test 
    void testBuildHouse() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.player.buildHouse(buildable);
        });
        assertEquals("You're trying to build a house on a box you don't own", exception.getMessage()); 
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isBlank());
    }

    /**
     * Check the method getNumberStationOwned().
     */
    @Test 
    void testNumberStationOwned() {
        this.player.buyBox(eastStation);
        this.player.buyBox(westStation);
        Assertions.assertEquals(2, this.player.getNumberStationOwned());
    }

    /**
     * Check the method getBuyableOwned().
     */
    @Test
    void testBuyableOwned() {
        this.player.buyBox(buyable);
        this.buyables.add(buyable);
        Assertions.assertEquals(buyables, this.player.getBuyableOwned());
        Assertions.assertNotNull(buyables);
    }

    /**
     * Check the method getBuildableOwned().
     */
    @Test
    void testBuildableOwned() {
        this.player.buyBox(buildable);
        this.player.buildHouse(buildable);
        this.buildables.add(buildable); 
        Assertions.assertEquals(buildables, this.player.getBuildableOwned());
    }
    /**
     * Check the method sellBuyable().
     */
    @Test 
    void testSellBuyable() {
        this.player.buyBox(buyable);
        this.player.sellBuyable(buyable);
        List<Buyable> buyableOwned = this.player.getBuyableOwned();
        Assertions.assertTrue(buyableOwned.isEmpty());
    }

    /**
     * Check the method getHouseBuilt().
     */
    @Test
    void testHouseBuilt() {
        this.player.buyBox(buildable);
        this.player.buildHouse(buildable);
        Optional<Integer> numberHouseBuilt = this.player.getHouseBuilt(buildable); 
        Assertions.assertTrue(numberHouseBuilt.isPresent());
        Assertions.assertEquals(Optional.of(1), numberHouseBuilt);
    }
}
