package app.game.view;

public enum BtnCodeEnum {

    rollDice(0),
    buyPropriety(1),
    sellPropriety(2),
    buyHouse(3),
    endTurn(4);

    int code;

    BtnCodeEnum(int code) {
        this.code = code;
    }

}