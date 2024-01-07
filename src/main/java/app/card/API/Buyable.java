package app.card.API;

import app.player.API.Player;

public interface Buyable {
    int getPrice();
    Boolean isOwned();
    Boolean isOwnedByPlayer(Player player);
    Player getOwner();
    int getTransitFees();
}
