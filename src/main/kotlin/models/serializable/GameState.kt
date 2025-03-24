data class GameState(
    val character: Character,
    val dungeons: MutableList<Dungeon>,
    var currentDungeonIndex: Int,
    var runAway: Boolean,
    var exit: Boolean,
    var defeated: Boolean,
    val inventory: Inventory
)