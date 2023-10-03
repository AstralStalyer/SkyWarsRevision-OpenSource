package cn.rmc.skywars.game;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.enums.Data;
import cn.rmc.skywars.enums.PlayerState;
import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.perk.PerkBasic;
import cn.rmc.skywars.perk.PerkType;
import cn.rmc.skywars.util.ActionBarUtils;
import cn.rmc.skywars.util.DataUtils;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.database.KeyValue;
import cn.rmc.skywars.util.json.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerData {
    @Getter
    private UUID uuid;

    @Getter
    private Player player;

    @Getter
    @Setter
    private Game game;

    @Getter
    @Setter
    private Game SpecGame;

    @Getter
    private KitBasic kit;

    @Getter
    private ArrayList<PerkBasic> perks = new ArrayList<>();

    @Getter
    @Setter
    private PlayerState state;

    @Getter
    @Setter
    private Long timeplayed;

    @Getter
    @Setter
    private Location deathloc;

    public PlayerData(Player p){
        player = p;
        uuid = p.getUniqueId();
        state = PlayerState.SPAWNING;
    }

    public void setKit(KitType kit, boolean isJoin){
        this.kit = kit.getKit(player);
        JSONObject oj;
        if(DataUtils.get(Data.field.CURRENTKIT,this) == null){
            oj = new JSONObject();
        }else{
            oj = new JSONObject(DataUtils.get(Data.field.CURRENTKIT,this));
        }
        oj.put(getGame().getMode().name(),kit.name());
        DataUtils.set(Data.field.CURRENTKIT,this,oj.toString());
        ActionBarUtils.sendActionBar(getPlayer(),"§e已选择的职业: §a"+ getKit().getName());
        if(DataUtils.get(Data.field.KitNAME,this) == null){
            SkyWars.getDataBase().dbInsert(Data.JOB.getWhere(),new KeyValue(){{add("KitName",game.getTotalmode().name()+getKit().getType().name());add("UUID",uuid.toString());}});
        }
        if (!isJoin) {
            player.sendMessage("§e你选择了" + kit.getDBName() + "职业!");
        }
    }

    public void giveWaitItem(){
        Inventory inventory = player.getInventory();
        inventory.clear();
        inventory.setItem(0,new ItemBuilder(Material.BOW).setName("§a职业选择 §7(右键点击)").toItemStack());
        inventory.setItem(8,new ItemBuilder(Material.BED).setName("§c返回大厅 §7(右键点击)").setLore("§7右键来离开并回到大厅").toItemStack());
    }

    public void giveSpecItem(){
        Inventory inv = player.getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.COMPASS).setName("§a传送器 §8(右键点击)").setLore("§7右键来观察玩家!").toItemStack());
        inv.setItem(4,new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§b旁观者设置 §8(右键点击)").setLore("§7右键点击更改你的旁观者设置!").toItemStack());
        inv.setItem(8,new ItemBuilder(Material.BED).setName("§c返回大厅 §7(右键点击)").setLore("§7右键来离开并回到大厅").toItemStack());
    }
    public void loadperk(){
        for (PerkType perkType : PerkType.values()) {
            if(DataUtils.get(getPlayer(), Data.field.PERKLEVEL,getGame().getTotalmode(),perkType) != null){
                PerkBasic b = perkType.getPerk(getPlayer(),Integer.valueOf(DataUtils.get(getPlayer(),Data.field.PERKLEVEL,getGame().getTotalmode(),perkType)));
                addPerk(b);
            }
        }
    }
    public void addPerk(PerkBasic pb){
        perks.add(pb);
    }
    public void clearPerk(){
        perks.forEach(PerkBasic::dispose);
    }
    public void savedata(){
        
        DataUtils.add(Data.field.KILL,this,getKills());
        DataUtils.add(Data.field.DEATH,this,getDeaths());
        DataUtils.add(Data.field.ASSIST,this,getAssists());
        DataUtils.add(Data.field.BLOCKS_BROKEN,this,getBlocks_broken());
        DataUtils.add(Data.field.BLOCKS_PLACED,this,getBlocks_palced());
        DataUtils.add(Data.field.EGGS_THROWN,this,getEggs_thrown());
        DataUtils.add(Data.field.ENDERPERLS_THROWN,this,getEnderperls_thrown());
        DataUtils.add(Data.field.MOBS_KILLED,this,getMobs_killed());
        DataUtils.add(Data.field.CHESTS_OPENED,this,getChests_opened());
        DataUtils.add(Data.field.HEADS_GATHERED,this,getHeads_gathered());
        DataUtils.add(Data.field.MELEE_KILL,this,getMelee_kills());
        DataUtils.add(Data.field.VOID_KILL,this,getVoid_kills());
        DataUtils.add(Data.field.KILLS_BY_MOBS,this,getKills_by_mobs());
        DataUtils.add(Data.field.BOW_KILL,this,getBow_kills());
        DataUtils.add(Data.field.BOW_HIT,this,getBow_hits());
        DataUtils.add(Data.field.BOW_SHOT,this,getBow_shots());
        DataUtils.add(Data.field.COIN,this,getCoins());
        DataUtils.add(Data.field.SOUL,this,getSoul());
        DataUtils.add(Data.field.EXPERIENCE,this,getExpenience());
        if(Double.parseDouble(DataUtils.get(Data.field.BOW_LONGEST_KILL,this) == null ? "0":DataUtils.get(Data.field.BOW_LONGEST_KILL,this)) < getBow_longest_kill()){
            DataUtils.set(Data.field.BOW_LONGEST_KILL,this, String.valueOf(getBow_longest_kill()));
        }
        if(Double.parseDouble(DataUtils.get(Data.field.BOW_LONGEST_SHOT,this) == null ? "0":DataUtils.get(Data.field.BOW_LONGEST_SHOT,this)) < getBow_longest_shot()){
            DataUtils.set(Data.field.BOW_LONGEST_SHOT,this, String.valueOf(getBow_longest_shot()));
        }
        if(win){
            DataUtils.add(Data.field.TIME_PLAYED,this, (int) (game.getEndtimestamp()-game.getStarttimestamp()));
        }else {
            DataUtils.add(Data.field.TIME_PLAYED, this, getTimeplayed().intValue());
        }
        if(win &&Integer.parseInt(DataUtils.get(Data.field.FASTEST_WIN,this) == null? "0":DataUtils.get(Data.field.FASTEST_WIN,this)) < getTimeplayed()){
            DataUtils.set(this,Data.field.FASTEST_WIN,getTimeplayed().intValue());
        }
        if(Integer.parseInt(DataUtils.get(Data.field.MOST_KILLS_IN_A_GAME,this) == null ? "0":DataUtils.get(Data.field.MOST_KILLS_IN_A_GAME,this)) < getKills()){
            DataUtils.set(this,Data.field.MOST_KILLS_IN_A_GAME,getKills());
        }
        if(win){
            DataUtils.add(Data.field.WIN,this,1);
        }else{
            DataUtils.add(Data.field.LOSS,this,1);
        }
    }
    @Getter
    @Setter
    private int kills= 0,assists= 0,deaths = 0,coins = 0,soul = 0,expenience = 0,blocks_broken= 0,blocks_palced= 0,eggs_thrown= 0,enderperls_thrown= 0,
            mobs_killed= 0,chests_opened= 0,heads_gathered= 0,melee_kills= 0,void_kills= 0,
            kills_by_mobs= 0,bow_kills= 0,bow_shots= 0,bow_hits= 0;
    @Getter
    @Setter
    private double bow_longest_kill= 0,bow_longest_shot = 0;
    @Getter
    @Setter
    private Boolean win = false;

}
