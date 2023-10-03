package cn.rmc.skywars.manager;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.GameMode;
import cn.rmc.skywars.game.Map;
import cn.rmc.skywars.util.Config;
import org.bukkit.Difficulty;
import org.bukkit.Location;

import java.util.ArrayList;

public class MapManager {
    ArrayList<Map> data = new ArrayList<>();

    public MapManager(){
        load();
    }

    public void load(){
        Config config = SkyWars.getMapconfig();

        if(config.getConfigurationSection("maps") == null || config.getConfigurationSection("maps").getKeys(false) == null){
            return;
        }
        for(String s: config.getConfigurationSection("maps").getKeys(false)){
            Map result = new Map();
            result.name = s;
            result.author = config.get("maps."+s+".author").toString();
            Boolean middle = false;
            Boolean spawn = false;
            System.out.println("加载"+s);
            if(config.get("maps."+s+".middle") == null){
                System.out.println("没有设置中点!");
            }else{
                middle = true;
                result.middle = (Location) config.get("maps."+s+".middle");
            }
            if(config.getConfigurationSection("maps."+s+".spawns") == null){
                System.out.println("没有设置出生点!");
            }else{
                spawn = true;
                for(String ss :config.getConfigurationSection("maps."+s+".spawns").getKeys(false)){
                    result.locations.add((Location) config.get("maps."+s+".spawns."+ss));
                }
            }
            if(config.getConfigurationSection("maps."+s+".smallchests") != null){
                for(String ss :config.getConfigurationSection("maps."+s+".smallchests").getKeys(false)){
                    result.smallchests.add((Location) config.get("maps."+s+".smallchests."+ss));
                    result.openchests.put((Location) config.get("maps."+s+".smallchests."+ss),null);
                    result.isopened.put((Location) config.get("maps."+s+".smallchests."+ss),false);
                }
            }
            if(config.getConfigurationSection("maps."+s+".middlechests").getKeys(false) != null){
                for(String ss :config.getConfigurationSection("maps."+s+".middlechests").getKeys(false)){
                    result.middlechests.add((Location) config.get("maps."+s+".middlechests."+ss));
                    result.openchests.put((Location) config.get("maps."+s+".middlechests."+ss),null);
                    result.isopened.put((Location) config.get("maps."+s+".middlechests."+ss),false);
                }
            }
            result.mode = GameMode.valueOf(config.getString("maps."+s+".mode"));
            if(middle && spawn){
                result.middle.getWorld().setGameRuleValue("doDaylightCycle","false");
                result.middle.getWorld().setTime(0);
                result.middle.getWorld().setTime(6000);
                result.middle.getWorld().setDifficulty(Difficulty.NORMAL);
                result.middle.getWorld().setGameRuleValue("doMobSpawning","false");
                data.add(result);
            }
        }
    }

}
