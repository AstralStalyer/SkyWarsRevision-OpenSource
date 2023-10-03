package cn.rmc.skywars.task;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.enums.Cages;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.json.simple.JSONArray;

import java.io.File;

public abstract class CreateCageTask implements Runnable {

    Location loc;
    Cages cage;

    private EditSession session;
    public CreateCageTask(Location loc, Cages cage){
        this.loc =loc;
        this.cage = cage;
    }
    @Override
    public void run() {
        File file = new File(SkyWars.getInstance().getDataFolder(),"//schematics//"+ cage.getSchem() +".schematic");

        World world = new BukkitWorld(Bukkit.getWorld("world"));
        Vector position = new Vector(loc.getX(),loc.getY(),loc.getZ());

        final MCEditSchematicFormat mcedit = (MCEditSchematicFormat) SchematicFormat.MCEDIT;

        session = FaweAPI.getEditSessionBuilder(world).build();

        try {
            final CuboidClipboard clipboard = mcedit.load(file);
            clipboard.paste(session, position, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        finall();

    }
    public abstract void finall();
}
