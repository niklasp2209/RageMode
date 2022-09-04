package utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    public ItemStack createItem(Material id, String name, int ammount, String lore){
        ItemStack item = new ItemStack(id, ammount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> list = new ArrayList<>();
        list.add(lore);
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;

    }

    public ItemStack createItemLore(Material id, String name, int ammount, List<String> lore){
        ItemStack item = new ItemStack(id, ammount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> list = new ArrayList<>();
        list.addAll(lore);
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;
    }

    public void setLobbyItems(Player player){
        ItemStack item_Mapvoting = createItem(Material.PAPER, "§aMap Auswahl", 1, "§7● Rechtsklick zum benutzen");
        ItemStack item_Achievements = createItem(Material.NETHER_STAR, "§aErfolge", 1, "§7● Rechtsklick zum benutzen");
        ItemStack item_Leave = createItem(Material.MAGMA_CREAM, "§czurück zur Lobby", 1, "§7● Rechtsklick zum benutzen");

        player.getInventory().clear();
        player.getInventory().setItem(0, item_Mapvoting);
        player.getInventory().setItem(7, item_Achievements);
        player.getInventory().setItem(8, item_Leave);

        player.setGameMode(GameMode.ADVENTURE);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFlying(false);
        player.setLevel(0);
        player.setExp(0);
    }

    public void setIngameItems(Player player){
        ItemStack item_bow = createItemLore(Material.BOW, "§eBogen", 1, Arrays.asList("§7Kann unendlich oft genutzt","§7werden und tötet Gegner mit","§7einem Schuss"));
        ItemStack item_sword = createItemLore(Material.IRON_SWORD, "§cMesser", 1, Arrays.asList("§7Tötet Gegner mit einem Schlag"));
        ItemStack item_axe = createItemLore(Material.COOKIE, "§bCookie-Wurfstern", 1, Arrays.asList("§7Kann einmalig geworfen werden", "§7und tötet Gegner beim treffen"));
        ItemStack item_arrow = new ItemStack(Material.ARROW);
        item_bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        item_bow.addEnchantment(Enchantment.DURABILITY, 3);
        ItemMeta itemMeta = item_bow.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item_bow.setItemMeta(itemMeta);

        player.getInventory().setItem(35, item_arrow);
        player.getInventory().setItem(1, item_sword);
        player.getInventory().setItem(0, item_bow);
        player.getInventory().setItem(2, item_axe);
    }
}
