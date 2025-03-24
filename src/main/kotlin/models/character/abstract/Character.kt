import kotlinx.serialization.Serializable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@Polymorphic
@JsonClassDiscriminator("class")
abstract class Character(
    val charName: String,  // Name of the character
    val typeCharacter: TypeCharacter,  // Type of character (defines base stats)
    var maxHealth: Int = typeCharacter.health,  // Maximum health of the character
    var attack: Int = typeCharacter.attack,  // Basic attack stat
    var defense: Int = typeCharacter.defense,  // Basic defense stat
    var specialAttack: Int = typeCharacter.specialAttack,  // Special attack stat
    var specialDefense: Int = typeCharacter.specialDefense,  // Special defense stat
    var speed: Int = typeCharacter.speed,  // Character's speed
    val ability: Ability = typeCharacter.ability  // Special ability of the character
) {
    companion object {
        private const val MULTIPLIER_DURATION = 3  // Number of turns a damage multiplier lasts
        const val REDUCTION_PER_ATTACK = 0.6  // Factor used to calculate damage reduction from defense
    }

    var health: Int = maxHealth  // Current health of the character
    var inventory = Inventory()  // Character's inventory
    var damageMultiplier: Int = 1  // Multiplier for damage dealt
    var multiplierTurns: Int = 0  // Remaining turns for the multiplier effect

    // Basic attack function: calculates damage based on enemy defense
    open fun attack(enemyDefense: Int): Int {
        val dmg = attack - (enemyDefense.toFloat() * REDUCTION_PER_ATTACK).toInt()
        return if (dmg < 0) 0 else dmg  // Ensure damage is never negative
    }

    // Special attack function: similar to basic attack but uses special stats
    open fun specialAttack(enemySpecialDefense: Int): Int {
        val dmg = specialAttack - (enemySpecialDefense.toFloat() * REDUCTION_PER_ATTACK).toInt()
        return if (dmg < 0) 0 else dmg
    }

    // Defense function: reduces incoming damage based on either defense or special defense
    open fun defend(enemyAttack: Int, specialAttack: Boolean): Int {
        return if (specialAttack) {
            val dmg = enemyAttack - specialDefense
            if (dmg < 0) 0 else dmg
        } else {
            val dmg = enemyAttack - defense
            if (dmg < 0) 0 else dmg
        }
    }

    // Improves the character's stats based on equipped items
    fun improveSkills(output: Output) {
        val equipments = inventory.getEquipments()
        equipments.forEach { equipment ->
            when (equipment.stat) {
                StatType.HEALTH -> maxHealth += equipment.boost
                StatType.ATTACK -> attack += equipment.boost
                StatType.DEFENSE -> defense += equipment.boost
                StatType.SPECIAL_ATTACK -> specialAttack += equipment.boost
                StatType.SPECIAL_DEFENSE -> specialDefense += equipment.boost
                StatType.SPEED -> speed += equipment.boost
            }
        }
        output.showStats(this)  // Display updated stats
        inventory.removeEquipments(equipments)  // Remove used equipment
    }

    // Heals the character, but does not exceed max health
    fun heal(amount: Int) {
        health = if ((health + amount) > maxHealth) maxHealth else health + amount
    }

    // Reduces health, ensuring it does not go below zero
    fun loseHealth(amount: Int) {
        health = if (health <= amount) 0 else health - amount
    }

    // Reduces the remaining turns for the damage multiplier effect
    fun updateMultiplier() {
        if (multiplierTurns > 0) {
            multiplierTurns--
        }
    }

    // Applies a damage multiplier effect for a limited number of turns
    fun applyDamageMultiplier(multiplier: Int) {
        this.damageMultiplier = multiplier
        this.multiplierTurns = MULTIPLIER_DURATION
    }
}
