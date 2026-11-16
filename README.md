# APCS Dungeon Crawler — ASCII Roguelike in Java

An archival upload of a game I made for fun in 2024, during my senior year of high school. I built it as a personal challenge to apply as many concepts from my AP Computer Science class as I could.

## Description

This is an ASCII roguelike that processes each game frame after a player input (turn-based).

- **Procedural dungeon:** The map is randomly generated, with rooms that have random layouts, enemy behavior, and fight rewards.
- **Items:** The player can equip items to change their fight choices, restore health, boost stats, and alter their character's appearance in the stats screen.
- **Goal:** The dungeon has a single floor with a start room and an end room with a randomly generated path between them. The game concludes when the player defeats the boss in the final room.

## Running the Game

Requires a Java Development Kit (JDK) 

1. Download all files from this repository.
2. Run `GameRunner.java`.

From a terminal, in the folder containing the files:

```
javac *.java
java GameRunner
```
Or press the run button in VSCode while GameRunner.java is selected
