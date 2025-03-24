import kotlinx.serialization.Serializable

@Serializable
enum class Ability(val desc: String) {
    BERSERK("Berserk: When warrior loses 50% of his health, he gains x2 attack."),
    GAIN_LIFER("GainLifer: The wizard has a 15% of omnivamp."),
    HEAVYWEIGHT("Heavyweight: Tank doesn't receive normal damage when he defends."),
    LOOTER("Looter: Thief gains x2 loots every room."),
    RANGE("Range: Archer attacks twice, but the second attack deals 50% damage.")
}