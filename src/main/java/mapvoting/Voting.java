package mapvoting;

import de.niklas.ragemode.Ragemode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import utils.ItemBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Voting {

    private Ragemode plugin;
    private ArrayList<Maps> mapsArrayList;
    private Maps[] votingMaps;
    private int[] inventoryOrder = new int[]{11, 13, 15};
    private HashMap<String, Integer> playerVotes;
    private Inventory inventory;

    public Voting(Ragemode plugin, ArrayList<Maps> mapsArrayList){
        this.plugin = plugin;
        this.mapsArrayList = mapsArrayList;
        votingMaps = new Maps[3];
        playerVotes = new HashMap<>();

        randomMaps();
        initInventory();
    }

    private void randomMaps(){
        for(int i = 0; i < votingMaps.length; i++){
            Collections.shuffle(mapsArrayList);
            votingMaps[i] = mapsArrayList.remove(0);
        }
    }

    public void initInventory(){
        inventory = Bukkit.createInventory(null, 9*3, "§7● §eMap Abstimmung");
        for(int i = 0; i < votingMaps.length; i++){
            Maps currentMap = votingMaps[i];
            inventory.setItem(inventoryOrder[i], new ItemBuilder().createItemLore(Material.PAPER, "§a"+currentMap.getName()+" §7- §c"+currentMap.getVotes()+" §cStimmen", 1,
                    Arrays.asList("§r","§7von §e"+currentMap.getBuilder())));
        }
    }

    public Maps getWinnerMap(){
        Maps winnerMap = votingMaps[0];
        for(int i = 1; i < votingMaps.length; i++){
            if(votingMaps[i].getVotes() >= winnerMap.getVotes())
                winnerMap = votingMaps[i];
        }
        return winnerMap;
    }

    public void vote(Player player, int votingMap){
        if(!playerVotes.containsKey(player.getName())){
            votingMaps[votingMap].addVote();
            player.closeInventory();
            player.sendMessage(plugin.getPrefix()+"§7Du hast für §a"+votingMaps[votingMap].getName()+" §7abgestimmt");
            playerVotes.put(player.getName(), votingMap);
            initInventory();
        }else
            player.sendMessage(plugin.getPrefix()+"§cDu hast bereits abgstimmt");
    }

    public HashMap<String, Integer> getPlayerVotes() {
        return playerVotes;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int[] getInventoryOrder() {
        return inventoryOrder;
    }

    public Maps[] getVotingMaps() {
        return votingMaps;
    }
}