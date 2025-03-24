// Converts the GameState to a Serializable version
fun GameState.toSerializable(): SerializableGameState {
    return SerializableGameState(
        character = this.character.toState(), // Convert character to its state representation
        dungeons = this.dungeons.map { it.toState() }, // Convert each dungeon to its state representation
        currentDungeonIndex = this.currentDungeonIndex, // Keep track of the current dungeon index
        runAway = this.runAway, // Boolean indicating if the player chose to run away
        exit = this.exit, // Boolean indicating if the player exited
        defeated = this.defeated, // Boolean indicating if the player is defeated
        inventory = this.inventory.toState() // Convert the inventory to its state representation
    )
}

// Converts a Character to its state representation
fun Character.toState(): CharacterState {
    return CharacterState(
        name = this.charName, // Character's name
        typeCharacterName = this.typeCharacter.name, // Character's type (e.g., WARRIOR, WIZARD)
        maxHealth = this.maxHealth, // Max health of the character
        health = this.health, // Current health
        attack = this.attack, // Character's attack value
        defense = this.defense, // Character's defense value
        specialAttack = this.specialAttack, // Special attack value
        specialDefense = this.specialDefense, // Special defense value
        speed = this.speed, // Speed value
        ability = this.ability, // Character's ability
        inventory = this.inventory.toState(), // Convert the inventory to its state representation
        damageMultiplier = this.damageMultiplier, // Multiplier for damage
        multiplierTurns = this.multiplierTurns // Turns for which multiplier is active
    )
}

// Converts a Dungeon to its state representation
fun Dungeon.toState(): DungeonState {
    return DungeonState(
        name = this.name, // Name of the dungeon
        level = this.level, // Dungeon level
        rooms = this.rooms.toList(), // List of rooms in the dungeon
        currentRoomIndex = this.currentRoomIndex // Index of the current room
    )
}

// Converts DungeonState back to a Dungeon
fun DungeonState.toDungeon(): Dungeon {
    return Dungeon(
        name = this.name, // Name of the dungeon
        level = this.level, // Dungeon level
        rooms = this.rooms.toMutableList(), // Convert rooms back to mutable list
        currentRoomIndex = this.currentRoomIndex // Current room index
    )
}

// Converts a CharacterState to the corresponding Character class (e.g., Warrior, Wizard, etc.)
fun CharacterState.toCharacter(): Character {
    val typeCharacter = typeCharacterName.toTypeCharacter() // Convert typeCharacterName back to TypeCharacter enum
    return when (typeCharacter) {
        TypeCharacter.WARRIOR -> Warrior(name).apply { applyState(this@toCharacter) } // Apply state to Warrior
        TypeCharacter.WIZARD -> Wizard(name).apply { applyState(this@toCharacter) } // Apply state to Wizard
        TypeCharacter.TANK -> Tank(name).apply { applyState(this@toCharacter) } // Apply state to Tank
        TypeCharacter.THIEF -> Thief(name).apply { applyState(this@toCharacter) } // Apply state to Thief
        TypeCharacter.ARCHER -> Archer(name).apply { applyState(this@toCharacter) } // Apply state to Archer
    }
}

// Helper function to apply state to a character
private fun Character.applyState(state: CharacterState) {
    health = state.health
    attack = state.attack
    defense = state.defense
    specialAttack = state.specialAttack
    specialDefense = state.specialDefense
    speed = state.speed
    inventory = state.inventory.toInventory() // Convert inventory state back to Inventory
    damageMultiplier = state.damageMultiplier
    multiplierTurns = state.multiplierTurns
}

// Converts Inventory to its state representation
fun Inventory.toState(): InventoryState {
    return InventoryState(
        items = this.getAllItems(), // All items in the inventory
        resources = this.resources.toList() // All resources in the inventory
    )
}

// Converts InventoryState back to Inventory
fun InventoryState.toInventory(): Inventory {
    val inventory = Inventory()
    this.items.forEach { inventory.addItem(it, Console()) } // Add items back to the inventory
    this.resources.forEach { inventory.addResource(it, Console()) } // Add resources back to the inventory
    return inventory
}

// Converts a String to a TypeCharacter enum
fun String.toTypeCharacter(): TypeCharacter {
    return TypeCharacter.valueOf(this) // Converts the string to the corresponding TypeCharacter enum value
}