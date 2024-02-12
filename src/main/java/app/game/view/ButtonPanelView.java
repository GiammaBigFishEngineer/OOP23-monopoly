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

    private Map<BtnCodeEnum, Boolean> btnCodeList;
    private Map<BtnCodeEnum, JButton> btnList = new HashMap<>();

    private JButton rollDice;
    private JButton buyPropriety;
    private JButton sellPropriety;
    private JButton buyHouse;
    private JButton endTurn;

    public ButtonPanelView(List<Player> playersList, Observer obs) {

        logic = new GameControllerImpl(playersList);
        logic.registerObserver(obs);

        btnCodeList = logic.newTurn();

        changeButtonVisibility();

        rollDice = new JButton("Roll Dice");
        this.add(rollDice);
        btnList.put(BtnCodeEnum.rollDice, rollDice);

        rollDice.addActionListener(e -> {
            logic.rollDice(true);
        });

        buyPropriety = new JButton("Buy Propriety");
        this.add(buyPropriety);
        btnList.put(BtnCodeEnum.buyPropriety, buyPropriety);

        sellPropriety = new JButton("Sell Propriety");
        this.add(sellPropriety);
        btnList.put(BtnCodeEnum.sellPropriety, sellPropriety);

        buyHouse = new JButton("Buy House");
        this.add(buyHouse);
        btnList.put(BtnCodeEnum.buyHouse, buyHouse);

        endTurn = new JButton("End Turn");
        this.add(endTurn);
        btnList.put(BtnCodeEnum.endTurn, endTurn);

        endTurn.addActionListener(e -> {

            btnCodeList = logic.newTurn();

            changeButtonVisibility();

        });

    }

    public void changeButtonVisibility() {
        for (var entry : btnCodeList.entrySet()) {

            var code = entry.getKey();
            var bool = entry.getValue();

            btnList.get(code).setEnabled(bool);

        }
    }

}
