package app.player.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import app.card.apii.Buildable;
import app.card.apii.Buyable;
import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.player.apii.BankAccount;
import app.player.apii.Player;

/**
 * Class which implements a Player.
 */
public final class PlayerImpl implements Player {
    /**
     * Maximum number of houses a player can build on a box.
     */
    private static final int MAX_NUMBER_HOUSES = 3;

    /**
     * Map which associates each Card box with an Optional that indicates
     * if that box is already owned by the current player
     * and the number of houses built on that box.
     */
    private Map<Card, Optional<Integer>> map;
    private int currentPosition;
    private final String name;
    private final int id;
    private final BankAccount account;
    private boolean isInJail;
    private boolean positionChanged;
    private final String color;

    /**
     * @param name
     * @param id
     * @param cards
     * @param initialAmount
     *                      In order to avoid errors with the method map.get(), in
     *                      case the map isn't initialized,
     *                      this constructor sets the values of the map with
     *                      Optional.empty().
     */
    public PlayerImpl(final String name, final int id, final List<Card> cards, final int initialAmount) {
        this.name = name;
        this.id = id;
        this.map = new HashMap<>();
        this.currentPosition = 0;
        this.isInJail = false;
        for (final Card box : cards) {
            this.map.put(box, Optional.empty());
        }
        this.account = new BankAccountImpl(initialAmount);

        switch (this.getID()) {
            case 1:
                color = "#E52B50";
                break;

            case 2:
                color = "#884DA7";
                break;

            case 3:
                color = "#FF6600";
                break;

            case 4:
                color = "#E52B50";
                break;

            default:
                color = "#E52B50";
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final int position) {
        this.currentPosition = position;
        this.positionChanged = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPositionChanged() {
        return this.positionChanged;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getID() {
        return this.id;
    }

    /**
     * Getter for player's color.
     * 
     * @return color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Card, Optional<Integer>> getMap() {
        return new HashMap<>(this.map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInJail() {
        return this.isInJail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInJail(final boolean isInJail) {
        this.isInJail = isInJail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BankAccount getBankAccount() {
        return new BankAccountImpl(this.account.getBalance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Buyable> getBuyableOwned() {
        return map.keySet().stream()
                .filter(box -> map.get(box).isPresent() && box.isBuyable())
                .map(CardAdapter::buyableAdapter)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Buildable> getBuildableOwned() {
        return map.keySet().stream()
                .filter(box -> map.get(box).isPresent() && box.isBuildable())
                .map(CardAdapter::buildableAdapter)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getHouseBuilt(final Buildable built) {
        // chi usa questo metodo, si dovrà preoccupare di verificare che il player
        // possieda la casella
        return map.get(built);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberStationOwned() {
        return (int) map.keySet().stream()
                .filter(box -> map.get(box).isPresent() && box.isBuyable() && !box.isBuildable())
                .count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buyBox(final Buyable box) {
        if (map.get(box).isPresent()) {
            return false;
        }
        if (account.payPlayer(null, box.getPrice())) {
            map.put(box, Optional.of(0)); // 0 perché possiedo la casella con 0 case costruite
            box.setOwner(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean buildHouse(final Buildable box) {
        if (map.get(box).isEmpty()) {
            throw new IllegalArgumentException("You're trying to build a house on a box you don't own");
        }
        if (map.get(box).get() >= MAX_NUMBER_HOUSES) {
            return false;
        }
        if (this.account.payPlayer(null, box.getHousePrice())) {
            // Aggiungo alla casella acquistata il numero di case incrementato di 1
            map.put(box, Optional.of(map.get(box).get() + 1));
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param box current box
     * @return value of the current box, obtained by the sum of box's price
     *         with the number of houses built by the player on that box
     *         This method is private, because it must not be used by anyone else.
     *         It throws an IllegalArgumentException if the player doesn't own the
     *         box:
     *         in fact, the map associates a box with an Optional<Integer>.
     *         If it's empty, it means the player doesn't own the box.
     */
    private int boxValue(final Buyable box) {
        if (!(box instanceof Buildable)) {
            return box.getPrice();
        }
        final Optional<Integer> numberHouses = getHouseBuilt(CardAdapter.buildableAdapter(box));
        if (numberHouses.isEmpty()) {
            throw new IllegalArgumentException("Player doesn't own the current box.");
        }
        return CardAdapter.buildableAdapter(box).getPrice()
                + numberHouses.get() * CardAdapter.buildableAdapter(box).getHousePrice();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sellBuyable(final Buyable box) {
        this.account.receivePayment(boxValue(box));
        // lo eseguo dopo perché altrimenti rimuovo prima di contare quante case ho
        // sopra
        map.put(box, Optional.empty());
    }

    /**
     * As the method getBankAccount() returns a defensive copy of the bankAccount of
     * the player,
     * only the player can modify this object.
     * Who uses the method getBankAccount() would modify the copy of the account:
     * as a result, I have created the following methods which effectively modify
     * the player's account.
     * 
     * @param amount
     */
    @Override
    public void receivePayment(final int amount) {
        this.account.receivePayment(amount);
    }

    /**
     * @param player
     * @param amount
     * @return boolean
     */
    @Override
    public boolean payPlayer(final Player player, final int amount) {
        return this.account.payPlayer(player, amount);
    }

    public void setMap(final Map<Card, Optional<Integer>> map) {
        this.map = new HashMap<>(map);
    }
}