package model;

// Represent a player's profile used for the gambling site
public class Player {

    private String name;
    private int fund;

    // EFFECTS: constructs a player starting with fund amount
    public Player(String name, int fund) {
        this.name = name;
        this.fund = fund;
    }

    // REQUIRES: fund >= 0
    //           amount >= 0
    // MODIFIES: this
    // EFFECTS: add amount onto fund
    public void manuallyAddFund(int amount) {
        this.fund += amount;
    }

    // REQUIRES: fund >= amount >= 0
    // MODIFIES: this
    // EFFECTS: subject amount onto fund
    public boolean manuallyRemoveFund(int amount) {
        if (amount <= fund) {
            this.fund -= amount;
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: an existing player
    // MODIFIES: this
    // EFFECTS: change the name of the player
    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getFund() {
        return fund;
    }

}
