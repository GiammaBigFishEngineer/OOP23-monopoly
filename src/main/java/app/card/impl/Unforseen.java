package app.card.impl;

import app.card.api.CardFactory;
import app.card.api.Unbuyable;

public enum Unforseen {
    U0(tempFactory.factory.createStaticCard(0, "U0", "giveMoneyPlayer", 100),
        "Maturano le cedole delle vostre cartelle di rendita ritirate 100"),
    U1(tempFactory.factory.createStaticCard(1, "U1", "movePlayer", 15),
        "Andate in via accademia,"),
    U2(tempFactory.factory.createStaticCard(2, "U2", "playerPay", 50),
        "Matrimonio familiare, spendi 50"),
    U3(tempFactory.factory.createStaticCard(3, "U3", "playerPay", 100),
        "Decidi di versare 100 euro in beneficenza"),
    U4(tempFactory.factory.createStaticCard(4, "U4", "giveMoneyPlayer", 50),
        "Vinci una partita di black jack, incassi 50"),
    U5(tempFactory.factory.createStaticCard(5, "U5", "movePlayer", 0),
        "Andate al via con tanti auguri!"),
    U6(tempFactory.factory.createStaticCard(6, "U6", "movePlayer", 23),
        "Andate fino al Grand Hotel!"),
    U7(tempFactory.factory.createStaticCard(7, "U7", "giveMoneyPlayer", 20),
        "Hai vinto un premio di bellezza incassi solo 20 (perchè non sei così tanto bello/a)"),
    U8(tempFactory.factory.createStaticCard(8, "U8", "playerPay", 150),
        "Scade l'assicurazione, paghi 150"),
    U9(tempFactory.factory.createStaticCard(9, "U9", "movePlayer", 20),
        "Lo shopping compulsivo ti prende, vai alle Befane shopping center"),
    U10(tempFactory.factory.createStaticCard(10, "U10", "playerPay", 50),
        "Ti sei morso la lingua e devi farti visitare, spendi 50"),
    U11(tempFactory.factory.createStaticCard(11, "U11", "movePlayer", 0),
        "Andate al via con tanti auguri"),
    U12(tempFactory.factory.createStaticCard(12, "U12", "playerPay", 100),
        "Paghi una multa per eccesso di stupidità, sono 100 euro"),
    U13(tempFactory.factory.createStaticCard(13, "U13", "giveMoneyPlayer", 100),
        "Erediti 100 euro da un lontano zio (beato te)"),
    U14(tempFactory.factory.createStaticCard(14, "U14", "giveMoneyPlayer", 25),
        "Vendi il vaso con le ceneri della tua tris Nonna, incassi 25");

    private Unbuyable baseCard;
    private String description;  

    private Unforseen(Unbuyable baseCard, String description){
        this.baseCard = baseCard;
        this.description = description;
    }

    public Unbuyable getCard(){
        return this.baseCard;
    }

    public String getDescription(){
        return this.description;
    }

    private class tempFactory{
        private static final CardFactory factory = new CardFactoryImpl();
    }

}
