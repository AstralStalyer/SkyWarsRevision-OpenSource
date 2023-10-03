package cn.rmc.skywars.task;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.util.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.Arrays;
import java.util.List;

public class GameStartTask implements Runnable{

    private final Game game;
    private int i = 40;
    private final List<Integer> tip = Arrays.asList(30,15,10,5,4,3,2,1);
    public GameStartTask(Game g){
        this.game = g;
    }
    @Override
    public void run() {
        game.setCount(i);
        if(10 < i && game.getPlayers().size() >= game.getMaxPlayer()-3){
            i = 10;
        }
        if(i >0){
            game.getPlayers().forEach(playerData -> {
                if(tip.contains(i)) {
                    if(i >= 15){
                        playerData.getPlayer().sendMessage("§e游戏将在§a" + i + "§e秒后开始!");
                    }else if(i == 10){
                        playerData.getPlayer().sendMessage("§e游戏将在§6" + i + "§e秒后开始!");
                        TitleUtils.sendFullTitle(playerData.getPlayer(),0,20*3,0,"§e10秒","§e右键点击弓来选择一个职业!");
                    }else {
                        playerData.getPlayer().sendMessage("§e游戏将在§c" + i + "§e秒后开始!");
                        TitleUtils.sendFullTitle(playerData.getPlayer(),0,20*3,0,"§c"+i,"§e准备战斗吧!");
                    }
                    SkyWars.getInstance().getScoreBoardManager().updatescoreboard();
                    playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.NOTE_STICKS, 1, 1);
                }
            });
        }else{
            Bukkit.getScheduler().runTask(SkyWars.getInstance(), game::start);
        }
        i--;
    }
}
