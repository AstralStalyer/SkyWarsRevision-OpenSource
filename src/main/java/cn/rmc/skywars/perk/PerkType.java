package cn.rmc.skywars.perk;

import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.perk.perks.*;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public enum PerkType {
    ArrowRecovery(new ItemBuilder(Material.ARROW).toItemStack(),ArrowRecovery.class,5,"箭矢回收"),
    BlazingArrows(new ItemBuilder(Material.BLAZE_POWDER).toItemStack(),BlazingArrows.class,5,"火焰箭矢"),
    Bulldozer(new ItemBuilder(Material.ANVIL).toItemStack(),Bulldozer.class,5,"推土机"),
    EnderMastery(new ItemBuilder(Material.ENDER_PEARL).toItemStack(),EnderMastery.class,5,"末影专精"),
    InstantSmelting(new ItemBuilder(Material.FURNACE).toItemStack(),InstantSmelting.class,1,"瞬间烧炼"),
    Juggernaut(new ItemBuilder(Material.DIAMOND_HELMET).toItemStack(),Juggernaut.class,5,"主宰者"),
    Marksmanship(new ItemBuilder(Material.BOW).toItemStack(),Marksmanship.class,1,"百步穿杨"),
    MiningExpertise(new ItemBuilder(Material.IRON_PICKAXE).toItemStack(),MiningExpertise.class,5,"挖掘专家"),
    ResistanceBoost(new ItemBuilder(Material.IRON_CHESTPLATE).toItemStack(),ResistanceBoost.class,3,"抗性提升"),
    SpeedBoost(new ItemBuilder(Material.BREWING_STAND_ITEM).toItemStack(),SpeedBoost.class,5,"速度提升"),
    Knowledge(new ItemBuilder(Material.BOOK).toItemStack(),Knowledge.class,3,"知识"),
    AnnoyOMite(new ItemBuilder(Material.MONSTER_EGG,1,(byte)60).toItemStack(),AnnoyOMite.class,5,"群虫射手"),
    Nourishment(new ItemBuilder(Material.BREAD).toItemStack(),Nourishment.class,1,"能量汲取"),
    Revenge(new ItemBuilder(Material.BREAD).toItemStack(),Revenge.class,1,"复仇"),
    Fat(new ItemBuilder(Material.GOLDEN_APPLE).toItemStack(),Fat.class,5,"胖子"),
    Bridger(new ItemBuilder(Material.WOOD).toItemStack(),Bridger.class,5,"建造者"),
    EnvironmentalExpert(new ItemBuilder(Material.LEATHER_CHESTPLATE).toItemStack(),EnvironmentalExpert.class,3,"生存专家"),
    LuckyCharm(new ItemBuilder(Material.SPECKLED_MELON).toItemStack(),LuckyCharm.class,20,"幸运符"),
    Necromancer(new ItemBuilder(Material.ROTTEN_FLESH).toItemStack(),Necromancer.class,5,"亡灵法师"),
    BlackMagic(new ItemBuilder(Material.CAULDRON_ITEM).toItemStack(),BlackMagic.class,5,"黑魔法"),
    Robbery(new ItemBuilder(Material.IRON_FENCE).toItemStack(),Robbery.class,10,"盗窃"),
    Frost(new ItemBuilder(Material.ICE).toItemStack(),Frost.class,5,"冰霜法术");


    @Getter
    private ItemStack item;
    @Getter
    private Class<? extends PerkBasic> clazz;
    @Getter
    private String DBName;
    @Getter
    private Integer maxLevel;
    //private final String[] lore;

    PerkType(ItemStack item, Class<? extends PerkBasic> clazz, Integer maxLevel,String DBName) {
        this.item = item;
        this.DBName = DBName;
        this.clazz = clazz;
        this.maxLevel = maxLevel;
    }

    public PerkBasic getPerk(Player owner,Integer innerLevel){
        PerkBasic perk = null;
        try {
            perk = clazz.getDeclaredConstructor(UUID.class,Integer.class).newInstance(owner.getUniqueId(),innerLevel);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return perk;
    }
}
