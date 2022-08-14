package countdowns;

import de.niklas.ragemode.Ragemode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import utils.ItemBuilder;

public class GraceCountdown extends Countdown{

    private int seconds=10;
    private Ragemode plugin;

    public GraceCountdown(Ragemode plugin){
        this.plugin = plugin;
    }

    @Override
    public void start() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                switch (seconds){
                    case 10: case 5: case 3: case 2:
                        Bukkit.broadcastMessage(plugin.getPrefix()+"§7Die Schutzzeit endet in §e"+seconds+" §7Sekunden");
                        break;

                    case 1:
                        Bukkit.broadcastMessage(plugin.getPrefix()+"§7Die Schutzzeit endet in §e"+seconds+" §7Sekunde");
                        break;

                    case 0:
                        stop();
                        Bukkit.broadcastMessage(plugin.getPrefix()+"§cSpieler können sich nun angreifen");

                        for(Player current : Bukkit.getOnlinePlayers())
                        new ItemBuilder().setIngameItems(current);
                        break;

                    default:
                        break;
                }
                seconds--;
            }
        },0 ,20);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
