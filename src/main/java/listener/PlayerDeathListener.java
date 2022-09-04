package listener;

import de.niklas.ragemode.Ragemode;
import gamestate.GameState;
import gamestate.GameStateUtils;
import gamestate.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import utils.FastBoard;
import utils.ItemBuilder;
import utils.LocationUtil;
import utils.ScoreboardManager;

import java.util.HashMap;

public class PlayerDeathListener implements Listener {

    private Ragemode plugin;
    private GameStateUtils gameStateUtils;
    public static HashMap<Player, Integer> points;

    public PlayerDeathListener(Ragemode plugin){
        this.plugin = plugin;
        this.gameStateUtils = plugin.getGameStateUtils();
        points = new HashMap<>();
    }

    @EventHandler
    public void handleDeath(PlayerDeathEvent event){
        event.setDeathMessage(null);
        if(plugin.getGameStateUtils().getCurrentGameState() instanceof IngameState){
            Player player = event.getEntity();
            if(player.getKiller() == null) {
                int points_Player = points.get(player);
                if(points_Player != 0)
                    points_Player--;
                points.replace(player, points_Player-1, points_Player);

                FastBoard fastBoard = new FastBoard(player);
                fastBoard.updateTitle("§6·§e• CookieRage §8| §7Stats");
                new ScoreboardManager(plugin).updateBoard(fastBoard);

                Bukkit.broadcastMessage(plugin.getPrefix()+"§a"+player.getName()+" §7(§e"+points.get(player)+"§7) §7ist gestorben");

            }else{
                Player killer = player.getKiller();
                int points_Killer = points.get(killer);
                int points_Player = points.get(player);
                if(points_Player != 0)
                    points_Player--;
                points_Killer++;
                points.replace(killer, points_Killer-1, points_Killer);
                points.replace(player, points_Player+1, points_Player);
                Bukkit.broadcastMessage(plugin.getPrefix()+"§c"+killer.getName()+" §7(§e"+points.get(killer)+"§7) §7hat §a"+player.getName()+" §7(§e"+points.get(player)+"§7) §7getötet");

//                FastBoard fastBoard = new FastBoard(player);
//                fastBoard.updateTitle("§6·§e• CookieRage §8| §7Stats");
//                fastBoard.updateLine(9, "§7» §e"+ points_Player);

                FastBoard fastBoard = new FastBoard(killer);
                fastBoard.updateTitle("§6·§e• CookieRage §8| §7Stats");
                new ScoreboardManager(plugin).updateBoard(fastBoard);

                checkEnd(killer);
                new ItemBuilder().setIngameItems(killer);

            }
            Bukkit.getScheduler().runTask(plugin, () -> event.getEntity().spigot().respawn());
            new ItemBuilder().setIngameItems(player);
        }
    }

    public void checkEnd(Player player){
        int points = getPoints().get(player);
        if(points >= 10){
            Bukkit.broadcastMessage(plugin.getPrefix()+"§a"+player.getName()+" §7hat das Spiel §agewonnen§7!");
            for(Player current : Bukkit.getOnlinePlayers()){
                current.sendTitle("§a"+player.getName(), "§7hat §agewonnen", 15, 40, 15);
                current.playSound(current.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1L, 1L);
                current.getInventory().clear();
                current.setLevel(0);
                current.setExp(0);
                current.setHealth(20);
                LocationUtil locationUtil = new LocationUtil(plugin, "Lobby");
                if(locationUtil.loadLocation() != null)
                    current.teleport(locationUtil.loadLocation());
                gameStateUtils.setGameState(GameState.End_State);

                FastBoard fastBoard = new FastBoard(current);
                fastBoard.updateTitle("§6·§e• CookieRage §8| §7Stats");
                new ScoreboardManager(plugin).updateBoard(fastBoard);
            }
        }else{
            if(plugin.getPlayers().size() == 0){
                Bukkit.shutdown();
            }else if(plugin.getPlayers().size() < plugin.getMin_Players()){
                for(Player current : Bukkit.getOnlinePlayers()){
                    current.playSound(current.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1L, 1L);
                    current.getInventory().clear();
                    current.setLevel(0);
                    current.setExp(0);
                    current.setHealth(20);
                    LocationUtil locationUtil = new LocationUtil(plugin, "Lobby");
                    if(locationUtil.loadLocation() != null)
                        current.teleport(locationUtil.loadLocation());
                    gameStateUtils.setGameState(GameState.End_State);

                    FastBoard fastBoard = new FastBoard(current);
                    fastBoard.updateTitle("§6·§e• CookieRage §8| §7Stats");
                    new ScoreboardManager(plugin).updateBoard(fastBoard);
                }
            }
        }
    }

    public HashMap<Player, Integer> getPoints() {
        return points;
    }

    public void setPoints(HashMap<Player, Integer> points) {
        this.points = points;
    }
}
