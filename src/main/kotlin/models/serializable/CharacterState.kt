import kotlinx.serialization.Serializable

@Serializable
data class CharacterState(
    val name: String,
    val typeCharacterName: String,
    val maxHealth: Int,
    val health: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val ability: Ability,
    val inventory: InventoryState,
    val damageMultiplier: Int,
    val multiplierTurns: Int,
)