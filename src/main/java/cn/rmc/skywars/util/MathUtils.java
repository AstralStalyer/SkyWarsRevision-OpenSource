package cn.rmc.skywars.util;

import cn.rmc.skywars.game.PlayerData;

import java.util.*;

public class MathUtils {
    public static Boolean Chance(int chance){
        if(Math.random() * 100 < chance) {
            return true;
        }else {
            return false;
        }
    }
    public static List<Map.Entry<PlayerData,Integer>> gettop(Map<PlayerData, Integer> map){
        //Map<String, Integer> map = new TreeMap<>();
        List<Map.Entry<PlayerData,Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        List<Map.Entry<PlayerData,Integer>> l = new ArrayList<>();
        for(int i = list.size()-1;i>=0;i--){
            l.add(list.get(i));
        }
        return l;
    }
}
