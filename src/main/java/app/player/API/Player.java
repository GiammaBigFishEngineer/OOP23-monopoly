package app.player.api;

import java.util.*;

import app.card.api.Buildable;
import app.card.api.Buyable;

public interface Player {
    int getCurrentPosition();
    String getName();
    int getId();
    void buyBox(Buyable box);
    void buildHouse(Buildable box);
    int getNumberStationOwned();
    BankAccount getBankAccount();
    List<Buyable> getBuildableOwned();
    void sellBuyable(Buyable box);
    int getHouseBuilt(Buildable built);
}
