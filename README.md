# Artificial Gambling Site

## Functionality

An artificial gambling site that focuses on player betting. Similar to sports betting, users
can add *players* and place bets, winning or losing their imaginary funds based on the 
number of users and total pot.

Possible Features:
- Create events for people to be hold bets
- Create unique player profiles for different players to use
- Wager funds against friends in hopes to win the grand jackpot


## Inspiration

Gambling in real life isn't a very healthy hobby. However,
it is always more fun to play with something at stake, whether that's
gambling on your favourite team or local bets between friends.
Thus, an artificial gambling sites can give the excitement
that gambling provides without the harm of it. The app will be
helpful in creating a safe yet competitive environment 
for peers.

## Targeted Audience 

This application would be useful for anyone, everyone. However, those who
are more competitive than others tend to hold things at stake for an even more 
exiting turn of events.



# User Stories

- As a user, I want to be able to add funds to my account
- As a user, I want to be able to remove funds from my account
- As a user, I want to be able to adjust the funds on my account
- As a user, I want to be able to gamble my funds on a game
- As a user, I want to be able to create a person profile where I can
  use to gamble with others
- As a user, I want to be able to create bets and add players to those bets
- As a user, I want to be able to select which player wins the bet and takes the entire pot

- As a user, I want to be able to save all my current players and bets
- As a user, I want to be able to load my previously saved files


# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by first going to the player tab. Press the create
  player button and type in a string for the name and an integer for the starting fund. Now go to the bet tab and press the
  create bet button to create a new bet. Type in strings for both the title and descriptions. Once a bet is made, go press the 
  edit bet button and then the add player button. Double-click on the row of the player you wish to add and type in the integer
  of the amount of fund you would like it to bet.
- You can generate the second required action related to adding Xs to a Y by going back to the player tab. Here, you have
  the option to edit or delete an existing player. Pressing the edit button will offer changes to name and funds of the
  selected player. Pressing the delete button will remove the selected player. Same thing can be done with the bet tab and 
  each of the individual bets (i.e. edit, remove).
- You can locate my visual component by looking at the home tab. You can find another visual component by pressing the winner??
  button in the bet's tab. (NOTE: a bet must first be made with at least one player in it) After confirming a winner, then another
  visual component will appear.
- You can save the state of my application by pressing the save button located in the home tab. NOTE: pressing save will
  overwrite the previous save file as it can only maintain a single save file at a time.
- You can reload the state of my application by pressing the load button located in the home tab.