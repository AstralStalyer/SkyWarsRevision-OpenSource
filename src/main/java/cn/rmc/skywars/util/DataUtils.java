package cn.rmc.skywars.util;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.GameMode;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.enums.Data;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.perk.PerkType;
import cn.rmc.skywars.util.database.KeyValue;
import cn.rmc.skywars.util.json.JSONObject;
import org.bukkit.entity.Player;

public class DataUtils {
    public static void add(Data.field field, PlayerData pd, int i){
        Player player = pd.getPlayer();
        String uuid = player.getUniqueId().toString();
        String str = pd.getGame().getTotalmode().name();
        switch (field.getTable()){
            case PLAYERINFO:
                String tab = field.getTable().getWhere();
                if(SkyWars.getDataBase().dbSelectFirst(tab,field.getWhere(),new KeyValue("UUID",uuid)) == null){
                    SkyWars.getDataBase().dbUpdate(tab,new KeyValue(field.getWhere(),i),new KeyValue("UUID",uuid));
                }else{
                    int p = Integer.parseInt(SkyWars.getDataBase().dbSelectFirst(tab,field.getWhere(),new KeyValue("UUID",uuid)));
                    SkyWars.getDataBase().dbUpdate(tab,new KeyValue(field.getWhere(),p + i),new KeyValue("UUID",uuid));
                }
                break;
            case JOB:
                String table = field.getTable().getWhere();
                if(SkyWars.getDataBase().dbSelectFirst(table,field.getWhere(),new KeyValue(){{ add("UUID",uuid);add("KitName",str+pd.getKit().getType().name()); }}) == null){
                    SkyWars.getDataBase().dbUpdate(table,new KeyValue(field.getWhere(),i),new KeyValue(){{ add("UUID",uuid);add("KitName",str+pd.getKit().getType().name()); }});
                }else{
                    int p = Integer.parseInt(SkyWars.getDataBase().dbSelectFirst(table,field.getWhere(),new KeyValue(){{ add("UUID",uuid);add("KitName",str+pd.getKit().getType().name()); }}));
                    SkyWars.getDataBase().dbUpdate(table,new KeyValue(field.getWhere(),p + i),new KeyValue(){{ add("UUID",uuid);add("KitName",str+pd.getKit().getType().name()); }});
                }
                break;
            case PAJ:
                String pjptable = Data.PLAYERINFO.getWhere();
                JSONObject oj;
                if(SkyWars.getDataBase().dbSelectFirst(pjptable,field.getWhere(),new KeyValue("UUID",uuid)) == null){
                    oj = new JSONObject();
                }else{
                    oj = new JSONObject(get(Data.PLAYERINFO, field,pd));
                }

                int before = oj.keySet().contains(pd.getGame().getMode().name())? oj.getInt(pd.getGame().getMode().name()):0;
                oj.put(pd.getGame().getMode().name(),before+i);
                set(field,pd,oj.toString());
/*
                if(SkyWars.getDataBase().dbSelectFirst(pjptable,field.getWhere(),new KeyValue("UUID",uuid)) == null){
                    SkyWars.getDataBase().dbUpdate(pjptable,new KeyValue(field.getWhere(),i),new KeyValue("UUID",uuid));
                }else{
                    int ii = Integer.parseInt(SkyWars.getDataBase().dbSelectFirst(pjptable,field.getWhere(),new KeyValue("UUID",uuid)));
                    SkyWars.getDataBase().dbUpdate(pjptable,new KeyValue(field.getWhere(),ii + i),new KeyValue("UUID",uuid));
                }

 */
                String pjjtable = Data.JOB.getWhere();

                if(SkyWars.getDataBase().dbSelectFirst(pjjtable,field.getWhere(),new KeyValue(){{ add("UUID",uuid);add("KitName",str+pd.getKit().getType().name()); }}) == null){
                    SkyWars.getDataBase().dbUpdate(pjjtable,new KeyValue(field.getWhere(),i),new KeyValue(){{ add("UUID",uuid);add("KitName",str+pd.getKit().getType().name()); }});
                }else{
                    int iii = Integer.parseInt(SkyWars.getDataBase().dbSelectFirst(pjjtable,field.getWhere(),new KeyValue(){{ add("UUID",uuid);add("KitName",str+pd.getKit().getType().name()); }}));
                    SkyWars.getDataBase().dbUpdate(pjjtable,new KeyValue(field.getWhere(),iii + i),new KeyValue(){{ add("UUID",uuid);add("KitName",str+pd.getKit().getType().name()); }});
                }
        }
    }
    public static String get(Data.field field, PlayerData pd){
        String str = pd.getGame().getTotalmode().name();
        String table;
        if(field.getTable() == null){
             table = Data.PLAYERINFO.getWhere();
        }else{
            table = field.getTable().getWhere();
        }
        Player player = pd.getPlayer();
        String uuid = player.getUniqueId().toString();
        if(field.getTable() == Data.JOB){
            return SkyWars.getDataBase().dbSelectFirst(table,field.getWhere(),new KeyValue(){{add("UUID",uuid);add("KitName",str+pd.getKit().getType().name());}});
        }else{
            return SkyWars.getDataBase().dbSelectFirst(table,field.getWhere(),new KeyValue("UUID",uuid));
        }
    }
    public static String get(Data data,Data.field field, PlayerData pd){
        Player player = pd.getPlayer();
        String str = pd.getGame().getTotalmode().name();
        String uuid = player.getUniqueId().toString();
        if(data == Data.JOB){
            return SkyWars.getDataBase().dbSelectFirst(data.getWhere(),field.getWhere(),new KeyValue(){{add("UUID",uuid);add("KitName",str+pd.getKit().getType().name());}});
        }else if(data == Data.PLAYERINFO) {
            return SkyWars.getDataBase().dbSelectFirst(data.getWhere(),field.getWhere(),new KeyValue("UUID",uuid));
        }else{
            return null;
        }
    }
    public static void set(PlayerData p, Data.field field, Integer i){
        //if(SkyWars.getDataBase().dbSelectFirst(Data.JOB.getWhere(),field.getWhere(),new KeyValue(){{add("UUID",p.getPlayer().getUniqueId().toString());add(Data.field.KitNAME.getWhere(),p.getGame().getTotalmode().name()+p.getKit().getType().name());}}) == null) {
        if(!SkyWars.getDataBase().dbExist(Data.JOB.getWhere(),new KeyValue(){{add("UUID",p.getPlayer().getUniqueId().toString());add(Data.field.KitNAME.getWhere(),p.getGame().getTotalmode().name()+p.getKit().getType().name());}})){
            SkyWars.getDataBase().dbInsert(field.getTable().getWhere(),new KeyValue(){{
                add(Data.field.UUID.getWhere(),p.getPlayer().getUniqueId().toString());
                add(Data.field.KitNAME.getWhere(),p.getGame().getTotalmode().name()+p.getKit().getType().name());
            }});
        }
        SkyWars.getDataBase().dbUpdate(Data.JOB.getWhere(),new KeyValue(field.getWhere(),i),new KeyValue(){{
            add(Data.field.UUID.getWhere(),p.getPlayer().getUniqueId().toString());
            add(Data.field.KitNAME.getWhere(),p.getGame().getTotalmode().name()+p.getKit().getType().name());
        }});
    }
    public static void set(Data.field field,PlayerData pd,String s){
        Player player = pd.getPlayer();
        String uuid = player.getUniqueId().toString();
        if(!SkyWars.getDataBase().dbExist(Data.PLAYERINFO.getWhere(),new KeyValue("UUID",uuid))){
            SkyWars.getDataBase().dbInsert(Data.PLAYERINFO.getWhere(), new KeyValue("UUID", uuid));
        }
        SkyWars.getDataBase().dbUpdate(Data.PLAYERINFO.getWhere(), new KeyValue(field.getWhere(), s), new KeyValue("UUID", uuid));
                /*
            case PAJ:
                String pjptable = Data.PLAYERINFO.getWhere();
                SkyWars.getDataBase().dbUpdate(pjptable,new KeyValue(field.getWhere(),s),new KeyValue("UUID",uuid));
                String pjjtable = Data.JOB.getWhere();
                SkyWars.getDataBase().dbUpdate(pjjtable,new KeyValue(field.getWhere(),s),new KeyValue(){{ add("UUID",uuid);add("KitName",pd.getKit().getType().name()); }});

                 */
    }
    public static String get(Player p, Data.field field, GameMode gm, PerkType perkType){
        return SkyWars.getDataBase().dbSelectFirst(Data.PERK.getWhere(),field.getWhere(),new KeyValue(){{
            add("UUID",p.getUniqueId().toString());
            add(Data.field.PERKNAME.getWhere(),gm.name()+perkType.name());
        }});
    }
    public static String get(Player p, Data.field field, GameMode gm, KitType kitType){
        return SkyWars.getDataBase().dbSelectFirst(Data.JOB.getWhere(),field.getWhere(),new KeyValue(){{
            add("UUID",p.getUniqueId().toString());
            add("KitName",gm.name()+kitType.name());
        }});
    }
    public static void set(Player p,Data.field field,Integer i){
        //if(SkyWars.getDataBase().dbSelectFirst(field.getTable().getWhere(),field.getWhere(),new KeyValue("UUID",p.getUniqueId().toString())) == null) {
        if(!SkyWars.getDataBase().dbExist(field.getTable().getWhere(),new KeyValue("UUID",p.getUniqueId().toString()))){
            SkyWars.getDataBase().dbInsert(field.getTable().getWhere(),new KeyValue("UUID",p.getUniqueId().toString()));
        }
        SkyWars.getDataBase().dbUpdate(field.getTable().getWhere(),new KeyValue(field.getWhere(),i),new KeyValue("UUID",p.getUniqueId().toString()));
    }
}
