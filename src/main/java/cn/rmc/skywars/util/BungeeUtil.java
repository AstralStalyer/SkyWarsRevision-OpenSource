package cn.rmc.skywars.util;

import cn.rmc.skywars.SkyWars;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeUtil {
    public static void sendPlayer(Player player, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream o = new DataOutputStream(b);
        try {
            o.writeUTF("Connect");
            o.writeUTF(server);
            player.sendPluginMessage(SkyWars.getInstance(), "BungeeCord", b.toByteArray());
        }
        catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§c无法连接至BungeeCord,请检查spigot.yml");
        }
    }
}
