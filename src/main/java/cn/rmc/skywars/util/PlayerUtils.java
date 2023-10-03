package cn.rmc.skywars.util;

import cn.rmc.skywars.game.PlayerData;

public class PlayerUtils {

    public static void setFly(PlayerData pd){
        try {
            pd.getPlayer().setAllowFlight(true);
            pd.getPlayer().getClass().getDeclaredMethod("getHandle").setAccessible(true);
            pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer()).getClass().getSuperclass().getDeclaredField("abilities").setAccessible(true);
            pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer()).getClass().getSuperclass().getDeclaredField("abilities").get(pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer())).getClass().getDeclaredField("isFlying").setAccessible(true);
            pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer()).getClass().getSuperclass().getDeclaredField("abilities").get(pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer())).getClass().getDeclaredField("isFlying").set(pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer()).getClass().getSuperclass().getDeclaredField("abilities").get(pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer())),true);
            pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer()).getClass().getSuperclass().getDeclaredMethod("updateAbilities").setAccessible(true);
            pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer()).getClass().getSuperclass().getDeclaredMethod("updateAbilities").invoke(pd.getPlayer().getClass().getDeclaredMethod("getHandle").invoke(pd.getPlayer()));
        }catch (Exception exception){}
    }
}
