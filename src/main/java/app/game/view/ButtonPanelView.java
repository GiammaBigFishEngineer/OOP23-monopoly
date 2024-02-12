package app.game.view;

import javax.swing.*;

import app.game.apii.GameController;
import app.game.apii.Observer;
import app.game.controller.GameControllerImpl;
import app.player.apii.Player;

import java.util.*;

/**
 * ButtonsView.
 */
public class ButtonPanelView extends JPanel {

    private GameController logic;

    private Map<JButton, Integer> btnList = new HashMap<>();

    private JButton rollDice;
    private JButton buyPropriety;
    private JButton sellPropriety;
    private JButton buyHouse;
    private JButton endTurn;

    public ButtonPanelView(List<Player> playersList, Observer obs) {

        logic = new GameControllerImpl(null);
        logic.registerObserver(obs);

        rollDice = new JButton("Roll Dice");
        this.add(rollDice);
        btnList.put(rollDice, BtnCodeEnum.rollDice.code);

        buyPropriety = new JButton("Buy Propriety");
        this.add(buyPropriety);
        btnList.put(buyPropriety, BtnCodeEnum.buyPropriety.code);

        sellPropriety = new JButton("Sell Propriety");
        this.add(sellPropriety);
        btnList.put(sellPropriety, BtnCodeEnum.sellPropriety.code);

        buyHouse = new JButton("Buy House");
        this.add(buyHouse);
        btnList.put(buyHouse, BtnCodeEnum.buyHouse.code);

        endTurn = new JButton("End Turn");
        this.add(endTurn);
        btnList.put(endTurn, BtnCodeEnum.endTurn.code);

    }

    protected Map<JButton, Integer> getBtnList() {
        return this.btnList;
    }

}
