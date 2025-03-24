import kotlinx.serialization.Serializable

@Serializable
class Archer (val name: String): Character (charName = name, TypeCharacter.ARCHER) {
    companion object {
        // Defines a constant for doubling the attack or special attack
        private const val DOUBLE_ATTACK = 0.5
    }

    // Override the attack method to implement Archer's attack behavior
    override fun attack(enemyDefense: Int): Int {
        // Call the super (Character) class's attack method and calculate additional damage based on DOUBLE_ATTACK multiplier
        return (super.attack(enemyDefense) + super.attack(enemyDefense) * DOUBLE_ATTACK).toInt()
    }

    // Override the specialAttack method to implement Archer's special attack behavior
    override fun specialAttack(enemySpecialDefense: Int): Int {
        // Call the super (Character) class's specialAttack method and calculate additional damage based on DOUBLE_ATTACK multiplier
        return (super.specialAttack(enemySpecialDefense) + super.specialAttack(enemySpecialDefense) * DOUBLE_ATTACK).toInt()
    }
}