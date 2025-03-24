import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@Polymorphic
@JsonClassDiscriminator("class")
sealed class Resource {
    abstract val name: String
    abstract val points: Int
    abstract val probability: Int

    companion object {
        // A list that holds all instances of the different types of resources
        val allResources: List<Resource> = addAllResources()

        // A private function that creates and returns a list of all the resources
        private fun addAllResources(): List<ValuableResources> {
            return listOf(
                // Valuable resources with specific points and probability
                ValuableResources("Gold", 5, 50),
                ValuableResources("Silver", 2, 80),
                ValuableResources("Emerald", 7, 30),
                ValuableResources("Ruby", 10, 10)
            )
        }
    }


    @Serializable
    @SerialName("ValuableResources")
    data class ValuableResources(
        override val name: String,
        override val points: Int,
        override val probability: Int
    ) : Resource()
}
