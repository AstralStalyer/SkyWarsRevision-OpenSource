package cn.rmc.skywars.game;

import me.arasple.mc.trhologram.hologram.Hologram;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Map {

    public ArrayList<Location> locations = new ArrayList<>();

    public ArrayList<Location> smallchests = new ArrayList<>();

    public ArrayList<Location> middlechests = new ArrayList<>();

    public HashMap<Location, Hologram> openchests = new HashMap<>();

    public HashMap<Location,Boolean> isopened = new HashMap<>();

    public Location middle;

    public String name;

    public String author;

    public GameMode mode;

}
