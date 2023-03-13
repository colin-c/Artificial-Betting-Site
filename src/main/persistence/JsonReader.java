package persistence;

import model.Bet;
import model.Game;
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

// Represents a reader that reads game from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
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

    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        String name = jsonObject.getString("game");
        Game game = new Game(name,new ArrayList<>(), new ArrayList<>());
        addAllBets(game, jsonObject);
        addAllPlayers(game, jsonObject);

        return game;
    }

    private void addAllPlayers(Game game, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allplayers");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayerToGame(game, nextPlayer);
        }
    }

    // MODIFIES: game
    // EFFECTS: parse player from JSON object and adds it to game
    private void addPlayerToGame(Game game, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int fund = jsonObject.getInt("fund");
        Player player = new Player(name, fund);
        game.addPlayer(player);
    }

    // MODIFIES: game
    // EFFECTS: parse bets from JSON object and adds them to game
    private void addAllBets(Game game, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("allbets");
        for (Object json : jsonArray) {
            JSONObject nextBet = (JSONObject) json;
            addBet(game, nextBet);
        }
    }

    // MODIFIES: game
    // EFFECTS: parse bet from JSON object and adds it to game
    private void addBet(Game game, JSONObject jsonObject) {
        String betTitle = jsonObject.getString("betTitle");
        String betDescription = jsonObject.getString("betDescription");
        int totalPot = jsonObject.getInt("totalPot");
        ArrayList<Player> players = new ArrayList<>();
        Bet bet = new Bet(betTitle, betDescription, players);
        bet.addTotalPot(totalPot);
        addPlayersToBet(bet,jsonObject);
        game.addBet(bet);
    }

    // MODIFIES: bet
    // EFFECTS: parse player from JSON object and adds them to bet
    private void addPlayersToBet(Bet bet, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(bet, nextPlayer);
        }
    }

    // MODIFIES: bet
    // EFFECTS: parse player from JSON object and adds it to bet
    private void addPlayer(Bet bet, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int fund = jsonObject.getInt("fund");
        Player player = new Player(name, fund);
        bet.addPlayer(player);
    }
}