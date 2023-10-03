package cn.rmc.skywars.kit;

import cn.rmc.skywars.kit.kits.*;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.ItemUtil;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public enum KitType {
    Default(new ItemBuilder(Material.WOOD_PICKAXE).toItemStack(), Default.class,"默认",
            new String[]{
                    "§7木镐",
                    "§7木斧",
                    "§7木锹"
    }),
    Armorer(new ItemBuilder(Material.GOLD_CHESTPLATE).toItemStack(), Armorer.class,"装备师",
            new String[]{
                    "§7金胸甲",
                    "§7金护腿",
                    "§7金靴子"
    }),
    Armorsmith(new ItemBuilder(Material.ANVIL).toItemStack(), Armorsmith.class,"附魔师",
            new String[]{
                    "§7铁砧",
                    "§7附魔书",
                    "   §8• 保护 III",
                    "   §8• 锋利 I",
                    "§7附魔之瓶 §8x64",
                    "§7钻石头盔"
    }),
    BaseBallPlayer(new ItemBuilder(Material.IRON_HELMET).toItemStack(),BaseBallPlayer.class,"棒球手",
            new String[]{
                    "§7铁头盔",
                    "   §8• 保护 I",
                    "§7木剑",
                    "   §8• 击退 I"
    }),
    Cannoneer(new ItemBuilder(Material.TNT).toItemStack(),Cannoneer.class,"炮手",
            new String[]{
                    "§7TNT §8x16",
                    "§7红石块 §8x4",
                    "§7铁靴子",
                    "   §8• 摔落保护 III",
                    "   §8• 爆炸保护 III",
                    "§7水桶",
                    "§压力板 §8x4"
    }),
    Ecologist(ItemUtil.addItemFlag(new ItemBuilder(Material.IRON_AXE).toItemStack(),ItemFlag.HIDE_ATTRIBUTES),Ecologist.class,"生态学家",
            new String[]{
                    "§7铁斧",
                    "   §8• 效率 I",
                    "§7橡木 §8x16"
    }),
    Enchanter(new ItemBuilder(Material.ENCHANTMENT_TABLE).toItemStack(),Enchanter.class,"附魔师",
            new String[]{
                    "§7附魔台",
                    "§7附魔之瓶 §8x64",
                    "§7书架 §8x8"
    }),
    EnderChest(new ItemBuilder(Material.ENDER_CHEST).toItemStack(),EnderChest.class,"末影拾荒者",
            new String[]{
                    "§7金苹果",
                    "§7在你的笼子下生成第四个",
                    "§7带有出生物品的箱子"
    }),
    Enderman(new ItemBuilder(Material.ENDER_PEARL).toItemStack(),Enderman.class,"末影人",
            new String[]{
                    "§7损坏的珍珠 §8(§730秒冷却§8)"
    }),
    Farmer(new ItemBuilder(Material.IRON_LEGGINGS).toItemStack(),Farmer.class,"农夫",
            new String[]{
                    "§7铁护腿",
                    "   §8• 弹射物保护 III",
                    "§7蛋 §8x64",
                    "§7金苹果"
    }),
    Fisherman(new ItemBuilder(Material.FISHING_ROD).toItemStack(),Fisherman.class,"渔夫",
            new String[]{
                    "§7钓鱼竿",
                    "   §8• 耐久 X",
                    "   §8• 击退 I",
                    "   §8• 海之眷顾 X",
                    "   §8• 饵钓 VII",
                    "§7熟鱼 §8x16",
    }),
    Hunter(new ItemBuilder(Material.BOW).toItemStack(),Hunter.class,"猎人",
            new String[]{
                    "§7弓",
                    "   §8• 力量 I",
                    "§7箭 §8x16"
    }),
    Knight(ItemUtil.addItemFlag(new ItemBuilder(Material.GOLD_SWORD).toItemStack(),ItemFlag.HIDE_ATTRIBUTES),Knight.class,"骑士",
            new String[]{
                    "§7金剑",
                    "   §8• 锋利 II",
                    "   §8• 耐久 V",
                    "§7金头盔",
                    "   §7• 保护 I"
    }),
    Pharaoh(new ItemBuilder(Material.GOLD_HELMET).toItemStack(),Pharaoh.class,"法老",
            new String[]{
                    "§7金头盔",
                    "§7白色皮革外套",
                    "§7白色皮革裤子",
                    "§7信标",
                    "§7绿宝石块 §8x42"
    }),
    Pyro(new ItemBuilder(Material.FLINT_AND_STEEL).toItemStack(),Pyro.class,"纵火者",
            new String[]{
                    "§7打火石",
                    "   §8• 耐久 X",
                    "§7熔岩桶 §8x5",
                    "§7铁胸甲",
                    "§f喷溅型抗火药水 (无限)"
    }),
    Rookie(new ItemBuilder(Material.LEATHER_HELMET).toItemStack(),Rookie.class,"菜鸟",
            new String[]{
                    "§7皮革帽子",
                    "§7皮革外套",
                    "§7皮革裤子",
                    "§7皮革靴子",
                    "§7木剑",
                    "§7玻璃 §8x16",
                    "§7牛排"
    }),
    Snowman(new ItemBuilder(Material.SNOW_BALL).toItemStack(),Snowman.class,"雪人",
            new String[]{
                    "§7雪球 §8x16",
                    "§7雪块 §8x2",
                    "§7铁锹",
                    "§7南瓜"
            }),
    Speleologist(ItemUtil.addItemFlag(new ItemBuilder(Material.IRON_PICKAXE).toItemStack(),ItemFlag.HIDE_ATTRIBUTES),Speleologist.class,"考古学家",
            new String[]{
                    "§7铁镐",
                    "   §8• 效率 III",
                    "   §8• 锋利 I",
                    "   §8• 耐久 III",
                    "§7木头 §8x16"
    }),
    Troll(new ItemBuilder(Material.WEB).toItemStack(),Troll.class,"熊孩子",
            new String[]{
                    "§7蜘蛛网 §8x16",
                    "§7烟花火箭 §8x5",
                    "§7皮革帽子",
                    "§7皮革外套",
                    "§7皮革裤子",
                    "§7皮革靴子"
    }),
    Batguy(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.BLACK).toItemStack(),Batguy.class,"蝙蝠人",
            new String[]{
                    "§7皮革帽子",
                    "§7皮革外套",
                    "§7皮革裤子",
                    "§7皮革靴子",
                    "§a蝙蝠人药水 (8秒)",
                    "§7蝙蝠 蛋 §8x5"

    }),
    Disco(new ItemBuilder(Material.JUKEBOX).toItemStack(),Disco.class,"迪斯科",
            new String[]{
                    "§7金头盔",
                    "   §8• 弹射物保护 IV",
                    "§7皮革外套",
                    "   §8• 保护 II",
                    "   §8• 荆棘 III",
                    "§7皮革裤子",
                    "§7皮革靴子",
                    "   §8• 摔落保护 II",
                    "§7唱片机",
                    "§7音符盒 §8x12",
                    "§7随机音乐唱片"
    }),
    Energix(new ItemBuilder(Material.BREWING_STAND_ITEM).toItemStack(),Energix.class,"大力士",
            new String[]{
                    "§7力量药水 (5秒)"
    }),
    Frog(ItemUtil.getCustomSkull("ewogICJ0aW1lc3RhbXAiIDogMTU5NTMxOTUxNDgyMSwKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kMmIyMTJiNzNiYmEwNjRkNWYyZmUwNjFlNDZmZmZmNzMwZWQ1NWZiODM2N2NiZTEyZTcxYmRiMjdhYzAwZGU3IgogICAgfQogIH0KfQ==")
            ,Frog.class,"青蛙",
            new String[]{
                    "§a青蛙头饰",
                    "§7皮革外套",
                    "§7皮革裤子",
                    "§7皮革靴子",
                    "§a青蛙药水 (20秒)"
    }),
    Grenade(new ItemBuilder(Material.MONSTER_EGG,1,(byte)50).toItemStack(),Grenade.class,"苦力怕",
            new String[]{
                    "§a闪电苦力怕蛋"
            }),
    Guardian(new ItemBuilder(Material.OBSIDIAN).toItemStack(),Guardian.class,"守卫者",
            new String[]{
                    "§7黑曜石 §8x6",
                    "§f喷溅型抗性提升药水 (10秒)",
                    "§7骷髅 蛋§8x2",
                    "§7僵尸 蛋§8x2"
            }),
    Healer(new ItemBuilder(Material.CAKE).toItemStack(),Healer.class,"医师",
            new String[]{
                    "§7喷溅型药水 - 治疗 §8x3",
                    "§7喷溅型药水 - 生命恢复 §8x2",
            }),
    Scout(ItemUtil.addPotionEffect(new Potion(PotionType.SPEED).splash().toItemStack(1),new PotionEffect(PotionEffectType.SPEED,20*15,1)),
            Scout.class,"侦察兵",
            new String[]{
                    "§7喷溅性药水 - 速度 §8x3"
            }),
    Princess(new ItemBuilder(Material.RED_ROSE).toItemStack(),Princess.class,"公主",
            new String[]{
                    "§7金头盔",
                    "   §8• 保护 I",
                    "§7弓",
                    "   §8• 火失 I",
                    "§7箭"
            }),
    Engineer(new ItemBuilder(Material.REDSTONE).toItemStack(),Engineer.class,"工程师",
            new String[]{
                    "§7绊线钩 §8x2",
                    "§7蜘蛛网 §8x8",
                    "§7活塞 §8x4",
                    "§7粘液球 §8x4",
                    "§7红石 §8x16",
                    "§7拉杆",
                    "§7发射器 §8x2",
            }),
    Salmon(new ItemBuilder(Material.RAW_FISH,1,(byte)1).toItemStack(),Salmon.class,"鲑鱼",
            new String[]{
                    "§7钻石靴子",
                    "   §8• 深海探索者 III",
                    "§7水桶 §8x3"
            }),
    PigRider(new ItemBuilder(Material.CARROT_STICK).toItemStack(),PigRider.class,"野猪骑士",
            new String[]{
                    "§7鞍",
                    "§7猪 蛋",
                    "§7金头盔",
                    "   §8• 保护 IV",
                    "§7胡萝卜钓竿"
            }),
    Slime(new ItemBuilder(Material.SLIME_BLOCK).toItemStack(),Slime.class,"史莱姆",
            new String[]{
                    "§7铁靴子",
                    "   §8• 摔落保护 IV",
                    "§7史莱姆块 §8x16"
            }),
    Jester(new ItemBuilder(Material.DIAMOND_SWORD).toItemStack(), Jester.class,"小丑",
            new String[]{
                    "§7随机的剑",
                    "§7喷溅型药水 - 生命恢复"
            }),
    Zookeeper(new ItemBuilder(Material.MONSTER_EGG).toItemStack(),Zookeeper.class,"饲养员",
            new String[]{
                    "§dMystery Egg §8x3"
            }),
    Sloth(ItemUtil.addPotionEffect(new Potion(PotionType.SLOWNESS).toItemStack(1),new PotionEffect(PotionEffectType.SLOW,20*5,1)),
            Sloth.class,"小树懒",
            new String[]{
                    "§7树懒药水 §8x3",
                    "§7皮革帽子",
                    "§7皮革外套",
                    "§7皮革裤子",
                    "§7皮革靴子",
                    "§7丛林木 §8x4",
                    "§7永久受到缓慢II影响",
            }),
    Magician(ItemUtil.addPotionEffect(new Potion(PotionType.INVISIBILITY).splash().toItemStack(1),new PotionEffect(PotionEffectType.INVISIBILITY,20*15,1)),
            Magician.class,"魔术师",
            new String[]{
                    "§7兔子 蛋",
                    "§7皮革帽子",
                    "§7魔术师药水 §8x2",
                    "§7法杖",
                    "   §8d• 锋利 IV"
            });

    @Getter
    private final String DBName;
    private final ItemStack item;
    private final String[] lore;

    public ItemStack getItem(){
        ItemBuilder ib = new ItemBuilder(item);
        ib.setName("§a"+DBName);
        ib.removeEnchantments();
        ib.addFlag(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS);
        ArrayList<String> re = new ArrayList<>(Arrays.asList(lore));
        re.add("");
        re.add("§e点击选择");
        ib.setLore(re);
        ib.removeEnchantment(Enchantment.ARROW_INFINITE);
        return ib.toItemStack();
    }
    public ItemStack getunlockedItem(){
        ArrayList<String> re = new ArrayList<>(Arrays.asList(lore));
        re.add("");
        re.add("§c尚未解锁!");
        ItemStack result = new ItemBuilder(Material.STAINED_GLASS_PANE,1,(byte)14)
                .setName("§c"+DBName).addFlag(ItemFlag.HIDE_ATTRIBUTES).setLore(re).toItemStack();
        return result;
    }
    public ItemStack getSelectedItem(){
        ItemBuilder ib = new ItemBuilder(item);
        ib.setName("§a"+DBName);
        ib.addFlag(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS);
        ArrayList<String> re = new ArrayList<>(Arrays.asList(lore));
        re.add("");
        re.add("§a已选择");
        ib.setLore(re);
        ib.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        return ib.toItemStack();
    }
    @Getter
    private final Class<? extends KitBasic> clazz;
    KitType(ItemStack itemStack, Class<? extends KitBasic> clazz, String DBName, String[] lore){
        this.item = itemStack;
        this.DBName = DBName;
        this.clazz = clazz;
        this.lore = lore;
    }
    public KitBasic getKit(Player owner) {
        KitBasic basic = null;
        try {
            basic = clazz.getDeclaredConstructor(UUID.class).newInstance(owner.getUniqueId());
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return basic;
    }
}
