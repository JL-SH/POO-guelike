import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("Dragon")
class Dragon(): Monster(MonstersName.entries.random(), "Blue-Eyed Dragon", TypeMonster.DRAGON) {
    override fun sayHello(output: Output) {
        output.showMsg("RWAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR!!!!!!!!!!!!!!!...")
    }
}