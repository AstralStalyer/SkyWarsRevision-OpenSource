package cn.rmc.skywars.manager;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.Game;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    @Getter
    private final HashMap<String,Game> games = new HashMap<>();
    @Getter
    private final ArrayList<Game> gameArrayList = new ArrayList<>();

    public GameManager(){
        load();
    }

    public void load(){
        SkyWars.getInstance().getMapManager().data.forEach(map -> {
            System.out.println("成功加载地图 "+ map.name);
            Game result = new Game(map);
            games.put(map.name.toLowerCase(),result);
            gameArrayList.add(result);
        });

        for (String s : games.keySet()){
            System.out.println(s);
            System.out.println(games.get(s));
        }
    }
}
