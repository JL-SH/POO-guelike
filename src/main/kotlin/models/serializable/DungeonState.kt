import kotlinx.serialization.Serializable

@Serializable
data class DungeonState(
    val name: DungeonName,
    val level: Int,
    val rooms: List<Room>,
    val currentRoomIndex: Int
)