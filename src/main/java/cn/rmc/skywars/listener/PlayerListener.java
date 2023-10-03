package cn.rmc.skywars.listener;

import cn.rmc.skywars.Inventory.MenuBasic;
import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.enums.GameState;
import cn.rmc.skywars.enums.PlayerState;
import cn.rmc.skywars.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

import java.util.*;

public class PlayerListener implements Listener {

    protected static HashMap<PlayerData,PlayerData> lastDamage = new HashMap<>();
    protected static HashMap<PlayerData,HashMap<PlayerData,Long>> damagetime = new HashMap<>();
    public static HashMap<Entity,PlayerData> entityby = new HashMap<>();
    public static HashMap<Player, UUID> lastarrow = new HashMap<>();
    public static HashMap<Player, EntityDamageEvent.DamageCause> lastcause = new HashMap<>();

    @EventHandler
    public void onLoing(PlayerLoginEvent e){
        Game join = SkyWars.getInstance().getGameManager().getGameArrayList().get(0);
        if (join != null) {
            if (join.getState() == GameState.FIGHTING) {
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, "游戏已开始");
            }
        } else {
            e.getPlayer().sendMessage("§c这个服务端还没有被配置！");
        }

    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        SkyWars.getInstance().getPlayerManager().Create(p);
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        Game join= SkyWars.getInstance().getGameManager().getGameArrayList().get(0);
        if (join != null) {
            p.teleport(join.getMiddle());
            for (PlayerData pdd : join.getPlayers()) {
                TagUtils.setTag(p, pdd.getPlayer(), LuckPerms.getPrefix(p), "");
            }
            join.addPlayer(pd);
            SkyWars.getInstance().getScoreBoardManager().updatescoreboard(p);
            Bukkit.getScheduler().runTaskLater(SkyWars.getInstance(), () -> {
                for (PlayerData pdd : join.getPlayers()) {
                    TagUtils.setTag(pdd.getPlayer(), p, LuckPerms.getPrefix(pdd.getPlayer()), "");
                }
            }, 2L);
            Bukkit.getScheduler().runTaskLaterAsynchronously(SkyWars.getInstance(), () -> {
                TitleUtils.sendFullTitle(p, 20, 60, 20, "§e空岛战争", "§a普通模式");
                Bukkit.getScheduler().runTaskLaterAsynchronously(SkyWars.getInstance(), () -> {
                    TitleUtils.sendFullTitle(p, 20, 60, 20, "§e地图:" + join.getMap().name, "§a作者: " + join.getMap().author);
                }, 60L);
            }, 60L);
        }


        //SkyWars.getInstance().getGameManager().getGames().get("tree").addPlayer(pd);
    }
    @EventHandler
    public void onAttackbySPEC(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player )) return;
        if(SkyWars.getInstance().getPlayerManager().get((Player)e.getDamager()).getState() == PlayerState.SPECING){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onattackbyentity(EntityDamageByEntityEvent e) {


        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player)e.getEntity();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        if(!(e.getDamager() instanceof Player)){
            if(entityby.containsKey(e.getDamager())){
                if(entityby.get(e.getDamager()) == pd){
                    e.setCancelled(false);
                }
            }
            if(e.getDamager().getType() == EntityType.ARROW){
                if(((Arrow)e.getDamager()).getShooter() instanceof Player) {
                    Player damager = (Player) ((Arrow) e.getDamager()).getShooter();
                    PlayerData damagerpd = SkyWars.getInstance().getPlayerManager().get(damager);
                    lastarrow.put((Player)((Arrow) e.getDamager()).getShooter(),e.getDamager().getUniqueId());
                    lastcause.put(p, EntityDamageEvent.DamageCause.PROJECTILE);
                    setdamage(pd, damagerpd);
                    return;
                }else{
                    Entity entity = (Entity) ((Arrow) e.getDamager()).getShooter();
                    if(entityby.containsKey(entity)){
                        if(entityby.get(entity) == pd) {
                            e.setCancelled(true);
                        }else{
                            setdamage(pd,entityby.get(entity));
                            lastcause.put(p, EntityDamageEvent.DamageCause.CUSTOM);
                        }
                    }
                }

            }
            if(e.getDamager().getType() == EntityType.SMALL_FIREBALL){
                Entity entity = (Entity) ((SmallFireball) e.getDamager()).getShooter();
                if(entityby.containsKey(entity)){
                    if(entityby.get(entity) == pd) {
                        e.setCancelled(true);
                    }else{
                        setdamage(pd,entityby.get(entity));
                        lastcause.put(p, EntityDamageEvent.DamageCause.CUSTOM);
                    }
                }
            }
            if(e.getDamager().getType() == EntityType.SNOWBALL){
                Player player = (Player)((Snowball)e.getDamager()).getShooter();
                PlayerData damagerpd = SkyWars.getInstance().getPlayerManager().get(player);
                setdamage(pd,damagerpd);
            }
            if(e.getDamager().getType() == EntityType.FISHING_HOOK){
                Player player = (Player)((FishHook)e.getDamager()).getShooter();
                PlayerData damagerpd = SkyWars.getInstance().getPlayerManager().get(player);
                setdamage(pd,damagerpd);
            }
        }
        if(e.getDamager() instanceof Player){
            PlayerData damagerpd = SkyWars.getInstance().getPlayerManager().get((Player) e.getDamager());
            if(!e.isCancelled()) {
                setdamage(pd, damagerpd);
            }
        }

    }
    @EventHandler
    public void ondeath(PlayerDeathEvent e){
        e.setDeathMessage(null);
        Player p = e.getEntity();
        e.getDrops().forEach(itemStack -> p.getWorld().dropItem(p.getLocation(),itemStack));
        e.getDrops().clear();

        p.setFireTicks(0);
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        p.setHealth(20);
        switch (pd.getState()){
            case FIGHTING:
                //pd.getGame().addkill(lastDamage.get(pd));
                pd.setDeathloc(p.getLocation());
                ArrayList<PlayerData> assists = new ArrayList<>();
                if(damagetime.containsKey(pd)) {
                    for (PlayerData pdd : damagetime.get(pd).keySet()) {
                        if (System.currentTimeMillis() - damagetime.get(pd).get(pdd) <= 15000) {
                            assists.add(pdd);
                        }
                    }
                }
                pd.getGame().addFight(pd,lastDamage.get(pd),assists,pd.getPlayer().getLastDamageCause().getCause());
                pd.getGame().addspecer(pd);
                pd.getGame().detectend();
                for(Entity entity:entityby.keySet()){
                    Creature creature = (Creature) entity;
                    creature.setTarget(null);
                }
                SkyWars.getInstance().getScoreBoardManager().updatescoreboard();
        }
    }
    @EventHandler
    public void onmove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        switch (pd.getState()){
            case FIGHTING:
                if(e.getTo().getY() <= -50){
                    if(pd.getState() == PlayerState.FIGHTING){
                        switch (pd.getGame().getState()){
                            case WAITING:
                            case COUNTING:
                                pd.getPlayer().teleport(pd.getGame().getPlayerspawns().get(pd));
                                return;
                            case FIGHTING:
                                ArrayList<PlayerData> assists = new ArrayList<>();
                                if(damagetime.containsKey(pd)) {
                                    for (PlayerData pdd : damagetime.get(pd).keySet()) {
                                        if (System.currentTimeMillis() - damagetime.get(pd).get(pdd) <= 10000) {
                                            assists.add(pdd);
                                        }
                                    }
                                }
                                pd.setDeathloc(p.getLocation());
                                lastcause.put(p, EntityDamageEvent.DamageCause.VOID);
                                pd.getGame().addFight(pd, lastDamage.get(pd),assists,EntityDamageEvent.DamageCause.VOID);
                                pd.getGame().addspecer(pd);
                                pd.getGame().detectend();
                                for(Entity entity:entityby.keySet()){
                                    Creature creature = (Creature) entity;
                                    creature.setTarget(null);
                                }
                                SkyWars.getInstance().getScoreBoardManager().updatescoreboard();
                                break;
                        }
                    }
                }
            case SPECING:
                if(e.getTo().getY() <= -50) {
                    pd.getPlayer().teleport(pd.getGame().getMiddle());
                }
        }
    }
    @EventHandler
    public void onEntitymoveaway(PlayerMoveEvent e){
        for(Entity entity:entityby.keySet()){
            PlayerData pd = entityby.get(entity);
            Player p = pd.getPlayer();
            if(p.getLocation().distance(entity.getLocation()) > 20){
                entity.teleport(p.getLocation());
            }
        }
    }
    @EventHandler
    public void ondamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;;
        Player p = (Player) e.getEntity();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        switch (pd.getState()){
            case FIGHTING:
                Game g = pd.getGame();
                switch (g.getState()){
                    case WAITING:
                    case COUNTING:
                    case ENDING:
                        e.setCancelled(true);
                        break;
                    case FIGHTING:
                        if(System.currentTimeMillis() - g.getStarttimestamp() < 3000){
                            e.setCancelled(true);
                        }
                        break;
                }
                break;
            case SPECING:
                e.setCancelled(true);
                break;
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        if(MenuBasic.uis.containsKey(e.getPlayer())){
            MenuBasic.uis.get(e.getPlayer()).forEach(MenuBasic::destery);
        }
        switch (pd.getState()){
            case FIGHTING:
                Game g = pd.getGame();
                switch (g.getState()){
                    case WAITING:
                    case COUNTING:
                        pd.getGame().getPlayerlist().remove(pd);
                        break;
                    case FIGHTING:
                        pd.setTimeplayed(System.currentTimeMillis()-g.getStarttimestamp());
                        setdamage(pd, lastDamage.getOrDefault(pd, null));
                }
            case SPECING:
                pd.getGame().removePlayer(pd);
                pd.getGame().detectend();
                break;
            case SPAWNING:
        }
        SkyWars.getInstance().getPlayerManager().remove(p);
    }
    @EventHandler
    public void onInter(PlayerInteractEvent e){
        Player p = e.getPlayer();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        if(pd.getState() == PlayerState.SPECING && e.getItem() == null){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInt(PlayerInteractEvent e){
        if(e.getMaterial() != Material.MONSTER_EGG || e.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }
        e.setCancelled(true);
        Player p = e.getPlayer();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        EntityType et = ((SpawnEgg)e.getItem().getData()).getSpawnedType();
        if(et == null){
            List<EntityType> type = Arrays.asList(EntityType.ZOMBIE,EntityType.SKELETON,EntityType.WOLF,EntityType.CHICKEN,EntityType.GHAST,EntityType.OCELOT,EntityType.SNOWMAN,
                    EntityType.RABBIT,EntityType.WITHER_SKULL,EntityType.ENDERMITE,EntityType.GUARDIAN);
            et = type.get(ChestUtils.getrandomint(0,type.size()-1));
        }
        Location loc = e.getClickedBlock().getLocation();
        loc.setX(loc.getX()+0.5);
        loc.setZ(loc.getZ()+0.5);
        switch (e.getBlockFace()){
            case UP:
                loc.setY(loc.getY()+1);
                break;
            case DOWN:
                loc.setY(loc.getY()-2);
                break;
            case EAST:
                loc.setX(loc.getX()+1);
                break;
            case SOUTH:
                loc.setZ(loc.getZ()+1);
                break;
            case WEST:
                loc.setX(loc.getX()-1);
                break;
            case NORTH:
                loc.setZ(loc.getZ()-1);
                break;
        }
        ItemStack hand =e.getPlayer().getItemInHand();
        hand.setAmount(hand.getAmount() - 1);
        e.getPlayer().setItemInHand(hand);
        Entity entity = loc.getWorld().spawnEntity(loc,et);
        if(entity.getType() == EntityType.CREEPER){
            ((Creeper)entity).setPowered(true);
        }
        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setCanPickupItems(true);
        entityby.put(entity,pd);

    }
    @EventHandler
    public void onHungry(FoodLevelChangeEvent e){
        if(!(e.getEntity() instanceof Player))return;
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get((Player)e.getEntity());
        switch (pd.getState()){
            case SPECING:
            case SPAWNING:
                e.setCancelled(true);
                break;
            case FIGHTING:
                Game g = pd.getGame();
                switch (g.getState()){
                    case FIGHTING:
                        if(MathUtils.Chance(45)){
                            e.setCancelled(true);
                        }
                        break;
                    default:
                        e.setCancelled(true);
                }
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(e.getPlayer());
        switch (pd.getState()){
            case SPECING:
            case SPAWNING:
                e.setCancelled(true);
                break;
            case FIGHTING:
                Game g = pd.getGame();
                switch (g.getState()){
                    case COUNTING:
                    case WAITING:
                    case ENDING:
                        e.setCancelled(true);
                        break;
                }
        }
    }
    public void setdamage(PlayerData be,PlayerData damager){
        lastDamage.put(be,damager);
        if(damagetime.containsKey(be)){
            damagetime.get(be).put(damager,System.currentTimeMillis());
        }else{
            HashMap<PlayerData,Long> result = new HashMap<>();
            result.put(damager,System.currentTimeMillis());
            damagetime.put(be,result);
        }

    }
}
