enum class TypeMonster(var health: Int,
                       var attack: Int,
                       var defense: Int,
                       var specialAttack: Int,
                       var specialDefense: Int,
                       var speed: Int,
                       val ability: MonsterAbility
) {
    GOBLIN(50, 30, 25, 10, 15, 60, MonsterAbility.SWARM),
    SPIDER(55, 15, 35, 40, 35, 40, MonsterAbility.VENOMOUS_BITE),
    SKELETON(45, 25, 30, 25, 30, 50, MonsterAbility.UNDYING),
    DRAGON(320, 55, 50, 55, 50, 35, MonsterAbility.DRAGON_FURY),
    DEMON_KING(280, 65, 45, 70, 50, 40, MonsterAbility.HELLFIRE)
}