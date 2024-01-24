package app.card.view;

import javax.swing.BorderFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import app.card.apii.Card;
import app.card.apii.CardAdapter;
import app.card.impl.CardFactoryImpl;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * View of table in the game.
 */
public class TableView extends JPanel {

    private static final long serialVersionUID = 2298666777798069846L;
    private final List<Card> cardList = new CardFactoryImpl().cardsInitializer();
    private final Map<Card, JPanel> cells = new HashMap<>();
    private static final int FONTSIZE = 15;
    private static final int IMAGESIZE = 70;

    /**
     * @param size is the n card for a side in table
     * @throws IOException
     */
    public TableView(final int size) throws IOException {

        this.setLayout(new GridLayout(size, size));
        this.setBackground(Color.decode("#7FFFD4"));

        int index = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JPanel jp = new JPanel();
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
     * @return a map of Cards, every card is rappresented by specific JPanel
     */
    public Map<Card, JPanel> getCells() {
        return new HashMap<>(this.cells);
    }

    /**
     * @param index is the index of card in list
     * @return the JPanel that's rappresent a Card
     */
    private JPanel renderJPanel(final int index) {
        final var jp = new JPanel();
        final Card card = cardList.get(index);
        jp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jp.setLayout(new BorderLayout());
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

        name.setFont(new Font("Verdana", 1, FONTSIZE));
        jp.add(name, BorderLayout.NORTH);
        jp.add(price, BorderLayout.SOUTH);

        jp.setBackground(Color.decode("#7FFFD4"));
        return jp;
    }
}
