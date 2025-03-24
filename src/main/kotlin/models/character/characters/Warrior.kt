import kotlinx.serialization.Serializable

@Serializable
class Warrior(val name: String) : Character(charName = name, TypeCharacter.WARRIOR) {
    // Override the attack method to implement Warrior's attack behavior
    override fun attack(enemyDefense: Int): Int {
        // If the Warrior's health is less than or equal to 50% of max health, the attack damage is doubled
        return if (health <= (health * 0.5)) {
            // Calculate damage by doubling the base attack and reducing it based on the enemy's defense
            val dmg = attack * 2 - (enemyDefense.toFloat() * REDUCTION_PER_ATTACK).toInt()
            // Ensure that the damage is not negative
            if (dmg < 0) 0 else dmg
        } else {
            // Otherwise, use the regular attack method from the parent (Character) class
            super.attack(enemyDefense)
        }
    }
}
