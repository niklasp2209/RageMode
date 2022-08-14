package commands;

import countdowns.LobbyCountdown;
import de.niklas.ragemode.Ragemode;
import gamestate.LobbyState;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    private Ragemode plugin;

    public StartCommand(Ragemode plugin){
     this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            if(player.hasPermission("ragemode.cmd.start")){
                if(args.length == 0){
                    if(plugin.getGameStateUtils().getCurrentGameState() instanceof LobbyState){
                        LobbyState lobbyState = (LobbyState) plugin.getGameStateUtils().getCurrentGameState();
                        LobbyCountdown lobbyCountdown = lobbyState.getCountdown();
                        lobbyCountdown.setSeconds(10);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1L, 1L);
                        player.sendMessage(plugin.getPrefix()+"§cDu hast das Spiel gestartet");
                    }else
                        player.sendMessage(plugin.getPrefix()+"§cDas Spiel wurde bereits gestartet!");
                }else
                    player.sendMessage(plugin.getPrefix()+"§cBenutze /start");
            }else
                player.sendMessage(plugin.getNo_Permission());
        }
        return false;
    }
}
