package cn.rmc.skywars.manager;


import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.task.ScoreBoardTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreBoardManager {

    public ScoreBoardManager(){
        Bukkit.getScheduler().runTaskTimer(SkyWars.getInstance(),()->{
            Bukkit.getOnlinePlayers().forEach(player ->
                    Bukkit.getScheduler().runTask(SkyWars.getInstance(),new ScoreBoardTask(player)));
        },0L,5L);
    }

    public void updatescoreboard(){

        Bukkit.getOnlinePlayers().forEach(player -> Bukkit.getScheduler().runTask(SkyWars.getInstance(),new ScoreBoardTask(player)));
    }
    public void updatescoreboard(Player player){
        Bukkit.getScheduler().runTask(SkyWars.getInstance(),new ScoreBoardTask(player));
    }
}