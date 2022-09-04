package listener;

import countdowns.LobbyCountdown;
import de.niklas.ragemode.Ragemode;
import gamestate.IngameState;
import gamestate.LobbyState;
import mapvoting.Voting;
import mysql.NayzAPI;
import mysql.StatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import utils.ItemBuilder;
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
            if(plugin.getStatsAPI().getKills(plugin.getStatsAPI().playerUUID(player.getName())) == -1)
                plugin.getStatsAPI().setDefault(plugin.getStatsAPI().playerUUID(player.getName()));

            plugin.getPlayers().add(player);
            event.setJoinMessage("§7» §a"+player.getDisplayName()+" §7hat die Runde betreten [§a"+
                    plugin.getPlayers().size()+"§7/§c"+plugin.getMax_Players()+"§7]");

            new ItemBuilder().setLobbyItems(player);
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
        }else if(plugin.getGameStateUtils().getCurrentGameState() instanceof IngameState){
            event.setJoinMessage(null);
            player.setGameMode(GameMode.SPECTATOR);
            player.setAllowFlight(true);
            player.setFlying(true);
            for(Player current : Bukkit.getOnlinePlayers())
                current.hidePlayer(player);
        }else{
            event.setJoinMessage(null);
            player.kickPlayer("§cDer Server startet neu...");
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
            Voting voting = plugin.getVoting();
            if(voting.getPlayerVotes().containsKey(player.getName())) {
                voting.getVotingMaps()[voting.getPlayerVotes().get(player.getName())].removeVote();
                voting.getPlayerVotes().remove(player.getName());
                voting.initInventory();
            }
        }else if(plugin.getGameStateUtils().getCurrentGameState() instanceof IngameState){
            if(plugin.getPlayers().contains(player))
                plugin.getPlayers().remove(player);
            new PlayerDeathListener(plugin).checkEnd(player);
        }
    }
}
