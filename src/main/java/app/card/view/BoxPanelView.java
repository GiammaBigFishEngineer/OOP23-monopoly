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

/**
 * Class who rappreset a Box in table.
 * Use the concept every player has a unique color.
 */
public class BoxPanelView extends JPanel{

    private JLabel name;
    private JLabel price;
    private int index;
    private JPanel players;
    private List<String> playerList;
    private static final int FONTSIZE = 15;
    private static final String PLAYER_SYMBOL = "◉";
    private static final long serialVersionUID = 2298666777798069846L;

public BoxPanelView() { /* Empty constructor for initializing normal JPanel. */ }

    public BoxPanelView(final JLabel name, final JLabel price, final int index) {
        this.name = name;
        this.price = price;
        this.index = index;
        this.playerList = new ArrayList<>();
        this.players = new JPanel();
        this.name.setFont(new Font("Verdana", 1, FONTSIZE));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setLayout(new BorderLayout());
        this.add(name, BorderLayout.NORTH);
        this.add(price, BorderLayout.SOUTH);
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
        for (Component component : this.players.getComponents()) {
            if (((JLabel) component).getBackground().equals(Color.BLACK)) {
                this.players.remove(component);
            }
        }
        this.players.revalidate();
        this.players.repaint();
        this.playerList.remove(color);
    }

    /**
     * @return name
     */
    public JLabel getNameLabel() {
        return this.name;
    }

    /**
     * @return price
     */
    public JLabel getPrice() {
        return this.price;
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
        return this.playerList;
    }

    /**
     * @return list of components JPanel players
     */
    public Component[] getPlayersComponents() {
        return this.players.getComponents();
    }

    @Override
    public String toString() {
        return this.getNameLabel().getText() + " " + this.getPlayerList() + " " + this.getIndex();
    }

}
