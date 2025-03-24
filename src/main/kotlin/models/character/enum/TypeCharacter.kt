import kotlinx.serialization.Serializable

@Serializable
enum class TypeCharacter(var health: Int,
                         var attack: Int,
                         var defense: Int,
                         var specialAttack: Int,
                         var specialDefense: Int,
                         var speed: Int,
                         val ability: Ability
) {
    WARRIOR(80, 45, 50, 20, 40, 40, Ability.BERSERK),
    WIZARD(65, 15, 35, 60, 45, 35, Ability.GAIN_LIFER),
    TANK(90, 45, 70, 25, 60, 15, Ability.HEAVYWEIGHT),
    THIEF(50, 55, 15, 10, 15, 70, Ability.LOOTER),
    ARCHER(55, 15, 20, 45, 25, 55, Ability.RANGE)
}