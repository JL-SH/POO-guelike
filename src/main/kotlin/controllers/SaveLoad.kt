import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

class SaveLoad(private val console: Console = Console()) {
    // Directory where saved games will be stored
    private val saveDirectory = "src/main/kotlin/data"

    // Serializer module to handle different monster types
    private val monsterModule = SerializersModule {
        polymorphic(Monster::class) {
            subclass(Goblin::class)
            subclass(Skeleton::class)
            subclass(Spider::class)
            subclass(Dragon::class)
            subclass(DemonKing::class)
        }
    }

    // Serializer module to handle different item types
    private val itemModule = SerializersModule {
        polymorphic(Item::class) {
            subclass(Item.Enchantments::class)
            subclass(Item.Potion::class)
            subclass(Item.Bombs::class)
            subclass(Item.Equipment::class)
        }
    }

    // Serializer module to handle resource types
    private val resourceModule = SerializersModule {
        polymorphic(Resource::class) {
            subclass(Resource.ValuableResources::class)
        }
    }

    // JSON formatter with support for polymorphism
    val jsonFormatter = Json {
        prettyPrint = true
        classDiscriminator = "class"
        serializersModule = SerializersModule {
            include(monsterModule)
            include(itemModule)
            include(resourceModule)
        }
    }

    // Function to save the game state
    fun saveGame(state: GameState) {
        try {
            var fileName: String
            var baseName: String

            // Ask the user for a save name until a valid one is entered
            do {
                console.showMsg("Enter save name (3-15 alphanumeric characters or spaces):")
                fileName = console.readEntry().trim().takeIf { it.isNotEmpty() } ?: "savegame"
                baseName = fileName.removeSuffix(".json")

                if (!Validator.NAME_VALIDATOR.isValid(baseName)) {
                    console.showMsg("Invalid name. Use 3-15 letters, numbers, or spaces")
                }
            } while (!Validator.NAME_VALIDATOR.isValid(baseName))

            // Ensure the filename ends with .json
            val finalFileName = if (fileName.endsWith(".json")) fileName else "$baseName.json"
            val filePath = File(saveDirectory, finalFileName)

            // Convert the game state to a serializable format and save it as JSON
            val serializableState = state.toSerializable()
            val json = jsonFormatter.encodeToString(serializableState)

            // Create the save directory if it doesn't exist and write the save file
            File(saveDirectory).mkdirs()
            filePath.writeText(json)
            console.showMsg("Game saved successfully as: $finalFileName")
        } catch (e: Exception) {
            console.showMsg("Error saving game: ${e.message}")
        }
    }

    // Function to load a saved game
    fun loadGame(): GameState? {
        val savesDir = File(saveDirectory)
        val saves = savesDir.listFiles { _, name -> name.endsWith(".json") }.orEmpty()

        // Check if there are any saved games
        if (saves.isEmpty()) {
            console.showMsg("\n❌ No saved games found.")
            return null
        }

        // Show the list of saved games
        console.showMsg("\n🔄 Select a save to load or enter 0 to return to the main menu:")
        saves.forEachIndexed { index, file ->
            console.showMsg("${index + 1}. ${file.name}")
        }

        // Read user input and load the selected save
        val selectedIndex = console.readNumber()
        if (selectedIndex == 0) {
            console.showMsg("\nReturning to main menu...\n")
            return null
        }
        if (selectedIndex in 1..saves.size) {
            return loadSavedState(saves[selectedIndex - 1].path)
        }
        console.showMsg("\n⚠️ Invalid selection.")
        return null
    }

    // Function to read and decode the saved game state from a file
    private fun loadSavedState(filePath: String): GameState? {
        return try {
            val json = File(filePath).readText()
            val saved = jsonFormatter.decodeFromString<SerializableGameState>(json)

            // Restore the player's inventory and character
            val inventory = saved.inventory.toInventory()
            val character = saved.character.toCharacter().apply {
                this.inventory = inventory
            }

            // Create a GameState object from the saved data
            GameState(
                character = character,
                dungeons = saved.dungeons.map { it.toDungeon() }.toMutableList(),
                currentDungeonIndex = saved.currentDungeonIndex,
                runAway = saved.runAway,
                exit = saved.exit,
                defeated = saved.defeated,
                inventory = inventory
            )
        } catch (e: Exception) {
            console.showMsg("Error loading: ${e.stackTraceToString()}")
            null
        }
    }

    // Function to delete a saved game
    fun deleteGame() {
        val savesDir = File(saveDirectory)
        val saves = savesDir.listFiles { _, name -> name.endsWith(".json") }.orEmpty()

        // Check if there are any saved games
        if (saves.isEmpty()) {
            console.showMsg("\n❌ No saved games found.")
            return
        }

        // Show the list of saved games
        console.showMsg("\n🗑️ Select a save to delete or enter 0 to return to the main menu:")
        saves.forEachIndexed { index, file ->
            console.showMsg("${index + 1}. ${file.name}")
        }

        // Read user input and delete the selected save
        val selectedIndex = console.readNumber()
        if (selectedIndex == 0) {
            console.showMsg("\nReturning to main menu...\n")
            return
        }
        if (selectedIndex in 1..saves.size) {
            val fileToDelete = saves[selectedIndex - 1]
            if (fileToDelete.delete()) {
                console.showMsg("\n✅ Save deleted successfully.")
            } else {
                console.showMsg("\n❌ Failed to delete save.")
            }
        } else {
            console.showMsg("\n⚠️ Invalid selection.")
        }
    }
}