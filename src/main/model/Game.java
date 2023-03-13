package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class Game implements Writable {

    private ArrayList<Bet> bets;
    private ArrayList<Player> players;
    private String title;

    public Game(String title, ArrayList<Bet> bets, ArrayList<Player> players) {
        this.bets = bets;
        this.players = players;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public ArrayList<Bet> getBets() {
        return this.bets;
    }

    // MODIFIES: this
    // EFFECTS: add bet to bets
    public void addBet(Bet bet) {
        bets.add(bet);
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    // MODIFIES: this
    // EFFECTS: add player to players
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("game", title);
        json.put("allbets", allBetsToJson());
        json.put("allplayers", allPlayersToJson());
        return json;
    }

    // EFFECTS: returns bets in this game as a JSON array
    private JSONArray allBetsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Bet b : bets) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;

    }

    // EFFECTS: returns players in this game as a JSON array
    private JSONArray allPlayersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : players) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;

    }
}
