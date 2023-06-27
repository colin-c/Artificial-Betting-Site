# Bet Haven

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



## Phase 4: Task 2

Wed Apr 12 22:20:39 PDT 2023
Player 'Cloin C' has been created with $1000

Wed Apr 12 22:20:45 PDT 2023
Player 'Alu' has been created with $500

Wed Apr 12 22:20:54 PDT 2023
Alu has changed its name to Alu W

Wed Apr 12 22:20:54 PDT 2023
$500 removed from Alu W's account

Wed Apr 12 22:20:54 PDT 2023
$1000 added to Alu W's account

Wed Apr 12 22:21:03 PDT 2023
'Bet One' has been created

Wed Apr 12 22:21:12 PDT 2023
'Bet One' has changed its title to 'First Bet'

Wed Apr 12 22:21:24 PDT 2023
'empty' has changed its description to 'Both players are betting $500'

Wed Apr 12 22:21:29 PDT 2023
$500 removed from Cloin C's account

Wed Apr 12 22:21:29 PDT 2023
Cloin C has been added this bet 'First Bet'

Wed Apr 12 22:21:29 PDT 2023
The pot for 'First Bet' is now $500

Wed Apr 12 22:21:35 PDT 2023
$500 removed from Alu W's account

Wed Apr 12 22:21:35 PDT 2023
Alu W has been added this bet 'First Bet'

Wed Apr 12 22:21:35 PDT 2023
The pot for 'First Bet' is now $500

Wed Apr 12 22:21:43 PDT 2023
Cloin C has won the bet 'First Bet'

Wed Apr 12 22:21:43 PDT 2023
$1000 added to Cloin C's account



## Phase 4: Task 3
Looking back at this project, there would have been quite a few things I would have done
differently in order to improve the design. One major change would be to split up the BetGUI class
into multiple subclasses. Rather than having the entire GUI code in a single class, I would split it up into many more
classes, preferably having each side panel as its own class and perhaps instantiating some interfaces or abstract classes
for the different panels within those side panels, as many of them share common elements. 
This way, I am able to implement the Single Responsibility Principle, which can help with 
maintainability and extensibility of my project.

Another change I would do is removing the Game class entirely. The only reason I had to add a third model class was because I
could not find a way to save the project into a JSON file without having to create a "new" object to umbrella my lists of bets
and players. If I had more time, I could have perhaps debugged the issue without have to create unnecessary additions to my project. 
However, I may also not conduct such a change as my original idea was to create other game modes for the user, meaning this 
Game class would eventually be implemented had I follow through with that idea.

One more minor change I would have done is kept the consistency with the BettingApp console class. In the backend aspect 
of the project, I could have kept the consistency of having the user either input a string or type an index to follow through
with an action rather than having both. Although this would not have changed much, it would have kept the project cleaner and
consistent, which is an important aspect when it comes to the programming world.
