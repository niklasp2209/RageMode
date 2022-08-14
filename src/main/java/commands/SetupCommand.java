package commands;

import de.niklas.ragemode.Ragemode;
import mapvoting.Maps;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.LocationUtil;

import java.io.IOException;

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
                    }else
                        sendInstructions(player);
                }else if(args.length == 3){
                    if(args[0].equalsIgnoreCase("create")){
                        Maps maps = new Maps(plugin, args[1]);
                        if(!maps.exists()){
                            maps.create(args[2]);
                            player.sendMessage(plugin.getPrefix()+"§7Die Map §e"+args[1]+" §7wurde erstellt.");
                        }else
                            player.sendMessage(plugin.getPrefix()+"§cDiese Map existiert bereits");
                    }else if(args[0].equalsIgnoreCase("set")){
                        Maps maps = new Maps(plugin, args[1]);
                        if(maps.exists()){
                            try{
                                int locationID = Integer.parseInt(args[2]);
                                if(locationID > 0 && locationID <= plugin.getMax_Players()){
                                    maps.setLocations(locationID, player.getLocation());
                                    player.sendMessage(plugin.getPrefix()+"§7Du hast Spawn-Location §6"+locationID+" §7gesetzt");
                                }else
                                    player.sendMessage(plugin.getPrefix()+"§cBitte gib eine Zahl zwsichen 1 und §c"+plugin.getMax_Players()+" §cein");
                            }catch(NumberFormatException exception){
                                exception.printStackTrace();
                            }
                        }else
                            player.sendMessage(plugin.getPrefix()+"§cDiese Map existiert nicht");
                    }else
                        sendInstructions(player);
                }else
                    sendInstructions(player);
            }else
                player.sendMessage(plugin.getNo_Permission());
        }
        return false;
    }

    public void sendInstructions(Player player){
        player.sendMessage(plugin.getPrefix() + "§a/setup <Lobby>");
        player.sendMessage(plugin.getPrefix() + "§a/setup create <Name>");
        player.sendMessage(plugin.getPrefix() + "§a/setup set <Mapname> <1-"+plugin.getMax_Players()+">");
    }
}
