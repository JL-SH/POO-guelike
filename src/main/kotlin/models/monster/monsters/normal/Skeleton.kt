import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("Skeleton")
class Skeleton() : Monster(MonstersName.entries.random(), "Skeleton", TypeMonster.SKELETON) {
    override fun sayHello(output: Output) {
        output.showMsg("You can’t kill what’s already dead... but I can kill you!...")
    }
}