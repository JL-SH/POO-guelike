enum class MonsterAbility(val desc: String) {
    SWARM("Swarm: The Goblin calls another goblin when his health is below 50%."),
    VENOMOUS_BITE("Venomous Bite: The Spider poisons the enemy, reducing their attack by 20% for 2 turns."),
    UNDYING("Undying: The Skeleton revives once with 30% of its max health."),
    DRAGON_FURY("Dragon Fury: When the Dragon is below 50% health, its attack and special attack increase by 30%."),
    HELLFIRE("Each turn when attacks, the Demon King deals 10% of the enemy max HP as damage.")
}