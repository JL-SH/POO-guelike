import kotlinx.serialization.Serializable

@Serializable
class Dungeon(var name: DungeonName = DungeonName.entries.random(),
              var level: Int = 0,
              val rooms: MutableList<Room> = mutableListOf(),
              var currentRoomIndex: Int = 0
) {
    // Initialize the dungeon by generating rooms if it's empty
    init {
        if (rooms.isEmpty()) {
            generateRooms()
            level++  // Increase the dungeon level after generating rooms
        }
    }

    // Function to generate rooms for the dungeon
    private fun generateRooms() {
        // Create 4 non-boss rooms
        repeat(4) { rooms.add(Room(false)) }
        // Create 1 boss room at the end
        rooms.add(Room(true))
    }

    fun getCurrentRoomIndexPlusOne(): Int = currentRoomIndex + 1

    fun getCurrentRoom(): RoomName = rooms[currentRoomIndex].name

    fun nextRoom() {
        if (!isCompleted()) {
            currentRoomIndex++
        }
    }

    fun isCompleted(): Boolean = currentRoomIndex >= rooms.size - 1
}