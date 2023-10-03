package cn.rmc.skywars.listener;

import cn.rmc.skywars.SkyWars;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeMessageListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if(!s.equals("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
    }
}
