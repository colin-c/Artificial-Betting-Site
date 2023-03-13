package persistence;

import model.Bet;
import model.Player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// CITATION: model after the JsonSerializationDemo sample application

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ArrayList<Object> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private ArrayList<Object> parseGame(JSONObject jsonObject) {
        ArrayList<Object> game = new ArrayList<>();
        game.add(parseBet(jsonObject));
        game.add(parsePlayer(jsonObject));

        return game;
    }


    private ArrayList<Player> parsePlayer(JSONObject jsonObject) {
        JSONObject jsonGame = jsonObject.getJSONObject("game");
        JSONArray jsonPlayer = jsonGame.getJSONArray("player");
        ArrayList<Player> players = new ArrayList<>();

        for (int i = 0; i < jsonPlayer.length(); i++) {
            String name = jsonPlayer.getJSONObject(i).getString("name");
            int fund = jsonPlayer.getJSONObject(i).getInt("fund");
            Player player = new Player(name, fund);
            players.add(player);
        }
        return players;
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private ArrayList<Bet> parseBet(JSONObject jsonObject) {
        JSONObject jsonGame = jsonObject.getJSONObject("game");
        JSONArray jsonBet = jsonGame.getJSONArray("bet");
        ArrayList<Bet> bets = new ArrayList<>();

        for (int i = 0; i < jsonBet.length(); i++) {
            String betTitle = jsonBet.getJSONObject(i).getString("betTitle");
            String betDescription = jsonBet.getJSONObject(i).getString("betDescription");
            int totalPot = jsonBet.getJSONObject(i).getInt("totalPot");
            List<Player> players = new ArrayList<>();
            Bet bet = new Bet(betTitle, betDescription, (ArrayList<Player>) players);
            addPlayer(bet, jsonObject, i);
            bets.add(bet);
        }
        return bets;
    }

    // MODIFIES: bet
    // EFFECTS: parses thingy from JSON object and adds it to bet
    private void addPlayer(Bet bet, JSONObject jsonObject, int i) {
        JSONObject jsonGame = jsonObject.getJSONObject("game");
        JSONArray jsonBet = jsonGame.getJSONArray("bet");
        JSONArray jsonPlayers = jsonBet.getJSONObject(i).getJSONArray("players");
        for (int j = 0; j < jsonPlayers.length(); j++) {
            String name = jsonPlayers.getJSONObject(j).getString("name");
            int fund = jsonPlayers.getJSONObject(j).getInt("fund");
            Player player = new Player(name, fund);
            bet.addPlayer(player);
        }
    }
}