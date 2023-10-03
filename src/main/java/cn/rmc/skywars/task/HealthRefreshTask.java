package cn.rmc.skywars.task;

import cn.rmc.skywars.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class HealthRefreshTask implements Runnable {

    Game g;
    public HealthRefreshTask(Game g){
        this.g = g;
    }

    @Override
    public void run() {
        g.getPlayers().forEach(playerData -> {
            Player p = playerData.getPlayer();
            Scoreboard scoreBoard = p.getScoreboard();
            if(scoreBoard.getObjective("healthb") == null){
                scoreBoard.registerNewObjective("healthb","dummy");
                scoreBoard.getObjective("healthb").setDisplayName("§c❤");
                scoreBoard.getObjective("healthb").setDisplaySlot(DisplaySlot.BELOW_NAME);
            }
            if(scoreBoard.getObjective("healthp") == null){
                scoreBoard.registerNewObjective("healthp","dummy");
                //scoreBoard.getObjective("healthp").setDisplayName("§c❤");
                scoreBoard.getObjective("healthp").setDisplaySlot(DisplaySlot.PLAYER_LIST);
            }


            Bukkit.getOnlinePlayers().forEach(player -> {
                Objective bb = scoreBoard.getObjective("healthb");
                Objective pp = scoreBoard.getObjective("healthp");
                if((int)player.getHealth() != bb.getScore(player.getName()).getScore()) {
                    bb.getScore(player.getName()).setScore((int) player.getHealth());
                }
                if((int)player.getHealth() != pp.getScore(player.getName()).getScore()) {
                    pp.getScore(player.getName()).setScore((int) player.getHealth());
                }
            });

        });
    }
}
