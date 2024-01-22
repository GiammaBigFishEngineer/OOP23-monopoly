package app.card.API;

import app.player.api.Player;

public interface Buyable {
    int getPrice();
    Boolean isOwned();
    Boolean isOwnedByPlayer(Player player);
    Player getOwner();
    int getTransitFees();
}
