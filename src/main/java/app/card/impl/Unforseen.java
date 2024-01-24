package app.card.impl;

import app.card.apii.CardFactory;
import app.card.apii.Unbuyable;

/**
 * every Ui is the i card unforseen to extract.
 */
public enum Unforseen {

    /**
     * give 100 money to player.
     */
    U0(TempFactory.FACTORY.createStaticCard(0, "U0", TempFactory.GIVEMONEY, 100),
        "Maturano le cedole delle vostre cartelle di rendita ritirate 100"),

    /**
     * move player to 15th position.
     */
    U1(TempFactory.FACTORY.createStaticCard(1, "U1", TempFactory.MOVE, 15),
        "Andate in via accademia,"),

    /**
     * pay 50 money to player.
     */
    U2(TempFactory.FACTORY.createStaticCard(2, "U2", TempFactory.PAYMONEY, 50),
        "Matrimonio familiare, spendi 50"),

    /**
     * pay 100 money.
     */
    U3(TempFactory.FACTORY.createStaticCard(3, "U3", TempFactory.PAYMONEY, 100),
        "Decidi di versare 100 euro in beneficenza"),

    /**
     * give 50 money to player.
     */
    U4(TempFactory.FACTORY.createStaticCard(4, "U4", TempFactory.GIVEMONEY, 50),
        "Vinci una partita di black jack, incassi 50"),

    /**
     * move player to 0th position.
     */
    U5(TempFactory.FACTORY.createStaticCard(5, "U5", TempFactory.MOVE, 0),
        "Andate al via con tanti auguri!"),

    /**
     * move player to 23th position.
     */
    U6(TempFactory.FACTORY.createStaticCard(6, "U6", TempFactory.MOVE, 23),
        "Andate fino al Grand Hotel!"),

    /**
     * give 20 money to player.
     */
    U7(TempFactory.FACTORY.createStaticCard(7, "U7", TempFactory.GIVEMONEY, 20),
        "Hai vinto un premio di bellezza incassi solo 20 (perchè non sei così tanto bello/a)"),

    /**
     * pay 150 money.
     */
    U8(TempFactory.FACTORY.createStaticCard(8, "U8", TempFactory.PAYMONEY, 150),
        "Scade l'assicurazione, paghi 150"),

    /**
     * move player to 20th position.
     */
    U9(TempFactory.FACTORY.createStaticCard(9, "U9", TempFactory.MOVE, 20),
        "Lo shopping compulsivo ti prende, vai alle Befane shopping center"),

    /**
     * pay 50 money.
     */
    U10(TempFactory.FACTORY.createStaticCard(10, "U10", TempFactory.PAYMONEY, 50),
        "Ti sei morso la lingua e devi farti visitare, spendi 50"),

    /**
     * move player to 50th position.
     */
    U11(TempFactory.FACTORY.createStaticCard(11, "U11", TempFactory.MOVE, 0),
        "Andate al via con tanti auguri"),

    /**
     * pay 100 money.
     */
    U12(TempFactory.FACTORY.createStaticCard(12, "U12", TempFactory.PAYMONEY, 100),
        "Paghi una multa per eccesso di stupidità, sono 100 euro"),

    /**
     * give 100 money to player.
     */
    U13(TempFactory.FACTORY.createStaticCard(13, "U13", TempFactory.GIVEMONEY, 100),
        "Erediti 100 euro da un lontano zio (beato te)"),

    /**
     * give 50 money to player.
     */
    U14(TempFactory.FACTORY.createStaticCard(14, "U14", TempFactory.GIVEMONEY, 25),
        "Vendi il vaso con le ceneri della tua tris Nonna, incassi 25");

    private Unbuyable baseCard;
    private String description;

    Unforseen(final Unbuyable baseCard, final String description) {
        this.baseCard = baseCard;
        this.description = description;
    }

    /**
     * @return the Unbuyable card assigned
     */
    public Unbuyable getCard() {
        return this.baseCard;
    }

    /**
     * @return the description of unforseen action
     */
    public String getDescription() {
        return this.description;
    }

    private final class TempFactory {
        private static final CardFactory FACTORY = new CardFactoryImpl();
        private static final String GIVEMONEY = "giveMoneyPlayer";
        private static final String PAYMONEY = "payPlayer";
        private static final String MOVE = "movePlayer";
    }

}
