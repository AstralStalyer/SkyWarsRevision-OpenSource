package cn.rmc.skywars.task;

import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.game.PlayerData;
import org.bukkit.entity.Player;

public class NearestPlayerTask implements Runnable {

    Game g;

    public NearestPlayerTask(Game g){
        this.g = g;
    }
    @Override
    public void run() {
        for(PlayerData playerData:g.getAliveplayers()){
            Player player = playerData.getPlayer();
            Player result = null;
            double lastDistance = Double.MAX_VALUE;
            for(PlayerData pdd : g.getAliveplayers()) {
                Player p = pdd.getPlayer();
                if(player == p) continue;
                double distance = player.getLocation().distance(p.getLocation());
                if(distance < lastDistance) {
                    lastDistance = distance;
                    result = p;
                }
            }
            if(result != null) {
                player.setCompassTarget(result.getLocation());
            }
        }

    }
}
