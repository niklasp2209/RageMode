package commands;

import de.niklas.ragemode.Ragemode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.LocationUtil;

public class SetupCommand implements CommandExecutor {

    private Ragemode plugin;

    public SetupCommand(Ragemode plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            if(player.hasPermission("ragemode.cmd.setup")){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("lobby")){
                        new LocationUtil(plugin, player.getLocation(), "Lobby").saveLocation();
                        player.sendMessage(plugin.getPrefix()+"§aDer Lobby-Punkt wurde gespeichert");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1L ,1L);
                    }else{
                        sendInstructions(player);
                    }
                }else {
                    sendInstructions(player);
                }
            }else
                player.sendMessage(plugin.getNo_Permission());
        }
        return false;
    }

    public void sendInstructions(Player player){
        player.sendMessage(plugin.getPrefix() + "§a/setup <Lobby>");
    }
}
