import kotlinx.serialization.Serializable

@Serializable
data class SerializableGameState(
    val character: CharacterState,
    val dungeons: List<DungeonState>,
    val currentDungeonIndex: Int,
    val runAway: Boolean,
    val exit: Boolean,
    val defeated: Boolean,
    val inventory: InventoryState
)