package cn.rmc.skywars.command;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.GameMode;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.enums.Data;
import cn.rmc.skywars.enums.GameState;
import cn.rmc.skywars.util.ChestUtils;
import cn.rmc.skywars.util.Config;
import cn.rmc.skywars.util.DataUtils;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.world.World;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class DebugCommand extends Command {

    private EditSession session;

    public DebugCommand(){
        super("debug");
    }
    @SneakyThrows
    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!sender.hasPermission("skywars.admin")) return true;
        Player p = (Player) sender;
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        if (args.length == 0) {
            p.sendMessage("§cUsage: /debug <value>");
            return true;
        }
        if(args[0].equalsIgnoreCase("start")){
            if(pd.getGame().getState() == GameState.WAITING || pd.getGame().getState() == GameState.COUNTING){
                pd.getGame().start();
            }
        }
        if(args[0].equals("add")){
            if(args.length != 3){
                p.sendMessage("§cUsage: /debug add <field> <amount>");
                return true;
            }
            DataUtils.add(Data.field.valueOf(args[1].toUpperCase()),pd,Integer.valueOf(args[2]));
        }
        if(args[0].equalsIgnoreCase("stats")){
            p.sendMessage(new String[]{
                    ""+pd.getState(),
                    ""+pd.getGame(),
                    ""+pd.getGame().getMap(),
                    ""+pd.getGame().getState(),
                    ""+pd.getGame().getAliveplayers(),
                    ""+pd.getGame().getPlayers()
            });
        }

        if(args[0].equalsIgnoreCase("cage")){
            if(args.length != 2){
                p.sendMessage("§cUsage: /debug cage <field>");
                return true;
            }
            Location loc = p.getLocation();
            File file = new File(SkyWars.getInstance().getDataFolder(),"//schematics//" + args[1] +".schematic");

            World world = new BukkitWorld(Bukkit.getWorld("world"));
            Vector position = new Vector(loc.getX(),loc.getY(),loc.getZ());

            final MCEditSchematicFormat mcedit = (MCEditSchematicFormat) SchematicFormat.MCEDIT;

            session = FaweAPI.getEditSessionBuilder(world).build();

            try {
                final CuboidClipboard clipboard = mcedit.load(file);
                clipboard.paste(session, position, false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            p.sendMessage("§aDone");
            return true;
        }
        if(args[0].equalsIgnoreCase("fill")){
            Block target =p.getTargetBlock((Set<Material>)null,10);
            System.out.println(target);
            if(target.getType() != Material.CHEST){
                p.sendMessage("请指向一个箱子");
                return true;
            }
            Chest chest = (Chest) target.getState();
            Inventory inv = chest.getBlockInventory();
            inv.clear();
            ArrayList<ItemStack> items = ChestUtils.getNormalSmallChest();
            int size = items.size();
            int item = 0;
            for(int i:ChestUtils.getrandomints(size,0,26)){
                inv.setItem(i,items.get(item));
                item++;
            }
            p.sendMessage("填充完毕");
            ChestUtils.playChestAction(chest,true);


        }
        if(args[0].equalsIgnoreCase("join")){
            if(args.length == 2){
                //SkyWars.getInstance().getGameManager().getGames().get(args[1].toLowerCase()).addPlayer(pd);
            }



        }
        //地图设置
        if(args[0].equalsIgnoreCase("map")){
            Config config = SkyWars.getMapconfig();
            if (args.length == 1) {
                p.sendMessage("§cUsage: /debug map <value>");
                return true;
            }
            if(args[1].equalsIgnoreCase("create")){
                if(args.length != 4){
                    p.sendMessage("§cUsage: /debug map create <name> <mode>");
                    return true;
                }
                config.createSection("maps."+args[2]);
                config.set("maps."+args[2]+".mode", GameMode.valueOf(args[3]).name());
                p.sendMessage("§aCreated Successfully!");
                return true;
            }
            if(args[1].equalsIgnoreCase("author")){
                if(args.length != 4){
                    p.sendMessage("§cUsage: /debug map author <map> <author>");
                    return true;
                }
                if(config.getConfigurationSection("maps."+args[2]) == null){
                    p.sendMessage("§c你还没创建这张地图!");
                    return true;
                }
                config.set("maps."+args[2]+".author", args[3]);
                p.sendMessage("§a你已成功设置此图作者!");
                return true;
            }
            if(args[1].equalsIgnoreCase("spawn")){
                if(args.length != 3){
                    p.sendMessage("§cUsage: /debug map spawn <maps>");
                    return true;
                }
                if(config.getConfigurationSection("maps."+args[2]) == null){
                    p.sendMessage("§c你还没创建这张地图!");
                    return true;
                }
                for(int i = 1;;i++){
                    if(config.get("maps."+args[2]+".spawns."+i) == null){
                        config.set("maps."+args[2]+".spawns."+i, p.getLocation());
                        p.sendMessage("§c你已成功设置§张地图的第"+i+"个出生点");
                        break;
                    }else{
                        continue;
                    }
                }
            }
            if(args[1].equals("deletespawn")){
                if(args.length != 4){
                    p.sendMessage("§cUsage: /debug map spawn <maps> <spawn位次>");
                    return true;
                }
                if(config.getConfigurationSection("maps."+args[2]) == null){
                    p.sendMessage("§c你还没创建这张地图!");
                    return true;
                }
                if(config.get("maps."+args[2]+".spawns."+args[3]) == null){
                    p.sendMessage("§c没有找到这个出生点");
                    return true;
                }
                config.set("maps."+args[2]+".spawns."+args[3],null);

            }
            if(args[1].equalsIgnoreCase("middle")){
                if(args.length != 3){
                    p.sendMessage("§cUsage: /debug map middle <maps>");
                    return true;
                }
                if(config.getConfigurationSection("maps."+args[2]) == null){
                    p.sendMessage("§c你还没创建§c这张地图!");
                    return true;
                }
                config.set("maps."+args[2]+".middle",p.getLocation());
                p.sendMessage("§c你已成功设置中点");
            }
            if(args[1].equalsIgnoreCase("save")){
                SkyWars.getMapconfig().save(new File(SkyWars.getInstance().getDataFolder(),"maps.yml"));
                p.sendMessage("§c成功保存配置");
            }
            if(args[1].equalsIgnoreCase("smallchest")){
                if(args.length != 3){
                    p.sendMessage("§cUsage: /debug map smallchest <maps>");
                    return true;
                }
                if(config.getConfigurationSection("maps."+args[2]) == null){
                    p.sendMessage("§c你还没创建§c这张地图!");
                    return true;
                }
                Block target = p.getTargetBlock((Set<Material>)null,10);
                if(target.getType() != Material.CHEST){
                    p.sendMessage("请指向一个箱子");
                    return true;
                }
                for(int i = 1;;i++){
                    if(config.get("maps."+args[2]+".smallchests."+i) == null){
                        config.set("maps."+args[2]+".smallchests."+i, target.getLocation());
                        ((Chest)target.getState()).getInventory().clear();
                        p.sendMessage("§c§c你已成功设置§c这张地图的第"+i+"个岛屿箱子");
                        break;
                    }else{
                        if(config.get("maps."+args[2]+".smallchests."+i).equals(target.getLocation())){
                            p.sendMessage("§c这个箱子已经设置过了");
                            break;
                        }
                        continue;
                    }
                }
            }
            if(args[1].equalsIgnoreCase("middlechest")){
                if(args.length != 3){
                    p.sendMessage("§cUsage: /debug map middlechest <maps>");
                    return true;
                }
                if(config.getConfigurationSection("maps."+args[2]) == null){
                    p.sendMessage("§c你还没创建§c这张地图!");
                    return true;
                }
                Block target = p.getTargetBlock((Set<Material>)null,10);
                if(target.getType() != Material.CHEST){
                    p.sendMessage("请指向一个箱子");
                    return true;
                }
                for(int i = 1;;i++){

                    if(config.get("maps."+args[2]+".middlechests."+i) == null){
                        config.set("maps."+args[2]+".middlechests."+i, target.getLocation());
                        ((Chest)target.getState()).getInventory().clear();
                        p.sendMessage("§c你已成功设置这张地图的第"+i+"个中心箱子");
                        break;
                    }else{
                        if(config.get("maps."+args[2]+".smallchests."+i).equals(target.getLocation())){
                            p.sendMessage("§c这个箱子已经设置过了");
                            break;
                        }
                        continue;
                    }
                }
            }
        }
        return true;
    }
}
