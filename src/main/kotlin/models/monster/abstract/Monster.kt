import kotlinx.serialization.Serializable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@Polymorphic
@JsonClassDiscriminator("class")
abstract class Monster(val name: MonstersName,
                       val type: String,
                       val typeMonsters: TypeMonster,
                       var health: Int = typeMonsters.health,
                       var attack: Int = typeMonsters.attack,
                       var defense: Int = typeMonsters.defense,
                       var specialAttack: Int = typeMonsters.specialAttack,
                       var specialDefense: Int = typeMonsters.specialDefense,
                       var speed: Int = typeMonsters.speed,
                       protected val ability: MonsterAbility = typeMonsters.ability
) {
    companion object {
        const val REDUCTION_PER_ATTACK = 0.6 // A constant used to reduce the damage dealt based on enemy defense
    }

    // Abstract method for saying hello (each monster will have a unique greeting)
    abstract fun sayHello(output: Output)

    // Method to handle defense (how the monster defends against an attack)
    open fun defend(enemyAttack: Int, specialAttack: Boolean): Int {
        if (specialAttack) {
            // If it's a special attack, the defense uses specialDefense
            val dmg = enemyAttack - specialDefense
            return if (dmg < 0) 0 else dmg // Ensure the damage can't go below 0
        } else {
            // Otherwise, normal defense uses defense stat
            val dmg = enemyAttack - defense
            return if (dmg < 0) 0 else dmg // Ensure the damage can't go below 0
        }
    }

    // Method for normal attack (calculates damage after reducing by enemy's defense)
    open fun attack(enemyDefense: Int): Int {
        // Attack reduced by a percentage of enemy's defense
        val dmg = attack - (enemyDefense.toFloat() * REDUCTION_PER_ATTACK).toInt()
        return if (dmg < 0) 0 else dmg // Ensure the damage can't go below 0
    }

    // Method for special attack (calculates damage after reducing by enemy's special defense)
    open fun specialAttack(enemySpecialDefense: Int): Int {
        // Special attack reduced by a percentage of enemy's special defense
        val dmg = specialAttack - (enemySpecialDefense.toFloat() * REDUCTION_PER_ATTACK).toInt()
        return if (dmg < 0) 0 else dmg // Ensure the damage can't go below 0
    }

    // Method to reduce the monster's health when it takes damage
    fun loseHealth(amount: Int) {
        // If the monster's health is less than or equal to the damage amount, set health to 0
        if (health <= amount) health = 0 else health -= amount
    }

    // Method to randomly select an action for the monster: attack, special attack, or defense
    fun getRandomAction(): String = listOf("attack", "specialAttack", "defense").random()
}