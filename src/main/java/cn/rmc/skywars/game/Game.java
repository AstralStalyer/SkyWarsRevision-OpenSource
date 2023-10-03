package cn.rmc.skywars.game;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.enums.*;
import cn.rmc.skywars.event.GameEndEvent;
import cn.rmc.skywars.event.GameStartEvent;
import cn.rmc.skywars.event.PlayerKillEvent;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.task.*;
import cn.rmc.skywars.util.*;
import cn.rmc.skywars.util.json.JSONObject;
import com.boydti.fawe.util.TaskManager;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.*;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Game {

    private static final SkyWars plugin = SkyWars.getInstance();


    @Getter
    private final Map map;
    @Getter
    private cn.rmc.skywars.game.GameMode mode;
    @Getter
    private cn.rmc.skywars.game.GameMode totalmode;

    @Getter
    private final HashMap<Location,Boolean> spawns = new HashMap<>();
    @Getter
    private final HashMap<PlayerData,Location> playerspawns = new HashMap<>();
    @Getter
    private final ArrayList<PlayerData> playerlist = new ArrayList<>();
    @Getter
    private final ArrayList<PlayerData> players = new ArrayList<>();
    @Getter
    private final ArrayList<PlayerData> aliveplayers = new ArrayList<>();
    @Getter
    private final int countplayer = 3;

    @Getter
    @Setter
    private GameState state;
    @Getter
    @Setter
    private int count = 30;
    private BukkitTask countdown;
    private BukkitTask refreshspawn;
    private BukkitTask waiting;
    @Getter
    private BukkitTask Eventtask;
    @Getter
    private BukkitTask HealthRefreshtask;
    @Getter
    @Setter
    int EventLeftTime = 0;
    @Getter
    @Setter
    GameEvent Event = null;

    @Getter
    private final HashMap<PlayerData,Integer> kills = new HashMap<>();
    @Getter
    private final HashMap<PlayerData,Integer> assists = new HashMap<>();
    @Getter
    private final HashMap<PlayerData,Boolean> isDeath = new HashMap<>();
    @Getter
    private Long starttimestamp = 0L;
    @Getter
    private Long endtimestamp = 0L;
    @Getter
    private Location middle;
    public Game(Map map){
        this.map = map;
        setState(GameState.WAITING);
        for(Location loc : map.locations){
            spawns.put(loc,true);
        }
        mode = map.mode;
        if(mode.name().toUpperCase().contains("NORMAL")){
            totalmode = cn.rmc.skywars.game.GameMode.NORMAL;
        }else if(mode.name().toUpperCase().contains("INSANE")){
            totalmode = cn.rmc.skywars.game.GameMode.INSANE;
        }else {
            totalmode = mode;
        }

        this.middle = map.middle;
        waiting = Bukkit.getScheduler().runTaskTimer(SkyWars.getInstance(),()->{
            players.forEach(playerData -> ActionBarUtils.sendActionBar(playerData.getPlayer(),"§e已选择的职业: §a"+ playerData.getKit().getName()));
        },0L,10L);
    }

    public void start(){
        MinecraftServer.getServer().setMotd("Fighting");
        players.forEach(playerData -> {
            playerData.getPlayer().closeInventory();
            playerData.getPlayer().sendMessage(new String[]{
                    "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                    "                                   §f§l空岛战争",
                    "",
                    "                           §e§l在岛上收集资源和装备",
                    "                   §e§l登上中心岛获得特殊箱子和特殊物品",
                    "                           §e§l以消灭所有其他的玩家",
                    "",
                    "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",



            });
        });
        Bukkit.getPluginManager().callEvent(new GameStartEvent(this));
        waiting.cancel();
        players.forEach(playerData->{
            kills.put(playerData,0);
            assists.put(playerData,0);
            isDeath.put(playerData,false);
        });
        Eventtask = Bukkit.getScheduler().runTaskTimer(plugin,new GameEventTask(this),0L,20L);
        if(countdown != null) {
            countdown.cancel();
        }
        starttimestamp = System.currentTimeMillis();
        state = GameState.FIGHTING;
        Bukkit.getScheduler().runTaskAsynchronously(plugin,new ChestFillTask(this));
        for(Location loc:spawns.keySet()){
            Bukkit.getScheduler().runTaskAsynchronously(plugin,new CreateCageTask(loc,Cages.AIR){
                @Override public void finall() {}
            });
            players.forEach(PlayerData->{
                PlayerData.setState(PlayerState.FIGHTING);
                PlayerData.getKit().GiveItem();
            });
        }
        Bukkit.broadcastMessage("§e笼子打开了，开始战斗吧!");
        for(PlayerData pd: getPlayers()){
            for (PlayerData pdd: getPlayers()){
                TagUtils.setTag(pdd.getPlayer(),pd.getPlayer(), "§c","");
                TagUtils.setTag(pd.getPlayer(),pdd.getPlayer(), "§c","");
            }
        }
        HealthRefreshtask = Bukkit.getScheduler().runTaskTimer(plugin,new HealthRefreshTask(this),0L,5L);

        refreshspawn = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,new NearestPlayerTask(this),0L,20L);
    }

    public void end(){
        Player winner = aliveplayers.get(0).getPlayer();
        TitleUtils.sendFullTitle(winner,0,20*3,0,"§6胜利","§f你是本场游戏的胜利者");
        players.forEach(playerData -> playerData.getPlayer().sendMessage(new String[]{
                "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "                                   §a空岛战争",
                "",
                "                           §e胜利者  "+LuckPerms.getPrefix(winner)+winner.getPlayer().getName(),
                ""
        }));
        close();
    }
    public void forceend(){
        players.forEach(playerData -> playerData.getPlayer().sendMessage(new String[]{
                "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "                                   §a空岛战争",
                "",
                "                           §e胜利者  平局",
                ""
        }));
        close();
    }
    public void close(){
        HashMap<PlayerData,Integer> maps = new HashMap<>();
        playerlist.forEach(playerData -> maps.put(playerData,playerData.getKills()));
        List<java.util.Map.Entry<PlayerData,Integer>> l = MathUtils.gettop(maps);
        java.util.Map.Entry<PlayerData,Integer> one = l.size() < 1 ? null:l.get(0);
        java.util.Map.Entry<PlayerData,Integer> two = l.size() < 2 ? null:l.get(1);
        java.util.Map.Entry<PlayerData,Integer> three = l.size() < 3 ? null:l.get(2);
        players.forEach(playerData -> playerData.getPlayer().sendMessage(new String[]{
                "                   §a击杀数第一名 §7- "+(one == null ? "null":(LuckPerms.getPrefix(one.getKey().getPlayer())+one.getKey().getPlayer().getName() + " §7- "+one.getValue())),
                "                   §6击杀数第二名 §7- "+(two == null ? "null":(LuckPerms.getPrefix(two.getKey().getPlayer())+two.getKey().getPlayer().getName() + " §7- "+two.getValue())),
                "                   §c击杀数第三名 §7- "+ (three == null ? "null":(LuckPerms.getPrefix(three.getKey().getPlayer())+three.getKey().getPlayer().getName() + " §7- "+three.getValue())),
                "",
                "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"

        }));
        MinecraftServer.getServer().setMotd("Ending");
        endtimestamp = System.currentTimeMillis();
        Bukkit.getPluginManager().callEvent(new GameEndEvent(this));
        state = GameState.ENDING;
        setEvent(GameEvent.End);
        refreshspawn.cancel();
        Eventtask.cancel();
        Bukkit.getScheduler().runTaskLater(SkyWars.getInstance(),()->{
            players.forEach(playerData -> BungeeUtil.sendPlayer(playerData.getPlayer(),"SkyWarsLobby"));
            playerlist.clear();
            Bukkit.getScheduler().runTaskLater(plugin,Bukkit::shutdown,40L);
            },120L);
    }

    public void addPlayer(PlayerData pd){
        if(isFull()){
            pd.getPlayer().sendMessage("该局已满人");
            return;
        }
        if(players.contains(pd)){
            pd.getPlayer().sendMessage("你已经在游戏里了");
            return;
        }
        pd.setGame(this);
        pd.loadperk();
        if(DataUtils.get(Data.field.CURRENTKIT,pd) == null){
            pd.setKit(KitType.Default,true);
        }else if(!new JSONObject(DataUtils.get(Data.field.CURRENTKIT, pd)).keySet().contains(mode.name()) || new JSONObject(DataUtils.get(Data.field.CURRENTKIT,pd)).get(mode.name()) == null){
            pd.setKit(KitType.Default,true);
        }else{
            pd.setKit(KitType.valueOf(new JSONObject(DataUtils.get(Data.field.CURRENTKIT,pd)).getString(mode.name())),true);
        }
        pd.getPlayer().setGameMode(GameMode.SURVIVAL);
        ActionBarUtils.sendActionBar(pd.getPlayer(),"§e已选择的职业: §a"+ pd.getKit().getName());
        pd.setState(PlayerState.FIGHTING);

        for(Location loc : spawns.keySet()){
            if(spawns.get(loc)){
                spawns.put(loc,false);
                playerspawns.put(pd,loc);
                break;
            }
        }
        players.add(pd);
        playerlist.add(pd);
        aliveplayers.add(pd);
        players.forEach(playerData -> playerData.getPlayer().sendMessage(pd.getPlayer().getName()+"§e加入了游戏 (§b"+players.size()+"§e/§b"+getMaxPlayer()+"§e)"));
        pd.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        pd.giveWaitItem();
        Location loc = playerspawns.get(pd);
        TaskManager.IMP.async(new CreateCageTask(loc,Cages.DEFAULT){
            @Override
            public void finall() {
                Location get = loc.clone();
                get.setY(loc.getY()+0.5);
                pd.getPlayer().teleport(loc);
            }

        });

        if(players.size() >= countplayer && state == GameState.WAITING){
            setState(GameState.COUNTING);
            countdown = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin ,new GameStartTask(this),20L,20L);
        }
    }
    public void removePlayer(PlayerData pd){
        spawns.put(playerspawns.get(pd),true);
        String color = ChatColor.getLastColors(LuckPerms.getPrefix(pd.getPlayer()));
        players.forEach(playerData -> playerData.getPlayer().sendMessage(color+ pd.getPlayer().getName()+"§e已退出!"));
        playerspawns.remove(pd);
        aliveplayers.remove(pd);
        players.remove(pd);
        switch (state){
            case WAITING:
                players.remove(pd);
                break;
            case COUNTING:
                players.remove(pd);
                if(players.size() < countplayer){
                    countdown.cancel();
                    setState(GameState.WAITING);
                }
                break;
        }



    }
    public void addspecer(PlayerData pd){
        pd.getGame().aliveplayers.remove(pd);
        pd.getPlayer().getInventory().clear();
        pd.setState(PlayerState.SPECING);
        pd.setSpecGame(this);
        pd.getPlayer().teleport(pd.getGame().getPlayerspawns().get(pd));
        Player p =pd.getPlayer();
        PlayerUtils.setFly(pd);
        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));
        p.setFoodLevel(20);
        p.setHealth(20);
        //
        Bukkit.getScheduler().runTaskLater(SkyWars.getInstance(), pd::giveSpecItem,5L);
        //
        p.updateInventory();
        p.spigot().setCollidesWithEntities(false);
        Scoreboard score = p.getScoreboard();
        Team team = score.getTeam("spec");
        if(team == null){
            team = score.registerNewTeam("spec");
            team.setPrefix("§7");
            team.setNameTagVisibility(NameTagVisibility.ALWAYS);
            team.setCanSeeFriendlyInvisibles(false);
        }

        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,99999,1,false,false));
        for(PlayerData playerData: pd.getGame().getPlayers()){
            if(playerData.getState() == PlayerState.SPECING) {
                team.addEntry(playerData.getPlayer().getName());
                Team spect = playerData.getPlayer().getScoreboard().getTeam("spec");
                spect.addEntry(p.getName());
                pd.getPlayer().showPlayer(playerData.getPlayer());
            }else{
                playerData.getPlayer().hidePlayer(pd.getPlayer());
            }

        }
    }
    public Boolean isFull() {
        return players.size() >= spawns.size();
    }
    public void addFight(PlayerData playerData, PlayerData killer, ArrayList<PlayerData> assist, EntityDamageEvent.DamageCause cause){
        Bukkit.getPluginManager().callEvent(new PlayerKillEvent(killer,playerData,assist,cause));
        if(playerData != null){
            isDeath.put(playerData,true);
        }
        if(killer != null){
            kills.put(killer,kills.get(killer)+1);
        }
        if(assist.size() != 0){
            assist.forEach(ass -> {
                if(ass != killer) {
                    assists.put(ass, assists.get(ass) + 1);
                }
            });
        }
        assert playerData != null;

        if (killer != null) {
            switch (cause) {
                case ENTITY_ATTACK:
                    Bukkit.broadcastMessage("§7" + playerData.getPlayer().getName() + "§e被§7" + (killer == null ? null : killer.getPlayer().getName()) + "§e击杀");
                    break;
                case VOID:
                    Bukkit.broadcastMessage("§7" + playerData.getPlayer().getName() + "§e被§7" + (killer == null ? null : killer.getPlayer().getName()) + "§e扔下了虚空");
                    break;
                case FALL:
                    Bukkit.broadcastMessage("§7" + playerData.getPlayer().getName() + "§e被§7" + (killer == null ? null : killer.getPlayer().getName()) + "§e扔下了悬崖");
                    break;
                case PROJECTILE:
                    Bukkit.broadcastMessage("§7" + playerData.getPlayer().getName() + "§e被§7" + (killer == null ? null : killer.getPlayer().getName()) + "§e射杀");
                    break;
                default:
                    Bukkit.broadcastMessage("§7" + playerData.getPlayer().getName() + "§e死了");
                    break;
            }
        } else {
            Bukkit.broadcastMessage("§7" + playerData.getPlayer().getName() + "§e死了");
        }
    }
    /*
    public void addkill(PlayerData pd){
        System.out.println(pd);;
        if(kills.containsKey(pd)) {
            System.out.println("okk");
            kills.put(pd, kills.get(pd) + 1);
        }
    }

     */
    public void detectend(){
        if(state != GameState.FIGHTING) return;
        if(aliveplayers.size() == 1){
            end();
        }
    }
    public int getMaxPlayer(){
        return spawns.size();
    }

}
