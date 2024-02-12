package app.card.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class who rappreset a Box in table.
 * Use the concept every player has a unique color.
 */
public class BoxPanelView extends JPanel {

    private JLabel name;
    private JLabel price;
    private int index;
    private JPanel players;
    private List<String> playerList;
    private static final int FONTSIZE = 15;
    private static final String PLAYER_SYMBOL = "◉";
    private static final long serialVersionUID = 1L;

    /**
     * Empty constructor for initializing normal JPanel. 
     */
    public BoxPanelView() { /* Empty constructor for initializing normal JPanel. */ }

    /**
     * Constructor for initializing normal JPanel but with more paramtetres. 
     * @param name
     * @param price
     * @param index
     */
    public BoxPanelView(final String name, final String price, final int index) {
        this.name = new JLabel(name);
        this.price = new JLabel(price);
        this.index = index;
        this.playerList = new ArrayList<>();
        this.players = new JPanel();
        this.name.setFont(new Font("Verdana", 1, FONTSIZE));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(new BorderLayout());
        this.add(this.name, BorderLayout.NORTH);
        this.add(this.price, BorderLayout.SOUTH);
        this.players.setBackground(Color.decode("#7FFFD4"));
        this.add(players, BorderLayout.EAST);
        this.setBackground(Color.decode("#7FFFD4"));
    }

    /**
     * Draw a player on box.
     * @param color
     */
    public void drawCircle(final String color) {
        final var player = new JLabel(PLAYER_SYMBOL);
        player.setForeground(Color.decode(color));
        this.players.add(player);
        this.playerList.add(color);
    }

    /**
     * Remove the drawed player on box.
     * @param color
     */
    public void removeCircle(final String color) {
        for (final Component component : this.players.getComponents()) {
            if (((JLabel) component).getForeground().equals(Color.decode(color))) {
                this.players.remove(component);
            }
        }
        this.players.revalidate();
        this.players.repaint();
        this.playerList.remove(color);
    }

    /**
     * @return price
     */
    public JLabel getPrice() {
        return new JLabel(this.price.getText());
    }

    /**
     * @return index of card
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * @return list of colors
     */
    public List<String> getPlayerList() {
        return List.of(this.playerList.stream().toArray(String[]::new));
    }

    /**
     * @return list of components JPanel players
     */
    public Component[] getPlayersComponents() {
        return Arrays.copyOf(this.players.getComponents(), this.players.getComponents().length);
    }

    /**
     * @return the name of object composed by name, player inside, index.
     */
    @Override
    public String toString() {
        return this.name.getText() + " " + this.getPlayerList() + " " + this.getIndex();
    }

}
