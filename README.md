## System description

Score board system in which you can start a game, update its score, finish a game and get a summary of all available games.

## Requirements

- Start a game
  - Input: Home team name, Away team name
  - Output: -
  - Result: The new game initialized and added to the scoreboard. 
- Finish agame
  - Input: Home team name, Away team name
  - Output: -
  - Result: The game completely removed from the scoreboard.
- Update score
  - Input: Home team name and score, Away team name and score
  - Output: -
  - Result: The score updated on the scoreboard.
- Get a summary of games:
  - Input: -
  - Output: List of games in the scoreboard, ordered by: total score desc, creation time desc.

## Entities

Game:
- home team name
- away team name
- home team score
- away team score

Scoreboard:
- list of games

I could come up with some other entities (such as `Team` entity), but for the current requirements this is enough.

