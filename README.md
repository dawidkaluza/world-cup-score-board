# World Cup Scoreboard

## System description

Scoreboard system in which you can start a game, update its score, finish a game and get a summary of all available games.

Open the project folder in the terminal and run the following command:
```
./gradlew test
```
It will run the tests and generate results as well as coverage reports.

## Requirements

- Start a game
  - Input: Home team name, Away team name
  - Output: -
  - Result: The new game initialized and added to the scoreboard. 
  - Requirements:
    - team names must be valid (non-empty strings)
    - a game can't be started if given teams already play, either against each other or against any other team
- Finish agame
  - Input: Home team name, Away team name
  - Output: -
  - Result: The game being removed from the scoreboard.
  - Requirements:
    - a game can't be finished if it's not present on the scoreboard
- Update score
  - Input: Home team name and score, Away team name and score
  - Output: -
  - Result: The score updated on the scoreboard.
  - Requirements:
    - home and away scores must be positive numbers
    - it's allowed to reduce the score, if such need arises (e.g., goal canceled by VAR, human error)
- Get a summary of games:
  - Input: -
  - Output: List of games on the scoreboard, ordered by: total score desc, creation time desc.

Since it was mentioned to keep the solution simple, I did not include any thread-safety mechanisms in the solution.

## Entities

Game:
- id (home and away teams names)
- home team score
- away team score

Scoreboard:
- list of games

I could come up with some other entities (e.g., `Team`), but for the current requirements, this list is enough.
