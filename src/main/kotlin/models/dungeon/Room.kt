import kotlin.random.Random
import kotlinx.serialization.Serializable

@Serializable
class Room(val boss: Boolean) {
    val name = RoomName.entries.random() // Randomly pick a room name from RoomName enum
    val enemies: List<Monster>           // List of monsters in the room
    val loot: Pair<List<Item>, List<Resource>>  // Loot generated for the room

    init {
        enemies = generateEnemies(boss) // Generate enemies based on whether it's a boss room
        loot = lootGenerator()          // Generate loot for the room
    }

    // Generate a list of enemies depending on whether it's a boss room or not
    private fun generateEnemies(boss: Boolean): List<Monster> {
        return if (boss) listOf(generateBoss()) else List(Random.nextInt(2, 5)) { generateMonster() }
    }

    // Generate a random monster for the room (could be Goblin, Skeleton, or Spider)
    private fun generateMonster(): Monster {
        return when (Random.nextInt(1, 4)) {
            1 -> Goblin()
            2 -> Skeleton()
            3 -> Spider()
            else -> Goblin()
        }
    }

    // Generate a boss monster (either Dragon or DemonKing)
    private fun generateBoss(): Monster {
        return when (Random.nextInt(1, 3)) {
            1 -> Dragon()
            2 -> DemonKing()
            else -> Dragon()
        }
    }

    // Generate loot consisting of items and resources
    private fun lootGenerator(): Pair<List<Item>, List<Resource>> {
        val items = generateItems()
        val resources = generateResources()
        return Pair(items, resources)
    }

    // Generate a random list of items based on their probability
    private fun generateItems(): List<Item> {
        val generatedItems = mutableListOf<Item>()
        for (item in Item.allItems) {
            if (Random.nextInt(0, 100) < item.probability) {
                generatedItems.add(item)
            }
        }
        return generatedItems
    }

    // Generate a random list of resources based on their probability
    private fun generateResources(): List<Resource> {
        val generatedResources = mutableListOf<Resource>()
        for (resource in Resource.allResources) {
            if (Random.nextInt(0, 100) < resource.probability) {
                generatedResources.add(resource)
            }
        }
        return generatedResources
    }

    // Main function that simulates the exploration of the room
    fun exploreRoom(character: Character, output: Output): Boolean {
        var defeated = false

        output.clearConsole()
        output.showMsg("\n===== Exploring the Room =====\n")
        output.pressEnter()

        // Display message for looking for loot
        output.showMsg("Looking around for loot...\n")
        output.pressEnter()

        // Fight the enemies in the room
        for (monster in enemies) {
            output.clearConsole()
            output.showMsg("\nA ${monster.type} appears and tries to attack you!\n")
            output.pressEnter()

            // The monster introduces itself
            monster.sayHello(output)

            // Start combat, and if the player is defeated, stop the exploration
            if (!Combat(monster, character, output).startCombat()) {
                defeated = true
                break
            }
        }

        // If the player survived, loot the room
        if (!defeated) {
            output.showMsg("\nThe room is now safe, you can loot freely.\n")
            output.pressEnter()
            lootRoom(character, output)
            return true
        } else {
            return false
        }
    }

    // Handle looting the room (add items and resources to character's inventory)
    private fun lootRoom(character: Character, output: Output) {
        output.clearConsole()
        output.showMsg("\n===== Looting the Room =====\n")
        output.pressEnter()

        // Show items found
        output.showMsg("\nYou found the following loot:\n")
        output.pressEnter()

        output.showMsg("📦 Items Found:")
        if (loot.first.isEmpty()) {
            output.showMsg("- No items found.")
        } else {
            loot.first.forEach { item ->
                output.showMsg("- ${item.name}")
                character.inventory.addItem(item, output)
                // If the character is a Thief, they loot extra items
                if (character.typeCharacter == TypeCharacter.THIEF) {
                    character.inventory.addItem(item, output)
                }
            }
        }

        output.pressEnter()

        // Let the character improve skills if applicable
        character.improveSkills(output)

        // Show resources found
        output.showMsg("\n🔨 Resources Found:")
        if (loot.second.isEmpty()) {
            output.showMsg("- No resources found.")
        } else {
            loot.second.forEach { resource ->
                output.showMsg("- ${resource.name}")
                character.inventory.addResource(resource, output)
                // If the character is a Thief, they loot extra resources
                if (character.typeCharacter == TypeCharacter.THIEF) {
                    character.inventory.addResource(resource, output)
                }
            }
        }

        output.pressEnter()

        // Show updated inventory after looting
        output.showMsg("\nYou collect everything and check your inventory...\n")
        output.pressEnter()

        output.clearConsole()
        output.showMsg("\n===== Updated Inventory =====\n")
        character.inventory.showItems(output)
        character.inventory.showResources(output)
    }
}
