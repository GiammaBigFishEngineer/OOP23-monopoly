package app.player.apii;

import java.util.*;

import app.card.apii.Buildable;
import app.card.apii.Buyable;

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
    void setPosition(int position);
}
