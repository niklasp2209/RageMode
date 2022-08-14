package mapvoting;

import de.niklas.ragemode.Ragemode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import utils.LocationUtil;

public class Maps {

    private Ragemode plugin;
    private String name;
    private String builder;
    private Location[] locations;

    public Maps(Ragemode plugin, String name){
        this.plugin = plugin;
        this.name = name.toUpperCase();
        this.locations = new Location[plugin.getMax_Players()];
    }

    public boolean exists(){
        return (plugin.getConfig().getString("Maps."+name+".Builder", builder) != null);
    }

    public String getName() {
        return name;
    }

    public void create(String builder){
        this.builder = builder;
        plugin.getConfig().set("Maps."+name+".Builder", builder);
        plugin.saveConfig();
    }

    public Location[] getLocations() {
        return locations;
    }

    public void setLocations(int locationID, Location location) {
        locations[locationID - 1] = location;
        new LocationUtil(plugin, location, "Maps." + name + "." + locationID).saveLocation();
    }

    public boolean isPlayable(){
        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("Maps."+name);
        if(!configurationSection.contains("Builder"))return false;
        for(int i = 1; i < plugin.getMax_Players()+1; i++){
            if(!configurationSection.contains(Integer.toString(i)))return false;
        }
        return true;
    }
}
