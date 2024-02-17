package app.game.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Comparator;

import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.card.apii.StaticActionStrategy.TriggeredEvent;
import app.card.impl.CardFactoryImpl;
import app.card.impl.Unforseen;
import app.game.apii.GameController;

import app.game.utils.Dice;

import app.player.apii.Player;
import app.player.impl.PlayerImpl;
import app.game.view.BtnCodeEnum;

public final class GameControllerImpl implements GameController {
    private final List<Player> players;
    private final List<Player> defeated;
    private Player currentPlayer;
    private int currentPlayerIndex = -1;

    private final List<String> playersName;

    private final List<Card> tableList;
    private final List<Card> cardsList;
    private Card currentCard;
    private int currentCardIndex;

    private boolean landedOnUnforseen;
    private String unforseenMessage;
    private boolean landedOnOwned;
    private String ownerName;

    private final Dice currentDice;
    private boolean doubleDice;

    private int totalResult;

    private final Map<BtnCodeEnum, Boolean> btnList;

    /**
     * 
     * @param names is a list that contains all the player names
     * @throws IOException
     */

    public GameControllerImpl(final List<String> names) throws IOException {

        this.tableList = new CardFactoryImpl().cardsInitializer();
        this.cardsList = tableList.stream()
                .sorted(Comparator.comparingInt(Card::getCardId))
                .collect(Collectors.toList());

        this.playersName = new ArrayList<>();
        playersName.addAll(names);

        this.players = new ArrayList<>();
        this.initializePlayer();

        currentDice = new Dice();

        defeated = new ArrayList<>();

        btnList = new HashMap<>();
        btnList.put(BtnCodeEnum.buyHouse, false);
        btnList.put(BtnCodeEnum.buyPropriety, false);
        btnList.put(BtnCodeEnum.endTurn, false);
        btnList.put(BtnCodeEnum.rollDice, false);
        btnList.put(BtnCodeEnum.sellPropriety, false);
    }

    /*
     * run method will be called at the start of the game and at the end of every
     * turn
     */

    @Override
    public void newTurn() {

        this.disableAllBtn();
        this.nextPlayer();
        this.landedOnUnforseen = false;
        this.landedOnOwned = false;

    }

    public void nextPlayer() {

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);

    }

    // prima di iniziare il turno se il player è in prigione può decidere se pagare
    // o provare ad uscire con un doppio dado

    @Override
    public void tryLuckyBail() {

        // se non paga la cauzione prova a tirare il dado

        rollDice(false);

        if (doubleDice) {

            // inizia il turno del player corrente

            currentPlayer.setInJail(false);
            enableSingleButton(BtnCodeEnum.rollDice);

        } else {

            // finisce qui il suo turno

            enableSingleButton(BtnCodeEnum.endTurn);

        }

    }

    /**
     * 
     */

    @Override
    public void rollDice(final Boolean b) {

        int res1;
        int res2;

        currentDice.roll();

        res1 = currentDice.getDie1Result();
        res2 = currentDice.getDie2Result();
        totalResult = currentDice.getDiceResult();

        if (res1 == res2) {
            doubleDice = true;
        } else {
            doubleDice = false;
        }

        if (b) {
            disableSingleButton(BtnCodeEnum.rollDice);
            startTurn();

        }

    }

    @Override

    public void startTurn() {

        final int position = currentPlayer.getCurrentPosition();

        final int finalPosition = position + totalResult;

        if (finalPosition >= cardsList.size()) {

            final int new_position = finalPosition - cardsList.size();
            currentPlayer.setPosition(new_position);
            Unforseen.U0.getCard().makeAction(currentPlayer);

        } else {
            currentPlayer.setPosition(finalPosition);
        }

        currentCardIndex = currentPlayer.getCurrentPosition();

        currentCard = cardsList.get(currentCardIndex);

        handleCard();

    }

    // serve per attivare/disattivare certi bottoni in base alla carta su cui si è
    // finiti

    @Override
    public void handleCard() {

        if (currentCard.isUnbuyable()) {

            handleUnbuyable();

        } else if (currentCard.isBuildable()) {

            handleBuildable();

        } else if (currentCard.isBuyable() && !currentCard.isBuildable()) {

            handleBuyable();

        }

        enableSingleButton(BtnCodeEnum.endTurn);

    }

    public void handleUnbuyable() {

        if (currentCard.getCardId() != 0 && currentCard.getCardId() != 12 && currentCard.getCardId() != 6) {

            final TriggeredEvent e = CardAdapter.unbuyableAdapter(currentCard).makeAction(currentPlayer);
            this.unforseenMessage = e.getMessage();
            this.landedOnUnforseen = true;

            var balance = currentPlayer.getBankAccount().getBalance();

            if (currentCardIndex != currentPlayer.getCurrentPosition()) {
                System.out.println("imprevisto movimento");
                currentCardIndex = currentPlayer.getCurrentPosition();

                currentCard = cardsList.get(currentCardIndex);

                handleCard();
            } else if (balance != currentPlayer.getBankAccount().getBalance()) {

                if (e.equals(TriggeredEvent.UNPERFORMED)) {
                    System.out.println("imprevisto pagamento non riuscito !!");
                    this.defeatPlayer();
                }

                System.out.println("imprevisto pagamento riuscito");

            }

        } else {
            System.out.println("parcheggio/transito/start");
        }

    }

    public void handleBuildable() {

        final Boolean owned = CardAdapter.buildableAdapter(currentCard).isOwned();

        if (owned) {

            final Player owner = CardAdapter.buildableAdapter(currentCard).getOwner();

            if (currentPlayer.equals(owner)) {

                enableSingleButton(BtnCodeEnum.buyHouse);
                enableSingleButton(BtnCodeEnum.sellPropriety);

            } else {
                payFees(owner);
            }

        } else {
            enableSingleButton(BtnCodeEnum.buyPropriety);

        }

    }

    public void handleBuyable() {
        final Boolean owned = CardAdapter.buyableAdapter(currentCard).isOwned();

        if (owned) {

            final Player owner = CardAdapter.buyableAdapter(currentCard).getOwner();

            if (!currentPlayer.equals(owner)) {
                payFees(owner);
            } else {
                enableSingleButton(BtnCodeEnum.sellPropriety);
            }

        } else {
            enableSingleButton(BtnCodeEnum.buyPropriety);

        }
    }

    @Override
    public boolean buyPropriety() {

        disableSingleButton(BtnCodeEnum.buyPropriety);

        if (!currentPlayer.buyBox(CardAdapter.buyableAdapter(currentCard))) {

            return false;
        }

        return true;

    }

    @Override
    public boolean buildHouse() {

        disableSingleButton(BtnCodeEnum.buyHouse);

        if (!currentPlayer.buildHouse(CardAdapter.buildableAdapter(currentCard))) {

            return false;
        }

        return true;

    }

    @Override
    public void sellPropriety() {

        disableSingleButton(BtnCodeEnum.sellPropriety);
        currentPlayer.sellBuyable(CardAdapter.buyableAdapter(currentCard));
    }

    @Override
    public void payFees(final Player owner) {
        landedOnOwned = true;
        ownerName = CardAdapter.buyableAdapter(currentCard).getOwner().getName();

        final int fees = CardAdapter.buyableAdapter(currentCard).getTransitFees();

        if (!currentPlayer.payPlayer(owner, fees)) {
            this.defeatPlayer();
        }

    }

    @Override
    public void defeatPlayer() {

        defeated.add(currentPlayer);
        players.remove(currentPlayer);

        newIndex();

    }

    @Override
    public boolean isOver() {
        return players.size() == 1;
    }

    public void endGame() {

    }

    public boolean isCurrentPlayerDefeated() {
        return defeated.contains(currentPlayer);
    }

    public void newIndex() {
        this.currentPlayerIndex--;
    }

    @Override
    public void enableSingleButton(final BtnCodeEnum code) {

        btnList.put(code, true);

    }

    @Override
    public void disableSingleButton(final BtnCodeEnum code) {

        btnList.put(code, false);

    }

    public void disableAllBtn() {

        for (final var entry : btnList.entrySet()) {
            entry.setValue(false);
        }

    }

    @Override
    public void initializePlayer() {
        var id = 1;
        for (final String name : playersName) {

            this.players.add(new PlayerImpl(name, id, cardsList, 500));
            id++;

        }
    }

    @Override
    public Map<BtnCodeEnum, Boolean> getBtnStatus() {
        return btnList;
    }

    @Override
    public Player getCurrentPlayer() {

        return currentPlayer;
    }

    @Override
    public Dice getDice() {
        return this.currentDice;
    }

    @Override
    public Card getCurrentCard() {
        return currentCard;
    }

    @Override
    public List<Card> getTableList() {
        List<Card> copyList = new ArrayList<>();
        copyList.addAll(tableList);
        return copyList;
    }

    @Override
    public List<Card> getCardList() {
        List<Card> copyList = new ArrayList<>();
        copyList.addAll(cardsList);
        return copyList;
    }

    @Override
    public List<Player> getPlayerList() {
        List<Player> copyList = new ArrayList<>();
        copyList.addAll(players);
        return copyList;
    }

    @Override
    public List<Player> getDefeatedList() {
        List<Player> copyList = new ArrayList<>();
        copyList.addAll(defeated);
        return copyList;
    }

    public Boolean isCurrentPlayerInJail() {
        return currentPlayer.isInJail();
    }

    @Override
    public void setDiceValue(final int value) {
        this.totalResult = value;
    }

    public boolean isCurrentPlayerOnUnforseen() {
        return this.landedOnUnforseen;
    }

    public String getUnforseenMessage() {
        return this.unforseenMessage;
    }

    public boolean isCurrentPlayerOnOwnedPropriety() {
        return this.landedOnOwned;
    }

    public String getOwner() {
        return this.ownerName;
    }

}
