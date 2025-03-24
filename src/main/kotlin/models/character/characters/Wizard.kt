import kotlinx.serialization.Serializable

@Serializable
class Wizard(val name: String): Character(charName = name, TypeCharacter.WIZARD) {
    companion object {
        // Constant for healing percentage per attack
        private const val HEALING_PER_ATTACK_PERCENT = 0.15
    }

    // Override the attack method to implement Wizard's attack behavior
    override fun attack(enemyDefense: Int): Int {
        // Calculate the damage by reducing the enemy's defense from the Wizard's attack
        val dmg = attack - (enemyDefense.toFloat() * REDUCTION_PER_ATTACK).toInt()

        // Heal the Wizard by a percentage of the damage dealt (15% of attack damage)
        heal((dmg * HEALING_PER_ATTACK_PERCENT).toInt())

        // Ensure that the damage is not negative
        return if (dmg < 0) 0 else dmg
    }

    // Override the specialAttack method to implement Wizard's special attack behavior
    override fun specialAttack(enemySpecialDefense: Int): Int {
        // Calculate the special attack damage by reducing the enemy's special defense
        val dmg = specialAttack - (enemySpecialDefense.toFloat() * REDUCTION_PER_ATTACK).toInt()

        // Heal the Wizard by a percentage of the special attack damage (15% of special attack damage)
        heal((dmg * HEALING_PER_ATTACK_PERCENT).toInt())

        // Ensure that the damage is not negative
        return if (dmg < 0) 0 else dmg
    }
}