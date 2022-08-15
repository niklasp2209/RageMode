package listener;

import de.niklas.ragemode.Ragemode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class PlayerChatListener implements Listener {

    private Ragemode plugin;

    public PlayerChatListener(Ragemode plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void handleCommand(PlayerCommandPreprocessEvent event){
        if(!(event.isCancelled())){
            Player player = event.getPlayer();
            String message = event.getMessage().split(" ")[0];
            HelpTopic helpTopic = Bukkit.getServer().getHelpMap().getHelpTopic(message);
            if(helpTopic == null){
                player.sendMessage(plugin.getPrefix()+"§7"+message + " §7wurde nicht gefunden!");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        event.setFormat("§a"+player.getName()+" §8» §7"+message);
    }
}
