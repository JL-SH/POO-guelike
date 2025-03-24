import kotlinx.serialization.Serializable

@Serializable
class Thief(val name: String): Character(charName = name, TypeCharacter.THIEF)