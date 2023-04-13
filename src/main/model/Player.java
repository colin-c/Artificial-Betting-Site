package model;

import org.json.JSONObject;
import persistence.Writable;

// Represent a player's profile used for the gambling site
public class Player implements Writable {

    private String name;
    private int fund;

    // EFFECTS: constructs a player starting with fund amount
    public Player(String name, int fund) {
        this.name = name;
        this.fund = fund;
        EventLog.getInstance().logEvent(new Event("Player '" + name
                + "' has been created with $" + fund));
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: add amount onto fund
    public boolean manuallyAddFund(int amount) {
        if (amount >= 0) {
            this.fund += amount;
            EventLog.getInstance().logEvent(new Event("$" + amount + " added"
                    + " to " + this.name + "'s account"));
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: fund >= amount >= 0
    // MODIFIES: this
    // EFFECTS: subject amount onto fund
    public boolean manuallyRemoveFund(int amount) {
        if (amount <= fund) {
            this.fund -= amount;
            EventLog.getInstance().logEvent(new Event("$" + amount + " removed"
                    + " from " + this.name + "'s account"));
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: an existing player
    // MODIFIES: this
    // EFFECTS: change the name of the player
    public void changeName(String name) {
        EventLog.getInstance().logEvent(new Event(this.name + " has changed"
                + " its name to " + name));
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getFund() {
        return fund;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("fund", fund);
        return json;
    }

}
