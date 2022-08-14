package gamestate;

import de.niklas.ragemode.Ragemode;
import mapvoting.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class IngameState extends GameState{

    private Ragemode plugin;
    private Maps maps;
    private ArrayList<Player> players;

    public IngameState(Ragemode plugin){
        this.plugin = plugin;
        Collections.shuffle(plugin.getPlayers());
        players = plugin.getPlayers();
    }

    @Override
    public void start() {
        maps = plugin.getVoting().getWinnerMap();
        maps.load();
        for(int i = 0; i < players.size(); i++)
            players.get(i).teleport(maps.getLocations()[i]);

        for(Player current : Bukkit.getOnlinePlayers())
            current.getInventory().clear();
    }

    @Override
    public void stop() {

    }
}
