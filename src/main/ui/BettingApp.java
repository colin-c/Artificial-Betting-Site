package ui;

import model.Bet;
import model.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

// Virtual betting site application
public class BettingApp {

    private ArrayList<Bet> bets;
    private ArrayList<Player> players;
    private Scanner input;


    // EFFECTS: runs the betting application
    public BettingApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean running = true;
        String command = null;
        players = new ArrayList<>();
        bets = new ArrayList<>();

        init();

        while (running) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("e")) {
                running = false;
            } else {
                processCommandMainMenu(command);
            }
        }

        System.out.println("Thank you for playing!");
    }

    // MODIFIES: this
    // EFFECTS: processes main menu user command
    private void processCommandMainMenu(String command) {
        if (command.equals("b")) {
            displayBetMenu();
            command = input.next();
            command = command.toLowerCase();
            processCommandBetMenu(command);
        } else if (command.equals("p")) {
            displayPlayerMenu();
            command = input.next();
            command = command.toLowerCase();
            processCommandPlayerMenu(command);
        } else {
            System.out.println("Invalid selection...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes player user command
    private void processCommandPlayerMenu(String command) {
        if (command.equals("c")) {
            createPlayer();
        } else if (command.equals("v")) {
            viewPlayer();
        } else if (command.equals("d")) {
            deletePlayer();
        } else if (command.equals("r")) {
            displayMainMenu();
            command = input.next();
            processCommandMainMenu(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes bet user command
    private void processCommandBetMenu(String command) {
        if (command.equals("c")) {
            createBet();
        } else if (command.equals("v")) {
            viewBet();
        } else if (command.equals("d")) {
            deleteBet();
        } else if (command.equals("r")) {
            displayMainMenu();
            command = input.next();
            processCommandMainMenu(command);
        }
    }

    // REQUIRES: cannot create a new player which an already existing name
    // MODIFIES: this
    // EFFECTS: constructs a new player
    private void createPlayer() {
        System.out.println("Enter player's name:");
        input.nextLine();
        String name = input.nextLine();

        System.out.println("Enter starting fund");
        int fund = input.nextInt();

        Player p = new Player(name, fund);
        players.add(p);

        System.out.println("New player " + p.getName() + " with $" + p.getFund() + " was created!");
    }

    // EFFECTS: see a list of existing players
    private void viewPlayer() {
        System.out.println("\tList of existing player:");
        for (Player p : players) {
            System.out.println(p.getName() + ": $" + p.getFund());
        }
        System.out.println("Type name of an existing player if you want to edit them or press r to return:");
        input.nextLine();
        String typed = input.nextLine();
        for (Player p : players) {
            if (p.getName().equals(typed)) {
                editPlayer(p);
            } else if (typed.equals("r")) {
                System.out.println("Returning to main menu.");
            } else {
                System.out.println("The player isn't a currently existing user...");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: change funds or name of a player
    private void editPlayer(Player p) {
        System.out.println("\ta -> add funds");
        System.out.println("\tr -> remove funds");
        System.out.println("\te -> edit name");
        String command = input.next();
        if (command.equals("a")) {
            System.out.println("Input the amount you want to add to the account");
            p.manuallyAddFund(input.nextInt());
            System.out.println(p.getName() + " now has $" + p.getFund());
        } else if (command.equals("r")) {
            System.out.println("Input the amount you want to remove from the account");
            p.manuallyRemoveFund(input.nextInt());
            System.out.println(p.getName() + " now has $" + p.getFund());
        } else if (command.equals("e")) {
            System.out.println("Type in the profile's new name");
            input.nextLine();
            p.changeName(input.nextLine());
            System.out.println(p.getName() + " now has $" + p.getFund());
        } else {
            System.out.println("Invalid input...");
        }
    }

    // MODIFIES: this
    // EFFECTS: remove a player
    private void deletePlayer() {
        System.out.println("Enter the player's name of the profile that would like to be deleted:");
        for (Player p : players) {
            System.out.println(p.getName());
        }

        input.nextLine();
        String selectedPlayer = input.nextLine();

        ListIterator<Player> iter = players.listIterator();
        while (iter.hasNext()) {
            if (iter.next().getName().equals(selectedPlayer)) {
                System.out.println(selectedPlayer + " has been successfully removed!");
                iter.remove();

            }
        }


    }

    // MODIFIES: this
    // EFFECTS: construct a new bet
    private void createBet() {
        System.out.println("What is the bet title?");
        input.nextLine();
        String betTitle = input.nextLine();

        System.out.println("What is the bet description?");
        String betDescription = input.nextLine();

        Bet b = new Bet(betTitle, betDescription, new ArrayList<Player>());
        bets.add(b);
        System.out.println("Bet: '" + betTitle + "' has been successfully added!");
    }

    private void viewBet() {
        int index = 0;
        for (Bet bet : bets) {
            index++;
            System.out.println(index + ". " + bet.getBetTitle() + " : " + bet.getBetDescription()
                    + " : Total Pot is $" + bet.getTotalPot() + " : " + playersInBet(bet));
        }

        displayViewBetMenu();
        String command = input.next();
        if (command.equals("e")) {
            editBet();
        } else if (command.equals("r")) {
            System.out.println("Returning to main menu...");
        }

    }

    private ArrayList<String> playersInBet(Bet bet) {
        ArrayList<Player> list = bet.getPlayers();
        ArrayList<String> names = new ArrayList<>();
        for (Player player : list) {
            names.add(player.getName());
        }
        return names;
    }

    private void editBet() {
        System.out.println("Please type the index of the bet you wish to edit");
        int index = 0;
        for (Bet bet : bets) {
            index++;
            System.out.println(index + ". " + bet.getBetTitle());
        }
        int selected = input.nextInt();
        selected--;
        Bet chosenBet = bets.get(selected);
        displayEditBetMenu();
        String command = input.next();

        if (command.equals("a")) {
            addPlayerToBet(chosenBet);
        } else if (command.equals("w")) {
            winnerOfBet(chosenBet);
        } else if (command.equals("b")) {
            changeTitleOrDescription(chosenBet);
        }
    }

    // REQUIRES: player to be existing and wager <= player's fund
    // MODIFIES: this
    // EFFECTS: player is participating in bet
    private void addPlayerToBet(Bet bet) {
        int index = 0;
        for (Player p : players) {
            index++;
            System.out.println(index + ". " + p.getName() + ": $" + p.getFund());
        }
        System.out.println("Type in the index of player you would like to add:");
        int chosenIndex = input.nextInt();
        chosenIndex--;
        bet.addPlayer(players.get(chosenIndex));

        System.out.println("Type in " + players.get(chosenIndex).getName() + "'s wager:");
        int wager = input.nextInt();
        bet.addTotalPot(wager);
        players.get(chosenIndex).manuallyRemoveFund(wager);

        System.out.println(players.get(chosenIndex).getName() + " has wager $" + wager + " into the '"
                + bet.getBetTitle() + "' bet");
    }

    private void winnerOfBet(Bet bet) {
        System.out.println("Type the index of the player who won:");
        int index = 0;
        for (Player player : bet.getPlayers()) {
            index++;
            System.out.println(index + ". " + player.getName());
        }

        int selected = input.nextInt();
        selected--;
        Player selectedPlayer = bet.getPlayers().get(selected);
        bet.winBet(selectedPlayer);

        bet.changeBetTitle(bet.getBetTitle() + " (DONE)");
    }

    private void changeTitleOrDescription(Bet b) {
        System.out.println("Select one of the following:");
        System.out.println("\tt -> to change the title of the bet");
        System.out.println("\td -> to change the description of the bet");

        input.nextLine();
        String command = input.nextLine();

        if (command.equals("t")) {
            System.out.println("Type in the bet's new title");
            b.changeBetTitle(input.nextLine());
            System.out.println("Title successfully changed!");
        } else if (command.equals("d")) {
            System.out.println("Type in the bet's new description");
            b.changeBetDescription(input.nextLine());
            System.out.println("Description successfully changed!");
        }
    }

    // REQUIRES: bet to have been already existing
    // MODIFIES: this
    // EFFECTS: removes bet from app
    private void deleteBet() {
        System.out.println("Please select the index of the bet that you wish to delete:");
        for (Bet bet : bets) {
            int index = 0;
            index++;
            System.out.println(index + ". " + bet.getBetTitle());
        }
        int command = input.nextInt();
        if (command <= bets.size()) {
            command--;
            bets.remove(command);
            System.out.println("Bet has successfully been removed!");
        } else {
            System.out.println("Invalid option...");
        }
    }

    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("\tb -> bet options");
        System.out.println("\tp -> player options");
        System.out.println("\te -> exit");

    }

    // EFFECTS: displays player menu of options to user
    private void displayPlayerMenu() {
        System.out.println("\nPlease select one of the following");
        System.out.println("\tc -> create new player");
        System.out.println("\tv -> view or edit existing player");
        System.out.println("\td -> delete player");
        System.out.println("\tr -> return to main menu");
    }

    // EFFECTS: displays bet menu of options to user
    private void displayBetMenu() {
        System.out.println("\nPlease select one of the following");
        System.out.println("\tc -> create new bet");
        System.out.println("\tv -> view bets");
        System.out.println("\td -> delete bet");
        System.out.println("\tr -> return to main menu");
    }

    // EFFECTS: display view-bet menu of options to user
    private void displayViewBetMenu() {
        System.out.println("\nSelected one of the following options:");
        System.out.println("\te -> edit bet");
        System.out.println("\tr -> return to main menu");
    }

    private void displayEditBetMenu() {
        System.out.println("\nSelect one of the following options:");
        System.out.println("\ta -> add a player to bet");
        System.out.println("\tw -> select winner(s) of the bet");
        System.out.println("\tb -> to change bet's title or description");
    }

    // MODIFIES: this
    // EFFECTS: initializes bets
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }
}
