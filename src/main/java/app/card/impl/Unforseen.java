package app.card.impl;

import app.card.api.CardFactory;
import app.card.api.Unbuyable;

public enum Unforseen{
    U1(tempFactory.factory.createStaticCard(0, "U0", "giveMoneyPlayer", 100),
        "Maturano le cedole delle vostre cartelle di rendita ritirate 100"),
    U2(tempFactory.factory.createStaticCard(1, "U1", "movePlayer", 15),
        "Andate in via accademia,"),
    U3(tempFactory.factory.createStaticCard(2, "U2", "giveMoneyPlayer", 0),
        ""),
    U4(tempFactory.factory.createStaticCard(3, "U3", "giveMoneyPlayer", 0),
        ""),
    U5(tempFactory.factory.createStaticCard(4, "U4", "giveMoneyPlayer", 0),
        ""),
    U6(tempFactory.factory.createStaticCard(5, "U5", "giveMoneyPlayer", 0),
        ""),
    U7(tempFactory.factory.createStaticCard(6, "U6", "giveMoneyPlayer", 0),
        ""),
    U8(tempFactory.factory.createStaticCard(7, "U7", "giveMoneyPlayer", 0),
        ""),
    U9(tempFactory.factory.createStaticCard(8, "U8", "giveMoneyPlayer", 0),
        ""),
    U10(tempFactory.factory.createStaticCard(9, "U9", "giveMoneyPlayer", 0),
        ""),
    U11(tempFactory.factory.createStaticCard(10, "U10", "giveMoneyPlayer", 0),
        ""),
    U12(tempFactory.factory.createStaticCard(11, "U11", "giveMoneyPlayer", 0),
        ""),
    U13(tempFactory.factory.createStaticCard(12, "U12", "giveMoneyPlayer", 0),
        ""),
    U14(tempFactory.factory.createStaticCard(13, "U13", "giveMoneyPlayer", 0),
        ""),
    U15(tempFactory.factory.createStaticCard(14, "U14", "giveMoneyPlayer", 0),
        "");

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
