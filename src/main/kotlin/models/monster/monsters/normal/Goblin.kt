import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("Goblin")
class Goblin(): Monster(MonstersName.entries.random(),"Goblin", TypeMonster.GOBLIN) {
    override fun sayHello(output: Output) {
        output.showMsg("Time to smash and grab! Hope you brought a helmet!...")
    }
}