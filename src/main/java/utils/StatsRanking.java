package utils;

import de.niklas.ragemode.Ragemode;
import mysql.NayzAPI;
import mysql.StatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsRanking {

    static HashMap<Integer, String> rang = new HashMap<Integer, String>();

    private Ragemode plugin;

    public StatsRanking(Ragemode plugin){
        this.plugin = plugin;
    }

    public void set(){
        ResultSet rs = plugin.getMysql().getResult("SELECT UUID FROM CookieRage ORDER BY Stats_Points DESC LIMIT 3");

        int in = 0;

        try {
            while(rs.next()) {
                in++;
                rang.put(in, rs.getString("UUID"));
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }

        Location location1 = new Location(Bukkit.getWorld("world"), -3.7, 82, -2.5);
        Location location2 = new Location(Bukkit.getWorld("world"), -3.7, 82, -3.5);
        Location location3 = new Location(Bukkit.getWorld("world"), -3.7, 82, -4.5);

        List<Location> locations = new ArrayList<Location>();
        locations.add(location1);
        locations.add(location2);
        locations.add(location3);

        for(int i = 0; i != locations.size(); i++) {
            int id = i+1;

            Location skullLocation = locations.get(i);

            skullLocation.getBlock().setType(Material.PLAYER_HEAD);
            if(skullLocation.getBlock().getType() != Material.PLAYER_HEAD)return;
            Skull skull = (Skull)skullLocation.getBlock().getState();
            skull.setOwner(plugin.getStatsAPI().getUUID(rang.get(id)));
            skull.setRotation(BlockFace.WEST);
            skull.update();

            Location location = new Location(locations.get(i).getWorld(), locations.get(i).getX()+1, locations.get(i).getY()-1, locations.get(i).getZ());
            if(location.getBlock().getState() instanceof Sign) {
                BlockState blockState = location.getBlock().getState();
                Sign sign = (Sign) blockState;

                sign.setLine(0, "Platz #"+id);
                sign.setLine(1, plugin.getStatsAPI().getUUID(rang.get(id)));
                sign.setLine(2, ""+plugin.getStatsAPI().getPoints(rang.get(id)) + " Punkte");
                sign.setLine(3, ""+plugin.getStatsAPI().getWins(rang.get(id))+" Siege");
                sign.update();
            }
        }
    }
}
