package app.card.view;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.card.impl.CardFactoryImpl;
import app.player.apii.Player;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * View of table in the game.
 * TableView extend the abstract class Observable who extende JPanel,
 * than TableView is a JPanel.
 */
public class TableView extends ObservableImpl<Player> {

    private static final long serialVersionUID = 2298666777798069846L;
    private final List<Card> cardList = new CardFactoryImpl().cardsInitializer();
    private final Map<Card, BoxPanelView> cells = new HashMap<>();
    private static final int IMAGESIZE = 70;
    private final int size;

    /**
     * @param size is the number of cards for a side in table
     * @throws IOException
     */
    public TableView(final int size) throws IOException {
        this.size = size;
        this.setLayout(new GridLayout(size, size));
        this.setBackground(Color.decode("#7FFFD4"));

        int index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                BoxPanelView jp = new BoxPanelView();
                if (i != 0 && j != 0 && i != size - 1 && j != size - 1) {
                    jp.setVisible(false);
                } else {
                    jp = renderJPanel(index);
                    index++;
                }
                this.add(jp);
            }
        }
        this.setVisible(true);
    }

    /**
     * Redraw a player on the position passed.
     * this method takes advantage of the fact that each player has a unique color.
     * @param color
     * @param position
     */
    public void redrawPlayer(final String color, final int position) {
        if (position > (this.size * 4) - 1 || position < 0) {
            throw new IllegalArgumentException("Position passed is not a position in table size");
        }
        for (final var i: this.cells.keySet()) {
            if (i.getId() == position) {
                this.getCells().get(i).drawCircle(color);
            }
        }
    }

     /**
     * Remove a player on the position passed.
     * this method takes advantage of the fact that each player has a unique color.
     * @param color
     * @param position
     */
    public void removePlayer(final String color, final int position) {
        if (position > (this.size * 4) - 1 || position < 0) {
            throw new IllegalArgumentException("Position passed is not a position in table size");
        }
        for (final var i: this.cells.keySet()) {
            if (i.getId() == position) {
                this.getCells().get(i).removeCircle(color);
            }
        }
    }

    /**
     * @return a map of Cards, every card is rappresented by specific JPanel
     */
    public Map<Card, BoxPanelView> getCells() {
        return new HashMap<>(this.cells);
    }

    /**
     * @param index is the index of card in list
     * @return the JPanel that's rappresent a Card
     */
    private BoxPanelView renderJPanel(final int index) {
        final Card card = cardList.get(index);
        String price = "";
        if (card.isBuildable()) {
            final var buildable = CardAdapter.buildableAdapter(card);
            price = String.valueOf(buildable.getPrice() + "$");
        } else if (card.isBuyable()) {
            final var buyable = CardAdapter.buyableAdapter(card);
            price = String.valueOf(buyable.getPrice() + "$");
        } else if (card.isUnbuyable()) {
            price = "";
        }
        final BoxPanelView jp = new BoxPanelView(card.getName(), price, card.getId());

        final String sep = File.separator;
        final String fileName = System.getProperty("user.dir") + sep + "src" + sep + "main" 
            + sep + "java" + sep + "app" + sep + "card" + sep + "view" + sep + "resources" + sep;
        if (card.isUnbuyable()) {
            switch (card.getName()) {
                case "Imprevisti":
                    Image icon = new ImageIcon(fileName + "unforseen.png").getImage()
                        .getScaledInstance(IMAGESIZE, IMAGESIZE, Image.SCALE_SMOOTH);
                    JLabel image = new JLabel();
                    image.setIcon(new ImageIcon(icon));
                    jp.add(image, BorderLayout.CENTER);
                break;
                case "Vai in Prigione":
                    icon = new ImageIcon(fileName + "goprison.png").getImage()
                        .getScaledInstance(IMAGESIZE, IMAGESIZE, Image.SCALE_SMOOTH);
                    image = new JLabel();
                    image.setIcon(new ImageIcon(icon));
                    jp.add(image, BorderLayout.CENTER);
                break;
                case "Transito":
                    icon = new ImageIcon(fileName + "prison.png").getImage()
                        .getScaledInstance(IMAGESIZE, IMAGESIZE, Image.SCALE_SMOOTH);
                    image = new JLabel();
                    image.setIcon(new ImageIcon(icon));
                    jp.add(image, BorderLayout.CENTER);
                break;
                case "GO":
                    icon = new ImageIcon(fileName + "go.png").getImage()
                        .getScaledInstance(IMAGESIZE, IMAGESIZE, Image.SCALE_SMOOTH);
                    image = new JLabel();
                    image.setIcon(new ImageIcon(icon));
                    jp.add(image, BorderLayout.CENTER);
                break;
                case "Parcheggio":
                    icon = new ImageIcon(fileName + "parking.png").getImage()
                        .getScaledInstance(IMAGESIZE, IMAGESIZE, Image.SCALE_SMOOTH);
                    image = new JLabel();
                    image.setIcon(new ImageIcon(icon));
                    jp.add(image, BorderLayout.CENTER);
                break;
                default:
                break;
            }
        }
        this.cells.put(card, jp);
        return jp;
    }

}
