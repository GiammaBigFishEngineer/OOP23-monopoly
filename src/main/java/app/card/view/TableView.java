package app.card.view;

import javax.swing.BorderFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.card.api.Buyable;
import app.card.api.Card;
import app.card.api.CardAdapter;
import app.card.api.CardFactory;
import app.card.api.Unbuyable;
import app.card.impl.CardFactoryImpl;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TableView extends JPanel{

    private final CardFactory cardFactory = new CardFactoryImpl();
    private final Map<Card,JPanel> cells = new HashMap<>();

    public TableView(final int size) throws IOException {

        final var cardList = cardFactory.cardsInitializer();
       

        this.setLayout(new GridLayout(size,size));
        

        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JPanel jp = new JPanel();
                if(i != 0 && j != 0 && i != size-1 && j != size-1){
                    jp.setVisible(false);
                }
                jp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jp.setLayout(new BorderLayout());

                Card card = cardList.get(i+j);
                JLabel price = new JLabel();
                if(card.isBuildable()) {
                    var buildable = CardAdapter.buildableAdapter(card);
                    price = new JLabel(String.valueOf(buildable.getPrice()+"$"));
                } else if(card.isBuyable()) {
                    var buyable = (Buyable)CardAdapter.buyableAdapter(card);
                    price = new JLabel(String.valueOf(buyable.getPrice()+"$"));
                } else if(card.isUnbuyable()) {
                    card = (Unbuyable)CardAdapter.unbuyableAdapter(card);
                    price = new JLabel();
                }
                this.cells.put(card, jp);

                final JLabel name = new JLabel(card.getName());

                name.setFont(new Font("Verdana",1,15));
                jp.add(name,BorderLayout.NORTH);
                jp.add(price,BorderLayout.SOUTH);
                this.add(jp);
            }
        }
        
        System.out.println(this.cells.values());
        this.setVisible(true);
    }

    public Map<Card,JPanel> getCells() {
        return this.cells;
    }
}
