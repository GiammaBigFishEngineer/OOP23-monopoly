package app.player.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.player.apii.BankAccount;
import app.player.apii.Player;

/**
 * Player Implementation.
 */
public class PlayerImpl implements Player {

    private int currentPosition;
    private final String name;
    private final int id;
    private final BankAccount bankAccount;
    private boolean positionChanged;

    /**
     * Constructor.
     * 
     * @param name the name of the player
     * @param id the id of the player
     */
    public PlayerImpl(final String name, final int id) {
        this.name = name;
        this.id = id;
        this.currentPosition = 0;
        this.bankAccount = new BankAccountImpl();
    }

    /**
     * @return the current position
     */
    @Override
    public int getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * @return the name of player
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return the id of player
     */
    @Override
    public int getID() {
        return this.id;
    }

    /**
     * @return the player's bank account
     */
    @Override
    public BankAccount getBankAccount() {
        return new BankAccountImpl(this.bankAccount.getBalance());
    }

    /**
     * @param position the new position to be set
     */
    @Override
    public void setPosition(final int position) {
        this.currentPosition = position;
        positionChanged = true;
    }

    /**
     * @return {@code true} if there are changes in position, false otherwise
     */
    @Override
    public boolean hasPositionChanged() {
        return positionChanged;
    }

    /**
     * @param box the buyable box to buy
     */
    @Override
    public void buyBox(final Buyable box) {
        // to do
    }

    /**
     * @param box the buildable box on which to build the house
     */
    @Override
    public void buildHouse(final Buildable box) {
        // to do
    }

    /**
     * @return the number of stations owned
     */
    @Override
    public int getNumberStationOwned() {
        return 0; // to do
    }

    /**
     * @param box the buyable box to sold
     */
    @Override
    public void sellBuyable(final Buyable box) {
        // to do
    }

    /**
     * @param built the buildable box
     * @return the number of houses built
     */
    @Override
    public Optional<Integer> getHouseBuilt(final Buildable built) {
        return Optional.empty(); // to do
    }

    /**
     * @return the list of buyable boxes owned
     */
    @Override
    public List<Buyable> getBuyableOwned() {
        return new LinkedList<>(); // to do
    }

    /**
     * @return the list of buildable boxes owned
     */
    @Override
    public List<Buildable> getBuildableOwned() {
        return new LinkedList<>(); // to do
    }
}
