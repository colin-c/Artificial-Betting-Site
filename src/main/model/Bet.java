package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// // Represent a bet that player(s) can partake in
public class Bet implements Writable {

    private String betTitle;
    private String betDescription;
    private int totalPot;
    private ArrayList<Player> players;

    // EFFECTS: construct the bet
    public Bet(String title, String description, ArrayList<Player> players) {
        this.betTitle = title;
        this.betDescription = description;
        this.players = new ArrayList<>();
        this.totalPot = 0;
        EventLog.getInstance().logEvent(new Event("'" + this.betTitle
                + "' has been created"));
    }

    // REQUIRES: unique name not already added in the bet
    // MODIFIES: this
    // EFFECTS: adds player to bet
    public boolean addPlayer(Player p) {
        if (players.contains(p)) {
            return false;
        } else {
            players.add(p);
            EventLog.getInstance().logEvent(new Event(p.getName() + " has been added this bet '"
                    + this.betTitle + "'"));
            return true;
        }
    }


    // REQUIRES: player to be in the players list
    // MODIFIES: this
    // EFFECTS: removes player from bet
    public boolean removePlayer(Player p) {
        if (players.contains(p)) {
            players.remove(p);
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: i >= 0
    // MODIFIES: THIS
    // EFFECTS: increase the totalPot size by i
    public void addTotalPot(int i) {
        this.totalPot += i;
        EventLog.getInstance().logEvent(new Event("The pot for '"
                + this.betTitle + "' is now $" + i));
    }

    // REQUIRES: players.size() > 0
    // MODIFIES: this and Player
    // EFFECTS: add totalPot to player's fund and reduce totalPot to 0
    public void winBet(Player p) {
        System.out.println("Congrats to " + p.getName() + " for winning $" + totalPot);
        EventLog.getInstance().logEvent(new Event(p.getName() + " has won the bet '"
                + this.betTitle + "'"));
        p.manuallyAddFund(totalPot);
        this.totalPot = 0;
    }

    // MODIFIES: this
    // EFFECTS: change the bet title
    public void changeBetTitle(String title) {
        EventLog.getInstance().logEvent(new Event("'" + this.betTitle + "'"
                + " has changed its title to '" + title + "'"));
        this.betTitle = title;
    }

    // MODIFIES: this
    // EFFECTS: change the bet description
    public void changeBetDescription(String description) {
        EventLog.getInstance().logEvent(new Event("'" + this.betDescription + "'"
                + " has changed its description to '" + description + "'"));
        this.betDescription = description;
    }

    public String getBetTitle() {
        return betTitle;
    }

    public String getBetDescription() {
        return betDescription;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTotalPot() {
        return totalPot;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("betTitle", betTitle);
        json.put("betDescription", betDescription);
        json.put("totalPot", totalPot);
        json.put("players", playersToJson());
        return json;
    }

    // EFFECTS: returns players in this bet as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : players) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;

    }

}
