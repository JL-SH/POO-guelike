import kotlinx.serialization.Serializable

@Serializable
data class InventoryState(
    val items: List<Item>,
    val resources: List<Resource>
)