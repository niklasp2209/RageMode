package listener;

import de.niklas.ragemode.Ragemode;
import gamestate.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import utils.FastBoard;
import utils.ScoreboardManager;

import java.util.HashMap;

public class PlayerDeathListener implements Listener {

    private Ragemode plugin;
    public static HashMap<Player, Integer> points;

    public PlayerDeathListener(Ragemode plugin){
        this.plugin = plugin;
        points = new HashMap<>();
    }

    @EventHandler
    public void handleDeath(PlayerDeathEvent event){
        event.setDeathMessage(null);
        if(plugin.getGameStateUtils().getCurrentGameState() instanceof IngameState){
            Player player = event.getEntity();
            if(player.getKiller() == null) {
                Bukkit.broadcastMessage(plugin.getPrefix()+"§a"+player.getName()+" §7(§e"+points.get(player)+"§7) §7ist gestorben");
                int points_Player = points.get(player);
                if(points_Player != 0)
                    points_Player--;
                points.replace(player, points_Player-1, points_Player);
                FastBoard fastBoard = new FastBoard(player);
                fastBoard.updateTitle("§6·§e• RageMode §8| §7Stats");
                fastBoard.updateLine(9, "§7» §e"+ points_Player);

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

                FastBoard fastBoard = new FastBoard(player);
                fastBoard.updateTitle("§6·§e• RageMode §8| §7Stats");
                fastBoard.updateLine(9, "§7» §e"+ points_Player);

                FastBoard fastBoardKiller = new FastBoard(killer);
                fastBoard.updateTitle("§6·§e• RageMode §8| §7Stats");
                fastBoardKiller.updateLine(9, "§7» §e"+ points_Killer);

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
