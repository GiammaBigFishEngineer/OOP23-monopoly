package app.card.view;

import javax.swing.BorderFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.card.api.Card;
import app.card.api.CardAdapter;
import app.card.api.CardFactory;
import app.card.impl.CardFactoryImpl;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * View of table in the game.
 */
public class TableView extends JPanel {

    private static final long serialVersionUID = 2298666777798069846L;
    private final CardFactory cardFactory = new CardFactoryImpl();
    private final Map<Card, JPanel> cells = new HashMap<>();
    private static final int FONTSIZE = 15;

    /**
     * @param size is the n card for a side in table
     * @throws IOException
     */
    public TableView(final int size) throws IOException {

        final var cardList = cardFactory.cardsInitializer();
        this.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JPanel jp = new JPanel();
                if (i != 0 && j != 0 && i != size - 1 && j != size - 1) {
                    jp.setVisible(false);
                }
                jp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jp.setLayout(new BorderLayout());

                final Card card = cardList.get(i + j);
                JLabel price = new JLabel();
                if (card.isBuildable()) {
                    final var buildable = CardAdapter.buildableAdapter(card);
                    price = new JLabel(String.valueOf(buildable.getPrice() + "$"));
                } else if (card.isBuyable()) {
                    final var buyable = CardAdapter.buyableAdapter(card);
                    price = new JLabel(String.valueOf(buyable.getPrice() + "$"));
                } else if (card.isUnbuyable()) {
                    price = new JLabel();
                }
                this.cells.put(card, jp);
                final JLabel name = new JLabel(card.getName());

                name.setFont(new Font("Verdana", 1, FONTSIZE));
                jp.add(name, BorderLayout.NORTH);
                jp.add(price, BorderLayout.SOUTH);
                this.add(jp);
            }
        }
        this.setVisible(true);
    }

    /**
    * @return a JPnale that's rappresented by a specific card
    */
    public Map<Card, JPanel> getCells() {
        return this.cells;
    }
}
