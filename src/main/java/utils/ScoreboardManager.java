package utils;

import countdowns.LobbyCountdown;
import de.niklas.ragemode.Ragemode;
import gamestate.IngameState;
import gamestate.LobbyState;
import mapvoting.Maps;
import mapvoting.Voting;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager implements Listener {

    public static final Map<UUID, FastBoard> boards = new HashMap<>();
    private Ragemode plugin;

    public ScoreboardManager(Ragemode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FastBoard fastBoard = new FastBoard(player);
        fastBoard.updateTitle("§6·§e• RageMode §8| §7Stats");
        boards.put(player.getUniqueId(), fastBoard);
        updateBoard(fastBoard);
    }

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        FastBoard fastBoard = boards.remove(player.getUniqueId());
        if(fastBoard != null) {
            fastBoard.delete();
        }
    }
    public void updateBoard(FastBoard fastBoard) {
        Player player = fastBoard.getPlayer();
        if(plugin.getGameStateUtils().getCurrentGameState() instanceof LobbyState) {
            fastBoard.updateLines(
                    ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------",
                    " ",
                    "§6·§e• §aMap",
                    "§7»§e ",
                    " ",
                    "§6·§e• §aTeams",
                    "§7» §cVerboten",
                    " ",
                    ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------- ");

            LobbyState lobbyState = (LobbyState) plugin.getGameStateUtils().getCurrentGameState();
            LobbyCountdown lobbyCountdown = lobbyState.getCountdown();
            if(lobbyCountdown.getSeconds() > 5)
                fastBoard.updateLine(3, "§7» §eAbstimmung läuft...");
            else{
                Voting voting = plugin.getVoting();
                Maps winnerMap;
                winnerMap = voting.getWinnerMap();
                fastBoard.updateLine(3, "§7» §e"+ winnerMap.getName());

            }

        }else if(plugin.getGameStateUtils().getCurrentGameState() instanceof IngameState) {
            fastBoard.updateLines(
                    ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "---------------------",
                    " ",
                    "§6·§e• §aAktuelle Killstreak",
                    "§7»§e ",
                    " ",
                    "§6·§e• §aKillstreak §7§o(Rekord)",
                    "§7»§e ",
                    " ",
                    "§6·§e• §aGesamte Kills §7§o(bis Sieg)",
                    "§7» §e",
                    " ",
                    ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "--------------------- ");
        }
        Scoreboard scoreboard = player.getScoreboard();
        if(scoreboard.getTeam("spieler") == null){
            Team spieler = scoreboard.registerNewTeam("spieler");
        }else{
            Team spieler = scoreboard.getTeam("spieler");
            spieler.setPrefix("§a");
            spieler.setColor(ChatColor.GREEN);
            spieler.addEntry(fastBoard.getPlayer().getName());
        }
    }

    public void startHotbar() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().size() < 1)return;
                for(Player current : Bukkit.getOnlinePlayers()) {
                    current.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7·§e• §cTeam verboten §e•§7·"));
                }

            }

        }, 0, 20);
    }
}
