import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("Spider")
class Spider(): Monster(MonstersName.entries.random(), "Spider", TypeMonster.SPIDER) {
    override fun sayHello(output: Output) {
        output.showMsg("Struggle all you want… the web always wins...")
    }
}