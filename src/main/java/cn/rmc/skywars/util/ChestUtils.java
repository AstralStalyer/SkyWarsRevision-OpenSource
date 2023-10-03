package cn.rmc.skywars.util;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestUtils {
    public static Set<Integer> getrandomints(int amount, int min, int max){
        Set<Integer> integers = new HashSet<>();
        for(;integers.size()<amount;){
            integers.add(min+(int)(Math.random() * (max-min)));
        }
        return integers;
    }
    public static Integer getrandomint(int min, int max){
        return min+(int)(Math.random() * (max-min));
    }

    //普通小岛
    public static ArrayList<ItemStack> getNormalSmallChest(){
        int swords = 1;
        int blocks = getrandomint(1,2);
        int utils = getrandomint(1,2);
        int armors = getrandomint(0,3);
        int miscs = getrandomint(1,3);
        Set<ItemStack> sword = new HashSet<>();
        Set<ItemStack> block = new HashSet<>();
        Set<ItemStack> util = new HashSet<>();
        Set<ItemStack> armor = new HashSet<>();
        Set<ItemStack> misc = new HashSet<>();
        while(sword.size() < swords){
            sword.add(randomSword());
        }
        while (block.size() < blocks){
            block.add(randomBlock());
        }
        while (util.size() < utils){
            util.add(randomUtil());
        }
        while (armor.size() < armors){
            armor.add(randomArmor());
        }
        while(misc.size() < miscs){
            misc.add(randomMisc());
        }
        ArrayList<ItemStack> result = new ArrayList<>();
        result.addAll(sword);
        result.addAll(block);
        result.addAll(util);
        result.addAll(armor);
        result.addAll(misc);
        return result;
    }
    private static ItemStack randomSword(){
        List<ItemStack> itemStacks = Arrays.asList(
                new ItemBuilder(Material.STONE_SWORD).toItemStack(),
                new ItemBuilder(Material.STONE_SWORD).addEnchant(Enchantment.DAMAGE_ALL,1).toItemStack(),
                new ItemBuilder(Material.IRON_SWORD).toItemStack()
        );
        return itemStacks.get(getrandomint(0,itemStacks.size()-1));
    }
    private static ItemStack randomBlock(){
        List<ItemStack> itemStacks = Arrays.asList(
                new ItemBuilder(Material.WOOD,16).toItemStack(),
                new ItemBuilder(Material.WOOD,24).toItemStack(),
                new ItemBuilder(Material.STONE,24).toItemStack(),
                new ItemBuilder(Material.STONE,32).toItemStack()
        );
        return itemStacks.get(getrandomint(0,itemStacks.size()-1));
    }
    private static ItemStack randomMisc(){
        List<ItemStack> itemStacks = Arrays.asList(
                new ItemBuilder(Material.COOKED_BEEF,16).toItemStack(),
                new ItemBuilder(Material.SNOW_BALL,8).toItemStack(),
                new ItemBuilder(Material.EXP_BOTTLE,8).toItemStack(),
                new ItemBuilder(Material.LAVA_BUCKET).toItemStack(),
                new ItemBuilder(Material.WATER_BUCKET).toItemStack(),
                new ItemBuilder(Material.FISHING_ROD).toItemStack(),
                new ItemBuilder(Material.GOLDEN_APPLE).toItemStack(),
                new ItemBuilder(Material.EGG,16).toItemStack()
        );
        return itemStacks.get(getrandomint(0,itemStacks.size()-1));
    }
    private static ItemStack randomArmor(){
        List<ItemStack> itemStacks = Arrays.asList(
                new ItemBuilder(Material.GOLD_CHESTPLATE).toItemStack(),
                new ItemBuilder(Material.GOLD_LEGGINGS).toItemStack(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).toItemStack(),
                new ItemBuilder(Material.IRON_BOOTS).toItemStack(),
                new ItemBuilder(Material.IRON_HELMET).toItemStack(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).toItemStack()
                );
        return itemStacks.get(getrandomint(0,itemStacks.size()-1));
    }
    private static ItemStack randomUtil(){
        List<ItemStack> itemStacks = Arrays.asList(
                new ItemBuilder(Material.IRON_AXE).toItemStack(),
                new ItemBuilder(Material.STONE_PICKAXE).toItemStack()
        );
        return itemStacks.get(getrandomint(0,itemStacks.size()-1));
    }
    //中岛
    public static ArrayList<ItemStack> getNormalMiddleChest() {
        ArrayList<ItemStack> result = new ArrayList<>();
        int i = getrandomint(6,11);
        while(result.size()<i){
            result.add(randommiddle());
        }
        return result;
    }
    private static ItemStack randommiddle(){
        List<ItemStack> itemStacks = Arrays.asList(
                new ItemBuilder(Material.FLINT_AND_STEEL).toItemStack(),
                new ItemBuilder(Material.ARROW,32).toItemStack(),
                new ItemBuilder(Material.LOG,64).toItemStack(),
                new ItemBuilder(Material.SNOW_BALL,64).toItemStack(),
                new ItemBuilder(Material.EXP_BOTTLE,64).toItemStack(),
                new ItemBuilder(Material.ENDER_PEARL).toItemStack(),
                new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).toItemStack(),
                new ItemBuilder(Material.TNT,10).toItemStack(),
                new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,1).toItemStack(),
                new ItemBuilder(Material.ARROW,16).toItemStack(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2).toItemStack(),
                new ItemBuilder(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_PROJECTILE,3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchant(Enchantment.DIG_SPEED,1).toItemStack(),
                new ItemBuilder(Material.LAVA_BUCKET).toItemStack(),
                new ItemBuilder(Material.MONSTER_EGG,1,(byte)61).toItemStack(),
                new ItemBuilder(Material.MONSTER_EGG,1,(byte)51).toItemStack()

        );
        return itemStacks.get(getrandomint(0,itemStacks.size()-1));
    }


    public static void playChestAction(Chest chest, boolean open) {
        Location location = chest.getLocation();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
        TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(position);
        world.playBlockAction(position, tileChest.w(), 1, open ? 1 : 0);
    }

}
