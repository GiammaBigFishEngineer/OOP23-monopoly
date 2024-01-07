package app.player.API;

public interface BankAccount {
    int getBalance();
    void payPlayer(Player player, int amount);
    void receivePayment(int amount);
    Boolean isPaymentAllowed();
}
