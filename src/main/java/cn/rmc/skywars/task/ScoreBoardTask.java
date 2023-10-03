package cn.rmc.skywars.task;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.enums.GameEvent;
import cn.rmc.skywars.enums.GameState;
import cn.rmc.skywars.util.ScoreBoardUtils;
import cn.rmc.skywars.util.TimeUtils;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreBoardTask implements Runnable{

    Player player;

    public ScoreBoardTask(Player p){
        this.player = p;
    }
    @Override
    public void run() {
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(player);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd");
        String format = simpleDateFormat.format(new Date());
        String ip = "§ewww.hypixel.net";
        switch (pd.getState()){
            case SPAWNING:
                break;
            case FIGHTING:
                Game g = pd.getGame();
                switch (g.getState()){
                    case WAITING:
                        new ScoreBoardUtils().SidebarDisplay(player,new String[]{
                                "§e§l空岛战争",
                                "§7"+format,
                                " ",
                                "§f玩家: §a"+ g.getPlayers().size()+"/"+g.getMaxPlayer(),
                                "  ",
                                "§f等待中...",
                                "   ",
                                "§f地图: §a"+g.getMap().name,
                                "§f模式: " + g.getMap().mode.getDisplayName(),
                                "    ",
                                ip
                        });
                        break;
                    case COUNTING:
                        new ScoreBoardUtils().SidebarDisplay(player,new String[]{
                                "§e§l空岛战争",
                                "§7"+format,
                                " ",
                                "§f玩家: §a"+ g.getPlayers().size()+"/"+g.getMaxPlayer(),
                                "  ",
                                "§f即将开始: §a"+g.getCount()+"秒",
                                "   ",
                                "§f地图: §a"+g.getMap().name,
                                "§f模式: " + g.getMap().mode.getDisplayName(),
                                "    ",
                                ip
                        });
                        break;
                    case FIGHTING:
                    case ENDING:
                        new ScoreBoardUtils().SidebarDisplay(player,new String[]{
                                "§e§l空岛战争",
                                "§7"+format,
                                " ",
                                "§f下个事件:",
                                "§a"+g.getEvent().displayname+" "+ (g.getState() == GameState.ENDING ? "":TimeUtils.formatIntoMMSS(g.getEventLeftTime())),
                                "  ",
                                "§f存活玩家: §a"+g.getAliveplayers().size(),
                                "   ",
                                "§f击杀数: §a"+g.getKills().get(pd),
                                "    ",
                                "§f地图: §a"+g.getMap().name,
                                "§f模式: " + g.getMap().mode.getDisplayName(),
                                "     ",
                                ip
                        });
                        break;
                }
                break;
            case SPECING:
                Game gg = pd.getSpecGame();
                new ScoreBoardUtils().SidebarDisplay(player,new String[]{
                        "§e§l空岛战争",
                        "§7"+format,
                        " ",
                        "§f下个事件:",
                        "§a"+ gg.getEvent().displayname+" "+ (gg.getEvent() == GameEvent.End ? "":TimeUtils.formatIntoMMSS(gg.getEventLeftTime())),
                        "  ",
                        "§f存活玩家: §a"+gg.getAliveplayers().size(),
                        "   ",
                        "§f击杀数: §a"+gg.getKills().get(pd),
                        "    ",
                        "§f地图: §a"+gg.getMap().name,
                        "§f模式: " + gg.getMap().mode.getDisplayName(),
                        "     ",
                        ip
                });
                break;
        }

    }
}
