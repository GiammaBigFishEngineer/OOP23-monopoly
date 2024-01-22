package app.player.api;

import app.card.API.Buildable;
import app.card.API.Buyable;
import java.util.*;

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