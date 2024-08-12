package fr.mathip.azplugin.bukkit.handlers;

public enum PLSPPlayerModel {
    DROPPED_ITEM(1),
    EXPERIENCE_ORB(2),
    AREA_EFFECT_CLOUD(3),
    EGG(7),
    LEAD_KNOT(8),
    PAINTING(9),
    ARROW(10),
    SNOWBALL(11),
    GHAST_FIREBALL(12),
    BLAZE_FIREBALL(13),
    ENDER_PEARL(14),
    EYE_OF_ENDER(15),
    SPLASH_POTION(16),
    BOTTLE_ENCHANTING(17),
    WITHER_SKULL(19),
    PRIMED_TNT(20),
    FALLING_BLOCK(21),
    FIREWORK_ROCKET(22),
    SPECTRAL_ARROW(24),
    SHULKER_BULLET(25),
    DRAGON_FIREBALL(26),
    ARMOR_STAND(30),
    MINECART_COMMAND_BLOCK(40),
    BOAT(41),
    MINECART(42),
    STORAGE_MINECART(43),
    POWERED_MINECART(44),
    MINECART_TNT(45),
    MINECART_HOPPER(46),
    MINECART_SPAWNER(47),
    CREEPER(50),
    SKELETON(51),
    SPIDER(52),
    GIANT_ZOMBIE(53),
    ZOMBIE(54),
    SLIME(55),
    GHAST(56),
    ZOMBIE_PIGMAN(57),
    ENDERMAN(58),
    CAVE_SPIDER(59),
    SILVERFISH(60),
    BLAZE(61),
    MAGMA_CUBE(62),
    ENDER_DRAGON(63),
    WITHER(64),
    BAT(65),
    WITCH(66),
    ENDERMITE(67),
    GUARDIAN(68),
    SHULKER(69),
    PIG(90),
    SHEEP(91),
    COW(92),
    CHICKEN(93),
    SQUID(94),
    WOLF(95),
    MOOSHROOM(96),
    SNOW_GOLEM(97),
    OCELOT(98),
    IRON_GOLEM(99),
    HORSE(100),
    RABBIT(101),
    VILLAGER(120),
    ENDER_CRYSTAL(200);

    private final Integer entityId;

    PLSPPlayerModel(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getId() {
        return entityId;
    }

    public static Integer getId(PLSPPlayerModel entity) {
        return entity.getId();
    }
}
