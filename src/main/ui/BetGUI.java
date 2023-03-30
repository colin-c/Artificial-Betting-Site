package ui;

import model.Bet;
import model.Game;
import model.Player;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BetGUI extends JFrame {

    private static final String JSON_STORE = "./data/betApp.json";
    private Game game;
    private Bet selectedBet;

    private ArrayList<Bet> bets;
    private ArrayList<Player> players;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;
    private JFrame editBetFrame;
    private JFrame addPlayerFrame;
    private JFrame winnerFrame;


    private JLabel greeting;

    private JPanel playerTab;
    private JPanel betTab;
    private JPanel homeTab;
    private JPanel playerButtonPanel;
    private JPanel betButtonPanel;
    private JPanel editBetPanel;
    private JPanel editBetButtonPanel;
    private JPanel addPlayerPanel;
    private JPanel winnerPanel;
    private JPanel extraTab;

    private JTabbedPane sidebar;

    private JTable playerTable;
    private JTable betTable;
    private JTable addPlayerTable;
    private JTable winnerTable;

    private DefaultTableModel playerTableModel;
    private DefaultTableModel betTableModel;
    private DefaultTableModel addPlayerTableModel;
    private DefaultTableModel winnerTableModel;


    private JButton newPlayerButton;
    private JButton editPlayerButton;
    private JButton editBetButton;
    private JButton winnerBetButton;
    private JButton deletePlayerButton;
    private JButton newBetButton;
    private JButton changeTitleButton;
    private JButton changeDescriptionButton;
    private JButton addPlayerButton;

    private JScrollPane scrollPanePlayer;
    private JScrollPane scrollPaneAddPlayer;
    private JScrollPane scrollPaneWinner;
    private JPanel winnerImagePanel;
    private ImageIcon winnerImageIcon;
    private JLabel winnerImageLabel;
    private JLabel winnerMessageLabel;
    private JButton deleteBetButton;
    private Player selectedPlayer;

    // MODIFIES: this
    // EFFECTS: initiate the Json and frame
    public void init() {
        frame.setTitle("Bet Haven");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // EFFECTS: create a new instance for the field
    public void instantiate() {
        sidebar = new JTabbedPane();
        homeTab = new JPanel();
        betTab = new JPanel();
        playerTab = new JPanel();
        extraTab = new JPanel();
        playerTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        betTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        playerTable = new JTable();
        playerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        betTable = new JTable();
        bets = new ArrayList<>();
        players = new ArrayList<>();
        game = new Game("Game", bets, players);
    }

    // EFFECTS: initiates the BetGUI
    public BetGUI() {
        instantiate();

        run();

    }

    // MODIFIES: this
    // EFFECTS: runs the program
    public void run() {
        frame = new JFrame("BetGUI");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        init();

        sidebar.setTabPlacement(JTabbedPane.LEFT);
        sidebar.setBackground(new Color(0xABDCAB, true));
        sidebar.setForeground(new Color(0xB31E721E));

        loadTab();

        betTab();
        playerTab();
        homeTab();

        frame.add(sidebar);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add in the home, bets, and players tab
    public void loadTab() {
        sidebar.add("Home", homeTab);
        sidebar.add("Bets", betTab);
        sidebar.add("Players", playerTab);
//        sidebar.add("Extra", extraTab);

    }

    // MODIFIES: this
    // EFFECTS: constructs a home tab for console with a greeting and save/load function
    public JPanel homeTab() {
        homeTab = (JPanel) sidebar.getComponentAt(0);
        homeTab.setLayout(new GridLayout(4, 1));
        homeTab.setBackground(new Color(0x5DB55D));

        greeting();
        image();
        saveAndLoad();
        exit();

        return homeTab;
    }

    // MODIFIES: this
    // EFFECTS: creates a greeting message
    public void greeting() {
        greeting = new JLabel("You've arrived at Bet Haven - welcome!", JLabel.CENTER);
        greeting.setFont(new Font("Monospaced", Font.BOLD, 30));
        greeting.setSize(WIDTH, HEIGHT / 4);
        homeTab.add(greeting);
    }

    // MODIFIES: this
    // EFFECTS: adds a logo image
    public void image() {
        try {
            URL url = getClass().getResource("/resources/moneyGIF.gif");

            ImageIcon logoIcon = new ImageIcon(url);
            Image original = logoIcon.getImage();
            Image scaled = original.getScaledInstance(200, 150, Image.SCALE_DEFAULT);
            ImageIcon convertImage = new ImageIcon(scaled);
            JLabel gifLabel = new JLabel(convertImage);

            JPanel logoIconPanel = new JPanel(new FlowLayout());
            logoIconPanel.add(gifLabel);

            homeTab.add(gifLabel);
        } catch (Exception e) {
            System.out.println("GIF output has fail");
        }
    }


    // MODIFIES: this
    // EFFECTS: saves the current state or load the previously saved state
    public void saveAndLoad() {
        JButton save = new JButton("Save");
        save.setBackground(new Color(0xFFBFDCB7));

        JButton load = new JButton("Load");
        load.setBackground(new Color(0xFFBFDCB7));

        JPanel saveAndLoadPanel = new JPanel(new FlowLayout());
        saveAndLoadPanel.setBackground(new Color(0x5DB55D));
        saveAndLoadPanel.add(save);
        saveAndLoadPanel.add(load);
        saveAndLoadPanel.setSize(WIDTH, HEIGHT / 4);

        homeTab.add(saveAndLoadPanel);

        save.addActionListener(e -> {
            saveBettingApp();
        });

        load.addActionListener(e -> {
            loadBettingApp();
        });

    }

    // MODIFIES: this
    // EFFECTS: leaves the application
    public void exit() {
        JButton exit = new JButton("Exit");
        JPanel exitPanel = new JPanel(new FlowLayout());
        exitPanel.setBackground(new Color(0x5DB55D));
        exitPanel.add(exit);

        homeTab.add(exitPanel);

        exit.addActionListener(e -> {
            exitApp();
        });
    }

    // MODIFIES: this
    // EFFECTS: constructs a bet tab where bets can be created, edited, or removed
    public JPanel betTab() {
        betTab = (JPanel) sidebar.getComponentAt(1);
        betTab.setLayout(new BorderLayout());

        betButton();
        tableBet();

        betTable.setVisible(true);

        return betTab;
    }

    // MODIFIES: this
    // EFFECTS: bet buttons
    public void betButton() {
        newBetButton = new JButton("+ create bet");
        editBetButton = new JButton("... edit bet");
        winnerBetButton = new JButton("$$ winner?");
        deleteBetButton = new JButton("- delete bet");

        betButtonPanel = new JPanel();
        betButtonPanel.setBackground(new Color(0x4CF873));
        betButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        betButtonPanel.add(newBetButton);
        betButtonPanel.add(editBetButton);
        betButtonPanel.add(winnerBetButton);
        betButtonPanel.add(deleteBetButton);

        betTab.add(betButtonPanel, BorderLayout.SOUTH);

        betButtonActions();
    }

    // EFFECTS: helper function for actions of buttons
    public void betButtonActions() {
        newBetButton.addActionListener(e -> {
            createNewBet();
            updateTableBet();
        });

        editBetButton.addActionListener(e -> {
            editBet();
            scrollPanePlayer = scrollPaneAddPlayer;
        });

        winnerBetButton.addActionListener(e -> {
            selectWinner();
            updateTablePlayer();
        });

        deleteBetButton.addActionListener(e -> {
            deleteBet();
            updateTableBet();
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a new bet
    public void createNewBet() {
        String title = JOptionPane.showInputDialog(this, "What is the title of this bet?");
        String description = JOptionPane.showInputDialog(this, "What is the description of this bet?");

        if (title != null && description != null) {
            Bet b = new Bet(title, description, new ArrayList<>());
            bets.add(b);
            JOptionPane.showMessageDialog(this, "The bet -" + title + "- has been successfully created");
        } else {
            JOptionPane.showMessageDialog(this, "No new bet was created");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a tableBet JTable containing just the columns
    public void tableBet() {
        betTableModel.setColumnCount(0);
        betTableModel.addColumn("Title:");
        betTableModel.addColumn("Description:");
        betTableModel.addColumn("Total Pot:");
        betTableModel.addColumn("Players:");


        betTable.setModel(betTableModel);
        betTable.getColumnModel().getColumn(0).setPreferredWidth(2000);
        betTable.getColumnModel().getColumn(1).setPreferredWidth(4000);
        betTable.getColumnModel().getColumn(2).setPreferredWidth(1500);
        betTable.getColumnModel().getColumn(3).setPreferredWidth(3000);

        betTable.getColumnModel().getColumn(1).setCellRenderer(new WrapTextRenderer());

        betTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPaneBet = new JScrollPane(betTable);
        betTab.add(scrollPaneBet);
    }

    // MODIFIES: this
    // EFFECTS: updates the TableBet JTable
    public void updateTableBet() {
        betTableModel.setRowCount(0);

        for (Bet b : bets) {
            Object[] betInfo = new Object[]{b.getBetTitle(), b.getBetDescription(), b.getTotalPot(),
                    getPlayersName(b.getPlayers())};
            betTableModel.addRow(betInfo);
        }
    }

    // EFFECTS: converts a list of players into a list of strings with the players name
    public ArrayList getPlayersName(ArrayList<Player> l) {
        ArrayList nameList = new ArrayList<>();
        for (Player p : l) {
            String name = p.getName();
            nameList.add(name);
        }
        return nameList;
    }

    // MODIFIES: this
    // EFFECTS: option for editing a bet
    public void editBet() {
        selectedBet = bets.get(betTable.getSelectedRow());
        editBetFrame = new JFrame("Editing " + selectedBet.getBetTitle());
        editBetFrame.setSize(400, 80);
        editBetFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        editBetPanel();

        editBetFrame.add(editBetPanel);
        editBetFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: a panel for the editBet button
    public void editBetPanel() {
        editBetPanel = new JPanel();
        editBetPanel.setLayout(new BorderLayout());

        editBetButtonPanel = new JPanel();
        playerButtonPanel.setBackground(new Color(0x4CF873));
        editBetButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        changeTitleButton = new JButton("change title");
        changeDescriptionButton = new JButton("change description");
        addPlayerButton = new JButton("add player");

        editBetButtonPanel.add(changeTitleButton);
        editBetButtonPanel.add(changeDescriptionButton);
        editBetButtonPanel.add(addPlayerButton);

        editBetPanel.add(editBetButtonPanel, BorderLayout.SOUTH);

        editBetPanelActions();
    }

    // EFFECTS: helper function to editBetPanel buttons' addActionListener
    public void editBetPanelActions() {
        changeTitleButton.addActionListener(e -> {
            changeTitle();
            updateTableBet();
        });
        changeDescriptionButton.addActionListener(e -> {
            changeDescription();
            updateTableBet();
        });
        addPlayerButton.addActionListener(e -> {
            addPlayer();
            updateTableBet();
        });
    }

    // MODIFIES: this
    // EFFECTS: changes the title of the selected bet
    public void changeTitle() {
        String initTitle = selectedBet.getBetTitle();

        int response = JOptionPane.showConfirmDialog(this, "Do you want to edit the title '" + initTitle
                + "'?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            String newTitle = JOptionPane.showInputDialog(this, "Replace the title '" + initTitle + "' with?");

            if (newTitle != null) {
                selectedBet.changeBetTitle(newTitle);
                JOptionPane.showMessageDialog(this, "Title has been changed");
            } else {
                JOptionPane.showMessageDialog(this, "Error 111");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the description of the selected bet
    public void changeDescription() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to edit the description of '"
                + selectedBet.getBetTitle() + "'?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            String newDescription = JOptionPane.showInputDialog(this, "Replace the description of '"
                    + selectedBet.getBetTitle() + "' with?");

            if (newDescription != null) {
                selectedBet.changeBetDescription(newDescription);
                JOptionPane.showMessageDialog(this, "Description has been changed");
            } else {
                JOptionPane.showMessageDialog(this, "Error 111");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: option of adding players to a bet
    public void addPlayer() {
        addPlayerFrame = new JFrame("add players to " + selectedBet.getBetTitle());
        addPlayerFrame.setSize(800, 400);

        addPlayerPanel = new JPanel();
        addPlayerPanel.setLayout(new BorderLayout());

        createAddPlayerTableModel();

        addPlayerPanel.add(scrollPaneAddPlayer);
        addPlayerFrame.add(addPlayerPanel);

        addPlayerFrame.setVisible(true);

        mouseEvent();
    }

    // MODIFIES: this
    // EFFECTS: selects a player from the JTable and added it to the bet along with amountBet fund
    public void mouseEvent() {
        addPlayerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Player p = players.get(addPlayerTable.getSelectedRow());

                int response = JOptionPane.showConfirmDialog(addPlayerFrame, "Do you want to add player '" + p.getName()
                        + "' to the bet?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    String amountBet = JOptionPane.showInputDialog(addPlayerFrame, "How much will '"
                            + p.getName() + "' bet?");
                    if (amountBet != null && Integer.parseInt(amountBet) <= p.getFund()) {
                        p.manuallyRemoveFund(Integer.parseInt(amountBet));
                        selectedBet.addPlayer(p);
                        selectedBet.addTotalPot(Integer.parseInt(amountBet));
                        JOptionPane.showMessageDialog(addPlayerFrame, "Player has successfully been added to bet");
                        toUpdate();
                    } else {
                        JOptionPane.showMessageDialog(addPlayerFrame, "Error 111");
                    }
                }
            }
        });

    }

    // EFFECTS: creates a addPlayerTableModel
    public void createAddPlayerTableModel() {
        addPlayerTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        addPlayerTableModel.setColumnCount(0);
        addPlayerTableModel.addColumn("Name:");
        addPlayerTableModel.addColumn("Fund:");

        updateAddPlayerTable();
    }

    // MODIFIES: this
    // EFFECTS: updates the addPlayerTable
    public void updateAddPlayerTable() {
        addPlayerTableModel.setRowCount(0);

        for (Player p : players) {
            Object[] playerInfo = new Object[]{p.getName(), p.getFund()};
            addPlayerTableModel.addRow(playerInfo);
        }

        addPlayerTable = new JTable();
        addPlayerTable.setModel(addPlayerTableModel);

        scrollPaneAddPlayer = new JScrollPane(addPlayerTable);
    }

    // EFFECTS: helper function
    public void toUpdate() {
        updateAddPlayerTable();
        updateTableBet();
        updateTablePlayer();
    }

    // MODIFIES: this
    // EFFECTS: selects a winner to the bet and give that player the totalPot
    public void selectWinner() {
        selectedBet = bets.get(betTable.getSelectedRow());

        winnerFrame = new JFrame("Select a winner to '" + selectedBet.getBetTitle() + "'");
        winnerFrame.setSize(800, 400);
        winnerFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        winnerPanel = new JPanel(new BorderLayout());

        createWinnerTableModel();
        pickWinner();
        updateTablePlayer();

        winnerPanel.add(scrollPaneWinner);
        winnerFrame.add(winnerPanel);

        winnerFrame.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: selects a winner
    public void pickWinner() {
        winnerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                selectedPlayer = selectedBet.getPlayers().get(winnerTable.getSelectedRow());

                int response = JOptionPane.showConfirmDialog(winnerFrame, "Are you sure '" + selectedPlayer.getName()
                                + "' has won the '" + selectedBet.getBetTitle() + "' bet?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                winnerImage(selectedPlayer);
                if (response == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, winnerImagePanel, "Winner!", JOptionPane.PLAIN_MESSAGE);
                    selectedBet.winBet(selectedPlayer);
                    updateTableBet();
                    winnerFrame.dispose();
                }
            }
        });
    }

    // EFFECTS: creates an image and message for the winner
    public void winnerImage(Player p) {
        winnerImagePanel = new JPanel(new BorderLayout());
        winnerImagePanel.setSize(200,100);

        URL url = getClass().getResource("/resources/winnerGIF.gif");
        winnerImageIcon = new ImageIcon(url);
        winnerImageLabel = new JLabel(winnerImageIcon);
        winnerImagePanel.add(winnerImageLabel, BorderLayout.CENTER);

        String winnerMessage = "Congrats to '" + p.getName() + "' for winning $" + selectedBet.getTotalPot()
                + " from the bet '" + selectedBet.getBetTitle() + "'!!";
        winnerMessageLabel = new JLabel(winnerMessage);
        winnerMessageLabel.setHorizontalAlignment(JLabel.CENTER);

        winnerImagePanel.add(winnerMessageLabel, BorderLayout.SOUTH);

        winnerImagePanel.setVisible(true);
    }

    // EFFECTS: create a winnerTableModel
    public void createWinnerTableModel() {
        winnerTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        winnerTableModel.setColumnCount(0);
        winnerTableModel.addColumn("Name:");

        updateWinnerTableModel();
    }

    // MODIFIES: this
    // EFFECTS: updates the winnerTable
    public void updateWinnerTableModel() {
        winnerTableModel.setRowCount(0);

        for (Player p : selectedBet.getPlayers()) {
            Object[] playerInfo = new Object[]{p.getName()};
            winnerTableModel.addRow(playerInfo);
        }

        winnerTable = new JTable();
        winnerTable.setModel(winnerTableModel);

        scrollPaneWinner = new JScrollPane(winnerTable);
    }

    // MODIFIES: this
    // EFFECTS: deletes a bet
    public void deleteBet() {
        Bet b = bets.get(betTable.getSelectedRow());
        String title = b.getBetTitle();
        JOptionPane.showMessageDialog(this, "'" + title + "' has been removed");
        bets.remove(betTable.getSelectedRow());
    }

    // EFFECTS: constructs a player tab where players can be created, edited or removed
    public JPanel playerTab() {
        playerTab = (JPanel) sidebar.getComponentAt(2);
        playerTab.setLayout(new BorderLayout());

        playerButton();
        tablePlayer();

        playerTab.setVisible(true);

        return playerTab;
    }

    // MODIFIES: this
    // EFFECTS: a button that can be used to create a new player
    public void playerButton() {
        newPlayerButton = new JButton("+ create player");
        editPlayerButton = new JButton("... edit player");
        deletePlayerButton = new JButton("- delete player");

        playerButtonPanel = new JPanel();
        playerButtonPanel.setBackground(new Color(0x4CF873));
        playerButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        playerButtonPanel.add(newPlayerButton);
        playerButtonPanel.add(editPlayerButton);
        playerButtonPanel.add(deletePlayerButton);

        playerTab.add(playerButtonPanel, BorderLayout.SOUTH);

        newPlayerButton.addActionListener(e -> {
            createNewPlayer();
            updateTablePlayer();
        });
        editPlayerButton.addActionListener(e -> {
            editPlayer();
            updateTablePlayer();
        });
        deletePlayerButton.addActionListener(e -> {
            deletePlayer();
            updateTablePlayer();
        });
    }

    // MODIFIES: this
    // EFFECTS: constructs a new player
    public void createNewPlayer() {
        String name = JOptionPane.showInputDialog(this, "What is the player's name?");
        String fund = JOptionPane.showInputDialog(this, "What is the starting fund?");

        if (name != null && fund != null) {
            int intFund = Integer.parseInt(fund);
            Player p = new Player(name, intFund);
            players.add(p);
            JOptionPane.showMessageDialog(this, "Player " + name + " has been successfully created with $" + fund);
        } else {
            JOptionPane.showMessageDialog(this, "No new player was created");
        }
    }

    // EFFECTS: creates a tablePlayer JTable containing just the columns
    public void tablePlayer() {
        playerTableModel.setColumnCount(0);
        playerTableModel.addColumn("Name:");
        playerTableModel.addColumn("Fund:");

        playerTable.setModel(playerTableModel);

        scrollPanePlayer = new JScrollPane(playerTable);
        playerTab.add(scrollPanePlayer);
    }

    // MODIFIES: this
    // EFFECTS: updates the tablePlayer with the current players list
    public void updateTablePlayer() {
        playerTableModel.setRowCount(0);

        for (Player p : players) {
            Object[] playerInfo = new Object[]{p.getName(), p.getFund()};
            playerTableModel.addRow(playerInfo);
        }
    }

    // MODIFIES: this
    // EFFECTS: edits the selected player's name and fund
    public void editPlayer() {
        Player p = players.get(playerTable.getSelectedRow());
        int initFund = p.getFund();

        int response = JOptionPane.showConfirmDialog(this, "Do you want to edit '" + p.getName()
                + "'?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            String newName = JOptionPane.showInputDialog(this, "Replace '" + p.getName() + "' with what name?");
            String newFund = JOptionPane.showInputDialog(this, "Replace '" + p.getFund() + "' with how much?");
            if (newName != null && newFund != null) {
                p.changeName(newName);
                p.manuallyRemoveFund(initFund);
                p.manuallyAddFund(Integer.parseInt(newFund));
                JOptionPane.showMessageDialog(this, "Player has successfully been changed");
            } else {
                JOptionPane.showMessageDialog(this, "Error 111");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the selected player
    public void deletePlayer() {
        Player p = players.get(playerTable.getSelectedRow());
        String name = p.getName();
        JOptionPane.showMessageDialog(this, name + " has been removed");
        players.remove(playerTable.getSelectedRow());

    }

    // MODIFIES: this
    // EFFECTS: saves the betting app
    private void saveBettingApp() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();

            JOptionPane.showMessageDialog(this, "Saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads betting app from file
    private void loadBettingApp() {
        try {
            game = jsonReader.read();
            bets = game.getBets();
            players = game.getPlayers();

            updateTablePlayer();
            updateTableBet();
            JOptionPane.showMessageDialog(this, "Loaded previous state!");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: closes the app
    public void exitApp() {
        JOptionPane.showMessageDialog(this, "Thank you for coming, see you next time!");
        System.exit(0);
    }


}
