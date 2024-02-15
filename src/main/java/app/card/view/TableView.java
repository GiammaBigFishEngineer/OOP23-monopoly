package app.card.view;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.card.impl.CardFactoryImpl;
import app.card.utils.UseGetResource;
import app.player.apii.Player;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.io.ObjectInputStream;
/**
 * View of table in the game.
 * TableView extend the abstract class Observable who extende JPanel,
 * than TableView is a JPanel.
 */
public class TableView extends ObservableImpl<Player> {

    private static final long serialVersionUID = 3L;
    private transient List<Card> cardList;
    private transient Map<Card, BoxPanelView> cells = new HashMap<>();
    private static final int IMAGESIZE = 70;
    private final int size;

    /**
     * Method of serialization.
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.cells = new HashMap<>();
        this.cardList = new CardFactoryImpl().cardsInitializer();
    }

    /**
     * @param size is the number of cards for a side in table
     * @throws IOException
     */
    public TableView(final List<Card> list, final int size) throws IOException {
        this.size = size;
        this.cardList = list;
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
     * @return a list of all Cards in table.
     */
    public List<Card> getCardList() {
        return List.of(this.cardList.stream().toArray(Card[]::new));
    }

    /**
     * Return the image loaded from UseGetResource utility class.
     * @param fileName is the name of image.
     * @return the image loaded
     */
    private JLabel renderImage(final String fileName) {
        final Image icon = new ImageIcon(Objects.requireNonNull(
            UseGetResource.loadResource("view/image/" + fileName)))
            .getImage()
            .getScaledInstance(IMAGESIZE, IMAGESIZE, Image.SCALE_SMOOTH);
        final JLabel image = new JLabel();
        image.setIcon(new ImageIcon(icon));
        return image;
    }

    /**
     * @param index is the index of card in list
     * @return the JPanel that's rappresent a Card
     */
    private BoxPanelView renderJPanel(final int index) {
        final Card card = this.cardList.get(index);
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
        if (card.isUnbuyable()) {
            switch (card.getName()) {
                case "Imprevisti" -> jp.add(renderImage("unforseen.png"), BorderLayout.CENTER);
                case "Vai in Prigione" -> jp.add(renderImage("goprison.png"), BorderLayout.CENTER);
                case "Transito" -> jp.add(renderImage("prison.png"), BorderLayout.CENTER);
                case "GO" -> jp.add(renderImage("go.png"), BorderLayout.CENTER);
                case "Parcheggio" -> jp.add(renderImage("parking.png"), BorderLayout.CENTER);
                default -> throw new IllegalArgumentException("the name read isn't a static name card");
            }
        }
        this.cells.put(card, jp);
        return jp;
    }

}
