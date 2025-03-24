class Combat(private val enemy: Monster, private val character: Character, private val output: Output = Console()) {
    // Tracking the state of the combat
    private var playerDefendedLastTurn: Boolean = false
    private var monsterDefendedLastTurn: Boolean = false
    private var turn: Int = 1
    private var playerDefending: Boolean = false
    private var monsterDefending: Boolean = false

    // Start combat
    fun startCombat(): Boolean {
        var combatEnded = false  // Flag to check if the combat has ended

        // Main combat loop
        while (!combatEnded && !isCombatOver()) {
            output.clearConsole()
            output.show("\n===== Turn $turn =====\n")
            output.showHealths(enemy, character) // Display the health of both characters

            var playerAction = getPlayerAction() // Get the player's action
            var usedInventory = false

            // Handle inventory usage
            while (playerAction == "inventory") {
                usedInventory = true
                if (useInventory()) {  // If the player uses an item that ends the combat
                    combatEnded = true
                    break
                }
                playerAction = getPlayerAction() // Ask for a new action
            }
            if (combatEnded) break // Exit if combat has ended

            if (usedInventory) continue // Skip turn processing if inventory was used

            handlePlayerAction(playerAction) // Process player's action

            if (isCombatOver()) break // Check if combat has ended after player's turn

            val monsterAction = enemy.getRandomAction() // Enemy selects a random action
            handleMonsterAction(monsterAction) // Process enemy's action

            val playerFirst = determinePriority(playerAction, monsterAction)
            // Determine who acts first

            output.clearConsole()
            output.show("\n=== Actions in Turn $turn ===\n")

            // Apply actions based on priority
            if (playerFirst) {
                applyAction(playerAction, character, enemy, monsterDefending)
                if (isCombatOver()) break
                applyAction(monsterAction, enemy, character, playerDefending)
            } else {
                applyAction(monsterAction, enemy, character, playerDefending)
                if (isCombatOver()) break
                applyAction(playerAction, character, enemy, monsterDefending)
            }

            // Update defense states and multipliers
            playerDefendedLastTurn = playerDefending
            monsterDefendedLastTurn = monsterDefending
            playerDefending = false
            monsterDefending = false
            character.updateMultiplier()
            turn++

            output.pressEnter() // Wait for user input before continuing
        }

        output.clearConsole()
        output.showHealths(enemy, character) // Display final healths

        // Determine and display the final combat result
        return if (character.health <= 0) {
            output.show("\nYou have been defeated...\n")
            output.pressEnter()
            false
        } else {
            output.show("\nYou have defeated the monster!\n")
            output.pressEnter()
            true
        }
    }

    // Prompts the player for an action during their turn
    private fun getPlayerAction(): String {
        output.showCombatOptions()
        while (true) {
            when (output.readNumber()) {
                1 -> return "attack"
                2 -> return "specialAttack"
                3 -> return "defense"
                4 -> return "inventory"
                else -> output.show("\nInvalid option...\n")
            }
        }
    }

    // Handles the player's action, setting whether they are defending
    private fun handlePlayerAction(action: String) {
        playerDefending = action == "defense"
    }

    // Handles the monster's action, setting whether it's defending
    private fun handleMonsterAction(action: String) {
        monsterDefending = action == "defense"
    }

    // Determines priority for turn order based on actions and speed
    private fun determinePriority(playerAction: String, monsterAction: String): Boolean {
        return when {
            playerAction == "defense" && monsterAction != "defense" -> false
            monsterAction == "defense" && playerAction != "defense" -> true
            else -> character.speed > enemy.speed
        }
    }

    // Applies the given action from the attacker to the defender
    private fun applyAction(action: String, attacker: Character, defender: Monster, isDefending: Boolean) {
        when (action) {
            "attack", "specialAttack" -> {
                var damage = if (action == "attack") {
                    attacker.attack(defender.defense) // Calculate attack damage
                } else {
                    attacker.specialAttack(defender.specialDefense) // Calculate special attack damage
                }

                // Check if the player defended last turn and apply a damage multiplier
                if (playerDefendedLastTurn) {
                    damage = (damage * 1.5).toInt()
                    output.show("\n${attacker.charName} and deals 1.5x damage!\n")
                }

                // Check if the defender is defending and apply defense logic
                if (isDefending) {
                    if (!playerDefendedLastTurn) {
                        damage = defender.defend(
                            if (action == "attack") attacker.attack else attacker.specialAttack,
                            action != "attack"
                        )
                        output.show("\n${defender.name} is defending! Damage reduced to $damage.\n")
                    } else {
                        output.show("\n${defender.name} cannot defend because ${attacker.charName} has priority!\n")
                    }
                }

                defender.loseHealth(damage) // Apply damage to the defender
                output.show("\n${attacker.charName} attacks ${defender.name} for $damage damage.\n")
            }
            "defense" -> {
                output.show("\n${attacker.charName} defends.\n") // Show defense action
            }
        }
    }

    // Applies the given action from the attacker to the defender (for monsters vs. characters)
    private fun applyAction(action: String, attacker: Monster, defender: Character, isDefending: Boolean) {
        when (action) {
            "attack", "specialAttack" -> {
                var damage = if (action == "attack") {
                    attacker.attack(defender.defense) // Calculate attack damage
                } else {
                    attacker.specialAttack(defender.specialDefense) // Calculate special attack damage
                }

                // Check if the monster defended last turn and apply a damage multiplier
                if (monsterDefendedLastTurn) {
                    damage = (damage * 1.5).toInt()
                    output.show("\n${attacker.name} has priority and deals 1.5x damage!\n")
                }

                // Check if the defender is defending and apply defense logic
                if (isDefending) {
                    if (!monsterDefendedLastTurn) {
                        damage = defender.defend(
                            if (action == "attack") attacker.attack else attacker.specialAttack,
                            action != "attack"
                        )
                        output.show("\n${defender.charName} is defending! Damage reduced to $damage.\n")
                    } else {
                        output.show("\n${defender.charName} cannot defend because ${attacker.name} has priority!\n")
                    }
                }

                defender.loseHealth(damage) // Apply damage to the defender
                output.show("\n${attacker.name} attacks ${defender.charName} for $damage damage.\n")
            }
            "defense" -> {
                output.show("\n${attacker.name} defends.\n") // Show defense action
            }
        }
    }

    // Checks if the combat is over (either the player or enemy's health is 0 or less)
    private fun isCombatOver(): Boolean {
        return character.health <= 0 || enemy.health <= 0
    }

    // Handles the player's inventory usage during combat
    private fun useInventory(): Boolean {
        // Check if the inventory is empty
        if (character.inventory.getAllItems().isEmpty()) {
            // Inform the player that the inventory is empty if there are no items
            output.show("\nYour inventory is empty.\n")
            output.pressEnter() // Wait for the player to acknowledge the message
            return false // Return false since no items can be used
        }

        // Inform the player that they are accessing their inventory
        output.show("\nAccessing inventory...\n")
        // Show the list of items in the player's inventory
        character.inventory.showItems(output)

        // Read the player's input to select an item from the inventory
        val input = output.readNumber()

        // Check if the selected input is valid (within the range of available items)
        if (input > 0 && input <= character.inventory.getAllItems().size) {
            // Get the selected item from the inventory
            val selectedItem = character.inventory.getAllItems()[input - 1]

            // Handle the different types of items in the inventory
            when (selectedItem) {
                // If the selected item is a Potion
                is Item.Potion -> {
                    // Heal the player by the amount defined in the potion
                    character.heal(selectedItem.healing)
                    // Inform the player that the potion was used and show how much health was restored
                    output.show("\n${character.charName} used ${selectedItem.name} and healed ${selectedItem.healing} HP!\n")
                    // Remove the item from the inventory since it has been used
                    character.inventory.useItem(selectedItem)
                }

                // If the selected item is a Bomb
                is Item.Bombs -> {
                    // Deal damage to the enemy as defined by the bomb
                    enemy.loseHealth(selectedItem.damage)
                    // Inform the player that the bomb was used and how much damage was dealt
                    output.show("\n${character.charName} used ${selectedItem.name} and dealt ${selectedItem.damage} damage to ${enemy.name}!\n")
                    // Remove the bomb from the inventory since it has been used
                    character.inventory.useItem(selectedItem)

                    // Check if the combat is over after using the bomb
                    if (isCombatOver()) {
                        // Inform the player that the enemy has been defeated
                        output.show("\n${enemy.name} has been defeated!\n")
                        return true // Return true indicating the combat has ended
                    }
                }

                // If the selected item is an Enchantment
                is Item.Enchantments -> {
                    // Apply the damage multiplier effect from the enchantment
                    character.applyDamageMultiplier(selectedItem.multiplier)
                    // Inform the player that the enchantment was used and the multiplier applied
                    output.show("\n${character.charName} used ${selectedItem.name} and gained a x${selectedItem.multiplier} damage multiplier!\n")
                    // Remove the enchantment from the inventory since it has been used
                    character.inventory.useItem(selectedItem)
                }

                // If the selected item is not usable at the moment
                else -> {
                    // Inform the player that this item cannot be used right now
                    output.show("\nThis item cannot be used right now.\n")
                }
            }
        } else {
            // Inform the player if the input is invalid (out of range)
            output.show("\nInvalid selection.\n")
        }

        // Wait for the player to acknowledge the message before returning
        output.pressEnter()
        // Return false since the inventory was used but the combat is not yet over
        return false
    }
}