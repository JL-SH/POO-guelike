class Game(private val output: Output = Console()) {
    fun mainMenu() {
        var exit = false
        output.showTitle()
        output.pressEnter()
        // Main Menu
        do {
            output.clearConsole()
            output.showMainMenu()
            when (output.readNumber()) {
                // Invalid option
                -1 -> {
                    output.showMsg("\n⚠️ Invalid option. Please try again.\n")
                    output.pressEnter()
                }
                // New play
                1 -> {
                    output.clearConsole()
                    newGame()
                }
                // Load Game
                2 -> {
                    output.clearConsole()
                    output.showMsg("\n🔄 Loading game...\n")
                    val saveGame = SaveLoad().loadGame()
                    if (saveGame != null) {
                        playGame(saveGame)
                    } else {
                        output.showMsg("\n❌ Failed to load the game. Returning to the main menu...\n")
                        output.pressEnter()
                    }
                }
                // Delete Game
                3 -> {
                    output.clearConsole()
                    SaveLoad().deleteGame()
                    output.pressEnter()
                }
                // Show Instructions
                4 -> {
                    output.clearConsole()
                    output.showInstructions()
                    output.pressEnter()
                }
                // Exit
                5 -> {
                    output.clearConsole()
                    output.showMsg("\n👋 Bye bye...\n")
                    exit = true
                }
            }
        } while (!exit)
    }

    private fun askName(): String {
        while (true) {
            output.clearConsole()
            output.showMsg("\n📝 Enter your name (5-13 alphanumeric characters):")
            output.showInput("> ")
            val name = output.readEntry()
            if (isValidCharacterName(name)) return name
            output.showMsg("\n⚠️ Invalid name. Please try again.\n")
            output.pressEnter()
        }
    }

    private fun chooseCharacter(name: String): Character {
        output.clearConsole()
        output.showCharacters()
        output.showMsg("\n🎭 Choose your character:")
        output.showInput("> ")
        do {
            when (output.readNumber()) {
                1 -> return Warrior(name)
                2 -> return Archer(name)
                3 -> return Thief(name)
                4 -> return Wizard(name)
                5 -> return Tank(name)
                else -> {
                    output.showMsg("\n⚠️ Invalid option. Try again.\n")
                    output.pressEnter()
                }
            }
        } while (true)
    }

    private fun playGame(state: GameState) {
        // Start the game loop
        do {
            // Get the current dungeon based on the game state
            val currentDungeon = state.dungeons[state.currentDungeonIndex]

            // Display dungeon information to the player
            output.clearConsole()
            output.showMsg("\n🏰 You enter Dungeon Level: ${currentDungeon.level}\n")
            output.showMsg("📍 Location: ${currentDungeon.name}\n")
            output.showMsg("🚪 Room ${currentDungeon.getCurrentRoomIndexPlusOne()} -> ${currentDungeon.getCurrentRoom()}\n")
            output.pressEnter()

            // Explore the current room and get the result (true if successful, false if defeated)
            val roomResult = currentDungeon.rooms[currentDungeon.getCurrentRoomIndexPlusOne()]
                .exploreRoom(state.character, output)

            // If the room exploration is successful
            if (roomResult) {
                // Ask the player what to do next and act accordingly
                when (chooseOptionAfterRoom()) {
                    1 -> { // Move to the next room
                        output.clearConsole()
                        output.showMsg("\n➡️ ${state.character.charName} advances to the next room...\n")
                        output.pressEnter()
                        currentDungeon.nextRoom()
                    }
                    2 -> { // Move to the next room and save the game
                        currentDungeon.nextRoom()
                        SaveLoad().saveGame(state)
                        output.clearConsole()
                        output.showMsg("\n💾 Game saved. ${state.character.charName} advances to the next room...\n")
                        output.pressEnter()
                    }
                    3 -> { // Save the game and return to the main menu
                        currentDungeon.nextRoom()
                        SaveLoad().saveGame(state)
                        output.clearConsole()
                        output.showMsg("\n💾 Game saved. Returning to main menu...\n")
                        output.pressEnter()
                        state.exit = true
                    }
                    4 -> { // Run away from the dungeon
                        output.clearConsole()
                        output.showMsg("\n🏃 ${state.character.charName} runs away from the dungeon like a chicken...\n")
                        output.pressEnter()
                        state.runAway = true
                    }
                }
            } else {
                // If the player is defeated, update the game state
                state.defeated = true
            }

            // Continue looping until the player runs away, exits, or is defeated
        } while (!state.runAway && !state.exit && !state.defeated)

        // If the player runs away or is defeated, show inventory resources
        if (state.runAway || state.defeated) {
            output.clearConsole()
            state.character.inventory.showResourcesAndGetTotal(output)
            output.pressEnter()
        }
    }


    private fun chooseOptionAfterRoom(): Int {
        output.clearConsole()
        output.showOptionsAfterRoom()
        return output.readNumber()
    }

    private fun newGame() {
        val character = chooseCharacter(askName())
        // Create the initial game state with a new dungeon and default values
        val initialState = GameState(
            character = character,               // The player's character
            dungeons = mutableListOf(Dungeon()), // Start with one dungeon
            currentDungeonIndex = 0,             // The first dungeon
            runAway = false,                     // Player has not run away
            exit = false,                         // Player has not exited
            defeated = false,                     // Player is not defeated
            inventory = character.inventory       // Copy the character's inventory
        )
        output.clearConsole()
        output.showMsg("\n📖 This was the beginning of your story, ${character.charName}...\n")
        output.pressEnter()
        playGame(initialState)
    }

    fun isValidCharacterName(name: String): Boolean {
        return Validator.NAME_VALIDATOR.isValid(name)
    }
}