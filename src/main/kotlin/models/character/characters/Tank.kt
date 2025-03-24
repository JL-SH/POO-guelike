import kotlinx.serialization.Serializable

@Serializable
class Tank(val name: String): Character(charName = name, TypeCharacter.TANK) {
    // Overriding the defend method to provide custom defense logic for the Tank ability
    override fun defend(enemyAttack: Int, specialAttack: Boolean): Int {

        // If the enemy's attack is a special attack
        if (specialAttack) {
            // Calculate the damage after factoring in the special defense
            val dmg = enemyAttack - specialDefense

            // If the damage is less than 0, return 0 (i.e., no damage taken), else return the calculated damage
            return if (dmg < 0) 0 else dmg
        } else {
            // If it's not a special attack, return 0 damage, since the Tank fully blocks normal attacks
            return 0
        }
    }
}