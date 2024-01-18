package app.card.view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import app.card.impl.Unforseen;

public class UnforseenView extends JPanel{

    public UnforseenView(Unforseen card) {
        JOptionPane.showMessageDialog(null, card.getDescription());
    }
}
