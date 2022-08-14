package mapvoting;

import de.niklas.ragemode.Ragemode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class VotingListener implements Listener {

    private Ragemode plugin;
    private Voting voting;

    public VotingListener(Ragemode plugin){
        this.plugin = plugin;
        voting = plugin.getVoting();
    }

    @EventHandler
    public void handleInteract(PlayerInteractEvent event){
        if(event.getItem() == null)return;
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))return;
        Player player = event.getPlayer();
        if(!(event.getItem().getType() == Material.PAPER)) return;
        if(!(event.getItem().getItemMeta().getDisplayName().equals("§aMap Auswahl")))return;
        player.openInventory(voting.getInventory());
    }

    @EventHandler
    public void handleClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player))return;
        Player player = (Player) event.getWhoClicked();
        if(!(event.getView().getTitle().equals("§7● §eMap Abstimmung")))return;
        event.setCancelled(true);
        for(int i = 0; i < voting.getInventoryOrder().length; i++){
            if(voting.getInventoryOrder()[i] == event.getSlot()){
                voting.vote(player, i);
                return;
            }
        }
    }
}
