import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@Polymorphic
@JsonClassDiscriminator("class")
sealed class Item {
    abstract val name: String
    abstract val probability: Int

    companion object {
        // A list that will hold all instances of the different types of items
        val allItems: List<Item> = addAllItems()

        // A private function that creates and returns a list of all the items
        private fun addAllItems(): List<Item> {
            return listOf(
                // Enchantments: Items that modify the damage multiplier
                Enchantments("MagicRage", 2, "Gain x2 damage multiplier", 25),
                // Potion: Items that heal the player
                Potion("HealthPotion", 20, "Heals you 20 health", 55),
                // Bombs: Items that deal damage to enemies
                Bombs("ExplosiveKebab", 30, "Boom boom for 30 damage", 40),
                // Equipment: Items that boost stats (health, attack, defense, etc.)
                Equipment("HealthCrystal", StatType.HEALTH, 10, 30),
                Equipment("IronSword", StatType.ATTACK, 5, 30),
                Equipment("SteelArmor", StatType.DEFENSE, 7, 30),
                Equipment("SpellBook", StatType.SPECIAL_ATTACK, 5, 30),
                Equipment("MagicAmulet", StatType.SPECIAL_DEFENSE, 7, 30),
                Equipment("SpeedyBoots", StatType.SPEED, 10, 30)
            )
        }
    }

    @Serializable
    @SerialName("Enchantments")
    data class Enchantments(override val name: String,
                            val multiplier: Int,
                            val description: String,
                            override val probability: Int): Item()
    @Serializable
    @SerialName("Potion")
    data class Potion(override val name: String,
                      val healing: Int,
                      val description: String,
                      override val probability: Int): Item()
    @Serializable
    @SerialName("Bombs")
    data class Bombs(override val name: String,
                     val damage: Int,
                     val description: String,
                     override val probability: Int): Item()

    @Serializable
    @SerialName("Equipment")
    data class Equipment(
        override val name: String,
        val stat: StatType,
        val boost: Int,
        override val probability: Int) : Item()
}