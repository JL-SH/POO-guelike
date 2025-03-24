import com.github.lalyos.jfiglet.FigletFont

class Console : Output {
    private val separator = "════════════════════════════════════════"  // A visual separator used for formatting output

    // Display the title of the game with ASCII art and colors
    override fun showTitle() {
        clearConsole()
        println("\u001B[36m" + centerText(FigletFont.convertOneLine("Welcome")) + "\u001B[0m")
        println("\u001B[35m" + centerText(FigletFont.convertOneLine("To")) + "\u001B[0m")
        println("\u001B[33m" + centerText(FigletFont.convertOneLine("POO-guelike")) + "\u001B[0m")
    }

    // Display a message and prompt for user input
    override fun showInput(msg: String) {
        print("\u001B[32m$msg\u001B[0m")
    }

    // Display a simple message
    override fun showMsg(msg: String) {
        println("\u001B[34m$msg\u001B[0m")
    }

    // Reads a line of input from the user
    override fun readEntry(): String {
        return readLine() ?: ""  // Returns input or empty string if null
    }

    // Reads a number input from the user, or returns -1 if invalid
    override fun readNumber(): Int {
        return readLine()?.toIntOrNull() ?: -1  // Attempts to convert the input to an integer
    }

    // Clears the console screen depending on the OS
    override fun clearConsole() {
        val os = System.getProperty("os.name").lowercase()
        if (os.contains("win")) {
            ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()  // For Windows OS
        } else {
            print("\u001B[H\u001B[2J")  // For UNIX-based OS (clear screen)
            System.out.flush()
        }
    }

    // Display the main menu of the game
    override fun showMainMenu() {
        println("\n\u001B[33m🕹️ MAIN MENU\u001B[0m")
        println(separator)  // Visual separator
        println("1️⃣ 🎮 New Play")
        println("2️⃣ 💾 Load Game")
        println("3️⃣ 🗑️ Delete Saved Game")
        println("4️⃣ 📜 Instructions")
        println("5️⃣ ❌ Exit")
        println(separator)
        showInput("🔽 Select an option: ")
    }

    // Display character selection options
    override fun showCharacters() {
        println("\n\u001B[35mChoose Your Character:\u001B[0m")
        println(separator)
        println("1️⃣  ⚔️ Warrior")
        println("2️⃣  🏹 Archer")
        println("3️⃣  🥷 Thief")
        println("4️⃣  🔮 Wizard")
        println("5️⃣  🛡️ Tank")
        println(separator)
    }

    // Show game instructions
    override fun showInstructions() {
        println("\n📜 \u001B[33mINSTRUCTIONS\u001B[0m")
        println(separator)
        println("- 🏰 Explore dungeons, defeat enemies and collect loot.")
        println("- 🎯 Clear as many dungeons as possible for a high score.")
        println("- ⚠️ Be careful! Danger lurks in every corner.")
        println(separator)
    }

    // Show options after a room has been explored
    override fun showOptionsAfterRoom() {
        println("\n\u001B[36mNext Move:\u001B[0m")
        println(separator)
        println("1️⃣  🚪 Explore the next room.")
        println("2️⃣  💾 Save & Continue.")
        println("3️⃣  💾 Save & Exit.")
        println("4️⃣  🏃 Run Away!")
        println(separator)
        showInput("Choose an option: ")
    }

    // Wait for the user to press Enter to continue
    override fun pressEnter() {
        var input: String?
        do {
            print("\n\u001B[33mPress Enter to continue...\u001B[0m")  // Prompt
            input = readEntry()  // Read user input
        } while (input != "")  // Continue until Enter is pressed
    }

    // Display the health of the character and enemy
    override fun showHealths(enemie: Monster, character: Character) {
        println("\n\u001B[31m❤️ ${character.charName}: ${character.health} HP \u001B[0m | " +
                "\u001B[31m💀 ${enemie.type}: ${enemie.health} HP\u001B[0m")
    }

    // Show combat options for the player to choose from
    override fun showCombatOptions() {
        println("\n⚔️ \u001B[35mCombat Options:\u001B[0m")
        println(separator)
        println("1️⃣  Attack 🗡️")
        println("2️⃣  Special Attack 🔥")
        println("3️⃣  Defend 🛡️")
        println("4️⃣  Use Items 🎒")
        println(separator)
        showInput("> ")
    }

    // Display a custom message with cyan color
    override fun show(x: Any) {
        println("\u001B[36m$x\u001B[0m")
    }

    // Display the stats of the character
    override fun showStats(character: Character) {
        println("\n📊 \u001B[33m${character.charName}'s Stats:\u001B[0m")
        println(separator)
        println("- ❤️ Health: ${character.maxHealth}")
        println("- ⚔️ Attack: ${character.attack}")
        println("- 🛡️ Defense: ${character.defense}")
        println("- 🔥 Special Attack: ${character.specialAttack}")
        println("- 🔮 Special Defense: ${character.specialDefense}")
        println("- ⚡ Speed: ${character.speed}")
        println(separator)
    }

    // Helper function to center text within a specified width
    fun centerText(text: String, width: Int = 80): String {
        val padding = maxOf((width - text.length) / 2, 0)  // Calculate padding to center text
        return " ".repeat(padding) + text  // Return the centered text
    }
}