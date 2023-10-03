package cn.rmc.skywars.manager;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.enums.Data;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.perk.PerkBasic;
import cn.rmc.skywars.perk.PerkType;
import cn.rmc.skywars.util.DataUtils;
import cn.rmc.skywars.util.database.KeyValue;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private HashMap<UUID, PlayerData> data = new HashMap<>();

    public PlayerData get(Player p){
        return data.get(p.getUniqueId());
    }
    public void Create(Player p){
        data.put(p.getUniqueId(),new PlayerData(p));
        loaddata(p);
    }
    public void remove(Player p){
        get(p).clearPerk();
        data.remove(p.getUniqueId());
    }

    public void loaddata(Player p){
        UUID uuid = p.getUniqueId();
        PlayerData pd = get(p);
       if(!SkyWars.getDataBase().dbExist("playerinfo",new KeyValue("UUID",uuid.toString()))){
           createData(p);
       }
    }
    public void createData(Player p){
        UUID uuid = p.getUniqueId();
        SkyWars.getDataBase().dbInsert("playerinfo",new KeyValue("UUID",uuid.toString()));

    }


}
