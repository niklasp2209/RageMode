package utils;

import de.niklas.ragemode.Ragemode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationUtil {

    private Ragemode plugin;
    private Location location;
    private String path;

    public LocationUtil(Ragemode plugin, Location location, String path){
        this.plugin = plugin;
        this.location = location;
        this.path = path;
    }

    public LocationUtil(Ragemode plugin, String path){
        this(plugin, null, path);
    }

    public void saveLocation(){
        FileConfiguration fileConfiguration = plugin.getConfig();
        fileConfiguration.set(path+".World", location.getWorld().getName());
        fileConfiguration.set(path+".X", location.getX());
        fileConfiguration.set(path+".Y", location.getY());
        fileConfiguration.set(path+".Z", location.getZ());
        fileConfiguration.set(path+".Yaw", location.getYaw());
        fileConfiguration.set(path+".Pitch", location.getPitch());
        plugin.saveConfig();
    }

    public Location loadLocation(){
        FileConfiguration fileConfiguration = plugin.getConfig();

        if(fileConfiguration.contains(path)) {
            World world = Bukkit.getWorld(fileConfiguration.getString(path + ".World"));
            double x = fileConfiguration.getDouble(path + ".X"),
                    y = fileConfiguration.getDouble(path + ".Y"),
                    z = fileConfiguration.getDouble(path + ".Z");
            float yaw = (float) fileConfiguration.getDouble(path + ".Yaw"),
                    pitch = (float) fileConfiguration.getDouble(path + ".Pitch");

            return new Location(world, x, y, z, yaw, pitch);
        }else
            return null;
    }
}
