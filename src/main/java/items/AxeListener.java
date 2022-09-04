package items;

import de.niklas.ragemode.Ragemode;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftSnowball;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

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
        if(!(event.getItem().getType() == Material.COOKIE))return;
        Item item = player.getWorld().dropItem(player.getEyeLocation(), player.getItemInHand());
        item.setVelocity(player.getLocation().getDirection().multiply(1.3D));
        player.setItemInHand(new ItemStack(Material.AIR));
        new AxeThread(player, 0.6, item).start();
        Snowball snowball = (Snowball) player.launchProjectile(Snowball.class);
        snowball.setVelocity(item.getVelocity());
        for(Player current : Bukkit.getOnlinePlayers())
        ((CraftPlayer)current).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(((CraftSnowball) snowball).getHandle().getId()));
    }

    @EventHandler
    public void handleAxeHit(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player))return;
            Player player = (Player)event.getEntity();
            if(event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                if(event.getDamage() == 0.0D){
                    event.setDamage(30.0D);
                    player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1L, 1L);
                }
        }else if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                event.setCancelled(true);
                event.setDamage(0.0D);
        }
    }
}
