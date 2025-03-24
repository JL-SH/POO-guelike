import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
@SerialName("DemonKing")
class DemonKing: Monster(MonstersName.entries.random(), "Lucifer", TypeMonster.DEMON_KING) {
    override fun sayHello(output: Output) {
        output.showMsg("You’ve come far... but your journey ends here. Burn in my eternal hell...")
    }
}