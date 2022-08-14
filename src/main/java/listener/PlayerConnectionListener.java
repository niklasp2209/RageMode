package listener;

import countdowns.LobbyCountdown;
import de.niklas.ragemode.Ragemode;
import gamestate.IngameState;
import gamestate.LobbyState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import utils.LocationUtil;

public class PlayerConnectionListener implements Listener {

    private Ragemode plugin;
    public PlayerConnectionListener(Ragemode plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleConnect(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(plugin.getGameStateUtils().getCurrentGameState() instanceof LobbyState){
            plugin.getPlayers().add(player);
            event.setJoinMessage("§7» §a"+player.getDisplayName()+" §7hat die Runde betreten [§a"+
                    plugin.getPlayers().size()+"§7/§c"+plugin.getMax_Players()+"§7]");

            LocationUtil locationUtil = new LocationUtil(plugin, "Lobby");
            if(locationUtil.loadLocation() != null)
                player.teleport(locationUtil.loadLocation());

            if(plugin.getPlayers().size() >= plugin.getMin_Players()){
                LobbyState lobbyState = (LobbyState)plugin.getGameStateUtils().getCurrentGameState();
                LobbyCountdown countdown = lobbyState.getCountdown();
                if(!countdown.isRunning()){
                    countdown.stopIdle();
                    countdown.start();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleDisconnect(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(plugin.getGameStateUtils().getCurrentGameState() instanceof LobbyState){
            plugin.getPlayers().remove(player);
            event.setQuitMessage("§7« §a"+player.getDisplayName()+" §7hat die Runde verlassen [§a"+
                    plugin.getPlayers().size()+"§7/§c"+plugin.getMax_Players()+"§7]");

            if(plugin.getPlayers().size() < plugin.getMin_Players()){
                LobbyState lobbyState = (LobbyState)plugin.getGameStateUtils().getCurrentGameState();
                LobbyCountdown countdown = lobbyState.getCountdown();
                if(countdown.isRunning()){
                    countdown.stop();
                    countdown.startIdle();
                }
            }
        }
    }
}
