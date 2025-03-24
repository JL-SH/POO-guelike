interface Output {
    fun showTitle()
    fun showMsg(msg: String)
    fun readEntry(): String
    fun readNumber(): Int
    fun clearConsole()
    fun showInput(msg: String)
    fun showMainMenu()
    fun showCharacters()
    fun showInstructions()
    fun showOptionsAfterRoom()
    fun pressEnter()
    fun showHealths(enemie: Monster, character: Character)
    fun showCombatOptions()
    fun show(x: Any)
    fun showStats(character: Character)
}