package cn.rmc.skywars;

import cn.rmc.skywars.command.DebugCommand;
import cn.rmc.skywars.listener.*;
import cn.rmc.skywars.manager.GameManager;
import cn.rmc.skywars.manager.MapManager;
import cn.rmc.skywars.manager.PlayerManager;
import cn.rmc.skywars.manager.ScoreBoardManager;
import cn.rmc.skywars.util.Config;
import cn.rmc.skywars.util.database.DataBase;
import cn.rmc.skywars.util.inventory.UIListener;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public class SkyWars extends JavaPlugin {


    @Getter
    private static SkyWars instance;
    @Getter
    private static Config mapconfig;

    @Getter
    private PlayerManager playerManager;

    @Getter
    private MapManager mapManager;

    @Getter
    private GameManager gameManager;

    @Getter
    private ScoreBoardManager scoreBoardManager;

    @Getter
    private static DataBase dataBase;


    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        configload();
        regEvents();
        regManager();
        regCommand();
        regMysql();
        MinecraftServer.getServer().setMotd("Waiting");
    }

    @Override
    public void onDisable() {
        dataBase.close();
    }

    void regManager(){
        playerManager = new PlayerManager();
        mapManager = new MapManager();
        gameManager = new GameManager();
        scoreBoardManager = new ScoreBoardManager();
    }

    void configload() throws Throwable {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File schem = new File(getDataFolder(),"//schematics");
        if(!schem.exists()){
            schem.mkdir();
        }
        saveResource("schematics/Air.schematic",false);
        saveResource("schematics/Default.schematic",false);
        File mapfile = new File(this.getDataFolder(),"maps.yml");
        if(!mapfile.exists()) mapfile.createNewFile();
        mapconfig = new Config(mapfile);

    }

    void regEvents(){
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this,"BungeeCord",new BungeeMessageListener());
        Arrays.asList(new PlayerListener(),
                new UIListener(),
                new WorldListener(),
                new EntityListener(),
                new PlayerInteractListener(),
                new GameListener(),
                new KitListener()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener,this));
    }
    void regCommand(){
        registerCommand(new DebugCommand());
    }


    void regMysql(){
        dataBase = DataBase.create(new MemoryConfiguration(){{
            set("ip","127.0.0.1");
            set("port",3306);
            set("database","skywars");
            set("username","root");
            set("password","TransCraftStudio");
        }});
    }

    private void registerCommand(Command cmd) {
        MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), this.getName(), cmd);
    }

}
