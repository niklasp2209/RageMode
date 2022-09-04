package listener;

import de.niklas.ragemode.Ragemode;
import gamestate.LobbyState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import utils.LocationUtil;

public class PlayerListener implements Listener {

    private Ragemode plugin;
    public PlayerListener(Ragemode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void handleMove(PlayerMoveEvent event){
        if(plugin.getGameStateUtils().getCurrentGameState() instanceof LobbyState) {
            Player player = event.getPlayer();

            if(player.getLocation().getY() < 60) {
                LocationUtil locationUtil = new LocationUtil(plugin, "Lobby");
                player.teleport(locationUtil.loadLocation());
            }
        }
    }

    @EventHandler
    public void handleDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePickup(PlayerPickupItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void handlePickupArraw(PlayerPickupArrowEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void handleWeather(WeatherChangeEvent event){
        if(event.toWeatherState())
            event.setCancelled(true);
    }

    @EventHandler
    public void handleDamage(EntityDamageEvent event){
        if(plugin.getGameStateUtils().getCurrentGameState() instanceof LobbyState)
            event.setCancelled(true);
    }
}
