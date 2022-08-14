package items;

import de.niklas.ragemode.Ragemode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AxeListener implements Listener {

    private Ragemode plugin;

    public AxeListener(Ragemode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void handleInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() == null)return;
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))return;
        if(!(event.getItem().getType() == Material.IRON_AXE))return;
        Item item = player.getWorld().dropItem(player.getEyeLocation(), player.getItemInHand());
        item.setVelocity(player.getLocation().getDirection().multiply(1.3D));
        player.setItemInHand(new ItemStack(Material.AIR));
        new AxeThread(player, 0.6, item);
    }
}
