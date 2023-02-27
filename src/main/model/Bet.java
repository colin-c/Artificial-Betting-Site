package model;

import java.util.ArrayList;

public class Bet {

    private String betTitle;
    private String betDescription;
    private int wager;
    private int reward;
    private int totalPot;
    private ArrayList<Player> players;

    // EFFECTS: construct the bet
    public Bet(String title, String description, ArrayList<Player> players) {
        this.betTitle = title;
        this.betDescription = description;
        this.players = new ArrayList<>();
        this.totalPot = 0;
    }

    // REQUIRES: unique name not already added in the bet
    // MODIFIES: this
    // EFFECTS: adds player to bet
    public boolean addPlayer(Player p) {
        if (players.contains(p)) {
            return false;
        } else {
            players.add(p);
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
    }

    // REQUIRES: players.size() > 0
    // MODIFIES: this and Player
    // EFFECTS: add totalPot to player's fund and reduce totalPot to 0
    public void winBet(Player p) {
        System.out.println("Congrats to " + p.getName() + " for winning $" + totalPot);
        p.manuallyAddFund(totalPot);
        this.totalPot = 0;
    }

    // MODIFIES: this
    // EFFECTS: change the bet title
    public void changeBetTitle(String title) {
        this.betTitle = title;
    }

    // MODIFIES: this
    // EFFECTS: change the bet description
    public void changeBetDescription(String description) {
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

}
